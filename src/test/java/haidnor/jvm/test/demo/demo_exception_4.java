package haidnor.jvm.test.demo;

/**
 * 调用方没有捕获调用方法抛出的异常类型
 */
public class demo_exception_4 {

    public static void main(String[] args) {
        int a = 0;
        try {
            fun(a);
        } catch (NullPointerException exception) {
            System.out.println("catch Exception");
        }
    }

    public static void fun(int a) {
        System.out.println(1 / a);
    }

}
/*
main(String[] args)
     0 iconst_0
     1 istore_1
     2 iload_1
     3 invokestatic #7 <haidnor/jvm/test/demo/demo_exception_4.fun : (I)V>
     6 goto 18 (+12)
     9 astore_2
    10 getstatic #15 <java/lang/System.out : Ljava/io/PrintStream;>
    13 ldc #21 <catch Exception>
    15 invokevirtual #23 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    18 return

+--------+-------+--------+--------+--------------------------------+
| Nr.    | 起始PC | 结束PC  | 跳转PC  | 捕获类型                       |
+--------+-------+--------+--------+--------------------------------+
| 0      | 2     | 6      | 9      | java/lang/NullPointerException |
+--------+-------+--------+--------+--------------------------------+


fun(int a)
    0 getstatic #15 <java/lang/System.out : Ljava/io/PrintStream;>
    3 iconst_1
    4 iload_0
    5 idiv
    6 invokevirtual #29 <java/io/PrintStream.println : (I)V>
    9 return

*/