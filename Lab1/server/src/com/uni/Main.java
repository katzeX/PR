package com.uni;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket client = serverSocket.accept();
        System.out.println("Connected");

        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        String line = "";

        while (!line.equals("Done")) {
            line = inputStream.readUTF();
            System.out.println(line);
        }

        System.out.println("Closing connection");

        inputStream.close();
        client.close();
    }
}
