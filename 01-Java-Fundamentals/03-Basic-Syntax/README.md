# Java Basic Syntax - Your First Steps

## Understanding Java Syntax

Think of syntax as the **grammar rules** of Java language. Just like English has grammar, Java has specific rules we must follow.

## Basic Structure of a Java Program

```java
public class MyFirstProgram {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Let's break this down piece by piece:

### 1. Class Declaration
```java
public class MyFirstProgram
```
- **public**: Anyone can access this class
- **class**: We're creating a new class (like a blueprint)
- **MyFirstProgram**: Name of our class (must match filename)

### 2. Main Method
```java
public static void main(String[] args)
```
- **public**: Anyone can access this method
- **static**: Can be called without creating an object
- **void**: This method doesn't return anything
- **main**: Special method name - Java starts execution here
- **String[] args**: Parameters passed from command line

### 3. Method Body
```java
{
    System.out.println("Hello, World!");
}
```
- **{ }**: Curly braces contain the method's code
- **System.out.println()**: Prints text to screen
- **;**: Semicolon ends each statement

## Important Syntax Rules

### 1. Case Sensitivity
Java is **case-sensitive**, meaning:
```java
// These are all different:
int number = 5;
int Number = 10;
int NUMBER = 15;
```

### 2. Naming Conventions

#### Class Names (PascalCase)
```java
class MyClass { }
class StudentRecord { }
class BankAccount { }
```

#### Method and Variable Names (camelCase)
```java
int studentAge;
String firstName;
void calculateTotal() { }
```

#### Constants (UPPER_CASE)
```java
final int MAX_SIZE = 100;
final String COMPANY_NAME = "TechCorp";
```

### 3. Curly Braces { }
- **Always come in pairs**
- **Contain blocks of code**
```java
class MyClass {          // Class block starts
    void myMethod() {    // Method block starts
        if (true) {      // If block starts
            // code here
        }                // If block ends
    }                    // Method block ends
}                        // Class block ends
```

### 4. Semicolons ;
- **End every statement**
- **Not needed after curly braces**
```java
int x = 5;                    // ✓ Correct
System.out.println("Hi");     // ✓ Correct
if (x > 0) { }               // ✓ Correct (no semicolon after })
```

## Comments - Explaining Your Code

### Single Line Comments
```java
// This is a single line comment
int age = 25; // Comment at end of line
```

### Multi-line Comments
```java
/*
  This is a multi-line comment
  You can write multiple lines here
  Useful for longer explanations
*/
int salary = 50000;
```

### Documentation Comments
```java
/**
 * This method calculates the area of a circle
 * @param radius the radius of the circle
 * @return the area of the circle
 */
public double calculateArea(double radius) {
    return 3.14 * radius * radius;
}
```

## Identifiers - Naming Things

### Rules for Naming:
1. **Must start with**: Letter, underscore (_), or dollar sign ($)
2. **Can contain**: Letters, digits, underscore, dollar sign
3. **Cannot use**: Java keywords (like class, public, int)

### Valid Examples:
```java
int age;           // ✓ Good
String firstName;  // ✓ Good
double _salary;    // ✓ Valid but not recommended
int $count;        // ✓ Valid but not recommended
```

### Invalid Examples:
```java
int 2age;          // ✗ Cannot start with digit
String first-name; // ✗ Hyphen not allowed
int class;         // ✗ 'class' is a keyword
```

## Java Keywords

These words are **reserved** and cannot be used as variable names:

```
abstract    continue    for          new         switch
assert      default     goto         package     synchronized
boolean     do          if           private     this
break       double      implements   protected   throw
byte        else        import       public      throws
case        enum        instanceof   return      transient
catch       extends     int          short       try
char        final       interface    static      void
class       finally     long         strictfp    volatile
const       float       native       super       while
```

## Basic Program Examples

### Example 1: Simple Output
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Welcome to Java!");
        System.out.println("This is my first program");
        System.out.print("This doesn't add new line");
        System.out.println(" - continuing on same line");
    }
}
```

### Example 2: Using Variables
```java
public class VariableExample {
    public static void main(String[] args) {
        String name = "John";
        int age = 25;
        double height = 5.9;
        
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Height: " + height + " feet");
    }
}
```

### Example 3: Simple Calculation
```java
public class Calculator {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;
        
        int sum = a + b;
        int difference = a - b;
        int product = a * b;
        int quotient = a / b;
        
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
        System.out.println("Product: " + product);
        System.out.println("Quotient: " + quotient);
    }
}
```

## Common Beginner Mistakes

### 1. Missing Semicolon
```java
// ✗ Wrong
int x = 5
System.out.println(x);

// ✓ Correct
int x = 5;
System.out.println(x);
```

### 2. Mismatched Braces
```java
// ✗ Wrong
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello");
    // Missing closing brace

// ✓ Correct
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

### 3. Class Name Different from Filename
```java
// File: HelloWorld.java
// ✗ Wrong
public class Test {  // Class name doesn't match filename
    // code
}

// ✓ Correct
public class HelloWorld {  // Matches filename
    // code
}
```

## Output Methods

### println() vs print()
```java
System.out.println("First line");   // Adds new line
System.out.println("Second line");  // Adds new line
System.out.print("Third ");         // No new line
System.out.print("line");           // Continues on same line
```

**Output:**
```
First line
Second line
Third line
```

## Escape Characters

Special characters in strings:
```java
System.out.println("Hello\nWorld");     // \n = new line
System.out.println("Hello\tWorld");     // \t = tab
System.out.println("He said \"Hi\"");   // \" = quote
System.out.println("C:\\Users\\John");  // \\ = backslash
```

## Quick Reference

### File Structure:
1. **One public class per file**
2. **Filename must match public class name**
3. **File extension must be .java**

### Compilation and Execution:
1. **Compile**: `javac ClassName.java`
2. **Run**: `java ClassName`

### Remember:
- Java is **case-sensitive**
- Every statement ends with **;**
- Blocks use **{ }**
- Comments start with **//** or **/* */**
- Main method is the **entry point**

## Practice Exercises

1. Create a program that prints your personal information
2. Write a program with proper comments explaining each line
3. Create a simple calculator program
4. Practice using different output methods
5. Experiment with escape characters