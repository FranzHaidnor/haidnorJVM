package haidnor.jvm.instruction.control;

import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
@Slf4j
public class TABLESWITCH extends Instruction {

    private final int low_case_value;//最小值
    private final int high_case_value;//最大值
    private final int jump_default;//默认跳转
    private final Map<Integer,Integer> map;//映射表

    public TABLESWITCH(CodeStream codeStream) {
        super(codeStream);
        this.jump_default = codeStream.readInt(this);
        this.low_case_value = codeStream.readInt(this);
        this.high_case_value = codeStream.readInt(this);
        this.map = new LinkedHashMap<>();
        for(int i=low_case_value;i<=high_case_value;i++)
        {
            map.put(i,codeStream.readInt(this));
            //创建映射表
        }
    }

    @Override
    public void execute(Frame frame) {

        int value = frame.popInt();//取出index
        Integer offSet = map.getOrDefault(value, jump_default);//得到跳转值
        super.setOffSet(offSet);
    }

}
