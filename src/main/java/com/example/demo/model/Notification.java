package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String id;
    private String message;
    private String type; // e.g., "SSE" or "SOCKET"
    private long timestamp;
}
