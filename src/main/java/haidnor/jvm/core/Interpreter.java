package haidnor.jvm.core;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.instruction.InstructionFactory;
import haidnor.jvm.instruction.control.RETURN;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.util.CodeStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 字节码执行解释器
 *
 * @author wang xiang
 */
@Slf4j
public class Interpreter {

    private static int frameCount = -1;

    @SneakyThrows
    public static void executeFrame(Frame frame) {
        frameCount++;
        StringBuilder blank = new StringBuilder();
        blank.append("                    ".repeat(Math.max(0, frameCount)));
        int index = 0;
        for (int i = 0; i < frameCount; i++) {
            blank.replace(index, index + 1, "│");
            index += 20;
        }

        log.debug("{}┌──────────────────[{}] {} | {} ", blank, frameCount + 1, frame.aKlass.getClassName(), frame.getMethod());
        Map<Integer, Instruction> instructionMap = new HashMap<>();

        // 解析方法中的字节码指令
        CodeStream codeStream = frame.getCodeStream();
        while (codeStream.available() > 0) {
            Instruction instruction = InstructionFactory.creatInstruction(codeStream);
            // log.debug("{}│>{}", blank, instruction);
            instructionMap.put(instruction.index(), instruction);
        }
        // log.debug("{}├╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌", blank);

        // 执行方法中的字节码指令 tip:(int i, 相当于程序计数器, 记录当前执行到的字节码指令的”行号“)
        for (int i = 0; i < frame.getCodeLength(); ) {
            Instruction instruction = instructionMap.get(i);
            log.debug("{}│ {}", blank, instruction);
            instruction.execute(frame);
            if (instruction instanceof RETURN) {
                break;
            }
            i += instruction.offSet();
        }
        log.debug("{}└──────────────────", blank);
        frameCount--;
    }

}