package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

public class LLOAD_1 extends Instruction {

    public LLOAD_1(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long value = frame.slotGetLong(1);
        frame.push(new StackValue(Const.T_LONG, value));
    }

}
