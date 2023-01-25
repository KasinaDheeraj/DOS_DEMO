package com.multi;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {

    private Socket socket;
    private ArrayList<Socket> clients;
    private HashMap<Socket, String> clientNameList;
    HashMap<Socket, Integer> requestCount;

    public ServerThread(Socket socket, ArrayList<Socket> clients, HashMap<Socket, String> clientNameList,HashMap<Socket, Integer> requestCount) {
        this.socket = socket;
        this.clients = clients;
        this.clientNameList = clientNameList;
        this.requestCount=requestCount;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                requestCount.put(socket,requestCount.getOrDefault(socket,0)+1);
                String outputString = input.readLine();
                if (outputString.equalsIgnoreCase("exit")) {
                    throw new SocketException();
                }
                if (!clientNameList.containsKey(socket)) {
                    String[] messageString = outputString.split(":", 2);
                    clientNameList.put(socket, messageString[0]);
                    String message=messageString[0] + messageString[1]+"("+requestCount.getOrDefault(socket,0).toString()+")";
                    System.out.println(message);
                    showMessageToAllClients(socket, message);
                } else {
                    String message=outputString+"("+requestCount.getOrDefault(socket,0).toString()+")";
                    System.out.println(message);
                    showMessageToAllClients(socket, message);
                }
            }
        } catch (SocketException e) {
            String printMessage = "Node "+clientNameList.get(socket) + " has left the Server";
            System.out.println(printMessage);
            showMessageToAllClients(socket, printMessage);
            clients.remove(socket);
            clientNameList.remove(socket);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private void showMessageToAllClients(Socket sender, String outputString) {
        Socket socket;
        PrintWriter printWriter;
        int i = 0;
        while (i < clients.size()) {
            socket = clients.get(i);
            i++;
            try {
                if (socket != sender) {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(outputString);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}