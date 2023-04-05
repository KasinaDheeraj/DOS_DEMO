package com.multi;


import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static com.multi.ServerDriver.MAX_PACKET_SIZE;

public class ClientDriver {

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        String msg="";
        Scanner sc=new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 5000)) {

            ClientThread threadClient = new ClientThread(socket);
            new Thread(threadClient).start();

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Node "+msg + ": has joined Server.");
            do {

                if(!socket.isConnected())throw new Exception("Not Connected!");

                System.out.println("___"+sdf3.format(new Timestamp(System.currentTimeMillis())));
                // Read input.
                msg = sc.nextLine();
                if (msg.equalsIgnoreCase("exit")) {
                    writer.println("exit");
                    break;
                }

                // Printing Timestamp.
                System.out.println(sdf3.format(new Timestamp(System.currentTimeMillis())));

                if(!socket.isConnected())throw new Exception("Not Connected!");
                // Sending message to server.
                writer.println(msg);

            } while (!msg.equalsIgnoreCase("exit"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("Bye Bye!");
    }
}