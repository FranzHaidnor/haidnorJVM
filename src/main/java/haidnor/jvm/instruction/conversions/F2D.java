package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class F2D extends Instruction {

    public F2D(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        float floatVal = frame.popFloat();
        frame.pushDouble(Float.valueOf(floatVal).doubleValue());
    }

}
