# Core Java Q&A: Essential Concepts

This section covers 12 fundamental Java concepts frequently asked in technical interviews. Each Q&A pair includes a detailed explanation, practical code examples, key points to remember, and follow-up questions to deepen understanding.

---

## Q1: What is JVM, JRE, and JDK? How are they related?

### Explanation

The **JVM (Java Virtual Machine)** is an abstract computing machine that enables a computer to run Java programs and programs written in other languages that are compiled to Java bytecode. It's an interpreter that translates bytecode into native machine instructions for the underlying operating system.

The **JRE (Java Runtime Environment)** is a package that includes the JVM, core libraries, and other components needed to run Java applications. It's what end-users need to execute Java programs but cannot compile or develop new programs.

The **JDK (Java Development Kit)** is a complete development toolkit that includes the JRE plus development tools like the Java compiler (javac), debugger, and other utilities needed to write, compile, and run Java applications. Developers need the JDK, while end-users only need the JRE.

### Code Example

```java
// This code demonstrates the compilation and execution process

public class JVMDemo {
    public static void main(String[] args) {
        // When you compile: javac JVMDemo.java
        // This creates JVMDemo.class (bytecode)
        
        // When you run: java JVMDemo
        // The JVM reads the .class file and interprets bytecode
        
        System.out.println("JVM is executing this Java bytecode");
        System.out.println("Platform: " + System.getProperty("os.name"));
        System.out.println("Java Version: " + System.getProperty("java.version"));
        
        // Bytecode is platform-independent, but JVM is platform-specific
        // Different JVMs exist for Windows, Linux, Mac, etc.
    }
}
```

### Key Points
- **JVM is platform-specific** (Windows JVM differs from Linux JVM)
- **Bytecode is platform-independent** (same .class file works everywhere)
- **Write once, run anywhere (WORA)** - Java's key philosophy
- **JDK ⊃ JRE ⊃ JVM** (JDK contains JRE, which contains JVM)
- Different JVM implementations exist (Hotspot, GraalVM, OpenJ9)

### Follow-up Questions
1. Why is bytecode important to Java's portability?
2. What's the difference between compilation and interpretation in Java?
3. Can you explain how the JVM achieves "write once, run anywhere"?
4. What is JIT (Just-In-Time) compilation and how does it relate to JVM?
5. How would you distribute a Java application (compiled or source)?

---

## Q2: What is the difference between String, StringBuffer, and StringBuilder?

### Explanation

**String** is immutable - once created, it cannot be changed. Any modification creates a new String object. Strings are stored in the String pool for memory efficiency.

**StringBuffer** is mutable and thread-safe (synchronized). Multiple threads can safely use it without causing corruption. The synchronization overhead makes it slower for single-threaded use.

**StringBuilder** is mutable but not thread-safe (not synchronized). It provides the performance benefits of mutability without synchronization overhead, making it faster than StringBuffer for single-threaded scenarios.

### Code Example

```java
public class StringComparison {
    public static void main(String[] args) {
        // String - Immutable
        String str = "Hello";
        str = str + " World";  // Creates new String object
        // Original "Hello" is now garbage
        
        // StringBuffer - Mutable and synchronized
        StringBuffer sb = new StringBuffer("Hello");
        sb.append(" World");  // Modifies existing object
        sb.insert(0, "Say: ");
        System.out.println("StringBuffer: " + sb);
        
        // StringBuilder - Mutable, not synchronized
        StringBuilder sbl = new StringBuilder("Hello");
        sbl.append(" World");  // Modifies existing object
        sbl.reverse();  // Reverse the string
        System.out.println("StringBuilder: " + sbl);
        
        // Performance demonstration
        long start = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < 10000; i++) {
            result += i;  // Creates 10000 String objects - SLOW
        }
        System.out.println("String concatenation: " + 
                          (System.currentTimeMillis() - start) + "ms");
        
        start = System.currentTimeMillis();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb2.append(i);  // Reuses same object - FAST
        }
        System.out.println("StringBuilder append: " + 
                          (System.currentTimeMillis() - start) + "ms");
    }
}
```

### Key Points
- **Use String** for fixed, immutable values and as HashMap keys
- **Use StringBuilder** for single-threaded string manipulations (DEFAULT CHOICE)
- **Use StringBuffer** only when thread safety is explicitly needed
- String concatenation with + in loops is O(n²) complexity
- StringBuilder appends are amortized O(1) complexity

### Follow-up Questions
1. Why is String immutable in Java and what are the benefits?
2. How does the String pool work internally?
3. When would you use each type in real-world code?
4. What happens to the original String when you concatenate two strings?
5. How does the += operator differ from concat() method?

---

## Q3: What is String pool?

### Explanation

The String pool is a special memory region in the heap where Java stores string literals. When you create a string using string literals (e.g., `String str = "Java"`), Java checks if this string already exists in the pool. If it does, it returns a reference to the existing string; if not, it creates a new string and adds it to the pool. This mechanism reduces memory consumption by avoiding duplicate string storage.

When you create strings using the `new` keyword (e.g., `String str = new String("Java")`), a new string object is created in the heap memory outside the pool, even if the same string already exists in the pool. The `intern()` method can be used to explicitly add a string to the pool or get a reference to an existing pooled string.

### Code Example

```java
public class StringPoolDemo {
    public static void main(String[] args) {
        // These are String literals - stored in pool
        String s1 = "Hello";
        String s2 = "Hello";
        // s1 and s2 reference the SAME object in pool
        System.out.println("s1 == s2: " + (s1 == s2));  // true
        
        // Creating String with 'new' - outside pool
        String s3 = new String("Hello");
        String s4 = new String("Hello");
        // s3 and s4 are different objects in heap
        System.out.println("s3 == s4: " + (s3 == s4));  // false
        System.out.println("s3.equals(s4): " + s3.equals(s4));  // true
        
        // intern() adds string to pool and returns reference
        String s5 = new String("World");
        String s6 = s5.intern();
        String s7 = "World";
        System.out.println("s6 == s7: " + (s6 == s7));  // true
        
        // String concatenation behavior
        String a = "Java";
        String b = "Java";
        String c = new String("Java");
        String d = a + b;  // Concatenation creates new string
        String e = "JavaJava";  // String literal
        System.out.println("d == e: " + (d == e));  // false
        System.out.println("d.equals(e): " + (d.equals(e)));  // true
    }
}
```

### Key Points
- String pool is part of Java heap memory
- Only literals and intern() results go into pool
- Reduces memory usage when duplicate strings are common
- Never use == to compare string values (use equals())
- String pool is also called "String interning"
- Pool size is fixed (configurable with XX:StringTableSize)

### Follow-up Questions
1. How many String objects are created in the pool vs heap in my example?
2. Why doesn't `new String("Hello")` go into the pool?
3. When would you use intern() and what are the performance implications?
4. How does string pool relate to garbage collection?
5. Can strings be removed from the pool once added?

---

## Q4: How is memory managed in Java? Explain Stack vs Heap.

### Explanation

Java memory is divided into **Stack** and **Heap**. The **Stack** stores primitive values and object references. It's automatically managed with LIFO (Last-In-First-Out) behavior. Each method call creates a new stack frame; when the method ends, the frame is destroyed.

The **Heap** stores actual objects. It's shared across the entire application and requires garbage collection to reclaim unused memory. Heap memory is allocated dynamically and can fragment.

Understanding this distinction is crucial: primitives and references go on the stack; objects go on the heap.

### Code Example

```java
public class MemoryManagement {
    public static void main(String[] args) {
        // STACK MEMORY:
        int age = 25;  // Primitive value stored in stack
        double height = 5.8;  // Primitive value stored in stack
        
        // HEAP MEMORY:
        String name = new String("John");  // 'name' reference in stack,
                                           // actual String object in heap
        Person person = new Person("Alice");  // 'person' reference in stack,
                                              // Person object in heap
        
        demonstrateGarbageCollection();
        
        // After this line, memory allocation in demonstrateGarbageCollection
        // is eligible for garbage collection
    }
    
    public static void demonstrateGarbageCollection() {
        // These objects are created in heap
        String tempString = new String("temporary");  // Created in heap
        int[] array = new int[1000];  // Array object in heap
        
        // When this method ends, stack frame is destroyed
        // Heap objects become unreferenced and eligible for GC
    }
}

class Person {
    String name;  // Reference stored in object, actual String in heap
    int[] scores;  // Array reference, actual array in heap
    
    Person(String name) {
        this.name = name;
        this.scores = new int[100];
    }
}
```

### Key Points
- **Stack**: Thread-safe, fast, limited size, automatic cleanup
- **Heap**: Shared resource, larger, manual cleanup via GC
- **Stack Overflow**: Too much recursion or local variables
- **Heap Exhaustion (OutOfMemoryError)**: Too many objects, GC can't keep up
- Primitive variables are completely stored on stack
- Object references are on stack, but objects are on heap

### Follow-up Questions
1. What happens when the stack overflows?
2. Why is stack access faster than heap access?
3. How does garbage collection decide which objects to reclaim?
4. Can objects communicate between stack and heap?
5. What's the difference between stack memory and heap memory size limits?

---

## Q5: What is the difference between == and equals()?

### Explanation

The **== operator** compares references (memory addresses) for objects, and actual values for primitives. Two objects are equal with == only if they point to the exact same location in memory.

The **equals() method** compares the actual content/values of objects. By default, it behaves like ==, but classes can override it to define custom equality logic.

This distinction is critical: == asks "are these the same object?" while equals() asks "do these objects have equivalent content?"

### Code Example

```java
public class EqualityComparison {
    public static void main(String[] args) {
        // PRIMITIVES: == compares values
        int a = 5;
        int b = 5;
        System.out.println("a == b: " + (a == b));  // true (values are same)
        
        // OBJECTS WITHOUT equals() OVERRIDE:
        String s1 = new String("Hello");
        String s2 = new String("Hello");
        System.out.println("s1 == s2: " + (s1 == s2));  // false (different objects)
        System.out.println("s1.equals(s2): " + s1.equals(s2));  // true (same content)
        
        // String pool makes s3 == s4 true
        String s3 = "Hello";
        String s4 = "Hello";
        System.out.println("s3 == s4: " + (s3 == s4));  // true (same pool object)
        
        // CUSTOM OBJECTS:
        Student student1 = new Student("John", 101);
        Student student2 = new Student("John", 101);
        System.out.println("student1 == student2: " + 
                          (student1 == student2));  // false
        System.out.println("student1.equals(student2): " + 
                          (student1.equals(student2)));  // true (overridden)
    }
}

class Student {
    String name;
    int id;
    
    Student(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    // Override equals() to compare by content
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Same reference check
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        Student other = (Student) obj;
        return this.id == other.id && 
               this.name.equals(other.name);
    }
    
    // Always override hashCode() when overriding equals()
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, id);
    }
}
```

### Key Points
- **== compares references** (memory address) for objects
- **equals() compares content** (by default same as ==, but overrideable)
- Always override hashCode() if you override equals()
- Never use == to compare String values
- Collections (HashMap, HashSet) use equals() and hashCode()
- Primitive comparison always uses values, not references

### Follow-up Questions
1. When would you override equals() in a custom class?
2. Why must you override hashCode() when overriding equals()?
3. What does the contract between equals() and hashCode() mean?
4. How does HashMap use equals() and hashCode() together?
5. What are the common mistakes when overriding equals()?

---

## Q6: What are access modifiers?

### Explanation

Access modifiers control the visibility and accessibility of classes, methods, and variables. **public** allows access from anywhere. **private** restricts access to only the same class. **protected** allows access to subclasses and classes in the same package. **package-private** (no modifier) restricts access to classes in the same package only.

Proper use of access modifiers is essential for encapsulation, which hides implementation details and protects internal state from unauthorized modification.

### Code Example

```java
// File: AccessModifierDemo.java (same package)
public class AccessModifierDemo {
    public static void main(String[] args) {
        Employee emp = new Employee("John", 50000);
        
        System.out.println("Name: " + emp.getName());  // public - OK
        System.out.println("Department: " + emp.department);  // package-private - OK
        
        // System.out.println(emp.salary);  // COMPILE ERROR - private
        // emp.performAudit();  // COMPILE ERROR - protected
    }
}

class Employee {
    // public - accessible everywhere
    public String id = "E001";
    
    // private - accessible only within this class
    private double salary;
    
    // protected - accessible in subclasses and same package
    protected void performAudit() {
        System.out.println("Performing audit...");
    }
    
    // package-private (no modifier) - accessible in same package only
    String department = "IT";
    
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    
    public double getSalary() {
        return salary;  // private field, public getter
    }
    
    public void setSalary(double salary) {
        if (salary > 0) {  // validation
            this.salary = salary;
        }
    }
    
    public String getName() {
        return name;
    }
    
    private String name;
    
    // private method - helper method
    private void validateSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Invalid salary");
        }
    }
}

// File: Manager.java (different package)
public class Manager extends Employee {
    @Override
    protected void performAudit() {  // CAN override protected method
        super.performAudit();
        System.out.println("Manager audit...");
    }
}
```

### Key Points
- **public**: Most visible, use for API contracts
- **private**: Most restrictive, use for internal state
- **protected**: Use for inheritance hierarchies
- **package-private**: Use to group related classes
- Principle of least privilege: Use the most restrictive modifier
- Getters/setters expose private fields in a controlled manner

### Follow-up Questions
1. Why is hiding data with private fields important?
2. What's the difference between protected and package-private?
3. When would you use protected instead of private?
4. How do access modifiers relate to encapsulation?
5. Can you override a private method in a subclass?

---

## Q7: What is the difference between static and instance variables?

### Explanation

**Instance variables** are created for each object and stored on the heap. Each object has its own copy with its own value. They are accessed through object references.

**Static variables** are shared by all instances of a class and stored in memory only once. They are created when the class loads, not when objects are instantiated. They're accessed via the class name or instance (though class name is preferred).

This distinction affects both memory usage and behavior: static fields waste memory if used excessively but are efficient for truly shared data.

### Code Example

```java
public class VariableDemo {
    public static void main(String[] args) {
        // Static variable counts all objects created
        System.out.println("Total students before creation: " 
                          + Student.totalStudents);  // 0
        
        Student s1 = new Student("Alice");
        System.out.println("Total students: " 
                          + Student.totalStudents);  // 1
        
        Student s2 = new Student("Bob");
        System.out.println("Total students: " 
                          + Student.totalStudents);  // 2
        
        // Instance variables are independent
        System.out.println("s1 name: " + s1.name);  // Alice
        System.out.println("s2 name: " + s2.name);  // Bob
        
        // Modifying one student's GPA doesn't affect other
        s1.setGPA(3.8);
        s2.setGPA(3.5);
        System.out.println("s1 GPA: " + s1.getGPA());  // 3.8
        System.out.println("s2 GPA: " + s2.getGPA());  // 3.5
        
        // Static variable is same for all instances
        System.out.println("Access via s1: " 
                          + s1.totalStudents);  // 2
        System.out.println("Access via s2: " 
                          + s2.totalStudents);  // 2
        System.out.println("Access via class: " 
                          + Student.totalStudents);  // 2 (preferred)
    }
}

class Student {
    // Instance variable - each object gets its own copy
    private String name;
    private double gpa;
    
    // Static variable - shared by all Student objects
    public static int totalStudents = 0;
    
    // Static constant - convention: all caps
    public static final double MIN_GPA = 0.0;
    
    Student(String name) {
        this.name = name;
        this.gpa = 0.0;
        totalStudents++;  // Increment shared counter
    }
    
    public void setGPA(double gpa) {
        if (gpa >= MIN_GPA && gpa <= 4.0) {
            this.gpa = gpa;
        }
    }
    
    public double getGPA() {
        return gpa;
    }
    
    // Static method - can only access static variables
    public static int getTotalStudents() {
        // return name;  // COMPILE ERROR - instance variable
        return totalStudents;  // OK - static variable
    }
}
```

### Key Points
- **Instance variables**: One per object, different values possible
- **Static variables**: One per class, same value for all instances
- Static variables are initialized when class loads
- Instance variables are initialized when object is created
- Access static via class name (Student.totalStudents), not instance
- Static methods cannot access instance variables

### Follow-up Questions
1. When should you use static variables?
2. What's the difference in memory usage between static and instance variables?
3. Can you override a static variable in a subclass?
4. Why can't static methods access instance variables?
5. When is it bad practice to use static variables excessively?

---

## Q8: What is the difference between final, finally, and finalize()?

### Explanation

**final** is a keyword used with variables (prevents reassignment), methods (prevents overriding), or classes (prevents extending). **finally** is a block in exception handling that always executes regardless of whether an exception occurred. **finalize()** is a deprecated method (Java 9+) called by garbage collector before destroying an object.

These three completely unrelated terms often confuse beginners, but understanding the distinction is important for Java mastery.

### Code Example

```java
public class FinalFinallyFinalizeDemo {
    public static void main(String[] args) {
        // 1. FINAL - prevents reassignment
        final int maxAge = 150;
        // maxAge = 200;  // COMPILE ERROR
        
        final String[] array = new String[5];
        array[0] = "changed";  // OK - changing content
        // array = new String[10];  // ERROR - reassigning reference
        
        demonstrateFinally();
        demonstrateFinalize();
    }
    
    // 2. FINALLY - exception handling
    public static void demonstrateFinally() {
        try {
            System.out.println("In try block");
            int x = 10 / 0;  // Exception occurs
            System.out.println("This won't execute");
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            // Always executes, even if exception occurs
            System.out.println("Finally block always runs");
        }
    }
    
    // 3. FINALIZE - called before garbage collection
    public static void demonstrateFinalize() {
        Resource res = new Resource("file.txt");
        res = null;  // Eligible for garbage collection
        System.gc();  // Request garbage collection (not guaranteed)
    }
}

class Resource {
    String filename;
    
    Resource(String filename) {
        this.filename = filename;
    }
    
    // FINAL METHOD - cannot be overridden
    final void criticalOperation() {
        System.out.println("Critical operation - cannot be overridden");
    }
    
    // FINALIZE - deprecated, avoid using
    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("Finalizing resource: " + filename);
            // Clean up resources
        } finally {
            super.finalize();
        }
    }
}

// FINAL CLASS - cannot be extended
final class ImmutableClass {
    private final int value;
    
    ImmutableClass(int value) {
        this.value = value;
    }
}

// class ExtendImmutable extends ImmutableClass {}  // COMPILE ERROR
```

### Key Points
- **final variable**: Value cannot change after initialization
- **final method**: Subclasses cannot override
- **final class**: Cannot create subclasses
- **finally block**: Executes even if exception or return statement
- **finalize()**: Deprecated since Java 9, use try-with-resources instead
- Immutable objects should use final fields

### Follow-up Questions
1. Why would you make a variable final?
2. Can you reassign a final array or list?
3. When is finally block skipped (if ever)?
4. Why is finalize() deprecated?
5. What should you use instead of finalize()?

---

## Q9: What is immutability? Why is it important?

### Explanation

An **immutable object** cannot be modified after creation. All fields are private and final. No setters are provided. If you need to change data, you create a new object instead.

Immutability is important for: thread safety (no synchronization needed), security (can't be altered), simplicity (predictable behavior), and efficiency (can be cached/pooled). Immutable objects are also safe to use as HashMap keys and in multithreaded environments.

### Code Example

```java
public class ImmutabilityDemo {
    public static void main(String[] args) {
        // String is immutable
        String str = "Hello";
        String modified = str.concat(" World");
        System.out.println("Original: " + str);  // Hello (unchanged)
        System.out.println("Modified: " + modified);  // Hello World
        
        // Custom immutable object
        Point p1 = new Point(10, 20);
        System.out.println("Original point: " + p1);
        
        Point p2 = p1.move(5, 5);
        System.out.println("After move: " + p1);  // (10, 20) unchanged
        System.out.println("New point: " + p2);  // (15, 25)
        
        // Safe use in HashMap
        Point point1 = new Point(1, 1);
        java.util.Map<Point, String> map = new java.util.HashMap<>();
        map.put(point1, "Origin");
        System.out.println("Map lookup: " + map.get(point1));
        // point1 cannot be modified, so map lookup stays valid
    }
}

// Immutable class example
final class Point {
    private final int x;
    private final int y;
    
    // Constructor - only way to set values
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Getters only - no setters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    // Return new object instead of modifying
    public Point move(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy);
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }
}
```

### Key Points
- Make class `final` to prevent subclassing
- Make all fields `private` and `final`
- Don't provide setter methods
- Return copies of mutable objects
- Use defensive copying in constructor
- Immutable objects are inherently thread-safe
- Can be used as HashMap keys safely

### Follow-up Questions
1. Is String truly immutable? (Yes, with some caveats using reflection)
2. How does immutability relate to functional programming?
3. What's the trade-off between immutability and memory efficiency?
4. Can immutable classes have mutable fields?
5. How do you create immutable collections?

---

## Q10: How do you create immutable objects? What are the best practices?

### Explanation

To create truly immutable objects, follow a strict pattern: declare the class final, make all fields private and final, provide only a constructor and getters, use defensive copying for mutable fields, and override equals() and hashCode(). Special care is needed when dealing with mutable field types like arrays or collections.

### Code Example

```java
import java.util.*;

public class ImmutableObjectsDemo {
    public static void main(String[] args) {
        // Creating immutable objects
        List<String> hobbies = Arrays.asList("Reading", "Gaming");
        Person person = new Person("John", 30, hobbies);
        
        System.out.println("Original: " + person);
        
        // Attempt to modify original list
        hobbies.add("Coding");  // Modifies original list
        System.out.println("After modifying original list: " + person);
        
        // Create safer version
        List<String> hobbies2 = Arrays.asList("Reading", "Gaming");
        PersonImmutable personIm = new PersonImmutable("Alice", 25, hobbies2);
        
        System.out.println("Immutable person: " + personIm);
        // hobbies2.add("Coding");  // Does nothing - not reflected
        System.out.println("After modifying list (immutable): " + personIm);
    }
}

// NOT IMMUTABLE - vulnerable to external modification
class Person {
    private final String name;
    private final int age;
    private final List<String> hobbies;
    
    Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;  // PROBLEM: shared reference
    }
    
    public List<String> getHobbies() {
        return hobbies;  // PROBLEM: returns mutable list
    }
    
    @Override
    public String toString() {
        return name + ", " + age + ", " + hobbies;
    }
}

// TRULY IMMUTABLE - defensive copying
final class PersonImmutable {
    private final String name;
    private final int age;
    private final List<String> hobbies;  // final doesn't make content immutable
    
    PersonImmutable(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        // Defensive copy - create new list with same contents
        this.hobbies = new ArrayList<>(hobbies);
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    // Return unmodifiable copy instead of original
    public List<String> getHobbies() {
        return Collections.unmodifiableList(
                new ArrayList<>(hobbies));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PersonImmutable)) return false;
        PersonImmutable other = (PersonImmutable) obj;
        return this.name.equals(other.name) &&
               this.age == other.age &&
               this.hobbies.equals(other.hobbies);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age, hobbies);
    }
    
    @Override
    public String toString() {
        return name + ", " + age + ", " + hobbies;
    }
}
```

### Key Points
- **final field != immutable object** (if field references mutable object)
- Always use defensive copying for mutable parameter types
- Return immutable copies from getters of mutable fields
- Collections.unmodifiableList/Set/Map return immutable wrappers
- Immutable objects are powerful for thread safety and correctness

### Follow-up Questions
1. What's the difference between final and immutable?
2. Why is defensive copying necessary?
3. Can you have truly immutable objects with mutable fields?
4. What's the performance cost of defensive copying?
5. When should you prioritize immutability vs performance?

---

## Q11: What is Exception Handling Hierarchy? Explain Checked vs Unchecked Exceptions.

### Explanation

Java exceptions follow a hierarchy with Throwable at the root. It has two main branches: **Error** (JVM-level, not recoverable) and **Exception** (application-level, recoverable).

**Checked exceptions** inherit from Exception (excluding RuntimeException). The compiler forces you to handle them with try-catch or throw them. Examples: IOException, SQLException.

**Unchecked exceptions** inherit from RuntimeException. The compiler doesn't require handling. They typically indicate programming errors. Examples: NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException.

### Code Example

```java
import java.io.*;

public class ExceptionHierarchyDemo {
    public static void main(String[] args) {
        // Checked exception - must be handled
        try {
            readFile("data.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }
        
        // Unchecked exception - not required to handle
        try {
            int[] array = new int[5];
            System.out.println(array[10]);  // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index out of bounds: " + e.getMessage());
        }
        
        demonstrateCustomException();
    }
    
    // Checked exception - must declare in throws clause
    public static void readFile(String filename) 
            throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(filename);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        br.close();
    }
    
    // Unchecked exception - no throws declaration needed
    public static void demonstrateCustomException() {
        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
}

// Custom checked exception
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Custom unchecked exception (for programming errors)
class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(String message) {
        super(message);
    }
}

class AgeValidator {
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age must be 0-150, got: " + age);
        }
    }
    
    // Unchecked exception for logic errors
    public static void validateUniqueId(String id, java.util.Set<String> ids) {
        if (ids.contains(id)) {
            throw new DuplicateIdException("ID already exists: " + id);
        }
        ids.add(id);
    }
}
```

### Key Points
- **Checked**: Must be caught or declared, compiler enforces handling
- **Unchecked**: Not required to catch, indicate programming errors
- Never catch Exception or Throwable (too broad)
- Always log exceptions with context
- Exception hierarchy: Throwable → Error/Exception → checked/unchecked

### Follow-up Questions
1. Should you create checked or unchecked custom exceptions?
2. When should you catch exceptions vs let them propagate?
3. What's the difference between throws and throw?
4. Why not catch Exception for all exceptions?
5. How do you handle multiple exception types?

---

## Q12: What is the difference between Checked and Unchecked Exceptions? When should you use each?

### Explanation

**Checked exceptions** (except RuntimeException) must be declared in method signature with throws or caught in try-catch. They represent conditions that could happen to any code, even if correctly written (IO errors, network timeouts, missing files).

**Unchecked exceptions** (RuntimeException subclasses) don't require declaration or catching. They typically indicate programmer errors (null pointer dereference, invalid method arguments, index out of bounds).

The key decision: use checked for recoverable situations where calling code might logically handle them; use unchecked for errors that indicate bugs.

### Code Example

```java
import java.io.*;
import java.sql.*;

public class CheckedVsUncheckedDemo {
    public static void main(String[] args) {
        // Checked exception handling - expected condition
        try {
            String data = readConfigFile("config.properties");
            System.out.println("Config loaded: " + data);
        } catch (FileNotFoundException e) {
            // Caller can recover - use default config
            System.out.println("Config not found, using defaults");
            loadDefaultConfig();
        } catch (IOException e) {
            // Caller can recover - retry logic
            System.out.println("IO error reading config: " + e.getMessage());
        }
        
        // Unchecked exception - indicates bug
        try {
            String[] names = {"Alice", "Bob"};
            System.out.println(names[5]);  // Bug - wrong index
        } catch (ArrayIndexOutOfBoundsException e) {
            // Fix: check array length before access
            System.out.println("Bug: accessing wrong index");
        }
        
        // Unchecked exception - indicates invalid input
        try {
            User user = new User(null);  // Bug - null argument
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
    
    // CHECKED: Recoverable I/O condition
    public static String readConfigFile(String filename) 
            throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(filename));
        String line = reader.readLine();
        reader.close();
        return line;
    }
    
    private static void loadDefaultConfig() {
        System.out.println("Loaded default configuration");
    }
}

// Checked custom exception - expected condition
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message, Exception cause) {
        super(message, cause);
    }
}

// Unchecked custom exception - programmer error
class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}

class User {
    private String name;
    private int age;
    
    public User(String name) {
        if (name == null || name.isEmpty()) {
            // Unchecked - indicates bug in calling code
            throw new InvalidUserException(
                    "Name cannot be null or empty");
        }
        this.name = name;
    }
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            // Unchecked - indicates bug in calling code
            throw new IllegalArgumentException(
                    "Age must be 0-150, got: " + age);
        }
        this.age = age;
    }
}

class DatabaseConnection {
    // Checked - caller should handle potential connection loss
    public ResultSet executeQuery(String sql) 
            throws DatabaseConnectionException {
        try {
            // Simulate database connection
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/db");
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(
                    "Failed to execute query: " + sql, e);
        }
    }
}
```

### Key Points
- **Checked Exception**: For recoverable conditions, must handle
- **Unchecked Exception**: For programming errors, fix the code
- Over-using checked exceptions makes APIs hard to use
- Modern Java frameworks often convert checked to unchecked
- Design APIs considering what the caller can do about the error

### Follow-up Questions
1. Why does Java have two types of exceptions?
2. Are checked exceptions more or less common in modern Java?
3. Can you convert checked to unchecked exception?
4. What are wrapper exceptions and when do you use them?
5. How do modern frameworks like Spring handle checked exceptions?

---

## Summary & Next Steps

This guide covers the 12 most fundamental Core Java concepts tested in interviews. Master these concepts:
- Know the explanations well enough to explain them simply
- Be able to write the code examples from memory
- Understand the practical applications and trade-offs
- Practice explaining these concepts out loud

**Next Study Steps:**
1. Run all code examples locally in your IDE
2. Modify examples to test your understanding
3. Answer follow-up questions in depth
4. Teach these concepts to someone else
5. Review weekly until interview day

Good luck with your interview preparation!
