package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class IASTORE extends Instruction {

    public IASTORE(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int val = frame.popInt();
        int index = frame.popInt();
        final BasicTypeArray array = (BasicTypeArray) frame.popRef();
        array.ints[index] = val;
    }

}
