package haidnor.jvm.test.demo;

/**
 * 调用方捕获调用方法的异常
 */
public class demo_exception_2 {

    public static void main(String[] args) {
        int a = 0;
        try {
            fun(a);
        } catch (Exception exception) {
            System.out.println("catch Exception");
        }
    }

    public static void fun(int a) {
        if (a == 0) {
            throw new ArithmeticException();
        }
    }

}
/*
main(String[] args)
    0 iconst_0
    1 istore_1
    2 iload_1
    3 invokestatic #7 <haidnor/jvm/test/demo/demo_exception.fun : (I)V>
    6 goto 18 (+12)
    9 astore_2
    10 getstatic #15 <java/lang/System.out : Ljava/io/PrintStream;>
    13 ldc #21 <catch Exception>
    15 invokevirtual #23 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    18 return

+--------+-------+--------+--------+---------------------+
| Nr.    | 起始PC | 结束PC  | 跳转PC  | 捕获类型             |
+--------+-------+--------+--------+---------------------+
| 0      | 2     | 6      | 9      | java/lang/Exception |
+--------+-------+--------+--------+---------------------+


fun(int a)
     0 iload_0
     1 ifne 12 (+11)
     4 new #29 <java/lang/ArithmeticException>
     7 dup
     8 invokespecial #31 <java/lang/ArithmeticException.<init> : ()V>
    11 athrow
    12 return

*/