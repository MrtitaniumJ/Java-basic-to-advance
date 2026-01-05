# Space Complexity Analysis in Java

## Introduction

Space complexity measures the amount of memory an algorithm requires relative to its input size. Unlike time complexity, which focuses on execution time, space complexity helps us understand memory requirements—crucial for systems with limited resources like embedded devices, mobile applications, and large-scale distributed systems. Understanding space complexity allows developers to optimize memory usage and make informed trade-offs between speed and memory consumption.

## Key Concepts

### Auxiliary Space vs Total Space

**Total Space Complexity**: The complete memory used by an algorithm, including both input and auxiliary space.

**Auxiliary Space**: The extra memory an algorithm uses beyond the input data structure. When discussing space complexity, we typically refer to auxiliary space unless otherwise specified.

### Understanding Big-O Space Notation

- **O(1)**: Constant space - doesn't grow with input size
- **O(log n)**: Logarithmic space - grows slowly
- **O(n)**: Linear space - proportional to input size
- **O(n log n)**: Linear-logarithmic space
- **O(n²)**: Quadratic space - rarely acceptable
- **O(2ⁿ)**: Exponential space - typically avoided

## Stack Space and Recursion

Recursive functions consume stack memory for each function call. The maximum space used equals the maximum call stack depth multiplied by space per frame.

### Recursion Space Complexity Examples

```java
// O(n) space complexity - linear recursion depth
public class RecursionSpaceAnalysis {
    
    // Factorial - O(n) stack space
    // Space is used for n recursive calls on the call stack
    public static long factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);  // Creates n stack frames
    }
    
    // Sum of array - O(n) stack space
    // Each recursive call adds to the call stack
    public static int sumArray(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        return arr[index] + sumArray(arr, index + 1);
    }
    
    // Fibonacci recursive - O(n) space in worst case
    // Due to call stack depth, not the exponential time complexity
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    // Tail recursion optimization - can be optimized to O(1)
    // Some compilers optimize this to O(1) with tail recursion
    public static long tailRecursiveSum(int n, long accumulator) {
        if (n == 0) {
            return accumulator;
        }
        return tailRecursiveSum(n - 1, accumulator + n);
    }
    
    public static void main(String[] args) {
        System.out.println("Factorial of 5: " + factorial(5));
        System.out.println("Sum of [1,2,3,4,5]: " + sumArray(new int[]{1,2,3,4,5}, 0));
    }
}
```

## In-Place Algorithms

In-place algorithms modify data within the same memory allocation, requiring only O(1) auxiliary space. These are memory-efficient but may sacrifice code clarity.

```java
public class InPlaceAlgorithms {
    
    // In-place array reversal - O(1) auxiliary space
    public static void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            // Swap using XOR - no extra variable needed
            arr[left] ^= arr[right];
            arr[right] ^= arr[left];
            arr[left] ^= arr[right];
            left++;
            right--;
        }
    }
    
    // In-place array reversal with temp variable - still O(1)
    public static void reverseArraySimple(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    // In-place partition for quicksort - O(log n) for recursion stack
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
    
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    // Remove duplicates in-place from sorted array - O(1) space
    public static int removeDuplicates(int[] arr) {
        if (arr.length == 0) return 0;
        int writeIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[writeIndex]) {
                writeIndex++;
                arr[writeIndex] = arr[i];
            }
        }
        return writeIndex + 1;
    }
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        reverseArray(arr);
        System.out.println("Reversed: " + java.util.Arrays.toString(arr));
        
        int[] sorted = {1, 1, 2, 2, 3, 3, 4, 5, 5};
        int newLength = removeDuplicates(sorted);
        System.out.println("After removing duplicates: " + newLength);
    }
}
```

## Memory Optimization Techniques

### String Building and Concatenation

```java
public class MemoryOptimization {
    
    // Inefficient - O(n²) time, creates many intermediate strings
    public static String naiveStringConcat(String[] words) {
        String result = "";
        for (String word : words) {
            result += word;  // Creates new string each iteration
        }
        return result;
    }
    
    // Efficient - O(n) time and space, single allocation
    public static String efficientStringConcat(String[] words) {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
        }
        return sb.toString();
    }
    
    // Using functional approach - O(n) space
    public static String functionalConcat(String[] words) {
        return java.util.Arrays.stream(words)
            .collect(java.util.stream.Collectors.joining());
    }
}
```

### Space Optimization with Iteration vs Recursion

```java
public class IterationVsRecursion {
    
    // Recursive factorial - O(n) space due to call stack
    public static long recursiveFactorial(int n) {
        return n <= 1 ? 1 : n * recursiveFactorial(n - 1);
    }
    
    // Iterative factorial - O(1) space
    public static long iterativeFactorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    // Fibonacci with memoization - O(n) space, O(n) time
    public static long fibonacciMemo(int n, java.util.Map<Integer, Long> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long result = fibonacciMemo(n - 1, memo) + fibonacciMemo(n - 2, memo);
        memo.put(n, result);
        return result;
    }
    
    // Fibonacci with tabulation - O(n) space, O(n) time, iterative
    public static long fibonacciDP(int n) {
        if (n <= 1) return n;
        long[] dp = new long[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
    
    // Space-optimized Fibonacci - O(1) space, O(n) time
    public static long fibonacciOptimized(int n) {
        if (n <= 1) return n;
        long prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            long next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
}
```

## Space Complexity of Data Structures

```java
public class DataStructureSpaceComplexity {
    
    // ArrayList - O(n) space for n elements
    // Capacity may exceed size
    public static void arrayListSpace() {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        System.out.println("ArrayList stores " + list.size() + " elements");
    }
    
    // HashMap - O(n) space for n key-value pairs
    // Plus internal bucket array (capacity >= size)
    public static void hashMapSpace() {
        java.util.Map<Integer, String> map = new java.util.HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, "Value " + i);
        }
        System.out.println("HashMap stores " + map.size() + " entries");
    }
    
    // Binary Search Tree - O(n) for n nodes
    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }
    
    // Height of BST affects recursion space
    public static int treeHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(treeHeight(root.left), treeHeight(root.right));
    }
    
    // Graph - O(V + E) space for adjacency list
    public static void graphSpace() {
        java.util.List<Integer>[] adj = new java.util.ArrayList[100];
        for (int i = 0; i < 100; i++) {
            adj[i] = new java.util.ArrayList<>();
        }
        // O(V) for array + O(E) for all edges
    }
}
```

## Time-Space Trade-offs

### Caching vs Computation

```java
public class TimeSpaceTradeoff {
    
    // Low space, high time - compute everything
    public static int nthPrimeDirect(int n) {
        int count = 0, num = 2;
        while (count < n) {
            if (isPrime(num)) {
                count++;
                if (count == n) return num;
            }
            num++;
        }
        return num;
    }
    
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
    
    // High space, low time - cache results
    public static class PrimeCache {
        private java.util.List<Integer> primes;
        
        public PrimeCache(int maxPrimes) {
            primes = new java.util.ArrayList<>();
            int num = 2;
            while (primes.size() < maxPrimes) {
                if (isPrimeEfficient(num)) {
                    primes.add(num);
                }
                num++;
            }
        }
        
        private boolean isPrimeEfficient(int num) {
            for (int prime : primes) {
                if (prime * prime > num) break;
                if (num % prime == 0) return false;
            }
            return true;
        }
        
        public int getNthPrime(int n) {
            return primes.get(n - 1);  // O(1) lookup
        }
    }
    
    // Sorting example: Merge Sort vs Quick Sort
    // Merge Sort: O(n) auxiliary space, O(n log n) time guaranteed
    // Quick Sort: O(log n) auxiliary space, O(n log n) average time
}
```

## Calculating Space Complexity

### Step-by-Step Analysis

```java
public class SpaceComplexityCalculation {
    
    // Example 1: Simple variables - O(1)
    public static int example1(int n) {
        int sum = 0;        // 1 int
        int count = 0;      // 1 int
        return sum + count; // Total: O(1)
    }
    
    // Example 2: Array allocation - O(n)
    public static int example2(int[] arr) {
        int[] temp = new int[arr.length];  // O(n) - proportional to input
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i] * 2;
        }
        return temp.length; // Total: O(n)
    }
    
    // Example 3: Nested loops with array - O(n²)
    public static void example3(int n) {
        int[][] matrix = new int[n][n];  // O(n²) - n×n matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i * j;
            }
        }
    }
    
    // Example 4: Recursion depth - O(n)
    public static void example4(int n) {
        if (n > 0) {
            example4(n - 1);  // Call stack grows to depth n = O(n)
        }
    }
    
    // Example 5: Multiple data structures - O(n)
    public static void example5(int[] arr) {
        java.util.Set<Integer> set = new java.util.HashSet<>(java.util.Arrays.asList(
            java.util.stream.IntStream.of(arr)
                .boxed().toArray(Integer[]::new)
        ));  // O(n) for set
        
        java.util.Map<Integer, Integer> map = new java.util.HashMap<>();  // O(n) for map
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        // Total: O(n) + O(n) = O(n)
    }
}
```

## Practical Memory Analysis

```java
public class MemoryAnalysisExample {
    
    public static void main(String[] args) {
        // Monitor memory usage
        Runtime runtime = Runtime.getRuntime();
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Create data structure
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = (afterMemory - beforeMemory) / 1024;  // KB
        
        System.out.println("Memory used: " + memoryUsed + " KB");
        System.out.println("Elements: " + list.size());
        System.out.println("Per-element overhead: " + (memoryUsed * 1024 / list.size()) + " bytes");
    }
}
```

## Best Practices

1. **Prefer In-Place Algorithms**: When possible, modify data in-place to save memory
2. **Avoid Unnecessary Copies**: Use references instead of creating duplicate data
3. **Iterate Instead of Recurse**: Use loops instead of recursion to save stack space
4. **Use Appropriate Data Structures**: Choose structures that match your access patterns
5. **Profile Memory Usage**: Measure actual memory consumption in production scenarios
6. **Balance Trade-offs**: Sometimes O(n) space with O(n) time beats O(1) space with O(n²) time
7. **Clean Resources**: Release unused data structures and null out references

## Common Space Complexity Pitfalls

- Forgetting about implicit space in recursive calls
- Assuming in-place algorithms use exactly O(1) space
- Ignoring language-specific memory overhead (object headers, padding)
- Not considering worst-case scenarios (unbalanced trees, hash collisions)
- Creating unnecessary copies of data during operations

## Conclusion

Space complexity analysis is essential for writing scalable, efficient applications. Understanding the trade-offs between time and space helps developers make informed decisions about algorithm selection. By mastering space complexity concepts and using optimization techniques, you can build applications that perform well even with large datasets and limited memory resources.
