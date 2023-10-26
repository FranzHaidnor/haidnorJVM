package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

public class MONITORENTER extends Instruction {

    public MONITORENTER(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("MONITORENTER");
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("MONITORENTER");
    }

}
