package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class IXOR extends Instruction {

    public IXOR(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        final int v2 = frame.popInt();
        final int v1 = frame.popInt();
        frame.pushInt(v1 ^ v2);
    }

}
