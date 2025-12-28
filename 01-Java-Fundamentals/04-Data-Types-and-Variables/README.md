# Data Types and Variables - Storing Information

## What are Variables?

Think of variables as **labeled boxes** where you store different types of information. Each box has:
- **A name** (variable name)
- **A type** (what kind of data it holds)
- **A value** (the actual data stored)

```
┌─────────────────┐
│    Variable     │
│   Name: age     │
│   Type: int     │
│   Value: 25     │
└─────────────────┘
```

## Java Data Types

Java has **8 primitive data types** - think of them as different types of storage containers:

### 1. Numbers Without Decimals (Integers)

#### byte
- **Size**: 1 byte (8 bits)
- **Range**: -128 to 127
- **Use**: When memory is very important
```java
byte temperature = 25;
byte score = -10;
```

#### short
- **Size**: 2 bytes (16 bits)
- **Range**: -32,768 to 32,767
- **Use**: Small numbers when int is too big
```java
short year = 2024;
short population = 15000;
```

#### int (Most Common)
- **Size**: 4 bytes (32 bits)
- **Range**: -2,147,483,648 to 2,147,483,647
- **Use**: Most common for whole numbers
```java
int age = 25;
int salary = 50000;
int students = 1000;
```

#### long
- **Size**: 8 bytes (64 bits)
- **Range**: Very large numbers (19 digits)
- **Use**: When int is not enough
```java
long worldPopulation = 7800000000L;  // Note the 'L' at the end
long distance = 299792458L;
```

### 2. Numbers With Decimals

#### float
- **Size**: 4 bytes
- **Precision**: ~7 decimal digits
- **Use**: When double is too much memory
```java
float price = 19.99f;  // Note the 'f' at the end
float weight = 65.5f;
```

#### double (Most Common for Decimals)
- **Size**: 8 bytes
- **Precision**: ~15 decimal digits
- **Use**: Most common for decimal numbers
```java
double pi = 3.14159;
double salary = 75000.50;
double temperature = 98.6;
```

### 3. Characters

#### char
- **Size**: 2 bytes
- **Use**: Single characters (letters, digits, symbols)
```java
char grade = 'A';
char symbol = '@';
char digit = '5';
char space = ' ';
```

### 4. True/False Values

#### boolean
- **Size**: 1 bit (but usually 1 byte in practice)
- **Values**: true or false only
- **Use**: Yes/No, On/Off situations
```java
boolean isStudent = true;
boolean isMarried = false;
boolean hasLicense = true;
```

## Data Type Memory Visualization

```
┌─────────┬──────────┬────────────┬─────────────────────┐
│  Type   │   Size   │   Example  │       Range         │
├─────────┼──────────┼────────────┼─────────────────────┤
│  byte   │  1 byte  │     25     │    -128 to 127      │
│  short  │  2 bytes │   2024     │  -32,768 to 32,767  │
│  int    │  4 bytes │  50000     │    -2B to 2B        │
│  long   │  8 bytes │ 7800000L   │   Very large        │
│  float  │  4 bytes │  19.99f    │   ~7 digits         │
│  double │  8 bytes │   3.14     │   ~15 digits        │
│  char   │  2 bytes │    'A'     │  Unicode characters │
│  boolean│  1 bit   │   true     │   true or false     │
└─────────┴──────────┴────────────┴─────────────────────┘
```

## Variable Declaration and Initialization

### Declaring Variables
```java
// Declaration (creating the box)
int age;
String name;
double salary;
```

### Initializing Variables
```java
// Initialization (putting value in the box)
age = 25;
name = "John";
salary = 50000.0;
```

### Declaration + Initialization
```java
// Doing both at once (recommended)
int age = 25;
String name = "John";
double salary = 50000.0;
```

## Reference Data Types

Besides primitive types, Java has **reference types**:

### String (Most Important Reference Type)
```java
String firstName = "John";
String lastName = "Doe";
String fullName = firstName + " " + lastName;
String address = "123 Main Street";
```

### Arrays
```java
int[] numbers = {1, 2, 3, 4, 5};
String[] names = {"John", "Jane", "Bob"};
```

## Variable Examples with Real-World Context

### Student Information System
```java
public class Student {
    public static void main(String[] args) {
        // Student personal info
        String studentName = "Alice Johnson";
        int studentAge = 20;
        char grade = 'A';
        boolean isEnrolled = true;
        
        // Academic info
        double gpa = 3.85;
        int creditHours = 15;
        
        // Financial info
        float tuitionFee = 5000.50f;
        long studentId = 2024001234L;
        
        // Print student information
        System.out.println("=== Student Information ===");
        System.out.println("Name: " + studentName);
        System.out.println("Age: " + studentAge);
        System.out.println("Grade: " + grade);
        System.out.println("Enrolled: " + isEnrolled);
        System.out.println("GPA: " + gpa);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Tuition Fee: $" + tuitionFee);
        System.out.println("Student ID: " + studentId);
    }
}
```

### Banking System
```java
public class BankAccount {
    public static void main(String[] args) {
        // Account information
        long accountNumber = 1234567890123456L;
        String accountHolder = "Robert Smith";
        double balance = 15750.25;
        boolean isActive = true;
        char accountType = 'S';  // S for Savings
        
        // Transaction info
        float interestRate = 2.5f;
        int transactionCount = 45;
        
        System.out.println("=== Bank Account Details ===");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
        System.out.println("Active: " + isActive);
        System.out.println("Account Type: " + accountType);
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Transactions: " + transactionCount);
    }
}
```

## Type Conversion

### Automatic Conversion (Widening)
Java automatically converts smaller types to larger types:
```java
int smallNumber = 100;
long bigNumber = smallNumber;  // Automatic conversion
double decimal = smallNumber;   // Automatic conversion

// Flow: byte → short → int → long → float → double
```

### Manual Conversion (Casting)
Sometimes you need to force conversion:
```java
double decimal = 3.14;
int wholeNumber = (int) decimal;  // Result: 3 (loses decimal part)

long bigNumber = 1000L;
int smallNumber = (int) bigNumber;  // Force conversion
```

### String Conversions
```java
// Number to String
int age = 25;
String ageText = String.valueOf(age);  // "25"

// String to Number
String numberText = "123";
int number = Integer.parseInt(numberText);  // 123
```

## Variable Naming Best Practices

### Good Names (Descriptive)
```java
int studentAge = 20;           // ✓ Clear purpose
double bankBalance = 1500.50;  // ✓ Descriptive
String firstName = "John";     // ✓ Meaningful
boolean isLoggedIn = true;     // ✓ Clear boolean
```

### Poor Names (Confusing)
```java
int a = 20;          // ✗ What does 'a' represent?
double x = 1500.50;  // ✗ Unclear purpose
String s = "John";   // ✗ Not descriptive
boolean b = true;    // ✗ What is 'b'?
```

## Constants - Values That Never Change

Use `final` keyword for constants:
```java
final double PI = 3.14159;
final int MAX_STUDENTS = 30;
final String SCHOOL_NAME = "Java Academy";

// Constants are usually named in ALL_CAPS
```

## Common Mistakes and Solutions

### 1. Using Wrong Data Type
```java
// ✗ Wrong - age should be int
double age = 25.0;

// ✓ Correct
int age = 25;
```

### 2. Forgetting to Initialize
```java
// ✗ Wrong - variable not initialized
int count;
System.out.println(count);  // Error!

// ✓ Correct
int count = 0;
System.out.println(count);
```

### 3. Mixing Up Sizes
```java
// ✗ Potential problem - might overflow
int worldPopulation = 8000000000;  // Too big for int

// ✓ Correct
long worldPopulation = 8000000000L;
```

## Memory Usage Tips

### When to Use Each Type:

**Use int for:**
- Age, count, year, month, day
- Small to medium whole numbers

**Use long for:**
- Population numbers, file sizes
- Very large whole numbers

**Use double for:**
- Money, measurements, calculations
- Any decimal numbers

**Use String for:**
- Names, addresses, text
- Any text data

**Use boolean for:**
- Yes/No questions
- Status flags (active/inactive)

## Practice Exercises

### Exercise 1: Personal Profile
Create variables to store your complete profile:
```java
public class MyProfile {
    public static void main(String[] args) {
        // Create variables for:
        // - Your name (String)
        // - Your age (int)
        // - Your height (double)
        // - Are you a student? (boolean)
        // - Your grade (char)
        // - Your phone number (long)
        
        // Print all information
    }
}
```

### Exercise 2: Shopping Calculator
```java
public class Shopping {
    public static void main(String[] args) {
        // Item prices
        double shirtPrice = 25.99;
        double jeansPrice = 45.50;
        int quantity = 2;
        
        // Calculate total
        // Add tax (8.5%)
        // Print receipt
    }
}
```

### Exercise 3: Temperature Converter
```java
public class Temperature {
    public static void main(String[] args) {
        // Convert Celsius to Fahrenheit
        // Formula: F = (C × 9/5) + 32
        
        double celsius = 25.0;
        // Calculate fahrenheit
        // Print both temperatures
    }
}
```

## Quick Reference Guide

```java
// Most commonly used data types:
int age = 25;              // Whole numbers
double price = 19.99;      // Decimal numbers  
String name = "John";      // Text
boolean isTrue = true;     // true/false
char grade = 'A';          // Single character

// Remember the suffixes:
long bigNumber = 123L;     // L for long
float decimal = 3.14f;     // f for float
```

## Key Takeaways

1. **Choose the right type** for your data
2. **Use meaningful names** for variables
3. **Initialize variables** before using them
4. **int and double** are most commonly used
5. **String** is not primitive but very important
6. **boolean** is perfect for true/false situations
7. **Use constants** for values that never change