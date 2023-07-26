package haidnor.jvm.test.demo;

public class demo_helloWorld {

    public static void main(String[] args) {
        System.out.println("hello,world");
    }

}
/*

main(String[] args)
    0 getstatic #7 <java/lang/System.out : Ljava/io/PrintStream;>
    3 ldc #13 <hello,world>
    5 invokevirtual #15 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    8 return

 */