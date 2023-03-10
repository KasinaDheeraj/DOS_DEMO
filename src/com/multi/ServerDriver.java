package com.multi;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerDriver {

    public static void main(String[] args) {
        ArrayList<Socket> threadList = new ArrayList<>();
        HashMap<Socket, String> clientNameList = new HashMap<Socket, String>();
        HashMap<Socket, Integer> requestCount=new HashMap<>();
        try (ServerSocket serversocket = new ServerSocket(5000)) {
            System.out.println("Started Server:");
            while (true) {
                Socket socket = serversocket.accept();
                threadList.add(socket);
                ServerThread ThreadServer = new ServerThread(socket, threadList, clientNameList,requestCount);
                ThreadServer.start();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}