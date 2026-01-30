public class OperatorsPrecedence {
    public static void main(String[] args) {
        // when a calculation contains more than one operator, Java follows order of operations rules to decide which part calculate first.

        // order of operations:
        // 1. Parentheses ()
        // 2. *, /, % - Multiplication, Division, Modulus (from left to right)
        // 3. +, - - Addition, Subtraction (from left to right)
        // 4. >, <, >=, <= - Comparison Operators
        // 5. ==, != - Equality Operators
        // 6. && - Logical AND
        // 7. || - Logical OR
        // 8. = - Assignment

        // example 1
        int result1 = 2 + 3 * 4;
        int result2 = (2 + 3) * 4;
        System.out.println("Result without parentheses: " + result1); // Outputs 14
        System.out.println("Result with parentheses: " + result2);    // Outputs 20

        // example 2
        int result3 = 10 - 2 + 5;
        int result4 = 10 - (2 + 5);
        System.out.println("Result without parentheses: " + result3); // Outputs 13
        System.out.println("Result with parentheses: " + result4);    // Outputs 3
    }
}
