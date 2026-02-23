public class NonAccessModifiers {
    // Non-access modifiers are keywords in Java that provide additional information about classes, methods, and variables. They do not affect the accessibility of the members but rather specify certain properties or behaviors. Some common non-access modifiers in Java include: static, final, abstract, synchronized, volatile, transient, and native.

    // They do not control visibility but rather define other characteristics of the members. For example, the static modifier indicates that a member belongs to the class rather than to any specific instance, while the final modifier indicates that a member cannot be modified after it has been initialized.

    // Non-access modifiers can be used in combination with access modifiers to provide a more complete definition of a class, method, or variable. For example, a method can be declared as public static final, which means it can be accessed from anywhere, belongs to the class, and cannot be overridden by subclasses.

    // 1. Static: A static method belongs to the class, not to any specific object. This means you can call it without creating an object of the class. Static methods can only access static variables and other static methods.
    // 2. Final: A final variable cannot be changed once it has been initialized. A final method cannot be overridden by subclasses, and a final class cannot be subclassed.
    // 3. Abstract: An abstract class cannot be instantiated and may contain abstract methods that must be implemented by subclasses. An abstract method is a method that is declared without an implementation.
    // 4. Synchronized: A synchronized method can only be accessed by one thread at a time. It is used to control access to a particular resource in a multi-threaded environment.
    // 5. Volatile: A volatile variable is one that can be accessed by multiple threads. It ensures that changes to the variable are immediately visible to all threads.
    // 6. Transient: A transient variable is one that is not serialized when an object is serialized. It is used to indicate that a variable should not be persisted.
    // 7. Native: A native method is a method that is implemented in a language other than Java, such as C or C++. It is used to access system-level resources or to perform tasks that are not possible in Java.
}

// Final example
class FinalExample {
    final int x = 10;
    final double PI = 3.14;

    public static void main(String[] args) {
        FinalExample example = new FinalExample();
        // example.x = 50; // This will cause a compile-time error because x is final and cannot be changed
        // example.PI = 3.14159; // This will also cause a compile-time error because PI is final and cannot be changed
        System.out.println("Value of x: " + example.x);
        System.out.println("Value of PI: " + example.PI);
    }
}

// static example
class StaticExample {
    // static method
    static void myStaticMethod() {
        System.out.println("Static methods can be called without creating an object of the class.");
    }

    // Main method
    public static void main(String[] args) {
        // Calling the static method
        myStaticMethod(); // This is the preferred way to call a static method
        StaticExample.myStaticMethod(); // This also works but is less common
    }
}

// Abstract example
// Abstract class
abstract class AbstractExample {
    public String fname = "John";
    public int age = 24;
    public abstract void study(); // Abstract method (does not have a body)
}

// Subclass (inherit from AbstractExample)
class Student extends AbstractExample {
    public int graduationYear = 2018;
    public void study() { // The body of the abstract method is provided here
        System.out.println("Studying all day long");
    }
}