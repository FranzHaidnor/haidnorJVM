package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.core.CodeStream;

/**
 * return 系列字节码指令的抽象类
 */
public abstract class ReturnableInstruction extends Instruction {

    public ReturnableInstruction(CodeStream codeStream) {
        super(codeStream);
    }

}
