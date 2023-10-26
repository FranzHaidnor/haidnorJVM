package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class ALOAD_3 extends Instruction {

    public ALOAD_3(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        Object value = frame.slotGetRef(3);
        frame.push(new StackValue(Const.T_OBJECT, value));
    }

}
