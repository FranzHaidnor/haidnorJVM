package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

public class MONITOREXIT extends Instruction {

    public MONITOREXIT(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("MONITOREXIT");
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("MONITOREXIT");
    }

}
