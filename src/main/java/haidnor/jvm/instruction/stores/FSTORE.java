package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;

public class FSTORE extends Instruction {

    private final int index;

    public FSTORE(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedShort(this);
    }

    @Override
    public void execute(Frame frame) {
        StackValue value = frame.pop();
        frame.slotSetFloat(index, (float) value.getValue());
    }

}
