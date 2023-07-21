package haidnor.jvm.test.clazz;

public class Student extends Human {

    public static String school = "家里蹲大学";

    public String name;

    public void printSchool() {
        System.out.println(school);
    }

    public static Student newStudent() {
        return new Student();
    }

}
