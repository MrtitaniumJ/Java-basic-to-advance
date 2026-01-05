# Quick Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [Partitioning Strategies](#partitioning-strategies)
4. [How Quick Sort Works](#how-quick-sort-works)
5. [Implementation](#implementation)
6. [Complexity Analysis](#complexity-analysis)
7. [Advantages and Disadvantages](#advantages-and-disadvantages)
8. [When to Use](#when-to-use)
9. [Practical Examples](#practical-examples)

## Introduction

Quick Sort is one of the most efficient and widely used sorting algorithms, developed by Tony Hoare in 1959. It's a **Divide and Conquer** algorithm that, unlike Merge Sort, does most of its work during the partitioning phase rather than the merging phase. This makes it exceptionally fast in practice, often outperforming other O(n log n) algorithms.

Quick Sort is the default sorting algorithm in many programming languages and libraries due to its excellent average-case performance, in-place nature, and cache-friendly behavior. However, its worst-case O(n²) performance requires careful implementation with pivot selection strategies.

## Algorithm Explanation

### Core Concept

Quick Sort works by selecting a **pivot** element and partitioning the array around it such that:
1. **Left partition:** All elements less than or equal to pivot
2. **Right partition:** All elements greater than pivot
3. **Recursively sort** both partitions

The key insight is that after partitioning, the pivot is in its final sorted position, and we never need to compare elements across partitions.

### Step-by-Step Process

1. **Base Case:** If array has 0 or 1 element, it's already sorted
2. **Choose Pivot:** Select an element as the pivot
3. **Partition:** Rearrange array so elements < pivot are on left, > pivot on right
4. **Recursive Sort:** Recursively apply Quick Sort to left and right partitions
5. **Combine:** No explicit merging needed - array is sorted in-place

### Key Characteristics

- **In-place sorting:** O(log n) space for recursion
- **Not stable:** May change relative order of equal elements
- **Cache-friendly:** Good locality of reference
- **Practical efficiency:** Often fastest in practice despite O(n²) worst case

## Partitioning Strategies

### 1. Lomuto Partition Scheme

**Characteristics:**
- Simple to understand
- Pivot chosen as last element
- Less efficient than Hoare's scheme

**Process:**
```
Pivot at end, scan from left
Elements ≤ pivot moved to left side
```

### 2. Hoare Partition Scheme

**Characteristics:**
- More efficient (3x fewer swaps on average)
- Two pointers moving towards each other
- Original Quick Sort method

**Process:**
```
Pointers at both ends
Move towards each other, swap when needed
```

### 3. Three-Way Partitioning (Dutch National Flag)

**Characteristics:**
- Handles duplicates efficiently
- Creates three partitions: <, =, >
- Excellent for arrays with many duplicates

**Process:**
```
Three regions: less than, equal to, greater than pivot
```

### Pivot Selection Strategies

| Strategy | Description | Use Case |
|----------|-------------|----------|
| **First Element** | Always choose first | Simple, but worst for sorted data |
| **Last Element** | Always choose last | Simple, common in Lomuto |
| **Middle Element** | Choose middle index | Better than first/last |
| **Random** | Choose randomly | Good average case |
| **Median-of-Three** | Median of first, middle, last | Best practical choice |

## How Quick Sort Works

### Visual Example with Lomuto Partition

Sorting `[10, 7, 8, 9, 1, 5]` with last element as pivot:

**Initial Array:**
```
[10, 7, 8, 9, 1, 5]
                 ↑ pivot = 5
```

**Partition Process:**
```
i = -1 (boundary between ≤ and > pivot)
j scans array

j=0: 10 > 5, no swap
j=1: 7 > 5, no swap
j=2: 8 > 5, no swap
j=3: 9 > 5, no swap
j=4: 1 < 5, i++, swap arr[0] with arr[4]
     [1, 7, 8, 9, 10, 5]
      ↑              
Place pivot: swap arr[i+1] with arr[5]
     [1, 5, 8, 9, 10, 7]
         ↑ pivot in correct position
```

**Recursive Steps:**
```
Sort [1] - base case, done
Sort [8, 9, 10, 7]:
  Partition with pivot=7 → [7, 9, 10, 8]
  Sort [9, 10, 8]:
    Partition with pivot=8 → [8, 10, 9]
    Sort [10, 9]:
      Partition with pivot=9 → [9, 10]
      Done

Final: [1, 5, 7, 8, 9, 10]
```

### Recursion Tree

```
                    [10, 7, 8, 9, 1, 5]
                           |
                    Partition (pivot=5)
                           |
              [1] | 5 | [8, 9, 10, 7]
               ✓    ✓         |
                        Partition (pivot=7)
                              |
                        7 | [8, 10, 9]
                        ✓        |
                           Partition (pivot=9)
                                 |
                           [8] | 9 | [10]
                            ✓    ✓    ✓
```

## Implementation

```java
import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    
    private static Random random = new Random();
    
    /**
     * Quick sort using Lomuto partition scheme
     * Time: O(n log n) average, O(n²) worst
     * Space: O(log n) recursion stack
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = lomutoPartition(arr, low, high);
            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }
    
    /**
     * Lomuto partition scheme
     * Pivot is chosen as last element
     */
    private static int lomutoPartition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1; // Index of smaller element
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    /**
     * Quick sort using Hoare partition scheme
     * More efficient than Lomuto (fewer swaps)
     */
    public static void quickSortHoare(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortHoareHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortHoareHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = hoarePartition(arr, low, high);
            quickSortHoareHelper(arr, low, pivotIndex);
            quickSortHoareHelper(arr, pivotIndex + 1, high);
        }
    }
    
    /**
     * Hoare partition scheme
     * Two pointers move towards each other
     */
    private static int hoarePartition(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low - 1;
        int j = high + 1;
        
        while (true) {
            // Move left pointer right
            do {
                i++;
            } while (arr[i] < pivot);
            
            // Move right pointer left
            do {
                j--;
            } while (arr[j] > pivot);
            
            // Pointers crossed, return partition point
            if (i >= j) {
                return j;
            }
            
            // Swap elements at pointers
            swap(arr, i, j);
        }
    }
    
    /**
     * Quick sort with random pivot
     * Avoids worst-case on sorted arrays
     */
    public static void quickSortRandom(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortRandomHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortRandomHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomPartition(arr, low, high);
            quickSortRandomHelper(arr, low, pivotIndex - 1);
            quickSortRandomHelper(arr, pivotIndex + 1, high);
        }
    }
    
    /**
     * Partition with random pivot selection
     */
    private static int randomPartition(int[] arr, int low, int high) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        return lomutoPartition(arr, low, high);
    }
    
    /**
     * Quick sort with median-of-three pivot
     * Best practical pivot selection strategy
     */
    public static void quickSortMedianOfThree(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortMedianHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortMedianHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = medianOfThreePartition(arr, low, high);
            quickSortMedianHelper(arr, low, pivotIndex - 1);
            quickSortMedianHelper(arr, pivotIndex + 1, high);
        }
    }
    
    /**
     * Median-of-three pivot selection
     * Choose median of first, middle, and last elements
     */
    private static int medianOfThreePartition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        
        // Sort low, mid, high
        if (arr[low] > arr[mid]) {
            swap(arr, low, mid);
        }
        if (arr[low] > arr[high]) {
            swap(arr, low, high);
        }
        if (arr[mid] > arr[high]) {
            swap(arr, mid, high);
        }
        
        // Place median at high position
        swap(arr, mid, high);
        return lomutoPartition(arr, low, high);
    }
    
    /**
     * Three-way quick sort (Dutch National Flag)
     * Efficient for arrays with many duplicate elements
     */
    public static void quickSort3Way(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort3WayHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSort3WayHelper(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        
        int pivot = arr[low];
        int lt = low;      // Elements < pivot
        int gt = high;     // Elements > pivot
        int i = low + 1;   // Current element
        
        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, lt++, i++);
            } else if (arr[i] > pivot) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }
        
        // Now arr[low..lt-1] < pivot = arr[lt..gt] < arr[gt+1..high]
        quickSort3WayHelper(arr, low, lt - 1);
        quickSort3WayHelper(arr, gt + 1, high);
    }
    
    /**
     * Iterative quick sort using stack
     * Avoids recursion stack overflow
     */
    public static void quickSortIterative(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int[] stack = new int[arr.length];
        int top = -1;
        
        // Push initial values
        stack[++top] = 0;
        stack[++top] = arr.length - 1;
        
        while (top >= 0) {
            int high = stack[top--];
            int low = stack[top--];
            
            int p = lomutoPartition(arr, low, high);
            
            // Push left subarray
            if (p - 1 > low) {
                stack[++top] = low;
                stack[++top] = p - 1;
            }
            
            // Push right subarray
            if (p + 1 < high) {
                stack[++top] = p + 1;
                stack[++top] = high;
            }
        }
    }
    
    /**
     * Tail-recursive optimized quick sort
     * Reduces space complexity to O(log n)
     */
    public static void quickSortTailRecursive(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortTailHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortTailHelper(int[] arr, int low, int high) {
        while (low < high) {
            int p = lomutoPartition(arr, low, high);
            
            // Recur for smaller partition
            if (p - low < high - p) {
                quickSortTailHelper(arr, low, p - 1);
                low = p + 1;
            } else {
                quickSortTailHelper(arr, p + 1, high);
                high = p - 1;
            }
        }
    }
    
    /**
     * Hybrid quick sort with insertion sort for small subarrays
     */
    private static final int INSERTION_THRESHOLD = 10;
    
    public static void quickSortHybrid(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortHybridHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortHybridHelper(int[] arr, int low, int high) {
        if (high - low + 1 <= INSERTION_THRESHOLD) {
            insertionSort(arr, low, high);
            return;
        }
        
        if (low < high) {
            int p = medianOfThreePartition(arr, low, high);
            quickSortHybridHelper(arr, low, p - 1);
            quickSortHybridHelper(arr, p + 1, high);
        }
    }
    
    /**
     * Insertion sort for small subarrays
     */
    private static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Generic quick sort for any Comparable type
     */
    public static <T extends Comparable<T>> void genericQuickSort(T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        genericQuickSortHelper(arr, 0, arr.length - 1);
    }
    
    private static <T extends Comparable<T>> void genericQuickSortHelper(
            T[] arr, int low, int high) {
        if (low < high) {
            int p = genericPartition(arr, low, high);
            genericQuickSortHelper(arr, low, p - 1);
            genericQuickSortHelper(arr, p + 1, high);
        }
    }
    
    private static <T extends Comparable<T>> int genericPartition(
            T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        T temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
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
     * Demonstrate partition process
     */
    public static void demonstratePartition(int[] arr) {
        System.out.println("Initial array:");
        printArray(arr);
        System.out.println("Pivot: " + arr[arr.length - 1]);
        
        int pivotIndex = lomutoPartition(arr, 0, arr.length - 1);
        
        System.out.println("After partition:");
        printArray(arr);
        System.out.println("Pivot index: " + pivotIndex);
        System.out.print("Left partition: ");
        for (int i = 0; i < pivotIndex; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        System.out.println("Pivot: " + arr[pivotIndex]);
        System.out.print("Right partition: ");
        for (int i = pivotIndex + 1; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== QUICK SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic quick sort
        System.out.println("1. Basic Quick Sort (Lomuto Partition):");
        int[] arr1 = {10, 7, 8, 9, 1, 5};
        System.out.print("Original: ");
        printArray(arr1);
        quickSort(arr1);
        System.out.print("Sorted: ");
        printArray(arr1);
        
        // Example 2: Hoare partition
        System.out.println("\n2. Quick Sort with Hoare Partition:");
        int[] arr2 = {64, 25, 12, 22, 11};
        System.out.print("Original: ");
        printArray(arr2);
        quickSortHoare(arr2);
        System.out.print("Sorted: ");
        printArray(arr2);
        
        // Example 3: Random pivot
        System.out.println("\n3. Quick Sort with Random Pivot:");
        int[] arr3 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("Original (sorted - worst case for basic): ");
        printArray(arr3);
        quickSortRandom(arr3);
        System.out.print("Sorted: ");
        printArray(arr3);
        System.out.println("(Random pivot avoids O(n²) on sorted data)");
        
        // Example 4: Median-of-three
        System.out.println("\n4. Quick Sort with Median-of-Three:");
        int[] arr4 = {38, 27, 43, 3, 9, 82, 10};
        System.out.print("Original: ");
        printArray(arr4);
        quickSortMedianOfThree(arr4);
        System.out.print("Sorted: ");
        printArray(arr4);
        
        // Example 5: Three-way partition (duplicates)
        System.out.println("\n5. Three-Way Quick Sort (Many Duplicates):");
        int[] arr5 = {4, 9, 4, 4, 1, 9, 4, 4, 9, 4, 4, 1, 4};
        System.out.print("Original: ");
        printArray(arr5);
        quickSort3Way(arr5);
        System.out.print("Sorted: ");
        printArray(arr5);
        System.out.println("(Efficient for many duplicates)");
        
        // Example 6: Iterative quick sort
        System.out.println("\n6. Iterative Quick Sort:");
        int[] arr6 = {5, 2, 8, 4, 9, 1, 7};
        System.out.print("Original: ");
        printArray(arr6);
        quickSortIterative(arr6);
        System.out.print("Sorted: ");
        printArray(arr6);
        
        // Example 7: Tail-recursive optimization
        System.out.println("\n7. Tail-Recursive Quick Sort:");
        int[] arr7 = {12, 11, 13, 5, 6, 7};
        System.out.print("Original: ");
        printArray(arr7);
        quickSortTailRecursive(arr7);
        System.out.print("Sorted: ");
        printArray(arr7);
        
        // Example 8: Hybrid quick sort
        System.out.println("\n8. Hybrid Quick Sort (with Insertion Sort):");
        int[] arr8 = new int[100];
        for (int i = 0; i < 100; i++) {
            arr8[i] = random.nextInt(100);
        }
        System.out.println("Sorting 100 random elements...");
        long startTime = System.nanoTime();
        quickSortHybrid(arr8);
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime) / 1000.0 + " microseconds");
        System.out.print("First 10: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(arr8[i] + " ");
        }
        System.out.println("...");
        
        // Example 9: Performance comparison
        System.out.println("\n9. Performance Test (1000 elements):");
        int[] test = new int[1000];
        for (int i = 0; i < 1000; i++) {
            test[i] = random.nextInt(1000);
        }
        
        int[] test1 = Arrays.copyOf(test, test.length);
        startTime = System.nanoTime();
        quickSort(test1);
        endTime = System.nanoTime();
        System.out.println("Lomuto: " + (endTime - startTime) / 1000000.0 + " ms");
        
        int[] test2 = Arrays.copyOf(test, test.length);
        startTime = System.nanoTime();
        quickSortHoare(test2);
        endTime = System.nanoTime();
        System.out.println("Hoare: " + (endTime - startTime) / 1000000.0 + " ms");
        
        int[] test3 = Arrays.copyOf(test, test.length);
        startTime = System.nanoTime();
        quickSortHybrid(test3);
        endTime = System.nanoTime();
        System.out.println("Hybrid: " + (endTime - startTime) / 1000000.0 + " ms");
        
        // Example 10: Generic quick sort
        System.out.println("\n10. Generic Quick Sort with Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry"};
        System.out.print("Original: ");
        printGenericArray(strArr);
        genericQuickSort(strArr);
        System.out.print("Sorted: ");
        printGenericArray(strArr);
        
        // Example 11: Partition demonstration
        System.out.println("\n11. Partition Process Visualization:");
        int[] arr9 = {10, 80, 30, 90, 40, 50, 70};
        demonstratePartition(arr9);
        
        // Example 12: Already sorted (worst case)
        System.out.println("\n12. Worst Case - Already Sorted:");
        int[] arr10 = {1, 2, 3, 4, 5};
        System.out.print("Original: ");
        printArray(arr10);
        System.out.println("Using random pivot to avoid O(n²)...");
        quickSortRandom(arr10);
        System.out.print("Sorted: ");
        printArray(arr10);
        
        // Example 13: Reverse sorted
        System.out.println("\n13. Reverse Sorted Array:");
        int[] arr11 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.print("Original: ");
        printArray(arr11);
        quickSortMedianOfThree(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        // Example 14: Edge cases
        System.out.println("\n14. Edge Cases:");
        int[] arr12 = {42};
        System.out.print("Single element: ");
        printArray(arr12);
        quickSort(arr12);
        System.out.print("Sorted: ");
        printArray(arr12);
        
        int[] arr13 = {7, 3};
        System.out.print("Two elements: ");
        printArray(arr13);
        quickSort(arr13);
        System.out.print("Sorted: ");
        printArray(arr13);
        
        // Example 15: Large array benchmark
        System.out.println("\n15. Large Array Benchmark (10000 elements):");
        int[] large = new int[10000];
        for (int i = 0; i < 10000; i++) {
            large[i] = random.nextInt(10000);
        }
        startTime = System.nanoTime();
        quickSortHybrid(large);
        endTime = System.nanoTime();
        System.out.println("Sorted 10000 elements in " + 
                         (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("First 10: " + Arrays.toString(Arrays.copyOf(large, 10)));
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n log n) | Pivot always divides array in half |
| **Average Case** | O(n log n) | Expected with good pivot selection |
| **Worst Case** | O(n²) | Pivot is always smallest/largest (sorted array) |

**Detailed Analysis:**
- **Best/Average:** Tree height log n, work per level n → O(n log n)
- **Worst case:** Tree height n, degenerates to O(n²)
- **Recurrence:** T(n) = T(k) + T(n-k-1) + O(n)

### Space Complexity

- **Average:** O(log n) - Recursion stack depth
- **Worst case:** O(n) - When tree is completely unbalanced
- **Iterative version:** O(n) for explicit stack
- **In-place:** Yes, sorts within the array

### Stability

Quick Sort is **NOT STABLE** in standard implementations - may change relative order of equal elements due to long-distance swaps.

## Advantages and Disadvantages

### Advantages

1. **Fast in practice** - Often faster than other O(n log n) algorithms
2. **In-place sorting** - O(log n) space, no auxiliary array
3. **Cache-friendly** - Good locality of reference
4. **Practical efficiency** - Low constant factors
5. **Parallelizable** - Partitions can be sorted independently
6. **Good average case** - O(n log n) expected performance

### Disadvantages

1. **Worst case O(n²)** - On sorted/reverse sorted data without randomization
2. **Not stable** - Changes relative order of equal elements
3. **Recursion overhead** - Deep recursion on worst-case input
4. **Pivot selection** - Performance depends on good pivot choice
5. **Not adaptive** - Doesn't benefit from partially sorted data

## When to Use

### Ideal Use Cases

1. **General-purpose sorting** - Default choice for most applications
2. **Large datasets** - Excellent average-case performance
3. **Memory-constrained** - In-place with O(log n) space
4. **Good average case priority** - Faster than Merge Sort in practice
5. **Primitive types** - Java's Arrays.sort() uses Quick Sort for primitives
6. **When stability not required** - Use Merge Sort if stability needed

### When NOT to Use

1. **Worst-case guarantees needed** - Use Merge Sort or Heap Sort
2. **Stability required** - Use Merge Sort
3. **Sorted or nearly sorted data** - Without randomization, use Merge/Heap Sort
4. **When duplicates are common** - Use three-way partitioning

### Used in Production

- **Java:** `Arrays.sort()` for primitive types (Dual-Pivot Quick Sort)
- **C++ STL:** `std::sort()` (IntroSort: Quick Sort + Heap Sort)
- **Linux kernel:** For various sorting operations
- **Many databases:** For in-memory sorting

## Practical Examples

### Example 1: Selecting Kth Smallest Element

Quick Sort's partition is used in QuickSelect algorithm:

```java
// Find kth smallest in O(n) average time
public static int quickSelect(int[] arr, int k) {
    return quickSelectHelper(arr, 0, arr.length - 1, k - 1);
}

private static int quickSelectHelper(int[] arr, int low, int high, int k) {
    int p = lomutoPartition(arr, low, high);
    if (p == k) return arr[p];
    if (p > k) return quickSelectHelper(arr, low, p - 1, k);
    return quickSelectHelper(arr, p + 1, high, k);
}
```

### Example 2: Three-Way Partition for Color Sorting

Dutch National Flag problem:

```java
// Sort 0s, 1s, and 2s
int[] colors = {2, 0, 2, 1, 1, 0};
quickSort3Way(colors); // Efficient with three distinct values
```

### Example 3: Database Query Optimization

```java
// Sort query results efficiently in-place
int[] queryResults = getQueryResults();
quickSortHybrid(queryResults); // Fast for in-memory sorting
```

## Summary

Quick Sort is the most widely used sorting algorithm in practice due to its excellent average-case performance and in-place nature. While it has a worst-case time complexity of O(n²), careful pivot selection strategies make this rare in practice.

**Key Takeaways:**
- Average O(n log n), worst O(n²) time complexity
- In-place with O(log n) space
- Fast in practice, often outperforms other O(n log n) algorithms
- Not stable, requires good pivot selection
- Used as default in many standard libraries
- Basis for QuickSelect and IntroSort algorithms

Quick Sort's combination of theoretical efficiency and practical performance makes it indispensable in modern computing, serving as the backbone for sorting in countless applications and systems.
