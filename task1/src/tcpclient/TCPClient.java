package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        // create a socket
        Socket clientSocket = new Socket(hostname, port);
        clientSocket.setSoTimeout(5000);
        String result = null;


         try{
             //write to the server
             if(ToServer != null){
                 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                 outToServer.writeBytes(ToServer + "\n");
             }

             // create a bufferReader and get input form the server
             BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

             //set a timer
             long timeToStop = System.currentTimeMillis()+5000;

             // read line by line from the BufferReader
             String line;
             StringBuilder resultBuilder = new StringBuilder("");
             while((line = inFromServer.readLine()) != null ){
                 if(line.isEmpty())
                     resultBuilder.append("\n");
                 else
                     resultBuilder.append(line).append("\n");

                 // update the result
                 result = resultBuilder.toString();

                 // check the timer
                 if(System.currentTimeMillis() > timeToStop){
                     throw new SocketTimeoutException();
                 }
             }

             clientSocket.close();
             return result;
         }

         catch (SocketTimeoutException e){
            clientSocket.close();
            return result;
         }


    }

    public static String askServer(String hostname, int port) throws  IOException {
        // this function is never used by the TCPAsk class
        // have the same code except for writing to the server
        return null;
    }
}

