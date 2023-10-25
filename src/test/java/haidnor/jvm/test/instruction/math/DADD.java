package haidnor.jvm.test.instruction.math;

public class DADD {

    public static void main(String[] args) {
        double c = add(1D, 2D);
        System.out.println(c);
    }

    public static double add(double a, double b) {
        double c = a + b;
        return c;
    }

}
