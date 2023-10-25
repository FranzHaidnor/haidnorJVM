package haidnor.jvm.test;

import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.classloader.JVMClassLoader;

import java.io.IOException;

public class BCELTest {
    public static void main(String[] args) throws IOException {
        JVMClassLoader classLoader = new JVMClassLoader("JVMClassLoader");
        JavaClass javaClass = classLoader.loadWithAbsolutePath("D:\\project_haidnor\\haidnorJVM\\target\\test-classes\\haidnor\\jvm\\Student.class");

        System.out.println(javaClass.getStaticJavaFieldMap());
    }
}
