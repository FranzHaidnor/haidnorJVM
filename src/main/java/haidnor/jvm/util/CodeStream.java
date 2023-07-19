package haidnor.jvm.util;

import haidnor.jvm.instruction.Instruction;
import lombok.SneakyThrows;
import org.apache.bcel.classfile.Code;

import java.io.DataInputStream;

public class CodeStream {

    /**
     * 当前读取到的数组下标
     * -1 代表还没有开始读
     */
    private int index = -1;

    public Code code;

    private final DataInputStream codeStream;

    public CodeStream(Code code) {
        this.code = code;
        this.codeStream = IoUtil.getDataInputStream(code.getCode());
    }

    @SneakyThrows
    public int available() {
        return codeStream.available();
    }

    /**
     * 读取占用一个字节指定代码
     */
    @SneakyThrows
    public int readJavaVmOpcode() {
        this.index += 1;
        return this.codeStream.readUnsignedByte();
    }

    /**
     * 读取占用一个字节的操作数
     */
    @SneakyThrows
    public int readByte(Instruction instruction) {
        instruction.setOffSet(instruction.offSet() + 1);
        this.index += 1;
        return this.codeStream.readByte();
    }

    /**
     * 读取占用一个字节的操作数
     * int 类型
     */
    @SneakyThrows
    public int readUnsignedByte(Instruction instruction) {
        instruction.setOffSet(instruction.offSet() + 1);
        this.index += 1;
        return this.codeStream.readUnsignedByte();
    }

    /**
     * 读取占用两个字节的操作数
     */
    @SneakyThrows
    public int readShort(Instruction instruction) {
        instruction.setOffSet(instruction.offSet() + 2);
        this.index += 2;
        return this.codeStream.readShort();
    }

    /**
     * 读取占用两个字节的操作数
     * int 类型
     */
    @SneakyThrows
    public int readUnsignedShort(Instruction instruction) {
        instruction.setOffSet(instruction.offSet() + 2);
        this.index += 2;
        return this.codeStream.readUnsignedShort();
    }

    /**
     * 读取占用四个字节的操作数
     */
    @SneakyThrows
    public int readInt(Instruction instruction) {
        instruction.setOffSet(instruction.offSet() + 4);
        this.index += 4;
        return this.codeStream.readInt();
    }

    public int index() {
        return this.index;
    }

    public Code getCode() {
        return code;
    }

}
