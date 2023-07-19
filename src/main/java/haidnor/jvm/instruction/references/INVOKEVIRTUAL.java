package haidnor.jvm.instruction.references;

import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.heap.KlassMethod;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import haidnor.jvm.util.SignatureUtil;
import lombok.SneakyThrows;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;

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
        ConstantPool constantPool = frame.getConstantPool();
        ConstantPoolUtil constantPoolUtil = frame.getConstantPoolUtil();

        ConstantMethodref methodref = constantPool.getConstant(constantMethodrefIndex);

        String className = constantPoolUtil.getBelongClassName(methodref);
        String methodName = constantPoolUtil.getMethodName(methodref);
        String methodSignature = constantPoolUtil.getMethodSignature(methodref);

        //  系统类反射 自定义类另外处理
        if (className.startsWith("java/")) {
            // 解析方法签名得到方法的返回类型
            String returnType = Utility.methodSignatureReturnType(methodSignature, false);
            // 执行方法的参数列表
            Class<?>[] parameterTypeArr = SignatureUtil.getParameterTypes(methodSignature);
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
            if (!Objects.equals(Const.getTypeName(Const.T_VOID), returnType)) {     // void 调用的方法无返回值
                frame.push(new StackValue(Const.T_OBJECT, value));
            }
        }
        // 调用自定义类的方法
        else {
            // 获得栈顶对象实例,根据对象的实际类型进行分派(虚方法分派),支持多态
            StackValue stackValue = frame.peek();
            Instance instance = (Instance) stackValue.getValue();
            Klass meteKlass = instance.klass;
            JavaClass javaClass = meteKlass.getJavaClass();
            for (org.apache.bcel.classfile.Method method : javaClass.getMethods()) {
                if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                    KlassMethod klassMethod = new KlassMethod(meteKlass, method);
                    JavaExecutionEngine.callMethod(frame, klassMethod);
                    break;
                }
            }
        }
    }


}
