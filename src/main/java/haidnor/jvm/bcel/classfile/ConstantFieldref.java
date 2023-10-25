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
package haidnor.jvm.bcel.classfile;

import haidnor.jvm.bcel.Const;

import java.io.DataInput;
import java.io.IOException;

/**
 * This class represents a constant pool reference to a field.
 */
public final class ConstantFieldref extends ConstantCP {

    /**
     * Initialize from another object.
     *
     * @param c Source to copy.
     */
    public ConstantFieldref(final ConstantFieldref c) {
        super(Const.CONSTANT_Fieldref, c.getClassIndex(), c.getNameAndTypeIndex());
    }

    /**
     * Initialize instance from input data.
     *
     * @param input input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantFieldref(final DataInput input) throws IOException {
        super(Const.CONSTANT_Fieldref, input);
    }

    /**
     * @param classIndex       Reference to the class containing the Field
     * @param nameAndTypeIndex and the Field signature
     */
    public ConstantFieldref(final int classIndex, final int nameAndTypeIndex) {
        super(Const.CONSTANT_Fieldref, classIndex, nameAndTypeIndex);
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of Fields, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitConstantFieldref(this);
    }

    /**
     * 获取字段所处于Java类的类名, 例如 java/lang/String
     */
    public String getBelongClassName() {
        ConstantClass constClass = constantPool.getConstant(getClassIndex());
        return (String) constClass.getConstantValue(constantPool);
    }

    /**
     * 获取字段名称
     */
    public String getName() {
        ConstantNameAndType constNameAndType = constantPool.getConstant(getNameAndTypeIndex());
        return constNameAndType.getName(constantPool);
    }

    /**
     * 获取字段类型签名
     */
    public String getSignature() {
        ConstantNameAndType constNameAndType = constantPool.getConstant(getNameAndTypeIndex());
        return constNameAndType.getSignature(constantPool);
    }

}
