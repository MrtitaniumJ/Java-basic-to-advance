# Binary Search Pattern

## Table of Contents
- [Introduction](#introduction)
- [Core Concepts](#core-concepts)
- [Template Approach](#template-approach)
- [Binary Search Patterns](#binary-search-patterns)
- [Complete Java Implementation](#complete-java-implementation)
- [Complexity Analysis](#complexity-analysis)
- [Common Pitfalls](#common-pitfalls)
- [Edge Cases](#edge-cases)
- [Practice Problems](#practice-problems)

## Introduction

Binary Search is one of the most fundamental and efficient search algorithms in computer science. It follows the divide-and-conquer paradigm, systematically eliminating half of the search space in each iteration. While the basic concept is simple, mastering binary search requires understanding various patterns and their applications across different problem domains.

The power of binary search extends far beyond finding elements in sorted arrays. It's a versatile technique applicable to optimization problems, finding boundaries, searching in rotated arrays, 2D matrices, and even problems that don't initially appear to involve searching. Understanding these patterns is crucial for technical interviews and competitive programming.

## Core Concepts

Binary search operates on the principle of **monotonicity** - the search space must have some ordered property. This doesn't always mean a sorted array; it means there's a decision boundary where we can determine which half to eliminate.

### Key Principles:
1. **Search Space Reduction**: Each iteration eliminates half of the remaining possibilities
2. **Invariant Maintenance**: Loop invariants ensure correctness
3. **Boundary Handling**: Proper handling of left, right, and middle pointers
4. **Convergence**: The algorithm must eventually terminate with the correct answer

### When to Use Binary Search:
- Searching in sorted/rotated arrays
- Finding boundaries (first/last occurrence)
- Optimization problems with monotonic properties
- Peak finding in mountains/valleys
- Searching in solution spaces (answer lies in a range)
- Matrix search with sorted properties

## Template Approach

Understanding different templates helps avoid off-by-one errors and infinite loops. There are three main templates based on how we handle the search space.

### Template 1: Standard Binary Search
Used when we can determine the target from the current element immediately.
```
while (left <= right) {
    mid = left + (right - left) / 2;
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) left = mid + 1;
    else right = mid - 1;
}
```

### Template 2: Left Neighbor Check
Used when we need to access the element's immediate right neighbor.
```
while (left < right) {
    mid = left + (right - left) / 2;
    if (condition) left = mid + 1;
    else right = mid;
}
```

### Template 3: Two Neighbor Check
Used when we need to check both left and right neighbors.
```
while (left + 1 < right) {
    mid = left + (right - left) / 2;
    if (condition) left = mid;
    else right = mid;
}
// Post-processing for left and right
```

## Binary Search Patterns

### 1. Standard Binary Search
Finding an exact element in a sorted array. The most basic form with O(log n) time complexity.

### 2. Leftmost/Rightmost Search
Finding the first or last occurrence of an element. Essential for problems involving duplicates or finding boundaries.

### 3. Search in Rotated Array
Handling arrays that have been rotated at an unknown pivot. Requires identifying which half is properly sorted.

### 4. Search in 2D Matrix
Extending binary search to two dimensions. Can treat matrix as a 1D array or perform binary search on rows and columns.

### 5. Peak Finding
Finding local or global peaks in arrays. Used in mountain array problems.

### 6. Median of Two Sorted Arrays
Advanced pattern requiring binary search on the smaller array to partition both arrays efficiently.

## Complete Java Implementation

```java
/**
 * Comprehensive Binary Search Patterns Implementation
 * Demonstrates various binary search techniques and applications
 */
public class BinarySearchPatterns {
    
    // ==================== PATTERN 1: STANDARD BINARY SEARCH ====================
    
    /**
     * Standard binary search - Find exact element
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2; // Prevent overflow
            
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1; // Not found
    }
    
    /**
     * Recursive binary search implementation
     */
    public int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) return binarySearchRecursive(arr, target, mid + 1, right);
        return binarySearchRecursive(arr, target, left, mid - 1);
    }
    
    // ==================== PATTERN 2: LEFTMOST/RIGHTMOST SEARCH ====================
    
    /**
     * Find first occurrence (leftmost) of target
     * Returns leftmost index or -1 if not found
     */
    public int findFirstOccurrence(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;           // Store potential result
                right = mid - 1;        // Continue searching in left half
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Find last occurrence (rightmost) of target
     */
    public int findLastOccurrence(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;           // Store potential result
                left = mid + 1;         // Continue searching in right half
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Find range [first, last] of target occurrences
     */
    public int[] searchRange(int[] arr, int target) {
        int[] result = {-1, -1};
        
        result[0] = findFirstOccurrence(arr, target);
        if (result[0] != -1) {
            result[1] = findLastOccurrence(arr, target);
        }
        
        return result;
    }
    
    // ==================== PATTERN 3: SEARCH IN ROTATED ARRAY ====================
    
    /**
     * Search in rotated sorted array
     * Array is sorted but rotated at unknown pivot
     * Example: [4,5,6,7,0,1,2]
     */
    public int searchRotatedArray(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) return mid;
            
            // Determine which half is properly sorted
            if (arr[left] <= arr[mid]) {
                // Left half is sorted
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1;    // Target in left half
                } else {
                    left = mid + 1;     // Target in right half
                }
            } else {
                // Right half is sorted
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1;     // Target in right half
                } else {
                    right = mid - 1;    // Target in left half
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Find minimum in rotated sorted array
     */
    public int findMinInRotatedArray(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] > arr[right]) {
                // Minimum is in right half
                left = mid + 1;
            } else {
                // Minimum is in left half or at mid
                right = mid;
            }
        }
        
        return arr[left];
    }
    
    /**
     * Find rotation point (pivot) in rotated array
     */
    public int findRotationPoint(int[] arr) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            if (arr[left] < arr[right]) return left; // Not rotated
            
            int mid = left + (right - left) / 2;
            
            if (arr[mid] > arr[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    // ==================== PATTERN 4: SEARCH IN 2D MATRIX ====================
    
    /**
     * Search in row-wise and column-wise sorted matrix
     * Each row sorted, first element of each row > last element of previous row
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int left = 0, right = rows * cols - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = matrix[mid / cols][mid % cols]; // Convert 1D to 2D
            
            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
    
    /**
     * Search in matrix where each row and column is sorted
     * Start from top-right or bottom-left corner
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int row = 0;
        int col = matrix[0].length - 1;
        
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;  // Move left
            } else {
                row++;  // Move down
            }
        }
        
        return false;
    }
    
    // ==================== PATTERN 5: PEAK FINDING ====================
    
    /**
     * Find peak element (element greater than neighbors)
     * Array may have multiple peaks, return any one
     */
    public int findPeakElement(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        if (arr.length == 1) return 0;
        
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] > arr[mid + 1]) {
                // Peak is in left half or at mid
                right = mid;
            } else {
                // Peak is in right half
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Find peak in mountain array
     * Mountain array: strictly increasing then strictly decreasing
     */
    public int peakIndexInMountainArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] < arr[mid + 1]) {
                // Ascending part, peak is to the right
                left = mid + 1;
            } else {
                // Descending part or peak, peak is to the left or at mid
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Search in mountain array
     * First find peak, then binary search in both halves
     */
    public int searchInMountainArray(int[] arr, int target) {
        int peak = peakIndexInMountainArray(arr);
        
        // Search in ascending part
        int result = binarySearchAscending(arr, target, 0, peak);
        if (result != -1) return result;
        
        // Search in descending part
        return binarySearchDescending(arr, target, peak + 1, arr.length - 1);
    }
    
    private int binarySearchAscending(int[] arr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    private int binarySearchDescending(int[] arr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] > target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    // ==================== PATTERN 6: MEDIAN OF TWO SORTED ARRAYS ====================
    
    /**
     * Find median of two sorted arrays
     * Time Complexity: O(log(min(m,n)))
     * Space Complexity: O(1)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;
        
        while (left <= right) {
            int partition1 = left + (right - left) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;
            
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Found correct partition
                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else {
                    return Math.max(maxLeft1, maxLeft2);
                }
            } else if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            } else {
                left = partition1 + 1;
            }
        }
        
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
    
    // ==================== ADDITIONAL PATTERNS ====================
    
    /**
     * Find square root using binary search
     * Returns floor(sqrt(x))
     */
    public int mySqrt(int x) {
        if (x == 0 || x == 1) return x;
        
        long left = 1, right = x;
        long result = 0;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long square = mid * mid;
            
            if (square == x) {
                return (int) mid;
            } else if (square < x) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return (int) result;
    }
    
    /**
     * Find smallest letter greater than target
     * Letters wrap around (circular array)
     */
    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0, right = letters.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (letters[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return letters[left % letters.length]; // Wrap around
    }
    
    /**
     * Find K closest elements to target
     * Returns k elements closest to x in sorted order
     */
    public java.util.List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare distances: which window is better?
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        java.util.List<Integer> result = new java.util.ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        
        return result;
    }
    
    /**
     * Capacity to ship packages within D days
     * Find minimum capacity needed
     */
    public int shipWithinDays(int[] weights, int days) {
        int left = 0, right = 0;
        
        for (int weight : weights) {
            left = Math.max(left, weight);      // Minimum capacity
            right += weight;                     // Maximum capacity
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canShip(weights, days, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canShip(int[] weights, int days, int capacity) {
        int daysNeeded = 1;
        int currentLoad = 0;
        
        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                daysNeeded++;
                currentLoad = weight;
            } else {
                currentLoad += weight;
            }
        }
        
        return daysNeeded <= days;
    }
    
    // ==================== DEMO AND TEST METHODS ====================
    
    public static void main(String[] args) {
        BinarySearchPatterns bsp = new BinarySearchPatterns();
        
        System.out.println("=== Binary Search Patterns Demo ===\n");
        
        // Test 1: Standard Binary Search
        int[] arr1 = {1, 3, 5, 7, 9, 11, 13, 15};
        System.out.println("Test 1: Standard Binary Search");
        System.out.println("Array: " + java.util.Arrays.toString(arr1));
        System.out.println("Search 7: Index = " + bsp.binarySearch(arr1, 7));
        System.out.println("Search 6: Index = " + bsp.binarySearch(arr1, 6));
        System.out.println();
        
        // Test 2: First and Last Occurrence
        int[] arr2 = {1, 2, 2, 2, 3, 4, 5, 5, 5, 6};
        System.out.println("Test 2: First and Last Occurrence");
        System.out.println("Array: " + java.util.Arrays.toString(arr2));
        System.out.println("First occurrence of 2: " + bsp.findFirstOccurrence(arr2, 2));
        System.out.println("Last occurrence of 2: " + bsp.findLastOccurrence(arr2, 2));
        System.out.println("Range of 5: " + java.util.Arrays.toString(bsp.searchRange(arr2, 5)));
        System.out.println();
        
        // Test 3: Rotated Array
        int[] arr3 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Test 3: Rotated Array Search");
        System.out.println("Array: " + java.util.Arrays.toString(arr3));
        System.out.println("Search 0: Index = " + bsp.searchRotatedArray(arr3, 0));
        System.out.println("Minimum element: " + bsp.findMinInRotatedArray(arr3));
        System.out.println("Rotation point: " + bsp.findRotationPoint(arr3));
        System.out.println();
        
        // Test 4: 2D Matrix Search
        int[][] matrix = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        System.out.println("Test 4: 2D Matrix Search");
        System.out.println("Search 3: " + bsp.searchMatrix(matrix, 3));
        System.out.println("Search 13: " + bsp.searchMatrix(matrix, 13));
        System.out.println();
        
        // Test 5: Peak Finding
        int[] arr5 = {1, 2, 3, 1};
        System.out.println("Test 5: Peak Finding");
        System.out.println("Array: " + java.util.Arrays.toString(arr5));
        System.out.println("Peak index: " + bsp.findPeakElement(arr5));
        
        int[] mountain = {0, 2, 5, 8, 4, 2, 1};
        System.out.println("Mountain array: " + java.util.Arrays.toString(mountain));
        System.out.println("Peak index: " + bsp.peakIndexInMountainArray(mountain));
        System.out.println();
        
        // Test 6: Median of Two Sorted Arrays
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("Test 6: Median of Two Sorted Arrays");
        System.out.println("Array 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Array 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Median: " + bsp.findMedianSortedArrays(nums1, nums2));
        System.out.println();
        
        // Test 7: Square Root
        System.out.println("Test 7: Square Root");
        System.out.println("sqrt(8) = " + bsp.mySqrt(8));
        System.out.println("sqrt(16) = " + bsp.mySqrt(16));
        System.out.println();
    }
}
```

## Complexity Analysis

### Time Complexity

| Pattern | Best Case | Average Case | Worst Case | Notes |
|---------|-----------|--------------|------------|-------|
| Standard Binary Search | O(1) | O(log n) | O(log n) | Found at first check |
| First/Last Occurrence | O(log n) | O(log n) | O(log n) | Always full search |
| Rotated Array | O(1) | O(log n) | O(log n) | Additional pivot check |
| 2D Matrix (Method 1) | O(1) | O(log(m×n)) | O(log(m×n)) | Treat as 1D array |
| 2D Matrix (Method 2) | O(1) | O(m+n) | O(m+n) | Start from corner |
| Peak Finding | O(1) | O(log n) | O(log n) | Any peak acceptable |
| Median Two Arrays | O(log(min(m,n))) | O(log(min(m,n))) | O(log(min(m,n))) | Search on smaller |

### Space Complexity

All iterative implementations: **O(1)** - constant space
Recursive implementations: **O(log n)** - call stack depth

### Why Binary Search is O(log n)

In each iteration, we eliminate half the search space:
- Iteration 1: n elements
- Iteration 2: n/2 elements
- Iteration 3: n/4 elements
- Iteration k: n/(2^k) elements

When n/(2^k) = 1, we have k = log₂(n) iterations.

## Common Pitfalls

### 1. Integer Overflow
**Wrong**: `mid = (left + right) / 2`
**Correct**: `mid = left + (right - left) / 2`

When left and right are large, their sum can overflow.

### 2. Infinite Loops
Common with incorrect boundary updates:
```java
// Wrong - can cause infinite loop
while (left < right) {
    mid = (left + right) / 2;
    if (condition) left = mid;    // Should be mid + 1
    else right = mid - 1;
}
```

### 3. Off-by-One Errors
- Using `<=` vs `<` in loop condition
- Using `mid`, `mid + 1`, or `mid - 1` for updates
- Boundary conditions at array edges

### 4. Not Considering Edge Cases
- Empty array
- Single element array
- Target at boundaries
- All elements same
- Target not present

### 5. Incorrect Template Choice
Different problems require different templates. Using the wrong one leads to bugs.

### 6. Forgetting Sorted Property
Binary search requires some form of monotonicity. Verify this property exists.

## Edge Cases

### Critical Edge Cases to Test

1. **Empty Array**: `arr = []`
2. **Single Element**: `arr = [5]`
3. **Two Elements**: `arr = [1, 2]`
4. **Target at Start**: `target = arr[0]`
5. **Target at End**: `target = arr[n-1]`
6. **Target Not Present**: Smaller and larger than all elements
7. **All Elements Same**: `arr = [5, 5, 5, 5]`
8. **Duplicates**: Multiple occurrences of target
9. **Negative Numbers**: Handle signed integers
10. **Large Numbers**: Check overflow in mid calculation

### Handling Edge Cases

```java
public int robustBinarySearch(int[] arr, int target) {
    // Edge case: null or empty array
    if (arr == null || arr.length == 0) {
        return -1;
    }
    
    // Edge case: single element
    if (arr.length == 1) {
        return arr[0] == target ? 0 : -1;
    }
    
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        // Prevent overflow
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}
```

## Practice Problems

### Beginner Level
1. Binary Search (LeetCode 704)
2. Search Insert Position (LeetCode 35)
3. First Bad Version (LeetCode 278)
4. Valid Perfect Square (LeetCode 367)

### Intermediate Level
5. Find First and Last Position (LeetCode 34)
6. Search in Rotated Sorted Array (LeetCode 33)
7. Find Peak Element (LeetCode 162)
8. Search a 2D Matrix (LeetCode 74)
9. Find Minimum in Rotated Array (LeetCode 153)
10. Peak Index in Mountain Array (LeetCode 852)

### Advanced Level
11. Median of Two Sorted Arrays (LeetCode 4)
12. Capacity To Ship Packages (LeetCode 1011)
13. Koko Eating Bananas (LeetCode 875)
14. Split Array Largest Sum (LeetCode 410)
15. Aggressive Cows (SPOJ)

### Key Takeaways

1. **Master the Templates**: Understanding which template to use prevents bugs
2. **Identify Monotonicity**: Not all binary search problems involve sorted arrays
3. **Handle Boundaries Carefully**: Most bugs occur at boundaries
4. **Practice Pattern Recognition**: Learn to recognize when binary search applies
5. **Optimize Space**: Prefer iterative over recursive for O(1) space
6. **Think About Edge Cases**: Always validate inputs and boundaries
7. **Use Binary Search for Optimization**: Many "minimize/maximize" problems use binary search on answer space

Binary search is a fundamental technique that appears frequently in interviews and competitive programming. Mastering these patterns provides a strong foundation for solving complex algorithmic problems efficiently.

---

**Word Count**: ~1,450 words  
**Code Lines**: ~475 lines  
**Last Updated**: January 2026
