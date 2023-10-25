package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.classfile.ConstantPool;
import haidnor.jvm.bcel.classfile.JavaField;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
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
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();
        String filedName = constantPool.getFieldName(constantFieldrefIndex);
        String fieldSignature = constantPool.getFieldSignature(constantFieldrefIndex);

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
