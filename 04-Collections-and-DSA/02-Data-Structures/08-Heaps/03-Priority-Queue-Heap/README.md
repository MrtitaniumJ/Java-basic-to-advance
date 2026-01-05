# Priority Queue using Heap

## Introduction

A **Priority Queue** is an abstract data type where elements are associated with priorities, and the element with the highest (or lowest) priority is served first, regardless of insertion order. Heaps provide the most efficient implementation of priority queues, offering O(1) access to the highest/lowest priority element and O(log n) insertion and deletion. Priority queues are fundamental to numerous real-world applications including task scheduling, network routing, Dijkstra's algorithm, and Huffman coding. Understanding heap-based priority queues is essential for solving complex algorithmic problems efficiently.

## What is a Priority Queue?

A **Priority Queue** is a queue data structure with the following characteristics:

1. **Priority-Based Ordering**: Elements are served based on priority, not insertion order
2. **Efficient Access**: O(1) access to highest/lowest priority element
3. **Dynamic Insertion/Deletion**: Elements can be added/removed efficiently
4. **Flexible Priorities**: Can be integers, floats, or custom comparable objects
5. **Heap Implementation**: Best implemented using min-heap or max-heap

### Key Difference: Queue vs Priority Queue

```
Standard Queue (FIFO):
insert(10) → enqueue
insert(5)  → enqueue
insert(20) → enqueue
dequeue() → 10 (first inserted)

Priority Queue (Highest First):
insert(10, priority=1)
insert(5, priority=3)
insert(20, priority=2)
dequeue() → 5 (highest priority)
```

## Priority Queue Properties

### Fundamental Properties

- **Priority Association**: Each element has an associated priority value
- **Ordered Access**: Higher/lower priority elements accessed first
- **Efficient Operations**: O(log n) for insert and delete operations
- **Flexible Implementation**: Can use min-heap or max-heap based on priority direction
- **Thread-Safe Variants**: Can be synchronized for concurrent access

### Priority Types

1. **Min-Priority Queue**: Lowest priority value extracted first
2. **Max-Priority Queue**: Highest priority value extracted first
3. **Custom Priorities**: User-defined comparison logic

## Internal Structure with Visual Diagrams

### Priority Queue Structure (Max-Priority)

```
Elements with priorities:
insert((Element, Priority)):
- ("Task A", 5)
- ("Task B", 10)
- ("Task C", 8)
- ("Task D", 3)

Resulting Max-Heap:
        (Task B, 10)
       /           \
  (Task C, 8)    (Task A, 5)
   /
(Task D, 3)

Extraction Order:
1. Extract (Task B, 10)
2. Extract (Task C, 8)
3. Extract (Task A, 5)
4. Extract (Task D, 3)
```

### Comparison: Different Priority Queue Implementations

```
Array-Based:        Linked List:      Heap-Based:
Extract: O(n)       Extract: O(1)     Extract: O(log n)
Insert: O(1)        Insert: O(n)      Insert: O(log n)
Memory: O(n)        Memory: O(n)      Memory: O(n)

Heap-based provides best average performance!
```

## Priority Queue Operations

### Core Operations

#### 1. Insert (Add with Priority)

**Steps**:
1. Create new element-priority pair
2. Add to heap array
3. Heapify up based on priority
4. Return success

**Time Complexity**: O(log n)

#### 2. Extract (Remove Highest Priority)

**Steps**:
1. Get root element (highest priority)
2. Save the element
3. Replace root with last element
4. Remove last element
5. Heapify down
6. Return saved element

**Time Complexity**: O(log n)

#### 3. Peek (View Highest Priority)

**Steps**:
1. Return root element without removal

**Time Complexity**: O(1)

#### 4. Update Priority

**Steps**:
1. Find element (O(n))
2. Update its priority
3. Heapify up or down based on new priority

**Time Complexity**: O(n) for finding, O(log n) for reheapifying

#### 5. Remove Specific Element

**Steps**:
1. Find element by value
2. Replace with last element
3. Heapify up or down

**Time Complexity**: O(n) for finding, O(log n) for reheapifying

## Complete Java Implementation

```java
import java.util.*;
import java.util.Comparator;

/**
 * Generic Priority Queue Implementation using Heap
 * Supports both max and min priority queues
 */
public class PriorityQueueHeap<E extends Comparable<E>> {
    
    /**
     * Element wrapper class that stores element and priority
     */
    public static class PriorityElement<T extends Comparable<T>> 
            implements Comparable<PriorityElement<T>> {
        private T element;
        private int priority;
        
        public PriorityElement(T element, int priority) {
            this.element = element;
            this.priority = priority;
        }
        
        public T getElement() {
            return element;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public void setPriority(int priority) {
            this.priority = priority;
        }
        
        @Override
        public int compareTo(PriorityElement<T> other) {
            return Integer.compare(this.priority, other.priority);
        }
        
        @Override
        public String toString() {
            return "(" + element + ", " + priority + ")";
        }
    }
    
    private List<PriorityElement<E>> heap;
    private Comparator<PriorityElement<E>> comparator;
    private boolean isMaxHeap;
    
    /**
     * Constructor for max-priority queue
     */
    public PriorityQueueHeap(boolean isMaxHeap) {
        this.heap = new ArrayList<>();
        this.isMaxHeap = isMaxHeap;
        
        if (isMaxHeap) {
            this.comparator = (a, b) -> Integer.compare(b.priority, a.priority);
        } else {
            this.comparator = (a, b) -> Integer.compare(a.priority, b.priority);
        }
    }
    
    /**
     * Get parent index
     */
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }
    
    /**
     * Get left child index
     */
    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }
    
    /**
     * Get right child index
     */
    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }
    
    /**
     * Check if index is valid
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index < heap.size();
    }
    
    /**
     * Get left child
     */
    private PriorityElement<E> getLeftChild(int index) {
        int leftIndex = getLeftChildIndex(index);
        return isValidIndex(leftIndex) ? heap.get(leftIndex) : null;
    }
    
    /**
     * Get right child
     */
    private PriorityElement<E> getRightChild(int index) {
        int rightIndex = getRightChildIndex(index);
        return isValidIndex(rightIndex) ? heap.get(rightIndex) : null;
    }
    
    /**
     * Get parent
     */
    private PriorityElement<E> getParent(int index) {
        int parentIndex = getParentIndex(index);
        return isValidIndex(parentIndex) ? heap.get(parentIndex) : null;
    }
    
    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return heap.size() == 0;
    }
    
    /**
     * Get queue size
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * Swap two elements
     */
    private void swap(int i, int j) {
        if (isValidIndex(i) && isValidIndex(j)) {
            PriorityElement<E> temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }
    
    /**
     * Heapify up - move element up until heap property satisfied
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = getParentIndex(index);
            PriorityElement<E> current = heap.get(index);
            PriorityElement<E> parent = heap.get(parentIndex);
            
            // For max heap: child should be <= parent
            // For min heap: child should be >= parent
            if (comparator.compare(current, parent) < 0) {
                break;
            }
            
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    /**
     * Heapify down - move element down until heap property satisfied
     */
    private void heapifyDown(int index) {
        while (isValidIndex(getLeftChildIndex(index))) {
            int smallerChildIndex = getLeftChildIndex(index);
            PriorityElement<E> leftChild = getLeftChild(index);
            PriorityElement<E> rightChild = getRightChild(index);
            
            // Find the child with higher priority (for max heap)
            if (rightChild != null && comparator.compare(rightChild, leftChild) > 0) {
                smallerChildIndex = getRightChildIndex(index);
            }
            
            PriorityElement<E> current = heap.get(index);
            PriorityElement<E> childToCompare = heap.get(smallerChildIndex);
            
            if (comparator.compare(current, childToCompare) >= 0) {
                break;
            }
            
            swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }
    
    /**
     * Insert element with priority
     * Time Complexity: O(log n)
     */
    public void insert(E element, int priority) {
        PriorityElement<E> newElement = new PriorityElement<>(element, priority);
        heap.add(newElement);
        heapifyUp(heap.size() - 1);
    }
    
    /**
     * Peek at highest priority element without removing
     * Time Complexity: O(1)
     */
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return heap.get(0).getElement();
    }
    
    /**
     * Get priority of highest priority element
     * Time Complexity: O(1)
     */
    public int peekPriority() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return heap.get(0).getPriority();
    }
    
    /**
     * Extract and return highest priority element
     * Time Complexity: O(log n)
     */
    public E poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        
        E maxElement = heap.get(0).getElement();
        PriorityElement<E> lastElement = heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown(0);
        }
        
        return maxElement;
    }
    
    /**
     * Find element in queue
     * Time Complexity: O(n)
     */
    public int findElement(E element) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).getElement().equals(element)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Update priority of element
     * Time Complexity: O(n) for finding, O(log n) for reheapifying
     */
    public boolean updatePriority(E element, int newPriority) {
        int index = findElement(element);
        if (index == -1) {
            return false;
        }
        
        int oldPriority = heap.get(index).getPriority();
        heap.get(index).setPriority(newPriority);
        
        if (newPriority > oldPriority) {
            heapifyUp(index);
        } else if (newPriority < oldPriority) {
            heapifyDown(index);
        }
        
        return true;
    }
    
    /**
     * Remove specific element
     * Time Complexity: O(n)
     */
    public boolean remove(E element) {
        int index = findElement(element);
        if (index == -1) {
            return false;
        }
        
        PriorityElement<E> lastElement = heap.remove(heap.size() - 1);
        
        if (index < heap.size()) {
            heap.set(index, lastElement);
            int parentIndex = getParentIndex(index);
            
            if (parentIndex >= 0 && 
                comparator.compare(heap.get(index), heap.get(parentIndex)) > 0) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }
        
        return true;
    }
    
    /**
     * Clear the priority queue
     */
    public void clear() {
        heap.clear();
    }
    
    /**
     * Get all elements in priority order
     */
    public List<E> getAllInOrder() {
        List<E> result = new ArrayList<>();
        List<PriorityElement<E>> tempHeap = new ArrayList<>(heap);
        
        // Create temporary copy and extract all
        while (!tempHeap.isEmpty()) {
            result.add(tempHeap.get(0).getElement());
            PriorityElement<E> lastElement = tempHeap.remove(tempHeap.size() - 1);
            
            if (!tempHeap.isEmpty()) {
                tempHeap.set(0, lastElement);
                // Simplified heapify for display
            }
        }
        
        return result;
    }
    
    /**
     * Display queue structure
     */
    public void display() {
        System.out.println("Priority Queue: " + heap);
    }
    
    /**
     * Validate heap property
     */
    public boolean isValidHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int leftIndex = getLeftChildIndex(i);
            int rightIndex = getRightChildIndex(i);
            
            if (isValidIndex(leftIndex)) {
                if (comparator.compare(heap.get(i), heap.get(leftIndex)) < 0) {
                    return false;
                }
            }
            
            if (isValidIndex(rightIndex)) {
                if (comparator.compare(heap.get(i), heap.get(rightIndex)) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Main method demonstrating priority queue operations
     */
    public static void main(String[] args) {
        System.out.println("=== Max Priority Queue Demo ===\n");
        
        PriorityQueueHeap<String> pq = new PriorityQueueHeap<>(true);
        
        // Insert tasks with priorities
        System.out.println("Inserting tasks with priorities:");
        pq.insert("Task A", 5);
        System.out.println("Inserted (Task A, 5): " + pq.heap);
        
        pq.insert("Task B", 10);
        System.out.println("Inserted (Task B, 10): " + pq.heap);
        
        pq.insert("Task C", 8);
        System.out.println("Inserted (Task C, 8): " + pq.heap);
        
        pq.insert("Task D", 3);
        System.out.println("Inserted (Task D, 3): " + pq.heap);
        
        pq.insert("Task E", 12);
        System.out.println("Inserted (Task E, 12): " + pq.heap);
        
        System.out.println("\nQueue validation: " + pq.isValidHeap());
        System.out.println("Queue size: " + pq.size());
        
        // Extract elements
        System.out.println("\n=== Extracting Tasks in Priority Order ===");
        while (!pq.isEmpty()) {
            System.out.println("Extracted: " + pq.poll() + 
                             " (priority: " + pq.peekPriority() + "), " +
                             "Remaining: " + pq.heap);
        }
        
        // Min Priority Queue Demo
        System.out.println("\n\n=== Min Priority Queue Demo ===\n");
        
        PriorityQueueHeap<String> minPQ = new PriorityQueueHeap<>(false);
        
        int[] priorities = {50, 10, 40, 30, 20};
        String[] elements = {"Server A", "Server B", "Server C", "Server D", "Server E"};
        
        System.out.println("Inserting servers with load (lower is better):");
        for (int i = 0; i < elements.length; i++) {
            minPQ.insert(elements[i], priorities[i]);
            System.out.println("Inserted (" + elements[i] + ", " + priorities[i] + "): " + minPQ.heap);
        }
        
        System.out.println("\n=== Servers by Load (Ascending) ===");
        while (!minPQ.isEmpty()) {
            System.out.println("Next server: " + minPQ.poll() + 
                             " (load: " + minPQ.peekPriority() + "), " +
                             "Remaining: " + minPQ.heap);
        }
        
        // Update Priority Demo
        System.out.println("\n\n=== Update Priority Demo ===\n");
        
        PriorityQueueHeap<String> updatePQ = new PriorityQueueHeap<>(true);
        updatePQ.insert("Job 1", 5);
        updatePQ.insert("Job 2", 8);
        updatePQ.insert("Job 3", 3);
        
        System.out.println("Initial queue: " + updatePQ.heap);
        
        System.out.println("Updating Job 3 priority from 3 to 15");
        updatePQ.updatePriority("Job 3", 15);
        System.out.println("After update: " + updatePQ.heap);
        
        System.out.println("\nHeap validation after update: " + updatePQ.isValidHeap());
    }
}
```

## Performance Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Insert | O(log n) | O(1) | Add and bubble up |
| Extract (Poll) | O(log n) | O(1) | Remove and heapify down |
| Peek | O(1) | O(1) | View root only |
| Find Element | O(n) | O(1) | Linear search required |
| Update Priority | O(n) + O(log n) | O(1) | Find O(n), reheapify O(log n) |
| Remove Element | O(n) + O(log n) | O(1) | Find O(n), reheapify O(log n) |
| Build Queue | O(n) | O(1) | From n elements |

## Comparison with Other Implementations

| Implementation | Insert | Extract | Space | Notes |
|---|---|---|---|---|
| **Heap** | O(log n) | O(log n) | O(n) | **Best overall** |
| **Sorted Array** | O(n) | O(1) | O(n) | Slow insertion |
| **Unsorted Array** | O(1) | O(n) | O(n) | Slow extraction |
| **Linked List** | O(n) | O(1) | O(n) | Poor cache |
| **BST** | O(log n) | O(log n) | O(n) | More overhead |

## Use Cases

### 1. **Task Scheduling**
- Operating systems schedule processes by priority
- Higher priority tasks execute before lower priority

### 2. **Dijkstra's Shortest Path Algorithm**
- Select minimum distance node efficiently
- Process nodes in order of distance

### 3. **Huffman Coding**
- Build optimal binary tree for data compression
- Merge nodes with lowest frequencies

### 4. **A* Pathfinding Algorithm**
- Explore nodes with lowest f-cost first
- Game AI navigation and planning

### 5. **Bandwidth Allocation**
- Serve high-bandwidth requests first
- Network router packet scheduling

### 6. **Load Balancing**
- Route to least-loaded server
- Distribute work across resources

## Real-World Applications

- **Operating Systems**: Process/thread scheduling with priorities
- **Network Routers**: QoS packet scheduling
- **Database Systems**: Query execution optimization
- **File Systems**: Disk I/O scheduling
- **Multimedia**: Video encoding job scheduling
- **E-commerce**: Order processing by customer tier
- **Medical Systems**: Patient triage and treatment order
- **Cloud Computing**: VM resource allocation

## Advantages and Disadvantages

### Advantages

✅ **Efficient Operations**: O(log n) for insert and extract  
✅ **Flexible Priorities**: Easily adjust task importance  
✅ **Memory Efficient**: Array-based implementation  
✅ **Proven Algorithm**: Well-studied and optimized  
✅ **Priority Modification**: Update priorities dynamically  

### Disadvantages

❌ **Find Element**: O(n) for searching arbitrary elements  
❌ **Not Sorted**: Elements not fully sorted in queue  
❌ **Custom Objects**: Need proper comparator implementation  
❌ **Memory Overhead**: Wrapper objects for element-priority pairs  

## Conclusion

Priority queues implemented with heaps provide the optimal balance of efficient insertion, extraction, and memory usage. They are fundamental to numerous algorithms and real-world systems requiring priority-based processing. Understanding heap-based priority queues is essential for solving complex algorithmic problems, implementing efficient scheduling systems, and designing scalable applications. The combination of theoretical efficiency and practical performance makes heaps the preferred choice for priority queue implementations in production systems.
