package haidnor.jvm.test;

import haidnor.jvm.classloader.ClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.Klass;
import haidnor.jvm.rtda.KlassMethod;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.JVMThread;
import haidnor.jvm.test.demo.*;
import haidnor.jvm.test.instruction.Array;
import haidnor.jvm.test.instruction.DO_WHILE;
import haidnor.jvm.test.instruction.math.ISUB;
import haidnor.jvm.test.instruction.math.LSUB;
import haidnor.jvm.test.instruction.references.NEW;
import haidnor.jvm.util.JavaClassUtil;
import haidnor.jvm.util.JvmThreadHolder;
import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class TestJVM {

    public static void runMainClass(java.lang.Class<?> mainClass) throws IOException {
        JvmThreadHolder.set(new JVMThread());
        ClassLoader bootClassLoader = new ClassLoader("ApplicationClassLoader");
        Klass mainMeteKlass = bootClassLoader.loadClass(mainClass.getName().replace('.', '/'));
        KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
        Metaspace.registerJavaClass(mainMeteKlass);

        JavaExecutionEngine.callMainMethod(mainKlassMethod);
    }

    /**
     * hello,world
     */
    @Test
    public void test_1() throws Exception {
        runMainClass(demo_helloWorld.class);
    }

    @Test
    public void test_2() throws Exception {
        runMainClass(Demo2.class);
    }

    @Test
    public void test_3() throws Exception {
        runMainClass(Demo3.class);
    }

    @Test
    public void test_4() throws Exception {
        runMainClass(Demo4.class);
    }

    @Test
    public void test_5() throws Exception {
        runMainClass(Demo5.class);
    }

    @Test
    public void test_6() throws Exception {
        runMainClass(Demo6.class);
    }

    @Test
    public void test_7() throws Exception {
        runMainClass(Demo7.class);
    }

    @Test
    public void demo_while() throws Exception {
        runMainClass(demo_while.class);
    }

    @Test
    public void demo_doWhile() throws Exception {
        runMainClass(demo_doWhile.class);
    }

    @Test
    public void demo_for() throws Exception {
        runMainClass(demo_for.class);
    }

    @Test
    public void demo_foreach() throws Exception {
        runMainClass(demo_foreach.class);
    }

    /**
     * --add-opens java.base/java.util=ALL-UNNAMED
     */
    @Test
    public void demo_foreach_2() throws Exception {
        runMainClass(demo_foreach_2.class);
    }

    /**
     * --add-opens java.base/java.util=ALL-UNNAMED
     */
    @Test
    public void demo_foreach_3() throws Exception {
        runMainClass(demo_foreach_3.class);
    }

    @Test
    public void test_8() throws Exception {
        runMainClass(Demo8.class);
    }

    @Test(expected = ArithmeticException.class)
    public void demo_exception_1() throws Exception {
        runMainClass(demo_exception_1.class);
    }

    @Test
    public void demo_exception_2() throws Exception {
        runMainClass(demo_exception_2.class);
    }

    @Test
    public void demo_exception_3() throws Exception {
        runMainClass(demo_exception_3.class);
    }

    @Test(expected = ArithmeticException.class)
    public void demo_exception_4() throws Exception {
        runMainClass(demo_exception_4.class);
    }

    @Test
    public void demo_finally_1() throws Exception {
        runMainClass(demo_finally_1.class);
    }

    @Test(expected = ArithmeticException.class)
    public void demo_finally_2() throws Exception {
        runMainClass(demo_finally_2.class);
    }

    @Test
    public void demo_finally_3() throws Exception {
        runMainClass(demo_finally_3.class);
    }


    @Test
    public void test_NEW() throws Exception {
        runMainClass(NEW.class);
    }

    @Test
    public void test_ISUB() throws Exception {
        runMainClass(ISUB.class);
    }

    @Test
    public void test_LSUB() throws Exception {
        runMainClass(LSUB.class);
    }

    @Test
    public void test_DOWHILE() throws Exception {
        runMainClass(DO_WHILE.class);
    }

    @Test
    public void test_Array() throws Exception {
        runMainClass(Array.class);
    }

    @Test
    public void test_() throws Exception {
        String jarFilePath = "D:\\project_java\\JvmDemo\\target\\JvmDemo-1.0-SNAPSHOT.jar";

        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Manifest manifest = jarFile.getManifest();
            // 读取指定键的值
            String mainClass = manifest.getMainAttributes().getValue("Main-Class");
            System.out.println(mainClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_jar() throws Exception {
        String jarFilePath = "D:/project_java/JvmDemo/target/JvmDemo-1.0-SNAPSHOT.jar";

        JvmThreadHolder.set(new JVMThread());
        try (JarFile jarFile = new JarFile(jarFilePath)) {

            ClassLoader bootClassLoader = new ClassLoader(jarFile, "ApplicationClassLoader");

            // 找到主类 a.b.Main
            String mainClass = jarFile.getManifest().getMainAttributes().getValue("Main-Class");

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                    if (className.equals(mainClass)) {
                        Klass mainMeteKlass = bootClassLoader.loadClass(jarFile, entry);
                        KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
                        Metaspace.registerJavaClass(mainMeteKlass);
                        JavaExecutionEngine.callMainMethod(mainKlassMethod);
                        break;
                    }
                }
            }
        }
    }

}
