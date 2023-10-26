package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.ArrayInstance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

public class ARRAYLENGTH extends Instruction {

    public ARRAYLENGTH(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ArrayInstance array = (ArrayInstance) frame.popRef();
        frame.pushInt(array.size);
    }

}
