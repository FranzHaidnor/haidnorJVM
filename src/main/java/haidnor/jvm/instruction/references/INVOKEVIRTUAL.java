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

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 调用对象实例方法,根据对象的实际类型进行分派(虚方法分派),支持多态
 */
public class INVOKEVIRTUAL extends Instruction {

    private final int constantMethodrefIndex;

    public INVOKEVIRTUAL(CodeStream codeStream) {
        super(codeStream);
        this.constantMethodrefIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();
        ConstantMethodref methodRef = constantPool.getConstant(constantMethodrefIndex);
        String className = methodRef.getClassName();
        String methodName = methodRef.getMethodName();
        String methodSignature = methodRef.getMethodSignature();

        //  系统类反射 自定义类另外处理
        if (className.startsWith("java/")) {

            // 执行方法的参数列表
            Class<?>[] parameterTypeArr = methodRef.getParameterTypeArr();
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

            StackValue stackValue = frame.pop();
            Object obj = stackValue.getValue();
            Method method = obj.getClass().getMethod(methodName, parameterTypeArr);
            method.setAccessible(true);
            Object value = method.invoke(obj, args);

            // 解析方法签名得到方法的返回类型
            String returnType = methodRef.getReturnType();
            // void 调用的方法无返回值
            if (!Objects.equals(Const.getTypeName(Const.T_VOID), returnType)) {
                frame.push(new StackValue(Const.T_OBJECT, value));
            }
        }
        // 调用自定义类的方法
        else {
            JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
            if (javaClass == null) {
                JVMClassLoader classLoader = frame.getJavaClass().getJVMClassLoader();
                javaClass = classLoader.loadWithClassPath(className);
            }

            // 按照继承关系从下往上找实现的方法 (实现多态)
            JavaMethod method = getMethod(javaClass, methodSignature, methodName);
            if (method == null) {
                throw new AbstractMethodError();
            }
            JavaExecutionEngine.callMethod(frame, method);
        }
    }

    /**
     * 递归查找方法, 如果子类没有实现方法则向父类查找
     */
    @SneakyThrows
    private static JavaMethod getMethod(JavaClass javaClass, String methodSignature, String methodName) {
        JavaMethod m = null;
        for (JavaMethod method : javaClass.getMethods()) {
            if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                m =  method;
            }
        }
        if (m != null) {
            return m;
        }
        if (javaClass.getSuperClass() == null) {
            return null;
        }
        return getMethod(javaClass.getSuperClass(), methodSignature, methodName);
    }

}
