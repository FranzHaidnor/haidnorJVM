package haidnor.jvm.instruction.constants;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantPool;

/**
 * 将 long 或 double 型常量从常量池中推送至栈顶 (宽索引)
 */
public class LDC2W extends Instruction {

    private final byte constantTag;

    private long longValue;

    private double doubleValue;

    public LDC2W(CodeStream codeStream) {
        super(codeStream);
        int index = codeStream.readUnsignedShort(this);
        ConstantPool constantPool = codeStream.getCode().getConstantPool();
        Constant constant = constantPool.getConstant(index);
        this.constantTag = constant.getTag();
        if (constantTag == Const.CONSTANT_Long) {
            ConstantLong constantLong = (ConstantLong) constant;
            this.longValue = constantLong.getBytes();
        } else if (constantTag == Const.CONSTANT_Double) {
            ConstantDouble constantDouble = (ConstantDouble) constant;
            this.doubleValue = constantDouble.getBytes();
        }
    }

    @Override
    public void execute(Frame frame) {
        if (constantTag == Const.CONSTANT_Long) {
            frame.push(new StackValue(Const.T_LONG, this.longValue));
        } else if (constantTag == Const.CONSTANT_Double) {
            frame.push(new StackValue(Const.T_DOUBLE, this.doubleValue));
        }
    }

}
