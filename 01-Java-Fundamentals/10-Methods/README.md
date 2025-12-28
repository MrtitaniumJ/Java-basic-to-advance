# Methods - Building Reusable Code Blocks

## What are Methods?

Methods are **reusable blocks of code** that perform specific tasks. Think of them as:
- **Mini-programs** within your main program
- **Tools** that you can use whenever needed
- **Functions** that take input, process it, and often return output
- **Building blocks** for organizing code efficiently

```java
// Without methods (repetitive)
System.out.println("Welcome, John!");
System.out.println("Your balance is: $1500");
System.out.println("Thank you for banking with us!");

System.out.println("Welcome, Sarah!");  
System.out.println("Your balance is: $2300");
System.out.println("Thank you for banking with us!");

// With methods (clean and reusable)
displayWelcome("John", 1500);
displayWelcome("Sarah", 2300);
```

## Why Use Methods?

Methods provide several key benefits:
- **Code Reusability**: Write once, use many times
- **Organization**: Break complex problems into smaller pieces  
- **Maintainability**: Fix bugs in one place
- **Readability**: Make code easier to understand
- **Testing**: Test individual pieces of functionality
- **Modularity**: Create independent, interchangeable components

---

## Method Syntax and Structure

### Basic Method Structure
```java
public class MethodBasics {
    // Method components:
    // access_modifier return_type method_name(parameters) {
    //     method_body
    //     return statement; // if return_type is not void
    // }
    
    public static void main(String[] args) {
        // Call methods
        greetUser();
        int result = addNumbers(5, 3);
        System.out.println("Sum: " + result);
        
        displayUserInfo("Alice", 25, "Engineer");
    }
    
    // Method with no parameters, no return value
    public static void greetUser() {
        System.out.println("Hello! Welcome to our application!");
        System.out.println("Have a great day!");
    }
    
    // Method with parameters, with return value
    public static int addNumbers(int a, int b) {
        int sum = a + b;
        return sum; // Return the result
    }
    
    // Method with multiple parameters
    public static void displayUserInfo(String name, int age, String profession) {
        System.out.println("User Information:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Profession: " + profession);
        System.out.println("-------------------");
    }
}
```

### Access Modifiers
```java
public class AccessModifiers {
    // public - accessible from anywhere
    public static void publicMethod() {
        System.out.println("This is a public method");
    }
    
    // private - only accessible within the same class
    private static void privateMethod() {
        System.out.println("This is a private method");
    }
    
    // protected - accessible within same package and subclasses
    protected static void protectedMethod() {
        System.out.println("This is a protected method");
    }
    
    // default (no modifier) - accessible within same package
    static void defaultMethod() {
        System.out.println("This is a default method");
    }
    
    public static void main(String[] args) {
        publicMethod();     // ✓ Works
        privateMethod();    // ✓ Works (same class)
        protectedMethod();  // ✓ Works (same class)
        defaultMethod();    // ✓ Works (same class)
    }
}
```

---

## Method Parameters and Arguments

### 1. Different Parameter Types
```java
public class ParameterTypes {
    public static void main(String[] args) {
        // Primitive parameters
        calculateArea(10.5, 20.3);
        
        // String parameter
        greetByName("John");
        
        // Array parameter
        int[] numbers = {1, 2, 3, 4, 5};
        printArray(numbers);
        
        // Multiple mixed parameters
        createAccount("Alice", 25, 1500.75, true);
    }
    
    // Method with double parameters
    public static void calculateArea(double length, double width) {
        double area = length * width;
        System.out.printf("Area: %.2f square units\n", area);
    }
    
    // Method with String parameter
    public static void greetByName(String name) {
        System.out.println("Hello, " + name + "! Nice to meet you!");
    }
    
    // Method with array parameter
    public static void printArray(int[] arr) {
        System.out.print("Array contents: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    // Method with multiple parameter types
    public static void createAccount(String name, int age, double balance, boolean isPremium) {
        System.out.println("Creating account...");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.printf("Initial Balance: $%.2f\n", balance);
        System.out.println("Premium Member: " + (isPremium ? "Yes" : "No"));
        System.out.println("Account created successfully!\n");
    }
}
```

### 2. Variable Arguments (Varargs)
```java
public class VarargsExample {
    public static void main(String[] args) {
        // Call with different number of arguments
        calculateSum();                    // No arguments
        calculateSum(5);                   // One argument
        calculateSum(1, 2, 3);            // Multiple arguments
        calculateSum(10, 20, 30, 40, 50); // Many arguments
        
        // Mix regular parameters with varargs
        displayResults("Test Scores", 85, 92, 78, 90, 88);
    }
    
    // Varargs method - accepts variable number of integers
    public static void calculateSum(int... numbers) {
        if (numbers.length == 0) {
            System.out.println("No numbers provided. Sum = 0");
            return;
        }
        
        int sum = 0;
        System.out.print("Numbers: ");
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            System.out.print(numbers[i]);
            if (i < numbers.length - 1) {
                System.out.print(" + ");
            }
        }
        System.out.println(" = " + sum);
    }
    
    // Regular parameter + varargs (varargs must be last)
    public static void displayResults(String title, int... scores) {
        System.out.println("\n" + title + ":");
        
        if (scores.length == 0) {
            System.out.println("No scores available.");
            return;
        }
        
        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Score " + (i + 1) + ": " + scores[i]);
            total += scores[i];
        }
        
        double average = (double) total / scores.length;
        System.out.printf("Average: %.2f\n", average);
        System.out.println();
    }
}
```

---

## Return Types and Return Statements

### 1. Methods with Different Return Types
```java
public class ReturnTypes {
    public static void main(String[] args) {
        // Methods that return values
        int sum = add(10, 5);
        System.out.println("Sum: " + sum);
        
        double average = calculateAverage(85, 90, 78, 92);
        System.out.println("Average: " + average);
        
        String greeting = generateGreeting("Alice", "morning");
        System.out.println(greeting);
        
        boolean isPassing = isPassingGrade(75);
        System.out.println("Is passing: " + isPassing);
        
        int[] evenNumbers = getEvenNumbers(1, 10);
        System.out.print("Even numbers: ");
        for (int num : evenNumbers) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    // Return integer
    public static int add(int a, int b) {
        return a + b;
    }
    
    // Return double
    public static double calculateAverage(int... scores) {
        if (scores.length == 0) {
            return 0.0;
        }
        
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        
        return (double) sum / scores.length;
    }
    
    // Return String
    public static String generateGreeting(String name, String timeOfDay) {
        String greeting;
        
        switch (timeOfDay.toLowerCase()) {
            case "morning":
                greeting = "Good morning";
                break;
            case "afternoon":
                greeting = "Good afternoon";
                break;
            case "evening":
                greeting = "Good evening";
                break;
            default:
                greeting = "Hello";
        }
        
        return greeting + ", " + name + "!";
    }
    
    // Return boolean
    public static boolean isPassingGrade(int score) {
        return score >= 60; // Return true if score is 60 or higher
    }
    
    // Return array
    public static int[] getEvenNumbers(int start, int end) {
        // Count even numbers first
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                count++;
            }
        }
        
        // Create array and populate with even numbers
        int[] evenNumbers = new int[count];
        int index = 0;
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                evenNumbers[index] = i;
                index++;
            }
        }
        
        return evenNumbers;
    }
}
```

### 2. Multiple Return Statements
```java
public class MultipleReturns {
    public static void main(String[] args) {
        System.out.println("Grade A: " + getLetterGrade(95));
        System.out.println("Grade B: " + getLetterGrade(85));
        System.out.println("Grade C: " + getLetterGrade(75));
        System.out.println("Grade F: " + getLetterGrade(45));
        
        System.out.println("Max of 10, 5: " + findMaximum(10, 5));
        System.out.println("Max of 3, 8: " + findMaximum(3, 8));
        
        validateAge(25);
        validateAge(-5);
        validateAge(150);
    }
    
    // Multiple return statements based on conditions
    public static String getLetterGrade(int score) {
        if (score >= 90) {
            return "A"; // Early return
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F"; // Default return
    }
    
    // Early return for efficiency
    public static int findMaximum(int a, int b) {
        if (a >= b) {
            return a; // Return immediately if a is larger
        }
        return b; // Only reached if a < b
    }
    
    // Void method with early return
    public static void validateAge(int age) {
        if (age < 0) {
            System.out.println("Error: Age cannot be negative!");
            return; // Exit method early
        }
        
        if (age > 120) {
            System.out.println("Error: Age seems unrealistic!");
            return; // Exit method early
        }
        
        // This code only runs if age is valid
        System.out.println("Valid age: " + age);
    }
}
```

---

## Method Overloading

### Method Overloading Examples
```java
public class MethodOverloading {
    public static void main(String[] args) {
        // Same method name, different parameters
        System.out.println("Add two integers: " + add(5, 3));
        System.out.println("Add three integers: " + add(5, 3, 2));
        System.out.println("Add two doubles: " + add(5.5, 3.2));
        System.out.println("Add integer and double: " + add(5, 3.2));
        
        // Overloaded display methods
        displayInfo("John");
        displayInfo("Alice", 25);
        displayInfo("Bob", 30, "Engineer");
        
        // Overloaded calculation methods
        System.out.println("Circle area: " + calculateArea(5.0));
        System.out.println("Rectangle area: " + calculateArea(4.0, 6.0));
        System.out.println("Triangle area: " + calculateArea(3.0, 4.0, 5.0));
    }
    
    // Overloaded add methods - different number of parameters
    public static int add(int a, int b) {
        System.out.println("Adding two integers");
        return a + b;
    }
    
    public static int add(int a, int b, int c) {
        System.out.println("Adding three integers");
        return a + b + c;
    }
    
    // Overloaded add methods - different parameter types
    public static double add(double a, double b) {
        System.out.println("Adding two doubles");
        return a + b;
    }
    
    public static double add(int a, double b) {
        System.out.println("Adding integer and double");
        return a + b;
    }
    
    // Overloaded display methods
    public static void displayInfo(String name) {
        System.out.println("Name: " + name);
    }
    
    public static void displayInfo(String name, int age) {
        System.out.println("Name: " + name + ", Age: " + age);
    }
    
    public static void displayInfo(String name, int age, String profession) {
        System.out.println("Name: " + name + ", Age: " + age + ", Profession: " + profession);
    }
    
    // Overloaded area calculation methods
    public static double calculateArea(double radius) {
        // Circle area: π * r²
        return Math.PI * radius * radius;
    }
    
    public static double calculateArea(double length, double width) {
        // Rectangle area: length * width
        return length * width;
    }
    
    public static double calculateArea(double a, double b, double c) {
        // Triangle area using Heron's formula
        double s = (a + b + c) / 2; // semi-perimeter
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}
```

---

## Scope and Lifetime

### Variable Scope in Methods
```java
public class VariableScope {
    // Class variable (accessible to all methods in the class)
    private static int classVariable = 100;
    
    public static void main(String[] args) {
        // Local variable in main method
        int mainVariable = 10;
        System.out.println("Main variable: " + mainVariable);
        System.out.println("Class variable: " + classVariable);
        
        // Call method with parameter
        demonstrateScope(20);
        
        // mainVariable is still accessible here
        System.out.println("Main variable after method call: " + mainVariable);
        
        // Demonstrate block scope
        demonstrateBlockScope();
    }
    
    public static void demonstrateScope(int parameter) {
        // Parameter and local variables
        int localVariable = 30;
        
        System.out.println("Inside demonstrateScope:");
        System.out.println("Parameter: " + parameter);
        System.out.println("Local variable: " + localVariable);
        System.out.println("Class variable: " + classVariable);
        
        // Modify class variable
        classVariable = 200;
        System.out.println("Modified class variable: " + classVariable);
        
        // Cannot access mainVariable here - it's out of scope
        // System.out.println(mainVariable); // This would cause an error
    }
    
    public static void demonstrateBlockScope() {
        System.out.println("\nDemonstrating block scope:");
        
        int outerVariable = 40;
        
        if (true) {
            // Block scope
            int blockVariable = 50;
            System.out.println("Inside block - outer variable: " + outerVariable);
            System.out.println("Inside block - block variable: " + blockVariable);
            
            // Can create new variable with different name
            int anotherBlockVariable = 60;
            System.out.println("Another block variable: " + anotherBlockVariable);
        }
        
        // blockVariable is not accessible here
        // System.out.println(blockVariable); // This would cause an error
        
        System.out.println("Outside block - outer variable: " + outerVariable);
    }
}
```

---

## Real-World Method Examples

### 1. Banking System Methods
```java
public class BankingSystem {
    public static void main(String[] args) {
        // Simulate banking operations
        double currentBalance = 1000.0;
        
        System.out.printf("Initial Balance: $%.2f\n", currentBalance);
        
        // Perform transactions
        currentBalance = deposit(currentBalance, 250.0);
        currentBalance = withdraw(currentBalance, 100.0);
        currentBalance = withdraw(currentBalance, 2000.0); // Should fail
        
        // Calculate interest
        double newBalance = calculateInterest(currentBalance, 0.03, 12);
        System.out.printf("Balance after 1 year with 3%% interest: $%.2f\n", newBalance);
        
        // Validate account details
        boolean isValidAccount = validateAccount("12345678", "1234");
        System.out.println("Account validation: " + (isValidAccount ? "Success" : "Failed"));
    }
    
    public static double deposit(double currentBalance, double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive!");
            return currentBalance;
        }
        
        double newBalance = currentBalance + amount;
        System.out.printf("Deposited $%.2f. New balance: $%.2f\n", amount, newBalance);
        return newBalance;
    }
    
    public static double withdraw(double currentBalance, double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive!");
            return currentBalance;
        }
        
        if (amount > currentBalance) {
            System.out.printf("Error: Insufficient funds! Cannot withdraw $%.2f (Balance: $%.2f)\n", 
                            amount, currentBalance);
            return currentBalance;
        }
        
        double newBalance = currentBalance - amount;
        System.out.printf("Withdrew $%.2f. New balance: $%.2f\n", amount, newBalance);
        return newBalance;
    }
    
    public static double calculateInterest(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12;
        double finalAmount = principal * Math.pow(1 + monthlyRate, months);
        double interest = finalAmount - principal;
        
        System.out.printf("Interest calculation:\n");
        System.out.printf("Principal: $%.2f\n", principal);
        System.out.printf("Annual rate: %.2f%%\n", annualRate * 100);
        System.out.printf("Time: %d months\n", months);
        System.out.printf("Interest earned: $%.2f\n", interest);
        
        return finalAmount;
    }
    
    public static boolean validateAccount(String accountNumber, String pin) {
        // Simple validation (in real systems, this would check against a database)
        boolean validAccountNumber = accountNumber.length() == 8 && 
                                   accountNumber.matches("\\d{8}");
        boolean validPin = pin.length() == 4 && pin.matches("\\d{4}");
        
        if (!validAccountNumber) {
            System.out.println("Error: Account number must be 8 digits");
        }
        if (!validPin) {
            System.out.println("Error: PIN must be 4 digits");
        }
        
        return validAccountNumber && validPin;
    }
}
```

### 2. Text Processing Utility Methods
```java
public class TextUtils {
    public static void main(String[] args) {
        String sampleText = "  Hello World! This is a SAMPLE text for processing.  ";
        
        // Demonstrate text processing methods
        System.out.println("Original: '" + sampleText + "'");
        System.out.println("Cleaned: '" + cleanText(sampleText) + "'");
        System.out.println("Word count: " + countWords(sampleText));
        System.out.println("Capitalized: '" + capitalizeWords(sampleText) + "'");
        System.out.println("Reversed: '" + reverseString(sampleText.trim()) + "'");
        System.out.println("Is palindrome 'racecar': " + isPalindrome("racecar"));
        System.out.println("Is palindrome 'hello': " + isPalindrome("hello"));
        
        // Password strength
        testPasswordStrength("weak");
        testPasswordStrength("StrongPass123!");
        
        // Text analysis
        analyzeText("The quick brown fox jumps over the lazy dog.");
    }
    
    public static String cleanText(String text) {
        if (text == null) {
            return "";
        }
        
        // Remove leading/trailing spaces and normalize internal spaces
        return text.trim().replaceAll("\\s+", " ");
    }
    
    public static int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        
        String cleanedText = cleanText(text);
        String[] words = cleanedText.split("\\s+");
        return words.length;
    }
    
    public static String capitalizeWords(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        String[] words = cleanText(text).split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.length() > 0) {
                // Capitalize first letter, lowercase the rest
                String capitalizedWord = word.substring(0, 1).toUpperCase() + 
                                       word.substring(1).toLowerCase();
                result.append(capitalizedWord);
                
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }
        
        return result.toString();
    }
    
    public static String reverseString(String text) {
        if (text == null) {
            return null;
        }
        
        StringBuilder reversed = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            reversed.append(text.charAt(i));
        }
        
        return reversed.toString();
    }
    
    public static boolean isPalindrome(String text) {
        if (text == null) {
            return false;
        }
        
        // Convert to lowercase and remove non-letter characters
        String cleaned = text.toLowerCase().replaceAll("[^a-z]", "");
        
        int left = 0;
        int right = cleaned.length() - 1;
        
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    public static int getPasswordStrength(String password) {
        if (password == null) return 0;
        
        int strength = 0;
        
        // Check length
        if (password.length() >= 8) strength++;
        if (password.length() >= 12) strength++;
        
        // Check character types
        if (password.matches(".*[a-z].*")) strength++; // Lowercase
        if (password.matches(".*[A-Z].*")) strength++; // Uppercase
        if (password.matches(".*\\d.*")) strength++;   // Digits
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) strength++; // Special chars
        
        return strength;
    }
    
    public static void testPasswordStrength(String password) {
        int strength = getPasswordStrength(password);
        String level;
        
        switch (strength) {
            case 0:
            case 1:
                level = "Very Weak";
                break;
            case 2:
            case 3:
                level = "Weak";
                break;
            case 4:
                level = "Moderate";
                break;
            case 5:
                level = "Strong";
                break;
            case 6:
                level = "Very Strong";
                break;
            default:
                level = "Unknown";
        }
        
        System.out.printf("Password '%s' strength: %s (%d/6)\n", password, level, strength);
    }
    
    public static void analyzeText(String text) {
        if (text == null) {
            System.out.println("Cannot analyze null text");
            return;
        }
        
        System.out.println("\nText Analysis:");
        System.out.println("Text: " + text);
        System.out.println("Length: " + text.length() + " characters");
        System.out.println("Words: " + countWords(text));
        
        // Count vowels and consonants
        int vowels = 0, consonants = 0;
        String lowerText = text.toLowerCase();
        
        for (char c : lowerText.toCharArray()) {
            if (Character.isLetter(c)) {
                if ("aeiou".indexOf(c) != -1) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        
        System.out.println("Vowels: " + vowels);
        System.out.println("Consonants: " + consonants);
        System.out.println();
    }
}
```

### 3. Math Utility Methods
```java
public class MathUtils {
    public static void main(String[] args) {
        // Test mathematical utility methods
        System.out.println("Factorial of 5: " + factorial(5));
        System.out.println("Fibonacci of 10: " + fibonacci(10));
        System.out.println("GCD of 48 and 18: " + gcd(48, 18));
        System.out.println("LCM of 12 and 8: " + lcm(12, 8));
        
        System.out.println("Is 17 prime? " + isPrime(17));
        System.out.println("Is 15 prime? " + isPrime(15));
        
        // Power calculation
        System.out.println("2^10 = " + power(2, 10));
        System.out.println("3.5^2 = " + power(3.5, 2));
        
        // Statistical functions
        int[] numbers = {5, 10, 15, 20, 25, 30};
        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Sum: " + sum(numbers));
        System.out.println("Average: " + average(numbers));
        System.out.println("Max: " + max(numbers));
        System.out.println("Min: " + min(numbers));
    }
    
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        
        if (n == 0 || n == 1) {
            return 1;
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        
        return result;
    }
    
    public static int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci is not defined for negative numbers");
        }
        
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        int prev1 = 0, prev2 = 1;
        int current = 0;
        
        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        
        return current;
    }
    
    public static int gcd(int a, int b) {
        // Euclidean algorithm for Greatest Common Divisor
        a = Math.abs(a);
        b = Math.abs(b);
        
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        
        return a;
    }
    
    public static int lcm(int a, int b) {
        // Least Common Multiple = (a * b) / GCD(a, b)
        if (a == 0 || b == 0) {
            return 0;
        }
        
        return Math.abs(a * b) / gcd(a, b);
    }
    
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        
        if (n == 2) {
            return true;
        }
        
        if (n % 2 == 0) {
            return false;
        }
        
        // Check odd divisors up to sqrt(n)
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static double power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        
        if (exponent < 0) {
            return 1.0 / power(base, -exponent);
        }
        
        double result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        
        return result;
    }
    
    // Statistical methods for arrays
    public static int sum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        
        int total = 0;
        for (int num : array) {
            total += num;
        }
        
        return total;
    }
    
    public static double average(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        
        return (double) sum(array) / array.length;
    }
    
    public static int max(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int maximum = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maximum) {
                maximum = array[i];
            }
        }
        
        return maximum;
    }
    
    public static int min(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int minimum = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minimum) {
                minimum = array[i];
            }
        }
        
        return minimum;
    }
}
```

---

## Common Method Patterns

### 1. Validation Methods
```java
public class ValidationMethods {
    public static boolean isValidEmail(String email) {
        return email != null && 
               email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    
    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 120;
    }
    
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && 
               phone.matches("\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}");
    }
}
```

### 2. Utility Methods
```java
public class UtilityMethods {
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
    
    public static String truncateString(String str, int maxLength) {
        if (str == null) return null;
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
```

### 3. Conversion Methods
```java
public class ConversionMethods {
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }
    
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5.0 / 9.0;
    }
    
    public static int binaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }
}
```

---

## Practice Exercises

### Exercise 1: Calculator Methods
```java
public class Calculator {
    public static void main(String[] args) {
        // Implement a calculator with these methods:
        // add(double, double)
        // subtract(double, double)  
        // multiply(double, double)
        // divide(double, double) - handle division by zero
        // percentage(double, double) - calculate percentage
        // power(double, int) - raise to power
        
        // Test all methods with sample values
    }
}
```

### Exercise 2: String Processor
```java
public class StringProcessor {
    public static void main(String[] args) {
        // Implement these string processing methods:
        // removeVowels(String) - remove all vowels
        // countCharacter(String, char) - count occurrences of character
        // reverseWords(String) - reverse each word but keep word order
        // caesarCipher(String, int) - shift characters by n positions
        // removeExtraSpaces(String) - normalize spacing
        
        String test = "Hello World Programming";
        // Test all methods with the test string
    }
}
```

### Exercise 3: Number Analytics
```java
public class NumberAnalytics {
    public static void main(String[] args) {
        // Implement these number analysis methods:
        // isEven(int), isOdd(int)
        // isPerfectNumber(int) - sum of factors equals number
        // reverseNumber(int) - reverse digits
        // sumOfDigits(int) - sum all digits
        // isPalindromic(int) - reads same forwards and backwards
        // getNthPrime(int) - get the nth prime number
        
        // Test with various numbers
    }
}
```

## Method Design Best Practices

### 1. Single Responsibility
```java
// ✓ GOOD - Each method does one thing
public static double calculateArea(double radius) {
    return Math.PI * radius * radius;
}

public static void printArea(double area) {
    System.out.printf("Area: %.2f\n", area);
}

// ✗ POOR - Method does too many things
public static void calculateAndPrintArea(double radius) {
    double area = Math.PI * radius * radius;
    System.out.printf("Area: %.2f\n", area);
    // Also logging, validation, formatting...
}
```

### 2. Clear Method Names
```java
// ✓ GOOD - Clear, descriptive names
public static boolean isValidPassword(String password) { ... }
public static double calculateMonthlyPayment(double principal, double rate, int months) { ... }
public static String formatPhoneNumber(String phone) { ... }

// ✗ POOR - Unclear names
public static boolean check(String s) { ... }
public static double calc(double a, double b, int c) { ... }
public static String format(String s) { ... }
```

### 3. Input Validation
```java
public static double divide(double dividend, double divisor) {
    if (divisor == 0) {
        throw new IllegalArgumentException("Cannot divide by zero");
    }
    return dividend / divisor;
}

public static String capitalizeFirstLetter(String text) {
    if (text == null || text.isEmpty()) {
        return text;
    }
    return text.substring(0, 1).toUpperCase() + text.substring(1);
}
```

## Key Takeaways

1. **Methods break code into manageable, reusable pieces**
2. **Choose descriptive names** that clearly indicate what the method does
3. **Keep methods focused** on a single responsibility
4. **Use parameters** to make methods flexible and reusable
5. **Return values** when the method produces a result
6. **Method overloading** allows multiple versions with different parameters
7. **Validate inputs** to prevent errors and unexpected behavior
8. **Organize related methods** into logical groups or classes
9. **Write methods that are easy to test** independently
10. **Good methods make code more readable, maintainable, and debuggable**

Methods are fundamental building blocks that transform scattered code into organized, professional programs!