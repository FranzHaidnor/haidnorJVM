package haidnor.jvm.instruction.references;

import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Klass;
import haidnor.jvm.rtda.KlassMethod;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import haidnor.jvm.util.SignatureUtil;
import lombok.SneakyThrows;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.*;

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
        ConstantPool constantPool = frame.getConstantPool();
        ConstantPoolUtil constantPoolUtil = frame.getConstantPoolUtil();

        String className;
        String methodName;
        String methodSignature;

        Constant constant = constantPool.getConstant(constantIndex);
        if (constant instanceof ConstantMethodref constantMethodref) {
            className = constantPoolUtil.constantMethodref_ClassName(constantMethodref);
            methodName = constantPoolUtil.constantMethodref_MethodName(constantMethodref);
            methodSignature = constantPoolUtil.constantMethodref_MethodSignature(constantMethodref);

        } else if (constant instanceof ConstantInterfaceMethodref interfaceMethodref) {
            className = constantPoolUtil.constantInterfaceMethodref_ClassName(interfaceMethodref);
            methodName = constantPoolUtil.constantInterfaceMethodref_MethodName(interfaceMethodref);
            methodSignature = constantPoolUtil.constantInterfaceMethodref_MethodSignature(interfaceMethodref);
        } else {
            throw new ClassCastException();
        }

        //  系统类反射 自定义类另外处理
        if (className.startsWith("java/")) {
            // 解析方法签名得到方法的返回类型
            String returnType = Utility.methodSignatureReturnType(methodSignature, false);
            java.lang.Class<?>[] parameterTypeArr = SignatureUtil.getParameterTypes(methodSignature);
            Object[] stacksValueArr = frame.popStacksValue(parameterTypeArr.length);
            for (int i = 0; i < parameterTypeArr.length; i++) {
                java.lang.Class<?> aClass = parameterTypeArr[i];
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
            Klass klass = Metaspace.getJavaClass(Utility.compactClassName(className));
            // 调用静态方法时加载类
            if (klass == null) {
                klass = frame.getMetaClass().getClassLoader().loadClass(className);
            }
            JavaClass javaClass = klass.getJavaClass();
            for (Method method : javaClass.getMethods()) {
                if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                    KlassMethod klassMethod = new KlassMethod(klass, method);
                    JavaExecutionEngine.callMethod(frame, klassMethod);
                    break;
                }
            }

        }

    }

}
