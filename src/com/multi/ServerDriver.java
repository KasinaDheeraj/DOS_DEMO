package com.multi;

import java.io.FileWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ServerDriver {

    public static final String MAX_PACKET_SIZE;

    static{
        char[] chars = new char[32*1024];
        // Optional step - unnecessary if you're happy with the array being full of \0
        Arrays.fill(chars,'I');
        MAX_PACKET_SIZE = new String(chars);
    }

    public static void main(String[] args) {



        ArrayList<Socket> threadList = new ArrayList<>();
        HashMap<Socket, String> clientNameList = new HashMap<Socket, String>();
        HashMap<Socket, Integer> requestCount=new HashMap<>();

        try (ServerSocket serversocket = new ServerSocket(5000)) {
            FileWriter myWriter = new FileWriter("C:\\Users\\Dheeru\\Desktop\\8th Sem\\Final year project\\ServerLog.txt",false);
            myWriter.write("Server Started:\n");
            myWriter.flush();

            System.out.println("Server Started:");
            while (true) {
                Socket socket = serversocket.accept();
                threadList.add(socket);
                ServerThread ThreadServer = new ServerThread(socket, threadList, clientNameList,requestCount,myWriter);
                ThreadServer.start();
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}


/* Error:
    "C:\Program Files\Java\jdk-14.0.1\bin\java.exe" -Dfile.encoding=UTF-8 -classpath "C:\Users\Dheeru\IdeaProjects\DOS_DEMO\out\production\DOS_DEMO;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.1\plugins\Kotlin\kotlinc\lib\kotlin-stdlib.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.1\plugins\Kotlin\kotlinc\lib\kotlin-reflect.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.1\plugins\Kotlin\kotlinc\lib\kotlin-test.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.1\plugins\Kotlin\kotlinc\lib\kotlin-stdlib-jdk7.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.1\plugins\Kotlin\kotlinc\lib\kotlin-stdlib-jdk8.jar" com.multi.ClientDriver
java.net.SocketException: No buffer space available (maximum connections reached?): connect
	at java.base/sun.nio.ch.Net.connect0(Native Method)
	at java.base/sun.nio.ch.Net.connect(Net.java:503)
	at java.base/sun.nio.ch.Net.connect(Net.java:492)
	at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:588)
	at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:333)
	at java.base/java.net.Socket.connect(Socket.java:648)
	at java.base/java.net.Socket.connect(Socket.java:597)
	at java.base/java.net.Socket.<init>(Socket.java:520)
	at java.base/java.net.Socket.<init>(Socket.java:294)
	at com.multi.ClientDriver.main(ClientDriver.java:19)

Process finished with exit code 0

 */