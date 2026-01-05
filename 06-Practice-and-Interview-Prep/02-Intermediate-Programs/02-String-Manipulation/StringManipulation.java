/**
 * String Manipulation Program
 * Demonstrates: anagrams, permutations, and substring pattern matching
 * 
 * Learning Objectives:
 * - Master String class methods
 * - Implement advanced string algorithms
 * - Work with character arrays and collections
 * - Understand pattern matching techniques
 */

import java.util.*;

public class StringManipulation {
    
    // Check if two strings are anagrams
    // Anagrams: words with same characters in different order (e.g., "listen" and "silent")
    public static boolean areAnagrams(String str1, String str2) {
        // Remove spaces and convert to lowercase
        str1 = str1.replaceAll(" ", "").toLowerCase();
        str2 = str2.replaceAll(" ", "").toLowerCase();
        
        // If lengths differ, they can't be anagrams
        if (str1.length() != str2.length()) {
            return false;
        }
        
        // Convert strings to character arrays and sort
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        // Compare sorted arrays
        return Arrays.equals(arr1, arr2);
    }
    
    // Check if two strings are anagrams using frequency map (Alternative approach)
    public static boolean areAnagramsUsingMap(String str1, String str2) {
        str1 = str1.replaceAll(" ", "").toLowerCase();
        str2 = str2.replaceAll(" ", "").toLowerCase();
        
        if (str1.length() != str2.length()) {
            return false;
        }
        
        // Create frequency map for first string
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : str1.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Decrement count for second string
        for (char c : str2.toCharArray()) {
            if (!charCount.containsKey(c)) {
                return false;
            }
            charCount.put(c, charCount.get(c) - 1);
            if (charCount.get(c) < 0) {
                return false;
            }
        }
        
        // Check if all counts are zero
        for (int count : charCount.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
    
    // Generate all permutations of a string
    public static void generatePermutations(String str) {
        Set<String> permutations = new HashSet<>();
        permute(str.toCharArray(), 0, str.length() - 1, permutations);
        
        System.out.println("All permutations of \"" + str + "\":");
        for (String perm : permutations) {
            System.out.println("  " + perm);
        }
    }
    
    // Helper method for generating permutations recursively
    private static void permute(char[] chars, int start, int end, Set<String> permutations) {
        if (start == end) {
            permutations.add(new String(chars));
        } else {
            for (int i = start; i <= end; i++) {
                // Swap
                swap(chars, start, i);
                // Recursively permute
                permute(chars, start + 1, end, permutations);
                // Swap back (backtrack)
                swap(chars, start, i);
            }
        }
    }
    
    // Helper method to swap characters
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
    
    // Check if a string is palindrome
    public static boolean isPalindrome(String str) {
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0, right = cleaned.length() - 1;
        
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    // Find all occurrences of a substring in a string
    public static List<Integer> findSubstringOccurrences(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        int index = 0;
        
        while ((index = text.indexOf(pattern, index)) != -1) {
            positions.add(index);
            index += 1; // Move to next character to find overlapping occurrences
        }
        
        return positions;
    }
    
    // Check if a string is a rotation of another string
    // Example: "waterbottle" is rotation of "bottlewater"
    public static boolean isRotation(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        
        // Clever trick: if str2 is rotation of str1, it will be substring of str1+str1
        String combined = str1 + str1;
        return combined.contains(str2);
    }
    
    // Reverse each word in a string but keep order
    public static String reverseEachWord(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            result.append(new StringBuilder(words[i]).reverse());
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        
        return result.toString();
    }
    
    // Count frequency of each character in a string
    public static void printCharacterFrequency(String str) {
        str = str.toLowerCase();
        Map<Character, Integer> frequency = new LinkedHashMap<>();
        
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            }
        }
        
        System.out.println("Character frequency in \"" + str + "\":");
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            System.out.println("  '" + entry.getKey() + "' -> " + entry.getValue());
        }
    }
    
    // Find the first non-repeating character
    public static Character firstNonRepeatingChar(String str) {
        Map<Character, Integer> charCount = new LinkedHashMap<>();
        
        // Count frequency
        for (char c : str.toLowerCase().toCharArray()) {
            if (c != ' ') {
                charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            }
        }
        
        // Find first with count 1
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println("===== STRING MANIPULATION DEMO =====\n");
        
        // Anagram Check
        System.out.println("--- ANAGRAM CHECK ---");
        String s1 = "listen";
        String s2 = "silent";
        String s3 = "hello";
        
        System.out.println("\"" + s1 + "\" and \"" + s2 + "\" are anagrams? " + 
                         areAnagrams(s1, s2));
        System.out.println("\"" + s1 + "\" and \"" + s3 + "\" are anagrams? " + 
                         areAnagrams(s1, s3));
        System.out.println();
        
        // Anagram using Map
        System.out.println("--- ANAGRAM CHECK (Using Map) ---");
        System.out.println("\"" + s1 + "\" and \"" + s2 + "\" are anagrams? " + 
                         areAnagramsUsingMap(s1, s2));
        System.out.println();
        
        // Permutations
        System.out.println("--- PERMUTATIONS ---");
        generatePermutations("ABC");
        System.out.println();
        
        // Palindrome Check
        System.out.println("--- PALINDROME CHECK ---");
        String[] testStrings = {"racecar", "hello", "A man a plan a canal Panama"};
        for (String str : testStrings) {
            System.out.println("\"" + str + "\" is palindrome? " + isPalindrome(str));
        }
        System.out.println();
        
        // Find Substring Occurrences
        System.out.println("--- FIND SUBSTRING OCCURRENCES ---");
        String text = "hello world, hello java, hello programming";
        String pattern = "hello";
        List<Integer> positions = findSubstringOccurrences(text, pattern);
        System.out.println("Finding \"" + pattern + "\" in \"" + text + "\"");
        System.out.println("Found at positions: " + positions);
        System.out.println();
        
        // String Rotation
        System.out.println("--- STRING ROTATION CHECK ---");
        String original = "waterbottle";
        String rotated = "bottlewater";
        System.out.println("\"" + rotated + "\" is rotation of \"" + original + "\"? " + 
                         isRotation(original, rotated));
        System.out.println();
        
        // Reverse Each Word
        System.out.println("--- REVERSE EACH WORD ---");
        String sentence = "hello world java";
        System.out.println("Original: \"" + sentence + "\"");
        System.out.println("Reversed words: \"" + reverseEachWord(sentence) + "\"");
        System.out.println();
        
        // Character Frequency
        System.out.println("--- CHARACTER FREQUENCY ---");
        printCharacterFrequency("programming");
        System.out.println();
        
        // First Non-Repeating Character
        System.out.println("--- FIRST NON-REPEATING CHARACTER ---");
        String testStr = "programming";
        Character first = firstNonRepeatingChar(testStr);
        System.out.println("First non-repeating character in \"" + testStr + "\": " + 
                         (first != null ? first : "None"));
    }
}
