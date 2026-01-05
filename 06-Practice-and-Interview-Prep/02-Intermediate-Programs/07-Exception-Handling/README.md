# Exception Handling

## Problem Statement

Exception handling is crucial for writing robust, production-quality code. This program demonstrates comprehensive exception handling techniques including custom exceptions, try-catch-finally blocks, exception propagation, exception chaining, and resource management. Proper exception handling prevents program crashes and provides meaningful error feedback.

Exception handling is essential for:
- Creating robust, crash-resistant programs
- Providing meaningful error messages
- Cleaning up resources properly
- Debugging application issues
- Writing professional-quality code

## Concepts

### Exception Hierarchy

Java exceptions follow an inheritance hierarchy:

```
Throwable
├── Error (system-level, don't catch these)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── ...
└── Exception (application-level, should catch these)
    ├── Checked Exceptions (must be caught)
    │   ├── IOException
    │   ├── SQLException
    │   └── ...
    └── Unchecked Exceptions (can be caught)
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        ├── ArithmeticException
        └── ...
```

### Checked vs Unchecked Exceptions

**Checked Exceptions:**
- Must be caught or declared with throws
- Checked at compile time
- Examples: IOException, SQLException
- Represent recoverable conditions

**Unchecked Exceptions:**
- Don't need to be caught
- Checked at runtime
- Examples: NullPointerException, ArrayIndexOutOfBoundsException
- Represent programming errors

### Try-Catch-Finally Blocks

**Try Block:**
- Contains code that might throw exception
- Must be followed by catch or finally

**Catch Block:**
- Handles specific exception type
- Can have multiple catch blocks
- Executes if matching exception occurs

**Finally Block:**
- Always executes, exception or not
- Used for cleanup (closing resources)
- Optional but recommended

### Custom Exceptions

Creating custom exceptions allows domain-specific error handling:

```java
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
```

**Benefits:**
- Meaningful error messages
- Specific exception catching
- Additional error information
- Clear error hierarchy

### Exception Propagation

Exceptions can be thrown up the call stack:

```
methodA() -> methodB() -> methodC() -> exception thrown
catch in methodA
```

Using `throws` declaration propagates exceptions:

```java
public void methodB() throws CustomException {
    methodC();  // May throw exception
}
```

### Exception Chaining

Preserving original exception as cause:

```java
try {
    originalCode();
} catch (OriginalException e) {
    throw new CustomException("Context message", e);
}
```

## Complexity Analysis

Exception handling itself has minimal overhead:
- **Try block (no exception):** O(1) overhead
- **Exception throwing:** O(n) where n = stack depth
- **Stack trace generation:** O(n) for n stack frames

## Sample Input/Output

```
===== EXCEPTION HANDLING DEMO =====

--- BASIC TRY-CATCH ---
Caught exception: Index 5 out of bounds for length 3
Exception type: ArrayIndexOutOfBoundsException
Program continues after exception

--- MULTIPLE CATCH BLOCKS ---
Caught: Invalid number format - For input string: "abc"

Caught: Arithmetic error - / by zero

--- TRY-CATCH-FINALLY ---
Inside try block
Result: 5
Finally block always executes

--- CUSTOM EXCEPTIONS ---
Age 25 is valid
Error: Age must be between 0 and 150, but got: -5
Error: Age must be between 0 and 150, but got: 200

--- EXCEPTION WITH DATA ---
Error: Cannot withdraw 150.0 from balance 100.0
Shortfall amount: 50.0

--- EXCEPTION PROPAGATION ---
Caught in methodA: Error occurred in methodC
Exception propagated through: methodC -> methodB -> methodA

--- CHAINED EXCEPTIONS ---
Main message: Failed to parse input: abc
Cause: java.lang.NumberFormatException: For input string: "abc"
Cause message: For input string: "abc"

--- TRY-WITH-RESOURCES ---
Opening resource: FileReader
Using resource: FileReader
Closing resource: FileReader

--- THROWS DECLARATION ---
Caught: Cannot divide by zero

--- NESTED TRY-CATCH ---
Outer catch: Index 5 out of bounds for length 3

--- FINALLY ALWAYS EXECUTES ---
Iteration 1
Finally block executes on iteration 1
Iteration 2
Caught: Error on iteration 2
Finally block executes on iteration 2
Iteration 3
Finally block executes on iteration 3

Program completed successfully
```

## Key Methods Explained

### `basicTryCatch()`
- Demonstrates simple try-catch
- Shows how to catch specific exceptions
- Explains exception message retrieval

### `multipleCatchBlocks()`
- Shows multiple catch blocks for different exceptions
- Demonstrates specific-to-general exception catching
- Important: Order of catch blocks matters

### `tryCatchFinally()`
- Shows finally block execution
- Finally executes whether exception occurs or not
- Used for resource cleanup

### `validateAge()`
- Custom exception throwing
- Shows exception with meaningful message
- Demonstrates method with throws declaration

### `parseAndValidate()`
- Exception chaining example
- Preserves original exception as cause
- Provides context with new exception

### `demonstrateTryWithResources()`
- Try-with-resources (Java 7+)
- Automatic resource management
- Resource must implement AutoCloseable

## Best Practices

### 1. Catch Specific Exceptions
```java
// Good
try {
    readFile();
} catch (IOException e) {
    // Handle IO error
}

// Avoid
try {
    readFile();
} catch (Exception e) {
    // Too broad, masks bugs
}
```

### 2. Always Use Finally or Try-With-Resources
```java
// Good - try-with-resources
try (FileReader reader = new FileReader("file.txt")) {
    // Use reader
}

// Or good - explicit finally
try {
    // code
} finally {
    resource.close();
}
```

### 3. Provide Context in Custom Exceptions
```java
// Good
throw new CustomException("Failed to process file: " + filename, cause);

// Avoid
throw new CustomException("Error");
```

### 4. Don't Ignore Exceptions
```java
// Bad
try {
    riskyOperation();
} catch (Exception e) {
    // Silently ignore
}

// Good
try {
    riskyOperation();
} catch (Exception e) {
    logger.error("Operation failed", e);
    throw e;
}
```

### 5. Log Stack Traces for Debugging
```java
catch (Exception e) {
    logger.error("Error occurred", e);  // Includes stack trace
    e.printStackTrace();  // Development only
}
```

## Variations and Challenges

### Variation 1: Multi-Catch Blocks (Java 7+)
Handle multiple exceptions in single catch
```java
try {
    // code
} catch (IOException | SQLException e) {
    // Handle both exceptions
}
```

### Variation 2: Custom Exception Hierarchy
Create exception class hierarchy for related errors
```java
class ApplicationException extends Exception
class ValidationException extends ApplicationException
class DatabaseException extends ApplicationException
```

### Challenge 1: Resource Management Wrapper
Create utility class ensuring resource cleanup
```java
public class ManagedResource<T> implements AutoCloseable
```

### Challenge 2: Exception Translation
Convert low-level exceptions to high-level ones
```java
public static void translateException(Exception e)
```

### Challenge 3: Retry Logic
Implement exception handling with retry mechanism
```java
public static void retryOperation(int maxRetries)
```

### Challenge 4: Exception Aggregation
Collect multiple exceptions and report together
```java
public static void aggregateExceptions()
```

## Interview Questions

1. **What's the difference between checked and unchecked exceptions?**
   - Checked: Must be caught or declared; checked at compile time
   - Unchecked: Don't need to be caught; represent programming errors

2. **Why use finally block instead of code after try-catch?**
   - Finally executes even if exception or return in catch block
   - Ensures cleanup code runs reliably

3. **When would you use custom exceptions?**
   - When you need domain-specific error handling
   - When you need to add data to exception
   - When you want specific catch blocks

4. **What's exception chaining and why use it?**
   - Preserving original exception as cause
   - Maintains stack trace context
   - Helps debugging root cause

5. **How does try-with-resources work?**
   - Automatically closes resources implementing AutoCloseable
   - Eliminates manual try-finally cleanup
   - Cleaner, less error-prone code

## Edge Cases to Consider

- **Nested exceptions:** Multiple exception levels in call stack
- **Exception in finally:** Finally exception may mask original
- **Finally with return:** Finally executes even with return statement
- **Resource initialization failure:** Exception before try block enters
- **Multiple resource failure:** Which exception is primary?
- **Exception subclassing:** Order of catch blocks matters
- **Null exception message:** Handle gracefully in logging

## Running the Program

```powershell
# Compile
javac ExceptionHandlingDemo.java

# Run
java ExceptionHandlingDemo
```

## Tips for Learning

1. **Understand Hierarchy:** Know Exception vs Error vs Throwable
2. **Test Edge Cases:** Try different exception scenarios
3. **Trace Execution:** Follow exception through call stack
4. **Log Thoroughly:** Use stack traces for debugging
5. **Clean Up:** Always ensure resources are cleaned

## Common Mistakes

- Catching too broad exceptions (Exception instead of specific)
- Not cleaning up resources properly
- Ignoring or swallowing exceptions silently
- Not providing context in custom exceptions
- Throwing exception in finally block
- Not using try-with-resources for AutoCloseable resources
- Relying on exception handling for normal control flow

## Advanced Concepts

### Exception Translation
Convert low-level exceptions to application-level exceptions for better API design

### Sneaky Throws
Using generics to throw checked exceptions as unchecked (avoid this)

### Exception Grouping
Using custom exception hierarchy to group related errors

### Retry Mechanisms
Automatically retrying operations that fail temporarily

### Fallback Strategies
Providing alternative behavior when exceptions occur

---

**Practice:** Exception handling is critical for professional code. Master these techniques. Write code that fails gracefully, provides meaningful error messages, and cleans up resources properly.
