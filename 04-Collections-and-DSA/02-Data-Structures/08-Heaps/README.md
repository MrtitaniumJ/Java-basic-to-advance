# Heaps in Java

## Table of Contents
1. [Simple Explanation](#simple-explanation)
2. [Professional Definition](#professional-definition)
3. [Heap Properties](#heap-properties)
4. [Min-Heap vs Max-Heap](#min-heap-vs-max-heap)
5. [Heap Operations](#heap-operations)
6. [Java Implementations](#java-implementations)
7. [Heap Sort Algorithm](#heap-sort-algorithm)
8. [Applications](#applications)
9. [Interview Questions](#interview-questions)

---

## Simple Explanation

### Priority System Analogy

Think of a heap like a **hospital emergency room priority system**:

- **Max-Heap**: Like an emergency room where the most critical patient (highest priority) is always treated first
  - The doctor always sees the patient with the highest severity score
  - When a new patient arrives, they're inserted based on their condition
  - The most critical patient is always at the "top" of the queue

- **Min-Heap**: Like a task scheduler where the task with the earliest deadline (lowest value) is executed first
  - The system always picks the task with the smallest deadline number
  - New tasks are inserted and automatically positioned by priority
  - The most urgent task is always ready to be executed

**Key Concept**: A heap automatically maintains order so you can always quickly access the highest (or lowest) priority item without sorting the entire collection!

---

## Professional Definition

A **Heap** is a specialized tree-based data structure that satisfies the **heap property**. It is a complete binary tree where:

- **Max-Heap**: Every parent node has a value greater than or equal to its children
- **Min-Heap**: Every parent node has a value less than or equal to its children

Heaps are commonly used to implement **priority queues** and are fundamental to algorithms like **Heap Sort**.

**Time Complexity**:
- Insert: O(log n)
- Extract Min/Max: O(log n)
- Peek Min/Max: O(1)
- Heapify: O(n)

**Space Complexity**: O(n)

---

## Heap Properties

### 1. Complete Binary Tree
- All levels are completely filled except possibly the last level
- The last level is filled from left to right
- This property allows array representation

### 2. Heap Property

**Max-Heap Property**:
```
For every node i (except root):
A[Parent(i)] ≥ A[i]
```

**Min-Heap Property**:
```
For every node i (except root):
A[Parent(i)] ≤ A[i]
```

### 3. Array Representation
For node at index `i`:
- **Parent**: `(i - 1) / 2`
- **Left Child**: `2 * i + 1`
- **Right Child**: `2 * i + 2`

**Visual Example**:
```
        10                    Array: [10, 8, 6, 4, 5, 3, 2]
       /  \                   Index:  0   1  2  3  4  5  6
      8    6
     / \  / \
    4  5 3  2
```

---

## Min-Heap vs Max-Heap

### Max-Heap
```
         100
        /   \
       80    90
      / \   / \
     40 60 70 50
```
- **Root**: Largest element
- **Parent > Children**
- **Use Case**: Find maximum element, largest K elements

### Min-Heap
```
          10
        /   \
       20    30
      / \   / \
     40 50 60 70
```
- **Root**: Smallest element
- **Parent < Children**
- **Use Case**: Find minimum element, smallest K elements, scheduling

### Comparison Table

| Feature | Min-Heap | Max-Heap |
|---------|----------|----------|
| Root Element | Minimum | Maximum |
| Parent-Child Relationship | Parent ≤ Child | Parent ≥ Child |
| Extract Operation | Extracts minimum | Extracts maximum |
| Common Use | Priority queues, Dijkstra's | Job scheduling, Heap sort |

---

## Heap Operations

### 1. Insert (Bubble Up / Heapify Up)
**Process**:
1. Add element at the end (last position)
2. Compare with parent
3. Swap if heap property violated
4. Repeat until heap property satisfied

**Time Complexity**: O(log n)

### 2. Extract Min/Max (Bubble Down / Heapify Down)
**Process**:
1. Remove root element
2. Move last element to root
3. Compare with children
4. Swap with smaller/larger child (depending on heap type)
5. Repeat until heap property satisfied

**Time Complexity**: O(log n)

### 3. Heapify
Convert an arbitrary array into a heap.

**Process**:
1. Start from last non-leaf node
2. Apply heapify down on each node
3. Move towards root

**Time Complexity**: O(n)

### 4. Peek
Return root element without removing it.

**Time Complexity**: O(1)

---

## Java Implementations

### Min-Heap Implementation

```java
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Complete Min-Heap implementation
 * Maintains minimum element at root
 */
public class MinHeap {
    private ArrayList<Integer> heap;
    
    // Constructor
    public MinHeap() {
        this.heap = new ArrayList<>();
    }
    
    // Constructor with initial capacity
    public MinHeap(int capacity) {
        this.heap = new ArrayList<>(capacity);
    }
    
    // Helper method to get parent index
    private int parent(int index) {
        return (index - 1) / 2;
    }
    
    // Helper method to get left child index
    private int leftChild(int index) {
        return 2 * index + 1;
    }
    
    // Helper method to get right child index
    private int rightChild(int index) {
        return 2 * index + 2;
    }
    
    // Check if heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    // Get size of heap
    public int size() {
        return heap.size();
    }
    
    // Peek at minimum element (root)
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }
    
    // Insert element into heap
    public void insert(int value) {
        // Add element at the end
        heap.add(value);
        
        // Heapify up to maintain heap property
        heapifyUp(heap.size() - 1);
    }
    
    // Heapify up (bubble up)
    private void heapifyUp(int index) {
        int currentIndex = index;
        
        // Continue until we reach root or heap property is satisfied
        while (currentIndex > 0) {
            int parentIndex = parent(currentIndex);
            
            // If current element is smaller than parent, swap
            if (heap.get(currentIndex) < heap.get(parentIndex)) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;  // Heap property satisfied
            }
        }
    }
    
    // Extract minimum element (root)
    public int extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        // Store root value
        int min = heap.get(0);
        
        // Move last element to root
        int lastElement = heap.remove(heap.size() - 1);
        
        // If heap is not empty after removal, place last element at root
        if (!isEmpty()) {
            heap.set(0, lastElement);
            // Heapify down to maintain heap property
            heapifyDown(0);
        }
        
        return min;
    }
    
    // Heapify down (bubble down)
    private void heapifyDown(int index) {
        int currentIndex = index;
        int size = heap.size();
        
        while (true) {
            int leftChildIndex = leftChild(currentIndex);
            int rightChildIndex = rightChild(currentIndex);
            int smallest = currentIndex;
            
            // Find smallest among current, left child, and right child
            if (leftChildIndex < size && 
                heap.get(leftChildIndex) < heap.get(smallest)) {
                smallest = leftChildIndex;
            }
            
            if (rightChildIndex < size && 
                heap.get(rightChildIndex) < heap.get(smallest)) {
                smallest = rightChildIndex;
            }
            
            // If current is smallest, heap property is satisfied
            if (smallest == currentIndex) {
                break;
            }
            
            // Swap and continue
            swap(currentIndex, smallest);
            currentIndex = smallest;
        }
    }
    
    // Delete element at specific index
    public void delete(int index) {
        if (index < 0 || index >= heap.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        // Replace with last element
        int lastElement = heap.remove(heap.size() - 1);
        
        if (index < heap.size()) {
            int oldValue = heap.get(index);
            heap.set(index, lastElement);
            
            // Decide whether to heapify up or down
            if (lastElement < oldValue) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }
    }
    
    // Build heap from array
    public void buildHeap(int[] array) {
        heap.clear();
        for (int value : array) {
            heap.add(value);
        }
        
        // Start from last non-leaf node and heapify down
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    // Swap two elements
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    // Display heap as array
    public void display() {
        System.out.println(heap);
    }
    
    // Display heap as tree structure
    public void displayTree() {
        if (isEmpty()) {
            System.out.println("Heap is empty");
            return;
        }
        
        int height = (int) Math.ceil(Math.log(heap.size() + 1) / Math.log(2));
        displayTreeHelper(0, 0, height);
    }
    
    private void displayTreeHelper(int index, int level, int maxHeight) {
        if (index >= heap.size()) {
            return;
        }
        
        // Print right subtree
        displayTreeHelper(rightChild(index), level + 1, maxHeight);
        
        // Print current node with indentation
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(heap.get(index));
        
        // Print left subtree
        displayTreeHelper(leftChild(index), level + 1, maxHeight);
    }
}
```

### Max-Heap Implementation

```java
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Complete Max-Heap implementation
 * Maintains maximum element at root
 */
public class MaxHeap {
    private ArrayList<Integer> heap;
    
    // Constructor
    public MaxHeap() {
        this.heap = new ArrayList<>();
    }
    
    // Constructor with initial capacity
    public MaxHeap(int capacity) {
        this.heap = new ArrayList<>(capacity);
    }
    
    // Helper method to get parent index
    private int parent(int index) {
        return (index - 1) / 2;
    }
    
    // Helper method to get left child index
    private int leftChild(int index) {
        return 2 * index + 1;
    }
    
    // Helper method to get right child index
    private int rightChild(int index) {
        return 2 * index + 2;
    }
    
    // Check if heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    // Get size of heap
    public int size() {
        return heap.size();
    }
    
    // Peek at maximum element (root)
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }
    
    // Insert element into heap
    public void insert(int value) {
        // Add element at the end
        heap.add(value);
        
        // Heapify up to maintain heap property
        heapifyUp(heap.size() - 1);
    }
    
    // Heapify up (bubble up)
    private void heapifyUp(int index) {
        int currentIndex = index;
        
        // Continue until we reach root or heap property is satisfied
        while (currentIndex > 0) {
            int parentIndex = parent(currentIndex);
            
            // If current element is larger than parent, swap
            if (heap.get(currentIndex) > heap.get(parentIndex)) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;  // Heap property satisfied
            }
        }
    }
    
    // Extract maximum element (root)
    public int extractMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        // Store root value
        int max = heap.get(0);
        
        // Move last element to root
        int lastElement = heap.remove(heap.size() - 1);
        
        // If heap is not empty after removal, place last element at root
        if (!isEmpty()) {
            heap.set(0, lastElement);
            // Heapify down to maintain heap property
            heapifyDown(0);
        }
        
        return max;
    }
    
    // Heapify down (bubble down)
    private void heapifyDown(int index) {
        int currentIndex = index;
        int size = heap.size();
        
        while (true) {
            int leftChildIndex = leftChild(currentIndex);
            int rightChildIndex = rightChild(currentIndex);
            int largest = currentIndex;
            
            // Find largest among current, left child, and right child
            if (leftChildIndex < size && 
                heap.get(leftChildIndex) > heap.get(largest)) {
                largest = leftChildIndex;
            }
            
            if (rightChildIndex < size && 
                heap.get(rightChildIndex) > heap.get(largest)) {
                largest = rightChildIndex;
            }
            
            // If current is largest, heap property is satisfied
            if (largest == currentIndex) {
                break;
            }
            
            // Swap and continue
            swap(currentIndex, largest);
            currentIndex = largest;
        }
    }
    
    // Build heap from array
    public void buildHeap(int[] array) {
        heap.clear();
        for (int value : array) {
            heap.add(value);
        }
        
        // Start from last non-leaf node and heapify down
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    // Swap two elements
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    // Display heap as array
    public void display() {
        System.out.println(heap);
    }
    
    // Display heap as tree structure
    public void displayTree() {
        if (isEmpty()) {
            System.out.println("Heap is empty");
            return;
        }
        
        displayTreeHelper(0, 0);
    }
    
    private void displayTreeHelper(int index, int level) {
        if (index >= heap.size()) {
            return;
        }
        
        // Print right subtree
        displayTreeHelper(rightChild(index), level + 1);
        
        // Print current node with indentation
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(heap.get(index));
        
        // Print left subtree
        displayTreeHelper(leftChild(index), level + 1);
    }
}
```

### Priority Queue Using Java's Built-in Heap

```java
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Comparator;

/**
 * Examples using Java's built-in PriorityQueue
 * PriorityQueue is implemented as a Min-Heap by default
 */
public class PriorityQueueExamples {
    
    // Example 1: Min-Heap (default)
    public static void minHeapExample() {
        System.out.println("=== Min-Heap Example ===");
        
        // Create min-heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Insert elements
        minHeap.offer(30);
        minHeap.offer(10);
        minHeap.offer(50);
        minHeap.offer(20);
        minHeap.offer(40);
        
        System.out.println("Min-Heap: " + minHeap);
        
        // Extract elements (will be in ascending order)
        System.out.print("Extracted in order: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println("\n");
    }
    
    // Example 2: Max-Heap (using Collections.reverseOrder())
    public static void maxHeapExample() {
        System.out.println("=== Max-Heap Example ===");
        
        // Create max-heap
        PriorityQueue<Integer> maxHeap = 
            new PriorityQueue<>(Collections.reverseOrder());
        
        // Insert elements
        maxHeap.offer(30);
        maxHeap.offer(10);
        maxHeap.offer(50);
        maxHeap.offer(20);
        maxHeap.offer(40);
        
        System.out.println("Max-Heap: " + maxHeap);
        
        // Extract elements (will be in descending order)
        System.out.print("Extracted in order: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");
    }
    
    // Example 3: Custom comparator for complex objects
    static class Task {
        String name;
        int priority;
        
        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        
        @Override
        public String toString() {
            return name + "(P" + priority + ")";
        }
    }
    
    public static void customComparatorExample() {
        System.out.println("=== Custom Comparator Example ===");
        
        // Create priority queue with custom comparator
        // Lower priority number = higher priority
        PriorityQueue<Task> taskQueue = new PriorityQueue<>(
            Comparator.comparingInt(task -> task.priority)
        );
        
        // Add tasks
        taskQueue.offer(new Task("Email", 3));
        taskQueue.offer(new Task("Bug Fix", 1));
        taskQueue.offer(new Task("Meeting", 2));
        taskQueue.offer(new Task("Documentation", 4));
        
        System.out.println("Task Queue: " + taskQueue);
        
        // Process tasks by priority
        System.out.println("Processing tasks:");
        while (!taskQueue.isEmpty()) {
            System.out.println("  - " + taskQueue.poll());
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        minHeapExample();
        maxHeapExample();
        customComparatorExample();
    }
}
```

### Complete Demo Program

```java
/**
 * Comprehensive demonstration of heap operations
 */
public class HeapDemo {
    
    public static void main(String[] args) {
        demonstrateMinHeap();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateMaxHeap();
    }
    
    public static void demonstrateMinHeap() {
        System.out.println("=== MIN-HEAP DEMONSTRATION ===\n");
        
        MinHeap minHeap = new MinHeap();
        
        // Test 1: Insert elements
        System.out.println("1. Inserting elements: 50, 30, 70, 20, 40, 60, 80");
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            minHeap.insert(value);
            System.out.println("   Inserted " + value + ": " + 
                             "Heap = " + getHeapArray(minHeap));
        }
        
        // Test 2: Display as tree
        System.out.println("\n2. Heap Tree Structure:");
        minHeap.displayTree();
        
        // Test 3: Peek minimum
        System.out.println("\n3. Peek minimum: " + minHeap.peek());
        
        // Test 4: Extract minimum
        System.out.println("\n4. Extracting minimum elements:");
        for (int i = 0; i < 3; i++) {
            int min = minHeap.extractMin();
            System.out.println("   Extracted: " + min + 
                             ", Remaining: " + getHeapArray(minHeap));
        }
        
        // Test 5: Build heap from array
        System.out.println("\n5. Building heap from array [90, 15, 25, 5, 35, 45, 55]:");
        int[] array = {90, 15, 25, 5, 35, 45, 55};
        minHeap.buildHeap(array);
        System.out.println("   Result: " + getHeapArray(minHeap));
        minHeap.displayTree();
    }
    
    public static void demonstrateMaxHeap() {
        System.out.println("=== MAX-HEAP DEMONSTRATION ===\n");
        
        MaxHeap maxHeap = new MaxHeap();
        
        // Test 1: Insert elements
        System.out.println("1. Inserting elements: 50, 30, 70, 20, 40, 60, 80");
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int value : values) {
            maxHeap.insert(value);
            System.out.println("   Inserted " + value + ": " + 
                             "Heap = " + getHeapArray(maxHeap));
        }
        
        // Test 2: Display as tree
        System.out.println("\n2. Heap Tree Structure:");
        maxHeap.displayTree();
        
        // Test 3: Peek maximum
        System.out.println("\n3. Peek maximum: " + maxHeap.peek());
        
        // Test 4: Extract maximum
        System.out.println("\n4. Extracting maximum elements:");
        for (int i = 0; i < 3; i++) {
            int max = maxHeap.extractMax();
            System.out.println("   Extracted: " + max + 
                             ", Remaining: " + getHeapArray(maxHeap));
        }
        
        // Test 5: Build heap from array
        System.out.println("\n5. Building heap from array [10, 50, 30, 80, 40, 60, 70]:");
        int[] array = {10, 50, 30, 80, 40, 60, 70};
        maxHeap.buildHeap(array);
        System.out.println("   Result: " + getHeapArray(maxHeap));
        maxHeap.displayTree();
    }
    
    // Helper method to get heap as string (would need to be implemented)
    private static String getHeapArray(Object heap) {
        // This is a simplified version - actual implementation would 
        // need to access the internal ArrayList
        return heap.toString();
    }
}
```

---

## Heap Sort Algorithm

Heap Sort is a comparison-based sorting algorithm that uses a binary heap data structure.

**Algorithm**:
1. Build a max-heap from the input array
2. Repeatedly extract the maximum element and place it at the end
3. Reduce heap size and heapify

**Time Complexity**: O(n log n) - consistent for all cases  
**Space Complexity**: O(1) - in-place sorting  
**Stability**: Not stable

### Heap Sort Implementation

```java
/**
 * Complete Heap Sort implementation
 */
public class HeapSort {
    
    /**
     * Main heap sort method
     * Sorts array in ascending order using max-heap
     */
    public static void heapSort(int[] array) {
        int n = array.length;
        
        // Step 1: Build max-heap
        // Start from last non-leaf node and heapify down
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        
        System.out.println("After building max-heap: " + arrayToString(array));
        
        // Step 2: Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(array, 0, i);
            
            // Call heapify on reduced heap
            heapify(array, i, 0);
        }
    }
    
    /**
     * Heapify a subtree rooted at index i
     * n is the size of heap
     */
    private static void heapify(int[] array, int heapSize, int rootIndex) {
        int largest = rootIndex;        // Initialize largest as root
        int leftChild = 2 * rootIndex + 1;   // Left child index
        int rightChild = 2 * rootIndex + 2;  // Right child index
        
        // If left child is larger than root
        if (leftChild < heapSize && array[leftChild] > array[largest]) {
            largest = leftChild;
        }
        
        // If right child is larger than current largest
        if (rightChild < heapSize && array[rightChild] > array[largest]) {
            largest = rightChild;
        }
        
        // If largest is not root
        if (largest != rootIndex) {
            swap(array, rootIndex, largest);
            
            // Recursively heapify the affected subtree
            heapify(array, heapSize, largest);
        }
    }
    
    /**
     * Iterative heapify (alternative to recursive)
     */
    private static void heapifyIterative(int[] array, int heapSize, int rootIndex) {
        int currentIndex = rootIndex;
        
        while (true) {
            int largest = currentIndex;
            int leftChild = 2 * currentIndex + 1;
            int rightChild = 2 * currentIndex + 2;
            
            // Find largest among current, left child, and right child
            if (leftChild < heapSize && array[leftChild] > array[largest]) {
                largest = leftChild;
            }
            
            if (rightChild < heapSize && array[rightChild] > array[largest]) {
                largest = rightChild;
            }
            
            // If current is largest, we're done
            if (largest == currentIndex) {
                break;
            }
            
            // Swap and continue with the affected subtree
            swap(array, currentIndex, largest);
            currentIndex = largest;
        }
    }
    
    /**
     * Swap two elements in array
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Helper method to print array
     */
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Demonstration of heap sort with step-by-step output
     */
    public static void main(String[] args) {
        System.out.println("=== HEAP SORT DEMONSTRATION ===\n");
        
        // Test case 1: Random array
        int[] array1 = {12, 11, 13, 5, 6, 7};
        System.out.println("Test 1: Random Array");
        System.out.println("Original array: " + arrayToString(array1));
        heapSort(array1);
        System.out.println("Sorted array:   " + arrayToString(array1));
        System.out.println();
        
        // Test case 2: Already sorted
        int[] array2 = {1, 2, 3, 4, 5, 6};
        System.out.println("Test 2: Already Sorted");
        System.out.println("Original array: " + arrayToString(array2));
        heapSort(array2);
        System.out.println("Sorted array:   " + arrayToString(array2));
        System.out.println();
        
        // Test case 3: Reverse sorted
        int[] array3 = {9, 7, 5, 3, 1};
        System.out.println("Test 3: Reverse Sorted");
        System.out.println("Original array: " + arrayToString(array3));
        heapSort(array3);
        System.out.println("Sorted array:   " + arrayToString(array3));
        System.out.println();
        
        // Test case 4: Duplicates
        int[] array4 = {5, 2, 8, 2, 9, 1, 5, 5};
        System.out.println("Test 4: With Duplicates");
        System.out.println("Original array: " + arrayToString(array4));
        heapSort(array4);
        System.out.println("Sorted array:   " + arrayToString(array4));
    }
}
```

---

## Applications

### 1. Priority Queue Implementation

```java
/**
 * Task scheduler using priority queue
 */
class TaskScheduler {
    
    static class Task implements Comparable<Task> {
        String name;
        int priority;
        int executionTime;
        
        Task(String name, int priority, int executionTime) {
            this.name = name;
            this.priority = priority;
            this.executionTime = executionTime;
        }
        
        @Override
        public int compareTo(Task other) {
            // Higher priority first (lower number = higher priority)
            return Integer.compare(this.priority, other.priority);
        }
        
        @Override
        public String toString() {
            return String.format("%s (P%d, %dms)", name, priority, executionTime);
        }
    }
    
    public static void scheduleTasks() {
        PriorityQueue<Task> taskQueue = new PriorityQueue<>();
        
        // Add tasks with different priorities
        taskQueue.offer(new Task("Database Backup", 3, 1000));
        taskQueue.offer(new Task("Critical Bug Fix", 1, 300));
        taskQueue.offer(new Task("Email Processing", 4, 500));
        taskQueue.offer(new Task("Security Update", 1, 800));
        taskQueue.offer(new Task("UI Enhancement", 5, 400));
        taskQueue.offer(new Task("API Optimization", 2, 600));
        
        System.out.println("=== Task Scheduler ===\n");
        System.out.println("Executing tasks by priority:\n");
        
        int totalTime = 0;
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            totalTime += task.executionTime;
            System.out.printf("Executing: %-30s Time: %4dms | Total: %5dms%n", 
                            task, task.executionTime, totalTime);
        }
    }
}
```

### 2. Find K-th Largest Element

```java
/**
 * Find k-th largest element using heap
 */
class KthLargest {
    
    /**
     * Find k-th largest element using min-heap
     * Time: O(n log k), Space: O(k)
     */
    public static int findKthLargest(int[] nums, int k) {
        // Use min-heap of size k
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.offer(num);
            
            // Keep only k largest elements
            if (minHeap.size() > k) {
                minHeap.poll();  // Remove smallest
            }
        }
        
        // Root of min-heap is k-th largest
        return minHeap.peek();
    }
    
    /**
     * Find k-th largest element using max-heap
     * Time: O(n + k log n), Space: O(n)
     */
    public static int findKthLargestMaxHeap(int[] nums, int k) {
        // Build max-heap with all elements
        PriorityQueue<Integer> maxHeap = 
            new PriorityQueue<>(Collections.reverseOrder());
        
        for (int num : nums) {
            maxHeap.offer(num);
        }
        
        // Extract k-1 largest elements
        for (int i = 0; i < k - 1; i++) {
            maxHeap.poll();
        }
        
        // k-th largest is now at top
        return maxHeap.peek();
    }
    
    public static void demo() {
        System.out.println("=== Find K-th Largest Element ===\n");
        
        int[] nums = {3, 2, 1, 5, 6, 4};
        System.out.println("Array: " + Arrays.toString(nums));
        
        for (int k = 1; k <= 3; k++) {
            int result = findKthLargest(nums, k);
            System.out.printf("%d-th largest element: %d%n", k, result);
        }
        
        System.out.println("\nArray: [3, 2, 3, 1, 2, 4, 5, 5, 6]");
        int[] nums2 = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        System.out.println("4-th largest element: " + findKthLargest(nums2, 4));
    }
}
```

### 3. Find Median from Data Stream

```java
import java.util.PriorityQueue;
import java.util.Collections;

/**
 * MedianFinder - finds median from a stream of numbers
 * Uses two heaps: max-heap for lower half, min-heap for upper half
 */
class MedianFinder {
    // Max-heap for lower half of numbers
    private PriorityQueue<Integer> maxHeap;
    
    // Min-heap for upper half of numbers
    private PriorityQueue<Integer> minHeap;
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }
    
    /**
     * Add number to data structure
     * Time: O(log n)
     */
    public void addNum(int num) {
        // Add to max-heap (lower half) first
        maxHeap.offer(num);
        
        // Balance: move largest from lower half to upper half
        minHeap.offer(maxHeap.poll());
        
        // Maintain size property: maxHeap.size() >= minHeap.size()
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    /**
     * Find median of all elements
     * Time: O(1)
     */
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            // Odd number of elements
            return maxHeap.peek();
        } else {
            // Even number of elements
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
    
    public static void demo() {
        System.out.println("=== Find Median from Data Stream ===\n");
        
        MedianFinder mf = new MedianFinder();
        
        int[] stream = {5, 15, 1, 3, 8, 7, 9, 10, 20};
        
        System.out.println("Adding numbers and finding median:");
        for (int num : stream) {
            mf.addNum(num);
            System.out.printf("Added %2d | Median: %.1f%n", num, mf.findMedian());
        }
    }
}
```

### 4. Merge K Sorted Lists

```java
import java.util.PriorityQueue;

/**
 * Merge k sorted linked lists using heap
 */
class MergeKSortedLists {
    
    static class ListNode {
        int val;
        ListNode next;
        
        ListNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Merge k sorted linked lists
     * Time: O(N log k) where N is total number of nodes
     * Space: O(k) for the heap
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Min-heap to store nodes
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.val, b.val)
        );
        
        // Add first node of each list to heap
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }
        
        // Dummy node for result
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Extract min and add next node from same list
        while (!minHeap.isEmpty()) {
            ListNode minNode = minHeap.poll();
            current.next = minNode;
            current = current.next;
            
            // Add next node from the same list
            if (minNode.next != null) {
                minHeap.offer(minNode.next);
            }
        }
        
        return dummy.next;
    }
    
    // Helper method to create linked list from array
    private static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    // Helper method to print linked list
    private static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
    
    public static void demo() {
        System.out.println("=== Merge K Sorted Lists ===\n");
        
        // Create k sorted lists
        ListNode[] lists = new ListNode[3];
        lists[0] = createList(new int[]{1, 4, 5});
        lists[1] = createList(new int[]{1, 3, 4});
        lists[2] = createList(new int[]{2, 6});
        
        System.out.println("Input Lists:");
        for (int i = 0; i < lists.length; i++) {
            System.out.print("List " + (i + 1) + ": ");
            printList(lists[i]);
        }
        
        ListNode result = mergeKLists(lists);
        
        System.out.println("\nMerged List:");
        printList(result);
    }
}
```

### 5. Top K Frequent Elements

```java
import java.util.*;

/**
 * Find top k frequent elements using heap
 */
class TopKFrequent {
    
    /**
     * Find k most frequent elements
     * Time: O(n log k), Space: O(n)
     */
    public static List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Min-heap based on frequency (keep k largest frequencies)
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Extract elements
        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().getKey());
        }
        
        Collections.reverse(result);  // Descending order
        return result;
    }
    
    public static void demo() {
        System.out.println("=== Top K Frequent Elements ===\n");
        
        int[] nums = {1, 1, 1, 2, 2, 3, 4, 4, 4, 4};
        int k = 2;
        
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("K = " + k);
        
        List<Integer> result = topKFrequent(nums, k);
        System.out.println("Top " + k + " frequent elements: " + result);
    }
}
```

### Applications Demo Main Class

```java
import java.util.Arrays;

/**
 * Main class to demonstrate all heap applications
 */
public class HeapApplications {
    
    public static void main(String[] args) {
        // Application 1: Task Scheduler
        TaskScheduler.scheduleTasks();
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Application 2: K-th Largest Element
        KthLargest.demo();
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Application 3: Median Finder
        MedianFinder.demo();
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Application 4: Merge K Sorted Lists
        MergeKSortedLists.demo();
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Application 5: Top K Frequent
        TopKFrequent.demo();
    }
}
```

---

## Interview Questions

### Basic Level

#### 1. What is a Heap? Explain Min-Heap and Max-Heap.
**Answer**: A heap is a complete binary tree that satisfies the heap property. In a Min-Heap, every parent node is smaller than or equal to its children (root is minimum). In a Max-Heap, every parent node is greater than or equal to its children (root is maximum).

#### 2. What is the time complexity of heap operations?
**Answer**:
- Insert: O(log n)
- Extract Min/Max: O(log n)
- Peek: O(1)
- Build Heap: O(n)
- Heapify: O(log n)

#### 3. How is a heap stored in memory?
**Answer**: A heap is stored as an array where for element at index i:
- Parent: `(i-1)/2`
- Left child: `2*i + 1`
- Right child: `2*i + 2`

#### 4. What is the difference between a heap and a binary search tree?
**Answer**:
- **Heap**: Parent-child relationship (parent > or < children), used for priority operations
- **BST**: Left-right ordering (left < parent < right), used for searching
- Heap has O(1) peek, BST has O(log n) search

### Intermediate Level

#### 5. Implement a function to find the k-th largest element in an unsorted array.
```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    
    return minHeap.peek();
}
```

#### 6. How would you merge k sorted arrays using a heap?
**Answer**: Use a min-heap to keep track of the smallest element from each array. Extract minimum, add to result, and insert the next element from that array.

```java
public int[] mergeKSortedArrays(int[][] arrays) {
    PriorityQueue<int[]> minHeap = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]  // Compare by value
    );
    
    int totalSize = 0;
    // Add first element of each array with [value, arrayIdx, elemIdx]
    for (int i = 0; i < arrays.length; i++) {
        if (arrays[i].length > 0) {
            minHeap.offer(new int[]{arrays[i][0], i, 0});
            totalSize += arrays[i].length;
        }
    }
    
    int[] result = new int[totalSize];
    int idx = 0;
    
    while (!minHeap.isEmpty()) {
        int[] current = minHeap.poll();
        result[idx++] = current[0];
        
        int arrayIdx = current[1];
        int elemIdx = current[2];
        
        // Add next element from same array
        if (elemIdx + 1 < arrays[arrayIdx].length) {
            minHeap.offer(new int[]{
                arrays[arrayIdx][elemIdx + 1],
                arrayIdx,
                elemIdx + 1
            });
        }
    }
    
    return result;
}
```

#### 7. Explain heap sort. What are its advantages and disadvantages?
**Answer**:
- **Process**: Build max-heap, repeatedly extract max and place at end
- **Advantages**: O(n log n) in all cases, in-place, no extra space
- **Disadvantages**: Not stable, slower than quicksort in practice, poor cache performance

### Advanced Level

#### 8. Design a data structure that supports finding the median in O(1) time.
**Answer**: Use two heaps - max-heap for lower half, min-heap for upper half.

```java
class MedianFinder {
    PriorityQueue<Integer> maxHeap;  // Lower half
    PriorityQueue<Integer> minHeap;  // Upper half
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
```

#### 9. Find the k closest points to the origin.
```java
public int[][] kClosest(int[][] points, int k) {
    // Max-heap based on distance
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
    );
    
    for (int[] point : points) {
        maxHeap.offer(point);
        if (maxHeap.size() > k) {
            maxHeap.poll();
        }
    }
    
    int[][] result = new int[k][2];
    for (int i = 0; i < k; i++) {
        result[i] = maxHeap.poll();
    }
    
    return result;
}
```

#### 10. Implement a Min Stack that supports push, pop, top, and retrieving minimum in O(1).
```java
class MinStack {
    Stack<Integer> stack;
    PriorityQueue<Integer> minHeap;
    
    public MinStack() {
        stack = new Stack<>();
        minHeap = new PriorityQueue<>();
    }
    
    public void push(int val) {
        stack.push(val);
        minHeap.offer(val);
    }
    
    public void pop() {
        int val = stack.pop();
        minHeap.remove(val);
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minHeap.peek();
    }
}
```

### Expert Level

#### 11. Design a system to handle real-time stream of data and find the k most frequent elements at any time.
**Answer**: Combine frequency map with heap. Use min-heap of size k to track top k frequent elements.

#### 12. Explain how heaps are used in Dijkstra's shortest path algorithm.
**Answer**: A min-heap (priority queue) is used to always process the vertex with minimum distance. Each vertex is inserted with its distance, and we extract minimum distance vertex, update neighbors, and repeat.

#### 13. What is a Fibonacci Heap? How does it differ from a binary heap?
**Answer**:
- **Binary Heap**: Simple structure, O(log n) operations
- **Fibonacci Heap**: More complex, amortized O(1) for insert/decrease-key, O(log n) for delete-min
- Used in advanced graph algorithms where decrease-key is frequent

#### 14. How would you implement an external sort for a file too large to fit in memory?
**Answer**: Use heap to perform k-way merge:
1. Split file into chunks that fit in memory
2. Sort each chunk and write to disk
3. Use min-heap to merge k sorted chunks
4. Read one element from each chunk into heap
5. Extract min, write to output, load next from that chunk

---

## Summary

### Key Takeaways

1. **Heaps are efficient** for priority-based operations with O(log n) insert/extract
2. **Complete binary tree** structure allows array representation
3. **Two types**: Min-Heap (minimum at root) and Max-Heap (maximum at root)
4. **Java's PriorityQueue** is a min-heap by default
5. **Common applications**: Priority queues, heap sort, k-th largest, median finding

### When to Use Heaps

✅ **Use Heaps When**:
- Need to repeatedly find/remove min or max element
- Implementing priority queue
- Finding k largest/smallest elements
- Merging k sorted sequences
- Scheduling tasks by priority

❌ **Don't Use Heaps When**:
- Need to search for arbitrary elements (use hash table or BST)
- Need all elements in sorted order (use sorting algorithm)
- Need range queries (use segment tree or BST)

### Comparison with Other Data Structures

| Operation | Heap | BST | Array (sorted) |
|-----------|------|-----|----------------|
| Find Min/Max | O(1) | O(log n) | O(1) |
| Insert | O(log n) | O(log n) | O(n) |
| Delete Min/Max | O(log n) | O(log n) | O(n) |
| Search | O(n) | O(log n) | O(log n) |
| Space | O(n) | O(n) | O(n) |

---

## Practice Resources

### Recommended Problems
1. Kth Largest Element in an Array (LeetCode 215)
2. Top K Frequent Elements (LeetCode 347)
3. Merge k Sorted Lists (LeetCode 23)
4. Find Median from Data Stream (LeetCode 295)
5. Sliding Window Maximum (LeetCode 239)
6. Task Scheduler (LeetCode 621)
7. Reorganize String (LeetCode 767)
8. K Closest Points to Origin (LeetCode 973)
9. Last Stone Weight (LeetCode 1046)
10. Kth Largest Element in a Stream (LeetCode 703)

### Next Topics
- [Binary Search Trees](../05-Binary-Search-Trees/README.md)
- [Tries](../09-Tries/README.md)
- [Graph Data Structures](../10-Graphs/README.md)

---

**Last Updated**: January 2026  
**Author**: Java Learning Path  
**Difficulty**: Intermediate to Advanced
