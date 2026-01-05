# Networking in Java

## Introduction

Networking enables Java applications to communicate over networks, from simple client-server interactions to complex distributed systems. Java abstracts platform-specific networking details, enabling portable network applications that work across operating systems. Socket programming forms the foundation of network communication, enabling both connection-oriented (TCP) and connectionless (UDP) protocols.

Understanding networking concepts is crucial for building modern applications: web services, messaging systems, real-time communication, and distributed computing all depend on solid networking fundamentals. Java provides multiple levels of abstraction—from low-level sockets to high-level HTTP clients and frameworks—allowing developers to choose the appropriate level for their use case.

## Network Communication Basics

### OSI Model and Java Networking

Java typically works at the transport layer (Layer 4) and application layer (Layer 7) of the OSI model. Sockets provide access to the transport layer, while higher-level APIs abstract these details.

### Protocols

**TCP (Transmission Control Protocol)**
- Connection-oriented, reliable protocol
- Guarantees delivery in order
- Used for email, web browsers, file transfer
- Slower but more reliable

**UDP (User Datagram Protocol)**
- Connectionless, unreliable protocol
- No delivery guarantees
- Faster with lower overhead
- Used for streaming, online games, DNS

## Socket Programming

A socket is an endpoint of a network connection. Client sockets initiate connections; server sockets listen for incoming connections. Java provides ServerSocket for servers and Socket for clients.

### Server-Side Sockets

ServerSocket listens on a specific port for incoming connections. When a connection arrives, ServerSocket.accept() returns a Socket object representing the connection.

### Client-Side Sockets

Client Socket objects connect to servers by specifying the server address and port.

### Streams from Sockets

Once a socket is established, you obtain input and output streams for bidirectional communication.

## URL and HTTP Handling

The java.net.URL class provides high-level abstractions for web resources. Java 11+ introduced HttpClient for modern HTTP communication with support for HTTP/2 and async operations.

## Key Networking Classes

**Socket**: Client-side socket for TCP communication
**ServerSocket**: Server-side socket accepting incoming connections
**DatagramSocket**: UDP communication endpoint
**DatagramPacket**: Container for UDP data
**URL**: Represents a Uniform Resource Locator
**HttpClient**: Modern HTTP communication (Java 11+)
**InetAddress**: Represents IP address with DNS resolution

## Important Concepts

**Port Numbers**: Numbers 1-65535; ports 1-1024 are reserved
**Localhost**: 127.0.0.1 or ::1 for IPv6; addresses the current machine
**Threading**: Each client typically needs a separate thread for concurrent connections
**Timeouts**: Important to prevent indefinite blocking
**Exception Handling**: Network operations fail; robust error handling is essential

## Best Practices

1. **Use timeouts** - Prevent indefinite blocking with setSoTimeout()
2. **Close resources properly** - Use try-with-resources for sockets
3. **One thread per client** - Use thread pools to manage threads efficiently
4. **Handle exceptions gracefully** - Network operations are inherently unreliable
5. **Use HTTP clients instead of raw sockets** - For web services, prefer HttpClient
6. **Implement heartbeats** - Detect disconnected clients in long-lived connections
7. **Buffer appropriately** - Use adequate buffer sizes for your data
8. **Consider security** - Use SSL/TLS for sensitive data transmission

---

## Complete Working Examples

### Example 1: Basic TCP Server and Client

```java
import java.io.*;
import java.net.*;

public class SimpleServer {
    
    public static void main(String[] args) {
        int port = 5000;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                
                // Handle client in separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    static class ClientHandler implements Runnable {
        private Socket socket;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                
                String message;
                System.out.println("Handler started for " + socket.getInetAddress());
                
                while ((message = input.readLine()) != null) {
                    System.out.println("Received: " + message);
                    String response = "Echo: " + message;
                    output.println(response);
                }
                
            } catch (IOException e) {
                System.err.println("Client handling error: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Socket close error: " + e.getMessage());
                }
            }
        }
    }
}

public class SimpleClient {
    
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5000;
        
        try (Socket socket = new Socket(host, port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            
            System.out.println("Connected to " + host + ":" + port);
            System.out.println("Type messages (type 'quit' to exit):");
            
            String userMessage;
            while ((userMessage = userInput.readLine()) != null) {
                if ("quit".equalsIgnoreCase(userMessage)) {
                    break;
                }
                
                output.println(userMessage);
                String response = input.readLine();
                System.out.println("Server response: " + response);
            }
            
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
```

**Output (Server):**
```
Server listening on port 5000
Client connected from /127.0.0.1
Handler started for /127.0.0.1
Received: Hello Server
Received: This is a test
Received: quit
```

**Output (Client):**
```
Connected to localhost:5000
Type messages (type 'quit' to exit):
Hello Server
Server response: Echo: Hello Server
This is a test
Server response: Echo: This is a test
quit
```

### Example 2: Multithreaded Chat Server

```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    
    private static final int PORT = 5001;
    private static final Set<ClientHandler> clients = new HashSet<>();
    
    public static void main(String[] args) {
        System.out.println("Chat Server starting on port " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                
                ClientHandler handler = new ClientHandler(clientSocket);
                synchronized (clients) {
                    clients.add(handler);
                }
                new Thread(handler).start();
                
                System.out.println("Active clients: " + clients.size());
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    public static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }
    
    public static void removeClient(ClientHandler handler) {
        synchronized (clients) {
            clients.remove(handler);
        }
    }
    
    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter output;
        private String username;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {
                
                output = new PrintWriter(socket.getOutputStream(), true);
                
                // Get username
                username = input.readLine();
                System.out.println(username + " joined the chat");
                broadcast(username + " joined the chat", this);
                
                // Handle messages
                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println(username + ": " + message);
                    broadcast(username + ": " + message, this);
                }
                
            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                System.out.println(username + " left the chat");
                broadcast(username + " left the chat", this);
                ChatServer.removeClient(this);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public void sendMessage(String message) {
            output.println(message);
        }
    }
}

public class ChatClient {
    
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5001;
        
        try (Socket socket = new Socket(host, port)) {
            
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.print("Enter your username: ");
            String username = userInput.readLine();
            output.println(username);
            
            System.out.println("Connected to chat server. Type messages:");
            
            // Thread to read messages from server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = input.readLine()) != null) {
                        System.out.println("\n" + message);
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.err.println("Disconnected from server");
                }
            }).start();
            
            // Send messages
            String message;
            System.out.print("> ");
            while ((message = userInput.readLine()) != null) {
                output.println(message);
                System.out.print("> ");
            }
            
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
```

**Output (Chat interaction):**
```
[Server] alice joined the chat
[Client alice receives] alice joined the chat
[Server] bob joined the chat
[Client bob receives] alice joined the chat, bob joined the chat
[Server received from alice] alice: Hello everyone
[Server received from bob] bob: Hi alice!
```

### Example 3: UDP Client and Server

```java
import java.net.*;
import java.io.*;

public class UDPServer {
    
    public static void main(String[] args) throws IOException {
        int port = 5002;
        DatagramSocket socket = new DatagramSocket(port);
        
        System.out.println("UDP Server listening on port " + port);
        
        byte[] buffer = new byte[1024];
        
        for (int i = 0; i < 5; i++) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received from " + packet.getAddress() + ": " + message);
            
            // Send response
            byte[] response = ("Echo: " + message).getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                response, response.length, packet.getAddress(), packet.getPort());
            socket.send(responsePacket);
        }
        
        socket.close();
        System.out.println("Server closed");
    }
}

public class UDPClient {
    
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5002;
        
        DatagramSocket socket = new DatagramSocket();
        
        String[] messages = {
            "First UDP message",
            "Second UDP message",
            "Third UDP message"
        };
        
        for (String msg : messages) {
            byte[] buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(
                buffer, buffer.length, InetAddress.getByName(host), port);
            
            socket.send(packet);
            System.out.println("Sent: " + msg);
            
            // Receive response
            byte[] responseBuffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(
                responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);
            
            String response = new String(
                responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Received: " + response);
        }
        
        socket.close();
    }
}
```

**Output:**
```
Server Received from 127.0.0.1:
First UDP message
Second UDP message
Third UDP message

Client Sent: First UDP message
Received: Echo: First UDP message
Sent: Second UDP message
Received: Echo: Second UDP message
Sent: Third UDP message
Received: Echo: Third UDP message
```

### Example 4: HTTP Client with URL

```java
import java.net.*;
import java.io.*;

public class HTTPClient {
    
    public static void fetchWebPage(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        
        System.out.println("Fetching: " + urlString);
        System.out.println("Content-Type: " + connection.getContentType());
        System.out.println("Content-Length: " + connection.getContentLength());
        System.out.println("\nContent preview (first 500 chars):");
        System.out.println("-".repeat(50));
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            
            String line;
            int charCount = 0;
            while ((line = reader.readLine()) != null && charCount < 500) {
                System.out.println(line);
                charCount += line.length();
            }
        }
    }
    
    public static void main(String[] args) {
        // Example with a simple public API
        try {
            String jsonUrl = "https://api.github.com/users/github";
            URLConnection connection = new URL(jsonUrl).openConnection();
            connection.setRequestProperty("User-Agent", "Java-Client");
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                
                System.out.println("GitHub API Response:");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Note: This example requires internet connection");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### Example 5: HttpClient (Java 11+)

```java
import java.net.http.*;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class ModernHttpClient {
    
    public static void synchronousRequest() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/users/github"))
            .GET()
            .header("User-Agent", "Java-HttpClient")
            .build();
        
        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());
        
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body:");
        System.out.println(response.body().substring(0, Math.min(500, response.body().length())));
    }
    
    public static void asynchronousRequest() throws Exception {
        HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com"))
            .GET()
            .header("User-Agent", "Java-HttpClient")
            .timeout(java.time.Duration.ofSeconds(10))
            .build();
        
        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(
            request,
            HttpResponse.BodyHandlers.ofString()
        );
        
        responseFuture.thenAccept(response -> {
            System.out.println("Async Status: " + response.statusCode());
            System.out.println("Async Response received");
        }).exceptionally(e -> {
            System.err.println("Request failed: " + e.getMessage());
            return null;
        }).join();
    }
    
    public static void main(String[] args) {
        System.out.println("Modern HTTP Client Examples (Java 11+)");
        System.out.println("Note: These require internet connection\n");
        
        try {
            System.out.println("=== Synchronous Request ===");
            synchronousRequest();
            
            System.out.println("\n=== Asynchronous Request ===");
            asynchronousRequest();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

## Performance Analysis

- **TCP vs UDP**: TCP adds overhead for reliability; UDP is 2-3x faster
- **Thread per client**: Scales to ~1000 clients per server; use thread pools
- **NIO networking**: Can handle 10,000+ concurrent connections per thread
- **Socket buffer size**: Larger buffers (64KB) improve throughput for large data

## Summary

Java networking provides comprehensive capabilities for building connected applications. Traditional socket programming gives low-level control, while modern APIs like HttpClient abstract common patterns. Understanding both synchronous and asynchronous approaches, choosing between TCP and UDP, and implementing proper error handling and resource management are essential for building reliable networked applications.
