package haidnor.jvm.core;


import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.instruction.InstructionFactory;
import haidnor.jvm.instruction.control.ReturnableInstruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.runtime.JVMThread;
import haidnor.jvm.runtime.StackValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM 字节码执行引擎
 */
@Slf4j
public class JavaExecutionEngine {

    /**
     * 执行  public static void main(String[] args) 方法
     */
    public static void callMain(JavaClass clazz) {
        JavaMethod mainMethod = clazz.getMainMethod();
        callMethod(null, mainMethod);
    }

    /**
     * 执行普通方法
     *
     * @param lastFrame  方法调用者的栈帧
     * @param method 方法元信息
     */
    public static void callMethod(Frame lastFrame, JavaMethod method) {
        JVMThread thread = JVMThreadHolder.get();
        // 调用方法时会创建新的栈帧
        Frame newFrame = new Frame(thread, method);

        // 如果线程栈内存在栈帧, 代表可能需要方法调用传参
        if (lastFrame != null) {
            String signature = method.getSignature();
            String[] argumentTypes = Utility.methodSignatureArgumentTypes(signature);

            int argumentSlotSize = argumentTypes.length;
            if (!method.isStatic()) {
                argumentSlotSize++;
            }

            // 方法调用传参 (原理: 将顶部栈帧的操作数栈中的数据弹出, 存入新栈帧的局部变量表中)
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

        // 将新栈帧压入线程栈顶部, 并执行新栈帧中的代码
        thread.push(newFrame);
        executeFrame(newFrame);
    }

    /**
     * 执行 JVM 线程栈栈帧 (haidnorJVM 中最关键的代码)
     */
    @SneakyThrows
    public static void executeFrame(Frame frame) {
        int stackSize = frame.getJvmThread().stackSize();

        String blank = getDebugStackInfoBlank(stackSize);
        log.debug("{}┌──────────────────[{}] {} | {}", blank, stackSize, frame.getJavaClass().getClassName(), frame.getJavaMethod());

        // 解析方法中的字节码指令
        Map<Integer, Instruction> instructionMap = new HashMap<>();
        CodeStream codeStream = frame.getCodeStream();
        while (codeStream.available() > 0) {
            Instruction instruction = InstructionFactory.creatInstruction(codeStream);
            instructionMap.put(instruction.getIndex(), instruction);
            // log.debug("{}│> {}", blank, instruction);   // debug 输出解析字节码指令的内容
        }
        // log.debug("{}├ - - - - - - - - -", blank);

        // 执行方法中的字节码指令
        // 提示: 变量 pc 相当于程序计数器, 记录当前执行到的字节码指令的"行号"
        for (int pc = 0; pc < frame.getCodeLength(); ) {
            Instruction instruction = instructionMap.get(pc);
            log.debug("{}│ {}", blank, instruction);
            try {
                // 执行字节码指令
                instruction.execute(frame);
                // 若为 return 系列指令则结束当前栈帧 (RETURN,ARETURN,DRETURN,FRETURN,IRETURN)
                if (instruction instanceof ReturnableInstruction) {
                    break;
                }
                // 程序计数器值增加. 指向下一次执行的字节码行号
                pc += instruction.getOffSet();
            }
            // 捕获执行字节码指令抛出的异常
            catch (Exception exception) {
                // 从类元信息中获取异常表
                CodeException[] exceptionTable = frame.getJavaMethod().getCode().getExceptionTable();

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
                            // 从常量池中查询异常表定义的异常类型
                            ConstantClass constantClass = frame.getJavaMethod().getConstantPool().getConstant(catchType);
                            String exceptionClassName = constantClass.getClassName();
                            exceptionClassName = Utility.compactClassName(exceptionClassName, false);

                            // 判断异常的泛型类型. 假如执行指令抛出的是 NullPointerException 类型, 异常表定义的是 Exception 类型, 则此异常可以被捕获
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

        log.debug("{}└──────────────────[{}] {} | {}", blank, stackSize, frame.getJavaClass().getClassName(), frame.getJavaMethod());
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
