# Exception with Method Overriding in Java

## Simple Explanation

Think of **Exception with Method Overriding** as **family rules getting stricter but never looser**:

- **Parent class** = Family rules about what problems can happen
- **Child class** = Individual family member's stricter rules
- **Method overriding** = Child following same activity but with their own rules
- **Exception rules** = Child can have same or fewer problems, but never more
- **Overridden method** = Child's version must be "safer" or equally safe

### Real-World Analogies
- **Speed limits** = Child road can have lower speed limit, never higher
- **Noise restrictions** = Child apartment can be quieter, never louder
- **Budget constraints** = Child can spend less or same, never more
- **Safety protocols** = Child procedure can be safer, never more dangerous

## Professional Definition

**Exception handling in method overriding** refers to the rules and constraints that govern how exceptions are declared and thrown when a subclass overrides a method from its superclass. Java enforces strict rules to maintain the Liskov Substitution Principle, ensuring that subclass instances can safely replace superclass instances without breaking exception handling contracts.

## Why Exception Rules in Overriding Matter

### Problems Without Proper Exception Rules:
```java
// PROBLEMATIC CODE - Violating exception rules in overriding

import java.io.*;
import java.sql.*;
import java.util.*;

// This code shows what would happen if Java didn't enforce exception rules
class ProblematicExceptionOverriding {
    
    public void demonstrateProblems() {
        System.out.println("=== PROBLEMS WITHOUT EXCEPTION RULES IN OVERRIDING ===");
        
        // Problems shown through conceptual examples
        demonstrateSubstitutabilityProblem();
        demonstrateClientCodeBreakage();
        demonstrateUnpredictableExceptions();
        demonstrateContractViolation();
    }
    
    // PROBLEM 1: Substitutability violation
    public void demonstrateSubstitutabilityProblem() {
        System.out.println("\n--- Substitutability Problem ---");
        
        // If Java allowed this, it would break substitutability
        FileProcessor processor1 = new BasicFileProcessor();
        FileProcessor processor2 = new ProblematicEnhancedProcessor(); // Violates rules!
        
        // Client code expects to handle only IOException
        List<FileProcessor> processors = Arrays.asList(processor1, processor2);
        
        for (FileProcessor processor : processors) {
            try {
                processor.processFile("test.txt");
                System.out.println("✅ File processed successfully");
                
            } catch (IOException e) {
                System.out.printf("⚠️ IO Exception handled: %s%n", e.getMessage());
                
                // PROBLEM: What if processor2 throws SQLException?
                // Client code is NOT prepared to handle it!
                // This would cause unexpected crashes!
                
            } catch (Exception e) {
                // This catch-all hides the real problem
                System.out.printf("❌ Unexpected exception: %s%n", e.getClass().getSimpleName());
                System.out.println("   Client code wasn't prepared for this exception type!");
            }
        }
        
        System.out.println("💡 Java's exception rules prevent this substitutability problem");
    }
    
    // PROBLEM 2: Client code breakage
    public void demonstrateClientCodeBreakage() {
        System.out.println("\n--- Client Code Breakage ---");
        
        // Show how client code would break without exception rules
        System.out.println("🔄 Client code written for parent class...");
        
        // Client code designed for parent class exceptions only
        DataProcessor processor = getRandomProcessor(); // Could be child class
        
        try {
            String result = processor.processData("sample data");
            System.out.printf("✅ Data processed: %s%n", result);
            
        } catch (ProcessingException e) {
            // Client expects only ProcessingException or its subtypes
            System.out.printf("⚠️ Processing error handled: %s%n", e.getMessage());
            
            // PROBLEM: If child could throw broader exceptions,
            // this catch block wouldn't catch them, causing crashes!
        }
        
        System.out.println("💡 Exception rules ensure client code remains functional");
    }
    
    private DataProcessor getRandomProcessor() {
        // Randomly return parent or child instance
        return new Random().nextBoolean() 
               ? new BaseDataProcessor() 
               : new SafeEnhancedDataProcessor();
    }
    
    // PROBLEM 3: Unpredictable exception behavior
    public void demonstrateUnpredictableExceptions() {
        System.out.println("\n--- Unpredictable Exception Behavior ---");
        
        // Show how method behavior becomes unpredictable
        System.out.println("🔄 Calling same method on different objects...");
        
        Calculator calc1 = new BasicCalculator();
        Calculator calc2 = new SafeAdvancedCalculator();
        
        Calculator[] calculators = {calc1, calc2};
        
        for (int i = 0; i < calculators.length; i++) {
            try {
                double result = calculators[i].divide(10, 2);
                System.out.printf("Calculator %d result: %.2f%n", i + 1, result);
                
            } catch (ArithmeticException e) {
                System.out.printf("Calculator %d arithmetic error: %s%n", i + 1, e.getMessage());
                
                // PROBLEM: If different calculators could throw different
                // exception types, behavior becomes unpredictable!
                // Client can't write robust exception handling!
            }
        }
        
        System.out.println("💡 Consistent exception behavior is crucial for reliability");
    }
    
    // PROBLEM 4: Contract violation
    public void demonstrateContractViolation() {
        System.out.println("\n--- Contract Violation ---");
        
        // Show how exception contracts get violated
        DocumentProcessor docProcessor = new SafeDocumentProcessor();
        
        try {
            // Method contract says: throws DocumentException
            docProcessor.parseDocument("invalid.xml");
            
        } catch (DocumentException e) {
            System.out.printf("⚠️ Document error (expected): %s%n", e.getMessage());
            
            // PROBLEM: If overriding method could throw SQLException,
            // it would violate the documented contract!
            // Users expect only DocumentException and its subtypes!
            
        } catch (RuntimeException e) {
            System.out.printf("❌ Runtime error (unexpected): %s%n", e.getClass().getSimpleName());
            System.out.println("   This violates the method's documented contract!");
        }
        
        System.out.println("💡 Exception rules maintain method contracts");
    }
    
    // Supporting classes for problem demonstration
    
    static abstract class FileProcessor {
        public abstract void processFile(String filename) throws IOException;
    }
    
    static class BasicFileProcessor extends FileProcessor {
        @Override
        public void processFile(String filename) throws IOException {
            System.out.println("  Processing file with basic processor: " + filename);
            // Safe implementation
        }
    }
    
    // This would violate exception rules if Java allowed it!
    static class ProblematicEnhancedProcessor extends FileProcessor {
        
        // ILLEGAL! Cannot throw broader exception than parent
        // public void processFile(String filename) throws IOException, SQLException {
        
        @Override
        public void processFile(String filename) throws IOException {
            System.out.println("  Processing file with enhanced processor: " + filename);
            
            // Simulating what would happen if broader exceptions were allowed
            if (filename.contains("sql")) {
                // Would want to throw SQLException but can't due to rules!
                throw new IOException("Database connection failed (wrapped SQLException)");
            }
        }
    }
    
    static class ProcessingException extends Exception {
        public ProcessingException(String message) { super(message); }
    }
    
    static abstract class DataProcessor {
        public abstract String processData(String data) throws ProcessingException;
    }
    
    static class BaseDataProcessor extends DataProcessor {
        @Override
        public String processData(String data) throws ProcessingException {
            if (data == null) {
                throw new ProcessingException("Data cannot be null");
            }
            return "Processed: " + data;
        }
    }
    
    static class SafeEnhancedDataProcessor extends DataProcessor {
        @Override
        public String processData(String data) throws ProcessingException {
            if (data == null) {
                throw new ProcessingException("Data cannot be null");
            }
            if (data.trim().isEmpty()) {
                throw new ProcessingException("Data cannot be empty");
            }
            return "Enhanced processing: " + data.toUpperCase();
        }
    }
    
    static abstract class Calculator {
        public abstract double divide(double a, double b) throws ArithmeticException;
    }
    
    static class BasicCalculator extends Calculator {
        @Override
        public double divide(double a, double b) throws ArithmeticException {
            if (b == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        }
    }
    
    static class SafeAdvancedCalculator extends Calculator {
        @Override
        public double divide(double a, double b) throws ArithmeticException {
            if (b == 0) {
                throw new ArithmeticException("Advanced calculator: Division by zero detected");
            }
            // Additional validation
            if (Double.isInfinite(a) || Double.isNaN(a)) {
                throw new ArithmeticException("Invalid dividend");
            }
            return a / b;
        }
    }
    
    static class DocumentException extends Exception {
        public DocumentException(String message) { super(message); }
    }
    
    static abstract class DocumentProcessor {
        public abstract void parseDocument(String filename) throws DocumentException;
    }
    
    static class SafeDocumentProcessor extends DocumentProcessor {
        @Override
        public void parseDocument(String filename) throws DocumentException {
            if (filename == null || !filename.endsWith(".xml")) {
                throw new DocumentException("Invalid XML document: " + filename);
            }
            System.out.println("  Parsing document: " + filename);
        }
    }
}
```

### With Proper Exception Rules - Safe and Predictable:
```java
// ROBUST CODE - Following exception rules in method overriding

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

class RobustExceptionOverriding {
    
    public void demonstrateProperExceptionRules() {
        System.out.println("=== PROPER EXCEPTION RULES IN METHOD OVERRIDING ===");
        
        demonstrateCheckedExceptionRules();
        demonstrateUncheckedExceptionRules();
        demonstrateNoExceptionOverriding();
        demonstrateExceptionSubtypes();
        demonstrateComplexInheritanceHierarchy();
    }
    
    // RULE 1: Checked exception rules
    public void demonstrateCheckedExceptionRules() {
        System.out.println("\n--- Checked Exception Rules ---");
        
        System.out.println("✅ Rule: Overriding method can throw same, subtype, or no checked exception");
        
        // Test different implementations following the rules
        NetworkService[] services = {
            new BasicNetworkService(),
            new EnhancedNetworkService(),
            new AdvancedNetworkService()
        };
        
        for (int i = 0; i < services.length; i++) {
            NetworkService service = services[i];
            
            try {
                String data = service.fetchData("https://api.example.com/data");
                System.out.printf("Service %d: %s%n", i + 1, data);
                
            } catch (IOException e) {
                // All implementations can be handled the same way!
                System.out.printf("Service %d IO error: %s%n", i + 1, e.getMessage());
                
            } catch (Exception e) {
                System.out.printf("Service %d unexpected error: %s%n", i + 1, e.getMessage());
            }
        }
        
        System.out.println("✅ All implementations safely substitutable");
    }
    
    // RULE 2: Unchecked exception rules  
    public void demonstrateUncheckedExceptionRules() {
        System.out.println("\n--- Unchecked Exception Rules ---");
        
        System.out.println("✅ Rule: Overriding method can throw any unchecked exception");
        
        MathProcessor[] processors = {
            new SimpleMathProcessor(),
            new ValidatingMathProcessor(),
            new ScientificMathProcessor()
        };
        
        for (int i = 0; i < processors.length; i++) {
            MathProcessor processor = processors[i];
            
            try {
                double result = processor.calculate(10, 0);
                System.out.printf("Processor %d result: %.2f%n", i + 1, result);
                
            } catch (ArithmeticException e) {
                System.out.printf("Processor %d arithmetic error: %s%n", i + 1, e.getMessage());
                
            } catch (IllegalArgumentException e) {
                System.out.printf("Processor %d validation error: %s%n", i + 1, e.getMessage());
                
            } catch (RuntimeException e) {
                System.out.printf("Processor %d runtime error: %s%n", i + 1, e.getClass().getSimpleName());
            }
        }
        
        System.out.println("✅ Different unchecked exceptions handled appropriately");
    }
    
    // RULE 3: No exception to having exception
    public void demonstrateNoExceptionOverriding() {
        System.out.println("\n--- No Exception to Having Exception ---");
        
        System.out.println("✅ Rule: If parent throws no exception, child cannot throw checked exception");
        
        DataValidator[] validators = {
            new SimpleDataValidator(),
            new EnhancedDataValidator()
        };
        
        for (int i = 0; i < validators.length; i++) {
            DataValidator validator = validators[i];
            
            // No exception handling needed - parent method throws no checked exceptions
            boolean isValid = validator.isValid("test data");
            System.out.printf("Validator %d result: %s%n", i + 1, isValid ? "Valid" : "Invalid");
        }
        
        System.out.println("✅ All validators usable without exception handling");
    }
    
    // RULE 4: Exception subtypes
    public void demonstrateExceptionSubtypes() {
        System.out.println("\n--- Exception Subtypes ---");
        
        System.out.println("✅ Rule: Overriding method can throw subtypes of declared exceptions");
        
        DatabaseService[] services = {
            new BasicDatabaseService(),
            new TransactionalDatabaseService(),
            new ClusteredDatabaseService()
        };
        
        for (int i = 0; i < services.length; i++) {
            DatabaseService service = services[i];
            
            try {
                String result = service.executeQuery("SELECT * FROM users");
                System.out.printf("Database service %d: %s%n", i + 1, result);
                
            } catch (DatabaseException e) {
                // Handles all subtypes: ConnectionException, QueryException, etc.
                System.out.printf("Database service %d error: %s (%s)%n", 
                                 i + 1, e.getMessage(), e.getClass().getSimpleName());
                
                // Can also handle specific subtypes
                if (e instanceof ConnectionException) {
                    System.out.println("  → Connection-specific handling could go here");
                } else if (e instanceof QueryException) {
                    System.out.println("  → Query-specific handling could go here");
                }
            }
        }
        
        System.out.println("✅ Exception hierarchy allows flexible yet safe handling");
    }
    
    // RULE 5: Complex inheritance hierarchy
    public void demonstrateComplexInheritanceHierarchy() {
        System.out.println("\n--- Complex Inheritance Hierarchy ---");
        
        System.out.println("✅ Rule: Exception rules apply through entire inheritance chain");
        
        // Multi-level inheritance with exception constraints
        FileHandler[] handlers = {
            new BasicFileHandler(),
            new BufferedFileHandler(),
            new CompressedFileHandler(),
            new EncryptedFileHandler()
        };
        
        String filename = "test-file.txt";
        
        for (int i = 0; i < handlers.length; i++) {
            FileHandler handler = handlers[i];
            
            try {
                handler.readFile(filename);
                System.out.printf("Handler %d (%s): File read successfully%n", 
                                 i + 1, handler.getClass().getSimpleName());
                
            } catch (FileNotFoundException e) {
                System.out.printf("Handler %d: File not found - %s%n", i + 1, e.getMessage());
                
            } catch (IOException e) {
                System.out.printf("Handler %d: IO error - %s%n", i + 1, e.getMessage());
                
            } catch (SecurityException e) {
                System.out.printf("Handler %d: Security error - %s%n", i + 1, e.getMessage());
            }
        }
        
        System.out.println("✅ All handlers in hierarchy follow exception rules");
    }
    
    // Supporting classes demonstrating proper exception rules
    
    // CHECKED EXCEPTION EXAMPLES
    abstract class NetworkService {
        // Parent declares IOException
        public abstract String fetchData(String url) throws IOException;
    }
    
    static class BasicNetworkService extends NetworkService {
        @Override
        public String fetchData(String url) throws IOException {
            // Can throw same exception
            if (url == null) {
                throw new IOException("URL cannot be null");
            }
            return "Basic data from " + url;
        }
    }
    
    static class EnhancedNetworkService extends NetworkService {
        @Override
        public String fetchData(String url) throws FileNotFoundException {
            // Can throw subtype of IOException ✅
            if (url.contains("missing")) {
                throw new FileNotFoundException("Resource not found: " + url);
            }
            return "Enhanced data from " + url;
        }
    }
    
    static class AdvancedNetworkService extends NetworkService {
        @Override
        public String fetchData(String url) {
            // Can throw no checked exception ✅
            return "Advanced cached data from " + url;
        }
    }
    
    // UNCHECKED EXCEPTION EXAMPLES
    abstract class MathProcessor {
        public abstract double calculate(double a, double b);
    }
    
    static class SimpleMathProcessor extends MathProcessor {
        @Override
        public double calculate(double a, double b) {
            if (b == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        }
    }
    
    static class ValidatingMathProcessor extends MathProcessor {
        @Override
        public double calculate(double a, double b) throws IllegalArgumentException {
            // Can throw different unchecked exception ✅
            if (Double.isNaN(a) || Double.isNaN(b)) {
                throw new IllegalArgumentException("NaN values not allowed");
            }
            if (b == 0) {
                throw new ArithmeticException("Division by zero in validating processor");
            }
            return a / b;
        }
    }
    
    static class ScientificMathProcessor extends MathProcessor {
        @Override
        public double calculate(double a, double b) {
            // Can throw custom unchecked exception ✅
            if (Double.isInfinite(a) || Double.isInfinite(b)) {
                throw new ScientificCalculationException("Infinite values require special handling");
            }
            if (b == 0) {
                return Double.POSITIVE_INFINITY; // Different behavior, no exception
            }
            return a / b;
        }
    }
    
    static class ScientificCalculationException extends RuntimeException {
        public ScientificCalculationException(String message) { super(message); }
    }
    
    // NO EXCEPTION EXAMPLES
    abstract class DataValidator {
        public abstract boolean isValid(String data); // No exceptions declared
    }
    
    static class SimpleDataValidator extends DataValidator {
        @Override
        public boolean isValid(String data) {
            // Cannot throw checked exception ✅
            return data != null && !data.trim().isEmpty();
        }
    }
    
    static class EnhancedDataValidator extends DataValidator {
        @Override
        public boolean isValid(String data) throws IllegalArgumentException {
            // Can throw unchecked exception ✅
            if (data == null) {
                throw new IllegalArgumentException("Data cannot be null for enhanced validation");
            }
            return data.trim().length() >= 3;
        }
    }
    
    // EXCEPTION HIERARCHY EXAMPLES
    static class DatabaseException extends Exception {
        public DatabaseException(String message) { super(message); }
    }
    
    static class ConnectionException extends DatabaseException {
        public ConnectionException(String message) { super(message); }
    }
    
    static class QueryException extends DatabaseException {
        public QueryException(String message) { super(message); }
    }
    
    static class TransactionException extends DatabaseException {
        public TransactionException(String message) { super(message); }
    }
    
    abstract class DatabaseService {
        public abstract String executeQuery(String query) throws DatabaseException;
    }
    
    static class BasicDatabaseService extends DatabaseService {
        @Override
        public String executeQuery(String query) throws DatabaseException {
            if (query == null || query.trim().isEmpty()) {
                throw new DatabaseException("Query cannot be empty");
            }
            return "Basic query result for: " + query;
        }
    }
    
    static class TransactionalDatabaseService extends DatabaseService {
        @Override
        public String executeQuery(String query) throws QueryException {
            // Can throw subtype ✅
            if (query.toLowerCase().contains("drop")) {
                throw new QueryException("DROP statements not allowed in transactional service");
            }
            return "Transactional query result for: " + query;
        }
    }
    
    static class ClusteredDatabaseService extends DatabaseService {
        @Override
        public String executeQuery(String query) throws ConnectionException {
            // Can throw different subtype ✅
            if (query.length() > 1000) {
                throw new ConnectionException("Query too large for cluster distribution");
            }
            return "Clustered query result for: " + query;
        }
    }
    
    // MULTI-LEVEL INHERITANCE EXAMPLES
    abstract class FileHandler {
        public abstract void readFile(String filename) throws IOException;
    }
    
    abstract class StreamingFileHandler extends FileHandler {
        // Inherits: throws IOException
        @Override
        public abstract void readFile(String filename) throws IOException;
    }
    
    static class BasicFileHandler extends FileHandler {
        @Override
        public void readFile(String filename) throws FileNotFoundException {
            // Can throw subtype ✅
            if ("missing.txt".equals(filename)) {
                throw new FileNotFoundException("File not found: " + filename);
            }
            System.out.println("    Reading file with basic handler: " + filename);
        }
    }
    
    static class BufferedFileHandler extends StreamingFileHandler {
        @Override
        public void readFile(String filename) throws IOException {
            // Can throw same exception ✅
            if (filename.contains("corrupt")) {
                throw new IOException("File corrupted: " + filename);
            }
            System.out.println("    Reading file with buffered handler: " + filename);
        }
    }
    
    static class CompressedFileHandler extends StreamingFileHandler {
        @Override
        public void readFile(String filename) {
            // Can throw no checked exception ✅
            System.out.println("    Reading compressed file: " + filename);
            // Might still throw unchecked exceptions
            if (filename.length() > 100) {
                throw new RuntimeException("Filename too long for compression");
            }
        }
    }
    
    static class EncryptedFileHandler extends FileHandler {
        @Override
        public void readFile(String filename) throws IOException {
            // Can throw same exception ✅
            if (filename.contains("unauthorized")) {
                // Could throw SecurityException (unchecked) instead
                throw new SecurityException("Unauthorized access to encrypted file");
            }
            System.out.println("    Reading encrypted file: " + filename);
        }
    }
}
```

## Real-World Exception Overriding Scenarios

```java
// Real-world examples of exception overriding patterns

import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;

class RealWorldExceptionOverriding {
    
    public void demonstrateRealWorldScenarios() {
        System.out.println("=== REAL-WORLD EXCEPTION OVERRIDING SCENARIOS ===");
        
        demonstrateRepositoryPattern();
        demonstrateServiceLayerPattern();
        demonstrateTemplateMethodPattern();
        demonstrateStrategyPattern();
        demonstrateDecoratorPattern();
    }
    
    // SCENARIO 1: Repository Pattern with different data sources
    public void demonstrateRepositoryPattern() {
        System.out.println("\n--- Repository Pattern ---");
        
        List<UserRepository> repositories = Arrays.asList(
            new DatabaseUserRepository(),
            new FileUserRepository(),
            new CachedUserRepository()
        );
        
        for (UserRepository repo : repositories) {
            try {
                Optional<User> user = repo.findById("user123");
                if (user.isPresent()) {
                    System.out.printf("✅ %s: Found user %s%n", 
                                     repo.getClass().getSimpleName(), user.get().getName());
                } else {
                    System.out.printf("⚠️ %s: User not found%n", 
                                     repo.getClass().getSimpleName());
                }
                
            } catch (DataAccessException e) {
                System.out.printf("❌ %s: Data access error - %s%n", 
                                 repo.getClass().getSimpleName(), e.getMessage());
            }
        }
        
        System.out.println("✅ All repository implementations follow same exception contract");
    }
    
    // SCENARIO 2: Service Layer with business logic
    public void demonstrateServiceLayerPattern() {
        System.out.println("\n--- Service Layer Pattern ---");
        
        List<PaymentService> services = Arrays.asList(
            new CreditCardService(),
            new PayPalService(), 
            new BankTransferService()
        );
        
        PaymentRequest request = new PaymentRequest("100.00", "USD", "user123");
        
        for (PaymentService service : services) {
            try {
                PaymentResult result = service.processPayment(request);
                System.out.printf("✅ %s: Payment processed - %s%n", 
                                 service.getClass().getSimpleName(), result.getTransactionId());
                
            } catch (PaymentException e) {
                System.out.printf("❌ %s: Payment failed - %s%n", 
                                 service.getClass().getSimpleName(), e.getMessage());
                
                // Handle specific payment exception types
                if (e instanceof InsufficientFundsException) {
                    System.out.println("   → Insufficient funds - notify user");
                } else if (e instanceof InvalidCardException) {
                    System.out.println("   → Invalid card - request new payment method");
                } else if (e instanceof ServiceUnavailableException) {
                    System.out.println("   → Service down - retry later");
                }
            }
        }
    }
    
    // SCENARIO 3: Template Method Pattern with exception handling
    public void demonstrateTemplateMethodPattern() {
        System.out.println("\n--- Template Method Pattern ---");
        
        List<DocumentProcessor> processors = Arrays.asList(
            new PDFDocumentProcessor(),
            new WordDocumentProcessor(),
            new ExcelDocumentProcessor()
        );
        
        String documentPath = "sample-document";
        
        for (DocumentProcessor processor : processors) {
            try {
                DocumentMetadata metadata = processor.processDocument(documentPath);
                System.out.printf("✅ %s: Processed document - %d pages%n", 
                                 processor.getClass().getSimpleName(), metadata.getPageCount());
                
            } catch (DocumentProcessingException e) {
                System.out.printf("❌ %s: Processing failed - %s%n", 
                                 processor.getClass().getSimpleName(), e.getMessage());
                
                // Specific exception handling
                if (e instanceof UnsupportedFormatException) {
                    System.out.println("   → Format not supported - convert document");
                } else if (e instanceof CorruptedDocumentException) {
                    System.out.println("   → Document corrupted - request new copy");
                }
            }
        }
    }
    
    // SCENARIO 4: Strategy Pattern with exception consistency  
    public void demonstrateStrategyPattern() {
        System.out.println("\n--- Strategy Pattern ---");
        
        DataCompressionContext context = new DataCompressionContext();
        
        // Different compression strategies with consistent exception handling
        List<CompressionStrategy> strategies = Arrays.asList(
            new ZipCompressionStrategy(),
            new GzipCompressionStrategy(),
            new LzmaCompressionStrategy()
        );
        
        byte[] testData = "This is test data for compression".getBytes();
        
        for (CompressionStrategy strategy : strategies) {
            context.setStrategy(strategy);
            
            try {
                byte[] compressed = context.compress(testData);
                double ratio = (double) compressed.length / testData.length;
                System.out.printf("✅ %s: Compression ratio %.2f%n", 
                                 strategy.getClass().getSimpleName(), ratio);
                
            } catch (CompressionException e) {
                System.out.printf("❌ %s: Compression failed - %s%n", 
                                 strategy.getClass().getSimpleName(), e.getMessage());
            }
        }
    }
    
    // SCENARIO 5: Decorator Pattern with exception propagation
    public void demonstrateDecoratorPattern() {
        System.out.println("\n--- Decorator Pattern ---");
        
        // Build decorator chain
        MessageSender baseSender = new EmailSender();
        MessageSender encryptedSender = new EncryptionDecorator(baseSender);
        MessageSender retryingSender = new RetryDecorator(encryptedSender);
        MessageSender loggingSender = new LoggingDecorator(retryingSender);
        
        List<MessageSender> senders = Arrays.asList(
            baseSender,
            encryptedSender,
            retryingSender,
            loggingSender
        );
        
        Message message = new Message("test@example.com", "Test Subject", "Test Body");
        
        for (MessageSender sender : senders) {
            try {
                sender.send(message);
                System.out.printf("✅ %s: Message sent successfully%n", 
                                 sender.getClass().getSimpleName());
                
            } catch (MessagingException e) {
                System.out.printf("❌ %s: Send failed - %s%n", 
                                 sender.getClass().getSimpleName(), e.getMessage());
            }
        }
    }
    
    // Supporting classes for real-world scenarios
    
    // REPOSITORY PATTERN
    static class DataAccessException extends Exception {
        public DataAccessException(String message) { super(message); }
    }
    
    static class User {
        private final String id;
        private final String name;
        
        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
    }
    
    interface UserRepository {
        Optional<User> findById(String id) throws DataAccessException;
    }
    
    static class DatabaseUserRepository implements UserRepository {
        @Override
        public Optional<User> findById(String id) throws DataAccessException {
            // Can throw same exception ✅
            if ("error".equals(id)) {
                throw new DataAccessException("Database connection failed");
            }
            return "user123".equals(id) ? 
                   Optional.of(new User(id, "John Database")) : 
                   Optional.empty();
        }
    }
    
    static class FileUserRepository implements UserRepository {
        @Override
        public Optional<User> findById(String id) throws DataAccessException {
            // Can throw same exception ✅  
            if ("missing".equals(id)) {
                throw new DataAccessException("User file not found");
            }
            return "user123".equals(id) ? 
                   Optional.of(new User(id, "John File")) : 
                   Optional.empty();
        }
    }
    
    static class CachedUserRepository implements UserRepository {
        @Override
        public Optional<User> findById(String id) {
            // Can throw no checked exception ✅
            return "user123".equals(id) ? 
                   Optional.of(new User(id, "John Cached")) : 
                   Optional.empty();
        }
    }
    
    // PAYMENT SERVICE PATTERN
    static class PaymentException extends Exception {
        public PaymentException(String message) { super(message); }
    }
    
    static class InsufficientFundsException extends PaymentException {
        public InsufficientFundsException(String message) { super(message); }
    }
    
    static class InvalidCardException extends PaymentException {
        public InvalidCardException(String message) { super(message); }
    }
    
    static class ServiceUnavailableException extends PaymentException {
        public ServiceUnavailableException(String message) { super(message); }
    }
    
    static class PaymentRequest {
        private final String amount;
        private final String currency;
        private final String userId;
        
        public PaymentRequest(String amount, String currency, String userId) {
            this.amount = amount;
            this.currency = currency;
            this.userId = userId;
        }
        
        public String getAmount() { return amount; }
        public String getCurrency() { return currency; }
        public String getUserId() { return userId; }
    }
    
    static class PaymentResult {
        private final String transactionId;
        private final boolean success;
        
        public PaymentResult(String transactionId, boolean success) {
            this.transactionId = transactionId;
            this.success = success;
        }
        
        public String getTransactionId() { return transactionId; }
        public boolean isSuccess() { return success; }
    }
    
    interface PaymentService {
        PaymentResult processPayment(PaymentRequest request) throws PaymentException;
    }
    
    static class CreditCardService implements PaymentService {
        @Override
        public PaymentResult processPayment(PaymentRequest request) throws InvalidCardException {
            // Can throw subtype ✅
            if ("invalid".equals(request.getUserId())) {
                throw new InvalidCardException("Credit card validation failed");
            }
            return new PaymentResult("CC_" + System.currentTimeMillis(), true);
        }
    }
    
    static class PayPalService implements PaymentService {
        @Override
        public PaymentResult processPayment(PaymentRequest request) throws InsufficientFundsException {
            // Can throw different subtype ✅
            if ("broke".equals(request.getUserId())) {
                throw new InsufficientFundsException("PayPal account has insufficient funds");
            }
            return new PaymentResult("PP_" + System.currentTimeMillis(), true);
        }
    }
    
    static class BankTransferService implements PaymentService {
        @Override
        public PaymentResult processPayment(PaymentRequest request) throws PaymentException {
            // Can throw parent exception ✅
            if ("unavailable".equals(request.getUserId())) {
                throw new PaymentException("Bank transfer service temporarily unavailable");
            }
            return new PaymentResult("BT_" + System.currentTimeMillis(), true);
        }
    }
    
    // DOCUMENT PROCESSING PATTERN
    static class DocumentProcessingException extends Exception {
        public DocumentProcessingException(String message) { super(message); }
    }
    
    static class UnsupportedFormatException extends DocumentProcessingException {
        public UnsupportedFormatException(String message) { super(message); }
    }
    
    static class CorruptedDocumentException extends DocumentProcessingException {
        public CorruptedDocumentException(String message) { super(message); }
    }
    
    static class DocumentMetadata {
        private final int pageCount;
        private final String title;
        
        public DocumentMetadata(int pageCount, String title) {
            this.pageCount = pageCount;
            this.title = title;
        }
        
        public int getPageCount() { return pageCount; }
        public String getTitle() { return title; }
    }
    
    abstract class DocumentProcessor {
        // Template method
        public final DocumentMetadata processDocument(String path) throws DocumentProcessingException {
            validateDocument(path);
            DocumentMetadata metadata = extractMetadata(path);
            processContent(path);
            return metadata;
        }
        
        protected abstract void validateDocument(String path) throws DocumentProcessingException;
        protected abstract DocumentMetadata extractMetadata(String path) throws DocumentProcessingException;
        protected abstract void processContent(String path) throws DocumentProcessingException;
    }
    
    static class PDFDocumentProcessor extends DocumentProcessor {
        @Override
        protected void validateDocument(String path) throws CorruptedDocumentException {
            // Can throw subtype ✅
            if (path.contains("corrupted")) {
                throw new CorruptedDocumentException("PDF file is corrupted");
            }
        }
        
        @Override
        protected DocumentMetadata extractMetadata(String path) {
            // Can throw no exception ✅
            return new DocumentMetadata(5, "PDF Document");
        }
        
        @Override
        protected void processContent(String path) throws DocumentProcessingException {
            // Can throw parent exception ✅
            System.out.println("Processing PDF content");
        }
    }
    
    static class WordDocumentProcessor extends DocumentProcessor {
        @Override
        protected void validateDocument(String path) throws UnsupportedFormatException {
            // Can throw different subtype ✅
            if (path.contains("old-format")) {
                throw new UnsupportedFormatException("Old Word format not supported");
            }
        }
        
        @Override
        protected DocumentMetadata extractMetadata(String path) throws DocumentProcessingException {
            // Can throw parent exception ✅
            return new DocumentMetadata(3, "Word Document");
        }
        
        @Override
        protected void processContent(String path) {
            // Can throw no exception ✅
            System.out.println("Processing Word content");
        }
    }
    
    static class ExcelDocumentProcessor extends DocumentProcessor {
        @Override
        protected void validateDocument(String path) {
            // Can throw no exception ✅
            System.out.println("Excel validation passed");
        }
        
        @Override
        protected DocumentMetadata extractMetadata(String path) throws DocumentProcessingException {
            // Can throw parent exception ✅
            return new DocumentMetadata(1, "Excel Spreadsheet");
        }
        
        @Override
        protected void processContent(String path) throws CorruptedDocumentException {
            // Can throw subtype ✅
            if (path.contains("formula-error")) {
                throw new CorruptedDocumentException("Excel formulas are corrupted");
            }
            System.out.println("Processing Excel content");
        }
    }
    
    // COMPRESSION STRATEGY PATTERN
    static class CompressionException extends Exception {
        public CompressionException(String message) { super(message); }
    }
    
    interface CompressionStrategy {
        byte[] compress(byte[] data) throws CompressionException;
    }
    
    static class DataCompressionContext {
        private CompressionStrategy strategy;
        
        public void setStrategy(CompressionStrategy strategy) {
            this.strategy = strategy;
        }
        
        public byte[] compress(byte[] data) throws CompressionException {
            return strategy.compress(data);
        }
    }
    
    static class ZipCompressionStrategy implements CompressionStrategy {
        @Override
        public byte[] compress(byte[] data) throws CompressionException {
            // Can throw same exception ✅
            if (data.length == 0) {
                throw new CompressionException("Cannot compress empty data");
            }
            return Arrays.copyOf(data, data.length / 2); // Simulated compression
        }
    }
    
    static class GzipCompressionStrategy implements CompressionStrategy {
        @Override
        public byte[] compress(byte[] data) {
            // Can throw no exception ✅
            return Arrays.copyOf(data, Math.max(1, data.length / 3)); // Simulated compression
        }
    }
    
    static class LzmaCompressionStrategy implements CompressionStrategy {
        @Override
        public byte[] compress(byte[] data) throws CompressionException {
            // Can throw same exception ✅
            if (data.length > 10000) {
                throw new CompressionException("LZMA cannot handle data larger than 10KB");
            }
            return Arrays.copyOf(data, Math.max(1, data.length / 4)); // Simulated compression
        }
    }
    
    // MESSAGE SENDER DECORATOR PATTERN
    static class MessagingException extends Exception {
        public MessagingException(String message) { super(message); }
    }
    
    static class Message {
        private final String to;
        private final String subject;
        private final String body;
        
        public Message(String to, String subject, String body) {
            this.to = to;
            this.subject = subject;
            this.body = body;
        }
        
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
    }
    
    interface MessageSender {
        void send(Message message) throws MessagingException;
    }
    
    static class EmailSender implements MessageSender {
        @Override
        public void send(Message message) throws MessagingException {
            if ("invalid@".equals(message.getTo())) {
                throw new MessagingException("Invalid email address");
            }
            System.out.println("Email sent via SMTP");
        }
    }
    
    abstract class MessageSenderDecorator implements MessageSender {
        protected final MessageSender wrapped;
        
        public MessageSenderDecorator(MessageSender wrapped) {
            this.wrapped = wrapped;
        }
        
        @Override
        public void send(Message message) throws MessagingException {
            wrapped.send(message);
        }
    }
    
    static class EncryptionDecorator extends MessageSenderDecorator {
        public EncryptionDecorator(MessageSender wrapped) {
            super(wrapped);
        }
        
        @Override
        public void send(Message message) throws MessagingException {
            // Can throw same exception ✅
            System.out.println("Encrypting message");
            super.send(message);
        }
    }
    
    static class RetryDecorator extends MessageSenderDecorator {
        public RetryDecorator(MessageSender wrapped) {
            super(wrapped);
        }
        
        @Override
        public void send(Message message) throws MessagingException {
            // Can throw same exception ✅
            System.out.println("Adding retry logic");
            try {
                super.send(message);
            } catch (MessagingException e) {
                System.out.println("Retrying after failure");
                super.send(message); // Retry once
            }
        }
    }
    
    static class LoggingDecorator extends MessageSenderDecorator {
        public LoggingDecorator(MessageSender wrapped) {
            super(wrapped);
        }
        
        @Override
        public void send(Message message) throws MessagingException {
            // Can throw same exception ✅
            System.out.printf("Logging: Sending message to %s%n", message.getTo());
            super.send(message);
            System.out.println("Message send completed");
        }
    }
}
```

## Complete Exception Overriding Demo

```java
public class CompleteExceptionOverridingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE EXCEPTION OVERRIDING DEMONSTRATION ===");
        
        // 1. Problems without exception rules
        System.out.println("\n1. PROBLEMS WITHOUT PROPER EXCEPTION RULES");
        ProblematicExceptionOverriding problematic = new ProblematicExceptionOverriding();
        problematic.demonstrateProblems();
        
        // 2. Proper exception rules implementation
        System.out.println("\n\n2. PROPER EXCEPTION RULES IMPLEMENTATION");
        RobustExceptionOverriding robust = new RobustExceptionOverriding();
        robust.demonstrateProperExceptionRules();
        
        // 3. Real-world scenarios
        System.out.println("\n\n3. REAL-WORLD EXCEPTION OVERRIDING SCENARIOS");
        RealWorldExceptionOverriding realWorld = new RealWorldExceptionOverriding();
        realWorld.demonstrateRealWorldScenarios();
        
        System.out.println("\n=== DEMONSTRATION COMPLETED ===");
        printExceptionRules();
    }
    
    private static void printExceptionRules() {
        System.out.println("\n🎯 EXCEPTION OVERRIDING RULES:");
        System.out.println("✅ CHECKED EXCEPTIONS:");
        System.out.println("   • Overriding method can throw same checked exception");
        System.out.println("   • Overriding method can throw subtype of checked exception");
        System.out.println("   • Overriding method can throw no checked exception");
        System.out.println("   • Overriding method CANNOT throw broader checked exception");
        
        System.out.println("\n✅ UNCHECKED EXCEPTIONS:");
        System.out.println("   • Overriding method can throw any unchecked exception");
        System.out.println("   • No restrictions on RuntimeException and its subtypes");
        System.out.println("   • Error and its subtypes are also unrestricted");
        
        System.out.println("\n✅ KEY PRINCIPLES:");
        System.out.println("   • Maintains Liskov Substitution Principle");
        System.out.println("   • Ensures client code compatibility");
        System.out.println("   • Prevents unexpected exception types");
        System.out.println("   • Maintains method contracts");
        
        System.out.println("\n❌ VIOLATIONS:");
        System.out.println("   ❌ Child throwing broader checked exception than parent");
        System.out.println("   ❌ Child throwing new checked exception not declared by parent");
        System.out.println("   ❌ Breaking exception handling contracts");
        System.out.println("   ❌ Making overridden methods less safe for callers");
    }
}
```

## Interview Questions & Answers

**Q1: What are the rules for exception handling in method overriding?**
**A:** Overriding method can throw: 1) Same checked exception as parent, 2) Subtype of parent's checked exception, 3) No checked exception. It CANNOT throw broader checked exceptions. Unchecked exceptions have no restrictions.

**Q2: Why can't an overriding method throw broader checked exceptions?**
**A:** It would violate the Liskov Substitution Principle and break client code that expects to handle only the parent's declared exceptions. Clients wouldn't be prepared for unexpected exception types.

**Q3: Can an overriding method throw unchecked exceptions freely?**
**A:** Yes, unchecked exceptions (RuntimeException and Error subtypes) have no overriding restrictions because they don't need to be declared in method signatures.

**Q4: What happens if parent method declares no exceptions?**
**A:** The overriding method cannot throw any checked exceptions, but can still throw unchecked exceptions since they don't need to be declared.

**Q5: How do exception rules work with multiple inheritance levels?**
**A:** Each level in the inheritance hierarchy must follow the exception rules relative to its immediate parent. The rules cascade down the inheritance chain.

**Q6: Can you provide an example of valid exception overriding?**
**A:** 
```java
class Parent {
    public void method() throws IOException { }
}
class Child extends Parent {
    public void method() throws FileNotFoundException { } // Valid - FileNotFoundException is subtype of IOException
}
```

**Q7: What is the relationship between exception overriding and polymorphism?**
**A:** Exception rules ensure that polymorphic method calls remain safe - any object can be substituted without changing the expected exception handling behavior.

**Q8: How do these rules affect API design?**
**A:** Design parent classes to declare appropriate exception types. Use exception hierarchies to provide flexibility for subclasses while maintaining contract compatibility.

## Key Takeaways

1. **Exception rules maintain substitutability** - child objects can safely replace parent objects
2. **Checked exception restrictions are strict** - cannot throw broader exceptions than parent
3. **Unchecked exceptions are unrestricted** - RuntimeException and Error subtypes have no limits
4. **Rules prevent client code breakage** - callers remain compatible with all implementations
5. **Exception hierarchies provide flexibility** - subclasses can throw more specific exceptions
6. **Rules apply through inheritance chain** - each level must follow rules relative to parent
7. **Contract preservation is key** - method promises about exceptions must be kept
8. **Polymorphism safety is ensured** - any implementation can be used without surprises
9. **API design benefits from planning** - proper exception hierarchies enable safe overriding
10. **Real-world patterns follow these rules** - Repository, Service, Strategy patterns all demonstrate compliance

---

*Remember: Exception overriding rules are like family house rules - children can have stricter rules but never more lenient ones than their parents established!*