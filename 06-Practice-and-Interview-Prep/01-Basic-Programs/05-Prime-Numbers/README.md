# Prime Numbers Program

## Problem Statement

Write a Java program that:
1. Checks if a given number is prime
2. Finds all prime numbers up to a given limit

**Definition:** A prime number is a natural number greater than 1 that has exactly two factors: 1 and itself.

**Prime Numbers:** 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, ...

**Input:** A number to check, and an upper limit
**Output:** Whether the number is prime, and list of all primes up to limit

---

## Concepts Explained

### 1. **Prime Number Definition**
- Numbers greater than 1
- Divisible only by 1 and itself
- 1 is NOT prime, 2 is the only even prime
- Infinite primes exist

### 2. **Basic Prime Check - Brute Force**
```java
boolean isPrime = true;
if (num <= 1) {
    isPrime = false;
} else {
    for (int i = 2; i < num; i++) {
        if (num % i == 0) {
            isPrime = false;
            break;
        }
    }
}
```
- Check divisibility from 2 to n-1
- If any divisor found, not prime

### 3. **Optimized Prime Check - Square Root**
```java
boolean isPrime = true;
if (num <= 1) {
    isPrime = false;
} else {
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) {
            isPrime = false;
            break;
        }
    }
}
```
- Only check up to √n
- If num = a × b and a ≤ b, then a ≤ √n

### 4. **Even Further Optimization**
```java
// After checking 2 and 3, check numbers of form 6k ± 1
for (int i = 5; i * i <= num; i += 6) {
    if (num % i == 0 || num % (i + 2) == 0) {
        return false;
    }
}
```
- All primes > 3 are of form 6k ± 1
- Reduces iterations significantly

---

## Solution Breakdown

### Approach 1: Simple Check (Brute Force)
```java
boolean isPrime(int num) {
    if (num <= 1) return false;
    
    for (int i = 2; i < num; i++) {
        if (num % i == 0) {
            return false;
        }
    }
    return true;
}
```

### Approach 2: Optimized Check (Square Root)
```java
boolean isPrime(int num) {
    if (num <= 1) return false;
    if (num <= 3) return true;
    if (num % 2 == 0 || num % 3 == 0) return false;
    
    for (int i = 5; i * i <= num; i += 6) {
        if (num % i == 0 || num % (i + 2) == 0) {
            return false;
        }
    }
    return true;
}
```

### Approach 3: Sieve of Eratosthenes (Find All Primes)
```java
boolean[] isPrime = new boolean[n + 1];
for (int i = 2; i <= n; i++) {
    isPrime[i] = true;
}

for (int i = 2; i * i <= n; i++) {
    if (isPrime[i]) {
        for (int j = i * i; j <= n; j += i) {
            isPrime[j] = false;
        }
    }
}
```

---

## Algorithm

**For Single Prime Check:**
1. If num ≤ 1, return false (not prime)
2. If num = 2, return true (only even prime)
3. If num is even, return false
4. Loop from 3 to √num (checking odd numbers)
5. If any number divides num, return false
6. Otherwise, return true

**For Finding All Primes (Sieve):**
1. Create boolean array of size n+1, mark all true
2. Mark 0 and 1 as false
3. For each i from 2 to √n:
   - If i is marked prime:
     - Mark all multiples of i (i², i²+i, i²+2i, ...) as false
4. Remaining true values are primes

---

## Time & Space Complexity

| Approach | Time Complexity | Space Complexity | Notes |
|----------|-----------------|------------------|-------|
| Brute Force Check | O(n) | O(1) | Slow for large numbers |
| Optimized Check | O(√n) | O(1) | Much faster |
| Sieve of Eratosthenes | O(n log log n) | O(n) | Best for finding all primes |

**Analysis:**
- **Single check:** O(√n) is practical and efficient
- **All primes:** Sieve is optimal
- Trade-off: Sieve uses O(n) space but very fast

---

## Sample Input/Output

### Example 1: Single Number Check
```
Input:  17
Output: 17 is a PRIME number
```

### Example 2: Not Prime
```
Input:  20
Output: 20 is NOT a prime number
```

### Example 3: Find All Primes
```
Input:  30
Output: Prime numbers up to 30: 2 3 5 7 11 13 17 19 23 29
Total primes found: 10
```

### Example 4: Edge Cases
```
Input:  0 (not prime)
Input:  1 (not prime)
Input:  2 (prime - only even prime)
```

---

## Code Walkthrough

```java
// Step 1: Optimize by checking special cases
if (num <= 1) return false;      // 0, 1, negatives not prime
if (num <= 3) return true;        // 2, 3 are prime
if (num % 2 == 0) return false;   // Even numbers not prime
if (num % 3 == 0) return false;   // Divisible by 3

// Step 2: Check divisibility only up to sqrt(n)
// Using 6k±1 optimization
for (int i = 5; i * i <= num; i += 6) {
    if (num % i == 0 || num % (i + 2) == 0) {
        return false;  // Found divisor
    }
}

// Step 3: No divisors found, must be prime
return true;
```

---

## Variations and Extensions

### Variation 1: Twin Primes
```java
// Find pairs of primes that differ by 2
// Examples: (3,5), (5,7), (11,13), (17,19)
for (int i = 2; i <= n - 2; i++) {
    if (isPrime(i) && isPrime(i + 2)) {
        System.out.println("(" + i + ", " + (i + 2) + ")");
    }
}
```

### Variation 2: Prime Factorization
```java
// Find all prime factors of a number
void primeFactors(int n) {
    while (n % 2 == 0) {
        System.out.print(2 + " ");
        n /= 2;
    }
    for (int i = 3; i * i <= n; i += 2) {
        while (n % i == 0) {
            System.out.print(i + " ");
            n /= i;
        }
    }
    if (n > 2) System.out.print(n);
}
```

### Variation 3: Sieve of Eratosthenes
```java
boolean[] sieve = new boolean[n + 1];
for (int i = 2; i <= n; i++) sieve[i] = true;

for (int i = 2; i * i <= n; i++) {
    if (sieve[i]) {
        for (int j = i * i; j <= n; j += i) {
            sieve[j] = false;
        }
    }
}

// Count and display primes
int count = 0;
for (int i = 2; i <= n; i++) {
    if (sieve[i]) count++;
}
```

### Variation 4: Next Prime
```java
// Find next prime number after n
int nextPrime(int n) {
    int candidate = n + 1;
    while (!isPrime(candidate)) {
        candidate++;
    }
    return candidate;
}
```

### Variation 5: Goldbach's Conjecture
```java
// Express even number as sum of two primes
void goldbach(int n) {
    for (int i = 2; i <= n / 2; i++) {
        if (isPrime(i) && isPrime(n - i)) {
            System.out.println(n + " = " + i + " + " + (n - i));
            break;
        }
    }
}
```

---

## Challenges

### Challenge 1: Prime Factorization
Write a program that finds all prime factors of a number.

### Challenge 2: Goldbach Conjecture
Verify Goldbach's conjecture: every even number > 2 is sum of two primes.

### Challenge 3: Twin Primes
Find all twin prime pairs (primes differing by 2) up to n.

### Challenge 4: Sieve Implementation
Implement Sieve of Eratosthenes and compare performance with single check.

### Challenge 5: Prime Gaps
Find gaps between consecutive primes (largest gap up to n).

### Challenge 6: Mersenne Primes
Check if a number is a Mersenne prime (of form 2^p - 1).

---

## Common Errors and Solutions

### Error 1: Starting Loop from 2, Not Optimizing
```java
for (int i = 2; i < num; i++) {  // Slow: O(n)
    if (num % i == 0) return false;
}
```
**Solution:** Check only up to √n:
```java
for (int i = 2; i * i <= num; i++) {  // Fast: O(√n)
    if (num % i == 0) return false;
}
```

### Error 2: Forgetting Special Cases
```java
for (int i = 2; i * i <= num; i++) {
    if (num % i == 0) return false;
}
return true;  // Returns true for 1!
```
**Solution:** Check boundary first:
```java
if (num <= 1) return false;
```

### Error 3: Not Handling Edge Case 2
```java
if (num % 2 == 0) return false;  // Returns false for 2!
// Should be: if (num != 2 && num % 2 == 0)
```

### Error 4: Sieve Index Out of Bounds
```java
boolean[] sieve = new boolean[n];  // Should be n+1
sieve[n] = false;  // Index out of bounds!
```
**Solution:**
```java
boolean[] sieve = new boolean[n + 1];
```

---

## Key Takeaways

1. **√n optimization** reduces time complexity dramatically
2. **Pattern 6k±1** further optimizes prime checking
3. **Sieve of Eratosthenes** best for finding all primes
4. **Prime checking is fundamental** in cryptography
5. **Trade-offs:** Single check vs finding all primes

---

## Interesting Prime Facts

- **Only even prime:** 2
- **Mersenne primes:** Primes of form 2^p - 1
- **Perfect numbers:** Related to Mersenne primes
- **Prime theorem:** Number of primes ≈ n / ln(n)
- **Infinite primes:** Proven by Euclid (~2300 years ago)

---

## Real-World Applications

1. **Cryptography:** RSA encryption uses large primes
2. **Hash Functions:** Prime numbers in hash table sizing
3. **Random Number Generation:** Prime moduli
4. **Data Structure Sizing:** Hash tables, arrays
5. **Network Protocols:** Prime numbers in algorithms

---

## Next Steps

1. Implement and test basic prime checker
2. Optimize using √n approach
3. Implement Sieve of Eratosthenes
4. Try prime factorization challenge
5. Move on to the **Palindrome Checker**

---

**Last Updated:** January 2026
**Difficulty:** ⭐⭐ Beginner
**Estimated Time:** 20 minutes
