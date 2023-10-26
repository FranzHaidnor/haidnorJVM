package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class LNEG extends Instruction {

    public LNEG(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        StackValue stackValue = frame.pop();
        long value = (long) stackValue.getValue();
        long tmp = -value;
        frame.push(new StackValue(Const.T_LONG, tmp));
    }

}
