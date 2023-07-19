package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.InstanceArray;
import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.metaspace.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import lombok.SneakyThrows;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Utility;

public class ANEWARRAY extends Instruction {

    private final int constantClassIndex;

    public ANEWARRAY(CodeStream codeStream) {
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

        Klass klass = Metaspace.getJavaClass(Utility.compactClassName(className));
        if (klass == null) {
            // 如果在元空间中找不到已加载的类,则开始进行类加载流程
            klass = frame.getMetaClass().getClassLoader().loadClass(className);
        }
        int size = frame.popInt();
        Instance[] items = new Instance[size];
        InstanceArray instanceArray = new InstanceArray(klass, items);

        frame.push(new StackValue(Const.T_OBJECT, instanceArray));
    }

}
