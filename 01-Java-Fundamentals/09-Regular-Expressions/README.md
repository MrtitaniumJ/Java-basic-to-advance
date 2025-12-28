# Regular Expressions - Pattern Matching and Text Processing

## What are Regular Expressions?

Regular Expressions (Regex) are **powerful patterns** used to find, match, and manipulate text. Think of them as:
- **Advanced search patterns** that can find complex text patterns
- **Smart filters** for text data  
- **Text validation tools** for checking formats
- **Pattern templates** for data extraction

```java
// Instead of checking email manually:
if (email.contains("@") && email.contains(".") && ...)

// Use regex pattern:
if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
```

## Why Use Regular Expressions?

Regular expressions are essential for:
- **Data Validation**: Email, phone numbers, passwords
- **Text Processing**: Find and replace operations
- **Data Extraction**: Parse structured text data
- **Input Sanitization**: Clean user input
- **Log File Analysis**: Extract information from log files

---

## Java Regex Classes

Java provides several classes for working with regular expressions:

### 1. Pattern Class
Creates compiled regex patterns for better performance:

```java
import java.util.regex.Pattern;

public class PatternBasics {
    public static void main(String[] args) {
        // Create pattern
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        
        // Test emails
        String[] emails = {
            "john.doe@gmail.com",     // Valid
            "invalid-email",          // Invalid
            "alice@company.co.uk",    // Valid
            "@domain.com",            // Invalid
            "test@domain"             // Invalid
        };
        
        System.out.println("Email Validation Results:");
        for (String email : emails) {
            boolean isValid = emailPattern.matcher(email).matches();
            System.out.printf("%-25s -> %s\n", email, isValid ? "VALID" : "INVALID");
        }
    }
}
```

### 2. Matcher Class
Performs matching operations on text:

```java
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherBasics {
    public static void main(String[] args) {
        String text = "Call me at 555-123-4567 or 555-987-6543 for more information.";
        Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = phonePattern.matcher(text);
        
        System.out.println("Original text: " + text);
        System.out.println("\nFound phone numbers:");
        
        // Find all matches
        while (matcher.find()) {
            String phoneNumber = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            
            System.out.printf("Phone: %s (positions %d-%d)\n", phoneNumber, start, end);
        }
        
        // Replace all phone numbers
        String censored = phonePattern.matcher(text).replaceAll("XXX-XXX-XXXX");
        System.out.println("\nCensored text: " + censored);
    }
}
```

---

## Basic Regex Syntax

### 1. Literal Characters
```java
public class LiteralMatching {
    public static void main(String[] args) {
        String text = "Java is awesome";
        
        // Exact match
        System.out.println("Contains 'Java': " + text.matches(".*Java.*"));
        System.out.println("Starts with 'Java': " + text.matches("Java.*"));
        System.out.println("Ends with 'awesome': " + text.matches(".*awesome"));
        System.out.println("Exact match 'Java is awesome': " + text.matches("Java is awesome"));
    }
}
```

### 2. Character Classes
```java
public class CharacterClasses {
    public static void main(String[] args) {
        String[] testStrings = {"a", "Z", "5", "@", " "};
        
        System.out.println("Character Class Testing:");
        System.out.println("Char\t[a-z]\t[A-Z]\t\\d\t\\w\t\\s");
        System.out.println("----------------------------------------");
        
        for (String s : testStrings) {
            boolean lowercase = s.matches("[a-z]");
            boolean uppercase = s.matches("[A-Z]");
            boolean digit = s.matches("\\d");
            boolean word = s.matches("\\w");
            boolean space = s.matches("\\s");
            
            System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n", 
                            s, lowercase, uppercase, digit, word, space);
        }
        
        // Practical examples
        System.out.println("\nPractical Examples:");
        
        // Validate single digit
        String input1 = "7";
        System.out.println("'" + input1 + "' is single digit: " + input1.matches("\\d"));
        
        // Validate letter (any case)
        String input2 = "A";
        System.out.println("'" + input2 + "' is letter: " + input2.matches("[a-zA-Z]"));
        
        // Validate alphanumeric
        String input3 = "a1";
        System.out.println("'" + input3 + "' is 2-char alphanumeric: " + input3.matches("\\w{2}"));
    }
}
```

### 3. Quantifiers
```java
public class Quantifiers {
    public static void main(String[] args) {
        // Test different quantifiers
        String[] passwords = {"abc", "abc123", "abcd1234", "a", "ab12345678"};
        
        System.out.println("Password Length Validation:");
        System.out.println("Password\t\t3+\t4-8\t6+\tExact 6");
        System.out.println("------------------------------------------------");
        
        for (String pwd : passwords) {
            boolean atLeast3 = pwd.matches(".{3,}");          // 3 or more
            boolean between4and8 = pwd.matches(".{4,8}");     // 4 to 8
            boolean atLeast6 = pwd.matches(".{6,}");          // 6 or more
            boolean exactly6 = pwd.matches(".{6}");           // Exactly 6
            
            System.out.printf("%-15s\t%s\t%s\t%s\t%s\n", 
                            pwd, atLeast3, between4and8, atLeast6, exactly6);
        }
        
        // Practical quantifier examples
        System.out.println("\nQuantifier Examples:");
        
        // Optional area code: (123) or 123 or nothing
        String phonePattern = "(\\(\\d{3}\\)|\\d{3})?\\s?\\d{3}-\\d{4}";
        String[] phones = {"555-1234", "123 555-1234", "(123) 555-1234"};
        
        for (String phone : phones) {
            boolean valid = phone.matches(phonePattern);
            System.out.println(phone + " -> " + (valid ? "VALID" : "INVALID"));
        }
    }
}
```

---

## Common Regex Patterns

### 1. Email Validation
```java
import java.util.regex.Pattern;

public class EmailValidation {
    // Different levels of email validation
    private static final Pattern SIMPLE_EMAIL = Pattern.compile("\\w+@\\w+\\.\\w+");
    private static final Pattern STANDARD_EMAIL = Pattern.compile(
        "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    );
    private static final Pattern STRICT_EMAIL = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    public static void main(String[] args) {
        String[] emails = {
            "john@gmail.com",
            "user.name@company.co.uk", 
            "test+tag@domain.org",
            "invalid.email",
            "@domain.com",
            "user@",
            "perfectly.valid@sub.domain.com"
        };
        
        System.out.println("Email Validation Comparison:");
        System.out.println("Email\t\t\t\tSimple\tStandard\tStrict");
        System.out.println("--------------------------------------------------------");
        
        for (String email : emails) {
            boolean simple = SIMPLE_EMAIL.matcher(email).matches();
            boolean standard = STANDARD_EMAIL.matcher(email).matches();
            boolean strict = STRICT_EMAIL.matcher(email).matches();
            
            System.out.printf("%-30s\t%s\t%s\t\t%s\n", 
                            email, simple, standard, strict);
        }
    }
}
```

### 2. Phone Number Validation
```java
import java.util.regex.Pattern;

public class PhoneValidation {
    public static void main(String[] args) {
        // Different phone number patterns
        Pattern usPattern = Pattern.compile("\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}");
        Pattern internationalPattern = Pattern.compile("\\+?\\d{1,3}[-.\\s]?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}");
        
        String[] phoneNumbers = {
            "555-123-4567",      // US format with dashes
            "(555) 123-4567",    // US format with parentheses  
            "555.123.4567",      // US format with dots
            "5551234567",        // US format no separators
            "+1-555-123-4567",   // US international format
            "+44 20 7123 4567",  // UK format
            "+81 3-1234-5678",   // Japan format
            "123-45-6789",       // Invalid (looks like SSN)
            "555-12-34567"       // Invalid format
        };
        
        System.out.println("Phone Number Validation:");
        System.out.println("Number\t\t\t\tUS Format\tInternational");
        System.out.println("--------------------------------------------------");
        
        for (String phone : phoneNumbers) {
            boolean usValid = usPattern.matcher(phone).matches();
            boolean intlValid = internationalPattern.matcher(phone).matches();
            
            System.out.printf("%-25s\t%s\t\t%s\n", phone, usValid, intlValid);
        }
    }
}
```

### 3. Password Strength Validation
```java
import java.util.regex.Pattern;

public class PasswordValidation {
    public static void main(String[] args) {
        String[] passwords = {
            "weak",
            "StrongPass123!",
            "NoSpecialChar123", 
            "nouppercasechar!",
            "NOLOWERCASECHAR123!",
            "NoNumbers!",
            "Short1!",
            "Perfect123!@#"
        };
        
        System.out.println("Password Strength Analysis:");
        System.out.println("Password\t\t\tLength\tUpper\tLower\tDigit\tSpecial\tStrong");
        System.out.println("--------------------------------------------------------------------------");
        
        for (String password : passwords) {
            boolean length = password.length() >= 8;
            boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
            boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
            boolean hasDigit = Pattern.compile("\\d").matcher(password).find();
            boolean hasSpecial = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find();
            boolean isStrong = length && hasUpper && hasLower && hasDigit && hasSpecial;
            
            System.out.printf("%-20s\t%s\t%s\t%s\t%s\t%s\t%s\n", 
                            password, length, hasUpper, hasLower, hasDigit, hasSpecial, isStrong);
        }
    }
}
```

---

## Advanced Regex Operations

### 1. Group Capturing
```java
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GroupCapturing {
    public static void main(String[] args) {
        String logEntry = "2024-01-15 14:30:45 ERROR Database connection failed for user john.doe";
        
        // Pattern with named groups (Java 7+)
        String patternString = "(\\d{4}-\\d{2}-\\d{2})\\s+(\\d{2}:\\d{2}:\\d{2})\\s+(\\w+)\\s+(.*)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(logEntry);
        
        if (matcher.matches()) {
            String date = matcher.group(1);
            String time = matcher.group(2);
            String level = matcher.group(3);
            String message = matcher.group(4);
            
            System.out.println("Log Entry Analysis:");
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Level: " + level);
            System.out.println("Message: " + message);
        }
        
        // Extract email addresses from text
        String text = "Contact us at support@company.com or sales@company.com for assistance.";
        Pattern emailPattern = Pattern.compile("([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})");
        Matcher emailMatcher = emailPattern.matcher(text);
        
        System.out.println("\nEmail Extraction:");
        while (emailMatcher.find()) {
            String fullEmail = emailMatcher.group(0);  // Full match
            String username = emailMatcher.group(1);   // First group
            String domain = emailMatcher.group(2);     // Second group
            
            System.out.printf("Email: %s (User: %s, Domain: %s)\n", fullEmail, username, domain);
        }
    }
}
```

### 2. Find and Replace Operations
```java
import java.util.regex.Pattern;

public class FindAndReplace {
    public static void main(String[] args) {
        String sensitiveText = "My SSN is 123-45-6789 and my credit card is 4532-1234-5678-9012.";
        
        // Replace SSN pattern
        String ssnPattern = "\\d{3}-\\d{2}-\\d{4}";
        String withoutSSN = sensitiveText.replaceAll(ssnPattern, "XXX-XX-XXXX");
        
        // Replace credit card pattern  
        String ccPattern = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
        String withoutCC = withoutSSN.replaceAll(ccPattern, "XXXX-XXXX-XXXX-XXXX");
        
        System.out.println("Original: " + sensitiveText);
        System.out.println("Masked:   " + withoutCC);
        
        // Format phone numbers consistently
        String mixedPhones = "Call 555-123-4567 or (555) 987-6543 or 555.111.2222";
        String phonePattern = "\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}";
        Pattern pattern = Pattern.compile(phonePattern);
        
        // Use replaceAll with lambda (Java 9+) or traditional approach
        String formatted = pattern.matcher(mixedPhones).replaceAll(matchResult -> {
            String phone = matchResult.group();
            // Extract just digits
            String digits = phone.replaceAll("[^\\d]", "");
            // Format as XXX-XXX-XXXX
            return String.format("%s-%s-%s", 
                                digits.substring(0, 3),
                                digits.substring(3, 6), 
                                digits.substring(6, 10));
        });
        
        System.out.println("\nPhone formatting:");
        System.out.println("Original:  " + mixedPhones);
        System.out.println("Formatted: " + formatted);
    }
}
```

### 3. Data Extraction
```java
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public class DataExtraction {
    public static void main(String[] args) {
        // Extract structured data from text
        String productList = "Laptop $999.99 (5 available), Mouse $29.99 (50 available), " +
                           "Keyboard $79.99 (15 available), Monitor $299.99 (8 available)";
        
        // Pattern to extract product name, price, and quantity
        String productPattern = "(\\w+)\\s+\\$(\\d+\\.\\d{2})\\s+\\((\\d+)\\s+available\\)";
        Pattern pattern = Pattern.compile(productPattern);
        Matcher matcher = pattern.matcher(productList);
        
        List<Product> products = new ArrayList<>();
        
        System.out.println("Extracted Product Information:");
        System.out.println("Product\t\tPrice\t\tQuantity\tTotal Value");
        System.out.println("---------------------------------------------------");
        
        double grandTotal = 0;
        
        while (matcher.find()) {
            String name = matcher.group(1);
            double price = Double.parseDouble(matcher.group(2));
            int quantity = Integer.parseInt(matcher.group(3));
            double totalValue = price * quantity;
            
            products.add(new Product(name, price, quantity));
            grandTotal += totalValue;
            
            System.out.printf("%-12s\t$%-10.2f\t%-8d\t$%.2f\n", 
                            name, price, quantity, totalValue);
        }
        
        System.out.println("---------------------------------------------------");
        System.out.printf("Grand Total Inventory Value: $%.2f\n", grandTotal);
        
        // Extract URLs from text
        String htmlContent = "Visit our website at https://www.example.com or " +
                           "check our blog at http://blog.example.com/articles";
        
        String urlPattern = "https?://[\\w.-]+(?:/[\\w.-]*)*";
        Pattern urlPatternCompiled = Pattern.compile(urlPattern);
        Matcher urlMatcher = urlPatternCompiled.matcher(htmlContent);
        
        System.out.println("\nExtracted URLs:");
        while (urlMatcher.find()) {
            System.out.println("- " + urlMatcher.group());
        }
    }
    
    static class Product {
        String name;
        double price;
        int quantity;
        
        Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }
}
```

---

## Real-World Applications

### 1. Log File Analyzer
```java
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogAnalyzer {
    public static void main(String[] args) {
        // Simulate web server log entries
        String[] logEntries = {
            "192.168.1.100 - - [15/Jan/2024:10:30:45 +0000] \"GET /index.html HTTP/1.1\" 200 1234",
            "10.0.0.50 - - [15/Jan/2024:10:31:12 +0000] \"POST /login HTTP/1.1\" 401 567", 
            "192.168.1.100 - - [15/Jan/2024:10:32:30 +0000] \"GET /images/logo.png HTTP/1.1\" 404 0",
            "203.0.113.45 - - [15/Jan/2024:10:33:15 +0000] \"GET /api/data HTTP/1.1\" 500 890"
        };
        
        // Apache Common Log Format pattern
        String logPattern = "(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) HTTP/1\\.1\" (\\d{3}) (\\d+)";
        Pattern pattern = Pattern.compile(logPattern);
        
        System.out.println("Web Server Log Analysis:");
        System.out.println("IP Address\t\tTimestamp\t\tMethod\tURL\t\t\tStatus\tSize");
        System.out.println("---------------------------------------------------------------------------------");
        
        int[] statusCounts = new int[6]; // 200s, 300s, 400s, 500s
        long totalBytes = 0;
        
        for (String logEntry : logEntries) {
            Matcher matcher = pattern.matcher(logEntry);
            
            if (matcher.matches()) {
                String ipAddress = matcher.group(1);
                String timestamp = matcher.group(2);
                String method = matcher.group(3);
                String url = matcher.group(4);
                int statusCode = Integer.parseInt(matcher.group(5));
                long responseSize = Long.parseLong(matcher.group(6));
                
                // Count status codes by category
                int statusCategory = statusCode / 100;
                if (statusCategory >= 2 && statusCategory <= 5) {
                    statusCounts[statusCategory - 2]++;
                }
                
                totalBytes += responseSize;
                
                System.out.printf("%-15s\t%-20s\t%s\t%-15s\t%d\t%d\n", 
                                ipAddress, timestamp, method, url, statusCode, responseSize);
            }
        }
        
        System.out.println("\nSummary:");
        System.out.println("2xx Success: " + statusCounts[0]);
        System.out.println("3xx Redirect: " + statusCounts[1]);
        System.out.println("4xx Client Error: " + statusCounts[2]);
        System.out.println("5xx Server Error: " + statusCounts[3]);
        System.out.println("Total bytes served: " + totalBytes);
    }
}
```

### 2. Data Format Validator
```java
import java.util.regex.Pattern;

public class DataFormatValidator {
    // Compile patterns once for better performance
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}(:\\d{2})?");
    private static final Pattern SSN_PATTERN = Pattern.compile("\\d{3}-\\d{2}-\\d{4}");
    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("\\d{5}(-\\d{4})?");
    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );
    
    public static void main(String[] args) {
        // Test data with mixed formats
        String[] testData = {
            "2024-01-15",        // Date
            "14:30:45",          // Time
            "123-45-6789",       // SSN
            "4532-1234-5678-9012", // Credit Card
            "12345-6789",        // ZIP+4
            "192.168.1.1",       // IP Address
            "invalid-date",      // Invalid
            "25:61:99",          // Invalid time
            "999-99-9999"        // Invalid SSN (but matches pattern)
        };
        
        System.out.println("Data Format Validation:");
        System.out.println("Data\t\t\tDate\tTime\tSSN\tCC\tZIP\tIP");
        System.out.println("-------------------------------------------------------------");
        
        for (String data : testData) {
            boolean isDate = DATE_PATTERN.matcher(data).matches();
            boolean isTime = TIME_PATTERN.matcher(data).matches();
            boolean isSSN = SSN_PATTERN.matcher(data).matches();
            boolean isCC = CREDIT_CARD_PATTERN.matcher(data).matches();
            boolean isZIP = ZIP_CODE_PATTERN.matcher(data).matches();
            boolean isIP = IPV4_PATTERN.matcher(data).matches();
            
            System.out.printf("%-20s\t%s\t%s\t%s\t%s\t%s\t%s\n", 
                            data, isDate, isTime, isSSN, isCC, isZIP, isIP);
        }
        
        // Enhanced validation with business logic
        System.out.println("\nEnhanced Validation (with business logic):");
        for (String data : testData) {
            if (DATE_PATTERN.matcher(data).matches()) {
                System.out.println(data + " -> " + (isValidDate(data) ? "Valid Date" : "Invalid Date"));
            } else if (IPV4_PATTERN.matcher(data).matches()) {
                System.out.println(data + " -> " + (isValidIP(data) ? "Valid IP" : "Invalid IP"));
            }
        }
    }
    
    private static boolean isValidDate(String date) {
        // Basic format check already passed, now check logical validity
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        
        return year >= 1900 && year <= 2100 && 
               month >= 1 && month <= 12 && 
               day >= 1 && day <= 31;
    }
    
    private static boolean isValidIP(String ip) {
        // Pattern already ensures format, this checks ranges
        String[] octets = ip.split("\\.");
        for (String octet : octets) {
            int value = Integer.parseInt(octet);
            if (value < 0 || value > 255) return false;
        }
        return true;
    }
}
```

---

## Common Regex Mistakes and Solutions

### 1. Forgetting to Escape Special Characters
```java
// ✗ WRONG - . matches any character
String pattern = "192.168.1.1";
if ("192X168Y1Z1".matches(pattern)) { // This will match!
    System.out.println("Matched");
}

// ✓ CORRECT - Escape the dots
String pattern = "192\\.168\\.1\\.1";
if ("192X168Y1Z1".matches(pattern)) { // This won't match
    System.out.println("Matched");
}
```

### 2. Not Using Raw Strings (Double Escaping)
```java
// ✗ CONFUSING - Java string escaping + regex escaping
String digitPattern = "\\\\d+"; // Need four backslashes!

// ✓ BETTER - Use clear escaping
String digitPattern = "\\d+"; // Two backslashes for one literal backslash in regex
```

### 3. Inefficient Pattern Compilation
```java
// ✗ WRONG - Compiles pattern every time
public boolean isValidEmail(String email) {
    return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
}

// ✓ CORRECT - Compile once, reuse
private static final Pattern EMAIL_PATTERN = Pattern.compile(
    "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
);

public boolean isValidEmail(String email) {
    return EMAIL_PATTERN.matcher(email).matches();
}
```

## Practice Exercises

### Exercise 1: Credit Card Validator
```java
public class CreditCardValidator {
    public static void main(String[] args) {
        String[] creditCards = {
            "4532-1234-5678-9012",  // Visa
            "5555-4444-3333-2222",  // MasterCard
            "3782-822463-10005",    // American Express
            "6011-1111-1111-1117",  // Discover
            "invalid-card-number"
        };
        
        // Create patterns for different card types:
        // Visa: starts with 4, 16 digits
        // MasterCard: starts with 5, 16 digits  
        // AmEx: starts with 34 or 37, 15 digits
        // Discover: starts with 6, 16 digits
        
        // Validate format and identify card type
    }
}
```

### Exercise 2: Configuration File Parser
```java
public class ConfigParser {
    public static void main(String[] args) {
        String[] configLines = {
            "database.host=localhost",
            "database.port=5432", 
            "# This is a comment",
            "app.name=My Application",
            "app.version=1.0.0",
            "invalid line without equals",
            "debug=true"
        };
        
        // Parse configuration file format:
        // key=value pairs
        // Ignore comments (lines starting with #)
        // Handle different value types (string, number, boolean)
        // Extract key-value pairs using regex groups
    }
}
```

### Exercise 3: HTML Tag Extractor
```java
public class HTMLTagExtractor {
    public static void main(String[] args) {
        String htmlContent = 
            "<html><head><title>My Page</title></head>" +
            "<body><h1>Welcome</h1><p>Hello <b>world</b>!</p>" +
            "<a href=\"https://example.com\">Link</a></body></html>";
        
        // Extract:
        // 1. All HTML tags
        // 2. Tag content (text between tags)
        // 3. Attributes from tags
        // 4. Links from <a> tags
        
        // Create patterns to match different HTML elements
    }
}
```

## Quick Reference

### Basic Patterns
```java
.           // Any character
\d          // Digit (0-9)
\w          // Word character (a-z, A-Z, 0-9, _)
\s          // Whitespace
[abc]       // One of a, b, or c
[a-z]       // Any lowercase letter
[^abc]      // Not a, b, or c
```

### Quantifiers
```java
*           // Zero or more
+           // One or more  
?           // Zero or one
{3}         // Exactly 3
{3,}        // 3 or more
{3,5}       // Between 3 and 5
```

### Anchors
```java
^           // Start of string
$           // End of string
\b          // Word boundary
```

### Groups
```java
(abc)       // Capture group
(?:abc)     // Non-capturing group
\1          // Back reference to first group
```

### Common Use Cases
```java
// Email
"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

// Phone (US)
"\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}"

// Date (YYYY-MM-DD)
"\\d{4}-\\d{2}-\\d{2}"

// IP Address
"\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"
```

### Java Methods
```java
String.matches(regex)           // Test if entire string matches
String.replaceAll(regex, replacement)  // Replace all matches
String.split(regex)            // Split by pattern

Pattern.compile(regex)         // Compile pattern
Matcher.find()                 // Find next match
Matcher.group()                // Get matched text
Matcher.replaceAll()           // Replace all matches
```

## Key Takeaways

1. **Regular expressions** are powerful pattern matching tools
2. **Compile patterns once** for better performance  
3. **Escape special characters** properly in patterns
4. **Use groups** to capture and extract data
5. **Test patterns thoroughly** with various inputs
6. **Start simple** and build complexity gradually
7. **Regex is not always the answer** - sometimes simple string methods are better
8. **Document complex patterns** with comments
9. **Use online regex testers** for pattern development
10. **Balance power and readability** in regex patterns