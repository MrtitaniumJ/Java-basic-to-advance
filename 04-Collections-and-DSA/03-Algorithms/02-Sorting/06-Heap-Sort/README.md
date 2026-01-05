# Heap Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [Binary Heap Data Structure](#binary-heap-data-structure)
4. [Heapify Process](#heapify-process)
5. [Implementation](#implementation)
6. [Complexity Analysis](#complexity-analysis)
7. [Advantages and Disadvantages](#advantages-and-disadvantages)
8. [When to Use](#when-to-use)
9. [Practical Examples](#practical-examples)

## Introduction

Heap Sort is a comparison-based sorting algorithm that uses a binary heap data structure. Invented by J. W. J. Williams in 1964, it combines the best features of Merge Sort (guaranteed O(n log n)) and Quick Sort (in-place sorting). Heap Sort is particularly valuable when both time and space efficiency are critical.

The algorithm works by first building a max heap from the input data, then repeatedly extracting the maximum element from the heap and reconstructing the heap until all elements are sorted. This elegant approach guarantees O(n log n) performance with only O(1) auxiliary space.

## Algorithm Explanation

### Core Concept

Heap Sort operates in two main phases:

1. **Build Max Heap:** Transform the array into a max heap structure
2. **Extract and Sort:** Repeatedly remove the largest element (root) and maintain heap property

The key insight is that a max heap always has its largest element at the root, making extraction of sorted elements straightforward.

### Step-by-Step Process

1. **Build Max Heap:** Convert unsorted array into a max heap
2. **Swap:** Exchange root (maximum) with last element
3. **Reduce Heap Size:** Exclude last element from heap
4. **Heapify:** Restore max heap property for reduced heap
5. **Repeat:** Steps 2-4 until heap size becomes 1
6. **Result:** Array is now sorted in ascending order

### Key Characteristics

- **In-place sorting:** O(1) auxiliary space
- **Not stable:** May change relative order of equal elements
- **Not adaptive:** No benefit from partially sorted data
- **Guaranteed O(n log n):** No worst-case degradation

## Binary Heap Data Structure

### What is a Binary Heap?

A **binary heap** is a complete binary tree that satisfies the **heap property**:
- **Max Heap:** Every parent node ≥ its children
- **Min Heap:** Every parent node ≤ its children

### Array Representation

A complete binary tree can be efficiently represented as an array:

```
Array: [90, 85, 75, 80, 70, 60, 55]

Tree representation:
           90
         /    \
       85      75
      /  \    /  \
    80   70  60  55
```

### Index Relationships

For element at index `i`:
- **Parent:** `(i - 1) / 2`
- **Left Child:** `2 * i + 1`
- **Right Child:** `2 * i + 2`

```java
// Navigation in heap
int parent(int i) { return (i - 1) / 2; }
int leftChild(int i) { return 2 * i + 1; }
int rightChild(int i) { return 2 * i + 2; }
```

### Max Heap Property

For every node i (except root):
```
arr[parent(i)] >= arr[i]
```

This ensures the maximum element is always at the root (index 0).

## Heapify Process

### What is Heapify?

**Heapify** is the process of converting a subtree into a heap. It assumes that the left and right subtrees are already heaps, but the root might violate the heap property.

### Top-Down Heapify (Sift Down)

```
1. Compare node with its children
2. If node < largest child, swap with that child
3. Recursively heapify the affected subtree
4. Continue until node is larger than children or becomes leaf
```

### Visual Example

Converting `[4, 10, 3, 5, 1]` into a max heap:

**Initial Array:**
```
       4
     /   \
   10     3
  /  \
 5    1
```

**Heapify from index 1 (node 10):**
```
Compare 10 with children 5 and 1
10 > 5 and 10 > 1 → Already satisfies heap property
```

**Heapify from index 0 (node 4):**
```
Compare 4 with children 10 and 3
10 > 4 → Swap 4 and 10

       10
     /   \
    4     3
   / \
  5   1

Now heapify node 4:
Compare 4 with children 5 and 1
5 > 4 → Swap 4 and 5

       10
     /   \
    5     3
   / \
  4   1

Final Max Heap: [10, 5, 3, 4, 1]
```

### Building a Heap

To build a heap from an unsorted array:
1. Start from the last non-leaf node: `(n/2 - 1)`
2. Heapify each node moving towards the root
3. Time complexity: O(n) - not O(n log n)!

## Implementation

```java
public class HeapSort {
    
    /**
     * Main heap sort function
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build max heap
        buildMaxHeap(arr, n);
        
        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(arr, 0, i);
            
            // Heapify reduced heap
            heapify(arr, i, 0);
        }
    }
    
    /**
     * Build max heap from unsorted array
     * Time Complexity: O(n)
     */
    private static void buildMaxHeap(int[] arr, int n) {
        // Start from last non-leaf node and heapify all nodes
        // Last non-leaf node: (n/2 - 1)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }
    
    /**
     * Heapify a subtree rooted at index i
     * n is the size of the heap
     * Time Complexity: O(log n)
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;           // Initialize largest as root
        int left = 2 * i + 1;      // Left child
        int right = 2 * i + 2;     // Right child
        
        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        // If largest is not root
        if (largest != i) {
            swap(arr, i, largest);
            
            // Recursively heapify the affected subtree
            heapify(arr, n, largest);
        }
    }
    
    /**
     * Iterative heapify (avoids recursion)
     */
    private static void heapifyIterative(int[] arr, int n, int i) {
        while (true) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            if (left < n && arr[left] > arr[largest]) {
                largest = left;
            }
            
            if (right < n && arr[right] > arr[largest]) {
                largest = right;
            }
            
            if (largest == i) {
                break;
            }
            
            swap(arr, i, largest);
            i = largest;
        }
    }
    
    /**
     * Heap sort using iterative heapify
     */
    public static void heapSortIterative(int[] arr) {
        int n = arr.length;
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyIterative(arr, n, i);
        }
        
        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyIterative(arr, i, 0);
        }
    }
    
    /**
     * Min heap sort (sorts in descending order)
     */
    public static void heapSortDescending(int[] arr) {
        int n = arr.length;
        
        // Build min heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            minHeapify(arr, n, i);
        }
        
        // Extract elements
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            minHeapify(arr, i, 0);
        }
    }
    
    /**
     * Min heapify for descending sort
     */
    private static void minHeapify(int[] arr, int n, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] < arr[smallest]) {
            smallest = left;
        }
        
        if (right < n && arr[right] < arr[smallest]) {
            smallest = right;
        }
        
        if (smallest != i) {
            swap(arr, i, smallest);
            minHeapify(arr, n, smallest);
        }
    }
    
    /**
     * Generic heap sort for any Comparable type
     */
    public static <T extends Comparable<T>> void genericHeapSort(T[] arr) {
        int n = arr.length;
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            genericHeapify(arr, n, i);
        }
        
        // Extract elements
        for (int i = n - 1; i > 0; i--) {
            T temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            genericHeapify(arr, i, 0);
        }
    }
    
    private static <T extends Comparable<T>> void genericHeapify(T[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left].compareTo(arr[largest]) > 0) {
            largest = left;
        }
        
        if (right < n && arr[right].compareTo(arr[largest]) > 0) {
            largest = right;
        }
        
        if (largest != i) {
            T temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            genericHeapify(arr, n, largest);
        }
    }
    
    /**
     * Priority Queue implementation using heap
     */
    static class MaxHeap {
        private int[] heap;
        private int size;
        private int capacity;
        
        public MaxHeap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.heap = new int[capacity];
        }
        
        public void insert(int value) {
            if (size == capacity) {
                throw new IllegalStateException("Heap is full");
            }
            
            // Insert at end
            heap[size] = value;
            size++;
            
            // Heapify up
            heapifyUp(size - 1);
        }
        
        public int extractMax() {
            if (size == 0) {
                throw new IllegalStateException("Heap is empty");
            }
            
            int max = heap[0];
            heap[0] = heap[size - 1];
            size--;
            
            // Heapify down
            heapifyDown(0);
            
            return max;
        }
        
        private void heapifyUp(int i) {
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (heap[i] <= heap[parent]) {
                    break;
                }
                swap(heap, i, parent);
                i = parent;
            }
        }
        
        private void heapifyDown(int i) {
            while (true) {
                int largest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < size && heap[left] > heap[largest]) {
                    largest = left;
                }
                
                if (right < size && heap[right] > heap[largest]) {
                    largest = right;
                }
                
                if (largest == i) {
                    break;
                }
                
                swap(heap, i, largest);
                i = largest;
            }
        }
        
        public int peek() {
            if (size == 0) {
                throw new IllegalStateException("Heap is empty");
            }
            return heap[0];
        }
        
        public int getSize() {
            return size;
        }
        
        public void printHeap() {
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Utility method to swap elements
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Utility method to print array
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    /**
     * Utility method to print generic array
     */
    public static <T> void printGenericArray(T[] arr) {
        for (T value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    /**
     * Visualize heap structure
     */
    public static void visualizeHeap(int[] arr) {
        int n = arr.length;
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("\nHeap structure:");
        
        int level = 0;
        int nodesInLevel = 1;
        int index = 0;
        
        while (index < n) {
            // Print spaces for formatting
            int spaces = (int) Math.pow(2, (int) (Math.log(n) / Math.log(2)) - level) - 1;
            for (int i = 0; i < spaces; i++) {
                System.out.print("  ");
            }
            
            // Print nodes at this level
            for (int i = 0; i < nodesInLevel && index < n; i++, index++) {
                System.out.print(arr[index] + "  ");
            }
            
            System.out.println();
            level++;
            nodesInLevel *= 2;
        }
    }
    
    /**
     * Demonstrate heap sort step by step
     */
    public static void heapSortWithSteps(int[] arr) {
        int n = arr.length;
        
        System.out.println("Initial array:");
        printArray(arr);
        
        System.out.println("\n--- Building Max Heap ---");
        // Build max heap with visualization
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
            System.out.println("After heapifying index " + i + ":");
            printArray(arr);
        }
        
        System.out.println("\n--- Extracting Elements ---");
        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            System.out.println("\nExtract max: " + arr[0]);
            swap(arr, 0, i);
            System.out.print("Array after swap: ");
            printArray(arr);
            
            heapify(arr, i, 0);
            System.out.print("After heapify: ");
            printArray(arr);
        }
        
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== HEAP SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic heap sort
        System.out.println("1. Basic Heap Sort:");
        int[] arr1 = {12, 11, 13, 5, 6, 7};
        System.out.print("Original: ");
        printArray(arr1);
        heapSort(arr1);
        System.out.print("Sorted: ");
        printArray(arr1);
        
        // Example 2: Already sorted array
        System.out.println("\n2. Already Sorted Array:");
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        System.out.print("Original: ");
        printArray(arr2);
        heapSort(arr2);
        System.out.print("Sorted: ");
        printArray(arr2);
        System.out.println("(Still O(n log n) - not adaptive)");
        
        // Example 3: Reverse sorted array
        System.out.println("\n3. Reverse Sorted Array:");
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.print("Original: ");
        printArray(arr3);
        heapSort(arr3);
        System.out.print("Sorted: ");
        printArray(arr3);
        
        // Example 4: Array with duplicates
        System.out.println("\n4. Array with Duplicates:");
        int[] arr4 = {5, 2, 8, 2, 9, 1, 5, 5};
        System.out.print("Original: ");
        printArray(arr4);
        heapSort(arr4);
        System.out.print("Sorted: ");
        printArray(arr4);
        
        // Example 5: Descending order
        System.out.println("\n5. Heap Sort - Descending Order:");
        int[] arr5 = {12, 11, 13, 5, 6, 7};
        System.out.print("Original: ");
        printArray(arr5);
        heapSortDescending(arr5);
        System.out.print("Sorted (desc): ");
        printArray(arr5);
        
        // Example 6: Iterative heap sort
        System.out.println("\n6. Iterative Heap Sort:");
        int[] arr6 = {64, 25, 12, 22, 11};
        System.out.print("Original: ");
        printArray(arr6);
        heapSortIterative(arr6);
        System.out.print("Sorted: ");
        printArray(arr6);
        
        // Example 7: Generic heap sort with Strings
        System.out.println("\n7. Generic Heap Sort with Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry"};
        System.out.print("Original: ");
        printGenericArray(strArr);
        genericHeapSort(strArr);
        System.out.print("Sorted: ");
        printGenericArray(strArr);
        
        // Example 8: Heap visualization
        System.out.println("\n8. Heap Visualization:");
        int[] arr7 = {4, 10, 3, 5, 1};
        System.out.println("Before building heap:");
        visualizeHeap(arr7);
        buildMaxHeap(arr7, arr7.length);
        System.out.println("\nAfter building max heap:");
        visualizeHeap(arr7);
        
        // Example 9: Step-by-step demonstration
        System.out.println("\n9. Step-by-Step Heap Sort:");
        int[] arr8 = {5, 3, 8, 4, 2};
        heapSortWithSteps(arr8);
        
        // Example 10: Priority Queue demonstration
        System.out.println("\n10. Priority Queue (Max Heap):");
        MaxHeap pq = new MaxHeap(10);
        int[] values = {3, 1, 6, 5, 2, 4};
        System.out.print("Inserting: ");
        for (int val : values) {
            System.out.print(val + " ");
            pq.insert(val);
        }
        System.out.println("\n\nExtracting in priority order:");
        while (pq.getSize() > 0) {
            System.out.print(pq.extractMax() + " ");
        }
        System.out.println();
        
        // Example 11: Large array performance
        System.out.println("\n11. Large Array Performance (10000 elements):");
        int[] large = new int[10000];
        for (int i = 0; i < 10000; i++) {
            large[i] = (int) (Math.random() * 10000);
        }
        long startTime = System.nanoTime();
        heapSort(large);
        long endTime = System.nanoTime();
        System.out.println("Sorted 10000 elements in " +
                         (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("First 10: " + java.util.Arrays.toString(
                         java.util.Arrays.copyOf(large, 10)));
        
        // Example 12: Finding K largest elements
        System.out.println("\n12. Finding K Largest Elements:");
        int[] arr9 = {3, 2, 1, 5, 6, 4};
        int k = 3;
        System.out.print("Array: ");
        printArray(arr9);
        // Build max heap and extract k elements
        buildMaxHeap(arr9, arr9.length);
        System.out.print(k + " largest elements: ");
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = arr9[0];
            swap(arr9, 0, arr9.length - 1 - i);
            heapify(arr9, arr9.length - 1 - i, 0);
        }
        printArray(result);
        
        // Example 13: Edge cases
        System.out.println("\n13. Edge Cases:");
        int[] arr10 = {42};
        System.out.print("Single element: ");
        printArray(arr10);
        heapSort(arr10);
        System.out.print("Sorted: ");
        printArray(arr10);
        
        int[] arr11 = {7, 3};
        System.out.print("Two elements: ");
        printArray(arr11);
        heapSort(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        // Example 14: Comparison with other O(n log n) algorithms
        System.out.println("\n14. Performance Comparison (1000 elements):");
        int[] test = new int[1000];
        for (int i = 0; i < 1000; i++) {
            test[i] = (int) (Math.random() * 1000);
        }
        
        int[] test1 = java.util.Arrays.copyOf(test, test.length);
        startTime = System.nanoTime();
        heapSort(test1);
        endTime = System.nanoTime();
        System.out.println("Heap Sort: " + (endTime - startTime) / 1000000.0 + " ms");
        
        System.out.println("\nHeap Sort characteristics:");
        System.out.println("- Guaranteed O(n log n)");
        System.out.println("- In-place (O(1) space)");
        System.out.println("- Not stable");
        System.out.println("- Not adaptive");
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n log n) | Building heap: O(n), extracting n elements: O(n log n) |
| **Average Case** | O(n log n) | Same as best case |
| **Worst Case** | O(n log n) | Guaranteed performance for all inputs |

**Detailed Analysis:**
- **Build Heap:** O(n) - not O(n log n) due to mathematical proof
- **Extract and Heapify:** O(log n) per extraction × n extractions = O(n log n)
- **Total:** O(n) + O(n log n) = O(n log n)

### Space Complexity

- **Auxiliary Space:** O(1) - Sorts in-place
- **Recursion Stack:** O(log n) - Height of heap for heapify
- **Total:** O(log n) space complexity
- **Iterative version:** O(1) - No recursion

### Stability

Heap Sort is **NOT STABLE** - may change relative order of equal elements due to the heapify operations that can move equal elements long distances.

## Advantages and Disadvantages

### Advantages

1. **Guaranteed O(n log n)** - No worst-case degradation
2. **In-place sorting** - O(1) auxiliary space (best among O(n log n) algorithms)
3. **Predictable performance** - Consistent across all input types
4. **No recursion overhead** - Can be implemented iteratively
5. **Memory efficient** - Minimal space requirements
6. **Foundation for Priority Queue** - Natural implementation

### Disadvantages

1. **Not stable** - Equal elements may be reordered
2. **Not adaptive** - No benefit from partially sorted data
3. **Poor cache performance** - Random memory access pattern
4. **Slower in practice** - Higher constants than Quick Sort
5. **Complex logic** - More difficult to understand and implement

## When to Use

### Ideal Use Cases

1. **Memory-constrained systems** - O(1) space with O(n log n) time
2. **Guaranteed performance needed** - No worst-case scenarios
3. **Embedded systems** - Predictable behavior
4. **Real-time systems** - Consistent timing
5. **Priority queue implementation** - Natural fit
6. **When stability not required** - And O(n log n) is needed

### When NOT to Use

1. **Stability required** - Use Merge Sort
2. **Cache performance critical** - Quick Sort is faster in practice
3. **Nearly sorted data** - Insertion Sort is better
4. **Small datasets** - Simple algorithms have lower overhead

### Used in Production

- **Linux kernel:** For certain sorting operations
- **Priority queues:** Foundation for heap-based implementations
- **Embedded systems:** Predictable performance
- **Real-time systems:** Guaranteed timing

## Practical Examples

### Example 1: K Largest/Smallest Elements

```java
// Find K largest elements using max heap
public static int[] findKLargest(int[] arr, int k) {
    buildMaxHeap(arr, arr.length);
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
        result[i] = arr[0];
        swap(arr, 0, arr.length - 1 - i);
        heapify(arr, arr.length - 1 - i, 0);
    }
    return result;
}
```

### Example 2: Priority Queue for Task Scheduling

```java
// Schedule tasks by priority
MaxHeap taskQueue = new MaxHeap(100);
taskQueue.insert(taskWithPriority5);
taskQueue.insert(taskWithPriority10);
taskQueue.insert(taskWithPriority3);

// Execute highest priority first
Task next = taskQueue.extractMax();
```

### Example 3: Median Maintenance

```java
// Maintain median using two heaps
MaxHeap smallerHalf = new MaxHeap(1000);
MinHeap largerHalf = new MinHeap(1000);
// Median is always at top of one heap
```

## Summary

Heap Sort is a robust sorting algorithm that guarantees O(n log n) time complexity with minimal space requirements. While not as fast as Quick Sort in average cases, its predictable performance and space efficiency make it valuable for specific scenarios.

**Key Takeaways:**
- Guaranteed O(n log n) for all inputs
- In-place with O(1) auxiliary space
- Not stable and not adaptive
- Foundation for priority queues and heap data structure
- Excellent for memory-constrained environments
- Predictable performance for real-time systems

Heap Sort's combination of guaranteed efficiency and minimal space usage makes it an important algorithm in computer science, particularly valuable when both time and space constraints must be met simultaneously.
