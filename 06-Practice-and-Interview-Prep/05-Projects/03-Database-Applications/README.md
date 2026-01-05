# Database Applications - Project Guides

Database-driven applications connect Java programs to persistent relational databases using JDBC. These projects teach you how to design database schemas, write SQL queries, implement CRUD operations, handle connections safely, and build transaction-based systems. These are essential skills for enterprise-level Java development.

---

## 1. Student Database Management System (JDBC)

### Project Description

Build a complete student information system that uses a relational database for persistent data storage. This project introduces JDBC fundamentals, SQL operations, and database integration patterns.

**Core Features:**
- Student registration and profile management in database
- Course management and enrollment
- Grade entry and management
- GPA and transcript generation from database
- Student search and filtering
- Batch operations (import students from file)
- Attendance tracking in database
- Report generation with database queries
- Data backup and export
- User authentication (optional)

### Required Concepts
- JDBC fundamentals (DriverManager, Connection, Statement, ResultSet)
- SQL queries (SELECT, INSERT, UPDATE, DELETE, JOIN)
- Connection management and resource handling
- Prepared statements to prevent SQL injection
- Transaction management
- Database schema design and relationships
- Exception handling for database operations
- Connection pooling basics

### Database Schema

```sql
-- Students Table
CREATE TABLE students (
    student_id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    enrollment_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- Courses Table
CREATE TABLE courses (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,
    instructor_id VARCHAR(10),
    max_capacity INT,
    description TEXT
);

-- Enrollments Table
CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(10) NOT NULL,
    course_id VARCHAR(10) NOT NULL,
    enrollment_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    UNIQUE(student_id, course_id)
);

-- Grades Table
CREATE TABLE grades (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    marks DECIMAL(5, 2),
    grade CHAR(2),
    grade_points DECIMAL(3, 2),
    exam_date DATE,
    FOREIGN KEY (enrollment_id) REFERENCES enrollments(enrollment_id)
);

-- Attendance Table
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(20),
    FOREIGN KEY (enrollment_id) REFERENCES enrollments(enrollment_id)
);

-- Create indexes for performance
CREATE INDEX idx_student_email ON students(email);
CREATE INDEX idx_enrollment_student ON enrollments(student_id);
CREATE INDEX idx_enrollment_course ON enrollments(course_id);
CREATE INDEX idx_grade_enrollment ON grades(enrollment_id);
```

### Architecture Overview

```
StudentDatabaseSystem
├── DatabaseConnection (class)
│   ├── connection (Connection)
│   ├── Methods: getConnection(), closeConnection(), testConnection()
│   └── Singleton pattern for connection management
├── Student (class)
│   ├── studentId, firstName, lastName, email, phone, dateOfBirth, enrollmentDate, status
│   └── Methods: getters, setters, toString()
├── Course (class)
│   ├── courseId, courseName, credits, instructorId, maxCapacity, description
│   └── Methods: getters, setters, toString()
├── Grade (class)
│   ├── gradeId, enrollmentId, marks, grade, gradePoints, examDate
│   └── Methods: calculateGrade(), calculateGradePoints()
├── StudentDAO (Data Access Object)
│   ├── Methods: addStudent(), updateStudent(), deleteStudent()
│   ├── Methods: findStudentById(), findAllStudents(), searchStudents()
│   └── Methods: getStudentEnrollments(), getStudentTranscript()
├── CourseDAO
│   ├── Methods: addCourse(), updateCourse(), deleteCourse()
│   ├── Methods: findCourseById(), findAllCourses()
│   └── Methods: getEnrolledStudents(), getCapacityInfo()
├── EnrollmentDAO
│   ├── Methods: enrollStudent(), withdrawStudent()
│   ├── Methods: findEnrollmentById(), findStudentEnrollments()
│   └── Methods: getEnrollmentStatus()
├── GradeDAO
│   ├── Methods: addGrade(), updateGrade(), deleteGrade()
│   ├── Methods: findGradeById(), getStudentGrades()
│   └── Methods: calculateGPA(), generateTranscript()
├── AttendanceDAO
│   ├── Methods: markAttendance(), getAttendanceRecord()
│   └── Methods: calculateAttendancePercentage()
├── ReportGenerator (class)
│   ├── Methods: generateTranscript(), generateEnrollmentReport()
│   ├── Methods: generatePerformanceReport(), generateAttendanceReport()
│   └── Methods: exportToCSV(), exportToPDF()
└── StudentManagementApplication (console interface)
    └── Menu-driven JDBC operations
```

### JDBC Implementation Notes

**Key Patterns:**
1. **DAO Pattern:** Separate data access logic from business logic
2. **Prepared Statements:** Always use prepared statements to prevent SQL injection
3. **Resource Management:** Close ResultSet, Statement, and Connection in finally blocks
4. **Error Handling:** Catch SQLException and handle appropriately
5. **Connection Pooling:** Consider implementing connection pooling for scalability

**Sample JDBC Operations:**

```java
// Connection Management
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// StudentDAO Example
public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            // ... set other parameters
            pstmt.executeUpdate();
        }
    }
    
    public List<Student> searchStudents(String searchTerm) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE first_name LIKE ? OR last_name LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                // ... map other fields
                students.add(student);
            }
        }
        return students;
    }
    
    public double getStudentGPA(String studentId) throws SQLException {
        String sql = "SELECT AVG(grade_points) as gpa FROM grades g " +
                     "INNER JOIN enrollments e ON g.enrollment_id = e.enrollment_id " +
                     "WHERE e.student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("gpa");
            }
        }
        return 0.0;
    }
}
```

### Implementation Steps

**Phase 1: Database Design (Days 1)**
1. Design database schema with all tables and relationships
2. Create MySQL database and tables
3. Create appropriate indexes for performance
4. Set up sample data for testing

**Phase 2: JDBC Connection Setup (Days 2)**
1. Create `DatabaseConnection` class for connection management
2. Test database connectivity
3. Implement exception handling for connection failures
4. Create utility methods for common operations

**Phase 3: DAO Implementation (Days 3-4)**
1. Implement `StudentDAO` with CRUD operations
2. Implement `CourseDAO` for course management
3. Implement `EnrollmentDAO` for course enrollment
4. Implement `GradeDAO` with grade management and GPA calculation
5. Implement `AttendanceDAO` for attendance tracking

**Phase 4: Business Logic and Reports (Days 5-6)**
1. Create `ReportGenerator` for transcript and report generation
2. Implement complex queries for analytics
3. Add search and filter functionality
4. Create export functionality (CSV)
5. Implement data validation

**Phase 5: User Interface (Days 7-9)**
1. Build menu-driven console interface
2. Implement all CRUD operations through menu
3. Add search and reporting features
4. Test all database operations
5. Performance testing with sample data

### Sample Input/Output

```
=== STUDENT DATABASE MANAGEMENT SYSTEM ===
1. Register Student
2. Enroll in Course
3. Enter Grades
4. View Transcript
5. View GPA
6. Generate Report
7. Search Student
8. Update Student Info
9. Exit

Enter choice: 1
Enter student ID: STU001
Enter first name: Virat
Enter last name: Kohli
Enter email: virat@university.edu
Enter phone: 9876543210
Enter date of birth (YYYY-MM-DD): 2002-11-05

Student registered successfully!

Enter choice: 2
Enter student ID: STU001
Enter course ID: CS101

Student enrolled in Java Programming successfully!

Enter choice: 3
Enter student ID: STU001
Select course:
1. CS101 - Java Programming
2. CS102 - Data Structures

Select (1): 1
Enter marks (0-100): 87

Grade assigned: A (Grade Points: 4.0)

Enter choice: 5
=== GPA CALCULATION ===
Student: Virat Kohli (STU001)

Course                  | Marks | Grade | Credits | Grade Points
Java Programming        | 87    | A     | 4       | 16.0
Data Structures         | 92    | A+    | 3       | 12.0
Database Design         | 85    | A     | 3       | 12.0
-----------------------------------------------------------
Total Credits: 10
Total Grade Points: 40.0
CGPA: 4.0

Enter choice: 4
=== TRANSCRIPT: STU001 ===
Name: Virat Kohli
Student ID: STU001
Enrollment Date: 2024-01-10

Courses and Grades:
Course                  | Credits | Marks | Grade
Java Programming        | 4       | 87    | A
Data Structures         | 3       | 92    | A+
Database Design         | 3       | 85    | A

Current CGPA: 4.0
Status: EXCELLENT STANDING

Enter choice: 6
Report generated successfully! Saved as: transcript_STU001.csv

Enter choice: 9
Database connection closed.
Thank you!
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Database Complexity |
|-----------|-----------------|------------------|---------------------|
| Add Student | O(1) | O(1) | Single INSERT |
| Find Student | O(log n) | O(1) | Single SELECT with index |
| Get Enrollments | O(m) | O(m) | m = enrollments, JOIN query |
| Calculate GPA | O(m) | O(1) | Aggregate function |
| Generate Transcript | O(m) | O(m) | m = courses, JOIN query |
| Search Students | O(n) | O(k) | Full table scan or index |

### Extensions and Challenges

**Intermediate Extensions:**
- Add course prerequisites validation
- Implement semester-wise tracking
- Add transcript certification feature
- Create academic performance analytics
- Implement student standing rules (probation, suspension)
- Add bulk student import from CSV

**Advanced Challenges:**
- Implement database transactions for concurrent operations
- Add connection pooling (HikariCP)
- Implement caching layer for frequently accessed data
- Create REST API using Spring Boot
- Add role-based authentication (Admin, Instructor, Student)
- Implement database migration system
- Add data archiving for historical records
- Create web dashboard with JSP/Spring

---

## 2. E-Commerce Database System (JDBC)

### Project Description

Build a complete e-commerce system with products, customers, orders, and payment tracking using JDBC. This project involves complex queries, transactions, and multi-table relationships.

**Core Features:**
- Product catalog management
- Customer account management
- Shopping cart persistence
- Order placement and tracking
- Payment processing and history
- Inventory management with stock tracking
- Order history and analytics
- Product reviews and ratings
- Discount and coupon management
- Report generation

### Required Concepts
- Complex SQL queries (JOINs, aggregations, subqueries)
- JDBC transactions (commit, rollback)
- Prepared statements for security
- Connection management
- Batch operations for efficiency
- Database constraints and relationships
- Trigger concepts (optional)
- Complex data retrieval and mapping

### Database Schema

```sql
-- Customers Table
CREATE TABLE customers (
    customer_id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    phone VARCHAR(20),
    created_date DATE NOT NULL,
    last_login DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- Products Table
CREATE TABLE products (
    product_id VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    category VARCHAR(50),
    description TEXT,
    unit_price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    reorder_level INT,
    supplier_id VARCHAR(10),
    created_date DATE NOT NULL,
    last_updated DATE
);

-- Orders Table
CREATE TABLE orders (
    order_id VARCHAR(15) PRIMARY KEY,
    customer_id VARCHAR(10) NOT NULL,
    order_date DATETIME NOT NULL,
    delivery_date DATE,
    total_amount DECIMAL(12, 2) NOT NULL,
    discount_amount DECIMAL(10, 2) DEFAULT 0,
    tax_amount DECIMAL(10, 2),
    status VARCHAR(20) DEFAULT 'PENDING',
    shipping_address TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Order Items Table
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(15) NOT NULL,
    product_id VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    line_total DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Payments Table
CREATE TABLE payments (
    payment_id VARCHAR(15) PRIMARY KEY,
    order_id VARCHAR(15) NOT NULL,
    payment_method VARCHAR(50),
    amount DECIMAL(12, 2) NOT NULL,
    payment_date DATETIME,
    status VARCHAR(20) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- Shopping Cart Table
CREATE TABLE shopping_carts (
    cart_id VARCHAR(15) PRIMARY KEY,
    customer_id VARCHAR(10) NOT NULL,
    created_date DATE,
    last_updated DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Cart Items Table
CREATE TABLE cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id VARCHAR(15) NOT NULL,
    product_id VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    added_date DATETIME,
    FOREIGN KEY (cart_id) REFERENCES shopping_carts(cart_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Coupons Table
CREATE TABLE coupons (
    coupon_id VARCHAR(10) PRIMARY KEY,
    coupon_code VARCHAR(50) UNIQUE NOT NULL,
    discount_percentage DECIMAL(5, 2),
    discount_amount DECIMAL(10, 2),
    minimum_purchase DECIMAL(10, 2),
    valid_from DATE NOT NULL,
    valid_until DATE NOT NULL,
    max_usage INT,
    current_usage INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- Reviews Table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(10) NOT NULL,
    customer_id VARCHAR(10) NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    review_text TEXT,
    review_date DATE,
    helpful_count INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Create indexes
CREATE INDEX idx_customer_email ON customers(email);
CREATE INDEX idx_order_customer ON orders(customer_id);
CREATE INDEX idx_order_date ON orders(order_date);
CREATE INDEX idx_product_category ON products(category);
CREATE INDEX idx_cart_customer ON shopping_carts(customer_id);
```

### Architecture Overview

```
ECommerceSystem
├── DatabaseConnection (singleton)
├── Customer (entity)
├── Product (entity)
├── Order (entity with order items)
├── Payment (entity)
├── ShoppingCart (entity)
├── Coupon (entity)
├── Review (entity)
├── CustomerDAO
│   ├── Methods: registerCustomer(), login(), updateProfile()
│   ├── Methods: findCustomerById(), getCustomerOrders()
│   └── Methods: getCustomerSpending(), getTopCustomers()
├── ProductDAO
│   ├── Methods: addProduct(), updateProduct(), deleteProduct()
│   ├── Methods: findProductById(), getAllProducts(), searchProducts()
│   ├── Methods: findByCategory(), getTopRatedProducts()
│   └── Methods: updateStock(), checkStockAvailability()
├── OrderDAO
│   ├── Methods: createOrder() [with transaction], updateOrderStatus()
│   ├── Methods: findOrderById(), getCustomerOrders()
│   ├── Methods: getOrderByDate(), getOrderStatus()
│   └── Methods: generateInvoice(), cancelOrder()
├── PaymentDAO
│   ├── Methods: processPayment(), getPaymentStatus()
│   ├── Methods: getPaymentHistory(), refund()
│   └── Methods: getRevenueReport()
├── ShoppingCartDAO
│   ├── Methods: createCart(), addItem(), removeItem()
│   ├── Methods: updateQuantity(), clearCart()
│   └── Methods: checkoutCart(), calculateTotal()
├── CouponDAO
│   ├── Methods: validateCoupon(), applyCoupon()
│   ├── Methods: calculateDiscount(), updateUsage()
│   └── Methods: getAvailableCoupons()
├── ReviewDAO
│   ├── Methods: addReview(), updateReview(), deleteReview()
│   ├── Methods: getProductReviews(), getAverageRating()
│   └── Methods: getHelpfulReviews()
├── OrderManager (business logic with transactions)
│   ├── Methods: placeOrder() [transaction]
│   ├── Methods: processOrderPayment() [transaction]
│   └── Methods: shipOrder() [transaction]
├── ReportGenerator
│   ├── Methods: generateSalesReport(), generateProductReport()
│   ├── Methods: generateCustomerReport(), generateRevenueAnalysis()
│   └── Methods: exportToCSV()
└── ECommerceApplication (console interface)
    └── Menu system for all operations
```

### JDBC Implementation Highlights

**Transaction Management:**

```java
public class OrderManager {
    public String placeOrder(String customerId, List<CartItem> cartItems, 
                            String couponCode) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try {
            conn.setAutoCommit(false);
            
            // Generate order ID
            String orderId = generateOrderId();
            double totalAmount = calculateTotal(cartItems);
            
            // Apply coupon if provided
            double discount = 0;
            if (couponCode != null) {
                discount = applyCoupon(conn, couponCode, totalAmount);
            }
            
            // Create order
            OrderDAO orderDAO = new OrderDAO();
            Order order = new Order();
            order.setOrderId(orderId);
            order.setCustomerId(customerId);
            order.setTotalAmount(totalAmount - discount);
            order.setStatus("PENDING");
            orderDAO.addOrder(conn, order);
            
            // Add order items
            for (CartItem item : cartItems) {
                orderDAO.addOrderItem(conn, orderId, item);
            }
            
            // Update product stock
            ProductDAO productDAO = new ProductDAO();
            for (CartItem item : cartItems) {
                productDAO.reduceStock(conn, item.getProductId(), 
                                      item.getQuantity());
            }
            
            // Clear shopping cart
            ShoppingCartDAO cartDAO = new ShoppingCartDAO();
            cartDAO.clearCart(conn, customerId);
            
            conn.commit();
            return orderId;
            
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

// Batch Operations Example
public void bulkAddProducts(List<Product> products) throws SQLException {
    String sql = "INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        conn.setAutoCommit(false);
        
        for (Product product : products) {
            pstmt.setString(1, product.getProductId());
            // ... set other parameters
            pstmt.addBatch();
        }
        
        pstmt.executeBatch();
        conn.commit();
        
    } catch (SQLException e) {
        conn.rollback();
        throw e;
    } finally {
        conn.setAutoCommit(true);
    }
}

// Complex Query Example
public List<Product> getTopSellingProducts(int limit) throws SQLException {
    String sql = "SELECT p.*, SUM(oi.quantity) as total_sold " +
                 "FROM products p " +
                 "INNER JOIN order_items oi ON p.product_id = oi.product_id " +
                 "GROUP BY p.product_id " +
                 "ORDER BY total_sold DESC " +
                 "LIMIT ?";
    
    List<Product> products = new ArrayList<>();
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, limit);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Map result set to Product object
        }
    }
    return products;
}
```

### Implementation Steps

**Phase 1: Database Setup (Days 1-2)**
1. Design comprehensive database schema
2. Create MySQL database and all tables
3. Set up relationships and constraints
4. Create necessary indexes
5. Insert sample test data

**Phase 2: Entity Classes (Days 3)**
1. Create entity classes for all tables
2. Implement getters/setters
3. Add toString() methods for debugging
4. Create helper methods where needed

**Phase 3: JDBC DAO Implementation (Days 4-6)**
1. Implement `CustomerDAO` with registration, login, search
2. Implement `ProductDAO` with inventory management
3. Implement `OrderDAO` with order creation and status tracking
4. Implement `PaymentDAO` with transaction handling
5. Implement `ShoppingCartDAO` with cart operations
6. Implement `CouponDAO` and `ReviewDAO`

**Phase 4: Business Logic and Transactions (Days 7-8)**
1. Create `OrderManager` with transaction-based order placement
2. Implement payment processing with rollback capability
3. Create report generation functionality
4. Add order fulfillment workflow
5. Implement analytics queries

**Phase 5: User Interface (Days 9-12)**
1. Build comprehensive menu system
2. Implement customer operations (registration, login, browsing)
3. Implement shopping cart operations
4. Build checkout and payment flow
5. Create order tracking interface
6. Generate reports and analytics
7. Test all operations including edge cases

### Sample Input/Output

```
=== E-COMMERCE STORE ===
1. Register Customer
2. Login
3. Browse Products
4. View Product Details
5. Add to Cart
6. View Cart
7. Apply Coupon
8. Checkout
9. View Order History
10. Track Order
11. Write Review
12. View Dashboard
13. Exit

Enter choice: 1
Enter customer email: user@example.com
Enter password: ****
Enter full name: John Smith
Enter phone: 555-1234

Customer registered successfully! ID: CUST001

Enter choice: 2
Enter email: user@example.com
Enter password: ****
Login successful!

Enter choice: 3
=== PRODUCT CATALOG ===
ID     | Name                | Category      | Price    | Stock | Rating
P001   | Laptop              | Electronics   | $1200.00 | 5     | 4.5/5
P002   | Wireless Mouse      | Accessories   | $25.00   | 50    | 4.2/5
P003   | USB-C Cable         | Accessories   | $15.00   | 100   | 4.8/5

Enter choice: 5
Enter product ID: P001
Enter quantity: 1
Product added to cart!

Enter choice: 6
=== SHOPPING CART ===
Product                | Qty | Unit Price | Total
Laptop                 | 1   | $1200.00   | $1200.00
                                        Subtotal: $1200.00

Enter choice: 7
Enter coupon code: SAVE10
Coupon applied successfully!
Discount: 10% = -$120.00
New total: $1080.00

Enter choice: 8
=== CHECKOUT ===
Subtotal: $1200.00
Discount: -$120.00
Tax (5%): $54.00
Total: $1134.00

Select payment method:
1. Credit Card
2. Debit Card
3. Net Banking
4. Digital Wallet

Select (1-4): 1
Enter card number: ****
Order placed successfully!
Order ID: ORD2024001
Expected delivery: 2024-02-20

Payment processed successfully!
Transaction ID: TXN123456

Enter choice: 9
=== ORDER HISTORY ===
Order ID  | Date       | Total    | Status      | Items
ORD2024001| 2024-01-15 | $1134.00 | PROCESSING  | 1
ORD2024002| 2024-01-10 | $800.00  | DELIVERED   | 2

Enter choice: 10
Enter order ID: ORD2024001
Order Status: PROCESSING
Order Date: 2024-01-15
Expected Delivery: 2024-02-20
Tracking: In Transit - At Local Hub

Enter choice: 11
Enter product ID: P001
Enter rating (1-5): 5
Enter review: Excellent laptop, very fast!
Review posted successfully!

Enter choice: 13
```

### Complexity Analysis

| Operation | Time Complexity | Database Complexity |
|-----------|-----------------|---------------------|
| Register Customer | O(1) | Single INSERT |
| Place Order | O(n) | Multiple INSERT with transaction |
| Search Products | O(log n) | Indexed SELECT |
| Get Orders | O(m) | JOIN on two tables |
| Get Top Products | O(n log n) | GROUP BY with sorting |
| Apply Coupon | O(1) | Indexed SELECT and UPDATE |
| Generate Report | O(n) | Complex JOIN and aggregation |

### Extensions and Challenges

**Intermediate Extensions:**
- Implement product wishlists
- Add customer tier/loyalty system
- Create automated email notifications
- Add promotional campaigns
- Implement recommendation engine
- Create vendor/seller management

**Advanced Challenges:**
- Multi-vendor marketplace
- Implement payment gateway integration (Stripe, PayPal)
- Add distributed transaction support
- Implement caching layer (Redis)
- Create REST API with Spring Boot
- Add machine learning recommendations
- Implement supply chain optimization
- Add real-time inventory sync

---

## 3. Employee Payroll System (JDBC)

### Project Description

Build a comprehensive employee payroll management system with salary calculations, deductions, taxes, and report generation. This project involves complex financial calculations, multiple related tables, and sophisticated queries.

**Core Features:**
- Employee master data management
- Salary structure configuration
- Attendance and leave tracking
- Salary calculation with multiple components
- Tax calculation and deductions
- Payslip generation
- Payroll processing and payment tracking
- Reimbursement management
- Performance bonus calculation
- Reports and analytics
- Compliance reporting

### Required Concepts
- Complex JDBC operations
- Advanced SQL queries with calculations
- Transaction management for payroll processing
- Prepared statements
- Stored procedures (optional)
- Aggregate functions (SUM, AVG, MAX, MIN)
- Date calculations
- Financial calculations and rounding
- Batch operations for bulk payroll

### Database Schema

```sql
-- Employees Table
CREATE TABLE employees (
    employee_id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    date_of_birth DATE,
    date_of_joining DATE NOT NULL,
    department VARCHAR(50),
    designation VARCHAR(50),
    manager_id VARCHAR(10),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (manager_id) REFERENCES employees(employee_id)
);

-- Salary Structure Table
CREATE TABLE salary_structures (
    structure_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(10) NOT NULL UNIQUE,
    base_salary DECIMAL(12, 2) NOT NULL,
    dearness_allowance DECIMAL(10, 2) DEFAULT 0,
    house_rent_allowance DECIMAL(10, 2) DEFAULT 0,
    conveyance_allowance DECIMAL(10, 2) DEFAULT 0,
    medical_allowance DECIMAL(10, 2) DEFAULT 0,
    other_allowances DECIMAL(10, 2) DEFAULT 0,
    professional_tax DECIMAL(10, 2) DEFAULT 0,
    pf_contribution_percent DECIMAL(5, 2) DEFAULT 12,
    esi_contribution_percent DECIMAL(5, 2) DEFAULT 0.75,
    effective_from DATE NOT NULL,
    effective_to DATE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Attendance Table
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(10) NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(20),
    working_hours DECIMAL(5, 2),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    UNIQUE(employee_id, attendance_date)
);

-- Leave Table
CREATE TABLE leaves (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(10) NOT NULL,
    leave_type VARCHAR(50),
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    number_of_days DECIMAL(5, 2) NOT NULL,
    reason TEXT,
    status VARCHAR(20) DEFAULT 'APPROVED',
    approval_date DATE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Payroll Table
CREATE TABLE payrolls (
    payroll_id VARCHAR(15) PRIMARY KEY,
    employee_id VARCHAR(10) NOT NULL,
    payroll_month DATE NOT NULL,
    working_days INT,
    present_days INT,
    absent_days INT,
    leave_days DECIMAL(5, 2),
    gross_salary DECIMAL(12, 2),
    total_deductions DECIMAL(12, 2),
    net_salary DECIMAL(12, 2),
    status VARCHAR(20) DEFAULT 'DRAFT',
    generated_date DATE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Payslip Table
CREATE TABLE payslips (
    payslip_id INT AUTO_INCREMENT PRIMARY KEY,
    payroll_id VARCHAR(15) NOT NULL,
    employee_id VARCHAR(10) NOT NULL,
    base_salary DECIMAL(12, 2),
    dearness_allowance DECIMAL(10, 2),
    house_rent_allowance DECIMAL(10, 2),
    conveyance_allowance DECIMAL(10, 2),
    medical_allowance DECIMAL(10, 2),
    other_allowances DECIMAL(10, 2),
    gross_salary DECIMAL(12, 2),
    pf_deduction DECIMAL(10, 2),
    esi_deduction DECIMAL(10, 2),
    professional_tax DECIMAL(10, 2),
    income_tax DECIMAL(10, 2),
    other_deductions DECIMAL(10, 2),
    total_deductions DECIMAL(12, 2),
    net_salary DECIMAL(12, 2),
    FOREIGN KEY (payroll_id) REFERENCES payrolls(payroll_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Reimbursement Table
CREATE TABLE reimbursements (
    reimbursement_id VARCHAR(15) PRIMARY KEY,
    employee_id VARCHAR(10) NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    category VARCHAR(50),
    description TEXT,
    receipt_date DATE,
    approval_status VARCHAR(20) DEFAULT 'PENDING',
    approval_date DATE,
    payment_date DATE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Tax Slabs Table (for income tax calculation)
CREATE TABLE tax_slabs (
    slab_id INT AUTO_INCREMENT PRIMARY KEY,
    financial_year VARCHAR(10),
    income_from DECIMAL(12, 2),
    income_to DECIMAL(12, 2),
    tax_percent DECIMAL(5, 2),
    fixed_amount DECIMAL(10, 2)
);

-- Create indexes
CREATE INDEX idx_employee_dept ON employees(department);
CREATE INDEX idx_attendance_emp_date ON attendance(employee_id, attendance_date);
CREATE INDEX idx_payroll_month ON payrolls(payroll_month);
CREATE INDEX idx_payroll_emp ON payrolls(employee_id);
CREATE INDEX idx_payslip_emp ON payslips(employee_id);
```

### Architecture Overview

```
PayrollManagementSystem
├── Employee (entity)
├── SalaryStructure (entity)
├── Attendance (entity)
├── Leave (entity)
├── Payroll (entity)
├── Payslip (entity)
├── Reimbursement (entity)
├── EmployeeDAO
│   ├── Methods: addEmployee(), updateEmployee(), deactivateEmployee()
│   ├── Methods: getEmployeeById(), getAllEmployees()
│   ├── Methods: getEmployeesByDepartment(), getReportingStructure()
│   └── Methods: getTotalEmployees(), getActiveEmployeeCount()
├── SalaryStructureDAO
│   ├── Methods: setSalaryStructure(), updateSalaryStructure()
│   ├── Methods: getActiveSalaryStructure(), getSalaryHistory()
│   └── Methods: calculateGrossSalary()
├── AttendanceDAO
│   ├── Methods: markAttendance(), updateAttendance()
│   ├── Methods: getAttendance(), getMonthlyAttendance()
│   ├── Methods: calculateWorkingDays(), calculatePresentDays()
│   └── Methods: generateAttendanceReport()
├── LeaveDAO
│   ├── Methods: applyLeave(), approveLeave(), cancelLeave()
│   ├── Methods: getLeaveBalance(), getLeaveHistory()
│   ├── Methods: getMonthlyLeaveDays()
│   └── Methods: generateLeaveReport()
├── PayrollProcessor (business logic)
│   ├── Methods: generatePayroll() [transaction]
│   ├── Methods: calculateGrossSalary(), calculateDeductions()
│   ├── Methods: calculateIncomeTax(), calculateNetSalary()
│   └── Methods: processPayment() [transaction]
├── PayslipGenerator
│   ├── Methods: generatePayslip(), generatePayslipReport()
│   ├── Methods: exportToCSV(), exportToPDF()
│   └── Methods: sendPayslipEmail()
├── ReimbursementDAO
│   ├── Methods: submitReimbursement(), approveReimbursement()
│   ├── Methods: getReimbursements(), processPayment()
│   └── Methods: generateReimbursementReport()
├── ReportGenerator
│   ├── Methods: generatePayrollReport(), generateAttendanceReport()
│   ├── Methods: generateTaxReport(), generateComplianceReport()
│   ├── Methods: generateDepartmentAnalysis()
│   └── Methods: generateYearEndReport()
└── PayrollApplication (console interface)
    └── Menu-driven interface
```

### Salary Calculation Algorithm

```
1. Calculate Gross Salary:
   Gross = Base Salary + DA + HRA + CA + MA + Other Allowances
   
2. Calculate Effective Days Worked:
   = (Working Days - Absent Days - Leave Days)
   
3. Proration Factor:
   = Effective Days / Total Working Days
   
4. Prorated Gross Salary:
   = Gross Salary × Proration Factor
   
5. Calculate Deductions:
   PF = Prorated Gross × (PF Contribution % / 100)
   ESI = Prorated Gross × (ESI % / 100)
   Professional Tax = As per configuration
   Income Tax = Based on tax slabs
   
6. Total Deductions:
   = PF + ESI + Professional Tax + Income Tax + Other Deductions
   
7. Net Salary:
   = Prorated Gross Salary - Total Deductions
```

### Sample JDBC Code

```java
public class PayrollProcessor {
    
    public void generatePayroll(String employeeId, String payrollMonth) 
            throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try {
            conn.setAutoCommit(false);
            
            // Get employee and salary structure
            EmployeeDAO empDAO = new EmployeeDAO();
            Employee employee = empDAO.getEmployeeById(conn, employeeId);
            
            SalaryStructureDAO structureDAO = new SalaryStructureDAO();
            SalaryStructure structure = 
                structureDAO.getActiveSalaryStructure(conn, employeeId);
            
            // Calculate working days and attendance
            AttendanceDAO attDAO = new AttendanceDAO();
            int workingDays = attDAO.calculateWorkingDays(conn, payrollMonth);
            int presentDays = attDAO.calculatePresentDays(conn, employeeId, 
                                                         payrollMonth);
            
            // Get leave days
            LeaveDAO leaveDAO = new LeaveDAO();
            double leaveDays = leaveDAO.getMonthlyLeaveDays(conn, employeeId, 
                                                           payrollMonth);
            
            // Calculate gross salary
            double grossSalary = structureDAO.calculateGrossSalary(structure);
            
            // Calculate effective days
            int effectiveDays = workingDays - (int)presentDays - (int)leaveDays;
            double prorationfactor = (double)effectiveDays / workingDays;
            double proratedGross = grossSalary * prorationfactor;
            
            // Calculate deductions
            double pf = calculatePF(proratedGross, structure);
            double esi = calculateESI(proratedGross, structure);
            double pt = structure.getProfessionalTax();
            double incomeTax = calculateIncomeTax(proratedGross);
            
            double totalDeductions = pf + esi + pt + incomeTax;
            double netSalary = proratedGross - totalDeductions;
            
            // Create payroll record
            Payroll payroll = new Payroll();
            payroll.setPayrollId(generatePayrollId());
            payroll.setEmployeeId(employeeId);
            payroll.setPayrollMonth(payrollMonth);
            payroll.setWorkingDays(workingDays);
            payroll.setPresentDays(presentDays);
            payroll.setLeaveDays(leaveDays);
            payroll.setGrossSalary(proratedGross);
            payroll.setTotalDeductions(totalDeductions);
            payroll.setNetSalary(netSalary);
            payroll.setStatus("DRAFT");
            
            PayrollDAO payrollDAO = new PayrollDAO();
            payrollDAO.addPayroll(conn, payroll);
            
            // Create payslip
            Payslip payslip = new Payslip();
            payslip.setPayrollId(payroll.getPayrollId());
            payslip.setEmployeeId(employeeId);
            payslip.setBaseSalary(structure.getBaseSalary());
            payslip.setDearnessAllowance(structure.getDearnessAllowance());
            // ... set other fields
            payslip.setPFDeduction(pf);
            payslip.setESIDeduction(esi);
            payslip.setIncomeTax(incomeTax);
            payslip.setNetSalary(netSalary);
            
            payrollDAO.addPayslip(conn, payslip);
            
            conn.commit();
            
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
    
    private double calculateIncomeTax(double grossSalary) throws SQLException {
        String sql = "SELECT * FROM tax_slabs WHERE income_from <= ? " +
                     "AND income_to >= ? ORDER BY income_from DESC LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, grossSalary);
            pstmt.setDouble(2, grossSalary);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double baseTax = rs.getDouble("fixed_amount");
                double percent = rs.getDouble("tax_percent");
                double amountAboveSlab = grossSalary - rs.getDouble("income_from");
                return baseTax + (amountAboveSlab * percent / 100);
            }
        }
        return 0;
    }
}
```

### Implementation Steps

**Phase 1: Database Setup (Days 1-2)**
1. Design payroll database schema
2. Create all tables with relationships
3. Set up tax slabs and configurations
4. Create necessary indexes
5. Insert sample employee and configuration data

**Phase 2: Entity Classes (Days 3)**
1. Create all entity classes (Employee, Salary, Payroll, etc.)
2. Implement getters/setters with validation
3. Add calculation methods where appropriate

**Phase 3: DAO Implementation (Days 4-5)**
1. Implement `EmployeeDAO` for employee management
2. Implement `SalaryStructureDAO` for salary configuration
3. Implement `AttendanceDAO` for attendance tracking
4. Implement `LeaveDAO` for leave management
5. Implement `PayrollDAO` for payroll operations
6. Implement `ReimbursementDAO` for reimbursement processing

**Phase 4: Business Logic and Calculations (Days 6-8)**
1. Create `PayrollProcessor` with salary calculation logic
2. Implement complex financial calculations
3. Create `PayslipGenerator` for payslip generation
4. Implement `ReportGenerator` for various reports
5. Add compliance and audit functionality

**Phase 5: User Interface and Testing (Days 9-12)**
1. Build admin menu for employee management
2. Build manager menu for approval workflows
3. Build employee menu for payslip view
4. Implement payroll generation and processing
5. Create comprehensive reporting interface
6. Test all calculations with various scenarios
7. Validate compliance requirements

### Sample Input/Output

```
=== EMPLOYEE PAYROLL SYSTEM ===
Admin Menu:
1. Manage Employees
2. Set Salary Structure
3. Process Attendance
4. Manage Leaves
5. Generate Payroll
6. Approve Reimbursements
7. Generate Reports
8. View Compliance
9. Exit

Manager Menu:
1. View Team
2. Approve Leaves
3. View Payrolls
4. Exit

Employee Menu:
1. View Payslips
2. Apply Leave
3. View Reimbursement Status
4. Exit

Enter choice (Admin): 5

=== GENERATE PAYROLL ===
Enter payroll month (YYYY-MM): 2024-01
Processing payroll for January 2024...

Processing: E001 - Rajesh Kumar
  Base Salary: $50,000.00
  Gross Salary (with allowances): $62,500.00
  Working Days: 22 | Present: 20 | Leave: 1 | Absent: 1
  Prorated Gross: $60,227.27
  
  Deductions:
  - PF (12%): $7,227.27
  - ESI (0.75%): $451.70
  - Professional Tax: $500.00
  - Income Tax: $5,000.00
  - Total Deductions: $13,178.97
  
  Net Salary: $47,048.30
  Status: GENERATED

Processing: E002 - Priya Singh
  ... similar processing ...

Payroll generated successfully!
Total employees processed: 50
Payroll ID: PR202401001
Generated date: 2024-01-31

Enter choice: 7

=== PAYROLL REPORTS ===
1. Employee Payroll Summary
2. Department-wise Analysis
3. Tax Report
4. Compliance Report
5. Year-End Summary
6. Exit

Enter choice: 1
Enter employee ID: E001

=== PAYROLL SUMMARY: E001 - Rajesh Kumar ===
Financial Year: 2023-24

Month   | Gross    | Deductions | Net Salary | Working Days
Jan     | $60,227  | $13,178    | $47,048    | 20
Feb     | $61,500  | $13,425    | $48,075    | 21
Mar     | $62,500  | $13,650    | $48,850    | 22
...
---
YTD Gross Salary: $734,231
YTD Deductions: $160,890
YTD Net Salary: $573,341
YTD Income Tax: $58,900

Enter choice: 3

=== TAX COMPLIANCE REPORT ===
Employee ID | Name          | YTD Gross | Income Tax | PF       | ESI
E001        | Rajesh Kumar  | $734,231  | $58,900    | $88,107  | $5,507
E002        | Priya Singh   | $620,450  | $42,350    | $74,454  | $4,653
...
Total Income Tax Collected: $2,850,000
Total PF Contribution: $3,420,500
Total ESI Contribution: $213,875
```

### Complexity Analysis

| Operation | Time Complexity | Database Complexity |
|-----------|-----------------|---------------------|
| Generate Payroll | O(n) | Multiple INSERT with transaction |
| Calculate Deductions | O(1) | Single SELECT for tax slab |
| Get Monthly Attendance | O(d) | d = days in month, SELECT with filter |
| Generate Payslips | O(n) | Batch INSERT |
| Generate Report | O(n log n) | Complex GROUP BY with aggregation |
| Calculate YTD | O(m) | m = months, SUM aggregation |

### Extensions and Challenges

**Intermediate Extensions:**
- Implement performance bonus calculations
- Add employee salary history
- Create compliance audit trails
- Implement salary revisions workflow
- Add pension/retirement calculations
- Create departmental analytics

**Advanced Challenges:**
- Multi-location payroll processing
- Integration with accounting software
- GST and other tax calculations
- Employee self-service portal
- Automated bank file generation (NEFT/RTGS)
- Integration with HR management system
- Real-time payroll simulation
- Advanced analytics and forecasting

---

## Summary: Database Applications Learning Path

By completing these three projects, you will have mastered:
- **JDBC Fundamentals:** Connections, statements, result sets
- **SQL:** Complex queries, JOINs, aggregations, subqueries
- **Transaction Management:** ACID properties, rollback mechanisms
- **Database Design:** Schema design, relationships, constraints
- **Data Security:** Prepared statements, SQL injection prevention
- **Performance:** Indexing, query optimization, batch operations
- **Business Logic:** Complex calculations and workflows
- **Error Handling:** Database-specific exception management

These projects represent enterprise-grade applications and provide the foundation for advanced database and backend development. Complete them thoroughly for professional-level competency!
