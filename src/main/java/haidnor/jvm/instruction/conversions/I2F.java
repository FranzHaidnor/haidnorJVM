package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class I2F extends Instruction {

    public I2F(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int intVal = frame.popInt();
        frame.pushFloat(Integer.valueOf(intVal).floatValue());
    }

}
