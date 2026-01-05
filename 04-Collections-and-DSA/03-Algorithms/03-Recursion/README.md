# Recursion in Java - Complete Guide

## Table of Contents
1. [Introduction](#introduction)
2. [How Recursion Works](#how-recursion-works)
3. [Call Stack Visualization](#call-stack-visualization)
4. [Base Case and Recursive Case](#base-case-and-recursive-case)
5. [Common Recursion Patterns](#common-recursion-patterns)
6. [Complete Java Examples](#complete-java-examples)
7. [Optimization Techniques](#optimization-techniques)
8. [Complexity Analysis](#complexity-analysis)
9. [When to Use Recursion](#when-to-use-recursion)
10. [Common Mistakes](#common-mistakes)

---

## Introduction

Recursion is a programming technique where a function calls itself to solve a problem by breaking it down into smaller subproblems of the same type. It's a fundamental concept in computer science and is particularly useful for problems with naturally recursive structures like trees, graphs, and mathematical sequences.

The power of recursion lies in its ability to transform complex problems into simpler, more manageable ones. Instead of thinking iteratively (using loops), you think declaratively: "How can I express this problem in terms of itself?"

---

## How Recursion Works

Recursion works by dividing a problem into two parts:

1. **Base Case**: The simplest instance of the problem that can be solved directly without recursion
2. **Recursive Case**: The step that breaks the problem into smaller versions of itself

When a recursive function is called:
1. The function's execution is paused
2. A new call to the same function with simpler parameters is made
3. This continues until the base case is reached
4. The results are combined as the call stack unwinds

**Key Principle**: Every recursive call must make progress toward the base case, otherwise you'll have infinite recursion.

---

## Call Stack Visualization

Understanding the call stack is crucial for mastering recursion. The call stack is a data structure that stores information about function calls.

### Simple Example: factorial(4)

```
Call Stack Growth (Building Up):
┌─────────────────┐
│ factorial(1)    │ ← Base case reached, returns 1
└─────────────────┘
┌─────────────────┐
│ factorial(2)    │ ← Waiting for factorial(1)
│ factorial(1)    │
└─────────────────┘
┌─────────────────┐
│ factorial(3)    │ ← Waiting for factorial(2)
│ factorial(2)    │
│ factorial(1)    │
└─────────────────┘
┌─────────────────┐
│ factorial(4)    │ ← Waiting for factorial(3)
│ factorial(3)    │
│ factorial(2)    │
│ factorial(1)    │
└─────────────────┘

Call Stack Unwinding (Results Coming Back):
4! = 4 × 3! = 4 × 6 = 24 ← factorial(4) completes
3! = 3 × 2! = 3 × 2 = 6 ← factorial(3) completes
2! = 2 × 1! = 2 × 1 = 2 ← factorial(2) completes
1! = 1 ← factorial(1) returns 1 (base case)
```

---

## Base Case and Recursive Case

### Base Case
The base case is the termination condition. Without it, recursion would continue indefinitely.

**Characteristics of a good base case:**
- It's the simplest possible instance of the problem
- It returns a value directly without further recursion
- It's always reachable through the recursive calls

### Recursive Case
The recursive case describes how to solve the problem in terms of simpler versions of itself.

**Characteristics of a good recursive case:**
- It makes progress toward the base case
- It calls the same function with simpler/smaller parameters
- It combines the results appropriately

### Example Pattern:
```
function solve(problem):
    if problem is base case:
        return solution_directly
    else:
        return combine(solve(smaller_problem), other_work)
```

---

## Common Recursion Patterns

### 1. **Linear Recursion**
Makes one recursive call per function invocation.
- Example: Factorial, Fibonacci (naive)

### 2. **Tree Recursion**
Makes multiple recursive calls per function invocation.
- Example: Fibonacci (optimal), Tree traversal

### 3. **Tail Recursion**
The recursive call is the last operation in the function.
- Can be optimized by compilers to use constant space
- Example: Factorial (tail-recursive version)

### 4. **Mutual Recursion**
Two or more functions call each other.
- Example: Even/Odd checking

### 5. **Divide and Conquer**
Divides the problem into independent subproblems, solves them, and combines results.
- Example: Merge sort, Quick sort

---

## Complete Java Examples

### Example 1: Factorial (Linear Recursion)

```java
/**
 * Calculates factorial using simple recursion.
 * Factorial of n = n × (n-1) × (n-2) × ... × 1
 * 
 * Base Case: factorial(0) = 1
 * Recursive Case: factorial(n) = n × factorial(n-1)
 */
public class FactorialExample {
    
    // Simple Recursive Factorial
    public static int factorial(int n) {
        // Base case
        if (n == 0 || n == 1) {
            return 1;
        }
        // Recursive case
        return n * factorial(n - 1);
    }
    
    // Tail-Recursive Factorial (optimizable)
    public static int factorialTail(int n) {
        return factorialHelper(n, 1);
    }
    
    private static int factorialHelper(int n, int accumulator) {
        // Base case
        if (n <= 1) {
            return accumulator;
        }
        // Tail recursive call
        return factorialHelper(n - 1, n * accumulator);
    }
    
    // With Error Checking
    public static int factorialSafe(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }
        return n == 0 || n == 1 ? 1 : n * factorialSafe(n - 1);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Factorial Examples ===");
        System.out.println("5! = " + factorial(5)); // 120
        System.out.println("10! = " + factorial(10)); // 3628800
        System.out.println("Tail recursive 5! = " + factorialTail(5)); // 120
        System.out.println("Safe 8! = " + factorialSafe(8)); // 40320
    }
}
```

### Example 2: Fibonacci (Tree Recursion)

```java
/**
 * Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21...
 * Each number is sum of the two preceding ones.
 */
public class FibonacciExample {
    
    // Naive recursive (inefficient - O(2^n))
    public static int fibonacciNaive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciNaive(n - 1) + fibonacciNaive(n - 2);
    }
    
    // Memoized recursive (efficient - O(n))
    public static int fibonacciMemo(int n) {
        int[] memo = new int[n + 1];
        return fibonacciMemoHelper(n, memo);
    }
    
    private static int fibonacciMemoHelper(int n, int[] memo) {
        // Base cases
        if (n <= 1) {
            return n;
        }
        
        // Check if already computed
        if (memo[n] != 0) {
            return memo[n];
        }
        
        // Compute and store
        memo[n] = fibonacciMemoHelper(n - 1, memo) + fibonacciMemoHelper(n - 2, memo);
        return memo[n];
    }
    
    // Iterative approach (for comparison)
    public static int fibonacciIterative(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
    
    // Generate Fibonacci sequence
    public static void generateFibonacci(int count) {
        System.out.println("Fibonacci sequence (first " + count + " numbers):");
        for (int i = 0; i < count; i++) {
            System.out.print(fibonacciMemo(i) + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Fibonacci Examples ===");
        System.out.println("fib(10) naive = " + fibonacciNaive(10)); // 55
        System.out.println("fib(20) memoized = " + fibonacciMemo(20)); // 6765
        System.out.println("fib(30) iterative = " + fibonacciIterative(30)); // 832040
        generateFibonacci(15);
    }
}
```

### Example 3: Tree Traversal

```java
import java.util.*;

/**
 * Binary Tree traversal using recursion.
 * Demonstrates how recursion naturally handles tree structures.
 */
public class TreeTraversalExample {
    
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int value) {
            this.value = value;
        }
    }
    
    // In-order traversal (Left -> Root -> Right)
    public static void inOrder(TreeNode node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }
    
    // Pre-order traversal (Root -> Left -> Right)
    public static void preOrder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }
    
    // Post-order traversal (Left -> Right -> Root)
    public static void postOrder(TreeNode node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }
    
    // Level-order traversal (using recursion)
    public static void levelOrder(TreeNode root) {
        int height = getHeight(root);
        for (int level = 1; level <= height; level++) {
            printLevel(root, level);
        }
    }
    
    private static int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    private static void printLevel(TreeNode node, int level) {
        if (node == null) return;
        if (level == 1) {
            System.out.print(node.value + " ");
        } else {
            printLevel(node.left, level - 1);
            printLevel(node.right, level - 1);
        }
    }
    
    // Find maximum value in tree
    public static int findMax(TreeNode node) {
        if (node == null) return Integer.MIN_VALUE;
        return Math.max(node.value, Math.max(findMax(node.left), findMax(node.right)));
    }
    
    // Count nodes
    public static int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Binary Tree Traversal Examples ===");
        
        // Build sample tree:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        System.out.print("In-order: ");
        inOrder(root);
        System.out.println();
        
        System.out.print("Pre-order: ");
        preOrder(root);
        System.out.println();
        
        System.out.print("Post-order: ");
        postOrder(root);
        System.out.println();
        
        System.out.print("Level-order: ");
        levelOrder(root);
        System.out.println();
        
        System.out.println("Max value: " + findMax(root));
        System.out.println("Node count: " + countNodes(root));
    }
}
```

### Example 4: Permutations (Backtracking)

```java
import java.util.*;

/**
 * Generate all permutations of a string using recursion.
 * Demonstrates recursive backtracking pattern.
 */
public class PermutationsExample {
    
    // Generate permutations of a string
    public static void generatePermutations(String str) {
        Set<String> permutations = new HashSet<>();
        char[] chars = str.toCharArray();
        permute(chars, 0, permutations);
        
        System.out.println("Permutations of \"" + str + "\":");
        permutations.forEach(p -> System.out.print(p + " "));
        System.out.println("\nTotal: " + permutations.size());
    }
    
    private static void permute(char[] chars, int index, Set<String> result) {
        // Base case: reached end of string
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }
        
        // Recursive case: try swapping each remaining character
        for (int i = index; i < chars.length; i++) {
            // Swap
            swap(chars, index, i);
            // Recurse
            permute(chars, index + 1, result);
            // Backtrack (undo swap)
            swap(chars, index, i);
        }
    }
    
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
    
    // Generate permutations of a list
    public static List<List<Integer>> generatePermutationsList(List<Integer> nums) {
        List<List<Integer>> result = new ArrayList<>();
        permutationsHelper(nums, 0, result);
        return result;
    }
    
    private static void permutationsHelper(List<Integer> nums, int start, List<List<Integer>> result) {
        if (start == nums.size() - 1) {
            result.add(new ArrayList<>(nums));
            return;
        }
        
        for (int i = start; i < nums.size(); i++) {
            Collections.swap(nums, start, i);
            permutationsHelper(nums, start + 1, result);
            Collections.swap(nums, start, i);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Permutation Examples ===");
        generatePermutations("ABC");
        System.out.println();
        
        List<Integer> nums = Arrays.asList(1, 2, 3);
        List<List<Integer>> perms = generatePermutationsList(nums);
        System.out.println("\nPermutations of [1, 2, 3]:");
        perms.forEach(System.out::println);
    }
}
```

---

## Optimization Techniques

### 1. Memoization (Top-Down)
Store results of expensive function calls and return cached results when the same inputs occur again.

```java
// Memoization reduces Fibonacci from O(2^n) to O(n)
Map<Integer, Long> memo = new HashMap<>();

public long fibonacci(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) {
        return memo.get(n);
    }
    long result = fibonacci(n - 1) + fibonacci(n - 2);
    memo.put(n, result);
    return result;
}
```

### 2. Tail Recursion
When the recursive call is the last operation, the compiler can optimize it to avoid building the call stack.

```java
// Tail recursive - compiler can optimize to loop
public int sum(int n, int accumulator) {
    if (n <= 0) return accumulator;
    return sum(n - 1, accumulator + n); // Last operation is return
}
```

### 3. Hybrid Approach
Combine recursion with iteration for large datasets.

```java
public int fibonacci(int n) {
    if (n < 30) return fibonacciRecursive(n); // Fast for small n
    return fibonacciIterative(n); // Switch to iterative for large n
}
```

---

## Complexity Analysis

| Example | Time Complexity | Space Complexity | Notes |
|---------|-----------------|------------------|-------|
| Factorial | O(n) | O(n) | Call stack depth = n |
| Fibonacci (naive) | O(2^n) | O(n) | Many duplicate calculations |
| Fibonacci (memoized) | O(n) | O(n) | Each subproblem solved once |
| Tree Traversal | O(n) | O(h) | h = height of tree |
| Permutations | O(n!) | O(n) | n! permutations, n depth |

**Key Insight**: Space complexity in recursion includes the call stack depth, which is often the hidden cost of recursive solutions.

---

## When to Use Recursion

✅ **Good Use Cases:**
- **Tree/Graph Traversal**: Natural recursive structure
- **Backtracking Problems**: Systematically explore possibilities
- **Divide and Conquer**: Natural problem decomposition
- **Mathematical Sequences**: Natural recursive definitions
- **When code clarity matters**: Recursive solution is more intuitive

❌ **Avoid Recursion:**
- **Deep Recursion on Large Input**: Risk of stack overflow
- **Tail Recursion in Java**: Java doesn't optimize tail calls (use iteration instead)
- **When Iteration Works Better**: Simple loops are more efficient
- **Performance-Critical Code**: Overhead of function calls

---

## Common Mistakes

### 1. Missing or Wrong Base Case
```java
// ❌ WRONG: No base case, infinite recursion
public int mistake1(int n) {
    return n + mistake1(n - 1); // Stack overflow!
}

// ✅ CORRECT
public int correct1(int n) {
    if (n == 0) return 0; // Base case
    return n + correct1(n - 1);
}
```

### 2. Not Making Progress Toward Base Case
```java
// ❌ WRONG: Always calling with same parameter
public void mistake2(int n) {
    if (n == 0) return;
    System.out.println(n);
    mistake2(n); // Infinite recursion!
}

// ✅ CORRECT
public void correct2(int n) {
    if (n == 0) return;
    System.out.println(n);
    correct2(n - 1); // Progress toward base case
}
```

### 3. Stack Overflow on Large Input
```java
// ❌ RISKY: May overflow for large n
public int mistake3(int n) {
    if (n == 0) return 1;
    return n * mistake3(n - 1); // Stack grows with n
}

// ✅ SAFER: Use tail recursion or iteration
public int correct3(int n) {
    int result = 1;
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    return result;
}
```

### 4. Inefficient Recursion Without Memoization
```java
// ❌ INEFFICIENT: O(2^n) due to repeated calculations
public int mistake4(int n) {
    if (n <= 1) return n;
    return mistake4(n - 1) + mistake4(n - 2);
}

// ✅ EFFICIENT: O(n) with memoization
private Map<Integer, Integer> cache = new HashMap<>();
public int correct4(int n) {
    if (n <= 1) return n;
    return cache.computeIfAbsent(n, k -> correct4(k - 1) + correct4(k - 2));
}
```

---

## Key Takeaways

1. **Recursion = Breaking Problems Down**: Express the solution in terms of the problem itself
2. **Always Have a Base Case**: Without it, recursion never stops
3. **Make Progress**: Each recursive call must move toward the base case
4. **Consider Stack Space**: Recursion depth uses call stack memory
5. **Use Memoization**: Cache results to avoid redundant calculations
6. **Know When to Iterate**: Not all problems benefit from recursion

Recursion is a powerful tool that, when used appropriately, can make code more readable and elegant. Master it by understanding the fundamentals and practicing with various problem types.

