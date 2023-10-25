package haidnor.jvm.test.instruction.math;

public class FADD {

    public static void main(String[] args) {
        float c = add(1, 2);
        System.out.println(c);
    }

    public static float add(float a, float b) {
        float c = a + b;
        return c;
    }

}
