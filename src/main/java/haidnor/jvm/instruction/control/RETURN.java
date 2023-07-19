package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.JvmThreadHolder;

public class RETURN extends Instruction {

    public RETURN(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        JvmThreadHolder.get().pop();
    }

}
