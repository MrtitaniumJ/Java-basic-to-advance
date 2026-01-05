# Basic Programs - Complete Guide

## Overview

This section contains 10 fundamental Java programs designed for beginners. Each program introduces key concepts in programming logic, control flow, and problem-solving. These programs form the foundation for understanding more complex algorithms and data structures.

## Program Difficulty Progression

```
Level 1: HelloWorld, Even/Odd, Largest Number
         ↓
Level 2: Sum of Numbers, Reverse String, Simple Calculator
         ↓
Level 3: Factorial, Fibonacci, Prime Numbers, Palindrome
```

## List of Programs

### 1. **HelloWorld** ⭐
- **Concept:** Program structure, main method, output
- **Difficulty:** Beginner
- **Time to Complete:** 5 minutes
- **Key Learning:** Java program structure, System.out.println()

### 2. **Even/Odd Checker** ⭐
- **Concept:** Conditional statements, modulo operator
- **Difficulty:** Beginner
- **Time to Complete:** 10 minutes
- **Key Learning:** if-else statements, logical operators

### 3. **Largest Number** ⭐
- **Concept:** Comparing numbers, conditional logic
- **Difficulty:** Beginner
- **Time to Complete:** 10 minutes
- **Key Learning:** Multiple comparisons, nested if statements

### 4. **Sum of Numbers** ⭐⭐
- **Concept:** Loops, accumulation, integer operations
- **Difficulty:** Beginner
- **Time to Complete:** 15 minutes
- **Key Learning:** for loops, variables, mathematical operations

### 5. **Simple Calculator** ⭐⭐
- **Concept:** Arithmetic operations, conditionals, method creation
- **Difficulty:** Beginner
- **Time to Complete:** 20 minutes
- **Key Learning:** Switch statements, basic methods, user interaction

### 6. **Reverse String** ⭐⭐
- **Concept:** String manipulation, loops, string operations
- **Difficulty:** Beginner
- **Time to Complete:** 15 minutes
- **Key Learning:** String class methods, loops, character handling

### 7. **Factorial** ⭐⭐
- **Concept:** Loops, mathematical functions, accumulation
- **Difficulty:** Beginner
- **Time to Complete:** 15 minutes
- **Key Learning:** Nested loops, variable scope, factorial algorithm

### 8. **Fibonacci Series** ⭐⭐
- **Concept:** Loops, sequence generation, mathematical patterns
- **Difficulty:** Beginner
- **Time to Complete:** 20 minutes
- **Key Learning:** Pattern recognition, while loops, number sequences

### 9. **Prime Numbers** ⭐⭐
- **Concept:** Loops, conditionals, optimization
- **Difficulty:** Beginner
- **Time to Complete:** 20 minutes
- **Key Learning:** Nested loops, break statements, algorithm optimization

### 10. **Palindrome Checker** ⭐⭐
- **Concept:** String manipulation, comparisons, logical thinking
- **Difficulty:** Beginner
- **Time to Complete:** 20 minutes
- **Key Learning:** String reversal, character comparison, logic

---

## Learning Path Recommendation

### Day 1-2: Basic Structure (Programs 1-3)
- Focus on Java syntax and program structure
- Understand how to read input and produce output
- Master conditional statements

### Day 3-4: Loops and Iteration (Programs 4-6)
- Master different types of loops (for, while, do-while)
- Understand loop control (break, continue)
- Practice accumulation patterns

### Day 5-7: Algorithms (Programs 7-10)
- Learn classic algorithms (factorial, Fibonacci, prime checking)
- Recognize algorithmic patterns
- Optimize basic algorithms

---

## Key Concepts Covered

### Variables and Data Types
```java
int number = 5;
String text = "Hello";
double value = 3.14;
boolean flag = true;
```

### Control Flow - If/Else
```java
if (condition) {
    // execute if true
} else if (other_condition) {
    // execute if other condition true
} else {
    // execute otherwise
}
```

### Control Flow - Loops
```java
// for loop
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}

// while loop
while (condition) {
    // execute while true
}

// do-while loop
do {
    // execute at least once
} while (condition);
```

### String Operations
```java
String str = "Hello";
int length = str.length();
char ch = str.charAt(0);
String substring = str.substring(0, 3);
String reversed = new StringBuilder(str).reverse().toString();
```

### Methods
```java
public static int add(int a, int b) {
    return a + b;
}
```

---

## Common Patterns in Basic Programs

### 1. **Input/Output Pattern**
```java
Scanner scanner = new Scanner(System.in);
int number = scanner.nextInt();
System.out.println(result);
```

### 2. **Loop and Accumulate Pattern**
```java
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;
}
```

### 3. **Validate Input Pattern**
```java
if (number < 0) {
    System.out.println("Invalid input");
    return;
}
```

### 4. **Compare and Select Pattern**
```java
int max = a > b ? a : b;
```

---

## Complexity Analysis Quick Reference

| Program | Time Complexity | Space Complexity | Notes |
|---------|-----------------|------------------|-------|
| HelloWorld | O(1) | O(1) | Constant output |
| Even/Odd | O(1) | O(1) | Single operation |
| Largest Number | O(1) or O(n) | O(1) | Depends on input |
| Sum of Numbers | O(n) | O(1) | Single loop |
| Simple Calculator | O(1) | O(1) | Fixed operations |
| Reverse String | O(n) | O(n) | Creates new string |
| Factorial | O(n) | O(1) | Single loop |
| Fibonacci | O(n) | O(1) | Single loop |
| Prime Numbers | O(√n) | O(1) | Optimization possible |
| Palindrome | O(n) | O(n) | String reversal |

---

## Tips for Success

### 1. **Start Simple**
- Don't try to write perfect code immediately
- Focus on getting it working first
- Optimize and refactor later

### 2. **Understand, Don't Memorize**
- Understand why each line exists
- Know what each function does
- Be able to explain the logic

### 3. **Test Thoroughly**
- Test with normal inputs
- Test with edge cases (0, 1, negative, large numbers)
- Test with invalid inputs

### 4. **Practice Variations**
- Modify programs slightly
- Solve similar problems
- Try different approaches

### 5. **Read Others' Code**
- See how experienced programmers solve problems
- Learn new techniques and patterns
- Understand best practices

---

## Common Mistakes to Avoid

1. **Off-by-One Errors in Loops**
   ```java
   // Wrong: will miss last element
   for (int i = 0; i < array.length - 1; i++)
   
   // Correct:
   for (int i = 0; i < array.length; i++)
   ```

2. **Forgetting Semicolons**
   ```java
   System.out.println("Hello") // Missing semicolon
   ```

3. **Case Sensitivity**
   ```java
   // Wrong: Java is case-sensitive
   int Number = 5;
   System.out.println(number); // Error: cannot find symbol
   ```

4. **Infinite Loops**
   ```java
   // Wrong: i never increases
   while (i < 10) {
       System.out.println(i);
   }
   ```

5. **Not Initializing Variables**
   ```java
   int sum;
   sum += 5; // Error: variable might not be initialized
   ```

---

## Practice Routine (Daily)

### 30-Minute Session
1. Pick one program (5 min)
2. Write code from scratch without reference (15 min)
3. Test with various inputs (5 min)
4. Review and optimize (5 min)

### 60-Minute Session
1. Solve two new programs or variations (40 min)
2. Review previous day's programs (10 min)
3. Optimize one program (10 min)

### Weekly Review
- Solve all 10 programs again from memory (Weekly)
- Time yourself (aim to reduce time)
- Focus on code quality and readability
- Identify patterns and shortcuts

---

## Progression Checklist

- [ ] **HelloWorld:** Can create and run basic Java program
- [ ] **Even/Odd:** Understand if-else statements
- [ ] **Largest Number:** Compare multiple values
- [ ] **Sum of Numbers:** Use for loops effectively
- [ ] **Simple Calculator:** Implement multiple operations
- [ ] **Reverse String:** Manipulate strings
- [ ] **Factorial:** Nested loops and accumulation
- [ ] **Fibonacci:** Pattern recognition and sequences
- [ ] **Prime Numbers:** Optimization and nested loops
- [ ] **Palindrome:** String comparison logic

---

## Advanced Variations to Try

Once you've completed a program, try these variations:

### HelloWorld
- [ ] Print in a specific format (rectangle of stars)
- [ ] Print multiple lines with different messages
- [ ] Use variables to customize output

### Even/Odd
- [ ] Check multiple numbers
- [ ] Count even and odd numbers in a range
- [ ] Calculate sum of even/odd numbers

### Largest Number
- [ ] Find minimum number
- [ ] Find median of numbers
- [ ] Find second largest number

### Sum of Numbers
- [ ] Calculate sum of first n odd numbers
- [ ] Calculate sum of squares
- [ ] Calculate sum of cubes

### Simple Calculator
- [ ] Add power operation
- [ ] Add modulo operation
- [ ] Handle division by zero

### Reverse String
- [ ] Check if string is palindrome during reversal
- [ ] Reverse only vowels or consonants
- [ ] Reverse words in a sentence

### Factorial
- [ ] Use recursion instead of loop
- [ ] Calculate factorial with very large numbers
- [ ] Find factorial of negative number (handle error)

### Fibonacci
- [ ] Find nth Fibonacci number without loop (recursion)
- [ ] Find all Fibonacci numbers less than given number
- [ ] Find sum of Fibonacci series

### Prime Numbers
- [ ] Find all prime numbers up to n
- [ ] Check if number is prime (optimized)
- [ ] Find next prime number

### Palindrome
- [ ] Check with spaces and punctuation ignored
- [ ] Find longest palindrome in string
- [ ] Convert number to check if palindrome

---

## Performance Tips

1. **Use Appropriate Data Types**
   - Use `int` for small numbers, `long` for large
   - Use `String` for text, `char` for single character

2. **Avoid Unnecessary Operations**
   - Don't create new objects in loops if not needed
   - Cache calculations outside loops

3. **Use Efficient Algorithms**
   - Early termination with `break`
   - Mathematical optimization (e.g., check only up to √n for primes)

---

## Next Steps

1. Complete all 10 basic programs
2. Time yourself to see improvement
3. Solve them again without looking at code
4. Understand the underlying algorithms
5. Move to **Intermediate Programs**

---

**Happy Coding! Start with Program 1 and progress through the list. Remember: consistency is key to mastery.**
