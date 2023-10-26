package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.ConstantClass;
import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.bcel.classfile.Utility;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.rtda.InstanceArray;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;

public class ANEWARRAY extends Instruction {

    private final int constantClassIndex;

    public ANEWARRAY(CodeStream codeStream) {
        super(codeStream);
        this.constantClassIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    public void execute(Frame frame) {
        ConstantClass constantClass = frame.getJavaMethod().getConstantPool().getConstant(constantClassIndex);
        String className = constantClass.getClassName();

        JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
        if (javaClass == null) {
            // 如果在元空间中找不到已加载的类,则开始进行类加载流程
            javaClass = frame.getJavaClass().getJVMClassLoader().loadWithClassPath(className);
        }
        int size = frame.popInt();
        Instance[] items = new Instance[size];
        InstanceArray instanceArray = new InstanceArray(javaClass, items);

        frame.push(new StackValue(Const.T_OBJECT, instanceArray));
    }

}
