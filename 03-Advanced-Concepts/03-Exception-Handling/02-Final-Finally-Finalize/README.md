# Final vs Finally vs Finalize in Java

## Simple Explanation

Think of **Final, Finally, and Finalize** as **different types of endings**:

- **final** = Permanent decision (like marriage vows - "this value will never change")
- **finally** = Guaranteed conclusion (like washing dishes - "this will happen no matter what")
- **finalize()** = Last-minute cleanup (like estate executor - "handle final affairs before disposal")

### Real-World Analogies
- **final variable** = Sealed document (cannot be modified once signed)
- **finally block** = Emergency exit protocol (always executed regardless of situation)
- **finalize() method** = Funeral arrangements (last rites before burial)
- **final class** = Sealed recipe (cannot be modified or inherited)

## Professional Definition

**final, finally, and finalize** are three distinct Java keywords with completely different purposes: **final** is a modifier for immutability and inheritance control; **finally** is an exception handling block that guarantees execution; **finalize()** is a deprecated method for object cleanup before garbage collection. Understanding their differences is crucial for proper Java programming and avoiding common misconceptions.

## Detailed Comparison and Usage

### 1. Final Keyword - Immutability and Inheritance Control

```java
// FINAL - Immutability and restriction modifier

import java.util.*;

public class FinalKeywordDemo {
    
    public void demonstrateFinalUsage() {
        System.out.println("=== FINAL KEYWORD DEMONSTRATION ===");
        
        demonstrateFinalVariables();
        demonstrateFinalMethods();
        demonstrateFinalClasses();
        demonstrateFinalParameters();
        demonstrateFinalCollections();
    }
    
    // Final variables - cannot be reassigned
    public void demonstrateFinalVariables() {
        System.out.println("\n--- Final Variables ---");
        
        // Final primitive - value cannot change
        final int MAX_USERS = 100;
        final double PI_VALUE = 3.14159;
        final boolean IS_PRODUCTION = true;
        
        System.out.printf("Max users: %d (cannot be changed)%n", MAX_USERS);
        System.out.printf("PI value: %.5f (mathematical constant)%n", PI_VALUE);
        System.out.printf("Production mode: %s (deployment setting)%n", IS_PRODUCTION);
        
        // Compilation error if you try to reassign:
        // MAX_USERS = 200; // ❌ Cannot assign a value to final variable
        
        // Final object reference - reference cannot change, but object can be modified
        final List<String> SUPPORTED_LANGUAGES = new ArrayList<>();
        SUPPORTED_LANGUAGES.add("Java");
        SUPPORTED_LANGUAGES.add("Python");
        SUPPORTED_LANGUAGES.add("JavaScript");
        
        System.out.printf("Supported languages: %s%n", SUPPORTED_LANGUAGES);
        
        // This is allowed - modifying object content
        SUPPORTED_LANGUAGES.add("C++");
        System.out.printf("After adding C++: %s%n", SUPPORTED_LANGUAGES);
        
        // This would cause compilation error:
        // SUPPORTED_LANGUAGES = new ArrayList<>(); // ❌ Cannot reassign final reference
        
        // Final with complex objects
        final UserAccount account = new UserAccount("john_doe", "john@email.com");
        System.out.printf("Account: %s%n", account);
        
        // Can modify object properties
        account.updateEmail("john.doe@company.com");
        System.out.printf("Updated account: %s%n", account);
        
        // Cannot reassign reference:
        // account = new UserAccount("jane", "jane@email.com"); // ❌ Compilation error
        
        System.out.println("✅ Final variables ensure reference immutability");
    }
    
    // Final methods - cannot be overridden
    public final void criticalSecurityMethod() {
        System.out.println("🔒 This method cannot be overridden for security reasons");
        System.out.println("   Contains critical security logic that must remain unchanged");
    }
    
    public final String generateSecurityToken() {
        // Critical security implementation that subclasses cannot override
        return "SEC_" + UUID.randomUUID().toString();
    }
    
    public void demonstrateFinalMethods() {
        System.out.println("\n--- Final Methods ---");
        
        criticalSecurityMethod();
        String token = generateSecurityToken();
        System.out.printf("Security token: %s%n", token);
        
        System.out.println("✅ Final methods prevent overriding in subclasses");
    }
    
    // Final classes demonstration through inheritance
    public void demonstrateFinalClasses() {
        System.out.println("\n--- Final Classes ---");
        
        // String is a final class - cannot be extended
        String text = "Hello World";
        System.out.printf("String (final class): %s%n", text);
        
        // Integer is also final
        Integer number = 42;
        System.out.printf("Integer (final class): %s%n", number);
        
        // Our custom final class
        ImmutablePoint point = new ImmutablePoint(10, 20);
        System.out.printf("Immutable point: %s%n", point);
        
        System.out.println("✅ Final classes prevent inheritance");
        System.out.println("   Examples: String, Integer, Double, LocalDate");
    }
    
    // Final parameters
    public void processUserData(final String userId, final List<String> permissions) {
        System.out.println("\n--- Final Parameters ---");
        
        System.out.printf("Processing user: %s%n", userId);
        System.out.printf("Initial permissions: %s%n", permissions);
        
        // Cannot reassign final parameters:
        // userId = "different_user"; // ❌ Compilation error
        // permissions = new ArrayList<>(); // ❌ Compilation error
        
        // But can modify the object content:
        permissions.add("READ_REPORTS");
        permissions.add("WRITE_DOCUMENTS");
        
        System.out.printf("Updated permissions: %s%n", permissions);
        
        System.out.println("✅ Final parameters prevent accidental reassignment");
    }
    
    public void demonstrateFinalParameters() {
        List<String> userPermissions = new ArrayList<>(Arrays.asList("LOGIN", "READ_PROFILE"));
        processUserData("user123", userPermissions);
    }
    
    // Final with collections - common misconception
    public void demonstrateFinalCollections() {
        System.out.println("\n--- Final Collections (Common Misconception) ---");
        
        // Final reference - reference cannot change
        final Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        
        System.out.printf("Initial scores: %s%n", scores);
        
        // These operations are ALLOWED - modifying content:
        scores.put("Diana", 98);          // Adding new entry
        scores.put("Alice", 97);          // Updating existing entry
        scores.remove("Bob");             // Removing entry
        
        System.out.printf("Modified scores: %s%n", scores);
        
        // This is NOT ALLOWED - reassigning reference:
        // scores = new HashMap<>(); // ❌ Compilation error
        
        System.out.println("⚠️ Important: 'final' makes reference immutable, not the object!");
        System.out.println("   For truly immutable collections, use Collections.unmodifiableMap()");
        
        // Creating truly immutable collection
        Map<String, Integer> immutableScores = Collections.unmodifiableMap(
            new HashMap<>(Map.of("Alice", 95, "Bob", 87, "Charlie", 92))
        );
        
        System.out.printf("Immutable scores: %s%n", immutableScores);
        
        try {
            immutableScores.put("Diana", 98); // This will throw UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("❌ Cannot modify truly immutable collection: " + e.getClass().getSimpleName());
        }
    }
    
    // Supporting classes
    static class UserAccount {
        private final String username; // final field
        private String email;          // mutable field
        
        public UserAccount(String username, String email) {
            this.username = username; // Can only be set once
            this.email = email;
        }
        
        public void updateEmail(String newEmail) {
            this.email = newEmail; // Allowed - not final
        }
        
        // Cannot provide setter for username due to final modifier
        // public void setUsername(String username) { // ❌ Would cause error
        //     this.username = username; // Cannot assign to final field
        // }
        
        @Override
        public String toString() {
            return String.format("UserAccount{username='%s', email='%s'}", username, email);
        }
    }
}

// Final class example - cannot be extended
final class ImmutablePoint {
    private final int x;
    private final int y;
    
    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    @Override
    public String toString() {
        return String.format("Point(%d, %d)", x, y);
    }
}

// This would cause compilation error:
// class ExtendedPoint extends ImmutablePoint { } // ❌ Cannot inherit from final class
```

### 2. Finally Block - Guaranteed Execution

```java
// FINALLY - Exception handling guarantee

import java.io.*;
import java.util.*;

public class FinallyBlockDemo {
    
    public void demonstrateFinallyUsage() {
        System.out.println("=== FINALLY BLOCK DEMONSTRATION ===");
        
        demonstrateFinallyExecution();
        demonstrateFinallyWithReturn();
        demonstrateFinallyWithException();
        demonstrateResourceCleanup();
        demonstrateComparisonWithTryWithResources();
    }
    
    // Finally always executes
    public void demonstrateFinallyExecution() {
        System.out.println("\n--- Finally Always Executes ---");
        
        for (String scenario : Arrays.asList("success", "exception", "early_return")) {
            System.out.printf("\n🔄 Scenario: %s%n", scenario);
            
            try {
                System.out.println("  📝 Try block executing...");
                
                switch (scenario) {
                    case "success":
                        System.out.println("  ✅ Normal execution path");
                        break;
                        
                    case "exception":
                        System.out.println("  ⚠️ About to throw exception...");
                        throw new RuntimeException("Simulated exception");
                        
                    case "early_return":
                        System.out.println("  🔄 Early return from try block");
                        return; // Even with return, finally executes!
                }
                
            } catch (RuntimeException e) {
                System.out.printf("  ❌ Caught exception: %s%n", e.getMessage());
                
            } finally {
                System.out.println("  🎯 Finally block ALWAYS executes!");
                System.out.println("     This runs regardless of success, exception, or return");
            }
            
            System.out.println("  📋 Code after try-catch-finally");
        }
    }
    
    // Finally executes even with return statements
    public String demonstrateFinallyWithReturn() {
        System.out.println("\n--- Finally with Return Statements ---");
        
        String result = methodWithMultipleReturns(true);
        System.out.printf("Method result: %s%n", result);
        
        result = methodWithMultipleReturns(false);
        System.out.printf("Method result: %s%n", result);
        
        return "demo_complete";
    }
    
    private String methodWithMultipleReturns(boolean condition) {
        System.out.printf("🔄 Method called with condition: %s%n", condition);
        
        try {
            if (condition) {
                System.out.println("  ✅ Taking success path");
                return "success_from_try";
            } else {
                System.out.println("  ❌ Taking failure path");
                throw new IllegalArgumentException("Condition was false");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.printf("  🔧 Exception handled: %s%n", e.getMessage());
            return "error_from_catch";
            
        } finally {
            System.out.println("  🎯 Finally executes even with return statements!");
            
            // Note: return from finally would override previous returns (not recommended)
            // return "return_from_finally"; // ❌ Bad practice - would override other returns
        }
    }
    
    // Finally with exceptions
    public void demonstrateFinallyWithException() {
        System.out.println("\n--- Finally with Unhandled Exceptions ---");
        
        try {
            methodThatThrowsUnhandledException();
        } catch (RuntimeException e) {
            System.out.printf("❌ Caught propagated exception: %s%n", e.getMessage());
        }
    }
    
    private void methodThatThrowsUnhandledException() {
        System.out.println("🔄 Method that throws unhandled exception");
        
        try {
            System.out.println("  📝 Try block before exception");
            throw new UnsupportedOperationException("Unhandled exception type");
            
        } catch (IllegalArgumentException e) {
            // This won't catch UnsupportedOperationException
            System.out.println("  🔧 This catch won't execute");
            
        } finally {
            System.out.println("  🎯 Finally executes even with unhandled exception!");
            System.out.println("     Exception will propagate after finally completes");
        }
    }
    
    // Resource cleanup with finally
    public void demonstrateResourceCleanup() {
        System.out.println("\n--- Resource Cleanup with Finally ---");
        
        FileWriter writer = null;
        
        try {
            System.out.println("📝 Opening file for writing...");
            writer = new FileWriter("temp_finally_demo.txt");
            
            writer.write("Data written in try block\n");
            writer.write("Current time: " + new Date() + "\n");
            
            // Simulate potential exception
            if (Math.random() > 0.5) {
                throw new IOException("Simulated write error");
            }
            
            writer.write("Additional data\n");
            System.out.println("✅ File written successfully");
            
        } catch (IOException e) {
            System.out.printf("❌ IO Error: %s%n", e.getMessage());
            
        } finally {
            // Guaranteed resource cleanup
            System.out.println("🧹 Cleaning up resources in finally...");
            
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("✅ File writer closed successfully");
                } catch (IOException e) {
                    System.out.printf("⚠️ Error closing writer: %s%n", e.getMessage());
                }
            }
        }
        
        System.out.println("✅ Resource cleanup guaranteed by finally");
    }
    
    // Comparison with try-with-resources
    public void demonstrateComparisonWithTryWithResources() {
        System.out.println("\n--- Finally vs Try-With-Resources ---");
        
        System.out.println("📋 Traditional finally approach:");
        traditionalFinallyApproach();
        
        System.out.println("\n📋 Modern try-with-resources approach:");
        modernTryWithResourcesApproach();
    }
    
    private void traditionalFinallyApproach() {
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader("sample.txt"));
            String line = reader.readLine();
            System.out.printf("Read line: %s%n", line != null ? line : "No data");
            
        } catch (IOException e) {
            System.out.printf("❌ Error: %s%n", e.getMessage());
            
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("✅ Reader closed in finally");
                } catch (IOException e) {
                    System.out.printf("⚠️ Error closing reader: %s%n", e.getMessage());
                }
            }
        }
    }
    
    private void modernTryWithResourcesApproach() {
        try (BufferedReader reader = new BufferedReader(new FileReader("sample.txt"))) {
            String line = reader.readLine();
            System.out.printf("Read line: %s%n", line != null ? line : "No data");
            
            // No finally needed - automatic resource management!
            
        } catch (IOException e) {
            System.out.printf("❌ Error: %s%n", e.getMessage());
        }
        
        System.out.println("✅ Reader automatically closed (no explicit finally needed)");
    }
}
```

### 3. Finalize Method - Deprecated Cleanup

```java
// FINALIZE - Deprecated object cleanup (Java 9+)

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.*;

public class FinalizeMethodDemo {
    
    @SuppressWarnings("deprecation") // Suppressing warnings for educational purposes
    public void demonstrateFinalizeUsage() {
        System.out.println("=== FINALIZE METHOD DEMONSTRATION ===");
        
        demonstrateBasicFinalize();
        demonstrateFinalizeProblems();
        demonstrateModernAlternatives();
    }
    
    // Basic finalize usage (DEPRECATED - for educational purposes only)
    public void demonstrateBasicFinalize() {
        System.out.println("\n--- Basic Finalize (DEPRECATED) ---");
        
        System.out.println("⚠️ WARNING: finalize() is deprecated since Java 9");
        System.out.println("   This demonstration is for educational purposes only");
        
        // Create objects with finalize methods
        for (int i = 1; i <= 3; i++) {
            LegacyResourceWithFinalize resource = new LegacyResourceWithFinalize("Resource-" + i);
            resource.doWork();
            // Objects become eligible for GC when they go out of scope
        }
        
        // Suggest garbage collection (not guaranteed to run immediately)
        System.out.println("🗑️ Suggesting garbage collection...");
        System.gc();
        
        // Give GC time to work (finalization is unpredictable)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("📝 Note: finalize() execution is unpredictable and not guaranteed");
    }
    
    // Problems with finalize
    public void demonstrateFinalizeProblems() {
        System.out.println("\n--- Problems with Finalize ---");
        
        System.out.println("❌ Major problems with finalize():");
        System.out.println("   1. Unpredictable execution timing");
        System.out.println("   2. Performance overhead");
        System.out.println("   3. Can resurrect objects");
        System.out.println("   4. Not guaranteed to run");
        System.out.println("   5. Blocks garbage collection");
        
        demonstrateObjectResurrection();
        demonstratePerformanceImpact();
    }
    
    @SuppressWarnings("deprecation")
    private void demonstrateObjectResurrection() {
        System.out.println("\n🧟 Object Resurrection Problem:");
        
        // Create object that can resurrect itself
        ResurrectableObject obj = new ResurrectableObject("Zombie Object");
        obj.kill(); // Mark for deletion
        
        obj = null; // Remove reference
        System.gc(); // Suggest GC
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Object might have resurrected itself!
        if (ResurrectableObject.resurrectedInstance != null) {
            System.out.println("👻 Object resurrected itself from finalize()!");
            System.out.println("   This is why finalize() is dangerous and deprecated");
        }
    }
    
    private void demonstratePerformanceImpact() {
        System.out.println("\n⚡ Performance Impact:");
        
        long startTime = System.currentTimeMillis();
        
        // Create many objects with finalize (slower)
        for (int i = 0; i < 1000; i++) {
            @SuppressWarnings("unused")
            LegacyResourceWithFinalize resource = new LegacyResourceWithFinalize("Perf-" + i);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("Creating 1000 objects with finalize: %d ms%n", endTime - startTime);
        
        startTime = System.currentTimeMillis();
        
        // Create objects without finalize (faster)
        for (int i = 0; i < 1000; i++) {
            @SuppressWarnings("unused")
            ModernResource resource = new ModernResource("Modern-" + i);
        }
        
        endTime = System.currentTimeMillis();
        System.out.printf("Creating 1000 objects without finalize: %d ms%n", endTime - startTime);
        
        System.out.println("📊 Objects with finalize() have GC overhead");
    }
    
    // Modern alternatives to finalize
    public void demonstrateModernAlternatives() {
        System.out.println("\n--- Modern Alternatives to Finalize ---");
        
        demonstrateExplicitCleanup();
        demonstrateTryWithResources();
        demonstratePhantomReferences();
    }
    
    private void demonstrateExplicitCleanup() {
        System.out.println("\n✅ Alternative 1: Explicit Cleanup Methods");
        
        // Use explicit close/cleanup methods
        ModernResource resource = new ModernResource("Modern Resource");
        
        try {
            resource.doWork();
            resource.processData("Important data");
            
        } finally {
            resource.close(); // Explicit cleanup
        }
        
        System.out.println("✅ Resources explicitly cleaned up");
    }
    
    private void demonstrateTryWithResources() {
        System.out.println("\n✅ Alternative 2: Try-With-Resources (Recommended)");
        
        // Automatic resource management
        try (AutoCloseableResource resource = new AutoCloseableResource("Auto Resource")) {
            resource.doWork();
            resource.processData("Auto-managed data");
            
            // Resource automatically closed at end of try block
            
        } catch (Exception e) {
            System.out.printf("Error: %s%n", e.getMessage());
        }
        
        System.out.println("✅ Resources automatically managed");
    }
    
    private void demonstratePhantomReferences() {
        System.out.println("\n✅ Alternative 3: Phantom References (Advanced)");
        
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        Object obj = new Object();
        
        PhantomReference<Object> phantomRef = new PhantomReference<>(obj, referenceQueue) {
            @Override
            public void clear() {
                super.clear();
                System.out.println("👻 Phantom reference cleanup executed");
            }
        };
        
        obj = null; // Remove strong reference
        System.gc();
        
        // Check if phantom reference was enqueued
        try {
            Thread.sleep(50);
            if (referenceQueue.poll() != null) {
                System.out.println("✅ Phantom reference detected object cleanup");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("📝 Phantom references provide better cleanup control than finalize");
    }
    
    // Supporting classes
    
    // Legacy class with finalize (DEPRECATED)
    @SuppressWarnings("deprecation")
    static class LegacyResourceWithFinalize {
        private String name;
        private boolean closed = false;
        
        public LegacyResourceWithFinalize(String name) {
            this.name = name;
            System.out.printf("🔧 Created legacy resource: %s%n", name);
        }
        
        public void doWork() {
            if (!closed) {
                System.out.printf("⚙️ %s is working...%n", name);
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            try {
                if (!closed) {
                    System.out.printf("🗑️ Finalizing %s (DEPRECATED cleanup)%n", name);
                    closed = true;
                }
            } finally {
                super.finalize();
            }
        }
    }
    
    // Object that can resurrect itself (BAD PRACTICE)
    @SuppressWarnings("deprecation")
    static class ResurrectableObject {
        private String name;
        private boolean killed = false;
        
        // Static reference to enable resurrection
        public static ResurrectableObject resurrectedInstance = null;
        
        public ResurrectableObject(String name) {
            this.name = name;
        }
        
        public void kill() {
            this.killed = true;
            System.out.printf("☠️ Killing %s%n", name);
        }
        
        @Override
        protected void finalize() throws Throwable {
            if (killed) {
                // Resurrect by creating static reference to self
                resurrectedInstance = this;
                System.out.printf("🧟 %s resurrected itself!%n", name);
            }
        }
    }
    
    // Modern resource without finalize
    static class ModernResource {
        private String name;
        private boolean closed = false;
        
        public ModernResource(String name) {
            this.name = name;
        }
        
        public void doWork() {
            if (closed) throw new IllegalStateException("Resource is closed");
            System.out.printf("⚙️ %s is working...%n", name);
        }
        
        public void processData(String data) {
            if (closed) throw new IllegalStateException("Resource is closed");
            System.out.printf("📊 %s processing: %s%n", name, data);
        }
        
        public void close() {
            if (!closed) {
                closed = true;
                System.out.printf("✅ %s closed explicitly%n", name);
            }
        }
    }
    
    // AutoCloseable resource (modern approach)
    static class AutoCloseableResource implements AutoCloseable {
        private String name;
        private boolean closed = false;
        
        public AutoCloseableResource(String name) {
            this.name = name;
            System.out.printf("🔧 Created auto-closeable resource: %s%n", name);
        }
        
        public void doWork() {
            if (closed) throw new IllegalStateException("Resource is closed");
            System.out.printf("⚙️ %s is working...%n", name);
        }
        
        public void processData(String data) {
            if (closed) throw new IllegalStateException("Resource is closed");
            System.out.printf("📊 %s processing: %s%n", name, data);
        }
        
        @Override
        public void close() {
            if (!closed) {
                closed = true;
                System.out.printf("✅ %s auto-closed%n", name);
            }
        }
    }
}
```

## Complete Comparison Demo

```java
public class CompleteFinalFinallyFinalizeDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE FINAL vs FINALLY vs FINALIZE DEMONSTRATION ===");
        
        // Summary table
        printComparisonTable();
        
        // 1. Final keyword
        System.out.println("\n1. FINAL KEYWORD");
        FinalKeywordDemo finalDemo = new FinalKeywordDemo();
        finalDemo.demonstrateFinalUsage();
        
        // 2. Finally block
        System.out.println("\n\n2. FINALLY BLOCK");
        FinallyBlockDemo finallyDemo = new FinallyBlockDemo();
        finallyDemo.demonstrateFinallyUsage();
        
        // 3. Finalize method
        System.out.println("\n\n3. FINALIZE METHOD (DEPRECATED)");
        FinalizeMethodDemo finalizeDemo = new FinalizeMethodDemo();
        finalizeDemo.demonstrateFinalizeUsage();
        
        // Best practices summary
        System.out.println("\n\n=== BEST PRACTICES SUMMARY ===");
        printBestPractices();
    }
    
    private static void printComparisonTable() {
        System.out.println("\n📊 COMPARISON TABLE:");
        System.out.println("┌─────────────┬────────────────┬──────────────────┬─────────────────────┐");
        System.out.println("│   Aspect    │     final      │     finally      │      finalize()     │");
        System.out.println("├─────────────┼────────────────┼──────────────────┼─────────────────────┤");
        System.out.println("│ Type        │ Keyword        │ Block            │ Method              │");
        System.out.println("│ Purpose     │ Immutability   │ Cleanup          │ GC cleanup (DEPR)   │");
        System.out.println("│ When used   │ Declaration    │ Exception handle │ Before GC           │");
        System.out.println("│ Guarantee   │ Compile-time   │ Always executes  │ No guarantee        │");
        System.out.println("│ Performance │ No impact      │ Minimal impact   │ Significant impact  │");
        System.out.println("│ Modern alt. │ N/A            │ try-with-res     │ AutoCloseable       │");
        System.out.println("│ Status      │ Active         │ Active           │ Deprecated (Java 9) │");
        System.out.println("└─────────────┴────────────────┴──────────────────┴─────────────────────┘");
    }
    
    private static void printBestPractices() {
        System.out.println("✅ RECOMMENDED PRACTICES:");
        System.out.println("• Use 'final' for constants and immutable references");
        System.out.println("• Use 'finally' for guaranteed cleanup (or try-with-resources)");
        System.out.println("• AVOID 'finalize()' - use AutoCloseable instead");
        System.out.println("• Prefer try-with-resources over manual finally blocks");
        System.out.println("• Make classes final if they shouldn't be extended");
        System.out.println("• Use final parameters to prevent accidental reassignment");
        
        System.out.println("\n❌ AVOID:");
        System.out.println("• Overriding finalize() method (deprecated)");
        System.out.println("• Returning from finally block (masks exceptions)");
        System.out.println("• Relying on finalize() for resource cleanup");
        System.out.println("• Confusing final reference with object immutability");
    }
}
```

## Interview Questions & Answers

**Q1: What is the difference between final, finally, and finalize?**
**A:** final is a keyword for immutability/inheritance control, finally is a block for guaranteed execution in exception handling, finalize() is a deprecated method called before garbage collection.

**Q2: Can a final variable be modified?**
**A:** Final variables cannot be reassigned, but if the variable is an object reference, the object's contents can be modified. The reference itself is immutable, not the object.

**Q3: Does finally block always execute?**
**A:** Finally executes in almost all cases, including when there are return statements in try/catch. Exceptions: System.exit(), JVM crash, infinite loops, or Thread.stop().

**Q4: Why is finalize() deprecated?**
**A:** finalize() is deprecated because it's unpredictable, has performance overhead, can resurrect objects, isn't guaranteed to run, and blocks efficient garbage collection. Use AutoCloseable instead.

**Q5: Can you override a final method?**
**A:** No, final methods cannot be overridden. This ensures that critical functionality remains unchanged in subclasses, often used for security-sensitive methods.

**Q6: What happens if finally block throws an exception?**
**A:** If finally throws an exception, it suppresses the original exception from try/catch. The finally exception becomes the main exception propagated by the method.

**Q7: Can a final class be extended?**
**A:** No, final classes cannot be extended. Examples include String, Integer, LocalDate. This prevents modification of core behavior through inheritance.

**Q8: What's the difference between final and immutable?**
**A:** final makes the reference immutable (cannot be reassigned), but doesn't make the object immutable. True immutability requires that object state cannot be changed after construction.

## Key Takeaways

1. **final keyword ensures immutability of references** and prevents inheritance/overriding
2. **finally block guarantees cleanup code execution** regardless of exceptions or returns
3. **finalize() method is deprecated** and should be replaced with AutoCloseable
4. **final variables can only be assigned once** but objects can still be modified
5. **finally is crucial for resource management** in traditional exception handling
6. **try-with-resources is preferred over finally** for AutoCloseable resources
7. **final classes cannot be extended** (String, Integer, LocalDate are examples)
8. **final methods cannot be overridden** in subclasses (security/stability)
9. **Performance: final has no impact, finally minimal, finalize significant overhead**
10. **Modern Java favors explicit resource management** over finalization

---

*Remember: final = permanent, finally = guaranteed, finalize = deprecated (avoid it!)*