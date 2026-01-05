# Constructors in Java - Object Initialization

## Simple Explanation

Think of **Constructors** as **initialization ceremonies** for new objects:

- Like a **birth certificate process** that officially creates a person
- **Setup instructions** that prepare a new object for use  
- **Assembly line step** that configures each new product
- **Registration process** that establishes object identity

### Real-World Analogies
- **New car setup** = Constructor (set color, add features, register)
- **Account opening** = Constructor (set account number, initial balance)
- **Student enrollment** = Constructor (assign ID, set name, major)
- **House initialization** = Constructor (set address, rooms, owner)

## Professional Definition

A **Constructor** is a special method that is automatically called when an object is created. It initializes the object's state by setting initial values for instance variables. Constructors have the same name as the class and no return type.

## Why Use Constructors?

### Benefits:
- **Guaranteed Initialization**: Ensures objects start in valid state
- **Automatic Invocation**: Called automatically with 'new' keyword
- **Parameter Flexibility**: Accept different initialization parameters
- **Data Validation**: Validate inputs during object creation
- **Code Organization**: Centralize initialization logic
- **Required Setup**: Ensure critical setup steps are not forgotten

### Problems Without Constructors:
```java
// Without constructors - risky approach
Car car = new Car();
// car.brand is null
// car.year is 0
// car.price is 0.0
// Object is in invalid state until manually set

car.brand = "Toyota";    // Easy to forget
car.year = 2023;        // Manual setup required
car.price = 25000.0;    // Error-prone
```

### With Constructors - Safe approach:
```java
// With constructors - safe and automatic
Car car = new Car("Toyota", 2023, 25000.0);
// Object immediately in valid state
// All required fields initialized
// Cannot forget initialization steps
```

## Types of Constructors

### 1. Default Constructor (No Parameters)
```java
public class Student {
    String name;
    int age;
    String major;
    double gpa;
    
    // Default constructor - no parameters
    public Student() {
        name = "Unknown";
        age = 18;
        major = "Undeclared";
        gpa = 0.0;
        System.out.println("Default student created");
    }
    
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age + 
                          ", Major: " + major + ", GPA: " + gpa);
    }
}

// Usage
Student student1 = new Student();  // Calls default constructor
student1.displayInfo();
// Output: Name: Unknown, Age: 18, Major: Undeclared, GPA: 0.0
```

### 2. Parameterized Constructor
```java
public class Student {
    String name;
    int age;
    String major;
    double gpa;
    
    // Parameterized constructor
    public Student(String studentName, int studentAge, String studentMajor, double studentGPA) {
        name = studentName;
        age = studentAge;
        major = studentMajor;
        gpa = studentGPA;
        System.out.println("Student " + name + " created");
    }
    
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age + 
                          ", Major: " + major + ", GPA: " + gpa);
    }
}

// Usage
Student student2 = new Student("Alice", 20, "Computer Science", 3.8);
student2.displayInfo();
// Output: Student Alice created
//         Name: Alice, Age: 20, Major: Computer Science, GPA: 3.8
```

### 3. Multiple Constructors (Constructor Overloading)
```java
public class Car {
    String brand;
    String model;
    int year;
    double price;
    String color;
    
    // Default constructor
    public Car() {
        this.brand = "Unknown";
        this.model = "Unknown";
        this.year = 2023;
        this.price = 0.0;
        this.color = "White";
        System.out.println("Default car created");
    }
    
    // Constructor with brand and model
    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
        this.year = 2023;        // Default year
        this.price = 0.0;        // Default price
        this.color = "White";    // Default color
        System.out.println("Car created: " + brand + " " + model);
    }
    
    // Constructor with brand, model, and year
    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = 0.0;        // Default price
        this.color = "White";    // Default color
        System.out.println("Car created: " + year + " " + brand + " " + model);
    }
    
    // Constructor with all parameters
    public Car(String brand, String model, int year, double price, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.color = color;
        System.out.println("Full car created: " + year + " " + color + " " + 
                          brand + " " + model + " - $" + price);
    }
    
    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
        System.out.println("Color: " + color);
    }
}

// Usage - different constructors
Car car1 = new Car();                                    // Default
Car car2 = new Car("Toyota", "Camry");                  // Brand & Model
Car car3 = new Car("Honda", "Civic", 2022);             // Brand, Model & Year
Car car4 = new Car("BMW", "X5", 2023, 55000.0, "Black"); // All parameters
```

## The 'this' Keyword in Constructors

### 1. Resolving Parameter and Instance Variable Conflicts
```java
public class Person {
    String name;
    int age;
    String email;
    
    // Without 'this' - confusing and incorrect
    public Person(String name, int age, String email) {
        // This assigns parameter to parameter (WRONG!)
        name = name;     // Assigns parameter 'name' to itself
        age = age;       // Assigns parameter 'age' to itself
        email = email;   // Assigns parameter 'email' to itself
        // Instance variables remain uninitialized!
    }
    
    // With 'this' - correct approach
    public Person(String name, int age, String email) {
        this.name = name;      // 'this.name' = instance variable
        this.age = age;        // 'name' = parameter
        this.email = email;
        System.out.println("Person created: " + this.name);
    }
    
    // Alternative approach - different parameter names
    public Person(String personName, int personAge, String personEmail) {
        name = personName;     // No 'this' needed when no conflict
        age = personAge;
        email = personEmail;
    }
}
```

### 2. Constructor Chaining with 'this()'
```java
public class Rectangle {
    double length;
    double width;
    String color;
    
    // Constructor with all parameters
    public Rectangle(double length, double width, String color) {
        this.length = length;
        this.width = width;
        this.color = color;
        System.out.println("Rectangle created: " + length + "x" + width + " " + color);
    }
    
    // Constructor with length and width (default color)
    public Rectangle(double length, double width) {
        this(length, width, "White");  // Call the main constructor
    }
    
    // Constructor with only length (square with default color)
    public Rectangle(double side) {
        this(side, side, "White");  // Call the main constructor
    }
    
    // Default constructor (unit square)
    public Rectangle() {
        this(1.0, 1.0, "White");  // Call the main constructor
    }
    
    public double getArea() {
        return length * width;
    }
    
    public void displayInfo() {
        System.out.println("Rectangle: " + length + "x" + width + 
                          " (" + color + "), Area: " + getArea());
    }
}

// Usage
Rectangle rect1 = new Rectangle(5.0, 3.0, "Blue");  // Full constructor
Rectangle rect2 = new Rectangle(4.0, 2.0);          // Default color
Rectangle rect3 = new Rectangle(3.0);               // Square
Rectangle rect4 = new Rectangle();                  // Default square
```

### 3. Rules for Constructor Chaining
```java
public class BankAccount {
    String accountNumber;
    String accountHolder;
    double balance;
    String accountType;
    
    // Main constructor
    public BankAccount(String accountNumber, String accountHolder, 
                      double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountType = accountType;
        validateAccount();  // Common validation logic
    }
    
    // Constructor with default account type
    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this(accountNumber, accountHolder, balance, "Savings");  // Must be first line
        // Additional initialization can go here
        System.out.println("Account created with default type: Savings");
    }
    
    // Constructor with zero balance
    public BankAccount(String accountNumber, String accountHolder) {
        this(accountNumber, accountHolder, 0.0);  // Must be first line
        System.out.println("Account created with zero balance");
    }
    
    private void validateAccount() {
        if (accountNumber == null || accountNumber.length() < 10) {
            throw new IllegalArgumentException("Invalid account number");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
    
    public void displayAccountInfo() {
        System.out.println("Account: " + accountNumber);
        System.out.println("Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
        System.out.println("Type: " + accountType);
    }
}

// Constructor Chaining Rules:
// 1. this() must be the FIRST statement in constructor
// 2. Cannot call this() and super() in same constructor
// 3. Cannot have circular calls between constructors
```

## Default Constructor Behavior

### 1. Implicit Default Constructor
```java
// When you don't write any constructor
public class SimpleClass {
    String name;
    int value;
    
    // Java automatically provides:
    // public SimpleClass() {
    //     super();  // Call to Object class constructor
    // }
}

// Usage
SimpleClass obj = new SimpleClass();  // Works - uses implicit default constructor
System.out.println(obj.name);         // null
System.out.println(obj.value);        // 0
```

### 2. Explicit Default Constructor
```java
public class InitializedClass {
    String name;
    int value;
    
    // Explicitly written default constructor
    public InitializedClass() {
        name = "Default";
        value = 100;
        System.out.println("Initialized with defaults");
    }
}

// Usage
InitializedClass obj = new InitializedClass();
System.out.println(obj.name);   // "Default"
System.out.println(obj.value);  // 100
```

### 3. No Default Constructor When Parameterized Constructor Exists
```java
public class ParameterizedOnly {
    String name;
    int value;
    
    // Only parameterized constructor provided
    public ParameterizedOnly(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    // Java does NOT provide implicit default constructor
}

// Usage
// ParameterizedOnly obj1 = new ParameterizedOnly();  // COMPILATION ERROR!
ParameterizedOnly obj2 = new ParameterizedOnly("Test", 50);  // Works
```

## Real-World Constructor Examples

### 1. Employee Class with Validation
```java
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String department;
    private double salary;
    private String email;
    
    // Full constructor with validation
    public Employee(String employeeId, String firstName, String lastName, 
                   String department, double salary, String email) {
        // Validate inputs
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Initialize instance variables
        this.employeeId = employeeId.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.department = department != null ? department.trim() : "General";
        this.salary = salary;
        this.email = email.trim();
        
        System.out.println("Employee " + getFullName() + " created successfully");
    }
    
    // Constructor without department (defaults to "General")
    public Employee(String employeeId, String firstName, String lastName, 
                   double salary, String email) {
        this(employeeId, firstName, lastName, "General", salary, email);
    }
    
    // Constructor for entry-level position (minimum salary)
    public Employee(String employeeId, String firstName, String lastName, String email) {
        this(employeeId, firstName, lastName, "General", 30000.0, email);
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public void displayEmployeeInfo() {
        System.out.println("=== Employee Information ===");
        System.out.println("ID: " + employeeId);
        System.out.println("Name: " + getFullName());
        System.out.println("Department: " + department);
        System.out.println("Salary: $" + String.format("%.2f", salary));
        System.out.println("Email: " + email);
    }
    
    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public String getEmail() { return email; }
}
```

### 2. Product Class with Different Creation Options
```java
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
    private boolean isActive;
    
    // Full constructor
    public Product(String productId, String name, String description, 
                  double price, int stockQuantity, String category) {
        validateInputs(productId, name, price, stockQuantity);
        
        this.productId = productId;
        this.name = name;
        this.description = description != null ? description : "No description";
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category != null ? category : "General";
        this.isActive = true;  // New products are active by default
        
        System.out.println("Product '" + name + "' created with ID: " + productId);
    }
    
    // Constructor without description
    public Product(String productId, String name, double price, 
                  int stockQuantity, String category) {
        this(productId, name, null, price, stockQuantity, category);
    }
    
    // Constructor for basic product (no category, no description)
    public Product(String productId, String name, double price, int stockQuantity) {
        this(productId, name, null, price, stockQuantity, null);
    }
    
    // Constructor for service product (no stock)
    public Product(String productId, String name, String description, double price) {
        this(productId, name, description, price, 0, "Service");
        System.out.println("Service product created (no stock tracking)");
    }
    
    private void validateInputs(String productId, String name, double price, int stockQuantity) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }
    
    public void updateStock(int quantity) {
        if (stockQuantity + quantity >= 0) {
            stockQuantity += quantity;
            System.out.println("Stock updated for " + name + ": " + stockQuantity);
        } else {
            System.out.println("Insufficient stock for operation");
        }
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
        System.out.println("Product " + name + " is now " + 
                          (active ? "active" : "inactive"));
    }
    
    public void displayProductInfo() {
        System.out.println("=== Product Information ===");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Stock: " + stockQuantity);
        System.out.println("Category: " + category);
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
    }
    
    // Getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public String getCategory() { return category; }
    public boolean isActive() { return isActive; }
}
```

### 3. Date Class with Multiple Input Formats
```java
public class SimpleDate {
    private int day;
    private int month;
    private int year;
    private static final String[] MONTH_NAMES = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    
    // Constructor with day, month, year
    public SimpleDate(int day, int month, int year) {
        validateDate(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
        System.out.println("Date created: " + toString());
    }
    
    // Constructor with date string "dd/mm/yyyy"
    public SimpleDate(String dateString) {
        if (dateString == null || !dateString.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new IllegalArgumentException("Date must be in format dd/mm/yyyy");
        }
        
        String[] parts = dateString.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        
        validateDate(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
        
        System.out.println("Date created from string: " + toString());
    }
    
    // Constructor with current date
    public SimpleDate() {
        // For demo purposes, using fixed "current" date
        this(28, 12, 2024);
        System.out.println("Current date used");
    }
    
    // Copy constructor
    public SimpleDate(SimpleDate other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy null date");
        }
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
        System.out.println("Date copied: " + toString());
    }
    
    private void validateDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year < 1) {
            throw new IllegalArgumentException("Year must be positive");
        }
        
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        // Check for leap year
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;  // February has 29 days in leap year
        }
        
        if (day < 1 || day > daysInMonth[month - 1]) {
            throw new IllegalArgumentException("Invalid day for given month and year");
        }
    }
    
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    public String getMonthName() {
        return MONTH_NAMES[month - 1];
    }
    
    public void addDays(int days) {
        // Simplified implementation
        day += days;
        while (day > 31) {  // Simplified - doesn't handle month boundaries properly
            day -= 30;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d (%s %d, %d)", 
                           day, month, year, getMonthName(), day, year);
    }
    
    public void displayDateInfo() {
        System.out.println("Date: " + toString());
        System.out.println("Day of month: " + day);
        System.out.println("Month: " + getMonthName() + " (" + month + ")");
        System.out.println("Year: " + year);
        System.out.println("Leap year: " + (isLeapYear(year) ? "Yes" : "No"));
    }
    
    // Getters
    public int getDay() { return day; }
    public int getMonth() { return month; }
    public int getYear() { return year; }
}
```

## Constructor Best Practices

### 1. Validate Input Parameters
```java
public class BankAccount {
    private String accountNumber;
    private double balance;
    private String ownerName;
    
    public BankAccount(String accountNumber, double initialBalance, String ownerName) {
        // Validate all inputs
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (accountNumber.length() < 8) {
            throw new IllegalArgumentException("Account number must be at least 8 characters");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be null or empty");
        }
        
        this.accountNumber = accountNumber.trim();
        this.balance = initialBalance;
        this.ownerName = ownerName.trim();
    }
}
```

### 2. Use Constructor Chaining to Avoid Code Duplication
```java
public class Circle {
    private double radius;
    private String color;
    private double centerX, centerY;
    
    // Master constructor with all parameters
    public Circle(double radius, String color, double centerX, double centerY) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
        this.color = color != null ? color : "Black";
        this.centerX = centerX;
        this.centerY = centerY;
    }
    
    // Chain to master constructor
    public Circle(double radius, String color) {
        this(radius, color, 0.0, 0.0);  // Default center at origin
    }
    
    public Circle(double radius) {
        this(radius, "Black");  // Default color
    }
    
    public Circle() {
        this(1.0);  // Default radius
    }
}
```

### 3. Initialize All Fields Appropriately
```java
public class Student {
    private String studentId;
    private String name;
    private List<String> courses;
    private double gpa;
    private boolean isActive;
    private Date enrollmentDate;
    
    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.courses = new ArrayList<>();  // Initialize collection
        this.gpa = 0.0;                   // Default GPA
        this.isActive = true;             // New students are active
        this.enrollmentDate = new Date(); // Current date
    }
}
```

### 4. Make Constructors Fail-Fast
```java
public class Rectangle {
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        // Fail fast - check all conditions upfront
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive, got: " + length);
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive, got: " + width);
        }
        if (Double.isNaN(length) || Double.isInfinite(length)) {
            throw new IllegalArgumentException("Length must be a valid number");
        }
        if (Double.isNaN(width) || Double.isInfinite(width)) {
            throw new IllegalArgumentException("Width must be a valid number");
        }
        
        this.length = length;
        this.width = width;
    }
}
```

## Constructor Anti-Patterns (What to Avoid)

### 1. Avoid Complex Logic in Constructors
```java
// BAD - complex logic in constructor
public class BadExample {
    private String data;
    
    public BadExample(String input) {
        // Avoid complex processing in constructor
        this.data = processComplexData(input);  // BAD
        connectToDatabase();                    // BAD
        sendEmailNotification();               // BAD
    }
}

// GOOD - keep constructors simple
public class GoodExample {
    private String data;
    
    public GoodExample(String input) {
        this.data = input;  // Simple assignment
    }
    
    // Separate method for complex operations
    public void initialize() {
        this.data = processComplexData(this.data);
        connectToDatabase();
        sendEmailNotification();
    }
}
```

### 2. Avoid Calling Overridable Methods
```java
// BAD - calling method that might be overridden
public class BadExample {
    protected String name;
    
    public BadExample(String name) {
        this.name = name;
        init();  // BAD - if init() is overridden, subclass version called
                 // before subclass constructor runs
    }
    
    protected void init() {
        // Initialization logic
    }
}

// GOOD - use final methods or avoid method calls
public class GoodExample {
    protected String name;
    
    public GoodExample(String name) {
        this.name = name;
        initializeFinal();  // GOOD - final method cannot be overridden
    }
    
    private final void initializeFinal() {
        // Initialization logic
    }
}
```

## Interview Questions & Answers

**Q1: What is a constructor? How is it different from a regular method?**
**A:** A constructor is a special method that initializes objects. Differences:
- Same name as class, no return type
- Called automatically when object is created
- Cannot be called directly like regular methods
- Used for initialization, not general operations

**Q2: What happens if you don't provide any constructor?**
**A:** Java automatically provides a default no-argument constructor that calls the superclass constructor. If you provide any constructor, Java doesn't provide the default one.

**Q3: Can constructors be overloaded? Can they be overridden?**
**A:** 
- **Overloaded**: Yes, you can have multiple constructors with different parameters
- **Overridden**: No, constructors cannot be overridden (inheritance concept)

**Q4: What is constructor chaining?**
**A:** Constructor chaining is calling one constructor from another using `this()`. It must be the first statement and helps avoid code duplication.

**Q5: Can you call this() and super() in the same constructor?**
**A:** No, both must be the first statement, so you can only use one. If you use `this()`, it will eventually call `super()` in the chain.

**Q6: What is the purpose of the 'this' keyword in constructors?**
**A:** 'this' in constructors:
- Distinguishes between parameters and instance variables when names conflict
- Calls other constructors in the same class (`this()`)
- Refers to the current object being initialized

**Q7: Can constructors be private? What's the use case?**
**A:** Yes, private constructors prevent object creation from outside the class. Use cases:
- Singleton pattern
- Utility classes (only static methods)
- Factory pattern where objects are created through factory methods

## Complete Example Program

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive Constructors demonstration
 * University Student Management System
 */

// Course class
class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private String instructor;
    
    // Constructor with validation
    public Course(String courseCode, String courseName, int credits, String instructor) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        if (instructor == null || instructor.trim().isEmpty()) {
            throw new IllegalArgumentException("Instructor name cannot be empty");
        }
        
        this.courseCode = courseCode.trim().toUpperCase();
        this.courseName = courseName.trim();
        this.credits = credits;
        this.instructor = instructor.trim();
    }
    
    // Getters
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    
    @Override
    public String toString() {
        return courseCode + ": " + courseName + " (" + credits + " credits) - " + instructor;
    }
}

// Student class with multiple constructors
class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private int year;
    private double gpa;
    private List<Course> enrolledCourses;
    private boolean isActive;
    
    // Full constructor - master constructor
    public Student(String studentId, String firstName, String lastName, 
                  String email, String major, int year) {
        validateInputs(studentId, firstName, lastName, email, year);
        
        this.studentId = studentId.trim().toUpperCase();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email.trim().toLowerCase();
        this.major = major != null ? major.trim() : "Undeclared";
        this.year = year;
        this.gpa = 0.0;  // New students start with 0 GPA
        this.enrolledCourses = new ArrayList<>();
        this.isActive = true;
        
        System.out.println("Student created: " + getFullName() + " (" + studentId + ")");
    }
    
    // Constructor without major (defaults to "Undeclared")
    public Student(String studentId, String firstName, String lastName, String email, int year) {
        this(studentId, firstName, lastName, email, "Undeclared", year);
    }
    
    // Constructor for freshman (year 1)
    public Student(String studentId, String firstName, String lastName, String email) {
        this(studentId, firstName, lastName, email, "Undeclared", 1);
        System.out.println("Freshman student registered");
    }
    
    // Constructor with full name as single string
    public Student(String studentId, String fullName, String email) {
        this(studentId, 
             extractFirstName(fullName), 
             extractLastName(fullName), 
             email);
        System.out.println("Student created from full name");
    }
    
    // Copy constructor
    public Student(Student other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy null student");
        }
        
        this.studentId = generateNewId();  // Generate new ID for copy
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.email = "copy_" + other.email;  // Modify email to avoid duplicates
        this.major = other.major;
        this.year = other.year;
        this.gpa = other.gpa;
        this.enrolledCourses = new ArrayList<>(other.enrolledCourses);  // Copy courses
        this.isActive = other.isActive;
        
        System.out.println("Student copied: " + getFullName() + " (" + studentId + ")");
    }
    
    private void validateInputs(String studentId, String firstName, String lastName, String email, int year) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        if (studentId.length() < 5) {
            throw new IllegalArgumentException("Student ID must be at least 5 characters");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (year < 1 || year > 6) {
            throw new IllegalArgumentException("Year must be between 1 and 6");
        }
    }
    
    private static String extractFirstName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        String[] parts = fullName.trim().split("\\s+");
        return parts[0];
    }
    
    private static String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) {
            return "Unknown";  // Only first name provided
        }
        return parts[parts.length - 1];  // Last part as last name
    }
    
    private String generateNewId() {
        return "COPY" + System.currentTimeMillis() % 100000;
    }
    
    // Student operations
    public void enrollInCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            System.out.println(getFullName() + " enrolled in " + course.getCourseCode());
        }
    }
    
    public void dropCourse(String courseCode) {
        enrolledCourses.removeIf(course -> course.getCourseCode().equals(courseCode));
        System.out.println(getFullName() + " dropped " + courseCode);
    }
    
    public void updateGPA(double newGPA) {
        if (newGPA >= 0.0 && newGPA <= 4.0) {
            this.gpa = newGPA;
            System.out.println("GPA updated for " + getFullName() + ": " + gpa);
        } else {
            System.out.println("Invalid GPA value: " + newGPA);
        }
    }
    
    public int getTotalCredits() {
        return enrolledCourses.stream().mapToInt(Course::getCredits).sum();
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getYearName() {
        switch (year) {
            case 1: return "Freshman";
            case 2: return "Sophomore";
            case 3: return "Junior";
            case 4: return "Senior";
            default: return "Graduate";
        }
    }
    
    public void displayStudentInfo() {
        System.out.println("\n=== STUDENT INFORMATION ===");
        System.out.println("ID: " + studentId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + email);
        System.out.println("Major: " + major);
        System.out.println("Year: " + year + " (" + getYearName() + ")");
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
        System.out.println("Total Credits: " + getTotalCredits());
        
        if (!enrolledCourses.isEmpty()) {
            System.out.println("\nEnrolled Courses:");
            for (int i = 0; i < enrolledCourses.size(); i++) {
                System.out.println((i + 1) + ". " + enrolledCourses.get(i));
            }
        } else {
            System.out.println("No courses enrolled");
        }
    }
    
    // Getters
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getMajor() { return major; }
    public int getYear() { return year; }
    public double getGpa() { return gpa; }
    public boolean isActive() { return isActive; }
    
    // Setters with validation
    public void setMajor(String major) {
        this.major = major != null ? major.trim() : "Undeclared";
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    @Override
    public String toString() {
        return studentId + ": " + getFullName() + " (" + major + ", " + getYearName() + ")";
    }
}

// Department class
class Department {
    private String departmentCode;
    private String departmentName;
    private String headOfDepartment;
    private List<Course> offeredCourses;
    private List<Student> students;
    
    // Constructor
    public Department(String departmentCode, String departmentName, String headOfDepartment) {
        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Department code cannot be empty");
        }
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        
        this.departmentCode = departmentCode.trim().toUpperCase();
        this.departmentName = departmentName.trim();
        this.headOfDepartment = headOfDepartment != null ? headOfDepartment.trim() : "TBD";
        this.offeredCourses = new ArrayList<>();
        this.students = new ArrayList<>();
        
        System.out.println("Department created: " + departmentName + " (" + departmentCode + ")");
    }
    
    // Constructor without head
    public Department(String departmentCode, String departmentName) {
        this(departmentCode, departmentName, "TBD");
    }
    
    public void addCourse(Course course) {
        if (course != null && !offeredCourses.contains(course)) {
            offeredCourses.add(course);
            System.out.println("Course " + course.getCourseCode() + " added to " + departmentName);
        }
    }
    
    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
            student.setMajor(departmentName);  // Set student's major to department
            System.out.println("Student " + student.getFullName() + " added to " + departmentName);
        }
    }
    
    public void displayDepartmentInfo() {
        System.out.println("\n=== DEPARTMENT INFORMATION ===");
        System.out.println("Code: " + departmentCode);
        System.out.println("Name: " + departmentName);
        System.out.println("Head: " + headOfDepartment);
        System.out.println("Number of courses: " + offeredCourses.size());
        System.out.println("Number of students: " + students.size());
        
        if (!offeredCourses.isEmpty()) {
            System.out.println("\nOffered Courses:");
            for (int i = 0; i < offeredCourses.size(); i++) {
                System.out.println((i + 1) + ". " + offeredCourses.get(i));
            }
        }
        
        if (!students.isEmpty()) {
            System.out.println("\nDepartment Students:");
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i));
            }
        }
    }
    
    // Getters
    public String getDepartmentCode() { return departmentCode; }
    public String getDepartmentName() { return departmentName; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public List<Course> getOfferedCourses() { return new ArrayList<>(offeredCourses); }
    public List<Student> getStudents() { return new ArrayList<>(students); }
}

// Main demo class
public class ConstructorsExample {
    public static void main(String[] args) {
        System.out.println("=== CONSTRUCTORS DEMONSTRATION ===");
        
        demonstrateBasicConstructors();
        demonstrateConstructorOverloading();
        demonstrateConstructorChaining();
        demonstrateValidationInConstructors();
        demonstrateCopyConstructor();
        runUniversitySystemDemo();
    }
    
    public static void demonstrateBasicConstructors() {
        System.out.println("\n--- BASIC CONSTRUCTORS ---");
        
        // Different ways to create students
        Student student1 = new Student("S001", "John", "Doe", "john.doe@university.edu", "Computer Science", 2);
        Student student2 = new Student("S002", "Jane", "Smith", "jane.smith@university.edu", 1);  // Without major
        Student student3 = new Student("S003", "Bob", "Johnson", "bob@university.edu");  // Freshman
        
        student1.displayStudentInfo();
        student2.displayStudentInfo();
        student3.displayStudentInfo();
    }
    
    public static void demonstrateConstructorOverloading() {
        System.out.println("\n--- CONSTRUCTOR OVERLOADING ---");
        
        // Different constructor signatures
        Student student1 = new Student("S004", "Alice Cooper", "alice@university.edu");  // From full name
        Student student2 = new Student("S005", "Charlie", "Brown", "charlie@university.edu", "Mathematics", 3);  // Full details
        
        student1.displayStudentInfo();
        student2.displayStudentInfo();
    }
    
    public static void demonstrateConstructorChaining() {
        System.out.println("\n--- CONSTRUCTOR CHAINING ---");
        
        // Show how constructors call each other
        Student freshman = new Student("S006", "Emily", "Davis", "emily@university.edu");
        System.out.println("Freshman created with default year and major");
        
        freshman.displayStudentInfo();
    }
    
    public static void demonstrateValidationInConstructors() {
        System.out.println("\n--- CONSTRUCTOR VALIDATION ---");
        
        try {
            // This will work
            Student validStudent = new Student("S007", "Michael", "Wilson", "michael@university.edu", "Physics", 2);
            System.out.println("Valid student created successfully");
            
            // These will throw exceptions
            System.out.println("\nTrying to create invalid students...");
            
            // Invalid email
            try {
                Student invalidEmail = new Student("S008", "Invalid", "Email", "not-an-email", 1);
            } catch (IllegalArgumentException e) {
                System.out.println("Caught exception for invalid email: " + e.getMessage());
            }
            
            // Invalid year
            try {
                Student invalidYear = new Student("S009", "Invalid", "Year", "valid@email.com", 10);
            } catch (IllegalArgumentException e) {
                System.out.println("Caught exception for invalid year: " + e.getMessage());
            }
            
            // Empty name
            try {
                Student emptyName = new Student("S010", "", "LastName", "valid@email.com", 1);
            } catch (IllegalArgumentException e) {
                System.out.println("Caught exception for empty first name: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    
    public static void demonstrateCopyConstructor() {
        System.out.println("\n--- COPY CONSTRUCTOR ---");
        
        // Create original student
        Student original = new Student("S011", "Sarah", "Connor", "sarah@university.edu", "Engineering", 3);
        original.updateGPA(3.8);
        
        // Create courses
        Course course1 = new Course("ENG101", "Engineering Fundamentals", 3, "Dr. Smith");
        Course course2 = new Course("MATH201", "Advanced Mathematics", 4, "Dr. Johnson");
        
        original.enrollInCourse(course1);
        original.enrollInCourse(course2);
        
        System.out.println("Original student:");
        original.displayStudentInfo();
        
        // Create copy
        Student copy = new Student(original);
        System.out.println("\nCopied student:");
        copy.displayStudentInfo();
        
        // Show they are independent
        original.updateGPA(3.9);
        copy.updateGPA(3.5);
        
        System.out.println("\nAfter separate GPA updates:");
        System.out.println("Original GPA: " + original.getGpa());
        System.out.println("Copy GPA: " + copy.getGpa());
    }
    
    public static void runUniversitySystemDemo() {
        System.out.println("\n=== UNIVERSITY SYSTEM DEMO ===");
        
        // Create departments
        Department csDept = new Department("CS", "Computer Science", "Dr. Alan Turing");
        Department mathDept = new Department("MATH", "Mathematics", "Dr. Emmy Noether");
        Department physicsDept = new Department("PHYS", "Physics");  // No head specified
        
        // Create courses
        Course cs101 = new Course("CS101", "Introduction to Programming", 3, "Dr. Java");
        Course cs201 = new Course("CS201", "Data Structures", 4, "Dr. Algorithm");
        Course math101 = new Course("MATH101", "Calculus I", 4, "Dr. Newton");
        Course phys101 = new Course("PHYS101", "General Physics", 3, "Dr. Einstein");
        
        // Add courses to departments
        csDept.addCourse(cs101);
        csDept.addCourse(cs201);
        mathDept.addCourse(math101);
        physicsDept.addCourse(phys101);
        
        // Create students using different constructors
        Student student1 = new Student("CS2024001", "Ada", "Lovelace", "ada@university.edu", "Computer Science", 2);
        Student student2 = new Student("MATH2024001", "Isaac Newton", "isaac@university.edu");  // Full name constructor
        Student student3 = new Student("PHYS2024001", "Marie", "Curie", "marie@university.edu", "Physics", 4);
        
        // Add students to departments
        csDept.addStudent(student1);
        mathDept.addStudent(student2);
        physicsDept.addStudent(student3);
        
        // Enroll students in courses
        student1.enrollInCourse(cs101);
        student1.enrollInCourse(cs201);
        student1.enrollInCourse(math101);  // Cross-department enrollment
        
        student2.enrollInCourse(math101);
        student2.enrollInCourse(cs101);    // Cross-department enrollment
        
        student3.enrollInCourse(phys101);
        student3.enrollInCourse(math101);
        
        // Update GPAs
        student1.updateGPA(3.7);
        student2.updateGPA(3.9);
        student3.updateGPA(4.0);
        
        // Display department information
        csDept.displayDepartmentInfo();
        mathDept.displayDepartmentInfo();
        physicsDept.displayDepartmentInfo();
        
        // Display detailed student information
        System.out.println("\n=== DETAILED STUDENT INFORMATION ===");
        student1.displayStudentInfo();
        student2.displayStudentInfo();
        student3.displayStudentInfo();
        
        System.out.println("\n=== UNIVERSITY SYSTEM SUMMARY ===");
        System.out.println("Total Departments: 3");
        System.out.println("Total Courses: 4");
        System.out.println("Total Students: 3");
        System.out.println("Cross-department enrollments enabled!");
    }
}
```

## Key Takeaways

1. **Constructors initialize objects** and ensure they start in valid state
2. **Constructor overloading** provides multiple ways to create objects
3. **Constructor chaining** with `this()` eliminates code duplication
4. **Validate inputs** in constructors to prevent invalid objects
5. **Use 'this' keyword** to resolve parameter/instance variable conflicts
6. **Keep constructors simple** - avoid complex logic
7. **Fail fast** - validate all inputs upfront
8. **Initialize all fields** appropriately in constructors
9. **Default constructor** provided by Java only if no constructors exist
10. **Copy constructors** create independent copies of objects

---

*Remember: Constructors are the gatekeepers of object creation - they ensure every object starts life properly configured and ready to use!*