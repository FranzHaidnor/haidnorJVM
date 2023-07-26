package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
import lombok.SneakyThrows;

public class INVOKEDYNAMIC extends Instruction {

    public INVOKEDYNAMIC(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("INVOKEDYNAMIC");
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("INVOKEDYNAMIC");
    }

}
