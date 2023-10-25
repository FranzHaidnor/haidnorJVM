package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.ConstantPool;
import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.bcel.classfile.Utility;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import lombok.SneakyThrows;

public class NEW extends Instruction {

    private final int constantClassIndex;

    public NEW(CodeStream codeStream) {
        super(codeStream);
        this.constantClassIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();
        String className = constantPool.constantClass_ClassName(constantClassIndex);

        if (className.startsWith("java/")) {
            frame.push(new StackValue(Const.T_OBJECT, null));
            return;
        }

        JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
        if (javaClass == null) {
            // 如果在元空间中找不到已加载的类,则开始进行类加载流程
            javaClass = frame.getJavaClass().getJVMClassLoader().loadWithClassPath(className);
        }
        Instance instance = javaClass.newInstance();
        frame.push(new StackValue(Const.T_OBJECT, instance));
    }

}
