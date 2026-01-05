# Coin Change - Greedy Approach

## Problem Statement

The coin change problem is a classic optimization problem where you need to:
- **Given**: A set of coin denominations and a target amount
- **Find**: The **minimum number of coins** needed to make the target amount

This is one of the most important problems that demonstrates the difference between greedy and dynamic programming approaches.

### Problem Constraints
1. You have an unlimited supply of each coin denomination
2. You must make exactly the target amount
3. Goal is to minimize the number of coins used
4. Not all coin systems have a greedy solution that is optimal

### Problem Example
```
Coin Denominations: [1, 5, 10, 25]
Target Amount: 41

Greedy Solution: 25 + 10 + 5 + 1 = 41 (4 coins)
This is optimal for standard US coins but not always for arbitrary denominations!

Counterexample:
Denominations: [1, 3, 4]
Target: 6
  Greedy: 4 + 1 + 1 = 6 (3 coins)
  Optimal: 3 + 3 = 6 (2 coins)
```

## Greedy Choice Property

The greedy strategy for coin change is **not always optimal** for arbitrary coin systems.

### Greedy Strategy
1. **Sort coins in descending order**
2. **For each coin**, use as many as possible without exceeding the remaining amount
3. **Continue** until the target amount is reached

### When Greedy Works
The greedy approach is **guaranteed optimal** only for certain coin systems called **canonical coin systems**:
- Standard currency systems (US coins: 1, 5, 10, 25)
- Most real-world currency systems
- Systems that satisfy specific mathematical properties

### When Greedy Fails
```
Denominations: [1, 3, 4]
Target: 6
Greedy tries: 4 (remaining=2) → 1 (remaining=1) → 1 (remaining=0)
Result: 3 coins (4, 1, 1)
Optimal: 2 coins (3, 3)
```

### Greedy vs Dynamic Programming
- **Greedy Approach**: O(n × amount) time, O(1) space, **not optimal for all cases**
- **Dynamic Programming**: O(n × amount) time, O(amount) space, **always optimal**

## Java Implementation

### Coin Information Class

```java
/**
 * Represents a coin denomination with count
 */
public class Coin implements Comparable<Coin> {
    public int denomination;
    public int count;
    
    public Coin(int denomination) {
        this.denomination = denomination;
        this.count = 0;
    }
    
    public Coin(int denomination, int count) {
        this.denomination = denomination;
        this.count = count;
    }
    
    // Sort by denomination in descending order
    @Override
    public int compareTo(Coin other) {
        return other.denomination - this.denomination;
    }
    
    @Override
    public String toString() {
        return String.format("Coin(denom=%d, count=%d)", 
                           denomination, count);
    }
}
```

```java
/**
 * Result class to store coin change solution
 */
public class CoinChangeResult {
    public List<Coin> coinsUsed;
    public int totalCoins;
    public int amount;
    public int[] denominations;
    
    public CoinChangeResult(List<Coin> coinsUsed, int totalCoins, 
                           int amount, int[] denominations) {
        this.coinsUsed = coinsUsed;
        this.totalCoins = totalCoins;
        this.amount = amount;
        this.denominations = denominations;
    }
    
    public void printResult() {
        System.out.println("\n--- Coin Change Result ---");
        System.out.println("Target Amount: " + amount);
        System.out.println("Total Coins: " + totalCoins);
        System.out.println("\nCoins Used:");
        for (Coin coin : coinsUsed) {
            if (coin.count > 0) {
                System.out.println("  - " + coin.denomination + " × " + 
                                 coin.count + " = " + 
                                 (coin.denomination * coin.count));
            }
        }
    }
}
```

### Greedy Algorithm Implementation

```java
public class CoinChangeGreedy {
    
    /**
     * Solves coin change using greedy approach
     * WARNING: Only optimal for canonical coin systems
     * Time Complexity: O(n log n + n × amount)
     * Space Complexity: O(n)
     */
    public static CoinChangeResult makeChange(int[] denominations, 
                                               int amount) {
        if (amount == 0) {
            return new CoinChangeResult(new ArrayList<>(), 0, 0, denominations);
        }
        
        if (denominations == null || denominations.length == 0) {
            return null;  // Cannot make change
        }
        
        // Create Coin objects and sort in descending order
        Coin[] coins = new Coin[denominations.length];
        for (int i = 0; i < denominations.length; i++) {
            coins[i] = new Coin(denominations[i]);
        }
        Arrays.sort(coins);
        
        List<Coin> result = new ArrayList<>();
        int remaining = amount;
        int totalCoins = 0;
        
        // Greedy approach: use largest coins first
        for (Coin coin : coins) {
            if (remaining >= coin.denomination) {
                int count = remaining / coin.denomination;
                coin.count = count;
                result.add(new Coin(coin.denomination, count));
                totalCoins += count;
                remaining = remaining % coin.denomination;
                
                // If amount is fully made, stop
                if (remaining == 0) {
                    break;
                }
            }
        }
        
        // Check if exact change can be made
        if (remaining > 0) {
            System.out.println("Cannot make exact change of " + amount);
            return null;
        }
        
        return new CoinChangeResult(result, totalCoins, amount, denominations);
    }
    
    /**
     * Detailed version with step-by-step tracking
     */
    public static CoinChangeResult makeChangeDetailed(int[] denominations, 
                                                       int amount) {
        if (amount == 0) {
            System.out.println("Amount is 0, no coins needed");
            return new CoinChangeResult(new ArrayList<>(), 0, 0, denominations);
        }
        
        Coin[] coins = new Coin[denominations.length];
        for (int i = 0; i < denominations.length; i++) {
            coins[i] = new Coin(denominations[i]);
        }
        Arrays.sort(coins);
        
        System.out.println("\nGreedy Coin Change Process:");
        System.out.println("Target Amount: " + amount);
        System.out.println("Sorted Coins: " + Arrays.toString(coins));
        System.out.println("\nStep-by-Step:");
        
        List<Coin> result = new ArrayList<>();
        int remaining = amount;
        int totalCoins = 0;
        int step = 1;
        
        for (Coin coin : coins) {
            if (remaining >= coin.denomination) {
                int count = remaining / coin.denomination;
                coin.count = count;
                result.add(new Coin(coin.denomination, count));
                totalCoins += count;
                
                System.out.printf("Step %d: Use %d × %d = %d (Remaining: %d)\n",
                    step++, count, coin.denomination, 
                    count * coin.denomination, remaining % coin.denomination);
                
                remaining = remaining % coin.denomination;
                
                if (remaining == 0) {
                    System.out.println("✓ Target amount reached!");
                    break;
                }
            }
        }
        
        if (remaining > 0) {
            System.out.println("✗ Cannot make exact change. Remaining: " + remaining);
            return null;
        }
        
        return new CoinChangeResult(result, totalCoins, amount, denominations);
    }
}
```

### Dynamic Programming Implementation (Optimal Solution)

```java
public class CoinChangeDP {
    
    /**
     * Optimal solution using Dynamic Programming
     * Works for ALL coin systems
     * Time Complexity: O(n × amount)
     * Space Complexity: O(amount)
     */
    public static CoinChangeResult makeChangeDP(int[] denominations, 
                                                 int amount) {
        if (amount == 0) {
            return new CoinChangeResult(new ArrayList<>(), 0, 0, denominations);
        }
        
        // dp[i] = minimum coins needed to make amount i
        int[] dp = new int[amount + 1];
        int[] parent = new int[amount + 1];  // Track which coin was used
        
        // Initialize
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        // Fill DP table
        for (int i = 1; i <= amount; i++) {
            for (int coin : denominations) {
                if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
                    if (dp[i - coin] + 1 < dp[i]) {
                        dp[i] = dp[i - coin] + 1;
                        parent[i] = coin;  // Track which coin gives optimal solution
                    }
                }
            }
        }
        
        // Check if solution exists
        if (dp[amount] == Integer.MAX_VALUE) {
            System.out.println("Cannot make exact change of " + amount);
            return null;
        }
        
        // Reconstruct solution
        List<Coin> result = new ArrayList<>();
        int current = amount;
        
        while (current > 0) {
            int coin = parent[current];
            
            // Find or create coin in result
            boolean found = false;
            for (Coin c : result) {
                if (c.denomination == coin) {
                    c.count++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(new Coin(coin, 1));
            }
            
            current -= coin;
        }
        
        // Sort result by denomination
        result.sort((c1, c2) -> c2.denomination - c1.denomination);
        
        return new CoinChangeResult(result, dp[amount], amount, denominations);
    }
}
```

### Comparison Utility Class

```java
public class CoinChangeComparator {
    
    /**
     * Compare greedy and DP solutions
     */
    public static void compareApproaches(int[] denominations, int amount) {
        System.out.println("\n========================================");
        System.out.println("Comparing Greedy vs DP for Coin Change");
        System.out.println("========================================");
        System.out.println("Denominations: " + Arrays.toString(denominations));
        System.out.println("Target Amount: " + amount);
        
        // Greedy approach
        System.out.println("\n--- GREEDY APPROACH ---");
        CoinChangeResult greedyResult = CoinChangeGreedy.makeChange(denominations, amount);
        if (greedyResult != null) {
            greedyResult.printResult();
            System.out.println("Total Coins (Greedy): " + greedyResult.totalCoins);
        }
        
        // DP approach
        System.out.println("\n--- DYNAMIC PROGRAMMING APPROACH ---");
        CoinChangeResult dpResult = CoinChangeDP.makeChangeDP(denominations, amount);
        if (dpResult != null) {
            dpResult.printResult();
            System.out.println("Total Coins (DP): " + dpResult.totalCoins);
        }
        
        // Comparison
        if (greedyResult != null && dpResult != null) {
            System.out.println("\n--- COMPARISON ---");
            System.out.println("Greedy Coins: " + greedyResult.totalCoins);
            System.out.println("DP Coins: " + dpResult.totalCoins);
            if (greedyResult.totalCoins == dpResult.totalCoins) {
                System.out.println("✓ Greedy solution is OPTIMAL for this coin system");
            } else {
                System.out.println("✗ Greedy solution is NOT optimal");
                System.out.println("Difference: " + 
                    (greedyResult.totalCoins - dpResult.totalCoins) + " extra coins");
            }
        }
    }
}
```

### Complete Test Driver

```java
public class CoinChangeDemo {
    
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("Coin Change Problem");
        System.out.println("===================================");
        
        // Test Case 1: US Coins (canonical system)
        testCase1();
        
        // Test Case 2: Non-canonical system where greedy fails
        testCase2();
        
        // Test Case 3: European coins
        testCase3();
        
        // Test Case 4: Custom denominations
        testCase4();
        
        // Test Case 5: Edge case
        testCase5();
    }
    
    private static void testCase1() {
        System.out.println("\n--- Test Case 1: US Coins (Canonical) ---");
        int[] coins = {1, 5, 10, 25};
        int amount = 41;
        CoinChangeComparator.compareApproaches(coins, amount);
    }
    
    private static void testCase2() {
        System.out.println("\n--- Test Case 2: Non-Canonical (Greedy Fails) ---");
        int[] coins = {1, 3, 4};
        int amount = 6;
        CoinChangeComparator.compareApproaches(coins, amount);
    }
    
    private static void testCase3() {
        System.out.println("\n--- Test Case 3: European Coins ---");
        int[] coins = {1, 2, 5, 10, 20, 50, 100, 200};
        int amount = 237;
        CoinChangeComparator.compareApproaches(coins, amount);
    }
    
    private static void testCase4() {
        System.out.println("\n--- Test Case 4: Custom Denominations ---");
        int[] coins = {1, 5, 10, 21, 25};
        int amount = 63;
        CoinChangeComparator.compareApproaches(coins, amount);
    }
    
    private static void testCase5() {
        System.out.println("\n--- Test Case 5: Zero Amount ---");
        int[] coins = {1, 5, 10, 25};
        int amount = 0;
        System.out.println("Denominations: " + Arrays.toString(coins));
        System.out.println("Target Amount: " + amount);
        CoinChangeResult result = CoinChangeDP.makeChangeDP(coins, amount);
        System.out.println("Result: " + (result.totalCoins == 0 ? 
                           "0 coins needed" : "Invalid"));
    }
}
```

## Complexity Analysis

### Greedy Approach
- **Time Complexity**: O(n log n + n × amount) where n is number of denominations
  - O(n log n) for sorting
  - O(n) for making change (at most n iterations)
- **Space Complexity**: O(n) for storing coins

### Dynamic Programming Approach
- **Time Complexity**: O(n × amount) where n is number of denominations
  - Must fill entire DP table
- **Space Complexity**: O(amount) for DP array

## Important Notes

### Canonical Coin Systems
A coin system is **canonical** if the greedy algorithm always gives the optimal solution:
- US coins: {1, 5, 10, 25} ✓
- Euro coins: {1, 2, 5, 10, 20, 50, 100, 200} ✓
- {1, 5, 10, 20} ✓

### Non-Canonical Systems
- {1, 3, 4} ✗ - fails for amount 6
- {1, 3, 5} ✗ - fails for amount 9 (greedy: 5+3+1=3 coins, optimal: 3+3+3=3 coins)
- {1, 4, 6} ✗ - fails for amount 8 (greedy: 6+1+1=3 coins, optimal: 4+4=2 coins)

## Key Insights

1. **Greedy Not Always Optimal**: The greedy approach only works for specific coin systems
2. **DP Always Optimal**: Dynamic programming guarantees optimal solution for all cases
3. **Canonical Property**: Real-world currencies are designed to be canonical
4. **Trade-off**: Greedy is faster but less general; DP is slower but always correct

## Related Problems

- **Unbounded Knapsack**: Similar DP formulation
- **Longest Increasing Subsequence**: Different DP application
- **Minimum Path Sum**: Another DP classic
- **Climbing Stairs**: Similar recursive problem

## References

- GeeksforGeeks: Coin Change Problem
- Introduction to Algorithms (CLRS)
- LeetCode: Coin Change and Coin Change 2
- Algorithm Design Manual - Greedy Algorithms
