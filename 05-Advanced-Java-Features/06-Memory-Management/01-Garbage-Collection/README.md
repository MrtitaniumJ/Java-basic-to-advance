# Garbage Collection in Java

## Table of Contents
1. [Introduction to Garbage Collection](#introduction-to-garbage-collection)
2. [Memory Structure and Heap Organization](#memory-structure-and-heap-organization)
3. [Garbage Collection Algorithms](#garbage-collection-algorithms)
4. [Generational Hypothesis](#generational-hypothesis)
5. [G1GC and Modern GC](#g1gc-and-modern-gc)
6. [GC Tuning and Optimization](#gc-tuning-and-optimization)
7. [Monitoring and Analysis Tools](#monitoring-and-analysis-tools)
8. [Best Practices](#best-practices)

## Introduction to Garbage Collection

Garbage Collection (GC) is an automatic memory management mechanism in Java that identifies and removes objects that are no longer reachable from the application. Unlike languages like C and C++, Java developers don't manually allocate and deallocate memory, significantly reducing programming errors and improving security.

The JVM maintains a heap where all objects are allocated. When the heap becomes full, the garbage collector runs to identify and reclaim memory occupied by unreachable objects. This process ensures efficient memory usage and prevents memory exhaustion in long-running applications.

**Key Concepts:**
- **Reachability**: An object is reachable if it can be accessed through references from the root set (stack variables, static variables, etc.)
- **Mark and Sweep**: Two-phase algorithm where live objects are marked and unreachable objects are swept
- **Generational Collection**: Objects are organized by age to optimize collection frequency
- **STW (Stop-The-World)**: Pause where application threads stop during GC

## Memory Structure and Heap Organization

The JVM memory is divided into several regions:

```
Heap Memory:
├── Young Generation
│   ├── Eden Space (largest)
│   ├── Survivor Space 0
│   └── Survivor Space 1
└── Old Generation

Stack Memory:
├── Local Variables
└── Method References

Permanent/Metaspace:
├── Class Definitions
└── Method Code
```

**Young Generation (Nursery):**
- Default: ~1/3 of heap size
- Most objects are allocated here
- Fast collection cycle (minor GC)
- High collection frequency

**Old Generation (Tenured):**
- Default: ~2/3 of heap size
- Long-lived objects promoted from Young
- Slower collection cycle (major/full GC)
- Lower collection frequency

## Garbage Collection Algorithms

### 1. Mark-Sweep Algorithm

The basic GC algorithm with two phases:

```java
// Example demonstrating Mark-Sweep concept
public class MarkSweepDemo {
    
    // Simplified representation of object graph
    static class Node {
        String name;
        Node next;
        Object data;
        
        Node(String name) {
            this.name = name;
            this.data = new byte[1024]; // 1KB data
        }
    }
    
    public static void main(String[] args) {
        // Create object graph
        Node head = new Node("Node1");
        head.next = new Node("Node2");
        head.next.next = new Node("Node3");
        
        // Simulate unreachable objects
        Node temp = head.next;
        head.next = null; // Node2 and Node3 become unreachable
        
        System.out.println("Before GC: Objects created");
        System.out.println("Reachable from head: " + traverseNodes(head));
        
        // Trigger garbage collection
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("After GC: Unreachable objects collected");
    }
    
    static int traverseNodes(Node head) {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}
```

**Algorithm Steps:**
1. **Mark Phase**: Traverse from roots, marking all reachable objects
2. **Sweep Phase**: Scan heap, deallocating unmarked objects
3. **Compact Phase**: Rearrange surviving objects (optional, reduces fragmentation)

### 2. Copying Algorithm (Young Generation)

Optimized for high mortality rate of young objects:

```java
public class CopyingAlgorithmDemo {
    
    static class MemoryRegion {
        int[] data;
        String id;
        
        MemoryRegion(String id, int size) {
            this.id = id;
            this.data = new int[size];
        }
    }
    
    public static void demonstrateCopying() {
        // Eden space allocation
        MemoryRegion[] edenObjects = new MemoryRegion[100];
        for (int i = 0; i < 100; i++) {
            edenObjects[i] = new MemoryRegion("eden-" + i, 1024);
        }
        
        // Simulate reaching survivor threshold (age = 15 by default)
        MemoryRegion promoted = edenObjects[0];
        
        // Other Eden objects become unreachable
        edenObjects = null;
        
        // During Minor GC:
        // 1. Live objects in Eden copied to Survivor Space
        // 2. Survived objects aged and moved between survivor spaces
        // 3. Objects reaching age threshold promoted to Old Gen
        
        System.out.println("Promoted object: " + promoted.id);
    }
    
    public static void main(String[] args) {
        demonstrateCopying();
    }
}
```

### 3. Generational Collection Algorithm

```java
public class GenerationalGCDemo {
    
    static class YoungGenObject {
        byte[] data;
        
        YoungGenObject(int size) {
            this.data = new byte[size];
        }
    }
    
    static class OldGenObject {
        byte[] data;
        long creationTime;
        
        OldGenObject(int size) {
            this.data = new byte[size];
            this.creationTime = System.currentTimeMillis();
        }
    }
    
    public static void demonstrateGenerations() {
        List<OldGenObject> oldGen = new ArrayList<>();
        
        // Phase 1: Create many short-lived objects in Young Gen
        for (int cycle = 0; cycle < 10; cycle++) {
            List<YoungGenObject> youngGen = new ArrayList<>();
            
            // Allocate young generation objects
            for (int i = 0; i < 1000; i++) {
                youngGen.add(new YoungGenObject(1024)); // 1KB each
            }
            
            // Minor GC triggered here (Young Gen collection)
            // Most young objects discarded, survivors promoted
            
            // A few objects survive and get promoted to Old Gen
            if (cycle % 3 == 0) {
                oldGen.add(new OldGenObject(10240)); // 10KB each
            }
            
            // Young gen objects go out of scope
            youngGen.clear();
        }
        
        System.out.println("Young Gen objects: Collected frequently (minor GC)");
        System.out.println("Old Gen objects: " + oldGen.size() + 
                         " (collected infrequently, full/major GC)");
        
        // Major GC triggered when Old Gen is full
        // More expensive than minor GC
    }
    
    public static void main(String[] args) {
        demonstrateGenerations();
    }
}
```

## Generational Hypothesis

The Generational Hypothesis is the foundation of modern garbage collection:

**Two Key Observations:**
1. **Weak Generational Hypothesis**: Most objects die young (short-lived)
2. **Strong Generational Hypothesis**: Old objects rarely reference young objects

**Implications:**
- Young generation needs frequent collection
- Old generation needs infrequent collection
- Very efficient for typical Java applications

```java
public class GenerationalHypothesisDemo {
    
    static class ObjectWithAge {
        static int nextId = 0;
        int id;
        int age = 0;
        byte[] data;
        ObjectWithAge reference;
        
        ObjectWithAge(int size) {
            this.id = nextId++;
            this.data = new byte[size];
        }
        
        void ageSurvival() {
            this.age++;
        }
    }
    
    public static void demonstrateHypothesis() {
        // Most young objects (age 0-3) are short-lived
        List<ObjectWithAge> ephemeralObjects = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            ephemeralObjects.add(new ObjectWithAge(256));
        }
        
        // Minor GC: Most of these are collected
        // Roughly 98% mortality rate
        ephemeralObjects.clear();
        
        // Long-lived objects survive multiple GC cycles
        List<ObjectWithAge> persistentObjects = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ObjectWithAge obj = new ObjectWithAge(1024);
            persistentObjects.add(obj);
        }
        
        // These objects survive young gen and get promoted
        // Will remain in old generation for application lifetime
        
        System.out.println("Ephemeral objects: ~10000 (99% collected in minor GC)");
        System.out.println("Persistent objects: " + persistentObjects.size() + 
                         " (promoted to old generation)");
    }
    
    public static void main(String[] args) {
        demonstrateHypothesis();
    }
}
```

## G1GC and Modern GC

G1GC (Garbage First) represents the modern approach:

```java
public class G1GCDemo {
    
    static class RegionObject {
        int regionId;
        byte[] data;
        
        RegionObject(int regionId, int size) {
            this.regionId = regionId;
            this.data = new byte[size];
        }
    }
    
    public static void demonstrateG1GC() {
        // G1GC divides heap into equal-sized regions
        // Each region can be Eden, Survivor, or Old
        
        List<RegionObject> managedObjects = new ArrayList<>();
        
        // Allocate objects across multiple regions
        for (int region = 0; region < 32; region++) {
            for (int i = 0; i < 100; i++) {
                managedObjects.add(new RegionObject(region, 4096)); // 4KB each
            }
        }
        
        // G1GC advantages:
        // 1. Predictable pause times (target: 200ms)
        // 2. Better memory compaction
        // 3. More flexible region allocation
        // 4. Parallel and concurrent collection
        
        System.out.println("G1GC manages " + managedObjects.size() + 
                         " objects across multiple regions");
        System.out.println("Expected pause time: ~200ms");
    }
    
    public static void main(String[] args) {
        demonstrateG1GC();
    }
}
```

## GC Tuning and Optimization

```java
public class GCTuningDemo {
    
    static class DataContainer {
        byte[] data;
        long timestamp;
        
        DataContainer(int size) {
            this.data = new byte[size];
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    public static void demonstrateGCBehavior() throws InterruptedException {
        // Key tuning parameters:
        // -Xms: Initial heap size
        // -Xmx: Maximum heap size
        // -XX:NewRatio=2: Old Gen to Young Gen ratio (2:1)
        // -XX:SurvivorRatio=8: Eden to Survivor ratio (8:1)
        // -XX:MaxTenuringThreshold=15: Age before promotion
        
        Runtime runtime = Runtime.getRuntime();
        long beforeGC = runtime.totalMemory() - runtime.freeMemory();
        
        // Create objects for young generation collection
        List<DataContainer> containers = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            containers.add(new DataContainer(1024));
        }
        
        long heapUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Heap used before GC: " + (heapUsed / 1024 / 1024) + " MB");
        
        // Clear references to trigger GC
        containers.clear();
        
        // Suggest GC
        System.gc();
        Thread.sleep(100);
        
        long afterGC = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Heap used after GC: " + (afterGC / 1024 / 1024) + " MB");
        System.out.println("Memory reclaimed: " + 
                         ((heapUsed - afterGC) / 1024 / 1024) + " MB");
    }
    
    public static void main(String[] args) throws InterruptedException {
        demonstrateGCBehavior();
    }
}
```

### Tuning Strategies

**1. Heap Size Configuration:**
- Set `-Xms` and `-Xmx` to same value (avoid dynamic resizing)
- Allocate 70-80% of available system memory
- Leave 20-30% for OS and other processes

**2. Generation Size Tuning:**
- Young generation: 25-30% for most applications
- Adjust `-XX:NewRatio` based on allocation rate
- Monitor minor GC frequency

**3. GC Algorithm Selection:**
```
-XX:+UseSerialGC       # Single-threaded (small apps)
-XX:+UseParallelGC     # Multi-threaded (batch processing)
-XX:+UseConcMarkSweepGC # Concurrent (low-latency)
-XX:+UseG1GC          # G1GC (balanced, default in Java 9+)
-XX:+UseZGC           # Ultra-low latency (Java 11+)
-XX:+UseShenandoahGC  # Low-pause (Java 12+)
```

## Monitoring and Analysis Tools

```java
public class GCMonitoringDemo {
    
    static class SystemMetrics {
        long timestamp;
        long heapUsed;
        long heapMax;
        long gcCount;
        long gcTime;
        
        SystemMetrics() {
            this.timestamp = System.currentTimeMillis();
            MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
            this.heapUsed = memBean.getHeapMemoryUsage().getUsed();
            this.heapMax = memBean.getHeapMemoryUsage().getMax();
            
            List<GarbageCollectorMXBean> gcBeans = 
                ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean bean : gcBeans) {
                this.gcCount += bean.getCollectionCount();
                this.gcTime += bean.getCollectionTime();
            }
        }
        
        void report() {
            System.out.println("=== GC Metrics at " + new Date(timestamp) + " ===");
            System.out.println("Heap Used: " + (heapUsed / 1024 / 1024) + " MB / " + 
                             (heapMax / 1024 / 1024) + " MB");
            System.out.println("Heap Usage: " + 
                             String.format("%.2f%%", (heapUsed * 100.0) / heapMax));
            System.out.println("GC Collections: " + gcCount);
            System.out.println("Total GC Time: " + gcTime + " ms");
            System.out.println();
        }
    }
    
    public static void monitorGC() throws InterruptedException {
        System.out.println("GC Monitoring Demo");
        System.out.println();
        
        // Initial metrics
        SystemMetrics initial = new SystemMetrics();
        initial.report();
        
        // Allocate memory
        List<byte[]> allocations = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            allocations.add(new byte[1024 * 1024]); // 1MB each
            if (i % 25 == 0) {
                Thread.sleep(100);
                new SystemMetrics().report();
            }
        }
        
        // Deallocate
        allocations.clear();
        System.gc();
        Thread.sleep(100);
        
        // Final metrics
        SystemMetrics final_metrics = new SystemMetrics();
        final_metrics.report();
    }
    
    public static void main(String[] args) throws InterruptedException {
        monitorGC();
    }
}
```

### External Tools

1. **JVisualVM**: Visual monitoring of GC, heap, threads
   - Built-in tool with JDK
   - Real-time GC event visualization

2. **JProfiler**: Comprehensive profiling
   - Detailed GC analysis
   - Memory leak detection
   - Heap walker for object inspection

3. **YourKit**: Low-overhead profiler
   - Minimal performance impact
   - Advanced GC analytics

4. **Java Flight Recorder (JFR)**:
   - Production-grade profiling
   - Zero-overhead when disabled
   ```bash
   -XX:StartFlightRecording=filename=recording.jfr
   jfr dump --output recording.jfr jcmd_file
   ```

## Best Practices

```java
public class GCBestPractices {
    
    // 1. Minimize object allocation
    static class ObjectPool {
        private Queue<byte[]> pool = new LinkedList<>();
        
        byte[] acquire(int size) {
            byte[] buffer = pool.poll();
            if (buffer == null || buffer.length < size) {
                buffer = new byte[size];
            }
            return buffer;
        }
        
        void release(byte[] buffer) {
            if (buffer != null && buffer.length <= 1024 * 1024) {
                Arrays.fill(buffer, (byte) 0); // Clear for reuse
                pool.offer(buffer);
            }
        }
    }
    
    // 2. Avoid creating objects in tight loops
    static void inefficient() {
        for (int i = 0; i < 1000000; i++) {
            String s = new String("value" + i); // New object each iteration
            System.out.println(s);
        }
    }
    
    static void efficient() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            sb.setLength(0);
            sb.append("value").append(i);
            System.out.println(sb.toString());
        }
    }
    
    // 3. Be cautious with string concatenation
    static String inefficientConcat(String[] parts) {
        String result = "";
        for (String part : parts) {
            result += part; // Creates new String each iteration
        }
        return result;
    }
    
    static String efficientConcat(String[] parts) {
        return String.join("", parts); // Single allocation
    }
    
    // 4. Manage large collections carefully
    static void collectionManagement() {
        List<String> cache = new ArrayList<>();
        
        // Bad: Unbounded growth
        // for (String item : items) cache.add(item);
        
        // Good: Size-limited
        if (cache.size() > 10000) {
            cache.clear(); // Periodic cleanup
        }
    }
    
    // 5. Avoid premature optimization
    // Don't call System.gc() explicitly
    static void avoidExplicitGC() {
        // WRONG: System.gc();
        
        // RIGHT: Let JVM manage GC
        // Use GC tuning parameters instead
    }
}
```

## Conclusion

Garbage collection is a cornerstone of Java's memory management. Understanding GC algorithms, the generational hypothesis, and tuning techniques enables developers to write efficient, scalable applications. Modern GC algorithms like G1GC provide excellent defaults, but monitoring and tuning remain important for high-performance systems.

Key takeaways:
- The generational hypothesis explains why generational GC is effective
- Young generation needs frequent, fast collection
- Old generation collection is less frequent but more expensive
- G1GC is the recommended default for most applications
- Monitor GC behavior and tune based on application characteristics
- Minimize unnecessary object allocation for better GC performance
