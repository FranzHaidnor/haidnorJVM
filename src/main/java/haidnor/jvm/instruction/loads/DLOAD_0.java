package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class DLOAD_0 extends Instruction {

    public DLOAD_0(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        double value = frame.slotGetDouble(0);
        frame.push(new StackValue(Const.T_DOUBLE, value));
    }

}
