package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LSHR extends Instruction {

    public LSHR(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        final int v2 = frame.popInt();
        final long v1 = frame.popLong();
        frame.pushLong(v1 >> v2);
    }

}
