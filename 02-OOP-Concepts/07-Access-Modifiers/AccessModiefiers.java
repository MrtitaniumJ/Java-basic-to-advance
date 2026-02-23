public class AccessModiefiers {
    // Access modifiers are keywords used to specify the accessibility of classes, methods, and variables in Java. They determine which other classes can access a particular class, method, or variable. The four access modifiers in Java are: public, private, protected, and default (no modifier).

    // 1. Public: A class, method, or variable declared as public can be accessed from any other class in the same package or from any other package. It is the most permissive access level.
    // 2. Private: A class, method, or variable declared as private can only be accessed within the same class. It is the most restrictive access level.
    // 3. Protected: A class, method, or variable declared as protected can be accessed within the same package and by subclasses in other packages. It is less restrictive than private but more restrictive than public.
    // 4. Default (no modifier): If no access modifier is specified, the class, method, or variable is accessible only within the same package. It is more restrictive than protected but less restrictive than private.

    // For classes, only public and default access modifiers are allowed. A class declared as public can be accessed from any other class, while a class with default access can only be accessed within the same package.
    // For methods, attributes and constructors, all four access modifiers can be used. The access level of a method, attribute, or constructor determines which other classes can access it. For example, a public method can be accessed from any other class, while a private method can only be accessed within the same class.

    public static void main(String[] args) {
        Person p = new Person();
        System.out.println("Name: " + p.name); // Accessing public variable
        // System.out.println("Age: " + p.age); // This will cause a compile-time error because age is private
        System.out.println("Address: " + p.address); // Accessing protected variable
        System.out.println("Phone Number: " + p.phoneNumber); // Accessing default variable
    }
}

// Example of access modifiers in Java
class Person {
    public String name = "John"; // Public variable - can be accessed from anywhere
    private int age = 30; // Private variable - can only be accessed within the Person class
    protected String address = "123 Main St"; // Protected variable - can be accessed within the same package and by subclasses
    String phoneNumber = "555-1234"; // Default variable - can only be accessed within the same package
}