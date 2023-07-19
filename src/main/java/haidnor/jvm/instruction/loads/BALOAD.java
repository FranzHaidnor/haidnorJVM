package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;


public class BALOAD extends Instruction {

    public BALOAD(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.popInt();
        BasicTypeArray array = (BasicTypeArray) frame.popRef();
        frame.pushInt(array.ints[index]);
    }

}
