# Classes and Objects in Java - The Foundation of OOP

## Simple Explanation

Think of **Classes** and **Objects** like **blueprints** and **actual buildings**:

### Class (Blueprint)
- Like a **house blueprint** that shows the design
- **Template** that defines what something should have
- **Recipe** that tells you how to make something
- **Mold** that shapes the final product

### Object (Actual Instance)  
- Like the **actual house** built from the blueprint
- **Real thing** created from the template
- **Actual cake** made from the recipe
- **Final product** that comes out of the mold

### Real-World Analogies
- **Car Class** → Specific cars (Toyota Camry, Honda Civic)
- **Phone Class** → Your iPhone, your friend's Android
- **Student Class** → Alice, Bob, Charlie (actual students)
- **Cookie Cutter Class** → Individual cookies made from it

## Professional Definition

A **Class** is a user-defined blueprint or template from which objects are created. It defines the properties (attributes/fields) and behaviors (methods) that objects of that class will have.

An **Object** is an instance of a class. It's a real-world entity that has state (values of attributes) and behavior (methods that can be called).

## Why Use Classes and Objects?

### Benefits:
- **Code Organization**: Group related data and functions together
- **Reusability**: Create multiple objects from one class
- **Modularity**: Each class handles its own responsibilities
- **Real-World Modeling**: Mirror real-world entities in code
- **Encapsulation**: Bundle data and methods together
- **Maintainability**: Changes to class affect all objects uniformly

### Object-Oriented Programming Advantages:
- Models real-world problems naturally
- Makes complex systems manageable
- Promotes code reuse and modularity
- Enables inheritance and polymorphism
- Facilitates team development

## Basic Class Structure

### 1. Simple Class Definition
```java
// Class definition - the blueprint
public class Car {
    // Attributes/Fields - what the object has
    String brand;
    String model;
    int year;
    String color;
    
    // Methods - what the object can do
    public void start() {
        System.out.println("Car is starting...");
    }
    
    public void stop() {
        System.out.println("Car has stopped.");
    }
    
    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Color: " + color);
    }
}
```

### 2. Creating and Using Objects
```java
public class CarDemo {
    public static void main(String[] args) {
        // Creating objects (instances) from the Car class
        Car car1 = new Car();  // First car object
        Car car2 = new Car();  // Second car object
        
        // Setting attributes for car1
        car1.brand = "Toyota";
        car1.model = "Camry";
        car1.year = 2022;
        car1.color = "Blue";
        
        // Setting attributes for car2
        car2.brand = "Honda";
        car2.model = "Civic";
        car2.year = 2023;
        car2.color = "Red";
        
        // Using methods on objects
        System.out.println("=== Car 1 Information ===");
        car1.displayInfo();
        car1.start();
        car1.stop();
        
        System.out.println("\n=== Car 2 Information ===");
        car2.displayInfo();
        car2.start();
        car2.stop();
    }
}

// Output:
// === Car 1 Information ===
// Brand: Toyota
// Model: Camry
// Year: 2022
// Color: Blue
// Car is starting...
// Car has stopped.
// 
// === Car 2 Information ===
// Brand: Honda
// Model: Civic
// Year: 2023
// Color: Red
// Car is starting...
// Car has stopped.
```

## Class Components Explained

### 1. Attributes/Fields (Instance Variables)
```java
public class Student {
    // Instance variables - each object has its own copy
    String name;           // Student's name
    int age;              // Student's age
    String studentId;     // Unique ID
    double gpa;           // Grade point average
    String major;         // Field of study
    
    // Class variable (static) - shared by all objects
    static String university = "Java University";
}
```

### 2. Methods (Instance Methods)
```java
public class Student {
    String name;
    int age;
    double gpa;
    
    // Instance methods - operate on specific object's data
    public void study() {
        System.out.println(name + " is studying...");
    }
    
    public void takeExam() {
        System.out.println(name + " is taking an exam.");
    }
    
    public void updateGPA(double newGPA) {
        if (newGPA >= 0.0 && newGPA <= 4.0) {
            this.gpa = newGPA;
            System.out.println(name + "'s GPA updated to: " + gpa);
        } else {
            System.out.println("Invalid GPA value!");
        }
    }
    
    public String getGradeLevel() {
        if (gpa >= 3.5) return "Excellent";
        else if (gpa >= 3.0) return "Good";
        else if (gpa >= 2.0) return "Average";
        else return "Needs Improvement";
    }
    
    public void displayStudentInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("GPA: " + gpa);
        System.out.println("Grade Level: " + getGradeLevel());
    }
}
```

### 3. The 'this' Keyword
```java
public class Person {
    String name;
    int age;
    
    // Method parameter has same name as instance variable
    public void setName(String name) {
        this.name = name;  // 'this.name' refers to instance variable
                          // 'name' refers to parameter
    }
    
    public void setAge(int age) {
        this.age = age;    // 'this' distinguishes between instance var and parameter
    }
    
    public void introduceYourself() {
        System.out.println("Hi, I'm " + this.name + " and I'm " + this.age + " years old.");
        // 'this' is optional when there's no ambiguity
        System.out.println("My name is " + name + " and my age is " + age);
    }
    
    public Person getSelf() {
        return this;       // Return reference to current object
    }
}
```

## Real-World Class Examples

### 1. Bank Account Class
```java
public class BankAccount {
    // Attributes
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;
    
    // Methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount);
            System.out.println("New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn $" + amount);
            System.out.println("Remaining balance: $" + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient funds!");
        } else {
            System.out.println("Invalid withdrawal amount!");
        }
    }
    
    public void checkBalance() {
        System.out.println("Account Balance: $" + balance);
    }
    
    public void displayAccountInfo() {
        System.out.println("=== Account Information ===");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + balance);
    }
    
    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }
    
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}
```

### 2. Book Class
```java
public class Book {
    // Attributes
    String title;
    String author;
    String isbn;
    int pageCount;
    double price;
    boolean isAvailable;
    
    // Methods
    public void openBook() {
        System.out.println("Opening '" + title + "' by " + author);
    }
    
    public void closeBook() {
        System.out.println("Closing '" + title + "'");
    }
    
    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("You have borrowed '" + title + "'");
        } else {
            System.out.println("Sorry, '" + title + "' is currently not available");
        }
    }
    
    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Thank you for returning '" + title + "'");
        } else {
            System.out.println("This book was not borrowed");
        }
    }
    
    public void displayBookInfo() {
        System.out.println("=== Book Information ===");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Pages: " + pageCount);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }
    
    public double calculateDiscountPrice(double discountPercent) {
        double discountAmount = price * (discountPercent / 100);
        return price - discountAmount;
    }
}
```

### 3. Employee Class
```java
public class Employee {
    // Attributes
    int employeeId;
    String firstName;
    String lastName;
    String department;
    String position;
    double salary;
    String email;
    String phoneNumber;
    
    // Methods
    public void work() {
        System.out.println(firstName + " " + lastName + " is working on " + department + " tasks");
    }
    
    public void takeBreak() {
        System.out.println(firstName + " is taking a break");
    }
    
    public void attendMeeting() {
        System.out.println(firstName + " is attending a meeting");
    }
    
    public void updateSalary(double newSalary) {
        if (newSalary > 0) {
            double oldSalary = salary;
            salary = newSalary;
            System.out.println("Salary updated from $" + oldSalary + " to $" + salary);
        } else {
            System.out.println("Invalid salary amount!");
        }
    }
    
    public void promote(String newPosition, double salaryIncrease) {
        position = newPosition;
        salary += salaryIncrease;
        System.out.println(firstName + " " + lastName + " has been promoted to " + position);
        System.out.println("New salary: $" + salary);
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public void displayEmployeeInfo() {
        System.out.println("=== Employee Information ===");
        System.out.println("ID: " + employeeId);
        System.out.println("Name: " + getFullName());
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Salary: $" + salary);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
    }
}
```

## Object Memory and References

### 1. Object Creation in Memory
```java
public class MemoryDemo {
    public static void main(String[] args) {
        // Creating objects
        Car car1 = new Car();    // car1 holds reference to Car object in heap
        Car car2 = new Car();    // car2 holds reference to different Car object
        Car car3 = car1;         // car3 holds same reference as car1
        
        car1.brand = "Toyota";
        car1.model = "Camry";
        
        // car1 and car3 point to same object
        System.out.println("car1 brand: " + car1.brand);  // Toyota
        System.out.println("car3 brand: " + car3.brand);  // Toyota (same object)
        
        car3.brand = "Honda";    // Changes the object that car1 also references
        System.out.println("car1 brand: " + car1.brand);  // Honda (changed!)
        System.out.println("car3 brand: " + car3.brand);  // Honda
        
        // car2 is a different object
        System.out.println("car2 brand: " + car2.brand);  // null (different object)
    }
}
```

### 2. null References
```java
public class NullDemo {
    public static void main(String[] args) {
        Car car = null;          // car doesn't point to any object
        
        // System.out.println(car.brand);  // NullPointerException!
        
        // Check for null before using
        if (car != null) {
            car.start();
        } else {
            System.out.println("Car object is null - cannot call methods");
        }
        
        // Create object
        car = new Car();
        car.brand = "BMW";
        
        // Now it's safe to use
        if (car != null) {
            System.out.println("Car brand: " + car.brand);
        }
    }
}
```

## Static vs Instance Members

### 1. Instance Variables and Methods
```java
public class Counter {
    // Instance variable - each object has its own copy
    int count = 0;
    
    // Instance method - operates on specific object's data
    public void increment() {
        count++;
        System.out.println("Instance count: " + count);
    }
    
    public void display() {
        System.out.println("Current count: " + count);
    }
}
```

### 2. Static Variables and Methods
```java
public class MathUtils {
    // Static variable - shared by all instances of the class
    static int totalCalculations = 0;
    
    // Static method - belongs to class, not to any instance
    public static int add(int a, int b) {
        totalCalculations++;
        return a + b;
    }
    
    public static int multiply(int a, int b) {
        totalCalculations++;
        return a * b;
    }
    
    // Static method to get total calculations
    public static int getTotalCalculations() {
        return totalCalculations;
    }
}

// Usage
public class StaticDemo {
    public static void main(String[] args) {
        // Call static methods using class name
        int sum = MathUtils.add(5, 3);
        int product = MathUtils.multiply(4, 6);
        
        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);
        System.out.println("Total calculations: " + MathUtils.getTotalCalculations());
        
        // Can also create objects and call static methods
        MathUtils math = new MathUtils();
        int result = math.add(10, 20);  // Works but not recommended
        int result2 = MathUtils.add(10, 20);  // Preferred way
    }
}
```

### 3. Mixing Instance and Static
```java
public class Student {
    // Static variable - shared by all students
    static String university = "Java University";
    static int totalStudents = 0;
    
    // Instance variables - each student has their own
    String name;
    int studentId;
    double gpa;
    
    // Static method - can only access static variables
    public static void displayUniversityInfo() {
        System.out.println("University: " + university);
        System.out.println("Total students: " + totalStudents);
        // System.out.println("Student name: " + name);  // ERROR! Can't access instance variable
    }
    
    // Instance method - can access both static and instance variables
    public void displayStudentInfo() {
        System.out.println("University: " + university);  // Can access static
        System.out.println("Student: " + name);           // Can access instance
        System.out.println("ID: " + studentId);           // Can access instance
        System.out.println("GPA: " + gpa);               // Can access instance
    }
    
    // Static method to increment total students
    public static void incrementTotalStudents() {
        totalStudents++;
    }
}
```

## Access Modifiers

### 1. Public Access
```java
public class PublicExample {
    public String publicVariable = "Accessible from anywhere";
    
    public void publicMethod() {
        System.out.println("This method can be called from anywhere");
    }
}
```

### 2. Private Access
```java
public class PrivateExample {
    private String privateVariable = "Only accessible within this class";
    private int secretNumber = 42;
    
    private void privateMethod() {
        System.out.println("This method can only be called from within this class");
    }
    
    // Public method that uses private members
    public void showPrivateData() {
        System.out.println("Private variable: " + privateVariable);
        System.out.println("Secret number: " + secretNumber);
        privateMethod();  // Can call private method from within class
    }
}
```

### 3. Package-Private (Default)
```java
class PackageExample {  // No access modifier = package-private
    String packageVariable = "Accessible within same package";
    
    void packageMethod() {
        System.out.println("Accessible within same package");
    }
}
```

### 4. Protected Access
```java
public class ProtectedExample {
    protected String protectedVariable = "Accessible within package and subclasses";
    
    protected void protectedMethod() {
        System.out.println("Accessible within package and subclasses");
    }
}
```

## Class Relationships

### 1. Association (Has-a relationship)
```java
// Student has an Address
public class Address {
    String street;
    String city;
    String state;
    String zipCode;
    
    public Address(String street, String city, String state, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    public void displayAddress() {
        System.out.println(street + ", " + city + ", " + state + " " + zipCode);
    }
}

public class Student {
    String name;
    Address address;  // Student has an Address (Association)
    
    public Student(String name, Address address) {
        this.name = name;
        this.address = address;
    }
    
    public void displayStudentInfo() {
        System.out.println("Student: " + name);
        System.out.print("Address: ");
        address.displayAddress();
    }
}

// Usage
Address address = new Address("123 Main St", "Anytown", "CA", "12345");
Student student = new Student("John Doe", address);
student.displayStudentInfo();
```

### 2. Composition (Strong Has-a relationship)
```java
// Car has an Engine (Engine cannot exist without Car)
public class Engine {
    String type;
    int horsepower;
    
    public Engine(String type, int horsepower) {
        this.type = type;
        this.horsepower = horsepower;
    }
    
    public void start() {
        System.out.println(type + " engine starting...");
    }
    
    public void stop() {
        System.out.println(type + " engine stopping...");
    }
}

public class Car {
    String brand;
    String model;
    Engine engine;  // Car has an Engine (Composition)
    
    public Car(String brand, String model, String engineType, int horsepower) {
        this.brand = brand;
        this.model = model;
        this.engine = new Engine(engineType, horsepower);  // Engine created with Car
    }
    
    public void start() {
        System.out.println("Starting " + brand + " " + model);
        engine.start();
    }
    
    public void stop() {
        engine.stop();
        System.out.println(brand + " " + model + " stopped");
    }
}
```

## Common Patterns and Best Practices

### 1. Getter and Setter Methods (Encapsulation)
```java
public class Person {
    private String name;
    private int age;
    private String email;
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setter methods with validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Invalid name!");
        }
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age! Must be between 0 and 150.");
        }
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            System.out.println("Invalid email format!");
        }
    }
}
```

### 2. toString() Method Override
```java
public class Product {
    private String name;
    private double price;
    private String category;
    
    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    // Override toString() for meaningful object representation
    @Override
    public String toString() {
        return "Product{" +
               "name='" + name + '\'' +
               ", price=" + price +
               ", category='" + category + '\'' +
               '}';
    }
    
    // Alternative toString() format
    public String toDisplayString() {
        return String.format("%s - $%.2f (%s)", name, price, category);
    }
}

// Usage
Product laptop = new Product("MacBook Pro", 1299.99, "Electronics");
System.out.println(laptop);  // Calls toString() automatically
System.out.println(laptop.toDisplayString());
```

### 3. Method Chaining
```java
public class Calculator {
    private double result = 0;
    
    public Calculator add(double value) {
        result += value;
        return this;  // Return current object for chaining
    }
    
    public Calculator subtract(double value) {
        result -= value;
        return this;
    }
    
    public Calculator multiply(double value) {
        result *= value;
        return this;
    }
    
    public Calculator divide(double value) {
        if (value != 0) {
            result /= value;
        }
        return this;
    }
    
    public double getResult() {
        return result;
    }
    
    public Calculator reset() {
        result = 0;
        return this;
    }
}

// Usage - method chaining
Calculator calc = new Calculator();
double result = calc.add(10)
                   .multiply(2)
                   .subtract(5)
                   .divide(3)
                   .getResult();
System.out.println("Result: " + result);  // 5.0
```

## Interview Questions & Answers

**Q1: What is the difference between a class and an object?**
**A:** 
- **Class**: Blueprint/template that defines structure and behavior
- **Object**: Instance of a class, actual entity created in memory
- Example: Car class is blueprint, your actual car is an object

**Q2: What is the 'this' keyword used for?**
**A:** 'this' keyword refers to the current object instance. Uses:
- Distinguish between instance variables and parameters with same name
- Call other constructors in same class
- Return current object reference
- Pass current object to other methods

**Q3: What is the difference between instance and static variables?**
**A:**
- **Instance variables**: Each object has its own copy, stored in heap
- **Static variables**: Shared by all instances, stored in method area
- Static variables are loaded when class is first loaded

**Q4: Can we access non-static variables from static methods?**
**A:** No, static methods belong to class and cannot access instance variables directly. You need an object reference to access instance variables from static context.

**Q5: What happens when we create an object using 'new' keyword?**
**A:** When using 'new':
1. Memory is allocated in heap
2. Instance variables are initialized to default values
3. Constructor is called
4. Reference to object is returned

**Q6: What is the difference between '==' and '.equals()' for objects?**
**A:**
- `==`: Compares references (memory addresses)
- `.equals()`: Compares object content (if properly overridden)

**Q7: What is encapsulation and how do we achieve it?**
**A:** Encapsulation is bundling data and methods together and controlling access. Achieved by:
- Making instance variables private
- Providing public getter/setter methods
- Validating data in setter methods

## Complete Example Program

```java
/**
 * Comprehensive Classes and Objects demonstration
 * Library Management System
 */

// Book class
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private String borrower;
    
    // Static variable to track total books
    private static int totalBooks = 0;
    
    // Constructor
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
        this.borrower = null;
        totalBooks++;
    }
    
    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrower() { return borrower; }
    public static int getTotalBooks() { return totalBooks; }
    
    // Book operations
    public boolean borrowBook(String borrowerName) {
        if (isAvailable) {
            isAvailable = false;
            borrower = borrowerName;
            System.out.println("Book '" + title + "' borrowed by " + borrowerName);
            return true;
        } else {
            System.out.println("Book '" + title + "' is not available (borrowed by " + borrower + ")");
            return false;
        }
    }
    
    public boolean returnBook() {
        if (!isAvailable) {
            System.out.println("Book '" + title + "' returned by " + borrower);
            isAvailable = true;
            borrower = null;
            return true;
        } else {
            System.out.println("Book '" + title + "' was not borrowed");
            return false;
        }
    }
    
    public void displayBookInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Status: " + (isAvailable ? "Available" : "Borrowed by " + borrower));
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " [" + (isAvailable ? "Available" : "Borrowed") + "]";
    }
}

// Member class
class Member {
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private int booksBorrowed;
    private static final int MAX_BOOKS = 5;
    
    public Member(String memberId, String name, String email, String phoneNumber) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.booksBorrowed = 0;
    }
    
    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getBooksBorrowed() { return booksBorrowed; }
    
    // Setters with validation
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("Invalid email format");
        }
    }
    
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.matches("\\d{10}")) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Invalid phone number format");
        }
    }
    
    public boolean canBorrowMore() {
        return booksBorrowed < MAX_BOOKS;
    }
    
    public void incrementBooksBorrowed() {
        if (canBorrowMore()) {
            booksBorrowed++;
        }
    }
    
    public void decrementBooksBorrowed() {
        if (booksBorrowed > 0) {
            booksBorrowed--;
        }
    }
    
    public void displayMemberInfo() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Books borrowed: " + booksBorrowed + "/" + MAX_BOOKS);
    }
    
    @Override
    public String toString() {
        return name + " (" + memberId + ") - " + booksBorrowed + " books borrowed";
    }
}

// Library class
class Library {
    private String libraryName;
    private String address;
    private Book[] books;
    private Member[] members;
    private int bookCount;
    private int memberCount;
    private static final int MAX_BOOKS = 1000;
    private static final int MAX_MEMBERS = 500;
    
    public Library(String libraryName, String address) {
        this.libraryName = libraryName;
        this.address = address;
        this.books = new Book[MAX_BOOKS];
        this.members = new Member[MAX_MEMBERS];
        this.bookCount = 0;
        this.memberCount = 0;
    }
    
    // Add book to library
    public boolean addBook(Book book) {
        if (bookCount < MAX_BOOKS) {
            books[bookCount] = book;
            bookCount++;
            System.out.println("Book '" + book.getTitle() + "' added to library");
            return true;
        } else {
            System.out.println("Library is full, cannot add more books");
            return false;
        }
    }
    
    // Add member to library
    public boolean addMember(Member member) {
        if (memberCount < MAX_MEMBERS) {
            members[memberCount] = member;
            memberCount++;
            System.out.println("Member '" + member.getName() + "' added to library");
            return true;
        } else {
            System.out.println("Member limit reached");
            return false;
        }
    }
    
    // Find book by title
    public Book findBookByTitle(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return books[i];
            }
        }
        return null;
    }
    
    // Find member by ID
    public Member findMemberById(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {
                return members[i];
            }
        }
        return null;
    }
    
    // Borrow book
    public boolean borrowBook(String bookTitle, String memberId) {
        Book book = findBookByTitle(bookTitle);
        Member member = findMemberById(memberId);
        
        if (book == null) {
            System.out.println("Book '" + bookTitle + "' not found in library");
            return false;
        }
        
        if (member == null) {
            System.out.println("Member with ID '" + memberId + "' not found");
            return false;
        }
        
        if (!member.canBorrowMore()) {
            System.out.println("Member has reached maximum borrowing limit");
            return false;
        }
        
        if (book.borrowBook(member.getName())) {
            member.incrementBooksBorrowed();
            return true;
        }
        
        return false;
    }
    
    // Return book
    public boolean returnBook(String bookTitle, String memberId) {
        Book book = findBookByTitle(bookTitle);
        Member member = findMemberById(memberId);
        
        if (book == null) {
            System.out.println("Book '" + bookTitle + "' not found in library");
            return false;
        }
        
        if (member == null) {
            System.out.println("Member with ID '" + memberId + "' not found");
            return false;
        }
        
        if (book.getBorrower() != null && book.getBorrower().equals(member.getName())) {
            if (book.returnBook()) {
                member.decrementBooksBorrowed();
                return true;
            }
        } else {
            System.out.println("This book was not borrowed by " + member.getName());
        }
        
        return false;
    }
    
    // Display all books
    public void displayAllBooks() {
        System.out.println("\n=== ALL BOOKS IN " + libraryName.toUpperCase() + " ===");
        if (bookCount == 0) {
            System.out.println("No books in library");
            return;
        }
        
        for (int i = 0; i < bookCount; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
    }
    
    // Display available books
    public void displayAvailableBooks() {
        System.out.println("\n=== AVAILABLE BOOKS ===");
        boolean foundAvailable = false;
        
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) {
                System.out.println("• " + books[i]);
                foundAvailable = true;
            }
        }
        
        if (!foundAvailable) {
            System.out.println("No books currently available");
        }
    }
    
    // Display all members
    public void displayAllMembers() {
        System.out.println("\n=== ALL MEMBERS ===");
        if (memberCount == 0) {
            System.out.println("No members in library");
            return;
        }
        
        for (int i = 0; i < memberCount; i++) {
            System.out.println((i + 1) + ". " + members[i]);
        }
    }
    
    // Display library statistics
    public void displayLibraryStats() {
        System.out.println("\n=== LIBRARY STATISTICS ===");
        System.out.println("Library: " + libraryName);
        System.out.println("Address: " + address);
        System.out.println("Total books: " + bookCount);
        System.out.println("Total members: " + memberCount);
        
        int availableBooks = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) {
                availableBooks++;
            }
        }
        
        System.out.println("Available books: " + availableBooks);
        System.out.println("Borrowed books: " + (bookCount - availableBooks));
    }
}

// Main demo class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Create library
        Library library = new Library("Central City Library", "123 Main Street, Central City");
        
        System.out.println("=== LIBRARY MANAGEMENT SYSTEM DEMO ===");
        
        // Add books
        System.out.println("\n--- Adding Books ---");
        library.addBook(new Book("Java: The Complete Reference", "Herbert Schildt", "978-0071808552"));
        library.addBook(new Book("Clean Code", "Robert Martin", "978-0132350884"));
        library.addBook(new Book("Design Patterns", "Gang of Four", "978-0201633610"));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "978-0134685991"));
        library.addBook(new Book("Spring in Action", "Craig Walls", "978-1617294945"));
        
        // Add members
        System.out.println("\n--- Adding Members ---");
        library.addMember(new Member("M001", "Alice Johnson", "alice@email.com", "1234567890"));
        library.addMember(new Member("M002", "Bob Smith", "bob@email.com", "2345678901"));
        library.addMember(new Member("M003", "Charlie Brown", "charlie@email.com", "3456789012"));
        
        // Display initial status
        library.displayLibraryStats();
        library.displayAllBooks();
        library.displayAvailableBooks();
        library.displayAllMembers();
        
        // Simulate borrowing
        System.out.println("\n--- Book Borrowing ---");
        library.borrowBook("Java: The Complete Reference", "M001");
        library.borrowBook("Clean Code", "M001");
        library.borrowBook("Design Patterns", "M002");
        library.borrowBook("Java: The Complete Reference", "M002");  // Should fail - already borrowed
        library.borrowBook("Effective Java", "M003");
        
        // Display status after borrowing
        System.out.println("\n--- Status After Borrowing ---");
        library.displayAvailableBooks();
        library.displayLibraryStats();
        
        // Simulate returning
        System.out.println("\n--- Book Returning ---");
        library.returnBook("Java: The Complete Reference", "M001");
        library.returnBook("Design Patterns", "M002");
        library.returnBook("Clean Code", "M002");  // Should fail - not borrowed by M002
        
        // Final status
        System.out.println("\n--- Final Status ---");
        library.displayLibraryStats();
        library.displayAvailableBooks();
        
        // Display total books created (static variable)
        System.out.println("\nTotal books created in system: " + Book.getTotalBooks());
    }
}
```

## Key Takeaways

1. **Classes are blueprints**, objects are instances created from those blueprints
2. **Instance variables** belong to objects, **static variables** belong to class
3. **Methods define behavior** that objects can perform
4. **'this' keyword** refers to current object instance
5. **Access modifiers** control visibility of class members
6. **Encapsulation** protects data through private variables and public methods
7. **Objects are created in heap memory** and accessed through references
8. **null references** don't point to any object - check before use
9. **Static members** can be accessed without creating objects
10. **Good class design** follows single responsibility principle

---

*Remember: Classes and objects are the foundation of OOP - they help you model real-world entities and create organized, reusable code!*