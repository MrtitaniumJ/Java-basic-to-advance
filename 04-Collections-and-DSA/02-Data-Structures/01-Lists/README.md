# Lists in Java - Ordered Collections

## Simple Explanation

Think of a **List** as a **numbered playlist** or **to-do list**:
- Items are stored in a **specific order**
- Each item has a **position/index** (0, 1, 2, 3...)
- You can have **duplicate** items
- You can **add, remove, or access** items by their position

### Real-World Analogy
Imagine a **music playlist**:
- Songs are in a specific order (position 1, 2, 3...)
- Same song can appear multiple times (duplicates allowed)
- You can insert a new song at any position
- You can jump to any song by its track number

## Professional Definition

A **List** is an ordered collection (also known as a sequence) that allows duplicate elements and provides positional access to its elements. Lists maintain insertion order and allow precise control over where elements are inserted. The List interface extends the Collection interface and provides additional methods for positional (indexed) access, searching, and iteration.

## List Interface Hierarchy

```
Collection (Interface)
    ↓
List (Interface)
    ├── ArrayList (Class) - Dynamic array implementation
    ├── LinkedList (Class) - Doubly-linked list implementation
    ├── Vector (Class) - Synchronized dynamic array (legacy)
    └── Stack (Class) - LIFO stack implementation (legacy)
```

## Types of List Implementations

### Overview Comparison:

| Feature | ArrayList | LinkedList | Vector | Stack |
|---------|-----------|------------|--------|-------|
| **Internal Structure** | Dynamic Array | Doubly-Linked Nodes | Dynamic Array | Array-based LIFO |
| **Random Access** | O(1) Fast | O(n) Slow | O(1) Fast | O(1) Fast |
| **Insertion/Deletion (Middle)** | O(n) Slow | O(1) Fast | O(n) Slow | N/A |
| **Insertion/Deletion (End)** | O(1) Fast* | O(1) Fast | O(1) Fast* | O(1) Fast |
| **Memory Overhead** | Low | High (node pointers) | Low | Low |
| **Thread-Safe** | No | No | Yes (synchronized) | Yes (synchronized) |
| **Best Use Case** | Random access, read-heavy | Frequent insertions/deletions | Legacy, thread-safe lists | LIFO operations |
| **When to Avoid** | Frequent middle insertions | Random access by index | Modern applications | General list operations |

*Amortized time complexity

## 1. ArrayList - Dynamic Array Implementation

### Key Characteristics:
- ✅ **Fast random access** - O(1) time to access any element by index
- ✅ **Memory efficient** - Stores elements in contiguous memory
- ✅ **Good for iteration** - Cache-friendly sequential access
- ❌ **Slow insertions/deletions** - O(n) when not at the end
- ❌ **Not thread-safe** - Requires external synchronization

### Internal Working:
```
Initial Capacity: 10 elements

Adding Elements:
[A, B, C, _, _, _, _, _, _, _]  ← Current size: 3, Capacity: 10

When Capacity Exceeds:
Old Array: [A, B, C, D, E, F, G, H, I, J] (full)
                    ↓ (creates new array with 1.5x capacity)
New Array: [A, B, C, D, E, F, G, H, I, J, _, _, _, _, _] (capacity: 15)
```

### Basic Operations:

```java
import java.util.ArrayList;
import java.util.List;

class ArrayListBasics {
    
    public static void demonstrateArrayList() {
        System.out.println("=== ArrayList Demonstration ===\n");
        
        // Creating ArrayList
        List<String> fruits = new ArrayList<>();
        
        // Adding elements
        fruits.add("Apple");           // [Apple]
        fruits.add("Banana");          // [Apple, Banana]
        fruits.add("Cherry");          // [Apple, Banana, Cherry]
        fruits.add(1, "Mango");        // [Apple, Mango, Banana, Cherry] - insert at index
        
        System.out.println("Fruits list: " + fruits);
        System.out.println("Size: " + fruits.size());
        
        // Accessing elements
        System.out.println("\n--- Accessing Elements ---");
        System.out.println("First fruit: " + fruits.get(0));
        System.out.println("Last fruit: " + fruits.get(fruits.size() - 1));
        
        // Searching
        System.out.println("\n--- Searching ---");
        System.out.println("Contains 'Banana': " + fruits.contains("Banana"));
        System.out.println("Index of 'Cherry': " + fruits.indexOf("Cherry"));
        
        // Updating
        fruits.set(1, "Papaya");  // Replace Mango with Papaya
        System.out.println("\nAfter update: " + fruits);
        
        // Removing elements
        fruits.remove("Apple");           // Remove by object
        fruits.remove(0);                 // Remove by index
        System.out.println("After removals: " + fruits);
        
        // Iteration
        System.out.println("\n--- Iterating ---");
        for (String fruit : fruits) {
            System.out.println("🍎 " + fruit);
        }
    }
    
    public static void main(String[] args) {
        demonstrateArrayList();
    }
}
```

### When to Use ArrayList:

✅ **Use ArrayList When:**
- Random access by index is frequent
- Mostly reading/iterating through elements
- Memory efficiency is important
- Order matters and duplicates are allowed

❌ **Avoid ArrayList When:**
- Frequent insertions/deletions in the middle
- Thread safety is required without external synchronization

---

## 2. LinkedList - Doubly-Linked List Implementation

### Key Characteristics:
- ✅ **Fast insertions/deletions** - O(1) if you have a reference to the node
- ✅ **No capacity issues** - Grows dynamically without resizing
- ✅ **Implements Queue/Deque** - Can be used as stack or queue
- ❌ **Slow random access** - O(n) to access elements by index
- ❌ **More memory** - Extra space for node pointers

### Internal Working:
```
Doubly-Linked List Structure:

null ← [prev | Data: A | next] ↔ [prev | Data: B | next] ↔ [prev | Data: C | next] → null
         ↑                                                                         ↑
       HEAD                                                                      TAIL
```

### Basic Operations:

```java
import java.util.LinkedList;

class LinkedListBasics {
    
    public static void demonstrateLinkedList() {
        System.out.println("=== LinkedList Demonstration ===\n");
        
        LinkedList<String> animals = new LinkedList<>();
        
        // Adding elements
        animals.add("Dog");
        animals.addFirst("Lion");
        animals.addLast("Elephant");
        
        System.out.println("Animals: " + animals);
        
        // Queue operations
        System.out.println("First: " + animals.getFirst());
        System.out.println("Last: " + animals.getLast());
        
        // Stack operations
        animals.push("Tiger");
        String popped = animals.pop();
        System.out.println("Popped: " + popped);
    }
    
    public static void main(String[] args) {
        demonstrateLinkedList();
    }
}
```

### When to Use LinkedList:

✅ **Use LinkedList When:**
- Frequent insertions/deletions at beginning or middle
- Need to implement a queue or deque
- Don't need random access by index

❌ **Avoid LinkedList When:**
- Random access is frequent
- Memory is constrained

---

## 3. Vector - Synchronized ArrayList (Legacy)

### Key Characteristics:
- ✅ **Thread-safe** - All methods are synchronized
- ✅ **Similar to ArrayList** - Dynamic array implementation
- ❌ **Slower** - Synchronization overhead
- ❌ **Legacy class** - Consider using ArrayList with synchronization

**Modern Alternative:**
```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

---

## 4. Stack - LIFO Implementation (Legacy)

### Key Characteristics:
- ✅ **LIFO (Last In First Out)** - Stack data structure
- ✅ **Thread-safe** - Extends Vector
- ❌ **Legacy class** - Use Deque instead

**Modern Alternative:**
```java
Deque<String> stack = new ArrayDeque<>();
stack.push("item");
stack.pop();
```

---

## Choosing the Right List Implementation

### Decision Tree:

```
Need a List?
    ↓
Do you need frequent insertions/deletions?
    ├── YES → LinkedList
    └── NO → ArrayList (default choice)
         ↓
Need thread safety?
    └── Use Collections.synchronizedList() or CopyOnWriteArrayList
```

---

## Common List Operations

```java
// Creating
List<String> list = new ArrayList<>();
List<String> list2 = new ArrayList<>(Arrays.asList("A", "B", "C"));
List<String> immutable = List.of("A", "B", "C");  // Java 9+

// Adding
list.add("item");
list.add(0, "item");
list.addAll(Arrays.asList("X", "Y"));

// Accessing
String first = list.get(0);
boolean has = list.contains("item");
int index = list.indexOf("item");

// Removing
list.remove(0);
list.remove("item");
list.removeIf(s -> s.startsWith("X"));
list.clear();

// Sorting
Collections.sort(list);
list.sort((a, b) -> a.length() - b.length());
```

---

## Best Practices

### ✅ DO:
```java
// Use interface type
List<String> list = new ArrayList<>();

// Specify initial capacity if size known
List<String> list = new ArrayList<>(1000);

// Use immutable lists when appropriate
List<String> immutable = List.of("A", "B", "C");
```

### ❌ DON'T:
```java
// Don't modify while iterating
for (String item : list) {
    list.remove(item);  // ❌ ConcurrentModificationException
}

// Don't use Vector/Stack in new code
Vector<String> vector = new Vector<>();  // ❌ Legacy
```

---

## Interview Questions

**Q: ArrayList vs LinkedList?**  
A: ArrayList uses dynamic array (fast O(1) random access), LinkedList uses doubly-linked nodes (fast O(1) insertion/deletion at ends). Use ArrayList for read-heavy, LinkedList for write-heavy operations.

**Q: How does ArrayList grow?**  
A: When capacity is exceeded, ArrayList creates new array with 1.5x capacity and copies all elements.

**Q: Why is ArrayList not thread-safe?**  
A: Multiple threads can modify simultaneously without synchronization. Use `Collections.synchronizedList()` or `CopyOnWriteArrayList`.

---

## Next Steps

Explore individual implementations:
1. [ArrayList](01-ArrayList/) - Dynamic array implementation
2. [LinkedList](02-LinkedList/) - Doubly-linked list implementation
3. [Vector](03-Vector/) - Synchronized list (legacy)
4. [Stack](04-Stack/) - LIFO stack (legacy)

Then explore:
- [Sets](../02-Sets/) - Unique element collections
- [Maps](../03-Maps/) - Key-value pairs
- [Queues](../04-Queues/) - FIFO collections
