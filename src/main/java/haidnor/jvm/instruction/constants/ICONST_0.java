package haidnor.jvm.instruction.constants;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import org.apache.bcel.Const;

/**
 * https://docs.oracle.com/javase/specs/jvms/se19/html/jvms-6.html#jvms-6.5.iconst_i
 */
public class ICONST_0 extends Instruction {

    public ICONST_0(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        frame.push(new StackValue(Const.T_INT, 0));
    }

}
