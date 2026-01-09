# 一、MyBatis概述

## 1.1 框架

+ 在文献中看到的framework被翻译为框架。
+ Java常用框架：
  - SSM三大框架：Spring + SpringMVC + MyBatis
  - SpringBoot
  - SpringCloud
  - 等。。
+ 框架其实就是对通用代码的封装，提前写好了一堆接口和类，我们可以在做项目的时候直接引入这些接口和类（引入框架），基于这些现有的接口和类进行开发，可以大大提高开发效率。
+ 框架一般都以jar包的形式存在。(jar包中有class文件以及各种配置文件等。)
+ SSM三大框架的学习顺序：MyBatis、Spring、SpringMVC（仅仅是建议）



## 1.2 三层架构

![image-20260107164652801](mybatis.assets/image-20260107164652801.png)

+ 表现层（UI）：直接跟前端打交互（一是接收前端ajax请求，二是返回json数据给前端）
+ 业务逻辑层（BLL）：一是处理表现层转发过来的前端请求（也就是具体业务），二是将从持久层获取的数据返回到表现层。
+ 数据访问层（DAL）：直接操作数据库完成CRUD，并将获得的数据返回到上一层（也就是业务逻辑层）。
+ Java持久层框架：
  - MyBatis
  - Hibernate（实现了JPA规范）
  - jOOQ
  - Guzz
  - Spring Data（实现了JPA规范）
  - ActiveJDBC
  - ......



## 1.3 JDBC不足

+ 示例代码1：

```java
// ......
// sql语句写死在java程序中
String sql = "insert into t_user(id,idCard,username,password,birth,gender,email,city,street,zipcode,phone,grade) values(?,?,?,?,?,?,?,?,?,?,?,?)";
PreparedStatement ps = conn.prepareStatement(sql);
// 繁琐的赋值：思考一下，这种有规律的代码能不能通过反射机制来做自动化。
ps.setString(1, "1");
ps.setString(2, "123456789");
ps.setString(3, "zhangsan");
ps.setString(4, "123456");
ps.setString(5, "1980-10-11");
ps.setString(6, "男");
ps.setString(7, "zhangsan@126.com");
ps.setString(8, "北京");
ps.setString(9, "大兴区凉水河二街");
ps.setString(10, "1000000");
ps.setString(11, "16398574152");
ps.setString(12, "A");
// 执行SQL
int count = ps.executeUpdate();
// ......
```

+ 示例代码2：

```java
// ......
// sql语句写死在java程序中
String sql = "select id,idCard,username,password,birth,gender,email,city,street,zipcode,phone,grade from t_user";
PreparedStatement ps = conn.prepareStatement(sql);
ResultSet rs = ps.executeQuery();
List<User> userList = new ArrayList<>();
// 思考以下循环中的所有代码是否可以使用反射进行自动化封装。
while(rs.next()){
    // 获取数据
    String id = rs.getString("id");
    String idCard = rs.getString("idCard");
    String username = rs.getString("username");
    String password = rs.getString("password");
    String birth = rs.getString("birth");
    String gender = rs.getString("gender");
    String email = rs.getString("email");
    String city = rs.getString("city");
    String street = rs.getString("street");
    String zipcode = rs.getString("zipcode");
    String phone = rs.getString("phone");
    String grade = rs.getString("grade");
    // 创建对象
    User user = new User();
    // 给对象属性赋值
    user.setId(id);
    user.setIdCard(idCard);
    user.setUsername(username);
    user.setPassword(password);
    user.setBirth(birth);
    user.setGender(gender);
    user.setEmail(email);
    user.setCity(city);
    user.setStreet(street);
    user.setZipcode(zipcode);
    user.setPhone(phone);
    user.setGrade(grade);
    // 添加到集合
    userList.add(user);
}
// ......
```

+ JDBC不足：
  - SQL语句写死在Java程序中，不灵活。改SQL的话就要改动Java代码，这违背了开闭原则OCP。
  - 给占位符`?`传值的操作比较繁琐的。（能不能自动化？？？）
  - 将结果集封装成Java对象是繁琐的。(能不能自动化？？？)



## 1.4 了解MyBatis

MyBatis本质上就是对JDBC的封装，通过MyBatis完成CRUD操作。

MyBatis在三层架构中负责持久层的，属于**持久层框架**。



MyBatis的发展历程：<font style="color:#E8323C;">【引用百度百科】</font>

- MyBatis本是apache的一个开源项目iBatis，2010年这个项目由apache software foundation迁移到了google code，并且改名为MyBatis。2013年11月迁移到Github。
- iBATIS一词来源于“internet”和“abatis”的组合，是一个基于Java的持久层框架。iBATIS提供的持久层框架包括SQL Maps和Data Access Objects（DAOs）。



打开mybatis代码可以看到它的包结构中包含：ibatis。

![image-20260107174131266](mybatis.assets/image-20260107174131266.png)



**ORM 对象关系映射：**

- O（**Object**）：Java虚拟机中的Java对象

- R（**Relational**）：关系型数据库

- M（**Mapping**）：将Java虚拟机中的Java对象映射到数据库表中一行记录，或是将数据库表中一行记录映射成Java虚拟机中的一个Java对象。

- ORM图示：

  ![image-20260107174219350](mybatis.assets/image-20260107174219350.png)

- ORM-对象关系映射 示例：

  ![001-ORM思想-对象关系映射](mybatis.assets/001-ORM%E6%80%9D%E6%83%B3-%E5%AF%B9%E8%B1%A1%E5%85%B3%E7%B3%BB%E6%98%A0%E5%B0%84.png)

- MyBatis属于半自动化ORM框架。

- Hibernate属于全自动化的ORM框架。



**MyBatis框架特点：**

- 支持定制化 SQL、存储过程、基本映射以及高级映射
- 避免了几乎所有的 JDBC 代码中手动设置参数以及获取结果集
- 支持XML开发，也支持注解式开发。【为了保证sql语句的灵活，所以mybatis大部分是采用XML方式开发。】
- 将接口和 Java 的 POJOs(Plain Ordinary Java Object，简单普通的Java对象)映射成数据库中的记录
- 体积小好学：两个jar包，两个XML配置文件。
- 完全做到sql解耦合。
- 提供了基本映射标签。
- 提供了高级映射标签。
- 提供了XML标签，支持动态SQL的编写。
- ......



# 二、MyBatis入门程序

## 2.1 版本

**软件版本**：

+ IntelliJ IDEA：2022.1.4
+ Navicat for MySQL：16.0.14
+ MySQL数据库：8.0.30

**组件版本：**

+ MySQL驱动：8.0.30
+ <font style="color:#F5222D;">MyBatis：3.5.10</font>
+ <font style="color:#F5222D;">JDK：Java17</font>
+ JUnit：4.13.2
+ Logback：1.2.11





## 2.2 MyBatis下载

从github上下载，地址：[https://github.com/mybatis/mybatis-3](https://github.com/mybatis/mybatis-3)

![](mybatis.assets/1659582991347-ee21a960-e4ae-45a0-83cf-74b3f1f3825c.png)

![](mybatis.assets/1659583157781-fd860428-d25b-4c4c-9e68-0219de7d3ba9.png)



将框架以及框架的源码都下载下来，下载框架后解压，打开mybatis目录

![](mybatis.assets/1659583275291-4ba5ba90-5420-449e-97f4-2ec759c994ca.png)

通过以上解压可以看到，框架一般都是以jar包的形式存在。我们的mybatis课程使用maven，所以这个jar我们不需要。

官方手册需要。



## 2.3 MyBatis入门程序开发步骤

### 2.3.1 准备数据库表

1. 准备数据库表`t_car`，字段包括：

   + `id`：主键（自增）【bigint】
   + `car_num`：汽车编号【varchar】
   + `brand`：品牌【varchar】
   + `guide_price`：厂家指导价【decimal类型，专门为财务数据准备的类型】
   + `produce_time`：生产时间【char，年月日即可，10个长度，'2022-10-11'】
   + `car_type`：汽车类型（燃油车、电车、氢能源）【varchar】

2. 在数据库中使用navicat建表：

   ![image-20260108175644726](mybatis.assets/image-20260108175644726.png)

3. 向表中插入两条数据：

   ![image-20260108175658252](mybatis.assets/image-20260108175658252.png)



### 2.3.2 创建IDEA项目

mybatis中有两个主要的配置文件：

1. `mybatis-config.xml`：这是核心配置文件，主要配置连接数据库的信息等，有且仅有一个。

2. `XxxxxMapper.xml`：这个文件是专门用来编写SQL语句的配置文件，一般是一个数据库表对应一个。

   如`t_user`表对应`UserMapper.xml`、`t_student`表对应`StudentMapper.xml`。



下面我们开始创建项目：

1. 创建一个空项目`mybatis`，在空项目下创建第一个maven模块`mybatis-001-introduction`。

   ![image-20260108213341796](mybatis.assets/image-20260108213341796.png)

2. 编写pom文件。

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>cn.piggy</groupId>
       <artifactId>mybatis-001-introduction</artifactId>
       <version>1.0-SNAPSHOT</version>
       <!--打包方式-->
       <packaging>jar</packaging>
   
       <dependencies>
           <!--mybatis依赖-->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.5.10</version>
           </dependency>
           <!--mysql驱动依赖-->
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.31</version>
           </dependency>
       </dependencies>
   </project>
   ```

3. 在resource根目录下创建`mybatis-config.xml`配置文件。

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <environments default="development">
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://8.134.254.79:3306/powernode"/>
                   <property name="username" value="test"/>
                   <property name="password" value="Aa123456#."/>
               </dataSource>
           </environment>
       </environments>
       <mappers>
           <!--sql映射文件创建好之后，需要将该文件路径配置到这里-->
           <!--resource属性自动会从类路径下开始查找资源 -->
           <mapper resource="CarMapper.xml"/>
       </mappers>
   </configuration>
   ```

   注意：

   + mybatis核心配置文件的文件名不一定是`mybatis-config.xml`，可以是其它名字。
   + mybatis核心配置文件存放的位置也可以随意。这里选择放在resources根下，相当于放到了类的根路径下。

   

4. 在resource根目录下创建`CarMapper.xml`配置文件。

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--namespace先随意写一个-->
   <mapper namespace="car">
       <!--insert sql：保存一个汽车信息-->
       <insert id="insertCar">
           insert into t_car
           (id,car_num,brand,guide_price,produce_time,car_type)
           values
           (null,'102','丰田mirai',40.30,'2014-10-05','氢能源')
       </insert>
   </mapper>
   ```

   注意1：**<font style="color:#E8323C;">sql语句最后结尾可以不写“;”</font>**

   注意2：`CarMapper.xml`文件的名字不是固定的。可以使用其它名字。

   注意3：`CarMapper.xml`文件的位置也是随意的。这里选择放在resources根下，相当于放到了类的根路径下。

   注意4：<font style="color:#F5222D;">将`CarMapper.xml`文件路径配置到`mybatis-config.xml`：</font>

   ![image-20260108213631320](mybatis.assets/image-20260108213631320.png)

5. 编写MyBatisIntroductionTest代码。

   ```java
   public class MyBatisIntroductionTest {
       public static void main(String[] args) throws IOException {
           // 获取SqlSessionFactoryBuilder对象
           SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
   
           // 获取SqlSessionFactory对象
           InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
           SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
   
           // 获取SqlSession对象
           SqlSession sqlSession = sqlSessionFactory.openSession();
   
           // 执行sql语句
           int count = sqlSession.insert("insertCar"); // 返回值是影响数据库表的记录条数
           System.out.println("插入了几条记录：" + count);
           
           // 提交事务
           sqlSession.commit();
   
           // 关闭资源（只关闭资源是不会提交事务的）
           sqlSession.close();
       }
   }
   ```

6. 运行测试

   ![image-20260108215826759](mybatis.assets/image-20260108215826759.png)

   默认采用的事务管理器是：JDBC。JDBC事务默认是不提交的，需要手动提交。



## 2.4 关于MyBatis核心配置文件的名字和路径详解



**核心配置文件的名字是随意的**，只要在配置的时候放入相应的文件名：

```java
InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
```



**核心配置文件必须放到resources下吗**？

不是。我们将mybatis配置文件放到不同的位置上测试一下。

1. 将配置文件放在类的根目录下的`com`目录下，然后使用`Resources.getResourceAsStream()`进行加载：

   ![image-20260109173601237](mybatis.assets/image-20260109173601237.png)

   

2. 将配置文件放到D盘根目录下，使用文件输入流`FileInputStream`进行加载：

   ![image-20260109174016044](mybatis.assets/image-20260109174016044.png)



经过测试说明mybatis核心配置文件的名字是随意的，存放路径也是随意的。

虽然mybatis核心配置文件的名字不是固定的，但通常该文件的名字叫做：`mybatis-config.xml`。

虽然mybatis核心配置文件的路径不是固定的，但通常该文件会存放到**<font style="color:#F5222D;">类路径</font>**当中，这样让项目的移植更加健壮。



>  在mybatis中提供了一个类：`Resources`【org.apache.ibatis.io.Resources】，该类可以从类路径当中获取资源，我们通常使用它来获取输入流InputStream，但**这种方式只能从类路径当中获取资源**，也就是说mybatis-config.xml文件必须要在类路径下。代码如下：
>
> ```java
> InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
> ```
>
> 这个方法底层仍然是使用类加载器中的获取文件流方法：
>
> ![image-20260109175956986](mybatis.assets/image-20260109175956986.png)



## 2.5 MyBatis第一个比较完整的代码写法

