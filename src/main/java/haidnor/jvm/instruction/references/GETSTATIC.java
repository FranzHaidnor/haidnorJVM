package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * 获取字段符号引用指定的对象或者值(类的静态字段 static 修饰),并将其压入操作数栈
 *
 * @author wang xiang
 */
public class GETSTATIC extends Instruction {

    private final int constantFieldrefIndex;

    public GETSTATIC(CodeStream codeStream) {
        super(codeStream);
        this.constantFieldrefIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantFieldref fieldref = frame.getJavaMethod().getConstantPool().getConstant(constantFieldrefIndex);
        // 动态链接. 找到字段所属的 Java 类
        String className = fieldref.getBelongClassName();
        // 动态链接. 找到字段的名字
        String fieldName = fieldref.getName();

        // 以上代码体现了动态链接.Class文件的常量池中存有大量的符号引用,字节码中的方法调用指令就以常量池里指向方法的符号引用作为参数.
        if (className.startsWith("java/")) {
            Class<?> clazz = Class.forName(className.replace('/', '.'));
            Field field = clazz.getField(fieldName);
            Object staticFiledValue = field.get(null);       // 获取静态字段上的值
            frame.push(new StackValue(Const.T_OBJECT, staticFiledValue));
        } else {
            JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
            JavaField staticField = javaClass.getStaticField(fieldName);
            frame.push(new StackValue(Const.T_OBJECT, staticField.getValue()));
        }
    }

}
