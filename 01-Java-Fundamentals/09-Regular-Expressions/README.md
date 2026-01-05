# Regular Expressions in Java - Pattern Matching Power

## Simple Explanation

Think of **Regular Expressions (Regex)** as **search patterns** or **blueprints** for finding text:
- Like a **template** that describes what you're looking for
- **Wildcards** that can match different characters
- **Rules** that define how text should be structured
- A **powerful search tool** that can find, validate, and replace text patterns

### Real-World Analogies
- **Address format** = Regex pattern (123 Main St, Apt 4B)
- **Phone number format** = Regex pattern (123-456-7890)
- **Email structure** = Regex pattern (name@domain.com)
- **License plate pattern** = Regex pattern (ABC-1234)

## Professional Definition

**Regular Expressions** are sequences of characters that form search patterns. In Java, they are used for pattern matching, text validation, searching, and string manipulation through the `java.util.regex` package.

## Why Use Regular Expressions?

### Benefits:
- **Pattern Matching**: Find specific patterns in text
- **Data Validation**: Verify input format (emails, phones, etc.)
- **Text Processing**: Extract, replace, or split text
- **Search and Replace**: Powerful find-and-replace operations
- **Parsing**: Extract structured data from unstructured text

### Common Use Cases:
- Email validation
- Phone number formatting
- Password strength checking
- Log file parsing
- Data cleaning
- Input sanitization

## Basic Regex Syntax

### 1. Literal Characters
```java
// Literal matching - exact characters
String text = "Hello World";
boolean matches = text.matches("Hello World");  // true

// Case sensitive by default
boolean matches2 = text.matches("hello world");  // false
```

### 2. Special Characters (Metacharacters)
```
.   - Matches any single character (except newline)
^   - Beginning of line
$   - End of line
*   - Zero or more occurrences
+   - One or more occurrences
?   - Zero or one occurrence
[]  - Character class (set of characters)
()  - Grouping
|   - OR operator
\   - Escape character
```

### 3. Character Classes
```java
// [abc] - Matches a, b, or c
String pattern1 = "[abc]";
"a".matches(pattern1);  // true
"b".matches(pattern1);  // true
"d".matches(pattern1);  // false

// [a-z] - Range of lowercase letters
String pattern2 = "[a-z]";
"m".matches(pattern2);  // true
"M".matches(pattern2);  // false

// [A-Z] - Range of uppercase letters
String pattern3 = "[A-Z]";
"M".matches(pattern3);  // true
"m".matches(pattern3);  // false

// [0-9] - Range of digits
String pattern4 = "[0-9]";
"5".matches(pattern4);  // true
"a".matches(pattern4);  // false

// [^abc] - NOT a, b, or c (negation)
String pattern5 = "[^abc]";
"d".matches(pattern5);  // true
"a".matches(pattern5);  // false
```

### 4. Predefined Character Classes
```java
// \d - Digit [0-9]
String digitPattern = "\\d";
"5".matches(digitPattern);  // true
"a".matches(digitPattern);  // false

// \D - Non-digit [^0-9]
String nonDigitPattern = "\\D";
"a".matches(nonDigitPattern);  // true
"5".matches(nonDigitPattern);  // false

// \s - Whitespace [ \t\n\r\f]
String spacePattern = "\\s";
" ".matches(spacePattern);   // true
"\t".matches(spacePattern);  // true
"a".matches(spacePattern);   // false

// \S - Non-whitespace [^\s]
String nonSpacePattern = "\\S";
"a".matches(nonSpacePattern);  // true
" ".matches(nonSpacePattern);  // false

// \w - Word character [a-zA-Z_0-9]
String wordPattern = "\\w";
"a".matches(wordPattern);  // true
"5".matches(wordPattern);  // true
"_".matches(wordPattern);  // true
"@".matches(wordPattern);  // false

// \W - Non-word character [^\w]
String nonWordPattern = "\\W";
"@".matches(nonWordPattern);  // true
"a".matches(nonWordPattern);  // false
```

### 5. Quantifiers
```java
// * - Zero or more
"abc*".matches("ab");     // true (zero c's)
"abc*".matches("abc");    // true (one c)
"abc*".matches("abccc");  // true (multiple c's)

// + - One or more
"abc+".matches("ab");     // false (no c's)
"abc+".matches("abc");    // true (one c)
"abc+".matches("abccc");  // true (multiple c's)

// ? - Zero or one
"abc?".matches("ab");     // true (zero c's)
"abc?".matches("abc");    // true (one c)
"abc?".matches("abcc");   // false (multiple c's)

// {n} - Exactly n times
"a{3}".matches("aaa");    // true
"a{3}".matches("aa");     // false
"a{3}".matches("aaaa");   // false

// {n,m} - Between n and m times
"a{2,4}".matches("aa");   // true
"a{2,4}".matches("aaa");  // true
"a{2,4}".matches("aaaa"); // true
"a{2,4}".matches("a");    // false
"a{2,4}".matches("aaaaa"); // false

// {n,} - n or more times
"a{2,}".matches("aa");    // true
"a{2,}".matches("aaaaaa"); // true
"a{2,}".matches("a");     // false
```

## Java Regex Classes

### 1. Pattern Class
```java
import java.util.regex.Pattern;

// Compile pattern
Pattern pattern = Pattern.compile("\\d+");  // One or more digits

// Check if string matches pattern
boolean matches = pattern.matcher("123").matches();  // true

// Split string using pattern
String[] parts = pattern.split("abc123def456ghi");
// Result: ["abc", "def", "ghi"]

// Pattern flags
Pattern caseInsensitive = Pattern.compile("hello", Pattern.CASE_INSENSITIVE);
boolean matches2 = caseInsensitive.matcher("HELLO").matches();  // true
```

### 2. Matcher Class
```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

String text = "The phone numbers are 123-456-7890 and 987-654-3210";
Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
Matcher matcher = phonePattern.matcher(text);

// Find all matches
while (matcher.find()) {
    System.out.println("Found: " + matcher.group());
    System.out.println("Start: " + matcher.start());
    System.out.println("End: " + matcher.end());
}
// Output:
// Found: 123-456-7890
// Start: 21
// End: 33
// Found: 987-654-3210
// Start: 38
// End: 50
```

### 3. String Methods with Regex
```java
String text = "Hello123World456";

// matches() - entire string must match pattern
boolean allDigits = text.matches("\\d+");        // false
boolean hasLettersAndDigits = text.matches("\\w+");  // true

// replaceAll() - replace all matches
String noNumbers = text.replaceAll("\\d+", "");  // "HelloWorld"
String spacedNumbers = text.replaceAll("\\d+", " ");  // "Hello World "

// replaceFirst() - replace first match
String firstOnly = text.replaceFirst("\\d+", "");  // "HelloWorld456"

// split() - split on pattern
String[] parts = text.split("\\d+");  // ["Hello", "World", ""]
```

## Common Regex Patterns

### 1. Email Validation
```java
// Basic email pattern
String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

public static boolean isValidEmail(String email) {
    return email != null && email.matches(emailPattern);
}

// Test emails
String[] emails = {
    "user@example.com",     // valid
    "test.email@domain.co.uk",  // valid
    "invalid-email",        // invalid
    "@domain.com",         // invalid
    "user@"                // invalid
};

for (String email : emails) {
    System.out.println(email + " is valid: " + isValidEmail(email));
}
```

### 2. Phone Number Validation
```java
// US phone number patterns
String[] phonePatterns = {
    "^\\d{10}$",                           // 1234567890
    "^\\d{3}-\\d{3}-\\d{4}$",             // 123-456-7890
    "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$",     // (123) 456-7890
    "^\\+1\\s\\d{3}\\s\\d{3}\\s\\d{4}$"   // +1 123 456 7890
};

public static boolean isValidPhoneNumber(String phone, String pattern) {
    return phone != null && phone.matches(pattern);
}

// Test phone numbers
String[] phones = {"1234567890", "123-456-7890", "(123) 456-7890", "+1 123 456 7890"};
for (int i = 0; i < phones.length; i++) {
    System.out.println(phones[i] + " matches pattern " + i + ": " + 
                      isValidPhoneNumber(phones[i], phonePatterns[i]));
}
```

### 3. Password Strength
```java
// Password requirements:
// - At least 8 characters
// - At least one lowercase letter
// - At least one uppercase letter  
// - At least one digit
// - At least one special character

public static boolean isStrongPassword(String password) {
    if (password == null || password.length() < 8) return false;
    
    // Check each requirement using regex
    boolean hasLower = password.matches(".*[a-z].*");
    boolean hasUpper = password.matches(".*[A-Z].*");
    boolean hasDigit = password.matches(".*\\d.*");
    boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    
    return hasLower && hasUpper && hasDigit && hasSpecial;
}

// Alternative: single regex (more complex)
public static boolean isStrongPasswordSingleRegex(String password) {
    String strongPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
    return password != null && password.matches(strongPattern);
}
```

### 4. Date Format Validation
```java
// Date patterns
public static boolean isValidDate(String date, String format) {
    String pattern;
    switch (format.toLowerCase()) {
        case "mm/dd/yyyy":
            pattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/(19|20)\\d{2}$";
            break;
        case "dd-mm-yyyy":
            pattern = "^(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-(19|20)\\d{2}$";
            break;
        case "yyyy.mm.dd":
            pattern = "^(19|20)\\d{2}\\.(0[1-9]|1[0-2])\\.(0[1-9]|[12]\\d|3[01])$";
            break;
        default:
            return false;
    }
    return date != null && date.matches(pattern);
}

// Test dates
String[] dates = {"12/25/2023", "25-12-2023", "2023.12.25"};
String[] formats = {"mm/dd/yyyy", "dd-mm-yyyy", "yyyy.mm.dd"};

for (int i = 0; i < dates.length; i++) {
    System.out.println(dates[i] + " in " + formats[i] + " format: " + 
                      isValidDate(dates[i], formats[i]));
}
```

### 5. Credit Card Validation
```java
// Credit card patterns (basic format validation only)
public static String getCreditCardType(String cardNumber) {
    // Remove spaces and dashes
    cardNumber = cardNumber.replaceAll("[\\s-]", "");
    
    if (cardNumber.matches("^4\\d{15}$")) {
        return "Visa";
    } else if (cardNumber.matches("^5[1-5]\\d{14}$")) {
        return "MasterCard";
    } else if (cardNumber.matches("^3[47]\\d{13}$")) {
        return "American Express";
    } else if (cardNumber.matches("^6(?:011|5\\d{2})\\d{12}$")) {
        return "Discover";
    } else {
        return "Unknown";
    }
}

// Test credit card numbers (fake numbers for demo)
String[] cards = {
    "4123 4567 8901 2345",  // Visa format
    "5123 4567 8901 2345",  // MasterCard format
    "3712 345678 90123",    // AmEx format
    "6011 1234 5678 9012"   // Discover format
};

for (String card : cards) {
    System.out.println(card + " → " + getCreditCardType(card));
}
```

## Advanced Regex Features

### 1. Groups and Capturing
```java
// Capturing groups using parentheses
String text = "John Doe (age: 25)";
Pattern pattern = Pattern.compile("(\\w+)\\s+(\\w+)\\s+\\(age:\\s+(\\d+)\\)");
Matcher matcher = pattern.matcher(text);

if (matcher.find()) {
    System.out.println("Full match: " + matcher.group(0));  // Entire match
    System.out.println("First name: " + matcher.group(1));  // John
    System.out.println("Last name: " + matcher.group(2));   // Doe
    System.out.println("Age: " + matcher.group(3));         // 25
}

// Named groups (Java 7+)
Pattern namedPattern = Pattern.compile("(?<first>\\w+)\\s+(?<last>\\w+)\\s+\\(age:\\s+(?<age>\\d+)\\)");
Matcher namedMatcher = namedPattern.matcher(text);

if (namedMatcher.find()) {
    System.out.println("First name: " + namedMatcher.group("first"));
    System.out.println("Last name: " + namedMatcher.group("last"));
    System.out.println("Age: " + namedMatcher.group("age"));
}
```

### 2. Lookahead and Lookbehind
```java
// Positive lookahead (?=...)
String passwordText = "Password123!";
// Must contain digit followed by special character
boolean hasDigitBeforeSpecial = passwordText.matches(".*\\d(?=[!@#$%]).*");

// Negative lookahead (?!...)
String filename = "document.txt";
// Must not end with .tmp
boolean notTempFile = filename.matches(".*(?!\\.tmp)\\..+$");

// Positive lookbehind (?<=...)
String priceText = "$19.99";
// Extract price after dollar sign
Pattern pricePattern = Pattern.compile("(?<=\\$)\\d+\\.\\d+");
Matcher priceMatcher = pricePattern.matcher(priceText);
if (priceMatcher.find()) {
    System.out.println("Price: " + priceMatcher.group());  // 19.99
}

// Negative lookbehind (?<!...)
String text2 = "pre-test post-test";
// Find 'test' not preceded by 'pre-'
Pattern pattern2 = Pattern.compile("(?<!pre-)test");
Matcher matcher2 = pattern2.matcher(text2);
while (matcher2.find()) {
    System.out.println("Found: " + matcher2.group());  // post-test
}
```

## Performance Considerations

### 1. Compile Once, Use Many Times
```java
// Inefficient - compiles pattern every time
for (String email : emailList) {
    if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
        // process email
    }
}

// Efficient - compile once
Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
for (String email : emailList) {
    if (emailPattern.matcher(email).matches()) {
        // process email
    }
}
```

### 2. Avoiding Catastrophic Backtracking
```java
// Dangerous pattern - can cause exponential backtracking
// Pattern.compile("(a+)+b");  // Avoid this!

// Better alternatives
Pattern safePattern = Pattern.compile("a+b");           // Simple
Pattern possessivePattern = Pattern.compile("(a++)++b"); // Possessive quantifiers
```

## Interview Questions & Answers

**Q1: What are Regular Expressions and why use them?**
**A:** Regular expressions are patterns used to match character sequences in strings. They're used for:
- Data validation (emails, phone numbers)
- Text processing and parsing
- Search and replace operations
- Input sanitization

**Q2: What's the difference between matches() and find() methods?**
**A:**
- `matches()`: Checks if entire string matches the pattern
- `find()`: Searches for pattern anywhere in the string

**Q3: What does the Pattern.compile() method do?**
**A:** It compiles a regex string into a Pattern object, which is more efficient when using the same pattern multiple times. It also allows setting flags like CASE_INSENSITIVE.

**Q4: How do you escape special characters in regex?**
**A:** Use backslash (\\) to escape special characters. In Java strings, you need double backslash (\\\\) because backslash is also an escape character in strings.

**Q5: What are quantifiers in regex?**
**A:** Quantifiers specify how many times a character or group should occur:
- `*` (zero or more)
- `+` (one or more) 
- `?` (zero or one)
- `{n}` (exactly n times)
- `{n,m}` (between n and m times)

## Complete Example Program

```java
import java.util.regex.*;
import java.util.Scanner;

/**
 * Comprehensive Regular Expressions demonstration
 */
public class RegexExample {
    
    // Pre-compiled patterns for efficiency
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
    
    private static final Pattern STRONG_PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$");
    
    public static void main(String[] args) {
        demonstrateBasicPatterns();
        demonstrateValidation();
        demonstrateTextProcessing();
        demonstrateGroupsAndCapturing();
        runInteractiveValidator();
    }
    
    public static void demonstrateBasicPatterns() {
        System.out.println("=== BASIC REGEX PATTERNS ===");
        
        // Character classes
        String[] words = {"cat", "bat", "rat", "hat", "mat"};
        Pattern pattern = Pattern.compile("[cbr]at");
        
        System.out.println("Words matching [cbr]at pattern:");
        for (String word : words) {
            if (pattern.matcher(word).matches()) {
                System.out.println("✓ " + word);
            } else {
                System.out.println("✗ " + word);
            }
        }
        
        // Quantifiers
        String[] numbers = {"1", "12", "123", "1234", "12345"};
        Pattern digitPattern = Pattern.compile("\\d{2,4}");
        
        System.out.println("\nNumbers matching \\d{2,4} (2-4 digits):");
        for (String number : numbers) {
            if (digitPattern.matcher(number).matches()) {
                System.out.println("✓ " + number);
            } else {
                System.out.println("✗ " + number);
            }
        }
        System.out.println();
    }
    
    public static void demonstrateValidation() {
        System.out.println("=== INPUT VALIDATION ===");
        
        // Email validation
        String[] emails = {
            "user@example.com",
            "test.email+tag@domain.co.uk",
            "invalid-email",
            "@domain.com",
            "user@.com"
        };
        
        System.out.println("Email validation:");
        for (String email : emails) {
            boolean isValid = EMAIL_PATTERN.matcher(email).matches();
            System.out.printf("%-30s %s%n", email, isValid ? "✓ Valid" : "✗ Invalid");
        }
        
        // Phone validation
        String[] phones = {
            "123-456-7890",
            "(123) 456-7890", 
            "1234567890",
            "123.456.7890",
            "12-345-678"
        };
        
        System.out.println("\nPhone validation:");
        for (String phone : phones) {
            boolean isValid = PHONE_PATTERN.matcher(phone).matches();
            System.out.printf("%-20s %s%n", phone, isValid ? "✓ Valid" : "✗ Invalid");
        }
        
        // Password validation
        String[] passwords = {
            "password",
            "Password123",
            "Pass123!",
            "StrongPassword123!",
            "weak"
        };
        
        System.out.println("\nPassword strength validation:");
        for (String password : passwords) {
            boolean isStrong = STRONG_PASSWORD_PATTERN.matcher(password).matches();
            System.out.printf("%-20s %s%n", password, isStrong ? "✓ Strong" : "✗ Weak");
        }
        System.out.println();
    }
    
    public static void demonstrateTextProcessing() {
        System.out.println("=== TEXT PROCESSING ===");
        
        String text = "Contact us at support@company.com or sales@company.com. " +
                     "Phone: 123-456-7890 or 987-654-3210. Visit www.company.com";
        
        System.out.println("Original text:");
        System.out.println(text);
        System.out.println();
        
        // Extract emails
        Pattern emailFinder = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher emailMatcher = emailFinder.matcher(text);
        
        System.out.println("Found emails:");
        while (emailMatcher.find()) {
            System.out.println("- " + emailMatcher.group());
        }
        
        // Extract phone numbers
        Pattern phoneFinder = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher phoneMatcher = phoneFinder.matcher(text);
        
        System.out.println("\nFound phone numbers:");
        while (phoneMatcher.find()) {
            System.out.println("- " + phoneMatcher.group());
        }
        
        // Replace sensitive information
        String censored = text.replaceAll("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", "[EMAIL]");
        censored = censored.replaceAll("\\d{3}-\\d{3}-\\d{4}", "[PHONE]");
        
        System.out.println("\nCensored text:");
        System.out.println(censored);
        System.out.println();
    }
    
    public static void demonstrateGroupsAndCapturing() {
        System.out.println("=== GROUPS AND CAPTURING ===");
        
        String logEntry = "2024-12-28 14:30:25 ERROR UserService: Login failed for user john.doe@email.com";
        
        // Pattern with groups
        Pattern logPattern = Pattern.compile(
            "(\\d{4}-\\d{2}-\\d{2})\\s+" +     // Group 1: Date
            "(\\d{2}:\\d{2}:\\d{2})\\s+" +     // Group 2: Time  
            "(\\w+)\\s+" +                     // Group 3: Level
            "(\\w+):\\s+" +                    // Group 4: Service
            "(.+)"                             // Group 5: Message
        );
        
        Matcher logMatcher = logPattern.matcher(logEntry);
        
        if (logMatcher.find()) {
            System.out.println("Log entry analysis:");
            System.out.println("Date: " + logMatcher.group(1));
            System.out.println("Time: " + logMatcher.group(2));
            System.out.println("Level: " + logMatcher.group(3));
            System.out.println("Service: " + logMatcher.group(4));
            System.out.println("Message: " + logMatcher.group(5));
        }
        
        // Named groups (Java 7+)
        Pattern namedPattern = Pattern.compile(
            "(?<date>\\d{4}-\\d{2}-\\d{2})\\s+" +
            "(?<time>\\d{2}:\\d{2}:\\d{2})\\s+" +
            "(?<level>\\w+)\\s+" +
            "(?<service>\\w+):\\s+" +
            "(?<message>.+)"
        );
        
        Matcher namedMatcher = namedPattern.matcher(logEntry);
        
        if (namedMatcher.find()) {
            System.out.println("\nUsing named groups:");
            System.out.println("Date: " + namedMatcher.group("date"));
            System.out.println("Level: " + namedMatcher.group("level"));
            System.out.println("Service: " + namedMatcher.group("service"));
        }
        System.out.println();
    }
    
    public static void runInteractiveValidator() {
        System.out.println("=== INTERACTIVE VALIDATOR ===");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nChoose validation type:");
            System.out.println("1. Email");
            System.out.println("2. Phone");
            System.out.println("3. Password");
            System.out.println("4. Custom pattern");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 5) break;
            
            switch (choice) {
                case 1:
                    validateUserInput(scanner, "email", EMAIL_PATTERN);
                    break;
                case 2:
                    validateUserInput(scanner, "phone number", PHONE_PATTERN);
                    break;
                case 3:
                    validateUserInput(scanner, "password", STRONG_PASSWORD_PATTERN);
                    break;
                case 4:
                    customPatternValidator(scanner);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        
        System.out.println("Goodbye!");
        scanner.close();
    }
    
    private static void validateUserInput(Scanner scanner, String type, Pattern pattern) {
        System.out.print("Enter " + type + ": ");
        String input = scanner.nextLine();
        
        boolean isValid = pattern.matcher(input).matches();
        System.out.println("Result: " + (isValid ? "✓ Valid" : "✗ Invalid"));
        
        if (!isValid && type.equals("password")) {
            System.out.println("Password requirements:");
            System.out.println("- At least 8 characters");
            System.out.println("- At least one lowercase letter");
            System.out.println("- At least one uppercase letter");
            System.out.println("- At least one digit");
            System.out.println("- At least one special character");
        }
    }
    
    private static void customPatternValidator(Scanner scanner) {
        System.out.print("Enter regex pattern: ");
        String patternStr = scanner.nextLine();
        
        try {
            Pattern customPattern = Pattern.compile(patternStr);
            System.out.print("Enter text to validate: ");
            String text = scanner.nextLine();
            
            boolean matches = customPattern.matcher(text).matches();
            System.out.println("Result: " + (matches ? "✓ Matches" : "✗ No match"));
            
            // Show all matches if using find()
            Matcher finder = customPattern.matcher(text);
            System.out.print("All matches: ");
            boolean found = false;
            while (finder.find()) {
                System.out.print("'" + finder.group() + "' ");
                found = true;
            }
            if (!found) {
                System.out.print("none");
            }
            System.out.println();
            
        } catch (PatternSyntaxException e) {
            System.out.println("Invalid regex pattern: " + e.getMessage());
        }
    }
}
```

## Key Takeaways

1. **Regex patterns define search templates** for text matching
2. **Use Pattern.compile()** for repeated pattern usage (performance)
3. **Escape special characters** with backslash (double in Java strings)
4. **Character classes** `[abc]` match specific sets of characters
5. **Quantifiers** `*`, `+`, `?`, `{n,m}` control repetition
6. **Groups** `()` allow capturing parts of matches
7. **Lookahead/lookbehind** provide advanced pattern matching
8. **Always validate input** using appropriate regex patterns
9. **Be careful with complex patterns** - they can cause performance issues
10. **Test regex patterns thoroughly** with various input cases

---

*Remember: Regular expressions are like a powerful microscope for text - they help you find exactly what you're looking for in any amount of text data!*
