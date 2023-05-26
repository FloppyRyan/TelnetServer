package com.ryan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class TelnetServer {

    private static final int port = 1050;

    public static void main(String[] args) {
        SpringApplication.run(TelnetServer.class, args);
        startTelnetServer();
    }

    private static void startTelnetServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            String out = String.format("Telnet started on port %d", port);
            System.out.println(out);

            ExecutorService executorService = Executors.newCachedThreadPool();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new TelnetServerThread(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
