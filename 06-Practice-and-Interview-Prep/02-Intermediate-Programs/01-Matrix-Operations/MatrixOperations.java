/**
 * Matrix Operations Program
 * Demonstrates: matrix addition, multiplication, and transpose operations
 * 
 * Learning Objectives:
 * - Work with 2D arrays (matrices)
 * - Implement matrix arithmetic operations
 * - Understand nested loop patterns
 * - Apply mathematical operations programmatically
 */

public class MatrixOperations {
    
    // Add two matrices
    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }
    
    // Multiply two matrices
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        int[][] result = new int[rows1][cols2];
        
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
    
    // Transpose a matrix (flip along diagonal)
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }
    
    // Print a matrix
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    // Calculate matrix determinant (2x2 and 3x3 only)
    public static int determinant(int[][] matrix) {
        if (matrix.length == 2 && matrix[0].length == 2) {
            // For 2x2 matrix: ad - bc
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        } else if (matrix.length == 3 && matrix[0].length == 3) {
            // For 3x3 matrix using rule of Sarrus
            int det = (matrix[0][0] * matrix[1][1] * matrix[2][2]) +
                      (matrix[0][1] * matrix[1][2] * matrix[2][0]) +
                      (matrix[0][2] * matrix[1][0] * matrix[2][1]) -
                      (matrix[0][2] * matrix[1][1] * matrix[2][0]) -
                      (matrix[0][0] * matrix[1][2] * matrix[2][1]) -
                      (matrix[0][1] * matrix[1][0] * matrix[2][2]);
            return det;
        }
        return 0;
    }
    
    // Find trace of a matrix (sum of diagonal elements)
    public static int traceMatrix(int[][] matrix) {
        int trace = 0;
        int minDimension = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < minDimension; i++) {
            trace += matrix[i][i];
        }
        return trace;
    }
    
    public static void main(String[] args) {
        System.out.println("===== MATRIX OPERATIONS DEMO =====\n");
        
        // Create sample matrices
        int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] matrixB = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };
        
        int[][] matrixC = {
            {1, 2},
            {3, 4}
        };
        
        // Matrix Addition
        System.out.println("--- MATRIX ADDITION ---");
        System.out.println("Matrix A:");
        printMatrix(matrixA);
        System.out.println("\nMatrix B:");
        printMatrix(matrixB);
        
        int[][] addResult = addMatrices(matrixA, matrixB);
        System.out.println("\nA + B:");
        printMatrix(addResult);
        
        // Matrix Transpose
        System.out.println("\n--- MATRIX TRANSPOSE ---");
        int[][] transposeResult = transposeMatrix(matrixA);
        System.out.println("Transpose of A:");
        printMatrix(transposeResult);
        
        // Matrix Multiplication (2x2 for simplicity)
        System.out.println("\n--- MATRIX MULTIPLICATION ---");
        int[][] matrixD = {
            {1, 2},
            {3, 4}
        };
        
        int[][] matrixE = {
            {5, 6},
            {7, 8}
        };
        
        System.out.println("Matrix D:");
        printMatrix(matrixD);
        System.out.println("\nMatrix E:");
        printMatrix(matrixE);
        
        int[][] multiplyResult = multiplyMatrices(matrixD, matrixE);
        System.out.println("\nD * E:");
        printMatrix(multiplyResult);
        
        // Matrix Determinant
        System.out.println("\n--- MATRIX DETERMINANT ---");
        System.out.println("Determinant of C:");
        printMatrix(matrixC);
        System.out.println("det(C) = " + determinant(matrixC));
        
        // Matrix Trace
        System.out.println("\n--- MATRIX TRACE ---");
        System.out.println("Trace of A (sum of diagonal elements):");
        System.out.println("trace(A) = " + traceMatrix(matrixA));
    }
}
