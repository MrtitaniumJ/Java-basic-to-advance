public class HollowReverseTrianglePattern {
    public static void main(String[] args) {
        int n = 5;

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
}
