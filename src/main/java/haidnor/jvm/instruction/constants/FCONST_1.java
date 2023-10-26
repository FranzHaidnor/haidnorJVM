package haidnor.jvm.instruction.constants;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

/**
 * Java VM opcode.
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.fconst_f"> Opcode definitions
 *      in The Java Virtual Machine Specification</a>
 */
public class FCONST_1 extends Instruction {

    public FCONST_1(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        frame.push(new StackValue(Const.T_FLOAT, 1.0f));
    }
    
}
