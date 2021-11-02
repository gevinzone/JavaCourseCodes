package com.gevinzone.nio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer01 {
    public static void main(String[] args) throws IOException {
        int port = 8801;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("http://localhost:" + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                String body = "hello,nio1";
                Common.service(socket, body);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
