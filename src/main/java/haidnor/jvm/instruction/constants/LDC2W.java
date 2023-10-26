package haidnor.jvm.instruction.constants;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.Constant;
import haidnor.jvm.bcel.classfile.ConstantDouble;
import haidnor.jvm.bcel.classfile.ConstantLong;
import haidnor.jvm.bcel.classfile.ConstantPool;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;

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
