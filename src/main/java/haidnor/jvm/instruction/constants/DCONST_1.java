package haidnor.jvm.instruction.constants;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

/**
 * Java VM opcode.
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.dconst_d"> Opcode definitions
 *      in The Java Virtual Machine Specification</a>
 */
public class DCONST_1 extends Instruction {

    public DCONST_1(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        frame.push(new StackValue(Const.T_DOUBLE, 1.0D));
    }
    
}
