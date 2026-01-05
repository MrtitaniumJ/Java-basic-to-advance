/**
 * Sum of Numbers Program
 * 
 * This program calculates the sum of first n natural numbers.
 * Demonstrates the use of loops and accumulation pattern.
 * 
 * Learning Objectives:
 * - Understand for loops
 * - Learn accumulation pattern (sum += number)
 * - Practice with variables and operators
 * - Calculate mathematical sums
 * 
 * Input: A positive integer n
 * Output: Sum of first n natural numbers (1 + 2 + 3 + ... + n)
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class SumProgram {
    
    /**
     * Main method - program entry point
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Sum of Numbers Program ===");
        System.out.println();
        
        // Get input from user
        System.out.print("Enter a positive number: ");
        int n = scanner.nextInt();
        
        // Validate input
        if (n <= 0) {
            System.out.println("Please enter a positive number!");
            scanner.close();
            return;
        }
        
        // Calculate sum using loop
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        
        // Display results
        System.out.println();
        System.out.println("Sum of numbers from 1 to " + n + " is: " + sum);
        
        // Show the calculation
        System.out.print("Calculation: ");
        for (int i = 1; i <= n; i++) {
            if (i < n) {
                System.out.print(i + " + ");
            } else {
                System.out.print(i);
            }
        }
        System.out.println(" = " + sum);
        
        // Show mathematical formula (Gaussian formula)
        int formulaResult = (n * (n + 1)) / 2;
        System.out.println("Using formula n*(n+1)/2: " + formulaResult);
        
        scanner.close();
    }
}
