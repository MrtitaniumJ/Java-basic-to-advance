# Subsets, Permutations, and Combinations Pattern

## Table of Contents
- [Overview](#overview)
- [Core Concepts](#core-concepts)
- [Pattern Identification](#pattern-identification)
- [Approaches](#approaches)
- [Complete Java Implementation](#complete-java-implementation)
- [Complexity Analysis](#complexity-analysis)
- [Recursion Trees](#recursion-trees)
- [Common Variations](#common-variations)
- [Practice Problems](#practice-problems)

## Overview

The Subsets, Permutations, and Combinations pattern is a fundamental algorithmic pattern that deals with generating all possible configurations from a given set of elements. This pattern is extensively used in combinatorial problems, constraint satisfaction, and exhaustive search scenarios. Understanding this pattern is crucial for solving problems that require exploring all possibilities systematically.

**Key Applications:**
- Generating power sets (all subsets)
- Finding all permutations of elements
- Creating combinations of specific sizes
- Solving puzzles and constraint problems
- Backtracking algorithms
- Game state exploration

## Core Concepts

### 1. Subsets (Power Set)
A **subset** is any combination of elements from the original set, including the empty set and the set itself. For a set with `n` elements, there are `2^n` possible subsets.

**Example:** For set `[1, 2, 3]`
```
[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
```

### 2. Permutations
A **permutation** is an arrangement of elements where order matters. For `n` distinct elements, there are `n!` permutations.

**Example:** For set `[1, 2, 3]`
```
[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
```

### 3. Combinations
A **combination** is a selection of elements where order does not matter. For choosing `k` elements from `n` elements: `C(n,k) = n!/(k!(n-k)!)`

**Example:** Combinations of size 2 from `[1, 2, 3]`
```
[1,2], [1,3], [2,3]
```

## Pattern Identification

**Use this pattern when:**
- Problem asks for "all possible" configurations
- Need to explore every combination/permutation
- Problem involves generating power sets
- Constraint satisfaction problems
- Problems with "find all ways to..." phrasing
- Backtracking is mentioned or implied

**Keywords to look for:**
- All subsets
- All permutations
- All combinations
- Generate all
- Find every possible
- Enumerate all

## Approaches

### 1. Backtracking Approach
Backtracking builds solutions incrementally by exploring all possibilities through recursive decision trees. At each step, we make a choice, explore its consequences, and backtrack if needed.

**Advantages:**
- Intuitive and easy to understand
- Memory efficient (only stores current path)
- Can be easily modified for constraints
- Works for both subsets and permutations

### 2. Bit Manipulation Approach
Uses binary representation where each bit represents inclusion/exclusion of an element. Only applicable for subsets.

**Advantages:**
- Fast for subsets generation
- No recursion overhead
- Compact implementation
- Good for small sets (up to 20-30 elements)

## Complete Java Implementation

```java
import java.util.*;

/**
 * Comprehensive implementation of Subsets, Permutations, and Combinations patterns
 * Demonstrates multiple approaches with detailed explanations
 */
public class SubsetsPermutationsCombinations {
    
    // ============= SUBSETS IMPLEMENTATIONS =============
    
    /**
     * Generate all subsets using backtracking approach
     * Time Complexity: O(2^n * n) - 2^n subsets, each taking O(n) to copy
     * Space Complexity: O(n) - recursion depth
     */
    public static List<List<Integer>> subsetsBacktracking(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackSubsets(int[] nums, int start, 
                                        List<Integer> current, 
                                        List<List<Integer>> result) {
        // Add current subset to result (base case - always valid)
        result.add(new ArrayList<>(current));
        
        // Explore all possibilities by including elements from start index onwards
        for (int i = start; i < nums.length; i++) {
            // Include current element
            current.add(nums[i]);
            
            // Recurse with next index
            backtrackSubsets(nums, i + 1, current, result);
            
            // Backtrack - remove current element to explore other branches
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Generate all subsets using bit manipulation
     * Each subset corresponds to a binary number from 0 to 2^n - 1
     * Time Complexity: O(2^n * n)
     * Space Complexity: O(1) excluding output
     */
    public static List<List<Integer>> subsetsBitManipulation(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n; // 2^n subsets
        
        // Iterate through all possible binary representations
        for (int i = 0; i < totalSubsets; i++) {
            List<Integer> subset = new ArrayList<>();
            
            // Check each bit position
            for (int j = 0; j < n; j++) {
                // If j-th bit is set, include nums[j] in subset
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }
        return result;
    }
    
    /**
     * Generate all unique subsets when array contains duplicates
     * Time Complexity: O(2^n * n)
     * Space Complexity: O(n)
     */
    public static List<List<Integer>> subsetsWithDuplicates(int[] nums) {
        Arrays.sort(nums); // Sort to handle duplicates
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsetsUnique(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackSubsetsUnique(int[] nums, int start,
                                              List<Integer> current,
                                              List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        
        for (int i = start; i < nums.length; i++) {
            // Skip duplicates: if current element equals previous and we're not
            // at the start position, skip to avoid duplicate subsets
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            
            current.add(nums[i]);
            backtrackSubsetsUnique(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // ============= PERMUTATIONS IMPLEMENTATIONS =============
    
    /**
     * Generate all permutations using backtracking
     * Time Complexity: O(n! * n) - n! permutations, each taking O(n) to copy
     * Space Complexity: O(n) - recursion depth
     */
    public static List<List<Integer>> permutations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermutations(nums, new ArrayList<>(), new boolean[nums.length], result);
        return result;
    }
    
    private static void backtrackPermutations(int[] nums, 
                                            List<Integer> current,
                                            boolean[] used,
                                            List<List<Integer>> result) {
        // Base case: permutation is complete
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try adding each unused element
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue; // Skip if already used
            
            // Choose
            current.add(nums[i]);
            used[i] = true;
            
            // Explore
            backtrackPermutations(nums, current, used, result);
            
            // Unchoose (backtrack)
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    /**
     * Generate all unique permutations when array contains duplicates
     * Time Complexity: O(n! * n) worst case, better with duplicates
     * Space Complexity: O(n)
     */
    public static List<List<Integer>> permutationsUnique(int[] nums) {
        Arrays.sort(nums); // Sort to handle duplicates
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermutationsUnique(nums, new ArrayList<>(), 
                                   new boolean[nums.length], result);
        return result;
    }
    
    private static void backtrackPermutationsUnique(int[] nums,
                                                   List<Integer> current,
                                                   boolean[] used,
                                                   List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            
            // Skip duplicates: if current element equals previous and previous
            // hasn't been used yet, skip to avoid duplicate permutations
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            
            current.add(nums[i]);
            used[i] = true;
            backtrackPermutationsUnique(nums, current, used, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    // ============= COMBINATIONS IMPLEMENTATIONS =============
    
    /**
     * Generate all combinations of k elements from n elements
     * Time Complexity: O(C(n,k) * k) where C(n,k) = n!/(k!(n-k)!)
     * Space Complexity: O(k) - recursion depth
     */
    public static List<List<Integer>> combinations(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombinations(1, n, k, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackCombinations(int start, int n, int k,
                                            List<Integer> current,
                                            List<List<Integer>> result) {
        // Base case: combination is complete
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Pruning: if remaining elements insufficient, return early
        int need = k - current.size(); // elements still needed
        int available = n - start + 1; // elements available
        if (available < need) return;
        
        // Try each number from start to n
        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrackCombinations(i + 1, n, k, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Generate combinations from an array with specific size k
     * Time Complexity: O(C(n,k) * k)
     * Space Complexity: O(k)
     */
    public static List<List<Integer>> combinationsFromArray(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackArrayCombinations(nums, 0, k, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackArrayCombinations(int[] nums, int start, int k,
                                                  List<Integer> current,
                                                  List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Pruning optimization
        int need = k - current.size();
        int available = nums.length - start;
        if (available < need) return;
        
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrackArrayCombinations(nums, i + 1, k, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // ============= UTILITY AND TESTING METHODS =============
    
    /**
     * Print all subsets with formatting
     */
    public static void printSubsets(List<List<Integer>> subsets) {
        System.out.println("Total subsets: " + subsets.size());
        for (List<Integer> subset : subsets) {
            System.out.println(subset);
        }
    }
    
    /**
     * Main method with comprehensive examples and testing
     */
    public static void main(String[] args) {
        System.out.println("=== SUBSETS PATTERN DEMONSTRATIONS ===\n");
        
        // Example 1: Basic Subsets
        System.out.println("1. SUBSETS using Backtracking");
        int[] nums1 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums1));
        List<List<Integer>> subsets1 = subsetsBacktracking(nums1);
        printSubsets(subsets1);
        System.out.println();
        
        // Example 2: Subsets using Bit Manipulation
        System.out.println("2. SUBSETS using Bit Manipulation");
        System.out.println("Input: " + Arrays.toString(nums1));
        List<List<Integer>> subsets2 = subsetsBitManipulation(nums1);
        printSubsets(subsets2);
        System.out.println();
        
        // Example 3: Subsets with Duplicates
        System.out.println("3. SUBSETS with Duplicates");
        int[] nums2 = {1, 2, 2};
        System.out.println("Input: " + Arrays.toString(nums2));
        List<List<Integer>> subsets3 = subsetsWithDuplicates(nums2);
        printSubsets(subsets3);
        System.out.println();
        
        // Example 4: Permutations
        System.out.println("4. PERMUTATIONS");
        int[] nums3 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums3));
        List<List<Integer>> perms1 = permutations(nums3);
        System.out.println("Total permutations: " + perms1.size());
        for (List<Integer> perm : perms1) {
            System.out.println(perm);
        }
        System.out.println();
        
        // Example 5: Unique Permutations with Duplicates
        System.out.println("5. UNIQUE PERMUTATIONS with Duplicates");
        int[] nums4 = {1, 1, 2};
        System.out.println("Input: " + Arrays.toString(nums4));
        List<List<Integer>> perms2 = permutationsUnique(nums4);
        System.out.println("Total unique permutations: " + perms2.size());
        for (List<Integer> perm : perms2) {
            System.out.println(perm);
        }
        System.out.println();
        
        // Example 6: Combinations
        System.out.println("6. COMBINATIONS C(4, 2)");
        List<List<Integer>> combs1 = combinations(4, 2);
        System.out.println("Total combinations: " + combs1.size());
        for (List<Integer> comb : combs1) {
            System.out.println(comb);
        }
        System.out.println();
        
        // Example 7: Combinations from Array
        System.out.println("7. COMBINATIONS from Array");
        int[] nums5 = {1, 2, 3, 4};
        int k = 2;
        System.out.println("Input: " + Arrays.toString(nums5) + ", k=" + k);
        List<List<Integer>> combs2 = combinationsFromArray(nums5, k);
        System.out.println("Total combinations: " + combs2.size());
        for (List<Integer> comb : combs2) {
            System.out.println(comb);
        }
        System.out.println();
        
        // Performance comparison
        System.out.println("=== PERFORMANCE COMPARISON ===");
        int[] perfTest = {1, 2, 3, 4, 5};
        
        long start = System.nanoTime();
        List<List<Integer>> bt = subsetsBacktracking(perfTest);
        long backtrackTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        List<List<Integer>> bm = subsetsBitManipulation(perfTest);
        long bitTime = System.nanoTime() - start;
        
        System.out.println("Array size: " + perfTest.length);
        System.out.println("Subsets count: " + bt.size());
        System.out.println("Backtracking time: " + backtrackTime + " ns");
        System.out.println("Bit manipulation time: " + bitTime + " ns");
        System.out.println("Ratio: " + (double)backtrackTime/bitTime + "x");
    }
}
```

## Complexity Analysis

### Subsets Generation

**Time Complexity:**
- **Backtracking:** O(2^n × n)
  - 2^n subsets to generate
  - Each subset takes O(n) time to copy to result
- **Bit Manipulation:** O(2^n × n)
  - Same reasoning as backtracking
  - Though constant factors may be better

**Space Complexity:**
- **Backtracking:** O(n)
  - Recursion stack depth is O(n)
  - Current subset maintained in recursion
- **Bit Manipulation:** O(1)
  - No recursion, only iteration
  - Excluding output space

### Permutations Generation

**Time Complexity:** O(n! × n)
- n! permutations to generate
- Each permutation takes O(n) time to copy
- Factorial growth makes this expensive for large n

**Space Complexity:** O(n)
- Recursion stack depth is O(n)
- Boolean array for tracking used elements: O(n)

### Combinations Generation

**Time Complexity:** O(C(n,k) × k)
- C(n,k) = n!/(k!(n-k)!) combinations
- Each combination takes O(k) time to copy
- With pruning, we can reduce unnecessary branches

**Space Complexity:** O(k)
- Recursion stack depth is at most k
- Current combination size is k

## Recursion Trees

### Subsets Recursion Tree for [1, 2, 3]

```
                         []
                    /    |    \
                [1]     [2]    [3]
              /   \      |
          [1,2] [1,3]  [2,3]
            |
         [1,2,3]

Decision at each level: Include or exclude current element
Total nodes: 2^n = 8 (including root)
Height: n = 3
```

**Detailed Trace:**
```
Level 0: []                    → Add to result
Level 1: Add 1 → [1]          → Add to result
Level 2: Add 2 → [1,2]        → Add to result  
Level 3: Add 3 → [1,2,3]      → Add to result
         Backtrack to [1,2]
         Backtrack to [1]
Level 2: Add 3 → [1,3]        → Add to result
         Backtrack to []
Level 1: Add 2 → [2]          → Add to result
Level 2: Add 3 → [2,3]        → Add to result
         Backtrack to []
Level 1: Add 3 → [3]          → Add to result
```

### Permutations Recursion Tree for [1, 2, 3]

```
                        []
            /           |           \
          [1]          [2]          [3]
        /    \        /   \        /    \
    [1,2]  [1,3]  [2,1] [2,3]  [3,1]  [3,2]
      |      |      |     |      |      |
   [1,2,3][1,3,2][2,1,3][2,3,1][3,1,2][3,2,1]

At each level, choose from remaining unused elements
Total leaf nodes: n! = 6
Height: n = 3
```

### Combinations Recursion Tree for C(4, 2)

```
                        []
          /      |       |       \
        [1]     [2]     [3]     [4]
       / | \    / \      |
   [1,2][1,3][1,4][2,3][2,4][3,4]
   
Only combinations of size k=2 are added to result
Pruning prevents exploring paths that can't reach size k
Total combinations: C(4,2) = 6
```

## Common Variations

### 1. Subsets of Specific Size
Generate all subsets of exactly size k - this is equivalent to combinations.

### 2. Permutations with Repetition
When elements can be reused, remove the `used` array check.

### 3. String Permutations
Convert string to character array and apply permutation algorithm, then convert back.

### 4. K-Permutations
Generate permutations of size k from n elements (like combinations but order matters).

### 5. Palindrome Permutations
Generate only permutations that form palindromes - requires preprocessing.

### 6. Next Permutation
Find the lexicographically next permutation in O(n) time without generating all.

## Practice Problems

### Beginner Level
1. **Power Set** - Generate all subsets of a set
2. **Combinations** - Generate all k-size combinations from n elements
3. **Letter Case Permutation** - Generate strings by changing case

### Intermediate Level
4. **Subsets II** - Subsets with duplicate elements
5. **Permutations II** - Unique permutations with duplicates
6. **Combination Sum** - Find combinations that sum to target
7. **Generate Parentheses** - Generate all valid parentheses combinations

### Advanced Level
8. **N-Queens** - Place N queens on N×N board (uses backtracking)
9. **Sudoku Solver** - Solve Sudoku puzzle (backtracking with constraints)
10. **Word Search II** - Find all words in board (backtracking + Trie)
11. **Expression Add Operators** - Generate expressions evaluating to target

## Key Takeaways

1. **Pattern Recognition:** Look for "all possible", "generate all", "enumerate" keywords
2. **Choose Approach:** Backtracking for flexibility, bit manipulation for speed with subsets
3. **Handle Duplicates:** Sort first, then skip consecutive duplicates with proper conditions
4. **Optimize:** Use pruning to avoid exploring impossible branches
5. **Space Trade-off:** Backtracking uses less space but involves recursion overhead
6. **Complexity Awareness:** These algorithms have exponential/factorial time complexity
7. **Testing:** Always test with edge cases (empty set, single element, duplicates)

---

*Master these patterns through practice - they form the foundation for many complex algorithmic problems!*
