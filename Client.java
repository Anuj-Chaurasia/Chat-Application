import java.io.*;
import java.net.*;
public class Client {

    Socket socket;

    BufferedReader br; 
    PrintWriter out;

    public Client()
    {
        try 
        {
            System.out.println("Sending Request to Server...");
            socket = new Socket("127.0.0.1",8080);
            System.out.println("Connection Done...");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // reading a data.
            out = new PrintWriter(socket.getOutputStream());  // writing a data.

            startReading();
            startWriting();
            
        } catch (Exception e) 
        {
            
        }
    }
    public void startReading()   // write reading code here
     {
        // we use multi-threading for duing reading and writhing operation simunteniously

        Runnable r1=()->
        {

            System.out.println("Reader started...");

            try
            { 
                 while(true)
                {   
                    String msg = br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("Server Terminated the chat..");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + msg); // it execute when chat is not exit;
                }
            }catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection closed...");
            }
        };
        
        new Thread(r1).start(); // creating a object of thread class and pass refrence
    }
    public void startWriting()  // write writing code here
    {
            // Thread - it take a data from user and send to the client side.
             Runnable r2=()->
            {
                System.out.println("Writer Started...");
                try
                {  
                  while(true && !socket.isClosed())
                  {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content = br1.readLine();

                        out.println(content);
                        out.flush();    

                        if(content.equals("exit"))
                        {
                            socket.close();
                            break;
                        }
                  }
                }catch(Exception e){
                    // e.printStackTrace();
                    System.out.println("Connection closed...");
                }
            };
            
            new Thread(r2).start();
    }
    public static void main(String[] args) {
        
        System.out.println("This is Client...");
        new Client();
    }
}
