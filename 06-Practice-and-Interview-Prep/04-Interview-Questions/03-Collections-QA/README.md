# Collections Framework Interview Questions & Answers

## Q1: ArrayList vs LinkedList - When to Use Each?

**Answer:**
Both are implementations of the List interface but with different internal structures. ArrayList uses a dynamic array, while LinkedList uses a doubly-linked list. The choice depends on access patterns and operations.

**Complexity Comparison Table:**

| Operation | ArrayList | LinkedList |
|-----------|-----------|-----------|
| **Access by index** | O(1) | O(n) |
| **Insert at beginning** | O(n) | O(1) |
| **Insert at end** | O(1) amortized | O(1) |
| **Insert in middle** | O(n) | O(n) |
| **Delete from beginning** | O(n) | O(1) |
| **Delete from end** | O(1) | O(1) |
| **Delete from middle** | O(n) | O(n) |
| **Memory** | Less overhead | More overhead (pointers) |

**Code Example:**
```java
List<Integer> arrayList = new ArrayList<>();
arrayList.add(10);      // O(1) amortized
arrayList.get(0);       // O(1) - fast access

List<Integer> linkedList = new LinkedList<>();
linkedList.add(0, 10);  // O(1) - insert at beginning
linkedList.add(20);     // O(1) - insert at end
linkedList.get(0);      // O(n) - slow access
```

**When to Use:**
- **ArrayList**: Frequent access by index, fewer insertions/deletions
- **LinkedList**: Frequent insertions/deletions at beginning, queue-like usage

---

## Q2: HashMap vs Hashtable - Key Differences

**Answer:**
Both implement the Map interface for key-value pairs, but have important differences.

| Feature | HashMap | Hashtable |
|---------|---------|-----------|
| **Synchronization** | Unsynchronized | Synchronized (thread-safe) |
| **Performance** | Faster | Slower (synchronization overhead) |
| **Null Keys/Values** | One null key, null values | No null keys or values |
| **Legacy** | Modern (Java 1.2+) | Legacy (before Collections) |
| **Iteration** | Fail-fast | Fail-safe |
| **When to Use** | Single-threaded | Multi-threaded (but use ConcurrentHashMap) |

**Code Example:**
```java
// HashMap: No synchronization, allows null
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("age", 25);
hashMap.put(null, 0);  // Allowed
hashMap.put("name", null);  // Allowed

// Hashtable: Synchronized, no null allowed
Map<String, Integer> hashtable = new Hashtable<>();
hashtable.put("age", 25);
// hashtable.put(null, 0);  // NullPointerException
// hashtable.put("name", null);  // NullPointerException
```

**Note:** For concurrent access, use `ConcurrentHashMap` instead of `Hashtable`.

---

## Q3: How HashMap Works Internally

**Answer:**
HashMap uses an array of buckets (Node array) and hashing. Each key is hashed to get an index in the array. If two keys hash to the same index (collision), they're stored as a linked list (or tree in Java 8+).

**Internal Structure:**
1. Hash function maps key to index: `index = hash(key) % bucketSize`
2. Store key-value pair at computed index
3. Handle collisions using chaining (linked list) or trees
4. Load factor (default 0.75) triggers resizing when exceeded

**Code Example:**
```java
// HashMap internal working
Map<String, String> map = new HashMap<>();
map.put("John", "Developer");   // hash("John") -> bucket index
map.put("Jane", "Designer");    // hash("Jane") -> bucket index
map.put("John", "Senior Dev");  // Replaces previous value

// Get operation
String value = map.get("John");  // hash("John") -> retrieve value

// Collision handling (Java 8+)
// If hash("key1") == hash("key2"):
// - Before Java 8: LinkedList of entries
// - Java 8+: LinkedList converts to TreeMap if size > 8

// Load factor example
Map<String, Integer> map2 = new HashMap<>(4, 0.75f);  // capacity=4, load factor=0.75
// Resizes when 4 * 0.75 = 3 entries are added
for (int i = 0; i < 5; i++) {
    map2.put("key" + i, i);  // Triggers resize at 4th entry
}
```

**Key Points:**
- Initial capacity: 16, Load factor: 0.75
- Collisions resolved via chaining (linked list)
- Java 8+: Converts to TreeMap if chain length > 8 for performance
- Resizing is O(n) but amortized O(1) for put/get

---

## Q4: How HashSet Maintains Uniqueness

**Answer:**
HashSet internally uses a HashMap to store elements. It checks uniqueness using `hashCode()` and `equals()` methods. For an element to be added, it must have a unique hash code AND not be equal to existing elements.

**Uniqueness Mechanism:**
1. Compute `hashCode()` of element
2. Check if element with same hash exists
3. If exists, use `equals()` to compare
4. If `equals()` returns false, add to collision chain
5. If `equals()` returns true, reject element (duplicate)

**Code Example:**
```java
Set<String> set = new HashSet<>();
set.add("Apple");      // Added
set.add("Banana");     // Added
set.add("Apple");      // Rejected (duplicate)

System.out.println(set.size());  // 2

// Custom class uniqueness
class Person {
    String name;
    int age;
    
    @Override
    public int hashCode() {
        return name.hashCode() + age;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) return false;
        Person p = (Person) obj;
        return name.equals(p.name) && age == p.age;
    }
}

Set<Person> people = new HashSet<>();
people.add(new Person("John", 25));  // Added
people.add(new Person("John", 25));  // Rejected (same hashCode and equals)
```

**Important:** Always override both `hashCode()` and `equals()` when using objects in HashSet.

---

## Q5: TreeMap Sorting - How Does It Work?

**Answer:**
TreeMap implements SortedMap using a Red-Black tree internally, maintaining keys in sorted order. Elements are automatically sorted during insertion based on their natural ordering (Comparable) or a custom comparator.

**Key Characteristics:**
- Implements NavigableMap interface
- Maintains sorted order
- O(log n) for put/get/remove operations
- No null keys (throws NullPointerException)
- Can have null values

**Code Example:**
```java
// Natural ordering (keys must implement Comparable)
Map<String, Integer> tree = new TreeMap<>();
tree.put("Charlie", 85);
tree.put("Alice", 92);
tree.put("Bob", 78);
// Order: Alice, Bob, Charlie (sorted alphabetically)

for (String name : tree.keySet()) {
    System.out.println(name);  // Alice, Bob, Charlie
}

// Custom ordering with Comparator
Map<String, Integer> tree2 = new TreeMap<>((a, b) -> 
    b.compareTo(a)  // Reverse order
);
tree2.put("Charlie", 85);
tree2.put("Alice", 92);
tree2.put("Bob", 78);
// Order: Charlie, Bob, Alice (reverse alphabetical)

// NavigableMap features
Map<String, Integer> tree3 = new TreeMap<>();
tree3.put("A", 1);
tree3.put("C", 3);
tree3.put("E", 5);

String first = tree3.firstKey();  // "A"
String last = tree3.lastKey();    // "E"
String lower = tree3.lowerKey("D");  // "C"
String higher = tree3.higherKey("B");  // "C"
```

**Time Complexity:** O(log n) for all operations due to tree structure.

---

## Q6: Fail-Fast Iterator - What Does It Mean?

**Answer:**
A fail-fast iterator throws `ConcurrentModificationException` if the collection is modified while iterating (except through iterator's own remove method). This detects concurrent modification bugs early.

**How It Works:**
- Iterator maintains a `modCount` snapshot
- Before each operation, checks if `modCount` changed
- If collection modified by another thread/method, throws exception

**Code Example:**
```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

// Fail-fast violation
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String item = iterator.next();
    if (item.equals("B")) {
        list.remove(item);  // ConcurrentModificationException!
    }
}

// Correct way using iterator's remove
Iterator<String> iterator2 = list.iterator();
while (iterator2.hasNext()) {
    String item = iterator2.next();
    if (item.equals("B")) {
        iterator2.remove();  // Safe - uses iterator's method
    }
}

// Also correct: remove before iterating
list.remove("B");
for (String item : list) {
    System.out.println(item);
}
```

**Note:** Fail-fast is best-effort, not guaranteed for all scenarios, especially multi-threaded ones.

---

## Q7: ConcurrentHashMap Usage and Benefits

**Answer:**
ConcurrentHashMap is a thread-safe alternative to HashMap designed for high-concurrency scenarios. It uses bucket-level locking (segment locking) instead of table-level locking, allowing multiple threads to access different buckets simultaneously.

**Key Features:**
- Thread-safe without full synchronization
- Multiple threads can read/write concurrently
- Divides map into segments, each with its own lock
- Does NOT throw ConcurrentModificationException
- No null keys or values

**Code Example:**
```java
// Multi-threaded scenario
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();

// Thread 1
new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        concurrentMap.put("key" + i, i);
    }
}).start();

// Thread 2
new Thread(() -> {
    for (int i = 1000; i < 2000; i++) {
        concurrentMap.put("key" + i, i);
    }
}).start();

// Thread 3 - can iterate safely
new Thread(() -> {
    for (String key : concurrentMap.keySet()) {
        System.out.println(key + ": " + concurrentMap.get(key));
    }
}).start();

// Atomic operations
concurrentMap.putIfAbsent("atomic", 100);
concurrentMap.replace("atomic", 100, 200);
```

**Performance:** Better than Hashtable for concurrent access due to segment-level locking.

---

## Q8: Collections.synchronizedMap vs ConcurrentHashMap

**Answer:**

| Feature | synchronizedMap | ConcurrentHashMap |
|---------|-----------------|-------------------|
| **Synchronization** | Full table lock | Segment/bucket lock |
| **Concurrency** | Only one thread | Multiple threads |
| **Performance** | Lower for high concurrency | Higher |
| **Iteration** | Requires manual sync | Can iterate without sync |
| **ConcurrentModificationException** | Possible | Not thrown |
| **Null Keys/Values** | Allowed | Not allowed |
| **When to Use** | Low concurrency | High concurrency |

**Code Example:**
```java
// synchronizedMap wrapper - less efficient for concurrency
Map<String, Integer> syncMap = Collections.synchronizedMap(
    new HashMap<>()
);
syncMap.put("key1", 100);

// Iteration requires external synchronization
synchronized(syncMap) {
    for (Map.Entry<String, Integer> entry : syncMap.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}

// ConcurrentHashMap - better for high concurrency
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put("key1", 100);

// Iteration safe without external synchronization
for (Map.Entry<String, Integer> entry : concurrentMap.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

**Recommendation:** Use ConcurrentHashMap for multi-threaded applications.

---

## Q9: How compareTo and compare Methods Work

**Answer:**
`compareTo()` is an instance method of Comparable interface comparing current object with another. `compare()` is a static/instance method of Comparator interface comparing two objects. Both return -1 (less), 0 (equal), or 1 (greater).

**Code Example:**
```java
// Comparable: compareTo method
class Student implements Comparable<Student> {
    int rollNo;
    String name;
    
    public Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }
    
    @Override
    public int compareTo(Student other) {
        // Compare by roll number
        return Integer.compare(this.rollNo, other.rollNo);
    }
}

// Using compareTo in Collections.sort
List<Student> students = new ArrayList<>();
students.add(new Student(3, "Charlie"));
students.add(new Student(1, "Alice"));
students.add(new Student(2, "Bob"));

Collections.sort(students);  // Uses compareTo
// Sorted by roll number: 1, 2, 3

// Comparator: compare method
class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);  // Compare by name
    }
}

Collections.sort(students, new NameComparator());
// Sorted by name: Alice, Bob, Charlie

// Lambda expression comparator
Collections.sort(students, (s1, s2) -> 
    s1.name.compareTo(s2.name)
);

// Multiple criteria sorting
Collections.sort(students, (s1, s2) -> {
    int nameCompare = s1.name.compareTo(s2.name);
    return (nameCompare != 0) ? nameCompare : 
           Integer.compare(s1.rollNo, s2.rollNo);
});
```

---

## Q10: Comparable vs Comparator

**Answer:**

| Feature | Comparable | Comparator |
|---------|-----------|-----------|
| **Interface** | java.lang.Comparable | java.util.Comparator |
| **Method** | compareTo(T obj) | compare(T o1, T o2) |
| **Modification** | Modify class (implement interface) | External class (no modification) |
| **Default Sorting** | Natural ordering | Custom ordering |
| **Usage** | Single default comparison | Multiple different comparisons |
| **When to Use** | Single way to sort | Multiple sort criteria |

**Code Example:**
```java
// Comparable: Natural ordering built into class
class Book implements Comparable<Book> {
    String title;
    int pages;
    
    public Book(String title, int pages) {
        this.title = title;
        this.pages = pages;
    }
    
    @Override
    public int compareTo(Book other) {
        return Integer.compare(this.pages, other.pages);
    }
}

List<Book> books = new ArrayList<>();
books.add(new Book("Java Advanced", 800));
books.add(new Book("Python Basics", 300));
books.add(new Book("Spring Guide", 600));

Collections.sort(books);  // Sorts by pages (natural order)

// Comparator: Custom sorting without modifying class
Comparator<Book> byTitle = (b1, b2) -> 
    b1.title.compareTo(b2.title);

Collections.sort(books, byTitle);  // Sorts by title

// Multiple comparators
Comparator<Book> byPages = (b1, b2) -> 
    Integer.compare(b1.pages, b2.pages);

books.stream()
     .sorted(byTitle.thenComparing(byPages))
     .forEach(b -> System.out.println(b.title));
```

---

## Q11: Stream API Usage and Benefits

**Answer:**
Stream API (Java 8+) provides functional-style operations on collections, enabling declarative, lazy evaluation of data. Streams allow functional operations like map, filter, and reduce without explicit loops.

**Key Concepts:**
- **Source** - Collections, arrays, generators
- **Intermediate operations** - filter, map, sorted (return Stream)
- **Terminal operations** - collect, forEach, reduce (consume stream)
- **Lazy evaluation** - Intermediate ops not executed until terminal op

**Code Example:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Traditional approach
List<Integer> evenSquares = new ArrayList<>();
for (Integer num : numbers) {
    if (num % 2 == 0) {
        evenSquares.add(num * num);
    }
}

// Stream approach (declarative and readable)
List<Integer> evenSquares2 = numbers.stream()
    .filter(n -> n % 2 == 0)         // Intermediate operation
    .map(n -> n * n)                 // Intermediate operation
    .collect(Collectors.toList());   // Terminal operation

// Reduce operation
int sum = numbers.stream()
    .filter(n -> n > 5)
    .reduce(0, (a, b) -> a + b);

// ForEach operation
numbers.stream()
    .filter(n -> n % 2 == 0)
    .forEach(System.out::println);

// Multiple terminal operations on same source
long count = numbers.stream()
    .filter(n -> n > 5)
    .count();

Optional<Integer> max = numbers.stream()
    .filter(n -> n < 8)
    .max(Integer::compareTo);
```

**Benefits:**
- Concise, readable code
- Lazy evaluation (efficient)
- Supports parallel processing
- Functional programming style
- Chainable operations

---

## Q12: Functional Interfaces

**Answer:**
A functional interface is an interface with exactly one abstract method. Java provides built-in functional interfaces and allows you to create custom ones. Functional interfaces enable lambda expressions and method references.

**Built-in Functional Interfaces:**

| Interface | Method | Purpose |
|-----------|--------|---------|
| **Predicate<T>** | test(T) | Condition testing |
| **Function<T,R>** | apply(T) | Transform T to R |
| **Consumer<T>** | accept(T) | Consume T |
| **Supplier<T>** | get() | Supply T |
| **BiFunction<T,U,R>** | apply(T,U) | Transform T,U to R |

**Code Example:**
```java
// Built-in functional interfaces with lambdas
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));  // true

Function<Integer, Integer> square = n -> n * n;
System.out.println(square.apply(5));  // 25

Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello");

Supplier<String> greeting = () -> "Hello World";
System.out.println(greeting.get());

BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
System.out.println(add.apply(3, 4));  // 7

// Custom functional interface
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

MathOperation multiply = (a, b) -> a * b;
System.out.println(multiply.operate(3, 4));  // 12

// Stream with functional interfaces
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.stream()
    .filter(isEven)          // Predicate
    .map(square)             // Function
    .forEach(printer);       // Consumer
```

**Important:** `@FunctionalInterface` annotation ensures interface has exactly one abstract method.

---

## Summary

**Key Takeaways:**

1. **Collections Choice:** ArrayList for random access, LinkedList for frequent insertions/deletions
2. **Map Selection:** HashMap for single-threaded, ConcurrentHashMap for multi-threaded
3. **Sorting:** Comparable for natural order, Comparator for custom ordering
4. **Thread Safety:** Hashtable is legacy, use ConcurrentHashMap for concurrent access
5. **Stream API:** Functional, lazy, and efficient for data transformations
6. **Functional Interfaces:** Enable lambda expressions and functional programming style

**Next Steps:**
- Master Stream API for data processing
- Understand concurrent collections for multi-threaded applications
- Learn custom comparators for complex sorting scenarios
