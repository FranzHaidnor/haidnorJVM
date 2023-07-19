package haidnor.jvm.rtda.heap;

import org.apache.bcel.classfile.Method;

/**
 * 类元信息方法
 */
public class KlassMethod {

    public final Klass aKlass;

    public final Method javaMethod;

    public KlassMethod(Klass klass, Method javaMethod) {
        this.aKlass = klass;
        this.javaMethod = javaMethod;
    }

}
