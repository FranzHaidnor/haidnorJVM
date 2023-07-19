package haidnor.jvm.util;

import haidnor.jvm.runtime.JvmThread;

public abstract class JvmThreadHolder {

    private static final ThreadLocal<JvmThread> holder = new ThreadLocal<>();

    public static void set(JvmThread thread) {
        holder.set(thread);
    }

    public static JvmThread get() {
        return holder.get();
    }

}
