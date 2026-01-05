# Complexity Analysis

## Table of Contents
1. [Introduction to Big-O Notation](#introduction-to-big-o-notation)
2. [Professional Definition](#professional-definition)
3. [Time Complexity](#time-complexity)
4. [Space Complexity](#space-complexity)
5. [Best, Average, Worst Case Analysis](#best-average-worst-case-analysis)
6. [Amortized Analysis](#amortized-analysis)
7. [Examples from Common Data Structures](#examples-from-common-data-structures)
8. [How to Calculate Complexity](#how-to-calculate-complexity)
9. [Complexity Comparison](#complexity-comparison)
10. [Practical Java Examples](#practical-java-examples)
11. [Interview Questions](#interview-questions)

---

## Introduction to Big-O Notation

### What is Big-O Notation?

Big-O notation is a way to describe how an algorithm's performance (time or space) grows as the input size increases. It helps us understand:

- **How slow will my program be?**
- **How much memory will it use?**
- **Which algorithm is more efficient?**

### Simple Analogy

Imagine you're searching for a contact in a phone book:

- **Linear Search**: You start from the beginning and check each name one by one
  - For 1,000 names: Worst case = 1,000 checks
  - For 10,000 names: Worst case = 10,000 checks
  - **Pattern**: As input grows, time grows proportionally → **O(n)**

- **Binary Search**: You open in the middle, go left or right based on what you see
  - For 1,000 names: Worst case = ~10 checks
  - For 10,000 names: Worst case = ~14 checks
  - **Pattern**: Time grows logarithmically → **O(log n)**

---

## Professional Definition

**Big-O notation** is used to classify algorithms by how their running time or space requirements grow as the input size grows. Formally:

For two functions f(n) and g(n), we say **f(n) is O(g(n))** if there exist positive constants `c` and `n₀` such that:

$$f(n) \leq c \cdot g(n) \text{ for all } n \geq n_0$$

In simpler terms: **O(g(n))** describes the upper bound on the growth rate of an algorithm's resource usage.

### Key Points:
- **We care about the dominant term**: O(n² + n + 5) simplifies to **O(n²)**
- **We ignore constants**: O(3n) = O(n)
- **We focus on the worst case** (unless specified otherwise)

---

## Time Complexity

Time complexity describes how the runtime of an algorithm grows with the input size.

### 1. **O(1) - Constant Time**

The execution time remains the same regardless of input size.

```
Operations: Fixed number of operations
Growth: Flat line
Examples: Accessing array element by index, HashMap lookup, Stack push/pop
```

**Java Examples:**

```java
// O(1) - Accessing array element
int[] arr = {1, 2, 3, 4, 5};
int value = arr[2];  // Direct access - O(1)

// O(1) - HashMap/Dictionary lookup
HashMap<String, Integer> map = new HashMap<>();
map.put("key", 100);
int result = map.get("key");  // O(1) average case

// O(1) - Simple arithmetic
int sum = 5 + 3 + 2;  // Always takes same time
```

---

### 2. **O(log n) - Logarithmic Time**

The time taken increases logarithmically with input size. Typically happens when you divide the problem in half each time.

```
Operations: Halving the problem size each iteration
Growth: Slow increase
Examples: Binary search, balanced BST operations
```

**Java Examples:**

```java
// O(log n) - Binary Search
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target)
            return mid;
        else if (arr[mid] < target)
            left = mid + 1;  // Eliminate left half
        else
            right = mid - 1;  // Eliminate right half
    }
    
    return -1;
}

// For n=1000, we need ~10 iterations
// For n=1,000,000, we need ~20 iterations
```

---

### 3. **O(n) - Linear Time**

The time grows proportionally with input size. For each input element, constant work is done.

```
Operations: Process each element once
Growth: Straight diagonal line
Examples: Linear search, array traversal, finding max/min
```

**Java Examples:**

```java
// O(n) - Linear Search
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {  // Loop n times
        if (arr[i] == target)
            return i;
    }
    return -1;
}

// O(n) - Finding sum of array
public static int sumArray(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {  // Loop n times
        sum += arr[i];  // O(1) operation
    }
    return sum;
}

// O(n) - ArrayList iteration
ArrayList<Integer> list = new ArrayList<>();
for (int i = 0; i < list.size(); i++) {  // O(n)
    System.out.println(list.get(i));  // O(1) per iteration
}
```

---

### 4. **O(n log n) - Linearithmic Time**

Combines linear and logarithmic complexity. Common in efficient sorting algorithms.

```
Operations: Process each element, and for each do logarithmic work
Growth: Between linear and quadratic
Examples: Merge Sort, Quick Sort (average), Heap Sort
```

**Java Examples:**

```java
// O(n log n) - Merge Sort
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        
        mergeSort(arr, left, mid);        // T(n/2)
        mergeSort(arr, mid + 1, right);   // T(n/2)
        merge(arr, left, mid, right);     // O(n) merge operation
    }
}

// O(n log n) - Heap Sort
public static void heapSort(int[] arr) {
    int n = arr.length;
    
    // Build heap: O(n log n)
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(arr, n, i);
    }
    
    // Extract elements: O(n log n)
    for (int i = n - 1; i > 0; i--) {
        swap(arr, 0, i);
        heapify(arr, i, 0);
    }
}
```

---

### 5. **O(n²) - Quadratic Time**

Time grows proportionally to the square of input size. Typically happens with nested loops.

```
Operations: Nested loops processing elements
Growth: Parabolic curve
Examples: Bubble Sort, Selection Sort, Insertion Sort (worst case)
```

**Java Examples:**

```java
// O(n²) - Bubble Sort
public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {        // Outer loop: n times
        for (int j = 0; j < n - i - 1; j++) { // Inner loop: ~n times
            if (arr[j] > arr[j + 1]) {
                // Swap
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// O(n²) - Selection Sort
public static void selectionSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {          // Outer loop: n times
        int minIdx = i;
        for (int j = i + 1; j < n; j++) {      // Inner loop: ~n times
            if (arr[j] < arr[minIdx]) {
                minIdx = j;
            }
        }
        swap(arr, i, minIdx);
    }
}

// O(n²) - 2D Array traversal
public static void print2DArray(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {        // n rows
        for (int j = 0; j < matrix[i].length; j++) { // n columns
            System.out.print(matrix[i][j]);  // O(1)
        }
    }
}
```

---

### 6. **O(2^n) - Exponential Time**

The time doubles with each additional input element. Extremely slow for large inputs.

```
Operations: Branching factor of 2 (or more)
Growth: Exponential curve (very steep)
Examples: Recursive fibonacci, generating all subsets
```

**Java Examples:**

```java
// O(2^n) - Recursive Fibonacci (without memoization)
public static int fibonacci(int n) {
    if (n <= 1)
        return n;
    return fibonacci(n - 1) + fibonacci(n - 2);  // Two recursive calls
}

/*
Tree of calls for fibonacci(5):
                    fib(5)
                  /        \
              fib(4)         fib(3)
             /      \       /      \
          fib(3)  fib(2)  fib(2)  fib(1)
         /    \   /  \    /  \
       ...    ...  ...   ...  ...

Total calls: 2^n in worst case
*/

// O(2^n) - Generate all subsets
public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private static void backtrack(List<List<Integer>> result, 
                               List<Integer> current, 
                               int[] nums, int index) {
    result.add(new ArrayList<>(current));
    
    for (int i = index; i < nums.length; i++) {
        current.add(nums[i]);
        backtrack(result, current, nums, i + 1);  // Exponential branching
        current.remove(current.size() - 1);
    }
}

// For n=20: 2^20 = 1,048,576 operations
// For n=30: 2^30 = 1,073,741,824 operations (>1 billion!)
```

---

### 7. **O(n!) - Factorial Time**

The slowest complexity. Time grows as factorial of input size.

```
Operations: n! permutations/arrangements
Growth: Extremely steep (fastest growing)
Examples: Generating all permutations, traveling salesman (brute force)
```

**Java Examples:**

```java
// O(n!) - Generate all permutations
public static List<List<Integer>> permutations(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums);
    return result;
}

private static void backtrack(List<List<Integer>> result, 
                               List<Integer> current, 
                               int[] nums) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int num : nums) {
        if (!current.contains(num)) {  // Check if already used
            current.add(num);
            backtrack(result, current, nums);  // Factorial branching
            current.remove(current.size() - 1);
        }
    }
}

// O(n!) - Traveling Salesman Problem (brute force)
public static void travelingSalesman(int[][] distances, 
                                      List<Integer> currentPath, 
                                      boolean[] visited, 
                                      int n) {
    if (currentPath.size() == n) {
        // Check this permutation
        return;
    }
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            visited[i] = true;
            currentPath.add(i);
            travelingSalesman(distances, currentPath, visited, n);  // n! permutations
            currentPath.remove(currentPath.size() - 1);
            visited[i] = false;
        }
    }
}

// For n=10: 10! = 3,628,800 operations
// For n=12: 12! = 479,001,600 operations
```

---

## Space Complexity

Space complexity describes how the memory usage of an algorithm grows with input size.

### Key Points:
- Includes auxiliary space (extra space used by the algorithm)
- Does NOT include input space (sometimes excluded from analysis)
- Similar notation as time complexity

### Common Space Complexities:

```java
// O(1) - Constant Space
public static int findMax(int[] arr) {
    int max = arr[0];  // Single variable
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > max)
            max = arr[i];
    }
    return max;  // Space: O(1) regardless of input size
}

// O(n) - Linear Space
public static List<Integer> createList(int n) {
    List<Integer> list = new ArrayList<>();  // Space grows with n
    for (int i = 0; i < n; i++) {
        list.add(i);
    }
    return list;  // Space: O(n)
}

// O(n²) - Quadratic Space
public static int[][] createMatrix(int n) {
    int[][] matrix = new int[n][n];  // n x n array
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matrix[i][j] = i + j;
        }
    }
    return matrix;  // Space: O(n²)
}

// O(log n) - Logarithmic Space (typical with recursion)
public static int binarySearchRecursive(int[] arr, int left, int right, int target) {
    if (left > right)
        return -1;
    
    int mid = left + (right - left) / 2;
    
    if (arr[mid] == target)
        return mid;
    else if (arr[mid] < target)
        return binarySearchRecursive(arr, mid + 1, right, target);  // Call stack: O(log n)
    else
        return binarySearchRecursive(arr, left, mid - 1, target);
}

// O(n) - Space from recursion stack
public static int fibonacci(int n) {
    if (n <= 1)
        return n;
    return fibonacci(n - 1) + fibonacci(n - 2);  // Recursion depth: O(n) space
}
```

---

## Best, Average, Worst Case Analysis

Different scenarios of algorithm performance based on input characteristics:

### Example: Linear Search

```java
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target)
            return i;
    }
    return -1;
}
```

| Case | Scenario | Example | Complexity |
|------|----------|---------|------------|
| **Best** | Element found at first position | arr = [5, 2, 3], target = 5 | O(1) |
| **Average** | Element found in middle | arr = [1, 2, 5, 4], target = 5 | O(n/2) = O(n) |
| **Worst** | Element not found or at end | arr = [1, 2, 3, 4], target = 5 | O(n) |

### Example: Quick Sort

```java
public static void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}
```

| Case | Scenario | Partition Quality | Complexity |
|------|----------|-------------------|------------|
| **Best** | Partition always splits array in half | Balanced | O(n log n) |
| **Average** | Random partitions | Random splits | O(n log n) |
| **Worst** | Always picks smallest or largest | 1 to n-1 split | O(n²) |

---

## Amortized Analysis

Amortized analysis calculates the average time per operation over a sequence of operations, even if individual operations have varying complexities.

### Example: Dynamic Array (ArrayList)

When an ArrayList is full and you add a new element:
- Most additions: O(1) - just insert
- Sometimes: O(n) - when array is full, create new array and copy all elements

**Amortized Analysis:**
- Every element added causes ~2 copies on average (due to doubling capacity)
- Amortized cost: O(1) per add operation

```java
public class DynamicArray {
    private int[] arr = new int[1];
    private int size = 0;
    
    public void add(int value) {
        if (size == arr.length) {
            // Resize: O(n) operation (but rare)
            int[] newArr = new int[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                newArr[i] = arr[i];  // Copy all: O(n)
            }
            arr = newArr;
        }
        arr[size++] = value;  // Insert: O(1)
    }
    
    /*
    Add operations:
    1st: O(1), 2nd: O(1), 3rd: O(3) [resize], 4th: O(1), 5th: O(1), 
    6th: O(1), 7th: O(1), 8th: O(8) [resize], ...
    
    Total for n adds: O(n)
    Amortized per add: O(n)/n = O(1)
    */
}
```

---

## Examples from Common Data Structures

### Array/List Operations

```
Access:     O(1)  - Direct index
Search:     O(n)  - Linear search (or O(log n) if sorted + binary search)
Insert:     O(n)  - May need to shift elements
Delete:     O(n)  - May need to shift elements
```

### Linked List Operations

```
Access:     O(n)  - Must traverse from head
Search:     O(n)  - Linear search
Insert:     O(1)  - If position known, O(n) if need to find position
Delete:     O(1)  - If node known, O(n) if need to find node
```

### Hash Table (HashMap)

```
Insert:     O(1)  - Average (O(n) worst with collisions)
Delete:     O(1)  - Average (O(n) worst with collisions)
Search:     O(1)  - Average (O(n) worst with collisions)
```

### Binary Search Tree

```
Insert:     O(log n) - Average (O(n) worst if unbalanced)
Delete:     O(log n) - Average (O(n) worst if unbalanced)
Search:     O(log n) - Average (O(n) worst if unbalanced)
```

### Heap

```
Insert:     O(log n) - Maintain heap property
Delete:     O(log n) - Remove and restore
Peek:       O(1)     - Access root
```

### Stack

```
Push:       O(1)
Pop:        O(1)
Peek:       O(1)
```

### Queue

```
Enqueue:    O(1)
Dequeue:    O(1)
Peek:       O(1)
```

---

## How to Calculate Complexity

### Step 1: Count Operations
Count how many times basic operations execute relative to input size.

```java
int count = 0;

// Single loop: O(n)
for (int i = 0; i < n; i++) {
    count++;  // Executes n times
}

// Nested loops: O(n²)
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        count++;  // Executes n * n times
    }
}

// Sequential loops: O(n) + O(n) = O(n)
for (int i = 0; i < n; i++) {
    count++;
}
for (int i = 0; i < n; i++) {
    count++;
}
```

### Step 2: Identify Dominant Term
Keep only the fastest growing term.

```
3n² + 2n + 5  →  O(n²)
n log n + n   →  O(n log n)
2^n + n³      →  O(2^n)
```

### Step 3: Handle Constants
Remove constant multipliers.

```
O(3n)   →  O(n)
O(2^n)  →  O(2^n)  [Power is significant]
O(n/2)  →  O(n)
```

### Step 4: Check Recursion
For recursive functions, use recurrence relations.

```java
// T(n) = T(n-1) + O(1)
// This expands to T(n) = O(1) + O(1) + ... + O(1) [n times]
// Result: O(n)
public static void printNumbers(int n) {
    if (n == 0) return;           // Base case: O(1)
    System.out.println(n);        // O(1)
    printNumbers(n - 1);          // T(n-1)
}

// T(n) = 2*T(n/2) + O(1)
// This is the master theorem: O(n log n) or O(n) depending on merge cost
// Merge Sort example
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);        // T(n/2)
        mergeSort(arr, mid + 1, right);   // T(n/2)
        merge(arr, left, mid, right);     // O(n)
    }
}
```

---

## Complexity Comparison

### Visual Comparison

```
Operations Count for Different Input Sizes:

n = 10:
O(1)      = 1
O(log n)  = 3.3
O(n)      = 10
O(n log n) = 33
O(n²)     = 100
O(2^n)    = 1,024
O(n!)     = 3,628,800

n = 100:
O(1)      = 1
O(log n)  = 6.6
O(n)      = 100
O(n log n) = 660
O(n²)     = 10,000
O(2^n)    = 1.27 × 10^30 (INFEASIBLE)
O(n!)     = 9.33 × 10^157 (INFEASIBLE)

n = 1,000:
O(1)      = 1
O(log n)  = 9.9
O(n)      = 1,000
O(n log n) = 9,966
O(n²)     = 1,000,000
O(2^n)    = INFEASIBLE
O(n!)     = INFEASIBLE

n = 1,000,000:
O(1)      = 1
O(log n)  = 19.9
O(n)      = 1,000,000
O(n log n) = 19,931,569
O(n²)     = INFEASIBLE
O(2^n)    = INFEASIBLE
O(n!)     = INFEASIBLE
```

### Growth Rate Graph

```
    Operations
    ^
    |                                    O(n!)
    |                                  /
    |                                O(2^n)
    |                              /
    |                          O(n²)
    |                        /
    |                    O(n log n)
    |                  /
    |              O(n)
    |            /
    |          /
    |        /
    |      O(log n)
    |    /
    |  O(1)
    |__________________|_________________|_________|______|___|_____|______> n
         10               100            1000      10000 100K  1M
```

### Complexity "Cheat Sheet"

| Complexity | Feasibility | Max n | Runtime at 10^8 ops/sec |
|----------|------------|-------|------------------------|
| O(1) | Always ✓ | ∞ | 1 ns |
| O(log n) | Always ✓ | ∞ | 30 ns |
| O(n) | Always ✓ | ~10^8 | 1 sec |
| O(n log n) | Almost always ✓ | ~10^7 | 10 sec |
| O(n²) | Sometimes ✓ | ~10^4 | 100 sec |
| O(n³) | Rarely ✓ | ~500 | 1000 sec |
| O(2^n) | Only small n ✓ | ~20 | Infeasible |
| O(n!) | Only very small n ✓ | ~10 | Infeasible |

---

## Practical Java Examples

### Example 1: Finding Duplicates in Array

```java
// Approach 1: O(n²) - Brute Force
public static boolean hasDuplicateN2(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = i + 1; j < arr.length; j++) {  // Nested loop
            if (arr[i] == arr[j])
                return true;
        }
    }
    return false;
}

// Approach 2: O(n log n) - Sorting
public static boolean hasDuplicateNLogN(int[] arr) {
    Arrays.sort(arr);  // O(n log n)
    for (int i = 0; i < arr.length - 1; i++) {  // O(n)
        if (arr[i] == arr[i + 1])
            return true;
    }
    return false;
}

// Approach 3: O(n) - Hash Set (Best!)
public static boolean hasDuplicateN(int[] arr) {
    Set<Integer> seen = new HashSet<>();
    for (int num : arr) {  // O(n)
        if (seen.contains(num))  // O(1)
            return true;
        seen.add(num);  // O(1)
    }
    return false;
}
```

### Example 2: Checking if Array is Sorted

```java
// O(n) - Must check all elements in worst case
public static boolean isSorted(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i] > arr[i + 1])
            return false;
    }
    return true;
}

/*
Time: O(n)
- Best case: O(1) - if first pair is not sorted
- Average case: O(n/2) = O(n)
- Worst case: O(n) - must check all pairs

Space: O(1)
*/
```

### Example 3: Computing All Pairs Sum

```java
// O(n²) with O(1) space
public static void printAllPairs(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            System.out.println(arr[i] + " + " + arr[j] + " = " + (arr[i] + arr[j]));
        }
    }
}

/*
Time: O(n²)
- Nested loops: n * (n-1) / 2 = O(n²)

Space: O(1)
- Only using constant variables
*/
```

### Example 4: Two Sum Problem

```java
// Approach 1: O(n²) - Brute Force
public static int[] twoSumN2(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {  // Nested loop
            if (nums[i] + nums[j] == target)
                return new int[]{i, j};
        }
    }
    return new int[]{-1, -1};
}

// Approach 2: O(n) - Hash Map (Best!)
public static int[] twoSumN(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(nums[i], i);
    }
    return new int[]{-1, -1};
}

/*
Approach 2 Analysis:
Time: O(n) - Single pass through array with O(1) HashMap operations
Space: O(n) - Storing up to n elements in HashMap
*/
```

### Example 5: String Reversal

```java
// Approach 1: O(n) time, O(n) space
public static String reverseString1(String s) {
    return new StringBuilder(s).reverse().toString();
}

// Approach 2: O(n) time, O(n) space (char array)
public static String reverseString2(String s) {
    char[] chars = s.toCharArray();  // O(n) space
    int left = 0, right = chars.length - 1;
    
    while (left < right) {  // O(n) iterations
        // Swap: O(1)
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        
        left++;
        right--;
    }
    
    return new String(chars);
}

/*
Both approaches:
Time: O(n)
Space: O(n) - needed for output in Java (strings are immutable)
*/
```

---

## Interview Questions

### Question 1: Basic Complexity
**Q: What is the time complexity of the following code?**

```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

**A:** **O(n)** - The loop executes n times, and each iteration does O(1) work.

---

### Question 2: Nested Loops
**Q: What is the time complexity?**

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        arr[i][j] = i + j;
    }
}
```

**A:** **O(n²)** - Nested loops: outer loop runs n times, inner loop runs n times per outer iteration.

---

### Question 3: Multiple Conditions
**Q: What is the time complexity?**

```java
for (int i = 0; i < n; i++) {
    System.out.println(i);          // O(1)
}

for (int i = 0; i < m; i++) {
    System.out.println(i);          // O(1)
}

HashSet<Integer> set = new HashSet<>(list);  // O(n)
```

**A:** **O(n + m)** - Add the complexities: O(n) + O(m) + O(n) = O(n + m) where n ≥ m → O(n)

---

### Question 4: Logarithmic Work
**Q: What is the time complexity?**

```java
int count = 0;
for (int i = n; i > 0; i = i / 2) {  // i: n, n/2, n/4, n/8, ..., 1
    count++;
}
```

**A:** **O(log n)** - The loop divides n by 2 each iteration, so it runs log₂(n) times.

---

### Question 5: Early Termination
**Q: What is the time complexity of finding an element in a sorted array?**

```java
public static boolean linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target)
            return true;
    }
    return false;
}
```

**A:** 
- **Best case: O(1)** - Element at first position
- **Average case: O(n)** - Element typically in middle
- **Worst case: O(n)** - Element at end or not present

Unless stated otherwise, we assume **O(n)** (worst case).

---

### Question 6: Recursive Fibonacci
**Q: What is the time complexity?**

```java
public static int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

**A:** **O(2^n)** - The function makes 2 recursive calls for each value, creating a binary tree of depth n.

**Improvement:** With memoization: **O(n)**

```java
public static int fibMemo(int n, int[] memo) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];
    memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    return memo[n];
}
```

---

### Question 7: Which Algorithm is Better?
**Q: Comparing two sorting algorithms:**
- **Algorithm A:** Time O(n²), Space O(1)
- **Algorithm B:** Time O(n log n), Space O(n)

**Which is better?**

**A:** Depends on context:
- **For n < 1000:** Algorithm A might be acceptable (low constant factors)
- **For n > 10,000:** Algorithm B is clearly better (O(n log n) vastly outperforms O(n²))
- **If memory is critical:** Algorithm A uses less space
- **If time is critical:** Algorithm B is faster

**Industry answer:** Usually Algorithm B is preferred for general use.

---

### Question 8: Two Approaches Complexity
**Q: You need to find if two arrays have a common element. Compare approaches:**

**Approach 1:**
```java
for (int i = 0; i < arr1.length; i++) {
    for (int j = 0; j < arr2.length; j++) {
        if (arr1[i] == arr2[j])
            return true;
    }
}
```

**Approach 2:**
```java
Set<Integer> set = new HashSet<>(Arrays.asList(arr1));
for (int i = 0; i < arr2.length; i++) {
    if (set.contains(arr2[i]))
        return true;
}
```

**A:** 
- **Approach 1:** O(n × m) time, O(1) space
- **Approach 2:** O(n + m) time, O(n) space

**Better:** Approach 2 is faster for large arrays (trades space for time).

---

### Question 9: Understanding Constants
**Q: Does O(n) = O(2n)?**

**A:** **Yes, they are equivalent.** 
- O(n) and O(2n) both describe linear growth
- Constants are ignored in Big-O notation
- Both simplify to O(n)

However: **O(n) ≠ O(n²)** - different growth rates!

---

### Question 10: Space Complexity with Recursion
**Q: What is the space complexity?**

```java
public static void print(int n) {
    if (n == 0) return;
    System.out.println(n);
    print(n - 1);
}
```

**A:** **O(n)** - The call stack grows to depth n.

```
print(5)
  print(4)
    print(3)
      print(2)
        print(1)
          print(0)  [base case]
          
Stack depth: 5 → O(n) space
```

---

### Question 11: Merge Sort Complexity
**Q: Explain time and space complexity of merge sort.**

**A:**
- **Time: O(n log n)** all cases
  - Divide: O(log n) levels
  - Conquer: O(n) work at each level
  - Merge: O(n) per level
  - Total: O(n log n)

- **Space: O(n)** 
  - Need temporary arrays for merging

```
Merge Sort Tree:
                    [n]
                 /       \
              [n/2]      [n/2]      Level 1: 1 array of size n = O(n)
             /    \    /    \
           [n/4] [n/4] [n/4] [n/4]   Level 2: 2 arrays of size n = O(n)
                    ... (continues)
          
Log(n) levels × O(n) work = O(n log n)
```

---

## Summary Table

| Complexity | Name | Examples | Feasibility |
|-----------|------|----------|-------------|
| O(1) | Constant | Array access, HashMap lookup | ✓ Always |
| O(log n) | Logarithmic | Binary search, balanced tree | ✓ Always |
| O(n) | Linear | Linear search, array iteration | ✓ Always |
| O(n log n) | Linearithmic | Merge sort, efficient sort | ✓ Almost always |
| O(n²) | Quadratic | Bubble sort, nested loops | ~ Sometimes |
| O(n³) | Cubic | 3D array iteration | ~ Sometimes |
| O(2^n) | Exponential | Recursive fibonacci | ✗ Small n only |
| O(n!) | Factorial | Permutations | ✗ Very small n only |

---

## Practice Problems

1. **Analyze complexity:** Find the time and space complexity of various code snippets
2. **Optimize algorithms:** Take a O(n²) solution and optimize to O(n) using hash maps
3. **Compare approaches:** Given multiple implementations, determine which is better
4. **Recursive analysis:** Determine complexity of recursive functions using recurrence relations
5. **Real-world scenarios:** Choose appropriate algorithms based on input constraints

---

## Resources for Further Learning

- Master Theorem for analyzing divide-and-conquer algorithms
- Amortized analysis for understanding average performance
- NP-completeness for understanding unsolvable problems
- Practical performance testing vs. theoretical analysis

---

**Last Updated:** January 2026

