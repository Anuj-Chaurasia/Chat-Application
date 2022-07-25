import java.net.*;
import java.io.*;
class Server{

    ServerSocket server;
    Socket socket;

    BufferedReader br; // for reading a data.
    PrintWriter out;  // for writing a data.

    public Server()
    {
        try {
            server = new ServerSocket(8080);
            System.out.println("Server is ready for accept connection..");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // reading a data.
            out = new PrintWriter(socket.getOutputStream());  // writing a data.

            startReading();
            startWriting();
            
        } catch (Exception e) {
            e.printStackTrace();
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
                            System.out.println("Client Terminated the chat..");
                            socket.close();
                            break;
                        }
                        System.out.println("Client: " + msg); // it execute when chat is not exit;
                   }
                }catch(Exception e)
                {
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
    public static void main(String[] args) 
    {
        
        System.out.println("This is server...");
        new Server();
    }
}