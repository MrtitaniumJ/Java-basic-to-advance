# Linear Search (Sequential Search)

## Overview
Linear search is the simplest searching algorithm that sequentially checks each element in a data structure until the target element is found or the end of the structure is reached. It works on both sorted and unsorted arrays, making it one of the most straightforward search techniques in computer science.

## Algorithm Explanation

### How Linear Search Works
Linear search starts from the first element and moves through the array one by one, comparing each element with the target value. When a match is found, the algorithm returns the index of that element. If the entire array is traversed without finding the target, it returns -1 (or indicates element not found).

### Key Characteristics
- **Simplicity**: Easy to understand and implement
- **No Preprocessing**: Works on unsorted data without any preparation
- **Straightforward Logic**: Compare current element with target, move to next
- **Sequential Access**: Must check elements in order
- **Deterministic**: Always covers the entire range if needed

### Variations of Linear Search
1. **Basic Linear Search**: Searches from start to end
2. **Reverse Linear Search**: Searches from end to start (useful for finding last occurrence)
3. **Linear Search with Sentinel**: Adds a sentinel value to reduce comparisons
4. **Linear Search for Multiple Occurrences**: Finds all positions where target appears
5. **Linear Search in 2D Arrays**: Extends to matrix search

## Time and Space Complexity Analysis

| Aspect | Complexity |
|--------|-----------|
| Best Case | O(1) - Element found at first position |
| Average Case | O(n) - Element typically in middle |
| Worst Case | O(n) - Element at end or not present |
| Space Complexity | O(1) - Constant extra space needed |

Where n is the number of elements in the array.

## When to Use Linear Search

✅ **Use Linear Search When:**
- The array is small (< 1000 elements)
- The data is unsorted and cannot be sorted
- Searching is a rare operation in your program
- You need simplicity over performance
- Working with linked lists or other linear structures
- Finding the first occurrence is sufficient
- The data is already stored sequentially in memory

❌ **Avoid Linear Search When:**
- The array is large (> 10,000 elements)
- The data is sorted (use binary search instead)
- Multiple searches will be performed (consider indexing)
- Performance is critical
- Real-time systems where O(n) is unacceptable

## Complete Java Implementation

```java
/**
 * Linear Search Implementation - Various Approaches
 * Demonstrates basic and advanced linear search techniques
 */
public class LinearSearch {
    
    /**
     * Basic Linear Search
     * Searches for target element and returns its index
     * Time: O(n), Space: O(1)
     * 
     * @param arr The array to search in
     * @param target The element to find
     * @return Index of target if found, -1 otherwise
     */
    public static int basicLinearSearch(int[] arr, int target) {
        // Iterate through each element in the array
        for (int i = 0; i < arr.length; i++) {
            // Check if current element matches target
            if (arr[i] == target) {
                return i; // Found at index i
            }
        }
        return -1; // Element not found in array
    }
    
    /**
     * Reverse Linear Search
     * Searches from end to beginning, useful for finding last occurrence
     * Time: O(n), Space: O(1)
     * 
     * @param arr The array to search in
     * @param target The element to find
     * @return Index of target (from end) if found, -1 otherwise
     */
    public static int reverseLinearSearch(int[] arr, int target) {
        // Start from the last element and move backwards
        for (int i = arr.length - 1; i >= 0; i--) {
            // Check if current element matches target
            if (arr[i] == target) {
                return i;
            }
        }
        return -1; // Element not found
    }
    
    /**
     * Linear Search with Sentinel
     * Reduces number of comparisons by placing sentinel at end
     * Time: O(n), Space: O(1)
     * 
     * @param arr The array to search in
     * @param target The element to find
     * @return Index of target if found, -1 otherwise
     */
    public static int linearSearchWithSentinel(int[] arr, int target) {
        // Store the last element and replace with target (sentinel)
        int lastElement = arr[arr.length - 1];
        arr[arr.length - 1] = target;
        
        int index = 0;
        // Search without checking array bounds (sentinel guarantees match)
        while (arr[index] != target) {
            index++;
        }
        
        // Restore the last element
        arr[arr.length - 1] = lastElement;
        
        // Check if found in actual array or just at sentinel
        if (index < arr.length - 1 || lastElement == target) {
            return index;
        }
        return -1; // Element not found
    }
    
    /**
     * Find All Occurrences
     * Returns array of all indices where target appears
     * Time: O(n), Space: O(k) where k is number of occurrences
     * 
     * @param arr The array to search in
     * @param target The element to find
     * @return Array of indices where target is found
     */
    public static int[] findAllOccurrences(int[] arr, int target) {
        // First pass: count occurrences
        int count = 0;
        for (int element : arr) {
            if (element == target) {
                count++;
            }
        }
        
        // If not found, return empty array
        if (count == 0) {
            return new int[0];
        }
        
        // Second pass: store indices in result array
        int[] result = new int[count];
        int resultIndex = 0;
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                result[resultIndex++] = i;
            }
        }
        
        return result;
    }
    
    /**
     * Count Occurrences
     * Counts how many times target appears in array
     * Time: O(n), Space: O(1)
     * 
     * @param arr The array to search in
     * @param target The element to count
     * @return Number of times target appears
     */
    public static int countOccurrences(int[] arr, int target) {
        int count = 0;
        // Traverse entire array and count matches
        for (int element : arr) {
            if (element == target) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Linear Search in 2D Array
     * Searches for target in a 2D matrix
     * Time: O(m*n), Space: O(1)
     * 
     * @param matrix 2D array to search
     * @param target Element to find
     * @return Array with [row, col] if found, null otherwise
     */
    public static int[] linearSearchIn2D(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }
        
        // Iterate through each row
        for (int i = 0; i < matrix.length; i++) {
            // Iterate through each column in the row
            for (int j = 0; j < matrix[i].length; j++) {
                // Check if element matches target
                if (matrix[i][j] == target) {
                    return new int[]{i, j}; // Found at position [i, j]
                }
            }
        }
        
        return null; // Element not found in matrix
    }
    
    /**
     * Print array utility function
     * 
     * @param arr Array to print
     */
    public static void printArray(int[] arr) {
        System.out.print("Array: [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    /**
     * Print 2D array utility function
     * 
     * @param matrix 2D array to print
     */
    public static void print2DArray(int[][] matrix) {
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.print("[");
            for (int i = 0; i < row.length; i++) {
                System.out.print(row[i]);
                if (i < row.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    /**
     * Main method - Demonstrates all linear search variations
     */
    public static void main(String[] args) {
        System.out.println("========== LINEAR SEARCH DEMONSTRATIONS ==========\n");
        
        // Test array 1: Unsorted array
        int[] arr1 = {45, 23, 89, 12, 67, 34, 90, 56, 78, 23};
        System.out.println("Test Array 1: Unsorted Array");
        printArray(arr1);
        System.out.println();
        
        // Demonstrate basic linear search
        System.out.println("--- Basic Linear Search ---");
        int target = 34;
        int result = basicLinearSearch(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate searching for non-existent element
        target = 100;
        result = basicLinearSearch(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found (expected)");
        }
        System.out.println();
        
        // Demonstrate reverse linear search
        System.out.println("--- Reverse Linear Search ---");
        target = 23; // Appears twice: at index 1 and 9
        result = reverseLinearSearch(arr1, target);
        System.out.println("Searching for: " + target + " (from end)");
        if (result != -1) {
            System.out.println("✓ Found at index: " + result + " (last occurrence)");
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate find all occurrences
        System.out.println("--- Find All Occurrences ---");
        target = 23;
        int[] occurrences = findAllOccurrences(arr1, target);
        System.out.println("All occurrences of " + target + ":");
        if (occurrences.length > 0) {
            System.out.print("Found at indices: ");
            for (int i = 0; i < occurrences.length; i++) {
                System.out.print(occurrences[i]);
                if (i < occurrences.length - 1) System.out.print(", ");
            }
            System.out.println();
        } else {
            System.out.println("Element not found");
        }
        System.out.println();
        
        // Demonstrate count occurrences
        System.out.println("--- Count Occurrences ---");
        target = 23;
        int count = countOccurrences(arr1, target);
        System.out.println("Element " + target + " appears " + count + " time(s)");
        System.out.println();
        
        // Test array 2: Array with single occurrence
        int[] arr2 = {10, 20, 30, 40, 50, 60, 70, 80, 90};
        System.out.println("Test Array 2:");
        printArray(arr2);
        System.out.println();
        
        // Demonstrate sentinel search
        System.out.println("--- Linear Search with Sentinel ---");
        target = 60;
        result = linearSearchWithSentinel(arr2, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Test 2D array
        int[][] matrix = {
            {5, 10, 15, 20},
            {25, 30, 35, 40},
            {45, 50, 55, 60},
            {65, 70, 75, 80}
        };
        
        System.out.println("Test Array 3: 2D Matrix");
        print2DArray(matrix);
        System.out.println();
        
        // Demonstrate 2D linear search
        System.out.println("--- Linear Search in 2D Array ---");
        target = 50;
        int[] position = linearSearchIn2D(matrix, target);
        System.out.println("Searching for: " + target);
        if (position != null) {
            System.out.println("✓ Found at position: [" + position[0] + "][" + position[1] + "]");
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Performance comparison
        System.out.println("--- Performance Notes ---");
        System.out.println("Best Case: O(1) - Element at first position");
        System.out.println("Average Case: O(n) - Element near middle");
        System.out.println("Worst Case: O(n) - Element at end or not found");
        System.out.println("Space Complexity: O(1) - Constant extra space");
    }
}
```

## Advantages and Disadvantages

### Advantages ✅
- Simple and easy to understand
- Works on unsorted data
- No additional space required
- Can find multiple occurrences easily
- Works on linked lists and streams

### Disadvantages ❌
- Inefficient for large datasets
- O(n) time complexity
- Not scalable for big data applications
- Other algorithms (binary search) perform better on sorted data

## Practical Applications
- Searching in linked lists
- Finding elements in small arrays
- Implementing `contains()` method for collections
- Searching when data is unsorted
- Real-time systems where preprocessing is not possible
- Database sequential file searches

## Key Takeaways
- Linear search is the foundation of searching algorithms
- Use it when simplicity is more important than performance
- Perfect for small datasets and unsorted data
- Its O(1) best-case and O(n) worst-case make it predictable
- Variations provide flexibility for specific use cases
