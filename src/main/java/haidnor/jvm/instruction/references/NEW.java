package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.rtda.Klass;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import lombok.SneakyThrows;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.Utility;

public class NEW extends Instruction {

    private final int constantClassIndex;

    public NEW(CodeStream codeStream) {
        super(codeStream);
        this.constantClassIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPoolUtil constantPoolUtil = frame.getConstantPoolUtil();
        String className = constantPoolUtil.constantClass_ClassName(constantClassIndex);

        if (className.startsWith("java/")) {
            frame.push(new StackValue(Const.T_OBJECT, null));
            return;
        }

        Klass klass = Metaspace.getJavaClass(Utility.compactClassName(className));
        if (klass == null) {
            // 如果在元空间中找不到已加载的类,则开始进行类加载流程
            klass = frame.getMetaClass().getClassLoader().loadClass(className);
        }
        Instance instance = klass.newInstance();
        frame.push(new StackValue(Const.T_OBJECT, instance));
    }

}
