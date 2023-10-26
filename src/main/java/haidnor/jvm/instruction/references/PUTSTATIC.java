package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * @author wang xiang
 */
public class PUTSTATIC extends Instruction {

    private final int constantFieldrefIndex;

    public PUTSTATIC(CodeStream codeStream) {
        super(codeStream);
        this.constantFieldrefIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantFieldref constFieldref = frame.getJavaMethod().getConstantPool().getConstant(constantFieldrefIndex);
        // 动态链接. 找到字段所属的 Java 类
        String className = constFieldref.getBelongClassName();
        // 动态链接. 找到字段的名字
        String fieldName = constFieldref.getName();
        // 以上代码体现了动态链接.Class文件的常量池中存有大量的符号引用,字节码中的方法调用指令就以常量池里指向方法的符号引用作为参数.

        JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
        JavaField staticField = javaClass.getStaticField(fieldName);
        staticField.setValue(frame.pop());
    }

}
