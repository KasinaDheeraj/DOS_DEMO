package com.multi;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientDriver {
    public static void main(String[] args) {
        String name="";
        String msg="";
        Scanner sc=new Scanner(System.in);
        System.out.println("Please enter node name to join the Server : ");
        msg=sc.nextLine();
        name=msg;

        try (Socket socket = new Socket("localhost", 5000)) {
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
                writer.println(message + msg);
            } while (!msg.equalsIgnoreCase("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}