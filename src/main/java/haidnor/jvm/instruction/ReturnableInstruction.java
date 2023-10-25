package haidnor.jvm.instruction;

import haidnor.jvm.util.CodeStream;

/**
 * return 系列字节码指令的抽象类
 */
public abstract class ReturnableInstruction extends Instruction {

    public ReturnableInstruction(CodeStream codeStream) {
        super(codeStream);
    }

}
