package haidnor.jvm.rtda;

public class BasicTypeArray extends ArrayInstance {

    public int[] ints;
    public long[] longs;
    public float[] floats;
    public double[] doubles;

    private BasicTypeArray(int size) {
        super(null, size);
    }

    public static BasicTypeArray charArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.ints = new int[size];
        return array;
    }

    public static BasicTypeArray boolArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.ints = new int[size];
        return array;
    }

    public static BasicTypeArray byteArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.ints = new int[size];
        return array;
    }

    public static BasicTypeArray shortArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.ints = new int[size];
        return array;
    }

    public static BasicTypeArray intArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.ints = new int[size];
        return array;
    }

    public static BasicTypeArray longArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.longs = new long[size];
        return array;
    }

    public static BasicTypeArray floatArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.floats = new float[size];
        return array;
    }

    public static BasicTypeArray doubleArray(int size) {
        final BasicTypeArray array = new BasicTypeArray(size);
        array.doubles = new double[size];
        return array;
    }

}