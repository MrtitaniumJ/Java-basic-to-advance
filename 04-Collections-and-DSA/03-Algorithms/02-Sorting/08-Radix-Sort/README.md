# Radix Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [LSD vs MSD Radix Sort](#lsd-vs-msd-radix-sort)
4. [How Radix Sort Works](#how-radix-sort-works)
5. [Implementation](#implementation)
6. [Complexity Analysis](#complexity-analysis)
7. [Advantages and Disadvantages](#advantages-and-disadvantages)
8. [When to Use](#when-to-use)
9. [Practical Examples](#practical-examples)

## Introduction

Radix Sort is a **non-comparative** integer sorting algorithm that sorts data by processing individual digits. Unlike comparison-based algorithms, Radix Sort distributes elements into buckets according to their digit values, working from the least significant digit (LSD) to the most significant digit (MSD), or vice versa.

Invented by Herman Hollerith in 1887 for use in tabulating machines, Radix Sort achieves **linear time complexity O(d(n + k))** where d is the number of digits, n is the number of elements, and k is the range of digit values (typically 10 for decimal). This makes it significantly faster than O(n log n) comparison-based algorithms for certain types of data.

## Algorithm Explanation

### Core Concept

Radix Sort processes numbers digit by digit, using a stable sorting algorithm (usually Counting Sort) as a subroutine:

1. **Start** from the least significant digit (LSD) or most significant digit (MSD)
2. **Sort** all numbers by that digit using a stable sort
3. **Move** to the next digit
4. **Repeat** until all digits are processed
5. **Result:** Array is fully sorted

The key insight is that by using a stable sort for each digit, the overall result is correctly sorted even though we process digits independently.

### Step-by-Step Process (LSD)

1. **Find Maximum:** Determine the number with maximum digits
2. **Count Digits:** Calculate how many digits to process (d)
3. **For each digit position** (from rightmost to leftmost):
   - Apply Counting Sort based on current digit
   - Maintain stability (crucial for correctness)
4. **Result:** After processing all digits, array is sorted

### Key Characteristics

- **Non-comparative:** Doesn't compare elements directly
- **Stable:** Maintains relative order (critical requirement)
- **Not in-place:** Requires auxiliary space
- **Digit-based:** Processes numbers digit by digit
- **Linear time:** O(d(n + k)) where d is typically small

## LSD vs MSD Radix Sort

### LSD (Least Significant Digit) Radix Sort

**Process:** Sort from rightmost digit to leftmost

**Characteristics:**
- Simpler to implement
- Always processes all digits
- More commonly used
- Naturally handles variable-length numbers

**Example:** For numbers 170, 45, 75, 90, 2, 802, 24, 66
```
Sort by ones place → tens place → hundreds place
```

### MSD (Most Significant Digit) Radix Sort

**Process:** Sort from leftmost digit to rightmost

**Characteristics:**
- More complex implementation
- Can terminate early for some branches
- Requires recursion
- Better for variable-length strings

**Example:** For numbers 170, 45, 75, 90, 2, 802, 24, 66
```
Sort by hundreds → tens → ones (recursively for each group)
```

## How Radix Sort Works

### Visual Example (LSD Radix Sort)

Sorting `[170, 45, 75, 90, 2, 802, 24, 66]`:

**Initial Array:**
```
[170, 45, 75, 90, 2, 802, 24, 66]
```

**Pass 1: Sort by ones place (1s digit)**
```
Numbers by ones digit:
0: 170, 90
2: 802, 2
4: 24
5: 45, 75
6: 66

After sorting by ones:
[170, 90, 802, 2, 24, 45, 75, 66]
```

**Pass 2: Sort by tens place (10s digit)**
```
Numbers by tens digit:
0: 802, 2
2: 24
4: 45
6: 66
7: 170, 75
9: 90

After sorting by tens:
[802, 2, 24, 45, 66, 170, 75, 90]
```

**Pass 3: Sort by hundreds place (100s digit)**
```
Numbers by hundreds digit:
0: 2, 24, 45, 66, 75, 90
1: 170
8: 802

After sorting by hundreds:
[2, 24, 45, 66, 75, 90, 170, 802]
```

**Final Sorted Array:** `[2, 24, 45, 66, 75, 90, 170, 802]`

### Why Stability Matters

Consider sorting `[170, 045, 075, 090]` where we track original positions:

If sorting by ones digit is **NOT STABLE**:
- 0 appears in 170 and 090
- Order might become: 090, 170 (wrong!)

With **STABLE** sort:
- Original order preserved for equal digits
- Ensures correct final sorting

## Implementation

```java
public class RadixSort {
    
    /**
     * LSD (Least Significant Digit) Radix Sort
     * Time: O(d(n + k)), Space: O(n + k)
     * where d = number of digits, k = radix (usually 10)
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find the maximum number to know number of digits
        int max = getMax(arr);
        
        // Do counting sort for every digit
        // exp is 10^i where i is current digit position
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    /**
     * Get maximum value in array
     */
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
    
    /**
     * Counting sort based on digit position (exp)
     * This is a stable sort crucial for radix sort correctness
     */
    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // 0-9 digits
        
        // Store count of occurrences
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
        
        // Change count[i] to actual position
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array (go backwards for stability)
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        // Copy output to arr
        System.arraycopy(output, 0, arr, 0, n);
    }
    
    /**
     * Radix sort with different radix (base)
     * Default radix is 10, but can use 2, 8, 16, etc.
     */
    public static void radixSortWithRadix(int[] arr, int radix) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int max = getMax(arr);
        
        for (int exp = 1; max / exp > 0; exp *= radix) {
            countingSortByDigitRadix(arr, exp, radix);
        }
    }
    
    private static void countingSortByDigitRadix(int[] arr, int exp, int radix) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[radix];
        
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % radix;
            count[digit]++;
        }
        
        for (int i = 1; i < radix; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % radix;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        System.arraycopy(output, 0, arr, 0, n);
    }
    
    /**
     * Radix sort for negative numbers
     * Separates positives and negatives, sorts separately
     */
    public static void radixSortWithNegatives(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Separate positive and negative numbers
        int negCount = 0;
        for (int num : arr) {
            if (num < 0) negCount++;
        }
        
        int[] negatives = new int[negCount];
        int[] positives = new int[arr.length - negCount];
        int negIndex = 0, posIndex = 0;
        
        for (int num : arr) {
            if (num < 0) {
                negatives[negIndex++] = -num; // Make positive for sorting
            } else {
                positives[posIndex++] = num;
            }
        }
        
        // Sort both arrays
        if (negatives.length > 0) {
            radixSort(negatives);
        }
        if (positives.length > 0) {
            radixSort(positives);
        }
        
        // Combine: negatives in reverse (make negative again), then positives
        int index = 0;
        for (int i = negatives.length - 1; i >= 0; i--) {
            arr[index++] = -negatives[i];
        }
        for (int i = 0; i < positives.length; i++) {
            arr[index++] = positives[i];
        }
    }
    
    /**
     * MSD (Most Significant Digit) Radix Sort
     * Sorts from left to right, uses recursion
     */
    public static void radixSortMSD(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int max = getMax(arr);
        int maxDigits = (int) Math.log10(max) + 1;
        int exp = (int) Math.pow(10, maxDigits - 1);
        
        radixSortMSDHelper(arr, 0, arr.length - 1, exp);
    }
    
    private static void radixSortMSDHelper(int[] arr, int low, int high, int exp) {
        if (low >= high || exp < 1) {
            return;
        }
        
        // Count sort by current digit
        int[] count = new int[10];
        int[] output = new int[high - low + 1];
        
        for (int i = low; i <= high; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = high; i >= low; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        System.arraycopy(output, 0, arr, low, high - low + 1);
        
        // Recursively sort each bucket
        int[] boundaries = new int[11];
        for (int i = 0; i < 10; i++) {
            boundaries[i] = low + (i == 0 ? 0 : count[i - 1]);
        }
        boundaries[10] = high + 1;
        
        for (int i = 0; i < 10; i++) {
            if (boundaries[i + 1] - boundaries[i] > 1) {
                radixSortMSDHelper(arr, boundaries[i], boundaries[i + 1] - 1, exp / 10);
            }
        }
    }
    
    /**
     * Radix sort for strings
     * Sorts strings lexicographically
     */
    public static void radixSortStrings(String[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find maximum length
        int maxLen = 0;
        for (String s : arr) {
            maxLen = Math.max(maxLen, s.length());
        }
        
        // Sort from rightmost character to leftmost
        for (int pos = maxLen - 1; pos >= 0; pos--) {
            countingSortByChar(arr, pos);
        }
    }
    
    private static void countingSortByChar(String[] arr, int pos) {
        int n = arr.length;
        String[] output = new String[n];
        int[] count = new int[257]; // 256 ASCII + 1 for shorter strings
        
        // Count characters (treat shorter strings as having '\0')
        for (int i = 0; i < n; i++) {
            int ch = (pos < arr[i].length()) ? arr[i].charAt(pos) + 1 : 0;
            count[ch]++;
        }
        
        // Cumulative count
        for (int i = 1; i < 257; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output
        for (int i = n - 1; i >= 0; i--) {
            int ch = (pos < arr[i].length()) ? arr[i].charAt(pos) + 1 : 0;
            output[count[ch] - 1] = arr[i];
            count[ch]--;
        }
        
        System.arraycopy(output, 0, arr, 0, n);
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
     * Utility method to print string array
     */
    public static void printStringArray(String[] arr) {
        for (String s : arr) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
    
    /**
     * Demonstrate radix sort step by step
     */
    public static void radixSortWithSteps(int[] arr) {
        System.out.println("Initial array:");
        printArray(arr);
        
        int max = getMax(arr);
        System.out.println("Maximum value: " + max);
        
        int digitNum = 1;
        for (int exp = 1; max / exp > 0; exp *= 10, digitNum++) {
            System.out.println("\nPass " + digitNum + ": Sorting by digit at position " + exp);
            
            System.out.print("Digit values: ");
            for (int num : arr) {
                System.out.print((num / exp) % 10 + " ");
            }
            System.out.println();
            
            countingSortByDigit(arr, exp);
            
            System.out.print("After sorting: ");
            printArray(arr);
        }
        
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== RADIX SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic radix sort
        System.out.println("1. Basic Radix Sort:");
        int[] arr1 = {170, 45, 75, 90, 2, 802, 24, 66};
        System.out.print("Original: ");
        printArray(arr1);
        radixSort(arr1);
        System.out.print("Sorted: ");
        printArray(arr1);
        
        // Example 2: Already sorted array
        System.out.println("\n2. Already Sorted Array:");
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("Original: ");
        printArray(arr2);
        radixSort(arr2);
        System.out.print("Sorted: ");
        printArray(arr2);
        
        // Example 3: Reverse sorted array
        System.out.println("\n3. Reverse Sorted Array:");
        int[] arr3 = {900, 800, 700, 600, 500, 400, 300, 200, 100};
        System.out.print("Original: ");
        printArray(arr3);
        radixSort(arr3);
        System.out.print("Sorted: ");
        printArray(arr3);
        
        // Example 4: Array with duplicates
        System.out.println("\n4. Array with Duplicates:");
        int[] arr4 = {100, 200, 100, 300, 200, 100};
        System.out.print("Original: ");
        printArray(arr4);
        radixSort(arr4);
        System.out.print("Sorted: ");
        printArray(arr4);
        
        // Example 5: Single digit numbers
        System.out.println("\n5. Single Digit Numbers:");
        int[] arr5 = {5, 2, 8, 4, 9, 1, 7};
        System.out.print("Original: ");
        printArray(arr5);
        radixSort(arr5);
        System.out.print("Sorted: ");
        printArray(arr5);
        
        // Example 6: Different radix (base 2)
        System.out.println("\n6. Radix Sort with Base 2 (Binary):");
        int[] arr6 = {170, 45, 75, 90, 2, 24, 66};
        System.out.print("Original: ");
        printArray(arr6);
        radixSortWithRadix(arr6, 2);
        System.out.print("Sorted: ");
        printArray(arr6);
        
        // Example 7: Different radix (base 16)
        System.out.println("\n7. Radix Sort with Base 16 (Hexadecimal):");
        int[] arr7 = {170, 45, 75, 90, 2, 24, 66};
        System.out.print("Original: ");
        printArray(arr7);
        radixSortWithRadix(arr7, 16);
        System.out.print("Sorted: ");
        printArray(arr7);
        
        // Example 8: With negative numbers
        System.out.println("\n8. Radix Sort with Negative Numbers:");
        int[] arr8 = {-170, 45, -75, 90, -2, 802, 24, -66};
        System.out.print("Original: ");
        printArray(arr8);
        radixSortWithNegatives(arr8);
        System.out.print("Sorted: ");
        printArray(arr8);
        
        // Example 9: MSD Radix Sort
        System.out.println("\n9. MSD (Most Significant Digit) Radix Sort:");
        int[] arr9 = {170, 45, 75, 90, 2, 802, 24, 66};
        System.out.print("Original: ");
        printArray(arr9);
        radixSortMSD(arr9);
        System.out.print("Sorted: ");
        printArray(arr9);
        
        // Example 10: Sorting strings
        System.out.println("\n10. Radix Sort for Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry", "fig"};
        System.out.print("Original: ");
        printStringArray(strArr);
        radixSortStrings(strArr);
        System.out.print("Sorted: ");
        printStringArray(strArr);
        
        // Example 11: Step-by-step demonstration
        System.out.println("\n11. Step-by-Step Radix Sort:");
        int[] arr10 = {170, 45, 75, 90, 802, 24, 2, 66};
        radixSortWithSteps(arr10);
        
        // Example 12: Large numbers
        System.out.println("\n12. Large Numbers:");
        int[] arr11 = {123456, 789012, 345678, 901234, 567890, 234567};
        System.out.print("Original: ");
        printArray(arr11);
        radixSort(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        // Example 13: Performance test
        System.out.println("\n13. Performance Test (10000 elements):");
        int[] large = new int[10000];
        for (int i = 0; i < 10000; i++) {
            large[i] = (int) (Math.random() * 1000000);
        }
        long startTime = System.nanoTime();
        radixSort(large);
        long endTime = System.nanoTime();
        System.out.println("Sorted 10000 elements in " +
                         (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("First 10: " + java.util.Arrays.toString(
                         java.util.Arrays.copyOf(large, 10)));
        
        // Example 14: Comparison with Quick Sort
        System.out.println("\n14. Performance Comparison (n=10000, d=6 digits):");
        int[] test1 = new int[10000];
        int[] test2 = new int[10000];
        for (int i = 0; i < 10000; i++) {
            int val = (int) (Math.random() * 1000000);
            test1[i] = val;
            test2[i] = val;
        }
        
        startTime = System.nanoTime();
        radixSort(test1);
        endTime = System.nanoTime();
        System.out.println("Radix Sort: " + (endTime - startTime) / 1000000.0 + " ms");
        
        startTime = System.nanoTime();
        java.util.Arrays.sort(test2);
        endTime = System.nanoTime();
        System.out.println("Arrays.sort: " + (endTime - startTime) / 1000000.0 + " ms");
        
        // Example 15: Edge cases
        System.out.println("\n15. Edge Cases:");
        int[] arr12 = {42};
        System.out.print("Single element: ");
        printArray(arr12);
        radixSort(arr12);
        System.out.print("Sorted: ");
        printArray(arr12);
        
        int[] arr13 = {7, 3};
        System.out.print("Two elements: ");
        printArray(arr13);
        radixSort(arr13);
        System.out.print("Sorted: ");
        printArray(arr13);
        
        int[] arr14 = {5, 5, 5, 5};
        System.out.print("All same: ");
        printArray(arr14);
        radixSort(arr14);
        System.out.print("Sorted: ");
        printArray(arr14);
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(d(n + k)) | d digits, n elements, k radix |
| **Average Case** | O(d(n + k)) | Same as best case |
| **Worst Case** | O(d(n + k)) | Same for all inputs |

**Detailed Analysis:**
- **d:** Number of digits in maximum number
- **n:** Number of elements
- **k:** Radix (base), typically 10 for decimal
- **Each pass:** O(n + k) for counting sort
- **Total passes:** d passes
- **Total:** d × O(n + k) = O(d(n + k))

**When is Radix Sort faster than comparison sorts?**
- When d is small (constant or O(log n))
- Example: Sorting 1 million 6-digit numbers
  - Radix Sort: O(6(n + 10)) ≈ O(n)
  - Quick Sort: O(n log n)

### Space Complexity

- **Auxiliary Arrays:** O(n + k) - Output and count arrays
- **Total:** O(n + k) space complexity
- **Not in-place:** Requires additional memory
- **Recursion (MSD):** O(d) for recursion stack

### Stability

Radix Sort **IS STABLE** - this is **CRITICAL** for correctness. The stability of the digit-sorting subroutine ensures that previous sorting passes are preserved.

## Advantages and Disadvantages

### Advantages

1. **Linear time complexity** - O(d(n + k)), often faster than O(n log n)
2. **Stable sorting** - Maintains relative order
3. **Predictable performance** - No worst-case degradation
4. **Excellent for integers** - Natural fit for fixed-length integers
5. **Parallelizable** - Can process digits/buckets in parallel
6. **Good for large datasets** - When d is small

### Disadvantages

1. **Not general-purpose** - Limited to integers or convertible data
2. **Not in-place** - Requires O(n + k) extra space
3. **Depends on d** - Slow when digits are many
4. **Not adaptive** - No benefit from partial sorting
5. **Cache performance** - Less cache-friendly than some algorithms
6. **Fixed-length assumption** - Complex for variable-length data

## When to Use

### Ideal Use Cases

1. **Fixed-length integers** - When d is small (constant)
   - Example: 32-bit integers (d ≤ 10)
   - Example: IP addresses (4 bytes)

2. **Large datasets** - When n is very large
   - Example: Sorting millions of phone numbers

3. **Known small range** - When digit range is limited
   - Example: Decimal numbers (k = 10)

4. **Parallel processing** - Easy to parallelize by digit

5. **Stability required** - Natural stable sort

### When NOT to Use

1. **Variable-length data** - Complex to handle efficiently

2. **Large digit count** - When d approaches n
   - Example: Arbitrary-precision numbers

3. **Floating-point numbers** - Requires special handling

4. **Memory-constrained** - O(n + k) space not available

5. **Small datasets** - Overhead not worth it

### Used in Production

- **Suffix array construction:** For string processing
- **Network routing:** IP address sorting
- **Database systems:** For integer key sorting
- **Parallel computing:** Easily parallelizable

## Practical Examples

### Example 1: Sorting Phone Numbers

```java
// Perfect use case: 10-digit phone numbers
int[] phoneNumbers = {9876543210, 1234567890, 5555551234, ...};
radixSort(phoneNumbers); // d=10, very efficient
```

### Example 2: Sorting Dates (YYYYMMDD)

```java
// Sort dates stored as integers: 20240115
int[] dates = {20240115, 20230312, 20241225, ...};
radixSort(dates); // d=8 digits, linear time
```

### Example 3: Sorting IP Addresses

```java
// Convert IP to 32-bit integer
int[] ipAddresses = {
    ipToInt("192.168.1.1"),
    ipToInt("10.0.0.1"),
    ...
};
radixSort(ipAddresses); // d=10 for 32-bit, efficient
```

### Example 4: Sorting Student IDs

```java
// 6-digit student IDs
int[] studentIDs = {123456, 789012, 345678, ...};
radixSort(studentIDs); // d=6, much faster than Quick Sort
```

## Summary

Radix Sort is a powerful non-comparative sorting algorithm that achieves linear time complexity by processing numbers digit by digit. When applicable (fixed-length integers, small d), it significantly outperforms comparison-based algorithms.

**Key Takeaways:**
- Linear O(d(n + k)) time complexity
- Stable sorting (critical for correctness)
- Excellent for fixed-length integers
- Requires O(n + k) extra space
- Not general-purpose, specific to integer/string data
- Much faster than O(n log n) when d is small
- Foundation for specialized sorting in production systems

Radix Sort exemplifies how exploiting data structure (digits) can lead to algorithms that surpass the O(n log n) lower bound of comparison-based sorting. It remains one of the fastest sorting algorithms for its ideal use cases.
