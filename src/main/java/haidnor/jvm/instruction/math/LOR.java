package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LOR extends Instruction {

    public LOR(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        Long v2 = frame.popLong();
        Long v1 = frame.popLong();
        frame.pushLong(v1 | v2);
    }

}
