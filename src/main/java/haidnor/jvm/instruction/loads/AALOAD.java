package haidnor.jvm.instruction.loads;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.InstanceArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;


public class AALOAD extends Instruction {

    public AALOAD(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.popInt();
        InstanceArray array = (InstanceArray) frame.popRef();
        Instance item = (Instance) array.items[index];
        frame.pushRef(item);
    }

}
