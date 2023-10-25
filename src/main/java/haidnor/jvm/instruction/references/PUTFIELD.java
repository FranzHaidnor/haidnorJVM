package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.classfile.ConstantPool;
import haidnor.jvm.bcel.classfile.JavaField;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
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
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();

        String filedName = constantPool.getFieldName(constantFieldrefIndex);
        String fieldSignature = constantPool.getFieldSignature(constantFieldrefIndex);

        StackValue stackValue = frame.pop();

        Instance instance = frame.popRef();
        JavaField field = instance.getField(filedName, fieldSignature);
        field.setValue(stackValue);
    }

}
