package haidnor.jvm.instruction.comparisons;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;

public class IFEQ extends Instruction {
    /**
     * 下次再执行的偏移量
     */
    private final int offSet;

    public IFEQ(CodeStream codeStream) {
        super(codeStream);
        this.offSet = codeStream.readShort(this);
    }

    @Override
    public void execute(Frame frame) {
        StackValue v1 = frame.pop();
        if ((int) v1.getValue() == 0) {
            super.setOffSet(offSet);
        } else {
            super.setOffSet(3);
        }
    }

    @Override
    public String toString() {
        return super.index() + " " + this.getClass().getSimpleName() + " "  + offSet;
    }

}
