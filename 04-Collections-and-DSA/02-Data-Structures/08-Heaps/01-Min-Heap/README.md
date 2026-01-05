# Min-Heap Data Structure

## Introduction

A **Min-Heap** is a complete binary tree where the parent node is always smaller than or equal to its child nodes. This property, called the "min-heap property," ensures that the smallest element is always at the root of the tree. Min-heaps are fundamental data structures used in various algorithms and applications, particularly in finding the minimum element efficiently, implementing priority queues with ascending priorities, and in heap sort algorithms.

## What is a Min-Heap?

A Min-Heap is a specialized binary tree with the following characteristics:

1. **Complete Binary Tree**: All levels are completely filled except possibly the last level, which is filled from left to right
2. **Heap Property**: For every node, the value is less than or equal to the values of its children
3. **Root Element**: The smallest element is always at the root
4. **Efficient Operations**: Supports insertion, deletion, and extraction in O(log n) time

## Min-Heap Properties

### Fundamental Properties

- **Min-Heap Property**: `parent ≤ child` for all nodes
- **Root Contains Minimum**: The element at index 0 is always the smallest
- **Complete Binary Tree**: Ensures optimal space utilization
- **Array Representation**: Can be efficiently stored in an array without gaps

### Index Relationships (Zero-Indexed Array)

For a node at index `i`:
- **Left Child Index**: `2*i + 1`
- **Right Child Index**: `2*i + 2`
- **Parent Index**: `(i-1) / 2` (integer division)

## Internal Structure with Visual Diagrams

### Min-Heap Visual Structure

```
Example Min-Heap:
        1
       / \
      2   3
     / \ / \
    4  5 6  7
   /
  8

Array Representation: [1, 2, 3, 4, 5, 6, 7, 8]
Index:               [0, 1, 2, 3, 4, 5, 6, 7]

Parent-Child Relationships:
- Index 0 (value 1): children at indices 1,2 (values 2,3)
- Index 1 (value 2): children at indices 3,4 (values 4,5)
- Index 2 (value 3): children at indices 5,6 (values 6,7)
- Index 3 (value 4): children at index 7 (value 8)
```

### Heap vs Non-Heap Comparison

```
Valid Min-Heap:          Invalid Structure:
      1                        5
     / \                      / \
    2   3                    2   3
   / \                      / \
  4   5                    4   1

(Satisfies min-heap       (Violates min-heap
 property)                 property: 5 > 3)
```

## Heapify Operations

### Heapify Down (Sink)

**Purpose**: Move a node down the heap until the min-heap property is satisfied.

**Algorithm**:
1. Compare the node with its children
2. Swap with the smaller child if it's smaller than the node
3. Repeat until the node is in a valid position (heap property satisfied)

**Time Complexity**: O(log n)

### Heapify Up (Bubble Up)

**Purpose**: Move a node up the heap after insertion.

**Algorithm**:
1. Compare the node with its parent
2. Swap with parent if the node is smaller
3. Repeat until root is reached or the node is larger than parent

**Time Complexity**: O(log n)

## Core Operations

### 1. Insert (Add Element)

**Steps**:
1. Add element at the end of the array
2. Bubble it up using heapify up operation
3. Stop when min-heap property is satisfied

**Example**:
```
Insert 2 into heap [1, 5, 3, 8, 6]:
Step 1: [1, 5, 3, 8, 6, 2]  (add at end)
Step 2: [1, 2, 3, 8, 6, 5]  (swap 2 with 5)
Result: [1, 2, 3, 8, 6, 5]
```

**Time Complexity**: O(log n)

### 2. Extract Min

**Steps**:
1. Save the root element (minimum)
2. Replace root with the last element
3. Remove the last element
4. Heapify down from the root
5. Return the saved element

**Example**:
```
Extract from [1, 2, 3, 8, 6, 5]:
Step 1: Save 1, move 5 to root: [5, 2, 3, 8, 6]
Step 2: Heapify down: [2, 5, 3, 8, 6]
Step 3: Continue: [2, 6, 3, 8, 5]
Result: Min=1, Heap=[2, 6, 3, 8, 5]
```

**Time Complexity**: O(log n)

### 3. Delete (Remove Specific Element)

**Steps**:
1. Find the element at index i
2. Replace with the last element
3. Heapify down or up depending on the new value
4. Remove the last element

**Time Complexity**: O(log n) for heapify, O(n) for finding element

### 4. Build Heap from Array

**Steps**:
1. Start from the last non-leaf node: index `n/2 - 1`
2. Call heapify down for each node moving towards the root

**Time Complexity**: O(n)

## Complete Java Implementation

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Min-Heap Implementation in Java
 * A complete binary tree where parent ≤ children
 */
public class MinHeap {
    private List<Integer> heap;
    
    /**
     * Constructor initializes the heap
     */
    public MinHeap() {
        heap = new ArrayList<>();
    }
    
    /**
     * Constructor creates heap from existing array
     */
    public MinHeap(int[] array) {
        heap = new ArrayList<>();
        for (int val : array) {
            heap.add(val);
        }
        buildHeap();
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
     * Get parent value
     */
    private Integer getParent(int index) {
        Integer parentIndex = getParentIndex(index);
        return parentIndex >= 0 ? heap.get(parentIndex) : null;
    }
    
    /**
     * Get left child value
     */
    private Integer getLeftChild(int index) {
        int leftIndex = getLeftChildIndex(index);
        return leftIndex < heap.size() ? heap.get(leftIndex) : null;
    }
    
    /**
     * Get right child value
     */
    private Integer getRightChild(int index) {
        int rightIndex = getRightChildIndex(index);
        return rightIndex < heap.size() ? heap.get(rightIndex) : null;
    }
    
    /**
     * Check if heap has only one element
     */
    public boolean isEmpty() {
        return heap.size() == 0;
    }
    
    /**
     * Get heap size
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * Swap two elements in the heap
     */
    private void swap(int indexOne, int indexTwo) {
        Integer temp = heap.get(indexOne);
        heap.set(indexOne, heap.get(indexTwo));
        heap.set(indexTwo, temp);
    }
    
    /**
     * Heapify Up (Bubble Up)
     * Move element up until min-heap property is satisfied
     */
    private void heapifyUp(int index) {
        while (index > 0 && heap.get(index) < heap.get(getParentIndex(index))) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }
    
    /**
     * Heapify Down (Sink)
     * Move element down until min-heap property is satisfied
     */
    private void heapifyDown(int index) {
        while (getLeftChild(index) != null) {
            int smallestIndex = getLeftChildIndex(index);
            
            // Compare left and right children
            if (getRightChild(index) != null && 
                heap.get(getRightChildIndex(index)) < heap.get(smallestIndex)) {
                smallestIndex = getRightChildIndex(index);
            }
            
            // If parent is smaller than both children, we're done
            if (heap.get(index) < heap.get(smallestIndex)) {
                break;
            }
            
            // Swap with smaller child and continue
            swap(index, smallestIndex);
            index = smallestIndex;
        }
    }
    
    /**
     * Insert element into heap
     * Time Complexity: O(log n)
     */
    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }
    
    /**
     * Get minimum element without removing
     * Time Complexity: O(1)
     */
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }
    
    /**
     * Extract and return minimum element
     * Time Complexity: O(log n)
     */
    public int extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int min = heap.get(0);
        int lastElement = heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown(0);
        }
        
        return min;
    }
    
    /**
     * Delete element at specific index
     * Time Complexity: O(log n)
     */
    public boolean delete(int index) {
        if (index < 0 || index >= heap.size()) {
            return false;
        }
        
        int lastElement = heap.remove(heap.size() - 1);
        
        if (index < heap.size()) {
            heap.set(index, lastElement);
            
            // Determine whether to heapify up or down
            int parentIndex = getParentIndex(index);
            if (parentIndex >= 0 && heap.get(index) < heap.get(parentIndex)) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }
        
        return true;
    }
    
    /**
     * Build min-heap from unsorted array
     * Time Complexity: O(n)
     */
    public void buildHeap() {
        // Start from last non-leaf node and heapify down
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    /**
     * Get heap as list
     */
    public List<Integer> getHeap() {
        return new ArrayList<>(heap);
    }
    
    /**
     * Print heap structure
     */
    public void display() {
        System.out.println("Heap: " + heap);
    }
    
    /**
     * Check if structure is valid min-heap
     */
    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            Integer left = getLeftChild(i);
            Integer right = getRightChild(i);
            
            if (left != null && left < heap.get(i)) return false;
            if (right != null && right < heap.get(i)) return false;
        }
        return true;
    }
    
    /**
     * Main method demonstrating min-heap operations
     */
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        
        System.out.println("=== Min-Heap Operations Demo ===\n");
        
        // Insert elements
        int[] elements = {10, 5, 20, 2, 8, 15, 30};
        System.out.println("Inserting elements: " + java.util.Arrays.toString(elements));
        for (int element : elements) {
            minHeap.insert(element);
            System.out.println("After inserting " + element + ": " + minHeap.getHeap());
        }
        
        System.out.println("\nHeap validation: " + minHeap.isValidMinHeap());
        System.out.println("Heap size: " + minHeap.size());
        System.out.println("Minimum element: " + minHeap.peek());
        
        // Extract elements
        System.out.println("\n=== Extracting Minimum Elements ===");
        while (!minHeap.isEmpty()) {
            System.out.println("Extracted: " + minHeap.extractMin() + 
                             ", Remaining heap: " + minHeap.getHeap());
        }
        
        // Build heap from array
        System.out.println("\n=== Build Heap from Array ===");
        int[] unsortedArray = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + java.util.Arrays.toString(unsortedArray));
        MinHeap builtHeap = new MinHeap(unsortedArray);
        System.out.println("Built heap: " + builtHeap.getHeap());
        System.out.println("Valid min-heap: " + builtHeap.isValidMinHeap());
        
        // Delete specific element
        System.out.println("\n=== Delete Element at Index ===");
        builtHeap.display();
        builtHeap.delete(2);
        System.out.println("After deleting element at index 2: " + builtHeap.getHeap());
    }
}
```

## Performance Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Insert | O(log n) | O(1) | Add and bubble up |
| Extract Min | O(log n) | O(1) | Remove and heapify down |
| Peek (Get Min) | O(1) | O(1) | Access root only |
| Delete | O(log n) | O(1) | Heapify operation |
| Build Heap | O(n) | O(1) | More efficient than n inserts |
| Search Element | O(n) | O(1) | No ordering guarantee |

## Use Cases

### 1. **Priority Queue Implementation**
- Process tasks in order of importance
- Handle events with ascending priority values

### 2. **Dijkstra's Shortest Path Algorithm**
- Efficiently select the next minimum distance node
- Explore graph edges optimally

### 3. **Heap Sort Algorithm**
- Sort array in O(n log n) time with O(1) space
- In-place sorting without external data structures

### 4. **Finding K Largest Elements**
- Use min-heap of size k to track largest elements
- Efficient for k << n

### 5. **Load Balancing**
- Distribute tasks to the least loaded server
- Find minimum load in O(1) time

## Real-World Applications

- **Operating Systems**: Process scheduling with priority levels
- **Network Routers**: Packet scheduling and queue management
- **Database Systems**: Transaction scheduling and buffer pool management
- **Game Development**: Event scheduling and AI decision making
- **Financial Systems**: Order matching and price monitoring
- **HRU Cache Management**: Track least recently used elements

## Comparison with Other Structures

| Structure | Min Lookup | Insert | Delete | Use Case |
|-----------|-----------|--------|--------|----------|
| **Min-Heap** | O(1) | O(log n) | O(log n) | Priority queues, scheduling |
| **Binary Search Tree** | O(log n) avg | O(log n) avg | O(log n) avg | Sorted range queries |
| **Balanced BST** | O(log n) | O(log n) | O(log n) | General ordered data |
| **Sorted Array** | O(1) | O(n) | O(n) | Frequent min queries |
| **Hash Table** | O(n) | O(1) | O(1) | Fast general lookup |

## Key Advantages

✅ **Efficient Minimum Access**: O(1) time to find minimum  
✅ **Balanced Tree Structure**: Guarantees O(log n) for insertions/deletions  
✅ **Complete Binary Tree**: No gaps, optimal memory usage  
✅ **Array Implementation**: Cache-friendly and space-efficient  
✅ **Fast Construction**: Build heap in O(n) from unsorted array  

## Key Disadvantages

❌ **Inefficient Search**: O(n) to find arbitrary elements  
❌ **Limited Ordering**: Only minimum guaranteed at root  
❌ **No Range Queries**: Cannot efficiently query elements in range  
❌ **Unbalanced Paths**: Heapify down can be slower on unbalanced trees  

## Conclusion

Min-heaps are essential data structures that provide efficient access to the minimum element while maintaining optimal performance for insertion and deletion operations. Their array-based implementation makes them memory-efficient and cache-friendly, while their logarithmic time complexity for core operations makes them ideal for priority queue implementations, scheduling algorithms, and sorting applications. Understanding min-heaps is crucial for mastering algorithmic problem-solving and designing efficient systems.
