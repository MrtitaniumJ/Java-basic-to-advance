# Matrix Operations

## Problem Statement

Matrix operations are fundamental in various fields including linear algebra, computer graphics, machine learning, and scientific computing. This program demonstrates the implementation of basic matrix operations from scratch without relying on external libraries.

A **matrix** is a rectangular grid of numbers arranged in rows and columns. Matrix operations include:

1. **Matrix Addition:** Adding corresponding elements of two matrices of the same dimensions
2. **Matrix Multiplication:** Combining two matrices based on linear algebra rules
3. **Matrix Transpose:** Flipping a matrix along its diagonal
4. **Matrix Determinant:** Computing a scalar value that describes matrix properties
5. **Matrix Trace:** Sum of diagonal elements

## Concepts

### Matrix Addition
Two matrices A and B of the same dimensions can be added element-wise:
- Result[i][j] = A[i][j] + B[i][j]
- Only possible for matrices with identical dimensions
- Time Complexity: O(rows × cols)

### Matrix Multiplication
For matrices A (m×n) and B (n×p), resulting matrix C will be (m×p):
- C[i][j] = Σ(A[i][k] × B[k][j]) for all k from 0 to n-1
- Number of columns in A must equal number of rows in B
- Time Complexity: O(m × n × p)

### Matrix Transpose
Transpose of matrix A:
- Transpose[j][i] = A[i][j]
- Rows become columns and columns become rows
- Time Complexity: O(rows × cols)

### Matrix Determinant
For 2×2 matrix: det = (a×d) - (b×c)
For 3×3 matrix: Uses the rule of Sarrus
- Determinant is only defined for square matrices
- Used in solving systems of linear equations
- Non-zero determinant indicates invertible matrix

### Matrix Trace
Sum of all diagonal elements: trace = A[0][0] + A[1][1] + A[2][2] + ...
- Only meaningful for square matrices
- Important in linear algebra and matrix analysis

## Approaches

### Basic Approach (Used in Program)
1. Use 2D arrays to represent matrices
2. Implement each operation with nested loops
3. Create separate methods for each operation
4. Validate matrix dimensions before operations

### Implementation Details

**Matrix Addition:**
```
FOR each row i
  FOR each column j
    result[i][j] = matrix1[i][j] + matrix2[i][j]
```

**Matrix Multiplication:**
```
FOR each row i in matrix1
  FOR each column j in matrix2
    FOR each element k in row/column
      result[i][j] += matrix1[i][k] * matrix2[k][j]
```

**Matrix Transpose:**
```
FOR each row i
  FOR each column j
    result[j][i] = matrix[i][j]
```

## Complexity Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Addition | O(m × n) | O(m × n) |
| Multiplication | O(m × n × p) | O(m × p) |
| Transpose | O(m × n) | O(m × n) |
| Determinant | O(1) for 2×2, O(n³) for n×n | O(1) |
| Trace | O(n) | O(1) |

Where m = rows in first matrix, n = columns in first matrix (or rows in second), p = columns in second matrix

## Sample Input/Output

```
===== MATRIX OPERATIONS DEMO =====

--- MATRIX ADDITION ---
Matrix A:
1 2 3
4 5 6
7 8 9

Matrix B:
9 8 7
6 5 4
3 2 1

A + B:
10 10 10
10 10 10
10 10 10

--- MATRIX TRANSPOSE ---
Transpose of A:
1 4 7
2 5 8
3 6 9

--- MATRIX MULTIPLICATION ---
Matrix D:
1 2
3 4

Matrix E:
5 6
7 8

D * E:
19 22
43 50

--- MATRIX DETERMINANT ---
Determinant of C:
det(C) = -2

--- MATRIX TRACE ---
trace(A) = 15
```

## Key Methods Explained

### `addMatrices(int[][] matrix1, int[][] matrix2)`
- Creates a new result matrix
- Uses double nested loop to add corresponding elements
- Assumes both matrices have same dimensions

### `multiplyMatrices(int[][] matrix1, int[][] matrix2)`
- Creates result matrix with dimensions [rows1][cols2]
- Uses triple nested loop for element calculation
- Validates that cols1 == rows2 implicitly

### `transposeMatrix(int[][] matrix)`
- Swaps row and column indices
- Creates new matrix with swapped dimensions
- Returns new transposed matrix

### `determinant(int[][] matrix)`
- Handles 2×2 and 3×3 matrices specifically
- Uses formula method rather than recursive approach
- Returns 0 for unsupported dimensions

## Variations and Challenges

### Variation 1: Matrix Subtraction
Implement matrix subtraction (similar to addition but with minus operator):
```java
public static int[][] subtractMatrices(int[][] matrix1, int[][] matrix2)
```

### Variation 2: Identity Matrix
Create a method to generate identity matrices (diagonal 1s, rest 0s):
```java
public static int[][] createIdentityMatrix(int size)
```

### Variation 3: Scalar Multiplication
Multiply all matrix elements by a scalar value:
```java
public static int[][] multiplyByScalar(int[][] matrix, int scalar)
```

### Variation 4: Matrix Inverse (Advanced)
Implement 2×2 matrix inversion using adjugate matrix method

### Challenge 1: Strassen's Algorithm
Implement faster matrix multiplication (O(n^2.807)) for large matrices

### Challenge 2: General Determinant
Implement determinant calculation for any size matrix using cofactor expansion

### Challenge 3: Matrix Powers
Calculate matrix raised to power n: A^n = A × A × A ... (n times)

### Challenge 4: Eigenvalues
Find eigenvalues and eigenvectors of a matrix

## Interview Questions

1. **How would you optimize matrix multiplication for very large matrices?**
   - Answer: Use Strassen's algorithm or parallelize with multi-threading

2. **What are the constraints on matrix dimensions for various operations?**
   - Answer: Addition requires same dimensions; multiplication requires cols1 = rows2

3. **How would you handle matrices with floating-point numbers?**
   - Answer: Use double[][] instead of int[][], account for precision errors

4. **Can you multiply incompatible matrices?**
   - Answer: No, you need to validate dimensions first

5. **Why is determinant important in linear algebra?**
   - Answer: It tells if matrix is invertible (det ≠ 0), used in solving equations

## Edge Cases to Consider

- **Single element matrices (1×1):** Should work but be mindful of special cases
- **Non-square matrices:** Multiplication works; transpose changes dimensions
- **Zero matrices:** All operations produce zero or identity properties
- **Identity matrices:** Multiplying by identity returns original matrix
- **Large matrices:** Consider stack overflow with recursive approaches

## Running the Program

```powershell
# Compile
javac MatrixOperations.java

# Run
java MatrixOperations
```

## Tips for Learning

1. **Visualize:** Draw matrices on paper before coding
2. **Test:** Run with known values to verify calculations
3. **Extend:** Add matrix operations one by one
4. **Optimize:** Try to improve from naive approach
5. **Document:** Add comments for complex logic

## Common Mistakes

- Off-by-one errors in nested loops
- Forgetting to validate matrix dimensions
- Incorrect loop bounds in multiplication
- Not initializing result matrix to zero
- Confusing row-major vs. column-major indexing

---

**Practice:** Try implementing these operations multiple times until you can do it from memory. This builds strong foundational knowledge for numerical computing.
