# Interpolation Search

## Overview
Interpolation Search is an improved variant of binary search for uniformly distributed sorted arrays. Instead of dividing the search space into two equal halves (like binary search), interpolation search uses the value of the target element to estimate its position based on the distribution of elements in the array. This makes it significantly faster than binary search when elements are uniformly distributed.

## How It Works

### Algorithm Concept
Interpolation search works by calculating where the target value is likely to be located using a linear interpolation formula:
- It assumes elements are uniformly distributed
- Uses the formula: `position = low + [(target - arr[low]) / (arr[high] - arr[low])] * (high - low)`
- This formula estimates how far into the remaining array the target might be

### Step-by-Step Process
1. Start with the entire sorted array
2. Calculate the estimated position using the interpolation formula
3. Compare the element at the estimated position with the target:
   - If equal, return the position
   - If target is smaller, search in the left subarray
   - If target is larger, search in the right subarray
4. Repeat until element is found or search space becomes empty

### Example
Consider array: `[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]` searching for `11`
- Position calculation: `0 + [(11-1)/(19-1)] * 10 = 5`
- Element at position 5 is `11` ✓ Found!

## Time and Space Complexity

| Scenario | Complexity | Notes |
|----------|-----------|-------|
| **Best Case** | O(1) | Element at estimated position |
| **Average Case** | O(log log n) | For uniformly distributed data |
| **Worst Case** | O(n) | When data is highly non-uniform or degenerates to linear search |
| **Space Complexity** | O(1) | Only uses constant extra space |

## When to Use Interpolation Search

### ✅ Ideal Scenarios
- **Uniformly distributed data**: Works best with evenly spaced elements
- **Large sorted arrays**: More efficient than binary search for uniform data
- **Numeric arrays**: Works particularly well with numbers in consistent ranges
- **Known distribution pattern**: When you know data follows a uniform distribution

### ❌ Avoid When
- **Non-uniform or clustered data**: Performance degrades significantly
- **Unknown data distribution**: Binary search is safer
- **Random or skewed distributions**: Interpolation formula becomes ineffective
- **Small arrays**: Overhead not justified; use binary search instead

## Comparison with Other Algorithms

| Algorithm | Best Case | Avg Case | Worst Case | Use When |
|-----------|-----------|----------|-----------|----------|
| **Linear Search** | O(1) | O(n) | O(n) | Unsorted data |
| **Binary Search** | O(1) | O(log n) | O(log n) | Sorted, any distribution |
| **Interpolation Search** | O(1) | O(log log n) | O(n) | Sorted, uniform distribution |
| **Exponential Search** | O(1) | O(log n) | O(log n) | Unbounded arrays |

## Java Implementation

```java
public class InterpolationSearch {

    /**
     * Performs interpolation search on a sorted array
     * @param arr - sorted array
     * @param target - element to search
     * @return index if found, -1 otherwise
     */
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high && target >= arr[low] && target <= arr[high]) {
            // Avoid division by zero
            if (arr[high] == arr[low]) {
                if (arr[low] == target) return low;
                return -1;
            }

            // Interpolation formula: estimate position
            int position = low + ((target - arr[low]) * (high - low)) / 
                          (arr[high] - arr[low]);

            // Ensure position is within bounds
            position = Math.max(low, Math.min(position, high));

            // Compare with target
            if (arr[position] == target) {
                return position;
            } else if (arr[position] < target) {
                low = position + 1;
            } else {
                high = position - 1;
            }
        }
        return -1;
    }

    /**
     * Performs interpolation search on double array (for non-integer data)
     */
    public static int interpolationSearch(double[] arr, double target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high && target >= arr[low] && target <= arr[high]) {
            if (arr[high] == arr[low]) {
                if (arr[low] == target) return low;
                return -1;
            }

            int position = low + (int) ((target - arr[low]) * (high - low)) / 
                          (int) (arr[high] - arr[low]);

            position = Math.max(low, Math.min(position, high));

            if (arr[position] == target) {
                return position;
            } else if (arr[position] < target) {
                low = position + 1;
            } else {
                high = position - 1;
            }
        }
        return -1;
    }

    /**
     * Recursive implementation of interpolation search
     */
    public static int interpolationSearchRecursive(int[] arr, int low, int high, int target) {
        if (low > high || target < arr[low] || target > arr[high]) {
            return -1;
        }

        if (arr[high] == arr[low]) {
            return arr[low] == target ? low : -1;
        }

        int position = low + ((target - arr[low]) * (high - low)) / 
                      (arr[high] - arr[low]);

        position = Math.max(low, Math.min(position, high));

        if (arr[position] == target) {
            return position;
        } else if (arr[position] < target) {
            return interpolationSearchRecursive(arr, position + 1, high, target);
        } else {
            return interpolationSearchRecursive(arr, low, position - 1, target);
        }
    }

    /**
     * Demonstrates performance comparison
     */
    public static void demonstratePerformance(int[] arr, int target) {
        long startTime, endTime;

        // Interpolation Search
        startTime = System.nanoTime();
        int result1 = interpolationSearch(arr, target);
        endTime = System.nanoTime();
        System.out.println("Interpolation Search Result: " + result1 + 
                          " (Time: " + (endTime - startTime) + " ns)");

        // Binary Search for comparison
        startTime = System.nanoTime();
        int result2 = binarySearch(arr, target);
        endTime = System.nanoTime();
        System.out.println("Binary Search Result: " + result2 + 
                          " (Time: " + (endTime - startTime) + " ns)");
    }

    /**
     * Binary search for comparison
     */
    public static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Interpolation Search Demo ===\n");

        // Test Case 1: Uniformly distributed integers
        int[] uniformArray = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        System.out.println("Test Case 1: Uniformly Distributed Array");
        System.out.println("Array: " + java.util.Arrays.toString(uniformArray));
        
        int[] testTargets = {1, 7, 19, 15, 20, -5};
        for (int target : testTargets) {
            int result = interpolationSearch(uniformArray, target);
            System.out.println("Search for " + target + ": " + (result >= 0 ? 
                              "Found at index " + result : "Not found"));
        }

        // Test Case 2: Large uniformly distributed array
        System.out.println("\nTest Case 2: Large Array Performance");
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Uniformly distributed
        }
        demonstratePerformance(largeArray, 12500);

        // Test Case 3: Non-uniform distribution
        System.out.println("\nTest Case 3: Non-Uniform Array");
        int[] nonUniformArray = {1, 2, 3, 4, 5, 100, 101, 102, 1000, 10000};
        System.out.println("Array: " + java.util.Arrays.toString(nonUniformArray));
        int result = interpolationSearch(nonUniformArray, 100);
        System.out.println("Search for 100: " + (result >= 0 ? 
                          "Found at index " + result : "Not found"));

        // Test Case 4: Recursive version
        System.out.println("\nTest Case 4: Recursive Implementation");
        result = interpolationSearchRecursive(uniformArray, 0, uniformArray.length - 1, 11);
        System.out.println("Recursive search for 11: " + (result >= 0 ? 
                          "Found at index " + result : "Not found"));

        // Test Case 5: Edge cases
        System.out.println("\nTest Case 5: Edge Cases");
        int[] edgeArray = {5};
        System.out.println("Single element array [5]:");
        System.out.println("Search for 5: " + (interpolationSearch(edgeArray, 5) >= 0 ? 
                          "Found" : "Not found"));
        System.out.println("Search for 3: " + (interpolationSearch(edgeArray, 3) >= 0 ? 
                          "Found" : "Not found"));

        // Test Case 6: Duplicate elements
        System.out.println("\nTest Case 6: Array with Duplicates");
        int[] duplicateArray = {1, 1, 1, 5, 5, 5, 10, 10, 10};
        System.out.println("Array: " + java.util.Arrays.toString(duplicateArray));
        result = interpolationSearch(duplicateArray, 5);
        System.out.println("Search for 5: " + (result >= 0 ? 
                          "Found at index " + result : "Not found"));

        // Test Case 7: Double precision values
        System.out.println("\nTest Case 7: Double Array");
        double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7};
        System.out.println("Array: " + java.util.Arrays.toString(doubleArray));
        int dResult = interpolationSearch(doubleArray, 5.5);
        System.out.println("Search for 5.5: " + (dResult >= 0 ? 
                          "Found at index " + dResult : "Not found"));
    }
}
```

## Key Points to Remember
- ✅ Works best on uniformly distributed data
- ✅ Reduces search space more effectively than binary search for uniform data
- ⚠️ Can degrade to O(n) with non-uniform data
- ⚠️ Requires careful handling of division by zero
- ✅ Space-efficient with O(1) extra space
- ✅ Can be implemented iteratively or recursively

## Practice Exercises
1. Modify the implementation to count comparisons and analyze actual performance
2. Implement adaptive interpolation that switches to binary search if performance degrades
3. Test with different data distributions and measure actual vs theoretical performance
4. Create a version that handles string search on alphabetically sorted arrays
