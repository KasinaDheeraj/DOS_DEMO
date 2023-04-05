package com.multi;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.multi.ServerDriver.MAX_PACKET_SIZE;

//public class AttackerDriver {
public class AttackerDriver implements Runnable {

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    public static void main(String[] args) {
    public void run(){

        final String msg=MAX_PACKET_SIZE;

        try (Socket socket = new Socket("localhost", 5000)){;//, InetAddress.getByName("127.0.0.1"),5000+instances)) {

            AttackerThread threadAttacker = new AttackerThread(socket);
            new Thread(threadAttacker).start();

            // Sending message to server.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Node "+ ": has joined Server.");

            do {
                String message = ("Attacker : ");

//                while(true) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            // Printing Timestamp.
//                            System.out.println(sdf3.format(new Timestamp(System.currentTimeMillis())));
//
//                            // Send message to Server.
//                            writer.println(message + msg);
//                        }
//                    }).start();
//                    TimeUnit.SECONDS.sleep(20);
//                }

                // Printing Timestamp.
                System.out.println(sdf3.format(new Timestamp(System.currentTimeMillis())));


                writer.println(message + msg);

            } while (!msg.equalsIgnoreCase("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}