public class Attributes {
    // In Java, variables declared inside a class are called "attributes" or "fields". They represent the state of an object and can hold data that is associated with the object. Attributes can be of any data type, including primitive types (such as int, double, boolean) and reference types (such as String, arrays, and other objects).
    // To declare an attribute in a class, you specify the data type followed by the attribute name. You can also assign a default value to the attribute when you declare it. For example, you can declare an attribute called "name" of type String and assign it a default value of "John".
    String name = "John"; // Attribute (state)
    int age = 25; // Attribute (state)

    // You can also declare attributes without assigning a default value. In this case, the attribute will be initialized with a default value based on its data type (e.g., 0 for int, null for String).
    String address; // Attribute (state) without a default value

    // If you don't want the ability to override the value of an attribute, you can declare it as final. A final attribute cannot be changed once it has been assigned a value. For example, you can declare a final attribute called "country" of type String and assign it a value of "USA".
    final String country = "USA"; // Final attribute (state)

    // Accessing attributes by creating an object of the class and using the dot operator to access the attribute. For example, you can create an object of the Attributes class and access its attributes like this:
    public static void main(String[] args) {
        Attributes myObj = new Attributes(); // Create an object of the Attributes class
        System.out.println(myObj.name); // Access the name attribute of the object
        System.out.println(myObj.age); // Access the age attribute of the object
        System.out.println(myObj.address); // Access the address attribute of the object (will print null)
        System.out.println(myObj.country); // Access the country attribute of the object (will print "USA")
        // You can also change the value of an attribute by assigning a new value to it. For example, you can change the name attribute of the object like this:
        myObj.name = "Alice"; // Change the value of the name attribute
        System.out.println(myObj.name); // Access the name attribute of the object (will print "Alice")
    }
}