package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class L2F extends Instruction {

    public L2F(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long longVal = frame.popLong();
        frame.pushFloat(Long.valueOf(longVal).floatValue());
    }

}
