/**
 * Array Problems Program
 * Demonstrates: duplicate elements, missing numbers, and array rotation
 * 
 * Learning Objectives:
 * - Solve common array-based interview problems
 * - Use HashSet and HashMap for efficient solutions
 * - Implement array manipulation techniques
 * - Understand mathematical approaches to array problems
 */

import java.util.*;

public class ArrayProblems {
    
    // Find all duplicate elements in array
    public static List<Integer> findDuplicates(int[] arr) {
        Set<Integer> duplicates = new HashSet<>();
        Set<Integer> seen = new HashSet<>();
        
        for (int num : arr) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }
        
        return new ArrayList<>(duplicates);
    }
    
    // Find duplicates with constraint: array contains n elements, values from 1 to n-1
    public static List<Integer> findDuplicatesConstrained(int[] arr) {
        List<Integer> result = new ArrayList<>();
        
        // Use array indices as hash
        for (int num : arr) {
            int index = Math.abs(num) - 1;
            if (arr[index] < 0) {
                result.add(Math.abs(num));
            } else {
                arr[index] = -arr[index];
            }
        }
        
        return result;
    }
    
    // Find missing number in array of 1 to n
    // Array contains n-1 numbers, find the missing one
    public static int findMissingNumber(int[] arr) {
        int n = arr.length + 1;
        long expectedSum = (long) n * (n + 1) / 2;
        long actualSum = 0;
        
        for (int num : arr) {
            actualSum += num;
        }
        
        return (int) (expectedSum - actualSum);
    }
    
    // Find missing number using XOR (doesn't overflow for large numbers)
    public static int findMissingNumberXOR(int[] arr) {
        int n = arr.length + 1;
        int result = 0;
        
        // XOR all numbers from 1 to n
        for (int i = 1; i <= n; i++) {
            result ^= i;
        }
        
        // XOR with all array elements
        for (int num : arr) {
            result ^= num;
        }
        
        return result;
    }
    
    // Rotate array to the right by k positions
    public static void rotateArray(int[] arr, int k) {
        if (arr.length == 0) return;
        
        k = k % arr.length; // Handle k > length
        reverse(arr, 0, arr.length - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
    }
    
    // Rotate array using temporary array
    public static void rotateArraySimple(int[] arr, int k) {
        if (arr.length == 0) return;
        
        k = k % arr.length;
        int[] temp = new int[k];
        
        // Copy last k elements to temp
        System.arraycopy(arr, arr.length - k, temp, 0, k);
        
        // Shift elements
        System.arraycopy(arr, 0, arr, k, arr.length - k);
        
        // Copy temp back
        System.arraycopy(temp, 0, arr, 0, k);
    }
    
    // Helper method to reverse portion of array
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    // Find second largest element in array
    public static int findSecondLargest(int[] arr) {
        if (arr.length < 2) return -1;
        
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }
        
        return secondLargest == Integer.MIN_VALUE ? -1 : secondLargest;
    }
    
    // Find common elements in two arrays
    public static List<Integer> findCommonElements(int[] arr1, int[] arr2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> common = new HashSet<>();
        
        for (int num : arr1) {
            set1.add(num);
        }
        
        for (int num : arr2) {
            if (set1.contains(num)) {
                common.add(num);
            }
        }
        
        return new ArrayList<>(common);
    }
    
    // Find pairs with given sum
    public static List<int[]> findPairsWithSum(int[] arr, int targetSum) {
        List<int[]> pairs = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Set<String> foundPairs = new HashSet<>();
        
        for (int num : arr) {
            int complement = targetSum - num;
            
            if (seen.contains(complement)) {
                // Create canonical representation to avoid duplicates
                String pairKey = Math.min(num, complement) + "-" + Math.max(num, complement);
                if (!foundPairs.contains(pairKey)) {
                    pairs.add(new int[]{complement, num});
                    foundPairs.add(pairKey);
                }
            }
            seen.add(num);
        }
        
        return pairs;
    }
    
    // Remove duplicates from sorted array in-place
    public static int removeDuplicatesSorted(int[] arr) {
        if (arr.length == 0) return 0;
        
        int uniqueIndex = 0;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[uniqueIndex]) {
                uniqueIndex++;
                arr[uniqueIndex] = arr[i];
            }
        }
        
        return uniqueIndex + 1;
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
    
    public static void main(String[] args) {
        System.out.println("===== ARRAY PROBLEMS DEMO =====\n");
        
        // Find Duplicates
        System.out.println("--- FIND DUPLICATES ---");
        int[] arr1 = {1, 2, 3, 2, 4, 3, 5};
        System.out.println("Array: ");
        printArray(arr1);
        System.out.println("Duplicates: " + findDuplicates(arr1));
        System.out.println();
        
        // Find Missing Number
        System.out.println("--- FIND MISSING NUMBER ---");
        int[] arr2 = {1, 2, 4, 5, 6}; // Missing 3
        System.out.println("Array (should contain 1-6): ");
        printArray(arr2);
        System.out.println("Missing number: " + findMissingNumber(arr2));
        System.out.println("Missing number (XOR method): " + findMissingNumberXOR(arr2));
        System.out.println();
        
        // Rotate Array
        System.out.println("--- ROTATE ARRAY ---");
        int[] arr3 = {1, 2, 3, 4, 5};
        System.out.println("Original array: ");
        printArray(arr3);
        rotateArray(arr3, 2);
        System.out.println("After rotating right by 2: ");
        printArray(arr3);
        System.out.println();
        
        // Rotate Array (Simple Method)
        System.out.println("--- ROTATE ARRAY (SIMPLE METHOD) ---");
        int[] arr4 = {1, 2, 3, 4, 5};
        System.out.println("Original array: ");
        printArray(arr4);
        rotateArraySimple(arr4, 2);
        System.out.println("After rotating right by 2: ");
        printArray(arr4);
        System.out.println();
        
        // Find Second Largest
        System.out.println("--- FIND SECOND LARGEST ---");
        int[] arr5 = {10, 5, 20, 8, 15};
        System.out.println("Array: ");
        printArray(arr5);
        System.out.println("Second largest: " + findSecondLargest(arr5));
        System.out.println();
        
        // Find Common Elements
        System.out.println("--- FIND COMMON ELEMENTS ---");
        int[] arr6 = {1, 2, 3, 4, 5};
        int[] arr7 = {3, 4, 5, 6, 7};
        System.out.println("Array 1: ");
        printArray(arr6);
        System.out.println("Array 2: ");
        printArray(arr7);
        System.out.println("Common elements: " + findCommonElements(arr6, arr7));
        System.out.println();
        
        // Find Pairs with Sum
        System.out.println("--- FIND PAIRS WITH SUM ---");
        int[] arr8 = {1, 5, 7, -1, 5};
        int targetSum = 6;
        System.out.println("Array: ");
        printArray(arr8);
        System.out.println("Finding pairs with sum = " + targetSum);
        List<int[]> pairs = findPairsWithSum(arr8, targetSum);
        for (int[] pair : pairs) {
            System.out.println("  (" + pair[0] + ", " + pair[1] + ")");
        }
        System.out.println();
        
        // Remove Duplicates from Sorted Array
        System.out.println("--- REMOVE DUPLICATES (SORTED) ---");
        int[] arr9 = {1, 1, 2, 2, 3, 3, 4, 5};
        System.out.println("Original array: ");
        printArray(arr9);
        int length = removeDuplicatesSorted(arr9);
        System.out.println("After removing duplicates (in-place), new length: " + length);
        System.out.print("Array content: ");
        for (int i = 0; i < length; i++) {
            System.out.print(arr9[i] + " ");
        }
        System.out.println();
    }
}
