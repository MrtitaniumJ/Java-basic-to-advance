/**
 * File Operations Demonstration Program
 * Demonstrates: reading, writing, appending, and manipulating files
 * 
 * Learning Objectives:
 * - Master file I/O operations in Java
 * - Understand different file reading/writing approaches
 * - Learn resource management for file operations
 * - Handle file-related exceptions properly
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileOperationsDemo {
    
    // ===== WRITING TO FILES =====
    
    // Write string content to file using FileWriter
    public static void writeToFileBasic(String filename, String content) {
        System.out.println("--- WRITING TO FILE (BASIC) ---");
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
            System.out.println("Successfully wrote to file: " + filename);
            System.out.println("Content: " + content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Write multiple lines to file
    public static void writeMultipleLines(String filename, List<String> lines) {
        System.out.println("--- WRITING MULTIPLE LINES ---");
        try (FileWriter writer = new FileWriter(filename)) {
            for (String line : lines) {
                writer.write(line);
                writer.write("\n");
            }
            System.out.println("Successfully wrote " + lines.size() + " lines to file");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Write using BufferedWriter (more efficient)
    public static void writeWithBufferedWriter(String filename, String content) {
        System.out.println("--- WRITING WITH BUFFERED WRITER ---");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            writer.flush();  // Ensure all data is written
            System.out.println("Successfully wrote using BufferedWriter");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // ===== READING FROM FILES =====
    
    // Read entire file content using FileReader
    public static String readFileBasic(String filename) {
        System.out.println("--- READING FILE (BASIC) ---");
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(filename)) {
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            System.out.println("Successfully read from file: " + filename);
            return content.toString();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return "";
        }
    }
    
    // Read file line by line using BufferedReader
    public static List<String> readFileLinesBuffered(String filename) {
        System.out.println("--- READING LINES WITH BUFFERED READER ---");
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("Successfully read " + lines.size() + " lines from file");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
    
    // Read file using Scanner (good for parsing)
    public static List<String> readFileWithScanner(String filename) {
        System.out.println("--- READING FILE WITH SCANNER ---");
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            System.out.println("Successfully read " + lines.size() + " lines using Scanner");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
    
    // Read file using NIO (Java 7+)
    public static String readFileNIO(String filename) {
        System.out.println("--- READING FILE USING NIO ---");
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            String content = new String(encoded);
            System.out.println("Successfully read file using NIO");
            return content;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return "";
        }
    }
    
    // ===== APPENDING TO FILES =====
    
    // Append content to existing file
    public static void appendToFile(String filename, String content) {
        System.out.println("--- APPENDING TO FILE ---");
        try (FileWriter writer = new FileWriter(filename, true)) {  // true = append mode
            writer.write(content);
            writer.write("\n");
            System.out.println("Successfully appended to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }
    
    // Append multiple lines
    public static void appendMultipleLines(String filename, List<String> lines) {
        System.out.println("--- APPENDING MULTIPLE LINES ---");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Successfully appended " + lines.size() + " lines");
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }
    
    // ===== FILE MANIPULATION =====
    
    // Check if file exists
    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }
    
    // Get file size
    public static long getFileSize(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            return file.length();
        }
        return -1;
    }
    
    // Delete a file
    public static boolean deleteFile(String filename) {
        System.out.println("--- DELETING FILE ---");
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Successfully deleted: " + filename);
                return true;
            } else {
                System.out.println("Failed to delete: " + filename);
                return false;
            }
        } else {
            System.out.println("File does not exist: " + filename);
            return false;
        }
    }
    
    // Rename/Move a file
    public static boolean renameFile(String oldName, String newName) {
        System.out.println("--- RENAMING FILE ---");
        File file = new File(oldName);
        File newFile = new File(newName);
        
        if (file.exists()) {
            if (file.renameTo(newFile)) {
                System.out.println("Successfully renamed " + oldName + " to " + newName);
                return true;
            } else {
                System.out.println("Failed to rename file");
                return false;
            }
        } else {
            System.out.println("Source file does not exist: " + oldName);
            return false;
        }
    }
    
    // Get file information
    public static void getFileInfo(String filename) {
        System.out.println("--- FILE INFORMATION ---");
        File file = new File(filename);
        
        if (file.exists()) {
            System.out.println("File name: " + file.getName());
            System.out.println("File path: " + file.getAbsolutePath());
            System.out.println("File size: " + file.length() + " bytes");
            System.out.println("Is file: " + file.isFile());
            System.out.println("Is directory: " + file.isDirectory());
            System.out.println("Can read: " + file.canRead());
            System.out.println("Can write: " + file.canWrite());
            System.out.println("Last modified: " + new Date(file.lastModified()));
        } else {
            System.out.println("File does not exist: " + filename);
        }
    }
    
    // ===== COPY FILE =====
    
    // Copy file using NIO (efficient for large files)
    public static boolean copyFileNIO(String sourceFile, String destFile) {
        System.out.println("--- COPYING FILE (NIO) ---");
        try {
            Files.copy(Paths.get(sourceFile), Paths.get(destFile), 
                      StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Successfully copied file");
            return true;
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
            return false;
        }
    }
    
    // Copy file using streams
    public static boolean copyFileStreams(String sourceFile, String destFile) {
        System.out.println("--- COPYING FILE (STREAMS) ---");
        try (FileInputStream input = new FileInputStream(sourceFile);
             FileOutputStream output = new FileOutputStream(destFile)) {
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            System.out.println("Successfully copied file using streams");
            return true;
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
            return false;
        }
    }
    
    // ===== WORKING WITH DIRECTORIES =====
    
    // Create directory
    public static boolean createDirectory(String dirName) {
        System.out.println("--- CREATING DIRECTORY ---");
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Successfully created directory: " + dirName);
                return true;
            } else {
                System.out.println("Failed to create directory");
                return false;
            }
        } else {
            System.out.println("Directory already exists: " + dirName);
            return true;
        }
    }
    
    // List files in directory
    public static void listFilesInDirectory(String dirName) {
        System.out.println("--- LISTING FILES IN DIRECTORY ---");
        File dir = new File(dirName);
        
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                System.out.println("Files in directory \"" + dirName + "\":");
                for (File file : files) {
                    String type = file.isDirectory() ? "[DIR]" : "[FILE]";
                    System.out.println("  " + type + " " + file.getName());
                }
            }
        } else {
            System.out.println("Not a directory: " + dirName);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("===== FILE OPERATIONS DEMO =====\n");
        
        String testFile = "test_file.txt";
        String testFile2 = "test_file2.txt";
        String testDir = "test_directory";
        
        // ===== WRITING =====
        System.out.println("=== WRITING OPERATIONS ===\n");
        writeToFileBasic(testFile, "Hello, World!\nJava File I/O Operations");
        System.out.println();
        
        List<String> lines = Arrays.asList(
            "Line 1: Programming",
            "Line 2: File Operations",
            "Line 3: Java IO"
        );
        writeMultipleLines(testFile, lines);
        System.out.println();
        
        writeWithBufferedWriter(testFile, "Buffered Writer Content\nMore efficient for large files");
        System.out.println();
        
        // ===== READING =====
        System.out.println("\n=== READING OPERATIONS ===\n");
        String content = readFileBasic(testFile);
        System.out.println("File content:\n" + content);
        System.out.println();
        
        List<String> readLines = readFileLinesBuffered(testFile);
        System.out.println("Read lines:");
        for (String line : readLines) {
            System.out.println("  " + line);
        }
        System.out.println();
        
        readFileWithScanner(testFile);
        System.out.println();
        
        readFileNIO(testFile);
        System.out.println();
        
        // ===== APPENDING =====
        System.out.println("\n=== APPENDING OPERATIONS ===\n");
        appendToFile(testFile, "Appended line 1");
        System.out.println();
        
        List<String> appendLines = Arrays.asList(
            "Appended line 2",
            "Appended line 3"
        );
        appendMultipleLines(testFile, appendLines);
        System.out.println();
        
        // ===== FILE INFORMATION =====
        System.out.println("\n=== FILE INFORMATION ===\n");
        getFileInfo(testFile);
        System.out.println();
        System.out.println("File exists: " + fileExists(testFile));
        System.out.println("File size: " + getFileSize(testFile) + " bytes");
        System.out.println();
        
        // ===== COPY FILE =====
        System.out.println("\n=== COPY OPERATIONS ===\n");
        copyFileNIO(testFile, testFile2);
        System.out.println();
        
        // ===== DIRECTORY OPERATIONS =====
        System.out.println("\n=== DIRECTORY OPERATIONS ===\n");
        createDirectory(testDir);
        System.out.println();
        
        // Create some test files in directory
        try {
            new File(testDir + "/file1.txt").createNewFile();
            new File(testDir + "/file2.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating test files: " + e.getMessage());
        }
        
        listFilesInDirectory(testDir);
        System.out.println();
        
        // ===== CLEANUP (OPTIONAL) =====
        System.out.println("\n=== CLEANUP ===\n");
        // Uncomment to delete test files
        // deleteFile(testFile);
        // deleteFile(testFile2);
        System.out.println("Test files created successfully. You can delete them manually.");
    }
}
