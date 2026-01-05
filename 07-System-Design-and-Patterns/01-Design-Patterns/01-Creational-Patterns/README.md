# Creational Design Patterns

Creational patterns provide object creation mechanisms that increase flexibility and reuse of existing code. They abstract the instantiation process to make systems independent of how their objects are created, composed, and represented.

## Table of Contents

1. [Singleton Pattern](#singleton-pattern)
2. [Factory Method Pattern](#factory-method-pattern)
3. [Abstract Factory Pattern](#abstract-factory-pattern)
4. [Builder Pattern](#builder-pattern)
5. [Prototype Pattern](#prototype-pattern)
6. [Object Pool Pattern](#object-pool-pattern)

---

## Singleton Pattern

### Definition

The Singleton pattern restricts the instantiation of a class to a single instance and provides a global point of access to that instance. It ensures that only one object of a particular class is ever created and provides a global access point to obtain that unique instance.

### Problem It Solves

Many scenarios require exactly one instance of a class:
- Database connections need centralized management
- Configuration managers should have a single, shared configuration
- Logger instances should be unified to maintain consistent output
- Thread pools need centralized management
- Caches should be single instances to maximize efficiency
- GUI applications need one main window or application state

Without the Singleton pattern, multiple instances might be created accidentally, leading to inconsistencies, resource waste, or conflicting states.

### Implementation Approaches

**1. Eager Initialization (Thread-Safe by Default)**
```java
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    
    private Singleton() {
        // Private constructor prevents instantiation
    }
    
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```
- Thread-safe automatically
- Instance created even if never used
- Simple and clear

**2. Lazy Initialization with Synchronized Method**
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
- Instance created only when needed
- Synchronization ensures thread safety
- Performance overhead due to synchronization on every call

**3. Double-Checked Locking (Optimized Lazy)**
```java
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```
- Minimizes synchronization overhead
- Requires volatile keyword for visibility
- More complex but efficient

**4. Class Loader Initialization (Bill Pugh Singleton)**
```java
public class Singleton {
    private Singleton() {}
    
    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```
- Thread-safe and efficient
- Lazy initialization without synchronization overhead
- Recommended approach in Java

**5. Enum Singleton (Serialization-Safe)**
```java
public enum Singleton {
    INSTANCE;
    
    public void doSomething() {
        // Implementation
    }
}
```
- Built-in serialization support
- Protection against reflection attacks
- Simplest and most robust approach
- Recommended for modern Java

### Benefits

- **Single Instance**: Guarantees only one instance exists
- **Global Access**: Easy to access from anywhere in application
- **Lazy Initialization**: Can defer expensive initialization
- **Thread-Safe**: Modern implementations ensure thread safety
- **Control**: Centralized control over creation and initialization
- **Lazy Resource Allocation**: Resources used only when needed

### Drawbacks

- **Testing Difficulty**: Hard to mock for unit testing
- **Hidden Dependencies**: Global access masks real dependencies
- **Thread Safety Overhead**: Synchronization adds performance cost
- **Serialization Issues**: Requires special handling for serialization
- **Reflection Attacks**: Can be circumvented using reflection
- **Flexibility Issues**: Tied to specific implementation

### Real-World Use Cases

**1. Logging Framework**
```
Logger.getInstance().log("Application started");
```
Ensures all parts of application log to same output destination.

**2. Configuration Manager**
```
Config config = Config.getInstance();
String dbUrl = config.getDatabaseUrl();
```
Provides single source of configuration values.

**3. Database Connection Pool**
```
ConnectionPool pool = ConnectionPool.getInstance();
Connection conn = pool.getConnection();
```
Manages single pool of database connections.

**4. Application Cache**
```
Cache cache = Cache.getInstance();
cache.put(key, value);
```
Maintains single application-wide cache.

**5. Thread Pool Executor**
```
ExecutorService executor = ExecutorService.getInstance();
executor.execute(task);
```
Manages single pool of worker threads.

### When to Use

- You need exactly one instance of a class
- This instance must be globally accessible
- You want lazy initialization of expensive resources
- You need centralized control over object creation
- Synchronization of shared resource access is required
- You have objects like loggers, pools, or managers

### When NOT to Use

- You need multiple instances with different configurations
- Testing requires easy mocking and dependency injection
- You're using dependency injection frameworks (prefer constructor injection)
- Instance might need to be replaced at runtime
- You need to create instances based on runtime configuration

---

## Factory Method Pattern

### Definition

The Factory Method pattern defines an interface for creating an object, but lets subclasses decide which class to instantiate. It provides an abstraction for object creation, allowing client code to work with abstract interfaces rather than concrete classes.

### Problem It Solves

Direct object instantiation creates tight coupling:
- Client code depends on concrete class names
- Adding new types requires modifying client code
- Different clients might use different creation logic
- Complex creation logic is scattered throughout codebase

The Factory Method centralizes and abstracts creation logic.

### Implementation Pattern

```java
// Abstract Creator
abstract class DocumentCreator {
    abstract Document createDocument();
    
    public void openDocument() {
        Document doc = createDocument();
        doc.open();
    }
}

// Concrete Creators
class PDFDocumentCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new PDFDocument();
    }
}

class WordDocumentCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

// Product Interface
interface Document {
    void open();
    void close();
}

// Concrete Products
class PDFDocument implements Document {
    public void open() { /* PDF opening logic */ }
    public void close() { /* PDF closing logic */ }
}

class WordDocument implements Document {
    public void open() { /* Word opening logic */ }
    public void close() { /* Word closing logic */ }
}
```

### Benefits

- **Loose Coupling**: Clients don't depend on concrete classes
- **Single Responsibility**: Creation logic centralized in factory
- **Easy to Extend**: Adding new types requires minimal changes
- **Consistency**: Ensures consistent object creation
- **Flexibility**: Can modify creation logic without affecting clients
- **Polymorphism**: Work with abstract interfaces

### Drawbacks

- **Added Complexity**: More classes and inheritance hierarchies
- **Performance Overhead**: Indirection adds slight overhead
- **Difficult to Track**: Object creation logic scattered across subclasses
- **Over-Engineering**: Can be overkill for simple cases

### Real-World Use Cases

- **UI Component Libraries**: Creating different button types
- **Database Drivers**: JDBC driver creation
- **File Format Handlers**: Creating appropriate parsers for different formats
- **Connection Managers**: Creating different connection types
- **Logger Implementations**: Creating different logger instances

### When to Use

- You have a class hierarchy and different subclasses need creation
- Client code shouldn't depend on concrete class implementations
- Object creation is complex or varies by type
- You want to centralize creation logic
- Adding new types should minimize code changes

---

## Abstract Factory Pattern

### Definition

The Abstract Factory pattern provides an interface for creating families of related or dependent objects without specifying their concrete classes. It's a factory for creating factories, enabling the creation of entire object families that work together.

### Problem It Solves

- Applications need to work with multiple families of related objects
- Object families must be used together (consistency requirements)
- Adding new families requires changes to existing code
- Client code has dependencies on concrete classes from multiple families

### Key Differences from Factory Method

- **Factory Method**: Creates one product type
- **Abstract Factory**: Creates families of related products
- **Factory Method**: Uses inheritance
- **Abstract Factory**: Uses composition

### Implementation Pattern

```java
// Abstract Factory
interface UIComponentFactory {
    Button createButton();
    TextField createTextField();
    CheckBox createCheckBox();
}

// Concrete Factories for different themes
class WindowsUIFactory implements UIComponentFactory {
    public Button createButton() { return new WindowsButton(); }
    public TextField createTextField() { return new WindowsTextField(); }
    public CheckBox createCheckBox() { return new WindowsCheckBox(); }
}

class MacUIFactory implements UIComponentFactory {
    public Button createButton() { return new MacButton(); }
    public TextField createTextField() { return new MacTextField(); }
    public CheckBox createCheckBox() { return new MacCheckBox(); }
}

// Product hierarchy
interface Button { void render(); }
interface TextField { void render(); }
interface CheckBox { void render(); }

// Concrete Products
class WindowsButton implements Button { 
    public void render() { /* Windows button rendering */ } 
}
class MacButton implements Button { 
    public void render() { /* Mac button rendering */ } 
}
// ... other concrete products
```

### Benefits

- **Family Consistency**: Ensures compatible objects work together
- **Easy Theme Support**: Switch entire UI families at runtime
- **Encapsulation**: Hides family implementation details
- **Maintainability**: Related families grouped logically
- **Scalability**: Adding new families doesn't affect existing code

### Drawbacks

- **Complexity**: More classes and interfaces than simpler approaches
- **Rigidity**: Adding new product types requires interface changes
- **Overhead**: Additional abstraction layers
- **Learning Curve**: More complex pattern to understand

### Real-World Use Cases

- **UI Frameworks**: Windows vs. Mac vs. Linux UI components
- **Database Abstraction**: MySQL vs. PostgreSQL vs. Oracle implementations
- **Operating System APIs**: Different implementations per OS
- **Renderer Selection**: Different rendering engines (2D, 3D, software)
- **Theme Management**: Different color schemes and styling families

### When to Use

- System needs to work with multiple families of products
- Object families must be used together consistently
- You want to hide family implementation details
- Adding new families should minimize changes
- Products from different families shouldn't be mixed

---

## Builder Pattern

### Definition

The Builder pattern separates the construction of a complex object from its representation, allowing step-by-step construction. It provides a flexible solution to construct objects that require many configuration options.

### Problem It Solves

**Without Builder**:
```java
// Telescoping constructor anti-pattern
Document(String title, String author, int pages) { }
Document(String title, String author, int pages, String language) { }
Document(String title, String author, int pages, String language, boolean printable) { }
// Many constructor overloads - difficult to maintain
```

Issues:
- Too many constructor overloads
- Client code unclear (what do parameters mean?)
- Adding new optional parameters requires new constructors
- Easy to pass parameters in wrong order
- Null handling becomes complex

### Implementation Pattern

```java
public class Document {
    private final String title;
    private final String author;
    private final int pages;
    private final String language;
    private final boolean printable;
    private final String format;
    
    // Private constructor
    private Document(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.pages = builder.pages;
        this.language = builder.language;
        this.printable = builder.printable;
        this.format = builder.format;
    }
    
    // Static Builder class
    public static class Builder {
        private final String title; // Required
        private final String author; // Required
        private int pages = 0; // Optional with defaults
        private String language = "English";
        private boolean printable = true;
        private String format = "PDF";
        
        public Builder(String title, String author) {
            this.title = title;
            this.author = author;
        }
        
        public Builder pages(int pages) {
            this.pages = pages;
            return this;
        }
        
        public Builder language(String language) {
            this.language = language;
            return this;
        }
        
        public Builder printable(boolean printable) {
            this.printable = printable;
            return this;
        }
        
        public Builder format(String format) {
            this.format = format;
            return this;
        }
        
        public Document build() {
            return new Document(this);
        }
    }
}

// Usage - Clear and flexible
Document doc = new Document.Builder("My Book", "John Doe")
    .pages(300)
    .language("Spanish")
    .printable(false)
    .format("EPUB")
    .build();
```

### Benefits

- **Readability**: Clear, fluent API shows what's being configured
- **Flexibility**: Easy to add new optional parameters
- **Immutability**: Built objects can be immutable
- **Validation**: Can validate complete object before creation
- **Optional Parameters**: Elegant handling of many optional properties
- **Maintainability**: Parameter order and defaults centralized

### Drawbacks

- **More Code**: Requires builder class and delegation
- **Performance**: Additional object creation overhead
- **Complexity**: Overkill for simple objects with few properties
- **Thread Safety**: Builder itself should be thread-confined

### Real-World Use Cases

- **HTTP Requests**: Building complex HTTP requests
- **Database Queries**: Constructing SQL queries with many optional clauses
- **Configuration Objects**: Creating objects with many configuration options
- **UI Dialogs**: Building dialogs with various optional components
- **JSON/XML Parsing**: Creating objects from parsed data

### When to Use

- Objects have many optional parameters
- You want clear, self-documenting construction code
- Objects should be immutable after creation
- You need to validate complete object before use
- Complex initialization logic exists
- Multiple ways to construct valid objects

---

## Prototype Pattern

### Definition

The Prototype pattern creates new objects by copying an existing object (prototype) rather than creating from scratch. It avoids costly creation operations by cloning existing instances.

### Problem It Solves

- Creating objects is computationally expensive
- Object requires complex initialization
- Want to clone objects with shared state
- Avoid subclass proliferation for object variants
- Runtime object type unknown at design time

### Implementation Pattern

```java
// Prototype Interface
public interface Cloneable {
    Object clone();
}

// Concrete Prototype
public class Employee implements Cloneable {
    private String name;
    private String department;
    private double salary;
    private List<String> projects; // Mutable object
    
    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.projects = new ArrayList<>();
    }
    
    // Shallow clone
    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Deep clone
    public Employee deepClone() {
        Employee clone = new Employee(this.name, this.department, this.salary);
        clone.projects = new ArrayList<>(this.projects);
        return clone;
    }
}

// Prototype Registry
public class EmployeeRegistry {
    private Map<String, Employee> prototypes = new HashMap<>();
    
    public void registerPrototype(String type, Employee prototype) {
        prototypes.put(type, prototype);
    }
    
    public Employee getPrototype(String type) {
        return prototypes.get(type).clone();
    }
}

// Usage
EmployeeRegistry registry = new EmployeeRegistry();
Employee seniorDeveloper = new Employee("Template", "Engineering", 100000);
registry.registerPrototype("SeniorDeveloper", seniorDeveloper);

Employee employee1 = registry.getPrototype("SeniorDeveloper");
Employee employee2 = registry.getPrototype("SeniorDeveloper");
```

### Shallow vs. Deep Cloning

**Shallow Clone**: Copies object references
- Fast but problematic with mutable fields
- Both objects reference same mutable collections

**Deep Clone**: Recursively copies all objects
- Slower but safer for complex objects
- Each cloned object has independent mutable state

### Benefits

- **Performance**: Faster than complex object creation
- **Consistency**: Clones inherit prototype's state
- **Flexibility**: Create objects at runtime without subclassing
- **Reduce Dependencies**: Prototype registry decouples from concrete classes

### Drawbacks

- **Complexity**: Deep cloning can be complex
- **Mutable Fields**: Shallow copy problematic with mutable objects
- **Performance Trade-off**: Can be slower than construction
- **Reflection Issues**: Some objects don't support cloning well

### Real-World Use Cases

- **Document Editors**: Cloning complex document structures
- **Game Development**: Creating game objects from templates
- **UI Element Replication**: Duplicating dialog components
- **Configuration Copying**: Creating modified copies of configurations
- **Undo/Redo Systems**: Storing object states for history

### When to Use

- Object creation is expensive
- Need independent copies of objects
- Want to avoid subclass proliferation
- Object variants exist at runtime
- Need object state snapshots for undo/redo

---

## Object Pool Pattern

### Definition

The Object Pool pattern maintains a pool of reusable objects instead of creating and destroying them repeatedly. Objects are obtained from the pool when needed and returned when no longer needed.

### Problem It Solves

- Creating and destroying objects repeatedly is expensive
- Objects require expensive initialization
- Garbage collection overhead accumulates
- Resource limits require controlled object creation (connections, threads)
- Need to manage lifetime of limited resources

### Implementation Pattern

```java
public interface Poolable {
    void reset();
}

public class DatabaseConnection implements Poolable {
    private String connectionString;
    
    public DatabaseConnection(String connectionString) {
        this.connectionString = connectionString;
        // Expensive initialization
    }
    
    @Override
    public void reset() {
        // Reset to clean state
    }
    
    public void execute(String query) { /* */ }
}

public class ObjectPool<T extends Poolable> {
    private final Queue<T> available = new LinkedList<>();
    private final Set<T> inUse = new HashSet<>();
    private final ObjectFactory<T> factory;
    private final int maxPoolSize;
    
    public ObjectPool(ObjectFactory<T> factory, int maxPoolSize) {
        this.factory = factory;
        this.maxPoolSize = maxPoolSize;
    }
    
    public T acquire() {
        T object;
        
        if (available.isEmpty()) {
            if (inUse.size() < maxPoolSize) {
                object = factory.create();
            } else {
                throw new RuntimeException("Pool exhausted");
            }
        } else {
            object = available.poll();
        }
        
        inUse.add(object);
        return object;
    }
    
    public void release(T object) {
        if (inUse.remove(object)) {
            object.reset();
            available.offer(object);
        }
    }
}

public interface ObjectFactory<T> {
    T create();
}

// Usage
ObjectPool<DatabaseConnection> pool = new ObjectPool<>(
    () -> new DatabaseConnection("jdbc:mysql://localhost"),
    10
);

DatabaseConnection conn = pool.acquire();
try {
    conn.execute("SELECT * FROM users");
} finally {
    pool.release(conn);
}
```

### Benefits

- **Performance**: Reuses expensive objects
- **Resource Management**: Limits resource consumption
- **Stability**: Prevents resource exhaustion
- **Scalability**: Handles high concurrency efficiently
- **Predictability**: Known resource limits

### Drawbacks

- **Complexity**: Requires management code
- **Thread Safety**: Must handle concurrent access
- **Memory Overhead**: Maintains pool of objects
- **State Management**: Objects must be properly reset
- **Debugging**: Harder to trace object lifecycle

### Real-World Use Cases

- **Connection Pooling**: Database connections (JDBC)
- **Thread Pools**: Executor Services
- **String Pools**: Java intern pools
- **Memory Buffering**: Reusable byte buffers
- **Thread-Local Caches**: Per-thread object pools

### When to Use

- Object creation is expensive
- Objects needed frequently and briefly
- Resource limits require strict control
- Performance is critical
- Need to prevent resource exhaustion

---

## Summary and Selection Guide

| Pattern | When Object Creation | Key Benefit | Complexity |
|---------|---|---|---|
| **Singleton** | Single instance | Global access, control | Low |
| **Factory Method** | Single type varies | Loose coupling | Medium |
| **Abstract Factory** | Families of objects | Family consistency | High |
| **Builder** | Complex multi-step | Clarity, flexibility | Medium |
| **Prototype** | Expensive operations | Performance | Medium |
| **Object Pool** | Frequent reuse | Resource management | High |

Choose creational patterns based on your specific creation challenges. Multiple patterns often work together in complex systems.

---

**References**:
- Gang of Four: "Design Patterns"
- Effective Java by Joshua Bloch
- Head First Design Patterns
- Java Design Pattern implementations
