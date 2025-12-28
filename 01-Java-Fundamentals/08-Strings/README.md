# Java Strings - Working with Text Data

## What are Strings?

A String is a **sequence of characters** that represents text. Think of it as:
- **A sentence** made up of individual letters
- **A chain** where each link is a character
- **Text data** that you can read, modify, and manipulate

```java
String message = "Hello, World!";
//               H e l l o ,   W o r l d !
//               0 1 2 3 4 5 6 7 8 9 10 11 12
```

In Java, Strings are **objects** (not primitive types) with many useful methods.

## Why are Strings Important?

Strings are everywhere in programming:
- **User input**: Names, addresses, passwords
- **File processing**: Reading and writing text files
- **Web development**: URLs, HTML content, JSON data
- **Data formatting**: Reports, messages, output display

---

## String Creation and Declaration

### Method 1: String Literal (Most Common)
```java
public class StringBasics {
    public static void main(String[] args) {
        // String literals - stored in string pool for efficiency
        String name = "John Doe";
        String greeting = "Hello, World!";
        String empty = "";
        
        System.out.println("Name: " + name);
        System.out.println("Greeting: " + greeting);
        System.out.println("Empty string: '" + empty + "'");
        
        // String properties
        System.out.println("Name length: " + name.length());
        System.out.println("Greeting length: " + greeting.length());
        System.out.println("Empty string length: " + empty.length());
    }
}
```

### Method 2: Using new Keyword
```java
public class StringCreation {
    public static void main(String[] args) {
        // Using new keyword (creates new object every time)
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        
        // String literals (may reuse objects from string pool)
        String str3 = "Hello";
        String str4 = "Hello";
        
        // Comparing references vs content
        System.out.println("str1 == str2: " + (str1 == str2));         // false
        System.out.println("str3 == str4: " + (str3 == str4));         // true
        System.out.println("str1.equals(str2): " + str1.equals(str2)); // true
        System.out.println("str1.equals(str3): " + str1.equals(str3)); // true
    }
}
```

### Method 3: From char Array
```java
public class StringFromChars {
    public static void main(String[] args) {
        // Create string from character array
        char[] letters = {'J', 'a', 'v', 'a'};
        String language = new String(letters);
        
        System.out.println("Language: " + language);
        
        // Create string from part of char array
        char[] message = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};
        String greeting = new String(message, 0, 5);  // "Hello"
        String target = new String(message, 6, 5);    // "World"
        
        System.out.println("Greeting: " + greeting);
        System.out.println("Target: " + target);
    }
}
```

---

## Essential String Methods

### 1. Basic Information Methods

```java
public class StringInfo {
    public static void main(String[] args) {
        String text = "Java Programming";
        
        // Length
        System.out.println("Text: '" + text + "'");
        System.out.println("Length: " + text.length());
        
        // Check if empty
        System.out.println("Is empty: " + text.isEmpty());
        
        // Check if blank (empty or only whitespace) - Java 11+
        System.out.println("Is blank: " + text.isBlank());
        
        String spaces = "   ";
        System.out.println("Spaces empty: " + spaces.isEmpty());   // false
        System.out.println("Spaces blank: " + spaces.isBlank());   // true
    }
}
```

### 2. Character Access and Manipulation

```java
public class StringAccess {
    public static void main(String[] args) {
        String word = "Programming";
        
        // Get character at specific index
        System.out.println("Word: " + word);
        System.out.println("First character: " + word.charAt(0));
        System.out.println("Last character: " + word.charAt(word.length() - 1));
        System.out.println("Middle character: " + word.charAt(word.length() / 2));
        
        // Get character codes (ASCII/Unicode values)
        System.out.println("ASCII of 'P': " + (int) word.charAt(0));
        System.out.println("ASCII of 'g': " + (int) word.charAt(10));
        
        // Convert to character array
        char[] characters = word.toCharArray();
        System.out.println("Characters: ");
        for (int i = 0; i < characters.length; i++) {
            System.out.println("Index " + i + ": " + characters[i]);
        }
    }
}
```

### 3. Case Conversion Methods

```java
public class StringCase {
    public static void main(String[] args) {
        String original = "Java Programming Language";
        
        // Case conversions
        String uppercase = original.toUpperCase();
        String lowercase = original.toLowerCase();
        
        System.out.println("Original: " + original);
        System.out.println("Uppercase: " + uppercase);
        System.out.println("Lowercase: " + lowercase);
        
        // Real-world example: Email validation
        String email1 = "John.Doe@GMAIL.COM";
        String email2 = "john.doe@gmail.com";
        
        // Normalize emails to lowercase for comparison
        String normalizedEmail1 = email1.toLowerCase();
        String normalizedEmail2 = email2.toLowerCase();
        
        System.out.println("\nEmail comparison:");
        System.out.println("Original emails equal: " + email1.equals(email2));
        System.out.println("Normalized emails equal: " + normalizedEmail1.equals(normalizedEmail2));
    }
}
```

### 4. String Searching and Checking

```java
public class StringSearching {
    public static void main(String[] args) {
        String sentence = "Java is a powerful programming language";
        
        // Check if string contains substring
        System.out.println("Contains 'Java': " + sentence.contains("Java"));
        System.out.println("Contains 'Python': " + sentence.contains("Python"));
        
        // Find index of substring
        int javaIndex = sentence.indexOf("Java");
        int powerfulIndex = sentence.indexOf("powerful");
        int notFoundIndex = sentence.indexOf("Python");
        
        System.out.println("Index of 'Java': " + javaIndex);
        System.out.println("Index of 'powerful': " + powerfulIndex);
        System.out.println("Index of 'Python': " + notFoundIndex); // -1 means not found
        
        // Check start and end
        System.out.println("Starts with 'Java': " + sentence.startsWith("Java"));
        System.out.println("Ends with 'language': " + sentence.endsWith("language"));
        System.out.println("Starts with 'Python': " + sentence.startsWith("Python"));
        
        // Find last occurrence
        String repeated = "Java is great, Java is powerful, Java is popular";
        System.out.println("First 'Java': " + repeated.indexOf("Java"));
        System.out.println("Last 'Java': " + repeated.lastIndexOf("Java"));
    }
}
```

### 5. String Extraction (Substrings)

```java
public class StringExtraction {
    public static void main(String[] args) {
        String fullName = "John Michael Doe";
        String email = "john.doe@company.com";
        String phoneNumber = "+1-555-123-4567";
        
        // Extract parts using substring
        System.out.println("Full name: " + fullName);
        
        // Extract first name (from start to first space)
        int firstSpace = fullName.indexOf(" ");
        String firstName = fullName.substring(0, firstSpace);
        
        // Extract last name (from last space to end)
        int lastSpace = fullName.lastIndexOf(" ");
        String lastName = fullName.substring(lastSpace + 1);
        
        // Extract middle name
        String middleName = fullName.substring(firstSpace + 1, lastSpace);
        
        System.out.println("First name: " + firstName);
        System.out.println("Middle name: " + middleName);
        System.out.println("Last name: " + lastName);
        
        // Extract email parts
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);
        
        System.out.println("\nEmail: " + email);
        System.out.println("Username: " + username);
        System.out.println("Domain: " + domain);
        
        // Extract phone number parts
        String[] phoneParts = phoneNumber.split("-");
        System.out.println("\nPhone: " + phoneNumber);
        System.out.println("Country code: " + phoneParts[0]);
        System.out.println("Area code: " + phoneParts[1]);
        System.out.println("Exchange: " + phoneParts[2]);
        System.out.println("Number: " + phoneParts[3]);
    }
}
```

### 6. String Modification Methods

```java
public class StringModification {
    public static void main(String[] args) {
        String messy = "   Hello World   ";
        String password = "myPassword123";
        String sentence = "Java-is-awesome";
        
        // Remove whitespace
        System.out.println("Original: '" + messy + "'");
        System.out.println("Trimmed: '" + messy.trim() + "'");
        
        // Replace characters and substrings
        System.out.println("\nPassword: " + password);
        String hiddenPassword = password.replace('s', '*');
        System.out.println("Hidden: " + hiddenPassword);
        
        // Replace all occurrences
        String noNumbers = password.replaceAll("[0-9]", "X");
        System.out.println("No numbers: " + noNumbers);
        
        // Replace first occurrence only
        String firstReplaced = sentence.replaceFirst("-", " ");
        System.out.println("Sentence: " + sentence);
        System.out.println("First replaced: " + firstReplaced);
        
        // Replace all occurrences
        String allReplaced = sentence.replaceAll("-", " ");
        System.out.println("All replaced: " + allReplaced);
    }
}
```

---

## String Concatenation

### Method 1: Using + Operator
```java
public class StringConcatenation {
    public static void main(String[] args) {
        String firstName = "John";
        String lastName = "Doe";
        int age = 30;
        double salary = 75000.50;
        
        // Simple concatenation
        String fullName = firstName + " " + lastName;
        System.out.println("Full name: " + fullName);
        
        // Concatenation with other types
        String info = "Name: " + fullName + ", Age: " + age + ", Salary: $" + salary;
        System.out.println(info);
        
        // Building complex strings
        String address = "123 Main St, " + "Anytown, " + "USA " + "12345";
        System.out.println("Address: " + address);
        
        // Be careful with operator precedence
        String calculation = "Result: " + 5 + 3;        // "Result: 53"
        String calculation2 = "Result: " + (5 + 3);     // "Result: 8"
        
        System.out.println(calculation);
        System.out.println(calculation2);
    }
}
```

### Method 2: Using concat() Method
```java
public class StringConcat {
    public static void main(String[] args) {
        String greeting = "Hello";
        String target = "World";
        
        // Using concat method
        String message = greeting.concat(", ").concat(target).concat("!");
        System.out.println("Message: " + message);
        
        // Chaining multiple concat calls
        String result = "Java"
                       .concat(" is")
                       .concat(" a")
                       .concat(" programming")
                       .concat(" language");
        System.out.println("Result: " + result);
    }
}
```

### Method 3: Using String.format()
```java
public class StringFormatting {
    public static void main(String[] args) {
        String name = "Alice";
        int age = 25;
        double gpa = 3.85;
        boolean isStudent = true;
        
        // Using String.format (similar to printf in C)
        String formatted = String.format("Name: %s, Age: %d, GPA: %.2f, Student: %b", 
                                        name, age, gpa, isStudent);
        System.out.println(formatted);
        
        // Format numbers with specific patterns
        double price = 1234.567;
        String priceFormatted = String.format("Price: $%.2f", price);
        System.out.println(priceFormatted);
        
        // Format with padding and alignment
        System.out.println("Product Report:");
        System.out.println(String.format("%-15s %8s %10s", "Product", "Quantity", "Price"));
        System.out.println(String.format("%-15s %8d $%9.2f", "Laptop", 5, 999.99));
        System.out.println(String.format("%-15s %8d $%9.2f", "Mouse", 25, 29.99));
        System.out.println(String.format("%-15s %8d $%9.2f", "Keyboard", 10, 79.99));
    }
}
```

---

## StringBuilder - Efficient String Building

When you need to build strings with many operations, StringBuilder is more efficient:

```java
public class StringBuilderExample {
    public static void main(String[] args) {
        // Problem: String concatenation in loops is inefficient
        // Each + operation creates a new String object
        
        long startTime = System.currentTimeMillis();
        
        // Inefficient way (creates many temporary String objects)
        String result1 = "";
        for (int i = 0; i < 1000; i++) {
            result1 += "Number " + i + " ";
        }
        
        long middleTime = System.currentTimeMillis();
        
        // Efficient way using StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Number ").append(i).append(" ");
        }
        String result2 = sb.toString();
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("String concatenation time: " + (middleTime - startTime) + "ms");
        System.out.println("StringBuilder time: " + (endTime - middleTime) + "ms");
        
        // StringBuilder methods
        StringBuilder builder = new StringBuilder("Hello");
        
        // Append
        builder.append(" World");
        builder.append("!").append(" Java is great!");
        System.out.println("After append: " + builder.toString());
        
        // Insert
        builder.insert(5, " Beautiful");
        System.out.println("After insert: " + builder.toString());
        
        // Replace
        builder.replace(6, 15, "Amazing");
        System.out.println("After replace: " + builder.toString());
        
        // Delete
        builder.delete(5, 13);
        System.out.println("After delete: " + builder.toString());
        
        // Reverse
        builder.reverse();
        System.out.println("After reverse: " + builder.toString());
    }
}
```

---

## String Comparison

### Proper Ways to Compare Strings

```java
public class StringComparison {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");
        String str4 = "HELLO";
        String str5 = null;
        
        // == compares references, not content
        System.out.println("str1 == str2: " + (str1 == str2));     // true (string pool)
        System.out.println("str1 == str3: " + (str1 == str3));     // false (different objects)
        
        // equals() compares content (case-sensitive)
        System.out.println("str1.equals(str2): " + str1.equals(str2));     // true
        System.out.println("str1.equals(str3): " + str1.equals(str3));     // true
        System.out.println("str1.equals(str4): " + str1.equals(str4));     // false
        
        // equalsIgnoreCase() ignores case
        System.out.println("str1.equalsIgnoreCase(str4): " + str1.equalsIgnoreCase(str4)); // true
        
        // Safe comparison with null values
        // System.out.println(str5.equals(str1)); // NullPointerException!
        
        // Safe ways to compare with potential null values
        System.out.println("Objects.equals(str1, str5): " + java.util.Objects.equals(str1, str5)); // false
        System.out.println("Objects.equals(str5, str5): " + java.util.Objects.equals(str5, str5)); // true
        
        // compareTo() for lexicographic comparison
        System.out.println("str1.compareTo(str2): " + str1.compareTo(str2)); // 0 (equal)
        System.out.println("str1.compareTo(str4): " + str1.compareTo(str4)); // positive (str1 > str4)
        
        String[] names = {"Charlie", "Alice", "Bob", "Diana"};
        java.util.Arrays.sort(names);
        System.out.println("Sorted names: " + java.util.Arrays.toString(names));
    }
}
```

---

## Real-World String Applications

### 1. User Input Validation

```java
public class InputValidation {
    public static void main(String[] args) {
        // Simulate user inputs
        String[] emails = {"john@gmail.com", "invalid-email", "alice@yahoo.com", "@domain.com"};
        String[] passwords = {"weak", "StrongPass123!", "12345678", "NoSpecial123"};
        String[] phoneNumbers = {"+1-555-123-4567", "5551234567", "555-123-45678", "invalid"};
        
        System.out.println("=== Email Validation ===");
        for (String email : emails) {
            boolean isValid = validateEmail(email);
            System.out.println(email + " -> " + (isValid ? "VALID" : "INVALID"));
        }
        
        System.out.println("\n=== Password Validation ===");
        for (String password : passwords) {
            boolean isValid = validatePassword(password);
            System.out.println(password + " -> " + (isValid ? "STRONG" : "WEAK"));
        }
        
        System.out.println("\n=== Phone Number Validation ===");
        for (String phone : phoneNumbers) {
            boolean isValid = validatePhoneNumber(phone);
            System.out.println(phone + " -> " + (isValid ? "VALID" : "INVALID"));
        }
    }
    
    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        
        // Check for @ symbol
        if (!email.contains("@")) return false;
        
        // Check format: something@something.something
        String[] parts = email.split("@");
        if (parts.length != 2) return false;
        
        String username = parts[0];
        String domain = parts[1];
        
        // Username should not be empty and contain valid characters
        if (username.isEmpty() || username.contains(" ")) return false;
        
        // Domain should contain a dot and not be empty
        if (!domain.contains(".") || domain.startsWith(".") || domain.endsWith(".")) {
            return false;
        }
        
        return true;
    }
    
    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) return false;
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    public static boolean validatePhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        
        // Remove all non-digit characters except +
        String cleaned = phone.replaceAll("[^+\\d]", "");
        
        // Check if it starts with + for international format
        if (cleaned.startsWith("+")) {
            return cleaned.length() >= 11 && cleaned.length() <= 15;
        } else {
            return cleaned.length() == 10; // US format
        }
    }
}
```

### 2. Text Processing System

```java
public class TextProcessor {
    public static void main(String[] args) {
        String article = "Java is a powerful programming language. " +
                        "Java was developed by Sun Microsystems. " +
                        "Java is platform independent and object-oriented. " +
                        "Many large applications are built with Java.";
        
        System.out.println("=== Text Analysis Report ===");
        System.out.println("Original text:");
        System.out.println(article);
        System.out.println();
        
        // Basic statistics
        int wordCount = countWords(article);
        int sentenceCount = countSentences(article);
        int characterCount = article.length();
        int characterCountNoSpaces = article.replaceAll("\\s", "").length();
        
        System.out.println("Statistics:");
        System.out.println("- Characters (with spaces): " + characterCount);
        System.out.println("- Characters (without spaces): " + characterCountNoSpaces);
        System.out.println("- Words: " + wordCount);
        System.out.println("- Sentences: " + sentenceCount);
        
        // Word frequency
        System.out.println("\nWord Frequency:");
        countWordFrequency(article);
        
        // Text transformations
        System.out.println("\nText Transformations:");
        System.out.println("UPPERCASE: " + article.toUpperCase());
        System.out.println("lowercase: " + article.toLowerCase());
        System.out.println("Title Case: " + toTitleCase(article));
        
        // Find and replace
        String modernized = article.replaceAll("Java", "Modern Java");
        System.out.println("\nModernized text:");
        System.out.println(modernized);
    }
    
    public static int countWords(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }
    
    public static int countSentences(String text) {
        if (text == null || text.isEmpty()) return 0;
        return text.split("[.!?]+").length;
    }
    
    public static void countWordFrequency(String text) {
        String[] words = text.toLowerCase()
                            .replaceAll("[^a-zA-Z\\s]", "")
                            .split("\\s+");
        
        // Simple frequency counter using arrays (in real projects, use HashMap)
        String[] uniqueWords = new String[words.length];
        int[] frequencies = new int[words.length];
        int uniqueCount = 0;
        
        for (String word : words) {
            boolean found = false;
            for (int i = 0; i < uniqueCount; i++) {
                if (uniqueWords[i].equals(word)) {
                    frequencies[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                uniqueWords[uniqueCount] = word;
                frequencies[uniqueCount] = 1;
                uniqueCount++;
            }
        }
        
        // Print frequencies
        for (int i = 0; i < uniqueCount; i++) {
            System.out.println("- " + uniqueWords[i] + ": " + frequencies[i]);
        }
    }
    
    public static String toTitleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        
        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        
        return result.toString();
    }
}
```

### 3. CSV Data Parser

```java
public class CSVParser {
    public static void main(String[] args) {
        // Simulate CSV data
        String csvData = "Name,Age,City,Salary\n" +
                        "John Doe,30,New York,75000\n" +
                        "Jane Smith,25,Los Angeles,68000\n" +
                        "Bob Johnson,35,Chicago,82000\n" +
                        "Alice Brown,28,Houston,71000";
        
        System.out.println("=== CSV Data Parser ===");
        System.out.println("Raw CSV data:");
        System.out.println(csvData);
        System.out.println();
        
        parseCSV(csvData);
    }
    
    public static void parseCSV(String csvData) {
        String[] lines = csvData.split("\n");
        
        if (lines.length == 0) {
            System.out.println("No data to parse.");
            return;
        }
        
        // Parse header
        String[] headers = lines[0].split(",");
        System.out.println("Parsed Data:");
        System.out.println("============");
        
        // Print formatted header
        for (String header : headers) {
            System.out.printf("%-15s", header);
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        
        // Parse data rows
        double totalSalary = 0;
        int employeeCount = 0;
        
        for (int i = 1; i < lines.length; i++) {
            String[] values = lines[i].split(",");
            
            // Print formatted data
            for (String value : values) {
                System.out.printf("%-15s", value);
            }
            System.out.println();
            
            // Calculate total salary (assuming salary is in the last column)
            try {
                double salary = Double.parseDouble(values[values.length - 1]);
                totalSalary += salary;
                employeeCount++;
            } catch (NumberFormatException e) {
                System.out.println("Warning: Invalid salary format for employee " + values[0]);
            }
        }
        
        // Calculate and display average salary
        if (employeeCount > 0) {
            double averageSalary = totalSalary / employeeCount;
            System.out.println();
            System.out.println("Summary:");
            System.out.printf("Total Employees: %d\n", employeeCount);
            System.out.printf("Average Salary: $%.2f\n", averageSalary);
        }
    }
}
```

---

## Common String Mistakes and Solutions

### 1. Using == Instead of equals()
```java
// ✗ WRONG
String str1 = new String("Hello");
String str2 = new String("Hello");
if (str1 == str2) { // This compares references, not content!
    System.out.println("Equal");
}

// ✓ CORRECT
if (str1.equals(str2)) { // This compares content
    System.out.println("Equal");
}
```

### 2. Not Handling Null Strings
```java
// ✗ WRONG - Can cause NullPointerException
String str = null;
if (str.equals("Hello")) { // NullPointerException!
    System.out.println("Found Hello");
}

// ✓ CORRECT - Multiple safe approaches
// Approach 1: Check for null first
if (str != null && str.equals("Hello")) {
    System.out.println("Found Hello");
}

// Approach 2: Use constant first (if comparing with literal)
if ("Hello".equals(str)) { // Safe even if str is null
    System.out.println("Found Hello");
}

// Approach 3: Use Objects.equals() for both potentially null
if (java.util.Objects.equals(str, otherStr)) {
    System.out.println("Equal");
}
```

### 3. Inefficient String Concatenation in Loops
```java
// ✗ WRONG - Creates many temporary objects
String result = "";
for (int i = 0; i < 1000; i++) {
    result += "Item " + i + " ";
}

// ✓ CORRECT - Use StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("Item ").append(i).append(" ");
}
String result = sb.toString();
```

### 4. Ignoring String Immutability
```java
// ✗ WRONG - Thinking string is modified
String str = "Hello";
str.toUpperCase(); // This doesn't change str!
System.out.println(str); // Still prints "Hello"

// ✓ CORRECT - Assign the result
String str = "Hello";
str = str.toUpperCase(); // Now str is changed
System.out.println(str); // Prints "HELLO"
```

## Practice Exercises

### Exercise 1: Name Formatter
```java
public class NameFormatter {
    public static void main(String[] args) {
        // Given various name formats, normalize them
        String[] names = {
            "john doe",
            "JANE SMITH", 
            "bob_johnson",
            "alice-brown",
            "charlie.wilson"
        };
        
        // Convert all to "First Last" format (Title Case)
        // Remove underscores, hyphens, dots
        // Handle edge cases (empty strings, null values)
    }
}
```

### Exercise 2: Password Generator
```java
public class PasswordGenerator {
    public static void main(String[] args) {
        // Create a password generator that:
        // 1. Takes a base word/phrase
        // 2. Adds numbers and special characters
        // 3. Ensures minimum length and complexity
        // 4. Provides multiple variations
        
        String baseWord = "JavaProgramming";
        // Generate secure passwords based on this
    }
}
```

### Exercise 3: Log File Processor
```java
public class LogProcessor {
    public static void main(String[] args) {
        // Simulate log entries
        String[] logEntries = {
            "2024-01-15 10:30:45 INFO User john.doe logged in",
            "2024-01-15 10:31:12 ERROR Database connection failed",
            "2024-01-15 10:31:45 INFO User jane.smith logged in",
            "2024-01-15 10:32:30 WARN Low disk space detected",
            "2024-01-15 10:33:15 ERROR File not found: data.txt"
        };
        
        // Parse and analyze:
        // 1. Extract timestamp, level, and message
        // 2. Count entries by log level
        // 3. Find all error messages
        // 4. Extract user activities
    }
}
```

## Quick Reference

### String Creation
```java
String str1 = "Hello";                    // String literal
String str2 = new String("Hello");        // New object
String str3 = new String(charArray);      // From char array
```

### Essential Methods
```java
str.length()                    // Get length
str.charAt(index)              // Get character at index
str.substring(start, end)      // Extract substring
str.indexOf("text")            // Find index of substring
str.contains("text")           // Check if contains substring
str.startsWith("prefix")       // Check if starts with
str.endsWith("suffix")         // Check if ends with
str.toLowerCase()              // Convert to lowercase
str.toUpperCase()              // Convert to uppercase
str.trim()                     // Remove leading/trailing spaces
str.replace(old, new)          // Replace characters/substrings
str.split("delimiter")         // Split into array
```

### String Comparison
```java
str1.equals(str2)                      // Content comparison
str1.equalsIgnoreCase(str2)           // Ignore case comparison
str1.compareTo(str2)                  // Lexicographic comparison
Objects.equals(str1, str2)            // Null-safe comparison
```

### String Building
```java
StringBuilder sb = new StringBuilder();
sb.append("text").append(number).append(char);
String result = sb.toString();
```

## Key Takeaways

1. **Strings are immutable** - methods return new strings
2. **Use equals()** for content comparison, not ==
3. **Handle null values** safely in string operations
4. **Use StringBuilder** for multiple concatenations
5. **String literals** are stored in string pool for efficiency
6. **Case matters** in string operations unless using ignore-case methods
7. **Validate user input** using string methods
8. **Index starts at 0** and goes to length()-1
9. **Many string methods** return new strings, don't modify original
10. **Choose appropriate method** for each string operation need