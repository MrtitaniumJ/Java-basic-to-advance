# Modified Binary Search Pattern

## Table of Contents
1. [Introduction](#introduction)
2. [Core Concept](#core-concept)
3. [Template Variations](#template-variations)
4. [Common Patterns](#common-patterns)
5. [Implementation Examples](#implementation-examples)
6. [Boundary Conditions](#boundary-conditions)
7. [Complexity Analysis](#complexity-analysis)
8. [Practice Problems](#practice-problems)

## Introduction

Modified Binary Search is an extension of the classic binary search algorithm applied to problems where the search space isn't a traditional sorted array. This pattern is crucial for solving optimization problems, searching in rotated or modified arrays, and finding elements in 2D matrices. Understanding these variations opens up solutions to numerous algorithmic challenges that appear frequently in technical interviews.

The power of modified binary search lies in its ability to reduce time complexity from O(n) to O(log n) by eliminating half of the search space in each iteration. However, the key challenge is identifying when and how to apply this pattern to non-standard scenarios.

## Core Concept

The fundamental principle remains the same as classic binary search: **divide and conquer by eliminating half of the search space**. The modification comes in:

1. **Defining the search space** - Not always a simple sorted array
2. **Determining the mid condition** - Custom logic based on problem requirements
3. **Deciding which half to eliminate** - May require additional comparisons
4. **Handling boundary cases** - Edge cases specific to the variation

### When to Use Modified Binary Search

- Array is sorted but rotated
- Array has some monotonic property (increasing/decreasing in parts)
- 2D matrices with sorted rows and columns
- Searching in infinite or unbounded arrays
- Finding optimal values (min/max) in constrained spaces
- Problems asking for "find first/last occurrence"

## Template Variations

### Template 1: Standard Binary Search
```java
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

### Template 2: Left Boundary Search
```java
int leftBoundary(int[] arr, int target) {
    int left = 0, right = arr.length;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] < target) left = mid + 1;
        else right = mid;
    }
    return left;
}
```

### Template 3: Right Boundary Search
```java
int rightBoundary(int[] arr, int target) {
    int left = 0, right = arr.length;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] <= target) left = mid + 1;
        else right = mid;
    }
    return left - 1;
}
```

## Common Patterns

### 1. Search in Rotated Sorted Array
A sorted array is rotated at an unknown pivot point. Find a target element.

**Key Insight**: At least one half of the array is always sorted. Determine which half is sorted, then decide whether to search that half or the other.

### 2. Search in Infinite Array
Array is sorted but infinitely large (or bounds unknown). Find target efficiently.

**Key Insight**: First find a bounded range where target exists by exponentially increasing the search boundary, then apply binary search.

### 3. Bitonic Array Search
Array first increases, then decreases (or vice versa). Find peak or search for element.

**Key Insight**: Use binary search to find the peak, then search both sides independently.

### 4. Search in 2D Matrix
Matrix has rows and columns sorted. Find target element.

**Key Insight**: Treat matrix as a single sorted array or start from corners to eliminate rows/columns.

### 5. Find Minimum in Rotated Array
Rotated sorted array, find minimum element.

**Key Insight**: Minimum is at the rotation point. Compare mid with rightmost element to determine which half contains the minimum.

### 6. Integer Square Root
Find floor of square root without using built-in functions.

**Key Insight**: Binary search on range [0, x] to find largest number whose square ≤ x.

## Implementation Examples

```java
/**
 * Modified Binary Search - Complete Implementation
 * Demonstrates various binary search variations and patterns
 */
public class ModifiedBinarySearch {
    
    // ==================== PATTERN 1: Rotated Array Search ====================
    
    /**
     * Search in Rotated Sorted Array
     * Time: O(log n), Space: O(1)
     * 
     * Example: [4,5,6,7,0,1,2], target = 0 → index 4
     */
    public int searchRotated(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) return mid;
            
            // Determine which half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;  // Target in left half
                } else {
                    left = mid + 1;   // Target in right half
                }
            } else {
                // Right half is sorted
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;   // Target in right half
                } else {
                    right = mid - 1;  // Target in left half
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Search in Rotated Array with Duplicates
     * Time: O(log n) average, O(n) worst case
     */
    public boolean searchRotatedWithDuplicates(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) return true;
            
            // Handle duplicates by shrinking the boundary
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right--;
            } else if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return false;
    }
    
    // ==================== PATTERN 2: Infinite Array ====================
    
    /**
     * Search in Infinite Sorted Array
     * Time: O(log n), Space: O(1)
     */
    public int searchInfinite(ArrayReader reader, int target) {
        // First, find the boundaries
        int left = 0, right = 1;
        
        // Exponentially expand right boundary until target is within bounds
        while (reader.get(right) < target) {
            left = right;
            right = right * 2;
        }
        
        // Now perform binary search in [left, right]
        return binarySearchInfinite(reader, target, left, right);
    }
    
    private int binarySearchInfinite(ArrayReader reader, int target, 
                                     int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = reader.get(mid);
            
            if (midValue == target) return mid;
            else if (midValue < target) left = mid + 1;
            else right = mid - 1;
        }
        
        return -1;
    }
    
    // Interface for infinite array
    interface ArrayReader {
        int get(int index);
    }
    
    // ==================== PATTERN 3: Bitonic Array ====================
    
    /**
     * Find Peak in Bitonic Array
     * Time: O(log n), Space: O(1)
     * 
     * Bitonic array: increases then decreases
     */
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[mid + 1]) {
                // We're in ascending part, peak is to the right
                left = mid + 1;
            } else {
                // We're in descending part or at peak
                right = mid;
            }
        }
        
        return left;  // left == right at peak
    }
    
    /**
     * Search in Bitonic Array
     * Time: O(log n), Space: O(1)
     */
    public int searchBitonic(int[] arr, int target) {
        int peak = findPeakElement(arr);
        
        // Search in ascending part
        int result = binarySearch(arr, target, 0, peak, true);
        if (result != -1) return result;
        
        // Search in descending part
        return binarySearch(arr, target, peak + 1, arr.length - 1, false);
    }
    
    private int binarySearch(int[] arr, int target, int left, int right, 
                            boolean isAscending) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) return mid;
            
            if (isAscending) {
                if (arr[mid] < target) left = mid + 1;
                else right = mid - 1;
            } else {
                if (arr[mid] > target) left = mid + 1;
                else right = mid - 1;
            }
        }
        
        return -1;
    }
    
    // ==================== PATTERN 4: Search Range ====================
    
    /**
     * Find First and Last Position in Sorted Array
     * Time: O(log n), Space: O(1)
     * 
     * Example: [5,7,7,8,8,10], target = 8 → [3,4]
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) return result;
        
        // Find leftmost occurrence
        result[0] = findBoundary(nums, target, true);
        if (result[0] == -1) return result;  // Target not found
        
        // Find rightmost occurrence
        result[1] = findBoundary(nums, target, false);
        
        return result;
    }
    
    private int findBoundary(int[] nums, int target, boolean isLeft) {
        int left = 0, right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                result = mid;
                if (isLeft) {
                    right = mid - 1;  // Continue searching left
                } else {
                    left = mid + 1;   // Continue searching right
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // ==================== PATTERN 5: Find Minimum in Rotated Array ====================
    
    /**
     * Find Minimum in Rotated Sorted Array
     * Time: O(log n), Space: O(1)
     * 
     * Example: [3,4,5,1,2] → 1
     */
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // Minimum is in right half
                left = mid + 1;
            } else {
                // Minimum is in left half or at mid
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Find Minimum with Duplicates
     * Time: O(log n) average, O(n) worst case
     */
    public int findMinWithDuplicates(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // nums[mid] == nums[right], can't determine which half
                right--;  // Safely reduce right boundary
            }
        }
        
        return nums[left];
    }
    
    // ==================== PATTERN 6: Search 2D Matrix ====================
    
    /**
     * Search in 2D Matrix (each row sorted, first element > last of previous row)
     * Time: O(log(m*n)), Space: O(1)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;
        
        int rows = matrix.length, cols = matrix[0].length;
        int left = 0, right = rows * cols - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = matrix[mid / cols][mid % cols];
            
            if (midValue == target) return true;
            else if (midValue < target) left = mid + 1;
            else right = mid - 1;
        }
        
        return false;
    }
    
    /**
     * Search in 2D Matrix II (rows and columns independently sorted)
     * Time: O(m + n), Space: O(1)
     */
    public boolean searchMatrixII(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;
        
        int row = 0, col = matrix[0].length - 1;
        
        // Start from top-right corner
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                row++;  // Move down
            } else {
                col--;  // Move left
            }
        }
        
        return false;
    }
    
    // ==================== PATTERN 7: Integer Square Root ====================
    
    /**
     * Find integer square root (floor)
     * Time: O(log n), Space: O(1)
     * 
     * Example: sqrt(8) = 2, sqrt(9) = 3
     */
    public int mySqrt(int x) {
        if (x < 2) return x;
        
        int left = 1, right = x / 2;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;  // Prevent overflow
            
            if (square == x) {
                return mid;
            } else if (square < x) {
                result = mid;  // Store potential answer
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Find square root with precision
     * Time: O(log(x/precision)), Space: O(1)
     */
    public double mySqrtPrecise(int x, double precision) {
        if (x < 2) return x;
        
        double left = 1, right = x;
        
        while (right - left > precision) {
            double mid = left + (right - left) / 2;
            double square = mid * mid;
            
            if (square < x) {
                left = mid;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    // ==================== ADDITIONAL PATTERNS ====================
    
    /**
     * Find Rotation Count (number of rotations in rotated array)
     * Time: O(log n), Space: O(1)
     */
    public int findRotationCount(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            if (nums[left] < nums[right]) {
                // Array is not rotated from this point
                return left;
            }
            
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Search Insert Position
     * Time: O(log n), Space: O(1)
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        
        return left;  // Insert position
    }
    
    /**
     * Find K Closest Elements
     * Time: O(log n + k), Space: O(1)
     */
    public int[] findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare distances from x to mid and mid+k
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = arr[left + i];
        }
        
        return result;
    }
}
```

## Boundary Conditions

### Critical Edge Cases to Consider

1. **Empty or Null Arrays**
   - Always check `arr == null || arr.length == 0`
   - Return appropriate default value (-1 for search, 0 for count)

2. **Single Element Arrays**
   - Verify loop conditions work with `length == 1`
   - Test both match and no-match scenarios

3. **Two Element Arrays**
   - Ensure mid calculation doesn't cause infinite loops
   - Test when target is at both positions

4. **Integer Overflow**
   - Use `mid = left + (right - left) / 2` instead of `(left + right) / 2`
   - For square root, cast to long: `(long) mid * mid`

5. **Loop Termination**
   - `while (left <= right)` for exact search
   - `while (left < right)` for boundary search
   - Ensure progress is made each iteration

6. **Array Boundaries**
   - Check `mid + 1` and `mid - 1` don't go out of bounds
   - Verify `nums[mid]`, `nums[left]`, `nums[right]` are valid

7. **Duplicate Elements**
   - May require special handling (shrink boundaries)
   - Can degrade to O(n) in worst case

8. **All Elements Same**
   - Test with arrays like `[5,5,5,5,5]`
   - Verify algorithm doesn't infinite loop

## Complexity Analysis

### Time Complexity

| Pattern | Best Case | Average Case | Worst Case |
|---------|-----------|--------------|------------|
| Standard Binary Search | O(1) | O(log n) | O(log n) |
| Rotated Array | O(1) | O(log n) | O(log n) |
| Rotated with Duplicates | O(1) | O(log n) | O(n) |
| Infinite Array | O(1) | O(log n) | O(log n) |
| Bitonic Array | O(1) | O(log n) | O(log n) |
| 2D Matrix (sorted) | O(1) | O(log(m×n)) | O(log(m×n)) |
| 2D Matrix II | O(1) | O(m+n) | O(m+n) |
| Square Root | O(1) | O(log n) | O(log n) |

**Key Points:**
- Most modified binary searches maintain O(log n) time complexity
- Duplicates can degrade performance to O(n) worst case
- 2D matrix search depends on matrix properties

### Space Complexity

All implementations shown use **O(1)** space complexity as they:
- Use only a constant number of variables (left, right, mid)
- Don't use recursion (iterative approach)
- Don't create additional data structures

**Recursive versions** would have O(log n) space due to call stack.

## Practice Problems

### Easy Level
1. Binary Search (LeetCode 704)
2. Search Insert Position (LeetCode 35)
3. First Bad Version (LeetCode 278)
4. Valid Perfect Square (LeetCode 367)

### Medium Level
5. Search in Rotated Sorted Array (LeetCode 33)
6. Find Peak Element (LeetCode 162)
7. Find Minimum in Rotated Sorted Array (LeetCode 153)
8. Search a 2D Matrix (LeetCode 74)
9. Search a 2D Matrix II (LeetCode 240)
10. Find First and Last Position (LeetCode 34)
11. Sqrt(x) (LeetCode 69)

### Hard Level
12. Search in Rotated Sorted Array II (LeetCode 81)
13. Find Minimum in Rotated Sorted Array II (LeetCode 154)
14. Median of Two Sorted Arrays (LeetCode 4)
15. Split Array Largest Sum (LeetCode 410)

## Key Takeaways

1. **Recognize the Pattern**: Not all binary search problems involve sorted arrays. Look for monotonic properties or ways to eliminate search space.

2. **Choose Right Template**: Different problems require different loop conditions and boundary updates.

3. **Handle Edge Cases**: Always consider empty arrays, single elements, duplicates, and integer overflow.

4. **Test Thoroughly**: Use small examples (1-3 elements) to verify your logic before implementing.

5. **Understand Trade-offs**: Duplicates can degrade performance; sometimes linear scan of a portion is acceptable.

6. **Practice Variations**: Master the standard patterns, then tackle harder variations with confidence.

Modified binary search is a powerful technique that transforms O(n) problems into O(log n) solutions. By understanding these patterns and practicing implementations, you'll be well-equipped to tackle a wide range of algorithmic challenges efficiently.

---

**Next Steps**: Practice implementing these patterns from scratch, then solve the recommended problems. Focus on understanding the invariant that each algorithm maintains and how boundaries shrink correctly toward the solution.
