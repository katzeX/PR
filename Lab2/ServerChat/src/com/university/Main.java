package com.university;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Main {

    // List to store active clients
    static List<ClientHandler> clientHandlers = new ArrayList<>();

    // counter for clients
    static int i = 0;

    public static void main(String[] args) throws IOException {
        // write your code here

        ServerSocket serverSocket = new ServerSocket(5000);
        Socket client;

        while (true) {
            // Accept the incoming request
            client = serverSocket.accept();

            System.out.println("Client connected" + client);

            // obtain input and output streams
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(client, "client" + i, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            clientHandlers.add(mtch);

            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;


        }
    }
}

class ClientHandler implements Runnable {

    private final String name;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private boolean isLoggedIn;

    // constructor
    public ClientHandler(Socket s, String name,
            DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {

        String received;
        while (true) {
            try {
                // receive the string
                received = dis.readUTF();

                System.out.println(received);

                if (received.equals("logout")) {
                    this.isLoggedIn = false;
                    this.s.close();
                    break;
                }

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String msgToSend = st.nextToken();
                String recipient = st.nextToken();

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler mc : Main.clientHandlers) {
                    // if the recipient is found, write on its
                    // output stream
                    if (mc.name.equals(recipient) && mc.isLoggedIn) {
                        mc.dos.writeUTF(this.name + " : " + msgToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
