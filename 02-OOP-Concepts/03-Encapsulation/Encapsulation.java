public class Encapsulation {
    // Encapsulation is one of the fundamental principles of OOP that allows us to hide the internal details of an object and only expose a public interface to interact with it. This is achieved by using access modifiers (private, protected, public) to restrict access to the internal state of an object and providing getter and setter methods to access and modify that state.

    // The main benefits of encapsulation include:
    // 1. Data Hiding: Encapsulation allows us to hide the internal state of an object from the outside world, which helps to protect the integrity of the data and prevent unauthorized access.
    // 2. Modularity: Encapsulation promotes modularity by allowing us to break down complex systems into smaller, more manageable pieces. Each class can be designed to have a specific responsibility, and the internal details of that class can be hidden from other classes.
    // 3. Maintainability: Encapsulation makes it easier to maintain and modify code by allowing us to change the internal implementation of a class without affecting the code that uses that class. This means that we can make changes to the internal workings of a class without having to worry about breaking other parts of the code that rely on that class.
    // 4. Reusability: Encapsulation promotes reusability by allowing us to create classes that can be easily reused in different contexts. By hiding the internal details of a class, we can create a more flexible and adaptable design that can be used in a variety of situations.

    // In Java, we can achieve encapsulation by declaring the instance variables of a class as private and providing public getter and setter methods to access and modify those variables. This way, we can control how the internal state of an object is accessed and modified, and we can ensure that the data is always in a valid state.

    // Getter and setter methods are used to access and modify the private instance variables of a class. A getter method is used to retrieve the value of a private variable, while a setter method is used to set the value of a private variable. By using getter and setter methods, we can control how the internal state of an object is accessed and modified, and we can ensure that the data is always in a valid state.

    // Example of encapsulation in Java
    public static void main(String[] args) {
        Person p = new Person();
        p.setName("John");
        p.setAge(30);
        System.out.println("Name: " + p.getName());
        System.out.println("Age: " + p.getAge());
    }
}
