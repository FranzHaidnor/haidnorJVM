package haidnor.jvm.runtime;

import java.util.Stack;

/**
 * JVM 线程
 * <p>
 * 目前这个 JVMThread 并没有真的被 start() 开启, 不继承 Thread 也可以
 */
public class JVMThread extends Thread {

    /**
     * JVM 线程栈
     */
    private final Stack<Frame> stack = new Stack<>();

    public void push(Frame frame) {
        this.stack.push(frame);
    }

    public Frame peek() {
        return this.stack.peek();
    }

    public void pop() {
        this.stack.pop();
    }

    /**
     * 获取 JVM 线程栈的栈帧数量
     */
    public int stackSize() {
        return this.stack.size();
    }

}
