
package haidnor.jvm.instruction.conversions;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class D2F extends Instruction {

    public D2F(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    public void execute(Frame frame) {
        double doubleVal = frame.popDouble();
        frame.pushFloat(Double.valueOf(doubleVal).floatValue());
    }

}
