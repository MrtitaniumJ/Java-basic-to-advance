# Data Structure Implementation

## Overview

This program demonstrates the internal implementation of three fundamental data structures: **Stack**, **Queue**, and **LinkedList**. Understanding how these structures work internally is crucial for:
- Writing efficient code
- Optimizing performance
- Preparing for technical interviews
- Understanding the Java Collections Framework

## Problem Analysis

### What We're Implementing

#### 1. **Stack (LIFO - Last In First Out)**
A stack is like a pile of plates where you add and remove from the top.

**Real-world examples:**
- Undo/Redo functionality in text editors
- Browser history (back button)
- Expression evaluation (parentheses matching)
- Function call stack in memory

**Key Operations:**
- `push(T value)`: Add element to top - O(1)
- `pop()`: Remove and return top element - O(1)
- `peek()`: View top element without removing - O(1)
- `isEmpty()`: Check if stack is empty - O(1)

#### 2. **Queue (FIFO - First In First Out)**
A queue is like a line at a store where people are served in arrival order.

**Real-world examples:**
- Print job queue
- Task scheduling
- Breadth-First Search (BFS) in graphs
- Asynchronous task processing

**Key Operations:**
- `enqueue(T value)`: Add element to rear - O(1)
- `dequeue()`: Remove element from front - O(1)
- `peek()`: View front element - O(1)
- `isEmpty()`: Check if queue is empty - O(1)

#### 3. **LinkedList (Dynamic array-like structure)**
A linked list is a chain of nodes where each node contains data and a reference to the next node.

**Real-world examples:**
- Implementing other data structures
- Memory management systems
- Undo/Redo with snapshots
- Browser cache

**Key Operations:**
- `add(T value)`: Add element at end - O(n)
- `addAtIndex(int index, T value)`: Insert at position - O(n)
- `get(int index)`: Access element - O(n)
- `remove(int index)`: Delete element - O(n)

## Design Decisions

### 1. Generic Implementation
All data structures use Java generics (`<T>`) to work with any data type:
```java
Stack<Integer> intStack = new Stack<>();
Stack<String> stringStack = new Stack<>();
```

**Advantages:**
- Type-safe operations
- Reusable code
- Compile-time type checking

### 2. Stack Using Array with Dynamic Resizing
```
Implementation Details:
- Use array to store elements
- Maintain 'top' pointer indicating stack top
- Double array capacity when full (amortized O(1))
```

**Why this approach:**
- Fast access and operations
- Predictable performance
- Easy to implement and understand

### 3. Queue Using Circular Array
```
Implementation Details:
- Use circular array to avoid wasted space
- Maintain 'front' and 'rear' pointers
- Automatically wrap around using modulo
```

**Why circular:**
- Prevents "shifting" elements (would be O(n))
- Efficient space usage
- O(1) enqueue and dequeue

### 4. LinkedList Node Structure
```java
private static class Node<T> {
    T data;
    Node<T> next;
}
```

**Advantages:**
- Dynamic memory allocation
- No fixed capacity
- Efficient insertions/deletions at known positions

## Complexity Analysis

### Stack
| Operation | Time | Space |
|-----------|------|-------|
| Push | O(1)* | O(n) |
| Pop | O(1) | - |
| Peek | O(1) | - |
| isEmpty | O(1) | - |

*Amortized O(1) with dynamic resizing

### Queue
| Operation | Time | Space |
|-----------|------|-------|
| Enqueue | O(1)* | O(n) |
| Dequeue | O(1) | - |
| Peek | O(1) | - |
| isEmpty | O(1) | - |

*Amortized O(1) with dynamic resizing

### LinkedList
| Operation | Time | Space |
|-----------|------|-------|
| Add | O(n) | O(n) |
| AddAtIndex | O(n) | - |
| Get | O(n) | - |
| Remove | O(n) | - |
| Contains | O(n) | - |

## Sample Input/Output

### Running the Program

```powershell
javac CustomDataStructures.java
java CustomDataStructures
```

### Expected Output

```
╔════════════════════════════════════════════════════════╗
║        CUSTOM DATA STRUCTURES IMPLEMENTATION          ║
╚════════════════════════════════════════════════════════╝

========== STACK DEMONSTRATION ==========
Pushing: 10, 20, 30, 40, 50
Stack size: 5
Peek: 50

Popping elements:
Popped: 50
Popped: 40
Popped: 30
Popped: 20
Popped: 10
Stack empty: true

========== QUEUE DEMONSTRATION ==========
Enqueueing: Alice, Bob, Charlie, David, Eve
Queue size: 5
Peek: Alice

Dequeueing elements:
Dequeued: Alice
Dequeued: Bob
Dequeued: Charlie
Dequeued: David
Dequeued: Eve
Queue empty: true

========== LINKEDLIST DEMONSTRATION ==========
Adding: 100, 200, 300, 400
LinkedList: 100 -> 200 -> 300 -> 400 -> null

Inserting 150 at index 1
LinkedList: 100 -> 150 -> 200 -> 300 -> 400 -> null

Accessing element at index 2: 200
List size: 5

Removing element at index 2
Removed: 200
LinkedList: 100 -> 150 -> 300 -> 400 -> null

Checking if list contains 300: true
Checking if list contains 999: false

========== REAL-WORLD USAGE: BALANCED PARENTHESES ==========
Expression: ({[()]})
Is balanced: true

Expression: (({[})
Is balanced: false

=======================================================
All demonstrations completed successfully!
=======================================================
```

## Real-World Application: Balanced Parentheses Checker

The program includes a practical application that uses a Stack to check if parentheses, braces, and brackets are properly balanced in an expression.

**Algorithm:**
1. Iterate through each character
2. If opening bracket, push to stack
3. If closing bracket, pop from stack and check match
4. At end, stack should be empty for valid expression

**Examples:**
- `({[]})` → Valid (true)
- `(({[})` → Invalid (false)
- `[({})` → Invalid - missing closing bracket

## Variations and Challenges

### Challenge 1: Implement Min Stack
Create a Stack that returns minimum element in O(1) time.

**Approach:** Maintain an additional stack of minimums
```java
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;
    // Store minimum up to current position
}
```

### Challenge 2: Implement Deque (Double-Ended Queue)
A queue that allows insertion/deletion from both ends.

**Operations:** addFirst, addLast, removeFirst, removeLast

### Challenge 3: Implement Stack Using Queue
Reverse the typical approach - implement Stack using Queue operations.

**Hint:** Use two queues or rearrange during pop

### Challenge 4: Reverse a LinkedList
Write a method to reverse a LinkedList in O(n) time and O(1) space.

```java
public void reverse() {
    Node<T> prev = null;
    Node<T> current = head;
    while (current != null) {
        Node<T> next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }
    head = prev;
}
```

### Challenge 5: Detect Cycle in LinkedList
Implement Floyd's cycle detection algorithm (tortoise and hare).

```java
public boolean hasCycle() {
    Node<T> slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

### Challenge 6: LRU Cache (Combining LinkedList and HashMap)
Implement an efficient cache with eviction policy using LinkedList + HashMap.

**Time Complexity:** O(1) for get and put operations
**Space Complexity:** O(capacity)

## Interview Tips

### When Discussing This Program

1. **Explain Design Choices:**
   - "I chose dynamic resizing for amortized O(1) performance"
   - "Circular array prevents shifting elements in queue"

2. **Discuss Trade-offs:**
   - Array-based: Fast access, fixed capacity
   - LinkedList: Dynamic size, slower access

3. **Real-world Applications:**
   - Stack: Used in compiler design, expression evaluation
   - Queue: Task scheduling, BFS algorithms
   - LinkedList: LRU caches, file systems

4. **Optimization Suggestions:**
   - How to improve space usage
   - How to handle exceptions
   - Memory management considerations

### Common Questions

**Q: Why use generic instead of specific types?**
A: Generics provide type-safety and reusability. Same code works for Integer, String, etc.

**Q: How does dynamic resizing work?**
A: When array is full, create new array with 2x capacity and copy elements.

**Q: What's the difference between Stack and Queue?**
A: Stack is LIFO (last item out first), Queue is FIFO (first item out first).

**Q: Why is circular array better for Queue?**
A: Eliminates need to shift elements, keeping dequeue operation O(1).

## Key Learnings

1. **Data structure design** impacts algorithm efficiency
2. **Generic programming** enables code reuse
3. **Dynamic resizing** provides flexibility with performance
4. **Internal implementation** understanding helps optimize code
5. **Real-world applications** validate theoretical concepts

## Further Exploration

1. Study the Collections Framework source code
2. Implement other structures: Deque, PriorityQueue, Heap
3. Combine structures for complex problems (LRU Cache)
4. Analyze memory usage with profiling tools
5. Benchmark different implementations

---

**Compilation**: `javac CustomDataStructures.java`
**Execution**: `java CustomDataStructures`
**Difficulty**: Intermediate
**Topics**: Data Structures, Generics, Arrays, LinkedLists
**Prerequisites**: Basic Java, OOP, Generics understanding
