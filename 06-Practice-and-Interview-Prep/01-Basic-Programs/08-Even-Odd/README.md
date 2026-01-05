# Even/Odd Checker Program

## Problem Statement

Write a Java program that determines whether a given number is even or odd.

**Definition:**
- **Even number:** Divisible by 2 with remainder 0
- **Odd number:** Not divisible by 2 (remainder is 1)

**Input:** An integer
**Output:** Whether the number is even or odd

**Examples:**
- 4 is EVEN (4 ÷ 2 = 2, remainder 0)
- 7 is ODD (7 ÷ 2 = 3, remainder 1)
- 0 is EVEN (0 ÷ 2 = 0, remainder 0)
- -3 is ODD (-3 ÷ 2 = -1, remainder -1)

---

## Concepts Explained

### 1. **Modulo Operator (%)**
```java
int remainder = number % 2;
if (remainder == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```
- `%` gives the remainder after division
- `num % 2 == 0` → number is even
- `num % 2 != 0` → number is odd

### 2. **Conditional Statements**
```java
if (number % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```
- `if` executes if condition is true
- `else` executes if condition is false

### 3. **Bitwise AND Operation**
```java
if ((number & 1) == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```
- `&` is bitwise AND
- Last bit 0 → even, 1 → odd
- Fastest method (used internally)

### 4. **Boolean Methods**
```java
public static boolean isEven(int num) {
    return num % 2 == 0;
}

public static boolean isOdd(int num) {
    return num % 2 != 0;
}
```
- Return true/false instead of printing
- More reusable and testable

---

## Solution Breakdown

### Approach 1: Simple If-Else
```java
if (number % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

### Approach 2: Using Methods
```java
public static boolean isEven(int num) {
    return num % 2 == 0;
}

if (isEven(number)) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

### Approach 3: Using Ternary Operator
```java
String result = (number % 2 == 0) ? "Even" : "Odd";
System.out.println(result);
```

### Approach 4: Using Bitwise AND
```java
if ((number & 1) == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

---

## Algorithm

**Basic Algorithm:**
1. Accept integer input from user
2. Calculate remainder: number % 2
3. If remainder == 0, number is EVEN
4. Else (remainder == 1), number is ODD
5. Display result

**Step-by-Step:**
1. Read number from input
2. Use modulo operator to find remainder when divided by 2
3. Check remainder:
   - If 0: even
   - If 1: odd
4. Print appropriate message

---

## Time & Space Complexity

| Aspect | Complexity |
|--------|-----------|
| Time Complexity | O(1) - Single operation |
| Space Complexity | O(1) - No extra space |

**Analysis:** 
- Constant time: always one modulo operation
- No loops or recursion
- Simple arithmetic operation

---

## Sample Input/Output

### Example 1: Positive Even Number
```
Input:  24
Output: 24 is an EVEN number
Explanation:
  24 % 2 = 0
  Since remainder is 0, 24 is EVEN
```

### Example 2: Positive Odd Number
```
Input:  17
Output: 17 is an ODD number
Explanation:
  17 % 2 = 1
  Since remainder is 1, 17 is ODD
```

### Example 3: Zero
```
Input:  0
Output: 0 is an EVEN number
Explanation:
  0 % 2 = 0
  Since remainder is 0, 0 is EVEN
```

### Example 4: Negative Number
```
Input:  -5
Output: -5 is an ODD number
Explanation:
  -5 % 2 = -1 (or 1 depending on language)
  Since remainder is non-zero, -5 is ODD
```

---

## Code Walkthrough

```java
// Step 1: Get input
System.out.print("Enter an integer number: ");
int number = scanner.nextInt();  // e.g., 12

// Step 2: Use modulo to check divisibility
int remainder = number % 2;  // 12 % 2 = 0

// Step 3: Check remainder
if (remainder == 0) {
    // Remainder is 0, so it's even
    System.out.println(number + " is EVEN");
} else {
    // Remainder is 1, so it's odd
    System.out.println(number + " is ODD");
}

// Output: 12 is EVEN
```

---

## Variations and Extensions

### Variation 1: Multiple Numbers
```java
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
for (int num : numbers) {
    System.out.println(num + " is " + (isEven(num) ? "EVEN" : "ODD"));
}
```

### Variation 2: Count Even and Odd
```java
int evenCount = 0, oddCount = 0;
for (int i = 1; i <= n; i++) {
    if (i % 2 == 0) {
        evenCount++;
    } else {
        oddCount++;
    }
}
System.out.println("Even: " + evenCount + ", Odd: " + oddCount);
```

### Variation 3: Sum of Even and Odd
```java
int evenSum = 0, oddSum = 0;
for (int i = 1; i <= n; i++) {
    if (i % 2 == 0) {
        evenSum += i;
    } else {
        oddSum += i;
    }
}
System.out.println("Sum of even: " + evenSum);
System.out.println("Sum of odd: " + oddSum);
```

### Variation 4: Range Checker
```java
// Find all even/odd numbers in a range
System.out.print("Even numbers: ");
for (int i = 1; i <= 20; i++) {
    if (i % 2 == 0) {
        System.out.print(i + " ");
    }
}
```

### Variation 5: Using Bitwise Operation
```java
// Fastest way using bitwise AND
if ((number & 1) == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
// & 1 checks the last bit (0 = even, 1 = odd)
```

---

## Challenges

### Challenge 1: Count Even and Odd
Write a program that counts even and odd numbers in a given range.

### Challenge 2: Sum Even and Odd Separately
Calculate the sum of even numbers and odd numbers separately from 1 to n.

### Challenge 3: Filter Even/Odd Numbers
Given an array of numbers, separate and display only even or only odd numbers.

### Challenge 4: Consecutive Even/Odd
Find the longest sequence of consecutive even or odd numbers in an array.

### Challenge 5: Even/Odd Checker for Multiple Inputs
Accept multiple numbers and display statistics (count, sum, average).

### Challenge 6: Prime and Even/Odd
Combine this with prime checking: find numbers that are both prime and odd/even.

---

## Common Errors and Solutions

### Error 1: Wrong Comparison
```java
if (number % 2 = 0) {  // Wrong: using assignment (=) instead of comparison (==)
    System.out.println("Even");
}
```
**Solution:** Use comparison operator:
```java
if (number % 2 == 0) {  // Correct
    System.out.println("Even");
}
```

### Error 2: Forgetting Else or Else-If
```java
if (number % 2 == 0) {
    System.out.println("Even");
}
if (number % 2 != 0) {  // Unnecessary, should use else
    System.out.println("Odd");
}
```
**Solution:** Use else clause:
```java
if (number % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

### Error 3: Misunderstanding Modulo with Negatives
```java
// Note: In Java, -5 % 2 = -1 (not 1)
// But -1 != 0, so it's correctly identified as odd
```

### Error 4: Comparing Results Incorrectly
```java
if (number % 2) {  // Wrong in Java - can't use int as boolean
    System.out.println("Odd");
}
```
**Solution:** Compare explicitly:
```java
if (number % 2 != 0) {  // Correct
    System.out.println("Odd");
}
```

---

## Key Takeaways

1. **Modulo operator (%)** gives remainder
2. **num % 2 == 0** → EVEN
3. **num % 2 != 0** → ODD
4. **Simplest basic check** - perfect for learning
5. **Multiple approaches** - modulo, bitwise, etc.

---

## Performance Notes

All approaches are O(1):
- Modulo: `num % 2`
- Bitwise: `num & 1`
- Both equally fast in modern compilers

**Readability preferred over micro-optimization** for this simple case.

---

## Real-World Applications

1. **Data Classification:** Separate data into categories
2. **Game Logic:** Alternating turns (even/odd rounds)
3. **Scheduling:** Even/odd days or weeks
4. **Array Manipulation:** Process alternate elements
5. **Mathematical Algorithms:** Number theory operations

---

## Next Steps

1. Implement and test basic version
2. Try all four approaches
3. Compare their behavior
4. Implement the variations
5. Move on to the **Largest Number Program**

---

**Last Updated:** January 2026
**Difficulty:** ⭐ Beginner
**Estimated Time:** 10 minutes
