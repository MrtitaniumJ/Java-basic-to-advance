# Searching Algorithms

## Problem Statement

Searching is one of the most frequently performed operations in computer science. Different searching algorithms have different performance characteristics and are suitable for different scenarios. This program demonstrates five fundamental searching algorithms: Linear Search, Binary Search, Jump Search, Exponential Search, and Sentinel Linear Search.

Efficient searching is crucial for:
- Database queries and indexing
- Web search engines
- Information retrieval systems
- Real-time applications
- Optimal algorithm selection

## Concepts

### Linear Search
The simplest search algorithm that checks elements one by one sequentially.

**How it works:**
1. Start from first element
2. Compare with target value
3. Move to next element if not found
4. Return index when found or -1 if not found

**Characteristics:**
- Works on both sorted and unsorted arrays
- Intuitive and easy to understand
- O(n) time complexity always
- O(1) space complexity
- Good for small arrays or unsorted data

### Binary Search
Efficient search algorithm for SORTED arrays that divides search space in half each time.

**How it works:**
1. Check middle element
2. If equal, return index
3. If target > middle, search right half
4. If target < middle, search left half
5. Repeat until found or range becomes empty

**Characteristics:**
- REQUIRES sorted array
- O(log n) time complexity
- O(1) space (iterative) or O(log n) (recursive)
- Most efficient for large sorted datasets
- Sensitive to comparisons

### Jump Search
Hybrid approach that jumps fixed steps then linear searches within block.

**How it works:**
1. Jump by √n steps through array
2. Find block containing target
3. Linear search within that block
4. Return index when found

**Characteristics:**
- Works only on sorted arrays
- O(√n) time complexity
- O(1) space complexity
- Better than linear for large arrays
- Fewer comparisons than binary search on some systems

### Exponential Search
Finds range containing target by exponentially expanding search space.

**How it works:**
1. Start with range [0, 1]
2. Double range until target is within range
3. Apply binary search on found range

**Characteristics:**
- Works on sorted arrays
- O(log n) time complexity
- O(1) space complexity
- Better when target is near start of array
- Fewer comparisons than binary search for small targets

### Sentinel Linear Search
Optimization of linear search that eliminates one comparison.

**How it works:**
1. Replace last element with target
2. Search without checking array bounds
3. Restore last element
4. Check if found before last position

**Characteristics:**
- Optimizes linear search slightly
- Still O(n) time complexity
- Reduces comparison count by ~1/n
- Useful when comparisons are expensive
- Works on unsorted arrays

## Complexity Analysis

| Algorithm | Requirement | Best Case | Average Case | Worst Case | Space |
|-----------|-------------|-----------|--------------|------------|-------|
| Linear Search | None | O(1) | O(n) | O(n) | O(1) |
| Binary Search | Sorted | O(1) | O(log n) | O(log n) | O(1) |
| Binary Recursive | Sorted | O(1) | O(log n) | O(log n) | O(log n) |
| Jump Search | Sorted | O(1) | O(√n) | O(√n) | O(1) |
| Exponential Search | Sorted | O(1) | O(log n) | O(log n) | O(1) |
| Sentinel Linear | None | O(1) | O(n) | O(n) | O(1) |

## Sample Input/Output

```
===== SEARCHING ALGORITHMS DEMO =====

--- LINEAR SEARCH ---
Unsorted Array: 
[64, 34, 25, 12, 22, 11, 90]
Searching for 22: Found at index 4

--- LINEAR SEARCH (ALL OCCURRENCES) ---
Array with duplicates: 
[5, 5, 10, 10, 15, 15, 20, 20, 25, 25]
Searching for 10: Found at indices [2, 3]

--- BINARY SEARCH ---
Sorted Array: 
[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
Searching for 30: Found at index 5

--- BINARY SEARCH (RECURSIVE) ---
Searching for 15 (recursive): Found at index 2

--- BINARY SEARCH (FIRST/LAST OCCURRENCE) ---
Array with duplicates: 
[5, 5, 10, 10, 15, 15, 20, 20, 25, 25]
Searching for 15:
  First occurrence at index: 4
  Last occurrence at index: 5

--- JUMP SEARCH ---
Sorted Array: 
[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
Searching for 25: Found at index 4

--- EXPONENTIAL SEARCH ---
Sorted Array: 
[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
Searching for 40: Found at index 7

--- SENTINEL LINEAR SEARCH ---
Unsorted Array: 
[64, 34, 25, 12, 22, 11, 90]
Searching for 11: Found at index 5

--- PERFORMANCE COMPARISON ---
Searching for 75000 in 100,000 sorted elements:
Linear Search: 13456 µs
Binary Search: 12 µs
Jump Search: 89 µs
```

## Key Methods Explained

### `linearSearch(int[] arr, int target)`
- Simple iterative approach
- Works on any array
- Returns first occurrence or -1

### `binarySearch(int[] arr, int target)`
- Iterative implementation avoids recursion overhead
- Uses mid = left + (right - left) / 2 to prevent overflow
- Most efficient for large sorted arrays

### `binarySearchRecursive(int[] arr, int target, int left, int right)`
- Elegant recursive approach
- Base case when left > right
- Uses same division strategy as iterative

### `binarySearchFirst(int[] arr, int target)`
- Finds leftmost (first) occurrence
- Continues searching left even after finding target
- Useful for duplicate arrays

### `binarySearchLast(int[] arr, int target)`
- Finds rightmost (last) occurrence
- Continues searching right after finding target
- Complements binarySearchFirst

### `jumpSearch(int[] arr, int target)`
- Uses Math.sqrt() for optimal step size
- Jumps then searches within block
- Balance between linear and binary search

## Search Algorithm Selection Guide

**Use Linear Search when:**
- Array is unsorted
- Array is very small (< 100 elements)
- You need simplicity and clarity
- You need to find all occurrences

**Use Binary Search when:**
- Array is sorted
- Array is large
- You need optimal performance
- You can afford preprocessing to sort

**Use Jump Search when:**
- Array is sorted
- You want uniform performance across all systems
- Comparisons are relatively expensive
- You need predictable cache behavior

**Use Exponential Search when:**
- Array is sorted
- Target is likely near the beginning
- You don't know array size
- You want adaptive behavior

**Use Sentinel Search when:**
- Array is small and unsorted
- Comparisons are very expensive
- You want to optimize comparison count slightly
- Simplicity is paramount

## Variations and Challenges

### Variation 1: Search in Rotated Array
Find element in rotated sorted array
```java
public static int searchRotated(int[] arr, int target)
```

### Variation 2: Search in 2D Array
Search for element in 2D sorted matrix
```java
public static boolean searchMatrix(int[][] matrix, int target)
```

### Variation 3: Find Closest Element
Find element closest to target value
```java
public static int findClosest(int[] arr, int target)
```

### Challenge 1: Ternary Search
Implement search that divides array into three parts
```java
public static int ternarySearch(int[] arr, int target)
```

### Challenge 2: Fibonacci Search
Use Fibonacci numbers to divide search space
```java
public static int fibonacciSearch(int[] arr, int target)
```

### Challenge 3: Interpolation Search
Use estimated position for adaptive searching
```java
public static int interpolationSearch(int[] arr, int target)
```

### Challenge 4: Search with Comparator
Generic search working with any comparable objects
```java
public static <T extends Comparable<T>> int search(T[] arr, T target)
```

## Interview Questions

1. **Why must array be sorted for binary search?**
   - Answer: Binary search relies on comparison to eliminate half of remaining elements

2. **When would linear search be faster than binary search?**
   - Answer: For very small arrays or when element is at beginning

3. **What's the advantage of jump search over binary search?**
   - Answer: Uniform cache behavior, sometimes fewer comparisons on systems with slow division

4. **How would you search in an unsorted array efficiently?**
   - Answer: Either sort first (O(n log n)) or use specialized algorithms like hashing

5. **Can you do better than O(log n) for sorted array search?**
   - Answer: Not with comparison-based algorithms; use hashing for O(1)

## Edge Cases to Consider

- **Empty array:** Return -1 without error
- **Single element:** Should work correctly for all algorithms
- **Target not present:** Return -1
- **Multiple occurrences:** Specify if finding first, last, or all
- **Duplicate elements:** Consider placement and consistency
- **Sorted vs Unsorted:** Choose algorithm accordingly
- **Very large array:** Consider memory and cache effects
- **Negative numbers:** All algorithms work with negatives

## Running the Program

```powershell
# Compile
javac SearchingImplementation.java

# Run
java SearchingImplementation
```

## Tips for Learning

1. **Visualize:** Draw arrays and trace algorithm steps
2. **Understand Trade-offs:** No algorithm is universally best
3. **Know Requirements:** Array properties determine best choice
4. **Benchmark:** Test with different array sizes
5. **Implement Multiple Times:** Ensure muscle memory

## Common Mistakes

- Using binary search on unsorted array
- Not handling mid = (left + right) / 2 overflow correctly
- Forgetting array bounds in loop conditions
- Confusing first/last occurrence positions
- Not checking if array is actually sorted
- Using wrong algorithm for data characteristics

---

**Practice:** Master searching algorithms. They're fundamental to computer science and appear frequently in interviews and real-world applications.
