package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

/**
 * JVM中的I2B指令是用于执行将整数类型（int）转换为字节类型（byte）的指令。该指令将一个int类型的数值从操作数栈中弹出，并将其转换为一个字节（byte）。
 * 然后，将转换后的字节值压入操作数栈顶。
 * <p>
 * byte 类型的数据是以 int 形式存在的,因此不需要做任何处理
 */
public class I2B extends Instruction {

    public I2B(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {

    }

}
