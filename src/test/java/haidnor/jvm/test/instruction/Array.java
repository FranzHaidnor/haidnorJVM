package haidnor.jvm.test.instruction;

import haidnor.jvm.test.instruction.loads.Student1;

public class Array {
    public static void main(String[] args) {
        Student1[] strArr = new Student1[10];
        strArr[5] = new Student1();
    }
}
