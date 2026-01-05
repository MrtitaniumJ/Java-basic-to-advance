# Behavioral Design Patterns

Behavioral patterns focus on communication between objects and the distribution of responsibility. They help define how objects interact and work together to achieve common goals while keeping them loosely coupled and flexible in their interactions.

## Table of Contents

1. [Command Pattern](#command-pattern)
2. [Iterator Pattern](#iterator-pattern)
3. [Mediator Pattern](#mediator-pattern)
4. [Memento Pattern](#memento-pattern)
5. [Observer Pattern](#observer-pattern)
6. [State Pattern](#state-pattern)
7. [Strategy Pattern](#strategy-pattern)
8. [Template Method Pattern](#template-method-pattern)
9. [Visitor Pattern](#visitor-pattern)
10. [Chain of Responsibility Pattern](#chain-of-responsibility-pattern)
11. [Interpreter Pattern](#interpreter-pattern)

---

## Command Pattern

### Concept

The Command pattern encapsulates a request as an object, allowing parameterization of clients with different requests, queuing of requests, and logging of requests. It decouples the object that invokes the request from the one that processes it.

### Problem Solved

- Need to parameterize objects with operations
- Want to queue, log, or undo requests
- Multiple clients with different requests
- Decouple invoker from receiver
- Execute requests at different times
- Implement operation queuing or request buffering

### Implementation Approach

```java
// Command interface
public interface Command {
    void execute();
    void undo();
}

// Receiver
public class Light {
    public void on() {
        System.out.println("Light is on");
    }
    
    public void off() {
        System.out.println("Light is off");
    }
}

// Concrete Commands
public class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    @Override
    public void undo() {
        light.off();
    }
}

public class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    @Override
    public void undo() {
        light.on();
    }
}

// Invoker
public class RemoteControl {
    private Command[] buttons = new Command[7];
    private Command lastCommand;
    
    public void setCommand(int slot, Command command) {
        buttons[slot] = command;
    }
    
    public void pressButton(int slot) {
        buttons[slot].execute();
        lastCommand = buttons[slot];
    }
    
    public void pressUndo() {
        lastCommand.undo();
    }
}

// Usage
Light light = new Light();
Command lightOn = new LightOnCommand(light);
Command lightOff = new LightOffCommand(light);

RemoteControl remote = new RemoteControl();
remote.setCommand(0, lightOn);
remote.setCommand(1, lightOff);

remote.pressButton(0);  // Light on
remote.pressButton(1);  // Light off
remote.pressUndo();     // Light on
```

### Real-World Examples

- **GUI Buttons**: Button clicks as commands
- **Menu Items**: Application menu operations
- **Keyboard Shortcuts**: Keystroke to command mapping
- **Undo/Redo**: Command history for state restoration
- **Task Scheduling**: Queued command execution
- **Macro Recording**: Recording and replaying command sequences

### Implementation Considerations

**1. Undo/Redo Implementation**
```java
public class CommandHistory {
    private Stack<Command> history = new Stack<>();
    
    public void execute(Command command) {
        command.execute();
        history.push(command);
    }
    
    public void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}
```

**2. Async Command Execution**
```java
public class AsyncCommandInvoker {
    private ExecutorService executor = Executors.newFixedThreadPool(4);
    
    public void executeAsync(Command command) {
        executor.submit(() -> command.execute());
    }
}
```

### When to Use

- Need to parameterize objects with operations
- Queue, log, or time operations
- Implement undo/redo functionality
- Schedule command execution
- Separate invoker from receiver
- Build macro recording systems

---

## Iterator Pattern

### Concept

The Iterator pattern provides a way to access elements of a collection sequentially without exposing its underlying representation. It enables uniform traversal of different collection types.

### Problem Solved

- Need to access collection elements sequentially
- Different collections have different access mechanisms
- Want to hide collection internal structure
- Multiple traversals needed simultaneously
- Decouple collection from access logic

### Implementation Approach

```java
// Iterator interface
public interface Iterator<T> {
    boolean hasNext();
    T next();
}

// Collection interface
public interface Collection<T> {
    Iterator<T> iterator();
}

// Concrete Iterator
public class ArrayIterator<T> implements Iterator<T> {
    private T[] items;
    private int index = 0;
    
    public ArrayIterator(T[] items) {
        this.items = items;
    }
    
    @Override
    public boolean hasNext() {
        return index < items.length;
    }
    
    @Override
    public T next() {
        return items[index++];
    }
}

// Concrete Collection
public class ArrayCollection<T> implements Collection<T> {
    private T[] items;
    
    public ArrayCollection(T[] items) {
        this.items = items;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(items);
    }
}

// Usage
Integer[] numbers = {1, 2, 3, 4, 5};
Collection<Integer> collection = new ArrayCollection<>(numbers);
Iterator<Integer> iterator = collection.iterator();

while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
```

### Real-World Examples

- **Java Collections**: List, Set, Map iterators
- **Database Cursors**: Row-by-row result set traversal
- **File Systems**: Directory traversal
- **Tree Traversal**: Different traversal strategies
- **Menu Navigation**: Menu item iteration
- **Search Results**: Result pagination

### Implementation Variations

**1. List Iterator (Bidirectional)**
```java
public interface ListIterator<T> extends Iterator<T> {
    boolean hasPrevious();
    T previous();
    void add(T element);
    void remove();
}
```

**2. Lazy Iterator (On-Demand Elements)**
```java
public class LazyIterator<T> implements Iterator<T> {
    private Supplier<T> supplier;
    private int remaining;
    
    @Override
    public boolean hasNext() {
        return remaining > 0;
    }
    
    @Override
    public T next() {
        remaining--;
        return supplier.get();
    }
}
```

### When to Use

- Need to access collection elements sequentially
- Want to hide collection internal structure
- Support multiple concurrent traversals
- Provide uniform access to different collections
- Implement lazy evaluation

---

## Mediator Pattern

### Concept

The Mediator pattern defines an object that encapsulates how a set of objects interact. It promotes loose coupling by keeping objects from referring to each other explicitly, instead making them communicate through a mediator object.

### Problem Solved

- Objects have complex interdependencies
- Direct communication creates tight coupling
- Many objects need to coordinate
- Communication logic is complex
- Want to reuse objects with minimal dependencies

### Implementation Approach

```java
// Mediator interface
public interface ChatRoomMediator {
    void sendMessage(String message, User sender, User receiver);
    void registerUser(User user);
}

// Concrete Mediator
public class ChatRoom implements ChatRoomMediator {
    private List<User> users = new ArrayList<>();
    
    @Override
    public void registerUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " joined chat room");
    }
    
    @Override
    public void sendMessage(String message, User sender, User receiver) {
        System.out.println(sender.getName() + " -> " + receiver.getName() + 
            ": " + message);
    }
}

// Colleague
public class User {
    private String name;
    private ChatRoomMediator mediator;
    
    public User(String name, ChatRoomMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.registerUser(this);
    }
    
    public void send(String message, User receiver) {
        mediator.sendMessage(message, this, receiver);
    }
    
    public String getName() {
        return name;
    }
}

// Usage
ChatRoomMediator chatRoom = new ChatRoom();
User user1 = new User("Alice", chatRoom);
User user2 = new User("Bob", chatRoom);

user1.send("Hello Bob", user2);
user2.send("Hi Alice", user1);
```

### Real-World Examples

- **Air Traffic Control**: Coordinating airplane movements
- **GUI Dialog Boxes**: Button coordination
- **Chat Applications**: Message routing between users
- **Game Lobbies**: Player coordination
- **Workflow Engines**: Process coordination

### Benefits vs. Drawbacks

**Benefits:**
- Reduces coupling between colleagues
- Centralizes complex communication
- Easy to understand interaction patterns
- Reusable colleagues

**Drawbacks:**
- Mediator can become large ("God Object")
- Mediator becomes single point of failure
- Testing complexity increases
- Performance overhead

### When to Use

- Objects have complex communication patterns
- Multiple objects need to collaborate
- Direct references create tight coupling
- Communication logic should be centralized
- Colleagues need to be independent

---

## Memento Pattern

### Concept

The Memento pattern captures and externalizes an object's internal state without violating encapsulation, allowing the object to be restored to this state later.

### Problem Solved

- Need to save/restore object state
- Undo/redo functionality required
- Save object snapshots
- Transaction rollback
- State history tracking

### Implementation Approach

```java
// Memento (stores state)
public class DocumentMemento {
    private final String content;
    private final int cursorPosition;
    private final LocalDateTime timestamp;
    
    public DocumentMemento(String content, int cursorPosition) {
        this.content = content;
        this.cursorPosition = cursorPosition;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getContent() { return content; }
    public int getCursorPosition() { return cursorPosition; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

// Originator (creates/restores from memento)
public class Document {
    private String content = "";
    private int cursorPosition = 0;
    
    public void write(String text) {
        content += text;
        cursorPosition = content.length();
    }
    
    public DocumentMemento save() {
        return new DocumentMemento(content, cursorPosition);
    }
    
    public void restore(DocumentMemento memento) {
        this.content = memento.getContent();
        this.cursorPosition = memento.getCursorPosition();
    }
    
    public String getContent() { return content; }
}

// Caretaker (manages history)
public class DocumentHistory {
    private Stack<DocumentMemento> history = new Stack<>();
    private Document document;
    
    public DocumentHistory(Document document) {
        this.document = document;
    }
    
    public void save() {
        history.push(document.save());
    }
    
    public void undo() {
        if (!history.isEmpty()) {
            document.restore(history.pop());
        }
    }
}

// Usage
Document doc = new Document();
DocumentHistory history = new DocumentHistory(doc);

doc.write("Hello ");
history.save();

doc.write("World");
System.out.println(doc.getContent()); // Hello World

history.undo();
System.out.println(doc.getContent()); // Hello 
```

### Real-World Examples

- **Text Editors**: Undo/redo functionality
- **Games**: Save points and state restoration
- **Transactions**: Transaction rollback
- **Version Control**: Commit snapshots
- **Database Snapshots**: Point-in-time recovery
- **Application Settings**: Configuration snapshots

### When to Use

- Need to save object state snapshots
- Implement undo/redo functionality
- Provide rollback capabilities
- Create version histories
- Track state changes over time

---

## Observer Pattern

### Concept

The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified automatically.

### Problem Solved

- Object state changes affect multiple dependent objects
- Dependents don't know about each other
- Want to notify multiple objects about state changes
- Loose coupling between subject and observers
- Dynamic observer registration/removal

### Implementation Approach

```java
// Observer interface
public interface Observer {
    void update(String message);
}

// Subject
public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private String state;
    
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(state);
        }
    }
}

// Concrete Observers
public class ConsoleObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Console: " + message);
    }
}

public class FileObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("File: Logged " + message);
    }
}

// Usage
Subject subject = new Subject();
Observer console = new ConsoleObserver();
Observer file = new FileObserver();

subject.attach(console);
subject.attach(file);

subject.setState("New state"); // Notifies all observers
```

### Real-World Examples

- **Event Listeners**: GUI button clicks, keyboard events
- **MVC Architecture**: Model-View updates
- **Stock Market**: Stock price changes
- **News Subscriptions**: Newsletter notifications
- **Reactive Programming**: Observable/Subject frameworks
- **Real-Time Systems**: Data change notifications

### Benefits vs. Drawbacks

**Benefits:**
- Loose coupling between subject and observers
- Support dynamic relationships
- Broadcast communication
- Easy to add/remove observers

**Drawbacks:**
- Observers notified in unpredictable order
- Memory leaks if observers not unsubscribed
- Performance with many observers
- Difficult debugging (invisible dependencies)

### When to Use

- Multiple objects need to react to state changes
- Object doesn't know how many observers will exist
- Want to decouple subject from dependents
- Event-driven programming needed
- Pub/sub communication pattern

---

## State Pattern

### Concept

The State pattern allows an object to alter its behavior when its internal state changes. The object appears to change its class when its behavior changes.

### Problem Solved

- Object behavior varies based on state
- Multiple state-dependent conditionals
- State transitions are complex
- Want to encapsulate state-specific behavior
- State machines with many states

### Implementation Approach

```java
// State interface
public interface State {
    void handle(Context context);
}

// Concrete States
public class ConcreteStateA implements State {
    @Override
    public void handle(Context context) {
        System.out.println("StateA behavior");
        context.setState(new ConcreteStateB());
    }
}

public class ConcreteStateB implements State {
    @Override
    public void handle(Context context) {
        System.out.println("StateB behavior");
        context.setState(new ConcreteStateA());
    }
}

// Context
public class Context {
    private State state;
    
    public Context(State state) {
        this.state = state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public void request() {
        state.handle(this);
    }
}

// Usage
Context context = new Context(new ConcreteStateA());
context.request(); // StateA behavior, transitions to StateB
context.request(); // StateB behavior, transitions to StateA
```

### Practical Example: Traffic Light

```java
public interface TrafficLightState {
    void next(TrafficLight light);
    void previous(TrafficLight light);
    void display();
}

public class RedLight implements TrafficLightState {
    @Override
    public void next(TrafficLight light) {
        light.setState(new GreenLight());
    }
    
    @Override
    public void previous(TrafficLight light) {
        light.setState(new YellowLight());
    }
    
    @Override
    public void display() {
        System.out.println("Red light - Stop");
    }
}

public class GreenLight implements TrafficLightState {
    @Override
    public void next(TrafficLight light) {
        light.setState(new YellowLight());
    }
    
    @Override
    public void previous(TrafficLight light) {
        light.setState(new RedLight());
    }
    
    @Override
    public void display() {
        System.out.println("Green light - Go");
    }
}

public class TrafficLight {
    private TrafficLightState state;
    
    public TrafficLight() {
        this.state = new RedLight();
    }
    
    public void setState(TrafficLightState state) {
        this.state = state;
    }
    
    public void next() {
        state.next(this);
    }
    
    public void display() {
        state.display();
    }
}
```

### Real-World Examples

- **Traffic Lights**: Red, Yellow, Green states
- **Order Processing**: Order states (Pending, Shipped, Delivered)
- **TCP Connections**: Connection states (Listen, Established, Closed)
- **Media Players**: Playing, Paused, Stopped states
- **Game Characters**: Idle, Running, Jumping states
- **Workflow Engines**: Workflow state transitions

### When to Use

- Object behavior depends on state and changes at runtime
- Large conditional statements based on state
- State-specific code should be in separate classes
- State transitions are well-defined
- Multiple states with complex transitions

---

## Strategy Pattern

### Concept

The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. It allows the algorithm to vary independently from clients that use it.

### Problem Solved

- Multiple algorithms for same problem
- Algorithm selection at runtime
- Conditional logic based on algorithm type
- Want to add new algorithms without modifying existing code
- Different processing strategies needed

### Implementation Approach

```java
// Strategy interface
public interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Strategies
public class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    
    public CreditCardStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via credit card");
    }
}

public class PayPalStrategy implements PaymentStrategy {
    private String email;
    
    public PayPalStrategy(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via PayPal");
    }
}

public class BitcoinStrategy implements PaymentStrategy {
    private String wallet;
    
    public BitcoinStrategy(String wallet) {
        this.wallet = wallet;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via Bitcoin");
    }
}

// Context
public class ShoppingCart {
    private PaymentStrategy strategy;
    private double total;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void checkout(double amount) {
        strategy.pay(amount);
    }
}

// Usage
ShoppingCart cart = new ShoppingCart();

cart.setPaymentStrategy(new CreditCardStrategy("1234-5678-9012-3456"));
cart.checkout(100.0);

cart.setPaymentStrategy(new PayPalStrategy("user@email.com"));
cart.checkout(50.0);

cart.setPaymentStrategy(new BitcoinStrategy("1A1z7agoat..."));
cart.checkout(30.0);
```

### Real-World Examples

- **Sorting Algorithms**: QuickSort, MergeSort, BubbleSort
- **Payment Methods**: Credit card, PayPal, Bitcoin
- **Routing Algorithms**: GPS navigation strategies
- **Compression Algorithms**: ZIP, RAR, 7Z
- **Authentication**: OAuth, JWT, Basic Auth
- **Data Validation**: Different validation rules

### Key Difference from State Pattern

| Aspect | Strategy | State |
|--------|----------|-------|
| **Purpose** | Select algorithm at runtime | Change behavior based on state |
| **Who Chooses** | Client selects strategy | Context changes state |
| **State Transitions** | Usually don't occur | Required and defined |
| **Strategy Changes** | Independent strategies | State-dependent transitions |

### When to Use

- Multiple algorithms for same task
- Algorithm selection at runtime
- Avoid conditional statements for algorithms
- Algorithms are interchangeable
- Client chooses specific algorithm

---

## Template Method Pattern

### Concept

The Template Method pattern defines the skeleton of an algorithm in a base class, deferring some steps to subclasses. It lets subclasses redefine certain steps without changing the algorithm's structure.

### Problem Solved

- Algorithm has multiple variants with common structure
- Code duplication across similar algorithms
- Want to define algorithm skeleton once
- Allow variation in specific steps
- Ensure algorithm steps execute in defined order

### Implementation Approach

```java
// Abstract Template
public abstract class DataProcessor {
    // Template method - defines algorithm structure
    public final void processData(String data) {
        readData(data);
        processData();
        saveData();
        notifyCompletion();
    }
    
    protected abstract void readData(String data);
    protected abstract void processData();
    protected abstract void saveData();
    
    protected void notifyCompletion() {
        System.out.println("Processing completed");
    }
}

// Concrete Implementations
public class CSVProcessor extends DataProcessor {
    @Override
    protected void readData(String data) {
        System.out.println("Reading CSV file: " + data);
    }
    
    @Override
    protected void processData() {
        System.out.println("Processing CSV data");
    }
    
    @Override
    protected void saveData() {
        System.out.println("Saving processed CSV");
    }
}

public class XMLProcessor extends DataProcessor {
    @Override
    protected void readData(String data) {
        System.out.println("Reading XML file: " + data);
    }
    
    @Override
    protected void processData() {
        System.out.println("Processing XML data");
    }
    
    @Override
    protected void saveData() {
        System.out.println("Saving processed XML");
    }
}

// Usage
DataProcessor processor = new CSVProcessor();
processor.processData("data.csv");

processor = new XMLProcessor();
processor.processData("data.xml");
```

### Real-World Examples

- **Web Requests**: HTTP request processing steps
- **Data Parsing**: File parsing algorithms
- **Report Generation**: Report creation steps
- **Game Loops**: Game update and render cycles
- **Database Queries**: Query execution steps
- **Framework Hooks**: Framework lifecycle methods

### When to Use

- Multiple algorithms with common structure
- Subclasses should only change specific steps
- Code duplication needs elimination
- Algorithm step order is important
- Want to prevent algorithm structure alteration

---

## Visitor Pattern

### Concept

The Visitor pattern represents an operation to be performed on the elements of an object structure. It lets you define a new operation without changing the classes of the elements on which it operates.

### Problem Solved

- Need to perform operations on complex object structures
- Many different operations on same structure
- Want to avoid modifying element classes
- Operations are unrelated to element's primary responsibility
- Need to add new operations frequently

### Implementation Approach

```java
// Element interface
public interface Element {
    void accept(Visitor visitor);
}

// Concrete Elements
public class Book implements Element {
    private String title;
    private double price;
    
    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
    public String getTitle() { return title; }
    public double getPrice() { return price; }
}

public class Magazine implements Element {
    private String title;
    private double price;
    
    public Magazine(String title, double price) {
        this.title = title;
        this.price = price;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
    public String getTitle() { return title; }
    public double getPrice() { return price; }
}

// Visitor interface
public interface Visitor {
    void visit(Book book);
    void visit(Magazine magazine);
}

// Concrete Visitors
public class PriceCalculatorVisitor implements Visitor {
    private double totalPrice = 0;
    
    @Override
    public void visit(Book book) {
        totalPrice += book.getPrice();
    }
    
    @Override
    public void visit(Magazine magazine) {
        totalPrice += magazine.getPrice() * 0.7; // Discount for magazines
    }
    
    public double getTotalPrice() { return totalPrice; }
}

public class DisplayVisitor implements Visitor {
    @Override
    public void visit(Book book) {
        System.out.println("Book: " + book.getTitle() + " - $" + book.getPrice());
    }
    
    @Override
    public void visit(Magazine magazine) {
        System.out.println("Magazine: " + magazine.getTitle() + " - $" + magazine.getPrice());
    }
}

// Usage
List<Element> items = new ArrayList<>();
items.add(new Book("Java Design Patterns", 45.00));
items.add(new Magazine("Tech Weekly", 10.00));
items.add(new Book("Clean Code", 35.00));

// Calculate total price
PriceCalculatorVisitor priceVisitor = new PriceCalculatorVisitor();
for (Element item : items) {
    item.accept(priceVisitor);
}
System.out.println("Total: $" + priceVisitor.getTotalPrice());

// Display items
DisplayVisitor displayVisitor = new DisplayVisitor();
for (Element item : items) {
    item.accept(displayVisitor);
}
```

### Real-World Examples

- **Compiler Optimization**: AST traversal and optimization
- **Tax Calculations**: Computing tax on different item types
- **Report Generation**: Generating different report formats
- **File System Operations**: File/folder operations
- **Object Serialization**: Converting objects to different formats
- **UI Theme Application**: Applying themes to different components

### When to Use

- Need to perform operations on complex object structures
- Many different operations on same structure
- Object classes shouldn't change for new operations
- Operations are temporary or specialized
- Adding new operations more frequent than adding new elements

---

## Chain of Responsibility Pattern

### Concept

The Chain of Responsibility pattern avoids coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. It chains the handlers together and passes the request along the chain.

### Problem Solved

- Multiple objects might handle a request
- Handler not known in advance
- Want to avoid direct sender-to-receiver coupling
- Multiple handlers with different responsibilities
- Request processing with fallback handlers

### Implementation Approach

```java
// Handler interface
public abstract class Handler {
    protected Handler successor;
    
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
    
    public abstract void handleRequest(Request request);
}

// Request class
public class Request {
    private int priority;
    private String content;
    
    public Request(int priority, String content) {
        this.priority = priority;
        this.content = content;
    }
    
    public int getPriority() { return priority; }
    public String getContent() { return content; }
}

// Concrete Handlers
public class LevelOneHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        if (request.getPriority() <= 1) {
            System.out.println("Level 1 handled: " + request.getContent());
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}

public class LevelTwoHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        if (request.getPriority() <= 2) {
            System.out.println("Level 2 handled: " + request.getContent());
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}

public class LevelThreeHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        System.out.println("Level 3 handled: " + request.getContent());
    }
}

// Usage
Handler level1 = new LevelOneHandler();
Handler level2 = new LevelTwoHandler();
Handler level3 = new LevelThreeHandler();

level1.setSuccessor(level2);
level2.setSuccessor(level3);

level1.handleRequest(new Request(1, "Low priority"));   // Level 1
level1.handleRequest(new Request(2, "Medium priority")); // Level 2
level1.handleRequest(new Request(3, "High priority"));   // Level 3
```

### Real-World Examples

- **Exception Handling**: Exception propagation in call stacks
- **Logger Levels**: DEBUG, INFO, WARN, ERROR handling
- **Approval Workflows**: Multi-level approval chains
- **Event Handling**: Event propagation in UI frameworks
- **Request Filtering**: HTTP middleware chains
- **Customer Support**: Support tier escalation

### When to Use

- Multiple objects might handle request
- Handler determined at runtime
- Avoid explicit sender-to-receiver coupling
- Chain of handlers needed
- Want fallback or escalation mechanism

---

## Interpreter Pattern

### Concept

The Interpreter pattern defines a representation for a grammar and an interpreter to interpret sentences in the language. It's used to build interpreters for domain-specific languages.

### Problem Solved

- Need to interpret a language or notation
- Grammar is relatively simple
- Language changes frequently
- Want to encapsulate grammar rules
- Execute expressions in a domain language

### Implementation Approach

```java
// Abstract Expression
public abstract class Expression {
    public abstract int interpret(Context context);
}

// Terminal Expression
public class Number extends Expression {
    private int number;
    
    public Number(int number) {
        this.number = number;
    }
    
    @Override
    public int interpret(Context context) {
        return number;
    }
}

// Non-terminal Expressions
public class Add extends Expression {
    private Expression left;
    private Expression right;
    
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public int interpret(Context context) {
        return left.interpret(context) + right.interpret(context);
    }
}

public class Subtract extends Expression {
    private Expression left;
    private Expression right;
    
    public Subtract(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public int interpret(Context context) {
        return left.interpret(context) - right.interpret(context);
    }
}

// Context
public class Context {
    // Context data if needed
}

// Usage - Parse and interpret: 5 + 3 - 2
Expression expression = new Subtract(
    new Add(
        new Number(5),
        new Number(3)
    ),
    new Number(2)
);

Context context = new Context();
int result = expression.interpret(context); // 6
System.out.println("Result: " + result);
```

### Real-World Examples

- **SQL Parsers**: Query interpretation
- **Regular Expressions**: Pattern matching
- **Configuration Files**: Config syntax interpretation
- **Domain-Specific Languages**: Custom language execution
- **Mathematical Expression Evaluators**: Formula parsing and evaluation
- **Template Engines**: Template syntax interpretation

### When to Use

- Need to interpret a language or notation
- Grammar is relatively simple and stable
- Language needs frequent modifications
- Performance is not critical
- Want to separate grammar from interpretation

---

## Behavioral Patterns Selection Guide

| Pattern | Purpose | Key Benefit | Complexity |
|---------|---------|---|---|
| **Command** | Encapsulate requests | Undo/redo, queuing | Medium |
| **Iterator** | Sequential access | Hide structure | Low |
| **Mediator** | Object communication | Reduced coupling | Medium |
| **Memento** | State capture | Undo/redo, snapshots | Medium |
| **Observer** | Notify dependents | Event handling | Low |
| **State** | Behavior by state | Clean state handling | Medium |
| **Strategy** | Algorithm selection | Algorithm variation | Low |
| **Template Method** | Algorithm skeleton | Code reuse | Low |
| **Visitor** | Add operations | Extensibility | High |
| **Chain of Responsibility** | Request delegation | Handler flexibility | Medium |
| **Interpreter** | Grammar interpretation | DSL support | High |

Choose behavioral patterns based on how objects need to communicate and collaborate to achieve your goals.

---

**References**:
- Gang of Four: "Design Patterns"
- Effective Java by Joshua Bloch
- Head First Design Patterns
- Java Design Pattern implementations
