# Fast-Slow Pointers Pattern - Complete Guide

## Pattern Overview

The Fast-Slow Pointers (also called Tortoise and Hare) pattern uses two pointers moving at different speeds through a data structure. Typically, the fast pointer moves two steps while the slow pointer moves one step. This elegant technique is particularly powerful for detecting cycles, finding middle elements, and solving problems related to linked list manipulation without requiring extra space.

## When to Use the Fast-Slow Pointers Pattern

### Recognition Signs
- **Cycle Detection**: Finding loops in linked lists or sequences
- **Middle Element**: Locating middle node in linked lists
- **Linked List Problems**: Most linked list manipulation tasks
- **Circular Array Detection**: Identifying circular patterns
- **Palindrome Verification**: Checking if linked lists are palindromes
- **Remove Duplicates**: Eliminating duplicate nodes from lists
- **Reordering Problems**: Rearranging list nodes in specific patterns
- **Phase Detection**: Two-phase algorithms (detect, then solve)

### Advantages
- **Constant Space**: O(1) extra space complexity
- **Single Pass**: Often solves problem without multiple traversals
- **Elegant Logic**: Simple and elegant implementation
- **In-Place Operations**: Modifies data structure directly
- **No Extra Storage**: Perfect for linked list problems

## Step-by-Step Approach

1. **Initialize Pointers**: Set both pointers at starting position (usually head)
2. **Define Speed**: Slow moves 1 step, fast moves 2 steps
3. **Movement Logic**: Move pointers according to defined speeds
4. **Detection Condition**: Identify when pointers meet or fast reaches end
5. **Phase Analysis**: Determine result of movement (cycle found, middle reached, etc.)
6. **Phase Two (if needed)**: Use findings from first phase for final solution
7. **Return Result**: Extract and return required information

## Java Implementations

### 1. Detect Cycle in Linked List
```java
public class CycleDetection {
    /**
     * Node definition for linked list
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Detect if linked list has a cycle
     * Time: O(n), Space: O(1)
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Move until they meet (cycle) or fast reaches end (no cycle)
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
            
            if (slow == fast) {
                return true;  // Cycle detected
            }
        }
        
        return false;  // No cycle
    }
    
    /**
     * Find the starting node of the cycle
     * Phase 1: Detect cycle and find meeting point
     * Phase 2: Find cycle start
     */
    public static ListNode detectCycleStart(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Phase 1: Detect cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                // Cycle detected, proceed to phase 2
                break;
            }
        }
        
        // If no cycle found
        if (fast == null || fast.next == null) {
            return null;
        }
        
        // Phase 2: Find cycle start
        // Reset slow to head, keep fast at meeting point
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;  // This is cycle start
    }
    
    /**
     * Find cycle length
     */
    public static int cycleLength(ListNode head) {
        if (head == null || head.next == null) {
            return 0;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Find meeting point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                // Count cycle length
                int length = 1;
                ListNode current = slow.next;
                while (current != slow) {
                    length++;
                    current = current.next;
                }
                return length;
            }
        }
        
        return 0;
    }
}
```

### 2. Find Middle of Linked List
```java
public class FindMiddleNode {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Find middle node of linked list
     * For even length, return first of two middle nodes
     * Time: O(n), Space: O(1)
     */
    public static ListNode findMiddle(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Move until fast reaches end
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;  // Slow pointer is at middle
    }
    
    /**
     * Find middle and return second middle for even length lists
     */
    public static ListNode findMiddleSecond(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head.next;  // Start fast one step ahead
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
}
```

### 3. Palindrome Linked List
```java
public class PalindromeLinkedList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Check if linked list is palindrome
     * Time: O(n), Space: O(1)
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // Step 1: Find middle using fast-slow pointers
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Step 2: Reverse second half
        ListNode firstHalf = head;
        ListNode secondHalf = reverseList(slow);
        
        // Step 3: Compare both halves
        ListNode reversed = secondHalf;
        while (reversed != null) {  // Second half is shorter for odd length
            if (firstHalf.val != reversed.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            reversed = reversed.next;
        }
        
        return true;
    }
    
    /**
     * Reverse a linked list
     */
    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        
        return prev;
    }
}
```

### 4. Reorder Linked List
```java
public class ReorderList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Reorder list: L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 ...
     * Time: O(n), Space: O(1)
     */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        
        // Step 1: Find middle
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Step 2: Reverse second half
        ListNode secondHalf = reverseList(slow);
        
        // Step 3: Merge first and second half
        ListNode firstHalf = head;
        while (secondHalf.next != null) {  // Second half is shorter
            ListNode temp1 = firstHalf.next;
            ListNode temp2 = secondHalf.next;
            
            firstHalf.next = secondHalf;
            secondHalf.next = temp1;
            
            firstHalf = temp1;
            secondHalf = temp2;
        }
    }
    
    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        
        return prev;
    }
}
```

### 5. Remove Nth Node From End
```java
public class RemoveNthNode {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Remove nth node from end of list
     * Time: O(n), Space: O(1)
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        
        // Create dummy node to handle edge case of removing head
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // Move fast pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            if (fast == null) {
                return head;  // n is greater than list length
            }
            fast = fast.next;
        }
        
        // Move both pointers until fast reaches end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // Remove the node
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    /**
     * Find nth node from end (without removing)
     */
    public static ListNode findNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Move fast n steps ahead
        for (int i = 0; i < n; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }
        
        // Move both until fast reaches end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
}
```

### 6. Circular Array Detection
```java
public class CircularArrayDetection {
    /**
     * Determine if array is circular based on movement pattern
     * Time: O(n), Space: O(1)
     */
    public static boolean circularArrayLoop(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            int slow = i;
            int fast = i;
            
            // Check if both pointers can move in same direction
            do {
                slow = getNextIndex(slow, nums);
                fast = getNextIndex(getNextIndex(fast, nums), nums);
            } while (nums[slow] * nums[fast] > 0 && slow != fast);
            
            // If they meet, we found a cycle
            if (slow == fast && nums[slow] * nums[getNextIndex(slow, nums)] > 0) {
                return true;
            }
        }
        
        return false;
    }
    
    private static int getNextIndex(int currentIndex, int[] nums) {
        int n = nums.length;
        int nextIndex = (currentIndex + nums[currentIndex]) % n;
        
        // Handle negative modulo
        if (nextIndex < 0) {
            nextIndex += n;
        }
        
        return nextIndex;
    }
}
```

### 7. Happy Number Detection
```java
public class HappyNumber {
    /**
     * Detect if number reaches 1 or enters cycle
     * Time: O(log n), Space: O(1)
     */
    public static boolean isHappyNumber(int n) {
        int slow = n;
        int fast = n;
        
        do {
            slow = sumOfSquares(slow);           // Move 1 step
            fast = sumOfSquares(sumOfSquares(fast)); // Move 2 steps
        } while (slow != fast);
        
        return slow == 1;
    }
    
    private static int sumOfSquares(int num) {
        int sum = 0;
        while (num > 0) {
            int digit = num % 10;
            sum += digit * digit;
            num /= 10;
        }
        return sum;
    }
    
    /**
     * Find the cycle that causes unhappy number
     */
    public static int[] findUnhappyCycle(int n) {
        int slow = n;
        int fast = n;
        
        // Find meeting point
        do {
            slow = sumOfSquares(slow);
            fast = sumOfSquares(sumOfSquares(fast));
        } while (slow != fast);
        
        // Find cycle start and collect cycle elements
        slow = n;
        while (slow != fast) {
            slow = sumOfSquares(slow);
            fast = sumOfSquares(fast);
        }
        
        // Collect all elements in cycle
        List<Integer> cycle = new ArrayList<>();
        int current = slow;
        do {
            cycle.add(current);
            current = sumOfSquares(current);
        } while (current != slow);
        
        return cycle.stream().mapToInt(Integer::intValue).toArray();
    }
}
```

## Problem Categories

1. **Cycle Detection**: Linked list cycles, circular arrays, unhappy numbers
2. **Middle Finding**: Middle node, removing middle, reordering from middle
3. **Palindrome Problems**: Palindrome linked lists, sequences
4. **List Manipulation**: Removing nodes, reordering, reversing parts
5. **Pattern Detection**: Repeating cycles, circular patterns
6. **Distance Measurement**: Nth node from end, distance between nodes

## Complexity Analysis

| Problem | Time Complexity | Space Complexity | Notes |
|---------|-----------------|------------------|-------|
| Cycle Detection | O(n) | O(1) | Linear scan with two pointers |
| Find Cycle Start | O(n) | O(1) | Two phases of traversal |
| Find Middle | O(n) | O(1) | Single pass to middle |
| Palindrome List | O(n) | O(1) | Find middle + reverse + compare |
| Remove Nth Node | O(n) | O(1) | Two pointer gap approach |
| Reorder List | O(n) | O(1) | Find middle + reverse + merge |

## Real-World Applications

1. **Memory Management**: Garbage collection cycle detection in objects
2. **Traffic Control**: Detecting circular routes in navigation systems
3. **Blockchain**: Cycle detection in transaction chains
4. **Game Development**: Detecting circular dependencies in game systems
5. **Operating Systems**: Process deadlock detection
6. **Network Routing**: Identifying routing loops
7. **Data Validation**: Detecting circular references in data structures

## Best Practices

1. Always initialize both pointers at the same or strategic starting position
2. Use dummy nodes when removing head node is possible
3. Define movement speeds clearly before implementation
4. Handle edge cases (empty list, single node, two nodes)
5. Use two-phase approach when finding and using information from pointers
6. Be careful with null pointer dereferences in fast pointer movement
7. Consider ListNode equality vs value equality

## Advanced Techniques

1. **Two-Phase Approach**: Detect information in first pass, use in second
2. **Dummy Nodes**: Simplify edge case handling for head removal
3. **Speed Variation**: Can use speeds other than 1 and 2
4. **Direction Control**: Useful for circular array problems
5. **Pointer Recording**: Save pointer state for later comparison

## Related Patterns

- **Two Pointers**: Similar technique but different movement strategy
- **Sliding Window**: Adjacent movement rather than fast-slow speeds
- **BFS/DFS**: Alternative approaches to cycle detection
- **Hash Set**: Memory-intensive alternative to fast-slow approach
