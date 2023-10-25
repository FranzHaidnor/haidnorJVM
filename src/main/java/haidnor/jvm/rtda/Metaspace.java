package haidnor.jvm.rtda;

import haidnor.jvm.bcel.classfile.JavaClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Metaspace {

    private static final Map<String, JavaClass> javaClassMap = new ConcurrentHashMap<>();

    /**
     * 名称用.符号分割
     * 例如: haidnor.jvm.test.instruction.references.NEW
     */
    public static JavaClass getJavaClass(String className) {
        return javaClassMap.get(className);
    }

    /**
     * 注册名称用.符号分割
     * 例如: haidnor.jvm.test.instruction.references.NEW
     */
    public static void registerJavaClass(JavaClass javaClass) {
        String className = javaClass.getClassName();
        javaClassMap.put(className, javaClass);
    }

}
