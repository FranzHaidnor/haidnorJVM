package haidnor.jvm.instruction.constants;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * Java VM opcode.
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc_w"> Opcode definitions in
 *      The Java Virtual Machine Specification</a>
 */
public class LDC_W extends Instruction {

    private final int constantIndex;

    public LDC_W(CodeStream codeStream) {
        super(codeStream);
        this.constantIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();

        // 从常量池中获取值
        Constant constant = constantPool.getConstant(constantIndex);

        switch (constant.getTag()) {
            case Const.CONSTANT_Integer: {
                ConstantInteger constantInteger = (ConstantInteger) constant;
                Object value = constantInteger.getConstantValue(constantPool);
                frame.push(new StackValue(Const.T_INT, value));
                break;
            }
            case Const.CONSTANT_Float: {
                ConstantFloat constantFloat = (ConstantFloat) constant;
                Object value = constantFloat.getConstantValue(constantPool);
                frame.push(new StackValue(Const.T_FLOAT, value));
                break;
            }
            case Const.CONSTANT_String: {
                ConstantString constString = (ConstantString) constant;
                Object value = constString.getConstantValue(constantPool);
                frame.push(new StackValue(Const.T_OBJECT, value));
                break;
            }
            default:
                throw new Error("not supported LDCW type" + constant.getTag());
        }
    }

}
