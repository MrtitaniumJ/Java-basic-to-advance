/**
 * Sorting Implementation Program
 * Demonstrates: Bubble Sort, Merge Sort, and Quick Sort algorithms
 * 
 * Learning Objectives:
 * - Implement classic sorting algorithms from scratch
 * - Understand algorithm efficiency and complexity
 * - Learn comparison-based sorting techniques
 * - Master divide-and-conquer approach
 */

public class SortingImplementation {
    
    // ===== BUBBLE SORT =====
    // Simple comparison-based algorithm that repeatedly steps through the list
    // Compares adjacent elements and swaps if they're in wrong order
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        
        // Outer loop for each pass
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            // Inner loop for comparisons in current pass
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // Optimization: if no swaps occurred, array is already sorted
            if (!swapped) {
                break;
            }
        }
    }
    
    // ===== MERGE SORT =====
    // Divide-and-conquer algorithm that divides array in half recursively
    // Then merges sorted subarrays back together
    public static void mergeSort(int[] arr) {
        if (arr.length == 0) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            // Find middle point
            int mid = left + (right - left) / 2;
            
            // Sort left half
            mergeSortHelper(arr, left, mid);
            
            // Sort right half
            mergeSortHelper(arr, mid + 1, right);
            
            // Merge sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        // Create temporary arrays
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        
        int[] leftArr = new int[leftSize];
        int[] rightArr = new int[rightSize];
        
        // Copy data to temporary arrays
        System.arraycopy(arr, left, leftArr, 0, leftSize);
        System.arraycopy(arr, mid + 1, rightArr, 0, rightSize);
        
        // Merge temporary arrays back
        int i = 0, j = 0, k = left;
        
        while (i < leftSize && j < rightSize) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        
        // Copy remaining elements from left array
        while (i < leftSize) {
            arr[k++] = leftArr[i++];
        }
        
        // Copy remaining elements from right array
        while (j < rightSize) {
            arr[k++] = rightArr[j++];
        }
    }
    
    // ===== QUICK SORT =====
    // Divide-and-conquer algorithm using pivot element
    // Elements less than pivot go left, greater go right, then recursively sort
    public static void quickSort(int[] arr) {
        if (arr.length == 0) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            // Partition array and get pivot index
            int pivotIndex = partition(arr, low, high);
            
            // Recursively sort elements before and after partition
            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }
    
    private static int partition(int[] arr, int low, int high) {
        // Choose last element as pivot
        int pivot = arr[high];
        
        // Index of smaller element - indicates right position of pivot
        int i = low - 1;
        
        // Traverse through all elements
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Swap arr[i+1] and arr[high] (pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    // ===== INSERTION SORT =====
    // Simpler algorithm: builds sorted array one item at a time
    // Good for small arrays and nearly sorted data
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Move elements greater than key one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Insert key at its correct position
            arr[j + 1] = key;
        }
    }
    
    // ===== SELECTION SORT =====
    // Find minimum element and place it at beginning, repeat
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            // Find minimum element in remaining unsorted array
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap minimum element with current position
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
    
    // ===== UTILITY METHODS =====
    
    // Print array
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    // Check if array is sorted
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    // Clone array to avoid modifying original
    public static int[] cloneArray(int[] arr) {
        return arr.clone();
    }
    
    // Benchmark a sorting algorithm
    public static long benchmarkSort(int[] arr, String algorithm) {
        int[] copy = cloneArray(arr);
        
        long startTime = System.nanoTime();
        
        switch (algorithm.toLowerCase()) {
            case "bubble":
                bubbleSort(copy);
                break;
            case "merge":
                mergeSort(copy);
                break;
            case "quick":
                quickSort(copy);
                break;
            case "insertion":
                insertionSort(copy);
                break;
            case "selection":
                selectionSort(copy);
                break;
        }
        
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000; // Convert to milliseconds
    }
    
    public static void main(String[] args) {
        System.out.println("===== SORTING ALGORITHMS DEMO =====\n");
        
        // Test arrays
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr2 = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        int[] arr3 = {50, 40, 30, 20, 10};
        
        // ===== BUBBLE SORT =====
        System.out.println("--- BUBBLE SORT ---");
        int[] bubbleTest = cloneArray(arr1);
        System.out.println("Original: ");
        printArray(bubbleTest);
        bubbleSort(bubbleTest);
        System.out.println("Sorted: ");
        printArray(bubbleTest);
        System.out.println("Is sorted: " + isSorted(bubbleTest));
        System.out.println();
        
        // ===== MERGE SORT =====
        System.out.println("--- MERGE SORT ---");
        int[] mergeTest = cloneArray(arr2);
        System.out.println("Original: ");
        printArray(mergeTest);
        mergeSort(mergeTest);
        System.out.println("Sorted: ");
        printArray(mergeTest);
        System.out.println("Is sorted: " + isSorted(mergeTest));
        System.out.println();
        
        // ===== QUICK SORT =====
        System.out.println("--- QUICK SORT ---");
        int[] quickTest = cloneArray(arr3);
        System.out.println("Original: ");
        printArray(quickTest);
        quickSort(quickTest);
        System.out.println("Sorted: ");
        printArray(quickTest);
        System.out.println("Is sorted: " + isSorted(quickTest));
        System.out.println();
        
        // ===== INSERTION SORT =====
        System.out.println("--- INSERTION SORT ---");
        int[] insertionTest = cloneArray(arr1);
        System.out.println("Original: ");
        printArray(insertionTest);
        insertionSort(insertionTest);
        System.out.println("Sorted: ");
        printArray(insertionTest);
        System.out.println();
        
        // ===== SELECTION SORT =====
        System.out.println("--- SELECTION SORT ---");
        int[] selectionTest = cloneArray(arr2);
        System.out.println("Original: ");
        printArray(selectionTest);
        selectionSort(selectionTest);
        System.out.println("Sorted: ");
        printArray(selectionTest);
        System.out.println();
        
        // ===== PERFORMANCE COMPARISON =====
        System.out.println("--- PERFORMANCE COMPARISON ---");
        int[] largeArr = new int[1000];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = (int) (Math.random() * 10000);
        }
        
        System.out.println("Sorting 1000 random integers:");
        System.out.println("Bubble Sort: " + benchmarkSort(largeArr, "bubble") + " ms");
        System.out.println("Insertion Sort: " + benchmarkSort(largeArr, "insertion") + " ms");
        System.out.println("Selection Sort: " + benchmarkSort(largeArr, "selection") + " ms");
        System.out.println("Merge Sort: " + benchmarkSort(largeArr, "merge") + " ms");
        System.out.println("Quick Sort: " + benchmarkSort(largeArr, "quick") + " ms");
    }
}
