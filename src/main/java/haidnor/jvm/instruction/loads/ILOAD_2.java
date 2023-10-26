package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import haidnor.jvm.bcel.Const;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iload_n
 */
public class ILOAD_2 extends Instruction {

    public ILOAD_2(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int value = frame.slotGetInt(2);
        frame.push(new StackValue(Const.T_INT, value));
    }

}
