package haidnor.jvm.test.demo;

/**
 * try 异常后执行 finally
 */
public class demo_finally_2 {

    public static void main(String[] args) {
        fun();
    }

    public static void fun() {
        try {
            System.out.println(1 / 0);
        } finally {
            System.out.println("此时应该执行 finally");
        }
    }

}
/*
main(String[] args):
    0 invokestatic #7 <haidnor/jvm/test/demo/demo_finally_2.fun : ()V>
    3 return


fun():
     0 getstatic #12 <java/lang/System.out : Ljava/io/PrintStream;>
     3 iconst_1
     4 iconst_0
     5 idiv
     6 invokevirtual #18 <java/io/PrintStream.println : (I)V>
     9 getstatic #12 <java/lang/System.out : Ljava/io/PrintStream;>
    12 ldc #24 <try>
    14 invokevirtual #26 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    17 getstatic #12 <java/lang/System.out : Ljava/io/PrintStream;>
    20 ldc #29 <此时不应该执行 finally>
    22 invokevirtual #26 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    25 goto 39 (+14)
    28 astore_0
    29 getstatic #12 <java/lang/System.out : Ljava/io/PrintStream;>
    32 ldc #29 <此时不应该执行 finally>
    34 invokevirtual #26 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    37 aload_0
    38 athrow
    39 return

Exception Table:
+--------+-------+--------+--------+---------+
| Nr.    | 起始PC | 结束PC  | 跳转PC | 捕获类型 |
+--------+-------+--------+--------+---------+
| 0      | 9     | 17     | 28     | any     |
+--------+-------+--------+--------+---------+

*/