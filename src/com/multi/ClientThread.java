package com.multi;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

    private Socket socket;
    private BufferedReader inputStream;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inputStream.readLine();
                System.out.println(message);
            }
        } catch (SocketException e) {
            System.out.println("You left the Server");
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}