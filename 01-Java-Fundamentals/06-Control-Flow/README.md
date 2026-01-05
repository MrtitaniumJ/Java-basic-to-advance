# Control Flow in Java - Making Decisions and Loops

## Simple Explanation

Think of **control flow** as **traffic signals** and **road signs** that direct the flow of your program:
- **if-else** = Traffic light (go this way or that way)
- **switch** = Roundabout with multiple exits
- **for loop** = Circular track (repeat specific number of times)
- **while loop** = Keep going until you reach destination

### Real-World Analogy
Control flow is like **following a recipe**:
- **if** hungry **then** cook food, **else** relax
- **for** each ingredient, add to bowl
- **while** mixture is lumpy, keep stirring
- **switch** between different cooking methods

## Professional Definition

**Control Flow** statements break up the sequential execution of statements by employing decision making, looping, and branching, enabling your program to conditionally execute particular blocks of code.

## Types of Control Flow Statements

### Overview:
```
Control Flow Statements
├── Decision Making
│   ├── if statement
│   ├── if-else statement
│   ├── if-else-if ladder
│   └── switch statement
├── Looping
│   ├── for loop
│   ├── enhanced for loop (for-each)
│   ├── while loop
│   └── do-while loop
└── Branching
    ├── break statement
    ├── continue statement
    └── return statement
```

## 1. Decision Making Statements

### if Statement

#### Simple Explanation
**if** is like asking a question. If the answer is "yes" (true), do something.

#### Syntax:
```java
if (condition) {
    // code to execute if condition is true
}
```

#### Examples:
```java
// Simple if
int age = 18;
if (age >= 18) {
    System.out.println("You can vote!");
}

// Real-world example: ATM withdrawal
double balance = 1000.50;
double withdrawAmount = 200.00;

if (balance >= withdrawAmount) {
    balance = balance - withdrawAmount;
    System.out.println("Withdrawal successful!");
    System.out.println("Remaining balance: $" + balance);
}

// Multiple conditions
int score = 85;
if (score >= 60 && score <= 100) {
    System.out.println("Valid passing score");
}
```

### if-else Statement

#### Simple Explanation
**if-else** is like a **fork in the road** - go left if condition is true, go right if false.

#### Syntax:
```java
if (condition) {
    // code if condition is true
} else {
    // code if condition is false
}
```

#### Examples:
```java
// Basic if-else
int number = 7;
if (number % 2 == 0) {
    System.out.println(number + " is even");
} else {
    System.out.println(number + " is odd");
}

// Real-world example: Login system
String username = "alice";
String password = "secret123";

if (username.equals("alice") && password.equals("secret123")) {
    System.out.println("Welcome, Alice!");
} else {
    System.out.println("Invalid credentials!");
}

// Nested if-else
int temperature = 75;
if (temperature > 80) {
    System.out.println("Hot weather - wear light clothes");
} else {
    if (temperature > 60) {
        System.out.println("Pleasant weather - perfect for outdoors");
    } else {
        System.out.println("Cold weather - wear jacket");
    }
}
```

### if-else-if Ladder

#### Simple Explanation
**if-else-if** is like **multiple choice questions** - check each option one by one until you find the right answer.

#### Syntax:
```java
if (condition1) {
    // code for condition1
} else if (condition2) {
    // code for condition2
} else if (condition3) {
    // code for condition3
} else {
    // default code
}
```

#### Examples:
```java
// Grade calculator
int marks = 87;
char grade;

if (marks >= 90) {
    grade = 'A';
} else if (marks >= 80) {
    grade = 'B';
} else if (marks >= 70) {
    grade = 'C';
} else if (marks >= 60) {
    grade = 'D';
} else {
    grade = 'F';
}

System.out.println("Your grade: " + grade);

// Real-world example: Ticket pricing
int age = 25;
double ticketPrice;

if (age < 5) {
    ticketPrice = 0.0;      // Free for toddlers
} else if (age < 18) {
    ticketPrice = 15.0;     // Child price
} else if (age < 65) {
    ticketPrice = 25.0;     // Adult price
} else {
    ticketPrice = 20.0;     // Senior discount
}

System.out.println("Ticket price: $" + ticketPrice);
```

### switch Statement

#### Simple Explanation
**switch** is like a **vending machine** - press a specific button (case) and get the corresponding item.

#### Syntax:
```java
switch (expression) {
    case value1:
        // code for value1
        break;
    case value2:
        // code for value2
        break;
    default:
        // default code
}
```

#### Examples:
```java
// Basic switch
int dayNumber = 3;
String dayName;

switch (dayNumber) {
    case 1:
        dayName = "Monday";
        break;
    case 2:
        dayName = "Tuesday";
        break;
    case 3:
        dayName = "Wednesday";
        break;
    case 4:
        dayName = "Thursday";
        break;
    case 5:
        dayName = "Friday";
        break;
    case 6:
        dayName = "Saturday";
        break;
    case 7:
        dayName = "Sunday";
        break;
    default:
        dayName = "Invalid day";
}

System.out.println("Day: " + dayName);

// Real-world example: Calculator
char operation = '+';
double num1 = 10.0;
double num2 = 5.0;
double result;

switch (operation) {
    case '+':
        result = num1 + num2;
        System.out.println(num1 + " + " + num2 + " = " + result);
        break;
    case '-':
        result = num1 - num2;
        System.out.println(num1 + " - " + num2 + " = " + result);
        break;
    case '*':
        result = num1 * num2;
        System.out.println(num1 + " * " + num2 + " = " + result);
        break;
    case '/':
        if (num2 != 0) {
            result = num1 / num2;
            System.out.println(num1 + " / " + num2 + " = " + result);
        } else {
            System.out.println("Error: Division by zero!");
        }
        break;
    default:
        System.out.println("Invalid operation!");
}

// Fall-through behavior (without break)
int month = 4;
String season;

switch (month) {
    case 12:
    case 1:
    case 2:
        season = "Winter";
        break;
    case 3:
    case 4:
    case 5:
        season = "Spring";
        break;
    case 6:
    case 7:
    case 8:
        season = "Summer";
        break;
    case 9:
    case 10:
    case 11:
        season = "Autumn";
        break;
    default:
        season = "Invalid month";
}

System.out.println("Season: " + season);
```

## 2. Looping Statements

### for Loop

#### Simple Explanation
**for loop** is like a **timer** - repeat something for a specific number of times.

#### Syntax:
```java
for (initialization; condition; increment/decrement) {
    // code to repeat
}
```

#### Examples:
```java
// Basic for loop - print numbers 1 to 5
for (int i = 1; i <= 5; i++) {
    System.out.println("Number: " + i);
}

// Real-world example: Interest calculation
double principal = 1000.0;
double rate = 0.05; // 5% annual interest
int years = 5;

System.out.println("Year\tAmount");
for (int year = 1; year <= years; year++) {
    double amount = principal * Math.pow(1 + rate, year);
    System.out.printf("%d\t$%.2f%n", year, amount);
}

// Reverse loop
for (int i = 10; i >= 1; i--) {
    System.out.println("Countdown: " + i);
}
System.out.println("Blast off!");

// Nested for loops - multiplication table
for (int i = 1; i <= 3; i++) {
    System.out.println("Table of " + i + ":");
    for (int j = 1; j <= 5; j++) {
        System.out.println(i + " × " + j + " = " + (i * j));
    }
    System.out.println(); // Empty line
}
```

### Enhanced for Loop (for-each)

#### Simple Explanation
**Enhanced for loop** is like going through a **playlist** - automatically go through each song without worrying about track numbers.

#### Syntax:
```java
for (dataType variable : collection) {
    // code to execute for each element
}
```

#### Examples:
```java
// Array iteration
int[] numbers = {10, 20, 30, 40, 50};

System.out.println("Array elements:");
for (int num : numbers) {
    System.out.println(num);
}

// String array
String[] fruits = {"apple", "banana", "orange", "grape"};

System.out.println("Fruits:");
for (String fruit : fruits) {
    System.out.println("I like " + fruit);
}

// Real-world example: Grade processing
double[] grades = {85.5, 92.0, 78.5, 96.0, 88.0};
double total = 0;
int count = 0;

for (double grade : grades) {
    total += grade;
    count++;
    System.out.println("Grade " + count + ": " + grade);
}

double average = total / count;
System.out.println("Average grade: " + average);
```

### while Loop

#### Simple Explanation
**while loop** is like **waiting for a bus** - keep waiting while the bus hasn't arrived.

#### Syntax:
```java
while (condition) {
    // code to repeat while condition is true
}
```

#### Examples:
```java
// Basic while loop
int count = 1;
while (count <= 5) {
    System.out.println("Count: " + count);
    count++; // Important: update the counter!
}

// Real-world example: User input validation
Scanner scanner = new Scanner(System.in);
int number;

System.out.println("Enter a number between 1 and 10:");
number = scanner.nextInt();

while (number < 1 || number > 10) {
    System.out.println("Invalid input! Please enter a number between 1 and 10:");
    number = scanner.nextInt();
}

System.out.println("You entered: " + number);

// Finding digits in a number
int originalNumber = 12345;
int tempNumber = originalNumber;
int digitCount = 0;

while (tempNumber > 0) {
    int digit = tempNumber % 10;
    System.out.println("Digit: " + digit);
    tempNumber = tempNumber / 10;
    digitCount++;
}

System.out.println("Total digits in " + originalNumber + ": " + digitCount);
```

### do-while Loop

#### Simple Explanation
**do-while** is like **trying food** - you always try at least once, then decide if you want more.

#### Syntax:
```java
do {
    // code to execute
} while (condition);
```

#### Examples:
```java
// Basic do-while
int number = 6;

do {
    System.out.println("Number: " + number);
    number++;
} while (number <= 5);
// Prints "Number: 6" once, even though condition is false

// Real-world example: Menu system
Scanner scanner = new Scanner(System.in);
int choice;

do {
    System.out.println("\n=== MENU ===");
    System.out.println("1. View Profile");
    System.out.println("2. Edit Profile");
    System.out.println("3. Settings");
    System.out.println("4. Exit");
    System.out.print("Enter your choice: ");
    
    choice = scanner.nextInt();
    
    switch (choice) {
        case 1:
            System.out.println("Viewing profile...");
            break;
        case 2:
            System.out.println("Editing profile...");
            break;
        case 3:
            System.out.println("Opening settings...");
            break;
        case 4:
            System.out.println("Goodbye!");
            break;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
} while (choice != 4);

// Password verification
String correctPassword = "secret123";
String enteredPassword;

do {
    System.out.print("Enter password: ");
    enteredPassword = scanner.next();
    
    if (!enteredPassword.equals(correctPassword)) {
        System.out.println("Incorrect password! Try again.");
    }
} while (!enteredPassword.equals(correctPassword));

System.out.println("Access granted!");
```

## 3. Branching Statements

### break Statement

#### Simple Explanation
**break** is like an **emergency exit** - immediately stop and get out of the loop or switch.

#### Examples:
```java
// break in for loop
for (int i = 1; i <= 10; i++) {
    if (i == 6) {
        break; // Exit loop when i equals 6
    }
    System.out.println("i = " + i);
}
// Prints: 1, 2, 3, 4, 5

// break in while loop - finding first factor
int number = 15;
int factor = 0;

for (int i = 2; i < number; i++) {
    if (number % i == 0) {
        factor = i;
        break; // Found first factor, no need to continue
    }
}

if (factor != 0) {
    System.out.println("First factor of " + number + " is: " + factor);
} else {
    System.out.println(number + " is a prime number");
}

// break in nested loop with label
outer: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            break outer; // Break out of both loops
        }
        System.out.println("i = " + i + ", j = " + j);
    }
}
```

### continue Statement

#### Simple Explanation
**continue** is like **skipping a song** - skip current iteration and move to the next one.

#### Examples:
```java
// continue in for loop
for (int i = 1; i <= 10; i++) {
    if (i % 2 == 0) {
        continue; // Skip even numbers
    }
    System.out.println("Odd number: " + i);
}
// Prints: 1, 3, 5, 7, 9

// Real-world example: Processing valid data only
int[] scores = {85, -5, 92, 150, 78, 0, 96};

System.out.println("Valid scores:");
for (int score : scores) {
    if (score < 0 || score > 100) {
        continue; // Skip invalid scores
    }
    System.out.println("Score: " + score);
}

// continue in while loop
int number = 0;
while (number < 20) {
    number++;
    if (number % 3 != 0) {
        continue; // Skip numbers not divisible by 3
    }
    System.out.println("Divisible by 3: " + number);
}
```

### return Statement

#### Simple Explanation
**return** is like **answering a question and leaving** - give back a value and exit the method.

#### Examples:
```java
// return with value
public static int findMaximum(int a, int b) {
    if (a > b) {
        return a; // Return a and exit method
    } else {
        return b; // Return b and exit method
    }
}

// return without value (void method)
public static void printGrade(int marks) {
    if (marks < 0 || marks > 100) {
        System.out.println("Invalid marks!");
        return; // Exit method early
    }
    
    if (marks >= 90) {
        System.out.println("Grade: A");
    } else if (marks >= 80) {
        System.out.println("Grade: B");
    } else if (marks >= 60) {
        System.out.println("Grade: C");
    } else {
        System.out.println("Grade: F");
    }
}

// Multiple return points
public static String getWeatherAdvice(int temperature) {
    if (temperature < 0) {
        return "Stay indoors, it's freezing!";
    }
    if (temperature < 60) {
        return "Wear a jacket";
    }
    if (temperature < 80) {
        return "Perfect weather for outdoors";
    }
    return "It's hot, stay hydrated!";
}
```

## Comparison of Loop Types

### When to Use Which Loop:

| Loop Type | When to Use | Example Use Case |
|-----------|-------------|------------------|
| **for** | Know exact number of iterations | Printing 1 to 10, processing array with index |
| **enhanced for** | Iterating through collections | Processing all elements in array/list |
| **while** | Unknown iterations, condition-based | Reading file until end, user input validation |
| **do-while** | Execute at least once | Menu systems, password verification |

### Visual Comparison:
```
for loop:        [Start] → [Check] → [Execute] → [Update] → [Check] → ...
enhanced for:    [Start] → [Get Next] → [Execute] → [Get Next] → ...
while:           [Check] → [Execute] → [Check] → [Execute] → ...
do-while:        [Execute] → [Check] → [Execute] → [Check] → ...
```

## Interview Questions & Answers

**Q1: What's the difference between while and do-while loops?**
**A:**
- **while**: Checks condition first, may not execute at all
- **do-while**: Executes at least once, then checks condition

**Q2: What happens if you forget to increment counter in while loop?**
**A:** Creates infinite loop - program keeps running forever until manually stopped or system runs out of memory.

**Q3: When would you use enhanced for loop vs regular for loop?**
**A:**
- **Enhanced for**: When you need to process all elements and don't need index
- **Regular for**: When you need index, partial processing, or reverse iteration

**Q4: What's the difference between break and continue?**
**A:**
- **break**: Exits the loop completely
- **continue**: Skips current iteration and moves to next

**Q5: Can you use break statement outside of loops or switch?**
**A:** No, break can only be used inside loops or switch statements. Using it elsewhere causes compilation error.

## Complete Example Program

```java
import java.util.Scanner;

/**
 * Comprehensive demonstration of control flow statements
 */
public class ControlFlowExample {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Demonstrate different control flow structures
        demonstrateIfElse();
        demonstrateSwitch();
        demonstrateLoops();
        
        // Interactive examples
        runCalculator(scanner);
        runNumberGuessingGame(scanner);
        
        scanner.close();
    }
    
    public static void demonstrateIfElse() {
        System.out.println("=== IF-ELSE DEMONSTRATION ===");
        
        int[] testScores = {95, 67, 82, 45, 78};
        
        for (int score : testScores) {
            String grade;
            String message;
            
            if (score >= 90) {
                grade = "A";
                message = "Excellent!";
            } else if (score >= 80) {
                grade = "B";
                message = "Good work!";
            } else if (score >= 70) {
                grade = "C";
                message = "Satisfactory";
            } else if (score >= 60) {
                grade = "D";
                message = "Needs improvement";
            } else {
                grade = "F";
                message = "Failed";
            }
            
            System.out.printf("Score: %d → Grade: %s (%s)%n", score, grade, message);
        }
        System.out.println();
    }
    
    public static void demonstrateSwitch() {
        System.out.println("=== SWITCH DEMONSTRATION ===");
        
        String[] days = {"MONDAY", "WEDNESDAY", "FRIDAY", "SUNDAY"};
        
        for (String day : days) {
            String activity;
            
            switch (day) {
                case "MONDAY":
                case "TUESDAY":
                case "WEDNESDAY":
                case "THURSDAY":
                case "FRIDAY":
                    activity = "Work day - Go to office";
                    break;
                case "SATURDAY":
                case "SUNDAY":
                    activity = "Weekend - Relax and enjoy";
                    break;
                default:
                    activity = "Unknown day";
            }
            
            System.out.println(day + ": " + activity);
        }
        System.out.println();
    }
    
    public static void demonstrateLoops() {
        System.out.println("=== LOOPS DEMONSTRATION ===");
        
        // for loop: multiplication table
        System.out.println("Multiplication table of 5:");
        for (int i = 1; i <= 10; i++) {
            System.out.printf("5 × %d = %d%n", i, 5 * i);
        }
        System.out.println();
        
        // Enhanced for loop: array processing
        int[] numbers = {15, 28, 33, 47, 52, 61, 79, 84, 90};
        System.out.println("Even numbers from array:");
        for (int num : numbers) {
            if (num % 2 == 0) {
                System.out.print(num + " ");
            }
        }
        System.out.println();
        
        // while loop: factorial calculation
        int n = 5;
        int factorial = 1;
        int temp = n;
        
        System.out.print("Calculating " + n + "! = ");
        while (temp > 0) {
            System.out.print(temp);
            factorial *= temp;
            temp--;
            if (temp > 0) {
                System.out.print(" × ");
            }
        }
        System.out.println(" = " + factorial);
        System.out.println();
    }
    
    public static void runCalculator(Scanner scanner) {
        System.out.println("=== SIMPLE CALCULATOR ===");
        
        char continueChoice;
        do {
            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();
            
            System.out.print("Enter operation (+, -, *, /): ");
            char operation = scanner.next().charAt(0);
            
            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();
            
            double result;
            boolean validOperation = true;
            
            switch (operation) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("Error: Division by zero!");
                        validOperation = false;
                        result = 0;
                    }
                    break;
                default:
                    System.out.println("Invalid operation!");
                    validOperation = false;
                    result = 0;
            }
            
            if (validOperation) {
                System.out.printf("Result: %.2f %c %.2f = %.2f%n", num1, operation, num2, result);
            }
            
            System.out.print("Continue? (y/n): ");
            continueChoice = scanner.next().charAt(0);
            
        } while (continueChoice == 'y' || continueChoice == 'Y');
        
        System.out.println("Calculator closed.");
        System.out.println();
    }
    
    public static void runNumberGuessingGame(Scanner scanner) {
        System.out.println("=== NUMBER GUESSING GAME ===");
        System.out.println("I'm thinking of a number between 1 and 100!");
        
        int secretNumber = (int)(Math.random() * 100) + 1;
        int attempts = 0;
        int maxAttempts = 7;
        boolean hasWon = false;
        
        while (attempts < maxAttempts && !hasWon) {
            System.out.printf("Attempt %d/%d - Enter your guess: ", attempts + 1, maxAttempts);
            int guess = scanner.nextInt();
            attempts++;
            
            if (guess == secretNumber) {
                hasWon = true;
                System.out.println("🎉 Congratulations! You guessed it in " + attempts + " attempts!");
            } else if (guess < secretNumber) {
                System.out.println("Too low! Try a higher number.");
            } else {
                System.out.println("Too high! Try a lower number.");
            }
            
            if (!hasWon && attempts == maxAttempts) {
                System.out.println("Game over! The number was: " + secretNumber);
            }
        }
        System.out.println();
    }
}
```

## Key Takeaways

1. **if-else** for binary decisions, **if-else-if** for multiple conditions
2. **switch** is efficient for testing single variable against many values
3. **for loop** when you know iteration count, **while** for condition-based
4. **enhanced for** is cleaner for collection iteration
5. **do-while** guarantees at least one execution
6. **break** exits loop, **continue** skips to next iteration
7. **return** exits method and optionally returns value
8. Always ensure loops have termination conditions to avoid infinite loops

---

*Remember: Control flow is like the GPS of your program - it decides which route your code takes based on conditions and requirements!*
