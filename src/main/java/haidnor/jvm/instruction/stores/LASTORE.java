package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class LASTORE extends Instruction {

    public LASTORE(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        long val = frame.popLong();
        int index = frame.popInt();
        final BasicTypeArray array = (BasicTypeArray) frame.popRef();
        array.longs[index] = val;
    }

}
