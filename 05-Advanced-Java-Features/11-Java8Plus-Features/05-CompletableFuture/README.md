# CompletableFuture: Asynchronous Programming in Java 8+

## Table of Contents
1. [Introduction to Asynchronous Programming](#introduction)
2. [CompletableFuture Basics](#basics)
3. [Core Methods](#core-methods)
4. [Exception Handling](#exception-handling)
5. [Parallel Streams](#parallel-streams)
6. [Performance Implications](#performance)
7. [Best Practices](#best-practices)
8. [Complete Examples](#examples)

## Introduction to Asynchronous Programming

Asynchronous programming allows your application to perform long-running tasks without blocking the main thread. Before Java 8, managing asynchronous operations was cumbersome using callbacks and Future interfaces. CompletableFuture revolutionized asynchronous programming by providing a more elegant, composable, and readable approach to handling asynchronous computations.

### Why Asynchronous Programming?

1. **Responsiveness**: Keep UI and main threads responsive
2. **Scalability**: Handle more concurrent operations with fewer threads
3. **Resource Efficiency**: Better CPU and memory utilization
4. **Non-blocking I/O**: Perform network/database operations without blocking

The traditional Future interface has limitations:
- No way to manually complete a future
- Cannot chain multiple futures
- No built-in exception handling
- Blocking calls required to get results

CompletableFuture addresses all these limitations, introduced in Java 8.

## CompletableFuture Basics

CompletableFuture implements both Future and CompletionStage interfaces, providing a versatile solution for asynchronous programming. It can be manually completed and allows composition of multiple asynchronous operations.

### Creating CompletableFutures

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureBasics {
    
    // 1. Create already-completed future
    public static void createCompletedFuture() {
        CompletableFuture<String> future = CompletableFuture.completedFuture("Result");
        System.out.println(future.join()); // Output: Result
    }
    
    // 2. Create using constructor
    public static void createWithConstructor() {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        // Complete the future manually
        future.complete("Manual completion");
        
        System.out.println(future.join()); // Output: Manual completion
    }
    
    // 3. Create using supplyAsync (with return value)
    public static void createWithSupplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simulate long operation
                return "Task completed";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        System.out.println(future.join());
    }
    
    // 4. Create using runAsync (no return value)
    public static void createWithRunAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Running asynchronous task");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        future.join();
        System.out.println("Task finished");
    }
    
    // 5. Create with custom ExecutorService
    public static void createWithExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 42;
        }, executor);
        
        System.out.println("Result: " + future.join());
        executor.shutdown();
    }
}
```

## Core Methods

### 1. thenApply - Transform Results

Transforms the result of a previous stage. Used when you need to process and return a value.

```java
public class ThenApplyExample {
    
    public static void basicThenApply() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5)
            .thenApply(x -> x * 2)        // 5 * 2 = 10
            .thenApply(x -> x + 10);       // 10 + 10 = 20
        
        System.out.println(future.join()); // Output: 20
    }
    
    public static void chainedOperations() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Java")
            .thenApply(String::toUpperCase)  // "JAVA"
            .thenApply(s -> s.length())      // 4 - type mismatch
            .thenApply(len -> "Length: " + len); // "Length: 4"
        
        System.out.println(future.join());
    }
    
    public static void practicalExample() {
        // Simulate fetching user data
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user from database...");
            return "User: John Doe, Email: john@example.com";
        })
        .thenApply(userData -> {
            System.out.println("Processing user data...");
            return userData.split(",")[0]; // Extract name
        })
        .thenApply(name -> {
            System.out.println("Formatting name...");
            return name.toUpperCase();
        });
        
        System.out.println("Result: " + future.join());
    }
}
```

### 2. thenAccept - Consume Results

Consumes the result without returning a value. Used for side effects like logging or saving.

```java
public class ThenAcceptExample {
    
    public static void basicThenAccept() {
        CompletableFuture.supplyAsync(() -> 100)
            .thenAccept(value -> System.out.println("Value: " + value))
            .join();
    }
    
    public static void multipleAccepts() {
        CompletableFuture.supplyAsync(() -> "Processing...")
            .thenAccept(msg -> System.out.println("1. " + msg))
            .thenAccept(v -> System.out.println("2. Task complete"))
            .join();
    }
    
    public static void databaseLogging() {
        CompletableFuture.supplyAsync(() -> {
            // Simulate API call
            return new UserData("123", "Alice", "alice@example.com");
        })
        .thenAccept(user -> {
            // Log user action
            System.out.println("User logged in: " + user.getName());
            // Could save to database here
        })
        .thenAccept(v -> {
            System.out.println("Logging completed");
        })
        .join();
    }
    
    static class UserData {
        String id, name, email;
        
        UserData(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        String getName() { return name; }
    }
}
```

### 3. thenCombine - Combine Multiple Futures

Combines results from multiple independent futures.

```java
public class ThenCombineExample {
    
    public static void combineSimple() {
        CompletableFuture<Integer> future1 = 
            CompletableFuture.supplyAsync(() -> {
                System.out.println("Task 1 started");
                return 10;
            });
        
        CompletableFuture<Integer> future2 = 
            CompletableFuture.supplyAsync(() -> {
                System.out.println("Task 2 started");
                return 20;
            });
        
        CompletableFuture<Integer> combined = future1.thenCombine(future2, 
            (result1, result2) -> result1 + result2);
        
        System.out.println("Combined result: " + combined.join()); // 30
    }
    
    public static void combineMultiple() {
        CompletableFuture<String> nameFuture = 
            CompletableFuture.supplyAsync(() -> "John");
        
        CompletableFuture<Integer> ageFuture = 
            CompletableFuture.supplyAsync(() -> 30);
        
        CompletableFuture<String> result = nameFuture.thenCombine(ageFuture,
            (name, age) -> name + " is " + age + " years old");
        
        System.out.println(result.join());
    }
    
    public static void combineManyFutures() {
        CompletableFuture<String> future1 = 
            CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = 
            CompletableFuture.supplyAsync(() -> " ");
        CompletableFuture<String> future3 = 
            CompletableFuture.supplyAsync(() -> "World");
        
        // Combine first two
        CompletableFuture<String> combined12 = future1.thenCombine(future2,
            (s1, s2) -> s1 + s2);
        
        // Combine result with third
        CompletableFuture<String> final_result = combined12.thenCombine(future3,
            (combined, s3) -> combined + s3);
        
        System.out.println(final_result.join()); // Hello World
    }
    
    public static void parallelFetching() {
        // Fetch user and permissions in parallel
        CompletableFuture<String> userFuture = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                return "User: Alice";
            });
        
        CompletableFuture<String> permissionsFuture = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(800); } catch (InterruptedException e) {}
                return "Permissions: READ, WRITE";
            });
        
        CompletableFuture<String> combined = 
            userFuture.thenCombine(permissionsFuture,
            (user, permissions) -> user + " | " + permissions);
        
        System.out.println(combined.join());
    }
}
```

### 4. Additional Composition Methods

```java
public class OtherCompositionMethods {
    
    // thenCompose - flatMap for futures
    public static void thenComposeExample() {
        CompletableFuture<Integer> result = 
            CompletableFuture.supplyAsync(() -> 5)
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> x * 2));
        
        System.out.println(result.join()); // 10
    }
    
    // applyToEither - race condition
    public static void applyToEitherExample() {
        CompletableFuture<String> future1 = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                return "Result from future1";
            });
        
        CompletableFuture<String> future2 = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                return "Result from future2";
            });
        
        // Whichever completes first wins
        CompletableFuture<String> result = 
            future1.applyToEither(future2, x -> x);
        
        System.out.println(result.join()); // Result from future2
    }
    
    // acceptEither - consume result from either
    public static void acceptEitherExample() {
        CompletableFuture<Integer> future1 = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
                return 100;
            });
        
        CompletableFuture<Integer> future2 = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                return 200;
            });
        
        future1.acceptEither(future2, 
            value -> System.out.println("Fastest result: " + value));
    }
    
    // allOf - wait for all futures
    public static void allOfExample() {
        CompletableFuture<String> future1 = 
            CompletableFuture.supplyAsync(() -> "Task 1");
        CompletableFuture<String> future2 = 
            CompletableFuture.supplyAsync(() -> "Task 2");
        CompletableFuture<String> future3 = 
            CompletableFuture.supplyAsync(() -> "Task 3");
        
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
            future1, future2, future3);
        
        allFutures.join();
        System.out.println("All tasks completed");
    }
    
    // anyOf - wait for any future
    public static void anyOfExample() {
        CompletableFuture<Integer> future1 = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                return 1;
            });
        
        CompletableFuture<Integer> future2 = 
            CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                return 2;
            });
        
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(
            future1, future2);
        
        System.out.println("First result: " + anyFuture.join());
    }
}
```

## Exception Handling

```java
public class ExceptionHandlingExample {
    
    // exceptionally - handle exceptions
    public static void exceptionallyExample() {
        CompletableFuture<Integer> future = 
            CompletableFuture.supplyAsync(() -> {
                if (true) throw new RuntimeException("Something went wrong");
                return 42;
            })
            .exceptionally(ex -> {
                System.out.println("Error: " + ex.getMessage());
                return 0; // Default value
            });
        
        System.out.println("Result: " + future.join());
    }
    
    // handle - process both success and failure
    public static void handleExample() {
        CompletableFuture<String> future = 
            CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException("API failed");
            })
            .handle((result, exception) -> {
                if (exception != null) {
                    System.out.println("Error occurred: " + exception.getMessage());
                    return "Failed";
                }
                return result;
            });
        
        System.out.println(future.join());
    }
    
    // whenComplete - callback on completion
    public static void whenCompleteExample() {
        CompletableFuture.supplyAsync(() -> 100)
            .thenApply(x -> x / 0) // Will throw ArithmeticException
            .whenComplete((result, exception) -> {
                if (exception != null) {
                    System.out.println("Task failed: " + exception.getClass().getSimpleName());
                } else {
                    System.out.println("Task succeeded: " + result);
                }
            })
            .exceptionally(ex -> {
                System.out.println("Final exception handler");
                return null;
            });
    }
    
    // Robust error handling chain
    public static void robustErrorHandling() {
        CompletableFuture<String> future = 
            CompletableFuture.supplyAsync(() -> {
                System.out.println("Fetching data from API...");
                throw new RuntimeException("Connection timeout");
            })
            .exceptionally(ex -> {
                System.out.println("Exception caught: " + ex.getMessage());
                System.out.println("Retrying with fallback...");
                return "Default data";
            })
            .thenApply(data -> {
                System.out.println("Processing: " + data);
                return data.toUpperCase();
            })
            .whenComplete((result, exception) -> {
                if (exception == null) {
                    System.out.println("Final result: " + result);
                }
            });
        
        future.join();
    }
}
```

## Parallel Streams

CompletableFuture works seamlessly with parallel streams for bulk operations:

```java
import java.util.*;
import java.util.stream.Collectors;

public class ParallelStreamsExample {
    
    // Process items in parallel with CompletableFuture
    public static void processListInParallel() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Map each number to a CompletableFuture
        List<CompletableFuture<Integer>> futures = numbers.stream()
            .map(num -> CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                return num * num;
            }))
            .collect(Collectors.toList());
        
        // Wait for all and collect results
        List<Integer> results = futures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
        
        System.out.println("Squared numbers: " + results);
    }
    
    // Using parallel stream directly
    public static void parallelStreamProcessing() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        List<String> upperNames = names.parallelStream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        System.out.println("Uppercase names: " + upperNames);
    }
    
    // Combining parallel streams with CompletableFuture
    public static void advancedParallelProcessing() {
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5);
        
        CompletableFuture<List<String>> future = 
            CompletableFuture.supplyAsync(() -> {
                return ids.parallelStream()
                    .map(id -> {
                        try { Thread.sleep(200); } catch (InterruptedException e) {}
                        return "User-" + id;
                    })
                    .collect(Collectors.toList());
            });
        
        System.out.println("Parallel result: " + future.join());
    }
}
```

## Performance Implications

### Key Considerations:

1. **Thread Pool Management**
   - Default CommonPool may not be suitable for all workloads
   - Use custom ExecutorService for better control

2. **Context Switching Overhead**
   - Too many threads = excessive context switching
   - Too few threads = reduced parallelism

3. **I/O vs CPU-bound Tasks**
   - I/O operations: benefit from more threads
   - CPU-bound: threads equal to processor count

4. **Memory Implications**
   - Each CompletableFuture holds references
   - Large chains can consume memory

```java
public class PerformanceConsiderations {
    
    // Custom thread pool for better control
    public static void customThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        );
        
        CompletableFuture<Integer> future = 
            CompletableFuture.supplyAsync(() -> {
                System.out.println("Running on custom thread pool");
                return 42;
            }, executor);
        
        System.out.println(future.join());
        executor.shutdown();
    }
    
    // Performance comparison
    public static void performanceComparison() {
        long startTime = System.currentTimeMillis();
        
        // Sequential
        for (int i = 0; i < 1000; i++) {
            try { Thread.sleep(1); } catch (InterruptedException e) {}
        }
        
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        
        // Parallel with CompletableFuture
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            futures.add(CompletableFuture.runAsync(() -> {
                try { Thread.sleep(1); } catch (InterruptedException e) {}
            }));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Sequential: " + sequentialTime + "ms");
        System.out.println("Parallel: " + parallelTime + "ms");
    }
}
```

## Best Practices

### 1. Always Handle Exceptions
```java
future.exceptionally(ex -> {
    logger.error("Failed to process", ex);
    return defaultValue;
});
```

### 2. Use Appropriate Executor
```java
ExecutorService executor = Executors.newFixedThreadPool(
    Math.min(4, Runtime.getRuntime().availableProcessors())
);
```

### 3. Avoid Blocking Operations
- Don't use `future.get()` in chains
- Use `join()` only at final consumption points
- Prefer composition methods

### 4. Clean Up Resources
```java
try {
    result = future.join();
} finally {
    executor.shutdown();
}
```

### 5. Timeout Management
```java
future.completeOnTimeout(defaultValue, 5, TimeUnit.SECONDS);
```

### 6. Avoid Creating Too Many Futures
- Consider batching operations
- Use stream processing for bulk operations

## Summary

CompletableFuture is a powerful tool for building responsive, scalable applications. By understanding its composition methods, exception handling, and performance characteristics, you can write elegant asynchronous code that maintains readability while delivering high performance.

Key takeaways:
- Use `thenApply` for transformations
- Use `thenAccept` for side effects
- Use `thenCombine` for independent operations
- Always handle exceptions
- Choose appropriate thread pools
- Avoid unnecessary blocking
