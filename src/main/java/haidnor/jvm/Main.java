package haidnor.jvm;

import org.apache.commons.cli.*;

import haidnor.jvm.classloader.ClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.Klass;
import haidnor.jvm.rtda.KlassMethod;
import haidnor.jvm.rtda.Metaspace;
import haidnor.jvm.runtime.JVMThread;
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
        String banner = """
                ░░   ░░  ░░░░░  ░░ ░░░░░░  ░░░    ░░  ░░░░░░  ░░░░░░           ░░ ░░    ░░ ░░░    ░░░\s
                ▒▒   ▒▒ ▒▒   ▒▒ ▒▒ ▒▒   ▒▒ ▒▒▒▒   ▒▒ ▒▒    ▒▒ ▒▒   ▒▒          ▒▒ ▒▒    ▒▒ ▒▒▒▒  ▒▒▒▒\s
                ▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒ ▒▒ ▒▒   ▒▒ ▒▒ ▒▒  ▒▒ ▒▒    ▒▒ ▒▒▒▒▒▒           ▒▒ ▒▒    ▒▒ ▒▒ ▒▒▒▒ ▒▒\s
                ▓▓   ▓▓ ▓▓   ▓▓ ▓▓ ▓▓   ▓▓ ▓▓  ▓▓ ▓▓ ▓▓    ▓▓ ▓▓   ▓▓     ▓▓   ▓▓  ▓▓  ▓▓  ▓▓  ▓▓  ▓▓\s
                ██   ██ ██   ██ ██ ██████  ██   ████  ██████  ██   ██      █████    ████   ██      ██\s
                """;

        System.out.println(banner);

        CommandLine cmd = initCommandLine(args);

        // 指定从 .jar 文件运行
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
                            JvmThreadHolder.set(new JVMThread());
                            Klass mainMeteKlass = bootClassLoader.loadClass(jarFile, entry);
                            KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
                            Metaspace.registerJavaClass(mainMeteKlass);
                            JavaExecutionEngine.callMainMethod(mainKlassMethod);
                            return;
                        }
                    }
                }
            }
        }

        // 指定从 .class 文件运行
        if (cmd.hasOption("class")) {
            JvmThreadHolder.set(new JVMThread());
            String path = cmd.getOptionValue("class");
            ClassLoader bootClassLoader = new ClassLoader("ApplicationClassLoader");
            Klass mainMeteKlass = bootClassLoader.loadClassWithAbsolutePath(path);
            KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
            Metaspace.registerJavaClass(mainMeteKlass);

            JavaExecutionEngine.callMainMethod(mainKlassMethod);
        }
    }

    private static  CommandLine initCommandLine(String[] args) throws ParseException {
        Option jarOption = new Option("jar", true, "运行 jar 程序");
        Option classOption = new Option("class", true, "运行 .class 字节码文件");

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(jarOption).addOption(classOption);

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

}