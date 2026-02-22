
public class MultiDimensionalArray {

    public static void main(String[] args) {
        // A multi-dimensional array is an array of arrays. It is also known as a 2D array or a matrix. It is used to store data in a table with rows and columns.
        // To declare a multi-dimensional array, define the variable type with square brackets for each dimension.
        int[][] myNumbers = {{1, 2, 3, 4}, {5, 6, 7}};

        // Here, myNumbers is a 2D array with 2 rows and 4 columns. The first row contains the values 1, 2, 3, and 4, while the second row contains the values 5, 6, and 7.
        // You can also access the values of the multi-dimensional array using the index. The first index represents the row, and the second index represents the column.
        System.out.println(myNumbers[0][0]); // Output: 1
        System.out.println(myNumbers[0][1]); // Output: 2
        System.out.println(myNumbers[1][0]); // Output: 5

        // You can also change the values of the multi-dimensional array using the index.
        myNumbers[0][0] = 10;
        System.out.println(myNumbers[0][0]); // Output: 10

        // You can use length to get the number of rows and columns in the multi-dimensional array.
        System.out.println("Number of rows: " + myNumbers.length); // Output: 2
        System.out.println("Number of columns in first row: " + myNumbers[0].length); // Output: 4 
        System.out.println("Number of columns in second row: " + myNumbers[1].length); // Output: 3

        // You can also use nested loops to access the values of the multi-dimensional array.
        for (int i = 0; i < myNumbers.length; i++) { // Loop through each row
            for (int j = 0; j < myNumbers[i].length; j++) { // Loop through each column in the current row
                System.out.print("myNumbers[" + i + "][" + j + "] = " + myNumbers[i][j] + " ");
            }
            System.out.println();
        }

        // Use a for-each loop to access the values of the multi-dimensional array.
        for (int[] row: myNumbers) {
            for (int num: row) {
                System.out.println(num);
            }
        }
    }
}
