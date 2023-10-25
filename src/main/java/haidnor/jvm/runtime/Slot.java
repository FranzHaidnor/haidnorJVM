package haidnor.jvm.runtime;

public class Slot {
    public Integer num;
    public Object ref;

    public Slot(int num) {
        this.num = num;
        this.ref = null;
    }

    public Slot(Object ref) {
        this.num = null;
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Slot{" + "num=" + num + ", ref=" + ref + '}';
    }
}
