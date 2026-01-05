/**
 * Simple Calculator Program
 * 
 * This program performs basic arithmetic operations on two numbers.
 * Demonstrates switch statements and method creation.
 * 
 * Learning Objectives:
 * - Understand switch statements
 * - Learn to create reusable methods
 * - Practice with arithmetic operators
 * - Handle user input and validation
 * 
 * Input: Two numbers and an operation (+, -, *, /, %)
 * Output: Result of the operation
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class Calculator {
    
    /**
     * Add two numbers
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    public static double add(double a, double b) {
        return a + b;
    }
    
    /**
     * Subtract two numbers
     * @param a First number
     * @param b Second number
     * @return a - b
     */
    public static double subtract(double a, double b) {
        return a - b;
    }
    
    /**
     * Multiply two numbers
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    public static double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Divide two numbers
     * @param a Numerator
     * @param b Denominator
     * @return a / b
     * @throws IllegalArgumentException if b is 0
     */
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return a / b;
    }
    
    /**
     * Get remainder
     * @param a Dividend
     * @param b Divisor
     * @return a % b
     * @throws IllegalArgumentException if b is 0
     */
    public static double modulus(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot calculate modulus with zero divisor!");
        }
        return a % b;
    }
    
    /**
     * Perform calculation based on operator
     * @param a First number
     * @param b Second number
     * @param operator The operation (+, -, *, /, %)
     * @return Result of operation
     */
    public static double calculate(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return add(a, b);
            case '-':
                return subtract(a, b);
            case '*':
                return multiply(a, b);
            case '/':
                return divide(a, b);
            case '%':
                return modulus(a, b);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;
        
        System.out.println("=== Simple Calculator ===");
        System.out.println();
        
        while (continueCalculating) {
            try {
                // Get first number
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();
                
                // Get operator
                System.out.print("Enter operator (+, -, *, /, %): ");
                char operator = scanner.next().charAt(0);
                
                // Get second number
                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();
                
                // Perform calculation
                double result = calculate(num1, num2, operator);
                
                // Display result
                System.out.println();
                System.out.printf("%.2f %c %.2f = %.2f%n", num1, operator, num2, result);
                System.out.println();
                
                // Ask to continue
                System.out.print("Do you want to calculate again? (yes/no): ");
                String response = scanner.next().toLowerCase();
                continueCalculating = response.equals("yes") || response.equals("y");
                System.out.println();
                
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter numbers and a valid operator.");
                scanner.nextLine();  // Clear the invalid input
                System.out.println();
            }
        }
        
        System.out.println("Thank you for using the calculator!");
        scanner.close();
    }
}
