package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("The server was started. Waiting for connection...");
            client = serverSocket.accept();
            System.out.println("Client " + client + " connected");
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            new Thread(() -> waitingInput()).start();
            Scanner sc = new Scanner(System.in);
            while (client.isBound()){
                try{
                    out.writeUTF(sc.nextLine());
                } catch (Exception e){
                    System.out.println("Connecting with "+client+" list.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitingInput (){
        boolean isTerminated = false;
        while (!isTerminated){
            try {
                try {
                    System.out.println("Client saying: " + in.readUTF());
                } catch (EOFException e){
                    System.out.println("Connecting "+client+" lost");
                    isTerminated=true;
                }
            } catch (IOException e) {
                System.out.println("Connecting with "+client+" was lost");
                break;
            }
        }
    }
}
