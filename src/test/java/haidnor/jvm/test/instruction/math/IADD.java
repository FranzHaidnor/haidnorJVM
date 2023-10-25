package haidnor.jvm.test.instruction.math;

public class IADD {

    public static void main(String[] args) {
        int c = add(1, 2);
        System.out.println(c);
    }

    public static int add(int a, int b) {
        int c = a + b;
        return c;
    }

}
