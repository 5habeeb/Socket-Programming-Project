import java.net.*;
import java.io.*;

public class HTTPAsk {
    private static ServerSocket server;
    private static int port;

    public static void main( String[] args) {
        port = Integer.parseInt(args[0]);

        try {
            server = new ServerSocket(port);
            System.out.println("server is running \t waiting for connection...");
        } catch (IOException e) {
            System.out.println("Cannot listen to port " + port);
            e.printStackTrace();
        }

        while (true) {
            String httpResponse;
            String hostname = null;
            String port = null;
            String string = null;
            boolean error = false;

            try {
                // Accept and establish a connection with a client
                Socket connectionSocket;
                connectionSocket = server.accept();
                System.out.println("Got a connection");

                // Get the data from the client
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                String line = inFromClient.readLine();
                String header = line;
                StringBuilder resultBuilder = new StringBuilder("");
                while (!line.isEmpty()) {
                    resultBuilder.append(line).append("\r\n");
                    line = inFromClient.readLine();
                }

                // the string could miss any of the characters used for splitting
                // which may cause runtime errors
                try{
                    String[] request = header.split("/");
                    String str1 = request[1];
                    String[] res1 = str1.split(" ");
                    String str2 = res1[0];
                    System.out.println(str2);


                    if ( str2.contains("ask") && str2.contains("hostname") && str2.contains("port") )
                    {
                        String res3 = str2.substring(4); // get rid of ask
                        String[] para = res3.split("&");
                        String[] para0 = para[0].split("=");
                        String[] para1 = para[1].split("=");

                        hostname = para0[1];
                        port = para1[1];
                        System.out.println(hostname);
                        System.out.println(port);

                        if (str2.contains("string"))
                        {
                            String[] para2 = para[2].split("=");
                            string = para2[1];
                            System.out.println(string);
                        }
                    }
                    else {
                        error = true;
                    }
                }catch(RuntimeException e)
                    {
                        System.out.println("normal error");
                        httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "Error: \n the requested method does not exist";
                    }


                if(error){
                    httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "Error: \n the requested method does not exist";
                }
                else{
                    String TCPClientResponse = TCPClient.askServer(hostname,Integer.parseInt(port),string);
                    System.out.println("method response: " + TCPClientResponse);

                    // generate the HTTP response
                    httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + TCPClientResponse;
                }

                PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
                outToClient.println(httpResponse);

                System.out.println("Sent to client: " + httpResponse);

                //close connection
                connectionSocket.close();
                System.out.println("connection closed");

            } catch (IOException e) {
                System.err.println("Error.");
                System.exit(1);
            }

        }


    }
}

