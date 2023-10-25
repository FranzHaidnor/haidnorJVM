package haidnor.jvm.test.demo;

public class demo_while {
    public static void main(String[] args) {
        int a = 0;
        while (a < 2) {
            a++;
        }
        System.out.println(a);
    }
}

/*
main(String[] args):
     0 iconst_0
     1 istore_1
     2 iload_1
     3 iconst_2

     4 if_icmpge 13 (+9)    < 符合条件就进入循环体
     7 iinc 1 by 1          < a++
    10 goto 2 (-8)          < 向前跳转指令

    13 getstatic #7 <java/lang/System.out : Ljava/io/PrintStream;>
    16 iload_1
    17 invokevirtual #13 <java/io/PrintStream.println : (I)V>
    20 return

 */