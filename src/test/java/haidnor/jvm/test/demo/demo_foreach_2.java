package haidnor.jvm.test.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要添加启动参数
 * --add-opens java.base/java.util=ALL-UNNAMED
 */
public class demo_foreach_2 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

}
