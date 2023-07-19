package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;


public class FALOAD extends Instruction {

    public FALOAD(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.popInt();
        BasicTypeArray array = (BasicTypeArray) frame.popRef();
        frame.pushFloat(array.floats[index]);
    }

}
