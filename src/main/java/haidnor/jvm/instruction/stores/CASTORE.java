package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class CASTORE extends Instruction {

    public CASTORE(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int val = frame.popInt();
        int index = frame.popInt();
        BasicTypeArray array = (BasicTypeArray) frame.popRef();
        array.ints[index] = val;
    }

}
