# Sorting Algorithms - Comprehensive Guide

## Table of Contents
1. [Overview](#overview)
2. [Comparison Table](#comparison-table)
3. [Detailed Algorithms](#detailed-algorithms)
4. [Complexity Analysis](#complexity-analysis)
5. [Java Implementations](#java-implementations)
6. [When to Use Each](#when-to-use-each)

---

## Overview

Sorting is one of the most fundamental algorithms in computer science. It arranges data in a specified order (ascending or descending) and is essential for various operations like searching, data analysis, and optimization.

### Key Concepts
- **In-place Sorting**: Uses O(1) additional space
- **Stable Sorting**: Maintains relative order of equal elements
- **Comparison-based**: Uses comparisons between elements
- **Non-comparison**: Uses properties of elements (counting, radix sort)

---

## Comparison Table

| Algorithm | Best Case | Average Case | Worst Case | Space | Stable | In-place |
|-----------|-----------|--------------|-----------|-------|--------|----------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Yes | Yes |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | No | Yes |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Yes | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | No |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | No | Yes |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | No | Yes |
| Counting Sort | O(n + k) | O(n + k) | O(n + k) | O(k) | Yes | No |
| Radix Sort | O(nk) | O(nk) | O(nk) | O(n + k) | Yes | No |

*Where n = number of elements, k = range of input*

---

## Detailed Algorithms

### 1. Bubble Sort

**Overview**: Compares adjacent elements and swaps them if in wrong order. Lighter elements "bubble up" to the top.

**How it Works**:
1. Compare first two elements
2. Swap if first > second
3. Move to next pair and repeat
4. After each pass, one element is in final position
5. Continue until no swaps occur

```
Pass 1: [5, 2, 3] → [2, 5, 3] → [2, 3, 5]
Pass 2: [2, 3] → [2, 3] (sorted)
Result: [2, 3, 5]
```

**Properties**: Stable, In-place, Simple but inefficient

**Use Cases**: Educational purposes, nearly sorted arrays, small datasets

---

### 2. Selection Sort

**Overview**: Divides array into sorted and unsorted portions. Repeatedly finds minimum from unsorted part.

**How it Works**:
1. Find minimum element in unsorted portion
2. Swap with first element of unsorted portion
3. Move boundary between sorted/unsorted to right
4. Repeat until all sorted

```
Initial: [5, 2, 3, 1]
Pass 1: [1, 2, 3, 5] (1 selected as minimum)
Pass 2: [1, 2, 3, 5] (2 already in place)
Pass 3: [1, 2, 3, 5] (3 already in place)
```

**Properties**: Not stable, In-place, Consistent O(n²) performance

**Use Cases**: When memory is limited, minimizing writes to memory is important

---

### 3. Insertion Sort

**Overview**: Builds sorted array one item at a time. Each element is inserted into its correct position.

**How it Works**:
1. Start with second element
2. Compare with elements before it
3. Shift larger elements right
4. Insert current element in correct position
5. Move to next element and repeat

```
Initial: [5, 2, 3, 1]
Step 1:  [2, 5, 3, 1] (insert 2)
Step 2:  [2, 3, 5, 1] (insert 3)
Step 3:  [1, 2, 3, 5] (insert 1)
```

**Properties**: Stable, In-place, Efficient for small or nearly sorted arrays

**Use Cases**: Small datasets, nearly sorted arrays, online sorting (stream of data)

---

### 4. Merge Sort

**Overview**: Divide-and-conquer algorithm. Divides array in half, sorts recursively, merges results.

**How it Works**:
1. Divide array into two halves
2. Recursively sort left half
3. Recursively sort right half
4. Merge two sorted halves

```
[5, 2, 3, 1]
  ↓
[5, 2] [3, 1]
  ↓
[5] [2] [3] [1]
  ↓
[2, 5] [1, 3]
  ↓
[1, 2, 3, 5]
```

**Properties**: Stable, Not in-place (O(n) extra space), Consistent O(n log n)

**Use Cases**: Large datasets, guaranteed O(n log n), when stability matters

---

### 5. Quick Sort

**Overview**: Divide-and-conquer algorithm using pivot partitioning. Generally faster than Merge Sort in practice.

**How it Works**:
1. Select pivot element
2. Partition: elements < pivot left, > pivot right
3. Recursively sort left partition
4. Recursively sort right partition

```
[5, 2, 3, 1, 4] (pivot = 3)
  ↓
[2, 1] [3] [5, 4]
  ↓
[1, 2] [3] [4, 5]
  ↓
[1, 2, 3, 4, 5]
```

**Properties**: Not stable, In-place, O(n log n) average but O(n²) worst case

**Use Cases**: General-purpose sorting, most practical algorithm, large datasets

---

### 6. Heap Sort

**Overview**: Uses heap data structure. Builds max-heap, repeatedly extracts maximum.

**How it Works**:
1. Build max-heap from array
2. Swap root (max) with last element
3. Reduce heap size by 1
4. Heapify root
5. Repeat steps 2-4

```
[5, 2, 3, 1, 4]
  ↓ (build heap)
Max-Heap representation
  ↓ (extract max repeatedly)
[1, 2, 3, 4, 5]
```

**Properties**: Not stable, In-place, Guaranteed O(n log n)

**Use Cases**: When guaranteed O(n log n) is needed, limited memory, priority queues

---

### 7. Counting Sort

**Overview**: Non-comparison algorithm for integers. Counts occurrences of each value.

**How it Works**:
1. Find max value in array
2. Create count array of size max+1
3. Count occurrences of each element
4. Calculate cumulative counts
5. Place elements in output array based on counts

```
Input: [3, 1, 4, 1, 5]
Counts: [0, 2, 0, 1, 1, 1]
Output: [1, 1, 3, 4, 5]
```

**Properties**: Stable, Not in-place, Linear time for integer inputs

**Use Cases**: Sorting integers with limited range, radix sort basis

---

### 8. Radix Sort

**Overview**: Non-comparison algorithm. Sorts numbers digit-by-digit from least significant to most significant.

**How it Works**:
1. Find maximum number to determine digit count
2. For each digit position (from least to most significant):
   - Use stable sort (counting sort) on that digit
3. After processing all digits, array is sorted

```
[321, 45, 3, 152]
After sorting by units: [321, 152, 45, 3]
After sorting by tens: [3, 321, 45, 152]
After sorting by hundreds: [3, 45, 152, 321]
```

**Properties**: Stable, Not in-place (depends on stable sort used), Linear for fixed-digit numbers

**Use Cases**: Sorting large numbers, fixed-width integers, strings of same length

---

## Complexity Analysis

### Time Complexity Breakdown

**O(n) - Linear**: Counting Sort, Radix Sort (with fixed digits)
**O(n log n) - Log-linear**: Merge Sort, Heap Sort, Quick Sort (average)
**O(n²) - Quadratic**: Bubble Sort, Selection Sort, Insertion Sort, Quick Sort (worst)

### Space Complexity Considerations
- **O(1)**: Bubble, Selection, Insertion, Heap, Quick Sort (in-place)
- **O(n)**: Merge, Counting, Radix Sort (need auxiliary space)
- **O(log n)**: Quick Sort (recursion stack)

### Best/Average/Worst Case Analysis

**Bubble Sort**: Best O(n) with optimization for already sorted data
**Insertion Sort**: Best O(n) for nearly sorted data
**Quick Sort**: Worst O(n²) occurs with poor pivot selection
**Merge Sort**: Always O(n log n) regardless of input

---

## Java Implementations

```java
// 1. Bubble Sort
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // Optimization for sorted arrays
        }
    }
}

// 2. Selection Sort
public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}

// 3. Insertion Sort
public class InsertionSort {
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}

// 4. Merge Sort
public class MergeSort {
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }
    }
}

// 5. Quick Sort
public class QuickSort {
    public static void quickSort(int[] arr) {
        if (arr.length == 0) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

// 6. Heap Sort
public class HeapSort {
    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }
}

// 7. Counting Sort
public class CountingSort {
    public static void countingSort(int[] arr) {
        if (arr.length == 0) return;

        int max = arr[0];
        int min = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
            if (num < min) min = num;
        }

        int range = max - min + 1;
        int[] count = new int[range];

        for (int num : arr) {
            count[num - min]++;
        }

        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        int[] output = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
        }
    }
}

// 8. Radix Sort
public class RadixSort {
    public static void radixSort(int[] arr) {
        if (arr.length == 0) return;

        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}
```

---

## When to Use Each

### Use Bubble Sort When:
- Learning sorting algorithms
- Array is nearly sorted
- Simplicity is priority
- Very small datasets (< 10 elements)

### Use Selection Sort When:
- Memory writes are expensive
- Need guaranteed O(n²) with minimum swaps
- Small arrays and simplicity matters

### Use Insertion Sort When:
- Array is nearly sorted
- Sorting small datasets
- Receiving data stream (online sorting)
- Working with linked lists

### Use Merge Sort When:
- Need guaranteed O(n log n) performance
- Stability is required
- Sorting linked lists
- External sorting (sorting disk-resident data)

### Use Quick Sort When:
- Need fastest average-case performance
- General-purpose sorting library code
- In-place sorting of large datasets
- Memory efficiency is important

### Use Heap Sort When:
- Need guaranteed O(n log n) with limited memory
- Maximum element frequently accessed
- Building priority queues
- Worst-case performance guarantee needed

### Use Counting Sort When:
- Sorting integers with small range
- Non-negative integers only
- Linear time sorting required
- Working as basis for Radix Sort

### Use Radix Sort When:
- Sorting large integers or strings
- Fixed-length keys (same number of digits)
- Range of integers is large
- Counting Sort alone is impractical

---

## Summary

Sorting is fundamental to programming. Choose your algorithm based on:
- **Data size**: Small (Insertion) vs Large (Merge/Quick)
- **Data nature**: Random (Quick) vs Nearly sorted (Insertion)
- **Requirements**: Stable (Merge) vs In-place (Heap)
- **Performance**: Average (Quick) vs Guaranteed (Merge/Heap)
- **Input type**: Integers (Counting/Radix) vs General (Comparison-based)

Master these algorithms for interviews and optimal program performance!
