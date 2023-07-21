# haidnorJVM
使用 Java17 编写的 Java 虚拟机

## 意义  
1. 纸上得来终觉浅，绝知此事要躬行。只学习 JVM 机制和理论，很多时候任然觉得缺乏那种大彻大悟之感  
2. 使用简单的方式实现 JVM，用于学习理解 JVM 运行原理

## 主要技术选型
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Apache Commons BCEL](https://commons.apache.org/proper/commons-bcel/)
* [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)

# 实现功能与局限性
* 支持基本数据类型数学运算
* 支持循环、条件结构代码
* 支持创建对象，访问对象
* 支持多态
* 支持访问静态方法
* 不支持多线程
* 垃圾回收依靠宿主 JVM

# 快速体验
## 你需要准备什么
1. 集成开发环境 (IDE)。你可以选择包括 IntelliJ IDEA、Spring Tools、Visual Studio Code 或 Eclipse 等等
2. JDK 17。并配置 JAVA_HOME
3. JDK 8。强烈推荐! haidnorJVM 的主要目标是运行 Java8 本版的字节码文件。(haidnorVM 没有强制要求字节码文件是 Java8 版本)
4. Maven

## 配置 haidnorJVM
### 配置日志输出级别
修改 `simplelogger.properties` 文件中的内容。配置日志输出级别，一般使用 `debug`、`info`

debug 级别下运行将会非常友好的输出 JVM 正在执行的栈信息   
例如使用 haidnorJVM 执行以下代码
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

info 级别将不会看到任何 haidnorJVM 内部运行信息

### 配置 rt.jar
修改 `haidnorJVM.properties` 文件中的内容。配置 rt.jar 的绝对路径，例如`rt.jar=D:/Program Files/Java/jdk1.8.0_361/jre/lib/rt.jar`

## 运行单元测试用例
打开 test 目录下的 `haidnor.jvm.test.TestJVM` 类文件。 这是 haidnorJVM 功能的主要测试类。 里面的测试方法可以解析并加载一些class字节码文件。
```java
@Test
public void test_LSUB() throws Exception {
    runMainClass(LSUB.class);
}
```
例如以上代码会加载 LSUB.class 类在 target 目录下的字节码文件，然后使用 haidnorJVM 运行其中的 main 函数。你可以使用打断点的方式看到 haidnorJVM 是如何解释运行 Java 字节码的。   
值得注意的是，这种方式编译运行的字节码文件是基于 java17 版本的。

## 运行 .class 文件
1. 使用 maven 命令将项目编译打包，得到 `haidnorJVM-1.0.jar` 文件
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
开发中...

# 联系作者
微信账号: haidnor (请备注 "JVM")   
![](/readme/20230721181408.png )