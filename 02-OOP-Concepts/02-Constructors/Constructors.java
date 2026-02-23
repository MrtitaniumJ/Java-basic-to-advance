public class Constructors {
    // A constructor is a special method that is called when an object is created. It is used to initialize the state of the object. A constructor has the same name as the class and does not have a return type.

    // In Java, if you do not provide a constructor for a class, the compiler will automatically create a default constructor for you. The default constructor is a no-argument constructor that initializes the object with default values. For example, if you have a class called Person and you do not provide a constructor, the compiler will create a default constructor that initializes the name attribute to null and the age attribute to 0.

    // You can also create your own constructor to initialize the object with specific values. For example, you can create a constructor for the Person class that takes a name and an age as parameters and initializes tha name and age attributes of the object with the values passed as arguments.

    // A constructor can also be overloaded, which means you can have multiple constructors with different parameters. This allows you to create objects in different ways. For example, you can have a constructor that takes only a name as a parameter and initializes the age attribute to a default value, and another constructor that takes both a name and an age as parameters.

    // To create a constructor in Java, you define a method with the same name as the class and no return type. You can then use the this keyword to refer to the current object and initialize its attributes with the values passed as parameters.

    // this keyword is used to refer to the current object. It is commonly used in constructors to differentiate between the instance variables (attributes) and the parameters with the same name. For example, in the constructor of the Person class, you can use this.name to refer to the name attribute of the object and name to refer to the parameter passed to the constructor.

    // Calling a contructor from Another constructor: You can call one constructor from another constructor in the same class using the this() keyword. This is known as constructor chaining. For example, you can have a constructor that takes only a name as a parameter and calls another constructor that takes both a name and an age as parameters, passing a default value for the age.

    String name; // Attribute (state)
    int age; // Attribute (state)

    // Constructor to initialize the name attribute of the Constructor class
    public Constructors(String name) { // Constructor with a parameter to initialize the name attribute
        this.name = name; // Initialize the name attribute with the value of the name parameter
        this.age = 0; // Initialize the age attribute to a default value of 0

        // You can also call another constructor from this constructor using the this() keyword. For example, you can call the constructor that takes both a name and an age as parameters, passing a default value for the age.
        // this(name, 0); // Call the constructor that takes both a name and an age as parameters, passing a default value of 0 for the age
    }

    // Constructor to initialize the name and age attributes of the Constructor class
    public Constructors(String name, int age) { // Constructor with parameters to initialize the name and age attributes
        this.name = name; // Initialize the name attribute with the value of the name parameter
        this.age = age; // Initialize the age attribute with the value of the age parameter
    }

    // Method to display the attributes of the Constructor class
    public void display() {
        System.out.println("Name: " + name); // Display the name attribute
        System.out.println("Age: " + age); // Display the age attribute
    }

    public static void main(String[] args) {
        Constructors person1 = new Constructors("Alice"); // Create an object of the Constructor class using the constructor with one parameter
        person1.display(); // Display the attributes of the person1 object (will print "Name: Alice" and "Age: 0")
        Constructors person2 = new Constructors("Bob", 25); // Create an object of the Constructor class using the constructor with two parameters
        person2.display(); // Display the attributes of the person2 object (will print "Name: Bob" and "Age: 25")
    }
}

// Example of a constructor in Java
    // In this example, we have a Person class with a constructor that takes a name and an age as parameters and initializes the name and age attributes of the object with the values passed as arguments. We then create two objects of the Person class using the constructor and access their attributes to verify that they have been initialized correctly.

class Person {
    String name; // Attribute (state)
    int age; // Attribute (state)

    // Constructor to initialize the name and age attributes of the Person class
    Person(String name, int age) { // Constructor with parameters to initialize the name and age attributes
        this.name = name; // Initialize the name attribute with the value of the name parameter
        this.age = age; // Initialize the age attribute with the value of the age parameter
    }

    public static void main(String[] args) {
        Person person1 = new Person("Alice", 30); // Create an object of the Person class using the constructor
        System.out.println(person1.name); // Access the name attribute of the person1 object (will print "Alice")
        System.out.println(person1.age); // Access the age attribute of the person1 object (will print 30)

        Person person2 = new Person("Bob", 25); // Create another object of the Person class using the constructor
        System.out.println(person2.name); // Access the name attribute of the person2 object (will print "Bob")
        System.out.println(person2.age); // Access the age attribute of the person2 object (will print 25)
    }
}
