/**
 * Hello World Program
 * 
 * This is the most basic Java program that demonstrates:
 * 1. How to create a Java class
 * 2. How to write the main method (entry point)
 * 3. How to use System.out.println() for output
 * 
 * Learning Objectives:
 * - Understand Java program structure
 * - Learn about the main method
 * - Practice basic output statements
 * 
 * @author Learning Java
 * @version 1.0
 */

public class HelloWorld {
    
    /**
     * Main method - the entry point of the program
     * The JVM looks for this method when executing the program
     * 
     * @param args Command line arguments (not used in this program)
     */
    public static void main(String[] args) {
        // Print basic message
        System.out.println("Hello, World!");
        
        // Print multiple messages
        System.out.println("Welcome to Java Programming!");
        System.out.println("This is a basic Java program.");
        
        // Print using variables
        String greeting = "Learning Java is fun!";
        System.out.println(greeting);
        
        // Print a formatted output
        String name = "Java Learner";
        int year = 2026;
        System.out.println("Hello, " + name + "! Welcome to " + year);
        
        // Using printf for formatted output
        System.out.printf("Name: %s, Year: %d%n", name, year);
    }
}
