# haidnorJVM
使用 Java17 编写的 Java 虚拟机

## 意义  
1. 纸上得来终觉浅，绝知此事要躬行。只学习 JVM 机制和理论，很多时候任然觉得缺乏那种大彻大悟之感  
2. 使用简单的方式实现 JVM，用于学习理解 JVM 运行原理

## 主要技术选型
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Apache Commons BCEL](https://commons.apache.org/proper/commons-bcel/)
* [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)

# 实现功能
* 支持基本数据类型数学运算
* 支持循环、条件结构代码
* 支持创建对象，访问对象
* 支持多态
* 支持反射
* 支持访问静态方法

# 局限性
* 不支持多线程
* 垃圾回收依靠宿主 JVM

# 快速体验
## 你需要准备什么
1. 集成开发环境 (IDE)。你可以选择包括 IntelliJ IDEA、Spring Tools、Visual Studio Code 或 Eclipse 等等
2. JDK 17。并配置 JAVA_HOME
3. JDK 8。haidnorJVM 的主要目标是运行 Java8 本版的字节码文件。(但 haidnorJVM 没有强制要求字节码文件是 Java8 版本)
4. Maven

## 配置 haidnorJVM
### 配置日志输出级别
在 `resources\simplelogger.properties` 文件中修改日志输出级别，一般使用 `debug`、`info`

* 配置 info 级别将不会看到任何 haidnorJVM 内部运行信息
* 配置 debug 级别下运行将会非常友好的输出 JVM 正在执行的栈信息 (例如使用 haidnorJVM 运行以下代码)
```java
public class Demo5 {

    public static void main(String[] args) {
        String str = method1("hello world");
        method1(str);
    }

    public static String method1(String s) {
        return method2(s);
    }

    public static String method2(String s) {
        return method3(s);
    }

    public static String method3(String s) {
        System.out.println(s);
        return "你好 世界";
    }
    
}
```
![](/readme/20230721204333.png)

### 配置 rt.jar
修改 `haidnorJVM.properties` 文件中的内容。配置 rt.jar 的绝对路径，例如`rt.jar=D:/Program Files/Java/jdk1.8.0_361/jre/lib/rt.jar`

## 运行单元测试用例
打开 test 目录下的 `haidnor.jvm.test.TestJVM` 类文件。 这是 haidnorJVM 功能的主要测试类。 里面的测试方法可以解析并加载一些class字节码文件。
```java
@Test
public void test() throws Exception {
    runMainClass(HelloWorld.class);
}
```
例如以上代码会加载 HelloWorld.class 类在 target 目录下的字节码文件，然后使用 haidnorJVM 运行其中的 main 函数。你可以使用打断点的方式看到 haidnorJVM 是如何解释运行 Java 字节码的。   
值得注意的是，这种方式编译运行的字节码文件是基于 java17 版本的。

## 运行 .class 文件
1. 使用 maven 命令将 haidnorJVM 编译打包，得到 `haidnorJVM-1.0.jar` 文件
2. 编写一个简单的程序，例如以下代码
   ```java
   public class HelloWorld {
      public static void main(String[] args) {
        System.out.println("HelloWorld");
      }
   }
   ```
3. 编译代码，得到 HelloWorld.class 文件。（推荐使用 JDK8 进行编译）
4. 使用 haidnorJVM 运行程序。执行命令 `java -jar haidnorJVM-1.0-SNAPSHOT.jar -class R:\HelloWorld.class`。注意需要 class 文件的绝对路径

## 运行 .jar 文件
1. 使用 maven 命令将 haidnorJVM 编译打包，得到 `haidnorJVM-1.0.jar` 文件
2. 编写一个 java 项目编译打包成 .jar 文件，例如 demo.jar。要求 .jar 文件中的 META-INF/MANIFEST.MF 文件内有 `Main-Class` 属性 (含有 public static void main(String[] args) 方法的主类信息)
3. 使用 haidnorJVM 运行程序。执行命令 `java -jar haidnorJVM-1.0-SNAPSHOT.jar -class R:\demo.jar`。注意需要 jar 文件的绝对路径

# 已实现的 JVM 指令
```java
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
                throw new Error("Not support JavaVM opcode DUP2_X1");
            }
            case Const.DUP2_X2 -> {
                throw new Error("Not support JavaVM opcode DUP2_X2");
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
                throw new Error("Not support JavaVM opcode TABLESWITCH");
            }
            case Const.LOOKUPSWITCH -> {
                throw new Error("Not support JavaVM opcode LOOKUPSWITCH");
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
                throw new Error("Not support JavaVM opcode INVOKEINTERFACE");
            }
            case Const.INVOKEDYNAMIC -> {
                throw new Error("Not support JavaVM opcode INVOKEDYNAMIC");
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
                throw new Error("Not support JavaVM opcode ATHROW");
            }
            case Const.CHECKCAST -> {
                return new CHECKCAST(codeStream);
            }
            case Const.INSTANCEOF -> {
                return new INSTANCEOF(codeStream);
            }
            case Const.MONITORENTER -> {
                throw new Error("Not support JavaVM opcode MONITORENTER");
            }
            case Const.MONITOREXIT -> {
                throw new Error("Not support JavaVM opcode MONITOREXIT");
            }
            case Const.WIDE -> {
                throw new Error("Not support JavaVM opcode WIDE");
            }
            case Const.MULTIANEWARRAY -> {
                throw new Error("Not support JavaVM opcode MULTIANEWARRAY");
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
```

# 联系作者
微信账号: haidnor (请备注 "JVM")   
![](/readme/20230721181408.png )