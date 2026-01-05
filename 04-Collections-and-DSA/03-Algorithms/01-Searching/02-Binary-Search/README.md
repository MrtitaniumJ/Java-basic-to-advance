# Binary Search

## Overview
Binary search is a highly efficient searching algorithm that works on sorted arrays. It uses a divide-and-conquer approach, repeatedly dividing the search space in half to locate the target element. Binary search is one of the most important algorithms in computer science and forms the foundation for many advanced searching techniques.

## Algorithm Explanation

### How Binary Search Works
Binary search requires a sorted array as input. It maintains two pointers: `low` (start) and `high` (end). In each iteration, it calculates the middle index and compares the middle element with the target value. Based on the comparison:
- If the middle element equals the target, return the index
- If the middle element is greater than target, search the left half
- If the middle element is less than target, search the right half

This process continues until the target is found or the search space is exhausted.

### Key Characteristics
- **Requires Sorted Data**: Must work with pre-sorted arrays
- **Divide-and-Conquer**: Reduces search space by half each iteration
- **Efficient**: Logarithmic time complexity O(log n)
- **Two Approaches**: Both iterative and recursive implementations
- **Precise**: Finds exact position or closest position if not found

### Variations of Binary Search
1. **Standard Binary Search**: Finds an occurrence of target
2. **First Occurrence**: Finds leftmost position of target
3. **Last Occurrence**: Finds rightmost position of target
4. **Closest Element**: Finds element closest to target
5. **Square Root Search**: Finds integer square root using binary search
6. **Rotated Array Search**: Searches in rotated sorted array

## Time and Space Complexity Analysis

| Aspect | Complexity |
|--------|-----------|
| Best Case | O(1) - Element at middle position |
| Average Case | O(log n) - Typical search |
| Worst Case | O(log n) - Element at edge or not found |
| Space Complexity (Iterative) | O(1) - Constant extra space |
| Space Complexity (Recursive) | O(log n) - Recursion call stack |

Where n is the number of elements in the array.

## When to Use Binary Search

✅ **Use Binary Search When:**
- Data is sorted or can be sorted
- Frequent searches are needed (log n is much better than n)
- Large datasets (> 1000 elements)
- Performance is critical
- Looking for any occurrence of element
- Need to find specific positions (first, last, closest)
- Array is stored contiguously in memory

❌ **Avoid Binary Search When:**
- Data is unsorted and cannot be sorted
- Insertion and deletion are frequent
- Space complexity is severely limited
- Data structure is a linked list (poor random access)
- Array is very small (< 10 elements)

## Complete Java Implementation

```java
/**
 * Binary Search Implementation - Complete Guide
 * Demonstrates iterative and recursive approaches with variations
 */
public class BinarySearch {
    
    /**
     * Standard Binary Search (Iterative)
     * Finds target element using iterative approach
     * Time: O(log n), Space: O(1)
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @return Index of target if found, -1 otherwise
     */
    public static int binarySearchIterative(int[] arr, int target) {
        int low = 0;           // Start pointer
        int high = arr.length - 1; // End pointer
        
        // Continue while search space exists
        while (low <= high) {
            // Calculate middle index (prevents overflow)
            int mid = low + (high - low) / 2;
            
            // Check middle element
            if (arr[mid] == target) {
                return mid; // Target found
            } 
            else if (arr[mid] < target) {
                // Target is in right half
                low = mid + 1;
            } 
            else {
                // Target is in left half
                high = mid - 1;
            }
        }
        
        return -1; // Target not found
    }
    
    /**
     * Standard Binary Search (Recursive)
     * Finds target element using recursive approach
     * Time: O(log n), Space: O(log n) due to recursion stack
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @return Index of target if found, -1 otherwise
     */
    public static int binarySearchRecursive(int[] arr, int target) {
        return binarySearchRecursiveHelper(arr, target, 0, arr.length - 1);
    }
    
    /**
     * Helper method for recursive binary search
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @param low Start index
     * @param high End index
     * @return Index of target if found, -1 otherwise
     */
    private static int binarySearchRecursiveHelper(int[] arr, int target, int low, int high) {
        // Base case: search space is exhausted
        if (low > high) {
            return -1;
        }
        
        // Calculate middle index
        int mid = low + (high - low) / 2;
        
        // Check middle element
        if (arr[mid] == target) {
            return mid; // Target found
        } 
        else if (arr[mid] < target) {
            // Recursively search right half
            return binarySearchRecursiveHelper(arr, target, mid + 1, high);
        } 
        else {
            // Recursively search left half
            return binarySearchRecursiveHelper(arr, target, low, mid - 1);
        }
    }
    
    /**
     * Find First Occurrence
     * Returns index of leftmost occurrence of target
     * Useful when duplicates exist
     * Time: O(log n), Space: O(1)
     * 
     * @param arr Sorted array with possible duplicates
     * @param target Element to find
     * @return Index of first occurrence, -1 if not found
     */
    public static int findFirstOccurrence(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int result = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == target) {
                result = mid; // Store this position
                // Continue searching in left half for earlier occurrence
                high = mid - 1;
            } 
            else if (arr[mid] < target) {
                low = mid + 1;
            } 
            else {
                high = mid - 1;
            }
        }
        
        return result; // Return index of first occurrence
    }
    
    /**
     * Find Last Occurrence
     * Returns index of rightmost occurrence of target
     * Useful when duplicates exist
     * Time: O(log n), Space: O(1)
     * 
     * @param arr Sorted array with possible duplicates
     * @param target Element to find
     * @return Index of last occurrence, -1 if not found
     */
    public static int findLastOccurrence(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int result = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == target) {
                result = mid; // Store this position
                // Continue searching in right half for later occurrence
                low = mid + 1;
            } 
            else if (arr[mid] < target) {
                low = mid + 1;
            } 
            else {
                high = mid - 1;
            }
        }
        
        return result; // Return index of last occurrence
    }
    
    /**
     * Count Total Occurrences
     * Counts how many times target appears in array
     * Uses first and last occurrence methods
     * Time: O(log n), Space: O(1)
     * 
     * @param arr Sorted array with possible duplicates
     * @param target Element to count
     * @return Number of times target appears
     */
    public static int countOccurrences(int[] arr, int target) {
        int first = findFirstOccurrence(arr, target);
        
        // If element not found
        if (first == -1) {
            return 0;
        }
        
        int last = findLastOccurrence(arr, target);
        return last - first + 1; // Calculate count from first to last
    }
    
    /**
     * Find Closest Element
     * Finds element closest to target value
     * Time: O(log n), Space: O(1)
     * 
     * @param arr Sorted array
     * @param target Value to find closest to
     * @return Index of closest element
     */
    public static int findClosestElement(int[] arr, int target) {
        if (arr.length == 0) return -1;
        
        int low = 0;
        int high = arr.length - 1;
        
        // Handle boundary cases
        if (target < arr[low]) return low;
        if (target > arr[high]) return high;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        
        // Check which is closer: arr[low] or arr[low-1]
        if (low > 0 && Math.abs(arr[low - 1] - target) < Math.abs(arr[low] - target)) {
            return low - 1;
        }
        
        return low;
    }
    
    /**
     * Find Square Root (Integer)
     * Finds integer square root of a number
     * Example: squareRoot(25) = 5, squareRoot(26) = 5
     * Time: O(log n), Space: O(1)
     * 
     * @param num Number to find square root of
     * @return Integer square root
     */
    public static int findSquareRoot(int num) {
        if (num == 0 || num == 1) {
            return num;
        }
        
        int low = 1;
        int high = num;
        int result = 0;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid is the perfect square root
            if (mid == num / mid) {
                return mid;
            } 
            // If mid*mid is less than num, search right
            else if (mid < num / mid) {
                result = mid; // Store as potential answer
                low = mid + 1;
            } 
            // If mid*mid is greater than num, search left
            else {
                high = mid - 1;
            }
        }
        
        return result; // Return floor of square root
    }
    
    /**
     * Search in Rotated Sorted Array
     * Searches in array that was sorted then rotated
     * Example: [4,5,6,7,0,1,2] rotated from [0,1,2,4,5,6,7]
     * Time: O(log n), Space: O(1)
     * 
     * @param arr Rotated sorted array
     * @param target Element to find
     * @return Index of target if found, -1 otherwise
     */
    public static int searchRotatedArray(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (target >= arr[low] && target < arr[mid]) {
                    // Target in left sorted half
                    high = mid - 1;
                } else {
                    // Target in right half
                    low = mid + 1;
                }
            } else {
                // Right half is sorted
                if (target > arr[mid] && target <= arr[high]) {
                    // Target in right sorted half
                    low = mid + 1;
                } else {
                    // Target in left half
                    high = mid - 1;
                }
            }
        }
        
        return -1; // Target not found
    }
    
    /**
     * Utility: Check if array is sorted
     * 
     * @param arr Array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Utility: Print array
     * 
     * @param arr Array to print
     */
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    /**
     * Main method - Demonstrates all binary search variations
     */
    public static void main(String[] args) {
        System.out.println("========== BINARY SEARCH DEMONSTRATIONS ==========\n");
        
        // Test array 1: Basic sorted array
        int[] arr1 = {2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78};
        System.out.println("Test Array 1: Basic Sorted Array");
        printArray(arr1);
        System.out.println("Sorted: " + isSorted(arr1));
        System.out.println();
        
        // Demonstrate iterative binary search
        System.out.println("--- Iterative Binary Search ---");
        int target = 38;
        int result = binarySearchIterative(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate recursive binary search
        System.out.println("--- Recursive Binary Search ---");
        target = 23;
        result = binarySearchRecursive(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate search with non-existent element
        System.out.println("--- Search for Non-existent Element ---");
        target = 50;
        result = binarySearchIterative(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found (expected)");
        }
        System.out.println();
        
        // Test array 2: Array with duplicates
        int[] arr2 = {1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 7, 8, 8, 9};
        System.out.println("Test Array 2: Sorted Array with Duplicates");
        printArray(arr2);
        System.out.println();
        
        // Demonstrate first occurrence
        System.out.println("--- Find First Occurrence ---");
        target = 5;
        result = findFirstOccurrence(arr2, target);
        System.out.println("First occurrence of " + target + ":");
        if (result != -1) {
            System.out.println("✓ Found at index: " + result + " (value: " + arr2[result] + ")");
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate last occurrence
        System.out.println("--- Find Last Occurrence ---");
        target = 5;
        result = findLastOccurrence(arr2, target);
        System.out.println("Last occurrence of " + target + ":");
        if (result != -1) {
            System.out.println("✓ Found at index: " + result + " (value: " + arr2[result] + ")");
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate count occurrences
        System.out.println("--- Count Occurrences ---");
        target = 5;
        int count = countOccurrences(arr2, target);
        System.out.println("Element " + target + " appears " + count + " time(s)");
        System.out.println();
        
        // Demonstrate find closest element
        System.out.println("--- Find Closest Element ---");
        int[] arr3 = {1, 3, 6, 9, 14, 20, 28, 35, 42};
        System.out.println("Array: ");
        printArray(arr3);
        int searchValue = 15;
        result = findClosestElement(arr3, searchValue);
        System.out.println("Closest element to " + searchValue + ":");
        System.out.println("✓ Found at index: " + result + " (value: " + arr3[result] + ")");
        System.out.println();
        
        // Demonstrate square root search
        System.out.println("--- Find Integer Square Root ---");
        int[] testNums = {9, 16, 25, 26, 50, 100, 101};
        System.out.println("Number -> Square Root");
        for (int num : testNums) {
            int sqrtResult = findSquareRoot(num);
            System.out.println(num + " -> " + sqrtResult);
        }
        System.out.println();
        
        // Demonstrate rotated array search
        System.out.println("--- Search in Rotated Sorted Array ---");
        int[] arr4 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Rotated Array: ");
        printArray(arr4);
        target = 0;
        result = searchRotatedArray(arr4, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Complexity analysis
        System.out.println("--- Complexity Analysis ---");
        System.out.println("Time Complexity: O(log n)");
        System.out.println("  - Best Case: O(1) - Element at middle");
        System.out.println("  - Average Case: O(log n)");
        System.out.println("  - Worst Case: O(log n)");
        System.out.println("Space Complexity (Iterative): O(1)");
        System.out.println("Space Complexity (Recursive): O(log n) - Recursion stack");
        System.out.println();
        
        // Performance comparison
        System.out.println("--- Performance vs Linear Search ---");
        int arraySize = 1000000;
        System.out.println("Array size: " + arraySize);
        System.out.println("Linear Search: ~" + (arraySize/2) + " comparisons (average)");
        System.out.println("Binary Search: ~" + (int)(Math.log(arraySize)/Math.log(2)) + " comparisons (average)");
        System.out.println("Speedup: " + String.format("%.1f", (double)arraySize / (Math.log(arraySize)/Math.log(2))) + "x faster");
    }
}
```

## Advantages and Disadvantages

### Advantages ✅
- Extremely fast - O(log n) time complexity
- Scales well to large datasets
- Predictable performance
- Works efficiently with caching and memory
- Foundation for many advanced algorithms

### Disadvantages ❌
- Requires data to be sorted
- Cannot handle unsorted data directly
- Sorting cost: O(n log n) if sorting required
- Not suitable for linked lists
- Wastes time if data is rarely searched

## Practical Applications
- Database indexing and searching
- Finding elements in sorted lists
- Implementing auto-complete search
- Finding square roots and nth roots
- Searching in file systems
- API rate limiting calculations
- Load balancing in distributed systems

## Key Takeaways
- Binary search is the gold standard for sorted data searching
- Choose between iterative (O(1) space) and recursive (O(log n) space) based on needs
- Variations like first/last occurrence handle duplicates efficiently
- Always verify data is sorted before using binary search
- O(log n) provides massive speedup for large datasets
