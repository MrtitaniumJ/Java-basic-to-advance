/**
 * Dynamic Programming Problems Implementation
 * 
 * Demonstrates classic DP problems:
 * - Longest Common Subsequence (LCS)
 * - 0/1 Knapsack Problem
 * - Coin Change Problem
 * - Fibonacci Sequence
 * - Longest Increasing Subsequence (LIS)
 */

import java.util.*;

public class DynamicProgrammingProblems {
    
    // ==================== LONGEST COMMON SUBSEQUENCE ====================
    /**
     * Find longest common subsequence of two strings
     * 
     * Example: "AGGTAB" and "GXTXAYB" → "GTAB" (length 4)
     * 
     * Approach: 2D DP table where dp[i][j] = LCS length of first i chars of s1
     *           and first j chars of s2
     * 
     * Time: O(m*n) where m,n are string lengths
     * Space: O(m*n)
     */
    static class LCS {
        
        public static int findLCSLength(String s1, String s2) {
            int m = s1.length();
            int n = s2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            // Build DP table
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            return dp[m][n];
        }
        
        public static String findLCS(String s1, String s2) {
            int m = s1.length();
            int n = s2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            // Build DP table
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            // Backtrack to find actual LCS
            StringBuilder lcs = new StringBuilder();
            int i = m, j = n;
            
            while (i > 0 && j > 0) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    lcs.insert(0, s1.charAt(i - 1));
                    i--;
                    j--;
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
            
            return lcs.toString();
        }
    }
    
    // ==================== 0/1 KNAPSACK PROBLEM ====================
    /**
     * Find maximum value that fits in knapsack with weight limit
     * 
     * Example: weights=[2,3,4,5], values=[3,4,5,6], capacity=5
     *          → max value = 10 (items 1 and 3)
     * 
     * Approach: dp[i][w] = max value using first i items with weight limit w
     * 
     * Time: O(n*W) where n is items, W is capacity
     * Space: O(n*W)
     */
    static class Knapsack {
        
        static class Item {
            int weight;
            int value;
            
            Item(int weight, int value) {
                this.weight = weight;
                this.value = value;
            }
        }
        
        public static int solve(Item[] items, int capacity) {
            int n = items.length;
            int[][] dp = new int[n + 1][capacity + 1];
            
            // Build DP table
            for (int i = 1; i <= n; i++) {
                for (int w = 1; w <= capacity; w++) {
                    // Option 1: Don't take item i-1
                    int exclude = dp[i - 1][w];
                    
                    // Option 2: Take item i-1 (if it fits)
                    int include = 0;
                    if (items[i - 1].weight <= w) {
                        include = items[i - 1].value + dp[i - 1][w - items[i - 1].weight];
                    }
                    
                    dp[i][w] = Math.max(include, exclude);
                }
            }
            
            return dp[n][capacity];
        }
        
        // Space-optimized version O(W) space
        public static int solveOptimized(Item[] items, int capacity) {
            int[] dp = new int[capacity + 1];
            
            for (Item item : items) {
                for (int w = capacity; w >= item.weight; w--) {
                    dp[w] = Math.max(dp[w], dp[w - item.weight] + item.value);
                }
            }
            
            return dp[capacity];
        }
        
        // Find which items to include
        public static List<Item> findItems(Item[] items, int capacity) {
            int n = items.length;
            int[][] dp = new int[n + 1][capacity + 1];
            
            // Build DP table
            for (int i = 1; i <= n; i++) {
                for (int w = 1; w <= capacity; w++) {
                    int exclude = dp[i - 1][w];
                    int include = 0;
                    if (items[i - 1].weight <= w) {
                        include = items[i - 1].value + dp[i - 1][w - items[i - 1].weight];
                    }
                    dp[i][w] = Math.max(include, exclude);
                }
            }
            
            // Backtrack to find items
            List<Item> selected = new ArrayList<>();
            int i = n, w = capacity;
            
            while (i > 0 && w > 0) {
                if (dp[i][w] != dp[i - 1][w]) {
                    selected.add(items[i - 1]);
                    w -= items[i - 1].weight;
                }
                i--;
            }
            
            return selected;
        }
    }
    
    // ==================== COIN CHANGE PROBLEM ====================
    /**
     * Find minimum coins needed to make amount
     * 
     * Example: coins=[1,2,5], amount=5 → 1 coin (5)
     *          coins=[2], amount=3 → impossible (-1)
     * 
     * Approach: dp[i] = minimum coins needed for amount i
     * 
     * Time: O(amount * n) where n is number of coins
     * Space: O(amount)
     */
    static class CoinChange {
        
        public static int minCoins(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            
            for (int i = 1; i <= amount; i++) {
                for (int coin : coins) {
                    if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                    }
                }
            }
            
            return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
        }
        
        // Find actual coins used
        public static List<Integer> findCoins(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            int[] parent = new int[amount + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            
            for (int i = 1; i <= amount; i++) {
                for (int coin : coins) {
                    if (coin <= i && dp[i - coin] != Integer.MAX_VALUE &&
                        dp[i - coin] + 1 < dp[i]) {
                        dp[i] = dp[i - coin] + 1;
                        parent[i] = coin;
                    }
                }
            }
            
            List<Integer> result = new ArrayList<>();
            if (dp[amount] == Integer.MAX_VALUE) {
                return result;
            }
            
            int current = amount;
            while (current > 0) {
                int coin = parent[current];
                result.add(coin);
                current -= coin;
            }
            
            return result;
        }
        
        // Count number of ways to make amount (combinations)
        public static int countWays(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i - coin];
                }
            }
            
            return dp[amount];
        }
    }
    
    // ==================== FIBONACCI WITH MEMOIZATION ====================
    /**
     * Calculate fibonacci with memoization
     * 
     * Time: O(n)
     * Space: O(n) for memoization array
     */
    static class Fibonacci {
        
        public static long fibMemo(int n, long[] memo) {
            if (n <= 1) {
                return n;
            }
            
            if (memo[n] != -1) {
                return memo[n];
            }
            
            memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
            return memo[n];
        }
        
        public static long fibDP(int n) {
            if (n <= 1) {
                return n;
            }
            
            long[] dp = new long[n + 1];
            dp[0] = 0;
            dp[1] = 1;
            
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            
            return dp[n];
        }
        
        // Space-optimized: O(1) space
        public static long fibOptimized(int n) {
            if (n <= 1) {
                return n;
            }
            
            long prev = 0, curr = 1;
            for (int i = 2; i <= n; i++) {
                long next = prev + curr;
                prev = curr;
                curr = next;
            }
            
            return curr;
        }
    }
    
    // ==================== LONGEST INCREASING SUBSEQUENCE ====================
    /**
     * Find longest strictly increasing subsequence
     * 
     * Example: [10, 9, 2, 5, 3, 7, 101, 18] → [2, 3, 7, 101] (length 4)
     * 
     * Approach 1: dp[i] = length of LIS ending at index i - O(n²)
     * Approach 2: Binary search - O(n log n)
     * 
     * Time: O(n²) or O(n log n)
     * Space: O(n)
     */
    static class LIS {
        
        // O(n²) approach
        public static int findLISLength(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            
            int[] dp = new int[nums.length];
            Arrays.fill(dp, 1);
            
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            
            return Arrays.stream(dp).max().orElse(0);
        }
        
        // O(n log n) approach using binary search
        public static int findLISLengthOptimized(int[] nums) {
            List<Integer> tails = new ArrayList<>();
            
            for (int num : nums) {
                int pos = Collections.binarySearch(tails, num);
                if (pos < 0) {
                    pos = -(pos + 1);
                }
                
                if (pos == tails.size()) {
                    tails.add(num);
                } else {
                    tails.set(pos, num);
                }
            }
            
            return tails.size();
        }
    }
    
    // ==================== DEMONSTRATIONS ====================
    
    public static void demonstrateLCS() {
        System.out.println("\n========== LONGEST COMMON SUBSEQUENCE ==========");
        
        String[][] testCases = {
            {"AGGTAB", "GXTXAYB"},
            {"ABCDGH", "AEDFHR"},
            {"AGATACGCA", "GAGACGACGA"}
        };
        
        for (String[] test : testCases) {
            int length = LCS.findLCSLength(test[0], test[1]);
            String lcs = LCS.findLCS(test[0], test[1]);
            System.out.println("String 1: " + test[0]);
            System.out.println("String 2: " + test[1]);
            System.out.println("LCS: " + lcs + " (length: " + length + ")");
            System.out.println();
        }
    }
    
    public static void demonstrateKnapsack() {
        System.out.println("\n========== 0/1 KNAPSACK PROBLEM ==========");
        
        Knapsack.Item[] items = {
            new Knapsack.Item(2, 3),
            new Knapsack.Item(3, 4),
            new Knapsack.Item(4, 5),
            new Knapsack.Item(5, 6)
        };
        int capacity = 8;
        
        System.out.println("Items (weight, value):");
        for (Knapsack.Item item : items) {
            System.out.println("  (" + item.weight + ", " + item.value + ")");
        }
        System.out.println("Capacity: " + capacity);
        
        int maxValue = Knapsack.solve(items, capacity);
        System.out.println("Maximum value: " + maxValue);
        
        List<Knapsack.Item> selected = Knapsack.findItems(items, capacity);
        System.out.println("Selected items: " + selected.size());
        int totalWeight = 0, totalValue = 0;
        for (Knapsack.Item item : selected) {
            System.out.println("  Weight: " + item.weight + ", Value: " + item.value);
            totalWeight += item.weight;
            totalValue += item.value;
        }
        System.out.println("Total weight: " + totalWeight + ", Total value: " + totalValue);
    }
    
    public static void demonstrateCoinChange() {
        System.out.println("\n========== COIN CHANGE PROBLEM ==========");
        
        int[][] testCases = {
            {1, 2, 5},
            {2},
            {10}
        };
        int[] amounts = {5, 3, 1};
        
        for (int i = 0; i < testCases.length; i++) {
            int[] coins = testCases[i];
            int amount = amounts[i];
            
            System.out.println("Coins: " + Arrays.toString(coins));
            System.out.println("Amount: " + amount);
            
            int minCoins = CoinChange.minCoins(coins, amount);
            if (minCoins == -1) {
                System.out.println("Cannot make amount");
            } else {
                System.out.println("Minimum coins needed: " + minCoins);
                List<Integer> coinsUsed = CoinChange.findCoins(coins, amount);
                System.out.println("Coins used: " + coinsUsed);
            }
            
            int ways = CoinChange.countWays(coins, amount);
            System.out.println("Number of ways to make amount: " + ways);
            System.out.println();
        }
    }
    
    public static void demonstrateFibonacci() {
        System.out.println("\n========== FIBONACCI SEQUENCE ==========");
        
        int n = 20;
        System.out.println("Computing Fibonacci(" + n + ")");
        
        long[] memo = new long[n + 1];
        Arrays.fill(memo, -1);
        
        long memoResult = Fibonacci.fibMemo(n, memo);
        System.out.println("With Memoization: " + memoResult);
        
        long dpResult = Fibonacci.fibDP(n);
        System.out.println("With DP: " + dpResult);
        
        long optimizedResult = Fibonacci.fibOptimized(n);
        System.out.println("Space-Optimized: " + optimizedResult);
        
        System.out.println("\nFibonacci sequence (first 10):");
        for (int i = 0; i < 10; i++) {
            System.out.print(Fibonacci.fibOptimized(i) + " ");
        }
        System.out.println();
    }
    
    public static void demonstrateLIS() {
        System.out.println("\n========== LONGEST INCREASING SUBSEQUENCE ==========");
        
        int[][] testCases = {
            {10, 9, 2, 5, 3, 7, 101, 18},
            {0, 1, 0, 4, 4, 4, 3, 5, 1},
            {3, 10, 2, 1, 20}
        };
        
        for (int[] nums : testCases) {
            System.out.println("Array: " + Arrays.toString(nums));
            int length1 = LIS.findLISLength(nums);
            int length2 = LIS.findLISLengthOptimized(nums);
            System.out.println("LIS Length (O(n²)): " + length1);
            System.out.println("LIS Length (O(n log n)): " + length2);
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        DYNAMIC PROGRAMMING PROBLEMS IMPLEMENTATION     ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        demonstrateLCS();
        demonstrateKnapsack();
        demonstrateCoinChange();
        demonstrateFibonacci();
        demonstrateLIS();
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("All demonstrations completed successfully!");
        System.out.println("=".repeat(55));
    }
}
