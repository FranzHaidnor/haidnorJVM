package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.classloader.JVMClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * 调用一些需要特殊处理的实例方法,包括构造器方法,私有方法,父类方法,这些方法都是静态绑定的,不会在调用时进行动态分配
 */
public class INVOKESPECIAL extends Instruction {

    private final int constantMethodrefIndex;

    public INVOKESPECIAL(CodeStream codeStream) {
        super(codeStream);
        this.constantMethodrefIndex = codeStream.readUnsignedShort(this);
    }

    @SneakyThrows
    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();
        ConstantMethodref methodref = constantPool.getConstant(constantMethodrefIndex);

        String className = methodref.getClassName();
        String methodName = methodref.getMethodName();
        String methodSignature = methodref.getMethodSignature();

        JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
        if (javaClass == null) {
            JVMClassLoader classLoader = frame.getJavaClass().getJVMClassLoader();
            javaClass = classLoader.loadWithClassPath(className);
        }

        if (className.startsWith("java/")) {
            // 执行 RT.jar 中 Java 对象构造方法的时候创建java对象
            if (methodName.equals("<init>")) {
                // 执行方法的参数列表
                Class<?>[] parameterTypeArr = methodref.getParameterTypeArr();
                // 执行方法的参数值
                Object[] args = frame.popStacksValue(parameterTypeArr.length);
                // 将特定的参数转换为基本类型
                for (int i = 0; i < parameterTypeArr.length; i++) {
                    Class<?> clazz = parameterTypeArr[i];
                    if (clazz.getName().equals("boolean")) { // boolean 存储方式为 int 类型
                        int booleanFlag = (int) args[i];
                        args[i] = booleanFlag == 1;
                    } else if (clazz.getName().equals("char")) { // char 存储方式为
                        int charInt = (int) args[i];
                        char c = (char) charInt;
                        args[i] = c;
                    }
                }
                Object javaObj = Class.forName(Utility.compactClassName(className,false)).getDeclaredConstructor(parameterTypeArr).newInstance(args);
                frame.push(new StackValue(Const.T_OBJECT, javaObj));    // NEW
                frame.push(new StackValue(Const.T_OBJECT, javaObj));    // DUP
                return;
            }
        }

        for (JavaMethod method : javaClass.getMethods()) {
            if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                JavaExecutionEngine.callMethod(frame, method);
                break;
            }
        }
    }


}
