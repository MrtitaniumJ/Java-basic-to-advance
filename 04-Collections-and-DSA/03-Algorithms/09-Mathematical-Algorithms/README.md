# Mathematical Algorithms in Java

## Table of Contents
1. [Introduction](#introduction)
2. [GCD and LCM](#gcd-and-lcm)
3. [Prime Numbers](#prime-numbers)
4. [Modular Arithmetic](#modular-arithmetic)
5. [Power Calculation](#power-calculation)
6. [Factorial Computations](#factorial-computations)
7. [Fibonacci Numbers](#fibonacci-numbers)
8. [Combinatorics](#combinatorics)
9. [Number Theory Basics](#number-theory-basics)
10. [Complete Implementation](#complete-implementation)

## Introduction

Mathematical algorithms form the foundation of computational mathematics and are essential for solving numerous programming challenges. These algorithms leverage mathematical principles to solve problems efficiently, often reducing exponential time complexities to polynomial or logarithmic ones. Understanding these algorithms is crucial for competitive programming, cryptography, and various computational applications.

This guide explores fundamental mathematical algorithms with rigorous proofs, complexity analysis, and practical Java implementations. Each algorithm is presented with its theoretical foundation, followed by optimized code implementations that demonstrate real-world applications.

## GCD and LCM

### Greatest Common Divisor (GCD)

The Greatest Common Divisor of two integers is the largest positive integer that divides both numbers without remainder. The Euclidean algorithm provides an efficient method for computing GCD.

**Mathematical Foundation:**

The Euclidean algorithm is based on the principle: `gcd(a, b) = gcd(b, a mod b)` where `a ≥ b`. This follows from the fact that any common divisor of `a` and `b` is also a divisor of `a - kb` for any integer `k`.

**Proof:**
Let `d = gcd(a, b)`. Then `d | a` and `d | b`. Since `a = bq + r` (where `r = a mod b`), we have `r = a - bq`. Therefore, `d | r`. Conversely, any divisor of `b` and `r` also divides `a = bq + r`. Hence, `gcd(a, b) = gcd(b, r)`.

**Time Complexity:** O(log min(a, b)) - Lamé's theorem proves that the number of steps is at most 5 times the number of digits in the smaller number.

**Space Complexity:** O(1) for iterative, O(log min(a, b)) for recursive due to call stack.

### Least Common Multiple (LCM)

The LCM of two integers is the smallest positive integer divisible by both numbers. It relates to GCD through: `lcm(a, b) = (a × b) / gcd(a, b)`.

**Applications:**
- Fraction arithmetic (finding common denominators)
- Synchronization problems (meeting times, cycles)
- Cryptography (key generation)
- Music theory (harmonic frequencies)

## Prime Numbers

### Sieve of Eratosthenes

The Sieve of Eratosthenes is an ancient algorithm for finding all prime numbers up to a specified integer. It systematically eliminates composite numbers by marking multiples of each prime.

**Algorithm:**
1. Create a boolean array of size n+1, initially all true
2. Mark 0 and 1 as non-prime
3. For each number p from 2 to √n:
   - If p is marked prime, mark all multiples of p (starting from p²) as composite
4. Remaining marked numbers are prime

**Mathematical Proof:**

The algorithm's correctness relies on the fact that every composite number has at least one prime factor ≤ √n. When we process prime p, we mark all its multiples. By starting from p², we avoid redundant marking (smaller multiples already marked by smaller primes).

**Time Complexity:** O(n log log n) - The nested loop runs n/2 + n/3 + n/5 + ... times, which sums to approximately n log log n.

**Space Complexity:** O(n) for the boolean array.

**Optimizations:**
- Process only odd numbers (after handling 2)
- Use bit arrays to reduce memory by factor of 8
- Segmented sieve for very large ranges

## Modular Arithmetic

Modular arithmetic is fundamental to cryptography, hashing, and number theory. Operations are performed "modulo" a number, wrapping around at the modulus.

**Properties:**
1. **(a + b) mod m = ((a mod m) + (b mod m)) mod m**
2. **(a × b) mod m = ((a mod m) × (b mod m)) mod m**
3. **(a - b) mod m = ((a mod m) - (b mod m) + m) mod m**

**Modular Inverse:**

For coprime integers a and m, the modular inverse a⁻¹ satisfies: `(a × a⁻¹) mod m = 1`. It can be computed using the Extended Euclidean Algorithm.

**Applications:**
- RSA cryptography
- Hash functions
- Random number generation
- Cyclic data structures

## Power Calculation

### Fast Exponentiation (Binary Exponentiation)

Computing a^n naively requires n-1 multiplications. Fast exponentiation reduces this to O(log n) using the binary representation of the exponent.

**Algorithm:**
The key insight is: a^n = (a^(n/2))² if n is even, and a^n = a × a^(n-1) if n is odd.

**Mathematical Proof:**

For even n = 2k: a^n = a^(2k) = (a^k)²
For odd n = 2k+1: a^n = a^(2k+1) = a × a^(2k) = a × (a^k)²

This recursive structure corresponds to processing the binary representation of n from least significant bit to most significant bit.

**Time Complexity:** O(log n)
**Space Complexity:** O(1) iterative, O(log n) recursive

## Factorial Computations

Factorial n! = n × (n-1) × ... × 2 × 1 grows extremely rapidly. For large n, direct computation causes overflow, requiring modular arithmetic or special data structures.

**Properties:**
- n! = n × (n-1)!
- Stirling's approximation: n! ≈ √(2πn) × (n/e)^n
- n! has exactly ⌊n/p⌋ + ⌊n/p²⌋ + ⌊n/p³⌋ + ... factors of prime p

**Applications:**
- Permutations and combinations
- Probability calculations
- Series expansions
- Trailing zeros calculation

## Fibonacci Numbers

The Fibonacci sequence F(n) = F(n-1) + F(n-2) with F(0) = 0, F(1) = 1 appears throughout mathematics and nature.

**Methods:**

1. **Recursive:** Direct but exponential O(2^n)
2. **Dynamic Programming:** O(n) time, O(1) space with two variables
3. **Matrix Exponentiation:** O(log n) using [[1,1],[1,0]]^n
4. **Binet's Formula:** Closed form using golden ratio φ = (1+√5)/2

**Mathematical Properties:**
- F(n) ≈ φ^n / √5 for large n
- gcd(F(m), F(n)) = F(gcd(m, n))
- Every positive integer can be represented as sum of non-consecutive Fibonacci numbers (Zeckendorf's theorem)

## Combinatorics

### Pascal's Triangle and Binomial Coefficients

The binomial coefficient C(n, k) = n! / (k! × (n-k)!) represents the number of ways to choose k items from n items.

**Properties:**
1. C(n, k) = C(n, n-k) (symmetry)
2. C(n, k) = C(n-1, k-1) + C(n-1, k) (Pascal's identity)
3. Sum of row n = 2^n

**Catalan Numbers:**

The nth Catalan number Cₙ = C(2n, n)/(n+1) counts various combinatorial structures:
- Number of valid parentheses expressions with n pairs
- Number of binary trees with n internal nodes
- Number of ways to triangulate a convex (n+2)-gon

## Number Theory Basics

### Divisibility and Factorization

**Trial Division:** Check divisors up to √n
**Prime Factorization:** Every integer > 1 uniquely factors into primes (Fundamental Theorem of Arithmetic)

### Euler's Totient Function

φ(n) counts integers from 1 to n that are coprime to n.

**Properties:**
- φ(p) = p - 1 for prime p
- φ(p^k) = p^k - p^(k-1)
- φ(mn) = φ(m)φ(n) if gcd(m,n) = 1

**Euler's Theorem:** If gcd(a, n) = 1, then a^φ(n) ≡ 1 (mod n)

**Applications:**
- RSA cryptography (key generation)
- Finding modular multiplicative inverses
- Solving linear congruences

## Complete Implementation

Below is a comprehensive Java implementation of all mathematical algorithms discussed:

```java
/**
 * Mathematical Algorithms Implementation
 * Comprehensive collection of number theory and mathematical computation algorithms
 */
public class MathematicalAlgorithms {
    
    private static final long MOD = 1_000_000_007;
    
    // ==================== GCD AND LCM ====================
    
    /**
     * Computes GCD using Euclidean algorithm (iterative)
     * Time Complexity: O(log min(a, b))
     * Space Complexity: O(1)
     */
    public static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    /**
     * Computes GCD recursively
     * Time Complexity: O(log min(a, b))
     * Space Complexity: O(log min(a, b))
     */
    public static long gcdRecursive(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }
    
    /**
     * Computes LCM using GCD
     * Time Complexity: O(log min(a, b))
     * Space Complexity: O(1)
     */
    public static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b; // Divide first to prevent overflow
    }
    
    /**
     * Extended Euclidean Algorithm
     * Finds x, y such that ax + by = gcd(a, b)
     * Returns array [gcd, x, y]
     * Time Complexity: O(log min(a, b))
     */
    public static long[] extendedGCD(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        }
        long[] result = extendedGCD(b, a % b);
        long gcd = result[0];
        long x1 = result[1];
        long y1 = result[2];
        
        // Update x and y using results from recursive call
        long x = y1;
        long y = x1 - (a / b) * y1;
        
        return new long[]{gcd, x, y};
    }
    
    /**
     * GCD of array of numbers
     * Time Complexity: O(n * log(max element))
     */
    public static long gcdArray(long[] arr) {
        long result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            result = gcd(result, arr[i]);
            if (result == 1) {
                break; // Optimization: GCD cannot be smaller than 1
            }
        }
        return result;
    }
    
    // ==================== PRIME NUMBERS ====================
    
    /**
     * Sieve of Eratosthenes - finds all primes up to n
     * Time Complexity: O(n log log n)
     * Space Complexity: O(n)
     */
    public static boolean[] sieveOfEratosthenes(int n) {
        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }
        
        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                // Mark all multiples of p starting from p^2
                for (int i = p * p; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }
        
        return isPrime;
    }
    
    /**
     * Optimized Sieve - only process odd numbers
     * Time Complexity: O(n log log n)
     * Space Complexity: O(n/2)
     */
    public static boolean[] optimizedSieve(int n) {
        if (n < 2) return new boolean[n + 1];
        
        // Only store odd numbers: index i represents number 2*i+1
        boolean[] isPrime = new boolean[(n + 1) / 2];
        for (int i = 0; i < isPrime.length; i++) {
            isPrime[i] = true;
        }
        
        // Iterate through odd numbers
        for (int i = 1; i * i < isPrime.length; i++) {
            if (isPrime[i]) {
                int p = 2 * i + 1;
                // Mark multiples starting from p^2
                for (int j = (p * p - 1) / 2; j < isPrime.length; j += p) {
                    isPrime[j] = false;
                }
            }
        }
        
        // Convert back to standard representation
        boolean[] result = new boolean[n + 1];
        result[2] = true;
        for (int i = 1; i < isPrime.length; i++) {
            if (isPrime[i]) {
                result[2 * i + 1] = true;
            }
        }
        
        return result;
    }
    
    /**
     * Check if a number is prime using trial division
     * Time Complexity: O(√n)
     */
    public static boolean isPrime(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        // Check divisors of form 6k ± 1 up to √n
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Prime factorization using trial division
     * Time Complexity: O(√n)
     */
    public static java.util.List<Long> primeFactors(long n) {
        java.util.List<Long> factors = new java.util.ArrayList<>();
        
        // Handle factor 2
        while (n % 2 == 0) {
            factors.add(2L);
            n /= 2;
        }
        
        // Handle odd factors
        for (long i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        
        // If n is still > 1, then it's a prime factor
        if (n > 1) {
            factors.add(n);
        }
        
        return factors;
    }
    
    /**
     * Count distinct prime factors
     * Time Complexity: O(√n)
     */
    public static int countDistinctPrimeFactors(long n) {
        int count = 0;
        
        if (n % 2 == 0) {
            count++;
            while (n % 2 == 0) {
                n /= 2;
            }
        }
        
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                count++;
                while (n % i == 0) {
                    n /= i;
                }
            }
        }
        
        if (n > 1) count++;
        
        return count;
    }
    
    // ==================== MODULAR ARITHMETIC ====================
    
    /**
     * Modular addition to prevent overflow
     * Time Complexity: O(1)
     */
    public static long modAdd(long a, long b, long mod) {
        return ((a % mod) + (b % mod)) % mod;
    }
    
    /**
     * Modular subtraction
     * Time Complexity: O(1)
     */
    public static long modSub(long a, long b, long mod) {
        return ((a % mod) - (b % mod) + mod) % mod;
    }
    
    /**
     * Modular multiplication to prevent overflow
     * Time Complexity: O(1)
     */
    public static long modMul(long a, long b, long mod) {
        return ((a % mod) * (b % mod)) % mod;
    }
    
    /**
     * Modular multiplicative inverse using Extended Euclidean Algorithm
     * Returns -1 if inverse doesn't exist
     * Time Complexity: O(log m)
     */
    public static long modInverse(long a, long m) {
        long[] result = extendedGCD(a, m);
        long gcd = result[0];
        long x = result[1];
        
        if (gcd != 1) {
            return -1; // Inverse doesn't exist
        }
        
        return (x % m + m) % m; // Ensure positive result
    }
    
    /**
     * Modular inverse using Fermat's Little Theorem (when m is prime)
     * a^(-1) ≡ a^(m-2) (mod m)
     * Time Complexity: O(log m)
     */
    public static long modInverseFermat(long a, long m) {
        return modPowerFast(a, m - 2, m);
    }
    
    // ==================== POWER CALCULATION ====================
    
    /**
     * Fast exponentiation using binary method (iterative)
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public static long powerFast(long base, long exp) {
        long result = 1;
        
        while (exp > 0) {
            // If exp is odd, multiply base with result
            if (exp % 2 == 1) {
                result *= base;
            }
            
            // Square the base and halve the exponent
            base *= base;
            exp /= 2;
        }
        
        return result;
    }
    
    /**
     * Modular exponentiation
     * Computes (base^exp) % mod efficiently
     * Time Complexity: O(log exp)
     */
    public static long modPowerFast(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        
        return result;
    }
    
    /**
     * Recursive fast exponentiation
     * Time Complexity: O(log n)
     * Space Complexity: O(log n)
     */
    public static long powerRecursive(long base, long exp) {
        if (exp == 0) return 1;
        if (exp == 1) return base;
        
        long half = powerRecursive(base, exp / 2);
        
        if (exp % 2 == 0) {
            return half * half;
        } else {
            return base * half * half;
        }
    }
    
    // ==================== FACTORIAL ====================
    
    /**
     * Simple factorial computation
     * Time Complexity: O(n)
     * Note: Overflows quickly, use only for small n
     */
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative factorial undefined");
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    /**
     * Factorial with modular arithmetic
     * Time Complexity: O(n)
     */
    public static long factorialMod(int n, long mod) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = (result * i) % mod;
        }
        return result;
    }
    
    /**
     * Count trailing zeros in n!
     * Number of times 10 divides n! = min(factors of 2, factors of 5)
     * Since factors of 5 are always less, count them
     * Time Complexity: O(log n)
     */
    public static int trailingZerosInFactorial(int n) {
        int count = 0;
        for (int i = 5; n / i > 0; i *= 5) {
            count += n / i;
        }
        return count;
    }
    
    /**
     * Precompute factorials up to n for repeated queries
     * Time Complexity: O(n) preprocessing, O(1) per query
     */
    public static long[] precomputeFactorials(int n, long mod) {
        long[] fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = (fact[i - 1] * i) % mod;
        }
        return fact;
    }
    
    // ==================== FIBONACCI ====================
    
    /**
     * Fibonacci using dynamic programming (space-optimized)
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static long fibonacci(int n) {
        if (n <= 1) return n;
        
        long prev2 = 0, prev1 = 1;
        long current = 0;
        
        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        
        return current;
    }
    
    /**
     * Fibonacci with modular arithmetic
     * Time Complexity: O(n)
     */
    public static long fibonacciMod(int n, long mod) {
        if (n <= 1) return n;
        
        long prev2 = 0, prev1 = 1;
        long current = 0;
        
        for (int i = 2; i <= n; i++) {
            current = (prev1 + prev2) % mod;
            prev2 = prev1;
            prev1 = current;
        }
        
        return current;
    }
    
    /**
     * Matrix exponentiation for Fibonacci
     * F(n) = [[1,1],[1,0]]^n
     * Time Complexity: O(log n)
     */
    public static long fibonacciMatrix(int n) {
        if (n <= 1) return n;
        
        long[][] base = {{1, 1}, {1, 0}};
        long[][] result = matrixPower(base, n - 1);
        
        return result[0][0];
    }
    
    /**
     * Matrix multiplication for 2x2 matrices
     */
    private static long[][] matrixMultiply(long[][] a, long[][] b) {
        long[][] result = new long[2][2];
        result[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        result[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        result[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        result[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        return result;
    }
    
    /**
     * Matrix exponentiation using fast exponentiation
     */
    private static long[][] matrixPower(long[][] matrix, int n) {
        long[][] result = {{1, 0}, {0, 1}}; // Identity matrix
        long[][] base = matrix;
        
        while (n > 0) {
            if (n % 2 == 1) {
                result = matrixMultiply(result, base);
            }
            base = matrixMultiply(base, base);
            n /= 2;
        }
        
        return result;
    }
    
    // ==================== COMBINATORICS ====================
    
    /**
     * Binomial coefficient C(n, k) using Pascal's triangle
     * Time Complexity: O(n*k)
     * Space Complexity: O(k)
     */
    public static long binomialCoefficient(int n, int k) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;
        
        // Optimize: C(n,k) = C(n, n-k)
        k = Math.min(k, n - k);
        
        long[] dp = new long[k + 1];
        dp[0] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int j = Math.min(i, k); j > 0; j--) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        
        return dp[k];
    }
    
    /**
     * Binomial coefficient with modular arithmetic
     * Time Complexity: O(n*k)
     */
    public static long binomialCoefficientMod(int n, int k, long mod) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;
        
        k = Math.min(k, n - k);
        
        long[] dp = new long[k + 1];
        dp[0] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int j = Math.min(i, k); j > 0; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % mod;
            }
        }
        
        return dp[k];
    }
    
    /**
     * Binomial coefficient using multiplicative formula
     * C(n, k) = n! / (k! * (n-k)!)
     * Time Complexity: O(k)
     */
    public static long binomialCoefficientMultiplicative(int n, int k) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;
        
        k = Math.min(k, n - k);
        
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        
        return result;
    }
    
    /**
     * Catalan number C(n) = C(2n, n) / (n + 1)
     * Time Complexity: O(n)
     */
    public static long catalanNumber(int n) {
        long c = binomialCoefficient(2 * n, n);
        return c / (n + 1);
    }
    
    /**
     * Catalan number using dynamic programming
     * C(n) = sum(C(i) * C(n-1-i)) for i from 0 to n-1
     * Time Complexity: O(n^2)
     */
    public static long catalanNumberDP(int n) {
        long[] catalan = new long[n + 1];
        catalan[0] = catalan[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - 1 - j];
            }
        }
        
        return catalan[n];
    }
    
    // ==================== NUMBER THEORY ====================
    
    /**
     * Euler's Totient Function φ(n)
     * Counts numbers from 1 to n that are coprime to n
     * Time Complexity: O(√n)
     */
    public static int eulerTotient(int n) {
        int result = n;
        
        // Process factor 2
        if (n % 2 == 0) {
            while (n % 2 == 0) {
                n /= 2;
            }
            result -= result / 2;
        }
        
        // Process odd factors
        for (int p = 3; p * p <= n; p += 2) {
            if (n % p == 0) {
                while (n % p == 0) {
                    n /= p;
                }
                result -= result / p;
            }
        }
        
        // If n is still > 1, it's a prime factor
        if (n > 1) {
            result -= result / n;
        }
        
        return result;
    }
    
    /**
     * Compute Euler's Totient for all numbers up to n
     * Using sieve-like approach
     * Time Complexity: O(n log log n)
     */
    public static int[] eulerTotientSieve(int n) {
        int[] phi = new int[n + 1];
        
        // Initialize with φ(i) = i
        for (int i = 1; i <= n; i++) {
            phi[i] = i;
        }
        
        // Apply formula φ(n) = n * (1 - 1/p1) * (1 - 1/p2) * ...
        for (int p = 2; p <= n; p++) {
            if (phi[p] == p) { // p is prime
                for (int i = p; i <= n; i += p) {
                    phi[i] -= phi[i] / p;
                }
            }
        }
        
        return phi;
    }
    
    /**
     * Chinese Remainder Theorem
     * Solve system: x ≡ a[i] (mod m[i]) for all i
     * Requires all m[i] to be pairwise coprime
     */
    public static long chineseRemainderTheorem(long[] a, long[] m) {
        long prod = 1;
        for (long mod : m) {
            prod *= mod;
        }
        
        long result = 0;
        for (int i = 0; i < a.length; i++) {
            long pp = prod / m[i];
            long inv = modInverse(pp, m[i]);
            result = (result + a[i] * pp % prod * inv % prod) % prod;
        }
        
        return (result + prod) % prod;
    }
    
    /**
     * Check if n is a perfect square
     * Time Complexity: O(1) using integer square root
     */
    public static boolean isPerfectSquare(long n) {
        if (n < 0) return false;
        long sqrt = (long) Math.sqrt(n);
        return sqrt * sqrt == n;
    }
    
    /**
     * Sum of divisors of n
     * Time Complexity: O(√n)
     */
    public static long sumOfDivisors(long n) {
        long sum = 0;
        for (long i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                sum += i;
                if (i != n / i) {
                    sum += n / i;
                }
            }
        }
        return sum;
    }
    
    /**
     * Count divisors of n
     * Time Complexity: O(√n)
     */
    public static int countDivisors(long n) {
        int count = 0;
        for (long i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                count++;
                if (i != n / i) {
                    count++;
                }
            }
        }
        return count;
    }
    
    // ==================== DEMONSTRATION AND TESTING ====================
    
    public static void main(String[] args) {
        System.out.println("=== Mathematical Algorithms Demonstration ===\n");
        
        // GCD and LCM
        System.out.println("1. GCD and LCM:");
        System.out.println("   GCD(48, 18) = " + gcd(48, 18));
        System.out.println("   LCM(48, 18) = " + lcm(48, 18));
        long[] extGcd = extendedGCD(48, 18);
        System.out.println("   Extended GCD: " + extGcd[0] + " = 48*" + extGcd[1] + " + 18*" + extGcd[2]);
        
        // Prime Numbers
        System.out.println("\n2. Prime Numbers:");
        boolean[] primes = sieveOfEratosthenes(50);
        System.out.print("   Primes up to 50: ");
        for (int i = 2; i <= 50; i++) {
            if (primes[i]) System.out.print(i + " ");
        }
        System.out.println("\n   Is 97 prime? " + isPrime(97));
        System.out.println("   Prime factors of 60: " + primeFactors(60));
        
        // Modular Arithmetic
        System.out.println("\n3. Modular Arithmetic:");
        System.out.println("   (123456 + 789012) mod 1000 = " + modAdd(123456, 789012, 1000));
        System.out.println("   (123456 * 789012) mod 1000 = " + modMul(123456, 789012, 1000));
        System.out.println("   Modular inverse of 3 mod 11 = " + modInverse(3, 11));
        System.out.println("   Verification: (3 * " + modInverse(3, 11) + ") mod 11 = " 
                          + (3 * modInverse(3, 11)) % 11);
        
        // Power Calculation
        System.out.println("\n4. Power Calculation:");
        System.out.println("   2^10 = " + powerFast(2, 10));
        System.out.println("   (2^100) mod 1000 = " + modPowerFast(2, 100, 1000));
        
        // Factorial
        System.out.println("\n5. Factorial:");
        System.out.println("   10! = " + factorial(10));
        System.out.println("   20! mod 1000000007 = " + factorialMod(20, MOD));
        System.out.println("   Trailing zeros in 100! = " + trailingZerosInFactorial(100));
        
        // Fibonacci
        System.out.println("\n6. Fibonacci Numbers:");
        System.out.print("   First 10 Fibonacci: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println("\n   F(50) using matrix method = " + fibonacciMatrix(50));
        
        // Combinatorics
        System.out.println("\n7. Combinatorics:");
        System.out.println("   C(10, 3) = " + binomialCoefficient(10, 3));
        System.out.println("   C(100, 50) = " + binomialCoefficientMultiplicative(100, 50));
        System.out.print("   First 7 Catalan numbers: ");
        for (int i = 0; i <= 6; i++) {
            System.out.print(catalanNumber(i) + " ");
        }
        
        // Number Theory
        System.out.println("\n\n8. Number Theory:");
        System.out.println("   Euler's Totient φ(36) = " + eulerTotient(36));
        System.out.println("   Sum of divisors of 28 = " + sumOfDivisors(28));
        System.out.println("   Count of divisors of 100 = " + countDivisors(100));
        System.out.println("   Is 144 perfect square? " + isPerfectSquare(144));
        
        // Chinese Remainder Theorem
        System.out.println("\n9. Chinese Remainder Theorem:");
        long[] a = {2, 3, 2};
        long[] m = {3, 5, 7};
        System.out.println("   Solving: x ≡ 2 (mod 3), x ≡ 3 (mod 5), x ≡ 2 (mod 7)");
        System.out.println("   Solution: x = " + chineseRemainderTheorem(a, m));
        
        System.out.println("\n=== End of Demonstration ===");
    }
}
```

## Complexity Summary

| Algorithm | Time Complexity | Space Complexity | Use Case |
|-----------|----------------|------------------|----------|
| Euclidean GCD | O(log min(a,b)) | O(1) | Finding common divisors |
| Sieve of Eratosthenes | O(n log log n) | O(n) | Finding all primes up to n |
| Prime Check | O(√n) | O(1) | Single prime verification |
| Fast Exponentiation | O(log n) | O(1) | Large power calculations |
| Modular Inverse | O(log m) | O(1) | Cryptography, modular division |
| Fibonacci (DP) | O(n) | O(1) | Computing sequence |
| Fibonacci (Matrix) | O(log n) | O(1) | Large n values |
| Binomial Coefficient | O(n*k) | O(k) | Combinations |
| Euler's Totient | O(√n) | O(1) | Counting coprimes |
| Totient Sieve | O(n log log n) | O(n) | Multiple totient values |

## Practical Applications

### Cryptography
- **RSA Encryption:** Uses modular exponentiation, prime generation, Euler's totient
- **Diffie-Hellman:** Relies on modular exponentiation and discrete logarithm problem
- **Hash Functions:** Employ modular arithmetic for uniform distribution

### Competitive Programming
- **Counting Problems:** Combinatorics for counting arrangements and selections
- **Game Theory:** GCD for Nim-like games, Fibonacci for ladder problems
- **Optimization:** Prime factorization for reducing fractions, finding patterns

### Computer Science
- **Algorithm Analysis:** Complexity theory uses logarithms, factorials
- **Data Structures:** Hash tables use modular arithmetic
- **Graphics:** Bezier curves use binomial coefficients

### Real-World Systems
- **Scheduling:** LCM for finding recurring patterns (bus schedules, task synchronization)
- **Music Theory:** GCD/LCM for rhythm analysis and harmony
- **Financial Systems:** Compound interest uses exponentiation

## Practice Problems

1. **Easy:**
   - Find GCD of array elements
   - Count primes in range using sieve
   - Compute power with modulo
   - Generate first n Fibonacci numbers

2. **Medium:**
   - Implement modular multiplicative inverse
   - Compute nCr mod p for large n
   - Find nth Catalan number
   - Solve linear congruences using CRT

3. **Hard:**
   - Segmented sieve for very large ranges
   - Matrix exponentiation for linear recurrences
   - Prime factorization in logarithmic time (Pollard's rho)
   - Compute factorial mod p^k

## Conclusion

Mathematical algorithms provide elegant solutions to computational problems by leveraging mathematical properties and theorems. Mastering these algorithms requires understanding both the underlying mathematics and efficient implementation techniques. The algorithms presented here form the foundation for advanced topics in number theory, combinatorics, and cryptography, making them essential for any serious programmer or computer scientist.

---

**Next Steps:**
- Implement optimization techniques for very large numbers (arbitrary precision)
- Study advanced topics: Miller-Rabin primality test, Pollard's rho factorization
- Explore applications in cryptographic protocols
- Practice competitive programming problems involving these algorithms
