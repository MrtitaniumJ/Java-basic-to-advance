# Interfaces in Java - Contracts and Multiple Inheritance

## Simple Explanation

Think of **Interfaces** as **contracts** or **job descriptions**:

- **Job posting** = Interface (defines what skills/methods are required)
- **Employee** = Class implementing interface (provides actual implementation)
- **Multiple skills** = Multiple interface implementation (can do multiple jobs)
- **Contract terms** = Abstract methods (must be implemented)

### Real-World Analogies
- **Driver's license** = Interface for driving (can drive car, motorcycle, truck)
- **Payment methods** = Interface for payments (credit card, cash, digital wallet)
- **Electrical outlet** = Standard interface (any compatible device can plug in)
- **USB connector** = Universal interface (works with mice, keyboards, drives)

## Professional Definition

An **Interface** in Java is a reference type that defines a contract specifying what methods a class must implement, without providing implementation details. Interfaces enable multiple inheritance of type, provide complete abstraction, and establish common behavior across unrelated classes. They support default methods (Java 8+), static methods, and private methods (Java 9+).

## Why Use Interfaces?

### Benefits:
- **Multiple Inheritance**: A class can implement multiple interfaces
- **Complete Abstraction**: Define behavior without implementation details
- **Loose Coupling**: Depend on abstractions, not concrete implementations
- **Polymorphism**: Common interface for different implementations
- **Code Flexibility**: Easy to add new implementations without changing existing code
- **API Design**: Define clear contracts for system components
- **Testing**: Easy to create mock implementations

### Problems Without Interfaces:
```java
// WITHOUT INTERFACES - Tight coupling and limited flexibility

class PaymentProcessor {
    public void processCreditCardPayment(String cardNumber, double amount) {
        System.out.println("Processing credit card payment: $" + amount);
        // Credit card specific logic
    }
    
    public void processPayPalPayment(String email, double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
        // PayPal specific logic
    }
    
    public void processBankTransferPayment(String account, double amount) {
        System.out.println("Processing bank transfer: $" + amount);
        // Bank transfer specific logic
    }
    
    // Need to add new method for each payment type!
    // Code becomes bloated and hard to maintain
}

class ShoppingCart {
    private PaymentProcessor processor = new PaymentProcessor();
    
    public void checkout(String paymentType, String details, double amount) {
        // Ugly switch statement - violates Open/Closed Principle
        switch (paymentType) {
            case "CREDIT_CARD":
                processor.processCreditCardPayment(details, amount);
                break;
            case "PAYPAL":
                processor.processPayPalPayment(details, amount);
                break;
            case "BANK_TRANSFER":
                processor.processBankTransferPayment(details, amount);
                break;
            // Need to modify this code for each new payment method!
        }
    }
}
```

### With Interfaces - Clean and Flexible:
```java
// WITH INTERFACES - Clean, flexible, extensible design

interface PaymentMethod {
    boolean processPayment(double amount);
    String getPaymentMethodName();
    boolean isAvailable();
}

class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment: $" + amount);
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
    
    @Override
    public boolean isAvailable() {
        return cardNumber != null && cardNumber.length() == 16;
    }
}

class PayPalPayment implements PaymentMethod {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
    
    @Override
    public boolean isAvailable() {
        return email != null && email.contains("@");
    }
}

class ShoppingCart {
    public void checkout(PaymentMethod paymentMethod, double amount) {
        // Clean, polymorphic code - no changes needed for new payment types!
        if (paymentMethod.isAvailable()) {
            boolean success = paymentMethod.processPayment(amount);
            System.out.println("Payment " + (success ? "successful" : "failed") + 
                             " via " + paymentMethod.getPaymentMethodName());
        }
    }
}

// Adding new payment method requires NO changes to existing code!
class BitcoinPayment implements PaymentMethod {
    private String walletAddress;
    
    public BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing Bitcoin payment: $" + amount);
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Bitcoin";
    }
    
    @Override
    public boolean isAvailable() {
        return walletAddress != null && walletAddress.length() > 25;
    }
}
```

## Interface Syntax and Rules

### 1. Basic Interface Declaration
```java
// Interface declaration
public interface Drawable {
    // Constants - automatically public, static, final
    int MAX_SIZE = 1000;
    String DEFAULT_COLOR = "BLACK";
    double DEFAULT_OPACITY = 1.0;
    
    // Abstract methods - automatically public and abstract (no body)
    void draw();
    void resize(double factor);
    void move(int x, int y);
    
    // Default method (Java 8+) - provides default implementation
    default void setColor(String color) {
        System.out.println("Setting color to: " + color);
    }
    
    // Static method (Java 8+) - belongs to interface itself
    static boolean isValidSize(double size) {
        return size > 0 && size <= MAX_SIZE;
    }
    
    // Private method (Java 9+) - for internal use only
    private void validateDrawing() {
        System.out.println("Validating drawing parameters");
    }
    
    // Private static method (Java 9+)
    private static String formatColor(String color) {
        return color.toUpperCase();
    }
}

// Implementing the interface
class Circle implements Drawable {
    private double radius;
    private int x, y;
    private String color;
    
    public Circle(double radius, int x, int y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = DEFAULT_COLOR; // Using interface constant
    }
    
    // Must implement all abstract methods
    @Override
    public void draw() {
        System.out.println("Drawing circle at (" + x + ", " + y + ") with radius " + radius);
    }
    
    @Override
    public void resize(double factor) {
        if (Drawable.isValidSize(radius * factor)) { // Using static method
            radius *= factor;
            System.out.println("Circle resized to radius: " + radius);
        } else {
            System.out.println("Invalid size for resize");
        }
    }
    
    @Override
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        System.out.println("Circle moved to (" + x + ", " + y + ")");
    }
    
    // Can override default method if needed
    @Override
    public void setColor(String color) {
        this.color = color;
        System.out.println("Circle color set to: " + color);
    }
    
    // Circle-specific methods
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    public double getRadius() {
        return radius;
    }
}

// Another implementation
class Rectangle implements Drawable {
    private double width, height;
    private int x, y;
    
    public Rectangle(double width, double height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing rectangle at (" + x + ", " + y + ") " + width + "x" + height);
    }
    
    @Override
    public void resize(double factor) {
        if (Drawable.isValidSize(Math.max(width * factor, height * factor))) {
            width *= factor;
            height *= factor;
            System.out.println("Rectangle resized to " + width + "x" + height);
        }
    }
    
    @Override
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        System.out.println("Rectangle moved to (" + x + ", " + y + ")");
    }
    
    // Uses default setColor implementation (no override)
    
    public double getArea() {
        return width * height;
    }
}
```

### 2. Interface Rules and Constraints
```java
public interface InterfaceRulesDemo {
    // 1. All fields are automatically public, static, final
    int PUBLIC_CONSTANT = 100;          // public static final int
    String MESSAGE = "Hello Interface"; // public static final String
    
    // 2. Cannot have instance variables
    // private int instanceVar;         // Compilation error!
    // protected String name;           // Compilation error!
    
    // 3. Abstract methods are automatically public
    void abstractMethod();              // public abstract void abstractMethod();
    
    // 4. Cannot have constructors
    // public InterfaceRulesDemo() { } // Compilation error!
    
    // 5. Default methods must have body (Java 8+)
    default void defaultMethod() {
        System.out.println("Default implementation");
        privateHelperMethod(); // Can call private methods
    }
    
    // 6. Static methods must have body (Java 8+)
    static void staticMethod() {
        System.out.println("Static method in interface");
        privateStaticHelper(); // Can call private static methods
    }
    
    // 7. Private methods for internal use (Java 9+)
    private void privateHelperMethod() {
        System.out.println("Private helper method");
    }
    
    // 8. Private static methods (Java 9+)
    private static void privateStaticHelper() {
        System.out.println("Private static helper");
    }
    
    // 9. Cannot have protected methods
    // protected void protectedMethod(); // Compilation error!
    
    // 10. Cannot override Object class methods (except toString, equals, hashCode)
    // public Class<?> getClass();      // Compilation error!
    
    // 11. Can override Object methods if needed
    boolean equals(Object obj);         // Valid override
    int hashCode();                     // Valid override
    String toString();                  // Valid override
}

class InterfaceRulesImplementation implements InterfaceRulesDemo {
    // Must implement all abstract methods
    @Override
    public void abstractMethod() {
        System.out.println("Implementing abstract method");
    }
    
    // Can access interface constants
    public void useConstants() {
        System.out.println("Constant: " + PUBLIC_CONSTANT);
        System.out.println("Message: " + MESSAGE);
    }
    
    // Can override default methods (optional)
    @Override
    public void defaultMethod() {
        System.out.println("Overriding default method");
        // Can call interface static methods
        InterfaceRulesDemo.staticMethod();
    }
    
    // Must implement Object method overrides from interface
    @Override
    public boolean equals(Object obj) {
        return obj instanceof InterfaceRulesImplementation;
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "InterfaceRulesImplementation instance";
    }
}
```

### 3. Multiple Interface Implementation
```java
// Multiple interfaces that a class can implement
interface Flyable {
    void fly();
    double getMaxAltitude();
    
    default void takeOff() {
        System.out.println("Taking off into the sky");
    }
}

interface Swimmable {
    void swim();
    double getMaxDepth();
    
    default void dive() {
        System.out.println("Diving into water");
    }
}

interface Walkable {
    void walk();
    double getMaxSpeed();
    
    default void run() {
        System.out.println("Running on ground");
    }
}

// Class implementing multiple interfaces
class Duck implements Flyable, Swimmable, Walkable {
    private String name;
    private double energy;
    
    public Duck(String name) {
        this.name = name;
        this.energy = 100.0;
    }
    
    // Implement Flyable
    @Override
    public void fly() {
        if (energy >= 20) {
            energy -= 20;
            System.out.println(name + " is flying gracefully");
        } else {
            System.out.println(name + " is too tired to fly");
        }
    }
    
    @Override
    public double getMaxAltitude() {
        return 1000.0; // 1000 feet
    }
    
    // Implement Swimmable
    @Override
    public void swim() {
        if (energy >= 10) {
            energy -= 10;
            System.out.println(name + " is swimming in water");
        } else {
            System.out.println(name + " is too tired to swim");
        }
    }
    
    @Override
    public double getMaxDepth() {
        return 10.0; // 10 feet underwater
    }
    
    // Implement Walkable
    @Override
    public void walk() {
        if (energy >= 5) {
            energy -= 5;
            System.out.println(name + " is waddling around");
        } else {
            System.out.println(name + " is too tired to walk");
        }
    }
    
    @Override
    public double getMaxSpeed() {
        return 8.0; // 8 mph walking
    }
    
    // Can override default methods from any interface
    @Override
    public void takeOff() {
        System.out.println(name + " flaps wings and takes off from water");
    }
    
    @Override
    public void dive() {
        System.out.println(name + " dives underwater to catch fish");
    }
    
    // Duck-specific methods
    public void quack() {
        System.out.println(name + " says: Quack! Quack!");
    }
    
    public void rest() {
        energy = Math.min(100.0, energy + 30);
        System.out.println(name + " is resting. Energy: " + energy);
    }
    
    public double getEnergy() {
        return energy;
    }
}

// Another class with different capabilities
class Fish implements Swimmable {
    private String species;
    private boolean isDeepSea;
    
    public Fish(String species, boolean isDeepSea) {
        this.species = species;
        this.isDeepSea = isDeepSea;
    }
    
    @Override
    public void swim() {
        System.out.println(species + " fish is swimming smoothly");
    }
    
    @Override
    public double getMaxDepth() {
        return isDeepSea ? 10000.0 : 100.0;
    }
    
    @Override
    public void dive() {
        double depth = isDeepSea ? 5000.0 : 50.0;
        System.out.println(species + " dives to " + depth + " feet");
    }
}

class Airplane implements Flyable {
    private String model;
    private int passengerCapacity;
    
    public Airplane(String model, int passengerCapacity) {
        this.model = model;
        this.passengerCapacity = passengerCapacity;
    }
    
    @Override
    public void fly() {
        System.out.println(model + " airplane is flying with " + passengerCapacity + " passengers");
    }
    
    @Override
    public double getMaxAltitude() {
        return 40000.0; // 40,000 feet
    }
    
    @Override
    public void takeOff() {
        System.out.println(model + " accelerates down runway and takes off");
    }
}

// Demonstration of polymorphism with multiple interfaces
class MultiInterfaceDemo {
    public static void demonstrateFlying(Flyable flyer) {
        System.out.println("=== Flying Demonstration ===");
        flyer.takeOff();
        flyer.fly();
        System.out.println("Max altitude: " + flyer.getMaxAltitude() + " feet");
    }
    
    public static void demonstrateSwimming(Swimmable swimmer) {
        System.out.println("=== Swimming Demonstration ===");
        swimmer.dive();
        swimmer.swim();
        System.out.println("Max depth: " + swimmer.getMaxDepth() + " feet");
    }
    
    public static void demonstrateWalking(Walkable walker) {
        System.out.println("=== Walking Demonstration ===");
        walker.run();
        walker.walk();
        System.out.println("Max speed: " + walker.getMaxSpeed() + " mph");
    }
}
```

## Interface Inheritance and Extension

### 1. Interface Extending Other Interfaces
```java
// Base interface
interface Animal {
    String getName();
    void makeSound();
    
    default void sleep() {
        System.out.println(getName() + " is sleeping");
    }
}

// Interface extending Animal
interface Mammal extends Animal {
    void giveBirth();
    boolean isWarmBlooded();
    
    // Can override default methods from parent interface
    @Override
    default void sleep() {
        System.out.println(getName() + " (mammal) is sleeping warmly");
    }
    
    // Add new default method
    default void nurseYoung() {
        System.out.println(getName() + " is nursing young ones");
    }
}

// Interface extending Animal (different branch)
interface Bird extends Animal {
    void layEggs();
    boolean canFly();
    
    @Override
    default void sleep() {
        System.out.println(getName() + " (bird) is roosting");
    }
    
    default void buildNest() {
        System.out.println(getName() + " is building a nest");
    }
}

// Interface extending multiple interfaces (diamond inheritance)
interface FlyingMammal extends Mammal, Flyable {
    // Must resolve conflicting default methods if they exist
    
    @Override
    default void takeOff() {
        System.out.println(getName() + " (flying mammal) spreads wings and takes off");
    }
}

// Implementation classes
class Dog implements Mammal, Walkable {
    private String name;
    
    public Dog(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " barks: Woof! Woof!");
    }
    
    @Override
    public void giveBirth() {
        System.out.println(name + " gives birth to puppies");
    }
    
    @Override
    public boolean isWarmBlooded() {
        return true;
    }
    
    @Override
    public void walk() {
        System.out.println(name + " walks on four legs");
    }
    
    @Override
    public double getMaxSpeed() {
        return 30.0; // 30 mph running
    }
    
    // Dog-specific methods
    public void fetch() {
        System.out.println(name + " fetches the ball");
    }
    
    public void wagTail() {
        System.out.println(name + " wags tail excitedly");
    }
}

class Eagle implements Bird, Flyable {
    private String name;
    
    public Eagle(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " screeches: Screech!");
    }
    
    @Override
    public void layEggs() {
        System.out.println(name + " lays eggs in the nest");
    }
    
    @Override
    public boolean canFly() {
        return true;
    }
    
    @Override
    public void fly() {
        System.out.println(name + " soars high in the sky");
    }
    
    @Override
    public double getMaxAltitude() {
        return 15000.0; // 15,000 feet
    }
    
    @Override
    public void takeOff() {
        System.out.println(name + " spreads mighty wings and takes off");
    }
    
    // Eagle-specific methods
    public void hunt() {
        System.out.println(name + " hunts for prey from above");
    }
}

class Bat implements FlyingMammal {
    private String name;
    
    public Bat(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " makes ultrasonic sounds");
    }
    
    @Override
    public void giveBirth() {
        System.out.println(name + " gives birth to live young");
    }
    
    @Override
    public boolean isWarmBlooded() {
        return true;
    }
    
    @Override
    public void fly() {
        System.out.println(name + " flies using echolocation");
    }
    
    @Override
    public double getMaxAltitude() {
        return 3000.0; // 3,000 feet
    }
    
    // Bat-specific methods
    public void useEcholocation() {
        System.out.println(name + " uses echolocation to navigate");
    }
    
    public void hangUpsideDown() {
        System.out.println(name + " hangs upside down to rest");
    }
}
```

### 2. Default Method Conflicts and Resolution
```java
interface InterfaceA {
    default void commonMethod() {
        System.out.println("Implementation from InterfaceA");
    }
    
    default void uniqueMethodA() {
        System.out.println("Unique method from InterfaceA");
    }
}

interface InterfaceB {
    default void commonMethod() {
        System.out.println("Implementation from InterfaceB");
    }
    
    default void uniqueMethodB() {
        System.out.println("Unique method from InterfaceB");
    }
}

// Class implementing both interfaces with conflicting default methods
class ConflictResolver implements InterfaceA, InterfaceB {
    
    // MUST override conflicting default method
    @Override
    public void commonMethod() {
        System.out.println("Resolving conflict in ConflictResolver");
        
        // Can call specific interface implementations
        InterfaceA.super.commonMethod();  // Call InterfaceA's version
        InterfaceB.super.commonMethod();  // Call InterfaceB's version
        
        // Or provide completely new implementation
        System.out.println("Custom implementation combining both");
    }
    
    // No conflict for unique methods - inherited as-is
    // uniqueMethodA() and uniqueMethodB() are available
    
    public void demonstrateResolution() {
        commonMethod();     // Calls overridden version
        uniqueMethodA();    // From InterfaceA
        uniqueMethodB();    // From InterfaceB
    }
}

// More complex conflict resolution
interface Printable {
    default void print() {
        System.out.println("Printing from Printable interface");
    }
    
    default String getDocument() {
        return "Document from Printable";
    }
}

interface Displayable {
    default void print() {
        System.out.println("Displaying on screen from Displayable interface");
    }
    
    default String getDocument() {
        return "Document from Displayable";
    }
}

interface Saveable {
    default void save() {
        System.out.println("Saving from Saveable interface");
    }
    
    default String getDocument() {
        return "Document from Saveable";
    }
}

class Document implements Printable, Displayable, Saveable {
    private String content;
    private String title;
    
    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    // Resolve print() conflict between Printable and Displayable
    @Override
    public void print() {
        System.out.println("=== Document: " + title + " ===");
        System.out.println("Printing AND displaying:");
        
        // Call both interface implementations
        Printable.super.print();
        Displayable.super.print();
        
        // Add custom behavior
        System.out.println("Content: " + content);
    }
    
    // Resolve getDocument() conflict between all three interfaces
    @Override
    public String getDocument() {
        String printableDoc = Printable.super.getDocument();
        String displayableDoc = Displayable.super.getDocument();
        String saveableDoc = Saveable.super.getDocument();
        
        return String.format("Combined document: %s | %s | %s - Title: %s", 
                           printableDoc, displayableDoc, saveableDoc, title);
    }
    
    // save() inherited from Saveable (no conflict)
    
    public void processDocument() {
        print();                            // Resolved method
        save();                             // Inherited method
        System.out.println(getDocument());  // Resolved method
    }
}
```

## Real-World Interface Examples

### 1. Database Connection Framework
```java
// Database connection interface
interface DatabaseConnection {
    // Connection management
    boolean connect(String url, String username, String password);
    void disconnect();
    boolean isConnected();
    
    // Query operations
    ResultSet executeQuery(String sql);
    int executeUpdate(String sql);
    boolean execute(String sql);
    
    // Transaction management
    void beginTransaction();
    void commit();
    void rollback();
    
    // Default methods for common operations
    default boolean executeUpdateWithTransaction(String sql) {
        try {
            beginTransaction();
            int result = executeUpdate(sql);
            commit();
            return result > 0;
        } catch (Exception e) {
            rollback();
            System.err.println("Transaction failed: " + e.getMessage());
            return false;
        }
    }
    
    default void executeBatch(String[] sqlStatements) {
        try {
            beginTransaction();
            for (String sql : sqlStatements) {
                executeUpdate(sql);
            }
            commit();
            System.out.println("Batch executed successfully");
        } catch (Exception e) {
            rollback();
            System.err.println("Batch execution failed: " + e.getMessage());
        }
    }
    
    // Static utility methods
    static boolean isValidConnectionString(String url) {
        return url != null && url.startsWith("jdbc:");
    }
    
    static String formatConnectionString(String host, int port, String database) {
        return String.format("jdbc:mysql://%s:%d/%s", host, port, database);
    }
}

// Simplified ResultSet interface for demo
interface ResultSet {
    boolean next();
    String getString(String columnName);
    int getInt(String columnName);
    double getDouble(String columnName);
    void close();
}

// MySQL implementation
class MySQLConnection implements DatabaseConnection {
    private String connectionUrl;
    private boolean connected;
    private boolean inTransaction;
    
    @Override
    public boolean connect(String url, String username, String password) {
        if (!DatabaseConnection.isValidConnectionString(url)) {
            System.err.println("Invalid connection string");
            return false;
        }
        
        System.out.println("Connecting to MySQL: " + url);
        this.connectionUrl = url;
        this.connected = true;
        this.inTransaction = false;
        System.out.println("MySQL connection established");
        return true;
    }
    
    @Override
    public void disconnect() {
        if (connected) {
            if (inTransaction) {
                rollback(); // Auto-rollback on disconnect
            }
            System.out.println("Disconnecting from MySQL");
            connected = false;
            connectionUrl = null;
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
    
    @Override
    public ResultSet executeQuery(String sql) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Executing MySQL query: " + sql);
        return new MySQLResultSet(); // Simplified implementation
    }
    
    @Override
    public int executeUpdate(String sql) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Executing MySQL update: " + sql);
        return 1; // Simulate affected rows
    }
    
    @Override
    public boolean execute(String sql) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Executing MySQL statement: " + sql);
        return true;
    }
    
    @Override
    public void beginTransaction() {
        if (connected && !inTransaction) {
            System.out.println("Beginning MySQL transaction");
            inTransaction = true;
        }
    }
    
    @Override
    public void commit() {
        if (connected && inTransaction) {
            System.out.println("Committing MySQL transaction");
            inTransaction = false;
        }
    }
    
    @Override
    public void rollback() {
        if (connected && inTransaction) {
            System.out.println("Rolling back MySQL transaction");
            inTransaction = false;
        }
    }
    
    // MySQL-specific methods
    public void optimizeTable(String tableName) {
        execute("OPTIMIZE TABLE " + tableName);
    }
    
    public void showProcessList() {
        ResultSet rs = executeQuery("SHOW PROCESSLIST");
        System.out.println("MySQL process list retrieved");
    }
}

// PostgreSQL implementation
class PostgreSQLConnection implements DatabaseConnection {
    private String connectionUrl;
    private boolean connected;
    private boolean inTransaction;
    
    @Override
    public boolean connect(String url, String username, String password) {
        if (!DatabaseConnection.isValidConnectionString(url)) {
            return false;
        }
        
        System.out.println("Connecting to PostgreSQL: " + url);
        this.connectionUrl = url;
        this.connected = true;
        this.inTransaction = false;
        return true;
    }
    
    @Override
    public void disconnect() {
        if (connected) {
            if (inTransaction) {
                rollback();
            }
            System.out.println("Disconnecting from PostgreSQL");
            connected = false;
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
    
    @Override
    public ResultSet executeQuery(String sql) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Executing PostgreSQL query: " + sql);
        return new PostgreSQLResultSet();
    }
    
    @Override
    public int executeUpdate(String sql) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Executing PostgreSQL update: " + sql);
        return 1;
    }
    
    @Override
    public boolean execute(String sql) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Executing PostgreSQL statement: " + sql);
        return true;
    }
    
    @Override
    public void beginTransaction() {
        if (connected && !inTransaction) {
            System.out.println("Beginning PostgreSQL transaction");
            inTransaction = true;
        }
    }
    
    @Override
    public void commit() {
        if (connected && inTransaction) {
            System.out.println("Committing PostgreSQL transaction");
            inTransaction = false;
        }
    }
    
    @Override
    public void rollback() {
        if (connected && inTransaction) {
            System.out.println("Rolling back PostgreSQL transaction");
            inTransaction = false;
        }
    }
    
    // PostgreSQL-specific methods
    public void analyzeTable(String tableName) {
        execute("ANALYZE " + tableName);
    }
    
    public void createIndex(String indexName, String tableName, String columnName) {
        execute("CREATE INDEX " + indexName + " ON " + tableName + " (" + columnName + ")");
    }
}

// Simplified ResultSet implementations
class MySQLResultSet implements ResultSet {
    private boolean hasNext = true;
    
    @Override
    public boolean next() {
        return hasNext;
    }
    
    @Override
    public String getString(String columnName) {
        return "MySQL_" + columnName;
    }
    
    @Override
    public int getInt(String columnName) {
        return 100;
    }
    
    @Override
    public double getDouble(String columnName) {
        return 99.99;
    }
    
    @Override
    public void close() {
        System.out.println("MySQL ResultSet closed");
    }
}

class PostgreSQLResultSet implements ResultSet {
    private boolean hasNext = true;
    
    @Override
    public boolean next() {
        return hasNext;
    }
    
    @Override
    public String getString(String columnName) {
        return "PostgreSQL_" + columnName;
    }
    
    @Override
    public int getInt(String columnName) {
        return 200;
    }
    
    @Override
    public double getDouble(String columnName) {
        return 199.99;
    }
    
    @Override
    public void close() {
        System.out.println("PostgreSQL ResultSet closed");
    }
}

// Database service using interface polymorphism
class DatabaseService {
    private DatabaseConnection connection;
    
    public DatabaseService(DatabaseConnection connection) {
        this.connection = connection;
    }
    
    public boolean connectToDatabase(String url, String username, String password) {
        return connection.connect(url, username, password);
    }
    
    public void createUser(String username, String email) {
        if (!connection.isConnected()) {
            System.err.println("Not connected to database");
            return;
        }
        
        String sql = String.format("INSERT INTO users (username, email) VALUES ('%s', '%s')", 
                                  username, email);
        
        // Using default method for transaction handling
        boolean success = connection.executeUpdateWithTransaction(sql);
        System.out.println("User creation " + (success ? "successful" : "failed"));
    }
    
    public void bulkInsertUsers(String[][] userData) {
        if (!connection.isConnected()) {
            System.err.println("Not connected to database");
            return;
        }
        
        String[] sqlStatements = new String[userData.length];
        for (int i = 0; i < userData.length; i++) {
            sqlStatements[i] = String.format("INSERT INTO users (username, email) VALUES ('%s', '%s')", 
                                            userData[i][0], userData[i][1]);
        }
        
        // Using default method for batch operations
        connection.executeBatch(sqlStatements);
    }
    
    public void findUserByEmail(String email) {
        if (!connection.isConnected()) {
            System.err.println("Not connected to database");
            return;
        }
        
        String sql = "SELECT * FROM users WHERE email = '" + email + "'";
        ResultSet rs = connection.executeQuery(sql);
        
        if (rs.next()) {
            System.out.println("Found user:");
            System.out.println("Username: " + rs.getString("username"));
            System.out.println("Email: " + rs.getString("email"));
        }
        rs.close();
    }
    
    public void disconnect() {
        connection.disconnect();
    }
}
```

### 2. Complete Interface Demonstration
```java
public class InterfaceDemo {
    public static void main(String[] args) {
        System.out.println("=== INTERFACE DEMONSTRATION ===");
        
        demonstrateBasicInterfaces();
        demonstrateMultipleInheritance();
        demonstrateInterfacePolymorphism();
        demonstrateDatabaseFramework();
    }
    
    public static void demonstrateBasicInterfaces() {
        System.out.println("\n--- BASIC INTERFACES ---");
        
        Drawable[] drawables = {
            new Circle(5.0, 100, 200),
            new Rectangle(8.0, 6.0, 300, 400)
        };
        
        for (Drawable drawable : drawables) {
            drawable.draw();
            drawable.resize(1.5);
            drawable.move(50, 50);
            drawable.setColor("Blue");
            System.out.println("Max size valid: " + Drawable.isValidSize(500));
            System.out.println();
        }
    }
    
    public static void demonstrateMultipleInheritance() {
        System.out.println("\n--- MULTIPLE INHERITANCE ---");
        
        Duck duck = new Duck("Donald");
        Fish fish = new Fish("Nemo", false);
        Airplane airplane = new Airplane("Boeing 737", 150);
        
        // Duck can fly, swim, and walk
        MultiInterfaceDemo.demonstrateFlying(duck);
        MultiInterfaceDemo.demonstrateSwimming(duck);
        MultiInterfaceDemo.demonstrateWalking(duck);
        
        duck.quack();
        duck.rest();
        
        // Fish can only swim
        MultiInterfaceDemo.demonstrateSwimming(fish);
        
        // Airplane can only fly
        MultiInterfaceDemo.demonstrateFlying(airplane);
    }
    
    public static void demonstrateInterfacePolymorphism() {
        System.out.println("\n--- INTERFACE POLYMORPHISM ---");
        
        // Interface inheritance demonstration
        Animal[] animals = {
            new Dog("Buddy"),
            new Eagle("Freedom"),
            new Bat("Batman")
        };
        
        for (Animal animal : animals) {
            animal.makeSound();
            animal.sleep();
            
            if (animal instanceof Mammal) {
                Mammal mammal = (Mammal) animal;
                mammal.nurseYoung();
            }
            
            if (animal instanceof Bird) {
                Bird bird = (Bird) animal;
                bird.buildNest();
                bird.layEggs();
            }
            
            if (animal instanceof Flyable) {
                Flyable flyer = (Flyable) animal;
                flyer.takeOff();
                flyer.fly();
            }
            
            System.out.println();
        }
        
        // Conflict resolution demonstration
        ConflictResolver resolver = new ConflictResolver();
        resolver.demonstrateResolution();
        
        Document doc = new Document("Interface Guide", "Comprehensive guide to Java interfaces");
        doc.processDocument();
    }
    
    public static void demonstrateDatabaseFramework() {
        System.out.println("\n--- DATABASE FRAMEWORK ---");
        
        // Create different database connections
        DatabaseConnection mysql = new MySQLConnection();
        DatabaseConnection postgresql = new PostgreSQLConnection();
        
        DatabaseConnection[] connections = {mysql, postgresql};
        
        for (DatabaseConnection db : connections) {
            System.out.println("\n=== Testing Database Connection ===");
            
            // Test connection
            String url = DatabaseConnection.formatConnectionString("localhost", 3306, "testdb");
            boolean connected = db.connect(url, "user", "password");
            
            if (connected) {
                // Create database service
                DatabaseService service = new DatabaseService(db);
                
                // Test single user creation
                service.createUser("john_doe", "john@example.com");
                
                // Test bulk user creation
                String[][] users = {
                    {"jane_smith", "jane@example.com"},
                    {"bob_johnson", "bob@example.com"},
                    {"alice_brown", "alice@example.com"}
                };
                service.bulkInsertUsers(users);
                
                // Test query
                service.findUserByEmail("john@example.com");
                
                // Disconnect
                service.disconnect();
            }
            
            System.out.println();
        }
    }
}
```

## Interview Questions & Answers

**Q1: What is an interface in Java? How does it differ from abstract classes?**
**A:** An interface is a contract that defines what methods a class must implement without providing implementation. Differences:
- Interface: All methods abstract (pre-Java 8), multiple inheritance, no constructors, only constants
- Abstract class: Can have concrete methods, single inheritance, can have constructors and instance variables

**Q2: Can interfaces have constructors?**
**A:** No, interfaces cannot have constructors because they cannot be instantiated. Only classes can have constructors.

**Q3: What are default methods in interfaces? Why were they introduced?**
**A:** Default methods (Java 8+) provide default implementation in interfaces. They were introduced to allow interface evolution without breaking existing implementations.

**Q4: Can a class implement multiple interfaces?**
**A:** Yes, Java supports multiple interface implementation using commas: `class MyClass implements Interface1, Interface2, Interface3`

**Q5: What happens when two interfaces have the same default method?**
**A:** The implementing class must override the method to resolve the conflict. You can call specific interface implementations using `InterfaceName.super.methodName()`.

**Q6: Can interfaces extend other interfaces?**
**A:** Yes, interfaces can extend other interfaces using `extends` keyword. They can extend multiple interfaces.

**Q7: What are marker interfaces? Give examples.**
**A:** Marker interfaces have no methods and are used to mark classes with special properties. Examples: `Serializable`, `Cloneable`, `Remote`.

**Q8: Can interfaces have static methods?**
**A:** Yes (Java 8+). Static methods in interfaces belong to the interface itself and can be called without implementing the interface.

## Key Takeaways

1. **Interfaces define contracts** without implementation details (pure abstraction)
2. **Multiple inheritance** is possible through interface implementation
3. **Default methods** enable interface evolution and provide common implementations
4. **Static methods** belong to the interface and provide utility functions
5. **Interface inheritance** allows building hierarchies of related contracts
6. **Conflict resolution** is required when default methods clash
7. **Polymorphism** works seamlessly with interface references
8. **Loose coupling** is achieved by depending on interfaces, not implementations
9. **API design** benefits from clear interface contracts
10. **Code flexibility** increases with interface-based design patterns

---

*Remember: Interfaces are like contracts - they specify what must be done, but not how to do it, allowing multiple different implementations of the same behavior!*