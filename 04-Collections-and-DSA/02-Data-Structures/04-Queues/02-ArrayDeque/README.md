# ArrayDeque in Java

## Table of Contents
1. [Overview](#overview)
2. [Real-World Analogy](#real-world-analogy)
3. [Internal Structure](#internal-structure)
4. [Circular Array Design](#circular-array-design)
5. [Constructors and Creation Methods](#constructors-and-creation-methods)
6. [Queue Operations](#queue-operations)
7. [Deque Operations](#deque-operations)
8. [Performance Characteristics](#performance-characteristics)
9. [Resizing Strategy](#resizing-strategy)
10. [Iteration Behavior](#iteration-behavior)
11. [When to Use](#when-to-use)
12. [Common Use Cases](#common-use-cases)
13. [Real-World Applications](#real-world-applications)
14. [Thread Safety](#thread-safety)
15. [Comparison with Other Implementations](#comparison-with-other-implementations)
16. [Performance Benchmarks](#performance-benchmarks)
17. [Code Examples](#code-examples)

## Overview

`ArrayDeque` is a resizable array implementation of the `Deque` interface (Double-Ended Queue). It provides efficient addition and removal of elements at both ends of the queue. Unlike `LinkedList`, which uses node pointers, `ArrayDeque` uses a circular array, making it more memory-efficient and cache-friendly.

**Key Characteristics:**
- Implements both `Queue` and `Deque` interfaces
- Backed by a circular array (no separate node objects)
- More memory-efficient than `LinkedList`
- Not thread-safe (use `ConcurrentLinkedDeque` for concurrent access)
- Does NOT allow `null` elements (throws `NullPointerException`)
- Faster iteration due to better cache locality
- Preferred choice for both Queue and Stack operations

## Real-World Analogy

Imagine a train station with a single track where trains can arrive and depart from both ends:

**Traditional Queue (FIFO):**
- Trains arrive at the rear and depart from the front
- Like a line at a ticket counter

**Deque (Double-Ended):**
- Express trains can depart from the rear (priority route)
- New trains can be added at either end
- Trains can be removed from either end
- Like a train yard where locomotives can couple/uncouple from both ends

`ArrayDeque` is the efficient, array-based implementation that lets you add and remove from both ends with minimal overhead.

## Internal Structure

### Circular Array Representation

Instead of a standard linear array, `ArrayDeque` uses a **circular array** where the end wraps around to the beginning:

```
Initial: [_, _, _, _, _]
Add 1:   [1, _, _, _, _]  head=0, tail=1
Add 2:   [1, 2, _, _, _]  head=0, tail=2
Add 3:   [1, 2, 3, _, _]  head=0, tail=3
Remove:  [_, 2, 3, _, _]  head=1, tail=3

After multiple operations (circular wraparound):
[3, _, 1, 2, 4]
     ^     ^
   tail   head

This is logically: [1, 2, 3, 4] with head pointing to 1, tail after 4
```

### Array Structure Example

```
Physical array:  [7, 8, 1, 2, 3, 4, 5, 6]
Index:           [0, 1, 2, 3, 4, 5, 6, 7]

head = 2 (points to first element)
tail = 2 (points to next insertion position)

Logical queue: [1, 2, 3, 4, 5, 6, 7, 8]
```

### Index Calculation in Circular Array

```java
// Add to the front
headIndex = (headIndex - 1 + arrayLength) % arrayLength;

// Add to the rear
tailIndex = (tailIndex + 1) % arrayLength;

// Check if full
if ((tail + 1) % capacity == head) {
    // Array is full, need to resize
}
```

## Constructors and Creation Methods

```java
// 1. Default constructor (capacity 16)
Deque<Integer> deque1 = new ArrayDeque<>();

// 2. Constructor with initial capacity
Deque<Integer> deque2 = new ArrayDeque<>(50);

// 3. Constructor from another collection
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
Deque<Integer> deque3 = new ArrayDeque<>(list);

// 4. Create from Set
Set<Integer> set = new HashSet<>(Arrays.asList(10, 20, 30));
Deque<Integer> deque4 = new ArrayDeque<>(set);

// 5. Using Java 9+ factory method (from Collections)
// Note: No direct factory, but can use:
Deque<String> deque5 = new ArrayDeque<>(
    Arrays.asList("a", "b", "c")
);
```

## Queue Operations

Queue interface operations treat ArrayDeque as FIFO:

```java
// Add (Enqueue) - throw exception if full
boolean add(E e);
boolean offer(E e);

// Remove (Dequeue)
E remove();     // Throws exception if empty
E poll();       // Returns null if empty

// Retrieve (Peek)
E element();    // Throws exception if empty
E peek();       // Returns null if empty
```

## Deque Operations

Deque adds operations for both ends:

```java
// Add operations
void addFirst(E e);     // Throws if full
void addLast(E e);      // Throws if full
boolean offerFirst(E e);
boolean offerLast(E e);

// Remove operations
E removeFirst();        // Throws if empty
E removeLast();         // Throws if empty
E pollFirst();          // Returns null if empty
E pollLast();           // Returns null if empty

// Peek operations
E getFirst();           // Throws if empty
E getLast();            // Throws if empty
E peekFirst();          // Returns null if empty
E peekLast();           // Returns null if empty
```

### Deque as Stack

```java
// Push (add to head)
void push(E e);

// Pop (remove from head)
E pop();

// Peek top of stack
E peek();
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| addFirst/addLast | O(1) amortized | O(1) amortized | Constant after resize |
| removeFirst/removeLast | O(1) | O(1) | Just pointer movement |
| getFirst/getLast/peek* | O(1) | O(1) | Direct array access |
| get(index) | O(1) | O(1) | Random access |
| add/remove middle | O(n) | O(1) | Requires shifting |
| contains | O(n) | O(1) | Linear search |
| iterator | O(n) | O(n) | Full traversal |
| resize | O(n) | O(n) | When capacity exceeded |

## Resizing Strategy

### Capacity Growth Algorithm

```
Initial capacity: 16
When full: capacity = capacity * 2

Resizing sequence:
16 → 32 → 64 → 128 → 256 → 512 → 1024 → ...
```

### Resizing Process

When the circular array becomes full:
1. A new array with doubled capacity is created
2. All elements are copied from old array to new array in logical order
3. The `head` pointer is reset to position 0 (array is no longer circular after resize)
4. The `tail` pointer points to the element count

### Cost Analysis

```java
// Resizing is expensive (O(n)) but amortized O(1)
// Example: 100 insertions
// Most are O(1), but when capacity exceeded: O(current_size)
// Average cost per insertion = O(1)
```

## Iteration Behavior

Unlike `PriorityQueue`, `ArrayDeque` iterates in insertion order:

```java
Deque<Integer> deque = new ArrayDeque<>();
deque.addLast(1);
deque.addLast(2);
deque.addLast(3);

// Iterates from head to tail: 1, 2, 3
for (Integer num : deque) {
    System.out.println(num);
}

// Reverse iteration
Iterator<Integer> descending = deque.descendingIterator();
// 3, 2, 1
```

## When to Use

Choose `ArrayDeque` when you need:
- FIFO queue behavior (preferred over `LinkedList`)
- LIFO stack behavior
- Double-ended queue operations
- Efficient memory usage and cache locality
- No null elements are acceptable
- High performance for insertions/deletions at ends only

Avoid `ArrayDeque` if you need:
- Null element support (use `LinkedList`)
- Frequent middle insertions/deletions
- Fixed capacity constraints
- Thread safety (use `ConcurrentLinkedDeque`)
- Priority-based ordering (use `PriorityQueue`)

## Common Use Cases

1. **Task Queue:** FIFO processing of tasks
2. **Browser History:** Back/forward navigation
3. **Undo/Redo:** Stack-based operations
4. **Graph Algorithms:** BFS traversal
5. **Expression Evaluation:** Parsing and evaluation
6. **Job Scheduling:** Round-robin scheduling

## Real-World Applications

### Browser Navigation History

```java
class BrowserHistory {
    Deque<String> backStack = new ArrayDeque<>();
    Deque<String> forwardStack = new ArrayDeque<>();
    String current = null;
    
    void visit(String url) {
        if (current != null) {
            backStack.push(current);
        }
        current = url;
        forwardStack.clear();
    }
    
    String back() {
        if (backStack.isEmpty()) return current;
        forwardStack.push(current);
        current = backStack.pop();
        return current;
    }
    
    String forward() {
        if (forwardStack.isEmpty()) return current;
        backStack.push(current);
        current = forwardStack.pop();
        return current;
    }
}
```

### Sliding Window Algorithm

Priority queues work with sliding windows to find max/min in window:

```java
// MaxSlidingWindow using ArrayDeque
public static int[] maxSlidingWindow(int[] nums, int k) {
    if (nums.length == 0) return new int[0];
    
    int[] result = new int[nums.length - k + 1];
    Deque<Integer> deque = new ArrayDeque<>(); // Stores indices
    
    for (int i = 0; i < nums.length; i++) {
        // Remove elements outside window
        if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        
        // Remove smaller elements
        while (!deque.isEmpty() && 
               nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        deque.offerLast(i);
        
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    
    return result;
}
```

## Thread Safety

`ArrayDeque` is **NOT thread-safe**. For concurrent scenarios:

```java
// Option 1: Use synchronized wrapper
Deque<Integer> syncDeque = Collections.synchronizedDeque(
    new ArrayDeque<>()
);

// Option 2: Use ConcurrentLinkedDeque
Deque<Integer> concurrentDeque = 
    new ConcurrentLinkedDeque<>();

// Option 3: Manual synchronization
Deque<Integer> deque = new ArrayDeque<>();
synchronized(deque) {
    deque.addLast(value);
}
```

## Comparison with Other Implementations

| Feature | ArrayDeque | LinkedList | PriorityQueue | Stack |
|---------|-----------|-----------|--------------|-------|
| Head/Tail ops | O(1) | O(1) | O(log n) | O(1) |
| Random access | O(1) | O(n) | O(n) | O(n) |
| Memory overhead | Low | High (pointers) | Low | Low |
| Cache friendly | Yes | No | No | Yes |
| Null support | No | Yes | No | No |
| Thread-safe | No | No | No | No |
| Priority order | No | No | Yes | No |
| Use as Queue | Best | OK | Only if needed | No |
| Use as Stack | Best | OK | No | Yes |

## Performance Benchmarks

```
Operations: 100,000 add/remove operations

ArrayDeque (Queue operations):
  addLast:     ~0.002ms per op
  removeFirst: ~0.002ms per op
  Typical: 200ms for 100k paired operations

LinkedList (Queue operations):
  addLast:     ~0.005ms per op
  removeFirst: ~0.005ms per op
  Typical: 500ms for 100k paired operations

Stack operations (push/pop):
  ArrayDeque:  ~0.002ms per op
  LinkedList:  ~0.005ms per op
  Stack class: ~0.003ms per op

Memory usage (100k integers):
  ArrayDeque:  ~800KB
  LinkedList:  ~2.4MB (more overhead)
  PriorityQueue: ~400KB
```

## Code Examples

### Example 1: Basic Queue Operations

```java
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;

public class BasicArrayDeque {
    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();
        
        // Queue operations (FIFO)
        System.out.println("=== Queue Operations ===");
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        deque.addLast(40);
        
        System.out.println("Queue: " + deque);
        System.out.println("First: " + deque.peekFirst());
        System.out.println("Last: " + deque.peekLast());
        
        // Remove from front (FIFO)
        System.out.println("\nRemoving from front:");
        while (!deque.isEmpty()) {
            System.out.println("Removed: " + deque.removeFirst());
        }
        
        // Stack operations (LIFO)
        System.out.println("\n=== Stack Operations ===");
        deque.push(100);
        deque.push(200);
        deque.push(300);
        deque.push(400);
        
        System.out.println("Stack: " + deque);
        
        // Pop from top
        System.out.println("\nPopping from top:");
        while (!deque.isEmpty()) {
            System.out.println("Popped: " + deque.pop());
        }
    }
}
```

### Example 2: Deque Operations (Both Ends)

```java
import java.util.Deque;
import java.util.ArrayDeque;

public class DequeOperations {
    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();
        
        System.out.println("=== Deque Operations ===");
        
        // Adding to both ends
        deque.addFirst("Start");
        deque.addLast("End");
        deque.addFirst("Beginning");
        deque.addLast("Finish");
        
        System.out.println("Deque: " + deque);
        
        // Removing from both ends
        System.out.println("\nRemoving operations:");
        System.out.println("removeFirst(): " + deque.removeFirst());
        System.out.println("removeLast(): " + deque.removeLast());
        System.out.println("Current deque: " + deque);
        
        // Peeking at both ends
        System.out.println("\nPeeking:");
        System.out.println("First: " + deque.getFirst());
        System.out.println("Last: " + deque.getLast());
        System.out.println("Deque unchanged: " + deque);
        
        // Safe operations returning null
        Deque<String> empty = new ArrayDeque<>();
        System.out.println("\nEmpty deque operations:");
        System.out.println("peekFirst(): " + empty.peekFirst());
        System.out.println("pollLast(): " + empty.pollLast());
        System.out.println("No exception thrown!");
    }
}
```

### Example 3: Browser History Implementation

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserHistory {
    private Deque<String> backStack;
    private Deque<String> forwardStack;
    private String currentPage;
    
    public BrowserHistory() {
        backStack = new ArrayDeque<>();
        forwardStack = new ArrayDeque<>();
        currentPage = "home";
    }
    
    public void visit(String url) {
        if (!currentPage.equals("home")) {
            backStack.push(currentPage);
        }
        currentPage = url;
        forwardStack.clear();
        System.out.println("Visited: " + url);
    }
    
    public void goBack() {
        if (backStack.isEmpty()) {
            System.out.println("Cannot go back (at beginning)");
            return;
        }
        forwardStack.push(currentPage);
        currentPage = backStack.pop();
        System.out.println("Back to: " + currentPage);
    }
    
    public void goForward() {
        if (forwardStack.isEmpty()) {
            System.out.println("Cannot go forward (at end)");
            return;
        }
        backStack.push(currentPage);
        currentPage = forwardStack.pop();
        System.out.println("Forward to: " + currentPage);
    }
    
    public String getCurrentPage() {
        return currentPage;
    }
    
    public static void main(String[] args) {
        BrowserHistory browser = new BrowserHistory();
        
        browser.visit("google.com");
        browser.visit("github.com");
        browser.visit("stackoverflow.com");
        
        System.out.println("Current: " + browser.getCurrentPage());
        
        browser.goBack();
        browser.goBack();
        browser.goBack();
        
        browser.visit("javadoc.org");
        browser.goForward();
    }
}
```

### Example 4: Max Sliding Window

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Arrays;

public class MaxSlidingWindow {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k <= 0) {
            return new int[0];
        }
        
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Remove indices outside current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            // Add current index
            deque.offerLast(i);
            
            // Add to result when window is filled
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 3, 1, 2, 0, 5};
        int k = 3;
        
        int[] result = maxSlidingWindow(nums, k);
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Window size: " + k);
        System.out.println("Max in each window: " + Arrays.toString(result));
    }
}
```

### Example 5: Undo/Redo System

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class UndoRedoSystem {
    static class Action {
        String type;
        String description;
        
        Action(String type, String description) {
            this.type = type;
            this.description = description;
        }
        
        @Override
        public String toString() {
            return type + ": " + description;
        }
    }
    
    private Deque<Action> undoStack;
    private Deque<Action> redoStack;
    private StringBuilder document;
    
    public UndoRedoSystem() {
        undoStack = new ArrayDeque<>();
        redoStack = new ArrayDeque<>();
        document = new StringBuilder();
    }
    
    public void addText(String text) {
        document.append(text);
        Action action = new Action("ADD", "Added '" + text + "'");
        undoStack.push(action);
        redoStack.clear();
        System.out.println("Action: " + action);
        System.out.println("Document: " + document);
    }
    
    public void deleteText(int length) {
        if (document.length() < length) {
            length = document.length();
        }
        String deleted = document.substring(
            document.length() - length
        );
        document.delete(document.length() - length, document.length());
        Action action = new Action("DELETE", "Deleted '" + deleted + "'");
        undoStack.push(action);
        redoStack.clear();
        System.out.println("Action: " + action);
        System.out.println("Document: " + document);
    }
    
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo!");
            return;
        }
        Action action = undoStack.pop();
        redoStack.push(action);
        System.out.println("Undid: " + action);
    }
    
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo!");
            return;
        }
        Action action = redoStack.pop();
        undoStack.push(action);
        System.out.println("Redid: " + action);
    }
    
    public static void main(String[] args) {
        UndoRedoSystem editor = new UndoRedoSystem();
        
        editor.addText("Hello");
        editor.addText(" World");
        editor.deleteText(5);
        
        System.out.println("\nUndoing...");
        editor.undo();
        editor.undo();
        
        System.out.println("\nRedoing...");
        editor.redo();
        editor.redo();
    }
}
```

### Example 6: Round-Robin Job Scheduler

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class RoundRobinScheduler {
    static class Job {
        int id;
        int timeRequired;
        int timeUsed;
        
        Job(int id, int timeRequired) {
            this.id = id;
            this.timeRequired = timeRequired;
            this.timeUsed = 0;
        }
        
        @Override
        public String toString() {
            return String.format("Job{id=%d, required=%d, used=%d}",
                id, timeRequired, timeUsed);
        }
    }
    
    public static void scheduleJobs(Deque<Job> jobs, int timeSlice) {
        int currentTime = 0;
        System.out.println("Time Slice: " + timeSlice + "ms\n");
        
        while (!jobs.isEmpty()) {
            Job job = jobs.pollFirst();
            int timeToRun = Math.min(timeSlice, 
                                     job.timeRequired - job.timeUsed);
            
            job.timeUsed += timeToRun;
            currentTime += timeToRun;
            
            System.out.printf("Time %d-%d: Running %s%n",
                currentTime - timeToRun, currentTime, job);
            
            if (job.timeUsed < job.timeRequired) {
                jobs.addLast(job);
            } else {
                System.out.println("  → Job " + job.id + " completed!");
            }
        }
    }
    
    public static void main(String[] args) {
        Deque<Job> jobs = new ArrayDeque<>();
        jobs.addLast(new Job(1, 30));
        jobs.addLast(new Job(2, 20));
        jobs.addLast(new Job(3, 25));
        jobs.addLast(new Job(4, 15));
        
        scheduleJobs(jobs, 10);
    }
}
```

### Example 7: Balanced Parentheses Validator

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ParenthesesValidator {
    public static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                
                char open = stack.pop();
                if (!matches(open, c)) return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "()[]{}",
            "([{}])",
            "([)]",
            "{[}",
            "((",
            ")("
        };
        
        for (String test : testCases) {
            System.out.println("\"" + test + "\" → " + isValid(test));
        }
    }
}
```

## Summary

`ArrayDeque` is the most efficient and recommended implementation for queue and deque operations in Java. Its circular array design eliminates the memory overhead of linked structures while providing O(1) operations at both ends. Whether implementing FIFO queues, LIFO stacks, or bidirectional deques, `ArrayDeque` delivers superior performance and memory efficiency, making it the go-to choice for most double-ended queue scenarios.
