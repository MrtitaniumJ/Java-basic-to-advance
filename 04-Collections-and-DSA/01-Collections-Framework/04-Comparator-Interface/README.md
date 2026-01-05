# Comparator Interface - Part 1: Fundamentals

## Simple Explanation

Think of **Comparator Interface** as **the custom sorting instructions for collections**:

- **Comparator** = Custom rule book for how to compare and sort objects
- **compare()** = Method that decides which object comes first
- **Natural ordering** = Default sorting (alphabetical for strings, numerical for numbers)
- **Custom ordering** = Your own sorting rules (by name, age, price, etc.)
- **Multiple comparators** = Different ways to sort the same data

### Real-World Analogies
- **Comparator** = Judge at a competition who decides rankings
- **compare() method** = Scoring criteria (speed vs style vs creativity)
- **Natural ordering** = Standard alphabetical filing system
- **Custom ordering** = Specialized filing (by date, priority, department)
- **Chained comparators** = Multiple judging criteria combined

## Professional Definition

**Comparator Interface** is a functional interface that defines a comparison function used to impose a total ordering on collections of objects. It enables custom sorting logic separate from the natural ordering defined by Comparable, supporting multiple comparison strategies for the same object type and enabling flexible, reusable sorting algorithms.

## Comparator vs Comparable

```
Comparison Approaches
├── Comparable<T> (Internal Comparison)
│   ├── Implemented by the class itself
│   ├── compareTo() method defines natural ordering
│   ├── Single comparison logic per class
│   └── Used automatically by sorting algorithms
└── Comparator<T> (External Comparison)
    ├── Separate interface for comparison logic
    ├── compare() method defines custom ordering
    ├── Multiple comparison strategies possible
    └── Can be passed to sorting methods
```

## Basic Comparator Usage

```java
// Basic Comparator demonstration

import java.util.*;
import java.util.function.Function;

class BasicComparatorUsage {
    
    public void demonstrateBasicComparators() {
        System.out.println("=== BASIC COMPARATOR USAGE DEMONSTRATION ===");
        
        demonstrateNaturalVsCustomOrdering();
        demonstrateComparatorMethods();
        demonstrateStringComparators();
        demonstrateNumericComparators();
        demonstrateComparatorCreation();
        demonstrateComparatorChaining();
    }
    
    // Natural vs Custom ordering
    public void demonstrateNaturalVsCustomOrdering() {
        System.out.println("\n--- Natural vs Custom Ordering ---");
        
        List<String> fruits = new ArrayList<>(Arrays.asList("Banana", "Apple", "Cherry", "Date"));
        System.out.printf("🍎 Original fruits: %s%n", fruits);
        
        // Natural ordering (alphabetical)
        System.out.println("\n🔤 Natural alphabetical ordering:");
        List<String> naturalOrder = new ArrayList<>(fruits);
        Collections.sort(naturalOrder);
        System.out.printf("  Sorted: %s%n", naturalOrder);
        
        // Custom ordering by length
        System.out.println("\n📏 Custom ordering by length:");
        List<String> lengthOrder = new ArrayList<>(fruits);
        Collections.sort(lengthOrder, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
        System.out.printf("  Sorted by length: %s%n", lengthOrder);
        
        // Custom ordering by length (lambda)
        System.out.println("\n📏 Same ordering with lambda:");
        List<String> lambdaOrder = new ArrayList<>(fruits);
        Collections.sort(lambdaOrder, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.printf("  Lambda sorted: %s%n", lambdaOrder);
        
        // Reverse natural ordering
        System.out.println("\n🔄 Reverse alphabetical ordering:");
        List<String> reverseOrder = new ArrayList<>(fruits);
        Collections.sort(reverseOrder, Collections.reverseOrder());
        System.out.printf("  Reverse sorted: %s%n", reverseOrder);
        
        // People example - multiple sorting criteria
        System.out.println("\n👤 People sorting example:");
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, 75000),
            new Person("Bob", 30, 80000),
            new Person("Charlie", 25, 70000),
            new Person("Diana", 28, 85000)
        );
        
        System.out.printf("  Original people: %s%n", people);
        
        // Sort by age
        List<Person> byAge = new ArrayList<>(people);
        Collections.sort(byAge, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p1.getAge(), p2.getAge());
            }
        });
        System.out.printf("  By age: %s%n", byAge);
        
        // Sort by salary (descending)
        List<Person> bySalary = new ArrayList<>(people);
        Collections.sort(bySalary, (p1, p2) -> Integer.compare(p2.getSalary(), p1.getSalary()));
        System.out.printf("  By salary (desc): %s%n", bySalary);
        
        System.out.println("✅ Comparator enables flexible custom sorting beyond natural ordering");
    }
    
    // Core comparator methods
    public void demonstrateComparatorMethods() {
        System.out.println("\n--- Core Comparator Methods ---");
        
        List<String> words = Arrays.asList("elephant", "cat", "butterfly", "dog");
        System.out.printf("🐾 Animals: %s%n", words);
        
        // Compare method return values
        System.out.println("\n🔢 Understanding compare() return values:");
        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        
        String word1 = "cat", word2 = "elephant", word3 = "dog";
        
        int result1 = lengthComparator.compare(word1, word2);
        int result2 = lengthComparator.compare(word2, word1);
        int result3 = lengthComparator.compare(word1, word3);
        
        System.out.printf("  compare('%s', '%s') = %d (first is shorter)%n", word1, word2, result1);
        System.out.printf("  compare('%s', '%s') = %d (first is longer)%n", word2, word1, result2);
        System.out.printf("  compare('%s', '%s') = %d (same length)%n", word1, word3, result3);
        
        // Sorting with the comparator
        System.out.println("\n🔄 Sorting with length comparator:");
        List<String> sortedByLength = new ArrayList<>(words);
        Collections.sort(sortedByLength, lengthComparator);
        System.out.printf("  Sorted by length: %s%n", sortedByLength);
        
        // Equals method (rarely overridden)
        System.out.println("\n⚖️ Comparator equality:");
        Comparator<String> comp1 = (s1, s2) -> s1.compareTo(s2);
        Comparator<String> comp2 = String::compareTo;
        Comparator<String> comp3 = Comparator.naturalOrder();
        
        // Note: These might not be equal as objects, but produce same results
        System.out.printf("  comp1.equals(comp2): %s%n", comp1.equals(comp2));
        System.out.printf("  comp1.compare('a', 'b'): %d%n", comp1.compare("a", "b"));
        System.out.printf("  comp2.compare('a', 'b'): %d%n", comp2.compare("a", "b"));
        System.out.printf("  comp3.compare('a', 'b'): %d%n", comp3.compare("a", "b"));
        
        System.out.println("✅ compare() method is the heart of Comparator functionality");
    }
    
    // String comparators
    public void demonstrateStringComparators() {
        System.out.println("\n--- String Comparators ---");
        
        List<String> mixedCaseWords = Arrays.asList("Apple", "banana", "Cherry", "date", "ELEPHANT");
        System.out.printf("📝 Mixed case words: %s%n", mixedCaseWords);
        
        // Case-sensitive (default)
        System.out.println("\n🔤 Case-sensitive ordering:");
        List<String> caseSensitive = new ArrayList<>(mixedCaseWords);
        Collections.sort(caseSensitive);
        System.out.printf("  Case-sensitive: %s%n", caseSensitive);
        
        // Case-insensitive
        System.out.println("\n🔤 Case-insensitive ordering:");
        List<String> caseInsensitive = new ArrayList<>(mixedCaseWords);
        Collections.sort(caseInsensitive, String.CASE_INSENSITIVE_ORDER);
        System.out.printf("  Case-insensitive: %s%n", caseInsensitive);
        
        // Custom case-insensitive with lambda
        System.out.println("\n🔤 Custom case-insensitive comparator:");
        List<String> customCase = new ArrayList<>(mixedCaseWords);
        Collections.sort(customCase, (s1, s2) -> s1.toLowerCase().compareTo(s2.toLowerCase()));
        System.out.printf("  Custom case-insensitive: %s%n", customCase);
        
        // By string length
        System.out.println("\n📏 By string length:");
        List<String> byLength = new ArrayList<>(mixedCaseWords);
        Collections.sort(byLength, Comparator.comparing(String::length));
        System.out.printf("  By length: %s%n", byLength);
        
        // By last character
        System.out.println("\n🔚 By last character:");
        List<String> byLastChar = new ArrayList<>(mixedCaseWords);
        Collections.sort(byLastChar, (s1, s2) -> {
            char last1 = s1.charAt(s1.length() - 1);
            char last2 = s2.charAt(s2.length() - 1);
            return Character.compare(last1, last2);
        });
        System.out.printf("  By last character: %s%n", byLastChar);
        
        // Null-safe string comparator
        System.out.println("\n🛡️ Null-safe string comparator:");
        List<String> withNulls = new ArrayList<>(Arrays.asList("apple", null, "banana", null, "cherry"));
        System.out.printf("  With nulls: %s%n", withNulls);
        
        // Sort with nulls last
        List<String> nullsLast = new ArrayList<>(withNulls);
        Collections.sort(nullsLast, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        System.out.printf("  Nulls last: %s%n", nullsLast);
        
        // Sort with nulls first
        List<String> nullsFirst = new ArrayList<>(withNulls);
        Collections.sort(nullsFirst, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        System.out.printf("  Nulls first: %s%n", nullsFirst);
        
        System.out.println("✅ String comparators handle various text sorting scenarios");
    }
    
    // Numeric comparators
    public void demonstrateNumericComparators() {
        System.out.println("\n--- Numeric Comparators ---");
        
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6);
        System.out.printf("🔢 Numbers: %s%n", numbers);
        
        // Natural numeric ordering
        System.out.println("\n🔢 Natural numeric ordering:");
        List<Integer> ascending = new ArrayList<>(numbers);
        Collections.sort(ascending);
        System.out.printf("  Ascending: %s%n", ascending);
        
        // Descending order
        System.out.println("\n🔢 Descending order:");
        List<Integer> descending = new ArrayList<>(numbers);
        Collections.sort(descending, Collections.reverseOrder());
        System.out.printf("  Descending: %s%n", descending);
        
        // Using Integer.compare()
        System.out.println("\n🔢 Using Integer.compare():");
        List<Integer> integerCompare = new ArrayList<>(numbers);
        Collections.sort(integerCompare, (i1, i2) -> Integer.compare(i1, i2));
        System.out.printf("  Integer.compare: %s%n", integerCompare);
        
        // Absolute value comparison
        System.out.println("\n🔢 By absolute value:");
        List<Integer> withNegatives = Arrays.asList(-5, 2, -8, 1, 9, -3, 7, -4, 6);
        System.out.printf("  With negatives: %s%n", withNegatives);
        
        List<Integer> byAbsValue = new ArrayList<>(withNegatives);
        Collections.sort(byAbsValue, (i1, i2) -> Integer.compare(Math.abs(i1), Math.abs(i2)));
        System.out.printf("  By absolute value: %s%n", byAbsValue);
        
        // Even numbers first, then odd
        System.out.println("\n🔢 Even numbers first, then odd:");
        List<Integer> evenFirst = new ArrayList<>(numbers);
        Collections.sort(evenFirst, (i1, i2) -> {
            boolean i1Even = i1 % 2 == 0;
            boolean i2Even = i2 % 2 == 0;
            
            if (i1Even && !i2Even) return -1; // i1 even, i2 odd -> i1 first
            if (!i1Even && i2Even) return 1;  // i1 odd, i2 even -> i2 first
            return Integer.compare(i1, i2);   // same parity -> natural order
        });
        System.out.printf("  Even first: %s%n", evenFirst);
        
        // Double comparison with precision
        System.out.println("\n🔢 Double comparison:");
        List<Double> doubles = Arrays.asList(3.14, 2.71, 1.41, 2.718, 3.141);
        System.out.printf("  Doubles: %s%n", doubles);
        
        List<Double> sortedDoubles = new ArrayList<>(doubles);
        Collections.sort(sortedDoubles);
        System.out.printf("  Sorted doubles: %s%n", sortedDoubles);
        
        // Custom precision comparison (2 decimal places)
        List<Double> precisionSorted = new ArrayList<>(doubles);
        Collections.sort(precisionSorted, (d1, d2) -> {
            double rounded1 = Math.round(d1 * 100.0) / 100.0;
            double rounded2 = Math.round(d2 * 100.0) / 100.0;
            return Double.compare(rounded1, rounded2);
        });
        System.out.printf("  Precision sorted (2 decimals): %s%n", precisionSorted);
        
        System.out.println("✅ Numeric comparators handle various number comparison scenarios");
    }
    
    // Comparator creation methods
    public void demonstrateComparatorCreation() {
        System.out.println("\n--- Comparator Creation Methods ---");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, 75000),
            new Person("Bob", 30, 80000),
            new Person("Charlie", 25, 70000),
            new Person("Diana", 28, 85000)
        );
        
        System.out.printf("👥 People: %s%n", people);
        
        // Anonymous class
        System.out.println("\n🎭 Anonymous class comparator:");
        Comparator<Person> ageComparatorAnon = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p1.getAge(), p2.getAge());
            }
        };
        
        List<Person> sortedByAgeAnon = new ArrayList<>(people);
        Collections.sort(sortedByAgeAnon, ageComparatorAnon);
        System.out.printf("  By age (anonymous): %s%n", sortedByAgeAnon);
        
        // Lambda expression
        System.out.println("\n🔗 Lambda expression comparator:");
        Comparator<Person> ageComparatorLambda = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
        
        List<Person> sortedByAgeLambda = new ArrayList<>(people);
        Collections.sort(sortedByAgeLambda, ageComparatorLambda);
        System.out.printf("  By age (lambda): %s%n", sortedByAgeLambda);
        
        // Method reference
        System.out.println("\n📍 Method reference comparator:");
        Comparator<Person> nameComparatorRef = Comparator.comparing(Person::getName);
        
        List<Person> sortedByNameRef = new ArrayList<>(people);
        Collections.sort(sortedByNameRef, nameComparatorRef);
        System.out.printf("  By name (method ref): %s%n", sortedByNameRef);
        
        // Static factory methods
        System.out.println("\n🏭 Static factory method comparators:");
        
        // Natural order
        List<String> words = Arrays.asList("zebra", "apple", "banana");
        List<String> naturalOrder = new ArrayList<>(words);
        Collections.sort(naturalOrder, Comparator.naturalOrder());
        System.out.printf("  Natural order: %s%n", naturalOrder);
        
        // Reverse order
        List<String> reverseOrder = new ArrayList<>(words);
        Collections.sort(reverseOrder, Comparator.reverseOrder());
        System.out.printf("  Reverse order: %s%n", reverseOrder);
        
        // Comparing with key extractor
        List<Person> bySalaryDesc = new ArrayList<>(people);
        Collections.sort(bySalaryDesc, Comparator.comparing(Person::getSalary).reversed());
        System.out.printf("  By salary desc: %s%n", bySalaryDesc);
        
        // Specialized comparing methods
        System.out.println("\n🎯 Specialized comparing methods:");
        
        // comparingInt
        List<Person> byAgeInt = new ArrayList<>(people);
        Collections.sort(byAgeInt, Comparator.comparingInt(Person::getAge));
        System.out.printf("  By age (comparingInt): %s%n", byAgeInt);
        
        // comparingLong
        List<Person> bySalaryLong = new ArrayList<>(people);
        Collections.sort(bySalaryLong, Comparator.comparingLong(p -> (long)p.getSalary()));
        System.out.printf("  By salary (comparingLong): %s%n", bySalaryLong);
        
        // comparingDouble
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 3.7),
            new Employee("Bob", 4.2),
            new Employee("Charlie", 3.9)
        );
        
        List<Employee> byRating = new ArrayList<>(employees);
        Collections.sort(byRating, Comparator.comparingDouble(Employee::getRating));
        System.out.printf("  By rating (comparingDouble): %s%n", byRating);
        
        System.out.println("✅ Multiple ways to create comparators for different scenarios");
    }
    
    // Comparator chaining
    public void demonstrateComparatorChaining() {
        System.out.println("\n--- Comparator Chaining ---");
        
        List<Student> students = Arrays.asList(
            new Student("Alice", "Computer Science", 3.8),
            new Student("Bob", "Mathematics", 3.6),
            new Student("Charlie", "Computer Science", 3.9),
            new Student("Diana", "Mathematics", 3.8),
            new Student("Eve", "Physics", 3.7),
            new Student("Frank", "Computer Science", 3.6)
        );
        
        System.out.printf("🎓 Students: %s%n", students);
        
        // Simple chaining - department then GPA
        System.out.println("\n🔗 Simple chaining (department, then GPA):");
        Comparator<Student> deptThenGPA = Comparator.comparing(Student::getDepartment)
                                                   .thenComparing(Student::getGpa);
        
        List<Student> sortedDeptGPA = new ArrayList<>(students);
        Collections.sort(sortedDeptGPA, deptThenGPA);
        System.out.printf("  By department, then GPA: %s%n", sortedDeptGPA);
        
        // Reverse chaining - GPA descending, then name
        System.out.println("\n🔗 Reverse chaining (GPA desc, then name):");
        Comparator<Student> gpaDescThenName = Comparator.comparing(Student::getGpa).reversed()
                                                       .thenComparing(Student::getName);
        
        List<Student> sortedGPAName = new ArrayList<>(students);
        Collections.sort(sortedGPAName, gpaDescThenName);
        System.out.printf("  By GPA desc, then name: %s%n", sortedGPAName);
        
        // Complex chaining - multiple criteria
        System.out.println("\n🔗 Complex chaining (dept, GPA desc, name):");
        Comparator<Student> complexChain = Comparator.comparing(Student::getDepartment)
                                                    .thenComparing(Student::getGpa, Collections.reverseOrder())
                                                    .thenComparing(Student::getName);
        
        List<Student> complexSorted = new ArrayList<>(students);
        Collections.sort(complexSorted, complexChain);
        System.out.printf("  Complex chain: %s%n", complexSorted);
        
        // Numeric chaining methods
        System.out.println("\n🔗 Specialized chaining methods:");
        
        // thenComparingInt
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, 75000),
            new Person("Bob", 25, 80000),
            new Person("Charlie", 25, 70000)
        );
        
        Comparator<Person> ageThenSalary = Comparator.comparingInt(Person::getAge)
                                                    .thenComparingInt(Person::getSalary);
        
        List<Person> sortedAgeSalary = new ArrayList<>(people);
        Collections.sort(sortedAgeSalary, ageThenSalary);
        System.out.printf("  Same age, by salary: %s%n", sortedAgeSalary);
        
        // Null-safe chaining
        System.out.println("\n🛡️ Null-safe chaining:");
        List<Student> studentsWithNull = new ArrayList<>(students);
        studentsWithNull.add(new Student(null, "Physics", 3.5));
        studentsWithNull.add(new Student("Grace", null, 3.9));
        
        System.out.printf("  With nulls: %s%n", studentsWithNull);
        
        Comparator<Student> nullSafeChain = Comparator.comparing(Student::getName, 
                                                                Comparator.nullsLast(String::compareTo))
                                                     .thenComparing(Student::getDepartment, 
                                                                   Comparator.nullsLast(String::compareTo))
                                                     .thenComparing(Student::getGpa);
        
        List<Student> nullSafeSorted = new ArrayList<>(studentsWithNull);
        Collections.sort(nullSafeSorted, nullSafeChain);
        System.out.printf("  Null-safe sorted: %s%n", nullSafeSorted);
        
        System.out.println("✅ Comparator chaining enables sophisticated multi-criteria sorting");
    }
    
    // Helper classes for demonstrations
    static class Person {
        private final String name;
        private final int age;
        private final int salary;
        
        public Person(String name, int age, int salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public int getSalary() { return salary; }
        
        @Override
        public String toString() {
            return String.format("%s(%d,$%,d)", name, age, salary);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Person)) return false;
            Person person = (Person) obj;
            return age == person.age && salary == person.salary && 
                   Objects.equals(name, person.name);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name, age, salary);
        }
    }
    
    static class Employee {
        private final String name;
        private final double rating;
        
        public Employee(String name, double rating) {
            this.name = name;
            this.rating = rating;
        }
        
        public String getName() { return name; }
        public double getRating() { return rating; }
        
        @Override
        public String toString() {
            return String.format("%s(%.1f)", name, rating);
        }
    }
    
    static class Student {
        private final String name;
        private final String department;
        private final double gpa;
        
        public Student(String name, String department, double gpa) {
            this.name = name;
            this.department = department;
            this.gpa = gpa;
        }
        
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getGpa() { return gpa; }
        
        @Override
        public String toString() {
            return String.format("%s(%s,%.1f)", name, department, gpa);
        }
    }
}
```

## Practical Examples

```java
// Practical Comparator examples for common scenarios

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class PracticalComparatorExamples {
    
    public void demonstratePracticalExamples() {
        System.out.println("=== PRACTICAL COMPARATOR EXAMPLES ===");
        
        demonstrateEmployeeManagement();
        demonstrateProductCatalog();
        demonstrateFileSystemSorting();
        demonstrateScoreboardRanking();
        demonstrateCustomBusinessLogic();
    }
    
    // Employee management system
    public void demonstrateEmployeeManagement() {
        System.out.println("\n--- Employee Management System ---");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", "Engineering", 85000, LocalDate.of(2019, 3, 15)),
            new Employee("Bob Smith", "Marketing", 65000, LocalDate.of(2020, 1, 10)),
            new Employee("Charlie Brown", "Engineering", 90000, LocalDate.of(2018, 7, 20)),
            new Employee("Diana Prince", "HR", 70000, LocalDate.of(2021, 5, 5)),
            new Employee("Eve Adams", "Engineering", 78000, LocalDate.of(2019, 11, 30)),
            new Employee("Frank Miller", "Marketing", 72000, LocalDate.of(2020, 8, 14))
        );
        
        System.out.printf("👥 Company employees: %s%n", employees);
        
        // Sort by department, then by salary descending
        System.out.println("\n🏢 By department, then salary (high to low):");
        List<Employee> deptSalarySorted = new ArrayList<>(employees);
        Collections.sort(deptSalarySorted, 
                        Comparator.comparing(Employee::getDepartment)
                                  .thenComparing(Employee::getSalary, Collections.reverseOrder()));
        
        for (Employee emp : deptSalarySorted) {
            System.out.printf("  %s%n", emp);
        }
        
        // Sort by hire date (seniority)
        System.out.println("\n📅 By seniority (hire date):");
        List<Employee> bySeniority = new ArrayList<>(employees);
        Collections.sort(bySeniority, Comparator.comparing(Employee::getHireDate));
        
        for (Employee emp : bySeniority) {
            System.out.printf("  %s%n", emp);
        }
        
        // Find top performers by salary in each department
        System.out.println("\n🏆 Top performer by department:");
        Map<String, Employee> topByDept = employees.stream()
                                                  .collect(Collectors.groupingBy(
                                                      Employee::getDepartment,
                                                      Collectors.maxBy(Comparator.comparing(Employee::getSalary))
                                                  ))
                                                  .entrySet().stream()
                                                  .collect(Collectors.toMap(
                                                      Map.Entry::getKey,
                                                      e -> e.getValue().orElse(null)
                                                  ));
        
        topByDept.forEach((dept, emp) -> 
            System.out.printf("  %s: %s%n", dept, emp));
        
        // Custom promotion eligibility (2+ years, Engineering, 80k+)
        System.out.println("\n🚀 Promotion eligible (Engineering, 2+ years, 80k+):");
        LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
        
        List<Employee> promotionEligible = employees.stream()
                                                   .filter(e -> e.getDepartment().equals("Engineering"))
                                                   .filter(e -> e.getHireDate().isBefore(twoYearsAgo))
                                                   .filter(e -> e.getSalary() >= 80000)
                                                   .sorted(Comparator.comparing(Employee::getSalary).reversed())
                                                   .collect(Collectors.toList());
        
        promotionEligible.forEach(e -> System.out.printf("  %s%n", e));
        
        System.out.println("✅ Employee management uses complex sorting criteria");
    }
    
    // Product catalog management
    public void demonstrateProductCatalog() {
        System.out.println("\n--- Product Catalog Management ---");
        
        List<Product> products = Arrays.asList(
            new Product("Laptop", "Electronics", 999.99, 4.5, 150),
            new Product("Book", "Books", 19.99, 4.8, 500),
            new Product("Headphones", "Electronics", 199.99, 4.3, 75),
            new Product("Coffee Mug", "Home", 12.99, 4.6, 200),
            new Product("Smartphone", "Electronics", 799.99, 4.4, 80),
            new Product("Novel", "Books", 14.99, 4.7, 300),
            new Product("Desk Lamp", "Home", 49.99, 4.2, 120)
        );
        
        System.out.printf("🛍️ Product catalog: %s%n", products);
        
        // Sort by category, then by rating descending
        System.out.println("\n⭐ By category, then rating (best first):");
        List<Product> categoryRating = new ArrayList<>(products);
        Collections.sort(categoryRating,
                        Comparator.comparing(Product::getCategory)
                                  .thenComparing(Product::getRating, Collections.reverseOrder()));
        
        categoryRating.forEach(p -> System.out.printf("  %s%n", p));
        
        // Sort by price range (budget, mid-range, premium)
        System.out.println("\n💰 By price range:");
        List<Product> byPriceRange = new ArrayList<>(products);
        Collections.sort(byPriceRange, (p1, p2) -> {
            String range1 = getPriceRange(p1.getPrice());
            String range2 = getPriceRange(p2.getPrice());
            
            // First by price range, then by price within range
            int rangeCompare = range1.compareTo(range2);
            if (rangeCompare != 0) return rangeCompare;
            
            return Double.compare(p1.getPrice(), p2.getPrice());
        });
        
        byPriceRange.forEach(p -> 
            System.out.printf("  [%s] %s%n", getPriceRange(p.getPrice()), p));
        
        // Best sellers (high rating + high stock)
        System.out.println("\n🔥 Best sellers (rating * availability score):");
        List<Product> bestSellers = new ArrayList<>(products);
        Collections.sort(bestSellers, (p1, p2) -> {
            double score1 = p1.getRating() * getAvailabilityScore(p1.getStock());
            double score2 = p2.getRating() * getAvailabilityScore(p2.getStock());
            return Double.compare(score2, score1); // Descending
        });
        
        bestSellers.forEach(p -> {
            double score = p.getRating() * getAvailabilityScore(p.getStock());
            System.out.printf("  %.2f - %s%n", score, p);
        });
        
        // Budget-friendly options (under $100, sorted by value)
        System.out.println("\n💸 Budget options (under $100, best value):");
        List<Product> budgetOptions = products.stream()
                                             .filter(p -> p.getPrice() < 100)
                                             .sorted((p1, p2) -> {
                                                 double value1 = p1.getRating() / p1.getPrice();
                                                 double value2 = p2.getRating() / p2.getPrice();
                                                 return Double.compare(value2, value1);
                                             })
                                             .collect(Collectors.toList());
        
        budgetOptions.forEach(p -> {
            double valueScore = p.getRating() / p.getPrice();
            System.out.printf("  Value %.4f - %s%n", valueScore, p);
        });
        
        System.out.println("✅ Product catalogs use business-driven sorting logic");
    }
    
    private String getPriceRange(double price) {
        if (price < 50) return "Budget";
        else if (price < 500) return "Mid-range";
        else return "Premium";
    }
    
    private double getAvailabilityScore(int stock) {
        if (stock > 200) return 1.0;
        else if (stock > 100) return 0.8;
        else if (stock > 50) return 0.6;
        else return 0.4;
    }
    
    // File system sorting
    public void demonstrateFileSystemSorting() {
        System.out.println("\n--- File System Sorting ---");
        
        List<FileInfo> files = Arrays.asList(
            new FileInfo("document.pdf", 2048, "pdf", false),
            new FileInfo("photos", 0, "folder", true),
            new FileInfo("readme.txt", 156, "txt", false),
            new FileInfo("music", 0, "folder", true),
            new FileInfo("image.jpg", 5120, "jpg", false),
            new FileInfo("backup", 0, "folder", true),
            new FileInfo("script.py", 384, "py", false),
            new FileInfo("video.mp4", 51200, "mp4", false)
        );
        
        System.out.printf("📁 Files and folders: %s%n", files);
        
        // Directories first, then files, alphabetically
        System.out.println("\n📂 Directories first, then files:");
        List<FileInfo> directoriesFirst = new ArrayList<>(files);
        Collections.sort(directoriesFirst,
                        Comparator.comparing(FileInfo::isDirectory, Collections.reverseOrder())
                                  .thenComparing(FileInfo::getName));
        
        directoriesFirst.forEach(f -> 
            System.out.printf("  %s %s%n", f.isDirectory() ? "📁" : "📄", f));
        
        // By file extension, then by size
        System.out.println("\n🔧 By extension, then by size:");
        List<FileInfo> byExtensionSize = files.stream()
                                             .filter(f -> !f.isDirectory()) // Files only
                                             .sorted(Comparator.comparing(FileInfo::getExtension)
                                                               .thenComparing(FileInfo::getSize))
                                             .collect(Collectors.toList());
        
        byExtensionSize.forEach(f -> System.out.printf("  %s%n", f));
        
        // By size descending (largest first)
        System.out.println("\n📏 By size (largest first):");
        List<FileInfo> bySize = files.stream()
                                    .filter(f -> !f.isDirectory())
                                    .sorted(Comparator.comparing(FileInfo::getSize, Collections.reverseOrder()))
                                    .collect(Collectors.toList());
        
        bySize.forEach(f -> 
            System.out.printf("  %s (%s)%n", f, formatFileSize(f.getSize())));
        
        System.out.println("✅ File system sorting considers type, size, and naming");
    }
    
    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        else if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        else return (bytes / (1024 * 1024)) + " MB";
    }
    
    // Scoreboard ranking
    public void demonstrateScoreboardRanking() {
        System.out.println("\n--- Scoreboard Ranking ---");
        
        List<GameScore> scores = Arrays.asList(
            new GameScore("Alice", 1500, 8, 2),
            new GameScore("Bob", 1500, 7, 3),
            new GameScore("Charlie", 1450, 9, 1),
            new GameScore("Diana", 1600, 6, 4),
            new GameScore("Eve", 1550, 8, 2),
            new GameScore("Frank", 1500, 8, 2)
        );
        
        System.out.printf("🎮 Game scores: %s%n", scores);
        
        // Traditional ranking (score desc, wins desc, losses asc)
        System.out.println("\n🏆 Traditional ranking (score, wins, losses):");
        List<GameScore> traditional = new ArrayList<>(scores);
        Collections.sort(traditional,
                        Comparator.comparing(GameScore::getScore, Collections.reverseOrder())
                                  .thenComparing(GameScore::getWins, Collections.reverseOrder())
                                  .thenComparing(GameScore::getLosses));
        
        int rank = 1;
        for (GameScore score : traditional) {
            System.out.printf("  #%d %s%n", rank++, score);
        }
        
        // Win ratio ranking
        System.out.println("\n📊 By win ratio:");
        List<GameScore> byWinRatio = new ArrayList<>(scores);
        Collections.sort(byWinRatio, (s1, s2) -> {
            double ratio1 = (double) s1.getWins() / (s1.getWins() + s1.getLosses());
            double ratio2 = (double) s2.getWins() / (s2.getWins() + s2.getLosses());
            return Double.compare(ratio2, ratio1); // Descending
        });
        
        rank = 1;
        for (GameScore score : byWinRatio) {
            double ratio = (double) score.getWins() / (score.getWins() + score.getLosses());
            System.out.printf("  #%d %.2f%% %s%n", rank++, ratio * 100, score);
        }
        
        // Composite ranking (weighted score)
        System.out.println("\n🎯 Composite ranking (weighted):");
        List<GameScore> composite = new ArrayList<>(scores);
        Collections.sort(composite, (s1, s2) -> {
            // Weighted: 60% score, 30% win ratio, 10% total games
            double weight1 = 0.6 * s1.getScore() + 
                           0.3 * (s1.getWins() * 100.0 / (s1.getWins() + s1.getLosses())) +
                           0.1 * (s1.getWins() + s1.getLosses()) * 10;
            
            double weight2 = 0.6 * s2.getScore() + 
                           0.3 * (s2.getWins() * 100.0 / (s2.getWins() + s2.getLosses())) +
                           0.1 * (s2.getWins() + s2.getLosses()) * 10;
            
            return Double.compare(weight2, weight1); // Descending
        });
        
        rank = 1;
        for (GameScore score : composite) {
            double weight = 0.6 * score.getScore() + 
                          0.3 * (score.getWins() * 100.0 / (score.getWins() + score.getLosses())) +
                          0.1 * (score.getWins() + score.getLosses()) * 10;
            System.out.printf("  #%d %.1f %s%n", rank++, weight, score);
        }
        
        System.out.println("✅ Scoreboard ranking uses complex weighted criteria");
    }
    
    // Custom business logic
    public void demonstrateCustomBusinessLogic() {
        System.out.println("\n--- Custom Business Logic ---");
        
        // Priority task sorting
        List<Task> tasks = Arrays.asList(
            new Task("Fix critical bug", "HIGH", LocalDate.now().minusDays(1), 8),
            new Task("Write documentation", "LOW", LocalDate.now().plusDays(3), 4),
            new Task("Code review", "MEDIUM", LocalDate.now(), 2),
            new Task("Security audit", "HIGH", LocalDate.now().plusDays(1), 12),
            new Task("Performance optimization", "MEDIUM", LocalDate.now().plusDays(2), 6),
            new Task("Bug fixes", "LOW", LocalDate.now().minusDays(2), 3)
        );
        
        System.out.printf("📋 Task list: %s%n", tasks);
        
        // Priority-based sorting (overdue first, then priority, then effort)
        System.out.println("\n⚡ Priority sorting (overdue, priority, effort):");
        List<Task> prioritized = new ArrayList<>(tasks);
        Collections.sort(prioritized, (t1, t2) -> {
            // Overdue tasks first
            boolean t1Overdue = t1.getDueDate().isBefore(LocalDate.now());
            boolean t2Overdue = t2.getDueDate().isBefore(LocalDate.now());
            
            if (t1Overdue && !t2Overdue) return -1;
            if (!t1Overdue && t2Overdue) return 1;
            
            // Then by priority
            int priority1 = getPriorityValue(t1.getPriority());
            int priority2 = getPriorityValue(t2.getPriority());
            int priorityCompare = Integer.compare(priority2, priority1); // High priority first
            
            if (priorityCompare != 0) return priorityCompare;
            
            // Then by effort (easier tasks first)
            return Integer.compare(t1.getEffortHours(), t2.getEffortHours());
        });
        
        prioritized.forEach(t -> {
            String status = t.getDueDate().isBefore(LocalDate.now()) ? "⚠️ OVERDUE" : "📅 Pending";
            System.out.printf("  %s %s%n", status, t);
        });
        
        // Sprint planning (fit tasks within 40-hour sprint)
        System.out.println("\n🏃 Sprint planning (40-hour capacity):");
        List<Task> sprintTasks = prioritized.stream()
                                           .filter(t -> !t.getDueDate().isBefore(LocalDate.now().minusDays(7))) // Not too old
                                           .collect(Collectors.toList());
        
        int totalHours = 0;
        List<Task> sprint = new ArrayList<>();
        
        for (Task task : sprintTasks) {
            if (totalHours + task.getEffortHours() <= 40) {
                sprint.add(task);
                totalHours += task.getEffortHours();
            }
        }
        
        System.out.printf("  Sprint capacity used: %d/40 hours%n", totalHours);
        sprint.forEach(t -> System.out.printf("    %s%n", t));
        
        System.out.println("✅ Custom business logic drives practical sorting decisions");
    }
    
    private int getPriorityValue(String priority) {
        switch (priority) {
            case "HIGH": return 3;
            case "MEDIUM": return 2;
            case "LOW": return 1;
            default: return 0;
        }
    }
    
    // Helper classes for demonstrations
    static class Employee {
        private final String name;
        private final String department;
        private final int salary;
        private final LocalDate hireDate;
        
        public Employee(String name, String department, int salary, LocalDate hireDate) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.hireDate = hireDate;
        }
        
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public int getSalary() { return salary; }
        public LocalDate getHireDate() { return hireDate; }
        
        @Override
        public String toString() {
            return String.format("%s (%s, $%,d, hired %s)", 
                               name, department, salary, hireDate);
        }
    }
    
    static class Product {
        private final String name;
        private final String category;
        private final double price;
        private final double rating;
        private final int stock;
        
        public Product(String name, String category, double price, double rating, int stock) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.rating = rating;
            this.stock = stock;
        }
        
        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getPrice() { return price; }
        public double getRating() { return rating; }
        public int getStock() { return stock; }
        
        @Override
        public String toString() {
            return String.format("%s ($%.2f, ⭐%.1f, stock:%d)", 
                               name, price, rating, stock);
        }
    }
    
    static class FileInfo {
        private final String name;
        private final long size;
        private final String extension;
        private final boolean isDirectory;
        
        public FileInfo(String name, long size, String extension, boolean isDirectory) {
            this.name = name;
            this.size = size;
            this.extension = extension;
            this.isDirectory = isDirectory;
        }
        
        public String getName() { return name; }
        public long getSize() { return size; }
        public String getExtension() { return extension; }
        public boolean isDirectory() { return isDirectory; }
        
        @Override
        public String toString() {
            if (isDirectory) {
                return String.format("%s/", name);
            } else {
                return String.format("%s (%d bytes)", name, size);
            }
        }
    }
    
    static class GameScore {
        private final String player;
        private final int score;
        private final int wins;
        private final int losses;
        
        public GameScore(String player, int score, int wins, int losses) {
            this.player = player;
            this.score = score;
            this.wins = wins;
            this.losses = losses;
        }
        
        public String getPlayer() { return player; }
        public int getScore() { return score; }
        public int getWins() { return wins; }
        public int getLosses() { return losses; }
        
        @Override
        public String toString() {
            return String.format("%s (Score:%d, %dW-%dL)", player, score, wins, losses);
        }
    }
    
    static class Task {
        private final String description;
        private final String priority;
        private final LocalDate dueDate;
        private final int effortHours;
        
        public Task(String description, String priority, LocalDate dueDate, int effortHours) {
            this.description = description;
            this.priority = priority;
            this.dueDate = dueDate;
            this.effortHours = effortHours;
        }
        
        public String getDescription() { return description; }
        public String getPriority() { return priority; }
        public LocalDate getDueDate() { return dueDate; }
        public int getEffortHours() { return effortHours; }
        
        @Override
        public String toString() {
            return String.format("%s [%s] (Due:%s, %dh)", 
                               description, priority, dueDate, effortHours);
        }
    }
}
```

## Complete Part 1 Demo

```java
public class ComparatorPart1Demo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPARATOR INTERFACE - PART 1 DEMONSTRATION ===");
        
        // 1. Basic Comparator usage
        System.out.println("\n1. BASIC COMPARATOR USAGE");
        BasicComparatorUsage basic = new BasicComparatorUsage();
        basic.demonstrateBasicComparators();
        
        System.out.println("\n" + "=".repeat(60));
        
        // 2. Practical examples
        System.out.println("\n2. PRACTICAL COMPARATOR EXAMPLES");
        PracticalComparatorExamples practical = new PracticalComparatorExamples();
        practical.demonstratePracticalExamples();
        
        System.out.println("\n=== PART 1 DEMONSTRATION COMPLETED ===");
        printPart1Summary();
    }
    
    private static void printPart1Summary() {
        System.out.println("\n🎯 PART 1 KEY CONCEPTS:");
        System.out.println("✅ Comparator enables custom sorting beyond natural ordering");
        System.out.println("✅ compare() method returns negative, zero, or positive values");
        System.out.println("✅ Lambda expressions simplify comparator creation");
        System.out.println("✅ Method references provide clean syntax for simple cases");
        System.out.println("✅ Chaining enables multi-criteria sorting");
        System.out.println("✅ Static factory methods cover common scenarios");
        System.out.println("✅ Practical applications drive business value");
        
        System.out.println("\n📚 CONTINUE TO PART 2 for:");
        System.out.println("• Advanced Comparator features");
        System.out.println("• Performance optimization techniques");
        System.out.println("• Complex sorting scenarios");
        System.out.println("• Best practices and patterns");
        System.out.println("• Interview preparation");
    }
}
```

---

*Part 1 Summary: Comparator Interface is like having a team of expert judges - each comparator is a specialist who knows exactly how to rank items according to specific criteria, and you can combine multiple judges for sophisticated ranking systems!*

**🔗 Continue to [Comparator Interface - Part 2](README_PART2.md) for advanced features and techniques.**