package haidnor.jvm.util;

import lombok.SneakyThrows;
import org.apache.bcel.classfile.Utility;

/**
 * @author wang xiang
 */
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
            argumentClass = switch (argumentType) {
                case "byte" -> byte.class;
                case "short" -> short.class;
                case "boolean" -> boolean.class;
                case "char" -> char.class;
                case "int" -> int.class;
                case "long" -> long.class;
                case "float" -> float.class;
                case "double" -> double.class;
                default -> Class.forName(argumentType);
            };
            argumentClassArr[i] = argumentClass;
        }
        return argumentClassArr;
    }

}
