package haidnor.jvm.runtime;

import haidnor.jvm.rtda.heap.Instance;
import haidnor.jvm.rtda.heap.Klass;
import haidnor.jvm.rtda.heap.KlassMethod;
import haidnor.jvm.util.CodeStream;
import haidnor.jvm.util.ConstantPoolUtil;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.LocalVariableTable;

import java.util.Stack;

/**
 * JVM 线程栈帧
 */
public class Frame {
    /**
     * 当前栈帧所处于的 JVM 线程
     */
    private final JvmThread jvmThread;

    /**
     * 栈帧所属的方法
     * <p>
     * <<深入理解JAVA虚拟机>>:
     * 每一个栈帧都包含一个指向运行时常量池中该栈帧所属的方法引用,持有这个引用的目的是为了支持方法调用过程中的动态链接(Dynamic Linking)
     */
    private final org.apache.bcel.classfile.Method method;

    private final KlassMethod klassMethod;

    public final Klass aKlass;

    /**
     * 栈帧所属的方法代码对象
     */
    private final Code code;

    private final CodeStream codeStream;

    private final ConstantPool constantPool;

    private final ConstantPoolUtil constantPoolUtil;

    /**
     * 操作数栈
     */
    private final Stack<StackValue> operandStack = new Stack<>();

    /**
     * 局部变量表
     */
    private final LocalVariableTable localVariableTable;

    /**
     * 槽位
     */
    private final Slot[] slots;

    public Frame(JvmThread jvmThread, KlassMethod klassMethod) {
        this.jvmThread = jvmThread;
        this.aKlass = klassMethod.aKlass;
        this.klassMethod = klassMethod;
        this.method = klassMethod.javaMethod;
        this.code = method.getCode();
        this.constantPool = method.getConstantPool();
        this.constantPoolUtil = new ConstantPoolUtil(constantPool);
        this.codeStream = new CodeStream(method.getCode());
        this.localVariableTable = code.getLocalVariableTable();
        this.slots = new Slot[code.getMaxLocals()];
    }

    public JvmThread getJvmThread() {
        return jvmThread;
    }

    public int getCodeLength() {
        return this.code.getCode().length;
    }

    public CodeStream getCodeStream() {
        return codeStream;
    }

    public org.apache.bcel.classfile.Method getMethod() {
        return method;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public ConstantPoolUtil getConstantPoolUtil() {
        return constantPoolUtil;
    }

    public KlassMethod getMetaMethod() {
        return klassMethod;
    }

    public Klass getMetaClass() {
        return aKlass;
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
            switch (stackValue.getType()) {
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
        switch (stackValue.getType()) {
            case Const.T_CHAR:
                throw new Error("T_CHAR，未作处理");
            case Const.T_INT:
                slotSetInt(index, (int) stackValue.getValue());
                break;
            case Const.T_OBJECT:
                slotSetRef(index, stackValue.getValue());
                break;
            case Const.T_LONG:
                slotSetLong(index, (long) stackValue.getValue());
                break;
            case Const.T_DOUBLE:
                slotSetDouble(index, (double) stackValue.getValue());
                break;
            case Const.T_FLOAT:
                slotSetFloat(index, (float) stackValue.getValue());
                break;
            case Const.T_ARRAY:
                throw new Error("T_ARRAY，未作处理");
            default:
                throw new Error("无法识别的参数类型");
        }
    }

}
