# 第一章节 Spring启示录

## 引入

### 1. 环境配置

该配置环境为 Spring6，Java17，首先先创建一个项目。

1. 打开idea，创建一个空项目。

   ![image-20251026141850392](Spring6.assets/image-20251026141850392.png)

2. 配置Maven环境信息（我们使用默认捆绑的maven）。

   ![image-20251026141928072](Spring6.assets/image-20251026141928072.png)

3. 在空项目下创建maven模块。

   ![image-20251026142338836](Spring6.assets/image-20251026142338836.png)



### 2. 创建web项目

1. 持久层

   ![image-20251026145640768](Spring6.assets/image-20251026145640768.png)

2. 业务层

   ![image-20251026145703305](Spring6.assets/image-20251026145703305.png)

3. 表现层

   ![image-20251026145726834](Spring6.assets/image-20251026145726834.png)



### 3. 问题

如果需要升级成ORACLE数据库，对功能进行扩展的时候，不仅需要新增一个ORACLE的持久层，还要修改UserService业务层的代码。

这样一来就违背了OCP开闭原则。

![image-20251026155552464](Spring6.assets/image-20251026155552464.png)



## 1.  OCP开闭原则

### 1.1 什么是OCP?

OCP是软件七大开发原则当中最基本的一个原则：`开闭原则`

+ 对什么开？对**扩展**开放。
+ 对什么闭？对**修改**关闭。



OCP原则是最核心的，最基本的，其他的六个原则都是为这个原则服务的。



### 1.2 OCP开闭原则的核心
只要你在扩展系统功能的时候，没有修改以前写好的代码，那么你就是符合OCP原则的。

反之，如果在扩展系统功能的时候，你修改了之前的代码，那么这个设计是失败的，违背OCP原则。

当进行系统功能扩展的时候，如果动了之前稳定的程序，修改了之前的程序，之前所有程序都需要进行重新测试。这是不想看到的，因为非常麻烦。导致以上问题的主要原因是：代码和代码之间的耦合度太高。如下图所示：

![image.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1663658802926-4c783887-3bd3-4a35-b32a-b2cd57d0061c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_18%2Ctext_6ICB5p2c%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

可以很明显的看出，**上层**是依赖**下层**的。`UserController`依赖`UserServiceImpl`，而`UserServiceImpl`依赖`UserDaoImplForMySQL`，这样就会导致**下面只要改动**，**上面必然会受牵连（跟着也会改）**，所谓牵一发而动全身。这样也就同时违背了另一个开发原则：**依赖倒置原则**。





## 1.2 依赖倒置原则DIP

**依赖倒置原则**(Dependence Inversion Principle)，简称DIP，主要倡导面向抽象编程，面向接口编程，不要面向具体编程，让上层不再依赖下层，下面改动了，上面的代码不会受到牵连。这样可以大大**降低程序的耦合度**，耦合度低了，扩展力就强了，同时代码复用性也会增强。（**软件七大开发原则都是在为解耦合服务**）

你可能会说，上面的代码已经面向接口编程了呀：

![image.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1663663167652-73de3acd-61de-4f32-8a78-6c7698e910d3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6ICB5p2c%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

确实已经面向接口编程了，但对象的创建是：new UserDaoImplForOracle()显然并没有完全面向接口编程，还是使用到了具体的接口实现类。什么叫做完全面向接口编程？什么叫做完全符合依赖倒置原则呢？请看以下代码：

![image.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1663663356201-4e57a395-503b-41ec-b98a-c3cd38f9279a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6ICB5p2c%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

如果代码是这样编写的，才算是完全面向接口编程，才符合依赖倒置原则。那你可能会问，这样userDao是null，在执行的时候就会出现空指针异常呀。你说的有道理，确实是这样的，所以我们要解决这个问题。解决空指针异常的问题，其实就是解决两个核心的问题：

- 第一个问题：谁来负责对象的创建。【也就是说谁来：`new UserDaoImplForOracle()`/`new UserDaoImplForMySQL()`】
- 第二个问题：谁来负责把创建的对象赋到这个属性上。【也就是说谁来把上面创建的对象赋给`userDao`属性】

如果我们把以上两个核心问题解决了，就可以做到既符合OCP开闭原则，又符合依赖倒置原则。



很荣幸的通知你：Spring框架可以做到。

在Spring框架中，它可以帮助我们new对象，并且它还可以将new出来的对象赋到属性上。换句话说，Spring框架可以帮助我们创建对象，并且可以帮助我们维护对象和对象之间的关系。比如：

![image.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1663664672011-b1f5c534-5c8b-412b-adb3-f7c60a3ab359.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6ICB5p2c%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fformat%2Cwebp)

Spring可以new出来`UserDaoImplForMySQL`对象，也可以new出来`UserDaoImplForOracle`对象，并且还可以让new出来的dao对象和service对象产生关系（产生关系其实本质上就是给属性赋值）。

很显然，这种方式是将对象的创建权/管理权交出去了，不再使用硬编码的方式了。同时也把对象关系的管理权交出去了，也不再使用硬编码的方式了。像这种把对象的创建权交出去，把对象关系的管理权交出去，被称为**控制反转**。



## 1.3 控制反转IoC

**控制反转**（Inversion of Control，缩写为IoC），是面向对象编程中的一种设计思想，可以用来降低代码之间的耦合度，符合依赖倒置原则。

控制反转的核心是：**将对象的创建权交出去，将对象和对象之间关系的管理权交出去，由第三方容器来负责创建与维护**。



IoC可以认为是一种**全新的设计模式**，但是理论和时间成熟相对较晚，并没有包含在GoF中。（GoF指的是23种设计模式）

而Spring框架就是一个实现了IoC思想的框架。



**Spring框架：**

+ Spring实现了控制反转IoC这种思想

  + Spring框架可以创建（new）对象。
  + Spring框架可以维护对象和对象之间的关系。

+ Spring是一个实现了IoC思想的容器

+ 控制反转是一种设计思想，有很多种实现方式，其中比较重要的是：**依赖注入**（Dependency Injection，简称DI）

  通常，依赖注入的实现又包括两种方式：

  - set方法注入

  - 构造方法注入

  > 依赖注入中，“依赖”和“注入”是什么意思？
  >
  > + 依赖：A对象和B对象的关系。
  > + 注入：是一种手段，通过这种手段，可以让A对象和B对象产生关系。



# 第二章节 Spring概述

## 2.1 Spring概述

来自百度百科

>  Spring是一个开源框架，它由Rod Johnson创建。它是为了解决企业应用开发的复杂性而创建的。
>
> 从简单性、可测试性和松耦合的角度而言，任何Java应用都可以从Spring中受益。
>
> + **Spring是一个轻量级的控制反转(IoC)和面向切面(AOP)的容器框架。**
> + **Spring最初的出现是为了解决EJB臃肿的设计，以及难以测试等问题。**
> + **Spring为简化开发而生，让程序员只需关注核心业务的实现，尽可能的不再关注非业务逻辑代码（事务控制，安全日志等）。**



## 2.2 Spring八大模块

注意：Spring5版本之后是8个模块。在Spring5中新增了WebFlux模块。

![image.png](Spring6.assets/1663726169861-b5acb757-17e0-4d3d-a811-400eb7edd1b3.png)

 

1. **Spring Core模块**

   这是Spring框架最基础的部分，它提供了依赖注入（DependencyInjection）特征来实现容器对Bean的管理。核心容器的主要组件是 BeanFactory，BeanFactory是工厂模式的一个实现，是任何Spring应用的核心。它使用IoC将应用配置和依赖从实际的应用代码中分离出来。

2. **Spring Context模块**

   如果说核心模块中的BeanFactory使Spring成为容器的话，那么上下文模块就是Spring成为框架的原因。

   这个模块扩展了BeanFactory，增加了对国际化（I18N）消息、事件传播、验证的支持。另外提供了许多企业服务，例如电子邮件、JNDI访问、EJB集成、远程以及时序调度（scheduling）服务。也包括了对模版框架例如Velocity和FreeMarker集成的支持。

3. **Spring AOP模块**

   Spring在它的AOP模块中提供了对面向切面编程的丰富支持，Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖组件，就可以将声明性事务管理集成到应用程序中，可以自定义拦截器、切点、日志等操作。

4. **Spring DAO模块**

   提供了一个JDBC的抽象层和异常层次结构，消除了烦琐的JDBC编码和数据库厂商特有的错误代码解析，用于简化JDBC。

5. **Spring ORM模块**

   Spring提供了ORM模块。Spring并不试图实现它自己的ORM解决方案，而是为几种流行的ORM框架提供了集成方案，包括Hibernate、JDO和iBATIS SQL映射，这些都遵从 Spring 的通用事务和 DAO 异常层次结构。

6. **Spring Web MVC模块**

   Spring为构建Web应用提供了一个功能全面的MVC框架。虽然Spring可以很容易地与其它MVC框架集成，例如Struts，但Spring的MVC框架使用IoC对控制逻辑和业务对象提供了完全的分离。

7. **Spring WebFlux模块**

   Spring Framework 中包含的原始 Web 框架 Spring Web MVC 是专门为 Servlet API 和 Servlet 容器构建的。反应式堆栈 Web 框架 Spring WebFlux 是在 5.0 版的后期添加的。它是完全非阻塞的，支持反应式流(Reactive Stream)背压，并在Netty，Undertow和Servlet 3.1+容器等服务器上运行。

   ![image.png](Spring6.assets/1663740062570-1823e8ac-1794-4590-87de-87e4a139a722.png)

8. **Spring Web模块**

   Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文，提供了Spring和其它Web框架的集成，比如Struts、WebWork。还提供了一些面向服务支持，例如：实现文件上传的multipart请求。



## 2.3 Spring特点

1. **轻量**

   + 从大小与开销两方面而言Spring都是轻量的。完整的Spring框架可以在一个大小只有1MB多的JAR文件里发布。并且Spring所需的处理开销也是微不足道的。

   + Spring是非侵入式的：Spring应用中的对象不依赖于Spring的特定类。

     > 侵入式会依赖其他特定类，不利于单元测试。

2. **控制反转**

   ​	Spring通过一种称作控制反转（IoC）的技术促进了松耦合。当应用了IoC，一个对象依赖的其它对象会通过被动的方式传递进来，而不是这个对象自己创建或者查找依赖对象。你可以认为IoC与JNDI相反——不是对象从容器中查找依赖，而是容器在对象初始化时不等对象请求就主动将依赖传递给它。

3. **面向切面**

   ​	Spring提供了面向切面编程的丰富支持，允许通过分离应用的业务逻辑与系统级服务（例如审计（auditing）和事务（transaction）管理）进行内聚性的开发。应用对象只实现它们应该做的——完成业务逻辑——仅此而已。它们并不负责（甚至是意识）其它的系统级关注点，例如日志或事务支持。

4. **容器**

   ​	Spring包含并管理应用对象的配置和生命周期，在这个意义上它是一种容器，你可以配置你的每个bean如何被创建——基于一个可配置原型（prototype），你的bean可以创建一个单独的实例或者每次需要时都生成一个新的实例——以及它们是如何相互关联的。然而，Spring不应该被混同于传统的重量级的EJB容器，它们经常是庞大与笨重的，难以使用。

5. **框架**

   ​	Spring可以将简单的组件配置、组合成为复杂的应用。在Spring中，应用对象被声明式地组合，典型地是在一个XML文件里。Spring也提供了很多基础功能（事务管理、持久化框架集成等等），将应用逻辑的开发留给了你。



所有Spring的这些特征使你能够编写更干净、更可管理、并且更易于测试的代码。它们也为Spring中的各种模块提供了基础支持。





## 2.4 本教程软件版本

- IDEA工具：2022.1.4
- JDK：Java17**（Spring6要求JDK最低版本是Java17）**
- Maven：3.8.6
- Spring：6.0.0-M2
- JUnit：4.13.2



# 第三章节 Spring的入门程序

## 3.1 Spring的下载

官网地址：https://spring.io/

Spring包下载：[JFrog](https://repo.spring.io/ui/repos/tree/General/plugins-release/org/springframework/spring)

![image-20251101114018822](Spring6.assets/image-20251101114018822.png)



注册后下载dist.zip文件，并解压：

![image.png](Spring6.assets/1663753313903-f48e2caf-73f1-4aaf-a503-e275795e67ba.png)

> 文档说明：
>
> + docs：spring框架的API帮助文档
> + libs：spring框架的jar文件（**用spring框架就是用这些jar包**）
> + schema：spring框架的XML配置文件相关的约束文件



## 3.2 Spring的jar文件

以Spring 5.3.9为例，打开`lib`目录，能看到很多jar包：

![image.png](Spring6.assets/1663809473261-c5c10c35-7407-44d4-81da-8f0baa236179.png)

+ spring-core-5.3.9.jar：字节码（**这个是支撑程序运行的jar包**）

+ spring-core-5.3.9-javadoc.jar：代码中的注释

+ spring-core-5.3.9-sources.jar：源码



Spring框架下的jar包：

|JAR文件| 描述  |
| -------------------------------- | --------------------------------- |
| spring-aop-5.3.9.jar             | **这个jar 文件包含在应用中使用Spring 的AOP 特性时所需的类**  |
| spring-aspects-5.3.9.jar         | **提供对AspectJ的支持，以便可以方便的将面向切面的功能集成进IDE中** |
| spring-beans-5.3.9.jar           | **这个jar 文件是所有应用都要用到的，它包含访问配置文件、创建和管理bean 以及进行Inversion ofControl / Dependency Injection（IoC/DI）操作相关的所有类。如果应用只需基本的IoC/DI 支持，引入spring-core.jar 及spring-beans.jar 文件就可以了。** |
| spring-context-5.3.9.jar         | **这个jar 文件为Spring 核心提供了大量扩展。可以找到使用Spring ApplicationContext特性时所需的全部类，JDNI 所需的全部类，instrumentation组件以及校验Validation 方面的相关类。** |
| spring-context-indexer-5.3.9.jar | 虽然类路径扫描非常快，但是Spring内部存在大量的类，添加此依赖，可以通过在编译时创建候选对象的静态列表来提高大型应用程序的启动性能。 |
| spring-context-support-5.3.9.jar | 用来提供Spring上下文的一些扩展模块,例如实现邮件服务、视图解析、缓存、定时任务调度等 |
| spring-core-5.3.9.jar            | **Spring 框架基本的核心工具类。Spring 其它组件要都要使用到这个包里的类，是其它组件的基本核心，当然你也可以在自己的应用系统中使用这些工具类。** |
| spring-expression-5.3.9.jar      | Spring表达式语言。                                           |
| spring-instrument-5.3.9.jar      | Spring3.0对服务器的代理接口。                                |
| spring-jcl-5.3.9.jar             | Spring的日志模块。JCL，全称为"Jakarta Commons Logging"，也可称为"Apache Commons Logging"。 |
| spring-jdbc-5.3.9.jar            | **Spring对JDBC的支持。**                                     |
| spring-jms-5.3.9.jar             | 这个jar包提供了对JMS 1.0.2/1.1的支持类。JMS是Java消息服务。属于JavaEE规范之一。 |
| spring-messaging-5.3.9.jar       | 为集成messaging api和消息协议提供支持                        |
| spring-orm-5.3.9.jar             | **Spring集成ORM框架的支持，比如集成hibernate，mybatis等。**  |
| spring-oxm-5.3.9.jar             | 为主流O/X Mapping组件提供了统一层抽象和封装，OXM是Object Xml Mapping。对象和XML之间的相互转换。 |
| spring-r2dbc-5.3.9.jar           | Reactive Relational Database Connectivity (关系型数据库的响应式连接) 的缩写。这个jar文件是Spring对r2dbc的支持。 |
| spring-test-5.3.9.jar            | 对Junit等测试框架的简单封装。                                |
| spring-tx-5.3.9.jar              | **为JDBC、Hibernate、JDO、JPA、Beans等提供的一致的声明式和编程式事务管理支持。** |
| spring-web-5.3.9.jar             | **Spring集成MVC框架的支持，比如集成Struts等。**              |
| spring-webflux-5.3.9.jar         | **WebFlux是 Spring5 添加的新模块，用于 web 的开发，功能和 SpringMVC 类似的，Webflux 使用当前一种比较流程响应式编程出现的框架。** |
| spring-webmvc-5.3.9.jar          | **SpringMVC框架的类库**                                      |
| spring-websocket-5.3.9.jar       | Spring集成WebSocket框架时使用                                |



**注意：**

**如果你只是想用Spring的IoC功能，仅需要引入：spring-context即可。将这个jar包添加到classpath当中。**

**如果采用maven只需要引入context的依赖即可。**

```xml
<!--Spring6的正式版发布之前，这个仓库地址是需要的-->
<repositories>
  <repository>
    <id>repository.spring.milestone</id>
    <name>Spring Milestone Repository</name>
    <url>https://repo.spring.io/milestone</url>
  </repository>
</repositories>

<dependencies>
  <!--spring context依赖：使用的是6.0.0-M2里程碑版-->
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>6.0.0-M2</version>
  </dependency>
</dependencies>
```



## 3.3 第一个Spring程序

做笔记是已经更新到spring7.0.x里程碑版本，但仍使用视频讲解的6.0.x里程碑版本。



**创建项目并配置依赖：**

![image-20251102113216075](Spring6.assets/image-20251102113216075.png)

1. 在Spring6项目下创建新的模块

   ![image-20251102145045633](Spring6.assets/image-20251102145045633.png)

   

2. 配置pom文件中添加spring仓库及依赖

   ![image-20251102113524714](Spring6.assets/image-20251102113524714.png)

   > 注意：
   >
   > 1. 引入spring-context之后，会将其基础依赖一起引入；如果还需要使用spring的jdbc等其他功能模块，则需要再次添加相应的依赖。
   > 2. 6.0.2（RC）版本之后可以不用配置里程碑仓库，可以直接在依赖里填写版本号。

3. 配置pom文件中的测试工具依赖

   ![image-20251102145643365](Spring6.assets/image-20251102145643365.png)

4. pom重加载后，spring-context依赖会关联其他依赖：

   ![image-20251102173619455](Spring6.assets/image-20251102173619455.png)



**定义bean类并编写xml配置文件**

1. 定义Bean：User

   ![image-20251102174919322](Spring6.assets/image-20251102174919322.png)

2. 编写spring的配置文件：spring.xml

   ![image-20251102175214300](Spring6.assets/image-20251102175214300.png)

   ![image-20251102175303140](Spring6.assets/image-20251102175303140.png)

   

   **写一个单元测试：**

   在测试目录下新建测试类，并运行测试spring

   ![image-20251102175932034](Spring6.assets/image-20251102175932034.png)



## 3.4 第一个Spring程序详细剖析

1. **bean标签的id属性可以重复嘛？**

   不可以，在spring的配置文件中id是不能重名。

   

2. **底层是怎么创建对象的，是通过反射机制调用无参数构造方法吗？**

   spring是通过调用类的无参数构造方法来创建对象的，所以要想让spring给你创建对象，必须保证无参数构造方法是存在的。

   

   Spring是如何创建对象的呢？原理是什么？

   ```java
   // dom4j解析beans.xml文件，从中获取class的全限定类名
   // 通过反射机制调用无参数构造方法创建对象
   Class clazz = Class.forName("com.powernode.spring6.bean.User");
   Object obj = clazz.newInstance();
   ```

3. **把创建好的对象存储到一个什么样的数据结构当中了呢？**

   ![image.png](Spring6.assets/1663829973365-59ca2f4c-4d81-471f-8e4c-aa272f8c2b81.png)

   

4. **spring配置文件的名字必须叫做beans.xml吗？**

   可以自行定义名称。

   

5. **像这样的beans.xml文件可以有多个吗？**

   我们新建了一个`vip类`和`vip.xml`的配置文件，在启动Spring的代码中将`vip.xml`添加进去，这样我们就能从spring容器中获取到vip类。![image-20251105154714463](Spring6.assets/image-20251105154714463.png)

   从源码中也能看到，这个启动类方法的参数是个可变列表。

   ![image-20251105154959243](Spring6.assets/image-20251105154959243.png)

   

6. **在配置文件中配置的类必须是自定义的吗，可以使用JDK中的类吗，例如：java.util.Date？**

   可以。在spring配置文件中配置的bean可以任意类，只要这个类不是抽象的，并且提供了无参数构造方法。

   ![image-20251105171555375](Spring6.assets/image-20251105171555375.png)

   

7. **getBean()方法调用时，如果指定的id不存在会怎样？**

   直接报错。

   ![image-20251105171955023](Spring6.assets/image-20251105171955023.png)

   

8. **getBean()方法返回的类型是Object，如果访问子类的特有属性和方法时，还需要向下转型，有其它办法可以解决这个问题吗？**

   ![image-20251105172600540](Spring6.assets/image-20251105172600540.png)

   

9. **ClassPathXmlApplicationContext是从类路径中加载配置文件，如果没有在类路径当中，又应该如何加载配置文件呢？**

   没有在类路径中的话，需要使用`FileSystemXmlApplicationContext`类进行加载配置文件。

   这种方式较少用。一般都是将配置文件放到类路径当中，这样可移植性更强。

   ![image-20251105173247482](Spring6.assets/image-20251105173247482.png)

   

10. **ApplicationContext的超级父接口BeanFactory**

    + `ApplicationContext`接口的超级父接口是 `BeanFactory`。
    + `BeanFactory`是IoC容器的顶级接口。
    + Spring的IoC容器底层实际上使用了**工厂模式**。
    + Spring底层的IoC实现：`XML解析 + 工厂模式 + 反射机制`。

    ![image-20251105173743574](Spring6.assets/image-20251105173743574.png)

    

11. **创建Bean对象的时机**

    ```java
    @Test
    public void testBeginInitBean() {
        // 在执行下面这行代码的时候，就已经创建了Bean对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        
        // getBean()只是获取该Bean对象
        User user = applicationContext.getBean("userBean", User.class);
        System.out.println(user);
    }
    ```



## 3.5 Spring6启用Log4j2日志框架

从Spring5之后，Spring框架支持集成的日志框架是Log4j2.如何启用日志框架。

1. 引入Log4j2的依赖

   ```xml
   <!--log4j2的依赖-->
   <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-core</artifactId>
       <version>2.19.0</version>
   </dependency>
   <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-slf4j2-impl</artifactId>
       <version>2.19.0</version>
   </dependency>
   ```

   

2. 在类的根路径下提供log4j2.xml配置文件（文件名固定为：log4j2.xml，文件必须放到类根路径下。）

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   
   <configuration>
   
       <loggers>
           <!--
               level指定日志级别，从低到高的优先级：
                   ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
           -->
           <root level="DEBUG">
               <appender-ref ref="spring6log"/>
           </root>
       </loggers>
   
       <appenders>
           <!--输出日志信息到控制台-->
           <console name="spring6log" target="SYSTEM_OUT">
               <!--控制日志输出的格式-->
               <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss SSS} [%t] %-3level %logger{1024} - %msg%n"/>
           </console>
       </appenders>
   
   </configuration>
   ```

   

3. 使用日志框架

   ```java
   @Test
   public void testLogger() {
       // 如何使用 log4j2记录日志信息？
       // 1. 获取当前类FirstSpringTest类的日志记录器对象
       //  也就是说只要是记录FirstSpringTest类中代码执行记录的话，就输出相关日志信息
       Logger logger = LoggerFactory.getLogger(FirstSpringTest.class);
   
       // 2. 记录日志，根据不同的级别来输出日志
       logger.info("我是一条消息");
       logger.debug("我是一条调试消息");
       logger.error("我是一条错误消息");
   }
   ```





# 第四章节 Spring对IoC的实现

## 4.1 IoC控制反转

+ 控制反转是一种思想。
+ 控制反转是为了降低程序耦合度，提高程序扩展力，达到OCP原则，达到DIP原则。
+ 控制反转，反转的是什么？

- - 将对象的创建权利交出去，交给第三方容器负责。
  - 将对象和对象之间关系的维护权交出去，交给第三方容器负责。

- 控制反转这种思想如何实现呢？

- - DI（Dependency Injection）：依赖注入



## 4.2 依赖注入

依赖注入实现了控制反转的思想。

**Spring通过依赖注入的方式来完成Bean管理的。**

**Bean管理说的是：Bean对象的创建，以及Bean对象中属性的赋值（或者叫做Bean对象之间关系的维护）。**

依赖注入：

- 依赖指的是对象和对象之间的关联关系。
- 注入指的是一种数据传递行为，通过注入行为来让对象和对象产生关系。

依赖注入常见的实现方式包括两种：

- 第一种：set注入
- 第二种：构造注入





**准备工作：**

1.  新建模块：`spring6-002-dependency-injection`

   ![image-20251113174904560](Spring6.assets/image-20251113174904560.png)

   

2. 引入依赖，并写入配置文件

   **pom.xml**

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>cn.piggy</groupId>
       <artifactId>spring6-003-dependency-injection</artifactId>
       <version>1.0-SNAPSHOT</version>
       <packaging>jar</packaging>
   
       <!-- 配置多个仓库 -->
       <repositories>
           <!-- spring里程碑版本的仓库 -->
           <repository>
               <id>spring-milestones</id>
               <name>Spring Milestones</name>
               <url>https://repo.spring.io/milestone</url>
               <snapshots>
                   <enabled>false</enabled>
               </snapshots>
           </repository>
       </repositories>
   
       <!-- 依赖 -->
       <dependencies>
           <!--spring context依赖-->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>6.0.0-M2</version>
           </dependency>
   
           <!-- junit依赖 -->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.13.2</version>
               <scope>test</scope>
           </dependency>
   
           <!--log4j2的依赖-->
           <dependency>
               <groupId>org.apache.logging.log4j</groupId>
               <artifactId>log4j-core</artifactId>
               <version>2.19.0</version>
           </dependency>
           <dependency>
               <groupId>org.apache.logging.log4j</groupId>
               <artifactId>log4j-slf4j2-impl</artifactId>
               <version>2.19.0</version>
           </dependency>
       </dependencies>
   
       <properties>
           <maven.compiler.source>17</maven.compiler.source>
           <maven.compiler.target>17</maven.compiler.target>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       </properties>
   </project>
   ```
   **log4j2日志配置文件：**

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration>
       <loggers>
           <!--
               level指定日志级别，从低到高的优先级：
                   ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
           -->
           <root level="DEBUG">
               <appender-ref ref="spring6log"/>
           </root>
       </loggers>
   
       <appenders>
           <!--输出日志信息到控制台-->
           <console name="spring6log" target="SYSTEM_OUT">
               <!--控制日志输出的格式-->
               <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss SSS} [%t] %-3level %logger{1024} - %msg%n"/>
           </console>
       </appenders>
   </configuration>
   ```

   

3. 创建UserDao和UserService类

   ```java
   // UserDao.class
   package cn.piggy.spring6.dao;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   
   public class UserDao {
   
       public static final Logger logger = LoggerFactory.getLogger(UserDao.class);
   
       public void insert() {
           // System.out.println("数据库正在保存用户信息...");
           // 使用下log4j2日志框架
           logger.info("数据库正在保存用户信息...");
       }
   }
   ```

   ```java
   package cn.piggy.spring6.service;
   import cn.piggy.spring6.dao.UserDao;
   
   public class UserService {
   
       private UserDao userDao;
   
       public void saveUser() {
           // 保存用户信息到数据库
           userDao.insert();
       }
   }
   ```

   

4. 创建测试类

   ```java
   package cn.piggy.spring6.test;
   import cn.piggy.spring6.service.UserService;
   import org.junit.Test;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   public class SpringDITest {
   
       @Test
       public void testSetDI() {
           ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
           UserService userService = applicationContext.getBean("userServiceBean", UserService.class);
           userService.saveUser();
       }
   }
   ```



### 4.2.1 set注入

set注入，基于set方法实现的，底层会通过反射机制调用属性对应的set方法然后给属性赋值。这种方式要求属性必须对外提供set方法。

![image-20251114160714881](Spring6.assets/image-20251114160714881.png)

**实现原理：**

+ 通过property标签获取到属性名：`userDao`
+ 通过属性名推断出set方法名：`setUserDao`
+ 通过反射机制调用`setUserDao()`方法给属性赋值
+ `property`标签的name是属性名。
+ `property`标签的`ref`是要注入的bean对象的id。**(通过ref属性来完成bean的装配，这是bean最简单的一种装配方式。装配指的是：创建系统组件之间关联的动作)**



**总结：set注入的核心实现原理：通过反射机制调用set方法来给属性赋值，让两个对象之间产生关系。**



### 4.2.2 构造注入

核心原理：通过调用构造方法来给属性赋值。

1. 新建`VipDao`类和`CustomerService`类

   ```java
   // VipDao.class
   package cn.piggy.spring6.dao;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   
   public class VipDao {
       public static final Logger logger = LoggerFactory.getLogger(VipDao.class);
       public void insert() {
           logger.info("正在保存vip信息...");
       }
   }
   ```

   ```java
   // CustomerService.class
   package cn.piggy.spring6.service;
   import cn.piggy.spring6.dao.UserDao;
   import cn.piggy.spring6.dao.VipDao;
   
   public class CustomerService {
   
       private UserDao userDao;
       private VipDao vipDao;
   
       public CustomerService(UserDao userDao, VipDao vipDao) {
           this.userDao = userDao;
           this.vipDao = vipDao;
       }
   
       public void save() {
           userDao.insert();
           vipDao.insert();
       }
   }
   ```

   

2. 新建个测试方法

   ```java
   // SpringDITest.class
   
   public class SpringDITest {
   	
       //...
   
       @Test
       public void testConstructorDI() {
           ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
           CustomerService customerService = applicationContext.getBean("csBean", CustomerService.class);
           customerService.save();
       }
   }
   ```

   

3. 新建个spring配置文件 `beans.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="userDao" class="cn.piggy.spring6.dao.UserDao" />
       <bean id="vipDao" class="cn.piggy.spring6.dao.VipDao" />
       <bean id="csBean" class="cn.piggy.spring6.service.CustomerService" >
           <!--构造注入-->
           <!--制定构造方法的第一个参数，下标是0-->
           <constructor-arg index="0" ref="userDao" />
           <!--制定构造方法的第一个参数，下标是1-->
           <constructor-arg index="1" ref="vipDao" />
       </bean>
   </beans>
   ```

4. 测试

   <img src="Spring6.assets/image-20251114163816831.png" alt="image-20251114163816831" style="zoom:80%;" />

5. 实际上，构造方法默认的格式还有两种：根据参数名和自行匹配：

   ![image-20251114163958455](Spring6.assets/image-20251114163958455.png)



通过测试得知，通过构造方法注入的时候：

- 可以通过下标
- 可以通过参数名
- 也可以不指定下标和参数名，可以类型自动推断。

Spring在装配方面做的还是比较健壮的。



## 4.3 set注入专题

准备工作：

1. 新建`OrderDao`和`OrderService`类，以及测试程序

   ```java
   // OrderDao.class
   package cn.piggy.spring6.dao;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   
   public class OrderDao {
       private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);
   
       public void insert() {
           logger.info("订单正在生成...");
       }
   }
   ```

   ```java
   // OrderService.class
   package cn.piggy.spring6.service;
   import cn.piggy.spring6.dao.OrderDao;
   
   public class OrderService {
       private OrderDao orderDao;
   
       // 通过set方法给属性赋值
       public void setOrderDao(OrderDao orderDao) {
           this.orderDao = orderDao;
       }
   
       // 生成订单的业务方法
       public void generate() {
           orderDao.insert();
       }
   }
   ```

   ```java
   // SpringDITest.class
   public class SpringDITest {
       @Test
       public void testSetDI2() {
           ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
           OrderService orderService = applicationContext.getBean("orderServiceBean", OrderService.class);
           orderService.generate();
       }
       
       //...
   }
   ```

2. 新建新的spring配置文件`set-di.xml`，我们将在这个配置文件里做内部和外部Bean的配置测试。



### 4.3.1 注入外部Bean

外部Bean的特点：bean定义到外面，在property标签中使用ref属性进行注入。通常这种方式最为常用。

![image-20251115112737916](Spring6.assets/image-20251115112737916.png)



### 4.3.2 注入内部Bean

内部Bean的方式：在bean标签中嵌套bean标签。

![image-20251115112910208](Spring6.assets/image-20251115112910208.png)



### 4.3.3 注入简单类型

#### 4.3.3.1 简单类型注入演示

1. 创建bean包，创建`User`类

   ![image-20251115114350337](Spring6.assets/image-20251115114350337.png)

2. 编写配置文件`set-di`，给User对象进行set注入赋值

   ![image-20251115114435802](Spring6.assets/image-20251115114435802.png)

3. 编写测试程序测试

   ![image-20251115114517144](Spring6.assets/image-20251115114517144.png)



**需要特别注意：如果给简单类型赋值，使用value属性或value标签。而不是ref。**



#### 4.3.3.2简单类型的种类

在BeanUtils类中查询简单类型判断方法：

![image-20251115160925819](Spring6.assets/image-20251115160925819.png)



#### 4.3.3.3 简单类型测试

![image-20251115170351427](Spring6.assets/image-20251115170351427.png)

![image-20251115170417095](Spring6.assets/image-20251115170417095.png)



> 注意：对于Date类型来说，使用value引入必须要符合其默认的输出格式规范。
>
> ![image-20251115171750323](Spring6.assets/image-20251115171750323.png)



#### 4.3.3.4 JDBC数据源应用演示

![image-20251115173539338](Spring6.assets/image-20251115173539338.png)

![image-20251115173557437](Spring6.assets/image-20251115173557437.png)



### 4.3.4 级联属性赋值（了解）

在一个类中引用另一个类，比如在Student类中有其所属班级Clazz的属性。

**之前的做法：**

![image-20251116100309719](Spring6.assets/image-20251116100309719.png)

![image-20251116100344353](Spring6.assets/image-20251116100344353.png)

![image-20251116100357319](Spring6.assets/image-20251116100357319.png)



**使用级联属性赋值：**

![image-20251116101302548](Spring6.assets/image-20251116101302548.png)



注意点：

+ 使用级联属性赋值时，需要注意赋值顺序。
+ 级联属性必须提供getter方法。





### 4.3.5 注入数组

**简单类型数组：**

![image-20251116114144183](Spring6.assets/image-20251116114144183.png)



**非简单类型数组：**

![image-20251116114813544](Spring6.assets/image-20251116114813544.png)





**要点：**

- **如果数组中是简单类型，使用value标签。**
- **如果数组中是非简单类型，使用ref标签。**





### 4.3.6 - 4.3.7 注入List及Set集合

1. 创建`Person`类和spring配置文件`spring-collection.xml`

   ![image-20251117194729249](Spring6.assets/image-20251117194729249.png)

2. 编写测试程序，运行结果

   ![image-20251117194758853](Spring6.assets/image-20251117194758853.png)



**要点：**

+ 注入List集合的时候使用`<list>`标签，注入Set集合的时候使用`<set>`标签。
+ 如果List或Set集合中是简单类型使用`<value>`标签，反之使用`<ref>`标签。





### 4.3.8注入Map集合

新加Map对象`phones`，并配置赋值。

![image-20251117221952694](Spring6.assets/image-20251117221952694.png)



**要点：**

- **使用`<map>`标签**
- **如果key是简单类型，使用` key` 属性，反之使用 `key-ref` 属性。**
- **如果value是简单类型，使用` value `属性，反之使用 `value-ref `属性。**





### 4.3.9 注入Properties属性类对象

java.util.Properties继承java.util.Hashtable，所以Properties也是一个Map集合。

![image-20251117222154528](Spring6.assets/image-20251117222154528.png)



新加Properties对象`properties`，并配置赋值。

![image-20251117222339823](Spring6.assets/image-20251117222339823.png)



**要点：**

- **使用`<props>`标签嵌套`<prop>`标签完成。**
- key和value都只能是**String**类型。



### 4.3.10 注入null和空字符串

创建Cat类：

```java
public class Cat {

    private String name;

    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```



1. 注入null

   ![image-20251117224429697](Spring6.assets/image-20251117224429697.png)

   > 注意：
   >
   > + 不给属性注入，属性的默认是就是`null`。
   >
   > + 下列配置信息相当于给`name`属性赋值了一条字符串“null”，并不是真的null。
   >
   >   ```xml
   >   <property name="name" value="null"/>
   >   ```
   >
   > + 手动注入null可使用`<null/>`

2. 注入空字符串

   ![image-20251117224805142](Spring6.assets/image-20251117224805142.png)

   > 注意：
   >
   > + 下列配置信息相当于给`name`属性赋值了一条空字符串。
   >
   >   ```xml
   >   <property name="name" value=""/>
   >   ```
   >
   > + 手动注入空字符串可使用`<value/>`



### 4.3.11 注入的值中还有特殊符号

XML中有5个特殊字符，分别是：`<`、`>`、`'`、`"`、`&`。

以上5个特殊符号在XML中会被特殊对待，会被当做XML语法的一部分进行解析，如果这些特殊符号直接出现在注入的字符串当中，会报错。

![image-20251117230742567](Spring6.assets/image-20251117230742567.png)



**解决方案包括两种：**

- 第一种：特殊符号使用转义字符代替。
- 第二种：将含有特殊符号的字符串放到：`<![CDATA[]]> `当中。因为放在CDATA区中的数据不会被XML文件解析器解析。



5个特殊字符对应的转义字符分别是：

| **特殊字符** | **转义字符** |
| ------------ | ------------ |
| >            | `&gt;`       |
| <            | `&lt;`       |
| '            | `&apos;`     |
| "            | `&quot;`     |
| &            | `&amp;`      |



**演示：**

![image-20251117231058131](Spring6.assets/image-20251117231058131.png)





## 4.4 p命名空间注入

