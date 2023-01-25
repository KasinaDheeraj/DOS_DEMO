package com.demo;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws Exception {
        HashMap<String ,Long > clients;
        int i=0;
        try{
            ServerSocket server=new ServerSocket(8888);
            Socket serverClient=server.accept();
            System.out.println(i++);
            DataInputStream inStream=new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream=new DataOutputStream(serverClient.getOutputStream());
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            String clientMessage="", serverMessage="";
            while(!clientMessage.equals("bye")){
                clientMessage=inStream.readUTF();
                System.out.println("From Client: "+serverClient.getInetAddress()+clientMessage);
                serverMessage=reader.readLine();
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            serverClient.close();
            server.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}