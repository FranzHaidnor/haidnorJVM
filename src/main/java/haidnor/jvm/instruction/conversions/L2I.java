package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class L2I extends Instruction {

    public L2I(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long longVal = frame.popLong();
        frame.pushInt(Long.valueOf(longVal).intValue());
    }

}
