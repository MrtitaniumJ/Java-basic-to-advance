public class Variables {
    public static void main(String[] args) {
        String name = "John";
        int myNum = 15;
        //declare a variable without assigning a value and assign it later
        int yourNum;
        yourNum = 20;
        // change the value of myNum from 15 to 25
        myNum = 25;

        // Final variable (constant): its value cannot be changed once assigned
        final double PI = 3.14159;

        float myFloatNum = 5.99f;
        char myLetter = 'D';
        boolean myBool = true;

        // To combine both text and a variable, use the + operator
        String myName = "John";


        System.out.println(name);
        System.out.println(myNum);
        System.out.println(yourNum);
        System.out.println(PI);
        System.out.println(myFloatNum);
        System.out.println(myLetter);
        System.out.println(myBool);
        System.out.println("Hello " + myName);
        // use the + operator to add a variable to another variable
        System.out.println(myName + " " + name);

        // + operator joins two strings together (called concatenation)
        // + operator adds two numbers
        int x = 5;
        int y = 6;
        System.err.println("The sum of x and y is: " + (x + y));

        //declare more than one variable of the same type in a single line
        int a = 5, b = 10, c = 15;
        System.out.println("a: " + a + ", b: " + b + ", c: " + c);

        // asign the same value to multiple variables in a single line
        int p, q, r;
        p = q = r = 50;
        System.out.println("p: " + p + ", q: " + q + ", r: " + r);

        // declare variables as final when their values should never change. by conevention, final variables are written in uppercase letters
        final int DAYS_IN_WEEK = 7;
        System.out.println("Days in a week: " + DAYS_IN_WEEK);
        final int MINUTES_IN_HOUR = 60;
        System.out.println("Minutes in an hour: " + MINUTES_IN_HOUR);
        final int BIRTH_YEAR = 2002;
        System.out.println("Birth year: " + BIRTH_YEAR);

        // real life example: using variables to store user information
        String studentName = "John Doe";
        int studentID = 15;
        int studentAge = 23;
        float studentFee = 75.25f;
        char studentGrade = 'B';
        boolean isEnrolled = true;

        System.out.println("Student Name: " + studentName);
        System.out.println("Student ID: " + studentID);
        System.out.println("Student Age: " + studentAge);
        System.out.println("Student Fee: " + studentFee);
        System.out.println("Student Grade: " + studentGrade);
        System.out.println("Is Enrolled: " + isEnrolled);

        // calculate the area of a rectanngle
        int length = 4;
        int width = 6;
        int area;

        // formula
        area = length * width;

        //print variables
        System.out.println("Length is: " + length);
        System.out.println("Width is: " + width);
        System.out.println("Area of the rectangle is: " + area);
    }
}
