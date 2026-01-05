# Encapsulation in Java - Data Protection and Information Hiding

## Simple Explanation

Think of **Encapsulation** as **protective packaging** or **security systems**:

- Like a **medicine capsule** that protects and contains the medicine inside
- **Bank vault** that protects valuable items with controlled access
- **TV remote control** - you use buttons without knowing internal circuits
- **Car dashboard** - simple interface hiding complex engine mechanics

### Real-World Analogies
- **ATM machine** = Encapsulation (simple interface, complex internal operations)
- **Smartphone** = Encapsulation (user-friendly interface hiding complex technology)
- **House with doors/windows** = Encapsulation (controlled access points)
- **Company departments** = Encapsulation (each department has private operations and public services)

## Professional Definition

**Encapsulation** is a fundamental OOP principle that bundles data (variables) and methods (functions) together into a single unit (class) while restricting direct access to some components. It achieves **data hiding** by making instance variables private and providing controlled access through public methods (getters and setters).

## Why Use Encapsulation?

### Benefits:
- **Data Protection**: Prevents unauthorized access and modification
- **Data Validation**: Control how data is set and retrieved
- **Code Maintainability**: Internal implementation can change without affecting external code
- **Security**: Sensitive data remains hidden from outside access
- **Flexibility**: Can add validation, logging, or processing in accessor methods
- **Code Organization**: Related data and methods are grouped together

### Problems Without Encapsulation:
```java
// BAD EXAMPLE - No Encapsulation
class BadBankAccount {
    public String accountNumber;    // Anyone can change this!
    public double balance;          // Direct access - dangerous!
    public String pin;             // Security risk!
    
    public void withdraw(double amount) {
        balance -= amount;  // No validation!
    }
}

// Usage - Dangerous!
BadBankAccount account = new BadBankAccount();
account.balance = 1000000;  // Anyone can set any balance!
account.pin = "1234";       // Security breach!
account.accountNumber = ""; // Can break the system!
```

### With Encapsulation - Safe and Controlled:
```java
// GOOD EXAMPLE - Proper Encapsulation
class GoodBankAccount {
    private String accountNumber;   // Protected data
    private double balance;         // Controlled access
    private String pin;            // Hidden from outside
    
    // Controlled access methods
    public double getBalance() {
        // Can add logging, authentication, etc.
        return balance;
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;  // Invalid withdrawal
    }
}
```

## The Four Pillars of Encapsulation

### 1. Private Instance Variables
```java
public class Student {
    // Private data - cannot be accessed directly from outside
    private String studentId;
    private String name;
    private int age;
    private double gpa;
    private String email;
    
    // Private variables are only accessible within this class
    private void validateAge(int age) {
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
    }
}
```

### 2. Public Getter Methods
```java
public class Student {
    private String name;
    private int age;
    private double gpa;
    
    // Getter methods - provide READ access
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    // Can add logic in getters
    public String getGradeLevel() {
        if (gpa >= 3.5) return "Excellent";
        else if (gpa >= 3.0) return "Good";
        else if (gpa >= 2.0) return "Average";
        else return "Needs Improvement";
    }
    
    // Can return formatted data
    public String getFormattedGPA() {
        return String.format("%.2f", gpa);
    }
}
```

### 3. Public Setter Methods with Validation
```java
public class Student {
    private String name;
    private int age;
    private double gpa;
    private String email;
    
    // Setter methods - provide CONTROLLED WRITE access
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }
    
    public void setAge(int age) {
        if (age < 16 || age > 100) {
            throw new IllegalArgumentException("Age must be between 16 and 100");
        }
        this.age = age;
    }
    
    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }
    
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.toLowerCase().trim();
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
}
```

### 4. Controlled Access Through Methods
```java
public class Student {
    private String studentId;
    private String name;
    private int age;
    private double gpa;
    private List<String> courses;
    
    // Constructor with validation
    public Student(String studentId, String name, int age) {
        setStudentId(studentId);  // Use setter for validation
        setName(name);
        setAge(age);
        this.gpa = 0.0;
        this.courses = new ArrayList<>();
    }
    
    // Business logic methods
    public void enrollInCourse(String courseName) {
        if (courseName != null && !courses.contains(courseName)) {
            courses.add(courseName);
            System.out.println(name + " enrolled in " + courseName);
        }
    }
    
    public boolean dropCourse(String courseName) {
        if (courses.remove(courseName)) {
            System.out.println(name + " dropped " + courseName);
            return true;
        }
        return false;
    }
    
    public int getCourseCount() {
        return courses.size();
    }
    
    // Read-only access to courses (defensive copying)
    public List<String> getCourses() {
        return new ArrayList<>(courses);  // Return copy, not original
    }
}
```

## Real-World Encapsulation Examples

### 1. Bank Account with Complete Encapsulation
```java
public class BankAccount {
    // Private instance variables
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String pin;
    private boolean isActive;
    private List<String> transactionHistory;
    
    // Constructor with validation
    public BankAccount(String accountNumber, String accountHolderName, String pin) {
        setAccountNumber(accountNumber);
        setAccountHolderName(accountHolderName);
        setPin(pin);
        this.balance = 0.0;
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created");
    }
    
    // Getters - READ access
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    // Controlled setters with validation
    private void setAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 10) {
            throw new IllegalArgumentException("Account number must be at least 10 characters");
        }
        this.accountNumber = accountNumber;
    }
    
    private void setAccountHolderName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Account holder name must be at least 2 characters");
        }
        this.accountHolderName = name.trim();
    }
    
    private void setPin(String pin) {
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits");
        }
        this.pin = pin;
    }
    
    // Business methods with encapsulation
    public boolean deposit(double amount) {
        if (!isActive) {
            System.out.println("Account is inactive");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return false;
        }
        
        balance += amount;
        addTransaction("Deposited $" + String.format("%.2f", amount));
        System.out.println("Deposited $" + String.format("%.2f", amount) + 
                          ". New balance: $" + String.format("%.2f", balance));
        return true;
    }
    
    public boolean withdraw(double amount, String enteredPin) {
        if (!validatePin(enteredPin)) {
            System.out.println("Invalid PIN");
            return false;
        }
        if (!isActive) {
            System.out.println("Account is inactive");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return false;
        }
        
        balance -= amount;
        addTransaction("Withdrew $" + String.format("%.2f", amount));
        System.out.println("Withdrew $" + String.format("%.2f", amount) + 
                          ". Remaining balance: $" + String.format("%.2f", balance));
        return true;
    }
    
    public boolean transfer(BankAccount targetAccount, double amount, String enteredPin) {
        if (withdraw(amount, enteredPin)) {
            if (targetAccount.deposit(amount)) {
                addTransaction("Transferred $" + String.format("%.2f", amount) + 
                             " to " + targetAccount.getAccountNumber());
                return true;
            } else {
                // Reverse the withdrawal if deposit failed
                balance += amount;
                addTransaction("Transfer failed - reversed");
            }
        }
        return false;
    }
    
    // Private helper methods
    private boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }
    
    private void addTransaction(String transaction) {
        String timestamp = java.time.LocalDateTime.now().toString();
        transactionHistory.add(timestamp + ": " + transaction);
    }
    
    public void printTransactionHistory(String enteredPin) {
        if (!validatePin(enteredPin)) {
            System.out.println("Invalid PIN - cannot view transaction history");
            return;
        }
        
        System.out.println("\n=== Transaction History for " + accountNumber + " ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
    
    public void changePin(String oldPin, String newPin) {
        if (!validatePin(oldPin)) {
            System.out.println("Invalid current PIN");
            return;
        }
        
        try {
            setPin(newPin);  // Uses validation from setter
            addTransaction("PIN changed");
            System.out.println("PIN changed successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("PIN change failed: " + e.getMessage());
        }
    }
    
    public void deactivateAccount(String enteredPin) {
        if (!validatePin(enteredPin)) {
            System.out.println("Invalid PIN - cannot deactivate account");
            return;
        }
        
        isActive = false;
        addTransaction("Account deactivated");
        System.out.println("Account deactivated");
    }
}
```

### 2. Employee Management with Encapsulation
```java
public class Employee {
    // Private data - protected from outside access
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private String position;
    private double salary;
    private java.time.LocalDate hireDate;
    private boolean isActive;
    
    // Constructor with validation
    public Employee(String employeeId, String firstName, String lastName, 
                   String email, String department, String position, double salary) {
        setEmployeeId(employeeId);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setDepartment(department);
        setPosition(position);
        setSalary(salary);
        this.hireDate = java.time.LocalDate.now();
        this.isActive = true;
    }
    
    // Getters - provide READ access
    public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public java.time.LocalDate getHireDate() { return hireDate; }
    public boolean isActive() { return isActive; }
    
    // Setters with validation - provide CONTROLLED WRITE access
    private void setEmployeeId(String employeeId) {
        if (employeeId == null || !employeeId.matches("EMP\\d{6}")) {
            throw new IllegalArgumentException("Employee ID must be in format EMP123456");
        }
        this.employeeId = employeeId;
    }
    
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().length() < 2) {
            throw new IllegalArgumentException("First name must be at least 2 characters");
        }
        this.firstName = firstName.trim();
    }
    
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().length() < 2) {
            throw new IllegalArgumentException("Last name must be at least 2 characters");
        }
        this.lastName = lastName.trim();
    }
    
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.toLowerCase().trim();
    }
    
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }
        this.phoneNumber = phoneNumber;
    }
    
    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        this.department = department.trim();
    }
    
    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be empty");
        }
        this.position = position.trim();
    }
    
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
    
    // Business methods
    public void promote(String newPosition, double salaryIncrease) {
        if (!isActive) {
            System.out.println("Cannot promote inactive employee");
            return;
        }
        
        String oldPosition = this.position;
        double oldSalary = this.salary;
        
        setPosition(newPosition);
        setSalary(salary + salaryIncrease);
        
        System.out.println(getFullName() + " promoted from " + oldPosition + 
                          " to " + newPosition + ". Salary increased by $" + 
                          String.format("%.2f", salaryIncrease));
    }
    
    public void transferDepartment(String newDepartment) {
        if (!isActive) {
            System.out.println("Cannot transfer inactive employee");
            return;
        }
        
        String oldDepartment = this.department;
        setDepartment(newDepartment);
        System.out.println(getFullName() + " transferred from " + oldDepartment + 
                          " to " + newDepartment);
    }
    
    public void giveRaise(double percentage) {
        if (!isActive) {
            System.out.println("Cannot give raise to inactive employee");
            return;
        }
        if (percentage <= 0) {
            System.out.println("Raise percentage must be positive");
            return;
        }
        
        double raiseAmount = salary * (percentage / 100);
        setSalary(salary + raiseAmount);
        System.out.println(getFullName() + " received " + percentage + 
                          "% raise. New salary: $" + String.format("%.2f", salary));
    }
    
    public void terminate() {
        if (!isActive) {
            System.out.println("Employee already inactive");
            return;
        }
        
        isActive = false;
        System.out.println(getFullName() + " has been terminated");
    }
    
    public int getYearsOfService() {
        return java.time.Period.between(hireDate, java.time.LocalDate.now()).getYears();
    }
    
    // Private helper methods
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    public void displayEmployeeInfo() {
        System.out.println("=== Employee Information ===");
        System.out.println("ID: " + employeeId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + email);
        System.out.println("Phone: " + (phoneNumber != null ? phoneNumber : "Not provided"));
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Salary: $" + String.format("%.2f", salary));
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Years of Service: " + getYearsOfService());
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
    }
}
```

### 3. Product Inventory with Advanced Encapsulation
```java
public class Product {
    // Private data with different access patterns
    private final String productId;      // Immutable after creation
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
    private boolean isActive;
    private double discountPercentage;
    private java.time.LocalDate createdDate;
    private java.time.LocalDate lastUpdated;
    
    // Constructor
    public Product(String productId, String name, double price, int initialStock, String category) {
        this.productId = validateAndSetProductId(productId);
        setName(name);
        setPrice(price);
        setStockQuantity(initialStock);
        setCategory(category);
        this.description = "";
        this.isActive = true;
        this.discountPercentage = 0.0;
        this.createdDate = java.time.LocalDate.now();
        updateLastModified();
    }
    
    // Getters
    public String getProductId() { return productId; }  // No setter - immutable
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public String getCategory() { return category; }
    public boolean isActive() { return isActive; }
    public double getDiscountPercentage() { return discountPercentage; }
    public java.time.LocalDate getCreatedDate() { return createdDate; }
    public java.time.LocalDate getLastUpdated() { return lastUpdated; }
    
    // Computed getters
    public double getDiscountedPrice() {
        return price * (1 - discountPercentage / 100);
    }
    
    public boolean isInStock() {
        return stockQuantity > 0;
    }
    
    public boolean isLowStock() {
        return stockQuantity > 0 && stockQuantity <= 10;
    }
    
    public String getStockStatus() {
        if (stockQuantity == 0) return "Out of Stock";
        if (stockQuantity <= 10) return "Low Stock";
        if (stockQuantity <= 50) return "Medium Stock";
        return "Well Stocked";
    }
    
    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Product name must be at least 2 characters");
        }
        this.name = name.trim();
        updateLastModified();
    }
    
    public void setDescription(String description) {
        this.description = description != null ? description.trim() : "";
        updateLastModified();
    }
    
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
        updateLastModified();
    }
    
    private void setStockQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = quantity;
        updateLastModified();
    }
    
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category.trim();
        updateLastModified();
    }
    
    public void setDiscountPercentage(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 50) {
            throw new IllegalArgumentException("Discount must be between 0% and 50%");
        }
        this.discountPercentage = discountPercentage;
        updateLastModified();
    }
    
    // Business operations
    public boolean addStock(int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity to add must be positive");
            return false;
        }
        
        stockQuantity += quantity;
        updateLastModified();
        System.out.println("Added " + quantity + " units. New stock: " + stockQuantity);
        return true;
    }
    
    public boolean reduceStock(int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity to reduce must be positive");
            return false;
        }
        if (quantity > stockQuantity) {
            System.out.println("Cannot reduce more than available stock");
            return false;
        }
        
        stockQuantity -= quantity;
        updateLastModified();
        System.out.println("Reduced " + quantity + " units. Remaining stock: " + stockQuantity);
        
        if (isLowStock()) {
            System.out.println("WARNING: " + name + " is running low on stock!");
        }
        
        return true;
    }
    
    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 50) {
            System.out.println("Discount must be between 0% and 50%");
            return;
        }
        
        setDiscountPercentage(percentage);
        System.out.println("Applied " + percentage + "% discount to " + name + 
                          ". New price: $" + String.format("%.2f", getDiscountedPrice()));
    }
    
    public void removeDiscount() {
        setDiscountPercentage(0.0);
        System.out.println("Removed discount from " + name + 
                          ". Price: $" + String.format("%.2f", price));
    }
    
    public void activate() {
        isActive = true;
        updateLastModified();
        System.out.println(name + " activated");
    }
    
    public void deactivate() {
        isActive = false;
        updateLastModified();
        System.out.println(name + " deactivated");
    }
    
    // Private helper methods
    private String validateAndSetProductId(String productId) {
        if (productId == null || !productId.matches("PRD\\d{6}")) {
            throw new IllegalArgumentException("Product ID must be in format PRD123456");
        }
        return productId;
    }
    
    private void updateLastModified() {
        this.lastUpdated = java.time.LocalDate.now();
    }
    
    public void displayProductInfo() {
        System.out.println("=== Product Information ===");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + (description.isEmpty() ? "No description" : description));
        System.out.println("Category: " + category);
        System.out.println("Price: $" + String.format("%.2f", price));
        
        if (discountPercentage > 0) {
            System.out.println("Discount: " + discountPercentage + "%");
            System.out.println("Discounted Price: $" + String.format("%.2f", getDiscountedPrice()));
        }
        
        System.out.println("Stock: " + stockQuantity + " (" + getStockStatus() + ")");
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
        System.out.println("Created: " + createdDate);
        System.out.println("Last Updated: " + lastUpdated);
    }
    
    @Override
    public String toString() {
        return productId + ": " + name + " - $" + String.format("%.2f", getDiscountedPrice()) + 
               " (Stock: " + stockQuantity + ")";
    }
}
```

## Encapsulation Design Patterns

### 1. Data Transfer Object (DTO) Pattern
```java
public class UserDTO {
    private final String userId;      // Immutable
    private final String username;    // Immutable
    private final String email;       // Immutable
    private final boolean isActive;   // Immutable
    
    // Constructor - only way to set values
    public UserDTO(String userId, String username, String email, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
    }
    
    // Only getters - no setters (immutable)
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isActive() { return isActive; }
    
    @Override
    public String toString() {
        return "User{id='" + userId + "', username='" + username + 
               "', email='" + email + "', active=" + isActive + "}";
    }
}
```

### 2. Builder Pattern with Encapsulation
```java
public class Computer {
    private final String brand;
    private final String processor;
    private final int ramSize;
    private final int storageSize;
    private final String graphicsCard;
    private final boolean hasWiFi;
    private final double price;
    
    // Private constructor - can only be called by Builder
    private Computer(ComputerBuilder builder) {
        this.brand = builder.brand;
        this.processor = builder.processor;
        this.ramSize = builder.ramSize;
        this.storageSize = builder.storageSize;
        this.graphicsCard = builder.graphicsCard;
        this.hasWiFi = builder.hasWiFi;
        this.price = builder.price;
    }
    
    // Only getters - immutable object
    public String getBrand() { return brand; }
    public String getProcessor() { return processor; }
    public int getRamSize() { return ramSize; }
    public int getStorageSize() { return storageSize; }
    public String getGraphicsCard() { return graphicsCard; }
    public boolean hasWiFi() { return hasWiFi; }
    public double getPrice() { return price; }
    
    // Static nested Builder class
    public static class ComputerBuilder {
        private String brand;
        private String processor;
        private int ramSize;
        private int storageSize;
        private String graphicsCard = "Integrated";  // Default
        private boolean hasWiFi = true;              // Default
        private double price;
        
        public ComputerBuilder(String brand, String processor, int ramSize, int storageSize) {
            this.brand = brand;
            this.processor = processor;
            this.ramSize = ramSize;
            this.storageSize = storageSize;
        }
        
        public ComputerBuilder graphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        
        public ComputerBuilder hasWiFi(boolean hasWiFi) {
            this.hasWiFi = hasWiFi;
            return this;
        }
        
        public ComputerBuilder price(double price) {
            this.price = price;
            return this;
        }
        
        public Computer build() {
            if (brand == null || processor == null) {
                throw new IllegalStateException("Brand and processor are required");
            }
            if (ramSize <= 0 || storageSize <= 0) {
                throw new IllegalStateException("RAM and storage must be positive");
            }
            return new Computer(this);
        }
    }
    
    public void displaySpecs() {
        System.out.println("=== Computer Specifications ===");
        System.out.println("Brand: " + brand);
        System.out.println("Processor: " + processor);
        System.out.println("RAM: " + ramSize + " GB");
        System.out.println("Storage: " + storageSize + " GB");
        System.out.println("Graphics: " + graphicsCard);
        System.out.println("WiFi: " + (hasWiFi ? "Yes" : "No"));
        System.out.println("Price: $" + String.format("%.2f", price));
    }
}

// Usage:
Computer laptop = new Computer.ComputerBuilder("Dell", "Intel i7", 16, 512)
                             .graphicsCard("NVIDIA GTX 1650")
                             .hasWiFi(true)
                             .price(1299.99)
                             .build();
```

### 3. Singleton Pattern with Encapsulation
```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String connectionString;
    private boolean isConnected;
    
    // Private constructor - prevents external instantiation
    private DatabaseConnection() {
        this.connectionString = "jdbc:mysql://localhost:3306/mydb";
        this.isConnected = false;
    }
    
    // Synchronized method to get single instance
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    // Encapsulated connection methods
    public boolean connect() {
        if (!isConnected) {
            // Simulate connection logic
            isConnected = true;
            System.out.println("Database connected");
            return true;
        }
        System.out.println("Already connected");
        return false;
    }
    
    public boolean disconnect() {
        if (isConnected) {
            isConnected = false;
            System.out.println("Database disconnected");
            return true;
        }
        System.out.println("Already disconnected");
        return false;
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    // Private method - internal use only
    private void validateConnection() {
        if (!isConnected) {
            throw new IllegalStateException("Database not connected");
        }
    }
    
    public void executeQuery(String query) {
        validateConnection();
        System.out.println("Executing query: " + query);
    }
}
```

## Benefits of Encapsulation in Practice

### 1. Data Validation and Integrity
```java
public class Temperature {
    private double celsius;
    
    public void setCelsius(double celsius) {
        if (celsius < -273.15) {  // Absolute zero
            throw new IllegalArgumentException("Temperature cannot be below absolute zero");
        }
        this.celsius = celsius;
    }
    
    public double getCelsius() { return celsius; }
    
    public double getFahrenheit() {
        return (celsius * 9.0 / 5.0) + 32;
    }
    
    public double getKelvin() {
        return celsius + 273.15;
    }
    
    public void setFahrenheit(double fahrenheit) {
        setCelsius((fahrenheit - 32) * 5.0 / 9.0);  // Uses validation
    }
    
    public void setKelvin(double kelvin) {
        setCelsius(kelvin - 273.15);  // Uses validation
    }
}
```

### 2. Audit Trail and Logging
```java
public class SecureDocument {
    private String content;
    private String lastModifiedBy;
    private java.time.LocalDateTime lastModified;
    private List<String> accessLog;
    
    public SecureDocument(String initialContent, String author) {
        this.content = initialContent;
        this.lastModifiedBy = author;
        this.lastModified = java.time.LocalDateTime.now();
        this.accessLog = new ArrayList<>();
        logAccess("Document created by " + author);
    }
    
    public String getContent(String userId) {
        logAccess("Content accessed by " + userId);
        return content;
    }
    
    public void updateContent(String newContent, String userId) {
        logAccess("Content modified by " + userId);
        this.content = newContent;
        this.lastModifiedBy = userId;
        this.lastModified = java.time.LocalDateTime.now();
    }
    
    private void logAccess(String action) {
        String logEntry = java.time.LocalDateTime.now() + ": " + action;
        accessLog.add(logEntry);
    }
    
    public List<String> getAccessLog(String adminUserId) {
        logAccess("Access log viewed by admin " + adminUserId);
        return new ArrayList<>(accessLog);  // Defensive copy
    }
}
```

### 3. Caching and Performance Optimization
```java
public class ExpensiveCalculation {
    private double input;
    private Double cachedResult;  // Nullable to detect if calculated
    private long calculationTime;
    
    public void setInput(double input) {
        if (this.input != input) {
            this.input = input;
            this.cachedResult = null;  // Invalidate cache
        }
    }
    
    public double getResult() {
        if (cachedResult == null) {
            performCalculation();
        }
        return cachedResult;
    }
    
    private void performCalculation() {
        long startTime = System.currentTimeMillis();
        
        // Simulate expensive calculation
        try {
            Thread.sleep(1000);  // Simulate 1 second calculation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        cachedResult = Math.pow(input, 10) + Math.sqrt(input);
        calculationTime = System.currentTimeMillis() - startTime;
        System.out.println("Calculation completed in " + calculationTime + "ms");
    }
    
    public long getLastCalculationTime() {
        return calculationTime;
    }
    
    public boolean isResultCached() {
        return cachedResult != null;
    }
}
```

## Common Encapsulation Mistakes and Solutions

### 1. Exposing Mutable Objects
```java
// BAD - Exposes internal mutable state
class BadClass {
    private List<String> items = new ArrayList<>();
    
    public List<String> getItems() {
        return items;  // Returns reference to internal list!
    }
}

// BAD USAGE:
BadClass bad = new BadClass();
List<String> items = bad.getItems();
items.add("External modification!");  // Breaks encapsulation!

// GOOD - Returns defensive copy
class GoodClass {
    private List<String> items = new ArrayList<>();
    
    public List<String> getItems() {
        return new ArrayList<>(items);  // Returns copy
    }
    
    public void addItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            items.add(item);
        }
    }
    
    public boolean removeItem(String item) {
        return items.remove(item);
    }
    
    public int getItemCount() {
        return items.size();
    }
}
```

### 2. Insufficient Validation in Setters
```java
// BAD - No validation
class BadAge {
    private int age;
    
    public void setAge(int age) {
        this.age = age;  // Allows negative or impossible values
    }
}

// GOOD - Proper validation
class GoodAge {
    private int age;
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150, got: " + age);
        }
        this.age = age;
    }
}
```

### 3. Public Fields Instead of Encapsulation
```java
// BAD - Public fields
class BadStudent {
    public String name;     // Anyone can modify
    public double gpa;      // No validation possible
    public List<String> courses = new ArrayList<>();  // Dangerous!
}

// GOOD - Proper encapsulation
class GoodStudent {
    private String name;
    private double gpa;
    private List<String> courses = new ArrayList<>();
    
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }
    
    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        }
    }
    
    public String getName() { return name; }
    public double getGpa() { return gpa; }
    
    public void addCourse(String course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }
    
    public List<String> getCourses() {
        return new ArrayList<>(courses);  // Defensive copy
    }
}
```

## Interview Questions & Answers

**Q1: What is encapsulation? Why is it important?**
**A:** Encapsulation is bundling data and methods together while hiding internal implementation. Benefits:
- Data protection and validation
- Code maintainability
- Flexibility to change implementation
- Security and controlled access

**Q2: How do you achieve encapsulation in Java?**
**A:** 
- Make instance variables private
- Provide public getter/setter methods
- Validate data in setter methods
- Hide implementation details through private methods

**Q3: What's the difference between encapsulation and data hiding?**
**A:**
- **Data hiding**: Making data private (part of encapsulation)
- **Encapsulation**: Complete concept including data bundling, hiding, and controlled access through methods

**Q4: Why should instance variables be private?**
**A:** 
- Prevents unauthorized access and modification
- Allows validation in setter methods
- Enables future changes without breaking external code
- Maintains object integrity

**Q5: What are getter and setter methods? Are they always necessary?**
**A:**
- **Getters**: Provide read access to private variables
- **Setters**: Provide controlled write access with validation
- Not always necessary - some fields might be read-only, write-only, or calculated

**Q6: How does encapsulation support maintainability?**
**A:** 
- Internal implementation can change without affecting external code
- Validation logic centralized in setter methods
- Dependencies reduced through controlled interfaces
- Easier debugging and testing

**Q7: What is defensive copying and when should it be used?**
**A:** Creating copies of mutable objects when returning them from getters. Use when:
- Returning collections or arrays
- Returning mutable objects
- Want to prevent external modifications to internal state

## Complete Example Program

```java
import java.time.LocalDateTime;
import java.util.*;

/**
 * Comprehensive Encapsulation demonstration
 * Online Shopping Cart System
 */

// Product class with encapsulation
class Product {
    private final String productId;
    private String name;
    private double price;
    private String category;
    private boolean inStock;
    
    public Product(String productId, String name, double price, String category) {
        this.productId = validateProductId(productId);
        setName(name);
        setPrice(price);
        setCategory(category);
        this.inStock = true;
    }
    
    private String validateProductId(String id) {
        if (id == null || !id.matches("P\\d{5}")) {
            throw new IllegalArgumentException("Product ID must be in format P12345");
        }
        return id;
    }
    
    // Getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isInStock() { return inStock; }
    
    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Product name must be at least 2 characters");
        }
        this.name = name.trim();
    }
    
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }
    
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category.trim();
    }
    
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    
    @Override
    public String toString() {
        return name + " (" + productId + ") - $" + String.format("%.2f", price) + 
               " [" + (inStock ? "In Stock" : "Out of Stock") + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(productId, product.productId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}

// Cart item with encapsulation
class CartItem {
    private final Product product;
    private int quantity;
    private final LocalDateTime addedTime;
    
    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.product = product;
        setQuantity(quantity);
        this.addedTime = LocalDateTime.now();
    }
    
    // Getters
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getAddedTime() { return addedTime; }
    
    // Calculated getters
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
    
    public String getProductName() {
        return product.getName();
    }
    
    // Setter with validation
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }
    
    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity += amount;
    }
    
    public boolean decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount >= quantity) {
            return false;  // Cannot decrease below 1
        }
        quantity -= amount;
        return true;
    }
    
    @Override
    public String toString() {
        return product.getName() + " x" + quantity + " = $" + 
               String.format("%.2f", getSubtotal());
    }
}

// Shopping cart with complete encapsulation
class ShoppingCart {
    private final String cartId;
    private final String customerId;
    private final Map<String, CartItem> items;  // ProductId -> CartItem
    private final LocalDateTime createdTime;
    private LocalDateTime lastModified;
    private boolean isActive;
    private double discountPercentage;
    
    public ShoppingCart(String cartId, String customerId) {
        this.cartId = validateCartId(cartId);
        this.customerId = validateCustomerId(customerId);
        this.items = new HashMap<>();
        this.createdTime = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
        this.isActive = true;
        this.discountPercentage = 0.0;
    }
    
    // Validation methods
    private String validateCartId(String cartId) {
        if (cartId == null || cartId.trim().isEmpty()) {
            throw new IllegalArgumentException("Cart ID cannot be empty");
        }
        return cartId.trim();
    }
    
    private String validateCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        return customerId.trim();
    }
    
    // Getters
    public String getCartId() { return cartId; }
    public String getCustomerId() { return customerId; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public LocalDateTime getLastModified() { return lastModified; }
    public boolean isActive() { return isActive; }
    public double getDiscountPercentage() { return discountPercentage; }
    
    // Calculated getters
    public int getItemCount() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }
    
    public int getUniqueItemCount() {
        return items.size();
    }
    
    public double getSubtotal() {
        return items.values().stream().mapToDouble(CartItem::getSubtotal).sum();
    }
    
    public double getDiscountAmount() {
        return getSubtotal() * (discountPercentage / 100);
    }
    
    public double getTotal() {
        return getSubtotal() - getDiscountAmount();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    // Defensive copy for items
    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }
    
    public Set<String> getProductIds() {
        return new HashSet<>(items.keySet());
    }
    
    // Business methods
    public boolean addProduct(Product product, int quantity) {
        if (!isActive) {
            System.out.println("Cannot add to inactive cart");
            return false;
        }
        if (product == null) {
            System.out.println("Product cannot be null");
            return false;
        }
        if (!product.isInStock()) {
            System.out.println("Product is out of stock: " + product.getName());
            return false;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be positive");
            return false;
        }
        
        String productId = product.getProductId();
        
        if (items.containsKey(productId)) {
            // Update existing item
            CartItem existingItem = items.get(productId);
            existingItem.increaseQuantity(quantity);
            System.out.println("Updated quantity for " + product.getName() + 
                             ". New quantity: " + existingItem.getQuantity());
        } else {
            // Add new item
            CartItem newItem = new CartItem(product, quantity);
            items.put(productId, newItem);
            System.out.println("Added " + product.getName() + " x" + quantity + " to cart");
        }
        
        updateLastModified();
        return true;
    }
    
    public boolean removeProduct(String productId) {
        if (!isActive) {
            System.out.println("Cannot remove from inactive cart");
            return false;
        }
        
        CartItem removedItem = items.remove(productId);
        if (removedItem != null) {
            System.out.println("Removed " + removedItem.getProductName() + " from cart");
            updateLastModified();
            return true;
        } else {
            System.out.println("Product not found in cart: " + productId);
            return false;
        }
    }
    
    public boolean updateQuantity(String productId, int newQuantity) {
        if (!isActive) {
            System.out.println("Cannot update inactive cart");
            return false;
        }
        
        CartItem item = items.get(productId);
        if (item == null) {
            System.out.println("Product not found in cart: " + productId);
            return false;
        }
        
        if (newQuantity <= 0) {
            return removeProduct(productId);
        } else {
            item.setQuantity(newQuantity);
            System.out.println("Updated quantity for " + item.getProductName() + 
                             " to " + newQuantity);
            updateLastModified();
            return true;
        }
    }
    
    public void applyDiscount(double percentage) {
        if (!isActive) {
            System.out.println("Cannot apply discount to inactive cart");
            return;
        }
        if (percentage < 0 || percentage > 50) {
            System.out.println("Discount must be between 0% and 50%");
            return;
        }
        
        this.discountPercentage = percentage;
        updateLastModified();
        System.out.println("Applied " + percentage + "% discount to cart");
    }
    
    public void removeDiscount() {
        this.discountPercentage = 0.0;
        updateLastModified();
        System.out.println("Removed discount from cart");
    }
    
    public void clearCart() {
        if (!isActive) {
            System.out.println("Cart is already inactive");
            return;
        }
        
        items.clear();
        discountPercentage = 0.0;
        updateLastModified();
        System.out.println("Cart cleared");
    }
    
    public void deactivateCart() {
        isActive = false;
        updateLastModified();
        System.out.println("Cart deactivated");
    }
    
    public boolean hasProduct(String productId) {
        return items.containsKey(productId);
    }
    
    public CartItem getCartItem(String productId) {
        return items.get(productId);  // Returns null if not found
    }
    
    // Private helper method
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
    
    public void displayCart() {
        System.out.println("\n=== SHOPPING CART ===");
        System.out.println("Cart ID: " + cartId);
        System.out.println("Customer: " + customerId);
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
        System.out.println("Created: " + createdTime);
        System.out.println("Last Modified: " + lastModified);
        
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        
        System.out.println("\nItems:");
        int itemNumber = 1;
        for (CartItem item : items.values()) {
            System.out.println(itemNumber + ". " + item);
            itemNumber++;
        }
        
        System.out.println("\n--- Cart Summary ---");
        System.out.println("Unique Items: " + getUniqueItemCount());
        System.out.println("Total Items: " + getItemCount());
        System.out.println("Subtotal: $" + String.format("%.2f", getSubtotal()));
        
        if (discountPercentage > 0) {
            System.out.println("Discount (" + discountPercentage + "%): -$" + 
                             String.format("%.2f", getDiscountAmount()));
        }
        
        System.out.println("Total: $" + String.format("%.2f", getTotal()));
    }
    
    @Override
    public String toString() {
        return "Cart " + cartId + " (" + getItemCount() + " items, $" + 
               String.format("%.2f", getTotal()) + ")";
    }
}

// Main demo class
public class EncapsulationExample {
    public static void main(String[] args) {
        System.out.println("=== ENCAPSULATION DEMONSTRATION ===");
        
        demonstrateBasicEncapsulation();
        demonstrateDataValidation();
        demonstrateShoppingCartSystem();
    }
    
    public static void demonstrateBasicEncapsulation() {
        System.out.println("\n--- BASIC ENCAPSULATION ---");
        
        // Create products with encapsulation
        try {
            Product laptop = new Product("P10001", "Gaming Laptop", 1299.99, "Electronics");
            Product mouse = new Product("P10002", "Wireless Mouse", 29.99, "Accessories");
            
            // Show encapsulated access
            System.out.println("Product created: " + laptop);
            System.out.println("Product price: $" + laptop.getPrice());
            
            // Modify through controlled interface
            laptop.setPrice(1199.99);
            System.out.println("Updated price: $" + laptop.getPrice());
            
            // Demonstrate validation
            try {
                laptop.setPrice(-100);  // This will fail
            } catch (IllegalArgumentException e) {
                System.out.println("Validation caught: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void demonstrateDataValidation() {
        System.out.println("\n--- DATA VALIDATION IN ENCAPSULATION ---");
        
        try {
            // Valid product creation
            Product validProduct = new Product("P10003", "Valid Product", 99.99, "Category");
            System.out.println("Valid product created successfully");
            
            // Test various validation scenarios
            System.out.println("\nTesting validation...");
            
            // Invalid product ID
            try {
                Product invalidId = new Product("INVALID", "Product", 50.0, "Category");
            } catch (IllegalArgumentException e) {
                System.out.println("Caught invalid ID: " + e.getMessage());
            }
            
            // Invalid name
            try {
                Product invalidName = new Product("P10004", "", 50.0, "Category");
            } catch (IllegalArgumentException e) {
                System.out.println("Caught invalid name: " + e.getMessage());
            }
            
            // Invalid price
            try {
                validProduct.setPrice(-50);
            } catch (IllegalArgumentException e) {
                System.out.println("Caught invalid price: " + e.getMessage());
            }
            
            System.out.println("All validations working correctly!");
            
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    
    public static void demonstrateShoppingCartSystem() {
        System.out.println("\n--- SHOPPING CART SYSTEM ---");
        
        // Create products
        Product laptop = new Product("P20001", "Gaming Laptop", 1299.99, "Electronics");
        Product mouse = new Product("P20002", "Wireless Mouse", 29.99, "Accessories");
        Product keyboard = new Product("P20003", "Mechanical Keyboard", 149.99, "Accessories");
        Product monitor = new Product("P20004", "4K Monitor", 399.99, "Electronics");
        
        // Create shopping cart
        ShoppingCart cart = new ShoppingCart("CART001", "CUSTOMER123");
        
        // Demonstrate cart operations
        System.out.println("Created new shopping cart");
        cart.displayCart();
        
        // Add products
        System.out.println("\n--- Adding Products ---");
        cart.addProduct(laptop, 1);
        cart.addProduct(mouse, 2);
        cart.addProduct(keyboard, 1);
        
        cart.displayCart();
        
        // Add more of existing product
        System.out.println("\n--- Adding More Mice ---");
        cart.addProduct(mouse, 1);  // Should update existing item
        
        // Update quantities
        System.out.println("\n--- Updating Quantities ---");
        cart.updateQuantity("P20002", 5);  // Update mouse quantity
        
        cart.displayCart();
        
        // Apply discount
        System.out.println("\n--- Applying Discount ---");
        cart.applyDiscount(15.0);  // 15% discount
        
        cart.displayCart();
        
        // Add another product
        System.out.println("\n--- Adding Monitor ---");
        cart.addProduct(monitor, 1);
        
        cart.displayCart();
        
        // Remove a product
        System.out.println("\n--- Removing Keyboard ---");
        cart.removeProduct("P20003");
        
        cart.displayCart();
        
        // Demonstrate encapsulation protection
        System.out.println("\n--- Testing Encapsulation Protection ---");
        
        // Cannot modify cart items directly
        List<CartItem> items = cart.getItems();
        System.out.println("Retrieved cart items (defensive copy): " + items.size() + " items");
        
        // Modifying the returned list doesn't affect the original
        items.clear();
        System.out.println("Cleared retrieved list, but original cart unaffected:");
        cart.displayCart();
        
        // Test validation in cart operations
        System.out.println("\n--- Testing Cart Validations ---");
        
        Product outOfStock = new Product("P20005", "Out of Stock Item", 99.99, "Test");
        outOfStock.setInStock(false);
        
        cart.addProduct(outOfStock, 1);  // Should fail
        cart.addProduct(laptop, -5);     // Should fail
        cart.applyDiscount(-10);         // Should fail
        cart.applyDiscount(60);          // Should fail
        
        // Final cart state
        System.out.println("\n--- Final Cart State ---");
        cart.displayCart();
        
        // Deactivate cart
        System.out.println("\n--- Deactivating Cart ---");
        cart.deactivateCart();
        cart.addProduct(laptop, 1);  // Should fail on inactive cart
        
        System.out.println("\n=== ENCAPSULATION DEMO COMPLETE ===");
        System.out.println("Key points demonstrated:");
        System.out.println("1. Private fields with controlled access");
        System.out.println("2. Data validation in setters");
        System.out.println("3. Defensive copying for collections");
        System.out.println("4. Business logic encapsulation");
        System.out.println("5. State management and consistency");
    }
}
```

## Key Takeaways

1. **Encapsulation bundles data and methods** while controlling access
2. **Private fields with public getters/setters** provide controlled access
3. **Validation in setters** ensures data integrity
4. **Defensive copying** prevents external modification of internal collections
5. **Hide implementation details** through private methods
6. **Immutable objects** (final fields, no setters) provide ultimate protection
7. **Business logic encapsulation** keeps related operations together
8. **State management** maintains object consistency
9. **Interface design** exposes only necessary functionality
10. **Security through controlled access** protects sensitive data

---

*Remember: Encapsulation is like a protective shell - it keeps the important stuff safe inside while providing a clean, controlled interface for the outside world to interact with!*