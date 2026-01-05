# Max-Heap Data Structure

## Introduction

A **Max-Heap** is a complete binary tree where the parent node is always greater than or equal to its child nodes. This property, called the "max-heap property," ensures that the largest element is always at the root of the tree. Max-heaps are fundamental data structures used in selecting the maximum element efficiently, implementing priority queues with descending priorities, heap sort algorithms, and various optimization problems. The structure and operations of max-heaps mirror those of min-heaps, with the comparison operator inverted.

## What is a Max-Heap?

A Max-Heap is a specialized binary tree with the following characteristics:

1. **Complete Binary Tree**: All levels are completely filled except possibly the last level, which is filled from left to right
2. **Heap Property**: For every node, the value is greater than or equal to the values of its children
3. **Root Element**: The largest element is always at the root
4. **Efficient Operations**: Supports insertion, deletion, and extraction in O(log n) time
5. **Symmetric to Min-Heap**: Logic identical but with reversed comparisons

## Max-Heap Properties

### Fundamental Properties

- **Max-Heap Property**: `parent ≥ child` for all nodes
- **Root Contains Maximum**: The element at index 0 is always the largest
- **Complete Binary Tree**: Ensures optimal space utilization
- **Array Representation**: Can be efficiently stored in an array without gaps

### Index Relationships (Zero-Indexed Array)

For a node at index `i`:
- **Left Child Index**: `2*i + 1`
- **Right Child Index**: `2*i + 2`
- **Parent Index**: `(i-1) / 2` (integer division)

## Internal Structure with Visual Diagrams

### Max-Heap Visual Structure

```
Example Max-Heap:
        99
       /  \
      88   77
     / \  / \
    66 55 44 33
   /
  22

Array Representation: [99, 88, 77, 66, 55, 44, 33, 22]
Index:               [0,  1,  2,  3,  4,  5,  6,  7]

Parent-Child Relationships:
- Index 0 (99): children at indices 1,2 (88,77)
- Index 1 (88): children at indices 3,4 (66,55)
- Index 2 (77): children at indices 5,6 (44,33)
- Index 3 (66): child at index 7 (22)
```

### Heap vs Non-Heap Comparison

```
Valid Max-Heap:          Invalid Structure:
      99                       50
     /  \                     /  \
    88   77                  60   40
   /  \                     /  \
  66   55                  80   70

(Satisfies max-heap       (Violates max-heap
 property)                 property: 50 < 60)
```

## Heapify Operations

### Heapify Down (Sink)

**Purpose**: Move a node down the heap until the max-heap property is satisfied.

**Algorithm**:
1. Compare the node with its children
2. Swap with the larger child if that child is larger than the node
3. Repeat until the node is in a valid position (heap property satisfied)

**Time Complexity**: O(log n)

### Heapify Up (Bubble Up)

**Purpose**: Move a node up the heap after insertion.

**Algorithm**:
1. Compare the node with its parent
2. Swap with parent if the node is larger
3. Repeat until root is reached or the node is smaller than parent

**Time Complexity**: O(log n)

## Core Operations

### 1. Insert (Add Element)

**Steps**:
1. Add element at the end of the array
2. Bubble it up using heapify up operation
3. Stop when max-heap property is satisfied

**Example**:
```
Insert 50 into heap [100, 80, 60, 70, 30]:
Step 1: [100, 80, 60, 70, 30, 50]  (add at end)
Step 2: [100, 80, 60, 70, 30, 50]  (50 < 80, no swap)
Result: [100, 80, 60, 70, 30, 50]
```

**Time Complexity**: O(log n)

### 2. Extract Max

**Steps**:
1. Save the root element (maximum)
2. Replace root with the last element
3. Remove the last element
4. Heapify down from the root
5. Return the saved element

**Example**:
```
Extract from [100, 80, 60, 70, 30, 50]:
Step 1: Save 100, move 50 to root: [50, 80, 60, 70, 30]
Step 2: Heapify down: [80, 50, 60, 70, 30]
Step 3: Continue: [80, 70, 60, 50, 30]
Result: Max=100, Heap=[80, 70, 60, 50, 30]
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
 * Max-Heap Implementation in Java
 * A complete binary tree where parent ≥ children
 */
public class MaxHeap {
    private List<Integer> heap;
    
    /**
     * Constructor initializes the heap
     */
    public MaxHeap() {
        heap = new ArrayList<>();
    }
    
    /**
     * Constructor creates heap from existing array
     */
    public MaxHeap(int[] array) {
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
     * Check if heap is empty
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
     * Move element up until max-heap property is satisfied
     */
    private void heapifyUp(int index) {
        while (index > 0 && heap.get(index) > heap.get(getParentIndex(index))) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }
    
    /**
     * Heapify Down (Sink)
     * Move element down until max-heap property is satisfied
     */
    private void heapifyDown(int index) {
        while (getLeftChild(index) != null) {
            int largestIndex = getLeftChildIndex(index);
            
            // Compare left and right children
            if (getRightChild(index) != null && 
                heap.get(getRightChildIndex(index)) > heap.get(largestIndex)) {
                largestIndex = getRightChildIndex(index);
            }
            
            // If parent is larger than both children, we're done
            if (heap.get(index) > heap.get(largestIndex)) {
                break;
            }
            
            // Swap with larger child and continue
            swap(index, largestIndex);
            index = largestIndex;
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
     * Get maximum element without removing
     * Time Complexity: O(1)
     */
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }
    
    /**
     * Extract and return maximum element
     * Time Complexity: O(log n)
     */
    public int extractMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int max = heap.get(0);
        int lastElement = heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown(0);
        }
        
        return max;
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
            if (parentIndex >= 0 && heap.get(index) > heap.get(parentIndex)) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }
        
        return true;
    }
    
    /**
     * Build max-heap from unsorted array
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
     * Check if structure is valid max-heap
     */
    public boolean isValidMaxHeap() {
        for (int i = 0; i < heap.size(); i++) {
            Integer left = getLeftChild(i);
            Integer right = getRightChild(i);
            
            if (left != null && left > heap.get(i)) return false;
            if (right != null && right > heap.get(i)) return false;
        }
        return true;
    }
    
    /**
     * Find k largest elements
     * Time Complexity: O(k log n)
     */
    public List<Integer> findKLargest(int k) {
        List<Integer> result = new ArrayList<>();
        MaxHeap tempHeap = new MaxHeap(heap.stream().mapToInt(i->i).toArray());
        
        for (int i = 0; i < k && !tempHeap.isEmpty(); i++) {
            result.add(tempHeap.extractMax());
        }
        
        return result;
    }
    
    /**
     * Main method demonstrating max-heap operations
     */
    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();
        
        System.out.println("=== Max-Heap Operations Demo ===\n");
        
        // Insert elements
        int[] elements = {10, 50, 20, 30, 80, 15, 40};
        System.out.println("Inserting elements: " + java.util.Arrays.toString(elements));
        for (int element : elements) {
            maxHeap.insert(element);
            System.out.println("After inserting " + element + ": " + maxHeap.getHeap());
        }
        
        System.out.println("\nHeap validation: " + maxHeap.isValidMaxHeap());
        System.out.println("Heap size: " + maxHeap.size());
        System.out.println("Maximum element: " + maxHeap.peek());
        
        // Extract elements
        System.out.println("\n=== Extracting Maximum Elements ===");
        while (!maxHeap.isEmpty()) {
            System.out.println("Extracted: " + maxHeap.extractMax() + 
                             ", Remaining heap: " + maxHeap.getHeap());
        }
        
        // Build heap from array
        System.out.println("\n=== Build Heap from Array ===");
        int[] unsortedArray = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + java.util.Arrays.toString(unsortedArray));
        MaxHeap builtHeap = new MaxHeap(unsortedArray);
        System.out.println("Built heap: " + builtHeap.getHeap());
        System.out.println("Valid max-heap: " + builtHeap.isValidMaxHeap());
        
        // Delete specific element
        System.out.println("\n=== Delete Element at Index ===");
        builtHeap.display();
        builtHeap.delete(2);
        System.out.println("After deleting element at index 2: " + builtHeap.getHeap());
        
        // Find k largest
        System.out.println("\n=== Find K Largest Elements ===");
        MaxHeap kHeap = new MaxHeap(unsortedArray);
        System.out.println("5 largest elements: " + kHeap.findKLargest(5));
    }
}
```

## Performance Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Insert | O(log n) | O(1) | Add and bubble up |
| Extract Max | O(log n) | O(1) | Remove and heapify down |
| Peek (Get Max) | O(1) | O(1) | Access root only |
| Delete | O(log n) | O(1) | Heapify operation |
| Build Heap | O(n) | O(1) | More efficient than n inserts |
| Search Element | O(n) | O(1) | No ordering guarantee |
| Find k Largest | O(k log n) | O(1) | Extract k times |

## Use Cases

### 1. **Reverse Priority Queues**
- Process tasks in descending order of importance
- Higher values get processed first

### 2. **Heap Sort (Descending Order)**
- Sort array in descending order in O(n log n) time
- In-place sorting without extra space

### 3. **Finding K Smallest Elements**
- Use max-heap of size k to track smallest elements
- More efficient than sorting entire array

### 4. **Load Balancing (Maximum Load)**
- Track heavily loaded servers for rebalancing
- Find maximum load in O(1) time

### 5. **Memory Management**
- Allocate memory to processes with highest priority
- Track maximum memory requests

## Real-World Applications

- **Task Scheduling**: Execute high-priority tasks before low-priority ones
- **Network Buffering**: Process packets with highest priority first
- **Bandwidth Allocation**: Allocate to high-demand connections
- **Gaming**: Manage entities by importance or damage
- **E-commerce**: Process VIP orders before regular orders
- **Resource Management**: Allocate resources to high-need processes

## Comparison with Other Structures

| Structure | Max Lookup | Insert | Delete | Use Case |
|-----------|-----------|--------|--------|----------|
| **Max-Heap** | O(1) | O(log n) | O(log n) | Priority queues, scheduling |
| **Binary Search Tree** | O(log n) avg | O(log n) avg | O(log n) avg | Sorted range queries |
| **Balanced BST** | O(log n) | O(log n) | O(log n) | General ordered data |
| **Sorted Array** | O(1) | O(n) | O(n) | Frequent max queries |
| **Hash Table** | O(n) | O(1) | O(1) | Fast general lookup |

## Key Advantages

✅ **Efficient Maximum Access**: O(1) time to find maximum  
✅ **Balanced Tree Structure**: Guarantees O(log n) for insertions/deletions  
✅ **Complete Binary Tree**: No gaps, optimal memory usage  
✅ **Array Implementation**: Cache-friendly and space-efficient  
✅ **Fast K Largest Queries**: Efficiently extract k largest elements  

## Key Disadvantages

❌ **Inefficient Search**: O(n) to find arbitrary elements  
❌ **Limited Ordering**: Only maximum guaranteed at root  
❌ **No Range Queries**: Cannot efficiently query elements in range  
❌ **Inverse Min-Heap**: Some algorithms naturally use min-heaps  

## Conclusion

Max-heaps are essential data structures that provide efficient access to the maximum element while maintaining optimal performance for insertion and deletion operations. Their array-based implementation makes them memory-efficient and cache-friendly, while their logarithmic time complexity for core operations makes them ideal for descending priority queues, heap sort in descending order, and various selection algorithms. Understanding max-heaps is crucial for mastering algorithmic problem-solving, implementing efficient scheduling systems, and designing performant data processing pipelines.
