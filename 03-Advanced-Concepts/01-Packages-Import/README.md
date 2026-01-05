# Packages and Import in Java - Code Organization and Namespace Management

## Simple Explanation

Think of **Packages** as **folders on your computer** that organize related files:

- **Documents folder** = Contains all document files (like a package for document classes)
- **Photos folder** = Contains all image files (like a package for image-related classes)
- **Music folder** = Contains all audio files (like a package for audio classes)
- **Import** = Creating shortcuts to files from other folders so you can use them easily

### Real-World Analogies
- **Library organization** = Books grouped by subject (Science, History, Fiction packages)
- **Department store** = Items organized by category (Electronics, Clothing, Grocery packages)
- **Company structure** = Employees organized by department (HR, IT, Sales packages)
- **File cabinet** = Documents sorted by type and project (different drawer packages)

## Professional Definition

**Packages** in Java are namespaces that organize related classes and interfaces into a hierarchical structure. They provide access control, prevent naming conflicts, and make code more modular and maintainable. **Import** statements allow you to use classes from other packages without writing their fully qualified names.

## Why Use Packages?

### Benefits:
- **Organization**: Logically group related classes and interfaces
- **Namespace Management**: Avoid naming conflicts between classes
- **Access Control**: Provide package-level visibility and protection
- **Code Reusability**: Share packages across different projects
- **Maintainability**: Easier to locate and manage related code
- **Modularity**: Create self-contained modules with clear dependencies

### Problems Without Packages:
```java
// WITHOUT PACKAGES - Everything in default package
// All classes in the same folder - chaos!

// File: BankAccount.java (default package)
class BankAccount {
    // Banking logic
}

// File: Account.java (default package) - NAMING CONFLICT!
class Account {
    // Some other account logic
}

// File: Customer.java (default package)  
class Customer {
    // Can't distinguish between different Account classes!
    // Account account; // Which Account? Banking or other?
}

// File: String.java (default package) - CONFLICTS WITH java.lang.String!
class String {  
    // This would shadow java.lang.String - DISASTER!
}

// No organization, no protection, naming chaos!
```

### With Packages - Clean and Organized:
```java
// WITH PACKAGES - Well organized structure

// File: com/bank/account/BankAccount.java
package com.bank.account;
public class BankAccount {
    // Banking account logic
}

// File: com/social/account/Account.java  
package com.social.account;
public class Account {
    // Social media account logic - NO CONFLICT!
}

// File: com/bank/customer/Customer.java
package com.bank.customer;
import com.bank.account.BankAccount;

public class Customer {
    private BankAccount bankAccount;  // Clear which Account type!
    // Can use multiple Account types without confusion
}

// Clear organization, no conflicts, easy to maintain!
```

## Package Declaration and Structure

### 1. Basic Package Declaration
```java
// Package declaration MUST be the first non-comment line
package com.company.project.module;

// After package declaration, imports come next
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

// Then class declaration
public class Employee {
    private String name;
    private LocalDate hireDate;
    private List<String> skills;
    
    public Employee(String name) {
        this.name = name;
        this.hireDate = LocalDate.now();
        this.skills = new ArrayList<>();
    }
    
    // Class implementation
}
```

### 2. Package Naming Conventions
```java
// CORRECT package naming conventions (reverse domain naming)
package com.companyname.projectname.modulename;
package org.apache.commons.lang;
package edu.stanford.cs.algorithms;
package io.github.username.projectname;

// INCORRECT naming conventions - avoid these!
// package MyProject;              // Should be lowercase
// package com.Company.Project;    // Should not use camelCase
// package java.custom;            // Don't use java.* (reserved)
// package sun.internal;           // Don't use sun.* (reserved)

// Real-world examples:
package com.amazon.ecommerce.cart;
package com.google.search.algorithms;
package org.springframework.web.mvc;
package io.microservices.user.service;

// Package structure for a comprehensive project:
package com.techcorp.hrms.employee.model;
package com.techcorp.hrms.employee.service;
package com.techcorp.hrms.employee.controller;
package com.techcorp.hrms.payroll.calculator;
package com.techcorp.hrms.payroll.service;
package com.techcorp.hrms.database.connection;
package com.techcorp.hrms.utils.validation;
package com.techcorp.hrms.utils.formatter;
```

### 3. Directory Structure and File Organization
```
// Corresponding directory structure on file system:
src/
└── main/
    └── java/
        └── com/
            └── techcorp/
                └── hrms/
                    ├── employee/
                    │   ├── model/
                    │   │   ├── Employee.java
                    │   │   ├── Department.java
                    │   │   └── Position.java
                    │   ├── service/
                    │   │   ├── EmployeeService.java
                    │   │   ├── DepartmentService.java
                    │   │   └── PositionService.java
                    │   └── controller/
                    │       ├── EmployeeController.java
                    │       └── DepartmentController.java
                    ├── payroll/
                    │   ├── calculator/
                    │   │   ├── SalaryCalculator.java
                    │   │   ├── TaxCalculator.java
                    │   │   └── BenefitsCalculator.java
                    │   └── service/
                    │       ├── PayrollService.java
                    │       └── TaxService.java
                    ├── database/
                    │   └── connection/
                    │       ├── DatabaseManager.java
                    │       ├── ConnectionPool.java
                    │       └── TransactionManager.java
                    └── utils/
                        ├── validation/
                        │   ├── EmailValidator.java
                        │   ├── PhoneValidator.java
                        │   └── SSNValidator.java
                        └── formatter/
                            ├── DateFormatter.java
                            ├── CurrencyFormatter.java
                            └── PhoneFormatter.java
```

## Import Statements

### 1. Single Type Import (Recommended)
```java
package com.example.demo;

// Import specific classes - RECOMMENDED approach
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Import from custom packages
import com.techcorp.hrms.employee.model.Employee;
import com.techcorp.hrms.employee.service.EmployeeService;
import com.techcorp.hrms.payroll.calculator.SalaryCalculator;

public class EmployeeManager {
    private List<Employee> employees;              // Uses imported List
    private Map<String, Employee> employeeMap;     // Uses imported Map
    private EmployeeService employeeService;       // Uses imported EmployeeService
    private SalaryCalculator salaryCalculator;     // Uses imported SalaryCalculator
    
    public EmployeeManager() {
        employees = new ArrayList<>();             // Uses imported ArrayList
        employeeMap = new HashMap<>();             // Uses imported HashMap
        employeeService = new EmployeeService();
        salaryCalculator = new SalaryCalculator();
    }
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employeeMap.put(employee.getId(), employee);
        
        // Uses imported LocalDate and DateTimeFormatter
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Employee added on: " + today.format(formatter));
    }
}
```

### 2. Wildcard Import (Use Sparingly)
```java
package com.example.demo;

// Wildcard imports - use only when importing many classes from same package
import java.util.*;                    // Imports ALL public classes from java.util
import java.time.*;                    // Imports ALL public classes from java.time
import javax.swing.*;                  // Common for GUI development

// DON'T do this with your custom packages - too broad
// import com.techcorp.hrms.*;         // Avoid - unclear what's imported
// import com.techcorp.hrms.employee.*; // Avoid - unclear dependencies

public class WildcardImportExample {
    private List<String> names;         // From java.util.*
    private Set<Integer> numbers;       // From java.util.*
    private Map<String, Object> data;   // From java.util.*
    private LocalDate date;             // From java.time.*
    private LocalDateTime timestamp;    // From java.time.*
    
    // Problem: Not clear which specific classes are being used
    // Solution: Use specific imports for better code clarity
}
```

### 3. Static Import
```java
package com.example.demo;

// Static imports - import static methods and constants
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

// Static import of ALL static members (use carefully)
import static java.lang.System.*;

// Static import from custom classes
import static com.techcorp.hrms.utils.Constants.MAX_EMPLOYEES;
import static com.techcorp.hrms.utils.Constants.DEFAULT_DEPARTMENT;
import static com.techcorp.hrms.utils.MathUtils.calculatePercentage;
import static com.techcorp.hrms.utils.ValidationUtils.isValidEmail;

public class StaticImportExample {
    public double calculateCircleArea(double radius) {
        // Can use PI directly (imported statically from Math)
        return PI * pow(radius, 2);  // Instead of Math.PI * Math.pow(radius, 2)
    }
    
    public double calculateDistance(double x1, double y1, double x2, double y2) {
        // Using statically imported methods directly
        double deltaX = abs(x2 - x1);  // Instead of Math.abs()
        double deltaY = abs(y2 - y1);  // Instead of Math.abs()
        return sqrt(deltaX * deltaX + deltaY * deltaY);  // Instead of Math.sqrt()
    }
    
    public void validateAndAddEmployee(String email, String department) {
        // Using statically imported constants and methods
        if (isValidEmail(email)) {  // From ValidationUtils
            out.println("Valid email: " + email);  // out imported from System
            
            String dept = (department != null) ? department : DEFAULT_DEPARTMENT;
            if (calculatePercentage(employees.size(), MAX_EMPLOYEES) < 90) {
                // Add employee logic
                out.println("Employee can be added to " + dept);
            }
        } else {
            err.println("Invalid email format");  // err imported from System
        }
    }
    
    private java.util.List<String> employees = new java.util.ArrayList<>();
}
```

### 4. Fully Qualified Names (When Imports Conflict)
```java
package com.example.demo;

// When you have naming conflicts, use fully qualified names
// import java.util.Date;        // Conflicts with java.sql.Date
// import java.sql.Date;         // Conflicts with java.util.Date

public class DateConflictExample {
    // Use fully qualified names to avoid conflicts
    private java.util.Date utilDate;          // Utility date
    private java.sql.Date sqlDate;            // SQL date
    private java.time.LocalDate localDate;    // Modern date (preferred)
    
    public void demonstrateConflictResolution() {
        // Different Date types with full qualification
        utilDate = new java.util.Date();                    // Current timestamp
        sqlDate = new java.sql.Date(System.currentTimeMillis()); // SQL date
        localDate = java.time.LocalDate.now();              // Modern approach
        
        // Using different String classes (hypothetical conflict)
        String javaString = "Java built-in String";
        // If you had custom String class in your package:
        // com.example.demo.String customString = new com.example.demo.String();
        
        System.out.println("Util Date: " + utilDate);
        System.out.println("SQL Date: " + sqlDate);
        System.out.println("Local Date: " + localDate);
    }
    
    // Method parameters with fully qualified types
    public void processDateTypes(java.util.Date utilDate, 
                                java.sql.Date sqlDate,
                                java.time.LocalDate localDate) {
        // Clear distinction between different date types
        System.out.println("Processing different date types:");
        System.out.println("Util Date: " + utilDate.toString());
        System.out.println("SQL Date: " + sqlDate.toString());
        System.out.println("Local Date: " + localDate.toString());
    }
}
```

## Package Access Control

### 1. Package-Private (Default) Access
```java
// File: com/company/model/Employee.java
package com.company.model;

// Package-private class (no public modifier)
class InternalEmployee {  // Only accessible within com.company.model package
    String employeeId;    // Package-private field
    String name;          // Package-private field
    
    void updateRecord() { // Package-private method
        System.out.println("Updating internal employee record");
    }
}

// Public class (accessible from other packages)
public class Employee {
    private String id;
    private String name;
    
    // Package-private fields - accessible within package only
    String department;      // No access modifier = package-private
    double salary;         // Package-private
    
    // Public constructor
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Package-private methods - internal package operations
    void setDepartment(String department) {  // Only within package
        this.department = department;
    }
    
    void setSalary(double salary) {          // Only within package
        this.salary = salary;
    }
    
    double calculateAnnualSalary() {         // Package-private calculation
        return salary * 12;
    }
    
    // Public methods - external interface
    public String getId() { return id; }
    public String getName() { return name; }
    
    // Public methods that use package-private functionality
    public String getEmployeeInfo() {
        return String.format("Employee: %s (%s) - Dept: %s, Salary: %.2f", 
                           name, id, department, salary);
    }
}

// File: com/company/model/Department.java
package com.company.model;

public class Department {
    private String name;
    private java.util.List<Employee> employees;
    
    public Department(String name) {
        this.name = name;
        this.employees = new java.util.ArrayList<>();
    }
    
    public void addEmployee(Employee employee) {
        // Can access package-private members of Employee
        employee.setDepartment(this.name);    // Package-private method
        employee.setSalary(50000.0);          // Package-private method
        
        employees.add(employee);
        System.out.println("Added employee to department: " + name);
        System.out.println("Department: " + employee.department); // Package-private field
    }
    
    public double calculateTotalSalaries() {
        double total = 0;
        for (Employee employee : employees) {
            // Can access package-private method
            total += employee.calculateAnnualSalary();
        }
        return total;
    }
    
    // Can create and use package-private classes
    public void processInternalEmployees() {
        InternalEmployee internal = new InternalEmployee(); // Same package
        internal.employeeId = "INT001";                     // Package-private field
        internal.updateRecord();                            // Package-private method
    }
}
```

### 2. Cross-Package Access Control
```java
// File: com/company/service/EmployeeService.java
package com.company.service;

import com.company.model.Employee;
import com.company.model.Department;
// import com.company.model.InternalEmployee;  // Cannot import - not public

public class EmployeeService {
    private java.util.List<Employee> employees;
    private java.util.List<Department> departments;
    
    public EmployeeService() {
        employees = new java.util.ArrayList<>();
        departments = new java.util.ArrayList<>();
    }
    
    public void createEmployee(String id, String name) {
        Employee employee = new Employee(id, name);  // Can use public constructor
        
        // CANNOT access package-private members (different package)
        // employee.setDepartment("IT");     // Compilation error!
        // employee.setSalary(60000);        // Compilation error!
        // employee.department = "IT";       // Compilation error!
        // employee.salary = 60000;          // Compilation error!
        
        // CAN only use public methods
        System.out.println("Created: " + employee.getName());
        System.out.println("Employee ID: " + employee.getId());
        
        employees.add(employee);
    }
    
    public void assignToDepartment(Employee employee, Department department) {
        // Can only use public methods
        department.addEmployee(employee);  // Department handles internal assignment
        
        // Cannot create package-private classes from different package
        // InternalEmployee internal = new InternalEmployee();  // Compilation error!
    }
    
    public void displayEmployeeDetails() {
        for (Employee employee : employees) {
            // Can only access through public interface
            System.out.println(employee.getEmployeeInfo()); // Public method
            
            // Cannot access package-private calculation directly
            // System.out.println(employee.calculateAnnualSalary()); // Compilation error!
        }
    }
}
```

## Real-World Package Organization Examples

### 1. E-Commerce Application Structure
```java
// Complete package structure for e-commerce application

// Product Management
package com.ecommerce.product.model;
public class Product {
    private String productId;
    private String name;
    private double price;
    private String category;
    private int stockQuantity;
    
    // Package-private for internal product operations
    void updateStock(int quantity) {
        this.stockQuantity = quantity;
    }
    
    double getDiscountedPrice(double discountPercentage) {
        return price * (1 - discountPercentage / 100);
    }
    
    // Public interface
    public Product(String productId, String name, double price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stockQuantity = 0;
    }
    
    // Public getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getStockQuantity() { return stockQuantity; }
}

package com.ecommerce.product.service;
import com.ecommerce.product.model.Product;
import java.util.*;

public class ProductService {
    private Map<String, Product> products;
    private Map<String, List<Product>> categoryProducts;
    
    public ProductService() {
        products = new HashMap<>();
        categoryProducts = new HashMap<>();
    }
    
    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
        
        // Organize by category
        categoryProducts.computeIfAbsent(product.getCategory(), k -> new ArrayList<>())
                       .add(product);
        
        System.out.println("Added product: " + product.getName());
    }
    
    public Product getProduct(String productId) {
        return products.get(productId);
    }
    
    public List<Product> getProductsByCategory(String category) {
        return new ArrayList<>(categoryProducts.getOrDefault(category, new ArrayList<>()));
    }
    
    public void updateProductStock(String productId, int quantity) {
        Product product = products.get(productId);
        if (product != null) {
            // Can access package-private method (same parent package group)
            product.updateStock(quantity);
            System.out.println("Updated stock for " + product.getName() + ": " + quantity);
        }
    }
    
    public double calculateCategoryValue(String category) {
        return getProductsByCategory(category).stream()
               .mapToDouble(p -> p.getPrice() * p.getStockQuantity())
               .sum();
    }
}

// Shopping Cart Management
package com.ecommerce.cart.model;
import com.ecommerce.product.model.Product;

public class CartItem {
    private Product product;
    private int quantity;
    private double unitPrice;
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }
    
    public double getTotalPrice() {
        return unitPrice * quantity;
    }
    
    // Package-private for cart operations
    void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    void applyDiscount(double discountPercentage) {
        this.unitPrice = product.getDiscountedPrice(discountPercentage);
    }
    
    // Public getters
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
}

package com.ecommerce.cart.model;
import java.util.*;

public class ShoppingCart {
    private String cartId;
    private String customerId;
    private List<CartItem> items;
    private double totalAmount;
    
    public ShoppingCart(String cartId, String customerId) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }
    
    public void addItem(CartItem item) {
        // Check if product already exists
        Optional<CartItem> existingItem = items.stream()
            .filter(i -> i.getProduct().getProductId().equals(item.getProduct().getProductId()))
            .findFirst();
        
        if (existingItem.isPresent()) {
            // Update quantity using package-private method
            CartItem existing = existingItem.get();
            existing.updateQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
        
        calculateTotal();
    }
    
    public void applyCartDiscount(double discountPercentage) {
        for (CartItem item : items) {
            // Use package-private method
            item.applyDiscount(discountPercentage);
        }
        calculateTotal();
    }
    
    private void calculateTotal() {
        totalAmount = items.stream()
                          .mapToDouble(CartItem::getTotalPrice)
                          .sum();
    }
    
    // Public getters
    public String getCartId() { return cartId; }
    public String getCustomerId() { return customerId; }
    public List<CartItem> getItems() { return new ArrayList<>(items); }
    public double getTotalAmount() { return totalAmount; }
    public int getItemCount() { return items.size(); }
}

// Order Processing
package com.ecommerce.order.model;
import com.ecommerce.cart.model.ShoppingCart;
import com.ecommerce.cart.model.CartItem;
import java.time.LocalDateTime;
import java.util.*;

public class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }
    
    public Order(String orderId, ShoppingCart cart) {
        this.orderId = orderId;
        this.customerId = cart.getCustomerId();
        this.orderItems = convertCartItemsToOrderItems(cart.getItems());
        this.totalAmount = cart.getTotalAmount();
        this.status = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();
    }
    
    private List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                cartItem.getProduct().getProductId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getUnitPrice()
            );
            orderItems.add(orderItem);
        }
        return orderItems;
    }
    
    // Package-private status management
    void updateStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.DELIVERED) {
            this.deliveryDate = LocalDateTime.now();
        }
    }
    
    // Public getters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getOrderItems() { return new ArrayList<>(orderItems); }
    public double getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
}

package com.ecommerce.order.model;

public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    
    public OrderItem(String productId, String productName, int quantity, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }
    
    // All getters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
}

// Service Layer
package com.ecommerce.service;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.cart.model.*;
import com.ecommerce.order.model.Order;
import java.util.*;

public class ECommerceService {
    private ProductService productService;
    private Map<String, ShoppingCart> activeCarts;
    private Map<String, Order> orders;
    
    public ECommerceService() {
        productService = new ProductService();
        activeCarts = new HashMap<>();
        orders = new HashMap<>();
    }
    
    // Product operations
    public void addProduct(String id, String name, double price, String category) {
        Product product = new Product(id, name, price, category);
        productService.addProduct(product);
    }
    
    public void updateProductStock(String productId, int quantity) {
        productService.updateProductStock(productId, quantity);
    }
    
    // Cart operations
    public String createCart(String customerId) {
        String cartId = "CART_" + System.currentTimeMillis();
        ShoppingCart cart = new ShoppingCart(cartId, customerId);
        activeCarts.put(cartId, cart);
        return cartId;
    }
    
    public void addToCart(String cartId, String productId, int quantity) {
        ShoppingCart cart = activeCarts.get(cartId);
        Product product = productService.getProduct(productId);
        
        if (cart != null && product != null && product.getStockQuantity() >= quantity) {
            CartItem item = new CartItem(product, quantity);
            cart.addItem(item);
            
            // Update product stock
            productService.updateProductStock(productId, 
                product.getStockQuantity() - quantity);
            
            System.out.println("Added " + quantity + " x " + product.getName() + " to cart");
        }
    }
    
    // Order operations
    public String placeOrder(String cartId) {
        ShoppingCart cart = activeCarts.get(cartId);
        if (cart != null && cart.getItemCount() > 0) {
            String orderId = "ORDER_" + System.currentTimeMillis();
            Order order = new Order(orderId, cart);
            orders.put(orderId, order);
            
            // Remove cart after order
            activeCarts.remove(cartId);
            
            System.out.println("Order placed: " + orderId + " for $" + order.getTotalAmount());
            return orderId;
        }
        return null;
    }
    
    public void updateOrderStatus(String orderId, Order.OrderStatus status) {
        Order order = orders.get(orderId);
        if (order != null) {
            // Can access package-private method if in same package
            // order.updateStatus(status);  // Would work if in com.ecommerce.order package
            System.out.println("Order " + orderId + " status updated to " + status);
        }
    }
    
    // Reporting
    public void generateSalesReport() {
        System.out.println("\n=== SALES REPORT ===");
        double totalSales = orders.values().stream()
                                 .mapToDouble(Order::getTotalAmount)
                                 .sum();
        
        System.out.println("Total Orders: " + orders.size());
        System.out.println("Total Sales: $" + String.format("%.2f", totalSales));
        System.out.println("Active Carts: " + activeCarts.size());
    }
}
```

### 2. Complete Package Demonstration
```java
// Main demonstration class
package com.ecommerce;
import com.ecommerce.service.ECommerceService;
import com.ecommerce.order.model.Order;

public class ECommerceDemo {
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE PACKAGE DEMONSTRATION ===");
        
        ECommerceService service = new ECommerceService();
        
        // Add products
        service.addProduct("P001", "Laptop", 999.99, "Electronics");
        service.addProduct("P002", "Mouse", 29.99, "Electronics");
        service.addProduct("P003", "Keyboard", 79.99, "Electronics");
        service.addProduct("P004", "Monitor", 299.99, "Electronics");
        
        // Update stock
        service.updateProductStock("P001", 10);
        service.updateProductStock("P002", 50);
        service.updateProductStock("P003", 30);
        service.updateProductStock("P004", 15);
        
        // Create shopping cart
        String cartId = service.createCart("CUSTOMER001");
        
        // Add items to cart
        service.addToCart(cartId, "P001", 1);  // Laptop
        service.addToCart(cartId, "P002", 2);  // Mouse x2
        service.addToCart(cartId, "P003", 1);  // Keyboard
        
        // Place order
        String orderId = service.placeOrder(cartId);
        
        // Update order status
        service.updateOrderStatus(orderId, Order.OrderStatus.CONFIRMED);
        service.updateOrderStatus(orderId, Order.OrderStatus.SHIPPED);
        service.updateOrderStatus(orderId, Order.OrderStatus.DELIVERED);
        
        // Generate report
        service.generateSalesReport();
        
        // Demonstrate package access control
        demonstratePackageAccess();
    }
    
    private static void demonstratePackageAccess() {
        System.out.println("\n=== PACKAGE ACCESS CONTROL DEMO ===");
        
        // Can create public classes from other packages
        com.ecommerce.product.model.Product product = 
            new com.ecommerce.product.model.Product("P005", "Tablet", 499.99, "Electronics");
        
        // Can access public methods
        System.out.println("Product: " + product.getName());
        System.out.println("Price: $" + product.getPrice());
        
        // Cannot access package-private methods (different package)
        // product.updateStock(10);     // Compilation error!
        // product.getDiscountedPrice(10); // Compilation error!
        
        com.ecommerce.cart.model.ShoppingCart cart = 
            new com.ecommerce.cart.model.ShoppingCart("CART002", "CUSTOMER002");
        
        com.ecommerce.cart.model.CartItem item = 
            new com.ecommerce.cart.model.CartItem(product, 1);
        
        cart.addItem(item);
        
        // Cannot access package-private methods
        // item.updateQuantity(2);      // Compilation error!
        // item.applyDiscount(10);      // Compilation error!
        
        System.out.println("Cart Total: $" + cart.getTotalAmount());
    }
}
```

## Interview Questions & Answers

**Q1: What are packages in Java? Why are they important?**
**A:** Packages are namespaces that organize related classes and interfaces. They're important for:
- Avoiding naming conflicts
- Providing access control
- Organizing code logically
- Making code more maintainable
- Enabling code reusability

**Q2: What's the difference between import and package statements?**
**A:**
- **package**: Declares which package the current class belongs to (first line)
- **import**: Brings classes from other packages into current namespace (after package, before class)

**Q3: What happens if you don't declare a package?**
**A:** The class belongs to the **default package** (unnamed package). This is not recommended for production code as it lacks organization and can cause naming conflicts.

**Q4: Can two classes have the same name in Java?**
**A:** Yes, if they're in different packages. The fully qualified name (package + class name) must be unique.

**Q5: What's the difference between import and static import?**
**A:**
- **import**: Imports classes and interfaces
- **static import**: Imports static methods and constants, allowing direct use without class name

**Q6: What is package-private access?**
**A:** Default access level (no modifier) where members are accessible only within the same package. It's more restrictive than protected but less than private.

**Q7: Can you import from the default package?**
**A:** No, you cannot import classes from the default package into named packages. This is another reason to avoid default package.

**Q8: What's the naming convention for packages?**
**A:** Use reverse domain naming: com.company.project.module (all lowercase, separated by dots). This ensures global uniqueness.

## Key Takeaways

1. **Packages organize code** into logical, hierarchical namespaces
2. **Package naming** follows reverse domain convention for uniqueness
3. **Import statements** enable using classes from other packages
4. **Static imports** allow direct access to static methods and constants
5. **Package-private access** provides intermediate visibility level
6. **Directory structure** must match package hierarchy exactly
7. **Fully qualified names** resolve naming conflicts
8. **Default package** should be avoided in production code
9. **Access control** is enhanced by package boundaries
10. **Code organization** through packages improves maintainability and collaboration

---

*Remember: Packages are like organizing your digital files into folders - they keep everything tidy, prevent naming conflicts, and make it easy to find what you need!*