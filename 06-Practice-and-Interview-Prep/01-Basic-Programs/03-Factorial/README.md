# Factorial Program

## Problem Statement

Write a Java program that calculates the factorial of a given number.

**Definition:** Factorial of n (denoted as n!) is the product of all positive integers from 1 to n.
- 0! = 1 (by definition)
- 1! = 1
- 5! = 5 × 4 × 3 × 2 × 1 = 120
- n! = n × (n-1) × (n-2) × ... × 1

**Input:** A non-negative integer n
**Output:** The factorial of n

---

## Concepts Explained

### 1. **Factorial Definition**
```
0! = 1
n! = n × (n-1) × (n-2) × ... × 1 (for n ≥ 1)
```
- Special case: 0! = 1 (definition in combinatorics)
- Factorials grow very quickly
- Used in permutations and combinations

### 2. **Multiplication Accumulation**
```java
long factorial = 1;
for (int i = 2; i <= n; i++) {
    factorial *= i;  // Multiply and assign
}
```
- Similar to sum, but uses multiplication instead of addition
- Start with 1 (identity for multiplication) instead of 0

### 3. **Loop from 2 to n**
```java
for (int i = 2; i <= n; i++) {
    factorial *= i;
}
```
- Start from 2 (since 1! = 1)
- Multiply by each number up to n

### 4. **Using long Data Type**
```java
long factorial = 1;  // long instead of int
```
- Factorials grow exponentially
- `int` can only hold values up to about 12!
- `long` can hold values up to 20!

---

## Solution Breakdown

### Approach 1: Iterative Loop
```java
long factorial = 1;
for (int i = 2; i <= n; i++) {
    factorial *= i;
}
System.out.println(n + "! = " + factorial);
```

### Approach 2: Recursive (Alternative)
```java
public static long factorial(int n) {
    if (n <= 1) {
        return 1;
    }
    return n * factorial(n - 1);
}
```

### Approach 3: Using Separate Method
```java
public static long calculateFactorial(int n) {
    long factorial = 1;
    for (int i = 2; i <= n; i++) {
        factorial *= i;
    }
    return factorial;
}
```

---

## Algorithm

**Iterative Approach:**
1. Accept input n from user
2. Validate that n is non-negative
3. Initialize factorial = 1
4. Loop from i = 2 to n:
   - Multiply factorial by i
5. Print the result

**Recursive Approach:**
1. Base case: if n ≤ 1, return 1
2. Recursive case: return n × factorial(n-1)

---

## Time & Space Complexity

| Approach | Time Complexity | Space Complexity | Notes |
|----------|-----------------|------------------|-------|
| Iterative | O(n) | O(1) | Linear time, constant space |
| Recursive | O(n) | O(n) | Linear time, O(n) stack space |

**Analysis:**
- **Iterative:** Loop runs n times, very efficient
- **Recursive:** Also O(n) but uses stack space for recursion
- **Iterative is preferred** for factorial calculation

---

## Sample Input/Output

### Example 1:
```
Input:  5
Output: 5! = 120
Calculation: 5 × 4 × 3 × 2 × 1 = 120
```

### Example 2:
```
Input:  0
Output: 0! = 1
Calculation: 0! = 1
```

### Example 3:
```
Input:  10
Output: 10! = 3,628,800
Calculation: 10 × 9 × 8 × 7 × 6 × 5 × 4 × 3 × 2 × 1 = 3,628,800
```

### Example 4:
```
Input:  -5
Output: Factorial is not defined for negative numbers!
```

---

## Code Walkthrough

```java
// Step 1: Get input
System.out.print("Enter a non-negative number: ");
int n = scanner.nextInt();

// Step 2: Validate input
if (n < 0) {
    System.out.println("Factorial is not defined for negative numbers!");
    return;
}

// Step 3: Calculate factorial
long factorial = 1;
for (int i = 2; i <= n; i++) {
    factorial *= i;  // factorial = factorial * i
}

// Step 4: Display result
System.out.println(n + "! = " + factorial);
```

---

## Variations and Extensions

### Variation 1: Factorial Using Recursion
```java
public static long factorial(int n) {
    if (n == 0 || n == 1) {
        return 1;
    }
    return n * factorial(n - 1);
}
```

### Variation 2: Factorial with Memoization
```java
// Cache previously calculated factorials
static Map<Integer, Long> cache = new HashMap<>();

public static long factorial(int n) {
    if (n <= 1) return 1;
    if (cache.containsKey(n)) return cache.get(n);
    
    long result = n * factorial(n - 1);
    cache.put(n, result);
    return result;
}
```

### Variation 3: Calculate Multiple Factorials
```java
for (int i = 0; i <= n; i++) {
    System.out.println(i + "! = " + calculateFactorial(i));
}
```

### Variation 4: Factorial with BigInteger
```java
// For very large factorials
BigInteger factorial = BigInteger.ONE;
for (int i = 2; i <= n; i++) {
    factorial = factorial.multiply(BigInteger.valueOf(i));
}
```

### Variation 5: Find Factorial with Trailing Zeros
```java
// Count trailing zeros in factorial
int trailingZeros = 0;
int divisor = 5;
while (divisor <= n) {
    trailingZeros += n / divisor;
    divisor *= 5;
}
```

---

## Challenges

### Challenge 1: Factorial Using Recursion
Implement factorial using recursion instead of iteration. Compare the approaches.

### Challenge 2: Sum of Factorials
Write a program that calculates the sum of factorials for first n numbers: 1! + 2! + 3! + ... + n!

### Challenge 3: Find Number from Factorial
Given a factorial value, find which number's factorial it is.

### Challenge 4: Factorial with Big Numbers
Use `BigInteger` to calculate factorial of very large numbers (e.g., 100!, 1000!).

### Challenge 5: Trailing Zeros in Factorial
Write a program that finds how many trailing zeros are in n! without calculating the factorial.

### Challenge 6: Factorial Digits
Calculate the number of digits in n! without calculating the entire factorial (use logarithms).

---

## Common Errors and Solutions

### Error 1: Integer Overflow
```java
int factorial = 1;  // Wrong: int overflows for n > 12
for (int i = 2; i <= n; i++) {
    factorial *= i;
}
```
**Solution:** Use `long` or `BigInteger`:
```java
long factorial = 1;  // Can handle up to 20!
```

### Error 2: Starting from 1
```java
for (int i = 1; i <= n; i++) {
    factorial *= i;  // Works but unnecessary (multiply by 1)
}
```
**Solution:** Start from 2:
```java
for (int i = 2; i <= n; i++) {
    factorial *= i;
```

### Error 3: Not Handling 0!
```java
// Forgets that 0! = 1
if (n == 0) {
    System.out.println("0! = " + factorial);  // Works because factorial initialized to 1
}
```

### Error 4: Not Validating Negative Input
```java
// Doesn't check for negative input
long factorial = calculateFactorial(n);  // n could be negative
```
**Solution:** Validate first:
```java
if (n < 0) {
    System.out.println("Error: negative input");
    return;
}
```

---

## Key Takeaways

1. **Factorials grow quickly** - need appropriate data types
2. **Multiplication accumulation** is similar to sum pattern
3. **Special cases matter** - handle 0! = 1
4. **Data type matters** - int vs long vs BigInteger
5. **Recursion vs iteration** - both work, iteration preferred for factorials

---

## Mathematical Properties

- **0! = 1** (by definition)
- **n! = n × (n-1)!** (recursive definition)
- **n! grows faster than 2^n** for large n
- **Maximum for long:** 20! (before overflow)
- **Stirling's Approximation:** n! ≈ √(2πn) × (n/e)^n

---

## Practical Applications

1. **Combinations & Permutations:** nCr = n! / (r! × (n-r)!)
2. **Probability:** Calculate arrangements and probabilities
3. **Algorithm Analysis:** Used in complexity analysis
4. **Physics:** Quantum mechanics calculations
5. **Computer Science:** Dynamic programming problems

---

## Next Steps

1. Implement and understand the basic version
2. Try the recursive version
3. Experiment with large numbers
4. Try challenges with BigInteger
5. Move on to the **Fibonacci Program**

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 20 minutes
