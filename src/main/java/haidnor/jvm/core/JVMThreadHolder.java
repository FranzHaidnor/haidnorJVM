package haidnor.jvm.core;

import haidnor.jvm.runtime.JVMThread;

public abstract class JVMThreadHolder {

    private static final ThreadLocal<JVMThread> holder = new ThreadLocal<>();

    public static void set(JVMThread thread) {
        holder.set(thread);
    }

    public static JVMThread get() {
        return holder.get();
    }

}
