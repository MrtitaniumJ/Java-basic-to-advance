public class WhileLoop {
    public static void main(String[] args) {
        // while loop syntax
        // while (condition) {
          // code to be executed
        //}

        // Example: Print numbers from 1 to 5
        int i = 1; // initialization
        while (i <= 5) { // condition
            System.out.println(i); // code to be executed
            i++; //increment
        }

        // Example: Countdown example
        int countdown = 3;

        while (countdown > 0) {
            System.out.println(countdown);
            countdown--;
        }
        System.out.println("Happy New Year!");

        // Example: Loop until a random number is less than 0.5
        double randomNumber = Math.random(); // generate a random number
        while (randomNumber >= 0.5) {
            System.out.println("Generated: " + randomNumber);
            randomNumber = Math.random(); // generate a new random number
        }
        System.out.println("Random number less than 0.5: " + randomNumber);

        // example: while loop with false condition
        int x = 10;
        while (x < 5) { // this condition is false, so the loop will not execute
            System.out.println("This will not be printed.");
            x++;
        }

        // Example: Infinite loop (uncomment to run, but be careful!)
        // while (true) {
        //     System.out.println("This will run forever!");
        // }

        
    }
}
