package com.example.demo.service;

import com.example.demo.model.Notification;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final SocketIOServer socketIOServer;

    public NotificationService(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    // SSE Logic
    public SseEmitter createSseEmitter(String userId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30 min
        sseEmitters.put(userId, emitter);

        emitter.onCompletion(() -> sseEmitters.remove(userId));
        emitter.onTimeout(() -> sseEmitters.remove(userId));
        emitter.onError((e) -> sseEmitters.remove(userId));

        return emitter;
    }

    public void sendSseNotification(String userId, String message) {
        SseEmitter emitter = sseEmitters.get(userId);
        if (emitter != null) {
            try {
                Notification notification = new Notification(
                    UUID.randomUUID().toString(),
                    message,
                    "SSE",
                    System.currentTimeMillis()
                );
                emitter.send(SseEmitter.event()
                    .name("notification")
                    .data(notification)
                    .id(notification.getId()));
            } catch (IOException e) {
                sseEmitters.remove(userId);
            }
        }
    }

    // Socket.io Logic
    public void sendSocketNotification(String message) {
        Notification notification = new Notification(
            UUID.randomUUID().toString(),
            message,
            "SOCKET",
            System.currentTimeMillis()
        );
        socketIOServer.getBroadcastOperations().sendEvent("notification", notification);
    }

    public void broadcastToAll(String message) {
        // Send to all SSE users
        sseEmitters.keySet().forEach(userId -> sendSseNotification(userId, message));
        // Send to all Socket.io users
        sendSocketNotification(message);
    }
}
