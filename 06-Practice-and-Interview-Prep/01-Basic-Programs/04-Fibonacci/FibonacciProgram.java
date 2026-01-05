/**
 * Fibonacci Program
 * 
 * This program generates and prints the Fibonacci series.
 * Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
 * Each number is the sum of the previous two numbers.
 * 
 * Learning Objectives:
 * - Understand pattern recognition
 * - Learn to generate sequences
 * - Practice with multiple variables
 * - Apply mathematical sequences
 * 
 * Input: Number of terms to generate
 * Output: Fibonacci series
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class FibonacciProgram {
    
    /**
     * Generate Fibonacci series
     * @param n Number of terms to generate
     */
    public static void generateFibonacci(int n) {
        if (n <= 0) {
            System.out.println("Please enter a positive number!");
            return;
        }
        
        long num1 = 0, num2 = 1;
        System.out.println();
        System.out.print("Fibonacci Series: ");
        
        for (int i = 1; i <= n; i++) {
            System.out.print(num1 + " ");
            
            // Calculate next Fibonacci number
            long nextNum = num1 + num2;
            num1 = num2;
            num2 = nextNum;
        }
        System.out.println();
    }
    
    /**
     * Get the nth Fibonacci number
     * @param n The position in Fibonacci series (0-indexed)
     * @return The nth Fibonacci number
     */
    public static long getFibonacciNumber(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        
        long num1 = 0, num2 = 1;
        for (int i = 2; i <= n; i++) {
            long nextNum = num1 + num2;
            num1 = num2;
            num2 = nextNum;
        }
        return num2;
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Fibonacci Series Generator ===");
        System.out.println();
        
        // Get input from user
        System.out.print("Enter number of terms to generate: ");
        int n = scanner.nextInt();
        
        // Generate and display Fibonacci series
        generateFibonacci(n);
        
        // Show details
        System.out.println();
        System.out.println("Details:");
        System.out.println("- Fibonacci series starts with 0 and 1");
        System.out.println("- Each next number is sum of previous two");
        System.out.println("- F(n) = F(n-1) + F(n-2)");
        
        // Show individual numbers
        System.out.println();
        System.out.println("Individual Fibonacci Numbers:");
        for (int i = 0; i < n && i < 15; i++) {
            System.out.println("F(" + i + ") = " + getFibonacciNumber(i));
        }
        
        // Show some interesting facts
        System.out.println();
        System.out.println("Interesting Properties:");
        System.out.println("- Appears in nature (spiral shells, flower petals)");
        System.out.println("- Related to golden ratio: φ ≈ 1.618");
        System.out.println("- Used in algorithm analysis");
        
        scanner.close();
    }
}
