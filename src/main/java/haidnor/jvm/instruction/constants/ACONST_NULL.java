package haidnor.jvm.instruction.constants;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;

public class ACONST_NULL extends Instruction {

    public ACONST_NULL(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        frame.push(new StackValue(Const.T_OBJECT, null));
    }

}
