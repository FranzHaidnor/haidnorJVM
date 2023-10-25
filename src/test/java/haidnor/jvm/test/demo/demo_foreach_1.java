package haidnor.jvm.test.demo;

public class demo_foreach_1 {
    public static void main(String[] args) {
        int[] arr = new int[]{999, 333};
        for (int i : arr) {
            System.out.println(i);
        }
    }
}

/*
main(String[] args):
     0 iconst_2              < 操作数栈压入2,这个是 arr[] 的长度
     1 newarray 10 (int)     < 创建数组
     3 dup

     4 iconst_0
     5 sipush 999
     8 iastore               < arr[0] = 999

     9 dup                   < 复制栈顶的 arr[], 压入操作数栈

    10 iconst_1
    11 sipush 333
    14 iastore                < arr[1] = 333

    15 astore_1
    16 aload_1
    17 astore_2
    18 aload_2
    19 arraylength
    20 istore_3
    21 iconst_0
    22 istore 4

    24 iload 4
    26 iload_3
    27 if_icmpge 50 (+23)
    30 aload_2
    31 iload 4
    33 iaload
    34 istore 5
    36 getstatic #7 <java/lang/System.out : Ljava/io/PrintStream;>
    39 iload 5
    41 invokevirtual #13 <java/io/PrintStream.println : (I)V>
    44 iinc 4 by 1
    47 goto 24 (-23)

    50 return

+--------+-------+--------+--------+-----------+-------------------------+
| Nr.    | 起始PC | 长度   | 序号    | 名字       | 描述符(存储类型)          |
+--------+-------+--------+--------+-----------+-------------------------+
| 0      | 0     | 51     | 0      | args      | [Ljava/lang/String;     |
+--------+-------+--------+--------+-----------+-------------------------+
| 1      | 16    | 35     | 1      | arr       | [I                      |
+--------+-------+--------+--------+-----------+-------------------------+
| 2      | 36    | 8      | 5      | i         | I                       |
+--------+-------+--------+--------+-----------+-------------------------+

 */