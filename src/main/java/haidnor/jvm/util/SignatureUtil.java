package haidnor.jvm.util;

import lombok.SneakyThrows;
import org.apache.bcel.classfile.Utility;

public abstract class SignatureUtil {

    /**
     * 解析方法签名返回方法参数类型数组
     */
    @SneakyThrows
    public static Class<?>[] getParameterTypes(String methodeSignature) {
        String[] argumentTypeArr = Utility.methodSignatureArgumentTypes(methodeSignature, false);
        Class<?>[] argumentClassArr = new Class[argumentTypeArr.length];
        for (int i = 0; i < argumentTypeArr.length; i++) {
            Class<?> argumentClass;
            String argumentType = argumentTypeArr[i];
            switch (argumentType) {
                case "byte":
                    argumentClass = byte.class;
                    break;
                case "short":
                    argumentClass = short.class;
                    break;
                case "boolean":
                    argumentClass = boolean.class;
                    break;
                case "char":
                    argumentClass = char.class;
                    break;
                case "int":
                    argumentClass = int.class;
                    break;
                case "long":
                    argumentClass = long.class;
                    break;
                case "float":
                    argumentClass = float.class;
                    break;
                case "double":
                    argumentClass = double.class;
                    break;
                default:
                    argumentClass = Class.forName(argumentType);
            }
            argumentClassArr[i] = argumentClass;
        }
        return argumentClassArr;
    }

}
