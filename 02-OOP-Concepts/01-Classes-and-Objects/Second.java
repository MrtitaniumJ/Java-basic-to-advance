class Second extends First {
    public static void main(String[] args) {
        Second myObj = new Second(); // Create object of child class
        System.out.println(myObj.x); // Inherited from First
    }
}
