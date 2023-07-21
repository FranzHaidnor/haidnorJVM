package haidnor.jvm.rtda.heap;

import haidnor.jvm.classloader.ClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.metaspace.Metaspace;
import lombok.SneakyThrows;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类元信息
 *
 * @author wang xiang
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

    /**
     * 静态字段
     */
    private Map<String, KlassField> staticFieldMap = new HashMap<>();

    /**
     * 加载类元数据并将类放入元空间
     * <p>
     * 执行顺序如下
     * 1.优先加载父类
     * 2.为类元数据创建静态字段(此时并没有给静态字符赋值)
     * 3.执行 clinit 方法(静态方法), 为静态字段赋值,或执行自定义逻辑.
     */
    @SneakyThrows
    public Klass(ClassLoader classLoader, JavaClass javaClass) {
        this.javaClass = javaClass;
        this.classLoader = classLoader;
        this.className = javaClass.getClassName();
        this.superClassName = javaClass.getSuperclassName();

        // loader super class
        JavaClass superJavaClass = javaClass.getSuperClass();
        if (superJavaClass != null) {
            this.superKlass = new Klass(classLoader, superJavaClass);
        }

        Metaspace.registerJavaClass(this);

        // init staticFieldMap
        for (Field field : javaClass.getFields()) {
            if (field.isStatic()) {
                staticFieldMap.put(field.getName(), new KlassField(field));
            }
        }

        // execute <clinit> method
        if (!javaClass.getClassName().startsWith("java.")) {
            for (Method method : javaClass.getMethods()) {
                if (method.toString().equals("static void <clinit>()")) {
                    KlassMethod klassMethod = new KlassMethod(this, method);
                    JavaExecutionEngine.callMethod(null, klassMethod);
                    break;
                }
            }
        }
    }

    public KlassField getStaticField(String filedName) {
        return staticFieldMap.get(filedName);
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
