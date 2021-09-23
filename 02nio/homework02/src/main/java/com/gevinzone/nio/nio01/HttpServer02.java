package com.gevinzone.nio.nio01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer02 {
    public static void main(String[] args) throws IOException {
        int port = 8802;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("http://localhost:" + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                String body = "hello,nio2";
                new Thread(() -> Common.service(socket, body)).start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
