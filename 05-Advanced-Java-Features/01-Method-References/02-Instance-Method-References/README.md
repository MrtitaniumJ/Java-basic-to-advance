# Instance Method References

## Overview

Instance method references allow you to reference non-static methods of an object instance. They come in two varieties: bound instance method references (where the instance is already known) and unbound instance method references (where the instance is supplied as a parameter). This feature enables elegant functional programming patterns when working with existing object methods.

## Definition

An instance method reference is a reference to a non-static method that belongs to an instance (object) of a class. Bound references refer to methods of a specific instance, while unbound references require the instance to be provided as the first parameter.

## Syntax

### Bound Instance Method Reference
```java
instance::methodName
```

### Unbound Instance Method Reference
```java
ClassName::methodName
```

## Types of Instance Method References

### Bound Instance Method References

A bound instance method reference refers to a method of a specific, known instance.

```java
import java.util.Arrays;
import java.util.List;

public class BoundInstanceMethodReference {
    
    public class Logger {
        private String prefix;
        
        public Logger(String prefix) {
            this.prefix = prefix;
        }
        
        public void log(String message) {
            System.out.println(prefix + ": " + message);
        }
    }
    
    public static void main(String[] args) {
        Logger appLogger = new Logger("[APP]");
        Logger errorLogger = new Logger("[ERROR]");
        
        List<String> messages = Arrays.asList(
            "Application started",
            "Processing request",
            "Request completed"
        );
        
        // Using bound instance method reference
        System.out.println("Application Logs:");
        messages.forEach(appLogger::log);
        
        System.out.println("\nError Logs:");
        messages.stream()
            .filter(msg -> msg.contains("Error") || msg.contains("error"))
            .forEach(errorLogger::log);
        
        // Compare with lambda expression
        System.out.println("\nUsing Lambda (equivalent):");
        messages.forEach(msg -> appLogger.log(msg));
    }
}
```

### Unbound Instance Method References

An unbound instance method reference refers to a method where the instance is provided as the first parameter.

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class UnboundInstanceMethodReference {
    
    public static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public void introduce() {
            System.out.println("Hello, I am " + name + ", age " + age);
        }
        
        public void greet(String greeting) {
            System.out.println(greeting + ", I am " + name);
        }
        
        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }
    
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 28)
        );
        
        // Using unbound instance method reference
        System.out.println("Unbound Method Reference - Introductions:");
        people.forEach(Person::introduce);
        
        // Unbound with parameters
        System.out.println("\nUnbound Method Reference - Greetings:");
        people.stream()
            .peek(person -> {})  // Access person for context
            .forEach(p -> p.greet("Welcome"));
        
        // Compare with lambda expression
        System.out.println("\nEquivalent Lambda Expression:");
        people.forEach(person -> person.introduce());
    }
}
```

## Practical Examples

### Example 1: String Operations with Bound References

```java
import java.util.Arrays;
import java.util.List;

public class StringOperationsExample {
    
    public static class StringProcessor {
        public void printUpperCase(String str) {
            System.out.println(str.toUpperCase());
        }
        
        public void printLength(String str) {
            System.out.println(str + " has length: " + str.length());
        }
        
        public void printReverse(String str) {
            System.out.println(new StringBuilder(str).reverse());
        }
    }
    
    public static void main(String[] args) {
        StringProcessor processor = new StringProcessor();
        List<String> words = Arrays.asList("java", "programming", "method", "references");
        
        System.out.println("Uppercase conversion:");
        words.forEach(processor::printUpperCase);
        
        System.out.println("\nString lengths:");
        words.forEach(processor::printLength);
        
        System.out.println("\nReversed strings:");
        words.forEach(processor::printReverse);
    }
}
```

### Example 2: Collection Operations with Unbound References

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class CollectionOperationsExample {
    
    public static class DataContainer {
        private int value;
        
        public DataContainer(int value) {
            this.value = value;
        }
        
        public void display() {
            System.out.println("Value: " + value);
        }
        
        public void increment() {
            value++;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    public static void main(String[] args) {
        List<DataContainer> containers = Arrays.asList(
            new DataContainer(10),
            new DataContainer(20),
            new DataContainer(30),
            new DataContainer(40)
        );
        
        // Using unbound instance method reference
        System.out.println("Initial Display:");
        containers.forEach(DataContainer::display);
        
        System.out.println("\nIncrementing all values:");
        containers.forEach(DataContainer::increment);
        containers.forEach(DataContainer::display);
        
        // Filter and display
        System.out.println("\nValues greater than 30:");
        containers.stream()
            .filter(c -> c.getValue() > 30)
            .forEach(DataContainer::display);
    }
}
```

### Example 3: Custom Validators with Instance Methods

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ValidatorExample {
    
    public static class EmailValidator {
        public boolean isValid(String email) {
            return email.contains("@") && email.contains(".");
        }
        
        public boolean isNotEmpty(String email) {
            return email != null && !email.trim().isEmpty();
        }
    }
    
    public static void main(String[] args) {
        EmailValidator validator = new EmailValidator();
        List<String> emails = Arrays.asList(
            "john@example.com",
            "invalid.email",
            "alice@company.org",
            "",
            "bob@test.net",
            "charlie@.com"
        );
        
        // Using bound instance method reference for validation
        System.out.println("Valid emails:");
        emails.stream()
            .filter(validator::isValid)
            .filter(validator::isNotEmpty)
            .forEach(System.out::println);
        
        System.out.println("\nInvalid emails:");
        emails.stream()
            .filter(Predicate.not(validator::isValid))
            .forEach(System.out::println);
    }
}
```

### Example 4: Method Chaining with Instance References

```java
import java.util.function.Function;

public class MethodChainingExample {
    
    public static class StringBuilder2 {
        private String value;
        
        public StringBuilder2(String initial) {
            this.value = initial;
        }
        
        public StringBuilder2 toUpperCase() {
            this.value = this.value.toUpperCase();
            return this;
        }
        
        public StringBuilder2 reverse() {
            this.value = new StringBuilder(this.value).reverse().toString();
            return this;
        }
        
        public StringBuilder2 addPrefix(String prefix) {
            this.value = prefix + this.value;
            return this;
        }
        
        public StringBuilder2 addSuffix(String suffix) {
            this.value = this.value + suffix;
            return this;
        }
        
        public String build() {
            return this.value;
        }
    }
    
    public static void main(String[] args) {
        StringBuilder2 builder = new StringBuilder2("hello world");
        
        // Using instance method references in chaining
        String result = builder
            .toUpperCase()
            .reverse()
            .addPrefix(">>> ")
            .addSuffix(" <<<")
            .build();
        
        System.out.println("Result: " + result);
    }
}
```

### Example 5: Event Handling with Instance References

```java
import java.util.ArrayList;
import java.util.List;

public class EventHandlingExample {
    
    public interface EventListener {
        void onEvent(String event);
    }
    
    public static class EventDispatcher {
        private List<EventListener> listeners = new ArrayList<>();
        
        public void subscribe(EventListener listener) {
            listeners.add(listener);
        }
        
        public void dispatch(String event) {
            listeners.forEach(listener -> listener.onEvent(event));
        }
    }
    
    public static class EventHandler {
        public void handleApplicationEvent(String event) {
            System.out.println("[APP EVENT] " + event);
        }
        
        public void handleErrorEvent(String event) {
            System.out.println("[ERROR EVENT] " + event);
        }
        
        public void handleInfoEvent(String event) {
            System.out.println("[INFO EVENT] " + event);
        }
    }
    
    public static void main(String[] args) {
        EventDispatcher dispatcher = new EventDispatcher();
        EventHandler handler = new EventHandler();
        
        // Using bound instance method references for event handling
        dispatcher.subscribe(handler::handleApplicationEvent);
        dispatcher.subscribe(handler::handleErrorEvent);
        dispatcher.subscribe(handler::handleInfoEvent);
        
        // Dispatch events
        dispatcher.dispatch("Application started successfully");
        dispatcher.dispatch("Database connection failed");
        dispatcher.dispatch("Request processed in 150ms");
    }
}
```

## Comparison with Lambda Expressions

### Bound Reference vs Lambda
```java
// Bound instance method reference
list.forEach(logger::log);

// Equivalent lambda expression
list.forEach(msg -> logger.log(msg));
```

### Unbound Reference vs Lambda
```java
// Unbound instance method reference
people.forEach(Person::introduce);

// Equivalent lambda expression
people.forEach(person -> person.introduce());
```

## Use Cases

1. **Event Handling**: Subscribing instance methods as event listeners
2. **Stream Operations**: Processing collections with instance methods
3. **Callbacks**: Passing instance methods as callback functions
4. **Validation**: Applying instance-based validation logic
5. **Data Transformation**: Using instance methods for data mapping
6. **Logging and Monitoring**: Using instance logger methods
7. **Custom Business Logic**: Applying domain-specific instance operations

## Best Practices

1. **Choose Bound Over Unbound**: When you have a specific instance, use bound references
2. **Meaningful Method Names**: Ensure instance methods have clear, descriptive names
3. **Functional Interface Compatibility**: Ensure method signature matches the functional interface
4. **Performance**: Instance method references have the same performance as lambda expressions
5. **Readability**: Use method references when they improve code clarity
6. **Avoid Over-Chaining**: Keep method chains readable and maintainable
7. **Document Instance Methods**: Clearly document methods intended as method references

## Summary

Instance method references provide a clean way to reference methods of specific objects or classes. By understanding both bound and unbound references, you can write more expressive and maintainable Java code, especially when working with streams, callbacks, and event handling patterns.
