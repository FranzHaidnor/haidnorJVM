package haidnor.jvm.instruction.references;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.SignatureUtil;
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

        Constant constant = constantPool.getConstant(constantIndex);
        if (constant instanceof ConstantMethodref constantMethodref) {
            className = constantPool.constantMethodref_ClassName(constantMethodref);
            methodName = constantPool.constantMethodref_MethodName(constantMethodref);
            methodSignature = constantPool.constantMethodref_MethodSignature(constantMethodref);

        } else if (constant instanceof ConstantInterfaceMethodref interfaceMethodref) {
            className = constantPool.constantInterfaceMethodref_ClassName(interfaceMethodref);
            methodName = constantPool.constantInterfaceMethodref_MethodName(interfaceMethodref);
            methodSignature = constantPool.constantInterfaceMethodref_MethodSignature(interfaceMethodref);
        } else {
            throw new ClassCastException();
        }

        //  系统类反射 自定义类另外处理
        if (className.startsWith("java/")) {
            // 解析方法签名得到方法的返回类型
            String returnType = Utility.methodSignatureReturnType(methodSignature, false);
            Class<?>[] parameterTypeArr = SignatureUtil.getParameterTypeArr(methodSignature);
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
