package haidnor.jvm.util;

import org.apache.bcel.classfile.*;

/**
 * ConstantPoolUtil
 */
public class ConstantPoolUtil {

    private final ConstantPool cp;

    public ConstantPoolUtil(ConstantPool cp) {
        this.cp = cp;
    }

    public ConstantFieldref getConstantFieldref(int constantFieldrefIndex) {
        return cp.getConstant(constantFieldrefIndex);
    }

    public ConstantMethodref getConstantMethodref(int constantMethodrefIndex) {
        return cp.getConstant(constantMethodrefIndex);
    }

    public ConstantClass getConstantClass(int constantClassIndex) {
        return cp.getConstant(constantClassIndex);
    }

    public ConstantNameAndType getConstantNameAndType(int constantNameAndTypeIndex) {
        return cp.getConstant(constantNameAndTypeIndex);
    }

    // ConstantClass ---------------------------------------------------------------------------------------------------

    /**
     * 获取长类名, 例如 java/lang/String
     */
    public String getClassName(final ConstantClass constantClass) {
        ConstantUtf8 constantUtf8 = cp.getConstant(constantClass.getNameIndex());
        return constantUtf8.getBytes();
    }

    // ConstantFieldref ------------------------------------------------------------------------------------------------

    /**
     * 获取字段所处于Java类的类名, 例如 java/lang/String
     */
    public String getFiledBelongClassName(final ConstantFieldref constantFieldref) {
        ConstantClass constClass = cp.getConstant(constantFieldref.getClassIndex());
        return (String) constClass.getConstantValue(cp);
    }

    /**
     * 获取字段所处于Java类的类名, 例如 java/lang/String
     */
    public String getFiledBelongClassName(int constantFieldrefIndex) {
        ConstantFieldref constantFieldref = getConstantFieldref(constantFieldrefIndex);
        return getFiledBelongClassName(constantFieldref);
    }

    /**
     * 获取字段名称
     */
    public String getFieldName(final ConstantFieldref constantFieldref) {
        ConstantNameAndType constNameAndType = cp.getConstant(constantFieldref.getNameAndTypeIndex());
        return constNameAndType.getName(cp);
    }

    /**
     * 获取字段名称
     */
    public String getFieldName(int constantFieldrefIndex) {
        ConstantFieldref constantFieldref = getConstantFieldref(constantFieldrefIndex);
        return getFieldName(constantFieldref);
    }

    /**
     * 获取字段类型签名
     */
    public String getFieldSignature(final ConstantFieldref constantFieldref) {
        ConstantNameAndType constNameAndType = cp.getConstant(constantFieldref.getNameAndTypeIndex());
        return constNameAndType.getSignature(cp);
    }

    /**
     * 获取字段类型签名
     */
    public String getFieldSignature(int constantFieldrefIndex) {
        ConstantFieldref constantFieldref = getConstantFieldref(constantFieldrefIndex);
        return getFieldSignature(constantFieldref);
    }

    // ConstantMethodref -----------------------------------------------------------------------------------------------

    /**
     * 获取方法所处于Java类的类名
     * 名称使用/分割,例如 haidnor/jvm/test/instruction/references/NEW
     */
    public String getBelongClassName(final ConstantMethodref methodref) {
        ConstantClass constClass = cp.getConstant(methodref.getClassIndex());
        return (String) constClass.getConstantValue(cp);
    }

    /**
     * 获取方法名
     */
    public String getMethodName(final ConstantMethodref methodref) {
        ConstantNameAndType constNameAndType = cp.getConstant(methodref.getNameAndTypeIndex());
        return constNameAndType.getName(cp);
    }

    /**
     * 获取方法签名
     */
    public String getMethodSignature(final ConstantMethodref methodref) {
        ConstantNameAndType constNameAndType = cp.getConstant(methodref.getNameAndTypeIndex());
        return constNameAndType.getSignature(cp);
    }

}
