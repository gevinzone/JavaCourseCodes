package com.gevinzone.nio.nio01;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Common {
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
//            TimeUnit.MILLISECONDS.sleep(20);
            TimeUnit.NANOSECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
