# Lambda Expressions in Java 8+

## Table of Contents
1. [Introduction](#introduction)
2. [Lambda Expression Syntax](#lambda-expression-syntax)
3. [Functional Interfaces](#functional-interfaces)
4. [Parameter Types](#parameter-types)
5. [Method References](#method-references)
6. [Practical Use Cases](#practical-use-cases)
7. [Best Practices](#best-practices)

## Introduction

Lambda expressions are one of the most significant features introduced in Java 8. They provide a concise and functional programming approach to writing code, enabling you to pass functions as method arguments and write cleaner, more readable code. Lambda expressions allow you to treat functions as first-class objects in Java, bringing functional programming paradigms to the language.

### What is a Lambda Expression?

A lambda expression is a short block of code that takes in parameters and returns a value. It's essentially an anonymous function—a function without a name that can be used immediately in the code.

**Key Benefits:**
- Cleaner and more concise syntax
- Enables functional programming in Java
- Reduces boilerplate code significantly
- Improves readability in certain contexts
- Facilitates parallel processing and stream operations

## Lambda Expression Syntax

### Basic Syntax

```java
(parameters) -> { body }
```

### Components

1. **Parameters**: Input values (can be empty)
2. **Arrow Token (->)**: Separates parameters from body
3. **Body**: The code to execute (can be single expression or multiple statements)

### Syntax Variations

```java
// No parameters
() -> System.out.println("Hello");

// Single parameter (parentheses optional)
x -> x * 2
(x) -> x * 2

// Multiple parameters
(x, y) -> x + y

// Multiple statements (curly braces required)
(x, y) -> {
    int sum = x + y;
    return sum;
}

// Return statement
(x) -> { return x * 2; }

// Single expression (return statement and braces optional)
(x) -> x * 2
```

## Functional Interfaces

A functional interface is an interface with exactly one abstract method. Lambda expressions can only be used to implement functional interfaces.

### Key Rules

1. Must have exactly one abstract method
2. Can have multiple default methods
3. Can have static methods
4. Can override methods from Object class (equals, hashCode, toString)

### @FunctionalInterface Annotation

This annotation marks an interface as functional and causes compilation errors if it violates the contract:

```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
}
```

## Parameter Types

Lambda expressions support various parameter type approaches:

### Type Inference

The compiler can infer parameter types from context:

```java
// Type inference - types are inferred
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(name -> System.out.println(name));
```

### Explicit Type Declaration

```java
// Explicit types - types are explicitly specified
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach((String name) -> System.out.println(name));
```

### Mixed Types

When using multiple parameters, consistency is important:

```java
// All explicit or use type inference
(String a, String b) -> a.concat(b)  // Both explicit
(a, b) -> a.concat(b)                // Both inferred
// (String a, b) -> a.concat(b)      // ERROR - inconsistent
```

## Method References

Method references provide a more readable way to refer to methods. They're a shorthand for lambda expressions that only call an existing method.

### Types of Method References

### 1. Static Method References

Syntax: `ClassName::staticMethodName`

```java
// Instead of: x -> Integer.parseInt(x)
Function<String, Integer> converter = Integer::parseInt;
```

### 2. Instance Method References

Syntax: `object::instanceMethodName`

```java
// Instead of: str -> str.toUpperCase()
String str = "hello";
Function<String, String> converter = String::toUpperCase;
```

### 3. Class Instance Method References

Syntax: `ClassName::instanceMethodName`

```java
// Instead of: (str) -> str.toUpperCase()
Function<String, String> converter = String::toUpperCase;
```

### 4. Constructor References

Syntax: `ClassName::new`

```java
// Instead of: () -> new ArrayList<>()
Supplier<ArrayList> supplier = ArrayList::new;

// With parameters
Function<String, Integer> parseInt = Integer::new;
```

## Practical Use Cases

### Use Case 1: Sorting Collections

```java
// Traditional approach with anonymous class
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});

// With lambda expression
Collections.sort(names, (a, b) -> a.compareTo(b));

// With method reference
Collections.sort(names, String::compareTo);
```

### Use Case 2: Event Handling

```java
// UI Button Click Handler
button.setOnClickListener(event -> {
    System.out.println("Button clicked: " + event.getTimestamp());
    updateUI();
});
```

### Use Case 3: Stream Operations

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

// Filter and map
List<Integer> evenSquares = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .collect(Collectors.toList());

// Using method references
List<String> words = Arrays.asList("Java", "Lambda", "Stream");
words.stream()
    .forEach(System.out::println);
```

### Use Case 4: Thread Creation

```java
// Traditional approach
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Running in thread");
    }
}).start();

// With lambda expression
new Thread(() -> System.out.println("Running in thread")).start();
```

### Use Case 5: Custom Functional Interfaces

```java
@FunctionalInterface
interface DataProcessor {
    String process(String input);
}

// Usage
DataProcessor upperCase = s -> s.toUpperCase();
DataProcessor reverse = s -> new StringBuilder(s).reverse().toString();
DataProcessor addPrefix = s -> "PREFIX_" + s;

System.out.println(upperCase.process("hello"));      // HELLO
System.out.println(reverse.process("hello"));        // olleh
System.out.println(addPrefix.process("hello"));      // PREFIX_hello
```

## Best Practices

### 1. Use Lambda When Functional Interface is Appropriate

```java
// Good: Lambda for functional interface
list.forEach(item -> System.out.println(item));

// Not recommended: Complex business logic in lambda
list.forEach(item -> {
    validateItem(item);
    processItem(item);
    logItem(item);
    notifySubscribers(item);
    updateDatabase(item);
});
```

### 2. Keep Lambda Expressions Simple and Readable

```java
// Good: Simple and clear
numbers.stream()
    .filter(n -> n > 0)
    .collect(Collectors.toList());

// Avoid: Overly complex lambda
numbers.stream()
    .filter(n -> n > 0 && (n % 2 == 0 || n % 3 == 0) && 
                  Math.sqrt(n) > 5 && isPrime(n))
    .collect(Collectors.toList());
```

### 3. Use Method References When Possible

```java
// Good: Method reference is clearer
names.forEach(System.out::println);

// Less readable: Lambda doing the same thing
names.forEach(name -> System.out.println(name));
```

### 4. Leverage Type Inference

```java
// Good: Let compiler infer types
(x, y) -> x + y

// Unnecessary: Explicit types when not needed
(Integer x, Integer y) -> x + y
```

### 5. Avoid Side Effects in Lambdas

```java
// Good: Pure function, no side effects
int sum = numbers.stream()
    .map(n -> n * 2)
    .reduce(0, Integer::sum);

// Avoid: Modifying external state
List<Integer> doubled = new ArrayList<>();
numbers.forEach(n -> doubled.add(n * 2));  // Side effect!
```

### 6. Use Descriptive Variable Names in Parameters

```java
// Good: Clear naming
employees.stream()
    .filter(emp -> emp.getSalary() > 50000)
    .forEach(System.out::println);

// Less clear: Cryptic names
employees.stream()
    .filter(e -> e.getSalary() > 50000)
    .forEach(System.out::println);
```

### 7. Consider Readability vs Conciseness

```java
// Very concise but harder to read
Function<Integer, Integer> f = x -> x * x + 2 * x + 1;

// More readable with explanation
Function<Integer, Integer> quadratic = x -> {
    // Calculate quadratic: x^2 + 2x + 1 = (x+1)^2
    return x * x + 2 * x + 1;
};
```

## Summary

Lambda expressions have revolutionized Java programming by introducing functional programming concepts. They make code more concise, readable, and maintainable when used appropriately. Combined with streams, method references, and functional interfaces, they provide powerful tools for modern Java development.

### Key Takeaways

- **Lambda expressions** enable functional programming in Java
- **Functional interfaces** are required to use lambdas
- **Method references** provide cleaner syntax for specific patterns
- **Type inference** reduces boilerplate but maintain clarity
- **Simplicity and readability** should guide lambda usage
- **Avoid side effects** in lambda expressions for better code quality
