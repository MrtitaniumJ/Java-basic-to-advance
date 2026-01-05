# Comparator Interface - Part 2: Advanced Features

## Advanced Comparator Features

```java
// Advanced Comparator features and optimization techniques

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Collectors;

class AdvancedComparatorFeatures {
    
    public void demonstrateAdvancedFeatures() {
        System.out.println("=== ADVANCED COMPARATOR FEATURES ===");
        
        demonstrateNullHandling();
        demonstratePerformanceOptimization();
        demonstrateComparatorCaching();
        demonstrateComplexSortingScenarios();
        demonstrateFunctionalComposition();
        demonstrateCustomComparatorImplementations();
    }
    
    // Null handling strategies
    public void demonstrateNullHandling() {
        System.out.println("\n--- Advanced Null Handling ---");
        
        List<Person> peopleWithNulls = Arrays.asList(
            new Person("Alice", 25, "Engineering"),
            new Person(null, 30, "Marketing"),
            new Person("Charlie", null, "Engineering"),
            new Person("Diana", 28, null),
            new Person(null, null, "HR"),
            new Person("Eve", 35, "Engineering")
        );
        
        System.out.printf("👥 People with nulls: %s%n", peopleWithNulls);
        
        // Null-safe name comparison (nulls last)
        System.out.println("\n🛡️ Null-safe name comparison (nulls last):");
        List<Person> nullsLastName = new ArrayList<>(peopleWithNulls);
        Collections.sort(nullsLastName, 
                        Comparator.comparing(Person::getName, 
                                           Comparator.nullsLast(String::compareTo)));
        
        nullsLastName.forEach(p -> System.out.printf("  %s%n", p));
        
        // Complex null handling with chaining
        System.out.println("\n🔗 Complex null handling with chaining:");
        List<Person> complexNullHandling = new ArrayList<>(peopleWithNulls);
        
        Comparator<Person> complexComparator = Comparator
            .comparing(Person::getName, 
                      Comparator.nullsLast(String::compareTo))
            .thenComparing(Person::getAge, 
                          Comparator.nullsFirst(Integer::compareTo))
            .thenComparing(Person::getDepartment, 
                          Comparator.nullsLast(String::compareTo));
        
        Collections.sort(complexNullHandling, complexComparator);
        complexNullHandling.forEach(p -> System.out.printf("  %s%n", p));
        
        // Custom null handling logic
        System.out.println("\n🎯 Custom null handling logic:");
        List<Person> customNullHandling = new ArrayList<>(peopleWithNulls);
        
        Comparator<Person> customNullComparator = (p1, p2) -> {
            // Treat null age as 0, null names as "Unknown", null departments as "Unassigned"
            String name1 = p1.getName() != null ? p1.getName() : "Unknown";
            String name2 = p2.getName() != null ? p2.getName() : "Unknown";
            
            Integer age1 = p1.getAge() != null ? p1.getAge() : 0;
            Integer age2 = p2.getAge() != null ? p2.getAge() : 0;
            
            String dept1 = p1.getDepartment() != null ? p1.getDepartment() : "Unassigned";
            String dept2 = p2.getDepartment() != null ? p2.getDepartment() : "Unassigned";
            
            // Compare using transformed values
            int nameCompare = name1.compareTo(name2);
            if (nameCompare != 0) return nameCompare;
            
            int ageCompare = age1.compareTo(age2);
            if (ageCompare != 0) return ageCompare;
            
            return dept1.compareTo(dept2);
        };
        
        Collections.sort(customNullHandling, customNullComparator);
        customNullHandling.forEach(p -> System.out.printf("  %s%n", p));
        
        // Conditional null handling
        System.out.println("\n🔄 Conditional null handling:");
        Comparator<Person> conditionalNullComparator = (p1, p2) -> {
            // If both have department, sort by department then name
            // If only one has department, that one comes first
            // If neither has department, sort by name only
            
            boolean p1HasDept = p1.getDepartment() != null;
            boolean p2HasDept = p2.getDepartment() != null;
            
            if (p1HasDept && !p2HasDept) return -1;
            if (!p1HasDept && p2HasDept) return 1;
            
            if (p1HasDept && p2HasDept) {
                int deptCompare = p1.getDepartment().compareTo(p2.getDepartment());
                if (deptCompare != 0) return deptCompare;
            }
            
            // Fallback to name comparison (nulls last)
            return Comparator.comparing(Person::getName, 
                                      Comparator.nullsLast(String::compareTo))
                             .compare(p1, p2);
        };
        
        List<Person> conditionalSorted = new ArrayList<>(peopleWithNulls);
        Collections.sort(conditionalSorted, conditionalNullComparator);
        System.out.println("  Conditional null handling result:");
        conditionalSorted.forEach(p -> System.out.printf("    %s%n", p));
        
        System.out.println("✅ Advanced null handling provides robust sorting solutions");
    }
    
    // Performance optimization
    public void demonstratePerformanceOptimization() {
        System.out.println("\n--- Performance Optimization Techniques ---");
        
        // Generate large dataset
        int dataSize = 100000;
        System.out.printf("🚀 Performance testing with %,d elements:%n", dataSize);
        
        List<Employee> employees = generateEmployees(dataSize);
        
        // Cached key extraction vs repeated extraction
        System.out.println("\n📊 Cached vs Repeated Key Extraction:");
        
        // Expensive computation comparator (simulated)
        Comparator<Employee> expensiveComparator = Comparator.comparing(emp -> {
            // Simulate expensive computation (string manipulation + math)
            String processed = emp.getName().toLowerCase().replace(" ", "");
            return processed.length() * emp.getSalary() / 1000.0;
        });
        
        // Test repeated expensive computation
        List<Employee> list1 = new ArrayList<>(employees.subList(0, 10000));
        long startTime = System.nanoTime();
        Collections.sort(list1, expensiveComparator);
        long expensiveTime = System.nanoTime() - startTime;
        
        // Cached computation using transformation
        List<Employee> list2 = new ArrayList<>(employees.subList(0, 10000));
        startTime = System.nanoTime();
        
        // Pre-compute expensive values
        Map<Employee, Double> keyCache = list2.stream()
                                             .collect(Collectors.toMap(
                                                 Function.identity(),
                                                 emp -> {
                                                     String processed = emp.getName().toLowerCase().replace(" ", "");
                                                     return processed.length() * emp.getSalary() / 1000.0;
                                                 }));
        
        Collections.sort(list2, Comparator.comparing(keyCache::get));
        long cachedTime = System.nanoTime() - startTime;
        
        System.out.printf("  Expensive repeated computation: %,d ns%n", expensiveTime);
        System.out.printf("  Cached computation: %,d ns%n", cachedTime);
        System.out.printf("  Performance improvement: %.1fx faster%n", (double) expensiveTime / cachedTime);
        
        // Primitive comparisons vs object comparisons
        System.out.println("\n🔢 Primitive vs Object Comparisons:");
        
        List<Employee> list3 = new ArrayList<>(employees.subList(0, 50000));
        List<Employee> list4 = new ArrayList<>(list3);
        
        // Using Integer.compare (primitive-oriented)
        startTime = System.nanoTime();
        Collections.sort(list3, (e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        long primitiveTime = System.nanoTime() - startTime;
        
        // Using Integer object comparison
        startTime = System.nanoTime();
        Collections.sort(list4, Comparator.comparing(Employee::getSalary));
        long objectTime = System.nanoTime() - startTime;
        
        System.out.printf("  Primitive comparison: %,d ns%n", primitiveTime);
        System.out.printf("  Object comparison: %,d ns%n", objectTime);
        System.out.printf("  Primitive advantage: %.1fx faster%n", (double) objectTime / primitiveTime);
        
        // Specialized comparators for common types
        System.out.println("\n⚡ Specialized Comparator Methods:");
        
        List<Employee> list5 = new ArrayList<>(employees.subList(0, 50000));
        List<Employee> list6 = new ArrayList<>(list5);
        List<Employee> list7 = new ArrayList<>(list5);
        
        // Generic comparing
        startTime = System.nanoTime();
        Collections.sort(list5, Comparator.comparing(Employee::getSalary));
        long genericTime = System.nanoTime() - startTime;
        
        // comparingInt
        startTime = System.nanoTime();
        Collections.sort(list6, Comparator.comparingInt(Employee::getSalary));
        long intTime = System.nanoTime() - startTime;
        
        // Manual int comparison
        startTime = System.nanoTime();
        Collections.sort(list7, (e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        long manualTime = System.nanoTime() - startTime;
        
        System.out.printf("  Generic comparing: %,d ns%n", genericTime);
        System.out.printf("  comparingInt: %,d ns%n", intTime);
        System.out.printf("  Manual comparison: %,d ns%n", manualTime);
        
        System.out.println("✅ Performance optimization critical for large datasets");
    }
    
    private List<Employee> generateEmployees(int count) {
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Henry"};
        String[] departments = {"Engineering", "Marketing", "HR", "Sales", "Finance"};
        Random random = new Random(42); // Fixed seed for reproducible results
        
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = names[random.nextInt(names.length)] + i;
            String department = departments[random.nextInt(departments.length)];
            int salary = 50000 + random.nextInt(100000);
            employees.add(new Employee(name, department, salary));
        }
        
        return employees;
    }
    
    // Comparator caching and reuse
    public void demonstrateComparatorCaching() {
        System.out.println("\n--- Comparator Caching and Reuse ---");
        
        // Comparator factory with caching
        ComparatorFactory factory = new ComparatorFactory();
        
        System.out.println("🏭 Comparator factory with caching:");
        
        // Get comparators for employees
        Comparator<Employee> byName1 = factory.getEmployeeComparator("name");
        Comparator<Employee> byName2 = factory.getEmployeeComparator("name"); // Should be same instance
        Comparator<Employee> bySalary = factory.getEmployeeComparator("salary");
        
        System.out.printf("  byName1 == byName2: %s (cached)%n", byName1 == byName2);
        System.out.printf("  byName1 == bySalary: %s (different)%n", byName1 == bySalary);
        
        // Test performance with cached comparators
        List<Employee> employees = generateEmployees(10000);
        
        long startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            List<Employee> copy = new ArrayList<>(employees);
            Collections.sort(copy, factory.getEmployeeComparator("name"));
        }
        long cachedTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            List<Employee> copy = new ArrayList<>(employees);
            Collections.sort(copy, Comparator.comparing(Employee::getName)); // New instance each time
        }
        long newTime = System.nanoTime() - startTime;
        
        System.out.printf("  100 sorts with cached comparator: %,d ns%n", cachedTime);
        System.out.printf("  100 sorts with new comparators: %,d ns%n", newTime);
        System.out.printf("  Caching benefit: %.1fx faster%n", (double) newTime / cachedTime);
        
        // Thread-safe comparator sharing
        System.out.println("\n🔒 Thread-safe comparator sharing:");
        ThreadSafeComparatorPool pool = new ThreadSafeComparatorPool();
        
        List<Thread> threads = new ArrayList<>();
        List<Long> threadTimes = Collections.synchronizedList(new ArrayList<>());
        
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                List<Employee> localList = generateEmployees(5000);
                long threadStart = System.nanoTime();
                
                for (int j = 0; j < 10; j++) {
                    List<Employee> copy = new ArrayList<>(localList);
                    Comparator<Employee> comp = pool.getComparator("salary_desc");
                    Collections.sort(copy, comp);
                }
                
                long threadTime = System.nanoTime() - threadStart;
                threadTimes.add(threadTime);
                System.out.printf("    Thread-%d completed in %,d ns%n", threadId, threadTime);
            });
            
            threads.add(thread);
            thread.start();
        }
        
        // Wait for all threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        double avgTime = threadTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        System.out.printf("  Average thread time: %,.0f ns%n", avgTime);
        
        System.out.println("✅ Comparator caching improves performance and memory usage");
    }
    
    // Complex sorting scenarios
    public void demonstrateComplexSortingScenarios() {
        System.out.println("\n--- Complex Sorting Scenarios ---");
        
        // Multi-dimensional data sorting
        List<DataPoint> dataPoints = Arrays.asList(
            new DataPoint(1.5, 2.3, 0.8, "A"),
            new DataPoint(2.1, 1.9, 1.2, "B"),
            new DataPoint(1.8, 2.5, 0.9, "C"),
            new DataPoint(2.0, 2.0, 1.0, "D"),
            new DataPoint(1.7, 2.2, 1.1, "E")
        );
        
        System.out.printf("📊 Multi-dimensional data: %s%n", dataPoints);
        
        // Sort by distance from origin
        System.out.println("\n📏 Sort by distance from origin:");
        List<DataPoint> byDistance = new ArrayList<>(dataPoints);
        Collections.sort(byDistance, Comparator.comparing(dp -> 
            Math.sqrt(dp.getX() * dp.getX() + dp.getY() * dp.getY() + dp.getZ() * dp.getZ())));
        
        byDistance.forEach(dp -> {
            double distance = Math.sqrt(dp.getX() * dp.getX() + dp.getY() * dp.getY() + dp.getZ() * dp.getZ());
            System.out.printf("  %.2f - %s%n", distance, dp);
        });
        
        // Sort by weighted scoring
        System.out.println("\n⚖️ Sort by weighted score (3x + 2y + z):");
        List<DataPoint> byWeightedScore = new ArrayList<>(dataPoints);
        Collections.sort(byWeightedScore, Comparator.comparing(dp -> 
            3 * dp.getX() + 2 * dp.getY() + dp.getZ()).reversed());
        
        byWeightedScore.forEach(dp -> {
            double score = 3 * dp.getX() + 2 * dp.getY() + dp.getZ();
            System.out.printf("  %.2f - %s%n", score, dp);
        });
        
        // Conditional sorting based on data characteristics
        System.out.println("\n🔀 Conditional sorting:");
        List<Student> students = Arrays.asList(
            new Student("Alice", 85, 92, "Senior"),
            new Student("Bob", 78, 88, "Junior"),
            new Student("Charlie", 92, 76, "Senior"),
            new Student("Diana", 88, 94, "Senior"),
            new Student("Eve", 82, 85, "Junior")
        );
        
        System.out.printf("🎓 Students: %s%n", students);
        
        // Different sorting for different years
        Comparator<Student> conditionalComparator = (s1, s2) -> {
            // Seniors: sort by math score (priority for advanced courses)
            // Juniors: sort by english score (focus on communication)
            
            if (s1.getYear().equals("Senior") && s2.getYear().equals("Senior")) {
                return Integer.compare(s2.getMathScore(), s1.getMathScore()); // Desc
            } else if (s1.getYear().equals("Junior") && s2.getYear().equals("Junior")) {
                return Integer.compare(s2.getEnglishScore(), s1.getEnglishScore()); // Desc
            } else {
                // Different years: seniors first, then by overall average
                if (s1.getYear().equals("Senior") && s2.getYear().equals("Junior")) {
                    return -1;
                } else if (s1.getYear().equals("Junior") && s2.getYear().equals("Senior")) {
                    return 1;
                } else {
                    double avg1 = (s1.getMathScore() + s1.getEnglishScore()) / 2.0;
                    double avg2 = (s2.getMathScore() + s2.getEnglishScore()) / 2.0;
                    return Double.compare(avg2, avg1);
                }
            }
        };
        
        List<Student> conditionallySorted = new ArrayList<>(students);
        Collections.sort(conditionallySorted, conditionalComparator);
        
        System.out.println("  Conditional sorting result:");
        conditionallySorted.forEach(s -> {
            double avg = (s.getMathScore() + s.getEnglishScore()) / 2.0;
            System.out.printf("    [%s] %s (avg: %.1f)%n", s.getYear(), s, avg);
        });
        
        // Hierarchical sorting (tree-like structure)
        System.out.println("\n🌳 Hierarchical sorting:");
        List<FileSystemItem> items = Arrays.asList(
            new FileSystemItem("/root/docs/file1.txt", "file", 1024),
            new FileSystemItem("/root/docs", "directory", 0),
            new FileSystemItem("/root/images/photo1.jpg", "file", 2048),
            new FileSystemItem("/root", "directory", 0),
            new FileSystemItem("/root/docs/file2.pdf", "file", 3072),
            new FileSystemItem("/root/images", "directory", 0),
            new FileSystemItem("/root/backup/old.txt", "file", 512),
            new FileSystemItem("/root/backup", "directory", 0)
        );
        
        System.out.printf("📁 File system items: %s%n", items);
        
        // Sort hierarchically: by path depth, then directories first, then alphabetically
        Comparator<FileSystemItem> hierarchicalComparator = Comparator
            .comparing((FileSystemItem item) -> item.getPath().split("/").length) // Depth first
            .thenComparing(item -> item.getType().equals("directory") ? 0 : 1)     // Directories first
            .thenComparing(FileSystemItem::getPath);                               // Alphabetical
        
        List<FileSystemItem> hierarchicallySorted = new ArrayList<>(items);
        Collections.sort(hierarchicallySorted, hierarchicalComparator);
        
        System.out.println("  Hierarchical sorting result:");
        hierarchicallySorted.forEach(item -> {
            int depth = item.getPath().split("/").length - 2; // Adjust for root
            String indent = "  ".repeat(Math.max(0, depth));
            String icon = item.getType().equals("directory") ? "📁" : "📄";
            System.out.printf("    %s%s %s%n", indent, icon, item);
        });
        
        System.out.println("✅ Complex sorting handles sophisticated business requirements");
    }
    
    // Functional composition
    public void demonstrateFunctionalComposition() {
        System.out.println("\n--- Functional Composition with Comparators ---");
        
        List<Product> products = Arrays.asList(
            new Product("Laptop", "Electronics", 999.99, 4.5, true),
            new Product("Book", "Books", 19.99, 4.8, false),
            new Product("Headphones", "Electronics", 199.99, 4.3, true),
            new Product("Magazine", "Books", 4.99, 3.9, false),
            new Product("Smartphone", "Electronics", 799.99, 4.4, true)
        );
        
        System.out.printf("🛍️ Products: %s%n", products);
        
        // Functional composition with predicates
        System.out.println("\n🔧 Functional composition:");
        
        // Create reusable functions
        Function<Product, String> categoryExtractor = Product::getCategory;
        Function<Product, Double> ratingExtractor = Product::getRating;
        Function<Product, Boolean> availabilityExtractor = Product::isInStock;
        Predicate<Product> isExpensive = p -> p.getPrice() > 100;
        Predicate<Product> isHighRated = p -> p.getRating() >= 4.0;
        
        // Compose complex sorting logic
        Comparator<Product> composedComparator = Comparator
            .comparing(availabilityExtractor, Collections.reverseOrder()) // In stock first
            .thenComparing(categoryExtractor)                              // By category
            .thenComparing(ratingExtractor, Collections.reverseOrder())   // High rating first
            .thenComparing(Product::getPrice);                            // Price ascending
        
        List<Product> composedSorted = new ArrayList<>(products);
        Collections.sort(composedSorted, composedComparator);
        
        System.out.println("  Composed sorting (stock, category, rating, price):");
        composedSorted.forEach(p -> System.out.printf("    %s%n", p));
        
        // Conditional composition
        System.out.println("\n🔀 Conditional composition:");
        
        // Different sorting for expensive vs cheap products
        Comparator<Product> conditionalComposer = (p1, p2) -> {
            boolean p1Expensive = isExpensive.test(p1);
            boolean p2Expensive = isExpensive.test(p2);
            
            if (p1Expensive && p2Expensive) {
                // Both expensive: sort by rating then price descending
                return Comparator.comparing(Product::getRating, Collections.reverseOrder())
                                 .thenComparing(Product::getPrice, Collections.reverseOrder())
                                 .compare(p1, p2);
            } else if (!p1Expensive && !p2Expensive) {
                // Both cheap: sort by price then rating
                return Comparator.comparing(Product::getPrice)
                                 .thenComparing(Product::getRating, Collections.reverseOrder())
                                 .compare(p1, p2);
            } else {
                // One expensive, one cheap: expensive first if high rated
                Product expensive = p1Expensive ? p1 : p2;
                Product cheap = p1Expensive ? p2 : p1;
                int multiplier = p1Expensive ? -1 : 1;
                
                return isHighRated.test(expensive) ? multiplier : -multiplier;
            }
        };
        
        List<Product> conditionalComposed = new ArrayList<>(products);
        Collections.sort(conditionalComposed, conditionalComposer);
        
        System.out.println("  Conditional composition result:");
        conditionalComposed.forEach(p -> {
            String category = isExpensive.test(p) ? "EXPENSIVE" : "BUDGET";
            System.out.printf("    [%s] %s%n", category, p);
        });
        
        // Chaining with transformation
        System.out.println("\n🔗 Chaining with transformation:");
        
        // Transform products to value objects and sort
        Function<Product, ProductValue> toValueObject = p -> 
            new ProductValue(p, p.getRating() / p.getPrice() * 100); // Value score
        
        List<ProductValue> valueProducts = products.stream()
                                                  .map(toValueObject)
                                                  .sorted(Comparator.comparing(ProductValue::getValueScore, Collections.reverseOrder())
                                                                    .thenComparing(pv -> pv.getProduct().getName()))
                                                  .collect(Collectors.toList());
        
        System.out.println("  By value score (rating/price ratio):");
        valueProducts.forEach(pv -> 
            System.out.printf("    %.2f - %s%n", pv.getValueScore(), pv.getProduct()));
        
        System.out.println("✅ Functional composition enables flexible and reusable sorting logic");
    }
    
    // Custom comparator implementations
    public void demonstrateCustomComparatorImplementations() {
        System.out.println("\n--- Custom Comparator Implementations ---");
        
        // Fuzzy string comparator
        System.out.println("🔤 Fuzzy string comparator:");
        List<String> names = Arrays.asList("John", "Jon", "Jane", "Jain", "Jack", "Jake");
        FuzzyStringComparator fuzzyComparator = new FuzzyStringComparator();
        
        List<String> fuzzySorted = new ArrayList<>(names);
        Collections.sort(fuzzySorted, fuzzyComparator);
        
        System.out.printf("  Original: %s%n", names);
        System.out.printf("  Fuzzy sorted: %s%n", fuzzySorted);
        
        // Version comparator
        System.out.println("\n📦 Version comparator:");
        List<String> versions = Arrays.asList("1.2.3", "1.10.1", "1.2.10", "2.0.0", "1.9.9", "1.2.3-beta");
        VersionComparator versionComparator = new VersionComparator();
        
        List<String> versionSorted = new ArrayList<>(versions);
        Collections.sort(versionSorted, versionComparator);
        
        System.out.printf("  Original: %s%n", versions);
        System.out.printf("  Version sorted: %s%n", versionSorted);
        
        // Natural order comparator with custom rules
        System.out.println("\n🔢 Natural order with custom rules:");
        List<String> mixedContent = Arrays.asList("item10", "item2", "item1", "item20", "chapter3", "chapter10", "chapter1");
        NaturalOrderComparator naturalComparator = new NaturalOrderComparator();
        
        List<String> naturalSorted = new ArrayList<>(mixedContent);
        Collections.sort(naturalSorted, naturalComparator);
        
        System.out.printf("  Original: %s%n", mixedContent);
        System.out.printf("  Natural sorted: %s%n", naturalSorted);
        
        // Priority queue with custom comparator
        System.out.println("\n📋 Priority queue with custom comparator:");
        PriorityQueue<Task> taskQueue = new PriorityQueue<>(new TaskPriorityComparator());
        
        taskQueue.offer(new Task("Write report", 2, 5));
        taskQueue.offer(new Task("Fix bug", 1, 8));
        taskQueue.offer(new Task("Code review", 3, 3));
        taskQueue.offer(new Task("Meeting", 2, 2));
        taskQueue.offer(new Task("Deploy", 1, 9));
        
        System.out.println("  Task queue (priority order):");
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            System.out.printf("    %s%n", task);
        }
        
        // Comparator with state
        System.out.println("\n📊 Stateful comparator:");
        List<String> words = Arrays.asList("apple", "banana", "apricot", "blueberry", "avocado");
        StatefulComparator statefulComparator = new StatefulComparator("a"); // Prefer words starting with 'a'
        
        List<String> statefulSorted = new ArrayList<>(words);
        Collections.sort(statefulSorted, statefulComparator);
        
        System.out.printf("  Words: %s%n", words);
        System.out.printf("  Stateful sorted (prefer 'a'): %s%n", statefulSorted);
        
        // Change state and sort again
        statefulComparator.setPreferredPrefix("b");
        Collections.sort(words, statefulComparator);
        
        System.out.printf("  Stateful sorted (prefer 'b'): %s%n", words);
        
        System.out.println("✅ Custom comparators handle domain-specific requirements");
    }
    
    // Helper classes for demonstrations
    static class Person {
        private final String name;
        private final Integer age;
        private final String department;
        
        public Person(String name, Integer age, String department) {
            this.name = name;
            this.age = age;
            this.department = department;
        }
        
        public String getName() { return name; }
        public Integer getAge() { return age; }
        public String getDepartment() { return department; }
        
        @Override
        public String toString() {
            return String.format("Person{name=%s, age=%s, dept=%s}", name, age, department);
        }
    }
    
    static class Employee {
        private final String name;
        private final String department;
        private final int salary;
        
        public Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public int getSalary() { return salary; }
        
        @Override
        public String toString() {
            return String.format("%s (%s, $%,d)", name, department, salary);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Employee)) return false;
            Employee employee = (Employee) obj;
            return salary == employee.salary &&
                   Objects.equals(name, employee.name) &&
                   Objects.equals(department, employee.department);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name, department, salary);
        }
    }
    
    static class DataPoint {
        private final double x, y, z;
        private final String label;
        
        public DataPoint(double x, double y, double z, String label) {
            this.x = x; this.y = y; this.z = z; this.label = label;
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public double getZ() { return z; }
        public String getLabel() { return label; }
        
        @Override
        public String toString() {
            return String.format("%s(%.1f,%.1f,%.1f)", label, x, y, z);
        }
    }
    
    static class Student {
        private final String name;
        private final int mathScore;
        private final int englishScore;
        private final String year;
        
        public Student(String name, int mathScore, int englishScore, String year) {
            this.name = name;
            this.mathScore = mathScore;
            this.englishScore = englishScore;
            this.year = year;
        }
        
        public String getName() { return name; }
        public int getMathScore() { return mathScore; }
        public int getEnglishScore() { return englishScore; }
        public String getYear() { return year; }
        
        @Override
        public String toString() {
            return String.format("%s(M:%d,E:%d)", name, mathScore, englishScore);
        }
    }
    
    static class FileSystemItem {
        private final String path;
        private final String type;
        private final long size;
        
        public FileSystemItem(String path, String type, long size) {
            this.path = path;
            this.type = type;
            this.size = size;
        }
        
        public String getPath() { return path; }
        public String getType() { return type; }
        public long getSize() { return size; }
        
        @Override
        public String toString() {
            String name = path.substring(path.lastIndexOf('/') + 1);
            return type.equals("directory") ? name + "/" : name + " (" + size + "B)";
        }
    }
    
    static class Product {
        private final String name;
        private final String category;
        private final double price;
        private final double rating;
        private final boolean inStock;
        
        public Product(String name, String category, double price, double rating, boolean inStock) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.rating = rating;
            this.inStock = inStock;
        }
        
        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getPrice() { return price; }
        public double getRating() { return rating; }
        public boolean isInStock() { return inStock; }
        
        @Override
        public String toString() {
            String stock = inStock ? "✓" : "✗";
            return String.format("%s (%s, $%.2f, ⭐%.1f, %s)", name, category, price, rating, stock);
        }
    }
    
    static class ProductValue {
        private final Product product;
        private final double valueScore;
        
        public ProductValue(Product product, double valueScore) {
            this.product = product;
            this.valueScore = valueScore;
        }
        
        public Product getProduct() { return product; }
        public double getValueScore() { return valueScore; }
    }
    
    static class Task {
        private final String description;
        private final int urgency; // 1 = high, 2 = medium, 3 = low
        private final int importance; // 1-10 scale
        
        public Task(String description, int urgency, int importance) {
            this.description = description;
            this.urgency = urgency;
            this.importance = importance;
        }
        
        public String getDescription() { return description; }
        public int getUrgency() { return urgency; }
        public int getImportance() { return importance; }
        
        @Override
        public String toString() {
            String urgencyText = urgency == 1 ? "HIGH" : urgency == 2 ? "MED" : "LOW";
            return String.format("%s [%s, Imp:%d]", description, urgencyText, importance);
        }
    }
    
    // Utility classes
    static class ComparatorFactory {
        private final Map<String, Comparator<Employee>> cache = new ConcurrentHashMap<>();
        
        public Comparator<Employee> getEmployeeComparator(String field) {
            return cache.computeIfAbsent(field, this::createComparator);
        }
        
        private Comparator<Employee> createComparator(String field) {
            System.out.printf("    Creating new comparator for field: %s%n", field);
            switch (field.toLowerCase()) {
                case "name":
                    return Comparator.comparing(Employee::getName);
                case "department":
                    return Comparator.comparing(Employee::getDepartment);
                case "salary":
                    return Comparator.comparing(Employee::getSalary);
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        }
    }
    
    static class ThreadSafeComparatorPool {
        private final Map<String, Comparator<Employee>> comparators = new ConcurrentHashMap<>();
        
        public ThreadSafeComparatorPool() {
            // Pre-populate common comparators
            comparators.put("name", Comparator.comparing(Employee::getName));
            comparators.put("salary", Comparator.comparing(Employee::getSalary));
            comparators.put("salary_desc", Comparator.comparing(Employee::getSalary).reversed());
            comparators.put("department", Comparator.comparing(Employee::getDepartment));
        }
        
        public Comparator<Employee> getComparator(String key) {
            return comparators.get(key);
        }
    }
    
    // Custom comparator implementations
    static class FuzzyStringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            // Simple fuzzy logic: compare by similarity to common patterns
            double similarity1 = calculateSimilarity(s1);
            double similarity2 = calculateSimilarity(s2);
            
            int similarityCompare = Double.compare(similarity2, similarity1);
            if (similarityCompare != 0) return similarityCompare;
            
            return s1.compareTo(s2); // Fallback to natural ordering
        }
        
        private double calculateSimilarity(String s) {
            // Simple heuristic: prefer shorter names with common letters
            String common = "aeiou";
            long commonLetters = s.toLowerCase().chars()
                                  .filter(c -> common.indexOf(c) >= 0)
                                  .count();
            
            return commonLetters / (double) s.length();
        }
    }
    
    static class VersionComparator implements Comparator<String> {
        @Override
        public int compare(String v1, String v2) {
            String[] parts1 = v1.split("[-.]");
            String[] parts2 = v2.split("[-.]");
            
            int maxLength = Math.max(parts1.length, parts2.length);
            
            for (int i = 0; i < maxLength; i++) {
                String part1 = i < parts1.length ? parts1[i] : "0";
                String part2 = i < parts2.length ? parts2[i] : "0";
                
                // Try to compare as numbers first
                try {
                    int num1 = Integer.parseInt(part1);
                    int num2 = Integer.parseInt(part2);
                    int numCompare = Integer.compare(num1, num2);
                    if (numCompare != 0) return numCompare;
                } catch (NumberFormatException e) {
                    // Compare as strings if not numbers
                    int stringCompare = part1.compareTo(part2);
                    if (stringCompare != 0) return stringCompare;
                }
            }
            
            return 0;
        }
    }
    
    static class NaturalOrderComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            // Natural ordering: handle numbers within strings correctly
            return naturalCompare(s1, s2);
        }
        
        private int naturalCompare(String s1, String s2) {
            int i1 = 0, i2 = 0;
            
            while (i1 < s1.length() && i2 < s2.length()) {
                char c1 = s1.charAt(i1);
                char c2 = s2.charAt(i2);
                
                if (Character.isDigit(c1) && Character.isDigit(c2)) {
                    // Extract and compare numbers
                    int num1 = 0, num2 = 0;
                    
                    while (i1 < s1.length() && Character.isDigit(s1.charAt(i1))) {
                        num1 = num1 * 10 + (s1.charAt(i1) - '0');
                        i1++;
                    }
                    
                    while (i2 < s2.length() && Character.isDigit(s2.charAt(i2))) {
                        num2 = num2 * 10 + (s2.charAt(i2) - '0');
                        i2++;
                    }
                    
                    int numCompare = Integer.compare(num1, num2);
                    if (numCompare != 0) return numCompare;
                } else {
                    // Compare characters
                    int charCompare = Character.compare(c1, c2);
                    if (charCompare != 0) return charCompare;
                    
                    i1++;
                    i2++;
                }
            }
            
            return Integer.compare(s1.length(), s2.length());
        }
    }
    
    static class TaskPriorityComparator implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            // Priority = urgency (lower number = higher priority) + importance (higher number = higher priority)
            double priority1 = (4 - t1.getUrgency()) + (t1.getImportance() / 10.0);
            double priority2 = (4 - t2.getUrgency()) + (t2.getImportance() / 10.0);
            
            return Double.compare(priority2, priority1); // Higher priority first
        }
    }
    
    static class StatefulComparator implements Comparator<String> {
        private String preferredPrefix;
        
        public StatefulComparator(String preferredPrefix) {
            this.preferredPrefix = preferredPrefix;
        }
        
        public void setPreferredPrefix(String preferredPrefix) {
            this.preferredPrefix = preferredPrefix;
        }
        
        @Override
        public int compare(String s1, String s2) {
            boolean s1Preferred = s1.toLowerCase().startsWith(preferredPrefix.toLowerCase());
            boolean s2Preferred = s2.toLowerCase().startsWith(preferredPrefix.toLowerCase());
            
            if (s1Preferred && !s2Preferred) return -1;
            if (!s1Preferred && s2Preferred) return 1;
            
            return s1.compareTo(s2); // Natural order for same preference
        }
    }
}
```

## Interview Questions & Answers

**Q1: Explain the difference between Comparable and Comparator interfaces.**
**A:** Comparable defines natural ordering within a class (compareTo method), while Comparator defines external comparison logic (compare method). Comparable: one sorting strategy per class. Comparator: multiple strategies, better separation of concerns.

**Q2: What does the compare() method return and what do the values mean?**
**A:** compare() returns int: negative (first object < second), zero (equal), positive (first object > second). Common implementation: return Integer.compare(o1.field, o2.field) or o1.field.compareTo(o2.field).

**Q3: How do you handle null values in comparators?**
**A:** Use Comparator.nullsFirst() or Comparator.nullsLast() to wrap existing comparators. For custom logic, manually check for nulls first: if (o1 == null) return o2 == null ? 0 : -1; if (o2 == null) return 1;

**Q4: Explain comparator chaining with thenComparing().**
**A:** thenComparing() creates compound comparators for multi-criteria sorting. Example: Comparator.comparing(Person::getAge).thenComparing(Person::getName) sorts by age first, then by name for same age.

**Q5: What are the performance implications of different comparator implementations?**
**A:** Lambda expressions have slight overhead vs method references. Avoid expensive computations in compare(). Cache computed values. Use primitive comparators (comparingInt) vs generic ones. Consider comparator reuse vs recreation.

**Q6: How do you create thread-safe comparator implementations?**
**A:** Stateless comparators are inherently thread-safe. For stateful comparators, use immutable state or proper synchronization. Prefer functional approach with pure functions over mutable state.

**Q7: Explain the contract that comparators must satisfy.**
**A:** Reflexivity: compare(x,x) == 0. Antisymmetry: compare(x,y) == -compare(y,x). Transitivity: if compare(x,y) > 0 and compare(y,z) > 0, then compare(x,z) > 0. Consistency with equals recommended.

**Q8: When would you use Comparator.comparing() vs custom compare() implementation?**
**A:** Use Comparator.comparing() for simple field extraction. Use custom compare() for complex logic, multiple conditions, performance-critical code, or when you need fine-grained control over comparison logic.

## Performance Best Practices

```java
// Performance-optimized comparator patterns

class PerformanceOptimizedComparators {
    
    // ✅ GOOD: Use primitive comparators when possible
    public static final Comparator<Employee> BY_SALARY_OPTIMIZED = 
        Comparator.comparingInt(Employee::getSalary);
    
    // ❌ AVOID: Generic comparison for primitives
    public static final Comparator<Employee> BY_SALARY_SLOW = 
        Comparator.comparing(Employee::getSalary);
    
    // ✅ GOOD: Cache expensive computations
    public static Comparator<String> createCachedLengthComparator() {
        Map<String, Integer> lengthCache = new ConcurrentHashMap<>();
        return Comparator.comparing(s -> lengthCache.computeIfAbsent(s, String::length));
    }
    
    // ✅ GOOD: Reuse comparator instances
    private static final Comparator<Person> NAME_COMPARATOR = 
        Comparator.comparing(Person::getName);
    
    // ✅ GOOD: Pre-compute complex sorting keys
    public static List<Employee> sortByComplexCriteria(List<Employee> employees) {
        return employees.stream()
                       .map(emp -> new SortableEmployee(emp, computeComplexScore(emp)))
                       .sorted(Comparator.comparing(SortableEmployee::getScore))
                       .map(SortableEmployee::getEmployee)
                       .collect(Collectors.toList());
    }
    
    private static double computeComplexScore(Employee emp) {
        // Expensive computation done once per employee
        return emp.getSalary() * Math.log(emp.getName().length()) / 1000.0;
    }
    
    static class SortableEmployee {
        private final Employee employee;
        private final double score;
        
        public SortableEmployee(Employee employee, double score) {
            this.employee = employee;
            this.score = score;
        }
        
        public Employee getEmployee() { return employee; }
        public double getScore() { return score; }
    }
}
```

## Complete Part 2 Demo

```java
public class ComparatorPart2Demo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPARATOR INTERFACE - PART 2 DEMONSTRATION ===");
        
        AdvancedComparatorFeatures advanced = new AdvancedComparatorFeatures();
        advanced.demonstrateAdvancedFeatures();
        
        System.out.println("\n=== PART 2 DEMONSTRATION COMPLETED ===");
        printFinalBestPractices();
    }
    
    private static void printFinalBestPractices() {
        System.out.println("\n🎯 COMPARATOR BEST PRACTICES:");
        System.out.println("✅ Use Comparator.comparing() for simple field extraction");
        System.out.println("✅ Handle nulls explicitly with nullsFirst()/nullsLast()");
        System.out.println("✅ Chain comparators with thenComparing() for multi-criteria sorting");
        System.out.println("✅ Prefer method references over lambda when possible");
        System.out.println("✅ Use primitive comparators (comparingInt) for better performance");
        System.out.println("✅ Cache expensive computations in comparison logic");
        System.out.println("✅ Reuse comparator instances instead of creating new ones");
        System.out.println("✅ Consider using sorted streams for complex transformations");
        
        System.out.println("\n❌ AVOID:");
        System.out.println("❌ Expensive operations inside compare() methods");
        System.out.println("❌ Violating the comparator contract (reflexivity, transitivity)");
        System.out.println("❌ Creating new comparators for every sort operation");
        System.out.println("❌ Ignoring null handling in custom comparators");
        System.out.println("❌ Using object comparison when primitive comparison is available");
        System.out.println("❌ Complex stateful comparators without thread safety consideration");
    }
}
```

## Key Takeaways

1. **Comparator enables flexible custom sorting** beyond natural ordering
2. **Method chaining supports multi-criteria sorting** with thenComparing()
3. **Null handling is crucial** for robust sorting implementations
4. **Performance optimization matters** for large datasets and frequent sorting
5. **Functional composition** enables reusable and maintainable sorting logic
6. **Custom implementations** handle domain-specific requirements
7. **Thread safety considerations** important for concurrent usage
8. **Caching strategies** improve performance for expensive computations
9. **Primitive comparators** offer better performance than generic ones
10. **Complex sorting scenarios** require sophisticated comparison strategies

---

*Complete Summary: Comparator Interface is like having a world-class sorting laboratory - from simple alphabetical filing to complex multi-dimensional analysis, it provides all the tools and techniques needed for any sorting challenge in professional software development!*