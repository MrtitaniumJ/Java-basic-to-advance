# Java Basic Syntax - Your First Steps

## Simple Explanation

Think of Java syntax like **grammar rules for a language**. Just like English has rules (capitalize first letter, end with period), Java has rules for writing code that the computer can understand.

### Real-World Analogy
Writing Java code is like **writing a recipe**:
- You need **ingredients** (variables)
- You need **steps** (statements)
- You need **proper format** (syntax)
- You need to **follow rules** (Java syntax rules)

## Professional Definition

**Java Syntax** refers to the set of rules that define how a Java program should be written and formatted. It includes rules for naming, structuring code, using keywords, and organizing statements.

## Basic Structure of a Java Program

### Template:
```java
// This is a comment
public class ClassName {
    // Main method - where program starts
    public static void main(String[] args) {
        // Your code goes here
        System.out.println("Hello World!");
    }
}
```

### Breaking Down Each Part:

#### 1. Comments
```java
// Single line comment
/* Multi-line comment
   Can span multiple lines */
/**
 * Documentation comment
 * Used for generating documentation
 */
```

#### 2. Class Declaration
```java
public class HelloWorld {
    // class body
}
```

#### 3. Main Method
```java
public static void main(String[] args) {
    // program execution starts here
}
```

## Java Syntax Rules (Must Remember!)

### 1. Case Sensitivity
```java
// These are ALL different
int number = 5;
int Number = 10;
int NUMBER = 15;
```

### 2. Class Names
```java
// Correct ✓
public class StudentRecord { }
public class BankAccount { }

// Wrong ✗
public class studentrecord { }  // Should start with capital
public class student record { } // No spaces allowed
```

### 3. Method Names
```java
// Correct ✓
public void calculateTotal() { }
public void printReport() { }

// Wrong ✗
public void CalculateTotal() { }  // Should start with lowercase
public void calculate total() { } // No spaces allowed
```

### 4. Semicolons
```java
// Every statement ends with semicolon
int age = 25;
String name = "John";
System.out.println("Hello");
```

### 5. Curly Braces
```java
// Used to group code blocks
public class MyClass {          // class body starts
    public void myMethod() {    // method body starts
        if (true) {             // if block starts
            // code here
        }                       // if block ends
    }                           // method body ends
}                               // class body ends
```

## Keywords in Java

### What are Keywords?
Keywords are **reserved words** that have special meaning in Java. Think of them like **VIP seats** - they're already booked and you can't use them for anything else.

### Common Keywords:
```java
// Access modifiers
public, private, protected

// Class-related
class, interface, extends, implements

// Method-related
static, void, return

// Control flow
if, else, for, while, switch, case

// Data types
int, double, boolean, char, String

// Object-related
new, this, super

// Exception handling
try, catch, finally, throw, throws
```

### Example:
```java
public class Student {           // public, class are keywords
    private int age;             // private, int are keywords
    
    public void setAge(int newAge) { // public, void are keywords
        if (newAge > 0) {        // if is keyword
            this.age = newAge;   // this is keyword
        }
    }
}
```

## Identifiers - Naming Rules

### What are Identifiers?
Identifiers are **names you give** to classes, methods, variables. Think of them as **labels** you put on boxes.

### Rules for Identifiers:

#### 1. Must Start With:
```java
// Valid ✓
int age;
int _count;
int $amount;

// Invalid ✗
int 123number;  // Can't start with digit
int @email;     // Can't start with special symbol
```

#### 2. Can Contain:
```java
// Valid ✓
int student1;
int total_amount;
int $price;
int userName;

// Invalid ✗
int student-name;  // Hyphen not allowed
int total amount;  // Space not allowed
int class;         // Keyword not allowed
```

#### 3. Case Sensitive:
```java
int score = 85;
int Score = 90;     // Different variable
int SCORE = 95;     // Different variable
```

## Naming Conventions (Best Practices)

### 1. Classes: **PascalCase**
```java
public class StudentRecord { }
public class BankAccount { }
public class EmployeeDetails { }
```

### 2. Methods: **camelCase**
```java
public void calculateTotal() { }
public void printReport() { }
public void getUserName() { }
```

### 3. Variables: **camelCase**
```java
int studentAge;
String firstName;
double totalAmount;
```

### 4. Constants: **UPPER_CASE**
```java
final int MAX_SIZE = 100;
final String DEFAULT_NAME = "Unknown";
final double PI = 3.14159;
```

### 5. Packages: **lowercase**
```java
package com.company.project;
package java.util;
```

## Statements and Expressions

### Simple Explanation
- **Statement**: A complete instruction (like a complete sentence)
- **Expression**: A piece that produces a value (like a phrase)

### Examples:

#### Statements:
```java
int age = 25;                    // Declaration statement
age = 30;                       // Assignment statement
System.out.println("Hello");    // Method call statement
return age;                     // Return statement
```

#### Expressions:
```java
age + 5                         // Arithmetic expression
name.length()                   // Method call expression
(age > 18)                     // Boolean expression
new Student()                   // Object creation expression
```

## Code Blocks

### What are Code Blocks?
Code blocks are **groups of statements** enclosed in curly braces `{}`. Think of them as **rooms in a house** - each room has a specific purpose.

### Examples:
```java
public class House {                    // Class block
    
    public void kitchen() {             // Method block
        
        if (hungry) {                   // If block
            System.out.println("Cook food");
        } else {                        // Else block
            System.out.println("Relax");
        }
        
        for (int i = 0; i < 5; i++) {   // Loop block
            System.out.println("Step " + i);
        }
    }
}
```

## Common Syntax Errors (and How to Avoid)

### 1. Missing Semicolon
```java
// Wrong ✗
int age = 25
System.out.println(age);

// Correct ✓
int age = 25;
System.out.println(age);
```

### 2. Mismatched Braces
```java
// Wrong ✗
public class Test {
    public void method() {
        if (true) {
            System.out.println("Hello");
        // Missing closing brace for method
    // Missing closing brace for class

// Correct ✓
public class Test {
    public void method() {
        if (true) {
            System.out.println("Hello");
        }
    }
}
```

### 3. Wrong Case
```java
// Wrong ✗
Public class test {  // Should be 'public' and 'Test'
    Public Static Void Main(string[] args) {  // Wrong case
    }
}

// Correct ✓
public class Test {
    public static void main(String[] args) {
    }
}
```

## Interview Questions & Answers

**Q1: What are the rules for Java identifiers?**
**A:**
1. Must start with letter, underscore, or dollar sign
2. Can contain letters, digits, underscores, dollar signs
3. Cannot be a Java keyword
4. Case sensitive
5. No spaces allowed

**Q2: Difference between keywords and identifiers?**
**A:**
- **Keywords**: Reserved words with predefined meaning (like 'public', 'class')
- **Identifiers**: User-defined names for variables, methods, classes

**Q3: Why is Java case sensitive?**
**A:** Java distinguishes between uppercase and lowercase letters, so 'Name' and 'name' are treated as different identifiers. This allows more flexibility but requires careful attention to capitalization.

**Q4: What is the significance of the main method?**
**A:** The main method is the entry point of Java application. JVM looks for this specific signature to start program execution: `public static void main(String[] args)`

## Complete Example Program

```java
/**
 * This is a complete Java program demonstrating basic syntax
 * Author: Your Name
 * Date: Today's Date
 */

// Package declaration (optional)
package com.example.basics;

// Class declaration
public class SyntaxExample {
    
    // Class variable (field)
    private static final String WELCOME_MESSAGE = "Welcome to Java!";
    
    // Main method - program entry point
    public static void main(String[] args) {
        // Local variables
        String studentName = "Alice";
        int studentAge = 20;
        double gpa = 3.85;
        boolean isEnrolled = true;
        
        // Method calls
        printWelcomeMessage();
        displayStudentInfo(studentName, studentAge, gpa, isEnrolled);
        
        // Control structure
        if (gpa > 3.5) {
            System.out.println("Excellent performance!");
        }
    }
    
    // Custom method
    public static void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println("Let's learn Java syntax!");
    }
    
    // Method with parameters
    public static void displayStudentInfo(String name, int age, double gpa, boolean enrolled) {
        System.out.println("\n--- Student Information ---");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("GPA: " + gpa);
        System.out.println("Enrolled: " + enrolled);
    }
}
```

## Key Takeaways

1. **Java is case-sensitive** - 'Name' and 'name' are different
2. **Every statement ends with semicolon (;)**
3. **Code blocks use curly braces { }**
4. **Classes use PascalCase**, **methods/variables use camelCase**
5. **Keywords are reserved** and cannot be used as identifiers
6. **Comments help explain code** - use them!
7. **Main method is required** to run a program
8. **Proper indentation** makes code readable

---

*Remember: Good syntax is like good handwriting - it makes your code easy to read and understand!*
