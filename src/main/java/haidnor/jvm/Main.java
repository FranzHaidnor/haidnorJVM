package haidnor.jvm;

import haidnor.jvm.classloader.ClassLoader;
import haidnor.jvm.core.JavaExecutionEngine;
import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.heap.KlassMethod;
import haidnor.jvm.rtda.metaspace.Metaspace;
import haidnor.jvm.runtime.JvmThread;
import haidnor.jvm.util.JavaClassUtil;
import haidnor.jvm.util.JvmThreadHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

@Slf4j
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
            String path = cmd.getOptionValue("jar");
            // TODO
        }
        if (cmd.hasOption("class")) {
            String path = cmd.getOptionValue("class");
            ClassLoader bootClassLoader = new ClassLoader("ApplicationClassLoader");
            Klass mainMeteKlass = bootClassLoader.loadClassWithAbsolutePath(path);
            KlassMethod mainKlassMethod = JavaClassUtil.getMainMethod(mainMeteKlass);
            Metaspace.registerJavaClass(mainMeteKlass);
            JvmThreadHolder.set(new JvmThread());

            JavaExecutionEngine.callMainStaticMethod(mainKlassMethod);
        }
    }

}