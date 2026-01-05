# Queues in Java - FIFO and Priority Collections

## Simple Explanation

Think of a **Queue** as a **waiting line** at a store:
- **First person in line gets served first** (FIFO - First In, First Out)
- People **join at the back** (enqueue/offer)
- People **leave from the front** (dequeue/poll)
- You can **peek** at who's next without removing them

### Real-World Analogies
- **Queue**: Line at a ticket counter (FIFO)
- **PriorityQueue**: Emergency room (priority-based, not FIFO)
- **Deque**: Double-ended line (can join/leave from either end)

## Professional Definition

A **Queue** is a linear collection designed for holding elements prior to processing. Queues typically order elements in FIFO (first-in-first-out) manner, though other orderings are possible (e.g., priority-based). A **Deque** (Double-Ended Queue) allows insertions and deletions at both ends.

## Queue Hierarchy

```
Collection (Interface)
    ↓
Queue (Interface)
    ├── PriorityQueue (Class) - Heap-based priority queue
    ├── ArrayDeque (Class) - Resizable-array deque
    └── LinkedList (Class) - Linked-list implementation

Deque (Interface) extends Queue
    ├── ArrayDeque (Class) - Best general-purpose deque
    └── LinkedList (Class) - Can be used as deque
```

## Types of Queue Implementations

### Comparison Table:

| Feature | PriorityQueue | ArrayDeque | LinkedList (as Queue) |
|---------|---------------|------------|----------------------|
| **Ordering** | Priority-based (min-heap) | FIFO/LIFO | FIFO |
| **Add (offer)** | O(log n) | O(1) amortized | O(1) |
| **Remove (poll)** | O(log n) | O(1) | O(1) |
| **Peek** | O(1) | O(1) | O(1) |
| **Internal Structure** | Min-Heap (array) | Circular array | Doubly-linked list |
| **Allows Null** | No | No | Yes |
| **Thread-Safe** | No | No | No |
| **Best Use Case** | Priority-based processing | General FIFO/stack operations | Queue + list operations |
| **Memory** | Moderate | Low (efficient) | High (node pointers) |

## 1. PriorityQueue - Heap-Based Priority Queue

### Key Characteristics:
- ✅ **Priority-based** - Elements ordered by natural order or comparator
- ✅ **Efficient** - O(log n) insertions and deletions
- ✅ **Min-heap** - Smallest element always at front (by default)
- ❌ **Not FIFO** - Order based on priority, not insertion time
- ❌ **No null** - Cannot contain null elements

### Internal Working:
```
Min-Heap Structure (PriorityQueue):

         [2]           ← Root (smallest element)
        /   \
      [5]   [3]
     /  \   /
   [8] [7][4]

Array representation: [2, 5, 3, 8, 7, 4]
Parent at i, Left child at 2i+1, Right child at 2i+2
```

### Basic Operations:

```java
import java.util.PriorityQueue;
import java.util.Queue;

class PriorityQueueBasics {
    
    public static void demonstratePriorityQueue() {
        System.out.println("=== PriorityQueue Demonstration ===\n");
        
        // Min-heap (default - smallest element first)
        Queue<Integer> pq = new PriorityQueue<>();
        
        // Adding elements
        pq.offer(5);
        pq.offer(2);
        pq.offer(8);
        pq.offer(1);
        pq.offer(3);
        
        System.out.println("PriorityQueue: " + pq);  // Order not guaranteed in toString()
        System.out.println("Peek (min): " + pq.peek());  // 1 (smallest)
        
        // Polling (removes and returns min element)
        System.out.println("\n--- Polling Elements (sorted order) ---");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());  // 1, 2, 3, 5, 8
        }
        
        // Max-heap (largest element first)
        System.out.println("\n--- Max-Heap (Reverse Order) ---");
        Queue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.offer(5);
        maxHeap.offer(2);
        maxHeap.offer(8);
        maxHeap.offer(1);
        
        System.out.println("Max-heap peek: " + maxHeap.peek());  // 8 (largest)
        System.out.println("Polling: " + maxHeap.poll() + ", " + maxHeap.poll());  // 8, 5
    }
    
    public static void main(String[] args) {
        demonstratePriorityQueue();
    }
}
```

### Custom Priority Example:

```java
import java.util.*;

class Task {
    String name;
    int priority;  // Lower number = higher priority
    
    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    @Override
    public String toString() {
        return name + "(P" + priority + ")";
    }
}

class TaskScheduler {
    
    public static void demonstrateTaskScheduling() {
        System.out.println("=== Task Scheduling with PriorityQueue ===\n");
        
        // Custom comparator - lower priority number = higher priority
        Queue<Task> taskQueue = new PriorityQueue<>(
            Comparator.comparingInt(t -> t.priority)
        );
        
        taskQueue.offer(new Task("Write Code", 2));
        taskQueue.offer(new Task("Fix Critical Bug", 1));
        taskQueue.offer(new Task("Write Documentation", 3));
        taskQueue.offer(new Task("Deploy to Production", 1));
        
        System.out.println("Tasks in priority order:");
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            System.out.println("  Processing: " + task);
        }
    }
    
    public static void main(String[] args) {
        demonstrateTaskScheduling();
    }
}
```

### When to Use PriorityQueue:

✅ **Use PriorityQueue When:**
- Need elements in priority order
- Implementing scheduling algorithms
- Finding k-th largest/smallest elements
- Dijkstra's shortest path algorithm
- Huffman coding

❌ **Avoid PriorityQueue When:**
- Need strict FIFO order (use ArrayDeque)
- Need O(1) access to all elements
- Elements can be null

---

## 2. ArrayDeque - High-Performance Double-Ended Queue

### Key Characteristics:
- ✅ **Fastest** - More efficient than LinkedList
- ✅ **Flexible** - Can be used as stack or queue
- ✅ **No capacity restrictions** - Grows as needed
- ✅ **No null** - Doesn't allow null elements
- ❌ **Not thread-safe** - Requires external synchronization

### Internal Working:
```
Circular Array Implementation:

Initial: [_, _, _, _, _, _, _, _]
         ↑ head           ↑ tail

After adding elements:
[C, D, _, _, _, _, A, B]
       ↑ tail  ↑ head

Circular nature allows efficient head/tail operations
```

### Basic Queue Operations:

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

class ArrayDequeBasics {
    
    public static void demonstrateAsQueue() {
        System.out.println("=== ArrayDeque as Queue (FIFO) ===\n");
        
        Queue<String> queue = new ArrayDeque<>();
        
        // Enqueue (add to tail)
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        
        System.out.println("Queue: " + queue);
        System.out.println("Peek: " + queue.peek());  // First (front)
        
        // Dequeue (remove from head)
        System.out.println("\n--- Dequeuing ---");
        System.out.println("Removed: " + queue.poll());  // First
        System.out.println("Removed: " + queue.poll());  // Second
        System.out.println("Remaining: " + queue);
    }
    
    public static void demonstrateAsStack() {
        System.out.println("\n=== ArrayDeque as Stack (LIFO) ===\n");
        
        Deque<String> stack = new ArrayDeque<>();
        
        // Push (add to front)
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        
        System.out.println("Stack: " + stack);
        System.out.println("Peek: " + stack.peek());  // Third (top)
        
        // Pop (remove from front)
        System.out.println("\n--- Popping ---");
        System.out.println("Popped: " + stack.pop());  // Third
        System.out.println("Popped: " + stack.pop());  // Second
        System.out.println("Remaining: " + stack);
    }
    
    public static void demonstrateAsDeque() {
        System.out.println("\n=== ArrayDeque as Deque (Both Ends) ===\n");
        
        Deque<Integer> deque = new ArrayDeque<>();
        
        // Add to both ends
        deque.addFirst(2);    // [2]
        deque.addLast(3);     // [2, 3]
        deque.addFirst(1);    // [1, 2, 3]
        deque.addLast(4);     // [1, 2, 3, 4]
        
        System.out.println("Deque: " + deque);
        System.out.println("First: " + deque.getFirst());
        System.out.println("Last: " + deque.getLast());
        
        // Remove from both ends
        deque.removeFirst();  // [2, 3, 4]
        deque.removeLast();   // [2, 3]
        System.out.println("After removals: " + deque);
    }
    
    public static void main(String[] args) {
        demonstrateAsQueue();
        demonstrateAsStack();
        demonstrateAsDeque();
    }
}
```

### When to Use ArrayDeque:

✅ **Use ArrayDeque When:**
- Need a stack (instead of legacy Stack class)
- Need a queue (faster than LinkedList)
- Need double-ended queue operations
- Want best performance for stack/queue operations

❌ **Avoid ArrayDeque When:**
- Need to store null elements
- Need thread safety (use ConcurrentLinkedDeque)

---

## 3. LinkedList as Queue

### Key Characteristics:
- ✅ **Implements Queue** - Full queue operations
- ✅ **Allows null** - Unlike ArrayDeque
- ✅ **No resizing** - No array resizing overhead
- ❌ **Slower** - More memory and cache misses

### Basic Operations:

```java
import java.util.LinkedList;
import java.util.Queue;

class LinkedListQueue {
    
    public static void demonstrateLinkedListAsQueue() {
        System.out.println("=== LinkedList as Queue ===\n");
        
        Queue<String> queue = new LinkedList<>();
        
        queue.offer("Customer 1");
        queue.offer("Customer 2");
        queue.offer("Customer 3");
        
        System.out.println("Queue: " + queue);
        
        // Process queue
        while (!queue.isEmpty()) {
            String customer = queue.poll();
            System.out.println("Serving: " + customer);
        }
    }
    
    public static void main(String[] args) {
        demonstrateLinkedListAsQueue();
    }
}
```

### When to Use LinkedList as Queue:

✅ **Use LinkedList When:**
- Need both List and Queue operations
- Need to store null elements

❌ **Avoid LinkedList When:**
- Only need queue operations (use ArrayDeque - faster)

---

## Queue Operations Summary

### Core Queue Methods:

```java
Queue<String> queue = new ArrayDeque<>();

// Adding (returns boolean)
queue.offer("item");     // Preferred - returns false if full
queue.add("item");       // Throws exception if full

// Removing (returns element)
String item = queue.poll();   // Preferred - returns null if empty
String item = queue.remove(); // Throws exception if empty

// Examining (returns element without removing)
String item = queue.peek();    // Preferred - returns null if empty
String item = queue.element(); // Throws exception if empty

// Checking
boolean empty = queue.isEmpty();
int size = queue.size();
```

### Deque-Specific Methods:

```java
Deque<String> deque = new ArrayDeque<>();

// Head operations
deque.addFirst("item");
deque.offerFirst("item");
deque.removeFirst();
deque.pollFirst();
deque.getFirst();
deque.peekFirst();

// Tail operations
deque.addLast("item");
deque.offerLast("item");
deque.removeLast();
deque.pollLast();
deque.getLast();
deque.peekLast();

// Stack operations (operate on head)
deque.push("item");    // Same as addFirst
String item = deque.pop();     // Same as removeFirst
```

---

## Real-World Applications

### 1. BFS Graph Traversal:

```java
import java.util.*;

class BFSTraversal {
    
    public static void bfs(Map<Integer, List<Integer>> graph, int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        
        queue.offer(start);
        visited.add(start);
        
        System.out.println("BFS Traversal:");
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");
            
            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4, 5));
        graph.put(3, Arrays.asList(6));
        
        bfs(graph, 1);  // Output: 1 2 3 4 5 6
    }
}
```

### 2. Print Job Scheduler:

```java
import java.util.*;

class PrintJob {
    String document;
    int pages;
    
    PrintJob(String document, int pages) {
        this.document = document;
        this.pages = pages;
    }
    
    @Override
    public String toString() {
        return document + "(" + pages + " pages)";
    }
}

class PrinterQueue {
    
    public static void processPrintJobs() {
        Queue<PrintJob> printQueue = new ArrayDeque<>();
        
        // Add print jobs
        printQueue.offer(new PrintJob("Report.pdf", 10));
        printQueue.offer(new PrintJob("Invoice.pdf", 2));
        printQueue.offer(new PrintJob("Presentation.pptx", 25));
        
        System.out.println("=== Print Queue ===");
        while (!printQueue.isEmpty()) {
            PrintJob job = printQueue.poll();
            System.out.println("Printing: " + job);
            // Simulate printing time
        }
    }
    
    public static void main(String[] args) {
        processPrintJobs();
    }
}
```

### 3. Sliding Window Maximum:

```java
import java.util.*;

class SlidingWindow {
    
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k == 0) return new int[0];
        
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();  // Stores indices
        
        for (int i = 0; i < nums.length; i++) {
            // Remove elements outside window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Remove smaller elements (maintain decreasing order)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            deque.offerLast(i);
            
            // Record maximum for this window
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        
        int[] result = maxSlidingWindow(nums, k);
        System.out.println("Sliding window max: " + Arrays.toString(result));
        // Output: [3, 3, 5, 5, 6, 7]
    }
}
```

---

## Best Practices

### ✅ DO:

```java
// Use ArrayDeque instead of LinkedList for queue
Queue<String> queue = new ArrayDeque<>();  // ✅ Faster

// Use PriorityQueue for priority-based processing
Queue<Task> tasks = new PriorityQueue<>(comparator);

// Use offer/poll/peek (doesn't throw exceptions)
queue.offer("item");
String item = queue.poll();

// Check before polling
if (!queue.isEmpty()) {
    String item = queue.poll();
}
```

### ❌ DON'T:

```java
// Don't use LinkedList for simple queue operations
Queue<String> queue = new LinkedList<>();  // ❌ Slower than ArrayDeque

// Don't use Stack class (use ArrayDeque)
Stack<String> stack = new Stack<>();  // ❌ Legacy, synchronized

// Don't add null to ArrayDeque or PriorityQueue
arrayDeque.offer(null);  // ❌ NullPointerException
priorityQueue.offer(null);  // ❌ NullPointerException

// Don't expect FIFO order in PriorityQueue
// Elements are ordered by priority, not insertion time
```

---

## Choosing the Right Queue

### Decision Tree:

```
Need a Queue/Deque?
    ↓
Need priority-based ordering?
    ├── YES → PriorityQueue
    └── NO → Continue
         ↓
Need to store null?
    ├── YES → LinkedList
    └── NO → ArrayDeque (best general-purpose choice)
```

---

## Interview Questions

**Q: When would you use a PriorityQueue over a regular Queue?**  
A: When elements need to be processed based on priority rather than arrival order. Examples: task scheduling, Dijkstra's algorithm, finding k-th largest element.

**Q: What's the difference between offer() and add()?**  
A: `offer()` returns false when queue is full, `add()` throws exception. Use `offer()` for bounded queues.

**Q: Why use ArrayDeque instead of Stack class?**  
A: ArrayDeque is faster (no synchronization), more memory efficient, and part of modern Collections framework. Stack is legacy and slower.

**Q: Time complexity of PriorityQueue operations?**  
A: offer() and poll() are O(log n), peek() is O(1). Internal min-heap rebalances on add/remove.

---

## Next Steps

Explore individual implementations:
1. [PriorityQueue](01-PriorityQueue/) - Heap-based priority queue
2. [ArrayDeque](02-ArrayDeque/) - High-performance deque
3. [LinkedList as Queue](03-LinkedList-Queue/) - Queue with list operations

Then explore:
- [Stacks](../05-Stacks/) - LIFO data structure
- [Trees](../06-Trees/) - Hierarchical structures
- [Graphs](../07-Graphs/) - Network structures
