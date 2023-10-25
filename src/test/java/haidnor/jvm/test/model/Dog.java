package haidnor.jvm.test.model;

public abstract class Dog implements Animal {
    static {
        System.out.println("抽象狗类被加载了");
    }
}
