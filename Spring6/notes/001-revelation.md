# 第一章节 - Spring启示录

## 引入

### 1. 环境配置

该配置环境为 Spring6，Java17，首先先创建一个项目。

1. 打开idea，创建一个空项目。

   ![image-20251026141850392](001-revelation.assets/image-20251026141850392.png)

2. 配置Maven环境信息（我们使用默认捆绑的maven）。

   ![image-20251026141928072](001-revelation.assets/image-20251026141928072.png)

3. 在空项目下创建maven模块。

   ![image-20251026142338836](001-revelation.assets/image-20251026142338836.png)



### 2. 创建web项目

1. 持久层

   ![image-20251026145640768](001-revelation.assets/image-20251026145640768.png)

2. 业务层

   ![image-20251026145703305](001-revelation.assets/image-20251026145703305.png)

3. 表现层

   ![image-20251026145726834](001-revelation.assets/image-20251026145726834.png)



### 3. 问题

如果需要升级成ORACLE数据库，对功能进行扩展的时候，不仅需要新增一个ORACLE的持久层，还要修改UserService业务层的代码。

这样一来就违背了OCP开闭原则。

![image-20251026155552464](001-revelation.assets/image-20251026155552464.png)



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

