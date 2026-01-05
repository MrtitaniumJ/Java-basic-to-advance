# Thread Basics in Java

## Simple Explanation

Think of **threads** as **multiple workers in a kitchen**:

- **Single thread** = One chef cooking everything sequentially 
- **Multiple threads** = Multiple chefs working on different dishes simultaneously
- **Main thread** = Head chef who starts everything
- **Thread creation** = Hiring new chefs to help with the workload
- **Thread execution** = Each chef working on their assigned task
- **Thread coordination** = Chefs communicating to avoid conflicts

### Real-World Analogies
- **Restaurant kitchen** = Multiple cooks preparing different orders simultaneously
- **Assembly line** = Different workers handling different steps concurrently
- **Construction site** = Multiple crews working on different building sections
- **Orchestra** = Musicians playing different instruments in harmony

## Professional Definition

**Threads** are lightweight sub-processes that enable concurrent execution within a single program. They allow applications to perform multiple operations simultaneously, sharing the same memory space while maintaining separate execution contexts. Java provides built-in support for multithreading through the Thread class and Runnable interface, enabling efficient utilization of system resources and improved application responsiveness.

## Why Threads are Important

### Problems with Single-Threaded Applications:
```java
// PROBLEMATIC CODE - Single-threaded limitations

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

class SingleThreadedProblems {
    
    public void demonstrateProblems() {
        System.out.println("=== SINGLE-THREADED APPLICATION PROBLEMS ===");
        
        demonstrateBlockingOperations();
        demonstrateInefficiency();
        demonstratePoorUserExperience();
        demonstrateResourceUnderutilization();
    }
    
    // PROBLEM 1: Blocking operations freeze entire application
    public void demonstrateBlockingOperations() {
        System.out.println("\n--- Blocking Operations Problem ---");
        
        System.out.println("🔄 Starting file processing (simulated)...");
        long startTime = System.currentTimeMillis();
        
        // Simulate blocking file operations
        processLargeFile("file1.txt");
        processLargeFile("file2.txt");
        processLargeFile("file3.txt");
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ Total time: %d ms%n", (endTime - startTime));
        System.out.println("❌ Problem: User interface would be frozen during this entire time!");
        System.out.println("❌ Problem: No other operations can proceed!");
    }
    
    private void processLargeFile(String filename) {
        System.out.printf("  📄 Processing %s...%n", filename);
        
        // Simulate time-consuming file processing
        try {
            Thread.sleep(1000); // Represents blocking I/O operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.printf("  ✅ Completed %s%n", filename);
    }
    
    // PROBLEM 2: Inefficient resource utilization
    public void demonstrateInefficiency() {
        System.out.println("\n--- Resource Utilization Inefficiency ---");
        
        System.out.println("🔄 Processing data sequentially...");
        long startTime = System.currentTimeMillis();
        
        // Sequential processing - CPU idle during I/O
        for (int i = 1; i <= 5; i++) {
            System.out.printf("  🔢 Processing dataset %d...%n", i);
            
            // Simulate computation
            performCalculation();
            
            // Simulate I/O wait
            simulateNetworkCall();
            
            System.out.printf("  ✅ Dataset %d completed%n", i);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ Sequential processing time: %d ms%n", (endTime - startTime));
        System.out.println("❌ Problem: CPU sits idle during network calls!");
        System.out.println("❌ Problem: Cannot overlap computation with I/O!");
    }
    
    private void performCalculation() {
        // Simulate CPU-intensive work
        for (int i = 0; i < 1000000; i++) {
            Math.sqrt(i);
        }
    }
    
    private void simulateNetworkCall() {
        try {
            Thread.sleep(500); // Simulate network latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // PROBLEM 3: Poor user experience
    public void demonstratePoorUserExperience() {
        System.out.println("\n--- Poor User Experience ---");
        
        System.out.println("🔄 Simulating user interface operations...");
        
        // Simulate user clicking download button
        System.out.println("👤 User clicks 'Download Report' button");
        
        // Single thread must handle everything sequentially
        System.out.println("  📊 Generating report...");
        simulateReportGeneration();
        
        System.out.println("  💾 Saving to file...");
        simulateFileSave();
        
        System.out.println("  📤 Uploading to cloud...");
        simulateCloudUpload();
        
        System.out.println("✅ Download completed");
        System.out.println("❌ Problem: UI was unresponsive for entire operation!");
        System.out.println("❌ Problem: User couldn't interact with application!");
        System.out.println("❌ Problem: No progress updates possible!");
    }
    
    private void simulateReportGeneration() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void simulateFileSave() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void simulateCloudUpload() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // PROBLEM 4: Underutilization of multi-core systems
    public void demonstrateResourceUnderutilization() {
        System.out.println("\n--- Multi-Core Underutilization ---");
        
        System.out.println("🖥️ System info:");
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.printf("   Available CPU cores: %d%n", availableProcessors);
        
        System.out.println("🔄 Running CPU-intensive task on single thread...");
        long startTime = System.currentTimeMillis();
        
        // Single-threaded computation that could use multiple cores
        long result = computePrimeSum(1, 100000);
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ Single-threaded computation time: %d ms%n", (endTime - startTime));
        System.out.printf("📊 Result: %d%n", result);
        
        System.out.printf("❌ Problem: Only using 1 out of %d available cores!%n", availableProcessors);
        System.out.println("❌ Problem: Could be much faster with parallelization!");
    }
    
    private long computePrimeSum(int start, int end) {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }
    
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
```

### With Multithreading - Efficient and Responsive:
```java
// ROBUST CODE - Multithreaded solutions

import java.util.concurrent.*;
import java.util.*;

class MultithreadedSolutions {
    
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public void demonstrateSolutions() {
        System.out.println("=== MULTITHREADED SOLUTIONS ===");
        
        demonstrateNonBlockingOperations();
        demonstrateEfficientResourceUsage();
        demonstrateResponsiveUserExperience();
        demonstrateMultiCoreUtilization();
        
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
    
    // SOLUTION 1: Non-blocking operations with concurrent execution
    public void demonstrateNonBlockingOperations() {
        System.out.println("\n--- Non-Blocking Concurrent Operations ---");
        
        System.out.println("🔄 Starting concurrent file processing...");
        long startTime = System.currentTimeMillis();
        
        // Process files concurrently instead of sequentially
        List<Future<String>> futures = new ArrayList<>();
        
        futures.add(executor.submit(() -> processLargeFileConcurrent("file1.txt")));
        futures.add(executor.submit(() -> processLargeFileConcurrent("file2.txt")));
        futures.add(executor.submit(() -> processLargeFileConcurrent("file3.txt")));
        
        // Collect results as they complete
        for (int i = 0; i < futures.size(); i++) {
            try {
                String result = futures.get(i).get();
                System.out.printf("✅ %s%n", result);
            } catch (InterruptedException | ExecutionException e) {
                System.err.printf("❌ Error processing file: %s%n", e.getMessage());
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ Concurrent processing time: %d ms%n", (endTime - startTime));
        System.out.println("✅ Solution: Files processed concurrently!");
        System.out.println("✅ Solution: Application remains responsive!");
    }
    
    private String processLargeFileConcurrent(String filename) {
        System.out.printf("  📄 [%s] Processing %s...%n", 
                         Thread.currentThread().getName(), filename);
        
        try {
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted: " + filename;
        }
        
        return String.format("Completed %s on thread %s", 
                           filename, Thread.currentThread().getName());
    }
    
    // SOLUTION 2: Efficient resource utilization with overlap
    public void demonstrateEfficientResourceUsage() {
        System.out.println("\n--- Efficient Resource Utilization ---");
        
        System.out.println("🔄 Processing data with overlapped computation and I/O...");
        long startTime = System.currentTimeMillis();
        
        // Use thread pool to overlap computation with I/O
        List<Future<String>> futures = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            final int datasetId = i;
            futures.add(executor.submit(() -> processDatasetConcurrent(datasetId)));
        }
        
        // Monitor progress while tasks execute
        System.out.println("📊 Monitoring progress:");
        
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                System.out.printf("  ✅ %s%n", result);
            } catch (InterruptedException | ExecutionException e) {
                System.err.printf("  ❌ Error: %s%n", e.getMessage());
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ Concurrent processing time: %d ms%n", (endTime - startTime));
        System.out.println("✅ Solution: CPU and I/O operations overlapped!");
        System.out.println("✅ Solution: Much better resource utilization!");
    }
    
    private String processDatasetConcurrent(int datasetId) {
        String threadName = Thread.currentThread().getName();
        
        System.out.printf("  🔢 [%s] Processing dataset %d...%n", threadName, datasetId);
        
        // Simulate computation (CPU-bound)
        performCalculationConcurrent();
        
        // Simulate I/O (I/O-bound) - other threads can use CPU during this
        simulateNetworkCallConcurrent();
        
        return String.format("Dataset %d completed on %s", datasetId, threadName);
    }
    
    private void performCalculationConcurrent() {
        for (int i = 0; i < 500000; i++) { // Reduced for demo
            Math.sqrt(i);
        }
    }
    
    private void simulateNetworkCallConcurrent() {
        try {
            Thread.sleep(300); // Simulate network latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // SOLUTION 3: Responsive user experience with background processing
    public void demonstrateResponsiveUserExperience() {
        System.out.println("\n--- Responsive User Experience ---");
        
        System.out.println("🔄 Simulating responsive UI with background processing...");
        
        // Simulate user clicking download button
        System.out.println("👤 User clicks 'Download Report' button");
        
        // Start background task
        Future<String> reportTask = executor.submit(() -> {
            System.out.println("  📊 [Background] Generating report...");
            simulateReportGenerationConcurrent();
            
            System.out.println("  💾 [Background] Saving to file...");
            simulateFileSaveConcurrent();
            
            System.out.println("  📤 [Background] Uploading to cloud...");
            simulateCloudUploadConcurrent();
            
            return "Report download completed successfully";
        });
        
        // Simulate UI remaining responsive
        System.out.println("✅ UI immediately responsive - user can continue working!");
        
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(800);
                System.out.printf("👤 [UI Thread] User performs action %d (UI still responsive)%n", i);
                
                // Check if background task completed
                if (reportTask.isDone()) {
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Get final result
        try {
            String result = reportTask.get();
            System.out.printf("🎉 %s%n", result);
        } catch (InterruptedException | ExecutionException e) {
            System.err.printf("❌ Error: %s%n", e.getMessage());
        }
        
        System.out.println("✅ Solution: UI remained responsive throughout!");
        System.out.println("✅ Solution: User could continue working!");
    }
    
    private void simulateReportGenerationConcurrent() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void simulateFileSaveConcurrent() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void simulateCloudUploadConcurrent() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // SOLUTION 4: Multi-core utilization with parallel processing
    public void demonstrateMultiCoreUtilization() {
        System.out.println("\n--- Multi-Core Utilization ---");
        
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.printf("🖥️ Available CPU cores: %d%n", availableProcessors);
        
        System.out.println("🔄 Running CPU-intensive task on multiple threads...");
        long startTime = System.currentTimeMillis();
        
        // Divide work among multiple threads
        int range = 100000;
        int rangePerThread = range / availableProcessors;
        
        List<Future<Long>> futures = new ArrayList<>();
        
        for (int i = 0; i < availableProcessors; i++) {
            final int start = i * rangePerThread + 1;
            final int end = (i == availableProcessors - 1) ? range : (i + 1) * rangePerThread;
            
            futures.add(executor.submit(() -> computePrimeSumConcurrent(start, end)));
        }
        
        // Collect results from all threads
        long totalSum = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                long partialSum = futures.get(i).get();
                totalSum += partialSum;
                System.out.printf("  🧮 Thread %d contributed: %d%n", i + 1, partialSum);
            } catch (InterruptedException | ExecutionException e) {
                System.err.printf("❌ Error in thread %d: %s%n", i + 1, e.getMessage());
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ Multi-threaded computation time: %d ms%n", (endTime - startTime));
        System.out.printf("📊 Result: %d%n", totalSum);
        System.out.printf("✅ Solution: Utilized all %d CPU cores efficiently!%n", availableProcessors);
        System.out.println("✅ Solution: Significant performance improvement!");
    }
    
    private long computePrimeSumConcurrent(int start, int end) {
        String threadName = Thread.currentThread().getName();
        System.out.printf("  🔢 [%s] Computing primes from %d to %d%n", threadName, start, end);
        
        long sum = 0;
        for (int i = start; i <= end; i++) {
            if (isPrimeConcurrent(i)) {
                sum += i;
            }
        }
        
        System.out.printf("  ✅ [%s] Completed range %d-%d%n", threadName, start, end);
        return sum;
    }
    
    private boolean isPrimeConcurrent(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
```

## Core Thread Concepts

```java
// Comprehensive demonstration of thread basics

import java.util.concurrent.*;
import java.util.*;

class ThreadBasicsConcepts {
    
    public void demonstrateThreadBasics() {
        System.out.println("=== THREAD BASICS CONCEPTS ===");
        
        demonstrateMainThread();
        demonstrateThreadCreation();
        demonstrateThreadStates();
        demonstrateThreadProperties();
        demonstrateThreadCommunication();
    }
    
    // CONCEPT 1: Main thread and thread hierarchy
    public void demonstrateMainThread() {
        System.out.println("\n--- Main Thread Concepts ---");
        
        Thread mainThread = Thread.currentThread();
        System.out.printf("📍 Main thread name: %s%n", mainThread.getName());
        System.out.printf("📍 Main thread ID: %d%n", mainThread.getId());
        System.out.printf("📍 Main thread priority: %d%n", mainThread.getPriority());
        System.out.printf("📍 Main thread state: %s%n", mainThread.getState());
        System.out.printf("📍 Main thread group: %s%n", mainThread.getThreadGroup().getName());
        
        // Show active thread count
        System.out.printf("📊 Active threads in JVM: %d%n", Thread.activeCount());
        
        // Create child thread to show hierarchy
        Thread childThread = new Thread(() -> {
            Thread current = Thread.currentThread();
            System.out.printf("👶 Child thread name: %s%n", current.getName());
            System.out.printf("👶 Child thread parent group: %s%n", current.getThreadGroup().getName());
        }, "ChildThread-1");
        
        childThread.start();
        
        try {
            childThread.join(); // Wait for child to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Main thread continues after child completion");
    }
    
    // CONCEPT 2: Different ways to create threads
    public void demonstrateThreadCreation() {
        System.out.println("\n--- Thread Creation Methods ---");
        
        // Method 1: Extending Thread class
        System.out.println("🔧 Method 1: Extending Thread class");
        CustomThread customThread = new CustomThread("CustomWorker");
        customThread.start();
        
        // Method 2: Implementing Runnable interface
        System.out.println("🔧 Method 2: Implementing Runnable interface");
        CustomRunnable customRunnable = new CustomRunnable("RunnableWorker");
        Thread runnableThread = new Thread(customRunnable);
        runnableThread.start();
        
        // Method 3: Using lambda expression (modern approach)
        System.out.println("🔧 Method 3: Using lambda expression");
        Thread lambdaThread = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.printf("  🚀 [%s] Lambda thread executing%n", name);
            
            for (int i = 1; i <= 3; i++) {
                System.out.printf("  📊 [%s] Lambda work step %d%n", name, i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.printf("  ✅ [%s] Lambda thread completed%n", name);
        }, "LambdaWorker");
        
        lambdaThread.start();
        
        // Method 4: Using Callable with ExecutorService
        System.out.println("🔧 Method 4: Using Callable with ExecutorService");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        Future<String> callableResult = executor.submit(() -> {
            String name = Thread.currentThread().getName();
            System.out.printf("  🚀 [%s] Callable executing%n", name);
            
            Thread.sleep(800);
            
            return String.format("Callable completed on thread %s", name);
        });
        
        // Wait for all threads to complete
        waitForThreads(Arrays.asList(customThread, runnableThread, lambdaThread));
        
        try {
            String result = callableResult.get();
            System.out.printf("  ✅ Callable result: %s%n", result);
        } catch (InterruptedException | ExecutionException e) {
            System.err.printf("❌ Callable error: %s%n", e.getMessage());
        } finally {
            executor.shutdown();
        }
        
        System.out.println("✅ All thread creation methods demonstrated");
    }
    
    private void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Method 1 implementation
    static class CustomThread extends Thread {
        private final String workerName;
        
        public CustomThread(String workerName) {
            super(workerName);
            this.workerName = workerName;
        }
        
        @Override
        public void run() {
            System.out.printf("  🚀 [%s] CustomThread executing%n", workerName);
            
            for (int i = 1; i <= 3; i++) {
                System.out.printf("  📊 [%s] Custom work step %d%n", workerName, i);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.printf("  ✅ [%s] CustomThread completed%n", workerName);
        }
    }
    
    // Method 2 implementation
    static class CustomRunnable implements Runnable {
        private final String workerName;
        
        public CustomRunnable(String workerName) {
            this.workerName = workerName;
        }
        
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.printf("  🚀 [%s] CustomRunnable executing (%s)%n", threadName, workerName);
            
            for (int i = 1; i <= 3; i++) {
                System.out.printf("  📊 [%s] Runnable work step %d%n", threadName, i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.printf("  ✅ [%s] CustomRunnable completed%n", threadName);
        }
    }
    
    // CONCEPT 3: Thread states and lifecycle
    public void demonstrateThreadStates() {
        System.out.println("\n--- Thread States and Lifecycle ---");
        
        Thread stateMonitorThread = new Thread(() -> {
            String name = Thread.currentThread().getName();
            
            System.out.printf("  📍 [%s] Thread running (RUNNABLE state)%n", name);
            
            // Transition to TIMED_WAITING state
            try {
                System.out.printf("  😴 [%s] Going to sleep (TIMED_WAITING state)%n", name);
                Thread.sleep(1000);
                System.out.printf("  😊 [%s] Woke up from sleep%n", name);
            } catch (InterruptedException e) {
                System.out.printf("  ⚠️ [%s] Interrupted during sleep%n", name);
                Thread.currentThread().interrupt();
                return;
            }
            
            // Simulate some work
            System.out.printf("  🔄 [%s] Doing some work%n", name);
            for (int i = 0; i < 1000000; i++) {
                Math.sqrt(i);
            }
            
            System.out.printf("  ✅ [%s] Work completed%n", name);
        }, "StateMonitor");
        
        // Monitor thread states
        System.out.printf("🔍 Thread state before start: %s%n", stateMonitorThread.getState());
        
        stateMonitorThread.start();
        System.out.printf("🔍 Thread state after start: %s%n", stateMonitorThread.getState());
        
        // Monitor state changes
        new Thread(() -> {
            while (stateMonitorThread.isAlive()) {
                Thread.State currentState = stateMonitorThread.getState();
                System.out.printf("🔍 [Monitor] Current state: %s%n", currentState);
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.printf("🔍 [Monitor] Final state: %s%n", stateMonitorThread.getState());
        }, "StateWatcher").start();
        
        try {
            stateMonitorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Thread lifecycle demonstration completed");
    }
    
    // CONCEPT 4: Thread properties and configuration
    public void demonstrateThreadProperties() {
        System.out.println("\n--- Thread Properties ---");
        
        Thread propertyThread = new Thread(() -> {
            Thread current = Thread.currentThread();
            
            System.out.printf("  📋 Thread Name: %s%n", current.getName());
            System.out.printf("  📋 Thread ID: %d%n", current.getId());
            System.out.printf("  📋 Thread Priority: %d%n", current.getPriority());
            System.out.printf("  📋 Is Daemon: %s%n", current.isDaemon());
            System.out.printf("  📋 Is Alive: %s%n", current.isAlive());
            System.out.printf("  📋 Thread Group: %s%n", current.getThreadGroup().getName());
            
            System.out.println("  🔄 Performing thread work...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Configure thread properties
        propertyThread.setName("PropertyDemo");
        propertyThread.setPriority(Thread.MAX_PRIORITY);
        propertyThread.setDaemon(false);
        
        System.out.println("🔧 Thread configured with custom properties:");
        System.out.printf("   Name: %s%n", propertyThread.getName());
        System.out.printf("   Priority: %d%n", propertyThread.getPriority());
        System.out.printf("   Daemon: %s%n", propertyThread.isDaemon());
        
        propertyThread.start();
        
        // Demonstrate daemon thread
        Thread daemonThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("  👻 Daemon thread running...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("  👻 Daemon thread stopping...");
        }, "DaemonWorker");
        
        daemonThread.setDaemon(true);
        daemonThread.start();
        
        try {
            propertyThread.join();
            Thread.sleep(1500); // Let daemon run for a bit
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Main thread ending (daemon thread will stop automatically)");
    }
    
    // CONCEPT 5: Basic thread communication
    public void demonstrateThreadCommunication() {
        System.out.println("\n--- Basic Thread Communication ---");
        
        SharedCounter counter = new SharedCounter();
        
        // Producer thread
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                counter.increment();
                System.out.printf("  📈 Producer incremented counter to: %d%n", counter.getValue());
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("  ✅ Producer completed");
        }, "Producer");
        
        // Consumer thread
        Thread consumer = new Thread(() -> {
            int lastSeenValue = 0;
            
            while (lastSeenValue < 5) {
                int currentValue = counter.getValue();
                if (currentValue > lastSeenValue) {
                    System.out.printf("  📉 Consumer observed counter change: %d -> %d%n", 
                                     lastSeenValue, currentValue);
                    lastSeenValue = currentValue;
                }
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("  ✅ Consumer completed");
        }, "Consumer");
        
        // Monitor thread
        Thread monitor = new Thread(() -> {
            for (int i = 0; i < 8; i++) {
                System.out.printf("  👁️ Monitor: Counter value = %d%n", counter.getValue());
                
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("  ✅ Monitor completed");
        }, "Monitor");
        
        // Start all threads
        producer.start();
        consumer.start();
        monitor.start();
        
        // Wait for completion
        try {
            producer.join();
            consumer.join();
            monitor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.printf("🏁 Final counter value: %d%n", counter.getValue());
        System.out.println("✅ Thread communication demonstration completed");
    }
    
    // Helper class for thread communication
    static class SharedCounter {
        private int value = 0;
        
        public synchronized void increment() {
            value++;
        }
        
        public synchronized int getValue() {
            return value;
        }
    }
}
```

## Complete Thread Basics Demo

```java
public class CompleteThreadBasicsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE THREAD BASICS DEMONSTRATION ===");
        
        // 1. Single-threaded problems
        System.out.println("\n1. SINGLE-THREADED PROBLEMS");
        SingleThreadedProblems problems = new SingleThreadedProblems();
        problems.demonstrateProblems();
        
        System.out.println("\n" + "=".repeat(60));
        
        // 2. Multithreaded solutions
        System.out.println("\n2. MULTITHREADED SOLUTIONS");
        MultithreadedSolutions solutions = new MultithreadedSolutions();
        solutions.demonstrateSolutions();
        
        System.out.println("\n" + "=".repeat(60));
        
        // 3. Core thread concepts
        System.out.println("\n3. CORE THREAD CONCEPTS");
        ThreadBasicsConcepts concepts = new ThreadBasicsConcepts();
        concepts.demonstrateThreadBasics();
        
        System.out.println("\n=== DEMONSTRATION COMPLETED ===");
        printThreadingBestPractices();
    }
    
    private static void printThreadingBestPractices() {
        System.out.println("\n🎯 THREADING BEST PRACTICES:");
        System.out.println("✅ Use thread pools instead of creating raw threads");
        System.out.println("✅ Prefer Runnable/Callable over extending Thread class");
        System.out.println("✅ Handle InterruptedException properly");
        System.out.println("✅ Use appropriate thread synchronization mechanisms");
        System.out.println("✅ Avoid shared mutable state when possible");
        System.out.println("✅ Use immutable objects for thread safety");
        System.out.println("✅ Name your threads for easier debugging");
        System.out.println("✅ Set appropriate thread priorities carefully");
        
        System.out.println("\n❌ AVOID:");
        System.out.println("❌ Creating too many threads (causes context switching overhead)");
        System.out.println("❌ Ignoring InterruptedException");
        System.out.println("❌ Using stop(), suspend(), or resume() (deprecated)");
        System.out.println("❌ Accessing shared variables without synchronization");
        System.out.println("❌ Using busy waiting loops");
        System.out.println("❌ Creating threads without proper cleanup");
    }
}
```

## Interview Questions & Answers

**Q1: What is a thread and why do we need multithreading?**
**A:** A thread is a lightweight sub-process that enables concurrent execution. We need multithreading for: better resource utilization, responsive user interfaces, parallel processing on multi-core systems, and handling multiple tasks simultaneously.

**Q2: What are the different ways to create threads in Java?**
**A:** Four main ways: 1) Extend Thread class, 2) Implement Runnable interface, 3) Use lambda expressions with Thread constructor, 4) Use Callable with ExecutorService. Runnable is preferred as it allows implementing other interfaces.

**Q3: What are the different thread states in Java?**
**A:** Six states: NEW (created but not started), RUNNABLE (executing), BLOCKED (waiting for monitor lock), WAITING (waiting indefinitely), TIMED_WAITING (waiting for specified time), TERMINATED (completed execution).

**Q4: What's the difference between extending Thread and implementing Runnable?**
**A:** Implementing Runnable is preferred because: allows extending other classes, promotes composition over inheritance, supports multiple implementation possibilities, and enables better code reusability.

**Q5: What happens when you call run() instead of start()?**
**A:** Calling run() directly executes the method in the current thread (no new thread created), while start() creates a new thread and calls run() in that new thread context.

**Q6: What is the difference between daemon and user threads?**
**A:** User threads keep JVM alive until completion, while daemon threads run in background and JVM terminates when only daemon threads remain. Examples: garbage collector (daemon), main thread (user).

**Q7: How do you handle InterruptedException?**
**A:** Either propagate it by re-throwing, or restore interrupt status using Thread.currentThread().interrupt(). Never ignore it as it breaks the interruption contract.

**Q8: What are the advantages and disadvantages of multithreading?**
**A:** Advantages: parallelism, responsiveness, resource sharing. Disadvantages: complexity, synchronization overhead, potential race conditions, increased memory consumption.

## Key Takeaways

1. **Threads enable concurrent execution** improving application performance and responsiveness
2. **Multiple creation approaches exist** with Runnable interface being preferred
3. **Thread lifecycle has six distinct states** from NEW to TERMINATED
4. **Proper thread management prevents resource waste** and application crashes
5. **ExecutorService provides better thread management** than raw thread creation
6. **Daemon threads support background processing** without keeping JVM alive
7. **InterruptedException must be handled properly** to maintain interruption contracts
8. **Thread properties can be configured** for name, priority, and daemon status
9. **Thread communication requires coordination** through shared objects or messaging
10. **Best practices focus on safety and efficiency** avoiding common threading pitfalls

---

*Remember: Threads are like having multiple chefs in a kitchen - they can work together efficiently, but need proper coordination to avoid stepping on each other's toes!*