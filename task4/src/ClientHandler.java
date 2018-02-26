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
        int i = 0;

            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output= new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                String line = input.readLine() + " ";
                while(true){
                    System.out.println(line + ++i);
                    output.println(line + ++i);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
