# Stacks in Java - LIFO Data Structure

## Simple Explanation

Think of a **Stack** as a **stack of plates**:
- You can only add/remove plates from the **top**
- Last plate placed is the **first one you take** (LIFO)
- You cannot access plates in the middle directly
- All operations happen at one end only

### Real-World Analogy
Imagine a **stack of books**:
- Add a book on top (push)
- Remove a book from top (pop)
- Look at the top book without removing it (peek)
- You cannot take the bottom book without removing all books above it
- This is exactly how function calls work in programming!

## Professional Definition

A **Stack** is an Abstract Data Type (ADT) that follows the **LIFO (Last In, First Out)** principle. It is a linear data structure that allows insertion and deletion of elements from only one end, called the "top" of the stack. Stacks support three fundamental operations: push (add element), pop (remove element), and peek (view top element without removal).

## Stack ADT - Core Operations

```
Stack ADT Interface:
├── push(element)      - Add element to top
├── pop()              - Remove and return top element
├── peek() / top()     - View top element without removal
├── isEmpty()          - Check if stack is empty
└── size()             - Get number of elements
```

## LIFO Principle Visualization

```
Stack Operations:

Initial:     Push(A):    Push(B):    Push(C):    Pop():      Peek():
┌─────┐     ┌─────┐     ┌─────┐     ┌─────┐     ┌─────┐     ┌─────┐
│     │     │  A  │     │  B  │     │  C  │◄─TOP│  B  │◄─TOP│  B  │◄─TOP
└─────┘     └─────┘     ├─────┤     ├─────┤     ├─────┤     ├─────┤
 EMPTY                  │  A  │     │  B  │     │  A  │     │  A  │
                        └─────┘     ├─────┤     └─────┘     └─────┘
                                    │  A  │
                                    └─────┘
                                    Returns C    Returns B
                                                 (no removal)
```

## Stack Implementations in Java

### 1. Legacy Stack Class (❌ Not Recommended)

```java
import java.util.Stack;

// The old way (DON'T USE)
Stack<Integer> stack = new Stack<>();
stack.push(10);
stack.push(20);
int top = stack.pop();
```

**Problems with Legacy Stack:**
- Extends `Vector` (synchronized, slower)
- Allows index-based access (breaks stack abstraction)
- Not part of Collections Framework properly
- Violates Liskov Substitution Principle

### 2. ArrayDeque - Modern Approach (✅ Recommended)

```java
import java.util.ArrayDeque;
import java.util.Deque;

// The modern way (RECOMMENDED)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);
stack.push(20);
int top = stack.pop();
```

**Why ArrayDeque is Better:**
- ✅ Faster (no synchronization overhead)
- ✅ More memory efficient
- ✅ Part of modern Collections Framework
- ✅ Consistent with best practices
- ✅ Resizable array implementation

### Comparison:

| Feature | Stack (Legacy) | ArrayDeque | LinkedList |
|---------|----------------|------------|------------|
| **Performance** | Slower (synchronized) | Fastest | Fast |
| **Memory** | More overhead | Efficient | More overhead (pointers) |
| **Thread-Safe** | Yes | No | No |
| **Modern** | ❌ Legacy | ✅ Recommended | ✅ Alternative |
| **Index Access** | Yes (breaks abstraction) | No | No |
| **Best For** | Legacy code only | General stack operations | When also used as queue |

---

## Stack Operations - Complete Guide

```java
import java.util.ArrayDeque;
import java.util.Deque;

class StackOperations {
    
    public static void demonstrateBasicOperations() {
        System.out.println("=== Basic Stack Operations ===\n");
        
        // Creating a stack
        Deque<String> stack = new ArrayDeque<>();
        
        // Push - Add elements to top
        System.out.println("--- Push Operations ---");
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack after pushes: " + stack);
        // Output: [Third, Second, First]
        
        // Peek - View top without removal
        System.out.println("\n--- Peek Operation ---");
        String top = stack.peek();
        System.out.println("Top element: " + top);  // Third
        System.out.println("Stack unchanged: " + stack);
        
        // Pop - Remove and return top
        System.out.println("\n--- Pop Operations ---");
        String popped1 = stack.pop();
        System.out.println("Popped: " + popped1);   // Third
        String popped2 = stack.pop();
        System.out.println("Popped: " + popped2);   // Second
        System.out.println("Stack after pops: " + stack);  // [First]
        
        // Check if empty
        System.out.println("\n--- Status Check ---");
        System.out.println("Is empty? " + stack.isEmpty());  // false
        System.out.println("Size: " + stack.size());          // 1
        
        // Pop remaining
        stack.pop();
        System.out.println("After popping all - Is empty? " + stack.isEmpty());  // true
        
        // Handling empty stack
        try {
            stack.pop();  // Will throw exception
        } catch (Exception e) {
            System.out.println("Error: Cannot pop from empty stack!");
        }
    }
    
    public static void main(String[] args) {
        demonstrateBasicOperations();
    }
}
```

**Output:**
```
=== Basic Stack Operations ===

--- Push Operations ---
Stack after pushes: [Third, Second, First]

--- Peek Operation ---
Top element: Third
Stack unchanged: [Third, Second, First]

--- Pop Operations ---
Popped: Third
Popped: Second
Stack after pops: [First]

--- Status Check ---
Is empty? false
Size: 1
After popping all - Is empty? true
Error: Cannot pop from empty stack!
```

---

## Common Stack Applications

### 1. Balanced Parentheses Checker

**Problem:** Check if parentheses, brackets, and braces are balanced.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class BalancedParentheses {
    
    public static boolean isBalanced(String expression) {
        Deque<Character> stack = new ArrayDeque<>();
        
        for (char ch : expression.toCharArray()) {
            // Push opening brackets
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }
            // Check closing brackets
            else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;  // No matching opening bracket
                }
                
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) {
                    return false;  // Mismatched pair
                }
            }
        }
        
        // All brackets should be matched
        return stack.isEmpty();
    }
    
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "{[()]}",      // true
            "{[(])}",      // false
            "((()))",      // true
            "(()",         // false
            "{[}]",        // false
            ""             // true (empty is balanced)
        };
        
        System.out.println("=== Balanced Parentheses Checker ===\n");
        for (String expr : testCases) {
            System.out.printf("'%s' -> %s%n", expr, isBalanced(expr));
        }
    }
}
```

**Output:**
```
=== Balanced Parentheses Checker ===

'{[()]}' -> true
'{[(])}' -> false
'((()))' -> true
'(()' -> false
'{[}]' -> false
'' -> true
```

---

### 2. Reverse String Using Stack

```java
import java.util.ArrayDeque;
import java.util.Deque;

class ReverseString {
    
    public static String reverse(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        
        // Push all characters onto stack
        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }
        
        // Pop all characters to build reversed string
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }
        
        return reversed.toString();
    }
    
    public static int[] reverseArray(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        
        // Push all elements
        for (int num : arr) {
            stack.push(num);
        }
        
        // Pop to create reversed array
        int[] reversed = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = stack.pop();
        }
        
        return reversed;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Reverse Using Stack ===\n");
        
        // Reverse string
        String text = "Hello World";
        System.out.println("Original: " + text);
        System.out.println("Reversed: " + reverse(text));
        
        // Reverse array
        int[] numbers = {1, 2, 3, 4, 5};
        int[] reversedNums = reverseArray(numbers);
        System.out.print("\nOriginal array: ");
        for (int n : numbers) System.out.print(n + " ");
        System.out.print("\nReversed array: ");
        for (int n : reversedNums) System.out.print(n + " ");
    }
}
```

**Output:**
```
=== Reverse Using Stack ===

Original: Hello World
Reversed: dlroW olleH

Original array: 1 2 3 4 5 
Reversed array: 5 4 3 2 1
```

---

### 3. Evaluate Postfix Expression

**Problem:** Evaluate postfix notation (Reverse Polish Notation).  
**Example:** `"2 3 + 5 *"` means `(2 + 3) * 5 = 25`

```java
import java.util.ArrayDeque;
import java.util.Deque;

class PostfixEvaluator {
    
    public static int evaluatePostfix(String expression) {
        Deque<Integer> stack = new ArrayDeque<>();
        
        String[] tokens = expression.split(" ");
        
        for (String token : tokens) {
            // If token is a number, push it
            if (isNumeric(token)) {
                stack.push(Integer.parseInt(token));
            }
            // If token is an operator, pop two operands and apply
            else {
                int operand2 = stack.pop();  // Second operand
                int operand1 = stack.pop();  // First operand
                
                int result = applyOperator(token, operand1, operand2);
                stack.push(result);
            }
        }
        
        // Final result is the only element in stack
        return stack.pop();
    }
    
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static int applyOperator(String operator, int a, int b) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Postfix Expression Evaluator ===\n");
        
        String[] expressions = {
            "2 3 +",           // 2 + 3 = 5
            "2 3 + 5 *",       // (2 + 3) * 5 = 25
            "5 1 2 + 4 * + 3 -" // 5 + ((1 + 2) * 4) - 3 = 14
        };
        
        for (String expr : expressions) {
            int result = evaluatePostfix(expr);
            System.out.printf("'%s' = %d%n", expr, result);
        }
    }
}
```

**Output:**
```
=== Postfix Expression Evaluator ===

'2 3 +' = 5
'2 3 + 5 *' = 25
'5 1 2 + 4 * + 3 -' = 14
```

---

### 4. Function Call Stack Simulation

**Demonstrates:** How programming languages manage function calls.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class FunctionCallStack {
    
    static class CallFrame {
        String functionName;
        String localVariables;
        
        CallFrame(String name, String vars) {
            this.functionName = name;
            this.localVariables = vars;
        }
        
        @Override
        public String toString() {
            return String.format("%s(%s)", functionName, localVariables);
        }
    }
    
    // Simulated call stack
    private static Deque<CallFrame> callStack = new ArrayDeque<>();
    
    public static int factorial(int n) {
        callStack.push(new CallFrame("factorial", "n=" + n));
        printCallStack();
        
        if (n <= 1) {
            System.out.println("→ Base case reached, returning 1");
            callStack.pop();
            return 1;
        }
        
        int result = n * factorial(n - 1);
        
        System.out.println("→ Returning from factorial(" + n + ") = " + result);
        callStack.pop();
        
        return result;
    }
    
    private static void printCallStack() {
        System.out.println("Call Stack: " + callStack);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Function Call Stack Simulation ===\n");
        System.out.println("Computing factorial(4):\n");
        
        int result = factorial(4);
        
        System.out.println("\nFinal Result: " + result);
        System.out.println("Call stack is now: " + callStack + " (empty)");
    }
}
```

**Output:**
```
=== Function Call Stack Simulation ===

Computing factorial(4):

Call Stack: [factorial(n=4)]
Call Stack: [factorial(n=3), factorial(n=4)]
Call Stack: [factorial(n=2), factorial(n=3), factorial(n=4)]
Call Stack: [factorial(n=1), factorial(n=2), factorial(n=3), factorial(n=4)]
→ Base case reached, returning 1
→ Returning from factorial(2) = 2
→ Returning from factorial(3) = 6
→ Returning from factorial(4) = 24

Final Result: 24
Call stack is now: [] (empty)
```

---

### 5. Depth-First Search (DFS) Using Stack

**Problem:** Traverse a graph using iterative DFS.

```java
import java.util.*;

class DFSWithStack {
    
    static class Graph {
        private Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        
        public void addEdge(int source, int destination) {
            adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
            adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>()).add(source);
        }
        
        public void dfsIterative(int startNode) {
            Set<Integer> visited = new HashSet<>();
            Deque<Integer> stack = new ArrayDeque<>();
            
            stack.push(startNode);
            
            System.out.println("DFS Traversal starting from node " + startNode + ":");
            
            while (!stack.isEmpty()) {
                int current = stack.pop();
                
                if (visited.contains(current)) {
                    continue;
                }
                
                visited.add(current);
                System.out.print(current + " ");
                
                // Push neighbors in reverse order for consistent traversal
                List<Integer> neighbors = adjacencyList.getOrDefault(current, new ArrayList<>());
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== DFS Using Stack ===\n");
        
        Graph graph = new Graph();
        
        // Building graph:
        //     1
        //    / \
        //   2   3
        //  / \   \
        // 4   5   6
        
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        
        graph.dfsIterative(1);
    }
}
```

**Output:**
```
=== DFS Using Stack ===

DFS Traversal starting from node 1:
1 2 4 5 3 6
```

---

### 6. Browser History Navigation

**Problem:** Implement browser back/forward functionality.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class BrowserHistory {
    private Deque<String> backStack;
    private Deque<String> forwardStack;
    private String currentPage;
    
    public BrowserHistory(String homepage) {
        this.backStack = new ArrayDeque<>();
        this.forwardStack = new ArrayDeque<>();
        this.currentPage = homepage;
    }
    
    public void visit(String url) {
        // Save current page to back history
        backStack.push(currentPage);
        
        // Clear forward history (like browsers do)
        forwardStack.clear();
        
        // Navigate to new page
        currentPage = url;
        System.out.println("Visited: " + url);
    }
    
    public String back() {
        if (backStack.isEmpty()) {
            System.out.println("No pages in back history");
            return currentPage;
        }
        
        // Save current to forward history
        forwardStack.push(currentPage);
        
        // Go back
        currentPage = backStack.pop();
        System.out.println("Back to: " + currentPage);
        return currentPage;
    }
    
    public String forward() {
        if (forwardStack.isEmpty()) {
            System.out.println("No pages in forward history");
            return currentPage;
        }
        
        // Save current to back history
        backStack.push(currentPage);
        
        // Go forward
        currentPage = forwardStack.pop();
        System.out.println("Forward to: " + currentPage);
        return currentPage;
    }
    
    public String current() {
        return currentPage;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Browser History Simulation ===\n");
        
        BrowserHistory browser = new BrowserHistory("google.com");
        System.out.println("Homepage: " + browser.current() + "\n");
        
        browser.visit("github.com");
        browser.visit("stackoverflow.com");
        browser.visit("reddit.com");
        
        System.out.println("\n--- Navigation ---");
        browser.back();        // reddit.com → stackoverflow.com
        browser.back();        // stackoverflow.com → github.com
        browser.forward();     // github.com → stackoverflow.com
        browser.visit("linkedin.com");  // Clears forward history
        browser.forward();     // No forward history
        
        System.out.println("\nCurrent page: " + browser.current());
    }
}
```

**Output:**
```
=== Browser History Simulation ===

Homepage: google.com

Visited: github.com
Visited: stackoverflow.com
Visited: reddit.com

--- Navigation ---
Back to: stackoverflow.com
Back to: github.com
Forward to: stackoverflow.com
Visited: linkedin.com
No pages in forward history

Current page: linkedin.com
```

---

## Stack vs Other Data Structures

### Stack vs Queue

| Feature | Stack | Queue |
|---------|-------|-------|
| **Principle** | LIFO (Last In First Out) | FIFO (First In First Out) |
| **Operations** | push/pop from top | enqueue/dequeue from ends |
| **Use Cases** | Function calls, undo/redo | Task scheduling, BFS |
| **Access Pattern** | One end only | Both ends (front and rear) |
| **Real-World** | Stack of plates | Waiting line |

### Stack vs Array

| Feature | Stack | Array |
|---------|-------|-------|
| **Access** | Only top element | Any index (random access) |
| **Operations** | push/pop/peek | get/set by index |
| **Use Cases** | LIFO operations | General data storage |
| **Flexibility** | Restricted (by design) | Full access |

### Stack vs Heap (Data Structure)

| Feature | Stack | Heap |
|---------|-------|------|
| **Structure** | Linear (LIFO) | Tree-based (priority) |
| **Top Element** | Last added | Highest/lowest priority |
| **Operations** | O(1) push/pop | O(log n) insert/delete |
| **Use Cases** | Function calls, DFS | Priority queues, scheduling |

**Note:** Don't confuse Stack (data structure) with stack memory, or Heap (data structure) with heap memory!

---

## Best Practices

### ✅ DO:

```java
// 1. Use ArrayDeque for stack operations
Deque<String> stack = new ArrayDeque<>();

// 2. Check if empty before pop/peek
if (!stack.isEmpty()) {
    String item = stack.pop();
}

// 3. Use descriptive names
Deque<String> historyStack = new ArrayDeque<>();
Deque<Integer> operandStack = new ArrayDeque<>();

// 4. Handle exceptions properly
try {
    stack.pop();
} catch (NoSuchElementException e) {
    System.out.println("Stack is empty!");
}

// 5. Use generic types
Deque<Integer> intStack = new ArrayDeque<>();  // Type-safe
```

### ❌ DON'T:

```java
// 1. Don't use legacy Stack class
Stack<String> stack = new Stack<>();  // ❌ Avoid

// 2. Don't access by index (breaks stack abstraction)
String item = stack.get(2);  // ❌ Not stack behavior

// 3. Don't pop from empty stack without checking
String item = stack.pop();  // ❌ May throw exception

// 4. Don't use raw types
Deque stack = new ArrayDeque();  // ❌ No type safety

// 5. Don't iterate if order matters (use temp stack)
for (String item : stack) {  // ❌ Order not guaranteed
    process(item);
}
```

---

## Stack Implementation from Scratch

### Array-Based Stack:

```java
class ArrayStack<T> {
    private Object[] elements;
    private int top;
    private static final int DEFAULT_CAPACITY = 10;
    
    public ArrayStack() {
        elements = new Object[DEFAULT_CAPACITY];
        top = -1;
    }
    
    public void push(T item) {
        if (top == elements.length - 1) {
            resize();
        }
        elements[++top] = item;
    }
    
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = (T) elements[top];
        elements[top--] = null;  // Help GC
        return item;
    }
    
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return (T) elements[top];
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    public int size() {
        return top + 1;
    }
    
    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }
}
```

### LinkedList-Based Stack:

```java
class LinkedStack<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    private Node<T> top;
    private int size;
    
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int size() {
        return size;
    }
}
```

---

## Time & Space Complexity

### Operations Complexity:

| Operation | Array-Based | LinkedList-Based |
|-----------|-------------|------------------|
| **push()** | O(1) amortized* | O(1) |
| **pop()** | O(1) | O(1) |
| **peek()** | O(1) | O(1) |
| **isEmpty()** | O(1) | O(1) |
| **size()** | O(1) | O(1) |
| **Space** | O(n) | O(n) + pointer overhead |

*Amortized O(1) because occasional resizing takes O(n)

---

## Common Interview Questions

### Q1: Implement a Min Stack
**Problem:** Design a stack that supports push, pop, and retrieving minimum element in O(1).

```java
class MinStack {
    private Deque<Integer> stack;
    private Deque<Integer> minStack;
    
    public MinStack() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }
    
    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()) {
            minStack.pop();
        }
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}
```

### Q2: Stack vs Queue?
**Answer:** 
- **Stack:** LIFO (Last In First Out). Use for undo/redo, function calls, DFS, expression evaluation.
- **Queue:** FIFO (First In First Out). Use for scheduling, BFS, buffering.

### Q3: Why use ArrayDeque instead of Stack?
**Answer:**
1. **Performance:** ArrayDeque is faster (no synchronization)
2. **Design:** Stack extends Vector (legacy design flaw)
3. **Memory:** ArrayDeque is more memory efficient
4. **Modern:** Part of Collections Framework best practices

### Q4: Implement two stacks in one array?
```java
class TwoStacks {
    private int[] arr;
    private int top1, top2;
    
    public TwoStacks(int n) {
        arr = new int[n];
        top1 = -1;
        top2 = n;
    }
    
    public void push1(int x) {
        if (top1 < top2 - 1) {
            arr[++top1] = x;
        }
    }
    
    public void push2(int x) {
        if (top1 < top2 - 1) {
            arr[--top2] = x;
        }
    }
    
    public int pop1() {
        if (top1 >= 0) {
            return arr[top1--];
        }
        throw new IllegalStateException("Stack 1 empty");
    }
    
    public int pop2() {
        if (top2 < arr.length) {
            return arr[top2++];
        }
        throw new IllegalStateException("Stack 2 empty");
    }
}
```

### Q5: Check for duplicate parentheses in expression?
```java
public static boolean hasDuplicateParentheses(String expr) {
    Deque<Character> stack = new ArrayDeque<>();
    
    for (char ch : expr.toCharArray()) {
        if (ch == ')') {
            int count = 0;
            while (stack.peek() != '(') {
                stack.pop();
                count++;
            }
            stack.pop();  // Remove '('
            
            if (count <= 1) {
                return true;  // Duplicate found like ((a))
            }
        } else {
            stack.push(ch);
        }
    }
    return false;
}
```

### Q6: Convert infix to postfix using stack?
**Example:** `A + B * C` → `A B C * +`

```java
public static String infixToPostfix(String infix) {
    Deque<Character> stack = new ArrayDeque<>();
    StringBuilder postfix = new StringBuilder();
    
    for (char ch : infix.toCharArray()) {
        if (Character.isLetterOrDigit(ch)) {
            postfix.append(ch);
        } else if (ch == '(') {
            stack.push(ch);
        } else if (ch == ')') {
            while (!stack.isEmpty() && stack.peek() != '(') {
                postfix.append(stack.pop());
            }
            stack.pop();  // Remove '('
        } else {  // Operator
            while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                postfix.append(stack.pop());
            }
            stack.push(ch);
        }
    }
    
    while (!stack.isEmpty()) {
        postfix.append(stack.pop());
    }
    
    return postfix.toString();
}

private static int precedence(char op) {
    switch (op) {
        case '+':
        case '-': return 1;
        case '*':
        case '/': return 2;
        case '^': return 3;
    }
    return -1;
}
```

---

## Real-World Applications

### 1. **Program Execution**
- Function call stack (recursive calls)
- Stack frames for local variables
- Return address management

### 2. **Text Editors**
- Undo/Redo functionality
- Bracket matching
- Syntax checking

### 3. **Web Browsers**
- Back/Forward navigation
- Tab history
- JavaScript call stack

### 4. **Compilers**
- Expression evaluation
- Syntax parsing
- Code optimization

### 5. **Operating Systems**
- Process/thread management
- Memory management (stack memory)
- Interrupt handling

### 6. **Algorithms**
- Depth-First Search (DFS)
- Backtracking problems
- Tree traversals (iterative)

---

## Performance Comparison

### ArrayDeque vs LinkedList as Stack:

```java
// Benchmark results (1 million operations):
// ArrayDeque: ~50ms
// LinkedList: ~150ms
// Legacy Stack: ~200ms (synchronized overhead)

Deque<Integer> arrayStack = new ArrayDeque<>();      // ✅ Fastest
Deque<Integer> linkedStack = new LinkedList<>();     // Slower
Stack<Integer> legacyStack = new Stack<>();          // ❌ Slowest
```

**Recommendation:** Use `ArrayDeque` for best performance!

---

## Summary - Key Takeaways

| Aspect | Details |
|--------|---------|
| **Principle** | LIFO (Last In, First Out) |
| **Core Operations** | push(), pop(), peek() |
| **Time Complexity** | O(1) for all operations |
| **Best Implementation** | `Deque<E> stack = new ArrayDeque<>()` |
| **Avoid** | Legacy `Stack` class |
| **Common Uses** | Function calls, DFS, parentheses matching, undo/redo |
| **When to Use** | Need LIFO access pattern, backtracking, recursive problems |

---

## Next Steps

Explore related data structures:
1. **[Queues](../04-Queues/)** - FIFO data structure
2. **[Deque](../06-Deque/)** - Double-ended queue
3. **[Priority Queue](../07-Priority-Queue/)** - Heap-based priority queue
4. **[Trees](../08-Trees/)** - Hierarchical data structures

Practice problems:
- LeetCode: Valid Parentheses, Min Stack, Evaluate RPN
- Practice implementing: Calculator, Expression converter, Tower of Hanoi

---

## Additional Resources

- **Oracle Java Docs:** [Deque Interface](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html)
- **Visualizations:** [VisuAlgo Stack](https://visualgo.net/en/list)
- **Practice:** LeetCode Stack Tag

---

**Remember:** In modern Java, always use `ArrayDeque` for stack operations, not the legacy `Stack` class! 🎯
