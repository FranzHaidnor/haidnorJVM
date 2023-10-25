package haidnor.jvm.instruction.control;

import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.JVMThreadHolder;

public class RETURN extends ReturnableInstruction {

    public RETURN(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        JVMThreadHolder.get().pop();
    }

}
