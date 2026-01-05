/**
 * Even/Odd Checker Program
 * 
 * This program checks whether a number is even or odd.
 * An even number is divisible by 2, an odd number is not.
 * 
 * Learning Objectives:
 * - Understand modulo operator (%)
 * - Practice conditional statements (if-else)
 * - Learn about number properties
 * - Handle user input validation
 * 
 * Input: An integer number
 * Output: Whether the number is even or odd
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class EvenOddChecker {
    
    /**
     * Check if a number is even or odd
     * @param num The number to check
     * @return true if even, false if odd
     */
    public static boolean isEven(int num) {
        return num % 2 == 0;
    }
    
    /**
     * Check if number is odd
     * @param num The number to check
     * @return true if odd, false if even
     */
    public static boolean isOdd(int num) {
        return num % 2 != 0;
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Even/Odd Checker ===");
        System.out.println();
        
        // Get input
        System.out.print("Enter an integer number: ");
        int number = scanner.nextInt();
        
        System.out.println();
        
        // Check and display result
        if (isEven(number)) {
            System.out.println(number + " is an EVEN number");
        } else {
            System.out.println(number + " is an ODD number");
        }
        
        // Show mathematical reason
        System.out.println();
        System.out.println("Explanation:");
        System.out.println("  " + number + " % 2 = " + (number % 2));
        if (number % 2 == 0) {
            System.out.println("  Since remainder is 0, " + number + " is EVEN");
        } else {
            System.out.println("  Since remainder is 1, " + number + " is ODD");
        }
        
        // Additional information
        System.out.println();
        System.out.println("Additional Information:");
        System.out.println("  Even numbers: ..., -4, -2, 0, 2, 4, 6, 8, ...");
        System.out.println("  Odd numbers:  ..., -5, -3, -1, 1, 3, 5, 7, ...");
        System.out.println("  Test: (num % 2 == 0) → EVEN");
        System.out.println("  Test: (num % 2 != 0) → ODD");
        
        scanner.close();
    }
}
