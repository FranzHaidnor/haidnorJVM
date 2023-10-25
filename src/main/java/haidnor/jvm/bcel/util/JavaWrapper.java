/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package haidnor.jvm.bcel.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Java interpreter replacement, i.e., wrapper that uses its own ClassLoader to modify/generate classes as they're
 * requested. You can take this as a template for your own applications.
 * <p>
 * Call this wrapper with:
 * </p>
 *
 * <pre>
 * java org.apache.bcel.util.JavaWrapper &lt;real.class.name&gt; [arguments]
 * </pre>
 * <p>
 * To use your own class loader you can set the "bcel.classloader" system property.
 * </p>
 *
 * <pre>
 * java org.apache.bcel.util.JavaWrapper -Dbcel.classloader=foo.MyLoader &lt;real.class.name&gt; [arguments]
 * </pre>
 *
 * @see ClassLoader
 */
public class JavaWrapper {

    private static java.lang.ClassLoader getClassLoader() {
        final String s = System.getProperty("bcel.classloader");
        if (StringUtils.isEmpty(s)) {
            throw new IllegalStateException("The property 'bcel.classloader' must be defined");
        }
        try {
            return (java.lang.ClassLoader) Class.forName(s).newInstance();
        } catch (final Exception e) {
            throw new IllegalStateException(e.toString(), e);
        }
    }

    /**
     * Default main method used as wrapper, expects the fully qualified class name of the real class as the first argument.
     */
    public static void main(final String[] argv) throws Exception {
        /*
         * Expects class name as first argument, other arguments are by-passed.
         */
        if (argv.length == 0) {
            System.out.println("Missing class name.");
            return;
        }
        final String className = argv[0];
        final String[] newArgv = new String[argv.length - 1];
        System.arraycopy(argv, 1, newArgv, 0, newArgv.length);
        new JavaWrapper().runMain(className, newArgv);
    }

    private final java.lang.ClassLoader loader;

    public JavaWrapper() {
        this(getClassLoader());
    }

    public JavaWrapper(final java.lang.ClassLoader loader) {
        this.loader = loader;
    }

    /**
     * Runs the main method of the given class with the arguments passed in argv
     *
     * @param className the fully qualified class name
     * @param argv the arguments just as you would pass them directly
     * @throws ClassNotFoundException if {@code className} can't be found.
     */
    public void runMain(final String className, final String[] argv) throws ClassNotFoundException {
        final Class<?> cl = loader.loadClass(className);
        Method method = null;
        try {
            method = cl.getMethod("main", argv.getClass());
            /*
             * Method main is sane ?
             */
            final int m = method.getModifiers();
            final Class<?> r = method.getReturnType();
            if (!(Modifier.isPublic(m) && Modifier.isStatic(m)) || Modifier.isAbstract(m) || r != Void.TYPE) {
                throw new NoSuchMethodException();
            }
        } catch (final NoSuchMethodException no) {
            System.out.println("In class " + className + ": public static void main(String[] argv) is not defined");
            return;
        }
        try {
            method.invoke(null, (Object[]) argv);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
