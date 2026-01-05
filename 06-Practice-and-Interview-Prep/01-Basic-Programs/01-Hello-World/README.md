# Hello World Program

## Problem Statement

Write a Java program that prints "Hello, World!" to the console. This is the most fundamental program in any programming language and serves as an introduction to Java's syntax and structure.

**Objective:** Understand the basic structure of a Java program and how to execute code.

---

## Concepts Explained

### 1. **Class Declaration**
```java
public class HelloWorld {
    // Class body
}
```
- A Java program is organized into classes
- The class name must match the filename (`HelloWorld.java`)
- `public` means the class is accessible from other classes

### 2. **Main Method**
```java
public static void main(String[] args) {
    // Code execution starts here
}
```
- `public`: Method is accessible from anywhere
- `static`: Method belongs to the class, not individual objects
- `void`: Method doesn't return any value
- `String[] args`: Parameter to accept command-line arguments
- This is the entry point when you run the program with `java HelloWorld`

### 3. **System.out.println()**
```java
System.out.println("Hello, World!");
```
- `System`: A built-in Java class
- `out`: A static output stream object
- `println()`: Prints text and adds a newline at the end
- `print()`: Prints text without newline

### 4. **String Concatenation**
```java
String greeting = "Hello";
System.out.println(greeting + ", World!");
```
- The `+` operator joins strings together

---

## Solution Breakdown

### Step 1: Declare the Class
```java
public class HelloWorld {
```
Create a class named `HelloWorld`. This class name should match the filename.

### Step 2: Create the Main Method
```java
public static void main(String[] args) {
```
Define the main method where program execution begins.

### Step 3: Add Print Statements
```java
System.out.println("Hello, World!");
```
Print the message to the console.

---

## Algorithm

1. Create a Java class with the name matching the filename
2. Add the main method inside the class
3. Inside main, use `System.out.println()` to print output
4. Close all brackets properly

---

## Time & Space Complexity

| Aspect | Complexity |
|--------|-----------|
| Time Complexity | O(1) - Constant time, one print operation |
| Space Complexity | O(1) - No additional space used |

**Explanation:** This program performs a fixed number of operations regardless of input size.

---

## Sample Input/Output

### Execution Command
```bash
javac HelloWorld.java
java HelloWorld
```

### Output
```
Hello, World!
Welcome to Java Programming!
This is a basic Java program.
Learning Java is fun!
Hello, Java Learner! Welcome to 2026
Name: Java Learner, Year: 2026
```

---

## Code Walkthrough

```java
public class HelloWorld {              // 1. Class declaration
    public static void main(String[] args) {  // 2. Main method
        System.out.println("Hello, World!");  // 3. Print output
    }
}
```

**Execution Flow:**
1. JVM loads the `HelloWorld` class
2. JVM finds and executes the `main()` method
3. `System.out.println()` sends text to the console
4. Program terminates

---

## Variations and Extensions

### Variation 1: Store Output in a Variable
```java
String message = "Hello, World!";
System.out.println(message);
```

### Variation 2: Print Multiple Lines
```java
System.out.println("Hello,");
System.out.println("World!");
```

### Variation 3: Use String Concatenation
```java
String greeting = "Hello";
String target = "World";
System.out.println(greeting + ", " + target + "!");
```

### Variation 4: Formatted Output
```java
String name = "Java";
System.out.printf("Hello, %s!%n", name);
```

### Variation 5: Special Characters
```java
System.out.println("Hello, \"World\"!");  // Prints: Hello, "World"!
System.out.println("Line 1\nLine 2");     // Prints on two lines
System.out.println("Tab\tSeparated");     // Prints with tab
```

---

## Challenges

### Challenge 1: Print a Pattern
Write a program to print a box pattern:
```
*****
*   *
*   *
*****
```

### Challenge 2: Print with Variables
Create a program that takes a name and prints:
```
Hello, {name}!
Welcome to Java!
Today is {date}
```

### Challenge 3: Multiple Languages
Print "Hello, World!" in 5 different languages.

### Challenge 4: Formatted Output
Print your details in a formatted table:
```
Name     : John
Age      : 25
Location : City
```

### Challenge 5: ASCII Art
Print a simple ASCII art (like a smiley face or pyramid).

---

## Common Errors and Solutions

### Error 1: File Name Mismatch
```
Error: class HelloWorld is public, should be declared in a file named HelloWorld.java
```
**Solution:** Make sure the filename matches the class name exactly.

### Error 2: Missing Main Method
```
Error: No main method found in HelloWorld
```
**Solution:** Ensure the main method signature is exactly:
```java
public static void main(String[] args)
```

### Error 3: Missing Semicolon
```
Error: ';' expected
```
**Solution:** Every statement must end with a semicolon (`;`)

### Error 4: Missing Quotes
```
Error: unclosed string literal
```
**Solution:** Always close string literals with matching quotes:
```java
System.out.println("Text"); // Correct
System.out.println("Text);  // Wrong - missing closing quote
```

---

## Key Takeaways

1. **Every Java program needs a main method** to execute
2. **Class names should match filenames** (including case)
3. **System.out.println() outputs text** to the console
4. **Java is case-sensitive** - `public` and `Public` are different
5. **Statements end with semicolons** in Java

---

## Prerequisites Needed

- Java Development Kit (JDK) installed
- A text editor or IDE
- Basic understanding of file creation

---

## Next Steps

Once you're comfortable with this program:
1. Modify it to print different messages
2. Use variables to store strings
3. Experiment with string concatenation
4. Try printing special characters and formatted output
5. Move on to the **Even/Odd Checker** program

---

## Real-World Application

This basic program demonstrates:
- How to structure a Java program
- How to create executable code
- How to produce output to users
- The foundation for all Java applications

Every complex Java application starts with this basic structure!

---

## Additional Resources

- **Java Documentation:** https://docs.oracle.com/javase/
- **String Class Methods:** Research `System.out` and `println()` methods
- **Escape Sequences:** Learn about `\n`, `\t`, `\\`, `\"` etc.

---

**Last Updated:** January 2026
**Difficulty:** ⭐ Beginner
**Estimated Time:** 5 minutes
