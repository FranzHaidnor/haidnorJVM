package haidnor.jvm;

import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.classloader.JVMClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.JVMThread;
import haidnor.jvm.core.JVMThreadHolder;
import lombok.SneakyThrows;
import org.apache.commons.cli.*;

import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <a href="https://github.com/FranzHaidnor">GitHub FranzHaidnor</a>
 */
public class HaidnorJVM {

    @SneakyThrows
    public static void main(String[] args) {
        String banner = """
                    ██╗  ██╗ █████╗ ██╗██████╗ ███╗   ██╗ ██████╗ ██████╗          ██╗██╗   ██╗███╗   ███╗
                    ██║  ██║██╔══██╗██║██╔══██╗████╗  ██║██╔═══██╗██╔══██╗         ██║██║   ██║████╗ ████║
                    ███████║███████║██║██║  ██║██╔██╗ ██║██║   ██║██████╔╝         ██║██║   ██║██╔████╔██║
                    ██╔══██║██╔══██║██║██║  ██║██║╚██╗██║██║   ██║██╔══██╗    ██   ██║╚██╗ ██╔╝██║╚██╔╝██║
                    ██║  ██║██║  ██║██║██████╔╝██║ ╚████║╚██████╔╝██║  ██║    ╚█████╔╝ ╚████╔╝ ██║ ╚═╝ ██║
                    ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝     ╚════╝   ╚═══╝  ╚═╝     ╚═╝
                """;
        System.out.println(banner);

        CommandLine cmd = initCommandLine(args);

        // 指定从 .jar 文件运行
        if (cmd.hasOption("jar")) {
            String jarFilePath = cmd.getOptionValue("jar");
            try (JarFile jarFile = new JarFile(jarFilePath)) {
                JVMClassLoader classLoader = new JVMClassLoader(jarFile, "ApplicationClassLoader");
                String mainClass = jarFile.getManifest().getMainAttributes().getValue("Main-Class");

                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                        if (className.equals(mainClass)) {
                            JVMThreadHolder.set(new JVMThread());
                            JavaClass javaClass = classLoader.loadWithJar(jarFile, entry);
                            Metaspace.registerJavaClass(javaClass);
                            JavaExecutionEngine.callMain(javaClass);
                            return;
                        }
                    }
                }
            }
        }

        // 指定从 .class 文件运行
        if (cmd.hasOption("class")) {
            JVMThreadHolder.set(new JVMThread());
            String path = cmd.getOptionValue("class");
            JVMClassLoader bootClassLoader = new JVMClassLoader("ApplicationClassLoader");
            JavaClass javaClass = bootClassLoader.loadWithAbsolutePath(path);
            JavaExecutionEngine.callMain(javaClass);
        }
    }

    private static CommandLine initCommandLine(String[] args) throws ParseException {
        Option jarOption = new Option("jar", true, "运行 jar 程序");
        Option classOption = new Option("class", true, "运行 .class 字节码文件");

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(jarOption).addOption(classOption);

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }


    public static void testRun(Class<?> mainClass) {
        JVMThreadHolder.set(new JVMThread());
        JVMClassLoader bootClassLoader = new JVMClassLoader("ApplicationClassLoader");
        JavaClass javaClass = bootClassLoader.loadWithClassPath(mainClass.getName().replace('.', '/'));
        JavaExecutionEngine.callMain(javaClass);
    }

}