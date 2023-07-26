package haidnor.jvm.test.demo;

public class demo_doWhile {
    public static void main(String[] args) {
        int a = 0;
        do {
            a++;
        } while (a < 2);
        System.out.println(a);
    }
}

/*
main(String[] args):
     0 iconst_0
     1 istore_1

     2 iinc 1 by 1
     5 iload_1
     6 iconst_2
     7 if_icmplt 2 (-5)      < while (a < 2); 符合条件则向前跳转

    10 getstatic #7 <java/lang/System.out : Ljava/io/PrintStream;>
    13 iload_1
    14 invokevirtual #13 <java/io/PrintStream.println : (I)V>
    17 return
 */