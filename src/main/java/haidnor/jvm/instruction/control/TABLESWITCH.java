package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;

public class TABLESWITCH extends Instruction {

    public TABLESWITCH(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("TABLESWITCH");
    }

    @Override
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("TABLESWITCH");
    }

}
