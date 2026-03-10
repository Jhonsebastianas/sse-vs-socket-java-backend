package com.example.demo.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketIoRunner implements CommandLineRunner {

    private final SocketIOServer server;

    public SocketIoRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
        System.out.println("Socket.io server started on port 9092");
    }
}
