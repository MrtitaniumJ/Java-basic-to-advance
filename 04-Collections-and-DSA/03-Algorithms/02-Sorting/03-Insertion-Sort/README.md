# Insertion Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [How Insertion Sort Works](#how-insertion-sort-works)
4. [Implementation](#implementation)
5. [Adaptive Behavior](#adaptive-behavior)
6. [Complexity Analysis](#complexity-analysis)
7. [Advantages and Disadvantages](#advantages-and-disadvantages)
8. [When to Use](#when-to-use)
9. [Practical Examples](#practical-examples)

## Introduction

Insertion Sort is a simple and intuitive sorting algorithm that builds the final sorted array one element at a time. It works similarly to how most people sort playing cards in their hands - picking up one card at a time and inserting it into its correct position among the cards already held.

The algorithm is particularly efficient for small datasets and demonstrates excellent **adaptive behavior**, meaning it performs significantly better on partially sorted or nearly sorted data. This characteristic makes it a preferred choice in many practical scenarios and as part of hybrid sorting algorithms.

## Algorithm Explanation

### Core Concept

Insertion Sort maintains two regions in the array:
1. **Sorted region** - Initially contains just the first element
2. **Unsorted region** - Contains all remaining elements

The algorithm repeatedly takes the first element from the unsorted region and inserts it into its correct position in the sorted region by shifting elements as necessary.

### Step-by-Step Process

1. **Start** with the second element (index 1), considering the first element as sorted
2. **Pick** the current element (key) from the unsorted region
3. **Compare** the key with elements in the sorted region (right to left)
4. **Shift** elements in the sorted region that are greater than the key
5. **Insert** the key into its correct position
6. **Repeat** steps 2-5 until all elements are processed
7. **Result** is a fully sorted array

### Key Characteristics

- **In-place sorting:** O(1) auxiliary space
- **Stable:** Maintains relative order of equal elements
- **Adaptive:** Performance improves with partially sorted data
- **Online:** Can sort data as it's received

## How Insertion Sort Works

### Visual Example

Consider sorting the array: `[12, 11, 13, 5, 6]`

**Initial State:**
```
[12, 11, 13, 5, 6]
 ↑
 sorted region (size 1)
```

**Iteration 1:** Insert 11
```
Key = 11
[12, 11, 13, 5, 6]
Compare 11 with 12: 11 < 12, shift 12 right
[12, 12, 13, 5, 6]
Insert 11 at index 0
[11, 12, 13, 5, 6]
    ↑
    sorted region (size 2)
```

**Iteration 2:** Insert 13
```
Key = 13
[11, 12, 13, 5, 6]
Compare 13 with 12: 13 > 12, already in position
[11, 12, 13, 5, 6]
        ↑
        sorted region (size 3)
```

**Iteration 3:** Insert 5
```
Key = 5
[11, 12, 13, 5, 6]
Compare 5 with 13, 12, 11: shift all right
[11, 12, 13, 13, 6] → [11, 12, 12, 13, 6] → [11, 11, 12, 13, 6]
Insert 5 at index 0
[5, 11, 12, 13, 6]
            ↑
            sorted region (size 4)
```

**Iteration 4:** Insert 6
```
Key = 6
[5, 11, 12, 13, 6]
Compare 6 with 13, 12, 11: shift 13, 12, 11 right
Insert 6 after 5
[5, 6, 11, 12, 13]
              ↑
              sorted region (size 5)
```

**Final Result:** `[5, 6, 11, 12, 13]`

## Implementation

### Basic Insertion Sort

```java
public class InsertionSort {
    
    /**
     * Basic insertion sort implementation - ascending order
     * Time Complexity: O(n) best, O(n^2) average/worst
     * Space Complexity: O(1)
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Move elements of arr[0..i-1] that are greater than key
            // to one position ahead of their current position
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Insertion sort in descending order
     */
    public static void insertionSortDescending(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Move elements smaller than key
            while (j >= 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Optimized insertion sort with binary search
     * Reduces comparisons but not shifts
     * Time Complexity: O(n log n) comparisons, O(n^2) shifts
     */
    public static void binaryInsertionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            
            // Find position using binary search
            int insertPos = binarySearch(arr, 0, i - 1, key);
            
            // Shift elements to make space
            for (int j = i - 1; j >= insertPos; j--) {
                arr[j + 1] = arr[j];
            }
            
            arr[insertPos] = key;
        }
    }
    
    /**
     * Binary search helper for finding insertion position
     */
    private static int binarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (key < arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    
    /**
     * Recursive insertion sort implementation
     * Time Complexity: O(n^2)
     * Space Complexity: O(n) due to recursion
     */
    public static void recursiveInsertionSort(int[] arr, int n) {
        // Base case
        if (n <= 1) {
            return;
        }
        
        // Sort first n-1 elements
        recursiveInsertionSort(arr, n - 1);
        
        // Insert last element at its correct position in sorted array
        int last = arr[n - 1];
        int j = n - 2;
        
        while (j >= 0 && arr[j] > last) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = last;
    }
    
    /**
     * Insertion sort with sentinel
     * Places minimum element at start to eliminate bounds checking
     */
    public static void insertionSortWithSentinel(int[] arr) {
        int n = arr.length;
        if (n <= 1) return;
        
        // Find minimum and place it at the beginning
        int minIndex = 0;
        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        
        // Swap minimum to first position
        int temp = arr[0];
        arr[0] = arr[minIndex];
        arr[minIndex] = temp;
        
        // Now perform insertion sort without bounds checking
        for (int i = 2; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // No need to check j >= 0 because sentinel guarantees termination
            while (arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Generic insertion sort for any Comparable type
     */
    public static <T extends Comparable<T>> void genericInsertionSort(T[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Insertion sort with custom comparator
     */
    public static <T> void insertionSortWithComparator(
            T[] arr, java.util.Comparator<T> comparator) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && comparator.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Insertion sort for linked list
     * Demonstrates online sorting capability
     */
    static class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public static Node insertionSortLinkedList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        Node sorted = null;
        Node current = head;
        
        while (current != null) {
            Node next = current.next;
            sorted = sortedInsert(sorted, current);
            current = next;
        }
        
        return sorted;
    }
    
    private static Node sortedInsert(Node sorted, Node newNode) {
        // Insert at beginning or empty list
        if (sorted == null || sorted.data >= newNode.data) {
            newNode.next = sorted;
            return newNode;
        }
        
        // Find correct position
        Node current = sorted;
        while (current.next != null && current.next.data < newNode.data) {
            current = current.next;
        }
        
        newNode.next = current.next;
        current.next = newNode;
        
        return sorted;
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
     * Method to demonstrate sorting with step-by-step output
     */
    public static void insertionSortWithSteps(int[] arr) {
        int n = arr.length;
        System.out.println("Initial array:");
        printArray(arr);
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            System.out.println("\nIteration " + i + ": Inserting " + key);
            System.out.print("  Sorted region: [");
            for (int k = 0; k < i; k++) {
                System.out.print(arr[k]);
                if (k < i - 1) System.out.print(", ");
            }
            System.out.println("]");
            
            int shifts = 0;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                shifts++;
            }
            arr[j + 1] = key;
            
            System.out.println("  Shifts made: " + shifts);
            System.out.print("  Array after insertion: ");
            printArray(arr);
        }
        
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    /**
     * Method to demonstrate adaptive behavior
     */
    public static void demonstrateAdaptiveBehavior() {
        System.out.println("=== DEMONSTRATING ADAPTIVE BEHAVIOR ===\n");
        
        // Nearly sorted array
        int[] nearlySorted = {1, 2, 3, 4, 5, 7, 6, 8, 9, 10};
        System.out.println("Nearly sorted array:");
        printArray(nearlySorted);
        
        long startTime = System.nanoTime();
        insertionSort(nearlySorted);
        long endTime = System.nanoTime();
        
        System.out.print("Sorted: ");
        printArray(nearlySorted);
        System.out.println("Time: " + (endTime - startTime) + " ns\n");
        
        // Reverse sorted array
        int[] reverseSorted = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("Reverse sorted array (worst case):");
        printArray(reverseSorted);
        
        startTime = System.nanoTime();
        insertionSort(reverseSorted);
        endTime = System.nanoTime();
        
        System.out.print("Sorted: ");
        printArray(reverseSorted);
        System.out.println("Time: " + (endTime - startTime) + " ns");
        System.out.println("(Notice the significant time difference!)");
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== INSERTION SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic insertion sort
        System.out.println("1. Basic Insertion Sort:");
        int[] arr1 = {12, 11, 13, 5, 6};
        System.out.print("Original array: ");
        printArray(arr1);
        insertionSort(arr1);
        System.out.print("Sorted array: ");
        printArray(arr1);
        
        // Example 2: Descending order
        System.out.println("\n2. Insertion Sort - Descending Order:");
        int[] arr2 = {12, 11, 13, 5, 6};
        System.out.print("Original array: ");
        printArray(arr2);
        insertionSortDescending(arr2);
        System.out.print("Sorted array: ");
        printArray(arr2);
        
        // Example 3: Already sorted array (best case)
        System.out.println("\n3. Already Sorted Array (Best Case - O(n)):");
        int[] arr3 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("Original array: ");
        printArray(arr3);
        long startTime = System.nanoTime();
        insertionSort(arr3);
        long endTime = System.nanoTime();
        System.out.print("Sorted array: ");
        printArray(arr3);
        System.out.println("Time: " + (endTime - startTime) + " ns");
        
        // Example 4: Reverse sorted array (worst case)
        System.out.println("\n4. Reverse Sorted Array (Worst Case - O(n²)):");
        int[] arr4 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.print("Original array: ");
        printArray(arr4);
        startTime = System.nanoTime();
        insertionSort(arr4);
        endTime = System.nanoTime();
        System.out.print("Sorted array: ");
        printArray(arr4);
        System.out.println("Time: " + (endTime - startTime) + " ns");
        
        // Example 5: Array with duplicates
        System.out.println("\n5. Array with Duplicates:");
        int[] arr5 = {5, 2, 8, 2, 9, 1, 5, 5};
        System.out.print("Original array: ");
        printArray(arr5);
        insertionSort(arr5);
        System.out.print("Sorted array: ");
        printArray(arr5);
        
        // Example 6: Binary insertion sort
        System.out.println("\n6. Binary Insertion Sort:");
        int[] arr6 = {37, 23, 0, 17, 12, 72, 31};
        System.out.print("Original array: ");
        printArray(arr6);
        binaryInsertionSort(arr6);
        System.out.print("Sorted array: ");
        printArray(arr6);
        
        // Example 7: Recursive insertion sort
        System.out.println("\n7. Recursive Insertion Sort:");
        int[] arr7 = {38, 27, 43, 3, 9, 82, 10};
        System.out.print("Original array: ");
        printArray(arr7);
        recursiveInsertionSort(arr7, arr7.length);
        System.out.print("Sorted array: ");
        printArray(arr7);
        
        // Example 8: Insertion sort with sentinel
        System.out.println("\n8. Insertion Sort with Sentinel:");
        int[] arr8 = {5, 1, 4, 2, 8, 0, 9};
        System.out.print("Original array: ");
        printArray(arr8);
        insertionSortWithSentinel(arr8);
        System.out.print("Sorted array: ");
        printArray(arr8);
        
        // Example 9: Generic insertion sort with Strings
        System.out.println("\n9. Generic Insertion Sort with Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry"};
        System.out.print("Original array: ");
        printGenericArray(strArr);
        genericInsertionSort(strArr);
        System.out.print("Sorted array: ");
        printGenericArray(strArr);
        
        // Example 10: Custom comparator (by string length)
        System.out.println("\n10. Insertion Sort with Custom Comparator:");
        String[] strArr2 = {"apple", "pi", "banana", "cat", "elephant"};
        System.out.print("Original array: ");
        printGenericArray(strArr2);
        insertionSortWithComparator(strArr2, 
            (a, b) -> a.length() - b.length());
        System.out.print("Sorted by length: ");
        printGenericArray(strArr2);
        
        // Example 11: Step-by-step demonstration
        System.out.println("\n11. Step-by-Step Insertion Sort:");
        int[] arr9 = {5, 3, 8, 4, 2};
        insertionSortWithSteps(arr9);
        
        // Example 12: Linked list sorting
        System.out.println("\n12. Insertion Sort on Linked List:");
        Node head = new Node(5);
        head.next = new Node(3);
        head.next.next = new Node(8);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(2);
        System.out.print("Original list: ");
        printList(head);
        head = insertionSortLinkedList(head);
        System.out.print("Sorted list: ");
        printList(head);
        
        // Example 13: Adaptive behavior demonstration
        System.out.println("\n13. Adaptive Behavior Comparison:");
        demonstrateAdaptiveBehavior();
        
        // Example 14: Nearly sorted data
        System.out.println("\n14. Nearly Sorted Data (Where Insertion Sort Excels):");
        int[] arr10 = {1, 2, 3, 5, 4, 6, 7, 9, 8, 10};
        System.out.print("Original (nearly sorted): ");
        printArray(arr10);
        insertionSort(arr10);
        System.out.print("Sorted array: ");
        printArray(arr10);
        System.out.println("(Very efficient for nearly sorted data!)");
        
        // Example 15: Single and two element arrays
        System.out.println("\n15. Edge Cases:");
        int[] arr11 = {42};
        System.out.print("Single element: ");
        printArray(arr11);
        insertionSort(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        int[] arr12 = {7, 3};
        System.out.print("Two elements: ");
        printArray(arr12);
        insertionSort(arr12);
        System.out.print("Sorted: ");
        printArray(arr12);
    }
}
```

## Adaptive Behavior

### What Makes Insertion Sort Adaptive?

Insertion Sort's adaptive behavior is its most valuable characteristic. The algorithm naturally performs fewer operations when:

1. **Array is already sorted:** Only n-1 comparisons, no shifts - O(n)
2. **Array is nearly sorted:** Few shifts needed for misplaced elements
3. **Small inversions:** When elements are close to their final positions

### Performance Comparison

```
Array Type          | Comparisons | Shifts    | Time
--------------------|-------------|-----------|-------
Already sorted      | n-1         | 0         | O(n)
Nearly sorted       | ~n          | ~k        | O(n+k)
Random              | ~n²/2       | ~n²/2     | O(n²)
Reverse sorted      | ~n²/2       | ~n²/2     | O(n²)
```

Where k = number of inversions (pairs of elements out of order)

### Real-World Applications of Adaptive Behavior

1. **Maintaining sorted data:** Adding new elements to already sorted list
2. **Online sorting:** Processing streaming data
3. **Hybrid algorithms:** Used in TimSort (Python/Java) for small subarrays
4. **Database operations:** Sorting small result sets

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n) | Array is already sorted |
| **Average Case** | O(n²) | Random arrangement |
| **Worst Case** | O(n²) | Reverse sorted array |

**Detailed Analysis:**
- **Best case:** n-1 comparisons, 0 shifts
- **Average case:** ~n²/4 comparisons, ~n²/4 shifts
- **Worst case:** n(n-1)/2 comparisons, n(n-1)/2 shifts

### Space Complexity

- **Iterative:** O(1) - Constant extra space
- **Recursive:** O(n) - Call stack depth
- **Binary insertion sort:** O(1) - Still in-place despite binary search

### Stability

Insertion Sort is **STABLE** - maintains relative order of equal elements because we only shift when elements are strictly greater, not equal.

## Advantages and Disadvantages

### Advantages

1. **Adaptive behavior** - Excellent for nearly sorted data
2. **Simple implementation** - Easy to understand and code
3. **Stable sorting** - Preserves relative order of equal elements
4. **In-place sorting** - O(1) space complexity
5. **Online algorithm** - Can sort data as it arrives
6. **Efficient for small datasets** - Low overhead
7. **Few writes** - Good for arrays where writes are expensive
8. **Natural sorting** - Mimics human card-sorting behavior

### Disadvantages

1. **Poor performance on large datasets** - O(n²) time complexity
2. **Many shifts required** - In worst case, moves every element
3. **Not suitable for random data** - Average case still O(n²)
4. **Worse than Selection Sort for swaps** - More memory writes

## When to Use

### Ideal Use Cases

1. **Small datasets** (n < 50) - Very efficient
2. **Nearly sorted data** - Best adaptive algorithm
3. **Online sorting** - Process data as it arrives
4. **Maintaining sorted data** - Adding elements to sorted list
5. **As part of hybrid algorithms** - Used in TimSort, IntroSort
6. **Stable sorting needed** - When stability matters
7. **Simple implementation required** - Quick to code

### When NOT to Use

1. **Large random datasets** - Use Quick Sort or Merge Sort
2. **Reverse sorted data** - Worst case O(n²)
3. **Performance-critical with random data** - Better alternatives exist

### Used in Production

- **Java's Arrays.sort():** Uses Insertion Sort for small subarrays (< 47 elements)
- **Python's TimSort:** Uses Insertion Sort for runs < 64 elements
- **V8 JavaScript Engine:** Uses Insertion Sort for arrays < 10 elements

## Practical Examples

### Example 1: Adding Elements to Sorted List

```java
// Maintain sorted order while adding elements
int[] sortedList = {1, 3, 5, 7, 9};
int newElement = 6;
// Insert using insertion sort technique - O(n)
```

### Example 2: Database Query Results

```java
// Small result set from database query
int[] queryResults = {105, 102, 108, 101, 107};
insertionSort(queryResults); // Efficient for small data
```

### Example 3: Real-time Data Processing

```java
// Sort incoming sensor readings as they arrive
// Insertion sort works well for online/streaming data
```

## Summary

Insertion Sort is a practical and efficient algorithm for specific scenarios, particularly when dealing with small or nearly sorted datasets. Its adaptive behavior and simplicity make it a valuable tool in a programmer's arsenal.

**Key Takeaways:**
- Best sorting algorithm for small and nearly sorted datasets
- Adaptive: O(n) best case, O(n²) worst case
- Stable and in-place
- Used in production as part of hybrid algorithms
- Simple to implement and understand
- Excellent for online sorting and maintaining sorted data

While not suitable for large random datasets, Insertion Sort's characteristics make it indispensable for many practical applications, and it's frequently used as a component in sophisticated sorting algorithms like TimSort and IntroSort.
