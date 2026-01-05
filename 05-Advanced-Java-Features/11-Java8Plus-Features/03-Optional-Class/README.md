# Optional Class - Null Handling in Java

## Overview

The `Optional<T>` class, introduced in Java 8, is a container object used to contain a non-null value. It's part of the `java.util` package and provides a more elegant and expressive way to handle null values compared to traditional null checks. Optional is not intended to be a replacement for all uses of null references but rather a tool for designing better APIs and writing more readable code.

## Why Optional?

Traditional null checking creates code with multiple null checks, leading to:
- **Verbose code**: Multiple if-statements for null checks
- **Runtime errors**: NullPointerException if null references aren't checked
- **Unclear intent**: Hard to determine which values can be null
- **Difficult debugging**: Stack traces don't clearly indicate the problem

Optional provides a functional approach that makes null handling explicit and safer.

## Core Concepts

### Creating Optional Instances

**1. Optional.of(T value)**
- Creates an Optional with a non-null value
- Throws NullPointerException if value is null
- Use when you're certain the value is non-null

**2. Optional.ofNullable(T value)**
- Creates an Optional with a potentially null value
- Returns Optional.empty() if value is null
- Use when value might be null

**3. Optional.empty()**
- Returns an empty Optional container
- Useful for returning "no value" explicitly

### Core Methods

**Checking Presence:**
- `isPresent()`: Returns boolean if value is present
- `isEmpty()`: Returns boolean if value is absent (Java 11+)

**Retrieving Values:**
- `get()`: Returns value if present, throws NoSuchElementException otherwise
- `orElse(T other)`: Returns value if present, otherwise returns other
- `orElseGet(Supplier<T> supplier)`: Returns value if present, otherwise computes from supplier
- `orElseThrow()`: Returns value if present, throws NoSuchElementException otherwise

**Functional Operations:**
- `map(Function<T,U> mapper)`: Transforms value if present
- `flatMap(Function<T,Optional<U>> mapper)`: Chains Optional operations
- `filter(Predicate<T> predicate)`: Filters based on condition

**Conditional Actions:**
- `ifPresent(Consumer<T> action)`: Executes action if value present
- `ifPresentOrElse(Consumer<T> action, Runnable emptyAction)`: Executes action based on presence

## Usage Patterns and Best Practices

### 1. **Use Optional for Return Values**
Return Optional instead of null from methods to make it explicit that a value might not be present. This improves API usability and forces callers to handle the absence case.

### 2. **Avoid Optional.get() Without Checking**
Always check with isPresent() before calling get(), or use safer methods like orElse() or ifPresent().

### 3. **Use Method Chaining**
Leverage functional methods like map() and filter() to create chains of transformations. This is more elegant than nested if-statements.

### 4. **Don't Use Optional for Fields**
Optional is designed for return values and method parameters, not for storing in class fields. It adds unnecessary memory overhead and complexity.

### 5. **Avoid Excessive Nesting**
While Optional supports chaining, deeply nested chains can become hard to read. Use intermediate variables for clarity.

### 6. **Use Streams with Optional**
Optional works seamlessly with streams, allowing you to filter and map collections elegantly.

## Performance Considerations

1. **Object Creation Overhead**: Each Optional instance is a wrapper object. For high-frequency operations, this might add memory overhead.

2. **Comparison to Null Checks**: Optional adds a small performance cost compared to simple null checks. For performance-critical code, consider the trade-off.

3. **Use orElseGet() Instead of orElse()**
   - `orElse(value)`: Always evaluates the argument
   - `orElseGet(supplier)`: Only evaluates if needed (lazy evaluation)
   - Use orElseGet() when the default value is expensive to compute

4. **Stream Performance**: Converting Optional to Stream and filtering is efficient for chaining with other streams.

## Common Patterns and Anti-Patterns

### Anti-Pattern: Unnecessary Optional Wrapping
```java
// Bad: Unnecessary wrapping
Optional<String> name = Optional.ofNullable(getValue());
if (name.isPresent()) {
    System.out.println(name.get());
}
```

### Pattern: Using ifPresent()
```java
// Good: Clean and expressive
getValue().ifPresent(System.out::println);
```

### Pattern: Using orElse() for Default Values
```java
// Clean default value handling
String result = getValue().orElse("default");
```

### Anti-Pattern: Using Optional in Fields
```java
// Bad: Don't do this
public class User {
    private Optional<String> email;  // Avoid!
}

// Good: Nullable reference or use Optional in return type
public class User {
    private String email;  // or null-safe design
}
```

## Practical Code Examples

### Example 1: Basic Optional Usage
```java
import java.util.Optional;

public class OptionalBasics {
    
    // Method returning Optional
    public static Optional<String> findUserName(int userId) {
        if (userId == 1) {
            return Optional.of("John Doe");
        } else if (userId == 2) {
            return Optional.of("Jane Smith");
        }
        return Optional.empty();
    }
    
    public static void main(String[] args) {
        // 1. Creating Optional instances
        Optional<String> name1 = Optional.of("John");
        Optional<String> name2 = Optional.ofNullable(null);
        Optional<String> name3 = Optional.empty();
        
        System.out.println("--- Creating Optional ---");
        System.out.println("name1 present: " + name1.isPresent());  // true
        System.out.println("name2 present: " + name2.isPresent());  // false
        System.out.println("name3 present: " + name3.isPresent());  // false
        
        // 2. Checking presence and getting values
        System.out.println("\n--- Checking and Getting Values ---");
        System.out.println("name1 value: " + name1.get());  // John
        System.out.println("name2 orElse: " + name2.orElse("Unknown"));  // Unknown
        
        // 3. Using ifPresent()
        System.out.println("\n--- Using ifPresent() ---");
        name1.ifPresent(value -> System.out.println("Found: " + value));
        
        // 4. Using ifPresentOrElse()
        System.out.println("\n--- Using ifPresentOrElse() ---");
        name1.ifPresentOrElse(
            value -> System.out.println("Name: " + value),
            () -> System.out.println("No name found")
        );
        
        // 5. Using findUserName method
        System.out.println("\n--- Using findUserName() ---");
        findUserName(1).ifPresent(name -> System.out.println("User 1: " + name));
        findUserName(99).ifPresent(name -> System.out.println("User 99: " + name));
    }
}
```

### Example 2: Mapping and Filtering with Optional
```java
import java.util.Optional;

public class OptionalTransformations {
    
    static class User {
        String id;
        String name;
        String email;
        
        public User(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        @Override
        public String toString() {
            return "User{" + "id='" + id + '\'' + ", name='" + name + 
                   '\'' + ", email='" + email + '\'' + '}';
        }
    }
    
    // Database simulation
    static Optional<User> findUserById(String id) {
        if ("U001".equals(id)) {
            return Optional.of(new User("U001", "Alice", "alice@example.com"));
        } else if ("U002".equals(id)) {
            return Optional.of(new User("U002", "Bob", null));
        }
        return Optional.empty();
    }
    
    public static void main(String[] args) {
        System.out.println("--- Using map() ---");
        // Transform Optional value
        findUserById("U001")
            .map(user -> user.name.toUpperCase())
            .ifPresent(name -> System.out.println("User name: " + name));
        
        System.out.println("\n--- Using flatMap() ---");
        // Chain Optional operations
        findUserById("U001")
            .flatMap(user -> Optional.ofNullable(user.email))
            .ifPresent(email -> System.out.println("Email: " + email));
        
        // User with null email
        findUserById("U002")
            .flatMap(user -> Optional.ofNullable(user.email))
            .ifPresentOrElse(
                email -> System.out.println("Email: " + email),
                () -> System.out.println("No email found")
            );
        
        System.out.println("\n--- Using filter() ---");
        // Filter based on condition
        findUserById("U001")
            .filter(user -> user.email != null)
            .map(user -> user.email)
            .ifPresent(email -> System.out.println("Valid email: " + email));
        
        System.out.println("\n--- Chaining transformations ---");
        // Complex chain: find user -> get email -> convert to lowercase -> verify non-empty
        findUserById("U001")
            .map(user -> user.email)
            .filter(email -> email != null && !email.isEmpty())
            .map(String::toLowerCase)
            .ifPresent(email -> System.out.println("Processed email: " + email));
    }
}
```

### Example 3: Performance-Conscious Optional Usage
```java
import java.util.Optional;
import java.util.function.Supplier;

public class OptionalPerformance {
    
    // Expensive default value computation
    static class Configuration {
        static String loadDefaultConfig() {
            System.out.println("Loading expensive default configuration...");
            try {
                Thread.sleep(1000);  // Simulate expensive operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "default-config";
        }
    }
    
    static Optional<String> getConfigValue(boolean present) {
        if (present) {
            return Optional.of("custom-config");
        }
        return Optional.empty();
    }
    
    public static void main(String[] args) {
        System.out.println("--- orElse() vs orElseGet() ---");
        
        // BAD: Always executes expensive computation even if Optional has value
        System.out.println("\nUsing orElse() with expensive computation:");
        long start = System.currentTimeMillis();
        String config1 = getConfigValue(true)
            .orElse(Configuration.loadDefaultConfig());
        long end = System.currentTimeMillis();
        System.out.println("Config: " + config1);
        System.out.println("Time taken: " + (end - start) + "ms");
        
        // GOOD: Only executes if Optional is empty
        System.out.println("\nUsing orElseGet() with expensive computation:");
        start = System.currentTimeMillis();
        String config2 = getConfigValue(true)
            .orElseGet(Configuration::loadDefaultConfig);
        end = System.currentTimeMillis();
        System.out.println("Config: " + config2);
        System.out.println("Time taken: " + (end - start) + "ms");
        
        // When Optional is empty, orElseGet executes
        System.out.println("\nUsing orElseGet() when Optional is empty:");
        start = System.currentTimeMillis();
        String config3 = getConfigValue(false)
            .orElseGet(Configuration::loadDefaultConfig);
        end = System.currentTimeMillis();
        System.out.println("Config: " + config3);
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}
```

### Example 4: Optional with Collections
```java
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalWithCollections {
    
    static class Product {
        int id;
        String name;
        double price;
        
        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
        
        @Override
        public String toString() {
            return "Product{" + "id=" + id + ", name='" + name + 
                   '\'' + ", price=" + price + '}';
        }
    }
    
    static Optional<Product> findProductById(List<Product> products, int id) {
        return products.stream()
            .filter(p -> p.id == id)
            .findFirst();
    }
    
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product(1, "Laptop", 999.99),
            new Product(2, "Mouse", 29.99),
            new Product(3, "Keyboard", 79.99),
            new Product(4, "Monitor", 299.99)
        );
        
        System.out.println("--- Finding Products with Optional ---");
        findProductById(products, 2)
            .ifPresent(p -> System.out.println("Found: " + p));
        
        System.out.println("\n--- Transform Product with map() ---");
        findProductById(products, 3)
            .map(p -> p.name.toUpperCase())
            .ifPresent(name -> System.out.println("Product name: " + name));
        
        System.out.println("\n--- Filter Products by Price ---");
        findProductById(products, 4)
            .filter(p -> p.price < 300)
            .ifPresentOrElse(
                p -> System.out.println("Affordable: " + p),
                () -> System.out.println("Too expensive")
            );
        
        System.out.println("\n--- Filter and Transform in Stream ---");
        // Use Optional within stream processing
        List<String> expensiveProducts = products.stream()
            .filter(p -> p.price > 100)
            .map(Product::name)
            .collect(Collectors.toList());
        
        System.out.println("Expensive products: " + expensiveProducts);
        
        System.out.println("\n--- Convert Stream to Optional ---");
        // Get first expensive product as Optional
        products.stream()
            .filter(p -> p.price > 50)
            .findFirst()
            .ifPresent(p -> System.out.println("First expensive: " + p));
    }
}
```

### Example 5: Advanced Patterns - Optional with Exception Handling
```java
import java.util.Optional;

public class OptionalExceptionHandling {
    
    // Utility to convert checked exceptions to Optional
    static <T> Optional<T> tryOperation(ThrowingSupplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (Exception e) {
            System.err.println("Operation failed: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    @FunctionalInterface
    interface ThrowingSupplier<T> {
        T get() throws Exception;
    }
    
    // Simulated operations that might throw exceptions
    static int parseInteger(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }
    
    static String fetchDataFromAPI(String url) throws Exception {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Invalid URL");
        }
        return "Data from " + url;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Graceful Exception Handling with Optional ---");
        
        // Parsing with exception handling
        System.out.println("\n1. Parse integer safely:");
        tryOperation(() -> parseInteger("42"))
            .ifPresentOrElse(
                value -> System.out.println("Parsed value: " + value),
                () -> System.out.println("Failed to parse")
            );
        
        tryOperation(() -> parseInteger("invalid"))
            .ifPresentOrElse(
                value -> System.out.println("Parsed value: " + value),
                () -> System.out.println("Failed to parse invalid number")
            );
        
        // API call with exception handling
        System.out.println("\n2. API call with exception handling:");
        tryOperation(() -> fetchDataFromAPI("https://api.example.com"))
            .ifPresent(data -> System.out.println("Result: " + data));
        
        tryOperation(() -> fetchDataFromAPI(null))
            .ifPresent(data -> System.out.println("Result: " + data));
        
        // Chaining multiple operations
        System.out.println("\n3. Chaining operations with Optional:");
        tryOperation(() -> parseInteger("100"))
            .map(value -> value * 2)
            .map(value -> "Double is: " + value)
            .ifPresent(System.out::println);
    }
}
```

## Summary

Optional is a powerful tool for writing cleaner, more expressive code when dealing with potentially absent values. Use it for:
- **Return types** of methods to indicate optional values
- **Functional transformations** using map(), filter(), flatMap()
- **Safe chaining** of operations without null checks

Avoid using Optional for:
- **Class fields** (use nullable references instead)
- **Method parameters** (use method overloading or explicit null handling)
- **All null values** (it's a design tool, not a universal null wrapper)

By following these patterns and best practices, you can write more maintainable, readable, and robust Java code.
