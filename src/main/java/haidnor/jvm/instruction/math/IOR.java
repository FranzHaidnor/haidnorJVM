package haidnor.jvm.instruction.math;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class IOR extends Instruction {

    public IOR(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int v2 = frame.popInt();
        int v1 = frame.popInt();
        frame.pushInt(v1 | v2);
    }

}
