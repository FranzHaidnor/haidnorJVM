package haidnor.jvm.rtda.metaspace;

import haidnor.jvm.rtda.heap.Klass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元空间
 */
public class Metaspace {

    private static final Map<String, Klass> javaClassMapMap = new ConcurrentHashMap<>();

    /**
     * 名称用.符号分割
     * 例如: haidnor.jvm.test.instruction.references.NEW
     */
    public static Klass getJavaClass(String className) {
        return javaClassMapMap.get(className);
    }

    /**
     * 注册名称用.符号分割
     * 例如: haidnor.jvm.test.instruction.references.NEW
     */
    public static void registerJavaClass(Klass javaKlass) {
        String className = javaKlass.getClassName();
        javaClassMapMap.put(className, javaKlass);
    }

}
