package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.JvmThreadHolder;

public class FRETURN extends Instruction {

    public FRETURN(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        // 弹出操作数栈中的值
        StackValue stackValue = frame.pop();
        // 将当前栈帧从 jvm 线程栈中弹出
        JvmThreadHolder.get().pop();
        // 将方法返回值压入前一个栈帧的操作数栈中
        Frame topFrame = JvmThreadHolder.get().peek();
        topFrame.push(stackValue);
    }

}
