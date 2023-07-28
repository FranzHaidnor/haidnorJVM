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
* 实现了 99% 的 JVM 字节码指令。参照 JVM 字节码规范实现 [The Java Virtual Machine Instruction Set](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html)
* 支持算数运算符 (`+`,`-`,`*`,`^`,`%`,`++`,`--`)
* 支持关系运算符 (`==`,`!=`,`>`,`<`,`>=`,`<=`)
* 支持位运算符 (`&`,`|`,`^`,`~`,`<<`,`>>`,`>>>`)
* 支持赋值运算符 (`=`,`+=`,`-=`,`*=`,`%=`,`<<=`,`>>=`,`&=`,`^=`,`|=`)
* 支持 instanceof 运算符
* 支持循环结构代码 (`while`,`do...while`,`for`,`foreach`)
* 支持条件结构代码 (`if`,`if...else`,`if...else if`)
* 支出创建自定义类
* 支持创建对象、访问对象
* 支持抽象类
* 支持多态继承、接口
* 支持访问静态方法
* 支持访问对象方法
* 支持 JDK 中自带的 Java 类
* 支持反射
* 支持异常
* 枚举 (开发中...)
* switch 语法 (开发中...)
* lambda 表达式 (开发中...)

# 局限性
* 不支持多线程
* 不支持多维数组
* 暂无双亲委派机制实现
* 无垃圾收集器实现。垃圾回收依靠宿主 JVM

# 快速体验
## 你需要准备什么
1. 集成开发环境 (IDE)。你可以选择包括 IntelliJ IDEA、Visual Studio Code 或 Eclipse 等等
2. JDK 17。并配置 JAVA_HOME
3. JDK 8。haidnorJVM 的主要目标是运行 Java8 本版的字节码文件。(但 haidnorJVM 没有强制要求字节码文件是 Java8 版本)
4. Maven

## 配置 haidnorJVM
### 配置日志输出级别
在 `resources\simplelogger.properties` 文件中修改日志输出级别，一般使用 `debug`、`info`

* 配置 info 级别将不会看到任何 haidnorJVM 内部运行信息
* 配置 debug 级别下运行将会非常友好的输出 JVM 正在执行的栈信息
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
每一个 `匚` 结构图形，都表示一个 JVM 线程栈中的栈帧
![](/readme/20230721204333.png)

### 配置 rt.jar 路径
修改 `haidnorJVM.properties` 文件中的内容。配置 rt.jar 的绝对路径，例如`rt.jar=D:/Program Files/Java/jdk1.8.0_361/jre/lib/rt.jar`

## 运行单元测试用例
在 IDE 中打开项目中 test 目录下的 `haidnor.jvm.test.TestJVM.java` 文件。 这是 haidnorJVM 的主要测试类, 里面的测试方法可以解析加载运行 .class 字节码文件。
```java
public class TestJVM {
   /**
    *  haidnorJVM 会加载 HelloWorld.java 在 target 目录下的编译后的字节码文件，然后运行其中的 `main(String[] args)` 方法。
    *  你可以使用打断点的方式看到 haidnorJVM 是如何解释运行 Java 字节码的。
    *  值得注意的是，这种方式编译运行的字节码文件是基于 java17 版本的。
    */
   @Test
   public void test() {
      runMainClass(HelloWorld.class);
   }
}
```

## 运行 .class 文件
1. 使用 maven 命令将 haidnorJVM 编译打包，得到 `haidnorJVM.jar` 文件
2. 编写一个简单的程序，例如以下代码
```java
public class HelloWorld {
   public static void main(String[] args) {
     System.out.println("HelloWorld");
   }
}
```
3. 编译代码，得到 HelloWorld.class 文件 （推荐使用 JDK8 进行编译）
4. 使用 haidnorJVM 运行程序。执行命令 `java -jar haidnorJVM.jar -class R:\HelloWorld.class`。注意! 需要 class 文件的绝对路径

## 运行 .jar 文件
1. 使用 maven 命令将 haidnorJVM 编译打包，得到 `haidnorJVM.jar` 文件
2. 编写一个 java 项目编译打包成 .jar 文件，例如 demo.jar。要求 .jar 文件中的 META-INF/MANIFEST.MF 文件内有 `Main-Class` 属性 (含有 `public static void main(String[] args)` 方法的主类信息)
3. 使用 haidnorJVM 运行程序。执行命令 `java -jar haidnorJVM.jar -class R:\demo.jar`。注意! 需要 jar 文件的绝对路径

# 存在的问题
由于 haidnorJVM 目前运行 JDK 自带的类是使用反射解决的，因此 haidnorJVM 使用 JDK17 运行部分 JDK 自带的类时会存在一些问题，例如运行以下代码将会抛出异常
```java
public class Demo {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        list.add(6);
    }
}
```

```
java.lang.reflect.InaccessibleObjectException: Unable to make public boolean java.util.ImmutableCollections$AbstractImmutableCollection.add(java.lang.Object) accessible: module java.base does not "opens java.util" to unnamed module @18769467

	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354)
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:297)
	at java.base/java.lang.reflect.Method.checkCanSetAccessible(Method.java:199)
	at java.base/java.lang.reflect.Method.setAccessible(Method.java:193)
```
它表示尝试通过反射来访问一个方法或字段，但该方法或字段的可访问性限制导致无法访问。  

这个限制通常是由于 Java 模块系统引起的。模块系统允许将代码划分为独立的模块，
并控制模块之间的访问权限。以上异常的原因是 module java.base does not "opens java.util" to unnamed module，也就是说 java.base 模块没有向未命名模块开放 java.util 包

**解决方法：**  
启动 haidnorJVM 时添加 JVM 参数 `--add-opens java.base/java.util=ALL-UNNAMED` 绕过访问性限制

# 联系作者
![](/readme/20230721181408.png )  
微信号: haidnor