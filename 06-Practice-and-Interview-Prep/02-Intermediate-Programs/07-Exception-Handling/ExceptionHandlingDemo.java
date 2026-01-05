/**
 * Exception Handling Demonstration Program
 * Demonstrates: custom exceptions, try-catch-finally blocks, and exception propagation
 * 
 * Learning Objectives:
 * - Master exception handling concepts
 * - Create and use custom exceptions
 * - Understand exception hierarchy
 * - Implement robust error handling
 */

// ===== CUSTOM EXCEPTIONS =====

// Custom exception for invalid age
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Custom exception for insufficient funds
class InsufficientFundsException extends Exception {
    private double shortfall;
    
    public InsufficientFundsException(String message, double shortfall) {
        super(message);
        this.shortfall = shortfall;
    }
    
    public double getShortfall() {
        return shortfall;
    }
}

// Custom exception for divide by zero
class DivisionByZeroException extends ArithmeticException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}

// Custom exception for invalid input
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
    
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class ExceptionHandlingDemo {
    
    // ===== BASIC TRY-CATCH =====
    
    // Method demonstrating basic try-catch
    public static void basicTryCatch() {
        System.out.println("--- BASIC TRY-CATCH ---");
        try {
            int[] arr = {1, 2, 3};
            System.out.println("Accessing array element: " + arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught exception: " + e.getMessage());
            System.out.println("Exception type: " + e.getClass().getSimpleName());
        }
        System.out.println("Program continues after exception\n");
    }
    
    // ===== MULTIPLE CATCH BLOCKS =====
    
    // Method demonstrating multiple catch blocks
    public static void multipleCatchBlocks(String input) {
        System.out.println("--- MULTIPLE CATCH BLOCKS ---");
        try {
            int number = Integer.parseInt(input);
            int result = 100 / number;
            System.out.println("Result: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Caught: Invalid number format - " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Caught: Arithmetic error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught: Generic exception - " + e.getMessage());
        }
        System.out.println();
    }
    
    // ===== TRY-CATCH-FINALLY =====
    
    // Method demonstrating try-catch-finally block
    public static void tryCatchFinally() {
        System.out.println("--- TRY-CATCH-FINALLY ---");
        try {
            System.out.println("Inside try block");
            int result = 10 / 2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            System.out.println("Finally block always executes");
        }
        System.out.println();
    }
    
    // ===== CUSTOM EXCEPTIONS =====
    
    // Method that throws custom exception
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age must be between 0 and 150, but got: " + age);
        }
        System.out.println("Age " + age + " is valid");
    }
    
    // Method using custom exception
    public static void demonstrateCustomException() {
        System.out.println("--- CUSTOM EXCEPTIONS ---");
        
        int[] ages = {25, -5, 200};
        
        for (int age : ages) {
            try {
                validateAge(age);
            } catch (InvalidAgeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println();
    }
    
    // ===== EXCEPTION WITH ADDITIONAL DATA =====
    
    // Method throwing exception with additional information
    public static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            double shortfall = amount - balance;
            throw new InsufficientFundsException(
                "Cannot withdraw " + amount + " from balance " + balance,
                shortfall
            );
        }
        System.out.println("Successfully withdrew " + amount);
    }
    
    // Demonstrate exception with extra data
    public static void demonstrateExceptionData() {
        System.out.println("--- EXCEPTION WITH DATA ---");
        try {
            withdraw(100, 150);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Shortfall amount: " + e.getShortfall());
        }
        System.out.println();
    }
    
    // ===== EXCEPTION PROPAGATION =====
    
    // Inner method that throws exception
    public static void methodC() throws InvalidInputException {
        throw new InvalidInputException("Error occurred in methodC");
    }
    
    // Middle method that propagates exception
    public static void methodB() throws InvalidInputException {
        methodC();
    }
    
    // Outer method that calls methodB
    public static void methodA() {
        System.out.println("--- EXCEPTION PROPAGATION ---");
        try {
            methodB();
        } catch (InvalidInputException e) {
            System.out.println("Caught in methodA: " + e.getMessage());
            System.out.println("Exception propagated through: methodC -> methodB -> methodA");
        }
        System.out.println();
    }
    
    // ===== CHAINED EXCEPTIONS =====
    
    // Method demonstrating exception chaining
    public static void parseAndValidate(String input) throws InvalidInputException {
        try {
            int number = Integer.parseInt(input);
            if (number < 0) {
                throw new InvalidInputException("Number must be non-negative");
            }
        } catch (NumberFormatException e) {
            // Chain the original exception as cause
            throw new InvalidInputException("Failed to parse input: " + input, e);
        }
    }
    
    // Demonstrate chained exceptions
    public static void demonstrateChainedExceptions() {
        System.out.println("--- CHAINED EXCEPTIONS ---");
        try {
            parseAndValidate("abc");
        } catch (InvalidInputException e) {
            System.out.println("Main message: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
            System.out.println("Cause message: " + e.getCause().getMessage());
        }
        System.out.println();
    }
    
    // ===== TRY-WITH-RESOURCES =====
    
    // Resource class implementing AutoCloseable
    static class Resource implements AutoCloseable {
        private String name;
        
        public Resource(String name) {
            this.name = name;
            System.out.println("Opening resource: " + name);
        }
        
        public void use() {
            System.out.println("Using resource: " + name);
        }
        
        @Override
        public void close() throws Exception {
            System.out.println("Closing resource: " + name);
        }
    }
    
    // Demonstrate try-with-resources (Java 7+)
    public static void demonstrateTryWithResources() {
        System.out.println("--- TRY-WITH-RESOURCES ---");
        try (Resource resource = new Resource("FileReader")) {
            resource.use();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println();
    }
    
    // ===== THROWS DECLARATION =====
    
    // Method that declares throws but doesn't handle exception
    public static int safeDivide(int a, int b) throws DivisionByZeroException {
        if (b == 0) {
            throw new DivisionByZeroException("Cannot divide by zero");
        }
        return a / b;
    }
    
    // Call method with throws declaration
    public static void demonstrateThrows() {
        System.out.println("--- THROWS DECLARATION ---");
        try {
            int result = safeDivide(10, 0);
            System.out.println("Result: " + result);
        } catch (DivisionByZeroException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        System.out.println();
    }
    
    // ===== NESTED TRY-CATCH =====
    
    // Demonstrate nested try-catch blocks
    public static void nestedTryCatch() {
        System.out.println("--- NESTED TRY-CATCH ---");
        try {
            int[] arr = {1, 2, 3};
            try {
                int value = arr[5];
                int result = 10 / value;
            } catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e.getMessage());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Outer catch: " + e.getMessage());
        }
        System.out.println();
    }
    
    // ===== FINAL METHOD =====
    
    // Demonstrate finally always executing
    public static void finallyAlwaysExecutes() {
        System.out.println("--- FINALLY ALWAYS EXECUTES ---");
        
        for (int i = 1; i <= 3; i++) {
            try {
                System.out.println("Iteration " + i);
                if (i == 2) {
                    throw new RuntimeException("Error on iteration 2");
                }
            } catch (RuntimeException e) {
                System.out.println("Caught: " + e.getMessage());
            } finally {
                System.out.println("Finally block executes on iteration " + i);
            }
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("===== EXCEPTION HANDLING DEMO =====\n");
        
        basicTryCatch();
        
        multipleCatchBlocks("abc");
        multipleCatchBlocks("0");
        
        tryCatchFinally();
        
        demonstrateCustomException();
        
        demonstrateExceptionData();
        
        methodA();
        
        demonstrateChainedExceptions();
        
        demonstrateTryWithResources();
        
        demonstrateThrows();
        
        nestedTryCatch();
        
        finallyAlwaysExecutes();
        
        System.out.println("Program completed successfully");
    }
}
