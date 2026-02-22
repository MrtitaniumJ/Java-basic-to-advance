import java.util.Scanner;

public class LoopExercises {
    public static void main(String[] args) {
        // 1. Write a program to print all natural numbers from 1 to n and sum of them
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number n: ");
        int n = sc.nextInt();
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " ");
            sum += i;
        }
        System.out.println("\nSum of natural numbers from 1 to " + n + " is: " + sum);

        // 2. Write a program to print all natural numbers in reverse
        for (int i = n; i >= 1; i--) {
            System.out.print(i + " ");
        }

        // 3. Write a program to print tables
        System.out.print("\nEnter a number to print its table: ");
        int num = sc.nextInt();
        for (int i = 1; i <=10; i++) {
            System.out.println(num + " x " + i + " = " + (num * i));
        }

        // 4. Write a program to print reverse tables
        System.out.print("\nEnter a number to print its reverse table: ");
        int num2 = sc.nextInt();
        for (int i = 10; i >= 1; i--) {
            System.out.println(num2 + " x " + i + " = " + (num2 * i));
        }

        // 5. write a program to print all alphabets from a to z
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.print(c + " ");
        }

        // 6. Write a program to print all alphabets from Z to A
        for (char c = 'Z'; c >= 'A'; c--) {
            System.out.print(c + " ");
        }

        // 7. Write a program to print all even numbers from 1 to n and count them
        System.out.print("\nEnter a number n to print even numbers up to n: ");
        int n2 = sc.nextInt();
        int count = 0;
        int sum1 = 0;
        for (int i = 1; i <= n2; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
                count++;
                sum1 += i;
            }
        }
        System.out.println("\nCount of even numbers: " + count);
        System.out.println("Sum of even numbers: " + sum1);

        // 8. Write a program to print all odd numbers from 1 to n and count them
        System.out.print("\nEnter a number n to print odd numbers up to n: ");
        int n3 = sc.nextInt();
        int count2 = 0;
        int sum2 = 0;
        for (int i = 1; i <= n3; i++) {
            if (i % 2 != 0) {
                System.out.print(i + " ");
                count2++;
                sum2 += i;
            }
        }
        System.out.println("\nCount of odd numbers: " + count2);
        System.out.println("Sum of odd numbers: " + sum2);

        // 9. WAP to print the ASCII values
        System.out.println("\nASCII values of characters from A to Z:");
        for (int i = 1; i <= 255; i++) {
            System.out.println(i + " : " + (char)i);
        }

        // 10. WAP to find the factorial value of any number
        System.out.print("\nEnter a number to find its factorial: ");
        int fact = sc.nextInt();
        int factorial = 1;
        for (int i = 1; i <= fact; i++) {
            factorial *= i;
        }
        System.out.println("Factorial of " + fact + " is: " + factorial);

        // 11. WAP to find the value of one number raised to the power of another
        System.out.print("\nEnter the base number: ");
        int base = sc.nextInt();
        System.out.print("Enter the exponent: ");
        int exponent = sc.nextInt();
        int result = 1;
        for (int i = 1; i <= exponent; i++) {
            result *= base;
        }
        System.out.println("Result of " + base + " raised to the power of " + exponent + " is: " + result);

        // 12. WAP to reverse the given digits
        System.out.print("\nEnter a number to reverse its digits: ");
        int digits = sc.nextInt();
        int reversed = 0;
        while (digits != 0) {
            int digit = digits % 10;
            reversed = reversed * 10 + digit;
            digits /= 10;
        }
        System.out.println("Reversed number: " + reversed);

        // throught for loop
        for (int i = digits; i > 0; i /= 10) {
            int digit = i % 10;
            reversed = reversed * 10 + digit;
        }

        // sum of digits
        while (digits != 0) {
            int digit = digits % 10;
            sum += digit;
            digits /= 10;

        }
        System.out.println("Sum of digits: " + sum);

        // 13. WAP to print the prime numbers between 1 and n
        System.out.print("\nEnter a number n to print prime numbers up to n: ");
        int prime1 = sc.nextInt();
        System.out.println("Prime numbers between 1 and " + prime1 + ":");
        for (int i = 2; i <= prime1; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.print(i + " ");
            }
        }

        // 14. WAP to check if a number is prime or not
        System.out.print("\nEnter a number to check if it's prime: ");
        int prime = sc.nextInt();
        boolean isPrime = true;
        if (prime <= 1) {
            isPrime = false;
        } else {
            for (int i = 2; i <= Math.sqrt(prime); i++) {
                if (prime % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.println(prime + " is a prime number.");
            } else {
                System.out.println(prime + " is not a prime number.");
            }
        }
    }
}
