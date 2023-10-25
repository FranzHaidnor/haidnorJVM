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

import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.bcel.classfile.Utility;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Loads class files from the CLASSPATH. Inspired by sun.tools.ClassPath.
 */
public class ClassPath implements Closeable {

    private abstract static class AbstractPathEntry implements Closeable {

        abstract ClassFile getClassFile(String name, String suffix);

        abstract URL getResource(String name);

        abstract InputStream getResourceAsStream(String name);
    }

    private abstract static class AbstractZip extends AbstractPathEntry {

        private final ZipFile zipFile;

        AbstractZip(final ZipFile zipFile) {
            this.zipFile = Objects.requireNonNull(zipFile, "zipFile");
        }

        @Override
        public void close() throws IOException {
            if (zipFile != null) {
                zipFile.close();
            }

        }

        @Override
        ClassFile getClassFile(final String name, final String suffix) {
            final ZipEntry entry = zipFile.getEntry(toEntryName(name, suffix));

            if (entry == null) {
                return null;
            }

            return new ClassFile() {

                @Override
                public String getBase() {
                    return zipFile.getName();
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return zipFile.getInputStream(entry);
                }

                @Override
                public String getPath() {
                    return entry.toString();
                }

                @Override
                public long getSize() {
                    return entry.getSize();
                }

                @Override
                public long getTime() {
                    return entry.getTime();
                }
            };
        }

        @Override
        URL getResource(final String name) {
            final ZipEntry entry = zipFile.getEntry(name);
            try {
                return entry != null ? new URL("jar:file:" + zipFile.getName() + "!/" + name) : null;
            } catch (final MalformedURLException e) {
                return null;
            }
        }

        @Override
        InputStream getResourceAsStream(final String name) {
            final ZipEntry entry = zipFile.getEntry(name);
            try {
                return entry != null ? zipFile.getInputStream(entry) : null;
            } catch (final IOException e) {
                return null;
            }
        }

        protected abstract String toEntryName(final String name, final String suffix);

        @Override
        public String toString() {
            return zipFile.getName();
        }

    }

    /**
     * Contains information about file/ZIP entry of the Java class.
     */
    public interface ClassFile {

        /**
         * @return base path of found class, i.e. class is contained relative to that path, which may either denote a directory,
         *         or zip file
         */
        String getBase();

        /**
         * @return input stream for class file.
         * @throws IOException if an I/O error occurs.
         */
        InputStream getInputStream() throws IOException;

        /**
         * @return canonical path to class file.
         */
        String getPath();

        /**
         * @return size of class file.
         */
        long getSize();

        /**
         * @return modification time of class file.
         */
        long getTime();
    }

    private static class Dir extends AbstractPathEntry {

        private final String dir;

        Dir(final String d) {
            dir = d;
        }

        @Override
        public void close() throws IOException {
            // Nothing to do

        }

        @Override
        ClassFile getClassFile(final String name, final String suffix) {
            final File file = new File(dir + File.separatorChar + name.replace('.', File.separatorChar) + suffix);
            return file.exists() ? new ClassFile() {

                @Override
                public String getBase() {
                    return dir;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return new FileInputStream(file);
                }

                @Override
                public String getPath() {
                    try {
                        return file.getCanonicalPath();
                    } catch (final IOException e) {
                        return null;
                    }
                }

                @Override
                public long getSize() {
                    return file.length();
                }

                @Override
                public long getTime() {
                    return file.lastModified();
                }
            } : null;
        }

        @Override
        URL getResource(final String name) {
            // Resource specification uses '/' whatever the platform
            final File file = toFile(name);
            try {
                return file.exists() ? file.toURI().toURL() : null;
            } catch (final MalformedURLException e) {
                return null;
            }
        }

        @Override
        InputStream getResourceAsStream(final String name) {
            // Resource specification uses '/' whatever the platform
            final File file = toFile(name);
            try {
                return file.exists() ? new FileInputStream(file) : null;
            } catch (final IOException e) {
                return null;
            }
        }

        private File toFile(final String name) {
            return new File(dir + File.separatorChar + name.replace('/', File.separatorChar));
        }

        @Override
        public String toString() {
            return dir;
        }
    }

    private static class Jar extends AbstractZip {

        Jar(final ZipFile zip) {
            super(zip);
        }

        @Override
        protected String toEntryName(final String name, final String suffix) {
            return Utility.packageToPath(name) + suffix;
        }

    }

    private static class JrtModule extends AbstractPathEntry {

        private final Path modulePath;

        public JrtModule(final Path modulePath) {
            this.modulePath = Objects.requireNonNull(modulePath, "modulePath");
        }

        @Override
        public void close() throws IOException {
            // Nothing to do.

        }

        @Override
        ClassFile getClassFile(final String name, final String suffix) {
            final Path resolved = modulePath.resolve(Utility.packageToPath(name) + suffix);
            if (Files.exists(resolved)) {
                return new ClassFile() {

                    @Override
                    public String getBase() {
                        return Objects.toString(resolved.getFileName(), null);
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return Files.newInputStream(resolved);
                    }

                    @Override
                    public String getPath() {
                        return resolved.toString();
                    }

                    @Override
                    public long getSize() {
                        try {
                            return Files.size(resolved);
                        } catch (final IOException e) {
                            return 0;
                        }
                    }

                    @Override
                    public long getTime() {
                        try {
                            return Files.getLastModifiedTime(resolved).toMillis();
                        } catch (final IOException e) {
                            return 0;
                        }
                    }
                };
            }
            return null;
        }

        @Override
        URL getResource(final String name) {
            final Path resovled = modulePath.resolve(name);
            try {
                return Files.exists(resovled) ? new URL("jrt:" + modulePath + "/" + name) : null;
            } catch (final MalformedURLException e) {
                return null;
            }
        }

        @Override
        InputStream getResourceAsStream(final String name) {
            try {
                return Files.newInputStream(modulePath.resolve(name));
            } catch (final IOException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return modulePath.toString();
        }

    }

    private static class JrtModules extends AbstractPathEntry {

        private final ModularRuntimeImage modularRuntimeImage;
        private final JrtModule[] modules;

        public JrtModules(final String path) throws IOException {
            this.modularRuntimeImage = new ModularRuntimeImage();
            this.modules = modularRuntimeImage.list(path).stream().map(JrtModule::new).toArray(JrtModule[]::new);
        }

        @Override
        public void close() throws IOException {
            if (modules != null) {
                // don't use a for each loop to avoid creating an iterator for the GC to collect.
                for (final JrtModule module : modules) {
                    module.close();
                }
            }
            if (modularRuntimeImage != null) {
                modularRuntimeImage.close();
            }
        }

        @Override
        ClassFile getClassFile(final String name, final String suffix) {
            // don't use a for each loop to avoid creating an iterator for the GC to collect.
            for (final JrtModule module : modules) {
                final ClassFile classFile = module.getClassFile(name, suffix);
                if (classFile != null) {
                    return classFile;
                }
            }
            return null;
        }

        @Override
        URL getResource(final String name) {
            // don't use a for each loop to avoid creating an iterator for the GC to collect.
            for (final JrtModule module : modules) {
                final URL url = module.getResource(name);
                if (url != null) {
                    return url;
                }
            }
            return null;
        }

        @Override
        InputStream getResourceAsStream(final String name) {
            // don't use a for each loop to avoid creating an iterator for the GC to collect.
            for (final JrtModule module : modules) {
                final InputStream inputStream = module.getResourceAsStream(name);
                if (inputStream != null) {
                    return inputStream;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return Arrays.toString(modules);
        }

    }

    private static class Module extends AbstractZip {

        Module(final ZipFile zip) {
            super(zip);
        }

        @Override
        protected String toEntryName(final String name, final String suffix) {
            return "classes/" + Utility.packageToPath(name) + suffix;
        }

    }

    private static final FilenameFilter ARCHIVE_FILTER = (dir, name) -> {
        name = name.toLowerCase(Locale.ENGLISH);
        return name.endsWith(".zip") || name.endsWith(".jar");
    };

    private static final FilenameFilter MODULES_FILTER = (dir, name) -> {
        name = name.toLowerCase(Locale.ENGLISH);
        return name.endsWith(haidnor.jvm.bcel.classfile.Module.EXTENSION);
    };

    public static final ClassPath SYSTEM_CLASS_PATH = new ClassPath(getClassPath());

    private static void addJdkModules(final String javaHome, final List<String> list) {
        String modulesPath = System.getProperty("java.modules.path");
        if (modulesPath == null || modulesPath.trim().isEmpty()) {
            // Default to looking in JAVA_HOME/jmods
            modulesPath = javaHome + File.separator + "jmods";
        }
        final File modulesDir = new File(modulesPath);
        if (modulesDir.exists()) {
            final String[] modules = modulesDir.list(MODULES_FILTER);
            if (modules != null) {
                for (final String module : modules) {
                    list.add(modulesDir.getPath() + File.separatorChar + module);
                }
            }
        }
    }

    /**
     * Checks for class path components in the following properties: "java.class.path", "sun.boot.class.path",
     * "java.ext.dirs"
     *
     * @return class path as used by default by BCEL
     */
    // @since 6.0 no longer final
    public static String getClassPath() {
        final String classPathProp = System.getProperty("java.class.path");
        final String bootClassPathProp = System.getProperty("sun.boot.class.path");
        final String extDirs = System.getProperty("java.ext.dirs");
        // System.out.println("java.version = " + System.getProperty("java.version"));
        // System.out.println("java.class.path = " + classPathProp);
        // System.out.println("sun.boot.class.path=" + bootClassPathProp);
        // System.out.println("java.ext.dirs=" + extDirs);
        final String javaHome = System.getProperty("java.home");
        final List<String> list = new ArrayList<>();

        // Starting in JRE 9, .class files are in the modules directory. Add them to the path.
        final Path modulesPath = Paths.get(javaHome).resolve("lib/modules");
        if (Files.exists(modulesPath) && Files.isRegularFile(modulesPath)) {
            list.add(modulesPath.toAbsolutePath().toString());
        }
        // Starting in JDK 9, .class files are in the jmods directory. Add them to the path.
        addJdkModules(javaHome, list);

        getPathComponents(classPathProp, list);
        getPathComponents(bootClassPathProp, list);
        final List<String> dirs = new ArrayList<>();
        getPathComponents(extDirs, dirs);
        for (final String d : dirs) {
            final File extDir = new File(d);
            final String[] extensions = extDir.list(ARCHIVE_FILTER);
            if (extensions != null) {
                for (final String extension : extensions) {
                    list.add(extDir.getPath() + File.separatorChar + extension);
                }
            }
        }

        return list.stream().collect(Collectors.joining(File.pathSeparator));
    }

    private static void getPathComponents(final String path, final List<String> list) {
        if (path != null) {
            final StringTokenizer tokenizer = new StringTokenizer(path, File.pathSeparator);
            while (tokenizer.hasMoreTokens()) {
                final String name = tokenizer.nextToken();
                final File file = new File(name);
                if (file.exists()) {
                    list.add(name);
                }
            }
        }
    }

    private final String classPathString;

    private final ClassPath parent;

    private final List<AbstractPathEntry> paths;

    /**
     * Search for classes in CLASSPATH.
     *
     * @deprecated Use SYSTEM_CLASS_PATH constant
     */
    @Deprecated
    public ClassPath() {
        this(getClassPath());
    }

    @SuppressWarnings("resource")
    public ClassPath(final ClassPath parent, final String classPathString) {
        this.parent = parent;
        this.classPathString = Objects.requireNonNull(classPathString, "classPathString");
        this.paths = new ArrayList<>();
        for (final StringTokenizer tokenizer = new StringTokenizer(classPathString, File.pathSeparator); tokenizer.hasMoreTokens();) {
            final String path = tokenizer.nextToken();
            if (!path.isEmpty()) {
                final File file = new File(path);
                try {
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            paths.add(new Dir(path));
                        } else if (path.endsWith(haidnor.jvm.bcel.classfile.Module.EXTENSION)) {
                            paths.add(new Module(new ZipFile(file)));
                        } else if (path.endsWith(ModularRuntimeImage.MODULES_PATH)) {
                            paths.add(new JrtModules(ModularRuntimeImage.MODULES_PATH));
                        } else {
                            paths.add(new Jar(new ZipFile(file)));
                        }
                    }
                } catch (final IOException e) {
                    if (path.endsWith(".zip") || path.endsWith(".jar")) {
                        System.err.println("CLASSPATH component " + file + ": " + e);
                    }
                }
            }
        }
    }

    /**
     * Search for classes in given path.
     *
     * @param classPath
     */
    public ClassPath(final String classPath) {
        this(null, classPath);
    }

    @Override
    public void close() throws IOException {
        for (final AbstractPathEntry path : paths) {
            path.close();
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassPath other = (ClassPath) obj;
        return Objects.equals(classPathString, other.classPathString);
    }

    /**
     * @param name fully qualified file name, e.g. java/lang/String
     * @return byte array for class
     * @throws IOException if an I/O error occurs.
     */
    public byte[] getBytes(final String name) throws IOException {
        return getBytes(name, JavaClass.EXTENSION);
    }

    /**
     * @param name fully qualified file name, e.g. java/lang/String
     * @param suffix file name ends with suffix, e.g. .java
     * @return byte array for file on class path
     * @throws IOException if an I/O error occurs.
     */
    public byte[] getBytes(final String name, final String suffix) throws IOException {
        DataInputStream dis = null;
        try (InputStream inputStream = getInputStream(name, suffix)) {
            if (inputStream == null) {
                throw new IOException("Couldn't find: " + name + suffix);
            }
            dis = new DataInputStream(inputStream);
            final byte[] bytes = new byte[inputStream.available()];
            dis.readFully(bytes);
            return bytes;
        } finally {
            if (dis != null) {
                dis.close();
            }
        }
    }

    /**
     * @param name fully qualified class name, e.g. java.lang.String
     * @return input stream for class
     * @throws IOException if an I/O error occurs.
     */
    public ClassFile getClassFile(final String name) throws IOException {
        return getClassFile(name, JavaClass.EXTENSION);
    }

    /**
     * @param name fully qualified file name, e.g. java/lang/String
     * @param suffix file name ends with suff, e.g. .java
     * @return class file for the java class
     * @throws IOException if an I/O error occurs.
     */
    public ClassFile getClassFile(final String name, final String suffix) throws IOException {
        ClassFile cf = null;

        if (parent != null) {
            cf = parent.getClassFileInternal(name, suffix);
        }

        if (cf == null) {
            cf = getClassFileInternal(name, suffix);
        }

        if (cf != null) {
            return cf;
        }

        throw new IOException("Couldn't find: " + name + suffix);
    }

    private ClassFile getClassFileInternal(final String name, final String suffix) {
        for (final AbstractPathEntry path : paths) {
            final ClassFile cf = path.getClassFile(name, suffix);
            if (cf != null) {
                return cf;
            }
        }
        return null;
    }

    /**
     * Gets an InputStream.
     * <p>
     * The caller is responsible for closing the InputStream.
     * </p>
     * @param name fully qualified class name, e.g. java.lang.String
     * @return input stream for class
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream(final String name) throws IOException {
        return getInputStream(Utility.packageToPath(name), JavaClass.EXTENSION);
    }

    /**
     * Gets an InputStream for a class or resource on the classpath.
     * <p>
     * The caller is responsible for closing the InputStream.
     * </p>
     *
     * @param name   fully qualified file name, e.g. java/lang/String
     * @param suffix file name ends with suff, e.g. .java
     * @return input stream for file on class path
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream(final String name, final String suffix) throws IOException {
        try {
            final java.lang.ClassLoader classLoader = getClass().getClassLoader();
            @SuppressWarnings("resource") // closed by caller
            final
            InputStream inputStream = classLoader == null ? null : classLoader.getResourceAsStream(name + suffix);
            if (inputStream != null) {
                return inputStream;
            }
        } catch (final Exception ignored) {
            // ignored
        }
        return getClassFile(name, suffix).getInputStream();
    }

    /**
     * @param name name of file to search for, e.g. java/lang/String.java
     * @return full (canonical) path for file
     * @throws IOException if an I/O error occurs.
     */
    public String getPath(String name) throws IOException {
        final int index = name.lastIndexOf('.');
        String suffix = "";
        if (index > 0) {
            suffix = name.substring(index);
            name = name.substring(0, index);
        }
        return getPath(name, suffix);
    }

    /**
     * @param name name of file to search for, e.g. java/lang/String
     * @param suffix file name suffix, e.g. .java
     * @return full (canonical) path for file, if it exists
     * @throws IOException if an I/O error occurs.
     */
    public String getPath(final String name, final String suffix) throws IOException {
        return getClassFile(name, suffix).getPath();
    }

    /**
     * @param name fully qualified resource name, e.g. java/lang/String.class
     * @return URL supplying the resource, or null if no resource with that name.
     * @since 6.0
     */
    public URL getResource(final String name) {
        for (final AbstractPathEntry path : paths) {
            URL url;
            if ((url = path.getResource(name)) != null) {
                return url;
            }
        }
        return null;
    }

    /**
     * @param name fully qualified resource name, e.g. java/lang/String.class
     * @return InputStream supplying the resource, or null if no resource with that name.
     * @since 6.0
     */
    public InputStream getResourceAsStream(final String name) {
        for (final AbstractPathEntry path : paths) {
            InputStream is;
            if ((is = path.getResourceAsStream(name)) != null) {
                return is;
            }
        }
        return null;
    }

    /**
     * @param name fully qualified resource name, e.g. java/lang/String.class
     * @return An Enumeration of URLs supplying the resource, or an empty Enumeration if no resource with that name.
     * @since 6.0
     */
    public Enumeration<URL> getResources(final String name) {
        final Vector<URL> results = new Vector<>();
        for (final AbstractPathEntry path : paths) {
            URL url;
            if ((url = path.getResource(name)) != null) {
                results.add(url);
            }
        }
        return results.elements();
    }

    @Override
    public int hashCode() {
        return classPathString.hashCode();
    }

    /**
     * @return used class path string
     */
    @Override
    public String toString() {
        if (parent != null) {
            return parent + File.pathSeparator + classPathString;
        }
        return classPathString;
    }
}
