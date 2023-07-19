package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.KlassField;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
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
        ConstantPoolUtil constantPool = frame.getConstantPoolUtil();
        String filedName = constantPool.getFieldName(constantFieldrefIndex);
        String fieldSignature = constantPool.getFieldSignature(constantFieldrefIndex);

        StackValue stackValue = frame.pop();

        Instance instance = frame.popRef();
        KlassField field = instance.getField(filedName, fieldSignature);
        field.setVal(stackValue);
    }

}
