package haidnor.jvm.rtda.heap;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JVM 中的 Java 对象实例
 */
public class Instance {

    public final Klass klass;

    public final List<KlassField> fields;

    private Instance superInstance;

    public Instance(Klass klass) {
        this.klass = klass;
        this.fields = new ArrayList<>();
    }

    public Instance(List<KlassField> fields, Klass klass) {
        this.fields = fields;
        this.klass = klass;
    }


    public void setSuperInstance(Instance superInstance) {
        this.superInstance = superInstance;
    }

    /**
     * 获取字段
     *
     * @param name      字段名称
     * @param signature 字段签名
     */
    public KlassField getField(String name, String signature) {
        // this object
        for (KlassField field : fields) {
            if (Objects.equals(field.name, name) && Objects.equals(field.descriptor, signature)) {
                return field;
            }
        }
        if (this.superInstance == null) {
            return null;
        }
        // super object
        return this.superInstance.getField(name, signature);
    }

//    /**
//     * 设置字段
//     *
//     * @param name      字段名称
//     * @param signature 字段签名
//     * @param val       值
//     */
//    public void setField(String name, String signature, UnionSlot val) {
//        JavaField field = this.getField(name, signature);
//        field.set(val);
//    }


}
