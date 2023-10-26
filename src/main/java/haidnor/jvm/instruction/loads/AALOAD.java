package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.rtda.InstanceArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;


public class AALOAD extends Instruction {

    public AALOAD(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.popInt();
        InstanceArray array = (InstanceArray) frame.popRef();
        Instance item = array.items[index];
        frame.pushRef(item);
    }

}
