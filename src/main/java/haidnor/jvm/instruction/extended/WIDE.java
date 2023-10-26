package haidnor.jvm.instruction.extended;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.instruction.loads.*;
import haidnor.jvm.instruction.math.IINC;
import haidnor.jvm.instruction.stores.*;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

public class WIDE extends Instruction {

    private final Instruction instruction;

    public WIDE(CodeStream codeStream) {
        super(codeStream);
        int wideOpcode = codeStream.readShort(this);

        switch (wideOpcode) {
            case 0x84 -> instruction = new IINC(codeStream);
            case 0x15 -> instruction = new ILOAD(codeStream);
            case 0x17 -> instruction = new FLOAD(codeStream);
            case 0x19 -> instruction = new ALOAD(codeStream);
            case 0x16 -> instruction = new LLOAD(codeStream);
            case 0x18 -> instruction = new DLOAD(codeStream);
            case 0x36 -> instruction = new ISTORE(codeStream);
            case 0x38 -> instruction = new FSTORE(codeStream);
            case 0x3a -> instruction = new ASTORE(codeStream);
            case 0x37 -> instruction = new LSTORE(codeStream);
            case 0x39 -> instruction = new DSTORE(codeStream);
            case 0xa9 -> throw new UnsupportedOperationException(); // ret, ignore
            default -> throw new UnsupportedOperationException();
        }
    }

    @Override
    public void execute(Frame frame) {
        instruction.execute(frame);
    }

}
