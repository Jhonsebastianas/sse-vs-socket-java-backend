# SSE vs Socket.io Demo - Backend (Spring Boot)

This project is a Proof of Concept (PoC) designed to compare two real-time communication technologies: **Server-Sent Events (SSE)** and **Socket.io (WebSockets)**.

## 🚀 Features
- **SSE Implementation**: Uses Spring's `SseEmitter` for unidirectional server-to-client communication.
- **Socket.io Implementation**: Integrated using `netty-socketio` for bidirectional communication.
- **Unified Notification Service**: A single service that broadcasts messages to both SSE and Socket.io clients simultaneously.
- **Trigger API**: A REST endpoint to simulate external events (e.g., from another system or tab).

## 🛠️ Tech Stack
- **Java 21**
- **Spring Boot 3.2.3**
- **Maven**
- **Netty-Socket.io**
- **Lombok**

## 📋 Prerequisites
- JDK 21 or higher
- Maven 3.9+

## ⚙️ Installation & Running
1. Clone the repository.
2. Navigate to the `backend` directory.
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The server will start on:
- **HTTP API (REST & SSE)**: `http://localhost:8080`
- **Socket.io Server**: `ws://localhost:9092`

## 📡 API Endpoints
- `GET /api/notifications/sse/subscribe?userId={id}`: Subscribe to SSE stream.
- `POST /api/notifications/send`: Broadcast a message to all connected clients.
  - Body: `{"message": "Your message here"}`
