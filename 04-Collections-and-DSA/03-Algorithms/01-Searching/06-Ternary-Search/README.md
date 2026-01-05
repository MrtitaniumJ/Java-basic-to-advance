# Ternary Search

## Overview
Ternary Search is a divide-and-conquer algorithm used to find the maximum or minimum value in a unimodal (or bitonic) function. A unimodal function is one that has a single peak (for maximum) or valley (for minimum) - it increases up to a point and then decreases, or decreases up to a point and then increases. Ternary search divides the search interval into three parts instead of two (like binary search), making it particularly efficient for this use case.

## How It Works

### Algorithm Concept
Ternary search works by dividing the search interval into three equal parts and eliminating one-third of the interval in each iteration:
1. **Divide**: Split the range `[low, high]` into three equal parts using two midpoints: `mid1` and `mid2`
2. **Compare**: Compare the function values at `mid1` and `mid2`
3. **Eliminate**: Based on the comparison, eliminate one-third of the range
4. **Repeat**: Continue until the range converges to the maximum/minimum

The key insight is that in a unimodal function, the maximum/minimum must lie in one of the two remaining intervals.

### Mathematical Foundation
For a unimodal function with single peak/valley, if `f(mid1) < f(mid2)`:
- Maximum must be in `[mid1, high]`
- We can safely eliminate `[low, mid1]`

Similarly, if `f(mid1) > f(mid2)`:
- Maximum must be in `[low, mid2]`
- We can safely eliminate `[mid2, high]`

### Example
Consider function: `f(x) = -(x-5)² + 25` (peak at x=5) on range [0, 10]
- Iteration 1: `mid1=3, mid2=7`, f(mid1)=16, f(mid2)=16 → Equal, narrow range
- Iteration 2: Continue narrowing until maximum at x=5 is found
- Time: O(log₃ n) ≈ O(log n)

## Time and Space Complexity

| Scenario | Complexity | Notes |
|----------|-----------|-------|
| **Best Case** | O(1) | Element at first midpoint |
| **Average Case** | O(log₃ n) | Divide into 3 parts each iteration |
| **Worst Case** | O(log₃ n) | Guaranteed logarithmic |
| **Space Complexity (Iterative)** | O(1) | No extra space needed |
| **Space Complexity (Recursive)** | O(log n) | Call stack depth |

## Time Complexity Comparison

- Binary search: O(log₂ n) ≈ O(1.0 × log n)
- Ternary search: O(log₃ n) ≈ O(0.63 × log n)
- Linear search: O(n)

Ternary search eliminates 2/3 of range per iteration, vs 1/2 for binary search.

## When to Use Ternary Search

### ✅ Ideal Scenarios
- **Finding peaks/valleys**: Unimodal function optimization
- **Maximum/minimum finding**: When function is unimodal
- **Bitonic arrays**: Arrays that first increase then decrease (or vice versa)
- **Continuous functions**: Smooth unimodal distributions
- **Performance critical**: Slight improvement over binary search

### ❌ Avoid When
- **Multi-modal functions**: Multiple peaks/valleys present
- **Unknown function behavior**: Not guaranteed to be unimodal
- **Discrete binary search**: Use binary search instead
- **Simple data**: Binary search is simpler and comparable performance
- **Noisy data**: May not work well with non-smooth functions

## Comparison with Other Algorithms

| Algorithm | Best Case | Avg Case | Worst Case | Use When |
|-----------|-----------|----------|-----------|----------|
| **Linear Search** | O(1) | O(n) | O(n) | Unsorted, small |
| **Binary Search** | O(1) | O(log₂ n) | O(log₂ n) | Sorted array/monotonic |
| **Ternary Search** | O(1) | O(log₃ n) | O(log₃ n) | Unimodal function |
| **Golden Section** | O(1) | O(log n) | O(log n) | Continuous optimization |

## Java Implementation

```java
public class TernarySearch {

    /**
     * Finds the peak (maximum) in a unimodal array
     * @param arr - unimodal array (increases then decreases)
     * @return index of peak element
     */
    public static int findPeak(int[] arr) {
        int low = 0;
        int high = arr.length - 1;

        // Handle single or two element arrays
        if (arr.length <= 1) return 0;
        if (arr[0] > arr[1]) return 0;
        if (arr[arr.length - 1] > arr[arr.length - 2]) return arr.length - 1;

        while (low < high) {
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            // If peak is in left region
            if (arr[mid1] > arr[mid2]) {
                high = mid2 - 1;
            }
            // If peak is in right region
            else {
                low = mid1 + 1;
            }
        }

        return low;
    }

    /**
     * Iterative ternary search for finding maximum in unimodal function
     * @param arr - unimodal array
     * @return index of maximum element
     */
    public static int ternarySearchMax(int[] arr) {
        int low = 0;
        int high = arr.length - 1;

        while (low < high) {
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            if (arr[mid1] < arr[mid2]) {
                low = mid1 + 1;
            } else {
                high = mid2 - 1;
            }
        }

        return low;
    }

    /**
     * Iterative ternary search with detailed steps
     */
    public static int ternarySearchWithSteps(int[] arr) {
        System.out.println("\n--- Ternary Search Process ---");
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("Finding peak (maximum):\n");

        int low = 0;
        int high = arr.length - 1;
        int iteration = 0;

        while (low < high) {
            iteration++;
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            System.out.println("Iteration " + iteration + ":");
            System.out.println("  Range: [" + low + ", " + high + "]");
            System.out.println("  mid1=" + mid1 + " (value: " + arr[mid1] + "), " + 
                              "mid2=" + mid2 + " (value: " + arr[mid2] + ")");

            if (arr[mid1] < arr[mid2]) {
                System.out.println("  arr[mid1] < arr[mid2], eliminate left part");
                low = mid1 + 1;
            } else {
                System.out.println("  arr[mid1] >= arr[mid2], eliminate right part");
                high = mid2 - 1;
            }
        }

        System.out.println("\nPeak found at index " + low + 
                          " with value " + arr[low]);
        return low;
    }

    /**
     * Recursive ternary search
     */
    public static int ternarySearchRecursive(int[] arr, int low, int high) {
        if (low == high) {
            return low;
        }

        int mid1 = low + (high - low) / 3;
        int mid2 = high - (high - low) / 3;

        if (arr[mid1] < arr[mid2]) {
            return ternarySearchRecursive(arr, mid1 + 1, high);
        } else {
            return ternarySearchRecursive(arr, low, mid2 - 1);
        }
    }

    /**
     * Ternary search on continuous function (using sampling)
     */
    public static double ternarySearchContinuous(FunctionInterface f, 
                                                double low, double high, 
                                                double epsilon) {
        System.out.println("\n--- Continuous Ternary Search ---");
        int iterations = 0;

        while ((high - low) > epsilon) {
            iterations++;
            double mid1 = low + (high - low) / 3;
            double mid2 = high - (high - low) / 3;

            if (f.evaluate(mid1) < f.evaluate(mid2)) {
                low = mid1;
            } else {
                high = mid2;
            }

            System.out.println("Iteration " + iterations + ": Range [" + 
                              String.format("%.4f", low) + ", " + 
                              String.format("%.4f", high) + "]");
        }

        double result = (low + high) / 2;
        System.out.println("Maximum found at x ≈ " + String.format("%.4f", result) + 
                          " (Iterations: " + iterations + ")");
        return result;
    }

    /**
     * Functional interface for continuous functions
     */
    @FunctionalInterface
    public interface FunctionInterface {
        double evaluate(double x);
    }

    /**
     * Compare ternary vs binary search on unimodal array
     */
    public static void compareSearchMethods(int[] arr) {
        System.out.println("\n--- Algorithm Comparison ---");
        System.out.println("Array: " + java.util.Arrays.toString(arr));

        // Ternary Search
        long startTime = System.nanoTime();
        int result1 = ternarySearchMax(arr);
        long endTime = System.nanoTime();
        System.out.println("Ternary Search:   Peak at index " + result1 + 
                          " (Time: " + (endTime - startTime) + " ns)");

        // Binary-like search (modified for unimodal)
        startTime = System.nanoTime();
        int result2 = findPeak(arr);
        endTime = System.nanoTime();
        System.out.println("Peak Finding:     Peak at index " + result2 + 
                          " (Time: " + (endTime - startTime) + " ns)");

        // Linear scan for comparison
        startTime = System.nanoTime();
        int result3 = linearFindMax(arr);
        endTime = System.nanoTime();
        System.out.println("Linear Scan:      Peak at index " + result3 + 
                          " (Time: " + (endTime - startTime) + " ns)");
    }

    /**
     * Linear search to find maximum
     */
    private static int linearFindMax(int[] arr) {
        int maxIdx = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIdx]) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    /**
     * Validate if array is unimodal (increases then decreases)
     */
    public static boolean isUnimodal(int[] arr) {
        int n = arr.length;
        if (n <= 2) return true;

        // Find the peak
        int i = 0;
        // Ascending part
        while (i + 1 < n && arr[i] < arr[i + 1]) {
            i++;
        }

        // Descending part
        while (i + 1 < n) {
            if (arr[i] < arr[i + 1]) {
                return false; // Found ascending after descending
            }
            i++;
        }

        return true;
    }

    /**
     * Ternary search on double precision values
     */
    public static double ternarySearchDouble(double[] arr) {
        int low = 0;
        int high = arr.length - 1;

        while (low < high) {
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            if (arr[mid1] < arr[mid2]) {
                low = mid1 + 1;
            } else {
                high = mid2 - 1;
            }
        }

        return arr[low];
    }

    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Ternary Search Demo ===\n");

        // Test Case 1: Basic unimodal array
        int[] basicArray = {1, 3, 5, 7, 9, 15, 20, 25, 30, 28, 26, 20, 15};
        System.out.println("Test Case 1: Basic Unimodal Array");
        System.out.println("Array: " + java.util.Arrays.toString(basicArray));
        System.out.println("Peak index: " + ternarySearchMax(basicArray) + 
                          " (value: " + basicArray[ternarySearchMax(basicArray)] + ")");

        // Test Case 2: Detailed process
        System.out.println("\nTest Case 2: Detailed Process");
        ternarySearchWithSteps(basicArray);

        // Test Case 3: Validation and comparison
        System.out.println("\nTest Case 3: Algorithm Comparison");
        compareSearchMethods(basicArray);

        // Test Case 4: Recursive implementation
        System.out.println("\nTest Case 4: Recursive Implementation");
        int result = ternarySearchRecursive(basicArray, 0, basicArray.length - 1);
        System.out.println("Recursive peak: " + result + 
                          " (value: " + basicArray[result] + ")");

        // Test Case 5: Edge cases
        System.out.println("\nTest Case 5: Edge Cases");
        int[] singleElement = {42};
        System.out.println("Single element [42]: Peak at " + ternarySearchMax(singleElement));

        int[] twoElements = {10, 20};
        System.out.println("Two elements [10, 20]: Peak at " + ternarySearchMax(twoElements));

        // Test Case 6: Large unimodal array
        System.out.println("\nTest Case 6: Large Array Performance");
        int[] largeArray = new int[10000];
        int peak = 5000;
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (i < peak) ? i : (10000 - i);
        }
        compareSearchMethods(largeArray);

        // Test Case 7: Different peak positions
        System.out.println("\nTest Case 7: Peak at Different Positions");
        int[] peakLeft = {1, 2, 3, 4, 5, 4, 3, 2, 1};
        int[] peakCenter = {1, 3, 5, 7, 9, 7, 5, 3, 1};
        int[] peakRight = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        System.out.println("Peak on left: " + ternarySearchMax(peakLeft) + 
                          " (value: " + peakLeft[ternarySearchMax(peakLeft)] + ")");
        System.out.println("Peak center: " + ternarySearchMax(peakCenter) + 
                          " (value: " + peakCenter[ternarySearchMax(peakCenter)] + ")");
        System.out.println("Peak right: " + ternarySearchMax(peakRight) + 
                          " (value: " + peakRight[ternarySearchMax(peakRight)] + ")");

        // Test Case 8: Double precision array
        System.out.println("\nTest Case 8: Double Precision Array");
        double[] doubleArray = {1.1, 2.5, 3.8, 5.2, 7.3, 6.1, 4.9, 3.2};
        double maxValue = ternarySearchDouble(doubleArray);
        System.out.println("Double array: " + java.util.Arrays.toString(doubleArray));
        System.out.println("Maximum value: " + maxValue);

        // Test Case 9: Unimodal validation
        System.out.println("\nTest Case 9: Unimodal Validation");
        System.out.println("basicArray is unimodal: " + isUnimodal(basicArray));
        System.out.println("peakRight is unimodal: " + isUnimodal(peakRight));

        int[] nonUnimodal = {1, 3, 2, 4, 5};
        System.out.println("nonUnimodal [1,3,2,4,5] is unimodal: " + 
                          isUnimodal(nonUnimodal));

        // Test Case 10: Continuous function optimization
        System.out.println("\nTest Case 10: Continuous Function Optimization");
        // f(x) = -(x-3)^2 + 10 (peak at x=3)
        FunctionInterface f = x -> -(x - 3) * (x - 3) + 10;
        double result1 = ternarySearchContinuous(f, 0, 5, 0.001);
        System.out.println("f(x) at maximum: " + String.format("%.4f", f.evaluate(result1)));

        // g(x) = sin(x) on [0, 2π] (peak near π/2)
        FunctionInterface g = Math::sin;
        double result2 = ternarySearchContinuous(g, 0, Math.PI, 0.0001);
        System.out.println("sin(x) at maximum: " + String.format("%.4f", g.evaluate(result2)));
    }
}
```

## Key Points to Remember
- ✅ Optimal for unimodal functions (single peak/valley)
- ✅ O(log₃ n) complexity - faster than binary search theoretically
- ✅ Eliminates 2/3 of search space per iteration
- ⚠️ Only works on monotonic or unimodal functions
- ✅ Can be applied to continuous functions with proper sampling
- ✅ Simple and elegant implementation
- ✅ Guaranteed convergence on valid input

## Common Applications
1. **Peak finding**: Find maximum in mountain-shaped arrays
2. **Function optimization**: Optimize smooth unimodal functions
3. **Resource allocation**: Finding optimal allocation levels
4. **Tuning parameters**: Finding best parameter values
5. **Temperature/pressure curves**: Physical phenomena optimization

## Practice Exercises
1. Implement ternary search for finding minimum instead of maximum
2. Extend to handle bitonic (decreasing then increasing) arrays
3. Implement adaptive variant that detects non-unimodal functions
4. Create version for finding exact valley in V-shaped arrays
5. Benchmark against golden section search for continuous functions
6. Implement 2D ternary search for peak finding in matrices
