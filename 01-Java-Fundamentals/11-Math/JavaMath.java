
public class JavaMath {
    public static void main(String[] args) {
        // The Math class in Java provides various methods for performing mathematical operations
        int a = 10;
        int b = 3;

        // Math.max() - returns the maximum of two values
        System.out.println(Math.max(a, b)); // Outputs 10

        // Math.min() - returns the minimum of two values
        System.out.println(Math.min(a, b)); // Outputs 3

        // Math.sqrt() - returns the square root of a value
        System.out.println(Math.sqrt(16)); // Outputs 4.0

        // Math.pow() - returns the value of the first argument raised to the power of the second argument
        System.out.println(Math.pow(a, b)); // Outputs 1000.0 (10^3)

        // Math.abs() - returns the absolute value of a number
        System.out.println(Math.abs(-5)); // Outputs 5

        // Math.round() - rounds a floating-point number to the nearest integer
        System.out.println(Math.round(4.6)); // Outputs 5

        // Math.ceil() - rounds a floating-point number up to the nearest integer
        System.out.println(Math.ceil(4.2)); // Outputs 5.0

        // Math.floor() - rounds a floating-point number down to the nearest integer
        System.out.println(Math.floor(4.8)); // Outputs 4.0

        // Math.random() - returns a random double value between 0.0 and 1.0
        System.out.println(Math.random()); // Outputs a random number between 0.0 and
        
        System.out.println(Math.random() * 100); // Outputs a random number between 0.0 and 100.0. returns double

        // type casting to int
        int randomNum = (int)(Math.random() * 101); // Random number between 0 and 100
        System.out.println("Random number between 0 and 100: " + randomNum);
    }
}
