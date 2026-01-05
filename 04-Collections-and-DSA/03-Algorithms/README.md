# Algorithms - Comprehensive Guide

## Overview

Algorithms form the backbone of computer science and software engineering. This section covers fundamental and advanced algorithmic paradigms that are essential for solving complex problems efficiently. Each algorithm category addresses different problem-solving approaches with unique strengths, use cases, and performance characteristics.

---

## Table of Contents

1. [Sorting Algorithms](#sorting-algorithms)
2. [Searching Algorithms](#searching-algorithms)
3. [Recursion](#recursion)
4. [Dynamic Programming](#dynamic-programming)
5. [Greedy Algorithms](#greedy-algorithms)
6. [Backtracking](#backtracking)
7. [Additional Topics](#additional-topics)
8. [When to Use Each Approach](#when-to-use-each-approach)
9. [Interview Preparation](#interview-preparation)

---

## Sorting Algorithms

### Definition
Sorting algorithms rearrange elements in a data structure (typically an array or list) in a specific order—usually ascending or descending. Sorting is fundamental to many applications including data organization, searching optimization, and data analysis.

### Key Characteristics
- **Stability**: Whether equal elements maintain their relative order
- **In-place**: Whether the algorithm uses O(1) extra space
- **Adaptive**: Whether the algorithm performs better on partially sorted data
- **Recursive vs Iterative**: Algorithm structure and implementation style

### Common Sorting Algorithms

| Algorithm | Time Complexity (Avg) | Time Complexity (Worst) | Space Complexity | Stable | Best For |
|-----------|----------------------|------------------------|------------------|--------|----------|
| **Bubble Sort** | O(n²) | O(n²) | O(1) | Yes | Small datasets, nearly sorted data |
| **Selection Sort** | O(n²) | O(n²) | O(1) | No | Small datasets with limited memory |
| **Insertion Sort** | O(n²) | O(n²) | O(1) | Yes | Small/medium datasets, adaptive |
| **Merge Sort** | O(n log n) | O(n log n) | O(n) | Yes | Large datasets, guaranteed performance |
| **Quick Sort** | O(n log n) | O(n²) | O(log n) | No | General purpose, in-place, cache-friendly |
| **Heap Sort** | O(n log n) | O(n log n) | O(1) | No | Guaranteed performance, memory-constrained |
| **Counting Sort** | O(n + k) | O(n + k) | O(k) | Yes | Small range of integers |
| **Radix Sort** | O(d × n) | O(d × n) | O(n + k) | Yes | Large numbers, string sorting |
| **Shell Sort** | O(n log n) | O(n²) | O(1) | No | Medium datasets, cache-efficient |

### Example: Quick Sort Implementation

```java
public class QuickSort {
    // Partition the array and return pivot index
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Swap arr[i+1] and arr[high]
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    // Main quick sort method
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    // Utility method to sort entire array
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length - 1);
    }
}
```

### When to Use
- **Merge Sort**: When you need guaranteed O(n log n) performance, external sorting, or stability is required
- **Quick Sort**: General-purpose sorting with good average performance and low space overhead
- **Heap Sort**: When you need guaranteed worst-case performance and minimal memory
- **Insertion Sort**: Small datasets or nearly sorted data where adaptive behavior is beneficial
- **Counting/Radix Sort**: When dealing with integers in a specific range or non-comparable objects

### Detailed Guides
- [Sorting Algorithms - Detailed Study](./01-Sorting/README.md)
- [Advanced Sorting Techniques](./02-Sorting/README.md)

---

## Searching Algorithms

### Definition
Searching algorithms locate a target element within a data structure. The efficiency of search depends on data organization, structure, and the algorithm chosen.

### Categories and Comparison

| Algorithm | Time Complexity | Space Complexity | Requires Sorted Data | Best Use Case |
|-----------|-----------------|------------------|----------------------|---------------|
| **Linear Search** | O(n) | O(1) | No | Unordered data, small datasets |
| **Binary Search** | O(log n) | O(1) | Yes | Sorted array with frequent searches |
| **Jump Search** | O(√n) | O(1) | Yes | Sorted array, uniform distribution |
| **Exponential Search** | O(log n) | O(1) | Yes | Unbounded or infinite arrays |
| **Interpolation Search** | O(log log n) avg | O(1) | Yes | Uniformly distributed sorted data |
| **Ternary Search** | O(log₃ n) | O(1) | Yes | Sorted array, fewer comparisons |

### Example: Binary Search Implementation

```java
public class BinarySearch {
    /**
     * Iterative binary search implementation
     * 
     * @param arr Sorted array to search
     * @param target Element to find
     * @return Index of target, or -1 if not found
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoid integer overflow
            
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1; // Search right half
            } else {
                right = mid - 1; // Search left half
            }
        }
        
        return -1; // Target not found
    }
    
    /**
     * Recursive binary search implementation
     */
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }
}
```

### When to Use
- **Linear Search**: Small datasets, unsorted data, or linked lists
- **Binary Search**: Sorted data with frequent lookups, optimal when data access is cheap
- **Jump Search**: Middle ground between linear and binary, good for systems with block loading
- **Interpolation Search**: Uniformly distributed sorted data for better average performance

### Detailed Guides
- [Searching Algorithms - Fundamentals](./01-Searching/README.md)
- [Advanced Searching Techniques](./02-Searching/README.md)

---

## Recursion

### Definition
Recursion is a programming technique where a function calls itself directly or indirectly to solve a problem by breaking it into smaller, similar subproblems. Each recursive call should progress toward a base case to avoid infinite loops.

### Key Components
1. **Base Case**: The condition that stops recursion
2. **Recursive Case**: The function calling itself with modified parameters
3. **Progress**: Each recursive call must move toward the base case

### Advantages and Disadvantages

| Aspect | Advantages | Disadvantages |
|--------|------------|-----------------|
| **Code Clarity** | More readable for tree/graph problems | Can be harder to understand for beginners |
| **Implementation** | Natural fit for divide-and-conquer | Stack overflow risk for deep recursion |
| **Space** | Implicit call stack | Additional memory per call (O(n) stack space) |
| **Performance** | Can be optimized with memoization | Slower than iteration due to function calls |

### Example: Factorial and Fibonacci

```java
public class RecursionExamples {
    /**
     * Calculate factorial of n
     * Time: O(n), Space: O(n) due to call stack
     */
    public static long factorial(int n) {
        // Base case
        if (n <= 1) {
            return 1;
        }
        // Recursive case
        return n * factorial(n - 1);
    }
    
    /**
     * Fibonacci - demonstrates inefficient recursion
     * Time: O(2^n) - exponential!
     */
    public static long fibonacciNaive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciNaive(n - 1) + fibonacciNaive(n - 2);
    }
    
    /**
     * Fibonacci with memoization - optimized recursion
     * Time: O(n), Space: O(n)
     */
    public static long fibonacciMemo(int n, long[] memo) {
        if (n <= 1) {
            return n;
        }
        
        if (memo[n] != 0) {
            return memo[n]; // Return cached result
        }
        
        memo[n] = fibonacciMemo(n - 1, memo) + fibonacciMemo(n - 2, memo);
        return memo[n];
    }
    
    /**
     * Binary search using recursion
     * Time: O(log n), Space: O(log n) for call stack
     */
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }
}
```

### Recursion vs Iteration
- Use **recursion** for: Trees, graphs, divide-and-conquer, backtracking problems
- Use **iteration** for: Linear problems, when minimizing space is critical, deeply nested operations

### Detailed Guide
- [Recursion Fundamentals and Advanced Techniques](./03-Recursion/README.md)

---

## Dynamic Programming

### Definition
Dynamic Programming (DP) is an algorithmic technique that solves complex problems by breaking them into overlapping subproblems and storing results to avoid redundant computations. It works optimally on problems exhibiting optimal substructure and overlapping subproblems.

### Key Principles
1. **Optimal Substructure**: Solution to problem depends on solutions to subproblems
2. **Overlapping Subproblems**: Same subproblems recur multiple times
3. **Memoization**: Cache results of expensive function calls
4. **Tabulation**: Build solution bottom-up using a table

### Common DP Problems

| Problem | Approach | Time | Space | Difficulty |
|---------|----------|------|-------|------------|
| **Fibonacci** | Memoization | O(n) | O(n) | Easy |
| **0/1 Knapsack** | Tabulation | O(nW) | O(nW) | Medium |
| **Longest Common Subsequence** | Tabulation | O(mn) | O(mn) | Medium |
| **Longest Increasing Subsequence** | Tabulation | O(n²) | O(n) | Medium |
| **Coin Change** | Tabulation | O(nA) | O(A) | Medium |
| **Edit Distance** | Tabulation | O(mn) | O(mn) | Hard |
| **Matrix Chain Multiplication** | Tabulation | O(n³) | O(n²) | Hard |

### Example: 0/1 Knapsack Problem

```java
public class Knapsack {
    /**
     * 0/1 Knapsack using Dynamic Programming (Tabulation)
     * 
     * @param weights Array of item weights
     * @param values Array of item values
     * @param capacity Maximum weight capacity
     * @return Maximum value that can be obtained
     * 
     * Time: O(n * W), Space: O(n * W)
     */
    public static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        
        // dp[i][w] = max value using first i items with capacity w
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Build table bottom-up
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                // Don't take item i-1
                dp[i][w] = dp[i - 1][w];
                
                // Take item i-1 if it fits
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        dp[i][w],
                        values[i - 1] + dp[i - 1][w - weights[i - 1]]
                    );
                }
            }
        }
        
        return dp[n][capacity];
    }
    
    /**
     * Space-optimized 0/1 Knapsack
     * Time: O(n * W), Space: O(W)
     */
    public static int knapsackOptimized(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        
        // Process each item
        for (int i = 0; i < weights.length; i++) {
            // Traverse from right to left to avoid using same item twice
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
            }
        }
        
        return dp[capacity];
    }
}
```

### When to Use DP
- Optimization problems where you need to find minimum/maximum value
- Problems with overlapping subproblems
- When greedy approach doesn't guarantee optimal solution
- Sequence and interval problems (substring, subarray)

### Detailed Guide
- [Dynamic Programming - Complete Coverage](./04-Dynamic-Programming/README.md)

---

## Greedy Algorithms

### Definition
Greedy algorithms build solutions incrementally, always choosing the locally optimal option at each step without reconsidering previous choices. They're efficient but don't always produce globally optimal solutions.

### Characteristics
- **Local Optimization**: Make the best choice at each step
- **No Backtracking**: Once a choice is made, it's final
- **Efficiency**: Generally O(n log n) or better
- **Risk**: May not find globally optimal solution

### When Greedy Works

| Problem | Algorithm | Correctness |
|---------|-----------|-------------|
| **Activity Selection** | Sort by end time, greedily select | Always optimal |
| **Huffman Coding** | Always merge smallest frequencies | Always optimal |
| **Kruskal's MST** | Add minimum weight edges | Always optimal |
| **Dijkstra's Shortest Path** | Always expand smallest distance | Always optimal (no negative edges) |
| **Fractional Knapsack** | Take highest value-to-weight ratio | Always optimal |
| **0/1 Knapsack** | Greedy by ratio | NOT always optimal |
| **Coin Change (specific sets)** | Use largest coin repeatedly | Often not optimal |

### Example: Activity Selection Problem

```java
import java.util.Arrays;
import java.util.Comparator;

public class ActivitySelection {
    
    static class Activity {
        int start;
        int end;
        
        Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    
    /**
     * Select maximum non-overlapping activities
     * Greedy approach: Sort by end time, always select activity ending earliest
     * 
     * Time: O(n log n) due to sorting
     * Space: O(1) excluding output
     */
    public static void selectActivities(Activity[] activities) {
        // Sort activities by end time
        Arrays.sort(activities, Comparator.comparingInt(a -> a.end));
        
        System.out.println("Selected activities:");
        System.out.println("(" + activities[0].start + "," + activities[0].end + ")");
        
        int lastEndTime = activities[0].end;
        
        // Select remaining compatible activities
        for (int i = 1; i < activities.length; i++) {
            if (activities[i].start >= lastEndTime) {
                System.out.println("(" + activities[i].start + "," + activities[i].end + ")");
                lastEndTime = activities[i].end;
            }
        }
    }
}
```

### When to Use Greedy
- When greedy choice property and optimal substructure are proven
- Problems like activity selection, Huffman coding, MST
- When quick approximate solutions are acceptable
- Optimization problems where greedy approach guarantees optimality

### When NOT to Use
- 0/1 Knapsack (use DP instead)
- General shortest path with negative edges
- When optimal substructure is not proven

### Detailed Guide
- [Greedy Algorithms - Theory and Practice](./05-Greedy/README.md)
- [Greedy Algorithms - Additional Resources](./05-Greedy-Algorithms/README.md)

---

## Backtracking

### Definition
Backtracking is a systematic search technique that explores all possible solutions by building candidates incrementally and abandoning (backtracking) when a partial solution cannot lead to a valid complete solution.

### Key Characteristics
- **Incremental Building**: Build solutions step by step
- **Pruning**: Eliminate branches that cannot lead to solution
- **Exhaustive Search**: Explores all viable paths
- **Recursive Nature**: Naturally implemented recursively

### Common Backtracking Problems

| Problem | Constraints | Complexity | Space |
|---------|-------------|-----------|-------|
| **N-Queens** | No two queens attack | O(n!) | O(n) |
| **Sudoku Solver** | Each cell satisfies rules | O(9^(81)) avg | O(1) |
| **Permutations** | All arrangements | O(n!) | O(n) |
| **Combinations** | All subsets | O(2^n) | O(n) |
| **Word Search** | Path in grid | O(m×n×4^L) | O(L) |
| **Rat in Maze** | Find path to destination | O(4^(mn)) | O(mn) |

### Example: N-Queens Problem

```java
public class NQueens {
    private static final int N = 4;
    private int[][] board = new int[N][N];
    private int solutionCount = 0;
    
    /**
     * Solve N-Queens problem
     * Place N queens on N×N board so no two queens attack each other
     */
    public void solveNQueens() {
        // Initialize board (0 = empty, 1 = queen)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }
        
        // Start solving from first row
        if (backtrack(0)) {
            System.out.println("Found " + solutionCount + " solution(s)");
        }
    }
    
    /**
     * Backtracking function
     * @param row Current row to place queen
     */
    private boolean backtrack(int row) {
        // Base case: all queens placed successfully
        if (row == N) {
            printBoard();
            solutionCount++;
            return true;
        }
        
        // Try placing queen in each column of current row
        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                // Place queen
                board[row][col] = 1;
                
                // Move to next row
                backtrack(row + 1);
                
                // Backtrack: remove queen
                board[row][col] = 0;
            }
        }
        
        return false;
    }
    
    /**
     * Check if placing queen at (row, col) is safe
     */
    private boolean isSafe(int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        
        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        
        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) return false;
        }
        
        return true;
    }
    
    private void printBoard() {
        System.out.println("\nSolution " + (solutionCount + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
```

### Backtracking Template

```java
public class BacktrackingTemplate {
    public void backtrack(List<Integer> candidates, List<List<Integer>> result, List<Integer> current) {
        // Base case: check if current is a valid solution
        if (isValid(current)) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Explore all possibilities
        for (int candidate : candidates) {
            // Check constraints
            if (isValid(current, candidate)) {
                // Choose
                current.add(candidate);
                
                // Explore
                backtrack(candidates, result, current);
                
                // Unchoose (backtrack)
                current.remove(current.size() - 1);
            }
        }
    }
    
    private boolean isValid(List<Integer> current) {
        // Check if current partial solution is complete and valid
        return false;
    }
    
    private boolean isValid(List<Integer> current, int candidate) {
        // Check if adding candidate maintains validity
        return false;
    }
}
```

### When to Use Backtracking
- Constraint satisfaction problems (N-Queens, Sudoku)
- Permutation and combination generation
- Maze solving and path finding
- Puzzle solving
- Any problem requiring exhaustive search with pruning

### Detailed Guide
- [Backtracking - Complete Guide](./06-Backtracking/README.md)

---

## Additional Topics

This repository also covers specialized algorithm categories for comprehensive learning:

### [Graph Algorithms](./07-Graph-Algorithms/README.md)
- BFS/DFS traversal
- Shortest path (Dijkstra, Bellman-Ford)
- Minimum spanning tree (Kruskal, Prim)
- Topological sorting
- Cycle detection
- Strongly connected components

### [String Algorithms](./08-String-Algorithms/README.md)
- Pattern matching (KMP, Boyer-Moore)
- String hashing
- Trie data structure
- Suffix arrays
- Longest common substring/subsequence
- String compression

### [Mathematical Algorithms](./09-Mathematical-Algorithms/README.md)
- GCD and LCM
- Prime number generation
- Modular arithmetic
- Combinatorics
- Number theory problems

### [Bit Manipulation](./10-Bit-Manipulation/README.md)
- Bit operations (AND, OR, XOR)
- Bit shifting
- Bit counting
- Power of two problems
- Subset generation

---

## When to Use Each Approach

### Decision Framework

```
Problem Type: Need to sort?
├─ YES → Choose Sorting Algorithm
│   ├─ Guaranteed O(n log n) needed? → Merge Sort
│   ├─ Space constrained? → Quick Sort or Heap Sort
│   ├─ Nearly sorted data? → Insertion Sort
│   └─ Integer range limited? → Counting/Radix Sort
│
Problem Type: Need to find element?
├─ YES → Choose Searching Algorithm
│   ├─ Data sorted? → Binary Search
│   ├─ Specific distribution? → Interpolation Search
│   └─ Large dataset? → Jump Search
│
Problem Type: Break into similar subproblems?
├─ YES → Consider Recursion or Divide-and-Conquer
│   ├─ Overlapping subproblems? → Dynamic Programming
│   ├─ No optimal structure? → Backtracking
│   └─ Optimization problem? → Greedy or DP
│
Problem Type: Optimization problem?
├─ Greedy choice property? → Greedy Algorithm
├─ Overlapping subproblems? → Dynamic Programming
└─ Need all solutions? → Backtracking
```

### Quick Reference Table

| Problem Type | Algorithm | Time | When to Use |
|--------------|-----------|------|------------|
| Sorting | Quick Sort | O(n log n) avg | General purpose |
| Sorting | Merge Sort | O(n log n) | Guaranteed O(n log n) needed |
| Searching | Binary Search | O(log n) | Sorted data, frequent lookups |
| Subproblems | Recursion | Varies | Natural problem decomposition |
| Optimization | DP | Varies | Overlapping subproblems |
| Greedy Choice | Greedy | O(n log n) | Activity selection, MST |
| Exhaustive | Backtracking | Exponential | Constraint satisfaction |

---

## Interview Preparation

### Must-Know Algorithms

**Easy Level** (30 minutes)
1. Bubble/Insertion Sort
2. Linear/Binary Search
3. Simple Recursion (Factorial, Fibonacci)
4. Basic Backtracking (Permutations)

**Medium Level** (1 hour)
1. Quick Sort / Merge Sort
2. BFS/DFS (Graph Algorithms)
3. Dynamic Programming (Coin Change, LCS)
4. Greedy (Activity Selection)
5. More complex Backtracking (N-Queens)

**Hard Level** (2+ hours)
1. Advanced DP (Edit Distance, Matrix Chain)
2. Graph Algorithms (Dijkstra, Kruskal)
3. String Matching (KMP)
4. Complex Backtracking (Sudoku Solver)

### Common Interview Questions by Category

#### Sorting
- "Implement QuickSort and explain pivot selection"
- "When would you use Merge Sort over Quick Sort?"
- "Sort array with limited extra space"
- "Find k-th largest element efficiently"

#### Searching
- "Find element in rotated sorted array"
- "Search in 2D matrix"
- "First and last occurrence of element"
- "Peak element in array"

#### Recursion & DP
- "Climb stairs with n steps, 1 or 2 at a time"
- "0/1 Knapsack problem"
- "Longest increasing subsequence"
- "Edit distance between two strings"
- "Coin change with minimum coins"

#### Greedy
- "Assign cookies to children optimally"
- "Jumping game (can reach end?)"
- "Video stitching problem"
- "Merge intervals"

#### Backtracking
- "Generate all permutations"
- "Solve Sudoku"
- "Word search in grid"
- "Combination sum problem"

#### Graph Algorithms
- "Clone graph with deep copy"
- "Number of islands"
- "Shortest path in unweighted graph"
- "Course schedule (topological sort)"

### Preparation Checklist

- [ ] Master time/space complexity analysis
- [ ] Implement each algorithm from scratch
- [ ] Understand trade-offs between approaches
- [ ] Practice recognizing problem types
- [ ] Code multiple solutions for same problem
- [ ] Explain algorithms to someone else
- [ ] Optimize solutions iteratively
- [ ] Practice under time pressure

### Time Complexity Cheat Sheet

```
O(1)      - Constant: Direct access, simple operations
O(log n)  - Logarithmic: Binary search
O(n)      - Linear: Single loop, linear search
O(n log n) - Linearithmic: Merge sort, Quick sort average
O(n²)     - Quadratic: Nested loops, Bubble sort
O(n³)     - Cubic: Triple nested loops, Matrix multiplication
O(2^n)    - Exponential: Subset generation, Backtracking
O(n!)     - Factorial: Permutation generation
```

### Resources for Further Learning

1. **Books**
   - "Introduction to Algorithms" by CLRS
   - "Algorithm Design Manual" by Skiena
   - "Cracking the Coding Interview" by McDowell

2. **Online Platforms**
   - LeetCode: Sorted problems by algorithm type
   - HackerRank: Algorithm courses and challenges
   - GeeksforGeeks: Detailed algorithm explanations
   - InterviewBit: Interview-focused problems

3. **Practice**
   - Solve 20-30 problems per algorithm type
   - Code multiple solutions for same problem
   - Explain solutions aloud
   - Review optimal solutions after solving

---

## Next Steps

1. Choose a category from the navigation above
2. Study the detailed content in each subdirectory
3. Implement algorithms in Java with comments
4. Test with various input cases
5. Analyze time and space complexity
6. Solve related interview problems
7. Review and optimize your solutions

---

## Quick Navigation

| Topic | Path | Difficulty |
|-------|------|-----------|
| Sorting Fundamentals | [01-Sorting](./01-Sorting/README.md) | Beginner |
| Advanced Sorting | [02-Sorting](./02-Sorting/README.md) | Intermediate |
| Basic Searching | [01-Searching](./01-Searching/README.md) | Beginner |
| Advanced Searching | [02-Searching](./02-Searching/README.md) | Intermediate |
| Recursion Guide | [03-Recursion](./03-Recursion/README.md) | Beginner to Intermediate |
| Dynamic Programming | [04-Dynamic-Programming](./04-Dynamic-Programming/README.md) | Intermediate to Advanced |
| Greedy Algorithms | [05-Greedy](./05-Greedy/README.md) and [05-Greedy-Algorithms](./05-Greedy-Algorithms/README.md) | Intermediate |
| Backtracking | [06-Backtracking](./06-Backtracking/README.md) | Intermediate to Advanced |
| Graph Algorithms | [07-Graph-Algorithms](./07-Graph-Algorithms/README.md) | Advanced |
| String Algorithms | [08-String-Algorithms](./08-String-Algorithms/README.md) | Advanced |
| Mathematical Algorithms | [09-Mathematical-Algorithms](./09-Mathematical-Algorithms/README.md) | Intermediate |
| Bit Manipulation | [10-Bit-Manipulation](./10-Bit-Manipulation/README.md) | Intermediate |

---

## Summary

Algorithms are the foundation of efficient problem-solving. This comprehensive guide provides:

✅ Clear definitions and explanations  
✅ Professional theoretical foundations  
✅ Practical Java code examples  
✅ Comparison tables for decision-making  
✅ Time and space complexity analysis  
✅ Interview preparation guidance  
✅ Links to detailed subcategories for deeper learning  

Start with fundamentals (Sorting & Searching), progress through core paradigms (DP, Recursion), and master advanced techniques (Backtracking, Graphs). Regular practice and deep understanding of trade-offs will make you proficient in algorithmic problem-solving.

**Happy Learning! 🚀**
