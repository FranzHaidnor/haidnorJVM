package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LAND extends Instruction {

    public LAND(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long a1 = frame.popLong();
        long a2 = frame.popLong();
        frame.pushLong(a2 & a1);
    }

}
