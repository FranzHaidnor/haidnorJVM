package haidnor.jvm.rtda.heap;

public abstract class ArrayInstance extends Instance {

    public final int size;

    public ArrayInstance(Klass klass, int size) {
        super(klass);
        this.size = size;
    }

}
