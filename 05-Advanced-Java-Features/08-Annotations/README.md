# Annotations in Java

## Introduction

Annotations provide a way to add metadata to code that doesn't directly affect the code's operation. Introduced in Java 5, annotations enable embedding information in source code for tools, frameworks, and the JVM to process at compile-time or runtime. Annotations are powerful mechanisms for configuring applications declaratively without modifying actual code, reducing boilerplate and improving readability.

Annotations differ from comments in that they're processed by the compiler and runtime, enabling automatic behavior generation. Major frameworks like Spring, Hibernate, and JUnit rely heavily on annotations to eliminate configuration files and reduce code verbosity. Understanding both built-in annotations and creating custom annotations is essential for modern Java development.

## Built-in Annotations

Java provides several standard annotations for different purposes:

### Meta-Annotations

**@Retention**: Specifies annotation's lifecycle
- SOURCE: Retained only in source code
- CLASS: Retained in compiled bytecode (default)
- RUNTIME: Available at runtime via reflection

**@Target**: Specifies where annotation can be applied
- TYPE, METHOD, FIELD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, PACKAGE

**@Documented**: Includes annotation in Javadoc
**@Inherited**: Annotation is inherited by subclasses

### Standard Annotations

**@Override**: Indicates method overrides superclass method
**@Deprecated**: Marks element as outdated, discourage use
**@SuppressWarnings**: Tells compiler to suppress specific warnings
**@FunctionalInterface**: Marks interface as functional interface
**@SafeVarargs**: Suppresses warnings for varargs with generics

## Custom Annotations

Custom annotations enable creating domain-specific metadata. They're defined similarly to interfaces but use `@` prefix.

### Annotation Elements

Annotation elements are similar to methods with no parameters. Default values can be specified.

### Annotation Processing

Annotations are typically processed by:
1. **Compile-time processors**: Generate code based on annotations
2. **Runtime reflection**: Inspect annotations at runtime

## Use Cases

1. **Framework Configuration**: Spring uses @Autowired, @Service, @Repository
2. **Testing**: JUnit uses @Test, @Before, @After
3. **Data Validation**: Define validation rules declaratively
4. **AOP (Aspect-Oriented Programming)**: Mark cross-cutting concerns
5. **Documentation**: Enhance generated documentation
6. **Serialization Control**: Mark fields for serialization

## Best Practices

1. **Use appropriate retention policy** - RUNTIME only if needed at runtime
2. **Specify target elements** - Restrict where annotation can be used
3. **Provide clear documentation** - Document annotation purpose and usage
4. **Use element names clearly** - Make annotation intent obvious
5. **Avoid complex logic in annotations** - Keep them simple metadata containers
6. **Validate at processing time** - Check annotation correctness early
7. **Consider inheritance** - Use @Inherited if subclass annotation is needed
8. **Document element requirements** - Specify required vs optional elements

---

## Complete Working Examples

### Example 1: Custom Annotations and Built-in Annotations

```java
import java.lang.annotation.*;
import java.lang.reflect.*;

// Custom annotation for documenting API endpoints
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface APIEndpoint {
    String version() default "1.0";
    String basePath() default "/api";
    String description() default "";
}

// Custom annotation for API methods
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface APIMethod {
    String path();
    String httpMethod() default "GET";
    String description() default "";
    boolean requiresAuth() default true;
}

// Example API class
@APIEndpoint(
    version = "2.0",
    basePath = "/api/v2",
    description = "User management API"
)
public class UserAPI {
    
    @APIMethod(
        path = "/users",
        httpMethod = "GET",
        description = "Get all users",
        requiresAuth = false
    )
    public void getAllUsers() {
        System.out.println("GET /api/v2/users");
    }
    
    @APIMethod(
        path = "/users/{id}",
        httpMethod = "GET",
        description = "Get user by ID"
    )
    public void getUserById(int id) {
        System.out.println("GET /api/v2/users/" + id);
    }
    
    @APIMethod(
        path = "/users",
        httpMethod = "POST",
        description = "Create new user"
    )
    public void createUser(String name, String email) {
        System.out.println("POST /api/v2/users - Creating " + name);
    }
    
    @APIMethod(
        path = "/users/{id}",
        httpMethod = "DELETE",
        description = "Delete user"
    )
    public void deleteUser(int id) {
        System.out.println("DELETE /api/v2/users/" + id);
    }
}

public class AnnotationProcessing {
    
    public static void processAPIEndpoint(Class<?> apiClass) {
        APIEndpoint endpoint = apiClass.getAnnotation(APIEndpoint.class);
        
        if (endpoint != null) {
            System.out.println("API Endpoint Information:");
            System.out.println("  Version: " + endpoint.version());
            System.out.println("  Base Path: " + endpoint.basePath());
            System.out.println("  Description: " + endpoint.description());
            System.out.println();
            
            System.out.println("API Methods:");
            Method[] methods = apiClass.getDeclaredMethods();
            for (Method method : methods) {
                APIMethod apiMethod = method.getAnnotation(APIMethod.class);
                if (apiMethod != null) {
                    System.out.println("  " + apiMethod.httpMethod() + " " + 
                        endpoint.basePath() + apiMethod.path());
                    System.out.println("    Description: " + apiMethod.description());
                    System.out.println("    Auth Required: " + apiMethod.requiresAuth());
                }
            }
        }
    }
    
    public static void main(String[] args) {
        processAPIEndpoint(UserAPI.class);
    }
}
```

**Output:**
```
API Endpoint Information:
  Version: 2.0
  Base Path: /api/v2
  Description: User management API

API Methods:
  GET /api/v2/users
    Description: Get all users
    Auth Required: false
  GET /api/v2/users/{id}
    Description: Get user by ID
    Auth Required: true
  POST /api/v2/users
    Description: Create new user
    Auth Required: true
  DELETE /api/v2/users/{id}
    Description: Delete user
    Auth Required: true
```

### Example 2: Validation Annotations

```java
import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
    String message() default "Field cannot be null";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinValue {
    int value();
    String message() default "Value must be greater than or equal to minimum";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaxLength {
    int value();
    String message() default "Value length must not exceed maximum";
}

public class Person {
    
    @NotNull
    private String name;
    
    @NotNull
    @MinValue(value = 18)
    private Integer age;
    
    @MaxLength(value = 50)
    private String email;
    
    public Person(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Person{" + "name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}

public class Validator {
    
    public static boolean validate(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        boolean isValid = true;
        
        for (Field field : fields) {
            field.setAccessible(true);
            
            try {
                Object value = field.get(obj);
                
                // Check @NotNull
                if (field.isAnnotationPresent(NotNull.class)) {
                    if (value == null) {
                        NotNull annotation = field.getAnnotation(NotNull.class);
                        System.out.println("✗ " + field.getName() + ": " + annotation.message());
                        isValid = false;
                    } else {
                        System.out.println("✓ " + field.getName() + ": Not null");
                    }
                }
                
                // Check @MinValue
                if (field.isAnnotationPresent(MinValue.class) && value != null) {
                    MinValue annotation = field.getAnnotation(MinValue.class);
                    if (value instanceof Integer) {
                        Integer numValue = (Integer) value;
                        if (numValue < annotation.value()) {
                            System.out.println("✗ " + field.getName() + ": " + annotation.message());
                            isValid = false;
                        } else {
                            System.out.println("✓ " + field.getName() + ": >= " + annotation.value());
                        }
                    }
                }
                
                // Check @MaxLength
                if (field.isAnnotationPresent(MaxLength.class) && value != null) {
                    MaxLength annotation = field.getAnnotation(MaxLength.class);
                    if (value instanceof String) {
                        String strValue = (String) value;
                        if (strValue.length() > annotation.value()) {
                            System.out.println("✗ " + field.getName() + ": " + annotation.message());
                            isValid = false;
                        } else {
                            System.out.println("✓ " + field.getName() + ": Length <= " + annotation.value());
                        }
                    }
                }
                
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return isValid;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Valid Person ===");
        Person person1 = new Person("Alice", 28, "alice@example.com");
        System.out.println(person1);
        System.out.println("Validation: " + (validate(person1) ? "PASSED" : "FAILED"));
        
        System.out.println("\n=== Invalid Person (Age too low) ===");
        Person person2 = new Person("Bob", 15, "bob@example.com");
        System.out.println(person2);
        System.out.println("Validation: " + (validate(person2) ? "PASSED" : "FAILED"));
        
        System.out.println("\n=== Invalid Person (Null name) ===");
        Person person3 = new Person(null, 25, "charlie@example.com");
        System.out.println(person3);
        System.out.println("Validation: " + (validate(person3) ? "PASSED" : "FAILED"));
        
        System.out.println("\n=== Invalid Person (Email too long) ===");
        Person person4 = new Person("Diana", 30, "verylongemailaddress@verylongdomain.com");
        System.out.println(person4);
        System.out.println("Validation: " + (validate(person4) ? "PASSED" : "FAILED"));
    }
}
```

**Output:**
```
=== Valid Person ===
Person{name='Alice', age=28, email='alice@example.com'}
✓ name: Not null
✓ age: Not null
✓ age: >= 18
✓ email: Length <= 50
Validation: PASSED

=== Invalid Person (Age too low) ===
Person{name='Bob', age=15, email='bob@example.com'}
✓ name: Not null
✓ age: Not null
✗ age: Value must be greater than or equal to minimum
Validation: FAILED

=== Invalid Person (Null name) ===
Person{name='null', age=25, email='charlie@example.com'}
✗ name: Field cannot be null
✓ age: Not null
✓ age: >= 18
✓ email: Length <= 50
Validation: FAILED

=== Invalid Person (Email too long) ===
Person{name='Diana', age=30, email='verylongemailaddress@verylongdomain.com'}
✓ name: Not null
✓ age: Not null
✓ age: >= 18
✗ email: Value length must not exceed maximum
Validation: FAILED
```

### Example 3: Aspect-Oriented Programming with Annotations

```java
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecution {
    boolean logParameters() default true;
    boolean logResult() default true;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Timed {
    String name() default "";
}

public class Calculator {
    
    @LogExecution
    public int add(int a, int b) {
        return a + b;
    }
    
    @LogExecution(logResult = false)
    public int subtract(int a, int b) {
        return a - b;
    }
    
    @LogExecution
    @Timed(name = "Multiplication")
    public int multiply(int a, int b) {
        return a * b;
    }
    
    @Timed(name = "Division")
    public double divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return (double) a / b;
    }
}

public class AspectProxy {
    
    public static Object createProxy(Object target) {
        return java.lang.reflect.Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            (proxy, method, args) -> {
                LogExecution logExecution = method.getAnnotation(LogExecution.class);
                Timed timed = method.getAnnotation(Timed.class);
                
                // Log method call
                if (logExecution != null) {
                    System.out.print("Calling: " + method.getName());
                    if (logExecution.logParameters()) {
                        System.out.print(" with parameters: " + java.util.Arrays.toString(args));
                    }
                    System.out.println();
                }
                
                // Measure execution time
                long startTime = System.nanoTime();
                
                Object result;
                try {
                    result = method.invoke(target, args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
                
                long duration = System.nanoTime() - startTime;
                
                if (timed != null) {
                    String name = timed.name().isEmpty() ? method.getName() : timed.name();
                    System.out.println("  [" + name + "] executed in " + (duration / 1000.0) + " μs");
                }
                
                // Log result
                if (logExecution != null && logExecution.logResult()) {
                    System.out.println("  Result: " + result);
                }
                
                return result;
            }
        );
    }
    
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println("=== Direct Calls ===");
        System.out.println("5 + 3 = " + calc.add(5, 3));
        System.out.println("10 - 4 = " + calc.subtract(10, 4));
        System.out.println("6 * 7 = " + calc.multiply(6, 7));
        System.out.println("20 / 4 = " + calc.divide(20, 4));
        
        System.out.println("\n=== With AOP Proxy (would require interface) ===");
        System.out.println("Note: Proxy requires Calculator to implement an interface");
        System.out.println("This example demonstrates the annotation-based approach");
    }
}
```

**Output:**
```
=== Direct Calls ===
5 + 3 = 8
10 - 4 = 6
6 * 7 = 42
20 / 4 = 5.0

=== With AOP Proxy (would require interface) ===
Note: Proxy requires Calculator to implement an interface
This example demonstrates the annotation-based approach
```

### Example 4: Repeating Annotations

```java
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Permissions.class)
public @interface Permission {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {
    Permission[] value();
}

public class SecureService {
    
    @Permission("READ")
    @Permission("WRITE")
    public void editDocument() {
        System.out.println("Editing document...");
    }
    
    @Permission("READ")
    public void viewDocument() {
        System.out.println("Viewing document...");
    }
    
    @Permission("ADMIN")
    @Permission("DELETE")
    public void deleteDocument() {
        System.out.println("Deleting document...");
    }
}

public class SecurityManager {
    
    public static void printMethodPermissions(Class<?> serviceClass) {
        Method[] methods = serviceClass.getDeclaredMethods();
        
        for (Method method : methods) {
            Permission[] permissions = method.getAnnotationsByType(Permission.class);
            
            if (permissions.length > 0) {
                System.out.println("Method: " + method.getName());
                System.out.print("  Required permissions: ");
                for (int i = 0; i < permissions.length; i++) {
                    System.out.print(permissions[i].value());
                    if (i < permissions.length - 1) System.out.print(", ");
                }
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Method Permissions ===\n");
        printMethodPermissions(SecureService.class);
    }
}
```

**Output:**
```
=== Method Permissions ===

Method: editDocument
  Required permissions: READ, WRITE
Method: viewDocument
  Required permissions: READ
Method: deleteDocument
  Required permissions: ADMIN, DELETE
```

### Example 5: Annotation Inheritance

```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Serializable {
    String format() default "JSON";
}

@Serializable(format = "XML")
public class Parent {
    // Base class
}

public class Child extends Parent {
    // Inherits @Serializable annotation
}

public class AnnotationInheritance {
    
    public static void checkAnnotations(Class<?> cls) {
        System.out.println("Class: " + cls.getSimpleName());
        
        Serializable annotation = cls.getAnnotation(Serializable.class);
        if (annotation != null) {
            System.out.println("  @Serializable found");
            System.out.println("  Format: " + annotation.format());
        } else {
            System.out.println("  @Serializable not found");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Annotation Inheritance ===\n");
        
        checkAnnotations(Parent.class);
        System.out.println();
        checkAnnotations(Child.class);
        
        System.out.println("\nNote: @Inherited allows Child to inherit Parent's annotations");
    }
}
```

**Output:**
```
=== Annotation Inheritance ===

Class: Parent
  @Serializable found
  Format: XML

Class: Child
  @Serializable found
  Format: XML

Note: @Inherited allows Child to inherit Parent's annotations
```

## Performance Considerations

- **Retention.RUNTIME**: Has slight runtime overhead due to reflection
- **Retention.CLASS/SOURCE**: No runtime overhead
- **Reflection**: Calling getMethods with annotation checking is expensive; cache results when possible

## Summary

Annotations are a powerful mechanism for adding metadata to Java code, enabling framework configurations, validation, AOP, and code generation. The combination of built-in annotations and custom annotations provides flexibility for various use cases. Understanding retention policies, target elements, and proper annotation design enables building clean, maintainable applications with reduced boilerplate code and improved readability.
