package haidnor.jvm.instruction.extended;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

/**
 * Java VM opcode.
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.goto"> Opcode definitions in
 *      The Java Virtual Machine Specification</a>
 */
public class GOTO extends Instruction {
    /**
     * 下次再执行的偏移量
     */
    private final int offSet;

    public GOTO(CodeStream codeStream) {
        super(codeStream);
        this.offSet = codeStream.readShort(this);
    }

    @Override
    public void execute(Frame frame) {
        super.setOffSet(offSet);
    }

    @Override
    public String toString() {
        return super.getIndex() + " " + this.getClass().getSimpleName() + " "  + offSet;
    }
}
