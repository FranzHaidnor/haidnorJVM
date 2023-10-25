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
import haidnor.jvm.bcel.generic.Type;
import haidnor.jvm.bcel.util.BCELComparator;

import java.io.DataInput;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the field info structure, i.e., the representation for a variable in the class. See JVM
 * specification for details.
 */
public class JavaField extends FieldOrMethod {

    /**
     * Empty array constant.
     *
     * @since 6.6.0
     */
    public static final JavaField[] EMPTY_ARRAY = {};
    /**
     * Empty array.
     */
    static final JavaField[] EMPTY_FIELD_ARRAY = {};
    private static BCELComparator bcelComparator = new BCELComparator() {

        @Override
        public boolean equals(final Object o1, final Object o2) {
            final JavaField THIS = (JavaField) o1;
            final JavaField THAT = (JavaField) o2;
            return Objects.equals(THIS.getName(), THAT.getName()) && Objects.equals(THIS.getSignature(), THAT.getSignature());
        }

        @Override
        public int hashCode(final Object o) {
            final JavaField THIS = (JavaField) o;
            return THIS.getSignature().hashCode() ^ THIS.getName().hashCode();
        }
    };
    /**
     * 值类型
     */
    private int valueType;
    /**
     * 值
     */
    private Object value;

    /**
     * Construct object from file stream.
     *
     * @param file Input stream
     */
    JavaField(final DataInput file, final ConstantPool constantPool) throws IOException, ClassFormatException {
        super(file, constantPool);
    }

    /**
     * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a
     * physical copy.
     *
     * @param c Source to copy.
     */
    public JavaField(final JavaField c) {
        super(c);
    }

    /**
     * @param accessFlags    Access rights of field
     * @param nameIndex      Points to field name in constant pool
     * @param signatureIndex Points to encoded signature
     * @param attributes     Collection of attributes
     * @param constantPool   Array of constants
     */
    public JavaField(final int accessFlags, final int nameIndex, final int signatureIndex, final Attribute[] attributes, final ConstantPool constantPool) {
        super(accessFlags, nameIndex, signatureIndex, attributes, constantPool);
    }

    /**
     * @return Comparison strategy object
     */
    public static BCELComparator getComparator() {
        return bcelComparator;
    }

    /**
     * @param comparator Comparison strategy object
     */
    public static void setComparator(final BCELComparator comparator) {
        bcelComparator = comparator;
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitField(this);
    }

    /**
     * @return deep copy of this field
     */
    public JavaField copy(final ConstantPool constantPool) {
        return (JavaField) copy_(constantPool);
    }

    /**
     * Return value as defined by given BCELComparator strategy. By default two Field objects are said to be equal when
     * their names and signatures are equal.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        return bcelComparator.equals(this, obj);
    }

    /**
     * @return constant value associated with this field (may be null)
     */
    public ConstantValue getConstantValue() {
        for (final Attribute attribute : super.getAttributes()) {
            if (attribute.getTag() == Const.ATTR_CONSTANT_VALUE) {
                return (ConstantValue) attribute;
            }
        }
        return null;
    }

    /**
     * @return type of field
     */
    public Type getType() {
        return Type.getReturnType(getSignature());
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Return value as defined by given BCELComparator strategy. By default return the hashcode of the field's name XOR
     * signature.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return bcelComparator.hashCode(this);
    }

    /**
     * Return string representation close to declaration format, 'public static final short MAX = 100', e.g..
     *
     * @return String representation of field, including the signature.
     */
    @Override
    public String toString() {
        String name;
        String signature;
        String access; // Short cuts to constant pool

        // Get names from constant pool
        access = Utility.accessToString(super.getAccessFlags());
        access = access.isEmpty() ? "" : access + " ";
        signature = Utility.signatureToString(getSignature());
        name = getName();
        final StringBuilder buf = new StringBuilder(64); // CHECKSTYLE IGNORE MagicNumber
        buf.append(access).append(signature).append(" ").append(name);
        final ConstantValue cv = getConstantValue();
        if (cv != null) {
            buf.append(" = ").append(cv);
        }
        for (final Attribute attribute : super.getAttributes()) {
            if (!(attribute instanceof ConstantValue)) {
                buf.append(" [").append(attribute).append("]");
            }
        }
        return buf.toString();
    }
}
