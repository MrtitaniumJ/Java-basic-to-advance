# Exception Handling in Java - Robust Error Management

## Simple Explanation

Think of **Exception Handling** as **insurance and safety protocols**:

- **Exceptions** = Unexpected problems that occur while your program runs
- **Try-catch** = Safety nets to catch and handle problems gracefully
- **Finally** = Cleanup actions that always happen (like closing files)
- **Throw** = Reporting a problem when it happens
- **Custom exceptions** = Creating specific error types for your business

### Real-World Analogies
- **Car safety systems** = Exception handling (airbags, seatbelts, crumple zones)
- **Building fire exits** = Exception handling (planned escape routes for emergencies)
- **Medical insurance** = Exception handling (coverage for unexpected health issues)
- **Backup generators** = Exception handling (power alternatives when main supply fails)

## Professional Definition

**Exception Handling** in Java is a robust mechanism for dealing with runtime errors and exceptional conditions that can disrupt normal program execution. It uses a structured approach with try-catch-finally blocks, custom exception classes, and a hierarchy of exception types to ensure graceful error recovery, resource cleanup, and maintainable error management across application layers.

## Why Exception Handling is Critical

### Problems Without Exception Handling:
```java
// WITHOUT PROPER EXCEPTION HANDLING - Fragile and unreliable code

import java.io.*;
import java.util.Scanner;

class ProblematicBankingSystem {
    private double balance;
    
    public void withdraw(double amount) {
        // No validation - can cause negative balance
        balance = balance - amount;
        System.out.println("Withdrawn: $" + amount);
        System.out.println("Balance: $" + balance);
    }
    
    public void readAccountFile(String filename) {
        // No exception handling - program crashes if file doesn't exist
        Scanner scanner = new Scanner(new File(filename)); // FileNotFoundException!
        balance = scanner.nextDouble(); // NumberFormatException if invalid format!
        scanner.close(); // May not execute if exception occurs above!
    }
    
    public void divideBalance(int divisor) {
        // No zero-check - ArithmeticException crashes program
        double result = balance / divisor; // Division by zero!
        System.out.println("Division result: " + result);
    }
    
    public void accessArray(int[] array, int index) {
        // No bounds checking - ArrayIndexOutOfBoundsException
        array[index] = 100; // Crashes if index is invalid!
    }
    
    // PROBLEMS:
    // 1. Program crashes on any error
    // 2. Resources may not be cleaned up
    // 3. No graceful error recovery
    // 4. Poor user experience
    // 5. Difficult to debug and maintain
}

class ProblematicMain {
    public static void main(String[] args) {
        ProblematicBankingSystem bank = new ProblematicBankingSystem();
        
        // These operations can crash the entire program!
        bank.readAccountFile("nonexistent.txt");      // CRASH!
        bank.divideBalance(0);                         // CRASH!
        bank.accessArray(new int[5], 10);            // CRASH!
        
        System.out.println("This line may never execute!");
    }
}
```

### With Proper Exception Handling:
```java
// WITH PROPER EXCEPTION HANDLING - Robust and reliable code

import java.io.*;
import java.util.Scanner;

class RobustBankingSystem {
    private double balance = 0.0;
    private String accountNumber;
    
    public RobustBankingSystem(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public boolean withdraw(double amount) {
        try {
            validateAmount(amount);
            
            if (amount > balance) {
                throw new InsufficientFundsException(
                    "Insufficient funds. Balance: $" + balance + ", Requested: $" + amount
                );
            }
            
            balance -= amount;
            System.out.println("Successfully withdrawn: $" + amount);
            System.out.println("Remaining balance: $" + balance);
            return true;
            
        } catch (InvalidAmountException e) {
            System.err.println("Invalid withdrawal amount: " + e.getMessage());
            return false;
        } catch (InsufficientFundsException e) {
            System.err.println("Withdrawal failed: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error during withdrawal: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loadAccountData(String filename) {
        Scanner scanner = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileNotFoundException("Account file not found: " + filename);
            }
            
            scanner = new Scanner(file);
            
            if (scanner.hasNextDouble()) {
                double loadedBalance = scanner.nextDouble();
                if (loadedBalance < 0) {
                    throw new InvalidDataException("Invalid balance in file: " + loadedBalance);
                }
                balance = loadedBalance;
                System.out.println("Account data loaded successfully. Balance: $" + balance);
                return true;
            } else {
                throw new InvalidDataException("Invalid balance format in file");
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("File error: " + e.getMessage());
            System.out.println("Creating new account with zero balance.");
            balance = 0.0;
            return false;
        } catch (InvalidDataException e) {
            System.err.println("Data error: " + e.getMessage());
            balance = 0.0;
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error reading file: " + e.getMessage());
            balance = 0.0;
            return false;
        } finally {
            // Always close the scanner, even if exception occurs
            if (scanner != null) {
                scanner.close();
                System.out.println("File resources cleaned up.");
            }
        }
    }
    
    public double calculateInterest(int years) {
        try {
            if (years <= 0) {
                throw new IllegalArgumentException("Years must be positive: " + years);
            }
            
            if (years > 100) {
                throw new IllegalArgumentException("Years cannot exceed 100: " + years);
            }
            
            double interestRate = 0.05; // 5% annual interest
            double interest = balance * interestRate * years;
            
            System.out.println("Interest for " + years + " years: $" + interest);
            return interest;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid years parameter: " + e.getMessage());
            return 0.0;
        } catch (ArithmeticException e) {
            System.err.println("Calculation error: " + e.getMessage());
            return 0.0;
        }
    }
    
    public boolean processArrayOperation(int[] array, int index, int value) {
        try {
            validateArray(array);
            validateIndex(array, index);
            
            int oldValue = array[index];
            array[index] = value;
            
            System.out.println("Array updated at index " + index + 
                             ": " + oldValue + " -> " + value);
            return true;
            
        } catch (NullPointerException e) {
            System.err.println("Array is null: " + e.getMessage());
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid array index: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error in array operation: " + e.getMessage());
            return false;
        }
    }
    
    // Helper validation methods that throw specific exceptions
    private void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive: " + amount);
        }
        if (amount > 10000) {
            throw new InvalidAmountException("Amount exceeds daily limit: " + amount);
        }
    }
    
    private void validateArray(int[] array) {
        if (array == null) {
            throw new NullPointerException("Array cannot be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
    }
    
    private void validateIndex(int[] array, int index) {
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException(
                "Index " + index + " is out of bounds for array length " + array.length
            );
        }
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        try {
            validateAmount(amount);
            balance += amount;
            System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
        } catch (InvalidAmountException e) {
            System.err.println("Invalid deposit amount: " + e.getMessage());
        }
    }
}

// Custom exception classes
class BankingException extends Exception {
    public BankingException(String message) {
        super(message);
    }
    
    public BankingException(String message, Throwable cause) {
        super(message, cause);
    }
}

class InvalidAmountException extends BankingException {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends BankingException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class InvalidDataException extends BankingException {
    public InvalidDataException(String message) {
        super(message);
    }
}

class RobustMain {
    public static void main(String[] args) {
        System.out.println("=== ROBUST BANKING SYSTEM ===");
        
        RobustBankingSystem bank = new RobustBankingSystem("ACC12345");
        
        // All operations handle errors gracefully
        bank.loadAccountData("account.txt");  // Handles file errors gracefully
        bank.deposit(1000);                    // Safe deposit
        bank.withdraw(200);                    // Safe withdrawal
        bank.withdraw(2000);                   // Handles insufficient funds
        bank.calculateInterest(-5);           // Handles invalid input
        bank.processArrayOperation(new int[5], 10, 100); // Handles array errors
        
        System.out.println("Program completed successfully!");
        System.out.println("Final balance: $" + bank.getBalance());
    }
}
```

## Exception Hierarchy and Types

### 1. Java Exception Hierarchy
```java
/*
                    Throwable
                    /       \
              Error               Exception
             /     \             /        \
    OutOfMemoryError    RuntimeException    Checked Exceptions
    StackOverflowError   /       |      \   /              \
    etc.         NullPointer ArrayIndex  IllegalArgument  IOException
                 Exception   OutOfBounds Exception         SQLException
                           Exception                        etc.
*/

import java.io.*;
import java.sql.*;
import java.util.*;

public class ExceptionHierarchyDemo {
    
    // 1. CHECKED EXCEPTIONS - Must be handled or declared
    public void demonstrateCheckedExceptions() {
        System.out.println("=== CHECKED EXCEPTIONS ===");
        
        // IOException - File operations
        try {
            FileReader file = new FileReader("data.txt");
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            System.out.println("File content: " + line);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
        
        // SQLException - Database operations
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            System.out.println("Database query executed");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        
        // ClassNotFoundException - Class loading
        try {
            Class<?> clazz = Class.forName("com.example.NonExistentClass");
            System.out.println("Class loaded: " + clazz.getName());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        
        // ParseException - Date parsing
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("2024-13-45"); // Invalid date
            System.out.println("Parsed date: " + date);
        } catch (java.text.ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
        }
    }
    
    // 2. UNCHECKED EXCEPTIONS (Runtime Exceptions) - Optional handling
    public void demonstrateUncheckedExceptions() {
        System.out.println("\n=== UNCHECKED EXCEPTIONS ===");
        
        // NullPointerException
        try {
            String str = null;
            int length = str.length(); // NPE!
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        }
        
        // ArrayIndexOutOfBoundsException
        try {
            int[] array = {1, 2, 3};
            System.out.println(array[5]); // Index out of bounds!
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index error: " + e.getMessage());
        }
        
        // NumberFormatException
        try {
            int number = Integer.parseInt("abc"); // Invalid number!
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + e.getMessage());
        }
        
        // IllegalArgumentException
        try {
            List<String> list = new ArrayList<>();
            String item = list.get(-1); // Negative index!
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds: " + e.getMessage());
        }
        
        // ArithmeticException
        try {
            int result = 10 / 0; // Division by zero!
        } catch (ArithmeticException e) {
            System.err.println("Arithmetic error: " + e.getMessage());
        }
        
        // ClassCastException
        try {
            Object obj = "Hello";
            Integer number = (Integer) obj; // Invalid cast!
        } catch (ClassCastException e) {
            System.err.println("Class cast error: " + e.getMessage());
        }
        
        // ConcurrentModificationException
        try {
            List<String> list = new ArrayList<>();
            list.add("A");
            list.add("B");
            
            // Modifying list while iterating
            for (String item : list) {
                if ("A".equals(item)) {
                    list.remove(item); // ConcurrentModificationException!
                }
            }
        } catch (ConcurrentModificationException e) {
            System.err.println("Concurrent modification error: " + e.getMessage());
        }
    }
    
    // 3. ERRORS - Serious system problems, usually not handled
    public void demonstrateErrors() {
        System.out.println("\n=== ERRORS (Usually Not Handled) ===");
        
        try {
            // StackOverflowError simulation
            recursiveMethod();
        } catch (StackOverflowError e) {
            System.err.println("Stack overflow error caught (not recommended)");
        }
        
        try {
            // OutOfMemoryError simulation (commented out to prevent actual OOM)
            // List<int[]> memoryHog = new ArrayList<>();
            // while (true) {
            //     memoryHog.add(new int[1000000]);
            // }
            System.out.println("OutOfMemoryError simulation skipped");
        } catch (OutOfMemoryError e) {
            System.err.println("Out of memory error: " + e.getMessage());
        }
    }
    
    private void recursiveMethod() {
        recursiveMethod(); // Infinite recursion causes StackOverflowError
    }
}
```

### 2. Custom Exception Classes
```java
// Custom exception hierarchy for an e-commerce system

// Base business exception
abstract class ECommerceException extends Exception {
    private String errorCode;
    private long timestamp;
    
    public ECommerceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
    
    public ECommerceException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        return new Date(timestamp).toString();
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s] at %s: %s", 
                           getClass().getSimpleName(), 
                           errorCode, 
                           getFormattedTimestamp(), 
                           getMessage());
    }
}

// Product-related exceptions
class ProductException extends ECommerceException {
    public ProductException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    public ProductException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}

class ProductNotFoundException extends ProductException {
    private String productId;
    
    public ProductNotFoundException(String productId) {
        super("Product not found: " + productId, "PRODUCT_NOT_FOUND");
        this.productId = productId;
    }
    
    public String getProductId() {
        return productId;
    }
}

class InsufficientStockException extends ProductException {
    private String productId;
    private int available;
    private int requested;
    
    public InsufficientStockException(String productId, int available, int requested) {
        super(String.format("Insufficient stock for product %s. Available: %d, Requested: %d", 
                          productId, available, requested), "INSUFFICIENT_STOCK");
        this.productId = productId;
        this.available = available;
        this.requested = requested;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public int getAvailable() {
        return available;
    }
    
    public int getRequested() {
        return requested;
    }
}

// User-related exceptions
class UserException extends ECommerceException {
    public UserException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    public UserException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}

class InvalidUserCredentialsException extends UserException {
    public InvalidUserCredentialsException(String username) {
        super("Invalid credentials for user: " + username, "INVALID_CREDENTIALS");
    }
}

class UserAccountLockedException extends UserException {
    private Date lockoutExpiry;
    
    public UserAccountLockedException(String username, Date lockoutExpiry) {
        super("Account locked for user: " + username + " until " + lockoutExpiry, "ACCOUNT_LOCKED");
        this.lockoutExpiry = lockoutExpiry;
    }
    
    public Date getLockoutExpiry() {
        return lockoutExpiry;
    }
}

// Payment-related exceptions
class PaymentException extends ECommerceException {
    public PaymentException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    public PaymentException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}

class PaymentDeclinedException extends PaymentException {
    private String transactionId;
    private String reason;
    
    public PaymentDeclinedException(String transactionId, String reason) {
        super("Payment declined for transaction " + transactionId + ": " + reason, "PAYMENT_DECLINED");
        this.transactionId = transactionId;
        this.reason = reason;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getReason() {
        return reason;
    }
}

class InsufficientFundsException extends PaymentException {
    private double balance;
    private double required;
    
    public InsufficientFundsException(double balance, double required) {
        super(String.format("Insufficient funds. Balance: $%.2f, Required: $%.2f", 
                          balance, required), "INSUFFICIENT_FUNDS");
        this.balance = balance;
        this.required = required;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public double getRequired() {
        return required;
    }
}

// Order-related exceptions
class OrderException extends ECommerceException {
    public OrderException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    public OrderException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}

class OrderNotFoundException extends OrderException {
    private String orderId;
    
    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId, "ORDER_NOT_FOUND");
        this.orderId = orderId;
    }
    
    public String getOrderId() {
        return orderId;
    }
}

class OrderCancellationException extends OrderException {
    private String orderId;
    private String status;
    
    public OrderCancellationException(String orderId, String status) {
        super("Cannot cancel order " + orderId + " in status: " + status, "ORDER_CANCELLATION_FAILED");
        this.orderId = orderId;
        this.status = status;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getStatus() {
        return status;
    }
}
```

## Try-Catch-Finally Blocks

### 1. Basic Try-Catch Structure
```java
import java.io.*;
import java.util.Scanner;

public class TryCatchFinallyDemo {
    
    // Single catch block
    public void basicTryCatch() {
        System.out.println("=== BASIC TRY-CATCH ===");
        
        try {
            int result = 10 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.err.println("Cannot divide by zero: " + e.getMessage());
        }
        
        System.out.println("Program continues normally");
    }
    
    // Multiple catch blocks
    public void multipleCatchBlocks() {
        System.out.println("\n=== MULTIPLE CATCH BLOCKS ===");
        
        String[] data = {"10", "20", "abc", "30"};
        int[] results = new int[3];
        
        for (int i = 0; i < data.length; i++) {
            try {
                int number = Integer.parseInt(data[i]);
                results[i] = number * 2;
                System.out.println("Processed " + data[i] + " -> " + results[i]);
                
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + data[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Array index out of bounds for index: " + i);
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
    }
    
    // Multi-catch (Java 7+)
    public void multiCatch() {
        System.out.println("\n=== MULTI-CATCH ===");
        
        try {
            // Simulate various operations that might fail
            String input = null; // Could come from user
            
            if (input == null) {
                throw new NullPointerException("Input is null");
            }
            
            int number = Integer.parseInt(input);
            int[] array = {1, 2, 3};
            array[number] = 100;
            
        } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Input/Array error: " + e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Other error: " + e.getMessage());
        }
    }
    
    // Try-catch-finally
    public void tryCatchFinally() {
        System.out.println("\n=== TRY-CATCH-FINALLY ===");
        
        FileWriter writer = null;
        try {
            writer = new FileWriter("output.txt");
            writer.write("Hello, World!");
            
            // Simulate an error
            int error = 1 / 0; // This will throw exception
            
        } catch (ArithmeticException e) {
            System.err.println("Arithmetic error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } finally {
            // This ALWAYS executes, even if exception occurs
            System.out.println("Cleaning up resources in finally block");
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("File writer closed successfully");
                } catch (IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
    }
    
    // Try-with-resources (Java 7+) - Automatic resource management
    public void tryWithResources() {
        System.out.println("\n=== TRY-WITH-RESOURCES ===");
        
        // Single resource
        try (FileWriter writer = new FileWriter("auto-close.txt")) {
            writer.write("This file will be closed automatically");
            
            // Simulate error
            throw new RuntimeException("Simulated error");
            
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Runtime error: " + e.getMessage());
        }
        // FileWriter is automatically closed here, even if exception occurred
        System.out.println("FileWriter automatically closed by try-with-resources");
        
        // Multiple resources
        try (FileReader reader = new FileReader("input.txt");
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter("copy.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error during file copy: " + e.getMessage());
        }
        // All resources are automatically closed in reverse order
    }
    
    // Nested try-catch blocks
    public void nestedTryCatch() {
        System.out.println("\n=== NESTED TRY-CATCH ===");
        
        try {
            System.out.println("Outer try block");
            
            try {
                System.out.println("Inner try block");
                int result = Integer.parseInt("abc");
                
            } catch (NumberFormatException e) {
                System.err.println("Inner catch: Number format error");
                
                // Throwing another exception from catch block
                throw new IllegalArgumentException("Converted from NumberFormatException", e);
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Outer catch: " + e.getMessage());
            System.err.println("Original cause: " + e.getCause().getClass().getSimpleName());
        }
    }
    
    // Finally block execution scenarios
    public void finallyExecutionScenarios() {
        System.out.println("\n=== FINALLY EXECUTION SCENARIOS ===");
        
        // Scenario 1: Normal execution
        try {
            System.out.println("Normal execution");
        } finally {
            System.out.println("Finally: Normal case");
        }
        
        // Scenario 2: Exception caught
        try {
            throw new RuntimeException("Test exception");
        } catch (RuntimeException e) {
            System.out.println("Exception caught");
        } finally {
            System.out.println("Finally: Exception caught case");
        }
        
        // Scenario 3: Exception not caught (would terminate program)
        try {
            System.out.println("Finally executes even if exception is not caught");
        } finally {
            System.out.println("Finally: Exception not caught case");
        }
        
        // Scenario 4: Return in try block
        String result = methodWithReturnInTry();
        System.out.println("Returned: " + result);
    }
    
    private String methodWithReturnInTry() {
        try {
            return "Return from try";
        } finally {
            System.out.println("Finally: Even with return in try");
            // Note: Don't return from finally - it overrides try's return!
        }
    }
    
    // Exception information methods
    public void exceptionInformation() {
        System.out.println("\n=== EXCEPTION INFORMATION ===");
        
        try {
            throw new RuntimeException("This is a test exception");
        } catch (Exception e) {
            System.out.println("Exception class: " + e.getClass().getName());
            System.out.println("Exception message: " + e.getMessage());
            System.out.println("Exception cause: " + e.getCause());
            
            System.out.println("\nStack trace:");
            e.printStackTrace();
            
            System.out.println("\nStack trace elements:");
            StackTraceElement[] elements = e.getStackTrace();
            for (StackTraceElement element : elements) {
                System.out.println("  " + element.getClassName() + "." + 
                                 element.getMethodName() + "(" + 
                                 element.getFileName() + ":" + 
                                 element.getLineNumber() + ")");
            }
        }
    }
}
```

### 2. Advanced Exception Handling Patterns
```java
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

public class AdvancedExceptionHandling {
    
    // Exception chaining and wrapping
    public void demonstrateExceptionChaining() {
        System.out.println("=== EXCEPTION CHAINING ===");
        
        try {
            performDatabaseOperation();
        } catch (ServiceException e) {
            System.err.println("Service error: " + e.getMessage());
            System.err.println("Root cause: " + findRootCause(e).getMessage());
            
            // Print full chain
            Throwable current = e;
            int level = 0;
            while (current != null) {
                System.err.println("Level " + level + ": " + 
                                 current.getClass().getSimpleName() + " - " + 
                                 current.getMessage());
                current = current.getCause();
                level++;
            }
        }
    }
    
    private void performDatabaseOperation() throws ServiceException {
        try {
            performLowLevelOperation();
        } catch (Exception e) {
            // Wrap lower-level exception in business exception
            throw new ServiceException("Database operation failed", e);
        }
    }
    
    private void performLowLevelOperation() throws Exception {
        try {
            // Simulate low-level error
            throw new IllegalStateException("Connection pool exhausted");
        } catch (IllegalStateException e) {
            // Chain with more context
            throw new RuntimeException("Low-level operation failed", e);
        }
    }
    
    private Throwable findRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
    
    // Exception suppression (try-with-resources)
    public void demonstrateExceptionSuppression() {
        System.out.println("\n=== EXCEPTION SUPPRESSION ===");
        
        try (ProblematicResource resource = new ProblematicResource()) {
            resource.doSomething();
            throw new RuntimeException("Primary exception");
        } catch (Exception e) {
            System.err.println("Primary exception: " + e.getMessage());
            
            Throwable[] suppressed = e.getSuppressed();
            System.err.println("Suppressed exceptions: " + suppressed.length);
            
            for (Throwable t : suppressed) {
                System.err.println("  Suppressed: " + t.getMessage());
            }
        }
    }
    
    // Functional exception handling
    public void functionalExceptionHandling() {
        System.out.println("\n=== FUNCTIONAL EXCEPTION HANDLING ===");
        
        List<String> inputs = Arrays.asList("10", "20", "abc", "30", "xyz", "40");
        
        // Traditional approach
        List<Integer> results1 = new ArrayList<>();
        for (String input : inputs) {
            try {
                results1.add(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.err.println("Skipping invalid input: " + input);
            }
        }
        System.out.println("Traditional results: " + results1);
        
        // Functional approach with Optional
        List<Integer> results2 = inputs.stream()
            .map(this::safeParseInt)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Functional results: " + results2);
        
        // With custom result wrapper
        List<Result<Integer>> results3 = inputs.stream()
            .map(this::parseIntResult)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("Results with error info:");
        results3.forEach(result -> {
            if (result.isSuccess()) {
                System.out.println("  Success: " + result.getValue());
            } else {
                System.out.println("  Error: " + result.getError().getMessage());
            }
        });
    }
    
    private Optional<Integer> safeParseInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    private Result<Integer> parseIntResult(String s) {
        try {
            return Result.success(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Result.failure(e);
        }
    }
}

// Custom classes for demonstrations
class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ProblematicResource implements AutoCloseable {
    public void doSomething() {
        System.out.println("ProblematicResource doing something");
    }
    
    @Override
    public void close() throws Exception {
        throw new RuntimeException("Error closing resource");
    }
}

// Result wrapper for functional exception handling
class Result<T> {
    private final T value;
    private final Exception error;
    
    private Result(T value, Exception error) {
        this.value = value;
        this.error = error;
    }
    
    public static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }
    
    public static <T> Result<T> failure(Exception error) {
        return new Result<>(null, error);
    }
    
    public boolean isSuccess() {
        return error == null;
    }
    
    public T getValue() {
        if (error != null) {
            throw new RuntimeException("Cannot get value from failed result", error);
        }
        return value;
    }
    
    public Exception getError() {
        return error;
    }
    
    public <U> Result<U> map(Function<T, U> mapper) {
        if (isSuccess()) {
            try {
                return success(mapper.apply(value));
            } catch (Exception e) {
                return failure(e);
            }
        } else {
            return failure(error);
        }
    }
}
```

## Throwing and Re-throwing Exceptions

### 1. Throw Statement and Custom Exceptions
```java
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class ThrowingExceptionsDemo {
    
    // Basic throw usage
    public void validateAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new InvalidAgeException("Age seems unrealistic: " + age);
        }
        System.out.println("Valid age: " + age);
    }
    
    // Throwing checked exceptions
    public void processUser(String name, String email, LocalDate birthDate) 
            throws UserValidationException, InvalidAgeException {
        
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new UserValidationException("Name cannot be empty", "INVALID_NAME");
        }
        
        if (name.length() > 50) {
            throw new UserValidationException("Name too long: " + name.length() + " characters", "NAME_TOO_LONG");
        }
        
        // Validate email
        validateEmail(email);
        
        // Validate age
        int age = calculateAge(birthDate);
        validateAge(age);
        
        System.out.println("User processed successfully: " + name + " (" + age + " years old)");
    }
    
    private void validateEmail(String email) throws UserValidationException {
        if (email == null) {
            throw new UserValidationException("Email cannot be null", "EMAIL_NULL");
        }
        
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        if (!emailPattern.matcher(email).matches()) {
            throw new UserValidationException("Invalid email format: " + email, "INVALID_EMAIL_FORMAT");
        }
    }
    
    private int calculateAge(LocalDate birthDate) throws UserValidationException {
        if (birthDate == null) {
            throw new UserValidationException("Birth date cannot be null", "BIRTHDATE_NULL");
        }
        
        if (birthDate.isAfter(LocalDate.now())) {
            throw new UserValidationException("Birth date cannot be in the future: " + birthDate, "FUTURE_BIRTHDATE");
        }
        
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    // Throwing runtime exceptions
    public void processPayment(double amount, String currency) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive: " + amount);
        }
        
        if (amount > 10000) {
            throw new IllegalArgumentException("Payment amount exceeds limit: " + amount);
        }
        
        if (currency == null) {
            throw new NullPointerException("Currency cannot be null");
        }
        
        if (!isValidCurrency(currency)) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
        
        System.out.println("Processing payment: " + amount + " " + currency);
    }
    
    private boolean isValidCurrency(String currency) {
        return Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD").contains(currency.toUpperCase());
    }
    
    // Re-throwing exceptions with additional context
    public void executeBusinessLogic(String operation, Map<String, Object> params) 
            throws BusinessLogicException {
        
        try {
            switch (operation) {
                case "CREATE_USER":
                    createUser(params);
                    break;
                case "PROCESS_PAYMENT":
                    processPaymentInternal(params);
                    break;
                case "SEND_EMAIL":
                    sendEmail(params);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operation: " + operation);
            }
        } catch (UserValidationException e) {
            // Re-throw as business logic exception with context
            throw new BusinessLogicException("User creation failed in operation: " + operation, e);
        } catch (PaymentException e) {
            // Re-throw with additional business context
            throw new BusinessLogicException("Payment processing failed in operation: " + operation, e);
        } catch (EmailException e) {
            // Re-throw with context
            throw new BusinessLogicException("Email sending failed in operation: " + operation, e);
        } catch (Exception e) {
            // Catch any unexpected exceptions and wrap them
            throw new BusinessLogicException("Unexpected error in operation: " + operation, e);
        }
    }
    
    private void createUser(Map<String, Object> params) throws UserValidationException {
        try {
            String name = (String) params.get("name");
            String email = (String) params.get("email");
            LocalDate birthDate = (LocalDate) params.get("birthDate");
            
            processUser(name, email, birthDate);
        } catch (InvalidAgeException e) {
            // Convert to UserValidationException
            throw new UserValidationException("Invalid user age: " + e.getMessage(), "INVALID_AGE", e);
        }
    }
    
    private void processPaymentInternal(Map<String, Object> params) throws PaymentException {
        try {
            Double amount = (Double) params.get("amount");
            String currency = (String) params.get("currency");
            
            if (amount == null) {
                throw new PaymentException("Amount is required", "MISSING_AMOUNT");
            }
            
            processPayment(amount, currency);
        } catch (IllegalArgumentException e) {
            throw new PaymentException("Payment validation failed: " + e.getMessage(), "PAYMENT_VALIDATION", e);
        } catch (NullPointerException e) {
            throw new PaymentException("Required payment parameter is null: " + e.getMessage(), "NULL_PARAMETER", e);
        }
    }
    
    private void sendEmail(Map<String, Object> params) throws EmailException {
        String recipient = (String) params.get("recipient");
        String subject = (String) params.get("subject");
        String body = (String) params.get("body");
        
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new EmailException("Recipient email is required", "MISSING_RECIPIENT");
        }
        
        if (subject == null || subject.trim().isEmpty()) {
            throw new EmailException("Email subject is required", "MISSING_SUBJECT");
        }
        
        // Simulate email sending that might fail
        if (recipient.contains("invalid")) {
            throw new EmailException("Invalid recipient email: " + recipient, "INVALID_RECIPIENT");
        }
        
        System.out.println("Email sent to: " + recipient);
    }
    
    // Method that demonstrates exception propagation
    public void handleUserRequest(String userId, String operation, Map<String, Object> params) {
        try {
            System.out.println("Handling request for user: " + userId);
            executeBusinessLogic(operation, params);
            System.out.println("Request completed successfully");
            
        } catch (BusinessLogicException e) {
            System.err.println("Business logic error for user " + userId + ": " + e.getMessage());
            
            // Log the full chain of exceptions
            logExceptionChain(e);
            
            // Could re-throw or handle based on business requirements
            // throw new ServiceException("Service temporarily unavailable", e);
            
        } catch (Exception e) {
            System.err.println("Unexpected error for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void logExceptionChain(Throwable throwable) {
        System.err.println("Exception chain:");
        Throwable current = throwable;
        int level = 0;
        
        while (current != null) {
            System.err.println("  Level " + level + ": " + current.getClass().getSimpleName() + 
                             " - " + current.getMessage());
            current = current.getCause();
            level++;
        }
    }
}

// Custom exception classes
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class UserValidationException extends Exception {
    private String errorCode;
    
    public UserValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public UserValidationException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

class PaymentException extends Exception {
    private String errorCode;
    
    public PaymentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public PaymentException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

class EmailException extends Exception {
    private String errorCode;
    
    public EmailException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

class BusinessLogicException extends Exception {
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

## Complete Exception Handling Demo

```java
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CompleteExceptionHandlingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE EXCEPTION HANDLING DEMONSTRATION ===");
        
        CompleteExceptionHandlingDemo demo = new CompleteExceptionHandlingDemo();
        
        demo.demonstrateExceptionTypes();
        demo.demonstrateTryCatchFinally();
        demo.demonstrateCustomExceptions();
        demo.demonstrateRealWorldScenarios();
    }
    
    public void demonstrateExceptionTypes() {
        System.out.println("\n--- EXCEPTION TYPES ---");
        
        ExceptionHierarchyDemo hierarchy = new ExceptionHierarchyDemo();
        hierarchy.demonstrateCheckedExceptions();
        hierarchy.demonstrateUncheckedExceptions();
    }
    
    public void demonstrateTryCatchFinally() {
        System.out.println("\n--- TRY-CATCH-FINALLY ---");
        
        TryCatchFinallyDemo tcf = new TryCatchFinallyDemo();
        tcf.basicTryCatch();
        tcf.multipleCatchBlocks();
        tcf.multiCatch();
        tcf.tryCatchFinally();
        tcf.tryWithResources();
        tcf.nestedTryCatch();
        tcf.finallyExecutionScenarios();
        tcf.exceptionInformation();
    }
    
    public void demonstrateCustomExceptions() {
        System.out.println("\n--- CUSTOM EXCEPTIONS ---");
        
        ThrowingExceptionsDemo throwing = new ThrowingExceptionsDemo();
        
        // Test various scenarios
        try {
            throwing.validateAge(-5);
        } catch (InvalidAgeException e) {
            System.err.println("Age validation failed: " + e.getMessage());
        }
        
        try {
            throwing.processUser("John Doe", "john@example.com", LocalDate.of(1990, 5, 15));
        } catch (UserValidationException | InvalidAgeException e) {
            System.err.println("User processing failed: " + e.getMessage());
        }
        
        // Test business logic with exception chaining
        Map<String, Object> params = new HashMap<>();
        params.put("name", "");
        params.put("email", "invalid-email");
        params.put("birthDate", LocalDate.of(2030, 1, 1));
        
        throwing.handleUserRequest("USER123", "CREATE_USER", params);
    }
    
    public void demonstrateRealWorldScenarios() {
        System.out.println("\n--- REAL-WORLD SCENARIOS ---");
        
        // E-commerce order processing
        ECommerceOrderProcessor processor = new ECommerceOrderProcessor();
        processor.demonstrateOrderProcessing();
        
        // Advanced exception handling
        AdvancedExceptionHandling advanced = new AdvancedExceptionHandling();
        advanced.demonstrateExceptionChaining();
        advanced.demonstrateExceptionSuppression();
        advanced.functionalExceptionHandling();
    }
}

// E-commerce order processing system with comprehensive exception handling
class ECommerceOrderProcessor {
    private Map<String, Product> inventory;
    private Map<String, User> users;
    
    public ECommerceOrderProcessor() {
        initializeData();
    }
    
    private void initializeData() {
        inventory = new HashMap<>();
        inventory.put("LAPTOP001", new Product("LAPTOP001", "Gaming Laptop", 999.99, 5));
        inventory.put("MOUSE001", new Product("MOUSE001", "Wireless Mouse", 29.99, 50));
        inventory.put("KEYBOARD001", new Product("KEYBOARD001", "Mechanical Keyboard", 89.99, 0));
        
        users = new HashMap<>();
        users.put("USER001", new User("USER001", "john@example.com", 1500.00));
        users.put("USER002", new User("USER002", "jane@example.com", 500.00));
    }
    
    public void demonstrateOrderProcessing() {
        System.out.println("=== E-COMMERCE ORDER PROCESSING ===");
        
        // Successful order
        try {
            Order order1 = processOrder("USER001", "LAPTOP001", 1);
            System.out.println("Order successful: " + order1);
        } catch (ECommerceException e) {
            System.err.println("Order failed: " + e);
        }
        
        // Insufficient stock
        try {
            Order order2 = processOrder("USER001", "KEYBOARD001", 1);
        } catch (InsufficientStockException e) {
            System.err.println("Stock error: " + e.getMessage());
            System.err.println("Available: " + e.getAvailable() + ", Requested: " + e.getRequested());
        } catch (ECommerceException e) {
            System.err.println("Order failed: " + e);
        }
        
        // Insufficient funds
        try {
            Order order3 = processOrder("USER002", "LAPTOP001", 1);
        } catch (InsufficientFundsException e) {
            System.err.println("Payment error: " + e.getMessage());
            System.err.println("Balance: $" + e.getBalance() + ", Required: $" + e.getRequired());
        } catch (ECommerceException e) {
            System.err.println("Order failed: " + e);
        }
        
        // Product not found
        try {
            Order order4 = processOrder("USER001", "INVALID_PRODUCT", 1);
        } catch (ProductNotFoundException e) {
            System.err.println("Product error: " + e.getMessage());
            System.err.println("Product ID: " + e.getProductId());
        } catch (ECommerceException e) {
            System.err.println("Order failed: " + e);
        }
    }
    
    public Order processOrder(String userId, String productId, int quantity) 
            throws ECommerceException {
        
        try {
            // Validate user
            User user = validateUser(userId);
            
            // Validate product and stock
            Product product = validateProductAndStock(productId, quantity);
            
            // Calculate total
            double total = product.getPrice() * quantity;
            
            // Process payment
            processPayment(user, total);
            
            // Update inventory
            updateInventory(productId, quantity);
            
            // Create order
            String orderId = "ORD" + System.currentTimeMillis();
            Order order = new Order(orderId, userId, productId, quantity, total);
            
            System.out.println("Order processed successfully: " + orderId);
            return order;
            
        } catch (ECommerceException e) {
            // Re-throw business exceptions as-is
            throw e;
        } catch (Exception e) {
            // Wrap unexpected exceptions
            throw new ECommerceException("Unexpected error processing order", "SYSTEM_ERROR", e);
        }
    }
    
    private User validateUser(String userId) throws UserException {
        if (userId == null) {
            throw new UserException("User ID cannot be null", "NULL_USER_ID");
        }
        
        User user = users.get(userId);
        if (user == null) {
            throw new UserException("User not found: " + userId, "USER_NOT_FOUND");
        }
        
        return user;
    }
    
    private Product validateProductAndStock(String productId, int quantity) 
            throws ProductNotFoundException, InsufficientStockException {
        
        if (productId == null) {
            throw new ProductNotFoundException("NULL");
        }
        
        Product product = inventory.get(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
        
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(productId, product.getStock(), quantity);
        }
        
        return product;
    }
    
    private void processPayment(User user, double amount) throws InsufficientFundsException {
        if (user.getBalance() < amount) {
            throw new InsufficientFundsException(user.getBalance(), amount);
        }
        
        user.setBalance(user.getBalance() - amount);
        System.out.println("Payment processed: $" + amount);
    }
    
    private void updateInventory(String productId, int quantity) {
        Product product = inventory.get(productId);
        product.setStock(product.getStock() - quantity);
        System.out.println("Inventory updated: " + productId + " stock: " + product.getStock());
    }
}

// Supporting classes
class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
    
    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

class User {
    private String id;
    private String email;
    private double balance;
    
    public User(String id, String email, double balance) {
        this.id = id;
        this.email = email;
        this.balance = balance;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}

class Order {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private double total;
    private LocalDate orderDate;
    
    public Order(String orderId, String userId, String productId, int quantity, double total) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = LocalDate.now();
    }
    
    @Override
    public String toString() {
        return String.format("Order{id='%s', user='%s', product='%s', qty=%d, total=$%.2f}", 
                           orderId, userId, productId, quantity, total);
    }
}
```

## Interview Questions & Answers

**Q1: What is exception handling? Why is it important?**
**A:** Exception handling is a programming construct that allows graceful handling of runtime errors. It's important for program stability, resource management, user experience, and debugging.

**Q2: What's the difference between checked and unchecked exceptions?**
**A:** Checked exceptions must be handled or declared (IOException, SQLException). Unchecked exceptions are optional to handle (RuntimeException subclasses like NullPointerException).

**Q3: Explain try-catch-finally blocks.**
**A:** Try contains code that might throw exceptions, catch handles specific exceptions, finally executes regardless of exceptions (used for cleanup).

**Q4: What is the difference between throw and throws?**
**A:** `throw` is used to explicitly throw an exception. `throws` is used in method signatures to declare which exceptions the method might throw.

**Q5: Can finally block be skipped?**
**A:** Finally block executes in almost all cases except System.exit(), JVM crash, or infinite loops/deadlocks.

**Q6: What is exception chaining?**
**A:** Exception chaining preserves the original cause when wrapping exceptions, maintaining the full error context using constructors that accept Throwable cause.

**Q7: What is try-with-resources?**
**A:** Automatic resource management introduced in Java 7 that automatically closes resources implementing AutoCloseable, even if exceptions occur.

**Q8: Can you have multiple catch blocks? What about multiple finally blocks?**
**A:** Yes to multiple catch blocks (for different exception types). No to multiple finally blocks - only one finally per try block.

## Key Takeaways

1. **Exception handling prevents crashes** and enables graceful error recovery
2. **Try-catch-finally** provides structured error handling and cleanup
3. **Custom exceptions** create domain-specific error types for business logic
4. **Exception chaining** preserves error context across application layers
5. **Try-with-resources** automatically manages resource cleanup
6. **Checked vs unchecked** exceptions have different handling requirements
7. **Finally blocks** ensure cleanup code always executes
8. **Proper exception design** improves code maintainability and debugging
9. **Exception information** (stack traces, causes) aids in troubleshooting
10. **Layered exception handling** creates clean separation of concerns

---

*Remember: Exception handling is like insurance - it's better to have it and not need it than to need it and not have it!*