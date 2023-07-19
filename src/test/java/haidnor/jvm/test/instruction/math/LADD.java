package haidnor.jvm.test.instruction.math;

public class LADD {

    public static void main(String[] args) {
        long c = add(1, 2);
        System.out.println(c);
    }

    public static long add(long a, long b) {
        long c = a + b;
        return c;
    }

}
