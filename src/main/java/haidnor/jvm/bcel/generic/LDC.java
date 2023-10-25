/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package haidnor.jvm.bcel.generic;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.bcel.ExceptionConst;
import haidnor.jvm.bcel.classfile.*;
import haidnor.jvm.bcel.util.ByteSequence;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * LDC - Push item from constant pool.
 *
 * <PRE>
 * Stack: ... -&gt; ..., item
 * </PRE>
 */
public class LDC extends CPInstruction implements PushInstruction, ExceptionThrower {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    LDC() {
    }

    public LDC(final int index) {
        super(Const.LDC_W, index);
        setSize();
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitStackProducer(this);
        v.visitPushInstruction(this);
        v.visitExceptionThrower(this);
        v.visitTypedInstruction(this);
        v.visitCPInstruction(this);
        v.visitLDC(this);
    }

    /**
     * Dump instruction as byte code to stream out.
     *
     * @param out Output stream
     */
    @Override
    public void dump(final DataOutputStream out) throws IOException {
        out.writeByte(super.getOpcode());
        if (super.getLength() == 2) { // TODO useless check?
            out.writeByte(super.getIndex());
        } else {
            out.writeShort(super.getIndex());
        }
    }

    @Override
    public Class<?>[] getExceptions() {
        return ExceptionConst.createExceptions(ExceptionConst.EXCS.EXCS_STRING_RESOLUTION);
    }

    @Override
    public Type getType(final ConstantPoolGen cpg) {
        switch (cpg.getConstantPool().getConstant(super.getIndex()).getTag()) {
            case Const.CONSTANT_String:
                return Type.STRING;
            case Const.CONSTANT_Float:
                return Type.FLOAT;
            case Const.CONSTANT_Integer:
                return Type.INT;
            case Const.CONSTANT_Class:
                return Type.CLASS;
            default: // Never reached
                throw new IllegalArgumentException("Unknown or invalid constant type at " + super.getIndex());
        }
    }

    public Object getValue(final ConstantPoolGen cpg) {
        Constant c = cpg.getConstantPool().getConstant(super.getIndex());
        switch (c.getTag()) {
            case Const.CONSTANT_String:
                final int i = ((ConstantString) c).getStringIndex();
                c = cpg.getConstantPool().getConstant(i);
                return ((ConstantUtf8) c).getBytes();
            case Const.CONSTANT_Float:
                return Float.valueOf(((ConstantFloat) c).getBytes());
            case Const.CONSTANT_Integer:
                return Integer.valueOf(((ConstantInteger) c).getBytes());
            case Const.CONSTANT_Class:
                final int nameIndex = ((ConstantClass) c).getNameIndex();
                c = cpg.getConstantPool().getConstant(nameIndex);
                return Type.getType(((ConstantUtf8) c).getBytes());
            default: // Never reached
                throw new IllegalArgumentException("Unknown or invalid constant type at " + super.getIndex());
        }
    }

    /**
     * Read needed data (e.g. index) from file.
     */
    @Override
    protected void initFromFile(final ByteSequence bytes, final boolean wide) throws IOException {
        super.setLength(2);
        super.setIndex(bytes.readUnsignedByte());
    }

    /**
     * Set the index to constant pool and adjust size.
     */
    @Override
    public final void setIndex(final int index) {
        super.setIndex(index);
        setSize();
    }

    // Adjust to proper size
    protected final void setSize() {
        if (super.getIndex() <= Const.MAX_BYTE) { // Fits in one byte?
            super.setOpcode(Const.LDC);
            super.setLength(2);
        } else {
            super.setOpcode(Const.LDC_W);
            super.setLength(3);
        }
    }
}
