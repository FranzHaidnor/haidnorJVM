package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.BasicTypeArray;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;
import haidnor.jvm.bcel.Const;

public class NEWARRAY extends Instruction {

    private final int type;

    public NEWARRAY(CodeStream codeStream) {
        super(codeStream);
        this.type = codeStream.readUnsignedByte(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        int size = frame.popInt();
        switch (type) {
            case Const.T_BOOLEAN -> {
                frame.pushRef(BasicTypeArray.boolArray(size));
            }
            case Const.T_CHAR -> {
                frame.pushRef(BasicTypeArray.charArray(size));
            }
            case Const.T_FLOAT -> {
                frame.pushRef(BasicTypeArray.floatArray(size));
            }
            case Const.T_DOUBLE -> {
                frame.pushRef(BasicTypeArray.doubleArray(size));
            }
            case Const.T_BYTE -> {
                frame.pushRef(BasicTypeArray.byteArray(size));
            }
            case Const.T_SHORT -> {
                frame.pushRef(BasicTypeArray.shortArray(size));
            }
            case Const.T_INT -> {
                frame.pushRef(BasicTypeArray.intArray(size));
            }
            case Const.T_LONG -> {
                frame.pushRef(BasicTypeArray.longArray(size));
            }
            default -> throw new IllegalArgumentException();
        }

    }

}
