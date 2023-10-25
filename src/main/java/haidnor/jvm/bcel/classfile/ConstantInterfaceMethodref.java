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
import lombok.SneakyThrows;

import java.io.DataInput;
import java.io.IOException;

/**
 * This class represents a constant pool reference to an interface method.
 */
public final class ConstantInterfaceMethodref extends ConstantCP {

    /**
     * Initialize from another object.
     *
     * @param c Source to copy.
     */
    public ConstantInterfaceMethodref(final ConstantInterfaceMethodref c) {
        super(Const.CONSTANT_InterfaceMethodref, c.getClassIndex(), c.getNameAndTypeIndex());
    }

    /**
     * Initialize instance from input data.
     *
     * @param input input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantInterfaceMethodref(final DataInput input) throws IOException {
        super(Const.CONSTANT_InterfaceMethodref, input);
    }

    /**
     * @param classIndex       Reference to the class containing the method
     * @param nameAndTypeIndex and the method signature
     */
    public ConstantInterfaceMethodref(final int classIndex, final int nameAndTypeIndex) {
        super(Const.CONSTANT_InterfaceMethodref, classIndex, nameAndTypeIndex);
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitConstantInterfaceMethodref(this);
    }

    public String getClassName() {
        ConstantClass constClass = constantPool.getConstant(getClassIndex());
        return (String) constClass.getConstantValue(constantPool);
    }

    public String getMethodName() {
        ConstantNameAndType constNameAndType = constantPool.getConstant(getNameAndTypeIndex());
        return constNameAndType.getName(constantPool);
    }

    public String getMethodSignature() {
        ConstantNameAndType constNameAndType = constantPool.getConstant(getNameAndTypeIndex());
        return constNameAndType.getSignature(constantPool);
    }

    public String getReturnType() {
        return Utility.methodSignatureReturnType(getMethodSignature(), false);
    }

    /**
     * 解析方法签名返回方法参数类型数组
     */
    @SneakyThrows
    public Class<?>[] getParameterTypeArr() {
        String[] argumentTypeArr = Utility.methodSignatureArgumentTypes(getMethodSignature(), false);
        Class<?>[] argumentClassArr = new Class[argumentTypeArr.length];
        for (int i = 0; i < argumentTypeArr.length; i++) {
            Class<?> argumentClass;
            String argumentType = argumentTypeArr[i];
            argumentClass = switch (argumentType) {
                case "byte" -> byte.class;
                case "short" -> short.class;
                case "boolean" -> boolean.class;
                case "char" -> char.class;
                case "int" -> int.class;
                case "long" -> long.class;
                case "float" -> float.class;
                case "double" -> double.class;
                default -> Class.forName(argumentType);
            };
            argumentClassArr[i] = argumentClass;
        }
        return argumentClassArr;
    }
}
