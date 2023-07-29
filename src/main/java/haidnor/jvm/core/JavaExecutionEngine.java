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
import org.apache.bcel.Const;
import org.apache.bcel.classfile.*;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM 字节码执行引擎
 *
 * @author wang xiang
 */
@Slf4j
public class JavaExecutionEngine {

    /**
     * 执行  public static void main(String[] args) 方法
     */
    public static void callMainMethod(KlassMethod klassMethod) {
        callMethod(null, klassMethod);
    }

    /**
     * 执行普通方法
     *
     * @param lastFrame   方法调用者的栈帧
     * @param klassMethod 方法元信息
     */
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

    /**
     * 执行 JVM 线程栈栈帧 (haidnorJVM 中最关键的代码)
     *
     * @author wang xiang
     */
    @SneakyThrows
    public static void executeFrame(Frame frame) {
        int stackSize = frame.getJvmThread().stackSize();

        String blank = getDebugStackInfoBlank(stackSize);
        log.debug("{}┌──────────────────[{}] {} | {}", blank, stackSize, frame.klass.getClassName(), frame.getMethod());

        // 解析方法中的字节码指令
        Map<Integer, Instruction> instructionMap = new HashMap<>();
        CodeStream codeStream = frame.getCodeStream();
        while (codeStream.available() > 0) {
            Instruction instruction = InstructionFactory.creatInstruction(codeStream);
            instructionMap.put(instruction.index(), instruction);
            log.debug("{}│> {}", blank, instruction);
        }

        log.debug("{}├╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌", blank);

        // 执行方法中的字节码指令
        // 提示: 变量 pc 相当于程序计数器, 记录当前执行到的字节码指令的"行号"
        for (int pc = 0; pc < frame.getCodeLength(); ) {
            Instruction instruction = instructionMap.get(pc);
            log.debug("{}│ {}", blank, instruction);
            try {
                // 执行字节码指令
                instruction.execute(frame);
                // 若为 return 系列指令则结束当前栈帧
                if (instruction instanceof RETURN || instruction instanceof ARETURN || instruction instanceof DRETURN || instruction instanceof FRETURN || instruction instanceof IRETURN) {
                    break;
                }
                // 程序计数器值增加. 指向下一次执行的字节码行号
                pc += instruction.offSet();
            }
            // 捕获执行字节码指令抛出的异常
            catch (Exception exception) {
                // 从类元信息中获取异常表
                CodeException[] exceptionTable = frame.getMethod().getCode().getExceptionTable();

                // handlerPC 是从异常表中获取的处理异常的字节码行号 (如果查询不到就会一直为 null. 代表方法无异常处理代码)
                Integer handlerPC = null;
                for (CodeException codeException : exceptionTable) {
                    if (codeException.getStartPC() <= pc && pc <= codeException.getEndPC()) {
                        // 异常类型 (0 代表捕获所有类型的异常)
                        int catchType = codeException.getCatchType();
                        if (catchType == 0) {
                            frame.push(new StackValue(Const.T_OBJECT, exception));
                            handlerPC = codeException.getHandlerPC();
                        } else {
                            String exceptionClassName = frame.getConstantPoolUtil().constantClass_ClassName(catchType);
                            exceptionClassName = Utility.compactClassName(exceptionClassName, false);
                            Class<?> exceptionClass = Class.forName(exceptionClassName);
                            if (exceptionClass.isAssignableFrom(exception.getClass())) {
                                frame.push(new StackValue(Const.T_OBJECT, exception));
                                handlerPC = codeException.getHandlerPC();
                            }
                        }
                    }
                }
                if (handlerPC != null) {
                    // 将程序计数器跳转到处理异常的字节码指令上
                    pc = handlerPC;
                } else {
                    // 方法无异常处理,结束栈帧. 将异常抛给调用者处理
                    log.debug("{}└──────────────────[{}] No Exception Handler Return!", blank, stackSize);
                    throw exception;
                }
            }

        }

        log.debug("{}└──────────────────[{}] {} | {}", blank, stackSize, frame.klass.getClassName(), frame.getMethod());
    }

    /**
     * 计算绘画 jvm 线程栈 debug 日志信息的空格
     * 用于表现栈的调用关系
     *
     * @param stackSize 当前 JVM 线程栈的栈帧数量
     */
    private static String getDebugStackInfoBlank(int stackSize) {
        StringBuilder blank = new StringBuilder();
        blank.append("                    ".repeat(stackSize - 1));
        int index = 0;
        for (int i = 0; i < stackSize - 1; i++) {
            blank.replace(index, index + 1, "│");
            index += 20;
        }
        return blank.toString();
    }

}
