package haidnor.jvm.rtda;

import haidnor.jvm.bcel.classfile.JavaClass;

public abstract class ArrayInstance extends Instance {

    public final int size;

    public ArrayInstance(JavaClass javaClass, int size) {
        super(javaClass);
        this.size = size;
    }

}
