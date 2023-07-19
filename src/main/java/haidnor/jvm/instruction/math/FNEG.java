package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import org.apache.bcel.Const;

public class FNEG extends Instruction {

    public FNEG(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        StackValue stackValue = frame.pop();
        float value = (float) stackValue.getValue();
        float tmp = -value;
        frame.push(new StackValue(Const.T_FLOAT, tmp));
    }

}
