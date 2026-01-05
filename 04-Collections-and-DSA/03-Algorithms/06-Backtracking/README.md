# Backtracking Algorithm: Complete Guide

## Table of Contents
1. [Introduction](#introduction)
2. [Backtracking Fundamentals](#fundamentals)
3. [Core Concepts](#core-concepts)
4. [Classic Problems & Solutions](#classic-problems)
5. [Complexity Analysis](#complexity-analysis)
6. [When to Use Backtracking](#when-to-use)
7. [Optimization Techniques](#optimization)

## Introduction

Backtracking is a systematic problem-solving technique that explores all possible solutions by building them incrementally, abandoning solutions that violate constraints (pruning). It's essential for solving constraint satisfaction problems where you need to find one or all valid solutions from a large search space.

### Key Characteristics
- **Builds solutions incrementally**: Adds one element at a time
- **Prunes invalid paths**: Stops exploration when constraints are violated
- **Explores exhaustively**: Ensures all valid solutions are found
- **Systematic**: Uses recursion to navigate the solution space

## Fundamentals

### How Backtracking Works

```
1. Start at the root of the solution space
2. Choose a candidate for the next position
3. Check if the candidate is valid (constraint checking)
4. If valid, recurse with this choice (explore deeper)
5. If invalid or no solution found, backtrack (undo choice)
6. Try next candidate for current position
7. Repeat until all possibilities are exhausted
```

### Solution Space Tree

```
For N-Queens problem (N=3), the search tree looks like:

                        Start
                    /    |    \
                  Q1    Q1    Q1
                 /||\   /||    ||
               Q2 X X Q2 X    X...
              /| ...
            Q3 X
           /
         Valid!
```

- **Nodes**: Represent partial solutions
- **Branches**: Represent different choices
- **Leaves**: Represent complete solutions (valid or invalid)
- **Pruning**: Eliminates entire subtrees when constraints fail

## Core Concepts

### 1. Search Space
The complete set of all possible solutions that could satisfy your problem.

```java
// Example: Finding all permutations of [1,2,3]
// Search space size = n! = 3! = 6
// Permutations: [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
```

### 2. Constraint Checking
Validate whether a partial solution can lead to a valid complete solution.

```java
public boolean isValid(int[] board, int row, int col) {
    // Check row - no two queens in same row
    // Check column - no two queens in same column
    // Check diagonals - no two queens on same diagonal
}
```

### 3. Pruning Strategy
Eliminate branches of the search tree that cannot possibly lead to valid solutions.

```
Without pruning:  Explores all n! possibilities
With pruning:     Explores only valid branches (reduces exponentially)
```

### 4. Base Cases
- **Success**: Found a valid complete solution
- **Failure**: No valid solution possible from current state
- **Boundary**: Reached maximum depth or all positions filled

## Classic Problems & Solutions

### Problem 1: N-Queens Problem

Find all positions where N queens can be placed on an N×N board such that no two queens attack each other.

```java
import java.util.*;

public class NQueens {
    private List<List<String>> solutions;
    private int n;
    
    public List<List<String>> solveNQueens(int n) {
        this.solutions = new ArrayList<>();
        this.n = n;
        int[] board = new int[n]; // board[i] = column position of queen in row i
        backtrack(board, 0);
        return convertToStrings(board);
    }
    
    private void backtrack(int[] board, int row) {
        // Base case: all queens placed successfully
        if (row == n) {
            solutions.add(convertBoardToList(board));
            return;
        }
        
        // Try placing queen in each column of current row
        for (int col = 0; col < n; col++) {
            if (isValid(board, row, col)) {
                // Make choice
                board[row] = col;
                
                // Explore with this choice
                backtrack(board, row + 1);
                
                // Backtrack (undo choice) - happens implicitly here
            }
        }
    }
    
    private boolean isValid(int[] board, int row, int col) {
        // Check previous rows
        for (int i = 0; i < row; i++) {
            int prevCol = board[i];
            
            // Same column check
            if (prevCol == col) {
                return false;
            }
            
            // Diagonal check: |row1 - row2| == |col1 - col2|
            if (Math.abs(i - row) == Math.abs(prevCol - col)) {
                return false;
            }
        }
        return true;
    }
    
    private List<String> convertBoardToList(int[] board) {
        List<String> result = new ArrayList<>();
        for (int col : board) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(i == col ? 'Q' : '.');
            }
            result.add(sb.toString());
        }
        return result;
    }
    
    private List<List<String>> convertToStrings(int[] board) {
        List<List<String>> result = new ArrayList<>();
        List<List<String>> allSolutions = new ArrayList<>();
        // Convert internal representation to required format
        return allSolutions;
    }
}

// Time Complexity: O(N!) in worst case
// Space Complexity: O(N) for recursion stack
```

### Problem 2: Sudoku Solver

Solve a 9×9 Sudoku puzzle using constraint validation.

```java
public class SudokuSolver {
    private boolean[][] rows;
    private boolean[][] cols;
    private boolean[][][] boxes;
    private char[][] board;
    
    public void solveSudoku(char[][] board) {
        this.board = board;
        this.rows = new boolean[9][10];
        this.cols = new boolean[9][10];
        this.boxes = new boolean[3][3][10];
        
        // Initialize constraints from existing numbers
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int digit = board[i][j] - '0';
                    rows[i][digit] = true;
                    cols[j][digit] = true;
                    boxes[i/3][j/3][digit] = true;
                }
            }
        }
        
        backtrack(0, 0);
    }
    
    private boolean backtrack(int row, int col) {
        // Find next empty cell
        while (row < 9) {
            if (board[row][col] == '.') {
                break;
            }
            col++;
            if (col == 9) {
                row++;
                col = 0;
            }
        }
        
        // All cells filled successfully
        if (row == 9) {
            return true;
        }
        
        // Try digits 1-9
        for (int digit = 1; digit <= 9; digit++) {
            if (isValidPlacement(row, col, digit)) {
                // Make choice
                board[row][col] = (char)('0' + digit);
                rows[row][digit] = true;
                cols[col][digit] = true;
                boxes[row/3][col/3][digit] = true;
                
                // Explore
                if (backtrack(row, col)) {
                    return true;
                }
                
                // Undo choice
                board[row][col] = '.';
                rows[row][digit] = false;
                cols[col][digit] = false;
                boxes[row/3][col/3][digit] = false;
            }
        }
        
        return false; // No valid digit found
    }
    
    private boolean isValidPlacement(int row, int col, int digit) {
        return !rows[row][digit] && 
               !cols[col][digit] && 
               !boxes[row/3][col/3][digit];
    }
}

// Time Complexity: O(9^(81-k)) where k is pre-filled cells
// Space Complexity: O(81) for board storage
```

### Problem 3: Generate Permutations

Find all permutations of a given array.

```java
public class Permutations {
    private List<List<Integer>> result;
    
    public List<List<Integer>> permute(int[] nums) {
        result = new ArrayList<>();
        backtrack(nums, new ArrayList<>(), new boolean[nums.length]);
        return result;
    }
    
    private void backtrack(int[] nums, List<Integer> current, boolean[] used) {
        // Base case: complete permutation found
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try each unused element
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                // Make choice
                current.add(nums[i]);
                used[i] = true;
                
                // Explore
                backtrack(nums, current, used);
                
                // Undo choice
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
}

// Time Complexity: O(N! * N) - N! permutations, N time to copy each
// Space Complexity: O(N) for recursion depth
```

### Problem 4: Combinations

Generate all combinations of size K from N elements.

```java
public class Combinations {
    private List<List<Integer>> result;
    
    public List<List<Integer>> combine(int n, int k) {
        result = new ArrayList<>();
        backtrack(1, n, k, new ArrayList<>());
        return result;
    }
    
    private void backtrack(int start, int n, int k, List<Integer> current) {
        // Base case: combination complete
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Optimization: pruning
        // Remaining elements needed: k - current.size()
        // If remaining > available elements, prune
        int needed = k - current.size();
        int available = n - start + 1;
        
        if (needed > available) {
            return; // Early pruning
        }
        
        // Try each starting position
        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(i + 1, n, k, current);
            current.remove(current.size() - 1);
        }
    }
}

// Time Complexity: O(C(n,k) * k) - C(n,k) combinations, k time per copy
// Space Complexity: O(k) for recursion depth
```

### Problem 5: Word Search

Find if a word exists in a 2D grid.

```java
public class WordSearch {
    private int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0) return false;
        
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        
        // Try starting from each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (backtrack(board, word, 0, i, j, visited)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean backtrack(char[][] board, String word, 
                             int index, int row, int col, boolean[][] visited) {
        // Base case: entire word matched
        if (index == word.length()) {
            return true;
        }
        
        // Boundary and constraint checking
        if (row < 0 || row >= board.length || 
            col < 0 || col >= board[0].length ||
            visited[row][col] || board[row][col] != word.charAt(index)) {
            return false;
        }
        
        // Mark as visited
        visited[row][col] = true;
        
        // Explore 4 directions
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if (backtrack(board, word, index + 1, newRow, newCol, visited)) {
                return true;
            }
        }
        
        // Undo choice (backtrack)
        visited[row][col] = false;
        return false;
    }
}

// Time Complexity: O(N * 4^L) - N cells, L word length, 4 directions
// Space Complexity: O(L) for recursion stack
```

## Complexity Analysis

### Time Complexity Patterns

| Problem | Worst Case | Best Case | Notes |
|---------|-----------|----------|-------|
| N-Queens | O(N!) | O(N) | Early termination possible |
| Sudoku | O(9^(81-k)) | O(1) | k = filled cells |
| Permutations | O(N!) | O(N!) | Always explores all |
| Combinations | O(C(N,K)) | O(C(N,K)) | Binomial coefficient |
| Word Search | O(N*4^L) | O(1) | N = cells, L = word length |

### Space Complexity

- **Recursion Stack**: O(depth) - typically the limiting factor
- **Data Structures**: O(solution size) for storing results
- **Visited Arrays**: O(input size) for tracking state

## When to Use Backtracking

### Ideal Problems
1. **Constraint Satisfaction**: N-Queens, Sudoku
2. **Combinatorial Search**: Permutations, combinations, subsets
3. **Path Finding**: Maze solving, word search
4. **Exhaustive Search**: When all solutions needed, not just one

### Red Flags (Avoid Backtracking)
- Finding optimal solution in continuous space
- Very large search spaces without pruning opportunities
- Simple iteration problems (use iteration instead)
- Problems solvable by dynamic programming (more efficient)

## Optimization Techniques

### 1. Aggressive Pruning
```java
// Check constraints early to eliminate branches
if (violatesConstraint(candidate)) {
    return; // Don't recurse further
}
```

### 2. Constraint Propagation
```java
// Maintain sets of valid values for each position
Set<Integer> validForRow = getValidDigits(row);
for (int digit : validForRow) {
    // Only try valid digits
}
```

### 3. Most Constrained Variable
```java
// Process most constrained variables first
// Reduces branching factor earlier
```

### 4. Memoization
```java
// Cache already computed states
Map<State, Boolean> memo = new HashMap<>();
if (memo.containsKey(state)) {
    return memo.get(state);
}
```

### 5. Iterative Deepening
```java
// Explore solutions level by level
for (int depth = 1; depth <= maxDepth; depth++) {
    if (search(depth)) return;
}
```

## Best Practices

1. **Clear Base Cases**: Define when recursion stops
2. **Constraint Validation**: Check early and aggressively
3. **State Management**: Properly undo changes (backtrack)
4. **Documentation**: Explain search space and pruning logic
5. **Testing**: Verify with small inputs first
6. **Performance**: Profile to identify bottlenecks

## Summary

Backtracking is a powerful technique for solving constraint satisfaction and combinatorial problems. Success depends on:
- **Clear problem modeling** into search space
- **Effective constraint checking** for pruning
- **Proper state management** for undo operations
- **Smart optimization** to reduce search space

Choose backtracking when exhaustive search is needed and pruning opportunities exist. Always consider alternatives like dynamic programming or greedy approaches for specific problem types.
