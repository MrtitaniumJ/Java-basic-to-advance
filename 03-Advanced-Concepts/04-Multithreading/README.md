# Multithreading in Java - Concurrent Programming

## Simple Explanation

Think of **Multithreading** as **multiple workers doing different tasks simultaneously**:

- **Thread** = Worker (can work independently on different tasks)
- **Multithreading** = Multiple workers working at the same time
- **Synchronization** = Coordination between workers (like taking turns using shared tools)
- **Deadlock** = Workers waiting for each other indefinitely (like traffic jam)
- **Thread pool** = Team of workers ready to take on tasks

### Real-World Analogies
- **Restaurant kitchen** = Multiple chefs (threads) preparing different dishes simultaneously
- **Assembly line** = Workers (threads) handling different parts of production
- **Call center** = Multiple operators (threads) handling calls concurrently
- **Hospital** = Doctors (threads) treating different patients at the same time

## Professional Definition

**Multithreading** in Java is a concurrent programming paradigm that enables multiple threads of execution to run simultaneously within a single process, sharing the same memory space while executing independently. It provides mechanisms for thread creation, synchronization, communication, and coordination through classes like Thread, Runnable, ExecutorService, and synchronization primitives like synchronized blocks, locks, and atomic variables.

## Why Multithreading is Essential

### Problems Without Multithreading:
```java
// WITHOUT MULTITHREADING - Sequential, blocking operations

import java.io.*;
import java.net.*;
import java.util.*;

class SequentialServer {
    private static final int PORT = 8080;
    
    public static void main(String[] args) {
        System.out.println("Starting sequential server...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);
            
            while (true) {
                // PROBLEM: Server can only handle ONE client at a time!
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                // Process client request (blocking operation)
                handleClient(clientSocket);
                
                // Next client must wait until current client is completely finished!
                clientSocket.close();
                System.out.println("Client disconnected");
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            String request = in.readLine();
            System.out.println("Processing request: " + request);
            
            // Simulate time-consuming processing
            Thread.sleep(5000); // 5 seconds - blocks entire server!
            
            out.println("Response: Processed " + request);
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Client handling error: " + e.getMessage());
        }
    }
}

class SequentialDataProcessor {
    public void processLargeDataset() {
        System.out.println("=== SEQUENTIAL PROCESSING ===");
        long startTime = System.currentTimeMillis();
        
        // Process 4 large files sequentially
        processFile("file1.dat");
        processFile("file2.dat");
        processFile("file3.dat");
        processFile("file4.dat");
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + "ms");
        
        // PROBLEMS:
        // 1. Each file processed one after another
        // 2. CPU cores underutilized
        // 3. Total time = sum of all individual times
        // 4. UI freezes during processing
        // 5. No progress feedback until completion
    }
    
    private void processFile(String filename) {
        System.out.println("Processing " + filename + "...");
        
        // Simulate heavy computation
        try {
            Thread.sleep(2000); // 2 seconds per file
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Finished " + filename);
    }
}

class SequentialUI {
    public static void main(String[] args) {
        System.out.println("=== SEQUENTIAL UI ===");
        
        SequentialUI ui = new SequentialUI();
        
        // Simulate user clicking "Process Data" button
        ui.onProcessButtonClick();
        
        // PROBLEM: UI becomes completely unresponsive!
        ui.updateUI(); // This won't execute until processing is done
    }
    
    public void onProcessButtonClick() {
        System.out.println("Processing data...");
        
        // Heavy computation on UI thread - BLOCKS EVERYTHING!
        for (int i = 0; i < 10; i++) {
            performHeavyComputation();
            System.out.println("Progress: " + ((i + 1) * 10) + "%");
        }
        
        System.out.println("Processing complete!");
    }
    
    private void performHeavyComputation() {
        // Simulate CPU-intensive work
        long sum = 0;
        for (int i = 0; i < 100_000_000; i++) {
            sum += i;
        }
    }
    
    public void updateUI() {
        System.out.println("UI updated"); // This is delayed until processing finishes
    }
}
```

### With Multithreading - Concurrent and Responsive:
```java
// WITH MULTITHREADING - Concurrent, non-blocking operations

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class MultiThreadedServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    
    public static void main(String[] args) {
        System.out.println("Starting multithreaded server...");
        
        // Create thread pool for handling clients
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);
            
            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                // Handle client in separate thread - NON-BLOCKING!
                threadPool.submit(new ClientHandler(clientSocket));
                
                // Server immediately ready for next client!
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
    
    static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                String request = in.readLine();
                System.out.println("Thread " + Thread.currentThread().getName() + 
                                 " processing: " + request);
                
                // Time-consuming processing - doesn't block other clients!
                Thread.sleep(5000);
                
                out.println("Response from " + Thread.currentThread().getName() + 
                          ": Processed " + request);
                
                clientSocket.close();
                System.out.println("Client handled by " + Thread.currentThread().getName());
                
            } catch (IOException | InterruptedException e) {
                System.err.println("Client handling error: " + e.getMessage());
            }
        }
    }
}

class ConcurrentDataProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    public void processLargeDataset() {
        System.out.println("=== CONCURRENT PROCESSING ===");
        long startTime = System.currentTimeMillis();
        
        // Process 4 large files concurrently
        Future<String> task1 = executor.submit(() -> processFile("file1.dat"));
        Future<String> task2 = executor.submit(() -> processFile("file2.dat"));
        Future<String> task3 = executor.submit(() -> processFile("file3.dat"));
        Future<String> task4 = executor.submit(() -> processFile("file4.dat"));
        
        try {
            // Wait for all tasks to complete
            String result1 = task1.get();
            String result2 = task2.get();
            String result3 = task3.get();
            String result4 = task4.get();
            
            long endTime = System.currentTimeMillis();
            System.out.println("All files processed concurrently!");
            System.out.println("Total time: " + (endTime - startTime) + "ms");
            
            // BENEFITS:
            // 1. All files processed simultaneously
            // 2. Optimal CPU utilization
            // 3. Total time ≈ max individual time (not sum)
            // 4. Better resource utilization
            // 5. Scalable performance
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Processing error: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
    
    private String processFile(String filename) {
        System.out.println("Thread " + Thread.currentThread().getName() + 
                         " processing " + filename + "...");
        
        try {
            Thread.sleep(2000); // 2 seconds per file
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted";
        }
        
        System.out.println("Thread " + Thread.currentThread().getName() + 
                         " finished " + filename);
        return "Processed " + filename;
    }
}

class ResponsiveUI {
    private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();
    
    public static void main(String[] args) {
        System.out.println("=== RESPONSIVE UI ===");
        
        ResponsiveUI ui = new ResponsiveUI();
        
        // Simulate user clicking "Process Data" button
        ui.onProcessButtonClick();
        
        // UI remains responsive!
        ui.updateUI(); // This executes immediately
        
        // Simulate user interactions while processing
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                ui.updateUI(); // UI updates continuously
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        ui.shutdown();
    }
    
    public void onProcessButtonClick() {
        System.out.println("Starting background processing...");
        
        // Process in background thread - UI REMAINS RESPONSIVE!
        backgroundExecutor.submit(() -> {
            for (int i = 0; i < 10; i++) {
                performHeavyComputation();
                int progress = (i + 1) * 10;
                
                // Update UI from background thread
                updateProgress(progress);
                
                try {
                    Thread.sleep(100); // Allow other operations
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.println("Background processing complete!");
        });
    }
    
    private void performHeavyComputation() {
        // CPU-intensive work in background
        long sum = 0;
        for (int i = 0; i < 50_000_000; i++) {
            sum += i;
        }
    }
    
    private void updateProgress(int progress) {
        System.out.println("Progress: " + progress + "%");
    }
    
    public void updateUI() {
        System.out.println("UI updated - remaining responsive!");
    }
    
    public void shutdown() {
        backgroundExecutor.shutdown();
    }
}
```

## Thread Creation Methods

### 1. Extending Thread Class
```java
import java.util.Random;

public class ThreadCreationMethods {
    
    // Method 1: Extending Thread class
    static class WorkerThread extends Thread {
        private final String taskName;
        private final int workDuration;
        
        public WorkerThread(String taskName, int workDuration) {
            this.taskName = taskName;
            this.workDuration = workDuration;
            setName("Worker-" + taskName); // Set thread name
        }
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " started " + taskName);
            
            try {
                // Simulate work
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - " + taskName + " step " + i + "/5");
                    Thread.sleep(workDuration);
                }
                
                System.out.println(Thread.currentThread().getName() + " completed " + taskName);
                
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + 
                                 " - " + taskName + " was interrupted");
                Thread.currentThread().interrupt(); // Restore interrupt status
            }
        }
    }
    
    // Method 2: Implementing Runnable interface
    static class TaskRunner implements Runnable {
        private final String taskName;
        private final int iterations;
        private volatile boolean running = true;
        
        public TaskRunner(String taskName, int iterations) {
            this.taskName = taskName;
            this.iterations = iterations;
        }
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " executing " + taskName);
            
            for (int i = 1; i <= iterations && running; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - " + taskName + " iteration " + i + "/" + iterations);
                    
                    // Simulate work
                    Thread.sleep(500);
                    
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - " + taskName + " interrupted at iteration " + i);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.println(Thread.currentThread().getName() + 
                             " finished " + taskName + " after " + iterations + " iterations");
        }
        
        public void stop() {
            running = false;
        }
    }
    
    // Method 3: Using Callable interface (returns result)
    static class CalculationTask implements Callable<Integer> {
        private final String taskName;
        private final int numbers;
        
        public CalculationTask(String taskName, int numbers) {
            this.taskName = taskName;
            this.numbers = numbers;
        }
        
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + 
                             " starting calculation: " + taskName);
            
            int result = 0;
            Random random = new Random();
            
            for (int i = 0; i < numbers; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Calculation interrupted");
                }
                
                int number = random.nextInt(100);
                result += number;
                
                // Simulate processing time
                Thread.sleep(50);
                
                if ((i + 1) % 10 == 0) {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - " + taskName + " processed " + (i + 1) + "/" + numbers);
                }
            }
            
            System.out.println(Thread.currentThread().getName() + 
                             " completed calculation: " + taskName + " = " + result);
            return result;
        }
    }
    
    // Method 4: Lambda expressions (Java 8+)
    public static void demonstrateLambdaThreads() {
        System.out.println("\n=== LAMBDA THREADS ===");
        
        // Simple lambda thread
        Thread lambdaThread1 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + 
                                 " - Lambda task iteration " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Lambda-Thread-1");
        
        // Lambda with parameter capture
        String message = "Hello from captured variable";
        Thread lambdaThread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + message);
            
            // Demonstrate thread-local operations
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + 
                                 " processing item " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Lambda-Thread-2");
        
        lambdaThread1.start();
        lambdaThread2.start();
        
        try {
            lambdaThread1.join();
            lambdaThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void demonstrateThreadCreation() {
        System.out.println("=== THREAD CREATION METHODS ===");
        
        // Method 1: Extending Thread
        WorkerThread worker1 = new WorkerThread("FileProcessor", 800);
        WorkerThread worker2 = new WorkerThread("DataValidator", 600);
        
        worker1.start();
        worker2.start();
        
        // Method 2: Implementing Runnable
        TaskRunner task1 = new TaskRunner("EmailSender", 4);
        TaskRunner task2 = new TaskRunner("ReportGenerator", 3);
        
        Thread thread1 = new Thread(task1, "Email-Thread");
        Thread thread2 = new Thread(task2, "Report-Thread");
        
        thread1.start();
        thread2.start();
        
        // Method 3: Using ExecutorService with Callable
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        Future<Integer> calculation1 = executor.submit(new CalculationTask("Sum-A", 20));
        Future<Integer> calculation2 = executor.submit(new CalculationTask("Sum-B", 25));
        Future<Integer> calculation3 = executor.submit(new CalculationTask("Sum-C", 15));
        
        try {
            // Wait for workers to complete
            worker1.join();
            worker2.join();
            
            // Wait for task runners
            thread1.join();
            thread2.join();
            
            // Get calculation results
            System.out.println("\nCalculation Results:");
            System.out.println("Sum-A result: " + calculation1.get());
            System.out.println("Sum-B result: " + calculation2.get());
            System.out.println("Sum-C result: " + calculation3.get());
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Thread execution error: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
        
        // Method 4: Lambda expressions
        demonstrateLambdaThreads();
    }
}
```

### 2. Thread States and Lifecycle
```java
public class ThreadLifecycleDemo {
    
    static class LifecycleThread extends Thread {
        private final Object lock = new Object();
        private volatile boolean shouldWait = false;
        private volatile boolean shouldTerminate = false;
        
        public LifecycleThread(String name) {
            super(name);
        }
        
        @Override
        public void run() {
            System.out.println(getName() + " entered RUNNABLE state");
            
            try {
                // RUNNABLE state - executing
                for (int i = 1; i <= 10; i++) {
                    System.out.println(getName() + " - Step " + i + 
                                     " (State: " + getState() + ")");
                    
                    Thread.sleep(500); // TIMED_WAITING state
                    
                    // Check for wait condition
                    synchronized (lock) {
                        while (shouldWait && !shouldTerminate) {
                            System.out.println(getName() + " entering WAITING state");
                            lock.wait(); // WAITING state
                            System.out.println(getName() + " resumed from WAITING state");
                        }
                    }
                    
                    if (shouldTerminate) {
                        System.out.println(getName() + " terminating early");
                        break;
                    }
                }
                
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted");
                Thread.currentThread().interrupt();
            }
            
            System.out.println(getName() + " finished - will enter TERMINATED state");
        }
        
        public void pauseThread() {
            synchronized (lock) {
                shouldWait = true;
            }
        }
        
        public void resumeThread() {
            synchronized (lock) {
                shouldWait = false;
                lock.notify();
            }
        }
        
        public void terminateThread() {
            synchronized (lock) {
                shouldTerminate = true;
                if (shouldWait) {
                    shouldWait = false;
                    lock.notify();
                }
            }
        }
    }
    
    public static void demonstrateThreadStates() {
        System.out.println("=== THREAD LIFECYCLE STATES ===");
        
        LifecycleThread thread = new LifecycleThread("Lifecycle-Demo");
        
        // NEW state
        System.out.println("Thread created - State: " + thread.getState()); // NEW
        
        // Start thread (NEW -> RUNNABLE)
        thread.start();
        System.out.println("Thread started - State: " + thread.getState()); // RUNNABLE
        
        try {
            // Let thread run for a while
            Thread.sleep(2000);
            
            // Check state during sleep (should be TIMED_WAITING)
            System.out.println("During execution - State: " + thread.getState());
            
            // Pause the thread (RUNNABLE -> WAITING)
            thread.pauseThread();
            System.out.println("Thread paused - State: " + thread.getState());
            
            Thread.sleep(1000);
            
            // Resume the thread (WAITING -> RUNNABLE)
            thread.resumeThread();
            System.out.println("Thread resumed - State: " + thread.getState());
            
            Thread.sleep(2000);
            
            // Terminate thread early
            thread.terminateThread();
            
            // Wait for thread to complete
            thread.join();
            System.out.println("Thread completed - State: " + thread.getState()); // TERMINATED
            
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
    
    // Demonstrate thread priorities and daemon threads
    static class PriorityThread extends Thread {
        private final int iterations;
        
        public PriorityThread(String name, int priority, int iterations, boolean daemon) {
            super(name);
            setPriority(priority);
            setDaemon(daemon);
            this.iterations = iterations;
        }
        
        @Override
        public void run() {
            System.out.println(getName() + " started (Priority: " + getPriority() + 
                             ", Daemon: " + isDaemon() + ")");
            
            for (int i = 1; i <= iterations; i++) {
                System.out.println(getName() + " - Iteration " + i + "/" + iterations);
                
                // Yield to allow other threads to run
                if (i % 2 == 0) {
                    Thread.yield();
                }
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(getName() + " interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            System.out.println(getName() + " completed");
        }
    }
    
    public static void demonstratePriorityAndDaemon() {
        System.out.println("\n=== THREAD PRIORITY AND DAEMON ===");
        
        // Create threads with different priorities
        PriorityThread lowPriority = new PriorityThread("Low-Priority", Thread.MIN_PRIORITY, 5, false);
        PriorityThread normalPriority = new PriorityThread("Normal-Priority", Thread.NORM_PRIORITY, 5, false);
        PriorityThread highPriority = new PriorityThread("High-Priority", Thread.MAX_PRIORITY, 5, false);
        
        // Create a daemon thread
        PriorityThread daemonThread = new PriorityThread("Daemon-Thread", Thread.NORM_PRIORITY, 20, true);
        
        System.out.println("Starting threads with different priorities...");
        
        // Start all threads
        lowPriority.start();
        normalPriority.start();
        highPriority.start();
        daemonThread.start();
        
        try {
            // Wait for non-daemon threads to complete
            lowPriority.join();
            normalPriority.join();
            highPriority.join();
            
            System.out.println("All non-daemon threads completed");
            System.out.println("Daemon thread state: " + daemonThread.getState());
            
            // JVM will exit even if daemon thread is still running
            
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}
```

## Thread Synchronization

### 1. Synchronized Methods and Blocks
```java
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSynchronizationDemo {
    
    // PROBLEM: Race condition without synchronization
    static class UnsafeCounter {
        private int count = 0;
        
        public void increment() {
            count++; // NOT atomic! Read-modify-write operation
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // SOLUTION 1: Synchronized methods
    static class SafeCounterSyncMethods {
        private int count = 0;
        
        public synchronized void increment() {
            count++; // Now atomic due to synchronization
        }
        
        public synchronized void decrement() {
            count--;
        }
        
        public synchronized int getCount() {
            return count;
        }
        
        public synchronized void addValue(int value) {
            count += value;
        }
    }
    
    // SOLUTION 2: Synchronized blocks
    static class SafeCounterSyncBlocks {
        private int count = 0;
        private final Object lock = new Object();
        
        public void increment() {
            synchronized (lock) {
                count++;
            }
        }
        
        public void decrement() {
            synchronized (lock) {
                count--;
            }
        }
        
        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
        
        public void performComplexOperation(int a, int b) {
            // Some non-synchronized work
            int result = a * b;
            
            // Only synchronize critical section
            synchronized (lock) {
                count += result;
            }
            
            // More non-synchronized work
            System.out.println("Operation completed");
        }
    }
    
    // SOLUTION 3: Different locks for different operations
    static class MultiLockCounter {
        private int count = 0;
        private int total = 0;
        
        private final Object countLock = new Object();
        private final Object totalLock = new Object();
        
        public void incrementCount() {
            synchronized (countLock) {
                count++;
            }
        }
        
        public void addToTotal(int value) {
            synchronized (totalLock) {
                total += value;
            }
        }
        
        public void transferCountToTotal() {
            // Nested synchronization - be careful of deadlock!
            // Always acquire locks in same order
            synchronized (countLock) {
                synchronized (totalLock) {
                    total += count;
                    count = 0;
                }
            }
        }
        
        public String getStatus() {
            synchronized (countLock) {
                synchronized (totalLock) {
                    return "Count: " + count + ", Total: " + total;
                }
            }
        }
    }
    
    // Bank account example with synchronization
    static class BankAccount {
        private double balance;
        private final String accountNumber;
        private final Object balanceLock = new Object();
        
        public BankAccount(String accountNumber, double initialBalance) {
            this.accountNumber = accountNumber;
            this.balance = initialBalance;
        }
        
        public void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            
            synchronized (balanceLock) {
                double oldBalance = balance;
                balance += amount;
                System.out.println(Thread.currentThread().getName() + 
                                 " - Deposited $" + amount + 
                                 " to " + accountNumber + 
                                 " (Balance: $" + oldBalance + " -> $" + balance + ")");
            }
        }
        
        public boolean withdraw(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
            
            synchronized (balanceLock) {
                if (balance >= amount) {
                    double oldBalance = balance;
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + 
                                     " - Withdrew $" + amount + 
                                     " from " + accountNumber + 
                                     " (Balance: $" + oldBalance + " -> $" + balance + ")");
                    return true;
                } else {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - Insufficient funds for withdrawal of $" + amount + 
                                     " from " + accountNumber + 
                                     " (Balance: $" + balance + ")");
                    return false;
                }
            }
        }
        
        public double getBalance() {
            synchronized (balanceLock) {
                return balance;
            }
        }
        
        // Transfer between accounts - must avoid deadlock!
        public static boolean transfer(BankAccount from, BankAccount to, double amount) {
            // Avoid deadlock by always acquiring locks in same order
            BankAccount firstLock = from.accountNumber.compareTo(to.accountNumber) < 0 ? from : to;
            BankAccount secondLock = from.accountNumber.compareTo(to.accountNumber) < 0 ? to : from;
            
            synchronized (firstLock.balanceLock) {
                synchronized (secondLock.balanceLock) {
                    if (from.balance >= amount) {
                        from.balance -= amount;
                        to.balance += amount;
                        
                        System.out.println(Thread.currentThread().getName() + 
                                         " - Transferred $" + amount + 
                                         " from " + from.accountNumber + 
                                         " to " + to.accountNumber);
                        return true;
                    } else {
                        System.out.println(Thread.currentThread().getName() + 
                                         " - Transfer failed: Insufficient funds in " + 
                                         from.accountNumber);
                        return false;
                    }
                }
            }
        }
    }
    
    public static void demonstrateRaceCondition() {
        System.out.println("=== RACE CONDITION DEMONSTRATION ===");
        
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        SafeCounterSyncMethods safeCounter = new SafeCounterSyncMethods();
        
        final int THREADS = 10;
        final int INCREMENTS_PER_THREAD = 1000;
        
        Thread[] unsafeThreads = new Thread[THREADS];
        Thread[] safeThreads = new Thread[THREADS];
        
        // Create unsafe threads
        for (int i = 0; i < THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    unsafeCounter.increment();
                }
            }, "Unsafe-" + i);
        }
        
        // Create safe threads
        for (int i = 0; i < THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    safeCounter.increment();
                }
            }, "Safe-" + i);
        }
        
        // Start all threads
        long startTime = System.currentTimeMillis();
        
        for (Thread thread : unsafeThreads) {
            thread.start();
        }
        for (Thread thread : safeThreads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        try {
            for (Thread thread : unsafeThreads) {
                thread.join();
            }
            for (Thread thread : safeThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        
        int expectedCount = THREADS * INCREMENTS_PER_THREAD;
        System.out.println("Expected count: " + expectedCount);
        System.out.println("Unsafe counter final count: " + unsafeCounter.getCount());
        System.out.println("Safe counter final count: " + safeCounter.getCount());
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        
        if (unsafeCounter.getCount() != expectedCount) {
            System.out.println("❌ Race condition detected! Unsafe counter lost updates.");
        }
        if (safeCounter.getCount() == expectedCount) {
            System.out.println("✅ Synchronization working! Safe counter accurate.");
        }
    }
    
    public static void demonstrateBankAccountSynchronization() {
        System.out.println("\n=== BANK ACCOUNT SYNCHRONIZATION ===");
        
        BankAccount account1 = new BankAccount("ACC001", 1000.0);
        BankAccount account2 = new BankAccount("ACC002", 500.0);
        BankAccount account3 = new BankAccount("ACC003", 750.0);
        
        ExecutorService executor = Executors.newFixedThreadPool(6);
        
        // Multiple concurrent operations on accounts
        for (int i = 0; i < 5; i++) {
            final int operation = i;
            
            executor.submit(() -> {
                switch (operation % 4) {
                    case 0:
                        account1.deposit(100.0);
                        break;
                    case 1:
                        account2.withdraw(50.0);
                        break;
                    case 2:
                        BankAccount.transfer(account1, account2, 75.0);
                        break;
                    case 3:
                        BankAccount.transfer(account3, account1, 25.0);
                        break;
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nFinal balances:");
        System.out.println("Account 1: $" + account1.getBalance());
        System.out.println("Account 2: $" + account2.getBalance());
        System.out.println("Account 3: $" + account3.getBalance());
    }
}
```

### 2. Wait-Notify Pattern and Producer-Consumer
```java
import java.util.*;
import java.util.concurrent.*;

public class WaitNotifyDemo {
    
    // Classic Producer-Consumer problem
    static class ProducerConsumer {
        private final Queue<Integer> buffer = new LinkedList<>();
        private final int capacity;
        private final Object lock = new Object();
        
        public ProducerConsumer(int capacity) {
            this.capacity = capacity;
        }
        
        public void produce(int item) throws InterruptedException {
            synchronized (lock) {
                // Wait while buffer is full
                while (buffer.size() == capacity) {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - Buffer full, producer waiting...");
                    lock.wait();
                }
                
                // Add item to buffer
                buffer.offer(item);
                System.out.println(Thread.currentThread().getName() + 
                                 " - Produced: " + item + 
                                 " (Buffer size: " + buffer.size() + ")");
                
                // Notify waiting consumers
                lock.notifyAll();
            }
        }
        
        public int consume() throws InterruptedException {
            synchronized (lock) {
                // Wait while buffer is empty
                while (buffer.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + 
                                     " - Buffer empty, consumer waiting...");
                    lock.wait();
                }
                
                // Remove item from buffer
                int item = buffer.poll();
                System.out.println(Thread.currentThread().getName() + 
                                 " - Consumed: " + item + 
                                 " (Buffer size: " + buffer.size() + ")");
                
                // Notify waiting producers
                lock.notifyAll();
                return item;
            }
        }
        
        public int getBufferSize() {
            synchronized (lock) {
                return buffer.size();
            }
        }
    }
    
    static class Producer implements Runnable {
        private final ProducerConsumer pc;
        private final String name;
        private final int itemsToProduce;
        
        public Producer(ProducerConsumer pc, String name, int itemsToProduce) {
            this.pc = pc;
            this.name = name;
            this.itemsToProduce = itemsToProduce;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 1; i <= itemsToProduce; i++) {
                    int item = Integer.parseInt(name.substring(name.length() - 1)) * 100 + i;
                    pc.produce(item);
                    
                    // Random production delay
                    Thread.sleep(new Random().nextInt(1000) + 500);
                }
                System.out.println(Thread.currentThread().getName() + " finished producing");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class Consumer implements Runnable {
        private final ProducerConsumer pc;
        private final String name;
        private final int itemsToConsume;
        
        public Consumer(ProducerConsumer pc, String name, int itemsToConsume) {
            this.pc = pc;
            this.name = name;
            this.itemsToConsume = itemsToConsume;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 1; i <= itemsToConsume; i++) {
                    int item = pc.consume();
                    
                    // Simulate processing time
                    Thread.sleep(new Random().nextInt(800) + 300);
                }
                System.out.println(Thread.currentThread().getName() + " finished consuming");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Blocking Queue implementation using wait-notify
    static class CustomBlockingQueue<T> {
        private final Queue<T> queue = new LinkedList<>();
        private final int capacity;
        private final Object lock = new Object();
        
        public CustomBlockingQueue(int capacity) {
            this.capacity = capacity;
        }
        
        public void put(T item) throws InterruptedException {
            synchronized (lock) {
                while (queue.size() == capacity) {
                    lock.wait();
                }
                queue.offer(item);
                lock.notifyAll();
            }
        }
        
        public T take() throws InterruptedException {
            synchronized (lock) {
                while (queue.isEmpty()) {
                    lock.wait();
                }
                T item = queue.poll();
                lock.notifyAll();
                return item;
            }
        }
        
        public int size() {
            synchronized (lock) {
                return queue.size();
            }
        }
        
        public boolean isEmpty() {
            synchronized (lock) {
                return queue.isEmpty();
            }
        }
    }
    
    // Work scheduling system using wait-notify
    static class TaskScheduler {
        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Object lock = new Object();
        private volatile boolean shutdown = false;
        
        public void submitTask(Runnable task) {
            synchronized (lock) {
                if (!shutdown) {
                    tasks.offer(task);
                    System.out.println("Task submitted. Queue size: " + tasks.size());
                    lock.notify(); // Wake up one waiting worker
                }
            }
        }
        
        public Runnable getNextTask() throws InterruptedException {
            synchronized (lock) {
                while (tasks.isEmpty() && !shutdown) {
                    System.out.println(Thread.currentThread().getName() + " waiting for tasks...");
                    lock.wait();
                }
                
                if (!tasks.isEmpty()) {
                    return tasks.poll();
                } else {
                    return null; // Shutdown signal
                }
            }
        }
        
        public void shutdown() {
            synchronized (lock) {
                shutdown = true;
                lock.notifyAll(); // Wake up all waiting workers
                System.out.println("Scheduler shutdown initiated");
            }
        }
        
        public int getQueueSize() {
            synchronized (lock) {
                return tasks.size();
            }
        }
    }
    
    static class Worker implements Runnable {
        private final TaskScheduler scheduler;
        private final String workerName;
        
        public Worker(TaskScheduler scheduler, String workerName) {
            this.scheduler = scheduler;
            this.workerName = workerName;
        }
        
        @Override
        public void run() {
            System.out.println(workerName + " started");
            
            try {
                while (true) {
                    Runnable task = scheduler.getNextTask();
                    if (task == null) {
                        // Shutdown signal
                        break;
                    }
                    
                    System.out.println(workerName + " executing task");
                    task.run();
                    System.out.println(workerName + " completed task");
                }
            } catch (InterruptedException e) {
                System.out.println(workerName + " interrupted");
                Thread.currentThread().interrupt();
            }
            
            System.out.println(workerName + " stopped");
        }
    }
    
    public static void demonstrateProducerConsumer() {
        System.out.println("=== PRODUCER-CONSUMER PATTERN ===");
        
        ProducerConsumer pc = new ProducerConsumer(5); // Buffer capacity of 5
        
        // Create producers and consumers
        Thread producer1 = new Thread(new Producer(pc, "Producer-1", 8), "Producer-1");
        Thread producer2 = new Thread(new Producer(pc, "Producer-2", 6), "Producer-2");
        
        Thread consumer1 = new Thread(new Consumer(pc, "Consumer-1", 7), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer(pc, "Consumer-2", 7), "Consumer-2");
        
        // Start all threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        
        try {
            // Wait for all to complete
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
            
            System.out.println("Final buffer size: " + pc.getBufferSize());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void demonstrateTaskScheduler() {
        System.out.println("\n=== TASK SCHEDULER WITH WAIT-NOTIFY ===");
        
        TaskScheduler scheduler = new TaskScheduler();
        
        // Create worker threads
        Thread worker1 = new Thread(new Worker(scheduler, "Worker-1"));
        Thread worker2 = new Thread(new Worker(scheduler, "Worker-2"));
        Thread worker3 = new Thread(new Worker(scheduler, "Worker-3"));
        
        worker1.start();
        worker2.start();
        worker3.start();
        
        // Submit tasks
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            scheduler.submitTask(() -> {
                System.out.println("  Executing task " + taskId + 
                                 " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            try {
                Thread.sleep(200); // Delay between task submissions
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Let workers process for a while
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Shutdown scheduler
        scheduler.shutdown();
        
        try {
            worker1.join();
            worker2.join();
            worker3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("All workers stopped");
    }
}
```

## Complete Multithreading Demo

```java
public class CompleteMultithreadingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE MULTITHREADING DEMONSTRATION ===");
        
        // 1. Thread Creation Methods
        System.out.println("\n1. THREAD CREATION METHODS");
        ThreadCreationMethods.demonstrateThreadCreation();
        
        waitForUser("\nPress Enter to continue to Thread States demonstration...");
        
        // 2. Thread States and Lifecycle
        System.out.println("\n2. THREAD STATES AND LIFECYCLE");
        ThreadLifecycleDemo.demonstrateThreadStates();
        ThreadLifecycleDemo.demonstratePriorityAndDaemon();
        
        waitForUser("\nPress Enter to continue to Synchronization demonstration...");
        
        // 3. Thread Synchronization
        System.out.println("\n3. THREAD SYNCHRONIZATION");
        ThreadSynchronizationDemo.demonstrateRaceCondition();
        ThreadSynchronizationDemo.demonstrateBankAccountSynchronization();
        
        waitForUser("\nPress Enter to continue to Wait-Notify demonstration...");
        
        // 4. Wait-Notify Pattern
        System.out.println("\n4. WAIT-NOTIFY PATTERN");
        WaitNotifyDemo.demonstrateProducerConsumer();
        WaitNotifyDemo.demonstrateTaskScheduler();
        
        System.out.println("\n=== MULTITHREADING DEMONSTRATION COMPLETED ===");
    }
    
    private static void waitForUser(String message) {
        System.out.println(message);
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
}
```

## Interview Questions & Answers

**Q1: What is multithreading? What are its advantages?**
**A:** Multithreading allows multiple threads to execute concurrently within a process. Advantages: better CPU utilization, improved responsiveness, concurrent processing, resource sharing, and better user experience.

**Q2: What's the difference between Thread and Runnable?**
**A:** Thread is a class you extend; Runnable is an interface you implement. Runnable is preferred because Java supports single inheritance but multiple interface implementation, allowing more flexible design.

**Q3: What is synchronization? Why is it needed?**
**A:** Synchronization controls access to shared resources to prevent race conditions and data inconsistency. It's needed when multiple threads access shared mutable data simultaneously.

**Q4: Explain wait(), notify(), and notifyAll() methods.**
**A:** wait() makes current thread wait until notified; notify() wakes up one waiting thread; notifyAll() wakes up all waiting threads. Must be called within synchronized block/method.

**Q5: What is a deadlock? How can you prevent it?**
**A:** Deadlock occurs when two or more threads wait for each other indefinitely. Prevention: acquire locks in same order, use timeout, avoid nested locks, use concurrent utilities.

**Q6: What's the difference between synchronized method and synchronized block?**
**A:** Synchronized method synchronizes entire method; synchronized block synchronizes only specific code section. Blocks offer finer control and better performance.

**Q7: What is thread pool? What are its advantages?**
**A:** Thread pool is a collection of pre-created threads that can be reused. Advantages: reduced thread creation overhead, better resource management, improved performance, controlled concurrency.

**Q8: What is volatile keyword? When to use it?**
**A:** Volatile ensures variable visibility across threads and prevents instruction reordering. Use for flags, status variables, or any shared variable where you need visibility guarantees without full synchronization.

## Key Takeaways

1. **Multithreading enables concurrent execution** and better resource utilization
2. **Synchronization prevents race conditions** and ensures data consistency
3. **Wait-notify pattern enables thread coordination** and communication
4. **Thread pools improve performance** by reusing threads and managing resources
5. **Deadlock prevention requires careful lock ordering** and design
6. **Volatile keyword ensures visibility** for shared variables across threads
7. **Producer-consumer pattern solves coordination** between data generators and processors
8. **Thread states and lifecycle management** are crucial for proper thread handling
9. **ExecutorService provides high-level threading utilities** for enterprise applications
10. **Proper exception handling in threads** prevents resource leaks and unexpected termination

---

*Remember: Multithreading is like orchestra coordination - all musicians (threads) must play in harmony (synchronization) to create beautiful music (efficient program execution)!*