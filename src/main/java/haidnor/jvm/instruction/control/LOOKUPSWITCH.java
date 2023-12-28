package haidnor.jvm.instruction.control;


import haidnor.jvm.instruction.Instruction;
import haidnor.jvm.runtime.Frame;
import haidnor.jvm.core.CodeStream;
import lombok.extern.slf4j.Slf4j;
import java.util.LinkedHashMap;
import java.util.Map;
@Slf4j
public class LOOKUPSWITCH extends Instruction {

    private final int count;//索引数量
    private final int jump_default;//默认跳转
    private final Map<Integer,Integer> map;//映射表


    public LOOKUPSWITCH(CodeStream codeStream) {
        super(codeStream);
        this.jump_default = codeStream.readInt(this);

        this.count = codeStream.readInt(this);

        this.map = new LinkedHashMap<>();
        for(int i=0;i<count;i++)
        {
            map.put(codeStream.readInt(this),codeStream.readInt(this));
            //创建映射表

        }
    }

    @Override
    public void execute(Frame frame) {
        int value = frame.popInt();//取出index

        Integer offSet = map.getOrDefault(value, jump_default);//得到跳转值

        super.setOffSet(offSet);

        /*        frame.push(new StackValue(Const.T_INT,offSet+super.getOffSet()));//把跳转值压入栈中*/


    }


}
