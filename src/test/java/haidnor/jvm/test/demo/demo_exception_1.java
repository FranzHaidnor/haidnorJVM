package haidnor.jvm.test.demo;

/**
 * 调用方没有捕获调用方法的异常
 */
public class demo_exception_1 {

    public static void main(String[] args) {
        int a = 0;
        fun(a);
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
    3 invokestatic #7 <haidnor/jvm/test/demo/demo_exception_1.fun : (I)V>
    6 return

fun(int a)
     0 iload_0
     1 ifne 12 (+11)
     4 new #29 <java/lang/ArithmeticException>
     7 dup
     8 invokespecial #31 <java/lang/ArithmeticException.<init> : ()V>
    11 athrow
    12 return

*/