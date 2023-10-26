package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * JSR（Jump to Subroutine）指令是一条在Java虚拟机中用于进行子程序调用的指令。它将当前指令的返回地址压入操作数栈，并跳转到指定的子程序代码执行。子程序执行完毕后，通过RET（Return）指令返回到主程序的下一条指令继续执行。
 * <p>
 * JSR指令的操作码为0xA8，属于控制指令家族（Control Instructions）。它的操作数是一个16位的无符号偏移量，用于表示跳转目标的字节码偏移量。
 * <p>
 * 使用JSR指令可以实现方法的调用和返回，但在实际的Java字节码中，更常见的是使用invokestatic、invokevirtual等指令来进行方法调用。
 * <p>
 * 需要注意的是，JSR指令只在早期版本的Java虚拟机规范中存在，在Java SE 6及以后的版本中已被废弃，并且不建议在新的Java代码中使用。
 */
public class JSR extends Instruction {

    public JSR(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("JSR");
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("JSR");
    }

}
