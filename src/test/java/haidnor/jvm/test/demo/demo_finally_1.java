package haidnor.jvm.test.demo;

public class demo_finally_1 {

    public static void main(String[] args) {
        String name = fun();
        System.out.println(name);
    }

    public static String fun() {
        String str = "zhang san";
        try {
            return str;
        } finally {
            str = "li si";
        }
    }

}
/*
main(String[] args):
     0 invokestatic #7 <haidnor/jvm/test/demo/TEST_finally.fun : ()Ljava/lang/String;>
     3 astore_1
     4 getstatic #13 <java/lang/System.out : Ljava/io/PrintStream;>
     7 aload_1
     8 invokevirtual #19 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    11 return

fun():
     0 ldc #25 <zhang san>
     2 astore_0
     3 aload_0
     4 astore_1
     5 ldc #27 <li si>
     7 astore_0
     8 aload_1
     9 areturn
    10 astore_2             < 如果 3-5 指令出现了异常时才会跳转到 10. 如果出现了异常会把异常压入操作数栈，这里的 astore_2 就是把异常先存储起来。
    11 ldc #27 <li si>
    13 astore_0
    14 aload_2             < 执行完 finally 把 astore_2 的异常压入操作树栈
    15 athrow              < 把操作数栈上的异常抛出

Exception Table:
+--------+-------+--------+--------+---------+
| Nr.    | 起始PC | 结束PC  | 跳转PC | 捕获类型 |
+--------+-------+--------+--------+---------+
| 0      | 3     | 5      | 10     | any     |
+--------+-------+--------+--------+---------+

*/