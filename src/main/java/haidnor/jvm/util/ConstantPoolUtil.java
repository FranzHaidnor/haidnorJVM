package haidnor.jvm.util;

import org.apache.bcel.classfile.*;

/**
 * @author wang xiang
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


    //

    public ConstantPool getCp() {
        return cp;
    }


    // ConstantClass ---------------------------------------------------------------------------------------------------

    /**
     * 获取长类名, 例如 java/lang/String
     */
    public String constantClass_ClassName(final ConstantClass constantClass) {
        ConstantUtf8 constantUtf8 = cp.getConstant(constantClass.getNameIndex());
        return constantUtf8.getBytes();
    }

    /**
     * 获取长类名, 例如 java/lang/String
     */
    public String constantClass_ClassName(int constantClassIndex) {
        ConstantClass constantClass = cp.getConstant(constantClassIndex);
        return constantClass_ClassName(constantClass);
    }


    // ConstantFieldref ------------------------------------------------------------------------------------------------

    /**
     * 获取字段所处于Java类的类名, 例如 java/lang/String
     */
    public String constantFieldref_ClassName(final ConstantFieldref constantFieldref) {
        ConstantClass constClass = cp.getConstant(constantFieldref.getClassIndex());
        return (String) constClass.getConstantValue(cp);
    }

    /**
     * 获取字段所处于Java类的类名, 例如 java/lang/String
     */
    public String constantFieldref_ClassName(int constantFieldrefIndex) {
        ConstantFieldref constantFieldref = getConstantFieldref(constantFieldrefIndex);
        return constantFieldref_ClassName(constantFieldref);
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
    public String constantMethodref_ClassName(final ConstantMethodref methodref) {
        ConstantClass constClass = cp.getConstant(methodref.getClassIndex());
        return (String) constClass.getConstantValue(cp);
    }

    /**
     * 获取方法名
     */
    public String constantMethodref_MethodName(final ConstantMethodref methodref) {
        ConstantNameAndType constNameAndType = cp.getConstant(methodref.getNameAndTypeIndex());
        return constNameAndType.getName(cp);
    }

    /**
     * 获取方法签名
     */
    public String constantMethodref_MethodSignature(final ConstantMethodref methodref) {
        ConstantNameAndType constNameAndType = cp.getConstant(methodref.getNameAndTypeIndex());
        return constNameAndType.getSignature(cp);
    }

    // ConstantInterfaceMethodref -----------------------------------------------------------------------------------------------

    public String constantInterfaceMethodref_ClassName(final ConstantInterfaceMethodref methodref) {
        ConstantClass constClass = cp.getConstant(methodref.getClassIndex());
        return (String) constClass.getConstantValue(cp);
    }

    /**
     * 获取方法名
     */
    public String constantInterfaceMethodref_MethodName(final ConstantInterfaceMethodref methodref) {
        ConstantNameAndType constNameAndType = cp.getConstant(methodref.getNameAndTypeIndex());
        return constNameAndType.getName(cp);
    }

    /**
     * 获取方法签名
     */
    public String constantInterfaceMethodref_MethodSignature(final ConstantInterfaceMethodref methodref) {
        ConstantNameAndType constNameAndType = cp.getConstant(methodref.getNameAndTypeIndex());
        return constNameAndType.getSignature(cp);
    }


    // ConstantNameAndType -----------------------------------------------------------------------------------------------

    /**
     * ConstantNameAndType
     */
    public ConstantNameAndType constantNameAndType(int constantNameAndTypeIndex) {
        return cp.getConstant(constantNameAndTypeIndex);
    }

    public String constantNameAndType_name(int constantNameAndTypeIndex) {
        ConstantNameAndType constantNameAndType = constantNameAndType(constantNameAndTypeIndex);
        return constantNameAndType.getName(cp);
    }

    public String constantNameAndType_signature(int constantNameAndTypeIndex) {
        ConstantNameAndType constantNameAndType = constantNameAndType(constantNameAndTypeIndex);
        return constantNameAndType.getSignature(cp);
    }


}
