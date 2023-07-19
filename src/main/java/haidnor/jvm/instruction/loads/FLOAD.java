package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import org.apache.bcel.Const;


public class FLOAD extends Instruction {

    private final int index;

    public FLOAD(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedByte(this);
    }

    @Override
    public void execute(Frame frame) {
        float value = frame.slotGetFloat(index);
        frame.push(new StackValue(Const.T_FLOAT, value));
    }

}
