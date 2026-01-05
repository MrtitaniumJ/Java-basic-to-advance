# File Handling in Java

## Introduction

File handling is a fundamental aspect of Java programming, enabling applications to persist data, read configuration files, process logs, and interact with the file system. Java provides multiple approaches to file I/O: traditional blocking I/O through the java.io package, modern non-blocking NIO (New I/O), and convenience utilities in the NIO.2 package. Understanding when and how to use each approach is essential for building robust, efficient applications.

File I/O operations can be categorized into character-based operations (text files) and byte-based operations (binary files). Each approach has specific strengths: traditional I/O is simple and familiar, NIO excels at handling large numbers of concurrent connections, and NIO.2 provides modern convenience methods. The evolution from java.io to NIO to NIO.2 reflects Java's response to changing performance requirements and developer expectations.

## Traditional I/O (java.io)

Traditional I/O uses streams and readers/writers for file operations. While this approach has been used for decades and is well-understood, it uses blocking I/O which can be inefficient for handling many concurrent files or network connections.

### Key Classes

**FileReader/FileWriter**: Character-based I/O, useful for text files
**FileInputStream/FileOutputStream**: Byte-based I/O
**BufferedReader/BufferedWriter**: Buffered variants for better performance
**PrintWriter**: Convenient writing with formatting support

### Advantages
- Simple, straightforward API
- Suitable for sequential file access
- Adequate for most applications with moderate concurrency needs
- Extensive documentation and community knowledge

### Disadvantages
- Blocking operations can waste threads waiting for I/O
- Inefficient with thousands of concurrent connections
- No built-in file watching or advanced features

## NIO (java.nio)

NIO introduces channels, buffers, and selectors for non-blocking, scalable I/O. A channel represents a connection to an I/O resource (file or socket), while buffers provide efficient data containers. Selectors enable monitoring multiple channels with a single thread.

### Key Concepts

**Channels**: Bidirectional communication conduits
**Buffers**: Data containers with position, limit, and capacity
**Selectors**: Monitor multiple channels for ready operations

### Advantages
- Non-blocking I/O enables scalability
- Single thread can handle thousands of connections
- Higher throughput for large data volumes
- More efficient memory usage

### Disadvantages
- Steeper learning curve
- More complex code
- Overhead for simple operations
- Requires careful buffer management

## NIO.2 (java.nio.file)

NIO.2 introduces the Path API and Files utility class, providing modern convenience methods for file operations while leveraging NIO's performance.

### Key Classes

**Path**: Represents file system path (replacement for File)
**Files**: Utility methods for file operations
**WatchService**: Monitor file system changes

### Advantages
- Modern, intuitive API
- Concise syntax for common operations
- Built-in file watching
- Atomic operations
- Better error handling

## Comparison Table

| Feature | java.io | java.nio | java.nio.file |
|---------|---------|----------|---------------|
| Blocking | Yes | No | No |
| Scalability | Low | High | High |
| Learning curve | Low | High | Medium |
| Convenience | Medium | Low | High |
| Performance | Good | Excellent | Excellent |

## Best Practices

1. **Use Files utility for simple operations** - Use Files.readAllLines(), Files.write(), etc.
2. **Choose blocking I/O for simplicity** - When concurrency requirements are low
3. **Use NIO for high-concurrency scenarios** - When handling thousands of connections
4. **Always close resources properly** - Use try-with-resources or finally blocks
5. **Buffer appropriately** - Size buffers based on typical data volumes
6. **Consider character encoding** - Specify UTF-8 explicitly for text files
7. **Monitor file access patterns** - Use WatchService for reactive file handling
8. **Avoid blocking the UI thread** - Perform file I/O on background threads

---

## Complete Working Examples

### Example 1: Traditional File I/O with Streams

```java
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TraditionalFileIO {
    
    public static void writeToFile(String filename, String[] lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.println(line);
            }
        } // Auto-closes resources
    }
    
    public static void readFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }
        }
    }
    
    public static void copyFile(String source, String destination) throws IOException {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination))) {
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
    }
    
    public static void appendToFile(String filename, String text) throws IOException {
        try (FileWriter fw = new FileWriter(filename, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(text);
        }
    }
    
    public static void main(String[] args) throws IOException {
        String testFile = "test_file.txt";
        String copyFile = "test_file_copy.txt";
        
        // Write to file
        System.out.println("Writing to file...");
        String[] content = {
            "Java File I/O Operations",
            "Traditional Blocking I/O",
            "Using streams and readers",
            "Closing with try-with-resources"
        };
        writeToFile(testFile, content);
        
        // Read from file
        System.out.println("\nReading from file:");
        readFromFile(testFile);
        
        // Copy file
        System.out.println("\nCopying file...");
        copyFile(testFile, copyFile);
        System.out.println("File copied successfully");
        
        // Append to file
        System.out.println("\nAppending to file...");
        appendToFile(testFile, "Additional line added");
        
        System.out.println("\nFinal file contents:");
        readFromFile(testFile);
        
        // Cleanup
        new File(testFile).delete();
        new File(copyFile).delete();
        System.out.println("\nCleanup completed");
    }
}
```

**Output:**
```
Writing to file...

Reading from file:
1: Java File I/O Operations
2: Traditional Blocking I/O
3: Using streams and readers
4: Closing with try-with-resources

Copying file...
File copied successfully

Appending to file...

Final file contents:
1: Java File I/O Operations
2: Traditional Blocking I/O
3: Using streams and readers
4: Closing with try-with-resources
5: Additional line added

Cleanup completed
```

### Example 2: Modern NIO.2 File Handling

```java
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

public class ModernFileHandling {
    
    public static void demonstratePathAPI() throws IOException {
        System.out.println("=== Path API Demo ===");
        
        // Create paths
        Path currentDir = Paths.get(".");
        Path file = Paths.get(".", "data", "example.txt");
        
        System.out.println("Current directory: " + currentDir.toAbsolutePath());
        System.out.println("File path: " + file);
        System.out.println("Absolute path: " + file.toAbsolutePath());
        System.out.println("File name only: " + file.getFileName());
        System.out.println("Parent directory: " + file.getParent());
    }
    
    public static void demonstrateBasicOperations() throws IOException {
        System.out.println("\n=== Basic File Operations ===");
        
        Path file = Paths.get("sample.txt");
        
        // Write lines to file
        List<String> lines = List.of(
            "Line 1: Introduction to NIO.2",
            "Line 2: Modern Java File API",
            "Line 3: Simple and efficient"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);
        System.out.println("File written: " + file);
        
        // Read all lines
        List<String> readLines = Files.readAllLines(file);
        System.out.println("File contents:");
        readLines.forEach(System.out::println);
        
        // File properties
        System.out.println("\nFile properties:");
        System.out.println("Size: " + Files.size(file) + " bytes");
        System.out.println("Exists: " + Files.exists(file));
        System.out.println("Is file: " + Files.isRegularFile(file));
        System.out.println("Is readable: " + Files.isReadable(file));
        System.out.println("Is writable: " + Files.isWritable(file));
    }
    
    public static void demonstrateDirectoryOperations() throws IOException {
        System.out.println("\n=== Directory Operations ===");
        
        Path dir = Paths.get("test_directory");
        
        // Create directory
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
            System.out.println("Directory created: " + dir);
        }
        
        // Create nested directories
        Path nestedDir = Paths.get("test_directory", "sub1", "sub2");
        Files.createDirectories(nestedDir);
        System.out.println("Nested directories created");
        
        // List directory contents
        System.out.println("\nDirectory contents of " + dir + ":");
        try (var stream = Files.list(dir)) {
            stream.forEach(p -> System.out.println("  " + p.getFileName()));
        }
        
        // Walk directory tree
        System.out.println("\nDirectory tree:");
        try (var stream = Files.walk(dir)) {
            stream.forEach(p -> {
                int depth = p.getNameCount() - dir.getNameCount();
                System.out.println("  ".repeat(depth) + p.getFileName());
            });
        }
    }
    
    public static void demonstrateFileCopying() throws IOException {
        System.out.println("\n=== File Copying ===");
        
        Path source = Paths.get("sample.txt");
        Path copy = Paths.get("sample_copy.txt");
        
        Files.copy(source, copy, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied from " + source + " to " + copy);
        
        // Copy with options
        Path copyAtomic = Paths.get("sample_atomic.txt");
        Files.copy(source, copyAtomic, 
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES);
        System.out.println("Atomic copy created: " + copyAtomic);
    }
    
    public static void demonstrateFileMoving() throws IOException {
        System.out.println("\n=== File Moving ===");
        
        Path source = Paths.get("sample_copy.txt");
        Path target = Paths.get("sample_moved.txt");
        
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File moved from " + source + " to " + target);
    }
    
    public static void cleanup() throws IOException {
        System.out.println("\n=== Cleanup ===");
        
        Path[] filesToDelete = {
            Paths.get("sample.txt"),
            Paths.get("sample_atomic.txt"),
            Paths.get("sample_moved.txt"),
            Paths.get("test_directory")
        };
        
        for (Path path : filesToDelete) {
            if (Files.exists(path)) {
                if (Files.isDirectory(path)) {
                    Files.walk(path)
                        .sorted((p1, p2) -> p2.compareTo(p1))
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                System.err.println("Failed to delete " + p);
                            }
                        });
                } else {
                    Files.delete(path);
                }
                System.out.println("Deleted: " + path);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        demonstratePathAPI();
        demonstrateBasicOperations();
        demonstrateDirectoryOperations();
        demonstrateFileCopying();
        demonstrateFileMoving();
        cleanup();
    }
}
```

**Output:**
```
=== Path API Demo ===
Current directory: C:\projects\java
File path: .\data\example.txt
Absolute path: C:\projects\java\data\example.txt
File name only: example.txt
Parent directory: .\data

=== Basic File Operations ===
File written: sample.txt
File contents:
Line 1: Introduction to NIO.2
Line 2: Modern Java File API
Line 3: Simple and efficient

File properties:
Size: 96 bytes
Exists: true
Is file: true
Is readable: true
Is writable: true

=== Directory Operations ===
Directory created: test_directory
Nested directories created

Directory contents of test_directory:
  sub1

Directory tree:
  test_directory
    sub1
      sub2

=== File Copying ===
File copied from sample.txt to sample_copy.txt
Atomic copy created: sample_atomic.txt

=== File Moving ===
File moved from sample_copy.txt to sample_moved.txt

=== Cleanup ===
Deleted: sample.txt
Deleted: sample_atomic.txt
Deleted: sample_moved.txt
Deleted: test_directory
```

### Example 3: NIO Channels and Buffers

```java
import java.nio.file.*;
import java.nio.channels.*;
import java.nio.ByteBuffer;
import java.io.IOException;

public class NIOChannels {
    
    public static void demonstrateFileChannel() throws IOException {
        System.out.println("=== NIO File Channel Demo ===");
        
        Path file = Paths.get("nio_test.txt");
        
        // Write using FileChannel
        String content = "NIO FileChannel for efficient I/O operations";
        try (RandomAccessFile raf = new RandomAccessFile(file.toFile(), "rw");
             FileChannel channel = raf.getChannel()) {
            
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(content.getBytes());
            buffer.flip();
            
            int bytesWritten = channel.write(buffer);
            System.out.println("Bytes written: " + bytesWritten);
        }
        
        // Read using FileChannel
        System.out.println("\nReading file:");
        try (RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r");
             FileChannel channel = raf.getChannel()) {
            
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead = channel.read(buffer);
            
            buffer.flip();
            byte[] bytes = new byte[bytesRead];
            buffer.get(bytes);
            System.out.println("Content: " + new String(bytes));
        }
        
        // File position operations
        System.out.println("\nFile size and position:");
        try (RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r");
             FileChannel channel = raf.getChannel()) {
            System.out.println("File size: " + channel.size() + " bytes");
        }
    }
    
    public static void demonstrateBufferOperations() {
        System.out.println("\n=== Buffer Operations ===");
        
        // Create buffer
        ByteBuffer buffer = ByteBuffer.allocate(256);
        System.out.println("Initial state:");
        System.out.println("Capacity: " + buffer.capacity());
        System.out.println("Position: " + buffer.position());
        System.out.println("Limit: " + buffer.limit());
        
        // Put data
        String data = "Buffer demonstration";
        buffer.put(data.getBytes());
        System.out.println("\nAfter put:");
        System.out.println("Position: " + buffer.position());
        System.out.println("Limit: " + buffer.limit());
        
        // Flip for reading
        buffer.flip();
        System.out.println("\nAfter flip:");
        System.out.println("Position: " + buffer.position());
        System.out.println("Limit: " + buffer.limit());
        
        // Read data
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        System.out.println("Read data: " + new String(bytes));
        
        // Clear for reuse
        buffer.clear();
        System.out.println("\nAfter clear:");
        System.out.println("Position: " + buffer.position());
        System.out.println("Limit: " + buffer.limit());
    }
    
    public static void demonstrateChannelTransfer() throws IOException {
        System.out.println("\n=== Channel Transfer ===");
        
        Path source = Paths.get("source.txt");
        Path destination = Paths.get("destination.txt");
        
        // Write source file
        Files.write(source, "Source file content for transfer".getBytes());
        
        // Transfer using channels
        try (RandomAccessFile srcFile = new RandomAccessFile(source.toFile(), "r");
             RandomAccessFile destFile = new RandomAccessFile(destination.toFile(), "rw");
             FileChannel srcChannel = srcFile.getChannel();
             FileChannel destChannel = destFile.getChannel()) {
            
            long position = 0;
            long size = srcChannel.size();
            while (position < size) {
                position += srcChannel.transferTo(position, 1024, destChannel);
            }
            System.out.println("Transferred " + size + " bytes");
        }
        
        // Verify
        String destContent = new String(Files.readAllBytes(destination));
        System.out.println("Destination content: " + destContent);
        
        // Cleanup
        Files.delete(source);
        Files.delete(destination);
    }
    
    public static void main(String[] args) throws IOException {
        demonstrateFileChannel();
        demonstrateBufferOperations();
        demonstrateChannelTransfer();
    }
}
```

**Output:**
```
=== NIO File Channel Demo ===
Bytes written: 45

Reading file:
Content: NIO FileChannel for efficient I/O operations

File size and position:
File size: 45 bytes

=== Buffer Operations ===
Initial state:
Capacity: 256
Position: 0
Limit: 256

After put:
Position: 20
Limit: 256

After flip:
Position: 0
Limit: 20

Read data: Buffer demonstration

After clear:
Position: 0
Limit: 256

=== Channel Transfer ===
Transferred 32 bytes
Destination content: Source file content for transfer
```

### Example 4: File Watching with WatchService

```java
import java.nio.file.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FileWatching {
    
    public static void watchDirectory(Path directory) throws IOException, InterruptedException {
        System.out.println("Watching directory: " + directory);
        
        WatchService watchService = FileSystems.getDefault().newWatchService();
        WatchKey key = directory.register(watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY);
        
        System.out.println("Monitoring started. Press Ctrl+C to stop.");
        
        for (int i = 0; i < 10; i++) {
            WatchKey k = watchService.poll(5, TimeUnit.SECONDS);
            if (k == null) {
                System.out.println("No file activity...");
                continue;
            }
            
            for (WatchEvent<?> event : k.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                Path eventPath = (Path) event.context();
                
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("[CREATE] " + eventPath);
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("[DELETE] " + eventPath);
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("[MODIFY] " + eventPath);
                }
            }
            
            if (!k.reset()) {
                System.out.println("Watch key is no longer valid");
                break;
            }
        }
        
        watchService.close();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Path testDir = Paths.get("watch_test");
        if (!Files.exists(testDir)) {
            Files.createDirectory(testDir);
        }
        
        // Start watching in a separate thread
        Thread watchThread = new Thread(() -> {
            try {
                watchDirectory(testDir);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        watchThread.start();
        
        // Simulate file operations
        Thread.sleep(1000);
        
        Path file1 = testDir.resolve("file1.txt");
        Files.write(file1, "Content 1".getBytes());
        System.out.println("Created file1.txt");
        
        Thread.sleep(500);
        
        Files.write(file1, "Updated content".getBytes());
        System.out.println("Modified file1.txt");
        
        Thread.sleep(500);
        
        Path file2 = testDir.resolve("file2.txt");
        Files.write(file2, "Content 2".getBytes());
        System.out.println("Created file2.txt");
        
        Thread.sleep(500);
        
        Files.delete(file1);
        System.out.println("Deleted file1.txt");
        
        watchThread.join();
        
        // Cleanup
        Files.walk(testDir)
            .sorted((p1, p2) -> p2.compareTo(p1))
            .forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
```

**Output:**
```
Watching directory: watch_test
Monitoring started. Press Ctrl+C to stop.
Created file1.txt
[CREATE] file1.txt
Modified file1.txt
[MODIFY] file1.txt
Created file2.txt
[CREATE] file2.txt
Deleted file1.txt
[DELETE] file1.txt
```

## Performance Analysis

- **Traditional I/O**: Good for sequential access, simple operations; average 100-200 MB/s
- **NIO**: Excellent for random access, high-concurrency; up to 500 MB/s
- **NIO.2**: Optimal for most use cases; performance comparable to NIO with better usability
- **Buffer Size**: Larger buffers (8KB-64KB) generally perform better for large files

## Summary

Java's file handling capabilities have evolved significantly. For new code, prefer NIO.2's modern Files API for simplicity and performance. Traditional I/O remains suitable for simple scenarios. NIO is essential when handling thousands of concurrent file operations. Understanding the trade-offs between simplicity, performance, and scalability enables choosing the right approach for each situation. Always use try-with-resources to ensure proper resource cleanup.
