package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * DUP_X2指令是一条Java虚拟机指令，用于复制栈顶元素并插入到栈顶下面的第三个元素位置。
 * <p>
 * 具体而言，DUP_X2指令的作用是将栈顶元素复制一份，并将复制出的值插入到栈顶下面的第三个元素位置。这种操作会在栈上产生一个额外的副本，栈的深度会增加1。
 * <p>
 * 根据栈顶元素以及其后面的元素数量，DUP_X2指令有三种不同的形式：
 * <p>
 * 栈顶元素为A，下面两个元素为B和C的情况：
 * <p>
 * 执行DUP_X2指令后，栈的状态变为：A（副本）、B、C、A。
 * 栈顶元素为A，下面三个元素为B、C和D的情况：
 * <p>
 * 执行DUP_X2指令后，栈的状态变为：A（副本）、B、C、D、A。
 * 栈顶元素为A，下面一个元素为B的情况：
 * <p>
 * 执行DUP_X2指令后，栈的状态变为：A（副本）、B、A。
 * 在Java虚拟机规范中，DUP_X2指令的操作码为0x5C，也属于堆栈管理指令家族（Stack Management Instructions）。
 * <p>
 * 需要注意的是，执行DUP_X2指令时，操作数栈必须至少有两个或三个元素，具体取决于栈顶元素和其后面的元素的数量，否则指令无法正常执行。
 */
public class DUP_X2 extends Instruction {

    public DUP_X2(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        StackValue stackValue1 = frame.pop();
        StackValue stackValue2 = frame.pop();
        StackValue stackValue3 = frame.pop();
        frame.push(stackValue1);
        frame.push(stackValue3);
        frame.push(stackValue2);
        frame.push(stackValue1);
    }

}
