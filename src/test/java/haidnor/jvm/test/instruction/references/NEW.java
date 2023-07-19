package haidnor.jvm.test.instruction.references;

import java.io.IOException;

public class NEW {

    public static void main(String[] args) throws IOException {
        add(1, 2, 3, 4, 5, 6, 7);
    }

    public static void add(int a, int b, int c, int d, int e, int f, int g) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(f);
        System.out.println(g);
    }

}
