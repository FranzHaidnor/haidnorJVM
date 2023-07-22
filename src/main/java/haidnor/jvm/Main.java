package haidnor.jvm;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

import haidnor.jvm.classloader.ClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.heap.KlassMethod;
import haidnor.jvm.rtda.metaspace.Metaspace;
import haidnor.jvm.runtime.JvmThread;
import haidnor.jvm.util.JavaClassUtil;
import haidnor.jvm.util.JvmThreadHolder;
import lombok.SneakyThrows;

import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author wang xiang
 */
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Option jarOption = new Option("jar", true, "运行 jar 程序");
        Option classOption = new Option("class", true, "运行 .class 字节码文件");

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(jarOption).addOption(classOption);

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("jar")) {
            String jarFilePath = cmd.getOptionValue("jar");

            try (JarFile jarFile = new JarFile(jarFilePath)) {
                ClassLoader bootClassLoader = new ClassLoader(jarFile, "ApplicationClassLoader");
                String mainClass = jarFile.getManifest().getMainAttributes().getValue("Main-Class");

                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                        if (className.equals(mainClass)) {
                            JvmThreadHolder.set(new JvmThread());
                            Klass mainMeteKlass = bootClassLoader.loadClass(jarFile, entry);
                            KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
                            Metaspace.registerJavaClass(mainMeteKlass);
                            JavaExecutionEngine.callMainStaticMethod(mainKlassMethod);
                            break;
                        }
                    }
                }
            }
        } else if (cmd.hasOption("class")) {
            JvmThreadHolder.set(new JvmThread());
            String path = cmd.getOptionValue("class");
            ClassLoader bootClassLoader = new ClassLoader("ApplicationClassLoader");
            Klass mainMeteKlass = bootClassLoader.loadClassWithAbsolutePath(path);
            KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
            Metaspace.registerJavaClass(mainMeteKlass);

            JavaExecutionEngine.callMainStaticMethod(mainKlassMethod);
        }
    }

}