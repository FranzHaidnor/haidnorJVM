package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;

public class BASTORE extends Instruction {

    public BASTORE(CodeStream codeStream) {
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
