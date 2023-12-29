package haidnor.jvm.instruction;

import haidnor.jvm.bcel.Const;
import haidnor.jvm.instruction.comparisons.*;
import haidnor.jvm.instruction.constants.*;
import haidnor.jvm.instruction.control.*;
import haidnor.jvm.instruction.conversions.*;
import haidnor.jvm.instruction.extended.*;
import haidnor.jvm.instruction.loads.*;
import haidnor.jvm.instruction.math.*;
import haidnor.jvm.instruction.references.*;
import haidnor.jvm.instruction.stack.*;
import haidnor.jvm.instruction.stores.*;
import haidnor.jvm.core.CodeStream;

public abstract class InstructionFactory {

    public static Instruction creatInstruction(CodeStream codeStream) {
        int opcode = codeStream.readJavaVmOpcode();
        switch (opcode) {
            case Const.NOP -> {
                return new NOP(codeStream);
            }
            case Const.ACONST_NULL -> {
                return new ACONST_NULL(codeStream);
            }
            case Const.ICONST_M1 -> {
                return new ICONST_M1(codeStream);
            }
            case Const.ICONST_0 -> {
                return new ICONST_0(codeStream);
            }
            case Const.ICONST_1 -> {
                return new ICONST_1(codeStream);
            }
            case Const.ICONST_2 -> {
                return new ICONST_2(codeStream);
            }
            case Const.ICONST_3 -> {
                return new ICONST_3(codeStream);
            }
            case Const.ICONST_4 -> {
                return new ICONST_4(codeStream);
            }
            case Const.ICONST_5 -> {
                return new ICONST_5(codeStream);
            }
            case Const.LCONST_0 -> {
                return new LCONST_0(codeStream);
            }
            case Const.LCONST_1 -> {
                return new LCONST_1(codeStream);
            }
            case Const.FCONST_1 -> {
                return new FCONST_1(codeStream);
            }
            case Const.FCONST_2 -> {
                return new FCONST_2(codeStream);
            }
            case Const.DCONST_0 -> {
                return new DCONST_0(codeStream);
            }
            case Const.DCONST_1 -> {
                return new DCONST_1(codeStream);
            }
            case Const.BIPUSH -> {
                return new BIPUSH(codeStream);
            }
            case Const.SIPUSH -> {
                return new SIPUSH(codeStream);
            }
            case Const.LDC -> {
                return new LDC(codeStream);
            }
            case Const.LDC_W -> {
                return new LDC_W(codeStream);
            }
            case Const.LDC2_W -> {
                return new LDC2W(codeStream);
            }
            case Const.ILOAD -> {
                return new ILOAD(codeStream);
            }
            case Const.LLOAD -> {
                return new LLOAD(codeStream);
            }
            case Const.FLOAD -> {
                return new FLOAD(codeStream);
            }
            case Const.DLOAD -> {
                return new DLOAD(codeStream);
            }
            case Const.ALOAD -> {
                return new ALOAD(codeStream);
            }
            case Const.ILOAD_0 -> {
                return new ILOAD_0(codeStream);
            }
            case Const.ILOAD_1 -> {
                return new ILOAD_1(codeStream);
            }
            case Const.ILOAD_2 -> {
                return new ILOAD_2(codeStream);
            }
            case Const.ILOAD_3 -> {
                return new ILOAD_3(codeStream);
            }
            case Const.LLOAD_0 -> {
                return new LLOAD_0(codeStream);
            }
            case Const.LLOAD_1 -> {
                return new LLOAD_1(codeStream);
            }
            case Const.LLOAD_2 -> {
                return new LLOAD_2(codeStream);
            }
            case Const.LLOAD_3 -> {
                return new LLOAD_3(codeStream);
            }
            case Const.FLOAD_0 -> {
                return new FLOAD_0(codeStream);
            }
            case Const.FLOAD_1 -> {
                return new FLOAD_1(codeStream);
            }
            case Const.FLOAD_2 -> {
                return new FLOAD_2(codeStream);
            }
            case Const.FLOAD_3 -> {
                return new FLOAD_3(codeStream);
            }
            case Const.DLOAD_0 -> {
                return new DLOAD_0(codeStream);
            }
            case Const.DLOAD_1 -> {
                return new DLOAD_1(codeStream);
            }
            case Const.DLOAD_2 -> {
                return new DLOAD_2(codeStream);
            }
            case Const.DLOAD_3 -> {
                return new DLOAD_3(codeStream);
            }
            case Const.ALOAD_0 -> {
                return new ALOAD_0(codeStream);
            }
            case Const.ALOAD_1 -> {
                return new ALOAD_1(codeStream);
            }
            case Const.ALOAD_2 -> {
                return new ALOAD_2(codeStream);
            }
            case Const.ALOAD_3 -> {
                return new ALOAD_3(codeStream);
            }
            case Const.IALOAD -> {
                return new IALOAD(codeStream);
            }
            case Const.LALOAD -> {
                return new LALOAD(codeStream);
            }
            case Const.FALOAD -> {
                return new FALOAD(codeStream);
            }
            case Const.DALOAD -> {
                return new DALOAD(codeStream);
            }
            case Const.AALOAD -> {
                return new AALOAD(codeStream);
            }
            case Const.BALOAD -> {
                return new BALOAD(codeStream);
            }
            case Const.CALOAD -> {
                return new CALOAD(codeStream);
            }
            case Const.SALOAD -> {
                return new SALOAD(codeStream);
            }
            case Const.ISTORE -> {
                return new ISTORE(codeStream);
            }
            case Const.LSTORE -> {
                return new LSTORE(codeStream);
            }
            case Const.FSTORE -> {
                return new FSTORE(codeStream);
            }
            case Const.DSTORE -> {
                return new DSTORE(codeStream);
            }
            case Const.ASTORE -> {
                return new ASTORE(codeStream);
            }
            case Const.ISTORE_0 -> {
                return new ISTORE_0(codeStream);
            }
            case Const.ISTORE_1 -> {
                return new ISTORE_1(codeStream);
            }
            case Const.ISTORE_2 -> {
                return new ISTORE_2(codeStream);
            }
            case Const.ISTORE_3 -> {
                return new ISTORE_3(codeStream);
            }
            case Const.LSTORE_0 -> {
                return new LSTORE_0(codeStream);
            }
            case Const.LSTORE_1 -> {
                return new LSTORE_1(codeStream);
            }
            case Const.LSTORE_2 -> {
                return new LSTORE_2(codeStream);
            }
            case Const.LSTORE_3 -> {
                return new LSTORE_3(codeStream);
            }
            case Const.FSTORE_0 -> {
                return new FSTORE_0(codeStream);
            }
            case Const.FSTORE_1 -> {
                return new FSTORE_1(codeStream);
            }
            case Const.FSTORE_2 -> {
                return new FSTORE_2(codeStream);
            }
            case Const.FSTORE_3 -> {
                return new FSTORE_3(codeStream);
            }
            case Const.DSTORE_0 -> {
                return new DSTORE_0(codeStream);
            }
            case Const.DSTORE_1 -> {
                return new DSTORE_1(codeStream);
            }
            case Const.DSTORE_2 -> {
                return new DSTORE_2(codeStream);
            }
            case Const.DSTORE_3 -> {
                return new DSTORE_3(codeStream);
            }
            case Const.ASTORE_0 -> {
                return new ASTORE_0(codeStream);
            }
            case Const.ASTORE_1 -> {
                return new ASTORE_1(codeStream);
            }
            case Const.ASTORE_2 -> {
                return new ASTORE_2(codeStream);
            }
            case Const.ASTORE_3 -> {
                return new ASTORE_3(codeStream);
            }
            case Const.IASTORE -> {
                return new IASTORE(codeStream);
            }
            case Const.LASTORE -> {
                return new LASTORE(codeStream);
            }
            case Const.FASTORE -> {
                return new FASTORE(codeStream);
            }
            case Const.DASTORE -> {
                return new DASTORE(codeStream);
            }
            case Const.AASTORE -> {
                return new AASTORE(codeStream);
            }
            case Const.BASTORE -> {
                return new BASTORE(codeStream);
            }
            case Const.CASTORE -> {
                return new CASTORE(codeStream);
            }
            case Const.SASTORE -> {
                return new SASTORE(codeStream);
            }
            case Const.POP -> {
                return new POP(codeStream);
            }
            case Const.POP2 -> {
                return new POP2(codeStream);
            }
            case Const.DUP -> {
                return new DUP(codeStream);
            }
            case Const.DUP_X1 -> {
                return new DUP_X1(codeStream);
            }
            case Const.DUP_X2 -> {
                return new DUP_X2(codeStream);
            }
            case Const.DUP2 -> {
                return new DUP2(codeStream);
            }
            case Const.DUP2_X1 -> {
                return new DUP2_X1(codeStream);
            }
            case Const.DUP2_X2 -> {
                return new DUP2_X2(codeStream);
            }
            case Const.SWAP -> {
                return new SWAP(codeStream);
            }
            case Const.IADD -> {
                return new IADD(codeStream);
            }
            case Const.LADD -> {
                return new LADD(codeStream);
            }
            case Const.FADD -> {
                return new FADD(codeStream);
            }
            case Const.DADD -> {
                return new DADD(codeStream);
            }
            case Const.ISUB -> {
                return new ISUB(codeStream);
            }
            case Const.LSUB -> {
                return new LSUB(codeStream);
            }
            case Const.FSUB -> {
                return new FSUB(codeStream);
            }
            case Const.DSUB -> {
                return new DSUB(codeStream);
            }
            case Const.IMUL -> {
                return new IMUL(codeStream);
            }
            case Const.LMUL -> {
                return new LMUL(codeStream);
            }
            case Const.FMUL -> {
                return new FMUL(codeStream);
            }
            case Const.DMUL -> {
                return new DMUL(codeStream);
            }
            case Const.IDIV -> {
                return new IDIV(codeStream);
            }
            case Const.LDIV -> {
                return new LDIV(codeStream);
            }
            case Const.FDIV -> {
                return new FDIV(codeStream);
            }
            case Const.DDIV -> {
                return new DDIV(codeStream);
            }
            case Const.IREM -> {
                return new IREM(codeStream);
            }
            case Const.LREM -> {
                return new LREM(codeStream);
            }
            case Const.FREM -> {
                return new FREM(codeStream);
            }
            case Const.DREM -> {
                return new DREM(codeStream);
            }
            case Const.INEG -> {
                return new INEG(codeStream);
            }
            case Const.LNEG -> {
                return new LNEG(codeStream);
            }
            case Const.FNEG -> {
                return new FNEG(codeStream);
            }
            case Const.DNEG -> {
                return new DNEG(codeStream);
            }
            case Const.ISHL -> {
                return new ISHL(codeStream);
            }
            case Const.LSHL -> {
                return new LSHL(codeStream);
            }
            case Const.ISHR -> {
                return new ISHR(codeStream);
            }
            case Const.LSHR -> {
                return new LSHR(codeStream);
            }
            case Const.IUSHR -> {
                return new IUSHR(codeStream);
            }
            case Const.LUSHR -> {
                return new LUSHR(codeStream);
            }
            case Const.IAND -> {
                return new IAND(codeStream);
            }
            case Const.LAND -> {
                return new LAND(codeStream);
            }
            case Const.IOR -> {
                return new IOR(codeStream);
            }
            case Const.LOR -> {
                return new LOR(codeStream);
            }
            case Const.IXOR -> {
                return new IXOR(codeStream);
            }
            case Const.LXOR -> {
                return new LXOR(codeStream);
            }
            case Const.IINC -> {
                return new IINC(codeStream);
            }
            case Const.I2L -> {
                return new I2L(codeStream);
            }
            case Const.I2F -> {
                return new I2F(codeStream);
            }
            case Const.I2D -> {
                return new I2D(codeStream);
            }
            case Const.L2I -> {
                return new L2I(codeStream);
            }
            case Const.L2F -> {
                return new L2F(codeStream);
            }
            case Const.L2D -> {
                return new L2D(codeStream);
            }
            case Const.F2I -> {
                return new F2I(codeStream);
            }
            case Const.F2L -> {
                return new F2L(codeStream);
            }
            case Const.F2D -> {
                return new F2D(codeStream);
            }
            case Const.D2I -> {
                return new D2I(codeStream);
            }
            case Const.D2L -> {
                return new D2L(codeStream);
            }
            case Const.D2F -> {
                return new D2F(codeStream);
            }
            case Const.I2B -> {
                return new I2B(codeStream);
            }
            case Const.I2C -> {
                return new I2C(codeStream);
            }
            case Const.I2S -> {
                return new I2S(codeStream);
            }
            case Const.LCMP -> {
                return new LCMP(codeStream);
            }
            case Const.FCMPL -> {
                return new FCMPL(codeStream);
            }
            case Const.FCMPG -> {
                return new FCMPG(codeStream);
            }
            case Const.DCMPL -> {
                return new DCMPL(codeStream);
            }
            case Const.DCMPG -> {
                return new DCMPG(codeStream);
            }
            case Const.IFEQ -> {
                return new IFEQ(codeStream);
            }
            case Const.IFNE -> {
                return new IFNE(codeStream);
            }
            case Const.IFLT -> {
                return new IFLT(codeStream);
            }
            case Const.IFGE -> {
                return new IFGE(codeStream);
            }
            case Const.IFGT -> {
                return new IFGT(codeStream);
            }
            case Const.IFLE -> {
                return new IFLE(codeStream);
            }
            case Const.IF_ICMPEQ -> {
                return new IF_ICMPEQ(codeStream);
            }
            case Const.IF_ICMPNE -> {
                return new IF_ICMPNE(codeStream);
            }
            case Const.IF_ICMPLT -> {
                return new IF_ICMPLT(codeStream);
            }
            case Const.IF_ICMPGE -> {
                return new IF_ICMPGE(codeStream);
            }
            case Const.IF_ICMPGT -> {
                return new IF_ICMPGT(codeStream);
            }
            case Const.IF_ICMPLE -> {
                return new IF_ICMPLE(codeStream);
            }
            case Const.IF_ACMPEQ -> {
                return new IF_ACMPEQ(codeStream);
            }
            case Const.IF_ACMPNE -> {
                return new IF_ACMPNE(codeStream);
            }
            case Const.GOTO -> {
                return new GOTO(codeStream);
            }
            case Const.JSR -> {
                return new JSR(codeStream);
            }
            case Const.RET -> {
                return new RET(codeStream);
            }
            case Const.TABLESWITCH -> {
                return new TABLESWITCH(codeStream);
            }
            case Const.LOOKUPSWITCH -> {
                return new LOOKUPSWITCH(codeStream);
            }
            case Const.IRETURN -> {
                return new IRETURN(codeStream);
            }
            case Const.LRETURN -> {
                return new LRETURN(codeStream);
            }
            case Const.FRETURN -> {
                return new FRETURN(codeStream);
            }
            case Const.DRETURN -> {
                return new DRETURN(codeStream);
            }
            case Const.ARETURN -> {
                return new ARETURN(codeStream);
            }
            case Const.RETURN -> {
                return new RETURN(codeStream);
            }
            case Const.GETSTATIC -> {
                return new GETSTATIC(codeStream);
            }
            case Const.PUTSTATIC -> {
                return new PUTSTATIC(codeStream);
            }
            case Const.GETFIELD -> {
                return new GETFIELD(codeStream);
            }
            case Const.PUTFIELD -> {
                return new PUTFIELD(codeStream);
            }
            case Const.INVOKEVIRTUAL -> {
                return new INVOKEVIRTUAL(codeStream);
            }
            case Const.INVOKESPECIAL -> {
                return new INVOKESPECIAL(codeStream);
            }
            case Const.INVOKESTATIC -> {
                return new INVOKESTATIC(codeStream);
            }
            case Const.INVOKEINTERFACE -> {
                return new INVOKEINTERFACE(codeStream);
            }
            case Const.INVOKEDYNAMIC -> {
                return new INVOKEDYNAMIC(codeStream); // TODO
            }
            case Const.NEW -> {
                return new NEW(codeStream);
            }
            case Const.NEWARRAY -> {
                return new NEWARRAY(codeStream);
            }
            case Const.ANEWARRAY -> {
                return new ANEWARRAY(codeStream);
            }
            case Const.ARRAYLENGTH -> {
                return new ARRAYLENGTH(codeStream);
            }
            case Const.ATHROW -> {
                return new ATHROW(codeStream);
            }
            case Const.CHECKCAST -> {
                return new CHECKCAST(codeStream);
            }
            case Const.INSTANCEOF -> {
                return new INSTANCEOF(codeStream);
            }
            case Const.MONITORENTER -> {
                return new MONITORENTER(codeStream);
            }
            case Const.MONITOREXIT -> {
                return new MONITOREXIT(codeStream);
            }
            case Const.WIDE -> {
                return new WIDE(codeStream);
            }
            case Const.MULTIANEWARRAY -> {
                 return new MULTIANEWARRAY(codeStream);
            }
            case Const.IFNULL -> {
                return new IFNULL(codeStream);
            }
            case Const.IFNONNULL -> {
                return new IFNONNULL(codeStream);
            }
            case Const.GOTO_W -> {
                return new GOTO_W(codeStream);
            }
            case Const.JSR_W -> {
                return new JSR_W(codeStream);
            }
            default -> throw new Error("Unknown JavaVM opcode " + opcode);
        }
    }

}
