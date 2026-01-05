# Vector in Java

## Overview

`Vector` is a synchronized, dynamic array implementation that's part of the legacy Java Collections Framework. It was one of the original collection classes in Java (since Java 1.0) and implements both the `List` and `Collection` interfaces. Vector is similar to ArrayList in structure but includes built-in thread synchronization, making it thread-safe. However, Vector is considered legacy and is generally not recommended for new code—ArrayList with external synchronization or concurrent collections are preferred.

## Historical Context

Vector was the dynamic array implementation before ArrayList was introduced in Java 1.2 as part of the Collections Framework. Key historical points:

- **Java 1.0**: Vector introduced as the only dynamic array option
- **Java 1.2**: ArrayList introduced as a faster, non-synchronized alternative
- **Today**: Vector remains for backward compatibility but is rarely recommended for new projects

## How It Works Internally

Vector operates very similarly to ArrayList with a key difference: **every public method is synchronized**, making it thread-safe. This synchronization adds overhead even when thread-safety isn't needed.

### Internal Structure
```
Vector uses an Object array like ArrayList:
[E1, E2, E3, null, null, null, null, null, null, null]

Growth mechanism (different from ArrayList):
- Default capacity: 10
- Growth factor: 2x (new capacity = old capacity * 2)
- This 2x growth is less memory-efficient than ArrayList's 1.5x
```

## Constructors and Creation Methods

```java
// Default constructor - creates Vector with default capacity (10)
Vector<String> vec1 = new Vector<>();

// Constructor with initial capacity
Vector<String> vec2 = new Vector<>(20);

// Constructor with capacity and growth increment
Vector<String> vec3 = new Vector<>(10, 5);
// Capacity starts at 10, grows by 5 each time

// Constructor from collection
List<String> source = Arrays.asList("A", "B", "C");
Vector<String> vec4 = new Vector<>(source);
```

## Core Operations

All operations are synchronized. The synchronization is method-level, not fine-grained.

### Add Operations
```java
Vector<String> fruits = new Vector<>();

// add(E e) - synchronized, adds to end, O(1) amortized
fruits.add("Apple");      // [Apple]
fruits.add("Banana");     // [Apple, Banana]

// add(int index, E e) - synchronized, inserts at index, O(n)
fruits.add(1, "Cherry");  // [Apple, Cherry, Banana]

// addElement(E e) - legacy method, synchronized, same as add(), O(1) amortized
fruits.addElement("Date");  // [Apple, Cherry, Banana, Date]

// addAll(Collection) - synchronized
List<String> more = Arrays.asList("Elderberry", "Fig");
fruits.addAll(more);
// [Apple, Cherry, Banana, Date, Elderberry, Fig]
```

### Remove Operations
```java
Vector<Integer> numbers = new Vector<>(Arrays.asList(
    10, 20, 30, 40, 50
));

// remove(int index) - synchronized, removes at index, O(n)
int removed = numbers.remove(2);  // removes 30
System.out.println(removed);  // 30
// [10, 20, 40, 50]

// remove(Object o) - synchronized, removes first occurrence, O(n)
numbers.remove(Integer.valueOf(20));
// [10, 40, 50]

// removeElement(Object o) - legacy method, same as remove(Object), O(n)
numbers.removeElement(50);
// [10, 40]

// removeAllElements() - legacy method, clears vector, O(n)
numbers.removeAllElements();
// []

// clear() - synchronized, removes all elements, O(n)
numbers = new Vector<>(Arrays.asList(10, 20, 30));
numbers.clear();
// []

// removeIf() - synchronized (since Java 8)
numbers = new Vector<>(Arrays.asList(10, 20, 30, 40, 50));
numbers.removeIf(n -> n > 25);
// [10, 20]
```

### Get Operations
```java
Vector<String> colors = new Vector<>(Arrays.asList(
    "Red", "Green", "Blue", "Yellow"
));

// get(int index) - synchronized, retrieves element, O(1)
String color = colors.get(2);  // Blue

// elementAt(int index) - legacy method, synchronized, same as get(), O(1)
String colorLegacy = colors.elementAt(1);  // Green

// firstElement() - legacy method, synchronized, gets first, O(1)
String first = colors.firstElement();  // Red

// lastElement() - legacy method, synchronized, gets last, O(1)
String last = colors.lastElement();  // Yellow

// peek() - not available in Vector (not a Queue)

// elements() - legacy enumeration method
Enumeration<String> enum_ = colors.elements();
while (enum_.hasMoreElements()) {
    System.out.println(enum_.nextElement());
}
```

### Set Operations
```java
Vector<String> items = new Vector<>(Arrays.asList(
    "Item1", "Item2", "Item3"
));

// set(int index, E element) - synchronized, replaces element, O(1)
items.set(1, "UpdatedItem");
// [Item1, UpdatedItem, Item3]

// setElementAt(E obj, int index) - legacy method, synchronized
items.setElementAt("NewItem", 0);
// [NewItem, UpdatedItem, Item3]

// replaceAll() - synchronized (since Java 8)
items.replaceAll(String::toUpperCase);
// [NEWITEM, UPDATEDITEM, ITEM3]
```

### Query Operations
```java
Vector<String> fruits = new Vector<>(Arrays.asList(
    "Apple", "Banana", "Apple", "Cherry"
));

// size() - synchronized, returns element count, O(1)
int size = fruits.size();  // 4

// isEmpty() - synchronized, checks if empty, O(1)
boolean empty = fruits.isEmpty();  // false

// contains(Object o) - synchronized, checks existence, O(n)
boolean hasApple = fruits.contains("Apple");  // true

// indexOf(Object o) - synchronized, first occurrence, O(n)
int index = fruits.indexOf("Apple");  // 0

// lastIndexOf(Object o) - synchronized, last occurrence, O(n)
int lastIndex = fruits.lastIndexOf("Apple");  // 2

// capacity() - synchronized, returns current capacity, O(1)
int capacity = fruits.capacity();

// ensureCapacity(int) - synchronized, pre-allocate space, O(1) or O(n)
fruits.ensureCapacity(100);  // Ensure room for 100 elements

// trimToSize() - synchronized, reduce unused capacity, O(n)
fruits.trimToSize();  // Shrink to fit current size
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity | Synchronized |
|-----------|-----------------|------------------|--------------|
| add(E)    | O(1) amortized  | O(n) worst case  | Yes          |
| add(i, E) | O(n)            | O(n) worst case  | Yes          |
| remove(i) | O(n)            | O(1)             | Yes          |
| get(i)    | O(1)            | O(1)             | Yes          |
| set(i, E) | O(1)            | O(1)             | Yes          |
| contains()| O(n)            | O(1)             | Yes          |
| size()    | O(1)            | O(1)             | Yes          |

**Note**: The synchronization overhead applies to every operation, even in single-threaded scenarios.

## Memory Layout and Growth Strategy

Vector uses a 2x growth factor, which differs from ArrayList's 1.5x:

```
Growth Example (2x strategy):
Initial:     capacity = 10
After 11:    capacity = 20 (10 * 2)
After 21:    capacity = 40 (20 * 2)
After 41:    capacity = 80 (40 * 2)
After 81:    capacity = 160 (80 * 2)

Memory usage escalates faster than ArrayList:
- ArrayList: ~1.5x growth = less wasted space
- Vector: 2x growth = more wasted space

For 1,000,000 elements:
- ArrayList final capacity: ~1,500,000 (50% waste)
- Vector final capacity: ~2,097,152 (100% waste)
```

## Advantages

1. **Thread-safe by default**: No external synchronization needed
2. **Backward compatible**: Legacy code continues to work
3. **Familiar API**: Similar to ArrayList for quick migration
4. **Built-in synchronization**: Convenient for multi-threaded simple cases
5. **Legacy method variants**: Original methods like `addElement()`, `removeElement()`
6. **Capacity control**: Can pre-allocate with `ensureCapacity()`

## Disadvantages

1. **Synchronization overhead**: Even in single-threaded code, all methods are synchronized
2. **Coarse-grained locking**: Method-level synchronization can limit concurrency
3. **Inefficient growth**: 2x growth wastes more memory than ArrayList's 1.5x
4. **Performance penalty**: Slower than ArrayList due to synchronization checks
5. **Not recommended**: Modern code prefers ArrayList with external synchronization or concurrent collections
6. **Legacy status**: Part of the older Java API, not actively enhanced
7. **False sense of security**: Synchronization is method-level, not at the collection level for complex operations

## When to Use Vector

Use Vector when:
- Working with legacy Java code that already uses Vector
- You need thread-safe list with simple, method-level synchronization
- You require backward compatibility with older Java versions
- Performance isn't critical and you need built-in synchronization
- You prefer synchronized collections over external synchronization

**Modern alternative**: Use `Collections.synchronizedList(new ArrayList<>())` or `CopyOnWriteArrayList` instead.

## Common Use Cases

1. **Legacy codebases**: Existing systems using Vector throughout
2. **Simple multi-threaded scenarios**: When method-level synchronization suffices
3. **Backward compatibility**: Maintaining code that depends on Vector
4. **Educational purposes**: Learning about original Java collections

## Practical Code Examples

```java
import java.util.*;
import java.util.stream.*;

public class VectorExamples {
    
    // Example 1: Basic Vector operations
    public static void basicOperations() {
        Vector<Integer> vector = new Vector<>();
        
        // Adding elements
        for (int i = 1; i <= 5; i++) {
            vector.add(i * 10);
        }
        System.out.println("Vector: " + vector);
        // [10, 20, 30, 40, 50]
        
        System.out.println("Size: " + vector.size());
        System.out.println("Capacity: " + vector.capacity());
        
        // Legacy method
        vector.addElement(60);
        System.out.println("After addElement: " + vector);
        // [10, 20, 30, 40, 50, 60]
        
        // Get element
        System.out.println("Element at 2: " + vector.get(2));  // 30
        System.out.println("First: " + vector.firstElement()); // 10
        System.out.println("Last: " + vector.lastElement());   // 60
    }
    
    // Example 2: Vector capacity management
    public static void capacityManagement() {
        Vector<String> vector = new Vector<>(3);  // initial capacity = 3
        
        System.out.println("Initial capacity: " + vector.capacity());
        
        // Add elements to trigger growth
        String[] items = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < items.length; i++) {
            vector.add(items[i]);
            System.out.println("After adding " + items[i] + 
                " - size: " + vector.size() + 
                ", capacity: " + vector.capacity());
        }
        
        // Demonstrate trimToSize
        System.out.println("\nBefore trim - capacity: " + vector.capacity());
        vector.trimToSize();
        System.out.println("After trim - capacity: " + vector.capacity());
    }
    
    // Example 3: Thread safety demonstration
    public static void threadSafetyDemo() throws InterruptedException {
        Vector<Integer> vector = new Vector<>();
        
        // Thread 1: Adding elements
        Thread addThread = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                vector.add(i);
            }
        });
        
        // Thread 2: Removing elements
        Thread removeThread = new Thread(() -> {
            try {
                Thread.sleep(10);  // Let some elements be added first
                while (!vector.isEmpty()) {
                    vector.remove(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // Thread 3: Reading elements
        Thread readThread = new Thread(() -> {
            try {
                Thread.sleep(20);
                for (int i = 0; i < 50 && !vector.isEmpty(); i++) {
                    int index = i % vector.size();
                    try {
                        int val = vector.get(index);
                        System.out.println("Read: " + val);
                    } catch (IndexOutOfBoundsException e) {
                        // Safe - element removed by another thread
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        addThread.start();
        removeThread.start();
        readThread.start();
        
        addThread.join();
        removeThread.join();
        readThread.join();
        
        System.out.println("Final vector size: " + vector.size());
    }
    
    // Example 4: Enumeration (legacy iteration)
    public static void enumerationIteration() {
        Vector<String> colors = new Vector<>(Arrays.asList(
            "Red", "Green", "Blue", "Yellow", "Purple"
        ));
        
        System.out.println("Using Enumeration (legacy):");
        Enumeration<String> enumeration = colors.elements();
        while (enumeration.hasMoreElements()) {
            System.out.print(enumeration.nextElement() + " ");
        }
        System.out.println();
        
        System.out.println("Using Iterator (modern):");
        Iterator<String> iterator = colors.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        
        System.out.println("Using foreach (modern):");
        for (String color : colors) {
            System.out.print(color + " ");
        }
        System.out.println();
    }
    
    // Example 5: Removal operations
    public static void removalOperations() {
        Vector<Integer> numbers = new Vector<>(Arrays.asList(
            10, 20, 30, 40, 50, 60, 70, 80, 90
        ));
        
        System.out.println("Original: " + numbers);
        
        // Remove by index
        numbers.remove(4);  // removes 50
        System.out.println("After remove(4): " + numbers);
        
        // Remove by value
        numbers.remove(Integer.valueOf(30));
        System.out.println("After remove(30): " + numbers);
        
        // Remove with condition
        Vector<Integer> nums = new Vector<>(Arrays.asList(
            10, 20, 30, 40, 50, 60, 70, 80, 90
        ));
        nums.removeIf(n -> n > 50);
        System.out.println("After removeIf(>50): " + nums);
        
        // Clear all
        Vector<String> temp = new Vector<>(Arrays.asList("A", "B", "C"));
        temp.clear();
        System.out.println("After clear: " + temp);
    }
    
    // Example 6: Searching operations
    public static void searchOperations() {
        Vector<String> fruits = new Vector<>(Arrays.asList(
            "Apple", "Banana", "Cherry", "Date", "Apple", "Elderberry"
        ));
        
        System.out.println("Vector: " + fruits);
        
        // Find first occurrence
        int firstIndex = fruits.indexOf("Apple");
        System.out.println("First 'Apple' at index: " + firstIndex);  // 0
        
        // Find last occurrence
        int lastIndex = fruits.lastIndexOf("Apple");
        System.out.println("Last 'Apple' at index: " + lastIndex);    // 4
        
        // Check contains
        System.out.println("Contains 'Cherry': " + fruits.contains("Cherry"));  // true
        System.out.println("Contains 'Fig': " + fruits.contains("Fig"));        // false
    }
    
    // Example 7: Sorting Vector
    public static void sortingOperations() {
        Vector<String> words = new Vector<>(Arrays.asList(
            "elephant", "ant", "bear", "dog", "cat"
        ));
        
        System.out.println("Original: " + words);
        
        // Sort ascending
        Collections.sort(words);
        System.out.println("Sorted ascending: " + words);
        
        // Sort descending
        Collections.sort(words, Collections.reverseOrder());
        System.out.println("Sorted descending: " + words);
        
        // Custom comparator
        Collections.sort(words, (a, b) -> Integer.compare(a.length(), b.length()));
        System.out.println("Sorted by length: " + words);
    }
    
    // Example 8: Vector vs ArrayList performance
    public static void performanceComparison() {
        int size = 10000;
        int iterations = 100000;
        
        // ArrayList performance - add at end
        ArrayList<Integer> arrayList = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }
        long arrayAddTime = System.nanoTime() - start;
        
        // Vector performance - add at end
        Vector<Integer> vector = new Vector<>();
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            vector.add(i);
        }
        long vectorAddTime = System.nanoTime() - start;
        
        System.out.println("ArrayList add 10000 elements: " + arrayAddTime + " ns");
        System.out.println("Vector add 10000 elements: " + vectorAddTime + " ns");
        System.out.println("Vector is slower by: " + 
            String.format("%.2f", (double) vectorAddTime / arrayAddTime) + "x");
        
        // ArrayList read performance
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            arrayList.get(size / 2);
        }
        long arrayReadTime = System.nanoTime() - start;
        
        // Vector read performance
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            vector.get(size / 2);
        }
        long vectorReadTime = System.nanoTime() - start;
        
        System.out.println("\nArrayList 100000 reads: " + arrayReadTime + " ns");
        System.out.println("Vector 100000 reads: " + vectorReadTime + " ns");
        System.out.println("Vector is slower by: " + 
            String.format("%.2f", (double) vectorReadTime / arrayReadTime) + "x");
    }
    
    // Example 9: Stream operations
    public static void streamOperations() {
        Vector<String> names = new Vector<>(Arrays.asList(
            "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"
        ));
        
        // Filter and collect
        Vector<String> filtered = names.stream()
            .filter(name -> name.length() > 3)
            .collect(Collectors.toCollection(Vector::new));
        System.out.println("Filtered (length > 3): " + filtered);
        
        // Map and collect
        Vector<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toCollection(Vector::new));
        System.out.println("Name lengths: " + lengths);
    }
    
    // Example 10: Comparison with Collections.synchronizedList
    public static void synchronizedListComparison() {
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
        Vector<Integer> vector = new Vector<>();
        
        // Both are thread-safe
        System.out.println("Both Collections.synchronizedList and Vector are thread-safe");
        System.out.println("Advantages of synchronizedList:");
        System.out.println("- Better performance for unsynchronized operations");
        System.out.println("- Uses ArrayList's 1.5x growth (more efficient)");
        System.out.println("- More fine-grained lock control possible");
        System.out.println("\nVector advantages:");
        System.out.println("- Legacy code compatibility");
        System.out.println("- Familiar API for older Java developers");
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Basic Operations ===");
        basicOperations();
        
        System.out.println("\n=== Capacity Management ===");
        capacityManagement();
        
        System.out.println("\n=== Thread Safety Demo ===");
        threadSafetyDemo();
        
        System.out.println("\n=== Enumeration Iteration ===");
        enumerationIteration();
        
        System.out.println("\n=== Removal Operations ===");
        removalOperations();
        
        System.out.println("\n=== Search Operations ===");
        searchOperations();
        
        System.out.println("\n=== Sorting Operations ===");
        sortingOperations();
        
        System.out.println("\n=== Performance Comparison ===");
        performanceComparison();
        
        System.out.println("\n=== Stream Operations ===");
        streamOperations();
        
        System.out.println("\n=== Synchronized List Comparison ===");
        synchronizedListComparison();
    }
}
```

## Advanced Operations

### Pre-allocation Strategy
```java
Vector<String> vector = new Vector<>(5, 10);
// Initial capacity: 5
// Growth increment: 10 (when full, capacity becomes 15, then 25, etc.)

vector.ensureCapacity(1000);  // Pre-allocate to 1000
```

### Legacy Methods Still Available
```java
Vector<String> vector = new Vector<>();

// These legacy methods still work
vector.addElement("A");
vector.insertElementAt("B", 0);
vector.removeElementAt(1);
vector.setElementAt("C", 0);
String first = vector.firstElement();
String last = vector.lastElement();
Enumeration<String> e = vector.elements();
```

## Thread-Safety Considerations

While Vector is synchronized, remember:

1. **Method-level synchronization**: Only individual method calls are atomic
2. **Iteration not atomic**: If collection is modified during iteration, `ConcurrentModificationException` may occur
3. **Compound operations unsafe**: Multiple operations aren't atomic as a group

```java
// NOT SAFE - even with Vector
if (vector.contains("A")) {
    vector.remove("A");  // Another thread could modify between check and remove
}

// BETTER - even for Vector
synchronized(vector) {
    if (vector.contains("A")) {
        vector.remove("A");
    }
}

// BEST for new code - use concurrent collections
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
```

## Comparison with Other List Implementations

| Feature | Vector | ArrayList | LinkedList |
|---------|--------|-----------|-----------|
| Random Access | O(1) | O(1) | O(n) |
| Insert/Delete (start) | O(n) | O(n) | O(1) |
| Insert/Delete (end) | O(1)* | O(1)* | O(1) |
| Thread-Safe | Yes | No | No |
| Synchronization | Coarse | None | None |
| Growth Factor | 2x | 1.5x | N/A |
| Memory Overhead | Low | Low | High |
| Performance | Good | Excellent | Slower |
| Modern Use | Legacy | Preferred | Specialized |

## Migration Guide (from Vector to ArrayList)

```java
// Old code
Vector<String> old = new Vector<>();

// Option 1: Simple replacement (unsafe multi-threaded)
List<String> simple = new ArrayList<>(old);

// Option 2: Thread-safe alternative (recommended)
List<String> safe = Collections.synchronizedList(new ArrayList<>(old));

// Option 3: Modern concurrent alternative (best for high concurrency)
List<String> concurrent = new CopyOnWriteArrayList<>(old);
```

## Summary

Vector is a legacy Java class that provided the first dynamic array implementation. While it offers built-in thread-safety, this comes at a performance cost even for single-threaded applications. Modern Java development strongly recommends using ArrayList with external synchronization when needed, or preferring concurrent collections like `CopyOnWriteArrayList` or `ConcurrentHashMap` for multi-threaded scenarios. Vector remains useful primarily for maintaining backward compatibility with legacy codebases, but should be avoided in new projects.

