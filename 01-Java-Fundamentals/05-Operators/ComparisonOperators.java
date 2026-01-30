public class ComparisonOperators {
    public static void main(String[] args) {
        // Comparison Operators are used to compare two values
        // They return a boolean value (true or false)
        int x = 5;
        int y = 3;
        System.out.println("Comparison Operators:");
        System.out.println("x = " + x + ", y = " + y);
        System.out.println("x == y: " + (x == y)); // Equal to
        System.out.println("x != y: " + (x != y)); // Not equal to
        System.out.println("x > y: " + (x > y));   // Greater than
        System.out.println("x < y: " + (x < y));   // Less than
        System.out.println("x >= y: " + (x >= y)); // Greater than or equal to
        System.out.println("x <= y: " + (x <= y)); // Less than or equal to

        // real life example
        // Age verification
        int age = 18;
        System.out.println("Age: " + age);
        System.out.println(age >= 18); // true, old enough to vote
        System.out.println(age < 18); // false, underage to vote
    }
}
