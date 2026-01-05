# Largest Number Program

## Problem Statement

Write a Java program that finds the largest number among given numbers.

**Objectives:**
1. Find largest of two numbers
2. Find largest of three numbers  
3. Find largest in an array of numbers

**Input:** Multiple numbers
**Output:** The largest number

**Examples:**
- Largest of 5 and 9 → 9
- Largest of 15, 8, 23 → 23
- Largest of [3, 7, 2, 9, 1] → 9

---

## Concepts Explained

### 1. **Comparison Operators**
```java
if (a > b) {
    System.out.println(a + " is larger");
} else {
    System.out.println(b + " is larger");
}
```
- `>` : greater than
- `<` : less than
- `>=` : greater than or equal to
- `<=` : less than or equal to

### 2. **Ternary Operator**
```java
int max = (a > b) ? a : b;
// If a > b, max = a, else max = b
```
- Concise way to choose between two values
- Condition ? valueIfTrue : valueIfFalse

### 3. **Multiple Comparisons**
```java
if (a >= b && a >= c) {
    return a;
} else if (b >= a && b >= c) {
    return b;
} else {
    return c;
}
```
- Use `&&` (AND) to combine conditions
- Check against all other values

### 4. **Loop to Find Maximum**
```java
int max = array[0];
for (int i = 1; i < array.length; i++) {
    if (array[i] > max) {
        max = array[i];
    }
}
```
- Initialize with first element
- Compare each subsequent element
- Update if found larger

---

## Solution Breakdown

### Approach 1: Two Numbers
```java
if (a > b) {
    return a;
} else {
    return b;
}
// Or using ternary: return a > b ? a : b;
```

### Approach 2: Three Numbers
```java
if (a >= b && a >= c) return a;
else if (b >= a && b >= c) return b;
else return c;
```

### Approach 3: Array Loop
```java
int max = array[0];
for (int i = 1; i < array.length; i++) {
    if (array[i] > max) {
        max = array[i];
    }
}
return max;
```

### Approach 4: Using Built-in Method
```java
int max = Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
// Uses Java Streams API
```

---

## Algorithm

**Basic Algorithm (for array):**
1. Handle edge case (empty array)
2. Initialize max with first element
3. Loop through remaining elements:
   - If current element > max:
     - Update max
4. Return max

**Step-by-Step:**
```
1. max = array[0]
2. For each element from index 1 to end:
   - If element > max:
     - max = element
3. Return max
```

---

## Time & Space Complexity

| Method | Time Complexity | Space Complexity | Notes |
|--------|-----------------|------------------|-------|
| Two Numbers | O(1) | O(1) | Single comparison |
| Three Numbers | O(1) | O(1) | Multiple comparisons |
| Array Loop | O(n) | O(1) | Iterates through all elements |
| Streams | O(n) | O(1) | More readable, slight overhead |

**Analysis:**
- **Time:** Proportional to number of elements to compare
- **Space:** Constant - no extra data structure needed
- **Scalability:** Loop approach scales to any number of elements

---

## Sample Input/Output

### Example 1: Two Numbers
```
Input:  num1 = 45, num2 = 23
Output: Largest: 45
```

### Example 2: Three Numbers
```
Input:  num1 = 15, num2 = 8, num3 = 23
Output: Largest: 23
```

### Example 3: Array of Numbers
```
Input:  How many? 5
        Numbers: 3, 7, 2, 9, 1
Output: Numbers: [3, 7, 2, 9, 1]
        Largest: 9
        Sorted: [1, 2, 3, 7, 9]
        Smallest: 1
```

### Example 4: Negative Numbers
```
Input:  -5, -12, -2
Output: Largest: -2
```

### Example 5: With Duplicates
```
Input:  5, 9, 5, 12, 12
Output: Largest: 12
        (Duplicates are handled correctly)
```

---

## Code Walkthrough

```java
// Method 1: Two numbers using ternary operator
public static int findLargest(int a, int b) {
    return a > b ? a : b;  // Simple and concise
}
// If a > b, return a; otherwise return b

// Method 2: Three numbers using nested if
public static int findLargestThree(int a, int b, int c) {
    if (a >= b && a >= c) {         // a is largest
        return a;
    } else if (b >= a && b >= c) {  // b is largest
        return b;
    } else {                          // c is largest
        return c;
    }
}

// Method 3: Array using loop
public static int findLargestInArray(int[] numbers) {
    int max = numbers[0];            // Start with first element
    
    for (int i = 1; i < numbers.length; i++) {
        if (numbers[i] > max) {      // Found larger element
            max = numbers[i];        // Update max
        }
    }
    
    return max;
}
```

---

## Variations and Extensions

### Variation 1: Find Second Largest
```java
public static int findSecondLargest(int[] numbers) {
    int max = Integer.MIN_VALUE;
    int secondMax = Integer.MIN_VALUE;
    
    for (int num : numbers) {
        if (num > max) {
            secondMax = max;
            max = num;
        } else if (num > secondMax && num != max) {
            secondMax = num;
        }
    }
    
    return secondMax;
}
```

### Variation 2: Find Largest and Smallest
```java
public static void findLargestAndSmallest(int[] numbers) {
    int max = numbers[0];
    int min = numbers[0];
    
    for (int i = 1; i < numbers.length; i++) {
        if (numbers[i] > max) max = numbers[i];
        if (numbers[i] < min) min = numbers[i];
    }
    
    System.out.println("Largest: " + max);
    System.out.println("Smallest: " + min);
}
```

### Variation 3: Find Kth Largest
```java
public static int findKthLargest(int[] numbers, int k) {
    Arrays.sort(numbers);
    return numbers[numbers.length - k];
}
```

### Variation 4: Find Largest Without Built-in Functions
```java
// Manual implementation without Arrays.sort()
```

### Variation 5: Using Comparator
```java
public static int findLargest(Integer... numbers) {
    return Arrays.stream(numbers)
                 .max(Integer::compare)
                 .orElse(Integer.MIN_VALUE);
}
```

---

## Challenges

### Challenge 1: Find Second Largest
Write a program to find the second largest number without sorting.

### Challenge 2: Find Largest and Its Index
Find the largest number and its position/index in the array.

### Challenge 3: Find Kth Largest Element
Given k, find the kth largest element in the array.

### Challenge 4: Find All Elements Larger Than Average
Find all elements larger than the average of the array.

### Challenge 5: Find Largest in 2D Array
Given a 2D array, find the largest number.

### Challenge 6: Find Largest Difference
Find the largest difference between any two numbers in the array.

---

## Common Errors and Solutions

### Error 1: Not Initializing Correctly
```java
int max;  // No initialization
for (int i = 0; i < array.length; i++) {
    if (array[i] > max) {  // Error: max might not be initialized
        max = array[i];
    }
}
```
**Solution:** Initialize with first element:
```java
int max = array[0];  // Start with first element
```

### Error 2: Starting Loop from 0
```java
int max = array[0];
for (int i = 0; i < array.length; i++) {  // Starts from 0, redundant
    if (array[i] > max) {
        max = array[i];
    }
}
```
**Solution:** Start from 1:
```java
for (int i = 1; i < array.length; i++) {  // Skip first element
    if (array[i] > max) {
        max = array[i];
    }
}
```

### Error 3: Empty Array
```java
public static int findLargest(int[] array) {
    int max = array[0];  // Error if array is empty!
}
```
**Solution:** Check length first:
```java
public static int findLargest(int[] array) {
    if (array.length == 0) {
        return Integer.MIN_VALUE;  // Or throw exception
    }
    int max = array[0];
}
```

### Error 4: Using = Instead of >
```java
if (numbers[i] = max) {  // Wrong: assignment instead of comparison
    max = numbers[i];
}
```
**Solution:** Use comparison:
```java
if (numbers[i] > max) {  // Correct: comparison
    max = numbers[i];
}
```

---

## Key Takeaways

1. **Comparison operators** are fundamental
2. **Ternary operator** useful for simple comparisons
3. **Loop and update** pattern for multiple values
4. **Initialize with first element** is common pattern
5. **Edge cases matter** - empty arrays, negatives, duplicates

---

## Real-World Applications

1. **Inventory Management:** Find highest price/quantity
2. **Temperature Monitoring:** Find highest temperature
3. **Score Tracking:** Find highest score in game
4. **Performance Analysis:** Find peak memory usage
5. **Data Analysis:** Find maximum values in datasets

---

## Performance Considerations

- **O(n) is optimal** - must check all elements
- **One-pass algorithm** - can't do better
- **Minimal extra space** - O(1) is best possible
- **No sorting needed** - sorting would be O(n log n)

---

## Next Steps

1. Implement and test all approaches
2. Handle edge cases (empty, duplicates, negatives)
3. Try finding second largest
4. Try finding in 2D array
5. Move on to the **Simple Calculator**

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 15 minutes
