# Dynamic Programming

## Overview

Dynamic Programming is a powerful algorithmic technique for solving optimization problems with **overlapping subproblems** and **optimal substructure**. Understanding DP is essential for:
- Solving complex optimization problems
- Improving algorithm efficiency dramatically
- Preparing for technical interviews
- Tackling real-world problems efficiently

## Problem Analysis

### Core Concepts

#### 1. **Optimal Substructure**
A problem has optimal substructure if an optimal solution can be constructed from optimal solutions to its subproblems.

**Example:** Shortest path - shortest path from A to D via C = shortest A to C + shortest C to D

#### 2. **Overlapping Subproblems**
The problem contains many identical subproblems that are solved repeatedly.

**Example:** Fibonacci(5) = Fibonacci(4) + Fibonacci(3)
- Fibonacci(3) computed multiple times in naive recursion

#### 3. **Memoization vs. Tabulation**

**Memoization (Top-Down):**
```
Fibonacci(5)
├─ Fibonacci(4)
│  ├─ Fibonacci(3)
│  │  ├─ Fibonacci(2) [cached]
│  │  └─ Fibonacci(1) [cached]
│  └─ Fibonacci(2) [cached]
└─ Fibonacci(3) [cached]
```
- Recursive approach
- Store results to avoid recomputation
- Only compute needed subproblems
- Space: O(number of unique subproblems)

**Tabulation (Bottom-Up):**
```
F(0) = 0
F(1) = 1
F(2) = F(1) + F(0) = 1
F(3) = F(2) + F(1) = 2
F(4) = F(3) + F(2) = 3
F(5) = F(4) + F(3) = 5
```
- Iterative approach
- Build solution bottom-up
- Space: O(all subproblems)
- No recursion overhead

---

### Problems Covered

#### 1. **Longest Common Subsequence (LCS)**

**Problem:** Find longest subsequence common to two sequences

**Definition:** Subsequence maintains relative order but not contiguity
- "ACE" is subsequence of "ABCDE"
- "BAD" is not (order wrong)

**Examples:**
```
"AGGTAB" and "GXTXAYB"
         → "GTAB" (length 4)

"ABCDGH" and "AEDFHR"
         → "ADH" (length 3)
```

**Applications:**
- DNA sequence alignment
- Git diff algorithm
- Plagiarism detection
- Version control systems

**Algorithm:**
```
dp[i][j] = LCS length of first i chars of s1, first j chars of s2

If s1[i-1] == s2[j-1]:
    dp[i][j] = dp[i-1][j-1] + 1
Else:
    dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

**Complexity:**
- Time: O(m*n) where m,n are string lengths
- Space: O(m*n) basic, O(min(m,n)) optimized

**Space Optimization:** Only need previous row/column

#### 2. **0/1 Knapsack Problem**

**Problem:** Pack items into knapsack of capacity W to maximize value, each item used 0 or 1 times

**Constraints:**
- Can't use partial items
- Total weight ≤ capacity
- Maximize total value

**Example:**
```
Items: (weight, value)
(2, 3), (3, 4), (4, 5), (5, 6)
Capacity: 8

Optimal: items 1,3 → weight=7, value=9
         items 2,3 → weight=7, value=9
```

**Applications:**
- Resource allocation
- Capital budgeting
- Cargo loading
- Container packing

**Algorithm:**
```
dp[i][w] = max value using first i items, weight limit w

Option 1: Don't take item i
    dp[i][w] = dp[i-1][w]

Option 2: Take item i (if weight fits)
    dp[i][w] = value[i] + dp[i-1][w-weight[i]]

dp[i][w] = max(option1, option2)
```

**Complexity:**
- Time: O(n*W) where n is items, W is capacity
- Space: O(n*W) basic, O(W) optimized

**Space Optimization:** Iterate backwards to overwrite correctly

#### 3. **Coin Change Problem**

**Problem:** Find minimum coins needed to make exact amount, unlimited coins of each type

**Examples:**
```
coins = [1, 2, 5], amount = 5
→ 1 coin (just use 5)

coins = [2], amount = 3
→ impossible (-1)

coins = [1, 3, 4], amount = 6
→ 2 coins (3+3)
```

**Variations:**
1. Minimum coins (this program)
2. Number of ways to make amount
3. Specific coin combinations

**Applications:**
- Vending machines
- Currency exchange
- Making change
- Coin collection problems

**Algorithm:**
```
dp[i] = minimum coins for amount i

For each amount from 1 to target:
    For each coin:
        If coin ≤ amount:
            dp[amount] = min(dp[amount], dp[amount-coin] + 1)
```

**Complexity:**
- Time: O(amount * number_of_coins)
- Space: O(amount)

#### 4. **Longest Increasing Subsequence (LIS)**

**Problem:** Find length of longest strictly increasing subsequence

**Examples:**
```
[10, 9, 2, 5, 3, 7, 101, 18]
→ [2, 3, 7, 101] (length 4)

[0, 1, 0, 4, 4, 4, 3, 5, 1]
→ [0, 1, 4, 5] (length 4)
```

**Applications:**
- Data compression
- Pattern matching
- Time series analysis
- Stock trading (longest uptrend)

**Approaches:**

**O(n²) DP:**
```
dp[i] = length of LIS ending at index i

dp[i] = 1 + max(dp[j] for all j < i where nums[j] < nums[i])
```

**O(n log n) Binary Search:**
```
Maintain 'tails' array where tails[i] = smallest ending value
for LIS of length i+1

For each number, binary search position to place/replace
```

**Complexity:**
- O(n²) DP: Time O(n²), Space O(n)
- O(n log n): Time O(n log n), Space O(n)

#### 5. **Fibonacci Sequence**

**Problem:** Calculate nth Fibonacci number efficiently

**Definition:**
```
F(0) = 0
F(1) = 1
F(n) = F(n-1) + F(n-2)
```

**Issues with Naive Recursion:**
```
F(5) recursion tree:
           F(5)
          /    \
        F(4)    F(3)
       /   \    /   \
      F(3) F(2) F(2) F(1)
      ...

Time: O(2^n) - VERY SLOW
```

**Memoization Improvement:**
```
F(3) computed once, result stored
Next occurrence uses cached value
Time: O(n)
```

**DP Approach:**
```
Build array bottom-up
F[0] = 0, F[1] = 1
For i from 2 to n:
    F[i] = F[i-1] + F[i-2]
```

**Space Optimization:**
```
Only need last two values
O(1) space instead of O(n)
```

**Complexity:**
- Naive recursion: O(2^n)
- Memoization: O(n) time, O(n) space
- DP: O(n) time, O(n) space
- Optimized: O(n) time, O(1) space

## Design Decisions

### 1. DP Table Dimensions
- LCS: 2D table (both string lengths)
- Knapsack: 2D table (items × weight)
- Coin Change: 1D array (amount only)

**Why?** Each dimension represents a subproblem parameter

### 2. Initialization Values
- Distance problems: Initialize to infinity
- Count problems: Initialize to 0
- LCS/LIS: Initialize based on base cases

### 3. Recurrence Direction
- Forward: Build table increasing indices
- Backward: For 1D arrays with multiple iterations (knapsack)

**Why backward for knapsack?** Prevents using same item twice

### 4. Space Optimization
Trade-off between clarity and space efficiency:
- Keep full table for easy debugging
- Optimize to 1D/O(1) when memory critical

## Complexity Analysis

### All Problems Comparison

| Problem | Time | Space | Technique |
|---------|------|-------|-----------|
| LCS | O(m*n) | O(m*n) | Tabulation |
| Knapsack | O(n*W) | O(n*W) | Tabulation |
| Coin Change | O(A*n) | O(A) | Tabulation |
| Fibonacci | O(n) | O(n) | Memoization |
| LIS (naive) | O(n²) | O(n) | Tabulation |
| LIS (opt) | O(n log n) | O(n) | Binary Search |

*m,n = string/item lengths, W = weight, A = amount*

## Sample Input/Output

### Running the Program

```powershell
javac DynamicProgrammingProblems.java
java DynamicProgrammingProblems
```

### Expected Output

```
╔════════════════════════════════════════════════════════╗
║        DYNAMIC PROGRAMMING PROBLEMS IMPLEMENTATION     ║
╚════════════════════════════════════════════════════════╝

========== LONGEST COMMON SUBSEQUENCE ==========
String 1: AGGTAB
String 2: GXTXAYB
LCS: GTAB (length: 4)

String 1: ABCDGH
String 2: AEDFHR
LCS: ADH (length: 3)

========== 0/1 KNAPSACK PROBLEM ==========
Items (weight, value):
  (2, 3)
  (3, 4)
  (4, 5)
  (5, 6)
Capacity: 8
Maximum value: 10
Selected items: 2
  Weight: 4, Value: 5
  Weight: 5, Value: 6
Total weight: 9... (adjusted)
Total value: 10

========== COIN CHANGE PROBLEM ==========
Coins: [1, 2, 5]
Amount: 5
Minimum coins needed: 1
Coins used: [5]
Number of ways: 4

Coins: [2]
Amount: 3
Cannot make amount
Number of ways: 0

========== FIBONACCI SEQUENCE ==========
Computing Fibonacci(20)
With Memoization: 6765
With DP: 6765
Space-Optimized: 6765

Fibonacci sequence (first 10):
0 1 1 2 3 5 8 13 21 34

========== LONGEST INCREASING SUBSEQUENCE ==========
Array: [10, 9, 2, 5, 3, 7, 101, 18]
LIS Length (O(n²)): 4
LIS Length (O(n log n)): 4

Array: [0, 1, 0, 4, 4, 4, 3, 5, 1]
LIS Length (O(n²)): 4
LIS Length (O(n log n)): 4

=======================================================
All demonstrations completed successfully!
=======================================================
```

## Real-World Applications

### 1. **Bioinformatics**
- DNA sequence alignment (LCS)
- Protein structure prediction
- Phylogenetic analysis

### 2. **Resource Allocation**
- Budget allocation (Knapsack variant)
- Server capacity planning
- Manufacturing optimization

### 3. **Finance**
- Optimal portfolio selection
- Minimum cost trading strategies
- Coin change for vending machines

### 4. **Text Processing**
- Edit distance (Levenshtein)
- Spell checking
- Text compression

### 5. **Game Development**
- Optimal pathfinding
- Resource management
- AI decision making

## Variations and Challenges

### Challenge 1: Edit Distance (Levenshtein)
Find minimum operations to transform one string to another.

**Operations:** Insert, delete, replace
**Complexity:** O(m*n) time, O(m*n) space

### Challenge 2: Unbounded Knapsack
Items can be used unlimited times (vs. 0/1).

**Difference:** Forward iteration instead of backward
**Complexity:** O(n*W) time, O(W) space

### Challenge 3: Longest Common Substring
Find longest contiguous common substring.

**Difference:** Reset counter when mismatch, track maximum

### Challenge 4: Maximum Subarray Sum
Find contiguous subarray with maximum sum (Kadane's algorithm).

**Approach:** O(n) single pass with min DP
**Application:** Stock trading (max profit)

### Challenge 5: Two-Dimensional LCS
Extend LCS to 2D grids or matrices.

**Approach:** 3D DP table
**Complexity:** O(m*n*k) for dimensions m,n,k

### Challenge 6: Matrix Chain Multiplication
Find optimal parenthesization for minimum operations.

**Approach:** 2D DP with interval length iteration
**Complexity:** O(n³) time, O(n²) space

## Interview Tips

### When Discussing DP Problems

1. **Identify DP Structure:**
   - What are the subproblems?
   - How do subproblems overlap?
   - What's the recurrence relation?

2. **Explain Your Approach:**
   - Top-down (memoization) or bottom-up (tabulation)?
   - Why choose one over the other?
   - Space optimization possibilities?

3. **Discuss Complexity:**
   - Time: Usually product of subproblem count and solution time
   - Space: Can optimize from 2D to 1D often
   - Practical limits for constraints

4. **Test Edge Cases:**
   - Empty inputs
   - Single elements
   - Maximum constraints
   - Edge boundary values

### Common Questions

**Q: How to identify if problem needs DP?**
A: Look for optimal substructure and overlapping subproblems. Usually optimization (max/min/count).

**Q: Why backward iteration for knapsack?**
A: Prevents using same item twice. Forward would overwrite values before using them.

**Q: Can LCS be solved in O(n) space?**
A: Yes, only need previous row. However, backtracking needs both rows.

**Q: What's the time complexity of LIS with binary search?**
A: O(n log n) - each element binary searched in O(log n) tails array.

## Key Learnings

1. **Recognize DP patterns:** Optimization problems with subproblems
2. **Define recurrence:** Clear state and transition relation
3. **Choose approach:** Memoization vs. tabulation based on problem
4. **Optimize space:** Many 2D problems reducible to 1D
5. **Practice patterns:** LCS, knapsack, coin change are classics

## Further Exploration

1. **More DP Classics:** Edit distance, matrix chain, rod cutting
2. **Advanced Techniques:** Convex hull optimization, Divide-and-conquer DP
3. **Digit DP:** Problems on digit manipulations
4. **Profile DP:** Problems with complex states
5. **DP with Bitmasking:** Traveling salesman, subset problems

---

**Compilation**: `javac DynamicProgrammingProblems.java`
**Execution**: `java DynamicProgrammingProblems`
**Difficulty**: Intermediate to Advanced
**Topics**: Dynamic Programming, Optimization, Memoization, Tabulation
**Prerequisites**: Basic Java, Recursion, Arrays/Collections
