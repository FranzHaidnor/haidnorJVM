package haidnor.jvm.instruction.comparisons;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

/**
 * @author wang xiang
 */
public class DCMPL extends Instruction {

    public DCMPL(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        Double v2 = frame.popDouble();
        Double v1 = frame.popDouble();
        if (v1.equals(v2)) {
            frame.pushInt(0);
            return;
        }
        if (v1 < v2) {
            frame.pushInt(-1);
            return;
        }
        frame.pushInt(1);
    }

}
