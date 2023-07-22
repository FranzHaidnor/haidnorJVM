package haidnor.jvm.runtime;

import java.util.Stack;

/**
 * JVM 线程
 *
 * @author wang xiang
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

    public int stackSize() {
        return this.stack.size();
    }

}
