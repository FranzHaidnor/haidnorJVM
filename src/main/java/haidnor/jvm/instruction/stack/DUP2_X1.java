package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;
import haidnor.jvm.bcel.Const;

public class DUP2_X1 extends Instruction {

    public DUP2_X1(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        StackValue stackValue1 = frame.pop();
        StackValue stackValue2 = frame.pop();

        if (stackValue1.getValueType() == Const.T_DOUBLE || stackValue1.getValueType() == Const.T_LONG) {
            frame.push(stackValue1);
            frame.push(stackValue2);
            frame.push(stackValue1);
        } else {
            StackValue stackValue3 = frame.pop();
            frame.push(stackValue1);
            frame.push(stackValue2);
            frame.push(stackValue3);
            frame.push(stackValue1);
            frame.push(stackValue2);
        }
    }

}
