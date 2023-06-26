package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port 8000");
    }
}