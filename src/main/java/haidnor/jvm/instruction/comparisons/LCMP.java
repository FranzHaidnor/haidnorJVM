package haidnor.jvm.instruction.comparisons;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LCMP extends Instruction {

    public LCMP(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        Long v2 = frame.popLong();
        Long v1 = frame.popLong();
        if (v1.equals(v2)) {
            frame.pushInt(0);
            return;
        }
        if (v1 < v2) {
            frame.pushInt(-1);
            return;
        }
        frame.pushInt(1);
    }

}
