package haidnor.jvm.test.demo;


public class Demo7 {

    public static void main(String[] args) {
        String fun = fun();
        System.out.println(fun);
    }

    public static String fun() {
        String str = "hello";
        try {
            return str;
        } finally {
            System.out.println("zhangsan");
        }
    }

}
