# Fibonacci Program

## Problem Statement

Write a Java program that generates and prints the Fibonacci sequence up to n terms.

**Definition:** The Fibonacci sequence is a series of numbers where each number is the sum of the two preceding ones.
- F(0) = 0
- F(1) = 1
- F(n) = F(n-1) + F(n-2) for n > 1

**Sequence:** 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...

**Input:** Number of terms to generate
**Output:** The Fibonacci series

---

## Concepts Explained

### 1. **Fibonacci Definition**
```
F(0) = 0
F(1) = 1
F(n) = F(n-1) + F(n-2)
```
- Each number is sum of previous two numbers
- Starts with 0 and 1
- Used extensively in computer science and nature

### 2. **Variable Swapping Pattern**
```java
long num1 = 0, num2 = 1;
for (int i = 1; i <= n; i++) {
    System.out.print(num1 + " ");
    
    long nextNum = num1 + num2;
    num1 = num2;         // Move forward
    num2 = nextNum;      // Move forward
}
```
- Maintain two previous numbers
- Calculate next number
- Shift values forward

### 3. **Loop-based Generation**
```java
long num1 = 0, num2 = 1;
long fib = num1 + num2;  // First Fibonacci number
num1 = num2;
num2 = fib;
```
- Initialize with F(0) and F(1)
- Generate subsequent numbers in loop

---

## Solution Breakdown

### Approach 1: Iterative with Variable Shifting
```java
long num1 = 0, num2 = 1;
for (int i = 1; i <= n; i++) {
    System.out.print(num1 + " ");
    long nextNum = num1 + num2;
    num1 = num2;
    num2 = nextNum;
}
```

### Approach 2: Using Array
```java
long[] fib = new long[n];
fib[0] = 0;
fib[1] = 1;
for (int i = 2; i < n; i++) {
    fib[i] = fib[i-1] + fib[i-2];
}
```

### Approach 3: Recursive
```java
public static long fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n-1) + fibonacci(n-2);
}
```

---

## Algorithm

**Iterative Approach:**
1. Accept input n from user
2. Initialize two variables: num1 = 0, num2 = 1
3. Print num1
4. Loop from 2 to n:
   - Calculate nextNum = num1 + num2
   - Update: num1 = num2, num2 = nextNum
   - Print num1
5. Display complete series

---

## Time & Space Complexity

| Approach | Time Complexity | Space Complexity | Notes |
|----------|-----------------|------------------|-------|
| Iterative | O(n) | O(1) | Efficient, constant space |
| Array-based | O(n) | O(n) | Uses extra array space |
| Recursive | O(2^n) | O(n) | Very inefficient! |

**Analysis:**
- **Iterative:** Most efficient, O(n) time and O(1) space
- **Array:** Easier to understand but uses O(n) space
- **Recursive:** Very inefficient - recalculates same values

---

## Sample Input/Output

### Example 1:
```
Input:  7
Output: Fibonacci Series: 0 1 1 2 3 5 8
```

### Example 2:
```
Input:  10
Output: Fibonacci Series: 0 1 1 2 3 5 8 13 21 34
```

### Example 3:
```
Input:  15
Output: Fibonacci Series: 0 1 1 2 3 5 8 13 21 34 55 89 144 233 377
```

### Example 4:
```
Input:  5
Output: Fibonacci Series: 0 1 1 2 3

Individual Fibonacci Numbers:
F(0) = 0
F(1) = 1
F(2) = 1
F(3) = 2
F(4) = 3
```

---

## Code Walkthrough

```java
// Step 1: Initialize first two Fibonacci numbers
long num1 = 0, num2 = 1;
System.out.print("Fibonacci Series: ");

// Step 2: Loop to generate series
for (int i = 1; i <= n; i++) {
    // Print current number
    System.out.print(num1 + " ");
    
    // Calculate next number
    long nextNum = num1 + num2;  // Sum of previous two
    
    // Shift values forward
    num1 = num2;          // Current becomes previous
    num2 = nextNum;       // Next becomes current
}
```

---

## Variations and Extensions

### Variation 1: Get nth Fibonacci Number
```java
public static long getFibonacci(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;
    
    long a = 0, b = 1;
    for (int i = 2; i <= n; i++) {
        long temp = a + b;
        a = b;
        b = temp;
    }
    return b;
}
```

### Variation 2: Fibonacci with Recursion and Memoization
```java
static Map<Integer, Long> memo = new HashMap<>();

public static long fibonacci(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    
    long result = fibonacci(n-1) + fibonacci(n-2);
    memo.put(n, result);
    return result;
}
```

### Variation 3: Find Sum of Fibonacci Series
```java
long sum = 0;
long num1 = 0, num2 = 1;
for (int i = 1; i <= n; i++) {
    sum += num1;
    long nextNum = num1 + num2;
    num1 = num2;
    num2 = nextNum;
}
System.out.println("Sum of Fibonacci series: " + sum);
```

### Variation 4: Fibonacci Until a Limit
```java
// Generate Fibonacci numbers until they exceed limit
long num1 = 0, num2 = 1;
while (num1 <= limit) {
    System.out.print(num1 + " ");
    long nextNum = num1 + num2;
    num1 = num2;
    num2 = nextNum;
}
```

### Variation 5: Even Fibonacci Numbers
```java
// Print only even Fibonacci numbers
long num1 = 0, num2 = 1;
while (num1 <= limit) {
    if (num1 % 2 == 0) {
        System.out.print(num1 + " ");
    }
    long nextNum = num1 + num2;
    num1 = num2;
    num2 = nextNum;
}
```

---

## Challenges

### Challenge 1: Optimize Recursive Fibonacci
Implement recursive Fibonacci with memoization to make it efficient.

### Challenge 2: Sum of Fibonacci
Find the sum of first n Fibonacci numbers.

### Challenge 3: Fibonacci at Position
Write a method to get the nth Fibonacci number (0-indexed).

### Challenge 4: Count Fibonacci Numbers
Count how many Fibonacci numbers are less than or equal to a given number.

### Challenge 5: Golden Ratio
Calculate the ratio of consecutive Fibonacci numbers and observe how it approaches the golden ratio (1.618...).

### Challenge 6: Fibonacci Digit Sum
For each Fibonacci number, calculate the sum of its digits and display.

---

## Common Errors and Solutions

### Error 1: Recursive Without Memoization
```java
// Very slow! Recalculates same values
public static long fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n-1) + fibonacci(n-2);  // O(2^n)!
}
```
**Solution:** Use iteration or add memoization:
```java
long a = 0, b = 1;
for (int i = 2; i <= n; i++) {
    long temp = a + b;
    a = b;
    b = temp;
}
```

### Error 2: Not Initializing Correctly
```java
long num1 = 1, num2 = 1;  // Wrong start
for (int i = 1; i <= n; i++) {
    System.out.print(num1 + " ");  // Misses 0!
}
```
**Solution:** Start with 0 and 1:
```java
long num1 = 0, num2 = 1;
```

### Error 3: Integer Overflow
```java
int num1 = 0, num2 = 1;  // Overflows for large n
```
**Solution:** Use `long` or `BigInteger`:
```java
long num1 = 0, num2 = 1;
```

### Error 4: Incorrect Variable Update
```java
// Wrong: overwrites num1 before using num2
num1 = num2;
num2 = num1 + num2;  // ERROR: num1 already changed!
```
**Solution:** Use a temporary variable:
```java
long nextNum = num1 + num2;
num1 = num2;
num2 = nextNum;
```

---

## Key Takeaways

1. **Pattern recognition** is important for sequences
2. **Variable shifting** technique for tracking progression
3. **Iteration beats recursion** for Fibonacci
4. **Memoization** can optimize recursive approaches
5. **Natural occurrence** - Fibonacci appears everywhere in nature

---

## Interesting Properties

- **Golden Ratio:** Fibonacci(n) / Fibonacci(n-1) → φ ≈ 1.618
- **Sum Formula:** Sum of first n Fibonacci = F(n+2) - 1
- **Divisibility:** If m divides n, then F(m) divides F(n)
- **GCD Property:** GCD(F(n), F(m)) = F(GCD(n, m))
- **Nature:** Found in flower petals, spiral shells, galaxies

---

## Real-World Applications

1. **Database Indexing:** Fibonacci search algorithm
2. **Network Algorithms:** Fibonacci backoff in retry logic
3. **Computer Graphics:** Fractal patterns
4. **Cryptography:** Fibonacci numbers in key generation
5. **Biology:** Population growth modeling

---

## Next Steps

1. Generate Fibonacci series with different inputs
2. Implement recursive version and compare speed
3. Try memoization approach
4. Calculate golden ratio from consecutive numbers
5. Move on to the **Prime Numbers Program**

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 20 minutes
