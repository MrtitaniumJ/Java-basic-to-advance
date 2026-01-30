public class ArithmeticOperators {
    public static void main(String[] args) {
        int x = 10;
        int y = 3;

        double a = 10.0d;
        double b = 3.0d;

        System.out.println("Arithmetic Operators:");
        System.out.println("Addition: " + (x + y)); // Outputs 13
        System.out.println("Subtraction: " + (x - y)); // Outputs 7
        System.out.println("Multiplication: " + (x * y));
        System.out.println("Division: " + (x / y)); // Outputs 3
        System.out.println("Modulus: " + (x % y));
        System.out.println("Increment: " + (++x)); // Outputs 11
        System.out.println("Decrement: " + (--y)); // Outputs 2
        System.out.println("Floating Point Division: " + (a / b)); // Outputs 3.3333...

        // Real life example
        // Counting people
        int peopleInRoom = 0;

        // 3 people enter
        peopleInRoom++;
        peopleInRoom++;
        peopleInRoom++;

        System.out.println("People in room after 3 enter: " + peopleInRoom); // Outputs 3

        // 1 person leaves
        peopleInRoom--;

        System.out.println("People in room after 1 leaves: " + peopleInRoom); // Outputs 2
    }
}
