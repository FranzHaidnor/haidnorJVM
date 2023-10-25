package haidnor.jvm.rtda;


import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.bcel.classfile.JavaField;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Instance {

    @Getter
    private final JavaClass javaClass;

    private final List<JavaField> javaFieldList;

    private Instance superInstance;

    public Instance(JavaClass javaClass) {
        this.javaClass = javaClass;
        this.javaFieldList = new ArrayList<>();
    }

    public Instance(List<JavaField> javaFieldList, JavaClass javaClass) {
        this.javaFieldList = javaFieldList;
        this.javaClass = javaClass;
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
    public JavaField getField(String name, String signature) {
        // this object
        for (JavaField field : javaFieldList) {
            if (Objects.equals(field.getName(), name) && Objects.equals(field.getSignature(), signature)) {
                return field;
            }
        }
        if (this.superInstance == null) {
            return null;
        }
        // super object
        return this.superInstance.getField(name, signature);
    }


}
