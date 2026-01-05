# Polymorphism in Java - One Interface, Many Forms

## Simple Explanation

Think of **Polymorphism** as **one remote control** working with **different devices**:

- **Universal remote** = Same interface (buttons) works with TV, DVD player, sound system
- **Payment methods** = Same "pay" action works with credit card, cash, mobile wallet
- **Vehicles** = Same "start" command works for cars, motorcycles, airplanes
- **Animals** = Same "makeSound" command produces different sounds (bark, meow, roar)

### Real-World Analogies
- **Shape drawing** = Same "draw" command creates circles, rectangles, triangles
- **Media player** = Same "play" button works for videos, music, podcasts
- **Calculator** = Same "+" operator works for integers, decimals, fractions
- **Employees** = Same "work" method implemented differently by developers, managers, designers

## Professional Definition

**Polymorphism** is an OOP principle that allows objects of different classes to be treated as objects of a common base class while maintaining their specific behaviors. It enables a single interface to represent different underlying forms (data types). In Java, polymorphism is achieved through method overriding (runtime polymorphism) and method overloading (compile-time polymorphism).

## Types of Polymorphism in Java

### 1. Compile-time Polymorphism (Static Polymorphism)
- **Method Overloading**
- **Operator Overloading** (limited in Java)
- Resolved at compile time

### 2. Runtime Polymorphism (Dynamic Polymorphism)
- **Method Overriding**
- **Interface Implementation**
- Resolved at runtime using dynamic method dispatch

## Why Use Polymorphism?

### Benefits:
- **Code Flexibility**: Same code works with different object types
- **Maintainability**: Add new types without changing existing code
- **Extensibility**: Easy to extend functionality
- **Code Reusability**: Write generic code that works with multiple types
- **Abstraction**: Hide implementation details behind common interface
- **Loose Coupling**: Reduce dependencies between components

### Problems Without Polymorphism:
```java
// WITHOUT POLYMORPHISM - Repetitive and inflexible code
class ShapeProcessor {
    public void processCircle(Circle circle) {
        circle.drawCircle();
        System.out.println("Area: " + circle.calculateCircleArea());
    }
    
    public void processRectangle(Rectangle rectangle) {
        rectangle.drawRectangle();
        System.out.println("Area: " + rectangle.calculateRectangleArea());
    }
    
    public void processTriangle(Triangle triangle) {
        triangle.drawTriangle();
        System.out.println("Area: " + triangle.calculateTriangleArea());
    }
    
    // Need new method for each new shape type!
    // Violates Open-Closed Principle
}
```

### With Polymorphism - Clean and Extensible:
```java
// WITH POLYMORPHISM - One method handles all shapes
class ShapeProcessor {
    public void processShape(Shape shape) {  // Polymorphic parameter
        shape.draw();         // Calls appropriate draw method
        System.out.println("Area: " + shape.calculateArea());
    }
    
    // Works with ANY shape type without modification!
    // Follows Open-Closed Principle
}
```

## Method Overloading (Compile-time Polymorphism)

### 1. Basic Method Overloading
```java
class Calculator {
    // Different parameter counts
    public int add(int a, int b) {
        System.out.println("Adding two integers: " + a + " + " + b);
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        System.out.println("Adding three integers: " + a + " + " + b + " + " + c);
        return a + b + c;
    }
    
    // Different parameter types
    public double add(double a, double b) {
        System.out.println("Adding two doubles: " + a + " + " + b);
        return a + b;
    }
    
    public String add(String a, String b) {
        System.out.println("Concatenating strings: " + a + " + " + b);
        return a + b;
    }
    
    // Different parameter order
    public String format(String text, int number) {
        return text + ": " + number;
    }
    
    public String format(int number, String text) {
        return number + " - " + text;
    }
    
    // Array parameters
    public int add(int... numbers) {  // Varargs
        System.out.print("Adding array of integers: ");
        int sum = 0;
        for (int num : numbers) {
            sum += num;
            System.out.print(num + " ");
        }
        System.out.println();
        return sum;
    }
}

// Usage
Calculator calc = new Calculator();
System.out.println("Result: " + calc.add(5, 3));              // int add(int, int)
System.out.println("Result: " + calc.add(5, 3, 2));           // int add(int, int, int)
System.out.println("Result: " + calc.add(5.5, 3.2));          // double add(double, double)
System.out.println("Result: " + calc.add("Hello", "World"));   // String add(String, String)
System.out.println(calc.format("Score", 95));                 // format(String, int)
System.out.println(calc.format(95, "Score"));                 // format(int, String)
System.out.println("Result: " + calc.add(1, 2, 3, 4, 5));     // varargs version
```

### 2. Constructor Overloading
```java
class Student {
    private String name;
    private String id;
    private String email;
    private int age;
    private String major;
    private double gpa;
    
    // Default constructor
    public Student() {
        this.name = "Unknown";
        this.id = "000000";
        this.email = "unknown@email.com";
        this.age = 0;
        this.major = "Undeclared";
        this.gpa = 0.0;
        System.out.println("Default constructor called");
    }
    
    // Constructor with name and id
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.email = name.toLowerCase().replace(" ", ".") + "@student.edu";
        this.age = 0;
        this.major = "Undeclared";
        this.gpa = 0.0;
        System.out.println("Constructor with name and id called");
    }
    
    // Constructor with name, id, and major
    public Student(String name, String id, String major) {
        this(name, id);  // Call previous constructor
        this.major = major;
        System.out.println("Constructor with name, id, and major called");
    }
    
    // Constructor with all basic info
    public Student(String name, String id, String major, int age) {
        this(name, id, major);  // Constructor chaining
        this.age = age;
        System.out.println("Constructor with basic info called");
    }
    
    // Full constructor
    public Student(String name, String id, String email, int age, String major, double gpa) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.age = age;
        this.major = major;
        this.gpa = gpa;
        System.out.println("Full constructor called");
    }
    
    // Copy constructor
    public Student(Student other) {
        this.name = other.name;
        this.id = "COPY_" + other.id;
        this.email = "copy." + other.email;
        this.age = other.age;
        this.major = other.major;
        this.gpa = other.gpa;
        System.out.println("Copy constructor called");
    }
    
    public void displayInfo() {
        System.out.println("Student: " + name + " (ID: " + id + ")");
        System.out.println("Email: " + email + ", Age: " + age);
        System.out.println("Major: " + major + ", GPA: " + gpa);
    }
}

// Usage demonstrating constructor overloading
Student s1 = new Student();
Student s2 = new Student("Alice Johnson", "12345");
Student s3 = new Student("Bob Smith", "12346", "Computer Science");
Student s4 = new Student("Carol Davis", "12347", "Mathematics", 20);
Student s5 = new Student("David Brown", "12348", "david.brown@student.edu", 21, "Physics", 3.8);
Student s6 = new Student(s5);  // Copy constructor
```

### 3. Method Overloading Rules and Best Practices
```java
class OverloadingDemo {
    // VALID: Different number of parameters
    public void process() { System.out.println("No parameters"); }
    public void process(int x) { System.out.println("One int: " + x); }
    public void process(int x, int y) { System.out.println("Two ints: " + x + ", " + y); }
    
    // VALID: Different parameter types
    public void process(double x) { System.out.println("One double: " + x); }
    public void process(String s) { System.out.println("One string: " + s); }
    
    // VALID: Different order of parameter types
    public void process(int x, String s) { System.out.println("Int then String: " + x + ", " + s); }
    public void process(String s, int x) { System.out.println("String then Int: " + s + ", " + x); }
    
    // INVALID: Cannot overload based on return type alone
    // public int process(int x) { return x; }  // Compilation error!
    
    // INVALID: Parameter names don't matter for overloading
    // public void process(int y) { }  // Compilation error! Same as process(int x)
    
    // VALID: Array vs varargs (but confusing, avoid this)
    public void process(int[] array) { System.out.println("Array parameter"); }
    public void process(int... varargs) { System.out.println("Varargs parameter"); }
    // Note: This creates ambiguity - avoid having both
    
    // VALID: Wrapper classes vs primitives
    public void process(int primitive) { System.out.println("Primitive int: " + primitive); }
    public void process(Integer wrapper) { System.out.println("Integer wrapper: " + wrapper); }
    
    // Demonstration of autoboxing/unboxing with overloading
    public void demonstrate() {
        process(5);           // Calls primitive version (int)
        process(Integer.valueOf(5));  // Calls wrapper version (Integer)
        process((Integer) 5); // Explicit cast to Integer
    }
}
```

## Method Overriding (Runtime Polymorphism)

### 1. Basic Method Overriding
```java
// Base class
class Animal {
    protected String name;
    protected String species;
    
    public Animal(String name, String species) {
        this.name = name;
        this.species = species;
    }
    
    // Method that will be overridden
    public void makeSound() {
        System.out.println(name + " makes a generic animal sound");
    }
    
    public void move() {
        System.out.println(name + " moves around");
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    // Method that provides common functionality
    public void displayInfo() {
        System.out.println("Animal: " + name + " (" + species + ")");
    }
}

// Derived classes with overridden methods
class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name, "Canine");
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " barks: Woof! Woof!");
    }
    
    @Override
    public void move() {
        System.out.println(name + " runs and wags tail");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " eats dog food eagerly");
    }
    
    // Additional method specific to Dog
    public void fetch() {
        System.out.println(name + " fetches the ball");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Breed: " + breed);
    }
}

class Cat extends Animal {
    private boolean isIndoor;
    
    public Cat(String name, boolean isIndoor) {
        super(name, "Feline");
        this.isIndoor = isIndoor;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " meows: Meow! Purr...");
    }
    
    @Override
    public void move() {
        System.out.println(name + " moves gracefully and silently");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " eats cat food delicately");
    }
    
    @Override
    public void sleep() {
        System.out.println(name + " sleeps 16 hours a day");
    }
    
    // Additional method specific to Cat
    public void climb() {
        System.out.println(name + " climbs up the cat tree");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Location: " + (isIndoor ? "Indoor" : "Outdoor"));
    }
}

class Bird extends Animal {
    private boolean canFly;
    
    public Bird(String name, boolean canFly) {
        super(name, "Avian");
        this.canFly = canFly;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " chirps: Tweet! Tweet!");
    }
    
    @Override
    public void move() {
        if (canFly) {
            System.out.println(name + " soars through the sky");
        } else {
            System.out.println(name + " walks and hops on the ground");
        }
    }
    
    @Override
    public void eat() {
        System.out.println(name + " pecks at seeds and insects");
    }
    
    // Additional method specific to Bird
    public void buildNest() {
        System.out.println(name + " builds a nest");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Flight: " + (canFly ? "Can fly" : "Cannot fly"));
    }
}
```

### 2. Polymorphic Method Calls
```java
public class PolymorphismDemo {
    public static void main(String[] args) {
        // Creating objects of different types
        Animal[] animals = {
            new Dog("Buddy", "Golden Retriever"),
            new Cat("Whiskers", true),
            new Bird("Tweety", true),
            new Bird("Penguin", false),
            new Dog("Rex", "German Shepherd"),
            new Cat("Shadow", false)
        };
        
        System.out.println("=== POLYMORPHIC METHOD CALLS ===");
        
        // Same method calls, different behaviors
        for (Animal animal : animals) {
            System.out.println("\n" + "=".repeat(30));
            animal.displayInfo();     // Each class may override this
            animal.makeSound();       // Definitely overridden by each subclass
            animal.move();           // Overridden with specific behaviors
            animal.eat();            // Some classes override this
            animal.sleep();          // Only Cat overrides this
        }
        
        // Demonstrating runtime decision making
        System.out.println("\n=== RUNTIME POLYMORPHISM ===");
        demonstrateRuntimePolymorphism(new Dog("Max", "Bulldog"));
        demonstrateRuntimePolymorphism(new Cat("Mittens", true));
        demonstrateRuntimePolymorphism(new Bird("Eagle", true));
    }
    
    // Method that accepts any Animal and works polymorphically
    public static void demonstrateRuntimePolymorphism(Animal animal) {
        System.out.println("\nDemonstrating with: " + animal.name);
        
        // These calls are resolved at runtime based on actual object type
        animal.makeSound();  // Different implementation for each type
        animal.move();       // Different implementation for each type
        
        // Check actual type and call specific methods
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;  // Downcasting
            dog.fetch();
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;  // Downcasting
            cat.climb();
        } else if (animal instanceof Bird) {
            Bird bird = (Bird) animal;  // Downcasting
            bird.buildNest();
        }
    }
}
```

### 3. Virtual Method Calls and Dynamic Dispatch
```java
class Shape {
    protected String color;
    protected boolean filled;
    
    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }
    
    // Virtual method - can be overridden
    public void draw() {
        System.out.println("Drawing a generic shape");
    }
    
    public double calculateArea() {
        return 0.0;  // Default implementation
    }
    
    public void displayInfo() {
        System.out.println("Shape - Color: " + color + ", Filled: " + filled);
        System.out.println("Area: " + calculateArea());  // Virtual call!
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(String color, boolean filled, double radius) {
        super(color, filled);
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius " + radius);
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(String color, boolean filled, double width, double height) {
        super(color, filled);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle " + width + "x" + height);
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

// Demonstration
Shape circle = new Circle("Red", true, 5.0);
Shape rectangle = new Rectangle("Blue", false, 4.0, 6.0);

// Virtual method calls - resolved at runtime
circle.displayInfo();     // Calls Circle's calculateArea() from Shape's displayInfo()
rectangle.displayInfo();  // Calls Rectangle's calculateArea() from Shape's displayInfo()
```

## Interface-based Polymorphism

### 1. Interface Implementation
```java
// Interface defining contract
interface Drawable {
    void draw();
    void resize(double factor);
    double getArea();
}

// Interface for moveable objects
interface Moveable {
    void move(int x, int y);
    void getPosition();
}

// Class implementing multiple interfaces
class DrawableCircle implements Drawable, Moveable {
    private double radius;
    private int x, y;
    private String color;
    
    public DrawableCircle(double radius, int x, int y, String color) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing " + color + " circle at (" + x + ", " + y + 
                          ") with radius " + radius);
    }
    
    @Override
    public void resize(double factor) {
        radius *= factor;
        System.out.println("Resized circle, new radius: " + radius);
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        System.out.println("Moved circle to (" + x + ", " + y + ")");
    }
    
    @Override
    public void getPosition() {
        System.out.println("Circle position: (" + x + ", " + y + ")");
    }
}

class DrawableRectangle implements Drawable, Moveable {
    private double width, height;
    private int x, y;
    private String color;
    
    public DrawableRectangle(double width, double height, int x, int y, String color) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing " + color + " rectangle at (" + x + ", " + y + 
                          ") with dimensions " + width + "x" + height);
    }
    
    @Override
    public void resize(double factor) {
        width *= factor;
        height *= factor;
        System.out.println("Resized rectangle, new dimensions: " + width + "x" + height);
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    @Override
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        System.out.println("Moved rectangle to (" + x + ", " + y + ")");
    }
    
    @Override
    public void getPosition() {
        System.out.println("Rectangle position: (" + x + ", " + y + ")");
    }
}

// Using interface polymorphism
Drawable[] drawables = {
    new DrawableCircle(5.0, 10, 20, "Red"),
    new DrawableRectangle(4.0, 6.0, 30, 40, "Blue"),
    new DrawableCircle(3.0, 50, 60, "Green")
};

Moveable[] moveables = {
    new DrawableCircle(2.0, 70, 80, "Yellow"),
    new DrawableRectangle(3.0, 3.0, 90, 100, "Purple")
};

// Polymorphic operations
for (Drawable drawable : drawables) {
    drawable.draw();
    drawable.resize(1.5);
    System.out.println("Area: " + drawable.getArea());
    System.out.println();
}

for (Moveable moveable : moveables) {
    moveable.getPosition();
    moveable.move(0, 0);
    System.out.println();
}
```

### 2. Functional Interface Polymorphism
```java
// Functional interfaces for different operations
@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
}

@FunctionalInterface
interface StringProcessor {
    String process(String input);
}

@FunctionalInterface
interface NumberValidator {
    boolean validate(int number);
}

public class FunctionalPolymorphism {
    public static void main(String[] args) {
        // Different implementations of the same interface
        MathOperation addition = (a, b) -> a + b;
        MathOperation subtraction = (a, b) -> a - b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation division = (a, b) -> b != 0 ? a / b : 0;
        MathOperation power = (a, b) -> Math.pow(a, b);
        
        // Using polymorphism with functional interfaces
        double x = 10, y = 3;
        
        System.out.println("=== MATH OPERATIONS ===");
        performOperation("Addition", addition, x, y);
        performOperation("Subtraction", subtraction, x, y);
        performOperation("Multiplication", multiplication, x, y);
        performOperation("Division", division, x, y);
        performOperation("Power", power, x, y);
        
        // String processing polymorphism
        StringProcessor uppercase = String::toUpperCase;
        StringProcessor lowercase = String::toLowerCase;
        StringProcessor reverse = s -> new StringBuilder(s).reverse().toString();
        StringProcessor addPrefix = s -> "Hello, " + s;
        StringProcessor removeSpaces = s -> s.replaceAll("\\s+", "");
        
        String text = "Java Programming";
        
        System.out.println("\n=== STRING PROCESSING ===");
        processString("Uppercase", uppercase, text);
        processString("Lowercase", lowercase, text);
        processString("Reverse", reverse, text);
        processString("Add Prefix", addPrefix, text);
        processString("Remove Spaces", removeSpaces, text);
        
        // Number validation polymorphism
        NumberValidator isEven = n -> n % 2 == 0;
        NumberValidator isPositive = n -> n > 0;
        NumberValidator isPrime = n -> {
            if (n < 2) return false;
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) return false;
            }
            return true;
        };
        NumberValidator isInRange = n -> n >= 1 && n <= 100;
        
        int[] numbers = {7, 12, -5, 23, 150, 2};
        
        System.out.println("\n=== NUMBER VALIDATION ===");
        for (int number : numbers) {
            System.out.println("Number: " + number);
            validateNumber("  Even", isEven, number);
            validateNumber("  Positive", isPositive, number);
            validateNumber("  Prime", isPrime, number);
            validateNumber("  In Range 1-100", isInRange, number);
            System.out.println();
        }
    }
    
    private static void performOperation(String name, MathOperation operation, double a, double b) {
        double result = operation.operate(a, b);
        System.out.println(name + ": " + a + " op " + b + " = " + result);
    }
    
    private static void processString(String name, StringProcessor processor, String input) {
        String result = processor.process(input);
        System.out.println(name + ": '" + input + "' -> '" + result + "'");
    }
    
    private static void validateNumber(String name, NumberValidator validator, int number) {
        boolean result = validator.validate(number);
        System.out.println(name + ": " + result);
    }
}
```

## Real-World Polymorphism Examples

### 1. Payment Processing System
```java
// Base payment interface
interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
    boolean refundPayment(String transactionId, double amount);
    String getPaymentMethod();
    boolean isAvailable();
}

// Credit Card Payment
class CreditCardProcessor implements PaymentProcessor {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    
    public CreditCardProcessor(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = maskCardNumber(cardNumber);
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing credit card payment...");
        System.out.println("Card: " + cardNumber);
        System.out.println("Amount: " + amount + " " + currency);
        
        // Simulate credit card processing
        if (validateCard() && amount > 0) {
            System.out.println("✓ Credit card payment successful");
            return true;
        }
        System.out.println("✗ Credit card payment failed");
        return false;
    }
    
    @Override
    public boolean refundPayment(String transactionId, double amount) {
        System.out.println("Processing credit card refund for transaction: " + transactionId);
        System.out.println("✓ Refund of " + amount + " processed to card " + cardNumber);
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
    
    @Override
    public boolean isAvailable() {
        return validateCard();
    }
    
    private boolean validateCard() {
        // Simplified validation
        return cardNumber.length() >= 16 && expiryDate != null && cvv.length() == 3;
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 4) return cardNumber;
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}

// PayPal Payment
class PayPalProcessor implements PaymentProcessor {
    private String email;
    private String password;
    private boolean isLoggedIn;
    
    public PayPalProcessor(String email, String password) {
        this.email = email;
        this.password = password;
        this.isLoggedIn = false;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing PayPal payment...");
        System.out.println("Account: " + email);
        System.out.println("Amount: " + amount + " " + currency);
        
        if (login() && amount > 0) {
            System.out.println("✓ PayPal payment successful");
            return true;
        }
        System.out.println("✗ PayPal payment failed");
        return false;
    }
    
    @Override
    public boolean refundPayment(String transactionId, double amount) {
        System.out.println("Processing PayPal refund for transaction: " + transactionId);
        if (login()) {
            System.out.println("✓ Refund of " + amount + " processed to " + email);
            return true;
        }
        return false;
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
    
    @Override
    public boolean isAvailable() {
        return email != null && password != null;
    }
    
    private boolean login() {
        if (!isLoggedIn) {
            System.out.println("Logging into PayPal...");
            isLoggedIn = true;
        }
        return true;
    }
}

// Bank Transfer Payment
class BankTransferProcessor implements PaymentProcessor {
    private String accountNumber;
    private String routingNumber;
    private String bankName;
    
    public BankTransferProcessor(String accountNumber, String routingNumber, String bankName) {
        this.accountNumber = maskAccountNumber(accountNumber);
        this.routingNumber = routingNumber;
        this.bankName = bankName;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing bank transfer...");
        System.out.println("Bank: " + bankName);
        System.out.println("Account: " + accountNumber);
        System.out.println("Amount: " + amount + " " + currency);
        
        if (validateBankDetails() && amount > 0) {
            System.out.println("✓ Bank transfer initiated (processing time: 2-3 business days)");
            return true;
        }
        System.out.println("✗ Bank transfer failed");
        return false;
    }
    
    @Override
    public boolean refundPayment(String transactionId, double amount) {
        System.out.println("Processing bank transfer refund for transaction: " + transactionId);
        System.out.println("✓ Refund of " + amount + " initiated to account " + accountNumber);
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
    
    @Override
    public boolean isAvailable() {
        return validateBankDetails();
    }
    
    private boolean validateBankDetails() {
        return accountNumber != null && routingNumber != null && bankName != null;
    }
    
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() < 4) return accountNumber;
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}

// Cryptocurrency Payment
class CryptoProcessor implements PaymentProcessor {
    private String walletAddress;
    private String cryptoType;
    private double exchangeRate;
    
    public CryptoProcessor(String walletAddress, String cryptoType, double exchangeRate) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
        this.exchangeRate = exchangeRate;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing cryptocurrency payment...");
        System.out.println("Crypto: " + cryptoType);
        System.out.println("Wallet: " + walletAddress);
        
        double cryptoAmount = amount * exchangeRate;
        System.out.println("Amount: " + amount + " " + currency + " (" + cryptoAmount + " " + cryptoType + ")");
        
        if (validateWallet() && amount > 0) {
            System.out.println("✓ Cryptocurrency payment successful");
            return true;
        }
        System.out.println("✗ Cryptocurrency payment failed");
        return false;
    }
    
    @Override
    public boolean refundPayment(String transactionId, double amount) {
        System.out.println("Note: Cryptocurrency transactions are typically irreversible");
        System.out.println("Manual refund process required for transaction: " + transactionId);
        return false;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Cryptocurrency (" + cryptoType + ")";
    }
    
    @Override
    public boolean isAvailable() {
        return validateWallet() && exchangeRate > 0;
    }
    
    private boolean validateWallet() {
        return walletAddress != null && walletAddress.length() > 20;
    }
}

// Payment Gateway that uses polymorphism
class PaymentGateway {
    private PaymentProcessor[] processors;
    private PaymentProcessor defaultProcessor;
    
    public PaymentGateway(PaymentProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
        this.processors = new PaymentProcessor[0];
    }
    
    public void addPaymentMethod(PaymentProcessor processor) {
        PaymentProcessor[] newProcessors = new PaymentProcessor[processors.length + 1];
        System.arraycopy(processors, 0, newProcessors, 0, processors.length);
        newProcessors[processors.length] = processor;
        processors = newProcessors;
    }
    
    public boolean processPayment(double amount, String currency, String preferredMethod) {
        System.out.println("\n=== PAYMENT PROCESSING ===");
        
        // Try preferred method first
        if (preferredMethod != null) {
            for (PaymentProcessor processor : processors) {
                if (processor.getPaymentMethod().toLowerCase()
                          .contains(preferredMethod.toLowerCase()) && processor.isAvailable()) {
                    return processor.processPayment(amount, currency);
                }
            }
        }
        
        // Fallback to any available method
        for (PaymentProcessor processor : processors) {
            if (processor.isAvailable()) {
                System.out.println("Using fallback payment method: " + processor.getPaymentMethod());
                return processor.processPayment(amount, currency);
            }
        }
        
        // Use default processor
        System.out.println("Using default payment method: " + defaultProcessor.getPaymentMethod());
        return defaultProcessor.processPayment(amount, currency);
    }
    
    public void displayAvailablePaymentMethods() {
        System.out.println("\n=== AVAILABLE PAYMENT METHODS ===");
        for (PaymentProcessor processor : processors) {
            String status = processor.isAvailable() ? "✓ Available" : "✗ Unavailable";
            System.out.println(processor.getPaymentMethod() + " - " + status);
        }
    }
    
    // Polymorphic batch processing
    public void processBatchPayments(double[] amounts, String currency) {
        System.out.println("\n=== BATCH PAYMENT PROCESSING ===");
        
        for (int i = 0; i < amounts.length && i < processors.length; i++) {
            PaymentProcessor processor = processors[i % processors.length];
            System.out.println("\nBatch " + (i + 1) + ":");
            processor.processPayment(amounts[i], currency);
        }
    }
}
```

### 2. Complete Payment System Demo
```java
public class PaymentSystemDemo {
    public static void main(String[] args) {
        // Create different payment processors
        CreditCardProcessor creditCard = new CreditCardProcessor("1234567890123456", "12/25", "123");
        PayPalProcessor paypal = new PayPalProcessor("user@example.com", "password123");
        BankTransferProcessor bankTransfer = new BankTransferProcessor("9876543210", "123456789", "Chase Bank");
        CryptoProcessor bitcoin = new CryptoProcessor("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "BTC", 0.000023);
        
        // Create payment gateway with default processor
        PaymentGateway gateway = new PaymentGateway(creditCard);
        
        // Add various payment methods
        gateway.addPaymentMethod(creditCard);
        gateway.addPaymentMethod(paypal);
        gateway.addPaymentMethod(bankTransfer);
        gateway.addPaymentMethod(bitcoin);
        
        // Display available methods
        gateway.displayAvailablePaymentMethods();
        
        // Process different payments polymorphically
        System.out.println("\n" + "=".repeat(50));
        gateway.processPayment(99.99, "USD", "credit");
        
        System.out.println("\n" + "=".repeat(50));
        gateway.processPayment(249.50, "USD", "paypal");
        
        System.out.println("\n" + "=".repeat(50));
        gateway.processPayment(1500.00, "USD", "bank");
        
        System.out.println("\n" + "=".repeat(50));
        gateway.processPayment(50.00, "USD", "crypto");
        
        // Batch processing demonstration
        double[] batchAmounts = {25.99, 75.50, 199.99, 49.99};
        gateway.processBatchPayments(batchAmounts, "USD");
        
        // Demonstrating polymorphic array processing
        System.out.println("\n=== DIRECT POLYMORPHIC PROCESSING ===");
        PaymentProcessor[] allProcessors = {creditCard, paypal, bankTransfer, bitcoin};
        
        for (PaymentProcessor processor : allProcessors) {
            if (processor.isAvailable()) {
                System.out.println("\nTesting " + processor.getPaymentMethod() + ":");
                processor.processPayment(100.0, "USD");
                processor.refundPayment("TXN123", 50.0);
            }
        }
        
        // Demonstrating runtime type checking and casting
        System.out.println("\n=== RUNTIME TYPE CHECKING ===");
        for (PaymentProcessor processor : allProcessors) {
            System.out.println("\nProcessor: " + processor.getClass().getSimpleName());
            
            if (processor instanceof CreditCardProcessor) {
                System.out.println("- This is a credit card processor");
            } else if (processor instanceof PayPalProcessor) {
                System.out.println("- This is a PayPal processor");
            } else if (processor instanceof BankTransferProcessor) {
                System.out.println("- This is a bank transfer processor");
            } else if (processor instanceof CryptoProcessor) {
                System.out.println("- This is a cryptocurrency processor");
            }
            
            System.out.println("- Available: " + processor.isAvailable());
            System.out.println("- Method: " + processor.getPaymentMethod());
        }
    }
}
```

## Interview Questions & Answers

**Q1: What is polymorphism? Explain with an example.**
**A:** Polymorphism means "one interface, many forms." It allows objects of different classes to be treated as objects of a common base class while maintaining their specific behaviors. Example: `Animal animal = new Dog(); animal.makeSound();` - the `makeSound()` call will execute Dog's version even though the reference is Animal type.

**Q2: What are the types of polymorphism in Java?**
**A:** 
- **Compile-time polymorphism**: Method overloading, operator overloading (limited)
- **Runtime polymorphism**: Method overriding, interface implementation

**Q3: What's the difference between method overloading and overriding?**
**A:**
- **Overloading**: Same method name, different parameters in the same class (compile-time)
- **Overriding**: Same method signature in parent/child classes (runtime)

**Q4: How does Java achieve runtime polymorphism?**
**A:** Through dynamic method dispatch using virtual method calls. The JVM determines which method to call based on the actual object type at runtime, not the reference type.

**Q5: What is dynamic method dispatch?**
**A:** It's the mechanism by which Java resolves method calls at runtime. When a method is called on a reference, the JVM looks at the actual object type and calls the appropriate overridden method.

**Q6: Can you override static methods in Java?**
**A:** No, static methods cannot be overridden because they belong to the class, not instances. However, you can hide them by declaring a static method with the same signature in a subclass.

**Q7: What is the difference between static binding and dynamic binding?**
**A:**
- **Static binding**: Method calls resolved at compile time (overloading, static methods, private methods, final methods)
- **Dynamic binding**: Method calls resolved at runtime (overriding, virtual method calls)

**Q8: Explain the 'instanceof' operator with polymorphism.**
**A:** The `instanceof` operator checks if an object is an instance of a particular class or implements an interface. It's useful in polymorphic scenarios to determine actual object type before casting: `if (animal instanceof Dog) { Dog dog = (Dog) animal; }`

## Key Takeaways

1. **Polymorphism enables code flexibility** - same interface works with different implementations
2. **Method overloading** provides multiple ways to call the same logical operation
3. **Method overriding** enables specialized behavior in subclasses
4. **Runtime polymorphism** uses dynamic method dispatch for flexible code
5. **Interface polymorphism** allows multiple inheritance-like behavior
6. **Functional interfaces** enable polymorphism with lambda expressions
7. **instanceof and casting** help work with polymorphic objects safely
8. **Virtual method calls** ensure correct method version is called at runtime
9. **Polymorphic collections** can hold objects of different types with common interface
10. **Open/Closed Principle** - code open for extension, closed for modification

---

*Remember: Polymorphism is like having a universal remote control - one interface that works with many different devices, each responding in their own specific way!*