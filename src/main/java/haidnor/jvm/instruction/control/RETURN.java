package haidnor.jvm.instruction.control;

import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.core.JVMThreadHolder;

public class RETURN extends ReturnableInstruction {

    public RETURN(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        JVMThreadHolder.get().pop();
    }

}
