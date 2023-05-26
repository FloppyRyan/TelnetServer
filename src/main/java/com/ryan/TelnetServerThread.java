package com.ryan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TelnetServerThread implements Runnable {

    private final Socket clientSocket;

    public TelnetServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            writer.println("Welcome to the Telnet server!");
            writer.print("> ");
            writer.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.equals("exit")) {
                    break;
                }
                writer.println("You entered: " + line);
                // send > to client
                writer.print("> ");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
