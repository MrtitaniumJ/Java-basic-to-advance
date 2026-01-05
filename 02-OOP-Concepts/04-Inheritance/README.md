# Inheritance in Java - Code Reusability and Relationships

## Simple Explanation

Think of **Inheritance** as **family traits** that get passed down:

- Like **children inheriting features** from their parents (eyes, height, talents)
- **Car models** sharing common features from a base design
- **Student types** (undergraduate, graduate) sharing basic student properties
- **Animal species** inheriting common characteristics from animal kingdom

### Real-World Analogies
- **Family tree** = Inheritance hierarchy (grandparents → parents → children)
- **Vehicle types** = Inheritance (Vehicle → Car → SportsCar)
- **Employee hierarchy** = Inheritance (Person → Employee → Manager)
- **Device categories** = Inheritance (Device → Computer → Laptop)

## Professional Definition

**Inheritance** is a fundamental OOP principle where a new class (subclass/child) inherits properties and behaviors from an existing class (superclass/parent). It promotes code reusability, establishes "is-a" relationships, and enables polymorphism.

## Why Use Inheritance?

### Benefits:
- **Code Reusability**: Write common code once in parent class
- **Code Organization**: Logical hierarchy of related classes
- **Maintainability**: Changes in parent affect all children automatically
- **Extensibility**: Add new features by extending existing classes
- **Polymorphism**: Enable method overriding and dynamic behavior
- **Natural Modeling**: Mirror real-world relationships

### Problems Without Inheritance:
```java
// WITHOUT INHERITANCE - Code Duplication
class Student {
    String name, id, email;
    int age;
    
    void displayPersonalInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

class Teacher {
    String name, id, email;  // DUPLICATE CODE!
    int age;                 // DUPLICATE CODE!
    
    void displayPersonalInfo() {  // DUPLICATE CODE!
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

class Employee {
    String name, id, email;  // DUPLICATE CODE AGAIN!
    int age;                 // DUPLICATE CODE AGAIN!
    
    void displayPersonalInfo() {  // DUPLICATE CODE AGAIN!
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
```

### With Inheritance - Clean and Reusable:
```java
// WITH INHERITANCE - Code Reuse
class Person {  // Parent/Base class
    String name, id, email;
    int age;
    
    void displayPersonalInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

class Student extends Person {  // Child class
    String major;
    double gpa;
    // Inherits: name, id, email, age, displayPersonalInfo()
}

class Teacher extends Person {  // Child class
    String subject;
    int experience;
    // Inherits: name, id, email, age, displayPersonalInfo()
}

class Employee extends Person {  // Child class
    String department;
    double salary;
    // Inherits: name, id, email, age, displayPersonalInfo()
}
```

## Basic Inheritance Syntax

### 1. Single Inheritance
```java
// Parent class (Superclass/Base class)
class Animal {
    String name;
    int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void eat() {
        System.out.println(name + " is eating.");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
    
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

// Child class (Subclass/Derived class)
class Dog extends Animal {
    String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);  // Call parent constructor
        this.breed = breed;
    }
    
    // Inherited methods: eat(), sleep(), displayInfo()
    // Additional method specific to Dog
    public void bark() {
        System.out.println(name + " is barking: Woof! Woof!");
    }
    
    // Can override inherited methods
    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("Breed: " + breed);
    }
}

// Usage
Dog myDog = new Dog("Buddy", 3, "Golden Retriever");
myDog.eat();         // Inherited from Animal
myDog.sleep();       // Inherited from Animal
myDog.bark();        // Dog's own method
myDog.displayInfo(); // Overridden method
```

### 2. Multi-level Inheritance
```java
// Grandparent class
class Vehicle {
    String brand;
    int year;
    
    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }
    
    public void start() {
        System.out.println("Vehicle is starting...");
    }
    
    public void stop() {
        System.out.println("Vehicle has stopped.");
    }
}

// Parent class
class Car extends Vehicle {
    int doors;
    String fuelType;
    
    public Car(String brand, int year, int doors, String fuelType) {
        super(brand, year);  // Call Vehicle constructor
        this.doors = doors;
        this.fuelType = fuelType;
    }
    
    public void drive() {
        System.out.println("Car is driving on the road.");
    }
    
    @Override
    public void start() {
        System.out.println("Car engine is starting...");
    }
}

// Child class
class SportsCar extends Car {
    int topSpeed;
    boolean hasTurbo;
    
    public SportsCar(String brand, int year, int doors, String fuelType, 
                     int topSpeed, boolean hasTurbo) {
        super(brand, year, doors, fuelType);  // Call Car constructor
        this.topSpeed = topSpeed;
        this.hasTurbo = hasTurbo;
    }
    
    public void activateTurbo() {
        if (hasTurbo) {
            System.out.println("Turbo activated! Maximum speed!");
        } else {
            System.out.println("No turbo available.");
        }
    }
    
    @Override
    public void drive() {
        System.out.println("Sports car is speeding on the highway!");
    }
    
    public void displaySpecs() {
        System.out.println("Brand: " + brand);        // From Vehicle
        System.out.println("Year: " + year);          // From Vehicle
        System.out.println("Doors: " + doors);        // From Car
        System.out.println("Fuel: " + fuelType);      // From Car
        System.out.println("Top Speed: " + topSpeed + " mph");
        System.out.println("Turbo: " + (hasTurbo ? "Yes" : "No"));
    }
}

// Usage
SportsCar ferrari = new SportsCar("Ferrari", 2023, 2, "Gasoline", 211, true);
ferrari.start();        // Overridden in Car
ferrari.drive();        // Overridden in SportsCar
ferrari.activateTurbo(); // SportsCar's own method
ferrari.stop();         // Inherited from Vehicle
ferrari.displaySpecs(); // Uses inherited fields
```

## The 'super' Keyword

### 1. Calling Parent Constructor
```java
class Person {
    String name;
    int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Person constructor called");
    }
}

class Student extends Person {
    String studentId;
    String major;
    
    public Student(String name, int age, String studentId, String major) {
        super(name, age);  // MUST be first line in constructor
        this.studentId = studentId;
        this.major = major;
        System.out.println("Student constructor called");
    }
    
    // If no super() call, Java automatically calls super() with no arguments
    // This would cause error if parent has no default constructor
}
```

### 2. Calling Parent Methods
```java
class Employee {
    String name;
    double baseSalary;
    
    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }
    
    public double calculateSalary() {
        return baseSalary;
    }
    
    public void displayInfo() {
        System.out.println("Employee: " + name);
        System.out.println("Base Salary: $" + baseSalary);
    }
}

class Manager extends Employee {
    double bonus;
    int teamSize;
    
    public Manager(String name, double baseSalary, double bonus, int teamSize) {
        super(name, baseSalary);
        this.bonus = bonus;
        this.teamSize = teamSize;
    }
    
    @Override
    public double calculateSalary() {
        double parentSalary = super.calculateSalary();  // Call parent method
        return parentSalary + bonus;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent method first
        System.out.println("Bonus: $" + bonus);
        System.out.println("Team Size: " + teamSize);
        System.out.println("Total Salary: $" + calculateSalary());
    }
}
```

### 3. Accessing Parent Fields
```java
class Shape {
    protected String color;  // protected = accessible to subclasses
    protected double area;
    
    public Shape(String color) {
        this.color = color;
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
        calculateArea();
    }
    
    private void calculateArea() {
        super.area = length * width;  // Access parent field
        // or simply: area = length * width; (inherited field)
    }
    
    public void displayInfo() {
        System.out.println("Rectangle:");
        System.out.println("Color: " + super.color);  // Access parent field
        System.out.println("Dimensions: " + length + " x " + width);
        System.out.println("Area: " + super.area);
    }
}
```

## Method Overriding

### 1. Basic Method Overriding
```java
class Animal {
    public void makeSound() {
        System.out.println("Animal makes a generic sound");
    }
    
    public void move() {
        System.out.println("Animal moves");
    }
}

class Cat extends Animal {
    @Override  // Annotation indicates method overriding
    public void makeSound() {
        System.out.println("Cat says: Meow!");
    }
    
    @Override
    public void move() {
        System.out.println("Cat walks gracefully");
    }
    
    // Additional method specific to Cat
    public void climb() {
        System.out.println("Cat climbs the tree");
    }
}

class Bird extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bird says: Tweet!");
    }
    
    @Override
    public void move() {
        System.out.println("Bird flies in the sky");
    }
    
    public void fly() {
        System.out.println("Bird soars high");
    }
}

// Usage demonstrating polymorphism
Animal[] animals = {
    new Animal(),
    new Cat(),
    new Bird()
};

for (Animal animal : animals) {
    animal.makeSound();  // Calls appropriate overridden method
    animal.move();       // Calls appropriate overridden method
    System.out.println();
}

// Output:
// Animal makes a generic sound
// Animal moves
//
// Cat says: Meow!
// Cat walks gracefully
//
// Bird says: Tweet!
// Bird flies in the sky
```

### 2. Method Overriding Rules
```java
class Parent {
    // Original method
    public String getMessage() {
        return "Message from Parent";
    }
    
    // Method with parameters
    public void process(String data) {
        System.out.println("Parent processing: " + data);
    }
    
    // Protected method
    protected void internalMethod() {
        System.out.println("Parent internal method");
    }
    
    // Final method - cannot be overridden
    public final void finalMethod() {
        System.out.println("This cannot be overridden");
    }
}

class Child extends Parent {
    // VALID: Same signature, compatible return type
    @Override
    public String getMessage() {
        return "Message from Child";
    }
    
    // VALID: Same signature
    @Override
    public void process(String data) {
        System.out.println("Child processing: " + data.toUpperCase());
    }
    
    // VALID: Protected can be overridden as public or protected
    @Override
    public void internalMethod() {  // Changed from protected to public
        System.out.println("Child internal method");
    }
    
    // INVALID: Cannot override final method
    // public void finalMethod() { }  // Compilation error!
    
    // INVALID: Cannot reduce access modifier
    // private String getMessage() { }  // Compilation error!
    
    // INVALID: Different parameter types (this is overloading, not overriding)
    // public void process(int data) { }  // This is a different method
}
```

### 3. Covariant Return Types
```java
class Animal {
    public Animal reproduce() {
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    public Dog reproduce() {  // Covariant return type (Dog is subtype of Animal)
        return new Dog();
    }
}

class Cat extends Animal {
    @Override
    public Cat reproduce() {  // Covariant return type
        return new Cat();
    }
}

// Usage
Dog parentDog = new Dog();
Dog puppyDog = parentDog.reproduce();  // Returns Dog, not Animal

Cat parentCat = new Cat();
Cat kitten = parentCat.reproduce();    // Returns Cat, not Animal
```

## Access Modifiers in Inheritance

### 1. Understanding Access Levels
```java
class Parent {
    public String publicField = "Public - accessible everywhere";
    protected String protectedField = "Protected - accessible in package and subclasses";
    String defaultField = "Default - accessible in same package";
    private String privateField = "Private - only in this class";
    
    public void publicMethod() {
        System.out.println("Public method");
    }
    
    protected void protectedMethod() {
        System.out.println("Protected method");
    }
    
    void defaultMethod() {
        System.out.println("Default method");
    }
    
    private void privateMethod() {
        System.out.println("Private method");
    }
}

class Child extends Parent {
    public void testAccess() {
        // Can access public
        System.out.println(publicField);
        publicMethod();
        
        // Can access protected
        System.out.println(protectedField);
        protectedMethod();
        
        // Can access default (if in same package)
        System.out.println(defaultField);
        defaultMethod();
        
        // CANNOT access private
        // System.out.println(privateField);  // Compilation error!
        // privateMethod();                   // Compilation error!
    }
}
```

### 2. Inheritance and Package Visibility
```java
// In package com.example.parent
package com.example.parent;

public class BaseClass {
    public String publicField = "Public";
    protected String protectedField = "Protected";
    String packageField = "Package";
    private String privateField = "Private";
}

// In package com.example.child
package com.example.child;
import com.example.parent.BaseClass;

public class DerivedClass extends BaseClass {
    public void checkAccess() {
        // Can access public
        System.out.println(publicField);
        
        // Can access protected (even from different package)
        System.out.println(protectedField);
        
        // CANNOT access package-private from different package
        // System.out.println(packageField);  // Compilation error!
        
        // CANNOT access private
        // System.out.println(privateField);  // Compilation error!
    }
}
```

---

# Part 2: Advanced Inheritance Concepts

## Constructor Chaining in Inheritance

### 1. Automatic Constructor Chaining
```java
class GrandParent {
    public GrandParent() {
        System.out.println("GrandParent constructor called");
    }
    
    public GrandParent(String name) {
        System.out.println("GrandParent constructor with name: " + name);
    }
}

class Parent extends GrandParent {
    public Parent() {
        // super() is called automatically
        System.out.println("Parent constructor called");
    }
    
    public Parent(String name) {
        super(name);  // Explicit call to GrandParent constructor
        System.out.println("Parent constructor with name: " + name);
    }
}

class Child extends Parent {
    public Child() {
        // super() is called automatically
        System.out.println("Child constructor called");
    }
    
    public Child(String name) {
        super(name);  // Call Parent constructor
        System.out.println("Child constructor with name: " + name);
    }
}

// Usage
Child child = new Child("Alice");
// Output:
// GrandParent constructor with name: Alice
// Parent constructor with name: Alice
// Child constructor with name: Alice
```

### 2. Complex Constructor Chaining
```java
class Employee {
    protected String name;
    protected String employeeId;
    protected double baseSalary;
    
    public Employee(String name, String employeeId, double baseSalary) {
        this.name = name;
        this.employeeId = employeeId;
        this.baseSalary = baseSalary;
        System.out.println("Employee created: " + name);
    }
}

class Manager extends Employee {
    private String department;
    private int teamSize;
    
    public Manager(String name, String employeeId, double baseSalary, 
                   String department, int teamSize) {
        super(name, employeeId, baseSalary);  // Initialize parent
        this.department = department;
        this.teamSize = teamSize;
        System.out.println("Manager created for department: " + department);
    }
    
    // Convenience constructor
    public Manager(String name, String employeeId, String department) {
        this(name, employeeId, 80000.0, department, 5);  // Call main constructor
    }
}

class Director extends Manager {
    private String region;
    private double bonus;
    
    public Director(String name, String employeeId, double baseSalary,
                   String department, int teamSize, String region, double bonus) {
        super(name, employeeId, baseSalary, department, teamSize);
        this.region = region;
        this.bonus = bonus;
        System.out.println("Director created for region: " + region);
    }
    
    // Convenience constructor
    public Director(String name, String employeeId, String department, String region) {
        super(name, employeeId, department);  // Call Manager convenience constructor
        this.region = region;
        this.bonus = 20000.0;
    }
}
```

## Real-World Inheritance Examples

### 1. Shape Hierarchy
```java
abstract class Shape {
    protected String color;
    protected boolean filled;
    
    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    // Concrete method - can be used by all subclasses
    public void displayInfo() {
        System.out.println("Color: " + color);
        System.out.println("Filled: " + filled);
        System.out.println("Area: " + String.format("%.2f", calculateArea()));
        System.out.println("Perimeter: " + String.format("%.2f", calculatePerimeter()));
    }
    
    // Getters and setters
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean isFilled() { return filled; }
    public void setFilled(boolean filled) { this.filled = filled; }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(String color, boolean filled, double radius) {
        super(color, filled);
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Circle ===");
        System.out.println("Radius: " + radius);
        super.displayInfo();
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, boolean filled, double length, double width) {
        super(color, filled);
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
    
    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Rectangle ===");
        System.out.println("Length: " + length + ", Width: " + width);
        super.displayInfo();
    }
}

class Square extends Rectangle {
    public Square(String color, boolean filled, double side) {
        super(color, filled, side, side);  // Square is special case of Rectangle
    }
    
    public double getSide() {
        return getLength();  // Since length equals width in a square
    }
    
    public void setSide(double side) {
        setLength(side);
        setWidth(side);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Square ===");
        System.out.println("Side: " + getSide());
        System.out.println("Color: " + color);
        System.out.println("Filled: " + filled);
        System.out.println("Area: " + String.format("%.2f", calculateArea()));
        System.out.println("Perimeter: " + String.format("%.2f", calculatePerimeter()));
    }
}
```

### 2. Bank Account Hierarchy
```java
class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected String accountType;
    protected java.time.LocalDate openDate;
    
    public BankAccount(String accountNumber, String accountHolder, 
                       double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.openDate = java.time.LocalDate.now();
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn $" + amount + ". New balance: $" + balance);
            return true;
        }
        System.out.println("Insufficient funds or invalid amount");
        return false;
    }
    
    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + String.format("%.2f", balance));
        System.out.println("Open Date: " + openDate);
    }
    
    // Getters
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
}

class SavingsAccount extends BankAccount {
    private double interestRate;
    private int minimumBalance;
    
    public SavingsAccount(String accountNumber, String accountHolder, 
                         double initialBalance, double interestRate) {
        super(accountNumber, accountHolder, initialBalance, "Savings");
        this.interestRate = interestRate;
        this.minimumBalance = 100;  // Minimum balance requirement
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= minimumBalance) {
            balance -= amount;
            System.out.println("Withdrawn $" + amount + " from Savings. New balance: $" + balance);
            return true;
        }
        System.out.println("Withdrawal failed. Must maintain minimum balance of $" + minimumBalance);
        return false;
    }
    
    public void addInterest() {
        double interest = balance * (interestRate / 100) / 12;  // Monthly interest
        balance += interest;
        System.out.println("Interest added: $" + String.format("%.2f", interest) + 
                          ". New balance: $" + String.format("%.2f", balance));
    }
    
    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo();
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Minimum Balance: $" + minimumBalance);
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit;
    private double overdraftFee;
    private int freeTransactions;
    private int transactionCount;
    
    public CheckingAccount(String accountNumber, String accountHolder, 
                          double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolder, initialBalance, "Checking");
        this.overdraftLimit = overdraftLimit;
        this.overdraftFee = 35.0;
        this.freeTransactions = 10;
        this.transactionCount = 0;
    }
    
    @Override
    public boolean withdraw(double amount) {
        transactionCount++;
        
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            
            if (balance < 0) {
                balance -= overdraftFee;
                System.out.println("Overdraft used. Fee charged: $" + overdraftFee);
            }
            
            if (transactionCount > freeTransactions) {
                balance -= 2.0;  // Transaction fee
                System.out.println("Transaction fee charged: $2.00");
            }
            
            System.out.println("Withdrawn $" + amount + ". New balance: $" + balance);
            return true;
        }
        System.out.println("Withdrawal exceeds overdraft limit");
        return false;
    }
    
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        transactionCount++;
        
        if (transactionCount > freeTransactions) {
            balance -= 2.0;
            System.out.println("Transaction fee charged: $2.00");
        }
    }
    
    public void resetMonthlyTransactions() {
        transactionCount = 0;
        System.out.println("Monthly transaction count reset");
    }
    
    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo();
        System.out.println("Overdraft Limit: $" + overdraftLimit);
        System.out.println("Transactions this month: " + transactionCount + "/" + freeTransactions + " (free)");
    }
}

class PremiumAccount extends CheckingAccount {
    private String relationshipManager;
    private double cashbackRate;
    private double totalCashback;
    
    public PremiumAccount(String accountNumber, String accountHolder, 
                         double initialBalance, String relationshipManager) {
        super(accountNumber, accountHolder, initialBalance, 10000);  // High overdraft limit
        this.relationshipManager = relationshipManager;
        this.cashbackRate = 0.02;  // 2% cashback
        this.totalCashback = 0.0;
        this.freeTransactions = 50;  // More free transactions
        this.overdraftFee = 0.0;     // No overdraft fees
    }
    
    @Override
    public boolean withdraw(double amount) {
        // No transaction fees for premium accounts
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            
            // Calculate cashback for large withdrawals
            if (amount > 1000) {
                double cashback = amount * cashbackRate;
                totalCashback += cashback;
                balance += cashback;
                System.out.println("Cashback earned: $" + String.format("%.2f", cashback));
            }
            
            System.out.println("Withdrawn $" + amount + ". New balance: $" + balance);
            return true;
        }
        System.out.println("Withdrawal exceeds overdraft limit");
        return false;
    }
    
    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo();
        System.out.println("Account Type: Premium");
        System.out.println("Relationship Manager: " + relationshipManager);
        System.out.println("Cashback Rate: " + (cashbackRate * 100) + "%");
        System.out.println("Total Cashback Earned: $" + String.format("%.2f", totalCashback));
    }
}
```

## Interview Questions & Answers

**Q1: What is inheritance? What are its benefits?**
**A:** Inheritance allows a new class to inherit properties and methods from existing classes. Benefits:
- Code reusability and reduced duplication
- Establishes "is-a" relationships
- Enables polymorphism
- Logical organization of classes
- Easy maintenance and updates

**Q2: What's the difference between 'extends' and 'implements'?**
**A:**
- `extends`: Used for class inheritance (single inheritance in Java)
- `implements`: Used for interface implementation (multiple implementation allowed)

**Q3: What is the 'super' keyword used for?**
**A:** 'super' keyword:
- Calls parent class constructor: `super(parameters)`
- Accesses parent class methods: `super.methodName()`
- Accesses parent class variables: `super.variableName`

**Q4: Can constructors be inherited in Java?**
**A:** No, constructors are not inherited. Child classes must define their own constructors, but can call parent constructors using `super()`.

**Q5: What are the rules for method overriding?**
**A:** Method overriding rules:
- Same method signature (name, parameters, return type)
- Cannot reduce access modifier (private → public ❌, public → private ✅)
- Cannot override final, static, or private methods
- Exception handling: can throw same, subclass, or no exceptions

**Q6: What's the difference between method overriding and overloading?**
**A:**
- **Overriding**: Same signature in parent/child classes (runtime polymorphism)
- **Overloading**: Same name, different parameters in same class (compile-time polymorphism)

**Q7: Does Java support multiple inheritance? Why or why not?**
**A:** Java doesn't support multiple class inheritance to avoid diamond problem (ambiguity). However, it supports:
- Multiple interface implementation
- Multi-level inheritance
- Hierarchical inheritance

## Complete Example Program

```java
import java.time.LocalDate;
import java.util.*;

/**
 * Comprehensive Inheritance demonstration
 * University Management System with Employee Hierarchy
 */

// Base class - Person
abstract class Person {
    protected String name;
    protected String id;
    protected String email;
    protected String phoneNumber;
    protected LocalDate birthDate;
    protected String address;
    
    public Person(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getRole();
    public abstract void displaySpecificInfo();
    
    // Concrete methods - inherited by all subclasses
    public void displayBasicInfo() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + (phoneNumber != null ? phoneNumber : "Not provided"));
        System.out.println("Role: " + getRole());
    }
    
    public int getAge() {
        if (birthDate != null) {
            return LocalDate.now().getYear() - birthDate.getYear();
        }
        return 0;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public String getId() { return id; }
    public String getEmail() { return email; }
    
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setAddress(String address) { this.address = address; }
}

// Employee class extending Person
class Employee extends Person {
    protected String employeeId;
    protected String department;
    protected String position;
    protected double salary;
    protected LocalDate hireDate;
    protected String supervisor;
    protected boolean isActive;
    
    public Employee(String name, String id, String email, String employeeId, 
                   String department, String position, double salary) {
        super(name, id, email);
        this.employeeId = employeeId;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.isActive = true;
    }
    
    @Override
    public String getRole() {
        return "Employee";
    }
    
    @Override
    public void displaySpecificInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Salary: $" + String.format("%.2f", salary));
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Supervisor: " + (supervisor != null ? supervisor : "None"));
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
    }
    
    public void work() {
        System.out.println(name + " is working in " + department);
    }
    
    public void attendMeeting() {
        System.out.println(name + " is attending a meeting");
    }
    
    public double calculateAnnualSalary() {
        return salary * 12;
    }
    
    public int getYearsOfService() {
        return LocalDate.now().getYear() - hireDate.getYear();
    }
    
    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    
    // Setters
    public void setSalary(double salary) { this.salary = salary; }
    public void setPosition(String position) { this.position = position; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }
}

// Faculty class extending Employee
class Faculty extends Employee {
    private String specialization;
    private String degree;
    private List<String> coursesTeaching;
    private int researchProjects;
    private List<String> publications;
    
    public Faculty(String name, String id, String email, String employeeId,
                  String specialization, String degree, double salary) {
        super(name, id, email, employeeId, "Academic", "Faculty", salary);
        this.specialization = specialization;
        this.degree = degree;
        this.coursesTeaching = new ArrayList<>();
        this.researchProjects = 0;
        this.publications = new ArrayList<>();
    }
    
    @Override
    public String getRole() {
        return "Faculty Member";
    }
    
    @Override
    public void displaySpecificInfo() {
        super.displaySpecificInfo();
        System.out.println("Specialization: " + specialization);
        System.out.println("Degree: " + degree);
        System.out.println("Courses Teaching: " + coursesTeaching.size());
        System.out.println("Research Projects: " + researchProjects);
        System.out.println("Publications: " + publications.size());
    }
    
    @Override
    public void work() {
        System.out.println(name + " is teaching and conducting research in " + specialization);
    }
    
    public void teachCourse(String courseName) {
        if (!coursesTeaching.contains(courseName)) {
            coursesTeaching.add(courseName);
            System.out.println(name + " is now teaching " + courseName);
        }
    }
    
    public void conductResearch() {
        researchProjects++;
        System.out.println(name + " is conducting research project #" + researchProjects);
    }
    
    public void publishPaper(String paperTitle) {
        publications.add(paperTitle);
        System.out.println(name + " published: " + paperTitle);
    }
    
    public void holdOfficeHours() {
        System.out.println(name + " is holding office hours for students");
    }
    
    // Getters
    public List<String> getCoursesTeaching() { return new ArrayList<>(coursesTeaching); }
    public List<String> getPublications() { return new ArrayList<>(publications); }
    public String getSpecialization() { return specialization; }
}

// AdministrativeStaff class extending Employee
class AdministrativeStaff extends Employee {
    private String officeLocation;
    private List<String> responsibilities;
    private String workShift;
    
    public AdministrativeStaff(String name, String id, String email, String employeeId,
                              String position, String officeLocation, double salary) {
        super(name, id, email, employeeId, "Administration", position, salary);
        this.officeLocation = officeLocation;
        this.responsibilities = new ArrayList<>();
        this.workShift = "9 AM - 5 PM";
    }
    
    @Override
    public String getRole() {
        return "Administrative Staff";
    }
    
    @Override
    public void displaySpecificInfo() {
        super.displaySpecificInfo();
        System.out.println("Office Location: " + officeLocation);
        System.out.println("Work Shift: " + workShift);
        System.out.println("Responsibilities: " + responsibilities.size());
    }
    
    @Override
    public void work() {
        System.out.println(name + " is handling administrative tasks at " + officeLocation);
    }
    
    public void addResponsibility(String responsibility) {
        responsibilities.add(responsibility);
        System.out.println("Added responsibility for " + name + ": " + responsibility);
    }
    
    public void processDocuments() {
        System.out.println(name + " is processing documents");
    }
    
    public void assistStudents() {
        System.out.println(name + " is assisting students with administrative queries");
    }
    
    public void organizeEvent(String eventName) {
        System.out.println(name + " is organizing event: " + eventName);
    }
    
    // Getters
    public String getOfficeLocation() { return officeLocation; }
    public List<String> getResponsibilities() { return new ArrayList<>(responsibilities); }
}

// Student class extending Person
class Student extends Person {
    private String studentId;
    private String major;
    private int year;
    private double gpa;
    private List<String> coursesEnrolled;
    private String advisor;
    private boolean isFullTime;
    
    public Student(String name, String id, String email, String studentId, 
                  String major, int year) {
        super(name, id, email);
        this.studentId = studentId;
        this.major = major;
        this.year = year;
        this.gpa = 0.0;
        this.coursesEnrolled = new ArrayList<>();
        this.isFullTime = true;
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
    
    @Override
    public void displaySpecificInfo() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Major: " + major);
        System.out.println("Year: " + year + " (" + getYearName() + ")");
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Courses Enrolled: " + coursesEnrolled.size());
        System.out.println("Advisor: " + (advisor != null ? advisor : "Not assigned"));
        System.out.println("Status: " + (isFullTime ? "Full-time" : "Part-time"));
    }
    
    public void enrollInCourse(String courseName) {
        if (!coursesEnrolled.contains(courseName)) {
            coursesEnrolled.add(courseName);
            System.out.println(name + " enrolled in " + courseName);
        }
    }
    
    public void dropCourse(String courseName) {
        if (coursesEnrolled.remove(courseName)) {
            System.out.println(name + " dropped " + courseName);
        }
    }
    
    public void study() {
        System.out.println(name + " is studying " + major);
    }
    
    public void takeExam(String subject) {
        System.out.println(name + " is taking exam in " + subject);
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
    
    // Getters and setters
    public String getStudentId() { return studentId; }
    public String getMajor() { return major; }
    public int getYear() { return year; }
    public double getGpa() { return gpa; }
    
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setAdvisor(String advisor) { this.advisor = advisor; }
    public void setYear(int year) { this.year = year; }
}

// GraduateStudent class extending Student
class GraduateStudent extends Student {
    private String thesisTitle;
    private String researchAdvisor;
    private String degreeType;  // "MS" or "PhD"
    private boolean isThesisCompleted;
    
    public GraduateStudent(String name, String id, String email, String studentId,
                          String major, String degreeType) {
        super(name, id, email, studentId, major, 5);  // Graduate level
        this.degreeType = degreeType;
        this.isThesisCompleted = false;
    }
    
    @Override
    public String getRole() {
        return "Graduate Student (" + degreeType + ")";
    }
    
    @Override
    public void displaySpecificInfo() {
        super.displaySpecificInfo();
        System.out.println("Degree Type: " + degreeType);
        System.out.println("Research Advisor: " + (researchAdvisor != null ? researchAdvisor : "Not assigned"));
        System.out.println("Thesis Title: " + (thesisTitle != null ? thesisTitle : "Not decided"));
        System.out.println("Thesis Status: " + (isThesisCompleted ? "Completed" : "In Progress"));
    }
    
    @Override
    public void study() {
        System.out.println(name + " is conducting advanced research in " + getMajor());
    }
    
    public void conductResearch() {
        System.out.println(name + " is conducting research for " + degreeType + " degree");
    }
    
    public void writeThesis() {
        System.out.println(name + " is working on thesis: " + 
                          (thesisTitle != null ? thesisTitle : "Untitled"));
    }
    
    public void defendThesis() {
        if (thesisTitle != null) {
            isThesisCompleted = true;
            System.out.println(name + " successfully defended thesis: " + thesisTitle);
        } else {
            System.out.println("Cannot defend thesis - no thesis title set");
        }
    }
    
    // Setters
    public void setThesisTitle(String thesisTitle) { this.thesisTitle = thesisTitle; }
    public void setResearchAdvisor(String researchAdvisor) { this.researchAdvisor = researchAdvisor; }
}

// Main demonstration class
public class InheritanceExample {
    public static void main(String[] args) {
        System.out.println("=== INHERITANCE DEMONSTRATION ===");
        
        demonstrateBasicInheritance();
        demonstrateMethodOverriding();
        demonstratePolymorphism();
        demonstrateUniversitySystem();
    }
    
    public static void demonstrateBasicInheritance() {
        System.out.println("\n--- BASIC INHERITANCE ---");
        
        // Create different types of people
        Faculty faculty = new Faculty("Dr. John Smith", "P001", "john.smith@university.edu",
                                    "EMP001", "Computer Science", "PhD", 85000);
        
        AdministrativeStaff admin = new AdministrativeStaff("Mary Johnson", "P002", 
                                                          "mary.johnson@university.edu",
                                                          "EMP002", "Registrar", "Room 101", 55000);
        
        Student student = new Student("Alice Brown", "P003", "alice.brown@student.university.edu",
                                    "STU001", "Computer Science", 3);
        
        // Show inheritance in action
        System.out.println("=== Faculty Information ===");
        faculty.displayBasicInfo();   // Inherited from Person
        faculty.displaySpecificInfo(); // Overridden in Faculty
        
        System.out.println("\n=== Administrative Staff Information ===");
        admin.displayBasicInfo();     // Inherited from Person
        admin.displaySpecificInfo();  // Overridden in AdministrativeStaff
        
        System.out.println("\n=== Student Information ===");
        student.displayBasicInfo();   // Inherited from Person
        student.displaySpecificInfo(); // Overridden in Student
    }
    
    public static void demonstrateMethodOverriding() {
        System.out.println("\n--- METHOD OVERRIDING ---");
        
        Faculty professor = new Faculty("Dr. Sarah Davis", "P004", "sarah.davis@university.edu",
                                      "EMP003", "Mathematics", "PhD", 90000);
        
        Student undergrad = new Student("Bob Wilson", "P005", "bob.wilson@student.university.edu",
                                      "STU002", "Mathematics", 2);
        
        // Same method name, different implementations
        System.out.println("=== Different work() implementations ===");
        professor.work();  // Faculty version
        undergrad.study(); // Student doesn't override work(), but has study()
        
        // Method overriding in displaySpecificInfo()
        System.out.println("\n=== Overridden displaySpecificInfo() ===");
        professor.displaySpecificInfo();
        System.out.println();
        undergrad.displaySpecificInfo();
    }
    
    public static void demonstratePolymorphism() {
        System.out.println("\n--- POLYMORPHISM THROUGH INHERITANCE ---");
        
        // Array of Person references pointing to different subclass objects
        Person[] people = {
            new Faculty("Dr. Lisa Chen", "P006", "lisa.chen@university.edu",
                       "EMP004", "Physics", "PhD", 95000),
            new AdministrativeStaff("Tom Garcia", "P007", "tom.garcia@university.edu",
                                  "EMP005", "IT Support", "Room 205", 50000),
            new Student("Emma Davis", "P008", "emma.davis@student.university.edu",
                       "STU003", "Physics", 4),
            new GraduateStudent("Alex Kim", "P009", "alex.kim@student.university.edu",
                              "STU004", "Computer Science", "PhD")
        };
        
        // Polymorphic method calls
        for (Person person : people) {
            System.out.println("=== " + person.getName() + " ===");
            person.displayBasicInfo();    // Same method, different behavior
            System.out.println();
            person.displaySpecificInfo(); // Overridden method
            System.out.println("Role: " + person.getRole()); // Abstract method implementation
            System.out.println();
        }
    }
    
    public static void demonstrateUniversitySystem() {
        System.out.println("\n--- COMPLETE UNIVERSITY SYSTEM ---");
        
        // Create faculty members
        Faculty csProfessor = new Faculty("Dr. Michael Johnson", "P010", 
                                        "michael.johnson@university.edu",
                                        "EMP006", "Computer Science", "PhD", 88000);
        csProfessor.setPhoneNumber("555-0101");
        
        Faculty mathProfessor = new Faculty("Dr. Jennifer Lee", "P011",
                                          "jennifer.lee@university.edu", 
                                          "EMP007", "Mathematics", "PhD", 85000);
        mathProfessor.setPhoneNumber("555-0102");
        
        // Add courses and research
        csProfessor.teachCourse("Data Structures");
        csProfessor.teachCourse("Algorithms");
        csProfessor.conductResearch();
        csProfessor.publishPaper("Advanced Graph Algorithms in Distributed Systems");
        
        mathProfessor.teachCourse("Calculus I");
        mathProfessor.teachCourse("Linear Algebra");
        mathProfessor.conductResearch();
        mathProfessor.publishPaper("Non-linear Optimization Techniques");
        
        // Create administrative staff
        AdministrativeStaff registrar = new AdministrativeStaff("Patricia Moore", "P012",
                                                              "patricia.moore@university.edu",
                                                              "EMP008", "Senior Registrar", 
                                                              "Administration Building", 62000);
        registrar.addResponsibility("Student Registration");
        registrar.addResponsibility("Transcript Management");
        registrar.addResponsibility("Graduation Processing");
        
        // Create students
        Student juniorStudent = new Student("David Thompson", "P013", 
                                          "david.thompson@student.university.edu",
                                          "STU005", "Computer Science", 3);
        juniorStudent.setGpa(3.7);
        juniorStudent.setAdvisor("Dr. Michael Johnson");
        
        GraduateStudent phdStudent = new GraduateStudent("Lisa Zhang", "P014",
                                                       "lisa.zhang@student.university.edu",
                                                       "STU006", "Computer Science", "PhD");
        phdStudent.setGpa(3.9);
        phdStudent.setResearchAdvisor("Dr. Michael Johnson");
        phdStudent.setThesisTitle("Machine Learning Applications in Distributed Computing");
        
        // Demonstrate university activities
        System.out.println("=== UNIVERSITY DAILY ACTIVITIES ===");
        
        System.out.println("\n--- Faculty Activities ---");
        csProfessor.work();
        csProfessor.holdOfficeHours();
        csProfessor.attendMeeting();
        
        mathProfessor.work();
        mathProfessor.holdOfficeHours();
        
        System.out.println("\n--- Administrative Activities ---");
        registrar.work();
        registrar.processDocuments();
        registrar.assistStudents();
        registrar.organizeEvent("Fall Registration");
        
        System.out.println("\n--- Student Activities ---");
        juniorStudent.study();
        juniorStudent.enrollInCourse("Data Structures");
        juniorStudent.enrollInCourse("Software Engineering");
        juniorStudent.takeExam("Algorithms");
        
        phdStudent.study();
        phdStudent.conductResearch();
        phdStudent.writeThesis();
        
        // Display complete information
        System.out.println("\n=== COMPLETE UNIVERSITY ROSTER ===");
        
        Person[] universityMembers = {
            csProfessor, mathProfessor, registrar, juniorStudent, phdStudent
        };
        
        for (Person member : universityMembers) {
            System.out.println("\n" + "=".repeat(50));
            member.displayBasicInfo();
            System.out.println();
            member.displaySpecificInfo();
        }
        
        System.out.println("\n=== INHERITANCE HIERARCHY SUMMARY ===");
        System.out.println("Person (Abstract Base Class)");
        System.out.println("├── Employee");
        System.out.println("│   ├── Faculty");
        System.out.println("│   └── AdministrativeStaff");
        System.out.println("└── Student");
        System.out.println("    └── GraduateStudent");
        
        System.out.println("\nKey Inheritance Concepts Demonstrated:");
        System.out.println("1. Single inheritance (each class extends one parent)");
        System.out.println("2. Multi-level inheritance (GraduateStudent → Student → Person)");
        System.out.println("3. Method overriding (@Override annotation)");
        System.out.println("4. Abstract methods and classes");
        System.out.println("5. Constructor chaining (super() calls)");
        System.out.println("6. Polymorphism through inheritance");
        System.out.println("7. Access modifiers in inheritance (protected fields)");
    }
}
```

## Key Takeaways

1. **Inheritance promotes code reusability** through "is-a" relationships
2. **Single inheritance** - Java classes can extend only one parent class
3. **Multi-level inheritance** - Child can become parent to another class
4. **Method overriding** enables specialized behavior in subclasses
5. **Constructor chaining** with `super()` maintains initialization order
6. **Access modifiers** control visibility in inheritance hierarchy
7. **Abstract classes** define common interface with some implementation
8. **Polymorphism** works through inheritance and method overriding
9. **Protected access** allows subclass access while maintaining encapsulation
10. **'super' keyword** provides access to parent class members and constructors

---

*Remember: Inheritance is like a family tree - children inherit traits from parents but can develop their own unique characteristics and behaviors!*