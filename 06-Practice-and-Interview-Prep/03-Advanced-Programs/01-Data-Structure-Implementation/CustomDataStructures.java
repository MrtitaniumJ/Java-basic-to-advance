/**
 * Custom Data Structures Implementation
 * 
 * Demonstrates the implementation of fundamental data structures:
 * - Stack (LIFO)
 * - Queue (FIFO)
 * - Singly LinkedList
 * 
 * This program shows how these structures work internally and helps understand
 * the collections framework better.
 */

public class CustomDataStructures {
    
    // ==================== CUSTOM STACK IMPLEMENTATION ====================
    /**
     * Generic Stack implementation using array
     * Time Complexity: O(1) for push/pop/peek
     * Space Complexity: O(n) where n is capacity
     */
    static class Stack<T> {
        private static final int DEFAULT_CAPACITY = 10;
        private T[] items;
        private int top;
        
        @SuppressWarnings("unchecked")
        public Stack() {
            items = new Object[DEFAULT_CAPACITY];
            top = -1;
        }
        
        public void push(T value) {
            if (top == items.length - 1) {
                resize();
            }
            items[++top] = value;
        }
        
        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack underflow");
            }
            T value = items[top];
            items[top--] = null; // Help garbage collection
            return value;
        }
        
        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack is empty");
            }
            return items[top];
        }
        
        public boolean isEmpty() {
            return top == -1;
        }
        
        public int size() {
            return top + 1;
        }
        
        @SuppressWarnings("unchecked")
        private void resize() {
            T[] newItems = new Object[items.length * 2];
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems;
        }
    }
    
    // ==================== CUSTOM QUEUE IMPLEMENTATION ====================
    /**
     * Generic Queue implementation using circular array
     * Time Complexity: O(1) for enqueue/dequeue
     * Space Complexity: O(n) where n is capacity
     */
    static class Queue<T> {
        private static final int DEFAULT_CAPACITY = 10;
        private T[] items;
        private int front;
        private int rear;
        private int size;
        
        @SuppressWarnings("unchecked")
        public Queue() {
            items = new Object[DEFAULT_CAPACITY];
            front = 0;
            rear = -1;
            size = 0;
        }
        
        public void enqueue(T value) {
            if (size == items.length) {
                resize();
            }
            rear = (rear + 1) % items.length;
            items[rear] = value;
            size++;
        }
        
        public T dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue underflow");
            }
            T value = items[front];
            items[front] = null;
            front = (front + 1) % items.length;
            size--;
            return value;
        }
        
        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            return items[front];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int size() {
            return size;
        }
        
        @SuppressWarnings("unchecked")
        private void resize() {
            T[] newItems = new Object[items.length * 2];
            for (int i = 0; i < size; i++) {
                newItems[i] = items[(front + i) % items.length];
            }
            items = newItems;
            front = 0;
            rear = size - 1;
        }
    }
    
    // ==================== CUSTOM LINKEDLIST IMPLEMENTATION ====================
    /**
     * Singly LinkedList implementation
     * Time Complexity: O(n) for access, O(1) for insert/delete at known position
     * Space Complexity: O(n)
     */
    static class LinkedList<T> {
        private Node<T> head;
        private int size;
        
        private static class Node<T> {
            T data;
            Node<T> next;
            
            Node(T data) {
                this.data = data;
            }
        }
        
        public void add(T value) {
            Node<T> newNode = new Node<>(value);
            if (head == null) {
                head = newNode;
            } else {
                Node<T> current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }
        
        public void addAtIndex(int index, T value) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Invalid index");
            }
            
            Node<T> newNode = new Node<>(value);
            
            if (index == 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node<T> current = getNode(index - 1);
                newNode.next = current.next;
                current.next = newNode;
            }
            size++;
        }
        
        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Invalid index");
            }
            return getNode(index).data;
        }
        
        public T remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Invalid index");
            }
            
            T data;
            
            if (index == 0) {
                data = head.data;
                head = head.next;
            } else {
                Node<T> current = getNode(index - 1);
                data = current.next.data;
                current.next = current.next.next;
            }
            
            size--;
            return data;
        }
        
        public boolean contains(T value) {
            Node<T> current = head;
            while (current != null) {
                if (current.data.equals(value)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        
        public int size() {
            return size;
        }
        
        public void display() {
            Node<T> current = head;
            System.out.print("LinkedList: ");
            while (current != null) {
                System.out.print(current.data + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
        
        private Node<T> getNode(int index) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }
    
    // ==================== DEMO AND TESTING ====================
    
    public static void demonstrateStack() {
        System.out.println("\n========== STACK DEMONSTRATION ==========");
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("Pushing: 10, 20, 30, 40, 50");
        for (int i = 10; i <= 50; i += 10) {
            stack.push(i);
        }
        
        System.out.println("Stack size: " + stack.size());
        System.out.println("Peek: " + stack.peek());
        
        System.out.println("\nPopping elements:");
        while (!stack.isEmpty()) {
            System.out.println("Popped: " + stack.pop());
        }
        
        System.out.println("Stack empty: " + stack.isEmpty());
    }
    
    public static void demonstrateQueue() {
        System.out.println("\n========== QUEUE DEMONSTRATION ==========");
        Queue<String> queue = new Queue<>();
        
        System.out.println("Enqueueing: Alice, Bob, Charlie, David, Eve");
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve"};
        for (String name : names) {
            queue.enqueue(name);
        }
        
        System.out.println("Queue size: " + queue.size());
        System.out.println("Peek: " + queue.peek());
        
        System.out.println("\nDequeueing elements:");
        while (!queue.isEmpty()) {
            System.out.println("Dequeued: " + queue.dequeue());
        }
        
        System.out.println("Queue empty: " + queue.isEmpty());
    }
    
    public static void demonstrateLinkedList() {
        System.out.println("\n========== LINKEDLIST DEMONSTRATION ==========");
        LinkedList<Integer> list = new LinkedList<>();
        
        System.out.println("Adding: 100, 200, 300, 400");
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(400);
        list.display();
        
        System.out.println("\nInserting 150 at index 1");
        list.addAtIndex(1, 150);
        list.display();
        
        System.out.println("\nAccessing element at index 2: " + list.get(2));
        System.out.println("List size: " + list.size());
        
        System.out.println("\nRemoving element at index 2");
        int removed = list.remove(2);
        System.out.println("Removed: " + removed);
        list.display();
        
        System.out.println("\nChecking if list contains 300: " + list.contains(300));
        System.out.println("Checking if list contains 999: " + list.contains(999));
    }
    
    public static void demonstrateRealWorldUsage() {
        System.out.println("\n========== REAL-WORLD USAGE: BALANCED PARENTHESES ==========");
        String expression = "({[()]})";
        System.out.println("Expression: " + expression);
        System.out.println("Is balanced: " + isBalanced(expression));
        
        System.out.println("\nExpression: (({[}])");
        System.out.println("Is balanced: " + isBalanced("(({[})"));
    }
    
    // Real-world application: Check balanced parentheses
    public static boolean isBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : expression.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
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
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        CUSTOM DATA STRUCTURES IMPLEMENTATION          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        demonstrateStack();
        demonstrateQueue();
        demonstrateLinkedList();
        demonstrateRealWorldUsage();
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("All demonstrations completed successfully!");
        System.out.println("=".repeat(55));
    }
}
