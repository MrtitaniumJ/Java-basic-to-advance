# String Reversal Program

## Problem Statement

Write a Java program that reverses a given string using multiple methods.

**Input:** A string
**Output:** The reversed string, and check if it's a palindrome

**Examples:**
- "Hello" → "olleH"
- "Java" → "avaJ"
- "racecar" → "racecar" (palindrome!)

---

## Concepts Explained

### 1. **StringBuilder.reverse()**
```java
String original = "Hello";
String reversed = new StringBuilder(original).reverse().toString();
// Most efficient and readable method
```
- `StringBuilder` is designed for string manipulation
- `reverse()` method specifically for reversal
- `toString()` converts back to String

### 2. **Character Array Approach**
```java
String str = "Hello";
char[] chars = str.toCharArray();  // Convert to array
// Reverse the array
String reversed = new String(chars);  // Convert back
```
- Convert to character array
- Iterate and reconstruct

### 3. **Two-Pointer Swapping**
```java
int left = 0, right = chars.length - 1;
while (left < right) {
    char temp = chars[left];
    chars[left] = chars[right];
    chars[right] = temp;
    left++;
    right--;
}
```
- Use two pointers from both ends
- Swap characters moving inward
- In-place reversal

### 4. **String Concatenation (Inefficient)**
```java
String reversed = "";
for (int i = str.length() - 1; i >= 0; i--) {
    reversed += str.charAt(i);  // Creates new string each time!
}
```
- Creates new String object for each concatenation
- Avoid this method for performance

### 5. **Recursion**
```java
public static String reverse(String str) {
    if (str.isEmpty()) return str;
    return reverse(str.substring(1)) + str.charAt(0);
}
```
- Elegant but inefficient
- O(n²) time due to substring calls

---

## Algorithm

**StringBuilder Approach (Recommended):**
1. Accept string input
2. Create StringBuilder with the string
3. Call reverse() method
4. Convert back to String with toString()
5. Return reversed string

**Two-Pointer Approach:**
1. Convert string to character array
2. Initialize left pointer at start (0)
3. Initialize right pointer at end (length-1)
4. While left < right:
   - Swap characters at left and right
   - Move left forward, right backward
5. Convert array back to String

---

## Time & Space Complexity

| Method | Time Complexity | Space Complexity | Notes |
|--------|-----------------|------------------|-------|
| StringBuilder | O(n) | O(n) | Best method - efficient and readable |
| Two-Pointer | O(n) | O(n) | In-place in array, but array takes space |
| String Concat | O(n²) | O(n) | Very inefficient - avoid |
| Recursion | O(n²) | O(n) | Inefficient due to substring calls |
| Loop Char by Char | O(n) | O(n) | Acceptable, but StringBuilder better |

---

## Sample Input/Output

### Example 1:
```
Input:  Hello
Output: Original String: Hello
        Method 1 (StringBuilder):  olleH
        Method 2 (Array Loop):    olleH
        Method 3 (Two-Pointer):   olleH
        Method 4 (Recursion):     olleH

Character Breakdown:
Original: H e l l o
Reversed: o l l e H

This string is NOT a palindrome
```

### Example 2:
```
Input:  racecar
Output: Original String: racecar
        (All methods show): racecar

This string is a PALINDROME
```

### Example 3:
```
Input:  Java Programming
Output: Original String: Java Programming
        (All methods show): gnimmargorP avaJ

Character Breakdown:
Original: J a v a   P r o g r a m m i n g
Reversed: g n i m m a r g o r P   a v a J

This string is NOT a palindrome
```

---

## Code Walkthrough

### Method 1: StringBuilder (Simplest)
```java
public static String reverseUsingStringBuilder(String str) {
    return new StringBuilder(str).reverse().toString();
}
// That's it! One line!
```

### Method 2: Character Array
```java
public static String reverseUsingArray(String str) {
    // Step 1: Convert to character array
    char[] chars = str.toCharArray();
    
    // Step 2: Build reversed string by iterating backwards
    String reversed = "";
    for (int i = chars.length - 1; i >= 0; i--) {
        reversed += chars[i];  // Inefficient but works
    }
    
    // Step 3: Return result
    return reversed;
}
```

### Method 3: Two-Pointer Swap
```java
public static String reverseUsingTwoPointer(String str) {
    // Step 1: Convert to array
    char[] chars = str.toCharArray();
    
    // Step 2: Initialize pointers
    int left = 0, right = chars.length - 1;
    
    // Step 3: Swap from both ends
    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        
        left++;
        right--;
    }
    
    // Step 4: Convert back to String
    return new String(chars);
}
```

---

## Variations and Extensions

### Variation 1: Reverse Only Vowels
```java
public static String reverseVowels(String str) {
    String vowels = "aeiouAEIOU";
    List<Character> vowelChars = new ArrayList<>();
    
    for (char c : str.toCharArray()) {
        if (vowels.indexOf(c) != -1) {
            vowelChars.add(c);
        }
    }
    
    Collections.reverse(vowelChars);
    
    StringBuilder result = new StringBuilder();
    int index = 0;
    for (char c : str.toCharArray()) {
        if (vowels.indexOf(c) != -1) {
            result.append(vowelChars.get(index++));
        } else {
            result.append(c);
        }
    }
    return result.toString();
}
```

### Variation 2: Reverse Words
```java
public static String reverseWords(String str) {
    String[] words = str.split(" ");
    StringBuilder reversed = new StringBuilder();
    
    for (int i = words.length - 1; i >= 0; i--) {
        reversed.append(words[i]);
        if (i > 0) reversed.append(" ");
    }
    
    return reversed.toString();
}
// "Hello World Java" → "Java World Hello"
```

### Variation 3: Reverse in Groups
```java
public static String reverseInGroups(String str, int groupSize) {
    StringBuilder result = new StringBuilder();
    
    for (int i = 0; i < str.length(); i += groupSize) {
        int end = Math.min(i + groupSize, str.length());
        String group = str.substring(i, end);
        result.append(new StringBuilder(group).reverse());
    }
    
    return result.toString();
}
// "Hello World", size=2 → "lHeLWolro"
```

### Variation 4: Reverse with Character Frequency
```java
// Count character frequencies, then reconstruct in reverse
Map<Character, Integer> freq = new HashMap<>();
for (char c : str.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}
```

### Variation 5: Reverse Special Handling
```java
public static String reverseKeepSpecial(String str) {
    // Keep special characters in place, reverse only alphanumeric
    char[] result = str.toCharArray();
    int left = 0, right = str.length() - 1;
    
    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(result[left])) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(result[right])) {
            right--;
        }
        
        char temp = result[left];
        result[left] = result[right];
        result[right] = temp;
        
        left++;
        right--;
    }
    
    return new String(result);
}
```

---

## Challenges

### Challenge 1: Reverse Only Vowels
Keep consonants in place, reverse only vowels in the string.

### Challenge 2: Reverse Words Order
Reverse the order of words in a sentence.

### Challenge 3: Reverse in Groups
Reverse a string in groups of given size.

### Challenge 4: Reverse Preserving Case
Reverse string but keep uppercase/lowercase positions.

### Challenge 5: Efficient Large String Reversal
Reverse a very large string efficiently (consider memory constraints).

### Challenge 6: Reverse and Count
Reverse a string and count character frequencies.

---

## Common Errors and Solutions

### Error 1: String Concatenation in Loop
```java
String reversed = "";
for (int i = str.length() - 1; i >= 0; i--) {
    reversed += str.charAt(i);  // Creates new String each iteration!
}
// O(n²) time complexity - very slow!
```
**Solution:** Use StringBuilder:
```java
StringBuilder reversed = new StringBuilder();
for (int i = str.length() - 1; i >= 0; i--) {
    reversed.append(str.charAt(i));
}
```

### Error 2: Forgetting toString() Conversion
```java
StringBuilder sb = new StringBuilder(str).reverse();
System.out.println(sb);  // Works, but not best practice
String result = sb.toString();  // Better
```

### Error 3: Not Handling Empty String
```java
// Recursion without base case
public static String reverse(String str) {
    return reverse(str.substring(1)) + str.charAt(0);  // Error if empty!
}
```
**Solution:** Add base case:
```java
public static String reverse(String str) {
    if (str.isEmpty()) return str;
    return reverse(str.substring(1)) + str.charAt(0);
}
```

### Error 4: Modifying Original String
```java
String original = "Hello";
// Strings are immutable - can't modify original
// Any reversal creates new string
```

---

## Key Takeaways

1. **StringBuilder.reverse()** is the best method - use it!
2. **String immutability** - reversal always creates new string
3. **Efficiency matters** - avoid string concatenation loops
4. **Multiple approaches** teach different techniques
5. **Character arrays** useful for in-place operations

---

## Performance Comparison

For string of length 10,000:
- **StringBuilder:** ~0.1ms
- **Two-Pointer:** ~0.2ms  
- **Character Loop:** ~5ms
- **String Concat:** ~5000ms (avoid!)
- **Recursion:** ~10000ms (avoid!)

---

## Real-World Applications

1. **Text Processing:** Reverse search
2. **Palindrome Checking:** Used in previous program
3. **Data Validation:** Mirror/symmetric patterns
4. **File Processing:** Reverse log reading
5. **String Manipulation:** Text formatting

---

## Next Steps

1. Implement all four methods
2. Compare their outputs
3. Measure performance differences
4. Try the variation challenges
5. Move on to the **Even/Odd Checker**

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 15 minutes
