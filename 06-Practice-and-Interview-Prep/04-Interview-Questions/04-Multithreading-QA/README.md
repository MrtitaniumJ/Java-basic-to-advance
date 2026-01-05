# Multithreading Interview Questions & Answers

## Q1: Thread Creation - Runnable vs Thread Class

**Concept:**
Both approaches create threads, but Runnable is preferred because Java doesn't support multiple inheritance. Implementing Runnable allows a class to extend another class while still being executable as a thread. The Thread class-based approach directly extends Thread.

**Code Example:**
```java
// Runnable approach (preferred)
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread via Runnable");
    }
}
Thread t1 = new Thread(new MyRunnable());
t1.start();

// Thread class approach
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread via Thread class");
    }
}
new MyThread().start();
```

**Common Mistakes:**
- Calling `run()` directly instead of `start()` - this executes in the main thread, not a new thread
- Not implementing `run()` method, leading to no actual work in the thread
- Extending Thread when Runnable would be more flexible

**Best Practices:**
- Always prefer Runnable/Callable over extending Thread
- Use thread pools instead of creating threads manually
- Always call `start()`, never call `run()` directly
- Use lambda expressions with Runnable: `new Thread(() -> { /* code */ }).start();`

---

## Q2: Thread States and Lifecycle

**Concept:**
A thread goes through five states: NEW (created but not started), RUNNABLE (eligible to run), BLOCKED/WAITING (waiting for resources or other conditions), TIMED_WAITING (waiting with timeout), and TERMINATED (execution complete). Understanding these states is crucial for diagnosing threading issues.

**Code Example:**
```java
Thread t = new Thread(() -> {
    System.out.println(Thread.currentThread().getState()); // NEW or RUNNABLE
    try {
        Thread.sleep(1000); // TIMED_WAITING
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
});

System.out.println(t.getState()); // NEW
t.start();
System.out.println(t.getState()); // RUNNABLE
```

**Common Mistakes:**
- Assuming RUNNABLE means the thread is actively executing (it may be waiting for CPU time)
- Not handling InterruptedException properly when a thread is interrupted
- Checking state after thread completion without proper synchronization

**Best Practices:**
- Use thread state checking only for debugging, not for synchronization logic
- Use proper synchronization primitives (locks, semaphores) instead of polling thread states
- Always handle InterruptedException by restoring the interrupt flag: `Thread.currentThread().interrupt();`

---

## Q3: Synchronized Keyword Mechanism

**Concept:**
The `synchronized` keyword ensures that only one thread can execute a method or block at a time by acquiring a monitor lock. When a thread enters a synchronized block, it locks the monitor; when it exits, the lock is released, allowing other threads to acquire it.

**Code Example:**
```java
class Counter {
    private int count = 0;
    
    // Synchronized method - locks 'this' object
    public synchronized void increment() {
        count++;
    }
    
    // Synchronized block - locks specific object
    public void decrement() {
        synchronized(this) {
            count--;
        }
    }
    
    public int getCount() {
        return count;
    }
}
```

**Common Mistakes:**
- Synchronizing the wrong object or using inconsistent lock objects
- Holding locks while performing slow operations (I/O, network)
- Synchronizing only some methods that access shared data
- Not considering reentrancy - synchronized methods in same object can be called recursively

**Best Practices:**
- Prefer higher-level synchronization utilities (ReentrantLock, CountDownLatch)
- Keep synchronized blocks small to reduce contention
- Use synchronized for method-level access when simplicity is needed; blocks for fine-grained control
- Never synchronize in constructors or during object initialization

---

## Q4: Volatile Keyword Usage

**Concept:**
`volatile` ensures that a variable is always read from and written to main memory, not cached in thread-local memory. It guarantees visibility but NOT atomicity, making it suitable for flags and simple status variables but not for counters or compound operations.

**Code Example:**
```java
class VolatileExample {
    private volatile boolean running = true;
    private volatile int counter = 0; // Visibility only, not atomic
    
    public void stopThread() {
        running = false; // All threads see this immediately
    }
    
    public void run() {
        while (running) {
            counter++; // NOT thread-safe for compound operation
        }
    }
}
// Safe usage for flags, unsafe for counters
```

**Common Mistakes:**
- Using volatile for compound operations like `counter++` thinking it's atomic
- Assuming volatile provides mutual exclusion (it doesn't)
- Using volatile on reference types and modifying object contents (visibility only applies to the reference)
- Overusing volatile when synchronization or atomics are needed

**Best Practices:**
- Use volatile only for simple flag variables and status updates
- Use AtomicInteger/AtomicLong for counters instead of volatile
- Pair volatile with proper synchronization for compound operations
- Document volatile usage clearly as it affects performance

---

## Q5: Mutex vs Semaphore

**Concept:**
A Mutex (mutual exclusion) is a binary lock (locked/unlocked) owned by the acquiring thread and must be released by the same thread. A Semaphore is a counter-based lock that allows multiple threads (up to a limit) and can be released by any thread, making it more flexible for resource pool management.

**Code Example:**
```java
// Mutex behavior with ReentrantLock
ReentrantLock mutex = new ReentrantLock();
mutex.lock();
try {
    // Only one thread executes here
} finally {
    mutex.unlock();
}

// Semaphore - allows N threads
Semaphore semaphore = new Semaphore(3); // 3 permits
try {
    semaphore.acquire();
    // Up to 3 threads can be here
} finally {
    semaphore.release();
}
```

**Common Mistakes:**
- Using Semaphore for tasks requiring ownership semantics
- Not releasing semaphore/mutex in case of exceptions (missing try-finally)
- Creating multiple semaphores for the same resource instead of one shared instance
- Confusing permit count with thread count

**Best Practices:**
- Use Mutex (ReentrantLock) for protecting critical sections with ownership
- Use Semaphore for controlling access to resource pools (connection pools, thread pools)
- Always wrap acquire/lock in try-finally to ensure release
- Use try-with-resources when possible with locks

---

## Q6: Deadlock and Prevention

**Concept:**
Deadlock occurs when two or more threads are blocked forever, waiting for each other to release locks. The classic scenario involves circular wait: Thread A waits for lock held by Thread B, while Thread B waits for lock held by Thread A. Prevention requires breaking one of the four deadlock conditions: mutual exclusion, hold-and-wait, no-preemption, or circular wait.

**Code Example:**
```java
// Deadlock scenario
class Account { int balance; }

Account acc1 = new Account();
Account acc2 = new Account();

// Thread 1: locks acc1, then waits for acc2
Thread t1 = new Thread(() -> {
    synchronized(acc1) {
        Thread.sleep(100);
        synchronized(acc2) { } // Waits here
    }
});

// Thread 2: locks acc2, then waits for acc1
Thread t2 = new Thread(() -> {
    synchronized(acc2) {
        synchronized(acc1) { } // Waits here
    }
});

// Prevention: always acquire locks in same order
synchronized(acc1) { synchronized(acc2) { } }
```

**Common Mistakes:**
- Acquiring multiple locks without consistent ordering
- Holding locks while performing blocking operations
- Nesting synchronized blocks without careful planning
- Ignoring timeout-based approaches for lock acquisition

**Best Practices:**
- Always acquire locks in the same global order across all threads
- Use lock timeouts: `lock.tryLock(timeout, TimeUnit.SECONDS)`
- Minimize lock scope and avoid nested locks when possible
- Use deadlock detection tools and timeout-aware lock APIs

---

## Q7: Race Conditions

**Concept:**
A race condition occurs when multiple threads access shared data concurrently and at least one modifies it, with the outcome depending on timing. The result is unpredictable and can vary between executions. The classic example is unsynchronized counter increment, where read-modify-write is not atomic.

**Code Example:**
```java
class UnsafeCounter {
    private int count = 0;
    
    // Race condition: read-modify-write not atomic
    public void increment() {
        count++; // Three operations: read, modify, write
    }
    
    public int getCount() { return count; }
}

// Safe version
class SafeCounter {
    private int count = 0;
    
    public synchronized void increment() { count++; }
    public synchronized int getCount() { return count; }
}
```

**Common Mistakes:**
- Synchronizing getters but not setters (or vice versa)
- Using volatile instead of synchronization for compound operations
- Assuming modern CPUs eliminate race conditions
- Not synchronizing check-then-act patterns: `if (value == 0) value = 1;`

**Best Practices:**
- Synchronize all accesses to shared mutable data consistently
- Use AtomicInteger/AtomicReference for simple atomic operations
- Prefer immutable objects to avoid synchronization altogether
- Use happens-before relationships and memory barriers correctly

---

## Q8: Thread Pool and ExecutorService

**Concept:**
A thread pool manages a fixed set of reusable threads, queuing tasks for execution instead of creating new threads for each task. ExecutorService is the interface providing methods to submit tasks and manage the pool lifecycle, offering better resource management and reduced thread creation overhead.

**Code Example:**
```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit runnable tasks
for (int i = 0; i < 10; i++) {
    executor.submit(() -> System.out.println("Task"));
}

executor.shutdown(); // No new tasks accepted
if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
    executor.shutdownNow(); // Force terminate
}
```

**Common Mistakes:**
- Not shutting down executor, leading to resource leaks
- Using single-threaded executor for blocking I/O-heavy tasks
- Submitting blocking tasks to limited thread pools, causing deadlock
- Ignoring rejected task exceptions in unbounded queues

**Best Practices:**
- Always shut down executors in finally blocks or try-with-resources
- Choose pool size based on workload (CPU-bound: CPU count; I/O-bound: higher)
- Use specific executors: newFixedThreadPool, newCachedThreadPool, etc.
- Monitor queue sizes and rejected task counts for tuning

---

## Q9: Future and Callable

**Concept:**
Callable is similar to Runnable but returns a result and can throw checked exceptions. Future represents the result of an asynchronous computation, allowing querying completion status, waiting for result, and canceling execution. Together, they provide a clean abstraction for async task execution.

**Code Example:**
```java
ExecutorService executor = Executors.newFixedThreadPool(2);

Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 42;
};

Future<Integer> future = executor.submit(task);

try {
    Integer result = future.get(2, TimeUnit.SECONDS);
    System.out.println("Result: " + result);
} catch (TimeoutException e) {
    future.cancel(true);
} catch (ExecutionException e) {
    // Handle execution exception
}

executor.shutdown();
```

**Common Mistakes:**
- Calling `get()` without timeout on UI threads, causing UI freeze
- Not handling TimeoutException for time-sensitive operations
- Ignoring ExecutionException wrapping actual task exceptions
- Assuming `cancel()` always succeeds (it fails if task already running)

**Best Practices:**
- Always use timeout in `get()` calls to prevent indefinite blocking
- Handle ExecutionException and TimeoutException explicitly
- Use CompletableFuture for chaining async operations
- Cancel futures only if truly needed; prefer timeout instead

---

## Q10: Wait, Notify, NotifyAll

**Concept:**
`wait()` causes a thread to release its lock and pause until notified; `notify()` wakes one waiting thread; `notifyAll()` wakes all waiting threads. These methods enable producer-consumer patterns and inter-thread communication but require careful synchronization and condition checking.

**Code Example:**
```java
class BoundedBuffer<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    
    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait(); // Release lock and wait
        }
        queue.add(item);
        notifyAll(); // Wake waiting consumers
    }
    
    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.remove();
        notifyAll(); // Wake waiting producers
        return item;
    }
}
```

**Common Mistakes:**
- Calling wait/notify without synchronization (IllegalMonitorStateException)
- Using `notify()` instead of `notifyAll()`, potentially starving threads
- Not re-checking condition after waking (spurious wakeups)
- Calling wait/notify on wrong object (wrong monitor)

**Best Practices:**
- Always call wait/notify inside synchronized blocks
- Always use while loops with conditions, not if statements
- Prefer notifyAll() to ensure all waiting threads are considered
- Use Condition with Lock for more control than monitor-based wait/notify

---

## Q11: Lock and ReentrantLock

**Concept:**
Lock is an interface providing more control than synchronized, supporting fair ordering, timeout-based attempts, and condition variables. ReentrantLock is the common implementation allowing the same thread to acquire the lock multiple times, useful for recursive methods and cleaner lock management.

**Code Example:**
```java
Lock lock = new ReentrantLock(true); // Fair lock

lock.lock();
try {
    // Critical section
} finally {
    lock.unlock();
}

// Try lock with timeout
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
} else {
    System.out.println("Could not acquire lock");
}

// Reentrance - same thread can acquire multiple times
lock.lock();
lock.lock(); // OK for ReentrantLock
lock.unlock();
lock.unlock();
```

**Common Mistakes:**
- Forgetting unlock in finally blocks (use try-finally or try-with-resources)
- Using fair locks when fairness is not needed (causes performance overhead)
- Assuming ReentrantLock has same performance as synchronized
- Not understanding lock counters (multiple lock calls need equal unlocks)

**Best Practices:**
- Always pair lock with unlock in try-finally
- Use try-with-resources with lock.lock() when possible
- Prefer synchronized for simple cases; use Lock for advanced features
- Use Condition variables with Lock for complex signaling patterns

---

## Q12: Happens-Before Relationship

**Concept:**
Happens-before is a guarantee that memory operations from one thread are visible to another thread. It's established by various mechanisms: synchronized blocks, volatile reads/writes, lock operations, and Thread.start/join. Without happens-before relationships, one thread's writes may never be visible to another.

**Code Example:**
```java
class VolatileExample {
    private volatile boolean ready = false;
    private int value = 0;
    
    public void writer() {
        value = 42;
        ready = true; // volatile write: happens-before
    }
    
    public void reader() {
        if (ready) { // volatile read: sees writer's write
            System.out.println(value); // Guaranteed to see 42
        }
    }
}

// Synchronization happens-before
class SyncExample {
    private int value = 0;
    
    public synchronized void method1() { value = 42; }
    public synchronized void method2() { System.out.println(value); }
    // method1's writes happen-before method2's reads
}
```

**Common Mistakes:**
- Assuming visibility without explicit happens-before
- Not understanding that happens-before is not just about reordering
- Using volatile on objects expecting content visibility
- Relying on timing/delays instead of synchronization for visibility

**Best Practices:**
- Understand and apply happens-before rules consistently
- Document visibility assumptions in critical code
- Use synchronization mechanisms (volatile, synchronized, Lock) instead of hoping for visibility
- Test multithreaded code extensively; never assume timing guarantees correctness
