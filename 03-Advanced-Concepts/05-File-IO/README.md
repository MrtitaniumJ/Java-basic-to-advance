# File I/O in Java - Input/Output Operations

## Simple Explanation

Think of **File I/O** as **reading and writing documents**:

- **Input Stream** = Reading from a file (like reading a book)
- **Output Stream** = Writing to a file (like writing in a notebook)
- **Buffer** = Temporary storage (like taking notes before writing final version)
- **File operations** = Managing files (create, delete, copy, move)
- **Serialization** = Converting objects to save them in files

### Real-World Analogies
- **Library system** = File I/O (check out books, return books, manage records)
- **Document processing** = File operations (create, edit, save, backup documents)
- **Mail system** = Stream I/O (send letters, receive letters through postal service)
- **Database backup** = Serialization (save entire data structure for later restoration)

## Professional Definition

**File I/O** in Java provides comprehensive mechanisms for reading from and writing to files, directories, and other data sources through stream-based APIs, NIO (New I/O) packages, and serialization frameworks. It includes byte streams, character streams, buffered I/O, random access files, file system operations, and object serialization for persistent data storage and retrieval.

## Why File I/O is Critical

### Problems Without Proper File I/O:
```java
// WITHOUT PROPER FILE I/O - Data loss and poor performance

import java.io.*;

class ProblematicFileHandling {
    
    // PROBLEM 1: No resource management - memory leaks
    public void badFileReading(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            int data;
            while ((data = fis.read()) != -1) {
                System.out.print((char) data);
            }
            // PROBLEM: File stream never closed - resource leak!
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            // PROBLEM: Stream still not closed even in error case!
        }
    }
    
    // PROBLEM 2: Inefficient character-by-character reading
    public void inefficientReading(String filename) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            
            // PROBLEM: Reading one byte at a time - extremely slow!
            int data;
            while ((data = fis.read()) != -1) {
                System.out.print((char) data); // Each read() = system call!
            }
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }
    
    // PROBLEM 3: No error handling - application crashes
    public void noErrorHandling(String filename) throws IOException {
        FileReader file = new FileReader(filename); // Throws FileNotFoundException!
        BufferedReader reader = new BufferedReader(file);
        
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        
        reader.close();
        // PROBLEMS:
        // 1. Crashes if file doesn't exist
        // 2. No graceful error recovery
        // 3. Poor user experience
    }
    
    // PROBLEM 4: Data corruption without proper encoding
    public void encodingProblems(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            
            String text = "Hello 世界! Café résumé naïve";
            
            // PROBLEM: Using default encoding - data corruption possible!
            fos.write(text.getBytes()); // Uses platform default encoding
            
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // PROBLEM 5: No backup or transaction safety
    public void unsafeDataUpdate(String filename, String newData) {
        try {
            // PROBLEM: Directly overwriting file - data loss if operation fails!
            FileWriter writer = new FileWriter(filename); // Truncates existing file!
            writer.write(newData);
            
            // If exception occurs here, original data is lost forever!
            if (newData.contains("error")) {
                throw new RuntimeException("Simulated error");
            }
            
            writer.close();
        } catch (Exception e) {
            System.err.println("Data update failed - original file corrupted!");
        }
    }
}
```

### With Proper File I/O - Safe, Efficient, and Reliable:
```java
// WITH PROPER FILE I/O - Safe, efficient, and reliable operations

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

class RobustFileHandling {
    
    // SOLUTION 1: Try-with-resources for automatic resource management
    public String safeFileReading(String filename) {
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(filename), StandardCharsets.UTF_8)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            
            System.out.println("✅ File read successfully: " + filename);
            return content.toString();
            
        } catch (NoSuchFileException e) {
            System.err.println("❌ File not found: " + filename);
            return null;
        } catch (AccessDeniedException e) {
            System.err.println("❌ Access denied to file: " + filename);
            return null;
        } catch (IOException e) {
            System.err.println("❌ IO error reading file: " + e.getMessage());
            return null;
        }
        // Automatic resource cleanup - no memory leaks!
    }
    
    // SOLUTION 2: Buffered I/O for efficiency
    public boolean efficientFileCopy(String sourcePath, String destinationPath) {
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(sourcePath));
             BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(destinationPath))) {
            
            byte[] buffer = new byte[8192]; // 8KB buffer for efficient copying
            int bytesRead;
            long totalBytes = 0;
            
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            
            System.out.println("✅ File copied successfully: " + totalBytes + " bytes");
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ File copy failed: " + e.getMessage());
            return false;
        }
    }
    
    // SOLUTION 3: Comprehensive error handling with recovery
    public List<String> robustFileReader(String filename, String fallbackPath) {
        List<String> lines = new ArrayList<>();
        
        try {
            lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            System.out.println("✅ Primary file read: " + filename);
            
        } catch (NoSuchFileException e) {
            System.out.println("⚠️ Primary file not found, trying fallback...");
            
            try {
                lines = Files.readAllLines(Paths.get(fallbackPath), StandardCharsets.UTF_8);
                System.out.println("✅ Fallback file read: " + fallbackPath);
                
            } catch (IOException fallbackError) {
                System.err.println("❌ Both primary and fallback files failed");
                
                // Create default content as last resort
                lines.add("# Default configuration");
                lines.add("# Generated due to missing config files");
                
                try {
                    saveLinesToFile(lines, filename);
                    System.out.println("✅ Created default file: " + filename);
                } catch (IOException createError) {
                    System.err.println("❌ Failed to create default file: " + createError.getMessage());
                }
            }
            
        } catch (IOException e) {
            System.err.println("❌ IO error: " + e.getMessage());
        }
        
        return lines;
    }
    
    // SOLUTION 4: Proper encoding handling
    public boolean safeTextWriting(String filename, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filename), 
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            
            writer.write(content);
            System.out.println("✅ Text written with UTF-8 encoding: " + filename);
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Failed to write text: " + e.getMessage());
            return false;
        }
    }
    
    // SOLUTION 5: Atomic file updates with backup
    public boolean atomicFileUpdate(String filename, String newContent) {
        Path originalPath = Paths.get(filename);
        Path backupPath = Paths.get(filename + ".backup");
        Path tempPath = Paths.get(filename + ".tmp");
        
        try {
            // Step 1: Create backup of original file (if exists)
            if (Files.exists(originalPath)) {
                Files.copy(originalPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("✅ Backup created: " + backupPath);
            }
            
            // Step 2: Write new content to temporary file
            Files.write(tempPath, newContent.getBytes(StandardCharsets.UTF_8),
                       StandardOpenOption.CREATE,
                       StandardOpenOption.WRITE,
                       StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("✅ Content written to temporary file: " + tempPath);
            
            // Step 3: Atomic replace - move temp file to original location
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Atomic update completed: " + originalPath);
            
            // Step 4: Clean up backup on success
            Files.deleteIfExists(backupPath);
            
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Atomic update failed: " + e.getMessage());
            
            // Recovery: restore from backup if available
            try {
                if (Files.exists(backupPath)) {
                    Files.move(backupPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("✅ Restored from backup");
                }
            } catch (IOException restoreError) {
                System.err.println("❌ Failed to restore from backup: " + restoreError.getMessage());
            }
            
            // Clean up temporary file
            try {
                Files.deleteIfExists(tempPath);
            } catch (IOException cleanupError) {
                System.err.println("⚠️ Failed to clean up temp file: " + cleanupError.getMessage());
            }
            
            return false;
        }
    }
    
    // Helper method
    private void saveLinesToFile(List<String> lines, String filename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filename), StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    // File information and validation
    public void displayFileInfo(String filename) {
        Path path = Paths.get(filename);
        
        try {
            if (Files.exists(path)) {
                System.out.println("📄 File Information for: " + filename);
                System.out.println("  Size: " + Files.size(path) + " bytes");
                System.out.println("  Readable: " + Files.isReadable(path));
                System.out.println("  Writable: " + Files.isWritable(path));
                System.out.println("  Executable: " + Files.isExecutable(path));
                System.out.println("  Last Modified: " + Files.getLastModifiedTime(path));
                System.out.println("  Is Directory: " + Files.isDirectory(path));
                System.out.println("  Is Regular File: " + Files.isRegularFile(path));
            } else {
                System.out.println("❌ File does not exist: " + filename);
            }
        } catch (IOException e) {
            System.err.println("❌ Error getting file info: " + e.getMessage());
        }
    }
}
```

## Byte Streams vs Character Streams

### 1. Byte Streams (Binary Data)
```java
import java.io.*;
import java.nio.file.*;

public class ByteStreamDemo {
    
    // Raw byte stream operations
    public void demonstrateByteStreams() {
        System.out.println("=== BYTE STREAMS DEMONSTRATION ===");
        
        // Write binary data
        String filename = "binary_data.bin";
        writeBinaryData(filename);
        
        // Read binary data
        readBinaryData(filename);
        
        // Copy binary file
        String copyFilename = "binary_data_copy.bin";
        copyBinaryFile(filename, copyFilename);
        
        // Process binary data with buffering
        processLargeBinaryFile(filename);
    }
    
    public void writeBinaryData(String filename) {
        System.out.println("\n--- Writing Binary Data ---");
        
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)))) {
            
            // Write different data types as binary
            dos.writeInt(42);                    // 4 bytes
            dos.writeLong(123456789L);          // 8 bytes
            dos.writeDouble(3.14159);           // 8 bytes
            dos.writeFloat(2.718f);             // 4 bytes
            dos.writeBoolean(true);             // 1 byte
            dos.writeUTF("Hello Binary World"); // Variable length
            
            // Write byte array
            byte[] data = {65, 66, 67, 68, 69}; // ASCII: ABCDE
            dos.write(data);
            
            dos.flush();
            System.out.println("✅ Binary data written to: " + filename);
            
        } catch (IOException e) {
            System.err.println("❌ Error writing binary data: " + e.getMessage());
        }
    }
    
    public void readBinaryData(String filename) {
        System.out.println("\n--- Reading Binary Data ---");
        
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(filename)))) {
            
            // Read data in same order as written
            int intValue = dis.readInt();
            long longValue = dis.readLong();
            double doubleValue = dis.readDouble();
            float floatValue = dis.readFloat();
            boolean boolValue = dis.readBoolean();
            String stringValue = dis.readUTF();
            
            // Read byte array
            byte[] data = new byte[5];
            dis.readFully(data);
            
            System.out.println("📖 Binary data read:");
            System.out.println("  Int: " + intValue);
            System.out.println("  Long: " + longValue);
            System.out.println("  Double: " + doubleValue);
            System.out.println("  Float: " + floatValue);
            System.out.println("  Boolean: " + boolValue);
            System.out.println("  String: " + stringValue);
            System.out.println("  Bytes: " + new String(data));
            
        } catch (IOException e) {
            System.err.println("❌ Error reading binary data: " + e.getMessage());
        }
    }
    
    public void copyBinaryFile(String source, String destination) {
        System.out.println("\n--- Copying Binary File ---");
        
        try (InputStream input = new BufferedInputStream(new FileInputStream(source));
             OutputStream output = new BufferedOutputStream(new FileOutputStream(destination))) {
            
            byte[] buffer = new byte[4096]; // 4KB buffer
            int bytesRead;
            long totalBytes = 0;
            
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            
            System.out.println("✅ File copied: " + totalBytes + " bytes");
            
        } catch (IOException e) {
            System.err.println("❌ Error copying file: " + e.getMessage());
        }
    }
    
    public void processLargeBinaryFile(String filename) {
        System.out.println("\n--- Processing Large Binary File ---");
        
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            
            long fileSize = raf.length();
            System.out.println("📊 File size: " + fileSize + " bytes");
            
            // Read from specific position
            raf.seek(4); // Skip first int (4 bytes)
            long longValue = raf.readLong();
            System.out.println("📍 Long value at position 4: " + longValue);
            
            // Jump to end of file
            raf.seek(fileSize - 5);
            byte[] lastBytes = new byte[5];
            raf.readFully(lastBytes);
            System.out.println("📍 Last 5 bytes: " + new String(lastBytes));
            
        } catch (IOException e) {
            System.err.println("❌ Error processing file: " + e.getMessage());
        }
    }
    
    // Image file processing example
    public void processImageFile(String imagePath, String outputPath) {
        System.out.println("\n--- Processing Image File ---");
        
        try (InputStream input = new BufferedInputStream(new FileInputStream(imagePath));
             OutputStream output = new BufferedOutputStream(new FileOutputStream(outputPath))) {
            
            byte[] buffer = new byte[8192]; // 8KB buffer for image processing
            int bytesRead;
            long totalBytes = 0;
            
            // Read file header to identify image type
            byte[] header = new byte[10];
            input.mark(10);
            input.read(header);
            input.reset();
            
            String headerStr = new String(header);
            if (headerStr.startsWith("PNG")) {
                System.out.println("🖼️ Processing PNG image");
            } else if (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8) {
                System.out.println("🖼️ Processing JPEG image");
            } else {
                System.out.println("🖼️ Processing unknown image format");
            }
            
            // Copy image data
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
                
                // Progress indicator for large files
                if (totalBytes % 100000 == 0) {
                    System.out.println("  Processed: " + totalBytes + " bytes");
                }
            }
            
            System.out.println("✅ Image processed: " + totalBytes + " bytes");
            
        } catch (IOException e) {
            System.err.println("❌ Error processing image: " + e.getMessage());
        }
    }
}
```

### 2. Character Streams (Text Data)
```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class CharacterStreamDemo {
    
    public void demonstrateCharacterStreams() {
        System.out.println("=== CHARACTER STREAMS DEMONSTRATION ===");
        
        // Write text data
        String filename = "text_data.txt";
        writeTextData(filename);
        
        // Read text data
        readTextData(filename);
        
        // Process CSV file
        String csvFile = "sample_data.csv";
        createAndProcessCSV(csvFile);
        
        // Handle different encodings
        demonstrateEncodings();
        
        // Text file analysis
        analyzeTextFile(filename);
    }
    
    public void writeTextData(String filename) {
        System.out.println("\n--- Writing Text Data ---");
        
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                    new FileWriter(filename, StandardCharsets.UTF_8)))) {
            
            // Write various text content
            writer.println("=== Sample Text File ===");
            writer.println("Created on: " + new Date());
            writer.println();
            
            // Multi-language content
            writer.println("English: Hello World!");
            writer.println("Spanish: ¡Hola Mundo!");
            writer.println("French: Bonjour le Monde!");
            writer.println("German: Hallo Welt!");
            writer.println("Chinese: 你好世界!");
            writer.println("Japanese: こんにちは世界!");
            writer.println("Russian: Привет мир!");
            writer.println();
            
            // Formatted output
            writer.printf("Formatted number: %,.2f%n", 12345.6789);
            writer.printf("Formatted date: %tF %tT%n", new Date(), new Date());
            writer.printf("Padded text: '%10s'%n", "test");
            
            // Special characters
            writer.println("Special characters: \"quotes\", 'apostrophes', \\backslashes");
            writer.println("Unicode symbols: ©️ ®️ ™️ ♠️ ♥️ ♦️ ♣️");
            
            System.out.println("✅ Text data written to: " + filename);
            
        } catch (IOException e) {
            System.err.println("❌ Error writing text data: " + e.getMessage());
        }
    }
    
    public void readTextData(String filename) {
        System.out.println("\n--- Reading Text Data ---");
        
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(filename), StandardCharsets.UTF_8)) {
            
            String line;
            int lineNumber = 1;
            
            while ((line = reader.readLine()) != null) {
                System.out.printf("%3d: %s%n", lineNumber++, line);
            }
            
        } catch (IOException e) {
            System.err.println("❌ Error reading text data: " + e.getMessage());
        }
    }
    
    public void createAndProcessCSV(String filename) {
        System.out.println("\n--- Creating and Processing CSV ---");
        
        // Create CSV file
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filename), StandardCharsets.UTF_8))) {
            
            writer.println("ID,Name,Age,Salary,Department");
            writer.println("1,\"John Doe\",30,50000.00,Engineering");
            writer.println("2,\"Jane Smith\",25,45000.00,Marketing");
            writer.println("3,\"Mike Johnson\",35,60000.00,Sales");
            writer.println("4,\"Sarah Wilson\",28,52000.00,Engineering");
            writer.println("5,\"David Brown\",42,75000.00,Management");
            
            System.out.println("✅ CSV file created: " + filename);
            
        } catch (IOException e) {
            System.err.println("❌ Error creating CSV: " + e.getMessage());
            return;
        }
        
        // Process CSV file
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(filename), StandardCharsets.UTF_8)) {
            
            String headerLine = reader.readLine();
            System.out.println("\n📊 CSV Analysis:");
            System.out.println("Headers: " + headerLine);
            
            String line;
            double totalSalary = 0;
            int employeeCount = 0;
            Map<String, Integer> departmentCount = new HashMap<>();
            
            while ((line = reader.readLine()) != null) {
                String[] fields = parseCSVLine(line);
                
                if (fields.length >= 5) {
                    String name = fields[1].replace("\"", "");
                    double salary = Double.parseDouble(fields[3]);
                    String department = fields[4];
                    
                    totalSalary += salary;
                    employeeCount++;
                    departmentCount.put(department, departmentCount.getOrDefault(department, 0) + 1);
                    
                    System.out.printf("  Employee: %-12s Salary: $%8.2f Dept: %s%n", 
                                    name, salary, department);
                }
            }
            
            System.out.println("\n📈 Statistics:");
            System.out.printf("  Total employees: %d%n", employeeCount);
            System.out.printf("  Average salary: $%,.2f%n", totalSalary / employeeCount);
            System.out.println("  Department distribution:");
            departmentCount.forEach((dept, count) -> 
                System.out.printf("    %s: %d employees%n", dept, count));
            
        } catch (IOException e) {
            System.err.println("❌ Error processing CSV: " + e.getMessage());
        }
    }
    
    private String[] parseCSVLine(String line) {
        // Simple CSV parser (for demo purposes)
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }
    
    public void demonstrateEncodings() {
        System.out.println("\n--- Encoding Demonstration ---");
        
        String content = "Multi-byte characters: 你好 Café résumé naïve Москва";
        
        // Write with different encodings
        String[] encodings = {"UTF-8", "UTF-16", "ISO-8859-1"};
        
        for (String encoding : encodings) {
            String filename = "encoding_" + encoding.replace("-", "_") + ".txt";
            
            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(filename), encoding)) {
                
                writer.write(content);
                
                // Check file size
                long size = Files.size(Paths.get(filename));
                System.out.printf("📝 %s: %d bytes%n", encoding, size);
                
            } catch (IOException e) {
                System.err.println("❌ Error with encoding " + encoding + ": " + e.getMessage());
            }
        }
        
        // Read and compare
        for (String encoding : encodings) {
            String filename = "encoding_" + encoding.replace("-", "_") + ".txt";
            
            try (Reader reader = new InputStreamReader(
                    new FileInputStream(filename), encoding)) {
                
                StringBuilder sb = new StringBuilder();
                int ch;
                while ((ch = reader.read()) != -1) {
                    sb.append((char) ch);
                }
                
                boolean matches = content.equals(sb.toString());
                System.out.printf("📖 %s read: %s %s%n", 
                                encoding, 
                                matches ? "✅" : "❌",
                                matches ? "correct" : "corrupted");
                
            } catch (IOException e) {
                System.err.println("❌ Error reading " + encoding + ": " + e.getMessage());
            }
        }
    }
    
    public void analyzeTextFile(String filename) {
        System.out.println("\n--- Text File Analysis ---");
        
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(filename), StandardCharsets.UTF_8)) {
            
            int lines = 0;
            int words = 0;
            int characters = 0;
            int nonEmptyLines = 0;
            
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                characters += line.length();
                
                if (!line.trim().isEmpty()) {
                    nonEmptyLines++;
                    words += line.trim().split("\\s+").length;
                }
            }
            
            System.out.println("📊 File Statistics:");
            System.out.printf("  Total lines: %d%n", lines);
            System.out.printf("  Non-empty lines: %d%n", nonEmptyLines);
            System.out.printf("  Total words: %d%n", words);
            System.out.printf("  Total characters: %d%n", characters);
            System.out.printf("  Average words per line: %.2f%n", 
                            nonEmptyLines > 0 ? (double) words / nonEmptyLines : 0);
            
        } catch (IOException e) {
            System.err.println("❌ Error analyzing file: " + e.getMessage());
        }
    }
}
```

## Modern NIO (New I/O) Operations

### 1. Path and Files API
```java
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;

public class NIOPathsDemo {
    
    public void demonstratePathOperations() {
        System.out.println("=== NIO PATHS DEMONSTRATION ===");
        
        // Path creation and manipulation
        pathCreationAndManipulation();
        
        // Directory operations
        directoryOperations();
        
        // File attributes
        fileAttributes();
        
        // File watching
        demonstrateFileWatching();
    }
    
    public void pathCreationAndManipulation() {
        System.out.println("\n--- Path Creation and Manipulation ---");
        
        // Different ways to create paths
        Path path1 = Paths.get("documents", "files", "example.txt");
        Path path2 = Paths.get("/home/user/documents/files/example.txt");
        Path path3 = Paths.get("C:\\Users\\User\\Documents\\files\\example.txt");
        
        System.out.println("📂 Path Examples:");
        System.out.println("  Relative path: " + path1);
        System.out.println("  Absolute Unix: " + path2);
        System.out.println("  Absolute Windows: " + path3);
        
        // Path operations
        Path workingPath = Paths.get("project", "src", "main", "java", "Example.java");
        
        System.out.println("\n🔧 Path Operations:");
        System.out.println("  Original: " + workingPath);
        System.out.println("  Absolute: " + workingPath.toAbsolutePath());
        System.out.println("  Parent: " + workingPath.getParent());
        System.out.println("  Filename: " + workingPath.getFileName());
        System.out.println("  Name count: " + workingPath.getNameCount());
        System.out.println("  Root: " + workingPath.getRoot());
        System.out.println("  File extension: " + getFileExtension(workingPath));
        
        // Path manipulation
        Path resolved = workingPath.resolve("../resources/config.properties");
        Path normalized = resolved.normalize();
        
        System.out.println("\n⚙️ Path Manipulation:");
        System.out.println("  Resolved: " + resolved);
        System.out.println("  Normalized: " + normalized);
        
        // Relativizing paths
        Path base = Paths.get("/home/user/project");
        Path target = Paths.get("/home/user/project/src/main/java/Example.java");
        Path relative = base.relativize(target);
        
        System.out.println("\n🔗 Path Relationships:");
        System.out.println("  Base: " + base);
        System.out.println("  Target: " + target);
        System.out.println("  Relative: " + relative);
    }
    
    private String getFileExtension(Path path) {
        String filename = path.getFileName().toString();
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot) : "";
    }
    
    public void directoryOperations() {
        System.out.println("\n--- Directory Operations ---");
        
        Path testDir = Paths.get("test_directory");
        Path subDir = testDir.resolve("subdirectory");
        Path file1 = testDir.resolve("file1.txt");
        Path file2 = subDir.resolve("file2.txt");
        
        try {
            // Create directory structure
            Files.createDirectories(subDir);
            System.out.println("✅ Created directory structure: " + subDir);
            
            // Create files
            Files.createFile(file1);
            Files.write(file1, "Content of file1".getBytes());
            
            Files.createFile(file2);
            Files.write(file2, "Content of file2".getBytes());
            
            System.out.println("✅ Created test files");
            
            // List directory contents
            System.out.println("\n📁 Directory Contents:");
            try (Stream<Path> paths = Files.walk(testDir)) {
                paths.forEach(path -> {
                    try {
                        String type = Files.isDirectory(path) ? "DIR " : "FILE";
                        long size = Files.isDirectory(path) ? 0 : Files.size(path);
                        System.out.printf("  %s %s (%d bytes)%n", type, path, size);
                    } catch (IOException e) {
                        System.err.println("❌ Error reading: " + path);
                    }
                });
            }
            
            // Find files by pattern
            System.out.println("\n🔍 Finding .txt files:");
            try (Stream<Path> paths = Files.find(testDir, 10,
                    (path, attrs) -> path.toString().endsWith(".txt"))) {
                
                paths.forEach(path -> System.out.println("  Found: " + path));
            }
            
            // Copy directory
            Path backupDir = Paths.get("backup_directory");
            copyDirectory(testDir, backupDir);
            
            // Clean up
            deleteDirectory(testDir);
            deleteDirectory(backupDir);
            
        } catch (IOException e) {
            System.err.println("❌ Directory operation error: " + e.getMessage());
        }
    }
    
    private void copyDirectory(Path source, Path destination) throws IOException {
        try (Stream<Path> paths = Files.walk(source)) {
            paths.forEach(srcPath -> {
                try {
                    Path destPath = destination.resolve(source.relativize(srcPath));
                    
                    if (Files.isDirectory(srcPath)) {
                        Files.createDirectories(destPath);
                    } else {
                        Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    System.err.println("❌ Copy error: " + e.getMessage());
                }
            });
        }
        System.out.println("✅ Directory copied: " + source + " -> " + destination);
    }
    
    private void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            try (Stream<Path> paths = Files.walk(directory)) {
                paths.sorted(Comparator.reverseOrder())
                     .forEach(path -> {
                         try {
                             Files.delete(path);
                         } catch (IOException e) {
                             System.err.println("❌ Delete error: " + e.getMessage());
                         }
                     });
            }
            System.out.println("✅ Directory deleted: " + directory);
        }
    }
    
    public void fileAttributes() {
        System.out.println("\n--- File Attributes ---");
        
        Path tempFile = null;
        try {
            // Create temporary file for testing
            tempFile = Files.createTempFile("demo", ".tmp");
            Files.write(tempFile, "Temporary file content for attribute testing".getBytes());
            
            System.out.println("📄 File: " + tempFile);
            
            // Basic attributes
            BasicFileAttributes attrs = Files.readAttributes(tempFile, BasicFileAttributes.class);
            
            System.out.println("\n📊 Basic Attributes:");
            System.out.println("  Size: " + attrs.size() + " bytes");
            System.out.println("  Creation time: " + 
                             LocalDateTime.ofInstant(attrs.creationTime().toInstant(), ZoneId.systemDefault()));
            System.out.println("  Last modified: " + 
                             LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault()));
            System.out.println("  Last accessed: " + 
                             LocalDateTime.ofInstant(attrs.lastAccessTime().toInstant(), ZoneId.systemDefault()));
            System.out.println("  Is directory: " + attrs.isDirectory());
            System.out.println("  Is regular file: " + attrs.isRegularFile());
            System.out.println("  Is symbolic link: " + attrs.isSymbolicLink());
            
            // File permissions (Unix/Linux systems)
            if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                try {
                    PosixFileAttributes posixAttrs = Files.readAttributes(tempFile, PosixFileAttributes.class);
                    
                    System.out.println("\n🔐 POSIX Attributes:");
                    System.out.println("  Owner: " + posixAttrs.owner().getName());
                    System.out.println("  Group: " + posixAttrs.group().getName());
                    System.out.println("  Permissions: " + PosixFilePermissions.toString(posixAttrs.permissions()));
                } catch (UnsupportedOperationException e) {
                    System.out.println("⚠️ POSIX attributes not supported on this system");
                }
            }
            
            // File system information
            FileStore store = Files.getFileStore(tempFile);
            System.out.println("\n💾 File Store Information:");
            System.out.println("  Name: " + store.name());
            System.out.println("  Type: " + store.type());
            System.out.printf("  Total space: %.2f GB%n", store.getTotalSpace() / (1024.0 * 1024 * 1024));
            System.out.printf("  Usable space: %.2f GB%n", store.getUsableSpace() / (1024.0 * 1024 * 1024));
            System.out.printf("  Unallocated space: %.2f GB%n", store.getUnallocatedSpace() / (1024.0 * 1024 * 1024));
            
            // Modify file attributes
            FileTime newTime = FileTime.fromMillis(System.currentTimeMillis() - 86400000); // 1 day ago
            Files.setLastModifiedTime(tempFile, newTime);
            System.out.println("✅ Modified last modified time");
            
        } catch (IOException e) {
            System.err.println("❌ File attributes error: " + e.getMessage());
        } finally {
            // Clean up
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    System.err.println("❌ Cleanup error: " + e.getMessage());
                }
            }
        }
    }
    
    public void demonstrateFileWatching() {
        System.out.println("\n--- File Watching ---");
        
        Path watchDir = Paths.get("watch_directory");
        
        try {
            // Create watch directory
            Files.createDirectories(watchDir);
            
            // Create file watcher
            WatchService watcher = FileSystems.getDefault().newWatchService();
            
            // Register directory for watching
            WatchKey key = watchDir.register(watcher,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
            
            System.out.println("👀 Watching directory: " + watchDir);
            System.out.println("Creating test files...");
            
            // Create a separate thread to watch for changes
            Thread watchThread = new Thread(() -> {
                try {
                    WatchKey watchKey;
                    while ((watchKey = watcher.take()) != null) {
                        for (WatchEvent<?> event : watchKey.pollEvents()) {
                            WatchEvent.Kind<?> kind = event.kind();
                            Path fileName = (Path) event.context();
                            
                            System.out.printf("📂 %s: %s%n", kind.name(), fileName);
                        }
                        
                        if (!watchKey.reset()) {
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("👁️ File watcher stopped");
                }
            });
            
            watchThread.start();
            
            // Simulate file operations
            Thread.sleep(1000);
            
            Path testFile = watchDir.resolve("test1.txt");
            Files.createFile(testFile);
            Thread.sleep(500);
            
            Files.write(testFile, "Hello World".getBytes());
            Thread.sleep(500);
            
            Path testFile2 = watchDir.resolve("test2.txt");
            Files.createFile(testFile2);
            Thread.sleep(500);
            
            Files.delete(testFile);
            Thread.sleep(500);
            
            Files.delete(testFile2);
            Thread.sleep(500);
            
            // Stop watching
            watcher.close();
            watchThread.interrupt();
            watchThread.join(1000);
            
            // Clean up
            deleteDirectory(watchDir);
            
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ File watching error: " + e.getMessage());
        }
    }
}
```

## Object Serialization

### 1. Basic Serialization
```java
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class SerializationDemo {
    
    // Serializable class example
    static class Employee implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private int id;
        private double salary;
        private String department;
        private transient String password; // Won't be serialized
        private static String companyName = "TechCorp"; // Static fields not serialized
        private LocalDateTime hireDate;
        
        public Employee(String name, int id, double salary, String department, String password) {
            this.name = name;
            this.id = id;
            this.salary = salary;
            this.department = department;
            this.password = password;
            this.hireDate = LocalDateTime.now();
        }
        
        // Custom serialization method
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("🔒 Custom serialization for: " + name);
            
            // Default serialization
            out.defaultWriteObject();
            
            // Custom data (encrypt password before saving)
            String encryptedPassword = password != null ? "ENCRYPTED:" + password : null;
            out.writeObject(encryptedPassword);
            
            // Additional metadata
            out.writeObject("METADATA:" + System.currentTimeMillis());
        }
        
        // Custom deserialization method
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println("🔓 Custom deserialization for employee");
            
            // Default deserialization
            in.defaultReadObject();
            
            // Read custom data
            String encryptedPassword = (String) in.readObject();
            if (encryptedPassword != null && encryptedPassword.startsWith("ENCRYPTED:")) {
                this.password = encryptedPassword.substring("ENCRYPTED:".length());
            }
            
            String metadata = (String) in.readObject();
            System.out.println("  Metadata: " + metadata);
        }
        
        @Override
        public String toString() {
            return String.format("Employee{name='%s', id=%d, salary=%.2f, dept='%s', hired=%s}",
                               name, id, salary, department, hireDate);
        }
        
        // Getters and setters
        public String getName() { return name; }
        public int getId() { return id; }
        public double getSalary() { return salary; }
        public String getDepartment() { return department; }
        public String getPassword() { return password; }
        public LocalDateTime getHireDate() { return hireDate; }
    }
    
    // Complex object with collections
    static class Company implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private List<Employee> employees;
        private Map<String, Integer> departmentHeadcount;
        private Set<String> locations;
        
        public Company(String name) {
            this.name = name;
            this.employees = new ArrayList<>();
            this.departmentHeadcount = new HashMap<>();
            this.locations = new HashSet<>();
        }
        
        public void addEmployee(Employee employee) {
            employees.add(employee);
            departmentHeadcount.merge(employee.getDepartment(), 1, Integer::sum);
        }
        
        public void addLocation(String location) {
            locations.add(location);
        }
        
        @Override
        public String toString() {
            return String.format("Company{name='%s', employees=%d, departments=%s, locations=%s}",
                               name, employees.size(), departmentHeadcount.keySet(), locations);
        }
        
        public List<Employee> getEmployees() { return employees; }
        public Map<String, Integer> getDepartmentHeadcount() { return departmentHeadcount; }
        public Set<String> getLocations() { return locations; }
    }
    
    public void demonstrateSerialization() {
        System.out.println("=== OBJECT SERIALIZATION DEMONSTRATION ===");
        
        // Basic serialization
        basicSerialization();
        
        // Complex object serialization
        complexObjectSerialization();
        
        // Versioning and compatibility
        demonstrateVersioning();
        
        // Performance comparison
        performanceComparison();
    }
    
    public void basicSerialization() {
        System.out.println("\n--- Basic Serialization ---");
        
        // Create objects
        Employee emp1 = new Employee("John Doe", 1001, 75000.0, "Engineering", "secret123");
        Employee emp2 = new Employee("Jane Smith", 1002, 68000.0, "Marketing", "password456");
        
        String filename = "employees.ser";
        
        // Serialize objects
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)))) {
            
            System.out.println("💾 Serializing employees...");
            oos.writeObject(emp1);
            oos.writeObject(emp2);
            oos.writeInt(42); // Primitive values too
            oos.writeUTF("Additional metadata");
            
            System.out.println("✅ Serialization completed");
            
        } catch (IOException e) {
            System.err.println("❌ Serialization error: " + e.getMessage());
            return;
        }
        
        // Deserialize objects
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)))) {
            
            System.out.println("\n📂 Deserializing employees...");
            
            Employee deserializedEmp1 = (Employee) ois.readObject();
            Employee deserializedEmp2 = (Employee) ois.readObject();
            int number = ois.readInt();
            String metadata = ois.readUTF();
            
            System.out.println("✅ Deserialization completed");
            System.out.println("📋 Deserialized data:");
            System.out.println("  " + deserializedEmp1);
            System.out.println("  " + deserializedEmp2);
            System.out.println("  Number: " + number);
            System.out.println("  Metadata: " + metadata);
            
            // Verify transient fields
            System.out.println("\n🔐 Transient field verification:");
            System.out.println("  emp1 password: " + deserializedEmp1.getPassword());
            System.out.println("  emp2 password: " + deserializedEmp2.getPassword());
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Deserialization error: " + e.getMessage());
        }
    }
    
    public void complexObjectSerialization() {
        System.out.println("\n--- Complex Object Serialization ---");
        
        // Create complex object structure
        Company company = new Company("TechCorp Solutions");
        
        company.addEmployee(new Employee("Alice Johnson", 2001, 85000.0, "Engineering", "alice123"));
        company.addEmployee(new Employee("Bob Wilson", 2002, 72000.0, "Engineering", "bob456"));
        company.addEmployee(new Employee("Carol Davis", 2003, 65000.0, "Marketing", "carol789"));
        company.addEmployee(new Employee("David Brown", 2004, 78000.0, "Sales", "david101"));
        company.addEmployee(new Employee("Eva Martinez", 2005, 95000.0, "Management", "eva202"));
        
        company.addLocation("New York");
        company.addLocation("San Francisco");
        company.addLocation("Austin");
        
        String filename = "company.ser";
        
        // Serialize complex object
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)))) {
            
            System.out.println("💾 Serializing company data...");
            oos.writeObject(company);
            
            long fileSize = new File(filename).length();
            System.out.printf("✅ Company serialized (%d bytes)%n", fileSize);
            
        } catch (IOException e) {
            System.err.println("❌ Company serialization error: " + e.getMessage());
            return;
        }
        
        // Deserialize complex object
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)))) {
            
            System.out.println("\n📂 Deserializing company data...");
            
            Company deserializedCompany = (Company) ois.readObject();
            
            System.out.println("✅ Company deserialized");
            System.out.println("🏢 Company information:");
            System.out.println("  " + deserializedCompany);
            
            System.out.println("\n👥 Employees:");
            deserializedCompany.getEmployees().forEach(emp -> 
                System.out.println("  " + emp));
            
            System.out.println("\n📊 Department statistics:");
            deserializedCompany.getDepartmentHeadcount().forEach((dept, count) -> 
                System.out.printf("  %s: %d employees%n", dept, count));
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Company deserialization error: " + e.getMessage());
        }
    }
    
    public void demonstrateVersioning() {
        System.out.println("\n--- Serialization Versioning ---");
        
        // This demonstrates the importance of serialVersionUID
        // In practice, you would modify the class and see compatibility issues
        
        System.out.println("💡 Serialization versioning best practices:");
        System.out.println("  1. Always define serialVersionUID");
        System.out.println("  2. Use custom writeObject/readObject for compatibility");
        System.out.println("  3. Test deserialization with old versions");
        System.out.println("  4. Consider using Externalizable for full control");
        System.out.println("  5. Document serialization format changes");
    }
    
    public void performanceComparison() {
        System.out.println("\n--- Serialization Performance ---");
        
        // Create test data
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            employees.add(new Employee("Employee " + i, i, 50000.0 + i * 100, "Dept" + (i % 5), "pass" + i));
        }
        
        // Test different serialization methods
        testSerializationMethod("Standard Serialization", employees, 
            () -> serializeWithStandard(employees),
            () -> deserializeWithStandard());
        
        // Note: In a real application, you might compare with:
        // - JSON serialization (Jackson, Gson)
        // - Protocol Buffers
        // - Avro
        // - MessagePack
        // etc.
    }
    
    private void testSerializationMethod(String methodName, List<Employee> data, 
                                       Runnable serializer, Runnable deserializer) {
        System.out.println("\n🚀 Testing: " + methodName);
        
        // Serialize
        long startTime = System.currentTimeMillis();
        serializer.run();
        long serializeTime = System.currentTimeMillis() - startTime;
        
        // Deserialize
        startTime = System.currentTimeMillis();
        deserializer.run();
        long deserializeTime = System.currentTimeMillis() - startTime;
        
        // File size
        File file = new File("performance_test.ser");
        long fileSize = file.exists() ? file.length() : 0;
        
        System.out.printf("  Serialize time: %d ms%n", serializeTime);
        System.out.printf("  Deserialize time: %d ms%n", deserializeTime);
        System.out.printf("  File size: %d bytes%n", fileSize);
        System.out.printf("  Total time: %d ms%n", serializeTime + deserializeTime);
        
        // Cleanup
        file.delete();
    }
    
    private void serializeWithStandard(List<Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("performance_test.ser"))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.err.println("❌ Standard serialization error: " + e.getMessage());
        }
    }
    
    private void deserializeWithStandard() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("performance_test.ser"))) {
            @SuppressWarnings("unchecked")
            List<Employee> employees = (List<Employee>) ois.readObject();
            // Process data (just count for performance test)
            int count = employees.size();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Standard deserialization error: " + e.getMessage());
        }
    }
}
```

## Complete File I/O Demo

```java
public class CompleteFileIODemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE FILE I/O DEMONSTRATION ===");
        
        // 1. Byte Streams
        System.out.println("\n1. BYTE STREAMS");
        ByteStreamDemo byteDemo = new ByteStreamDemo();
        byteDemo.demonstrateByteStreams();
        
        waitForUser("\nPress Enter to continue to Character Streams...");
        
        // 2. Character Streams
        System.out.println("\n2. CHARACTER STREAMS");
        CharacterStreamDemo charDemo = new CharacterStreamDemo();
        charDemo.demonstrateCharacterStreams();
        
        waitForUser("\nPress Enter to continue to NIO demonstration...");
        
        // 3. NIO Operations
        System.out.println("\n3. NIO (NEW I/O) OPERATIONS");
        NIOPathsDemo nioDemo = new NIOPathsDemo();
        nioDemo.demonstratePathOperations();
        nioDemo.directoryOperations();
        nioDemo.fileAttributes();
        
        waitForUser("\nPress Enter to continue to Serialization...");
        
        // 4. Serialization
        System.out.println("\n4. OBJECT SERIALIZATION");
        SerializationDemo serDemo = new SerializationDemo();
        serDemo.demonstrateSerialization();
        
        System.out.println("\n=== FILE I/O DEMONSTRATION COMPLETED ===");
        
        // Cleanup any remaining test files
        cleanupTestFiles();
    }
    
    private static void waitForUser(String message) {
        System.out.println(message);
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
    
    private static void cleanupTestFiles() {
        String[] testFiles = {
            "binary_data.bin", "binary_data_copy.bin", "text_data.txt",
            "sample_data.csv", "encoding_UTF_8.txt", "encoding_UTF_16.txt",
            "encoding_ISO_8859_1.txt", "employees.ser", "company.ser"
        };
        
        for (String filename : testFiles) {
            try {
                Files.deleteIfExists(Paths.get(filename));
            } catch (IOException e) {
                // Ignore cleanup errors
            }
        }
    }
}
```

## Interview Questions & Answers

**Q1: What's the difference between byte streams and character streams?**
**A:** Byte streams handle raw binary data (8-bit bytes), while character streams handle text data with proper encoding. Use byte streams for binary files (images, executables), character streams for text files.

**Q2: What is the purpose of BufferedInputStream and BufferedOutputStream?**
**A:** They improve performance by reducing system calls. Instead of reading/writing one byte at a time, they use internal buffers to batch operations.

**Q3: Explain try-with-resources and its benefits.**
**A:** Try-with-resources automatically closes resources that implement AutoCloseable. It prevents resource leaks and ensures cleanup even if exceptions occur.

**Q4: What is serialization? What are its requirements?**
**A:** Serialization converts objects to byte streams for storage/transmission. Requirements: implement Serializable, have serialVersionUID, all fields must be serializable or transient.

**Q5: What's the difference between FileInputStream and FileReader?**
**A:** FileInputStream reads raw bytes, FileReader reads characters with encoding conversion. Use FileInputStream for binary data, FileReader for text data.

**Q6: How do you handle different character encodings?**
**A:** Specify encoding explicitly using InputStreamReader/OutputStreamWriter with charset parameters, or use Files.newBufferedReader/Writer with StandardCharsets.

**Q7: What is NIO? What are its advantages?**
**A:** NIO (New I/O) provides non-blocking I/O, memory-mapped files, and better performance. Advantages: scalability, channel-based I/O, selectors for multiplexing.

**Q8: How do you ensure thread safety in file operations?**
**A:** Use synchronization, separate file handles per thread, or thread-safe classes like ConcurrentHashMap for caching file data.

## Key Takeaways

1. **Choose appropriate streams** - byte streams for binary, character streams for text
2. **Use buffering** for better performance and fewer system calls
3. **Try-with-resources** ensures proper resource management
4. **Handle encodings explicitly** to prevent data corruption
5. **NIO provides modern file operations** with better performance and features
6. **Serialization enables object persistence** but requires careful version management
7. **Error handling is crucial** for robust file operations
8. **Path API offers powerful file system operations** and manipulation
9. **File watching enables reactive applications** that respond to changes
10. **Performance considerations matter** for large files and frequent operations

---

*Remember: File I/O is like a postal service - you need the right envelope (stream type), proper addressing (paths), and reliable delivery (error handling) for successful communication!*