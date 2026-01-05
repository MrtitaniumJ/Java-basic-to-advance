# Constructor References

## Overview

Constructor references allow you to reference constructors of classes using the double colon (`::`) operator combined with the `new` keyword. They provide a concise way to create new instances and are particularly useful when working with functional interfaces that need to instantiate objects, such as factories or stream mapping operations.

## Definition

A constructor reference is a shorthand syntax for creating a functional interface implementation that calls a specific constructor. Instead of using a lambda expression to instantiate an object, you can directly reference the constructor, making the code more readable and maintainable.

## Syntax

```java
ClassName::new
```

For generic classes:
```java
GenericClass<Type>::new
```

For arrays:
```java
Type[]::new
```

## Basic Constructor References

### Example 1: Simple Constructor Reference

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorReferenceBasics {
    
    public static class Person {
        private String name;
        
        public Person(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return "Person{" + "name='" + name + '\'' + '}';
        }
    }
    
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        // Using lambda expression
        List<Person> peopleWithLambda = names.stream()
            .map(name -> new Person(name))
            .collect(Collectors.toList());
        System.out.println("Using Lambda: " + peopleWithLambda);
        
        // Using constructor reference
        List<Person> peopleWithReference = names.stream()
            .map(Person::new)
            .collect(Collectors.toList());
        System.out.println("Using Constructor Reference: " + peopleWithReference);
    }
}
```

### Example 2: Multiple Constructor Overloads

```java
import java.util.function.BiFunction;
import java.util.function.Function;

public class MultipleConstructorOverloads {
    
    public static class Employee {
        private String name;
        private double salary;
        private String department;
        
        // Constructor with one parameter
        public Employee(String name) {
            this.name = name;
            this.salary = 0;
            this.department = "Not Assigned";
        }
        
        // Constructor with two parameters
        public Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
            this.department = "Not Assigned";
        }
        
        // Constructor with three parameters
        public Employee(String name, double salary, String department) {
            this.name = name;
            this.salary = salary;
            this.department = department;
        }
        
        @Override
        public String toString() {
            return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
        }
    }
    
    public static void main(String[] args) {
        // Constructor reference with one parameter
        Function<String, Employee> createEmployeeWithName = Employee::new;
        Employee emp1 = createEmployeeWithName.apply("John");
        System.out.println(emp1);
        
        // Constructor reference with two parameters
        BiFunction<String, Double, Employee> createEmployeeWithSalary = Employee::new;
        Employee emp2 = createEmployeeWithSalary.apply("Jane", 75000.0);
        System.out.println(emp2);
        
        // To use three-parameter constructor, we need a custom functional interface
        ThreeParameterFunction<String, Double, String, Employee> createFullEmployee = Employee::new;
        Employee emp3 = createFullEmployee.apply("Bob", 80000.0, "Engineering");
        System.out.println(emp3);
    }
    
    // Custom functional interface for three parameters
    @FunctionalInterface
    public interface ThreeParameterFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
```

### Example 3: Generic Constructor References

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericConstructorReference {
    
    public static class Box<T> {
        private T content;
        
        public Box(T content) {
            this.content = content;
        }
        
        public T getContent() {
            return content;
        }
        
        @Override
        public String toString() {
            return "Box{" + "content=" + content + '}';
        }
    }
    
    public static void main(String[] args) {
        // String boxes
        List<String> strings = Arrays.asList("Apple", "Banana", "Cherry");
        List<Box<String>> stringBoxes = strings.stream()
            .map(Box<String>::new)
            .collect(Collectors.toList());
        System.out.println("String Boxes: " + stringBoxes);
        
        // Integer boxes
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Box<Integer>> integerBoxes = numbers.stream()
            .map(Box<Integer>::new)
            .collect(Collectors.toList());
        System.out.println("Integer Boxes: " + integerBoxes);
    }
}
```

## Advanced Constructor References

### Example 4: Array Constructor References

```java
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class ArrayConstructorReference {
    
    public static void main(String[] args) {
        // Create array using constructor reference
        IntFunction<String[]> stringArrayConstructor = String[]::new;
        String[] array1 = stringArrayConstructor.apply(5);
        System.out.println("Created String array with size: " + array1.length);
        
        // Using array constructor reference with streams
        IntFunction<Integer[]> integerArrayConstructor = Integer[]::new;
        Integer[] numbersArray = IntStream.rangeClosed(1, 5)
            .boxed()
            .collect(java.util.stream.Collectors.toCollection(() -> integerArrayConstructor.apply(5)))
            .toArray(Integer[]::new);
        
        System.out.println("Array contents: " + java.util.Arrays.toString(numbersArray));
        
        // Creating 2D array
        int rows = 3;
        int cols = 4;
        Integer[][] matrix = IntStream.range(0, rows)
            .mapToObj(i -> new Integer[cols])
            .toArray(Integer[][]::new);
        System.out.println("Created 2D array with dimensions: " + rows + " x " + cols);
    }
}
```

### Example 5: Factory Pattern with Constructor References

```java
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryPatternExample {
    
    public static abstract class Animal {
        public abstract void makeSound();
        
        public static class Dog extends Animal {
            @Override
            public void makeSound() {
                System.out.println("Woof! Woof!");
            }
        }
        
        public static class Cat extends Animal {
            @Override
            public void makeSound() {
                System.out.println("Meow! Meow!");
            }
        }
        
        public static class Bird extends Animal {
            @Override
            public void makeSound() {
                System.out.println("Tweet! Tweet!");
            }
        }
    }
    
    public static class AnimalFactory {
        private Map<String, Supplier<? extends Animal>> registry = new HashMap<>();
        
        public AnimalFactory() {
            // Register constructors using constructor references
            registry.put("dog", Animal.Dog::new);
            registry.put("cat", Animal.Cat::new);
            registry.put("bird", Animal.Bird::new);
        }
        
        public Animal createAnimal(String type) {
            Supplier<? extends Animal> constructor = registry.get(type.toLowerCase());
            if (constructor == null) {
                throw new IllegalArgumentException("Unknown animal type: " + type);
            }
            return constructor.get();
        }
        
        public void listAvailableTypes() {
            System.out.println("Available animals: " + registry.keySet());
        }
    }
    
    public static void main(String[] args) {
        AnimalFactory factory = new AnimalFactory();
        factory.listAvailableTypes();
        
        String[] animalTypes = {"dog", "cat", "bird", "dog", "cat"};
        
        System.out.println("\nCreating and using animals:");
        for (String type : animalTypes) {
            Animal animal = factory.createAnimal(type);
            System.out.print(type.toUpperCase() + " says: ");
            animal.makeSound();
        }
    }
}
```

### Example 6: Custom Object Creation Pipeline

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectCreationPipeline {
    
    public static class Product {
        private String name;
        private double price;
        private String category;
        
        public Product(String name) {
            this(name, 0.0, "Uncategorized");
        }
        
        public Product(String name, double price) {
            this(name, price, "Uncategorized");
        }
        
        public Product(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public void setPrice(double price) { this.price = price; }
        public void setCategory(String category) { this.category = category; }
        
        @Override
        public String toString() {
            return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
        }
    }
    
    public static class ProductBuilder {
        private String name;
        private double price = 0.0;
        private String category = "General";
        
        public ProductBuilder(String name) {
            this.name = name;
        }
        
        public ProductBuilder withPrice(double price) {
            this.price = price;
            return this;
        }
        
        public ProductBuilder withCategory(String category) {
            this.category = category;
            return this;
        }
        
        public Product build() {
            return new Product(name, price, category);
        }
    }
    
    public static void main(String[] args) {
        // Simple creation pipeline
        List<String> productNames = Arrays.asList("Laptop", "Mouse", "Keyboard", "Monitor");
        
        List<Product> simpleProducts = productNames.stream()
            .map(Product::new)
            .collect(Collectors.toList());
        System.out.println("Simple Products: " + simpleProducts);
        
        // Using BiFunction for creation with price
        BiFunction<String, Double, Product> productFactory = Product::new;
        Product p1 = productFactory.apply("Headphones", 79.99);
        Product p2 = productFactory.apply("Speaker", 49.99);
        System.out.println("\nProducts with Price:");
        System.out.println(p1);
        System.out.println(p2);
        
        // Builder pattern with constructor reference
        Function<String, ProductBuilder> builderFactory = ProductBuilder::new;
        Product builtProduct = builderFactory
            .apply("Gaming Chair")
            .withPrice(299.99)
            .withCategory("Furniture")
            .build();
        System.out.println("\nBuilt Product: " + builtProduct);
    }
}
```

### Example 7: Supplier-based Object Creation

```java
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

public class SupplierBasedCreation {
    
    public static class Connection {
        private static int connectionCount = 0;
        private int id;
        
        public Connection() {
            this.id = ++connectionCount;
            System.out.println("Connection " + id + " created");
        }
        
        public void close() {
            System.out.println("Connection " + id + " closed");
        }
        
        @Override
        public String toString() {
            return "Connection{id=" + id + '}';
        }
    }
    
    public static class ConnectionPool {
        private List<Connection> availableConnections = new ArrayList<>();
        private Supplier<Connection> factory;
        private int poolSize;
        
        public ConnectionPool(int poolSize) {
            this.poolSize = poolSize;
            this.factory = Connection::new;
            initializePool();
        }
        
        private void initializePool() {
            for (int i = 0; i < poolSize; i++) {
                availableConnections.add(factory.get());
            }
        }
        
        public Connection getConnection() {
            if (availableConnections.isEmpty()) {
                return factory.get();
            }
            return availableConnections.remove(0);
        }
        
        public void releaseConnection(Connection conn) {
            availableConnections.add(conn);
        }
        
        public int getAvailableConnections() {
            return availableConnections.size();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Creating connection pool with 3 connections:\n");
        ConnectionPool pool = new ConnectionPool(3);
        
        System.out.println("\nAvailable connections: " + pool.getAvailableConnections());
        
        Connection conn1 = pool.getConnection();
        System.out.println("After getting one: " + pool.getAvailableConnections());
        
        Connection conn2 = pool.getConnection();
        System.out.println("After getting another: " + pool.getAvailableConnections());
        
        pool.releaseConnection(conn1);
        System.out.println("After releasing one: " + pool.getAvailableConnections());
    }
}
```

## Comparison with Lambda Expressions

### Comparison Table

| Aspect | Lambda Expression | Constructor Reference |
|--------|------------------|----------------------|
| Syntax | `name -> new Person(name)` | `Person::new` |
| Readability | Verbose | Concise |
| Intent Clarity | Less clear intention | Clearly indicates object creation |
| Use Case | Custom creation logic | Standard object instantiation |
| Performance | Comparable | Comparable |

## Use Cases

1. **Factory Pattern**: Creating objects through factory methods
2. **Stream Operations**: Mapping data to objects (e.g., `stream.map(Person::new)`)
3. **Collection Creation**: Collecting streams into custom collections
4. **Dependency Injection**: Creating instances in frameworks
5. **Object Pooling**: Creating pools of objects
6. **Array Creation**: Creating arrays of specific types
7. **Builder Patterns**: Integrating with builder implementations

## Best Practices

1. **Use for Simple Creation**: Use constructor references when instantiation is straightforward
2. **Clear Constructor Signatures**: Ensure constructors match the functional interface signature
3. **Document Complex Creation**: Add comments for complex object creation scenarios
4. **Prefer Constructor References**: When applicable, prefer them over lambda expressions for clarity
5. **Combine with Streams**: Leverage constructor references with stream operations
6. **Consider Immutability**: Use constructor references with immutable objects
7. **Performance Aware**: Constructor references have the same performance as lambda expressions
8. **Avoid Overloading Confusion**: When overloading constructors, ensure type clarity

## Summary

Constructor references provide a powerful and concise way to instantiate objects within functional programming contexts. They are particularly useful with streams, factories, and supplier patterns. By understanding when and how to use constructor references, you can write cleaner, more expressive Java code that clearly communicates the intent of creating new objects.
