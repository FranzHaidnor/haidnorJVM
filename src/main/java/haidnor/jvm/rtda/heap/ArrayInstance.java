package haidnor.jvm.rtda.heap;

/**
 * @author wang xiang
 */
public abstract class ArrayInstance extends Instance {

    public final int size;

    public ArrayInstance(Klass klass, int size) {
        super(klass);
        this.size = size;
    }

}
