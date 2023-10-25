package haidnor.jvm.test.instruction;

public class DO_WHILE {
    public static void main(String[] args) {
        int a = 3;
        do {
            System.out.println(a);
            a--;
        } while (a != 0);
    }
}
