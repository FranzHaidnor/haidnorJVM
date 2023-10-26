package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LXOR extends Instruction {

    public LXOR(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long v2 = frame.popLong();
        long v1 = frame.popLong();
        frame.pushLong(v1 ^ v2);
    }

}
