# Array Problems

## Problem Statement

Array problems are among the most frequently asked questions in technical interviews. This program demonstrates solutions to common array manipulation challenges that test algorithmic thinking, space optimization, and mathematical insights. Mastering these problems is essential for any programmer.

Common array problems include:
1. **Finding Duplicates:** Identify repeated elements efficiently
2. **Missing Numbers:** Locate missing elements in sequences
3. **Array Rotation:** Shift elements while maintaining circular structure
4. **Common Elements:** Find intersection of multiple arrays
5. **Pair Finding:** Locate pairs matching specific criteria

## Concepts

### Finding Duplicates

**Problem:** Given an array with values from 1 to n-1 appearing multiple times, find duplicates.

**Key Insight:** Use array indices as a hash map since values are in known range.

**Methods:**
- HashSet approach: O(n) time, O(n) space
- Array index marking: O(n) time, O(1) extra space
- XOR approach: O(n) time, O(1) space

### Missing Number Problem

**Problem:** Array contains n-1 unique numbers from 1 to n; find the missing one.

**Mathematical Insight:** 
- Expected sum = n × (n + 1) / 2
- Actual sum = sum of array elements
- Missing = Expected - Actual

**Alternative Method:**
- XOR all numbers 1 to n
- XOR with all array elements
- Remaining value is the missing number

### Array Rotation

**Problem:** Rotate array to the right by k positions.

**Example:** [1, 2, 3, 4, 5] rotated right by 2 = [4, 5, 1, 2, 3]

**Methods:**

1. **Reversal Algorithm** (Most Efficient)
   - Reverse entire array
   - Reverse first k elements
   - Reverse remaining elements

2. **Temporary Array Method**
   - Copy last k elements to temp
   - Shift first n-k elements right
   - Copy temp back to front

3. **Element-by-element Rotation**
   - Move each element individually
   - Less efficient but intuitive

### Common Elements

**Problem:** Find intersection of two arrays.

**Approach:**
1. Create set from first array
2. Iterate second array
3. Add to result if element exists in first set

**Complexity:** O(m + n) time, O(min(m, n)) space

### Pair Finding

**Problem:** Find all pairs with sum equal to target.

**Algorithm:**
1. Use HashSet to track seen numbers
2. For each number, check if complement exists
3. Store pairs to avoid duplicates

## Complexity Analysis

| Problem | Method | Time | Space |
|---------|--------|------|-------|
| Duplicates | HashSet | O(n) | O(k) |
| Duplicates | Array Index | O(n) | O(1) |
| Missing Number | Math | O(n) | O(1) |
| Missing Number | XOR | O(n) | O(1) |
| Rotate | Reversal | O(n) | O(1) |
| Rotate | Temp Array | O(n) | O(k) |
| Common | HashSet | O(m+n) | O(min(m,n)) |
| Pairs | HashSet | O(n) | O(n) |

## Sample Input/Output

```
===== ARRAY PROBLEMS DEMO =====

--- FIND DUPLICATES ---
Array: 
[1, 2, 3, 2, 4, 3, 5]
Duplicates: [2, 3]

--- FIND MISSING NUMBER ---
Array (should contain 1-6): 
[1, 2, 4, 5, 6]
Missing number: 3
Missing number (XOR method): 3

--- ROTATE ARRAY ---
Original array: 
[1, 2, 3, 4, 5]
After rotating right by 2: 
[4, 5, 1, 2, 3]

--- ROTATE ARRAY (SIMPLE METHOD) ---
Original array: 
[1, 2, 3, 4, 5]
After rotating right by 2: 
[4, 5, 1, 2, 3]

--- FIND SECOND LARGEST ---
Array: 
[10, 5, 20, 8, 15]
Second largest: 15

--- FIND COMMON ELEMENTS ---
Array 1: 
[1, 2, 3, 4, 5]
Array 2: 
[3, 4, 5, 6, 7]
Common elements: [3, 4, 5]

--- FIND PAIRS WITH SUM ---
Array: 
[1, 5, 7, -1, 5]
Finding pairs with sum = 6
  (1, 5)
  (5, 1)
```

## Key Methods Explained

### `findDuplicates(int[] arr)`
- Uses HashSet.add() which returns false if already present
- Time: O(n), Space: O(k) where k is number of duplicates
- Simple and straightforward approach

### `findMissingNumber(int[] arr)`
- Uses mathematical formula for sum of first n numbers
- Time: O(n), Space: O(1)
- Elegant solution avoiding space overhead

### `rotateArray(int[] arr, int k)`
- Uses reversal algorithm (most space-efficient)
- Step 1: Reverse all
- Step 2: Reverse first k
- Step 3: Reverse remaining
- Time: O(n), Space: O(1)

### `findCommonElements(int[] arr1, int[] arr2)`
- Creates set from first array for fast lookup
- Time: O(m + n), Space: O(min(m, n))
- Avoids nested loops

### `findPairsWithSum(int[] arr, int targetSum)`
- Uses single pass with HashSet
- Tracks seen numbers and finds complements
- Time: O(n), Space: O(n)

## Variations and Challenges

### Variation 1: Find All Duplicates Using Constraints
Array has n elements with values 1 to n-1, multiple occurrences allowed. Find all duplicates without using extra space (except output).

```java
public static List<Integer> findDuplicatesInPlace(int[] arr)
```

### Variation 2: Rotate Left Instead of Right
Rotate array to the left by k positions
```java
public static void rotateLeft(int[] arr, int k)
```

### Variation 3: Find Kth Largest Element
Find the kth largest distinct element in array
```java
public static int findKthLargest(int[] arr, int k)
```

### Variation 4: Array Subset Check
Check if one array is subset of another
```java
public static boolean isSubset(int[] arr1, int[] arr2)
```

### Challenge 1: Find All Subarrays with Sum
Find all contiguous subarrays that sum to target value
```java
public static List<int[]> findSubarraysWithSum(int[] arr, int sum)
```

### Challenge 2: Maximum Subarray Sum (Kadane's Algorithm)
Find contiguous subarray with maximum sum
```java
public static int maxSubarraySum(int[] arr)
```

### Challenge 3: Majority Element
Find element appearing more than n/2 times (Boyer-Moore voting algorithm)
```java
public static int findMajority(int[] arr)
```

### Challenge 4: Trap Rainwater
Calculate how much water can be trapped between elevation bars
```java
public static int trapRainwater(int[] heights)
```

## Interview Questions

1. **How would you find duplicates without using extra space?**
   - Answer: Use array indices as hash, or XOR method

2. **What's the most efficient way to rotate an array?**
   - Answer: Reversal algorithm O(n) time, O(1) space

3. **How would you find missing number without overflow?**
   - Answer: Use XOR method instead of subtraction

4. **Can you find pairs with sum in one pass?**
   - Answer: Yes, use HashSet to track seen numbers

5. **What's the difference between rotation and rearrangement?**
   - Answer: Rotation maintains circular order; rearrangement doesn't

## Edge Cases to Consider

- **Empty array:** Return appropriate empty/null results
- **Single element:** Most operations return -1 or empty
- **All duplicates:** Find all or none depending on operation
- **No matches:** Return empty results gracefully
- **Large k in rotation:** Use k % length to optimize
- **Negative numbers:** Should work with mathematical approaches
- **Zero values:** Ensure not confused with missing values

## Running the Program

```powershell
# Compile
javac ArrayProblems.java

# Run
java ArrayProblems
```

## Tips for Learning

1. **Understand Constraints:** Problem constraints guide optimal solution
2. **Space vs Time:** Consider tradeoffs between complexity types
3. **Test Edge Cases:** Try empty, single, all same elements
4. **Practice Variations:** Master each problem type thoroughly
5. **Explain Your Logic:** Articulate approach before coding

## Common Mistakes

- Not handling k > array length in rotation
- Off-by-one errors in index calculations
- Assuming array is always sorted (when not stated)
- Not considering negative numbers in solutions
- Forgetting to reset array values after modifications

---

**Practice:** These problems are interview classics. Practice multiple times until you can solve them without references. Master them, and you'll be well-prepared for array-based interview questions.
