# Access Modifiers in Java - Controlling Visibility and Access

## Simple Explanation

Think of **Access Modifiers** as **different types of doors and locks** in a building:

- **Public** = **Main entrance** - Anyone can enter (accessible everywhere)
- **Protected** = **Family entrance** - Only family members and invited guests (package + subclasses)
- **Default/Package** = **Resident entrance** - Only residents of the building (same package only)
- **Private** = **Personal room** - Only the owner has the key (same class only)

### Real-World Analogies
- **Company departments** = Different access levels for employees
- **Hospital areas** = Public lobby, staff areas, restricted surgical rooms
- **School zones** = Public areas, teacher lounges, principal's office
- **Bank security** = Public area, employee area, vault (increasing restrictions)

## Professional Definition

**Access Modifiers** are Java keywords that set the accessibility or visibility of classes, methods, constructors, and variables. They control which parts of your code can access specific class members, providing encapsulation and security. Java has four access modifiers: `public`, `protected`, default (package-private), and `private`.

## Why Use Access Modifiers?

### Benefits:
- **Security**: Protect sensitive data and methods from unauthorized access
- **Encapsulation**: Hide internal implementation details
- **Maintainability**: Control what can be changed externally
- **Code Organization**: Clearly define interfaces and internal components
- **API Design**: Create clear boundaries between public and private functionality
- **Inheritance Control**: Manage what subclasses can access

### Problems Without Access Modifiers:
```java
// WITHOUT PROPER ACCESS CONTROL - Everything public!
public class BankAccount {
    public String accountNumber;      // Should be protected!
    public String customerName;       // Should be protected!
    public double balance;            // Should be private!
    public String password;           // NEVER public!
    public boolean isActive;          // Should be private!
    
    // Internal methods exposed publicly
    public void validatePassword(String pwd) {  // Should be private!
        // Sensitive validation logic exposed
    }
    
    public void updateInternalState() {         // Should be private!
        // Critical internal operations exposed
    }
    
    // Anyone can modify anything directly!
    // No validation, no security, no control
}

// Anyone can do this - DANGEROUS!
BankAccount account = new BankAccount();
account.balance = 1000000;           // Direct manipulation!
account.password = "hacked";         // Security breach!
account.updateInternalState();       // Break internal consistency!
```

### With Proper Access Modifiers - Secure and Controlled:
```java
public class BankAccount {
    private String accountNumber;      // Only this class can access
    private String customerName;       // Only this class can access
    private double balance;            // Only this class can access
    private String passwordHash;       // Only this class can access
    private boolean isActive;          // Only this class can access
    
    // Public interface - controlled access
    public double getBalance() {       // Safe read access
        return isActive ? balance : 0.0;
    }
    
    public boolean deposit(double amount) {    // Controlled modification
        if (amount > 0 && isActive) {
            balance += amount;
            return true;
        }
        return false;
    }
    
    // Private methods - internal implementation
    private boolean validatePassword(String pwd) {  // Hidden implementation
        return passwordHash.equals(hashPassword(pwd));
    }
    
    private String hashPassword(String pwd) {       // Hidden utility
        // Secure hashing implementation
        return pwd.hashCode() + "_hashed";
    }
}

// Only safe operations allowed
BankAccount account = new BankAccount();
// account.balance = 1000000;        // Compilation error!
// account.password = "hacked";      // Compilation error!
account.deposit(100);                // Safe and controlled!
```

## Access Modifier Levels

### 1. Public Access Modifier
```java
// Public class - accessible from anywhere
public class PublicExample {
    // Public field - accessible from anywhere
    public String publicField = "Everyone can see this";
    
    // Public method - accessible from anywhere
    public void publicMethod() {
        System.out.println("This method is accessible from anywhere");
    }
    
    // Public constructor - can be called from anywhere
    public PublicExample() {
        System.out.println("Public constructor called");
    }
    
    // Public nested class - accessible from anywhere
    public class PublicNestedClass {
        public void nestedMethod() {
            System.out.println("Public nested class method");
        }
    }
}

// Usage from any package
public class TestPublicAccess {
    public static void main(String[] args) {
        PublicExample example = new PublicExample();
        
        // All public members accessible
        System.out.println(example.publicField);
        example.publicMethod();
        
        PublicExample.PublicNestedClass nested = example.new PublicNestedClass();
        nested.nestedMethod();
    }
}
```

### 2. Private Access Modifier
```java
public class PrivateExample {
    // Private fields - only accessible within this class
    private String privateField = "Only this class can see this";
    private int secretNumber = 42;
    
    // Private methods - only accessible within this class
    private void privateMethod() {
        System.out.println("This is a private method");
        System.out.println("Secret number: " + secretNumber);
    }
    
    // Private constructor - prevents external instantiation
    private PrivateExample() {
        System.out.println("Private constructor - internal use only");
    }
    
    // Public factory method to control object creation
    public static PrivateExample createInstance() {
        return new PrivateExample();  // Can call private constructor from same class
    }
    
    // Public method that uses private members
    public void publicMethodUsingPrivates() {
        System.out.println("Public method accessing: " + privateField);
        privateMethod();  // Can call private method from same class
        
        // Direct access to private field
        secretNumber = 100;
        System.out.println("Modified secret number: " + secretNumber);
    }
    
    // Private nested class - only accessible within outer class
    private class PrivateNestedClass {
        private String nestedData = "Private nested data";
        
        private void nestedPrivateMethod() {
            // Can access outer class private members
            System.out.println("Nested accessing: " + privateField);
            privateMethod();
        }
    }
    
    public void usePrivateNestedClass() {
        PrivateNestedClass nested = new PrivateNestedClass();
        System.out.println("Nested data: " + nested.nestedData);
        nested.nestedPrivateMethod();
    }
}

// Testing private access
public class TestPrivateAccess {
    public static void main(String[] args) {
        // Cannot directly instantiate due to private constructor
        // PrivateExample example = new PrivateExample();  // Compilation error!
        
        // Must use factory method
        PrivateExample example = PrivateExample.createInstance();
        
        // Cannot access private members
        // System.out.println(example.privateField);  // Compilation error!
        // example.privateMethod();                   // Compilation error!
        
        // Can only use public interface
        example.publicMethodUsingPrivates();
        example.usePrivateNestedClass();
    }
}
```

### 3. Protected Access Modifier
```java
// Base class with protected members
package com.example.parent;

public class ProtectedExample {
    // Protected fields - accessible to subclasses and same package
    protected String protectedField = "Subclasses and package can see this";
    protected int protectedNumber = 25;
    
    // Protected method - accessible to subclasses and same package
    protected void protectedMethod() {
        System.out.println("This is a protected method");
        System.out.println("Protected number: " + protectedNumber);
    }
    
    // Protected constructor - can be called by subclasses
    protected ProtectedExample(String field) {
        this.protectedField = field;
        System.out.println("Protected constructor called");
    }
    
    // Public constructor for general use
    public ProtectedExample() {
        this("Default protected field");
    }
    
    // Protected nested class
    protected class ProtectedNestedClass {
        protected String nestedData = "Protected nested data";
        
        protected void displayInfo() {
            System.out.println("Nested class accessing: " + protectedField);
        }
    }
    
    // Package method using protected members
    void packageMethodUsingProtected() {
        System.out.println("Package method accessing protected: " + protectedField);
        protectedMethod();
    }
}

// Subclass in different package accessing protected members
package com.example.child;
import com.example.parent.ProtectedExample;

public class ChildClass extends ProtectedExample {
    private String childData;
    
    // Constructor calling protected parent constructor
    public ChildClass(String field, String childData) {
        super(field);  // Calling protected constructor
        this.childData = childData;
    }
    
    public void demonstrateProtectedAccess() {
        // Can access protected members from parent
        System.out.println("Child accessing protected field: " + protectedField);
        protectedMethod();  // Can call protected method
        
        // Can modify protected field
        protectedField = "Modified by child class";
        protectedNumber = 50;
        
        // Can create protected nested class instance
        ProtectedNestedClass nested = new ProtectedNestedClass();
        System.out.println("Nested data: " + nested.nestedData);
        nested.displayInfo();
    }
    
    // Override and enhance protected method
    @Override
    protected void protectedMethod() {
        super.protectedMethod();  // Call parent implementation
        System.out.println("Child class enhancement of protected method");
        System.out.println("Child data: " + childData);
    }
}

// Class in same package as ProtectedExample
package com.example.parent;

public class SamePackageClass {
    public void testProtectedAccess() {
        ProtectedExample example = new ProtectedExample();
        
        // Can access protected members (same package)
        System.out.println("Same package accessing: " + example.protectedField);
        example.protectedMethod();
        
        // Can modify protected field
        example.protectedField = "Modified by same package class";
        
        // Can use protected constructor
        ProtectedExample customExample = new ProtectedExample("Custom field");
        
        // Can access package method
        example.packageMethodUsingProtected();
    }
}

// Class in different package (not subclass)
package com.example.other;
import com.example.parent.ProtectedExample;

public class DifferentPackageClass {
    public void testProtectedAccess() {
        ProtectedExample example = new ProtectedExample();
        
        // CANNOT access protected members (different package, not subclass)
        // System.out.println(example.protectedField);  // Compilation error!
        // example.protectedMethod();                   // Compilation error!
        
        // CAN only use public methods
        // example has only public constructor and any public methods
    }
}
```

### 4. Default/Package Access Modifier
```java
// Default access - no modifier specified
package com.example.defaultaccess;

// Default class - accessible only within same package
class DefaultExample {
    // Default fields - accessible within same package
    String defaultField = "Same package can see this";
    int defaultNumber = 15;
    
    // Default methods - accessible within same package
    void defaultMethod() {
        System.out.println("This is a default access method");
        System.out.println("Default number: " + defaultNumber);
    }
    
    // Default constructor - accessible within same package
    DefaultExample() {
        System.out.println("Default constructor called");
    }
    
    DefaultExample(String field) {
        this.defaultField = field;
        System.out.println("Default parameterized constructor");
    }
    
    // Default nested class
    class DefaultNestedClass {
        String nestedData = "Default nested data";
        
        void displayInfo() {
            System.out.println("Nested accessing: " + defaultField);
        }
    }
    
    // Public method for external access
    public void publicMethodUsingDefaults() {
        System.out.println("Public method accessing default: " + defaultField);
        defaultMethod();
    }
}

// Another class in same package
package com.example.defaultaccess;

public class SamePackageAccessTest {
    public void testDefaultAccess() {
        // Can access default class and members (same package)
        DefaultExample example = new DefaultExample();
        DefaultExample customExample = new DefaultExample("Custom");
        
        // Can access default fields and methods
        System.out.println("Accessing default field: " + example.defaultField);
        example.defaultMethod();
        
        // Can modify default field
        example.defaultField = "Modified by same package";
        example.defaultNumber = 30;
        
        // Can create default nested class
        DefaultExample.DefaultNestedClass nested = example.new DefaultNestedClass();
        System.out.println("Nested data: " + nested.nestedData);
        nested.displayInfo();
    }
}

// Class in different package
package com.example.differentpackage;
// import com.example.defaultaccess.DefaultExample;  // Cannot import default class!

public class DifferentPackageTest {
    public void testDefaultAccess() {
        // CANNOT access default class from different package
        // DefaultExample example = new DefaultExample();  // Compilation error!
        
        // Default access is package-private, not accessible from outside package
    }
}
```

## Access Modifier Combinations in Classes

### 1. Comprehensive Class Example
```java
package com.example.comprehensive;

public class ComprehensiveAccessExample {
    // Different access levels for fields
    public String publicData = "Public data";
    protected String protectedData = "Protected data";
    String packageData = "Package data";  // Default
    private String privateData = "Private data";
    
    // Static members with different access levels
    public static String PUBLIC_CONSTANT = "Public constant";
    protected static String PROTECTED_CONSTANT = "Protected constant";
    static String PACKAGE_CONSTANT = "Package constant";
    private static String PRIVATE_CONSTANT = "Private constant";
    
    // Constructors with different access levels
    public ComprehensiveAccessExample() {
        System.out.println("Public constructor");
        initializeData();
    }
    
    protected ComprehensiveAccessExample(String protectedData) {
        this();
        this.protectedData = protectedData;
        System.out.println("Protected constructor");
    }
    
    ComprehensiveAccessExample(String packageData, boolean isPackage) {
        this();
        this.packageData = packageData;
        System.out.println("Package constructor");
    }
    
    private ComprehensiveAccessExample(String privateData, int dummy) {
        this();
        this.privateData = privateData;
        System.out.println("Private constructor");
    }
    
    // Public methods - external interface
    public void publicMethod() {
        System.out.println("Public method - accessible everywhere");
        System.out.println("Public data: " + publicData);
        
        // Can access all internal members
        protectedMethod();
        packageMethod();
        privateMethod();
    }
    
    public String getPrivateData() {
        return privateData;  // Controlled access to private data
    }
    
    public void setPrivateData(String privateData) {
        if (validatePrivateData(privateData)) {  // Private validation
            this.privateData = privateData;
        }
    }
    
    // Protected methods - for subclasses and package
    protected void protectedMethod() {
        System.out.println("Protected method - for subclasses and package");
        System.out.println("Protected data: " + protectedData);
    }
    
    protected boolean isDataValid() {
        return privateData != null && !privateData.trim().isEmpty();
    }
    
    // Package methods - for same package
    void packageMethod() {
        System.out.println("Package method - for same package only");
        System.out.println("Package data: " + packageData);
    }
    
    void updatePackageData(String newData) {
        this.packageData = newData;
        System.out.println("Package data updated");
    }
    
    // Private methods - internal implementation
    private void privateMethod() {
        System.out.println("Private method - internal use only");
        System.out.println("Private data: " + privateData);
    }
    
    private boolean validatePrivateData(String data) {
        return data != null && data.length() >= 3 && data.length() <= 50;
    }
    
    private void initializeData() {
        privateData = "Initialized private data";
        System.out.println("Data initialized");
    }
    
    // Static methods with different access levels
    public static void publicStaticMethod() {
        System.out.println("Public static method");
        System.out.println("Public constant: " + PUBLIC_CONSTANT);
        
        // Can access other static methods
        protectedStaticMethod();
        packageStaticMethod();
        privateStaticMethod();
    }
    
    protected static void protectedStaticMethod() {
        System.out.println("Protected static method");
        System.out.println("Protected constant: " + PROTECTED_CONSTANT);
    }
    
    static void packageStaticMethod() {
        System.out.println("Package static method");
        System.out.println("Package constant: " + PACKAGE_CONSTANT);
    }
    
    private static void privateStaticMethod() {
        System.out.println("Private static method");
        System.out.println("Private constant: " + PRIVATE_CONSTANT);
    }
    
    // Factory method using private constructor
    public static ComprehensiveAccessExample createWithPrivateData(String data) {
        return new ComprehensiveAccessExample(data, 0);  // Using private constructor
    }
    
    // Nested classes with different access levels
    public class PublicNestedClass {
        public String publicNestedData = "Public nested data";
        
        public void accessOuterMembers() {
            // Can access all outer class members (even private!)
            System.out.println("Nested accessing outer private: " + privateData);
            privateMethod();
        }
    }
    
    protected class ProtectedNestedClass {
        protected String protectedNestedData = "Protected nested data";
        
        protected void protectedNestedMethod() {
            System.out.println("Protected nested method");
            System.out.println("Accessing outer protected: " + protectedData);
        }
    }
    
    class PackageNestedClass {
        String packageNestedData = "Package nested data";
        
        void packageNestedMethod() {
            System.out.println("Package nested method");
            System.out.println("Accessing outer package: " + packageData);
        }
    }
    
    private class PrivateNestedClass {
        private String privateNestedData = "Private nested data";
        
        private void privateNestedMethod() {
            System.out.println("Private nested method");
            System.out.println("Accessing outer private: " + privateData);
        }
        
        public void publicMethodInPrivateClass() {
            System.out.println("Public method in private nested class");
            // Still only accessible through outer class
        }
    }
    
    // Method to demonstrate nested class usage
    public void demonstrateNestedClasses() {
        System.out.println("\n=== Nested Classes Demonstration ===");
        
        PublicNestedClass publicNested = new PublicNestedClass();
        System.out.println(publicNested.publicNestedData);
        publicNested.accessOuterMembers();
        
        ProtectedNestedClass protectedNested = new ProtectedNestedClass();
        System.out.println(protectedNested.protectedNestedData);
        protectedNested.protectedNestedMethod();
        
        PackageNestedClass packageNested = new PackageNestedClass();
        System.out.println(packageNested.packageNestedData);
        packageNested.packageNestedMethod();
        
        PrivateNestedClass privateNested = new PrivateNestedClass();
        System.out.println(privateNested.privateNestedData);
        privateNested.privateNestedMethod();
        privateNested.publicMethodInPrivateClass();
    }
}
```

### 2. Subclass Access Demonstration
```java
package com.example.inheritance;
import com.example.comprehensive.ComprehensiveAccessExample;

public class SubclassAccessDemo extends ComprehensiveAccessExample {
    private String subclassData;
    
    public SubclassAccessDemo() {
        super();
        this.subclassData = "Subclass specific data";
        demonstrateInheritedAccess();
    }
    
    public SubclassAccessDemo(String protectedData, String subclassData) {
        super(protectedData);  // Calling protected constructor
        this.subclassData = subclassData;
    }
    
    public void demonstrateInheritedAccess() {
        System.out.println("\n=== Subclass Access Demonstration ===");
        
        // Can access public members
        System.out.println("Public data from parent: " + publicData);
        publicMethod();
        
        // Can access protected members
        System.out.println("Protected data from parent: " + protectedData);
        protectedMethod();
        
        // CANNOT access package members (different package)
        // System.out.println(packageData);  // Compilation error!
        // packageMethod();                  // Compilation error!
        
        // CANNOT access private members
        // System.out.println(privateData);  // Compilation error!
        // privateMethod();                  // Compilation error!
        
        // Can access through public methods
        System.out.println("Private data through public method: " + getPrivateData());
        setPrivateData("New private data set by subclass");
        
        // Can access public static members
        System.out.println("Public static constant: " + PUBLIC_CONSTANT);
        publicStaticMethod();
        
        // Can access protected static members
        System.out.println("Protected static constant: " + PROTECTED_CONSTANT);
        protectedStaticMethod();
        
        // CANNOT access package static members (different package)
        // System.out.println(PACKAGE_CONSTANT);  // Compilation error!
        // packageStaticMethod();                  // Compilation error!
        
        // Can create public nested class
        PublicNestedClass publicNested = new PublicNestedClass();
        System.out.println("Public nested data: " + publicNested.publicNestedData);
        
        // Can create protected nested class
        ProtectedNestedClass protectedNested = new ProtectedNestedClass();
        System.out.println("Protected nested data: " + protectedNested.protectedNestedData);
        
        // CANNOT access package or private nested classes (different package)
        // PackageNestedClass packageNested = new PackageNestedClass();  // Error!
        // PrivateNestedClass privateNested = new PrivateNestedClass();  // Error!
    }
    
    // Override protected method
    @Override
    protected void protectedMethod() {
        System.out.println("Overridden protected method in subclass");
        super.protectedMethod();  // Call parent implementation
        System.out.println("Subclass data: " + subclassData);
        
        // Can access and modify protected data
        protectedData = "Modified by subclass";
    }
    
    // Override with different access modifier (wider access)
    @Override
    public boolean isDataValid() {  // Changed from protected to public
        return super.isDataValid() && subclassData != null;
    }
    
    // Cannot override with narrower access
    // @Override
    // private void publicMethod() { }  // Compilation error!
    
    // Additional subclass-specific methods
    public String getSubclassData() {
        return subclassData;
    }
    
    public void setSubclassData(String subclassData) {
        this.subclassData = subclassData;
    }
}
```

## Real-World Access Control Examples

### 1. Banking System with Proper Access Control
```java
package com.bank.core;

public class BankAccount {
    // Private - critical data protection
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String encryptedPin;
    private boolean isActive;
    private java.time.LocalDateTime lastTransaction;
    
    // Protected - for specialized account types
    protected String accountType;
    protected double interestRate;
    protected int transactionLimit;
    protected java.util.List<String> transactionHistory;
    
    // Package - for bank internal operations
    String branchCode;
    String managerApproval;
    java.time.LocalDate openDate;
    
    // Public - customer interface
    public static final double MINIMUM_BALANCE = 100.0;
    public static final int MAX_DAILY_TRANSACTIONS = 10;
    
    // Public constructor with validation
    public BankAccount(String accountHolderName, String pin) {
        if (!isValidName(accountHolderName) || !isValidPin(pin)) {
            throw new IllegalArgumentException("Invalid account details");
        }
        
        this.accountNumber = generateAccountNumber();
        this.accountHolderName = accountHolderName;
        this.encryptedPin = encryptPin(pin);
        this.balance = 0.0;
        this.isActive = true;
        this.accountType = "SAVINGS";
        this.interestRate = 0.03;
        this.transactionLimit = 5;
        this.transactionHistory = new java.util.ArrayList<>();
        this.openDate = java.time.LocalDate.now();
        this.lastTransaction = java.time.LocalDateTime.now();
        
        logTransaction("Account opened");
    }
    
    // Public methods - customer operations
    public boolean deposit(double amount, String pin) {
        if (!validatePin(pin)) {
            logTransaction("Failed deposit - Invalid PIN");
            return false;
        }
        
        if (!isValidTransactionAmount(amount)) {
            logTransaction("Failed deposit - Invalid amount: " + amount);
            return false;
        }
        
        if (!canPerformTransaction()) {
            logTransaction("Failed deposit - Transaction limit exceeded");
            return false;
        }
        
        balance += amount;
        lastTransaction = java.time.LocalDateTime.now();
        logTransaction("Deposit: +" + amount + " | Balance: " + balance);
        
        return true;
    }
    
    public boolean withdraw(double amount, String pin) {
        if (!validatePin(pin)) {
            logTransaction("Failed withdrawal - Invalid PIN");
            return false;
        }
        
        if (!isValidTransactionAmount(amount)) {
            logTransaction("Failed withdrawal - Invalid amount: " + amount);
            return false;
        }
        
        if (!canPerformTransaction()) {
            logTransaction("Failed withdrawal - Transaction limit exceeded");
            return false;
        }
        
        if (balance - amount < MINIMUM_BALANCE) {
            logTransaction("Failed withdrawal - Insufficient funds");
            return false;
        }
        
        balance -= amount;
        lastTransaction = java.time.LocalDateTime.now();
        logTransaction("Withdrawal: -" + amount + " | Balance: " + balance);
        
        return true;
    }
    
    public double getBalance(String pin) {
        if (validatePin(pin)) {
            logTransaction("Balance inquiry");
            return balance;
        }
        logTransaction("Failed balance inquiry - Invalid PIN");
        return -1; // Error indicator
    }
    
    public boolean changePin(String oldPin, String newPin) {
        if (!validatePin(oldPin)) {
            logTransaction("Failed PIN change - Invalid old PIN");
            return false;
        }
        
        if (!isValidPin(newPin)) {
            logTransaction("Failed PIN change - Invalid new PIN format");
            return false;
        }
        
        encryptedPin = encryptPin(newPin);
        logTransaction("PIN changed successfully");
        return true;
    }
    
    public String getAccountNumber() {
        return accountNumber.substring(0, 4) + "****" + 
               accountNumber.substring(accountNumber.length() - 4);
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    // Protected methods - for specialized accounts
    protected void setInterestRate(double rate) {
        if (rate >= 0.0 && rate <= 0.15) {  // 0% to 15%
            this.interestRate = rate;
            logTransaction("Interest rate updated to " + (rate * 100) + "%");
        }
    }
    
    protected void setTransactionLimit(int limit) {
        if (limit > 0 && limit <= 20) {
            this.transactionLimit = limit;
            logTransaction("Transaction limit updated to " + limit);
        }
    }
    
    protected boolean performInternalTransfer(double amount, String reason) {
        balance += amount;  // Can be positive or negative
        logTransaction("Internal transfer: " + amount + " - " + reason);
        return true;
    }
    
    protected java.util.List<String> getTransactionHistory() {
        return new java.util.ArrayList<>(transactionHistory); // Defensive copy
    }
    
    // Package methods - for bank operations
    void setManagerApproval(String managerApproval) {
        this.managerApproval = managerApproval;
        logTransaction("Manager approval: " + managerApproval);
    }
    
    void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    void freezeAccount() {
        isActive = false;
        logTransaction("Account frozen by bank");
    }
    
    void unfreezeAccount() {
        isActive = true;
        logTransaction("Account unfrozen by bank");
    }
    
    double getActualBalance() {
        return balance;  // Unmasked balance for bank operations
    }
    
    // Private methods - internal implementation
    private boolean validatePin(String pin) {
        return encryptedPin.equals(encryptPin(pin));
    }
    
    private String encryptPin(String pin) {
        // Simplified encryption (in real app, use proper encryption)
        return "ENC_" + pin.hashCode() + "_" + pin.length();
    }
    
    private boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}"); // 4 digits
    }
    
    private boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2 && 
               name.matches("[a-zA-Z ]+");
    }
    
    private boolean isValidTransactionAmount(double amount) {
        return amount > 0 && amount <= 50000; // Max $50,000 per transaction
    }
    
    private boolean canPerformTransaction() {
        if (!isActive) return false;
        
        java.time.LocalDate today = java.time.LocalDate.now();
        long todayTransactions = transactionHistory.stream()
            .filter(t -> t.contains(today.toString()))
            .count();
        
        return todayTransactions < transactionLimit;
    }
    
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() + 
               (int)(Math.random() * 1000);
    }
    
    private void logTransaction(String transaction) {
        String timestamp = java.time.LocalDateTime.now().toString();
        transactionHistory.add(timestamp + " - " + transaction);
        
        // Keep only last 100 transactions
        if (transactionHistory.size() > 100) {
            transactionHistory.remove(0);
        }
    }
}

// Specialized account extending BankAccount
package com.bank.accounts;
import com.bank.core.BankAccount;

public class PremiumAccount extends BankAccount {
    private String relationshipManager;
    private double creditLimit;
    private boolean hasOverdraftProtection;
    
    public PremiumAccount(String accountHolderName, String pin, String relationshipManager) {
        super(accountHolderName, pin);
        this.relationshipManager = relationshipManager;
        this.creditLimit = 10000.0;
        this.hasOverdraftProtection = true;
        
        // Access protected members
        setInterestRate(0.05);     // Higher interest rate
        setTransactionLimit(15);   // More transactions allowed
        accountType = "PREMIUM";   // Can access protected field
    }
    
    // Enhanced withdrawal with overdraft protection
    @Override
    public boolean withdraw(double amount, String pin) {
        // Try normal withdrawal first
        if (super.withdraw(amount, pin)) {
            return true;
        }
        
        // If failed and overdraft protection is enabled
        if (hasOverdraftProtection) {
            double currentBalance = getBalance(pin);
            double shortfall = amount - currentBalance;
            
            if (shortfall <= creditLimit) {
                // Use internal transfer (protected method)
                performInternalTransfer(-amount, "Overdraft withdrawal");
                creditLimit -= shortfall;
                System.out.println("Overdraft used: $" + shortfall);
                return true;
            }
        }
        
        return false;
    }
    
    public void assignRelationshipManager(String manager) {
        this.relationshipManager = manager;
        // Can access protected method
        performInternalTransfer(0, "Relationship manager assigned: " + manager);
    }
    
    // Premium-specific features
    public double getCreditLimit() {
        return creditLimit;
    }
    
    public void increaseCreditLimit(double increase) {
        if (increase > 0) {
            creditLimit += increase;
            performInternalTransfer(0, "Credit limit increased by " + increase);
        }
    }
    
    public java.util.List<String> getDetailedTransactionHistory() {
        return getTransactionHistory(); // Access protected method
    }
}

// Bank manager operations (same package)
package com.bank.core;

public class BankManager {
    private String managerId;
    private String name;
    
    public BankManager(String managerId, String name) {
        this.managerId = managerId;
        this.name = name;
    }
    
    // Can access package methods of BankAccount
    public void approveAccount(BankAccount account, String reason) {
        account.setManagerApproval(managerId + ": " + reason);
        account.setBranchCode("BR001");
    }
    
    public void freezeAccount(BankAccount account, String reason) {
        System.out.println("Manager " + name + " freezing account for: " + reason);
        account.freezeAccount();
    }
    
    public void unfreezeAccount(BankAccount account, String reason) {
        System.out.println("Manager " + name + " unfreezing account for: " + reason);
        account.unfreezeAccount();
    }
    
    public double getAccountBalance(BankAccount account) {
        // Can access package method for actual balance
        return account.getActualBalance();
    }
    
    public void generateAccountReport(BankAccount account) {
        System.out.println("=== ACCOUNT REPORT ===");
        System.out.println("Manager: " + name);
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Holder: " + account.getAccountHolderName());
        System.out.println("Type: " + account.accountType); // Package field
        System.out.println("Branch: " + account.branchCode); // Package field
        System.out.println("Balance: $" + account.getActualBalance()); // Package method
        System.out.println("Status: " + (account.isActive() ? "Active" : "Frozen"));
        System.out.println("Open Date: " + account.openDate); // Package field
        
        // Cannot access private members
        // System.out.println(account.encryptedPin);  // Compilation error!
    }
}
```

### 2. Complete Access Control Demonstration
```java
public class AccessModifierDemo {
    public static void main(String[] args) {
        System.out.println("=== ACCESS MODIFIER DEMONSTRATION ===");
        
        demonstrateBankingSystem();
        demonstrateClassAccess();
        demonstrateInheritanceAccess();
    }
    
    public static void demonstrateBankingSystem() {
        System.out.println("\n--- BANKING SYSTEM ACCESS CONTROL ---");
        
        // Create accounts
        BankAccount regularAccount = new BankAccount("John Doe", "1234");
        PremiumAccount premiumAccount = new PremiumAccount("Jane Smith", "5678", "Alice Manager");
        
        // Customer operations (public interface)
        System.out.println("\n=== Customer Operations ===");
        regularAccount.deposit(1000, "1234");
        regularAccount.withdraw(500, "1234");
        System.out.println("Balance: $" + regularAccount.getBalance("1234"));
        
        premiumAccount.deposit(5000, "5678");
        premiumAccount.withdraw(6000, "5678"); // Will use overdraft
        
        // Bank manager operations (package access)
        BankManager manager = new BankManager("MGR001", "Bob Manager");
        manager.approveAccount(regularAccount, "Account verified");
        manager.generateAccountReport(regularAccount);
        
        // Specialized account operations (protected access)
        premiumAccount.assignRelationshipManager("Carol Senior Manager");
        premiumAccount.increaseCreditLimit(5000);
        
        // Cannot access private members
        // System.out.println(regularAccount.encryptedPin);  // Compilation error!
        // regularAccount.logTransaction("test");            // Compilation error!
    }
    
    public static void demonstrateClassAccess() {
        System.out.println("\n--- CLASS ACCESS LEVELS ---");
        
        ComprehensiveAccessExample example = new ComprehensiveAccessExample();
        
        // Public access - always available
        System.out.println("Public data: " + example.publicData);
        example.publicMethod();
        
        // Static public access
        System.out.println("Public constant: " + ComprehensiveAccessExample.PUBLIC_CONSTANT);
        ComprehensiveAccessExample.publicStaticMethod();
        
        // Factory method using private constructor
        ComprehensiveAccessExample special = 
            ComprehensiveAccessExample.createWithPrivateData("Special private data");
        
        // Demonstrate nested classes
        example.demonstrateNestedClasses();
        
        // Create public nested class
        ComprehensiveAccessExample.PublicNestedClass nested = example.new PublicNestedClass();
        nested.accessOuterMembers();
    }
    
    public static void demonstrateInheritanceAccess() {
        System.out.println("\n--- INHERITANCE ACCESS CONTROL ---");
        
        SubclassAccessDemo subclass = new SubclassAccessDemo();
        
        // Subclass accessing inherited members
        subclass.demonstrateInheritedAccess();
        
        // Test overridden methods
        subclass.protectedMethod(); // Calls overridden version
        System.out.println("Data valid: " + subclass.isDataValid());
        
        // Access through inheritance
        System.out.println("Subclass data: " + subclass.getSubclassData());
    }
}
```

## Interview Questions & Answers

**Q1: What are access modifiers in Java? List all of them.**
**A:** Java has 4 access modifiers:
- **public**: Accessible everywhere
- **protected**: Accessible within package and by subclasses
- **default (package-private)**: Accessible within the same package only
- **private**: Accessible only within the same class

**Q2: What's the difference between protected and default access?**
**A:** 
- **Default**: Accessible only within the same package
- **Protected**: Accessible within the same package AND by subclasses in other packages

**Q3: Can you reduce access modifier visibility in method overriding?**
**A:** No, you cannot reduce visibility. You can only maintain or increase it. For example, if parent has protected method, child can make it protected or public, but not private or default.

**Q4: What access modifier should you use for class fields?**
**A:** Generally, fields should be **private** to ensure encapsulation and data protection. Use public only for constants, protected for inheritance-specific data, and default rarely.

**Q5: Can a class have private constructor? What's the use case?**
**A:** Yes, private constructors prevent external instantiation. Use cases: Singleton pattern, utility classes, factory methods, preventing instantiation of utility classes.

**Q6: What happens if you don't specify an access modifier?**
**A:** It becomes **default (package-private)** access, meaning it's accessible only within the same package.

**Q7: Can interface methods have access modifiers?**
**A:** Interface methods are **public** by default. You can explicitly declare them public. Default and static methods (Java 8+) can also be in interfaces.

**Q8: How do access modifiers affect nested classes?**
**A:** Nested classes can have any access modifier. Private nested classes are only accessible within the outer class. Inner classes can access all members of outer class, including private ones.

## Key Takeaways

1. **Private** = Strongest encapsulation, class-level access only
2. **Protected** = Inheritance-friendly, package + subclass access
3. **Default/Package** = Package-level organization and access
4. **Public** = Open interface, accessible everywhere
5. **Constructor access** controls object creation patterns
6. **Method access** defines the public API vs internal implementation
7. **Field access** should be private for proper encapsulation
8. **Static member access** follows same rules as instance members
9. **Nested class access** provides fine-grained visibility control
10. **Inheritance access** enables controlled extension and specialization

---

*Remember: Access modifiers are like security levels in a building - each level provides appropriate access while protecting sensitive areas from unauthorized entry!*