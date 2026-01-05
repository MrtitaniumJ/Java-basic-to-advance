# Synchronization in Java

## Simple Explanation

Think of **synchronization** as **traffic control systems**:

- **Unsynchronized access** = Multiple cars trying to use same lane simultaneously (crashes!)
- **Synchronized access** = Traffic light controlling who can go when (safe and orderly)
- **Critical section** = Intersection where only one car can be at a time
- **Lock** = Traffic light that prevents conflicts
- **Thread safety** = Ensuring no accidents happen when multiple "cars" (threads) share the same "road" (resource)

### Real-World Analogies
- **ATM machine** = Only one person can use it at a time
- **Bathroom door lock** = Prevents multiple people from entering simultaneously
- **Conference room booking** = Only one meeting can happen at a time
- **Parking space** = Only one car can occupy the same spot

## Professional Definition

**Synchronization** is a mechanism that controls access to shared resources in a multithreaded environment to prevent race conditions, data corruption, and inconsistent states. It ensures that critical sections of code are executed by only one thread at a time, maintaining data integrity and thread safety through various locking mechanisms, atomic operations, and coordination primitives.

## Why Synchronization is Critical

### Problems Without Synchronization:
```java
// PROBLEMATIC CODE - Race conditions and data corruption

import java.util.*;
import java.util.concurrent.*;

class UnsynchronizedProblems {
    
    public void demonstrateProblems() {
        System.out.println("=== UNSYNCHRONIZED ACCESS PROBLEMS ===");
        
        demonstrateRaceConditions();
        demonstrateDataCorruption();
        demonstrateInconsistentState();
        demonstrateVisibilityProblems();
    }
    
    // PROBLEM 1: Race conditions with shared counter
    public void demonstrateRaceConditions() {
        System.out.println("\n--- Race Condition Problem ---");
        
        UnsafeCounter counter = new UnsafeCounter();
        int numThreads = 10;
        int incrementsPerThread = 1000;
        
        System.out.printf("🔄 Starting %d threads, each incrementing %d times%n", 
                         numThreads, incrementsPerThread);
        System.out.printf("📊 Expected final value: %d%n", numThreads * incrementsPerThread);
        
        List<Thread> threads = new ArrayList<>();
        
        // Create threads that increment counter
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment(); // RACE CONDITION!
                }
            }, "Incrementer-" + i);
            
            threads.add(thread);
        }
        
        // Start all threads
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long endTime = System.currentTimeMillis();
        int finalValue = counter.getValue();
        
        System.out.printf("⏱️ Execution time: %d ms%n", (endTime - startTime));
        System.out.printf("📊 Actual final value: %d%n", finalValue);
        System.out.printf("❌ Lost increments: %d%n", (numThreads * incrementsPerThread) - finalValue);
        System.out.println("❌ Problem: Race condition caused lost updates!");
    }
    
    // PROBLEM 2: Data corruption in collections
    public void demonstrateDataCorruption() {
        System.out.println("\n--- Data Corruption Problem ---");
        
        UnsafeShoppingCart cart = new UnsafeShoppingCart();
        int numThreads = 5;
        
        System.out.printf("🔄 %d threads simultaneously adding items to shopping cart%n", numThreads);
        
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                // Each thread adds different items
                for (int j = 1; j <= 10; j++) {
                    String item = String.format("Thread%d-Item%d", threadId, j);
                    cart.addItem(item); // CONCURRENT MODIFICATION!
                    
                    // Also try to get total count
                    int count = cart.getItemCount(); // INCONSISTENT READS!
                    
                    if (j % 3 == 0) {
                        System.out.printf("  [Thread-%d] Added %s, Total items: %d%n", 
                                         threadId, item, count);
                    }
                }
            }, "CartUser-" + i);
            
            threads.add(thread);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException | ConcurrentModificationException e) {
                System.out.printf("❌ Thread crashed: %s%n", e.getClass().getSimpleName());
            }
        }
        
        System.out.printf("📊 Expected total items: %d%n", numThreads * 10);
        System.out.printf("📊 Actual total items: %d%n", cart.getItemCount());
        System.out.println("❌ Problem: Data corruption and inconsistent state!");
        
        // Show some items to demonstrate corruption
        System.out.println("📝 Sample items (may be corrupted):");
        List<String> items = cart.getItems();
        for (int i = 0; i < Math.min(5, items.size()); i++) {
            System.out.printf("   %d. %s%n", i + 1, items.get(i));
        }
    }
    
    // PROBLEM 3: Inconsistent state in bank account
    public void demonstrateInconsistentState() {
        System.out.println("\n--- Inconsistent State Problem ---");
        
        UnsafeBankAccount account1 = new UnsafeBankAccount("Account-1", 1000);
        UnsafeBankAccount account2 = new UnsafeBankAccount("Account-2", 1000);
        
        System.out.printf("💰 Initial balances: %s=$%.2f, %s=$%.2f%n", 
                         account1.getAccountName(), account1.getBalance(),
                         account2.getAccountName(), account2.getBalance());
        
        double initialTotal = account1.getBalance() + account2.getBalance();
        System.out.printf("💰 Initial total: $%.2f%n", initialTotal);
        
        // Multiple threads transferring money between accounts
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            // Thread transferring from account1 to account2
            Thread thread1 = new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    double amount = 10.0;
                    transferMoney(account1, account2, amount); // RACE CONDITION!
                }
            }, "Transfer1to2-" + i);
            
            // Thread transferring from account2 to account1  
            Thread thread2 = new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    double amount = 10.0;
                    transferMoney(account2, account1, amount); // RACE CONDITION!
                }
            }, "Transfer2to1-" + i);
            
            threads.add(thread1);
            threads.add(thread2);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        double finalTotal = account1.getBalance() + account2.getBalance();
        
        System.out.printf("💰 Final balances: %s=$%.2f, %s=$%.2f%n", 
                         account1.getAccountName(), account1.getBalance(),
                         account2.getAccountName(), account2.getBalance());
        System.out.printf("💰 Final total: $%.2f%n", finalTotal);
        System.out.printf("❌ Money lost/gained: $%.2f%n", finalTotal - initialTotal);
        System.out.println("❌ Problem: Money appeared/disappeared due to race conditions!");
    }
    
    private void transferMoney(UnsafeBankAccount from, UnsafeBankAccount to, double amount) {
        // PROBLEMATIC: Two separate operations that should be atomic
        from.withdraw(amount);    // Operation 1
        to.deposit(amount);       // Operation 2
        // Another thread could read balances between these operations!
    }
    
    // PROBLEM 4: Visibility problems
    public void demonstrateVisibilityProblems() {
        System.out.println("\n--- Memory Visibility Problem ---");
        
        VisibilityProblemDemo demo = new VisibilityProblemDemo();
        
        // Thread that changes the flag
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("  ✍️ Writer thread: Setting stop flag to true");
                demo.setStopFlag(true); // May not be visible to reader thread!
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Writer");
        
        // Thread that reads the flag
        Thread reader = new Thread(() -> {
            System.out.println("  👁️ Reader thread: Starting to watch flag");
            int iterations = 0;
            
            while (!demo.isStopFlag()) {
                iterations++;
                // May never see the flag change due to visibility problems!
                if (iterations % 1000000 == 0) {
                    System.out.printf("  👁️ Reader thread: Still running (iterations: %d)%n", iterations);
                }
            }
            
            System.out.printf("  👁️ Reader thread: Stopped after %d iterations%n", iterations);
        }, "Reader");
        
        writer.start();
        reader.start();
        
        try {
            writer.join();
            // Give reader thread some time to see the change
            reader.join(3000); // Wait up to 3 seconds
            
            if (reader.isAlive()) {
                System.out.println("❌ Reader thread is still running - visibility problem!");
                System.out.println("❌ Reader thread cannot see the flag change!");
                reader.interrupt(); // Force stop
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Supporting classes for problem demonstrations
    
    static class UnsafeCounter {
        private int value = 0;
        
        public void increment() {
            value++; // NOT ATOMIC! Read-modify-write operation
        }
        
        public int getValue() {
            return value; // May see partially updated values
        }
    }
    
    static class UnsafeShoppingCart {
        private final List<String> items = new ArrayList<>(); // NOT THREAD-SAFE!
        
        public void addItem(String item) {
            items.add(item); // May corrupt internal array structure
        }
        
        public int getItemCount() {
            return items.size(); // May read inconsistent size
        }
        
        public List<String> getItems() {
            return new ArrayList<>(items); // May see partial updates
        }
    }
    
    static class UnsafeBankAccount {
        private final String accountName;
        private double balance;
        
        public UnsafeBankAccount(String accountName, double initialBalance) {
            this.accountName = accountName;
            this.balance = initialBalance;
        }
        
        public void deposit(double amount) {
            // NOT ATOMIC: Read current balance, add amount, write back
            balance += amount;
        }
        
        public void withdraw(double amount) {
            // NOT ATOMIC: Read current balance, subtract amount, write back
            balance -= amount;
        }
        
        public double getBalance() {
            return balance; // May see partially updated balance
        }
        
        public String getAccountName() {
            return accountName;
        }
    }
    
    static class VisibilityProblemDemo {
        private boolean stopFlag = false; // NOT VOLATILE - visibility issues
        
        public void setStopFlag(boolean stopFlag) {
            this.stopFlag = stopFlag;
        }
        
        public boolean isStopFlag() {
            return stopFlag;
        }
    }
}
```

### With Proper Synchronization - Safe and Reliable:
```java
// ROBUST CODE - Synchronized access ensuring thread safety

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class SynchronizedSolutions {
    
    public void demonstrateSolutions() {
        System.out.println("=== SYNCHRONIZED SOLUTIONS ===");
        
        demonstrateSynchronizedMethods();
        demonstrateSynchronizedBlocks();
        demonstrateReentrantLocks();
        demonstrateAtomicOperations();
        demonstrateVolatileFields();
        demonstrateThreadSafeCollections();
    }
    
    // SOLUTION 1: Synchronized methods
    public void demonstrateSynchronizedMethods() {
        System.out.println("\n--- Synchronized Methods Solution ---");
        
        SafeCounter counter = new SafeCounter();
        int numThreads = 10;
        int incrementsPerThread = 1000;
        
        System.out.printf("🔄 Starting %d threads, each incrementing %d times%n", 
                         numThreads, incrementsPerThread);
        System.out.printf("📊 Expected final value: %d%n", numThreads * incrementsPerThread);
        
        List<Thread> threads = new ArrayList<>();
        
        // Create threads that increment counter safely
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment(); // THREAD-SAFE!
                }
            }, "SafeIncrementer-" + i);
            
            threads.add(thread);
        }
        
        // Start all threads
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long endTime = System.currentTimeMillis();
        int finalValue = counter.getValue();
        
        System.out.printf("⏱️ Execution time: %d ms%n", (endTime - startTime));
        System.out.printf("📊 Actual final value: %d%n", finalValue);
        System.out.printf("✅ All increments preserved: %s%n", 
                         finalValue == (numThreads * incrementsPerThread) ? "YES" : "NO");
        System.out.println("✅ Solution: Synchronized methods prevented race conditions!");
    }
    
    // SOLUTION 2: Synchronized blocks for fine-grained control
    public void demonstrateSynchronizedBlocks() {
        System.out.println("\n--- Synchronized Blocks Solution ---");
        
        SafeShoppingCart cart = new SafeShoppingCart();
        int numThreads = 5;
        
        System.out.printf("🔄 %d threads safely adding items to shopping cart%n", numThreads);
        
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                // Each thread adds different items
                for (int j = 1; j <= 10; j++) {
                    String item = String.format("Thread%d-Item%d", threadId, j);
                    cart.addItem(item); // THREAD-SAFE!
                    
                    // Get consistent count
                    int count = cart.getItemCount(); // THREAD-SAFE!
                    
                    if (j % 3 == 0) {
                        System.out.printf("  [Thread-%d] Added %s, Total items: %d%n", 
                                         threadId, item, count);
                    }
                }
            }, "SafeCartUser-" + i);
            
            threads.add(thread);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.printf("📊 Expected total items: %d%n", numThreads * 10);
        System.out.printf("📊 Actual total items: %d%n", cart.getItemCount());
        System.out.println("✅ Solution: All data preserved with no corruption!");
        
        // Show items to demonstrate integrity
        System.out.println("📝 Sample items (guaranteed consistent):");
        List<String> items = cart.getItems();
        for (int i = 0; i < Math.min(5, items.size()); i++) {
            System.out.printf("   %d. %s%n", i + 1, items.get(i));
        }
    }
    
    // SOLUTION 3: ReentrantLock for advanced control
    public void demonstrateReentrantLocks() {
        System.out.println("\n--- ReentrantLock Solution ---");
        
        SafeBankAccount account1 = new SafeBankAccount("Account-1", 1000);
        SafeBankAccount account2 = new SafeBankAccount("Account-2", 1000);
        
        System.out.printf("💰 Initial balances: %s=$%.2f, %s=$%.2f%n", 
                         account1.getAccountName(), account1.getBalance(),
                         account2.getAccountName(), account2.getBalance());
        
        double initialTotal = account1.getBalance() + account2.getBalance();
        System.out.printf("💰 Initial total: $%.2f%n", initialTotal);
        
        // Multiple threads transferring money between accounts safely
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            // Thread transferring from account1 to account2
            Thread thread1 = new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    double amount = 10.0;
                    safeTransferMoney(account1, account2, amount); // ATOMIC OPERATION!
                }
            }, "SafeTransfer1to2-" + i);
            
            // Thread transferring from account2 to account1  
            Thread thread2 = new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    double amount = 10.0;
                    safeTransferMoney(account2, account1, amount); // ATOMIC OPERATION!
                }
            }, "SafeTransfer2to1-" + i);
            
            threads.add(thread1);
            threads.add(thread2);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        double finalTotal = account1.getBalance() + account2.getBalance();
        
        System.out.printf("💰 Final balances: %s=$%.2f, %s=$%.2f%n", 
                         account1.getAccountName(), account1.getBalance(),
                         account2.getAccountName(), account2.getBalance());
        System.out.printf("💰 Final total: $%.2f%n", finalTotal);
        System.out.printf("✅ Money conservation: $%.2f difference%n", Math.abs(finalTotal - initialTotal));
        System.out.println("✅ Solution: Atomic transfers with proper locking!");
    }
    
    private void safeTransferMoney(SafeBankAccount from, SafeBankAccount to, double amount) {
        // Get locks in consistent order to prevent deadlock
        SafeBankAccount firstLock = from.getAccountName().compareTo(to.getAccountName()) < 0 ? from : to;
        SafeBankAccount secondLock = firstLock == from ? to : from;
        
        firstLock.lock();
        try {
            secondLock.lock();
            try {
                // Now both accounts are locked - atomic operation
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }
    
    // SOLUTION 4: Atomic operations
    public void demonstrateAtomicOperations() {
        System.out.println("\n--- Atomic Operations Solution ---");
        
        AtomicCounter atomicCounter = new AtomicCounter();
        int numThreads = 10;
        int incrementsPerThread = 1000;
        
        System.out.printf("🔄 %d threads using atomic operations%n", numThreads);
        
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    atomicCounter.increment(); // LOCK-FREE ATOMIC!
                }
            }, "AtomicIncrementer-" + i);
            
            threads.add(thread);
        }
        
        long startTime = System.currentTimeMillis();
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.printf("⏱️ Atomic operations time: %d ms%n", (endTime - startTime));
        System.out.printf("📊 Expected value: %d%n", numThreads * incrementsPerThread);
        System.out.printf("📊 Actual value: %d%n", atomicCounter.getValue());
        System.out.println("✅ Solution: Lock-free atomic operations ensure consistency!");
    }
    
    // SOLUTION 5: Volatile fields for visibility
    public void demonstrateVolatileFields() {
        System.out.println("\n--- Volatile Fields Solution ---");
        
        VolatileVisibilityDemo demo = new VolatileVisibilityDemo();
        
        // Thread that changes the flag
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("  ✍️ Writer thread: Setting stop flag to true");
                demo.setStopFlag(true); // VISIBLE to all threads immediately!
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Writer");
        
        // Thread that reads the flag
        Thread reader = new Thread(() -> {
            System.out.println("  👁️ Reader thread: Starting to watch flag");
            int iterations = 0;
            
            while (!demo.isStopFlag()) {
                iterations++;
                // Will see the flag change immediately due to volatile!
                if (iterations % 1000000 == 0) {
                    System.out.printf("  👁️ Reader thread: Still running (iterations: %d)%n", iterations);
                }
            }
            
            System.out.printf("  👁️ Reader thread: Stopped after %d iterations%n", iterations);
        }, "Reader");
        
        writer.start();
        reader.start();
        
        try {
            writer.join();
            reader.join(2000); // Should complete quickly now
            
            if (reader.isAlive()) {
                System.out.println("❌ Still have visibility issues!");
                reader.interrupt();
            } else {
                System.out.println("✅ Solution: Volatile ensured immediate visibility!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // SOLUTION 6: Thread-safe collections
    public void demonstrateThreadSafeCollections() {
        System.out.println("\n--- Thread-Safe Collections Solution ---");
        
        // Compare different collection approaches
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        
        int numThreads = 5;
        
        System.out.printf("🔄 Testing thread-safe collections with %d threads%n", numThreads);
        
        // Test synchronized map
        testMapPerformance("SynchronizedMap", synchronizedMap, numThreads);
        
        // Test concurrent map
        testMapPerformance("ConcurrentHashMap", concurrentMap, numThreads);
        
        System.out.printf("📊 SynchronizedMap final size: %d%n", synchronizedMap.size());
        System.out.printf("📊 ConcurrentHashMap final size: %d%n", concurrentMap.size());
        System.out.println("✅ Solution: Thread-safe collections prevent data corruption!");
    }
    
    private void testMapPerformance(String mapType, Map<String, Integer> map, int numThreads) {
        List<Thread> threads = new ArrayList<>();
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    String key = String.format("Thread%d-Key%d", threadId, j);
                    map.put(key, j);
                    
                    // Also read some values
                    Integer value = map.get(key);
                    if (value != null && j % 25 == 0) {
                        System.out.printf("  [%s] Thread-%d: Key=%s, Value=%d%n", 
                                         mapType, threadId, key, value);
                    }
                }
            }, mapType + "-User-" + i);
            
            threads.add(thread);
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("⏱️ %s time: %d ms%n", mapType, (endTime - startTime));
    }
    
    // Supporting classes for synchronized solutions
    
    static class SafeCounter {
        private int value = 0;
        
        public synchronized void increment() {
            value++; // ATOMIC within synchronized method
        }
        
        public synchronized int getValue() {
            return value; // Consistent read
        }
        
        public synchronized void add(int amount) {
            value += amount; // Multiple operations atomic together
        }
    }
    
    static class SafeShoppingCart {
        private final List<String> items = new ArrayList<>();
        private final Object lock = new Object(); // Explicit lock object
        
        public void addItem(String item) {
            synchronized (lock) {
                items.add(item); // Thread-safe modification
            }
        }
        
        public int getItemCount() {
            synchronized (lock) {
                return items.size(); // Consistent read
            }
        }
        
        public List<String> getItems() {
            synchronized (lock) {
                return new ArrayList<>(items); // Safe copy
            }
        }
        
        public void addMultipleItems(List<String> newItems) {
            synchronized (lock) {
                // Multiple operations atomic together
                for (String item : newItems) {
                    items.add(item);
                }
            }
        }
    }
    
    static class SafeBankAccount {
        private final String accountName;
        private double balance;
        private final ReentrantLock lock = new ReentrantLock();
        
        public SafeBankAccount(String accountName, double initialBalance) {
            this.accountName = accountName;
            this.balance = initialBalance;
        }
        
        public void deposit(double amount) {
            lock.lock();
            try {
                balance += amount; // Atomic within lock
            } finally {
                lock.unlock();
            }
        }
        
        public void withdraw(double amount) {
            lock.lock();
            try {
                balance -= amount; // Atomic within lock
            } finally {
                lock.unlock();
            }
        }
        
        public double getBalance() {
            lock.lock();
            try {
                return balance; // Consistent read
            } finally {
                lock.unlock();
            }
        }
        
        public String getAccountName() {
            return accountName; // Immutable - no synchronization needed
        }
        
        public void lock() {
            lock.lock();
        }
        
        public void unlock() {
            lock.unlock();
        }
        
        public boolean tryLock() {
            return lock.tryLock();
        }
    }
    
    static class AtomicCounter {
        private final AtomicInteger value = new AtomicInteger(0);
        
        public void increment() {
            value.incrementAndGet(); // Atomic operation
        }
        
        public int getValue() {
            return value.get(); // Atomic read
        }
        
        public int addAndGet(int amount) {
            return value.addAndGet(amount); // Atomic add-and-get
        }
        
        public boolean compareAndSet(int expected, int newValue) {
            return value.compareAndSet(expected, newValue); // Atomic compare-and-swap
        }
    }
    
    static class VolatileVisibilityDemo {
        private volatile boolean stopFlag = false; // VOLATILE ensures visibility
        
        public void setStopFlag(boolean stopFlag) {
            this.stopFlag = stopFlag; // Immediately visible to all threads
        }
        
        public boolean isStopFlag() {
            return stopFlag; // Always reads latest value
        }
    }
}
```

## Advanced Synchronization Patterns

```java
// Advanced synchronization techniques and patterns

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

class AdvancedSynchronizationPatterns {
    
    public void demonstrateAdvancedPatterns() {
        System.out.println("=== ADVANCED SYNCHRONIZATION PATTERNS ===");
        
        demonstrateReadWriteLocks();
        demonstrateConditionVariables();
        demonstrateSemaphores();
        demonstrateCountDownLatch();
        demonstrateCyclicBarrier();
        demonstrateExchanger();
    }
    
    // PATTERN 1: ReadWriteLock for multiple readers, single writer
    public void demonstrateReadWriteLocks() {
        System.out.println("\n--- ReadWriteLock Pattern ---");
        
        SharedDocument document = new SharedDocument();
        
        // Multiple reader threads
        List<Thread> readers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int readerId = i;
            Thread reader = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    String content = document.readContent();
                    System.out.printf("  📖 Reader-%d: %s%n", readerId, content);
                    
                    try {
                        Thread.sleep(100); // Simulate reading time
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }, "Reader-" + i);
            
            readers.add(reader);
        }
        
        // Single writer thread
        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                String newContent = "Document version " + i;
                document.writeContent(newContent);
                System.out.printf("  ✍️ Writer: Updated to '%s'%n", newContent);
                
                try {
                    Thread.sleep(200); // Simulate writing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Writer");
        
        // Start all threads
        for (Thread reader : readers) {
            reader.start();
        }
        writer.start();
        
        // Wait for completion
        for (Thread reader : readers) {
            try {
                reader.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        try {
            writer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ ReadWriteLock allowed concurrent reads while serializing writes!");
    }
    
    // PATTERN 2: Condition variables for thread coordination
    public void demonstrateConditionVariables() {
        System.out.println("\n--- Condition Variables Pattern ---");
        
        ProducerConsumerBuffer buffer = new ProducerConsumerBuffer(5);
        
        // Producer threads
        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 8; i++) {
                try {
                    buffer.put("Item-" + i);
                    System.out.printf("  📦 Producer1: Added Item-%d%n", i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Producer1");
        
        // Consumer threads
        Thread consumer1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    String item = buffer.take();
                    System.out.printf("  📤 Consumer1: Got %s%n", item);
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Consumer1");
        
        Thread consumer2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    String item = buffer.take();
                    System.out.printf("  📤 Consumer2: Got %s%n", item);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "Consumer2");
        
        producer1.start();
        consumer1.start();
        consumer2.start();
        
        try {
            producer1.join();
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Condition variables coordinated producer-consumer interaction!");
    }
    
    // PATTERN 3: Semaphores for resource management
    public void demonstrateSemaphores() {
        System.out.println("\n--- Semaphore Pattern ---");
        
        ResourcePool pool = new ResourcePool(3); // 3 available resources
        
        List<Thread> workers = new ArrayList<>();
        
        for (int i = 0; i < 7; i++) {
            final int workerId = i;
            Thread worker = new Thread(() -> {
                try {
                    pool.acquireResource(workerId);
                    
                    // Use resource for some time
                    Thread.sleep(1000);
                    
                    pool.releaseResource(workerId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);
            
            workers.add(worker);
        }
        
        // Start all workers
        for (Thread worker : workers) {
            worker.start();
        }
        
        // Wait for completion
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("✅ Semaphore managed resource pool access!");
    }
    
    // PATTERN 4: CountDownLatch for waiting on multiple events
    public void demonstrateCountDownLatch() {
        System.out.println("\n--- CountDownLatch Pattern ---");
        
        int numServices = 3;
        CountDownLatch startupLatch = new CountDownLatch(numServices);
        
        // Service initialization threads
        for (int i = 0; i < numServices; i++) {
            final int serviceId = i;
            Thread serviceThread = new Thread(() -> {
                try {
                    // Simulate service initialization
                    System.out.printf("  🔧 Service-%d: Starting initialization...%n", serviceId);
                    Thread.sleep(1000 + serviceId * 500); // Different startup times
                    System.out.printf("  ✅ Service-%d: Initialization complete%n", serviceId);
                    
                    startupLatch.countDown(); // Signal completion
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Service-" + i);
            
            serviceThread.start();
        }
        
        // Main application thread waiting for all services
        Thread mainApp = new Thread(() -> {
            try {
                System.out.println("  📱 Main app: Waiting for all services to start...");
                startupLatch.await(); // Wait for all services
                System.out.println("  🚀 Main app: All services ready - starting application!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "MainApp");
        
        mainApp.start();
        
        try {
            mainApp.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ CountDownLatch coordinated application startup!");
    }
    
    // PATTERN 5: CyclicBarrier for synchronization points
    public void demonstrateCyclicBarrier() {
        System.out.println("\n--- CyclicBarrier Pattern ---");
        
        int numWorkers = 4;
        CyclicBarrier barrier = new CyclicBarrier(numWorkers, () -> {
            System.out.println("  🎯 All workers reached the barrier - proceeding to next phase!");
        });
        
        List<Thread> workers = new ArrayList<>();
        
        for (int i = 0; i < numWorkers; i++) {
            final int workerId = i;
            Thread worker = new Thread(() -> {
                try {
                    for (int phase = 1; phase <= 3; phase++) {
                        // Simulate work
                        System.out.printf("  ⚙️ Worker-%d: Working on phase %d...%n", workerId, phase);
                        Thread.sleep(500 + workerId * 200); // Different work times
                        System.out.printf("  ✅ Worker-%d: Completed phase %d%n", workerId, phase);
                        
                        // Wait for all workers to complete this phase
                        barrier.await();
                        System.out.printf("  🚀 Worker-%d: Moving to next phase%n", workerId);
                    }
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);
            
            workers.add(worker);
        }
        
        // Start all workers
        for (Thread worker : workers) {
            worker.start();
        }
        
        // Wait for completion
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("✅ CyclicBarrier synchronized workers through phases!");
    }
    
    // PATTERN 6: Exchanger for thread data exchange
    public void demonstrateExchanger() {
        System.out.println("\n--- Exchanger Pattern ---");
        
        Exchanger<String> exchanger = new Exchanger<>();
        
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    String data = "ProducedData-" + i;
                    System.out.printf("  📦 Producer: Created %s%n", data);
                    
                    // Exchange data with consumer
                    String receivedData = exchanger.exchange(data);
                    System.out.printf("  📩 Producer: Received %s in return%n", receivedData);
                    
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    String acknowledgment = "Ack-" + i;
                    
                    // Exchange acknowledgment for data
                    String receivedData = exchanger.exchange(acknowledgment);
                    System.out.printf("  📤 Consumer: Received %s, sent %s%n", receivedData, acknowledgment);
                    
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Exchanger facilitated bidirectional data exchange!");
    }
    
    // Supporting classes for advanced patterns
    
    static class SharedDocument {
        private String content = "Initial document content";
        private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock readLock = rwLock.readLock();
        private final Lock writeLock = rwLock.writeLock();
        
        public String readContent() {
            readLock.lock();
            try {
                // Multiple readers can access simultaneously
                return content;
            } finally {
                readLock.unlock();
            }
        }
        
        public void writeContent(String newContent) {
            writeLock.lock();
            try {
                // Only one writer can access at a time
                this.content = newContent;
            } finally {
                writeLock.unlock();
            }
        }
    }
    
    static class ProducerConsumerBuffer {
        private final Queue<String> buffer = new LinkedList<>();
        private final int capacity;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();
        
        public ProducerConsumerBuffer(int capacity) {
            this.capacity = capacity;
        }
        
        public void put(String item) throws InterruptedException {
            lock.lock();
            try {
                while (buffer.size() == capacity) {
                    notFull.await(); // Wait until buffer not full
                }
                
                buffer.offer(item);
                notEmpty.signal(); // Signal consumers
            } finally {
                lock.unlock();
            }
        }
        
        public String take() throws InterruptedException {
            lock.lock();
            try {
                while (buffer.isEmpty()) {
                    notEmpty.await(); // Wait until buffer not empty
                }
                
                String item = buffer.poll();
                notFull.signal(); // Signal producers
                return item;
            } finally {
                lock.unlock();
            }
        }
    }
    
    static class ResourcePool {
        private final Semaphore semaphore;
        
        public ResourcePool(int numResources) {
            this.semaphore = new Semaphore(numResources);
        }
        
        public void acquireResource(int workerId) throws InterruptedException {
            System.out.printf("  ⏳ Worker-%d: Waiting for resource...%n", workerId);
            semaphore.acquire();
            System.out.printf("  ✅ Worker-%d: Acquired resource%n", workerId);
        }
        
        public void releaseResource(int workerId) {
            System.out.printf("  📤 Worker-%d: Released resource%n", workerId);
            semaphore.release();
        }
    }
}
```

## Complete Synchronization Demo

```java
public class CompleteSynchronizationDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE SYNCHRONIZATION DEMONSTRATION ===");
        
        // 1. Unsynchronized problems
        System.out.println("\n1. UNSYNCHRONIZED PROBLEMS");
        UnsynchronizedProblems problems = new UnsynchronizedProblems();
        problems.demonstrateProblems();
        
        System.out.println("\n" + "=".repeat(60));
        
        // 2. Synchronized solutions
        System.out.println("\n2. SYNCHRONIZED SOLUTIONS");
        SynchronizedSolutions solutions = new SynchronizedSolutions();
        solutions.demonstrateSolutions();
        
        System.out.println("\n" + "=".repeat(60));
        
        // 3. Advanced synchronization patterns
        System.out.println("\n3. ADVANCED SYNCHRONIZATION PATTERNS");
        AdvancedSynchronizationPatterns patterns = new AdvancedSynchronizationPatterns();
        patterns.demonstrateAdvancedPatterns();
        
        System.out.println("\n=== DEMONSTRATION COMPLETED ===");
        printSynchronizationBestPractices();
    }
    
    private static void printSynchronizationBestPractices() {
        System.out.println("\n🎯 SYNCHRONIZATION BEST PRACTICES:");
        System.out.println("✅ Use the least restrictive synchronization necessary");
        System.out.println("✅ Prefer immutable objects to avoid synchronization");
        System.out.println("✅ Use thread-safe collections when appropriate");
        System.out.println("✅ Keep synchronized blocks as small as possible");
        System.out.println("✅ Avoid nested locks to prevent deadlocks");
        System.out.println("✅ Use timeout-based locking for critical sections");
        System.out.println("✅ Consider using atomic classes for simple operations");
        System.out.println("✅ Use volatile for simple flag-based coordination");
        
        System.out.println("\n❌ AVOID:");
        System.out.println("❌ Synchronizing on public objects or strings");
        System.out.println("❌ Holding locks for extended periods");
        System.out.println("❌ Accessing synchronized code from constructors");
        System.out.println("❌ Using synchronized methods for large operations");
        System.out.println("❌ Ignoring lock ordering to prevent deadlocks");
        System.out.println("❌ Over-synchronization that kills performance");
    }
}
```

## Interview Questions & Answers

**Q1: What is synchronization and why is it needed?**
**A:** Synchronization controls access to shared resources in multithreaded environments to prevent race conditions, data corruption, and inconsistent states. It's needed because multiple threads accessing shared data simultaneously can cause unpredictable results.

**Q2: What's the difference between synchronized methods and synchronized blocks?**
**A:** Synchronized methods lock the entire method using the object's intrinsic lock, while synchronized blocks allow fine-grained control over what's locked and which lock to use, potentially improving performance.

**Q3: What is a race condition and how do you prevent it?**
**A:** A race condition occurs when multiple threads access shared data simultaneously and the result depends on thread timing. Prevent it using synchronization, atomic operations, or thread-safe data structures.

**Q4: Explain the difference between volatile and synchronized.**
**A:** volatile ensures visibility of changes across threads but doesn't provide atomicity. synchronized provides both visibility and atomicity through mutual exclusion. Use volatile for simple flags, synchronized for complex operations.

**Q5: What are atomic classes and when should you use them?**
**A:** Atomic classes provide lock-free thread-safe operations on single variables. Use them for simple operations like counters or flags where performance matters and you don't need to coordinate multiple operations.

**Q6: What is a deadlock and how can you prevent it?**
**A:** Deadlock occurs when threads wait for each other's locks indefinitely. Prevent by: consistent lock ordering, timeout-based locking, avoiding nested locks, or using higher-level concurrency utilities.

**Q7: When would you use ReentrantLock over synchronized?**
**A:** Use ReentrantLock when you need: timeout-based locking, fair locking, condition variables, lock interruption, or the ability to try lock without blocking.

**Q8: What are the benefits and drawbacks of different synchronization mechanisms?**
**A:** Synchronized: Simple but limited. ReentrantLock: Flexible but complex. Atomic: Fast but limited scope. Volatile: Lightweight but only visibility. Choose based on specific requirements.

## Key Takeaways

1. **Synchronization prevents race conditions** ensuring data integrity in multithreaded applications
2. **Multiple synchronization mechanisms exist** each with specific use cases and trade-offs  
3. **Synchronized methods/blocks provide mutual exclusion** preventing concurrent access to critical sections
4. **ReentrantLocks offer advanced features** like timeouts, fair locking, and condition variables
5. **Atomic classes enable lock-free operations** for simple thread-safe operations with high performance
6. **Volatile ensures visibility** of changes across threads without providing atomicity
7. **Thread-safe collections prevent data corruption** in concurrent access scenarios
8. **Proper lock ordering prevents deadlocks** in complex multi-lock situations
9. **Advanced patterns solve complex coordination** problems like producer-consumer and barrier synchronization
10. **Performance vs safety trade-offs require careful consideration** when choosing synchronization strategies

---

*Remember: Synchronization is like traffic lights at intersections - it prevents crashes by ensuring only authorized "vehicles" (threads) can proceed through "intersections" (critical sections) at the right time!*