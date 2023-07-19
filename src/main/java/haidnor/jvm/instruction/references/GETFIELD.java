package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.KlassField;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import lombok.SneakyThrows;

public class GETFIELD extends Instruction {

    private final int constantFieldrefIndex;

    public GETFIELD(CodeStream codeStream) {
        super(codeStream);
        this.constantFieldrefIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPoolUtil constantPool = frame.getConstantPoolUtil();
        String filedName = constantPool.getFieldName(constantFieldrefIndex);
        String fieldSignature = constantPool.getFieldSignature(constantFieldrefIndex);

        Instance instanceRef = frame.popRef();
        KlassField field = instanceRef.getField(filedName, fieldSignature);
        switch (field.descriptor) {
            case "Z":
            case "C":
            case "B":
            case "S":
            case "I":
                frame.pushInt((int) field.getValue());
                break;
            case "J":
                frame.pushLong((long) field.getValue());
                break;
            case "F":
                frame.pushFloat((float) field.getValue());
                break;
            case "D":
                frame.pushDouble((double) field.getValue());
                break;
            default: // ref
                frame.pushRef(field.getValue());
                break;
        }
    }

}
