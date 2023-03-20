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

                // Read messages from client.
                String outputString = input.readLine();

                // For maintaining count of requests from this client.
                requestCount.put(socket,requestCount.getOrDefault(socket,0)+1);

                if (outputString.equalsIgnoreCase("exit")) {
                    throw new SocketException();
                }
                if (!clientNameList.containsKey(socket)) {
                    String[] messageString = outputString.split(":", 2);
                    clientNameList.put(socket, messageString[0]);
                    String message=messageString[0] + messageString[1]+"("+requestCount.getOrDefault(socket,0).toString()+")";
                    System.out.println(message);
                } else {
                    String message=outputString+"("+requestCount.getOrDefault(socket,0).toString()+")";
                    System.out.println(message);
                }

                //Sending acknowledge message to client.
                String ack = "ACK\n";
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(ack);
                System.out.println("ACK sent to the client\n");

            }
        } catch (SocketException e) {
            String printMessage = clientNameList.get(socket) + " has been disconnected";
            System.out.println(printMessage);
            clients.remove(socket);
            clientNameList.remove(socket);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

}