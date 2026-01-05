# Methods in Java - Reusable Code Blocks

## Simple Explanation

Think of **Methods** as **mini-programs** or **action blocks** inside your main program:
- Like **recipes** with step-by-step instructions
- **Reusable tools** that perform specific tasks
- **Functions** that can take inputs and give outputs
- **Building blocks** that make programs organized and manageable

### Real-World Analogies
- **Recipe** = Method (ingredients = parameters, cooking = process, dish = return value)
- **Calculator functions** = Methods (add, subtract, multiply, divide)
- **TV remote buttons** = Methods (each button does a specific action)
- **Factory machines** = Methods (raw materials in → finished product out)

## Professional Definition

**Methods** are blocks of reusable code that perform specific tasks. They help organize code, avoid repetition, and make programs more modular and maintainable. Methods can accept parameters (inputs) and return values (outputs).

## Why Use Methods?

### Benefits:
- **Code Reusability**: Write once, use many times
- **Organization**: Break large problems into smaller pieces
- **Maintainability**: Easier to debug and update
- **Readability**: Makes code more understandable
- **Testing**: Can test individual pieces separately
- **Modularity**: Each method has a specific responsibility

### Without Methods (Repetitive):
```java
// Calculating area multiple times without methods
double length1 = 10, width1 = 5;
double area1 = length1 * width1;
System.out.println("Area 1: " + area1);

double length2 = 8, width2 = 6;
double area2 = length2 * width2;
System.out.println("Area 2: " + area2);

double length3 = 12, width3 = 4;
double area3 = length3 * width3;
System.out.println("Area 3: " + area3);
```

### With Methods (Organized):
```java
// Using methods - clean and reusable
public static double calculateArea(double length, double width) {
    return length * width;
}

public static void main(String[] args) {
    System.out.println("Area 1: " + calculateArea(10, 5));
    System.out.println("Area 2: " + calculateArea(8, 6));
    System.out.println("Area 3: " + calculateArea(12, 4));
}
```

## Method Anatomy

### Basic Structure:
```java
[access_modifier] [static] [return_type] methodName([parameters]) {
    // Method body
    [return statement;]  // if return type is not void
}
```

### Components Explained:
1. **Access Modifier**: `public`, `private`, `protected` (who can use it)
2. **Static**: Method belongs to class, not instance
3. **Return Type**: What the method gives back (`void`, `int`, `String`, etc.)
4. **Method Name**: What you call it (follows camelCase convention)
5. **Parameters**: Inputs the method needs (optional)
6. **Method Body**: The actual code that executes
7. **Return Statement**: Sends back the result (if not void)

## Types of Methods

### 1. Methods with No Parameters, No Return Value (void)
```java
public static void sayHello() {
    System.out.println("Hello, World!");
    System.out.println("Welcome to Java programming!");
}

public static void main(String[] args) {
    sayHello();  // Call the method
    sayHello();  // Can call multiple times
}

// Output:
// Hello, World!
// Welcome to Java programming!
// Hello, World!
// Welcome to Java programming!
```

### 2. Methods with Parameters, No Return Value
```java
public static void greetUser(String name) {
    System.out.println("Hello, " + name + "!");
    System.out.println("Nice to meet you!");
}

public static void printRectangle(int width, int height, char symbol) {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            System.out.print(symbol);
        }
        System.out.println();
    }
}

public static void main(String[] args) {
    greetUser("Alice");    // Pass argument
    greetUser("Bob");      // Different argument
    
    System.out.println("\nRectangle:");
    printRectangle(5, 3, '*');
}

// Output:
// Hello, Alice!
// Nice to meet you!
// Hello, Bob!
// Nice to meet you!
// 
// Rectangle:
// *****
// *****
// *****
```

### 3. Methods with No Parameters, Return Value
```java
public static int getCurrentYear() {
    return 2024;  // Return a value
}

public static double generateRandomNumber() {
    return Math.random() * 100;  // Random number 0-100
}

public static String getWelcomeMessage() {
    return "Welcome to our application!";
}

public static void main(String[] args) {
    int year = getCurrentYear();
    System.out.println("Current year: " + year);
    
    double randomNum = generateRandomNumber();
    System.out.println("Random number: " + randomNum);
    
    String message = getWelcomeMessage();
    System.out.println(message);
}
```

### 4. Methods with Parameters and Return Value
```java
public static int addNumbers(int a, int b) {
    int sum = a + b;
    return sum;
}

public static double calculateCircleArea(double radius) {
    double area = Math.PI * radius * radius;
    return area;
}

public static String createFullName(String firstName, String lastName) {
    return firstName + " " + lastName;
}

public static boolean isEven(int number) {
    return number % 2 == 0;
}

public static void main(String[] args) {
    int result = addNumbers(10, 20);
    System.out.println("Sum: " + result);
    
    double circleArea = calculateCircleArea(5.0);
    System.out.println("Circle area: " + circleArea);
    
    String fullName = createFullName("John", "Doe");
    System.out.println("Full name: " + fullName);
    
    boolean evenCheck = isEven(15);
    System.out.println("15 is even: " + evenCheck);
}
```

## Method Overloading

**Method Overloading** = Same method name, different parameters

```java
// Same method name with different parameter combinations
public static int calculate(int a, int b) {
    return a + b;  // Addition for integers
}

public static double calculate(double a, double b) {
    return a + b;  // Addition for doubles
}

public static int calculate(int a, int b, int c) {
    return a + b + c;  // Addition for three integers
}

public static String calculate(String a, String b) {
    return a + b;  // Concatenation for strings
}

public static void main(String[] args) {
    System.out.println(calculate(5, 3));           // Calls int version
    System.out.println(calculate(5.5, 3.2));      // Calls double version
    System.out.println(calculate(1, 2, 3));       // Calls three-parameter version
    System.out.println(calculate("Hello", "World")); // Calls string version
}

// Output:
// 8
// 8.7
// 6
// HelloWorld
```

### Valid Overloading Examples:
```java
// Different number of parameters
public static void print(String message) { }
public static void print(String message, int times) { }

// Different parameter types
public static double area(double radius) { }                    // Circle
public static double area(double length, double width) { }      // Rectangle
public static double area(double base, double height, String shape) { } // Triangle

// Different parameter order (not recommended, confusing)
public static void process(String name, int age) { }
public static void process(int age, String name) { }
```

### Invalid Overloading (Compilation Error):
```java
// Only return type is different - NOT valid overloading
public static int getValue() { return 1; }
public static double getValue() { return 1.0; }  // ERROR!

// Only parameter names are different - NOT valid overloading
public static void method(int a) { }
public static void method(int b) { }  // ERROR!
```

## Variable Arguments (Varargs)

**Varargs** allows methods to accept variable number of arguments:

```java
// Method that accepts any number of integers
public static int sum(int... numbers) {
    int total = 0;
    for (int num : numbers) {
        total += num;
    }
    return total;
}

// Method that accepts fixed parameter + varargs
public static void printInfo(String prefix, String... messages) {
    System.out.print(prefix + ": ");
    for (String message : messages) {
        System.out.print(message + " ");
    }
    System.out.println();
}

public static void main(String[] args) {
    // Can call with different number of arguments
    System.out.println(sum());              // 0 (no arguments)
    System.out.println(sum(5));             // 5
    System.out.println(sum(1, 2, 3));       // 6
    System.out.println(sum(10, 20, 30, 40)); // 100
    
    printInfo("Alert", "System", "is", "ready");
    printInfo("Error", "File", "not", "found", "please", "check");
}

// Output:
// 0
// 5
// 6
// 100
// Alert: System is ready 
// Error: File not found please check
```

### Varargs Rules:
1. **Only one varargs parameter** per method
2. **Must be last parameter** if multiple parameters exist
3. **Treated as array** inside the method

## Method Scope and Local Variables

### Local Variables:
```java
public static void demonstrateScope() {
    int x = 10;        // Local variable - only exists in this method
    String message = "Hello";  // Another local variable
    
    if (x > 5) {
        int y = 20;    // Block-scope variable - only exists in this if block
        System.out.println("x = " + x + ", y = " + y);
    }
    
    // System.out.println(y);  // ERROR! y is not accessible here
    System.out.println("x = " + x);  // x is still accessible
}

public static void anotherMethod() {
    // System.out.println(x);  // ERROR! x from demonstrateScope() not accessible here
    int x = 30;  // This is a different x, local to this method
    System.out.println("Different x: " + x);
}
```

### Parameter vs Local Variable:
```java
public static void processData(int input) {  // 'input' is a parameter
    int result = input * 2;    // 'result' is a local variable
    String status = "processed";  // 'status' is a local variable
    
    System.out.println("Input: " + input);
    System.out.println("Result: " + result);
    System.out.println("Status: " + status);
}  // All variables destroyed when method ends
```

## Recursive Methods

**Recursion** = Method calls itself

```java
// Calculate factorial using recursion
public static int factorial(int n) {
    // Base case - stops the recursion
    if (n <= 1) {
        return 1;
    }
    // Recursive case - method calls itself
    return n * factorial(n - 1);
}

// Calculate Fibonacci number using recursion
public static int fibonacci(int n) {
    // Base cases
    if (n <= 0) return 0;
    if (n == 1) return 1;
    
    // Recursive case
    return fibonacci(n - 1) + fibonacci(n - 2);
}

// Print numbers from n to 1 using recursion
public static void countDown(int n) {
    // Base case
    if (n <= 0) {
        System.out.println("Done!");
        return;
    }
    
    // Print current number
    System.out.println(n);
    
    // Recursive call
    countDown(n - 1);
}

public static void main(String[] args) {
    System.out.println("Factorial of 5: " + factorial(5));  // 120
    System.out.println("Fibonacci of 6: " + fibonacci(6));  // 8
    
    System.out.println("Countdown from 5:");
    countDown(5);
}

// Output:
// Factorial of 5: 120
// Fibonacci of 6: 8
// Countdown from 5:
// 5
// 4
// 3
// 2
// 1
// Done!
```

### How Recursion Works:
```java
// factorial(5) breakdown:
// factorial(5) = 5 * factorial(4)
//              = 5 * 4 * factorial(3)
//              = 5 * 4 * 3 * factorial(2)
//              = 5 * 4 * 3 * 2 * factorial(1)
//              = 5 * 4 * 3 * 2 * 1
//              = 120
```

## Access Modifiers in Methods

### 1. Public Methods
```java
public static void publicMethod() {
    System.out.println("This method can be called from anywhere");
}
```

### 2. Private Methods
```java
private static void privateMethod() {
    System.out.println("This method can only be called from within this class");
}

// Helper method - often private
private static boolean isValidAge(int age) {
    return age >= 0 && age <= 150;
}

public static void registerUser(String name, int age) {
    if (isValidAge(age)) {  // Using private helper method
        System.out.println("User " + name + " registered successfully");
    } else {
        System.out.println("Invalid age: " + age);
    }
}
```

### 3. Protected Methods
```java
protected static void protectedMethod() {
    System.out.println("Accessible within package and subclasses");
}
```

## Static vs Non-Static Methods

### Static Methods (Class Methods):
```java
public class Calculator {
    // Static method - belongs to the class
    public static int add(int a, int b) {
        return a + b;
    }
    
    // Call static method using class name
    public static void main(String[] args) {
        int result = Calculator.add(5, 3);  // ClassName.methodName()
        System.out.println("Result: " + result);
    }
}
```

### Non-Static Methods (Instance Methods):
```java
public class Calculator {
    private int memory = 0;  // Instance variable
    
    // Non-static method - belongs to object instance
    public int addToMemory(int value) {
        memory += value;
        return memory;
    }
    
    public int getMemory() {
        return memory;
    }
    
    public static void main(String[] args) {
        // Need to create object to call non-static methods
        Calculator calc = new Calculator();
        System.out.println("Memory: " + calc.addToMemory(10));
        System.out.println("Memory: " + calc.addToMemory(5));
        System.out.println("Current memory: " + calc.getMemory());
    }
}

// Output:
// Memory: 10
// Memory: 15
// Current memory: 15
```

## Best Practices for Methods

### 1. Single Responsibility Principle
```java
// BAD - method does too many things
public static void processUserBad(String name, int age, String email) {
    // Validate name
    if (name == null || name.trim().isEmpty()) {
        System.out.println("Invalid name");
        return;
    }
    
    // Validate age
    if (age < 0 || age > 150) {
        System.out.println("Invalid age");
        return;
    }
    
    // Validate email
    if (!email.contains("@")) {
        System.out.println("Invalid email");
        return;
    }
    
    // Save to database
    System.out.println("Saving user to database...");
    
    // Send welcome email
    System.out.println("Sending welcome email...");
}

// GOOD - separate methods for each responsibility
public static boolean isValidName(String name) {
    return name != null && !name.trim().isEmpty();
}

public static boolean isValidAge(int age) {
    return age >= 0 && age <= 150;
}

public static boolean isValidEmail(String email) {
    return email != null && email.contains("@") && email.contains(".");
}

public static void saveUserToDatabase(String name, int age, String email) {
    System.out.println("Saving user " + name + " to database...");
}

public static void sendWelcomeEmail(String email) {
    System.out.println("Sending welcome email to " + email);
}

public static void processUserGood(String name, int age, String email) {
    if (!isValidName(name)) {
        System.out.println("Invalid name");
        return;
    }
    
    if (!isValidAge(age)) {
        System.out.println("Invalid age");
        return;
    }
    
    if (!isValidEmail(email)) {
        System.out.println("Invalid email");
        return;
    }
    
    saveUserToDatabase(name, age, email);
    sendWelcomeEmail(email);
}
```

### 2. Meaningful Method Names
```java
// BAD - unclear names
public static int calc(int a, int b) { return a + b; }
public static void process() { System.out.println("Processing..."); }
public static boolean check(String s) { return s.length() > 0; }

// GOOD - descriptive names
public static int calculateSum(int first, int second) { return first + second; }
public static void processPaymentTransaction() { System.out.println("Processing payment..."); }
public static boolean isStringNotEmpty(String text) { return text.length() > 0; }
```

### 3. Method Size
```java
// Keep methods small and focused (generally under 20-30 lines)
// If a method is too long, break it into smaller methods

// BAD - too long and complex
public static void processOrderBad() {
    // 50+ lines of code doing many things
}

// GOOD - broken into smaller methods
public static void processOrder() {
    validateOrder();
    calculateTotals();
    applyDiscounts();
    processPayment();
    updateInventory();
    sendConfirmation();
}
```

## Common Method Patterns

### 1. Utility Methods
```java
// Math utilities
public static double calculateDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

public static boolean isPrime(int number) {
    if (number <= 1) return false;
    for (int i = 2; i <= Math.sqrt(number); i++) {
        if (number % i == 0) return false;
    }
    return true;
}

// String utilities
public static String capitalizeWords(String text) {
    String[] words = text.toLowerCase().split(" ");
    StringBuilder result = new StringBuilder();
    for (String word : words) {
        if (word.length() > 0) {
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1))
                  .append(" ");
        }
    }
    return result.toString().trim();
}
```

### 2. Validation Methods
```java
public static boolean isValidPassword(String password) {
    return password != null && 
           password.length() >= 8 && 
           password.matches(".*[A-Z].*") &&     // Has uppercase
           password.matches(".*[a-z].*") &&     // Has lowercase
           password.matches(".*[0-9].*") &&     // Has digit
           password.matches(".*[!@#$%^&*].*");  // Has special char
}

public static boolean isValidCreditCard(String cardNumber) {
    // Remove spaces and dashes
    cardNumber = cardNumber.replaceAll("[\\s-]", "");
    
    // Check if all digits and proper length
    if (!cardNumber.matches("\\d{13,19}")) {
        return false;
    }
    
    // Luhn algorithm check (simplified)
    int sum = 0;
    boolean alternate = false;
    
    for (int i = cardNumber.length() - 1; i >= 0; i--) {
        int digit = Character.getNumericValue(cardNumber.charAt(i));
        
        if (alternate) {
            digit *= 2;
            if (digit > 9) {
                digit = (digit % 10) + 1;
            }
        }
        
        sum += digit;
        alternate = !alternate;
    }
    
    return sum % 10 == 0;
}
```

### 3. Factory Methods
```java
// Methods that create and return objects
public static String createEmailSignature(String name, String title, String company) {
    return "Best regards,\n" +
           name + "\n" +
           title + "\n" +
           company;
}

public static int[] createNumberSequence(int start, int end, int step) {
    int size = (end - start) / step + 1;
    int[] sequence = new int[size];
    
    for (int i = 0; i < size; i++) {
        sequence[i] = start + (i * step);
    }
    
    return sequence;
}
```

## Interview Questions & Answers

**Q1: What is the difference between parameters and arguments?**
**A:** 
- **Parameters** are variables defined in method signature (placeholders)
- **Arguments** are actual values passed when calling the method

**Q2: What is method overloading? What are its rules?**
**A:** Method overloading is having multiple methods with same name but different parameters. Rules:
- Same method name
- Different parameter list (number, type, or order)
- Return type alone cannot differentiate overloaded methods

**Q3: What is the difference between static and non-static methods?**
**A:**
- **Static methods** belong to class, called using class name, cannot access instance variables
- **Non-static methods** belong to objects, called using object reference, can access instance variables

**Q4: What is recursion? What are its components?**
**A:** Recursion is when a method calls itself. Components:
- **Base case**: Condition to stop recursion
- **Recursive case**: Method calls itself with modified parameters

**Q5: What is the purpose of the return statement?**
**A:** Return statement:
- Exits the method immediately
- Returns a value to the caller (if return type is not void)
- Can be used without value in void methods to exit early

**Q6: Can we have multiple return statements in a method?**
**A:** Yes, but only one executes per method call. Often used with conditional logic for different exit points.

**Q7: What happens if we don't provide a return statement in a non-void method?**
**A:** Compilation error occurs. Non-void methods must return a value of the specified type.

## Complete Example Program

```java
import java.util.Scanner;
import java.util.Arrays;

/**
 * Comprehensive Methods demonstration
 */
public class MethodsExample {
    
    public static void main(String[] args) {
        demonstrateBasicMethods();
        demonstrateMethodOverloading();
        demonstrateVarargs();
        demonstrateRecursion();
        runCalculatorDemo();
    }
    
    // ===== BASIC METHODS DEMONSTRATION =====
    
    public static void demonstrateBasicMethods() {
        System.out.println("=== BASIC METHODS DEMONSTRATION ===");
        
        // Method with no parameters, no return
        printWelcomeBanner();
        
        // Method with parameters, no return
        greetUser("Alice", 25);
        greetUser("Bob", 30);
        
        // Method with no parameters, with return
        String currentTime = getCurrentTimeString();
        System.out.println("Current time: " + currentTime);
        
        // Method with parameters and return
        double circleArea = calculateCircleArea(5.0);
        System.out.println("Circle area (radius 5): " + circleArea);
        
        boolean isLeapYear = checkLeapYear(2024);
        System.out.println("2024 is leap year: " + isLeapYear);
        
        System.out.println();
    }
    
    public static void printWelcomeBanner() {
        System.out.println("********************************");
        System.out.println("*     WELCOME TO JAVA METHODS    *");
        System.out.println("********************************");
    }
    
    public static void greetUser(String name, int age) {
        System.out.println("Hello " + name + "!");
        System.out.println("You are " + age + " years old.");
        
        if (age >= 18) {
            System.out.println("You are an adult.");
        } else {
            System.out.println("You are a minor.");
        }
        System.out.println("---");
    }
    
    public static String getCurrentTimeString() {
        return "2024-12-28 14:30:00";  // Simplified for demo
    }
    
    public static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;
    }
    
    public static boolean checkLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    // ===== METHOD OVERLOADING DEMONSTRATION =====
    
    public static void demonstrateMethodOverloading() {
        System.out.println("=== METHOD OVERLOADING DEMONSTRATION ===");
        
        // Same method name, different parameters
        System.out.println("Area of square (5): " + calculateArea(5));
        System.out.println("Area of rectangle (4, 6): " + calculateArea(4, 6));
        System.out.println("Area of circle (radius 3): " + calculateArea(3.0));
        System.out.println("Area of triangle (base 8, height 5): " + calculateArea(8, 5, "triangle"));
        
        // Distance calculations
        System.out.println("Distance 1D (5 to 10): " + calculateDistance(5, 10));
        System.out.println("Distance 2D (0,0 to 3,4): " + calculateDistance(0, 0, 3, 4));
        
        System.out.println();
    }
    
    // Overloaded calculateArea methods
    public static int calculateArea(int side) {  // Square
        return side * side;
    }
    
    public static int calculateArea(int length, int width) {  // Rectangle
        return length * width;
    }
    
    public static double calculateArea(double radius) {  // Circle
        return Math.PI * radius * radius;
    }
    
    public static double calculateArea(double base, double height, String shape) {  // Triangle
        if ("triangle".equalsIgnoreCase(shape)) {
            return 0.5 * base * height;
        }
        return 0;
    }
    
    // Overloaded calculateDistance methods
    public static int calculateDistance(int point1, int point2) {  // 1D distance
        return Math.abs(point2 - point1);
    }
    
    public static double calculateDistance(double x1, double y1, double x2, double y2) {  // 2D distance
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    // ===== VARARGS DEMONSTRATION =====
    
    public static void demonstrateVarargs() {
        System.out.println("=== VARARGS DEMONSTRATION ===");
        
        // Sum with different number of arguments
        System.out.println("Sum of no numbers: " + sum());
        System.out.println("Sum of (5): " + sum(5));
        System.out.println("Sum of (1, 2, 3): " + sum(1, 2, 3));
        System.out.println("Sum of (10, 20, 30, 40, 50): " + sum(10, 20, 30, 40, 50));
        
        // Print formatted messages
        printMessage("INFO", "System started");
        printMessage("ERROR", "Database", "connection", "failed");
        printMessage("WARNING", "Low", "disk", "space", "detected");
        
        // Find maximum
        System.out.println("Max of (3, 7, 2, 9, 1): " + findMaximum(3, 7, 2, 9, 1));
        
        System.out.println();
    }
    
    public static int sum(int... numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }
    
    public static void printMessage(String level, String... parts) {
        System.out.print("[" + level + "] ");
        for (String part : parts) {
            System.out.print(part + " ");
        }
        System.out.println();
    }
    
    public static int findMaximum(int... numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("At least one number required");
        }
        
        int max = numbers[0];
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }
    
    // ===== RECURSION DEMONSTRATION =====
    
    public static void demonstrateRecursion() {
        System.out.println("=== RECURSION DEMONSTRATION ===");
        
        // Factorial calculation
        System.out.println("Factorial of 5: " + factorial(5));
        System.out.println("Factorial of 7: " + factorial(7));
        
        // Fibonacci sequence
        System.out.print("Fibonacci sequence (first 10): ");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();
        
        // Power calculation
        System.out.println("2 to the power of 8: " + power(2, 8));
        System.out.println("3 to the power of 4: " + power(3, 4));
        
        // String reversal
        System.out.println("Reverse of 'Hello': " + reverseString("Hello"));
        System.out.println("Reverse of 'Recursion': " + reverseString("Recursion"));
        
        // Sum of digits
        System.out.println("Sum of digits in 12345: " + sumOfDigits(12345));
        
        System.out.println();
    }
    
    public static long factorial(int n) {
        // Base case
        if (n <= 1) {
            return 1;
        }
        // Recursive case
        return n * factorial(n - 1);
    }
    
    public static int fibonacci(int n) {
        // Base cases
        if (n <= 0) return 0;
        if (n == 1) return 1;
        
        // Recursive case
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    public static long power(int base, int exponent) {
        // Base case
        if (exponent == 0) {
            return 1;
        }
        
        // Recursive case
        return base * power(base, exponent - 1);
    }
    
    public static String reverseString(String str) {
        // Base case
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        // Recursive case
        return reverseString(str.substring(1)) + str.charAt(0);
    }
    
    public static int sumOfDigits(int number) {
        // Base case
        if (number < 10) {
            return number;
        }
        
        // Recursive case
        return (number % 10) + sumOfDigits(number / 10);
    }
    
    // ===== CALCULATOR DEMO =====
    
    public static void runCalculatorDemo() {
        System.out.println("=== CALCULATOR DEMO ===");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nCalculator Menu:");
            System.out.println("1. Basic Operations");
            System.out.println("2. Advanced Operations");
            System.out.println("3. Array Operations");
            System.out.println("4. String Operations");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    basicOperations(scanner);
                    break;
                case 2:
                    advancedOperations(scanner);
                    break;
                case 3:
                    arrayOperations(scanner);
                    break;
                case 4:
                    stringOperations(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the calculator!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    private static void basicOperations(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        
        System.out.println("Results:");
        System.out.println("Addition: " + add(num1, num2));
        System.out.println("Subtraction: " + subtract(num1, num2));
        System.out.println("Multiplication: " + multiply(num1, num2));
        System.out.println("Division: " + divide(num1, num2));
        System.out.println("Modulus: " + modulus(num1, num2));
    }
    
    private static void advancedOperations(Scanner scanner) {
        System.out.print("Enter a number: ");
        double num = scanner.nextDouble();
        
        System.out.println("Results:");
        System.out.println("Square: " + square(num));
        System.out.println("Cube: " + cube(num));
        System.out.println("Square root: " + squareRoot(num));
        System.out.println("Absolute value: " + absoluteValue(num));
        System.out.println("Is even: " + isEven((int)num));
        System.out.println("Is prime: " + isPrime((int)num));
    }
    
    private static void arrayOperations(Scanner scanner) {
        System.out.print("Enter array size: ");
        int size = scanner.nextInt();
        int[] array = new int[size];
        
        System.out.println("Enter " + size + " numbers:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        
        System.out.println("Array: " + Arrays.toString(array));
        System.out.println("Sum: " + sumArray(array));
        System.out.println("Average: " + averageArray(array));
        System.out.println("Maximum: " + findMax(array));
        System.out.println("Minimum: " + findMin(array));
        System.out.println("Sorted: " + Arrays.toString(sortArray(array)));
    }
    
    private static void stringOperations(Scanner scanner) {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter a string: ");
        String text = scanner.nextLine();
        
        System.out.println("Results:");
        System.out.println("Length: " + getStringLength(text));
        System.out.println("Uppercase: " + toUpperCase(text));
        System.out.println("Lowercase: " + toLowerCase(text));
        System.out.println("Reversed: " + reverseString(text));
        System.out.println("Is palindrome: " + isPalindrome(text));
        System.out.println("Vowel count: " + countVowels(text));
        System.out.println("Word count: " + countWords(text));
    }
    
    // Basic operation methods
    public static double add(double a, double b) { return a + b; }
    public static double subtract(double a, double b) { return a - b; }
    public static double multiply(double a, double b) { return a * b; }
    public static double divide(double a, double b) { 
        return b != 0 ? a / b : Double.POSITIVE_INFINITY; 
    }
    public static double modulus(double a, double b) { return a % b; }
    
    // Advanced operation methods
    public static double square(double num) { return num * num; }
    public static double cube(double num) { return num * num * num; }
    public static double squareRoot(double num) { return Math.sqrt(num); }
    public static double absoluteValue(double num) { return Math.abs(num); }
    
    public static boolean isEven(int num) { return num % 2 == 0; }
    
    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }
    
    // Array operation methods
    public static int sumArray(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }
    
    public static double averageArray(int[] array) {
        return array.length > 0 ? (double) sumArray(array) / array.length : 0;
    }
    
    public static int findMax(int[] array) {
        if (array.length == 0) return Integer.MIN_VALUE;
        int max = array[0];
        for (int num : array) {
            if (num > max) max = num;
        }
        return max;
    }
    
    public static int findMin(int[] array) {
        if (array.length == 0) return Integer.MAX_VALUE;
        int min = array[0];
        for (int num : array) {
            if (num < min) min = num;
        }
        return min;
    }
    
    public static int[] sortArray(int[] array) {
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        return sorted;
    }
    
    // String operation methods
    public static int getStringLength(String str) { return str.length(); }
    public static String toUpperCase(String str) { return str.toUpperCase(); }
    public static String toLowerCase(String str) { return str.toLowerCase(); }
    
    public static boolean isPalindrome(String str) {
        String cleaned = str.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        return cleaned.equals(reverseString(cleaned));
    }
    
    public static int countVowels(String str) {
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : str.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
    
    public static int countWords(String str) {
        if (str == null || str.trim().isEmpty()) return 0;
        return str.trim().split("\\s+").length;
    }
}
```

## Key Takeaways

1. **Methods organize code** into reusable blocks
2. **Single Responsibility**: Each method should do one thing well
3. **Method overloading** allows same name with different parameters
4. **Varargs** enable flexible parameter counts
5. **Recursion** is powerful for problems with self-similar subproblems
6. **Static methods** belong to class, non-static to objects
7. **Access modifiers** control method visibility
8. **Good naming** makes code self-documenting
9. **Small methods** are easier to understand and test
10. **Return early** to avoid deep nesting in conditional logic

---

*Remember: Methods are like building blocks - small, focused pieces that combine to create complex and powerful programs!*
