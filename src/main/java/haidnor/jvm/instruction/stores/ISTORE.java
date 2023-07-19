package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;

public class ISTORE extends Instruction {

    private final int index;

    public ISTORE(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedShort(this);
    }

    @Override
    public void execute(Frame frame) {
        StackValue value = frame.pop();
        frame.slotSetInt(index, (int) value.getValue());
    }

}
