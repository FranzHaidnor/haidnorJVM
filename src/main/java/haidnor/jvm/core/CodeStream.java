package haidnor.jvm.core;


import haidnor.jvm.bcel.classfile.Code;
import haidnor.jvm.instruction.Instruction;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class CodeStream {

    private final DataInputStream codeStream;
    @Getter
    public Code code;
    /**
     * 当前读取到的数组下标
     * -1 代表还没有开始读
     */
    @Getter
    private int index = -1;

    public CodeStream(Code code) {
        this.code = code;
        this.codeStream = getDataInputStream(code.getCode());
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
        instruction.setOffSet(instruction.getOffSet() + 1);
        this.index += 1;
        return this.codeStream.readByte();
    }

    /**
     * 读取占用一个字节的操作数
     * int 类型
     */
    @SneakyThrows
    public int readUnsignedByte(Instruction instruction) {
        instruction.setOffSet(instruction.getOffSet() + 1);
        this.index += 1;
        return this.codeStream.readUnsignedByte();
    }

    /**
     * 读取占用两个字节的操作数
     */
    @SneakyThrows
    public int readShort(Instruction instruction) {
        instruction.setOffSet(instruction.getOffSet() + 2);
        this.index += 2;
        return this.codeStream.readShort();
    }

    /**
     * 读取占用两个字节的操作数
     * int 类型
     */
    @SneakyThrows
    public int readUnsignedShort(Instruction instruction) {
        instruction.setOffSet(instruction.getOffSet() + 2);
        this.index += 2;
        return this.codeStream.readUnsignedShort();
    }

    /**
     * 读取占用四个字节的操作数
     */
    @SneakyThrows
    public int readInt(Instruction instruction) {
        instruction.setOffSet(instruction.getOffSet() + 4);
        this.index += 4;
        return this.codeStream.readInt();
    }

    private DataInputStream getDataInputStream(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return new DataInputStream(inputStream);
    }

}
