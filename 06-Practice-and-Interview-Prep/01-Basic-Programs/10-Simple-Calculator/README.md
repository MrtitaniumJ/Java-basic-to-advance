# Simple Calculator Program

## Problem Statement

Write a Java program that performs basic arithmetic operations on two numbers.

**Operations Supported:**
- Addition (+)
- Subtraction (-)
- Multiplication (*)
- Division (/)
- Modulus (%)

**Input:** Two numbers and an operator
**Output:** Result of the operation

**Examples:**
- 10 + 5 = 15
- 20 - 8 = 12
- 6 * 7 = 42
- 15 / 3 = 5
- 17 % 5 = 2

---

## Concepts Explained

### 1. **Switch Statement**
```java
switch (operator) {
    case '+':
        result = a + b;
        break;
    case '-':
        result = a - b;
        break;
    default:
        System.out.println("Invalid operator");
}
```
- Alternative to multiple if-else statements
- More readable for multiple cases
- `break` prevents fall-through
- `default` handles invalid cases

### 2. **Arithmetic Operators**
```java
int sum = a + b;        // Addition
int diff = a - b;       // Subtraction
int product = a * b;    // Multiplication
double quotient = a / b;// Division
int remainder = a % b;  // Modulus (remainder)
```
- Basic mathematical operations
- Division by zero must be handled
- Order of operations matters

### 3. **Method Creation and Reusability**
```java
public static double add(double a, double b) {
    return a + b;
}

public static double multiply(double a, double b) {
    return a * b;
}
```
- Each operation as separate method
- Improves code organization
- Easy to test and reuse
- Follows single responsibility principle

### 4. **Exception Handling**
```java
try {
    double result = calculate(num1, num2, operator);
    System.out.println("Result: " + result);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("Invalid input");
}
```
- Catches division by zero
- Handles invalid operators
- Prevents program crash

### 5. **String Formatting**
```java
System.out.printf("%.2f %c %.2f = %.2f%n", num1, operator, num2, result);
// Formats output with 2 decimal places
```
- `%.2f` - floating point with 2 decimals
- `%c` - character
- `%n` - newline

---

## Solution Breakdown

### Approach 1: Simple Switch Statement
```java
double result;
switch (operator) {
    case '+':
        result = num1 + num2;
        break;
    case '-':
        result = num1 - num2;
        break;
    // ... other cases
}
System.out.println(result);
```

### Approach 2: Separate Methods (Better)
```java
public static double add(double a, double b) { return a + b; }
public static double subtract(double a, double b) { return a - b; }

double result = (operator == '+') ? add(num1, num2) : subtract(num1, num2);
```

### Approach 3: Unified Calculate Method
```java
public static double calculate(double a, double b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        // ...
        default: throw new IllegalArgumentException("Invalid");
    }
}
```

---

## Algorithm

1. Accept first number from user
2. Accept operator from user
3. Accept second number from user
4. Validate operator (must be +, -, *, /, %)
5. Validate divisor (if /, % → not zero)
6. Perform calculation based on operator
7. Display result
8. Ask if user wants to calculate again
9. Loop back to step 1 if yes, otherwise exit

---

## Time & Space Complexity

| Aspect | Complexity |
|--------|-----------|
| Time Complexity | O(1) - Single operation |
| Space Complexity | O(1) - Fixed number of variables |

**Analysis:**
- Each arithmetic operation is O(1)
- No loops or recursion involved
- Exception handling adds minimal overhead

---

## Sample Input/Output

### Example 1: Addition
```
Input:  num1 = 15, operator = +, num2 = 8
Output: 15.00 + 8.00 = 23.00
```

### Example 2: Division
```
Input:  num1 = 20, operator = /, num2 = 4
Output: 20.00 / 4.00 = 5.00
```

### Example 3: Modulus
```
Input:  num1 = 17, operator = %, num2 = 5
Output: 17.00 % 5.00 = 2.00
```

### Example 4: Error Handling
```
Input:  num1 = 10, operator = /, num2 = 0
Output: Error: Cannot divide by zero!
```

### Example 5: Invalid Operator
```
Input:  num1 = 5, operator = &, num2 = 3
Output: Error: Invalid operator: &
```

### Example 6: Multiple Operations
```
Iteration 1: 10 + 5 = 15.00
Iteration 2: 12 * 4 = 48.00
Iteration 3: 30 / 6 = 5.00
```

---

## Code Walkthrough

```java
// Step 1: Get inputs
System.out.print("Enter first number: ");
double num1 = scanner.nextDouble();  // 15

System.out.print("Enter operator: ");
char operator = scanner.next().charAt(0);  // '+'

System.out.print("Enter second number: ");
double num2 = scanner.nextDouble();  // 8

// Step 2: Perform calculation using switch
double result;
switch (operator) {
    case '+':
        result = add(num1, num2);  // 15 + 8 = 23
        break;
    case '-':
        result = subtract(num1, num2);
        break;
    case '*':
        result = multiply(num1, num2);
        break;
    case '/':
        result = divide(num1, num2);  // Checks for zero
        break;
    case '%':
        result = modulus(num1, num2);  // Checks for zero
        break;
    default:
        throw new IllegalArgumentException("Invalid operator");
}

// Step 3: Display result
System.out.printf("%.2f %c %.2f = %.2f%n", num1, operator, num2, result);
// Output: 15.00 + 8.00 = 23.00
```

---

## Variations and Extensions

### Variation 1: Advanced Operations
```java
case '^':  // Power
    return Math.pow(a, b);
case 's':  // Square root
    return Math.sqrt(a);
```

### Variation 2: History of Calculations
```java
List<String> history = new ArrayList<>();
history.add(num1 + " " + operator + " " + num2 + " = " + result);
// Later, display all calculations
```

### Variation 3: Decimal Precision Selection
```java
System.out.print("Decimal places (0-10): ");
int precision = scanner.nextInt();
System.out.printf("%." + precision + "f%n", result);
```

### Variation 4: Expression Evaluation
```java
// Handle complex expressions: 2 + 3 * 4
// Would require parsing and order of operations
```

### Variation 5: Memory Function
```java
double memory = 0;
case 'm':  // Memory store
    memory = result;
    break;
case 'r':  // Memory recall
    return memory;
```

### Variation 6: Temperature Converter
```java
// Extend calculator to include unit conversion
case 'f':  // Celsius to Fahrenheit
    return (num1 * 9/5) + 32;
```

---

## Challenges

### Challenge 1: Multi-Step Calculation
Create a calculator that chains operations: (5 + 3) * 2

### Challenge 2: Expression Parser
Parse and evaluate mathematical expressions: "10 + 5 * 2"

### Challenge 3: Calculation History
Keep and display history of all calculations performed.

### Challenge 4: Advanced Operations
Add power (^), square root, factorial operations.

### Challenge 5: Programmer Calculator
Add bitwise operations (AND, OR, XOR).

### Challenge 6: Unit Converter
Extend calculator to convert between units (temperature, distance, etc.).

---

## Common Errors and Solutions

### Error 1: Missing Break in Switch
```java
switch (operator) {
    case '+':
        result = a + b;
        // Missing break - falls through!
    case '-':
        result = a - b;
        break;
}
```
**Solution:** Add break statement:
```java
case '+':
    result = a + b;
    break;  // Prevents fall-through
```

### Error 2: Division by Zero Not Handled
```java
double result = a / b;  // What if b = 0?
System.out.println(result);  // Infinity or NaN
```
**Solution:** Check before division:
```java
if (b == 0) {
    throw new IllegalArgumentException("Cannot divide by zero!");
}
return a / b;
```

### Error 3: Integer Division Returning Integer
```java
int result = 10 / 3;  // Result: 3, not 3.333...
```
**Solution:** Use double:
```java
double result = 10.0 / 3;  // Result: 3.333...
```

### Error 4: Not Handling Invalid Input
```java
double num = scanner.nextDouble();  // What if user enters "abc"?
// Will crash with InputMismatchException
```
**Solution:** Wrap in try-catch:
```java
try {
    double num = scanner.nextDouble();
} catch (InputMismatchException e) {
    System.out.println("Please enter a valid number");
    scanner.nextLine();  // Clear buffer
}
```

### Error 5: Switch with String (Risky)
```java
switch (operator) {  // operator is char
    case "+":  // Wrong: comparing char to String
        // ...
}
```
**Solution:** Use correct type:
```java
switch (operator) {  // operator is char
    case '+':  // Correct: comparing char to char
        // ...
}
```

---

## Key Takeaways

1. **Switch statements** excellent for multiple cases
2. **Separate methods** improves code organization
3. **Exception handling** prevents crashes
4. **Input validation** is critical
5. **Division by zero** must always be checked

---

## Real-World Applications

1. **Accounting Software:** Financial calculations
2. **Scientific Applications:** Complex calculations
3. **Trading Platforms:** Price calculations
4. **Mobile Apps:** Unit converters
5. **Game Development:** Score calculations

---

## Performance Considerations

- **O(1) operations** - all arithmetic is constant time
- **Exception handling** adds negligible overhead
- **Input validation** more important than optimization
- **Readability** prioritized over micro-optimization

---

## Best Practices Demonstrated

1. **Separate concerns** - each operation in own method
2. **Validate input** - check for errors before operations
3. **Handle exceptions** - gracefully deal with errors
4. **Clear output** - formatted and easy to read
5. **User-friendly** - loop for multiple calculations

---

## Next Steps

1. Implement and test basic calculator
2. Add error handling for division by zero
3. Test with various inputs
4. Try advanced operations challenges
5. Build a more complex expression evaluator

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 20 minutes
