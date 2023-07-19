package haidnor.jvm.instruction.comparisons;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;

public class IF_ACMPEQ extends Instruction {
    /**
     * 下次再执行的偏移量
     */
    private final int offSet;

    public IF_ACMPEQ(CodeStream codeStream) {
        super(codeStream);
        this.offSet = codeStream.readShort(this);
    }

    @Override
    public void execute(Frame frame) {
        Object val2 = frame.popRef();
        Object val1 = frame.popRef();
        if (val1 == val2) {
            super.setOffSet(offSet);
        } else {
            super.setOffSet(3);
        }
    }

    @Override
    public String toString() {
        return super.index() + " " + this.getClass().getSimpleName() + " " + offSet;
    }

}
