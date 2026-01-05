# Operators in Java - The Action Performers

## Simple Explanation

Think of **operators** as **action words** or **tools** that perform operations on data:
- **+** is like a **calculator** that adds numbers
- **>** is like a **judge** that compares values
- **=** is like a **mover** that puts values into boxes (variables)
- **&&** is like **AND gate** - both conditions must be true

### Real-World Analogy
Operators are like **kitchen tools**:
- **Knife (/)** = Cuts/divides things
- **Mixer (+)** = Combines ingredients  
- **Scale (>)** = Compares weights
- **Timer (==)** = Checks if time matches

## Professional Definition

**Operators** are special symbols that perform specific operations on one, two, or three operands, and then return a result. They are the building blocks for creating expressions and performing calculations in Java programs.

## Types of Operators

### Overview:
```
Java Operators
├── Arithmetic Operators (+, -, *, /, %)
├── Assignment Operators (=, +=, -=, etc.)
├── Comparison/Relational Operators (==, !=, >, <, etc.)
├── Logical Operators (&&, ||, !)
├── Unary Operators (++, --, !, -)
├── Bitwise Operators (&, |, ^, ~, <<, >>)
├── Ternary Operator (? :)
└── instanceof Operator
```

## 1. Arithmetic Operators

### Simple Explanation
These are like **math operations** you learned in school - add, subtract, multiply, divide.

### Addition (+)
```java
int a = 10;
int b = 5;
int sum = a + b;        // sum = 15

// String concatenation
String firstName = "John";
String lastName = "Doe";
String fullName = firstName + " " + lastName;  // "John Doe"

// Mixed types
String message = "Age: " + 25;  // "Age: 25"
```

### Subtraction (-)
```java
int total = 100;
int used = 30;
int remaining = total - used;   // remaining = 70

double price = 99.99;
double discount = 10.50;
double finalPrice = price - discount;  // finalPrice = 89.49
```

### Multiplication (*)
```java
int length = 10;
int width = 5;
int area = length * width;      // area = 50

double rate = 15.5;
int hours = 8;
double salary = rate * hours;   // salary = 124.0
```

### Division (/)
```java
// Integer division
int total = 20;
int groups = 4;
int perGroup = total / groups;  // perGroup = 5

// Floating point division
double totalMoney = 100.0;
int people = 3;
double perPerson = totalMoney / people;  // perPerson = 33.333...

// Watch out for integer division!
int result1 = 10 / 3;          // result1 = 3 (not 3.33)
double result2 = 10.0 / 3;     // result2 = 3.333...
```

### Modulus/Remainder (%)
```java
// Find remainder after division
int dividend = 17;
int divisor = 5;
int remainder = dividend % divisor;     // remainder = 2
// Because 17 ÷ 5 = 3 with remainder 2

// Common uses:
// Check if number is even or odd
int number = 8;
if (number % 2 == 0) {
    System.out.println("Even");        // This will print
} else {
    System.out.println("Odd");
}

// Check if divisible
int year = 2024;
if (year % 4 == 0) {
    System.out.println("Leap year candidate");
}
```

## 2. Assignment Operators

### Simple Assignment (=)
```java
// Think of = as "putting value into variable"
int age = 25;           // Put 25 into age box
String name = "Alice";  // Put "Alice" into name box
boolean active = true;  // Put true into active box
```

### Compound Assignment Operators
```java
int score = 100;

// These are shortcuts:
score += 10;    // Same as: score = score + 10;    → score = 110
score -= 5;     // Same as: score = score - 5;     → score = 105
score *= 2;     // Same as: score = score * 2;     → score = 210
score /= 3;     // Same as: score = score / 3;     → score = 70
score %= 7;     // Same as: score = score % 7;     → score = 0

// String concatenation assignment
String message = "Hello";
message += " World";    // Same as: message = message + " World";
                       // message = "Hello World"
```

## 3. Comparison/Relational Operators

### Simple Explanation
These are like **comparison tools** that answer yes/no questions about values.

### Equal to (==)
```java
int a = 10;
int b = 10;
boolean isEqual = (a == b);     // true

String name1 = "Alice";
String name2 = "Alice";
// For Strings, use .equals() method
boolean sameNames = name1.equals(name2);  // true
// Don't use == for Strings!
```

### Not equal to (!=)
```java
int x = 5;
int y = 8;
boolean isDifferent = (x != y);  // true

char grade1 = 'A';
char grade2 = 'B';
boolean differentGrades = (grade1 != grade2);  // true
```

### Greater than (>)
```java
int myScore = 85;
int passingScore = 60;
boolean passed = (myScore > passingScore);  // true

double price1 = 99.99;
double price2 = 89.99;
boolean isExpensive = (price1 > price2);   // true
```

### Less than (<)
```java
int age = 17;
int votingAge = 18;
boolean cannotVote = (age < votingAge);    // true
```

### Greater than or equal to (>=)
```java
int marks = 75;
int requiredMarks = 75;
boolean qualified = (marks >= requiredMarks);  // true
```

### Less than or equal to (<=)
```java
int speed = 60;
int speedLimit = 65;
boolean withinLimit = (speed <= speedLimit);  // true
```

## 4. Logical Operators

### Simple Explanation
These are like **decision makers** that combine multiple conditions.

### Logical AND (&&)
```java
// Both conditions must be true
int age = 25;
boolean hasLicense = true;
boolean canDrive = (age >= 18) && hasLicense;  // true

// Real-world example: Bank loan approval
int salary = 50000;
int creditScore = 750;
boolean approvedForLoan = (salary > 30000) && (creditScore > 700);  // true
```

### Logical OR (||)
```java
// At least one condition must be true
char grade = 'B';
boolean goodGrade = (grade == 'A') || (grade == 'B');  // true

// Real-world example: Discount eligibility
boolean isStudent = false;
boolean isSenior = true;
boolean getDiscount = isStudent || isSenior;  // true
```

### Logical NOT (!)
```java
// Reverses the boolean value
boolean isRaining = false;
boolean isSunny = !isRaining;  // true

// Real-world example: Access control
boolean isLoggedIn = true;
boolean needsLogin = !isLoggedIn;  // false
```

### Combining Logical Operators
```java
// Complex conditions
int age = 22;
boolean hasJob = true;
boolean isStudent = false;

boolean canGetLoan = (age >= 18) && (hasJob || isStudent);
// age >= 18: true
// hasJob || isStudent: true || false = true
// Final result: true && true = true
```

## 5. Unary Operators

### Simple Explanation
These operators work on **only one operand** (one value).

### Increment (++)
```java
int count = 5;

// Pre-increment: increment first, then use value
int a = ++count;    // count becomes 6, then a = 6

// Post-increment: use value first, then increment  
int b = count++;    // b = 6, then count becomes 7

System.out.println("count: " + count);  // count: 7
System.out.println("a: " + a);          // a: 6
System.out.println("b: " + b);          // b: 6
```

### Decrement (--)
```java
int lives = 3;

// Pre-decrement
int remaining = --lives;    // lives becomes 2, then remaining = 2

// Post-decrement
int current = lives--;      // current = 2, then lives becomes 1

System.out.println("lives: " + lives);      // lives: 1
System.out.println("remaining: " + remaining); // remaining: 2
System.out.println("current: " + current);     // current: 2
```

### Unary Plus and Minus
```java
int positive = +5;      // positive = 5
int negative = -5;      // negative = -5

int x = 10;
int opposite = -x;      // opposite = -10
```

### Logical NOT (!)
```java
boolean isComplete = false;
boolean isIncomplete = !isComplete;  // isIncomplete = true
```

## 6. Ternary Operator (? :)

### Simple Explanation
This is like a **shortcut for if-else**. It asks a question and chooses between two answers.

### Syntax:
```java
condition ? valueIfTrue : valueIfFalse
```

### Examples:
```java
// Instead of if-else:
int age = 20;
String status;
if (age >= 18) {
    status = "Adult";
} else {
    status = "Minor";
}

// Use ternary operator:
String status = (age >= 18) ? "Adult" : "Minor";

// More examples:
int a = 10, b = 20;
int max = (a > b) ? a : b;              // max = 20

boolean hasTicket = true;
String access = hasTicket ? "Enter" : "Buy ticket first";

// Nested ternary (use sparingly)
int score = 85;
char grade = (score >= 90) ? 'A' : (score >= 80) ? 'B' : 'C';  // grade = 'B'
```

## 7. Operator Precedence and Associativity

### Simple Explanation
**Precedence** is like **order of operations** in math - multiplication before addition.
**Associativity** is the **direction** of evaluation when operators have same precedence.

### Precedence Table (High to Low):
```
1. () [] .                          (Highest precedence)
2. ++ -- ! - + (unary)
3. * / %
4. + -
5. < <= > >= instanceof  
6. == !=
7. &&
8. ||
9. ? :
10. = += -= *= /= %=               (Lowest precedence)
```

### Examples:
```java
// Without parentheses
int result = 2 + 3 * 4;         // result = 14 (not 20)
// Because: 2 + (3 * 4) = 2 + 12 = 14

// With parentheses to change order
int result2 = (2 + 3) * 4;      // result2 = 20
// Because: (2 + 3) * 4 = 5 * 4 = 20

// Complex example
int a = 10, b = 5, c = 2;
int result3 = a + b * c;        // result3 = 20
// Because: a + (b * c) = 10 + (5 * 2) = 10 + 10 = 20

// Logical operators
boolean result4 = true || false && false;  // result4 = true
// Because: true || (false && false) = true || false = true
```

## Interview Questions & Answers

**Q1: What's the difference between = and ==?**
**A:**
- `=` is **assignment operator** - assigns value to variable
- `==` is **comparison operator** - checks if two values are equal

**Q2: What's the difference between ++i and i++?**
**A:**
- `++i` (pre-increment): increments i first, then returns new value
- `i++` (post-increment): returns current value of i, then increments

**Q3: What happens with integer division in Java?**
**A:** Integer division truncates decimal part. Example: `7/3 = 2` (not 2.33). Use `7.0/3` or `(double)7/3` to get decimal result.

**Q4: What's the difference between & and &&?**
**A:**
- `&&` is **logical AND** - if first condition is false, doesn't check second (short-circuit)
- `&` is **bitwise AND** - always evaluates both conditions

**Q5: What is operator precedence?**
**A:** Order in which operators are evaluated. Multiplication has higher precedence than addition, so `2 + 3 * 4 = 14`, not `20`.

## Complete Example Program

```java
/**
 * Comprehensive demonstration of Java operators
 */
public class OperatorsExample {
    
    public static void main(String[] args) {
        // Demonstrate different types of operators
        demonstrateArithmeticOperators();
        demonstrateComparisonOperators();
        demonstrateLogicalOperators();
        demonstrateAssignmentOperators();
        demonstrateTernaryOperator();
        demonstrateOperatorPrecedence();
    }
    
    public static void demonstrateArithmeticOperators() {
        System.out.println("=== Arithmetic Operators ===");
        
        int a = 15, b = 4;
        
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Addition (a + b): " + (a + b));        // 19
        System.out.println("Subtraction (a - b): " + (a - b));     // 11
        System.out.println("Multiplication (a * b): " + (a * b));  // 60
        System.out.println("Division (a / b): " + (a / b));        // 3
        System.out.println("Modulus (a % b): " + (a % b));         // 3
        System.out.println();
    }
    
    public static void demonstrateComparisonOperators() {
        System.out.println("=== Comparison Operators ===");
        
        int score1 = 85, score2 = 92;
        
        System.out.println("score1 = " + score1 + ", score2 = " + score2);
        System.out.println("score1 == score2: " + (score1 == score2));  // false
        System.out.println("score1 != score2: " + (score1 != score2));  // true
        System.out.println("score1 > score2: " + (score1 > score2));    // false
        System.out.println("score1 < score2: " + (score1 < score2));    // true
        System.out.println("score1 >= 80: " + (score1 >= 80));          // true
        System.out.println("score2 <= 100: " + (score2 <= 100));        // true
        System.out.println();
    }
    
    public static void demonstrateLogicalOperators() {
        System.out.println("=== Logical Operators ===");
        
        boolean hasLicense = true;
        int age = 22;
        boolean hasInsurance = false;
        
        System.out.println("hasLicense: " + hasLicense);
        System.out.println("age: " + age);
        System.out.println("hasInsurance: " + hasInsurance);
        
        boolean canDrive = hasLicense && (age >= 18);
        boolean needsInsurance = hasLicense && !hasInsurance;
        boolean canRent = hasLicense || (age >= 25);
        
        System.out.println("Can drive: " + canDrive);               // true
        System.out.println("Needs insurance: " + needsInsurance);   // true  
        System.out.println("Can rent: " + canRent);                 // true
        System.out.println();
    }
    
    public static void demonstrateAssignmentOperators() {
        System.out.println("=== Assignment Operators ===");
        
        int value = 100;
        System.out.println("Initial value: " + value);
        
        value += 25;    // value = value + 25
        System.out.println("After += 25: " + value);       // 125
        
        value -= 15;    // value = value - 15
        System.out.println("After -= 15: " + value);       // 110
        
        value *= 2;     // value = value * 2
        System.out.println("After *= 2: " + value);        // 220
        
        value /= 4;     // value = value / 4
        System.out.println("After /= 4: " + value);        // 55
        
        value %= 10;    // value = value % 10
        System.out.println("After %= 10: " + value);       // 5
        System.out.println();
    }
    
    public static void demonstrateTernaryOperator() {
        System.out.println("=== Ternary Operator ===");
        
        int score = 87;
        String result = (score >= 60) ? "Pass" : "Fail";
        System.out.println("Score " + score + ": " + result);       // Pass
        
        int a = 15, b = 25;
        int larger = (a > b) ? a : b;
        System.out.println("Larger of " + a + " and " + b + ": " + larger); // 25
        
        boolean isEven = (score % 2 == 0) ? true : false;
        System.out.println("Is " + score + " even? " + isEven);     // false
        System.out.println();
    }
    
    public static void demonstrateOperatorPrecedence() {
        System.out.println("=== Operator Precedence ===");
        
        // Demonstrating precedence
        int result1 = 2 + 3 * 4;
        System.out.println("2 + 3 * 4 = " + result1);              // 14 (not 20)
        
        int result2 = (2 + 3) * 4;  
        System.out.println("(2 + 3) * 4 = " + result2);            // 20
        
        // Complex expression
        int a = 10, b = 5, c = 2;
        int result3 = a + b * c - 3;
        System.out.println("10 + 5 * 2 - 3 = " + result3);         // 17
        // Calculation: 10 + (5 * 2) - 3 = 10 + 10 - 3 = 17
        
        // Logical operators precedence
        boolean result4 = true || false && false;
        System.out.println("true || false && false = " + result4);  // true
        // Calculation: true || (false && false) = true || false = true
        System.out.println();
    }
}
```

## Real-World Applications

### 1. Calculator Logic
```java
public static double calculate(double num1, double num2, char operator) {
    switch (operator) {
        case '+': return num1 + num2;
        case '-': return num1 - num2;
        case '*': return num1 * num2;
        case '/': return (num2 != 0) ? num1 / num2 : 0;
        default: return 0;
    }
}
```

### 2. Grade Calculator
```java
public static char calculateGrade(int score) {
    return (score >= 90) ? 'A' : 
           (score >= 80) ? 'B' : 
           (score >= 70) ? 'C' : 
           (score >= 60) ? 'D' : 'F';
}
```

### 3. Validation Logic
```java
public static boolean isValidAge(int age) {
    return (age >= 0) && (age <= 150);
}

public static boolean canVote(int age, boolean isCitizen) {
    return (age >= 18) && isCitizen;
}
```

## Key Takeaways

1. **Arithmetic operators** perform math operations (+, -, *, /, %)
2. **Assignment operators** store values (=, +=, -=, etc.)
3. **Comparison operators** compare values (==, !=, >, <, etc.)
4. **Logical operators** combine conditions (&&, ||, !)
5. **Ternary operator** is shorthand for if-else (? :)
6. **Operator precedence** determines evaluation order
7. **Use parentheses** to make precedence clear
8. **++ and --** have pre and post forms with different behavior

---

*Remember: Operators are the tools that make your data come alive - choose the right operator for the right job!*
