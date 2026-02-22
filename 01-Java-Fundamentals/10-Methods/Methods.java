
public class Methods {
    // A method is a block of code that performs a specific task. It is also known as a function or a procedure. It is used to perform a specific task and can be called from other parts of the program.
    // To declare a method, define the method name followed by parentheses and a block of code. The method can also have parameters, which are variables that are passed to the method when it is called.
    // A method can also return a value, which is the result of the method. To return a value, use the return keyword followed by the value to be returned. The method must also specify the return type, which is the type of value that the method will return.
    // A method must be declared inside a class, and it can be called from other parts of the program using the method name followed by parentheses. If the method has parameters, you must pass the appropriate arguments when calling the method.
    // Here, we have declared a method called myMethod that takes no parameters and returns nothing (void). The method simply prints "Hello World!" to the console.

    static void myMethod() { // Declare a method called myMethod that takes no parameters and returns nothing (void)
        System.out.println("Hello World!");
    }

    // myMethod() is the name of the method. static means that the method belongs to the class and can be called without creating an instance of the class. void means that the method does not return any value.

    // Information can be passed to methods as parameters. Parameters are variables that are defined in the method declaration and are used to receive values when the method is called. You can have multiple parameters in a method, and they can be of different types.
    // To declare a parameter, specify the type of the parameter followed by the parameter name in the method declaration. When calling the method, you must pass the appropriate arguments that match the parameters in the method declaration.
    // For example, you can declare a method called myMethod1 that takes a String parameter called name and prints a greeting message to the console.
    static void myMethod1(String name) { // Declare a method called myMethod1 that takes a String parameter called name
        System.out.println("Hello " + name + "!");
    }

    // Multiple parameters can be declared in a method by separating them with commas. For example, you can declare a method called myMethod2 that takes two parameters, a String called name and an int called age, and prints a message to the console.
    static void myMethod2(String name, int age) {
        System.out.println(name + " is " + age);
    }

    // Create a checkAge method that takes an int parameter called age and returns a boolean value indicating whether the age is greater than or equal to 18.
    static boolean checkAge(int age) {
        if (age >= 18) {
            return true; // Return true if age is greater than or equal to 18
        } else {
            return false; // Return false if age is less than 18    
        }
    }

    // define a int method
    static int add(int a, int b) {
        return a + b; // Return the sum of a and b
    }

    // Method overloading is a feature in Java that allows you to create multiple methods with the same name but different parameters. The method that is called is determined by the number and type of parameters passed to the method when it is called. This allows you to create methods that perform similar tasks but with different inputs.
    // For example, you can create two methods called myMethod3 that take different parameters. One method can take a String parameter and the other method can take an int parameter. When you call myMethod3 with a String argument, the first method will be called, and when you call myMethod3 with an int argument, the second method will be called.
    static void myMethod3(String name) {
        System.out.println("Hello " + name + "!");
    }

    static void myMethod3(int age) {
        System.out.println("You are " + age + " years old.");
    }

    public static void main(String[] args) {
        myMethod(); // Call the method
        myMethod1("Alice"); // Call the method with a parameter
        myMethod1("Bob"); // Call the method with a different parameter
        myMethod1("Charlie"); // Call the method with another parameter

        // When a parameter is passed to a method, it is called an argument. The argument is the value that is passed to the method when it is called. The parameter is the variable that receives the value of the argument in the method declaration.
        // In the example above, "Alice", "Bob", and "Charlie" are the arguments that are passed to the myMethod1 method, and name is the parameter that receives the value of the argument in the method declaration.
        myMethod2("Alice", 25); // Call the method with two parameters
        myMethod2("Bob", 30); // Call the method with two different parameters
        myMethod2("Charlie", 35); // Call the method with two more different parameters

        // The checkAge method takes an int parameter called age and returns a boolean value indicating whether the age is greater than or equal to 18. You can call this method and pass different age values to check if they are old enough.
        System.out.println(checkAge(20)); // Output: true
        System.out.println(checkAge(15)); // Output: false

        // You can also use the checkAge method in an if statement to perform different actions based on the age value.
        int age = 20;
        if (checkAge(age)) {
            System.out.println("You are old enough to vote.");
        } else {
            System.out.println("You are not old enough to vote.");
        }

        // The add method takes two int parameters, a and b, and returns the sum of a and b. You can call this method and pass different integer values to get their sum.
        System.out.println(add(5, 10)); // Output: 15

        // When you call the myMethod3 method with a String argument, the first method will be called, and when you call myMethod3 with an int argument, the second method will be called.
        myMethod3("Alice"); // Output: Hello Alice!
        myMethod3(25); // Output: You are 25 years old.

        
    }
}
