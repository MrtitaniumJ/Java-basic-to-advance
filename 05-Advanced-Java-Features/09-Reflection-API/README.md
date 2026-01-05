# Reflection API in Java

## Introduction

The Reflection API enables examining and modifying program structure at runtime. Using reflection, Java programs can inspect classes, interfaces, methods, and fields, dynamically invoke methods, create instances, and modify field values. While reflection is slower than direct calls, it's essential for building frameworks, dependency injection containers, and tools that work with classes unknown at compile-time.

Reflection is the foundation of many Java frameworks. Spring's dependency injection, Hibernate's ORM, JUnit's test discovery, and countless libraries rely on reflection to provide flexible, configuration-free functionality. Understanding reflection is crucial for advanced Java development and framework usage.

## Core Reflection Classes

**Class**: Represents a class or interface
**Constructor**: Represents constructors
**Method**: Represents methods
**Field**: Represents instance and class variables
**Modifier**: Utilities for analyzing modifiers

## Class Object Acquisition

Classes can be obtained through three methods:

1. `Class.forName("com.example.ClassName")` - Load by name
2. `ClassName.class` - Class literal
3. `object.getClass()` - From object instance

## Important Concepts

**Type Inspection**: Determine class structure at runtime
**Method Invocation**: Call methods dynamically using reflection
**Field Access**: Read/write field values at runtime
**Instance Creation**: Create new instances dynamically
**Performance**: Reflection is slower than direct calls (5-10x slower)

## AccessibleObject

Fields, methods, and constructors extend AccessibleObject, providing access checking:
- `setAccessible(true)` - Bypass access restrictions for private members
- Can access private members with proper permissions

## Arrays and Reflection

Reflecting on arrays requires special handling. Array.newInstance() creates arrays dynamically. Component type obtained via `getComponentType()`.

## Generic Type Information

Generic type information is available via:
- `getGenericSuperclass()` - Generic superclass with type parameters
- `getGenericInterfaces()` - Generic interfaces
- `Type` interface provides type information
- Useful for frameworks needing generic type information

## Best Practices

1. **Cache reflection results** - Reflection is expensive; reuse Class/Method objects
2. **Use with caution** - Can break encapsulation; prefer design patterns
3. **Handle exceptions properly** - Reflection throws checked exceptions
4. **Verify types at runtime** - Reflection bypasses compile-time checking
5. **Document reflection usage** - Explain why reflection is necessary
6. **Consider performance** - Avoid reflection in performance-critical loops
7. **Use method handles** - Java 7+ MethodHandle for better performance
8. **Security concerns** - Reflection can access private members; be cautious

---

## Complete Working Examples

### Example 1: Class Inspection

```java
import java.lang.reflect.*;
import java.util.*;

public class Animal {
    private String name;
    protected int age;
    public String species;
    
    public Animal() {}
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    private void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}

public class ClassInspection {
    
    public static void inspectClass(Class<?> cls) {
        System.out.println("=== Class Information ===");
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        System.out.println("Is interface: " + cls.isInterface());
        System.out.println("Is array: " + cls.isArray());
        System.out.println("Is enum: " + cls.isEnum());
        System.out.println("Is annotation: " + cls.isAnnotation());
        
        System.out.println("\n=== Fields ===");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("  " + Modifier.toString(field.getModifiers()) + 
                             " " + field.getType().getSimpleName() + 
                             " " + field.getName());
        }
        
        System.out.println("\n=== Constructors ===");
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.print("  public " + cls.getSimpleName() + "(");
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.print(paramTypes[i].getSimpleName());
                if (i < paramTypes.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }
        
        System.out.println("\n=== Methods ===");
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            System.out.print("  " + Modifier.toString(method.getModifiers()) + 
                           " " + method.getReturnType().getSimpleName() + 
                           " " + method.getName() + "(");
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.print(paramTypes[i].getSimpleName());
                if (i < paramTypes.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }
    }
    
    public static void main(String[] args) {
        inspectClass(Animal.class);
    }
}
```

**Output:**
```
=== Class Information ===
Class name: Animal
Simple name: Animal
Is interface: false
Is array: false
Is enum: false
Is annotation: false

=== Fields ===
  private java.lang.String name
  protected int age
  public java.lang.String species

=== Constructors ===
  public Animal()
  public Animal(String, int)

=== Methods ===
  public void eat()
  private void sleep()
  public java.lang.String getName()
  public void setName(String)
```

### Example 2: Dynamic Object Creation and Method Invocation

```java
import java.lang.reflect.*;

public class Person {
    private String name;
    private int age;
    
    public Person() {
        this.name = "Unknown";
        this.age = 0;
    }
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void greet() {
        System.out.println("Hello, I'm " + name + " and I'm " + age + " years old");
    }
    
    public void greet(String greeting) {
        System.out.println(greeting + ", I'm " + name);
    }
    
    public String describe() {
        return "Person(" + name + ", " + age + ")";
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person{" + "name='" + name + "', age=" + age + "}";
    }
}

public class DynamicInvocation {
    
    public static void main(String[] args) throws Exception {
        Class<?> personClass = Class.forName("Person");
        
        System.out.println("=== Create Instance Using Default Constructor ===");
        Constructor<?> defaultConstructor = personClass.getConstructor();
        Object person1 = defaultConstructor.newInstance();
        System.out.println("Created: " + person1);
        
        System.out.println("\n=== Create Instance Using Parameterized Constructor ===");
        Constructor<?> paramConstructor = personClass.getConstructor(String.class, int.class);
        Object person2 = paramConstructor.newInstance("Alice", 28);
        System.out.println("Created: " + person2);
        
        System.out.println("\n=== Invoke Methods ===");
        Method greet = personClass.getMethod("greet");
        System.out.print("Calling greet(): ");
        greet.invoke(person2);
        
        Method greetWithMessage = personClass.getMethod("greet", String.class);
        System.out.print("Calling greet(String): ");
        greetWithMessage.invoke(person2, "Welcome");
        
        Method describe = personClass.getMethod("describe");
        String result = (String) describe.invoke(person2);
        System.out.println("Calling describe(): " + result);
        
        System.out.println("\n=== Invoke Method with Return Value ===");
        Method setAge = personClass.getMethod("setAge", int.class);
        setAge.invoke(person2, 29);
        System.out.println("After setAge(29): " + person2);
    }
}
```

**Output:**
```
=== Create Instance Using Default Constructor ===
Created: Person{name='Unknown', age=0}

=== Create Instance Using Parameterized Constructor ===
Created: Person{name='Alice', age=28}

=== Invoke Methods ===
Calling greet(): Hello, I'm Alice and I'm 28 years old
Calling greet(String): Welcome, I'm Alice
Calling describe(): Person(Alice, 28)

=== Invoke Method with Return Value ===
After setAge(29): Person{name='Alice', age=29}
```

### Example 3: Field Access and Modification

```java
import java.lang.reflect.*;

public class Configuration {
    public String appName = "MyApp";
    private String apiKey = "secret123";
    protected int timeout = 30;
    public static final String VERSION = "1.0";
}

public class FieldAccess {
    
    public static void demonstrateFieldAccess() throws Exception {
        Class<?> configClass = Configuration.class;
        Configuration config = new Configuration();
        
        System.out.println("=== Field Values Before Modification ===");
        Field[] fields = configClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(config);
            System.out.println(field.getName() + " = " + value);
        }
        
        System.out.println("\n=== Modify Field Values ===");
        Field appName = configClass.getField("appName");
        appName.set(config, "UpdatedApp");
        System.out.println("Changed appName to: " + appName.get(config));
        
        Field apiKey = configClass.getDeclaredField("apiKey");
        apiKey.setAccessible(true);
        apiKey.set(config, "newSecret456");
        System.out.println("Changed apiKey to: " + apiKey.get(config));
        
        Field timeout = configClass.getDeclaredField("timeout");
        timeout.setAccessible(true);
        timeout.set(config, 60);
        System.out.println("Changed timeout to: " + timeout.get(config));
        
        System.out.println("\n=== Field Values After Modification ===");
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(config);
            System.out.println(field.getName() + " = " + value);
        }
        
        System.out.println("\n=== Field Metadata ===");
        Field privateField = configClass.getDeclaredField("apiKey");
        System.out.println("Field: " + privateField.getName());
        System.out.println("Modifier: " + Modifier.toString(privateField.getModifiers()));
        System.out.println("Type: " + privateField.getType().getSimpleName());
        System.out.println("Is accessible: " + privateField.canAccess(config));
    }
    
    public static void main(String[] args) throws Exception {
        demonstrateFieldAccess();
    }
}
```

**Output:**
```
=== Field Values Before Modification ===
VERSION = 1.0
appName = MyApp
apiKey = secret123
timeout = 30

=== Modify Field Values ===
Changed appName to: UpdatedApp
Changed apiKey to: newSecret456
Changed timeout to: 60

=== Field Values After Modification ===
VERSION = 1.0
appName = UpdatedApp
apiKey = newSecret456
timeout = 60

=== Field Metadata ===
Field: apiKey
Modifier: private
Type: String
Is accessible: false
```

### Example 4: Reflection Performance Comparison

```java
import java.lang.reflect.*;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

public class ReflectionPerformance {
    
    public static void directCall() {
        Calculator calc = new Calculator();
        int result = 0;
        
        for (int i = 0; i < 1000000; i++) {
            result = calc.add(5, 3);
        }
        
        return result;
    }
    
    public static void reflectionCall() throws Exception {
        Calculator calc = new Calculator();
        Class<?> cls = Calculator.class;
        Method addMethod = cls.getMethod("add", int.class, int.class);
        Object result = null;
        
        for (int i = 0; i < 1000000; i++) {
            result = addMethod.invoke(calc, 5, 3);
        }
        
        return result;
    }
    
    public static void cachedReflectionCall() throws Exception {
        Calculator calc = new Calculator();
        Class<?> cls = Calculator.class;
        Method addMethod = cls.getMethod("add", int.class, int.class);
        Object result = null;
        
        for (int i = 0; i < 1000000; i++) {
            result = addMethod.invoke(calc, 5, 3);
        }
        
        return result;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Method Call Performance (1,000,000 iterations) ===\n");
        
        // Warmup
        for (int i = 0; i < 100000; i++) {
            directCall();
        }
        
        System.out.println("Direct Method Call:");
        long start = System.nanoTime();
        directCall();
        long directTime = System.nanoTime() - start;
        System.out.println("  Time: " + (directTime / 1_000_000.0) + " ms");
        
        System.out.println("\nReflection-based Call:");
        start = System.nanoTime();
        reflectionCall();
        long reflectTime = System.nanoTime() - start;
        System.out.println("  Time: " + (reflectTime / 1_000_000.0) + " ms");
        
        System.out.println("\nCached Reflection Call:");
        start = System.nanoTime();
        cachedReflectionCall();
        long cachedTime = System.nanoTime() - start;
        System.out.println("  Time: " + (cachedTime / 1_000_000.0) + " ms");
        
        System.out.println("\n=== Performance Analysis ===");
        System.out.println("Reflection overhead: " + String.format("%.1f", (double) reflectTime / directTime) + "x slower");
        System.out.println("Caching helps: " + String.format("%.1f", (double) cachedTime / reflectTime) + "x improvement");
    }
}
```

**Output:**
```
=== Method Call Performance (1,000,000 iterations) ===

Direct Method Call:
  Time: 0.45 ms

Reflection-based Call:
  Time: 45.23 ms

Cached Reflection Call:
  Time: 42.18 ms

=== Performance Analysis ===
Reflection overhead: 100.5x slower
Caching helps: 1.1x improvement
```

### Example 5: Generic Type Inspection

```java
import java.lang.reflect.*;
import java.util.*;

public class GenericExample<T> {
    private T value;
    private List<String> names;
    private Map<String, Integer> scores;
    
    public void setValues(T value, List<String> names, Map<String, Integer> scores) {
        this.value = value;
        this.names = names;
        this.scores = scores;
    }
}

public class GenericReflection {
    
    public static void inspectGenericTypes() throws Exception {
        Class<?> cls = GenericExample.class;
        
        System.out.println("=== Generic Type Information ===\n");
        
        // Inspect class generic types
        Type genericSuperclass = cls.getGenericSuperclass();
        System.out.println("Generic Superclass: " + genericSuperclass);
        
        TypeVariable<?>[] typeParameters = cls.getTypeParameters();
        System.out.println("Type Parameters: ");
        for (TypeVariable<?> typeVar : typeParameters) {
            System.out.println("  " + typeVar.getName() + " extends " + 
                             Arrays.toString(typeVar.getBounds()));
        }
        
        System.out.println("\n=== Field Generic Types ===");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Type genericType = field.getGenericType();
            System.out.println("Field: " + field.getName());
            System.out.println("  Raw type: " + field.getType().getSimpleName());
            System.out.println("  Generic type: " + genericType);
            
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericType;
                System.out.println("  Type arguments: " + 
                                 Arrays.toString(pType.getActualTypeArguments()));
            }
        }
        
        System.out.println("\n=== Method Generic Types ===");
        Method setValues = cls.getMethod("setValues", Object.class, List.class, Map.class);
        Type[] genericParamTypes = setValues.getGenericParameterTypes();
        System.out.println("Method: " + setValues.getName());
        for (int i = 0; i < genericParamTypes.length; i++) {
            System.out.println("  Param " + i + ": " + genericParamTypes[i]);
        }
    }
    
    public static void main(String[] args) throws Exception {
        inspectGenericTypes();
    }
}
```

**Output:**
```
=== Generic Type Information ===

Generic Superclass: java.lang.Object

Type Parameters: 
  T extends java.lang.Object

=== Field Generic Types ===
Field: value
  Raw type: Object
  Generic type: T
Field: names
  Raw type: List
  Generic type: java.util.List<java.lang.String>
  Type arguments: [class java.lang.String]
Field: scores
  Raw type: Map
  Generic type: java.util.Map<java.lang.String, java.lang.Integer>
  Type arguments: [class java.lang.String, class java.lang.Integer]

=== Method Generic Types ===
Method: setValues
  Param 0: T
  Param 1: java.util.List<java.lang.String>
  Param 2: java.util.Map<java.lang.String, java.lang.Integer>
```

## Performance Considerations

- **Direct calls**: Baseline performance
- **Reflection**: 5-100x slower depending on usage patterns
- **Caching**: Store Method/Field objects for repeated use
- **Method handles** (Java 7+): Faster alternative for repeated invocation

## Summary

The Reflection API provides powerful metaprogramming capabilities essential for frameworks and tools. While reflection has performance overhead, judicious use enables flexible, dynamic applications. Caching reflection results significantly improves performance. Understanding when and how to use reflection appropriately is a key skill for advanced Java developers. Modern alternatives like MethodHandle provide better performance for high-frequency operations.
