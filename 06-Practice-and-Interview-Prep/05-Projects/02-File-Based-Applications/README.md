# File-Based Applications - Project Guides

File-based applications introduce data persistence using file I/O operations. These projects teach you how to save and load data from files, handle different file formats, manage file-related exceptions, and maintain data consistency across program executions. These are critical skills for building practical applications that retain user data.

---

## 1. File-Based Inventory System

### Project Description

Build a comprehensive inventory management system that persists data to files. This project teaches file I/O, data serialization, and handling complex data structures in file formats.

**Core Features:**
- Product management with file persistence
- Inventory tracking and stock levels
- Supplier management
- Purchase orders generation
- Stock alerts and low inventory warnings
- Inventory reports and analytics
- Batch import/export of products
- Stock history tracking
- Search and filter products
- Automatic backup functionality

### Required Concepts
- File I/O (FileReader, FileWriter, BufferedReader, BufferedWriter)
- Data serialization (CSV, JSON, or custom format)
- Exception handling for file operations
- Collections (HashMap, ArrayList)
- Date/Time handling
- String parsing and manipulation
- File format handling and validation
- Object serialization concepts

### Architecture Overview

```
FileBasedInventorySystem
├── Product (class)
│   ├── productId (String)
│   ├── name (String)
│   ├── category (String)
│   ├── unitPrice (double)
│   ├── quantity (int)
│   ├── reorderLevel (int)
│   ├── supplier (String)
│   ├── lastRestockDate (LocalDate)
│   └── Methods: toCSV(), fromCSV(), isLowStock()
├── StockMovement (class)
│   ├── movementId (String)
│   ├── productId (String)
│   ├── type (String: PURCHASE, SALE, ADJUSTMENT)
│   ├── quantity (int)
│   ├── date (LocalDate)
│   ├── reason (String)
│   └── Methods: toCSV(), fromCSV()
├── Supplier (class)
│   ├── supplierId (String)
│   ├── name (String)
│   ├── contact (String)
│   ├── products (List<String>)
│   └── Methods: toCSV(), fromCSV()
├── PurchaseOrder (class)
│   ├── orderId (String)
│   ├── supplierId (String)
│   ├── items (HashMap<String, Integer>)
│   ├── orderDate (LocalDate)
│   ├── expectedDelivery (LocalDate)
│   ├── status (String)
│   └── Methods: toCSV(), fromCSV(), calculateTotal()
├── InventoryReport (class)
│   ├── totalProducts (int)
│   ├── lowStockItems (List<Product>)
│   ├── totalValue (double)
│   ├── generationDate (LocalDate)
│   └── Methods: generateReport(), toFile()
├── FileManager (class)
│   ├── Methods: readProducts(), writeProducts(), readMovements(), writeMovements()
│   └── Handles: reading/writing CSV files, data validation
├── Inventory (class)
│   ├── products (HashMap<String, Product>)
│   ├── movements (ArrayList<StockMovement>)
│   ├── suppliers (HashMap<String, Supplier>)
│   └── Methods for all operations
└── InventoryApplication (console interface)
    └── Menu-driven interface for all operations
```

### Implementation Steps

**Phase 1: File Format and Data Classes (Days 1-2)**
1. Define CSV file format for products, suppliers, and movements
2. Create `Product` class with serialization methods
3. Create `StockMovement` class for tracking
4. Implement parsing logic for file data

**Phase 2: File I/O Operations (Days 3-4)**
1. Implement file reading with BufferedReader
2. Implement file writing with BufferedWriter
3. Create data validation during file operations
4. Handle file not found and I/O exceptions
5. Implement backup functionality

**Phase 3: Business Logic (Days 5-6)**
1. Implement inventory operations (add, update, remove products)
2. Create stock movement tracking
3. Implement low stock alerts
4. Create purchase order generation
5. Implement search and filter operations
6. Create inventory reports

**Phase 4: User Interface and Features (Days 7-9)**
1. Build menu-driven console interface
2. Implement all CRUD operations
3. Add data validation
4. Create formatted report outputs
5. Implement batch import/export
6. Add automatic backup on shutdown

### Sample Input/Output

```
=== FILE-BASED INVENTORY SYSTEM ===
1. View Products
2. Add Product
3. Update Stock
4. Generate Purchase Order
5. View Low Stock Items
6. Generate Inventory Report
7. Search Products
8. Import Products (CSV)
9. Export Products (CSV)
10. Exit

Enter choice: 1
=== PRODUCT INVENTORY ===
ID    | Name              | Category      | Price  | Stock | Min Level | Supplier
P001  | Laptop            | Electronics   | $1000  | 5     | 3         | Tech Supplies
P002  | Mouse             | Accessories   | $25    | 50    | 10        | Tech Supplies
P003  | Keyboard          | Accessories   | $75    | 30    | 15        | Electronic Inc
P004  | Monitor           | Electronics   | $300   | 2     | 5         | Tech Supplies

Enter choice: 2
Enter product ID: P005
Enter product name: USB Hub
Enter category: Accessories
Enter unit price: $45
Enter initial quantity: 20
Enter reorder level: 8
Enter supplier: Tech Supplies
Product added successfully!

Enter choice: 3
Enter product ID: P001
Current stock: 5
Enter quantity to add (-/+ for adjustment): -2
Stock updated successfully!
New stock: 3
Stock movement recorded: SALE, Qty: 2, Date: 2024-01-15

Enter choice: 5
=== LOW STOCK ALERT ===
The following items are below reorder level:
P001 | Laptop        | Current: 3  | Min Level: 3  | Supplier: Tech Supplies
P004 | Monitor       | Current: 2  | Min Level: 5  | Supplier: Tech Supplies

Action: Generate purchase order for these items? (Y/N): Y
Purchase order PO001 created!
Supplier: Tech Supplies
Expected delivery: 2024-02-15

Enter choice: 6
=== INVENTORY REPORT (Generated: 2024-01-15) ===
Total Products: 5
Total Products in Stock: 107 units
Total Inventory Value: $39,650.00

Stock Distribution:
Category       | Products | Units  | Value
Electronics    | 3        | 57     | $36,400.00
Accessories    | 2        | 50     | $3,250.00

Low Stock Items: 2
Movement Summary (Last 30 days):
Date       | Type      | Product       | Qty
2024-01-15 | SALE      | Laptop        | -2
2024-01-14 | PURCHASE  | Mouse         | 10
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Load Products | O(n) | O(n) | n = number of products |
| Save Products | O(n) | O(1) | Writing to file |
| Add Product | O(1) | O(1) | HashMap insertion |
| Update Stock | O(1) | O(1) | Direct update |
| Find Product | O(1) | O(1) | HashMap lookup |
| Generate Report | O(n+m) | O(n) | n = products, m = movements |
| Filter by Category | O(n) | O(k) | k = matching products |

### Extensions and Challenges

**Intermediate Extensions:**
- Implement JSON format support (using external library or custom JSON parsing)
- Add multi-file organization (separate files for products, suppliers, movements)
- Implement automatic daily backups with timestamps
- Add data compression for large inventories
- Create inventory trend analysis
- Implement product categorization hierarchy

**Advanced Challenges:**
- Implement database integration (migrate from files to JDBC)
- Add encryption for sensitive supplier information
- Implement database transactions for data consistency
- Create REST API for inventory access
- Add real-time stock synchronization across multiple locations
- Implement inventory forecasting algorithms
- Add barcode/QR code support
- Create multi-user access with file locking

---

## 2. Todo List Manager with File Persistence

### Project Description

Create a task management application that saves todos to files and allows complex task organization, filtering, and status tracking. This project focuses on practical file handling and user experience.

**Core Features:**
- Create, read, update, delete tasks
- Task categories and priorities
- Due dates and reminders
- Task completion tracking
- Recurring tasks
- Task filtering and sorting
- File-based persistence
- Data export/import functionality
- Backup and recovery
- Task history and completion analytics

### Required Concepts
- File I/O operations (JSON/CSV format)
- Collections (ArrayList for tasks, HashMap for categories)
- Date/Time handling and comparisons
- String parsing and manipulation
- Exception handling for file operations
- Sorting with custom comparators
- Data format conversion and validation

### Architecture Overview

```
TodoListManager
├── Task (class)
│   ├── taskId (String)
│   ├── title (String)
│   ├── description (String)
│   ├── category (String)
│   ├── priority (enum: HIGH, MEDIUM, LOW)
│   ├── dueDate (LocalDate)
│   ├── status (enum: PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
│   ├── createdDate (LocalDate)
│   ├── completedDate (LocalDate, nullable)
│   ├── isRecurring (boolean)
│   ├── recurringPattern (String: DAILY, WEEKLY, MONTHLY)
│   └── Methods: isOverdue(), toJSON(), fromJSON(), getRecurringNextDate()
├── Category (class)
│   ├── categoryId (String)
│   ├── name (String)
│   ├── color (String, for UI)
│   ├── description (String)
│   └── Methods: toJSON(), fromJSON()
├── TaskStatistics (class)
│   ├── totalTasks (int)
│   ├── completedTasks (int)
│   ├── pendingTasks (int)
│   ├── overdueTasks (int)
│   ├── completionRate (double)
│   └── Methods: calculate(), toReport()
├── FileManager (class)
│   ├── Methods: loadTasks(), saveTasks(), createBackup(), restoreBackup()
│   └── Handles: JSON parsing, file I/O, error handling
├── TodoManager (class)
│   ├── tasks (ArrayList<Task>)
│   ├── categories (HashMap<String, Category>)
│   ├── statistics (TaskStatistics)
│   └── Methods for all CRUD and filtering operations
└── TodoApplication (console interface)
    └── Menu-driven interface
```

### Implementation Steps

**Phase 1: Task and Category Classes (Days 1-2)**
1. Create `Task` class with all properties
2. Create `Category` class
3. Implement JSON serialization methods
4. Create date/time utility methods

**Phase 2: File Operations (Days 3-4)**
1. Implement task file loading with JSON parsing
2. Implement task file saving
3. Create backup mechanism
4. Add error handling for file operations
5. Implement restore from backup

**Phase 3: Task Management Logic (Days 4-5)**
1. Implement CRUD operations
2. Create task filtering and sorting
3. Implement priority-based organization
4. Add date-based operations (overdue detection, upcoming tasks)
5. Implement recurring task logic
6. Create statistics calculation

**Phase 4: User Interface (Days 6-8)**
1. Build menu-driven console interface
2. Implement task display with formatting
3. Create search and filter interface
4. Add task creation wizard
5. Implement bulk operations
6. Create statistics and report display

### Sample Input/Output

```
=== TODO LIST MANAGER ===
1. View All Tasks
2. View Tasks by Category
3. View Tasks by Priority
4. Add New Task
5. Update Task
6. Complete Task
7. Delete Task
8. Search Tasks
9. View Statistics
10. Create Backup
11. Exit

Enter choice: 4
=== CREATE NEW TASK ===
Enter task title: Complete Java project
Enter description: Finish file I/O implementation
Enter category: Work
Enter priority (1=HIGH, 2=MEDIUM, 3=LOW): 1
Enter due date (YYYY-MM-DD): 2024-02-20
Task created successfully! ID: TASK001

Enter choice: 1
=== ALL TASKS (Sorted by Priority) ===
ID      | Title                    | Priority | Status    | Due Date   | Category
TASK001 | Complete Java project    | HIGH     | PENDING   | 2024-02-20 | Work
TASK002 | Buy groceries            | MEDIUM   | PENDING   | 2024-01-20 | Personal
TASK003 | Team meeting             | HIGH     | COMPLETED | 2024-01-15 | Work
TASK004 | Read book chapter        | LOW      | PENDING   | 2024-02-28 | Personal

Enter choice: 6
Enter task ID to complete: TASK001
Task completed successfully!
Completion time: 5 days before due date
Task moved to history.

Enter choice: 9
=== TASK STATISTICS ===
Total Tasks: 4
Completed: 1 (25%)
Pending: 2 (50%)
Cancelled: 0 (0%)
Overdue: 0 (0%)

This Week:
- Tasks due: 1
- Completed: 0
- Pending: 1

This Month:
- Tasks due: 3
- Completed: 1
- Pending: 2

Average Completion Time: 5 days before due date
Productivity Trend: Improving
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Load Tasks | O(n) | O(n) | n = number of tasks |
| Save Tasks | O(n) | O(1) | Writing to file |
| Add Task | O(1) | O(1) | List addition |
| Find Task | O(n) | O(1) | Linear search |
| Filter Tasks | O(n) | O(k) | k = matching tasks |
| Sort Tasks | O(n log n) | O(n) | Custom comparator |
| Generate Statistics | O(n) | O(1) | Single pass |

### Extensions and Challenges

**Intermediate Extensions:**
- Add task dependencies and subtasks
- Implement time-based reminders (scheduled output)
- Add task notes and attachments
- Create productivity graphs
- Add collaborative features (multiple users)
- Implement task templates for recurring task types

**Advanced Challenges:**
- Add database backend (migrate from JSON to JDBC)
- Implement task synchronization across devices
- Create REST API for remote access
- Add machine learning-based task recommendations
- Implement natural language parsing for quick task creation
- Add calendar view integration
- Create mobile app synchronization

---

## 3. Contact Address Book

### Project Description

Build a comprehensive address book application with contact management, organization, and file persistence. This project emphasizes practical file handling and contact manipulation.

**Core Features:**
- Add, edit, delete contacts
- Multiple phone numbers and email addresses per contact
- Contact grouping and tags
- Search and filter contacts
- Contact list export/import
- Birthday reminders
- Favorite contacts
- Contact history and notes
- Contact statistics
- File-based persistence

### Required Concepts
- File I/O operations
- Complex data structures (contacts with multiple phone/email)
- Collections (ArrayList, HashMap)
- String manipulation and searching
- Date handling
- Exception handling
- Data validation
- Sorting and filtering

### Architecture Overview

```
AddressBookSystem
├── Contact (class)
│   ├── contactId (String)
│   ├── firstName (String)
│   ├── lastName (String)
│   ├── dateOfBirth (LocalDate)
│   ├── phoneNumbers (List<PhoneNumber>)
│   ├── emailAddresses (List<EmailAddress>)
│   ├── addresses (List<Address>)
│   ├── company (String)
│   ├── jobTitle (String)
│   ├── tags (List<String>)
│   ├── notes (String)
│   ├── isFavorite (boolean)
│   ├── createdDate (LocalDate)
│   ├── lastModified (LocalDate)
│   └── Methods: toCSV(), fromCSV(), getAge(), getDisplayName()
├── PhoneNumber (class)
│   ├── number (String)
│   ├── type (enum: MOBILE, HOME, WORK, OTHER)
│   ├── isPreferred (boolean)
│   └── Methods: isValid(), toCSV(), fromCSV()
├── EmailAddress (class)
│   ├── email (String)
│   ├── type (enum: PERSONAL, WORK, OTHER)
│   ├── isPreferred (boolean)
│   └── Methods: isValid(), toCSV(), fromCSV()
├── Address (class)
│   ├── street (String)
│   ├── city (String)
│   ├── state (String)
│   ├── zipCode (String)
│   ├── country (String)
│   ├── type (enum: HOME, WORK, OTHER)
│   └── Methods: getFullAddress(), toCSV(), fromCSV()
├── FileManager (class)
│   ├── Methods: loadContacts(), saveContacts(), exportToCSV(), importFromCSV()
│   └── Handles: file I/O, CSV parsing, error handling
├── AddressBook (class)
│   ├── contacts (HashMap<String, Contact>)
│   ├── groups (HashMap<String, List<String>>)
│   └── Methods for all CRUD and search operations
└── AddressBookApplication (console interface)
    └── Menu-driven interface
```

### Implementation Steps

**Phase 1: Contact and Related Classes (Days 1-2)**
1. Create `Contact` class with all properties
2. Create `PhoneNumber`, `EmailAddress`, `Address` classes
3. Implement CSV serialization methods
4. Create phone/email validation methods

**Phase 2: File Operations (Days 3)**
1. Implement contact loading from CSV file
2. Implement contact saving to CSV file
3. Create import/export functionality
4. Add backup capabilities
5. Handle file errors gracefully

**Phase 3: Address Book Logic (Days 4-5)**
1. Implement CRUD operations for contacts
2. Create contact grouping/tagging system
3. Implement search functionality (by name, phone, email)
4. Create filter operations (by group, tags, favorite)
5. Implement contact statistics
6. Add birthday reminder system

**Phase 4: User Interface (Days 6-7)**
1. Build comprehensive menu system
2. Implement contact creation wizard
3. Create contact detail view
4. Build search and filter interface
5. Create formatted contact display
6. Implement bulk operations

### Sample Input/Output

```
=== CONTACT ADDRESS BOOK ===
1. View All Contacts
2. View Contact Details
3. Add New Contact
4. Edit Contact
5. Delete Contact
6. Search Contacts
7. View Contacts by Group
8. Birthday Reminders
9. Export Contacts
10. Import Contacts
11. Contact Statistics
12. Exit

Enter choice: 3
=== ADD NEW CONTACT ===
Enter first name: Rajesh
Enter last name: Kumar
Enter date of birth (YYYY-MM-DD): 1995-06-15
Enter company: Tech Solutions
Enter job title: Software Engineer

Add phone number? (Y/N): Y
Enter phone number: +91-9876543210
Enter type (1=Mobile, 2=Home, 3=Work): 1
Is this preferred? (Y/N): Y

Add another phone? (Y/N): Y
Enter phone number: 040-23456789
Enter type (1=Mobile, 2=Home, 3=Work): 3
Is this preferred? (Y/N): N

Add email address? (Y/N): Y
Enter email: rajesh@techsolutions.com
Enter type (1=Personal, 2=Work, 3=Other): 2
Is this preferred? (Y/N): Y

Enter tags (comma-separated): colleague, friend
Enter notes: Met at Java workshop
Contact created successfully! ID: CONT001

Enter choice: 1
=== ALL CONTACTS ===
ID      | Name              | Company              | Phone           | Email
CONT001 | Rajesh Kumar      | Tech Solutions       | +91-9876543210  | rajesh@techsolutions.com
CONT002 | Priya Singh       | InfoTech Corp        | +91-8765432109  | priya.singh@infotech.com
CONT003 | Arun Patel        | Startup Inc          | +91-7654321098  | arun.patel@startup.in

Enter choice: 2
Enter contact ID: CONT001
=== CONTACT DETAILS ===
Name: Rajesh Kumar
Date of Birth: June 15, 1995 (Age: 28)
Company: Tech Solutions | Job Title: Software Engineer

Phone Numbers:
  +91-9876543210 (Mobile, Preferred)
  040-23456789 (Work)

Email Addresses:
  rajesh@techsolutions.com (Work, Preferred)

Tags: colleague, friend
Notes: Met at Java workshop
Created: 2024-01-15 | Last Modified: 2024-01-15

Enter choice: 8
=== BIRTHDAY REMINDERS ===
Upcoming Birthdays (Next 30 days):
- Rajesh Kumar: June 15 (Turning 29 in 150 days)
- Priya Singh: March 10 (Turning 26 in 54 days)

No birthdays in the next 7 days.

Enter choice: 6
Enter search term: rajesh
=== SEARCH RESULTS ===
Found 1 contact(s):
ID      | Name           | Company              | Phone
CONT001 | Rajesh Kumar   | Tech Solutions       | +91-9876543210

Enter choice: 9
Export format (1=CSV, 2=JSON): 1
Contacts exported successfully to: contacts_backup_2024-01-15.csv
Total contacts exported: 3
```

### Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Load Contacts | O(n) | O(n) | n = number of contacts |
| Save Contacts | O(n) | O(1) | Writing to file |
| Add Contact | O(1) | O(1) | HashMap insertion |
| Find by ID | O(1) | O(1) | Direct lookup |
| Search by Name | O(n) | O(k) | k = matching contacts |
| Get by Group | O(n) | O(k) | k = contacts in group |
| Sort Contacts | O(n log n) | O(n) | Custom comparator |

### Extensions and Challenges

**Intermediate Extensions:**
- Implement contact image/photo storage
- Add contact deduplication and merge functionality
- Create communication history (call/email logs)
- Add social media links
- Implement contact backup to cloud
- Add duplicate contact detection

**Advanced Challenges:**
- Integrate with database (JDBC)
- Add REST API for contact management
- Create web interface (JSP/Spring)
- Implement contact synchronization with external services
- Add encryption for sensitive data
- Create contact relationship mapping (friend of, colleague of, etc.)
- Implement full-text search with indexing
- Add multi-user support with permissions

---

## Summary: File-Based Applications Learning Path

By completing these three projects, you will have developed expertise in:
- **File I/O:** Reading, writing, and managing file operations
- **Data Persistence:** Saving and loading complex data structures
- **Format Handling:** Working with CSV, JSON, and custom formats
- **Exception Handling:** Managing file-related errors gracefully
- **Data Validation:** Ensuring data integrity during file operations
- **Collections:** Using appropriate data structures for file-based data
- **User Experience:** Building intuitive interfaces for file management

These projects prepare you for building real-world applications that work with persistent storage. The skills learned here directly transfer to database programming and are essential for any Java developer.

Next, progress to database-driven applications to learn JDBC and integrate with relational databases!
