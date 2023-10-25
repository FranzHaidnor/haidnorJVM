package haidnor.jvm.test.model;

public class Demo {

    static {
        System.out.println("类被加载了");
    }

    public static void main(String[] args) {
        System.out.println("helloWorld");
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int div(int a, int b) {
        int c = 0;
        try {
            c = a / b;
        } catch (Exception exception) {
            throw new RuntimeException();
        } finally {
            System.out.println("调用 div()");
        }
        return c;
    }

}
