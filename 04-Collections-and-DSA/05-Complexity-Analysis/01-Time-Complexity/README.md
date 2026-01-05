# Time Complexity Analysis

## Table of Contents
- [Introduction](#introduction)
- [Big O Notation](#big-o-notation)
- [Common Time Complexities](#common-time-complexities)
  - [O(1) - Constant Time](#o1---constant-time)
  - [O(log n) - Logarithmic Time](#olog-n---logarithmic-time)
  - [O(n) - Linear Time](#on---linear-time)
  - [O(n log n) - Linearithmic Time](#on-log-n---linearithmic-time)
  - [O(n²) - Quadratic Time](#on²---quadratic-time)
  - [O(n³) - Cubic Time](#on³---cubic-time)
  - [O(2^n) - Exponential Time](#o2n---exponential-time)
  - [O(n!) - Factorial Time](#on---factorial-time)
- [Best, Worst, and Average Cases](#best-worst-and-average-cases)
- [Amortized Analysis](#amortized-analysis)
- [Master Theorem](#master-theorem)
- [How to Calculate Time Complexity](#how-to-calculate-time-complexity)
- [Complexity Comparison](#complexity-comparison)
- [Practice Tips](#practice-tips)

## Introduction

Time complexity is a computational concept that describes the amount of time an algorithm takes to run as a function of the input size. It's a critical metric for evaluating algorithm efficiency and helps developers choose the most appropriate solution for a given problem. Understanding time complexity enables you to predict how your code will scale with larger datasets and optimize performance bottlenecks.

Time complexity is typically expressed using Big O notation, which provides an upper bound on the growth rate of an algorithm's running time. This asymptotic notation focuses on the dominant term and ignores constant factors and lower-order terms, making it easier to compare algorithms at scale.

## Big O Notation

Big O notation describes the worst-case scenario of an algorithm's time complexity. It characterizes functions according to their growth rates, where different functions with the same growth rate may be represented using the same O notation. The key principle is that we're interested in how the runtime grows relative to the input size, not the exact number of operations.

**Key Rules for Big O Analysis:**
1. Drop constants: O(2n) becomes O(n)
2. Drop non-dominant terms: O(n² + n) becomes O(n²)
3. Focus on worst-case scenario unless specified otherwise
4. Consider the input size approaching infinity

## Common Time Complexities

### O(1) - Constant Time

**Definition:** The algorithm takes the same amount of time regardless of input size. Operations execute in constant time with no loops or recursive calls dependent on input size.

**Characteristics:**
- Most efficient time complexity
- Independent of input size
- Common in direct access operations like array indexing, hash table lookups

```java
public class ConstantTimeExamples {
    
    // Example 1: Array element access
    public static int getFirstElement(int[] arr) {
        if (arr.length == 0) return -1;
        return arr[0];  // O(1) - direct access
    }
    
    // Example 2: Getting array length
    public static int getArrayLength(int[] arr) {
        return arr.length;  // O(1) - stored property
    }
    
    // Example 3: Hash map get operation
    public static String getValueFromMap(java.util.HashMap<String, String> map, String key) {
        return map.get(key);  // O(1) average case
    }
    
    // Example 4: Mathematical operations
    public static int addTwoNumbers(int a, int b) {
        return a + b;  // O(1)
    }
    
    // Example 5: Stack push operation
    public static void pushToStack(java.util.Stack<Integer> stack, int value) {
        stack.push(value);  // O(1)
    }
    
    // Example 6: Variable assignment
    public static void constantOperations() {
        int x = 5;           // O(1)
        int y = 10;          // O(1)
        int sum = x + y;     // O(1)
        System.out.println(sum);  // O(1)
        // Total: Still O(1) because constant operations
    }
}
```

### O(log n) - Logarithmic Time

**Definition:** The algorithm's runtime grows logarithmically with input size. Typically seen in algorithms that divide the problem in half at each step, such as binary search.

**Characteristics:**
- Very efficient, second only to O(1)
- Common in divide-and-conquer algorithms
- Each iteration reduces problem size by a constant factor

```java
public class LogarithmicTimeExamples {
    
    // Example 1: Binary Search (Iterative)
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {  // O(log n) iterations
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;  // Eliminate left half
            } else {
                right = mid - 1;  // Eliminate right half
            }
        }
        return -1;
    }
    
    // Example 2: Binary Search (Recursive)
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }
    
    // Example 3: Finding power using exponentiation by squaring
    public static long power(int base, int exponent) {
        if (exponent == 0) return 1;
        
        long half = power(base, exponent / 2);  // O(log n)
        
        if (exponent % 2 == 0) {
            return half * half;
        } else {
            return base * half * half;
        }
    }
    
    // Example 4: Finding position in sorted rotated array
    public static int findRotationPoint(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {  // O(log n)
            int mid = left + (right - left) / 2;
            
            if (arr[mid] > arr[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    // Example 5: Count digits in a number
    public static int countDigits(int n) {
        if (n == 0) return 1;
        int count = 0;
        while (n != 0) {  // O(log₁₀ n)
            n /= 10;
            count++;
        }
        return count;
    }
}
```

### O(n) - Linear Time

**Definition:** The algorithm's runtime grows linearly with input size. Each element is typically processed once.

**Characteristics:**
- Scalable for moderate-sized inputs
- Common in single-pass algorithms
- Often optimal when all elements must be examined

```java
public class LinearTimeExamples {
    
    // Example 1: Linear Search
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {  // O(n)
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    // Example 2: Sum of array elements
    public static int sumArray(int[] arr) {
        int sum = 0;
        for (int num : arr) {  // O(n)
            sum += num;
        }
        return sum;
    }
    
    // Example 3: Find maximum element
    public static int findMax(int[] arr) {
        if (arr.length == 0) return Integer.MIN_VALUE;
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {  // O(n)
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    // Example 4: Count occurrences
    public static int countOccurrences(int[] arr, int target) {
        int count = 0;
        for (int num : arr) {  // O(n)
            if (num == target) {
                count++;
            }
        }
        return count;
    }
    
    // Example 5: Reverse array in-place
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {  // O(n/2) = O(n)
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    // Example 6: String traversal
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {  // O(n)
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    // Example 7: Copy array
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {  // O(n)
            copy[i] = arr[i];
        }
        return copy;
    }
}
```

### O(n log n) - Linearithmic Time

**Definition:** Combination of linear and logarithmic growth. Common in efficient sorting algorithms that use divide-and-conquer strategies.

**Characteristics:**
- Optimal for comparison-based sorting
- Better than O(n²) but slower than O(n)
- Common in merge sort, quick sort (average case), heap sort

```java
public class LinearithmicTimeExamples {
    
    // Example 1: Merge Sort
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSort(arr, left, mid);      // T(n/2)
            mergeSort(arr, mid + 1, right); // T(n/2)
            merge(arr, left, mid, right);    // O(n)
            // Total: O(n log n)
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        
        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }
    
    // Example 2: Quick Sort (Average Case)
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);  // O(n)
            quickSort(arr, low, pivotIndex - 1);         // T(n/2)
            quickSort(arr, pivotIndex + 1, high);        // T(n/2)
            // Average: O(n log n), Worst: O(n²)
        }
    }
    
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
    
    // Example 3: Heap Sort
    public static void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build max heap - O(n)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Extract elements from heap one by one - O(n log n)
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            heapify(arr, i, 0);  // O(log n)
        }
    }
    
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest])
            largest = left;
        
        if (right < n && arr[right] > arr[largest])
            largest = right;
        
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            
            heapify(arr, n, largest);
        }
    }
}
```

### O(n²) - Quadratic Time

**Definition:** Runtime grows quadratically with input size. Typically involves nested loops where each element is compared with every other element.

**Characteristics:**
- Poor scalability for large inputs
- Common in naive sorting algorithms
- Acceptable for small datasets

```java
public class QuadraticTimeExamples {
    
    // Example 1: Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {           // O(n)
            for (int j = 0; j < n - i - 1; j++) {   // O(n)
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        // Total: O(n²)
    }
    
    // Example 2: Selection Sort
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {           // O(n)
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {       // O(n)
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
    
    // Example 3: Insertion Sort
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {      // O(n)
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {        // O(n) worst case
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    // Example 4: Find all pairs with given sum
    public static void findPairsWithSum(int[] arr, int targetSum) {
        for (int i = 0; i < arr.length; i++) {           // O(n)
            for (int j = i + 1; j < arr.length; j++) {   // O(n)
                if (arr[i] + arr[j] == targetSum) {
                    System.out.println("(" + arr[i] + ", " + arr[j] + ")");
                }
            }
        }
    }
    
    // Example 5: Check for duplicate elements
    public static boolean hasDuplicates(int[] arr) {
        for (int i = 0; i < arr.length; i++) {           // O(n)
            for (int j = i + 1; j < arr.length; j++) {   // O(n)
                if (arr[i] == arr[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // Example 6: Matrix addition
    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {         // O(n)
            for (int j = 0; j < cols; j++) {     // O(n)
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;  // O(n²) for n×n matrix
    }
}
```

### O(n³) - Cubic Time

**Definition:** Runtime grows cubically with input size. Common in algorithms with three nested loops.

**Characteristics:**
- Very poor scalability
- Common in naive matrix multiplication
- Should be optimized when possible

```java
public class CubicTimeExamples {
    
    // Example 1: Matrix Multiplication (Naive)
    public static int[][] multiplyMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        
        for (int i = 0; i < n; i++) {           // O(n)
            for (int j = 0; j < n; j++) {       // O(n)
                for (int k = 0; k < n; k++) {   // O(n)
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;  // Total: O(n³)
    }
    
    // Example 2: Find all triplets with given sum
    public static void findTripletsWithSum(int[] arr, int targetSum) {
        int n = arr.length;
        
        for (int i = 0; i < n - 2; i++) {           // O(n)
            for (int j = i + 1; j < n - 1; j++) {   // O(n)
                for (int k = j + 1; k < n; k++) {   // O(n)
                    if (arr[i] + arr[j] + arr[k] == targetSum) {
                        System.out.println("(" + arr[i] + ", " + arr[j] + ", " + arr[k] + ")");
                    }
                }
            }
        }
    }
    
    // Example 3: Floyd-Warshall Algorithm (All pairs shortest path)
    public static int[][] floydWarshall(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        
        // Initialize distance matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        
        // Find shortest paths
        for (int k = 0; k < n; k++) {           // O(n)
            for (int i = 0; i < n; i++) {       // O(n)
                for (int j = 0; j < n; j++) {   // O(n)
                    if (dist[i][k] != Integer.MAX_VALUE && 
                        dist[k][j] != Integer.MAX_VALUE &&
                        dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }
}
```

### O(2^n) - Exponential Time

**Definition:** Runtime doubles with each additional element in input. Common in naive recursive solutions.

**Characteristics:**
- Extremely poor scalability
- Impractical for large inputs (n > 30)
- Often indicates need for dynamic programming optimization

```java
public class ExponentialTimeExamples {
    
    // Example 1: Fibonacci (Naive Recursive)
    public static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);  // O(2^n)
        // Creates binary tree of recursive calls
    }
    
    // Example 2: Tower of Hanoi
    public static void towerOfHanoi(int n, char from, char to, char aux) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + from + " to " + to);
            return;
        }
        towerOfHanoi(n - 1, from, aux, to);     // O(2^n)
        System.out.println("Move disk " + n + " from " + from + " to " + to);
        towerOfHanoi(n - 1, aux, to, from);
        // Total moves: 2^n - 1
    }
    
    // Example 3: Generate all subsets (Power Set)
    public static void generateSubsets(int[] arr, int index, 
                                      java.util.List<Integer> current,
                                      java.util.List<java.util.List<Integer>> result) {
        if (index == arr.length) {
            result.add(new java.util.ArrayList<>(current));
            return;
        }
        
        // Exclude current element
        generateSubsets(arr, index + 1, current, result);
        
        // Include current element
        current.add(arr[index]);
        generateSubsets(arr, index + 1, current, result);
        current.remove(current.size() - 1);
        // Total subsets: 2^n
    }
    
    // Example 4: Recursive subset sum
    public static boolean hasSubsetSum(int[] arr, int n, int sum) {
        if (sum == 0) return true;
        if (n == 0) return false;
        
        // Exclude last element or include it
        return hasSubsetSum(arr, n - 1, sum) || 
               hasSubsetSum(arr, n - 1, sum - arr[n - 1]);
        // O(2^n) without memoization
    }
}
```

### O(n!) - Factorial Time

**Definition:** Runtime grows factorially with input size. Most inefficient complexity class.

**Characteristics:**
- Only practical for very small inputs (n < 12)
- Common in brute-force permutation problems
- Should always be optimized if possible

```java
public class FactorialTimeExamples {
    
    // Example 1: Generate all permutations
    public static void generatePermutations(int[] arr, int start, 
                                           java.util.List<int[]> result) {
        if (start == arr.length - 1) {
            result.add(arr.clone());
            return;
        }
        
        for (int i = start; i < arr.length; i++) {  // O(n!)
            swap(arr, start, i);
            generatePermutations(arr, start + 1, result);
            swap(arr, start, i);  // backtrack
        }
        // Total permutations: n!
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    // Example 2: Traveling Salesman Problem (Brute Force)
    public static int tspBruteForce(int[][] graph, boolean[] visited, 
                                    int currPos, int n, int count, 
                                    int cost, int minCost) {
        if (count == n && graph[currPos][0] > 0) {
            return Math.min(minCost, cost + graph[currPos][0]);
        }
        
        for (int i = 0; i < n; i++) {  // O(n!)
            if (!visited[i] && graph[currPos][i] > 0) {
                visited[i] = true;
                minCost = tspBruteForce(graph, visited, i, n, 
                                       count + 1, 
                                       cost + graph[currPos][i], 
                                       minCost);
                visited[i] = false;
            }
        }
        return minCost;
    }
    
    // Example 3: N-Queens Problem (Backtracking)
    public static void solveNQueens(int n) {
        int[][] board = new int[n][n];
        solveNQueensUtil(board, 0, n);
    }
    
    private static boolean solveNQueensUtil(int[][] board, int col, int n) {
        if (col >= n) return true;
        
        for (int i = 0; i < n; i++) {  // O(n!) with backtracking
            if (isSafe(board, i, col, n)) {
                board[i][col] = 1;
                
                if (solveNQueensUtil(board, col + 1, n))
                    return true;
                
                board[i][col] = 0;  // backtrack
            }
        }
        return false;
    }
    
    private static boolean isSafe(int[][] board, int row, int col, int n) {
        // Check row on left side
        for (int j = 0; j < col; j++)
            if (board[row][j] == 1) return false;
        
        // Check upper diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1) return false;
        
        // Check lower diagonal
        for (int i = row, j = col; i < n && j >= 0; i++, j--)
            if (board[i][j] == 1) return false;
        
        return true;
    }
}
```

## Best, Worst, and Average Cases

Time complexity analysis often considers three scenarios:

**Best Case (Ω - Omega):** Minimum time required for any input of size n. Represents the most favorable scenario.

**Average Case (Θ - Theta):** Expected time for random input of size n. Most realistic for practical analysis.

**Worst Case (O - Big O):** Maximum time required for any input of size n. Used for guaranteeing upper bounds.

```java
public class ComplexityCasesExample {
    
    // Quick Sort: Demonstrates all three cases
    public static void quickSortAnalysis(int[] arr, int low, int high) {
        // Best Case: O(n log n) - pivot always divides array in half
        // Average Case: O(n log n) - random pivot distribution
        // Worst Case: O(n²) - pivot is always smallest/largest element
        
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortAnalysis(arr, low, pi - 1);
            quickSortAnalysis(arr, pi + 1, high);
        }
    }
    
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
    
    // Linear Search example
    public static int linearSearchCases(int[] arr, int target) {
        // Best Case: O(1) - element is at first position
        // Average Case: O(n/2) = O(n) - element is in middle
        // Worst Case: O(n) - element is at end or not present
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }
}
```

## Amortized Analysis

Amortized analysis provides the average performance of each operation in the worst case over a sequence of operations. It's useful when an expensive operation is rare and offset by many cheap operations.

**Key Concept:** While individual operations may be expensive, the average cost per operation over a sequence remains low.

```java
import java.util.ArrayList;

public class AmortizedAnalysisExample {
    
    // Dynamic Array (ArrayList) - Amortized O(1) insertion
    public static void demonstrateAmortizedComplexity() {
        ArrayList<Integer> list = new ArrayList<>();
        
        // Most insertions are O(1)
        // Occasionally O(n) when array needs resizing
        // Amortized: O(1) per insertion
        
        for (int i = 0; i < 1000; i++) {
            list.add(i);  // Amortized O(1)
        }
    }
    
    // Custom Dynamic Array implementation
    static class DynamicArray {
        private int[] arr;
        private int size;
        private int capacity;
        
        public DynamicArray() {
            capacity = 2;
            arr = new int[capacity];
            size = 0;
        }
        
        public void add(int element) {
            if (size == capacity) {
                // Resize operation: O(n)
                // Happens only when size = 2, 4, 8, 16, 32...
                capacity *= 2;
                int[] newArr = new int[capacity];
                for (int i = 0; i < size; i++) {
                    newArr[i] = arr[i];
                }
                arr = newArr;
            }
            arr[size++] = element;  // O(1)
        }
        
        // Analysis:
        // After n insertions:
        // - Number of O(1) operations: n
        // - Number of O(n) operations: log(n) (resize operations)
        // - Total cost: n + 2 + 4 + 8 + ... + n = n + 2n = 3n
        // - Amortized cost per insertion: 3n/n = O(1)
    }
}
```

## Master Theorem

The Master Theorem provides a cookbook method for solving recurrence relations of the form:

**T(n) = aT(n/b) + f(n)**

Where:
- a ≥ 1: number of subproblems
- b > 1: factor by which input size is reduced
- f(n): cost of work done outside recursive calls

**Three Cases:**

1. **Case 1:** If f(n) = O(n^c) where c < log_b(a)
   - **T(n) = Θ(n^(log_b(a)))**

2. **Case 2:** If f(n) = Θ(n^c * log^k(n)) where c = log_b(a)
   - **T(n) = Θ(n^c * log^(k+1)(n))**

3. **Case 3:** If f(n) = Ω(n^c) where c > log_b(a)
   - **T(n) = Θ(f(n))**

```java
public class MasterTheoremExamples {
    
    // Example 1: Binary Search
    // T(n) = T(n/2) + O(1)
    // a=1, b=2, f(n)=O(1)=O(n^0)
    // log_b(a) = log_2(1) = 0
    // Case 2: c = log_b(a) → T(n) = O(log n)
    public static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target)
            return binarySearch(arr, target, mid + 1, right);
        return binarySearch(arr, target, left, mid - 1);
    }
    
    // Example 2: Merge Sort
    // T(n) = 2T(n/2) + O(n)
    // a=2, b=2, f(n)=O(n)
    // log_b(a) = log_2(2) = 1
    // Case 2: c = log_b(a) → T(n) = O(n log n)
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);  // O(n)
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        // O(n) merging operation
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int i = 0; i < n2; i++) R[i] = arr[mid + 1 + i];
        
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
    
    // Example 3: Matrix Multiplication (Strassen's Algorithm - Theoretical)
    // T(n) = 7T(n/2) + O(n²)
    // a=7, b=2, f(n)=O(n²)
    // log_b(a) = log_2(7) ≈ 2.807
    // Case 1: c=2 < log_b(a) → T(n) = O(n^2.807)
}
```

## How to Calculate Time Complexity

**Step-by-Step Process:**

1. **Identify basic operations:** Focus on operations that contribute most to runtime
2. **Count iterations:** Determine how many times each loop executes
3. **Analyze nested structures:** Multiply complexities of nested loops
4. **Consider recursive calls:** Use recurrence relations
5. **Apply rules:** Drop constants and lower-order terms
6. **Determine dominant term:** The highest-growing term determines complexity

```java
public class CalculatingComplexity {
    
    // Example 1: Simple calculation
    public static void example1(int n) {
        int sum = 0;           // O(1)
        for (int i = 0; i < n; i++) {  // O(n)
            sum += i;          // O(1)
        }
        // Total: O(1) + O(n) * O(1) = O(n)
    }
    
    // Example 2: Nested loops with same bound
    public static void example2(int n) {
        for (int i = 0; i < n; i++) {        // O(n)
            for (int j = 0; j < n; j++) {    // O(n)
                System.out.println(i + j);   // O(1)
            }
        }
        // Total: O(n) * O(n) * O(1) = O(n²)
    }
    
    // Example 3: Sequential loops
    public static void example3(int n) {
        for (int i = 0; i < n; i++) {     // O(n)
            System.out.println(i);
        }
        for (int j = 0; j < n; j++) {     // O(n)
            System.out.println(j);
        }
        // Total: O(n) + O(n) = O(2n) = O(n)
    }
    
    // Example 4: Different bounds
    public static void example4(int n, int m) {
        for (int i = 0; i < n; i++) {        // O(n)
            for (int j = 0; j < m; j++) {    // O(m)
                System.out.println(i + j);
            }
        }
        // Total: O(n * m) - cannot simplify further
    }
    
    // Example 5: Logarithmic loop
    public static void example5(int n) {
        for (int i = 1; i < n; i *= 2) {  // O(log n)
            System.out.println(i);
        }
        // i doubles each iteration: 1, 2, 4, 8, ...
        // Stops when i ≥ n
        // 2^k = n → k = log₂(n)
    }
    
    // Example 6: Complex nested structure
    public static void example6(int n) {
        for (int i = 0; i < n; i++) {           // O(n)
            for (int j = 0; j < i; j++) {       // O(i) average = O(n/2)
                for (int k = 0; k < n; k++) {   // O(n)
                    System.out.println(i + j + k);
                }
            }
        }
        // Total: O(n) * O(n/2) * O(n) = O(n³/2) = O(n³)
    }
}
```

## Complexity Comparison

### Growth Rate Table

| Complexity | n=10 | n=100 | n=1,000 | n=10,000 | n=100,000 |
|------------|------|-------|---------|----------|-----------|
| **O(1)**   | 1 | 1 | 1 | 1 | 1 |
| **O(log n)** | 3 | 7 | 10 | 13 | 17 |
| **O(n)**   | 10 | 100 | 1,000 | 10,000 | 100,000 |
| **O(n log n)** | 30 | 700 | 10,000 | 130,000 | 1,700,000 |
| **O(n²)**  | 100 | 10,000 | 1,000,000 | 100,000,000 | 10,000,000,000 |
| **O(n³)**  | 1,000 | 1,000,000 | 1,000,000,000 | 10¹² | 10¹⁵ |
| **O(2^n)** | 1,024 | 1.27×10³⁰ | ∞ | ∞ | ∞ |
| **O(n!)**  | 3,628,800 | ∞ | ∞ | ∞ | ∞ |

### Algorithm Comparison Chart

| Algorithm | Best | Average | Worst | Space |
|-----------|------|---------|-------|-------|
| **Bubble Sort** | O(n) | O(n²) | O(n²) | O(1) |
| **Selection Sort** | O(n²) | O(n²) | O(n²) | O(1) |
| **Insertion Sort** | O(n) | O(n²) | O(n²) | O(1) |
| **Merge Sort** | O(n log n) | O(n log n) | O(n log n) | O(n) |
| **Quick Sort** | O(n log n) | O(n log n) | O(n²) | O(log n) |
| **Heap Sort** | O(n log n) | O(n log n) | O(n log n) | O(1) |
| **Binary Search** | O(1) | O(log n) | O(log n) | O(1) |
| **Linear Search** | O(1) | O(n) | O(n) | O(1) |

## Practice Tips

1. **Start with loops:** Count iterations and multiply for nested structures
2. **Recognize patterns:** Learn to identify common complexity patterns quickly
3. **Practice recurrence relations:** Master solving recursive time complexities
4. **Consider input characteristics:** Think about best, worst, and average cases
5. **Verify empirically:** Test your analysis with different input sizes
6. **Focus on scalability:** Consider how algorithms perform with large datasets
7. **Learn standard algorithms:** Know the complexity of common algorithms by heart
8. **Trade-offs:** Understand space-time trade-offs in algorithm design

### Key Takeaways

- **O(1), O(log n), O(n), O(n log n)** are generally acceptable for production code
- **O(n²)** is acceptable for small inputs but problematic for large datasets
- **O(2^n), O(n!)** require optimization through dynamic programming or pruning
- Always consider the expected input size when choosing algorithms
- Big O describes worst-case behavior; amortized analysis provides average behavior
- Master theorem simplifies analysis of divide-and-conquer algorithms

---

**Next Steps:** Practice analyzing the time complexity of your own code. Start with simple algorithms and gradually move to more complex recursive and iterative solutions. Understanding time complexity is fundamental to writing efficient, scalable code.
