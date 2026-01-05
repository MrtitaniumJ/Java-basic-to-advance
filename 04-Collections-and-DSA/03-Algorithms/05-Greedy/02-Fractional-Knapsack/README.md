# Fractional Knapsack Problem

## Table of Contents
1. [Problem Statement](#problem-statement)
2. [Greedy Choice Property](#greedy-choice-property)
3. [Proof of Correctness](#proof-of-correctness)
4. [Algorithm Design](#algorithm-design)
5. [Java Implementation](#java-implementation)
6. [Complexity Analysis](#complexity-analysis)
7. [Examples and Test Cases](#examples-and-test-cases)
8. [Key Insights](#key-insights)

## Problem Statement

The **Fractional Knapsack Problem** is a variant of the classic knapsack problem where items can be divided into fractional parts. The goal is to maximize the total value of items placed in a knapsack with a fixed capacity, where portions of items can be taken.

### Formal Definition

Given:
- A knapsack of capacity $W$ (maximum weight it can hold)
- A set of $n$ items with:
  - Weight: $w_i$ for item $i$
  - Value: $v_i$ for item $i$
  - Value-to-weight ratio: $\rho_i = \frac{v_i}{w_i}$ (value density)
- Items can be taken in fractional amounts: $x_i \in [0, 1]$

Objective: Maximize total value:
$$\text{Maximize: } \sum_{i=1}^{n} x_i \cdot v_i$$

Subject to: 
$$\sum_{i=1}^{n} x_i \cdot w_i \leq W$$

where $0 \leq x_i \leq 1$ for all items.

### Problem Variants and Differences

**Fractional Knapsack** (This problem):
- Items can be divided into fractional parts
- Greedy algorithm provides optimal solution
- Time complexity: $O(n \log n)$
- Real-world analogy: Pouring liquids into containers

**0/1 Knapsack** (Different problem):
- Each item is either taken entirely or not taken
- Requires dynamic programming
- Time complexity: $O(nW)$
- Real-world analogy: Packing discrete objects

### Real-World Applications

- **Resource Allocation**: Distributing budget across projects with different value densities
- **Liquid Packing**: Filling containers with different liquid densities (water, oil, syrup)
- **Portfolio Management**: Allocating investment capital across assets with different returns
- **Cargo Loading**: Loading trucks with divisible goods (flour, sand, grain)
- **Memory Management**: Allocating cache/memory space to different processes
- **Mining Operations**: Extracting ores with different value densities from deposits

## Greedy Choice Property

### The Greedy Strategy

The optimal solution can be found using the following greedy choice: **Always select items in order of decreasing value-to-weight ratio (value density) and take them completely until the knapsack is full, then take a fraction of the next item.**

### Mathematical Intuition

For the fractional knapsack problem, value density determines the optimal selection:

$$\text{Value Density} = \frac{\text{Value}}{\text{Weight}} = \frac{v_i}{w_i}$$

This metric represents **value per unit weight**, which directly indicates how efficiently each item uses knapsack capacity.

### Why This Strategy Works

1. **Capacity Efficiency**: Items with higher value density maximize value per unit of weight used.

2. **Fractional Flexibility**: Since items can be divided, we can always fill the knapsack completely by taking fractions of items, eliminating wasted space.

3. **No Opportunity Cost**: Taking a fraction of a high-density item is never worse than leaving space unused or taking a low-density item.

4. **Local vs Global Optimality**: Greedy choice at each step leads to global optimality for fractional problems.

### Why Other Strategies Fail

- **Highest Value First**: A very valuable but heavy item might waste capacity space
- **Lowest Weight First**: Light but low-value items might not maximize total value
- **Random Selection**: Without considering value density, no guarantee of optimality

## Proof of Correctness

### Theorem
The greedy algorithm that selects items in order of decreasing value-to-weight ratio produces an optimal solution to the fractional knapsack problem.

### Proof by Contradiction

**Assume:** There exists an optimal solution $OPT$ that differs from the greedy solution $GREEDY$.

**Setup:** 
- Let items be indexed 1 to $n$ such that $\rho_1 \geq \rho_2 \geq ... \geq \rho_n$
- The greedy algorithm takes $x_1, x_2, ..., x_k$ amounts of items 1 through $k$

**Key Cases:**

**Case 1: Completely Different Selections**
If $OPT$ takes less of item 1 than $GREEDY$, then $OPT$ takes more of some item $j > 1$.
Since $\rho_1 > \rho_j$, we can increase value by trading item $j$ for item 1.

**Case 2: Exchange Argument**
Let $i$ be the first item where $OPT$ and $GREEDY$ differ in the amount taken.
- If $OPT$ takes less of item $i$ than $GREEDY$, it must take more of some item $j > i$
- Since $\rho_i > \rho_j$, replacing part of $j$ with item $i$ increases total value

**Mathematical Proof:**
If $x_i^{OPT} < x_i^{GREEDY}$ and we replace $\Delta$ weight of item $j$ with item $i$:
$$\Delta \text{Value} = \Delta(\rho_i - \rho_j) > 0$$

This contradicts the optimality of $OPT$, so our greedy solution must be optimal.

## Algorithm Design

### High-Level Algorithm

```
FRACTIONAL_KNAPSACK(items, capacity):
    1. Calculate value-to-weight ratio for each item
    2. Sort items by ratio in descending order
    3. Initialize total_value = 0, remaining_capacity = capacity
    4. For each item in sorted order:
        If item_weight <= remaining_capacity:
            Take entire item
            total_value += item_value
            remaining_capacity -= item_weight
        Else:
            Take fraction of item to fill knapsack
            total_value += (remaining_capacity / item_weight) * item_value
            remaining_capacity = 0
            Break
    5. Return total_value and selection details
```

### Algorithm Characteristics

- **Greedy Choice**: Select items by highest value-to-weight ratio
- **Optimal Substructure**: After selecting item $i$, solve knapsack with remaining capacity
- **Correctness**: Proven by exchange argument
- **Efficiency**: Sorting dominates the time complexity

## Java Implementation

### Complete Implementation with Classes

```java
import java.util.*;

/**
 * Represents an item in the knapsack problem
 */
class Item implements Comparable<Item> {
    private int id;
    private double weight;
    private double value;
    private double valueDensity; // value per unit weight
    
    /**
     * Constructor for Item
     * @param id unique identifier
     * @param weight the weight of the item
     * @param value the value of the item
     */
    public Item(int id, double weight, double value) {
        this.id = id;
        this.weight = weight;
        this.value = value;
        this.valueDensity = value / weight;
    }
    
    /**
     * Get the item ID
     * @return the ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the weight of this item
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Get the value of this item
     * @return the value
     */
    public double getValue() {
        return value;
    }
    
    /**
     * Get the value-to-weight ratio
     * This is the key metric for the greedy algorithm
     * @return the value density
     */
    public double getValueDensity() {
        return valueDensity;
    }
    
    /**
     * Compare items by value density in descending order
     * @param other the item to compare with
     * @return comparison result
     */
    @Override
    public int compareTo(Item other) {
        // Descending order: higher density comes first
        return Double.compare(other.valueDensity, this.valueDensity);
    }
    
    /**
     * String representation of the item
     * @return formatted item information
     */
    @Override
    public String toString() {
        return String.format("Item %d: weight=%.2f, value=%.2f, density=%.4f", 
            id, weight, value, valueDensity);
    }
}

/**
 * Represents a selection of an item with the fraction taken
 */
class ItemSelection {
    private Item item;
    private double fraction; // 0 to 1
    private double selectedWeight;
    private double selectedValue;
    
    /**
     * Constructor for ItemSelection
     * @param item the item being selected
     * @param fraction the fraction of item taken (0 to 1)
     */
    public ItemSelection(Item item, double fraction) {
        this.item = item;
        this.fraction = Math.min(1.0, Math.max(0.0, fraction));
        this.selectedWeight = this.fraction * item.getWeight();
        this.selectedValue = this.fraction * item.getValue();
    }
    
    /**
     * Get the selected item
     * @return the item
     */
    public Item getItem() {
        return item;
    }
    
    /**
     * Get the fraction taken
     * @return the fraction (0 to 1)
     */
    public double getFraction() {
        return fraction;
    }
    
    /**
     * Get the actual weight taken
     * @return the weight used in knapsack
     */
    public double getSelectedWeight() {
        return selectedWeight;
    }
    
    /**
     * Get the actual value obtained
     * @return the value obtained
     */
    public double getSelectedValue() {
        return selectedValue;
    }
    
    /**
     * Check if this is a partial selection
     * @return true if 0 < fraction < 1
     */
    public boolean isPartial() {
        return fraction > 0 && fraction < 1;
    }
    
    /**
     * String representation of the selection
     * @return formatted selection information
     */
    @Override
    public String toString() {
        if (isPartial()) {
            return String.format("Item %d: %.2f%% selected (%.2f weight, %.2f value)", 
                item.getId(), fraction * 100, selectedWeight, selectedValue);
        } else if (fraction > 0) {
            return String.format("Item %d: fully selected (%.2f weight, %.2f value)", 
                item.getId(), selectedWeight, selectedValue);
        } else {
            return String.format("Item %d: not selected", item.getId());
        }
    }
}

/**
 * Result of the fractional knapsack algorithm
 */
class KnapsackResult {
    private List<ItemSelection> selections;
    private double totalWeight;
    private double totalValue;
    private double capacity;
    
    /**
     * Constructor for KnapsackResult
     * @param capacity the knapsack capacity
     */
    public KnapsackResult(double capacity) {
        this.capacity = capacity;
        this.selections = new ArrayList<>();
        this.totalWeight = 0;
        this.totalValue = 0;
    }
    
    /**
     * Add a selection to the result
     * @param selection the item selection to add
     */
    public void addSelection(ItemSelection selection) {
        selections.add(selection);
        totalWeight += selection.getSelectedWeight();
        totalValue += selection.getSelectedValue();
    }
    
    /**
     * Get all selections
     * @return list of selections
     */
    public List<ItemSelection> getSelections() {
        return new ArrayList<>(selections);
    }
    
    /**
     * Get total weight used
     * @return the total weight
     */
    public double getTotalWeight() {
        return totalWeight;
    }
    
    /**
     * Get total value obtained
     * @return the total value
     */
    public double getTotalValue() {
        return totalValue;
    }
    
    /**
     * Get capacity utilization percentage
     * @return utilization as percentage
     */
    public double getUtilizationPercent() {
        return (totalWeight / capacity) * 100;
    }
    
    /**
     * Get how much capacity is remaining
     * @return remaining capacity
     */
    public double getRemainingCapacity() {
        return capacity - totalWeight;
    }
    
    /**
     * String representation of results
     * @return formatted results
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Knapsack Result ===\n");
        sb.append(String.format("Capacity: %.2f\n", capacity));
        sb.append(String.format("Total Weight: %.2f\n", totalWeight));
        sb.append(String.format("Total Value: %.2f\n", totalValue));
        sb.append(String.format("Utilization: %.2f%%\n", getUtilizationPercent()));
        sb.append(String.format("Remaining Capacity: %.2f\n\n", getRemainingCapacity()));
        
        sb.append("Selected Items:\n");
        for (ItemSelection sel : selections) {
            if (sel.getFraction() > 0) {
                sb.append("  ").append(sel).append("\n");
            }
        }
        
        return sb.toString();
    }
}

/**
 * Solver for the fractional knapsack problem
 */
class FractionalKnapsackSolver {
    
    /**
     * Solves the fractional knapsack problem
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(n) for storing items and results
     * 
     * @param items list of items to consider
     * @param capacity the knapsack capacity
     * @return KnapsackResult containing the optimal solution
     */
    public static KnapsackResult solve(List<Item> items, double capacity) {
        if (items == null || items.isEmpty() || capacity <= 0) {
            return new KnapsackResult(capacity);
        }
        
        // Create a copy and sort by value density (greedy choice)
        List<Item> sortedItems = new ArrayList<>(items);
        Collections.sort(sortedItems);
        
        KnapsackResult result = new KnapsackResult(capacity);
        double remainingCapacity = capacity;
        
        // Greedy selection: pick items in order of value density
        for (Item item : sortedItems) {
            if (remainingCapacity <= 0) {
                break;
            }
            
            if (item.getWeight() <= remainingCapacity) {
                // Take the entire item
                ItemSelection selection = new ItemSelection(item, 1.0);
                result.addSelection(selection);
                remainingCapacity -= item.getWeight();
            } else {
                // Take a fraction of the item to fill remaining capacity
                double fraction = remainingCapacity / item.getWeight();
                ItemSelection selection = new ItemSelection(item, fraction);
                result.addSelection(selection);
                remainingCapacity = 0;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * Compares greedy solution with hypothetical optimal for validation
     * @param items list of items
     * @param capacity knapsack capacity
     * @return analysis of the solution
     */
    public static Map<String, Object> analyzeSelection(List<Item> items, double capacity) {
        KnapsackResult result = solve(items, capacity);
        Map<String, Object> analysis = new LinkedHashMap<>();
        
        analysis.put("Total Items Available", items.size());
        analysis.put("Items Selected (Fully)", 
            (long) result.getSelections().stream()
                .filter(sel -> sel.getFraction() == 1.0).count());
        analysis.put("Items Selected (Partial)", 
            (long) result.getSelections().stream()
                .filter(ItemSelection::isPartial).count());
        analysis.put("Total Value Obtained", 
            String.format("%.2f", result.getTotalValue()));
        analysis.put("Capacity Utilization", 
            String.format("%.2f%%", result.getUtilizationPercent()));
        
        return analysis;
    }
}

/**
 * Demo and testing class for Fractional Knapsack
 */
public class FractionalKnapsackDemo {
    
    /**
     * Example 1: Classic fractional knapsack with clear optimal solution
     */
    public static void example1() {
        System.out.println("=== Example 1: Classic Fractional Knapsack ===\n");
        
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 10, 100));  // weight 10, value 100, density 10
        items.add(new Item(2, 20, 120));  // weight 20, value 120, density 6
        items.add(new Item(3, 30, 150));  // weight 30, value 150, density 5
        
        double capacity = 35;
        
        System.out.println("Items Available:");
        for (Item item : items) {
            System.out.println("  " + item);
        }
        System.out.println("\nKnapsack Capacity: " + capacity + "\n");
        
        KnapsackResult result = FractionalKnapsackSolver.solve(items, capacity);
        System.out.println(result);
    }
    
    /**
     * Example 2: Real-world scenario - Liquid storage
     */
    public static void example2() {
        System.out.println("=== Example 2: Liquid Storage Problem ===\n");
        
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 10, 60));   // 10L of water worth 60 units
        items.add(new Item(2, 15, 90));   // 15L of milk worth 90 units
        items.add(new Item(3, 20, 100));  // 20L of oil worth 100 units
        items.add(new Item(4, 25, 110));  // 25L of honey worth 110 units
        
        double capacity = 40; // 40 liters available
        
        System.out.println("Liquids to Store (id: volume, value):");
        for (Item item : items) {
            System.out.println("  " + item);
        }
        System.out.println("\nContainer Capacity: " + capacity + " liters\n");
        
        KnapsackResult result = FractionalKnapsackSolver.solve(items, capacity);
        System.out.println(result);
        
        Map<String, Object> analysis = FractionalKnapsackSolver.analyzeSelection(items, capacity);
        System.out.println("Analysis:");
        analysis.forEach((key, value) -> System.out.println("  " + key + ": " + value));
        System.out.println();
    }
    
    /**
     * Example 3: Portfolio allocation problem
     */
    public static void example3() {
        System.out.println("=== Example 3: Investment Portfolio ===\n");
        
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 5000, 500));    // Stock A
        items.add(new Item(2, 3000, 450));    // Stock B
        items.add(new Item(3, 4000, 500));    // Stock C
        items.add(new Item(4, 2000, 250));    // Bond D
        
        double budget = 8000; // Investment budget
        
        System.out.println("Investment Options:");
        for (Item item : items) {
            System.out.println("  " + item);
        }
        System.out.println("\nInvestment Budget: " + budget + "\n");
        
        KnapsackResult result = FractionalKnapsackSolver.solve(items, budget);
        System.out.println(result);
    }
    
    /**
     * Example 4: Edge cases
     */
    public static void example4() {
        System.out.println("=== Example 4: Edge Cases ===\n");
        
        // Single item
        System.out.println("Case 1: Single Item (Capacity > Weight)");
        List<Item> single = new ArrayList<>();
        single.add(new Item(1, 5, 50));
        KnapsackResult result1 = FractionalKnapsackSolver.solve(single, 10);
        System.out.println("Total Value: " + result1.getTotalValue() + "\n");
        
        // Capacity smaller than smallest item
        System.out.println("Case 2: Capacity < All Items");
        List<Item> items2 = new ArrayList<>();
        items2.add(new Item(1, 10, 100));
        items2.add(new Item(2, 20, 200));
        KnapsackResult result2 = FractionalKnapsackSolver.solve(items2, 5);
        System.out.println("Total Value: " + result2.getTotalValue() + "\n");
        
        // Perfect fit
        System.out.println("Case 3: Perfect Fit (No Waste)");
        List<Item> items3 = new ArrayList<>();
        items3.add(new Item(1, 5, 50));
        items3.add(new Item(2, 5, 60));
        KnapsackResult result3 = FractionalKnapsackSolver.solve(items3, 10);
        System.out.println("Total Value: " + result3.getTotalValue());
        System.out.println("Utilization: " + result3.getUtilizationPercent() + "%\n");
    }
    
    /**
     * Performance testing with large datasets
     */
    public static void performanceTest() {
        System.out.println("=== Performance Test ===\n");
        
        // Create 1000 random items
        List<Item> items = new ArrayList<>();
        Random rand = new Random(42);
        
        for (int i = 0; i < 1000; i++) {
            double weight = 1 + rand.nextDouble() * 20;
            double value = 1 + rand.nextDouble() * 100;
            items.add(new Item(i, weight, value));
        }
        
        double capacity = 5000;
        
        long startTime = System.nanoTime();
        KnapsackResult result = FractionalKnapsackSolver.solve(items, capacity);
        long endTime = System.nanoTime();
        
        System.out.println("Items: " + items.size());
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Total Value: " + String.format("%.2f", result.getTotalValue()));
        System.out.println("Capacity Utilization: " + 
            String.format("%.2f%%", result.getUtilizationPercent()) + "\n");
    }
    
    /**
     * Main method to run all examples
     */
    public static void main(String[] args) {
        example1();
        example2();
        example3();
        example4();
        performanceTest();
    }
}
```

## Complexity Analysis

### Time Complexity

$$T(n) = O(n \log n)$$

- **Sorting by value density**: $O(n \log n)$ - Dominant operation
- **Greedy selection loop**: $O(n)$ - Single pass through sorted items
- **Overall**: $O(n \log n)$ determined by sorting

### Space Complexity

$$S(n) = O(n)$$

- **Sorted items list**: $O(n)$
- **Result selections**: $O(n)$ in worst case
- **Temporary variables**: $O(1)$
- **Overall**: $O(n)$

### Comparison with 0/1 Knapsack

| Aspect | Fractional Knapsack | 0/1 Knapsack |
|--------|-------------------|-------------|
| **Greedy Optimal?** | Yes ✓ | No ✗ |
| **Time Complexity** | $O(n \log n)$ | $O(nW)$ |
| **Space Complexity** | $O(n)$ | $O(nW)$ |
| **Algorithm** | Greedy | Dynamic Programming |
| **Divisibility** | Items fractional | Items integral |

## Examples and Test Cases

### Test Case 1: Classic Example
- **Items**: (weight=10, value=100), (weight=20, value=120), (weight=30, value=150)
- **Capacity**: 35
- **Expected**: Item 1 (full), Item 2 (full), Item 3 (1/3)
- **Total Value**: 100 + 120 + 50 = 270

### Test Case 2: Liquid Storage
- **Scenario**: Storing different liquids in limited container space
- **Items**: Water (10L, 60V), Milk (15L, 90V), Oil (20L, 100V), Honey (25L, 110V)
- **Capacity**: 40L
- **Optimal**: All high-density items fully, then fraction of next best

### Test Case 3: Perfect Fit
- **Items**: (5, 50), (5, 60)
- **Capacity**: 10
- **Result**: 100% utilization, no waste

### Test Case 4: Single Item
- **Items**: (10, 100)
- **Capacity**: 20
- **Result**: One item fully selected (50% capacity used)

## Key Insights

1. **Value Density Is Critical**: The value-to-weight ratio is the sole determining factor for the greedy choice, ensuring optimal solutions.

2. **Fractional Property Is Essential**: The ability to take fractions of items is what allows the greedy algorithm to guarantee optimality. Without this property, the problem becomes NP-hard (0/1 Knapsack).

3. **Always Fills Capacity**: The greedy algorithm with fractional items always achieves 100% capacity utilization, maximizing value usage.

4. **Proof Technique**: The exchange argument used to prove correctness is applicable to many greedy problems where items have inherent values.

5. **Practical Applications**: Many real-world scenarios involve divisible resources (liquids, investments, time allocation) where this algorithm applies.

6. **Efficiency Trade-off**: While fractional knapsack is easier than 0/1 knapsack algorithmically, it's more realistic for divisible resources.

## Related Problems

- **0/1 Knapsack Problem**: Non-divisible items (requires DP)
- **Weighted Activity Selection**: Maximize profit from non-overlapping activities
- **Job Scheduling with Deadlines**: Assign tasks to maximize profit
- **Huffman Coding**: Optimal prefix-free code (similar greedy structure)
- **Interval Scheduling**: Select maximum non-overlapping intervals
