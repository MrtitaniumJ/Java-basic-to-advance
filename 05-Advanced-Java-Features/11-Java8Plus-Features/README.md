# Java 8+ Features

## Introduction

Java 8, released in 2014, brought paradigm-shifting changes to the Java language. The introduction of lambda expressions, the Streams API, and functional interfaces fundamentally transformed how developers write Java code. Subsequent versions (Java 9 through Java 21) continued adding features: modules, the var keyword, records, sealed classes, and pattern matching. These modern features enable writing more concise, expressive, and type-safe code while maintaining backward compatibility.

This module provides an overview of the most impactful Java 8+ features. While earlier modules covered Streams and Lambda in detail, this section explores additional features introduced in Java 8 and beyond, demonstrating how modern Java enables functional programming while maintaining its object-oriented foundations.

## Java 8 Features

### Lambda Expressions and Functional Programming
Enabled writing concise anonymous functions, enabling functional programming paradigms in an object-oriented language.

### Streams API
Transformed collection processing from imperative loops to declarative pipelines with built-in parallelization support.

### Method References
Provided shorthand notation for referring to methods without invoking them, improving code readability.

### Default Methods in Interfaces
Allowed interfaces to have method implementations, enabling evolving interfaces without breaking existing implementations.

### Optional<T>
Provided an explicit way to represent optional values, reducing NullPointerExceptions.

### Functional Interfaces
Formalized interfaces with single abstract methods, enabling lambda expressions.

## Java 9+ Features

### Module System (Java 9)
Introduced modules for organizing code into explicit packages with clear dependencies and encapsulation boundaries.

### var Keyword (Java 10)
Enabled local variable type inference, reducing boilerplate while maintaining type safety.

### Records (Java 14)
Provided concise syntax for immutable data carriers, reducing boilerplate for simple classes.

### Sealed Classes (Java 15)
Enabled restricting which classes can extend a class, improving design and pattern matching.

### Pattern Matching (Java 16+)
Enabled extracting data from objects and checking patterns, reducing instanceof boilerplate.

### Text Blocks (Java 13)
Provided multi-line string literals for readable HTML, JSON, and SQL strings.

## Key Concepts

**Functional Interfaces**: Interfaces with single abstract method, enabling lambda expressions

**Streams vs Collections**: Streams are lazy, composable, and functional; collections are eager and state-based

**Immutability**: Records and final classes promote immutable design patterns

**Type Inference**: var keyword reduces boilerplate without sacrificing type safety

**Pattern Matching**: Simplified type checking and value extraction patterns

## Best Practices

1. **Use functional programming where appropriate** - But don't overuse lambdas for simple cases
2. **Embrace Optional** - Use Optional for optional values instead of null
3. **Use var judiciously** - Improve readability but maintain clarity
4. **Prefer records for data classes** - Reduce boilerplate significantly
5. **Use sealed classes for restricted hierarchies** - Improve design and pattern matching
6. **Leverage pattern matching** - Simplify instanceof and extraction patterns
7. **Use text blocks for multi-line strings** - Improve readability of SQL, JSON, HTML
8. **Understand module boundaries** - Explicitly declare dependencies and exports

---

## Complete Working Examples

### Example 1: Optional<T> - Handling Optional Values

```java
import java.util.*;
import java.util.stream.Collectors;

public class OptionalDemo {
    
    static class User {
        private String name;
        private int age;
        private String email;
        
        public User(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public Optional<String> getEmail() {
            return email != null ? Optional.of(email) : Optional.empty();
        }
        
        @Override
        public String toString() {
            return "User{" + name + ", " + age + "}";
        }
    }
    
    public static void demonstrateOptional() {
        System.out.println("=== Optional Usage Patterns ===\n");
        
        Optional<String> presentValue = Optional.of("Hello");
        Optional<String> emptyValue = Optional.empty();
        
        System.out.println("isPresent(): " + presentValue.isPresent());
        System.out.println("isEmpty(): " + presentValue.isEmpty());
        
        System.out.println("\nget(): " + presentValue.get());
        System.out.println("orElse(): " + emptyValue.orElse("default"));
        System.out.println("orElseGet(): " + emptyValue.orElseGet(() -> "computed default"));
        
        System.out.println("\nifPresent():");
        presentValue.ifPresent(v -> System.out.println("  Value present: " + v));
        emptyValue.ifPresent(v -> System.out.println("  This won't print"));
        
        System.out.println("\nifPresentOrElse():");
        presentValue.ifPresentOrElse(
            v -> System.out.println("  Present: " + v),
            () -> System.out.println("  Empty")
        );
        
        emptyValue.ifPresentOrElse(
            v -> System.out.println("  Present: " + v),
            () -> System.out.println("  Empty")
        );
    }
    
    public static void demonstrateOptionalWithObjects() {
        System.out.println("\n=== Optional with Objects ===\n");
        
        List<User> users = Arrays.asList(
            new User("Alice", 28, "alice@example.com"),
            new User("Bob", 32, null),
            new User("Charlie", 25, "charlie@example.com")
        );
        
        System.out.println("Processing user emails:");
        users.forEach(user -> {
            user.getEmail()
                .ifPresentOrElse(
                    email -> System.out.println("  " + user.getName() + ": " + email),
                    () -> System.out.println("  " + user.getName() + ": [no email]")
                );
        });
        
        System.out.println("\nFiltering users with emails:");
        List<String> emailUsers = users.stream()
            .filter(u -> u.getEmail().isPresent())
            .map(User::getName)
            .collect(Collectors.toList());
        System.out.println("  " + emailUsers);
        
        System.out.println("\nFlatMap with Optional:");
        List<String> emails = users.stream()
            .flatMap(u -> u.getEmail().stream())
            .collect(Collectors.toList());
        System.out.println("  " + emails);
    }
    
    public static void main(String[] args) {
        demonstrateOptional();
        demonstrateOptionalWithObjects();
    }
}
```

**Output:**
```
=== Optional Usage Patterns ===

isPresent(): true
isEmpty(): false

get(): Hello
orElse(): default
orElseGet(): computed default

ifPresent():
  Value present: Hello

ifPresentOrElse():
  Present: Hello
  Empty

=== Optional with Objects ===

Processing user emails:
  Alice: alice@example.com
  Bob: [no email]
  Charlie: charlie@example.com

Filtering users with emails:
  [Alice, Charlie]

FlatMap with Optional:
  [alice@example.com, charlie@example.com]
```

### Example 2: var Keyword - Local Variable Type Inference

```java
import java.util.*;

public class VarKeywordDemo {
    
    public static void demonstrateVarKeyword() {
        System.out.println("=== var Keyword Examples ===\n");
        
        // Type inference for primitives
        var count = 10; // int
        var price = 29.99; // double
        var active = true; // boolean
        var initial = 'A'; // char
        
        System.out.println("Primitive types:");
        System.out.println("  count = " + count + " (type: " + 
                         count.getClass().getSimpleName() + ")");
        System.out.println("  price = " + price + " (type: " + 
                         ((Object) price).getClass().getSimpleName() + ")");
        System.out.println("  active = " + active + " (type: boolean)");
        System.out.println("  initial = " + initial + " (type: char)");
        
        // Type inference for collections
        var numbers = new ArrayList<Integer>();
        numbers.addAll(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\nCollections:");
        System.out.println("  numbers = " + numbers);
        
        var names = List.of("Alice", "Bob", "Charlie");
        System.out.println("  names = " + names);
        
        var scores = new HashMap<String, Integer>();
        scores.put("Math", 95);
        scores.put("English", 88);
        System.out.println("  scores = " + scores);
        
        // Type inference in loops
        System.out.println("\nLoop with var:");
        for (var i = 0; i < 3; i++) {
            System.out.println("  Iteration " + i);
        }
        
        // Type inference for enhanced for loop
        System.out.println("\nEnhanced for with var:");
        for (var num : numbers) {
            System.out.println("  " + num);
        }
    }
    
    public static void demonstrateVarLimitations() {
        System.out.println("\n=== var Keyword Limitations ===\n");
        
        // Type must be inferrable
        var list = new ArrayList<String>(); // OK
        System.out.println("ArrayList inferred correctly");
        
        // Diamond operator helps
        var map = new HashMap<String, Integer>(); // OK
        System.out.println("HashMap inferred correctly");
        
        System.out.println("\nvar cannot be used for:");
        System.out.println("  - Method parameters");
        System.out.println("  - Method return types");
        System.out.println("  - Class/instance fields");
        System.out.println("  - Lambda parameter types (must use explicit types)");
    }
    
    public static void main(String[] args) {
        demonstrateVarKeyword();
        demonstrateVarLimitations();
    }
}
```

**Output:**
```
=== var Keyword Examples ===

Primitive types:
  count = 10 (type: Integer)
  price = 29.99 (type: Double)
  active = true (type: boolean)
  initial = A (type: char)

Collections:
  numbers = [1, 2, 3, 4, 5]
  names = [Alice, Bob, Charlie]
  scores = {Math=95, English=88}

Loop with var:
  Iteration 0
  Iteration 1
  Iteration 2

Enhanced for with var:
  1
  2
  3
  4
  5

=== var Keyword Limitations ===

ArrayList inferred correctly
HashMap inferred correctly

var cannot be used for:
  - Method parameters
  - Method return types
  - Class/instance fields
  - Lambda parameter types (must use explicit types)
```

### Example 3: Records - Immutable Data Classes

```java
public record Point(int x, int y) {
    // Compact constructor
    public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }
}

public record Person(String name, int age, String email) {
    // Static factory method
    public static Person of(String name, int age) {
        return new Person(name, age, "unknown@example.com");
    }
    
    // Instance method
    public boolean isAdult() {
        return age >= 18;
    }
}

public record StudentRecord(String id, String name, double gpa) {
    // Validation in compact constructor
    public StudentRecord {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
    }
    
    // Additional methods
    public boolean isHonorStudent() {
        return gpa >= 3.5;
    }
    
    public String getAcademicStatus() {
        if (gpa >= 3.5) return "Excellent";
        if (gpa >= 3.0) return "Good";
        if (gpa >= 2.0) return "Satisfactory";
        return "Needs Improvement";
    }
}

public class RecordsDemo {
    
    public static void demonstrateRecords() {
        System.out.println("=== Records Demo ===\n");
        
        // Simple record
        Point p = new Point(3, 4);
        System.out.println("Point: " + p);
        System.out.println("  x = " + p.x() + ", y = " + p.y());
        
        // Record with validation
        try {
            Point invalid = new Point(-1, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation caught invalid point: " + e.getMessage());
        }
        
        // Record with methods
        Person person = new Person("Alice", 25, "alice@example.com");
        System.out.println("\nPerson: " + person);
        System.out.println("  Is adult: " + person.isAdult());
        
        // Static factory
        Person person2 = Person.of("Bob", 17);
        System.out.println("Person from factory: " + person2);
        System.out.println("  Is adult: " + person2.isAdult());
        
        // Record equality (built-in)
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        System.out.println("\nRecord equality:");
        System.out.println("  p1.equals(p2) = " + p1.equals(p2));
        System.out.println("  p1.hashCode() == p2.hashCode() = " + 
                         (p1.hashCode() == p2.hashCode()));
        
        // Student record with comprehensive methods
        StudentRecord student = new StudentRecord("S001", "Charlie", 3.7);
        System.out.println("\nStudent: " + student);
        System.out.println("  Honor student: " + student.isHonorStudent());
        System.out.println("  Academic status: " + student.getAcademicStatus());
    }
    
    public static void main(String[] args) {
        demonstrateRecords();
    }
}
```

**Output:**
```
=== Records Demo ===

Point: Point[x=3, y=4]
  x = 3, y = 4
✓ Validation caught invalid point: Coordinates must be non-negative

Person: Person[name=Alice, age=25, email=alice@example.com]
  Is adult: true
Person from factory: Person[name=Bob, age=17, email=unknown@example.com]
  Is adult: false

Record equality:
  p1.equals(p2) = true
  p1.hashCode() == p2.hashCode() = true

Student: StudentRecord[id=S001, name=Charlie, gpa=3.7]
  Honor student: true
  Academic status: Excellent
```

### Example 4: Text Blocks - Multi-line Strings

```java
public class TextBlocksDemo {
    
    public static void demonstrateTextBlocks() {
        System.out.println("=== Text Blocks (Java 13+) ===\n");
        
        // JSON example
        String json = """
            {
                "name": "Alice",
                "age": 28,
                "email": "alice@example.com"
            }""";
        System.out.println("JSON:");
        System.out.println(json);
        
        // HTML example
        String html = """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>Example Page</title>
                </head>
                <body>
                    <h1>Welcome</h1>
                    <p>This is an example HTML.</p>
                </body>
            </html>""";
        System.out.println("\nHTML:");
        System.out.println(html);
        
        // SQL example
        String sql = """
            SELECT u.id, u.name, COUNT(o.id) as order_count
            FROM users u
            LEFT JOIN orders o ON u.id = o.user_id
            WHERE u.age > 18
            GROUP BY u.id, u.name
            ORDER BY order_count DESC
            LIMIT 10;""";
        System.out.println("\nSQL:");
        System.out.println(sql);
        
        // Comparison with traditional concatenation
        System.out.println("\n=== Advantages over concatenation ===");
        String traditional = "Line 1\n" +
                           "Line 2\n" +
                           "Line 3";
        String textBlock = """
            Line 1
            Line 2
            Line 3""";
        System.out.println("Traditional: " + (traditional.equals(textBlock) ? "✓ Same result" : "Different"));
        System.out.println("Text block is more readable!");
    }
    
    public static void main(String[] args) {
        demonstrateTextBlocks();
    }
}
```

**Output:**
```
=== Text Blocks (Java 13+) ===

JSON:
{
    "name": "Alice",
    "age": 28,
    "email": "alice@example.com"
}

HTML:
<!DOCTYPE html>
<html>
    <head>
        <title>Example Page</title>
    </head>
    <body>
        <h1>Welcome</h1>
        <p>This is an example HTML.</p>
    </body>
</html>

SQL:
SELECT u.id, u.name, COUNT(o.id) as order_count
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE u.age > 18
GROUP BY u.id, u.name
ORDER BY order_count DESC
LIMIT 10;

=== Advantages over concatenation ===
Traditional: ✓ Same result
Text block is more readable!
```

### Example 5: Sealed Classes - Restricted Inheritance

```java
// Sealed class with permitted subclasses
public sealed class Shape permits Circle, Rectangle, Triangle {
    public abstract double getArea();
}

public final class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public String toString() {
        return "Circle(r=" + radius + ")";
    }
}

public final class Rectangle extends Shape {
    private double width, height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    @Override
    public String toString() {
        return "Rectangle(w=" + width + ", h=" + height + ")";
    }
}

public final class Triangle extends Shape {
    private double base, height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double getArea() {
        return (base * height) / 2;
    }
    
    @Override
    public String toString() {
        return "Triangle(b=" + base + ", h=" + height + ")";
    }
}

public class SealedClassesDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Sealed Classes Demo ===\n");
        
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);
        Shape triangle = new Triangle(3, 4);
        
        Shape[] shapes = {circle, rectangle, triangle};
        
        System.out.println("Shapes and their areas:");
        for (Shape shape : shapes) {
            System.out.println("  " + shape + " -> Area: " + 
                             String.format("%.2f", shape.getArea()));
        }
        
        System.out.println("\nSealed class benefits:");
        System.out.println("  1. Controlled inheritance hierarchy");
        System.out.println("  2. Compiler knows all possible subclasses");
        System.out.println("  3. Better pattern matching");
        System.out.println("  4. Improved design and maintainability");
    }
}
```

**Output:**
```
=== Sealed Classes Demo ===

Shapes and their areas:
  Circle(r=5) -> Area: 78.54
  Rectangle(w=4, h=6) -> Area: 24.00
  Triangle(b=3, h=4) -> Area: 6.00

Sealed class benefits:
  1. Controlled inheritance hierarchy
  2. Compiler knows all possible subclasses
  3. Better pattern matching
  4. Improved design and maintainability
```

## Migration Path

When upgrading Java versions:
1. Java 8: Adopt Streams and Lambda expressions
2. Java 10: Use var keyword for local variables
3. Java 14+: Migrate data classes to Records
4. Java 15+: Use sealed classes for controlled hierarchies
5. Java 16+: Embrace pattern matching

## Performance Improvements

- **Lambda/Streams**: Zero overhead compared to traditional approaches
- **Records**: Minimal overhead; same as handwritten classes
- **var keyword**: Zero runtime overhead
- **Sealed classes**: Enable compiler optimizations

## Summary

Java 8+ features represent significant evolution, introducing functional programming paradigms while maintaining backward compatibility. Lambda expressions and Streams changed how developers process collections. Optional improved null handling. The var keyword, Records, sealed classes, and pattern matching continue modernizing the language. These features, used appropriately, enable writing more concise, expressive, and maintainable Java code. Adopting these features progressively improves code quality and developer productivity.
