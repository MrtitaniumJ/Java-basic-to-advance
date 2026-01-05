# HashMap: Complete Guide

## Real-World Analogy
Imagine a library with a large hash table (card catalog system). When you want to find a book, instead of searching linearly through all books, the librarian uses the ISBN number (key) to quickly locate the book (value) in a specific section. If multiple books hash to the same section (collision), they're stored in a list within that section. This is exactly how HashMap works—it trades some memory for exceptional speed.

## Internal Structure & Mechanism
HashMap uses **hash table** data structure internally with **bucket-based storage**:
- **Array of Buckets**: A fixed-size array where each index is a bucket
- **Hash Function**: Converts keys to array indices using `key.hashCode() % array.length`
- **Collision Resolution**: Uses **separate chaining** (Java 8+) with linked lists and red-black trees
- **Load Factor**: Default 0.75—when occupied buckets exceed 75% capacity, the array doubles

### Visual Bucket Structure
```
[Index: 0] → [Key:Value] → [Key:Value] → [Key:Value]
[Index: 1] → [Key:Value]
[Index: 2] → null
[Index: 3] → [Key:Value] → [Key:Value]
...
[Index: 15] → [Key:Value]
```

When size exceeds `capacity * loadFactor`, internal array is resized (rehashed).

## Key Storage Mechanism
- **Keys**: Stored as hash entries; must be immutable (recommended)
- **Values**: Can be any object, including null
- **Hash Code Computation**: `hash = key.hashCode() & 0x7fffffff`, then `index = hash % capacity`
- **Equality Check**: Uses `equals()` method; both `hashCode()` and `equals()` must be overridden together
- **Null Key**: HashMap allows exactly ONE null key, null values unlimited

## Constructors & Creation Methods

```java
// Default constructor (capacity=16, loadFactor=0.75)
HashMap<String, Integer> map1 = new HashMap<>();

// With initial capacity
HashMap<String, Integer> map2 = new HashMap<>(32);

// With capacity and load factor
HashMap<String, Integer> map3 = new HashMap<>(64, 0.8f);

// Copy constructor
Map<String, Integer> existing = Map.of("a", 1, "b", 2);
HashMap<String, Integer> map4 = new HashMap<>(existing);

// Using Java 9+ factory methods
Map<String, Integer> immutable = Map.of("x", 10, "y", 20);
```

## Core Operations with Performance

| Operation | Time Complexity | Details |
|-----------|-----------------|---------|
| `put(K, V)` | O(1) average | O(n) worst case (all collisions) |
| `get(K)` | O(1) average | O(n) worst case |
| `remove(K)` | O(1) average | O(n) worst case |
| `containsKey(K)` | O(1) average | O(n) worst case |
| `containsValue(V)` | O(n) | Linear scan required |
| `clear()` | O(n) | Clears all entries |
| `size()` | O(1) | Maintains size counter |

## Load Factor & Resizing Strategy
- **Default Load Factor**: 0.75 (balance between time and space)
- **When Resizing Occurs**: `size > capacity * loadFactor`
- **New Capacity**: Previous capacity × 2 (always power of 2)
- **Rehashing Cost**: O(n) for redistribution, but amortized O(1) per insertion
- **Custom Load Factor**: Pass to constructor; smaller = more memory, faster; larger = less memory, more collisions

## Collision Handling
- **Before Java 8**: Separate chaining with linked lists only
- **Java 8+**: Uses linked list until ~8 collisions, then converts to red-black tree
- **Tree Threshold**: TREEIFY_THRESHOLD = 8, UNTREEIFY_THRESHOLD = 6
- **Advantages**: Reduces collision impact from O(n) to O(log n)

## Iteration Methods

```java
// Four main iteration approaches
HashMap<String, Integer> map = new HashMap<>();
map.put("apple", 1);
map.put("banana", 2);
map.put("cherry", 3);

// 1. entrySet() - Most efficient
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
}

// 2. keySet() - Iterate only keys
for (String key : map.keySet()) {
    Integer value = map.get(key);
}

// 3. values() - Iterate only values
for (Integer value : map.values()) {
    // process value
}

// 4. Iterator pattern
Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
while (iterator.hasNext()) {
    Map.Entry<String, Integer> entry = iterator.next();
    // process
}

// 5. forEach (Java 8+)
map.forEach((key, value) -> System.out.println(key + ": " + value));
```

## When to Use HashMap
✅ **Use when:**
- You need O(1) average-case lookup and insertion
- Order of elements doesn't matter
- You want the fastest key-value storage
- Working with null keys/values
- Need high-performance caching

❌ **Avoid when:**
- Need sorted order (use TreeMap)
- Need insertion order (use LinkedHashMap)
- Require thread safety without synchronization (use ConcurrentHashMap)
- Working with weak references (use WeakHashMap)

## Common Use Cases with Examples

### 1. Caching User Sessions
```java
HashMap<String, UserSession> sessionCache = new HashMap<>();

class UserSession {
    String userId;
    String token;
    long timestamp;
    
    UserSession(String userId, String token) {
        this.userId = userId;
        this.token = token;
        this.timestamp = System.currentTimeMillis();
    }
}

// Store session
String sessionId = UUID.randomUUID().toString();
sessionCache.put(sessionId, new UserSession("user123", "token456"));

// Retrieve
UserSession session = sessionCache.get(sessionId);
if (session != null) {
    System.out.println("Session found: " + session.userId);
}

// Remove expired
sessionCache.remove(sessionId);
```

### 2. Frequency Counter
```java
public static Map<Integer, Integer> countFrequency(int[] arr) {
    HashMap<Integer, Integer> frequencyMap = new HashMap<>();
    
    for (int num : arr) {
        frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
    }
    
    return frequencyMap;
}

// Usage
int[] numbers = {1, 2, 2, 3, 3, 3, 4, 4, 4, 4};
Map<Integer, Integer> freq = countFrequency(numbers);
freq.forEach((num, count) -> System.out.println(num + " appears " + count + " times"));
```

### 3. Word Index Mapping
```java
HashMap<String, List<Integer>> wordIndex = new HashMap<>();

String text = "hello world hello java world";
String[] words = text.split(" ");

for (int i = 0; i < words.length; i++) {
    wordIndex.computeIfAbsent(words[i], k -> new ArrayList<>()).add(i);
}

wordIndex.forEach((word, indices) -> 
    System.out.println(word + " appears at indices: " + indices)
);
```

### 4. Group Students by Grade
```java
class Student {
    String name;
    String grade;
    
    Student(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }
}

HashMap<String, List<Student>> gradeGroups = new HashMap<>();
List<Student> students = Arrays.asList(
    new Student("Alice", "A"),
    new Student("Bob", "B"),
    new Student("Charlie", "A"),
    new Student("David", "B")
);

students.forEach(student ->
    gradeGroups.computeIfAbsent(student.grade, k -> new ArrayList<>())
               .add(student)
);

gradeGroups.forEach((grade, groupedStudents) -> {
    System.out.println("Grade " + grade + ": ");
    groupedStudents.forEach(s -> System.out.println("  - " + s.name));
});
```

### 5. Two-Sum Problem
```java
public static int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> map = new HashMap<>();
    
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        
        map.put(nums[i], i);
    }
    
    return new int[]{};
}

// Usage
int[] numbers = {2, 7, 11, 15};
int[] result = twoSum(numbers, 9);
System.out.println("Indices: " + Arrays.toString(result)); // [0, 1]
```

### 6. Cache with Expiration (Using putIfAbsent and merge)
```java
class CacheEntry<T> {
    T value;
    long expiryTime;
    
    CacheEntry(T value, long ttlMillis) {
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
    }
    
    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

HashMap<String, CacheEntry<String>> cache = new HashMap<>();

// Put with expiration (5 seconds)
cache.put("key1", new CacheEntry<>("value1", 5000));

// Get with expiration check
String cachedValue = null;
if (cache.containsKey("key1")) {
    CacheEntry<String> entry = cache.get("key1");
    if (!entry.isExpired()) {
        cachedValue = entry.value;
    } else {
        cache.remove("key1");
    }
}
```

## Edge Cases & Gotchas

### 1. Mutable Key Modification
```java
class MutableKey {
    int id;
    MutableKey(int id) { this.id = id; }
    
    @Override
    public int hashCode() { return id; }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof MutableKey && ((MutableKey) o).id == id;
    }
}

// ❌ DANGER: Modifying key after insertion
MutableKey key = new MutableKey(1);
HashMap<MutableKey, String> map = new HashMap<>();
map.put(key, "value1");
key.id = 2;  // Changes hash code!
System.out.println(map.get(new MutableKey(1))); // null - LOST!
```

### 2. Null Key/Value Handling
```java
HashMap<String, String> map = new HashMap<>();
map.put(null, "null key");
map.put("nullValue", null);

// Null key uniqueness
map.put(null, "updated");

System.out.println(map.get(null)); // "updated"
System.out.println(map.containsKey(null)); // true
System.out.println(map.get("nullValue")); // null
```

### 3. hashCode() Consistency
```java
class BadKey {
    int value;
    
    BadKey(int value) { this.value = value; }
    
    @Override
    public int hashCode() {
        return (int) System.currentTimeMillis(); // ❌ WRONG!
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof BadKey && ((BadKey) o).value == value;
    }
}

// This will cause data loss and lookup failures
```

## Thread Safety & Synchronization
- **HashMap is NOT thread-safe**
- **ConcurrentHashMap**: Segment-level locking for concurrent access
- **Collections.synchronizedMap()**: Wraps HashMap with synchronization
- **Alternative**: Use `ConcurrentHashMap` for multi-threaded environments

```java
// Synchronized wrapper (less efficient)
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Better for concurrent use
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

// Manual synchronization
Map<String, Integer> unsafeMap = new HashMap<>();
synchronized(unsafeMap) {
    unsafeMap.put("key", 1);
}
```

## Comparison with Other Map Implementations

| Feature | HashMap | TreeMap | LinkedHashMap | Hashtable |
|---------|---------|---------|---------------|-----------|
| **Ordering** | No | Sorted (natural/custom) | Insertion order | No |
| **Time Complexity (get)** | O(1) avg | O(log n) | O(1) avg | O(1) avg |
| **Null keys** | 1 allowed | Not allowed | 1 allowed | Not allowed |
| **Thread-safe** | No | No | No | Yes (legacy) |
| **Best use case** | General purpose | Sorted keys needed | Insertion order needed | Legacy code |

## Performance Benchmarks

```java
public class HashMapBenchmark {
    public static void main(String[] args) {
        int size = 1_000_000;
        
        // Test put operation
        long start = System.nanoTime();
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(i, "value_" + i);
        }
        long putTime = System.nanoTime() - start;
        
        // Test get operation
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            map.get(i);
        }
        long getTime = System.nanoTime() - start;
        
        // Test iteration
        start = System.nanoTime();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            String val = entry.getValue();
        }
        long iterationTime = System.nanoTime() - start;
        
        System.out.println("Put 1M items: " + (putTime / 1_000_000) + " ms");
        System.out.println("Get 1M items: " + (getTime / 1_000_000) + " ms");
        System.out.println("Iterate 1M items: " + (iterationTime / 1_000_000) + " ms");
    }
}
```

## Advanced Features (Java 8+)

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);

// putIfAbsent - Atomic operation
map.putIfAbsent("c", 3);
map.putIfAbsent("a", 10); // Won't overwrite existing

// getOrDefault
Integer value = map.getOrDefault("d", 0);

// compute - Compute new value based on key and old value
map.compute("a", (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);

// merge - Combine old and new value
map.merge("a", 5, Integer::sum);

// replaceAll
map.replaceAll((key, value) -> value * 2);

// removeIf
map.entrySet().removeIf(entry -> entry.getValue() < 2);
```

## Key Takeaways
1. HashMap provides O(1) average-case performance—fastest for unordered key-value storage
2. Always override both `hashCode()` and `equals()` for custom keys
3. Keep keys immutable to avoid data loss
4. Java 8+ uses red-black trees for collision chains (threshold = 8)
5. Load factor 0.75 is optimal balance
6. Use ConcurrentHashMap for multi-threaded scenarios
7. Iteration via `entrySet()` is most efficient
8. HashMap allows null key and values

