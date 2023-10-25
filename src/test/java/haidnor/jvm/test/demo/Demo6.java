package haidnor.jvm.test.demo;


import haidnor.jvm.test.clazz.Human;
import haidnor.jvm.test.clazz.Student;

public class Demo6 {

    public static void main(String[] args) {
        Student student = new Student();
        if (student instanceof Human) {
            System.out.println("true");
        }
    }

}
