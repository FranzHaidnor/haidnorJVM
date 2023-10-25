package haidnor.jvm.test.model;

public class DogTest {

    public static void main(String[] args) {
        ThinDog dog1 = new ThinDog();
        if (dog1 instanceof Dog) {
            System.out.println("细狗是一种狗");
        }
    }

    static class InnerClass {

    }
}
