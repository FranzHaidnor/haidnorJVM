package haidnor.jvm.test.clazz;

public class Human {

    public static final String HUMAN_NAME = "123";

    static {
        System.out.println("Human 类被加载了");
    }

    public void eat() {
        Object o = new Object();
        System.out.println("人类吃饭");
    }

}
