# Generics in Java

## Introduction

Generics enable writing type-safe, reusable code by allowing classes and methods to work with different data types while maintaining compile-time type checking. Introduced in Java 5, generics eliminate the need for unchecked casts and provide compile-time detection of type errors. Without generics, collections would require casting every element retrieved, making code verbose and error-prone.

Generics use type parameters—placeholders for concrete types that are specified when the generic type is instantiated. This mechanism enables the same code to work safely with different data types while the compiler ensures type consistency. Understanding type erasure, wildcards, and bounded type parameters is essential for writing effective generic code.

## Generic Basics

A generic type is declared by adding type parameters in angle brackets: `ClassName<TypeParameter>`. Type parameters are conventionally single uppercase letters (T for Type, E for Element, K/V for Key/Value).

### Type Parameters

- `<T>`: Generic type for any class
- `<E>`: Generic type for collection elements
- `<K, V>`: Multiple type parameters
- `<T extends Number>`: Bounded type parameter

### Benefits of Generics

1. **Type Safety**: Catch type errors at compile-time
2. **Elimination of Casts**: No need to cast retrieved objects
3. **Code Reusability**: Single implementation for multiple types
4. **Better IDE Support**: Autocomplete and type checking

## Type Erasure

Type parameters exist only during compilation. At runtime, they're replaced with their bounds or `Object`, creating a single compiled class. This "type erasure" enables backward compatibility with pre-generics code but has limitations.

### Erasure Rules

1. Replace type parameters with their bounds
2. Insert casts where needed
3. Generate synthetic methods to preserve polymorphism

### Consequences

- Cannot create arrays of generic types
- Cannot use instanceof with generic types
- Cannot create instances of type parameters
- Type information is lost at runtime

## Wildcards

Wildcards provide flexibility when the exact type isn't known. They enable working with unknown generic types.

### Wildcard Types

- `<?>`: Unbounded wildcard (any type)
- `<? extends Type>`: Upper bounded (Type or subtypes)
- `<? super Type>`: Lower bounded (Type or supertypes)

### PECS Principle

**Producer Extends, Consumer Super**
- Use `extends` when reading from a generic (producer)
- Use `super` when writing to a generic (consumer)

## Bounded Type Parameters

Bounded type parameters restrict which types can be used as type arguments.

### Syntax

- `<T extends Number>`: T must be Number or subclass
- `<T extends Number & Comparable>`: T must extend Number and implement Comparable

## Best Practices

1. **Use generics consistently** - Don't mix generic and raw types
2. **Avoid raw types** - Always provide type arguments
3. **Use wildcards appropriately** - Follow PECS principle
4. **Understand type erasure** - Remember type information is lost at runtime
5. **Don't create arrays of generics** - Use collections instead
6. **Be specific with bounds** - Constrain types appropriately
7. **Name type parameters clearly** - Use conventional names (T, E, K, V)

---

## Complete Working Examples

### Example 1: Generic Classes and Interfaces

```java
import java.util.*;

public class GenericBox<T> {
    private T contents;
    
    public void put(T value) {
        this.contents = value;
    }
    
    public T get() {
        return contents;
    }
    
    public void display() {
        System.out.println("Contents: " + contents);
        System.out.println("Type: " + contents.getClass().getSimpleName());
    }
}

interface Processor<T> {
    T process(T input);
}

class DoubleProcessor implements Processor<Integer> {
    @Override
    public Integer process(Integer input) {
        return input * 2;
    }
}

class StringReverser implements Processor<String> {
    @Override
    public String process(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}

public class GenericClassesDemo {
    
    public static void main(String[] args) {
        // Generic Box with String
        GenericBox<String> stringBox = new GenericBox<>();
        stringBox.put("Hello Generics");
        System.out.println("String Box:");
        stringBox.display();
        
        // Generic Box with Integer
        GenericBox<Integer> intBox = new GenericBox<>();
        intBox.put(42);
        System.out.println("\nInteger Box:");
        intBox.display();
        
        // Generic Box with List
        GenericBox<List<String>> listBox = new GenericBox<>();
        listBox.put(Arrays.asList("Java", "Generics", "Example"));
        System.out.println("\nList Box:");
        listBox.display();
        
        // Generic Processors
        System.out.println("\n=== Generic Processors ===");
        Processor<Integer> doubler = new DoubleProcessor();
        System.out.println("Double of 21: " + doubler.process(21));
        
        Processor<String> reverser = new StringReverser();
        System.out.println("Reverse of 'Generics': " + reverser.process("Generics"));
    }
}
```

**Output:**
```
String Box:
Contents: Hello Generics
Type: String

Integer Box:
Contents: 42
Type: Integer

List Box:
Contents: [Java, Generics, Example]
Type: ArrayList

=== Generic Processors ===
Double of 21: 42
Reverse of 'Generics': scireineG
```

### Example 2: Generic Methods

```java
import java.util.*;

public class GenericMethods {
    
    // Simple generic method
    public static <T> void printArray(T[] array) {
        System.out.println("Array contents:");
        for (T element : array) {
            System.out.println("  " + element);
        }
    }
    
    // Generic method with return type
    public static <T> T getRandomElement(T[] array) {
        if (array.length == 0) return null;
        int randomIndex = (int) (Math.random() * array.length);
        return array[randomIndex];
    }
    
    // Generic method with bounded type parameter
    public static <T extends Number> double sum(T[] numbers) {
        double sum = 0;
        for (T number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }
    
    // Generic method with multiple type parameters
    public static <K, V> void printMap(K[] keys, V[] values) {
        System.out.println("Key-Value pairs:");
        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            System.out.println("  " + keys[i] + " -> " + values[i]);
        }
    }
    
    // Generic method that returns a collection
    public static <T> List<T> convertToList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
    
    // Generic method with upper bound
    public static <T extends Comparable<T>> T findMax(T[] array) {
        if (array.length == 0) return null;
        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }
        return max;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Print Array ===");
        String[] strings = {"Java", "Generics", "Methods"};
        printArray(strings);
        
        System.out.println("\n=== Get Random Element ===");
        Integer[] numbers = {10, 20, 30, 40, 50};
        System.out.println("Random element: " + getRandomElement(numbers));
        
        System.out.println("\n=== Sum Numbers ===");
        Double[] doubles = {1.5, 2.5, 3.5, 4.5};
        System.out.println("Sum: " + sum(doubles));
        
        System.out.println("\n=== Print Map ===");
        String[] keys = {"A", "B", "C"};
        Integer[] values = {100, 200, 300};
        printMap(keys, values);
        
        System.out.println("\n=== Convert to List ===");
        String[] fruits = {"Apple", "Banana", "Cherry"};
        List<String> fruitList = convertToList(fruits);
        System.out.println("List: " + fruitList);
        
        System.out.println("\n=== Find Maximum ===");
        System.out.println("Max of numbers: " + findMax(numbers));
        System.out.println("Max of strings: " + findMax(strings));
    }
}
```

**Output:**
```
=== Print Array ===
Array contents:
  Java
  Generics
  Methods

=== Get Random Element ===
Random element: 30

=== Sum Numbers ===
Sum: 12.0

=== Print Map ===
Key-Value pairs:
  A -> 100
  B -> 200
  C -> 300

=== Convert to List ===
List: [Apple, Banana, Cherry]

=== Find Maximum ===
Max of numbers: 50
Max of strings: Java
```

### Example 3: Wildcards and PECS

```java
import java.util.*;

public class WildcardsDemo {
    
    // Producer (extends) - reads from the collection
    public static double sumOfNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }
    
    // Consumer (super) - writes to the collection
    public static void fillWithIntegers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }
    
    // Unbounded wildcard - works with any type
    public static void printListSize(List<?> list) {
        System.out.println("List size: " + list.size());
    }
    
    // Copy using PECS
    public static <T> void copyElements(List<? extends T> source, List<? super T> destination) {
        for (T element : source) {
            destination.add(element);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Producer (Extends) ===");
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Sum of integers: " + sumOfNumbers(integers));
        
        List<Double> doubles = Arrays.asList(1.5, 2.5, 3.5);
        System.out.println("Sum of doubles: " + sumOfNumbers(doubles));
        
        System.out.println("\n=== Consumer (Super) ===");
        List<Number> numbers = new ArrayList<>();
        fillWithIntegers(numbers);
        System.out.println("Numbers list: " + numbers);
        
        System.out.println("\n=== Unbounded Wildcard ===");
        printListSize(integers);
        printListSize(doubles);
        List<String> strings = Arrays.asList("A", "B", "C");
        printListSize(strings);
        
        System.out.println("\n=== Copy Elements ===");
        List<Integer> source = Arrays.asList(10, 20, 30);
        List<Number> dest = new ArrayList<>();
        copyElements(source, dest);
        System.out.println("Destination: " + dest);
    }
}
```

**Output:**
```
=== Producer (Extends) ===
Sum of integers: 15.0
Sum of doubles: 7.5

=== Consumer (Super) ===
Numbers list: [1, 2, 3]

=== Unbounded Wildcard ===
List size: 5
List size: 3
List size: 3

=== Copy Elements ===
Destination: [10, 20, 30]
```

### Example 4: Bounded Type Parameters

```java
import java.util.*;

public class BoundedTypesDemo {
    
    // Single bound
    public static <T extends Number> void printDoubleValue(T number) {
        System.out.println("Value: " + number);
        System.out.println("Double value: " + number.doubleValue());
    }
    
    // Multiple bounds
    public static <T extends Number & Comparable<T>> T findMin(T[] array) {
        if (array.length == 0) return null;
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
        }
        return min;
    }
    
    // Bounded type with collections
    public static <T extends Comparable<T>> List<T> sortList(List<T> list) {
        Collections.sort(list);
        return list;
    }
    
    // Type parameter bounded by another type parameter
    public static <T, S extends T> void process(T parent, S child) {
        System.out.println("Parent: " + parent.getClass().getSimpleName());
        System.out.println("Child: " + child.getClass().getSimpleName());
    }
    
    public static void main(String[] args) {
        System.out.println("=== Single Bound ===");
        printDoubleValue(42);
        printDoubleValue(3.14);
        
        System.out.println("\n=== Multiple Bounds ===");
        Double[] numbers = {5.5, 2.2, 8.8, 1.1, 9.9};
        System.out.println("Min value: " + findMin(numbers));
        
        System.out.println("\n=== Sort with Bounded Type ===");
        List<String> words = new ArrayList<>(Arrays.asList("Zebra", "Apple", "Mango"));
        System.out.println("Original: " + words);
        System.out.println("Sorted: " + sortList(words));
        
        System.out.println("\n=== Type Parameter as Bound ===");
        process(new Number() {
            @Override
            public int intValue() { return 0; }
            @Override
            public long longValue() { return 0; }
            @Override
            public float floatValue() { return 0; }
            @Override
            public double doubleValue() { return 0; }
        }, 42);
    }
}
```

**Output:**
```
=== Single Bound ===
Value: 42
Double value: 42.0
Value: 3.14
Double value: 3.14

=== Multiple Bounds ===
Min value: 1.1

=== Sort with Bounded Type ===
Original: [Zebra, Apple, Mango]
Sorted: [Apple, Mango, Zebra]

=== Type Parameter as Bound ===
Parent: Number
Child: Integer
```

### Example 5: Generic Collections and Type Safety

```java
import java.util.*;

public class GenericCollectionsDemo {
    
    static class Student {
        private int id;
        private String name;
        
        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        @Override
        public String toString() {
            return "Student{" + id + ", " + name + "}";
        }
    }
    
    public static <T> void printElements(Collection<T> collection) {
        System.out.println("Collection size: " + collection.size());
        for (T element : collection) {
            System.out.println("  " + element);
        }
    }
    
    public static <K, V> void printMapEntries(Map<K, V> map) {
        System.out.println("Map entries:");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Type-Safe List ===");
        List<String> stringList = new ArrayList<>();
        stringList.add("Java");
        stringList.add("Generics");
        stringList.add("Collections");
        
        printElements(stringList);
        
        System.out.println("\n=== Type-Safe Set ===");
        Set<Integer> intSet = new HashSet<>();
        intSet.add(10);
        intSet.add(20);
        intSet.add(30);
        
        printElements(intSet);
        
        System.out.println("\n=== Type-Safe Map ===");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        
        printMapEntries(scores);
        
        System.out.println("\n=== Custom Objects ===");
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Alice"));
        students.add(new Student(2, "Bob"));
        students.add(new Student(3, "Charlie"));
        
        printElements(students);
        
        System.out.println("\n=== Nested Generics ===");
        Map<String, List<String>> nestedMap = new HashMap<>();
        nestedMap.put("Fruits", Arrays.asList("Apple", "Banana"));
        nestedMap.put("Vegetables", Arrays.asList("Carrot", "Broccoli"));
        
        System.out.println("Nested Map:");
        for (Map.Entry<String, List<String>> entry : nestedMap.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

**Output:**
```
=== Type-Safe List ===
Collection size: 3
  Java
  Generics
  Collections

=== Type-Safe Set ===
Collection size: 3
  10
  20
  30

=== Type-Safe Map ===
Map entries:
  Alice -> 95
  Bob -> 87
  Charlie -> 92

=== Custom Objects ===
Collection size: 3
  Student{1, Alice}
  Student{2, Bob}
  Student{3, Charlie}

=== Nested Generics ===
Nested Map:
  Fruits: [Apple, Banana]
  Vegetables: [Carrot, Broccoli]
```

## Type Erasure Implications

Generic type information is available at compile time but not at runtime due to type erasure. This means:

- Cannot use `new T()` to create instances
- Cannot use `T[].class` to get the class
- Cannot catch exceptions with generic types
- Method overloading based on type parameters alone is not allowed

## Performance Analysis

Generics have no runtime overhead since type parameters are erased. The generated bytecode contains necessary casts but is efficient.

## Summary

Generics are a cornerstone of modern Java programming, enabling type-safe, reusable code. Understanding generic classes, methods, wildcards, and bounded types enables writing flexible, maintainable code. While type erasure has limitations, proper use of generics prevents ClassCastException and provides compile-time safety. Following PECS principle and using bounded types appropriately leads to clean, professional Java code.
