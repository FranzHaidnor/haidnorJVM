package haidnor.jvm.instruction.stores;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.InstanceArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;

public class AASTORE extends Instruction {

    public AASTORE(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        Instance val = frame.popRef();
        int index = frame.popInt();
        InstanceArray array = (InstanceArray) frame.popRef();
        array.items[index] = val;
    }

}
