# Functional Interfaces in Java

## Table of Contents
1. [Introduction to Functional Interfaces](#introduction)
2. [What is a Functional Interface?](#what-is-a-functional-interface)
3. [Built-in Functional Interfaces](#built-in-functional-interfaces)
4. [Core Functional Interfaces](#core-functional-interfaces)
5. [Specialized Functional Interfaces](#specialized-functional-interfaces)
6. [Creating Custom Functional Interfaces](#creating-custom-functional-interfaces)
7. [Practical Examples](#practical-examples)
8. [Best Practices](#best-practices)

## Introduction to Functional Interfaces

Functional interfaces are the foundation of lambda expressions and functional programming in Java. Java 8 introduced a comprehensive set of built-in functional interfaces in the `java.util.function` package, eliminating the need to create custom functional interfaces for common operations. These interfaces follow a consistent naming convention and cover most common functional programming patterns.

### Why Functional Interfaces Matter

1. **Lambda Expression Foundation**: Lambda expressions can only be used with functional interfaces
2. **Code Reusability**: Standard interfaces reduce code duplication
3. **Consistency**: Uniform patterns across the codebase
4. **Composability**: Functional interfaces enable method composition
5. **Stream API Integration**: Core to effective stream operations

## What is a Functional Interface?

A functional interface is an interface with exactly one abstract method. It serves as the target type for lambda expressions and method references.

### Requirements

```java
// Valid Functional Interface
@FunctionalInterface
interface SingleMethod {
    void execute();
}

// Valid with multiple default methods
@FunctionalInterface
interface WithDefaults {
    void execute();
    default void defaultMethod() {}
    default String toString() { return ""; }
}

// Valid with static methods
@FunctionalInterface
interface WithStatic {
    void execute();
    static void staticMethod() {}
}

// Invalid - multiple abstract methods
@FunctionalInterface
interface Invalid {  // Compiler error
    void method1();
    void method2();
}
```

## Built-in Functional Interfaces

Java provides a comprehensive set of functional interfaces in the `java.util.function` package:

### 1. Function<T, R>

Represents a function that takes one argument and produces a result.

```java
import java.util.function.Function;

public class FunctionExample {
    public static void main(String[] args) {
        // Lambda expression with Function
        Function<Integer, Integer> square = x -> x * x;
        Function<String, Integer> stringLength = s -> s.length();
        Function<Double, Double> squareRoot = x -> Math.sqrt(x);
        
        System.out.println("Square of 5: " + square.apply(5));           // 25
        System.out.println("Length of 'Hello': " + stringLength.apply("Hello")); // 5
        System.out.println("Square root of 16: " + squareRoot.apply(16.0));      // 4.0
        
        // Function composition
        Function<Integer, Integer> addFive = x -> x + 5;
        Function<Integer, Integer> multiplyByTwo = x -> x * 2;
        
        // Compose: first apply multiplyByTwo, then addFive
        Function<Integer, Integer> composed = addFive.compose(multiplyByTwo);
        System.out.println("Composed (10): " + composed.apply(10)); // (10*2)+5 = 25
        
        // AndThen: first apply addFive, then multiplyByTwo
        Function<Integer, Integer> andThen = addFive.andThen(multiplyByTwo);
        System.out.println("AndThen (10): " + andThen.apply(10));  // (10+5)*2 = 30
        
        // BiFunction - takes two arguments
        java.util.function.BiFunction<Integer, Integer, Integer> add = 
            (a, b) -> a + b;
        System.out.println("3 + 5 = " + add.apply(3, 5)); // 8
    }
}
```

### 2. Predicate<T>

A function that returns a boolean value. Used for filtering and conditions.

```java
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.List;

public class PredicateExample {
    public static void main(String[] args) {
        // Simple predicate
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<String> isLongerThanFive = s -> s.length() > 5;
        
        System.out.println("Is 4 even? " + isEven.test(4));    // true
        System.out.println("Is 7 even? " + isEven.test(7));    // false
        System.out.println("Is 'Hello' longer than 5? " + 
            isLongerThanFive.test("Hello"));                   // false
        
        // Predicate composition
        Predicate<Integer> greaterThanZero = x -> x > 0;
        Predicate<Integer> lessThanTen = x -> x < 10;
        
        // AND composition
        Predicate<Integer> between0and10 = 
            greaterThanZero.and(lessThanTen);
        System.out.println("Is 5 between 0 and 10? " + 
            between0and10.test(5));                             // true
        
        // OR composition
        Predicate<Integer> outside0to10 = 
            greaterThanZero.or(lessThanTen);
        
        // NOT composition
        Predicate<Integer> notEven = isEven.negate();
        System.out.println("Is 7 not even? " + notEven.test(7));// true
        
        // Filtering with predicates
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.stream()
            .filter(isEven)
            .forEach(System.out::println);                      // 2, 4, 6, 8, 10
        
        // BiPredicate - two arguments
        java.util.function.BiPredicate<Integer, Integer> isGreater = 
            (a, b) -> a > b;
        System.out.println("Is 10 > 5? " + isGreater.test(10, 5)); // true
    }
}
```

### 3. Consumer<T>

A function that accepts an argument and returns no result. Used for side effects.

```java
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;

public class ConsumerExample {
    public static void main(String[] args) {
        // Simple consumer
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
        Consumer<Integer> printSquare = x -> System.out.println("Square: " + (x * x));
        
        printUpperCase.accept("hello");    // HELLO
        printSquare.accept(5);              // Square: 25
        
        // Consumer chaining with andThen
        Consumer<String> printLowerCase = s -> System.out.println(s.toLowerCase());
        Consumer<String> printLength = s -> System.out.println("Length: " + s.length());
        
        Consumer<String> chained = printLowerCase.andThen(printLength);
        chained.accept("HELLO");            // hello, Length: 5
        
        // Using with forEach
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.forEach(x -> System.out.println("Value: " + x));
        
        // BiConsumer - two arguments
        java.util.function.BiConsumer<String, String> printPair = 
            (key, value) -> System.out.println(key + " : " + value);
        printPair.accept("Name", "John");  // Name : John
        
        // Practical example: processing list items
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
        Consumer<String> processFruit = fruit -> {
            System.out.println("Processing: " + fruit);
            System.out.println("Length: " + fruit.length());
        };
        fruits.forEach(processFruit);
    }
}
```

### 4. Supplier<T>

A function that takes no arguments and returns a result. Used for lazy evaluation and object creation.

```java
import java.util.function.Supplier;
import java.util.Arrays;
import java.util.List;

public class SupplierExample {
    public static void main(String[] args) {
        // Simple supplier
        Supplier<String> greeting = () -> "Hello, World!";
        Supplier<Double> randomNumber = () -> Math.random();
        Supplier<Integer> counter = new Counter()::getCount;
        
        System.out.println(greeting.get());        // Hello, World!
        System.out.println(randomNumber.get());    // Random double
        System.out.println(counter.get());         // 0
        
        // Lazy initialization
        class Database {
            private String connection;
            
            public String getConnection(Supplier<String> supplier) {
                if (connection == null) {
                    connection = supplier.get();  // Only called when needed
                }
                return connection;
            }
        }
        
        Database db = new Database();
        System.out.println(db.getConnection(() -> "jdbc:mysql://localhost:3306"));
        
        // Object creation with suppliers
        Supplier<StringBuilder> stringBuilderSupplier = StringBuilder::new;
        StringBuilder sb = stringBuilderSupplier.get();
        sb.append("Hello");
        System.out.println(sb.toString());
        
        // Supplier for immutable defaults
        Supplier<List<String>> emptyList = () -> Arrays.asList();
        System.out.println("Empty list: " + emptyList.get());
    }
    
    static class Counter {
        private int count = 0;
        public int getCount() { return count++; }
    }
}
```

### 5. BiFunction<T, U, R>

A function that takes two arguments and produces a result.

```java
import java.util.function.BiFunction;

public class BiFunctionExample {
    public static void main(String[] args) {
        // Simple bifunction
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<String, String, String> combine = (s1, s2) -> s1 + " " + s2;
        BiFunction<Double, Double, Double> multiply = (x, y) -> x * y;
        
        System.out.println("5 + 3 = " + add.apply(5, 3));           // 8
        System.out.println(combine.apply("Hello", "World"));        // Hello World
        System.out.println("2.5 * 4.0 = " + multiply.apply(2.5, 4.0)); // 10.0
        
        // BiFunction with andThen
        BiFunction<Integer, Integer, Integer> subtract = (a, b) -> a - b;
        BiFunction<Integer, Integer, Integer> result = 
            add.andThen(x -> x * 2);
        System.out.println("(5 + 3) * 2 = " + result.apply(5, 3)); // 16
        
        // Practical example: user authentication
        BiFunction<String, String, Boolean> authenticate = (username, password) -> {
            return username.equals("admin") && password.equals("password123");
        };
        System.out.println("Auth admin/password123: " + 
            authenticate.apply("admin", "password123")); // true
    }
}
```

## Specialized Functional Interfaces

### Primitive Type Variants

For performance optimization with primitive types:

```java
import java.util.function.*;

public class PrimitiveVariantExample {
    public static void main(String[] args) {
        // IntFunction - takes int, returns R
        IntFunction<String> intToString = i -> "Number: " + i;
        System.out.println(intToString.apply(42));  // Number: 42
        
        // IntPredicate - tests an int
        IntPredicate isEven = i -> i % 2 == 0;
        System.out.println("4 is even: " + isEven.test(4));  // true
        
        // IntConsumer - accepts int
        IntConsumer printInt = i -> System.out.println("Int: " + i);
        printInt.accept(10);
        
        // IntSupplier - returns int
        IntSupplier randomInt = () -> (int)(Math.random() * 100);
        System.out.println("Random int: " + randomInt.getAsInt());
        
        // IntToDoubleFunction
        IntToDoubleFunction intToDouble = i -> i * 3.14;
        System.out.println("5 * 3.14 = " + intToDouble.applyAsDouble(5)); // 15.7
        
        // Similar variants exist for Long, Double
        LongPredicate isNegative = l -> l < 0;
        System.out.println("-5 is negative: " + isNegative.test(-5)); // true
        
        DoublePredicate isGreaterThanOne = d -> d > 1.0;
        System.out.println("2.5 > 1.0: " + isGreaterThanOne.test(2.5)); // true
    }
}
```

## Creating Custom Functional Interfaces

When built-in interfaces don't fit your needs:

```java
public class CustomFunctionalInterfaceExample {
    
    // Custom functional interfaces for specific domains
    @FunctionalInterface
    interface DataValidator {
        boolean validate(String data);
    }
    
    @FunctionalInterface
    interface DataTransformer {
        String transform(String data);
    }
    
    @FunctionalInterface
    interface Logger {
        void log(String level, String message);
    }
    
    // Processor that uses multiple functional interfaces
    @FunctionalInterface
    interface DataProcessor {
        String process(String input);
        
        // Default method for chaining
        default DataProcessor chain(DataProcessor other) {
            return input -> other.process(this.process(input));
        }
    }
    
    public static void main(String[] args) {
        // Implementations using lambdas
        DataValidator emailValidator = email -> 
            email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        
        DataTransformer toUpperCase = String::toUpperCase;
        
        Logger consoleLogger = (level, message) -> 
            System.out.println("[" + level + "] " + message);
        
        // Usage
        System.out.println("Valid email: " + 
            emailValidator.validate("test@example.com")); // true
        System.out.println("Transformed: " + 
            toUpperCase.transform("hello"));              // HELLO
        consoleLogger.log("INFO", "Application started"); // [INFO] Application started
        
        // Processor chaining
        DataProcessor trim = String::trim;
        DataProcessor upper = String::toUpperCase;
        DataProcessor addPrefix = s -> "PREFIX_" + s;
        
        DataProcessor pipeline = trim.chain(upper).chain(addPrefix);
        System.out.println(pipeline.process("  hello world  ")); 
        // PREFIX_HELLO WORLD
    }
}
```

## Practical Examples

### Example 1: Stream Processing Pipeline

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.*;

public class StreamProcessingExample {
    
    static class Product {
        private String name;
        private double price;
        private int quantity;
        
        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        
        @Override
        public String toString() {
            return String.format("%s - $%.2f (qty: %d)", name, price, quantity);
        }
    }
    
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 999.99, 5),
            new Product("Mouse", 29.99, 50),
            new Product("Keyboard", 79.99, 20),
            new Product("Monitor", 299.99, 10),
            new Product("Headphones", 149.99, 15)
        );
        
        // Predicates for filtering
        Predicate<Product> inStock = p -> p.getQuantity() > 0;
        Predicate<Product> expensive = p -> p.getPrice() > 100;
        Predicate<Product> affordable = p -> p.getPrice() < 100;
        
        // Functions for transformation
        Function<Product, Double> totalValue = p -> p.getPrice() * p.getQuantity();
        Function<Product, String> formatProduct = 
            p -> String.format("%s ($%.2f)", p.getName(), p.getPrice());
        
        // Consumers for processing
        Consumer<Product> printProduct = System.out::println;
        Consumer<Double> printTotal = total -> System.out.printf("Total Value: $%.2f%n", total);
        
        System.out.println("=== All Products ===");
        products.forEach(printProduct);
        
        System.out.println("\n=== Expensive In-Stock Products ===");
        products.stream()
            .filter(inStock.and(expensive))
            .forEach(printProduct);
        
        System.out.println("\n=== Formatted Affordable Products ===");
        products.stream()
            .filter(affordable)
            .map(formatProduct)
            .forEach(System.out::println);
        
        System.out.println("\n=== Total Inventory Value ===");
        double totalInventoryValue = products.stream()
            .filter(inStock)
            .map(totalValue)
            .reduce(0.0, Double::sum);
        printTotal.accept(totalInventoryValue);
    }
}
```

### Example 2: Event Handling System

```java
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class EventHandlingExample {
    
    @FunctionalInterface
    interface EventListener {
        void onEvent(Event event);
    }
    
    class Event {
        private String type;
        private String message;
        private long timestamp;
        
        public Event(String type, String message) {
            this.type = type;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getType() { return type; }
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }
    
    class EventEmitter {
        private List<EventListener> listeners = new ArrayList<>();
        
        public void on(String eventType, Consumer<Event> handler) {
            listeners.add(event -> {
                if (event.getType().equals(eventType)) {
                    handler.accept(event);
                }
            });
        }
        
        public void emit(Event event) {
            listeners.forEach(listener -> listener.onEvent(event));
        }
    }
    
    public static void main(String[] args) {
        EventHandlingExample example = new EventHandlingExample();
        EventEmitter emitter = example.new EventEmitter();
        
        // Register event handlers using lambdas
        emitter.on("UserLogin", event -> {
            System.out.println("LOGIN: " + event.getMessage() + 
                " at " + event.getTimestamp());
        });
        
        emitter.on("UserLogout", event -> {
            System.out.println("LOGOUT: " + event.getMessage());
        });
        
        emitter.on("Error", event -> {
            System.err.println("ERROR: " + event.getMessage());
        });
        
        // Emit events
        emitter.emit(example.new Event("UserLogin", "User: john_doe"));
        emitter.emit(example.new Event("UserLogout", "User: john_doe"));
        emitter.emit(example.new Event("Error", "Database connection failed"));
    }
}
```

## Best Practices

### 1. Choose the Right Interface

```java
// Good: Use specific functional interface for clarity
Predicate<Integer> isPositive = n -> n > 0;
Function<String, Integer> parseInteger = Integer::parseInt;
Consumer<String> printMessage = System.out::println;

// Avoid: Using generic Function when specific interface exists
Function<Integer, Boolean> checkPositive = n -> n > 0;  // Use Predicate instead
```

### 2. Leverage Composition

```java
// Good: Compose predicates for complex conditions
Predicate<Integer> isEven = n -> n % 2 == 0;
Predicate<Integer> isPositive = n -> n > 0;
Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);

// Compose functions
Function<Integer, Integer> double_ = n -> n * 2;
Function<Integer, Integer> addTen = n -> n + 10;
Function<Integer, Integer> doubleThenAddTen = double_.andThen(addTen);
```

### 3. Use Method References When Appropriate

```java
// Good: Clear intent with method references
list.forEach(System.out::println);
numbers.stream().map(Integer::toString);
suppliers.forEach(Supplier::get);

// Less clear: Lambda doing what method reference can do
list.forEach(item -> System.out.println(item));
numbers.stream().map(n -> Integer.toString(n));
```

### 4. Document Custom Functional Interfaces

```java
/**
 * Validates data against specific criteria.
 * Used in data processing pipelines for conditional filtering.
 * 
 * @param <T> the type of data to validate
 */
@FunctionalInterface
interface DataValidator<T> {
    /**
     * Validates the provided data.
     * 
     * @param data the data to validate
     * @return true if valid, false otherwise
     */
    boolean isValid(T data);
}
```

### 5. Avoid Excessive Nesting

```java
// Good: Readable chain
data.stream()
    .filter(isValid)
    .map(transformer)
    .forEach(printer);

// Avoid: Deeply nested lambdas
data.forEach(item -> {
    if (isValid.test(item)) {
        String transformed = transformer.apply(item);
        System.out.println(transformed);
    }
});
```

## Summary

Functional interfaces are central to modern Java programming:

- **Foundation for lambda expressions** and method references
- **Standard library provides comprehensive built-in interfaces** for common patterns
- **Composition capabilities** enable flexible and reusable code
- **Performance optimization** through primitive type variants
- **Clear semantics** with specific interfaces for specific purposes

### Quick Reference

| Interface | Purpose | Method | Use Case |
|-----------|---------|--------|----------|
| `Function<T,R>` | Transform T to R | `apply(T)` | Mapping, transformation |
| `Predicate<T>` | Test condition | `test(T)` | Filtering |
| `Consumer<T>` | Perform action | `accept(T)` | Side effects, iteration |
| `Supplier<T>` | Provide value | `get()` | Lazy initialization |
| `BiFunction<T,U,R>` | Transform two values | `apply(T,U)` | Two-argument operations |
