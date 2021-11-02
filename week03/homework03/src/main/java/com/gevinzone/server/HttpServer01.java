package com.gevinzone.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class HttpServer01 {
    public static void main(String[] args) throws IOException {
        int port = 8801;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("http://localhost:" + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                String body = "hello,nio1";
                service(socket, body);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void service(Socket socket, String body) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
//            sleep();
            printWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
