# DSA Patterns: Essential Problem-Solving Strategies

## Overview

DSA Patterns are reusable problem-solving techniques that help you identify and solve common algorithmic challenges efficiently. Rather than solving each problem from scratch, recognizing patterns allows you to apply proven approaches.

---

## Table of Contents

1. [Two Pointers](#two-pointers)
2. [Sliding Window](#sliding-window)
3. [Fast-Slow Pointers](#fast-slow-pointers)
4. [Merge Intervals](#merge-intervals)
5. [Cyclic Sort](#cyclic-sort)

---

## Two Pointers

### Simple Explanation

The Two Pointers pattern uses two references (pointers) that traverse a data structure, typically starting from different positions (beginning and end, or two different starting points). They move toward each other based on specific conditions until they meet or one reaches the end.

### When to Recognize and Apply

**Red Flags to Identify:**
- Problem involves a **sorted array or linked list**
- Looking for a **pair of elements** with specific properties
- Need to remove/modify elements in-place
- Comparing elements from **opposite ends** of a structure
- Problem asks for **"pair", "triple", "sum to target"** scenarios

**Best For:**
- Finding pairs with target sum
- Removing duplicates from sorted arrays
- Container with most water
- Reversing arrays/strings
- Partition problems

### Step-by-Step Approach

1. **Identify the problem structure** - Is it sorted? Do we need pairs?
2. **Initialize pointers** - Typically at start and end, or two different starting points
3. **Move pointers intelligently** - Based on comparison, move to narrow down the search space
4. **Process valid conditions** - When a valid pair is found, process it
5. **Continue until convergence** - When pointers meet or conditions are satisfied

### Code Example: Two Sum in Sorted Array

```java
/**
 * Find two numbers that add up to target in a sorted array
 * Time: O(n), Space: O(1)
 */
public class TwoPointers {
    public int[] twoSum(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int sum = arr[left] + arr[right];
            
            if (sum == target) {
                return new int[]{arr[left], arr[right]};
            } else if (sum < target) {
                left++;  // Need larger sum
            } else {
                right--;  // Need smaller sum
            }
        }
        
        return new int[]{-1, -1};  // Not found
    }
    
    // Example: Reverse an array in-place
    public void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            // Swap
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            
            left++;
            right--;
        }
    }
    
    // Example: Remove duplicates from sorted array
    public int removeDuplicates(int[] arr) {
        if (arr.length == 0) return 0;
        
        int left = 0;  // Position to place unique elements
        
        for (int right = 1; right < arr.length; right++) {
            if (arr[right] != arr[left]) {
                left++;
                arr[left] = arr[right];
            }
        }
        
        return left + 1;  // Length of unique elements
    }
    
    public static void main(String[] args) {
        TwoPointers tp = new TwoPointers();
        
        // Test two sum
        int[] arr = {2, 7, 11, 15};
        int[] result = tp.twoSum(arr, 9);
        System.out.println("Two sum result: " + result[0] + ", " + result[1]);  // Output: 2, 7
        
        // Test remove duplicates
        int[] arr2 = {1, 1, 2, 2, 3};
        int length = tp.removeDuplicates(arr2);
        System.out.println("Unique elements count: " + length);  // Output: 3
    }
}
```

### Problem Categories

| Category | Examples |
|----------|----------|
| **Pair Finding** | Two Sum, Pair with Target, 3Sum |
| **Palindromes** | Valid Palindrome, Palindrome II |
| **Sorting/Partitioning** | Sort Colors, Dutch Flag Problem |
| **Array Manipulation** | Remove Duplicates, Reverse, Move Zeros |
| **Linked Lists** | Palindrome List, Reverse List |

### Interview Question Examples

1. **Two Sum II** - Find two numbers in sorted array that add to target
2. **Container With Most Water** - Find two lines that form the largest container
3. **3Sum** - Find all triplets that sum to zero
4. **Trapping Rain Water** - Calculate water trapped between bars
5. **Valid Palindrome** - Check if string is palindrome considering only alphanumeric

---

## Sliding Window

### Simple Explanation

The Sliding Window pattern maintains a window of elements and slides it across a data structure. The window expands to include new elements and contracts when the condition is violated or we've found the solution.

### When to Recognize and Apply

**Red Flags to Identify:**
- Problem involves **contiguous elements** (substring, subarray)
- Looking for **"minimum/maximum length"** or **"best window"**
- Finding patterns within a **continuous range**
- Problem mentions **"substring", "subarray", "longest", "shortest"**
- Variables that can be **tracked incrementally**

**Best For:**
- Finding substrings with specific properties
- Finding subarrays with target sum
- Maximum window problems
- Pattern matching in sequences

### Step-by-Step Approach

1. **Create window boundaries** - Initialize left and right pointers
2. **Define window state** - Use a data structure (set, map, counter) to track window contents
3. **Expand window** - Move right pointer to include new elements
4. **Contract window** - Move left pointer to shrink window when condition violated
5. **Update result** - Track the best window found
6. **Continue until end** - When right pointer reaches the end

### Code Example: Longest Substring Without Repeating Characters

```java
import java.util.*;

/**
 * Find the longest substring without repeating characters
 * Time: O(n), Space: O(min(n, charset))
 */
public class SlidingWindow {
    
    // Example 1: Longest substring without repeating characters
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Map<Character, Integer> charIndex = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            
            // If character is already in window, move left pointer
            if (charIndex.containsKey(rightChar)) {
                left = Math.max(left, charIndex.get(rightChar) + 1);
            }
            
            // Update character position
            charIndex.put(rightChar, right);
            
            // Update max length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    // Example 2: Maximum sum of subarray with fixed window size k
    public int maxSumSubarray(int[] arr, int k) {
        if (arr.length < k) return -1;
        
        int currentSum = 0;
        // Calculate sum of first window
        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }
        
        int maxSum = currentSum;
        
        // Slide the window
        for (int i = k; i < arr.length; i++) {
            // Remove leftmost element and add new rightmost element
            currentSum = currentSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    // Example 3: Minimum window substring containing all characters
    public String minWindowSubstring(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Count required characters
        Map<Character, Integer> required = new HashMap<>();
        for (char c : t.toCharArray()) {
            required.put(c, required.getOrDefault(c, 0) + 1);
        }
        
        // Window state
        Map<Character, Integer> windowCount = new HashMap<>();
        int formed = 0;  // How many unique characters in window have desired frequency
        int required_size = required.size();
        
        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int minStart = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowCount.put(rightChar, windowCount.getOrDefault(rightChar, 0) + 1);
            
            // If frequency matches, increment formed count
            if (required.containsKey(rightChar) && 
                windowCount.get(rightChar).equals(required.get(rightChar))) {
                formed++;
            }
            
            // Contract window while it contains all characters
            while (left <= right && formed == required_size) {
                char leftChar = s.charAt(left);
                
                // Update result if this window is smaller
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    minStart = left;
                }
                
                // Remove leftmost character
                windowCount.put(leftChar, windowCount.get(leftChar) - 1);
                if (required.containsKey(leftChar) && 
                    windowCount.get(leftChar) < required.get(leftChar)) {
                    formed--;
                }
                
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }
    
    public static void main(String[] args) {
        SlidingWindow sw = new SlidingWindow();
        
        // Test longest substring without repeating
        String result1 = "abcabcbb";
        System.out.println("Longest substring length: " + sw.lengthOfLongestSubstring(result1));  // Output: 3
        
        // Test max sum subarray
        int[] arr = {1, 4, 2, 10, 2, 3, 1, 0, 20};
        System.out.println("Max sum subarray (k=4): " + sw.maxSumSubarray(arr, 4));  // Output: 24
        
        // Test minimum window substring
        String minWindow = sw.minWindowSubstring("ADOBECODEBANC", "ABC");
        System.out.println("Minimum window: " + minWindow);  // Output: BANC
    }
}
```

### Problem Categories

| Category | Examples |
|----------|----------|
| **Substring Problems** | Longest Substring Without Repeat, Min Window Substring |
| **Subarray Sum** | Max Sum Subarray, Subarray Sum Equals K |
| **Frequency Counting** | Permutation in String, Anagrams |
| **Fixed Window** | Maximum Average, Maximize Radius |
| **Variable Window** | Min Window, Longest Palindrome |

### Interview Question Examples

1. **Longest Substring Without Repeating Characters** - Find longest substring with unique characters
2. **Minimum Window Substring** - Find smallest substring containing all target characters
3. **Permutation in String** - Check if s1 permutation exists in s2
4. **Longest Repeating Character Replacement** - Maximize repeating character by replacing k characters
5. **Subarray Sum Equals K** - Find all subarrays with sum equal to k

---

## Fast-Slow Pointers

### Simple Explanation

The Fast-Slow Pointers pattern uses two pointers moving at different speeds through a data structure. The fast pointer moves 2 steps while the slow pointer moves 1 step. This creates a relative motion useful for detecting cycles, finding midpoints, and rearranging linked lists.

### When to Recognize and Apply

**Red Flags to Identify:**
- Working with **linked lists** (especially when you can't access by index)
- Problem asks about **"cycle detection"** or **"circular"**
- Need to find the **"middle" of linked list**
- Looking for **"kth element from end"**
- Problem involves **"rearrangement"** in a single pass
- Detecting whether movement will **"meet"** or **"loop"**

**Best For:**
- Cycle detection in linked lists
- Finding middle of linked list
- Finding kth element from end
- Palindrome checking in linked lists
- Reordering linked lists
- Detecting circular arrays

### Step-by-Step Approach

1. **Initialize pointers** - Both start at beginning (or specific positions)
2. **Define movement speeds** - Slow moves 1 step, fast moves 2 steps
3. **Check stopping condition** - Usually when fast reaches end or pointers meet
4. **Process at meeting point** - When pointers meet, perform required operation
5. **Handle cycles** - Fast pointer reaching null indicates no cycle; if they meet, cycle exists

### Code Example: Cycle Detection and Middle Finding

```java
/**
 * Fast-Slow Pointers for Linked List operations
 */
public class FastSlowPointers {
    
    // Definition for singly-linked list node
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }
    
    // Example 1: Detect cycle in linked list
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
            
            if (slow == fast) {
                return true;  // Cycle detected
            }
        }
        
        return false;  // No cycle
    }
    
    // Example 2: Find middle of linked list
    public ListNode findMiddle(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // When fast reaches end, slow will be at middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    // Example 3: Find kth element from end
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Create dummy node to handle edge case (removing head)
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // Move fast pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            if (fast == null) return head;
            fast = fast.next;
        }
        
        // Move both pointers until fast reaches end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // Remove the nth node
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    // Example 4: Check if linked list is palindrome
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // Find middle of linked list
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse second half
        ListNode secondHalf = reverseList(slow);
        
        // Compare first half and reversed second half
        ListNode first = head;
        ListNode second = secondHalf;
        
        while (second != null) {  // second half might be shorter
            if (first.val != second.val) {
                return false;
            }
            first = first.next;
            second = second.next;
        }
        
        return true;
    }
    
    // Helper: Reverse a linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
    
    // Example 5: Reorder linked list (1->2->3->4 becomes 1->4->2->3)
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        
        // Find middle
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Split list into two halves
        if (prev != null) {
            prev.next = null;
        }
        
        // Reverse second half
        ListNode secondHalf = reverseList(slow);
        
        // Merge two halves
        ListNode first = head;
        ListNode second = secondHalf;
        
        while (second != null) {
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;
            
            first.next = second;
            second.next = temp1;
            
            first = temp1;
            second = temp2;
        }
    }
    
    // Helper: Print linked list
    private void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    public static void main(String[] args) {
        // Test cycle detection
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = head.next;  // Create cycle
        
        FastSlowPointers fsp = new FastSlowPointers();
        System.out.println("Has cycle: " + fsp.hasCycle(head));  // Output: true
        
        // Test find middle
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(3);
        head2.next.next.next = new ListNode(4);
        
        ListNode middle = fsp.findMiddle(head2);
        System.out.println("Middle element: " + middle.val);  // Output: 3
        
        // Test palindrome
        ListNode head3 = new ListNode(1);
        head3.next = new ListNode(2);
        head3.next.next = new ListNode(2);
        head3.next.next.next = new ListNode(1);
        
        System.out.println("Is palindrome: " + fsp.isPalindrome(head3));  // Output: true
    }
}
```

### Problem Categories

| Category | Examples |
|----------|----------|
| **Cycle Detection** | Cycle in List, Find Cycle Start, Circular Array |
| **Midpoint Finding** | Middle of List, Split List |
| **Distance/Gap** | Kth Element from End, Nth Node Removal |
| **Reordering** | Reorder List, Rearrange to Alternate |
| **Palindrome** | Palindrome List Detection |

### Interview Question Examples

1. **Linked List Cycle** - Detect if linked list has a cycle
2. **Linked List Cycle II** - Find the node where cycle begins
3. **Middle of the Linked List** - Find middle element
4. **Palindrome Linked List** - Check if linked list is palindrome
5. **Reorder List** - Reorder list as first, last, second, second-last pattern

---

## Merge Intervals

### Simple Explanation

The Merge Intervals pattern identifies overlapping intervals and merges them into non-overlapping ones. It involves sorting intervals and intelligently combining those that overlap based on their start and end points.

### When to Recognize and Apply

**Red Flags to Identify:**
- Problem involves **intervals, ranges, or segments**
- Looking for **"overlapping"** regions or conflicts
- Need to **"merge", "combine", or "consolidate"** ranges
- Problem asks about **"intersection", "union"** of intervals
- Calendar scheduling or meeting room problems
- Insert new interval and merge

**Best For:**
- Merging overlapping intervals
- Inserting and merging intervals
- Meeting room scheduling
- Employee work schedule
- Screen coordinate problems

### Step-by-Step Approach

1. **Sort intervals** - By start time (or position)
2. **Initialize result** - With first interval
3. **Iterate through remaining intervals** - Compare with last interval in result
4. **Check overlap condition** - If current start <= last end, they overlap
5. **Merge if overlapping** - Extend last interval's end to max of both ends
6. **Add if not overlapping** - Add current interval as new entry

### Code Example: Merge and Insert Intervals

```java
import java.util.*;

/**
 * Merge Intervals Pattern
 */
public class MergeIntervals {
    
    // Definition for interval
    static class Interval {
        int start;
        int end;
        
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }
    
    // Example 1: Merge overlapping intervals
    public List<Interval> mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new ArrayList<>();
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<Interval> merged = new ArrayList<>();
        Interval current = new Interval(intervals[0][0], intervals[0][1]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            
            if (interval[0] <= current.end) {
                // Overlapping - merge by extending end
                current.end = Math.max(current.end, interval[1]);
            } else {
                // Non-overlapping - add current and start new
                merged.add(current);
                current = new Interval(interval[0], interval[1]);
            }
        }
        
        // Don't forget to add the last interval
        merged.add(current);
        
        return merged;
    }
    
    // Example 2: Insert interval and merge
    public List<Interval> insertInterval(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<>();
        
        if (intervals == null || intervals.isEmpty()) {
            result.add(newInterval);
            return result;
        }
        
        int i = 0;
        
        // Add all intervals that end before new interval starts
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            result.add(intervals.get(i));
            i++;
        }
        
        // Merge all overlapping intervals
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);
            newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
            i++;
        }
        
        result.add(newInterval);
        
        // Add remaining intervals
        while (i < intervals.size()) {
            result.add(intervals.get(i));
            i++;
        }
        
        return result;
    }
    
    // Example 3: Find overlapping intervals (intersection)
    public List<Interval> findOverlappingIntervals(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return new ArrayList<>();
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<Interval> intersections = new ArrayList<>();
        int maxStart = intervals[0][0];
        int minEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            int currentStart = intervals[i][0];
            int currentEnd = intervals[i][1];
            
            // Update the overlapping region
            maxStart = Math.max(maxStart, currentStart);
            minEnd = Math.min(minEnd, currentEnd);
            
            // If there's a valid overlap
            if (maxStart <= minEnd) {
                intersections.add(new Interval(maxStart, minEnd));
            }
            
            // Reset for next comparison
            maxStart = currentStart;
            minEnd = currentEnd;
        }
        
        return intersections;
    }
    
    // Example 4: Meeting rooms - minimum meeting rooms needed
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Separate start and end times
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        int roomsNeeded = 0;
        int maxRooms = 0;
        int startIdx = 0;
        int endIdx = 0;
        
        // Sweep line algorithm
        while (startIdx < starts.length) {
            if (starts[startIdx] < ends[endIdx]) {
                // Meeting starts before another ends - need new room
                roomsNeeded++;
                maxRooms = Math.max(maxRooms, roomsNeeded);
                startIdx++;
            } else {
                // Meeting starts after another ends - can reuse room
                roomsNeeded--;
                endIdx++;
            }
        }
        
        return maxRooms;
    }
    
    // Example 5: Calculate total time occupied by intervals
    public int calculateTotalTime(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int totalTime = 0;
        int currentStart = intervals[0][0];
        int currentEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= currentEnd) {
                // Overlapping - extend current interval
                currentEnd = Math.max(currentEnd, intervals[i][1]);
            } else {
                // Non-overlapping - add current interval and start new
                totalTime += currentEnd - currentStart;
                currentStart = intervals[i][0];
                currentEnd = intervals[i][1];
            }
        }
        
        // Add the last interval
        totalTime += currentEnd - currentStart;
        
        return totalTime;
    }
    
    public static void main(String[] args) {
        MergeIntervals mi = new MergeIntervals();
        
        // Test merge intervals
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<Interval> merged = mi.mergeIntervals(intervals);
        System.out.println("Merged intervals: " + merged);  // [[1,6], [8,10], [15,18]]
        
        // Test insert interval
        List<Interval> existing = Arrays.asList(
            new Interval(1, 2),
            new Interval(3, 5),
            new Interval(6, 7)
        );
        Interval toInsert = new Interval(5, 7);
        List<Interval> inserted = mi.insertInterval(existing, toInsert);
        System.out.println("After insertion: " + inserted);
        
        // Test meeting rooms
        int[][] meetings = {{0, 30}, {5, 10}, {15, 20}};
        int rooms = mi.minMeetingRooms(meetings);
        System.out.println("Minimum meeting rooms: " + rooms);  // Output: 2
        
        // Test total time
        int[][] times = {{1, 3}, {2, 6}, {8, 10}};
        int total = mi.calculateTotalTime(times);
        System.out.println("Total time occupied: " + total);  // Output: 7
    }
}
```

### Problem Categories

| Category | Examples |
|----------|----------|
| **Merging** | Merge Intervals, Insert and Merge |
| **Scheduling** | Meeting Rooms, Employee Schedule |
| **Overlap Detection** | Interval Intersection, Video Stitching |
| **Time Tracking** | Employee Free Time, Sky Line |
| **Range Union** | Merge Ranges, Group Consecutive Numbers |

### Interview Question Examples

1. **Merge Intervals** - Merge overlapping intervals into non-overlapping ones
2. **Insert Interval** - Insert new interval and merge if needed
3. **Meeting Rooms II** - Find minimum number of meeting rooms needed
4. **Employee Free Time** - Find common free time slots
5. **Video Stitching** - Find minimum clips to cover entire video

---

## Cyclic Sort

### Simple Explanation

The Cyclic Sort pattern is designed for problems involving **arrays with numbers in a specific range** (e.g., 1 to n). It places each number in its correct position by swapping. Each element is visited at most twice, making it extremely efficient for specific problem types.

### When to Recognize and Apply

**Red Flags to Identify:**
- Array contains **numbers from 1 to n** or **0 to n-1**
- **"Missing number", "duplicate number", or "finding extra"** mentioned
- Numbers should be in **specific positions**
- In-place sorting required where **index is related to value**
- Problem involves **permutation** of 1 to n

**Best For:**
- Finding missing numbers
- Finding duplicate numbers
- Finding all missing numbers
- First missing positive
- Permutation cycles

### Step-by-Step Approach

1. **Identify range** - Confirm array has numbers in known range (1 to n)
2. **Iterate through array** - For each position i
3. **Place in correct position** - Number x should be at index x-1 (or x for 0-indexed)
4. **Swap if wrong position** - If number at index i isn't in correct position, swap
5. **Continue until sorted** - Each number reaches its correct position
6. **Identify anomalies** - Missing, duplicates, or extra elements become apparent

### Code Example: Find Missing and Duplicate Numbers

```java
import java.util.*;

/**
 * Cyclic Sort Pattern
 * Best for problems where array contains numbers in range 1 to n
 */
public class CyclicSort {
    
    // Example 1: Find missing number in array [1, n]
    public int findMissing(int[] nums) {
        int n = nums.length + 1;  // Range is 1 to n
        
        // Place each number in its correct position
        for (int i = 0; i < nums.length; i++) {
            // Correct position for number nums[i] is nums[i] - 1
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // Swap nums[i] with nums[nums[i] - 1]
                int correctIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Find missing number
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        return n;
    }
    
    // Example 2: Find all missing numbers in array [1, n]
    public List<Integer> findAllMissing(int[] nums) {
        List<Integer> missing = new ArrayList<>();
        
        // Cyclic sort
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                // Swap
                int correctIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Find missing numbers
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                missing.add(i + 1);
            }
        }
        
        return missing;
    }
    
    // Example 3: Find duplicate number
    public int findDuplicate(int[] nums) {
        // All numbers should be in range [1, n] where n = nums.length - 1
        
        // Cyclic sort
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i && nums[i] < nums.length && nums[nums[i]] != nums[i]) {
                // Swap
                int correctIndex = nums[i];
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Find duplicate
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return nums[i];
            }
        }
        
        return -1;
    }
    
    // Example 4: Find all duplicates
    public List<Integer> findAllDuplicates(int[] nums) {
        List<Integer> duplicates = new ArrayList<>();
        
        // Cyclic sort
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                int correctIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Find duplicates
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                duplicates.add(nums[i]);
            }
        }
        
        return duplicates;
    }
    
    // Example 5: Find first missing positive
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // Cyclic sort - place positive numbers in range [1, n] at correct positions
        for (int i = 0; i < n; i++) {
            // Keep swapping until nums[i] is in correct position or out of range
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int correctIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Find first missing positive
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        return n + 1;
    }
    
    // Example 6: Find corrupt pair
    public int[] findCorruptedPair(int[] nums) {
        // Array should contain 1 to n, but has duplicate and missing
        int n = nums.length;
        
        // Cyclic sort
        for (int i = 0; i < n; i++) {
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                int correctIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Find duplicate and missing
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return new int[]{nums[i], i + 1};
            }
        }
        
        return new int[]{-1, -1};
    }
    
    public static void main(String[] args) {
        CyclicSort cs = new CyclicSort();
        
        // Test find missing
        int[] nums1 = {1, 3, 4, 5};
        System.out.println("Missing number: " + cs.findMissing(nums1));  // Output: 2
        
        // Test find all missing
        int[] nums2 = {2, 3, 1, 5};
        System.out.println("All missing: " + cs.findAllMissing(nums2));  // Output: [4]
        
        // Test find duplicate
        int[] nums3 = {1, 3, 4, 2, 2};
        System.out.println("Duplicate: " + cs.findDuplicate(nums3));  // Output: 2
        
        // Test find all duplicates
        int[] nums4 = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.println("All duplicates: " + cs.findAllDuplicates(nums4));  // Output: [2, 3]
        
        // Test first missing positive
        int[] nums5 = {3, 4, -1, 1};
        System.out.println("First missing positive: " + cs.firstMissingPositive(nums5));  // Output: 2
        
        // Test corrupted pair
        int[] nums6 = {1, 1, 3, 4};
        int[] corrupt = cs.findCorruptedPair(nums6);
        System.out.println("Corrupted pair: [" + corrupt[0] + ", " + corrupt[1] + "]");  // [1, 2]
    }
}
```

### Problem Categories

| Category | Examples |
|----------|----------|
| **Missing Numbers** | Missing Number, First Missing Positive |
| **Duplicate Numbers** | Find Duplicate, Find All Duplicates |
| **Permutation** | Missing and Duplicate, Corrupted Pair |
| **Array Arrangement** | Sort Array by Parity, Arrange Products |
| **Cycle Finding** | Find Cycle in Array |

### Interview Question Examples

1. **Missing Number** - Find missing number from 1 to n
2. **Find the Duplicate Number** - Find duplicate in array with one repeated
3. **First Missing Positive** - Find smallest missing positive integer
4. **Find All Duplicates in Array** - Find all duplicate numbers
5. **Set Mismatch** - Find duplicate and missing number

---

## Pattern Recognition Cheat Sheet

### Quick Identification Guide

| Pattern | Key Words | Data Structure | Complexity |
|---------|-----------|-----------------|-----------|
| **Two Pointers** | pair, sum, opposite ends, sorted, reverse | Array, String | O(n) |
| **Sliding Window** | substring, subarray, longest, shortest, window | Array, String | O(n) |
| **Fast-Slow** | cycle, middle, kth, linked list | Linked List | O(n) |
| **Merge Intervals** | overlap, merge, interval, schedule | Array/Pairs | O(n log n) |
| **Cyclic Sort** | 1 to n, missing, duplicate, permutation | Array | O(n) |

### When to Use Each Pattern

```
Problem has sorted array / opposite ends?
  → Try Two Pointers

Problem about contiguous elements / window?
  → Try Sliding Window

Problem about linked list / cycle detection?
  → Try Fast-Slow Pointers

Problem about intervals / overlapping ranges?
  → Try Merge Intervals

Problem about array with 1 to n numbers?
  → Try Cyclic Sort
```

---

## Practice Recommendations

1. **Start with understanding** - Read each pattern explanation and example
2. **Trace through code** - Step through examples manually to understand logic
3. **Identify problems** - Practice recognizing which pattern applies
4. **Implement from scratch** - Write solutions without looking at examples
5. **Combine patterns** - Some problems require combining multiple patterns
6. **Interview prep** - Practice explaining patterns and solutions under time pressure

---

## Additional Resources

- **LeetCode**: Practice on pattern-specific problem sets
- **GeeksforGeeks**: Detailed pattern explanations and variations
- **InterviewBit**: Interview-focused pattern problems
- **HackerRank**: Pattern-based competitive programming
