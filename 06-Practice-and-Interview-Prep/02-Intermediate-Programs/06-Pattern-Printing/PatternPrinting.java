/**
 * Pattern Printing Program
 * Demonstrates: pyramids, diamonds, and number patterns
 * 
 * Learning Objectives:
 * - Master nested loop structures
 * - Develop logical thinking for pattern generation
 * - Practice string formatting and output
 * - Understand space and alignment concepts
 */

public class PatternPrinting {
    
    // ===== PYRAMID PATTERNS =====
    
    // Simple pyramid pattern with stars
    // Output:
    //     *
    //    **
    //   ***
    //  ****
    // *****
    public static void pyramidStars(int rows) {
        System.out.println("Simple Pyramid:");
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // Pyramid with numbers
    public static void pyramidNumbers(int rows) {
        System.out.println("Number Pyramid:");
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print numbers
            for (int j = 1; j <= i; j++) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
    
    // Right-aligned pyramid
    public static void rightPyramid(int rows) {
        System.out.println("Right-aligned Pyramid:");
        for (int i = 1; i <= rows; i++) {
            // Print stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // Inverted pyramid
    public static void invertedPyramid(int rows) {
        System.out.println("Inverted Pyramid:");
        for (int i = rows; i >= 1; i--) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // ===== DIAMOND PATTERNS =====
    
    // Diamond pattern with stars
    public static void diamondStars(int rows) {
        System.out.println("Diamond Pattern:");
        // Upper half
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 0; j < 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        
        // Lower half
        for (int i = rows - 1; i >= 1; i--) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 0; j < 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // Diamond with numbers
    public static void diamondNumbers(int rows) {
        System.out.println("Diamond with Numbers:");
        // Upper half
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print ascending numbers
            for (int j = 0; j < i; j++) {
                System.out.print(j + 1);
            }
            // Print descending numbers
            for (int j = i - 2; j >= 0; j--) {
                System.out.print(j + 1);
            }
            System.out.println();
        }
        
        // Lower half
        for (int i = rows - 1; i >= 1; i--) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            // Print ascending numbers
            for (int j = 0; j < i; j++) {
                System.out.print(j + 1);
            }
            // Print descending numbers
            for (int j = i - 2; j >= 0; j--) {
                System.out.print(j + 1);
            }
            System.out.println();
        }
    }
    
    // Hollow diamond
    public static void hollowDiamond(int rows) {
        System.out.println("Hollow Diamond:");
        // Upper half
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            System.out.print("*");
            
            // Print middle spaces (only for i > 1)
            if (i > 1) {
                for (int j = 0; j < 2 * (i - 1) - 1; j++) {
                    System.out.print(" ");
                }
                System.out.print("*");
            }
            System.out.println();
        }
        
        // Lower half
        for (int i = rows - 1; i >= 1; i--) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            System.out.print("*");
            
            // Print middle spaces (only for i > 1)
            if (i > 1) {
                for (int j = 0; j < 2 * (i - 1) - 1; j++) {
                    System.out.print(" ");
                }
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // ===== NUMBER PATTERNS =====
    
    // Floyd's triangle
    // Output:
    // 1
    // 2 3
    // 4 5 6
    // 7 8 9 10
    public static void floydsTriangle(int rows) {
        System.out.println("Floyd's Triangle:");
        int num = 1;
        for (int i = 1; i <= rows; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(num++ + " ");
            }
            System.out.println();
        }
    }
    
    // Pascal's triangle
    public static void pascalsTriangle(int rows) {
        System.out.println("Pascal's Triangle:");
        for (int i = 0; i < rows; i++) {
            // Print leading spaces
            for (int j = 0; j < rows - i; j++) {
                System.out.print(" ");
            }
            
            // Calculate and print values
            int value = 1;
            for (int j = 0; j <= i; j++) {
                System.out.print(value + " ");
                value = value * (i - j) / (j + 1);
            }
            System.out.println();
        }
    }
    
    // Multiplication table pattern
    public static void multiplicationTable(int size) {
        System.out.println("Multiplication Table:");
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.printf("%3d ", i * j);
            }
            System.out.println();
        }
    }
    
    // Alternating numbers
    public static void alternatingNumbers(int rows) {
        System.out.println("Alternating Numbers:");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++) {
                if (j % 2 == 1) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
    
    // ===== SPECIAL PATTERNS =====
    
    // Butterfly pattern
    public static void butterfly(int rows) {
        System.out.println("Butterfly Pattern:");
        // Upper half
        for (int i = 1; i <= rows; i++) {
            // Left stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            // Middle spaces
            for (int j = 0; j < 2 * (rows - i); j++) {
                System.out.print(" ");
            }
            // Right stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        
        // Lower half
        for (int i = rows; i >= 1; i--) {
            // Left stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            // Middle spaces
            for (int j = 0; j < 2 * (rows - i); j++) {
                System.out.print(" ");
            }
            // Right stars
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    // Cross pattern
    public static void crossPattern(int size) {
        System.out.println("Cross Pattern:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j || i + j == size - 1) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("===== PATTERN PRINTING DEMO =====\n");
        
        int rows = 5;
        
        pyramidStars(rows);
        System.out.println();
        
        pyramidNumbers(rows);
        System.out.println();
        
        rightPyramid(rows);
        System.out.println();
        
        invertedPyramid(rows);
        System.out.println();
        
        diamondStars(rows);
        System.out.println();
        
        diamondNumbers(rows);
        System.out.println();
        
        hollowDiamond(rows);
        System.out.println();
        
        floydsTriangle(rows);
        System.out.println();
        
        pascalsTriangle(rows);
        System.out.println();
        
        multiplicationTable(5);
        System.out.println();
        
        alternatingNumbers(rows);
        System.out.println();
        
        butterfly(rows);
        System.out.println();
        
        crossPattern(5);
    }
}
