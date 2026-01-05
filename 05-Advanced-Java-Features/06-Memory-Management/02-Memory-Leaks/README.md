# Memory Leaks in Java

## Table of Contents
1. [Introduction to Memory Leaks](#introduction-to-memory-leaks)
2. [Why Memory Leaks Occur](#why-memory-leaks-occur)
3. [Common Memory Leak Patterns](#common-memory-leak-patterns)
4. [Detection Techniques](#detection-techniques)
5. [Prevention Strategies](#prevention-strategies)
6. [Tools and Profilers](#tools-and-profilers)
7. [Real-World Examples](#real-world-examples)
8. [Best Practices](#best-practices)

## Introduction to Memory Leaks

A memory leak in Java occurs when objects are no longer needed but remain reachable from the root set, preventing the garbage collector from reclaiming their memory. Unlike C/C++, Java's automatic garbage collection prevents most memory leaks, but they can still occur through logical errors in the application.

**Key Difference from Native Languages:**
- C/C++: Manual memory management → allocation errors cause leaks
- Java: Automatic GC → logical errors cause leaks (usually through unintended object references)

**Impact of Memory Leaks:**
- Gradual heap exhaustion
- Eventual OutOfMemoryError (OOM)
- Performance degradation
- Application crashes
- System instability in long-running applications

## Why Memory Leaks Occur

Java memory leaks happen because:

1. **Unintended Object References**: Objects maintain references to data that should have been discarded
2. **Container Growth**: Collections grow without bounds
3. **Thread-Related Issues**: ThreadLocal variables not properly cleaned
4. **Static References**: Static collections accumulating objects
5. **Incomplete Cleanup**: Resources not released in finally blocks or try-with-resources
6. **Listener/Observer Pattern**: Listeners registered but never unregistered

```java
public class MemoryLeakCauses {
    
    // ANTI-PATTERN 1: Unintended object references
    static class ObjectHolderLeak {
        private Object[] elements;
        private int size = 0;
        
        public ObjectHolderLeak(int capacity) {
            this.elements = new Object[capacity];
        }
        
        public void push(Object obj) {
            if (size < elements.length) {
                elements[size++] = obj;
            }
        }
        
        public Object pop() {
            if (size == 0) return null;
            // LEAK: Reference not cleared, element still reachable
            return elements[--size];
        }
        
        public Object popFixed() {
            if (size == 0) return null;
            Object obj = elements[--size];
            elements[size] = null; // Clear reference - GOOD
            return obj;
        }
    }
    
    // ANTI-PATTERN 2: Static collections without bounds
    static class StaticCacheLeak {
        // This cache grows indefinitely
        private static Map<String, byte[]> cache = new HashMap<>();
        
        public static void cacheData(String key, byte[] data) {
            cache.put(key, data); // No eviction policy
        }
        
        // BETTER: Bounded cache
        private static class BoundedCache {
            private Map<String, byte[]> data = new LinkedHashMap<String, byte[]>(100, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > 100; // Limit to 100 entries
                }
            };
            
            void put(String key, byte[] value) {
                data.put(key, value);
            }
        }
    }
    
    // ANTI-PATTERN 3: Unregistered listeners
    static class ListenerLeak {
        interface DataListener {
            void onDataChanged(String data);
        }
        
        static class DataSource {
            private List<DataListener> listeners = new ArrayList<>();
            
            void registerListener(DataListener listener) {
                listeners.add(listener); // Registration
            }
            
            void unregisterListener(DataListener listener) {
                listeners.remove(listener); // Often forgotten!
            }
            
            void notifyListeners(String data) {
                for (DataListener listener : listeners) {
                    listener.onDataChanged(data);
                }
            }
        }
        
        static class DataListener1 implements DataListener {
            private byte[] largeData = new byte[10 * 1024 * 1024]; // 10MB
            
            public void onDataChanged(String data) {
                System.out.println("Data changed: " + data);
            }
        }
        
        public static void demonstrateLeak() {
            DataSource source = new DataSource();
            DataListener1 listener = new DataListener1();
            
            source.registerListener(listener);
            // Listener holds 10MB, never unregistered
            // If source lives longer than needed, memory persists
        }
    }
    
    // ANTI-PATTERN 4: ThreadLocal leaks
    static class ThreadLocalLeak {
        // ThreadLocal holding large objects
        private static ThreadLocal<byte[]> threadBuffer = 
            ThreadLocal.withInitial(() -> new byte[10 * 1024 * 1024]); // 10MB
        
        public static void process() {
            byte[] buffer = threadBuffer.get();
            // Use buffer
        }
        
        // LEAK: If thread pool reuses threads, ThreadLocal never cleaned
        
        // BETTER: Explicit cleanup
        static class ImprovedThreadLocal {
            private static ThreadLocal<byte[]> buffer = 
                ThreadLocal.withInitial(() -> new byte[10 * 1024 * 1024]);
            
            public static void processWithCleanup() {
                try {
                    byte[] buf = buffer.get();
                    // Use buffer
                } finally {
                    buffer.remove(); // Explicit cleanup
                }
            }
        }
    }
}
```

## Common Memory Leak Patterns

### Pattern 1: Stack-Like Data Structures

```java
public class StackLeakPattern {
    
    // LEAK: Custom Stack implementation
    static class LeakyStack<E> {
        private E[] elements;
        private int size = 0;
        
        @SuppressWarnings("unchecked")
        public LeakyStack(int initialCapacity) {
            elements = new Object[initialCapacity];
        }
        
        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }
        
        public E pop() {
            if (size == 0) {
                throw new EmptyStackException();
            }
            // MEMORY LEAK: Reference not cleared
            return elements[--size];
        }
        
        private void ensureCapacity() {
            if (elements.length == size) {
                // Grow array
                Object[] oldElements = elements;
                @SuppressWarnings("unchecked")
                E[] newElements = new Object[2 * elements.length];
                System.arraycopy(oldElements, 0, newElements, 0, size);
                elements = (E[]) newElements;
            }
        }
    }
    
    // FIXED: Proper cleanup
    static class FixedStack<E> {
        private E[] elements;
        private int size = 0;
        
        @SuppressWarnings("unchecked")
        public FixedStack(int initialCapacity) {
            elements = new Object[initialCapacity];
        }
        
        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }
        
        public E pop() {
            if (size == 0) {
                throw new EmptyStackException();
            }
            E result = elements[--size];
            elements[size] = null; // Clear reference - IMPORTANT!
            return result;
        }
        
        private void ensureCapacity() {
            if (elements.length == size) {
                Object[] oldElements = elements;
                @SuppressWarnings("unchecked")
                E[] newElements = new Object[2 * elements.length];
                System.arraycopy(oldElements, 0, newElements, 0, size);
                elements = (E[]) newElements;
            }
        }
    }
    
    public static void demonstrateStackLeak() {
        LeakyStack<String> leakyStack = new LeakyStack<>(10);
        
        for (int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 100000; j++) {
                sb.append("Data");
            }
            leakyStack.push(sb.toString());
        }
        
        // Pop items, but references persist in array
        while (leakyStack.size > 0) {
            leakyStack.pop(); // Strings leaked
        }
    }
}
```

### Pattern 2: Caching without Eviction

```java
public class CacheLeakPattern {
    
    // LEAK: Unbounded cache
    static class LeakyCache {
        private Map<String, byte[]> cache = new HashMap<>();
        
        public void cache(String key, byte[] value) {
            cache.put(key, value); // Grows indefinitely
        }
        
        public byte[] get(String key) {
            return cache.get(key);
        }
    }
    
    // FIXED: Bounded cache with eviction
    static class BoundedCache {
        private Map<String, byte[]> cache;
        private static final int MAX_SIZE = 1000;
        
        public BoundedCache() {
            cache = new LinkedHashMap<String, byte[]>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > MAX_SIZE;
                }
            };
        }
        
        public void cache(String key, byte[] value) {
            cache.put(key, value);
        }
        
        public byte[] get(String key) {
            return cache.get(key);
        }
        
        public int size() {
            return cache.size();
        }
    }
    
    // FIXED: WeakHashMap for automatic cleanup
    static class WeakCache {
        private Map<String, byte[]> cache = new WeakHashMap<>();
        
        public void cache(String key, byte[] value) {
            cache.put(key, value);
        }
        
        public byte[] get(String key) {
            return cache.get(key);
        }
    }
    
    public static void demonstrateCacheLeak() throws InterruptedException {
        System.out.println("=== Cache Leak Demonstration ===\n");
        
        // Leaky cache
        LeakyCache leaky = new LeakyCache();
        Runtime runtime = Runtime.getRuntime();
        
        for (int i = 0; i < 10000; i++) {
            leaky.cache("key-" + i, new byte[10240]); // 10KB each
            if (i % 2000 == 0) {
                long used = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Leaky cache size " + i + ": " + (used / 1024 / 1024) + " MB");
            }
        }
        
        System.out.println("\n=== Bounded Cache ===\n");
        
        // Bounded cache
        BoundedCache bounded = new BoundedCache();
        
        for (int i = 0; i < 10000; i++) {
            bounded.cache("key-" + i, new byte[10240]); // 10KB each
            if (i % 2000 == 0) {
                long used = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Bounded cache - entries: " + bounded.size() + 
                                 ", memory: " + (used / 1024 / 1024) + " MB");
            }
        }
    }
}
```

### Pattern 3: Listener/Observer Issues

```java
public class ListenerLeakPattern {
    
    static class EventSource {
        private List<EventListener> listeners = new ArrayList<>();
        
        public void addListener(EventListener listener) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }
        
        public void removeListener(EventListener listener) {
            listeners.remove(listener);
        }
        
        public void fireEvent(String event) {
            for (EventListener listener : new ArrayList<>(listeners)) {
                listener.onEvent(event);
            }
        }
        
        public int getListenerCount() {
            return listeners.size();
        }
    }
    
    interface EventListener {
        void onEvent(String event);
    }
    
    // LEAK: Listener not unregistered
    static class LeakyListener implements EventListener {
        private byte[] data = new byte[1024 * 1024]; // 1MB
        
        public void onEvent(String event) {
            System.out.println("Event: " + event);
        }
    }
    
    // FIXED: Weak listener reference
    static class WeakListenerWrapper implements EventListener {
        private WeakReference<EventListener> delegateRef;
        
        public WeakListenerWrapper(EventListener delegate) {
            this.delegateRef = new WeakReference<>(delegate);
        }
        
        public void onEvent(String event) {
            EventListener delegate = delegateRef.get();
            if (delegate != null) {
                delegate.onEvent(event);
            }
        }
    }
    
    // FIXED: Proper unregistration
    static class ManagedListener implements EventListener, AutoCloseable {
        private EventSource source;
        private byte[] data = new byte[1024 * 1024]; // 1MB
        
        public ManagedListener(EventSource source) {
            this.source = source;
            source.addListener(this);
        }
        
        public void onEvent(String event) {
            System.out.println("Event: " + event);
        }
        
        public void close() {
            source.removeListener(this);
        }
    }
    
    public static void demonstrateListenerLeak() throws InterruptedException {
        EventSource source = new EventSource();
        
        // Add many listeners (leak)
        for (int i = 0; i < 1000; i++) {
            source.addListener(new LeakyListener());
        }
        
        System.out.println("Listeners registered: " + source.getListenerCount());
        System.out.println("Memory held by listeners: ~1000 MB (leaked)");
        
        // Using try-with-resources for proper cleanup
        System.out.println("\n=== Managed Listeners ===");
        EventSource source2 = new EventSource();
        
        for (int i = 0; i < 10; i++) {
            ManagedListener listener = new ManagedListener(source2);
            listener.onEvent("test"); // Simulate event
            listener.close(); // Explicit cleanup
        }
        
        System.out.println("Listeners after cleanup: " + source2.getListenerCount());
    }
}
```

### Pattern 4: Thread and ThreadLocal Issues

```java
public class ThreadLocalLeakPattern {
    
    // LEAK: ThreadLocal holding large object
    static class ThreadPoolTask implements Runnable {
        // Shared across thread pool threads
        private static ThreadLocal<byte[]> buffer = 
            ThreadLocal.withInitial(() -> new byte[10 * 1024 * 1024]); // 10MB
        
        private String taskId;
        
        public ThreadPoolTask(String taskId) {
            this.taskId = taskId;
        }
        
        public void run() {
            byte[] buf = buffer.get(); // Obtain buffer
            processData(buf);
            // LEAK: buffer.remove() not called
            // In thread pool, thread is reused, ThreadLocal persists
        }
        
        private void processData(byte[] buf) {
            System.out.println("Processing task: " + taskId);
        }
    }
    
    // FIXED: Explicit cleanup
    static class CleanedThreadTask implements Runnable {
        private static ThreadLocal<byte[]> buffer = 
            ThreadLocal.withInitial(() -> new byte[10 * 1024 * 1024]); // 10MB
        
        private String taskId;
        
        public CleanedThreadTask(String taskId) {
            this.taskId = taskId;
        }
        
        public void run() {
            try {
                byte[] buf = buffer.get();
                processData(buf);
            } finally {
                buffer.remove(); // Explicit cleanup in finally
            }
        }
        
        private void processData(byte[] buf) {
            System.out.println("Processing task: " + taskId);
        }
    }
    
    // FIXED: Wrapper for automatic cleanup
    static class ManagedThreadLocal<T> {
        private ThreadLocal<T> delegate;
        
        public ManagedThreadLocal(Supplier<T> initializer) {
            this.delegate = ThreadLocal.withInitial(initializer);
        }
        
        public <V> V executeWithCleanup(Function<T, V> task) {
            try {
                T value = delegate.get();
                return task.apply(value);
            } finally {
                delegate.remove();
            }
        }
    }
    
    public static void demonstrateThreadLocalLeak() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        // Leaky version
        System.out.println("=== ThreadLocal Leak Demonstration ===");
        for (int i = 0; i < 20; i++) {
            executor.submit(new ThreadPoolTask("task-" + i));
        }
        
        Thread.sleep(1000);
        System.out.println("Memory still held by ThreadLocal in reused threads");
        
        executor.shutdown();
    }
}
```

## Detection Techniques

### Heap Dump Analysis

```java
public class MemoryLeakDetection {
    
    static class LeakingApplication {
        private static List<byte[]> leakingCollection = new ArrayList<>();
        
        public static void runWithLeak() throws InterruptedException {
            // Simulate memory leak
            for (int i = 0; i < 1000; i++) {
                leakingCollection.add(new byte[1024 * 1024]); // 1MB each
                Thread.sleep(100);
                
                if (i % 100 == 0) {
                    reportMemory(i);
                }
            }
        }
        
        private static void reportMemory(int iteration) {
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();
            
            System.out.printf("Iteration %d: Memory used: %d MB / %d MB (%.1f%%)%n",
                iteration,
                usedMemory / 1024 / 1024,
                maxMemory / 1024 / 1024,
                (usedMemory * 100.0) / maxMemory);
        }
    }
    
    // Programmatic leak detection
    static class LeakDetector {
        private List<MemorySnapshot> snapshots = new ArrayList<>();
        
        static class MemorySnapshot {
            long timestamp;
            long heapUsed;
            long heapFree;
            
            MemorySnapshot() {
                this.timestamp = System.currentTimeMillis();
                MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
                this.heapUsed = bean.getHeapMemoryUsage().getUsed();
                this.heapFree = bean.getHeapMemoryUsage().getCommitted() - heapUsed;
            }
        }
        
        public void recordSnapshot() {
            snapshots.add(new MemorySnapshot());
        }
        
        public void analyzeForLeak() {
            if (snapshots.size() < 2) return;
            
            List<MemorySnapshot> samples = snapshots.stream()
                .filter(s -> snapshots.indexOf(s) % 10 == 0) // Every 10th
                .collect(Collectors.toList());
            
            if (samples.size() >= 2) {
                MemorySnapshot first = samples.get(0);
                MemorySnapshot last = samples.get(samples.size() - 1);
                
                long memoryGrowth = last.heapUsed - first.heapUsed;
                long timeElapsed = last.timestamp - first.timestamp;
                
                if (memoryGrowth > 0) {
                    double growthRate = memoryGrowth / (double) timeElapsed;
                    
                    System.out.println("=== Memory Leak Analysis ===");
                    System.out.println("Memory growth: " + (memoryGrowth / 1024 / 1024) + " MB");
                    System.out.println("Time elapsed: " + (timeElapsed / 1000) + " seconds");
                    System.out.println("Growth rate: " + String.format("%.2f KB/sec", growthRate / 1024));
                    
                    if (growthRate > 100) { // >100 KB/sec is suspicious
                        System.out.println("WARNING: Possible memory leak detected!");
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        LeakDetector detector = new LeakDetector();
        
        // Record baseline
        detector.recordSnapshot();
        
        // Run potentially leaking code
        long startTime = System.currentTimeMillis();
        
        try {
            LeakingApplication.runWithLeak();
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError: " + e.getMessage());
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("\nElapsed time: " + ((endTime - startTime) / 1000) + " seconds");
        
        // Analyze
        detector.recordSnapshot();
        detector.analyzeForLeak();
    }
}
```

## Prevention Strategies

```java
public class LeakPreventionStrategies {
    
    // Strategy 1: Use WeakReference and WeakHashMap
    static class WeakReferenceExample {
        static class CacheEntry {
            String key;
            byte[] value;
            
            CacheEntry(String key, byte[] value) {
                this.key = key;
                this.value = value;
            }
        }
        
        private Map<String, WeakReference<CacheEntry>> cache = new HashMap<>();
        
        public void cache(String key, CacheEntry entry) {
            cache.put(key, new WeakReference<>(entry));
        }
        
        public CacheEntry get(String key) {
            WeakReference<CacheEntry> ref = cache.get(key);
            if (ref == null) return null;
            
            CacheEntry entry = ref.get();
            if (entry == null) {
                cache.remove(key); // Auto-cleanup if garbage collected
            }
            return entry;
        }
    }
    
    // Strategy 2: Try-with-resources for resource management
    static class ResourceManagement {
        interface Resource extends AutoCloseable {
            void use();
        }
        
        static class DataResource implements Resource {
            private byte[] buffer = new byte[10 * 1024 * 1024]; // 10MB
            
            public void use() {
                System.out.println("Using resource");
            }
            
            public void close() {
                // Cleanup
                buffer = null;
                System.out.println("Resource closed and cleaned up");
            }
        }
        
        public static void proper Usage() {
            try (Resource resource = new DataResource()) {
                resource.use();
            } // Automatically closed, no leak
        }
    }
    
    // Strategy 3: Copy-on-write for collections
    static class SafeListenerManagement {
        private CopyOnWriteArrayList<String> listeners = 
            new CopyOnWriteArrayList<>();
        
        public void addListener(String listener) {
            listeners.add(listener);
        }
        
        public void removeListener(String listener) {
            listeners.remove(listener);
        }
        
        public void notifyListeners(String event) {
            for (String listener : listeners) {
                System.out.println("Notifying: " + listener);
            }
        }
    }
    
    // Strategy 4: Nulling references explicitly
    static class ExplicitNulling {
        private Object obj = new Object();
        private List<Object> items = new ArrayList<>();
        
        public void cleanup() {
            items.clear(); // Clear collection
            for (int i = 0; i < items.size(); i++) {
                items.set(i, null); // Null individual items
            }
            obj = null; // Null field
        }
    }
    
    // Strategy 5: Listener with automatic unregistration
    static class AutoUnregisteringListener {
        private EventSource source;
        
        public AutoUnregisteringListener(EventSource source) {
            this.source = source;
            source.addListener(this);
            
            // Register shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                source.removeListener(AutoUnregisteringListener.this);
            }));
        }
    }
    
    // Dummy EventSource for compilation
    static class EventSource {
        void addListener(Object l) {}
        void removeListener(Object l) {}
    }
}
```

## Tools and Profilers

### JVisualVM Memory Profiling

```java
public class ProfilingExample {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== JVisualVM Profiling Example ===\n");
        System.out.println("Steps:");
        System.out.println("1. Start application with: java -Dcom.sun.management.jmxremote ProfilingExample");
        System.out.println("2. Open jvisualvm from JDK tools");
        System.out.println("3. Connect to this process");
        System.out.println("4. Go to Memory tab to see heap usage");
        System.out.println("5. Create heap dump by clicking button\n");
        
        // Create memory pressure
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            data.add("Data item: " + i);
            if (i % 100000 == 0) {
                System.out.println("Allocated " + i + " items");
                Thread.sleep(100);
            }
        }
        
        System.out.println("Press Ctrl+C to exit and analyze heap dumps");
        Thread.sleep(Long.MAX_VALUE);
    }
}
```

### JFR (Java Flight Recorder)

```bash
# Start recording
java -XX:StartFlightRecording=filename=recording.jfr,duration=10s MyApp

# Analyze with jfr command
jfr dump recording.jfr

# Or use JDK Mission Control GUI
jmc
```

## Real-World Examples

```java
public class RealWorldLeakExamples {
    
    // Example 1: Servlet storing user data globally
    static class LeakyServlet {
        private static Map<String, UserSession> sessions = new HashMap<>();
        
        public void doGet(String userId) {
            UserSession session = new UserSession(userId);
            sessions.put(userId, session); // Never cleaned up
        }
        
        static class UserSession {
            String userId;
            byte[] largeUserData;
            
            UserSession(String userId) {
                this.userId = userId;
                this.largeUserData = new byte[1024 * 1024]; // 1MB
            }
        }
    }
    
    // Example 2: Framework holding onto listeners
    static class EventFrameworkLeak {
        private static EventListener singletonListener;
        
        public static void registerFramework(EventListener listener) {
            singletonListener = listener; // Never cleared
        }
    }
    
    // Example 3: Caching query results indefinitely
    static class QueryCacheLeak {
        private static Map<String, QueryResult> cache = new HashMap<>();
        
        public static QueryResult executeQuery(String sql) {
            if (!cache.containsKey(sql)) {
                QueryResult result = new QueryResult(sql);
                cache.put(sql, result); // No expiration
            }
            return cache.get(sql);
        }
        
        static class QueryResult {
            byte[] resultData;
            
            QueryResult(String sql) {
                this.resultData = new byte[10 * 1024 * 1024]; // 10MB
            }
        }
    }
}
```

## Best Practices

```java
public class MemoryLeakBestPractices {
    
    // 1. Use appropriate data structures
    class BestPracticeDataStructures {
        // Don't: List growing unbounded
        // Do: Use bounded queue
        private Queue<String> bounded = new LinkedList<String>() {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 1000;
            }
        };
        
        // Or: Use CacheBuilder from Guava
        // Cache<String, String> cache = CacheBuilder.newBuilder()
        //     .expireAfterWrite(10, TimeUnit.MINUTES)
        //     .maximumSize(1000)
        //     .build();
    }
    
    // 2. Always implement resource cleanup
    class ProperResourceHandling {
        void goodExample() throws IOException {
            try (FileInputStream fis = new FileInputStream("file.txt")) {
                // Use resource
            } // Automatically closed
        }
        
        void betterExample() throws IOException {
            FileInputStream fis = new FileInputStream("file.txt");
            try {
                // Use resource
            } finally {
                fis.close(); // Guaranteed cleanup
            }
        }
    }
    
    // 3. Be careful with static references
    class StaticReferenceWarning {
        // Don't: Growing static collection
        // private static List<Object> globalCache = new ArrayList<>();
        
        // Do: Use lazy initialization and limits
        private static List<Object> limitedCache = new ArrayList<Object>() {
            public boolean add(Object o) {
                if (size() >= 1000) {
                    remove(0); // Remove oldest
                }
                return super.add(o);
            }
        };
    }
    
    // 4. Properly manage listeners
    class ListenerManagement {
        private List<PropertyChangeListener> listeners = new ArrayList<>();
        
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            listeners.add(listener);
        }
        
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            listeners.remove(listener);
        }
    }
    
    // 5. Monitor memory regularly
    class MemoryMonitoring {
        void logMemoryUsage() {
            MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heap = bean.getHeapMemoryUsage();
            
            System.out.printf("Heap: %d / %d MB (%.1f%%)%n",
                heap.getUsed() / 1024 / 1024,
                heap.getMax() / 1024 / 1024,
                (heap.getUsed() * 100.0) / heap.getMax());
        }
    }
}
```

## Conclusion

Memory leaks in Java are subtle but serious issues that can degrade application performance and cause crashes in production. While automatic garbage collection prevents many types of leaks, developers must understand common leak patterns and use proper prevention strategies.

Key takeaways:
- Understand reachability and how garbage collection works
- Be cautious with static references, caches, and listeners
- Always clean up resources using try-with-resources or finally blocks
- Use weak references and collections when appropriate
- Monitor memory usage in production
- Use profiling tools to detect leaks early
- Follow best practices for resource management

Regular testing, monitoring, and proactive leak detection enable robust, long-running Java applications.
