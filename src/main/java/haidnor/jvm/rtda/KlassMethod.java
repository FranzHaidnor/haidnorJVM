package haidnor.jvm.rtda;

import org.apache.bcel.classfile.Method;

/**
 * 类元信息方法
 *
 * @author wang xiang
 */
public class KlassMethod {

    public final Klass aKlass;

    public final Method javaMethod;

    public KlassMethod(Klass klass, Method javaMethod) {
        this.aKlass = klass;
        this.javaMethod = javaMethod;
    }

}
