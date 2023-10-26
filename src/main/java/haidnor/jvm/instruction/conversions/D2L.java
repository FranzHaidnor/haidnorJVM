package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class D2L extends Instruction {

    public D2L(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        double doubleVal = frame.popDouble();
        frame.pushLong(Double.valueOf(doubleVal).longValue());
    }

}
