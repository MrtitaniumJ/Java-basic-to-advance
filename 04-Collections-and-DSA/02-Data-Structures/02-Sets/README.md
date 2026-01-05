# Sets in Java - Unique Element Collections

## Simple Explanation

Think of a **Set** as a **bag of unique items**:
- **No duplicates** allowed (like a collection of unique stamps)
- **Unordered** in HashSet (random arrangement)
- **Ordered** in LinkedHashSet (insertion order) and TreeSet (sorted order)
- Fast **membership checking** (contains())

### Real-World Analogy
Imagine a **set of unique playing cards**:
- Each card appears only once (no duplicates)
- You can quickly check if a specific card exists
- HashSet: Cards in random pile
- LinkedHashSet: Cards in the order you added them
- TreeSet: Cards sorted by rank and suit

## Professional Definition

A **Set** is a collection that contains no duplicate elements and models the mathematical set abstraction. Sets determine equality using the `equals()` method and provide efficient membership testing operations. The Set interface extends Collection and adds the restriction that duplicate elements are prohibited.

## Set Interface Hierarchy

```
Collection (Interface)
    ↓
Set (Interface)
    ├── HashSet (Class) - Hash table implementation, O(1) operations
    ├── LinkedHashSet (Class) - Hash table + linked list, maintains insertion order
    ├── TreeSet (Class) - Red-Black tree, sorted order, O(log n) operations
    └── EnumSet (Abstract Class) - Specialized for enum types, extremely efficient
```

## Types of Set Implementations

### Overview Comparison:

| Feature | HashSet | LinkedHashSet | TreeSet | EnumSet |
|---------|---------|---------------|---------|---------|
| **Ordering** | None (random) | Insertion order | Natural/Custom sorted | Enum declaration order |
| **Add/Remove/Contains** | O(1) | O(1) | O(log n) | O(1) |
| **Internal Structure** | Hash Table | Hash Table + Linked List | Red-Black Tree | Bit Vector |
| **Allows Null** | Yes (one null) | Yes (one null) | No | No |
| **Thread-Safe** | No | No | No | No |
| **Best Use Case** | Fast lookup, no order needed | Fast lookup + insertion order | Sorted data, range operations | Enum-only collections |
| **Memory** | Moderate | Higher (linked list overhead) | Higher (tree nodes) | Very low (bit flags) |

## 1. HashSet - Fast Unordered Set

### Key Characteristics:
- ✅ **Fastest operations** - O(1) add, remove, contains
- ✅ **Memory efficient** - Uses hash table
- ✅ **Best for lookups** - Quick membership testing
- ❌ **No ordering** - Elements in random order
- ❌ **One null allowed** - Can store one null element

### Internal Working:
```
Hash Table Structure:

Bucket 0: null
Bucket 1: → ["Apple"] → null
Bucket 2: → ["Banana"] → ["Cherry"] → null  (collision chain)
Bucket 3: → ["Date"] → null
Bucket 4: null
...

hashCode() determines the bucket
equals() finds the exact element in bucket
```

### Basic Operations:

```java
import java.util.HashSet;
import java.util.Set;

class HashSetBasics {
    
    public static void demonstrateHashSet() {
        System.out.println("=== HashSet Demonstration ===\n");
        
        Set<String> fruits = new HashSet<>();
        
        // Adding elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");  // Duplicate - won't be added
        
        System.out.println("Fruits: " + fruits);  // Order not guaranteed
        System.out.println("Size: " + fruits.size());  // 3 (no duplicates)
        
        // Contains check (O(1))
        System.out.println("\n--- Membership Testing ---");
        System.out.println("Contains 'Banana': " + fruits.contains("Banana"));
        System.out.println("Contains 'Mango': " + fruits.contains("Mango"));
        
        // Removing
        fruits.remove("Apple");
        System.out.println("\nAfter removing Apple: " + fruits);
        
        // Iteration
        System.out.println("\n--- Iterating (order not guaranteed) ---");
        for (String fruit : fruits) {
            System.out.println("🍎 " + fruit);
        }
        
        // Set operations
        Set<String> moreFruits = new HashSet<>();
        moreFruits.add("Date");
        moreFruits.add("Cherry");  // Common element
        
        System.out.println("\n--- Set Operations ---");
        Set<String> union = new HashSet<>(fruits);
        union.addAll(moreFruits);
        System.out.println("Union: " + union);
        
        Set<String> intersection = new HashSet<>(fruits);
        intersection.retainAll(moreFruits);
        System.out.println("Intersection: " + intersection);
        
        Set<String> difference = new HashSet<>(fruits);
        difference.removeAll(moreFruits);
        System.out.println("Difference: " + difference);
    }
    
    public static void main(String[] args) {
        demonstrateHashSet();
    }
}
```

### When to Use HashSet:

✅ **Use HashSet When:**
- Need fast O(1) membership testing
- Don't care about element order
- Want to eliminate duplicates
- Frequent add/remove/contains operations

❌ **Avoid HashSet When:**
- Need sorted or insertion-ordered elements
- Need null safety (use custom implementation)
- Need thread safety (use ConcurrentHashSet or synchronize)

---

## 2. LinkedHashSet - Ordered HashSet

### Key Characteristics:
- ✅ **Maintains insertion order** - Elements in order they were added
- ✅ **Fast operations** - O(1) like HashSet
- ✅ **Predictable iteration** - Same order every time
- ❌ **More memory** - Doubly-linked list overhead
- ❌ **Slightly slower** - Maintains linked list

### Internal Working:
```
Hash Table + Doubly-Linked List:

Hash Table (for O(1) lookup):
Bucket 1: → ["Apple"]
Bucket 2: → ["Banana"]
Bucket 3: → ["Cherry"]

Linked List (for insertion order):
HEAD → ["Apple"] ↔ ["Banana"] ↔ ["Cherry"] ← TAIL
```

### Basic Operations:

```java
import java.util.LinkedHashSet;
import java.util.Set;

class LinkedHashSetBasics {
    
    public static void demonstrateLinkedHashSet() {
        System.out.println("=== LinkedHashSet Demonstration ===\n");
        
        Set<String> colors = new LinkedHashSet<>();
        
        // Adding in specific order
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        colors.add("Red");  // Duplicate - ignored
        
        System.out.println("Colors: " + colors);
        System.out.println("Size: " + colors.size());
        
        // Iteration maintains insertion order
        System.out.println("\n--- Iteration (insertion order maintained) ---");
        for (String color : colors) {
            System.out.println("🎨 " + color);
        }
        
        // Still O(1) operations
        System.out.println("\nContains 'Blue': " + colors.contains("Blue"));
        
        colors.remove("Green");
        System.out.println("After removing Green: " + colors);
    }
    
    public static void main(String[] args) {
        demonstrateLinkedHashSet();
    }
}
```

**Output:**
```
=== LinkedHashSet Demonstration ===

Colors: [Red, Green, Blue, Yellow]
Size: 4

--- Iteration (insertion order maintained) ---
🎨 Red
🎨 Green
🎨 Blue
🎨 Yellow

Contains 'Blue': true
After removing Green: [Red, Blue, Yellow]
```

### When to Use LinkedHashSet:

✅ **Use LinkedHashSet When:**
- Need insertion order preservation
- Want O(1) performance like HashSet
- Building cache with predictable access patterns
- Want deterministic iteration

❌ **Avoid LinkedHashSet When:**
- Don't need ordering (use HashSet - less memory)
- Need sorted order (use TreeSet)

---

## 3. TreeSet - Sorted Set

### Key Characteristics:
- ✅ **Sorted order** - Natural or custom ordering
- ✅ **NavigableSet** - Range operations, floor, ceiling
- ✅ **No duplicates** - Like all sets
- ❌ **Slower** - O(log n) for add/remove/contains
- ❌ **No null** - TreeSet doesn't allow null elements

### Internal Working:
```
Red-Black Tree (Self-Balancing BST):

          [5]
        /     \
     [3]       [8]
    /   \     /   \
  [1]   [4] [6]   [9]

Maintains sorted order: [1, 3, 4, 5, 6, 8, 9]
Balanced tree ensures O(log n) operations
```

### Basic Operations:

```java
import java.util.TreeSet;
import java.util.Set;

class TreeSetBasics {
    
    public static void demonstrateTreeSet() {
        System.out.println("=== TreeSet Demonstration ===\n");
        
        TreeSet<Integer> numbers = new TreeSet<>();
        
        // Adding elements
        numbers.add(5);
        numbers.add(2);
        numbers.add(8);
        numbers.add(1);
        numbers.add(9);
        numbers.add(2);  // Duplicate - ignored
        
        System.out.println("Numbers (sorted): " + numbers);
        
        // NavigableSet operations
        System.out.println("\n--- NavigableSet Operations ---");
        System.out.println("First: " + numbers.first());
        System.out.println("Last: " + numbers.last());
        System.out.println("Lower than 5: " + numbers.lower(5));     // Largest < 5
        System.out.println("Floor of 5: " + numbers.floor(5));       // Largest <= 5
        System.out.println("Ceiling of 6: " + numbers.ceiling(6));   // Smallest >= 6
        System.out.println("Higher than 5: " + numbers.higher(5));   // Smallest > 5
        
        // Range operations
        System.out.println("\n--- Range Operations ---");
        System.out.println("HeadSet (<5): " + numbers.headSet(5));       // Elements < 5
        System.out.println("TailSet (>=5): " + numbers.tailSet(5));      // Elements >= 5
        System.out.println("SubSet [2,8): " + numbers.subSet(2, 8));     // Elements [2, 8)
        
        // Descending order
        System.out.println("\n--- Descending Order ---");
        System.out.println("Descending: " + numbers.descendingSet());
        
        // Poll operations (remove and return)
        System.out.println("\n--- Poll Operations ---");
        System.out.println("Poll first: " + numbers.pollFirst());  // Remove 1
        System.out.println("Poll last: " + numbers.pollLast());    // Remove 9
        System.out.println("After polls: " + numbers);
    }
    
    public static void main(String[] args) {
        demonstrateTreeSet();
    }
}
```

### Custom Sorting:

```java
import java.util.TreeSet;
import java.util.Comparator;

class TreeSetCustomSort {
    
    static class Person {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
    
    public static void demonstrateCustomSort() {
        System.out.println("=== TreeSet with Custom Comparator ===\n");
        
        // Sort by age
        TreeSet<Person> peopleByAge = new TreeSet<>(
            Comparator.comparingInt(p -> p.age)
        );
        
        peopleByAge.add(new Person("Alice", 30));
        peopleByAge.add(new Person("Bob", 25));
        peopleByAge.add(new Person("Charlie", 35));
        
        System.out.println("People by age: " + peopleByAge);
        
        // Sort by name length, then alphabetically
        TreeSet<String> words = new TreeSet<>(
            Comparator.comparingInt(String::length)
                     .thenComparing(Comparator.naturalOrder())
        );
        
        words.add("apple");
        words.add("cat");
        words.add("banana");
        words.add("dog");
        
        System.out.println("\nWords by length: " + words);
    }
    
    public static void main(String[] args) {
        demonstrateCustomSort();
    }
}
```

### When to Use TreeSet:

✅ **Use TreeSet When:**
- Need elements in sorted order
- Need range operations (subSet, headSet, tailSet)
- Need floor/ceiling/lower/higher operations
- Want natural ordering or custom comparator

❌ **Avoid TreeSet When:**
- Don't need sorting (use HashSet - faster)
- Need O(1) operations (use HashSet)
- Elements can be null

---

## 4. EnumSet - Specialized for Enums

### Key Characteristics:
- ✅ **Extremely fast** - Bit vector implementation
- ✅ **Memory efficient** - Uses bit flags
- ✅ **Type-safe** - Works only with enums
- ❌ **Enum-only** - Cannot use with other types

### Basic Operations:

```java
import java.util.EnumSet;
import java.util.Set;

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

class EnumSetBasics {
    
    public static void demonstrateEnumSet() {
        System.out.println("=== EnumSet Demonstration ===\n");
        
        // Creating EnumSets
        Set<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        Set<Day> weekdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        Set<Day> allDays = EnumSet.allOf(Day.class);
        Set<Day> noDays = EnumSet.noneOf(Day.class);
        
        System.out.println("Weekend: " + weekend);
        System.out.println("Weekdays: " + weekdays);
        System.out.println("All days: " + allDays);
        
        // Complement (opposite set)
        Set<Day> notWeekend = EnumSet.complementOf(weekend);
        System.out.println("\nNot weekend: " + notWeekend);
        
        // Fast operations
        System.out.println("\n--- Operations ---");
        System.out.println("Is Monday a weekend?: " + weekend.contains(Day.MONDAY));
        
        weekdays.add(Day.SATURDAY);  // Make Saturday a weekday
        System.out.println("Modified weekdays: " + weekdays);
        
        // Set operations
        Set<Day> union = EnumSet.copyOf(weekend);
        union.addAll(weekdays);
        System.out.println("\nUnion: " + union);
    }
    
    public static void main(String[] args) {
        demonstrateEnumSet();
    }
}
```

### When to Use EnumSet:

✅ **Use EnumSet When:**
- Working with enum types
- Need maximum performance
- Want minimal memory footprint

❌ **Avoid EnumSet When:**
- Not working with enums (impossible to use)

---

## Choosing the Right Set

### Decision Tree:

```
Need a Set?
    ↓
Are elements enums?
    ├── YES → EnumSet (fastest, most efficient)
    └── NO → Continue
         ↓
Need sorted order?
    ├── YES → TreeSet (O(log n) operations)
    └── NO → Continue
         ↓
Need insertion order preserved?
    ├── YES → LinkedHashSet
    └── NO → HashSet (fastest O(1))
```

### Performance Summary:

| Operation | HashSet | LinkedHashSet | TreeSet | EnumSet |
|-----------|---------|---------------|---------|---------|
| **add()** | O(1) | O(1) | O(log n) | O(1) |
| **remove()** | O(1) | O(1) | O(log n) | O(1) |
| **contains()** | O(1) | O(1) | O(log n) | O(1) |
| **Iteration** | O(n) | O(n) | O(n) | O(n) |
| **Ordering** | None | Insertion | Sorted | Enum order |
| **Memory** | Low | Medium | High | Very Low |

---

## Common Set Operations

### 1. Creating Sets:

```java
// Empty set
Set<String> set1 = new HashSet<>();

// From collection
Set<String> set2 = new HashSet<>(Arrays.asList("A", "B", "C"));

// Immutable set (Java 9+)
Set<String> set3 = Set.of("A", "B", "C");

// EnumSet
Set<Day> days = EnumSet.of(Day.MONDAY, Day.FRIDAY);
```

### 2. Set Operations:

```java
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));

// Union (all elements from both)
Set<Integer> union = new HashSet<>(set1);
union.addAll(set2);  // {1, 2, 3, 4, 5, 6}

// Intersection (common elements)
Set<Integer> intersection = new HashSet<>(set1);
intersection.retainAll(set2);  // {3, 4}

// Difference (elements in set1 but not set2)
Set<Integer> difference = new HashSet<>(set1);
difference.removeAll(set2);  // {1, 2}

// Symmetric Difference (elements in either but not both)
Set<Integer> symDiff = new HashSet<>(union);
symDiff.removeAll(intersection);  // {1, 2, 5, 6}

// Subset check
boolean isSubset = set2.containsAll(intersection);
```

### 3. Removing Duplicates:

```java
List<String> listWithDuplicates = Arrays.asList("A", "B", "A", "C", "B");
Set<String> uniqueSet = new HashSet<>(listWithDuplicates);
System.out.println(uniqueSet);  // [A, B, C]

// Back to list
List<String> uniqueList = new ArrayList<>(uniqueSet);
```

---

## Best Practices

### ✅ DO:

```java
// Use interface type
Set<String> set = new HashSet<>();

// Specify initial capacity for large sets
Set<String> set = new HashSet<>(1000);

// Use EnumSet for enums
Set<Day> days = EnumSet.of(Day.MONDAY, Day.FRIDAY);

// Check before adding
if (!set.contains(item)) {
    set.add(item);  // Though redundant, makes intent clear
}
```

### ❌ DON'T:

```java
// Don't modify elements that affect equals/hashCode
Person p = new Person("Alice");
set.add(p);
p.name = "Bob";  // ❌ Now set is corrupted

// Don't add null to TreeSet
treeSet.add(null);  // ❌ NullPointerException

// Don't expect specific order with HashSet
// Order can change between runs
```

---

## Interview Questions

**Q: HashSet vs TreeSet vs LinkedHashSet?**  
A: HashSet is fastest (O(1)) with no ordering. TreeSet maintains sorted order (O(log n)) and provides NavigableSet operations. LinkedHashSet maintains insertion order with O(1) operations.

**Q: How does HashSet handle duplicates?**  
A: Uses `hashCode()` to find bucket, then `equals()` to check if element exists. If found, doesn't add duplicate.

**Q: Why doesn't TreeSet allow null?**  
A: TreeSet needs to compare elements using Comparable/Comparator. Null cannot be compared, so it throws NullPointerException.

**Q: When to use Set over List?**  
A: Use Set when uniqueness matters, order doesn't (or custom ordering needed), and fast membership testing is required.

---

## Next Steps

Explore individual implementations:
1. [HashSet](01-HashSet/) - Fast unordered set
2. [LinkedHashSet](02-LinkedHashSet/) - Insertion-ordered set
3. [TreeSet](03-TreeSet/) - Sorted set with NavigableSet
4. [EnumSet](04-EnumSet/) - Enum-specialized set

Then explore:
- [Maps](../03-Maps/) - Key-value pair collections
- [Queues](../04-Queues/) - FIFO and priority collections
