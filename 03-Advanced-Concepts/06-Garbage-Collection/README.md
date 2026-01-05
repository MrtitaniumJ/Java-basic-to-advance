# Garbage Collection in Java - Memory Management

## Simple Explanation

Think of **Garbage Collection** as **automatic housekeeping**:

- **Memory** = Hotel rooms (finite space for guests)
- **Objects** = Hotel guests (occupy rooms while staying)
- **Garbage Collection** = Housekeeping service (cleans up after guests check out)
- **GC Algorithm** = Cleaning strategy (how and when to clean rooms)
- **Memory leak** = Guests who never check out (rooms never become available)

### Real-World Analogies
- **Automatic vacuum cleaner** = GC (cleans house automatically without manual intervention)
- **Library book return system** = GC (removes books no longer being read)
- **Restaurant table management** = GC (clears tables when customers leave)
- **Parking space turnover** = GC (frees up spaces when cars leave)

## Professional Definition

**Garbage Collection** in Java is an automatic memory management process that identifies and deallocates objects that are no longer reachable or referenced by the application, freeing up heap memory for future allocations. It uses sophisticated algorithms like mark-and-sweep, generational collection, and concurrent collection to optimize performance while ensuring memory safety and preventing memory leaks.

## Why Garbage Collection is Critical

### Problems Without Garbage Collection:
```java
// WITHOUT GARBAGE COLLECTION (Manual Memory Management like C++)
// This is what Java developers would need to do without GC

import java.util.*;

class ManualMemoryManagement {
    
    // PROBLEM 1: Manual memory allocation/deallocation
    public void problematicDataProcessing() {
        System.out.println("=== MANUAL MEMORY PROBLEMS ===");
        
        // Simulate manual memory management problems
        List<LargeObject> objects = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++) {
            // Create object (allocate memory)
            LargeObject obj = new LargeObject("Object " + i);
            objects.add(obj);
            
            // PROBLEM: Forgetting to deallocate memory!
            // In C++, you would need: delete obj;
            // Without deallocation, memory keeps growing
            
            if (i % 100 == 0) {
                System.out.println("Created " + (i + 1) + " objects - Memory growing!");
            }
        }
        
        // PROBLEM: Even when we're done, objects remain in memory
        // Developer must remember to clear references manually
        // objects.clear(); // This is manual "deallocation" in Java
        
        System.out.println("❌ Without GC, memory would never be freed automatically");
    }
    
    // PROBLEM 2: Dangling pointers and memory corruption
    public void danglingPointerProblem() {
        System.out.println("\n--- Dangling Pointer Problems ---");
        
        // This demonstrates what could happen without GC safety
        LargeObject obj1 = new LargeObject("Primary Object");
        LargeObject obj2 = obj1; // Both references point to same object
        
        // In manual memory management, if obj1 is "freed":
        obj1 = null; // Simulate manual deallocation
        
        // PROBLEM: obj2 would be a dangling pointer in C++
        // Accessing obj2 would cause crash or undefined behavior
        // Java GC prevents this by keeping object alive while obj2 exists
        
        if (obj2 != null) {
            System.out.println("✅ Java GC keeps object safe: " + obj2.getData());
        }
        
        System.out.println("✅ GC prevents dangling pointer crashes");
    }
    
    // PROBLEM 3: Memory leaks from forgotten references
    public void memoryLeakSimulation() {
        System.out.println("\n--- Memory Leak Simulation ---");
        
        // Common memory leak: static collections never cleared
        StaticDataHolder.addData("Never cleared data " + System.currentTimeMillis());
        
        // Event listener leak: listeners not removed
        EventPublisher publisher = new EventPublisher();
        EventListener listener = new EventListener() {
            @Override
            public void onEvent(String event) {
                // Handle event
            }
        };
        
        publisher.addListener(listener);
        // PROBLEM: Forgot to remove listener!
        // publisher.removeListener(listener); // This line missing!
        
        // Thread leak: threads not properly terminated
        Thread workerThread = new Thread(() -> {
            while (true) { // PROBLEM: Infinite loop keeps thread alive
                try {
                    Thread.sleep(1000);
                    // Do some work
                } catch (InterruptedException e) {
                    break; // Proper way to exit
                }
            }
        });
        
        // workerThread.start(); // Commented to avoid actual leak
        
        System.out.println("⚠️ Even with GC, poor programming can cause memory leaks");
    }
    
    static class LargeObject {
        private String data;
        private byte[] largeArray; // Simulate memory usage
        
        public LargeObject(String data) {
            this.data = data;
            this.largeArray = new byte[1024 * 10]; // 10KB per object
        }
        
        public String getData() {
            return data;
        }
    }
    
    // Memory leak source: static collection never cleared
    static class StaticDataHolder {
        private static List<String> staticData = new ArrayList<>();
        
        public static void addData(String data) {
            staticData.add(data);
        }
        
        public static int getSize() {
            return staticData.size();
        }
    }
    
    interface EventListener {
        void onEvent(String event);
    }
    
    static class EventPublisher {
        private List<EventListener> listeners = new ArrayList<>();
        
        public void addListener(EventListener listener) {
            listeners.add(listener);
        }
        
        public void removeListener(EventListener listener) {
            listeners.remove(listener);
        }
        
        public void publishEvent(String event) {
            listeners.forEach(listener -> listener.onEvent(event));
        }
    }
}
```

### With Automatic Garbage Collection - Safe and Efficient:
```java
// WITH GARBAGE COLLECTION - Automatic, safe memory management

import java.lang.ref.*;
import java.util.concurrent.atomic.AtomicLong;

class AutomaticMemoryManagement {
    
    private static AtomicLong objectCounter = new AtomicLong(0);
    
    // SOLUTION 1: Automatic memory management
    public void automaticDataProcessing() {
        System.out.println("=== AUTOMATIC MEMORY MANAGEMENT ===");
        
        List<ManagedObject> objects = new ArrayList<>();
        
        System.out.println("🏗️ Creating objects...");
        for (int i = 0; i < 1000; i++) {
            ManagedObject obj = new ManagedObject("Object " + i);
            objects.add(obj);
            
            if (i % 200 == 0) {
                System.out.println("Created " + (i + 1) + " objects");
                printMemoryUsage();
            }
        }
        
        System.out.println("\n🗑️ Clearing references...");
        objects.clear(); // Remove references
        
        System.out.println("📞 Suggesting garbage collection...");
        System.gc(); // Suggest GC (not guaranteed immediate)
        
        // Give GC time to work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        printMemoryUsage();
        System.out.println("✅ GC automatically freed unreachable objects");
    }
    
    // SOLUTION 2: Understanding object reachability
    public void demonstrateObjectReachability() {
        System.out.println("\n=== OBJECT REACHABILITY ===");
        
        // Strong reference
        ManagedObject strongRef = new ManagedObject("Strong Reference");
        System.out.println("📌 Created object with strong reference");
        
        // Weak reference
        WeakReference<ManagedObject> weakRef = new WeakReference<>(strongRef);
        System.out.println("🔗 Created weak reference to same object");
        
        // Object is reachable via strong reference
        System.out.println("Strong ref accessible: " + (strongRef != null));
        System.out.println("Weak ref accessible: " + (weakRef.get() != null));
        
        // Remove strong reference
        strongRef = null;
        System.gc(); // Suggest collection
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check if object was collected
        System.out.println("\nAfter removing strong reference:");
        System.out.println("Strong ref accessible: false");
        System.out.println("Weak ref accessible: " + (weakRef.get() != null));
        
        if (weakRef.get() == null) {
            System.out.println("✅ Object was garbage collected (weak reference cleared)");
        } else {
            System.out.println("⏳ Object not yet collected (GC timing is unpredictable)");
        }
    }
    
    // SOLUTION 3: Preventing memory leaks with proper practices
    public void properMemoryManagement() {
        System.out.println("\n=== PROPER MEMORY MANAGEMENT ===");
        
        // Proper event listener management
        AutoCleanupEventPublisher publisher = new AutoCleanupEventPublisher();
        
        // Use try-with-resources for automatic cleanup
        try (EventSubscription subscription = publisher.subscribe(event -> 
            System.out.println("Received event: " + event))) {
            
            publisher.publishEvent("Test event 1");
            publisher.publishEvent("Test event 2");
            
            System.out.println("✅ Event listener will be automatically removed");
            
        } // AutoCloseable ensures cleanup
        
        publisher.publishEvent("Test event 3 (no listeners)");
        
        // Proper collection management
        Map<String, String> cache = new HashMap<>();
        
        // Add data to cache
        for (int i = 0; i < 100; i++) {
            cache.put("key" + i, "value" + i);
        }
        
        System.out.println("📦 Cache size: " + cache.size());
        
        // Clear cache when done (releases references)
        cache.clear();
        System.out.println("🧹 Cache cleared - references released for GC");
        
        // Demonstrate WeakHashMap for automatic cleanup
        demonstrateWeakHashMap();
    }
    
    private void demonstrateWeakHashMap() {
        System.out.println("\n--- WeakHashMap Demonstration ---");
        
        // Regular HashMap keeps strong references
        Map<String, String> strongMap = new HashMap<>();
        
        // WeakHashMap allows keys to be garbage collected
        Map<String, String> weakMap = new WeakHashMap<>();
        
        // Create keys
        String key1 = new String("strong_key"); // Strong reference
        String key2 = new String("weak_key");   // Will lose strong reference
        
        strongMap.put(key1, "Strong value");
        weakMap.put(key2, "Weak value");
        
        System.out.println("Before GC:");
        System.out.println("  Strong map size: " + strongMap.size());
        System.out.println("  Weak map size: " + weakMap.size());
        
        // Remove strong reference to key2
        key2 = null;
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("After GC:");
        System.out.println("  Strong map size: " + strongMap.size());
        System.out.println("  Weak map size: " + weakMap.size());
        
        if (weakMap.size() == 0) {
            System.out.println("✅ WeakHashMap automatically removed unreachable key");
        }
    }
    
    private void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.printf("💾 Memory: Used=%,d KB, Free=%,d KB, Total=%,d KB%n",
                         usedMemory / 1024, freeMemory / 1024, totalMemory / 1024);
    }
    
    // Managed object with finalization tracking
    static class ManagedObject {
        private String data;
        private byte[] memory;
        private long id;
        
        public ManagedObject(String data) {
            this.data = data;
            this.memory = new byte[1024 * 5]; // 5KB
            this.id = objectCounter.incrementAndGet();
        }
        
        @Override
        protected void finalize() throws Throwable {
            // Note: finalize() is deprecated in Java 9+
            // This is for educational purposes only
            System.out.println("🗑️ Finalizing object " + id + ": " + data);
            super.finalize();
        }
        
        public String getData() {
            return data;
        }
        
        public long getId() {
            return id;
        }
    }
    
    // AutoCloseable event subscription
    static class EventSubscription implements AutoCloseable {
        private AutoCleanupEventPublisher publisher;
        private EventListener listener;
        
        public EventSubscription(AutoCleanupEventPublisher publisher, EventListener listener) {
            this.publisher = publisher;
            this.listener = listener;
        }
        
        @Override
        public void close() {
            if (publisher != null && listener != null) {
                publisher.removeListener(listener);
                System.out.println("🔌 Event listener automatically removed");
            }
        }
    }
    
    static class AutoCleanupEventPublisher {
        private List<EventListener> listeners = new ArrayList<>();
        
        public EventSubscription subscribe(EventListener listener) {
            listeners.add(listener);
            return new EventSubscription(this, listener);
        }
        
        public void removeListener(EventListener listener) {
            listeners.remove(listener);
        }
        
        public void publishEvent(String event) {
            listeners.forEach(listener -> listener.onEvent(event));
        }
    }
    
    @FunctionalInterface
    interface EventListener {
        void onEvent(String event);
    }
}
```

## Garbage Collection Algorithms

### 1. Generational Garbage Collection
```java
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GarbageCollectionDemo {
    
    public void demonstrateGenerationalGC() {
        System.out.println("=== GENERATIONAL GARBAGE COLLECTION ===");
        
        System.out.println("🏗️ Understanding Generational Hypothesis:");
        System.out.println("  1. Most objects die young (Eden space)");
        System.out.println("  2. Objects that survive become long-lived (Old generation)");
        System.out.println("  3. Different GC strategies for different generations");
        
        demonstrateYoungGeneration();
        demonstrateOldGeneration();
        demonstrateGenerationPromotion();
    }
    
    private void demonstrateYoungGeneration() {
        System.out.println("\n--- Young Generation (Eden Space) ---");
        
        System.out.println("🆕 Creating many short-lived objects...");
        
        // Simulate many short-lived objects (typical in young generation)
        for (int batch = 1; batch <= 5; batch++) {
            List<ShortLivedObject> tempObjects = new ArrayList<>();
            
            // Create many objects in quick succession
            for (int i = 0; i < 10000; i++) {
                ShortLivedObject obj = new ShortLivedObject("Temp-" + batch + "-" + i);
                tempObjects.add(obj);
                
                // Some objects used immediately and discarded
                if (i % 1000 == 0) {
                    String processed = obj.processData();
                    // Object becomes eligible for GC immediately after use
                }
            }
            
            System.out.printf("📊 Batch %d created (10,000 objects)%n", batch);
            
            // Clear references - objects become eligible for young gen GC
            tempObjects.clear();
            
            // Minor GC likely to occur here for young generation
            suggestMinorGC();
            
            try {
                Thread.sleep(10); // Allow GC to work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("✅ Most objects collected by minor GC (young generation)");
    }
    
    private void demonstrateOldGeneration() {
        System.out.println("\n--- Old Generation (Tenured Space) ---");
        
        // Create long-lived objects that will be promoted to old generation
        List<LongLivedObject> persistentObjects = new ArrayList<>();
        
        System.out.println("🏛️ Creating long-lived objects...");
        
        for (int i = 0; i < 1000; i++) {
            LongLivedObject obj = new LongLivedObject("Persistent-" + i);
            persistentObjects.add(obj);
            
            // Simulate some minor GC cycles to promote objects
            if (i % 100 == 0) {
                triggerMinorGCCycles();
                System.out.printf("📈 Created %d persistent objects%n", i + 1);
            }
        }
        
        System.out.println("📊 Objects surviving multiple GC cycles get promoted to old generation");
        
        // These objects will require major GC to collect
        System.out.println("🕰️ Long-lived objects remain in old generation");
        
        // Keep references to prevent collection
        System.out.println("📌 " + persistentObjects.size() + " objects held by strong references");
    }
    
    private void demonstrateGenerationPromotion() {
        System.out.println("\n--- Generation Promotion Process ---");
        
        System.out.println("🔄 Object lifecycle demonstration:");
        
        // Create objects and track their lifecycle
        List<SurvivingObject> survivors = new ArrayList<>();
        
        for (int generation = 0; generation < 5; generation++) {
            System.out.printf("\n📅 Generation %d:%n", generation);
            
            // Create new objects (start in Eden)
            List<SurvivingObject> newObjects = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                SurvivingObject obj = new SurvivingObject("Gen" + generation + "-" + i, generation);
                newObjects.add(obj);
            }
            
            // Some objects survive to next generation
            for (int i = 0; i < newObjects.size(); i += 10) { // 10% survival rate
                SurvivingObject survivor = newObjects.get(i);
                survivor.survive();
                survivors.add(survivor);
            }
            
            System.out.printf("  🆕 Created: 1,000 objects%n");
            System.out.printf("  ✅ Survived: %d objects%n", newObjects.size() / 10);
            System.out.printf("  📊 Total survivors: %d objects%n", survivors.size());
            
            // Clear new objects (simulate GC)
            newObjects.clear();
            triggerMinorGCCycles();
        }
        
        System.out.println("\n📈 Survival statistics:");
        Map<Integer, Long> generationCounts = survivors.stream()
            .collect(Collectors.groupingBy(SurvivingObject::getBirthGeneration, Collectors.counting()));
        
        generationCounts.forEach((gen, count) -> 
            System.out.printf("  Generation %d survivors: %d%n", gen, count));
    }
    
    private void suggestMinorGC() {
        // Note: System.gc() may trigger full GC, not just minor GC
        // This is for demonstration purposes
        System.gc();
    }
    
    private void triggerMinorGCCycles() {
        // Simulate allocation pressure to trigger minor GC
        List<byte[]> pressure = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            pressure.add(new byte[1024 * 10]); // 10KB each
        }
        pressure.clear(); // Release immediately
    }
    
    // Classes to demonstrate GC behavior
    static class ShortLivedObject {
        private String data;
        private byte[] buffer = new byte[512]; // 512 bytes
        
        public ShortLivedObject(String data) {
            this.data = data;
            Arrays.fill(buffer, (byte) 1); // Use the buffer
        }
        
        public String processData() {
            return "Processed: " + data;
        }
    }
    
    static class LongLivedObject {
        private String identifier;
        private Map<String, Object> cache = new ConcurrentHashMap<>();
        private byte[] largeBuffer = new byte[1024 * 2]; // 2KB
        
        public LongLivedObject(String identifier) {
            this.identifier = identifier;
            // Initialize cache with some data
            for (int i = 0; i < 10; i++) {
                cache.put("key" + i, "value" + i);
            }
        }
        
        public String getIdentifier() {
            return identifier;
        }
        
        public Map<String, Object> getCache() {
            return cache;
        }
    }
    
    static class SurvivingObject {
        private String name;
        private int birthGeneration;
        private int survivalCount = 0;
        private byte[] data = new byte[256]; // 256 bytes
        
        public SurvivingObject(String name, int birthGeneration) {
            this.name = name;
            this.birthGeneration = birthGeneration;
        }
        
        public void survive() {
            survivalCount++;
        }
        
        public int getBirthGeneration() {
            return birthGeneration;
        }
        
        public int getSurvivalCount() {
            return survivalCount;
        }
    }
}
```

### 2. GC Tuning and Monitoring
```java
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class GCMonitoringDemo {
    
    public void demonstrateGCMonitoring() {
        System.out.println("=== GARBAGE COLLECTION MONITORING ===");
        
        // Memory and GC monitoring
        monitorMemoryUsage();
        monitorGarbageCollectors();
        demonstrateMemoryPressure();
        analyzeGCPerformance();
    }
    
    private void monitorMemoryUsage() {
        System.out.println("\n--- Memory Usage Monitoring ---");
        
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        
        // Heap memory usage
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        System.out.println("💾 Heap Memory:");
        printMemoryUsage("  ", heapUsage);
        
        // Non-heap memory usage (method area, code cache, etc.)
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        System.out.println("\n🧠 Non-Heap Memory:");
        printMemoryUsage("  ", nonHeapUsage);
        
        // Pending finalization count
        int pendingFinalization = memoryBean.getObjectPendingFinalizationCount();
        System.out.println("\n🗑️ Objects pending finalization: " + pendingFinalization);
    }
    
    private void printMemoryUsage(String prefix, MemoryUsage usage) {
        long used = usage.getUsed();
        long committed = usage.getCommitted();
        long max = usage.getMax();
        
        System.out.printf("%sUsed: %,d KB%n", prefix, used / 1024);
        System.out.printf("%sCommitted: %,d KB%n", prefix, committed / 1024);
        System.out.printf("%sMax: %s KB%n", prefix, max == -1 ? "Unlimited" : String.format("%,d", max / 1024));
        System.out.printf("%sUsage: %.2f%%%n", prefix, (double) used / committed * 100);
    }
    
    private void monitorGarbageCollectors() {
        System.out.println("\n--- Garbage Collector Information ---");
        
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        
        System.out.printf("🔧 Available Garbage Collectors: %d%n", gcBeans.size());
        
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            String name = gcBean.getName();
            String[] memoryPoolNames = gcBean.getMemoryPoolNames();
            long collectionCount = gcBean.getCollectionCount();
            long collectionTime = gcBean.getCollectionTime();
            
            System.out.printf("\n📊 GC: %s%n", name);
            System.out.printf("  Collections: %,d%n", collectionCount);
            System.out.printf("  Total time: %,d ms%n", collectionTime);
            
            if (collectionCount > 0) {
                System.out.printf("  Average time: %.2f ms%n", (double) collectionTime / collectionCount);
            }
            
            System.out.printf("  Memory pools: %s%n", String.join(", ", memoryPoolNames));
        }
    }
    
    private void demonstrateMemoryPressure() {
        System.out.println("\n--- Memory Pressure Demonstration ---");
        
        // Record initial state
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        
        long initialUsed = memoryBean.getHeapMemoryUsage().getUsed();
        Map<String, Long> initialCollections = new HashMap<>();
        Map<String, Long> initialCollectionTime = new HashMap<>();
        
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            initialCollections.put(gcBean.getName(), gcBean.getCollectionCount());
            initialCollectionTime.put(gcBean.getName(), gcBean.getCollectionTime());
        }
        
        System.out.printf("🎯 Initial heap usage: %,d KB%n", initialUsed / 1024);
        
        // Create memory pressure
        System.out.println("⚡ Creating memory pressure...");
        List<MemoryConsumer> consumers = new ArrayList<>();
        
        try {
            for (int wave = 1; wave <= 10; wave++) {
                // Create objects in waves
                for (int i = 0; i < 1000; i++) {
                    MemoryConsumer consumer = new MemoryConsumer("Wave-" + wave + "-Item-" + i);
                    consumers.add(consumer);
                }
                
                System.out.printf("  Wave %d: %,d objects created%n", wave, wave * 1000);
                
                // Periodically release some objects
                if (wave % 3 == 0) {
                    int releaseCount = consumers.size() / 4;
                    for (int i = 0; i < releaseCount; i++) {
                        consumers.remove(0);
                    }
                    System.out.printf("    Released %,d objects%n", releaseCount);
                }
                
                // Check memory status
                long currentUsed = memoryBean.getHeapMemoryUsage().getUsed();
                System.out.printf("    Heap usage: %,d KB%n", currentUsed / 1024);
                
                Thread.sleep(50); // Allow GC to work
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Force GC and analyze results
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Report GC activity
        System.out.println("\n📈 GC Activity Report:");
        long finalUsed = memoryBean.getHeapMemoryUsage().getUsed();
        System.out.printf("Final heap usage: %,d KB%n", finalUsed / 1024);
        System.out.printf("Memory change: %+,d KB%n", (finalUsed - initialUsed) / 1024);
        
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            String name = gcBean.getName();
            long finalCollections = gcBean.getCollectionCount();
            long finalTime = gcBean.getCollectionTime();
            
            long collectionsIncrease = finalCollections - initialCollections.get(name);
            long timeIncrease = finalTime - initialCollectionTime.get(name);
            
            if (collectionsIncrease > 0) {
                System.out.printf("  %s: %d collections (+%,d ms)%n", 
                                name, collectionsIncrease, timeIncrease);
            }
        }
        
        // Clear references
        consumers.clear();
    }
    
    private void analyzeGCPerformance() {
        System.out.println("\n--- GC Performance Analysis ---");
        
        // Simulate different allocation patterns
        performanceTest("Rapid Allocation", this::rapidAllocationPattern);
        performanceTest("Steady Allocation", this::steadyAllocationPattern);
        performanceTest("Burst Allocation", this::burstAllocationPattern);
    }
    
    private void performanceTest(String testName, Runnable test) {
        System.out.printf("\n🧪 %s Test:%n", testName);
        
        // Record initial GC state
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        Map<String, Long> initialCollections = new HashMap<>();
        Map<String, Long> initialTime = new HashMap<>();
        
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            initialCollections.put(gcBean.getName(), gcBean.getCollectionCount());
            initialTime.put(gcBean.getName(), gcBean.getCollectionTime());
        }
        
        // Run test
        long startTime = System.currentTimeMillis();
        test.run();
        long endTime = System.currentTimeMillis();
        
        // Analyze results
        long testDuration = endTime - startTime;
        System.out.printf("  Test duration: %,d ms%n", testDuration);
        
        long totalGCTime = 0;
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            String name = gcBean.getName();
            long collections = gcBean.getCollectionCount() - initialCollections.get(name);
            long time = gcBean.getCollectionTime() - initialTime.get(name);
            totalGCTime += time;
            
            if (collections > 0) {
                System.out.printf("    %s: %d collections, %,d ms%n", name, collections, time);
            }
        }
        
        double gcOverhead = (double) totalGCTime / testDuration * 100;
        System.out.printf("  GC overhead: %.2f%%%n", gcOverhead);
    }
    
    private void rapidAllocationPattern() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            objects.add(new MemoryConsumer("Rapid-" + i));
            if (i % 10000 == 0) {
                objects.subList(0, Math.min(5000, objects.size())).clear();
            }
        }
    }
    
    private void steadyAllocationPattern() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            objects.add(new MemoryConsumer("Steady-" + i));
            try {
                Thread.sleep(1); // Steady pace
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void burstAllocationPattern() {
        for (int burst = 0; burst < 5; burst++) {
            List<Object> objects = new ArrayList<>();
            
            // Rapid allocation burst
            for (int i = 0; i < 10000; i++) {
                objects.add(new MemoryConsumer("Burst-" + burst + "-" + i));
            }
            
            // Pause between bursts
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            objects.clear(); // Release objects
        }
    }
    
    static class MemoryConsumer {
        private String identifier;
        private byte[] data = new byte[1024]; // 1KB per object
        private List<String> metadata = new ArrayList<>();
        
        public MemoryConsumer(String identifier) {
            this.identifier = identifier;
            
            // Add some metadata
            for (int i = 0; i < 10; i++) {
                metadata.add("Meta-" + i + "-" + identifier);
            }
        }
        
        public String getIdentifier() {
            return identifier;
        }
    }
}
```

## Memory Leaks and Prevention

### 1. Common Memory Leak Patterns
```java
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.ref.WeakReference;

public class MemoryLeakDemo {
    
    public void demonstrateMemoryLeaks() {
        System.out.println("=== MEMORY LEAK PATTERNS AND PREVENTION ===");
        
        // Common memory leak patterns
        demonstrateStaticCollectionLeak();
        demonstrateListenerLeak();
        demonstrateThreadLocalLeak();
        demonstrateInnerClassLeak();
        
        // Prevention techniques
        demonstrateLeakPrevention();
    }
    
    // LEAK PATTERN 1: Static collection that grows indefinitely
    private void demonstrateStaticCollectionLeak() {
        System.out.println("\n--- Static Collection Memory Leak ---");
        
        System.out.println("❌ BAD: Static collection never cleared");
        
        // Simulate adding to static collection (memory leak)
        for (int i = 0; i < 1000; i++) {
            LeakyStaticCache.addItem("Item-" + i, new LargeObject("Data-" + i));
        }
        
        System.out.printf("Static cache size: %,d items%n", LeakyStaticCache.getSize());
        System.out.println("⚠️ These objects will NEVER be garbage collected!");
        
        // Show proper approach
        System.out.println("\n✅ GOOD: Proper cache management");
        
        ProperCache properCache = new ProperCache(100); // Limited size cache
        
        for (int i = 0; i < 200; i++) {
            properCache.put("Key-" + i, new LargeObject("Value-" + i));
        }
        
        System.out.printf("Proper cache size: %,d items (auto-limited)%n", properCache.size());
        System.out.println("✅ Old entries automatically evicted");
    }
    
    // LEAK PATTERN 2: Event listeners not removed
    private void demonstrateListenerLeak() {
        System.out.println("\n--- Event Listener Memory Leak ---");
        
        LeakyEventSource eventSource = new LeakyEventSource();
        
        System.out.println("❌ BAD: Adding listeners without removal");
        
        // Add many listeners without removing them
        for (int i = 0; i < 100; i++) {
            final int listenerId = i;
            EventListener listener = new EventListener() {
                private LargeObject data = new LargeObject("Listener-" + listenerId);
                
                @Override
                public void onEvent(String event) {
                    // Handle event
                }
            };
            
            eventSource.addListener(listener);
        }
        
        System.out.printf("Event source listeners: %,d%n", eventSource.getListenerCount());
        System.out.println("⚠️ All listeners and their data held in memory!");
        
        // Show proper approach
        System.out.println("\n✅ GOOD: Proper listener management");
        
        ProperEventSource properEventSource = new ProperEventSource();
        List<ListenerRegistration> registrations = new ArrayList<>();
        
        for (int i = 0; i < 100; i++) {
            final int listenerId = i;
            EventListener listener = event -> {
                // Handle event - no large data stored
            };
            
            ListenerRegistration registration = properEventSource.addListener(listener);
            registrations.add(registration);
        }
        
        System.out.printf("Proper event source listeners: %,d%n", properEventSource.getListenerCount());
        
        // Clean up listeners
        registrations.forEach(ListenerRegistration::remove);
        System.out.printf("After cleanup: %,d listeners%n", properEventSource.getListenerCount());
        System.out.println("✅ All listeners properly removed");
    }
    
    // LEAK PATTERN 3: ThreadLocal variables not cleaned up
    private void demonstrateThreadLocalLeak() {
        System.out.println("\n--- ThreadLocal Memory Leak ---");
        
        System.out.println("❌ BAD: ThreadLocal not cleared");
        
        // Create threads that set ThreadLocal but don't clear it
        List<Thread> leakyThreads = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                // Set ThreadLocal value
                LeakyThreadLocalManager.setData(new LargeObject("Thread-" + threadId));
                
                // Do some work
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Thread ends without clearing ThreadLocal!
                System.out.println("Thread " + threadId + " finished (ThreadLocal not cleared)");
            }, "LeakyThread-" + i);
            
            leakyThreads.add(thread);
            thread.start();
        }
        
        // Wait for threads to complete
        leakyThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        System.out.println("⚠️ ThreadLocal data may remain in memory until threads are GC'd");
        
        // Show proper approach
        System.out.println("\n✅ GOOD: Proper ThreadLocal cleanup");
        
        List<Thread> properThreads = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                try {
                    // Set ThreadLocal value
                    ProperThreadLocalManager.setData(new LargeObject("Thread-" + threadId));
                    
                    // Do some work
                    Thread.sleep(100);
                    
                    System.out.println("Thread " + threadId + " finished");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // Always clean up ThreadLocal
                    ProperThreadLocalManager.clearData();
                }
            }, "ProperThread-" + i);
            
            properThreads.add(thread);
            thread.start();
        }
        
        // Wait for threads to complete
        properThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        System.out.println("✅ All ThreadLocal data properly cleaned up");
    }
    
    // LEAK PATTERN 4: Inner class holding outer class reference
    private void demonstrateInnerClassLeak() {
        System.out.println("\n--- Inner Class Memory Leak ---");
        
        System.out.println("❌ BAD: Non-static inner class holds outer reference");
        
        List<Runnable> leakyTasks = new ArrayList<>();
        
        // Create tasks using non-static inner class
        OuterClassWithLeak outer = new OuterClassWithLeak();
        for (int i = 0; i < 100; i++) {
            leakyTasks.add(outer.createLeakyTask(i));
        }
        
        // Even if we null the outer reference, inner classes keep it alive
        outer = null;
        
        System.out.printf("Created %,d leaky tasks%n", leakyTasks.size());
        System.out.println("⚠️ Inner class tasks prevent outer class from being GC'd");
        
        // Show proper approach
        System.out.println("\n✅ GOOD: Static inner class or separate class");
        
        List<Runnable> properTasks = new ArrayList<>();
        
        OuterClassWithoutLeak properOuter = new OuterClassWithoutLeak();
        for (int i = 0; i < 100; i++) {
            properTasks.add(properOuter.createProperTask(i));
        }
        
        // Clear outer reference
        properOuter = null;
        
        System.out.printf("Created %,d proper tasks%n", properTasks.size());
        System.out.println("✅ Outer class can be GC'd independently");
    }
    
    private void demonstrateLeakPrevention() {
        System.out.println("\n--- Memory Leak Prevention Techniques ---");
        
        System.out.println("🛡️ Prevention strategies:");
        System.out.println("  1. Use WeakReferences for caches");
        System.out.println("  2. Implement AutoCloseable for resource management");
        System.out.println("  3. Clear collections when done");
        System.out.println("  4. Remove listeners and callbacks");
        System.out.println("  5. Clean up ThreadLocal variables");
        System.out.println("  6. Use static inner classes when possible");
        System.out.println("  7. Set large objects to null when done");
        System.out.println("  8. Use try-with-resources");
        System.out.println("  9. Profile memory usage regularly");
        System.out.println("  10. Avoid static collections for dynamic data");
        
        demonstrateWeakReferenceCache();
    }
    
    private void demonstrateWeakReferenceCache() {
        System.out.println("\n--- Weak Reference Cache ---");
        
        WeakReferenceCache<String, LargeObject> cache = new WeakReferenceCache<>();
        
        // Add objects to cache
        List<LargeObject> strongRefs = new ArrayList<>(); // Keep strong references temporarily
        
        for (int i = 0; i < 100; i++) {
            LargeObject obj = new LargeObject("Cached-" + i);
            cache.put("key" + i, obj);
            
            // Keep strong reference for some objects
            if (i < 10) {
                strongRefs.add(obj);
            }
        }
        
        System.out.printf("Cache size after adding: %,d items%n", cache.size());
        
        // Trigger GC
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check cache size after GC
        cache.cleanup(); // Remove cleared weak references
        System.out.printf("Cache size after GC: %,d items%n", cache.size());
        System.out.println("✅ Objects without strong references were automatically removed");
        
        // Clear strong references
        strongRefs.clear();
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        cache.cleanup();
        System.out.printf("Cache size after clearing strong refs: %,d items%n", cache.size());
    }
    
    // Supporting classes for demonstrations
    static class LargeObject {
        private String data;
        private byte[] largeArray = new byte[1024 * 10]; // 10KB
        
        public LargeObject(String data) {
            this.data = data;
        }
        
        public String getData() {
            return data;
        }
    }
    
    // BAD: Static collection that never clears
    static class LeakyStaticCache {
        private static Map<String, LargeObject> cache = new HashMap<>();
        
        public static void addItem(String key, LargeObject value) {
            cache.put(key, value);
        }
        
        public static int getSize() {
            return cache.size();
        }
        
        // Missing: clear() method!
    }
    
    // GOOD: Proper cache with size limits
    static class ProperCache extends LinkedHashMap<String, LargeObject> {
        private final int maxSize;
        
        public ProperCache(int maxSize) {
            super(16, 0.75f, true); // Access-ordered
            this.maxSize = maxSize;
        }
        
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, LargeObject> eldest) {
            return size() > maxSize;
        }
    }
    
    // Event listener classes
    @FunctionalInterface
    interface EventListener {
        void onEvent(String event);
    }
    
    // BAD: No way to remove listeners
    static class LeakyEventSource {
        private List<EventListener> listeners = new ArrayList<>();
        
        public void addListener(EventListener listener) {
            listeners.add(listener);
        }
        
        public int getListenerCount() {
            return listeners.size();
        }
        
        // Missing: removeListener() method!
    }
    
    // GOOD: Proper listener management
    static class ProperEventSource {
        private List<EventListener> listeners = new ArrayList<>();
        
        public ListenerRegistration addListener(EventListener listener) {
            listeners.add(listener);
            return () -> listeners.remove(listener);
        }
        
        public int getListenerCount() {
            return listeners.size();
        }
    }
    
    @FunctionalInterface
    interface ListenerRegistration {
        void remove();
    }
    
    // BAD: ThreadLocal not cleaned up
    static class LeakyThreadLocalManager {
        private static ThreadLocal<LargeObject> threadData = new ThreadLocal<>();
        
        public static void setData(LargeObject data) {
            threadData.set(data);
        }
        
        public static LargeObject getData() {
            return threadData.get();
        }
        
        // Missing: clearData() method!
    }
    
    // GOOD: Proper ThreadLocal cleanup
    static class ProperThreadLocalManager {
        private static ThreadLocal<LargeObject> threadData = new ThreadLocal<>();
        
        public static void setData(LargeObject data) {
            threadData.set(data);
        }
        
        public static LargeObject getData() {
            return threadData.get();
        }
        
        public static void clearData() {
            threadData.remove(); // Important: clean up ThreadLocal
        }
    }
    
    // BAD: Non-static inner class
    static class OuterClassWithLeak {
        private byte[] largeData = new byte[1024 * 100]; // 100KB
        
        public Runnable createLeakyTask(int taskId) {
            // Non-static inner class holds reference to outer instance
            return new Runnable() {
                @Override
                public void run() {
                    System.out.println("Leaky task " + taskId + " running");
                    // Implicit reference to OuterClassWithLeak instance
                }
            };
        }
    }
    
    // GOOD: Static inner class
    static class OuterClassWithoutLeak {
        private byte[] largeData = new byte[1024 * 100]; // 100KB
        
        public Runnable createProperTask(int taskId) {
            // Static inner class doesn't hold outer reference
            return new ProperTask(taskId);
        }
        
        private static class ProperTask implements Runnable {
            private final int taskId;
            
            public ProperTask(int taskId) {
                this.taskId = taskId;
            }
            
            @Override
            public void run() {
                System.out.println("Proper task " + taskId + " running");
            }
        }
    }
    
    // Weak reference cache implementation
    static class WeakReferenceCache<K, V> {
        private Map<K, WeakReference<V>> cache = new ConcurrentHashMap<>();
        
        public void put(K key, V value) {
            cache.put(key, new WeakReference<>(value));
        }
        
        public V get(K key) {
            WeakReference<V> ref = cache.get(key);
            if (ref != null) {
                V value = ref.get();
                if (value == null) {
                    cache.remove(key); // Remove cleared reference
                }
                return value;
            }
            return null;
        }
        
        public void cleanup() {
            cache.entrySet().removeIf(entry -> entry.getValue().get() == null);
        }
        
        public int size() {
            cleanup();
            return cache.size();
        }
    }
}
```

## Complete Garbage Collection Demo

```java
public class CompleteGarbageCollectionDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE GARBAGE COLLECTION DEMONSTRATION ===");
        
        // 1. GC Fundamentals
        System.out.println("\n1. GC FUNDAMENTALS");
        AutomaticMemoryManagement amm = new AutomaticMemoryManagement();
        amm.automaticDataProcessing();
        amm.demonstrateObjectReachability();
        amm.properMemoryManagement();
        
        waitForUser("\nPress Enter to continue to GC Algorithms...");
        
        // 2. GC Algorithms
        System.out.println("\n2. GARBAGE COLLECTION ALGORITHMS");
        GarbageCollectionDemo gcDemo = new GarbageCollectionDemo();
        gcDemo.demonstrateGenerationalGC();
        
        waitForUser("\nPress Enter to continue to GC Monitoring...");
        
        // 3. GC Monitoring
        System.out.println("\n3. GC MONITORING AND TUNING");
        GCMonitoringDemo monitorDemo = new GCMonitoringDemo();
        monitorDemo.demonstrateGCMonitoring();
        
        waitForUser("\nPress Enter to continue to Memory Leaks...");
        
        // 4. Memory Leak Prevention
        System.out.println("\n4. MEMORY LEAK PREVENTION");
        MemoryLeakDemo leakDemo = new MemoryLeakDemo();
        leakDemo.demonstrateMemoryLeaks();
        
        System.out.println("\n=== GARBAGE COLLECTION DEMONSTRATION COMPLETED ===");
        
        // Final memory status
        printFinalMemoryStatus();
    }
    
    private static void waitForUser(String message) {
        System.out.println(message);
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
    
    private static void printFinalMemoryStatus() {
        System.out.println("\n--- Final Memory Status ---");
        
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // Suggest final cleanup
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        System.out.printf("📊 Final Memory Report:%n");
        System.out.printf("  Used: %,d KB%n", usedMemory / 1024);
        System.out.printf("  Free: %,d KB%n", freeMemory / 1024);
        System.out.printf("  Total: %,d KB%n", totalMemory / 1024);
        System.out.printf("  Max: %,d KB%n", maxMemory / 1024);
        System.out.printf("  Usage: %.2f%%%n", (double) usedMemory / totalMemory * 100);
    }
}
```

## Interview Questions & Answers

**Q1: What is garbage collection? How does it work in Java?**
**A:** Garbage collection is automatic memory management that reclaims memory used by objects that are no longer reachable. It works by identifying unreachable objects, marking them for deletion, and freeing their memory.

**Q2: Explain the generational hypothesis in GC.**
**A:** The generational hypothesis states that most objects die young. Based on this, heap is divided into young generation (Eden, Survivor spaces) and old generation, with different GC strategies for each.

**Q3: What's the difference between minor GC and major GC?**
**A:** Minor GC cleans young generation (frequent, fast), major GC cleans old generation (less frequent, slower). Full GC cleans entire heap including permanent generation.

**Q4: How can you prevent memory leaks in Java?**
**A:** Clear collections, remove listeners, clean ThreadLocal variables, use WeakReferences, implement AutoCloseable, avoid static collections for dynamic data, and profile memory regularly.

**Q5: What are different types of references in Java?**
**A:** Strong (default, prevents GC), Weak (allows GC), Soft (GC only when memory low), Phantom (for cleanup notification). Each has different GC behavior.

**Q6: When would you call System.gc()? Is it guaranteed to run?**
**A:** Call System.gc() for testing or before memory-intensive operations, but it's not guaranteed to run immediately. JVM decides when actual GC occurs based on its algorithms.

**Q7: What is memory leak? How do you detect it?**
**A:** Memory leak is when objects remain in memory despite being unused. Detect using memory profilers, monitoring heap usage over time, and analyzing GC logs.

**Q8: Explain finalize() method and why it's problematic.**
**A:** finalize() is called before GC removes object, but it's unpredictable, can resurrect objects, and is deprecated. Use try-with-resources or explicit cleanup instead.

## Key Takeaways

1. **Garbage collection is automatic memory management** that prevents manual memory errors
2. **Generational GC optimizes** for the fact that most objects die young
3. **Memory leaks can occur** even with GC through retained references
4. **Weak references enable automatic cleanup** without preventing garbage collection
5. **GC tuning can improve performance** but requires careful monitoring and testing
6. **ThreadLocal cleanup is crucial** to prevent memory leaks in server applications
7. **Static collections can cause leaks** if not properly managed
8. **Inner classes hold outer references** which can prevent garbage collection
9. **GC monitoring helps identify** memory issues and optimization opportunities
10. **Proper resource management** complements garbage collection for optimal memory usage

---

*Remember: Garbage collection is like an automatic janitor - it keeps your memory clean, but you still need to be tidy and not leave unnecessary messes around!*