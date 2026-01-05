# WeakHashMap: Complete Guide

## Real-World Analogy
Imagine a sticky-note storage system in a busy office. When you write notes about people (keys pointing to Person objects) and attach important information (values) to them, the notes are kept secure. However, here's the twist: **if the person leaves the office permanently and nobody else remembers them, the sticky note is automatically thrown away by the janitor**. The sticky note can only stay as long as someone actively remembers (holds a strong reference to) that person. This is WeakHashMap—it automatically cleans up entries when keys are no longer referenced elsewhere.

## Key Concept: Weak References

WeakHashMap uses **weak references** for keys, not regular strong references:
- **Strong Reference**: Normal reference; object stays in memory as long as reference exists
- **Weak Reference**: Reference that doesn't prevent garbage collection; object can be collected if no other strong references exist
- **Garbage Collection**: When a key's only references are weak (like in WeakHashMap), it becomes eligible for garbage collection
- **Automatic Cleanup**: WeakHashMap automatically removes entries whose keys have been garbage collected

### Visual Difference
```
HashMap: key1 -> (Strong Ref) -> KeyObject
         Entry keeps key alive indefinitely

WeakHashMap: key1 -> (Weak Ref) -> KeyObject
             Entry discarded if KeyObject garbage collected
```

## When Keys are Garbage Collected

```java
// Example 1: Key becomes garbage collectible
WeakHashMap<String, String> map = new WeakHashMap<>();

String key = new String("mykey"); // Create key object
map.put(key, "myvalue");

System.out.println("Before: " + map.size()); // 1

key = null; // Remove only strong reference to key
System.gc(); // Request garbage collection

// After GC, key object might be collected
System.out.println("After: " + map.size()); // Likely 0
```

## Internal Structure & Mechanism

WeakHashMap combines:
- **Hash Table**: Same bucket-based structure as HashMap
- **Weak References**: Keys stored as `WeakReference<K>` internally
- **Reference Queue**: Tracks when weak references are cleared
- **Lazy Cleanup**: Removed during internal operations (put, get, size, etc.)

### How Cleanup Works
```
1. Key object created with strong reference in user code
2. User puts key into WeakHashMap
3. Internal WeakReference<K> created; weak reference to same object
4. If user releases their strong reference (key = null)
5. Only weak references remain (from WeakHashMap)
6. Garbage collector collects the object
7. WeakReference is cleared automatically
8. WeakHashMap detects cleared reference on next operation
9. Associated entry is removed from map
```

## Constructors & Creation Methods

```java
// Default constructor (capacity=16, loadFactor=0.75)
WeakHashMap<String, String> map1 = new WeakHashMap<>();

// With initial capacity
WeakHashMap<String, String> map2 = new WeakHashMap<>(32);

// With capacity and load factor
WeakHashMap<String, String> map3 = new WeakHashMap<>(64, 0.8f);

// Copy constructor
WeakHashMap<String, String> map4 = new WeakHashMap<>(map1);

// Common pattern: using custom objects as keys
class CacheKey {
    String id;
    CacheKey(String id) { this.id = id; }
    
    @Override
    public int hashCode() { return id.hashCode(); }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof CacheKey && ((CacheKey) o).id.equals(id);
    }
}

WeakHashMap<CacheKey, String> cacheMap = new WeakHashMap<>();
```

## Core Operations with Performance

| Operation | Time Complexity | Special Notes |
|-----------|-----------------|---------------|
| `put(K, V)` | O(1) average | Triggers cleanup of cleared references |
| `get(K)` | O(1) average | May trigger cleanup |
| `remove(K)` | O(1) average | Removes weak reference |
| `containsKey(K)` | O(1) average | Checks if key present |
| `containsValue(V)` | O(n) | Linear scan required |
| `clear()` | O(n) | Clears all entries |
| `size()` | O(n) | Counts valid entries, triggers cleanup |
| `entrySet()` | O(n) | Gets all entries after cleanup |
| `keySet()` | O(n) | Gets all keys that still exist |
| `values()` | O(n) | Gets values for existing keys |

**Important**: Size, iteration, and contains operations may trigger garbage collection and cleanup, making them more expensive than HashMap.

## Weak References in Action

```java
class User {
    String name;
    User(String name) { this.name = name; }
    
    @Override
    public String toString() { return name; }
}

public class WeakReferenceDemo {
    public static void main(String[] args) {
        WeakHashMap<User, String> userCache = new WeakHashMap<>();
        
        // Create strong references
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        
        userCache.put(user1, "Alice's data");
        userCache.put(user2, "Bob's data");
        
        System.out.println("Initial size: " + userCache.size()); // 2
        
        // Release user1
        user1 = null;
        System.out.println("After user1=null, size: " + userCache.size()); // Still 2 (GC not called)
        
        // Request garbage collection
        System.gc();
        
        // Check again (cleanup happens on next operation)
        System.out.println("After GC, size: " + userCache.size()); // Likely 1
        
        // user2 still in map
        System.out.println("Remaining entries: " + userCache.entrySet());
        
        // Release user2
        user2 = null;
        System.gc();
        System.out.println("After user2=null and GC, size: " + userCache.size()); // 0
    }
}
```

## Iteration Methods

```java
WeakHashMap<String, String> map = new WeakHashMap<>();
map.put("key1", "value1");
map.put("key2", "value2");
map.put("key3", "value3");

// 1. entrySet()
System.out.println("entrySet():");
for (Map.Entry<String, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 2. keySet() - Only living keys
System.out.println("keySet():");
for (String key : map.keySet()) {
    System.out.println(key);
}

// 3. values()
System.out.println("values():");
for (String value : map.values()) {
    System.out.println(value);
}

// 4. Iterator (with modification safety)
Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, String> entry = it.next();
    if (entry.getValue().length() < 5) {
        it.remove();
    }
}

// 5. forEach
map.forEach((k, v) -> System.out.println(k + ": " + v));

// IMPORTANT: Iteration may trigger cleanup
// Size during iteration can decrease as weak references are collected
```

## When to Use WeakHashMap
✅ **Use when:**
- Building caches with automatic cleanup (cached objects can be garbage collected)
- Object lifetime should control cache entry lifetime
- Memory is precious and automatic eviction is desired
- Storing metadata about external objects without preventing their collection
- Building lookup tables for objects that may be garbage collected
- Canonical mapping (like storing derived information about objects)

❌ **Avoid when:**
- Keys should always remain accessible (use HashMap)
- Expecting predictable size/iteration behavior (size is non-deterministic)
- Performance is critical (garbage collection checking adds overhead)
- Need thread safety (use ConcurrentWeakHashMap if it exists, or synchronize manually)
- Keys are primitive types (can't be wrapped as objects with weak references)

## Common Use Cases with Examples

### 1. Listener/Observer Registry with Automatic Cleanup
```java
class EventBus {
    interface EventListener {
        void onEvent(String event);
    }
    
    // Listeners are automatically removed when listener object is garbage collected
    private WeakHashMap<EventListener, String> listeners = new WeakHashMap<>();
    
    public void registerListener(EventListener listener, String name) {
        listeners.put(listener, name);
    }
    
    public void notifyListeners(String event) {
        System.out.println("Event: " + event);
        // Iteration also triggers cleanup of dead listeners
        listeners.forEach((listener, name) -> {
            System.out.println("Notifying " + name);
            listener.onEvent(event);
        });
    }
    
    public void printActiveListeners() {
        System.out.println("Active listeners: " + listeners.size());
        listeners.values().forEach(System.out::println);
    }
}

EventBus bus = new EventBus();

// Create listeners
EventBus.EventListener listener1 = e -> System.out.println("  Listener1 got: " + e);
EventBus.EventListener listener2 = e -> System.out.println("  Listener2 got: " + e);

bus.registerListener(listener1, "Listener1");
bus.registerListener(listener2, "Listener2");
bus.printActiveListeners(); // 2 listeners

bus.notifyListeners("startup");

// Release listener2 from external code
listener2 = null;
System.gc();

bus.printActiveListeners(); // 1 listener
bus.notifyListeners("shutdown"); // Only listener1 notified
```

### 2. Object Metadata Cache
```java
class ResourceCache {
    static class Resource {
        String id;
        String content;
        
        Resource(String id, String content) {
            this.id = id;
            this.content = content;
        }
    }
    
    static class Metadata {
        String contentType;
        long lastModified;
        int size;
        
        Metadata(String contentType, String content) {
            this.contentType = contentType;
            this.lastModified = System.currentTimeMillis();
            this.size = content.length();
        }
    }
    
    // Cache metadata for Resource objects
    // Automatically cleared when Resource is garbage collected
    private WeakHashMap<Resource, Metadata> metadataCache = new WeakHashMap<>();
    
    public void cacheMetadata(Resource resource, String contentType) {
        metadataCache.put(resource, new Metadata(contentType, resource.content));
    }
    
    public Metadata getMetadata(Resource resource) {
        return metadataCache.getOrDefault(resource, null);
    }
    
    public int getCacheSize() {
        return metadataCache.size();
    }
}

ResourceCache cache = new ResourceCache();

Resource res1 = new ResourceCache.Resource("res1", "Hello World");
Resource res2 = new ResourceCache.Resource("res2", "Java Programming");

cache.cacheMetadata(res1, "text/plain");
cache.cacheMetadata(res2, "text/plain");

System.out.println("Cache size: " + cache.getCacheSize()); // 2

res1 = null; // Allow res1 to be garbage collected
System.gc();

System.out.println("After GC, cache size: " + cache.getCacheSize()); // Likely 1
```

### 3. View Component Property Storage
```java
class UIPropertyCache {
    static class Component {
        String id;
        Component(String id) { this.id = id; }
        
        @Override
        public String toString() { return "Component(" + id + ")"; }
    }
    
    // Store computed properties for UI components
    // Automatically cleared when component is removed from scene
    private WeakHashMap<Component, Map<String, Object>> propertyCache = 
        new WeakHashMap<>();
    
    public void setProperty(Component component, String property, Object value) {
        propertyCache.computeIfAbsent(component, k -> new HashMap<>())
                    .put(property, value);
    }
    
    public Object getProperty(Component component, String property) {
        Map<String, Object> props = propertyCache.get(component);
        return props != null ? props.get(property) : null;
    }
    
    public void printActiveComponents() {
        System.out.println("Cached components: " + propertyCache.size());
        propertyCache.keySet().forEach(System.out::println);
    }
}

UIPropertyCache cache = new UIPropertyCache();

UIPropertyCache.Component button = new UIPropertyCache.Component("button1");
UIPropertyCache.Component label = new UIPropertyCache.Component("label1");

cache.setProperty(button, "backgroundColor", "blue");
cache.setProperty(button, "width", 100);
cache.setProperty(label, "text", "Hello");

cache.printActiveComponents(); // 2 components

// Component removed from UI (strong reference released)
button = null;
System.gc();

cache.printActiveComponents(); // 1 component
```

### 4. Thread Local Cleanup Registry
```java
class ThreadLocalRegistry {
    // Map of Thread -> Thread-local resources
    // Entries automatically removed when thread terminates
    private WeakHashMap<Thread, Map<String, Object>> threadResources = 
        new WeakHashMap<>();
    
    public void setThreadResource(String key, Object value) {
        threadResources.computeIfAbsent(Thread.currentThread(), t -> new HashMap<>())
                      .put(key, value);
    }
    
    public Object getThreadResource(String key) {
        Map<String, Object> resources = threadResources.get(Thread.currentThread());
        return resources != null ? resources.get(key) : null;
    }
    
    public void printActiveThreads() {
        System.out.println("Threads with resources: " + threadResources.size());
        threadResources.keySet().forEach(t -> System.out.println("  " + t.getName()));
    }
}

ThreadLocalRegistry registry = new ThreadLocalRegistry();

Thread t1 = new Thread(() -> {
    registry.setThreadResource("connection", "DB-Connection-1");
    registry.setThreadResource("session", "Session-ABC");
    System.out.println("T1 stored resources");
}, "WorkerThread-1");

Thread t2 = new Thread(() -> {
    registry.setThreadResource("connection", "DB-Connection-2");
    System.out.println("T2 stored resources");
}, "WorkerThread-2");

t1.start();
t2.start();
t1.join();
t2.join();

System.gc();
registry.printActiveThreads(); // Threads may be cleaned up
```

### 5. Dependency Tracking for Garbage Collection
```java
class DependencyTracker {
    static class Package {
        String name;
        Package(String name) { this.name = name; }
        
        @Override
        public String toString() { return "Package(" + name + ")"; }
    }
    
    // Track reverse dependencies: Package -> Modules that depend on it
    // When package is garbage collected, dependent modules are updated
    private WeakHashMap<Package, List<String>> dependencyMap = new WeakHashMap<>();
    
    public void addDependency(Package pkg, String dependentModule) {
        dependencyMap.computeIfAbsent(pkg, k -> new ArrayList<>())
                    .add(dependentModule);
    }
    
    public List<String> getDependencies(Package pkg) {
        List<String> deps = dependencyMap.get(pkg);
        return deps != null ? new ArrayList<>(deps) : new ArrayList<>();
    }
    
    public void printDependencyMap() {
        System.out.println("Tracked packages: " + dependencyMap.size());
        dependencyMap.forEach((pkg, modules) -> 
            System.out.println(pkg + " <- " + modules)
        );
    }
}

DependencyTracker tracker = new DependencyTracker();

DependencyTracker.Package utils = new DependencyTracker.Package("utils");
DependencyTracker.Package logger = new DependencyTracker.Package("logger");

tracker.addDependency(utils, "module-a");
tracker.addDependency(utils, "module-b");
tracker.addDependency(logger, "module-a");

tracker.printDependencyMap(); // 2 packages

logger = null; // Release logger package
System.gc();

tracker.printDependencyMap(); // 1 package
```

### 6. Per-Object Statistics
```java
class PerformanceMonitor {
    static class Task {
        String name;
        Task(String name) { this.name = name; }
    }
    
    static class TaskStats {
        long executionTime;
        int executionCount;
        long lastRun;
        
        TaskStats() {
            this.executionCount = 0;
            this.executionTime = 0;
        }
        
        void recordExecution(long time) {
            this.executionTime += time;
            this.executionCount++;
            this.lastRun = System.currentTimeMillis();
        }
        
        double getAverageTime() {
            return executionCount > 0 ? (double) executionTime / executionCount : 0;
        }
    }
    
    private WeakHashMap<Task, TaskStats> taskStats = new WeakHashMap<>();
    
    public void recordTaskExecution(Task task, long executionTime) {
        taskStats.computeIfAbsent(task, k -> new TaskStats())
                .recordExecution(executionTime);
    }
    
    public TaskStats getTaskStats(Task task) {
        return taskStats.get(task);
    }
    
    public void printStats() {
        System.out.println("Tasks being monitored: " + taskStats.size());
        taskStats.forEach((task, stats) -> 
            System.out.println(task.name + ": avg=" + 
                             String.format("%.2f", stats.getAverageTime()) + 
                             "ms, count=" + stats.executionCount)
        );
    }
}

PerformanceMonitor monitor = new PerformanceMonitor();

PerformanceMonitor.Task task1 = new PerformanceMonitor.Task("QueryDB");
PerformanceMonitor.Task task2 = new PerformanceMonitor.Task("ProcessData");

monitor.recordTaskExecution(task1, 100);
monitor.recordTaskExecution(task1, 150);
monitor.recordTaskExecution(task2, 50);

monitor.printStats(); // 2 tasks

task1 = null;
System.gc();

monitor.printStats(); // 1 task
```

## Edge Cases & Gotchas

### 1. Unpredictable Size
```java
WeakHashMap<String, String> map = new WeakHashMap<>();

String key1 = new String("key");
map.put(key1, "value");

System.out.println("Size with strong ref: " + map.size()); // 1

key1 = null; // Remove strong reference

// Size might still be 1 (GC not called yet)
System.out.println("Size before GC: " + map.size()); // Could be 1

System.gc();

// Size now likely 0 (entry cleaned up)
System.out.println("Size after GC: " + map.size()); // Likely 0
```

### 2. Strings are Cached
```java
// String literals are cached and strong-referenced by JVM
WeakHashMap<String, String> map = new WeakHashMap<>();

// String literal - WILL NOT be garbage collected
map.put("key1", "value1");
String key1 = "key1";
key1 = null;
System.gc();
System.out.println("String literal entry: " + map.size()); // Still 1

// String created with new - CAN be garbage collected
map.put(new String("key2"), "value2");
System.out.println("New String entry before GC: " + map.size()); // 2
System.gc();
System.out.println("New String entry after GC: " + map.size()); // Likely 1
```

### 3. GC Timing is Non-deterministic
```java
WeakHashMap<Object, String> map = new WeakHashMap<>();

Object key = new Object();
map.put(key, "value");

key = null;

// Calling size() triggers cleanup but doesn't force GC
System.out.println("After key=null, size: " + map.size()); // Still 1

// GC might not happen immediately
System.gc();
System.out.println("After GC request, size: " + map.size()); // Likely 0 but not guaranteed

// GC is not guaranteed to run; it's just a request
```

### 4. Iteration Safety
```java
WeakHashMap<String, String> map = new WeakHashMap<>();
map.put("a", "1");
map.put("b", "2");
map.put("c", "3");

// Iteration might see different sizes due to GC during iteration
for (Map.Entry<String, String> entry : map.entrySet()) {
    // During iteration, if key's strong reference is released elsewhere,
    // entry might be removed by next operation
    System.out.println(entry.getKey());
}
```

## Synchronization & Thread Safety
- **WeakHashMap is NOT thread-safe**
- **Multi-threaded Use**:
```java
// Option 1: Synchronize externally
WeakHashMap<String, String> map = new WeakHashMap<>();
synchronized(map) {
    map.put("key", "value");
}

// Option 2: Collections.synchronizedMap() with WeakHashMap
Map<String, String> syncWeakMap = 
    Collections.synchronizedMap(new WeakHashMap<>());
```

## Comparison with Other Map Implementations

| Feature | WeakHashMap | HashMap | LinkedHashMap | TreeMap |
|---------|------------|---------|---------------|---------|
| **Key Lifecycle** | Automatic removal | Held indefinitely | Held indefinitely | Held indefinitely |
| **Time (get)** | O(1) avg | O(1) avg | O(1) avg | O(log n) |
| **Predictable size** | ❌ No | ✅ Yes | ✅ Yes | ✅ Yes |
| **Memory efficient** | ✅ Yes | ❌ No | ❌ No | ❌ No |
| **Use case** | Caches | General | Ordered access | Sorted keys |
| **GC interaction** | Active | Passive | Passive | Passive |

## Performance Characteristics

```java
public class WeakHashMapBenchmark {
    public static void main(String[] args) {
        int size = 100_000;
        
        // WeakHashMap with no GC pressure
        long start = System.nanoTime();
        WeakHashMap<Integer, String> weakMap = new WeakHashMap<>();
        for (int i = 0; i < size; i++) {
            weakMap.put(i, "value_" + i);
        }
        long weakInsertTime = System.nanoTime() - start;
        
        // Regular HashMap for comparison
        start = System.nanoTime();
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            hashMap.put(i, "value_" + i);
        }
        long hashInsertTime = System.nanoTime() - start;
        
        System.out.println("WeakHashMap insert: " + (weakInsertTime / 1_000_000) + "ms");
        System.out.println("HashMap insert: " + (hashInsertTime / 1_000_000) + "ms");
        
        // WeakHashMap size triggers cleanup
        start = System.nanoTime();
        int weakSize = weakMap.size();
        long weakSizeTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        int hashSize = hashMap.size();
        long hashSizeTime = System.nanoTime() - start;
        
        System.out.println("WeakHashMap.size(): " + (weakSizeTime / 1_000) + "μs");
        System.out.println("HashMap.size(): " + (hashSizeTime / 1_000) + "μs");
    }
}
```

## Key Takeaways
1. **Automatic Cleanup**: Entries removed when keys are garbage collected
2. **Use for Caches**: Perfect for caching where object lifetime controls cache lifetime
3. **Unpredictable Size**: Size depends on garbage collection timing
4. **String Literals**: Won't be garbage collected (cached by JVM)
5. **Weak vs Strong References**: Keys held by weak references; values held strongly
6. **No Null Keys**: Like HashMap, doesn't allow null keys
7. **Performance Overhead**: Size/iteration slower due to cleanup checks
8. **Not Thread-Safe**: Synchronize externally for multi-threaded use
9. **Reference Queue**: Internally uses ReferenceQueue to track cleared weak references
10. **Best for Objects**: Most useful when keys are custom objects that can be garbage collected
11. **Iteration may Change**: Size may decrease during iteration as objects are collected
12. **Use Cases**: Caches, listeners, metadata tracking, resource management

