package haidnor.jvm.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public abstract class IoUtil {

    public static DataInputStream getDataInputStream(byte[] bytes) {
        ByteArrayInputStream inputStream = getByteArrayInputStream(bytes);
        return new DataInputStream(inputStream);
    }

    public static ByteArrayInputStream getByteArrayInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

}
