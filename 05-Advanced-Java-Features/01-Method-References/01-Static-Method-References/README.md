# Static Method References

## Overview

Static method references are a concise way to refer to static methods in Java. They provide a shorthand notation for functional interfaces that can be satisfied by static methods, eliminating the need to write explicit lambda expressions when you simply want to call a static method.

## Definition

A static method reference is a reference to a static method of a class. It uses the double colon (`::`) operator to point directly to an existing static method, allowing the method to be used as a functional interface implementation without creating an anonymous inner class or lambda expression.

## Syntax

```java
ClassName::staticMethodName
```

The syntax consists of three parts:
- **ClassName**: The class containing the static method
- **::** (Double Colon Operator): The method reference operator
- **staticMethodName**: The name of the static method to reference

## Practical Examples

### Example 1: Basic Static Method Reference

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class StaticMethodReferenceExample {
    
    // Static method to convert string to integer
    public static Integer parseInteger(String str) {
        return Integer.parseInt(str);
    }
    
    public static void main(String[] args) {
        List<String> numbers = Arrays.asList("10", "20", "30", "40", "50");
        
        // Using lambda expression
        List<Integer> lambdaResult = numbers.stream()
            .map(str -> Integer.parseInt(str))
            .toList();
        System.out.println("Lambda Result: " + lambdaResult);
        
        // Using static method reference
        List<Integer> methodRefResult = numbers.stream()
            .map(Integer::parseInt)
            .toList();
        System.out.println("Method Reference Result: " + methodRefResult);
    }
}
```

### Example 2: Custom Static Method Reference

```java
import java.util.Arrays;
import java.util.List;

public class MathOperationsExample {
    
    // Static utility methods
    public static double calculateSquareRoot(double number) {
        return Math.sqrt(number);
    }
    
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
    
    public static int compareNumbers(int a, int b) {
        return Integer.compare(a, b);
    }
    
    public static void main(String[] args) {
        List<Double> values = Arrays.asList(16.0, 25.0, 36.0, 49.0, 64.0);
        
        // Static method reference with Math::sqrt
        System.out.println("Square Roots:");
        values.forEach(value -> System.out.println(
            "√" + value + " = " + calculateSquareRoot(value)));
        
        // Static method reference with custom method
        List<Double> prices = Arrays.asList(99.99, 149.50, 199.99, 299.00);
        System.out.println("\nFormatted Prices:");
        prices.forEach(price -> System.out.println(formatCurrency(price)));
    }
}
```

### Example 3: Static Method References with Comparators

```java
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComparatorExample {
    
    public static class Employee {
        private String name;
        private double salary;
        
        public Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return "Employee{" + "name='" + name + '\'' + ", salary=" + salary + '}';
        }
    }
    
    // Static method for comparing employees by salary
    public static int compareBySalary(Employee e1, Employee e2) {
        return Double.compare(e1.getSalary(), e2.getSalary());
    }
    
    // Static method for comparing employees by name
    public static int compareByName(Employee e1, Employee e2) {
        return e1.getName().compareTo(e2.getName());
    }
    
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", 75000),
            new Employee("Bob Smith", 65000),
            new Employee("Charlie Brown", 80000),
            new Employee("Diana Prince", 70000)
        );
        
        // Using static method reference for sorting by salary
        System.out.println("Sorted by Salary:");
        employees.stream()
            .sorted(ComparatorExample::compareBySalary)
            .forEach(System.out::println);
        
        // Using static method reference for sorting by name
        System.out.println("\nSorted by Name:");
        employees.stream()
            .sorted(ComparatorExample::compareByName)
            .forEach(System.out::println);
    }
}
```

### Example 4: Static Method References with Filtering

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilteringExample {
    
    // Static utility methods for validation
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
    
    public static boolean isPositive(int number) {
        return number > 0;
    }
    
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        
        // Filter even numbers using static method reference
        List<Integer> evenNumbers = numbers.stream()
            .filter(FilteringExample::isEven)
            .collect(Collectors.toList());
        System.out.println("Even Numbers: " + evenNumbers);
        
        // Filter positive numbers using static method reference
        List<Integer> positiveNumbers = numbers.stream()
            .filter(FilteringExample::isPositive)
            .collect(Collectors.toList());
        System.out.println("Positive Numbers: " + positiveNumbers);
        
        // Filter prime numbers using static method reference
        List<Integer> primeNumbers = numbers.stream()
            .filter(FilteringExample::isPrime)
            .collect(Collectors.toList());
        System.out.println("Prime Numbers: " + primeNumbers);
    }
}
```

### Example 5: Static Method References with Function Chains

```java
import java.util.function.Function;

public class FunctionChainingExample {
    
    // Static utility methods
    public static Integer convertToInt(String str) {
        return Integer.parseInt(str);
    }
    
    public static Integer multiplyByTwo(Integer num) {
        return num * 2;
    }
    
    public static Integer addTen(Integer num) {
        return num + 10;
    }
    
    public static String convertToString(Integer num) {
        return "Result: " + num;
    }
    
    public static void main(String[] args) {
        // Create function chain using static method references
        Function<String, Integer> parseFunc = FunctionChainingExample::convertToInt;
        Function<Integer, Integer> doubleFunc = FunctionChainingExample::multiplyByTwo;
        Function<Integer, Integer> addFunc = FunctionChainingExample::addTen;
        Function<Integer, String> stringFunc = FunctionChainingExample::convertToString;
        
        // Chain operations: "5" -> 5 -> 10 -> 20 -> "Result: 20"
        String result = parseFunc
            .andThen(doubleFunc)
            .andThen(addFunc)
            .andThen(stringFunc)
            .apply("5");
        
        System.out.println(result);
        
        // Demonstrate intermediate results
        System.out.println("\nStep-by-step execution:");
        System.out.println("1. Parse: " + parseFunc.apply("5"));
        System.out.println("2. Multiply by 2: " + doubleFunc.apply(parseFunc.apply("5")));
        System.out.println("3. Add 10: " + addFunc.apply(20));
        System.out.println("4. Convert to String: " + stringFunc.apply(30));
    }
}
```

## Comparison with Lambda Expressions

### When to Use Static Method References

Static method references are preferred over lambda expressions when:
- You have an existing static method that exactly matches the functional interface
- You want more readable and concise code
- You're calling a well-known library method (e.g., `Integer::parseInt`)

### Comparison Table

| Aspect | Lambda Expression | Static Method Reference |
|--------|------------------|------------------------|
| Syntax | `str -> Integer.parseInt(str)` | `Integer::parseInt` |
| Readability | More verbose | More concise |
| Reusability | Limited to that context | References reusable method |
| Performance | Comparable | Comparable |
| Use Case | Custom logic | Existing method |

## Use Cases

1. **Stream Processing**: Transforming or filtering data using standard utility methods
2. **Comparators**: Sorting collections with static comparison methods
3. **Data Validation**: Filtering data based on static validation methods
4. **Type Conversion**: Converting between different types using static methods
5. **Mathematical Operations**: Performing calculations with static math methods
6. **String Operations**: Processing strings with static utility methods

## Best Practices

1. **Clarity First**: Only use method references if they make the code more readable
2. **Familiar Methods**: Use method references for well-known methods like `Integer::parseInt`
3. **Meaningful Names**: Ensure static method names are descriptive and clear
4. **Documentation**: Document static methods that will be used as method references
5. **Consistency**: Use method references consistently throughout your codebase
6. **Performance Considerations**: Method references have minimal performance overhead
7. **Avoid Overuse**: Don't force method references where lambda expressions are clearer

## Summary

Static method references provide a concise and elegant way to reference static methods in functional programming contexts. They improve code readability when used appropriately and make streams and functional operations more expressive. Understanding when and how to use static method references is essential for modern Java development.
