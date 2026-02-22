public class Booleans {
    public static void main(String[] args) {
        // Booleans represent one of two values: true or false
        //boolean values
        boolean isJavaFun = true;
        boolean isFishTasty = false;
        System.out.println(isJavaFun);
        System.out.println(isFishTasty);

        // Boolean expressions
        int a = 10;
        int b = 9;
        System.out.println(a > b);  // returns true because 10 is greater than 9
        System.out.println(a < b);  // returns false because 10 is not less than
        System.out.println(a == b); // returns false because 10 is not equal to 9
        System.out.println(a != b); // returns true because 10 is not equal to
        System.out.println(a >= b); // returns true because 10 is greater than or equal to 9
        System.out.println(a <= b); // returns false because 10 is not less than or

        // real life example
        int myAge = 25;
        int votingAge = 18;
        System.out.println(myAge >= votingAge); // true, old enough to vote

        if (myAge >= votingAge) {
            System.out.println("Old enough to vote!");
        } else {
            System.out.println("Not old enough to vote.");
        }
    }
}
