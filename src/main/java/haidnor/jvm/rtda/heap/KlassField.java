package haidnor.jvm.rtda.heap;

import haidnor.jvm.runtime.StackValue;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.Field;

/**
 * 类元信息字段
 */
public class KlassField {

    public final int accessFlags;

    public final String name;

    public final String descriptor;

    /**
     * 值类型
     */
    private int type;
    /**
     * 值
     */
    private Object value;


    public KlassField(Field field) {
        this.accessFlags = field.getAccessFlags();
        this.name = field.getName();
        this.descriptor = field.getSignature();
    }

    public boolean isStatic() {
        return (accessFlags & Const.ACC_STATIC) != 0;
    }

    public void setVal(StackValue stackValue) {
        this.type = stackValue.getType();
        this.value = stackValue.getValue();
    }

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
