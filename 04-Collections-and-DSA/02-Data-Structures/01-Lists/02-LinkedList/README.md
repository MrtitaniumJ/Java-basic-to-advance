# LinkedList in Java

## Overview

`LinkedList` is a doubly-linked list implementation of both the `List` and `Deque` interfaces in Java's Collections Framework. Unlike ArrayList which uses a contiguous array, LinkedList uses a chain of nodes where each node contains a reference to the next and previous nodes. This structure makes LinkedList particularly efficient for insertion and deletion operations, especially at the beginning of the list.

## How It Works Internally

LinkedList consists of nodes, where each node contains:
- **Element (data)**: The actual value stored
- **next**: Reference to the next node
- **previous**: Reference to the previous node

The LinkedList maintains references to both the head (first node) and tail (last node) for efficient operations at both ends.

### Internal Structure
```
Node Structure:
[previous | data | next] <-> [previous | data | next] <-> [previous | data | next]

Visual representation:
head -> [null | E1 | *] <-> [* | E2 | *] <-> [* | E3 | null] <- tail

Where:
- * represents a pointer to the next/previous node
- null indicates no previous/next node
```

## Constructors and Creation Methods

```java
// Default constructor - creates empty LinkedList
LinkedList<String> list1 = new LinkedList<>();

// Constructor from another collection
List<String> source = Arrays.asList("A", "B", "C");
LinkedList<String> list2 = new LinkedList<>(source);

// Using stream
LinkedList<Integer> list3 = Stream.of(1, 2, 3, 4, 5)
    .collect(Collectors.toCollection(LinkedList::new));

// Adding elements during initialization (similar to ArrayList)
LinkedList<String> list4 = new LinkedList<String>() {{
    add("First");
    add("Second");
    add("Third");
}};
```

## Core Operations

### Add Operations (Insertion)
```java
LinkedList<String> tasks = new LinkedList<>();

// add(E e) - adds element at the end, O(1)
tasks.add("Task 1");
tasks.add("Task 2");
tasks.add("Task 3");
// [Task 1, Task 2, Task 3]

// add(int index, E e) - inserts at specific position, O(n)
tasks.add(1, "Priority Task");
// [Task 1, Priority Task, Task 2, Task 3]

// addFirst(E e) - adds to beginning, O(1)
tasks.addFirst("Urgent Task");
// [Urgent Task, Task 1, Priority Task, Task 2, Task 3]

// addLast(E e) - adds to end, O(1)
tasks.addLast("Task 4");
// [Urgent Task, Task 1, Priority Task, Task 2, Task 3, Task 4]

// push(E e) - same as addFirst() (Deque interface), O(1)
tasks.push("High Priority");
// [High Priority, Urgent Task, Task 1, Priority Task, Task 2, Task 3, Task 4]
```

### Remove Operations
```java
LinkedList<Integer> numbers = new LinkedList<>(Arrays.asList(
    10, 20, 30, 40, 50
));

// remove(int index) - removes at index, O(n)
int removed = numbers.remove(2);  // removes 30, returns it
System.out.println(removed);  // 30
// [10, 20, 40, 50]

// remove(Object o) - removes first occurrence, O(n)
numbers.remove(Integer.valueOf(20));
// [10, 40, 50]

// removeFirst() - removes from beginning, O(1)
numbers.removeFirst();  // removes 10
// [40, 50]

// removeLast() - removes from end, O(1)
numbers.removeLast();  // removes 50
// [40]

// pop() - same as removeFirst() (Deque interface), O(1)
numbers.add(10);
numbers.add(20);
int popped = numbers.pop();  // [10, 20, 40] -> returns 10
// [20, 40]

// removeIf() - removes elements matching predicate, O(n)
numbers.removeIf(n -> n > 30);
// [20]

// clear() - removes all elements, O(n)
numbers.clear();
```

### Get Operations
```java
LinkedList<String> items = new LinkedList<>(Arrays.asList(
    "Apple", "Banana", "Cherry", "Date"
));

// get(int index) - retrieves element at index, O(n)
String item = items.get(2);  // Cherry

// getFirst() - gets first element, O(1)
String first = items.getFirst();  // Apple

// getLast() - gets last element, O(1)
String last = items.getLast();  // Date

// peek() - retrieves first element without removing, O(1)
String peeked = items.peek();  // Apple (list unchanged)

// peekFirst() - same as peek(), O(1)
String peekedFirst = items.peekFirst();  // Apple

// peekLast() - retrieves last element without removing, O(1)
String peekedLast = items.peekLast();  // Date
```

### Set Operations
```java
LinkedList<String> colors = new LinkedList<>(Arrays.asList(
    "Red", "Green", "Blue"
));

// set(int index, E element) - replaces element at index, O(n)
colors.set(1, "Yellow");
// [Red, Yellow, Blue]

// replaceAll() - replaces all elements with function
colors.replaceAll(String::toUpperCase);
// [RED, YELLOW, BLUE]
```

### Query Operations
```java
LinkedList<String> fruits = new LinkedList<>(Arrays.asList(
    "Apple", "Banana", "Apple", "Cherry"
));

// size() - returns number of elements, O(1)
int size = fruits.size();  // 4

// isEmpty() - checks if list is empty, O(1)
boolean empty = fruits.isEmpty();  // false

// contains(Object o) - checks if element exists, O(n)
boolean hasApple = fruits.contains("Apple");  // true

// indexOf(Object o) - returns first occurrence index, O(n)
int index = fruits.indexOf("Apple");  // 0

// lastIndexOf(Object o) - returns last occurrence index, O(n)
int lastIndex = fruits.lastIndexOf("Apple");  // 2
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| add(E)    | O(1)            | O(1)             |
| add(i, E) | O(n)            | O(1)             |
| remove(i) | O(n)            | O(1)             |
| get(i)    | O(n)            | O(1)             |
| set(i, E) | O(n)            | O(1)             |
| contains()| O(n)            | O(1)             |
| indexOf() | O(n)            | O(1)             |
| addFirst()| O(1)            | O(1)             |
| removeFirst()| O(1)         | O(1)             |

Note: LinkedList's O(n) for get/set operations because it must traverse from head to reach the element.

## Memory Layout and Node Structure

Each node in LinkedList occupies more memory than ArrayList elements:

```
ArrayList element overhead:
32-bit JVM: 4 bytes pointer
64-bit JVM: 8 bytes pointer

LinkedList node overhead:
64-bit JVM: ~40 bytes per node
  - Object header: 12 bytes
  - three references (previous, element, next): 24 bytes
  - padding/alignment: 4 bytes
```

This overhead makes LinkedList memory-inefficient for small objects but efficient for operations that require frequent insertion/deletion at the beginning.

## Advantages

1. **Efficient insertions/deletions at ends**: O(1) for addFirst, removeLast, etc.
2. **No capacity resizing needed**: No expensive array copying
3. **Better for frequent modifications**: Especially at the beginning or middle
4. **Deque implementation**: Can be used as both Queue and Stack
5. **Predictable performance**: No worst-case O(n) resizing behavior
6. **Good for stream processing**: Works well in functional-style operations

## Disadvantages

1. **Slow random access**: O(n) time to access element at arbitrary index
2. **Higher memory overhead**: Extra pointers per node consume memory
3. **Cache unfriendly**: Nodes scattered in memory hurt CPU cache performance
4. **Complex internal structure**: More overhead than simple array
5. **Not thread-safe**: Requires external synchronization
6. **Slower iteration**: More memory accesses than ArrayList
7. **No capacity optimization**: Cannot pre-allocate based on anticipated size

## When to Use LinkedList

Use LinkedList when:
- You frequently add/remove elements at the beginning or end
- You don't need random access to elements
- You need to implement Queue or Deque operations
- You have frequent insertions/deletions in the middle of a large list
- Memory is not a primary concern
- You're implementing algorithms requiring bidirectional traversal

## Common Use Cases

1. **Task/Job queues**: Add tasks at end, remove from front (FIFO)
2. **Browser history**: Add new pages, can navigate back/forward
3. **Undo/Redo stacks**: Push actions onto stack, undo by popping
4. **LRU caches**: Move accessed items to end, remove from beginning
5. **Graph/Tree implementations**: Store adjacency lists using LinkedList
6. **Music playlists**: Add/remove songs from beginning or end

## Practical Code Examples

```java
import java.util.*;
import java.util.stream.*;

public class LinkedListExamples {
    
    // Example 1: Basic LinkedList operations
    public static void basicOperations() {
        LinkedList<Integer> deque = new LinkedList<>();
        
        // Adding elements at both ends
        deque.addLast(10);   // [10]
        deque.addLast(20);   // [10, 20]
        deque.addFirst(5);   // [5, 10, 20]
        deque.addLast(30);   // [5, 10, 20, 30]
        deque.addFirst(1);   // [1, 5, 10, 20, 30]
        
        System.out.println("Deque: " + deque);
        
        // Removing elements
        System.out.println("First: " + deque.removeFirst());  // 1
        System.out.println("Last: " + deque.removeLast());    // 30
        System.out.println("Deque after removals: " + deque);  // [5, 10, 20]
        
        // Peeking without removing
        System.out.println("Peek first: " + deque.peekFirst());  // 5
        System.out.println("Peek last: " + deque.peekLast());    // 20
    }
    
    // Example 2: LinkedList as Queue
    public static void queueOperations() {
        LinkedList<String> queue = new LinkedList<>();
        
        // Enqueue (add to end)
        queue.offer("Customer1");  // [Customer1]
        queue.offer("Customer2");  // [Customer1, Customer2]
        queue.offer("Customer3");  // [Customer1, Customer2, Customer3]
        queue.offer("Customer4");  // [Customer1, Customer2, Customer3, Customer4]
        
        System.out.println("Queue: " + queue);
        System.out.println("Queue size: " + queue.size());
        
        // Dequeue (remove from front)
        while (!queue.isEmpty()) {
            String served = queue.poll();
            System.out.println("Serving: " + served);
        }
    }
    
    // Example 3: LinkedList as Stack
    public static void stackOperations() {
        LinkedList<String> stack = new LinkedList<>();
        
        // Push
        stack.push("Book1");    // [Book1]
        stack.push("Book2");    // [Book2, Book1]
        stack.push("Book3");    // [Book3, Book2, Book1]
        stack.push("Book4");    // [Book4, Book3, Book2, Book1]
        
        System.out.println("Stack: " + stack);
        
        // Pop
        while (!stack.isEmpty()) {
            String book = stack.pop();
            System.out.println("Taking: " + book);
        }
    }
    
    // Example 4: Bidirectional iteration
    public static void bidirectionalIteration() {
        LinkedList<String> colors = new LinkedList<>(Arrays.asList(
            "Red", "Green", "Blue", "Yellow", "Purple"
        ));
        
        System.out.println("Forward:");
        Iterator<String> forward = colors.iterator();
        while (forward.hasNext()) {
            System.out.print(forward.next() + " ");
        }
        System.out.println();
        
        System.out.println("Backward:");
        Iterator<String> backward = colors.descendingIterator();
        while (backward.hasNext()) {
            System.out.print(backward.next() + " ");
        }
        System.out.println();
    }
    
    // Example 5: ListIterator for modification during iteration
    public static void listIteratorOperations() {
        LinkedList<Integer> numbers = new LinkedList<>(Arrays.asList(
            1, 2, 3, 4, 5
        ));
        
        System.out.println("Original: " + numbers);
        
        ListIterator<Integer> iterator = numbers.listIterator();
        while (iterator.hasNext()) {
            int num = iterator.next();
            if (num % 2 == 0) {
                iterator.set(num * 10);  // Multiply even numbers by 10
            }
        }
        
        System.out.println("After modification: " + numbers);  // [1, 20, 3, 40, 5]
    }
    
    // Example 6: Insertion in middle with LinkedList efficiency
    public static void insertionComparison() {
        // ArrayList insertion at beginning
        ArrayList<Integer> arrayList = new ArrayList<>(
            Arrays.asList(2, 3, 4, 5)
        );
        long start = System.nanoTime();
        arrayList.add(0, 1);
        long arrayTime = System.nanoTime() - start;
        
        // LinkedList insertion at beginning
        LinkedList<Integer> linkedList = new LinkedList<>(
            Arrays.asList(2, 3, 4, 5)
        );
        start = System.nanoTime();
        linkedList.addFirst(1);
        long linkedTime = System.nanoTime() - start;
        
        System.out.println("ArrayList add at 0: " + arrayTime + " ns");
        System.out.println("LinkedList addFirst: " + linkedTime + " ns");
        System.out.println("LinkedList is faster: " + (linkedTime < arrayTime));
    }
    
    // Example 7: Implementing LRU Cache concept
    public static void lruCacheSimulation() {
        LinkedList<String> cache = new LinkedList<>();
        int maxSize = 3;
        
        // Function to access/add item
        System.out.println("Accessing items (LRU Cache, max size 3):");
        String[] items = {"A", "B", "C", "D", "B", "E", "A"};
        
        for (String item : items) {
            if (cache.contains(item)) {
                cache.remove(item);
            } else if (cache.size() == maxSize) {
                String evicted = cache.removeFirst();
                System.out.println("  Evicting: " + evicted);
            }
            cache.addLast(item);
            System.out.println("  After accessing " + item + ": " + cache);
        }
    }
    
    // Example 8: Stream operations with LinkedList
    public static void streamOperations() {
        LinkedList<String> names = new LinkedList<>(Arrays.asList(
            "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"
        ));
        
        // Filter, map, and collect
        LinkedList<String> filtered = names.stream()
            .filter(name -> name.length() > 3)
            .map(String::toUpperCase)
            .collect(Collectors.toCollection(LinkedList::new));
        
        System.out.println("Original: " + names);
        System.out.println("Filtered (length > 3, uppercase): " + filtered);
        
        // Reduce to single string
        String joined = names.stream()
            .collect(Collectors.joining(", "));
        System.out.println("Joined: " + joined);
    }
    
    // Example 9: Reverse traversal
    public static void reverseTraversal() {
        LinkedList<String> playlist = new LinkedList<>(Arrays.asList(
            "Song1", "Song2", "Song3", "Song4", "Song5"
        ));
        
        System.out.println("Playing forward:");
        playlist.forEach(song -> System.out.print(song + " -> "));
        System.out.println("END");
        
        System.out.println("Playing backward:");
        playlist.descendingIterator()
            .forEachRemaining(song -> System.out.print(song + " <- "));
        System.out.println("START");
    }
    
    // Example 10: Performance comparison for different operations
    public static void performanceComparison() {
        int size = 10000;
        int iterations = 1000;
        
        // ArrayList performance
        ArrayList<Integer> arrayList = new ArrayList<>(
            Stream.range(0, size).boxed().collect(Collectors.toList())
        );
        
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            arrayList.get(size / 2);  // Access middle
        }
        long arrayGetTime = System.nanoTime() - start;
        
        // LinkedList performance
        LinkedList<Integer> linkedList = new LinkedList<>(
            Stream.range(0, size).boxed().collect(Collectors.toList())
        );
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            linkedList.get(size / 2);  // Access middle
        }
        long linkedGetTime = System.nanoTime() - start;
        
        System.out.println("ArrayList get middle (1000x): " + arrayGetTime + " ns");
        System.out.println("LinkedList get middle (1000x): " + linkedGetTime + " ns");
        System.out.println("LinkedList is slower by factor: " + 
            String.format("%.2f", (double) linkedGetTime / arrayGetTime));
        
        // Insertion at beginning
        arrayList = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            arrayList.add(0, i);  // Insert at beginning
        }
        long arrayInsertTime = System.nanoTime() - start;
        
        linkedList = new LinkedList<>();
        start = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            linkedList.addFirst(i);  // Insert at beginning
        }
        long linkedInsertTime = System.nanoTime() - start;
        
        System.out.println("\nArrayList insert at 0 (100x): " + arrayInsertTime + " ns");
        System.out.println("LinkedList addFirst (100x): " + linkedInsertTime + " ns");
        System.out.println("ArrayList is slower by factor: " + 
            String.format("%.2f", (double) arrayInsertTime / linkedInsertTime));
    }
    
    public static void main(String[] args) {
        System.out.println("=== Basic Operations ===");
        basicOperations();
        
        System.out.println("\n=== Queue Operations ===");
        queueOperations();
        
        System.out.println("\n=== Stack Operations ===");
        stackOperations();
        
        System.out.println("\n=== Bidirectional Iteration ===");
        bidirectionalIteration();
        
        System.out.println("\n=== ListIterator Operations ===");
        listIteratorOperations();
        
        System.out.println("\n=== Insertion Comparison ===");
        insertionComparison();
        
        System.out.println("\n=== LRU Cache Simulation ===");
        lruCacheSimulation();
        
        System.out.println("\n=== Stream Operations ===");
        streamOperations();
        
        System.out.println("\n=== Reverse Traversal ===");
        reverseTraversal();
        
        System.out.println("\n=== Performance Comparison ===");
        performanceComparison();
    }
}
```

## Advanced Operations

### Deque Methods
```java
LinkedList<String> deque = new LinkedList<>();

// Queue methods
deque.offer("A");      // add to end
deque.poll();          // remove from front

// Stack methods
deque.push("B");       // add to front
deque.pop();           // remove from front

// Peek operations
deque.peek();          // view front without removing
deque.peekFirst();     // view first without removing
deque.peekLast();      // view last without removing
```

### Sorting
```java
LinkedList<String> words = new LinkedList<>(Arrays.asList(
    "elephant", "ant", "bear", "dog", "cat"
));

Collections.sort(words);  // In-place sort
System.out.println(words);  // [ant, bear, cat, dog, elephant]
```

## Thread-Safety Considerations

LinkedList is **not thread-safe**. For concurrent use:

```java
// Synchronize manually
List<String> syncList = Collections.synchronizedList(new LinkedList<>());

// Or use ConcurrentLinkedDeque for high concurrency
Deque<String> concurrent = new ConcurrentLinkedDeque<>();
```

## Comparison with Other List Implementations

| Feature | LinkedList | ArrayList | Vector |
|---------|-----------|-----------|--------|
| Random Access | O(n) | O(1) | O(1) |
| Insert/Delete (start) | O(1) | O(n) | O(n) |
| Insert/Delete (end) | O(1) | O(1)* | O(1)* |
| Memory Overhead | High | Low | Low |
| Iteration Speed | Slower | Faster | Faster |
| Deque Support | Yes | No | No |
| Thread-Safe | No | No | Yes |
| Use Case | Queue/Deque | General | Legacy |

## Summary

LinkedList is the ideal choice when you need efficient insertion/deletion at both ends of the list or require Deque functionality. However, its higher memory overhead and poor random access performance make it unsuitable for applications requiring frequent element access. Understanding when to use LinkedList versus ArrayList is crucial for optimizing data structure performance in Java applications.

