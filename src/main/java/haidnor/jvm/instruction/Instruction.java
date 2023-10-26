package haidnor.jvm.instruction;

import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Instruction {
    /**
     * 指令在字节码 code 数组中的索引下标 (指令行号)
     */
    private final int index;
    /**
     * 执行下一个指令的偏移量
     */
    @Setter
    private int offSet = 1;

    public Instruction(CodeStream codeStream) {
        this.index = codeStream.getIndex();
    }

    public abstract void execute(Frame frame);

    @Override
    public String toString() {
        return index + " " + this.getClass().getSimpleName();
    }

}
