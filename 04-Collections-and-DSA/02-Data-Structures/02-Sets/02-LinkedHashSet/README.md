# LinkedHashSet in Java

## Simple Explanation with Real-World Analogy

Imagine a **library checkout system** that not only tracks which books are checked out (like HashSet) but also **remembers the order** in which patrons checked them out. When you ask for a list of all checked-out books, they're presented in the **order they were borrowed**, not randomly.

**LinkedHashSet** combines the **speed of HashSet** with the **order preservation of insertion order**. It's a HashSet that maintains a **doubly-linked list** running through all entries, ensuring that iteration occurs in the order elements were inserted. This makes it perfect when you need fast operations AND predictable ordering.

## Internal Structure: How LinkedHashSet Works

```
┌──────────────────────────────────────────────────┐
│        LinkedHashSet Structure (Doubly-Linked)   │
├──────────────────────────────────────────────────┤
│  Hash Table (for fast lookup):                   │
│   [0] → Element A (entry node)                   │
│   [1] → null                                     │
│   [2] → Element C (entry node)                   │
│   [3] → Element B (entry node)                   │
│                                                  │
│  Linked List (maintains insertion order):        │
│   Head ↔ Element A ↔ Element B ↔ Element C ↔ Tail│
│         (1st added) (3rd added) (2nd added)      │
└──────────────────────────────────────────────────┘

Each element is stored in an Entry node containing:
- hashCode & value (for hash table)
- before & after pointers (for linked list)
```

**LinkedHashSet** extends HashSet but uses a different type of entry node. Here's how it works:

1. **Hash Table**: Like HashSet, uses hash codes for O(1) lookups
2. **Doubly-Linked List**: Each entry has `before` and `after` pointers
3. **Sentinel Nodes**: Maintains a circular doubly-linked list with head/tail sentinels
4. **Iteration**: Traverses the linked list instead of the hash table
5. **Load Factor**: Same resizing behavior as HashSet (0.75 default)

## Access Modes: Insertion Order vs Access Order

```java
public class LinkedHashSetModes {
    public static void main(String[] args) {
        // Default: Insertion Order (most common)
        insertionOrderExample();
        
        // Access Order: Least Recently Used tracking
        accessOrderExample();
    }
    
    // 1. Insertion Order (LinkedHashSet)
    static void insertionOrderExample() {
        System.out.println("=== Insertion Order Mode ===");
        LinkedHashSet<String> tasks = new LinkedHashSet<>();
        
        // Add tasks in specific order
        tasks.add("Task 1");
        tasks.add("Task 2");
        tasks.add("Task 3");
        tasks.add("Task 1"); // Duplicate - not added
        tasks.add("Task 4");
        
        System.out.println("Iteration order (same as insertion):");
        for (String task : tasks) {
            System.out.println("  " + task);
        }
        // Output: Task 1, Task 2, Task 3, Task 4
    }
    
    // 2. Access Order with LinkedHashMap
    static void accessOrderExample() {
        System.out.println("\n=== Access Order Mode (LRU Cache) ===");
        
        // LinkedHashMap with accessOrder=true
        Map<String, Integer> lruCache = new LinkedHashMap<String, Integer>(16, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 3; // Keep only 3 most recently used
            }
        };
        
        lruCache.put("A", 1);
        lruCache.put("B", 2);
        lruCache.put("C", 3);
        System.out.println("Initial: " + lruCache.keySet()); // [A, B, C]
        
        lruCache.get("A"); // Access A (moves to end)
        System.out.println("After accessing A: " + lruCache.keySet()); // [B, C, A]
        
        lruCache.put("D", 4); // Add new, removes oldest (B)
        System.out.println("After adding D: " + lruCache.keySet()); // [C, A, D]
    }
}
```

## Constructors and Creation Methods

```java
// 1. Default constructor - maintains insertion order
LinkedHashSet<String> set1 = new LinkedHashSet<>();

// 2. Constructor with initial capacity
LinkedHashSet<Integer> set2 = new LinkedHashSet<>(32);

// 3. Constructor with capacity and load factor
LinkedHashSet<Double> set3 = new LinkedHashSet<>(64, 0.8f);

// 4. Constructor with collection (maintains order from collection)
List<String> list = Arrays.asList("first", "second", "third");
LinkedHashSet<String> set4 = new LinkedHashSet<>(list);

// 5. Copy from another set (maintains source's order)
HashSet<String> hashSet = new HashSet<>(Arrays.asList("A", "B", "C"));
LinkedHashSet<String> set5 = new LinkedHashSet<>(hashSet);

// 6. Pre-initialized (capacity recommendation: size/0.75)
LinkedHashSet<String> set6 = new LinkedHashSet<>(100);
```

## Core Operations

### Add Operation with Order Tracking
```java
LinkedHashSet<String> colors = new LinkedHashSet<>();

// Add in specific order
colors.add("red");     // 1st
colors.add("green");   // 2nd
colors.add("blue");    // 3rd
colors.add("red");     // Duplicate - not added

boolean added = colors.add("yellow"); // true
System.out.println("Added yellow: " + added);
System.out.println("Colors (insertion order): " + colors);
// Output: [red, green, blue, yellow]

// Add multiple maintaining order
List<String> more = Arrays.asList("orange", "purple");
colors.addAll(more);
System.out.println("After addAll: " + colors);
// Output: [red, green, blue, yellow, orange, purple]
```

### Remove Operation (Preserves Remaining Order)
```java
LinkedHashSet<String> animals = new LinkedHashSet<>(
    Arrays.asList("cat", "dog", "elephant", "fox", "giraffe")
);

System.out.println("Original: " + animals);
// Output: [cat, dog, elephant, fox, giraffe]

// Remove middle element
animals.remove("elephant");
System.out.println("After removing elephant: " + animals);
// Output: [cat, dog, fox, giraffe]

// Remove multiple
animals.removeAll(Arrays.asList("dog", "giraffe"));
System.out.println("After removing dog & giraffe: " + animals);
// Output: [cat, fox]

// Remove with condition
animals.add("dog");
animals.add("cat");
animals.removeIf(animal -> animal.length() < 4);
System.out.println("After removeIf (length < 4): " + animals);
// Output: [elephant] - if we kept it

// Clear maintains no order, just empties
animals.clear();
System.out.println("After clear: " + animals); // Output: []
```

### Contains and Size Operations
```java
LinkedHashSet<String> playlist = new LinkedHashSet<>(
    Arrays.asList("Song1", "Song2", "Song3", "Song4")
);

// Contains check - O(1) average
boolean hasSong2 = playlist.contains("Song2");
System.out.println("Has Song2: " + hasSong2); // true

// Size - O(1)
System.out.println("Playlist size: " + playlist.size()); // 4

// Is empty - O(1)
System.out.println("Is empty: " + playlist.isEmpty()); // false

// Check multiple elements
boolean hasAll = playlist.containsAll(
    Arrays.asList("Song1", "Song3")
);
System.out.println("Has Song1 and Song3: " + hasAll); // true
```

## Iteration Behavior and Ordering

```java
LinkedHashSet<Integer> ordered = new LinkedHashSet<>(
    Arrays.asList(5, 1, 3, 2, 4)
);

// 1. Enhanced for loop - Guaranteed insertion order
System.out.println("=== Enhanced For Loop ===");
for (Integer num : ordered) {
    System.out.println(num);
}
// Output: 5, 1, 3, 2, 4 (exact insertion order guaranteed)

// 2. Iterator - Guaranteed insertion order
System.out.println("\n=== Iterator ===");
Iterator<Integer> iter = ordered.iterator();
while (iter.hasNext()) {
    System.out.print(iter.next() + " ");
}
System.out.println();
// Output: 5 1 3 2 4

// 3. Reverse iteration
System.out.println("\n=== Reverse Iteration (Manual) ===");
List<Integer> list = new ArrayList<>(ordered);
Collections.reverse(list);
list.forEach(num -> System.out.print(num + " "));
// Output: 4 2 3 1 5

// 4. Stream operations preserve order
System.out.println("\n=== Stream API ===");
ordered.stream()
    .filter(n -> n > 2)
    .forEach(n -> System.out.print(n + " "));
System.out.println();
// Output: 5 3 4

// 5. forEach preserves order
System.out.println("\n=== ForEach ===");
ordered.forEach(n -> System.out.print(n + " "));
// Output: 5 1 3 2 4
```

## Performance Characteristics

```java
public class LinkedHashSetPerformance {
    public static void main(String[] args) {
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        
        // Add 1,000,000 elements
        long startAdd = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            set.add(i);
        }
        long endAdd = System.nanoTime();
        double addTime = (endAdd - startAdd) / 1_000_000.0; // ms
        System.out.printf("Add 1M elements: %.2f ms\n", addTime);
        
        // Contains search (still O(1) average)
        long startSearch = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            set.contains(i);
        }
        long endSearch = System.nanoTime();
        double searchTime = (endSearch - startSearch) / 1_000_000.0;
        System.out.printf("Search 100K elements: %.2f ms\n", searchTime);
        
        // Iteration (must traverse linked list)
        long startIter = System.nanoTime();
        set.forEach(i -> {}); // Traverse all
        long endIter = System.nanoTime();
        double iterTime = (endIter - startIter) / 1_000_000.0;
        System.out.printf("Iterate 1M elements: %.2f ms\n", iterTime);
        
        // Remove
        long startRemove = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            set.remove(i);
        }
        long endRemove = System.nanoTime();
        double removeTime = (endRemove - startRemove) / 1_000_000.0;
        System.out.printf("Remove 100K elements: %.2f ms\n", removeTime);
    }
}

// Typical Output:
// Add 1M elements: 250-350 ms (slightly slower than HashSet due to linked list overhead)
// Search 100K elements: 8-12 ms
// Iterate 1M elements: 150-250 ms
// Remove 100K elements: 30-80 ms
```

| Operation | Time Complexity | LinkedList Overhead |
|-----------|---|---|
| Add | O(1) average | Small - just pointer updates |
| Remove | O(1) average | Small - just pointer updates |
| Contains | O(1) average | None - direct hash lookup |
| Iteration | O(n) | Traverses linked list (faster than HashSet for iteration) |
| Clear | O(n) | Need to update all pointers |

## Real-World Code Examples

```java
public class LinkedHashSetRealWorld {
    public static void main(String[] args) {
        // Example 1: LRU Cache Implementation
        lruCacheExample();
        
        // Example 2: Recent Search History
        searchHistoryExample();
        
        // Example 3: Task Queue with Uniqueness
        taskQueueExample();
        
        // Example 4: Processing Order
        processingOrderExample();
    }
    
    // Example 1: Simple LRU Cache
    static void lruCacheExample() {
        System.out.println("=== LRU Cache ===");
        LinkedHashSet<String> recentFiles = new LinkedHashSet<>();
        final int MAX_SIZE = 5;
        
        String[] files = {"file1.txt", "file2.doc", "file3.pdf", 
                         "file1.txt", "file4.xls", "file5.zip", "file2.doc"};
        
        for (String file : files) {
            if (recentFiles.contains(file)) {
                recentFiles.remove(file); // Move to end
            }
            recentFiles.add(file);
            
            if (recentFiles.size() > MAX_SIZE) {
                String oldest = recentFiles.iterator().next();
                recentFiles.remove(oldest);
            }
            System.out.println("Current: " + recentFiles);
        }
    }
    
    // Example 2: Recent Search History
    static void searchHistoryExample() {
        System.out.println("\n=== Search History ===");
        LinkedHashSet<String> searchHistory = new LinkedHashSet<>();
        
        String[] searches = {"Java HashMap", "Java Set", "Java HashMap", 
                            "Collections", "Java Set", "Algorithms"};
        
        for (String search : searches) {
            searchHistory.remove(search); // Remove if exists
            searchHistory.add(search);    // Add at end
        }
        
        System.out.println("Search history (most recent last):");
        int count = 1;
        for (String search : searchHistory) {
            System.out.println(count + ". " + search);
            count++;
        }
    }
    
    // Example 3: Task queue with unique tasks
    static void taskQueueExample() {
        System.out.println("\n=== Task Queue ===");
        LinkedHashSet<String> taskQueue = new LinkedHashSet<>();
        
        // Add tasks
        taskQueue.add("ProcessPayment");
        taskQueue.add("SendEmail");
        taskQueue.add("UpdateDB");
        taskQueue.add("SendEmail"); // Duplicate - will be ignored
        
        System.out.println("Tasks in order:");
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.iterator().next();
            System.out.println("Processing: " + task);
            taskQueue.remove(task);
        }
    }
    
    // Example 4: Processing order maintenance
    static void processingOrderExample() {
        System.out.println("\n=== Processing Order ===");
        LinkedHashSet<Integer> requests = new LinkedHashSet<>();
        
        // Simulate requests arriving
        for (int i = 1; i <= 5; i++) {
            requests.add(i * 100);
        }
        
        System.out.println("Process requests in arrival order:");
        for (Integer reqId : requests) {
            System.out.println("Processing request: " + reqId);
        }
    }
}
```

## Null Handling and Thread Safety

```java
public class NullAndThreadSafety {
    public static void main(String[] args) {
        // Null Handling
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(null);
        set.add("value1");
        set.add("value2");
        
        System.out.println("Contains null: " + set.contains(null)); // true
        System.out.println("Order preserved: " + set);
        // Output: [null, value1, value2] - null is first
        
        // Remove null
        set.remove(null);
        System.out.println("After removing null: " + set);
        // Output: [value1, value2]
        
        // Thread Safety - LinkedHashSet is NOT synchronized
        Set<String> syncSet = Collections.synchronizedSet(
            new LinkedHashSet<>()
        );
        
        // Multi-threaded access
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                syncSet.add("T1-" + i);
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                syncSet.add("T2-" + i);
            }
        });
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Thread-safe set: " + syncSet);
    }
}
```

## When to Use LinkedHashSet

✅ **Use LinkedHashSet when:**
- You need **O(1) operations** AND **insertion order preservation**
- You need to **maintain a queue or history** of unique items
- You're implementing an **LRU (Least Recently Used) cache**
- You need **predictable iteration order** for debugging/logging
- You're working with **recent items** that need ordering

❌ **Don't use LinkedHashSet when:**
- You don't care about **order** (use HashSet - faster)
- You need **sorted order** (use TreeSet instead)
- Memory is extremely **limited** (extra pointers overhead)
- You need **concurrent access** without synchronization

## Memory Usage

```java
// LinkedHashSet uses approximately 60 bytes per entry
// (compared to HashSet's 48 bytes)
// Extra 12 bytes per entry for doubly-linked list pointers

LinkedHashSet<String> set = new LinkedHashSet<>(10000);
System.out.println("Approx memory: " + (10000 * 60 / 1024) + "KB");
```

## Comparison with Other Set Implementations

| Feature | HashSet | LinkedHashSet | TreeSet | EnumSet |
|---------|---------|---------------|---------|---------|
| Order | Unordered | Insertion | Sorted | Natural |
| Add | O(1) avg | O(1) avg | O(log n) | O(1) |
| Remove | O(1) avg | O(1) avg | O(log n) | O(1) |
| Contains | O(1) avg | O(1) avg | O(log n) | O(1) |
| Iteration | Random | Insertion order | Sorted | Natural |
| Space | 48 bytes/entry | 60 bytes/entry | More | Minimal |

## Key Takeaways

- **LinkedHashSet** = **HashSet + Order Preservation**
- Uses a **doubly-linked list** to maintain insertion order
- All operations remain **O(1) average time** like HashSet
- Iteration is **fast and predictable** due to linked list
- Perfect for **LRU caches**, **history tracking**, and **queue implementations**
- Slightly higher memory overhead but worth it for ordered unique collections
- Not thread-safe - wrap with `Collections.synchronizedSet()` if needed
