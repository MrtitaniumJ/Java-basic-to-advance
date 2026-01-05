# Design Patterns

## Overview

Design Patterns are reusable solutions to common programming problems that provide templates for writing maintainable, scalable, and robust code. Understanding design patterns is essential for:
- Writing clean, maintainable code
- Solving recurring design problems
- Communicating solutions with other developers
- Preparing for system design interviews
- Creating flexible and extensible architectures

## Problem Analysis

### What Are Design Patterns?

Design Patterns are proven, reusable solutions to specific problems in software design. They represent:
- **Best practices** from experienced developers
- **Templates** for structuring code
- **Communication** tools for discussing designs
- **Standards** for solving recurring problems

### Pattern Categories

**Creational Patterns** - Object creation
- Singleton, Factory, Builder, Prototype, Abstract Factory

**Structural Patterns** - Object composition
- Adapter, Bridge, Composite, Decorator, Facade, Proxy, Flyweight

**Behavioral Patterns** - Communication between objects
- Observer, Strategy, Command, Iterator, State, Template Method

---

## Patterns Covered

### 1. **Singleton Pattern**

**Problem:** Ensure a class has only one instance and provide global access to it

**Real-World Examples:**
- Database connections
- Logger instances
- Configuration managers
- Thread pools
- Cache managers

**Implementation Approaches:**

**Approach 1: Lazy Initialization (Thread-Safe)**
```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
    public synchronized static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Approach 2: Eager Initialization**
```java
public class Singleton {
    private static final Singleton instance = new Singleton();
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        return instance;
    }
}
```

**Approach 3: Double-Checked Locking (optimized)**
```java
public synchronized static Singleton getInstance() {
    if (instance == null) {
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton();
            }
        }
    }
    return instance;
}
```

**Approach 4: Class Loader (Safest)**
```java
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton instance = new Singleton();
    }
    
    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
}
```

**Advantages:**
- Ensures single instance
- Lazy loading (when needed)
- Global access point
- Controlled instantiation

**Disadvantages:**
- Difficult to test (tight coupling)
- Global state (can hide dependencies)
- Not suitable for multi-instance scenarios
- Thread-safety complexity

**When to Use:**
- Database connections
- Logger implementations
- Configuration objects
- Thread pools
- Caches

### 2. **Factory Pattern**

**Problem:** Create objects without specifying concrete classes

**Benefits:**
- Decouples object creation from usage
- Centralizes instantiation logic
- Easy to add new types
- Single responsibility principle

**Implementation:**

**Simple Factory:**
```java
class ProcessorFactory {
    static PaymentProcessor createProcessor(String type) {
        switch(type) {
            case "credit": return new CreditCardProcessor();
            case "paypal": return new PayPalProcessor();
            default: throw new Exception("Unknown type");
        }
    }
}
```

**Factory Method:**
```java
abstract class PaymentFactory {
    abstract PaymentProcessor createProcessor();
}

class CreditCardFactory extends PaymentFactory {
    public PaymentProcessor createProcessor() {
        return new CreditCardProcessor();
    }
}
```

**Abstract Factory:**
```java
interface PaymentFactory {
    PaymentProcessor createProcessor();
    RefundProcessor createRefundProcessor();
}
```

**Real-World Examples:**
- Database drivers (JDBC)
- File parsers (JSON, XML)
- Widget creation in UI frameworks
- Java's Collection factories
- Spring dependency injection

**Advantages:**
- Decoupled design
- Easy to extend
- Centralizes creation logic
- Can implement lazy loading

**Disadvantages:**
- Extra classes and complexity
- Overkill for simple cases
- Can make code harder to follow

**When to Use:**
- Multiple object types with common interface
- Object type determined at runtime
- Want to decouple client from concrete classes

### 3. **Observer Pattern**

**Problem:** Define a subscription mechanism to notify multiple objects about state changes

**Also Known As:** Publish-Subscribe, Listener

**Components:**
- **Subject:** Maintains list of observers, notifies them
- **Observer:** Interface for objects that want updates
- **Concrete Observer:** Implements update logic

**Real-World Examples:**
- Event handling systems (GUI buttons)
- Model-View-Controller (MVC)
- Stock price updates
- Message brokers
- File system watchers
- Social media notifications

**Implementation Pattern:**
```
Subject maintains list of Observers
When state changes:
    for each Observer:
        Observer.update(event, data)
```

**Advantages:**
- Loose coupling (subject doesn't need observer details)
- Dynamic subscriptions
- Supports broadcast communication
- Follows open/closed principle

**Disadvantages:**
- Observers notified in random order
- Memory leaks if unsubscribe forgotten
- Performance impact with many observers
- Testing can be complex

**When to Use:**
- Multiple objects need updates on state change
- Don't know number of objects in advance
- Objects should react to events independently

### 4. **Strategy Pattern**

**Problem:** Define a family of algorithms, encapsulate each, make them interchangeable

**Also Known As:** Policy Pattern

**Components:**
- **Context:** Uses strategy
- **Strategy:** Common interface for algorithms
- **Concrete Strategies:** Different implementations

**Real-World Examples:**
- Sorting algorithms (merge, quick, bubble)
- File compression formats (ZIP, RAR, GZIP)
- Payment methods (credit card, PayPal, crypto)
- Routing algorithms
- Caching strategies
- Authentication methods

**Implementation Pattern:**
```
Strategy strategy = selectStrategy();
context.executeWith(strategy);

// Later, switch strategies
context.setStrategy(differentStrategy);
context.executeWith(strategy);
```

**Advantages:**
- Easy to switch algorithms
- Eliminates conditional logic
- Open/closed principle
- Single responsibility principle

**Disadvantages:**
- More classes
- Overkill for simple cases
- Client needs to choose strategy

**When to Use:**
- Multiple algorithms for same task
- Algorithm selection at runtime
- Want to avoid conditionals
- Algorithms might change frequently

### 5. **Builder Pattern**

**Problem:** Construct complex objects step by step with optional parameters

**Alternative to:** Telescoping constructors, constructor overloading

**Benefits:**
- Readable object creation
- Easy to add optional parameters
- Immutable objects possible
- Validation possible before building

**Implementation Pattern:**
```java
Computer computer = new ComputerBuilder()
    .withCPU("Intel i9")
    .withRAM("32GB")
    .withGPU("RTX 4090")
    .build();
```

**Real-World Examples:**
- StringBuilder in Java
- SQL query builders
- Configuration objects
- HTTP request builders
- UI component builders

**Advantages:**
- Readable code
- Easy to add optional parameters
- Can validate before creating
- Immutability possible
- Cleaner than many constructors

**Disadvantages:**
- Extra builder class needed
- More code than simple constructor
- Not suitable for simple objects

**Comparison with Constructor:**
```
// Bad: Too many parameters
Computer(String cpu, String mobo, String ram, String storage, 
         String gpu, String psu, String cooler)

// Good: Builder pattern
new ComputerBuilder()
    .withCPU("Intel i9")
    .withRAM("32GB")
    .build()
```

**When to Use:**
- Objects with many optional parameters
- Want immutable objects
- Construction logic complex
- Need object validation

## Design Decisions

### 1. Private Constructor for Singleton
Prevents external instantiation, forces use of getInstance()

### 2. Factory Method Return Type
Use interface/abstract class for flexibility

### 3. Observer List Storage
ArrayList for performance, other structures for ordering

### 4. Strategy Context Class
Composition over inheritance (has-a vs is-a)

### 5. Builder vs Constructor
Builders for 3+ parameters, constructors for simple cases

## Complexity Analysis

| Pattern | Creation Time | Runtime | Space |
|---------|---|---|---|
| Singleton | O(1)* | O(1) | O(1) |
| Factory | O(1)** | O(1) | O(n) |
| Observer | O(n) | O(n) | O(n) |
| Strategy | O(1) | O(1) | O(1) |
| Builder | O(n) | O(1) | O(n) |

*After first creation
**Depends on factory logic

## Sample Input/Output

### Running the Program

```powershell
javac DesignPatternDemo.java
java DesignPatternDemo
```

### Expected Output

```
╔════════════════════════════════════════════════════════╗
║          DESIGN PATTERNS IMPLEMENTATION                ║
╚════════════════════════════════════════════════════════╝

========== SINGLETON PATTERN ==========
Same instance? true
Connected to: jdbc:mysql://localhost:3306/database
db1 connected? true
db2 connected? true
Query result: Result of: SELECT * FROM users
Disconnected from database
db1 connected? false
db2 connected? false

========== FACTORY PATTERN ==========
Processing payments with different methods:
Processing credit card payment: $99.99
Card: 3456

Processing PayPal payment: $50.0
Email: user@example.com

Processing Bitcoin payment: 0.005 BTC
Wallet: 1A1z7agoat2LWANY2QYYvYzG1nrwtBqaco

========== OBSERVER PATTERN ==========
Observer subscribed to AAPL
Observer subscribed to AAPL
Observer subscribed to AAPL
Current price: $150.0

Stock price change to $155.5:
John notified: AAPL changed from $150.0 to $155.5 (change: 5.50)
Jane notified: AAPL changed from $150.0 to $155.5 (change: 5.50)
CNBC reports: AAPL moved UP by 3.67%

Stock price change to $148.75:
John notified: AAPL changed from $155.5 to $148.75 (change: -6.75)
Jane notified: AAPL changed from $155.5 to $148.75 (change: -6.75)
CNBC reports: AAPL moved DOWN by -4.34%

========== STRATEGY PATTERN ==========
Original data: Important backup data

Using GZIP:
Archiving backup.tar.gz using GzipCompression
Result: GZIP[Important backup data]

Switching to ZIP:
Archiving backup.zip using ZipCompression
Result: ZIP[Important backup data]

Switching to RAR:
Archiving backup.rar using RarCompression
Result: RAR[Important backup data]

========== BUILDER PATTERN ==========
Gaming PC Configuration:
Computer {
  CPU: Intel i9-13900K
  Motherboard: ASUS ROG Maximus Z790
  RAM: 64GB DDR5
  Storage: 2TB NVMe SSD + 4TB HDD
  GPU: NVIDIA RTX 4090
  Power Supply: 1200W 80+ Platinum
  Cooler: NZXT Kraken X73
}

Budget PC Configuration:
Computer {
  CPU: AMD Ryzen 5 5600X
  Motherboard: ASRock B450M
  RAM: 16GB DDR4
  Storage: 256GB SSD
  GPU: Not set
  Power Supply: 500W 80+ Bronze
  Cooler: Not set
}

=======================================================
All demonstrations completed successfully!
=======================================================
```

## SOLID Principles Applied

### Single Responsibility
- Each pattern class has one reason to change
- Factory handles creation only
- Observer handles notification only

### Open/Closed
- Open for extension (new implementations)
- Closed for modification (interfaces don't change)

### Liskov Substitution
- All implementations are substitutable
- PaymentProcessor implementations interchangeable

### Interface Segregation
- Small, focused interfaces
- Observer interface has single method

### Dependency Inversion
- Depend on abstractions, not concretions
- Use interfaces, not concrete classes

## Variations and Challenges

### Challenge 1: Thread-Safe Singleton
Ensure singleton is safe in multi-threaded environment.

**Solutions:**
- Synchronized method (simple)
- Double-checked locking (optimized)
- Class loader pattern (best)

### Challenge 2: Abstract Factory
Extend factory for related object families.

**Example:** UI themes (Windows, Mac, Linux)
- Each theme has buttons, windows, menus

### Challenge 3: Object Pool Pattern
Reuse expensive objects instead of creating new.

**Combine with Singleton:**
- Pool manager is singleton
- Manages object lifecycle

### Challenge 4: Decorator Pattern
Add behavior to objects dynamically.

**Example:** File compression chain
- Original file
- Add encryption
- Add compression

### Challenge 5: Template Method Pattern
Define algorithm skeleton, let subclasses fill details.

**Example:** Data processing pipeline
- Load data (subclass implements)
- Process data (subclass implements)
- Save data (subclass implements)

### Challenge 6: Command Pattern
Encapsulate requests as objects.

**Example:** Undo/Redo systems
- Each action is a Command object
- Can queue, log, and undo

## Interview Tips

### When Discussing Design Patterns

1. **Know the Problem:**
   - What problem does pattern solve?
   - When would you use it?
   - What are trade-offs?

2. **Show Real-World Examples:**
   - Java Collections uses Factory
   - Swing uses Observer
   - StringBuilder uses Builder

3. **Discuss Alternatives:**
   - When NOT to use pattern
   - Simpler solutions sometimes better
   - Avoid over-engineering

4. **Code Implementation:**
   - Write pattern code cleanly
   - Explain each component
   - Show usage examples

### Common Questions

**Q: When should you use Singleton?**
A: For shared resources (DB connection, logger). But often dependency injection is better.

**Q: Is Singleton an anti-pattern?**
A: Can be if misused. Better alternatives: dependency injection, static methods, enums.

**Q: Factory vs. Builder?**
A: Factory for object creation, Builder for complex construction.

**Q: Observer pattern vs. EventBus?**
A: Observer is 1-to-many, EventBus allows loose coupling with many producers/consumers.

**Q: Strategy pattern vs. Template Method?**
A: Strategy encapsulates algorithm (composition), Template Method defines skeleton (inheritance).

## Key Learnings

1. **Patterns solve recurring problems** - recognize them
2. **Trade-offs exist** - simplicity vs. flexibility
3. **Don't over-engineer** - use patterns when needed
4. **Combination patterns** - often used together
5. **Language-specific** - some patterns less relevant in certain languages

## Further Exploration

1. **More Patterns:** Adapter, Composite, Decorator, Facade, Proxy
2. **Architectural Patterns:** MVC, MVVM, Repository
3. **Concurrency Patterns:** Thread-safe singleton, mutex
4. **Enterprise Patterns:** DAO, Service Locator
5. **Anti-patterns:** Learn what NOT to do

## Recommended Reading

- **Design Patterns: Elements of Reusable Object-Oriented Software** (Gang of Four)
- **Head First Design Patterns** (easier to understand)
- **Refactoring to Patterns** (practical applications)

---

**Compilation**: `javac DesignPatternDemo.java`
**Execution**: `java DesignPatternDemo`
**Difficulty**: Intermediate
**Topics**: Design Patterns, SOLID Principles, OOP
**Prerequisites**: Basic Java, OOP concepts, Interfaces
