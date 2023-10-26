package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class INEG extends Instruction {

    public INEG(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        StackValue stackValue = frame.pop();
        int value = (int) stackValue.getValue();
        int tmp = -value;
        frame.push(new StackValue(Const.T_INT, tmp));
    }

}
