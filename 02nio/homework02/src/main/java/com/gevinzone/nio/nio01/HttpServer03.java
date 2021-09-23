package com.gevinzone.nio.nio01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer03 {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
        int port = 8803;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("http://localhost:" + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                String body = "hello,nio3";
                service.execute(() -> Common.service(socket, body));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
