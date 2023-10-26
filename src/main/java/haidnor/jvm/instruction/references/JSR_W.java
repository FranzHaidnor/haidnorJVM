package haidnor.jvm.instruction.references;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

/**
 * 在早期版本的Java虚拟机规范中，存在JSR和JSR_W两个指令。其中，JSR指令用于进行子程序调用，而JSR_W指令用于进行宽跳转的子程序调用。宽跳转指令可以用于在字节码中的跳转偏移量超过16位时进行有效的跳转。
 * <p>
 * 然而，从Java SE 6及以后的版本开始，Java虚拟机规范已废弃了JSR_W指令，并且不建议在新的Java代码中使用。而是通过其他方式来实现跳转，例如使用ldc_w、ldc2_w和goto_w等指令结合进行宽跳转。
 */
public class JSR_W extends Instruction {

    public JSR_W(CodeStream codeStream) {
        super(codeStream);
        throw new UnsupportedOperationException("JSR_W");
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        throw new UnsupportedOperationException("JSR");
    }

}
