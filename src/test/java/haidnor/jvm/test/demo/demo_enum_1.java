package haidnor.jvm.test.demo;

import haidnor.jvm.test.clazz.StudentEnum;

public class demo_enum_1 {
    public static void main(String[] args) {
        StudentEnum studentEnum = StudentEnum.ZHANG_SAN;
        System.out.println(studentEnum.getAge());
        System.out.println(studentEnum.getName());
    }
}
