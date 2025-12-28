# Introduction to Java and Environment Setup

## What is Java?

Java is a **programming language** that helps us create computer programs. Think of it like learning a new language to talk to computers!

### Why Java is Special?

1. **Write Once, Run Anywhere**: You can write a program on Windows and run it on Mac or Linux without changes
2. **Simple**: Easy to learn and understand
3. **Secure**: Protects your computer from harmful code
4. **Popular**: Used by millions of developers worldwide

## Real-World Examples

Java is used to create:
- **Mobile Apps**: Android apps are written in Java
- **Websites**: Banking websites, shopping sites
- **Games**: Minecraft is written in Java
- **Desktop Applications**: IDEs like Eclipse, IntelliJ

## Setting Up Java Environment

### Step 1: Install Java
1. Go to Oracle's website or use OpenJDK
2. Download Java Development Kit (JDK)
3. Install it on your computer

### Step 2: Set Environment Variables
```
JAVA_HOME = C:\Program Files\Java\jdk-17
PATH = Add %JAVA_HOME%\bin
```

### Step 3: Verify Installation
Open command prompt and type:
```bash
java -version
javac -version
```

You should see version information if everything is installed correctly.

## Your First Java Program

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java World!");
    }
}
```

### How to Run:
1. Save file as `HelloWorld.java`
2. Open command prompt
3. Navigate to the file location
4. Compile: `javac HelloWorld.java`
5. Run: `java HelloWorld`

## Key Concepts to Remember

- **File Name**: Must match the class name exactly
- **Case Sensitive**: `HelloWorld` ≠ `helloworld`
- **Extension**: Java files always end with `.java`
- **Compilation**: Creates `.class` files that computer can understand

## Practice Exercises

1. Create a program that prints your name
2. Create a program that prints multiple lines
3. Try changing the class name and see what happens