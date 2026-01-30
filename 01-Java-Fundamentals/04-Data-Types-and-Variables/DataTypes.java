public class DataTypes {
    public static void main(String[] args) {
        int myNum = 5;              // Integer (whole number)
        float myFloatNum = 5.99f;   // Floating point number
        char myLetter = 'D';        // Character
        boolean myBool = true;      // Boolean
        String myText = "Hello";    // String (text)

        // Primitive data types: byte, short, int, long, float, double, char, boolean
        // Non-primitive data type: String, Arryay, Classes, Interfaces

        // Primitive data types store simple values directly in memory
        // Example: int myNum = 5; stores the value 5 directly in memory
        // byte: Stores whole numbers from -128 to 127
        byte byteNum = 100;
        System.out.println("Byte value: " + byteNum);
        // short: Stores whole numbers from -32,768 to 32,767
        short shortNum = 5000;
        System.out.println("Short value: " + shortNum);
        // int: Stores whole numbers from -2,147,483,648 to 2,147,483,647
        int intNum = 100000;
        System.out.println("Integer value: " + intNum);
        // long: Stores whole numbers from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
        long longNum = 15000000000L;
        System.out.println("Long value: " + longNum);
        // float: Stores fractional numbers. Sufficient for storing 6 to 7 decimal digits
        float floatNum = 5.75f;
        System.out.println("Float value: " + floatNum);
        // double: Stores fractional numbers. Sufficient for storing 15 decimal digits
        double doubleNum = 19.99d;
        System.out.println("Double value: " + doubleNum);
        // char: Stores a single character/letter or ASCII values
        char letter = 'A';
        System.out.println("Character value: " + letter);

        char myVar1 = 65, myVar2 = 66, myVar3 = 67;
        System.out.println("Characters: " + myVar1 + ", " + myVar2 + ", " + myVar3);
        // boolean: Stores true or false values
        boolean isJavaFun = true;
        System.out.println("Is Java fun? " + isJavaFun);

        // String: the ninth special type in Java (non-primitive)
        String greeting = "Hello, World!";
        System.out.println(greeting);

        // Once a variable is declared with a data type, it can only store values of that type

        // Use float or double? The precision of a float is only six or seven decimal digits, while a double precision is about 15 decimal digits. Therefore, it is safer to use double for most calculations.

        // Scientific numbers can be represented using e or E to indicate the power of 10
        float f1 = 35e3f; // 35 x 10^3
        double d1 = 12E4d; // 12 x 10^4
        System.out.println("Scientific float value: " + f1);
        System.out.println("Scientific double value: " + d1);

        // Non-primitive data types (reference types because refer to object) store references to the memory location where the object is stored

        // Var keyword (Java 10 and later): allows the compiler to infer the data type of a variable based on the assigned value
        var myVar = "Hello"; // inferred as String. Only works when assign a value during declaration
        System.out.println("Var value: " + myVar);

        // when to use var? For simple variables, it's usually clearer to write the type directly (int, double, char, etc). But for more complex types, such as ArrayList or HashMap, var can make the code cleaner and easier to read.
        // Without var
        // ArrayList<String> cars = new ArrayList<String>();

        // with var
        // var cars = new ArrayList<String>();

    }
}
