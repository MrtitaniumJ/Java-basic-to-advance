/**
 * Palindrome Checker
 * 
 * This program checks if a string or number is a palindrome.
 * A palindrome reads the same forwards and backwards.
 * 
 * Learning Objectives:
 * - String manipulation and comparison
 * - Working with character arrays
 * - Understanding string reversal
 * - Practice with logical comparisons
 * 
 * Input: A string or number to check
 * Output: Whether it's a palindrome or not
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class PalindromeChecker {
    
    /**
     * Check if a string is palindrome (ignoring spaces and case)
     * @param str String to check
     * @return true if palindrome, false otherwise
     */
    public static boolean isPalindrome(String str) {
        // Remove spaces and convert to lowercase
        String cleaned = str.replaceAll(" ", "").toLowerCase();
        
        // Compare characters from both ends
        int left = 0;
        int right = cleaned.length() - 1;
        
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Check if a number is palindrome
     * @param num Number to check
     * @return true if palindrome, false otherwise
     */
    public static boolean isPalindromeNumber(long num) {
        String str = String.valueOf(Math.abs(num));
        return isPalindrome(str);
    }
    
    /**
     * Reverse a string
     * @param str String to reverse
     * @return Reversed string
     */
    public static String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Palindrome Checker ===");
        System.out.println();
        
        // Get input
        System.out.print("Enter a string or word to check: ");
        String input = scanner.nextLine();
        
        // Check if palindrome
        System.out.println();
        if (isPalindrome(input)) {
            System.out.println("\"" + input + "\" is a PALINDROME");
        } else {
            System.out.println("\"" + input + "\" is NOT a palindrome");
        }
        
        // Show reversed string
        String cleaned = input.replaceAll(" ", "").toLowerCase();
        String reversed = reverseString(cleaned);
        System.out.println();
        System.out.println("Original (cleaned): " + cleaned);
        System.out.println("Reversed:          " + reversed);
        
        // Show more examples
        System.out.println();
        System.out.println("Examples of Palindromes:");
        String[] examples = {"racecar", "level", "radar", "noon", "civic", "kayak"};
        for (String example : examples) {
            System.out.println("  - " + example);
        }
        
        System.out.println();
        System.out.println("Palindrome Numbers: 121, 1331, 12321, 9009, 0, 11");
        
        scanner.close();
    }
}
