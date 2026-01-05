# Structural Design Patterns

Structural patterns deal with object composition and the relationships between entities. They help create new functionality by combining objects and classes in elegant ways that add flexibility without modifying existing code. These patterns focus on realizing new functionality by combining objects and classes.

## Table of Contents

1. [Adapter Pattern](#adapter-pattern)
2. [Bridge Pattern](#bridge-pattern)
3. [Composite Pattern](#composite-pattern)
4. [Decorator Pattern](#decorator-pattern)
5. [Facade Pattern](#facade-pattern)
6. [Flyweight Pattern](#flyweight-pattern)
7. [Proxy Pattern](#proxy-pattern)

---

## Adapter Pattern

### Definition

The Adapter pattern converts the interface of a class into another interface clients expect. It lets classes work together that couldn't otherwise because of incompatible interfaces. The adapter acts as a bridge between incompatible components.

### Problem It Solves

- Two incompatible interfaces need to work together
- Legacy code uses different interface than new code
- Third-party libraries have interfaces you cannot change
- Want to reuse class with incompatible interface
- Need to integrate components designed independently

### Implementation Patterns

**Class Adapter (Inheritance)**
```java
// Existing incompatible class
public class OldSystem {
    public void legacyRequest() {
        System.out.println("Old system response");
    }
}

// Target interface
public interface NewSystemInterface {
    void newRequest();
}

// Class Adapter
public class ClassAdapter extends OldSystem implements NewSystemInterface {
    @Override
    public void newRequest() {
        legacyRequest(); // Convert old method call to new
    }
}
```

**Object Adapter (Composition)**
```java
public class ObjectAdapter implements NewSystemInterface {
    private OldSystem oldSystem;
    
    public ObjectAdapter(OldSystem oldSystem) {
        this.oldSystem = oldSystem;
    }
    
    @Override
    public void newRequest() {
        oldSystem.legacyRequest();
    }
}
```

### Explanation

The adapter pattern works by:
1. Accepting requests in the new interface format
2. Translating those requests to the old interface
3. Delegating to the adapted object
4. Returning results to the caller

This allows incompatible components to collaborate without modification.

### Advantages

- **Integration**: Allows incompatible classes to work together
- **Reusability**: Enables reuse of classes with different interfaces
- **No Modification**: Doesn't modify original classes
- **Separation**: Isolates adaptation logic from client code
- **Flexibility**: Can use composition or inheritance as needed

### Disadvantages

- **Added Complexity**: Extra layer of indirection
- **Performance**: Method call overhead
- **Runtime Overhead**: Translation adds processing time
- **Code Duplication**: May duplicate existing interface logic
- **Tight Coupling**: Adapter couples to specific incompatible class

### Use Cases

- **Legacy Integration**: Making old code work with new systems
- **Third-Party Libraries**: Adapting external library interfaces
- **Different Versions**: Supporting multiple versions of an interface
- **Protocol Conversion**: Converting between different protocols
- **Format Conversion**: Adapting data formats

### When to Use

- Need to use class with incompatible interface
- Want to reuse code with different expected interface
- Multiple objects with different interfaces need uniformity
- Creating wrapper for external library
- Bridging legacy and modern code

---

## Bridge Pattern

### Definition

The Bridge pattern decouples an abstraction from its implementation so the two can vary independently. It provides a bridge between abstract and concrete layers, allowing them to change without affecting each other.

### Problem It Solves

- Implementation tied to abstraction creates tight coupling
- Changes to abstraction force implementation changes
- Changes to implementation affect abstraction
- Different platforms need different implementations
- Prevents "diamond problem" from multiple inheritance

### Key Difference from Adapter

- **Adapter**: Fixes incompatibility between existing interfaces
- **Bridge**: Decouples abstraction from implementation during design

### Implementation Pattern

```java
// Abstraction
public abstract class Shape {
    protected Color color; // Bridge to implementation
    
    public Shape(Color color) {
        this.color = color;
    }
    
    abstract void draw();
}

// Refined Abstractions
public class Circle extends Shape {
    public Circle(Color color) {
        super(color);
    }
    
    @Override
    void draw() {
        System.out.print("Circle: ");
        color.fill();
    }
}

public class Rectangle extends Shape {
    public Rectangle(Color color) {
        super(color);
    }
    
    @Override
    void draw() {
        System.out.print("Rectangle: ");
        color.fill();
    }
}

// Implementation interface (bridge)
public interface Color {
    void fill();
}

// Concrete Implementations
public class RedColor implements Color {
    @Override
    public void fill() {
        System.out.println("Filling with red");
    }
}

public class BlueColor implements Color {
    @Override
    public void fill() {
        System.out.println("Filling with blue");
    }
}

// Usage - Abstraction and implementation vary independently
Shape redCircle = new Circle(new RedColor());
Shape blueRectangle = new Rectangle(new BlueColor());

redCircle.draw();      // Circle: Filling with red
blueRectangle.draw();  // Rectangle: Filling with blue
```

### Explanation

The bridge pattern:
1. Creates abstraction hierarchy (Shape and subclasses)
2. Creates implementation hierarchy (Color and subclasses)
3. Bridges between them via composition
4. Allows independent variation

This is particularly useful with combinatorial explosion potential (n abstractions × m implementations).

### Advantages

- **Decoupling**: Abstraction and implementation independent
- **Flexibility**: Easy to extend abstractions and implementations
- **Avoids Tight Coupling**: Changes in one hierarchy don't affect other
- **Platform Independence**: Different implementations per platform
- **Eliminates Diamond**: Avoids multiple inheritance problems

### Disadvantages

- **Added Complexity**: More classes and interfaces
- **Over-Engineering**: Can be overkill for simple cases
- **Indirection Overhead**: Extra method call overhead
- **Learning Curve**: More difficult to understand initially

### Use Cases

- **GUI Frameworks**: Different rendering per operating system
- **Database Abstraction**: Different databases same interface
- **Media Players**: Different codecs, different players
- **Device Drivers**: Same functionality on different devices
- **Configuration Systems**: Different storage backends

### When to Use

- Want to avoid permanent binding between abstraction and implementation
- Changes to implementation shouldn't affect abstraction consumers
- Need to share implementation across multiple abstractions
- Want to reduce class explosion from combinatorial possibilities
- Different platform-specific implementations needed

---

## Composite Pattern

### Definition

The Composite pattern composes objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects and compositions of objects uniformly.

### Problem It Solves

- Need to represent hierarchical structures (trees)
- Want to treat individual objects and compositions the same
- Client code shouldn't care if working with leaf or branch
- Building flexible hierarchies with different component types
- Want to add new component types without changing client code

### Implementation Pattern

```java
// Component interface
public abstract class Component {
    public abstract void operation();
    public abstract void add(Component component);
    public abstract void remove(Component component);
}

// Leaf
public class Leaf extends Component {
    private String name;
    
    public Leaf(String name) {
        this.name = name;
    }
    
    @Override
    public void operation() {
        System.out.println("Leaf " + name);
    }
    
    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }
}

// Composite
public class Composite extends Component {
    private String name;
    private List<Component> children = new ArrayList<>();
    
    public Composite(String name) {
        this.name = name;
    }
    
    @Override
    public void operation() {
        System.out.println("Composite " + name);
        for (Component child : children) {
            child.operation();
        }
    }
    
    @Override
    public void add(Component component) {
        children.add(component);
    }
    
    @Override
    public void remove(Component component) {
        children.remove(component);
    }
}

// Usage - Uniform treatment of objects
Component root = new Composite("root");
Component branch1 = new Composite("branch1");
Component leaf1 = new Leaf("leaf1");
Component leaf2 = new Leaf("leaf2");

root.add(branch1);
root.add(leaf1);
branch1.add(leaf2);

root.operation(); // Recursively processes all children
```

### Real-World Example: File System

```java
public abstract class FileSystemItem {
    public abstract void display();
    public abstract int getSize();
}

public class File extends FileSystemItem {
    private String name;
    private int size;
    
    @Override
    public void display() {
        System.out.println("File: " + name);
    }
    
    @Override
    public int getSize() { return size; }
}

public class Directory extends FileSystemItem {
    private String name;
    private List<FileSystemItem> items = new ArrayList<>();
    
    @Override
    public void display() {
        System.out.println("Directory: " + name);
        for (FileSystemItem item : items) {
            item.display();
        }
    }
    
    @Override
    public int getSize() {
        return items.stream()
            .mapToInt(FileSystemItem::getSize)
            .sum();
    }
    
    public void add(FileSystemItem item) {
        items.add(item);
    }
}
```

### Advantages

- **Simplicity**: Treat leaf and composite uniformly
- **Flexibility**: Easy to add new component types
- **Tree Structures**: Natural representation of hierarchies
- **Recursive Composition**: Elegant recursive operations
- **Clean Code**: Client code simpler and more readable

### Disadvantages

- **Type Safety**: Casting needed to access specific operations
- **Complex Hierarchies**: Can become deep and hard to manage
- **Design Constraints**: Assumes tree structure appropriate
- **Operation Constraints**: Some operations don't apply to leaves/branches

### Use Cases

- **File Systems**: Directories containing files
- **UI Components**: Windows containing panels and controls
- **Organization Charts**: Departments containing teams/employees
- **Graphics**: Drawing scenes with groups and shapes
- **Menus**: Menu items and submenus
- **XML/DOM**: Element hierarchies

### When to Use

- Need to represent part-whole hierarchies
- Want clients to ignore differences between leaf and composite
- Hierarchies are the main abstraction
- Adding new component types should be easy
- Complex tree structures need traversal

---

## Decorator Pattern

### Definition

The Decorator pattern attaches additional responsibilities to an object dynamically. Decorators provide flexible alternative to subclassing for extending functionality.

### Problem It Solves

- Need to add features to objects without modifying them
- Subclassing creates class explosion
- Features can be combined in various ways
- Want to add features at runtime
- Original class shouldn't be modified

### Implementation Pattern

```java
// Component interface
public interface Component {
    String getDescription();
    double cost();
}

// Concrete Component
public class Beverage implements Component {
    @Override
    public String getDescription() {
        return "Basic Beverage";
    }
    
    @Override
    public double cost() {
        return 2.00;
    }
}

// Decorator base class
public abstract class BeverageDecorator implements Component {
    protected Component beverage;
    
    public BeverageDecorator(Component beverage) {
        this.beverage = beverage;
    }
}

// Concrete Decorators
public class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(Component beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }
}

public class WhippedCreamDecorator extends BeverageDecorator {
    public WhippedCreamDecorator(Component beverage) {
        super(beverage);
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.75;
    }
}

// Usage - Compose features dynamically
Component coffee = new Beverage();
coffee = new MilkDecorator(coffee);
coffee = new WhippedCreamDecorator(coffee);

System.out.println(coffee.getDescription());  // Basic Beverage, Milk, Whipped Cream
System.out.println(coffee.cost());            // 3.25
```

### Key Difference from Inheritance

**Inheritance Approach** (Class Explosion):
```java
class Beverage { }
class BeverageWithMilk extends Beverage { }
class BeverageWithWhippedCream extends Beverage { }
class BeverageWithMilkAndWhippedCream extends Beverage { }
// Exponential growth with more features
```

**Decorator Approach** (Flexible Composition):
```java
Beverage b = new Beverage();
b = new MilkDecorator(b);
b = new WhippedCreamDecorator(b);
// Linear growth with more features
```

### Advantages

- **Flexible Feature Addition**: Add features at runtime
- **Avoids Subclass Explosion**: No need for countless subclasses
- **Single Responsibility**: Each decorator handles one concern
- **Composable**: Combine decorators in various ways
- **Original Unchanged**: Original class remains unmodified

### Disadvantages

- **Wrapper Objects**: Creates many wrapper objects
- **Ordering Matters**: Order of decorators affects behavior
- **Complexity**: Can be harder to understand execution path
- **Debugging**: Stack of wrappers harder to debug
- **Performance**: Each wrapper adds method call overhead

### Use Cases

- **I/O Streams**: Java streams (BufferedInputStream, etc.)
- **UI Components**: Adding borders, scrollbars, effects
- **Logging**: Wrapping objects to add logging
- **Encryption**: Wrapping data with encryption
- **Compression**: Adding compression to data streams
- **Feature Toggles**: Adding optional features dynamically

### When to Use

- Need to add features without modifying original
- Feature combinations are numerous
- Want to compose features flexibly
- Features need to be added/removed at runtime
- Subclassing would cause class explosion

---

## Facade Pattern

### Definition

The Facade pattern provides a unified, simplified interface to a set of interfaces in a subsystem. It hides the complexity of the subsystem and provides easy access to its functionality.

### Problem It Solves

- Subsystems are complex with many interdependent classes
- Client code depends on many classes
- Want to simplify access to complex subsystems
- Decoupling client from subsystem details
- Complex initialization or coordination needed

### Implementation Pattern

```java
// Complex subsystem classes
public class Engine {
    public void start() { System.out.println("Engine started"); }
    public void stop() { System.out.println("Engine stopped"); }
}

public class Fuel {
    public void pump() { System.out.println("Fuel pumped"); }
}

public class Ignition {
    public void on() { System.out.println("Ignition on"); }
    public void off() { System.out.println("Ignition off"); }
}

public class Lights {
    public void on() { System.out.println("Lights on"); }
    public void off() { System.out.println("Lights off"); }
}

// Facade
public class Car {
    private Engine engine = new Engine();
    private Fuel fuel = new Fuel();
    private Ignition ignition = new Ignition();
    private Lights lights = new Lights();
    
    public void startCar() {
        lights.on();
        fuel.pump();
        ignition.on();
        engine.start();
    }
    
    public void stopCar() {
        engine.stop();
        ignition.off();
        lights.off();
    }
}

// Client usage - Simple interface
Car car = new Car();
car.startCar();   // Handles all complex coordination
car.stopCar();
```

### Explanation

The facade pattern:
1. Encapsulates complex subsystem
2. Provides simplified public interface
3. Coordinates subsystem components
4. Hides complexity from clients

Client code becomes simpler and decoupled from subsystem details.

### Advantages

- **Simplification**: Complex subsystems appear simple
- **Decoupling**: Clients don't depend on subsystem details
- **Flexibility**: Can change subsystem without affecting clients
- **Organization**: Groups related functionality
- **Ease of Use**: Simpler API for common operations

### Disadvantages

- **God Object**: Facade can become too large
- **Limited Functionality**: Only exposes common operations
- **Reduced Flexibility**: Can't access specialized subsystem features easily
- **Over-Simplification**: Might hide important details

### Use Cases

- **Frameworks**: Providing simple API for complex framework
- **Library Wrappers**: Simplifying third-party library use
- **REST API**: Facade as API gateway
- **Database Access**: ORM frameworks as database facade
- **Logging Frameworks**: Unified logging over different backends
- **Build Systems**: Maven/Gradle simplifying build process

### When to Use

- Subsystem is complex with many classes
- Want to reduce dependencies on subsystem
- Need simplified interface for common operations
- Layered subsystems need clear boundaries
- Want to decouple from subsystem implementation

---

## Flyweight Pattern

### Definition

The Flyweight pattern uses sharing to support large numbers of fine-grained objects efficiently. It reduces memory usage by sharing common state between multiple objects.

### Problem It Solves

- Creating many similar objects uses excessive memory
- Objects share significant common state
- Memory or performance becomes critical issue
- Large numbers of similar objects needed simultaneously

### Implementation Pattern

```java
// Intrinsic state (shared) - immutable
public class CharacterFont {
    private String fontFamily;
    private int fontSize;
    
    public CharacterFont(String fontFamily, int fontSize) {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
    
    public void display(String character, int position) {
        System.out.println("Character: " + character + 
            " at position " + position + 
            " Font: " + fontFamily + " " + fontSize);
    }
}

// Flyweight Factory
public class CharacterFontFactory {
    private Map<String, CharacterFont> cache = new HashMap<>();
    
    public CharacterFont getFont(String fontFamily, int fontSize) {
        String key = fontFamily + fontSize;
        
        if (!cache.containsKey(key)) {
            cache.put(key, new CharacterFont(fontFamily, fontSize));
        }
        
        return cache.get(key);
    }
}

// Extrinsic state (unique) - varies per object
public class Character {
    private CharacterFont font; // Shared flyweight
    private int position;       // Unique extrinsic state
    private String letter;
    
    public Character(CharacterFont font, int position, String letter) {
        this.font = font;
        this.position = position;
        this.letter = letter;
    }
    
    public void render() {
        font.display(letter, position);
    }
}

// Usage - Many objects, shared fonts
CharacterFontFactory factory = new CharacterFontFactory();
CharacterFont arialSmall = factory.getFont("Arial", 12);
CharacterFont timesLarge = factory.getFont("Times", 14);

List<Character> text = new ArrayList<>();
text.add(new Character(arialSmall, 0, "H"));
text.add(new Character(arialSmall, 1, "e"));
text.add(new Character(arialSmall, 2, "l"));
text.add(new Character(arialSmall, 3, "l"));
text.add(new Character(arialSmall, 4, "o"));

// All characters share font objects, reducing memory
for (Character c : text) {
    c.render();
}
```

### Key Concepts

- **Intrinsic State**: Immutable, shared state stored in flyweight
- **Extrinsic State**: Unique, variable state stored outside flyweight
- **Flyweight Factory**: Manages flyweight creation and sharing

### Advantages

- **Memory Efficiency**: Dramatically reduces memory usage
- **Performance**: Less object creation and garbage collection
- **Scalability**: Supports large numbers of objects
- **Transparency**: Client code usually unaffected

### Disadvantages

- **Complexity**: Adds complexity to object creation
- **Thread Safety**: Shared objects must be thread-safe
- **Lookup Overhead**: Factory lookup has performance cost
- **Mutable State**: Extrinsic state must be immutable and thread-safe

### Use Cases

- **Text Editors**: Character objects share font flyweights
- **Games**: Terrain tiles, sprite textures, particle effects
- **Graphics**: Shared colors, patterns, textures
- **Database Connections**: Shared connection pools
- **Java Strings**: String intern pool uses flyweight pattern
- **Icon/Image Caches**: UI components sharing images

### When to Use

- Application creates many similar objects
- Memory or performance is critical
- Objects have shareable intrinsic state
- Large number of objects needed simultaneously
- Intrinsic state is immutable

---

## Proxy Pattern

### Definition

The Proxy pattern provides a placeholder or surrogate for another object to control access to it. The proxy stands in for the real subject and provides additional behavior while maintaining the same interface.

### Problem It Solves

- Direct access to object is expensive (remote, complex)
- Need to control access to sensitive objects
- Want to add functionality before/after object access
- Object creation is expensive (lazy initialization)
- Need to protect object from unauthorized access

### Implementation Pattern

```java
// Subject interface
public interface Image {
    void display();
}

// Real Subject
public class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    
    private void loadFromDisk() {
        System.out.println("Loading image: " + filename);
    }
    
    @Override
    public void display() {
        System.out.println("Displaying: " + filename);
    }
}

// Proxy
public class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    
    public ImageProxy(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // Lazy loading
        }
        realImage.display();
    }
}

// Usage - Object created only when needed
Image image = new ImageProxy("photo.jpg");
// Not loaded yet
image.display(); // Now loaded and displayed
image.display(); // Reuses already loaded image
```

### Types of Proxies

**1. Virtual Proxy** (Lazy Initialization)
```java
public class VirtualProxy implements Image {
    private RealImage realImage;
    private String filename;
    
    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
```

**2. Remote Proxy** (Remote Object Access)
```java
public class RemoteImageProxy implements Image {
    @Override
    public void display() {
        // Network call to remote server
        fetchFromRemoteServer();
    }
}
```

**3. Protection Proxy** (Access Control)
```java
public class ProtectionProxy implements Image {
    private RealImage realImage;
    private boolean authorized;
    
    public ProtectionProxy(RealImage image, boolean authorized) {
        this.realImage = image;
        this.authorized = authorized;
    }
    
    @Override
    public void display() {
        if (authorized) {
            realImage.display();
        } else {
            System.out.println("Access denied");
        }
    }
}
```

**4. Logging Proxy** (Add Functionality)
```java
public class LoggingProxy implements Image {
    private RealImage realImage;
    
    @Override
    public void display() {
        System.out.println("Accessing image...");
        realImage.display();
        System.out.println("Image access logged");
    }
}
```

### Advantages

- **Lazy Initialization**: Defer expensive operations
- **Access Control**: Restrict access to objects
- **Logging/Auditing**: Track object access
- **Remote Access**: Access objects across network
- **Caching**: Cache expensive computations
- **Same Interface**: Transparent to client code

### Disadvantages

- **Performance**: Additional method call overhead
- **Complexity**: Extra layer of indirection
- **Same Interface**: Proxy must implement full interface
- **Debugging**: Harder to trace execution
- **Performance Cost**: Proxy adds latency

### Use Cases

- **Lazy Loading**: Deferring expensive object creation
- **Remote Objects**: RMI, web services
- **Access Control**: Permission checking
- **Logging**: Request logging
- **Caching**: Caching expensive operations
- **Smart References**: Reference counting, garbage collection
- **Database Connections**: Proxy for connection objects

### When to Use

- Object creation or access is expensive
- Need to control access to object
- Want to add functionality before/after access
- Remote object access needed
- Lazy initialization desired
- Access logging/auditing required

---

## Summary and Selection Guide

| Pattern | Purpose | Key Benefit | Complexity |
|---------|---------|---|---|
| **Adapter** | Incompatible interfaces | Integration | Low |
| **Bridge** | Decouple abstraction | Flexibility | Medium |
| **Composite** | Tree hierarchies | Uniform treatment | Medium |
| **Decorator** | Add features dynamically | Flexibility | Medium |
| **Facade** | Simplify complex subsystems | Easy use | Low |
| **Flyweight** | Share objects efficiently | Memory efficiency | High |
| **Proxy** | Control access | Additional functionality | Medium |

Choose structural patterns based on how you need to organize and compose your objects. These patterns often work together to create flexible architectures.

---

**References**:
- Gang of Four: "Design Patterns"
- Effective Java by Joshua Bloch
- Head First Design Patterns
- Java Design Pattern implementations
