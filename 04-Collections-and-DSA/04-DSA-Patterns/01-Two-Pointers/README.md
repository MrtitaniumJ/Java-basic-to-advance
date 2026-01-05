# Two-Pointers Pattern - Complete Guide

## Pattern Overview

The Two-Pointers pattern is a fundamental algorithmic technique that uses two reference points (pointers) moving through a data structure simultaneously. These pointers typically move in opposite directions or at different speeds to solve problems efficiently without requiring extra space. This pattern is particularly effective for sorted arrays, linked lists, and problems requiring pair or triplet searches.

## When to Use the Two-Pointers Pattern

### Recognition Signs
- **Sorted Arrays**: When working with sorted data and searching for pairs/triplets
- **Paired Operations**: Finding elements with specific relationships (sum, difference, product)
- **Palindrome Checking**: Verifying if sequences read the same forwards and backwards
- **Reverse Operations**: Reversing arrays or linked lists in-place
- **Container Problems**: Finding optimal pairs based on certain constraints
- **Target Sum**: Finding pairs or triplets that sum to a target value
- **Remove Duplicates**: Efficiently managing unique elements in sorted arrays

### Advantages
- **Space Efficient**: Typically O(1) extra space complexity
- **Time Efficient**: Usually O(n) or O(n log n) time complexity
- **In-Place Operations**: Modifies data without extra storage
- **Simple Logic**: Easy to understand and implement

## Step-by-Step Approach

1. **Identify the Problem Type**: Determine if it's opposite-end or same-direction movement
2. **Initialize Pointers**: Place pointers at strategic starting positions (start/end or both at start)
3. **Define Movement Logic**: Establish conditions for moving each pointer forward or backward
4. **Process Elements**: Handle operations based on pointer values or conditions
5. **Termination Condition**: Define when the algorithm should stop
6. **Return Result**: Collect and return the required output

## Java Implementations

### 1. Two Sum in Sorted Array
```java
public class TwoPointersSortedArray {
    /**
     * Find two numbers that add up to a target in a sorted array
     * Time: O(n), Space: O(1)
     */
    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[]{};
        }
        
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                return new int[]{nums[left], nums[right]};
            } else if (sum < target) {
                left++;  // Need a larger number
            } else {
                right--;  // Need a smaller number
            }
        }
        
        return new int[]{};  // No pair found
    }
    
    /**
     * Find all pairs that sum to target with duplicates allowed
     */
    public static List<int[]> twoSumAllPairs(int[] nums, int target) {
        List<int[]> result = new ArrayList<>();
        if (nums == null || nums.length < 2) {
            return result;
        }
        
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                result.add(new int[]{nums[left], nums[right]});
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return result;
    }
}
```

### 2. Three Sum Problem
```java
public class ThreeSum {
    /**
     * Find all unique triplets that sum to zero
     * Time: O(n²), Space: O(1) if we ignore output
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // Sort the array for two-pointer approach
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate values for i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // If smallest possible sum is greater than 0, break
            if (nums[i] > 0) {
                break;
            }
            
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left pointer
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // Skip duplicates for right pointer
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
}
```

### 3. Palindrome Checking
```java
public class PalindromeChecker {
    /**
     * Check if array is a palindrome using two pointers
     * Time: O(n), Space: O(1)
     */
    public static boolean isPalindrome(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true;
        }
        
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            if (nums[left] != nums[right]) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Check if string is palindrome ignoring spaces and case
     */
    public static boolean isPalindromeString(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        
        int left = 0, right = s.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric characters
            while (left < right && !Character.isAlphaNumeric(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isAlphaNumeric(s.charAt(right))) {
                right--;
            }
            
            if (Character.toLowerCase(s.charAt(left)) != 
                Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
}
```

### 4. Container With Most Water
```java
public class ContainerWithMostWater {
    /**
     * Find two lines that form a container with maximum water
     * Time: O(n), Space: O(1)
     */
    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        
        int maxArea = 0;
        int left = 0, right = height.length - 1;
        
        while (left < right) {
            // Calculate current area
            int width = right - left;
            int currentHeight = Math.min(height[left], height[right]);
            int area = width * currentHeight;
            
            maxArea = Math.max(maxArea, area);
            
            // Move the pointer pointing to the smaller height
            // This ensures we might find a larger container
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
}
```

### 5. Remove Duplicates from Sorted Array
```java
public class RemoveDuplicates {
    /**
     * Remove duplicates in-place, keeping only unique elements
     * Time: O(n), Space: O(1)
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int writeIndex = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[writeIndex]) {
                writeIndex++;
                nums[writeIndex] = nums[i];
            }
        }
        
        return writeIndex + 1;
    }
    
    /**
     * Remove duplicates allowing at most 2 occurrences of each element
     */
    public static int removeDuplicatesAtMost2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int writeIndex = 0;
        int count = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[writeIndex]) {
                count++;
                if (count < 2) {
                    writeIndex++;
                    nums[writeIndex] = nums[i];
                }
            } else {
                writeIndex++;
                nums[writeIndex] = nums[i];
                count = 0;
            }
        }
        
        return writeIndex + 1;
    }
}
```

### 6. Reverse Array In-Place
```java
public class ReverseArray {
    /**
     * Reverse array in-place using two pointers
     * Time: O(n), Space: O(1)
     */
    public static void reverse(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            // Swap elements
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            
            left++;
            right--;
        }
    }
    
    /**
     * Reverse a string builder in-place
     */
    public static void reverseString(char[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }
        
        int left = 0, right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            
            left++;
            right--;
        }
    }
}
```

## Problem Categories

1. **Array/String Problems**: Two sum, container with water, trapping rain water
2. **Linked List Problems**: Cycle detection, reverse linked lists, partition lists
3. **Palindrome Problems**: String palindrome, list palindrome, valid sequences
4. **Merging Problems**: Merge sorted arrays, merge sorted lists
5. **Partitioning Problems**: Dutch national flag, sort array with duplicates
6. **Intersection Problems**: Finding common elements, intersection points

## Complexity Analysis

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|-----------------|------------------|-------|
| Two Sum (Sorted) | O(n) | O(1) | Single pass with two pointers |
| Three Sum | O(n²) | O(1) | Nested loops with two pointers |
| Palindrome Check | O(n) | O(1) | Compare from both ends |
| Reverse Array | O(n) | O(1) | Swap elements from both ends |
| Container Area | O(n) | O(1) | Single pass optimization |

## Real-World Applications

1. **Network Protocols**: Buffer management with read/write pointers
2. **Data Compression**: Sliding window compression algorithms
3. **Stream Processing**: Handling incoming and outgoing data streams
4. **Game Development**: Collision detection with moving objects
5. **Database Indexing**: Range queries and sorted data access
6. **String Matching**: Pattern matching and palindrome validation

## Best Practices

1. Always sort data before applying two-pointer technique when necessary
2. Handle edge cases (null, empty, single element arrays)
3. Understand pointer movement logic before implementation
4. Skip duplicates carefully in problems requiring unique results
5. Consider whether in-place modification is allowed
6. Test with various input scenarios

## Related Patterns

- **Sliding Window**: Similar optimization but uses window expansion/contraction
- **Fast-Slow Pointers**: Different movement speeds for cycle detection
- **Binary Search**: Similar divide-and-conquer in sorted arrays
