# Dynamic Programming: Complete Guide

## Table of Contents
1. [Fundamentals](#fundamentals)
2. [Core Concepts](#core-concepts)
3. [Approaches](#approaches)
4. [Classic Problems](#classic-problems)
5. [Implementation Examples](#implementation-examples)
6. [Complexity Analysis](#complexity-analysis)
7. [When to Use DP](#when-to-use-dp)
8. [Common Patterns](#common-patterns)

---

## Fundamentals

Dynamic Programming (DP) is a powerful algorithmic technique that solves complex problems by breaking them down into simpler overlapping subproblems. Rather than solving subproblems repeatedly, DP stores their results and reuses them—a strategy called **memoization** (top-down) or **tabulation** (bottom-up).

The term "programming" here refers to the tabular method of solving problems, not computer programming. Donald Bellman coined the term in the 1950s while developing optimization algorithms.

### When Does DP Apply?

A problem can be solved with DP if it exhibits two key properties:

1. **Optimal Substructure**: An optimal solution contains optimal solutions to subproblems
2. **Overlapping Subproblems**: The same subproblems are solved multiple times

Problems lacking these properties cannot benefit from dynamic programming.

---

## Core Concepts

### Optimal Substructure

This means the optimal solution to a problem can be constructed from optimal solutions of its subproblems. For example, the shortest path from A to C through B is the shortest A→B plus shortest B→C.

```
Problem(n) = f(Problem(n-1), Problem(n-2), ...)
```

### Overlapping Subproblems

Without overlapping subproblems, recursion alone is sufficient (e.g., binary search). DP becomes beneficial when we solve the same subproblems repeatedly. Consider computing `fib(5)`:

```
                    fib(5)
           /                    \
        fib(4)                fib(3)
       /      \                /      \
    fib(3)   fib(2)         fib(2)   fib(1)
    /    \    /    \        /    \
fib(2) fib(1) fib(1) fib(0) fib(1) fib(0)
```

Notice `fib(3)`, `fib(2)`, and `fib(1)` are computed multiple times. DP eliminates this redundancy.

### State Space Visualization

Every DP problem has a "state space"—the collection of all unique subproblems. For 1D problems (like Fibonacci), the state is a single index. For 2D problems (like Knapsack), the state is a pair (capacity, item index).

```
1D State: dp[n]
2D State: dp[capacity][items]
3D State: dp[string1_index][string2_index][operation_count]
```

---

## Approaches

### Top-Down (Memoization)

Start with the original problem and recursively break it down. Store computed results to avoid recalculation.

**Characteristics:**
- Natural recursive thinking
- Easier to implement from recursion
- Added function call overhead
- Only computes necessary subproblems

```java
Map<Integer, Long> memo = new HashMap<>();

long fibonacci(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    
    long result = fibonacci(n-1) + fibonacci(n-2);
    memo.put(n, result);
    return result;
}
```

### Bottom-Up (Tabulation)

Build solutions iteratively from the smallest subproblems to the original problem. Use tables (arrays) to store results.

**Characteristics:**
- Iterative approach
- No recursion overhead
- More space-efficient than recursive calls
- Must compute all subproblems (sometimes unnecessary)

```java
long fibonacci(int n) {
    if (n <= 1) return n;
    
    long[] dp = new long[n+1];
    dp[0] = 0;
    dp[1] = 1;
    
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n];
}
```

---

## Classic Problems

### 1. Fibonacci Sequence

**Problem**: Find the nth Fibonacci number where F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2)

**Optimal Substructure**: F(n) = F(n-1) + F(n-2)

**Overlapping**: Yes, F(n-1) and F(n-2) share many computations

**Time Complexity**:
- Naive Recursion: O(2^n)
- DP: O(n)
- Space: O(n) for tabulation, O(1) for optimized bottom-up

### 2. 0/1 Knapsack Problem

**Problem**: Given items with weights and values, maximize value within capacity constraint.

**State Definition**: `dp[i][w]` = maximum value using first i items with capacity w

**Recurrence**:
```
dp[i][w] = max(
    dp[i-1][w],           // exclude item i
    dp[i-1][w-weight[i]] + value[i]  // include item i
)
```

**Time**: O(n*W) where n=items, W=capacity

**Space**: O(n*W) or O(W) with space optimization

### 3. Coin Change Problem

**Problem**: Minimum coins needed to make target amount

**Variations**: 
- Find minimum coin count (classic DP)
- Count ways to make amount (combinatorial)

**State**: `dp[amount]` = minimum coins to make this amount

**Recurrence**:
```
dp[amount] = 1 + min(dp[amount - coin] for each coin)
```

**Time**: O(amount * coins)

### 4. Longest Common Subsequence (LCS)

**Problem**: Find longest subsequence common to two strings (not necessarily contiguous)

**State**: `dp[i][j]` = LCS length of first i chars of string1 and first j chars of string2

**Recurrence**:
```
if s1[i-1] == s2[j-1]:
    dp[i][j] = dp[i-1][j-1] + 1
else:
    dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

**Time**: O(m*n) where m, n are string lengths

### 5. Matrix Chain Multiplication

**Problem**: Minimum operations to multiply a chain of matrices

**State**: `dp[i][j]` = minimum multiplications for matrices i through j

**Recurrence**:
```
dp[i][j] = min(
    dp[i][k] + dp[k+1][j] + cost[i][k][j]
) for all i ≤ k < j
```

**Time**: O(n³)

### 6. Edit Distance (Levenshtein Distance)

**Problem**: Minimum operations (insert, delete, replace) to transform one string to another

**State**: `dp[i][j]` = edit distance between first i chars of string1 and first j chars of string2

**Recurrence**:
```
if s1[i-1] == s2[j-1]:
    dp[i][j] = dp[i-1][j-1]
else:
    dp[i][j] = 1 + min(
        dp[i-1][j],      // delete
        dp[i][j-1],      // insert
        dp[i-1][j-1]     // replace
    )
```

**Time**: O(m*n)

---

## Implementation Examples

### Complete Java Implementation Suite

```java
import java.util.*;

public class DynamicProgramming {
    
    // ==================== FIBONACCI ====================
    
    // Top-down (Memoization)
    public long fibonacciMemo(int n, Map<Integer, Long> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        long result = fibonacciMemo(n-1, memo) + fibonacciMemo(n-2, memo);
        memo.put(n, result);
        return result;
    }
    
    // Bottom-up (Tabulation)
    public long fibonacciTab(int n) {
        if (n <= 1) return n;
        
        long[] dp = new long[n+1];
        dp[0] = 0;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
    
    // Space-optimized bottom-up
    public long fibonacciOptimized(int n) {
        if (n <= 1) return n;
        
        long prev2 = 0, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            long current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return prev1;
    }
    
    // ==================== 0/1 KNAPSACK ====================
    
    // Bottom-up tabulation
    public int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n+1][capacity+1];
        
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // Option 1: exclude current item
                dp[i][w] = dp[i-1][w];
                
                // Option 2: include current item (if it fits)
                if (weights[i-1] <= w) {
                    dp[i][w] = Math.max(
                        dp[i][w],
                        dp[i-1][w - weights[i-1]] + values[i-1]
                    );
                }
            }
        }
        return dp[n][capacity];
    }
    
    // Space-optimized knapsack (1D array)
    public int knapsackOptimized(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        
        for (int i = 0; i < weights.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }
    
    // Return items included in optimal solution
    public List<Integer> knapsackWithItems(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n+1][capacity+1];
        
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i-1][w];
                if (weights[i-1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                        dp[i-1][w - weights[i-1]] + values[i-1]);
                }
            }
        }
        
        // Backtrack to find items
        List<Integer> items = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                items.add(i-1);
                w -= weights[i-1];
            }
        }
        return items;
    }
    
    // ==================== COIN CHANGE ====================
    
    // Minimum coins to make amount
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Initialize with impossible value
        dp[0] = 0;
        
        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] = Math.min(dp[a], dp[a - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    // Count ways to make amount
    public int countWays(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1; // One way to make 0: use no coins
        
        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }
        return dp[amount];
    }
    
    // ==================== LONGEST COMMON SUBSEQUENCE ====================
    
    public int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m][n];
    }
    
    // Reconstruct actual LCS string
    public String lcsString(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        
        // Backtrack
        StringBuilder result = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                result.insert(0, s1.charAt(i-1));
                i--;
                j--;
            } else if (dp[i-1][j] > dp[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }
        return result.toString();
    }
    
    // ==================== EDIT DISTANCE ====================
    
    public int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        
        // Base cases
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        Math.min(dp[i-1][j], dp[i][j-1]),  // delete, insert
                        dp[i-1][j-1]  // replace
                    );
                }
            }
        }
        return dp[m][n];
    }
    
    // ==================== MATRIX CHAIN MULTIPLICATION ====================
    
    public int matrixChainOrder(int[] dimensions) {
        int n = dimensions.length - 1;
        int[][] dp = new int[n][n];
        
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k+1][j] + 
                               dimensions[i] * dimensions[k+1] * dimensions[j+1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        return dp[0][n-1];
    }
}
```

---

## Complexity Analysis

| Problem | Time | Space | Optimized Space |
|---------|------|-------|-----------------|
| Fibonacci | O(n) | O(n) | O(1) |
| 0/1 Knapsack | O(n*W) | O(n*W) | O(W) |
| Coin Change | O(amount*coins) | O(amount) | O(amount) |
| LCS | O(m*n) | O(m*n) | O(min(m,n)) |
| Edit Distance | O(m*n) | O(m*n) | O(min(m,n)) |
| Matrix Chain | O(n³) | O(n²) | O(n²) |

---

## When to Use DP

### Red Flags for DP:
1. **Overlapping subproblems**: Same calculations repeated
2. **Optimal substructure**: Problem breaks into independent subproblems
3. **Large input size**: Naive recursion would time out
4. **Integer constraints**: Discrete state space (sizes, counts, weights)

### When NOT to Use DP:
- No overlapping subproblems (simple recursion suffices)
- Continuous/real-valued state space
- Greedy algorithms work better
- Problem requires exploring all possibilities without substructure

---

## Common Patterns

### Pattern 1: Linear Sequences
**Examples**: Fibonacci, house robber, maximum subarray
**State**: `dp[i]` depends on previous elements

### Pattern 2: Two Sequences
**Examples**: LCS, edit distance, regex matching
**State**: `dp[i][j]` depends on both sequence indices

### Pattern 3: Knapsack Variants
**Examples**: 0/1 knapsack, unbounded knapsack, partition equal subset
**State**: `dp[i][capacity]` = optimal value with capacity

### Pattern 4: Game Theory
**Examples**: Optimal play in games
**State**: `dp[game_state]` = optimal score

### Pattern 5: Counting
**Examples**: Ways to climb stairs, coin combinations
**State**: `dp[n]` = number of ways to reach state n

---

## Practice Tips

1. **Start Simple**: Master Fibonacci, then knapsack
2. **Identify State**: Define `dp[i]` or `dp[i][j]` clearly
3. **Find Recurrence**: Write mathematical relationship
4. **Implement Base Cases**: Handle boundary conditions
5. **Test Backtracking**: Reconstruct actual solution
6. **Optimize Space**: Can we reduce 2D to 1D?
7. **Compare Approaches**: Time/space tradeoffs of top-down vs bottom-up

---

## Conclusion

Dynamic Programming is essential for competitive programming and interviews. Mastering these six classic problems and understanding when to apply DP will significantly improve your problem-solving abilities. Focus on identifying optimal substructure and overlapping subproblems first—implementation follows naturally from that analysis.
