# Selection Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [How Selection Sort Works](#how-selection-sort-works)
4. [Implementation](#implementation)
5. [Complexity Analysis](#complexity-analysis)
6. [Advantages and Disadvantages](#advantages-and-disadvantages)
7. [When to Use](#when-to-use)
8. [Practical Examples](#practical-examples)
9. [Comparison with Other Algorithms](#comparison-with-other-algorithms)

## Introduction

Selection Sort is a simple comparison-based sorting algorithm that divides the input array into two parts: a sorted portion at the left end and an unsorted portion at the right end. Initially, the sorted portion is empty, and the unsorted portion is the entire array. The algorithm repeatedly selects the smallest (or largest) element from the unsorted portion and moves it to the sorted portion.

The name "Selection Sort" comes from its core operation: repeatedly **selecting** the minimum element from the unsorted portion. Despite its simplicity, it performs better than Bubble Sort in terms of the number of swaps required.

## Algorithm Explanation

### Core Concept

Selection Sort works by maintaining two subarrays:
1. **Sorted subarray** - built from left to right
2. **Unsorted subarray** - reduces from left to right

The algorithm finds the minimum element in the unsorted subarray and swaps it with the leftmost unsorted element, moving the boundary between sorted and unsorted subarrays one position to the right.

### Step-by-Step Process

1. **Find** the minimum element in the unsorted portion
2. **Swap** it with the first element of the unsorted portion
3. **Move** the boundary between sorted and unsorted portions one position right
4. **Repeat** steps 1-3 until the entire array is sorted
5. **Terminate** when only one element remains in the unsorted portion

### Key Characteristics

- **In-place sorting:** Requires O(1) extra space
- **Not stable:** May change relative order of equal elements
- **Not adaptive:** Performance doesn't improve with partially sorted data
- **Performs well with limited memory:** Minimizes number of swaps

## How Selection Sort Works

### Visual Example

Consider sorting the array: `[64, 25, 12, 22, 11]`

**Initial Array:**
```
[64, 25, 12, 22, 11]
 ↑
 sorted/unsorted boundary
```

**Pass 1:** Find minimum (11), swap with position 0
```
[11, 25, 12, 22, 64]
     ↑
     sorted/unsorted boundary
```

**Pass 2:** Find minimum (12), swap with position 1
```
[11, 12, 25, 22, 64]
         ↑
         sorted/unsorted boundary
```

**Pass 3:** Find minimum (22), swap with position 2
```
[11, 12, 22, 25, 64]
             ↑
             sorted/unsorted boundary
```

**Pass 4:** Find minimum (25), already in correct position
```
[11, 12, 22, 25, 64]
                 ↑
                 sorted/unsorted boundary
```

**Final Result:** `[11, 12, 22, 25, 64]`

## Implementation

### Basic Selection Sort

```java
public class SelectionSort {
    
    /**
     * Basic selection sort implementation - ascending order
     * Time Complexity: O(n^2) in all cases
     * Space Complexity: O(1)
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        // Move boundary of unsorted subarray one by one
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap the found minimum element with the first element
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
    
    /**
     * Selection sort in descending order
     * Finds maximum element instead of minimum
     */
    public static void selectionSortDescending(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            // Find the maximum element in unsorted array
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] > arr[maxIndex]) {
                    maxIndex = j;
                }
            }
            
            // Swap the found maximum element with the first element
            if (maxIndex != i) {
                int temp = arr[i];
                arr[i] = arr[maxIndex];
                arr[maxIndex] = temp;
            }
        }
    }
    
    /**
     * Stable selection sort implementation
     * Maintains relative order of equal elements
     * Uses shifting instead of swapping
     */
    public static void stableSelectionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            // Find minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Move minimum element to correct position by shifting
            int key = arr[minIndex];
            while (minIndex > i) {
                arr[minIndex] = arr[minIndex - 1];
                minIndex--;
            }
            arr[i] = key;
        }
    }
    
    /**
     * Recursive selection sort implementation
     * Time Complexity: O(n^2)
     * Space Complexity: O(n) due to recursion stack
     */
    public static void recursiveSelectionSort(int[] arr, int start, int n) {
        // Base case: if we've reached the end
        if (start >= n - 1) {
            return;
        }
        
        // Find minimum element in arr[start..n-1]
        int minIndex = start;
        for (int i = start + 1; i < n; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        
        // Swap the found minimum with the element at start
        if (minIndex != start) {
            int temp = arr[start];
            arr[start] = arr[minIndex];
            arr[minIndex] = temp;
        }
        
        // Recursively sort the remaining array
        recursiveSelectionSort(arr, start + 1, n);
    }
    
    /**
     * Double-ended selection sort
     * Finds both minimum and maximum in each pass
     * Slightly more efficient - requires n/2 passes
     */
    public static void doubleSelectionSort(int[] arr) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            int minIndex = left;
            int maxIndex = right;
            
            // Find minimum and maximum in current range
            for (int i = left; i <= right; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                }
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            
            // Handle case where min and max are at the boundaries
            if (minIndex == right && maxIndex == left) {
                // Swap left and right
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            } else {
                // Swap minimum to left
                if (minIndex != left) {
                    int temp = arr[left];
                    arr[left] = arr[minIndex];
                    arr[minIndex] = temp;
                }
                
                // If maximum was at left position, update its index
                if (maxIndex == left) {
                    maxIndex = minIndex;
                }
                
                // Swap maximum to right
                if (maxIndex != right) {
                    int temp = arr[right];
                    arr[right] = arr[maxIndex];
                    arr[maxIndex] = temp;
                }
            }
            
            left++;
            right--;
        }
    }
    
    /**
     * Generic selection sort for any Comparable type
     */
    public static <T extends Comparable<T>> void genericSelectionSort(T[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                T temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
    
    /**
     * Selection sort with custom comparator
     */
    public static <T> void selectionSortWithComparator(T[] arr, java.util.Comparator<T> comparator) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                T temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
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
     * Method to demonstrate sorting with step-by-step output
     */
    public static void selectionSortWithSteps(int[] arr) {
        int n = arr.length;
        System.out.println("Initial array:");
        printArray(arr);
        
        for (int i = 0; i < n - 1; i++) {
            System.out.println("\nPass " + (i + 1) + ":");
            
            // Find minimum element
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            System.out.println("  Minimum element: " + arr[minIndex] + 
                             " at index " + minIndex);
            
            // Swap if necessary
            if (minIndex != i) {
                System.out.println("  Swapping " + arr[i] + 
                                 " with " + arr[minIndex]);
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            } else {
                System.out.println("  Element already in correct position");
            }
            
            System.out.print("  Array after pass: ");
            printArray(arr);
            System.out.print("  Sorted portion: [");
            for (int k = 0; k <= i; k++) {
                System.out.print(arr[k]);
                if (k < i) System.out.print(", ");
            }
            System.out.println("]");
        }
        
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    /**
     * Method to count number of swaps and comparisons
     */
    public static void selectionSortWithStats(int[] arr) {
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
            }
        }
        
        System.out.println("Total comparisons: " + comparisons);
        System.out.println("Total swaps: " + swaps);
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== SELECTION SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic selection sort
        System.out.println("1. Basic Selection Sort:");
        int[] arr1 = {64, 25, 12, 22, 11};
        System.out.print("Original array: ");
        printArray(arr1);
        selectionSort(arr1);
        System.out.print("Sorted array: ");
        printArray(arr1);
        
        // Example 2: Descending order
        System.out.println("\n2. Selection Sort - Descending Order:");
        int[] arr2 = {64, 25, 12, 22, 11};
        System.out.print("Original array: ");
        printArray(arr2);
        selectionSortDescending(arr2);
        System.out.print("Sorted array: ");
        printArray(arr2);
        
        // Example 3: Array with duplicates
        System.out.println("\n3. Array with Duplicates:");
        int[] arr3 = {5, 2, 8, 2, 9, 1, 5, 5};
        System.out.print("Original array: ");
        printArray(arr3);
        selectionSort(arr3);
        System.out.print("Sorted array: ");
        printArray(arr3);
        
        // Example 4: Already sorted array
        System.out.println("\n4. Already Sorted Array:");
        int[] arr4 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("Original array: ");
        printArray(arr4);
        selectionSort(arr4);
        System.out.print("Sorted array: ");
        printArray(arr4);
        System.out.println("(Still performs O(n²) comparisons)");
        
        // Example 5: Reverse sorted array
        System.out.println("\n5. Reverse Sorted Array:");
        int[] arr5 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.print("Original array: ");
        printArray(arr5);
        selectionSort(arr5);
        System.out.print("Sorted array: ");
        printArray(arr5);
        
        // Example 6: Recursive selection sort
        System.out.println("\n6. Recursive Selection Sort:");
        int[] arr6 = {38, 27, 43, 3, 9, 82, 10};
        System.out.print("Original array: ");
        printArray(arr6);
        recursiveSelectionSort(arr6, 0, arr6.length);
        System.out.print("Sorted array: ");
        printArray(arr6);
        
        // Example 7: Double-ended selection sort
        System.out.println("\n7. Double-Ended Selection Sort:");
        int[] arr7 = {5, 1, 4, 2, 8, 0, 9, 7, 3, 6};
        System.out.print("Original array: ");
        printArray(arr7);
        doubleSelectionSort(arr7);
        System.out.print("Sorted array: ");
        printArray(arr7);
        
        // Example 8: Stable selection sort
        System.out.println("\n8. Stable Selection Sort:");
        int[] arr8 = {4, 3, 3, 2, 1};
        System.out.print("Original array: ");
        printArray(arr8);
        stableSelectionSort(arr8);
        System.out.print("Sorted array: ");
        printArray(arr8);
        
        // Example 9: Generic selection sort with Strings
        System.out.println("\n9. Generic Selection Sort with Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry"};
        System.out.print("Original array: ");
        printGenericArray(strArr);
        genericSelectionSort(strArr);
        System.out.print("Sorted array: ");
        printGenericArray(strArr);
        
        // Example 10: Custom comparator (sort by string length)
        System.out.println("\n10. Selection Sort with Custom Comparator (by length):");
        String[] strArr2 = {"apple", "pi", "banana", "cat", "zebra"};
        System.out.print("Original array: ");
        printGenericArray(strArr2);
        selectionSortWithComparator(strArr2, (a, b) -> a.length() - b.length());
        System.out.print("Sorted array: ");
        printGenericArray(strArr2);
        
        // Example 11: Step-by-step demonstration
        System.out.println("\n11. Step-by-Step Selection Sort:");
        int[] arr9 = {5, 3, 8, 4, 2};
        selectionSortWithSteps(arr9);
        
        // Example 12: Statistics demonstration
        System.out.println("\n12. Selection Sort with Statistics:");
        int[] arr10 = {64, 25, 12, 22, 11, 90, 88, 45, 50, 32};
        System.out.print("Array: ");
        printArray(arr10);
        selectionSortWithStats(arr10);
        System.out.print("Sorted array: ");
        printArray(arr10);
        
        // Example 13: Single element
        System.out.println("\n13. Edge Case - Single Element:");
        int[] arr11 = {42};
        System.out.print("Original array: ");
        printArray(arr11);
        selectionSort(arr11);
        System.out.print("Sorted array: ");
        printArray(arr11);
        
        // Example 14: Two elements
        System.out.println("\n14. Edge Case - Two Elements:");
        int[] arr12 = {7, 3};
        System.out.print("Original array: ");
        printArray(arr12);
        selectionSort(arr12);
        System.out.print("Sorted array: ");
        printArray(arr12);
        
        // Example 15: Large array comparison
        System.out.println("\n15. Performance on Larger Array:");
        int[] arr13 = new int[20];
        System.out.print("Random array: ");
        for (int i = 0; i < 20; i++) {
            arr13[i] = (int)(Math.random() * 100);
        }
        printArray(arr13);
        long startTime = System.nanoTime();
        selectionSort(arr13);
        long endTime = System.nanoTime();
        System.out.print("Sorted array: ");
        printArray(arr13);
        System.out.println("Time taken: " + (endTime - startTime) + " nanoseconds");
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n²) | Even if array is sorted, all comparisons are made |
| **Average Case** | O(n²) | Random arrangement of elements |
| **Worst Case** | O(n²) | Reverse sorted or any arrangement |

**Detailed Analysis:**
- **Number of comparisons:** Always (n-1) + (n-2) + ... + 1 = n(n-1)/2 ≈ O(n²)
- **Number of swaps:** At most n-1 swaps (better than Bubble Sort)
- **NOT adaptive:** Performance doesn't improve with partially sorted data

### Space Complexity

- **Auxiliary Space:** O(1) - Only uses a constant amount of extra space
- **Recursive Version:** O(n) - Due to recursion call stack

### Comparison Count vs Swap Count

```
Total Comparisons: n(n-1)/2
Total Swaps: O(n) in best case, O(n) in worst case
```

Selection Sort minimizes the number of swaps compared to Bubble Sort, making it useful when swap operations are expensive.

## Advantages and Disadvantages

### Advantages

1. **Simple to understand and implement** - Very straightforward logic
2. **Minimal number of swaps** - At most n-1 swaps
3. **In-place sorting** - Requires O(1) extra space
4. **Good when swaps are expensive** - Minimizes write operations
5. **Works well with small datasets** - Simplicity is beneficial
6. **Predictable performance** - Always O(n²), no best/worst case variance

### Disadvantages

1. **Poor time complexity** - O(n²) for all cases
2. **Not adaptive** - Doesn't take advantage of existing order
3. **Not stable** - May change relative order of equal elements
4. **Many comparisons** - Always performs maximum comparisons
5. **Inefficient for large datasets** - Much slower than efficient algorithms

## When to Use

### Good Use Cases

1. **Small datasets** (n < 50) - Simple and adequate for small sizes
2. **When swap operations are expensive** - Minimizes writes to memory
3. **When memory is limited** - O(1) space complexity
4. **Educational purposes** - Teaching sorting fundamentals
5. **When simplicity is priority** - Easy to implement and debug
6. **Auxiliary memory writes are costly** - Flash memory, EEPROM

### When NOT to Use

1. **Large datasets** - Too slow for significant data
2. **When stability is required** - Use Merge Sort instead
3. **When adaptive behavior needed** - Use Insertion Sort
4. **Performance-critical applications** - Use Quick Sort or Merge Sort
5. **When data is nearly sorted** - Insertion Sort is much better

### Better Alternatives

- **For small arrays:** Insertion Sort (adaptive, stable)
- **For general use:** Quick Sort, Merge Sort
- **For stability:** Merge Sort, Insertion Sort
- **For memory-constrained:** Heap Sort

## Practical Examples

### Example 1: Finding Top K Elements

Selection Sort is useful when you only need first k sorted elements:

```java
// Sort only first 3 elements
for (int i = 0; i < 3 && i < arr.length - 1; i++) {
    int minIndex = i;
    for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[minIndex]) {
            minIndex = j;
        }
    }
    // Swap
    int temp = arr[i];
    arr[i] = arr[minIndex];
    arr[minIndex] = temp;
}
// Now arr[0], arr[1], arr[2] are the 3 smallest elements
```

### Example 2: Flash Memory Sorting

When writing to memory is expensive (flash drives, EEPROM), Selection Sort's minimal swaps are beneficial:

```java
// Minimize write operations to flash memory
int[] flashData = {45, 23, 67, 12, 89};
selectionSort(flashData); // Only 4 swaps maximum
```

### Example 3: Real-World Application

Sorting examination hall seats by roll numbers (small dataset):

```java
int[] rollNumbers = {105, 102, 108, 101, 107, 103, 106, 104, 110, 109};
selectionSort(rollNumbers);
// Efficient for this small dataset
```

## Comparison with Other Algorithms

### Selection Sort vs Bubble Sort

| Aspect | Selection Sort | Bubble Sort |
|--------|---------------|-------------|
| Swaps | O(n) | O(n²) |
| Comparisons | O(n²) | O(n²) |
| Adaptive | No | Yes (optimized) |
| Stable | No | Yes |
| Best for | Expensive swaps | Nearly sorted data |

### Selection Sort vs Insertion Sort

| Aspect | Selection Sort | Insertion Sort |
|--------|---------------|----------------|
| Comparisons | Always O(n²) | O(n) to O(n²) |
| Swaps | O(n) | O(n²) |
| Adaptive | No | Yes |
| Stable | No | Yes |
| Best Case | O(n²) | O(n) |

## Summary

Selection Sort is a straightforward sorting algorithm that excels in scenarios where swap operations are expensive. While it has a consistent O(n²) time complexity regardless of input, its minimal number of swaps makes it valuable in specific use cases.

**Key Takeaways:**
- Always performs O(n²) comparisons, not adaptive
- Minimizes number of swaps (at most n-1)
- Simple to implement but not suitable for large datasets
- Not stable in standard implementation
- Best used when write operations are costly or datasets are small
- Good for selecting top k elements without fully sorting

Despite its limitations compared to more sophisticated algorithms, Selection Sort remains an important algorithm for understanding sorting fundamentals and has niche applications where its characteristics align with specific requirements.
