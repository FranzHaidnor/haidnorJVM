package haidnor.jvm.runtime;

import lombok.Getter;

@Getter
public class StackValue {
    /**
     * 类型
     */
    private final int valueType;
    /**
     * 值
     */
    private final Object value;

    public StackValue(int valueType, Object value) {
        this.valueType = valueType;
        this.value = value;
    }

}
