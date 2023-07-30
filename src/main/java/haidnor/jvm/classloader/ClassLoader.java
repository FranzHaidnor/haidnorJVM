package haidnor.jvm.classloader;

import haidnor.jvm.rtda.Klass;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author wang xiang
 */
public class ClassLoader {

    public final String name;

    public final static String JAVA_HOME;

    /**
     * java.base.jmod 文件路径
     */
    public final static String javaBaseJmodPath;

    public JarFile jarFile = null;

    static {
        JAVA_HOME = System.getenv( "JAVA_HOME");
        javaBaseJmodPath = JAVA_HOME + "/jmods/java.base.jmod";
    }

    public ClassLoader(String name) {
        this.name = name;
    }

    public ClassLoader(JarFile jarFile, String name) {
        this.name = name;
        this.jarFile = jarFile;
    }

    /**
     * @param classPath 类路径,例如 haidnor/jvm/classloader/ClassLoader
     */
    public Klass loadClass(String classPath) throws IOException {
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

        JavaClass javaClass = classParser.parse();
        return new Klass(this, javaClass);
    }

    public Klass loadClass(JarFile jarFile, JarEntry entry) throws IOException {
        InputStream inputStream = jarFile.getInputStream(entry);
        ClassParser classParser = new ClassParser(inputStream, null);
        JavaClass javaClass = classParser.parse();
        return new Klass(this, javaClass);
    }

    public Klass loadClassWithAbsolutePath(String absolutePath) throws IOException {
        ClassParser classParser = new ClassParser(absolutePath);
        JavaClass javaClass = classParser.parse();
        return new Klass(this, javaClass);
    }

}