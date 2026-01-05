/**
 * Searching Algorithms Implementation
 * Demonstrates: Linear Search, Binary Search, and Jump Search
 * 
 * Learning Objectives:
 * - Implement fundamental search algorithms
 * - Understand search optimization techniques
 * - Learn when to use each algorithm
 * - Analyze search algorithm efficiency
 */

public class SearchingImplementation {
    
    // ===== LINEAR SEARCH =====
    // Simplest search algorithm: check each element sequentially
    // Works on both sorted and unsorted arrays
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Target found at index i
            }
        }
        return -1; // Target not found
    }
    
    // Linear search returning all occurrences
    public static int[] linearSearchAll(int[] arr, int target) {
        int count = 0;
        
        // Count occurrences first
        for (int value : arr) {
            if (value == target) count++;
        }
        
        if (count == 0) return new int[0];
        
        int[] result = new int[count];
        int index = 0;
        
        // Store all indices
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                result[index++] = i;
            }
        }
        
        return result;
    }
    
    // ===== BINARY SEARCH =====
    // Efficient search for SORTED arrays only
    // Divides search space in half with each comparison
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoid overflow
            
            if (arr[mid] == target) {
                return mid; // Target found
            } else if (arr[mid] < target) {
                left = mid + 1; // Search right half
            } else {
                right = mid - 1; // Search left half
            }
        }
        
        return -1; // Target not found
    }
    
    // Recursive binary search
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: target not found
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
    
    // Find first occurrence of target in sorted array
    public static int binarySearchFirst(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1; // Continue searching left
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // Find last occurrence of target in sorted array
    public static int binarySearchLast(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;
                left = mid + 1; // Continue searching right
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // ===== JUMP SEARCH =====
    // Optimal algorithm for sorted arrays when both linear and binary aren't suitable
    // Jump fixed steps, then linear search in the block
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        int step = (int) Math.sqrt(n); // Optimal step size
        int prev = 0;
        
        // Finding the block where target is present
        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.sqrt(n);
            if (prev >= n) return -1; // Target not found
        }
        
        // Linear search in the identified block
        while (arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) {
                return -1; // Target not found
            }
        }
        
        // Check if target is found
        if (arr[prev] == target) {
            return prev;
        }
        
        return -1;
    }
    
    // ===== EXPONENTIAL SEARCH =====
    // Useful when you don't know the range of values
    // Finds a range and then applies binary search
    public static int exponentialSearch(int[] arr, int target) {
        if (arr[0] == target) {
            return 0;
        }
        
        // Find range where target is present
        int i = 1;
        while (i < arr.length && arr[i] < target) {
            i *= 2;
        }
        
        // Apply binary search on the range
        return binarySearch(arr, 0, Math.min(i, arr.length - 1), target);
    }
    
    // Helper method for binary search within range
    private static int binarySearch(int[] arr, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    // ===== SENTINEL LINEAR SEARCH =====
    // Optimization of linear search by eliminating one comparison
    public static int sentinelLinearSearch(int[] arr, int target) {
        int n = arr.length;
        int last = arr[n - 1];
        arr[n - 1] = target;
        
        int i = 0;
        while (arr[i] != target) {
            i++;
        }
        
        // Restore last element
        arr[n - 1] = last;
        
        // Check if found at last position or not found
        if (i < n - 1 || last == target) {
            return i;
        }
        
        return -1;
    }
    
    // ===== UTILITY METHODS =====
    
    // Check if array is sorted
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    // Print array
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    // Print array with indices
    public static void printArrayWithIndices(int[] arr) {
        System.out.println("Array:");
        printArray(arr);
        System.out.print("Index: [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(i);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        System.out.println("===== SEARCHING ALGORITHMS DEMO =====\n");
        
        int[] unsortedArr = {64, 34, 25, 12, 22, 11, 90};
        int[] sortedArr = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        int[] arrWithDuplicates = {5, 5, 10, 10, 15, 15, 20, 20, 25, 25};
        
        // ===== LINEAR SEARCH =====
        System.out.println("--- LINEAR SEARCH ---");
        System.out.println("Unsorted Array: ");
        printArray(unsortedArr);
        int target = 22;
        int result = linearSearch(unsortedArr, target);
        System.out.println("Searching for " + target + ": " + 
                         (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println();
        
        // ===== LINEAR SEARCH ALL OCCURRENCES =====
        System.out.println("--- LINEAR SEARCH (ALL OCCURRENCES) ---");
        System.out.println("Array with duplicates: ");
        printArray(arrWithDuplicates);
        target = 10;
        int[] allResults = linearSearchAll(arrWithDuplicates, target);
        System.out.println("Searching for " + target + ": Found at indices " + 
                         java.util.Arrays.toString(allResults));
        System.out.println();
        
        // ===== BINARY SEARCH =====
        System.out.println("--- BINARY SEARCH ---");
        System.out.println("Sorted Array: ");
        printArray(sortedArr);
        target = 30;
        result = binarySearch(sortedArr, target);
        System.out.println("Searching for " + target + ": " + 
                         (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println();
        
        // ===== BINARY SEARCH RECURSIVE =====
        System.out.println("--- BINARY SEARCH (RECURSIVE) ---");
        result = binarySearchRecursive(sortedArr, 15, 0, sortedArr.length - 1);
        System.out.println("Searching for 15 (recursive): " + 
                         (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println();
        
        // ===== BINARY SEARCH FIRST AND LAST =====
        System.out.println("--- BINARY SEARCH (FIRST/LAST OCCURRENCE) ---");
        System.out.println("Array with duplicates: ");
        printArray(arrWithDuplicates);
        target = 15;
        int first = binarySearchFirst(arrWithDuplicates, target);
        int last = binarySearchLast(arrWithDuplicates, target);
        System.out.println("Searching for " + target + ":");
        System.out.println("  First occurrence at index: " + 
                         (first != -1 ? first : "Not found"));
        System.out.println("  Last occurrence at index: " + 
                         (last != -1 ? last : "Not found"));
        System.out.println();
        
        // ===== JUMP SEARCH =====
        System.out.println("--- JUMP SEARCH ---");
        System.out.println("Sorted Array: ");
        printArray(sortedArr);
        target = 25;
        result = jumpSearch(sortedArr, target);
        System.out.println("Searching for " + target + ": " + 
                         (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println();
        
        // ===== EXPONENTIAL SEARCH =====
        System.out.println("--- EXPONENTIAL SEARCH ---");
        System.out.println("Sorted Array: ");
        printArray(sortedArr);
        target = 40;
        result = exponentialSearch(sortedArr, target);
        System.out.println("Searching for " + target + ": " + 
                         (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println();
        
        // ===== SENTINEL LINEAR SEARCH =====
        System.out.println("--- SENTINEL LINEAR SEARCH ---");
        int[] sentinelArr = unsortedArr.clone();
        System.out.println("Unsorted Array: ");
        printArray(sentinelArr);
        target = 11;
        result = sentinelLinearSearch(sentinelArr, target);
        System.out.println("Searching for " + target + ": " + 
                         (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println();
        
        // ===== PERFORMANCE COMPARISON =====
        System.out.println("--- PERFORMANCE COMPARISON ---");
        int[] largeArr = new int[100000];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = i;
        }
        
        int searchTarget = 75000;
        
        // Linear Search
        long start = System.nanoTime();
        linearSearch(largeArr, searchTarget);
        long linearTime = System.nanoTime() - start;
        
        // Binary Search
        start = System.nanoTime();
        binarySearch(largeArr, searchTarget);
        long binaryTime = System.nanoTime() - start;
        
        // Jump Search
        start = System.nanoTime();
        jumpSearch(largeArr, searchTarget);
        long jumpTime = System.nanoTime() - start;
        
        System.out.println("Searching for " + searchTarget + " in 100,000 sorted elements:");
        System.out.println("Linear Search: " + linearTime / 1000 + " µs");
        System.out.println("Binary Search: " + binaryTime / 1000 + " µs");
        System.out.println("Jump Search: " + jumpTime / 1000 + " µs");
    }
}
