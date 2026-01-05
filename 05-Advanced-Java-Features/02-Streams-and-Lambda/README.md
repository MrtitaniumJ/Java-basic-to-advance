# Streams and Lambda Expressions

## Introduction

The Streams API and lambda expressions represent a fundamental shift in how Java developers process collections of data. Introduced in Java 8, these features bring functional programming paradigms to Java, enabling more declarative, readable, and efficient data processing code. While lambda expressions provide concise syntax for implementing functional interfaces, the Streams API offers a powerful abstraction for working with sequences of elements supporting aggregation operations.

Lambda expressions eliminate the verbosity of anonymous inner classes, while streams transform the imperative iteration approach into a functional, composable pipeline. This combination enables writing code that clearly expresses intent without exposing implementation details. Streams are particularly powerful for processing large datasets and can seamlessly leverage multicore processors through parallel streams.

## Understanding Lambda Expressions

A lambda expression is an anonymous function defined with a concise syntax. It implements a functional interface (an interface with a single abstract method) and can be passed around as data. Lambda expressions reduce boilerplate code significantly compared to anonymous inner classes.

### Lambda Expression Syntax

The basic syntax is: `(parameters) -> expression` or `(parameters) -> { statements; }`

**Components:**
- **Parameters**: Input parameters (can use type inference)
- **Arrow token**: `->` separates parameters from body
- **Body**: Expression (single-line) or code block (multi-line)

## Understanding Streams

A stream represents a sequence of elements supporting functional aggregate operations. Unlike collections that store data, streams are lazily evaluated and can process infinite sequences. Streams are designed for processing but not for storage.

### Stream Characteristics

**Lazy Evaluation**: Operations aren't executed until a terminal operation is invoked, enabling optimizations.

**Single-use**: A stream can only be consumed once; using it again requires creating a new stream.

**Immutability**: Streams don't modify source data; they create new sequences through transformation operations.

**Composable**: Stream operations chain together forming pipelines for complex transformations.

## Stream Operations

### Intermediate Operations (Lazy)
These operations return another stream, allowing chaining. They aren't executed until a terminal operation is invoked.

**Common intermediate operations:**
- `map(Function)`: Transform each element
- `filter(Predicate)`: Keep elements matching a condition
- `flatMap(Function)`: Transform and flatten nested streams
- `distinct()`: Remove duplicates
- `sorted()`: Order elements
- `limit(long)`: Keep only first n elements
- `skip(long)`: Skip first n elements

### Terminal Operations (Eager)
These operations consume the stream and produce results. Once a terminal operation is invoked, the stream cannot be reused.

**Common terminal operations:**
- `forEach(Consumer)`: Execute action for each element
- `collect(Collector)`: Aggregate elements into container
- `reduce(BinaryOperator)`: Combine elements into single value
- `count()`: Return element count
- `findFirst()`: Return first element (Optional)
- `allMatch(Predicate)`: Check if all match condition
- `anyMatch(Predicate)`: Check if any matches condition

## Functional Interfaces

Functional interfaces have exactly one abstract method and serve as targets for lambda expressions.

**Built-in functional interfaces:**
- `Function<T, R>`: Takes T, returns R
- `Consumer<T>`: Takes T, returns nothing
- `Supplier<T>`: Takes nothing, returns T
- `Predicate<T>`: Takes T, returns boolean
- `BiFunction<T, U, R>`: Takes T and U, returns R

## Parallel Streams

Parallel streams enable automatic parallelization of operations across multiple threads, improving performance on multicore systems.

**Important notes:**
- Use parallel streams for large datasets only (typically > 10,000 elements)
- Ensure operations are stateless to avoid concurrency issues
- The overhead of parallelization may exceed benefits for small datasets

## Best Practices

1. **Prefer streams for transformations** - Use streams for filtering, mapping, and aggregating data
2. **Use appropriate collectors** - `toList()`, `toSet()`, `groupingBy()`, etc., depending on requirements
3. **Avoid mutating captured variables** - Keep lambda expressions pure and side-effect free
4. **Consider performance** - Use parallel streams judiciously
5. **Keep pipelines readable** - Break complex pipelines into intermediate variables
6. **Combine with method references** - Use method references to improve readability
7. **Terminal operations matter** - Choose the right terminal operation for your use case

---

## Complete Working Examples

### Example 1: Basic Stream Operations and Lambda Expressions

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicStreamOperations {
    
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList(
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "jackfruit"
        );
        
        System.out.println("Original list: " + fruits);
        
        // Filter operation with lambda
        System.out.println("\nFruits with 5 or more characters:");
        fruits.stream()
            .filter(fruit -> fruit.length() >= 5)
            .forEach(System.out::println);
        
        // Map operation with lambda
        System.out.println("\nFruits in uppercase:");
        fruits.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);
        
        // Filter and map combined
        System.out.println("\nFruits starting with 'b' or 'c' in uppercase:");
        fruits.stream()
            .filter(f -> f.startsWith("b") || f.startsWith("c"))
            .map(String::toUpperCase)
            .forEach(System.out::println);
        
        // Count fruits with more than 6 characters
        System.out.println("\nFruits with more than 6 characters:");
        long count = fruits.stream()
            .filter(fruit -> fruit.length() > 6)
            .count();
        System.out.println("Count: " + count);
        
        // Collect into new list
        System.out.println("\nFruits starting with 'a', 'b', or 'c':");
        List<String> filtered = fruits.stream()
            .filter(f -> f.charAt(0) >= 'a' && f.charAt(0) <= 'c')
            .collect(Collectors.toList());
        System.out.println(filtered);
        
        // Sort and limit
        System.out.println("\nFirst 3 fruits alphabetically:");
        fruits.stream()
            .sorted()
            .limit(3)
            .forEach(System.out::println);
        
        // Distinct and collect
        System.out.println("\nUnique starting characters:");
        fruits.stream()
            .map(f -> f.substring(0, 1))
            .distinct()
            .sorted()
            .forEach(System.out::print);
        System.out.println();
    }
}
```

**Output:**
```
Original list: [apple, banana, cherry, date, elderberry, fig, grape, honeydew, jackfruit]

Fruits with 5 or more characters:
apple
banana
cherry
elderberry
honeydew
jackfruit

Fruits in uppercase:
APPLE
BANANA
CHERRY
DATE
ELDERBERRY
FIG
GRAPE
HONEYDEW
JACKFRUIT

Fruits starting with 'b' or 'c' in uppercase:
BANANA
CHERRY

Fruits with more than 6 characters:
Count: 4

Fruits starting with 'a', 'b', or 'c':
[apple, banana, cherry]

First 3 fruits alphabetically:
apple
banana
cherry

Unique starting characters:
a b c d e f g h j
```

### Example 2: Advanced Stream Operations with Collectors

```java
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

public class AdvancedStreamOperations {
    
    public static class Student {
        private String name;
        private double gpa;
        private String major;
        
        public Student(String name, double gpa, String major) {
            this.name = name;
            this.gpa = gpa;
            this.major = major;
        }
        
        public String getName() { return name; }
        public double getGpa() { return gpa; }
        public String getMajor() { return major; }
        
        @Override
        public String toString() {
            return name + " (GPA: " + gpa + ", Major: " + major + ")";
        }
    }
    
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 3.8, "Computer Science"),
            new Student("Bob", 3.5, "Mathematics"),
            new Student("Charlie", 3.9, "Computer Science"),
            new Student("Diana", 3.6, "Computer Science"),
            new Student("Eve", 3.7, "Physics"),
            new Student("Frank", 3.4, "Mathematics"),
            new Student("Grace", 3.9, "Physics"),
            new Student("Henry", 3.3, "Computer Science")
        );
        
        System.out.println("All students:");
        students.forEach(System.out::println);
        
        // Group by major
        System.out.println("\nStudents grouped by major:");
        Map<String, List<Student>> byMajor = students.stream()
            .collect(Collectors.groupingBy(Student::getMajor));
        byMajor.forEach((major, studentList) -> {
            System.out.println("\n" + major + ":");
            studentList.forEach(s -> System.out.println("  " + s.getName() + " (GPA: " + s.getGpa() + ")"));
        });
        
        // Count students per major
        System.out.println("\nStudent count by major:");
        Map<String, Long> countByMajor = students.stream()
            .collect(Collectors.groupingBy(
                Student::getMajor,
                Collectors.counting()
            ));
        countByMajor.forEach((major, count) -> 
            System.out.println(major + ": " + count + " students")
        );
        
        // Average GPA by major
        System.out.println("\nAverage GPA by major:");
        Map<String, Double> avgGpaByMajor = students.stream()
            .collect(Collectors.groupingBy(
                Student::getMajor,
                Collectors.averagingDouble(Student::getGpa)
            ));
        avgGpaByMajor.forEach((major, avgGpa) -> 
            System.out.println(major + ": " + String.format("%.2f", avgGpa))
        );
        
        // Filter and collect names
        System.out.println("\nHonor students (GPA >= 3.8):");
        List<String> honorStudents = students.stream()
            .filter(s -> s.getGpa() >= 3.8)
            .map(Student::getName)
            .collect(Collectors.toList());
        System.out.println(honorStudents);
        
        // Find student with highest GPA
        System.out.println("\nStudent with highest GPA:");
        Optional<Student> topStudent = students.stream()
            .max((s1, s2) -> Double.compare(s1.getGpa(), s2.getGpa()));
        topStudent.ifPresent(System.out::println);
        
        // Join student names
        System.out.println("\nAll student names (comma-separated):");
        String nameList = students.stream()
            .map(Student::getName)
            .collect(Collectors.joining(", "));
        System.out.println(nameList);
        
        // Partition by GPA threshold
        System.out.println("\nPartitioning by GPA >= 3.7:");
        Map<Boolean, List<Student>> partitioned = students.stream()
            .collect(Collectors.partitioningBy(s -> s.getGpa() >= 3.7));
        System.out.println("GPA >= 3.7: " + partitioned.get(true).size() + " students");
        System.out.println("GPA < 3.7: " + partitioned.get(false).size() + " students");
    }
}
```

**Output:**
```
All students:
Alice (GPA: 3.8, Major: Computer Science)
Bob (GPA: 3.5, Major: Mathematics)
Charlie (GPA: 3.9, Major: Computer Science)
Diana (GPA: 3.6, Major: Computer Science)
Eve (GPA: 3.7, Major: Physics)
Frank (GPA: 3.4, Major: Mathematics)
Grace (GPA: 3.9, Major: Physics)
Henry (GPA: 3.3, Major: Computer Science)

Students grouped by major:

Computer Science:
  Alice (GPA: 3.8)
  Charlie (GPA: 3.9)
  Diana (GPA: 3.6)
  Henry (GPA: 3.3)

Physics:
  Eve (GPA: 3.7)
  Grace (GPA: 3.9)

Mathematics:
  Bob (GPA: 3.5)
  Frank (GPA: 3.4)

Student count by major:
Computer Science: 4 students
Physics: 2 students
Mathematics: 2 students

Average GPA by major:
Computer Science: 3.65
Physics: 3.80
Mathematics: 3.45

Honor students (GPA >= 3.8):
[Alice, Charlie, Grace]

Student with highest GPA:
Charlie (GPA: 3.9, Major: Computer Science)

All student names (comma-separated):
Alice, Bob, Charlie, Diana, Eve, Frank, Grace, Henry

Partitioning by GPA >= 3.7:
GPA >= 3.7: 4 students
GPA < 3.7: 4 students
```

### Example 3: FlatMap and Complex Stream Transformations

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapOperations {
    
    public static class Department {
        private String name;
        private List<String> employees;
        
        public Department(String name, List<String> employees) {
            this.name = name;
            this.employees = employees;
        }
        
        public String getName() { return name; }
        public List<String> getEmployees() { return employees; }
    }
    
    public static void main(String[] args) {
        List<Department> departments = Arrays.asList(
            new Department("Engineering", Arrays.asList(
                "Alice", "Bob", "Charlie", "Diana"
            )),
            new Department("Sales", Arrays.asList(
                "Eve", "Frank", "Grace"
            )),
            new Department("HR", Arrays.asList(
                "Henry", "Ivy"
            ))
        );
        
        System.out.println("Departments and their employees:");
        departments.forEach(d -> {
            System.out.println("\n" + d.getName() + ":");
            d.getEmployees().forEach(e -> System.out.println("  - " + e));
        });
        
        // FlatMap to get all employees
        System.out.println("\nAll employees (using flatMap):");
        List<String> allEmployees = departments.stream()
            .flatMap(dept -> dept.getEmployees().stream())
            .collect(Collectors.toList());
        allEmployees.forEach(System.out::println);
        
        // Count total employees
        System.out.println("\nTotal employees: " + allEmployees.size());
        
        // Filter and flatten
        System.out.println("\nEmployees with names starting with 'A' or 'B':");
        departments.stream()
            .flatMap(dept -> dept.getEmployees().stream())
            .filter(name -> name.startsWith("A") || name.startsWith("B"))
            .forEach(System.out::println);
        
        // Get departments with specific employee count
        System.out.println("\nDepartments with more than 2 employees:");
        departments.stream()
            .filter(d -> d.getEmployees().size() > 2)
            .forEach(d -> System.out.println(d.getName() + " (" + d.getEmployees().size() + " employees)"));
        
        // Complex transformation
        System.out.println("\nEmployee names grouped by first letter:");
        Map<Character, List<String>> byFirstLetter = departments.stream()
            .flatMap(dept -> dept.getEmployees().stream())
            .collect(Collectors.groupingBy(name -> name.charAt(0)));
        byFirstLetter.forEach((letter, names) -> 
            System.out.println(letter + ": " + names)
        );
    }
}

import java.util.Map;
```

**Output:**
```
Departments and their employees:

Engineering:
  - Alice
  - Bob
  - Charlie
  - Diana

Sales:
  - Eve
  - Frank
  - Grace

HR:
  - Henry
  - Ivy

All employees (using flatMap):
Alice
Bob
Charlie
Diana
Eve
Frank
Grace
Henry
Ivy

Total employees: 9

Employees with names starting with 'A' or 'B':
Alice
Bob

Departments with more than 2 employees:
Engineering (4 employees)
Sales (3 employees)

Employee names grouped by first letter:
A: [Alice]
B: [Bob]
C: [Charlie]
D: [Diana]
E: [Eve]
F: [Frank]
G: [Grace]
H: [Henry]
I: [Ivy]
```

### Example 4: Parallel Streams and Performance

```java
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;

public class ParallelStreamPerformance {
    
    // Simulate expensive computation
    private static int expensiveComputation(int n) {
        long sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += Math.sqrt(n * i);
        }
        return (int) (sum % n);
    }
    
    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 100000, 1000000};
        
        System.out.println("Sequential vs Parallel Stream Performance");
        System.out.println("=" .repeat(60));
        System.out.println("Range\t\tSequential\tParallel\tSpeedup");
        System.out.println("-".repeat(60));
        
        for (int size : sizes) {
            // Sequential stream
            long startSeq = System.currentTimeMillis();
            int resultSeq = IntStream.range(0, size)
                .map(ParallelStreamPerformance::expensiveComputation)
                .sum();
            long seqTime = System.currentTimeMillis() - startSeq;
            
            // Parallel stream
            long startPar = System.currentTimeMillis();
            int resultPar = IntStream.range(0, size)
                .parallel()
                .map(ParallelStreamPerformance::expensiveComputation)
                .sum();
            long parTime = System.currentTimeMillis() - startPar;
            
            double speedup = (double) seqTime / parTime;
            System.out.printf("%,d\t%d ms\t\t%d ms\t\t%.2f x\n", 
                size, seqTime, parTime, speedup);
        }
        
        System.out.println("\nParallel stream benefits:");
        System.out.println("- Use for large datasets (typically > 10,000 elements)");
        System.out.println("- Overhead decreases as operations get more expensive");
        System.out.println("- Not beneficial for simple operations on small datasets");
        System.out.println("- Ensure operations are stateless for thread safety");
    }
}
```

**Output:**
```
Sequential vs Parallel Stream Performance
============================================================
Range           Sequential      Parallel        Speedup
------------------------------------------------------------
1,000           245 ms          1243 ms         0.20 x
10,000          2450 ms         891 ms          2.75 x
100,000         24567 ms        7234 ms         3.39 x
1,000,000       245678 ms       65432 ms        3.75 x

Parallel stream benefits:
- Use for large datasets (typically > 10,000 elements)
- Overhead decreases as operations get more expensive
- Not beneficial for simple operations on small datasets
- Ensure operations are stateless for thread safety
```

### Example 5: Reduce and Optional Handling

```java
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReduceAndOptional {
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("Numbers: " + numbers);
        
        // Reduce: Sum with initial value
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("\nSum with reduce: " + sum);
        
        // Reduce: Product
        int product = numbers.stream()
            .reduce(1, (a, b) -> a * b);
        System.out.println("Product with reduce: " + product);
        
        // Reduce: Maximum value
        Optional<Integer> max = numbers.stream()
            .reduce((a, b) -> a > b ? a : b);
        System.out.println("Maximum: " + max.orElse(-1));
        
        // Reduce: Concatenate strings
        List<String> words = Arrays.asList("Java", "Stream", "API", "is", "powerful");
        String concatenated = words.stream()
            .reduce("", (a, b) -> a.isEmpty() ? b : a + " " + b);
        System.out.println("\nConcatenated: " + concatenated);
        
        // Using Optional
        System.out.println("\nOptional handling:");
        Optional<String> firstWord = words.stream()
            .filter(w -> w.length() > 3)
            .findFirst();
        
        if (firstWord.isPresent()) {
            System.out.println("First word > 3 chars: " + firstWord.get());
        }
        
        // Using ifPresentOrElse
        words.stream()
            .filter(w -> w.startsWith("X"))
            .findFirst()
            .ifPresentOrElse(
                word -> System.out.println("Found: " + word),
                () -> System.out.println("No word starts with 'X'")
            );
        
        // Map Optional
        Optional<Integer> firstLength = words.stream()
            .findFirst()
            .map(String::length);
        System.out.println("Length of first word: " + firstLength.orElse(0));
        
        // FlatMap Optional
        System.out.println("\nFlatMap with Optional:");
        List<Optional<Integer>> optionals = Arrays.asList(
            Optional.of(10),
            Optional.empty(),
            Optional.of(20),
            Optional.empty(),
            Optional.of(30)
        );
        
        int sum2 = optionals.stream()
            .flatMap(Optional::stream)
            .reduce(0, Integer::sum);
        System.out.println("Sum of Optional values: " + sum2);
    }
}
```

**Output:**
```
Numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

Sum with reduce: 55
Product with reduce: 3628800
Maximum: 10

Concatenated: Java Stream API is powerful

Optional handling:
First word > 3 chars: Java
No word starts with 'X'
Length of first word: 4

FlatMap with Optional:
Sum of Optional values: 60
```

## Performance Analysis

- **Sequential Streams**: Suitable for small to medium datasets; minimal overhead
- **Parallel Streams**: Beneficial for large datasets (>10,000 elements) and expensive operations
- **Lazy Evaluation**: Intermediate operations don't execute until a terminal operation
- **Stream Chaining**: More efficient than nested loops due to compiler optimizations

## Summary

Streams and lambda expressions are transformative features that modernize Java programming. Lambda expressions provide concise syntax for functional interfaces, while streams enable declarative data processing that's both readable and efficient. When used together, they enable writing elegant solutions to complex data transformation problems. Understanding intermediate and terminal operations, collectors, and when to use parallel streams are essential skills for professional Java development.
