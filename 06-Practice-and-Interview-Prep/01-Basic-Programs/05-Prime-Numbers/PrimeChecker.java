/**
 * Prime Number Checker
 * 
 * This program checks if a number is prime and finds all prime numbers up to n.
 * A prime number is a natural number greater than 1 that has no positive divisors other than 1 and itself.
 * 
 * Learning Objectives:
 * - Understand prime numbers
 * - Practice nested loops
 * - Learn optimization techniques
 * - Apply break statements for early termination
 * 
 * Input: A number to check if prime, and an upper limit for prime list
 * Output: Whether number is prime, and list of all primes up to limit
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class PrimeChecker {
    
    /**
     * Check if a number is prime
     * @param num The number to check
     * @return true if prime, false otherwise
     */
    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        
        // Check for divisors up to sqrt(num)
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Find and print all prime numbers up to n
     * @param n Upper limit
     */
    public static void findAllPrimes(int n) {
        System.out.println();
        System.out.print("Prime numbers up to " + n + ": ");
        int count = 0;
        
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
                count++;
            }
        }
        System.out.println();
        System.out.println("Total primes found: " + count);
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Prime Number Checker ===");
        System.out.println();
        
        // Get input - number to check
        System.out.print("Enter a number to check if prime: ");
        int number = scanner.nextInt();
        
        // Check if prime
        System.out.println();
        if (isPrime(number)) {
            System.out.println(number + " is a PRIME number");
        } else {
            System.out.println(number + " is NOT a prime number");
        }
        
        // Find all primes up to limit
        System.out.print("\nEnter upper limit to find all primes: ");
        int limit = scanner.nextInt();
        findAllPrimes(limit);
        
        // Show information
        System.out.println();
        System.out.println("Prime Number Facts:");
        System.out.println("- 2 is the only even prime number");
        System.out.println("- All other primes are odd");
        System.out.println("- Infinite primes exist (Euclid's theorem)");
        System.out.println("- Used in cryptography and security");
        
        scanner.close();
    }
}
