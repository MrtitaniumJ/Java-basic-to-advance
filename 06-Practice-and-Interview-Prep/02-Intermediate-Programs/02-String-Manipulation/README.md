# String Manipulation

## Problem Statement

String manipulation is one of the most common tasks in programming. This program explores advanced string processing techniques including checking for anagrams, generating all permutations, finding substring patterns, and other practical string operations. These problems are frequently asked in technical interviews and form the basis for text processing applications.

String operations are fundamental in:
- Search engines and text searching
- Spell checkers and autocorrect
- DNA sequence analysis
- Cryptography and encoding
- Natural language processing

## Concepts

### Anagrams
An anagram is formed by rearranging the letters of another word, using exactly the same letters.
- **Example:** "listen" and "silent" are anagrams
- **Key Property:** Same character frequencies, possibly different order
- **Detection Methods:** Sorting or frequency mapping

### Permutations
A permutation is an arrangement of all elements where order matters.
- **Example:** Permutations of "ABC" are: ABC, ACB, BAC, BCA, CAB, CBA
- **Count Formula:** For string of length n, there are n! permutations
- **Approach:** Recursive backtracking

### Palindromes
A word, phrase, or number that reads the same forwards and backwards.
- **Example:** "racecar", "noon", "A man a plan a canal Panama"
- **Method:** Compare character from start and end, moving inward
- **Consideration:** Ignore spaces and case sensitivity

### Substring Patterns
Finding occurrences of patterns within text.
- **Example:** Find "lo" in "hello world" at position 3
- **Applications:** Search, pattern matching, text analysis
- **Methods:** Linear search, KMP, or built-in functions

### String Rotation
Checking if one string is a rotation of another.
- **Example:** "waterbottle" rotated by 5 gives "bottlewater"
- **Clever Trick:** If s2 is rotation of s1, then s2 will be substring of s1+s1

## Approaches

### Anagram Detection Approaches

**Approach 1: Sorting**
- Sort characters of both strings
- Compare sorted arrays
- Time: O(n log n), Space: O(1)

**Approach 2: Frequency Map**
- Count character frequencies in both strings
- Compare frequency maps
- Time: O(n), Space: O(k) where k is alphabet size

**Approach 3: Character Array**
- Use array for ASCII character counts
- More efficient than HashMap for fixed alphabets
- Time: O(n), Space: O(1)

### Permutation Generation Approaches

**Recursive Backtracking (Used in Program)**
```
permute(string, start, end):
  if start == end:
    add string to results
  else:
    for i = start to end:
      swap(start, i)
      permute(string, start+1, end)
      swap(start, i)  // backtrack
```

**Iterative Approach**
- Use next lexicographic permutation algorithm
- More complex but avoids recursion overhead

### Substring Finding Approaches

**Simple Linear Search**
- Iterate through text, check for pattern at each position
- Time: O(m × n) where m = text length, n = pattern length

**KMP Algorithm**
- More efficient for large patterns
- Time: O(m + n) using failure function

## Complexity Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Anagram (Sorting) | O(n log n) | O(1) |
| Anagram (Map) | O(n) | O(k) |
| Permutations | O(n! × n) | O(n!) |
| Palindrome Check | O(n) | O(1) |
| Substring Finding | O(m × n) | O(1) |
| String Rotation | O(n) | O(n) |
| Char Frequency | O(n) | O(k) |

## Sample Input/Output

```
===== STRING MANIPULATION DEMO =====

--- ANAGRAM CHECK ---
"listen" and "silent" are anagrams? true
"listen" and "hello" are anagrams? false

--- ANAGRAM CHECK (Using Map) ---
"listen" and "silent" are anagrams? true

--- PERMUTATIONS ---
All permutations of "ABC":
  ABC
  ACB
  BAC
  BCA
  CAB
  CBA

--- PALINDROME CHECK ---
"racecar" is palindrome? true
"hello" is palindrome? false
"A man a plan a canal Panama" is palindrome? true

--- FIND SUBSTRING OCCURRENCES ---
Finding "hello" in "hello world, hello java, hello programming"
Found at positions: [0, 13, 25]

--- STRING ROTATION CHECK ---
"bottlewater" is rotation of "waterbottle"? true

--- REVERSE EACH WORD ---
Original: "hello world java"
Reversed words: "olleh dlrow java"

--- CHARACTER FREQUENCY ---
Character frequency in "programming":
  'p' -> 1
  'r' -> 2
  'o' -> 1
  'g' -> 2
  'a' -> 1
  'm' -> 2
  'i' -> 1
  'n' -> 1

--- FIRST NON-REPEATING CHARACTER ---
First non-repeating character in "programming": p
```

## Key Methods Explained

### `areAnagrams(String str1, String str2)`
- Removes spaces and converts to lowercase
- Sorts character arrays and compares
- Simple but slightly less efficient approach

### `areAnagramsUsingMap(String str1, String str2)`
- Uses HashMap for character frequency counting
- Linear time complexity
- More efficient for real-world use

### `generatePermutations(String str)`
- Uses recursive backtracking algorithm
- Generates all n! permutations
- Stores in Set to avoid duplicates

### `isPalindrome(String str)`
- Removes non-alphanumeric characters
- Compares characters from both ends moving inward
- Handles case-insensitivity

### `findSubstringOccurrences(String text, String pattern)`
- Uses indexOf method with position tracking
- Finds all overlapping occurrences
- Simple and effective approach

## Variations and Challenges

### Variation 1: Case-Insensitive Anagram
Modify anagram check to treat 'A' and 'a' as different characters

### Variation 2: Unique Permutations
Generate only unique permutations for strings with duplicate characters
```java
public static Set<String> uniquePermutations(String str)
```

### Variation 3: Longest Palindromic Substring
Find the longest substring that is a palindrome within a given string
```java
public static String longestPalindrome(String str)
```

### Variation 4: Edit Distance (Levenshtein Distance)
Calculate minimum edits to transform one string into another
```java
public static int editDistance(String s1, String s2)
```

### Challenge 1: Wildcard Pattern Matching
Implement pattern matching with '?' (any char) and '*' (zero or more chars)
```java
public static boolean isMatch(String text, String pattern)
```

### Challenge 2: KMP Algorithm
Implement Knuth-Morris-Pratt algorithm for efficient substring searching

### Challenge 3: Trie for Multiple Pattern Search
Build a Trie to search for multiple patterns efficiently

### Challenge 4: Longest Common Subsequence
Find the longest sequence of characters common to both strings

## Interview Questions

1. **How would you check anagrams of a large number of strings efficiently?**
   - Answer: Sort strings and group by sorted form, or use HashMap

2. **What's the space-time tradeoff when generating permutations?**
   - Answer: Recursive uses O(n) stack but O(n!) results; iterative avoids stack

3. **How would you optimize substring finding for very large texts?**
   - Answer: Use KMP, Boyer-Moore, or Rabin-Karp algorithm

4. **Can you find anagrams of a string in a dictionary efficiently?**
   - Answer: Sort each dictionary word and use HashMap to group

5. **How would you handle Unicode and special characters in strings?**
   - Answer: Use Character class methods or regex for filtering

## Edge Cases to Consider

- **Empty strings:** Should return true for anagrams, handle gracefully
- **Single character:** All operations should work correctly
- **Duplicate characters:** Permutations grow exponentially
- **Special characters and spaces:** Must decide whether to include or exclude
- **Very long strings:** Consider memory and performance implications
- **Case sensitivity:** Decide on case-handling for each operation
- **Null inputs:** Add null checks to prevent NullPointerException

## Running the Program

```powershell
# Compile
javac StringManipulation.java

# Run
java StringManipulation
```

## Tips for Learning

1. **Understand the Problem:** Clearly define what you're looking for
2. **Choose Method Wisely:** Sorting vs. Frequency - know the tradeoffs
3. **Test Edge Cases:** Empty, single char, all duplicates
4. **Visualize:** Draw out permutations for small strings
5. **Optimize:** Start simple, then improve complexity

## Common Mistakes

- Forgetting to handle spaces or case sensitivity in anagrams
- Not backtracking correctly in permutation generation
- Off-by-one errors in substring searching
- Not considering empty results for permutations
- Assuming all characters are ASCII

---

**Practice:** Master these string operations. They're fundamental building blocks for more complex text processing problems and frequently appear in interviews.
