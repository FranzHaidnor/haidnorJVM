package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LSHL extends Instruction {

    public LSHL(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int v2 = frame.popInt();
        long v1 = frame.popLong();
        int s = v2 & 0x1f;
        long ret = v1 << s;
        frame.pushLong(ret);
    }

}
