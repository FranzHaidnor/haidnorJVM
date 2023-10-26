package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * DUP_X1指令是一条Java虚拟机指令，用于复制栈顶元素并插入到栈顶下面的第二个元素位置。
 * <p>
 * 具体而言，DUP_X1指令的作用是将栈顶元素复制一份，并将复制出的值插入到栈顶下面的第二个元素位置。这种操作会在栈上产生一个额外的副本，栈的深度会增加1。
 * <p>
 * 例如，如果栈顶是A，下面的两个元素是B和C，执行DUP_X1指令后，栈的状态将变为：A（副本）、B、C、A。
 * <p>
 * 在Java虚拟机规范中，DUP_X1指令的操作码为0x5A，属于堆栈管理指令家族（Stack Management Instructions）。
 * <p>
 * 需要注意的是，执行DUP_X1指令时，操作数栈必须至少有两个元素，否则指令无法正常执行。
 */
public class DUP_X1 extends Instruction {

    public DUP_X1(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        StackValue stackValue1 = frame.pop();
        StackValue stackValue2 = frame.pop();
        frame.push(stackValue1);
        frame.push(stackValue2);
        frame.push(stackValue1);
    }

}
