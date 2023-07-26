package haidnor.jvm.test.demo;

public class demo_for {
    public static void main(String[] args) {
        int a = 0;
        for (int i = 0; i < 3; i++) {
            a++;
        }
        System.out.println(a);
    }
}

/*
main(String[] args):
     0 iconst_0      < int a = 0
     1 istore_1
     2 iconst_0      < int i =0
     3 istore_2

     4 iload_2
     5 iconst_3                < 比较值3
     6 if_icmpge 18 (+12)      < (i < 3)
     9 iinc 1 by 1             < a++
    12 iinc 2 by 1             < i++
    15 goto 4 (-11)            < 向前跳转代码

    18 getstatic #7 <java/lang/System.out : Ljava/io/PrintStream;>
    21 iload_1
    22 invokevirtual #13 <java/io/PrintStream.println : (I)V>
    25 return
 */