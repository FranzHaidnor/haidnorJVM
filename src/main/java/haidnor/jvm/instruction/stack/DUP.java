package haidnor.jvm.instruction.stack;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.core.CodeStream;
import lombok.SneakyThrows;

public class DUP extends Instruction {

    public DUP(CodeStream codeStream) {
        super(codeStream);
    }

    @Override
    @SneakyThrows
    public void execute(Frame frame) {
        StackValue stackValue = frame.pop();

        // 判空是为了兼容创建 Java 对象, 创建rt.jar java对象时,执行 NEW 指令会在栈帧中存放一个 NULL
        if (stackValue.getValue() != null) {
            frame.push(stackValue);
            frame.push(stackValue);
        }
    }

}
