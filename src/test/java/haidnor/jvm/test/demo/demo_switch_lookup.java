package haidnor.jvm.test.demo;

public class demo_switch_lookup {
    public static void main(String[] args) {
    //LOOKUPSWITCH的demo
        int i = 1;
        switch (i) {
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;
            case 10:
                System.out.println("10");
                break;
            default:
                System.out.println("default");
                break;
        }

    }
}
