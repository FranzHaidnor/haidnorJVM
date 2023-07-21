package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.heap.KlassField;
import haidnor.jvm.rtda.metaspace.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import lombok.SneakyThrows;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Utility;

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
        ConstantPool constantPool = frame.getConstantPool();
        ConstantPoolUtil constantPoolUtil = frame.getConstantPoolUtil();
        ConstantFieldref constFieldref = constantPool.getConstant(constantFieldrefIndex);
        // 动态链接. 找到字段所属的 Java 类
        String className = constantPoolUtil.getFiledBelongClassName(constFieldref);
        // 动态链接. 找到字段的名字
        String fieldName = constantPoolUtil.getFieldName(constFieldref);
        // 以上代码体现了动态链接.Class文件的常量池中存有大量的符号引用,字节码中的方法调用指令就以常量池里指向方法的符号引用作为参数.

        Klass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
        KlassField staticField = javaClass.getStaticField(fieldName);
        staticField.setVal(frame.pop());
    }

}
