
public class Patterns {

    public static void HollowReverseTrianglePattern(int n) {

        for (int i = n; i >= 1; i--) {
            for (int j = i; j < n; j++) {
                System.out.print("  "); // Print spaces for alignment
            }
            for (int k = 1; k <= (2 * i - 1); k++) {
                if (k == 1 || i == n || k == (2 * i - 1)) {
                    System.out.print("* ");
                } else {
                    System.out.print("  "); // Print spaces for hollow effect
                }
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    public static void hollowTrianglePattern(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n - i; j++) {
                System.out.print("  "); // Print spaces for alignment
            }
            for (int k = 1; k <= (2 * i - 1); k++) {
                if (k == 1 || i == n || k == (2 * i - 1)) {
                    System.out.print("* ");
                } else {
                    System.out.print("  "); // Print spaces for hollow effect
                }
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    public static void leftHalfPyramid(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n - i; j++) {
                System.out.print("  ");
                 // Print spaces for the left half
            }
            for (int k = 1; k <= i; k++) {
                System.out.print("* ");
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    public static void reverseLeftHalfPyramid(int n) {
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= n - i; j++) {
                System.out.print("  "); // Print spaces for alignment
            }
            for (int k = 1; k <= i; k++) {
                System.out.print("* ");
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    

    public static void main(String[] args) {
        HollowReverseTrianglePattern(5);
        System.out.println();
        hollowTrianglePattern(5);
        System.out.println();
        leftHalfPyramid(5);
        System.out.println();
        reverseLeftHalfPyramid(5);
    }
}
