package com.multi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Attacker {

    public static void main(String[] args) {

        int i=1300;
        while(i!=0){
            try {
                Socket socket = new Socket("localhost", 5000);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("Node+msg + : has joined Server.");

//                ClientThread threadClient = new ClientThread(socket);
//                new Thread(threadClient).start();
                AttackerDriver ad=new AttackerDriver();
                new Thread(ad).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i--;
        }
    }
}
