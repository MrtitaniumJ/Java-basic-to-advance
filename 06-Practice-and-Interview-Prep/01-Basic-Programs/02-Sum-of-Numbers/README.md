# Sum of Numbers Program

## Problem Statement

Write a Java program that calculates the sum of the first n natural numbers (1 + 2 + 3 + ... + n).

**Input:** A positive integer n
**Output:** The sum of all numbers from 1 to n

**Example:**
- If n = 5, Output = 1 + 2 + 3 + 4 + 5 = 15
- If n = 10, Output = 1 + 2 + 3 + ... + 10 = 55

---

## Concepts Explained

### 1. **For Loop**
```java
for (int i = 1; i <= n; i++) {
    // This code runs n times
    // i increases by 1 each time
}
```
- Loops are used to repeat code multiple times
- `i = 1`: Initialize loop variable
- `i <= n`: Condition to check each iteration
- `i++`: Increment after each iteration

### 2. **Accumulation Pattern**
```java
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;  // Add current number to sum
}
```
- Start with initial value (0 for sum)
- Add to it in each iteration
- This is a fundamental pattern in programming

### 3. **Input/Output with Scanner**
```java
Scanner scanner = new Scanner(System.in);
int n = scanner.nextInt();
```
- Read integers from user input
- Always close the scanner when done

### 4. **Mathematical Formula**
```java
int sum = n * (n + 1) / 2;  // Gaussian formula
```
- Sum of first n natural numbers = n(n+1)/2
- More efficient than using loops

---

## Solution Breakdown

### Approach 1: Using Loop (Iterative)
```java
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;
}
System.out.println(sum);
```

### Approach 2: Using Formula (Mathematical)
```java
int sum = (n * (n + 1)) / 2;
System.out.println(sum);
```

### Approach 3: Using While Loop
```java
int sum = 0;
int i = 1;
while (i <= n) {
    sum += i;
    i++;
}
System.out.println(sum);
```

---

## Algorithm

**Iterative Approach:**
1. Accept input n from user
2. Validate that n is positive
3. Initialize sum = 0
4. Loop from i = 1 to n:
   - Add i to sum
5. Print the sum
6. Close scanner

**Mathematical Approach:**
1. Accept input n from user
2. Calculate sum using formula: n * (n + 1) / 2
3. Print the result

---

## Time & Space Complexity

| Approach | Time Complexity | Space Complexity | Notes |
|----------|-----------------|------------------|-------|
| Iterative | O(n) | O(1) | Linear time, constant space |
| Formula | O(1) | O(1) | Constant time, very efficient |

**Analysis:**
- **Iterative:** Loop runs n times, so time grows with input
- **Formula:** Direct calculation, always same time regardless of n
- **Space:** Both use only one variable, so O(1)

---

## Sample Input/Output

### Example 1:
```
Input:  5
Output: Sum of numbers from 1 to 5 is: 15
Calculation: 1 + 2 + 3 + 4 + 5 = 15
Using formula n*(n+1)/2: 15
```

### Example 2:
```
Input:  10
Output: Sum of numbers from 1 to 10 is: 55
Calculation: 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 = 55
Using formula n*(n+1)/2: 55
```

### Example 3:
```
Input:  100
Output: Sum of numbers from 1 to 100 is: 5050
Calculation: 1 + 2 + 3 + ... + 100 = 5050
Using formula n*(n+1)/2: 5050
```

### Example 4 (Invalid Input):
```
Input:  -5
Output: Please enter a positive number!
```

---

## Code Walkthrough

```java
// Step 1: Create Scanner object for input
Scanner scanner = new Scanner(System.in);

// Step 2: Display program title
System.out.println("=== Sum of Numbers Program ===");

// Step 3: Get input from user
System.out.print("Enter a positive number: ");
int n = scanner.nextInt();

// Step 4: Validate input
if (n <= 0) {
    System.out.println("Please enter a positive number!");
    scanner.close();
    return;
}

// Step 5: Calculate sum using loop
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;  // Accumulate: sum = sum + i
}

// Step 6: Display result
System.out.println("Sum of numbers from 1 to " + n + " is: " + sum);

// Step 7: Clean up
scanner.close();
```

---

## Variations and Extensions

### Variation 1: Sum of First n Odd Numbers
```java
// 1 + 3 + 5 + 7 + ... (first n odd numbers)
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += (2 * i - 1);
}
System.out.println("Sum of first " + n + " odd numbers: " + sum);
```

### Variation 2: Sum of First n Even Numbers
```java
// 2 + 4 + 6 + 8 + ... (first n even numbers)
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += (2 * i);
}
System.out.println("Sum of first " + n + " even numbers: " + sum);
```

### Variation 3: Sum in a Given Range
```java
// Sum from a to b
int sum = 0;
for (int i = a; i <= b; i++) {
    sum += i;
}
System.out.println("Sum from " + a + " to " + b + ": " + sum);
```

### Variation 4: Sum of Squares
```java
// 1² + 2² + 3² + ... + n²
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += (i * i);
}
System.out.println("Sum of squares: " + sum);
```

### Variation 5: Cumulative Display
```java
// Show running sum
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;
    System.out.println("Sum after " + i + ": " + sum);
}
```

---

## Challenges

### Challenge 1: Sum of Cubes
Write a program that calculates the sum of first n cubes (1³ + 2³ + 3³ + ... + n³).

### Challenge 2: Sum with Condition
Write a program that sums only numbers divisible by a given number (e.g., sum of multiples of 3).

### Challenge 3: Range Sum
Write a program that finds the sum of all numbers between two given numbers (inclusive).

### Challenge 4: Digit Sum
Write a program that takes a single number and sums its digits (e.g., for 123: 1 + 2 + 3 = 6).

### Challenge 5: Interactive Sum
Create a program that lets users enter multiple numbers and calculate the sum.

### Challenge 6: Performance Comparison
Modify the program to time both approaches (loop vs formula) and compare their speed for large values of n.

---

## Common Errors and Solutions

### Error 1: Starting from 0
```java
int sum = 0;
for (int i = 0; i <= n; i++) {  // Wrong: includes 0
    sum += i;
}
```
**Solution:** Start from 1:
```java
for (int i = 1; i <= n; i++) {
```

### Error 2: Off-by-One Error
```java
for (int i = 1; i < n; i++) {  // Wrong: misses n
    sum += i;
}
```
**Solution:** Use `<=` to include n:
```java
for (int i = 1; i <= n; i++) {
```

### Error 3: Integer Overflow
```java
int sum = n * (n + 1) / 2;  // Might overflow for very large n
```
**Solution:** Use long for large numbers:
```java
long sum = (long) n * (n + 1) / 2;
```

### Error 4: Forgetting to Initialize Sum
```java
for (int i = 1; i <= n; i++) {
    sum += i;  // Error: sum not initialized
}
```
**Solution:** Initialize before loop:
```java
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;
}
```

---

## Key Takeaways

1. **For loops** are essential for repeating operations
2. **Accumulation pattern** (sum += value) is fundamental
3. **Mathematical formulas** can optimize algorithms
4. **Input validation** prevents incorrect results
5. **Time complexity matters** - O(1) is better than O(n)

---

## Mathematical Insight

The sum of first n natural numbers is given by:
**Sum = n × (n + 1) / 2**

This formula comes from the Gaussian approach where you pair numbers:
- (1 + n) + (2 + n-1) + (3 + n-2) + ...
- Each pair sums to (n + 1)
- There are n/2 pairs
- So total = (n + 1) × n / 2

---

## Next Steps

1. Understand the loop thoroughly
2. Try all variations mentioned
3. Test with different input values
4. Compare loop vs formula approaches
5. Move on to the **Factorial Program**

---

## Real-World Applications

- Calculating totals and aggregates in data processing
- Sum of sensor readings in IoT applications
- Financial calculations (total expenses, revenue, etc.)
- Game scoring systems
- Statistical analysis and data aggregation

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 15 minutes
