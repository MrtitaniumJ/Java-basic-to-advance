# Abstraction in Java - Hiding Complexity, Showing Essentials

## Simple Explanation

Think of **Abstraction** as **using a car without knowing how the engine works**:

- **Car interface** = Steering wheel, pedals, gear stick (what you need to know)
- **Hidden complexity** = Engine mechanics, transmission, fuel injection (what you don't need to know)
- **TV remote** = Simple buttons to control complex internal electronics
- **ATM machine** = Easy interface hiding complex banking operations
- **Smartphone** = Touch screen hiding complicated hardware and software

### Real-World Analogies
- **Email system** = Simple "Send" button hiding network protocols and server operations
- **Online shopping** = Click "Buy Now" hiding payment processing, inventory management, shipping
- **Coffee machine** = Press button for coffee, hiding water heating, grinding, brewing
- **Calculator** = Number buttons and operations hiding complex mathematical computations

## Professional Definition

**Abstraction** is an OOP principle that focuses on hiding implementation details while showing only essential features and functionality to the user. It provides a simplified interface to complex systems, reducing complexity and increasing code maintainability. In Java, abstraction is achieved through abstract classes and interfaces.

## Why Use Abstraction?

### Benefits:
- **Simplicity**: Hide complex implementation details
- **Maintainability**: Changes to implementation don't affect users
- **Security**: Sensitive implementation details remain hidden
- **Code Organization**: Clear separation between interface and implementation
- **Reusability**: Abstract designs can be reused across different implementations
- **Focus**: Users focus on what to do, not how it's done
- **Flexibility**: Multiple implementations possible for same abstract design

### Problems Without Abstraction:
```java
// WITHOUT ABSTRACTION - Exposing all implementation details
class DatabaseConnectionExposed {
    public String host = "localhost";
    public int port = 3306;
    public String username = "admin";
    public String password = "secret123";  // Security risk!
    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;
    
    // Users must handle all these complex details
    public void establishConnection() {
        // Complex networking code exposed
        try {
            socket = new Socket(host, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // Authentication protocol implementation
            // Error handling
            // Connection pooling logic
        } catch (Exception e) {
            // Complex error handling exposed
        }
    }
    
    // Users must understand SQL syntax and connection management
    public ResultSet executeRawQuery(String sql) {
        // Complex query execution exposed
        // SQL injection vulnerability
        // Result set management exposed
        return null;
    }
}
```

### With Abstraction - Clean and Simple:
```java
// WITH ABSTRACTION - Simple, secure interface
abstract class DatabaseConnection {
    // Implementation details hidden
    private String connectionString;
    private boolean isConnected;
    
    // Abstract methods - must be implemented by subclasses
    public abstract boolean connect();
    public abstract void disconnect();
    public abstract List<User> getUsers();
    public abstract boolean saveUser(User user);
    
    // Concrete method - common functionality
    public boolean isConnected() {
        return isConnected;
    }
    
    // Protected method - for subclass use only
    protected void setConnectionStatus(boolean status) {
        this.isConnected = status;
    }
}

// User doesn't need to know implementation details
DatabaseConnection db = new MySQLConnection();
db.connect();                    // Simple interface
List<User> users = db.getUsers(); // Implementation hidden
db.disconnect();                 // Clean and safe
```

## Abstract Classes

### 1. Basic Abstract Class
```java
// Abstract class cannot be instantiated directly
abstract class Vehicle {
    // Concrete fields - shared by all vehicles
    protected String brand;
    protected String model;
    protected int year;
    protected String color;
    protected boolean isRunning;
    
    // Constructor - can be called by subclasses
    public Vehicle(String brand, String model, int year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isRunning = false;
    }
    
    // Abstract methods - must be implemented by subclasses
    public abstract void start();
    public abstract void stop();
    public abstract void accelerate();
    public abstract void brake();
    public abstract int getMaxSpeed();
    public abstract String getFuelType();
    
    // Concrete methods - shared implementation
    public void displayInfo() {
        System.out.println("Vehicle: " + year + " " + brand + " " + model);
        System.out.println("Color: " + color);
        System.out.println("Status: " + (isRunning ? "Running" : "Stopped"));
        System.out.println("Max Speed: " + getMaxSpeed() + " mph");
        System.out.println("Fuel Type: " + getFuelType());
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    // Protected method - for subclass use
    protected void setRunningStatus(boolean status) {
        this.isRunning = status;
        System.out.println("Vehicle " + (status ? "started" : "stopped"));
    }
    
    // Template method pattern - defines algorithm structure
    public final void performMaintenance() {
        System.out.println("Starting maintenance for " + brand + " " + model);
        checkEngine();
        checkTires();
        checkFluids();
        performSpecificMaintenance(); // Abstract - each vehicle type implements
        System.out.println("Maintenance completed");
    }
    
    // Concrete maintenance steps
    private void checkEngine() {
        System.out.println("✓ Engine checked");
    }
    
    private void checkTires() {
        System.out.println("✓ Tires checked");
    }
    
    private void checkFluids() {
        System.out.println("✓ Fluids checked");
    }
    
    // Abstract - specific maintenance varies by vehicle type
    protected abstract void performSpecificMaintenance();
}

// Concrete implementation - Car
class Car extends Vehicle {
    private int doors;
    private String transmission;
    
    public Car(String brand, String model, int year, String color, int doors, String transmission) {
        super(brand, model, year, color);
        this.doors = doors;
        this.transmission = transmission;
    }
    
    @Override
    public void start() {
        if (!isRunning) {
            System.out.println("Turning key to start " + brand + " " + model);
            System.out.println("Engine started with smooth idle");
            setRunningStatus(true);
        } else {
            System.out.println("Car is already running");
        }
    }
    
    @Override
    public void stop() {
        if (isRunning) {
            System.out.println("Turning off engine of " + brand + " " + model);
            setRunningStatus(false);
        } else {
            System.out.println("Car is already stopped");
        }
    }
    
    @Override
    public void accelerate() {
        if (isRunning) {
            System.out.println("Car accelerating smoothly using " + transmission + " transmission");
        } else {
            System.out.println("Cannot accelerate - start the car first");
        }
    }
    
    @Override
    public void brake() {
        if (isRunning) {
            System.out.println("Car braking using disc brakes");
        } else {
            System.out.println("Car is already stopped");
        }
    }
    
    @Override
    public int getMaxSpeed() {
        return 120; // mph
    }
    
    @Override
    public String getFuelType() {
        return "Gasoline";
    }
    
    @Override
    protected void performSpecificMaintenance() {
        System.out.println("✓ Air conditioning system checked");
        System.out.println("✓ Power steering fluid checked");
        System.out.println("✓ Seat belts inspected");
    }
    
    // Car-specific methods
    public void openTrunk() {
        System.out.println("Trunk opened");
    }
    
    public void lockDoors() {
        System.out.println("All " + doors + " doors locked");
    }
}

// Concrete implementation - Motorcycle
class Motorcycle extends Vehicle {
    private int engineSize;
    private boolean hasSidecar;
    
    public Motorcycle(String brand, String model, int year, String color, int engineSize, boolean hasSidecar) {
        super(brand, model, year, color);
        this.engineSize = engineSize;
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public void start() {
        if (!isRunning) {
            System.out.println("Kick-starting " + brand + " " + model + " (" + engineSize + "cc)");
            System.out.println("Engine roars to life!");
            setRunningStatus(true);
        } else {
            System.out.println("Motorcycle is already running");
        }
    }
    
    @Override
    public void stop() {
        if (isRunning) {
            System.out.println("Engine off - " + brand + " " + model + " stopped");
            setRunningStatus(false);
        } else {
            System.out.println("Motorcycle is already stopped");
        }
    }
    
    @Override
    public void accelerate() {
        if (isRunning) {
            System.out.println("Motorcycle accelerating rapidly with " + engineSize + "cc power!");
        } else {
            System.out.println("Cannot accelerate - start the motorcycle first");
        }
    }
    
    @Override
    public void brake() {
        if (isRunning) {
            System.out.println("Applying front and rear brakes");
        } else {
            System.out.println("Motorcycle is already stopped");
        }
    }
    
    @Override
    public int getMaxSpeed() {
        return engineSize > 600 ? 180 : 100; // High-performance bikes go faster
    }
    
    @Override
    public String getFuelType() {
        return "Gasoline (Premium)";
    }
    
    @Override
    protected void performSpecificMaintenance() {
        System.out.println("✓ Chain lubrication and adjustment");
        System.out.println("✓ Brake pads inspection");
        if (hasSidecar) {
            System.out.println("✓ Sidecar attachment points checked");
        }
    }
    
    // Motorcycle-specific methods
    public void wheelie() {
        if (isRunning) {
            System.out.println("Performing wheelie on " + brand + " " + model + "!");
        } else {
            System.out.println("Start the motorcycle first");
        }
    }
}

// Usage example
Vehicle car = new Car("Toyota", "Camry", 2023, "Blue", 4, "Automatic");
Vehicle bike = new Motorcycle("Honda", "CBR600RR", 2023, "Red", 600, false);

// Polymorphic usage - same interface, different implementations
Vehicle[] vehicles = {car, bike};

for (Vehicle vehicle : vehicles) {
    vehicle.displayInfo();
    vehicle.start();
    vehicle.accelerate();
    vehicle.brake();
    vehicle.stop();
    vehicle.performMaintenance();
    System.out.println();
}
```

### 2. Abstract Class Rules and Best Practices
```java
// Comprehensive abstract class example
abstract class DatabaseManager {
    // Constants - shared by all implementations
    protected static final int MAX_CONNECTIONS = 10;
    protected static final int CONNECTION_TIMEOUT = 30000;
    
    // Instance variables
    protected String databaseUrl;
    protected String username;
    private boolean isInitialized;
    
    // Constructor
    public DatabaseManager(String databaseUrl, String username) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.isInitialized = false;
    }
    
    // Abstract methods - MUST be implemented by subclasses
    public abstract boolean connect();
    public abstract void disconnect();
    public abstract boolean executeUpdate(String query);
    public abstract List<Map<String, Object>> executeQuery(String query);
    public abstract boolean beginTransaction();
    public abstract boolean commitTransaction();
    public abstract boolean rollbackTransaction();
    
    // Concrete methods - shared implementation
    public boolean initialize() {
        if (!isInitialized) {
            System.out.println("Initializing database manager...");
            if (validateConfiguration() && connect()) {
                isInitialized = true;
                System.out.println("Database manager initialized successfully");
                return true;
            }
            System.out.println("Failed to initialize database manager");
            return false;
        }
        return true;
    }
    
    public boolean isInitialized() {
        return isInitialized;
    }
    
    // Template method - defines standard procedure
    public final boolean executeWithTransaction(String... queries) {
        if (!isInitialized()) {
            System.out.println("Database not initialized");
            return false;
        }
        
        try {
            if (beginTransaction()) {
                for (String query : queries) {
                    if (!executeUpdate(query)) {
                        rollbackTransaction();
                        return false;
                    }
                }
                return commitTransaction();
            }
            return false;
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println("Transaction failed: " + e.getMessage());
            return false;
        }
    }
    
    // Protected method - for subclass use
    protected boolean validateConfiguration() {
        if (databaseUrl == null || databaseUrl.trim().isEmpty()) {
            System.out.println("Database URL is required");
            return false;
        }
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username is required");
            return false;
        }
        return true;
    }
    
    // Final method - cannot be overridden
    public final void shutdown() {
        if (isInitialized) {
            System.out.println("Shutting down database manager...");
            disconnect();
            isInitialized = false;
            System.out.println("Database manager shut down");
        }
    }
}

// MySQL implementation
class MySQLManager extends DatabaseManager {
    private String version;
    
    public MySQLManager(String host, int port, String database, String username) {
        super("jdbc:mysql://" + host + ":" + port + "/" + database, username);
        this.version = "8.0";
    }
    
    @Override
    public boolean connect() {
        System.out.println("Connecting to MySQL database: " + databaseUrl);
        System.out.println("Using MySQL version: " + version);
        // Simulate MySQL-specific connection logic
        return true;
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from MySQL database");
        // MySQL-specific cleanup
    }
    
    @Override
    public boolean executeUpdate(String query) {
        System.out.println("Executing MySQL update: " + query);
        // MySQL-specific update execution
        return true;
    }
    
    @Override
    public List<Map<String, Object>> executeQuery(String query) {
        System.out.println("Executing MySQL query: " + query);
        // MySQL-specific query execution
        return new ArrayList<>();
    }
    
    @Override
    public boolean beginTransaction() {
        System.out.println("Starting MySQL transaction");
        return true;
    }
    
    @Override
    public boolean commitTransaction() {
        System.out.println("Committing MySQL transaction");
        return true;
    }
    
    @Override
    public boolean rollbackTransaction() {
        System.out.println("Rolling back MySQL transaction");
        return true;
    }
    
    // MySQL-specific method
    public void optimizeDatabase() {
        System.out.println("Running MySQL database optimization");
    }
}

// PostgreSQL implementation
class PostgreSQLManager extends DatabaseManager {
    private boolean useSSL;
    
    public PostgreSQLManager(String host, int port, String database, String username, boolean useSSL) {
        super("jdbc:postgresql://" + host + ":" + port + "/" + database, username);
        this.useSSL = useSSL;
    }
    
    @Override
    public boolean connect() {
        System.out.println("Connecting to PostgreSQL database: " + databaseUrl);
        System.out.println("SSL enabled: " + useSSL);
        // PostgreSQL-specific connection logic
        return true;
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from PostgreSQL database");
        // PostgreSQL-specific cleanup
    }
    
    @Override
    public boolean executeUpdate(String query) {
        System.out.println("Executing PostgreSQL update: " + query);
        // PostgreSQL-specific update execution
        return true;
    }
    
    @Override
    public List<Map<String, Object>> executeQuery(String query) {
        System.out.println("Executing PostgreSQL query: " + query);
        // PostgreSQL-specific query execution
        return new ArrayList<>();
    }
    
    @Override
    public boolean beginTransaction() {
        System.out.println("Starting PostgreSQL transaction");
        return true;
    }
    
    @Override
    public boolean commitTransaction() {
        System.out.println("Committing PostgreSQL transaction");
        return true;
    }
    
    @Override
    public boolean rollbackTransaction() {
        System.out.println("Rolling back PostgreSQL transaction");
        return true;
    }
    
    // PostgreSQL-specific method
    public void createBackup() {
        System.out.println("Creating PostgreSQL database backup");
    }
}
```

## Interfaces - Pure Abstraction

### 1. Basic Interface Implementation
```java
// Interface - 100% abstraction (before Java 8)
interface Drawable {
    // All methods are public and abstract by default
    void draw();
    void resize(double factor);
    
    // Constants - public, static, final by default
    String DEFAULT_COLOR = "BLACK";
    int MAX_SIZE = 1000;
}

// Interface for moveable objects
interface Moveable {
    void move(int deltaX, int deltaY);
    void setPosition(int x, int y);
    int getX();
    int getY();
}

// Interface for colorable objects
interface Colorable {
    void setColor(String color);
    String getColor();
    void changeOpacity(double opacity);
}

// Class implementing multiple interfaces
class Circle implements Drawable, Moveable, Colorable {
    private double radius;
    private int x, y;
    private String color;
    private double opacity;
    
    public Circle(double radius, int x, int y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = DEFAULT_COLOR;
        this.opacity = 1.0;
    }
    
    // Implementing Drawable interface
    @Override
    public void draw() {
        System.out.println("Drawing circle at (" + x + ", " + y + ") with radius " + radius);
        System.out.println("Color: " + color + ", Opacity: " + opacity);
    }
    
    @Override
    public void resize(double factor) {
        if (factor > 0 && radius * factor <= MAX_SIZE) {
            radius *= factor;
            System.out.println("Circle resized, new radius: " + radius);
        } else {
            System.out.println("Invalid resize factor or size exceeds maximum");
        }
    }
    
    // Implementing Moveable interface
    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        System.out.println("Circle moved to (" + x + ", " + y + ")");
    }
    
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Circle position set to (" + x + ", " + y + ")");
    }
    
    @Override
    public int getX() { return x; }
    
    @Override
    public int getY() { return y; }
    
    // Implementing Colorable interface
    @Override
    public void setColor(String color) {
        this.color = color;
        System.out.println("Circle color changed to " + color);
    }
    
    @Override
    public String getColor() { return color; }
    
    @Override
    public void changeOpacity(double opacity) {
        if (opacity >= 0.0 && opacity <= 1.0) {
            this.opacity = opacity;
            System.out.println("Circle opacity changed to " + opacity);
        } else {
            System.out.println("Opacity must be between 0.0 and 1.0");
        }
    }
    
    // Circle-specific methods
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
    public double getArea() { return Math.PI * radius * radius; }
}

class Rectangle implements Drawable, Moveable, Colorable {
    private double width, height;
    private int x, y;
    private String color;
    private double opacity;
    
    public Rectangle(double width, double height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = DEFAULT_COLOR;
        this.opacity = 1.0;
    }
    
    // Implementing Drawable interface
    @Override
    public void draw() {
        System.out.println("Drawing rectangle at (" + x + ", " + y + ") " + width + "x" + height);
        System.out.println("Color: " + color + ", Opacity: " + opacity);
    }
    
    @Override
    public void resize(double factor) {
        if (factor > 0 && Math.max(width * factor, height * factor) <= MAX_SIZE) {
            width *= factor;
            height *= factor;
            System.out.println("Rectangle resized, new dimensions: " + width + "x" + height);
        } else {
            System.out.println("Invalid resize factor or size exceeds maximum");
        }
    }
    
    // Implementing Moveable interface
    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        System.out.println("Rectangle moved to (" + x + ", " + y + ")");
    }
    
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Rectangle position set to (" + x + ", " + y + ")");
    }
    
    @Override
    public int getX() { return x; }
    
    @Override
    public int getY() { return y; }
    
    // Implementing Colorable interface
    @Override
    public void setColor(String color) {
        this.color = color;
        System.out.println("Rectangle color changed to " + color);
    }
    
    @Override
    public String getColor() { return color; }
    
    @Override
    public void changeOpacity(double opacity) {
        if (opacity >= 0.0 && opacity <= 1.0) {
            this.opacity = opacity;
            System.out.println("Rectangle opacity changed to " + opacity);
        } else {
            System.out.println("Opacity must be between 0.0 and 1.0");
        }
    }
    
    // Rectangle-specific methods
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getArea() { return width * height; }
}

// Using interface polymorphism
Drawable[] drawables = {
    new Circle(5.0, 10, 20),
    new Rectangle(6.0, 8.0, 30, 40),
    new Circle(3.0, 50, 60)
};

Moveable[] moveables = {
    new Circle(4.0, 70, 80),
    new Rectangle(5.0, 7.0, 90, 100)
};

// Polymorphic operations
for (Drawable drawable : drawables) {
    drawable.draw();
    drawable.resize(1.5);
    System.out.println();
}

for (Moveable moveable : moveables) {
    moveable.move(10, 10);
    System.out.println("Position: (" + moveable.getX() + ", " + moveable.getY() + ")");
    System.out.println();
}
```

### 2. Modern Interface Features (Java 8+)
```java
// Modern interface with default and static methods
interface PaymentProcessor {
    // Abstract methods (must be implemented)
    boolean processPayment(double amount, String currency);
    String getPaymentMethodName();
    
    // Default method - provides default implementation
    default boolean validatePayment(double amount, String currency) {
        if (amount <= 0) {
            System.out.println("Payment amount must be positive");
            return false;
        }
        if (currency == null || currency.trim().isEmpty()) {
            System.out.println("Currency is required");
            return false;
        }
        if (!getSupportedCurrencies().contains(currency.toUpperCase())) {
            System.out.println("Currency " + currency + " not supported");
            return false;
        }
        return true;
    }
    
    // Default method with implementation
    default List<String> getSupportedCurrencies() {
        return Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
    }
    
    // Default method for logging
    default void logTransaction(String transactionId, double amount, String currency) {
        System.out.println("Transaction logged: " + transactionId + 
                          " | " + amount + " " + currency + 
                          " | Method: " + getPaymentMethodName());
    }
    
    // Static method - utility function
    static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    // Static method for validation
    static boolean isValidCurrency(String currency) {
        List<String> validCurrencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
        return currency != null && validCurrencies.contains(currency.toUpperCase());
    }
    
    // Default method for transaction processing workflow
    default boolean executePayment(double amount, String currency) {
        String transactionId = generateTransactionId();
        System.out.println("Starting payment process - Transaction ID: " + transactionId);
        
        if (!validatePayment(amount, currency)) {
            System.out.println("Payment validation failed");
            return false;
        }
        
        boolean result = processPayment(amount, currency);
        logTransaction(transactionId, amount, currency);
        
        if (result) {
            System.out.println("Payment successful: " + transactionId);
        } else {
            System.out.println("Payment failed: " + transactionId);
        }
        
        return result;
    }
}

// Credit Card Implementation
class CreditCardProcessor implements PaymentProcessor {
    private String cardNumber;
    private String cardHolderName;
    
    public CreditCardProcessor(String cardNumber, String cardHolderName) {
        this.cardNumber = maskCardNumber(cardNumber);
        this.cardHolderName = cardHolderName;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing credit card payment...");
        System.out.println("Cardholder: " + cardHolderName);
        System.out.println("Card: " + cardNumber);
        System.out.println("Amount: " + amount + " " + currency);
        
        // Simulate payment processing
        boolean success = Math.random() > 0.1; // 90% success rate
        return success;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
    
    // Override default method to provide specific validation
    @Override
    public boolean validatePayment(double amount, String currency) {
        // Call default validation first
        if (!PaymentProcessor.super.validatePayment(amount, currency)) {
            return false;
        }
        
        // Additional credit card specific validation
        if (amount > 10000) {
            System.out.println("Credit card payment limit exceeded (max: $10,000)");
            return false;
        }
        
        return true;
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 4) return cardNumber;
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}

// PayPal Implementation
class PayPalProcessor implements PaymentProcessor {
    private String email;
    
    public PayPalProcessor(String email) {
        this.email = email;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing PayPal payment...");
        System.out.println("Account: " + email);
        System.out.println("Amount: " + amount + " " + currency);
        
        // Simulate payment processing
        boolean success = Math.random() > 0.05; // 95% success rate
        return success;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
    
    // Override default method for PayPal-specific currencies
    @Override
    public List<String> getSupportedCurrencies() {
        return Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "SEK");
    }
}

// Bank Transfer Implementation
class BankTransferProcessor implements PaymentProcessor {
    private String accountNumber;
    private String bankName;
    
    public BankTransferProcessor(String accountNumber, String bankName) {
        this.accountNumber = maskAccountNumber(accountNumber);
        this.bankName = bankName;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing bank transfer...");
        System.out.println("Bank: " + bankName);
        System.out.println("Account: " + accountNumber);
        System.out.println("Amount: " + amount + " " + currency);
        System.out.println("Processing time: 2-3 business days");
        
        // Simulate payment processing
        boolean success = Math.random() > 0.15; // 85% success rate
        return success;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Bank Transfer";
    }
    
    @Override
    public boolean validatePayment(double amount, String currency) {
        // Call default validation
        if (!PaymentProcessor.super.validatePayment(amount, currency)) {
            return false;
        }
        
        // Bank transfer specific validation
        if (amount < 10) {
            System.out.println("Bank transfer minimum amount is $10");
            return false;
        }
        
        return true;
    }
    
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() < 4) return accountNumber;
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}
```

### 3. Interface Inheritance and Multiple Implementation
```java
// Base interface
interface Readable {
    String read();
    boolean canRead();
}

// Interface extending another interface
interface Writable extends Readable {
    boolean write(String data);
    boolean canWrite();
    
    // Default method
    default boolean readWrite(String data) {
        if (canRead() && canWrite()) {
            System.out.println("Current content: " + read());
            boolean result = write(data);
            System.out.println("New content: " + read());
            return result;
        }
        return false;
    }
}

// Another interface
interface Searchable {
    List<String> search(String query);
    boolean contains(String text);
}

// Interface for file operations
interface FileOperations {
    boolean delete();
    long getSize();
    String getPath();
    boolean exists();
}

// Class implementing multiple interfaces
class TextFile implements Writable, Searchable, FileOperations {
    private String filePath;
    private StringBuilder content;
    private boolean readOnly;
    
    public TextFile(String filePath, boolean readOnly) {
        this.filePath = filePath;
        this.content = new StringBuilder();
        this.readOnly = readOnly;
    }
    
    // Implementing Readable interface (inherited through Writable)
    @Override
    public String read() {
        return content.toString();
    }
    
    @Override
    public boolean canRead() {
        return exists();
    }
    
    // Implementing Writable interface
    @Override
    public boolean write(String data) {
        if (canWrite()) {
            content.append(data);
            System.out.println("Data written to " + filePath);
            return true;
        }
        System.out.println("Cannot write - file is read-only or doesn't exist");
        return false;
    }
    
    @Override
    public boolean canWrite() {
        return !readOnly && exists();
    }
    
    // Implementing Searchable interface
    @Override
    public List<String> search(String query) {
        List<String> results = new ArrayList<>();
        if (content.length() > 0) {
            String[] lines = content.toString().split("\\n");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].toLowerCase().contains(query.toLowerCase())) {
                    results.add("Line " + (i + 1) + ": " + lines[i]);
                }
            }
        }
        return results;
    }
    
    @Override
    public boolean contains(String text) {
        return content.toString().toLowerCase().contains(text.toLowerCase());
    }
    
    // Implementing FileOperations interface
    @Override
    public boolean delete() {
        content.setLength(0);
        System.out.println("File " + filePath + " deleted");
        return true;
    }
    
    @Override
    public long getSize() {
        return content.length();
    }
    
    @Override
    public String getPath() {
        return filePath;
    }
    
    @Override
    public boolean exists() {
        return filePath != null && !filePath.trim().isEmpty();
    }
    
    // Additional methods
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        System.out.println("File " + filePath + " is now " + (readOnly ? "read-only" : "writable"));
    }
    
    public boolean isReadOnly() {
        return readOnly;
    }
}
```

## Real-World Abstraction Example

### 1. Media Player System
```java
// Abstract base class for media players
abstract class MediaPlayer {
    protected String playerName;
    protected String version;
    protected boolean isPlaying;
    protected boolean isPaused;
    protected double volume;
    protected String currentMedia;
    
    public MediaPlayer(String playerName, String version) {
        this.playerName = playerName;
        this.version = version;
        this.isPlaying = false;
        this.isPaused = false;
        this.volume = 0.5; // 50% volume
        this.currentMedia = null;
    }
    
    // Abstract methods - must be implemented by specific players
    public abstract boolean loadMedia(String mediaPath);
    public abstract boolean play();
    public abstract boolean pause();
    public abstract boolean stop();
    public abstract boolean seek(double position);
    public abstract double getCurrentPosition();
    public abstract double getDuration();
    public abstract List<String> getSupportedFormats();
    
    // Concrete methods - shared functionality
    public boolean setVolume(double volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            this.volume = volume;
            System.out.println(playerName + " volume set to " + (int)(volume * 100) + "%");
            return true;
        }
        System.out.println("Invalid volume level. Use 0.0 to 1.0");
        return false;
    }
    
    public double getVolume() {
        return volume;
    }
    
    public void displayPlayerInfo() {
        System.out.println("Player: " + playerName + " v" + version);
        System.out.println("Status: " + getStatusString());
        System.out.println("Volume: " + (int)(volume * 100) + "%");
        System.out.println("Current Media: " + (currentMedia != null ? currentMedia : "None"));
        System.out.println("Supported Formats: " + getSupportedFormats());
    }
    
    public String getStatusString() {
        if (isPlaying && !isPaused) return "Playing";
        if (isPaused) return "Paused";
        return "Stopped";
    }
    
    // Template method - defines standard playback workflow
    public final boolean playMedia(String mediaPath) {
        System.out.println("\n=== Starting playback workflow ===");
        
        if (!loadMedia(mediaPath)) {
            System.out.println("Failed to load media");
            return false;
        }
        
        if (!play()) {
            System.out.println("Failed to start playback");
            return false;
        }
        
        performPostPlayActions();
        return true;
    }
    
    // Hook method - can be overridden by subclasses
    protected void performPostPlayActions() {
        System.out.println("Playback started successfully");
    }
    
    // Protected utility methods
    protected boolean validateMediaPath(String mediaPath) {
        return mediaPath != null && !mediaPath.trim().isEmpty();
    }
    
    protected void updatePlayingState(boolean playing, boolean paused) {
        this.isPlaying = playing;
        this.isPaused = paused;
    }
    
    // Getters
    public boolean isPlaying() { return isPlaying && !isPaused; }
    public boolean isPaused() { return isPaused; }
    public String getPlayerName() { return playerName; }
    public String getCurrentMedia() { return currentMedia; }
}

// Audio Player Implementation
class AudioPlayer extends MediaPlayer {
    private String audioCodec;
    private int bitrate;
    private boolean equalizerEnabled;
    private Map<String, Double> equalizerSettings;
    
    public AudioPlayer() {
        super("Advanced Audio Player", "2.0");
        this.audioCodec = "MP3";
        this.bitrate = 320;
        this.equalizerEnabled = false;
        this.equalizerSettings = new HashMap<>();
        initializeEqualizer();
    }
    
    @Override
    public boolean loadMedia(String mediaPath) {
        if (!validateMediaPath(mediaPath)) {
            System.out.println("Invalid media path");
            return false;
        }
        
        String extension = getFileExtension(mediaPath);
        if (!getSupportedFormats().contains(extension)) {
            System.out.println("Unsupported audio format: " + extension);
            return false;
        }
        
        System.out.println("Loading audio file: " + mediaPath);
        this.currentMedia = mediaPath;
        this.audioCodec = detectCodec(extension);
        System.out.println("Audio loaded - Codec: " + audioCodec + ", Bitrate: " + bitrate + "kbps");
        return true;
    }
    
    @Override
    public boolean play() {
        if (currentMedia == null) {
            System.out.println("No audio loaded");
            return false;
        }
        
        if (isPlaying && !isPaused) {
            System.out.println("Audio is already playing");
            return true;
        }
        
        System.out.println("Playing audio: " + currentMedia);
        if (equalizerEnabled) {
            System.out.println("Equalizer active with custom settings");
        }
        updatePlayingState(true, false);
        return true;
    }
    
    @Override
    public boolean pause() {
        if (!isPlaying || isPaused) {
            System.out.println("Audio is not playing");
            return false;
        }
        
        System.out.println("Audio paused");
        updatePlayingState(true, true);
        return true;
    }
    
    @Override
    public boolean stop() {
        if (!isPlaying) {
            System.out.println("Audio is not playing");
            return false;
        }
        
        System.out.println("Audio stopped");
        updatePlayingState(false, false);
        return true;
    }
    
    @Override
    public boolean seek(double position) {
        if (currentMedia == null) {
            System.out.println("No audio loaded");
            return false;
        }
        
        double duration = getDuration();
        if (position < 0 || position > duration) {
            System.out.println("Invalid seek position");
            return false;
        }
        
        System.out.println("Seeking to " + formatTime(position) + " / " + formatTime(duration));
        return true;
    }
    
    @Override
    public double getCurrentPosition() {
        // Simulate current position
        return isPlaying ? 125.5 : 0.0;
    }
    
    @Override
    public double getDuration() {
        return currentMedia != null ? 245.8 : 0.0; // Simulated duration
    }
    
    @Override
    public List<String> getSupportedFormats() {
        return Arrays.asList("mp3", "wav", "flac", "aac", "ogg", "m4a");
    }
    
    @Override
    protected void performPostPlayActions() {
        super.performPostPlayActions();
        if (equalizerEnabled) {
            System.out.println("Applying equalizer settings");
        }
        System.out.println("Audio visualization started");
    }
    
    // Audio-specific methods
    public void enableEqualizer(boolean enable) {
        this.equalizerEnabled = enable;
        System.out.println("Equalizer " + (enable ? "enabled" : "disabled"));
    }
    
    public void setEqualizerBand(String band, double value) {
        if (value >= -12.0 && value <= 12.0) {
            equalizerSettings.put(band, value);
            System.out.println("Equalizer " + band + " set to " + value + "dB");
        }
    }
    
    private void initializeEqualizer() {
        equalizerSettings.put("Bass", 0.0);
        equalizerSettings.put("Mid", 0.0);
        equalizerSettings.put("Treble", 0.0);
    }
    
    private String getFileExtension(String filePath) {
        int lastDot = filePath.lastIndexOf('.');
        return lastDot > 0 ? filePath.substring(lastDot + 1).toLowerCase() : "";
    }
    
    private String detectCodec(String extension) {
        switch (extension) {
            case "mp3": return "MP3";
            case "wav": return "PCM";
            case "flac": return "FLAC";
            case "aac": return "AAC";
            case "ogg": return "Vorbis";
            case "m4a": return "AAC";
            default: return "Unknown";
        }
    }
    
    private String formatTime(double seconds) {
        int minutes = (int) seconds / 60;
        int secs = (int) seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }
}

// Video Player Implementation
class VideoPlayer extends MediaPlayer {
    private String videoCodec;
    private String resolution;
    private int frameRate;
    private boolean fullscreen;
    private boolean subtitlesEnabled;
    private String subtitleLanguage;
    
    public VideoPlayer() {
        super("Premium Video Player", "3.1");
        this.videoCodec = "H.264";
        this.resolution = "1920x1080";
        this.frameRate = 30;
        this.fullscreen = false;
        this.subtitlesEnabled = false;
        this.subtitleLanguage = "English";
    }
    
    @Override
    public boolean loadMedia(String mediaPath) {
        if (!validateMediaPath(mediaPath)) {
            System.out.println("Invalid media path");
            return false;
        }
        
        String extension = getFileExtension(mediaPath);
        if (!getSupportedFormats().contains(extension)) {
            System.out.println("Unsupported video format: " + extension);
            return false;
        }
        
        System.out.println("Loading video file: " + mediaPath);
        this.currentMedia = mediaPath;
        this.videoCodec = detectVideoCodec(extension);
        System.out.println("Video loaded - Codec: " + videoCodec + ", Resolution: " + resolution + ", FPS: " + frameRate);
        return true;
    }
    
    @Override
    public boolean play() {
        if (currentMedia == null) {
            System.out.println("No video loaded");
            return false;
        }
        
        if (isPlaying && !isPaused) {
            System.out.println("Video is already playing");
            return true;
        }
        
        System.out.println("Playing video: " + currentMedia);
        System.out.println("Display mode: " + (fullscreen ? "Fullscreen" : "Windowed"));
        if (subtitlesEnabled) {
            System.out.println("Subtitles: " + subtitleLanguage);
        }
        updatePlayingState(true, false);
        return true;
    }
    
    @Override
    public boolean pause() {
        if (!isPlaying || isPaused) {
            System.out.println("Video is not playing");
            return false;
        }
        
        System.out.println("Video paused");
        updatePlayingState(true, true);
        return true;
    }
    
    @Override
    public boolean stop() {
        if (!isPlaying) {
            System.out.println("Video is not playing");
            return false;
        }
        
        System.out.println("Video stopped");
        if (fullscreen) {
            fullscreen = false;
            System.out.println("Exited fullscreen mode");
        }
        updatePlayingState(false, false);
        return true;
    }
    
    @Override
    public boolean seek(double position) {
        if (currentMedia == null) {
            System.out.println("No video loaded");
            return false;
        }
        
        double duration = getDuration();
        if (position < 0 || position > duration) {
            System.out.println("Invalid seek position");
            return false;
        }
        
        System.out.println("Seeking to " + formatTime(position) + " / " + formatTime(duration));
        return true;
    }
    
    @Override
    public double getCurrentPosition() {
        return isPlaying ? 1230.5 : 0.0; // Simulated position
    }
    
    @Override
    public double getDuration() {
        return currentMedia != null ? 7890.0 : 0.0; // Simulated duration
    }
    
    @Override
    public List<String> getSupportedFormats() {
        return Arrays.asList("mp4", "avi", "mkv", "mov", "wmv", "flv", "webm");
    }
    
    @Override
    protected void performPostPlayActions() {
        super.performPostPlayActions();
        System.out.println("Video rendering started at " + resolution);
        System.out.println("Hardware acceleration: Enabled");
    }
    
    // Video-specific methods
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        System.out.println("Display mode: " + (fullscreen ? "Fullscreen" : "Windowed"));
    }
    
    public void enableSubtitles(boolean enable, String language) {
        this.subtitlesEnabled = enable;
        this.subtitleLanguage = language;
        System.out.println("Subtitles " + (enable ? "enabled (" + language + ")" : "disabled"));
    }
    
    public void changeResolution(String resolution) {
        this.resolution = resolution;
        System.out.println("Video resolution changed to " + resolution);
    }
    
    public void changePlaybackSpeed(double speed) {
        if (speed > 0.25 && speed <= 3.0) {
            System.out.println("Playback speed set to " + speed + "x");
        } else {
            System.out.println("Invalid speed. Use 0.25x to 3.0x");
        }
    }
    
    private String getFileExtension(String filePath) {
        int lastDot = filePath.lastIndexOf('.');
        return lastDot > 0 ? filePath.substring(lastDot + 1).toLowerCase() : "";
    }
    
    private String detectVideoCodec(String extension) {
        switch (extension) {
            case "mp4": return "H.264";
            case "avi": return "XVID";
            case "mkv": return "H.265";
            case "mov": return "H.264";
            case "wmv": return "WMV";
            case "flv": return "FLV";
            case "webm": return "VP9";
            default: return "Unknown";
        }
    }
    
    private String formatTime(double seconds) {
        int hours = (int) seconds / 3600;
        int minutes = (int) (seconds % 3600) / 60;
        int secs = (int) seconds % 60;
        return String.format("%d:%02d:%02d", hours, minutes, secs);
    }
}
```

### 2. Complete Abstraction Demonstration
```java
public class AbstractionExample {
    public static void main(String[] args) {
        System.out.println("=== ABSTRACTION DEMONSTRATION ===");
        
        demonstrateAbstractClasses();
        demonstrateInterfaces();
        demonstrateMediaPlayerSystem();
        demonstratePaymentProcessing();
    }
    
    public static void demonstrateAbstractClasses() {
        System.out.println("\n--- ABSTRACT CLASSES ---");
        
        // Cannot instantiate abstract class
        // Vehicle vehicle = new Vehicle(); // Compilation error!
        
        // Create concrete implementations
        Vehicle car = new Car("Honda", "Civic", 2023, "Silver", 4, "CVT");
        Vehicle motorcycle = new Motorcycle("Yamaha", "R1", 2023, "Blue", 1000, false);
        
        Vehicle[] vehicles = {car, motorcycle};
        
        for (Vehicle vehicle : vehicles) {
            System.out.println("\n" + "=".repeat(40));
            vehicle.displayInfo();
            vehicle.start();
            vehicle.accelerate();
            vehicle.brake();
            vehicle.stop();
            vehicle.performMaintenance();
        }
    }
    
    public static void demonstrateInterfaces() {
        System.out.println("\n--- INTERFACES ---");
        
        // Create objects implementing multiple interfaces
        Circle circle = new Circle(5.0, 100, 200);
        Rectangle rectangle = new Rectangle(8.0, 6.0, 300, 400);
        
        // Interface polymorphism
        Drawable[] drawables = {circle, rectangle};
        Moveable[] moveables = {circle, rectangle};
        Colorable[] colorables = {circle, rectangle};
        
        System.out.println("=== Drawable Operations ===");
        for (Drawable drawable : drawables) {
            drawable.draw();
            drawable.resize(1.2);
            System.out.println();
        }
        
        System.out.println("=== Moveable Operations ===");
        for (Moveable moveable : moveables) {
            moveable.move(50, -30);
            System.out.println("Current position: (" + moveable.getX() + ", " + moveable.getY() + ")");
            System.out.println();
        }
        
        System.out.println("=== Colorable Operations ===");
        for (Colorable colorable : colorables) {
            colorable.setColor("Purple");
            colorable.changeOpacity(0.7);
            System.out.println("Color: " + colorable.getColor());
            System.out.println();
        }
    }
    
    public static void demonstrateMediaPlayerSystem() {
        System.out.println("\n--- MEDIA PLAYER SYSTEM ---");
        
        // Create different media players
        AudioPlayer audioPlayer = new AudioPlayer();
        VideoPlayer videoPlayer = new VideoPlayer();
        
        // Array of abstract references
        MediaPlayer[] players = {audioPlayer, videoPlayer};
        
        for (MediaPlayer player : players) {
            System.out.println("\n" + "=".repeat(50));
            player.displayPlayerInfo();
            
            // Use template method
            if (player instanceof AudioPlayer) {
                player.playMedia("music/song.mp3");
                AudioPlayer ap = (AudioPlayer) player;
                ap.enableEqualizer(true);
                ap.setEqualizerBand("Bass", 3.0);
                ap.setEqualizerBand("Treble", 2.0);
            } else if (player instanceof VideoPlayer) {
                player.playMedia("videos/movie.mp4");
                VideoPlayer vp = (VideoPlayer) player;
                vp.setFullscreen(true);
                vp.enableSubtitles(true, "Spanish");
                vp.changePlaybackSpeed(1.25);
            }
            
            // Common operations
            player.setVolume(0.8);
            player.seek(30.0);
            player.pause();
            player.play();
            player.stop();
        }
    }
    
    public static void demonstratePaymentProcessing() {
        System.out.println("\n--- PAYMENT PROCESSING SYSTEM ---");
        
        // Create different payment processors
        PaymentProcessor[] processors = {
            new CreditCardProcessor("1234567890123456", "John Doe"),
            new PayPalProcessor("user@example.com"),
            new BankTransferProcessor("9876543210", "Chase Bank")
        };
        
        double[] amounts = {99.99, 249.50, 1500.00};
        String[] currencies = {"USD", "EUR", "CAD"};
        
        for (int i = 0; i < processors.length; i++) {
            PaymentProcessor processor = processors[i];
            double amount = amounts[i];
            String currency = currencies[i];
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("Testing: " + processor.getPaymentMethodName());
            
            // Use interface methods
            processor.executePayment(amount, currency);
            
            // Test static method
            String transactionId = PaymentProcessor.generateTransactionId();
            System.out.println("Generated transaction ID: " + transactionId);
            
            // Test currency validation
            System.out.println("Is GBP valid? " + PaymentProcessor.isValidCurrency("GBP"));
            System.out.println("Is XYZ valid? " + PaymentProcessor.isValidCurrency("XYZ"));
            
            System.out.println("Supported currencies: " + processor.getSupportedCurrencies());
        }
        
        // Demonstrate failed validation
        System.out.println("\n=== VALIDATION FAILURES ===");
        processors[0].executePayment(-50, "USD");    // Negative amount
        processors[1].executePayment(100, "XYZ");    // Invalid currency
        processors[2].executePayment(5, "USD");      // Below minimum for bank transfer
    }
}
```

## Interview Questions & Answers

**Q1: What is abstraction? How does it differ from encapsulation?**
**A:** Abstraction hides implementation complexity and shows only essential features to users. Encapsulation hides data and implementation details within a class. Abstraction focuses on "what" an object does, while encapsulation focuses on "how" it does it internally.

**Q2: What's the difference between abstract classes and interfaces?**
**A:**
- **Abstract classes**: Can have concrete methods, constructors, instance variables, any access modifier
- **Interfaces**: Only abstract methods (pre-Java 8), default/static methods (Java 8+), public constants only

**Q3: When should you use abstract classes vs interfaces?**
**A:**
- **Abstract classes**: When classes share common implementation, need constructors, or have is-a relationship
- **Interfaces**: When defining contracts, need multiple inheritance, or have can-do relationship

**Q4: Can abstract classes have constructors?**
**A:** Yes, abstract classes can have constructors. They're called when a subclass is instantiated, but you cannot directly instantiate an abstract class.

**Q5: What are default methods in interfaces? Why were they added?**
**A:** Default methods provide default implementation in interfaces (Java 8+). They enable interface evolution without breaking existing implementations and allow multiple inheritance of behavior.

**Q6: Can you have abstract methods in concrete classes?**
**A:** No, concrete (non-abstract) classes cannot have abstract methods. If a class has even one abstract method, it must be declared abstract.

**Q7: What is the Template Method pattern in abstraction?**
**A:** Template Method defines the structure of an algorithm in an abstract class, with some steps implemented and others left abstract for subclasses to implement.

**Q8: Can interfaces extend other interfaces?**
**A:** Yes, interfaces can extend other interfaces using `extends` keyword. They can extend multiple interfaces (multiple inheritance).

## Key Takeaways

1. **Abstraction hides complexity** and provides simplified interfaces
2. **Abstract classes** combine abstract and concrete elements for partial abstraction
3. **Interfaces** provide complete abstraction with contracts for implementation
4. **Template Method pattern** defines algorithms with flexible implementation points
5. **Default methods** enable interface evolution without breaking changes
6. **Multiple interface implementation** provides flexible design options
7. **Static methods in interfaces** offer utility functions related to the interface
8. **Abstract classes support constructors** for common initialization
9. **Polymorphism through abstraction** enables flexible, maintainable code
10. **Real-world abstraction** simplifies complex systems like payment processing, media players

---

*Remember: Abstraction is like using a TV remote - you only see the buttons you need (interface) while all the complex electronics are hidden inside (implementation)!*