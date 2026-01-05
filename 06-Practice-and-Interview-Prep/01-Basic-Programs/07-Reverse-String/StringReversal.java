/**
 * String Reversal Program
 * 
 * This program demonstrates various ways to reverse a string.
 * 
 * Learning Objectives:
 * - String manipulation techniques
 * - Working with character arrays
 * - Loop iteration backwards
 * - Using StringBuilder class
 * - Understanding string immutability
 * 
 * Input: A string to reverse
 * Output: The reversed string
 * 
 * @author Learning Java
 * @version 1.0
 */

import java.util.Scanner;

public class StringReversal {
    
    /**
     * Method 1: Using StringBuilder
     * @param str String to reverse
     * @return Reversed string
     */
    public static String reverseUsingStringBuilder(String str) {
        return new StringBuilder(str).reverse().toString();
    }
    
    /**
     * Method 2: Using character array and loop
     * @param str String to reverse
     * @return Reversed string
     */
    public static String reverseUsingArray(String str) {
        char[] chars = str.toCharArray();
        String reversed = "";
        for (int i = chars.length - 1; i >= 0; i--) {
            reversed += chars[i];
        }
        return reversed;
    }
    
    /**
     * Method 3: Using two-pointer swap
     * @param str String to reverse
     * @return Reversed string
     */
    public static String reverseUsingTwoPointer(String str) {
        char[] chars = str.toCharArray();
        int left = 0, right = chars.length - 1;
        
        while (left < right) {
            // Swap
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            
            left++;
            right--;
        }
        return new String(chars);
    }
    
    /**
     * Method 4: Using recursion
     * @param str String to reverse
     * @return Reversed string
     */
    public static String reverseUsingRecursion(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return reverseUsingRecursion(str.substring(1)) + str.charAt(0);
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== String Reversal Program ===");
        System.out.println();
        
        // Get input
        System.out.print("Enter a string to reverse: ");
        String input = scanner.nextLine();
        
        System.out.println();
        System.out.println("Original String: " + input);
        System.out.println();
        
        // Show all methods
        System.out.println("Method 1 (StringBuilder):  " + reverseUsingStringBuilder(input));
        System.out.println("Method 2 (Array Loop):    " + reverseUsingArray(input));
        System.out.println("Method 3 (Two-Pointer):   " + reverseUsingTwoPointer(input));
        System.out.println("Method 4 (Recursion):     " + reverseUsingRecursion(input));
        
        // Show character breakdown
        System.out.println();
        System.out.println("Character Breakdown:");
        System.out.print("Original: ");
        for (char c : input.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        System.out.print("Reversed: ");
        String reversed = reverseUsingStringBuilder(input);
        for (char c : reversed.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        // Check if palindrome
        System.out.println();
        if (input.equals(reversed)) {
            System.out.println("This string is a PALINDROME");
        } else {
            System.out.println("This string is NOT a palindrome");
        }
        
        scanner.close();
    }
}
