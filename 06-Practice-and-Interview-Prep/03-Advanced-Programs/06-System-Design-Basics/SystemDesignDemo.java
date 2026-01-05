/**
 * System Design Basics Implementation
 * 
 * Demonstrates fundamental system design concepts:
 * - LRU Cache: Memory-efficient caching with eviction
 * - Rate Limiter: Request throttling for API protection
 * - URL Shortener: Distributed URL encoding/retrieval
 * - Load Balancer: Traffic distribution across servers
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class SystemDesignDemo {
    
    // ==================== LRU CACHE ====================
    /**
     * Least Recently Used Cache implementation
     * 
     * Operations: O(1) for get and put
     * Space: O(capacity)
     * 
     * Eviction Policy: Remove least recently used item when full
     * 
     * Implementation: HashMap + Doubly LinkedList
     */
    static class LRUCache {
        private int capacity;
        private Map<Integer, Node> cache;
        private Node head;
        private Node tail;
        
        static class Node {
            int key;
            int value;
            Node prev;
            Node next;
            
            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
        
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            
            // Dummy head and tail for easier operations
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }
        
        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            
            Node node = cache.get(key);
            moveToHead(node);
            return node.value;
        }
        
        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                // Update existing
                Node node = cache.get(key);
                node.value = value;
                moveToHead(node);
            } else {
                // Add new
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                addToHead(newNode);
                
                if (cache.size() > capacity) {
                    // Remove least recently used (last before tail)
                    Node lru = tail.prev;
                    removeNode(lru);
                    cache.remove(lru.key);
                }
            }
        }
        
        private void moveToHead(Node node) {
            removeNode(node);
            addToHead(node);
        }
        
        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
        
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        
        public String toString() {
            StringBuilder sb = new StringBuilder("Cache[");
            Node current = head.next;
            while (current != tail) {
                sb.append("(").append(current.key).append(":").append(current.value).append(")");
                current = current.next;
                if (current != tail) sb.append(" ");
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    // ==================== RATE LIMITER ====================
    /**
     * Token Bucket Rate Limiter
     * 
     * Algorithm:
     * - Generate tokens at fixed rate
     * - Allow request if tokens available
     * - Block or queue if no tokens
     * 
     * Use case: API rate limiting, traffic shaping
     */
    static class RateLimiter {
        private final long maxTokens;
        private final long refillRate; // tokens per second
        private long tokens;
        private long lastRefillTime;
        private final Lock lock = new ReentrantLock();
        
        public RateLimiter(long maxTokens, long refillRate) {
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            this.tokens = maxTokens;
            this.lastRefillTime = System.currentTimeMillis();
        }
        
        public boolean allowRequest(int tokensNeeded) {
            lock.lock();
            try {
                refillTokens();
                
                if (tokens >= tokensNeeded) {
                    tokens -= tokensNeeded;
                    return true;
                }
                return false;
            } finally {
                lock.unlock();
            }
        }
        
        private void refillTokens() {
            long now = System.currentTimeMillis();
            long timePassed = now - lastRefillTime;
            long tokensToAdd = (timePassed / 1000) * refillRate;
            
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
        
        public long getAvailableTokens() {
            lock.lock();
            try {
                refillTokens();
                return tokens;
            } finally {
                lock.unlock();
            }
        }
    }
    
    // ==================== URL SHORTENER ====================
    /**
     * Simple URL Shortener
     * 
     * Approach: Base62 encoding of auto-incrementing ID
     * 
     * Trade-offs:
     * - Pro: Sequential, predictable IDs
     * - Con: IDs might be guessable
     * 
     * Real systems use: Unique random codes, sharding
     */
    static class URLShortener {
        private Map<String, String> urlMap; // short -> long
        private Map<String, String> reverseMap; // long -> short
        private long counter;
        private String baseUrl = "http://short.url/";
        private static final String BASE62 = 
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        
        public URLShortener() {
            this.urlMap = new HashMap<>();
            this.reverseMap = new HashMap<>();
            this.counter = 1000;
        }
        
        public String shorten(String longUrl) {
            if (reverseMap.containsKey(longUrl)) {
                return reverseMap.get(longUrl);
            }
            
            String shortCode = encodeBase62(counter++);
            String shortUrl = baseUrl + shortCode;
            
            urlMap.put(shortCode, longUrl);
            reverseMap.put(longUrl, shortUrl);
            
            return shortUrl;
        }
        
        public String expand(String shortUrl) {
            String shortCode = shortUrl.replace(baseUrl, "");
            return urlMap.getOrDefault(shortCode, "URL not found");
        }
        
        private String encodeBase62(long num) {
            StringBuilder sb = new StringBuilder();
            while (num > 0) {
                sb.insert(0, BASE62.charAt((int)(num % 62)));
                num /= 62;
            }
            return sb.toString();
        }
        
        public int getShortenerSize() {
            return urlMap.size();
        }
    }
    
    // ==================== LOAD BALANCER ====================
    /**
     * Round-Robin Load Balancer
     * 
     * Distributes requests across multiple servers
     * 
     * Strategies:
     * - Round-Robin: Simple, fair distribution
     * - Weighted Round-Robin: Based on server capacity
     * - Least Connections: Send to server with fewest active connections
     * - IP Hash: Route based on client IP
     */
    static class Server {
        String id;
        int activeConnections;
        
        Server(String id) {
            this.id = id;
            this.activeConnections = 0;
        }
        
        void handleRequest(String request) {
            activeConnections++;
            System.out.println("Server " + id + " handling: " + request + 
                             " (active: " + activeConnections + ")");
        }
        
        void releaseConnection() {
            activeConnections--;
        }
        
        String getStatus() {
            return "Server " + id + " - Active: " + activeConnections;
        }
    }
    
    static class LoadBalancer {
        private List<Server> servers;
        private int currentIndex = 0;
        
        public LoadBalancer(String... serverIds) {
            servers = new ArrayList<>();
            for (String id : serverIds) {
                servers.add(new Server(id));
            }
        }
        
        // Round-Robin strategy
        public Server getServer() {
            Server server = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size();
            return server;
        }
        
        // Least Connections strategy
        public Server getServerLeastConnections() {
            return servers.stream()
                .min(Comparator.comparingInt(s -> s.activeConnections))
                .orElse(servers.get(0));
        }
        
        public void displayStatus() {
            System.out.println("Load Balancer Status:");
            for (Server server : servers) {
                System.out.println("  " + server.getStatus());
            }
        }
    }
    
    // ==================== DEMONSTRATIONS ====================
    
    public static void demonstrateLRUCache() {
        System.out.println("\n========== LRU CACHE ==========");
        
        LRUCache cache = new LRUCache(3);
        
        System.out.println("Capacity: 3");
        System.out.println("\nOperations:");
        
        cache.put(1, "A");
        System.out.println("put(1, A): " + cache);
        
        cache.put(2, "B");
        System.out.println("put(2, B): " + cache);
        
        cache.put(3, "C");
        System.out.println("put(3, C): " + cache);
        
        System.out.println("get(1): " + cache.get(1));
        System.out.println("After get(1): " + cache);
        
        cache.put(4, "D"); // Evicts 2 (least recently used)
        System.out.println("put(4, D): " + cache);
        System.out.println("(Key 2 evicted - was least recently used)");
        
        System.out.println("\nget(2): " + cache.get(2));
        System.out.println("(Key 2 not found - was evicted)");
        
        cache.put(5, "E"); // Evicts 3
        System.out.println("put(5, E): " + cache);
        
        System.out.println("\nFinal state: " + cache);
    }
    
    public static void demonstrateRateLimiter() {
        System.out.println("\n========== RATE LIMITER ==========");
        
        RateLimiter limiter = new RateLimiter(10, 2); // 10 tokens, 2 per second
        
        System.out.println("Initial tokens: " + limiter.getAvailableTokens());
        System.out.println("Configuration: Max 10 tokens, refill 2/sec\n");
        
        // Requests requiring tokens
        String[] requests = {
            "GET /api/users",
            "POST /api/data",
            "GET /api/status",
            "DELETE /api/item",
            "GET /api/search",
            "GET /api/profile",
            "POST /api/upload"
        };
        
        for (String request : requests) {
            if (limiter.allowRequest(3)) {
                System.out.println("✓ Allowed: " + request + 
                                 " (Remaining: " + limiter.getAvailableTokens() + ")");
            } else {
                System.out.println("✗ Blocked: " + request + " - Rate limit exceeded");
            }
        }
        
        System.out.println("\nWaiting 1 second for token refill...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Available tokens: " + limiter.getAvailableTokens());
        if (limiter.allowRequest(3)) {
            System.out.println("✓ Allowed after refill");
        }
    }
    
    public static void demonstrateURLShortener() {
        System.out.println("\n========== URL SHORTENER ==========");
        
        URLShortener shortener = new URLShortener();
        
        String[] longUrls = {
            "https://www.example.com/very/long/path/to/some/resource",
            "https://github.com/user/repository/blob/main/file.java",
            "https://stackoverflow.com/questions/12345/how-to-do-something",
            "https://www.wikipedia.org/wiki/Computer_Science"
        };
        
        System.out.println("Shortening URLs:\n");
        
        Map<String, String> shortcuts = new HashMap<>();
        for (String longUrl : longUrls) {
            String shortUrl = shortener.shorten(longUrl);
            shortcuts.put(shortUrl, longUrl);
            System.out.println("Long:  " + longUrl);
            System.out.println("Short: " + shortUrl);
            System.out.println();
        }
        
        System.out.println("Expanding shortened URLs:\n");
        
        for (String shortUrl : shortcuts.keySet()) {
            String expanded = shortener.expand(shortUrl);
            System.out.println("Short: " + shortUrl);
            System.out.println("Long:  " + expanded);
            System.out.println("Match: " + expanded.equals(shortcuts.get(shortUrl)));
            System.out.println();
        }
        
        System.out.println("Total URLs stored: " + shortener.getShortenerSize());
    }
    
    public static void demonstrateLoadBalancer() {
        System.out.println("\n========== LOAD BALANCER - ROUND ROBIN ==========");
        
        LoadBalancer lb = new LoadBalancer("Server-1", "Server-2", "Server-3");
        
        String[] requests = {
            "Request-A",
            "Request-B",
            "Request-C",
            "Request-D",
            "Request-E",
            "Request-F"
        };
        
        System.out.println("Distributing requests using Round-Robin:\n");
        
        for (String request : requests) {
            Server server = lb.getServer();
            server.handleRequest(request);
        }
        
        System.out.println("\n" + "=".repeat(40));
        System.out.println("LOAD BALANCER - LEAST CONNECTIONS");
        System.out.println("=".repeat(40) + "\n");
        
        LoadBalancer lb2 = new LoadBalancer("Server-1", "Server-2", "Server-3");
        
        System.out.println("Distributing requests using Least Connections:\n");
        
        for (String request : requests) {
            Server server = lb2.getServerLeastConnections();
            server.handleRequest(request);
        }
        
        System.out.println();
        lb2.displayStatus();
        
        System.out.println("\nReleasing connections...");
        lb2.servers.get(0).releaseConnection();
        lb2.servers.get(0).releaseConnection();
        lb2.servers.get(1).releaseConnection();
        lb2.displayStatus();
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        SYSTEM DESIGN BASICS IMPLEMENTATION             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        demonstrateLRUCache();
        demonstrateRateLimiter();
        demonstrateURLShortener();
        demonstrateLoadBalancer();
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("All demonstrations completed successfully!");
        System.out.println("=".repeat(55));
    }
}
