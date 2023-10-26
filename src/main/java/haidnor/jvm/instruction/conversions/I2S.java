package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class I2S extends Instruction {

    public I2S(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int intVal = frame.popInt();
        short shortValue = Integer.valueOf(intVal).shortValue();
        frame.pushInt(((int) shortValue));
    }

}
