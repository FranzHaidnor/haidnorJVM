package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class IREM extends Instruction {

    public IREM(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        StackValue value2 = frame.pop();
        StackValue value1 = frame.pop();
        int result = (int) value1.getValue() % (int) value2.getValue();
        frame.push(new StackValue(Const.T_INT, result));
    }

}
