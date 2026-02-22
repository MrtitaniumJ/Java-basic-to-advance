public class ClassAndObjects {
    // A class is a blueprint for creating objects. It defines the properties and behaviors of the objects that will be created from the class. An object is an instance of a class. It is a real-world entity that has state and behavior. The state of an object is represented by its properties, and the behavior of an object is represented by its methods.
    // To create a class in Java, you use the class keyword followed by the name of the class. The body of the class is enclosed in curly braces. Inside the class, you can define properties (also known as fields) and methods (also known as functions). Properties are variables that hold the state of the object, while methods are blocks of code that perform specific tasks and define the behavior of the object.
    // To create an object from a class, you use the new keyword followed by the class name and parentheses. This is called instantiation. Once you have created an object, you can access its properties and methods using the dot operator.
    // For example, you can create a class called Car that has properties such as make, model, and year, and methods such as start() and stop(). You can then create an object of the Car class and access its properties and methods.

}

class Main {
    int x = 5; // Property (state)

    void myMethod() { // Method (behavior)
        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        Main myObj = new Main(); // Create an object of the Main class
        System.out.println(myObj.x); // Access the property of the object
        myObj.myMethod(); // Call the method of the object
    }
}

class Cars {
    String model; // Property (state)

    void honk() { // Method (behavior)
        System.out.println("Beep beep!");
    }

    public static void main(String[] args) {
        Cars myCar = new Cars(); // Create an object of the Cars class
        myCar.model = "Toyota"; // Set the property of the object
        System.out.println(myCar.model); // Access the property of the object
        myCar.honk(); // Call the method of the object
    }
}