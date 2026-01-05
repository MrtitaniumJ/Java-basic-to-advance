/**
 * Largest Number Program
 * 
 * This program finds the largest number among given numbers.
 * Demonstrates comparison operators and conditional logic.
 * 
 * Learning Objectives:
 * - Understand comparison operators (>, <, >=, <=)
 * - Learn to find maximum values
 * - Practice with multiple conditions
 * - Work with arrays and loops
 * 
 * Input: Multiple numbers
 * Output: The largest number
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Arrays;
import java.util.Scanner;

public class LargestNumber {
    
    /**
     * Find largest among two numbers
     * @param a First number
     * @param b Second number
     * @return The larger number
     */
    public static int findLargest(int a, int b) {
        return a > b ? a : b;
    }
    
    /**
     * Find largest among three numbers
     * @param a First number
     * @param b Second number
     * @param c Third number
     * @return The largest number
     */
    public static int findLargestThree(int a, int b, int c) {
        if (a >= b && a >= c) {
            return a;
        } else if (b >= a && b >= c) {
            return b;
        } else {
            return c;
        }
    }
    
    /**
     * Find largest in array
     * @param numbers Array of numbers
     * @return The largest number, or Integer.MIN_VALUE if empty
     */
    public static int findLargestInArray(int[] numbers) {
        if (numbers.length == 0) {
            return Integer.MIN_VALUE;
        }
        
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Largest Number Finder ===");
        System.out.println();
        
        // Example 1: Two numbers
        System.out.println("Method 1: Find Largest of Two Numbers");
        System.out.print("Enter first number: ");
        int num1 = scanner.nextInt();
        System.out.print("Enter second number: ");
        int num2 = scanner.nextInt();
        System.out.println("Largest: " + findLargest(num1, num2));
        
        // Example 2: Three numbers
        System.out.println();
        System.out.println("Method 2: Find Largest of Three Numbers");
        System.out.print("Enter first number: ");
        int num3 = scanner.nextInt();
        System.out.print("Enter second number: ");
        int num4 = scanner.nextInt();
        System.out.print("Enter third number: ");
        int num5 = scanner.nextInt();
        System.out.println("Largest: " + findLargestThree(num3, num4, num5));
        
        // Example 3: Multiple numbers
        System.out.println();
        System.out.println("Method 3: Find Largest of Multiple Numbers");
        System.out.print("How many numbers? ");
        int count = scanner.nextInt();
        int[] numbers = new int[count];
        
        for (int i = 0; i < count; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            numbers[i] = scanner.nextInt();
        }
        
        System.out.println();
        System.out.println("Numbers: " + Arrays.toString(numbers));
        System.out.println("Largest: " + findLargestInArray(numbers));
        
        // Show sorted array
        int[] sorted = numbers.clone();
        Arrays.sort(sorted);
        System.out.println("Sorted: " + Arrays.toString(sorted));
        
        // Find smallest too
        System.out.println("Smallest: " + sorted[0]);
        
        scanner.close();
    }
}
