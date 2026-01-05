# Counting Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [How Counting Sort Works](#how-counting-sort-works)
4. [Implementation](#implementation)
5. [Complexity Analysis](#complexity-analysis)
6. [Advantages and Disadvantages](#advantages-and-disadvantages)
7. [When to Use](#when-to-use)
8. [Practical Examples](#practical-examples)
9. [Variants and Extensions](#variants-and-extensions)

## Introduction

Counting Sort is a **non-comparative** integer sorting algorithm that operates by counting the number of objects with distinct key values. Unlike comparison-based algorithms (Quick Sort, Merge Sort, etc.), Counting Sort doesn't compare elements directly. Instead, it uses the actual values of elements as indices into an auxiliary counting array.

First published by Harold H. Seward in 1954, Counting Sort achieves **linear time complexity O(n + k)** where n is the number of elements and k is the range of input values. This makes it significantly faster than O(n log n) comparison-based algorithms when the range of input values is not significantly greater than the number of elements.

## Algorithm Explanation

### Core Concept

Counting Sort works in three phases:

1. **Count Phase:** Count the frequency of each distinct element
2. **Accumulate Phase:** Calculate cumulative positions for each element
3. **Place Phase:** Place elements in sorted order using the position information

The key insight is that if we know how many elements are less than a given value, we know its exact position in the sorted array.

### Step-by-Step Process

1. **Find Range:** Determine minimum and maximum values in the array
2. **Create Count Array:** Array of size (max - min + 1) initialized to 0
3. **Count Frequencies:** Increment count[arr[i] - min] for each element
4. **Calculate Positions:** Convert counts to cumulative sums (positions)
5. **Build Output:** Place each element at its calculated position
6. **Copy Back:** Transfer sorted output back to original array

### Key Characteristics

- **Non-comparative:** Doesn't compare elements
- **Stable:** Maintains relative order of equal elements
- **Not in-place:** Requires auxiliary arrays
- **Integer-based:** Works with integer keys or elements convertible to integers
- **Linear time:** O(n + k) where k is range

## How Counting Sort Works

### Visual Example

Sorting array `[2, 5, 3, 0, 2, 3, 0, 3]` with range 0-5:

**Step 1: Count Frequencies**
```
Array: [2, 5, 3, 0, 2, 3, 0, 3]

Count array (index = value, value = frequency):
Index:  0  1  2  3  4  5
Count: [2, 0, 2, 3, 0, 1]

Meaning:
0 appears 2 times
1 appears 0 times
2 appears 2 times
3 appears 3 times
4 appears 0 times
5 appears 1 time
```

**Step 2: Calculate Cumulative Positions**
```
Convert counts to positions:
Index:  0  1  2  3  4  5
Count: [2, 0, 2, 3, 0, 1]
        ↓  ↓  ↓  ↓  ↓  ↓
Cumul: [0, 2, 2, 4, 7, 7]

Then add 1 to each for 1-indexed positions:
Cumul: [2, 2, 4, 7, 7, 8]

Meaning:
Elements with value 0 occupy positions [0, 2)
Elements with value 1 occupy positions [2, 2)
Elements with value 2 occupy positions [2, 4)
Elements with value 3 occupy positions [4, 7)
etc.
```

**Step 3: Place Elements**
```
Process array right-to-left for stability:

Original: [2, 5, 3, 0, 2, 3, 0, 3]
                              ↑
Process 3: position = count[3] - 1 = 6
Output[6] = 3, count[3]--

Continue for all elements...

Final Output: [0, 0, 2, 2, 3, 3, 3, 5]
```

### Why Cumulative Counts?

Cumulative counts tell us the **starting position** of each value in the sorted output:
- All 0s start at position 0
- All 1s start at position 2
- All 2s start at position 2
- All 3s start at position 4
- etc.

## Implementation

```java
public class CountingSort {
    
    /**
     * Basic counting sort for non-negative integers
     * Time: O(n + k), Space: O(n + k)
     * where k = max value in array
     */
    public static void countingSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find maximum value
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        
        // Create count array
        int[] count = new int[max + 1];
        
        // Count frequencies
        for (int num : arr) {
            count[num]++;
        }
        
        // Reconstruct sorted array
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
            }
        }
    }
    
    /**
     * Stable counting sort with output array
     * Maintains relative order of equal elements
     */
    public static int[] countingSortStable(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }
        
        // Find min and max
        int min = arr[0], max = arr[0];
        for (int num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[arr.length];
        
        // Count frequencies
        for (int num : arr) {
            count[num - min]++;
        }
        
        // Calculate cumulative positions
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array (iterate backwards for stability)
        for (int i = arr.length - 1; i >= 0; i--) {
            int num = arr[i];
            int pos = count[num - min] - 1;
            output[pos] = num;
            count[num - min]--;
        }
        
        return output;
    }
    
    /**
     * Counting sort for array with negative numbers
     * Handles both positive and negative integers
     */
    public static void countingSortWithNegatives(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find min and max
        int min = arr[0], max = arr[0];
        for (int num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[arr.length];
        
        // Count frequencies (offset by min)
        for (int num : arr) {
            count[num - min]++;
        }
        
        // Calculate cumulative positions
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        for (int i = arr.length - 1; i >= 0; i--) {
            int num = arr[i];
            output[count[num - min] - 1] = num;
            count[num - min]--;
        }
        
        // Copy back to original array
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
    
    /**
     * Counting sort for specific range
     * More memory efficient when range is known
     */
    public static void countingSortInRange(int[] arr, int minVal, int maxVal) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int range = maxVal - minVal + 1;
        int[] count = new int[range];
        
        // Count frequencies
        for (int num : arr) {
            if (num < minVal || num > maxVal) {
                throw new IllegalArgumentException("Value out of range: " + num);
            }
            count[num - minVal]++;
        }
        
        // Reconstruct array
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (count[i] > 0) {
                arr[index++] = i + minVal;
                count[i]--;
            }
        }
    }
    
    /**
     * Counting sort for objects with integer keys
     * Sorts objects based on their integer key
     */
    static class Person {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
    
    public static void countingSortObjects(Person[] people) {
        if (people == null || people.length <= 1) {
            return;
        }
        
        // Find age range
        int minAge = people[0].age, maxAge = people[0].age;
        for (Person p : people) {
            if (p.age < minAge) minAge = p.age;
            if (p.age > maxAge) maxAge = p.age;
        }
        
        int range = maxAge - minAge + 1;
        int[] count = new int[range];
        Person[] output = new Person[people.length];
        
        // Count frequencies
        for (Person p : people) {
            count[p.age - minAge]++;
        }
        
        // Calculate cumulative positions
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output (backwards for stability)
        for (int i = people.length - 1; i >= 0; i--) {
            Person p = people[i];
            int pos = count[p.age - minAge] - 1;
            output[pos] = p;
            count[p.age - minAge]--;
        }
        
        // Copy back
        System.arraycopy(output, 0, people, 0, people.length);
    }
    
    /**
     * Counting sort for descending order
     */
    public static void countingSortDescending(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        
        int[] count = new int[max + 1];
        
        // Count frequencies
        for (int num : arr) {
            count[num]++;
        }
        
        // Reconstruct in descending order
        int index = 0;
        for (int i = count.length - 1; i >= 0; i--) {
            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
            }
        }
    }
    
    /**
     * Counting sort with digit extraction (for Radix Sort)
     * Sorts based on a specific digit position
     */
    public static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // 0-9 digits
        
        // Count occurrences of digits
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
        
        // Calculate cumulative count
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        // Copy back
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
     * Utility method to print Person array
     */
    public static void printPeople(Person[] people) {
        for (Person p : people) {
            System.out.print(p + " ");
        }
        System.out.println();
    }
    
    /**
     * Demonstrate counting sort step by step
     */
    public static void countingSortWithSteps(int[] arr) {
        System.out.println("Initial array:");
        printArray(arr);
        
        // Find max
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        System.out.println("Maximum value: " + max);
        
        // Create count array
        int[] count = new int[max + 1];
        System.out.println("\nStep 1: Counting frequencies");
        for (int num : arr) {
            count[num]++;
        }
        System.out.print("Count array: ");
        printArray(count);
        
        System.out.println("\nStep 2: Reconstructing sorted array");
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                System.out.println("  Adding " + i + " at position " + index);
                arr[index++] = i;
                count[i]--;
            }
        }
        
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    /**
     * Demonstrate stable counting sort
     */
    public static void demonstrateStability() {
        System.out.println("=== DEMONSTRATING STABILITY ===");
        
        // Array with duplicate values marked
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        System.out.print("Original (with implicit order): ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "[" + i + "] ");
        }
        System.out.println();
        
        int[] sorted = countingSortStable(arr);
        System.out.print("Stable sorted: ");
        printArray(sorted);
        System.out.println("(Original relative order of equal elements maintained)");
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== COUNTING SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic counting sort
        System.out.println("1. Basic Counting Sort:");
        int[] arr1 = {4, 2, 2, 8, 3, 3, 1};
        System.out.print("Original: ");
        printArray(arr1);
        countingSort(arr1);
        System.out.print("Sorted: ");
        printArray(arr1);
        
        // Example 2: Stable counting sort
        System.out.println("\n2. Stable Counting Sort:");
        int[] arr2 = {2, 5, 3, 0, 2, 3, 0, 3};
        System.out.print("Original: ");
        printArray(arr2);
        int[] sorted = countingSortStable(arr2);
        System.out.print("Sorted: ");
        printArray(sorted);
        
        // Example 3: With negative numbers
        System.out.println("\n3. Counting Sort with Negative Numbers:");
        int[] arr3 = {-5, -10, 0, -3, 8, 5, -1, 10};
        System.out.print("Original: ");
        printArray(arr3);
        countingSortWithNegatives(arr3);
        System.out.print("Sorted: ");
        printArray(arr3);
        
        // Example 4: Specific range
        System.out.println("\n4. Counting Sort in Specific Range (0-10):");
        int[] arr4 = {3, 7, 1, 9, 2, 8, 4, 5, 6, 0};
        System.out.print("Original: ");
        printArray(arr4);
        countingSortInRange(arr4, 0, 10);
        System.out.print("Sorted: ");
        printArray(arr4);
        
        // Example 5: Descending order
        System.out.println("\n5. Counting Sort - Descending Order:");
        int[] arr5 = {4, 2, 2, 8, 3, 3, 1};
        System.out.print("Original: ");
        printArray(arr5);
        countingSortDescending(arr5);
        System.out.print("Sorted (desc): ");
        printArray(arr5);
        
        // Example 6: Sorting objects by integer key
        System.out.println("\n6. Sorting Objects by Age:");
        Person[] people = {
            new Person("Alice", 25),
            new Person("Bob", 22),
            new Person("Charlie", 25),
            new Person("David", 22),
            new Person("Eve", 30)
        };
        System.out.print("Original: ");
        printPeople(people);
        countingSortObjects(people);
        System.out.print("Sorted by age: ");
        printPeople(people);
        System.out.println("(Stable: Alice before Charlie, Bob before David)");
        
        // Example 7: Already sorted array
        System.out.println("\n7. Already Sorted Array:");
        int[] arr6 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("Original: ");
        printArray(arr6);
        countingSort(arr6);
        System.out.print("Sorted: ");
        printArray(arr6);
        System.out.println("(Still O(n + k))");
        
        // Example 8: Array with all same elements
        System.out.println("\n8. All Same Elements:");
        int[] arr7 = {5, 5, 5, 5, 5};
        System.out.print("Original: ");
        printArray(arr7);
        countingSort(arr7);
        System.out.print("Sorted: ");
        printArray(arr7);
        
        // Example 9: Large range demonstration
        System.out.println("\n9. Array with Large Range:");
        int[] arr8 = {100, 50, 25, 75, 10, 90};
        System.out.print("Original: ");
        printArray(arr8);
        System.out.println("Range: 0-100 (count array size = 101)");
        countingSort(arr8);
        System.out.print("Sorted: ");
        printArray(arr8);
        
        // Example 10: Step-by-step demonstration
        System.out.println("\n10. Step-by-Step Counting Sort:");
        int[] arr9 = {4, 2, 2, 8, 3, 3, 1};
        countingSortWithSteps(arr9);
        
        // Example 11: Stability demonstration
        System.out.println("\n11. Stability Demonstration:");
        demonstrateStability();
        
        // Example 12: Performance test
        System.out.println("\n12. Performance Test (10000 elements, range 0-100):");
        int[] large = new int[10000];
        for (int i = 0; i < 10000; i++) {
            large[i] = (int) (Math.random() * 101); // 0-100
        }
        long startTime = System.nanoTime();
        countingSort(large);
        long endTime = System.nanoTime();
        System.out.println("Sorted 10000 elements in " +
                         (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("First 10: " + java.util.Arrays.toString(
                         java.util.Arrays.copyOf(large, 10)));
        
        // Example 13: Comparison with Quick Sort
        System.out.println("\n13. Performance Comparison (n=10000, k=100):");
        int[] test1 = new int[10000];
        int[] test2 = new int[10000];
        for (int i = 0; i < 10000; i++) {
            int val = (int) (Math.random() * 101);
            test1[i] = val;
            test2[i] = val;
        }
        
        startTime = System.nanoTime();
        countingSort(test1);
        endTime = System.nanoTime();
        System.out.println("Counting Sort: " + (endTime - startTime) / 1000000.0 + " ms");
        
        startTime = System.nanoTime();
        java.util.Arrays.sort(test2); // Uses Quick Sort variant
        endTime = System.nanoTime();
        System.out.println("Arrays.sort: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("(Counting sort faster for small range!)");
        
        // Example 14: Edge cases
        System.out.println("\n14. Edge Cases:");
        int[] arr10 = {42};
        System.out.print("Single element: ");
        printArray(arr10);
        countingSort(arr10);
        System.out.print("Sorted: ");
        printArray(arr10);
        
        int[] arr11 = {7, 3};
        System.out.print("Two elements: ");
        printArray(arr11);
        countingSort(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        // Example 15: Use case - Grade distribution
        System.out.println("\n15. Real-World Example - Sorting Exam Scores (0-100):");
        int[] scores = {85, 92, 78, 95, 88, 76, 89, 94, 82, 91, 87, 93};
        System.out.print("Scores: ");
        printArray(scores);
        countingSort(scores);
        System.out.print("Sorted scores: ");
        printArray(scores);
        System.out.println("Perfect use case: small known range, large dataset");
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n + k) | Linear in array size + range |
| **Average Case** | O(n + k) | Same as best case |
| **Worst Case** | O(n + k) | Same for all inputs |

**Detailed Analysis:**
- **n:** Number of elements in input array
- **k:** Range of input values (max - min + 1)
- **Counting phase:** O(n) - iterate through all elements
- **Cumulative phase:** O(k) - iterate through count array
- **Placing phase:** O(n) - place all elements
- **Total:** O(n + k)

**When is Counting Sort efficient?**
- When k = O(n), time complexity becomes O(n)
- Example: Sorting 1 million numbers in range 0-1000

### Space Complexity

- **Count Array:** O(k) - size of range
- **Output Array:** O(n) - size of input (for stable version)
- **Total:** O(n + k) auxiliary space
- **Not in-place:** Requires additional memory

### Stability

Counting Sort **IS STABLE** when implemented with the cumulative count method and processing elements from right to left. This preserves the relative order of equal elements.

## Advantages and Disadvantages

### Advantages

1. **Linear time complexity** - O(n + k), faster than O(n log n)
2. **Stable sorting** - Maintains relative order
3. **Simple to implement** - Straightforward logic
4. **Predictable performance** - No worst-case degradation
5. **Excellent for small ranges** - Much faster than comparison sorts
6. **Foundation for Radix Sort** - Used as subroutine

### Disadvantages

1. **Range-dependent** - Inefficient when k >> n
2. **Not in-place** - Requires O(n + k) extra space
3. **Integer keys only** - Can't sort arbitrary objects directly
4. **Memory intensive** - Large ranges require large count arrays
5. **Not general-purpose** - Limited to specific scenarios

## When to Use

### Ideal Use Cases

1. **Small known range** - When k = O(n)
   - Example: Sorting ages (0-150)
   - Example: Sorting grades (0-100)
   - Example: Sorting small integers

2. **Large datasets with small range** - n >> k
   - Example: 1 million numbers in range 0-1000

3. **Stability required** - When order matters for equal elements

4. **As subroutine** - In Radix Sort for digit sorting

5. **Fixed range** - When range is known in advance

### When NOT to Use

1. **Large range** - When k >> n
   - Example: Sorting 100 numbers in range 0-1,000,000

2. **Floating-point numbers** - Requires modification

3. **Memory-constrained** - When O(n + k) space not available

4. **Comparison needed** - When sorting by complex criteria

### Used in Production

- **Radix Sort:** Foundation for multi-digit sorting
- **Parallel computing:** Easy to parallelize
- **Database systems:** For sorting in known ranges
- **Computer graphics:** Color sorting, histogram generation

## Practical Examples

### Example 1: Sorting Student Grades

```java
// Perfect use case: limited range (0-100)
int[] grades = {85, 92, 78, 95, 88, 76, 89, 94};
countingSortInRange(grades, 0, 100);
// Much faster than O(n log n) algorithms
```

### Example 2: Age Distribution

```java
// Sorting people by age (0-150)
int[] ages = {25, 32, 18, 45, 22, 35, 28, 50};
countingSortInRange(ages, 0, 150);
// Linear time for large populations
```

### Example 3: Color Channel Sorting (0-255)

```java
// Sort RGB values
int[] redChannel = new int[1000000];
// Fill with values 0-255
countingSortInRange(redChannel, 0, 255);
// Extremely fast for image processing
```

## Variants and Extensions

### 1. Stable Counting Sort
Uses cumulative counts and reverse iteration to maintain stability.

### 2. Counting Sort for Characters
```java
// Sort lowercase letters
char[] chars = {'d', 'a', 'c', 'b', 'a'};
int[] count = new int[26]; // a-z
// Apply counting sort logic
```

### 3. Parallel Counting Sort
Count frequencies in parallel, then merge counts for faster execution on multi-core systems.

### 4. Counting Sort as Radix Sort Subroutine
Used to sort individual digits in Radix Sort algorithm.

## Summary

Counting Sort is a powerful non-comparative sorting algorithm that achieves linear time complexity when the range of input values is not significantly larger than the number of elements. Its simplicity and efficiency make it invaluable for specific use cases.

**Key Takeaways:**
- Linear O(n + k) time complexity
- Efficient when k = O(n)
- Stable and predictable
- Not in-place, requires O(n + k) space
- Works only with integer keys
- Foundation for Radix Sort
- Perfect for small, known ranges with large datasets

Counting Sort exemplifies how understanding your data characteristics (range, type, distribution) can lead to choosing algorithms that dramatically outperform general-purpose solutions. When applicable, it's one of the fastest sorting algorithms available.
