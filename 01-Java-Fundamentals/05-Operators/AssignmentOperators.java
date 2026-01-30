public class AssignmentOperators {
    public static void main(String[] args) {
        // Assignment Operators are used to assign values to variables
        int x = 10; // Simple assignment operator (=)
        System.out.println("Initial value of x: " + x);

        x += 5; // x = x + 5;  (Addition assignment operator)
        System.out.println("Value of x after += 5: " + x);
        x -= 3; // x = x - 3;  (Subtraction assignment operator)
        System.out.println("Value of x after -= 3: " + x);
        x *= 3; // x = x * 3;  (Multiplication assignment operator)
        System.out.println("Value of x after *= 3: " + x);
        x /= 4; // x = x / 4;  (Division assignment operator)
        System.out.println("Value of x after /= 4: " + x);
        x %= 3; // x = x % 3;  (Modulus assignment operator)
        System.out.println("Value of x after %= 3: " + x);
        x &= 3; // x = x & 3;  (Bitwise AND assignment operator)
        System.out.println("Value of x after &= 3: " + x);
        x |= 2; // x = x | 2;  (Bitwise OR assignment operator)
        System.out.println("Value of x after |= 2: " + x);
        x ^= 2; // x = x ^ 2;  (Bitwise XOR assignment operator)
        System.out.println("Value of x after ^= 2: " + x);
        x <<= 1; // x = x << 1;  (Left shift assignment operator)
        System.out.println("Value of x after <<= 1: " + x);
        x >>= 1; // x = x >> 1;  (Right shift assignment operator)
        System.out.println("Value of x after >>= 1: " + x);

        System.out.println("Final value of x: " + x);
    }
}
