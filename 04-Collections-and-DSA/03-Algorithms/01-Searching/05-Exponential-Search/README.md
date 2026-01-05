# Exponential Search

## Overview
Exponential Search is a searching algorithm designed specifically for unbounded or infinite sorted arrays. It works by first finding a range where the target element might exist (using exponential jumps), then performing binary search within that range. This algorithm is particularly useful when the size of the array is unknown or when you want to search in an infinite stream of sorted data.

## How It Works

### Algorithm Concept
Exponential search combines two strategies:
1. **Exponential jumping**: Start at index 1 and jump to 2, 4, 8, 16, 32... (powers of 2) until you find a range containing the target
2. **Binary search**: Once the range is identified, perform binary search within that range

The key idea is that it quickly eliminates a large portion of the search space without knowing the array size beforehand.

### Step-by-Step Process
1. Start at index 1 (or index 0)
2. Create a variable `i = 1` and jump while `arr[i] < target`
3. Double `i` each iteration: `i = i * 2`
4. Once `arr[i] >= target`, you've found a range `[i/2, i]` containing the target
5. Perform binary search in the range `[i/2, i]`
6. Return the index if found, or -1 if not found

### Example
Consider array: `[1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29]` searching for `15`
- Jump 1: Check index 1 → arr[1]=3 < 15, continue
- Jump 2: Check index 2 → arr[2]=5 < 15, continue
- Jump 3: Check index 4 → arr[4]=9 < 15, continue
- Jump 4: Check index 8 → arr[8]=17 >= 15, range found [arr[4], arr[8]]
- Binary search in [9, 17] finds 15 at index 7

## Time and Space Complexity

| Scenario | Complexity | Notes |
|----------|-----------|-------|
| **Best Case** | O(1) | Element at first jump position |
| **Average Case** | O(log n) | For uniformly distributed data |
| **Worst Case** | O(log n) | Even for worst case scenario |
| **Space Complexity** | O(log n) | For binary search call stack (recursive) |
| **Space Complexity (Iterative)** | O(1) | No extra space needed |

## When to Use Exponential Search

### ✅ Ideal Scenarios
- **Unbounded arrays**: Size is unknown or infinite
- **Sorted arrays with unknown size**: Perfect for streaming data
- **Smaller target values**: Element is likely near the beginning
- **Unknown array length**: Don't need to know array size beforehand
- **Memory-constrained systems**: O(1) space with iterative implementation

### ❌ Avoid When
- **Small arrays**: Binary search is more efficient
- **Random access expensive**: Multiple array accesses cause overhead
- **Array size known**: Binary search directly is better
- **Small datasets**: Overhead not justified

## Comparison with Other Algorithms

| Algorithm | Best Case | Avg Case | Worst Case | Use When |
|-----------|-----------|----------|-----------|----------|
| **Linear Search** | O(1) | O(n) | O(n) | Unsorted data |
| **Binary Search** | O(1) | O(log n) | O(log n) | Bounded sorted array |
| **Exponential Search** | O(1) | O(log n) | O(log n) | Unbounded sorted array |
| **Interpolation Search** | O(1) | O(log log n) | O(n) | Uniformly distributed |

## Java Implementation

```java
public class ExponentialSearch {

    /**
     * Performs exponential search on a sorted array
     * @param arr - sorted array
     * @param target - element to search
     * @return index if found, -1 otherwise
     */
    public static int exponentialSearch(int[] arr, int target) {
        int n = arr.length;

        // Element at index 0 is the first element
        if (arr[0] == target) {
            return 0;
        }

        // Find the range where target might exist
        int i = 1;
        while (i < n && arr[i] <= target) {
            i *= 2;
        }

        // Perform binary search in the range [i/2, min(i, n-1)]
        return binarySearch(arr, target, i / 2, Math.min(i, n - 1));
    }

    /**
     * Binary search helper function
     */
    private static int binarySearch(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Iterative exponential search with detailed steps
     */
    public static int exponentialSearchWithSteps(int[] arr, int target) {
        int n = arr.length;
        System.out.println("\n--- Exponential Search Process ---");
        System.out.println("Searching for: " + target);

        // Handle first element
        if (arr[0] == target) {
            System.out.println("Found at index 0");
            return 0;
        }

        // Exponential jump phase
        int i = 1;
        int jumpCount = 0;
        System.out.println("Exponential Jump Phase:");
        while (i < n && arr[i] <= target) {
            System.out.println("  Jump " + (jumpCount + 1) + ": Check index " + i + 
                              " (value: " + arr[i] + ")");
            i *= 2;
            jumpCount++;
        }

        // Range found
        int low = i / 2;
        int high = Math.min(i, n - 1);
        System.out.println("Range found: [" + low + ", " + high + "]");
        System.out.println("Range values: [" + arr[low] + ", " + arr[high] + "]");

        // Binary search phase
        System.out.println("Binary Search Phase:");
        while (low <= high) {
            int mid = low + (high - low) / 2;
            System.out.println("  Check index " + mid + " (value: " + arr[mid] + ")");

            if (arr[mid] == target) {
                System.out.println("Found at index " + mid);
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Element not found");
        return -1;
    }

    /**
     * Recursive exponential search
     */
    public static int exponentialSearchRecursive(int[] arr, int target) {
        return exponentialSearchRecursiveHelper(arr, 0, target, 1);
    }

    private static int exponentialSearchRecursiveHelper(int[] arr, int low, 
                                                       int target, int powerOf2) {
        int n = arr.length;
        int high = Math.min(powerOf2 - 1, n - 1);

        // If target is out of range
        if (low > high || arr[low] > target || arr[high] < target) {
            return -1;
        }

        if (arr[low] == target) {
            return low;
        }

        if (arr[high] == target) {
            return high;
        }

        // Target might exist in the next range
        if (arr[high] < target) {
            return exponentialSearchRecursiveHelper(arr, powerOf2, target, powerOf2 * 2);
        }

        // Binary search in current range
        return binarySearch(arr, target, low, high);
    }

    /**
     * Exponential search for double array
     */
    public static int exponentialSearch(double[] arr, double target) {
        int n = arr.length;

        if (arr[0] == target) {
            return 0;
        }

        int i = 1;
        while (i < n && arr[i] <= target) {
            i *= 2;
        }

        return binarySearchDouble(arr, target, i / 2, Math.min(i, n - 1));
    }

    private static int binarySearchDouble(double[] arr, double target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Compare exponential search with other algorithms
     */
    public static void compareAlgorithms(int[] arr, int target) {
        System.out.println("\n--- Algorithm Comparison ---");
        System.out.println("Array size: " + arr.length + ", Searching for: " + target);

        // Exponential Search
        long startTime = System.nanoTime();
        int result1 = exponentialSearch(arr, target);
        long endTime = System.nanoTime();
        System.out.println("Exponential Search: Found at " + result1 + 
                          " (Time: " + (endTime - startTime) + " ns)");

        // Binary Search
        startTime = System.nanoTime();
        int result2 = binarySearch(arr, target, 0, arr.length - 1);
        endTime = System.nanoTime();
        System.out.println("Binary Search:      Found at " + result2 + 
                          " (Time: " + (endTime - startTime) + " ns)");

        // Linear Search
        startTime = System.nanoTime();
        int result3 = linearSearch(arr, target);
        endTime = System.nanoTime();
        System.out.println("Linear Search:      Found at " + result3 + 
                          " (Time: " + (endTime - startTime) + " ns)");
    }

    /**
     * Linear search for comparison
     */
    private static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Exponential Search Demo ===\n");

        // Test Case 1: Basic usage
        int[] basicArray = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        System.out.println("Test Case 1: Basic Array");
        System.out.println("Array: " + java.util.Arrays.toString(basicArray));
        
        int[] testTargets = {1, 7, 19, 15, 20, -5};
        for (int target : testTargets) {
            int result = exponentialSearch(basicArray, target);
            System.out.println("Search for " + target + ": " + (result >= 0 ? 
                              "Found at index " + result : "Not found"));
        }

        // Test Case 2: Large array
        System.out.println("\nTest Case 2: Large Array Performance");
        int[] largeArray = new int[100000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1;
        }
        compareAlgorithms(largeArray, 50000);

        // Test Case 3: Exponential search with detailed steps
        System.out.println("\nTest Case 3: Detailed Process Trace");
        exponentialSearchWithSteps(basicArray, 13);
        exponentialSearchWithSteps(basicArray, 20);

        // Test Case 4: Recursive implementation
        System.out.println("\nTest Case 4: Recursive Implementation");
        int result = exponentialSearchRecursive(basicArray, 11);
        System.out.println("Recursive search for 11: " + (result >= 0 ? 
                          "Found at index " + result : "Not found"));

        // Test Case 5: Edge cases
        System.out.println("\nTest Case 5: Edge Cases");
        int[] edgeArray = {5};
        System.out.println("Single element [5]:");
        System.out.println("Search for 5: " + (exponentialSearch(edgeArray, 5) >= 0 ? 
                          "Found" : "Not found"));
        System.out.println("Search for 3: " + (exponentialSearch(edgeArray, 3) >= 0 ? 
                          "Found" : "Not found"));

        // Test Case 6: Target at boundaries
        System.out.println("\nTest Case 6: Boundary Elements");
        int[] boundaryArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("Array: " + java.util.Arrays.toString(boundaryArray));
        System.out.println("First element (1): " + exponentialSearch(boundaryArray, 1));
        System.out.println("Last element (10): " + exponentialSearch(boundaryArray, 10));

        // Test Case 7: Double precision
        System.out.println("\nTest Case 7: Double Array");
        double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8};
        System.out.println("Array: " + java.util.Arrays.toString(doubleArray));
        result = exponentialSearch(doubleArray, 5.5);
        System.out.println("Search for 5.5: " + (result >= 0 ? 
                          "Found at index " + result : "Not found"));

        // Test Case 8: Powers of 2 boundaries
        System.out.println("\nTest Case 8: Testing at Power of 2 Boundaries");
        int[] powerArray = new int[1024];
        for (int i = 0; i < powerArray.length; i++) {
            powerArray[i] = i * 2;
        }
        System.out.println("Testing element near exponential jump boundaries:");
        System.out.println("Search for " + (1 * 2) + ": " + exponentialSearch(powerArray, 1 * 2));
        System.out.println("Search for " + (2 * 2) + ": " + exponentialSearch(powerArray, 2 * 2));
        System.out.println("Search for " + (512 * 2) + ": " + exponentialSearch(powerArray, 512 * 2));
    }
}
```

## Key Points to Remember
- ✅ Perfect for unbounded or unknown-size sorted arrays
- ✅ Guaranteed O(log n) time complexity
- ✅ Efficiently finds the relevant range before binary search
- ✅ Space-efficient iterative implementation available
- ⚠️ Less efficient for very small arrays
- ✅ Better than linear search for streaming data
- ✅ Useful when array size grows dynamically

## Practical Applications
1. **Sorted streams**: Searching in continuously growing datasets
2. **Unbounded arrays**: When you don't know the array size
3. **Networking**: Search in buffers of unknown size
4. **Database indexing**: Searching in B-trees and similar structures
5. **Append-only logs**: Searching in continuously appended data structures

## Practice Exercises
1. Implement exponential search that returns closest value if exact match not found
2. Create a version that handles both ascending and descending sorted arrays
3. Measure actual performance against binary search on arrays of different sizes
4. Implement version for searching in linked lists
5. Create adaptive version that switches algorithms based on array characteristics
