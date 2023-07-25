package haidnor.jvm.instruction.references;

import haidnor.jvm.classloader.ClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.rtda.Instance;
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
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 调用对象实例方法,根据对象的实际类型进行分派(虚方法分派),支持多态
 */
public class INVOKEINTERFACE extends Instruction {

    private final int constantMethodrefIndex;

    public final int count;

    public final int zero;

    public INVOKEINTERFACE(CodeStream codeStream) {
        super(codeStream);
        this.constantMethodrefIndex = codeStream.readUnsignedShort(this);
        this.count = codeStream.readUnsignedByte(this);
        this.zero = codeStream.readUnsignedByte(this);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getConstantPool();
        ConstantPoolUtil constantPoolUtil = frame.getConstantPoolUtil();

        ConstantInterfaceMethodref interfaceMethodref = constantPool.getConstant(constantMethodrefIndex);

        int classIndex = interfaceMethodref.getClassIndex();
        String interfaceName = constantPoolUtil.getConstantClassClassName(classIndex);

        int nameAndTypeIndex = interfaceMethodref.getNameAndTypeIndex();
        String methodName = constantPoolUtil.constantNameAndType_name(nameAndTypeIndex);
        String methodSignature = constantPoolUtil.constantNameAndType_signature(nameAndTypeIndex);

        //  系统类反射 自定义类另外处理
        if (interfaceName.startsWith("java/")) {
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
            Klass interfaceKlass = Metaspace.getJavaClass(Utility.compactClassName(interfaceName));
            if (interfaceKlass == null) {
                ClassLoader classLoader = frame.getMetaClass().getClassLoader();
                classLoader.loadClass(interfaceName);
            }

            StackValue stackValue = frame.peek();
            Instance instance = (Instance) stackValue.getValue();
            Klass klass = instance.klass;

            // 按照继承关系从下往上找实现的方法 (实现多态)
            org.apache.bcel.classfile.Method method = getMethod(klass.getJavaClass(), methodSignature, methodName);
            // 从接口找方法 JDK8. interface default()
            if (method == null) {
                method = getMethodFromInterface(klass.getJavaClass(), methodSignature, methodName);
            }
            if (method == null) {
                throw new AbstractMethodError();
            }
            KlassMethod klassMethod = new KlassMethod(klass, method);
            JavaExecutionEngine.callMethod(frame, klassMethod);
        }
    }

    /**
     * 递归查找方法, 如果子类没有实现方法则向父类查找
     */
    private static org.apache.bcel.classfile.Method getMethod(JavaClass javaClass, String methodSignature, String methodName) throws ClassNotFoundException {
        org.apache.bcel.classfile.Method m = null;
        for (org.apache.bcel.classfile.Method method : javaClass.getMethods()) {
            if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                m = method;
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

    private static org.apache.bcel.classfile.Method getMethodFromInterface(JavaClass javaClass, String methodSignature, String methodName) throws ClassNotFoundException {
        JavaClass[] interfaces = javaClass.getInterfaces();
        for (JavaClass anInterface : interfaces) {
            for (org.apache.bcel.classfile.Method method : anInterface.getMethods()) {
                if (method.getSignature().equals(methodSignature) && method.getName().equals(methodName)) {
                    return method;
                }
            }
        }
        return null;
    }

}
