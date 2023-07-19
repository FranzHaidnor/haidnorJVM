package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import org.apache.bcel.Const;

public class LLOAD_0 extends Instruction {

    public LLOAD_0(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long value = frame.slotGetLong(0);
        frame.push(new StackValue(Const.T_LONG, value));
    }

}
