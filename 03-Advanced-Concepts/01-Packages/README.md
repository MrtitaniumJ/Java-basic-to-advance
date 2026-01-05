# Java Packages and Import Statements

## Simple Explanation

Think of **Java Packages** as **organizing your house**:

- **Package** = Different rooms in your house (kitchen, bedroom, bathroom)
- **Classes** = Items in each room (kitchen: stove, fridge; bedroom: bed, wardrobe)
- **Import statement** = Finding items from other rooms ("Go to kitchen and get the coffee maker")
- **Package hierarchy** = Multi-story house with rooms on different floors
- **Default package** = Items scattered in the hallway (messy, not organized)

### Real-World Analogies
- **Library system** = Packages (sections: Fiction, Science, History; books are classes)
- **Office building** = Packages (floors and departments; employees are classes)
- **Shopping mall** = Packages (stores categorized by type; products are classes)
- **File system** = Packages (folders and subfolders; files are classes)

## Professional Definition

**Java Packages** are organizational units that group related classes and interfaces together, providing namespace management, access control, and logical code organization. They prevent naming conflicts, enable encapsulation at the package level, and facilitate code reusability and maintainability through hierarchical structuring and controlled visibility.

## Why Packages are Essential

### Problems Without Packages:
```java
// WITHOUT PACKAGES (All classes in default package)
// This demonstrates the chaos without proper organization

// File: Customer.java (Banking domain)
public class Customer {
    private String name;
    private String accountNumber;
    
    public Customer(String name, String accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
    }
    
    public String getName() { return name; }
    public String getAccountNumber() { return accountNumber; }
}

// File: Customer.java (E-commerce domain) - PROBLEM: NAMING CONFLICT!
// Cannot have two Customer classes in the same package!
/*
public class Customer {
    private String name;
    private String email;
    private String shippingAddress;
    
    // This would cause compilation error!
}
*/

// File: Order.java
public class Order {
    // PROBLEM: Which Customer class does this refer to?
    private Customer customer; // Ambiguous reference!
    private String orderId;
    
    // PROBLEM: No organization - hard to find related classes
    // All classes mixed together in one namespace
}

// File: DatabaseConnection.java
// PROBLEM: Utility classes mixed with domain classes
public class DatabaseConnection {
    // Database utility code
}

// File: EmailService.java  
// PROBLEM: Service classes mixed with everything else
public class EmailService {
    // Email service code
}

class ProblematicCodeOrganization {
    public void demonstrateProblems() {
        System.out.println("=== PROBLEMS WITHOUT PACKAGES ===");
        
        // PROBLEM 1: Naming conflicts
        System.out.println("❌ Cannot have multiple classes with same name");
        System.out.println("❌ Banking Customer vs E-commerce Customer conflict");
        
        // PROBLEM 2: No logical grouping
        System.out.println("❌ All classes mixed together");
        System.out.println("❌ Hard to find related functionality");
        
        // PROBLEM 3: No access control
        System.out.println("❌ All classes have same visibility");
        System.out.println("❌ Cannot hide internal implementation");
        
        // PROBLEM 4: Poor maintainability
        System.out.println("❌ Difficult to maintain large codebases");
        System.out.println("❌ No clear separation of concerns");
        
        // PROBLEM 5: Team collaboration issues
        System.out.println("❌ Multiple developers working on same namespace");
        System.out.println("❌ Merge conflicts and confusion");
    }
}
```

### With Proper Package Organization - Clean and Scalable:
```java
// WITH PACKAGES - Proper organization and structure

// File: src/main/java/com/ecommerce/model/Customer.java
package com.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * E-commerce Customer entity representing online shoppers
 */
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String shippingAddress;
    private LocalDate registrationDate;
    private List<String> orderHistory;
    
    public Customer(String customerId, String name, String email, String shippingAddress) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.registrationDate = LocalDate.now();
        this.orderHistory = new ArrayList<>();
    }
    
    // Getters and setters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getShippingAddress() { return shippingAddress; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<String> getOrderHistory() { return orderHistory; }
    
    public void addOrder(String orderId) {
        orderHistory.add(orderId);
    }
    
    @Override
    public String toString() {
        return String.format("EcommerceCustomer{id='%s', name='%s', email='%s'}", 
                           customerId, name, email);
    }
}

// File: src/main/java/com/banking/model/Customer.java
package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * Banking Customer entity representing bank account holders
 */
public class Customer {
    private String customerId;
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDate accountOpenDate;
    private List<String> transactionHistory;
    
    public Customer(String customerId, String name, String accountNumber) {
        this.customerId = customerId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        this.accountOpenDate = LocalDate.now();
        this.transactionHistory = new ArrayList<>();
    }
    
    // Banking-specific methods
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        transactionHistory.add("DEPOSIT: " + amount);
    }
    
    public boolean withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            transactionHistory.add("WITHDRAWAL: " + amount);
            return true;
        }
        return false;
    }
    
    // Getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public LocalDate getAccountOpenDate() { return accountOpenDate; }
    public List<String> getTransactionHistory() { return transactionHistory; }
    
    @Override
    public String toString() {
        return String.format("BankCustomer{id='%s', name='%s', account='%s', balance=%s}", 
                           customerId, name, accountNumber, balance);
    }
}

// File: src/main/java/com/ecommerce/service/OrderService.java
package com.ecommerce.service;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.util.EmailService;
import com.ecommerce.util.DatabaseConnection;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Service class for managing e-commerce orders
 */
public class OrderService {
    private List<Order> orders;
    private EmailService emailService;
    private DatabaseConnection dbConnection;
    
    public OrderService() {
        this.orders = new ArrayList<>();
        this.emailService = new EmailService();
        this.dbConnection = new DatabaseConnection();
    }
    
    public Order createOrder(Customer customer, List<String> productIds, double totalAmount) {
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        
        Order order = new Order(orderId, customer.getCustomerId(), productIds, totalAmount);
        orders.add(order);
        
        // Update customer's order history
        customer.addOrder(orderId);
        
        // Send confirmation email
        emailService.sendOrderConfirmation(customer.getEmail(), order);
        
        // Save to database
        dbConnection.saveOrder(order);
        
        System.out.printf("✅ Order created: %s for customer %s%n", orderId, customer.getName());
        return order;
    }
    
    public List<Order> getOrdersByCustomer(String customerId) {
        return orders.stream()
                    .filter(order -> order.getCustomerId().equals(customerId))
                    .toList();
    }
    
    public Order findOrderById(String orderId) {
        return orders.stream()
                    .filter(order -> order.getOrderId().equals(orderId))
                    .findFirst()
                    .orElse(null);
    }
}

// File: src/main/java/com/ecommerce/model/Order.java
package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<String> productIds;
    private double totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;
    
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }
    
    public Order(String orderId, String customerId, List<String> productIds, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productIds = productIds;
        this.totalAmount = totalAmount;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
    
    // Getters and setters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<String> getProductIds() { return productIds; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }
    
    public void setStatus(OrderStatus status) { this.status = status; }
    
    @Override
    public String toString() {
        return String.format("Order{id='%s', customer='%s', amount=%.2f, status=%s}", 
                           orderId, customerId, totalAmount, status);
    }
}
```

## Package Organization Best Practices

### 1. Hierarchical Package Structure
```java
// File: src/main/java/com/company/ecommerce/EcommerceApplication.java
package com.company.ecommerce;

// Demonstrate proper package hierarchy
import com.company.ecommerce.model.*;
import com.company.ecommerce.service.*;
import com.company.ecommerce.util.*;
import com.company.ecommerce.controller.*;

import java.util.Arrays;

/**
 * Main application demonstrating package organization
 */
public class EcommerceApplication {
    
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE APPLICATION WITH PACKAGES ===");
        
        // Demonstrate organized code structure
        demonstratePackageOrganization();
        runEcommerceScenario();
    }
    
    private static void demonstratePackageOrganization() {
        System.out.println("\n--- Package Organization Structure ---");
        
        System.out.println("📦 com.company.ecommerce");
        System.out.println("  ├── 📁 model/          (Data entities)");
        System.out.println("  │   ├── Customer.java");
        System.out.println("  │   ├── Order.java");
        System.out.println("  │   └── Product.java");
        System.out.println("  ├── 📁 service/        (Business logic)");
        System.out.println("  │   ├── CustomerService.java");
        System.out.println("  │   ├── OrderService.java");
        System.out.println("  │   └── ProductService.java");
        System.out.println("  ├── 📁 controller/     (API endpoints)");
        System.out.println("  │   ├── CustomerController.java");
        System.out.println("  │   └── OrderController.java");
        System.out.println("  ├── 📁 util/          (Utilities)");
        System.out.println("  │   ├── DatabaseConnection.java");
        System.out.println("  │   ├── EmailService.java");
        System.out.println("  │   └── ValidationUtils.java");
        System.out.println("  └── 📁 config/        (Configuration)");
        System.out.println("      ├── DatabaseConfig.java");
        System.out.println("      └── EmailConfig.java");
        
        System.out.println("\n✅ Benefits of this organization:");
        System.out.println("  • Clear separation of concerns");
        System.out.println("  • Easy to locate related functionality");
        System.out.println("  • Scalable for team development");
        System.out.println("  • Logical code grouping");
        System.out.println("  • Controlled access between layers");
    }
    
    private static void runEcommerceScenario() {
        System.out.println("\n--- E-commerce Application Demo ---");
        
        // Using classes from different packages
        Customer customer = new Customer("CUST001", "John Doe", "john@email.com", "123 Main St");
        OrderService orderService = new OrderService();
        
        // Create an order
        Order order = orderService.createOrder(
            customer, 
            Arrays.asList("PROD001", "PROD002"), 
            299.99
        );
        
        System.out.println("Customer: " + customer);
        System.out.println("Order: " + order);
        
        // Demonstrate package benefits
        System.out.println("\n🎯 Package Benefits Demonstrated:");
        System.out.println("  ✅ No naming conflicts (Customer class exists in both ecommerce and banking)");
        System.out.println("  ✅ Clear import statements show dependencies");
        System.out.println("  ✅ Organized code structure");
        System.out.println("  ✅ Proper separation of concerns");
    }
}

// File: src/main/java/com/company/ecommerce/util/EmailService.java
package com.company.ecommerce.util;

import com.company.ecommerce.model.Order;

/**
 * Email service utility for sending notifications
 */
public class EmailService {
    
    public void sendOrderConfirmation(String email, Order order) {
        System.out.printf("📧 Sending order confirmation to %s for order %s%n", 
                         email, order.getOrderId());
        
        // Simulate email sending
        simulateEmailDelay();
        
        System.out.println("✅ Order confirmation email sent successfully");
    }
    
    public void sendShippingNotification(String email, String trackingNumber) {
        System.out.printf("📦 Sending shipping notification to %s (Tracking: %s)%n", 
                         email, trackingNumber);
        simulateEmailDelay();
        System.out.println("✅ Shipping notification sent successfully");
    }
    
    private void simulateEmailDelay() {
        try {
            Thread.sleep(100); // Simulate network delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// File: src/main/java/com/company/ecommerce/util/DatabaseConnection.java
package com.company.ecommerce.util;

import com.company.ecommerce.model.Order;
import com.company.ecommerce.model.Customer;

/**
 * Database connection utility for data persistence
 */
public class DatabaseConnection {
    
    public void saveOrder(Order order) {
        System.out.printf("💾 Saving order %s to database%n", order.getOrderId());
        
        // Simulate database operation
        simulateDatabaseOperation();
        
        System.out.println("✅ Order saved to database successfully");
    }
    
    public void saveCustomer(Customer customer) {
        System.out.printf("💾 Saving customer %s to database%n", customer.getCustomerId());
        simulateDatabaseOperation();
        System.out.println("✅ Customer saved to database successfully");
    }
    
    private void simulateDatabaseOperation() {
        try {
            Thread.sleep(50); // Simulate database latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### 2. Import Statement Types and Usage
```java
// File: src/main/java/com/company/examples/ImportExamples.java
package com.company.examples;

// DIFFERENT TYPES OF IMPORT STATEMENTS

// 1. Single class import (recommended)
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

// 2. Package import (use sparingly)
import java.util.*;  // Imports all classes from java.util

// 3. Static imports for methods and constants
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.System.out;

// 4. Importing from custom packages
import com.company.ecommerce.model.Customer;
import com.company.ecommerce.model.Order;

/**
 * Comprehensive demonstration of import statements and their usage
 */
public class ImportExamples {
    
    public void demonstrateImportTypes() {
        System.out.println("=== IMPORT STATEMENT EXAMPLES ===");
        
        // Using imported classes
        demonstrateSpecificImports();
        demonstrateWildcardImports();
        demonstrateStaticImports();
        demonstrateCustomPackageImports();
        demonstrateImportConflicts();
    }
    
    private void demonstrateSpecificImports() {
        System.out.println("\n--- Specific Class Imports ---");
        
        // Using ArrayList (imported specifically)
        ArrayList<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        
        // Using HashMap (imported specifically)
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 5);
        map.put("banana", 3);
        
        // Using LocalDate (imported specifically)
        LocalDate today = LocalDate.now();
        
        System.out.println("✅ Specific imports used:");
        System.out.printf("  ArrayList: %s%n", list);
        System.out.printf("  HashMap: %s%n", map);
        System.out.printf("  LocalDate: %s%n", today);
        
        System.out.println("📝 Benefits:");
        System.out.println("  • Clear dependencies visible at top");
        System.out.println("  • IDE can optimize imports automatically");
        System.out.println("  • No performance impact");
        System.out.println("  • Explicit about what classes are used");
    }
    
    private void demonstrateWildcardImports() {
        System.out.println("\n--- Wildcard Imports (import *) ---");
        
        // These classes are available due to import java.util.*;
        List<String> list = new LinkedList<>(); // LinkedList available via wildcard
        Set<Integer> set = new HashSet<>();     // HashSet available via wildcard
        Map<String, String> map = new TreeMap<>(); // TreeMap available via wildcard
        
        list.add("Wildcard import example");
        set.add(42);
        map.put("key", "value");
        
        System.out.println("✅ Wildcard import usage:");
        System.out.printf("  LinkedList: %s%n", list);
        System.out.printf("  HashSet: %s%n", set);
        System.out.printf("  TreeMap: %s%n", map);
        
        System.out.println("⚠️ Wildcard import considerations:");
        System.out.println("  • Can cause naming conflicts");
        System.out.println("  • Makes dependencies less clear");
        System.out.println("  • Use only for packages you trust completely");
        System.out.println("  • Good for: java.util.*, java.io.*");
        System.out.println("  • Avoid for: custom packages, third-party libraries");
    }
    
    private void demonstrateStaticImports() {
        System.out.println("\n--- Static Imports ---");
        
        // Using static imports (imported with 'import static')
        double radius = 5.0;
        double area = PI * radius * radius; // PI imported statically from Math
        double diagonal = sqrt(2) * radius;  // sqrt imported statically from Math
        
        // Using static import for System.out
        out.println("✅ Static imports used:");
        out.printf("  Circle area (radius %.1f): %.2f%n", radius, area);
        out.printf("  Square diagonal: %.2f%n", diagonal);
        
        System.out.println("📝 Static import guidelines:");
        System.out.println("  • Use for frequently used constants");
        System.out.println("  • Use for utility methods you call often");
        System.out.println("  • Avoid overusing - can make code unclear");
        System.out.println("  • Good for: Math.PI, Math.sqrt, System.out");
    }
    
    private void demonstrateCustomPackageImports() {
        System.out.println("\n--- Custom Package Imports ---");
        
        // Using classes from our custom packages
        Customer customer = new Customer("CUST123", "Alice Smith", "alice@email.com", "456 Oak Ave");
        
        System.out.println("✅ Custom package imports:");
        System.out.printf("  Customer from ecommerce.model: %s%n", customer);
        
        System.out.println("📝 Custom package benefits:");
        System.out.println("  • Organize your own code logically");
        System.out.println("  • Enable code reuse across projects");
        System.out.println("  • Create clear module boundaries");
        System.out.println("  • Support team development");
    }
    
    private void demonstrateImportConflicts() {
        System.out.println("\n--- Handling Import Conflicts ---");
        
        // When you have classes with same name from different packages
        // Use fully qualified names to resolve conflicts
        
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        
        System.out.println("✅ Conflict resolution:");
        System.out.printf("  java.util.Date: %s%n", utilDate);
        System.out.printf("  java.sql.Date: %s%n", sqlDate);
        
        System.out.println("📝 Conflict resolution strategies:");
        System.out.println("  1. Use fully qualified names");
        System.out.println("  2. Import one, qualify the other");
        System.out.println("  3. Use aliases (not supported in Java)");
        System.out.println("  4. Refactor to avoid conflicts");
        
        // Example of importing one and qualifying the other
        demonstratePartialQualification();
    }
    
    private void demonstratePartialQualification() {
        System.out.println("\n--- Partial Qualification Strategy ---");
        
        // Import the one you use most frequently
        LocalDate localDate = LocalDate.now(); // Imported at top
        
        // Fully qualify the one used less frequently
        java.util.Date legacyDate = new java.util.Date();
        
        System.out.println("✅ Partial qualification:");
        System.out.printf("  LocalDate (imported): %s%n", localDate);
        System.out.printf("  java.util.Date (qualified): %s%n", legacyDate);
    }
}

// Demonstrate import priority and scoping
class ImportScopingDemo {
    
    public void demonstrateImportScoping() {
        System.out.println("=== IMPORT SCOPING AND PRIORITY ===");
        
        explainImportPriority();
        demonstratePackagePrivateAccess();
    }
    
    private void explainImportPriority() {
        System.out.println("\n--- Import Resolution Priority ---");
        
        System.out.println("🔍 Java import resolution order:");
        System.out.println("  1. Classes in current package (no import needed)");
        System.out.println("  2. Classes from java.lang.* (automatic import)");
        System.out.println("  3. Specific imports (import com.example.Class)");
        System.out.println("  4. Wildcard imports (import com.example.*)");
        System.out.println("  5. Fully qualified names (com.example.Class)");
        
        // Examples
        String text = "Hello"; // java.lang.String (automatic)
        // ArrayList<String> list; // Would need import for ArrayList
        
        System.out.println("✅ String class available without import (java.lang.*)");
    }
    
    private void demonstratePackagePrivateAccess() {
        System.out.println("\n--- Package-Private Access ---");
        
        System.out.println("📝 Package access control:");
        System.out.println("  • No modifier = package-private");
        System.out.println("  • Accessible within same package only");
        System.out.println("  • Cannot import package-private from other packages");
        System.out.println("  • Useful for internal implementation classes");
    }
}
```

## Real-World Package Structure Examples

### 1. Enterprise Application Structure
```java
// Complete enterprise application package structure demonstration

// File: src/main/java/com/enterprise/banking/BankingApplication.java
package com.enterprise.banking;

import com.enterprise.banking.model.*;
import com.enterprise.banking.service.*;
import com.enterprise.banking.repository.*;
import com.enterprise.banking.controller.*;
import com.enterprise.banking.config.*;
import com.enterprise.banking.util.*;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Main banking application demonstrating enterprise package structure
 */
public class BankingApplication {
    
    public static void main(String[] args) {
        System.out.println("=== ENTERPRISE BANKING APPLICATION ===");
        
        demonstrateEnterpriseStructure();
        runBankingOperations();
    }
    
    private static void demonstrateEnterpriseStructure() {
        System.out.println("\n--- Enterprise Package Structure ---");
        
        System.out.println("🏢 com.enterprise.banking");
        System.out.println("  ├── 📁 model/              (Domain entities)");
        System.out.println("  │   ├── Account.java");
        System.out.println("  │   ├── Customer.java");
        System.out.println("  │   ├── Transaction.java");
        System.out.println("  │   └── BankBranch.java");
        System.out.println("  ├── 📁 service/            (Business logic)");
        System.out.println("  │   ├── AccountService.java");
        System.out.println("  │   ├── CustomerService.java");
        System.out.println("  │   ├── TransactionService.java");
        System.out.println("  │   └── NotificationService.java");
        System.out.println("  ├── 📁 repository/         (Data access)");
        System.out.println("  │   ├── AccountRepository.java");
        System.out.println("  │   ├── CustomerRepository.java");
        System.out.println("  │   └── TransactionRepository.java");
        System.out.println("  ├── 📁 controller/         (API endpoints)");
        System.out.println("  │   ├── AccountController.java");
        System.out.println("  │   ├── CustomerController.java");
        System.out.println("  │   └── TransactionController.java");
        System.out.println("  ├── 📁 config/             (Configuration)");
        System.out.println("  │   ├── DatabaseConfig.java");
        System.out.println("  │   ├── SecurityConfig.java");
        System.out.println("  │   └── ApplicationConfig.java");
        System.out.println("  ├── 📁 util/               (Utilities)");
        System.out.println("  │   ├── ValidationUtils.java");
        System.out.println("  │   ├── CurrencyUtils.java");
        System.out.println("  │   └── DateTimeUtils.java");
        System.out.println("  ├── 📁 exception/          (Custom exceptions)");
        System.out.println("  │   ├── InsufficientFundsException.java");
        System.out.println("  │   ├── AccountNotFoundException.java");
        System.out.println("  │   └── InvalidTransactionException.java");
        System.out.println("  └── 📁 dto/                (Data transfer objects)");
        System.out.println("      ├── AccountDTO.java");
        System.out.println("      ├── CustomerDTO.java");
        System.out.println("      └── TransactionDTO.java");
    }
    
    private static void runBankingOperations() {
        System.out.println("\n--- Banking Operations Demo ---");
        
        // Initialize services
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();
        
        // Create customer
        Customer customer = customerService.createCustomer(
            "John Banking", "john.bank@email.com", "555-0123"
        );
        
        // Create account
        Account account = accountService.createAccount(customer.getCustomerId(), BigDecimal.valueOf(1000));
        
        // Perform transactions
        transactionService.deposit(account.getAccountNumber(), BigDecimal.valueOf(500));
        transactionService.withdraw(account.getAccountNumber(), BigDecimal.valueOf(200));
        
        // Display results
        System.out.printf("Customer: %s%n", customer.getName());
        System.out.printf("Account: %s%n", account.getAccountNumber());
        System.out.printf("Final Balance: $%s%n", account.getBalance());
    }
}

// File: src/main/java/com/enterprise/banking/model/Account.java
package com.enterprise.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    private String accountNumber;
    private String customerId;
    private BigDecimal balance;
    private AccountType accountType;
    private LocalDateTime createdDate;
    
    public enum AccountType {
        SAVINGS, CHECKING, BUSINESS
    }
    
    public Account(String accountNumber, String customerId, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    
    @Override
    public String toString() {
        return String.format("Account{number='%s', type=%s, balance=$%s}", 
                           accountNumber, accountType, balance);
    }
}

// File: src/main/java/com/enterprise/banking/service/CustomerService.java
package com.enterprise.banking.service;

import com.enterprise.banking.model.Customer;
import com.enterprise.banking.repository.CustomerRepository;
import com.enterprise.banking.util.ValidationUtils;

import java.util.UUID;

public class CustomerService {
    private CustomerRepository customerRepository;
    
    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }
    
    public Customer createCustomer(String name, String email, String phone) {
        // Validate input
        ValidationUtils.validateNotEmpty(name, "Customer name");
        ValidationUtils.validateEmail(email);
        ValidationUtils.validatePhone(phone);
        
        // Generate customer ID
        String customerId = "CUST-" + UUID.randomUUID().toString().substring(0, 8);
        
        // Create customer
        Customer customer = new Customer(customerId, name, email, phone);
        
        // Save to repository
        customerRepository.save(customer);
        
        System.out.printf("✅ Customer created: %s%n", customer);
        return customer;
    }
    
    public Customer findById(String customerId) {
        return customerRepository.findById(customerId);
    }
}

// File: src/main/java/com/enterprise/banking/service/AccountService.java
package com.enterprise.banking.service;

import com.enterprise.banking.model.Account;
import com.enterprise.banking.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {
    private AccountRepository accountRepository;
    
    public AccountService() {
        this.accountRepository = new AccountRepository();
    }
    
    public Account createAccount(String customerId, BigDecimal initialDeposit) {
        String accountNumber = "ACC-" + UUID.randomUUID().toString().substring(0, 10);
        
        Account account = new Account(accountNumber, customerId, Account.AccountType.CHECKING);
        account.setBalance(initialDeposit);
        
        accountRepository.save(account);
        
        System.out.printf("✅ Account created: %s with initial deposit: $%s%n", 
                         accountNumber, initialDeposit);
        return account;
    }
    
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
```

### 2. Multi-Module Project Structure
```java
// Demonstrating how packages work across multiple modules/projects

// File: common-utils/src/main/java/com/company/common/util/StringUtils.java
package com.company.common.util;

/**
 * Common string utilities used across multiple projects
 */
public class StringUtils {
    
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static String capitalize(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    public static String maskEmail(String email) {
        if (isNullOrEmpty(email) || !email.contains("@")) {
            return email;
        }
        
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];
        
        if (username.length() <= 2) {
            return "*".repeat(username.length()) + "@" + domain;
        }
        
        return username.charAt(0) + "*".repeat(username.length() - 2) + 
               username.charAt(username.length() - 1) + "@" + domain;
    }
    
    public static String generateId(String prefix, int length) {
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        return prefix + "-" + uuid.substring(0, Math.min(length, uuid.length()));
    }
}

// File: common-utils/src/main/java/com/company/common/validation/Validator.java
package com.company.common.validation;

import com.company.common.util.StringUtils;
import java.util.regex.Pattern;

public class Validator {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    
    public static boolean isValidEmail(String email) {
        return !StringUtils.isNullOrEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        if (StringUtils.isNullOrEmpty(phone)) return false;
        String cleanPhone = phone.replaceAll("[\\s()-]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }
    
    public static void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }
    
    public static void validatePhone(String phone) {
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone format: " + phone);
        }
    }
}

// File: ecommerce-app/src/main/java/com/company/ecommerce/EcommerceMain.java
package com.company.ecommerce;

// Importing from common utilities module
import com.company.common.util.StringUtils;
import com.company.common.validation.Validator;

// Importing from local packages
import com.company.ecommerce.model.Product;
import com.company.ecommerce.service.ProductService;

public class EcommerceMain {
    
    public static void main(String[] args) {
        System.out.println("=== MULTI-MODULE E-COMMERCE APPLICATION ===");
        
        demonstrateCommonUtilsUsage();
        runEcommerceDemo();
    }
    
    private static void demonstrateCommonUtilsUsage() {
        System.out.println("\n--- Using Common Utilities Across Modules ---");
        
        // Using shared string utilities
        String rawName = "  john DOE  ";
        String cleanName = StringUtils.capitalize(rawName.trim());
        
        String email = "john.doe@company.com";
        String maskedEmail = StringUtils.maskEmail(email);
        
        String productId = StringUtils.generateId("PROD", 8);
        
        System.out.printf("✅ String utilities:%n");
        System.out.printf("  Raw name: '%s' → Clean: '%s'%n", rawName, cleanName);
        System.out.printf("  Email: %s → Masked: %s%n", email, maskedEmail);
        System.out.printf("  Generated ID: %s%n", productId);
        
        // Using shared validation
        testValidation("john@example.com", "+1-555-123-4567");
        testValidation("invalid-email", "invalid-phone");
    }
    
    private static void testValidation(String email, String phone) {
        System.out.printf("\n🔍 Validating email: %s, phone: %s%n", email, phone);
        
        try {
            Validator.validateEmail(email);
            System.out.println("  ✅ Email validation passed");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Email validation failed: " + e.getMessage());
        }
        
        try {
            Validator.validatePhone(phone);
            System.out.println("  ✅ Phone validation passed");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Phone validation failed: " + e.getMessage());
        }
    }
    
    private static void runEcommerceDemo() {
        System.out.println("\n--- E-commerce Application Features ---");
        
        ProductService productService = new ProductService();
        
        // Create products using shared utilities
        Product laptop = productService.createProduct("Gaming Laptop", 1299.99, "Electronics");
        Product mouse = productService.createProduct("Wireless Mouse", 29.99, "Electronics");
        
        System.out.println("📦 Products created:");
        System.out.printf("  %s%n", laptop);
        System.out.printf("  %s%n", mouse);
        
        System.out.println("\n🎯 Benefits of modular packages:");
        System.out.println("  ✅ Code reuse across multiple projects");
        System.out.println("  ✅ Centralized utility functions");
        System.out.println("  ✅ Consistent validation logic");
        System.out.println("  ✅ Easy maintenance and updates");
        System.out.println("  ✅ Clear separation of concerns");
    }
}
```

## Package Access Control and Visibility

```java
// Demonstrating package-level access control

// File: src/main/java/com/company/security/AccessControlDemo.java
package com.company.security;

import com.company.security.internal.InternalSecurityService;
import com.company.security.api.PublicSecurityAPI;

/**
 * Demonstrates package-level access control and visibility
 */
public class AccessControlDemo {
    
    public static void main(String[] args) {
        System.out.println("=== PACKAGE ACCESS CONTROL DEMONSTRATION ===");
        
        demonstrateAccessLevels();
        demonstratePackagePrivate();
        demonstrateProtectedAccess();
    }
    
    private static void demonstrateAccessLevels() {
        System.out.println("\n--- Java Access Levels ---");
        
        System.out.println("🔐 Access Level Hierarchy (most to least restrictive):");
        System.out.println("  1. private      - Same class only");
        System.out.println("  2. (no modifier) - Same package (package-private)");
        System.out.println("  3. protected    - Same package + subclasses");
        System.out.println("  4. public       - Everywhere");
        
        // Demonstrate access from different contexts
        SampleClass sample = new SampleClass();
        sample.demonstrateAccess();
    }
    
    private static void demonstratePackagePrivate() {
        System.out.println("\n--- Package-Private Access ---");
        
        // Can access package-private members within same package
        InternalSecurityService internal = new InternalSecurityService();
        
        // Package-private method accessible
        internal.performInternalOperation(); // This works - same package
        
        // Package-private field accessible
        String status = internal.internalStatus; // This works - same package
        
        System.out.printf("✅ Accessed package-private status: %s%n", status);
        
        System.out.println("📝 Package-private benefits:");
        System.out.println("  • Hide implementation details from external packages");
        System.out.println("  • Allow collaboration within package");
        System.out.println("  • Create internal APIs not visible outside");
        System.out.println("  • Support package-level cohesion");
    }
    
    private static void demonstrateProtectedAccess() {
        System.out.println("\n--- Protected Access ---");
        
        // Protected members accessible in same package
        ProtectedExample example = new ProtectedExample();
        example.protectedMethod(); // Accessible in same package
        
        String data = example.protectedField; // Accessible in same package
        System.out.printf("✅ Accessed protected field: %s%n", data);
    }
    
    // Inner class to demonstrate access levels
    static class SampleClass {
        private String privateField = "Private data";
        String packageField = "Package-private data";        // No modifier
        protected String protectedField = "Protected data";
        public String publicField = "Public data";
        
        public void demonstrateAccess() {
            System.out.println("\n🔍 Access demonstration from same class:");
            System.out.printf("  private: %s ✅%n", privateField);     // Always accessible
            System.out.printf("  package: %s ✅%n", packageField);     // Same package
            System.out.printf("  protected: %s ✅%n", protectedField); // Same package
            System.out.printf("  public: %s ✅%n", publicField);       // Always accessible
        }
        
        private void privateMethod() {
            System.out.println("Private method called");
        }
        
        void packageMethod() {
            System.out.println("Package-private method called");
        }
        
        protected void protectedMethod() {
            System.out.println("Protected method called");
        }
        
        public void publicMethod() {
            System.out.println("Public method called");
        }
    }
}

// File: src/main/java/com/company/security/internal/InternalSecurityService.java
package com.company.security.internal;

/**
 * Internal security service - package-private implementation details
 */
public class InternalSecurityService {
    
    // Package-private field - accessible within com.company.security package hierarchy
    String internalStatus = "Internal service active";
    
    // Package-private method
    void performInternalOperation() {
        System.out.println("🔒 Performing internal security operation");
        System.out.println("   This method is package-private");
    }
    
    // Public method for external access
    public void performPublicOperation() {
        System.out.println("🌐 Performing public security operation");
        performInternalOperation(); // Can call package-private from same package
    }
    
    // Private method for internal implementation
    private void performCriticalOperation() {
        System.out.println("🚨 Critical security operation (private)");
    }
}

// File: src/main/java/com/company/security/ProtectedExample.java
package com.company.security;

/**
 * Demonstrates protected access within same package
 */
public class ProtectedExample {
    
    protected String protectedField = "Protected data accessible in package";
    
    protected void protectedMethod() {
        System.out.println("🛡️ Protected method called within same package");
    }
    
    public void demonstrateProtectedAccess() {
        System.out.println("✅ Protected members accessible within same package");
        protectedMethod();
        System.out.printf("Protected field: %s%n", protectedField);
    }
}

// File: src/main/java/com/company/external/ExternalPackageAccess.java
package com.company.external;

import com.company.security.AccessControlDemo;
import com.company.security.ProtectedExample;
// import com.company.security.internal.InternalSecurityService; // Cannot access package-private

/**
 * Demonstrates access limitations from external package
 */
public class ExternalPackageAccess {
    
    public void demonstrateExternalAccess() {
        System.out.println("=== ACCESS FROM EXTERNAL PACKAGE ===");
        
        // Can access public classes and methods
        // AccessControlDemo demo = new AccessControlDemo(); // Public class accessible
        
        ProtectedExample example = new ProtectedExample();
        
        // Cannot access protected members from different package (unless subclass)
        // example.protectedMethod(); // Compilation error
        // String data = example.protectedField; // Compilation error
        
        System.out.println("❌ Cannot access protected members from external package");
        System.out.println("❌ Cannot access package-private classes/members");
        System.out.println("✅ Can only access public members from external package");
    }
}

// Subclass example showing protected access across packages
class ExternalSubclass extends ProtectedExample {
    
    public void demonstrateProtectedInheritance() {
        System.out.println("=== PROTECTED ACCESS VIA INHERITANCE ===");
        
        // Can access protected members through inheritance
        protectedMethod(); // ✅ Accessible through inheritance
        String data = protectedField; // ✅ Accessible through inheritance
        
        System.out.printf("✅ Protected field accessed via inheritance: %s%n", data);
        
        System.out.println("📝 Protected access rules:");
        System.out.println("  • Same package: Always accessible");
        System.out.println("  • Different package: Only via inheritance");
        System.out.println("  • Enables controlled extension of classes");
    }
}
```

## Complete Package Demo Application

```java
// File: src/main/java/com/company/demo/CompletePackageDemo.java
package com.company.demo;

import com.company.ecommerce.model.Customer;
import com.company.ecommerce.service.OrderService;
import com.company.banking.model.Customer as BankCustomer; // If Java supported aliases
// Since Java doesn't support import aliases, we use fully qualified names for conflicts
import com.company.common.util.StringUtils;
import com.company.common.validation.Validator;

/**
 * Complete demonstration of Java packages and import functionality
 */
public class CompletePackageDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE JAVA PACKAGES DEMONSTRATION ===");
        
        // 1. Basic package usage
        demonstrateBasicPackages();
        
        // 2. Import statements
        demonstrateImportStatements();
        
        // 3. Package organization
        demonstratePackageOrganization();
        
        // 4. Access control
        demonstrateAccessControl();
        
        // 5. Real-world examples
        demonstrateRealWorldUsage();
        
        // 6. Best practices summary
        summarizeBestPractices();
    }
    
    private static void demonstrateBasicPackages() {
        System.out.println("\n1. BASIC PACKAGE CONCEPTS");
        System.out.println("==========================");
        
        System.out.println("📦 Current package: " + CompletePackageDemo.class.getPackage().getName());
        System.out.println("🔗 Package provides namespace and organization");
        
        // Demonstrate namespace separation
        Customer ecommerceCustomer = new Customer("EC001", "Alice", "alice@shop.com", "123 Main St");
        
        // For banking customer, we need fully qualified name due to naming conflict
        com.enterprise.banking.model.Customer bankCustomer = 
            new com.enterprise.banking.model.Customer("BC001", "Alice", "alice@bank.com", "555-0123");
        
        System.out.printf("E-commerce Customer: %s%n", ecommerceCustomer);
        System.out.printf("Banking Customer: %s%n", bankCustomer);
        
        System.out.println("✅ Same class name, different packages - no conflict!");
    }
    
    private static void demonstrateImportStatements() {
        System.out.println("\n2. IMPORT STATEMENT TYPES");
        System.out.println("==========================");
        
        // Using imported utility classes
        String rawText = "  hello WORLD  ";
        String cleanText = StringUtils.capitalize(rawText.trim());
        
        String email = "user@example.com";
        boolean isValidEmail = Validator.isValidEmail(email);
        
        System.out.printf("Text processing: '%s' → '%s'%n", rawText, cleanText);
        System.out.printf("Email validation: %s → %s%n", email, isValidEmail ? "Valid" : "Invalid");
        
        System.out.println("✅ Import statements enable clean, readable code");
    }
    
    private static void demonstratePackageOrganization() {
        System.out.println("\n3. PACKAGE ORGANIZATION");
        System.out.println("========================");
        
        System.out.println("🏗️ Typical enterprise package structure:");
        System.out.println("com.company.project");
        System.out.println("├── model/       (Domain objects)");
        System.out.println("├── service/     (Business logic)");
        System.out.println("├── repository/  (Data access)");
        System.out.println("├── controller/  (API layer)");
        System.out.println("├── config/      (Configuration)");
        System.out.println("├── util/        (Utilities)");
        System.out.println("├── exception/   (Custom exceptions)");
        System.out.println("└── dto/         (Data transfer objects)");
        
        System.out.println("\n🎯 Benefits:");
        System.out.println("• Logical grouping of related classes");
        System.out.println("• Clear separation of concerns");
        System.out.println("• Easy navigation and maintenance");
        System.out.println("• Team collaboration support");
    }
    
    private static void demonstrateAccessControl() {
        System.out.println("\n4. PACKAGE ACCESS CONTROL");
        System.out.println("==========================");
        
        System.out.println("🔐 Access level summary:");
        System.out.println("public    → Accessible everywhere");
        System.out.println("protected → Same package + subclasses");
        System.out.println("(default) → Same package only");
        System.out.println("private   → Same class only");
        
        // Create instance to demonstrate access
        AccessExample example = new AccessExample();
        example.demonstratePublicAccess();
        
        // Package-private access works within same package
        example.packagePrivateMethod();
        
        System.out.println("✅ Package access control provides encapsulation");
    }
    
    private static void demonstrateRealWorldUsage() {
        System.out.println("\n5. REAL-WORLD USAGE EXAMPLES");
        System.out.println("=============================");
        
        System.out.println("🌐 Common package naming conventions:");
        System.out.println("• com.company.project - Standard enterprise");
        System.out.println("• org.apache.commons  - Open source organization");
        System.out.println("• java.util           - JDK standard library");
        System.out.println("• com.google.gson     - Third-party library");
        
        System.out.println("\n📚 Real-world examples:");
        System.out.println("• Spring Framework: org.springframework.*");
        System.out.println("• Apache Commons: org.apache.commons.*");
        System.out.println("• Google Guava: com.google.common.*");
        System.out.println("• JUnit Testing: org.junit.*");
        
        // Demonstrate actual usage
        java.util.List<String> list = java.util.Arrays.asList("Package", "Demo", "Example");
        java.time.LocalDate today = java.time.LocalDate.now();
        
        System.out.printf("Using java.util: %s%n", list);
        System.out.printf("Using java.time: %s%n", today);
    }
    
    private static void summarizeBestPractices() {
        System.out.println("\n6. PACKAGE BEST PRACTICES");
        System.out.println("==========================");
        
        System.out.println("✅ DO:");
        System.out.println("• Use reverse domain naming (com.company.project)");
        System.out.println("• Create logical package hierarchy");
        System.out.println("• Use specific imports over wildcard imports");
        System.out.println("• Group related classes in same package");
        System.out.println("• Use package-private for internal implementation");
        System.out.println("• Keep package names lowercase");
        System.out.println("• Make package names descriptive");
        
        System.out.println("\n❌ DON'T:");
        System.out.println("• Use default package for real applications");
        System.out.println("• Create too many levels of nesting");
        System.out.println("• Mix unrelated functionality in same package");
        System.out.println("• Use wildcard imports for external libraries");
        System.out.println("• Create circular dependencies between packages");
        System.out.println("• Use reserved keywords in package names");
        
        System.out.println("\n🎯 Key Takeaways:");
        System.out.println("• Packages provide namespace management");
        System.out.println("• Import statements control class visibility");
        System.out.println("• Proper organization improves maintainability");
        System.out.println("• Access control enables encapsulation");
        System.out.println("• Consistent naming conventions aid understanding");
    }
    
    // Supporting class for demonstrations
    static class AccessExample {
        public String publicField = "Public data";
        String packageField = "Package-private data";
        private String privateField = "Private data";
        
        public void demonstratePublicAccess() {
            System.out.println("🌐 Public method accessible everywhere");
        }
        
        void packagePrivateMethod() {
            System.out.println("📦 Package-private method accessible in same package");
        }
        
        private void privateMethod() {
            System.out.println("🔒 Private method accessible in same class only");
        }
    }
}
```

## Interview Questions & Answers

**Q1: What are packages in Java? Why are they important?**
**A:** Packages are organizational units that group related classes and interfaces, providing namespace management, access control, and logical code organization. They prevent naming conflicts, enable modular design, and support team collaboration.

**Q2: What's the difference between import and package statements?**
**A:** Package statement declares which package a class belongs to (must be first line). Import statements bring classes from other packages into current namespace for use without fully qualified names.

**Q3: Explain the different types of import statements.**
**A:** Single class import (import java.util.List), wildcard import (import java.util.*), static import (import static Math.PI), and fully qualified names (java.util.Date) for conflict resolution.

**Q4: What is package-private access? When would you use it?**
**A:** Package-private (no modifier) allows access within the same package only. Use for internal implementation classes, helper utilities, or when you want controlled access within a module but hide from external packages.

**Q5: How do you handle naming conflicts between classes from different packages?**
**A:** Use fully qualified names (java.util.Date vs java.sql.Date), import one and qualify the other, or reorganize code to avoid conflicts. Java doesn't support import aliases.

**Q6: What are the Java package naming conventions?**
**A:** Use reverse domain notation (com.company.project), all lowercase, descriptive names, and logical hierarchy. Avoid reserved keywords and keep names concise but clear.

**Q7: Can you have circular dependencies between packages?**
**A:** While Java allows circular package dependencies, they should be avoided as they indicate poor design, make code harder to maintain, and can cause issues with modular systems.

**Q8: What's the difference between java.util.Date and java.sql.Date?**
**A:** java.util.Date represents a point in time with millisecond precision. java.sql.Date extends util.Date but represents only date (no time) for SQL DATE columns. They're in different packages to avoid conflicts.

## Key Takeaways

1. **Packages organize code hierarchically** and provide namespace management
2. **Import statements control visibility** of classes from other packages  
3. **Package-private access** enables controlled sharing within modules
4. **Naming conventions** (reverse domain) prevent conflicts and aid understanding
5. **Proper package structure** improves maintainability and team collaboration
6. **Access control levels** (public, protected, package, private) enable encapsulation
7. **Wildcard imports** should be used sparingly to maintain code clarity
8. **Fully qualified names** resolve naming conflicts between packages
9. **Package hierarchy** should reflect logical application architecture
10. **Consistent organization** makes large codebases manageable and scalable

---

*Remember: Packages are like organizing your code library - each section has its place, everything is easy to find, and you can control who has access to what!*