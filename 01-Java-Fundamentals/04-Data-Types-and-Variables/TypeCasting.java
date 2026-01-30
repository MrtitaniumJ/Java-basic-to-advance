public class TypeCasting {
    public static void main(String[] args) {
        // Widening Casting (automatically) - converting a smaller type to a larger type
        // byte -> short -> char -> int -> long -> float -> double
        int myInt = 9;
        double myDouble = myInt;

        System.out.println("Widening Casting:");
        System.out.println("Integer value: " + myInt);      // Outputs 9
        System.out.println("Double value: " + myDouble);    // Outputs 9.0

        // Narrowing Casting (manually) - converting a larger type to a smaller type
        // double -> float -> long -> int -> char -> short -> byte
        double myDouble2 = 9.78d;
        int myInt2 = (int) myDouble2; // Manual casting: double to int

        System.out.println("Narrowing Casting:");
        System.out.println("Double value: " + myDouble2);    // Outputs 9.78
        System.out.println("Integer value: " + myInt2);      // Outputs 9

        // Real life example
        //Set the maximum possible score in the game to 500
        int maxScore = 500;

        // The actual score of the user
        int userScore = 423;

        /* Calculate the percentage score of the user's score in relation to the maximum available score.
        Convert userScore to double to make sure that the division is accurate */
        double percentage = (double) userScore / maxScore * 100.0d;

        System.out.println("User Score Percentage: " + percentage + "%"); // Outputs 84.6%
    }
}
