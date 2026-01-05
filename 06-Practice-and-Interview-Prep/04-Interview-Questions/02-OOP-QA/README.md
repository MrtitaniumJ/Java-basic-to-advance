# OOP Concepts Interview Questions & Answers

## Q1: What is Encapsulation? Provide an Example

**Answer:**
Encapsulation is the bundling of data (variables) and methods that operate on that data within a single unit (class), while hiding internal implementation details. It uses access modifiers to control visibility and protects data from unauthorized or unintended modification.

**Key Benefits:**
- Data protection through private variables
- Controlled access via getter/setter methods
- Validation of data before modification
- Internal changes don't affect external code
- Maintains invariants and class contracts

**Code Example:**
```java
public class BankAccount {
    private double balance;
    private String accountNumber;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

---

## Q2: Explain Inheritance and the Super Keyword

**Answer:**
Inheritance allows a class (child/subclass) to inherit properties and methods from another class (parent/superclass). This promotes code reuse and establishes hierarchical relationships. The `super` keyword refers to the parent class and is used to call parent constructors, methods, or access parent variables.

**Super Keyword Uses:**
- Call parent class constructor: `super()`
- Call parent class method: `super.methodName()`
- Access parent class variable: `super.variableName`
- Must be first statement in child constructor

**Code Example:**
```java
class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
}

class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name);  // Call parent constructor
        this.breed = breed;
    }
    
    @Override
    public void eat() {
        super.eat();  // Call parent method
        System.out.println("Dog eating kibble");
    }
    
    public void bark() {
        System.out.println(name + " barks: Woof!");
    }
}
```

---

## Q3: Method Overloading vs Method Overriding

**Answer:**

| Feature | Overloading | Overriding |
|---------|------------|-----------|
| **Definition** | Multiple methods with same name, different parameters | Child class reimplements parent method |
| **Type** | Compile-time (Static) polymorphism | Runtime (Dynamic) polymorphism |
| **Signature** | Must differ (parameters/types) | Must be identical |
| **Return Type** | Can differ | Must be same or covariant |
| **Class** | Same class or inheritance | Inheritance only |
| **Access Modifier** | Can differ | Cannot be more restrictive |

**Code Example:**
```java
// Overloading (same class, different parameters)
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}

// Overriding (inheritance, same signature)
class Animal {
    public void sound() {
        System.out.println("Generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("Woof woof");
    }
}

class Cat extends Animal {
    @Override
    public void sound() {
        System.out.println("Meow");
    }
}
```

---

## Q4: Polymorphism - Compile-Time vs Runtime

**Answer:**

**Compile-Time Polymorphism (Static):**
- Resolved during compilation via method overloading
- Method to call determined at compile time
- Type-safe with compiler checks
- Example: `System.out.println()` - overloaded for different types

**Runtime Polymorphism (Dynamic):**
- Resolved during execution via method overriding
- Method to call determined at runtime based on object type
- Same reference can call different methods
- Enables flexible, extensible code

**Code Example:**
```java
class Shape {
    public void draw() {
        System.out.println("Drawing shape");
    }
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
}

class Rectangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing rectangle");
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        // Compile-time polymorphism
        Calculator calc = new Calculator();
        System.out.println(calc.add(5, 10));
        System.out.println(calc.add(5.5, 10.5));
        
        // Runtime polymorphism
        Shape shape1 = new Circle();
        Shape shape2 = new Rectangle();
        shape1.draw();  // Calls Circle's draw()
        shape2.draw();  // Calls Rectangle's draw()
    }
}
```

---

## Q5: Abstract Class vs Interface

**Answer:**

| Aspect | Abstract Class | Interface |
|--------|----------------|-----------|
| **Instantiation** | Cannot instantiate | Cannot instantiate |
| **Variables** | Any (private, public, protected) | Only public static final |
| **Methods** | Concrete and abstract | Abstract (Java 8+: default, static) |
| **Inheritance** | Single (extends) | Multiple (implements) |
| **Constructor** | Can have | Cannot have |
| **Access Modifiers** | Any for methods | Public by default |
| **Use Case** | IS-A relationship, shared code | Contract/behavior definition |

**Code Example:**
```java
abstract class Vehicle {
    private String color;
    
    public Vehicle(String color) {
        this.color = color;
    }
    
    abstract void start();
    
    public void stop() {
        System.out.println("Stopping");
    }
}

interface Drawable {
    void draw();
    
    default void render() {
        System.out.println("Rendering");
    }
}

class Car extends Vehicle implements Drawable {
    public Car(String color) {
        super(color);
    }
    
    @Override
    public void start() {
        System.out.println("Car starting");
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing car");
    }
}
```

---

## Q6: What is the Diamond Problem?

**Answer:**
The Diamond Problem occurs in multiple inheritance when a class inherits from two classes that both inherit from a common ancestor. This creates ambiguity: which parent's method should be called?

**Visual Structure:**
```
        A (method foo())
       / \
      B   C (both override foo())
       \ /
        D (which foo() to use?)
```

**Java's Solution:**
Java prevents this by:
- Disallowing multiple class inheritance (single inheritance only)
- Using interface-based design with explicit resolution
- Default methods can be disambiguated with explicit calls

**Code Example:**
```java
interface A {
    default void display() {
        System.out.println("A's display");
    }
}

interface B extends A {
    @Override
    default void display() {
        System.out.println("B's display");
    }
}

interface C extends A {
    @Override
    default void display() {
        System.out.println("C's display");
    }
}

class D implements B, C {
    @Override
    public void display() {
        B.super.display();  // Explicitly choose B's implementation
    }
}
```

---

## Q7: SOLID Principles Overview

**Answer:**

**S - Single Responsibility Principle:**
A class should have only one reason to change. Each class handles one responsibility.

**O - Open/Closed Principle:**
Open for extension, closed for modification. Add functionality without changing existing code.

**L - Liskov Substitution Principle:**
Subclasses must be substitutable for base classes without breaking functionality.

**I - Interface Segregation Principle:**
Clients shouldn't depend on methods they don't use. Create focused, specific interfaces.

**D - Dependency Inversion Principle:**
Depend on abstractions, not concrete implementations.

**Code Example (SRP):**
```java
// Bad: Multiple responsibilities
class UserService {
    public void createUser(String name) { }
    public void sendEmail(String email) { }
    public void logActivity(String log) { }
}

// Good: Single responsibility each
class UserService {
    public void createUser(String name) { }
}

class EmailService {
    public void sendEmail(String email) { }
}

class LoggerService {
    public void log(String message) { }
}
```

---

## Q8: Composition vs Inheritance

**Answer:**

**Composition (HAS-A):**
- Objects are composed of other objects
- Flexible and maintainable
- Can change behavior at runtime
- Preferred approach in modern design

**Inheritance (IS-A):**
- Child inherits from parent
- Tighter coupling
- Cannot be changed at runtime
- Use only for true specialization

**Code Example:**
```java
// Bad: Inheritance where composition is better
class Bird {
    public void fly() { }
}

class Penguin extends Bird {  // Problem: penguins don't fly!
    @Override
    public void fly() {
        throw new UnsupportedOperationException();
    }
}

// Good: Composition approach
interface FlyingBehavior {
    void fly();
}

class Bird {
    private FlyingBehavior behavior;
    
    public Bird(FlyingBehavior behavior) {
        this.behavior = behavior;
    }
    
    public void fly() {
        behavior.fly();
    }
}

class Penguin {
    private FlyingBehavior behavior = () -> 
        System.out.println("Penguin cannot fly");
}
```

---

## Q9: Dependency Injection Basics

**Answer:**
Dependency Injection is a design pattern providing objects with their dependencies rather than having them create dependencies. It reduces coupling and improves testability.

**Types of DI:**
1. **Constructor Injection** - Dependency passed via constructor
2. **Setter Injection** - Dependency set via setter method
3. **Interface Injection** - Dependency injected through interface

**Code Example:**
```java
// Without DI (tightly coupled)
class UserService {
    private DatabaseConnection db = new DatabaseConnection();
}

// With Constructor Injection (loosely coupled)
class UserService {
    private DatabaseConnection db;
    
    public UserService(DatabaseConnection db) {
        this.db = db;
    }
    
    public void saveUser(User user) {
        db.save(user);
    }
}

// Usage
UserService service = new UserService(new DatabaseConnection());
```

**Benefits:**
- Easy to test (mock dependencies)
- Flexible (swap implementations)
- Loose coupling
- Reusable components

---

## Q10: Design Patterns - Singleton and Factory

**Answer:**

**Singleton Pattern:**
Ensures only one instance of a class exists, providing global access.

```java
class DatabaseConnection {
    private static DatabaseConnection instance;
    
    private DatabaseConnection() { }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

// Thread-safe variant (eager initialization)
class DatabaseConnection2 {
    private static final DatabaseConnection2 instance = 
        new DatabaseConnection2();
    
    private DatabaseConnection2() { }
    
    public static DatabaseConnection2 getInstance() {
        return instance;
    }
}
```

**Factory Pattern:**
Creates objects without specifying exact classes, promoting loose coupling.

```java
interface Animal {
    void sound();
}

class Dog implements Animal {
    @Override
    public void sound() {
        System.out.println("Woof");
    }
}

class AnimalFactory {
    public static Animal createAnimal(String type) {
        if ("dog".equals(type)) {
            return new Dog();
        }
        return null;
    }
}

// Usage
Animal animal = AnimalFactory.createAnimal("dog");
```

---

## Q11: Liskov Substitution Principle (LSP)

**Answer:**
Subtypes must be substitutable for their base types without breaking the application. Child classes should honor the contract of parent classes.

**Violation Example:**
```java
class Bird {
    public void fly() {
        System.out.println("Flying");
    }
}

class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly");
    }
}

// Problem: Cannot safely use Penguin as Bird
public void makeBirdFly(Bird bird) {
    bird.fly();  // Fails for Penguin!
}
```

**Correct Implementation:**
```java
abstract class Bird {
    abstract void move();
}

class FlyingBird extends Bird {
    @Override
    public void move() {
        System.out.println("Flying");
    }
}

class Penguin extends Bird {
    @Override
    public void move() {
        System.out.println("Swimming");
    }
}

// Now Penguin can safely substitute Bird
public void makeAnimalMove(Bird bird) {
    bird.move();  // Works for both flying and swimming birds
}
```

---

## Q12: Interface Segregation Principle (ISP)

**Answer:**
Clients should not be forced to depend on interfaces they don't use. Create specific, focused interfaces rather than large, general-purpose ones.

**Violation Example:**
```java
// Bad: Fat interface
interface Worker {
    void work();
    void eat();
    void sleep();
}

class Robot implements Worker {
    @Override
    public void work() { }
    @Override
    public void eat() { }  // Not applicable!
    @Override
    public void sleep() { } // Not applicable!
}
```

**Correct Implementation:**
```java
// Good: Segregated interfaces
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

class Robot implements Workable {
    @Override
    public void work() { }
    // Not forced to implement eat() and sleep()
}

class Human implements Workable, Eatable, Sleepable {
    @Override
    public void work() { }
    @Override
    public void eat() { }
    @Override
    public void sleep() { }
}
```

**Benefits:**
- Cleaner code
- Flexible implementations
- Reduced dependencies
- Easier testing and maintenance
