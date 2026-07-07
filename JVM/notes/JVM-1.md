本笔记主要通过[尚硅谷JVM全套教程（详解java虚拟机）](https://www.bilibili.com/video/BV1PJ411n7xZ)为基础编成。

测试环境：Java8



课程介绍：

![image-20260704173559462](JVM-1.assets/image-20260704173559462.png)



# 一、JVM与Java体系结构

// TODO









# 二、类加载子系统

在本章节中会使用到`jclasslib`软件查看类文件里的具体信息。但是IDEA中提供该插件，可以提前在IDEA中安装：

![image-20260705160748148](JVM-1.assets/image-20260705160748148.png)

`jclasslib`插件的使用：点击`.class`文件，找到显示字节码选项。

![image-20260705161654990](JVM-1.assets/image-20260705161654990.png)





## 2.1 内存结构概述

**JVM架构图 - 简图：**

![image-20260704172709302](JVM-1.assets/image-20260704172709302.png)



**JVM架构图（英）：**

![第02章_JVM架构-英](JVM-1.assets/%E7%AC%AC02%E7%AB%A0_JVM%E6%9E%B6%E6%9E%84-%E8%8B%B1.jpg)



**JVM架构图（中）：**

![第02章_JVM架构-中](JVM-1.assets/%E7%AC%AC02%E7%AB%A0_JVM%E6%9E%B6%E6%9E%84-%E4%B8%AD.jpg)



## 2.2 类加载器与类的加载过程

### 2.2.1 类加载器

**类加载器子系统作用：**

![image-20260704173955580](JVM-1.assets/image-20260704173955580.png)

+ 类加载器子系统负责从文件系统或者网络中加载Class文件，class文件在文件头有特定的文件标识【CAFE BABE】。
+ ClassLoader只负责class文件的加载，至于它是否可以运行，则有Execution Engine（执行引擎）决定。
+ 加载的类信息存放于一块称为方法区的内存空间。除了类的信息外，方法区中还会存放运行时常量池信息，可能还包括字符串字面量和数字常量（这部分常量信息是Class文件中常量池部分的内存映射）。



**类加载器ClassLoader角色：**

<img src="JVM-1.assets/image-20260704174504244.png" alt="image-20260704174504244" style="zoom:80%;" />

1. Class file 存放在本地硬盘上，可以理解为设计师画在纸上的模板，而最终这个模板在执行的时候是要加载到JVM当中来，JVM可以通过这个类文件来实例化多个这样的事例。
2. Class file 加载到JVM中，被称为DNA元数据模板，放在方法区。
3. 在`.class`文件 -> JVM -> 最终称为元数据模板，这个过程需要一个运输工具，也就是类加载器ClassLoader。类加载器会以二进制流的方法将本地硬盘上的类文件加载到JVM中来。



### 2.2.2 类的加载过程：

<img src="JVM-1.assets/image-20260704174847136.png" alt="image-20260704174847136" style="zoom:80%;" />

![第02章_类的加载过程](JVM-1.assets/%E7%AC%AC02%E7%AB%A0_%E7%B1%BB%E7%9A%84%E5%8A%A0%E8%BD%BD%E8%BF%87%E7%A8%8B.jpg)

#### 2.2.2.1 类的加载过程 - Loading

加载（Loading）：

1. 通过一个类的全限定名获取定义此类的二进制字节流。
2. 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构。
3. 在内存中生成一个代表这个类的`java.lang.Class`对象，作为方法区这个类的各种数据的访问入口。



补充：加载`.class`文件的方式

> 加载类文件主要有以下几种方式：
>
> + 从本地系统中直接加载
> + 通过网络获取，典型场景:Web Applet
> + 从zip压缩包中读取，成为日后jar、war格式的基础
> + 运行时计算生成，使用最多的是：动态代理技术
> + 由其他文件生成，典型场景：JSP
> + 应用从专有数据库中提取.class文件，比较少见从
> + 加密文件中获取，典型的防Class文件被反编译的保护措施





#### 2.2.2.2 类的加载过程 - Linking

链接（Linking）- 示意图

![image-20260705104725812](JVM-1.assets/image-20260705104725812.png)

具体步骤：

1. **验证**（Verify）：

   + 目的在于确保Class文件的字节流中包含信息符合当前虚拟机要求，保证被加载类的正确性，不会危害虚拟机自身安全。

   + 主要包括四种验证，文件格式验证，元数据验证，字节码验证，符号引用验证。

     其中文件格式验证会检查`.class`文件是否有魔数`CAFE BABE`：

     ![image-20260705104954564](JVM-1.assets/image-20260705104954564.png)

     

2. **准备**（Prepare）：

   + 为类变量分配内存并且设置该类变量的默认初始值，即零值。

     ```java
     // 下面定义的变量：
     //   在prepare阶段默认初始化：a = 0
     // 	 在initial阶段才会被赋值：a = 1
     private static int a = 1;
     ```

   + 这里不包含用`final`修饰的`static`，因为`final`在编译的时候就会分配了，准备阶段会显式初始化；

   + 这里不会为实例变量分配初始化，类变量会分配在方法区中，而实例变量是会随着对象一起分配到Java堆中。

   

3. **解析**（Resolve）：

   + 将常量池内的符号引用转换为直接引用的过程。
   + 事实上，解析操作往往会伴随着JVM在执行完初始化之后再执行。
   + 符号引用就是一组符号来描述所引用的目标。符号引用的字面量形式明确定义在《java虚拟机规范》的Class文件格式中。直接引用就是直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄。
   + 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型等。对应常量池中的`CONSTANT_Class_info`, `CONSTANT_Fieldref_info`, `CONSTANT_Methodref_info`等。





#### 2.2.2.3 类的加载过程 - Initialization

**初始化**（Initialization）：

+ 初始化阶段就是执行类构造器方法`<clinit>()`的过程。
+ 此方法不需定义，是`javac`编译器自动收集类中的所有类变量的赋值动作和静态代码块中的语句合并而来。
+ 构造器方法中指令按语句在源文件中出现的顺序执行。
+ `<clinit>()`不同于类的构造器。（关联:构造器是虚拟机视角下的`<init>()`）
+ 若该类具有父类，JVM会保证子类的`<clinit>`()执行前，父类的`<clinit>()`已经执行完毕。
+ 虚拟机必须保证一个类的`<clinit>()`方法在多线程下被同步加锁。



**类构造器方法的一般解释事项：**

1. 通过`jclasslib`查看`<clinit>()`方法：

   ![image-20260705162540621](JVM-1.assets/image-20260705162540621.png)

2. `<clinit>()`方法会将显式初始化和静态代码块的赋值放在一起执行：

   ![image-20260705163043223](JVM-1.assets/image-20260705163043223.png)

3. 构造器方法中的指令会按语句在源文件中出现的顺序执行：

   代码中`number`变量定义语句在静态代码块中赋值的后面，主要是因为：

   + 在**linking**的**prepare**阶段，`number`分配时默认为0。
   + 在**Initialization**初始化阶段时，可以看到代码执行顺序由上到下，先赋值20再赋值10，这个可以通过`<clinit>()`方法可以看出。

   ![image-20260705163627825](JVM-1.assets/image-20260705163627825.png)

4. 变量定义语句之前无法被使用（非法的前向引用）：

   ![image-20260705164136217](JVM-1.assets/image-20260705164136217.png)

5. `<clinit>()`和`<init>()`方法区别：

   `<clinit>()`方法对所有类变量的赋值动作和静态代码块中的语句进行处理。

   `<init>()`方法是类的构造方法。（如果没写构造方法，则默认是jvm提供的无参构造方法）

   ![image-20260705170135159](JVM-1.assets/image-20260705170135159.png)

   <img src="JVM-1.assets/image-20260705170438234.png" alt="image-20260705170438234" style="zoom:80%;" />

6. 子类加载之前，需要先加载父类：

   ![image-20260705171203227](JVM-1.assets/image-20260705171203227.png)

7. 类文件在JVM虚拟机中只会被加载一次：

   ![image-20260705175419813](JVM-1.assets/image-20260705175419813.png)







## 2.3 类加载器分类

JVM支持两种类型的类加载器，分别为**引导类加载器**（BootstrapClassLoader）和**自定义类加载器** （User-Defined ClassLoader）。



从概念上来讲，**自定义类加载器**一般指的是程序中由开发人员自定义的一类类加载器，但是Java虚拟机规范却没有这么定义，而是**将所有派生于抽象类ClassLoader的类加载器都划分为自定义类加载器**。



无论类加载器的类型如何划分，在程序中我们最常见的类加载器始终只有3个，如下所示：

![image-20260706101429049](JVM-1.assets/image-20260706101429049.png)

这里的四者之间的关系是包含关系。不是上层下层，也不是子父类的继承关系。



**类加载器输出测试：**

```java
public class ClassLoaderTest {
    public static void main(String[] args) {

        // 获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);  // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 获取其上层:拓展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);     // sun.misc.Launcher$ExtClassLoader@1b6d3586

        // 获取其上层：获取不到引导类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);   // null


        // 对于用户自定义类来说：默认使用系统类加载器进行加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);       // sun.misc.Launcher$AppClassLoader@18b4aac2

        // String类使用引导类加载器进行加载 -> Java核心类库都是
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);       // null
    }
}
```



### 2.3.1 启动类加载器

启动类加载器（引导类加载器，Bootstrap ClassLoader）

+ 这个类加载使用C/C++语言实现的，嵌套在JVM内部。
+ 它用来加载Java的核心库（`JAVA HOME/jre/lib/rt.jar`、`resources.jar`或`sun.boot.class.path`路径下的内容），用于提供JVM自身需要的类。
+ 并不继承自`java.lang.ClassLoader`，没有父加载器。
+ 加载扩展类和应用程序类加载器，并指定为他们的父类加载器。
+ 出于安全考虑，Bootstrap启动类加载器只加载包名为`java`、`javax`、`sun`等开头的类



### 2.3.2 扩展类加载器

扩展类加载器（Extension ClassLoader）

+ Java语言编写, 由`sun.misc.Launcher$ExtClassLoader`实现。
+ 派生于`ClassLoader`类。
+ 父类加载器为启动类加载器。
+ 从`java.ext.dirs`系统属性所指定的目录中加载类库，或从JDK的安装目录的`jre/lib/ext`子目录（扩展目录）下加载类库。**如果用户创建的JAR放在此目录下，也会自动由扩展类加载器加载。**



### 2.3.3 应用程序类加载器

应用程序类加载器（系统类加载器，AppClassLoader）

+ java言编写, 由`sun.misc.Launcher$AppClassLoader`实现。
+ 派生于`ClassLoader`类。
+ 父类加载器为扩展类加载器。
+ 它负责加载环境变量`classpath`或系统属性`java.class.path`指定路径下的类库。
+ **该类加载是程序中默认的类加载器**，一般来说，Java应用的类都是由它来完成加载。
+ 通过`classLoader#getSystemclassLoader()`方法可以获取到该类加载器。





### 2.3.4 类加载器测试及注意事项

1. 获取引导类加载器能加载的api路径

   ```java
   public class ClassLoaderTest1 {
       public static void main(String[] args) {
           System.out.println("=====启动类加载器=====");
           // 获取BootstrapClassLoader能够加载的api的路径
           URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
           for (URL urL : urLs) {
               System.out.println(urL);
           }
       }
   }
   ```

   测试结果：

   ![image-20260707101726116](JVM-1.assets/image-20260707101726116.png)

   其中`rt.jar`加载的是java中的常用类库（如String等）。

   随便找一个加载进来的库里的类，看其类加载器是否是引导类加载器？

   ![image-20260707102305618](JVM-1.assets/image-20260707102305618.png)

   

2. 获取扩展类加载器能加载的api路径

   ```java
   System.out.println("=====扩展类加载器=====");
   String extDirs = System.getProperty("java.ext.dirs");
   for (String path : extDirs.split(";")) {
       System.out.println(path);
   }
   ```

   测试结果：

   ![image-20260707102835182](JVM-1.assets/image-20260707102835182.png)

   随便找一个加载进来的库里的类，看其类加载器是否是引导类加载器？

   ![image-20260707104241758](JVM-1.assets/image-20260707104241758.png)





### 2.3.5 用户自定义类加载器（了解）

用户自定义类加载器：

+ 在Java的日常应用程序开发中，类的加载几乎是由上述3种类加载器相互配合执行的，在必要时，我们还可以自定义类加载器，来定制类的加载方式。
+ 为什么要自定义类加载器？
  + 隔离加载类
  + 修改类加载的方式
  + 扩展加载源
  + 防止源码泄漏



**用户自定义类加载器实现步骤：**

1. 开发人员可以通过继承抽象类`java.lang.ClassLoader`类的方式，实现自己的类加载器，以满足一些特殊的需求。
2. 在JDK1.2之前，在自定义类加载器时，总会去继承`ClassLoader`类并重写`loadClass()`方法，从而实现自定义的类加载类。但是在JDK1.2之后已不再建议用户去覆盖`loadClass()`方法，而是建议把自定义的类加载逻辑写在`findClass()`方法中。
3. 在编写自定义类加载器时，如果没有太过于复杂的需求，可以直接继承`URLClassLoader`类，这样就可以避免自己去编写`findClass()`方法及其获取字节码流的方式，使自定义类加载器编写更加简洁。



用户自定义类加载器的简单框架：

```java
public class CustomClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if (result == null) {
                throw new FileNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        throw new ClassNotFoundException(name);
    }

    private byte[] getClassFromCustomPath(String name) {
        // 从自定义路径中加载指定类：细节略
        // 如果指定路径的字节码文件进行了加密，则需要在此方法中进行解密操作

        return null;
    }

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> clazz = Class.forName("One", true, customClassLoader);
            Object obj = clazz.newInstance();
            System.out.println(obj.getClass().getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```





## 2.4 ClassLoader的使用说明

`ClassLoader`类，它是一个抽象类，其后所有的类加载器都继承自`ClassLoader`（启动类加载器除外）。

![image-20260707164916970](JVM-1.assets/image-20260707164916970.png)



类加载器的继承关系：

<img src="JVM-1.assets/image-20260707165029846.png" alt="image-20260707165029846" style="zoom:67%;" />

`sun.misc.Launcher`是一个java虚拟机的入口应用，扩展类和应用类加载器都是`Launcher`类的内部类：

![image-20260707165607072](JVM-1.assets/image-20260707165607072.png)



**获取类加载器的方法：**

```java
public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            // 1. 获取当前类的ClassLoader
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);    // null

            // 2. 获取当前线程上下文的ClassLoader
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            System.out.println(contextClassLoader);

            // 3. 获取系统的ClassLoader
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            System.out.println(systemClassLoader);
            // 通过系统类加载器获取应用类加载器
            ClassLoader appClassLoader = systemClassLoader.getParent();
            System.out.println(appClassLoader);

            // 4. 获取调用者的ClassLoader(sql相关)
            // DriverManager.getCallerClassLoader();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
```





## 2.5 双亲委派机制





## 2.6 其他

