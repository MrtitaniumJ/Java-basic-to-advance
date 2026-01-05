# Top K Elements Pattern

## Table of Contents
- [Introduction](#introduction)
- [Pattern Recognition](#pattern-recognition)
- [Core Approaches](#core-approaches)
- [Heap-Based Solutions](#heap-based-solutions)
- [QuickSelect Algorithm](#quickselect-algorithm)
- [Sorting-Based Approach](#sorting-based-approach)
- [Common Problem Types](#common-problem-types)
- [Complete Java Implementation](#complete-java-implementation)
- [Complexity Analysis](#complexity-analysis)
- [Trade-offs and When to Use Each Approach](#trade-offs-and-when-to-use-each-approach)
- [Practice Problems](#practice-problems)

---

## Introduction

The **Top K Elements** pattern is one of the most frequently used techniques in algorithm design, particularly for problems that require finding, tracking, or maintaining the k largest, smallest, most frequent, or closest elements in a dataset. This pattern is essential for optimization problems where we don't need to sort the entire dataset but only need to identify a subset of k elements based on specific criteria.

The beauty of this pattern lies in its efficiency—instead of sorting an entire array of n elements (O(n log n)), we can often find the top k elements in O(n log k) or even O(n) time using appropriate data structures and algorithms. This optimization becomes crucial when dealing with large datasets or streaming data where full sorting is impractical.

### Real-World Applications

Top K Elements pattern appears in numerous real-world scenarios:
- **Search Engines**: Finding top k most relevant search results
- **E-commerce**: Displaying top k best-selling products
- **Social Media**: Showing top k trending topics or most liked posts
- **System Monitoring**: Identifying top k resource-consuming processes
- **Recommendation Systems**: Selecting top k recommendations for users
- **Data Analysis**: Finding top k frequent patterns in large datasets

---

## Pattern Recognition

You should consider the Top K Elements pattern when you encounter problems with these characteristics:

1. **Finding k largest/smallest elements** in an unsorted collection
2. **Finding the kth largest/smallest element** (order statistics)
3. **Finding k most/least frequent elements** in a dataset
4. **Finding k closest elements** to a target value or point
5. **Maintaining a dynamic collection** of top k elements as data streams in
6. **Merging k sorted lists** or arrays

**Key Indicators**:
- The problem explicitly mentions "top k", "k largest", "k smallest", "k most frequent"
- You need only a subset of elements, not the complete sorted order
- The dataset is large, and full sorting would be inefficient
- Elements are being added dynamically, and you need to track top k elements

---

## Core Approaches

There are three primary approaches to solving Top K Elements problems, each with distinct characteristics:

### 1. Heap-Based Approach (Priority Queue)
Uses a min-heap or max-heap to maintain k elements efficiently. This is the most common and versatile approach.

**When to Use**:
- Need to find k largest/smallest elements
- Working with streaming data
- When O(n log k) complexity is acceptable
- Memory constraint allows storing k elements

### 2. QuickSelect Algorithm
A selection algorithm derived from QuickSort that can find the kth element in average O(n) time.

**When to Use**:
- Finding the exact kth largest/smallest element
- When average O(n) time is critical
- All data is available upfront (not streaming)
- Worst-case O(n²) is acceptable with good pivot selection

### 3. Sorting-Based Approach
Simply sort the entire array and select k elements.

**When to Use**:
- Dataset is small (n is close to k)
- Need multiple queries on the same dataset
- Simplicity is more important than optimal performance
- Already have a sorted structure available

---

## Heap-Based Solutions

Heaps (Priority Queues) are the cornerstone of Top K Elements solutions. The key insight is choosing the right type of heap:

### Min-Heap for K Largest Elements
To find k **largest** elements, use a **min-heap** of size k:
- The smallest element in our k largest elements sits at the root
- If a new element is larger than the root, remove root and add the new element
- After processing all elements, the heap contains k largest elements

### Max-Heap for K Smallest Elements
To find k **smallest** elements, use a **max-heap** of size k:
- The largest element in our k smallest elements sits at the root
- If a new element is smaller than the root, remove root and add the new element
- After processing all elements, the heap contains k smallest elements

### Why This Works
The heap maintains exactly k elements at all times. The root of the heap represents the "boundary" element—any element worse than this boundary is rejected, and any element better replaces the boundary. This ensures we always track the top k elements without storing or sorting the entire dataset.

---

## QuickSelect Algorithm

QuickSelect is an elegant selection algorithm that finds the kth smallest (or largest) element in O(n) average time. It's based on the partitioning step of QuickSort but only recurses into one partition.

### Algorithm Steps:
1. **Choose a pivot** element from the array
2. **Partition** the array so elements less than pivot are on the left, greater on the right
3. **Check pivot position**:
   - If pivot is at position k, we found the kth element
   - If pivot position > k, recurse on the left partition
   - If pivot position < k, recurse on the right partition

### Key Characteristics:
- **Average Time**: O(n) with good pivot selection
- **Worst Case**: O(n²) with poor pivot selection (mitigated by random pivot or median-of-medians)
- **Space**: O(1) if done in-place, O(log n) for recursion stack
- **Modifies**: The original array (can be avoided with extra space)

QuickSelect is optimal when you need the exact kth element and all data is available upfront. However, it doesn't work well for streaming data or when you need all k elements in order.

---

## Sorting-Based Approach

The simplest approach is to sort the entire array and then select the first or last k elements. While not optimal for large datasets, it has its place:

### Advantages:
- **Simple to implement** and understand
- **Produces sorted output** which may be required
- **Efficient for small datasets** where n ≈ k
- **Enables multiple queries** efficiently after one-time sorting

### Disadvantages:
- **O(n log n) time** complexity regardless of k
- **Not suitable for streaming data**
- **Overkill when k << n** (sorting all elements when we need only k)

---

## Common Problem Types

### 1. K Largest/Smallest Elements
Find the k largest or smallest elements from an unsorted array.

**Strategy**: Use min-heap for k largest, max-heap for k smallest.

### 2. Kth Largest/Smallest Element
Find the exact kth largest or smallest element (not all k elements).

**Strategy**: QuickSelect for optimal O(n) average time, or heap approach in O(n log k).

### 3. K Most Frequent Elements
Find k elements that appear most frequently in the array.

**Strategy**: Count frequencies using HashMap, then use min-heap based on frequency.

### 4. K Closest Elements
Find k elements closest to a target value or k points closest to origin.

**Strategy**: Use max-heap with custom comparator based on distance/difference.

### 5. Top K Frequent Words
Similar to frequent elements but with lexicographic ordering as tiebreaker.

**Strategy**: HashMap for counting + custom comparator in PriorityQueue.

---

## Complete Java Implementation

Below is a comprehensive implementation covering all major Top K Elements problems with multiple approaches:

```java
import java.util.*;

/**
 * Top K Elements Pattern - Comprehensive Implementation
 * Demonstrates multiple approaches to solving Top K problems
 */
public class TopKElementsPatterns {
    
    // ==================== Approach 1: Heap-Based Solutions ====================
    
    /**
     * Find K Largest Elements using Min-Heap
     * Time: O(n log k), Space: O(k)
     * 
     * Strategy: Maintain a min-heap of size k. The smallest of k largest 
     * elements is at the root. Any element larger than root replaces it.
     */
    public static List<Integer> findKLargestHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new ArrayList<>();
        }
        
        // Min-heap to store k largest elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.offer(num);
            
            // If heap size exceeds k, remove the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Convert heap to list
        return new ArrayList<>(minHeap);
    }
    
    /**
     * Find K Smallest Elements using Max-Heap
     * Time: O(n log k), Space: O(k)
     * 
     * Strategy: Maintain a max-heap of size k. The largest of k smallest
     * elements is at the root. Any element smaller than root replaces it.
     */
    public static List<Integer> findKSmallestHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new ArrayList<>();
        }
        
        // Max-heap (reverse order) to store k smallest elements
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int num : nums) {
            maxHeap.offer(num);
            
            // If heap size exceeds k, remove the largest element
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        return new ArrayList<>(maxHeap);
    }
    
    /**
     * Find Kth Largest Element using Min-Heap
     * Time: O(n log k), Space: O(k)
     * 
     * Returns the kth largest element (single element, not all k elements)
     */
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Root of min-heap is the kth largest element
        return minHeap.peek();
    }
    
    // ==================== Approach 2: QuickSelect Algorithm ====================
    
    /**
     * Find Kth Largest Element using QuickSelect
     * Time: O(n) average, O(n²) worst case, Space: O(1)
     * 
     * QuickSelect is optimal for finding kth element but doesn't sort.
     * It partitions array and recurses only into relevant partition.
     */
    public static int findKthLargestQuickSelect(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // To find kth largest, we find (n-k)th smallest in 0-indexed array
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }
    
    private static int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
        
        // Randomly select pivot to avoid worst case
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        
        // Partition array and get final pivot position
        pivotIndex = partition(nums, left, right, pivotIndex);
        
        // Check if pivot is at kth position
        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            // Recurse on left partition
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            // Recurse on right partition
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }
    
    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        
        // Move pivot to end
        swap(nums, pivotIndex, right);
        
        int storeIndex = left;
        
        // Move all smaller elements to the left
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }
        
        // Move pivot to its final position
        swap(nums, storeIndex, right);
        
        return storeIndex;
    }
    
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // ==================== Approach 3: Sorting-Based ====================
    
    /**
     * Find K Largest Elements using Sorting
     * Time: O(n log n), Space: O(1) or O(n) depending on sort implementation
     * 
     * Simple but not optimal. Good for small datasets or when sorted output needed.
     */
    public static List<Integer> findKLargestSorting(int[] nums, int k) {
        Arrays.sort(nums);
        List<Integer> result = new ArrayList<>();
        
        // Get last k elements (largest)
        for (int i = nums.length - k; i < nums.length; i++) {
            result.add(nums[i]);
        }
        
        return result;
    }
    
    // ==================== Problem Type: K Most Frequent Elements ====================
    
    /**
     * Find K Most Frequent Elements
     * Time: O(n log k), Space: O(n)
     * 
     * Uses HashMap for frequency counting and min-heap to track top k.
     */
    public static List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Min-heap based on frequency
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Extract elements
        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().getKey());
        }
        
        return result;
    }
    
    /**
     * Alternative: Bucket Sort for K Most Frequent (O(n) time)
     * Optimal when k is close to n or need O(n) time
     */
    public static List<Integer> topKFrequentBucketSort(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Create buckets: index = frequency, value = list of numbers
        List<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int frequency = entry.getValue();
            buckets[frequency].add(entry.getKey());
        }
        
        // Collect top k from highest frequency buckets
        List<Integer> result = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            for (int num : buckets[i]) {
                result.add(num);
                if (result.size() == k) {
                    break;
                }
            }
        }
        
        return result;
    }
    
    // ==================== Problem Type: K Closest Elements ====================
    
    /**
     * Find K Closest Elements to Target
     * Time: O(n log k), Space: O(k)
     * 
     * Uses max-heap with custom comparator based on distance from target.
     */
    public static List<Integer> findKClosestElements(int[] nums, int k, int target) {
        // Max-heap based on distance from target
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
            (a, b) -> {
                int diffA = Math.abs(a - target);
                int diffB = Math.abs(b - target);
                if (diffA == diffB) {
                    return b - a; // If same distance, prioritize smaller value
                }
                return diffB - diffA; // Max-heap based on distance
            }
        );
        
        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        List<Integer> result = new ArrayList<>(maxHeap);
        Collections.sort(result); // Sort result for output
        return result;
    }
    
    /**
     * Find K Closest Elements (Optimized for sorted array)
     * Time: O(log n + k), Space: O(1)
     * 
     * Uses binary search to find starting position, then expand window.
     */
    public static List<Integer> findKClosestElementsSorted(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - k;
        
        // Binary search for the start of k-sized window
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare distances: arr[mid] and arr[mid + k]
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        
        return result;
    }
    
    // ==================== Problem Type: K Frequent Words ====================
    
    /**
     * Top K Frequent Words with Lexicographic Order
     * Time: O(n log k), Space: O(n)
     * 
     * Similar to frequent elements but with string comparison for ties.
     */
    public static List<String> topKFrequentWords(String[] words, int k) {
        // Count frequencies
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
        
        // Min-heap with custom comparator
        PriorityQueue<String> minHeap = new PriorityQueue<>(
            (w1, w2) -> {
                int freq1 = frequencyMap.get(w1);
                int freq2 = frequencyMap.get(w2);
                
                if (freq1 == freq2) {
                    // If same frequency, reverse lexicographic order (for min-heap)
                    return w2.compareTo(w1);
                }
                return freq1 - freq2; // Min-heap based on frequency
            }
        );
        
        for (String word : frequencyMap.keySet()) {
            minHeap.offer(word);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Extract words in reverse order and reverse the list
        List<String> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        Collections.reverse(result);
        
        return result;
    }
    
    // ==================== Advanced: Streaming Top K ====================
    
    /**
     * Class to maintain top K elements in a stream
     * Useful for real-time data processing
     */
    static class StreamTopK {
        private PriorityQueue<Integer> minHeap;
        private int k;
        
        public StreamTopK(int k) {
            this.k = k;
            this.minHeap = new PriorityQueue<>();
        }
        
        /**
         * Add element to stream and maintain top k
         * Time: O(log k)
         */
        public void add(int num) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        /**
         * Get current top k elements
         * Time: O(k log k) due to sorting
         */
        public List<Integer> getTopK() {
            List<Integer> result = new ArrayList<>(minHeap);
            Collections.sort(result, Collections.reverseOrder());
            return result;
        }
        
        /**
         * Get kth largest element
         * Time: O(1)
         */
        public int getKthLargest() {
            return minHeap.peek();
        }
    }
    
    // ==================== Demonstration and Testing ====================
    
    public static void main(String[] args) {
        System.out.println("=== Top K Elements Pattern Demonstrations ===\n");
        
        // Test 1: K Largest Elements
        System.out.println("1. Finding K Largest Elements:");
        int[] nums1 = {3, 1, 5, 12, 2, 11, 9, 6};
        int k1 = 3;
        System.out.println("Array: " + Arrays.toString(nums1));
        System.out.println("K = " + k1);
        System.out.println("Using Heap: " + findKLargestHeap(nums1, k1));
        System.out.println("Using Sorting: " + findKLargestSorting(nums1.clone(), k1));
        System.out.println();
        
        // Test 2: Kth Largest Element
        System.out.println("2. Finding Kth Largest Element:");
        int[] nums2 = {3, 2, 1, 5, 6, 4};
        int k2 = 2;
        System.out.println("Array: " + Arrays.toString(nums2));
        System.out.println("K = " + k2);
        System.out.println("Using Heap: " + findKthLargest(nums2.clone(), k2));
        System.out.println("Using QuickSelect: " + findKthLargestQuickSelect(nums2.clone(), k2));
        System.out.println();
        
        // Test 3: K Most Frequent Elements
        System.out.println("3. Finding K Most Frequent Elements:");
        int[] nums3 = {1, 1, 1, 2, 2, 3, 3, 3, 3, 4};
        int k3 = 2;
        System.out.println("Array: " + Arrays.toString(nums3));
        System.out.println("K = " + k3);
        System.out.println("Using Heap: " + topKFrequent(nums3, k3));
        System.out.println("Using Bucket Sort: " + topKFrequentBucketSort(nums3, k3));
        System.out.println();
        
        // Test 4: K Closest Elements
        System.out.println("4. Finding K Closest Elements:");
        int[] nums4 = {1, 3, 5, 7, 9, 11};
        int k4 = 3;
        int target = 6;
        System.out.println("Array: " + Arrays.toString(nums4));
        System.out.println("K = " + k4 + ", Target = " + target);
        System.out.println("Using Heap: " + findKClosestElements(nums4, k4, target));
        System.out.println("Optimized (sorted): " + findKClosestElementsSorted(nums4, k4, target));
        System.out.println();
        
        // Test 5: K Frequent Words
        System.out.println("5. Finding K Frequent Words:");
        String[] words = {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
        int k5 = 4;
        System.out.println("Words: " + Arrays.toString(words));
        System.out.println("K = " + k5);
        System.out.println("Result: " + topKFrequentWords(words, k5));
        System.out.println();
        
        // Test 6: Streaming Top K
        System.out.println("6. Streaming Top K Elements:");
        StreamTopK stream = new StreamTopK(3);
        int[] streamData = {4, 5, 8, 2, 3, 10, 9, 1};
        System.out.println("Stream K = 3");
        for (int num : streamData) {
            stream.add(num);
            System.out.println("Added " + num + " -> Top 3: " + stream.getTopK() + 
                             ", 3rd largest: " + stream.getKthLargest());
        }
    }
}
```

---

## Complexity Analysis

### Heap-Based Approach

**Time Complexity**:
- Building heap: O(n log k)
  - n elements, each insertion/deletion takes O(log k)
- Getting all k elements: O(k) or O(k log k) if sorted output needed

**Space Complexity**: O(k) for heap storage

**Best For**: Most Top K problems, especially with streaming data

---

### QuickSelect Algorithm

**Time Complexity**:
- Average case: O(n)
  - Each partition reduces search space by half on average
  - T(n) = T(n/2) + O(n) → O(n) by Master Theorem
- Worst case: O(n²)
  - Poor pivot selection leads to unbalanced partitions
  - Mitigated by randomization or median-of-medians

**Space Complexity**: 
- O(1) for in-place implementation
- O(log n) for recursion stack

**Best For**: Finding exact kth element when all data available

---

### Sorting-Based Approach

**Time Complexity**: O(n log n)
- Dominated by sorting operation
- Selection is O(k)

**Space Complexity**: O(1) to O(n) depending on sort algorithm

**Best For**: Small datasets, multiple queries, or when sorted output required

---

### Bucket Sort Approach (for frequency problems)

**Time Complexity**: O(n)
- Counting: O(n)
- Bucketing: O(n)
- Collecting results: O(n)

**Space Complexity**: O(n) for buckets and frequency map

**Best For**: Frequency-based problems when k is large or O(n) time critical

---

## Trade-offs and When to Use Each Approach

### Use **Heap (PriorityQueue)** when:
✅ You need top k elements (not just kth element)  
✅ Working with streaming/dynamic data  
✅ Memory allows storing k elements  
✅ O(n log k) complexity is acceptable  
✅ Default choice for most Top K problems

**Pros**: Efficient, works with streams, easy to implement  
**Cons**: Not as fast as QuickSelect for kth element

---

### Use **QuickSelect** when:
✅ Finding exact kth element (not all k elements)  
✅ All data available upfront (not streaming)  
✅ Need O(n) average time complexity  
✅ Can modify original array or have extra space

**Pros**: Optimal O(n) average time  
**Cons**: Worst case O(n²), doesn't work for streams, doesn't give sorted output

---

### Use **Sorting** when:
✅ Dataset is small (n ≈ k)  
✅ Need sorted output anyway  
✅ Making multiple queries on same data  
✅ Simplicity preferred over optimization

**Pros**: Simple, reliable, sorted output  
**Cons**: O(n log n) time regardless of k, inefficient for k << n

---

### Use **Bucket Sort** when:
✅ Finding k frequent elements  
✅ Need O(n) time complexity  
✅ Frequencies are known to be bounded  
✅ Can afford O(n) space

**Pros**: Optimal O(n) time for frequency problems  
**Cons**: Extra space, only for frequency-based problems

---

## Practice Problems

### Easy
1. **Kth Largest Element** - Find kth largest in unsorted array
2. **Top K Frequent Numbers** - Most common elements
3. **K Closest Points to Origin** - Distance-based sorting

### Medium
4. **Top K Frequent Words** - With lexicographic tiebreaker
5. **Find K Pairs with Smallest Sums** - Merge k sorted arrays concept
6. **Kth Smallest Element in Sorted Matrix** - 2D array application
7. **Sort Characters by Frequency** - String frequency sorting
8. **Reorganize String** - Greedy with heap

### Hard
9. **Find Median from Data Stream** - Two heaps approach
10. **Sliding Window Median** - Dynamic median with two heaps
11. **Maximum Performance of a Team** - Greedy with heap optimization
12. **IPO (Maximum Capital)** - Greedy selection with two heaps

---

## Summary

The **Top K Elements** pattern is essential for efficient selection problems. Key takeaways:

1. **Heap approach** is the most versatile—use PriorityQueue with appropriate comparator
2. **QuickSelect** offers optimal O(n) average time for finding kth element
3. **Choose min-heap for k largest**, max-heap for k smallest
4. Understand trade-offs: time vs space, average vs worst-case, simplicity vs optimization
5. For streaming data, only heap-based solutions work effectively
6. Custom comparators enable solving complex variant problems

Master these approaches, understand when to apply each, and you'll be well-equipped to tackle any Top K Elements problem efficiently!

---

**Related Patterns**: Two Heaps, Modified Binary Search, Sorting Algorithms  
**Key Data Structure**: PriorityQueue (Heap)  
**Complexity Sweet Spot**: O(n log k) beats O(n log n) when k << n
