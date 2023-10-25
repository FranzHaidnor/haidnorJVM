package haidnor.jvm.test.demo;

import java.util.List;

/**
 * 需要添加启动参数
 * --add-opens java.base/java.util=ALL-UNNAMED
 */
public class demo_foreach_3 {

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        list.add(6);

//        for (Integer integer : list) {
//            System.out.println(integer);
//        }
    }

}
