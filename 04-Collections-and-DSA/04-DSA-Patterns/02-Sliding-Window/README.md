# Sliding Window Pattern - Complete Guide

## Pattern Overview

The Sliding Window pattern is a powerful technique for solving problems involving subarrays, substrings, or contiguous sequences. It uses a dynamic window that expands and contracts to explore different ranges of data efficiently. Instead of recalculating from scratch for each position, the window "slides" forward, updating only the affected elements, reducing redundant computations significantly.

## When to Use the Sliding Window Pattern

### Recognition Signs
- **Substring/Subarray Problems**: Finding longest/shortest substrings or subarrays with specific properties
- **Contiguous Elements**: Problems involving consecutive elements in arrays or strings
- **Constraint-Based Problems**: Finding elements that satisfy certain conditions within a range
- **Optimization Problems**: Maximizing or minimizing values in windows
- **Frequency/Character Problems**: Counting occurrences within contiguous sequences
- **Repeating Patterns**: Finding patterns or duplicates in sequences
- **Fixed/Variable Window Size**: Both fixed and dynamic window problems

### Advantages
- **Optimal Efficiency**: Reduces time complexity from O(n²) to O(n)
- **Minimal Redundancy**: Updates only affected window elements
- **Space Efficient**: Generally uses O(1) or O(k) extra space
- **Intuitive**: Natural fit for many substring/subarray problems
- **Scalable**: Works efficiently with large datasets

## Step-by-Step Approach

1. **Define Window Boundaries**: Establish left and right pointers for the window
2. **Initialize Window**: Start with empty or minimal window state
3. **Expand Window**: Move right pointer to include new elements
4. **Process Window**: Calculate or update result based on current window
5. **Contract Window**: Move left pointer when window is invalid or to find optimal solution
6. **Update Result**: Keep track of best solution found
7. **Termination**: Stop when right pointer reaches the end

## Java Implementations

### 1. Longest Substring Without Repeating Characters
```java
public class LongestSubstringWithoutRepeating {
    /**
     * Find the longest substring without repeating characters
     * Time: O(n), Space: O(min(m, n)) where m is charset size
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            
            // If character already exists in window, move left pointer
            if (charIndex.containsKey(rightChar)) {
                // Move left to position after the last occurrence
                left = Math.max(left, charIndex.get(rightChar) + 1);
            }
            
            // Update or add character with its current position
            charIndex.put(rightChar, right);
            
            // Update max length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Return the actual substring with longest unique characters
     */
    public static String longestSubstringString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        int maxStart = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            
            if (charIndex.containsKey(rightChar)) {
                left = Math.max(left, charIndex.get(rightChar) + 1);
            }
            
            charIndex.put(rightChar, right);
            
            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                maxStart = left;
            }
        }
        
        return s.substring(maxStart, maxStart + maxLength);
    }
}
```

### 2. Fixed Window Size - Maximum Sum
```java
public class FixedWindowMaxSum {
    /**
     * Find maximum sum of k consecutive elements
     * Time: O(n), Space: O(1)
     */
    public static int maxSumOfKElements(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return 0;
        }
        
        // Calculate sum of first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int maxSum = windowSum;
        
        // Slide the window
        for (int i = k; i < nums.length; i++) {
            // Remove leftmost element of previous window
            // Add new rightmost element
            windowSum = windowSum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    /**
     * Get the subarray with maximum sum of k consecutive elements
     */
    public static int[] maxSumSubarray(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return new int[]{};
        }
        
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int maxSum = windowSum;
        int maxIndex = 0;
        
        for (int i = k; i < nums.length; i++) {
            windowSum = windowSum - nums[i - k] + nums[i];
            if (windowSum > maxSum) {
                maxSum = windowSum;
                maxIndex = i - k + 1;
            }
        }
        
        return Arrays.copyOfRange(nums, maxIndex, maxIndex + k);
    }
}
```

### 3. Minimum Window Substring
```java
public class MinimumWindowSubstring {
    /**
     * Find minimum window substring containing all characters from target
     * Time: O(n + m), Space: O(1) for fixed character set
     */
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Count frequency of characters in target string
        Map<Character, Integer> tCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            tCount.put(c, tCount.getOrDefault(c, 0) + 1);
        }
        
        int required = tCount.size();  // Number of unique characters needed
        int formed = 0;  // Number of unique characters with desired frequency
        
        Map<Character, Integer> windowCounts = new HashMap<>();
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowCounts.put(rightChar, windowCounts.getOrDefault(rightChar, 0) + 1);
            
            // If frequency of character matches required, increment formed
            if (tCount.containsKey(rightChar) && 
                windowCounts.get(rightChar).equals(tCount.get(rightChar))) {
                formed++;
            }
            
            // Contract window from left
            while (left <= right && formed == required) {
                char leftChar = s.charAt(left);
                
                // Update result if current window is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }
                
                // Remove leftmost character
                windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);
                if (tCount.containsKey(leftChar) && 
                    windowCounts.get(leftChar) < tCount.get(leftChar)) {
                    formed--;
                }
                
                left++;
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
}
```

### 4. All Anagrams in String
```java
public class FindAnagramSubstrings {
    /**
     * Find all starting indices of anagram of p in s
     * Time: O(n), Space: O(1) for fixed character set
     */
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        
        if (s == null || p == null || s.length() < p.length()) {
            return result;
        }
        
        // Character frequency in pattern
        int[] pCount = new int[26];
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }
        
        // Character frequency in window
        int[] windowCount = new int[26];
        
        for (int i = 0; i < s.length(); i++) {
            // Add right character to window
            windowCount[s.charAt(i) - 'a']++;
            
            // Remove left character when window size exceeds pattern length
            if (i >= p.length()) {
                windowCount[s.charAt(i - p.length()) - 'a']--;
            }
            
            // Check if window matches pattern
            if (Arrays.equals(pCount, windowCount)) {
                result.add(i - p.length() + 1);
            }
        }
        
        return result;
    }
}
```

### 5. Longest Repeating Character After Replacement
```java
public class LongestRepeatingCharacterReplacement {
    /**
     * Find longest substring with same characters after k replacements
     * Time: O(n), Space: O(1)
     */
    public static int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int[] charCount = new int[26];
        int left = 0;
        int maxCount = 0;  // Max frequency in current window
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            // Update frequency of right character
            charCount[s.charAt(right) - 'A']++;
            maxCount = Math.max(maxCount, charCount[s.charAt(right) - 'A']);
            
            // Window size - max character count = characters needed to replace
            int charsToReplace = (right - left + 1) - maxCount;
            
            // If replacements needed exceed k, shrink window
            if (charsToReplace > k) {
                charCount[s.charAt(left) - 'A']--;
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

### 6. Fruit Into Baskets
```java
public class FruitIntoBaskets {
    /**
     * Pick maximum fruits using at most 2 baskets (2 different types)
     * Time: O(n), Space: O(1)
     */
    public static int totalFruit(int[] fruits) {
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        
        Map<Integer, Integer> fruitCount = new HashMap<>();
        int left = 0;
        int maxFruits = 0;
        
        for (int right = 0; right < fruits.length; right++) {
            // Add current fruit to basket
            fruitCount.put(fruits[right], fruitCount.getOrDefault(fruits[right], 0) + 1);
            
            // If more than 2 types, remove fruits from left
            while (fruitCount.size() > 2) {
                fruitCount.put(fruits[left], fruitCount.get(fruits[left]) - 1);
                if (fruitCount.get(fruits[left]) == 0) {
                    fruitCount.remove(fruits[left]);
                }
                left++;
            }
            
            maxFruits = Math.max(maxFruits, right - left + 1);
        }
        
        return maxFruits;
    }
}
```

## Problem Categories

1. **Substring Problems**: Longest without repeating, minimum window, anagram
2. **Subarray Problems**: Maximum sum, equal sum partition, target sum
3. **Character/Frequency Problems**: Character replacement, fruit baskets, vowels
4. **Pattern Matching**: Finding patterns, anagrams, repeated substrings
5. **Constraint-Based**: Problems with specific conditions or limitations
6. **Optimization Problems**: Maximizing or minimizing within windows

## Complexity Analysis

| Problem | Time Complexity | Space Complexity | Notes |
|---------|-----------------|------------------|-------|
| Longest Unique Substring | O(n) | O(min(m, n)) | m = charset size |
| Fixed Window Sum | O(n) | O(1) | Pre-calculated window sum |
| Minimum Window | O(n + m) | O(m) | m = target string length |
| Find Anagrams | O(n) | O(1) | Fixed character set |
| Character Replacement | O(n) | O(1) | Maximum 26 letters |
| Fruit Baskets | O(n) | O(1) | Maximum 2 types |

## Real-World Applications

1. **Media Streaming**: Buffer management for video playback
2. **Network Protocols**: TCP sliding window for data transmission
3. **Data Analytics**: Moving averages and statistical calculations
4. **Image Processing**: Convolution operations and filtering
5. **Search Engines**: Indexing and pattern matching in documents
6. **Database Queries**: Range queries and aggregations
7. **Compression Algorithms**: Finding optimal compression windows

## Best Practices

1. Identify if window is fixed or variable size
2. Use appropriate data structure for tracking window state (HashMap, array, etc.)
3. Expand window by moving right pointer
4. Contract window by moving left pointer when necessary
5. Update result after each window state change
6. Consider edge cases and boundary conditions
7. Optimize space by using fixed-size arrays for character sets

## Optimization Tips

- Pre-calculate if window has constant size
- Use arrays instead of HashMaps for fixed character sets
- Track only essential window information
- Minimize contraction operations when possible
- Clear strategy for when to expand vs contract

## Related Patterns

- **Two Pointers**: Similar but used for different problem types
- **Fast-Slow Pointers**: Different pointer speeds for cycle detection
- **Prefix Sum**: Preprocessing for faster window calculations
