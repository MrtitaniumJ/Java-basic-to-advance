# Job Sequencing with Deadlines and Profits

## Problem Statement

The job sequencing problem is a classic greedy algorithm problem where you are given a set of jobs, each with:
- **Job ID**: A unique identifier for the job
- **Deadline**: The latest time by which the job must be completed
- **Profit**: The profit gained if the job is completed by its deadline

The goal is to **schedule jobs to maximize total profit** such that each job is completed within its deadline. Each job takes exactly one unit of time to complete.

### Problem Constraints
1. Only one job can be executed at a time
2. A job must be completed within its specified deadline to gain profit
3. If a job cannot be completed by its deadline, it cannot be scheduled
4. Profit is only gained if the job is completed exactly by or before its deadline

### Problem Example
```
Jobs: 
  Job(id=1, deadline=4, profit=40)
  Job(id=2, deadline=1, profit=30)
  Job(id=3, deadline=2, profit=35)
  Job(id=4, deadline=2, profit=25)
  Job(id=5, deadline=3, profit=25)

Maximum Profit: 95 (Sequence: Job3, Job1, Job5)
```

## Greedy Choice Property

The greedy strategy for job sequencing is based on **profit maximization**:

### Greedy Strategy
1. **Sort jobs in decreasing order of profit**
2. **For each job in sorted order**, try to schedule it at the latest available time slot before its deadline
3. **If a slot is available**, schedule the job; otherwise, skip it

### Why This Works
- By sorting by profit first, we ensure that we attempt to include high-profit jobs
- By scheduling at the latest available time slot, we leave earlier slots open for jobs with earlier deadlines
- This greedy choice is **optimal** because:
  - If we skip a high-profit job, substituting it with lower-profit jobs cannot increase total profit
  - Scheduling at the latest slot maximizes flexibility for future job scheduling

### Greedy vs Dynamic Programming
- **Greedy Approach**: O(n²) time complexity, optimal for this problem
- **Dynamic Programming**: O(n × maxDeadline) time complexity, more general but less efficient for this specific problem

## Java Implementation

### Class Definitions

```java
// Job class to store job information
public class Job implements Comparable<Job> {
    public int id;
    public int deadline;
    public int profit;
    
    public Job(int id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
    
    // Sort by profit in descending order
    @Override
    public int compareTo(Job other) {
        return other.profit - this.profit;  // Descending order
    }
    
    @Override
    public String toString() {
        return String.format("Job(id=%d, deadline=%d, profit=%d)", 
                           id, deadline, profit);
    }
}
```

```java
// Result class to store scheduling outcome
public class JobSchedulingResult {
    public List<Job> scheduledJobs;
    public int totalProfit;
    public int[] schedule;
    
    public JobSchedulingResult(List<Job> scheduledJobs, 
                               int totalProfit, 
                               int[] schedule) {
        this.scheduledJobs = scheduledJobs;
        this.totalProfit = totalProfit;
        this.schedule = schedule;
    }
    
    public void printSchedule() {
        System.out.println("\nJob Scheduling Results:");
        System.out.println("=======================");
        System.out.println("Scheduled Jobs: " + scheduledJobs);
        System.out.println("Total Profit: " + totalProfit);
        System.out.println("\nSchedule Timeline:");
        for (int i = 0; i < schedule.length; i++) {
            if (schedule[i] != -1) {
                System.out.println("Time Slot " + (i + 1) + ": Job " + schedule[i]);
            }
        }
    }
}
```

### Greedy Algorithm Implementation

```java
public class JobSequencingGreedy {
    
    /**
     * Solves the job sequencing problem using greedy approach
     * Time Complexity: O(n²)
     * Space Complexity: O(n)
     */
    public static JobSchedulingResult scheduleJobs(Job[] jobs) {
        // Edge case: no jobs
        if (jobs == null || jobs.length == 0) {
            return new JobSchedulingResult(new ArrayList<>(), 0, new int[0]);
        }
        
        int n = jobs.length;
        
        // Step 1: Sort jobs by profit in descending order
        Arrays.sort(jobs);
        
        // Find maximum deadline to determine schedule array size
        int maxDeadline = 0;
        for (Job job : jobs) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        
        // Step 2: Create schedule array (-1 means slot is empty)
        int[] schedule = new int[maxDeadline];
        Arrays.fill(schedule, -1);
        
        // Step 3: Track which jobs are scheduled
        List<Job> scheduledJobs = new ArrayList<>();
        int totalProfit = 0;
        
        // Step 4: Process each job (already sorted by profit)
        for (Job job : jobs) {
            // Find a free slot for this job
            // Start from the latest slot before the deadline
            for (int slot = Math.min(job.deadline - 1, maxDeadline - 1); 
                 slot >= 0; slot--) {
                
                if (schedule[slot] == -1) {
                    // Slot is free, schedule the job here
                    schedule[slot] = job.id;
                    scheduledJobs.add(job);
                    totalProfit += job.profit;
                    break;  // Move to next job
                }
            }
        }
        
        return new JobSchedulingResult(scheduledJobs, totalProfit, schedule);
    }
    
    /**
     * Alternative implementation with more detailed tracking
     */
    public static JobSchedulingResult scheduleJobsDetailed(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new JobSchedulingResult(new ArrayList<>(), 0, new int[0]);
        }
        
        int n = jobs.length;
        Job[] jobsCopy = Arrays.copyOf(jobs, n);
        Arrays.sort(jobsCopy);
        
        int maxDeadline = 0;
        for (Job job : jobsCopy) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        
        int[] schedule = new int[maxDeadline];
        Arrays.fill(schedule, -1);
        boolean[] slotOccupied = new boolean[maxDeadline];
        
        List<Job> scheduledJobs = new ArrayList<>();
        int totalProfit = 0;
        
        for (Job job : jobsCopy) {
            // Try to place job at the latest available slot
            for (int slot = Math.min(job.deadline - 1, maxDeadline - 1); 
                 slot >= 0; slot--) {
                
                if (!slotOccupied[slot]) {
                    schedule[slot] = job.id;
                    slotOccupied[slot] = true;
                    scheduledJobs.add(job);
                    totalProfit += job.profit;
                    break;
                }
            }
        }
        
        return new JobSchedulingResult(scheduledJobs, totalProfit, schedule);
    }
}
```

### Utility Classes and Helper Methods

```java
public class JobSequencingUtils {
    
    /**
     * Print jobs in a formatted manner
     */
    public static void printJobs(Job[] jobs) {
        System.out.println("\nAvailable Jobs:");
        System.out.println("===============");
        System.out.printf("%-4s %-10s %-10s\n", "ID", "Deadline", "Profit");
        System.out.println("-".repeat(30));
        for (Job job : jobs) {
            System.out.printf("%-4d %-10d %-10d\n", 
                            job.id, job.deadline, job.profit);
        }
    }
    
    /**
     * Validate if a schedule meets all deadline constraints
     */
    public static boolean validateSchedule(Job[] jobs, int[] schedule) {
        for (int time = 0; time < schedule.length; time++) {
            if (schedule[time] != -1) {
                // Find the job in the original array
                for (Job job : jobs) {
                    if (job.id == schedule[time]) {
                        // Check if job is completed within deadline
                        if (time + 1 > job.deadline) {
                            return false;
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Calculate profit from a given schedule
     */
    public static int calculateProfit(Job[] jobs, int[] schedule) {
        int profit = 0;
        for (int time = 0; time < schedule.length; time++) {
            if (schedule[time] != -1) {
                for (Job job : jobs) {
                    if (job.id == schedule[time] && time + 1 <= job.deadline) {
                        profit += job.profit;
                        break;
                    }
                }
            }
        }
        return profit;
    }
}
```

### Complete Test Driver

```java
public class JobSequencingDemo {
    
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("Job Sequencing with Deadlines");
        System.out.println("==================================");
        
        // Test Case 1: Basic example
        testCase1();
        
        // Test Case 2: All jobs can be scheduled
        testCase2();
        
        // Test Case 3: Limited slots
        testCase3();
        
        // Test Case 4: Single job
        testCase4();
        
        // Test Case 5: Complex scenario
        testCase5();
    }
    
    private static void testCase1() {
        System.out.println("\n--- Test Case 1: Basic Example ---");
        Job[] jobs = {
            new Job(1, 4, 40),
            new Job(2, 1, 30),
            new Job(3, 2, 35),
            new Job(4, 2, 25),
            new Job(5, 3, 25)
        };
        
        JobSequencingUtils.printJobs(jobs);
        JobSchedulingResult result = JobSequencingGreedy.scheduleJobs(jobs);
        result.printSchedule();
    }
    
    private static void testCase2() {
        System.out.println("\n--- Test Case 2: All Jobs Can Be Scheduled ---");
        Job[] jobs = {
            new Job(1, 1, 100),
            new Job(2, 2, 200),
            new Job(3, 3, 150)
        };
        
        JobSequencingUtils.printJobs(jobs);
        JobSchedulingResult result = JobSequencingGreedy.scheduleJobs(jobs);
        result.printSchedule();
    }
    
    private static void testCase3() {
        System.out.println("\n--- Test Case 3: Limited Slots ---");
        Job[] jobs = {
            new Job(1, 1, 10),
            new Job(2, 1, 20),
            new Job(3, 1, 30)
        };
        
        JobSequencingUtils.printJobs(jobs);
        JobSchedulingResult result = JobSequencingGreedy.scheduleJobs(jobs);
        result.printSchedule();
    }
    
    private static void testCase4() {
        System.out.println("\n--- Test Case 4: Single Job ---");
        Job[] jobs = {
            new Job(1, 5, 100)
        };
        
        JobSequencingUtils.printJobs(jobs);
        JobSchedulingResult result = JobSequencingGreedy.scheduleJobs(jobs);
        result.printSchedule();
    }
    
    private static void testCase5() {
        System.out.println("\n--- Test Case 5: Complex Scenario ---");
        Job[] jobs = {
            new Job(1, 3, 60),
            new Job(2, 1, 100),
            new Job(3, 2, 40),
            new Job(4, 2, 50),
            new Job(5, 4, 80),
            new Job(6, 3, 70)
        };
        
        JobSequencingUtils.printJobs(jobs);
        JobSchedulingResult result = JobSequencingGreedy.scheduleJobs(jobs);
        result.printSchedule();
        
        // Validate schedule
        System.out.println("\nSchedule Validation: " + 
            JobSequencingUtils.validateSchedule(jobs, result.schedule));
    }
}
```

## Complexity Analysis

### Time Complexity
- **Sorting**: O(n log n) - sorting jobs by profit
- **Scheduling**: O(n × maxDeadline) - for each job, we might scan all deadline slots
- **Overall**: **O(n × maxDeadline)** or **O(n²)** in the worst case

### Space Complexity
- **Schedule Array**: O(maxDeadline)
- **Result Storage**: O(n)
- **Overall**: **O(n + maxDeadline)**

## Advantages and Disadvantages

### Advantages
✓ Greedy approach is intuitive and easy to understand
✓ Optimal solution for this specific problem
✓ Efficient time complexity compared to brute force
✓ No need for complex data structures

### Disadvantages
✗ Only applicable when profit is the optimization criteria
✗ Requires sorting which adds O(n log n) overhead
✗ Not optimal for variations (e.g., minimizing lateness)

## Key Insights

1. **Greedy Strategy**: Always pick the most profitable available job
2. **Slot Scheduling**: Place jobs at the latest possible time to maximize flexibility
3. **Optimality**: The greedy choice leads to an optimal solution
4. **Deadline Constraint**: Core to the problem - respecting deadlines is critical

## Related Problems

- **Fractional Knapsack**: Similar greedy approach but with weight constraints
- **Activity Selection**: Different greedy criteria (earliest finish time)
- **Huffman Coding**: Different application of greedy principle
- **Minimum Spanning Tree**: Greedy approach for graph problems

## References

- GeeksforGeeks: Job Sequencing Problem
- Introduction to Algorithms (CLRS) - Greedy Algorithms
- Algorithm Design Manual - Scheduling Problems
