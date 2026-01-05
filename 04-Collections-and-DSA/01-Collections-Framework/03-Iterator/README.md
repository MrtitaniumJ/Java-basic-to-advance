# Iterator in Java

## Simple Explanation

Think of **Iterator** as **the universal remote control for collections**:

- **Iterator** = Remote control that works with any collection (List, Set, Map)
- **hasNext()** = Check if there are more items to go through
- **next()** = Move to the next item and get its value
- **remove()** = Safely delete the current item while browsing
- **Enhanced for loop** = Automatic iterator usage behind the scenes

### Real-World Analogies
- **Iterator** = Reading a book page by page (hasNext = "more pages?", next = "turn page")
- **remove()** = Safely tearing out a page while reading
- **Enhanced for loop** = Speed reading (automatic page turning)
- **Different Iterator types** = Different reading styles (forward only, bidirectional, random access)

## Professional Definition

**Iterator** is a behavioral design pattern implemented as an interface in Java that provides a standardized way to traverse collections sequentially without exposing the underlying data structure implementation. It enables safe element access and modification during iteration while maintaining encapsulation and supporting fail-fast behavior.

## Iterator Pattern Hierarchy

```
Iterator Interfaces
├── Iterator<E> (Basic iteration)
│   ├── hasNext() - Check for more elements
│   ├── next() - Get next element
│   └── remove() - Safe removal
├── ListIterator<E> (Bidirectional for Lists)
│   ├── hasPrevious() - Check for previous elements
│   ├── previous() - Get previous element
│   ├── nextIndex() / previousIndex() - Position info
│   ├── set(E) - Replace current element
│   └── add(E) - Insert before current position
└── Spliterator<E> (Parallel processing - Java 8+)
    ├── tryAdvance() - Process one element
    ├── trySplit() - Split for parallel processing
    └── characteristics() - Behavioral hints

Collection Types & Their Iterators
├── List implementations
│   ├── ArrayList → fast-fail iterator
│   ├── LinkedList → bidirectional iterator
│   └── Vector → enumeration + iterator
├── Set implementations
│   ├── HashSet → unordered iterator
│   ├── LinkedHashSet → insertion-order iterator
│   └── TreeSet → sorted-order iterator
└── Queue implementations
    ├── PriorityQueue → heap-order iterator
    └── ArrayDeque → insertion-order iterator
```

## Core Iterator Usage

```java
// Essential Iterator demonstration

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

class BasicIteratorUsage {
    
    public void demonstrateIteratorBasics() {
        System.out.println("=== BASIC ITERATOR USAGE DEMONSTRATION ===");
        
        demonstrateBasicIteration();
        demonstrateSafeRemoval();
        demonstrateEnhancedForLoop();
        demonstrateIteratorTypes();
        demonstrateFailFastBehavior();
        demonstrateIteratorWithDifferentCollections();
    }
    
    // Basic iteration patterns
    public void demonstrateBasicIteration() {
        System.out.println("\n--- Basic Iteration Patterns ---");
        
        List<String> fruits = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date", "Elderberry"));
        System.out.printf("🍎 Fruits collection: %s%n", fruits);
        
        // Traditional iterator pattern
        System.out.println("\n🔄 Traditional iterator:");
        Iterator<String> iterator = fruits.iterator();
        int index = 0;
        
        while (iterator.hasNext()) {
            String fruit = iterator.next();
            System.out.printf("  [%d] %s%n", index++, fruit);
        }
        
        // Check iterator state after completion
        System.out.printf("  Iterator hasNext() after completion: %s%n", iterator.hasNext());
        
        // Fresh iterator for processing
        System.out.println("\n🔄 Processing with iterator:");
        Iterator<String> processor = fruits.iterator();
        
        while (processor.hasNext()) {
            String fruit = processor.next();
            if (fruit.length() > 6) {
                System.out.printf("  Long name fruit: %s (%d chars)%n", fruit, fruit.length());
            }
        }
        
        // Iterator with manual control
        System.out.println("\n🔄 Manual iterator control:");
        Iterator<String> manual = fruits.iterator();
        
        if (manual.hasNext()) {
            System.out.printf("  First element: %s%n", manual.next());
        }
        
        if (manual.hasNext()) {
            System.out.printf("  Second element: %s%n", manual.next());
        }
        
        System.out.printf("  Remaining elements count: %d%n", countRemaining(manual));
        
        System.out.println("✅ Iterator provides controlled sequential access");
    }
    
    private int countRemaining(Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
    
    // Safe removal during iteration
    public void demonstrateSafeRemoval() {
        System.out.println("\n--- Safe Removal During Iteration ---");
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.printf("🔢 Original numbers: %s%n", numbers);
        
        // Safe removal with iterator
        System.out.println("\n🗑️ Removing even numbers safely:");
        Iterator<Integer> iterator = numbers.iterator();
        
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            if (number % 2 == 0) {
                iterator.remove();
                System.out.printf("  Removed: %d%n", number);
            }
        }
        
        System.out.printf("  After safe removal: %s%n", numbers);
        
        // Demonstrate unsafe removal (will cause exception)
        System.out.println("\n❌ Unsafe removal demonstration:");
        List<String> words = new ArrayList<>(Arrays.asList("short", "medium", "very_long_word", "tiny"));
        System.out.printf("  Original words: %s%n", words);
        
        try {
            for (String word : words) {
                if (word.length() > 6) {
                    words.remove(word); // Unsafe - modifying collection during iteration
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.printf("  ❌ ConcurrentModificationException: %s%n", e.getClass().getSimpleName());
        }
        
        // Safe approach for the above
        System.out.println("\n✅ Correct approach for removing long words:");
        words = new ArrayList<>(Arrays.asList("short", "medium", "very_long_word", "tiny"));
        Iterator<String> wordIterator = words.iterator();
        
        while (wordIterator.hasNext()) {
            String word = wordIterator.next();
            if (word.length() > 6) {
                wordIterator.remove();
                System.out.printf("  Removed long word: %s%n", word);
            }
        }
        
        System.out.printf("  Final words: %s%n", words);
        
        System.out.println("✅ Iterator.remove() is the safe way to modify during iteration");
    }
    
    // Enhanced for loop
    public void demonstrateEnhancedForLoop() {
        System.out.println("\n--- Enhanced For Loop (Iterator Behind the Scenes) ---");
        
        Set<String> languages = new HashSet<>(Arrays.asList("Java", "Python", "C++", "JavaScript", "Kotlin"));
        System.out.printf("💻 Programming languages: %s%n", languages);
        
        // Enhanced for loop
        System.out.println("\n🔄 Enhanced for loop:");
        for (String language : languages) {
            System.out.printf("  Language: %s%n", language);
        }
        
        // Equivalent iterator code
        System.out.println("\n🔄 Equivalent iterator code:");
        Iterator<String> langIterator = languages.iterator();
        while (langIterator.hasNext()) {
            String language = langIterator.next();
            System.out.printf("  Language: %s%n", language);
        }
        
        // Enhanced for loop with index (requires wrapper)
        System.out.println("\n🔄 Enhanced for loop with index:");
        List<String> languageList = new ArrayList<>(languages);
        int index = 0;
        for (String language : languageList) {
            System.out.printf("  [%d] %s%n", index++, language);
        }
        
        // Limitations of enhanced for loop
        System.out.println("\n⚠️ Enhanced for loop limitations:");
        List<String> modifiable = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        
        System.out.println("  Cannot use enhanced for loop for removal:");
        // for (String item : modifiable) {
        //     if (item.equals("B")) {
        //         modifiable.remove(item); // Would cause ConcurrentModificationException
        //     }
        // }
        
        System.out.println("  Must use iterator for safe modification");
        
        System.out.println("✅ Enhanced for loop uses Iterator internally for clean syntax");
    }
    
    // Different iterator types
    public void demonstrateIteratorTypes() {
        System.out.println("\n--- Different Iterator Types ---");
        
        // Regular Iterator
        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.printf("🔢 Numbers list: %s%n", numbers);
        
        System.out.println("\n➡️ Regular Iterator (forward only):");
        Iterator<Integer> forward = numbers.iterator();
        while (forward.hasNext()) {
            System.out.printf("  Next: %d%n", forward.next());
        }
        
        // ListIterator (bidirectional)
        System.out.println("\n↔️ ListIterator (bidirectional):");
        ListIterator<Integer> bidirectional = numbers.listIterator();
        
        // Forward traversal
        System.out.println("  Forward traversal:");
        while (bidirectional.hasNext()) {
            int current = bidirectional.next();
            System.out.printf("    Forward[%d]: %d%n", bidirectional.nextIndex() - 1, current);
        }
        
        // Backward traversal
        System.out.println("  Backward traversal:");
        while (bidirectional.hasPrevious()) {
            int current = bidirectional.previous();
            System.out.printf("    Backward[%d]: %d%n", bidirectional.previousIndex() + 1, current);
        }
        
        // ListIterator modifications
        System.out.println("\n🔧 ListIterator modifications:");
        ListIterator<Integer> modifier = numbers.listIterator();
        
        // Move to middle position
        modifier.next(); // position 0->1
        modifier.next(); // position 1->2
        
        System.out.printf("  Current position: %d%n", modifier.nextIndex());
        
        // Add element
        modifier.add(25); // Insert 25 before position 2
        System.out.printf("  After adding 25: %s%n", numbers);
        
        // Set element
        if (modifier.hasNext()) {
            modifier.next(); // Move to next element (30)
            modifier.set(35); // Replace 30 with 35
        }
        System.out.printf("  After setting to 35: %s%n", numbers);
        
        System.out.println("✅ Different iterator types provide different capabilities");
    }
    
    // Fail-fast behavior
    public void demonstrateFailFastBehavior() {
        System.out.println("\n--- Fail-Fast Behavior ---");
        
        List<String> items = new ArrayList<>(Arrays.asList("Item1", "Item2", "Item3", "Item4"));
        System.out.printf("📦 Items: %s%n", items);
        
        // Fail-fast iterator
        System.out.println("\n⚡ Fail-fast iterator behavior:");
        Iterator<String> failFast = items.iterator();
        
        // Start iteration
        if (failFast.hasNext()) {
            String first = failFast.next();
            System.out.printf("  Retrieved: %s%n", first);
        }
        
        // Modify collection externally
        items.add("Item5");
        System.out.println("  Added 'Item5' to collection externally");
        
        // Try to continue iteration
        try {
            if (failFast.hasNext()) {
                failFast.next(); // Should fail
            }
        } catch (ConcurrentModificationException e) {
            System.out.printf("  ❌ Fail-fast detected external modification: %s%n", e.getClass().getSimpleName());
        }
        
        // Fail-safe iterator (CopyOnWriteArrayList)
        System.out.println("\n🛡️ Fail-safe iterator behavior:");
        List<String> failSafe = new CopyOnWriteArrayList<>(Arrays.asList("Safe1", "Safe2", "Safe3"));
        System.out.printf("  Original fail-safe list: %s%n", failSafe);
        
        Iterator<String> safeIterator = failSafe.iterator();
        
        // Start iteration
        if (safeIterator.hasNext()) {
            String first = safeIterator.next();
            System.out.printf("  Retrieved: %s%n", first);
        }
        
        // Modify collection externally
        failSafe.add("Safe4");
        System.out.println("  Added 'Safe4' to collection externally");
        System.out.printf("  Current list: %s%n", failSafe);
        
        // Continue iteration (should work with snapshot)
        System.out.println("  Iterator sees original snapshot:");
        while (safeIterator.hasNext()) {
            System.out.printf("    Snapshot item: %s%n", safeIterator.next());
        }
        
        System.out.println("✅ Fail-fast provides early error detection, fail-safe provides consistency");
    }
    
    // Iterator with different collection types
    public void demonstrateIteratorWithDifferentCollections() {
        System.out.println("\n--- Iterator with Different Collection Types ---");
        
        // ArrayList iterator
        List<String> arrayList = new ArrayList<>(Arrays.asList("A", "B", "C"));
        System.out.println("\n📋 ArrayList iterator:");
        demonstrateCollectionIterator("ArrayList", arrayList);
        
        // LinkedList iterator
        List<String> linkedList = new LinkedList<>(Arrays.asList("X", "Y", "Z"));
        System.out.println("\n🔗 LinkedList iterator:");
        demonstrateCollectionIterator("LinkedList", linkedList);
        
        // HashSet iterator (unordered)
        Set<String> hashSet = new HashSet<>(Arrays.asList("Red", "Green", "Blue"));
        System.out.println("\n🎲 HashSet iterator (unordered):");
        demonstrateCollectionIterator("HashSet", hashSet);
        
        // TreeSet iterator (sorted)
        Set<String> treeSet = new TreeSet<>(Arrays.asList("Zebra", "Apple", "Mango"));
        System.out.println("\n🌳 TreeSet iterator (sorted):");
        demonstrateCollectionIterator("TreeSet", treeSet);
        
        // LinkedHashSet iterator (insertion order)
        Set<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList("First", "Second", "Third"));
        System.out.println("\n🔗 LinkedHashSet iterator (insertion order):");
        demonstrateCollectionIterator("LinkedHashSet", linkedHashSet);
        
        // PriorityQueue iterator (heap order - not sorted!)
        Queue<Integer> priorityQueue = new PriorityQueue<>(Arrays.asList(30, 10, 40, 20));
        System.out.println("\n📊 PriorityQueue iterator (heap order):");
        System.out.printf("  %s: %s%n", "PriorityQueue", priorityQueue);
        System.out.print("    Iterator order: ");
        Iterator<Integer> pqIterator = priorityQueue.iterator();
        while (pqIterator.hasNext()) {
            System.out.printf("%d ", pqIterator.next());
        }
        System.out.println("\n    Note: Iterator doesn't guarantee priority order!");
        
        System.out.print("    Poll order (correct priority): ");
        Queue<Integer> tempQueue = new PriorityQueue<>(priorityQueue);
        while (!tempQueue.isEmpty()) {
            System.out.printf("%d ", tempQueue.poll());
        }
        System.out.println();
        
        System.out.println("✅ Iterator behavior varies by collection type but interface remains consistent");
    }
    
    private void demonstrateCollectionIterator(String type, Collection<String> collection) {
        System.out.printf("  %s: %s%n", type, collection);
        System.out.print("    Iterator order: ");
        
        for (String item : collection) {
            System.out.printf("%s ", item);
        }
        System.out.println();
    }
}
```

## Advanced Iterator Features

```java
// Advanced Iterator features and patterns

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.concurrent.CopyOnWriteArrayList;

class AdvancedIteratorFeatures {
    
    public void demonstrateAdvancedIterators() {
        System.out.println("=== ADVANCED ITERATOR FEATURES ===");
        
        demonstrateListIteratorAdvanced();
        demonstrateSpliterator();
        demonstrateCustomIterators();
        demonstrateIteratorPerformance();
        demonstrateIteratorPatterns();
        demonstrateParallelIteration();
    }
    
    // Advanced ListIterator features
    public void demonstrateListIteratorAdvanced() {
        System.out.println("\n--- Advanced ListIterator Features ---");
        
        List<String> editableList = new ArrayList<>(Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon"));
        System.out.printf("📝 Original list: %s%n", editableList);
        
        // ListIterator starting from specific position
        System.out.println("\n🎯 ListIterator from specific position:");
        ListIterator<String> positioned = editableList.listIterator(2); // Start at index 2
        
        System.out.printf("  Starting position: %d%n", positioned.nextIndex());
        System.out.printf("  Element at position: %s%n", positioned.next());
        
        // Bidirectional navigation
        System.out.println("\n↔️ Bidirectional navigation:");
        ListIterator<String> navigator = editableList.listIterator();
        
        // Move forward to middle
        navigator.next(); // Alpha
        navigator.next(); // Beta
        navigator.next(); // Gamma
        
        System.out.printf("  Current position (nextIndex): %d%n", navigator.nextIndex());
        System.out.printf("  Current position (previousIndex): %d%n", navigator.previousIndex());
        
        // Move backward
        String previous = navigator.previous(); // Back to Gamma
        System.out.printf("  Moved back to: %s%n", previous);
        
        // Edit operations
        System.out.println("\n✏️ Edit operations:");
        ListIterator<String> editor = editableList.listIterator();
        
        // Replace elements with uppercase versions
        while (editor.hasNext()) {
            String current = editor.next();
            if (current.startsWith("G")) {
                editor.set(current.toUpperCase());
                System.out.printf("  Changed %s to uppercase%n", current);
            }
        }
        
        System.out.printf("  After uppercase edit: %s%n", editableList);
        
        // Insert elements
        System.out.println("\n➕ Insert operations:");
        ListIterator<String> inserter = editableList.listIterator(2); // Position after Beta
        
        inserter.add("INSERTED");
        System.out.printf("  After insertion at position 2: %s%n", editableList);
        
        // Complex editing scenario
        System.out.println("\n🔧 Complex editing scenario:");
        ListIterator<String> complex = editableList.listIterator();
        
        while (complex.hasNext()) {
            String item = complex.next();
            
            // Remove short items
            if (item.length() <= 4) {
                complex.remove();
                System.out.printf("  Removed short item: %s%n", item);
            }
            // Add suffix to remaining items
            else {
                complex.set(item + "_MODIFIED");
                System.out.printf("  Modified item: %s%n", item);
            }
        }
        
        System.out.printf("  Final result: %s%n", editableList);
        
        System.out.println("✅ ListIterator provides full bidirectional editing capabilities");
    }
    
    // Spliterator (Java 8+)
    public void demonstrateSpliterator() {
        System.out.println("\n--- Spliterator for Parallel Processing ---");
        
        List<Integer> largeList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            largeList.add(i);
        }
        
        System.out.printf("📊 Large list size: %,d elements%n", largeList.size());
        
        // Basic spliterator usage
        System.out.println("\n🔄 Basic Spliterator:");
        Spliterator<Integer> spliterator = largeList.spliterator();
        
        System.out.printf("  Estimated size: %d%n", spliterator.estimateSize());
        System.out.printf("  Exact size known: %s%n", spliterator.hasCharacteristics(Spliterator.SIZED));
        System.out.printf("  Ordered: %s%n", spliterator.hasCharacteristics(Spliterator.ORDERED));
        System.out.printf("  Distinct elements: %s%n", spliterator.hasCharacteristics(Spliterator.DISTINCT));
        
        // Process first few elements
        System.out.println("  First 5 elements:");
        spliterator.tryAdvance(x -> System.out.printf("    Element: %d%n", x));
        spliterator.tryAdvance(x -> System.out.printf("    Element: %d%n", x));
        spliterator.tryAdvance(x -> System.out.printf("    Element: %d%n", x));
        spliterator.tryAdvance(x -> System.out.printf("    Element: %d%n", x));
        spliterator.tryAdvance(x -> System.out.printf("    Element: %d%n", x));
        
        // Splitting for parallel processing
        System.out.println("\n🔄 Splitting for parallel processing:");
        Spliterator<Integer> wholeSpliterator = largeList.spliterator();
        Spliterator<Integer> splitPart = wholeSpliterator.trySplit();
        
        if (splitPart != null) {
            System.out.printf("  Original spliterator size: %d%n", wholeSpliterator.estimateSize());
            System.out.printf("  Split part size: %d%n", splitPart.estimateSize());
            
            // Process parts in parallel (simplified)
            System.out.println("  Processing split parts:");
            
            long[] sum1 = {0};
            long[] sum2 = {0};
            
            wholeSpliterator.forEachRemaining(x -> sum1[0] += x);
            splitPart.forEachRemaining(x -> sum2[0] += x);
            
            System.out.printf("    Sum from part 1: %,d%n", sum1[0]);
            System.out.printf("    Sum from part 2: %,d%n", sum2[0]);
            System.out.printf("    Total sum: %,d%n", sum1[0] + sum2[0]);
        }
        
        // Stream integration (uses Spliterator internally)
        System.out.println("\n🌊 Stream integration:");
        long parallelSum = largeList.parallelStream()
                                   .mapToLong(Integer::longValue)
                                   .sum();
        
        System.out.printf("  Parallel stream sum: %,d%n", parallelSum);
        
        // Custom Spliterator characteristics
        System.out.println("\n🔧 Custom Spliterator:");
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        CustomSpliterator<String> customSplit = new CustomSpliterator<>(words);
        
        System.out.println("  Processing with custom spliterator:");
        customSplit.forEachRemaining(word -> System.out.printf("    Word: %s (length: %d)%n", word, word.length()));
        
        System.out.println("✅ Spliterator enables efficient parallel processing");
    }
    
    // Custom iterator implementations
    public void demonstrateCustomIterators() {
        System.out.println("\n--- Custom Iterator Implementations ---");
        
        // Range iterator
        System.out.println("🔢 Range Iterator:");
        RangeIterable range = new RangeIterable(1, 10, 2); // 1, 3, 5, 7, 9
        
        System.out.print("  Range values: ");
        for (Integer value : range) {
            System.out.printf("%d ", value);
        }
        System.out.println();
        
        // Fibonacci iterator
        System.out.println("\n🌀 Fibonacci Iterator:");
        FibonacciIterable fibonacci = new FibonacciIterable(10); // First 10 Fibonacci numbers
        
        System.out.print("  Fibonacci sequence: ");
        for (Long value : fibonacci) {
            System.out.printf("%d ", value);
        }
        System.out.println();
        
        // Filtering iterator
        System.out.println("\n🔍 Filtering Iterator:");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        FilteringIterable<Integer> evenNumbers = new FilteringIterable<>(numbers, x -> x % 2 == 0);
        
        System.out.print("  Even numbers: ");
        for (Integer value : evenNumbers) {
            System.out.printf("%d ", value);
        }
        System.out.println();
        
        // Transforming iterator
        System.out.println("\n🔄 Transforming Iterator:");
        List<String> words = Arrays.asList("hello", "world", "java", "iterator");
        TransformingIterable<String, String> upperWords = 
            new TransformingIterable<>(words, String::toUpperCase);
        
        System.out.print("  Uppercase words: ");
        for (String word : upperWords) {
            System.out.printf("%s ", word);
        }
        System.out.println();
        
        // Chaining iterators
        System.out.println("\n🔗 Chaining Multiple Iterators:");
        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("X", "Y", "Z");
        List<String> list3 = Arrays.asList("1", "2", "3");
        
        ChainedIterable<String> chained = new ChainedIterable<>(Arrays.asList(list1, list2, list3));
        
        System.out.print("  Chained iteration: ");
        for (String item : chained) {
            System.out.printf("%s ", item);
        }
        System.out.println();
        
        System.out.println("✅ Custom iterators enable specialized iteration patterns");
    }
    
    // Iterator performance considerations
    public void demonstrateIteratorPerformance() {
        System.out.println("\n--- Iterator Performance Considerations ---");
        
        int testSize = 100000;
        
        // ArrayList iterator performance
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < testSize; i++) {
            arrayList.add(i);
        }
        
        System.out.printf("🚀 Performance test with %,d elements:%n", testSize);
        
        // Iterator vs index access for ArrayList
        System.out.println("\n📋 ArrayList access patterns:");
        
        long startTime = System.nanoTime();
        long sum1 = 0;
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            sum1 += iterator.next();
        }
        long iteratorTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        long sum2 = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum2 += arrayList.get(i);
        }
        long indexTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        long sum3 = 0;
        for (Integer value : arrayList) {
            sum3 += value;
        }
        long enhancedForTime = System.nanoTime() - startTime;
        
        System.out.printf("  Iterator access: %,d ns (sum: %,d)%n", iteratorTime, sum1);
        System.out.printf("  Index access: %,d ns (sum: %,d)%n", indexTime, sum2);
        System.out.printf("  Enhanced for: %,d ns (sum: %,d)%n", enhancedForTime, sum3);
        
        // LinkedList iterator performance
        List<Integer> linkedList = new LinkedList<>(arrayList);
        
        System.out.println("\n🔗 LinkedList access patterns:");
        
        startTime = System.nanoTime();
        sum1 = 0;
        iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            sum1 += iterator.next();
        }
        iteratorTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        sum2 = 0;
        for (int i = 0; i < Math.min(1000, linkedList.size()); i++) { // Limited due to O(n) access
            sum2 += linkedList.get(i);
        }
        indexTime = System.nanoTime() - startTime;
        
        System.out.printf("  Iterator access: %,d ns (full list)%n", iteratorTime);
        System.out.printf("  Index access: %,d ns (first 1000 only!)%n", indexTime);
        System.out.println("  Note: Index access on LinkedList is O(n) per access!");
        
        // Iterator creation overhead
        System.out.println("\n🔄 Iterator creation overhead:");
        List<String> smallList = Arrays.asList("A", "B", "C", "D", "E");
        
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            Iterator<String> iter = smallList.iterator();
            if (iter.hasNext()) iter.next(); // Use it briefly
        }
        long creationTime = System.nanoTime() - startTime;
        
        System.out.printf("  10,000 iterator creations: %,d ns%n", creationTime);
        System.out.printf("  Average per creation: %.1f ns%n", creationTime / 10000.0);
        
        System.out.println("✅ Iterator performance varies by collection type and usage pattern");
    }
    
    // Iterator design patterns
    public void demonstrateIteratorPatterns() {
        System.out.println("\n--- Iterator Design Patterns ---");
        
        // Null-safe iterator
        System.out.println("🛡️ Null-safe Iterator:");
        List<String> possiblyNull = null;
        
        for (String item : nullSafeIterable(possiblyNull)) {
            System.out.printf("  Item: %s%n", item); // Won't execute
        }
        System.out.println("  Handled null collection gracefully");
        
        possiblyNull = Arrays.asList("A", "B", "C");
        for (String item : nullSafeIterable(possiblyNull)) {
            System.out.printf("  Item: %s%n", item);
        }
        
        // Lazy evaluation iterator
        System.out.println("\n💤 Lazy Evaluation Iterator:");
        LazySequence squares = new LazySequence(5); // Squares of 0, 1, 2, 3, 4
        
        System.out.println("  Lazy squares (computed on demand):");
        int count = 0;
        for (Integer square : squares) {
            System.out.printf("    Square[%d]: %d%n", count++, square);
            if (count >= 3) break; // Only compute first 3
        }
        
        // Buffered iterator
        System.out.println("\n📦 Buffered Iterator:");
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        BufferedIterable<Integer> buffered = new BufferedIterable<>(source, 3); // Buffer size 3
        
        System.out.println("  Buffered iteration (groups of 3):");
        for (List<Integer> buffer : buffered) {
            System.out.printf("    Buffer: %s%n", buffer);
        }
        
        // Peek iterator
        System.out.println("\n👀 Peek Iterator:");
        List<String> items = Arrays.asList("First", "Second", "Third");
        PeekableIterator<String> peekable = new PeekableIterator<>(items.iterator());
        
        System.out.println("  Peek without advancing:");
        while (peekable.hasNext()) {
            String peeked = peekable.peek();
            String actual = peekable.next();
            System.out.printf("    Peeked: %s, Got: %s%n", peeked, actual);
        }
        
        System.out.println("✅ Iterator patterns solve common iteration challenges");
    }
    
    // Parallel iteration
    public void demonstrateParallelIteration() {
        System.out.println("\n--- Parallel Iteration ---");
        
        List<Integer> largeDataset = new ArrayList<>();
        for (int i = 1; i <= 1000000; i++) {
            largeDataset.add(i);
        }
        
        System.out.printf("📊 Dataset size: %,d elements%n", largeDataset.size());
        
        // Sequential processing
        System.out.println("\n🔄 Sequential processing:");
        long startTime = System.nanoTime();
        long sequentialSum = largeDataset.stream()
                                        .mapToLong(Integer::longValue)
                                        .sum();
        long sequentialTime = System.nanoTime() - startTime;
        
        System.out.printf("  Sequential sum: %,d%n", sequentialSum);
        System.out.printf("  Time taken: %,d ns%n", sequentialTime);
        
        // Parallel processing
        System.out.println("\n⚡ Parallel processing:");
        startTime = System.nanoTime();
        long parallelSum = largeDataset.parallelStream()
                                      .mapToLong(Integer::longValue)
                                      .sum();
        long parallelTime = System.nanoTime() - startTime;
        
        System.out.printf("  Parallel sum: %,d%n", parallelSum);
        System.out.printf("  Time taken: %,d ns%n", parallelTime);
        System.out.printf("  Speedup: %.1fx%n", (double) sequentialTime / parallelTime);
        
        // Custom parallel iteration
        System.out.println("\n🔧 Custom parallel iteration:");
        List<String> words = Arrays.asList(
            "parallel", "processing", "iterator", "spliterator", 
            "concurrent", "threads", "performance", "java"
        );
        
        startTime = System.nanoTime();
        Map<Integer, Long> lengthFreq = words.parallelStream()
                                           .collect(groupingBy(
                                               String::length,
                                               counting()
                                           ));
        long parallelGroupTime = System.nanoTime() - startTime;
        
        System.out.printf("  Length frequency (parallel): %s%n", lengthFreq);
        System.out.printf("  Time taken: %,d ns%n", parallelGroupTime);
        
        // Compare with sequential
        startTime = System.nanoTime();
        Map<Integer, Long> lengthFreqSeq = words.stream()
                                              .collect(groupingBy(
                                                  String::length,
                                                  counting()
                                              ));
        long sequentialGroupTime = System.nanoTime() - startTime;
        
        System.out.printf("  Length frequency (sequential): %s%n", lengthFreqSeq);
        System.out.printf("  Time taken: %,d ns%n", sequentialGroupTime);
        
        System.out.println("✅ Parallel iteration can provide significant performance benefits");
    }
    
    // Utility method for null-safe iteration
    private static <T> Iterable<T> nullSafeIterable(Iterable<T> iterable) {
        return iterable != null ? iterable : Collections.emptyList();
    }
    
    // Custom iterator implementations
    static class CustomSpliterator<T> implements Spliterator<T> {
        private final List<T> list;
        private int current = 0;
        
        public CustomSpliterator(List<T> list) {
            this.list = list;
        }
        
        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (current < list.size()) {
                action.accept(list.get(current++));
                return true;
            }
            return false;
        }
        
        @Override
        public Spliterator<T> trySplit() {
            // Simple split in half
            if (list.size() - current > 10) {
                int mid = current + (list.size() - current) / 2;
                CustomSpliterator<T> split = new CustomSpliterator<>(list.subList(current, mid));
                current = mid;
                return split;
            }
            return null;
        }
        
        @Override
        public long estimateSize() {
            return list.size() - current;
        }
        
        @Override
        public int characteristics() {
            return ORDERED | SIZED | SUBSIZED;
        }
    }
    
    static class RangeIterable implements Iterable<Integer> {
        private final int start, end, step;
        
        public RangeIterable(int start, int end, int step) {
            this.start = start;
            this.end = end;
            this.step = step;
        }
        
        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                private int current = start;
                
                @Override
                public boolean hasNext() {
                    return current < end;
                }
                
                @Override
                public Integer next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    int result = current;
                    current += step;
                    return result;
                }
            };
        }
    }
    
    static class FibonacciIterable implements Iterable<Long> {
        private final int count;
        
        public FibonacciIterable(int count) {
            this.count = count;
        }
        
        @Override
        public Iterator<Long> iterator() {
            return new Iterator<Long>() {
                private int current = 0;
                private long a = 0, b = 1;
                
                @Override
                public boolean hasNext() {
                    return current < count;
                }
                
                @Override
                public Long next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    
                    current++;
                    if (current == 1) return a;
                    if (current == 2) return b;
                    
                    long next = a + b;
                    a = b;
                    b = next;
                    return next;
                }
            };
        }
    }
    
    static class FilteringIterable<T> implements Iterable<T> {
        private final Iterable<T> source;
        private final Predicate<T> predicate;
        
        public FilteringIterable(Iterable<T> source, Predicate<T> predicate) {
            this.source = source;
            this.predicate = predicate;
        }
        
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private final Iterator<T> sourceIterator = source.iterator();
                private T nextElement;
                private boolean hasNextElement = false;
                
                @Override
                public boolean hasNext() {
                    if (hasNextElement) {
                        return true;
                    }
                    
                    while (sourceIterator.hasNext()) {
                        T element = sourceIterator.next();
                        if (predicate.test(element)) {
                            nextElement = element;
                            hasNextElement = true;
                            return true;
                        }
                    }
                    
                    return false;
                }
                
                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    
                    hasNextElement = false;
                    return nextElement;
                }
            };
        }
    }
    
    static class TransformingIterable<T, R> implements Iterable<R> {
        private final Iterable<T> source;
        private final Function<T, R> transformer;
        
        public TransformingIterable(Iterable<T> source, Function<T, R> transformer) {
            this.source = source;
            this.transformer = transformer;
        }
        
        @Override
        public Iterator<R> iterator() {
            Iterator<T> sourceIterator = source.iterator();
            
            return new Iterator<R>() {
                @Override
                public boolean hasNext() {
                    return sourceIterator.hasNext();
                }
                
                @Override
                public R next() {
                    return transformer.apply(sourceIterator.next());
                }
            };
        }
    }
    
    static class ChainedIterable<T> implements Iterable<T> {
        private final List<? extends Iterable<T>> iterables;
        
        public ChainedIterable(List<? extends Iterable<T>> iterables) {
            this.iterables = iterables;
        }
        
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int currentIterableIndex = 0;
                private Iterator<T> currentIterator = getNextIterator();
                
                private Iterator<T> getNextIterator() {
                    while (currentIterableIndex < iterables.size()) {
                        Iterator<T> iter = iterables.get(currentIterableIndex++).iterator();
                        if (iter.hasNext()) {
                            return iter;
                        }
                    }
                    return null;
                }
                
                @Override
                public boolean hasNext() {
                    if (currentIterator != null && currentIterator.hasNext()) {
                        return true;
                    }
                    
                    currentIterator = getNextIterator();
                    return currentIterator != null && currentIterator.hasNext();
                }
                
                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return currentIterator.next();
                }
            };
        }
    }
    
    static class LazySequence implements Iterable<Integer> {
        private final int limit;
        
        public LazySequence(int limit) {
            this.limit = limit;
        }
        
        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                private int current = 0;
                
                @Override
                public boolean hasNext() {
                    return current < limit;
                }
                
                @Override
                public Integer next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    int result = current * current; // Square
                    current++;
                    System.out.printf("      Computed square: %d%n", result);
                    return result;
                }
            };
        }
    }
    
    static class BufferedIterable<T> implements Iterable<List<T>> {
        private final Iterable<T> source;
        private final int bufferSize;
        
        public BufferedIterable(Iterable<T> source, int bufferSize) {
            this.source = source;
            this.bufferSize = bufferSize;
        }
        
        @Override
        public Iterator<List<T>> iterator() {
            Iterator<T> sourceIterator = source.iterator();
            
            return new Iterator<List<T>>() {
                @Override
                public boolean hasNext() {
                    return sourceIterator.hasNext();
                }
                
                @Override
                public List<T> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    
                    List<T> buffer = new ArrayList<>();
                    for (int i = 0; i < bufferSize && sourceIterator.hasNext(); i++) {
                        buffer.add(sourceIterator.next());
                    }
                    
                    return buffer;
                }
            };
        }
    }
    
    static class PeekableIterator<T> implements Iterator<T> {
        private final Iterator<T> iterator;
        private T peekedElement;
        private boolean hasPeeked = false;
        
        public PeekableIterator(Iterator<T> iterator) {
            this.iterator = iterator;
        }
        
        public T peek() {
            if (!hasPeeked) {
                if (!iterator.hasNext()) {
                    throw new NoSuchElementException();
                }
                peekedElement = iterator.next();
                hasPeeked = true;
            }
            return peekedElement;
        }
        
        @Override
        public boolean hasNext() {
            return hasPeeked || iterator.hasNext();
        }
        
        @Override
        public T next() {
            if (hasPeeked) {
                hasPeeked = false;
                return peekedElement;
            }
            return iterator.next();
        }
    }
}
```

## Complete Iterator Demo

```java
public class CompleteIteratorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE ITERATOR DEMONSTRATION ===");
        
        // 1. Basic Iterator usage
        System.out.println("\n1. BASIC ITERATOR USAGE");
        BasicIteratorUsage basic = new BasicIteratorUsage();
        basic.demonstrateIteratorBasics();
        
        System.out.println("\n" + "=".repeat(60));
        
        // 2. Advanced Iterator features
        System.out.println("\n2. ADVANCED ITERATOR FEATURES");
        AdvancedIteratorFeatures advanced = new AdvancedIteratorFeatures();
        advanced.demonstrateAdvancedIterators();
        
        System.out.println("\n=== DEMONSTRATION COMPLETED ===");
        printIteratorBestPractices();
    }
    
    private static void printIteratorBestPractices() {
        System.out.println("\n🎯 ITERATOR BEST PRACTICES:");
        System.out.println("✅ Use Iterator.remove() for safe removal during iteration");
        System.out.println("✅ Prefer enhanced for loop for simple read-only iteration");
        System.out.println("✅ Use ListIterator for bidirectional navigation and editing");
        System.out.println("✅ Be aware of fail-fast behavior in most collections");
        System.out.println("✅ Consider custom iterators for specialized iteration patterns");
        System.out.println("✅ Use Spliterator for parallel processing scenarios");
        System.out.println("✅ Choose appropriate iterator type based on collection characteristics");
        System.out.println("✅ Handle ConcurrentModificationException appropriately");
        
        System.out.println("\n❌ AVOID:");
        System.out.println("❌ Modifying collection directly during iteration");
        System.out.println("❌ Calling next() without checking hasNext()");
        System.out.println("❌ Using index access when iterator is more appropriate");
        System.out.println("❌ Ignoring fail-fast exceptions without understanding the cause");
        System.out.println("❌ Creating unnecessary iterators for one-time access");
        System.out.println("❌ Assuming iteration order unless guaranteed by collection type");
    }
}
```

## Interview Questions & Answers

**Q1: What is the Iterator pattern and why is it useful in Java?**
**A:** Iterator pattern provides a standard way to traverse collections without exposing internal structure. Benefits: encapsulation, unified interface, safe modification during iteration, and support for different traversal strategies.

**Q2: What's the difference between Iterator and ListIterator?**
**A:** Iterator provides forward-only traversal with hasNext(), next(), and remove(). ListIterator extends Iterator for Lists with bidirectional navigation (hasPrevious(), previous()), positional access (nextIndex(), previousIndex()), and modification methods (set(), add()).

**Q3: Explain fail-fast vs fail-safe iterators with examples.**
**A:** Fail-fast (ArrayList, HashSet) throws ConcurrentModificationException if collection is modified during iteration. Fail-safe (CopyOnWriteArrayList) works with a snapshot, allowing concurrent modification but may not see latest changes.

**Q4: When would you use enhanced for loop vs traditional iterator?**
**A:** Use enhanced for loop for simple read-only iteration (cleaner syntax). Use traditional iterator when you need to remove elements, require index information, or need more control over iteration flow.

**Q5: How does the enhanced for loop work internally?**
**A:** Enhanced for loop is compiled to use Iterator internally. The compiler converts `for(Type item : collection)` to `Iterator<Type> iter = collection.iterator(); while(iter.hasNext()) { Type item = iter.next(); ... }`.

**Q6: What is Spliterator and when would you use it?**
**A:** Spliterator is for parallel processing, introduced in Java 8. It can split itself for concurrent processing and provides characteristics (ORDERED, DISTINCT, etc.). Use for parallel stream operations or custom parallel algorithms.

**Q7: How do you safely remove elements during iteration?**
**A:** Use Iterator.remove() method, which is the only safe way to modify a collection during iteration. Never use collection's remove() method directly during iteration as it causes ConcurrentModificationException.

**Q8: What happens if you call iterator.next() when hasNext() returns false?**
**A:** Throws NoSuchElementException. Always check hasNext() before calling next() to avoid this exception. This is a common programming error.

## Key Takeaways

1. **Iterator provides uniform traversal interface** across all collection types
2. **Enhanced for loop uses Iterator internally** for cleaner syntax
3. **Iterator.remove() enables safe modification** during iteration
4. **Fail-fast behavior detects concurrent modifications** for data integrity
5. **ListIterator offers bidirectional navigation** for List collections
6. **Spliterator enables parallel processing** for performance optimization
7. **Custom iterators support specialized patterns** like lazy evaluation
8. **Different collections provide different iteration characteristics** (order, performance)
9. **Iterator pattern maintains encapsulation** while enabling traversal
10. **Performance varies by collection type** - choose appropriately

---

*Remember: Iterator is like a guided tour through any collection - it knows the way, keeps you safe, and works the same way whether you're touring a museum (List), art gallery (Set), or library (Map)!*