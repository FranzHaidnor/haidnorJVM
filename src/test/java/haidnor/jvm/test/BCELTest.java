package haidnor.jvm.test;

import haidnor.jvm.bcel.classfile.ClassParser;
import haidnor.jvm.bcel.classfile.JavaClass;

import java.io.FileInputStream;
import java.io.IOException;

public class BCELTest {
    public static void main(String[] args) throws IOException {
        ClassParser classParser = new ClassParser(new FileInputStream("D:\\project_javaXL\\xuanleOa\\project-main\\target\\classes\\com\\xuanleOa\\XuanleOaApplication.class"), null);
        JavaClass javaClass = classParser.parse();
        System.out.println(javaClass);
    }
}
