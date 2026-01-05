# ArrayList in Java

## Overview

`ArrayList` is a resizable array implementation of the `List` interface in Java's Collections Framework. It's part of the `java.util` package and provides dynamic array functionality, automatically growing in size as elements are added. Unlike traditional arrays with fixed size, `ArrayList` adjusts its capacity automatically, making it one of the most commonly used collection classes in Java.

## How It Works Internally

ArrayList is backed by a dynamically-sized array. When you create an ArrayList, it initializes with a default capacity of 10 elements. As you add elements, when the capacity is exceeded, the ArrayList automatically creates a new larger array (typically 50% larger using the formula: `newCapacity = oldCapacity + (oldCapacity >> 1)`) and copies all existing elements to this new array.

### Internal Structure
```
Initial State:
[null, null, null, null, null, null, null, null, null, null]  // capacity = 10, size = 0

After adding 5 elements:
[E1, E2, E3, E4, E5, null, null, null, null, null]  // capacity = 10, size = 5

After adding 6th element (triggers expansion):
[E1, E2, E3, E4, E5, E6, null, null, null, null, null, null, null, null, null]  // capacity = 15, size = 6
```

## Constructors and Creation Methods

```java
// Default constructor - creates ArrayList with default capacity (10)
ArrayList<String> list1 = new ArrayList<>();

// Constructor with initial capacity
ArrayList<String> list2 = new ArrayList<>(50);

// Constructor from another collection
List<String> source = Arrays.asList("A", "B", "C");
ArrayList<String> list3 = new ArrayList<>(source);

// Using anonymous inner class (not recommended)
ArrayList<Integer> list4 = new ArrayList<Integer>() {{
    add(1);
    add(2);
    add(3);
}};

// Using Java 9+ List.of() (creates immutable list, wrapped in mutable ArrayList)
ArrayList<String> list5 = new ArrayList<>(List.of("X", "Y", "Z"));
```

## Core Operations

### Add Operations
```java
ArrayList<String> fruits = new ArrayList<>();

// add(E e) - adds element at the end, O(1) amortized
fruits.add("Apple");      // [Apple]
fruits.add("Banana");     // [Apple, Banana]

// add(int index, E e) - inserts element at specific index, O(n)
fruits.add(1, "Cherry");  // [Apple, Cherry, Banana]

// addAll() - adds multiple elements
List<String> moreFruits = Arrays.asList("Date", "Elderberry");
fruits.addAll(moreFruits);  // [Apple, Cherry, Banana, Date, Elderberry]

// addAll with index
fruits.addAll(2, Arrays.asList("Fig", "Grape"));
// [Apple, Cherry, Fig, Grape, Banana, Date, Elderberry]
```

### Remove Operations
```java
// remove(int index) - removes element at index, O(n)
String removed = fruits.remove(0);  // removes "Apple", returns it
System.out.println(removed);  // Apple

// remove(Object o) - removes first occurrence, O(n)
fruits.remove("Cherry");

// removeAll() - removes all elements in collection
fruits.removeAll(Arrays.asList("Date", "Elderberry"));

// removeIf() - removes elements matching predicate
fruits.removeIf(fruit -> fruit.length() > 4);

// clear() - removes all elements, O(n)
fruits.clear();
```

### Get Operations
```java
ArrayList<String> colors = new ArrayList<>(Arrays.asList("Red", "Green", "Blue"));

// get(int index) - retrieves element at index, O(1)
String color = colors.get(0);  // Red

// get without index - using iterator
for (String c : colors) {
    System.out.println(c);
}

// getFirst() / getLast() (Java 21+)
// String first = colors.getFirst();  // Red
// String last = colors.getLast();    // Blue
```

### Set Operations
```java
// set(int index, E element) - replaces element at index, O(1)
colors.set(1, "Yellow");  // [Red, Yellow, Blue]

// replaceAll() - replaces all elements with function
colors.replaceAll(String::toUpperCase);  // [RED, YELLOW, BLUE]
```

### Query Operations
```java
// size() - returns number of elements, O(1)
int size = colors.size();  // 3

// isEmpty() - checks if list is empty, O(1)
boolean empty = colors.isEmpty();  // false

// contains(Object o) - checks if element exists, O(n)
boolean hasRed = colors.contains("RED");  // true

// indexOf(Object o) - returns first occurrence index, O(n)
int index = colors.indexOf("RED");  // 0

// lastIndexOf(Object o) - returns last occurrence index, O(n)
int lastIndex = colors.lastIndexOf("BLUE");  // 2
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| add(E)    | O(1) amortized  | O(n) worst case  |
| add(i, E) | O(n)            | O(n) worst case  |
| remove(i) | O(n)            | O(1)             |
| get(i)    | O(1)            | O(1)             |
| set(i, E) | O(1)            | O(1)             |
| contains()| O(n)            | O(1)             |
| indexOf() | O(n)            | O(1)             |
| clear()   | O(n)            | O(1)             |

## Memory Layout and Resizing Strategy

ArrayList uses a growth strategy to minimize reallocation:

```
Growth Factor: 1.5x (new capacity = old capacity + old capacity >> 1)

Example progression:
Initial:     capacity = 10
After 11:    capacity = 15 (10 + 5)
After 16:    capacity = 22 (15 + 7)
After 23:    capacity = 33 (22 + 11)
After 34:    capacity = 49 (33 + 16)
```

This 1.5x growth factor provides a good balance between:
- **Memory efficiency**: Not allocating too much extra space
- **Time efficiency**: Not resizing too frequently

## Advantages

1. **Fast random access**: O(1) time complexity for accessing elements by index
2. **Memory efficient**: No extra overhead for pointers/links between elements
3. **Cache friendly**: Elements stored contiguously in memory, improving CPU cache performance
4. **Easy to use**: Simple and intuitive API
5. **Flexible size**: Automatically grows as needed
6. **Good for read-heavy operations**: Optimal when you frequently access elements

## Disadvantages

1. **Slow insertions/deletions at beginning**: O(n) for operations not at the end
2. **Memory waste**: May have unused capacity due to growth strategy
3. **Copying overhead**: Reallocation requires copying all elements
4. **Poor for large objects**: Copying large objects during reallocation is expensive
5. **Not thread-safe**: Requires external synchronization for concurrent access

## When to Use ArrayList

Use ArrayList when:
- You need fast random access to elements (frequent get operations)
- You primarily add/remove elements at the end of the list
- Memory locality and cache efficiency are important
- You have a stable or growing dataset without many mid-list modifications
- You want a simple, general-purpose list implementation

## Common Use Cases

1. **Storing search results**: Collect search results dynamically
2. **Building collections dynamically**: Accumulate data before processing
3. **Implementing caches**: Store frequently accessed data
4. **Processing variable-length data**: Handle lists of unknown size at compile time
5. **Game development**: Store game objects, particles, or entities

## Practical Code Examples

```java
import java.util.*;
import java.util.stream.*;

public class ArrayListExamples {
    
    // Example 1: Basic ArrayList operations
    public static void basicOperations() {
        ArrayList<Integer> numbers = new ArrayList<>();
        
        // Adding elements
        for (int i = 1; i <= 5; i++) {
            numbers.add(i * 10);
        }
        System.out.println("Numbers: " + numbers);  // [10, 20, 30, 40, 50]
        
        // Accessing elements
        System.out.println("First: " + numbers.get(0));      // 10
        System.out.println("Last: " + numbers.get(numbers.size() - 1));  // 50
        
        // Modifying elements
        numbers.set(2, 99);
        System.out.println("After set: " + numbers);  // [10, 20, 99, 40, 50]
        
        // Removing elements
        numbers.remove(Integer.valueOf(99));
        System.out.println("After remove: " + numbers);  // [10, 20, 40, 50]
    }
    
    // Example 2: Advanced operations with Streams
    public static void streamOperations() {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Eve", "Frank"
        ));
        
        // Filter and collect
        ArrayList<String> filtered = names.stream()
            .filter(name -> name.length() > 3)
            .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Filtered: " + filtered);
        // [Alice, Charlie, David, Frank]
        
        // Map and collect
        ArrayList<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Lengths: " + lengths);  // [5, 3, 7, 5, 3, 5]
        
        // Sort and display
        ArrayList<String> sorted = names.stream()
            .sorted()
            .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Sorted: " + sorted);
        // [Alice, Bob, Charlie, David, Eve, Frank]
    }
    
    // Example 3: ListIterator for bidirectional iteration
    public static void iteratorOperations() {
        ArrayList<String> colors = new ArrayList<>(Arrays.asList(
            "Red", "Green", "Blue", "Yellow", "Purple"
        ));
        
        System.out.println("Forward iteration:");
        ListIterator<String> iterator = colors.listIterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        
        System.out.println("Backward iteration:");
        while (iterator.hasPrevious()) {
            System.out.print(iterator.previous() + " ");
        }
        System.out.println();
    }
    
    // Example 4: Dynamic capacity tracking
    public static void capacityTracking() {
        ArrayList<Integer> list = new ArrayList<>(5);
        
        System.out.println("Initial capacity: " + getCapacity(list));
        
        for (int i = 1; i <= 20; i++) {
            list.add(i);
            if (i == 5 || i == 6 || i == 10 || i == 15 || i == 20) {
                System.out.println("After adding " + i + " elements, " +
                    "size: " + list.size() + ", capacity: " + getCapacity(list));
            }
        }
    }
    
    // Helper method to get ArrayList capacity (via reflection)
    public static int getCapacity(ArrayList<?> list) {
        try {
            java.lang.reflect.Field field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] elementData = (Object[]) field.get(list);
            return elementData.length;
        } catch (Exception e) {
            return -1;
        }
    }
    
    // Example 5: Removing duplicates while maintaining order
    public static void removeDuplicates() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(
            1, 2, 2, 3, 1, 4, 4, 5, 3, 5
        ));
        
        System.out.println("Original: " + numbers);  // [1, 2, 2, 3, 1, 4, 4, 5, 3, 5]
        
        ArrayList<Integer> unique = new ArrayList<>(
            new LinkedHashSet<>(numbers)
        );
        System.out.println("Without duplicates: " + unique);  // [1, 2, 3, 4, 5]
    }
    
    // Example 6: Batch operations
    public static void batchOperations() {
        ArrayList<String> fruits = new ArrayList<>(Arrays.asList(
            "Apple", "Banana", "Cherry"
        ));
        
        List<String> moreFruits = Arrays.asList("Date", "Elderberry", "Fig");
        
        // Add multiple elements
        fruits.addAll(moreFruits);
        System.out.println("After addAll: " + fruits);
        
        // Retain only specified elements (intersection)
        ArrayList<String> keep = new ArrayList<>(fruits);
        keep.retainAll(Arrays.asList("Apple", "Date", "Grape"));
        System.out.println("Retained: " + keep);  // [Apple, Date]
        
        // Remove multiple elements
        fruits.removeAll(Arrays.asList("Cherry", "Elderberry"));
        System.out.println("After removeAll: " + fruits);
    }
    
    // Example 7: Sorting with custom comparator
    public static void customSorting() {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(
            "elephant", "ant", "bear", "dog", "cat"
        ));
        
        // Sort by length
        words.sort((a, b) -> Integer.compare(a.length(), b.length()));
        System.out.println("Sorted by length: " + words);
        // [ant, cat, dog, bear, elephant]
        
        // Sort reverse alphabetically
        words.sort(Collections.reverseOrder());
        System.out.println("Reverse sorted: " + words);
        // [elephant, dog, cat, bear, ant]
    }
    
    // Example 8: Sublist operations
    public static void sublistOperations() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(
            10, 20, 30, 40, 50, 60, 70
        ));
        
        // Get sublist (view, not copy)
        List<Integer> sub = numbers.subList(2, 5);  // [30, 40, 50]
        System.out.println("Sublist: " + sub);
        
        // Modify sublist - affects original
        sub.set(0, 99);
        System.out.println("After modifying sublist: " + numbers);
        // [10, 20, 99, 40, 50, 60, 70]
    }
    
    public static void main(String[] args) {
        System.out.println("=== Basic Operations ===");
        basicOperations();
        
        System.out.println("\n=== Stream Operations ===");
        streamOperations();
        
        System.out.println("\n=== Iterator Operations ===");
        iteratorOperations();
        
        System.out.println("\n=== Capacity Tracking ===");
        capacityTracking();
        
        System.out.println("\n=== Remove Duplicates ===");
        removeDuplicates();
        
        System.out.println("\n=== Batch Operations ===");
        batchOperations();
        
        System.out.println("\n=== Custom Sorting ===");
        customSorting();
        
        System.out.println("\n=== Sublist Operations ===");
        sublistOperations();
    }
}
```

## Advanced Operations

### Cloning and Copying
```java
// Shallow copy
ArrayList<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));
ArrayList<String> clone1 = (ArrayList<String>) original.clone();
ArrayList<String> clone2 = new ArrayList<>(original);

// Deep copy for objects
List<String> deepCopy = original.stream().map(String::new)
    .collect(Collectors.toCollection(ArrayList::new));
```

### Converting to Array
```java
ArrayList<String> list = new ArrayList<>(Arrays.asList("X", "Y", "Z"));

// To Object array
Object[] objArray = list.toArray();

// To typed array
String[] strArray = list.toArray(new String[0]);
```

## Thread-Safety Considerations

ArrayList is **not thread-safe**. For concurrent access:

```java
// Synchronize manually
List<String> syncList = Collections.synchronizedList(new ArrayList<>());

// Use CopyOnWriteArrayList for read-heavy scenarios
List<String> cowList = new CopyOnWriteArrayList<>();
```

## Comparison with Other List Implementations

| Feature | ArrayList | LinkedList | Vector |
|---------|-----------|-----------|--------|
| Random Access | O(1) | O(n) | O(1) |
| Insert/Delete (start) | O(n) | O(1) | O(n) |
| Insert/Delete (end) | O(1)* | O(1) | O(1)* |
| Memory Overhead | Low | High | Low |
| Thread-Safe | No | No | Yes |
| Use Case | General purpose | Frequent insert/delete | Legacy, avoid |

## Summary

ArrayList is the most commonly used List implementation in Java due to its fast random access and simplicity. It's ideal when you need a flexible, dynamically-sized array but rarely modify elements at the beginning or middle. Understanding its internal resizing mechanism and performance characteristics is crucial for writing efficient Java applications.

