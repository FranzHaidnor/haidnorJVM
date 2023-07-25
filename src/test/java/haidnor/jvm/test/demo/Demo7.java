package haidnor.jvm.test.demo;


public class Demo7 {

    public static void main(String[] args) {
        try {
            String name = fun();
            System.out.println(name);
        } catch (Exception exception) {
            System.out.println("计算错误");
        }
    }

    public static String fun() {
        System.out.println(1 / 0);
        return "zhangsan";
    }

}
