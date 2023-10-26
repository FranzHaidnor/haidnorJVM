package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;
import haidnor.jvm.bcel.Const;

/**
 * @author wang xiang
 */
public class DUP2_X2 extends Instruction {

    public DUP2_X2(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        StackValue stackValue1 = frame.pop();
        // v1(64)
        if (stackValue1.getValueType() == Const.T_DOUBLE || stackValue1.getValueType() == Const.T_LONG) {
            StackValue stackValue2 = frame.pop();

            // v1(64) v2(64)
            if (stackValue2.getValueType() == Const.T_DOUBLE || stackValue2.getValueType() == Const.T_LONG) {
                frame.push(stackValue1);
                frame.push(stackValue2);
                frame.push(stackValue1);
            }
            // v1(64) v2(32)
            else {
                StackValue stackValue3 = frame.pop();

                frame.push(stackValue1);
                frame.push(stackValue2);
                frame.push(stackValue3);
                frame.push(stackValue1);
            }
        }
        // v1(32)
        else {
            StackValue stackValue2 = frame.pop();
            StackValue stackValue3 = frame.pop();

            // v1(32) v2(32) v3(64)
            if (stackValue3.getValueType() == Const.T_DOUBLE || stackValue3.getValueType() == Const.T_LONG) {
                frame.push(stackValue1);
                frame.push(stackValue2);
                frame.push(stackValue3);
                frame.push(stackValue1);
                frame.push(stackValue2);
            }
            // v1(32) v2(32) v3(32) v4(32)
            else {
                StackValue stackValue4 = frame.pop();

                frame.push(stackValue1);
                frame.push(stackValue2);
                frame.push(stackValue3);
                frame.push(stackValue4);
                frame.push(stackValue1);
                frame.push(stackValue2);
            }
        }
    }

}
