# Jump Search

## Overview
Jump search is a searching algorithm for sorted arrays that works by jumping ahead by fixed intervals rather than checking every element sequentially (like linear search). It combines the simplicity of linear search with the efficiency of binary search. Jump search is particularly useful for systems with limited memory or when optimization is needed between linear and binary search performance.

## Algorithm Explanation

### How Jump Search Works
Jump search divides the array into blocks by jumping ahead by a fixed interval. The optimal interval (jump size) is √n where n is the array length. The algorithm works in two phases:

1. **Finding the Block**: Jump ahead until an element greater than the target is found
2. **Linear Search**: Perform linear search within the identified block

The key insight is that by using jump size of √n, we minimize the total number of comparisons (both jumps and linear searches within the block).

### Step-by-Step Process
1. Calculate optimal jump size: `jump = √n`
2. Start at index 0, jump by `jump` size until we find an element ≥ target
3. If element equals target, return its index
4. If element is greater than target, search linearly in the previous block
5. If target not found, return -1

### Key Characteristics
- **Works on Sorted Data**: Requires sorted array
- **Jump-Based Approach**: Skips elements in fixed intervals
- **Optimal Interval**: Jump size of √n gives best performance
- **Two-Phase Process**: Jump phase followed by linear search
- **Balanced Approach**: Better than linear, simpler than binary

### Variations of Jump Search
1. **Standard Jump Search**: Fixed jump size of √n
2. **Optimized Jump Search**: Dynamic jump calculation
3. **Jump Search for Duplicates**: Find first/last occurrence
4. **Backward Jump Search**: Search from right to left
5. **Jump Search with Key**: Enhanced data structures

## Time and Space Complexity Analysis

| Aspect | Complexity |
|--------|-----------|
| Best Case | O(1) - Element at first jump position |
| Average Case | O(√n) - Element found after k jumps + linear search |
| Worst Case | O(√n) - Element at end or not present |
| Space Complexity | O(1) - Constant extra space needed |

Where n is the number of elements in the array.

The time complexity is derived from:
- Number of jumps: √n
- Maximum linear search in block: √n
- Total: O(√n) = O(√n)

## When to Use Jump Search

✅ **Use Jump Search When:**
- Array is sorted and relatively stable
- Binary search is not available or complex to implement
- Working with limited memory systems
- Array size is moderate (100 to 10,000 elements)
- Need a balance between simplicity and efficiency
- Cache performance is important
- Linear access patterns are preferred

❌ **Avoid Jump Search When:**
- Array is unsorted
- Using very large arrays (binary search is better)
- Frequent random access is needed
- Need guaranteed O(log n) performance
- Data is highly dynamic with frequent insertions/deletions
- Memory is not a constraint

## Complete Java Implementation

```java
/**
 * Jump Search Implementation - Complete Guide
 * Demonstrates jump search with variations and optimizations
 */
public class JumpSearch {
    
    /**
     * Basic Jump Search
     * Searches using jump intervals of sqrt(n)
     * Time: O(√n), Space: O(1)
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @return Index of target if found, -1 otherwise
     */
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        
        // Calculate optimal jump size
        int jump = (int) Math.sqrt(n);
        
        // Initialize previous to track the block to search
        int previous = 0;
        
        // Jump through the array blocks
        while (arr[Math.min(jump, n) - 1] < target) {
            previous = jump;
            jump += (int) Math.sqrt(n);
            
            // If we've gone past the array, element not found
            if (previous >= n) {
                return -1;
            }
        }
        
        // Linear search in the identified block
        while (arr[previous] < target) {
            previous++;
            
            // If we've reached the next jump position and haven't found target
            if (previous == Math.min(jump, n)) {
                return -1;
            }
        }
        
        // Check if element is found
        if (arr[previous] == target) {
            return previous;
        }
        
        return -1; // Element not found
    }
    
    /**
     * Optimized Jump Search with Step Count
     * Returns step count for analysis
     * Time: O(√n), Space: O(1)
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @return Index of target or -1, with performance metrics
     */
    public static int[] jumpSearchWithStats(int[] arr, int target) {
        int n = arr.length;
        int jump = (int) Math.sqrt(n);
        int previous = 0;
        int comparisons = 0;
        int jumps = 0;
        
        // Jump phase
        while (arr[Math.min(jump, n) - 1] < target) {
            comparisons++;
            previous = jump;
            jump += (int) Math.sqrt(n);
            jumps++;
            
            if (previous >= n) {
                return new int[]{-1, comparisons, jumps};
            }
        }
        
        // Linear search phase
        while (arr[previous] < target) {
            comparisons++;
            previous++;
            
            if (previous == Math.min(jump, n)) {
                return new int[]{-1, comparisons, jumps};
            }
        }
        
        // Check if found
        comparisons++;
        if (arr[previous] == target) {
            return new int[]{previous, comparisons, jumps};
        }
        
        return new int[]{-1, comparisons, jumps};
    }
    
    /**
     * Jump Search for First Occurrence
     * Finds the first (leftmost) occurrence of target
     * Useful for arrays with duplicates
     * Time: O(√n), Space: O(1)
     * 
     * @param arr Sorted array with possible duplicates
     * @param target Element to find
     * @return Index of first occurrence, -1 if not found
     */
    public static int jumpSearchFirstOccurrence(int[] arr, int target) {
        int n = arr.length;
        int jump = (int) Math.sqrt(n);
        int previous = 0;
        
        // Jump to find the block
        while (arr[Math.min(jump, n) - 1] < target) {
            previous = jump;
            jump += (int) Math.sqrt(n);
            
            if (previous >= n) {
                return -1;
            }
        }
        
        // Linear search in the block
        while (arr[previous] < target) {
            previous++;
            
            if (previous == Math.min(jump, n)) {
                return -1;
            }
        }
        
        // Check if found
        if (arr[previous] == target) {
            // Move back to find first occurrence
            while (previous > 0 && arr[previous - 1] == target) {
                previous--;
            }
            return previous;
        }
        
        return -1;
    }
    
    /**
     * Jump Search for Last Occurrence
     * Finds the last (rightmost) occurrence of target
     * Useful for arrays with duplicates
     * Time: O(√n), Space: O(1)
     * 
     * @param arr Sorted array with possible duplicates
     * @param target Element to find
     * @return Index of last occurrence, -1 if not found
     */
    public static int jumpSearchLastOccurrence(int[] arr, int target) {
        int n = arr.length;
        int jump = (int) Math.sqrt(n);
        int previous = 0;
        
        // Jump to find the block
        while (arr[Math.min(jump, n) - 1] < target) {
            previous = jump;
            jump += (int) Math.sqrt(n);
            
            if (previous >= n) {
                return -1;
            }
        }
        
        // Linear search in the block
        while (arr[previous] < target) {
            previous++;
            
            if (previous == Math.min(jump, n)) {
                return -1;
            }
        }
        
        // Check if found
        if (arr[previous] == target) {
            // Move forward to find last occurrence
            while (previous + 1 < Math.min(jump, n) && arr[previous + 1] == target) {
                previous++;
            }
            return previous;
        }
        
        return -1;
    }
    
    /**
     * Count Occurrences using Jump Search
     * Counts total occurrences of target
     * Time: O(√n), Space: O(1)
     * 
     * @param arr Sorted array with possible duplicates
     * @param target Element to count
     * @return Number of times target appears
     */
    public static int countOccurrencesJump(int[] arr, int target) {
        int first = jumpSearchFirstOccurrence(arr, target);
        
        if (first == -1) {
            return 0;
        }
        
        int last = jumpSearchLastOccurrence(arr, target);
        return last - first + 1;
    }
    
    /**
     * Jump Search with Custom Jump Size
     * Allows specifying custom jump interval for optimization
     * Time: O(√n), Space: O(1)
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @param customJump Custom jump size
     * @return Index of target if found, -1 otherwise
     */
    public static int jumpSearchCustomJump(int[] arr, int target, int customJump) {
        int n = arr.length;
        int jump = customJump;
        int previous = 0;
        
        // Validate jump size
        if (jump <= 0) {
            jump = (int) Math.sqrt(n);
        }
        
        // Jump phase
        while (arr[Math.min(jump, n) - 1] < target) {
            previous = jump;
            jump += customJump;
            
            if (previous >= n) {
                return -1;
            }
        }
        
        // Linear search phase
        while (arr[previous] < target) {
            previous++;
            
            if (previous == Math.min(jump, n)) {
                return -1;
            }
        }
        
        // Check if found
        if (arr[previous] == target) {
            return previous;
        }
        
        return -1;
    }
    
    /**
     * Calculate Optimal Jump Size
     * Returns the theoretically optimal jump size
     * Optimal jump = sqrt(n) where n is array length
     * 
     * @param arrayLength Length of array
     * @return Optimal jump size
     */
    public static int calculateOptimalJumpSize(int arrayLength) {
        return (int) Math.sqrt(arrayLength);
    }
    
    /**
     * Compare Algorithm Performance
     * Compares jump size options
     * 
     * @param arr Sorted array
     * @param target Element to search
     */
    public static void analyzeJumpSizes(int[] arr, int target) {
        int n = arr.length;
        int optimalJump = (int) Math.sqrt(n);
        
        System.out.println("Array length: " + n);
        System.out.println("Optimal jump size (√n): " + optimalJump);
        System.out.println();
        
        // Test different jump sizes
        int[] jumpSizes = {
            optimalJump / 2,
            optimalJump,
            optimalJump + optimalJump / 2,
            optimalJump * 2
        };
        
        for (int jumpSize : jumpSizes) {
            int[] stats = jumpSearchWithStats(arr, target);
            // Would show comparison data here
        }
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
     * Main method - Demonstrates jump search variations
     */
    public static void main(String[] args) {
        System.out.println("========== JUMP SEARCH DEMONSTRATIONS ==========\n");
        
        // Test array 1: Basic sorted array
        int[] arr1 = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377};
        System.out.println("Test Array 1: Basic Sorted Array");
        printArray(arr1);
        System.out.println("Sorted: " + isSorted(arr1));
        System.out.println("Array Length: " + arr1.length);
        System.out.println("Optimal Jump Size: " + calculateOptimalJumpSize(arr1.length));
        System.out.println();
        
        // Demonstrate basic jump search
        System.out.println("--- Basic Jump Search ---");
        int target = 89;
        int result = jumpSearch(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate jump search with statistics
        System.out.println("--- Jump Search with Performance Metrics ---");
        target = 55;
        int[] stats = jumpSearchWithStats(arr1, target);
        System.out.println("Searching for: " + target);
        System.out.println("Result: " + (stats[0] != -1 ? "Found at index " + stats[0] : "Not found"));
        System.out.println("Total comparisons: " + stats[1]);
        System.out.println("Number of jumps: " + stats[2]);
        System.out.println();
        
        // Demonstrate search for non-existent element
        System.out.println("--- Search for Non-existent Element ---");
        target = 100;
        result = jumpSearch(arr1, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found (expected)");
        }
        System.out.println();
        
        // Test array 2: Array with duplicates
        int[] arr2 = {1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 7, 8, 8, 9, 9, 9, 10};
        System.out.println("Test Array 2: Sorted Array with Duplicates");
        printArray(arr2);
        System.out.println();
        
        // Demonstrate first occurrence search
        System.out.println("--- Find First Occurrence ---");
        target = 5;
        result = jumpSearchFirstOccurrence(arr2, target);
        System.out.println("First occurrence of " + target + ":");
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate last occurrence search
        System.out.println("--- Find Last Occurrence ---");
        target = 5;
        result = jumpSearchLastOccurrence(arr2, target);
        System.out.println("Last occurrence of " + target + ":");
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Demonstrate count occurrences
        System.out.println("--- Count Occurrences ---");
        target = 5;
        int count = countOccurrencesJump(arr2, target);
        System.out.println("Element " + target + " appears " + count + " time(s)");
        System.out.println();
        
        // Test with custom jump size
        System.out.println("--- Jump Search with Custom Jump Size ---");
        int[] arr3 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        System.out.println("Array: ");
        printArray(arr3);
        
        target = 70;
        int optimalJump = calculateOptimalJumpSize(arr3.length);
        System.out.println("Optimal jump: " + optimalJump);
        System.out.println();
        
        System.out.println("Testing different jump sizes for target " + target + ":");
        int[] customJumps = {1, 2, 3, optimalJump, 5};
        for (int jump : customJumps) {
            result = jumpSearchCustomJump(arr3, target, jump);
            System.out.println("Jump size " + jump + ": " + (result != -1 ? "Found at index " + result : "Not found"));
        }
        System.out.println();
        
        // Test array 3: Large sorted array
        int[] arr4 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arr4[i] = i * 2; // Even numbers from 0 to 1998
        }
        
        System.out.println("Test Array 3: Large Sorted Array");
        System.out.println("Array size: " + arr4.length);
        System.out.println("Optimal jump size: " + calculateOptimalJumpSize(arr4.length));
        System.out.println();
        
        // Performance test on large array
        System.out.println("--- Performance on Large Array ---");
        target = 1000;
        result = jumpSearch(arr4, target);
        System.out.println("Searching for: " + target);
        if (result != -1) {
            System.out.println("✓ Found at index: " + result);
        } else {
            System.out.println("✗ Element not found");
        }
        System.out.println();
        
        // Complexity analysis
        System.out.println("--- Complexity Analysis ---");
        System.out.println("Time Complexity: O(√n)");
        System.out.println("  - Best Case: O(1) - Element at first jump position");
        System.out.println("  - Average Case: O(√n)");
        System.out.println("  - Worst Case: O(√n) - Element at end or not found");
        System.out.println("Space Complexity: O(1) - Constant extra space");
        System.out.println();
        
        // Comparison with other algorithms
        System.out.println("--- Comparison with Other Algorithms ---");
        int n = 1000000;
        System.out.println("Array size: " + n);
        double linearOps = (double) n / 2;
        double binaryOps = Math.log(n) / Math.log(2);
        double jumpOps = Math.sqrt(n);
        
        System.out.println("Linear Search: ~" + (int)linearOps + " operations (average)");
        System.out.println("Binary Search: ~" + (int)binaryOps + " operations");
        System.out.println("Jump Search: ~" + (int)jumpOps + " operations");
        System.out.println();
        System.out.println("Jump Search is " + String.format("%.1f", linearOps / jumpOps) + "x faster than Linear");
        System.out.println("Binary Search is " + String.format("%.1f", jumpOps / binaryOps) + "x faster than Jump");
    }
}
```

## Advantages and Disadvantages

### Advantages ✅
- Better than linear search - O(√n) vs O(n)
- Simpler than binary search to implement
- Works well with large arrays
- Constant space complexity
- Good cache locality due to sequential access
- Predictable performance

### Disadvantages ❌
- Not as fast as binary search - O(√n) vs O(log n)
- Requires sorted array
- Not suitable for linked lists
- Linear search phase can be slow for large blocks
- Not useful for very small arrays (< 10 elements)

## Practical Applications
- File systems searching
- Database searching with limited memory
- Cache-efficient searching
- Systems with slow random access
- Embedded systems with memory constraints
- Large dataset searching on tape storage
- Searching in sorted log files

## Jump Size Optimization

The optimal jump size is √n because:
- Jump phase: ~√n jumps in worst case
- Linear search phase: ~√n elements in worst case
- Total: O(√n) + O(√n) = O(√n)

Smaller jumps = more jumps, larger blocks
Larger jumps = fewer jumps, larger linear search

## Key Takeaways
- Jump search provides a middle ground between linear and binary search
- Optimal jump size of √n minimizes total comparisons
- Useful when binary search is not available or memory is limited
- Performs well for moderate-sized sorted arrays
- Excellent for systems where cache behavior matters
