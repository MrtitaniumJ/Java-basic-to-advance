# Bucket Sort Algorithm

## Table of Contents
1. [Introduction](#introduction)
2. [Algorithm Explanation](#algorithm-explanation)
3. [How Bucket Sort Works](#how-bucket-sort-works)
4. [Implementation](#implementation)
5. [Complexity Analysis](#complexity-analysis)
6. [Advantages and Disadvantages](#advantages-and-disadvantages)
7. [When to Use](#when-to-use)
8. [Practical Examples](#practical-examples)
9. [Variants and Applications](#variants-and-applications)

## Introduction

Bucket Sort (also known as Bin Sort) is a **distribution-based** sorting algorithm that works by distributing elements into a number of buckets, sorting individual buckets, and then concatenating the results. Unlike comparison-based algorithms, Bucket Sort is most effective when input is uniformly distributed across a range.

Developed as a generalization of Counting Sort, Bucket Sort achieves **linear time complexity O(n + k)** in the average case when data is uniformly distributed, where n is the number of elements and k is the number of buckets. This makes it significantly faster than O(n log n) comparison-based algorithms for certain data distributions.

## Algorithm Explanation

### Core Concept

Bucket Sort divides the input into multiple "buckets," where each bucket holds a subset of the input values:

1. **Create Buckets:** Initialize empty buckets to hold elements
2. **Distribute:** Place each element into its appropriate bucket based on a mapping function
3. **Sort Buckets:** Sort individual buckets using any sorting algorithm
4. **Concatenate:** Merge sorted buckets back into a single sorted array

The key insight is that if elements are uniformly distributed, each bucket will contain roughly the same number of elements, making individual bucket sorting very fast.

### Step-by-Step Process

1. **Determine Range:** Find minimum and maximum values
2. **Create Buckets:** Initialize k empty buckets
3. **Distribute Elements:** Map each element to a bucket
   - Bucket index = `floor((element - min) / bucketSize)`
4. **Sort Each Bucket:** Use insertion sort or another algorithm
5. **Concatenate:** Merge buckets in order
6. **Result:** Fully sorted array

### Key Characteristics

- **Distribution-based:** Distributes elements rather than comparing
- **Not stable** (in basic form): Can be made stable
- **Not in-place:** Requires auxiliary space for buckets
- **Uniform distribution assumption:** Performance depends on data distribution
- **Linear average case:** O(n + k) when well-distributed

## How Bucket Sort Works

### Visual Example

Sorting `[0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68]` (range 0-1):

**Step 1: Create Buckets**
```
Create 5 buckets for ranges:
Bucket 0: [0.0, 0.2)
Bucket 1: [0.2, 0.4)
Bucket 2: [0.4, 0.6)
Bucket 3: [0.6, 0.8)
Bucket 4: [0.8, 1.0)
```

**Step 2: Distribute Elements**
```
0.78 → Bucket 3
0.17 → Bucket 0
0.39 → Bucket 1
0.26 → Bucket 1
0.72 → Bucket 3
0.94 → Bucket 4
0.21 → Bucket 1
0.12 → Bucket 0
0.23 → Bucket 1
0.68 → Bucket 3

Distribution:
Bucket 0: [0.17, 0.12]
Bucket 1: [0.39, 0.26, 0.21, 0.23]
Bucket 2: []
Bucket 3: [0.78, 0.72, 0.68]
Bucket 4: [0.94]
```

**Step 3: Sort Each Bucket**
```
Bucket 0: [0.12, 0.17]
Bucket 1: [0.21, 0.23, 0.26, 0.39]
Bucket 2: []
Bucket 3: [0.68, 0.72, 0.78]
Bucket 4: [0.94]
```

**Step 4: Concatenate**
```
Final: [0.12, 0.17, 0.21, 0.23, 0.26, 0.39, 0.68, 0.72, 0.78, 0.94]
```

### Bucket Distribution

The bucket mapping function is crucial:
```java
bucketIndex = (int)((value - min) * numBuckets / (max - min + 1))
```

For uniform distribution, this spreads elements evenly across buckets.

## Implementation

```java
import java.util.*;

public class BucketSort {
    
    /**
     * Basic bucket sort for floating-point numbers in range [0, 1)
     * Time: O(n + k) average, O(n²) worst
     * Space: O(n + k)
     */
    public static void bucketSort(float[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        
        // Create n empty buckets
        @SuppressWarnings("unchecked")
        ArrayList<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Put array elements in different buckets
        for (float num : arr) {
            int bucketIndex = (int) (num * n);
            if (bucketIndex >= n) bucketIndex = n - 1; // Handle edge case
            buckets[bucketIndex].add(num);
        }
        
        // Sort individual buckets
        for (ArrayList<Float> bucket : buckets) {
            Collections.sort(bucket);
        }
        
        // Concatenate all buckets into arr
        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    /**
     * Bucket sort for integers with specified range
     * Works for any range [min, max]
     */
    public static void bucketSortIntegers(int[] arr, int numBuckets) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find minimum and maximum
        int min = arr[0], max = arr[0];
        for (int num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        // Create buckets
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] buckets = new ArrayList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements into buckets
        float range = (float)(max - min + 1) / numBuckets;
        for (int num : arr) {
            int bucketIndex = (int)((num - min) / range);
            if (bucketIndex >= numBuckets) bucketIndex = numBuckets - 1;
            buckets[bucketIndex].add(num);
        }
        
        // Sort each bucket
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }
        
        // Concatenate buckets
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    /**
     * Bucket sort with custom bucket size
     * Allows control over bucket count
     */
    public static void bucketSortWithSize(int[] arr, int bucketSize) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find min and max
        int min = arr[0], max = arr[0];
        for (int num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        // Calculate number of buckets needed
        int bucketCount = (max - min) / bucketSize + 1;
        
        // Create buckets
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements
        for (int num : arr) {
            int bucketIndex = (num - min) / bucketSize;
            buckets[bucketIndex].add(num);
        }
        
        // Sort and concatenate
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    /**
     * Bucket sort using insertion sort for individual buckets
     * More efficient for small buckets
     */
    public static void bucketSortWithInsertion(float[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        
        // Create buckets
        @SuppressWarnings("unchecked")
        ArrayList<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements
        for (float num : arr) {
            int bucketIndex = (int) (num * n);
            if (bucketIndex >= n) bucketIndex = n - 1;
            buckets[bucketIndex].add(num);
        }
        
        // Sort buckets using insertion sort
        for (ArrayList<Float> bucket : buckets) {
            insertionSort(bucket);
        }
        
        // Concatenate
        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    /**
     * Insertion sort for ArrayList
     */
    private static void insertionSort(ArrayList<Float> list) {
        for (int i = 1; i < list.size(); i++) {
            float key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
    
    /**
     * Stable bucket sort
     * Maintains relative order of equal elements
     */
    public static void bucketSortStable(float[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        
        // Create buckets (LinkedList maintains insertion order)
        @SuppressWarnings("unchecked")
        LinkedList<Float>[] buckets = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new LinkedList<>();
        }
        
        // Distribute elements
        for (float num : arr) {
            int bucketIndex = (int) (num * n);
            if (bucketIndex >= n) bucketIndex = n - 1;
            buckets[bucketIndex].add(num);
        }
        
        // Sort buckets stably (insertion sort is stable)
        for (LinkedList<Float> bucket : buckets) {
            insertionSortLinkedList(bucket);
        }
        
        // Concatenate
        int index = 0;
        for (LinkedList<Float> bucket : buckets) {
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    private static void insertionSortLinkedList(LinkedList<Float> list) {
        if (list.size() <= 1) return;
        
        Float[] temp = list.toArray(new Float[0]);
        for (int i = 1; i < temp.length; i++) {
            float key = temp[i];
            int j = i - 1;
            while (j >= 0 && temp[j] > key) {
                temp[j + 1] = temp[j];
                j--;
            }
            temp[j + 1] = key;
        }
        
        list.clear();
        for (Float f : temp) {
            list.add(f);
        }
    }
    
    /**
     * Bucket sort for negative numbers
     */
    public static void bucketSortWithNegatives(float[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Find min and max
        float min = arr[0], max = arr[0];
        for (float num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        int n = arr.length;
        float range = max - min;
        
        // Create buckets
        @SuppressWarnings("unchecked")
        ArrayList<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements
        for (float num : arr) {
            int bucketIndex = (int)((num - min) / range * (n - 1));
            buckets[bucketIndex].add(num);
        }
        
        // Sort and concatenate
        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            Collections.sort(bucket);
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    /**
     * Generic bucket sort for any Comparable type
     */
    public static <T extends Comparable<T>> void genericBucketSort(
            T[] arr, int numBuckets, BucketMapper<T> mapper) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Create buckets
        @SuppressWarnings("unchecked")
        ArrayList<T>[] buckets = new ArrayList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements using custom mapper
        for (T element : arr) {
            int bucketIndex = mapper.getBucketIndex(element, numBuckets);
            buckets[bucketIndex].add(element);
        }
        
        // Sort buckets
        for (ArrayList<T> bucket : buckets) {
            Collections.sort(bucket);
        }
        
        // Concatenate
        int index = 0;
        for (ArrayList<T> bucket : buckets) {
            for (T element : bucket) {
                arr[index++] = element;
            }
        }
    }
    
    /**
     * Interface for custom bucket mapping
     */
    public interface BucketMapper<T> {
        int getBucketIndex(T element, int numBuckets);
    }
    
    /**
     * Utility method to print float array
     */
    public static void printArray(float[] arr) {
        for (float value : arr) {
            System.out.printf("%.2f ", value);
        }
        System.out.println();
    }
    
    /**
     * Utility method to print int array
     */
    public static void printIntArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    /**
     * Utility method to print generic array
     */
    public static <T> void printGenericArray(T[] arr) {
        for (T value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    /**
     * Demonstrate bucket sort step by step
     */
    public static void bucketSortWithSteps(float[] arr) {
        System.out.println("Initial array:");
        printArray(arr);
        
        int n = arr.length;
        
        // Create buckets
        @SuppressWarnings("unchecked")
        ArrayList<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        System.out.println("\nStep 1: Distributing elements into buckets");
        // Distribute
        for (float num : arr) {
            int bucketIndex = (int) (num * n);
            if (bucketIndex >= n) bucketIndex = n - 1;
            buckets[bucketIndex].add(num);
            System.out.printf("  %.2f → Bucket %d\n", num, bucketIndex);
        }
        
        System.out.println("\nStep 2: Bucket contents before sorting");
        for (int i = 0; i < n; i++) {
            if (!buckets[i].isEmpty()) {
                System.out.print("  Bucket " + i + ": ");
                for (float num : buckets[i]) {
                    System.out.printf("%.2f ", num);
                }
                System.out.println();
            }
        }
        
        // Sort
        System.out.println("\nStep 3: Sorting individual buckets");
        for (int i = 0; i < n; i++) {
            if (!buckets[i].isEmpty()) {
                Collections.sort(buckets[i]);
                System.out.print("  Bucket " + i + " sorted: ");
                for (float num : buckets[i]) {
                    System.out.printf("%.2f ", num);
                }
                System.out.println();
            }
        }
        
        // Concatenate
        System.out.println("\nStep 4: Concatenating buckets");
        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
        
        System.out.println("\nFinal sorted array:");
        printArray(arr);
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== BUCKET SORT DEMONSTRATIONS ===\n");
        
        // Example 1: Basic bucket sort (0-1 range)
        System.out.println("1. Basic Bucket Sort (0-1 range):");
        float[] arr1 = {0.78f, 0.17f, 0.39f, 0.26f, 0.72f, 0.94f, 0.21f, 0.12f, 0.23f, 0.68f};
        System.out.print("Original: ");
        printArray(arr1);
        bucketSort(arr1);
        System.out.print("Sorted: ");
        printArray(arr1);
        
        // Example 2: Integer bucket sort
        System.out.println("\n2. Bucket Sort for Integers:");
        int[] arr2 = {42, 32, 23, 52, 46, 28, 19, 37, 41, 33};
        System.out.print("Original: ");
        printIntArray(arr2);
        bucketSortIntegers(arr2, 5); // 5 buckets
        System.out.print("Sorted: ");
        printIntArray(arr2);
        
        // Example 3: Uniform distribution
        System.out.println("\n3. Uniform Distribution (Best Case):");
        float[] arr3 = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f};
        System.out.print("Original: ");
        printArray(arr3);
        bucketSort(arr3);
        System.out.print("Sorted: ");
        printArray(arr3);
        System.out.println("(Each element goes to different bucket - O(n))");
        
        // Example 4: Non-uniform distribution
        System.out.println("\n4. Non-Uniform Distribution:");
        float[] arr4 = {0.1f, 0.11f, 0.12f, 0.13f, 0.8f, 0.81f, 0.82f, 0.83f};
        System.out.print("Original: ");
        printArray(arr4);
        bucketSort(arr4);
        System.out.print("Sorted: ");
        printArray(arr4);
        System.out.println("(Some buckets have multiple elements)");
        
        // Example 5: Custom bucket size
        System.out.println("\n5. Bucket Sort with Custom Bucket Size:");
        int[] arr5 = {1, 15, 8, 22, 17, 3, 29, 12, 5};
        System.out.print("Original: ");
        printIntArray(arr5);
        bucketSortWithSize(arr5, 5); // Bucket size = 5
        System.out.print("Sorted: ");
        printIntArray(arr5);
        
        // Example 6: With insertion sort
        System.out.println("\n6. Bucket Sort with Insertion Sort:");
        float[] arr6 = {0.42f, 0.32f, 0.23f, 0.52f, 0.46f, 0.28f};
        System.out.print("Original: ");
        printArray(arr6);
        bucketSortWithInsertion(arr6);
        System.out.print("Sorted: ");
        printArray(arr6);
        
        // Example 7: With negative numbers
        System.out.println("\n7. Bucket Sort with Negative Numbers:");
        float[] arr7 = {-0.5f, 0.3f, -0.2f, 0.7f, -0.8f, 0.1f};
        System.out.print("Original: ");
        printArray(arr7);
        bucketSortWithNegatives(arr7);
        System.out.print("Sorted: ");
        printArray(arr7);
        
        // Example 8: Stable bucket sort
        System.out.println("\n8. Stable Bucket Sort:");
        float[] arr8 = {0.5f, 0.2f, 0.5f, 0.3f, 0.2f, 0.8f};
        System.out.print("Original: ");
        printArray(arr8);
        bucketSortStable(arr8);
        System.out.print("Sorted (stable): ");
        printArray(arr8);
        
        // Example 9: Large range integers
        System.out.println("\n9. Large Range Integers:");
        int[] arr9 = {150, 50, 250, 100, 200, 75, 175, 125};
        System.out.print("Original: ");
        printIntArray(arr9);
        bucketSortIntegers(arr9, 4);
        System.out.print("Sorted: ");
        printIntArray(arr9);
        
        // Example 10: Generic bucket sort with strings
        System.out.println("\n10. Generic Bucket Sort with Strings:");
        String[] strArr = {"banana", "apple", "cherry", "date", "elderberry", "fig"};
        System.out.print("Original: ");
        printGenericArray(strArr);
        genericBucketSort(strArr, 5, (s, n) -> 
            (int)((s.charAt(0) - 'a') * n / 26.0));
        System.out.print("Sorted: ");
        printGenericArray(strArr);
        
        // Example 11: Step-by-step demonstration
        System.out.println("\n11. Step-by-Step Bucket Sort:");
        float[] arr10 = {0.78f, 0.17f, 0.39f, 0.26f, 0.72f};
        bucketSortWithSteps(arr10);
        
        // Example 12: Already sorted
        System.out.println("\n12. Already Sorted Array:");
        float[] arr11 = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f};
        System.out.print("Original: ");
        printArray(arr11);
        bucketSort(arr11);
        System.out.print("Sorted: ");
        printArray(arr11);
        
        // Example 13: Reverse sorted
        System.out.println("\n13. Reverse Sorted Array:");
        float[] arr12 = {0.9f, 0.8f, 0.7f, 0.6f, 0.5f};
        System.out.print("Original: ");
        printArray(arr12);
        bucketSort(arr12);
        System.out.print("Sorted: ");
        printArray(arr12);
        
        // Example 14: Performance test
        System.out.println("\n14. Performance Test (10000 elements):");
        float[] large = new float[10000];
        for (int i = 0; i < 10000; i++) {
            large[i] = (float) Math.random();
        }
        long startTime = System.nanoTime();
        bucketSort(large);
        long endTime = System.nanoTime();
        System.out.println("Sorted 10000 uniform random floats in " +
                         (endTime - startTime) / 1000000.0 + " ms");
        System.out.printf("First 10: ");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%.2f ", large[i]);
        }
        System.out.println();
        
        // Example 15: Edge cases
        System.out.println("\n15. Edge Cases:");
        float[] arr13 = {0.42f};
        System.out.print("Single element: ");
        printArray(arr13);
        bucketSort(arr13);
        System.out.print("Sorted: ");
        printArray(arr13);
        
        float[] arr14 = {0.7f, 0.3f};
        System.out.print("Two elements: ");
        printArray(arr14);
        bucketSort(arr14);
        System.out.print("Sorted: ");
        printArray(arr14);
        
        float[] arr15 = {0.5f, 0.5f, 0.5f};
        System.out.print("All same: ");
        printArray(arr15);
        bucketSort(arr15);
        System.out.print("Sorted: ");
        printArray(arr15);
    }
}
```

## Complexity Analysis

### Time Complexity

| Case | Complexity | Explanation |
|------|------------|-------------|
| **Best Case** | O(n + k) | Uniform distribution, each bucket has ≤ 1 element |
| **Average Case** | O(n + k) | Uniformly distributed elements |
| **Worst Case** | O(n²) | All elements in one bucket |

**Detailed Analysis:**
- **n:** Number of elements
- **k:** Number of buckets
- **Distribution:** O(n) - Place each element in bucket
- **Sorting buckets:** O(Σ(n_i²)) where n_i is size of bucket i
  - Average case: O(n) when uniform (each bucket has ~1 element)
  - Worst case: O(n²) when all in one bucket
- **Concatenation:** O(n) - Collect all elements
- **Total average:** O(n + k) when well-distributed

### Space Complexity

- **Buckets:** O(n + k) - Array of buckets plus elements
- **Total:** O(n + k) auxiliary space
- **Not in-place:** Requires additional memory

### Stability

Bucket Sort **CAN BE STABLE** when:
1. Individual bucket sorting algorithm is stable
2. Elements are added to buckets in original order
3. Buckets are concatenated in order

## Advantages and Disadvantages

### Advantages

1. **Linear average case** - O(n + k) when well-distributed
2. **Simple to understand** - Intuitive distribution concept
3. **Parallelizable** - Buckets can be sorted independently
4. **Flexible** - Works with various data types
5. **Can be stable** - With proper implementation
6. **Good for uniform data** - Excellent when data is evenly distributed

### Disadvantages

1. **Distribution-dependent** - Performance varies with data distribution
2. **Worst case O(n²)** - When all elements fall into one bucket
3. **Not in-place** - Requires O(n + k) extra space
4. **Bucket count selection** - Choosing optimal k can be difficult
5. **Not general-purpose** - Best for specific data patterns
6. **Extra overhead** - Bucket management adds complexity

## When to Use

### Ideal Use Cases

1. **Uniformly distributed data** - When elements are evenly spread
   - Example: Random floats in [0, 1)
   - Example: Hash values

2. **Known data distribution** - When distribution is predictable
   - Example: Grades (0-100)
   - Example: Ages (0-150)

3. **Floating-point numbers** - Natural fit for fractional values

4. **Parallel processing** - Easy to parallelize bucket sorting

5. **External sorting** - Can sort data larger than memory

### When NOT to Use

1. **Non-uniform distribution** - Skewed data causes poor performance
   - Example: Power-law distributions
   - Example: Highly clustered data

2. **Unknown distribution** - When data pattern is unpredictable

3. **Small datasets** - Overhead not worth it

4. **Memory-constrained** - O(n + k) space not available

5. **Comparison needed** - When complex sorting criteria

### Used in Production

- **Database systems:** For distributed sorting
- **Parallel computing:** Easy parallelization
- **Graphics applications:** Color sorting
- **Data analytics:** Histogram generation

## Practical Examples

### Example 1: Sorting Test Scores (0-100)

```java
// Perfect use case: known range, likely uniform distribution
int[] scores = {85, 92, 78, 95, 88, 76, 89};
bucketSortIntegers(scores, 10); // 10 buckets (0-9, 10-19, ..., 90-99)
// Much faster than comparison sorts
```

### Example 2: Sorting Random Floats

```java
// Ideal scenario: uniform distribution
float[] randomFloats = generateUniformRandom(10000);
bucketSort(randomFloats); // O(n) average case
```

### Example 3: Parallel Bucket Sort

```java
// Sort buckets in parallel
ExecutorService executor = Executors.newFixedThreadPool(numBuckets);
for (int i = 0; i < numBuckets; i++) {
    final int index = i;
    executor.submit(() -> Collections.sort(buckets[index]));
}
executor.shutdown();
// Much faster on multi-core systems
```

### Example 4: External Sorting

```java
// Sort file too large for memory
// 1. Read chunks, distribute to bucket files
// 2. Sort each bucket file
// 3. Concatenate sorted bucket files
```

## Variants and Applications

### 1. Histogram Sort
Used in image processing for color channel sorting.

### 2. Proxmap Sort
Variation that uses mathematical mapping to determine bucket assignments.

### 3. Flash Sort
Optimized bucket sort with better bucket distribution.

### 4. Interpolation Sort
Uses interpolation to predict bucket positions more accurately.

### 5. Parallel Bucket Sort
Distributes bucket sorting across multiple processors/cores.

## Summary

Bucket Sort is a distribution-based sorting algorithm that achieves linear average-case time complexity when data is uniformly distributed. While it has limitations (worst-case O(n²), space requirements, distribution dependence), it excels in scenarios with predictable, well-distributed data.

**Key Takeaways:**
- Linear O(n + k) average case for uniform distribution
- Worst case O(n²) when distribution is poor
- Not in-place, requires O(n + k) space
- Simple and intuitive concept
- Highly parallelizable
- Best for uniformly distributed floating-point data
- Performance highly dependent on data distribution

Bucket Sort exemplifies how understanding your data distribution can lead to dramatic performance improvements. When applicable (uniform distribution, known range), it can significantly outperform general-purpose O(n log n) algorithms, making it valuable for specific use cases in production systems.
