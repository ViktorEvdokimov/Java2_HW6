import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("The server was started. Waiting for connection...");
            Socket client = serverSocket.accept();
            System.out.println("Client " + client + " connected");
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            new Thread(() -> waitingInput(in, client));
            Scanner sc = new Scanner(System.in);
            out.writeUTF(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void waitingInput (DataInputStream in, Socket client){
        while (true){
            try {
                System.out.println(client + " saying: " + in.readUTF());
            } catch (IOException e) {
                throw new RuntimeException("Connecting with client was lost");
            }
        }
    }
}
