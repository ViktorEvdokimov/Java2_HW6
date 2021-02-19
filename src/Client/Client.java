package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;
    public Client() {
        try {
            System.out.println("Trying to connection to the server");
            server = new Socket("127.0.0.1", 8888);
            System.out.println("Connecting to the server: " + server + " successful");
            in = new DataInputStream(server.getInputStream());
            out = new DataOutputStream(server.getOutputStream());
            new Thread(() -> waitingInput()).start();
            Scanner sc = new Scanner(System.in);
            while (server.isConnected()) {
                try{
                    out.writeUTF(sc.nextLine());
                } catch (Exception e){
                    System.out.println("Connecting list.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitingInput (){

        try {
            boolean isTerminated = false;
            while (!isTerminated) {
                try {
                    System.out.println("Server saying: " + in.readUTF());
                } catch (EOFException e){
                    System.out.println("Connecting lost");
                    isTerminated=true;
                }
            }
        } catch (IOException e) {
            System.out.println("Connecting with server was lost");
        }
    }
}
