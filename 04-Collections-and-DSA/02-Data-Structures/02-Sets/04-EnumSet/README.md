# EnumSet in Java

## Simple Explanation with Real-World Analogy

Imagine a **survey questionnaire** with a fixed set of predefined answers (like "Yes", "No", "Maybe", "Not Applicable"). Instead of storing them in a regular list where each answer takes significant memory, the survey system uses a clever **checkbox grid** where each answer is represented by a single bit. This way, you can store all 4 answers using just 4 bits instead of much more memory. This is exactly how **EnumSet** works - it's an ultra-efficient set implementation specifically designed for working with **enum constants**.

**EnumSet** is the most memory-efficient and performant set implementation in Java because it uses a **bit vector** internally instead of hash tables or trees. Each bit represents whether an enum constant is present in the set, making it incredibly fast and memory-efficient.

## Internal Structure: How EnumSet Works

```
Enum Definition:
enum Color { RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE }

EnumSet Internal Representation:
┌─────────────────────────────────┐
│    Bit Vector (long or int[])   │
├─────────────────────────────────┤
│ Bit:  0  1  2  3  4  5          │
│       |  |  |  |  |  |          │
│       1  0  1  1  0  1          │
│       |  |  |  |  |  |          │
│      RED BLUE YELLOW PURPLE     │
│      (present) (not present)    │
└─────────────────────────────────┘

Memory: 1-2 bytes for up to 64 enum values
Lookup: Direct bit check = O(1) constant time
```

**EnumSet** is a specialized collection specifically for enum types. Here's how it works:

1. **Bit Representation**: Each enum constant is represented by a single bit
2. **Compact Storage**: Uses 1 long (64 bits) for up to 64 enum values, or an array of longs for more
3. **Direct Access**: No hashing - just bit operations
4. **Ultra-Fast**: All operations are O(1) constant time
5. **Type-Safe**: Compiler checks ensure only correct enum types are used

## Important: EnumSet Works ONLY with Enums

```java
// 1. Define enums to use with EnumSet
enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
enum Size { SMALL, MEDIUM, LARGE, X_LARGE }
enum Status { PENDING, ACTIVE, COMPLETED, CANCELLED }

public class EnumSetBasics {
    public static void main(String[] args) {
        // EnumSet ONLY works with enum types
        EnumSet<Day> days = EnumSet.noneOf(Day.class);
        EnumSet<Size> sizes = EnumSet.noneOf(Size.class);
        EnumSet<Status> statuses = EnumSet.noneOf(Status.class);
        
        // Regular classes won't compile with EnumSet
        // EnumSet<String> invalid = EnumSet.noneOf(String.class); // Compile error!
    }
}
```

## Constructors and Creation Methods

```java
enum Priority { LOW, MEDIUM, HIGH, CRITICAL }

public class EnumSetCreation {
    public static void main(String[] args) {
        // 1. Empty set
        EnumSet<Priority> empty = EnumSet.noneOf(Priority.class);
        System.out.println("Empty: " + empty); // []
        
        // 2. Full set (all enum constants)
        EnumSet<Priority> full = EnumSet.allOf(Priority.class);
        System.out.println("Full: " + full); // [LOW, MEDIUM, HIGH, CRITICAL]
        
        // 3. Range (consecutive values)
        EnumSet<Priority> range = EnumSet.range(Priority.LOW, Priority.HIGH);
        System.out.println("Range LOW-HIGH: " + range); // [LOW, MEDIUM, HIGH]
        
        // 4. Specific elements
        EnumSet<Priority> specific = EnumSet.of(Priority.HIGH, Priority.CRITICAL);
        System.out.println("HIGH and CRITICAL: " + specific); // [HIGH, CRITICAL]
        
        // 5. Single element
        EnumSet<Priority> single = EnumSet.of(Priority.MEDIUM);
        System.out.println("Only MEDIUM: " + single); // [MEDIUM]
        
        // 6. Copy from collection
        Set<Priority> regular = new HashSet<>(Arrays.asList(Priority.LOW, Priority.HIGH));
        EnumSet<Priority> copy = EnumSet.copyOf(regular);
        System.out.println("Copy from HashSet: " + copy); // [LOW, HIGH]
        
        // 7. Complement (all except specified)
        EnumSet<Priority> complement = EnumSet.complementOf(copy);
        System.out.println("Complement of [LOW, HIGH]: " + complement); 
        // [MEDIUM, CRITICAL]
    }
}
```

## Core Operations - All O(1) Constant Time!

### Add Operation
```java
enum Environment { DEVELOPMENT, STAGING, PRODUCTION }

EnumSet<Environment> activeEnvs = EnumSet.noneOf(Environment.class);

// Add single element - O(1)
activeEnvs.add(Environment.PRODUCTION);
activeEnvs.add(Environment.STAGING);
boolean added = activeEnvs.add(Environment.DEVELOPMENT);
System.out.println("Added DEVELOPMENT: " + added); // true

// Try adding duplicate
boolean duplicate = activeEnvs.add(Environment.PRODUCTION);
System.out.println("Added duplicate PRODUCTION: " + duplicate); // false

System.out.println("Active environments: " + activeEnvs);
// Output: [DEVELOPMENT, STAGING, PRODUCTION]

// Add multiple from collection
Collection<Environment> more = Arrays.asList(Environment.PRODUCTION);
activeEnvs.addAll(more);
System.out.println("After addAll: " + activeEnvs);

// Create and add in one go
EnumSet<Environment> merged = EnumSet.of(
    Environment.PRODUCTION,
    Environment.STAGING
);
activeEnvs.addAll(merged);
System.out.println("After addAll from EnumSet: " + activeEnvs);
```

### Remove Operation
```java
enum Permission { READ, WRITE, DELETE, EXECUTE, ADMIN }

EnumSet<Permission> userPerms = EnumSet.of(
    Permission.READ,
    Permission.WRITE,
    Permission.DELETE,
    Permission.EXECUTE
);

System.out.println("Original permissions: " + userPerms);
// Output: [READ, WRITE, DELETE, EXECUTE]

// Remove single element - O(1)
boolean removed = userPerms.remove(Permission.DELETE);
System.out.println("Removed DELETE: " + removed); // true
System.out.println("After remove: " + userPerms);
// Output: [READ, WRITE, EXECUTE]

// Remove non-existent element
boolean notFound = userPerms.remove(Permission.ADMIN);
System.out.println("Removed ADMIN: " + notFound); // false

// Remove multiple
userPerms.removeAll(EnumSet.of(Permission.WRITE, Permission.EXECUTE));
System.out.println("After removeAll: " + userPerms);
// Output: [READ]

// Remove with condition
EnumSet<Permission> allPerms = EnumSet.allOf(Permission.class);
allPerms.removeIf(p -> p.ordinal() > Permission.WRITE.ordinal());
System.out.println("After removeIf: " + allPerms);
// Output: [READ, WRITE]

// Clear all - O(1)
userPerms.clear();
System.out.println("After clear: " + userPerms); // []
```

### Contains and Size Operations
```java
enum Feature { LOGIN, REGISTER, PROFILE, SETTINGS, NOTIFICATIONS, ADMIN }

EnumSet<Feature> enabledFeatures = EnumSet.of(
    Feature.LOGIN,
    Feature.REGISTER,
    Feature.PROFILE,
    Feature.SETTINGS
);

// Contains check - O(1)
System.out.println("Has LOGIN: " + enabledFeatures.contains(Feature.LOGIN)); // true
System.out.println("Has ADMIN: " + enabledFeatures.contains(Feature.ADMIN)); // false

// Contains all - O(1) per element
boolean hasAll = enabledFeatures.containsAll(
    Arrays.asList(Feature.LOGIN, Feature.PROFILE)
);
System.out.println("Has LOGIN and PROFILE: " + hasAll); // true

// Size - O(1)
System.out.println("Number of features: " + enabledFeatures.size()); // 4

// Is empty - O(1)
System.out.println("Is empty: " + enabledFeatures.isEmpty()); // false

// Check if set matches another
EnumSet<Feature> subset = EnumSet.of(Feature.LOGIN, Feature.REGISTER);
System.out.println("enabledFeatures contains subset: " + 
    enabledFeatures.containsAll(subset)); // true
```

## Set Operations

```java
enum Category { JAVA, PYTHON, JAVASCRIPT, GOLANG, RUST, KOTLIN }

public class EnumSetOperations {
    public static void main(String[] args) {
        // Team A's expertise
        EnumSet<Category> teamA = EnumSet.of(
            Category.JAVA,
            Category.PYTHON,
            Category.KOTLIN
        );
        
        // Team B's expertise
        EnumSet<Category> teamB = EnumSet.of(
            Category.JAVA,
            Category.JAVASCRIPT,
            Category.GOLANG
        );
        
        System.out.println("Team A: " + teamA);
        System.out.println("Team B: " + teamB);
        
        // 1. Intersection (common skills)
        EnumSet<Category> common = EnumSet.copyOf(teamA);
        common.retainAll(teamB);
        System.out.println("Common skills: " + common); // [JAVA]
        
        // 2. Union (all skills)
        EnumSet<Category> union = EnumSet.copyOf(teamA);
        union.addAll(teamB);
        System.out.println("All skills: " + union); 
        // [JAVA, PYTHON, KOTLIN, JAVASCRIPT, GOLANG]
        
        // 3. Difference (A's unique skills)
        EnumSet<Category> unique = EnumSet.copyOf(teamA);
        unique.removeAll(teamB);
        System.out.println("Team A unique: " + unique); 
        // [PYTHON, KOTLIN]
        
        // 4. Skills neither team has
        EnumSet<Category> missing = EnumSet.allOf(Category.class);
        missing.removeAll(union);
        System.out.println("Missing skills: " + missing); // [RUST]
    }
}
```

## Iteration and Ordering

```java
enum Month { JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC }

EnumSet<Month> workingMonths = EnumSet.range(Month.JAN, Month.NOV);

// 1. Enhanced for loop - Natural order (definition order)
System.out.println("=== Forward Iteration ===");
for (Month month : workingMonths) {
    System.out.print(month + " ");
}
// Output: JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV

// 2. Iterator
System.out.println("\n=== Iterator ===");
Iterator<Month> iter = workingMonths.iterator();
while (iter.hasNext()) {
    System.out.print(iter.next() + " ");
}
// Output: JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV

// 3. Stream API
System.out.println("\n=== Stream API ===");
workingMonths.stream()
    .filter(m -> m.ordinal() < Month.JUL.ordinal())
    .forEach(m -> System.out.print(m + " "));
// Output: JAN FEB MAR APR MAY JUN

// 4. forEach
System.out.println("\n=== forEach ===");
workingMonths.forEach(m -> System.out.print(m + " "));
// Output: JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV

// 5. Reverse (manual)
System.out.println("\n=== Reverse ===");
List<Month> list = new ArrayList<>(workingMonths);
Collections.reverse(list);
list.forEach(m -> System.out.print(m + " "));
// Output: NOV OCT SEP AUG JUL JUN MAY APR MAR FEB JAN
```

## Performance Characteristics - Lightning Fast!

```java
enum Color { RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE, PINK, BROWN,
             BLACK, WHITE, GRAY, CYAN, MAGENTA, LIME, NAVY, TEAL }

public class EnumSetPerformance {
    public static void main(String[] args) {
        EnumSet<Color> set = EnumSet.noneOf(Color.class);
        
        // Add all - O(1) per element
        long startAdd = System.nanoTime();
        for (Color c : Color.values()) {
            set.add(c);
        }
        long endAdd = System.nanoTime();
        System.out.printf("Add 16 elements: %.2f ns\n", 
            (double)(endAdd - startAdd) / 16);
        
        // Contains search - O(1)
        long startSearch = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            set.contains(Color.RED);
        }
        long endSearch = System.nanoTime();
        System.out.printf("1M contains checks: %.2f ms\n", 
            (double)(endSearch - startSearch) / 1_000_000);
        
        // Iteration
        long startIter = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            set.forEach(c -> {});
        }
        long endIter = System.nanoTime();
        System.out.printf("Iterate 100K times: %.2f ms\n", 
            (double)(endIter - startIter) / 1_000_000);
        
        // Remove
        long startRemove = System.nanoTime();
        EnumSet<Color> copy = EnumSet.copyOf(set);
        for (Color c : Color.values()) {
            copy.remove(c);
        }
        long endRemove = System.nanoTime();
        System.out.printf("Remove 16 elements: %.2f ns\n", 
            (double)(endRemove - startRemove) / 16);
    }
}

// Typical Output:
// Add 16 elements: 5-10 ns per element
// 1M contains checks: 1-2 ms
// Iterate 100K times: 10-20 ms
// Remove 16 elements: 5-10 ns per element
```

| Operation | Time | Why |
|-----------|------|-----|
| Add | O(1) | Simple bit set operation |
| Remove | O(1) | Simple bit clear operation |
| Contains | O(1) | Bit check operation |
| Clear | O(1) | Reset entire bit vector |
| Size | O(1) | Count of set bits |
| Iteration | O(n) | But extremely fast due to bit operations |

## Real-World Code Examples

```java
public class EnumSetRealWorld {
    public static void main(String[] args) {
        // Example 1: User Permissions
        userPermissionsExample();
        
        // Example 2: Available Days
        availableDaysExample();
        
        // Example 3: Feature Flags
        featureFlagsExample();
        
        // Example 4: Status Tracking
        statusTrackingExample();
    }
    
    // Example 1: User role permissions
    static void userPermissionsExample() {
        System.out.println("=== User Permissions ===");
        
        enum Permission { READ, WRITE, DELETE, EXECUTE, ADMIN, AUDIT }
        
        class User {
            String name;
            EnumSet<Permission> permissions;
            
            User(String name) {
                this.name = name;
                this.permissions = EnumSet.noneOf(Permission.class);
            }
            
            void grantPermission(Permission... perms) {
                for (Permission p : perms) {
                    permissions.add(p);
                }
            }
            
            boolean canDo(Permission perm) {
                return permissions.contains(perm);
            }
            
            @Override
            public String toString() {
                return name + ": " + permissions;
            }
        }
        
        User admin = new User("Admin");
        admin.grantPermission(Permission.values());
        System.out.println(admin);
        
        User editor = new User("Editor");
        editor.grantPermission(Permission.READ, Permission.WRITE, Permission.DELETE);
        System.out.println(editor);
        
        User viewer = new User("Viewer");
        viewer.grantPermission(Permission.READ);
        System.out.println(viewer);
        
        System.out.println("Can editor execute? " + editor.canDo(Permission.EXECUTE));
    }
    
    // Example 2: Schedule availability
    static void availableDaysExample() {
        System.out.println("\n=== Schedule Availability ===");
        
        enum Weekday { MON, TUE, WED, THU, FRI, SAT, SUN }
        
        class TimeSlot {
            String name;
            EnumSet<Weekday> available;
            
            TimeSlot(String name) {
                this.name = name;
                this.available = EnumSet.noneOf(Weekday.class);
            }
            
            void setAvailable(Weekday... days) {
                for (Weekday d : days) {
                    available.add(d);
                }
            }
            
            boolean isAvailable(Weekday day) {
                return available.contains(day);
            }
            
            int hoursPerWeek() {
                return available.size() * 8; // 8 hours per day
            }
        }
        
        TimeSlot morning = new TimeSlot("Morning (9-5)");
        morning.setAvailable(Weekday.MON, Weekday.TUE, Weekday.WED, 
                             Weekday.THU, Weekday.FRI);
        System.out.println(morning.name + ": " + morning.available);
        System.out.println("Hours/week: " + morning.hoursPerWeek());
        
        TimeSlot weekend = new TimeSlot("Weekends");
        weekend.setAvailable(Weekday.SAT, Weekday.SUN);
        System.out.println(weekend.name + ": " + weekend.available);
        System.out.println("Hours/week: " + weekend.hoursPerWeek());
    }
    
    // Example 3: Feature flags
    static void featureFlagsExample() {
        System.out.println("\n=== Feature Flags ===");
        
        enum Feature { 
            DARK_MODE, BETA_DASHBOARD, NEW_API, AI_ASSISTANT, 
            TWO_FACTOR_AUTH, ADVANCED_ANALYTICS 
        }
        
        class Environment {
            String name;
            EnumSet<Feature> enabledFeatures;
            
            Environment(String name) {
                this.name = name;
                this.enabledFeatures = EnumSet.noneOf(Feature.class);
            }
            
            void enableFeature(Feature... features) {
                for (Feature f : features) {
                    enabledFeatures.add(f);
                }
            }
            
            boolean isEnabled(Feature feature) {
                return enabledFeatures.contains(feature);
            }
        }
        
        Environment dev = new Environment("Development");
        dev.enableFeature(Feature.values());
        System.out.println("Dev features: " + dev.enabledFeatures);
        
        Environment production = new Environment("Production");
        production.enableFeature(Feature.DARK_MODE, Feature.TWO_FACTOR_AUTH,
                                Feature.ADVANCED_ANALYTICS);
        System.out.println("Prod features: " + production.enabledFeatures);
        
        System.out.println("AI Assistant in prod? " + 
            production.isEnabled(Feature.AI_ASSISTANT));
    }
    
    // Example 4: Process status tracking
    static void statusTrackingExample() {
        System.out.println("\n=== Status Tracking ===");
        
        enum Status { CREATED, VALIDATED, PROCESSED, COMPLETED, ARCHIVED }
        
        class WorkItem {
            String id;
            EnumSet<Status> history;
            
            WorkItem(String id) {
                this.id = id;
                this.history = EnumSet.noneOf(Status.class);
                this.history.add(Status.CREATED);
            }
            
            void updateStatus(Status status) {
                history.add(status);
            }
            
            boolean hasBeenIn(Status status) {
                return history.contains(status);
            }
            
            String getLifecycle() {
                return String.join(" → ", 
                    history.stream()
                        .map(Status::toString)
                        .collect(java.util.stream.Collectors.toList()));
            }
        }
        
        WorkItem item = new WorkItem("TASK-001");
        item.updateStatus(Status.VALIDATED);
        item.updateStatus(Status.PROCESSED);
        item.updateStatus(Status.COMPLETED);
        
        System.out.println("Item: " + item.id);
        System.out.println("Lifecycle: " + item.getLifecycle());
        System.out.println("Has been archived? " + item.hasBeenIn(Status.ARCHIVED));
    }
}
```

## Null Handling and Thread Safety

```java
public class EnumSetNullSafety {
    enum Color { RED, BLUE, GREEN }
    
    public static void main(String[] args) {
        // Null Handling - EnumSet does NOT allow null
        EnumSet<Color> set = EnumSet.of(Color.RED, Color.BLUE);
        
        try {
            set.add(null); // Throws NullPointerException
        } catch (NullPointerException e) {
            System.out.println("EnumSet doesn't allow null!");
        }
        
        // Null checking
        System.out.println("Contains null: " + set.contains(null)); // false
        
        // Thread Safety - EnumSet is NOT synchronized
        EnumSet<Color> syncSet = EnumSet.noneOf(Color.class);
        
        // Use synchronized wrapper if needed
        Set<Color> threadSafeSet = Collections.synchronizedSet(syncSet);
        
        // Multi-threaded usage
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (Color c : Color.values()) {
                    threadSafeSet.add(c);
                }
            }).start();
        }
    }
}
```

## When to Use EnumSet

✅ **Use EnumSet when:**
- You're working with **enum types only**
- You need **ultra-fast operations** (O(1) constant time)
- Memory is **critical** (most efficient set)
- You need **bulk operations** on enums
- You're implementing **permission systems**, **feature flags**, **status tracking**
- You need **high-performance** set operations

❌ **Don't use EnumSet when:**
- You have **non-enum types** (won't compile)
- You need to **store null** values
- You're working with **generic collections** of mixed types
- You need **persistence** across JVM restarts (serialize differently)

## Memory Efficiency Comparison

```java
// Storing 100 boolean flags
// Option 1: HashSet<Integer> - ~4800 bytes
HashSet<Integer> hashSet = new HashSet<>();

// Option 2: EnumSet - ~16 bytes (for up to 64 values)
enum Flag { /* 100 enum values */ }
EnumSet<Flag> enumSet = EnumSet.noneOf(Flag.class);

// EnumSet is ~300x more memory efficient!
```

## Comparison with Other Set Implementations

| Feature | HashSet | LinkedHashSet | TreeSet | EnumSet |
|---------|---------|---------------|---------|---------|
| Order | Random | Insertion | Sorted | Natural |
| Add | O(1) avg | O(1) avg | O(log n) | O(1) ✓ |
| Remove | O(1) avg | O(1) avg | O(log n) | O(1) ✓ |
| Contains | O(1) avg | O(1) avg | O(log n) | O(1) ✓ |
| Type | Any | Any | Any | Enum only ✓ |
| Memory | High | Higher | High | Minimal ✓ |
| Null allowed | Yes | Yes | No | No |

## Key Takeaways

- **EnumSet** is the **fastest and most memory-efficient** set implementation
- All operations are **guaranteed O(1) constant time**
- Perfect for **enum types only** - won't work with regular classes
- Uses **bit vectors** internally - incredibly compact
- Ideal for **permission systems**, **feature flags**, and **state tracking**
- No null values allowed
- Not thread-safe - wrap with `Collections.synchronizedSet()` if needed
- **Always prefer EnumSet over HashSet** when working with enums
