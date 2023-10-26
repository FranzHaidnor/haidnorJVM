package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class L2D extends Instruction {

    public L2D(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long longVal = frame.popLong();
        frame.pushDouble(Long.valueOf(longVal).doubleValue());
    }

}
