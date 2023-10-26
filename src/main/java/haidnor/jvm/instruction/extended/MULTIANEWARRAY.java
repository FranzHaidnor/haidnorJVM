package haidnor.jvm.instruction.extended;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class MULTIANEWARRAY extends Instruction {

    public final int index;
    public final int dimensions;

    public MULTIANEWARRAY(CodeStream codeStream) {
        super(codeStream);
        this.index = codeStream.readUnsignedShort(this);
        this.dimensions = codeStream.readUnsignedByte(this);

        throw new UnsupportedOperationException("MULTIANEWARRAY");
    }

    @Override
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("MULTIANEWARRAY");
    }

}
