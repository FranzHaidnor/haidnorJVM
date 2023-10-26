package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

public class SWAP extends Instruction {

    public SWAP(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        Instance v2 = frame.popRef();
        Instance v1 = frame.popRef();
        frame.pushRef(v2);
        frame.pushRef(v1);
    }

}
