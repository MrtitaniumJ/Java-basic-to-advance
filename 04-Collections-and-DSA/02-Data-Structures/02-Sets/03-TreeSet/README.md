# TreeSet in Java

## Simple Explanation with Real-World Analogy

Imagine a **library's card catalog** where books are automatically **sorted alphabetically by title**. When you add a new book, the system finds the correct position and inserts it so the list remains sorted. When you retrieve books, they come out in alphabetical order. This is exactly how **TreeSet** works - it automatically maintains elements in **sorted order** as you add or remove them.

Unlike HashSet (which prioritizes speed) or LinkedHashSet (which preserves insertion order), **TreeSet** prioritizes **keeping everything sorted**. It trades some performance for the benefit of maintaining a **naturally ordered collection**. Under the hood, it uses a **Red-Black Tree** (a self-balancing binary search tree) to maintain this ordering.

## Internal Structure: How TreeSet Works

```
┌─────────────────────────────────────────────┐
│     TreeSet Red-Black Tree Structure        │
├─────────────────────────────────────────────┤
│                    [5] (root)               │
│                   /         \               │
│                [3]           [8]            │
│               /   \         /   \           │
│            [1]    [4]     [7]   [10]        │
│           /                              \  │
│        [null]                          [null]
│                                              │
│  All elements are sorted: 1, 3, 4, 5, 7, 8,10│
│  Height is always log(n) - balanced        │
│  No duplicates allowed                     │
└─────────────────────────────────────────────┘
```

**TreeSet** is based on a **NavigableMap** backed by a **TreeMap**, which implements a **Red-Black Tree**. Here's how it works:

1. **Binary Search Tree**: Each element has at most 2 children (left < parent < right)
2. **Balanced**: Tree automatically rebalances using Red-Black Tree algorithm
3. **Ordering**: Elements ordered using `Comparator` or their natural order (`Comparable`)
4. **Sorted Traversal**: In-order traversal gives elements in sorted order
5. **O(log n) Operations**: All operations take O(log n) time due to tree height

## Constructors and Creation Methods

```java
// 1. Default constructor - uses natural ordering (Comparable)
TreeSet<String> set1 = new TreeSet<>();

// 2. Constructor with custom Comparator
TreeSet<String> set2 = new TreeSet<>(Comparator.reverseOrder());

// 3. Constructor with custom Comparator (case-insensitive for strings)
TreeSet<String> set3 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

// 4. Constructor with collection
List<Integer> list = Arrays.asList(5, 2, 8, 1, 9);
TreeSet<Integer> set4 = new TreeSet<>(list);

// 5. Constructor with another SortedSet
TreeSet<Integer> set5 = new TreeSet<>(set4);

// 6. Custom Comparator for objects
TreeSet<Person> set6 = new TreeSet<>((p1, p2) -> {
    if (p1.age != p2.age) {
        return p1.age - p2.age; // Sort by age
    }
    return p1.name.compareTo(p2.name); // Then by name
});

// 7. Reverse order TreeSet
TreeSet<Integer> set7 = new TreeSet<>(Collections.reverseOrder());
```

## Custom Comparators

```java
public class CustomComparators {
    public static void main(String[] args) {
        comparingNumbers();
        comparingStrings();
        comparingObjects();
    }
    
    // 1. Custom numeric comparator
    static void comparingNumbers() {
        System.out.println("=== Custom Numeric Comparator ===");
        
        // Sort by absolute value
        TreeSet<Integer> set = new TreeSet<>((a, b) -> {
            int cmp = Integer.compare(Math.abs(a), Math.abs(b));
            if (cmp == 0) return Integer.compare(a, b);
            return cmp;
        });
        
        set.addAll(Arrays.asList(5, -3, 2, -7, 4));
        System.out.println("By absolute value: " + set);
        // Output: [-2, 2, -3, 3, -5, 5] or similar grouping
    }
    
    // 2. String comparators
    static void comparingStrings() {
        System.out.println("\n=== String Comparators ===");
        
        // 1. Natural order
        TreeSet<String> natural = new TreeSet<>();
        natural.addAll(Arrays.asList("zebra", "apple", "mango", "banana"));
        System.out.println("Natural: " + natural);
        // Output: [apple, banana, mango, zebra]
        
        // 2. Reverse order
        TreeSet<String> reverse = new TreeSet<>(Collections.reverseOrder());
        reverse.addAll(Arrays.asList("zebra", "apple", "mango", "banana"));
        System.out.println("Reverse: " + reverse);
        // Output: [zebra, mango, banana, apple]
        
        // 3. Case-insensitive
        TreeSet<String> caseInsensitive = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        caseInsensitive.addAll(Arrays.asList("Zebra", "apple", "MANGO", "bAnAnA"));
        System.out.println("Case-insensitive: " + caseInsensitive);
        // Output: [apple, bAnAnA, MANGO, Zebra]
        
        // 4. By length
        TreeSet<String> byLength = new TreeSet<>((s1, s2) -> {
            int cmp = Integer.compare(s1.length(), s2.length());
            if (cmp == 0) return s1.compareTo(s2);
            return cmp;
        });
        byLength.addAll(Arrays.asList("a", "bb", "ccc", "dd", "e"));
        System.out.println("By length: " + byLength);
        // Output: [a, e, bb, dd, ccc]
    }
    
    // 3. Custom object comparator
    static void comparingObjects() {
        System.out.println("\n=== Object Comparators ===");
        
        class Employee implements Comparable<Employee> {
            String name;
            double salary;
            
            Employee(String name, double salary) {
                this.name = name;
                this.salary = salary;
            }
            
            @Override
            public int compareTo(Employee other) {
                return this.name.compareTo(other.name);
            }
            
            @Override
            public String toString() {
                return name + "(" + salary + ")";
            }
        }
        
        TreeSet<Employee> bySalary = new TreeSet<>((e1, e2) -> {
            int cmp = Double.compare(e1.salary, e2.salary);
            if (cmp == 0) return e1.name.compareTo(e2.name);
            return cmp;
        });
        
        bySalary.add(new Employee("Alice", 50000));
        bySalary.add(new Employee("Bob", 60000));
        bySalary.add(new Employee("Charlie", 55000));
        
        System.out.println("By salary: " + bySalary);
        // Output: [Alice(50000), Charlie(55000), Bob(60000)]
    }
}
```

## Core Operations with Performance Analysis

### Add Operation
```java
TreeSet<Integer> numbers = new TreeSet<>();

// Add single element - O(log n)
numbers.add(50);
numbers.add(30);
numbers.add(70);
numbers.add(20);
numbers.add(40);
numbers.add(60);
numbers.add(80);

System.out.println("Numbers (auto-sorted): " + numbers);
// Output: [20, 30, 40, 50, 60, 70, 80]

// Add returns true/false
boolean added = numbers.add(25);
System.out.println("Added 25: " + added); // true
System.out.println("After adding 25: " + numbers);
// Output: [20, 25, 30, 40, 50, 60, 70, 80]

// Try to add duplicate
boolean duplicate = numbers.add(50);
System.out.println("Added duplicate 50: " + duplicate); // false

// Add multiple (TreeSet will sort them)
numbers.addAll(Arrays.asList(35, 55, 75));
System.out.println("After addAll: " + numbers);
// Output: [20, 25, 30, 35, 40, 50, 55, 60, 70, 75, 80]
```

### Remove Operation
```java
TreeSet<String> fruits = new TreeSet<>(
    Arrays.asList("apple", "banana", "cherry", "date", "elderberry")
);

System.out.println("Original: " + fruits);
// Output: [apple, banana, cherry, date, elderberry]

// Remove specific element - O(log n)
boolean removed = fruits.remove("cherry");
System.out.println("Removed cherry: " + removed); // true
System.out.println("After remove: " + fruits);
// Output: [apple, banana, date, elderberry]

// Remove first element
String first = fruits.pollFirst(); // Also returns the element
System.out.println("Removed first: " + first); // apple
System.out.println("After pollFirst: " + fruits);
// Output: [banana, date, elderberry]

// Remove last element
String last = fruits.pollLast();
System.out.println("Removed last: " + last); // elderberry
System.out.println("After pollLast: " + fruits);
// Output: [banana, date]

// Remove multiple
fruits.removeAll(Arrays.asList("banana", "date"));
System.out.println("After removeAll: " + fruits);
// Output: []
```

### Contains and Range Operations
```java
TreeSet<Integer> range = new TreeSet<>(
    Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90)
);

// Contains - O(log n)
System.out.println("Contains 40: " + range.contains(40)); // true
System.out.println("Contains 45: " + range.contains(45)); // false

// Range operations (subsets in range)
SortedSet<Integer> subset = range.subSet(30, 70); // [30, 70)
System.out.println("subSet(30, 70): " + subset);
// Output: [30, 40, 50, 60]

// Head set (all elements < 50)
SortedSet<Integer> head = range.headSet(50);
System.out.println("headSet(50): " + head);
// Output: [10, 20, 30, 40]

// Tail set (all elements >= 50)
SortedSet<Integer> tail = range.tailSet(50);
System.out.println("tailSet(50): " + tail);
// Output: [50, 60, 70, 80, 90]

// Size and empty
System.out.println("Size: " + range.size()); // 9
System.out.println("isEmpty: " + range.isEmpty()); // false
```

## NavigableSet Operations (Advanced)

```java
NavigableSet<Integer> nav = new TreeSet<>(
    Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90)
);

// Floor (greatest element <= value)
System.out.println("floor(35): " + nav.floor(35)); // 30
System.out.println("floor(40): " + nav.floor(40)); // 40

// Ceiling (least element >= value)
System.out.println("ceiling(35): " + nav.ceiling(35)); // 40
System.out.println("ceiling(40): " + nav.ceiling(40)); // 40

// Lower (strictly less than)
System.out.println("lower(40): " + nav.lower(40)); // 30

// Higher (strictly greater than)
System.out.println("higher(40): " + nav.higher(40)); // 50

// Get first and last
System.out.println("first: " + nav.first()); // 10
System.out.println("last: " + nav.last()); // 90

// Descending iterator
System.out.println("Descending order:");
nav.descendingIterator().forEachRemaining(System.out::println);
// Output: 90, 80, 70, 60, 50, 40, 30, 20, 10

// Descending set
NavigableSet<Integer> desc = nav.descendingSet();
System.out.println("Descending set: " + desc);
// Output: [90, 80, 70, 60, 50, 40, 30, 20, 10]
```

## Iteration Behavior

```java
TreeSet<String> sorted = new TreeSet<>(
    Arrays.asList("dog", "cat", "elephant", "bear", "antelope")
);

// 1. Forward iteration (ascending order - guaranteed)
System.out.println("=== Forward Iterator ===");
for (String animal : sorted) {
    System.out.print(animal + " ");
}
// Output: antelope bear cat dog elephant

// 2. Backward iteration
System.out.println("\n=== Backward Iterator ===");
sorted.descendingIterator().forEachRemaining(animal -> 
    System.out.print(animal + " ")
);
// Output: elephant dog cat bear antelope

// 3. Stream (maintains sorted order)
System.out.println("\n=== Stream API ===");
sorted.stream()
    .filter(a -> a.length() > 3)
    .forEach(a -> System.out.print(a + " "));
// Output: antelope elephant

// 4. Reverse stream
System.out.println("\n=== Reverse Stream ===");
sorted.descendingSet().stream()
    .forEach(a -> System.out.print(a + " "));
// Output: elephant dog cat bear antelope
```

## Performance Characteristics and Complexity Analysis

```java
public class TreeSetPerformance {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>();
        
        // Add 1,000,000 elements
        long startAdd = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            set.add(i);
        }
        long endAdd = System.nanoTime();
        double addTime = (endAdd - startAdd) / 1_000_000.0;
        System.out.printf("Add 1M elements: %.2f ms\n", addTime);
        
        // Contains search - O(log n)
        long startSearch = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            set.contains(i);
        }
        long endSearch = System.nanoTime();
        double searchTime = (endSearch - startSearch) / 1_000_000.0;
        System.out.printf("Search 100K elements: %.2f ms\n", searchTime);
        
        // Iteration (guaranteed sorted)
        long startIter = System.nanoTime();
        set.forEach(i -> {});
        long endIter = System.nanoTime();
        double iterTime = (endIter - startIter) / 1_000_000.0;
        System.out.printf("Iterate all 1M: %.2f ms\n", iterTime);
        
        // Range query
        long startRange = System.nanoTime();
        SortedSet<Integer> range = set.subSet(100000, 200000);
        long endRange = System.nanoTime();
        double rangeTime = (endRange - startRange) / 1_000_000.0;
        System.out.printf("Range query: %.2f ms, result size: %d\n", 
            rangeTime, range.size());
        
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
// Add 1M elements: 2000-3000 ms
// Search 100K elements: 500-800 ms
// Iterate all 1M: 100-200 ms
// Range query: 10-50 ms
// Remove 100K elements: 500-800 ms
```

| Operation | Time Complexity | Notes |
|-----------|---|---|
| Add | O(log n) | Tree height at most 2*log(n) |
| Remove | O(log n) | Tree rebalancing included |
| Contains | O(log n) | Binary search through tree |
| Clear | O(n) | Must visit all nodes |
| First/Last | O(1) | Direct access to min/max |
| Floor/Ceiling | O(log n) | Tree search |
| SubSet | O(log n) | Create view |
| Iteration | O(n) | In-order traversal |

## Real-World Code Examples

```java
public class TreeSetRealWorld {
    public static void main(String[] args) {
        // Example 1: Leaderboard (sorted scores)
        leaderboardExample();
        
        // Example 2: Range queries
        rangeQueryExample();
        
        // Example 3: AutoComplete with sorting
        autoCompleteExample();
        
        // Example 4: Finding percentiles
        percentileExample();
    }
    
    // Example 1: Game leaderboard
    static void leaderboardExample() {
        System.out.println("=== Leaderboard ===");
        
        class Score implements Comparable<Score> {
            String player;
            int score;
            
            Score(String player, int score) {
                this.player = player;
                this.score = score;
            }
            
            @Override
            public int compareTo(Score other) {
                // Descending order
                return Integer.compare(other.score, this.score);
            }
            
            @Override
            public String toString() {
                return player + ": " + score;
            }
        }
        
        TreeSet<Score> leaderboard = new TreeSet<>();
        leaderboard.add(new Score("Alice", 9500));
        leaderboard.add(new Score("Bob", 8700));
        leaderboard.add(new Score("Charlie", 9200));
        leaderboard.add(new Score("Diana", 9800));
        
        System.out.println("Top scores:");
        leaderboard.stream()
            .limit(3)
            .forEach(s -> System.out.println("  " + s));
    }
    
    // Example 2: Range queries
    static void rangeQueryExample() {
        System.out.println("\n=== Range Query ===");
        TreeSet<Integer> salaries = new TreeSet<>(Arrays.asList(
            30000, 35000, 40000, 45000, 50000, 55000, 60000, 70000, 80000
        ));
        
        // Find salaries in range [45000, 65000)
        SortedSet<Integer> inRange = salaries.subSet(45000, 65000);
        System.out.println("Salaries between 45K-65K: " + inRange);
        
        // Count salaries >= 50000
        long count = salaries.tailSet(50000).size();
        System.out.println("Salaries >= 50K: " + count);
    }
    
    // Example 3: AutoComplete
    static void autoCompleteExample() {
        System.out.println("\n=== AutoComplete ===");
        TreeSet<String> words = new TreeSet<>(Arrays.asList(
            "apple", "application", "apply", "apricot", "apt",
            "banana", "band", "bank"
        ));
        
        String prefix = "ap";
        // Find all words starting with prefix
        SortedSet<String> matches = words.subSet(
            prefix,
            prefix + Character.MAX_VALUE
        );
        System.out.println("Words starting with '" + prefix + "': " + matches);
    }
    
    // Example 4: Finding percentiles
    static void percentileExample() {
        System.out.println("\n=== Percentile Calculation ===");
        TreeSet<Integer> scores = new TreeSet<>();
        for (int i = 1; i <= 100; i++) {
            scores.add(i);
        }
        
        int percentile25 = scores.floor((int)(scores.size() * 0.25));
        int percentile50 = scores.floor((int)(scores.size() * 0.50));
        int percentile75 = scores.floor((int)(scores.size() * 0.75));
        
        System.out.println("25th percentile: " + percentile25);
        System.out.println("50th percentile: " + percentile50);
        System.out.println("75th percentile: " + percentile75);
    }
}
```

## Null Handling and Thread Safety

```java
public class TreeSetNullSafety {
    public static void main(String[] args) {
        // Null Handling
        TreeSet<String> set = new TreeSet<>();
        
        try {
            set.add(null); // Will throw NullPointerException
        } catch (NullPointerException e) {
            System.out.println("TreeSet doesn't allow null!");
        }
        
        // Custom comparator that handles null
        TreeSet<String> nullSafeSet = new TreeSet<>((a, b) -> {
            if (a == null && b == null) return 0;
            if (a == null) return -1;
            if (b == null) return 1;
            return a.compareTo(b);
        });
        
        nullSafeSet.add("value");
        nullSafeSet.add(null);
        System.out.println("Null-safe set: " + nullSafeSet);
        
        // Thread Safety - TreeSet is NOT synchronized
        NavigableSet<Integer> syncSet = Collections.synchronizedNavigableSet(
            new TreeSet<>()
        );
        
        // Multi-threaded insertion
        for (int i = 1; i <= 3; i++) {
            int threadId = i;
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    syncSet.add(threadId * 10 + j);
                }
            }).start();
        }
    }
}
```

## When to Use TreeSet

✅ **Use TreeSet when:**
- You need **sorted data** automatically maintained
- You need **range queries** (elements between X and Y)
- You need **navigational operations** (floor, ceiling, etc.)
- You need **percentile calculations** or **medians**
- You're implementing **leaderboards** or **ranking systems**
- You need **first/last element** access (O(1))

❌ **Don't use TreeSet when:**
- You need **maximum speed** (use HashSet)
- You need **insertion order preservation** (use LinkedHashSet)
- You have **null values** (TreeSet doesn't allow null)
- You don't care about **ordering** (overhead wasted)
- You need **constant space overhead** (TreeSet uses extra for pointers)

## Comparison with Other Set Implementations

| Feature | HashSet | LinkedHashSet | TreeSet | EnumSet |
|---------|---------|---------------|---------|---------|
| Order | Unordered | Insertion | Sorted | Natural |
| Add | O(1) avg | O(1) avg | O(log n) | O(1) |
| Remove | O(1) avg | O(1) avg | O(log n) | O(1) |
| Contains | O(1) avg | O(1) avg | O(log n) | O(1) |
| Iteration | Random | Insertion | Sorted | Natural |
| Range queries | Not | Not | Yes | No |
| Null allowed | Yes | Yes | No | No |
| Memory | 48 bytes | 60 bytes | More | Minimal |

## Key Takeaways

- **TreeSet** provides **automatically sorted unique elements**
- Uses **Red-Black Tree** internally for balanced O(log n) operations
- Perfect for **applications requiring sorted data** (leaderboards, rankings, range queries)
- Offers advanced **NavigableSet** operations (floor, ceiling, subSet, etc.)
- More overhead than HashSet but worth it for **sorted access patterns**
- **No null elements** allowed (will throw NullPointerException)
- Not thread-safe - wrap with `Collections.synchronizedNavigableSet()` if needed
