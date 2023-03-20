package com.multi;


import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientDriver {
    public static void main(String[] args) {

        char[] chars = new char[1024*1024*512];
        // Optional step - unnecessary if you're happy with the array being full of \0
        Arrays.fill(chars,'I');
        final String TEXT5MB=new String(chars);

        String name="";
        String msg="";
        Scanner sc=new Scanner(System.in);
        System.out.println("Please enter node name to join the Server : ");
        msg=sc.nextLine();
        name=msg;

        try (Socket socket = new Socket("localhost", 5000)) {
            // Sending message to server.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            ClientThread threadClient = new ClientThread(socket);
            new Thread(threadClient).start();

            writer.println("Node "+msg + ": has joined Server.");
            do {
                String message = (name + " : ");
                msg = sc.nextLine();
                if (msg.equalsIgnoreCase("exit")) {
                    writer.println("exit");
                    break;
                }

                writer.println(message + TEXT5MB);

            } while (!msg.equalsIgnoreCase("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}