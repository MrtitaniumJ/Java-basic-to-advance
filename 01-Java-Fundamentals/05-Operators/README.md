# Java Operators - Performing Operations on Data

## What are Operators?

Operators are **special symbols** that tell Java to perform specific operations on variables and values. Think of them as **mathematical and logical tools** that help you manipulate data.

```
Example: 5 + 3 = 8
         ↑   ↑   ↑
    operand operator result
```

## Types of Operators

Java has several types of operators. Let's explore each one with simple examples:

## 1. Arithmetic Operators

These perform **mathematical calculations** - just like a calculator!

### Basic Arithmetic Operators

```java
public class ArithmeticExample {
    public static void main(String[] args) {
        int a = 10;
        int b = 3;
        
        // Addition
        int sum = a + b;        // Result: 13
        System.out.println("Addition: " + a + " + " + b + " = " + sum);
        
        // Subtraction  
        int difference = a - b; // Result: 7
        System.out.println("Subtraction: " + a + " - " + b + " = " + difference);
        
        // Multiplication
        int product = a * b;    // Result: 30
        System.out.println("Multiplication: " + a + " * " + b + " = " + product);
        
        // Division
        int quotient = a / b;   // Result: 3 (integer division)
        System.out.println("Division: " + a + " / " + b + " = " + quotient);
        
        // Modulus (Remainder)
        int remainder = a % b;  // Result: 1 (10 ÷ 3 = 3 remainder 1)
        System.out.println("Modulus: " + a + " % " + b + " = " + remainder);
    }
}
```

### Understanding Division Types

```java
// Integer Division (whole numbers only)
int result1 = 10 / 3;     // Result: 3 (no decimal part)

// Decimal Division  
double result2 = 10.0 / 3; // Result: 3.3333...
double result3 = (double) 10 / 3; // Result: 3.3333... (casting)
```

### Modulus Operator (%) Examples

The modulus operator gives you the **remainder** after division:

```java
System.out.println(10 % 3);  // Output: 1 (10 ÷ 3 = 3 remainder 1)
System.out.println(15 % 4);  // Output: 3 (15 ÷ 4 = 3 remainder 3)
System.out.println(20 % 5);  // Output: 0 (20 ÷ 5 = 4 remainder 0)

// Useful for checking even/odd numbers
int number = 7;
if (number % 2 == 0) {
    System.out.println(number + " is even");
} else {
    System.out.println(number + " is odd");  // This will execute
}
```

## 2. Assignment Operators

These **assign values** to variables.

### Basic Assignment
```java
int x = 5;        // Assigns 5 to x
String name = "John";  // Assigns "John" to name
```

### Compound Assignment Operators
These combine arithmetic with assignment:

```java
public class AssignmentExample {
    public static void main(String[] args) {
        int x = 10;
        
        // Add and assign
        x += 5;    // Same as: x = x + 5;  → x becomes 15
        System.out.println("After +=: " + x);
        
        // Subtract and assign  
        x -= 3;    // Same as: x = x - 3;  → x becomes 12
        System.out.println("After -=: " + x);
        
        // Multiply and assign
        x *= 2;    // Same as: x = x * 2;  → x becomes 24
        System.out.println("After *=: " + x);
        
        // Divide and assign
        x /= 4;    // Same as: x = x / 4;  → x becomes 6
        System.out.println("After /=: " + x);
        
        // Modulus and assign
        x %= 4;    // Same as: x = x % 4;  → x becomes 2
        System.out.println("After %=: " + x);
    }
}
```

## 3. Comparison (Relational) Operators

These **compare values** and return true or false:

```java
public class ComparisonExample {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;
        int c = 10;
        
        // Equal to
        boolean isEqual = (a == c);        // true
        System.out.println(a + " == " + c + " is " + isEqual);
        
        // Not equal to
        boolean notEqual = (a != b);       // true  
        System.out.println(a + " != " + b + " is " + notEqual);
        
        // Greater than
        boolean greater = (a > b);         // true
        System.out.println(a + " > " + b + " is " + greater);
        
        // Less than
        boolean less = (a < b);            // false
        System.out.println(a + " < " + b + " is " + less);
        
        // Greater than or equal to
        boolean greaterEqual = (a >= c);   // true
        System.out.println(a + " >= " + c + " is " + greaterEqual);
        
        // Less than or equal to  
        boolean lessEqual = (a <= c);      // true
        System.out.println(a + " <= " + c + " is " + lessEqual);
    }
}
```

## 4. Logical Operators

These work with **boolean values** (true/false):

### AND Operator (&&)
Returns true only if **both conditions** are true:

```java
boolean result = (5 > 3) && (10 < 15);  // true && true = true
boolean result2 = (5 > 3) && (10 > 15); // true && false = false
```

### OR Operator (||)
Returns true if **at least one condition** is true:

```java
boolean result = (5 > 3) || (10 > 15);  // true || false = true
boolean result2 = (5 < 3) || (10 > 15); // false || false = false
```

### NOT Operator (!)
**Reverses** the boolean value:

```java
boolean isTrue = true;
boolean isFalse = !isTrue;  // !true = false

boolean result = !(5 > 10); // !(false) = true
```

### Real-World Example
```java
public class LoginSystem {
    public static void main(String[] args) {
        String username = "admin";
        String password = "12345";
        int age = 20;
        boolean isActive = true;
        
        // Check if user can login
        boolean canLogin = username.equals("admin") && 
                          password.equals("12345") && 
                          isActive;
        
        // Check if user is adult or teen
        boolean canUseApp = (age >= 18) || (age >= 13 && age < 18);
        
        System.out.println("Can login: " + canLogin);
        System.out.println("Can use app: " + canUseApp);
    }
}
```

## 5. Increment and Decrement Operators

These **increase or decrease** values by 1:

### Post-increment/decrement (value used, then changed)
```java
int x = 5;
int y = x++;  // y gets 5, then x becomes 6
System.out.println("y = " + y + ", x = " + x);  // y = 5, x = 6
```

### Pre-increment/decrement (value changed, then used)
```java
int a = 5;
int b = ++a;  // a becomes 6, then b gets 6
System.out.println("b = " + b + ", a = " + a);  // b = 6, a = 6
```

### Complete Example
```java
public class IncrementExample {
    public static void main(String[] args) {
        int count = 10;
        
        System.out.println("Original count: " + count);
        
        // Post-increment
        System.out.println("Post-increment: " + count++);  // Prints 10, count becomes 11
        System.out.println("Count after post-increment: " + count);  // Prints 11
        
        // Pre-increment
        System.out.println("Pre-increment: " + ++count);   // Count becomes 12, prints 12
        
        // Post-decrement
        System.out.println("Post-decrement: " + count--);  // Prints 12, count becomes 11
        
        // Pre-decrement  
        System.out.println("Pre-decrement: " + --count);   // Count becomes 10, prints 10
    }
}
```

## 6. Conditional (Ternary) Operator

A **shortcut for if-else** statements:

### Syntax: `condition ? value_if_true : value_if_false`

```java
public class TernaryExample {
    public static void main(String[] args) {
        int age = 20;
        
        // Traditional if-else
        String status1;
        if (age >= 18) {
            status1 = "Adult";
        } else {
            status1 = "Minor";
        }
        
        // Ternary operator (same result, shorter)
        String status2 = (age >= 18) ? "Adult" : "Minor";
        
        System.out.println("Status 1: " + status1);
        System.out.println("Status 2: " + status2);
        
        // More examples
        int score = 85;
        String grade = (score >= 90) ? "A" : 
                      (score >= 80) ? "B" : 
                      (score >= 70) ? "C" : "F";
        
        System.out.println("Grade: " + grade);
        
        // Finding maximum
        int a = 15, b = 25;
        int max = (a > b) ? a : b;
        System.out.println("Maximum: " + max);
    }
}
```

## 7. Operator Precedence

When multiple operators are used, Java follows a **specific order**:

### Precedence Table (High to Low)
```
1. ()                    - Parentheses (highest)
2. ++, --, !            - Unary operators
3. *, /, %              - Multiplication, Division, Modulus
4. +, -                 - Addition, Subtraction  
5. <, <=, >, >=         - Relational operators
6. ==, !=               - Equality operators
7. &&                   - Logical AND
8. ||                   - Logical OR
9. ?:                   - Ternary operator
10. =, +=, -=, *=, /=   - Assignment operators (lowest)
```

### Examples
```java
int result1 = 5 + 3 * 2;        // Result: 11 (not 16)
                                // Because * has higher precedence than +
                                // So: 5 + (3 * 2) = 5 + 6 = 11

int result2 = (5 + 3) * 2;      // Result: 16
                                // Parentheses have highest precedence
                                // So: (8) * 2 = 16

boolean result3 = 5 > 3 && 10 < 15;  // true
                                     // > and < are evaluated first
                                     // Then: true && true = true
```

## Real-World Applications

### 1. Shopping Cart Calculator
```java
public class ShoppingCart {
    public static void main(String[] args) {
        double itemPrice = 25.99;
        int quantity = 3;
        double taxRate = 0.085;  // 8.5%
        double discountPercent = 10; // 10% discount
        
        // Calculate subtotal
        double subtotal = itemPrice * quantity;
        
        // Apply discount
        double discountAmount = subtotal * (discountPercent / 100);
        double afterDiscount = subtotal - discountAmount;
        
        // Calculate tax
        double tax = afterDiscount * taxRate;
        
        // Final total
        double total = afterDiscount + tax;
        
        System.out.println("=== Shopping Cart ===");
        System.out.println("Item Price: $" + itemPrice);
        System.out.println("Quantity: " + quantity);
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Discount: $" + discountAmount);
        System.out.println("After Discount: $" + afterDiscount);
        System.out.println("Tax: $" + tax);
        System.out.println("Final Total: $" + total);
    }
}
```

### 2. Grade Calculator
```java
public class GradeCalculator {
    public static void main(String[] args) {
        int homework = 85;
        int quiz = 92;
        int midterm = 78;
        int finalExam = 88;
        
        // Calculate weighted average
        double average = (homework * 0.2) + (quiz * 0.1) + 
                        (midterm * 0.3) + (finalExam * 0.4);
        
        // Determine letter grade
        String letterGrade = (average >= 90) ? "A" :
                           (average >= 80) ? "B" :
                           (average >= 70) ? "C" :
                           (average >= 60) ? "D" : "F";
        
        // Check if passed
        boolean passed = average >= 60;
        
        System.out.println("=== Grade Report ===");
        System.out.println("Homework (20%): " + homework);
        System.out.println("Quiz (10%): " + quiz);
        System.out.println("Midterm (30%): " + midterm);
        System.out.println("Final (40%): " + finalExam);
        System.out.println("Average: " + average);
        System.out.println("Letter Grade: " + letterGrade);
        System.out.println("Passed: " + passed);
    }
}
```

## Common Mistakes and Solutions

### 1. Integer Division
```java
// ✗ Wrong - loses decimal part
int result = 5 / 2;  // Result: 2 (not 2.5)

// ✓ Correct - use double
double result = 5.0 / 2;  // Result: 2.5
// or
double result = (double) 5 / 2;  // Result: 2.5
```

### 2. Assignment vs Equality
```java
int x = 5;

// ✗ Wrong - this assigns 10 to x
if (x = 10) {  // Compilation error in boolean context
    System.out.println("This won't work");
}

// ✓ Correct - this compares x with 10
if (x == 10) {
    System.out.println("x equals 10");
}
```

### 3. String Comparison
```java
String name1 = "John";
String name2 = "John";

// ✗ Wrong - might not work as expected
if (name1 == name2) {
    System.out.println("Names are same");
}

// ✓ Correct - use equals() for strings
if (name1.equals(name2)) {
    System.out.println("Names are same");
}
```

## Practice Exercises

### Exercise 1: Basic Calculator
```java
public class Calculator {
    public static void main(String[] args) {
        // Create a calculator that:
        // 1. Takes two numbers
        // 2. Performs all arithmetic operations
        // 3. Shows which number is larger
        // 4. Checks if numbers are equal
        
        double num1 = 15.5;
        double num2 = 4.2;
        
        // Implement all operations and comparisons
    }
}
```

### Exercise 2: Age Category
```java
public class AgeCategory {
    public static void main(String[] args) {
        int age = 25;
        
        // Use operators to determine:
        // - Child (0-12)
        // - Teen (13-17)  
        // - Adult (18-64)
        // - Senior (65+)
        
        // Use ternary operator or logical operators
    }
}
```

### Exercise 3: Salary Calculator
```java
public class SalaryCalculator {
    public static void main(String[] args) {
        double hourlyRate = 25.50;
        int hoursWorked = 45;
        
        // Calculate:
        // - Regular pay (first 40 hours)
        // - Overtime pay (hours > 40 at 1.5x rate)
        // - Gross pay
        // - Tax deduction (22%)
        // - Net pay
    }
}
```

## Quick Reference

```java
// Arithmetic
+ - * / %

// Assignment  
= += -= *= /= %=

// Comparison
== != > < >= <=

// Logical
&& || !

// Increment/Decrement
++ --

// Ternary
condition ? value1 : value2

// Precedence (remember PEMDAS-like rule)
() → ++,--,! → *,/,% → +,- → <,>,<=,>= → ==,!= → && → || → ?: → =
```

## Key Takeaways

1. **Arithmetic operators** work like basic math
2. **Assignment operators** store values in variables
3. **Comparison operators** compare values and return true/false
4. **Logical operators** work with boolean values
5. **Use parentheses** when in doubt about precedence
6. **Integer division** drops decimal parts
7. **Ternary operator** is a shortcut for simple if-else
8. **Practice** with real-world examples to master operators