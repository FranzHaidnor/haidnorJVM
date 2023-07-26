package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;

public class LOOKUPSWITCH extends Instruction {

    public LOOKUPSWITCH(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("LOOKUPSWITCH");
    }

    @Override
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("LOOKUPSWITCH");
    }

}
