package com.multi;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerThread extends Thread {

    private Socket socket;
    private ArrayList<Socket> clients;
    private HashMap<Socket, String> clientNameList;
    private final String Address;
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    HashMap<Socket, Integer> requestCount;

    FileWriter fileWriter;

    public ServerThread(Socket socket, ArrayList<Socket> clients, HashMap<Socket, String> clientNameList,HashMap<Socket, Integer> requestCount,FileWriter myWriter) {
        this.socket = socket;
        this.clients = clients;
        this.clientNameList = clientNameList;
        this.requestCount=requestCount;
        this.Address=socket.getRemoteSocketAddress().toString();
        this.fileWriter=myWriter;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {

                // Read messages from client.
                String outputString = input.readLine();

                String ack = "ACK ";

                // For maintaining count of requests from this client.
                requestCount.put(socket,requestCount.getOrDefault(socket,0)+1);

                if (outputString.equalsIgnoreCase("exit")) {
                    throw new SocketException();
                }

                // If it is a New Connection.
                if (!clientNameList.containsKey(socket)) {
                    clientNameList.put(socket,Address);
                    String message="("+Address+")"+ " has joined Server"+"("+requestCount.getOrDefault(socket,0).toString()+")";
                    fileWriter.append(clientNameList.get(socket)+" ( "+sdf3.format(new Timestamp(System.currentTimeMillis()))+" )"+" : "+requestCount.get(socket)+"\n");
                    fileWriter.flush();
                    System.out.println(message);
                    ack="CONNECTION ACK";
                } else {
                    String message="("+Address+"): "+outputString+"("+requestCount.getOrDefault(socket,0).toString()+")";
                    fileWriter.append(clientNameList.get(socket)+" ( "+sdf3.format(new Timestamp(System.currentTimeMillis()))+" )"+" : "+requestCount.get(socket)+"\n");
                    fileWriter.flush();
                    System.out.println(message);
                }

                // Adding Timestamp.
                ack=ack+" ( "+sdf3.format(new Timestamp(System.currentTimeMillis()))+" )\n";


                //Sending acknowledge message to client.
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(ack);
                System.out.println("ACK sent to the client\n");

            }
        } catch (SocketException e) {
            String printMessage = clientNameList.get(socket) + " has been disconnected.\n";
            System.out.println(printMessage);
            clients.remove(socket);
            clientNameList.remove(socket);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}