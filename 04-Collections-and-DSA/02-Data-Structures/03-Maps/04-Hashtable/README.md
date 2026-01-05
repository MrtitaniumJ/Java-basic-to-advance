# Hashtable: Complete Guide

## Real-World Analogy
Imagine an old-style library catalog system from before computers became popular. It's a heavy, sturdy wooden cabinet with many locked drawers (synchronized). Unlike modern hash tables (HashMap), every drawer is protected by a lock—you can't access multiple drawers simultaneously. This ensures thread safety but sacrifices performance. Hashtable is this legacy system—it works reliably but is outdated and slower than modern alternatives.

## Why Hashtable is Legacy

Hashtable is one of the original Java Collections (since JDK 1.0) and was later retrofitted to implement the Map interface. **It should rarely be used in new code**—use `ConcurrentHashMap` or `Collections.synchronizedMap()` instead.

### Key Facts
- **Thread-safe**: Fully synchronized (all methods are synchronized)
- **Legacy**: Part of original Collections, predates the Collections Framework
- **Performance**: Synchronized methods lock entire table; slower than modern alternatives
- **Null Policy**: Does NOT allow null keys or null values (throws NullPointerException)
- **Synergy**: Works well with Vector (another legacy synchronized collection)

## Internal Structure & Mechanism

Hashtable uses **hash table** with **separate chaining** (like HashMap):
- **Array of Buckets**: Fixed-size array of linked lists
- **Synchronized Access**: Every public method is synchronized
- **Initial Capacity**: Default 11 (not 16 like HashMap)
- **Load Factor**: Default 0.75 (same as HashMap)
- **Collision Resolution**: Separate chaining with linked lists (no red-black trees)

### Synchronization Overhead
```java
// Every method acquisition tries to acquire lock
public synchronized V put(K key, V value) {
    // ... implementation
}

// This means:
// - Multiple threads must wait for each other
// - Better to synchronize at application level if possible
// - Or use ConcurrentHashMap (segment-based locking)
```

## Key Storage Mechanism
- **Keys**: Cannot be null; must provide proper `hashCode()` and `equals()`
- **Values**: Cannot be null (different from HashMap)
- **Hashing**: Uses `Math.abs(key.hashCode()) % capacity`
- **Equality**: Both `hashCode()` and `equals()` required
- **Hash Collisions**: Handled with linked lists (buckets)

## Constructors & Creation Methods

```java
// Default constructor (capacity=11, loadFactor=0.75)
Hashtable<String, Integer> ht1 = new Hashtable<>();

// With initial capacity
Hashtable<String, Integer> ht2 = new Hashtable<>(25);

// With capacity and load factor
Hashtable<String, Integer> ht3 = new Hashtable<>(50, 0.8f);

// Copy constructor (copies all entries)
Hashtable<String, Integer> ht4 = new Hashtable<>(ht1);

// From another Map
Map<String, Integer> source = new HashMap<>();
source.put("a", 1);
Hashtable<String, Integer> ht5 = new Hashtable<>(source);

// Note: No direct way to create Hashtable with initial Map like TreeMap
// Must use copy constructor with existing Hashtable or manually add
```

## Core Operations with Performance

| Operation | Time Complexity | Notes |
|-----------|-----------------|-------|
| `put(K, V)` | O(1) average | Synchronized; O(n) worst case (all collisions) |
| `get(K)` | O(1) average | Synchronized; O(n) worst case |
| `remove(K)` | O(1) average | Synchronized; O(n) worst case |
| `containsKey(K)` | O(1) average | Synchronized |
| `containsValue(V)` | O(n) | Linear scan, fully synchronized |
| `clear()` | O(n) | Clears all, synchronized |
| `size()` | O(1) | Synchronized (counts elements) |
| `isEmpty()` | O(1) | Synchronized |
| `elements()` | O(1) | Creates Enumeration, synchronized at creation |

## No Null Support

```java
Hashtable<String, Integer> ht = new Hashtable<>();
ht.put("a", 1);

// ❌ NullPointerException - null key
ht.put(null, 2);

// ❌ NullPointerException - null value
ht.put("b", null);

// This is KEY DIFFERENCE from HashMap
// HashMap allows: 1 null key, unlimited null values
// Hashtable allows: NO null keys or values
```

## Iteration Methods

```java
Hashtable<String, Integer> ht = new Hashtable<>();
ht.put("apple", 1);
ht.put("banana", 2);
ht.put("cherry", 3);

// 1. Enumeration (legacy, synchronized)
System.out.println("Using Enumeration:");
Enumeration<String> keys = ht.keys();
while (keys.hasMoreElements()) {
    String key = keys.nextElement();
    System.out.println(key + ": " + ht.get(key));
}

// 2. entrySet() (Iterator-based, modern)
System.out.println("Using entrySet():");
for (Map.Entry<String, Integer> entry : ht.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 3. keySet()
System.out.println("Using keySet():");
for (String key : ht.keySet()) {
    System.out.println(key);
}

// 4. values()
System.out.println("Using values():");
for (Integer value : ht.values()) {
    System.out.println(value);
}

// 5. Enumeration for values
Enumeration<Integer> values = ht.elements();
while (values.hasMoreElements()) {
    System.out.println(values.nextElement());
}

// 6. forEach (Java 8+)
ht.forEach((k, v) -> System.out.println(k + ": " + v));

// 7. Iterator (safe removal)
Iterator<Map.Entry<String, Integer>> it = ht.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, Integer> entry = it.next();
    if (entry.getValue() < 2) {
        it.remove();
    }
}
```

## When to Use Hashtable
✅ **Use when:**
- Working with legacy code that already uses Hashtable
- Need a drop-in replacement for Vector's properties
- Integrating with old API that returns/expects Hashtable
- Learning Java history and evolution

❌ **Use instead:**
- **ConcurrentHashMap**: Better performance for concurrent access (segment-based locking)
- **Collections.synchronizedMap()**: If you need to wrap an existing HashMap
- **HashMap**: If single-threaded (no synchronization needed)
- **LinkedHashMap**: If you need insertion order
- **TreeMap**: If you need sorted order

## Common Use Cases with Examples

### 1. Legacy Code Integration
```java
// Old library that expects Hashtable
class LegacyUserDatabase {
    private Hashtable<String, String> users = new Hashtable<>();
    
    public void addUser(String userId, String userData) {
        users.put(userId, userData);
    }
    
    public String getUser(String userId) {
        return users.get(userId);
    }
    
    public void removeUser(String userId) {
        users.remove(userId);
    }
}

// Usage - must work with Hashtable
LegacyUserDatabase db = new LegacyUserDatabase();
db.addUser("user1", "Alice");
db.addUser("user2", "Bob");
System.out.println(db.getUser("user1")); // "Alice"
db.removeUser("user2");
```

### 2. Properties-like Configuration
```java
// Similar to Java Properties (which extends Hashtable)
class ConfigManager {
    private Hashtable<String, String> config = new Hashtable<>();
    
    public void setProperty(String key, String value) {
        config.put(key, value);
    }
    
    public String getProperty(String key) {
        return config.get(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return config.getOrDefault(key, defaultValue);
    }
    
    public void printAllConfig() {
        Enumeration<String> keys = config.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " = " + config.get(key));
        }
    }
}

ConfigManager config = new ConfigManager();
config.setProperty("app.name", "MyApp");
config.setProperty("app.version", "1.0");
config.setProperty("db.host", "localhost");
config.printAllConfig();
```

### 3. Thread-Safe Registry (Outdated Approach)
```java
// NOT recommended - use ConcurrentHashMap instead
class ServiceRegistry {
    private Hashtable<String, Object> services = new Hashtable<>();
    
    public void registerService(String serviceName, Object service) {
        services.put(serviceName, service);
    }
    
    public Object getService(String serviceName) {
        return services.get(serviceName);
    }
    
    public void deregisterService(String serviceName) {
        services.remove(serviceName);
    }
    
    public int getServiceCount() {
        return services.size();
    }
}

ServiceRegistry registry = new ServiceRegistry();
registry.registerService("logger", new Object());
registry.registerService("database", new Object());
System.out.println("Registered services: " + registry.getServiceCount());
```

### 4. Simple Session Store
```java
class SessionStore {
    private Hashtable<String, SessionData> sessions = new Hashtable<>();
    
    static class SessionData {
        String userId;
        long createdTime;
        long lastAccessTime;
        
        SessionData(String userId) {
            this.userId = userId;
            this.createdTime = System.currentTimeMillis();
            this.lastAccessTime = this.createdTime;
        }
    }
    
    public String createSession(String userId) {
        String sessionId = java.util.UUID.randomUUID().toString();
        sessions.put(sessionId, new SessionData(userId));
        return sessionId;
    }
    
    public String getSessionUser(String sessionId) {
        SessionData data = sessions.get(sessionId);
        if (data != null) {
            data.lastAccessTime = System.currentTimeMillis();
            return data.userId;
        }
        return null;
    }
    
    public void expireOldSessions(long maxAgeMs) {
        long now = System.currentTimeMillis();
        for (Enumeration<String> keys = sessions.keys(); keys.hasMoreElements();) {
            String sessionId = keys.nextElement();
            SessionData data = sessions.get(sessionId);
            if (now - data.lastAccessTime > maxAgeMs) {
                sessions.remove(sessionId);
            }
        }
    }
}

SessionStore store = new SessionStore();
String sessionId = store.createSession("user123");
System.out.println("Session user: " + store.getSessionUser(sessionId));
```

### 5. Multi-threaded Counter (Pedagogical)
```java
// DEMONSTRATION ONLY - don't do this in production
class ThreadSafeCounter {
    private Hashtable<String, Integer> counters = new Hashtable<>();
    
    public void increment(String key) {
        Integer current = counters.getOrDefault(key, 0);
        counters.put(key, current + 1);
    }
    
    public int getCount(String key) {
        return counters.getOrDefault(key, 0);
    }
    
    public void printAllCounts() {
        counters.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}

// Multi-threaded usage
class CounterThread extends Thread {
    ThreadSafeCounter counter;
    String key;
    
    CounterThread(ThreadSafeCounter counter, String key) {
        this.counter = counter;
        this.key = key;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment(key);
        }
    }
}

ThreadSafeCounter counter = new ThreadSafeCounter();
Thread t1 = new CounterThread(counter, "counter1");
Thread t2 = new CounterThread(counter, "counter1");
t1.start();
t2.start();
try {
    t1.join();
    t2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("Final count: " + counter.getCount("counter1"));
```

### 6. Enumeration-Based Iteration
```java
class EnumerationDemo {
    public static void main(String[] args) {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("Java", "Language");
        ht.put("Python", "Language");
        ht.put("MySQL", "Database");
        ht.put("PostgreSQL", "Database");
        
        // Using Enumeration (legacy but still works)
        System.out.println("Keys using Enumeration:");
        Enumeration<String> keys = ht.keys();
        while (keys.hasMoreElements()) {
            System.out.println("  " + keys.nextElement());
        }
        
        System.out.println("\nValues using Enumeration:");
        Enumeration<String> values = ht.elements();
        while (values.hasMoreElements()) {
            System.out.println("  " + values.nextElement());
        }
        
        // Modern Iterator (preferred)
        System.out.println("\nKeys using Iterator:");
        Iterator<String> it = ht.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("  " + it.next());
        }
    }
}
```

## Synchronization Details

```java
Hashtable<String, Integer> ht = new Hashtable<>();

// Every public method is synchronized
// Thread 1: ht.put("key1", 1); // Locks Hashtable
// Thread 2: ht.get("key1");    // Waits for lock
// Thread 3: ht.size();         // Waits for lock

// Problem: Method-level synchronization prevents concurrent reads
// ConcurrentHashMap solves this with segment-based locking

// If you need to synchronize compound operations:
synchronized(ht) {
    if (!ht.containsKey("key1")) {
        ht.put("key1", 1);
    }
}

// Or use ConcurrentHashMap's atomic operations:
ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
chm.putIfAbsent("key1", 1); // Atomic
```

## Comparison with Other Map Implementations

| Feature | Hashtable | HashMap | ConcurrentHashMap | Collections.synchronizedMap |
|---------|-----------|---------|-------------------|---------------------------|
| **Null keys** | ❌ No | ✅ Yes | ❌ No | ✅ Yes (if wrapped map allows) |
| **Null values** | ❌ No | ✅ Yes | ❌ No | ✅ Yes (if wrapped map allows) |
| **Thread-safe** | ✅ Yes | ❌ No | ✅ Yes | ✅ Yes |
| **Sync Type** | Method-level | None | Segment-level | Method-level |
| **Concurrency** | ❌ Low | N/A | ✅ High | ❌ Low |
| **Performance** | 🐢 Slow | 🐇 Fast | 🚗 Good | 🐢 Slow |
| **Legacy** | ✅ Yes | ❌ No | ❌ No | ❌ No |
| **Best use** | Legacy code | Single-threaded | Concurrent access | Wrapping custom maps |

## Edge Cases & Gotchas

### 1. Null Key/Value
```java
Hashtable<String, Integer> ht = new Hashtable<>();
ht.put("a", 1);

// ❌ NullPointerException
try {
    ht.put(null, 2);
} catch (NullPointerException e) {
    System.out.println("Null key rejected: " + e);
}

// ❌ NullPointerException
try {
    ht.put("b", null);
} catch (NullPointerException e) {
    System.out.println("Null value rejected: " + e);
}
```

### 2. Hashtable vs HashMap Default Capacity
```java
// Hashtable uses 11 (prime number)
Hashtable<String, Integer> ht = new Hashtable<>();
// Initial capacity: 11

// HashMap uses 16 (power of 2)
HashMap<String, Integer> hm = new HashMap<>();
// Initial capacity: 16

// This is a minor implementation detail but shows age of Hashtable
```

### 3. Enumeration vs Iterator
```java
Hashtable<String, String> ht = new Hashtable<>();
ht.put("a", "1");
ht.put("b", "2");

// Enumeration is synchronized with Hashtable
// If Hashtable changes during enumeration, it continues safely
// (but elements() creates snapshot-like view)

// Iterator is "fail-fast"
// If Hashtable changes during iteration, throws ConcurrentModificationException
try {
    for (String key : ht.keySet()) {
        if (key.equals("a")) {
            ht.put("c", "3"); // ❌ Will throw exception
        }
    }
} catch (ConcurrentModificationException e) {
    System.out.println("Iterator is fail-fast");
}
```

### 4. Performance with Many Threads
```java
// Hashtable locks entire table for any operation
// With many threads, contention is high

// Simulated multi-threaded workload
class HashtableThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Hashtable<Integer, String> ht = new Hashtable<>();
        
        long start = System.currentTimeMillis();
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ht.put(threadId * 10000 + j, "value");
                    if (j % 100 == 0) {
                        ht.get(threadId * 10000 + (j / 2));
                    }
                }
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
        
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Time with Hashtable: " + elapsed + "ms");
        
        // ConcurrentHashMap would be significantly faster
    }
}
```

## Performance Characteristics

| Operation | Average Case | Worst Case | Synchronized? |
|-----------|--------------|-----------|---------------|
| `put(K, V)` | O(1) | O(n) | ✅ Yes |
| `get(K)` | O(1) | O(n) | ✅ Yes |
| `remove(K)` | O(1) | O(n) | ✅ Yes |
| `containsKey(K)` | O(1) | O(n) | ✅ Yes |
| `containsValue(V)` | O(n) | O(n) | ✅ Yes |
| `size()` | O(1) | O(1) | ✅ Yes |

## When NOT to Use Hashtable

```java
// ❌ DON'T do this (Hashtable in new code)
Hashtable<String, Integer> ht = new Hashtable<>();

// ✅ DO use ConcurrentHashMap instead
ConcurrentHashMap<String, Integer> cm = new ConcurrentHashMap<>();

// ✅ OR use Collections.synchronizedMap
Map<String, Integer> sync = Collections.synchronizedMap(new HashMap<>());

// ✅ OR use HashMap if single-threaded
HashMap<String, Integer> hm = new HashMap<>();
```

## Migration from Hashtable

```java
// Old code
Hashtable<String, Integer> oldMap = new Hashtable<>();
oldMap.put("a", 1);
Enumeration<String> keys = oldMap.keys();

// Modernized equivalent
ConcurrentHashMap<String, Integer> newMap = new ConcurrentHashMap<>();
newMap.put("a", 1);
Iterator<String> keys = newMap.keys().iterator();

// Or if you don't need concurrency
HashMap<String, Integer> simpleMap = new HashMap<>();
simpleMap.put("a", 1);
Iterator<String> keys2 = simpleMap.keySet().iterator();
```

## Key Takeaways
1. **Hashtable is LEGACY**—avoid in new code; use ConcurrentHashMap or HashMap instead
2. **Thread-safe by default**—every method synchronized; very slow for concurrent access
3. **No null keys or values**—throws NullPointerException if you try
4. **Enumeration support**—original iteration API from Collections 1.0
5. **Method-level synchronization**—locks entire table, not just buckets
6. **Default capacity is 11** (prime number) unlike HashMap's 16
7. **ConcurrentHashMap is better**—segment-based locking for true concurrency
8. **Only use if**—maintaining legacy code that requires Hashtable
9. **Properties extends Hashtable**—another reason to migrate away from Hashtable-based code
10. **Single-threaded? Use HashMap**—better performance if synchronization not needed

