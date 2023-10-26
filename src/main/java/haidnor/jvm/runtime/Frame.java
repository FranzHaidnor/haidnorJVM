package haidnor.jvm.runtime;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.classfile.Code;
import haidnor.jvm.bcel.classfile.JavaClass;
import haidnor.jvm.bcel.classfile.JavaMethod;
import haidnor.jvm.rtda.Instance;
import haidnor.jvm.core.CodeStream;
import lombok.Getter;

import java.util.Stack;

/**
 * JVM 线程栈帧
 */
public class Frame {
    /**
     * 每个栈帧中包含一个指向运行时常量池中该栈帧所属的方法的引用。包含这个引用的目的就是为了支持当前方法实现动态链接
     * 有了这个引用，执行引擎就可以找到指定的方法，加载字节码指令
     */
    @Getter
    public final JavaClass javaClass;
    /**
     * 当前栈帧所处于的 JVM 线程
     */
    @Getter
    private final JVMThread jvmThread;
    /**
     * 栈帧所属的方法
     * <p>
     * <<深入理解JAVA虚拟机>>:
     * 每一个栈帧都包含一个指向运行时常量池中该栈帧所属的方法引用,持有这个引用的目的是为了支持方法调用过程中的动态链接(Dynamic Linking)
     */
    @Getter
    private final JavaMethod javaMethod;
    /**
     * 栈帧所属的方法代码对象
     */
    private final Code code;

    @Getter
    private final CodeStream codeStream;

    /**
     * 操作数栈
     */
    private final Stack<StackValue> operandStack = new Stack<>();

    /**
     * 槽位
     */
    private final Slot[] slots;

    public Frame(JVMThread thread, JavaMethod method) {
        this.jvmThread = thread;
        this.javaClass = method.getJavaClass();
        this.javaMethod = method;
        this.code = method.getCode();
        this.codeStream = new CodeStream(method.getCode());
        this.slots = new Slot[code.getMaxLocals()];
    }

    public int getCodeLength() {
        return this.code.getCode().length;
    }


    /* 操作数栈操作 --------------------------------------------------------------------------------------------------- */

    /**
     * 压入操作数栈
     */
    public void push(StackValue stackValue) {
        this.operandStack.push(stackValue);
    }

    /**
     * 弹出操作数栈顶元素
     */
    public StackValue pop() {
        return this.operandStack.pop();
    }

    /**
     * 弹出操作数栈顶元素
     */
    public StackValue peek() {
        return this.operandStack.peek();
    }

    /**
     * 从操作数栈中弹出指定数量的元素的值
     */
    public Object[] popStacksValue(int num) {
        Object[] objArr = new Object[num];
        for (int i = num - 1; i >= 0; i--) {
            StackValue stackValue = operandStack.pop();
            switch (stackValue.getValueType()) {
                case Const.T_CHAR, Const.T_INT, Const.T_OBJECT, Const.T_LONG, Const.T_DOUBLE, Const.T_FLOAT:
                    break;
                case Const.T_ARRAY:
                    throw new Error("数组类型，未作处理");
                default:
                    throw new Error("无法识别的参数类型");
            }
            objArr[i] = stackValue.getValue();
        }
        return objArr;
    }


    public int popInt() {
        StackValue stackValue = pop();
        return (int) stackValue.getValue();
    }

    public void pushInt(int value) {
        push(new StackValue(Const.T_INT, value));
    }

    public long popLong() {
        StackValue stackValue = pop();
        return (long) stackValue.getValue();
    }

    public void pushLong(long value) {
        push(new StackValue(Const.T_LONG, value));
    }

    public float popFloat() {
        StackValue stackValue = pop();
        return (float) stackValue.getValue();
    }

    public void pushFloat(float value) {
        push(new StackValue(Const.T_FLOAT, value));
    }

    public double popDouble() {
        StackValue stackValue = pop();
        return (double) stackValue.getValue();
    }

    public void pushDouble(double value) {
        push(new StackValue(Const.T_DOUBLE, value));
    }

    public Instance popRef() {
        StackValue stackValue = pop();
        return (Instance) stackValue.getValue();
    }

    public void pushRef(Object value) {
        push(new StackValue(Const.T_OBJECT, value));
    }

    /**
     * 获取操作数栈中元素的数量
     */
    public int operandStackSize() {
        return this.operandStack.size();
    }

    /* 局部变量表操作 --------------------------------------------------------------------------------------------------- */

    public void slotSetInt(int index, int val) {
        slots[index] = new Slot(val);
    }

    public int slotGetInt(int index) {
        return slots[index].num;
    }

    public void slotSetFloat(int index, float val) {
        int tmp = Float.floatToIntBits(val);
        slots[index] = new Slot(tmp);
    }

    public float slotGetFloat(int index) {
        int num = slots[index].num;
        return Float.intBitsToFloat(num);
    }

    public long slotGetLong(int index) {
        int high = slots[index].num;
        int low = slots[index + 1].num;

        long l1 = (high & 0x000000ffffffffL) << 32;
        long l2 = low & 0x00000000ffffffffL;
        return l1 | l2;
    }

    public void slotSetLong(int index, long val) {
        // high 32
        int high = (int) (val >> 32);
        // low 32
        int low = (int) (val & 0x000000ffffffffL);

        slots[index] = new Slot(high);
        slots[index + 1] = new Slot(low);
    }

    public void slotSetDouble(int index, double val) {
        long tmp = Double.doubleToLongBits(val);
        // high 32
        int high = (int) (tmp >> 32);
        // low 32
        int low = (int) (tmp & 0x000000ffffffffL);

        slots[index] = new Slot(high);
        slots[index + 1] = new Slot(low);
    }

    public double slotGetDouble(int index) {
        long tmp = this.slotGetLong(index);
        return Double.longBitsToDouble(tmp);
    }

    public void slotSetRef(int index, Object ref) {
        slots[index] = new Slot(ref);
    }

    public Object slotGetRef(int index) {
        return slots[index].ref;
    }

    public void slotSet(int index, StackValue stackValue) {
        switch (stackValue.getValueType()) {
            case Const.T_CHAR -> throw new Error("T_CHAR，未作处理");
            case Const.T_INT -> slotSetInt(index, (int) stackValue.getValue());
            case Const.T_OBJECT -> slotSetRef(index, stackValue.getValue());
            case Const.T_LONG -> slotSetLong(index, (long) stackValue.getValue());
            case Const.T_DOUBLE -> slotSetDouble(index, (double) stackValue.getValue());
            case Const.T_FLOAT -> slotSetFloat(index, (float) stackValue.getValue());
            case Const.T_ARRAY -> throw new Error("T_ARRAY，未作处理");
            default -> throw new Error("无法识别的参数类型");
        }
    }

}
