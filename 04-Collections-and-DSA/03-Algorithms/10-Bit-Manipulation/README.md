# Bit Manipulation Algorithms

## Table of Contents
- [Introduction](#introduction)
- [Bitwise Operators](#bitwise-operators)
- [Common Bit Tricks](#common-bit-tricks)
- [Core Bit Manipulation Techniques](#core-bit-manipulation-techniques)
- [Java Implementations](#java-implementations)
- [Complexity Analysis](#complexity-analysis)
- [Practical Applications](#practical-applications)
- [Common Patterns](#common-patterns)

## Introduction

Bit manipulation is a powerful technique that involves operating directly on binary representations of numbers. It's one of the fastest computational operations since processors can perform bitwise operations directly at the hardware level. Bit manipulation is extensively used in competitive programming, system programming, cryptography, and optimization problems where performance is critical.

Understanding bit manipulation opens doors to elegant solutions for problems involving sets, toggles, flags, and mathematical operations. Many complex problems can be solved efficiently using simple bitwise operations, often reducing time complexity from O(n) to O(1) for certain operations.

## Bitwise Operators

### AND Operator (&)
Performs logical AND on each bit pair. The result bit is 1 only if both bits are 1.
- **Use Cases**: Checking if a bit is set, clearing bits, extracting specific bits
- **Example**: `5 & 3 = 0101 & 0011 = 0001 = 1`

### OR Operator (|)
Performs logical OR on each bit pair. The result bit is 1 if at least one bit is 1.
- **Use Cases**: Setting bits, combining flags
- **Example**: `5 | 3 = 0101 | 0011 = 0111 = 7`

### XOR Operator (^)
Performs logical XOR on each bit pair. The result bit is 1 if bits are different.
- **Use Cases**: Toggling bits, finding unique elements, swapping without temp variable
- **Example**: `5 ^ 3 = 0101 ^ 0011 = 0110 = 6`
- **Property**: `a ^ a = 0`, `a ^ 0 = a`, `a ^ b ^ a = b`

### NOT Operator (~)
Inverts all bits (one's complement).
- **Use Cases**: Creating bit masks, inverting flags
- **Example**: `~5 = ~0101 = 1010 (in 4-bit representation)`

### Left Shift (<<)
Shifts bits to the left, filling with zeros. Equivalent to multiplication by 2^n.
- **Use Cases**: Multiplying by powers of 2, creating bit masks
- **Example**: `5 << 2 = 0101 << 2 = 10100 = 20`

### Right Shift (>>)
Arithmetic shift preserving sign bit. Equivalent to division by 2^n.
- **Use Cases**: Dividing by powers of 2, extracting higher bits
- **Example**: `20 >> 2 = 10100 >> 2 = 00101 = 5`

### Unsigned Right Shift (>>>)
Logical shift filling with zeros regardless of sign.
- **Use Cases**: Working with unsigned integers, bit extraction
- **Example**: `-1 >>> 1` shifts without sign extension

## Common Bit Tricks

### Essential Bit Operations
1. **Check if number is odd**: `(n & 1) == 1`
2. **Check if number is even**: `(n & 1) == 0`
3. **Multiply by 2**: `n << 1`
4. **Divide by 2**: `n >> 1`
5. **Toggle ith bit**: `n ^ (1 << i)`
6. **Set ith bit**: `n | (1 << i)`
7. **Clear ith bit**: `n & ~(1 << i)`
8. **Check if ith bit is set**: `(n & (1 << i)) != 0`
9. **Clear lowest set bit**: `n & (n - 1)`
10. **Isolate lowest set bit**: `n & (-n)`

## Core Bit Manipulation Techniques

### Counting Set Bits (Population Count)
Multiple algorithms exist with varying efficiency. Brian Kernighan's algorithm is particularly elegant, clearing the lowest set bit in each iteration.

### Power of 2 Detection
A number is a power of 2 if it has exactly one bit set. Using `n & (n-1)` efficiently checks this property by verifying if clearing the lowest set bit results in zero.

### Single Number Problems
XOR properties make it ideal for finding unique elements. Since `a ^ a = 0` and XOR is associative and commutative, all duplicate elements cancel out, leaving only unique elements.

### Bit Masking
Using integers as bit arrays to represent sets. Each bit position represents presence or absence of an element. This technique is crucial for subset generation and dynamic programming on subsets.

## Java Implementations

```java
/**
 * Comprehensive Bit Manipulation Algorithms Implementation
 * Demonstrates various bit manipulation techniques and patterns
 */
public class BitManipulation {
    
    // ==================== Basic Bit Operations ====================
    
    /**
     * Checks if the ith bit is set in number n
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static boolean isIthBitSet(int n, int i) {
        return (n & (1 << i)) != 0;
    }
    
    /**
     * Sets the ith bit in number n
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int setIthBit(int n, int i) {
        return n | (1 << i);
    }
    
    /**
     * Clears the ith bit in number n
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int clearIthBit(int n, int i) {
        return n & ~(1 << i);
    }
    
    /**
     * Toggles the ith bit in number n
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int toggleIthBit(int n, int i) {
        return n ^ (1 << i);
    }
    
    /**
     * Updates the ith bit to given value (0 or 1)
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int updateIthBit(int n, int i, int value) {
        // Clear the ith bit first
        n = clearIthBit(n, i);
        // Set it to the desired value
        return n | (value << i);
    }
    
    // ==================== Counting Set Bits ====================
    
    /**
     * Counts the number of set bits using simple iteration
     * Time Complexity: O(log n) - number of bits
     * Space Complexity: O(1)
     */
    public static int countSetBitsSimple(int n) {
        int count = 0;
        while (n > 0) {
            count += (n & 1);
            n >>= 1;
        }
        return count;
    }
    
    /**
     * Brian Kernighan's Algorithm - counts set bits efficiently
     * Clears the rightmost set bit in each iteration
     * Time Complexity: O(k) where k is number of set bits
     * Space Complexity: O(1)
     */
    public static int countSetBitsKernighan(int n) {
        int count = 0;
        while (n > 0) {
            n &= (n - 1);  // Clear the rightmost set bit
            count++;
        }
        return count;
    }
    
    /**
     * Count set bits using Java's built-in method
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int countSetBitsBuiltIn(int n) {
        return Integer.bitCount(n);
    }
    
    /**
     * Counts set bits for all numbers from 0 to n
     * Uses dynamic programming approach
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[] countBitsDP(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i >> 1] + (i & 1);
        }
        return dp;
    }
    
    // ==================== Power of 2 Checks ====================
    
    /**
     * Checks if number is power of 2 using bit trick
     * A power of 2 has exactly one bit set
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    /**
     * Checks if number is power of 4
     * Power of 4 must be power of 2 and set bit at even position
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static boolean isPowerOfFour(int n) {
        // Check if power of 2 and bit at even position
        // 0x55555555 = 01010101010101010101010101010101 (bits at even positions)
        return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }
    
    /**
     * Finds the next power of 2 greater than or equal to n
     * Time Complexity: O(log log n)
     * Space Complexity: O(1)
     */
    public static int nextPowerOfTwo(int n) {
        if (n <= 0) return 1;
        n--;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n + 1;
    }
    
    // ==================== Single Number Problems ====================
    
    /**
     * Finds the single number when all others appear twice
     * Uses XOR property: a ^ a = 0, a ^ 0 = a
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
    
    /**
     * Finds two single numbers when all others appear twice
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int[] singleNumberTwo(int[] nums) {
        // XOR all numbers to get xor of the two unique numbers
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }
        
        // Find rightmost set bit in xor
        int rightmostBit = xor & (-xor);
        
        // Divide numbers into two groups based on this bit
        int num1 = 0, num2 = 0;
        for (int num : nums) {
            if ((num & rightmostBit) != 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }
        
        return new int[]{num1, num2};
    }
    
    /**
     * Finds single number when all others appear three times
     * Uses bit counting approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int singleNumberThree(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            twos |= ones & num;
            ones ^= num;
            int threes = ones & twos;
            ones &= ~threes;
            twos &= ~threes;
        }
        return ones;
    }
    
    // ==================== Bit Masking and Subsets ====================
    
    /**
     * Generates all subsets of an array using bit masking
     * Time Complexity: O(n * 2^n)
     * Space Complexity: O(n * 2^n) for output
     */
    public static List<List<Integer>> generateSubsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n;  // 2^n subsets
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            subsets.add(subset);
        }
        
        return subsets;
    }
    
    /**
     * Counts number of subsets with given sum using bit masking
     * Time Complexity: O(2^n * n)
     * Space Complexity: O(1)
     */
    public static int countSubsetsWithSum(int[] nums, int target) {
        int n = nums.length;
        int count = 0;
        int totalSubsets = 1 << n;
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += nums[i];
                }
            }
            if (sum == target) count++;
        }
        
        return count;
    }
    
    // ==================== Advanced Bit Tricks ====================
    
    /**
     * Swaps two numbers without using temporary variable
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static void swapWithoutTemp(int a, int b) {
        System.out.println("Before swap: a = " + a + ", b = " + b);
        a = a ^ b;
        b = a ^ b;  // b = a ^ b ^ b = a
        a = a ^ b;  // a = a ^ b ^ a = b
        System.out.println("After swap: a = " + a + ", b = " + b);
    }
    
    /**
     * Reverses bits of a 32-bit integer
     * Time Complexity: O(log n) - 32 iterations
     * Space Complexity: O(1)
     */
    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result |= (n & 1);
            n >>= 1;
        }
        return result;
    }
    
    /**
     * Finds missing number in array containing 0 to n
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int findMissingNumber(int[] nums) {
        int xor = 0;
        int n = nums.length;
        
        // XOR all numbers from 0 to n
        for (int i = 0; i <= n; i++) {
            xor ^= i;
        }
        
        // XOR all numbers in array
        for (int num : nums) {
            xor ^= num;
        }
        
        return xor;
    }
    
    /**
     * Gets the rightmost set bit position (1-indexed)
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int getRightmostSetBitPosition(int n) {
        if (n == 0) return 0;
        return (int)(Math.log(n & -n) / Math.log(2)) + 1;
    }
    
    /**
     * Isolates the rightmost set bit
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int isolateRightmostSetBit(int n) {
        return n & (-n);
    }
    
    /**
     * Checks if two numbers have opposite signs
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static boolean haveOppositeSigns(int a, int b) {
        return (a ^ b) < 0;
    }
    
    /**
     * Computes absolute value using bit manipulation
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int absoluteValue(int n) {
        int mask = n >> 31;  // All 1s if negative, all 0s if positive
        return (n + mask) ^ mask;
    }
    
    /**
     * Finds position of only set bit (if only one bit is set)
     * Returns -1 if multiple bits are set or no bits are set
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int findOnlySetBitPosition(int n) {
        if (n == 0 || (n & (n - 1)) != 0) {
            return -1;  // Not a power of 2
        }
        return (int)(Math.log(n) / Math.log(2)) + 1;
    }
    
    // ==================== Gray Code ====================
    
    /**
     * Converts binary to Gray code
     * Gray code: consecutive values differ by only one bit
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int binaryToGray(int n) {
        return n ^ (n >> 1);
    }
    
    /**
     * Converts Gray code to binary
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public static int grayToBinary(int n) {
        int binary = n;
        while (n > 0) {
            n >>= 1;
            binary ^= n;
        }
        return binary;
    }
    
    /**
     * Generates Gray code sequence of n bits
     * Time Complexity: O(2^n)
     * Space Complexity: O(2^n)
     */
    public static List<Integer> generateGrayCode(int n) {
        List<Integer> result = new ArrayList<>();
        int size = 1 << n;  // 2^n
        for (int i = 0; i < size; i++) {
            result.add(i ^ (i >> 1));
        }
        return result;
    }
    
    // ==================== Hamming Distance ====================
    
    /**
     * Calculates Hamming distance between two integers
     * Hamming distance: number of positions at which bits are different
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
    
    /**
     * Calculates total Hamming distance between all pairs in array
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int totalHammingDistance(int[] nums) {
        int total = 0;
        for (int i = 0; i < 32; i++) {
            int countOnes = 0;
            for (int num : nums) {
                countOnes += (num >> i) & 1;
            }
            total += countOnes * (nums.length - countOnes);
        }
        return total;
    }
    
    // ==================== Bit Manipulation in Strings ====================
    
    /**
     * Checks if a string has all unique characters using bit vector
     * Assumes lowercase letters only
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static boolean hasUniqueCharsBitVector(String s) {
        int checker = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = s.charAt(i) - 'a';
            if ((checker & (1 << val)) > 0) {
                return false;
            }
            checker |= (1 << val);
        }
        return true;
    }
    
    // ==================== Main Method for Testing ====================
    
    public static void main(String[] args) {
        System.out.println("=== Bit Manipulation Demonstrations ===\n");
        
        // Basic bit operations
        System.out.println("Basic Bit Operations:");
        int num = 10; // Binary: 1010
        System.out.println("Number: " + num + " (Binary: " + Integer.toBinaryString(num) + ")");
        System.out.println("Bit 1 is set: " + isIthBitSet(num, 1));
        System.out.println("After setting bit 0: " + setIthBit(num, 0) + 
                         " (Binary: " + Integer.toBinaryString(setIthBit(num, 0)) + ")");
        System.out.println("After clearing bit 1: " + clearIthBit(num, 1) +
                         " (Binary: " + Integer.toBinaryString(clearIthBit(num, 1)) + ")");
        System.out.println("After toggling bit 2: " + toggleIthBit(num, 2) +
                         " (Binary: " + Integer.toBinaryString(toggleIthBit(num, 2)) + ")");
        
        // Counting set bits
        System.out.println("\nCounting Set Bits:");
        int n = 29; // Binary: 11101
        System.out.println("Number: " + n + " (Binary: " + Integer.toBinaryString(n) + ")");
        System.out.println("Set bits (Simple): " + countSetBitsSimple(n));
        System.out.println("Set bits (Kernighan): " + countSetBitsKernighan(n));
        System.out.println("Set bits (Built-in): " + countSetBitsBuiltIn(n));
        
        // Power of 2 checks
        System.out.println("\nPower of 2 Checks:");
        int[] testNums = {16, 18, 64, 100};
        for (int testNum : testNums) {
            System.out.println(testNum + " is power of 2: " + isPowerOfTwo(testNum));
        }
        System.out.println("Next power of 2 after 100: " + nextPowerOfTwo(100));
        
        // Single number problems
        System.out.println("\nSingle Number Problems:");
        int[] arr1 = {4, 1, 2, 1, 2};
        System.out.println("Array: " + java.util.Arrays.toString(arr1));
        System.out.println("Single number: " + singleNumber(arr1));
        
        int[] arr2 = {1, 2, 1, 3, 2, 5};
        System.out.println("\nArray: " + java.util.Arrays.toString(arr2));
        System.out.println("Two single numbers: " + java.util.Arrays.toString(singleNumberTwo(arr2)));
        
        // Subsets generation
        System.out.println("\nSubset Generation:");
        int[] set = {1, 2, 3};
        System.out.println("Array: " + java.util.Arrays.toString(set));
        List<List<Integer>> subsets = generateSubsets(set);
        System.out.println("All subsets: " + subsets);
        System.out.println("Total subsets: " + subsets.size());
        
        // Advanced bit tricks
        System.out.println("\nAdvanced Bit Tricks:");
        swapWithoutTemp(5, 10);
        
        int toReverse = 43261596;
        System.out.println("\nOriginal number: " + toReverse);
        System.out.println("Reversed bits: " + reverseBits(toReverse));
        
        int[] missing = {3, 0, 1};
        System.out.println("\nArray with missing number: " + java.util.Arrays.toString(missing));
        System.out.println("Missing number: " + findMissingNumber(missing));
        
        // Gray code
        System.out.println("\nGray Code:");
        int grayN = 3;
        System.out.println("Gray code sequence for n=" + grayN + ": " + generateGrayCode(grayN));
        
        // Hamming distance
        System.out.println("\nHamming Distance:");
        int x = 1, y = 4;
        System.out.println("Between " + x + " and " + y + ": " + hammingDistance(x, y));
        
        int[] hammingArray = {4, 14, 2};
        System.out.println("Total Hamming distance in " + 
                         java.util.Arrays.toString(hammingArray) + ": " + 
                         totalHammingDistance(hammingArray));
        
        // String unique characters
        System.out.println("\nUnique Characters Check:");
        String str1 = "abcdef";
        String str2 = "abcdefa";
        System.out.println("'" + str1 + "' has unique chars: " + hasUniqueCharsBitVector(str1));
        System.out.println("'" + str2 + "' has unique chars: " + hasUniqueCharsBitVector(str2));
    }
}
```

## Complexity Analysis

### Time Complexity Comparison

| Operation | Complexity | Notes |
|-----------|-----------|-------|
| Check/Set/Clear/Toggle bit | O(1) | Direct bit operation |
| Count set bits (Simple) | O(log n) | Iterates through all bits |
| Count set bits (Kernighan) | O(k) | k = number of set bits |
| Power of 2 check | O(1) | Single bit operation |
| Single number (XOR) | O(n) | Single pass through array |
| Subset generation | O(n × 2^n) | 2^n subsets, n elements each |
| Reverse bits | O(log n) | Fixed 32 iterations for integers |
| Hamming distance | O(1) | Built-in bit count operation |
| Gray code generation | O(2^n) | Generates all 2^n codes |

### Space Complexity

Most bit manipulation operations use O(1) space, making them extremely memory-efficient. The exceptions are:
- Subset generation: O(n × 2^n) for storing all subsets
- Counting bits DP: O(n) for memoization array
- Gray code generation: O(2^n) for result storage

### Practical Performance

Bitwise operations are typically 2-5x faster than equivalent arithmetic operations because:
1. **Hardware support**: CPUs have dedicated circuitry for bit operations
2. **No overflow handling**: Unlike multiplication/division, bit shifts don't need overflow checks
3. **Predictable execution**: No branch misprediction or cache misses
4. **Parallelizable**: Modern CPUs can execute multiple bitwise operations simultaneously

## Practical Applications

### 1. System Programming
- **Memory management**: Bit masks for tracking allocated blocks
- **File permissions**: Unix uses 9 bits for read/write/execute permissions
- **Process scheduling**: CPU affinity masks in multi-core systems

### 2. Network Programming
- **IP addressing**: Subnet masks, CIDR notation
- **Protocol flags**: TCP flags (SYN, ACK, FIN) stored as bits
- **Data compression**: Huffman coding, bit-level encoding

### 3. Graphics and Gaming
- **Color representation**: RGB values packed in 32-bit integers
- **Collision detection**: Bitmasks for layer collision matrices
- **State management**: Player states (alive, jumping, crouching) as bit flags

### 4. Database Systems
- **Bitmap indexes**: Fast querying on categorical data
- **Bloom filters**: Probabilistic set membership testing
- **NULL handling**: Bit vectors for NULL column tracking

### 5. Cryptography
- **Encryption algorithms**: XOR-based ciphers (One-Time Pad)
- **Hash functions**: Bit mixing and avalanche effects
- **Random number generation**: Linear feedback shift registers

### 6. Competitive Programming
- **Dynamic programming**: Bitmask DP for subset problems
- **Optimization**: Space-efficient state representation
- **Fast calculations**: Quick power, GCD optimizations

## Common Patterns

### Pattern 1: XOR for Duplicates
When elements appear in pairs except one/two elements, XOR cancels duplicates:
```
a ^ a = 0
a ^ 0 = a
Commutative and associative
```

### Pattern 2: n & (n-1) Technique
This operation clears the rightmost set bit:
- **Power of 2**: Check if `n & (n-1) == 0`
- **Count set bits**: Repeat until n becomes 0
- **Detect single bit**: Verify only one bit is set

### Pattern 3: n & (-n) for LSB
Isolates the lowest set bit using two's complement:
- **Binary search on bits**: Find position of set bit
- **Fenwick trees**: Parent-child relationships
- **Priority extraction**: Get minimum set bit position

### Pattern 4: Bit Masking for Subsets
Represent subsets as integers where bit i indicates element i's presence:
- **Subset DP**: Process all 2^n subsets systematically
- **Combination generation**: Iterate through all masks
- **State compression**: Store complex states efficiently

### Pattern 5: Gray Code Pattern
Change only one bit between consecutive numbers:
- **Error correction**: Minimize transmission errors
- **Rotary encoders**: Mechanical position sensing
- **Genetic algorithms**: Smooth fitness landscape traversal

### Pattern 6: Bit Packing
Store multiple values in single integer:
- **Date storage**: Year (12 bits) + Month (4 bits) + Day (5 bits)
- **Flags and options**: Boolean settings as single bitmask
- **Coordinate compression**: Store 2D coordinates in 64 bits

### Pattern 7: Hamming Weight Applications
Count set bits for various problems:
- **Hamming distance**: Bit difference between numbers
- **DNA sequence comparison**: Nucleotide differences
- **Error detection**: Parity bit calculation

## Key Takeaways

1. **Master the basics**: Understand all six bitwise operators thoroughly
2. **Recognize XOR properties**: Most powerful for finding unique elements
3. **Use n & (n-1)**: Single most useful bit manipulation trick
4. **Think in binary**: Visualize numbers as bit patterns
5. **Leverage hardware**: Bit operations are fastest at CPU level
6. **Consider edge cases**: Handle negative numbers and overflow carefully
7. **Combine with algorithms**: Bit manipulation enhances many classic algorithms
8. **Practice subset problems**: Bitmask DP is common in competitive programming

Bit manipulation provides elegant solutions to complex problems, often reducing both time and space complexity significantly. The key is recognizing when a problem can benefit from thinking in terms of binary representations rather than decimal arithmetic.

---

*Note: All implementations use standard Java bit operations which are platform-independent and guaranteed to work consistently across different systems. For unsigned operations in Java, use the unsigned shift operator (>>>) and be aware of integer promotion rules.*
