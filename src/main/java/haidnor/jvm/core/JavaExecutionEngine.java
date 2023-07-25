package haidnor.jvm.core;


import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.instruction.InstructionFactory;
import haidnor.jvm.instruction.control.*;
import haidnor.jvm.rtda.KlassMethod;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.JVMThread;
import haidnor.jvm.runtime.StackValue;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.JvmThreadHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM 执行引擎
 *
 * @author wang xiang
 */
@Slf4j
public class JavaExecutionEngine {

    public static void callMainMethod(KlassMethod klassMethod) {
        callMethod(null, klassMethod);
    }

    public static void callMethod(Frame lastFrame, KlassMethod klassMethod) {
        JVMThread jvmThread = JvmThreadHolder.get();
        Frame newFrame = new Frame(jvmThread, klassMethod);

        // 如果有上一个栈帧, 代表需要传参
        if (lastFrame != null) {
            Method method = klassMethod.javaMethod;
            String signature = method.getSignature();
            String[] argumentTypes = Utility.methodSignatureArgumentTypes(signature);

            int argumentSlotSize = argumentTypes.length;
            if (!method.isStatic()) {
                argumentSlotSize++;
            }

            // 方法调用传参
            // 将上一个栈帧操作数栈中数据弹出,存入下一个栈帧的局部变量表中
            LocalVariableTable localVariableTable = method.getLocalVariableTable();
            if (localVariableTable != null) {
                for (int i = argumentSlotSize - 1; i >= 0; i--) {
                    LocalVariable[] localVariableArr = localVariableTable.getLocalVariableTable();
                    LocalVariable localVariable = localVariableArr[i];
                    int slotIndex = localVariable.getIndex();
                    StackValue stackValue = lastFrame.pop();
                    newFrame.slotSet(slotIndex, stackValue);
                }
            }
        }

        jvmThread.push(newFrame);
        executeFrame(newFrame);
    }

    @SneakyThrows
    public static void executeFrame(Frame frame) {
        int stackSize = frame.getJvmThread().stackSize();

        StringBuilder blank = new StringBuilder();
        blank.append("                    ".repeat(stackSize - 1));
        int index = 0;
        for (int i = 0; i < stackSize - 1; i++) {
            blank.replace(index, index + 1, "│");
            index += 20;
        }

        log.debug("{}┌──────────────────[{}] {} | {}", blank, stackSize, frame.klass.getClassName(), frame.getMethod());

        // 解析方法中的字节码指令
        Map<Integer, Instruction> instructionMap = new HashMap<>();
        CodeStream codeStream = frame.getCodeStream();
        while (codeStream.available() > 0) {
            Instruction instruction = InstructionFactory.creatInstruction(codeStream);
            log.debug("{}│> {}", blank, instruction);
            instructionMap.put(instruction.index(), instruction);
        }

        log.debug("{}├╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌", blank);

        // 执行方法中的字节码指令 tip:(int i, 相当于程序计数器, 记录当前执行到的字节码指令的”行号“)
        for (int i = 0; i < frame.getCodeLength(); ) {
            Instruction instruction = instructionMap.get(i);
            log.debug("{}│ {}", blank, instruction);
            instruction.execute(frame);
            if (instruction instanceof RETURN || instruction instanceof ARETURN || instruction instanceof DRETURN || instruction instanceof FRETURN || instruction instanceof IRETURN) {
                break;
            }
            i += instruction.offSet();
        }

        log.debug("{}└──────────────────[{}] {} | {}", blank, stackSize, frame.klass.getClassName(), frame.getMethod());
    }


}
