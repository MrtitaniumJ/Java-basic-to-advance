# Palindrome Checker Program

## Problem Statement

Write a Java program that checks if a given string or number is a palindrome.

**Definition:** A palindrome is a word, phrase, or number that reads the same forwards and backwards.

**Examples:**
- String: "racecar", "level", "radar"
- Number: 121, 1331, 12321
- Phrase: "A man, a plan, a canal: Panama" (ignoring spaces and punctuation)

**Input:** A string or number
**Output:** Whether it's a palindrome or not

---

## Concepts Explained

### 1. **String Comparison**
```java
String str = "racecar";
String reversed = new StringBuilder(str).reverse().toString();
if (str.equals(reversed)) {
    System.out.println("Palindrome!");
}
```
- Compare original with reversed
- If equal, it's a palindrome

### 2. **Two-Pointer Approach**
```java
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
```
- Compare from both ends moving inward
- More efficient than creating reversed string

### 3. **String Cleaning**
```java
String cleaned = input.replaceAll(" ", "").toLowerCase();
```
- Remove spaces: `replaceAll(" ", "")`
- Convert to lowercase: `toLowerCase()`
- Handle case-insensitivity

### 4. **StringBuilder for Reversal**
```java
String reversed = new StringBuilder(str).reverse().toString();
```
- `StringBuilder` is efficient for string manipulation
- `reverse()` method reverses the sequence
- `toString()` converts back to String

---

## Solution Breakdown

### Approach 1: String Reversal
```java
String original = "racecar";
String reversed = new StringBuilder(original).reverse().toString();
boolean isPalindrome = original.equals(reversed);
```

### Approach 2: Two-Pointer
```java
String str = "racecar";
int left = 0, right = str.length() - 1;

while (left < right) {
    if (str.charAt(left) != str.charAt(right)) {
        return false;
    }
    left++;
    right--;
}
return true;
```

### Approach 3: Recursive
```java
boolean isPalindrome(String str, int left, int right) {
    if (left >= right) return true;
    if (str.charAt(left) != str.charAt(right)) return false;
    return isPalindrome(str, left + 1, right - 1);
}
```

---

## Algorithm

**Two-Pointer Approach:**
1. Accept string input
2. Clean string (remove spaces, convert to lowercase)
3. Initialize left pointer at start, right pointer at end
4. While left < right:
   - Compare characters at left and right positions
   - If different, return false (not palindrome)
   - Move left forward, right backward
5. If loop completes, return true (is palindrome)

---

## Time & Space Complexity

| Approach | Time Complexity | Space Complexity | Notes |
|----------|-----------------|------------------|-------|
| String Reversal | O(n) | O(n) | Creates new reversed string |
| Two-Pointer | O(n) | O(1) | In-place checking |
| Recursive | O(n) | O(n) | Stack depth proportional to n |

**Analysis:**
- **Two-Pointer:** Best - O(n) time, O(1) space
- **Reversal:** O(n) time but O(n) extra space
- **Recursive:** Works but less efficient

---

## Sample Input/Output

### Example 1: Simple Word
```
Input:  racecar
Output: "racecar" is a PALINDROME
Original (cleaned): racecar
Reversed:          racecar
```

### Example 2: With Spaces
```
Input:  race car
Output: "race car" is NOT a palindrome
Original (cleaned): racecar
Reversed:          racecar (cleaned comparison shows it IS palindrome!)
```

### Example 3: Case Insensitive
```
Input:  Radar
Output: "Radar" is a PALINDROME
Original (cleaned): radar
Reversed:          radar
```

### Example 4: Number
```
Input:  121
Output: "121" is a PALINDROME
Original (cleaned): 121
Reversed:          121
```

---

## Code Walkthrough

```java
// Step 1: Clean the input (remove spaces, lowercase)
String cleaned = str.replaceAll(" ", "").toLowerCase();
//    "RaceCar" → "racecar"
//    "race car" → "racecar"

// Step 2: Initialize pointers
int left = 0;
int right = cleaned.length() - 1;  // For "racecar": right = 6

// Step 3: Compare from both ends
while (left < right) {
    // Compare characters
    if (cleaned.charAt(left) != cleaned.charAt(right)) {
        return false;  // Not equal, not a palindrome
    }
    // Move pointers
    left++;      // Move forward
    right--;     // Move backward
}

// Step 4: All characters matched
return true;
```

---

## Variations and Extensions

### Variation 1: Palindrome Number Check
```java
public static boolean isPalindromeNumber(int num) {
    String str = Integer.toString(num);
    return isPalindrome(str);
}
```

### Variation 2: Palindrome with Punctuation
```java
String cleaned = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
// Removes all non-alphanumeric characters
```

### Variation 3: Find Longest Palindrome in String
```java
String longest = "";
for (int i = 0; i < str.length(); i++) {
    for (int j = i + 1; j <= str.length(); j++) {
        String sub = str.substring(i, j);
        if (isPalindrome(sub) && sub.length() > longest.length()) {
            longest = sub;
        }
    }
}
```

### Variation 4: Minimum Insertions for Palindrome
```java
// Find minimum characters to insert to make palindrome
// Can use dynamic programming
```

### Variation 5: Palindrome using Recursion
```java
boolean isPalindrome(String str, int left, int right) {
    if (left >= right) return true;
    if (str.charAt(left) != str.charAt(right)) return false;
    return isPalindrome(str, left + 1, right - 1);
}
```

---

## Challenges

### Challenge 1: Palindrome Phrase
Check palindromes ignoring spaces and punctuation (e.g., "A man, a plan, a canal: Panama").

### Challenge 2: Longest Palindrome
Find the longest palindromic substring in a given string.

### Challenge 3: Check All Substrings
Find all palindromic substrings in a string.

### Challenge 4: Minimum Edits
Find minimum number of characters to remove to make string a palindrome.

### Challenge 5: Palindrome Partitions
Partition a string into all possible palindromic parts.

### Challenge 6: Palindrome Generation
Generate the shortest palindrome by adding characters to the beginning.

---

## Common Errors and Solutions

### Error 1: Not Handling Case Sensitivity
```java
String str = "Racecar";
if (str.equals(reverseString(str))) {  // Wrong! "Racecar" != "racecaR"
    // ...
}
```
**Solution:** Convert to lowercase:
```java
String str = "Racecar".toLowerCase();
if (str.equals(reverseString(str))) {  // Correct
    // ...
}
```

### Error 2: Not Removing Spaces
```java
String str = "race car";
// Checking as-is will fail because "race car" != "rac ecar"
```
**Solution:** Remove spaces:
```java
String cleaned = str.replaceAll(" ", "").toLowerCase();
```

### Error 3: Off-by-One Error
```java
for (int i = 0; i < str.length() / 2; i++) {  // Might skip middle char
    if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
        return false;
    }
}
```
**Solution:** Use while loop correctly:
```java
int left = 0, right = str.length() - 1;
while (left < right) {
    if (str.charAt(left) != str.charAt(right)) {
        return false;
    }
    left++;
    right--;
}
```

### Error 4: Creating Unnecessary String Objects
```java
String reversed = new StringBuilder(str).reverse().toString();
String reversed2 = new StringBuilder(str).reverse().toString();
// Creates multiple StringBuilder objects
```
**Solution:** Use two-pointer approach instead (no extra objects)

---

## Key Takeaways

1. **Two-pointer technique** is efficient for palindrome checking
2. **String cleaning** (spaces, case) is important for real-world data
3. **Multiple approaches** - reversal vs two-pointer vs recursive
4. **Time/Space trade-offs** - choose based on constraints
5. **String methods** - `charAt()`, `replaceAll()`, `substring()`

---

## Real-World Applications

1. **Data Validation:** Check symmetric patterns
2. **DNA Analysis:** Find palindromic DNA sequences
3. **Linguistics:** Analyze word patterns
4. **Cryptography:** Palindromic structures in encryption
5. **File System:** Mirror/symmetric operations

---

## Next Steps

1. Implement and test basic palindrome checker
2. Try with phrases and punctuation
3. Implement longest palindrome challenge
4. Test with numbers
5. Move on to the **Reverse String Program**

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 20 minutes
