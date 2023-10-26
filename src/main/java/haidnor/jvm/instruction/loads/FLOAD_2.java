package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class FLOAD_2 extends Instruction {

    public FLOAD_2(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        float value = frame.slotGetFloat(2);
        frame.push(new StackValue(Const.T_FLOAT, value));
    }

}
