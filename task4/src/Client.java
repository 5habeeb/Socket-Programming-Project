import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static final int PORT = 8888;

    public static void main(String [] args){
        try {
            socket = new Socket(InetAddress.getLocalHost(), PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Send something");
        Scanner userEntry = new Scanner(System.in);
        String packetTosend = userEntry.nextLine();
        PrintWriter output = null;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println(packetTosend);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String received = input.readLine();
            System.out.println("coming from server: " + received );

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
