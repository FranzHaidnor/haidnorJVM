package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

/**
 * JVM中的IUSHR指令是用于执行无符号右移操作的指令。该指令将两个整数值从操作数栈中弹出，
 * 然后将第一个操作数的位表示向右移动第二个操作数指定的位数，移位过程中高位用零填充。最后，将结果压入操作数栈中。
 *
 * 在Java虚拟机规范中，IUSHR指令的操作码为0x7C，它属于逻辑指令家族（logical instructions）。
 * 这个指令通常需用到无符号数值在位级上的运算，因此它主要用于一些特定的计算和算法中。
 *
 * 需要注意的是，IUSHR指令只适用于int类型的数据。如果操作数是long类型，则需要使用LUSHR指令进行无符号右移操作。
 */
public class IUSHR extends Instruction {

    public IUSHR(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int v2 = frame.popInt();
        int v1 = frame.popInt();
        int s = v2 & 0x1f;

        if (v1 >= 0) {
            int ret = v1 >> s;
            frame.pushInt(ret);
            return;
        }
        int ret = (v1 >> s) + (2 << ~s);
        frame.pushInt(ret);
    }

}
