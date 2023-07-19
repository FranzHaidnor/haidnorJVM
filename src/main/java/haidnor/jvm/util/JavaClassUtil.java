package haidnor.jvm.util;

import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.heap.KlassMethod;
import org.apache.bcel.classfile.JavaClass;

public abstract class JavaClassUtil {

    /**
     * 获取 main 方法
     */
    public static KlassMethod getMainMethod(Klass aKlass) {
        JavaClass javaClass = aKlass.getJavaClass();
        for (org.apache.bcel.classfile.Method method : javaClass.getMethods()) {
            if (method.toString().startsWith("public static void main(String[] args)")) {
                return new KlassMethod(aKlass, method);
            }
        }
        return null;
    }

}
