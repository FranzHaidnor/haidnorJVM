package haidnor.jvm.test.instruction.math;

public class IINC {

    public static void main(String[] args) {
        int a = -127;
        a++;
        System.out.println(a);

        int b = 129;
        b++;
        System.out.println(b);

        int c = 321;
        c++;
        System.out.println(c);

        int d = 0;
        d++;
        System.out.println(d);
    }

}
