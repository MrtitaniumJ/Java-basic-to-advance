public class Recursion {
    // Recursion is a programming technique where a function calls itself in order to solve a problem. It typically involves a base case that stops the recursion and a recursive case that breaks the problem into smaller subproblems.
    // A recursive function must have a base case that stops the recursion, otherwise it will continue indefinitely and eventually cause a stack overflow error. The base case is the simplest instance of the problem that can be solved directly without further recursion.
    // The recursive case is where the function calls itself with a modified argument that brings it closer to the base case. The recursive case should eventually lead to the base case, ensuring that the recursion will terminate.
    // For example, the factorial of a number n (denoted as n!) is the product of all positive integers less than or equal to n. The factorial can be defined recursively as follows:
    // factorial(0) = 1 (base case)
    // factorial(n) = n * factorial(n - 1) for n > 0 (recursive case)
    static int factorial(int n) {
        if (n == 0) { // Base case: factorial of 0 is 1
            return 1;
        } else { // Recursive case: factorial of n is n times factorial of (n - 1)
            return n * factorial(n - 1);
        }
    }

    // In this example, the factorial function takes an integer n as input and returns the factorial of n. If n is 0, it returns 1 (the base case). For any positive integer n, it returns n multiplied by the result of factorial(n - 1), which is the recursive case. This process continues until it reaches the base case, at which point the recursion will stop and the final result will be returned.

    // Use recursion to add all numbers from 1 to 10.

    public static int sum(int n) {
        if (n > 0) {
            return n + sum(n - 1); // Recursive case: sum of n is n plus sum of (n - 1)
        } else {
            return 0; // Base case: sum of 0 is 0
        }
    }

    // Halting condition: Just as a loop needs a halting condition to prevent it from running indefinitely, a recursive function also needs a halting condition (base case) to stop the recursion. The base case is the simplest instance of the problem that can be solved directly without further recursion. It ensures that the recursion will terminate and prevents infinite calls to the function.
    // In the sum function above, the base case is when n is 0 or less, at which point it returns 0. This halting condition ensures that the recursion will stop when it reaches 0, preventing infinite calls to the sum function.

    // Use recursion to add all numbers from 5 to 10.
    public static int sumInRange(int start, int end) {
        if (start > end) {
            return 0; // Base case: if start is greater than end, return 0
        } else {
            return start + sumInRange(start + 1, end); // Recursive case: sum of range is start plus sum of range starting from (start + 1)
        }
    }

    // WAP to calculate the nth Fibonacci number using recursion.
    public static int fibonacci(int n) {
        if (n <= 1) { // Base case: Fibonacci of 0 is 0 and Fibonacci of 1 is 1
            return n;
        } else { // Recursive case: Fibonacci of n is the sum of Fibonacci of (n - 1) and Fibonacci of (n - 2)
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    // WAP to create a countdown from a given number to 0 using recursion. 
    public static void countdown1(int n) {
        if (n == 0) { // Base case: if n is 0, print "Countdown complete!"
            System.out.println("Countdown complete!");
        } else { // Recursive case: print n and call countdown with (n - 1)
            System.out.println(n);
            countdown1(n - 1);
        }
    }

    public static void countdown2(int n) {
        if (n > 0) { // Recursive case: if n is greater than 0, print n and call countdown with (n - 1)
            System.out.println(n);
            countdown2(n - 1);
        } else { // Base case: if n is 0 or less, print "Countdown complete!"
            System.out.println("Countdown complete!");
        }
    }

    // WAP to calculate the factorial of a number using recursion.
    public static int factorial1(int n) {
        if (n <= 1) { // Base case: factorial of 0 is 1 and factorial of 1 is 1
            return 1;
        } else { // Recursive case: factorial of n is n times factorial of (n - 1)
            return n * factorial1(n - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(factorial(5)); // Output: 120
        System.out.println(factorial(0)); // Output: 1

        System.out.println(sum(10)); // Output: 55
        // Example explained: The sum function takes an integer n as input and returns the sum of all numbers from 1 to n. If n is greater than 0, it returns n + sum(n - 1), which is the recursive case. If n is 0 or less, it returns 0, which is the base case. This process continues until it reaches the base case, at which point the recursion will stop and the final result will be returned. In this example, sum(10) will return 55, which is the sum of all numbers from 1 to 10.
        // 10 + sum(9)
        // 10 + 9 + sum(8)
        // 10 + 9 + 8 + sum(7)
        // 10 + 9 + 8 + 7 + sum(6)
        // 10 + 9 + 8 + 7 + 6 + sum(5)
        // 10 + 9 + 8 + 7 + 6 + 5 + sum(4)
        // 10 + 9 + 8 + 7 + 6 + 5 + 4 + sum(3)  
        // 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + sum(2)
        // 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + 2 + sum(1)
        // 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + 2 + 1 + sum(0)
        // 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + 2 + 1 + 0
        // 55

        System.out.println(sumInRange(5, 10)); // Output: 45

        // Example explained: The sumInRange function takes two integers, start and end, as input and returns the sum of all numbers from start to end. If start is greater than end, it returns 0, which is the base case. If start is less than or equal to end, it returns start + sumInRange(start + 1, end), which is the recursive case. This process continues until it reaches the base case, at which point the recursion will stop and the final result will be returned. In this example, sumInRange(5, 10) will return 45, which is the sum of all numbers from 5 to 10.
        // 5 + sumInRange(6, 10)
        // 5 + 6 + sumInRange(7, 10)
        // 5 + 6 + 7 + sumInRange(8, 10)
        // 5 + 6 + 7 + 8 + sumInRange(9, 10)
        // 5 + 6 + 7 + 8 + 9 + sumInRange(10, 10)
        // 5 + 6 + 7 + 8 + 9 + 10 + sumInRange(11, 10)
        // 5 + 6 + 7 + 8 + 9 + 10 + 0
        // 45

        System.out.println(fibonacci(10)); // Output: 55

        // Example explained: The fibonacci function takes an integer n as input and returns the nth Fibonacci number. If n is 0 or 1, it returns n, which are the base cases. For any n greater than 1, it returns the sum of fibonacci(n - 1) and fibonacci(n - 2), which is the recursive case. This process continues until it reaches the base cases, at which point the recursion will stop and the final result will be returned. In this example, fibonacci(10) will return 55, which is the 10th Fibonacci number.
        // fibonacci(10) = fibonacci(9) + fibonacci(8) = 34 + 21 = 55
        // fibonacci(9) = fibonacci(8) + fibonacci(7) = 21 + 13 = 34
        // fibonacci(8) = fibonacci(7) + fibonacci(6) = 13 + 8 = 21
        // fibonacci(7) = fibonacci(6) + fibonacci(5) = 8 + 5 = 13
        // fibonacci(6) = fibonacci(5) + fibonacci(4) = 5 + 3 = 8
        // fibonacci(5) = fibonacci(4) + fibonacci(3) = 3 + 2 = 5
        // fibonacci(4) = fibonacci(3) + fibonacci(2) = 2 + 1 = 3
        // fibonacci(3) = fibonacci(2) + fibonacci(1) = 1 + 1 = 2
        // fibonacci(2) = fibonacci(1) + fibonacci(0) = 1 + 0 = 1
        // fibonacci(1) = 1 (base case)
        // fibonacci(0) = 0 (base case)

        countdown1(5);
        countdown2(5);

        // Example explained: The countdown1 function takes an integer n as input and prints a countdown from n to 0. If n is 0, it prints "Countdown complete!" and stops the recursion, which is the base case. If n is greater than 0, it prints n and calls countdown1 with (n - 1), which is the recursive case. This process continues until it reaches the base case, at which point the recursion will stop and the final message will be printed. In this example, countdown1(5) will print:
        // 5
        // 4
        // 3
        // 2
        // 1
        // Countdown complete!
        // The countdown2 function works similarly, but it checks the base case after the recursive call. If n is greater than 0, it prints n and calls countdown2 with (n - 1). If n is 0 or less, it prints "Countdown complete!" and stops the recursion. In this example, countdown2(5) will print the same output as countdown1(5):
        // 5
        // 4
        // 3
        // 2
        // 1
        // Countdown complete!

        System.out.println(factorial1(5)); // Output: 120
        System.out.println(factorial1(0)); // Output: 1
        // Example explained: The factorial1 function takes an integer n as input and returns the factorial of n. If n is 0 or 1, it returns 1, which are the base cases. For any n greater than 1, it returns n multiplied by factorial1(n - 1), which is the recursive case. This process continues until it reaches the base cases, at which point the recursion will stop and the final result will be returned. In this example, factorial1(5) will return 120, which is the factorial of 5.
        // factorial1(5) = 5 * factorial1(4) = 5 * 24 = 120
        // factorial1(4) = 4 * factorial1(3) = 4 * 6 = 24
        // factorial1(3) = 3 * factorial1(2) = 3 * 2 = 6
        // factorial1(2) = 2 * factorial1(1) = 2 * 1 = 2
        // factorial1(1) = 1 (base case)
    }
}
