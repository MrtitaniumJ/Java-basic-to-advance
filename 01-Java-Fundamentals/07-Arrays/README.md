# Java Arrays - Storing Multiple Values

## What are Arrays?

An array is like a **row of boxes** where each box can store one value of the same type. Think of it as:
- **A bookshelf** where each shelf holds one book
- **A parking lot** where each spot holds one car
- **An apartment building** where each unit has the same layout

```
Array: [10, 20, 30, 40, 50]
Index:  0   1   2   3   4
```

Each box has:
- **A number (index)** starting from 0
- **A value** of the same data type
- **Fixed size** that cannot change

## Why Use Arrays?

Instead of creating separate variables:
```java
// ✗ Tedious way
int student1Score = 85;
int student2Score = 92;
int student3Score = 78;
int student4Score = 96;
int student5Score = 88;
```

Use an array:
```java
// ✓ Better way
int[] studentScores = {85, 92, 78, 96, 88};
```

---

## Array Declaration and Initialization

### Method 1: Declare then Initialize
```java
public class ArrayBasics {
    public static void main(String[] args) {
        // Declare array (creates space for 5 integers)
        int[] numbers = new int[5];
        
        // Initialize individual elements
        numbers[0] = 10;
        numbers[1] = 20;
        numbers[2] = 30;
        numbers[3] = 40;
        numbers[4] = 50;
        
        // Print all elements
        System.out.println("Array elements:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Index " + i + ": " + numbers[i]);
        }
    }
}
```

### Method 2: Declare and Initialize Together
```java
public class ArrayInitialization {
    public static void main(String[] args) {
        // Method 1: Using curly braces
        int[] scores = {95, 87, 92, 78, 85};
        
        // Method 2: Using new keyword
        String[] names = new String[]{"Alice", "Bob", "Charlie", "Diana"};
        
        // Method 3: Mixed declaration
        double[] prices = new double[]{19.99, 29.99, 39.99, 49.99};
        
        System.out.println("Scores length: " + scores.length);
        System.out.println("Names length: " + names.length);
        System.out.println("Prices length: " + prices.length);
    }
}
```

## Array Types and Examples

### Integer Arrays
```java
public class IntegerArrays {
    public static void main(String[] args) {
        // Student ages in a class
        int[] ages = {18, 19, 18, 20, 19, 18, 21};
        
        // Find average age
        int sum = 0;
        for (int age : ages) {
            sum += age;
        }
        double averageAge = (double) sum / ages.length;
        
        System.out.println("Total students: " + ages.length);
        System.out.println("Average age: " + String.format("%.1f", averageAge));
        
        // Find youngest and oldest
        int youngest = ages[0];
        int oldest = ages[0];
        
        for (int age : ages) {
            if (age < youngest) youngest = age;
            if (age > oldest) oldest = age;
        }
        
        System.out.println("Youngest: " + youngest);
        System.out.println("Oldest: " + oldest);
    }
}
```

### String Arrays
```java
public class StringArrays {
    public static void main(String[] args) {
        // Days of the week
        String[] daysOfWeek = {
            "Monday", "Tuesday", "Wednesday", "Thursday", 
            "Friday", "Saturday", "Sunday"
        };
        
        // Print weekdays vs weekends
        System.out.println("Weekdays:");
        for (int i = 0; i < 5; i++) {
            System.out.println("- " + daysOfWeek[i]);
        }
        
        System.out.println("\nWeekend:");
        for (int i = 5; i < daysOfWeek.length; i++) {
            System.out.println("- " + daysOfWeek[i]);
        }
        
        // Find a specific day
        String targetDay = "Friday";
        int dayIndex = -1;
        
        for (int i = 0; i < daysOfWeek.length; i++) {
            if (daysOfWeek[i].equals(targetDay)) {
                dayIndex = i;
                break;
            }
        }
        
        if (dayIndex != -1) {
            System.out.println("\n" + targetDay + " is day number " + (dayIndex + 1));
        }
    }
}
```

### Double Arrays
```java
public class DoubleArrays {
    public static void main(String[] args) {
        // Monthly temperatures (Celsius)
        double[] monthlyTemps = {2.5, 5.1, 12.3, 18.7, 24.2, 28.9, 
                                31.4, 30.8, 26.1, 19.3, 11.7, 4.8};
        
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        System.out.println("Monthly Temperature Report:");
        System.out.println("Month\tTemp (°C)\tCategory");
        System.out.println("--------------------------------");
        
        double totalTemp = 0;
        
        for (int i = 0; i < monthlyTemps.length; i++) {
            double temp = monthlyTemps[i];
            totalTemp += temp;
            
            String category;
            if (temp < 0) {
                category = "Freezing";
            } else if (temp < 10) {
                category = "Cold";
            } else if (temp < 20) {
                category = "Cool";
            } else if (temp < 30) {
                category = "Warm";
            } else {
                category = "Hot";
            }
            
            System.out.printf("%s\t%.1f°C\t\t%s\n", months[i], temp, category);
        }
        
        double averageTemp = totalTemp / monthlyTemps.length;
        System.out.printf("\nAverage yearly temperature: %.1f°C\n", averageTemp);
    }
}
```

---

## Array Operations

### 1. Accessing Elements
```java
public class ArrayAccess {
    public static void main(String[] args) {
        String[] colors = {"Red", "Green", "Blue", "Yellow", "Purple"};
        
        // Access individual elements
        System.out.println("First color: " + colors[0]);
        System.out.println("Last color: " + colors[colors.length - 1]);
        System.out.println("Middle color: " + colors[colors.length / 2]);
        
        // Safe access with bounds checking
        int index = 10;
        if (index >= 0 && index < colors.length) {
            System.out.println("Color at index " + index + ": " + colors[index]);
        } else {
            System.out.println("Index " + index + " is out of bounds!");
        }
    }
}
```

### 2. Modifying Elements
```java
public class ArrayModification {
    public static void main(String[] args) {
        int[] inventory = {50, 30, 45, 20, 60};
        String[] items = {"Laptops", "Mice", "Keyboards", "Monitors", "Speakers"};
        
        System.out.println("Initial Inventory:");
        printInventory(items, inventory);
        
        // Simulate sales
        inventory[0] -= 5;  // Sold 5 laptops
        inventory[1] -= 10; // Sold 10 mice
        inventory[4] -= 3;  // Sold 3 speakers
        
        System.out.println("\nAfter Sales:");
        printInventory(items, inventory);
        
        // Restock items
        inventory[0] += 20; // Restocked laptops
        inventory[3] += 15; // Restocked monitors
        
        System.out.println("\nAfter Restocking:");
        printInventory(items, inventory);
    }
    
    // Helper method to print inventory
    public static void printInventory(String[] items, int[] quantities) {
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i] + ": " + quantities[i] + " units");
        }
    }
}
```

### 3. Searching Arrays
```java
public class ArraySearching {
    public static void main(String[] args) {
        int[] studentIds = {1001, 1005, 1003, 1008, 1002, 1007, 1004};
        String[] studentNames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace"};
        
        // Linear search for student
        int searchId = 1005;
        int foundIndex = -1;
        
        for (int i = 0; i < studentIds.length; i++) {
            if (studentIds[i] == searchId) {
                foundIndex = i;
                break;
            }
        }
        
        if (foundIndex != -1) {
            System.out.println("Student found!");
            System.out.println("ID: " + searchId);
            System.out.println("Name: " + studentNames[foundIndex]);
            System.out.println("Position in array: " + foundIndex);
        } else {
            System.out.println("Student with ID " + searchId + " not found.");
        }
        
        // Search for multiple students
        int[] searchIds = {1008, 1010, 1002};
        
        System.out.println("\nSearching for multiple students:");
        for (int id : searchIds) {
            boolean found = false;
            for (int i = 0; i < studentIds.length; i++) {
                if (studentIds[i] == id) {
                    System.out.println("✓ Found: " + studentNames[i] + " (ID: " + id + ")");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("✗ Not found: ID " + id);
            }
        }
    }
}
```

### 4. Sorting Arrays
```java
public class ArraySorting {
    public static void main(String[] args) {
        int[] scores = {85, 92, 78, 96, 88, 73, 91, 87};
        
        System.out.println("Original scores:");
        printArray(scores);
        
        // Simple bubble sort (ascending order)
        for (int i = 0; i < scores.length - 1; i++) {
            for (int j = 0; j < scores.length - 1 - i; j++) {
                if (scores[j] > scores[j + 1]) {
                    // Swap elements
                    int temp = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = temp;
                }
            }
        }
        
        System.out.println("Sorted scores (ascending):");
        printArray(scores);
        
        // Find ranks
        System.out.println("\nRanks (highest score = rank 1):");
        for (int i = scores.length - 1; i >= 0; i--) {
            int rank = scores.length - i;
            System.out.println("Rank " + rank + ": " + scores[i] + " points");
        }
    }
    
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
```

---

## Multidimensional Arrays

### 2D Arrays (Tables)

A 2D array is like a **table** with rows and columns:

```java
public class TwoDArrays {
    public static void main(String[] args) {
        // Seating arrangement in a classroom (3 rows, 4 seats each)
        String[][] seatingChart = {
            {"Alice", "Bob", "Charlie", "Diana"},
            {"Eve", "Frank", "Grace", "Henry"},
            {"Ivy", "Jack", "Kate", "Liam"}
        };
        
        System.out.println("Classroom Seating Chart:");
        System.out.println("========================");
        
        for (int row = 0; row < seatingChart.length; row++) {
            System.out.print("Row " + (row + 1) + ": ");
            for (int col = 0; col < seatingChart[row].length; col++) {
                System.out.print(seatingChart[row][col] + "\t");
            }
            System.out.println();
        }
        
        // Find student position
        String searchStudent = "Grace";
        boolean found = false;
        
        for (int row = 0; row < seatingChart.length; row++) {
            for (int col = 0; col < seatingChart[row].length; col++) {
                if (seatingChart[row][col].equals(searchStudent)) {
                    System.out.println("\n" + searchStudent + " sits in Row " + 
                                     (row + 1) + ", Seat " + (col + 1));
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
    }
}
```

### Matrix Operations
```java
public class MatrixOperations {
    public static void main(String[] args) {
        // Grade matrix (students × subjects)
        int[][] grades = {
            {85, 92, 78, 88}, // Alice: Math, English, Science, History
            {91, 87, 95, 82}, // Bob
            {78, 89, 84, 91}, // Charlie
            {94, 85, 91, 87}  // Diana
        };
        
        String[] students = {"Alice", "Bob", "Charlie", "Diana"};
        String[] subjects = {"Math", "English", "Science", "History"};
        
        System.out.println("Grade Report:");
        System.out.println("=============");
        
        // Print grade table
        System.out.print("Student\t\t");
        for (String subject : subjects) {
            System.out.print(subject + "\t");
        }
        System.out.println("Average");
        
        for (int i = 0; i < students.length; i++) {
            System.out.print(students[i] + "\t\t");
            
            int sum = 0;
            for (int j = 0; j < grades[i].length; j++) {
                System.out.print(grades[i][j] + "\t");
                sum += grades[i][j];
            }
            
            double average = (double) sum / grades[i].length;
            System.out.printf("%.1f\n", average);
        }
        
        // Calculate subject averages
        System.out.println("\nSubject Averages:");
        for (int j = 0; j < subjects.length; j++) {
            int sum = 0;
            for (int i = 0; i < students.length; i++) {
                sum += grades[i][j];
            }
            double average = (double) sum / students.length;
            System.out.printf("%s: %.1f\n", subjects[j], average);
        }
    }
}
```

---

## Array Utility Methods (java.util.Arrays)

```java
import java.util.Arrays;

public class ArrayUtilities {
    public static void main(String[] args) {
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        int[] copy = {64, 34, 25, 12, 22, 11, 90};
        
        System.out.println("Original array: " + Arrays.toString(numbers));
        
        // Sort array
        Arrays.sort(numbers);
        System.out.println("Sorted array: " + Arrays.toString(numbers));
        
        // Binary search (array must be sorted)
        int searchValue = 25;
        int index = Arrays.binarySearch(numbers, searchValue);
        System.out.println("Index of " + searchValue + ": " + index);
        
        // Fill array with value
        int[] filled = new int[5];
        Arrays.fill(filled, 42);
        System.out.println("Filled array: " + Arrays.toString(filled));
        
        // Copy array
        int[] copied = Arrays.copyOf(copy, copy.length);
        System.out.println("Copied array: " + Arrays.toString(copied));
        
        // Copy range
        int[] range = Arrays.copyOfRange(copy, 2, 5);
        System.out.println("Range copy (index 2-4): " + Arrays.toString(range));
        
        // Compare arrays
        boolean areEqual = Arrays.equals(copy, copied);
        System.out.println("Arrays are equal: " + areEqual);
    }
}
```

---

## Real-World Applications

### 1. Student Management System
```java
public class StudentManagementSystem {
    public static void main(String[] args) {
        // Student data
        String[] studentNames = {"Alice Johnson", "Bob Smith", "Charlie Brown", 
                                "Diana Prince", "Eve Adams"};
        int[] studentIds = {1001, 1002, 1003, 1004, 1005};
        double[] gpas = {3.85, 3.67, 3.92, 3.74, 3.88};
        boolean[] isActive = {true, true, false, true, true};
        
        System.out.println("=== Student Management System ===\n");
        
        // Display all students
        System.out.println("All Students:");
        System.out.println("ID\tName\t\t\tGPA\tStatus");
        System.out.println("------------------------------------------------");
        
        for (int i = 0; i < studentNames.length; i++) {
            String status = isActive[i] ? "Active" : "Inactive";
            System.out.printf("%d\t%-20s\t%.2f\t%s\n", 
                            studentIds[i], studentNames[i], gpas[i], status);
        }
        
        // Find honor roll students (GPA >= 3.8)
        System.out.println("\nHonor Roll Students (GPA >= 3.8):");
        for (int i = 0; i < studentNames.length; i++) {
            if (gpas[i] >= 3.8 && isActive[i]) {
                System.out.println("• " + studentNames[i] + " (GPA: " + gpas[i] + ")");
            }
        }
        
        // Calculate average GPA
        double totalGpa = 0;
        int activeCount = 0;
        
        for (int i = 0; i < gpas.length; i++) {
            if (isActive[i]) {
                totalGpa += gpas[i];
                activeCount++;
            }
        }
        
        double averageGpa = totalGpa / activeCount;
        System.out.printf("\nAverage GPA (active students): %.2f\n", averageGpa);
        
        // Find student with highest GPA
        double highestGpa = 0;
        String topStudent = "";
        
        for (int i = 0; i < gpas.length; i++) {
            if (isActive[i] && gpas[i] > highestGpa) {
                highestGpa = gpas[i];
                topStudent = studentNames[i];
            }
        }
        
        System.out.println("Top Student: " + topStudent + " (GPA: " + highestGpa + ")");
    }
}
```

### 2. Sales Analytics System
```java
public class SalesAnalytics {
    public static void main(String[] args) {
        // Monthly sales data for different products
        String[] products = {"Laptops", "Phones", "Tablets", "Accessories"};
        double[][] monthlySales = {
            {45000, 52000, 48000, 55000, 62000, 58000}, // Laptops (6 months)
            {78000, 85000, 72000, 89000, 95000, 82000}, // Phones
            {25000, 28000, 22000, 30000, 35000, 31000}, // Tablets  
            {12000, 15000, 18000, 16000, 19000, 21000}  // Accessories
        };
        
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
        
        System.out.println("=== Sales Analytics Dashboard ===\n");
        
        // Display sales table
        System.out.print("Product\t\t");
        for (String month : months) {
            System.out.print(month + "\t");
        }
        System.out.println("Total\t\tAverage");
        System.out.println("-----------------------------------------------------------------------");
        
        double grandTotal = 0;
        
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%-12s\t", products[i]);
            
            double productTotal = 0;
            for (int j = 0; j < monthlySales[i].length; j++) {
                System.out.printf("%.0f\t", monthlySales[i][j]);
                productTotal += monthlySales[i][j];
            }
            
            double productAverage = productTotal / monthlySales[i].length;
            System.out.printf("%.0f\t\t%.0f\n", productTotal, productAverage);
            grandTotal += productTotal;
        }
        
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("GRAND TOTAL: $%.0f\n", grandTotal);
        
        // Monthly totals
        System.out.println("\nMonthly Performance:");
        for (int j = 0; j < months.length; j++) {
            double monthTotal = 0;
            for (int i = 0; i < products.length; i++) {
                monthTotal += monthlySales[i][j];
            }
            System.out.printf("%s: $%.0f\n", months[j], monthTotal);
        }
        
        // Best performing product
        double maxProductSales = 0;
        String bestProduct = "";
        
        for (int i = 0; i < products.length; i++) {
            double productTotal = 0;
            for (int j = 0; j < monthlySales[i].length; j++) {
                productTotal += monthlySales[i][j];
            }
            
            if (productTotal > maxProductSales) {
                maxProductSales = productTotal;
                bestProduct = products[i];
            }
        }
        
        System.out.printf("\nBest Performing Product: %s ($%.0f)\n", 
                         bestProduct, maxProductSales);
    }
}
```

---

## Common Mistakes and Solutions

### 1. Array Index Out of Bounds
```java
// ✗ WRONG
int[] numbers = {1, 2, 3, 4, 5};
System.out.println(numbers[5]); // ERROR! Index 5 doesn't exist

// ✓ CORRECT - Always check bounds
if (index >= 0 && index < numbers.length) {
    System.out.println(numbers[index]);
} else {
    System.out.println("Invalid index!");
}
```

### 2. Not Initializing Array Elements
```java
// ✗ WRONG - Array has default values (0 for int)
int[] scores = new int[5];
// scores contains: [0, 0, 0, 0, 0]

// ✓ CORRECT - Initialize with meaningful values
int[] scores = {85, 92, 78, 96, 88};
// or
int[] scores = new int[5];
for (int i = 0; i < scores.length; i++) {
    scores[i] = getScoreFromUser(); // Get actual scores
}
```

### 3. Comparing Arrays Incorrectly
```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};

// ✗ WRONG - Compares references, not content
if (arr1 == arr2) { // Always false!
    System.out.println("Arrays are equal");
}

// ✓ CORRECT - Use Arrays.equals()
import java.util.Arrays;
if (Arrays.equals(arr1, arr2)) {
    System.out.println("Arrays are equal");
}
```

### 4. Modifying Array Size
```java
// ✗ WRONG - Cannot change array size
int[] numbers = {1, 2, 3};
numbers = new int[5]; // This creates a NEW array, loses old data

// ✓ CORRECT - Use ArrayList for dynamic size
import java.util.ArrayList;
ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(1);
numbers.add(2);
numbers.add(3);
numbers.add(4); // Can add as many as needed
```

## Practice Exercises

### Exercise 1: Grade Calculator
```java
public class GradeCalculator {
    public static void main(String[] args) {
        // Create arrays for student names and their test scores
        String[] students = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        int[][] testScores = {
            {85, 92, 78, 88, 91}, // Alice's 5 test scores
            {79, 85, 88, 92, 87}, // Bob's scores
            {92, 89, 95, 91, 93}, // Charlie's scores
            {78, 82, 85, 79, 81}, // Diana's scores
            {88, 91, 89, 94, 90}  // Eve's scores
        };
        
        // Calculate:
        // 1. Each student's average
        // 2. Each test's average
        // 3. Overall class average
        // 4. Highest and lowest individual scores
        // 5. Student with highest average
    }
}
```

### Exercise 2: Inventory Management
```java
public class InventoryManagement {
    public static void main(String[] args) {
        // Product data
        String[] products = {"Laptop", "Mouse", "Keyboard", "Monitor", "Speaker"};
        int[] quantities = {15, 50, 30, 8, 25};
        double[] prices = {999.99, 29.99, 79.99, 299.99, 149.99};
        
        // Implement:
        // 1. Display inventory with total values
        // 2. Find products below reorder level (< 10 units)
        // 3. Calculate total inventory value
        // 4. Sort products by value (price × quantity)
        // 5. Simulate sales and update quantities
    }
}
```

### Exercise 3: Tic-Tac-Toe Board
```java
public class TicTacToe {
    public static void main(String[] args) {
        // Create a 3x3 game board
        char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };
        
        // Implement:
        // 1. Display the board
        // 2. Place X's and O's
        // 3. Check for wins (rows, columns, diagonals)
        // 4. Check for draw (board full)
        // 5. Validate moves (position not taken)
    }
}
```

## Quick Reference

### Array Declaration & Initialization
```java
// Declaration
int[] arr = new int[5];              // Size 5, default values
String[] names = new String[3];      // Size 3, null values

// Initialization  
int[] numbers = {1, 2, 3, 4, 5};     // Direct initialization
String[] colors = new String[]{"Red", "Blue", "Green"};

// 2D Arrays
int[][] matrix = new int[3][4];      // 3 rows, 4 columns
int[][] data = {{1,2}, {3,4}, {5,6}}; // Direct initialization
```

### Common Operations
```java
arr.length              // Array size
arr[index]              // Access element
arr[index] = value      // Modify element

Arrays.toString(arr)    // Print 1D array
Arrays.deepToString(arr2D) // Print 2D array
Arrays.sort(arr)        // Sort array
Arrays.equals(arr1, arr2)  // Compare arrays
```

### Loops with Arrays
```java
// Traditional for loop
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

// Enhanced for loop (for-each)
for (int element : arr) {
    System.out.println(element);
}

// 2D array traversal
for (int i = 0; i < arr2D.length; i++) {
    for (int j = 0; j < arr2D[i].length; j++) {
        System.out.print(arr2D[i][j] + " ");
    }
    System.out.println();
}
```

## Key Takeaways

1. **Arrays store multiple values** of the same type
2. **Index starts at 0** and goes to length-1  
3. **Array size is fixed** after creation
4. **Use enhanced for loop** when you don't need index
5. **Always check bounds** to avoid exceptions
6. **Use Arrays class** for common operations
7. **2D arrays** are arrays of arrays
8. **Arrays are reference types** - be careful with comparisons
9. **Initialize arrays** with meaningful values
10. **Choose arrays** when size is known and fixed