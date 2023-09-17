package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.request.HttpRequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8084), 0);
        server.createContext("/", new HttpRequestHandler());
        server.start();
    }
}