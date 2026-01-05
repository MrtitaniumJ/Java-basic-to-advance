# HashSet in Java

## Simple Explanation with Real-World Analogy

Think of a **HashSet** like a **restaurant reservation system**. When a customer calls to book a table, the system doesn't store reservations in order. Instead, it uses a hash function to convert the customer's name into a number, then stores that reservation in a specific location based on that number. Later, when the customer arrives, the system can quickly look up their reservation in that specific location rather than searching through all reservations sequentially.

Similarly, HashSet uses a **hash function** to convert objects into array indices, allowing for extremely fast lookups, insertions, and deletions in average cases. However, just like multiple customers might have names that hash to the same location (collision), HashSet uses **chaining** or **open addressing** to handle these collisions.

## Internal Structure: How HashSet Works

```
┌─────────────────────────────────┐
│    HashSet Internal Structure   │
├─────────────────────────────────┤
│  Index → Bucket → Linked List   │
├─────────────────────────────────┤
│   [0] → null                    │
│   [1] → "apple" → "banana" → .. │
│   [2] → "cherry"                │
│   [3] → null                    │
│   [4] → "date" → "elderberry"   │
│   ...                           │
└─────────────────────────────────┘

Hash Function: index = hash(object) % capacity
```

**HashSet** is backed by a **HashMap**. Here's how it works internally:

1. **Hashing**: When you add an element, HashSet calculates a hash code using `hashCode()` method
2. **Indexing**: Converts the hash code to an array index using modulo operation: `index = hashCode() % capacity`
3. **Storage**: Stores the element at that index
4. **Collision Handling**: If multiple elements hash to the same index, they form a linked list (bucket) at that index
5. **Load Factor**: When elements exceed `capacity * loadFactor` (default 0.75), the hash table is resized

## Constructors and Creation Methods

```java
// 1. Default constructor - capacity 16, load factor 0.75
HashSet<String> set1 = new HashSet<>();

// 2. Constructor with initial capacity
HashSet<Integer> set2 = new HashSet<>(32);

// 3. Constructor with capacity and load factor
HashSet<Double> set3 = new HashSet<>(64, 0.8f);

// 4. Constructor with another collection
List<String> list = Arrays.asList("Java", "Python", "C++");
HashSet<String> set4 = new HashSet<>(list);

// 5. Copy constructor (from another set)
HashSet<String> set5 = new HashSet<>(set4);
```

## Core Operations

### Add Operation
```java
HashSet<String> fruits = new HashSet<>();

// Add single element - O(1) average, O(n) worst case
fruits.add("apple");
fruits.add("banana");
fruits.add("cherry");

// Add returns true if added, false if already exists
boolean isAdded = fruits.add("apple"); // false
System.out.println("Added: " + isAdded); // false

// Add multiple elements
List<String> moreFruits = Arrays.asList("date", "elderberry", "fig");
fruits.addAll(moreFruits);

System.out.println("Fruits: " + fruits);
// Output: Fruits: [apple, banana, cherry, date, elderberry, fig]
```

### Remove Operation
```java
// Remove single element - O(1) average, O(n) worst case
boolean removed = fruits.remove("banana");
System.out.println("Removed banana: " + removed); // true

// Remove if absent returns false
boolean notFound = fruits.remove("orange");
System.out.println("Removed orange: " + notFound); // false

// Remove multiple elements
List<String> toRemove = Arrays.asList("date", "elderberry");
fruits.removeAll(toRemove);

// Remove all elements matching a condition (lambda)
fruits.removeIf(fruit -> fruit.length() < 5);
```

### Contains Operation
```java
// Check existence - O(1) average, O(n) worst case
boolean hasCherries = fruits.contains("cherry");
System.out.println("Has cherry: " + hasCherries); // true

// Check if all elements are present
List<String> checkList = Arrays.asList("apple", "cherry");
boolean hasAll = fruits.containsAll(checkList);
System.out.println("Contains all: " + hasAll);

// Check if any element exists
boolean hasAny = fruits.stream()
    .anyMatch(f -> f.equals("apple"));
System.out.println("Has apple: " + hasAny); // true
```

### Clear and Size Operations
```java
// Get size - O(1)
int size = fruits.size();
System.out.println("Total fruits: " + size);

// Check if empty - O(1)
boolean isEmpty = fruits.isEmpty();
System.out.println("Is empty: " + isEmpty); // false

// Clear all elements - O(n)
fruits.clear();
System.out.println("Size after clear: " + fruits.size()); // 0
System.out.println("Is empty now: " + fruits.isEmpty()); // true
```

## Iteration Behavior and Ordering

```java
HashSet<String> colors = new HashSet<>(Arrays.asList(
    "red", "green", "blue", "yellow", "purple"
));

// 1. Enhanced for loop - No guaranteed order
System.out.println("Enhanced for loop:");
for (String color : colors) {
    System.out.println(color);
}
// Output might be: blue, red, purple, yellow, green (order varies)

// 2. Iterator - No guaranteed order, fail-fast
System.out.println("\nIterator:");
Iterator<String> iterator = colors.iterator();
while (iterator.hasNext()) {
    String color = iterator.next();
    System.out.println(color);
}

// 3. Stream API
System.out.println("\nStream API:");
colors.stream()
    .forEach(System.out::println);

// 4. forEach with Consumer
colors.forEach(color -> System.out.println("Color: " + color));
```

## Performance Characteristics

```java
public class PerformanceTest {
    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        
        // Add 1,000,000 elements
        long startAdd = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            set.add(i);
        }
        long endAdd = System.currentTimeMillis();
        System.out.println("Add 1M elements: " + (endAdd - startAdd) + "ms");
        
        // Contains search (average case)
        long startSearch = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            set.contains(i);
        }
        long endSearch = System.currentTimeMillis();
        System.out.println("Search 1M elements: " + (endSearch - startSearch) + "ms");
        
        // Remove elements
        long startRemove = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            set.remove(i);
        }
        long endRemove = System.currentTimeMillis();
        System.out.println("Remove 100K elements: " + (endRemove - startRemove) + "ms");
    }
}

// Typical Output:
// Add 1M elements: 150-250ms
// Search 1M elements: 100-200ms
// Remove 100K elements: 30-80ms
```

| Operation | Average | Worst Case | Note |
|-----------|---------|------------|------|
| Add | O(1) | O(n) | If too many collisions |
| Remove | O(1) | O(n) | If too many collisions |
| Contains | O(1) | O(n) | If too many collisions |
| Clear | O(n) | O(n) | - |
| Size | O(1) | O(1) | - |

## Collision Handling

```java
public class CollisionExample {
    public static void main(String[] args) {
        // Custom class with intentional hash collisions for demonstration
        HashSet<BadHashKey> set = new HashSet<>();
        
        // These will likely collide
        set.add(new BadHashKey("A"));
        set.add(new BadHashKey("B"));
        set.add(new BadHashKey("C"));
        
        System.out.println("Set size: " + set.size());
    }
    
    static class BadHashKey {
        String value;
        
        BadHashKey(String value) {
            this.value = value;
        }
        
        @Override
        public int hashCode() {
            return 1; // All objects hash to same value (deliberate collision)
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BadHashKey)) return false;
            BadHashKey that = (BadHashKey) o;
            return value.equals(that.value);
        }
    }
}

// When all hash to 1, they form a linked list: O(1) lookup becomes O(n)
```

## Null Handling and Thread Safety

```java
public class NullAndThreadSafety {
    public static void main(String[] args) {
        // Null Handling
        HashSet<String> set = new HashSet<>();
        set.add(null); // Allowed! But only one null
        set.add("value");
        
        System.out.println("Contains null: " + set.contains(null)); // true
        System.out.println("Set: " + set); // [null, value]
        
        // Adding another null won't duplicate
        set.add(null);
        System.out.println("Size after adding null again: " + set.size()); // 2
        
        // Thread Safety - HashSet is NOT synchronized
        // Use Collections.synchronizedSet() for thread-safe version
        Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
        syncSet.add("thread-safe");
        
        // Or use ConcurrentHashMap for better concurrency
        Set<String> concurrentSet = ConcurrentHashMap.newKeySet();
        concurrentSet.add("highly-concurrent");
    }
}
```

## Real-World Code Examples

```java
public class RealWorldHashSetExamples {
    public static void main(String[] args) {
        // Example 1: Remove Duplicates from List
        removeDuplicates();
        
        // Example 2: Find Unique Characters
        findUniqueCharacters();
        
        // Example 3: Common Elements Between Lists
        findCommonElements();
        
        // Example 4: Fast Lookup with User IDs
        userLookupExample();
    }
    
    // Example 1: Remove duplicates from a list
    static void removeDuplicates() {
        System.out.println("=== Remove Duplicates ===");
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5, 5);
        HashSet<Integer> unique = new HashSet<>(numbers);
        System.out.println("Original: " + numbers);
        System.out.println("Unique: " + unique); // [1, 2, 3, 4, 5]
    }
    
    // Example 2: Find unique characters in string
    static void findUniqueCharacters() {
        System.out.println("\n=== Find Unique Characters ===");
        String text = "mississippi";
        Set<Character> chars = new HashSet<>();
        for (char c : text.toCharArray()) {
            chars.add(c);
        }
        System.out.println("Unique chars in '" + text + "': " + chars);
        // Output: [i, m, p, s]
    }
    
    // Example 3: Find common elements between lists
    static void findCommonElements() {
        System.out.println("\n=== Common Elements ===");
        List<String> list1 = Arrays.asList("apple", "banana", "cherry", "date");
        List<String> list2 = Arrays.asList("banana", "date", "elderberry", "fig");
        
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        
        set1.retainAll(set2); // Keep only common elements
        System.out.println("Common: " + set1); // [banana, date]
    }
    
    // Example 4: Fast user lookup
    static void userLookupExample() {
        System.out.println("\n=== User Lookup ===");
        HashSet<Integer> registeredUserIds = new HashSet<>(
            Arrays.asList(101, 102, 103, 104, 105)
        );
        
        int queryId = 103;
        if (registeredUserIds.contains(queryId)) {
            System.out.println("User " + queryId + " is registered");
        } else {
            System.out.println("User " + queryId + " not found");
        }
    }
}
```

## When to Use HashSet

✅ **Use HashSet when:**
- You need **fast lookups, additions, and deletions** (O(1) average)
- **Order doesn't matter** for your data
- You need to **remove duplicates** quickly
- You're implementing **caching or membership testing**
- You have **good hash codes** for your objects (minimizing collisions)

❌ **Avoid HashSet when:**
- You need **sorted data** (use TreeSet instead)
- You need to **maintain insertion order** (use LinkedHashSet instead)
- You need **thread-safe operations** without synchronization overhead (consider ConcurrentHashMap.newKeySet())
- You're frequently dealing with **many hash collisions**

## Memory Usage and Efficiency

```java
public class MemoryAnalysis {
    public static void main(String[] args) {
        // HashSet uses about 48 bytes per entry on average (with overhead)
        // Including: hash code, object reference, linked list node (if collision)
        
        HashSet<String> set = new HashSet<>(1000); // Pre-allocate
        
        // Memory efficient for large datasets with good hash distribution
        for (int i = 0; i < 10000; i++) {
            set.add("Item-" + i);
        }
        
        System.out.println("Elements: " + set.size());
        System.out.println("Memory per element: ~48 bytes");
        System.out.println("Total approx: " + (set.size() * 48 / 1024) + "KB");
    }
}
```

## Comparison with Other Set Implementations

| Feature | HashSet | LinkedHashSet | TreeSet | EnumSet |
|---------|---------|---------------|---------|---------|
| Order | Unordered | Insertion order | Sorted | Natural order |
| Add | O(1) avg | O(1) avg | O(log n) | O(1) |
| Remove | O(1) avg | O(1) avg | O(log n) | O(1) |
| Contains | O(1) avg | O(1) avg | O(log n) | O(1) |
| Space | High | Higher | Medium | Lowest |
| Null | Allowed | Allowed | Not allowed | Not applicable |

## Key Takeaways

- **HashSet** is the fastest general-purpose set implementation for add/remove/contains operations
- Perfect for **deduplication** and **fast membership testing**
- No ordering guarantees - elements are stored based on their hash codes
- Performance degrades only when there are many hash collisions
- **Not thread-safe** - wrap with `Collections.synchronizedSet()` if needed
- Always implement proper `hashCode()` and `equals()` methods in your objects
