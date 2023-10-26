package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class F2I extends Instruction {

    public F2I(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        float floatVal = frame.popFloat();
        frame.pushInt(Float.valueOf(floatVal).intValue());
    }

}
