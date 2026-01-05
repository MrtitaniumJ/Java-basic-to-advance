# Maps in Java - Key-Value Pair Collections

## Simple Explanation

Think of a **Map** as a **dictionary or phone book**:
- Stores **key-value pairs** (word → definition, name → phone number)
- Each **key is unique** (no duplicate keys)
- Keys **map to values** (quick lookup by key)
- Values can be **duplicate** (multiple keys can have same value)

### Real-World Analogy
Imagine a **phone contacts list**:
- Name (key) → Phone Number (value)
- Each contact name is unique
- You can quickly find a phone number by searching the name
- Multiple contacts can have the same phone number
- HashMap: Contacts in random order
- LinkedHashMap: Contacts in the order you added them
- TreeMap: Contacts sorted alphabetically

## Professional Definition

A **Map** is an object that maps keys to values, where each key can map to at most one value. Maps do not extend the Collection interface but are part of the Java Collections Framework. A Map cannot contain duplicate keys, and each key can map to at most one value. The Map interface provides three collection views: keys, values, and key-value mappings (entries).

## Map Interface Hierarchy

```
Map (Interface)
    ├── HashMap (Class) - Hash table implementation, O(1) operations
    ├── LinkedHashMap (Class) - Hash table + linked list, maintains insertion order
    ├── TreeMap (Class) - Red-Black tree, sorted by keys, O(log n) operations
    ├── Hashtable (Class) - Legacy, synchronized, no null keys/values
    ├── WeakHashMap (Class) - Weak references for keys, eligible for GC
    ├── IdentityHashMap (Class) - Uses == for key comparison
    └── EnumMap (Class) - Specialized for enum keys, extremely efficient
```

## Types of Map Implementations

### Overview Comparison:

| Feature | HashMap | LinkedHashMap | TreeMap | Hashtable | WeakHashMap |
|---------|---------|---------------|---------|-----------|-------------|
| **Ordering** | None (random) | Insertion order | Natural/Custom sorted | None (random) | None (random) |
| **Get/Put/Remove** | O(1) | O(1) | O(log n) | O(1) | O(1) |
| **Internal Structure** | Hash Table | Hash Table + Linked List | Red-Black Tree | Hash Table | Hash Table (weak refs) |
| **Allows Null Key** | Yes (one) | Yes (one) | No | No | Yes (one) |
| **Allows Null Values** | Yes | Yes | Yes | No | Yes |
| **Thread-Safe** | No | No | No | Yes (synchronized) | No |
| **Best Use Case** | General purpose, fast lookup | Cache, insertion order | Sorted keys, range queries | Legacy code only | Memory-sensitive caching |
| **Memory** | Moderate | Higher (linked list) | Higher (tree nodes) | Moderate | Moderate + GC overhead |

## 1. HashMap - Fast General-Purpose Map

### Key Characteristics:
- ✅ **Fastest operations** - O(1) average time for get, put, remove
- ✅ **Allows null** - One null key and multiple null values
- ✅ **Best for general use** - Most commonly used Map implementation
- ❌ **No ordering** - Keys/values in random order
- ❌ **Not thread-safe** - Requires external synchronization

### Internal Working:
```
Hash Table with Buckets:

Bucket 0: null
Bucket 1: → ["key1" → "value1"] → null
Bucket 2: → ["key2" → "value2"] → ["key8" → "value8"] → null (collision chain)
Bucket 3: → ["key3" → "value3"] → null
Bucket 4: null
...
Bucket 15: → [null → "nullKeyValue"] → null

Hash function: hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)
Index calculation: index = hash & (table.length - 1)

Load Factor: 0.75 (resizes when 75% full)
Initial Capacity: 16
Resize: New capacity = old capacity × 2
```

### Basic Operations:

```java
import java.util.HashMap;
import java.util.Map;

class HashMapBasics {
    
    public static void demonstrateHashMap() {
        System.out.println("=== HashMap Demonstration ===\n");
        
        // Creating HashMap
        Map<String, Integer> scores = new HashMap<>();
        
        // Adding key-value pairs
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        scores.put("Alice", 98);  // Updates Alice's score (key already exists)
        
        System.out.println("Scores: " + scores);
        System.out.println("Size: " + scores.size());
        
        // Accessing values
        System.out.println("\n--- Accessing Values ---");
        System.out.println("Alice's score: " + scores.get("Alice"));
        System.out.println("David's score: " + scores.get("David"));  // null (key doesn't exist)
        System.out.println("David's score with default: " + 
                          scores.getOrDefault("David", 0));
        
        // Checking keys/values
        System.out.println("\n--- Checking Existence ---");
        System.out.println("Contains key 'Bob': " + scores.containsKey("Bob"));
        System.out.println("Contains value 92: " + scores.containsValue(92));
        
        // Removing
        System.out.println("\n--- Removing ---");
        Integer removed = scores.remove("Bob");
        System.out.println("Removed Bob's score: " + removed);
        System.out.println("After removal: " + scores);
        
        // Conditional put
        scores.putIfAbsent("Alice", 100);  // Won't update (key exists)
        scores.putIfAbsent("David", 85);   // Will add (key doesn't exist)
        System.out.println("\n After putIfAbsent: " + scores);
    }
    
    public static void main(String[] args) {
        demonstrateHashMap();
    }
}
```

**Output:**
```
=== HashMap Demonstration ===

Scores: {Alice=98, Bob=87, Charlie=92}
Size: 3

--- Accessing Values ---
Alice's score: 98
David's score: null
David's score with default: 0

--- Checking Existence ---
Contains key 'Bob': true
Contains value 92: true

--- Removing ---
Removed Bob's score: 87
After removal: {Alice=98, Charlie=92}

After putIfAbsent: {Alice=98, Charlie=92, David=85}
```

### Advanced HashMap Operations:

```java
import java.util.*;

class HashMapAdvanced {
    
    public static void demonstrateAdvancedOperations() {
        System.out.println("=== Advanced HashMap Operations ===\n");
        
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Apples", 50);
        inventory.put("Bananas", 30);
        inventory.put("Oranges", 20);
        
        // Iterating over keys
        System.out.println("--- Iterating Keys ---");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
        
        // Iterating over values
        System.out.println("\n--- Iterating Values ---");
        for (Integer value : inventory.values()) {
            System.out.println("Stock: " + value);
        }
        
        // Iterating over entries (most efficient)
        System.out.println("\n--- Iterating Entries ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
        
        // Compute operations (Java 8+)
        System.out.println("\n--- Compute Operations ---");
        inventory.compute("Apples", (k, v) -> v + 10);  // Add 10 to existing value
        System.out.println("After compute: " + inventory);
        
        inventory.computeIfPresent("Bananas", (k, v) -> v - 5);  // Subtract if key exists
        inventory.computeIfAbsent("Grapes", k -> 15);  // Add if key doesn't exist
        System.out.println("After conditional compute: " + inventory);
        
        // Merge operation
        inventory.merge("Apples", 20, (oldVal, newVal) -> oldVal + newVal);  // Add to existing
        inventory.merge("Peaches", 25, (oldVal, newVal) -> oldVal + newVal); // Create new
        System.out.println("After merge: " + inventory);
        
        // Replace operations
        inventory.replace("Oranges", 20, 35);  // Replace if current value is 20
        inventory.replaceAll((k, v) -> v * 2); // Double all values
        System.out.println("After replace operations: " + inventory);
    }
    
    public static void main(String[] args) {
        demonstrateAdvancedOperations();
    }
}
```

### When to Use HashMap:

✅ **Use HashMap When:**
- Need fast key-value lookups (O(1))
- Order doesn't matter
- No thread-safety requirements
- General-purpose mapping

❌ **Avoid HashMap When:**
- Need sorted keys (use TreeMap)
- Need insertion order (use LinkedHashMap)
- Need thread-safety (use ConcurrentHashMap)

---

## 2. LinkedHashMap - Ordered Map

### Key Characteristics:
- ✅ **Predictable iteration order** - Insertion order or access order
- ✅ **Fast operations** - O(1) like HashMap
- ✅ **Access-order mode** - Can implement LRU cache
- ❌ **Higher memory** - Maintains doubly-linked list
- ❌ **Slightly slower** - Additional pointer maintenance

### Internal Working:
```
Hash Table + Doubly-Linked List:

Hash Table (for fast access):
Bucket[0] → null
Bucket[1] → Entry("A", 1)
Bucket[2] → Entry("B", 2)
Bucket[3] → Entry("C", 3)

Linked List (for ordering):
HEAD → Entry("A", 1) ⇄ Entry("B", 2) ⇄ Entry("C", 3) ← TAIL

Iteration follows linked list order, not hash table order
```

### Basic Operations:

```java
import java.util.LinkedHashMap;
import java.util.Map;

class LinkedHashMapBasics {
    
    public static void demonstrateLinkedHashMap() {
        System.out.println("=== LinkedHashMap Demonstration ===\n");
        
        // Creating LinkedHashMap (insertion-order)
        Map<String, String> countries = new LinkedHashMap<>();
        
        countries.put("USA", "Washington DC");
        countries.put("India", "New Delhi");
        countries.put("Japan", "Tokyo");
        countries.put("France", "Paris");
        
        System.out.println("Countries: " + countries);
        
        // Iteration maintains insertion order
        System.out.println("\n--- Insertion Order Maintained ---");
        for (Map.Entry<String, String> entry : countries.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
        
        // Still O(1) operations
        System.out.println("\nCapital of Japan: " + countries.get("Japan"));
        
        countries.remove("India");
        System.out.println("\nAfter removing India: " + countries);
    }
    
    public static void main(String[] args) {
        demonstrateLinkedHashMap();
    }
}
```

**Output:**
```
=== LinkedHashMap Demonstration ===

Countries: {USA=Washington DC, India=New Delhi, Japan=Tokyo, France=Paris}

--- Insertion Order Maintained ---
USA → Washington DC
India → New Delhi
Japan → Tokyo
France → Paris

Capital of Japan: Tokyo

After removing India: {USA=Washington DC, Japan=Tokyo, France=Paris}
```

### LRU Cache Implementation:

```java
import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    
    public LRUCache(int capacity) {
        // accessOrder = true means access-order (not insertion-order)
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;  // Remove oldest when capacity exceeded
    }
    
    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        
        System.out.println("=== LRU Cache (Capacity: 3) ===\n");
        
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        System.out.println("After adding A, B, C: " + cache);
        
        cache.get("A");  // Access A (makes it most recently used)
        System.out.println("After accessing A: " + cache);
        
        cache.put("D", 4);  // Adds D, removes least recently used (B)
        System.out.println("After adding D (capacity exceeded): " + cache);
    }
}
```

**Output:**
```
=== LRU Cache (Capacity: 3) ===

After adding A, B, C: {A=1, B=2, C=3}
After accessing A: {A=1, B=2, C=3}
After adding D (capacity exceeded): {A=1, C=3, D=4}
```

### When to Use LinkedHashMap:

✅ **Use LinkedHashMap When:**
- Need insertion order preservation
- Implementing LRU cache
- Want predictable iteration
- Building access history

❌ **Avoid LinkedHashMap When:**
- Don't need ordering (use HashMap - less memory)
- Need sorted keys (use TreeMap)

---

## 3. TreeMap - Sorted Map

### Key Characteristics:
- ✅ **Sorted by keys** - Natural or custom ordering
- ✅ **NavigableMap** - Range operations, floor, ceiling
- ✅ **No null keys** - TreeMap doesn't allow null keys
- ❌ **Slower** - O(log n) for get, put, remove
- ❌ **Higher memory** - Tree node overhead

### Internal Working:
```
Red-Black Tree (Self-Balancing BST):

          [5, "Five"]
         /            \
    [3, "Three"]    [8, "Eight"]
    /        \      /         \
[1, "One"] [4, "Four"] [6, "Six"] [9, "Nine"]

In-order traversal gives sorted keys: 1, 3, 4, 5, 6, 8, 9
Tree automatically rebalances on insert/delete
```

### Basic Operations:

```java
import java.util.TreeMap;
import java.util.Map;

class TreeMapBasics {
    
    public static void demonstrateTreeMap() {
        System.out.println("=== TreeMap Demonstration ===\n");
        
        TreeMap<Integer, String> employees = new TreeMap<>();
        
        // Adding entries (will be sorted by keys)
        employees.put(103, "Charlie");
        employees.put(101, "Alice");
        employees.put(105, "Eve");
        employees.put(102, "Bob");
        employees.put(104, "David");
        
        System.out.println("Employees (sorted by ID): " + employees);
        
        // Accessing
        System.out.println("\n--- Accessing ---");
        System.out.println("Employee 103: " + employees.get(103));
        
        // NavigableMap operations
        System.out.println("\n--- NavigableMap Operations ---");
        System.out.println("First key: " + employees.firstKey());
        System.out.println("Last key: " + employees.lastKey());
        System.out.println("First entry: " + employees.firstEntry());
        System.out.println("Last entry: " + employees.lastEntry());
        
        System.out.println("\nLower key than 103: " + employees.lowerKey(103));     // Largest < 103
        System.out.println("Floor key of 103: " + employees.floorKey(103));         // Largest <= 103
        System.out.println("Ceiling key of 103: " + employees.ceilingKey(103));     // Smallest >= 103
        System.out.println("Higher key than 103: " + employees.higherKey(103));     // Smallest > 103
        
        // Range operations
        System.out.println("\n--- Range Views ---");
        System.out.println("Head map (< 103): " + employees.headMap(103));
        System.out.println("Tail map (>= 103): " + employees.tailMap(103));
        System.out.println("Sub map [102, 104): " + employees.subMap(102, 104));
        
        // Descending order
        System.out.println("\n--- Descending Order ---");
        System.out.println("Descending map: " + employees.descendingMap());
        System.out.println("Descending keys: " + employees.descendingKeySet());
    }
    
    public static void main(String[] args) {
        demonstrateTreeMap();
    }
}
```

**Output:**
```
=== TreeMap Demonstration ===

Employees (sorted by ID): {101=Alice, 102=Bob, 103=Charlie, 104=David, 105=Eve}

--- Accessing ---
Employee 103: Charlie

--- NavigableMap Operations ---
First key: 101
Last key: 105
First entry: 101=Alice
Last entry: 105=Eve

Lower key than 103: 102
Floor key of 103: 103
Ceiling key of 103: 103
Higher key than 103: 104

--- Range Views ---
Head map (< 103): {101=Alice, 102=Bob}
Tail map (>= 103): {103=Charlie, 104=David, 105=Eve}
Sub map [102, 104): {102=Bob, 103=Charlie}

--- Descending Order ---
Descending map: {105=Eve, 104=David, 103=Charlie, 102=Bob, 101=Alice}
Descending keys: [105, 104, 103, 102, 101]
```

### Custom Comparator:

```java
import java.util.*;

class TreeMapCustomComparator {
    
    public static void demonstrateCustomComparator() {
        System.out.println("=== TreeMap with Custom Comparator ===\n");
        
        // Sort by string length, then alphabetically
        Comparator<String> lengthComparator = (s1, s2) -> {
            int lengthCompare = Integer.compare(s1.length(), s2.length());
            return lengthCompare != 0 ? lengthCompare : s1.compareTo(s2);
        };
        
        TreeMap<String, Integer> wordCounts = new TreeMap<>(lengthComparator);
        
        wordCounts.put("apple", 5);
        wordCounts.put("banana", 3);
        wordCounts.put("cat", 7);
        wordCounts.put("dog", 2);
        wordCounts.put("elephant", 1);
        
        System.out.println("Words sorted by length: " + wordCounts);
        
        // Reverse order TreeMap
        TreeMap<Integer, String> reverseMap = new TreeMap<>(Collections.reverseOrder());
        reverseMap.put(1, "One");
        reverseMap.put(2, "Two");
        reverseMap.put(3, "Three");
        
        System.out.println("\nReverse order: " + reverseMap);
    }
    
    public static void main(String[] args) {
        demonstrateCustomComparator();
    }
}
```

### When to Use TreeMap:

✅ **Use TreeMap When:**
- Need sorted keys
- Need range queries (subMap, headMap, tailMap)
- Need floor/ceiling operations
- Need to find nearest keys

❌ **Avoid TreeMap When:**
- Don't need sorting (use HashMap - faster)
- Keys are not Comparable
- Need very fast lookups (O(1) vs O(log n))

---

## 4. Hashtable - Legacy Synchronized Map

### Key Characteristics:
- ✅ **Thread-safe** - All methods synchronized
- ✅ **Legacy compatibility** - Older Java code
- ❌ **No null keys/values** - Throws NullPointerException
- ❌ **Slower** - Synchronization overhead
- ❌ **Obsolete** - Use ConcurrentHashMap instead

### Basic Operations:

```java
import java.util.Hashtable;
import java.util.Map;

class HashtableBasics {
    
    public static void demonstrateHashtable() {
        System.out.println("=== Hashtable Demonstration ===\n");
        
        Hashtable<String, Integer> table = new Hashtable<>();
        
        // Adding entries
        table.put("One", 1);
        table.put("Two", 2);
        table.put("Three", 3);
        
        System.out.println("Hashtable: " + table);
        
        // No null keys or values
        try {
            table.put(null, 4);  // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("\n❌ Cannot add null key: " + e.getMessage());
        }
        
        try {
            table.put("Four", null);  // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("❌ Cannot add null value: " + e.getMessage());
        }
        
        // Thread-safe operations
        System.out.println("\n✅ All methods are synchronized (thread-safe)");
        System.out.println("But use ConcurrentHashMap for better performance");
    }
    
    public static void main(String[] args) {
        demonstrateHashtable();
    }
}
```

**Output:**
```
=== Hashtable Demonstration ===

Hashtable: {One=1, Two=2, Three=3}

❌ Cannot add null key: null
❌ Cannot add null value: null

✅ All methods are synchronized (thread-safe)
But use ConcurrentHashMap for better performance
```

### When to Use Hashtable:

✅ **Use Hashtable When:**
- Maintaining legacy code only
- Required for backward compatibility

❌ **Avoid Hashtable When:**
- Writing new code (use ConcurrentHashMap)
- Don't need thread-safety (use HashMap)
- Need null keys/values

---

## 5. WeakHashMap - Memory-Sensitive Map

### Key Characteristics:
- ✅ **Weak references** - Keys can be garbage collected
- ✅ **Automatic cleanup** - Entries removed when keys are GC'd
- ✅ **Memory efficient** - For caching scenarios
- ❌ **Unpredictable** - Entries may disappear
- ❌ **Not for strong references** - Only use for cache-like scenarios

### Internal Working:
```
WeakHashMap uses WeakReference for keys:

Strong Reference: Object → Map → Value (prevents GC)
Weak Reference: Object ⇢ Map → Value (allows GC)

When object has no strong references:
1. Garbage Collector marks it for collection
2. WeakHashMap automatically removes the entry
3. Memory is freed
```

### Basic Operations:

```java
import java.util.WeakHashMap;
import java.util.Map;

class WeakHashMapBasics {
    
    public static void demonstrateWeakHashMap() {
        System.out.println("=== WeakHashMap Demonstration ===\n");
        
        WeakHashMap<Person, String> cache = new WeakHashMap<>();
        
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        Person p3 = new Person("Charlie");
        
        cache.put(p1, "Engineer");
        cache.put(p2, "Doctor");
        cache.put(p3, "Teacher");
        
        System.out.println("Cache size: " + cache.size());
        System.out.println("Cache: " + cache);
        
        // Remove strong reference to p2
        p2 = null;
        
        // Request garbage collection (not guaranteed to run immediately)
        System.gc();
        
        // Wait a bit for GC to run
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        
        System.out.println("\nAfter removing strong reference to Bob:");
        System.out.println("Cache size: " + cache.size());
        System.out.println("Cache: " + cache);
        System.out.println("\n✅ Bob's entry may be removed by GC");
    }
    
    static class Person {
        String name;
        
        Person(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public static void main(String[] args) {
        demonstrateWeakHashMap();
    }
}
```

### When to Use WeakHashMap:

✅ **Use WeakHashMap When:**
- Implementing memory-sensitive caches
- Keys should not prevent garbage collection
- Building metadata maps

❌ **Avoid WeakHashMap When:**
- Need guaranteed persistence
- Keys must always remain
- Using primitive wrappers or String literals (may not be GC'd)

---

## Common Map Operations

### 1. Creating Maps:

```java
// Empty map
Map<String, Integer> map1 = new HashMap<>();

// With initial capacity and load factor
Map<String, Integer> map2 = new HashMap<>(100, 0.75f);

// From existing map
Map<String, Integer> map3 = new HashMap<>(map1);

// Immutable map (Java 9+)
Map<String, Integer> map4 = Map.of("A", 1, "B", 2, "C", 3);

// Immutable map with more entries (Java 9+)
Map<String, Integer> map5 = Map.ofEntries(
    Map.entry("One", 1),
    Map.entry("Two", 2),
    Map.entry("Three", 3)
);
```

### 2. CRUD Operations:

```java
Map<String, Integer> map = new HashMap<>();

// Create / Update
map.put("A", 1);                    // Add new entry
map.put("A", 2);                    // Update existing
map.putIfAbsent("B", 3);           // Add only if absent
map.putAll(otherMap);              // Add all from another map

// Read
Integer value = map.get("A");       // Get value (null if not found)
Integer defaultVal = map.getOrDefault("C", 0);  // Get with default

// Update
map.replace("A", 5);                // Replace if key exists
map.replace("A", 2, 6);            // Replace if current value is 2

// Delete
map.remove("A");                    // Remove by key
map.remove("B", 3);                // Remove only if value is 3
map.clear();                        // Remove all entries
```

### 3. Iteration Patterns:

```java
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 95);
scores.put("Bob", 87);
scores.put("Charlie", 92);

// 1. Iterate over keys
for (String name : scores.keySet()) {
    System.out.println(name + ": " + scores.get(name));
}

// 2. Iterate over values
for (Integer score : scores.values()) {
    System.out.println("Score: " + score);
}

// 3. Iterate over entries (most efficient)
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + " → " + entry.getValue());
}

// 4. Java 8+ forEach
scores.forEach((name, score) -> 
    System.out.println(name + ": " + score)
);

// 5. Iterator
Iterator<Map.Entry<String, Integer>> iterator = scores.entrySet().iterator();
while (iterator.hasNext()) {
    Map.Entry<String, Integer> entry = iterator.next();
    System.out.println(entry.getKey() + " → " + entry.getValue());
    // Can safely remove during iteration
    if (entry.getValue() < 90) {
        iterator.remove();
    }
}
```

### 4. Bulk Operations:

```java
Map<String, Integer> map1 = new HashMap<>();
map1.put("A", 1);
map1.put("B", 2);

Map<String, Integer> map2 = new HashMap<>();
map2.put("B", 3);
map2.put("C", 4);

// Merge maps
Map<String, Integer> merged = new HashMap<>(map1);
map2.forEach((key, value) -> 
    merged.merge(key, value, (v1, v2) -> v1 + v2)
);
// Result: {A=1, B=5, C=4}

// Filter entries
Map<String, Integer> filtered = new HashMap<>();
map1.entrySet().stream()
    .filter(e -> e.getValue() > 1)
    .forEach(e -> filtered.put(e.getKey(), e.getValue()));

// Transform values
map1.replaceAll((k, v) -> v * 10);
// Result: {A=10, B=20}
```

### 5. Compute Operations (Java 8+):

```java
Map<String, Integer> wordCount = new HashMap<>();

String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};

// Count word frequencies
for (String word : words) {
    wordCount.merge(word, 1, Integer::sum);
}
// Result: {apple=3, banana=2, cherry=1}

// Compute operations
wordCount.compute("apple", (k, v) -> v == null ? 1 : v + 1);
wordCount.computeIfPresent("banana", (k, v) -> v * 2);
wordCount.computeIfAbsent("date", k -> 10);

System.out.println(wordCount);
// Result: {apple=4, banana=4, cherry=1, date=10}
```

---

## Practical Examples

### Example 1: Word Frequency Counter

```java
import java.util.*;

class WordFrequencyCounter {
    
    public static Map<String, Integer> countWords(String text) {
        Map<String, Integer> frequency = new HashMap<>();
        
        String[] words = text.toLowerCase().split("\\s+");
        
        for (String word : words) {
            frequency.merge(word, 1, Integer::sum);
        }
        
        return frequency;
    }
    
    public static void main(String[] args) {
        String text = "Java is great Java is powerful Java is versatile";
        
        Map<String, Integer> wordCount = countWords(text);
        
        System.out.println("Word Frequencies:");
        wordCount.forEach((word, count) -> 
            System.out.println(word + ": " + count)
        );
        
        // Find most frequent word
        String mostFrequent = wordCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
        
        System.out.println("\nMost frequent word: " + mostFrequent);
    }
}
```

### Example 2: Grouping Data

```java
import java.util.*;
import java.util.stream.Collectors;

class DataGrouping {
    
    static class Student {
        String name;
        String department;
        
        Student(String name, String department) {
            this.name = name;
            this.department = department;
        }
    }
    
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", "CS"),
            new Student("Bob", "EE"),
            new Student("Charlie", "CS"),
            new Student("David", "ME"),
            new Student("Eve", "EE")
        );
        
        // Group students by department
        Map<String, List<Student>> byDepartment = new HashMap<>();
        
        for (Student student : students) {
            byDepartment.computeIfAbsent(student.department, k -> new ArrayList<>())
                       .add(student);
        }
        
        System.out.println("Students by Department:");
        byDepartment.forEach((dept, studentList) -> {
            System.out.println(dept + ":");
            studentList.forEach(s -> System.out.println("  - " + s.name));
        });
        
        // Using Streams (Java 8+)
        Map<String, List<Student>> grouped = students.stream()
            .collect(Collectors.groupingBy(s -> s.department));
    }
}
```

### Example 3: Two-Way Map (Bidirectional)

```java
import java.util.*;

class BidirectionalMap<K, V> {
    private Map<K, V> forward = new HashMap<>();
    private Map<V, K> reverse = new HashMap<>();
    
    public void put(K key, V value) {
        forward.put(key, value);
        reverse.put(value, key);
    }
    
    public V getValue(K key) {
        return forward.get(key);
    }
    
    public K getKey(V value) {
        return reverse.get(value);
    }
    
    public static void main(String[] args) {
        BidirectionalMap<String, Integer> employeeMap = new BidirectionalMap<>();
        
        employeeMap.put("Alice", 101);
        employeeMap.put("Bob", 102);
        employeeMap.put("Charlie", 103);
        
        System.out.println("Employee ID of Alice: " + employeeMap.getValue("Alice"));
        System.out.println("Employee with ID 102: " + employeeMap.getKey(102));
    }
}
```

---

## Best Practices

### ✅ DO:

```java
// Use interface type for declarations
Map<String, Integer> map = new HashMap<>();

// Specify initial capacity for large maps (avoid resizing)
Map<String, Integer> largeMap = new HashMap<>(1000);

// Use getOrDefault to avoid null checks
int value = map.getOrDefault("key", 0);

// Use computeIfAbsent for lazy initialization
map.computeIfAbsent("key", k -> expensiveComputation());

// Use forEach for cleaner iteration (Java 8+)
map.forEach((key, value) -> System.out.println(key + ": " + value));

// Immutable maps for constants (Java 9+)
Map<String, Integer> CONSTANTS = Map.of("MAX", 100, "MIN", 0);
```

### ❌ DON'T:

```java
// Don't modify key objects after adding to map
Person person = new Person("Alice");
map.put(person, "Engineer");
person.name = "Bob";  // ❌ Map is now corrupted

// Don't use mutable objects as keys without proper equals/hashCode
List<String> list = new ArrayList<>();
map.put(list, "value");  // ❌ Dangerous if list is modified

// Don't iterate and modify simultaneously (use Iterator)
for (String key : map.keySet()) {
    map.remove(key);  // ❌ ConcurrentModificationException
}

// Don't add null to TreeMap
treeMap.put(null, "value");  // ❌ NullPointerException

// Don't use Hashtable in new code
Hashtable<String, Integer> table = new Hashtable<>();  // ❌ Use HashMap or ConcurrentHashMap
```

---

## Performance Considerations

### Time Complexity Summary:

| Operation | HashMap | LinkedHashMap | TreeMap | Hashtable |
|-----------|---------|---------------|---------|-----------|
| **get()** | O(1) | O(1) | O(log n) | O(1) |
| **put()** | O(1) | O(1) | O(log n) | O(1) |
| **remove()** | O(1) | O(1) | O(log n) | O(1) |
| **containsKey()** | O(1) | O(1) | O(log n) | O(1) |
| **Iteration** | O(n) | O(n) | O(n) | O(n) |

### Memory Usage:

```java
// HashMap: Moderate memory
// Entry: ~32 bytes + key size + value size

// LinkedHashMap: Higher memory
// Entry: ~48 bytes + key size + value size (doubly-linked list)

// TreeMap: Highest memory
// Node: ~40 bytes + key size + value size + tree overhead
```

### Load Factor Impact:

```java
// Default load factor: 0.75
// Lower load factor (0.5): Faster lookups, more memory
Map<String, Integer> fastMap = new HashMap<>(100, 0.5f);

// Higher load factor (0.9): Slower lookups, less memory
Map<String, Integer> compactMap = new HashMap<>(100, 0.9f);
```

---

## Interview Questions

**Q1: HashMap vs Hashtable vs ConcurrentHashMap?**  
A: HashMap is fastest but not thread-safe, allows one null key. Hashtable is synchronized but legacy, no nulls. ConcurrentHashMap is modern thread-safe alternative with better performance than Hashtable.

**Q2: How does HashMap handle collisions?**  
A: Before Java 8: Uses chaining (linked list in bucket). Java 8+: Uses chaining for up to 8 elements, then converts to balanced tree for better performance.

**Q3: Why is String good for HashMap keys?**  
A: String is immutable, has cached hashCode(), and properly implements equals(). Immutability ensures hash code doesn't change after insertion.

**Q4: What happens if we modify a key object after putting it in HashMap?**  
A: If modification affects equals()/hashCode(), the entry becomes unreachable and the map is corrupted. Always use immutable keys.

**Q5: TreeMap vs HashMap - when to use which?**  
A: Use HashMap for general purpose (O(1) operations). Use TreeMap when you need sorted keys or range operations (O(log n) operations).

**Q6: Can HashMap have duplicate values?**  
A: Yes, values can be duplicate. Only keys must be unique.

**Q7: What is the default capacity and load factor of HashMap?**  
A: Default initial capacity: 16, default load factor: 0.75. Map resizes when size exceeds capacity × load factor.

**Q8: LinkedHashMap vs HashMap?**  
A: LinkedHashMap maintains insertion order using doubly-linked list, slightly slower and uses more memory. HashMap has no ordering but is fastest.

**Q9: How to make HashMap synchronized?**  
A: Use `Collections.synchronizedMap()` or use `ConcurrentHashMap` for better performance.

**Q10: What is fail-fast iterator in Maps?**  
A: Iterator throws ConcurrentModificationException if map is modified during iteration (except through iterator's own remove() method).

---

## Common Pitfalls

### 1. Using Mutable Keys:

```java
❌ BAD:
class MutableKey {
    int value;
    // equals/hashCode based on value
}

MutableKey key = new MutableKey(1);
map.put(key, "Data");
key.value = 2;  // Now map.get(key) returns null!

✅ GOOD:
Use immutable classes (String, Integer) or make your key class immutable.
```

### 2. Not Overriding equals() and hashCode():

```java
❌ BAD:
class Person {
    String name;
    // No equals/hashCode override
}

Person p1 = new Person("Alice");
Person p2 = new Person("Alice");
map.put(p1, "Engineer");
map.get(p2);  // Returns null (different objects)

✅ GOOD:
@Override
public boolean equals(Object o) { ... }

@Override
public int hashCode() { ... }
```

### 3. Concurrent Modification:

```java
❌ BAD:
for (String key : map.keySet()) {
    if (condition) {
        map.remove(key);  // ConcurrentModificationException
    }
}

✅ GOOD:
Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, Integer> entry = it.next();
    if (condition) {
        it.remove();  // Safe removal
    }
}
```

---

## Decision Flowchart

```
Need Key-Value Storage?
    │
    ├─ Need Thread-Safety?
    │   ├─ Yes → ConcurrentHashMap
    │   └─ No ↓
    │
    ├─ Need Sorted Keys?
    │   ├─ Yes → TreeMap
    │   └─ No ↓
    │
    ├─ Need Insertion Order?
    │   ├─ Yes → LinkedHashMap
    │   └─ No ↓
    │
    ├─ Memory-Sensitive Cache?
    │   ├─ Yes → WeakHashMap
    │   └─ No ↓
    │
    └─ Default Choice → HashMap
```

---

## Next Steps

Explore related topics:
1. **ConcurrentHashMap** - Thread-safe HashMap
2. **Custom HashMap Implementation** - Build your own
3. **Hash Functions** - Understanding hashCode()
4. **Collections Utilities** - Synchronized and unmodifiable maps

Then explore:
- [Queues](../04-Queues/) - FIFO and priority collections
- [Collections Framework](../../01-Collections-Framework/) - Overview of all collections
- [Algorithm Patterns](../../04-DSA-Patterns/) - Using maps in algorithms

---

## Summary

| Map Type | Ordering | Performance | Null Support | Best Use Case |
|----------|----------|-------------|--------------|---------------|
| **HashMap** | None | O(1) | One null key | General purpose, fast lookup |
| **LinkedHashMap** | Insertion | O(1) | One null key | Insertion order, LRU cache |
| **TreeMap** | Sorted | O(log n) | No null keys | Sorted keys, range queries |
| **Hashtable** | None | O(1) | No nulls | Legacy code only |
| **WeakHashMap** | None | O(1) | One null key | Memory-sensitive caching |

**Default Choice:** Use **HashMap** for most scenarios. Switch to LinkedHashMap for ordering, TreeMap for sorting, or ConcurrentHashMap for concurrency.
