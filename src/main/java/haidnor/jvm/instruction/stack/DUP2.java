package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * DUP2指令是一条Java虚拟机指令，用于复制栈顶两个元素，并将复制的值插入到栈顶之后。
 * <p>
 * DUP2指令有以下几种形式：
 * <p>
 * 栈顶元素为A的情况：
 * <p>
 * 执行DUP2指令后，栈的状态变为：A（副本）、A。
 * 栈顶元素为A和B的情况：
 * <p>
 * 执行DUP2指令后，栈的状态变为：A（副本）、B、A、B。
 * 需要注意的是，在执行DUP2指令时，操作数栈必须至少有一个或两个元素，具体取决于栈顶元素的数量。如果栈顶元素只有一个，则只会复制该元素一次；如果栈顶元素有两个，则会分别复制两个元素。
 * <p>
 * 在Java虚拟机规范中，DUP2指令的操作码为0x5C，也属于堆栈管理指令家族（Stack Management Instructions）。
 */
public class DUP2 extends Instruction {

    public DUP2(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        StackValue stackValue1 = frame.pop();
        StackValue stackValue2 = frame.pop();
        frame.push(stackValue2);
        frame.push(stackValue1);
        frame.push(stackValue2);
        frame.push(stackValue1);
    }

}
