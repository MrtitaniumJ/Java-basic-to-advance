# TreeMap: Complete Guide

## Real-World Analogy
Imagine a large library catalog where all books are automatically arranged alphabetically on shelves. When you search for a book, instead of jumping directly to a location (like HashMap), you navigate through the sorted shelves, but you always know books are in order. You can also ask "show me all books from A to M" or "what's the first/last book?" and get instant answers. This is TreeMap—trading direct access speed for guaranteed ordering and range operations.

## Internal Structure & Mechanism
TreeMap uses a **Red-Black Tree** (self-balancing binary search tree):
- **Ordered**: Keys ordered by natural ordering or custom Comparator
- **Self-Balancing**: Maintains O(log n) height through red-black tree properties
- **Node Structure**: Each node stores key, value, left child, right child, and color (red/black)
- **No Hash Function**: Relies on comparison, not hashing
- **Range Support**: Can efficiently retrieve subsets of entries within key ranges

### Visual Tree Structure
```
            B(5)
           /    \
        B(3)    R(8)
       /   \    /   \
     B(1) R(4) R(7) B(9)
```
*Where B=Black, R=Red (color used for self-balancing)*

## Ordering & Comparator

```java
// Natural ordering (keys must implement Comparable)
TreeMap<Integer, String> map1 = new TreeMap<>();
map1.put(3, "three");
map1.put(1, "one");
map1.put(2, "two");
// Order: 1, 2, 3

// Custom comparator (reverse order)
TreeMap<Integer, String> map2 = new TreeMap<>(Collections.reverseOrder());
map2.put(3, "three");
map2.put(1, "one");
map2.put(2, "two");
// Order: 3, 2, 1

// Custom comparator (by string length)
TreeMap<String, Integer> map3 = new TreeMap<>((s1, s2) -> {
    int compare = Integer.compare(s1.length(), s2.length());
    return compare != 0 ? compare : s1.compareTo(s2); // Tiebreaker
});
map3.put("hi", 1);
map3.put("hello", 2);
map3.put("hey", 3);
// Order: hi, hey, hello (by length)
```

## Constructors & Creation Methods

```java
// Default constructor (natural ordering)
TreeMap<String, Integer> map1 = new TreeMap<>();

// With custom comparator
TreeMap<String, Integer> map2 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

// Copy constructor (copies entries and uses source's comparator if TreeMap)
TreeMap<String, Integer> map3 = new TreeMap<>(map1);

// From another Map (uses natural ordering)
TreeMap<String, Integer> map4 = new TreeMap<>(Map.of("b", 2, "a", 1));

// Reverse order
TreeMap<Integer, String> map5 = new TreeMap<>(Collections.reverseOrder());

// Custom comparator with fallback
TreeMap<Person, String> map6 = new TreeMap<>((p1, p2) -> {
    int ageCompare = Integer.compare(p1.getAge(), p2.getAge());
    return ageCompare != 0 ? ageCompare : p1.getName().compareTo(p2.getName());
});

// Submap constructor (creates view of existing TreeMap)
TreeMap<Integer, String> original = new TreeMap<>();
original.put(1, "one");
original.put(2, "two");
original.put(3, "three");
SortedMap<Integer, String> submap = original.subMap(1, 3); // 1 to 2 (exclusive upper)
```

## Core Operations with Performance

| Operation | Time Complexity | Details |
|-----------|-----------------|---------|
| `put(K, V)` | O(log n) | Insertion with tree rebalancing |
| `get(K)` | O(log n) | Binary search through tree |
| `remove(K)` | O(log n) | Removal with tree rebalancing |
| `containsKey(K)` | O(log n) | Tree search |
| `containsValue(V)` | O(n) | Linear scan required |
| `firstKey()` / `lastKey()` | O(log n) | Navigate to extreme nodes |
| `firstEntry()` / `lastEntry()` | O(log n) | Get entry at extreme |
| `floorKey()` / `ceilingKey()` | O(log n) | Find key ≤ or ≥ target |
| `subMap(K1, K2)` | O(log n) | Creates lazy view |
| `size()` | O(1) | Constant time |
| `clear()` | O(n) | Clear all nodes |

## Specialized Navigation Operations

```java
TreeMap<Integer, String> map = new TreeMap<>();
map.put(10, "ten");
map.put(20, "twenty");
map.put(30, "thirty");
map.put(40, "forty");
map.put(50, "fifty");

// First and Last
System.out.println("First key: " + map.firstKey()); // 10
System.out.println("Last key: " + map.lastKey()); // 50
System.out.println("First entry: " + map.firstEntry()); // 10=ten
System.out.println("Last entry: " + map.lastEntry()); // 50=fifty

// Floor and Ceiling (key ≤ / ≥ target)
System.out.println("Floor of 25: " + map.floorKey(25)); // 20
System.out.println("Ceiling of 25: " + map.ceilingKey(25)); // 30

// Lower and Higher (key < / > target)
System.out.println("Lower than 25: " + map.lowerKey(25)); // 20
System.out.println("Higher than 25: " + map.higherKey(25)); // 30

// Poll operations (retrieve and remove)
System.out.println("Poll first: " + map.pollFirstEntry()); // 10=ten
System.out.println("Poll last: " + map.pollLastEntry()); // 50=fifty
```

## Range Operations & SubMaps

```java
TreeMap<Integer, String> scores = new TreeMap<>();
scores.put(60, "D");
scores.put(70, "C");
scores.put(80, "B");
scores.put(90, "A");
scores.put(100, "A+");

// subMap(fromKey, toKey) - from inclusive, to exclusive
SortedMap<Integer, String> range1 = scores.subMap(70, 90);
System.out.println("Scores 70-89: " + range1); // {70=C, 80=B}

// headMap(toKey) - all entries < toKey
SortedMap<Integer, String> headMap = scores.headMap(80);
System.out.println("Below 80: " + headMap); // {60=D, 70=C}

// tailMap(fromKey) - all entries >= fromKey
SortedMap<Integer, String> tailMap = scores.tailMap(80);
System.out.println("80 and above: " + tailMap); // {80=B, 90=A, 100=A+}

// navigableSubMap(from, fromInclusive, to, toInclusive)
NavigableMap<Integer, String> custom = scores.subMap(70, true, 90, true);
System.out.println("70-90 inclusive: " + custom); // {70=C, 80=B, 90=A}
```

## Iteration Methods

```java
TreeMap<String, Integer> map = new TreeMap<>();
map.put("Charlie", 3);
map.put("Alice", 1);
map.put("Bob", 2);

// 1. Forward iteration (default, sorted)
System.out.println("Forward:");
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 2. Reverse iteration
System.out.println("Reverse:");
for (Map.Entry<String, Integer> entry : map.descendingMap().entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 3. Keys sorted
for (String key : map.navigableKeySet()) {
    System.out.println(key);
}

// 4. Keys in reverse
for (String key : map.descendingKeySet()) {
    System.out.println(key);
}

// 5. Values (in key order)
for (Integer value : map.values()) {
    System.out.println(value);
}

// 6. Iterator with modification safety
Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
while (iterator.hasNext()) {
    Map.Entry<String, Integer> entry = iterator.next();
    if (entry.getValue() < 2) {
        iterator.remove();
    }
}

// 7. Stream API (naturally sorted)
map.entrySet().stream()
   .filter(e -> e.getValue() > 1)
   .sorted(Map.Entry.comparingByKey())
   .forEach(System.out::println);
```

## When to Use TreeMap
✅ **Use when:**
- Need sorted key order (natural or custom)
- Need range queries (all keys between X and Y)
- Need first/last key operations
- Need keys in descending order
- Building sorted leaderboards/rankings
- Need ceiling/floor operations
- Want predictable iteration order

❌ **Avoid when:**
- Don't need sorted order (use HashMap)
- Need insertion order (use LinkedHashMap)
- Need O(1) average lookup (use HashMap)
- Need thread safety (use ConcurrentSkipListMap)
- Keys don't implement Comparable and you forget comparator

## Common Use Cases with Examples

### 1. Leaderboard/Rankings
```java
class LeaderboardEntry {
    String playerName;
    int score;
    long timestamp;
    
    LeaderboardEntry(String name, int score) {
        this.playerName = name;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return playerName + " - " + score + " points";
    }
}

class Leaderboard {
    // Reverse order: highest scores first
    private TreeMap<Integer, List<String>> scoreBoard = 
        new TreeMap<>(Collections.reverseOrder());
    
    public void addScore(String player, int score) {
        scoreBoard.computeIfAbsent(score, k -> new ArrayList<>()).add(player);
    }
    
    public void printTopScores(int limit) {
        int count = 0;
        for (Map.Entry<Integer, List<String>> entry : scoreBoard.entrySet()) {
            if (count >= limit) break;
            System.out.println(entry.getKey() + " points: " + entry.getValue());
            count += entry.getValue().size();
        }
    }
    
    public void printScoresInRange(int minScore, int maxScore) {
        NavigableMap<Integer, List<String>> range = 
            scoreBoard.subMap(maxScore, true, minScore, true);
        System.out.println("Scores between " + minScore + " and " + maxScore + ":");
        range.forEach((score, players) -> 
            System.out.println(score + ": " + players)
        );
    }
}

Leaderboard board = new Leaderboard();
board.addScore("Alice", 1000);
board.addScore("Bob", 950);
board.addScore("Charlie", 1050);
board.addScore("David", 900);
board.printTopScores(2);
board.printScoresInRange(900, 1000);
```

### 2. Time-Based Event Tracking
```java
class EventLog {
    private TreeMap<Long, List<String>> timestampedEvents = new TreeMap<>();
    
    public void logEvent(String event) {
        long timestamp = System.currentTimeMillis();
        timestampedEvents.computeIfAbsent(timestamp, k -> new ArrayList<>())
                        .add(event);
    }
    
    public void printEventsSince(long timeMs) {
        long cutoff = System.currentTimeMillis() - timeMs;
        NavigableMap<Long, List<String>> recentEvents = 
            timestampedEvents.tailMap(cutoff);
        
        System.out.println("Events in last " + timeMs + "ms:");
        recentEvents.forEach((timestamp, events) -> {
            System.out.println("[" + new java.util.Date(timestamp) + "] " + events);
        });
    }
    
    public void printEventsInTimeRange(long startTime, long endTime) {
        NavigableMap<Long, List<String>> range = 
            timestampedEvents.subMap(startTime, true, endTime, true);
        range.forEach((timestamp, events) -> 
            System.out.println(timestamp + ": " + events)
        );
    }
}

EventLog log = new EventLog();
log.logEvent("User logged in");
log.logEvent("Query executed");
log.logEvent("Cache updated");
log.printEventsSince(60000); // Last 60 seconds
```

### 3. Salary Range Queries
```java
class SalaryManager {
    private TreeMap<Double, List<String>> salaryRanges = new TreeMap<>();
    
    public void addEmployee(String name, double salary) {
        salaryRanges.computeIfAbsent(salary, k -> new ArrayList<>()).add(name);
    }
    
    public double getAverageSalaryInRange(double min, double max) {
        NavigableMap<Double, List<String>> range = 
            salaryRanges.subMap(min, true, max, true);
        
        int count = 0;
        double total = 0;
        for (Map.Entry<Double, List<String>> entry : range.entrySet()) {
            total += entry.getKey() * entry.getValue().size();
            count += entry.getValue().size();
        }
        
        return count > 0 ? total / count : 0;
    }
    
    public void printEmployeesInSalaryRange(double min, double max) {
        NavigableMap<Double, List<String>> range = 
            salaryRanges.subMap(min, true, max, true);
        
        System.out.println("Employees with salary " + min + "-" + max + ":");
        range.forEach((salary, employees) -> 
            System.out.println("  $" + salary + ": " + employees)
        );
    }
    
    public String getHighestPaidEmployee() {
        Map.Entry<Double, List<String>> lastEntry = salaryRanges.lastEntry();
        return lastEntry != null ? lastEntry.getValue().get(0) : null;
    }
    
    public String getLowestPaidEmployee() {
        Map.Entry<Double, List<String>> firstEntry = salaryRanges.firstEntry();
        return firstEntry != null ? firstEntry.getValue().get(0) : null;
    }
}

SalaryManager sm = new SalaryManager();
sm.addEmployee("Alice", 75000);
sm.addEmployee("Bob", 85000);
sm.addEmployee("Charlie", 95000);
sm.addEmployee("David", 80000);
sm.printEmployeesInSalaryRange(75000, 90000);
System.out.println("Average in range: " + sm.getAverageSalaryInRange(75000, 90000));
System.out.println("Highest paid: " + sm.getHighestPaidEmployee());
```

### 4. Priority Queue using Comparators
```java
class Task implements Comparable<Task> {
    String name;
    int priority; // 1=low, 5=high
    
    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // Descending
    }
    
    @Override
    public String toString() {
        return name + " (P" + priority + ")";
    }
}

class TaskScheduler {
    private TreeMap<Task, String> tasks = new TreeMap<>();
    
    public void addTask(Task task, String description) {
        tasks.put(task, description);
    }
    
    public Task getNextTask() {
        Map.Entry<Task, String> first = tasks.firstEntry();
        if (first != null) {
            Task task = first.getKey();
            tasks.remove(task);
            return task;
        }
        return null;
    }
    
    public void printAllTasks() {
        System.out.println("Task Queue (by priority):");
        tasks.forEach((task, desc) -> 
            System.out.println("  " + task + ": " + desc)
        );
    }
}

TaskScheduler scheduler = new TaskScheduler();
scheduler.addTask(new Task("Documentation", 2), "Write API docs");
scheduler.addTask(new Task("Bug fix", 5), "Critical production bug");
scheduler.addTask(new Task("Feature", 3), "New sorting algorithm");
scheduler.addTask(new Task("Testing", 4), "Unit test coverage");
scheduler.printAllTasks();
```

### 5. Range-Based Data Analysis
```java
class ScoreAnalyzer {
    private TreeMap<Integer, Integer> scoreFrequency = new TreeMap<>();
    
    public void addScores(int... scores) {
        for (int score : scores) {
            scoreFrequency.put(score, scoreFrequency.getOrDefault(score, 0) + 1);
        }
    }
    
    public int countScoresInRange(int min, int max) {
        NavigableMap<Integer, Integer> range = 
            scoreFrequency.subMap(min, true, max, true);
        return range.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public double getPercentageInRange(int min, int max) {
        int inRange = countScoresInRange(min, max);
        int total = scoreFrequency.values().stream().mapToInt(Integer::intValue).sum();
        return total > 0 ? (inRange * 100.0) / total : 0;
    }
    
    public void printGradeDistribution() {
        System.out.println("Grade Distribution:");
        System.out.println("A (90-100): " + countScoresInRange(90, 100) + " students");
        System.out.println("B (80-89): " + countScoresInRange(80, 89) + " students");
        System.out.println("C (70-79): " + countScoresInRange(70, 79) + " students");
        System.out.println("D (60-69): " + countScoresInRange(60, 69) + " students");
        System.out.println("F (<60): " + countScoresInRange(0, 59) + " students");
    }
}

ScoreAnalyzer analyzer = new ScoreAnalyzer();
analyzer.addScores(95, 87, 76, 88, 92, 65, 78, 91, 82, 69);
analyzer.printGradeDistribution();
System.out.println("% in range [80-90]: " + analyzer.getPercentageInRange(80, 90) + "%");
```

### 6. Version Management
```java
class Version implements Comparable<Version> {
    int major, minor, patch;
    
    Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }
    
    @Override
    public int compareTo(Version other) {
        if (major != other.major) return major - other.major;
        if (minor != other.minor) return minor - other.minor;
        return patch - other.patch;
    }
    
    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}

class ReleaseNotes {
    private TreeMap<Version, String> releases = new TreeMap<>();
    
    public void addRelease(Version v, String notes) {
        releases.put(v, notes);
    }
    
    public void printReleaseHistory() {
        System.out.println("Release History (latest first):");
        releases.descendingMap().forEach((version, notes) ->
            System.out.println("  " + version + ": " + notes)
        );
    }
    
    public String getLatestRelease() {
        Map.Entry<Version, String> latest = releases.lastEntry();
        return latest != null ? latest.getKey().toString() : null;
    }
}

ReleaseNotes notes = new ReleaseNotes();
notes.addRelease(new Version(1, 0, 0), "Initial release");
notes.addRelease(new Version(1, 1, 0), "Bug fixes and improvements");
notes.addRelease(new Version(2, 0, 0), "Major redesign");
notes.addRelease(new Version(2, 1, 5), "Security patch");
notes.printReleaseHistory();
```

## Edge Cases & Gotchas

### 1. Null Keys Not Allowed
```java
TreeMap<String, Integer> map = new TreeMap<>();
map.put("a", 1);
// ❌ NullPointerException
map.put(null, 2); // Throws exception

// However, null values are allowed
map.put("b", null); // OK
```

### 2. Comparator Consistency
```java
// BAD: Comparator violates consistency
TreeMap<Integer, String> bad = new TreeMap<>((a, b) -> {
    return a % 2 - b % 2; // 2 and 4 both even, seem equal but aren't
});
bad.put(2, "two");
bad.put(4, "four"); // Overwrites "two" due to comparator treating them equally!

// GOOD: Consistent comparator
TreeMap<Integer, String> good = new TreeMap<>((a, b) -> {
    int cmp = a % 2 - b % 2;
    return cmp != 0 ? cmp : a.compareTo(b);
});
good.put(2, "two");
good.put(4, "four"); // Both entries preserved
```

### 3. Modification During Iteration
```java
TreeMap<String, Integer> map = new TreeMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

// ConcurrentModificationException if adding new keys
try {
    for (String key : map.keySet()) {
        if (key.equals("a")) {
            map.put("d", 4); // ❌ Throws exception
        }
    }
} catch (ConcurrentModificationException e) {
    System.out.println("Exception caught: " + e);
}

// Safe: Use iterator.remove() or collect first
Iterator<String> it = map.keySet().iterator();
while (it.hasNext()) {
    String key = it.next();
    if (key.equals("b")) {
        it.remove(); // Safe
    }
}
```

## Thread Safety & Synchronization
- **TreeMap is NOT thread-safe**
- **Synchronized Wrapper**:
```java
SortedMap<String, Integer> syncMap = 
    Collections.synchronizedSortedMap(new TreeMap<>());
```
- **Better Alternative**: ConcurrentSkipListMap for concurrent access
```java
ConcurrentNavigableMap<String, Integer> concurrent = 
    new ConcurrentSkipListMap<>();
```

## Comparison with Other Map Implementations

| Feature | TreeMap | HashMap | LinkedHashMap | ConcurrentSkipListMap |
|---------|---------|---------|---------------|----------------------|
| **Ordering** | Sorted | No | Insertion | Sorted |
| **Time (get)** | O(log n) | O(1) avg | O(1) avg | O(log n) |
| **Range queries** | ✅ Yes | ❌ No | ❌ No | ✅ Yes |
| **First/Last** | O(log n) | O(n) | O(1) | O(log n) |
| **Null keys** | ❌ No | ✅ Yes | ✅ Yes | ❌ No |
| **Thread-safe** | ❌ No | ❌ No | ❌ No | ✅ Yes |

## Performance Benchmarks

```java
public class TreeMapBenchmark {
    public static void main(String[] args) {
        int size = 100_000;
        int[] testValues = new int[size];
        for (int i = 0; i < size; i++) {
            testValues[i] = i;
        }
        java.util.Collections.shuffle(java.util.Arrays.asList(testValues));
        
        // Insert
        long start = System.nanoTime();
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            map.put(testValues[i], "value_" + i);
        }
        long insertTime = System.nanoTime() - start;
        
        // Lookup
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            map.get(testValues[i]);
        }
        long lookupTime = System.nanoTime() - start;
        
        // Range query (submap)
        start = System.nanoTime();
        SortedMap<Integer, String> range = map.subMap(size/4, size*3/4);
        int rangeSize = range.size();
        long rangeTime = System.nanoTime() - start;
        
        System.out.println("Insert 100k items: " + (insertTime / 1_000_000) + " ms");
        System.out.println("Lookup 100k items: " + (lookupTime / 1_000_000) + " ms");
        System.out.println("Range query: " + (rangeTime / 1_000_000) + " ms (found " + rangeSize + " items)");
    }
}
```

## Key Takeaways
1. TreeMap maintains sorted order—slower insertion/lookup than HashMap but guarantees ordering
2. Red-black tree ensures O(log n) for all basic operations
3. Range queries (subMap, headMap, tailMap) are efficient and return lazy views
4. Navigation methods (firstKey, lastKey, floor, ceiling) are powerful for bounded searches
5. Perfect for leaderboards, rankings, time-series data, and range-based queries
6. ComparableString and custom comparators control ordering
7. Null keys NOT allowed (throws NullPointerException)
8. No hash function—relies entirely on comparison
9. Use ConcurrentSkipListMap for thread-safe sorted maps
10. Iteration always yields entries in sorted order, including reverse iteration

