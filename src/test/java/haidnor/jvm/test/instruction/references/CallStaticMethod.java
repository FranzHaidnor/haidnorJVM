package haidnor.jvm.test.instruction.references;

public class CallStaticMethod {

    public static void main(String[] args) {
        float i = staticMethod();
        System.out.println(i);
    }

    public static float staticMethod() {
        return 1321.321f;
    }

}
