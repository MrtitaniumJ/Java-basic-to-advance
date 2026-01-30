public class Concatenation {
    public static void main(String[] args) {
        // String Concatenation is the process of joining two or more strings together.
        // The + operator is used for concatenation in Java.

        String firstName = "John";
        String lastName = "Doe";
        System.out.println(firstName + " " + lastName);

        //concatenation in sentences
        String name = "John";
        int age = 25;
        System.out.println("My name is " + name + " and I am " + age + " years old.");

        // concat() method
        String str1 = "Hello, ";
        String str2 = "world!";
        System.out.println(str1.concat(str2));

        String a = "Java ";
        String b = "is ";
        String c = "fun.";
        String result = a.concat(b).concat(c);
        System.out.println(result);

        // concatenation with numbers
        String text = "The result is: ";
        int num1 = 10;
        int num2 = 20;
        System.out.println(text + (num1 + num2)); // Outputs "The result is: 30"
        System.out.println(text + num1 + num2);   // Outputs "The result is: 1020"

        // special characters
        String line1 = "First Line\n";
        String line2 = "Second Line\tTabbed";
        System.out.println(line1 + line2);

        // \' and \" - single and double quotes
        // \\ - backslash
        // \n - new line
        // \t - tab
        // \r - carriage return
        // \b - backspace
        // \f - form feed

        // example
        String specialChars = "He said, \"Hello!\"\nThis is a backslash: \\";
        System.out.println(specialChars);
    }
}
