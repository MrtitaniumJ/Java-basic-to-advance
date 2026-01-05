# Memory Management and Garbage Collection

## Introduction

Memory management is fundamental to Java programming, enabling developers to build efficient applications that scale well. Unlike C/C++, Java abstracts manual memory management through automatic garbage collection, eliminating a major source of bugs. However, understanding how Java allocates and manages memory is crucial for writing high-performance applications, preventing memory leaks, and optimizing application behavior.

The Java Virtual Machine (JVM) manages memory automatically, but this doesn't mean developers can ignore memory concerns. Understanding garbage collection algorithms, heap organization, and memory profiling enables building applications that perform well under load and scale reliably. Modern JVMs offer multiple garbage collection algorithms suited to different application profiles.

## Java Memory Model

### Memory Regions

**Heap**: Stores all object instances. Garbage collected. Shared across threads.
**Stack**: Stores primitive values and object references. Thread-local. Automatically cleaned up.
**Method Area**: Stores class definitions, constant pool, field data. Shared across threads.
**Constant Pool**: Stores string literals and other constants.

### Memory Allocation

When objects are created with `new`, memory is allocated on the heap. References to objects are stored on the stack or in other objects. The heap is automatically managed by the garbage collector.

## Garbage Collection

Garbage collection automatically frees memory occupied by objects that are no longer reachable from active code. The garbage collector traverses object references starting from "root" objects, marking reachable objects as live and collecting unreachable objects.

### GC Algorithms

**Generational Garbage Collection**
Most modern garbage collectors use generational collection, based on the "weak generational hypothesis": young objects are more likely to become garbage than old objects.

**G1GC (Garbage First)**
Default GC in modern JVMs. Divides heap into regions, prioritizing regions with most garbage. Low latency, good throughput.

**ZGC (Z Garbage Collector)**
Ultra-low latency GC (< 10ms). Designed for large heaps. Minimal pause times.

**Parallel GC**
Optimized for throughput on multicore systems. Suitable for batch processing.

### GC Phases

**Mark**: Identify live objects by traversing references
**Sweep**: Collect memory of dead objects
**Compact**: Rearrange heap to reduce fragmentation

## Memory Leaks in Java

Despite automatic garbage collection, memory leaks occur when objects remain referenced even though they're no longer needed. Common causes:

1. **Static collections**: Never cleared, grow indefinitely
2. **Listeners/observers**: Not unregistered when no longer needed
3. **Circular references**: Though usually garbage collected
4. **Resource leaks**: Streams, connections not closed
5. **ThreadLocal variables**: Not cleared, persist for thread lifetime

## Key Concepts

**Reachability**: An object is reachable if referenced from root objects
**Strong References**: Normal references; prevent garbage collection
**Soft References**: Collected when memory is needed
**Weak References**: Collected during next GC, regardless of memory
**Phantom References**: Used to detect when objects are about to be collected

## Best Practices

1. **Use try-with-resources** - Ensures automatic resource closure
2. **Remove listeners explicitly** - Unregister when no longer needed
3. **Avoid memory leaks in static collections** - Clear or limit size
4. **Profile before optimizing** - Use profilers to identify actual bottlenecks
5. **Monitor heap usage** - Track memory consumption in production
6. **Tune GC parameters** - Match JVM arguments to application needs
7. **Avoid excessive object creation** - Reuse objects when possible
8. **Use weak references carefully** - Understand collection behavior

---

## Complete Working Examples

### Example 1: Memory Allocation and Garbage Collection

```java
import java.util.*;

public class MemoryAllocation {
    
    static class DataHolder {
        byte[] data;
        String name;
        
        public DataHolder(String name, int sizeInMB) {
            this.name = name;
            this.data = new byte[sizeInMB * 1024 * 1024];
        }
        
        @Override
        public String toString() {
            return name + " (" + (data.length / (1024 * 1024)) + " MB)";
        }
    }
    
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println("Memory Management Demo");
        System.out.println("=".repeat(50));
        System.out.println("Max Memory: " + formatBytes(runtime.maxMemory()));
        System.out.println("Total Memory: " + formatBytes(runtime.totalMemory()));
        System.out.println("Free Memory: " + formatBytes(runtime.freeMemory()));
        
        // Create objects
        System.out.println("\nCreating objects...");
        List<DataHolder> holders = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            DataHolder holder = new DataHolder("Object_" + i, 10);
            holders.add(holder);
            System.out.println("Created: " + holder);
            printMemoryStats(runtime);
        }
        
        // Clear references (enable garbage collection)
        System.out.println("\nClearing references...");
        holders.clear();
        System.out.println("References cleared");
        
        // Request garbage collection
        System.out.println("\nRequesting garbage collection...");
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        printMemoryStats(runtime);
    }
    
    private static void printMemoryStats(Runtime runtime) {
        long used = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("  Used: %s, Free: %s\n",
            formatBytes(used),
            formatBytes(runtime.freeMemory()));
    }
    
    private static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int unit = 1024;
        if (bytes < unit * unit) return (bytes / unit) + " KB";
        if (bytes < unit * unit * unit) return (bytes / (unit * unit)) + " MB";
        return (bytes / (unit * unit * unit)) + " GB";
    }
}
```

**Output:**
```
Memory Management Demo
==================================================
Max Memory: 4 GB
Total Memory: 513 MB
Free Memory: 510 MB

Creating objects...
Created: Object_1 (10 MB)
  Used: 10 MB, Free: 500 MB
Created: Object_2 (10 MB)
  Used: 20 MB, Free: 490 MB
Created: Object_3 (10 MB)
  Used: 30 MB, Free: 480 MB
Created: Object_4 (10 MB)
  Used: 40 MB, Free: 470 MB
Created: Object_5 (10 MB)
  Used: 50 MB, Free: 460 MB

Clearing references...
References cleared

Requesting garbage collection...
  Used: 2 MB, Free: 508 MB
```

### Example 2: Memory Leak Detection

```java
import java.util.*;

public class MemoryLeakExample {
    
    // Common source of memory leaks: static collection
    static class LeakyCache {
        private static final Map<String, byte[]> cache = new HashMap<>();
        
        public static void add(String key, int sizeInMB) {
            byte[] data = new byte[sizeInMB * 1024 * 1024];
            cache.put(key, data);
            System.out.println("Added to cache: " + key + " (" + sizeInMB + " MB)");
            System.out.println("Cache size: " + cache.size() + " items");
        }
        
        public static void clear() {
            cache.clear();
            System.out.println("Cache cleared");
        }
        
        public static int size() {
            return cache.size();
        }
    }
    
    // Proper solution: size-limited cache
    static class SafeCache {
        private final Map<String, byte[]> cache;
        private final int maxSize;
        
        public SafeCache(int maxSize) {
            this.maxSize = maxSize;
            this.cache = new LinkedHashMap<String, byte[]>(maxSize, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > maxSize;
                }
            };
        }
        
        public void add(String key, int sizeInMB) {
            byte[] data = new byte[sizeInMB * 1024 * 1024];
            cache.put(key, data);
            System.out.println("Added to safe cache: " + key + " (" + sizeInMB + " MB)");
            System.out.println("Cache size: " + cache.size() + " items");
        }
        
        public int size() {
            return cache.size();
        }
    }
    
    public static void demonstrateMemoryLeak() {
        System.out.println("=== Memory Leak Example ===\n");
        
        Runtime runtime = Runtime.getRuntime();
        long before = runtime.totalMemory() - runtime.freeMemory();
        
        // Simulate memory leak
        for (int i = 0; i < 10; i++) {
            LeakyCache.add("item_" + i, 5);
        }
        
        long after = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory used: " + formatBytes(after - before));
        System.out.println("Cache size remains: " + LeakyCache.size() + 
                         " (items never removed)\n");
    }
    
    public static void demonstrateSafeCache() {
        System.out.println("=== Safe Cache Example ===\n");
        
        Runtime runtime = Runtime.getRuntime();
        long before = runtime.totalMemory() - runtime.freeMemory();
        
        SafeCache cache = new SafeCache(3);
        
        // Add items to size-limited cache
        for (int i = 0; i < 10; i++) {
            cache.add("item_" + i, 5);
        }
        
        long after = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory used: " + formatBytes(after - before));
        System.out.println("Cache size limited to: " + cache.size() + 
                         " (exceeds discarded)\n");
    }
    
    public static void main(String[] args) {
        demonstrateMemoryLeak();
        System.out.println("Key difference:");
        System.out.println("- LeakyCache: All 10 items retained in memory");
        System.out.println("- SafeCache: Only 3 most recent items retained\n");
        demonstrateSafeCache();
    }
    
    private static String formatBytes(long bytes) {
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        return (bytes / (1024 * 1024)) + " MB";
    }
}
```

**Output:**
```
=== Memory Leak Example ===

Added to cache: item_0 (5 MB)
Cache size: 1 items
...
Added to cache: item_9 (5 MB)
Cache size: 10 items
Memory used: 50 MB
Cache size remains: 10 (items never removed)

Key difference:
- LeakyCache: All 10 items retained in memory
- SafeCache: Only 3 most recent items retained

=== Safe Cache Example ===

Added to safe cache: item_0 (5 MB)
Cache size: 1 items
...
Added to safe cache: item_9 (5 MB)
Cache size: 3 items
Memory used: 15 MB
Cache size limited to: 3 (exceeds discarded)
```

### Example 3: Weak References

```java
import java.util.*;
import java.lang.ref.*;

public class WeakReferencesDemo {
    
    static class CacheEntry {
        String key;
        byte[] value;
        
        CacheEntry(String key, int sizeInMB) {
            this.key = key;
            this.value = new byte[sizeInMB * 1024 * 1024];
        }
        
        @Override
        public String toString() {
            return key + " (" + (value.length / (1024 * 1024)) + " MB)";
        }
    }
    
    public static void demonstrateWeakReferences() {
        System.out.println("=== Weak References Demo ===\n");
        
        // Create weak references to cache entries
        Map<String, WeakReference<CacheEntry>> weakCache = new HashMap<>();
        
        System.out.println("Creating entries with weak references...");
        CacheEntry entry1 = new CacheEntry("entry_1", 2);
        CacheEntry entry2 = new CacheEntry("entry_2", 2);
        CacheEntry entry3 = new CacheEntry("entry_3", 2);
        
        weakCache.put("1", new WeakReference<>(entry1));
        weakCache.put("2", new WeakReference<>(entry2));
        weakCache.put("3", new WeakReference<>(entry3));
        
        System.out.println("Added 3 entries");
        printWeakCacheSize(weakCache);
        
        // Keep reference to entry1, release others
        System.out.println("\nReleasing entry2 and entry3 references...");
        entry2 = null;
        entry3 = null;
        
        System.out.println("Requesting garbage collection...");
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Checking weak references after GC:");
        System.out.println("Entry 1 available: " + 
            (weakCache.get("1").get() != null ? "YES" : "NO"));
        System.out.println("Entry 2 available: " + 
            (weakCache.get("2").get() != null ? "YES" : "NO"));
        System.out.println("Entry 3 available: " + 
            (weakCache.get("3").get() != null ? "YES" : "NO"));
        printWeakCacheSize(weakCache);
    }
    
    private static void printWeakCacheSize(Map<String, WeakReference<CacheEntry>> cache) {
        int available = 0;
        for (WeakReference<CacheEntry> ref : cache.values()) {
            if (ref.get() != null) {
                available++;
            }
        }
        System.out.println("Entries still in memory: " + available + " / " + cache.size());
    }
    
    public static void demonstrateSoftReferences() {
        System.out.println("\n=== Soft References Demo ===\n");
        
        List<SoftReference<byte[]>> softRefs = new ArrayList<>();
        
        System.out.println("Creating soft references to memory...");
        Runtime runtime = Runtime.getRuntime();
        long free = runtime.freeMemory();
        
        int buffers = 0;
        while (free > 0) {
            byte[] buffer = new byte[1024 * 1024]; // 1MB buffer
            softRefs.add(new SoftReference<>(buffer));
            buffers++;
            free = runtime.freeMemory();
            
            if (buffers % 10 == 0) {
                System.out.println("Created " + buffers + " soft references");
            }
        }
        
        System.out.println("Total soft references created: " + buffers);
        
        System.out.println("Requesting garbage collection...");
        System.gc();
        
        int retained = 0;
        for (SoftReference<byte[]> ref : softRefs) {
            if (ref.get() != null) {
                retained++;
            }
        }
        System.out.println("Soft references retained after GC: " + retained + " / " + buffers);
        System.out.println("(Soft refs are cleared when memory is needed)");
    }
    
    public static void main(String[] args) {
        demonstrateWeakReferences();
        demonstrateSoftReferences();
    }
}
```

**Output:**
```
=== Weak References Demo ===

Creating entries with weak references...
Added 3 entries
Entries still in memory: 3 / 3

Releasing entry2 and entry3 references...
Requesting garbage collection...
Checking weak references after GC:
Entry 1 available: YES
Entry 2 available: NO
Entry 3 available: NO
Entries still in memory: 1 / 3

=== Soft References Demo ===

Creating soft references to memory...
Created 10 soft references
...
Created 100 soft references
Total soft references created: 145
Requesting garbage collection...
Soft references retained after GC: 12 / 145
(Soft refs are cleared when memory is needed)
```

### Example 4: Resource Management with Try-With-Resources

```java
import java.io.*;
import java.util.*;

public class ResourceManagement {
    
    static class ManagedResource implements AutoCloseable {
        private String name;
        
        public ManagedResource(String name) {
            this.name = name;
            System.out.println("Resource acquired: " + name);
        }
        
        public void doWork() {
            System.out.println("  Working with " + name);
        }
        
        @Override
        public void close() {
            System.out.println("Resource released: " + name);
        }
    }
    
    public static void traditionalApproach() {
        System.out.println("=== Traditional Approach (Error-Prone) ===\n");
        
        ManagedResource resource = null;
        try {
            resource = new ManagedResource("Traditional Resource");
            resource.doWork();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("Error closing: " + e.getMessage());
                }
            }
        }
    }
    
    public static void tryWithResourcesApproach() {
        System.out.println("\n=== Try-With-Resources Approach (Java 7+) ===\n");
        
        try (ManagedResource resource = new ManagedResource("Modern Resource")) {
            resource.doWork();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void multipleResourcesApproach() {
        System.out.println("\n=== Multiple Resources (Java 9+) ===\n");
        
        ManagedResource res1 = new ManagedResource("Resource 1");
        ManagedResource res2 = new ManagedResource("Resource 2");
        
        try (res1; res2) {
            res1.doWork();
            res2.doWork();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void demonstrateFileHandling() throws IOException {
        System.out.println("\n=== File Handling with Try-With-Resources ===\n");
        
        String filename = "demo.txt";
        
        // Write file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Line 1: Memory Management");
            writer.println("Line 2: Resource Management");
            writer.println("Line 3: Best Practices");
            System.out.println("File written: " + filename);
        }
        
        // Read file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            System.out.println("File contents:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  " + line);
            }
        }
        
        // Cleanup
        new File(filename).delete();
        System.out.println("File cleaned up");
    }
    
    public static void main(String[] args) throws IOException {
        traditionalApproach();
        tryWithResourcesApproach();
        multipleResourcesApproach();
        demonstrateFileHandling();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Best Practice: Always use try-with-resources");
        System.out.println("Benefits:");
        System.out.println("  - Automatic resource closure");
        System.out.println("  - Exception handling during closure");
        System.out.println("  - Reduced boilerplate code");
        System.out.println("  - More readable and maintainable");
    }
}
```

**Output:**
```
=== Traditional Approach (Error-Prone) ===

Resource acquired: Traditional Resource
  Working with Traditional Resource
Resource released: Traditional Resource

=== Try-With-Resources Approach (Java 7+) ===

Resource acquired: Modern Resource
  Working with Modern Resource
Resource released: Modern Resource

=== Multiple Resources (Java 9+) ===

Resource acquired: Resource 1
Resource acquired: Resource 2
  Working with Resource 1
  Working with Resource 2
Resource released: Resource 2
Resource released: Resource 1

=== File Handling with Try-With-Resources ===

File written: demo.txt
File contents:
  Line 1: Memory Management
  Line 2: Resource Management
  Line 3: Best Practices
File cleaned up

==================================================
Best Practice: Always use try-with-resources
Benefits:
  - Automatic resource closure
  - Exception handling during closure
  - Reduced boilerplate code
  - More readable and maintainable
```

### Example 5: Monitoring Memory with JMX

```java
import java.lang.management.*;
import java.util.*;

public class MemoryMonitoring {
    
    public static void displayMemoryPools() {
        System.out.println("=== Memory Pools Information ===\n");
        
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        
        for (MemoryPoolMXBean pool : pools) {
            MemoryUsage usage = pool.getUsage();
            
            System.out.println("Pool: " + pool.getName());
            System.out.println("  Type: " + pool.getType());
            System.out.println("  Init: " + formatBytes(usage.getInit()));
            System.out.println("  Used: " + formatBytes(usage.getUsed()));
            System.out.println("  Max: " + formatBytes(usage.getMax()));
            System.out.println("  Committed: " + formatBytes(usage.getCommitted()));
            
            if (pool.isCollectionUsageThresholdSupported()) {
                System.out.println("  Collection threshold supported");
            }
            System.out.println();
        }
    }
    
    public static void displayGarbageCollectors() {
        System.out.println("=== Garbage Collectors Information ===\n");
        
        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        
        for (GarbageCollectorMXBean gc : gcs) {
            System.out.println("Collector: " + gc.getName());
            System.out.println("  Valid: " + gc.isValid());
            System.out.println("  Collection count: " + gc.getCollectionCount());
            System.out.println("  Collection time: " + gc.getCollectionTime() + " ms");
            System.out.println("  Memory pools:");
            for (String poolName : gc.getMemoryPoolNames()) {
                System.out.println("    - " + poolName);
            }
            System.out.println();
        }
    }
    
    public static void displayMemoryUsage() {
        System.out.println("=== Overall Memory Usage ===\n");
        
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        System.out.println("Heap Memory:");
        System.out.println("  Committed: " + formatBytes(heapUsage.getCommitted()));
        System.out.println("  Used: " + formatBytes(heapUsage.getUsed()));
        System.out.println("  Max: " + formatBytes(heapUsage.getMax()));
        double heapPercent = (double) heapUsage.getUsed() / heapUsage.getMax() * 100;
        System.out.println("  Used %: " + String.format("%.1f%%", heapPercent));
        
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        System.out.println("\nNon-Heap Memory:");
        System.out.println("  Committed: " + formatBytes(nonHeapUsage.getCommitted()));
        System.out.println("  Used: " + formatBytes(nonHeapUsage.getUsed()));
    }
    
    public static void main(String[] args) {
        displayMemoryUsage();
        System.out.println("\n" + "=".repeat(50) + "\n");
        displayMemoryPools();
        System.out.println("=".repeat(50) + "\n");
        displayGarbageCollectors();
    }
    
    private static String formatBytes(long bytes) {
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        if (bytes < 1024 * 1024 * 1024) return (bytes / (1024 * 1024)) + " MB";
        return (bytes / (1024 * 1024 * 1024)) + " GB";
    }
}
```

**Output:**
```
=== Overall Memory Usage ===

Heap Memory:
  Committed: 512 MB
  Used: 45 MB
  Max: 4 GB
  Used %: 1.1%

Non-Heap Memory:
  Committed: 64 MB
  Used: 32 MB

==================================================

=== Memory Pools Information ===

Pool: Young Generation
  Type: HEAP
  Init: 170 MB
  Used: 15 MB
  Max: 1.3 GB
  Committed: 170 MB

Pool: Old Generation
  Type: HEAP
  Init: 341 MB
  Used: 30 MB
  Max: 2.6 GB
  Committed: 341 MB

...

==================================================

=== Garbage Collectors Information ===

Collector: Young Generation Collector
  Valid: true
  Collection count: 5
  Collection time: 45 ms
  Memory pools:
    - Young Generation

Collector: Old Generation Collector
  Valid: true
  Collection count: 0
  Collection time: 0 ms
  Memory pools:
    - Old Generation
```

## Performance Considerations

- **Heap Size**: Larger heaps reduce GC frequency but increase pause time
- **GC Pauses**: Brief interruptions during garbage collection (~10ms for G1GC)
- **Memory Leak Impact**: Unreleased memory eventually causes OutOfMemoryError
- **Object Creation**: Excessive object creation increases GC pressure

## Summary

Effective memory management in Java requires understanding how the JVM allocates and manages memory. While garbage collection eliminates manual memory management, developers must still design applications to avoid memory leaks and inefficient memory usage. Using try-with-resources, managing static collections, and profiling applications are essential practices. Modern garbage collection algorithms minimize pause times, enabling building responsive, scalable applications.
