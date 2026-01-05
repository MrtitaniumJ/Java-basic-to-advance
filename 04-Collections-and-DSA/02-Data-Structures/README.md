# Data Structures in Java

## Table of Contents
- [Introduction](#introduction)
- [Why Data Structures Matter](#why-data-structures-matter)
- [Data Structure Categories](#data-structure-categories)
- [Comparison Table](#comparison-table)
- [Complexity Analysis](#complexity-analysis)
- [Decision Tree Guide](#decision-tree-guide)
- [Visual Hierarchy](#visual-hierarchy)
- [When to Use Each Type](#when-to-use-each-type)
- [Learning Path](#learning-path)

---

## Introduction

Data structures are specialized formats for organizing, processing, retrieving, and storing data. They provide a means to manage large amounts of data efficiently for uses such as large databases and internet indexing services. In Java, understanding data structures is fundamental to writing efficient, scalable, and maintainable code.

A data structure is not just a container for data—it's a way of organizing data that considers both the data itself and the operations that will be performed on it. The choice of data structure directly impacts the performance of your application, affecting everything from memory usage to execution speed.

Java provides a rich Collections Framework that implements various data structures, but understanding the underlying concepts, their strengths, weaknesses, and appropriate use cases is crucial for making informed design decisions. This guide covers eight fundamental data structure categories that form the backbone of computer science and software engineering.

---

## Why Data Structures Matter

### 1. **Performance Optimization**
Different data structures offer different performance characteristics for various operations. Choosing the right data structure can mean the difference between an O(1) constant-time operation and an O(n) linear-time operation, which becomes critical as data scales.

### 2. **Memory Efficiency**
Data structures vary significantly in their memory footprint. Understanding these differences helps in building applications that run efficiently even on resource-constrained environments.

### 3. **Code Organization**
Well-chosen data structures lead to cleaner, more maintainable code. They provide natural abstractions that make code easier to understand and modify.

### 4. **Problem-Solving Skills**
Many algorithmic problems have elegant solutions when the right data structure is applied. Mastering data structures enhances your problem-solving toolkit.

### 5. **Interview Preparation**
Data structures form the core of technical interviews at major tech companies. Deep understanding is essential for career advancement in software engineering.

### 6. **Real-World Applications**
- **Lists**: Shopping carts, playlists, task lists
- **Sets**: Unique user IDs, tag systems, duplicate detection
- **Maps**: Caching, configuration management, dictionaries
- **Queues**: Message brokers, task scheduling, breadth-first search
- **Stacks**: Undo/redo functionality, expression evaluation, depth-first search
- **Trees**: File systems, organizational hierarchies, decision trees
- **Graphs**: Social networks, routing algorithms, dependency resolution
- **Heaps**: Priority queues, scheduling algorithms, top-K problems

---

## Data Structure Categories

### 1. [Lists](01-Lists/)
**Sequential collections maintaining insertion order**

Lists are ordered collections that allow duplicate elements and provide positional access. Java offers multiple implementations:
- `ArrayList`: Dynamic array with fast random access
- `LinkedList`: Doubly-linked list with efficient insertions/deletions
- `Vector`: Synchronized ArrayList (legacy)
- `Stack`: LIFO structure (legacy, use Deque instead)

**Key Characteristics:**
- Maintains insertion order
- Allows duplicates
- Index-based access
- Dynamic sizing

### 2. [Sets](02-Sets/)
**Collections ensuring element uniqueness**

Sets guarantee no duplicate elements and are ideal when uniqueness is required:
- `HashSet`: Fast operations using hash table
- `LinkedHashSet`: Maintains insertion order
- `TreeSet`: Sorted set using Red-Black tree
- `EnumSet`: Specialized for enum types

**Key Characteristics:**
- No duplicates allowed
- Various ordering guarantees
- Membership testing
- Set operations (union, intersection, difference)

### 3. [Maps](03-Maps/)
**Key-value pair associations**

Maps store data as key-value pairs, providing efficient lookup by key:
- `HashMap`: Fast, unordered key-value storage
- `LinkedHashMap`: Maintains insertion/access order
- `TreeMap`: Sorted by keys using Red-Black tree
- `Hashtable`: Synchronized HashMap (legacy)
- `ConcurrentHashMap`: Thread-safe without full synchronization

**Key Characteristics:**
- Unique keys (values can duplicate)
- O(1) average lookup time
- Various ordering options
- Flexible data modeling

### 4. [Queues](04-Queues/)
**FIFO (First-In-First-Out) structures**

Queues process elements in order, essential for scheduling and buffering:
- `LinkedList`: Implements Queue interface
- `PriorityQueue`: Elements ordered by priority
- `ArrayDeque`: Resizable array implementation
- `BlockingQueue`: Thread-safe queue implementations

**Key Characteristics:**
- FIFO ordering (usually)
- Efficient add/remove at ends
- Producer-consumer patterns
- Scheduling and buffering

### 5. [Stacks](05-Stacks/)
**LIFO (Last-In-First-Out) structures**

Stacks provide last-in-first-out access, crucial for backtracking and recursive algorithms:
- `Stack`: Legacy implementation (use Deque)
- `Deque`: Recommended stack implementation
- Application in recursion, parsing, and state management

**Key Characteristics:**
- LIFO ordering
- Push and pop operations
- Backtracking support
- Expression evaluation

### 6. [Trees](06-Trees/)
**Hierarchical data structures**

Trees represent hierarchical relationships with parent-child nodes:
- Binary Trees
- Binary Search Trees (BST)
- AVL Trees (self-balancing)
- Red-Black Trees
- B-Trees and B+ Trees
- Trie (prefix trees)

**Key Characteristics:**
- Hierarchical organization
- Efficient searching (when balanced)
- Natural recursion
- Various balancing strategies

### 7. [Graphs](07-Graphs/)
**Network structures with vertices and edges**

Graphs model complex relationships and networks:
- Directed and Undirected graphs
- Weighted and Unweighted graphs
- Adjacency Matrix representation
- Adjacency List representation

**Key Characteristics:**
- Flexible relationship modeling
- Pathfinding algorithms
- Network analysis
- Cyclic and acyclic variants

### 8. [Heaps](08-Heaps/)
**Complete binary trees with ordering properties**

Heaps are specialized trees optimized for priority-based operations:
- Min Heap (parent ≤ children)
- Max Heap (parent ≥ children)
- Binary Heap (array-based)
- `PriorityQueue` uses heap internally

**Key Characteristics:**
- Efficient min/max retrieval
- Priority queue implementation
- O(log n) insertion and deletion
- Heap sort algorithm

---

## Comparison Table

| Data Structure | Ordering | Duplicates | Null Values | Thread-Safe Options | Primary Use Case |
|----------------|----------|------------|-------------|---------------------|------------------|
| **ArrayList** | Insertion order | Yes | Yes | Vector, Collections.synchronizedList() | General-purpose sequential data |
| **LinkedList** | Insertion order | Yes | Yes | Collections.synchronizedList() | Frequent insertions/deletions |
| **HashSet** | No order | No | One null | Collections.synchronizedSet() | Unique element collection |
| **TreeSet** | Sorted | No | No | Collections.synchronizedSortedSet() | Sorted unique elements |
| **HashMap** | No order | Values: Yes<br>Keys: No | One null key | ConcurrentHashMap, Hashtable | Fast key-value lookup |
| **TreeMap** | Key-sorted | Values: Yes<br>Keys: No | No | Collections.synchronizedSortedMap() | Sorted key-value pairs |
| **Queue** | FIFO (typical) | Yes | Implementation-dependent | BlockingQueue implementations | Task scheduling |
| **Stack/Deque** | LIFO | Yes | Yes (ArrayDeque: No) | Collections.synchronizedDeque() | Backtracking, DFS |
| **PriorityQueue** | Priority order | Yes | No | PriorityBlockingQueue | Priority-based processing |
| **Custom Trees** | Hierarchical | Structure-dependent | Implementation-dependent | Custom synchronization | Hierarchical data |
| **Custom Graphs** | Relationship-based | Edge-dependent | Implementation-dependent | Custom synchronization | Network modeling |
| **Custom Heaps** | Heap property | Structure-dependent | Implementation-dependent | Custom synchronization | Priority operations |

---

## Complexity Analysis

### Time Complexity Comparison

| Operation | ArrayList | LinkedList | HashSet | TreeSet | HashMap | TreeMap | Queue (Array) | Stack | Heap |
|-----------|-----------|------------|---------|---------|---------|---------|---------------|-------|------|
| **Access by Index** | O(1) | O(n) | N/A | N/A | N/A | N/A | N/A | N/A | N/A |
| **Search** | O(n) | O(n) | O(1) | O(log n) | O(1) | O(log n) | O(n) | O(n) | O(n) |
| **Insert (end)** | O(1)* | O(1) | O(1) | O(log n) | O(1) | O(log n) | O(1)* | O(1) | O(log n) |
| **Insert (middle)** | O(n) | O(1)** | N/A | O(log n) | N/A | O(log n) | N/A | N/A | N/A |
| **Delete** | O(n) | O(1)** | O(1) | O(log n) | O(1) | O(log n) | O(1) | O(1) | O(log n) |
| **Get Min/Max** | O(n) | O(n) | O(n) | O(1) | O(n) | O(1) | O(n) | O(n) | O(1) |

*Amortized time (occasional resize operation)  
**When iterator/reference is available; O(n) to find position

### Space Complexity

| Data Structure | Space Complexity | Additional Notes |
|----------------|------------------|------------------|
| ArrayList | O(n) | May have unused capacity |
| LinkedList | O(n) | Extra memory for node pointers |
| HashSet | O(n) | Load factor affects memory |
| TreeSet | O(n) | Balanced tree overhead |
| HashMap | O(n) | Load factor, bucket overhead |
| TreeMap | O(n) | Tree structure overhead |
| Queue/Stack | O(n) | Depends on implementation |
| Binary Tree | O(n) | Unbalanced: O(n) height |
| Graph (Adjacency List) | O(V + E) | V=vertices, E=edges |
| Graph (Adjacency Matrix) | O(V²) | Dense graphs |
| Heap | O(n) | Compact array representation |

---

## Decision Tree Guide

### Choosing the Right Data Structure

```
START: What do you need to store?

┌─ Single Values (Collection) ─────────────────────────────┐
│                                                           │
│  Need to maintain order?                                 │
│  ├─ YES → Need fast random access?                       │
│  │        ├─ YES → ArrayList                             │
│  │        └─ NO → LinkedList (frequent insert/delete)    │
│  │                                                        │
│  └─ NO → Need unique elements?                           │
│           ├─ YES → Need sorting?                         │
│           │        ├─ YES → TreeSet                      │
│           │        └─ NO → HashSet                       │
│           └─ NO → See ordered collections above          │
└───────────────────────────────────────────────────────────┘

┌─ Key-Value Pairs (Map) ──────────────────────────────────┐
│                                                           │
│  Need sorting by key?                                    │
│  ├─ YES → TreeMap                                        │
│  │                                                        │
│  └─ NO → Need insertion order?                           │
│           ├─ YES → LinkedHashMap                         │
│           └─ NO → HashMap (fastest)                      │
│                                                           │
│  Thread safety required?                                 │
│  └─ YES → ConcurrentHashMap                              │
└───────────────────────────────────────────────────────────┘

┌─ Processing Order Matters ───────────────────────────────┐
│                                                           │
│  First-in-first-out (FIFO)?                              │
│  ├─ YES → Need priority?                                 │
│  │        ├─ YES → PriorityQueue                         │
│  │        └─ NO → LinkedList or ArrayDeque               │
│  │                                                        │
│  └─ NO → Last-in-first-out (LIFO)?                       │
│           └─ YES → ArrayDeque (as Stack)                 │
└───────────────────────────────────────────────────────────┘

┌─ Hierarchical or Relational Data ────────────────────────┐
│                                                           │
│  Parent-child relationships?                             │
│  ├─ YES → Tree                                           │
│  │        ├─ Need sorting? → Binary Search Tree          │
│  │        ├─ Need balance? → AVL or Red-Black Tree       │
│  │        └─ Prefix search? → Trie                       │
│  │                                                        │
│  └─ NO → Complex relationships/networks?                 │
│           └─ YES → Graph                                 │
│                    ├─ Directed? → Directed Graph         │
│                    └─ Weighted? → Weighted Graph         │
└───────────────────────────────────────────────────────────┘

┌─ Priority-Based Operations ──────────────────────────────┐
│                                                           │
│  Need quick access to min/max?                           │
│  └─ YES → Heap (PriorityQueue)                           │
│           ├─ Smallest first? → Min Heap                  │
│           └─ Largest first? → Max Heap                   │
└───────────────────────────────────────────────────────────┘
```

### Performance Decision Tree

```
What's your priority?

├─ Fast Random Access
│  └─ ArrayList or Array
│
├─ Fast Insert/Delete at ends
│  └─ LinkedList or ArrayDeque
│
├─ Fast Insert/Delete anywhere
│  └─ LinkedList (with iterator)
│
├─ Fast Search
│  ├─ Exact match → HashMap or HashSet
│  └─ Range queries → TreeMap or TreeSet
│
├─ Memory Efficiency
│  └─ ArrayList or Arrays (when size known)
│
├─ Sorted Data
│  ├─ Keys → TreeMap
│  └─ Values → TreeSet
│
└─ Priority Processing
   └─ PriorityQueue (Heap)
```

---

## Visual Hierarchy

```
                    DATA STRUCTURES
                          |
        ┌─────────────────┼─────────────────┐
        |                 |                 |
    LINEAR           HIERARCHICAL       NON-LINEAR
        |                 |                 |
        |                 |                 |
    ┌───┴───┬────────┐    |            ┌────┴────┐
    |       |        |    |            |         |
  Lists   Sets    Maps  Trees       Graphs    Heaps
    |       |        |    |            |         |
    |       |        |    |            |         |
┌───┴───┐   |    ┌───┴──┐ |        ┌───┴───┐     |
|       |   |    |      | |        |       |     |
Array Linked Hash Hash Tree Binary General  Priority
List  List   Set  Map  Map  Trees  Graphs  Operations
              |             |       |
          ┌───┴───┐     ┌───┴───┐  |
          |       |     |       |  |
        Tree  Linked  BST    AVL Directed/
        Set   Hash    Trees  Trees Undirected
              Map                  Weighted/
                                  Unweighted

SPECIAL PURPOSE STRUCTURES:
├─ Queue Family: Queue, Deque, BlockingQueue, PriorityQueue
└─ Stack: Deque (preferred), Legacy Stack class
```

### Java Collections Framework Hierarchy

```
                    Iterable<E>
                        |
                  Collection<E>
                        |
        ┌───────────────┼───────────────┐
        |               |               |
      List<E>         Set<E>         Queue<E>
        |               |               |
    ┌───┴───┐       ┌───┴───┐       ┌───┴───┐
    |       |       |       |       |       |
ArrayList LinkedList HashSet TreeSet Priority ArrayDeque
Vector              LinkedHash     Queue
Stack               Set


                    Map<E,V>
                        |
        ┌───────────────┼───────────────┐
        |               |               |
    HashMap         TreeMap      ConcurrentHashMap
    LinkedHashMap
    Hashtable
```

---

## When to Use Each Type

### Lists
**Use when:**
- You need ordered collection with duplicates
- Random access by index is important
- Iterating through all elements is common
- You need to maintain insertion order

**ArrayList vs LinkedList:**
- **ArrayList**: More reads than writes, random access needed
- **LinkedList**: Frequent insertions/deletions, iterator-based access

**Example scenarios:**
- Shopping cart items
- Player scores list
- Task lists
- History/logs

### Sets
**Use when:**
- Uniqueness is required
- You need to test membership quickly
- Duplicate prevention is critical
- Set operations (union, intersection) needed

**HashSet vs TreeSet:**
- **HashSet**: Fastest operations, no ordering needed
- **TreeSet**: Need sorted elements, range operations

**Example scenarios:**
- Unique user IDs
- Tag systems
- Visited nodes in graph traversal
- Permissions set

### Maps
**Use when:**
- You need key-value associations
- Fast lookup by key is essential
- You want to associate metadata with objects
- Caching or memoization needed

**HashMap vs TreeMap vs LinkedHashMap:**
- **HashMap**: Fastest, no order guarantees
- **TreeMap**: Sorted by keys, range queries
- **LinkedHashMap**: Maintains insertion/access order

**Example scenarios:**
- Configuration settings
- User session data
- Database-like lookups
- Frequency counters

### Queues
**Use when:**
- FIFO processing is needed
- Task scheduling required
- Buffering elements
- Breadth-first search (BFS)

**Example scenarios:**
- Print job queue
- Message brokers
- Request handling
- Level-order tree traversal

### Stacks
**Use when:**
- LIFO processing needed
- Backtracking required
- Undo/redo functionality
- Depth-first search (DFS)

**Example scenarios:**
- Function call stack
- Expression evaluation
- Browser back button
- Parentheses matching

### Trees
**Use when:**
- Hierarchical data representation needed
- Fast search with sorted data
- Range queries required
- Prefix matching needed (Trie)

**Example scenarios:**
- File system structure
- Organization charts
- XML/HTML DOM
- Auto-complete systems

### Graphs
**Use when:**
- Complex relationships between entities
- Network modeling needed
- Pathfinding required
- Dependency resolution

**Example scenarios:**
- Social networks
- Road networks
- Course prerequisites
- Recommendation systems

### Heaps
**Use when:**
- Quick access to min/max element needed
- Priority-based processing required
- Top-K problems
- Median maintenance

**Example scenarios:**
- Task scheduling by priority
- Event simulation
- Dijkstra's algorithm
- K-th largest element problems

---

## Learning Path

### Beginner Level
1. Start with **[Lists](01-Lists/)** - Foundation of sequential data
2. Move to **[Sets](02-Sets/)** - Understand uniqueness constraints
3. Learn **[Maps](03-Maps/)** - Master key-value associations

### Intermediate Level
4. Study **[Queues](04-Queues/)** - FIFO processing patterns
5. Understand **[Stacks](05-Stacks/)** - LIFO and recursion
6. Explore **[Trees](06-Trees/)** - Hierarchical structures

### Advanced Level
7. Master **[Graphs](07-Graphs/)** - Complex relationships
8. Learn **[Heaps](08-Heaps/)** - Priority-based operations

### Practice Strategy
- Implement each structure from scratch once
- Use built-in Java implementations for production
- Solve 10-15 problems per data structure
- Compare performance with different data sizes
- Build projects using multiple structures together

### Resources per Section
Each subdirectory contains:
- Detailed explanations with examples
- Implementation guides
- Common operations and complexity
- Practice problems with solutions
- Interview questions
- Real-world use cases

---

## Summary

Mastering data structures is essential for writing efficient, scalable Java applications. This guide provides a comprehensive overview of eight fundamental categories, each with distinct characteristics and use cases. 

**Key Takeaways:**
1. **No single "best" data structure** - Choice depends on specific requirements
2. **Understand trade-offs** - Time vs space, simplicity vs performance
3. **Use built-in implementations** - Java Collections Framework is battle-tested
4. **Know the complexity** - Big O notation guides performance expectations
5. **Practice deliberately** - Implement and use each structure in real problems

Navigate to each subdirectory to dive deep into specific data structures, complete with implementations, examples, and practice problems. Start with Lists and progressively build your understanding through hands-on coding and problem-solving.

---

**Next Steps:**
- Begin with [Lists](01-Lists/) if you're new to data structures
- Review the [Collections Framework Overview](../01-Collections-Framework/) for Java-specific implementations
- Practice with [DSA Patterns](../04-DSA-Patterns/) to see data structures in action
- Test your knowledge with [Interview Questions](../../06-Practice-and-Interview-Prep/04-Interview-Questions/)

Happy Learning! 🚀
