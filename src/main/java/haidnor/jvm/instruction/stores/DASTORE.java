package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class DASTORE extends Instruction {

    public DASTORE(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        double val = frame.popDouble();
        int index = frame.popInt();
        final BasicTypeArray array = (BasicTypeArray) frame.popRef();
        array.doubles[index] = val;
    }

}
