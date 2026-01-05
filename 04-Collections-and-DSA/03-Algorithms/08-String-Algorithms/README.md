# String Algorithms in Java

## Table of Contents
- [Introduction](#introduction)
- [Pattern Matching Algorithms](#pattern-matching-algorithms)
  - [KMP Algorithm](#kmp-algorithm-knuth-morris-pratt)
  - [Rabin-Karp Algorithm](#rabin-karp-algorithm)
  - [Boyer-Moore Algorithm](#boyer-moore-algorithm)
- [String Searching](#string-searching)
- [Longest Common Subsequence](#longest-common-subsequence)
- [Longest Common Substring](#longest-common-substring)
- [Palindrome Algorithms](#palindrome-algorithms)
- [Anagram Detection](#anagram-detection)
- [String Manipulation](#string-manipulation)
- [Complete Implementation](#complete-implementation)
- [Complexity Analysis](#complexity-analysis)
- [Use Cases and Applications](#use-cases-and-applications)

## Introduction

String algorithms are fundamental to computer science and software development. They solve problems related to text processing, pattern matching, data compression, bioinformatics, and many other domains. Understanding these algorithms is crucial for efficient string manipulation and is frequently tested in technical interviews.

String algorithms can be categorized into several types: pattern matching algorithms that find occurrences of patterns within text, substring problems that identify common or unique substrings, palindrome detection algorithms, and various string manipulation techniques. Each algorithm has specific strengths and is optimized for particular use cases.

This comprehensive guide covers the most important string algorithms with complete Java implementations, complexity analysis, and practical applications. We'll explore both classical algorithms like KMP and modern approaches used in real-world applications.

## Pattern Matching Algorithms

Pattern matching is the process of finding occurrences of a pattern string within a larger text string. While the naive approach of checking every position has O(n*m) complexity, advanced algorithms achieve better performance.

### KMP Algorithm (Knuth-Morris-Pratt)

The KMP algorithm uses preprocessing of the pattern to avoid unnecessary comparisons. It builds a failure function (also called prefix function or LPS array) that indicates where to continue matching after a mismatch. The key insight is that when a mismatch occurs, the pattern itself contains information about where the next match could begin.

**Time Complexity:** O(n + m) where n is text length and m is pattern length  
**Space Complexity:** O(m) for the LPS array  
**Best Use Case:** When pattern has repeating substrings

### Rabin-Karp Algorithm

The Rabin-Karp algorithm uses hashing to find patterns. It computes a hash value for the pattern and compares it with hash values of substrings of the text. When hash values match, it performs a character-by-character comparison to confirm the match. Using rolling hash technique, it efficiently computes hash values for consecutive substrings.

**Time Complexity:** O(n + m) average case, O(n*m) worst case  
**Space Complexity:** O(1)  
**Best Use Case:** Multiple pattern searching, plagiarism detection

### Boyer-Moore Algorithm

Boyer-Moore is one of the most efficient string searching algorithms in practice. It scans the pattern from right to left and uses two heuristics: the bad character rule and the good suffix rule. These rules allow the algorithm to skip sections of text, making it very fast for large alphabets and long patterns.

**Time Complexity:** O(n/m) best case, O(n*m) worst case  
**Space Complexity:** O(alphabet size)  
**Best Use Case:** Large texts with large alphabets

## String Searching

String searching algorithms locate all occurrences of a substring within a string. Beyond pattern matching algorithms, various techniques like Z-algorithm, suffix arrays, and suffix trees provide efficient solutions for different scenarios.

## Longest Common Subsequence

The Longest Common Subsequence (LCS) problem finds the longest subsequence common to two sequences. Unlike substrings, subsequences don't need to be contiguous. This problem is solved using dynamic programming where we build a table representing solutions to subproblems.

**Time Complexity:** O(n*m)  
**Space Complexity:** O(n*m) or O(min(n,m)) with optimization  
**Applications:** Diff utilities, DNA sequence analysis, version control

## Longest Common Substring

The Longest Common Substring problem finds the longest contiguous substring that appears in both strings. This differs from LCS as the characters must be consecutive. Dynamic programming provides an efficient solution with modifications to the LCS approach.

**Time Complexity:** O(n*m)  
**Space Complexity:** O(n*m)  
**Applications:** Plagiarism detection, data deduplication

## Palindrome Algorithms

Palindrome detection and manipulation are common string problems. Techniques range from simple two-pointer approaches to sophisticated algorithms like Manacher's algorithm for finding all palindromic substrings in linear time.

**Time Complexity:** O(n) for basic palindrome check, O(n) for Manacher's algorithm  
**Applications:** DNA sequence analysis, text processing

## Anagram Detection

Anagrams are words or phrases formed by rearranging letters of another. Detection algorithms use sorting, frequency counting, or hashing to efficiently determine if two strings are anagrams.

**Time Complexity:** O(n log n) with sorting, O(n) with frequency array  
**Applications:** Word games, linguistic analysis, cryptography

## String Manipulation

String manipulation encompasses various operations like reversal, rotation, character replacement, compression, and transformation. These operations are fundamental building blocks for complex string processing tasks.

## Complete Implementation

```java
import java.util.*;

/**
 * Comprehensive String Algorithms Implementation
 * Contains pattern matching, LCS, palindromes, anagrams, and more
 */
public class StringAlgorithms {
    
    // ==================== KMP ALGORITHM ====================
    
    /**
     * KMP Pattern Matching Algorithm
     * Finds all occurrences of pattern in text
     */
    public static List<Integer> KMPSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null) {
            return occurrences;
        }
        
        int[] lps = computeLPSArray(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern
        
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            if (j == pattern.length()) {
                occurrences.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        return occurrences;
    }
    
    /**
     * Compute LPS (Longest Proper Prefix which is also Suffix) array
     * Used by KMP algorithm for pattern preprocessing
     */
    private static int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0; // length of previous longest prefix suffix
        int i = 1;
        
        lps[0] = 0; // lps[0] is always 0
        
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        return lps;
    }
    
    // ==================== RABIN-KARP ALGORITHM ====================
    
    /**
     * Rabin-Karp Pattern Matching using Rolling Hash
     */
    public static List<Integer> rabinKarpSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null) {
            return occurrences;
        }
        
        int n = text.length();
        int m = pattern.length();
        int prime = 101; // A prime number for hashing
        int d = 256; // Number of characters in alphabet
        
        long patternHash = 0;
        long textHash = 0;
        long h = 1;
        
        // Calculate h = d^(m-1) % prime
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % prime;
        }
        
        // Calculate hash for pattern and first window of text
        for (int i = 0; i < m; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % prime;
            textHash = (d * textHash + text.charAt(i)) % prime;
        }
        
        // Slide pattern over text
        for (int i = 0; i <= n - m; i++) {
            // Check if hash values match
            if (patternHash == textHash) {
                // Verify character by character
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    occurrences.add(i);
                }
            }
            
            // Calculate hash for next window
            if (i < n - m) {
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % prime;
                if (textHash < 0) {
                    textHash += prime;
                }
            }
        }
        
        return occurrences;
    }
    
    // ==================== BOYER-MOORE ALGORITHM ====================
    
    /**
     * Boyer-Moore Pattern Matching with Bad Character Heuristic
     */
    public static List<Integer> boyerMooreSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null) {
            return occurrences;
        }
        
        int n = text.length();
        int m = pattern.length();
        
        int[] badChar = preprocessBadCharacter(pattern);
        int shift = 0;
        
        while (shift <= n - m) {
            int j = m - 1;
            
            // Keep reducing j while characters match
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }
            
            if (j < 0) {
                // Pattern found
                occurrences.add(shift);
                shift += (shift + m < n) ? m - badChar[text.charAt(shift + m)] : 1;
            } else {
                // Shift pattern using bad character rule
                shift += Math.max(1, j - badChar[text.charAt(shift + j)]);
            }
        }
        
        return occurrences;
    }
    
    /**
     * Preprocess bad character table for Boyer-Moore
     */
    private static int[] preprocessBadCharacter(String pattern) {
        int[] badChar = new int[256]; // ASCII characters
        
        // Initialize all occurrences as -1
        Arrays.fill(badChar, -1);
        
        // Fill actual values of last occurrence
        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }
        
        return badChar;
    }
    
    // ==================== LONGEST COMMON SUBSEQUENCE ====================
    
    /**
     * Find length of Longest Common Subsequence
     * Dynamic Programming approach
     */
    public static int longestCommonSubsequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Get the actual LCS string
     */
    public static String getLCSString(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Backtrack to find LCS string
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return lcs.reverse().toString();
    }
    
    // ==================== LONGEST COMMON SUBSTRING ====================
    
    /**
     * Find length of Longest Common Substring
     * Unlike LCS, characters must be contiguous
     */
    public static int longestCommonSubstring(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    maxLength = Math.max(maxLength, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        
        return maxLength;
    }
    
    /**
     * Get the actual longest common substring
     */
    public static String getLongestCommonSubstring(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;
        int endIndex = 0;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i - 1;
                    }
                }
            }
        }
        
        if (maxLength == 0) {
            return "";
        }
        
        return str1.substring(endIndex - maxLength + 1, endIndex + 1);
    }
    
    // ==================== PALINDROME ALGORITHMS ====================
    
    /**
     * Check if string is palindrome - Two Pointer Approach
     * Time: O(n), Space: O(1)
     */
    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Find longest palindromic substring - Expand Around Center
     * Time: O(n^2), Space: O(1)
     */
    public static String longestPalindromicSubstring(String str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        
        int start = 0;
        int maxLength = 0;
        
        for (int i = 0; i < str.length(); i++) {
            // Check for odd length palindromes
            int len1 = expandAroundCenter(str, i, i);
            // Check for even length palindromes
            int len2 = expandAroundCenter(str, i, i + 1);
            
            int len = Math.max(len1, len2);
            
            if (len > maxLength) {
                maxLength = len;
                start = i - (len - 1) / 2;
            }
        }
        
        return str.substring(start, start + maxLength);
    }
    
    /**
     * Helper method to expand around center for palindrome detection
     */
    private static int expandAroundCenter(String str, int left, int right) {
        while (left >= 0 && right < str.length() && 
               str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
    /**
     * Count all palindromic substrings
     */
    public static int countPalindromicSubstrings(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        
        for (int i = 0; i < str.length(); i++) {
            // Count odd length palindromes
            count += countPalindromes(str, i, i);
            // Count even length palindromes
            count += countPalindromes(str, i, i + 1);
        }
        
        return count;
    }
    
    /**
     * Helper to count palindromes expanding from center
     */
    private static int countPalindromes(String str, int left, int right) {
        int count = 0;
        while (left >= 0 && right < str.length() && 
               str.charAt(left) == str.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
    
    // ==================== ANAGRAM ALGORITHMS ====================
    
    /**
     * Check if two strings are anagrams - Sorting approach
     * Time: O(n log n), Space: O(n)
     */
    public static boolean isAnagramSorting(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        return Arrays.equals(arr1, arr2);
    }
    
    /**
     * Check if two strings are anagrams - Frequency Count approach
     * Time: O(n), Space: O(1) - fixed size array
     */
    public static boolean isAnagramFrequency(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        
        int[] frequency = new int[256]; // ASCII characters
        
        for (int i = 0; i < str1.length(); i++) {
            frequency[str1.charAt(i)]++;
            frequency[str2.charAt(i)]--;
        }
        
        for (int count : frequency) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Group anagrams together
     * Time: O(n * k log k) where n is number of strings and k is max length
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    // ==================== STRING MANIPULATION ====================
    
    /**
     * Reverse a string - Multiple approaches
     */
    public static String reverseString(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char[] chars = str.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Check if string is rotation of another
     * Time: O(n), Space: O(n)
     */
    public static boolean isRotation(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        
        String concatenated = str1 + str1;
        return concatenated.contains(str2);
    }
    
    /**
     * Remove duplicate characters from string
     * Time: O(n), Space: O(n)
     */
    public static String removeDuplicates(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        Set<Character> seen = new LinkedHashSet<>();
        
        for (char c : str.toCharArray()) {
            seen.add(c);
        }
        
        StringBuilder result = new StringBuilder();
        for (char c : seen) {
            result.append(c);
        }
        
        return result.toString();
    }
    
    /**
     * Run-length encoding compression
     * Time: O(n), Space: O(n)
     */
    public static String runLengthEncoding(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(str.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        
        // Append last character and count
        compressed.append(str.charAt(str.length() - 1)).append(count);
        
        return compressed.length() < str.length() ? compressed.toString() : str;
    }
    
    /**
     * Find first non-repeating character
     * Time: O(n), Space: O(1) - fixed alphabet size
     */
    public static char firstNonRepeatingChar(String str) {
        if (str == null || str.isEmpty()) {
            return '\0';
        }
        
        Map<Character, Integer> frequency = new LinkedHashMap<>();
        
        for (char c : str.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        
        return '\0'; // No non-repeating character found
    }
    
    /**
     * Check if string has all unique characters
     * Time: O(n), Space: O(1) - assuming ASCII
     */
    public static boolean hasAllUniqueChars(String str) {
        if (str == null || str.length() > 256) {
            return false; // ASCII has 256 characters
        }
        
        boolean[] charSet = new boolean[256];
        
        for (char c : str.toCharArray()) {
            if (charSet[c]) {
                return false;
            }
            charSet[c] = true;
        }
        
        return true;
    }
    
    /**
     * Permutations of a string
     */
    public static List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        if (str == null) {
            return result;
        }
        
        permutationHelper(str.toCharArray(), 0, result);
        return result;
    }
    
    private static void permutationHelper(char[] chars, int index, List<String> result) {
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }
        
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            permutationHelper(chars, index + 1, result);
            swap(chars, index, i); // backtrack
        }
    }
    
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
```

## Complexity Analysis

### Pattern Matching Algorithms
| Algorithm | Time Complexity | Space Complexity | Best Use Case |
|-----------|----------------|------------------|---------------|
| KMP | O(n + m) | O(m) | Patterns with repetition |
| Rabin-Karp | O(n + m) avg | O(1) | Multiple pattern search |
| Boyer-Moore | O(n/m) to O(n*m) | O(k) | Large alphabets |
| Naive | O(n*m) | O(1) | Small texts |

### Substring Problems
- **LCS**: O(n*m) time, O(n*m) space (can be optimized to O(min(n,m)))
- **Longest Common Substring**: O(n*m) time, O(n*m) space
- **Palindrome Check**: O(n) time, O(1) space
- **Longest Palindromic Substring**: O(n²) time, O(1) space with expand-around-center

### Anagram Operations
- **Anagram Check (Sorting)**: O(n log n) time, O(n) space
- **Anagram Check (Frequency)**: O(n) time, O(1) space
- **Group Anagrams**: O(n*k log k) time where k is max string length

### String Manipulation
- **Reversal**: O(n) time, O(1) space (in-place)
- **Remove Duplicates**: O(n) time, O(n) space
- **Run-Length Encoding**: O(n) time, O(n) space
- **Permutations**: O(n! * n) time, O(n) space

## Use Cases and Applications

### Pattern Matching
**Text Editors**: Find and replace functionality uses pattern matching algorithms. Modern editors employ KMP or Boyer-Moore for efficient searching.

**Search Engines**: Web crawlers and search engines use pattern matching to index and retrieve content. Rabin-Karp is particularly useful for detecting duplicate content.

**Intrusion Detection**: Security systems use string matching to detect malicious patterns in network traffic or system logs.

**Bioinformatics**: DNA sequence matching relies heavily on pattern matching algorithms to identify genes, mutations, and similarities between sequences.

### Longest Common Subsequence/Substring
**Version Control Systems**: Git and other VCS use LCS algorithms in their diff tools to identify changes between file versions efficiently.

**Plagiarism Detection**: Educational institutions and content platforms use these algorithms to detect copied content by finding common substrings between documents.

**Data Deduplication**: Storage systems use longest common substring algorithms to identify and eliminate redundant data, saving storage space.

**Computational Biology**: Comparing DNA or protein sequences to understand evolutionary relationships and identify functional similarities.

### Palindrome Algorithms
**DNA Analysis**: Palindromic sequences in DNA are important for restriction enzyme recognition sites and genetic engineering.

**Natural Language Processing**: Detecting palindromes in text for linguistic analysis, wordplay detection, and text generation.

**Data Validation**: Checking for palindromic patterns in data for verification purposes, such as in certain cryptographic applications.

### Anagram Detection
**Word Games**: Scrabble helpers, crossword puzzle solvers, and word game applications use anagram algorithms to find valid word combinations.

**Cryptography**: Historical ciphers and modern cryptographic analysis use anagram techniques for encryption and decryption.

**Spell Checkers**: Suggesting corrections based on anagram relationships when detecting misspelled words.

**Data Analysis**: Grouping similar data items, detecting patterns in categorical data, and clustering text data.

### String Manipulation
**Data Cleaning**: Removing duplicates, normalizing text, and preprocessing data for machine learning and analysis.

**Compression**: Run-length encoding and other string compression techniques reduce storage requirements and transmission bandwidth.

**Text Processing**: Tokenization, normalization, and transformation operations in NLP pipelines.

**URL Shortening**: Services like bit.ly use string manipulation and hashing to create short, unique identifiers.

### Real-World Examples

1. **Google Search**: Employs multiple string algorithms including Boyer-Moore for fast text scanning and approximate matching algorithms for spell correction.

2. **Microsoft Word**: Uses KMP and other algorithms for find/replace, spell checking uses edit distance (a string algorithm), and change tracking uses LCS variants.

3. **Turnitin**: Plagiarism detection service uses longest common substring and subsequence algorithms combined with fingerprinting techniques.

4. **BLAST**: Basic Local Alignment Search Tool in bioinformatics uses sophisticated string matching algorithms to compare biological sequences.

5. **Git**: Version control relies heavily on LCS algorithms to compute diffs and merge changes efficiently.

## Best Practices

1. **Choose the Right Algorithm**: Select algorithms based on your specific use case. KMP for patterns with repetition, Boyer-Moore for large alphabets, Rabin-Karp for multiple patterns.

2. **Consider Space-Time Tradeoffs**: Some algorithms trade space for speed. Evaluate your constraints before choosing.

3. **Preprocessing**: Many algorithms benefit from preprocessing (like KMP's LPS array). Amortize preprocessing cost over multiple searches.

4. **Handle Edge Cases**: Always check for null strings, empty strings, and single-character strings.

5. **Optimization**: For production code, consider using built-in methods when appropriate. Java's `String.indexOf()` is highly optimized.

6. **Unicode Awareness**: Be careful with character encoding. The implementations above assume ASCII/Unicode compatibility.

## Conclusion

String algorithms form the backbone of text processing in computer science. From simple operations like palindrome checking to sophisticated pattern matching techniques like KMP and Boyer-Moore, these algorithms solve real-world problems efficiently. Understanding their complexity, implementation details, and appropriate use cases is crucial for writing efficient code and succeeding in technical interviews.

The implementations provided cover the most important string algorithms with detailed explanations and optimizations. Practice these algorithms, understand their underlying principles, and apply them to solve complex string manipulation problems in your projects and interviews.

---

**Practice Tips:**
- Implement each algorithm from scratch without looking at solutions
- Solve related LeetCode/HackerRank problems
- Understand when to use which algorithm
- Practice analyzing time and space complexity
- Study real-world applications and interview questions

**Further Reading:**
- "Introduction to Algorithms" by CLRS
- "The Algorithm Design Manual" by Steven Skiena
- Research papers on advanced string matching algorithms
- Unicode specification for internationalization considerations
