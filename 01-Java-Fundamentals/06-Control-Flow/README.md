# Control Flow - Making Decisions and Repeating Actions

## What is Control Flow?

Control flow determines **which code executes and in what order**. Think of it like giving directions to someone:
- "If it's raining, take an umbrella"
- "While you're hungry, keep eating" 
- "For each item in the list, check the price"

These are the same concepts we use in programming!

## Types of Control Flow

Java has several control flow statements:
1. **Conditional Statements** - Make decisions (if, else, switch)
2. **Loop Statements** - Repeat actions (for, while, do-while)
3. **Jump Statements** - Change flow (break, continue, return)

---

## 1. Conditional Statements

### if Statement

The `if` statement executes code **only if a condition is true**:

```java
public class IfExample {
    public static void main(String[] args) {
        int age = 18;
        
        // Simple if statement
        if (age >= 18) {
            System.out.println("You are an adult!");
            System.out.println("You can vote!");
        }
        
        // This will print because 18 >= 18 is true
    }
}
```

### if-else Statement

Execute different code based on **true or false** condition:

```java
public class IfElseExample {
    public static void main(String[] args) {
        int temperature = 25;
        
        if (temperature > 30) {
            System.out.println("It's hot outside!");
            System.out.println("Wear light clothes.");
        } else {
            System.out.println("It's not too hot.");
            System.out.println("Comfortable weather.");
        }
        
        // Output: "It's not too hot." and "Comfortable weather."
    }
}
```

### if-else-if Ladder

Check **multiple conditions** in order:

```java
public class GradeExample {
    public static void main(String[] args) {
        int score = 85;
        
        if (score >= 90) {
            System.out.println("Grade: A - Excellent!");
        } else if (score >= 80) {
            System.out.println("Grade: B - Good!");
        } else if (score >= 70) {
            System.out.println("Grade: C - Average");
        } else if (score >= 60) {
            System.out.println("Grade: D - Below Average");
        } else {
            System.out.println("Grade: F - Fail");
        }
        
        // Output: "Grade: B - Good!" (because 85 >= 80)
    }
}
```

### Nested if Statements

**if statements inside other if statements**:

```java
public class NestedIfExample {
    public static void main(String[] args) {
        boolean hasLicense = true;
        int age = 20;
        boolean hasInsurance = true;
        
        if (hasLicense) {
            System.out.println("You have a license.");
            
            if (age >= 18) {
                System.out.println("You are old enough to drive.");
                
                if (hasInsurance) {
                    System.out.println("You can drive legally!");
                } else {
                    System.out.println("You need insurance to drive.");
                }
            } else {
                System.out.println("You are too young to drive.");
            }
        } else {
            System.out.println("You need to get a license first.");
        }
    }
}
```

### switch Statement

**Clean way to check multiple specific values**:

```java
public class SwitchExample {
    public static void main(String[] args) {
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
                break;
        }
        
        System.out.println("Day " + dayNumber + " is " + dayName);
        // Output: "Day 3 is Wednesday"
    }
}
```

### switch with String (Java 7+)

```java
public class StringSwitchExample {
    public static void main(String[] args) {
        String grade = "B";
        String description;
        
        switch (grade.toLowerCase()) {
            case "a":
                description = "Excellent work!";
                break;
            case "b":
                description = "Good job!";
                break;
            case "c":
                description = "You can do better.";
                break;
            case "d":
                description = "Need improvement.";
                break;
            case "f":
                description = "Failed. Try again.";
                break;
            default:
                description = "Invalid grade";
                break;
        }
        
        System.out.println("Grade " + grade + ": " + description);
    }
}
```

---

## 2. Loop Statements

Loops **repeat code multiple times**. Think of them as:
- **for loop**: "Do this 10 times"
- **while loop**: "Keep doing this until something happens"
- **do-while loop**: "Do this first, then keep doing it while condition is true"

### for Loop

**Best when you know how many times to repeat**:

```java
public class ForLoopExample {
    public static void main(String[] args) {
        // Print numbers 1 to 5
        System.out.println("Counting from 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Count: " + i);
        }
        
        // Print even numbers from 2 to 10
        System.out.println("\nEven numbers from 2 to 10:");
        for (int i = 2; i <= 10; i += 2) {
            System.out.println(i);
        }
        
        // Multiplication table
        int number = 7;
        System.out.println("\nMultiplication table for " + number + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.println(number + " × " + i + " = " + (number * i));
        }
    }
}
```

### Enhanced for Loop (for-each)

**Perfect for arrays and collections**:

```java
public class ForEachExample {
    public static void main(String[] args) {
        // Array of fruits
        String[] fruits = {"Apple", "Banana", "Orange", "Grape"};
        
        System.out.println("Fruits in the basket:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
        
        // Array of numbers
        int[] numbers = {10, 20, 30, 40, 50};
        int sum = 0;
        
        for (int number : numbers) {
            sum += number;
        }
        
        System.out.println("Sum of numbers: " + sum);
    }
}
```

### while Loop

**Keep repeating while condition is true**:

```java
public class WhileLoopExample {
    public static void main(String[] args) {
        // Count down from 5
        int count = 5;
        System.out.println("Countdown:");
        
        while (count > 0) {
            System.out.println(count);
            count--; // Same as count = count - 1
        }
        System.out.println("Blast off! 🚀");
        
        // Find first number divisible by 7 after 50
        int number = 51;
        while (number % 7 != 0) {
            number++;
        }
        System.out.println("First number after 50 divisible by 7: " + number);
    }
}
```

### do-while Loop

**Execute code first, then check condition**:

```java
public class DoWhileExample {
    public static void main(String[] args) {
        // Menu system - always show menu at least once
        int choice;
        
        do {
            System.out.println("\n=== Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Settings");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            
            // Simulating user input
            choice = 2; // In real program, you'd read from user
            
            switch (choice) {
                case 1:
                    System.out.println("Showing profile...");
                    break;
                case 2:
                    System.out.println("Opening settings...");
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
            
            // For demo purposes, we'll break after first iteration
            break;
            
        } while (choice != 3);
    }
}
```

---

## 3. Jump Statements

### break Statement

**Exit from loops or switch statements**:

```java
public class BreakExample {
    public static void main(String[] args) {
        // Find first number greater than 100 that's divisible by 13
        System.out.println("Finding first number > 100 divisible by 13:");
        
        for (int i = 101; i <= 200; i++) {
            if (i % 13 == 0) {
                System.out.println("Found: " + i);
                break; // Exit the loop immediately
            }
        }
        
        // Breaking out of nested loops with labels
        System.out.println("\nSearching in a grid:");
        
        outerLoop:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.println("Checking position (" + i + "," + j + ")");
                
                if (i == 2 && j == 2) {
                    System.out.println("Target found at (2,2)!");
                    break outerLoop; // Breaks out of both loops
                }
            }
        }
    }
}
```

### continue Statement

**Skip current iteration and move to next**:

```java
public class ContinueExample {
    public static void main(String[] args) {
        // Print odd numbers from 1 to 10
        System.out.println("Odd numbers from 1 to 10:");
        
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // Skip even numbers
            }
            System.out.println(i);
        }
        
        // Skip negative numbers in processing
        int[] numbers = {5, -2, 8, -1, 3, -7, 9};
        System.out.println("\nProcessing positive numbers only:");
        
        for (int number : numbers) {
            if (number < 0) {
                System.out.println("Skipping negative number: " + number);
                continue;
            }
            System.out.println("Processing: " + number);
        }
    }
}
```

---

## Real-World Examples

### 1. ATM Machine Simulation

```java
public class ATMSimulation {
    public static void main(String[] args) {
        double balance = 1000.0;
        int pin = 1234;
        int attempts = 0;
        int maxAttempts = 3;
        
        // PIN validation with limited attempts
        boolean accessGranted = false;
        
        while (attempts < maxAttempts && !accessGranted) {
            // Simulating user input
            int enteredPin = (attempts == 0) ? 1111 : 1234; // Wrong first, correct second
            attempts++;
            
            if (enteredPin == pin) {
                accessGranted = true;
                System.out.println("Access granted! Welcome to ATM.");
            } else {
                System.out.println("Invalid PIN. Attempt " + attempts + " of " + maxAttempts);
                if (attempts < maxAttempts) {
                    System.out.println("Please try again.");
                }
            }
        }
        
        if (!accessGranted) {
            System.out.println("Too many failed attempts. Card blocked!");
            return; // Exit the program
        }
        
        // ATM Menu
        int choice = 1; // Simulating user choice
        
        switch (choice) {
            case 1:
                System.out.println("Current Balance: $" + balance);
                break;
            case 2:
                double withdrawAmount = 200.0;
                if (withdrawAmount <= balance) {
                    balance -= withdrawAmount;
                    System.out.println("Withdrawn: $" + withdrawAmount);
                    System.out.println("Remaining Balance: $" + balance);
                } else {
                    System.out.println("Insufficient funds!");
                }
                break;
            case 3:
                double depositAmount = 500.0;
                balance += depositAmount;
                System.out.println("Deposited: $" + depositAmount);
                System.out.println("New Balance: $" + balance);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
}
```

### 2. Grade Calculator with Multiple Students

```java
public class ClassGradeCalculator {
    public static void main(String[] args) {
        // Simulating student scores
        String[] students = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        int[] scores = {92, 78, 85, 67, 94};
        
        System.out.println("=== Class Grade Report ===\n");
        
        int totalStudents = students.length;
        int passedStudents = 0;
        int totalScore = 0;
        char[] grades = new char[totalStudents];
        
        // Calculate individual grades
        for (int i = 0; i < totalStudents; i++) {
            String student = students[i];
            int score = scores[i];
            char grade;
            
            // Determine letter grade
            if (score >= 90) {
                grade = 'A';
            } else if (score >= 80) {
                grade = 'B';
            } else if (score >= 70) {
                grade = 'C';
            } else if (score >= 60) {
                grade = 'D';
            } else {
                grade = 'F';
            }
            
            grades[i] = grade;
            
            // Check if student passed
            if (score >= 60) {
                passedStudents++;
            }
            
            totalScore += score;
            
            // Print individual result
            System.out.println(student + ": " + score + " points - Grade " + grade);
        }
        
        // Calculate class statistics
        double classAverage = (double) totalScore / totalStudents;
        double passRate = (double) passedStudents / totalStudents * 100;
        
        System.out.println("\n=== Class Statistics ===");
        System.out.println("Class Average: " + String.format("%.1f", classAverage));
        System.out.println("Students Passed: " + passedStudents + "/" + totalStudents);
        System.out.println("Pass Rate: " + String.format("%.1f", passRate) + "%");
        
        // Find highest and lowest scores
        int highest = scores[0];
        int lowest = scores[0];
        String topStudent = students[0];
        String bottomStudent = students[0];
        
        for (int i = 1; i < totalStudents; i++) {
            if (scores[i] > highest) {
                highest = scores[i];
                topStudent = students[i];
            }
            if (scores[i] < lowest) {
                lowest = scores[i];
                bottomStudent = students[i];
            }
        }
        
        System.out.println("\nTop Performer: " + topStudent + " (" + highest + " points)");
        System.out.println("Needs Help: " + bottomStudent + " (" + lowest + " points)");
    }
}
```

### 3. Shopping Cart with Discounts

```java
public class ShoppingCartSystem {
    public static void main(String[] args) {
        // Products and prices
        String[] products = {"Laptop", "Mouse", "Keyboard", "Monitor", "Speakers"};
        double[] prices = {999.99, 25.50, 75.00, 299.99, 89.99};
        int[] quantities = {1, 2, 1, 1, 2};
        
        double subtotal = 0;
        
        System.out.println("=== Shopping Cart ===");
        System.out.println("Item\t\tPrice\tQty\tTotal");
        System.out.println("----------------------------------------");
        
        // Calculate subtotal
        for (int i = 0; i < products.length; i++) {
            double itemTotal = prices[i] * quantities[i];
            subtotal += itemTotal;
            
            System.out.printf("%-12s\t$%.2f\t%d\t$%.2f\n", 
                            products[i], prices[i], quantities[i], itemTotal);
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal:\t\t\t\t$%.2f\n", subtotal);
        
        // Apply discounts based on subtotal
        double discount = 0;
        String discountMessage = "";
        
        if (subtotal >= 1000) {
            discount = subtotal * 0.15; // 15% discount
            discountMessage = "Premium Customer Discount (15%)";
        } else if (subtotal >= 500) {
            discount = subtotal * 0.10; // 10% discount  
            discountMessage = "Bulk Purchase Discount (10%)";
        } else if (subtotal >= 200) {
            discount = subtotal * 0.05; // 5% discount
            discountMessage = "Regular Customer Discount (5%)";
        }
        
        if (discount > 0) {
            System.out.printf("Discount (%s):\t\t-$%.2f\n", discountMessage, discount);
        }
        
        // Calculate tax
        double afterDiscount = subtotal - discount;
        double tax = afterDiscount * 0.08; // 8% tax
        double total = afterDiscount + tax;
        
        System.out.printf("Tax (8%%):\t\t\t\t+$%.2f\n", tax);
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL:\t\t\t\t\t$%.2f\n", total);
        
        // Suggest free shipping
        if (total >= 100) {
            System.out.println("\n🎉 Congratulations! You qualify for FREE SHIPPING!");
        } else {
            double needed = 100 - total;
            System.out.printf("\n💡 Add $%.2f more to qualify for free shipping!\n", needed);
        }
    }
}
```

---

## Common Mistakes and Solutions

### 1. Infinite Loops

```java
// ✗ WRONG - Infinite loop
int i = 1;
while (i <= 10) {
    System.out.println(i);
    // Forgot to increment i!
}

// ✓ CORRECT
int i = 1;
while (i <= 10) {
    System.out.println(i);
    i++; // Don't forget to update the condition variable
}
```

### 2. Off-by-One Errors

```java
// ✗ WRONG - Misses the last element
int[] numbers = {1, 2, 3, 4, 5};
for (int i = 0; i < numbers.length - 1; i++) { // Should be: i < numbers.length
    System.out.println(numbers[i]);
}

// ✓ CORRECT
for (int i = 0; i < numbers.length; i++) {
    System.out.println(numbers[i]);
}

// ✓ EVEN BETTER - Use enhanced for loop
for (int number : numbers) {
    System.out.println(number);
}
```

### 3. Missing break in switch

```java
// ✗ WRONG - Fall-through behavior
int grade = 2;
switch (grade) {
    case 1:
        System.out.println("Excellent");
        // Missing break - will execute case 2 as well!
    case 2:
        System.out.println("Good");
        // Missing break - will execute case 3 as well!
    case 3:
        System.out.println("Average");
        break;
}

// ✓ CORRECT
switch (grade) {
    case 1:
        System.out.println("Excellent");
        break;
    case 2:
        System.out.println("Good");
        break;
    case 3:
        System.out.println("Average");
        break;
}
```

## Practice Exercises

### Exercise 1: Number Guessing Game
```java
public class NumberGuessingGame {
    public static void main(String[] args) {
        int secretNumber = 42; // In real game, use Random
        int guess = 30; // Simulate user guesses: 30, 50, 42
        int attempts = 0;
        int maxAttempts = 5;
        
        // Implement the guessing game logic
        // Use while loop with attempts counter
        // Give hints: "too high" or "too low"
        // Congratulate on correct guess or end game after max attempts
    }
}
```

### Exercise 2: Pattern Printing
```java
public class PatternPrinting {
    public static void main(String[] args) {
        // Print this pattern:
        // *
        // **
        // ***
        // ****
        // *****
        
        // Use nested loops
        // Outer loop for rows, inner loop for stars
    }
}
```

### Exercise 3: Simple Banking System
```java
public class SimpleBankingSystem {
    public static void main(String[] args) {
        double balance = 1000.0;
        
        // Implement a menu system with:
        // 1. Check Balance
        // 2. Deposit Money  
        // 3. Withdraw Money
        // 4. Exit
        
        // Use do-while loop for menu
        // Use switch for options
        // Validate withdrawal amount
    }
}
```

## Quick Reference

### Conditional Statements
```java
// if-else
if (condition) {
    // code
} else if (condition2) {
    // code
} else {
    // code
}

// switch
switch (variable) {
    case value1:
        // code
        break;
    case value2:
        // code
        break;
    default:
        // code
}
```

### Loops
```java
// for loop
for (int i = 0; i < 10; i++) {
    // code
}

// enhanced for loop
for (type element : collection) {
    // code
}

// while loop
while (condition) {
    // code
}

// do-while loop
do {
    // code
} while (condition);
```

### Jump Statements
```java
break;      // Exit loop/switch
continue;   // Skip to next iteration
return;     // Exit method
```

## Key Takeaways

1. **if statements** make decisions based on conditions
2. **switch statements** are clean for checking specific values
3. **for loops** are best when you know iteration count
4. **while loops** are best when you check conditions
5. **Enhanced for loops** are perfect for arrays/collections
6. **break** exits loops/switch, **continue** skips iterations
7. **Always update loop variables** to avoid infinite loops
8. **Use meaningful conditions** and proper indentation
9. **Test edge cases** (empty arrays, zero values, etc.)
10. **Choose the right control structure** for each situation