# Strings in Java - Working with Text

## Simple Explanation

Think of a **String** as a **chain of letters** (like a necklace made of letter beads):
- Each **bead** is a character
- The **chain** is immutable (cannot change individual beads)
- You can make a **new chain** by combining or modifying existing ones
- You can **examine** any bead by its position

### Real-World Analogies
- **Book** = String (sequence of characters)
- **DNA sequence** = String (sequence of genetic codes)
- **Phone number** = String (sequence of digits)
- **Sentence** = String (sequence of words and punctuation)

## Professional Definition

A **String** in Java is an object that represents a sequence of characters. Strings are immutable, meaning once created, their content cannot be changed. Any operation that appears to modify a string actually creates a new string object.

## String Characteristics

### Key Properties:
- **Immutable**: Cannot be changed after creation
- **Object Type**: Reference type, not primitive
- **UTF-16 Encoding**: Supports Unicode characters
- **String Pool**: Java maintains a pool of string literals for memory optimization
- **Rich API**: Extensive methods for string manipulation

### Memory Representation:
```
String name = "Hello";

Memory:
┌─────────────────┐
│ String Object   │
├─────────────────┤
│ char[] value    │ → ['H','e','l','l','o']
│ int length = 5  │
│ hash code       │
└─────────────────┘
```

## String Creation

### 1. String Literals
```java
// Using string literals (preferred method)
String name = "Alice";
String message = "Hello World!";
String empty = "";

// String literals are stored in String Pool
String str1 = "Hello";
String str2 = "Hello";
// str1 and str2 refer to the same object in memory
```

### 2. Using new Keyword
```java
// Creates new String object (not recommended for literals)
String name = new String("Alice");
String message = new String("Hello World!");

// This creates two objects: one in pool, one in heap
String str1 = "Hello";        // In string pool
String str2 = new String("Hello");  // New object in heap
// str1 == str2 is false (different objects)
// str1.equals(str2) is true (same content)
```

### 3. From Character Array
```java
char[] charArray = {'H', 'e', 'l', 'l', 'o'};
String fromChars = new String(charArray);
System.out.println(fromChars);  // "Hello"

// From part of char array
String partial = new String(charArray, 1, 3);  // "ell"
```

## String Operations

### 1. Basic String Information

#### Length and Empty Check
```java
String text = "Hello World";

// Get length
int length = text.length();          // length = 11
System.out.println("Length: " + length);

// Check if empty
String empty = "";
boolean isEmpty = empty.isEmpty();   // true
boolean isBlank = empty.isBlank();   // true (Java 11+)

// Check blank (contains only whitespace)
String spaces = "   ";
boolean isSpacesEmpty = spaces.isEmpty();  // false
boolean isSpacesBlank = spaces.isBlank();  // true (Java 11+)
```

#### Character Access
```java
String word = "Programming";

// Get character at specific index
char firstChar = word.charAt(0);     // 'P'
char lastChar = word.charAt(word.length() - 1);  // 'g'

// Get character code
int asciiValue = (int) firstChar;    // 80

// Convert to character array
char[] charArray = word.toCharArray();
System.out.println(Arrays.toString(charArray));
// ['P','r','o','g','r','a','m','m','i','n','g']
```

### 2. String Comparison

#### Content Comparison
```java
String str1 = "Hello";
String str2 = "Hello";
String str3 = "hello";
String str4 = new String("Hello");

// equals() - case sensitive
boolean equal1 = str1.equals(str2);        // true
boolean equal2 = str1.equals(str3);        // false
boolean equal3 = str1.equals(str4);        // true

// equalsIgnoreCase() - ignore case
boolean equalIgnoreCase = str1.equalsIgnoreCase(str3);  // true

// Never use == for content comparison!
boolean reference1 = (str1 == str2);       // true (same object in pool)
boolean reference2 = (str1 == str4);       // false (different objects)

// compareTo() - lexicographic comparison
String name1 = "Alice";
String name2 = "Bob";
String name3 = "Alice";

int result1 = name1.compareTo(name2);  // negative (Alice < Bob)
int result2 = name2.compareTo(name1);  // positive (Bob > Alice)
int result3 = name1.compareTo(name3);  // 0 (Alice == Alice)

// compareToIgnoreCase()
int result4 = "HELLO".compareToIgnoreCase("hello");  // 0
```

#### Prefix and Suffix Check
```java
String filename = "document.pdf";

// Check prefix (starts with)
boolean isDoc = filename.startsWith("doc");     // true
boolean isImage = filename.startsWith("img");   // false

// Check suffix (ends with)
boolean isPDF = filename.endsWith(".pdf");      // true
boolean isWord = filename.endsWith(".docx");    // false

// Check at specific position
boolean hasDocAt0 = filename.startsWith("doc", 0);  // true
boolean hasDocAt2 = filename.startsWith("doc", 2);  // false
```

### 3. String Searching

#### Finding Characters and Substrings
```java
String text = "Java Programming is fun!";

// Find first occurrence
int firstA = text.indexOf('a');         // 1 (first 'a')
int firstProg = text.indexOf("Programming");  // 5

// Find last occurrence  
int lastA = text.lastIndexOf('a');      // 7 (last 'a')
int lastProg = text.lastIndexOf("Programming");  // 5

// Find from specific position
int aAfterIndex5 = text.indexOf('a', 5);  // 7

// Check if contains
boolean hasJava = text.contains("Java");     // true
boolean hasPython = text.contains("Python"); // false

// Return -1 if not found
int notFound = text.indexOf("xyz");     // -1
```

### 4. String Extraction

#### Substring Operations
```java
String sentence = "Learning Java Programming";

// Extract substring from index to end
String fromIndex8 = sentence.substring(8);     // "Java Programming"

// Extract substring between indices
String javaPart = sentence.substring(8, 12);   // "Java"

// Extract characters
char[] chars = sentence.toCharArray();

// Get specific range as char array
// (No direct method, need custom approach)
String subText = sentence.substring(8, 12);
char[] subChars = subText.toCharArray();
```

### 5. String Modification (Creates New Strings)

#### Case Conversion
```java
String original = "Hello World";

// Convert to uppercase
String upper = original.toUpperCase();     // "HELLO WORLD"

// Convert to lowercase  
String lower = original.toLowerCase();     // "hello world"

// Original string unchanged (immutable)
System.out.println(original);             // "Hello World"
```

#### Trimming and Replacing
```java
String messy = "  Hello World  ";

// Remove leading and trailing whitespace
String trimmed = messy.trim();            // "Hello World"

// Replace characters
String text = "Hello World";
String replaced1 = text.replace('o', '0');      // "Hell0 W0rld"
String replaced2 = text.replace("World", "Java"); // "Hello Java"

// Replace all occurrences (regex)
String numbers = "123-456-7890";
String onlyNumbers = numbers.replaceAll("-", "");  // "1234567890"

// Replace first occurrence
String firstOnly = numbers.replaceFirst("-", ".");  // "123.456-7890"
```

### 6. String Building and Concatenation

#### Concatenation Methods
```java
// Method 1: Using + operator
String first = "Hello";
String second = "World";
String result1 = first + " " + second;    // "Hello World"

// Method 2: Using concat() method
String result2 = first.concat(" ").concat(second);  // "Hello World"

// Method 3: Using String.join() (Java 8+)
String result3 = String.join(" ", first, second);   // "Hello World"

// Join with custom delimiter
String[] words = {"Java", "is", "awesome"};
String sentence = String.join(" ", words);          // "Java is awesome"
String csv = String.join(",", words);               // "Java,is,awesome"
```

#### StringBuilder for Multiple Operations
```java
// Inefficient - creates many temporary strings
String result = "";
for (int i = 0; i < 1000; i++) {
    result += "a";  // Creates new string each time
}

// Efficient - uses mutable buffer
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("a");  // Modifies existing buffer
}
String result = sb.toString();

// StringBuilder methods
StringBuilder builder = new StringBuilder("Hello");
builder.append(" World");           // "Hello World"
builder.insert(5, " Beautiful");   // "Hello Beautiful World"  
builder.delete(5, 15);             // "Hello World"
builder.reverse();                 // "dlroW olleH"
String finalResult = builder.toString();
```

## String Formatting

### 1. printf-style Formatting
```java
// Using String.format()
String name = "Alice";
int age = 25;
double salary = 50000.75;

String formatted = String.format("Name: %s, Age: %d, Salary: $%.2f", 
                                 name, age, salary);
// "Name: Alice, Age: 25, Salary: $50000.75"

// Common format specifiers
String examples = String.format(
    "String: %s%n" +
    "Integer: %d%n" + 
    "Float: %.2f%n" +
    "Character: %c%n" +
    "Boolean: %b%n",
    "Hello", 42, 3.14159, 'A', true
);

// Using System.out.printf()
System.out.printf("Hello %s, you are %d years old%n", name, age);
```

### 2. Text Blocks (Java 15+)
```java
// Multi-line strings with text blocks
String html = """
    <html>
        <body>
            <h1>Welcome to Java</h1>
            <p>Learning strings is fun!</p>
        </body>
    </html>
    """;

String sql = """
    SELECT customer_name, order_date, total_amount
    FROM orders 
    WHERE order_date >= '2024-01-01'
    ORDER BY order_date DESC
    """;
```

## Common String Patterns and Use Cases

### 1. Input Validation
```java
public static boolean isValidEmail(String email) {
    return email != null && 
           email.contains("@") && 
           email.contains(".") &&
           email.indexOf("@") < email.lastIndexOf(".");
}

public static boolean isValidPhoneNumber(String phone) {
    if (phone == null) return false;
    
    // Remove common separators
    String cleaned = phone.replaceAll("[-()\\s]", "");
    
    // Check if all digits and proper length
    return cleaned.matches("\\d{10}");
}
```

### 2. String Processing
```java
public static String capitalizeWords(String text) {
    if (text == null || text.isEmpty()) return text;
    
    String[] words = text.toLowerCase().split("\\s+");
    StringBuilder result = new StringBuilder();
    
    for (String word : words) {
        if (word.length() > 0) {
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1))
                  .append(" ");
        }
    }
    
    return result.toString().trim();
}

// Example usage
String title = capitalizeWords("hello world java programming");
// "Hello World Java Programming"
```

### 3. String Parsing
```java
// Parse CSV data
String csvLine = "Alice,25,Engineer,50000";
String[] fields = csvLine.split(",");
String name = fields[0];
int age = Integer.parseInt(fields[1]);
String job = fields[2];
double salary = Double.parseDouble(fields[3]);

// Parse key-value pairs
String config = "host=localhost;port=8080;ssl=true";
String[] pairs = config.split(";");
Map<String, String> settings = new HashMap<>();

for (String pair : pairs) {
    String[] keyValue = pair.split("=");
    settings.put(keyValue[0], keyValue[1]);
}
```

## String Performance Considerations

### 1. String Pool and Memory
```java
// String literals are pooled
String s1 = "Hello";     // Created in string pool
String s2 = "Hello";     // Reuses existing object from pool
System.out.println(s1 == s2);  // true

// new String() creates separate object
String s3 = new String("Hello");  // New object in heap
System.out.println(s1 == s3);     // false

// intern() method adds to pool
String s4 = s3.intern();          // Returns pooled version
System.out.println(s1 == s4);     // true
```

### 2. StringBuilder vs String Concatenation
```java
// Inefficient for many concatenations
String result = "";
long start = System.currentTimeMillis();
for (int i = 0; i < 10000; i++) {
    result += "x";  // Creates new string each time
}
long time1 = System.currentTimeMillis() - start;

// Efficient for many concatenations
StringBuilder sb = new StringBuilder();
start = System.currentTimeMillis();
for (int i = 0; i < 10000; i++) {
    sb.append("x");  // Modifies buffer
}
String result2 = sb.toString();
long time2 = System.currentTimeMillis() - start;

System.out.println("String concatenation: " + time1 + "ms");
System.out.println("StringBuilder: " + time2 + "ms");
```

## Interview Questions & Answers

**Q1: What is the difference between String, StringBuilder, and StringBuffer?**
**A:**
- **String**: Immutable, thread-safe by nature, creates new objects for modifications
- **StringBuilder**: Mutable, not thread-safe, efficient for single-threaded concatenation
- **StringBuffer**: Mutable, thread-safe (synchronized), slower than StringBuilder

**Q2: What is String pool and why is it used?**
**A:** String pool is a memory area in heap where string literals are stored. It's used for memory optimization - identical string literals share the same memory location.

**Q3: Why should we use equals() instead of == for string comparison?**
**A:**
- `==` compares object references (memory addresses)
- `equals()` compares actual content
- Use `equals()` for content comparison, `==` only for reference comparison

**Q4: What happens when you do string concatenation with +?**
**A:** Java compiler optimizes consecutive literal concatenations. For variables, it may create temporary StringBuilder objects. For multiple concatenations in loops, use StringBuilder explicitly.

**Q5: How do you reverse a string in Java?**
**A:**
```java
// Method 1: Using StringBuilder
String original = "Hello";
String reversed = new StringBuilder(original).reverse().toString();

// Method 2: Manual approach
public static String reverse(String str) {
    char[] chars = str.toCharArray();
    int left = 0, right = str.length() - 1;
    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        left++; right--;
    }
    return new String(chars);
}
```

## Complete Example Program

```java
import java.util.*;

/**
 * Comprehensive String operations demonstration
 */
public class StringsExample {
    
    public static void main(String[] args) {
        // Demonstrate basic string operations
        demonstrateBasicOperations();
        
        // Demonstrate string comparison
        demonstrateStringComparison();
        
        // Demonstrate string manipulation
        demonstrateStringManipulation();
        
        // Demonstrate StringBuilder
        demonstrateStringBuilder();
        
        // Real-world examples
        demonstrateRealWorldExamples();
    }
    
    public static void demonstrateBasicOperations() {
        System.out.println("=== BASIC STRING OPERATIONS ===");
        
        String text = "Java Programming";
        
        // Basic information
        System.out.println("Original: " + text);
        System.out.println("Length: " + text.length());
        System.out.println("Character at index 5: " + text.charAt(5));
        System.out.println("Is empty: " + text.isEmpty());
        
        // Character operations
        char[] charArray = text.toCharArray();
        System.out.println("Character array: " + Arrays.toString(charArray));
        
        // Case operations
        System.out.println("Uppercase: " + text.toUpperCase());
        System.out.println("Lowercase: " + text.toLowerCase());
        
        System.out.println();
    }
    
    public static void demonstrateStringComparison() {
        System.out.println("=== STRING COMPARISON ===");
        
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = "hello";
        String str4 = new String("Hello");
        
        // Reference comparison
        System.out.println("str1 == str2: " + (str1 == str2));  // true (same object)
        System.out.println("str1 == str4: " + (str1 == str4));  // false (different objects)
        
        // Content comparison
        System.out.println("str1.equals(str2): " + str1.equals(str2));  // true
        System.out.println("str1.equals(str3): " + str1.equals(str3));  // false
        System.out.println("str1.equals(str4): " + str1.equals(str4));  // true
        
        // Case-insensitive comparison
        System.out.println("str1.equalsIgnoreCase(str3): " + str1.equalsIgnoreCase(str3));  // true
        
        // Lexicographic comparison
        System.out.println("\"Apple\".compareTo(\"Banana\"): " + "Apple".compareTo("Banana"));  // negative
        System.out.println("\"Zebra\".compareTo(\"Apple\"): " + "Zebra".compareTo("Apple"));   // positive
        
        System.out.println();
    }
    
    public static void demonstrateStringManipulation() {
        System.out.println("=== STRING MANIPULATION ===");
        
        String text = "  Welcome to Java Programming  ";
        System.out.println("Original: '" + text + "'");
        
        // Trimming
        String trimmed = text.trim();
        System.out.println("Trimmed: '" + trimmed + "'");
        
        // Searching
        System.out.println("Index of 'Java': " + trimmed.indexOf("Java"));
        System.out.println("Last index of 'a': " + trimmed.lastIndexOf('a'));
        System.out.println("Contains 'Program': " + trimmed.contains("Program"));
        
        // Substring
        System.out.println("Substring(11): " + trimmed.substring(11));
        System.out.println("Substring(11, 15): " + trimmed.substring(11, 15));
        
        // Prefix/Suffix
        System.out.println("Starts with 'Welcome': " + trimmed.startsWith("Welcome"));
        System.out.println("Ends with 'ming': " + trimmed.endsWith("ming"));
        
        // Replacement
        String replaced = trimmed.replace("Java", "Python");
        System.out.println("Replaced Java with Python: " + replaced);
        
        // Splitting
        String sentence = "apple,banana,cherry,date";
        String[] fruits = sentence.split(",");
        System.out.println("Split fruits: " + Arrays.toString(fruits));
        
        // Joining
        String rejoined = String.join(" | ", fruits);
        System.out.println("Rejoined: " + rejoined);
        
        System.out.println();
    }
    
    public static void demonstrateStringBuilder() {
        System.out.println("=== STRINGBUILDER OPERATIONS ===");
        
        // Creating StringBuilder
        StringBuilder sb = new StringBuilder("Hello");
        System.out.println("Initial: " + sb.toString());
        
        // Appending
        sb.append(" World");
        sb.append("!");
        sb.append(" Java is ").append(2024);
        System.out.println("After appending: " + sb.toString());
        
        // Inserting
        sb.insert(13, " Amazing");
        System.out.println("After insert: " + sb.toString());
        
        // Deleting
        sb.delete(13, 21);  // Remove " Amazing"
        System.out.println("After delete: " + sb.toString());
        
        // Replacing
        sb.replace(14, 18, "Python");
        System.out.println("After replace: " + sb.toString());
        
        // Reversing
        StringBuilder reversed = new StringBuilder(sb).reverse();
        System.out.println("Reversed: " + reversed.toString());
        
        // StringBuilder vs String concatenation performance
        demonstratePerformance();
        
        System.out.println();
    }
    
    public static void demonstratePerformance() {
        int iterations = 10000;
        
        // String concatenation
        long start = System.nanoTime();
        String result1 = "";
        for (int i = 0; i < iterations; i++) {
            result1 += "x";
        }
        long timeString = System.nanoTime() - start;
        
        // StringBuilder
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("x");
        }
        String result2 = sb.toString();
        long timeStringBuilder = System.nanoTime() - start;
        
        System.out.println("Performance comparison (" + iterations + " iterations):");
        System.out.println("String concatenation: " + (timeString / 1_000_000) + " ms");
        System.out.println("StringBuilder: " + (timeStringBuilder / 1_000_000) + " ms");
        System.out.println("StringBuilder is " + (timeString / timeStringBuilder) + "x faster");
    }
    
    public static void demonstrateRealWorldExamples() {
        System.out.println("=== REAL-WORLD EXAMPLES ===");
        
        // Email validation
        String[] emails = {"user@example.com", "invalid-email", "test@domain.co.uk"};
        for (String email : emails) {
            System.out.println(email + " is valid: " + isValidEmail(email));
        }
        
        // Name formatting
        String[] names = {"john doe", "ALICE SMITH", "bob JOHNSON"};
        for (String name : names) {
            System.out.println("Original: " + name + " → Formatted: " + formatName(name));
        }
        
        // Password strength
        String[] passwords = {"123456", "Password123", "Str0ng!Pass"};
        for (String password : passwords) {
            System.out.println("Password: " + password + " → Strength: " + getPasswordStrength(password));
        }
        
        // Text statistics
        String article = "Java is a popular programming language. " +
                        "It is object-oriented and platform-independent. " +
                        "Java is used for web development, mobile apps, and enterprise software.";
        analyzeText(article);
        
        System.out.println();
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        
        email = email.trim().toLowerCase();
        
        // Basic validation
        return email.contains("@") && 
               email.contains(".") && 
               email.indexOf("@") > 0 &&
               email.indexOf("@") < email.lastIndexOf(".") &&
               !email.startsWith("@") && 
               !email.endsWith("@") &&
               !email.startsWith(".") && 
               !email.endsWith(".");
    }
    
    public static String formatName(String name) {
        if (name == null || name.trim().isEmpty()) return name;
        
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder formatted = new StringBuilder();
        
        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1))
                         .append(" ");
            }
        }
        
        return formatted.toString().trim();
    }
    
    public static String getPasswordStrength(String password) {
        if (password == null) return "Invalid";
        
        int score = 0;
        
        if (password.length() >= 8) score++;
        if (password.matches(".*[a-z].*")) score++;  // lowercase
        if (password.matches(".*[A-Z].*")) score++;  // uppercase  
        if (password.matches(".*[0-9].*")) score++;  // digit
        if (password.matches(".*[!@#$%^&*()].*")) score++;  // special char
        
        switch (score) {
            case 0: case 1: return "Very Weak";
            case 2: return "Weak";
            case 3: return "Medium";
            case 4: return "Strong";
            case 5: return "Very Strong";
            default: return "Unknown";
        }
    }
    
    public static void analyzeText(String text) {
        System.out.println("\n--- TEXT ANALYSIS ---");
        System.out.println("Text: " + text);
        
        // Basic statistics
        int charCount = text.length();
        int wordCount = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
        int sentenceCount = text.split("[.!?]+").length;
        
        // Character frequency
        Map<Character, Integer> charFreq = new HashMap<>();
        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
            }
        }
        
        System.out.println("Characters: " + charCount);
        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentenceCount);
        System.out.println("Average words per sentence: " + (wordCount / (double) sentenceCount));
        
        // Most frequent character
        char mostFreqChar = charFreq.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(' ');
        System.out.println("Most frequent letter: " + mostFreqChar + 
                          " (" + charFreq.get(mostFreqChar) + " times)");
    }
}
```

## Key Takeaways

1. **Strings are immutable** - any modification creates a new string
2. **Use equals()** for content comparison, not ==
3. **String pool optimizes memory** for string literals
4. **StringBuilder is efficient** for multiple concatenations
5. **Rich API provides many utilities** for string manipulation
6. **Always check for null** before string operations
7. **Use appropriate methods** for case-sensitive vs case-insensitive operations
8. **Regular expressions** (regex) provide powerful pattern matching
9. **Text blocks (Java 15+)** make multi-line strings easier
10. **Consider performance** when doing many string operations

---

*Remember: Strings are the building blocks of text processing - master them to handle any text-based challenge in Java!*
