# File Operations

## Problem Statement

File I/O operations are fundamental to most real-world applications. This program demonstrates various file operations including reading, writing, appending, copying, and manipulating files. Understanding different approaches and their trade-offs is essential for handling data persistence, logging, configuration files, and more.

File operations are crucial for:
- Persistent data storage
- Configuration management
- Logging and debugging
- Data processing and ETL
- Application data management

## Concepts

### File Writing

**FileWriter:**
- Character-based file writing
- Direct writing of strings and characters
- Simpler for small files
- Creates new file or overwrites existing

**BufferedWriter:**
- Wraps FileWriter with buffering
- More efficient for large amounts of data
- Reduces I/O operations
- Recommended for performance-critical code

**FileOutputStream:**
- Byte-based writing
- Better for binary data
- Lower-level than FileWriter

### File Reading

**FileReader:**
- Character-based file reading
- Reads one character at a time
- Simple but less efficient
- Good for educational purposes

**BufferedReader:**
- Wraps FileReader with buffering
- Reads lines efficiently
- `readLine()` method for line-by-line reading
- Recommended for text files

**Scanner:**
- Convenient parsing of text files
- Built-in methods for parsing numbers, etc.
- Higher-level abstraction
- Good when you need specific parsing

**NIO (Files class):**
- Modern approach (Java 7+)
- `Files.readAllBytes()` for entire file
- `Files.readAllLines()` for line list
- Most efficient for most use cases

### File Appending

Appending adds content to existing file without overwriting:
```java
new FileWriter(filename, true)  // true = append mode
```

**Methods:**
- Open in append mode with `FileWriter(name, true)`
- Use BufferedWriter for efficiency
- Ideal for log files and incremental data

### File Manipulation

**Common Operations:**
- Check existence: `file.exists()`
- Get size: `file.length()`
- Delete: `file.delete()`
- Rename: `file.renameTo()`
- Get info: name, path, permissions, modification time

### File Copying

**NIO Approach (Recommended):**
- `Files.copy()` with StandardCopyOption
- Efficient for large files
- Supports various copy options

**Stream Approach:**
- Read from source, write to destination
- Good for understanding underlying mechanics
- More control over process

### Working with Directories

**Operations:**
- Create: `file.mkdir()` or `file.mkdirs()`
- List: `file.listFiles()`
- Check type: `file.isDirectory()`
- Get path: `file.getAbsolutePath()`

## Complexity Analysis

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Write (char) | O(n) | O(1) | Per character written |
| Write (buffered) | O(n) | O(b) | b = buffer size |
| Read (char) | O(n) | O(1) | Per character read |
| Read (buffered) | O(n) | O(b) | b = buffer size |
| Copy | O(n) | O(b) | b = buffer size |
| Append | O(n) | O(1) | Seek to end, write |
| Directory list | O(k) | O(k) | k = number of files |

## Sample Input/Output

```
===== FILE OPERATIONS DEMO =====

=== WRITING OPERATIONS ===

--- WRITING TO FILE (BASIC) ---
Successfully wrote to file: test_file.txt
Content: Hello, World!
Java File I/O Operations

--- WRITING MULTIPLE LINES ---
Successfully wrote 3 lines to file

--- WRITING WITH BUFFERED WRITER ---
Successfully wrote using BufferedWriter

=== READING OPERATIONS ===

--- READING FILE (BASIC) ---
Successfully read from file: test_file.txt
File content:
Buffered Writer Content
More efficient for large files

--- READING LINES WITH BUFFERED READER ---
Successfully read 2 lines from file
Read lines:
  Buffered Writer Content
  More efficient for large files

--- READING FILE WITH SCANNER ---
Successfully read 2 lines using Scanner

--- READING FILE USING NIO ---
Successfully read file using NIO

=== APPENDING OPERATIONS ===

--- APPENDING TO FILE ---
Successfully appended to file: test_file.txt

--- APPENDING MULTIPLE LINES ---
Successfully appended 2 lines

=== FILE INFORMATION ===

--- FILE INFORMATION ---
File name: test_file.txt
File path: D:\Projects\test_file.txt
File size: 156 bytes
Is file: true
Is directory: false
Can read: true
Can write: true
Last modified: 2024-01-06 15:30:45.123

File exists: true
File size: 156 bytes

=== COPY OPERATIONS ===

--- COPYING FILE (NIO) ---
Successfully copied file

=== DIRECTORY OPERATIONS ===

--- CREATING DIRECTORY ---
Successfully created directory: test_directory

--- LISTING FILES IN DIRECTORY ---
Files in directory "test_directory":
  [FILE] file1.txt
  [FILE] file2.txt
```

## Key Methods Explained

### `writeToFileBasic()`
- Simple FileWriter for basic writing
- Automatic resource management with try-with-resources
- Suitable for small to medium files

### `readFileLinesBuffered()`
- BufferedReader for efficient line-by-line reading
- `readLine()` returns null at EOF
- Ideal for text file processing

### `readFileWithScanner()`
- Scanner provides convenient parsing
- `hasNextLine()` for condition checking
- Good when you need specific parsing logic

### `readFileNIO()`
- Modern, concise approach
- Reads entire file at once
- Recommended for most use cases in Java 7+

### `appendToFile()`
- Opens file in append mode (second parameter = true)
- Preserves existing content
- Essential for logs and incremental data

### `copyFileNIO()`
- Most efficient for large files
- StandardCopyOption provides flexibility
- Handles platform-specific considerations

### `listFilesInDirectory()`
- Lists all files in directory
- Checks if directory exists first
- Useful for batch file processing

## File I/O Approaches Comparison

| Approach | Best For | Pros | Cons |
|----------|----------|------|------|
| FileWriter | Small text | Simple, direct | Inefficient for large files |
| BufferedWriter | Medium/large text | Efficient, buffered | Slightly more complex |
| Scanner | Parsing specific data | Convenient parsing | Slower than BufferedReader |
| NIO Files | Modern, any size | Concise, efficient | Requires Java 7+ |
| FileInputStream | Binary data | Byte-level control | Not for text |
| RandomAccessFile | Selective access | Random positioning | Complex, not for all cases |

## Variations and Challenges

### Variation 1: Case-Insensitive File Search
Search for files with specific names (case-insensitive)
```java
public static List<File> findFiles(String directory, String pattern)
```

### Variation 2: File Statistics
Calculate statistics about files (count, total size, etc.)
```java
public static void getDirectoryStatistics(String directory)
```

### Variation 3: Text Processing
Count words, characters, lines in file
```java
public static void countFileStatistics(String filename)
```

### Challenge 1: Recursive Directory Copy
Copy entire directory structure recursively
```java
public static void copyDirectory(String source, String destination)
```

### Challenge 2: File Merging
Merge multiple files into one
```java
public static void mergeFiles(List<String> sourceFiles, String destFile)
```

### Challenge 3: Backup System
Create versioned backups of files
```java
public static void createBackup(String originalFile)
```

### Challenge 4: CSV Processing
Read and write CSV files with proper parsing
```java
public static List<Map<String, String>> readCSV(String filename)
```

## Interview Questions

1. **What's the difference between FileWriter and BufferedWriter?**
   - FileWriter: Direct writing, unbuffered
   - BufferedWriter: Buffered, more efficient for large writes

2. **When should you use NIO over traditional I/O?**
   - For new code (Java 7+), NIO is cleaner and more efficient
   - For large files or performance-critical operations

3. **Why use try-with-resources for file operations?**
   - Automatically closes resources even if exception occurs
   - Prevents file handle leaks
   - Cleaner code than try-finally

4. **How would you handle very large files efficiently?**
   - Use buffering (BufferedReader/Writer)
   - Read in chunks rather than entire file
   - Use NIO for best performance

5. **What's the difference between append and overwrite?**
   - Overwrite: `new FileWriter(name)` - replaces content
   - Append: `new FileWriter(name, true)` - adds to end

## Edge Cases to Consider

- **File doesn't exist:** Handle gracefully when reading
- **No write permissions:** Check canWrite() before writing
- **Disk full:** IOException when writing
- **File locked:** Another process has file open
- **Very large files:** Memory issues with readAll()
- **Special characters:** Handle encoding properly
- **Path separators:** Use File.separator for cross-platform
- **Empty files:** Handling EOF conditions
- **Directory as file:** Check isFile() vs isDirectory()

## Resource Management

**Always use try-with-resources:**
```java
try (FileReader reader = new FileReader(filename)) {
    // use reader
} catch (IOException e) {
    // handle error
}
```

This ensures files are closed even if exception occurs.

## Running the Program

```powershell
# Compile
javac FileOperationsDemo.java

# Run
java FileOperationsDemo
```

Note: This program creates temporary test files. You can delete them manually or uncomment the cleanup section.

## Tips for Learning

1. **Understand Layers:** FileWriter → BufferedWriter → underlying streams
2. **Choose Appropriate Tool:** Not all approaches are equal for all cases
3. **Always Close Resources:** Use try-with-resources or finally
4. **Test Edge Cases:** Empty files, missing files, permission issues
5. **Performance:** Profile before optimizing; buffering helps most

## Common Mistakes

- Not closing files (resource leak)
- Using FileWriter instead of BufferedWriter for large data
- Catching Exception too broadly
- Not checking if file exists before reading
- Using absolute paths instead of relative
- Not handling encoding issues (UTF-8, etc.)
- Assuming success without checking return values
- Not handling character encoding explicitly

## Best Practices

1. **Use try-with-resources:** Automatic resource management
2. **Buffer for efficiency:** BufferedWriter/BufferedReader
3. **Use NIO when possible:** Modern and concise (Java 7+)
4. **Check file properties:** Verify before operations
5. **Handle exceptions properly:** Provide meaningful error messages
6. **Use relative paths:** Better for portability
7. **Specify encoding:** Explicitly handle character encoding

---

**Practice:** File I/O is fundamental to real applications. Master these techniques. Understand buffering, resource management, and different approaches. Write code that handles files robustly and efficiently.
