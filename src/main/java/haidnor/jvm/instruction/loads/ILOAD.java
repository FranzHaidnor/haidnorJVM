package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;


public class ILOAD extends Instruction {

    private final int index;

    public ILOAD(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedByte(this);
    }

    @Override
    public void execute(Frame frame) {
        int value = frame.slotGetInt(index);
        frame.push(new StackValue(Const.T_INT, value));
    }

}
