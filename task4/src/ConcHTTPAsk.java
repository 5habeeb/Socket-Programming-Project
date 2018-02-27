import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcHTTPAsk {
    public static String port;

    public static void main (String[] args ){
        port = args[0];
        ConcHTTPAsk server = new ConcHTTPAsk();
        server.init();
    }

    private void init(){
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));

            while(true){
                System.out.println("wait for client...");
                Socket link = serverSocket.accept();
                System.out.println("Listening to port " + port);
                startConnection(link);
            }
        }
        catch (IOException ioe)
        { System.out.println("No connection"); }
    }

    private void startConnection(Socket link) {
        ClientHandler handler = new ClientHandler(link);
        Thread threadHandler = new Thread(handler);
        threadHandler.start();
    }
}
