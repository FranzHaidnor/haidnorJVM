package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;


public class LLOAD extends Instruction {

    private final int index;

    public LLOAD(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedByte(this);
    }

    @Override
    public void execute(Frame frame) {
        long value = frame.slotGetLong(index);
        frame.push(new StackValue(Const.T_LONG, value));
    }

}
