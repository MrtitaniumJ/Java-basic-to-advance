/**
 * Factorial Program
 * 
 * This program calculates the factorial of a given number.
 * Factorial of n (denoted as n!) = n × (n-1) × (n-2) × ... × 1
 * 
 * Learning Objectives:
 * - Understand nested loops
 * - Learn about mathematical concepts (factorial)
 * - Practice with accumulation using multiplication
 * - Handle edge cases (0! = 1)
 * 
 * Input: A non-negative integer n
 * Output: Factorial of n
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class FactorialProgram {
    
    /**
     * Calculate factorial using iterative approach
     * @param n The number to calculate factorial for
     * @return Factorial of n
     */
    public static long calculateFactorial(int n) {
        long factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Factorial Calculator ===");
        System.out.println();
        
        // Get input from user
        System.out.print("Enter a non-negative number: ");
        int n = scanner.nextInt();
        
        // Validate input
        if (n < 0) {
            System.out.println("Factorial is not defined for negative numbers!");
            scanner.close();
            return;
        }
        
        // Calculate factorial
        long factorial = calculateFactorial(n);
        
        // Display result
        System.out.println();
        System.out.println(n + "! = " + factorial);
        
        // Show the calculation
        System.out.print("Calculation: ");
        if (n == 0 || n == 1) {
            System.out.println(n + "! = 1");
        } else {
            for (int i = n; i >= 1; i--) {
                if (i > 1) {
                    System.out.print(i + " × ");
                } else {
                    System.out.print(i);
                }
            }
            System.out.println(" = " + factorial);
        }
        
        // Show some interesting facts
        System.out.println();
        System.out.println("Interesting Facts:");
        System.out.println("- 0! = 1 (by definition)");
        System.out.println("- 1! = 1");
        System.out.println("- 5! = 120");
        System.out.println("- 10! = 3,628,800");
        
        scanner.close();
    }
}
