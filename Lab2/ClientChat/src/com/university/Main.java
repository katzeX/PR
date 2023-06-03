package com.university;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);

        // establish the connection
        DataInputStream dis;
        DataOutputStream dos;
        Socket s = new Socket("127.0.0.1", 5000);

        // obtaining input and out streams
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

        // sendMessage thread
        Thread sendMessage = new Thread(() -> {
            while (true) {

                // read the message to deliver.
                String msg = scn.nextLine();

                try {
                    // write on the output stream
                    dos.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(() -> {

            while (true) {
                try {
                    // read the message sent to this client
                    String msg = dis.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }
}
