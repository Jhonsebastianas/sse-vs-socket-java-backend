package com.example.demo.controller;

import com.example.demo.service.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/sse/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam String userId) {
        return notificationService.createSseEmitter(userId);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        if (message == null) {
            message = "Default message";
        }
        notificationService.broadcastToAll(message);
    }
}
