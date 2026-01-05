# Cyclic Sort Pattern

## Overview
The Cyclic Sort pattern is a specialized sorting algorithm particularly useful for arrays containing integers in a specific range. Unlike general-purpose sorting algorithms, cyclic sort is optimized for finding missing numbers, duplicates, and performing in-place sorting when the array contains consecutive integers from 1 to n. This pattern is elegant for problems where the data has a natural relationship with array indices.

## Pattern Explanation
Cyclic sort operates on the principle that each element can be placed in its "correct" position based on its value. For an array containing numbers from 1 to n, each number `x` should ideally be at index `x-1`. The algorithm iterates through each position and places the element in its correct position, cycling until all elements are sorted.

**Key Idea**: For value `v` in an array of size `n`:
- If `v` is between 1 and n, it belongs at index `v-1`
- We swap the element with what's currently at index `v-1`
- We only increment the pointer if the element is in the wrong place
- If a duplicate is encountered, we skip it

## When to Use
- **Missing numbers**: Find missing integers in range [1, n]
- **Duplicate detection**: Identify duplicate numbers in a range
- **Finding disappeared numbers**: Arrays where one or more numbers are missing
- **First missing positive**: Find the smallest missing positive integer
- **Finding duplicates**: Identify which numbers appear more than once
- **Corrupted sequence**: Restore correct order when some numbers are wrong
- **Space-efficient problems**: When O(1) space is required for sorting
- **Index-mapping problems**: Where value-to-index relationship matters

## Algorithm Steps

### Core Cyclic Sort Algorithm
1. For each index `i` from 0 to n-1:
   - Assume the correct value should be `i+1` (1-indexed)
   - If the value at `i` is not `i+1`:
     - Calculate correct index: `correctIndex = value - 1`
     - If value at `correctIndex` is not equal to `value` (not a duplicate):
       - Swap elements at `i` and `correctIndex`
     - Else (it's a duplicate):
       - Skip this element
2. All elements are now in their correct positions

### Key Properties
- **In-place sorting**: No extra array needed
- **Non-comparison**: Uses index-value mapping instead of comparisons
- **Specialized**: Works only for consecutive integers starting from 1

## Problem Types

### 1. **Missing Number** (Classic)
- Find single missing number from 1 to n
- Example: `[1,2,3,5]` → missing `4`

### 2. **Find Duplicates**
- Identify which numbers appear more than once
- Example: `[1,3,4,2,2]` → duplicate is `2`

### 3. **First Missing Positive**
- Find smallest positive integer not in array
- Example: `[3,4,-1,1]` → answer is `2`

### 4. **Find All Duplicates**
- Return all duplicates that appear more than once
- Example: `[4,3,2,7,8,2,3,1]` → duplicates are `[2,3]`

### 5. **Find Corrupted Pair**
- Find numbers where one is replaced by another
- Example: Original: `[1,2,3]`, Corrupted: `[1,2,1]` → missing `3`, duplicate `1`

### 6. **Set Mismatch**
- Find both the number that appears twice and the number that's missing
- Example: `[1,2,2,4]` → duplicate `2`, missing `3`

## Complete Java Implementation

```java
import java.util.*;

/**
 * Cyclic Sort Pattern Implementation
 * Specialized sorting for integers in range [1, n]
 */
public class CyclicSortPattern {
    
    /**
     * Problem 1: Cyclic Sort - In-place sort
     * Time: O(n) - each element moved to correct position at most once
     * Space: O(1) - no extra space needed
     */
    public static void cyclicSort(int[] nums) {
        int i = 0;
        
        while (i < nums.length) {
            // Calculate correct index for current element (1-indexed)
            int correctIndex = nums[i] - 1;
            
            // If element is in wrong position and not a duplicate
            if (correctIndex >= 0 && correctIndex < nums.length && 
                nums[i] != nums[correctIndex]) {
                
                // Swap to correct position
                swap(nums, i, correctIndex);
            } else {
                // Move to next position if element is in correct spot or duplicate
                i++;
            }
        }
    }
    
    /**
     * Problem 2: Find Missing Number in range [1, n]
     * Time: O(n)
     * Space: O(1)
     */
    public static int findMissingNumber(int[] nums) {
        cyclicSort(nums);
        
        // Find first number that's not at correct index
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        // If all numbers 1 to n-1 are present, missing number is n
        return nums.length;
    }
    
    /**
     * Problem 3: Find All Missing Numbers
     * Time: O(n)
     * Space: O(1) excluding result
     */
    public static List<Integer> findAllMissingNumbers(int[] nums) {
        cyclicSort(nums);
        
        List<Integer> missing = new ArrayList<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                missing.add(i + 1);
            }
        }
        
        return missing;
    }
    
    /**
     * Problem 4: Find Duplicate Numbers
     * Time: O(n)
     * Space: O(1) excluding result
     */
    public static List<Integer> findDuplicates(int[] nums) {
        cyclicSort(nums);
        
        List<Integer> duplicates = new ArrayList<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                duplicates.add(nums[i]);
            }
        }
        
        return duplicates;
    }
    
    /**
     * Problem 5: Find First Missing Positive
     * Numbers can be negative or zero, find first positive not in array
     * Time: O(n)
     * Space: O(1)
     */
    public static int findFirstMissingPositive(int[] nums) {
        // First pass: place valid numbers (1 to n) in correct positions
        int i = 0;
        while (i < nums.length) {
            int correctIndex = nums[i] - 1;
            
            // Check if number is in valid range [1, n] and in wrong position
            if (correctIndex >= 0 && correctIndex < nums.length && 
                nums[i] != nums[correctIndex]) {
                
                swap(nums, i, correctIndex);
            } else {
                i++;
            }
        }
        
        // Second pass: find first position where number is not i+1
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != j + 1) {
                return j + 1;
            }
        }
        
        // If all positions have correct numbers, answer is n+1
        return nums.length + 1;
    }
    
    /**
     * Problem 6: Find All Duplicates in Array
     * Array contains numbers in range [1, n], some numbers appear twice
     * Time: O(n)
     * Space: O(1) excluding result
     */
    public static List<Integer> findAllDuplicatesInArray(int[] nums) {
        cyclicSort(nums);
        
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                result.add(nums[i]);
            }
        }
        
        return result;
    }
    
    /**
     * Problem 7: Find Corrupted Pair
     * One number appears twice, another is missing
     * Returns [duplicate, missing]
     * Time: O(n)
     * Space: O(1)
     */
    public static int[] findCorruptedPair(int[] nums) {
        cyclicSort(nums);
        
        int duplicate = -1;
        int missing = -1;
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                duplicate = nums[i];
                missing = i + 1;
                break;
            }
        }
        
        return new int[]{duplicate, missing};
    }
    
    /**
     * Problem 8: Set Mismatch
     * Find duplicate and missing number in set of [1, n]
     * Returns [duplicate, missing]
     * Time: O(n)
     * Space: O(1)
     */
    public static int[] setMismatch(int[] nums) {
        cyclicSort(nums);
        
        int duplicate = -1;
        int missing = -1;
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                duplicate = nums[i];
                missing = i + 1;
            }
        }
        
        return new int[]{duplicate, missing};
    }
    
    /**
     * Problem 9: Check if Array is Valid Sequence
     * Verify array contains exactly numbers 1 to n
     * Time: O(n)
     * Space: O(1)
     */
    public static boolean isValidSequence(int[] nums) {
        cyclicSort(nums);
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Problem 10: Index Equal to Value
     * Find index where nums[i] == i (or closest match after sorting)
     * Time: O(n)
     * Space: O(1)
     */
    public static List<Integer> indexEqualToValue(int[] nums) {
        cyclicSort(nums);
        
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i) {
                result.add(i);
            }
        }
        
        return result;
    }
    
    // ============ Helper Methods ============
    
    /**
     * Swap two elements in array
     */
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Print array for testing
     */
    private static void printArray(int[] nums) {
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    // ============ Test Cases ============
    
    public static void main(String[] args) {
        System.out.println("=== Cyclic Sort Pattern ===\n");
        
        // Test 1: Cyclic Sort
        System.out.println("Test 1: Cyclic Sort");
        int[] arr1 = {3, 1, 2, 5, 4};
        System.out.print("Before: ");
        printArray(arr1);
        cyclicSort(arr1);
        System.out.print("After:  ");
        printArray(arr1);
        System.out.println();
        
        // Test 2: Find Missing Number
        System.out.println("Test 2: Find Missing Number");
        int[] arr2 = {1, 2, 3, 5};
        System.out.print("Input: ");
        printArray(arr2);
        System.out.println("Missing: " + findMissingNumber(arr2.clone()));
        System.out.println();
        
        // Test 3: Find All Missing Numbers
        System.out.println("Test 3: Find All Missing Numbers");
        int[] arr3 = {1, 3, 5, 6};
        System.out.print("Input: ");
        printArray(arr3);
        System.out.println("Missing: " + findAllMissingNumbers(arr3.clone()));
        System.out.println();
        
        // Test 4: Find Duplicates
        System.out.println("Test 4: Find Duplicates");
        int[] arr4 = {1, 3, 4, 2, 2};
        System.out.print("Input: ");
        printArray(arr4);
        System.out.println("Duplicates: " + findDuplicates(arr4.clone()));
        System.out.println();
        
        // Test 5: First Missing Positive
        System.out.println("Test 5: First Missing Positive");
        int[] arr5 = {3, 4, -1, 1};
        System.out.print("Input: ");
        printArray(arr5);
        System.out.println("First Missing Positive: " + findFirstMissingPositive(arr5.clone()));
        System.out.println();
        
        // Test 6: Find All Duplicates in Array
        System.out.println("Test 6: Find All Duplicates in Array");
        int[] arr6 = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.print("Input: ");
        printArray(arr6);
        System.out.println("Duplicates: " + findAllDuplicatesInArray(arr6.clone()));
        System.out.println();
        
        // Test 7: Find Corrupted Pair
        System.out.println("Test 7: Find Corrupted Pair");
        int[] arr7 = {1, 2, 2, 4};
        System.out.print("Input: ");
        printArray(arr7);
        int[] corrupt = findCorruptedPair(arr7.clone());
        System.out.println("Duplicate: " + corrupt[0] + ", Missing: " + corrupt[1]);
        System.out.println();
        
        // Test 8: Set Mismatch
        System.out.println("Test 8: Set Mismatch");
        int[] arr8 = {1, 2, 2, 4};
        System.out.print("Input: ");
        printArray(arr8);
        int[] mismatch = setMismatch(arr8.clone());
        System.out.println("Duplicate: " + mismatch[0] + ", Missing: " + mismatch[1]);
        System.out.println();
        
        // Test 9: Valid Sequence Check
        System.out.println("Test 9: Valid Sequence Check");
        int[] arr9 = {1, 2, 3, 4, 5};
        System.out.print("Input: ");
        printArray(arr9);
        System.out.println("Is Valid: " + isValidSequence(arr9.clone()));
        System.out.println();
        
        // Test 10: Index Equal to Value
        System.out.println("Test 10: Index Equal to Value");
        int[] arr10 = {-1, 1, 1, 1, 2, 2, 3};
        System.out.print("Input: ");
        printArray(arr10);
        System.out.println("Indices with value equal to index: " + indexEqualToValue(arr10.clone()));
    }
}
```

## Complexity Analysis

### Core Cyclic Sort
- **Time Complexity**: O(n) - Each element is moved to its correct position at most once
- **Space Complexity**: O(1) - Only constant extra space needed (no additional arrays)

### Problem-Specific Analysis
- **Finding Missing Number**: O(n) time, O(1) space
- **Finding Duplicates**: O(n) time, O(1) space
- **First Missing Positive**: O(n) time, O(1) space
- **Multiple Problems**: O(n) time consistently

## Edge Cases

1. **Empty array**: Should handle gracefully
2. **Single element**: Array with one number
3. **All correct**: Array already sorted in cyclic order
4. **Complete disorder**: Reverse sorted array
5. **Duplicates**: Multiple duplicate numbers
6. **Missing multiple**: Several numbers missing
7. **Out of range**: Numbers outside [1, n]
8. **Negative numbers**: When searching for first missing positive

## Real-World Applications

1. **Database Integrity Checks**: Verify sequence of IDs in dataset
2. **File System Validation**: Check consecutive file block allocations
3. **Inventory Management**: Verify sequential SKU numbers
4. **Transaction Audit**: Find missing transaction IDs
5. **Data Quality**: Detect missing or duplicate records
6. **Memory Allocation**: Track allocated memory blocks
7. **Duplicate Detection**: Find repeated entries in datasets
8. **Error Correction**: Identify corrupted data values

## Key Takeaways

- **Natural mapping**: Value-to-index relationship is crucial
- **In-place advantage**: Saves memory compared to hash-based approaches
- **Single pass after sort**: Most problems need only one more iteration
- **Specialized use**: Only for problems with value range [1, n]
- **No comparisons**: Uses positioning instead of comparison-based sorting
- **Optimal for interviews**: Demonstrates understanding of clever problem patterns
