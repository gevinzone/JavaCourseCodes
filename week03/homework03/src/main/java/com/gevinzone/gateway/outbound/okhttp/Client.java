package com.gevinzone.gateway.outbound.okhttp;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Client {
    public static void main(String... args) {
        Map<String, String> serviceMap = new HashMap<>();
        serviceMap.put("1", "http://127.0.0.1:8801");
        serviceMap.put("2", "http://127.0.0.1:8802");
        serviceMap.put("3", "http://127.0.0.1:8803");
        serviceMap.put("8", "http://127.0.0.1:8808");

        String id = (null == args || args.length == 0) ? "1" : args[0];
        String serviceUrl = serviceMap.get(id);
        OkHttpClient client = new OkHttpClient();
        Request request = makeRequest(serviceUrl);
        for (int i = 0; i < 20; i++) {
            runRequest(client, request);
        }
    }

    private static Request makeRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    private static void runRequest(OkHttpClient client, Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                printFailMessage(response);
            }
            printResponse(response);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void printFailMessage(Response response) {
        System.out.println("Unexpected code: " + response.code());
        System.out.println("Message: " + response.message());
    }
    private static void printResponse(Response response) throws IOException {
        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }
        System.out.println(Objects.requireNonNull(response.body()).string());
    }
}

