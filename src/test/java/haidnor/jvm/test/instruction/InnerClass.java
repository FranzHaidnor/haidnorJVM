package haidnor.jvm.test.instruction;

public class InnerClass {
    public static void main(String[] args) {
        ClassA.ClassB classB = new ClassA.ClassB(999);

        System.out.println(classB.a);
    }
}
