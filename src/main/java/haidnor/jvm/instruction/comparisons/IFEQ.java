package haidnor.jvm.instruction.comparisons;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;

public class IFEQ extends Instruction {

    private final int offSet;

    public IFEQ(CodeStream codeStream) {
        super(codeStream);
        this.offSet = codeStream.readShort(this);
    }

    @Override
    public void execute(Frame frame) {
        StackValue v1 = frame.pop();
        if (v1.getValue() instanceof Boolean) {
            if (!((boolean) v1.getValue())) {
                super.setOffSet(offSet);
            } else {
                super.setOffSet(3);
            }
        } else {
            if ((int) v1.getValue() == 0) {
                super.setOffSet(offSet);
            } else {
                super.setOffSet(3);
            }
        }
    }

    @Override
    public String toString() {
        return super.getIndex() + " " + this.getClass().getSimpleName() + " " + offSet;
    }

}
