# Merge Intervals Pattern

## Overview
The Merge Intervals pattern is a fundamental technique for handling overlapping or adjacent intervals. This pattern is commonly used to merge, split, or find relationships between multiple intervals on a number line. It's particularly useful when dealing with scheduling problems, time slot management, or range operations.

## Pattern Explanation
An interval is typically represented as a pair of numbers `[start, end]`. The merge intervals pattern focuses on combining overlapping intervals into fewer, non-overlapping intervals. Two intervals `[a, b]` and `[c, d]` overlap if:
- They share a common range: `a <= c <= b` or `c <= a <= d`
- Or they are adjacent: `b == c` or `d == a`

The key insight is that if we sort intervals by their start time, we can process them sequentially and merge overlapping ones efficiently.

## When to Use
- **Meeting room scheduling**: Find total time slots needed for multiple meetings
- **Calendar conflicts**: Detect overlapping events and merge time blocks
- **Route optimization**: Combine overlapping delivery or travel segments
- **Data processing**: Merge overlapping data ranges or intervals
- **Employee availability**: Combine available time slots across employees
- **Interval intersection/union**: Find common or combined ranges
- **Insert intervals**: Add new intervals to a list and merge overlaps

## Algorithm Steps

### Merge Intervals Algorithm
1. Sort intervals by start time
2. Initialize result with first interval
3. For each subsequent interval:
   - If it overlaps with the last interval in result, merge them
   - Otherwise, add it as a new interval to result
4. Return the merged intervals

### Key Comparison Logic
```
Two intervals [a,b] and [c,d] (where a <= c):
- Overlapping: b >= c
- Non-overlapping: b < c
- Merged result: [a, max(b, d)]
```

## Problem Types

### 1. **Merge Intervals** (Core)
- Input: List of overlapping intervals
- Output: List of non-overlapping merged intervals
- Example: `[[1,3],[2,6],[8,10],[15,18]]` → `[[1,6],[8,10],[15,18]]`

### 2. **Insert Interval**
- Insert a new interval into a list of non-overlapping intervals
- Merge overlaps if necessary
- Example: Insert `[5,7]` into `[[1,2],[3,5],[6,9]]`

### 3. **Interval Intersection**
- Find all overlapping regions between two lists of intervals
- Example: `[[1,3],[5,6],[8,10]]` and `[[2,3],[5,7],[9,10]]` → `[[2,3],[5,6],[9,10]]`

### 4. **Meeting Rooms**
- Determine minimum conference rooms needed for meetings
- Check if person can attend all meetings without overlap

### 5. **Employee Free Time**
- Find free time slots between employee schedules
- Merge all busy intervals and find gaps

## Complete Java Implementation

```java
import java.util.*;

/**
 * Merge Intervals Pattern Implementation
 * Demonstrates various interval merging operations
 */
public class MergeIntervalsPattern {
    
    /**
     * Definition for an interval
     */
    public static class Interval {
        public int start;
        public int end;
        
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }
    
    /**
     * Problem 1: Merge Overlapping Intervals
     * Time: O(n log n) for sorting
     * Space: O(n) for result list
     */
    public static List<Interval> mergeIntervals(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Sort intervals by start time
        Collections.sort(intervals, (a, b) -> Integer.compare(a.start, b.start));
        
        List<Interval> result = new ArrayList<>();
        Interval current = intervals.get(0);
        
        for (int i = 1; i < intervals.size(); i++) {
            Interval next = intervals.get(i);
            
            // Check if intervals overlap
            if (current.end >= next.start) {
                // Merge intervals
                current.end = Math.max(current.end, next.end);
            } else {
                // No overlap, add current to result and move to next
                result.add(current);
                current = next;
            }
        }
        
        // Don't forget the last interval
        result.add(current);
        return result;
    }
    
    /**
     * Problem 2: Insert Interval into Non-Overlapping List
     * Time: O(n) as list is already sorted
     * Space: O(n) for result
     */
    public static List<Interval> insertInterval(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<>();
        int i = 0;
        
        // Add all intervals that end before new interval starts
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            result.add(intervals.get(i));
            i++;
        }
        
        // Merge overlapping intervals with new interval
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);
            newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
            i++;
        }
        result.add(newInterval);
        
        // Add remaining intervals
        while (i < intervals.size()) {
            result.add(intervals.get(i));
            i++;
        }
        
        return result;
    }
    
    /**
     * Problem 3: Find Interval Intersection
     * Time: O(m + n) where m and n are list sizes
     * Space: O(k) where k is number of intersections
     */
    public static List<Interval> intervalIntersection(List<Interval> list1, List<Interval> list2) {
        List<Interval> result = new ArrayList<>();
        
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            Interval a = list1.get(i);
            Interval b = list2.get(j);
            
            // Find intersection
            int start = Math.max(a.start, b.start);
            int end = Math.min(a.end, b.end);
            
            if (start <= end) {
                result.add(new Interval(start, end));
            }
            
            // Move pointer of interval that ends first
            if (a.end < b.end) {
                i++;
            } else {
                j++;
            }
        }
        
        return result;
    }
    
    /**
     * Problem 4: Minimum Meeting Rooms Needed
     * Time: O(n log n)
     * Space: O(n) for events array
     */
    public static int minMeetingRooms(List<Interval> meetings) {
        if (meetings == null || meetings.isEmpty()) {
            return 0;
        }
        
        int[][] events = new int[meetings.size() * 2][2];
        int index = 0;
        
        // Create start and end events
        for (Interval meeting : meetings) {
            events[index][0] = meeting.start;
            events[index][1] = 1; // Start event
            index++;
            
            events[index][0] = meeting.end;
            events[index][1] = -1; // End event
            index++;
        }
        
        // Sort by time, ends before starts for same time
        Arrays.sort(events, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            return a[1] - b[1]; // End events before start events
        });
        
        int maxRooms = 0;
        int currentRooms = 0;
        
        for (int[] event : events) {
            currentRooms += event[1];
            maxRooms = Math.max(maxRooms, currentRooms);
        }
        
        return maxRooms;
    }
    
    /**
     * Problem 5: Find Employee Free Time
     * Returns merged free time slots
     * Time: O(n log n)
     * Space: O(n)
     */
    public static List<Interval> findEmployeeFreeTime(List<List<Interval>> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Merge all busy intervals
        List<Interval> allBusy = new ArrayList<>();
        for (List<Interval> schedule : schedules) {
            allBusy.addAll(schedule);
        }
        
        List<Interval> merged = mergeIntervals(allBusy);
        
        // Find free slots between merged busy intervals
        List<Interval> freeTime = new ArrayList<>();
        for (int i = 0; i < merged.size() - 1; i++) {
            freeTime.add(new Interval(merged.get(i).end, merged.get(i + 1).start));
        }
        
        return freeTime;
    }
    
    /**
     * Problem 6: Remove Covered Intervals
     * Remove intervals that are completely covered by another interval
     * Time: O(n log n)
     * Space: O(1) excluding result
     */
    public static int removeCoveredIntervals(List<Interval> intervals) {
        Collections.sort(intervals, (a, b) -> {
            if (a.start != b.start) {
                return a.start - b.start;
            }
            // If start is same, longer interval comes first
            return b.end - a.end;
        });
        
        int count = 0;
        int prevEnd = 0;
        
        for (Interval interval : intervals) {
            if (interval.end > prevEnd) {
                count++;
                prevEnd = interval.end;
            }
        }
        
        return count;
    }

    // ============ Test Cases ============
    
    public static void main(String[] args) {
        System.out.println("=== Merge Intervals Pattern ===\n");
        
        // Test 1: Merge Intervals
        System.out.println("Test 1: Merge Intervals");
        List<Interval> intervals = Arrays.asList(
            new Interval(1, 3),
            new Interval(2, 6),
            new Interval(8, 10),
            new Interval(15, 18)
        );
        System.out.println("Input: " + intervals);
        System.out.println("Output: " + mergeIntervals(intervals));
        System.out.println();
        
        // Test 2: Insert Interval
        System.out.println("Test 2: Insert Interval");
        List<Interval> existing = Arrays.asList(
            new Interval(1, 2),
            new Interval(3, 5),
            new Interval(6, 9)
        );
        Interval toInsert = new Interval(5, 7);
        System.out.println("Existing: " + existing);
        System.out.println("Insert: " + toInsert);
        System.out.println("Output: " + insertInterval(existing, toInsert));
        System.out.println();
        
        // Test 3: Interval Intersection
        System.out.println("Test 3: Interval Intersection");
        List<Interval> list1 = Arrays.asList(
            new Interval(1, 3),
            new Interval(5, 6),
            new Interval(8, 10)
        );
        List<Interval> list2 = Arrays.asList(
            new Interval(2, 3),
            new Interval(5, 7),
            new Interval(9, 10)
        );
        System.out.println("List 1: " + list1);
        System.out.println("List 2: " + list2);
        System.out.println("Intersection: " + intervalIntersection(list1, list2));
        System.out.println();
        
        // Test 4: Minimum Meeting Rooms
        System.out.println("Test 4: Minimum Meeting Rooms");
        List<Interval> meetings = Arrays.asList(
            new Interval(0, 30),
            new Interval(5, 10),
            new Interval(15, 20)
        );
        System.out.println("Meetings: " + meetings);
        System.out.println("Minimum Rooms: " + minMeetingRooms(meetings));
        System.out.println();
        
        // Test 5: Employee Free Time
        System.out.println("Test 5: Employee Free Time");
        List<List<Interval>> schedules = Arrays.asList(
            Arrays.asList(new Interval(1, 3), new Interval(5, 6)),
            Arrays.asList(new Interval(2, 4)),
            Arrays.asList(new Interval(7, 9))
        );
        System.out.println("Schedules: " + schedules);
        System.out.println("Free Time: " + findEmployeeFreeTime(schedules));
        System.out.println();
        
        // Test 6: Remove Covered Intervals
        System.out.println("Test 6: Remove Covered Intervals");
        List<Interval> toFilter = Arrays.asList(
            new Interval(1, 4),
            new Interval(3, 6),
            new Interval(2, 8)
        );
        System.out.println("Input: " + toFilter);
        System.out.println("Non-covered count: " + removeCoveredIntervals(toFilter));
    }
}
```

## Complexity Analysis

### Merge Intervals
- **Time Complexity**: O(n log n) - dominated by sorting
- **Space Complexity**: O(n) - for the result list or sorting space

### Insert Interval (Sorted Input)
- **Time Complexity**: O(n) - single pass through list
- **Space Complexity**: O(n) - for result list

### Interval Intersection
- **Time Complexity**: O(m + n) - two-pointer approach
- **Space Complexity**: O(k) - k is number of intersections

### Minimum Meeting Rooms
- **Time Complexity**: O(n log n) - sorting events
- **Space Complexity**: O(n) - events array

## Edge Cases

1. **Empty intervals**: Handle null or empty input lists
2. **Single interval**: Should return as-is
3. **No overlap**: All intervals are distinct
4. **Complete overlap**: One interval contains another
5. **Adjacent intervals**: `[1,2]` and `[2,3]` - should merge to `[1,3]`
6. **Identical intervals**: Multiple identical intervals
7. **Nested intervals**: One completely inside another

## Real-World Applications

1. **Calendar Systems**: Merge overlapping meetings or events
2. **Reservation Systems**: Combine booked time slots
3. **Network Analytics**: Analyze overlapping network connections
4. **CPU Scheduling**: Merge overlapping task execution times
5. **Medical Scheduling**: Combine overlapping patient appointment windows
6. **Data Processing**: Merge overlapping data ranges
7. **GIS Applications**: Combine overlapping geographic regions
8. **Stock Market**: Analyze overlapping trading periods

## Key Takeaways

- **Sort first**: Sorting by start time is crucial for efficient merging
- **Comparison logic**: Key insight is `current.end >= next.start` for overlap detection
- **Extension**: After sorting, many problems become linear
- **Optimization**: For pre-sorted data, use insert algorithm (O(n) vs O(n log n))
