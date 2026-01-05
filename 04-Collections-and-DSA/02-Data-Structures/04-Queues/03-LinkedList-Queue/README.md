# LinkedList as Queue in Java

## Table of Contents
1. [Overview](#overview)
2. [Real-World Analogy](#real-world-analogy)
3. [Internal Structure](#internal-structure)
4. [Node-Based Architecture](#node-based-architecture)
5. [Constructors and Creation Methods](#constructors-and-creation-methods)
6. [Queue Operations](#queue-operations)
7. [Deque Operations](#deque-operations)
8. [Performance Characteristics](#performance-characteristics)
9. [Memory Overhead](#memory-overhead)
10. [Iteration Behavior](#iteration-behavior)
11. [When to Use](#when-to-use)
12. [Common Use Cases](#common-use-cases)
13. [Real-World Applications](#real-world-applications)
14. [Thread Safety](#thread-safety)
15. [Comparison with Other Implementations](#comparison-with-other-implementations)
16. [Performance Benchmarks](#performance-benchmarks)
17. [Code Examples](#code-examples)

## Overview

`LinkedList` is a doubly-linked list implementation of the `List`, `Queue`, and `Deque` interfaces. While it supports queue operations, it's generally less efficient than `ArrayDeque` for queue/deque usage due to higher memory overhead and cache locality issues. However, it allows `null` elements and provides good performance for specific scenarios.

**Key Characteristics:**
- Implements `List`, `Queue`, and `Deque` interfaces
- Backed by a doubly-linked list (each node contains forward and backward references)
- Allows `null` elements (unlike `ArrayDeque` or `PriorityQueue`)
- Not thread-safe (use `ConcurrentLinkedQueue` for concurrent access)
- Higher memory overhead due to node pointers
- Efficient for insertion/deletion at known positions
- Good for iterators that modify structure during iteration

## Real-World Analogy

Imagine a conga line at a party:

**Standard Queue (FIFO):**
- New dancers join at the back
- Dancers leave from the front
- Only the people at the ends matter

**LinkedList Queue:**
- Each dancer holds hands with the person before and after them
- We can quickly reach anyone in line, but we have to follow the chain
- If someone leaves, we just disconnect them from the chain
- We can insert someone anywhere in the middle

`LinkedList` is like a conga line where each person maintains references to both neighbors, allowing flexible insertion and deletion anywhere in the line.

## Internal Structure

### Node-Based Architecture

```java
class Node<E> {
    E item;           // The actual element
    Node<E> next;     // Reference to next node
    Node<E> prev;     // Reference to previous node
    
    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

### Doubly-Linked List Representation

```
Queue: [A] <-> [B] <-> [C] <-> [D]
       head                    tail

Visual representation:
┌─────┐     ┌─────┐     ┌─────┐     ┌─────┐
│  A  │────→│  B  │────→│  C  │────→│  D  │
│ ref │←────│ ref │←────│ ref │←────│ ref │
└─────┘     └─────┘     └─────┘     └─────┘

head points to Node(A)
tail points to Node(D)
```

### Queue Operations Internally

```
Add to end (addLast/offer):
1. Create new node
2. If empty: set as head and tail
3. If not: link current tail to new node
4. Update tail pointer

Remove from front (removeFirst/poll):
1. Get first node's element
2. Unlink first node
3. Update head pointer
4. Return element
```

## Constructors and Creation Methods

```java
// 1. Default constructor (empty LinkedList)
Queue<Integer> queue1 = new LinkedList<>();

// 2. Constructor from collection
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
Queue<Integer> queue2 = new LinkedList<>(list);

// 3. Constructor with null support
Queue<String> queue3 = new LinkedList<>();
queue3.add(null); // This works with LinkedList!

// 4. Using Deque interface
Deque<Integer> deque1 = new LinkedList<>();

// 5. Cast to specific interface
List<Integer> asLst = new LinkedList<>();
Queue<Integer> asQueue = (Queue<Integer>) asLst;
```

## Queue Operations

Standard FIFO queue operations:

```java
// Add operations
boolean add(E e);              // Throws exception if full
boolean offer(E e);            // Returns false if full

// Remove operations
E remove();                    // Throws exception if empty
E poll();                      // Returns null if empty

// Peek operations
E element();                   // Throws exception if empty
E peek();                      // Returns null if empty
```

### Internal Implementation Details

```java
// For LinkedList as Queue:
queue.add(e);       // Calls addLast()
queue.poll();       // Calls removeFirst()
queue.peek();       // Returns first node's element

// LinkedList implementation:
public boolean add(E e) {
    linkLast(e);
    return true;
}

private void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
}
```

## Deque Operations

`LinkedList` also implements `Deque` for double-ended operations:

```java
// Add to both ends
void addFirst(E e);           // Add at head
void addLast(E e);            // Add at tail
boolean offerFirst(E e);
boolean offerLast(E e);

// Remove from both ends
E removeFirst();              // Remove from head
E removeLast();               // Remove from tail
E pollFirst();
E pollLast();

// Peek at both ends
E getFirst();
E getLast();
E peekFirst();
E peekLast();

// Stack operations
void push(E e);              // Same as addFirst()
E pop();                     // Same as removeFirst()
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| add/addLast | O(1) | O(1) | Direct tail insertion |
| addFirst | O(1) | O(1) | Direct head insertion |
| remove/removeFirst | O(1) | O(1) | Direct head removal |
| removeLast | O(1) | O(1) | Direct tail removal |
| get(0)/getFirst | O(1) | O(1) | Direct head access |
| get(size-1)/getLast | O(1) | O(1) | Direct tail access |
| get(index) | O(n) | O(1) | Linear search from head |
| remove(index) | O(n) | O(1) | Must find node first |
| contains | O(n) | O(1) | Linear search |
| iterator | O(1) init, O(n) traversal | O(1) | Maintains node reference |
| reverse iteration | O(1) init, O(n) traversal | O(1) | Uses prev pointers |

## Memory Overhead

### Per-Node Memory Usage

```
Data: Integer (8 bytes - object reference)
Prev pointer: 8 bytes
Next pointer: 8 bytes
Node overhead: ~24 bytes
─────────────────────
Total per node: ~48 bytes

Storing Integer value 5:
LinkedList node: 48 bytes (including value)
ArrayDeque array slot: 8 bytes

LinkedList overhead ratio: 48/8 = 6x
```

### Example Comparison

```
Storing 1 million integers:

LinkedList:
- Array of node references: 8MB
- 1M nodes at 48 bytes each: 48MB
- Total: ~56MB

ArrayDeque:
- Array of integers: 8MB
- Total: ~8MB

LinkedList uses 7x more memory!
```

## Iteration Behavior

LinkedList provides both forward and reverse iteration:

```java
LinkedList<Integer> list = new LinkedList<>(
    Arrays.asList(10, 20, 30, 40, 50)
);

// Forward iteration
System.out.println("Forward:");
for (Integer num : list) {
    System.out.println(num);  // 10, 20, 30, 40, 50
}

// Reverse iteration (unique to LinkedList)
System.out.println("Reverse:");
Iterator<Integer> reverse = list.descendingIterator();
while (reverse.hasNext()) {
    System.out.println(reverse.next());  // 50, 40, 30, 20, 10
}

// Safe concurrent modification
Iterator<Integer> iter = list.iterator();
while (iter.hasNext()) {
    Integer num = iter.next();
    if (num == 30) {
        iter.remove();  // Safe removal during iteration
    }
}
```

## When to Use

Choose `LinkedList` when you need:
- `null` element support (required feature)
- Frequent insertion/deletion in the middle (with iterator)
- Bidirectional iteration (descendingIterator)
- Safe concurrent structural modification during iteration
- LIFO stack behavior with Deque interface

Avoid `LinkedList` if you need:
- Queue-only behavior (use `ArrayDeque` instead)
- High memory efficiency (use `ArrayDeque`)
- Random access performance (use `ArrayList`)
- No null elements (use `ArrayDeque` or `PriorityQueue`)
- Cache-friendly operations (use `ArrayDeque`)

## Common Use Cases

1. **Undo/Redo with Null States:** Track operations including null operations
2. **Musical Playlist:** Add/remove songs from both ends
3. **Message Queue with nulls:** Allow null sentinel values
4. **LRU Cache:** Efficient removal from middle with iterator
5. **Complex Data Structures:** Foundation for other structures

## Real-World Applications

### LRU Cache Implementation

```java
class LRUCache {
    LinkedList<Integer> recency;  // Order of access
    Map<Integer, Integer> cache;
    int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.recency = new LinkedList<>();
        this.cache = new HashMap<>();
    }
    
    public void access(Integer key) {
        if (recency.contains(key)) {
            recency.remove((Object) key);
        } else if (recency.size() >= capacity) {
            Integer leastRecent = recency.removeFirst();
            cache.remove(leastRecent);
        }
        recency.addLast(key);
    }
}
```

### Music Playlist with Null Support

Some applications need to track paused states using nulls:

```java
class Playlist {
    LinkedList<String> songs = new LinkedList<>();
    
    void play(String song) {
        songs.addLast(song);
    }
    
    void pause() {
        songs.addLast(null);  // LinkedList allows null!
    }
    
    void resume(String song) {
        songs.addLast(song);
    }
}
```

## Thread Safety

`LinkedList` is **NOT thread-safe**:

```java
// Option 1: Collections.synchronizedList
List<Integer> syncList = Collections.synchronizedList(
    new LinkedList<>()
);

// Option 2: ConcurrentLinkedQueue (for Queue interface)
Queue<Integer> concurrentQueue = 
    new ConcurrentLinkedQueue<>();

// Option 3: Manual synchronization
LinkedList<Integer> list = new LinkedList<>();
synchronized(list) {
    list.addLast(value);
}

// Important: Iterator is not synchronized!
synchronized(list) {
    Iterator<Integer> iter = list.iterator();
    while (iter.hasNext()) {
        System.out.println(iter.next());
    }
}
```

## Comparison with Other Implementations

| Feature | LinkedList | ArrayDeque | PriorityQueue | ArrayList |
|---------|-----------|-----------|--------------|-----------|
| Queue ops | O(1) | O(1) amort | O(log n) | O(n) |
| Get(index) | O(n) | O(1) | O(n) | O(1) |
| Memory | High | Low | Low | Low |
| Cache friendly | No | Yes | No | Yes |
| Null support | Yes | No | No | Yes |
| Thread-safe | No | No | No | No |
| Forward iterate | O(n) | O(n) | O(n) | O(n) |
| Reverse iterate | O(n) | O(n) | O(n) | O(n) |
| Best for Queue | Good | Best | Needs priority | Poor |
| Best for Stack | Good | Best | N/A | Poor |

## Performance Benchmarks

```
Operations: 100,000 add/remove pairs

LinkedList (head/tail operations):
  addLast:      ~0.003ms per op
  removeFirst:  ~0.003ms per op
  Typical:      300ms for 100k operations

ArrayDeque (head/tail operations):
  addLast:      ~0.002ms per op
  removeFirst:  ~0.002ms per op
  Typical:      200ms for 100k operations

LinkedList get(n/2):
  Time: ~2.5ms per operation
  
ArrayDeque get(n/2):
  Time: ~0.001ms per operation

Memory for 10,000 integers:
  LinkedList: ~960KB (with pointers)
  ArrayDeque: ~80KB (array only)
```

## Code Examples

### Example 1: Basic Queue Operations

```java
import java.util.Queue;
import java.util.LinkedList;

public class BasicLinkedListQueue {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        
        System.out.println("=== Queue Operations ===");
        
        // Adding elements
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        queue.offer("Fourth");
        queue.offer("Fifth");
        
        System.out.println("Queue after additions: " + queue);
        System.out.println("Queue size: " + queue.size());
        
        // Peek at front without removing
        System.out.println("Front element: " + queue.peek());
        System.out.println("Queue unchanged: " + queue);
        
        // Remove elements in FIFO order
        System.out.println("\nRemoving elements:");
        while (!queue.isEmpty()) {
            System.out.println("Removed: " + queue.poll());
        }
        
        System.out.println("Final queue: " + queue);
        System.out.println("isEmpty: " + queue.isEmpty());
        
        // Safe operations on empty queue
        System.out.println("\nOn empty queue:");
        System.out.println("peek(): " + queue.peek());
        System.out.println("poll(): " + queue.poll());
    }
}
```

### Example 2: Deque Operations (Both Ends)

```java
import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;

public class LinkedListDeque {
    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<>();
        
        System.out.println("=== Deque Operations ===");
        
        // Add to both ends
        deque.addFirst(1);
        deque.addLast(5);
        deque.addFirst(0);
        deque.addLast(6);
        deque.addFirst(-1);
        
        System.out.println("Deque: " + deque);
        
        // Remove from both ends
        System.out.println("\nRemoving from both ends:");
        System.out.println("removeFirst(): " + deque.removeFirst());
        System.out.println("removeLast(): " + deque.removeLast());
        System.out.println("After removals: " + deque);
        
        // Get elements from both ends
        System.out.println("\nGetting elements:");
        System.out.println("getFirst(): " + deque.getFirst());
        System.out.println("getLast(): " + deque.getLast());
        System.out.println("Deque unchanged: " + deque);
        
        // Peek operations (safe)
        System.out.println("\nPeeking:");
        System.out.println("peekFirst(): " + deque.peekFirst());
        System.out.println("peekLast(): " + deque.peekLast());
        
        // Reverse iteration
        System.out.println("\nReverse iteration:");
        Iterator<Integer> reverse = deque.descendingIterator();
        System.out.print("Reverse: ");
        while (reverse.hasNext()) {
            System.out.print(reverse.next() + " ");
        }
        System.out.println();
    }
}
```

### Example 3: Null Element Support

```java
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class NullElementHandling {
    public static void main(String[] args) {
        // LinkedList allows null
        Queue<String> linkedListQueue = new LinkedList<>();
        linkedListQueue.add("Start");
        linkedListQueue.add(null);  // Allowed!
        linkedListQueue.add("End");
        
        System.out.println("=== LinkedList with Nulls ===");
        System.out.println("Queue: " + linkedListQueue);
        
        while (!linkedListQueue.isEmpty()) {
            String element = linkedListQueue.poll();
            System.out.println("Processed: " + element);
        }
        
        // PriorityQueue does NOT allow null
        System.out.println("\n=== PriorityQueue with Nulls ===");
        Queue<String> priorityQueue = new PriorityQueue<>();
        try {
            priorityQueue.add(null);
        } catch (NullPointerException e) {
            System.out.println("PriorityQueue threw: " + 
                e.getClass().getSimpleName());
        }
        
        // Use LinkedList when null support is needed
        System.out.println("\nUse LinkedList for null support!");
    }
}
```

### Example 4: LRU Cache Implementation

```java
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private final int capacity;
    private final LinkedList<Integer> accessOrder;
    private final Map<Integer, Integer> cache;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.accessOrder = new LinkedList<>();
        this.cache = new HashMap<>();
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // Remove from access order
            accessOrder.remove((Object) key);
        } else if (cache.size() >= capacity) {
            // Remove least recently used
            Integer lru = accessOrder.removeFirst();
            cache.remove(lru);
        }
        
        cache.put(key, value);
        accessOrder.addLast(key);
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        // Move to end (most recently used)
        accessOrder.remove((Object) key);
        accessOrder.addLast(key);
        
        return cache.get(key);
    }
    
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        
        System.out.println("=== LRU Cache Operations ===");
        
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);
        System.out.println("Added 1, 2, 3");
        
        System.out.println("get(1): " + cache.get(1));
        
        cache.put(4, 400);  // Evicts 2 (least recently used)
        System.out.println("Added 4, evicted 2");
        
        System.out.println("get(2): " + cache.get(2));  // -1 (not found)
        System.out.println("get(3): " + cache.get(3));  // 300
    }
}
```

### Example 5: Safe Concurrent Modification

```java
import java.util.LinkedList;
import java.util.Iterator;

public class ConcurrentModification {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        
        System.out.println("=== Safe Removal with Iterator ===");
        System.out.println("Original: " + list);
        
        // Safe modification using iterator
        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()) {
            Integer num = iter.next();
            if (num % 2 == 0) {  // Remove even numbers
                iter.remove();  // Safe!
            }
        }
        
        System.out.println("After removing evens: " + list);
        
        // Unsafe: Direct removal during iteration
        System.out.println("\n=== Unsafe Removal ===");
        LinkedList<Integer> list2 = new LinkedList<>(list);
        
        try {
            for (Integer num : list2) {
                if (num > 5) {
                    list2.remove(num);  // ConcurrentModificationException!
                }
            }
        } catch (Exception e) {
            System.out.println("Caught: " + 
                e.getClass().getSimpleName());
            System.out.println("Reason: Structural modification during iteration");
        }
        
        System.out.println("List2: " + list2);
    }
}
```

### Example 6: Palindrome Validator

```java
import java.util.LinkedList;
import java.util.Deque;

public class PalindromeValidator {
    public static boolean isPalindrome(String str) {
        Deque<Character> deque = new LinkedList<>();
        
        // Add all characters
        for (char c : str.toLowerCase().toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                deque.add(c);
            }
        }
        
        // Compare from both ends
        while (deque.size() > 1) {
            char first = deque.removeFirst();
            char last = deque.removeLast();
            if (first != last) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "A man, a plan, a canal: Panama",
            "hello",
            "12321",
            "Was it a car or a cat I saw?",
            "Madam"
        };
        
        System.out.println("=== Palindrome Validation ===");
        for (String test : testCases) {
            System.out.println("\"" + test + "\" → " + 
                isPalindrome(test));
        }
    }
}
```

### Example 7: Expression Evaluation

```java
import java.util.LinkedList;
import java.util.Deque;

public class ExpressionEvaluator {
    public static int evaluate(String expression) {
        Deque<Integer> stack = new LinkedList<>();
        String[] tokens = expression.split(" ");
        
        for (String token : tokens) {
            if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                int result = applyOperator(a, b, token);
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
    
    private static int applyOperator(int a, int b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: return 0;
        }
    }
    
    public static void main(String[] args) {
        String[] expressions = {
            "3 4 +",      // 7
            "10 5 -",     // 5
            "6 4 *",      // 24
            "15 3 /",     // 5
            "3 4 + 2 *"   // 14
        };
        
        System.out.println("=== Postfix Expression Evaluation ===");
        for (String expr : expressions) {
            System.out.println("\"" + expr + "\" = " + 
                evaluate(expr));
        }
    }
}
```

## Summary

`LinkedList` serves as a flexible queue/deque implementation that prioritizes flexibility over performance. Its ability to support `null` elements, provide bidirectional iteration, and allow safe concurrent modification during iteration makes it valuable for specific scenarios. However, for typical queue and deque operations without null support requirements, `ArrayDeque` is the superior choice due to better memory efficiency and performance. Understanding when to use `LinkedList` versus `ArrayDeque` is crucial for optimal Java collections usage.
