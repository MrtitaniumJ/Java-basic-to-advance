# LinkedHashMap: Complete Guide

## Real-World Analogy
Imagine a library that maintains both a fast lookup system (like HashMap) AND a chronological card file showing the order books were added. When searching for a book, you get instant access; but you can also walk through the card file to see books in the order they were acquired. This dual system is LinkedHashMap—combining HashMap's speed with insertion order preservation.

## Internal Structure & Mechanism
LinkedHashMap extends HashMap and adds a **doubly-linked list** of entries:
- **Inherits HashMap**: All hash table efficiency for fast lookups
- **Doubly-Linked List**: Maintains insertion (or access) order
- **Entry Node**: Extends HashMap's Node with `before` and `after` pointers
- **Two Modes**: 
  - **Insertion-order**: Default; maintains order entries were added
  - **Access-order**: `LinkedHashMap(capacity, loadFactor, true)` - LRU cache pattern

### Visual Structure
```
HashMap layer: [Hash buckets for O(1) lookup]
     ↓
Linked list layer: entry1 ←→ entry2 ←→ entry3 ←→ entry4
                   (maintains order)
```

## Key Storage Mechanism
- **Order Preservation**: Each Entry holds `before` and `after` references
- **Header Sentinel**: Uses dummy entry as head/tail of doubly-linked list
- **No Extra Space**: Minimal overhead compared to HashMap
- **Access Tracking**: Optional—can update order on `get()` calls
- **Null Support**: Like HashMap, allows one null key and multiple null values

## Constructors & Creation Methods

```java
// Default: insertion-order, capacity=16, loadFactor=0.75
LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();

// With initial capacity
LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>(32);

// With capacity and load factor
LinkedHashMap<String, Integer> map3 = new LinkedHashMap<>(64, 0.8f);

// Access-order (LRU cache mode) - 3rd parameter true
LinkedHashMap<String, Integer> lruMap = new LinkedHashMap<>(16, 0.75f, true);

// Copy constructor (preserves iteration order)
LinkedHashMap<String, Integer> map4 = new LinkedHashMap<>(existing);

// Protected constructor for custom LinkedHashMap
class CustomLinkedHashMap extends LinkedHashMap<String, Integer> {
    protected boolean removeEldestEntry(Map.Entry eldest) {
        // Implement custom removal logic
        return size() > 100; // Remove oldest when size exceeds 100
    }
}
```

## Core Operations with Performance

| Operation | Time Complexity | Details |
|-----------|-----------------|---------|
| `put(K, V)` | O(1) average | O(n) worst case; maintains insertion order |
| `get(K)` | O(1) average | O(n) worst case; can trigger re-ordering in access-mode |
| `remove(K)` | O(1) average | Updates linked list pointers |
| `containsKey(K)` | O(1) average | Same as HashMap |
| `containsValue(V)` | O(n) | Linear scan required |
| `clear()` | O(n) | Clears all entries and links |
| `size()` | O(1) | Constant time |
| First Entry | O(1) | Access header.after |
| Last Entry | O(1) | Access header.before |

## Insertion vs Access Order

### Insertion-Order Mode (Default)
```java
LinkedHashMap<String, String> map = new LinkedHashMap<>();
map.put("apple", "a");
map.put("banana", "b");
map.put("cherry", "c");
map.put("apple", "A"); // Updates value, insertion order unchanged

// Iteration order: apple, banana, cherry
map.forEach((k, v) -> System.out.println(k + ":" + v));
```

### Access-Order Mode (LRU Pattern)
```java
LinkedHashMap<String, String> lruMap = new LinkedHashMap<>(16, 0.75f, true);
lruMap.put("apple", "a");
lruMap.put("banana", "b");
lruMap.put("cherry", "c");
lruMap.get("apple"); // Moves "apple" to end (most recent)
lruMap.get("banana"); // Moves "banana" to end

// Iteration order: cherry, apple, banana (access order)
lruMap.forEach((k, v) -> System.out.println(k + ":" + v));
```

## Iteration Methods

```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("first", 1);
map.put("second", 2);
map.put("third", 3);

// 1. entrySet() - Maintains order
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 2. keySet() - Maintains order
for (String key : map.keySet()) {
    System.out.println(key);
}

// 3. values() - Maintains order
for (Integer value : map.values()) {
    System.out.println(value);
}

// 4. Iterator with modification safety
Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, Integer> entry = it.next();
    if (entry.getValue() < 2) {
        it.remove(); // Safe removal during iteration
    }
}

// 5. forEach (preserves order)
map.forEach((k, v) -> System.out.println(k + ": " + v));

// 6. Stream API (preserves order)
map.entrySet().stream()
   .filter(e -> e.getValue() > 1)
   .forEach(e -> System.out.println(e.getKey()));
```

## Oldest & Newest Access

```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

// Get first (oldest in insertion-order, least recent in access-order)
Map.Entry<String, Integer> first = map.entrySet().iterator().next();
System.out.println("First: " + first.getKey()); // "a"

// Get last (newest)
Optional<Map.Entry<String, Integer>> last = map.entrySet().stream().reduce((a, b) -> b);
System.out.println("Last: " + last.get().getKey()); // "c"
```

## When to Use LinkedHashMap
✅ **Use when:**
- Need insertion order preservation
- Building ordered cache with predictable iteration
- Implementing LRU (Least Recently Used) cache
- Need to track access patterns
- Order matters for presentation/logging
- Want more control than HashMap but less overhead than TreeMap

❌ **Avoid when:**
- Don't need ordered access (use HashMap)
- Need sorted order (use TreeMap)
- Require full thread safety (use ConcurrentHashMap or synchronized wrapper)

## Common Use Cases with Examples

### 1. LRU Cache Implementation
```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;
    
    public LRUCache(int maxSize) {
        super(16, 0.75f, true); // access-order mode
        this.maxSize = maxSize;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}

// Usage
LRUCache<String, Integer> cache = new LRUCache<>(3);
cache.put("user1", 100);
cache.put("user2", 200);
cache.put("user3", 300);
System.out.println("Cache: " + cache); // {user1=100, user2=200, user3=300}

cache.get("user1"); // Moves user1 to end
cache.put("user4", 400); // Removes least recently used (user2)
System.out.println("Cache: " + cache); // {user3=300, user1=100, user4=400}
```

### 2. Maintaining Insertion Order
```java
class BirthdayTracker {
    LinkedHashMap<String, String> birthdays = new LinkedHashMap<>();
    
    public void addBirthday(String name, String date) {
        birthdays.put(name, date);
    }
    
    public void listInOrderAdded() {
        System.out.println("Birthdays (added in order):");
        birthdays.forEach((name, date) -> System.out.println(name + ": " + date));
    }
}

BirthdayTracker tracker = new BirthdayTracker();
tracker.addBirthday("Alice", "1990-01-15");
tracker.addBirthday("Bob", "1985-06-20");
tracker.addBirthday("Charlie", "1995-12-10");
tracker.listInOrderAdded();
```

### 3. Browser History (Most Recent First)
```java
class BrowserHistory {
    private LinkedHashMap<String, Long> history;
    
    public BrowserHistory(int maxSize) {
        history = new LinkedHashMap<String, Long>(16, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxSize;
            }
        };
    }
    
    public void visitUrl(String url) {
        history.put(url, System.currentTimeMillis());
    }
    
    public void printHistory() {
        System.out.println("Recent history (most recent last):");
        history.forEach((url, time) -> System.out.println(url));
    }
}

BrowserHistory history = new BrowserHistory(5);
history.visitUrl("google.com");
history.visitUrl("stackoverflow.com");
history.visitUrl("github.com");
history.visitUrl("google.com"); // Recent access, moved to end
history.printHistory();
```

### 4. JSON Object Ordering
```java
class OrderedJSONObject {
    private LinkedHashMap<String, Object> data = new LinkedHashMap<>();
    
    public void put(String key, Object value) {
        data.put(key, value);
    }
    
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder("{\n");
        data.forEach((key, value) -> 
            sb.append("  \"").append(key).append("\": ").append(value).append(",\n")
        );
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2); // Remove trailing comma
        }
        sb.append("\n}");
        return sb.toString();
    }
}

OrderedJSONObject obj = new OrderedJSONObject();
obj.put("name", "Alice");
obj.put("age", 30);
obj.put("email", "alice@example.com");
System.out.println(obj.toFormattedString());
// Output maintains insertion order
```

### 5. Task Queue with Order
```java
class TaskQueue {
    private LinkedHashMap<Integer, String> tasks = new LinkedHashMap<>();
    
    public void addTask(String task) {
        tasks.put(tasks.size() + 1, task);
    }
    
    public void processTasks() {
        tasks.forEach((id, task) -> {
            System.out.println("Processing task " + id + ": " + task);
        });
    }
    
    public void printPendingTasks() {
        System.out.println("Pending tasks (in order):");
        tasks.forEach((id, task) -> System.out.println(id + ". " + task));
    }
}

TaskQueue queue = new TaskQueue();
queue.addTask("Read email");
queue.addTask("Reply to messages");
queue.addTask("Update documentation");
queue.addTask("Code review");
queue.printPendingTasks();
queue.processTasks();
```

### 6. Caching with TTL (Time-based LRU)
```java
class TimedLRUCache<K, V> extends LinkedHashMap<K, V> {
    private final long ttlMillis;
    private final Map<K, Long> timestamps = new LinkedHashMap<>();
    
    public TimedLRUCache(int maxSize, long ttlMillis) {
        super(16, 0.75f, true);
        this.ttlMillis = ttlMillis;
    }
    
    @Override
    public V put(K key, V value) {
        timestamps.put(key, System.currentTimeMillis());
        return super.put(key, value);
    }
    
    @Override
    public V get(Object key) {
        if (timestamps.containsKey(key)) {
            long age = System.currentTimeMillis() - timestamps.get(key);
            if (age > ttlMillis) {
                remove(key);
                timestamps.remove(key);
                return null;
            }
        }
        return super.get(key);
    }
}

TimedLRUCache<String, String> cache = new TimedLRUCache<>(100, 5000); // 5 second TTL
cache.put("key1", "value1");
cache.put("key2", "value2");
System.out.println(cache.get("key1")); // "value1"
```

## Edge Cases & Gotchas

### 1. Order Changes on Update
```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

// In insertion-order mode, updating existing key doesn't change order
map.put("a", 10);
// Order still: a, b, c

// But in access-order mode, get() moves it to end
LinkedHashMap<String, Integer> accessMap = new LinkedHashMap<>(16, 0.75f, true);
accessMap.put("a", 1);
accessMap.put("b", 2);
accessMap.put("c", 3);
accessMap.get("a"); // Moves "a" to end
// Order now: b, c, a
```

### 2. removeEldestEntry Not Automatic
```java
// removeEldestEntry only called on put(), not get()
LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>(3, 0.75f, true) {
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > 3;
    }
};

map.put("a", 1);
map.put("b", 2);
map.put("c", 3);
map.put("d", 4); // "a" automatically removed
System.out.println(map.size()); // 3

// But manual remove() doesn't trigger removeEldestEntry
map.remove("b"); // Doesn't trigger removal logic
```

### 3. Iteration During Modification
```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

// ConcurrentModificationException if modifying during iteration
try {
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        if (entry.getValue() == 2) {
            map.put("d", 4); // ❌ Will throw exception
        }
    }
} catch (ConcurrentModificationException e) {
    System.out.println("Caught: " + e);
}

// Safe way: use iterator and remove()
Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, Integer> entry = it.next();
    if (entry.getValue() == 2) {
        it.remove(); // Safe
    }
}
```

### 4. First/Last Entry Access
```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

// Getting first entry (oldest in insertion-order)
String firstKey = map.entrySet().iterator().next().getKey();
System.out.println("First: " + firstKey); // "a"

// Getting last entry (requires iteration)
String lastKey = null;
for (String key : map.keySet()) {
    lastKey = key;
}
System.out.println("Last: " + lastKey); // "c"

// Better way: convert to stream
Optional<String> last = map.keySet().stream().reduce((a, b) -> b);
```

## Thread Safety & Synchronization
- **LinkedHashMap is NOT thread-safe**
- **Synchronized Wrapper**:
```java
Map<String, Integer> syncMap = Collections.synchronizedMap(new LinkedHashMap<>());
```
- **Multiple Threads**: Use external synchronization or ConcurrentHashMap
- **removeEldestEntry**: Automatically invoked during `put()` under lock if synchronized

## Comparison with Other Map Implementations

| Feature | LinkedHashMap | HashMap | TreeMap | ConcurrentHashMap |
|---------|---------------|---------|---------|------------------|
| **Order** | Insertion/Access | No | Sorted | No |
| **Time (get)** | O(1) avg | O(1) avg | O(log n) | O(1) avg |
| **Space** | More (linked list) | Less | Moderate | Moderate |
| **Iteration** | Predictable | Unpredictable | Sorted | Unpredictable |
| **Null keys** | 1 allowed | 1 allowed | Not allowed | 1 allowed |
| **Thread-safe** | No | No | No | Yes |

## Performance Characteristics

```java
public class LinkedHashMapPerformance {
    public static void main(String[] args) {
        int size = 100_000;
        
        // Insertion order preservation (minimal overhead vs HashMap)
        long start = System.nanoTime();
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(i, "value_" + i);
        }
        long insertionTime = System.nanoTime() - start;
        
        // Iteration in insertion order
        start = System.nanoTime();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            String val = entry.getValue();
        }
        long iterationTime = System.nanoTime() - start;
        
        // Access-order mode with frequent get()
        LinkedHashMap<Integer, String> lruMap = 
            new LinkedHashMap<>(16, 0.75f, true);
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            lruMap.put(i, "value_" + i);
            if (i > 1000) lruMap.get(i - 500);
        }
        long lruTime = System.nanoTime() - start;
        
        System.out.println("Insertion: " + (insertionTime / 1_000_000) + " ms");
        System.out.println("Iteration: " + (iterationTime / 1_000_000) + " ms");
        System.out.println("LRU with access: " + (lruTime / 1_000_000) + " ms");
    }
}
```

## Key Takeaways
1. LinkedHashMap combines HashMap speed with predictable iteration order
2. Two modes: insertion-order (default) and access-order (LRU pattern)
3. Minimal overhead: just `before` and `after` pointers per entry
4. Perfect for LRU cache implementation via `removeEldestEntry()`
5. Access-order mode moves accessed entries to end automatically
6. Iteration order is predictable and always maintained
7. Not thread-safe—synchronize externally or use Collections.synchronizedMap()
8. More memory than HashMap due to doubly-linked list structure
9. Same lookup/insert/remove performance as HashMap (O(1) average)
10. Better choice than TreeMap when order doesn't need sorting

