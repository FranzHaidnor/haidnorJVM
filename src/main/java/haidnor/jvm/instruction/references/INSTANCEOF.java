package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import lombok.SneakyThrows;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Utility;

/**
 * @author wang xiang
 */
public class INSTANCEOF extends Instruction {

    private final int constantClassIndex;

    public INSTANCEOF(CodeStream codeStream) {
        super(codeStream);
        this.constantClassIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getConstantPool();
        ConstantPoolUtil constantPoolUtil = frame.getConstantPoolUtil();
        ConstantClass constantClass = constantPool.getConstant(constantClassIndex);
        String className = constantPoolUtil.getClassName(constantClass);
        className = Utility.compactClassName(className);

        StackValue stackValue = frame.pop();
        Object obj = stackValue.getValue();
        Class<?> objClass = obj.getClass();

        if (obj instanceof Instance instance) {
            String instanceClassName = instance.klass.getClassName();
            if (instanceClassName.equals(className)) {
                frame.push(new StackValue(Const.T_INT, 1));
            } else {
                frame.push(new StackValue(Const.T_INT, 0));
            }
        } else {
            if (objClass.getName().equals(className)) {
                frame.push(new StackValue(Const.T_INT, 1));
            } else {
                frame.push(new StackValue(Const.T_INT, 0));
            }
        }
    }

}
