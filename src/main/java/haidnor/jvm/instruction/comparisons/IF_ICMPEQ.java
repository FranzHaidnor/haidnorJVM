package haidnor.jvm.instruction.comparisons;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;

/**
 * Java VM opcode.
 *
 * @author wang xiang
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.if_icmp_cond"> Opcode
 * definitions in The Java Virtual Machine Specification</a>
 */
public class IF_ICMPEQ extends Instruction {
    /**
     * 下次再执行的偏移量
     */
    private final int offSet;

    public IF_ICMPEQ(CodeStream codeStream) {
        super(codeStream);
        this.offSet = codeStream.readShort(this);
    }

    @Override
    public void execute(Frame frame) {
        StackValue v1 = frame.pop();
        StackValue v2 = frame.pop();

        if ((int) v1.getValue() == (int) v2.getValue()) {
            super.setOffSet(offSet);
        } else {
            super.setOffSet(3);
        }
    }

    @Override
    public String toString() {
        return super.getIndex() + " " + this.getClass().getSimpleName() + " " + offSet;
    }
}
