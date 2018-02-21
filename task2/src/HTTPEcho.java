import java.net.*;
import java.io.*;

public class HTTPEcho {
    private static ServerSocket server;

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try {
            server = new ServerSocket(port);
            System.out.println("server is running \t waiting for connection...");
        } catch (IOException e) {
            System.out.println("Cannot listen to port " + port);
            e.printStackTrace();
        }

        while (true) {
            try {
                // Accept and establish a connection with a client
                Socket connectionSocket;
                connectionSocket = server.accept();
                System.out.println("Got a connection");

                // Get the data from the client
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                String line = inFromClient.readLine();
                StringBuilder resultBuilder = new StringBuilder("");
                while (!line.isEmpty()) {
                    resultBuilder.append(line).append("\r\n");
                    line = inFromClient.readLine();
                }

                System.out.println("Client says: " + resultBuilder.toString());

                // generate the HTTP response
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + resultBuilder.toString();

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