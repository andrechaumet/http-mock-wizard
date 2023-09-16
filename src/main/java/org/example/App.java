package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8083), 0);
        server.createContext("/", new MocksHandler());

        server.start();
    }

    static class MocksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                final String httpMethod = exchange.getRequestMethod();
                final String uri = exchange.getRequestURI().toString();
                final String body = extractBody(exchange.getRequestBody());
                System.out.println(httpMethod + " " + uri);
                System.out.println(body);
                exchange.getRequestHeaders().values().forEach(System.out::println);
                final String responseBody = activeMocksHandler(uri);
                exchange.sendResponseHeaders(200, responseBody.getBytes().length);
                exchange.getResponseBody().write(responseBody.getBytes());
                exchange.close();
                System.out.println("------RESPONSE SENT------");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("------ERROR------");
            }
        }
    }

    private static String activeMocksHandler(final String uri) throws IOException {
        final String basePath = "C:/Users/Andy/Desktop/test/active-mocks";
/*        final File file = new File(basePath + uri);
        if(file.isDirectory()) {*/
        try {

            BufferedReader reader = Files.newBufferedReader(Paths.get(basePath + uri +"/200.txt/"));
            return reader.lines().collect(Collectors.joining());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        /*        }
         */
        /*        return null;*/
    }

    private static String extractBody(InputStream body) throws IOException {
        StringBuilder requestBodyBuilder = new StringBuilder();
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = body.read(buffer)) != -1) {
            requestBodyBuilder.append(new String(buffer, 0, bytesRead));
        }
        return requestBodyBuilder.toString();
    }
}