import java.net.*;
import java.io.*;

public class HTTPEcho {
    private static ServerSocket server;

    public static void main( String[] args) {
        int port = Integer.parseInt(args[0]);

        try {
            server = new ServerSocket(port);
            System.out.println("server is running \t waiting for connection...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                handleClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleClient() throws IOException {
        Socket connectionSocket;
        String HTTPRequest = null;

        // accept and establish a connection with a client
        connectionSocket = server.accept();
        System.out.println("Got a connection");
        connectionSocket.setSoTimeout(2000);

        // Get the data from the client
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        // read line by line from the BufferReader
        try{
            long timeToStop = System.currentTimeMillis()+ 2000;
            String line;
            StringBuilder resultBuilder = new StringBuilder("");
            while((line = inFromClient.readLine()) != null ){
                if(line.isEmpty())
                    resultBuilder.append("\n");
                else
                    resultBuilder.append(line).append("\n");

                // update the result
                HTTPRequest = resultBuilder.toString();

                // check the timer
                if(System.currentTimeMillis() > timeToStop){
                    throw new SocketTimeoutException();
                }
            }
        }catch (SocketTimeoutException e){
            // show the HTTP request sent by the client
            System.out.println( "client says: \n" + HTTPRequest);

            // generate a HTTP response
            PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
            outToClient.println("HTTP/1.1 200 ok");
            outToClient.println("Content-type: text/plain");
            outToClient.println("\r\n");
            outToClient.println(HTTPRequest);

            //close connection
            connectionSocket.close();
            System.out.println("connection closed");
        }

    }




}

