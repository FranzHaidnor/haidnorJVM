package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;

public class ASTORE extends Instruction {

    private final int index;

    public ASTORE(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedByte(this);
    }

    @Override
    public void execute(Frame frame) {
        StackValue value = frame.pop();
        frame.slotSetRef(index, (Instance) value.getValue());
    }

}
