# Arrays in Java - Organized Storage

## Simple Explanation

Think of an **array** as a **row of numbered boxes** (like gym lockers):
- Each **box has a number** (index starting from 0)
- All boxes are **the same size** (same data type)
- You can **put one item** in each box
- You can **quickly find any item** by its box number

### Real-World Analogies
- **Apartment building** = Array (multiple units of same type)
- **Parking lot** = Array (numbered parking spaces)
- **Book shelf** = Array (books arranged in order)
- **Egg carton** = Array (fixed slots for eggs)

## Professional Definition

An **Array** is a data structure that stores multiple elements of the same data type in a contiguous memory location. Each element can be accessed using an index, which represents the position of the element in the array.

## Array Characteristics

### Key Properties:
- **Homogeneous**: All elements must be of same data type
- **Fixed Size**: Size is determined at creation and cannot be changed
- **Indexed**: Elements accessed using index (0 to length-1)
- **Contiguous Memory**: Elements stored in consecutive memory locations
- **Random Access**: Direct access to any element using index

### Visual Representation:
```
Array: numbers = [10, 20, 30, 40, 50]

Index:    0   1   2   3   4
        ┌───┬───┬───┬───┬───┐
Value:  │10 │20 │30 │40 │50 │
        └───┴───┴───┴───┴───┘
Memory: 1001 1005 1009 1013 1017
```

## Array Declaration and Initialization

### 1. Declaration Syntax
```java
// Method 1: dataType[] arrayName;
int[] numbers;
String[] names;
double[] prices;

// Method 2: dataType arrayName[];
int numbers[];
String names[];
double prices[];
```

### 2. Memory Allocation
```java
// Allocate memory for specific size
numbers = new int[5];        // Creates array of 5 integers
names = new String[3];       // Creates array of 3 strings
prices = new double[10];     // Creates array of 10 doubles
```

### 3. Declaration with Initialization
```java
// Method 1: Declare, allocate, and initialize separately
int[] scores = new int[5];
scores[0] = 85;
scores[1] = 92;
scores[2] = 78;
scores[3] = 96;
scores[4] = 88;

// Method 2: Declare and initialize together
int[] numbers = {10, 20, 30, 40, 50};
String[] fruits = {"apple", "banana", "orange"};
double[] prices = {19.99, 25.50, 12.75};

// Method 3: Using new keyword with values
int[] ages = new int[]{25, 30, 35, 40};
String[] colors = new String[]{"red", "blue", "green"};
```

## Accessing Array Elements

### Index-Based Access
```java
int[] numbers = {10, 20, 30, 40, 50};

// Reading values
int firstNumber = numbers[0];    // firstNumber = 10
int thirdNumber = numbers[2];    // thirdNumber = 30
int lastNumber = numbers[4];     // lastNumber = 50

// Modifying values
numbers[1] = 25;                 // Changes 20 to 25
numbers[3] = 45;                 // Changes 40 to 45

System.out.println("First: " + numbers[0]);      // First: 10
System.out.println("Modified: " + numbers[1]);   // Modified: 25
```

### Array Length Property
```java
int[] scores = {85, 92, 78, 96, 88};

// Get array length
int arraySize = scores.length;   // arraySize = 5

// Access last element safely
int lastScore = scores[scores.length - 1];  // lastScore = 88

// Avoid index out of bounds
for (int i = 0; i < scores.length; i++) {
    System.out.println("Score " + (i + 1) + ": " + scores[i]);
}
```

## Array Operations

### 1. Iterating Through Arrays

#### Using Traditional for Loop
```java
String[] subjects = {"Math", "Science", "English", "History"};

// Print all subjects with index
for (int i = 0; i < subjects.length; i++) {
    System.out.println("Subject " + (i + 1) + ": " + subjects[i]);
}

// Find specific element
String searchSubject = "Science";
boolean found = false;

for (int i = 0; i < subjects.length; i++) {
    if (subjects[i].equals(searchSubject)) {
        System.out.println(searchSubject + " found at index " + i);
        found = true;
        break;
    }
}

if (!found) {
    System.out.println(searchSubject + " not found!");
}
```

#### Using Enhanced for Loop
```java
double[] temperatures = {98.6, 99.2, 97.8, 100.1, 98.9};

// Print all temperatures
for (double temp : temperatures) {
    System.out.println("Temperature: " + temp + "°F");
}

// Calculate average
double sum = 0;
for (double temp : temperatures) {
    sum += temp;
}
double average = sum / temperatures.length;
System.out.println("Average temperature: " + average + "°F");
```

### 2. Common Array Operations

#### Finding Maximum and Minimum
```java
int[] numbers = {45, 23, 78, 12, 67, 89, 34};

// Find maximum
int max = numbers[0];
for (int i = 1; i < numbers.length; i++) {
    if (numbers[i] > max) {
        max = numbers[i];
    }
}
System.out.println("Maximum: " + max);

// Find minimum
int min = numbers[0];
for (int i = 1; i < numbers.length; i++) {
    if (numbers[i] < min) {
        min = numbers[i];
    }
}
System.out.println("Minimum: " + min);
```

#### Searching for Element
```java
int[] scores = {85, 92, 78, 96, 88, 91, 83};
int targetScore = 96;
int foundIndex = -1;

// Linear search
for (int i = 0; i < scores.length; i++) {
    if (scores[i] == targetScore) {
        foundIndex = i;
        break;
    }
}

if (foundIndex != -1) {
    System.out.println("Score " + targetScore + " found at position " + (foundIndex + 1));
} else {
    System.out.println("Score " + targetScore + " not found!");
}
```

#### Copying Arrays
```java
int[] original = {1, 2, 3, 4, 5};

// Method 1: Manual copying
int[] copy1 = new int[original.length];
for (int i = 0; i < original.length; i++) {
    copy1[i] = original[i];
}

// Method 2: Using System.arraycopy()
int[] copy2 = new int[original.length];
System.arraycopy(original, 0, copy2, 0, original.length);

// Method 3: Using Arrays.copyOf()
import java.util.Arrays;
int[] copy3 = Arrays.copyOf(original, original.length);

// Verify copies
System.out.println("Original: " + Arrays.toString(original));
System.out.println("Copy 1: " + Arrays.toString(copy1));
System.out.println("Copy 2: " + Arrays.toString(copy2));
System.out.println("Copy 3: " + Arrays.toString(copy3));
```

## Multi-Dimensional Arrays

### 2D Arrays (Arrays of Arrays)

#### Simple Explanation
**2D Array** is like a **table** or **grid** - rows and columns of data.

#### Declaration and Initialization
```java
// Declaration
int[][] matrix;
String[][] table;

// Memory allocation
matrix = new int[3][4];      // 3 rows, 4 columns
table = new String[2][3];    // 2 rows, 3 columns

// Declaration with initialization
int[][] numbers = {
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 10, 11, 12}
};

// Visual representation:
//     Col0 Col1 Col2 Col3
// Row0  1    2    3    4
// Row1  5    6    7    8
// Row2  9   10   11   12
```

#### Accessing 2D Array Elements
```java
int[][] matrix = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

// Access individual elements
int element = matrix[1][2];    // element = 60 (row 1, column 2)

// Modify elements
matrix[0][1] = 25;             // Changes 20 to 25

// Get dimensions
int rows = matrix.length;           // rows = 3
int columns = matrix[0].length;     // columns = 3
```

#### Iterating Through 2D Arrays
```java
int[][] scores = {
    {85, 92, 78},
    {96, 88, 91},
    {83, 79, 94}
};

// Using nested for loops
System.out.println("Student Scores:");
for (int i = 0; i < scores.length; i++) {
    System.out.print("Student " + (i + 1) + ": ");
    for (int j = 0; j < scores[i].length; j++) {
        System.out.print(scores[i][j] + " ");
    }
    System.out.println();
}

// Using enhanced for loops
System.out.println("\nScores with enhanced for:");
int studentNum = 1;
for (int[] studentScores : scores) {
    System.out.print("Student " + studentNum++ + ": ");
    for (int score : studentScores) {
        System.out.print(score + " ");
    }
    System.out.println();
}
```

### 3D Arrays and Beyond
```java
// 3D Array: Think of it as a cube or multiple 2D arrays
int[][][] cube = new int[2][3][4];  // 2 layers, 3 rows, 4 columns

// Initialize with values
int[][][] data = {
    {
        {1, 2, 3},
        {4, 5, 6}
    },
    {
        {7, 8, 9},
        {10, 11, 12}
    }
};

// Access element
int value = data[1][0][2];  // value = 9 (layer 1, row 0, column 2)
```

## Common Array Utilities (java.util.Arrays)

### Useful Array Methods
```java
import java.util.Arrays;

int[] numbers = {45, 23, 78, 12, 67};

// 1. Convert array to string for printing
System.out.println("Array: " + Arrays.toString(numbers));

// 2. Sort array
Arrays.sort(numbers);
System.out.println("Sorted: " + Arrays.toString(numbers));

// 3. Binary search (only works on sorted arrays)
int index = Arrays.binarySearch(numbers, 45);
System.out.println("45 found at index: " + index);

// 4. Fill array with specific value
int[] filled = new int[5];
Arrays.fill(filled, 10);
System.out.println("Filled: " + Arrays.toString(filled));

// 5. Compare arrays
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
boolean areEqual = Arrays.equals(arr1, arr2);
System.out.println("Arrays equal: " + areEqual);

// 6. Copy array with different size
int[] original = {1, 2, 3, 4, 5};
int[] extended = Arrays.copyOf(original, 8);  // Size 8
System.out.println("Extended: " + Arrays.toString(extended));

int[] truncated = Arrays.copyOf(original, 3);  // Size 3
System.out.println("Truncated: " + Arrays.toString(truncated));
```

## Array vs ArrayList Comparison

| Feature | Array | ArrayList |
|---------|-------|-----------|
| **Size** | Fixed | Dynamic |
| **Memory** | Efficient | More overhead |
| **Performance** | Faster access | Slightly slower |
| **Data Type** | Primitives + Objects | Objects only |
| **Methods** | Limited | Many built-in methods |
| **Syntax** | `arr[index]` | `list.get(index)` |

```java
// Array example
int[] array = new int[5];
array[0] = 10;
int value = array[0];

// ArrayList example
import java.util.ArrayList;
ArrayList<Integer> list = new ArrayList<>();
list.add(10);
int value2 = list.get(0);
```

## Common Array Problems and Solutions

### 1. Array Index Out of Bounds
```java
int[] numbers = {10, 20, 30};

// Wrong - will throw ArrayIndexOutOfBoundsException
// System.out.println(numbers[5]);

// Correct - check bounds
int index = 5;
if (index >= 0 && index < numbers.length) {
    System.out.println(numbers[index]);
} else {
    System.out.println("Index out of bounds!");
}
```

### 2. Null Pointer Exception
```java
// Wrong - array reference is null
int[] numbers = null;
// System.out.println(numbers.length);  // NullPointerException

// Correct - check for null
if (numbers != null) {
    System.out.println("Length: " + numbers.length);
} else {
    System.out.println("Array is null!");
}
```

### 3. Comparing Arrays Incorrectly
```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};

// Wrong - compares references, not content
// boolean equal = (arr1 == arr2);  // false

// Correct - use Arrays.equals()
boolean equal = Arrays.equals(arr1, arr2);  // true
```

## Interview Questions & Answers

**Q1: What is the difference between array and ArrayList?**
**A:**
- **Array**: Fixed size, can store primitives, faster access, less memory overhead
- **ArrayList**: Dynamic size, stores objects only, more methods, automatic resizing

**Q2: How do you find the length of an array?**
**A:** Use the `length` property: `arrayName.length` (note: no parentheses, it's a property not a method)

**Q3: What happens if you access an array with invalid index?**
**A:** Throws `ArrayIndexOutOfBoundsException` at runtime. Always check bounds before accessing.

**Q4: Can you change the size of an array after creation?**
**A:** No, array size is fixed. You need to create a new array with different size and copy elements if needed.

**Q5: What is a 2D array and how do you access elements?**
**A:** 2D array is array of arrays. Access using `arr[row][column]` notation. Example: `matrix[2][3]` accesses element at row 2, column 3.

**Q6: How do you copy an array?**
**A:** 
- Manual loop
- `System.arraycopy()`
- `Arrays.copyOf()`
- `clone()` method

## Complete Example Program

```java
import java.util.Arrays;
import java.util.Scanner;

/**
 * Comprehensive array demonstration program
 */
public class ArraysExample {
    
    public static void main(String[] args) {
        // Demonstrate basic array operations
        demonstrateBasicArrays();
        
        // Demonstrate 2D arrays
        demonstrate2DArrays();
        
        // Demonstrate array utilities
        demonstrateArrayUtilities();
        
        // Interactive array example
        runGradeCalculator();
        
        // Advanced array operations
        demonstrateAdvancedOperations();
    }
    
    public static void demonstrateBasicArrays() {
        System.out.println("=== BASIC ARRAYS ===");
        
        // Different ways to create arrays
        int[] numbers = {85, 92, 78, 96, 88};
        String[] subjects = new String[]{"Math", "Science", "English"};
        double[] prices = new double[5];
        
        // Initialize prices array
        prices[0] = 19.99;
        prices[1] = 25.50;
        prices[2] = 12.75;
        prices[3] = 8.99;
        prices[4] = 35.25;
        
        // Display arrays
        System.out.println("Numbers: " + Arrays.toString(numbers));
        System.out.println("Subjects: " + Arrays.toString(subjects));
        System.out.println("Prices: " + Arrays.toString(prices));
        
        // Array statistics
        int sum = 0;
        int max = numbers[0];
        int min = numbers[0];
        
        for (int num : numbers) {
            sum += num;
            if (num > max) max = num;
            if (num < min) min = num;
        }
        
        double average = (double) sum / numbers.length;
        
        System.out.println("Sum: " + sum);
        System.out.println("Average: " + average);
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);
        System.out.println();
    }
    
    public static void demonstrate2DArrays() {
        System.out.println("=== 2D ARRAYS ===");
        
        // Student grades: 3 students, 4 subjects each
        int[][] grades = {
            {85, 92, 78, 96},  // Student 1
            {88, 84, 91, 89},  // Student 2
            {79, 95, 87, 93}   // Student 3
        };
        
        String[] subjects = {"Math", "Science", "English", "History"};
        
        // Display grade table
        System.out.printf("%-10s", "Student");
        for (String subject : subjects) {
            System.out.printf("%-10s", subject);
        }
        System.out.printf("%-10s%n", "Average");
        System.out.println("-".repeat(60));
        
        // Calculate and display grades for each student
        for (int i = 0; i < grades.length; i++) {
            System.out.printf("%-10s", "Student " + (i + 1));
            
            int sum = 0;
            for (int j = 0; j < grades[i].length; j++) {
                System.out.printf("%-10d", grades[i][j]);
                sum += grades[i][j];
            }
            
            double average = (double) sum / grades[i].length;
            System.out.printf("%-10.1f%n", average);
        }
        
        // Calculate subject averages
        System.out.println("-".repeat(60));
        System.out.printf("%-10s", "Average");
        for (int j = 0; j < subjects.length; j++) {
            int sum = 0;
            for (int i = 0; i < grades.length; i++) {
                sum += grades[i][j];
            }
            double average = (double) sum / grades.length;
            System.out.printf("%-10.1f", average);
        }
        System.out.println();
        System.out.println();
    }
    
    public static void demonstrateArrayUtilities() {
        System.out.println("=== ARRAY UTILITIES ===");
        
        int[] original = {45, 23, 78, 12, 67, 89, 34};
        System.out.println("Original: " + Arrays.toString(original));
        
        // Copy array
        int[] copy = Arrays.copyOf(original, original.length);
        System.out.println("Copy: " + Arrays.toString(copy));
        
        // Sort array (modifies original)
        Arrays.sort(copy);
        System.out.println("Sorted: " + Arrays.toString(copy));
        
        // Binary search (only works on sorted arrays)
        int searchValue = 67;
        int index = Arrays.binarySearch(copy, searchValue);
        System.out.println("Value " + searchValue + " found at index: " + index);
        
        // Fill array
        int[] filled = new int[5];
        Arrays.fill(filled, 42);
        System.out.println("Filled: " + Arrays.toString(filled));
        
        // Compare arrays
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        int[] arr3 = {1, 2, 4};
        
        System.out.println("arr1 equals arr2: " + Arrays.equals(arr1, arr2));
        System.out.println("arr1 equals arr3: " + Arrays.equals(arr1, arr3));
        System.out.println();
    }
    
    public static void runGradeCalculator() {
        System.out.println("=== INTERACTIVE GRADE CALCULATOR ===");
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of students: ");
        int numStudents = scanner.nextInt();
        
        String[] studentNames = new String[numStudents];
        double[] averages = new double[numStudents];
        
        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter name for student " + (i + 1) + ": ");
            studentNames[i] = scanner.next();
            
            System.out.print("Enter number of grades for " + studentNames[i] + ": ");
            int numGrades = scanner.nextInt();
            
            double[] grades = new double[numGrades];
            double sum = 0;
            
            for (int j = 0; j < numGrades; j++) {
                System.out.print("Enter grade " + (j + 1) + ": ");
                grades[j] = scanner.nextDouble();
                sum += grades[j];
            }
            
            averages[i] = sum / numGrades;
            System.out.println(studentNames[i] + "'s average: " + averages[i]);
            System.out.println();
        }
        
        // Display summary
        System.out.println("=== GRADE SUMMARY ===");
        double classTotal = 0;
        for (int i = 0; i < numStudents; i++) {
            char letterGrade = calculateLetterGrade(averages[i]);
            System.out.printf("%-15s: %.1f (%c)%n", studentNames[i], averages[i], letterGrade);
            classTotal += averages[i];
        }
        
        double classAverage = classTotal / numStudents;
        System.out.printf("Class Average: %.1f%n", classAverage);
        System.out.println();
    }
    
    public static char calculateLetterGrade(double average) {
        if (average >= 90) return 'A';
        if (average >= 80) return 'B';
        if (average >= 70) return 'C';
        if (average >= 60) return 'D';
        return 'F';
    }
    
    public static void demonstrateAdvancedOperations() {
        System.out.println("=== ADVANCED ARRAY OPERATIONS ===");
        
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original: " + Arrays.toString(numbers));
        
        // Bubble sort implementation
        int[] bubbleSorted = Arrays.copyOf(numbers, numbers.length);
        bubbleSort(bubbleSorted);
        System.out.println("Bubble Sorted: " + Arrays.toString(bubbleSorted));
        
        // Reverse array
        int[] reversed = Arrays.copyOf(numbers, numbers.length);
        reverseArray(reversed);
        System.out.println("Reversed: " + Arrays.toString(reversed));
        
        // Find second largest
        int secondLargest = findSecondLargest(numbers);
        System.out.println("Second largest: " + secondLargest);
        
        // Remove duplicates
        int[] withDuplicates = {1, 2, 2, 3, 4, 4, 5};
        int[] unique = removeDuplicates(withDuplicates);
        System.out.println("With duplicates: " + Arrays.toString(withDuplicates));
        System.out.println("Unique elements: " + Arrays.toString(unique));
    }
    
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    public static void reverseArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    public static int findSecondLargest(int[] arr) {
        if (arr.length < 2) return -1;
        
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }
        
        return secondLargest;
    }
    
    public static int[] removeDuplicates(int[] arr) {
        if (arr.length <= 1) return arr;
        
        int[] temp = new int[arr.length];
        int uniqueCount = 0;
        
        for (int i = 0; i < arr.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (arr[i] == temp[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                temp[uniqueCount++] = arr[i];
            }
        }
        
        return Arrays.copyOf(temp, uniqueCount);
    }
}
```

## Key Takeaways

1. **Arrays store multiple elements** of same type in contiguous memory
2. **Indexing starts from 0** and goes to length-1
3. **Array size is fixed** once created and cannot be changed
4. **Use array.length** to get size (property, not method)
5. **Enhanced for loop** is cleaner for simple iteration
6. **2D arrays** are arrays of arrays (matrix-like structure)
7. **Arrays.toString()** is useful for printing array contents
8. **Always check bounds** to avoid ArrayIndexOutOfBoundsException
9. **Arrays are objects** in Java, even for primitive types
10. **Use Collections** (ArrayList) when you need dynamic sizing

---

*Remember: Arrays are like organized storage containers - they keep your data neat, accessible, and efficiently managed in memory!*
