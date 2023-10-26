package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.classfile.ConstantFieldref;
import haidnor.jvm.bcel.classfile.JavaField;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
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
        ConstantFieldref fieldref = frame.getJavaMethod().getConstantPool().getConstant(constantFieldrefIndex);
        String filedName = fieldref.getName();
        String fieldSignature = fieldref.getSignature();

        Instance instanceRef = frame.popRef();
        JavaField field = instanceRef.getField(filedName, fieldSignature);
        switch (field.getSignature()) {
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
