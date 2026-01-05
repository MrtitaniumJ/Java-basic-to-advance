# Activity Selection Problem

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

The **Activity Selection Problem** is a classic optimization problem in algorithm design that demonstrates the power of greedy algorithms. Given a set of activities with their start and finish times, the goal is to select the maximum number of activities that can be performed by a single person, assuming the person can only work on one activity at a time.

### Formal Definition

Given:
- A set of $n$ activities $S = \{a_1, a_2, ..., a_n\}$
- Each activity $a_i$ has:
  - Start time: $s_i$
  - Finish time: $f_i$
  - Constraint: $s_i < f_i$

Two activities $a_i$ and $a_j$ are compatible if:
$$\text{non-overlapping condition: } f_i \leq s_j \text{ or } f_j \leq s_i$$

Objective: Find a maximum-size subset $A \subseteq S$ of mutually compatible activities.

### Real-World Applications

- **Classroom Scheduling**: Scheduling multiple classes in a single classroom with minimal conflicts
- **Conference Room Booking**: Managing room reservations throughout a day
- **Machine Utilization**: Maximizing the number of jobs that can be processed on a single machine
- **Medical Facilities**: Optimizing operating room scheduling in hospitals
- **Entertainment**: Concert or event scheduling at venues with single stages

## Greedy Choice Property

### The Greedy Strategy

The optimal solution can be found using the following greedy choice: **Always select the activity that finishes earliest among the remaining activities.**

### Intuition Behind the Strategy

The greedy choice to select the activity with the earliest finish time is optimal because:

1. **Minimizes Resource Occupation**: By choosing the activity that finishes first, we leave the maximum amount of time available for subsequent activities.

2. **Maximizes Future Opportunities**: Earlier finish times create more space for selecting additional activities later.

3. **No Dominance**: There is no other activity selection that can be done "earlier" than this, ensuring we don't miss opportunities.

### Why Other Greedy Choices Fail

- **Earliest Start Time**: Selecting activities with earliest start times can lead to selecting a long activity that blocks many shorter activities.
- **Shortest Duration**: This approach doesn't account for the position of activities on the time axis and can miss optimal selections.
- **Fewest Conflicts**: Computing conflicts requires analyzing all remaining activities, making it less efficient.

## Proof of Correctness

### Theorem
The greedy algorithm that selects activities in order of earliest finish time produces an optimal solution to the activity selection problem.

### Proof by Exchange Argument

**Let:**
- $OPT$ = an optimal solution (set of mutually compatible activities)
- $GREEDY$ = the set of activities selected by our greedy algorithm
- Both sets have activities listed in order of finish time

**Base Case:**
Let $a_1$ be the activity with the earliest finish time in $OPT$. If $a_1$ is also selected by GREEDY, then both start with the same activity, and we have made progress.

**Inductive Step:**
Suppose $OPT$ does not select $a_1$ (the activity with earliest finish time), but GREEDY does. Then there exists an activity $a_1'$ in $OPT$ with finish time $f_1' \geq f_1$.

**Key Observation:** We can replace $a_1'$ with $a_1$ in $OPT$ because:
- Activity $a_1$ finishes no later than $a_1'$ (i.e., $f_1 \leq f_1'$)
- Any activity compatible with $a_1'$ is also compatible with $a_1$
- Thus, the modified set remains feasible and has the same cardinality

**Conclusion:** By repeatedly applying this exchange argument, we can transform any optimal solution into the greedy solution without decreasing the number of selected activities. Therefore, the greedy solution is optimal.

## Algorithm Design

### High-Level Algorithm

```
ACTIVITY_SELECTION(activities):
    1. Sort activities by finish time in ascending order
    2. Initialize result set with first activity
    3. Store finish time of last selected activity
    4. For each remaining activity:
        If start time >= last finish time:
            Add to result set
            Update last finish time
    5. Return result set
```

### Algorithm Characteristics

- **Greedy Choice**: Select the activity that finishes earliest
- **Optimal Substructure**: After selecting activity $i$, solve the subproblem on activities that start after $i$ finishes
- **Correctness**: Guaranteed by exchange argument proof
- **Efficiency**: Single pass after sorting

## Java Implementation

### Complete Implementation with Classes

```java
import java.util.*;

/**
 * Represents an activity with start and finish times
 */
class Activity implements Comparable<Activity> {
    private int id;
    private int startTime;
    private int finishTime;
    
    /**
     * Constructor for Activity
     * @param id unique identifier for the activity
     * @param startTime when the activity begins
     * @param finishTime when the activity ends
     */
    public Activity(int id, int startTime, int finishTime) {
        this.id = id;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
    
    /**
     * Get the activity ID
     * @return the ID of this activity
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the start time of this activity
     * @return the start time
     */
    public int getStartTime() {
        return startTime;
    }
    
    /**
     * Get the finish time of this activity
     * @return the finish time
     */
    public int getFinishTime() {
        return finishTime;
    }
    
    /**
     * Check if two activities are compatible (non-overlapping)
     * @param other the other activity to check
     * @return true if activities don't overlap, false otherwise
     */
    public boolean isCompatible(Activity other) {
        return this.finishTime <= other.startTime || 
               other.finishTime <= this.startTime;
    }
    
    /**
     * Compare activities by finish time for sorting
     * This is the key to the greedy algorithm
     * @param other the other activity to compare
     * @return comparison result based on finish times
     */
    @Override
    public int compareTo(Activity other) {
        return Integer.compare(this.finishTime, other.finishTime);
    }
    
    /**
     * String representation of the activity
     * @return formatted activity information
     */
    @Override
    public String toString() {
        return String.format("Activity %d: [%d, %d)", id, startTime, finishTime);
    }
}

/**
 * Solver for the activity selection problem using greedy algorithm
 */
class ActivitySelector {
    
    /**
     * Solves the activity selection problem
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(n) for storing results
     * 
     * @param activities list of activities to schedule
     * @return list of selected activities in order of finish time
     */
    public static List<Activity> selectActivities(List<Activity> activities) {
        if (activities == null || activities.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Create a copy to avoid modifying the original list
        List<Activity> sortedActivities = new ArrayList<>(activities);
        
        // Step 1: Sort by finish time (greedy choice)
        Collections.sort(sortedActivities);
        
        // Step 2: Initialize result with first activity
        List<Activity> selected = new ArrayList<>();
        selected.add(sortedActivities.get(0));
        int lastFinishTime = sortedActivities.get(0).getFinishTime();
        
        // Step 3: Iterate through remaining activities
        for (int i = 1; i < sortedActivities.size(); i++) {
            Activity current = sortedActivities.get(i);
            
            // If compatible with last selected activity, add it
            if (current.getStartTime() >= lastFinishTime) {
                selected.add(current);
                lastFinishTime = current.getFinishTime();
            }
        }
        
        return selected;
    }
    
    /**
     * Alternative implementation using direct finish time comparison
     * More efficient when only count is needed
     * @param activities list of activities to schedule
     * @return number of activities selected
     */
    public static int countSelectedActivities(List<Activity> activities) {
        if (activities == null || activities.isEmpty()) {
            return 0;
        }
        
        List<Activity> sortedActivities = new ArrayList<>(activities);
        Collections.sort(sortedActivities);
        
        int count = 1;
        int lastFinishTime = sortedActivities.get(0).getFinishTime();
        
        for (int i = 1; i < sortedActivities.size(); i++) {
            if (sortedActivities.get(i).getStartTime() >= lastFinishTime) {
                count++;
                lastFinishTime = sortedActivities.get(i).getFinishTime();
            }
        }
        
        return count;
    }
    
    /**
     * Gets detailed statistics about the selection
     * @param activities list of activities
     * @return map containing selection details
     */
    public static Map<String, Object> getSelectionStats(List<Activity> activities) {
        List<Activity> selected = selectActivities(activities);
        Map<String, Object> stats = new LinkedHashMap<>();
        
        stats.put("Total Activities", activities.size());
        stats.put("Selected Activities", selected.size());
        stats.put("Selection Percentage", 
            String.format("%.2f%%", (100.0 * selected.size() / activities.size())));
        
        int totalTime = 0;
        for (Activity activity : selected) {
            totalTime += (activity.getFinishTime() - activity.getStartTime());
        }
        stats.put("Total Time Used", totalTime);
        
        return stats;
    }
}

/**
 * Demo and testing class for Activity Selection
 */
public class ActivitySelectionDemo {
    
    /**
     * Example 1: Basic example with clear solution
     */
    public static void example1() {
        System.out.println("=== Example 1: Basic Activity Selection ===");
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity(1, 1, 3));
        activities.add(new Activity(2, 2, 5));
        activities.add(new Activity(3, 4, 6));
        activities.add(new Activity(4, 6, 9));
        activities.add(new Activity(5, 5, 7));
        
        System.out.println("Input Activities:");
        for (Activity a : activities) {
            System.out.println("  " + a);
        }
        
        List<Activity> selected = ActivitySelector.selectActivities(activities);
        System.out.println("\nSelected Activities:");
        for (Activity a : selected) {
            System.out.println("  " + a);
        }
        System.out.println("Total Selected: " + selected.size() + "\n");
    }
    
    /**
     * Example 2: Complex scenario with many overlapping activities
     */
    public static void example2() {
        System.out.println("=== Example 2: Complex Scheduling ===");
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity(1, 10, 20));
        activities.add(new Activity(2, 12, 25));
        activities.add(new Activity(3, 20, 30));
        activities.add(new Activity(4, 0, 6));
        activities.add(new Activity(5, 5, 10));
        activities.add(new Activity(6, 25, 40));
        activities.add(new Activity(7, 30, 35));
        
        System.out.println("Input Activities:");
        for (Activity a : activities) {
            System.out.println("  " + a);
        }
        
        List<Activity> selected = ActivitySelector.selectActivities(activities);
        System.out.println("\nSelected Activities:");
        for (Activity a : selected) {
            System.out.println("  " + a);
        }
        
        Map<String, Object> stats = ActivitySelector.getSelectionStats(activities);
        System.out.println("\nStatistics:");
        stats.forEach((key, value) -> System.out.println("  " + key + ": " + value));
        System.out.println();
    }
    
    /**
     * Example 3: Edge cases
     */
    public static void example3() {
        System.out.println("=== Example 3: Edge Cases ===");
        
        // Single activity
        System.out.println("Case 1: Single Activity");
        List<Activity> single = new ArrayList<>();
        single.add(new Activity(1, 5, 10));
        System.out.println("Selected: " + ActivitySelector.selectActivities(single).size());
        
        // All overlapping
        System.out.println("\nCase 2: All Overlapping");
        List<Activity> overlapping = new ArrayList<>();
        overlapping.add(new Activity(1, 0, 10));
        overlapping.add(new Activity(2, 2, 8));
        overlapping.add(new Activity(3, 5, 9));
        System.out.println("Selected: " + ActivitySelector.selectActivities(overlapping).size());
        
        // No overlapping
        System.out.println("\nCase 3: No Overlapping");
        List<Activity> nonOverlapping = new ArrayList<>();
        nonOverlapping.add(new Activity(1, 0, 5));
        nonOverlapping.add(new Activity(2, 5, 10));
        nonOverlapping.add(new Activity(3, 10, 15));
        System.out.println("Selected: " + ActivitySelector.selectActivities(nonOverlapping).size());
        System.out.println();
    }
    
    /**
     * Main method to run all examples
     */
    public static void main(String[] args) {
        example1();
        example2();
        example3();
    }
}
```

## Complexity Analysis

### Time Complexity

$$T(n) = O(n \log n)$$

- **Sorting step**: $O(n \log n)$ - Dominant operation
- **Selection pass**: $O(n)$ - Single iteration through sorted list
- **Overall**: $O(n \log n)$ due to sorting

### Space Complexity

$$S(n) = O(n)$$

- **Sorted list storage**: $O(n)$
- **Result list**: $O(n)$ in worst case
- **Other variables**: $O(1)$
- **Overall**: $O(n)$

### Comparison with Alternatives

| Approach | Time Complexity | Space Complexity | Optimality |
|----------|-----------------|------------------|-----------|
| **Greedy (Earliest Finish)** | $O(n \log n)$ | $O(n)$ | ✓ Optimal |
| Dynamic Programming | $O(n^2)$ | $O(n^2)$ | ✓ Optimal |
| Brute Force | $O(2^n)$ | $O(n)$ | ✓ Optimal |
| Earliest Start | $O(n \log n)$ | $O(n)$ | ✗ Suboptimal |

## Examples and Test Cases

### Test Case 1: Basic Scheduling
- **Activities**: (1,3), (2,5), (4,6), (6,9), (5,7)
- **Expected Result**: Activities 1, 3, 4
- **Explanation**: Select activity finishing at 3, then 6, then 9

### Test Case 2: Classroom Scheduling
- **Classes**: [10-11], [11-12], [9-10], [12-13], [10:30-11:30]
- **Expected Result**: All except overlapping ones
- **Real-world relevance**: Fitting maximum classes in a day

### Test Case 3: Edge Case - Empty List
- **Activities**: []
- **Expected Result**: []
- **Robustness check**: Handling null/empty input

### Performance Testing
```
Input Size: 10,000 activities
Execution Time: ~5-10 milliseconds
Selected Activities: ~3,000-5,000
Efficiency: 30-50% of activities typically selected
```

## Key Insights

1. **Greedy Algorithms Are Context-Dependent**: The earliest-finish greedy choice works for this problem, but choosing a different criterion leads to suboptimal solutions.

2. **Optimal Substructure**: After selecting an activity, the remaining problem is an instance of the same problem, justifying the inductive proof.

3. **Sorting Is Essential**: The greedy choice requires activities to be sorted by finish time, making sorting the critical step.

4. **Practical Significance**: This algorithm models real-world scheduling constraints and provides an efficient, provably optimal solution.

5. **Mathematical Rigor**: The exchange argument proof demonstrates how to formally prove greedy algorithm correctness, a technique applicable to many other problems.

6. **Not Always Applicable**: Greedy algorithms work only when the problem exhibits optimal substructure and greedy choice properties. Many scheduling problems require more complex approaches.

## Related Problems

- **Weighted Activity Selection**: Maximize profit instead of count (requires dynamic programming)
- **Interval Scheduling Maximization**: Similar to activity selection
- **Job Sequencing with Deadlines**: Assign tasks to maximize profit
- **Classroom Timetable Generation**: Extended version with multiple rooms and teachers
