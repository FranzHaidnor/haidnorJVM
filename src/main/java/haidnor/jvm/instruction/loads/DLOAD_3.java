package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import org.apache.bcel.Const;

public class DLOAD_3 extends Instruction {

    public DLOAD_3(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        double value = frame.slotGetDouble(3);
        frame.push(new StackValue(Const.T_DOUBLE, value));
    }

}
