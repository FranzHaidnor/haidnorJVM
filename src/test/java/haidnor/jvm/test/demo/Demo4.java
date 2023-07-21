package haidnor.jvm.test.demo;

import haidnor.jvm.test.clazz.Student;

public class Demo4 {

    public static void main(String[] args) {
        Student student = Student.newStudent();
        student.eat();
        student.name = "张三";
        System.out.println(student.name);
    }

}
