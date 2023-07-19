package haidnor.jvm.runtime;

import lombok.Data;

/**
 * 操作数栈中的值对象
 */
@Data
public class StackValue {
    /**
     * 类型
     */
    private int type;
    /**
     * 值
     */
    private Object value;

    public StackValue(int type, Object val) {
        this.type = type;
        this.value = val;
    }

}
