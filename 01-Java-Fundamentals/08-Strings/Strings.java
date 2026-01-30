
public class Strings {

    public static void main(String[] args) {
        // Strings in Java are objects that represent sequences of characters. It contains methods that can perform certain operations on Strings.
        String greeting = "Hello";

        // String length
        String txt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println("The length of the txt string is: " + txt.length());

        // String toUpperCase() and toLowerCase()
        String text = "Hello World";
        System.out.println(text.toUpperCase());
        System.out.println(text.toLowerCase());

        // Finding a character in a string
        String str = "Please locate where 'locate' occurs!";
        System.out.println(str.indexOf("locate")); // Outputs 7
        System.out.println(str.indexOf("hello"));  // Outputs -1 (not found)
        System.out.println(str.lastIndexOf("locate")); // Outputs 21 - counts from the end of the string in manner of character positions

        // accessing characters in a string
        String sample = "Hello";
        System.out.println(sample.charAt(0)); // Outputs 'H'
        System.out.println(sample.charAt(4)); // Outputs 'o'

        // compare two strings
        String txt1 = "Hello";
        String txt2 = "Hello";

        String txt3 = "Greetings";
        String txt4 = "Great things";

        System.out.println(txt1.equals(txt2));
        System.out.println(txt3.equals(txt4));

        // remove whitespace from both ends of a string
        String strWithSpaces = "   Hello World!   ";
        System.out.println("Before: [" + strWithSpaces + "]");
        System.out.println("After: [" + strWithSpaces.trim() + "]");
    }
}
