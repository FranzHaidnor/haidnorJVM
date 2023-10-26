package haidnor.jvm.instruction.control;

import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.core.JVMThreadHolder;

public class ARETURN extends ReturnableInstruction {

    public ARETURN(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        // 弹出操作数栈中的值
        StackValue stackValue = frame.pop();
        // 将当前栈帧从 jvm 线程栈中弹出
        JVMThreadHolder.get().pop();
        // 将方法返回值压入前一个栈帧的操作数栈中
        Frame topFrame = JVMThreadHolder.get().peek();
        topFrame.push(stackValue);
    }

}
