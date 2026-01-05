# Stack in Java

## Overview

`Stack` is a legacy collection class that extends `Vector` and implements a Last-In-First-Out (LIFO) data structure. In a stack, elements are added and removed from the same end (the top). Stack inherits thread-safety from Vector (all methods are synchronized) and provides a simple interface for push and pop operations. While Stack remains part of the Java API, the modern approach is to use `Deque` interface implementations like `LinkedList` or `ArrayDeque`, which provide better performance and more flexibility.

## Historical Context

Stack has a unique place in Java's Collections hierarchy:

- **Java 1.0**: Stack introduced as a Vector subclass
- **Java 1.6**: Deque interface introduced as a more modern alternative
- **Today**: Stack is legacy; modern code uses LinkedList or ArrayDeque instead
- **Why subclasses Vector**: Historical design decision now considered poor practice (violates composition over inheritance principle)

## How It Works Internally

Stack extends Vector and operates in LIFO fashion. Each element is added to the "top" of the stack and removed from the same position.

### Internal Structure
```
Empty Stack:
[]

Stack with elements [A, B, C] (C added last, so C is at top):
Index:  0  1  2
        A  B  C  <- top (next to pop)
       
push(D):
Index:  0  1  2  3
        A  B  C  D  <- top (next to pop)

pop():
Index:  0  1  2
        A  B  C  <- top (next to pop)
```

The Stack class extends Vector, so it uses the same dynamic array mechanism with 2x growth factor.

## Constructors and Creation Methods

```java
// Default constructor - creates empty stack
Stack<String> stack1 = new Stack<>();

// Constructor with initial capacity (inherited from Vector)
// Note: This is not directly exposed but Vector can be extended
Stack<String> stack2 = new Stack<String>() {
    {
        // Initialize with elements
        push("First");
        push("Second");
        push("Third");
    }
};

// From Java 21+ - using modern Deque (recommended)
// Deque<String> modernStack = new ArrayDeque<>();

// Creating from another collection (elements in insertion order)
List<String> items = Arrays.asList("A", "B", "C");
Stack<String> stack3 = new Stack<>();
for (String item : items) {
    stack3.push(item);
}
// stack3: [A, B, C] with C at top
```

## Core Operations

All Stack operations are synchronized (inherited from Vector). The main operations are:

### Push Operation (Add)
```java
Stack<Integer> stack = new Stack<>();

// push(E e) - adds element to top, synchronized, O(1) amortized
stack.push(10);    // [10]
stack.push(20);    // [10, 20]
stack.push(30);    // [10, 20, 30]
stack.push(40);    // [10, 20, 30, 40]

System.out.println("Stack after pushes: " + stack);
// [10, 20, 30, 40] (40 is at top)
```

### Pop Operation (Remove)
```java
Stack<Integer> stack = new Stack<>(Arrays.asList(10, 20, 30, 40));

// pop() - removes and returns top element, synchronized, O(1)
int top = stack.pop();  // Returns 40, removes it
System.out.println("Popped: " + top);  // 40
System.out.println("Stack after pop: " + stack);
// [10, 20, 30]

// Pop until empty
while (!stack.isEmpty()) {
    System.out.println("Popping: " + stack.pop());
}
// Output:
// Popping: 30
// Popping: 20
// Popping: 10
```

### Peek Operation (View without Remove)
```java
Stack<String> stack = new Stack<>(Arrays.asList(
    "Alice", "Bob", "Charlie"
));

// peek() - returns top element without removing, synchronized, O(1)
String topName = stack.peek();
System.out.println("Top element: " + topName);  // Charlie
System.out.println("Stack unchanged: " + stack);
// [Alice, Bob, Charlie]

// peek on empty stack returns null
Stack<String> emptyStack = new Stack<>();
String empty = emptyStack.peek();  // null
```

### Search Operation
```java
Stack<String> stack = new Stack<>(Arrays.asList(
    "Bottom", "Middle", "Top"
));

// search(Object o) - returns distance from top, 1-based indexing
int position = stack.search("Middle");
System.out.println("Position from top: " + position);  // 2

int topPos = stack.search("Top");
System.out.println("Top position from top: " + topPos);  // 1

int notFound = stack.search("NotThere");
System.out.println("Not found: " + notFound);  // -1

// Note: search() is O(n) because it searches from top
```

### Query Operations
```java
Stack<Integer> stack = new Stack<>(Arrays.asList(1, 2, 3, 4, 5));

// isEmpty() - checks if stack is empty, synchronized, O(1)
boolean empty = stack.isEmpty();  // false

// size() - returns number of elements, synchronized, O(1)
int size = stack.size();  // 5

// empty() - legacy method, same as isEmpty(), O(1)
boolean empty2 = stack.empty();  // false

// Iterate to view elements (without modification)
System.out.println("Stack contents: " + stack);
// [1, 2, 3, 4, 5] (5 is at top)

// Check if contains element
boolean contains = stack.contains(3);  // true
```

### Combined Operations
```java
Stack<Character> stack = new Stack<>();

// Check and pop
if (!stack.isEmpty()) {
    char top = stack.pop();
}

// Push multiple elements safely
for (char c : "STACK".toCharArray()) {
    stack.push(c);
}
System.out.println("After pushing STACK: " + stack);

// Reverse string using stack
Stack<Character> reverseStack = new Stack<>();
String original = "HELLO";
for (char c : original.toCharArray()) {
    reverseStack.push(c);
}

System.out.print("Reversed: ");
while (!reverseStack.isEmpty()) {
    System.out.print(reverseStack.pop());
}
System.out.println();  // Output: OLLEH
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity | Synchronized |
|-----------|-----------------|------------------|--------------|
| push(E)   | O(1) amortized  | O(n) worst case  | Yes          |
| pop()     | O(1)            | O(1)             | Yes          |
| peek()    | O(1)            | O(1)             | Yes          |
| search()  | O(n)            | O(1)             | Yes          |
| isEmpty() | O(1)            | O(1)             | Yes          |
| size()    | O(1)            | O(1)             | Yes          |

**Note**: All methods include synchronization overhead inherited from Vector.

## Memory Layout and Resizing

Stack extends Vector and uses the same dynamic array mechanism:

```
Growth pattern (2x factor from Vector):
Initial:     capacity = 10
After push 11: capacity = 20
After push 21: capacity = 40
After push 41: capacity = 80

Memory considerations:
- Stack creates a Vector under the hood
- Uses contiguous memory like ArrayList
- 2x growth factor means more wasted space
- Each push potentially triggers O(n) copying (rare but possible)
```

## Advantages

1. **Simple, intuitive API**: push(), pop(), peek() are very clear
2. **Thread-safe**: All operations synchronized (inherited from Vector)
3. **Familiar concept**: LIFO is well-understood in computer science
4. **Efficient core operations**: push, pop, peek are O(1)
5. **Legacy compatibility**: Widely used in older Java code
6. **Works with generics**: Supports type-safe operations

## Disadvantages

1. **Legacy design**: Extends Vector (composition would be better)
2. **Synchronization overhead**: Even single-threaded code pays the cost
3. **Limited API**: Only LIFO operations; limited flexibility
4. **Vector inheritance problems**: Inherits problematic growth strategy
5. **Not recommended for new code**: Modern alternatives are better
6. **Method-level locking only**: Not truly concurrent-safe for complex operations
7. **Performance**: Slower than modern alternatives like ArrayDeque

## When to Use Stack

Use Stack when:
- You need a simple LIFO data structure
- Working with legacy Java code already using Stack
- Thread-safety via method-level synchronization is acceptable
- You need a familiar interface in legacy systems
- Simplicity is prioritized over performance

**Modern alternative**: Use `Deque<Integer> stack = new ArrayDeque<>()` instead.

## Common Use Cases

1. **Expression evaluation**: Evaluate infix to postfix conversion, calculate postfix expressions
2. **Browser navigation**: Back button (push visited pages, pop to go back)
3. **Undo/Redo functionality**: Push actions, pop for undo
4. **Function call stack**: JVM uses stacks for method calls and local variables
5. **DFS (Depth-First Search)**: Graph traversal using stack
6. **Backtracking algorithms**: Solve maze, N-queens problem
7. **Balanced parentheses checking**: Validate matching brackets
8. **Memory allocation**: Stack vs heap memory management
9. **Parsing**: Compiler parsing and syntax analysis
10. **Expression evaluation**: Converting and evaluating mathematical expressions

## Practical Code Examples

```java
import java.util.*;
import java.util.stream.*;

public class StackExamples {
    
    // Example 1: Basic Stack operations
    public static void basicStackOperations() {
        Stack<Integer> stack = new Stack<>();
        
        // Push elements
        System.out.println("Pushing: 10, 20, 30, 40, 50");
        for (int i = 1; i <= 5; i++) {
            stack.push(i * 10);
        }
        System.out.println("Stack: " + stack);  // [10, 20, 30, 40, 50]
        
        // Peek at top
        System.out.println("Top element (peek): " + stack.peek());  // 50
        System.out.println("Stack unchanged: " + stack);
        
        // Pop elements
        System.out.println("\nPopping all elements:");
        while (!stack.isEmpty()) {
            System.out.println("  Popped: " + stack.pop());
        }
        System.out.println("Stack after popping: " + stack);  // []
    }
    
    // Example 2: Reverse a string using stack
    public static void reverseString() {
        String input = "HELLO WORLD";
        Stack<Character> stack = new Stack<>();
        
        // Push each character
        for (char c : input.toCharArray()) {
            stack.push(c);
        }
        
        // Pop to reverse
        System.out.print("Original: " + input);
        System.out.print("\nReversed: ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }
    
    // Example 3: Check balanced parentheses
    public static boolean isBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : expression.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (!isMatching(top, c)) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    private static boolean isMatching(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
    
    // Example 4: Evaluate postfix expression
    public static double evaluatePostfix(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split(" ");
        
        for (String token : tokens) {
            if (isOperator(token)) {
                double b = stack.pop();
                double a = stack.pop();
                double result = performOperation(a, b, token.charAt(0));
                stack.push(result);
            } else {
                stack.push(Double.parseDouble(token));
            }
        }
        
        return stack.pop();
    }
    
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
               token.equals("*") || token.equals("/");
    }
    
    private static double performOperation(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: return 0;
        }
    }
    
    // Example 5: Browser history (back/forward)
    public static void browserHistory() {
        Stack<String> history = new Stack<>();
        Stack<String> forward = new Stack<>();
        
        String currentPage = "Home";
        
        // Visit pages
        history.push(currentPage);
        currentPage = "Google";
        history.push(currentPage);
        System.out.println("Current page: " + currentPage);
        
        currentPage = "GitHub";
        history.push(currentPage);
        System.out.println("Current page: " + currentPage);
        
        currentPage = "StackOverflow";
        history.push(currentPage);
        System.out.println("Current page: " + currentPage);
        
        // Go back
        System.out.println("\nGoing back...");
        forward.push(currentPage);
        currentPage = history.pop();
        System.out.println("Current page: " + currentPage);
        
        forward.push(currentPage);
        currentPage = history.pop();
        System.out.println("Current page: " + currentPage);
        
        // Go forward
        System.out.println("\nGoing forward...");
        history.push(currentPage);
        currentPage = forward.pop();
        System.out.println("Current page: " + currentPage);
    }
    
    // Example 6: Undo/Redo functionality
    public static class Document {
        private Stack<String> undoStack = new Stack<>();
        private Stack<String> redoStack = new Stack<>();
        private String content = "";
        
        public void type(String text) {
            undoStack.push(content);
            redoStack.clear();  // Clear redo when new action
            content += text;
            System.out.println("Typed: " + text + ", Content: " + content);
        }
        
        public void undo() {
            if (!undoStack.isEmpty()) {
                redoStack.push(content);
                content = undoStack.pop();
                System.out.println("Undo: Content is now: " + content);
            }
        }
        
        public void redo() {
            if (!redoStack.isEmpty()) {
                undoStack.push(content);
                content = redoStack.pop();
                System.out.println("Redo: Content is now: " + content);
            }
        }
        
        public String getContent() {
            return content;
        }
    }
    
    // Example 7: Depth-first search using stack
    public static void dfsTraversal() {
        // Simple graph: 0->1, 0->2, 1->3, 2->3
        int[][] graph = {
            {1, 2},      // 0 connected to 1, 2
            {3},         // 1 connected to 3
            {3},         // 2 connected to 3
            {}           // 3 has no outgoing edges
        };
        
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[4];
        
        stack.push(0);
        System.out.print("DFS: ");
        
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                visited[node] = true;
                System.out.print(node + " ");
                
                // Push neighbors in reverse order to maintain left-to-right order
                for (int i = graph[node].length - 1; i >= 0; i--) {
                    if (!visited[graph[node][i]]) {
                        stack.push(graph[node][i]);
                    }
                }
            }
        }
        System.out.println();
    }
    
    // Example 8: Decimal to binary conversion using stack
    public static String decimalToBinary(int num) {
        Stack<Integer> stack = new Stack<>();
        
        while (num > 0) {
            stack.push(num % 2);
            num /= 2;
        }
        
        StringBuilder binary = new StringBuilder();
        while (!stack.isEmpty()) {
            binary.append(stack.pop());
        }
        
        return binary.toString().isEmpty() ? "0" : binary.toString();
    }
    
    // Example 9: Stack search operation
    public static void searchOperation() {
        Stack<String> stack = new Stack<>(Arrays.asList(
            "Bottom", "Middle", "TopA", "TopB", "Top"
        ));
        
        System.out.println("Stack: " + stack);
        
        int pos1 = stack.search("Top");      // 1 (top position)
        int pos2 = stack.search("Middle");   // 4
        int pos3 = stack.search("NotThere"); // -1
        
        System.out.println("Position of 'Top': " + pos1);
        System.out.println("Position of 'Middle': " + pos2);
        System.out.println("Position of 'NotThere': " + pos3);
    }
    
    // Example 10: Convert infix to postfix
    public static String infixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        
        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                postfix.append(c).append(" ");
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop();  // Remove '('
            } else if (isOperator(String.valueOf(c))) {
                while (!stack.isEmpty() && 
                       stack.peek() != '(' &&
                       precedence(stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }
        
        return postfix.toString().trim();
    }
    
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            default: return 0;
        }
    }
    
    // Example 11: Compare Stack vs ArrayDeque (modern alternative)
    public static void compareStackAndDeque() {
        int iterations = 100000;
        
        // Stack performance
        Stack<Integer> stack = new Stack<>();
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            stack.push(i);
        }
        for (int i = 0; i < iterations; i++) {
            stack.pop();
        }
        long stackTime = System.nanoTime() - start;
        
        // ArrayDeque performance (modern alternative)
        Deque<Integer> deque = new ArrayDeque<>();
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            deque.push(i);
        }
        for (int i = 0; i < iterations; i++) {
            deque.pop();
        }
        long dequeTime = System.nanoTime() - start;
        
        System.out.println("Stack 100k push/pop: " + stackTime + " ns");
        System.out.println("ArrayDeque 100k push/pop: " + dequeTime + " ns");
        System.out.println("ArrayDeque is faster by: " + 
            String.format("%.2f", (double) stackTime / dequeTime) + "x");
    }
    
    public static void main(String[] args) {
        System.out.println("=== Basic Stack Operations ===");
        basicStackOperations();
        
        System.out.println("\n=== Reverse String ===");
        reverseString();
        
        System.out.println("\n=== Check Balanced Parentheses ===");
        String[] expressions = {"(())", "([{}])", "({[}])", "((())"};
        for (String expr : expressions) {
            System.out.println(expr + " is balanced: " + isBalanced(expr));
        }
        
        System.out.println("\n=== Evaluate Postfix ===");
        double result = evaluatePostfix("5 3 + 2 *");
        System.out.println("5 3 + 2 * = " + result);  // (5+3)*2 = 16
        
        System.out.println("\n=== Browser History ===");
        browserHistory();
        
        System.out.println("\n=== Undo/Redo ===");
        Document doc = new Document();
        doc.type("Hello ");
        doc.type("World");
        doc.undo();
        doc.undo();
        doc.type("Java");
        doc.redo();
        System.out.println("Final content: " + doc.getContent());
        
        System.out.println("\n=== DFS Traversal ===");
        dfsTraversal();
        
        System.out.println("\n=== Decimal to Binary ===");
        System.out.println("10 in binary: " + decimalToBinary(10));
        System.out.println("255 in binary: " + decimalToBinary(255));
        
        System.out.println("\n=== Stack Search ===");
        searchOperation();
        
        System.out.println("\n=== Infix to Postfix ===");
        String infix = "3+4*2/(1-5)";
        String postfix = infixToPostfix(infix);
        System.out.println("Infix: " + infix);
        System.out.println("Postfix: " + postfix);
        
        System.out.println("\n=== Stack vs ArrayDeque ===");
        compareStackAndDeque();
    }
}
```

## Advanced Operations

### Legacy Vector Methods in Stack
```java
Stack<String> stack = new Stack<>();

// Stack uses Vector methods inherited
stack.addElement("A");           // Works but unusual for Stack
stack.insertElementAt("B", 0);   // Works but unusual
String first = stack.firstElement();  // Works
int index = stack.indexOf("A");  // Works but odd for Stack
```

### Synchronization Details
```java
Stack<String> stack = new Stack<>();

// Synchronized method calls ensure atomic operations
stack.push("A");  // synchronized
stack.pop();      // synchronized

// But compound operations aren't atomic
if (!stack.isEmpty()) {
    String top = stack.pop();
    // Another thread could pop between isEmpty() check and pop()
}

// Proper synchronization needed
synchronized(stack) {
    if (!stack.isEmpty()) {
        String top = stack.pop();
    }
}
```

## Thread-Safety Considerations

Stack inherits synchronization from Vector:

1. **Method-level synchronization**: Individual methods are atomic
2. **Compound operations**: Multiple operations on the stack aren't atomic as a group
3. **Iteration**: Can throw `ConcurrentModificationException`
4. **Performance cost**: Synchronization overhead even when not needed

```java
// Single threaded? Use ArrayDeque instead (much faster)
Deque<String> efficient = new ArrayDeque<>();

// Multi-threaded with simple synchronization? Use Stack (legacy)
Stack<String> legacy = new Stack<>();

// Modern multi-threaded? Use ConcurrentLinkedDeque
Deque<String> modern = new ConcurrentLinkedDeque<>();
```

## Comparison with Other Stack Implementations

| Feature | Stack | LinkedList (as Deque) | ArrayDeque |
|---------|-------|----------------------|-----------|
| LIFO Order | Yes | Yes | Yes |
| Thread-Safe | Yes (synced) | No | No |
| Performance | Good | Good | Excellent |
| Memory Overhead | Moderate | High | Low |
| Legacy Code | Common | Possible | Modern |
| Extends Vector | Yes | No | No |
| Search Efficient | O(n) | O(n) | O(n) |
| Random Access | O(1) | O(n) | O(1) |

## Modern Alternative: ArrayDeque

```java
// Old way (Stack)
Stack<Integer> stack = new Stack<>();

// Modern way (ArrayDeque as Deque)
Deque<Integer> deque = new ArrayDeque<>();

// Both support push/pop, but ArrayDeque is:
// - Faster (no synchronization overhead)
// - More flexible (supports queue operations too)
// - Better memory layout
```

## Summary

Stack is a legacy Java class that provides a simple, thread-safe LIFO data structure. It extends Vector, which gives it built-in synchronization but at a performance cost. For modern Java development, `ArrayDeque` or `LinkedList` (used as a Deque) are preferred alternatives as they offer better performance and more flexibility. Stack remains valuable for:

1. Maintaining legacy code compatibility
2. Educational purposes (learning LIFO concept)
3. Situations where method-level thread-safety is explicitly needed and performance isn't critical

Common applications include expression evaluation, browser history, undo/redo functionality, depth-first search, and balanced parentheses checking. Understanding Stack's behavior and limitations is important for writing efficient Java applications, even though it's no longer the recommended approach for new projects.

