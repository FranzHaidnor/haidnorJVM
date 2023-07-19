package haidnor.jvm.test.instruction.references;

import java.util.HashSet;

public class INVOKEINTERFACE {

    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        int size = set.size();
        System.out.println(size);
    }

}
