package haidnor.jvm.test.demo;

import java.util.HashMap;

public class Demo3 {

    public static void main(String[] args) {
        Student sut1 = new Student(1);
        Student sut2 = new Student(2);

        if (sut1 == sut2) {

        }

        HashMap<Student, String> hashMap = new HashMap<>();
        hashMap.put(sut1, "张三");
        hashMap.put(sut2, "张三");

        System.out.println(hashMap.get(sut1)); // 1
        System.out.println(hashMap.get(sut2)); // 2
    }

}

class Student {

    public int age;

    public Student(int age) {
        this.age = age;
    }

//    @Override
//    public boolean equals(Object o) {
//        Student student = (Student) o;
//        System.out.println("调用了 equals 方法, 将 age=" + age + " 与 age=" + student.age + " 的对象进行比较");
//        return age == student.age;
//    }

    @Override
    public int hashCode() {
        System.out.println("调用了 hashCode 方法");
        return 123;  // 这个 hashCode() 算法很糟糕, 100% hash冲突
    }

}
