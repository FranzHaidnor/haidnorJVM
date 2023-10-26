package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.classfile.ConstantFieldref;
import haidnor.jvm.bcel.classfile.JavaField;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

public class PUTFIELD extends Instruction {

    private final int constantFieldrefIndex;

    public PUTFIELD(CodeStream codeStream) {
        super(codeStream);
        this.constantFieldrefIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantFieldref fieldref = frame.getJavaMethod().getConstantPool().getConstant(constantFieldrefIndex);
        String filedName = fieldref.getName();
        String fieldSignature = fieldref.getSignature();

        StackValue stackValue = frame.pop();

        Instance instance = frame.popRef();
        JavaField field = instance.getField(filedName, fieldSignature);
        field.setValue(stackValue);
    }

}
