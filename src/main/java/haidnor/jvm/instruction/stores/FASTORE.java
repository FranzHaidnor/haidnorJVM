package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class FASTORE extends Instruction {

    public FASTORE(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        float val = frame.popFloat();
        int index = frame.popInt();
        final BasicTypeArray array = (BasicTypeArray) frame.popRef();
        array.floats[index] = val;
    }

}
