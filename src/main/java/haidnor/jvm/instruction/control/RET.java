package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;

/**
 * RET（Return）指令是 Java 虚拟机中的一条指令，用于从方法中返回并将控制流转移到调用该方法的位置。
 * <p>
 * RET 指令在早期版本的 Java 虚拟机中使用，但自从 Java SE 6 版本以后，它已经被废弃不再使用。取而代之的是通过 JSR 和 RET 指令的组合实现的子例程（subroutines）已经被新的字节码指令 invokedynamic 所取代。
 * <p>
 * RET 指令需要一个操作数，作为局部变量表（local variable table）中一个给定索引处的值。这个索引通常是由 JSR（Jump SubRoutine）指令记录的，JSR 指令在携带一个偏移量的情况下，会将指令执行的位置压入操作数栈，并跳转到指定的位置。RET 指令则使用这个记录的位置来返回。
 * <p>
 * 需要注意的是，由于 RET 指令被废弃，所以在现代的 Java 虚拟机中，解释器或即时编译器会将 RET 指令替换为其他的指令序列来实现相同的功能。
 */
public class RET extends Instruction {

    public RET(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("RET");
    }

    @Override
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("RET");
    }

}
