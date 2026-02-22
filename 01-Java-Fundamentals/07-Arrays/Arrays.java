public class Arrays {
    public static void main(String[] args) {
        // Arrays are used to store multiple values in a single variable, instead of declaring separate variables for each value.
        // To declare an array, define the variable type with square brackets.
        
        String[] cars;

        // Then assign the values to the array.
        cars = new String[] {"Volvo", "BMW", "Ford", "Mazda"};

        // You can also assign the values to the array in a single line.
        String[] cars1 = {"Volvo", "BMW", "Ford", "Mazda"};

        // You can also access the values of the array using the index.
        System.out.println(cars1[0]);

        // You can also change the values of the array using the index.
        cars1[0] = "Opel";

        // You can also get the length of the array using the length property.
        System.out.println(cars1.length);

        // you can also create an array by specifying the size of the array and then assigning the values to the array.
        String[] cars2 = new String[4];
        cars2[0] = "Volvo";
        cars2[1] = "BMW";
        cars2[2] = "Ford";
        cars2[3] = "Mazda";

        System.out.println(cars2[0]);

        // You can also use a for loop to access the values of the array.
        for (int i = 0; i < cars2.length; i++) {
            System.out.println(cars2[i]);
        }

        // Calculate the sum of an array of integers
        int[] numbers = {1, 2, 3, 4, 5};
        int sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        System.out.println("Sum of numbers: " + sum);

        // Loop through an array using for-each loop
        for (String car: cars2) {
            System.out.println(car);
        }

        // Calculate the average of an array of integers
        int[] ages = {20, 22, 18, 35, 48, 26, 87, 70};

        float avg, sum1 = 0;

        int length = ages.length;
        for (int age: ages) {
            sum1 += age;
        }

        avg = sum1 / length;
        System.out.println("Average age: " + avg);

        // WAP to find the lowest ages among different ages in an array
        int ages1[] = {20, 22, 18, 35, 48, 26, 87, 70};
        int min = ages1[0]; // assume the first age is the minimum
        for (int age: ages1) {
            if (age < min) {
                min = age; // update min if a smaller age is found
            }
        }
        System.out.println("Lowest age: " + min);

        // WAP with a list of numbers where you want to skip negative numbers but stop completely if you find a zero.
        int[] numbers1 = {5, -3, 8, 0, -1, 4};

        for (int number: numbers1) {
            if (number < 0) {
                continue; // skip negative numbers
            }
            if (number == 0) {
                break; // stop completely if zero is found
            }
            System.out.println(number); // print the number if it's positive
        }
    }
}
