package haidnor.jvm.instruction.extended;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class GOTO_W extends Instruction {
    /**
     * 下次再执行的偏移量
     */
    private final int offSet;

    public GOTO_W(CodeStream codeStream) {
        super(codeStream);
        this.offSet = codeStream.readInt(this);
    }

    @Override
    public void execute(Frame frame) {
        super.setOffSet(offSet);
    }

    @Override
    public String toString() {
        return super.getIndex() + " " + this.getClass().getSimpleName() + " " + offSet;
    }
}
