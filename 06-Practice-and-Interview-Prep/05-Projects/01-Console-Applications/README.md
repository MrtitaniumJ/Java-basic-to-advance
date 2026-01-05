# Console Applications - Project Guides

Console-based applications form the foundation of practical Java programming. These projects focus on implementing core business logic, mastering object-oriented design, and working effectively with the Collections Framework. Each project teaches specific aspects of software architecture and design patterns.

---

## 1. Banking Management System

### Project Description

Build a comprehensive banking application that manages customer accounts, transactions, and account operations. This is the ideal starting point for learning OOP principles in a real-world context.

**Core Features:**
- Customer account creation and management
- Deposit and withdrawal operations with validation
- Fund transfer between accounts
- Transaction history tracking
- Account statement generation
- Interest calculation and credit
- Account closure functionality
- Search and filter accounts by various criteria

### Required Concepts
- Classes and Objects (Account, Customer, Bank)
- Inheritance (Savings Account, Checking Account, Premium Account)
- Encapsulation (private data with getter/setter validation)
- Collections (ArrayList for customers, HashMap for accounts)
- Exception Handling (custom exceptions for invalid operations)
- File I/O (optional: save/load customer data)

### Architecture Overview

```
BankingSystem (Main Application)
├── Customer (class)
│   ├── customerId (String)
│   ├── name (String)
│   ├── email (String)
│   └── phoneNumber (String)
├── Account (abstract class)
│   ├── accountNumber (String)
│   ├── accountHolder (Customer)
│   ├── balance (double)
│   ├── accountCreationDate (LocalDate)
│   └── Abstract Methods: withdraw(), deposit(), calculateInterest()
├── SavingsAccount (extends Account)
│   ├── interestRate (double)
│   └── minimumBalance (double)
├── CheckingAccount (extends Account)
│   ├── monthlyFee (double)
│   └── overdraftLimit (double)
├── Transaction (class)
│   ├── transactionId (String)
│   ├── type (String: DEPOSIT/WITHDRAWAL/TRANSFER)
│   ├── amount (double)
│   ├── timestamp (LocalDateTime)
│   └── description (String)
├── Bank (main handler)
│   ├── customers (HashMap<String, Customer>)
│   ├── accounts (HashMap<String, Account>)
│   ├── transactions (ArrayList<Transaction>)
│   └── Methods for all operations
└── BankingApplication (console interface)
    └── Menu-driven operations
```

### Implementation Steps

**Phase 1: Core Classes (Days 1-2)**
1. Create `Customer` class with appropriate fields and validation
2. Create abstract `Account` class with common functionality
3. Implement `SavingsAccount` and `CheckingAccount` subclasses
4. Create `Transaction` class for tracking operations

**Phase 2: Business Logic (Days 3-4)**
1. Implement deposit operation with validation
2. Implement withdrawal with balance checks
3. Create fund transfer functionality
4. Add interest calculation logic
5. Implement transaction history

**Phase 3: Bank Management (Days 5-7)**
1. Create `Bank` class to manage all accounts and customers
2. Implement account creation and closure
3. Add search and filter operations
4. Create account statement generation
5. Implement data persistence (optional)

**Phase 4: User Interface (Days 8-10)**
1. Build console menu system
2. Implement all operations through menu
3. Add input validation and error handling
4. Test all scenarios including edge cases

### Sample Input/Output

```
=== BANKING MANAGEMENT SYSTEM ===
1. Create Account
2. Deposit Money
3. Withdraw Money
4. Transfer Funds
5. View Balance
6. View Transaction History
7. Close Account
8. Exit

Enter choice: 1
Select account type (1. Savings, 2. Checking): 1
Enter customer name: John Doe
Enter email: john@example.com
Enter initial deposit: 5000
Account created successfully! Account #: ACC001234

Enter choice: 2
Enter account number: ACC001234
Enter deposit amount: 2000
Deposit successful! New balance: $7000.00

Enter choice: 3
Enter account number: ACC001234
Enter withdrawal amount: 1500
Withdrawal successful! New balance: $5500.00
Remaining daily limit: $4500.00

Enter choice: 7
Transaction History for ACC001234:
ID: TXN001 | Type: DEPOSIT | Amount: $2000.00 | Date: 2024-01-15 10:30
ID: TXN002 | Type: WITHDRAWAL | Amount: $1500.00 | Date: 2024-01-15 11:45

Enter choice: 8
Thank you for using Banking Management System!
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Create Account | O(1) | O(1) | HashMap insertion |
| Deposit/Withdraw | O(1) | O(1) | Direct balance update |
| Find Account | O(1) | O(1) | HashMap lookup |
| Transfer Funds | O(1) | O(1) | Two account updates |
| View History | O(n) | O(1) | n = number of transactions |
| Generate Statement | O(n) | O(n) | Filtering and formatting |

### Extensions and Challenges

**Intermediate Extensions:**
- Implement different account tiers with varying features
- Add overdraft protection for checking accounts
- Implement recurring transfers and automatic bill payments
- Add account freeze/unfreeze functionality
- Create seasonal interest bonuses
- Implement penalty for low balance

**Advanced Challenges:**
- Multi-threaded operations for concurrent transactions
- Database integration with JDBC
- Add encryption for sensitive data
- Implement role-based access (Admin, Customer, Teller)
- Create reporting system with analytics
- Add file-based backup and recovery
- Implement compound interest calculations
- Add multi-currency support

---

## 2. Student Management System

### Project Description

Create a comprehensive system for managing student information, courses, grades, and academic records. This project focuses on data organization and complex querying operations.

**Core Features:**
- Student registration and profile management
- Course enrollment and management
- Grade entry and calculation
- GPA calculation (CGPA)
- Transcript generation
- Academic performance tracking
- Course-wise student list
- Search and filter students
- Attendance tracking
- Report cards generation

### Required Concepts
- Classes and Objects (Student, Course, Grade)
- Collections (ArrayList for students, HashMap for courses, nested structures)
- Sorting and Searching (COMPARATOR interface)
- Encapsulation and Validation
- Exception Handling
- String manipulation for report generation
- Aggregation and composition

### Architecture Overview

```
StudentManagementSystem
├── Student (class)
│   ├── studentId (String, unique)
│   ├── name (String)
│   ├── email (String)
│   ├── enrolledCourses (List<Course>)
│   ├── grades (HashMap<Course, Grade>)
│   ├── dateOfBirth (LocalDate)
│   └── Methods: calculateGPA(), getTranscript()
├── Course (class)
│   ├── courseId (String, unique)
│   ├── courseName (String)
│   ├── credits (int)
│   ├── instructor (String)
│   ├── maxCapacity (int)
│   ├── enrolledStudents (List<Student>)
│   └── Methods: addStudent(), removeStudent(), isFull()
├── Grade (class)
│   ├── marks (double: 0-100)
│   ├── grade (String: A+, A, B+, B, C, D, F)
│   ├── gradePoints (double: 4.0, 3.7, etc.)
│   ├── courseCredit (int)
│   └── Methods: calculateGrade(), calculatePoints()
├── StudentManagementSystem (main handler)
│   ├── students (HashMap<String, Student>)
│   ├── courses (HashMap<String, Course>)
│   └── Methods for all operations
└── Application (console interface)
    └── Menu-driven interface
```

### Implementation Steps

**Phase 1: Core Data Structures (Days 1-2)**
1. Create `Student` class with validation
2. Create `Course` class with enrollment logic
3. Create `Grade` class with calculation methods
4. Implement GPA calculation algorithm

**Phase 2: Business Logic (Days 3-5)**
1. Implement student enrollment in courses
2. Create grade entry functionality
3. Implement course capacity management
4. Add transcript generation
5. Create report card functionality
6. Implement search and filter operations

**Phase 3: Advanced Features (Days 6-7)**
1. Add attendance tracking
2. Implement academic standing (good standing, probation, etc.)
3. Create performance analytics
4. Add course prerequisites validation
5. Implement marks distribution (internal/external)

**Phase 4: User Interface (Days 8-10)**
1. Build comprehensive menu system
2. Implement all operations
3. Add validation and error handling
4. Create formatted report outputs

### Sample Input/Output

```
=== STUDENT MANAGEMENT SYSTEM ===
1. Register Student
2. Add/View Courses
3. Enroll in Course
4. Enter Grades
5. View Transcript
6. View GPA
7. Search Student
8. Generate Report Card
9. Exit

Enter choice: 1
Enter student name: Alice Johnson
Enter email: alice@university.edu
Enter date of birth (YYYY-MM-DD): 2003-05-15
Student registered successfully! ID: STU001

Enter choice: 2
Available Courses:
1. Java Programming (CS101) - 4 Credits
2. Data Structures (CS102) - 3 Credits
3. Database Design (CS103) - 3 Credits

Enter choice: 3
Enter student ID: STU001
Select course (1-3): 1
Enrolled successfully in Java Programming!

Enter choice: 4
Enter student ID: STU001
Courses enrolled:
1. Java Programming (CS101)
Enter course (1): 1
Enter marks (0-100): 85
Grade assigned: A (Grade Points: 4.0)

Enter choice: 5
=== TRANSCRIPT: STU001 - Alice Johnson ===
Course Name          | Credits | Marks | Grade | Grade Points
Java Programming     |    4    |  85   |   A   |    4.0
---------------------------------------------------
Total Credits: 4 | Total Grade Points: 16.0 | CGPA: 4.0

Enter choice: 8
=== REPORT CARD ===
Student: Alice Johnson (STU001)
Semester: 1
Academic Standing: GOOD
GPA: 4.0
Performance: Outstanding
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Register Student | O(1) | O(1) | HashMap insertion |
| Enroll Course | O(1) | O(1) | Adding to list |
| Enter Grade | O(1) | O(1) | HashMap update |
| Calculate GPA | O(n) | O(1) | n = number of courses |
| Generate Transcript | O(n) | O(n) | n = number of courses |
| Search Student | O(1) | O(1) | HashMap lookup |
| Get Course List | O(m) | O(m) | m = total courses |

### Extensions and Challenges

**Intermediate Extensions:**
- Add semester-wise tracking
- Implement prerequisite checking
- Add academic warnings system
- Create course recommendations
- Add internship/project grades
- Implement weighted grading system

**Advanced Challenges:**
- Multi-department management
- Implement course scheduling
- Add conflict detection in schedules
- Database integration
- Generate analytics reports
- Implement role-based access (admin, instructor, student)
- Add transcript certification
- Implement scholarship eligibility calculation

---

## 3. E-Commerce Cart System

### Project Description

Build a shopping cart system for an online store with product management, cart operations, and order processing. This project teaches complex data manipulation and business logic implementation.

**Core Features:**
- Product catalog with categories
- Shopping cart management
- Add/remove/update product quantities
- Discount and coupon system
- Inventory management
- Price calculation with taxes
- Order summary generation
- Payment processing simulation
- Order history
- Search and filter products

### Required Concepts
- Classes and Objects (Product, Cart, Order, Customer)
- Collections (HashMap for products, ArrayList for cart items)
- Interfaces (Discountable, Payable)
- Exception Handling (OutOfStockException, InvalidCouponException)
- Calculation logic (discounts, taxes, total)
- Sorting and searching

### Architecture Overview

```
ECommerceSystem
├── Product (class)
│   ├── productId (String)
│   ├── name (String)
│   ├── category (String)
│   ├── price (double)
│   ├── quantity (int, stock)
│   ├── description (String)
│   └── Methods: isAvailable(), reduceStock()
├── CartItem (class)
│   ├── product (Product)
│   ├── quantity (int)
│   └── Methods: getSubtotal(), updateQuantity()
├── ShoppingCart (class)
│   ├── items (HashMap<String, CartItem>)
│   ├── customer (Customer)
│   ├── Methods: addItem(), removeItem(), updateQuantity(), clear()
├── Discount (interface)
│   └── applyDiscount(): double
├── Coupon (implements Discount)
│   ├── code (String)
│   ├── discountPercentage (double)
│   ├── minAmount (double)
│   └── expiryDate (LocalDate)
├── Order (class)
│   ├── orderId (String)
│   ├── items (List<CartItem>)
│   ├── subtotal (double)
│   ├── discount (double)
│   ├── tax (double)
│   ├── total (double)
│   ├── status (String)
│   └── Methods: calculateTotal(), generateInvoice()
├── Customer (class)
│   ├── customerId (String)
│   ├── name (String)
│   ├── address (String)
│   └── orderHistory (List<Order>)
├── Inventory (class)
│   ├── products (HashMap<String, Product>)
│   └── Methods: searchProduct(), filterByCategory()
└── ECommerceStore (main handler)
    ├── inventory (Inventory)
    ├── customers (HashMap<String, Customer>)
    └── orders (ArrayList<Order>)
```

### Implementation Steps

**Phase 1: Core Classes (Days 1-2)**
1. Create `Product` class with inventory management
2. Create `CartItem` class
3. Create `ShoppingCart` class
4. Implement `Coupon` class

**Phase 2: Business Logic (Days 3-4)**
1. Implement add/remove cart items
2. Implement quantity updates
3. Create discount calculation logic
4. Implement tax calculation
5. Create order generation from cart

**Phase 3: Store Management (Days 5-6)**
1. Create `Inventory` class for product management
2. Implement search and filter functionality
3. Create customer management
4. Implement order tracking
5. Create order history

**Phase 4: User Interface (Days 7-9)**
1. Build store catalog display
2. Implement shopping cart operations
3. Create checkout process
4. Add coupon application
5. Generate invoice/receipt

### Sample Input/Output

```
=== E-COMMERCE STORE ===
1. View Products
2. View Cart
3. Add to Cart
4. Remove from Cart
5. Apply Coupon
6. Checkout
7. Order History
8. Exit

Enter choice: 1
=== PRODUCT CATALOG ===
ID   | Name                | Category    | Price    | Stock
001  | Laptop              | Electronics | $1200.00 | 5
002  | Wireless Mouse      | Electronics | $25.00   | 20
003  | USB-C Cable         | Accessories | $15.00   | 50
004  | Monitor Stand       | Accessories | $45.00   | 10

Enter choice: 3
Enter product ID: 001
Enter quantity: 1
Product added to cart!

Enter choice: 3
Enter product ID: 002
Enter quantity: 2
Product added to cart!

Enter choice: 2
=== YOUR CART ===
Item                | Qty | Unit Price | Subtotal
Laptop              | 1   | $1200.00   | $1200.00
Wireless Mouse      | 2   | $25.00     | $50.00
                                    Subtotal: $1250.00
                                    
Enter choice: 5
Enter coupon code: SAVE10
Coupon applied! Discount: $125.00
New total: $1125.00

Enter choice: 6
=== ORDER SUMMARY ===
Subtotal: $1250.00
Discount: -$125.00
Tax (5%): $62.50
TOTAL: $1187.50

Proceed with payment? (Y/N): Y
Payment processing...
Payment successful!
Order #ORD2024001 created!
Delivery estimated: 3-5 business days
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Add to Cart | O(1) | O(1) | HashMap insertion |
| Remove from Cart | O(1) | O(1) | HashMap deletion |
| Search Product | O(n) | O(1) | n = total products |
| Calculate Total | O(m) | O(1) | m = cart items |
| Apply Coupon | O(1) | O(1) | Code lookup |
| Checkout | O(m) | O(m) | Processing and storing |

### Extensions and Challenges

**Intermediate Extensions:**
- Add wishlist functionality
- Implement product reviews and ratings
- Add multiple payment methods
- Create shipping cost calculation
- Add product recommendations
- Implement bulk discount system

**Advanced Challenges:**
- Database integration
- Real payment gateway integration
- Inventory synchronization across multiple stores
- Recommendation engine based on purchase history
- Dynamic pricing based on demand
- Order tracking and notifications
- Loyalty points system
- Multi-currency support

---

## 4. Library Management System

### Project Description

Develop a comprehensive library management system for tracking books, members, borrowing operations, and library resources. This project combines data management with complex business logic.

**Core Features:**
- Book catalog management
- Member registration and management
- Book borrowing and return tracking
- Due date and fine calculation
- Book reservation system
- Inventory and stock management
- Search books by various criteria
- Member borrowing history
- Fine payment and tracking
- Renewal functionality
- Report generation

### Required Concepts
- Classes and Objects (Book, Member, Borrow, Fine)
- Collections (HashMap for books, ArrayList for members)
- Date/Time handling (LocalDate, Duration)
- Sorting and searching operations
- Aggregation and composition
- Exception Handling
- Business logic and calculations

### Architecture Overview

```
LibraryManagementSystem
├── Book (class)
│   ├── bookId (String)
│   ├── title (String)
│   ├── author (String)
│   ├── isbn (String)
│   ├── category (String)
│   ├── totalCopies (int)
│   ├── availableCopies (int)
│   ├── publicationYear (int)
│   └── Methods: isAvailable(), borrowCopy(), returnCopy()
├── Member (class)
│   ├── memberId (String)
│   ├── name (String)
│   ├── email (String)
│   ├── joinDate (LocalDate)
│   ├── membershipStatus (String)
│   ├── borrowedBooks (List<BorrowRecord>)
│   ├── reservations (List<ReservationRecord>)
│   └── Methods: canBorrow(), getActiveBorrows()
├── BorrowRecord (class)
│   ├── recordId (String)
│   ├── book (Book)
│   ├── member (Member)
│   ├── borrowDate (LocalDate)
│   ├── dueDate (LocalDate)
│   ├── returnDate (LocalDate, nullable)
│   ├── fineAmount (double)
│   └── Methods: calculateFine(), isOverdue()
├── ReservationRecord (class)
│   ├── reservationId (String)
│   ├── book (Book)
│   ├── member (Member)
│   ├── reservationDate (LocalDate)
│   ├── status (String: WAITING, AVAILABLE, CANCELLED)
│   └── Methods: isExpired()
├── Fine (class)
│   ├── fineId (String)
│   ├── borrowRecord (BorrowRecord)
│   ├── amount (double)
│   ├── dateDue (LocalDate)
│   ├── status (String: PENDING, PAID)
│   └── Methods: payFine(), calculateInterest()
├── Library (main handler)
│   ├── books (HashMap<String, Book>)
│   ├── members (HashMap<String, Member>)
│   ├── borrowRecords (ArrayList<BorrowRecord>)
│   ├── reservations (ArrayList<ReservationRecord>)
│   └── Methods for all operations
└── LibraryApplication (console interface)
    └── Menu-driven operations
```

### Implementation Steps

**Phase 1: Core Classes (Days 1-2)**
1. Create `Book` class with inventory tracking
2. Create `Member` class with membership logic
3. Create `BorrowRecord` class
4. Implement `Fine` calculation logic

**Phase 2: Borrowing System (Days 3-4)**
1. Implement borrow functionality
2. Create return functionality with fine calculation
3. Implement renewal logic
4. Add overdue tracking

**Phase 3: Advanced Features (Days 5-6)**
1. Implement book reservation system
2. Create fine payment tracking
3. Add borrowing restrictions
4. Implement notification system (simulated)
5. Add membership tiers and benefits

**Phase 4: User Interface (Days 7-9)**
1. Build library menu system
2. Implement all member operations
3. Create admin functionality
4. Generate reports and statistics
5. Create member history views

### Sample Input/Output

```
=== LIBRARY MANAGEMENT SYSTEM ===
Member Menu:
1. Register as Member
2. Search Books
3. Borrow Book
4. Return Book
5. Pay Fine
6. Renew Book
7. Extend Membership
8. View Borrowed Books
Admin Menu:
9. Add New Book
10. View Reports
11. Exit

Enter choice: 1
Enter name: Raj Kumar
Enter email: raj@email.com
Membership created! ID: MEM001
Membership valid till: 2025-12-31

Enter choice: 2
Enter search keyword: Java
=== SEARCH RESULTS ===
ID    | Title                    | Author          | Category  | Available
B001  | Effective Java           | Joshua Bloch    | Programming | 3
B002  | Java Concurrency         | Brian Goetz     | Programming | 2
B003  | Clean Code               | Robert Martin   | Programming | 1

Enter choice: 3
Enter member ID: MEM001
Enter book ID: B001
Enter number of days: 14
Book borrowed successfully!
Due date: 2024-02-15

Enter choice: 8
=== BORROWED BOOKS (MEM001) ===
Title                | Due Date   | Days Remaining | Fine
Effective Java       | 2024-02-15 | 8 days         | $0.00

Enter choice: 4
Enter book ID: B001
Enter return date: 2024-02-10
Book returned successfully!
Fine: $0.00 (Returned on time)

Enter choice: 5
Pending fines: $0.00
No outstanding fines!

Enter choice: 10
=== LIBRARY REPORT ===
Total Books: 50
Total Members: 25
Currently Borrowed: 18
Overdue Books: 3
Total Fines Pending: $150.00
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Register Member | O(1) | O(1) | HashMap insertion |
| Search Books | O(n) | O(1) | n = total books |
| Borrow Book | O(1) | O(1) | List addition |
| Return Book | O(1) | O(1) | Status update |
| Calculate Fine | O(1) | O(1) | Date calculation |
| Generate Report | O(n+m) | O(n) | Scanning all records |
| Check Reservation | O(1) | O(1) | HashMap lookup |

### Extensions and Challenges

**Intermediate Extensions:**
- Implement member tiers (Gold, Silver, Bronze)
- Add book recommendations based on history
- Create notification system for due dates
- Add inter-library transfers
- Implement digital book management
- Add reading lists and collections

**Advanced Challenges:**
- Database integration with JDBC
- Email notification system
- Multi-branch library management
- Advanced search with filters
- Book analytics and popular titles
- Member analytics and reading patterns
- Implement machine learning recommendations
- Integration with ISBN database API

---

## Summary: Console Applications Learning Path

By completing these four projects, you will have mastered:
- **OOP Design:** Class hierarchies, inheritance, polymorphism, encapsulation
- **Collections Framework:** Effective use of ArrayList, HashMap, and searching/sorting
- **Business Logic:** Complex validation, calculations, and state management
- **Data Organization:** Structuring data effectively for real-world applications
- **Exception Handling:** Proper error handling and custom exceptions
- **User Interaction:** Building intuitive console interfaces
- **Code Quality:** Writing clean, maintainable, and well-documented code

Each project builds upon previous learning while introducing new complexity and real-world scenarios. Complete them systematically for maximum learning benefit!
