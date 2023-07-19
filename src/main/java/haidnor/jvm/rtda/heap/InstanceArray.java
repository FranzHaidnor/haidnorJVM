package haidnor.jvm.rtda.heap;

public class InstanceArray extends ArrayInstance {

    public final Instance[] items;

    public InstanceArray(Klass klass, Instance[] items) {
        super(klass, items.length);
        this.items = items;
    }

}