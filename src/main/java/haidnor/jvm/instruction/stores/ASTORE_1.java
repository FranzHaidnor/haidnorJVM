package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;

public class ASTORE_1 extends Instruction {

    public ASTORE_1(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        StackValue value = frame.pop();
        frame.slotSetRef(1, value.getValue());
    }

}
