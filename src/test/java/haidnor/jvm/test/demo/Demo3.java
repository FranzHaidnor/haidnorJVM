package haidnor.jvm.test.demo;

import haidnor.jvm.test.clazz.Student;

import java.util.HashMap;

public class Demo3 {

    public static void main(String[] args) {
        Student sut1 = new Student();
        Student sut2 = new Student();

        HashMap<Student, String> hashMap = new HashMap<>();
        hashMap.put(sut1, "张三123");
        hashMap.put(sut2, "张三");

        System.out.println(hashMap.get(sut1)); // 1
        System.out.println(hashMap.get(sut2)); // 2
    }

}

