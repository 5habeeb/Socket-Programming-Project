import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output= new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                String line = input.readLine() + " ";
                System.out.println(line);
                output.println(line);
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
