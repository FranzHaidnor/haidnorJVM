package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

import java.util.Objects;

/**
 * 调用 static 静态方法.这是静态绑定的
 */
public class INVOKESTATIC extends Instruction {

    private final int constantIndex;

    public INVOKESTATIC(CodeStream codeStream) {
        super(codeStream);
        this.constantIndex = codeStream.readUnsignedShort(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getJavaMethod().getConstantPool();

        String className;
        String methodName;
        String methodSignature;
        String returnType;
        Class<?>[] parameterTypeArr;

        Constant constant = constantPool.getConstant(constantIndex);
        if (constant instanceof ConstantMethodref methodref) {
            className = methodref.getClassName();
            methodName = methodref.getMethodName();
            methodSignature = methodref.getMethodSignature();
            returnType = methodref.getReturnType();
            parameterTypeArr = methodref.getParameterTypeArr();
        } else if (constant instanceof ConstantInterfaceMethodref methodref) {
            className = methodref.getClassName();
            methodName = methodref.getMethodName();
            methodSignature = methodref.getMethodSignature();
            returnType = methodref.getReturnType();
            parameterTypeArr = methodref.getParameterTypeArr();
        } else {
            throw new ClassCastException();
        }

        //  系统类反射 自定义类另外处理
        if (className.startsWith("java/")) {
            Object[] stacksValueArr = frame.popStacksValue(parameterTypeArr.length);
            for (int i = 0; i < parameterTypeArr.length; i++) {
                Class<?> aClass = parameterTypeArr[i];
                if (aClass.getName().equals("boolean")) {
                    int booleanFlag = (int) stacksValueArr[i];
                    stacksValueArr[i] = booleanFlag == 1;
                }
            }

            Class<?> javaClass = Class.forName(Utility.compactClassName(className, false));
            java.lang.reflect.Method method = javaClass.getMethod(methodName, parameterTypeArr);
            method.setAccessible(true);
            Object value = method.invoke(null, stacksValueArr);
            if (!Objects.equals(Const.getTypeName(Const.T_VOID), returnType)) {     // void 调用的方法无返回值
                frame.push(new StackValue(Const.T_OBJECT, value));
            }
        }
        // 自定义类的方法
        else {
            JavaClass javaClass = Metaspace.getJavaClass(Utility.compactClassName(className));
            // 调用静态方法时加载类
            if (javaClass == null) {
                javaClass = frame.getJavaClass().getJVMClassLoader().loadWithClassPath(className);
            }
            for (JavaMethod method : javaClass.getMethods()) {
                if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                    JavaExecutionEngine.callMethod(frame, method);
                    break;
                }
            }

        }

    }

}
