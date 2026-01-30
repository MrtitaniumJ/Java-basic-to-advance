public class LogicalOperators {
    public static void main(String[] args) {
        // Logical Operators are used to combine multiple boolean expressions
        // They return a boolean value (true or false)

        // Examples of logical operators:
        // && -> Logical AND - true if both operands are true
        // || -> Logical OR - true if at least one operand is true
        // !  -> Logical NOT - Reverse the result, returns false if the result is true 

        boolean a = true;
        boolean b = false;

        System.out.println("Logical Operators:");
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("a && b: " + (a && b)); // Logical AND
        System.out.println("a || b: " + (a || b)); // Logical OR
        System.out.println("!a: " + (!a));         // Logical NOT
        System.out.println("!b: " + (!b));         // Logical NOT

        // Real life example
        // Access control
        boolean hasKey = true;
        boolean knowsPassword = false;

        boolean canAccess = hasKey && knowsPassword; // Must have key AND know password
        System.out.println("Can access (hasKey && knowsPassword): " + canAccess);

        canAccess = hasKey || knowsPassword; // Can have key OR know password
        System.out.println("Can access (hasKey || knowsPassword): " + canAccess);

        // login check
        boolean isLoggedIn = true;
        boolean isAdmin = false;

        System.out.println("Regular user: " + (isLoggedIn && !isAdmin));
        System.out.println("Has access: " + (isLoggedIn || isAdmin));
        System.out.println("Not logged in: " + (!isLoggedIn));
    }
}
