# Merge Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [Divide and Conquer Strategy](#divide-and-conquer-strategy)
4. [How Merge Sort Works](#how-merge-sort-works)
5. [Implementation](#implementation)
6. [Complexity Analysis](#complexity-analysis)
7. [Advantages and Disadvantages](#advantages-and-disadvantages)
8. [When to Use](#when-to-use)
9. [Practical Examples](#practical-examples)

## Introduction

Merge Sort is one of the most efficient and reliable sorting algorithms, based on the **Divide and Conquer** paradigm. It was invented by John von Neumann in 1945 and remains one of the most important algorithms in computer science. Unlike simple O(n²) algorithms, Merge Sort guarantees O(n log n) performance in all cases, making it suitable for large datasets.

The algorithm works by recursively dividing the array into two halves, sorting them separately, and then merging the sorted halves back together. This elegant approach ensures consistent performance regardless of input data characteristics.

## Algorithm Explanation

### Core Concept

Merge Sort follows three fundamental steps:

1. **Divide:** Split the array into two halves
2. **Conquer:** Recursively sort each half
3. **Combine:** Merge the two sorted halves into one sorted array

The key insight is that merging two sorted arrays is much simpler and more efficient than sorting an unsorted array directly.

### Step-by-Step Process

1. **Base Case:** If array has 0 or 1 element, it's already sorted
2. **Divide:** Find the middle point and divide array into two halves
3. **Recursive Sort:** Recursively call merge sort for both halves
4. **Merge:** Combine the two sorted halves into a single sorted array
5. **Return:** The fully sorted array

### Key Characteristics

- **Divide and Conquer:** Breaks problem into smaller subproblems
- **Stable:** Maintains relative order of equal elements
- **Predictable:** Always O(n log n), no best/worst case variance
- **External sorting:** Excellent for sorting data that doesn't fit in memory

## Divide and Conquer Strategy

### The Three Steps

#### 1. Divide
```
Array: [38, 27, 43, 3, 9, 82, 10]
                   ↓
        [38, 27, 43, 3] | [9, 82, 10]
```

#### 2. Conquer
```
Recursively sort each half:
[38, 27, 43, 3] → [3, 27, 38, 43]
[9, 82, 10]     → [9, 10, 82]
```

#### 3. Combine
```
Merge sorted halves:
[3, 27, 38, 43] + [9, 10, 82] → [3, 9, 10, 27, 38, 43, 82]
```

### Recursion Tree

```
                [38, 27, 43, 3, 9, 82, 10]
                          /\
                         /  \
              [38, 27, 43, 3]  [9, 82, 10]
                    /\              /\
                   /  \            /  \
            [38, 27]  [43, 3]   [9, 82] [10]
              /\        /\        /\
             /  \      /  \      /  \
          [38] [27] [43] [3]  [9] [82]

Height of tree: log₂(n)
Operations at each level: n
Total: O(n log n)
```

## How Merge Sort Works

### Visual Example

Sorting `[38, 27, 43, 3, 9, 82, 10]`:

**Level 0 (Initial):**
```
[38, 27, 43, 3, 9, 82, 10]
```

**Level 1 (Divide):**
```
[38, 27, 43, 3] | [9, 82, 10]
```

**Level 2 (Divide):**
```
[38, 27] | [43, 3] | [9, 82] | [10]
```

**Level 3 (Divide to base case):**
```
[38] | [27] | [43] | [3] | [9] | [82] | [10]
```

**Level 2 (Merge back):**
```
[27, 38] | [3, 43] | [9, 82] | [10]
```

**Level 1 (Merge back):**
```
[3, 27, 38, 43] | [9, 10, 82]
```

**Level 0 (Final merge):**
```
[3, 9, 10, 27, 38, 43, 82]
```

### Merging Process

When merging `[27, 38]` and `[3, 43]`:

```
Left:  [27, 38]  i=0
Right: [3, 43]   j=0
Result: []

Step 1: Compare 27 and 3 → 3 is smaller → Result: [3]
Step 2: Compare 27 and 43 → 27 is smaller → Result: [3, 27]
Step 3: Compare 38 and 43 → 38 is smaller → Result: [3, 27, 38]
Step 4: Right array exhausted → Add remaining: [3, 27, 38, 43]
```

## Implementation

```java
public class MergeSort {
    
    /**
     * Main merge sort function - top-down approach
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(n)
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSortHelper(arr, temp, 0, arr.length - 1);
    }
    
    /**
     * Recursive helper function for merge sort
     */
    private static void mergeSortHelper(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2; // Avoid overflow
            
            // Sort first half
            mergeSortHelper(arr, temp, left, mid);
            
            // Sort second half
            mergeSortHelper(arr, temp, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, temp, left, mid, right);
        }
    }
    
    /**
     * Merge two sorted subarrays
     * arr[left..mid] and arr[mid+1..right]
     */
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // Copy elements to temporary array
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }
        
        int i = left;        // Index for left subarray
        int j = mid + 1;     // Index for right subarray
        int k = left;        // Index for merged array
        
        // Merge back to arr
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }
        
        // Copy remaining elements from left subarray
        while (i <= mid) {
            arr[k++] = temp[i++];
        }
        
        // Copy remaining elements from right subarray (optional, already in place)
        while (j <= right) {
            arr[k++] = temp[j++];
        }
    }
    
    /**
     * Alternative merge sort without auxiliary array in recursion
     * Creates temp array only once
     */
    public static void mergeSortOptimized(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSortOptimizedHelper(arr, temp, 0, arr.length - 1);
    }
    
    private static void mergeSortOptimizedHelper(int[] arr, int[] temp, 
                                                  int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortOptimizedHelper(arr, temp, left, mid);
            mergeSortOptimizedHelper(arr, temp, mid + 1, right);
            mergeOptimized(arr, temp, left, mid, right);
        }
    }
    
    /**
     * Optimized merge with early termination
     */
    private static void mergeOptimized(int[] arr, int[] temp, 
                                       int left, int mid, int right) {
        // Optimization: if already sorted, skip merge
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }
        
        // Copy only the left half to temp
        for (int i = left; i <= mid; i++) {
            temp[i] = arr[i];
        }
        
        int i = left;
        int j = mid + 1;
        int k = left;
        
        while (i <= mid && j <= right) {
            if (temp[i] <= arr[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }
        
        // Copy remaining from temp (left half)
        while (i <= mid) {
            arr[k++] = temp[i++];
        }
    }
    
    /**
     * Bottom-up (iterative) merge sort
     * Non-recursive implementation
     */
    public static void mergeSortBottomUp(int[] arr) {
        int n = arr.length;
        int[] temp = new int[n];
        
        // Start with subarrays of size 1, double each iteration
        for (int size = 1; size < n; size *= 2) {
            // Pick starting index of left subarray
            for (int left = 0; left < n - size; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                
                merge(arr, temp, left, mid, right);
            }
        }
    }
    
    /**
     * Merge sort for linked list
     * O(n log n) time, O(log n) space (recursion only)
     */
    static class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public static Node mergeSortLinkedList(Node head) {
        // Base case
        if (head == null || head.next == null) {
            return head;
        }
        
        // Find middle point
        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;
        middle.next = null;
        
        // Sort left half
        Node left = mergeSortLinkedList(head);
        
        // Sort right half
        Node right = mergeSortLinkedList(nextOfMiddle);
        
        // Merge sorted halves
        return mergeLists(left, right);
    }
    
    /**
     * Find middle of linked list using slow-fast pointer
     */
    private static Node getMiddle(Node head) {
        if (head == null) return head;
        
        Node slow = head;
        Node fast = head.next;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Merge two sorted linked lists
     */
    private static Node mergeLists(Node left, Node right) {
        Node dummy = new Node(0);
        Node current = dummy;
        
        while (left != null && right != null) {
            if (left.data <= right.data) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }
        
        // Attach remaining
        if (left != null) {
            current.next = left;
        } else {
            current.next = right;
        }
        
        return dummy.next;
    }
    
    /**
     * Generic merge sort for any Comparable type
     */
    public static <T extends Comparable<T>> void genericMergeSort(T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[arr.length];
        genericMergeSortHelper(arr, temp, 0, arr.length - 1);
    }
    
    private static <T extends Comparable<T>> void genericMergeSortHelper(
            T[] arr, T[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            genericMergeSortHelper(arr, temp, left, mid);
            genericMergeSortHelper(arr, temp, mid + 1, right);
            genericMerge(arr, temp, left, mid, right);
        }
    }
    
    private static <T extends Comparable<T>> void genericMerge(
            T[] arr, T[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }
        
        int i = left, j = mid + 1, k = left;
        
        while (i <= mid && j <= right) {
            if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }
        
        while (i <= mid) {
            arr[k++] = temp[i++];
        }
        
        while (j <= right) {
            arr[k++] = temp[j++];
        }
    }
    
    /**
     * Hybrid merge sort with insertion sort for small subarrays
     * More efficient in practice
     */
    private static final int INSERTION_SORT_THRESHOLD = 10;
    
    public static void hybridMergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        hybridMergeSortHelper(arr, temp, 0, arr.length - 1);
    }
    
    private static void hybridMergeSortHelper(int[] arr, int[] temp, 
                                              int left, int right) {
        // Use insertion sort for small subarrays
        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, left, right);
            return;
        }
        
        if (left < right) {
            int mid = left + (right - left) / 2;
            hybridMergeSortHelper(arr, temp, left, mid);
            hybridMergeSortHelper(arr, temp, mid + 1, right);
            merge(arr, temp, left, mid, right);
        }
    }
    
    /**
     * Insertion sort for small subarrays
     */
    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
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
     * Utility to print linked list
     */
    public static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Demonstrate merge sort with visualization
     */
    public static void mergeSortWithVisualization(int[] arr) {
        System.out.println("Initial array:");
        printArray(arr);
        System.out.println("\n--- Merge Sort Process ---");
        mergeSortVisualHelper(arr, 0, arr.length - 1, 0);
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    private static void mergeSortVisualHelper(int[] arr, int left, int right, int level) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Print dividing step
            System.out.print("Level " + level + " - Divide: [");
            for (int i = left; i <= right; i++) {
                System.out.print(arr[i]);
                if (i < right) System.out.print(", ");
            }
            System.out.println("]");
            
            mergeSortVisualHelper(arr, left, mid, level + 1);
            mergeSortVisualHelper(arr, mid + 1, right, level + 1);
            
            // Merge
            int[] temp = new int[arr.length];
            merge(arr, temp, left, mid, right);
            
            // Print merging step
            System.out.print("Level " + level + " - Merge: [");
            for (int i = left; i <= right; i++) {
                System.out.print(arr[i]);
                if (i < right) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== MERGE SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic merge sort
        System.out.println("1. Basic Merge Sort:");
        int[] arr1 = {38, 27, 43, 3, 9, 82, 10};
        System.out.print("Original array: ");
        printArray(arr1);
        mergeSort(arr1);
        System.out.print("Sorted array: ");
        printArray(arr1);
        
        // Example 2: Already sorted array
        System.out.println("\n2. Already Sorted Array:");
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("Original array: ");
        printArray(arr2);
        mergeSort(arr2);
        System.out.print("Sorted array: ");
        printArray(arr2);
        System.out.println("(Still O(n log n) - not adaptive)");
        
        // Example 3: Reverse sorted array
        System.out.println("\n3. Reverse Sorted Array:");
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.print("Original array: ");
        printArray(arr3);
        mergeSort(arr3);
        System.out.print("Sorted array: ");
        printArray(arr3);
        
        // Example 4: Array with duplicates
        System.out.println("\n4. Array with Duplicates:");
        int[] arr4 = {5, 2, 8, 2, 9, 1, 5, 5};
        System.out.print("Original array: ");
        printArray(arr4);
        mergeSort(arr4);
        System.out.print("Sorted array: ");
        printArray(arr4);
        
        // Example 5: Large array performance
        System.out.println("\n5. Large Array Performance Test:");
        int[] arr5 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arr5[i] = (int)(Math.random() * 1000);
        }
        long startTime = System.nanoTime();
        mergeSort(arr5);
        long endTime = System.nanoTime();
        System.out.println("Sorted 1000 elements in " + 
                         (endTime - startTime) / 1000000.0 + " ms");
        
        // Example 6: Bottom-up merge sort
        System.out.println("\n6. Bottom-Up (Iterative) Merge Sort:");
        int[] arr6 = {12, 11, 13, 5, 6, 7};
        System.out.print("Original array: ");
        printArray(arr6);
        mergeSortBottomUp(arr6);
        System.out.print("Sorted array: ");
        printArray(arr6);
        
        // Example 7: Optimized merge sort
        System.out.println("\n7. Optimized Merge Sort:");
        int[] arr7 = {64, 25, 12, 22, 11, 90, 88};
        System.out.print("Original array: ");
        printArray(arr7);
        mergeSortOptimized(arr7);
        System.out.print("Sorted array: ");
        printArray(arr7);
        
        // Example 8: Hybrid merge sort
        System.out.println("\n8. Hybrid Merge Sort (with Insertion Sort):");
        int[] arr8 = new int[100];
        for (int i = 0; i < 100; i++) {
            arr8[i] = (int)(Math.random() * 100);
        }
        System.out.println("Sorting 100 random elements...");
        startTime = System.nanoTime();
        hybridMergeSort(arr8);
        endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) / 1000.0 + " microseconds");
        System.out.println("First 10 elements: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(arr8[i] + " ");
        }
        System.out.println("...");
        
        // Example 9: Generic merge sort with Strings
        System.out.println("\n9. Generic Merge Sort with Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry", "fig"};
        System.out.print("Original array: ");
        printGenericArray(strArr);
        genericMergeSort(strArr);
        System.out.print("Sorted array: ");
        printGenericArray(strArr);
        
        // Example 10: Linked list merge sort
        System.out.println("\n10. Merge Sort on Linked List:");
        Node head = new Node(5);
        head.next = new Node(3);
        head.next.next = new Node(8);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(2);
        head.next.next.next.next.next = new Node(7);
        System.out.print("Original list: ");
        printList(head);
        head = mergeSortLinkedList(head);
        System.out.print("Sorted list: ");
        printList(head);
        
        // Example 11: Visualization
        System.out.println("\n11. Merge Sort with Visualization:");
        int[] arr9 = {5, 3, 8, 4, 2};
        mergeSortWithVisualization(arr9);
        
        // Example 12: Single and two elements
        System.out.println("\n12. Edge Cases:");
        int[] arr10 = {42};
        System.out.print("Single element: ");
        printArray(arr10);
        mergeSort(arr10);
        System.out.print("Sorted: ");
        printArray(arr10);
        
        int[] arr11 = {7, 3};
        System.out.print("Two elements: ");
        printArray(arr11);
        mergeSort(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        // Example 13: Stability demonstration
        System.out.println("\n13. Stability Test:");
        int[] arr12 = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        System.out.print("Original: ");
        printArray(arr12);
        mergeSort(arr12);
        System.out.print("Sorted (stable): ");
        printArray(arr12);
        System.out.println("(Equal elements maintain relative order)");
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n log n) | Tree height: log n, work per level: n |
| **Average Case** | O(n log n) | Same performance for all inputs |
| **Worst Case** | O(n log n) | Guaranteed performance |

**Detailed Analysis:**
- **Tree height:** log₂(n) levels of recursion
- **Work per level:** O(n) comparisons and copies
- **Total:** O(n) × O(log n) = O(n log n)
- **Recurrence relation:** T(n) = 2T(n/2) + O(n)

### Space Complexity

- **Auxiliary Space:** O(n) - Requires temporary array for merging
- **Recursion Stack:** O(log n) - Depth of recursion tree
- **Total:** O(n) space complexity
- **Linked List Version:** O(log n) - Only recursion stack, no extra array

### Stability

Merge Sort is **STABLE** - it maintains relative order of equal elements because we use `<=` in comparison, always taking from the left array first when equal.

## Advantages and Disadvantages

### Advantages

1. **Guaranteed O(n log n)** - Predictable performance for all inputs
2. **Stable sorting** - Preserves relative order of equal elements
3. **Efficient for large datasets** - Much better than O(n²) algorithms
4. **Parallelizable** - Left and right halves can be sorted independently
5. **External sorting** - Excellent for data that doesn't fit in memory
6. **Predictable** - No worst-case degradation unlike Quick Sort
7. **Good for linked lists** - O(log n) space instead of O(n)

### Disadvantages

1. **Space complexity O(n)** - Requires additional memory
2. **Not in-place** - Uses extra array for merging
3. **Slower for small arrays** - Higher overhead than simple algorithms
4. **Not adaptive** - Doesn't benefit from partially sorted data
5. **More complex** - Harder to implement than simple algorithms

## When to Use

### Ideal Use Cases

1. **Large datasets** - Guaranteed O(n log n) performance
2. **Stability required** - When equal elements must maintain order
3. **Linked list sorting** - Excellent space complexity for lists
4. **External sorting** - Sorting data larger than memory
5. **Parallel processing** - Easy to parallelize
6. **Predictable performance needed** - No worst-case scenarios
7. **Database sorting** - Used in many database systems

### When NOT to Use

1. **Memory-constrained** - Use Heap Sort (O(1) space)
2. **Small arrays** - Insertion Sort is faster
3. **In-place requirement** - Use Quick Sort or Heap Sort
4. **Average case priority** - Quick Sort is typically faster in practice

### Used in Production

- **Java:** `Arrays.sort()` for objects (stable TimSort, based on merge sort)
- **Python:** `sorted()` and `list.sort()` use TimSort
- **Perl, PHP:** Use merge sort for stable sorting
- **External sorting algorithms:** Databases and file systems

## Practical Examples

### Example 1: Sorting Large Files

```java
// External merge sort for files too large for memory
// Read chunks, sort each chunk, merge sorted chunks
```

### Example 2: Stable Sorting of Records

```java
// Sort student records by grade, maintaining enrollment order
class Student {
    String name;
    int grade;
    int enrollmentOrder;
}
// Merge sort preserves enrollment order for same grades
```

### Example 3: Parallel Merge Sort

```java
// Sort left and right halves in parallel threads
ExecutorService executor = Executors.newFixedThreadPool(2);
Future<Void> leftFuture = executor.submit(() -> sort(left));
Future<Void> rightFuture = executor.submit(() -> sort(right));
// Wait and merge
```

## Summary

Merge Sort is a robust, efficient, and stable sorting algorithm that guarantees O(n log n) performance in all cases. While it requires additional memory, its predictability and stability make it indispensable for many applications.

**Key Takeaways:**
- Guaranteed O(n log n) time complexity for all inputs
- Stable and predictable algorithm
- Requires O(n) extra space
- Excellent for linked lists and external sorting
- Foundation for hybrid algorithms like TimSort
- Ideal when stability and predictable performance are priorities

Merge Sort's divide-and-conquer approach exemplifies elegant algorithm design and remains one of the most important sorting algorithms in computer science, widely used in production systems and as a building block for more sophisticated algorithms.
