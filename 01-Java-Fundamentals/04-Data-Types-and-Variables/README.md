# Data Types and Variables - The Building Blocks

## Simple Explanation

Think of **variables** as **labeled boxes** where you store different types of things:
- A **small box** for numbers (int)
- A **medium box** for bigger numbers (long)
- A **transparent box** for true/false (boolean)
- A **text box** for words (String)

**Data types** tell Java what **size and type of box** you need.

### Real-World Analogy
Imagine you're organizing your closet:
- **Drawer for socks** (small items) = `byte`
- **Shelf for books** (medium items) = `int`
- **Wardrobe for clothes** (large items) = `long`
- **Switch for lights** (on/off) = `boolean`

## Professional Definition

**Data Types** define the type of data that can be stored in a variable. They specify the size and type of values that can be stored, along with the operations that can be performed on them.

**Variables** are named storage locations in memory that hold data values. They act as containers for storing data values during program execution.

## Types of Data Types in Java

### Overview:
```
Data Types
├── Primitive Types (8 types)
│   ├── Numeric
│   │   ├── Integer (byte, short, int, long)
│   │   └── Floating Point (float, double)
│   ├── Character (char)
│   └── Boolean (boolean)
└── Non-Primitive Types
    ├── String
    ├── Arrays
    ├── Classes
    └── Interfaces
```

## 1. Primitive Data Types (8 Types)

### Integer Types:

#### byte
```java
// Size: 1 byte (8 bits)
// Range: -128 to 127
byte temperature = -10;
byte score = 95;

// Real-world use: Small numbers, saving memory
byte monthNumber = 12;
```

**When to use**: Very small numbers, when memory is critical.

#### short
```java
// Size: 2 bytes (16 bits)
// Range: -32,768 to 32,767
short year = 2024;
short population = 25000;

// Real-world use: Medium-range numbers
short salary = 30000;  // In some countries
```

**When to use**: Numbers that don't fit in byte but are smaller than int.

#### int (Most Common)
```java
// Size: 4 bytes (32 bits)
// Range: -2,147,483,648 to 2,147,483,647
int age = 25;
int salary = 50000;
int distance = 1500;

// Real-world use: Most common integer type
int studentCount = 500;
int price = 299;
```

**When to use**: Default choice for integer values.

#### long
```java
// Size: 8 bytes (64 bits)
// Range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
long population = 1400000000L;  // Note the 'L' suffix
long fileSize = 2147483648L;
long microseconds = 1609459200000L;

// Real-world use: Very large numbers
long nationalDebt = 28000000000000L;
```

**When to use**: Very large numbers that exceed int range.

### Floating Point Types:

#### float
```java
// Size: 4 bytes (32 bits)
// Precision: ~7 decimal digits
float price = 99.99f;  // Note the 'f' suffix
float percentage = 75.5f;
float pi = 3.14f;

// Real-world use: When precision is not critical
float temperature = 98.6f;
```

**When to use**: Decimal numbers with moderate precision.

#### double (Most Common for Decimals)
```java
// Size: 8 bytes (64 bits)
// Precision: ~15-17 decimal digits
double salary = 75000.50;
double pi = 3.141592653589793;
double distance = 384400.0;  // Earth to Moon in km

// Real-world use: Scientific calculations, financial data
double accountBalance = 25000.75;
```

**When to use**: Default choice for decimal values, high precision needed.

### Character Type:

#### char
```java
// Size: 2 bytes (16 bits)
// Range: 0 to 65,535 (Unicode characters)
char grade = 'A';
char symbol = '@';
char letter = 'Z';

// Unicode characters
char heart = '\u2665';  // ♥
char smiley = '\u263A'; // ☺

// Real-world use: Single characters, grades, symbols
char gender = 'M';  // Male/Female
```

**When to use**: Single characters, grades, symbols.

### Boolean Type:

#### boolean
```java
// Size: 1 bit (but JVM implementation varies)
// Values: true or false only
boolean isStudentActive = true;
boolean hasPassport = false;
boolean isMarried = true;

// Real-world use: Yes/No decisions, flags
boolean isLoggedIn = false;
boolean hasPermission = true;
```

**When to use**: True/false conditions, flags, switches.

## 2. Non-Primitive Data Types

### String (Most Important)
```java
// Not a primitive type, but very commonly used
String name = "John Doe";
String address = "123 Main Street";
String email = "john@example.com";

// String operations
String firstName = "John";
String lastName = "Doe";
String fullName = firstName + " " + lastName;  // Concatenation
```

## Variable Declaration and Initialization

### Simple Explanation
**Declaring** a variable is like **putting a label on an empty box**.
**Initializing** is like **putting something inside the box**.

### Syntax:
```java
// Declaration only
dataType variableName;

// Declaration with initialization
dataType variableName = value;

// Multiple declarations
dataType var1, var2, var3;
```

### Examples:

#### Declaration Only:
```java
int age;           // Box labeled 'age' is empty
String name;       // Box labeled 'name' is empty
boolean isActive;  // Box labeled 'isActive' is empty

// Later initialization
age = 25;          // Put 25 in the 'age' box
name = "Alice";    // Put "Alice" in the 'name' box
isActive = true;   // Put true in the 'isActive' box
```

#### Declaration with Initialization:
```java
int age = 25;                    // Create box and put 25 in it
String name = "Alice";           // Create box and put "Alice" in it
boolean isActive = true;         // Create box and put true in it
double salary = 50000.75;       // Create box and put 50000.75 in it
```

#### Multiple Declarations:
```java
// Same type, multiple variables
int x, y, z;
int a = 10, b = 20, c = 30;

// Different lines for clarity
int length = 100;
int width = 50;
int height = 25;
```

## Memory Representation

### Visual Memory Layout:
```
Memory Address    Variable Name    Data Type    Value
──────────────    ─────────────    ─────────    ─────
1001             age              int          25
1002             salary           double       50000.75
1003             name             String       "Alice"
1004             isActive         boolean      true
1005             grade            char         'A'
```

### Size Comparison:
```
Data Type    Size        Example Value           Memory Usage
─────────    ────        ─────────────           ────────────
byte         1 byte      127                     ████
short        2 bytes     32000                   ████████
int          4 bytes     2000000                 ████████████████
long         8 bytes     9000000000L             ████████████████████████████████
float        4 bytes     3.14f                   ████████████████
double       8 bytes     3.141592653             ████████████████████████████████
char         2 bytes     'A'                     ████████
boolean      1 bit*      true                    ████
```

## Type Conversion

### 1. Implicit Conversion (Automatic)
```java
// Smaller to larger (safe conversion)
byte b = 10;
int i = b;        // byte → int (automatic)

short s = 100;
long l = s;       // short → long (automatic)

int num = 25;
double d = num;   // int → double (automatic)

// Conversion hierarchy (smaller → larger)
byte → short → int → long → float → double
```

### 2. Explicit Conversion (Manual Casting)
```java
// Larger to smaller (data loss possible)
double d = 98.76;
int i = (int) d;         // i = 98 (decimal part lost)

long l = 1000L;
int num = (int) l;       // Explicit cast needed

float f = 12.34f;
int x = (int) f;         // x = 12 (decimal part lost)
```

## Constants (final keyword)

### Simple Explanation
Constants are like **sealed boxes** - once you put something in, you can't change it.

### Examples:
```java
// Constants (cannot be changed)
final int MAX_STUDENTS = 100;
final double PI = 3.14159;
final String UNIVERSITY_NAME = "ABC University";
final boolean DEBUG_MODE = true;

// Naming convention: ALL_CAPS with underscores
final int MIN_AGE = 18;
final double TAX_RATE = 0.15;
```

### Benefits of Constants:
1. **No accidental changes**
2. **Better readability**
3. **Easy maintenance**
4. **Memory optimization**

## Variable Scope

### Simple Explanation
**Scope** is like **where you can use your labeled boxes**. Some boxes are available everywhere, some only in specific rooms.

### Types of Scope:

#### 1. Local Variables (Method Scope)
```java
public class Student {
    public void calculateGrade() {
        int marks = 85;        // Local variable
        char grade = 'B';      // Local variable
        
        // marks and grade only available inside this method
        System.out.println(marks);
    }
    
    public void anotherMethod() {
        // marks is not available here!
        // System.out.println(marks);  // Error!
    }
}
```

#### 2. Instance Variables (Class Scope)
```java
public class Student {
    // Instance variables - available to all methods
    private String name;
    private int age;
    private double gpa;
    
    public void setInfo() {
        name = "Alice";    // Can access instance variables
        age = 20;
    }
    
    public void displayInfo() {
        System.out.println(name);  // Can access instance variables
        System.out.println(age);
    }
}
```

#### 3. Class Variables (Static)
```java
public class Student {
    // Class variable - shared by all instances
    private static int totalStudents = 0;
    
    public Student() {
        totalStudents++;   // Increment for each student created
    }
    
    public static int getTotalStudents() {
        return totalStudents;
    }
}
```

## Interview Questions & Answers

**Q1: What's the difference between primitive and non-primitive data types?**
**A:**
- **Primitive**: Store actual values, fixed size, faster access (int, double, boolean)
- **Non-primitive**: Store references to objects, variable size, more memory (String, Arrays)

**Q2: Why do we use 'f' suffix with float and 'L' with long?**
**A:**
- `float`: Java treats decimal numbers as double by default, 'f' tells it to treat as float
- `long`: Java treats integers as int by default, 'L' tells it to treat as long

**Q3: What happens if you don't initialize a variable?**
**A:**
- **Local variables**: Compilation error - must initialize before use
- **Instance variables**: Get default values (0 for numbers, false for boolean, null for objects)

**Q4: Difference between int and Integer?**
**A:**
- `int`: Primitive data type, stores actual value
- `Integer`: Wrapper class (object), can be null, has methods

**Q5: What is the size of boolean in Java?**
**A:** Not precisely defined in JVM specification. Logically 1 bit, but JVM implementation may use 1 byte for efficiency.

## Complete Example Program

```java
/**
 * Comprehensive example demonstrating data types and variables
 */
public class DataTypesExample {
    // Class constants
    private static final String SCHOOL_NAME = "Java High School";
    private static final int MAX_CAPACITY = 1000;
    
    // Instance variables
    private String studentName;
    private int studentAge;
    private double gpa;
    private boolean isEnrolled;
    
    public static void main(String[] args) {
        // Primitive data types examples
        demonstratePrimitiveTypes();
        
        // Variable scope examples
        demonstrateVariableScope();
        
        // Type conversion examples
        demonstrateTypeConversion();
    }
    
    public static void demonstratePrimitiveTypes() {
        System.out.println("=== Primitive Data Types ===");
        
        // Integer types
        byte temperature = -5;
        short year = 2024;
        int population = 50000;
        long nationalDebt = 28000000000L;
        
        // Floating point types
        float price = 99.99f;
        double scientificValue = 3.141592653589793;
        
        // Character and boolean
        char grade = 'A';
        boolean isActive = true;
        
        // String (non-primitive)
        String message = "Learning Java Data Types!";
        
        // Display all values
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Year: " + year);
        System.out.println("Population: " + population);
        System.out.println("National Debt: $" + nationalDebt);
        System.out.println("Price: $" + price);
        System.out.println("Scientific Value: " + scientificValue);
        System.out.println("Grade: " + grade);
        System.out.println("Is Active: " + isActive);
        System.out.println("Message: " + message);
        System.out.println();
    }
    
    public static void demonstrateVariableScope() {
        System.out.println("=== Variable Scope ===");
        
        // Local variables
        int localVariable = 100;
        
        if (true) {
            int blockVariable = 200;
            System.out.println("Local Variable: " + localVariable);
            System.out.println("Block Variable: " + blockVariable);
        }
        
        // blockVariable is not accessible here
        System.out.println("Local Variable: " + localVariable);
        System.out.println();
    }
    
    public static void demonstrateTypeConversion() {
        System.out.println("=== Type Conversion ===");
        
        // Implicit conversion (widening)
        int intValue = 100;
        long longValue = intValue;          // int → long
        double doubleValue = intValue;      // int → double
        
        System.out.println("Original int: " + intValue);
        System.out.println("Converted to long: " + longValue);
        System.out.println("Converted to double: " + doubleValue);
        
        // Explicit conversion (narrowing)
        double originalDouble = 98.76;
        int convertedInt = (int) originalDouble;  // Explicit cast
        
        System.out.println("Original double: " + originalDouble);
        System.out.println("Converted to int: " + convertedInt);
        System.out.println("(Note: Decimal part is lost)");
    }
}
```

## Key Takeaways

1. **8 Primitive Types**: byte, short, int, long, float, double, char, boolean
2. **int and double** are most commonly used
3. **Variables are labeled storage boxes** in memory
4. **Declaration creates the box**, **initialization puts value in it**
5. **Scope determines where variables can be accessed**
6. **Constants (final) cannot be changed** once initialized
7. **Type conversion** can be automatic (safe) or manual (casting)
8. **Choose appropriate data type** based on range and precision needed

---

*Remember: Choosing the right data type is like choosing the right tool for the job - it makes your program efficient and bug-free!*
