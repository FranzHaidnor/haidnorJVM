package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class I2D extends Instruction {

    public I2D(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int intVal = frame.popInt();
        frame.pushDouble(Integer.valueOf(intVal).doubleValue());
    }

}
