package haidnor.jvm.rtda.heap;

import haidnor.jvm.classloader.ClassLoader;
import lombok.SneakyThrows;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 类元信息
 */
public class Klass {

    /**
     * BCEL JavaClass
     */
    private final JavaClass javaClass;

    /**
     * 加载此 Class 的类加载器
     */
    private final ClassLoader classLoader;

    /**
     * 此 Class 名称. 例如 java.util.ArrayList
     */
    private final String className;

    public final String superClassName;

    private Klass superKlass;

    @SneakyThrows
    public Klass(ClassLoader classLoader, JavaClass javaClass) {
        this.javaClass = javaClass;
        this.classLoader = classLoader;
        this.className = javaClass.getClassName();
        this.superClassName = javaClass.getSuperclassName();

        JavaClass superJavaClass = javaClass.getSuperClass();
        if (superJavaClass != null) {
            this.superKlass = new Klass(classLoader, superJavaClass);
        }
    }

    public Instance newInstance() {
        // 创建对象存放字段的内存空间
        List<KlassField> klassFieldList = new ArrayList<>();
        for (Field field : javaClass.getFields()) {
            KlassField klassField = new KlassField(field);
            klassFieldList.add(klassField);
        }
        // 创建 JVM 中的对象实例
        Instance obj = new Instance(klassFieldList, this);
        // 加载父类
        if (this.superKlass != null) {
            obj.setSuperInstance(this.superKlass.newInstance());
        }
        return obj;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getClassName() {
        return className;
    }

}
