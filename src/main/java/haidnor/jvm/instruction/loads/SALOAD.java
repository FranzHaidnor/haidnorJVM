package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;


public class SALOAD extends Instruction {

    public SALOAD(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.popInt();
        BasicTypeArray array = (BasicTypeArray) frame.popRef();
        frame.pushInt(array.ints[index]);
    }

}
