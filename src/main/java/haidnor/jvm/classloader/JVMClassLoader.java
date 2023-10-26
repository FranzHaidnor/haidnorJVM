package haidnor.jvm.classloader;

import haidnor.jvm.bcel.classfile.ClassParser;
import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.bcel.classfile.JavaMethod;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.Metaspace;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JVMClassLoader {

    public final static String JAVA_HOME;
    /**
     * JDK java.base.jmod path
     */
    public final static String javaBaseJmodPath;

    static {
        JAVA_HOME = System.getenv("JAVA_HOME");
        javaBaseJmodPath = JAVA_HOME + "/jmods/java.base.jmod";
    }

    public final String name;
    public JarFile jarFile = null;

    public JVMClassLoader(String name) {
        this.name = name;
    }

    public JVMClassLoader(JarFile jarFile, String name) {
        this.name = name;
        this.jarFile = jarFile;
    }

    /**
     * @param classPath 类路径,例如 haidnor/jvm/classloader/ClassLoader
     */
    @SneakyThrows
    public JavaClass loadWithClassPath(String classPath) {
        ClassParser classParser = null;
        if (classPath.startsWith("java/")) {
            classParser = new ClassParser(javaBaseJmodPath, "classes/" + classPath + ".class");
        } else if (jarFile != null) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().substring(0, entry.getName().length() - 6);
                    if (className.equals(classPath)) {
                        InputStream inputStream = jarFile.getInputStream(entry);
                        classParser = new ClassParser(inputStream, null);
                    }
                }
            }
        } else {
            URL resource = this.getClass().getResource("/");
            String fileName = resource.getPath() + classPath + ".class";
            classParser = new ClassParser(fileName);
        }
        return load(classParser);
    }

    @SneakyThrows
    public JavaClass loadWithJar(JarFile jarFile, JarEntry entry) {
        InputStream inputStream = jarFile.getInputStream(entry);
        ClassParser classParser = new ClassParser(inputStream, null);
        return load(classParser);
    }

    public JavaClass loadWithAbsolutePath(String absolutePath) {
        ClassParser classParser = new ClassParser(absolutePath);
        return load(classParser);
    }

    @SneakyThrows
    private JavaClass load(ClassParser classParser) {
        JavaClass javaClass = classParser.parse();
        javaClass.setJVMClassLoader(this);
        register(javaClass);
        return javaClass;
    }

    @SneakyThrows
    private void register(JavaClass javaClass) {
        JavaClass superJavaClass = javaClass.getSuperClass();
        if (superJavaClass != null) {
            register(superJavaClass);
            superJavaClass.setJVMClassLoader(javaClass.getJVMClassLoader());
        }
        Metaspace.registerJavaClass(javaClass);

        if (javaClass.getClassName().startsWith("java.")) {
            return;
        }
        for (JavaMethod method : javaClass.getMethods()) {
            if (method.toString().equals("static void <clinit>()")) {
                JavaExecutionEngine.callMethod(null, method);
                break;
            }
        }
    }


}