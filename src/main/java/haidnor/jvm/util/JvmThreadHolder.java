package haidnor.jvm.util;

import haidnor.jvm.runtime.JVMThread;

/**
 * @author wang xiang
 */
public abstract class JvmThreadHolder {

    private static final ThreadLocal<JVMThread> holder = new ThreadLocal<>();

    public static void set(JVMThread thread) {
        holder.set(thread);
    }

    public static JVMThread get() {
        return holder.get();
    }

}
