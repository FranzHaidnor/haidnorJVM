package haidnor.jvm.instruction;

import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;

/**
 * @author wang xiang
 */
public abstract class Instruction {
    /**
     * 指令在字节码 code 数组中的索引下标 (可以理解为指令的行号)
     */
    private final int index;

    /**
     * 执行下一个指令的偏移量
     */
    private int offSet = 1;

    public Instruction(CodeStream codeStream) {
        this.index = codeStream.index();
    }

    /**
     * 执行执行
     *
     * @param frame 当前 JVM 线程栈的栈帧
     */
    public abstract void execute(Frame frame);

    public int index() {
        return index;
    }

    public final int offSet() {
        return this.offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    @Override
    public String toString() {
        return index + " " + this.getClass().getSimpleName();
    }

}
