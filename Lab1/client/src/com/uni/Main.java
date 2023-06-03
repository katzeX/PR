package com.uni;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        Socket socket = new Socket("127.0.0.1", 5000);
        DataInputStream inputStream = new DataInputStream(System.in);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        String line = "";

        while (!line.equals("Done"))
        {
            try
            {
                line = inputStream.readLine();
                outputStream.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i.getMessage());
            }
        }
        System.out.println("Closing connection");

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
