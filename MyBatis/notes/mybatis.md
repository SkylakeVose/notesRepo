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



## 2.4 MyBatis配置文件详解

### 2.4.1 关于核心配置文件的名字和路径详解

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
>  ```java
>  InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
>  ```
>
>  这个方法底层仍然是使用类加载器中的获取文件流方法：
>
>  ![image-20260109175956986](mybatis.assets/image-20260109175956986.png)



### 2.4.2 关于Mapper配置文件的名字和路径详解

**Mapper名字和存放位置是固定的么？**

不是。`XxxMapper.xml`的名字和位置都不是固定的，在配置文件中`mybatis-config.xml`中的`mapper`属性中指定其位置。

+ 如果是在类根目录`resource`下，则使用`<mapper>的resource`属性从类目录下加载资源：

  ```xml
  <!--resource属性：自动会从类路径下开始查找资源 -->
  <!--<mapper resource="CarMapper.xml"/>-->
  
  <!--从类的根目录下的com目录下加载CarMapper.xml-->
  <mapper resource="com/CarMapper.xml"/>
  ```

+ 如果是在绝对路径下，就需要使用`<mapper>`中`url`属性加载资源：

  ```xml
  <!--url属性：从绝对路径下加载资源-->
  <mapper url="file:///d:/CarMapper.xml"/>
  ```

  > 语法格式：`file:///绝对路径`

跟核心配置文件一样，使用绝对路径加载的方式可移植性太差，不符合OCP原则。为了项目的可移植性和健壮性，最好还是将这些文件放到类路径下面。



### 2.4.3 Mybatis的事务管理机制

#### 2.4.3.1 事务管理器

在`mybatis-config.xml`配置文件中，可以通过以下配置开启mybatis的事务管理：

![image-20260111144949496](mybatis.assets/image-20260111144949496.png)

事务管理器中`type`的值有两个：`JDBC`和`MANAGED`，且不区分大小写。

在mybatis中提供了两种事务管理机制：

+ **JDBC事务管理器**：

  mybatis框架自己管理事务，自己采用原生的JDBC代码去管理事务：

  ```java
  conn.setAutoCommit(false);	// 开启事务
  // 业务处理...
  con.commit();		// 手动提交事务
  ```

  

+ **MANAGED事务管理器**：

  mybatis不再负责事务的管理，事务管理交给其他容器来负责，如Spring。

  对于我们当前单纯的只有mybatis的情况下，如果配置为`MANAGED`，就表示事务当前是没有开启的。



#### 2.4.3.2 JDBC事务管理器

**测试一**：我们在开启JDBC事务的时候，使用到`sqlSessionFactory.openSession()`这条语句：

> `sqlSessionFactory.openSession()`默认形参是`false`。

![image-20260111154712381](mybatis.assets/image-20260111154712381.png)

这样相当于mybatis开启了事务，且需要手动提交事务。



**测试二**：如果我们在开启JDBC事务的时候，将形参改为`true`，改动后的语句：`selSessionFactory.openSession(true)`：

![image-20260111155349372](mybatis.assets/image-20260111155349372.png)

这样一来，每一条DML语句都会被提交，相当于没有开启事务。显然这种方式不适合多DML语句。



#### 2.4.3.3 MANAGED事务管理器

我们开启MANAGED事务，现在只有单纯的mybatis，没有其他的框架来做事务管理，因此这里相当于是没有事务。

![image-20260111160004428](mybatis.assets/image-20260111160004428.png)

这样配置就是没有事务，每次执行DML语句都会自动提交。



>  注意：只要`autoCommit`为`true`，就表示没有开启事务。



## 2.5 第一个比较完整的Mybatis程序

```java
public class MyBatisCompleteTest {
    public static void main(String[] args) {
        SqlSession sqlSession = null;
        try {
            // 1. 创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            // 2. 创建SqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            // 3. 创建SqlSession开启会话（底层开启事务）
            sqlSession = sqlSessionFactory.openSession();
            // 4. 执行sql语句，处理相关业务
            int count = sqlSession.insert("insertCar");
            System.out.println(count);
            // 5. 执行到这里，没有发生任何异常，提交事务
            sqlSession.commit();
        } catch (IOException e) {
            // 回滚事务
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            // 6. 关闭会话
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
```





## 2.6 引入JUnit

JUnit是专门做单元测试的组件。

- 在实际开发中，单元测试一般是由我们Java程序员来完成的。
- 我们要对我们自己写的每一个业务方法负责任，要保证每个业务方法在进行测试的时候都能通过。



测试的过程中涉及到两个概念：

* 期望值
* 实际值

期望值和实际值相同表示测试通过，期望值和实际值不同则单元测试执行时会报错。这个部分我们会使用`Assert`断言来完成。



### 2.6.1 简单使用Junit

我们创建一个junit的测试项目`junit-test`：

1. 引入依赖。

   ```xml
   <!--junit依赖-->
   <dependency>
       <groupId>junit</groupId>
       <artifactId>junit</artifactId>
       <version>4.13.2</version>
       <scope>test</scope>
   </dependency>
   ```

2. 编写一个业务类。

   ```java
   public class MathService {
   
       /**
        * 求和的业务方法
        * @param a
        * @param b
        * @return
        */
       public int sum(int a,int b){
           return a+b;
       }
   
       /**
        * 求减的业务方法
        * @param a
        * @param b
        * @return
        */
       public int sub(int a,int b){
           return a-b;
       }
   }
   ```

3. 编写单元测试类和对应的测试方法。

   注意事项：

   + 一般是一个业务方法对应一个测试方法。
   + 单元测试类的名字规范：`XxxxTest`。
   + 测试方法的规范：`public void testXxxx() {}`。
   + 测试方法名的规范：以test开始。比如测试方法`sum()`，对应的测试名为：`testSum()`。
   + @Test注解非常重要，被这个注解标注的方法就是一个单元测试方法。

   

   ```java
   public class MathServiceTest {
   
       @Test
       public void testSum() {
           /**
            * 单元测试中有两个重要概念：
            * 1. 实际值（被测试的业务方法的真正执行结果）
            * 2. 期望值（执行了这个业务方法后，期望的执行结果）
            */
           MathService mathService = new MathService();
           // 获取实际值
           int actual = mathService.sum(1, 2);
           // 期望值
           int expected = 3;
           // 加断言进行测试
           Assert.assertEquals(expected,actual);
   
       }
   
       @Test
       public void testSub() {
           MathService mathService = new MathService();
           // 获取实际值
           int actual = mathService.sub(10, 5);
           // 期望值
           int expected = 6;
           // 加断言进行测试
           Assert.assertEquals(expected,actual);
       }
   }
   ```

4. 执行测试：

   因为我们在测试方法中加了断言，如果实际值与期望值不一致，就报错。

   ![image-20260117161831714](mybatis.assets/image-20260117161831714.png)



### 2.6.2 在mybatis中引入JUnit

跟简单使用junit一样，先引入依赖，再编写单元测试类：

```java
public class CarMapperTest {
    @Test
    public void testInsertCar(){
        // 编写mybatis程序
        SqlSession sqlSession = null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            // 开启会话（底层开启事务）
            sqlSession = sqlSessionFactory.openSession();
            // 执行sql语句，处理相关业务
            int count = sqlSession.insert("insertCar");
            System.out.println(count);
            // 执行到这里，没有发生任何异常，提交事务
            sqlSession.commit();
        } catch (IOException e) {
            // 回滚事务
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            // 关闭会话
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
```





## 2.7 引入日志框架logback

引入日志框架的目的是为了看清楚mybatis执行的具体sql。

启用标准日志组件，只需要在`mybatis-config.xml`文件中添加以下配置：【可参考mybatis手册】。

```xml
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING" />
</settings>
```

`<settings>`的放置位置是有顺序的：

<img src="mybatis.assets/image-20260118204753389.png" alt="image-20260118204753389" style="zoom:67%;" />



`STDOUT_LOGGING`标准日志配置不够灵活，可以继承其他的日志组件，mybatis支持的日志框架如下：

<img src="mybatis.assets/image-20260118203942347.png" alt="image-20260118203942347" style="zoom: 67%;" />

> 注：我们可以看到有几种主要的日志框架：`SLF4J`，`LOG4J`，`LOG4J2`，`STDOUT_LOGGING`等。
>
> 其中，`STDOUT_LOGGING`是mybatis自己就实现的，可以直接使用。而其他三个日志框架在使用前需要引入相关的依赖。
>
> 特别注意的是`SLF4J`只是一种日志标准，并没有直接的实现。但是有一个日志框架`logback`就实现了`SLF4J`规范。
>
> `SLF4J`，`LOG4J`，`LOG4J2`都是同一个作者。



`logback`是目前日志框架中性能较好的，较为流行的，因此我们选择它来作为我们引入的日志框架。

1. 引入logback相关依赖。

   ```xml
   <!--引入logback依赖，这个日志框架实现了slf4j规范-->
   <dependency>
       <groupId>ch.qos.logback</groupId>
       <artifactId>logback-classic</artifactId>
       <version>1.2.11</version>
   </dependency>
   ```

2. 引入logback相关配置文件（文件名必须叫`logback.xml`或`logback-test.xml`，且放在类路径之下）。

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   
   <configuration debug="false">
       <!-- 控制台输出 -->
       <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
           <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
               <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
               <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
           </encoder>
       </appender>
   
       <!-- 按照每天生成日志文件 -->
       <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
           <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
               <!--日志文件输出的文件名-->
               <FileNamePattern>${LOG_HOME}/TestWeb.log.%d{yyyy-MM-dd}.log</FileNamePattern>
               <!--日志文件保留天数-->
               <MaxHistory>30</MaxHistory>
           </rollingPolicy>
           <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
               <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
               <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
           </encoder>
           <!--日志文件最大的大小-->
           <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
               <MaxFileSize>100MB</MaxFileSize>
           </triggeringPolicy>
       </appender>
   
       <!--mybatis log configure-->
       <logger name="com.apache.ibatis" level="TRACE"/>
       <logger name="java.sql.Connection" level="DEBUG"/>
       <logger name="java.sql.Statement" level="DEBUG"/>
       <logger name="java.sql.PreparedStatement" level="DEBUG"/>
   
       <!-- 日志输出级别,logback日志级别包括五个：TRACE < DEBUG < INFO < WARN < ERROR -->
       <root level="DEBUG">
           <appender-ref ref="STDOUT"/>
           <appender-ref ref="FILE"/>
       </root>
   
   </configuration>
   ```

3. 在`mybatis-config.xml`中修改日志框架为`SLF4J`。

   ```xml
   <settings>
       <!--<setting name="logImpl" value="STDOUT_LOGGING"/>-->
       <setting name="logImpl" value="SLF4J"/>
   </settings>
   ```

4. 再次执行单元测试方法`testInsertCar()`，查看控制台的日志输出。

   ![image-20260118205932708](mybatis.assets/image-20260118205932708.png)





## 2.8 封装MyBatis工具类

我们创建一个工具类`SqlSessionUtil`：

```java
public class SqlSessionUtil {

    // 工具类的构造方法一般都是私有化的
    // 工具中所有的方法都是静态的，直接采用类名即可调用，不需要new对象

    // 为了防止new对象，私有化构造方法
    private SqlSessionUtil() {
    }

    private static SqlSessionFactory sqlSessionFactory;

    // 类加载时执行
    // SqlSessionUtil工具类在进行第一次加载的时候，即系mybatis-config.xml文件，创建SqlSessionFactory对象
    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }
}
```

在单元测试中无法测试，后续再找下原因。

我们在主程序中执行测试：

<img src="mybatis.assets/image-20260118211922556.png" alt="image-20260118211922556" style="zoom:80%;" />





# 三、使用MyBatis完成CRUD

先做一些准备工作，创建一个新的模块`mybatis-002-curd`，并拉取他们的依赖、各种配置文件、工具类`SqlSessionUtil`和测试用例`CarMapperTest`：

![image-20260119111100099](mybatis.assets/image-20260119111100099.png)



**什么是CURD？**

+ C：Create（增）
+ R：Retrieve（查，检索）
+ U：Update（改）
+ D：Delete（删）



## 3.1 insert（Create）

分析以下SQL映射文件中SQL语句存在的问题：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace先随意写一个-->
<mapper namespace="car">
    <!--insert sql：保存一个汽车信息-->
    <insert id="insertCar">
        insert into t_car (id,car_num,brand,guide_price,produce_time,car_type) values (null,'102','丰田mirai',40.30,'2014-10-05','氢能源')
    </insert>
</mapper>
```

存在的问题是：SQL语句中的值不应该写死，值应该是用户提供的。之前的JDBC代码是这样写的：

```java
// JDBC中使用 ? 作为占位符。那么MyBatis中会使用什么作为占位符呢？
String sql = "insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(?,?,?,?,?)";
// ......
// 给 ? 传值。那么MyBatis中应该怎么传值呢？
ps.setString(1,"103");
ps.setString(2,"奔驰E300L");
ps.setDouble(3,50.3);
ps.setString(4,"2022-01-01");
ps.setString(5,"燃油车");
```



在MyBatis中可以这样做：

+ 使用Map集合传参
+ 使用POJO传参



### 3.1.1 使用Map集合传参

**在Java程序中，将数据放到Map集合中**

**在sql语句中使用 `#{map集合的key} `来完成传值，`#{}` 等同于JDBC中的` ?` ，`#{}`就是占位符**

java代码：

```java
@Test
public void testInsertCar(){
    // 准备数据
    Map<String, Object> map = new HashMap<>();
    map.put("k1", "103");
    map.put("k2", "奔驰E300L");
    map.put("k3", 50.3);
    map.put("k4", "2020-10-01");
    map.put("k5", "燃油车");
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句（使用map集合给sql语句传递数据）
    int count = sqlSession.insert("insertCar", map);
    System.out.println("插入了几条记录：" + count);
}
```

SQL语句：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace先随意写一个-->
<mapper namespace="car">
    <!--insert sql：保存一个汽车信息-->
    <insert id="insertCar">
        insert into t_car(car_num,brand,guide_price,produce_time,car_type)
        values(#{k1},#{k2},#{k3},#{k4},#{k5})
    </insert>
</mapper>
```

运行测试：

![image-20260119112114427](mybatis.assets/image-20260119112114427.png)

<img src="mybatis.assets/image-20260119153738121.png" alt="image-20260119153738121" style="zoom:80%;" />



**如果SQL语句中占位符`#{}`里的key不存在**，会出现什么问题？

![image-20260119154011215](mybatis.assets/image-20260119154011215.png)

通过测试，看到程序并没有报错。正常执行。不过 `#{noExistKey}` 的写法导致无法获取到map集合中的数据，最终导致数据库表`car_num`插入了NULL。



在以上sql语句中，可以看到`#{k1} #{k2} #{k3} #{k4} #{k5}`的可读性太差，**为了增强可读性**，我们可以改一下测试代码和SQL语句：

```java
@Test
public void testInsertCar(){
    // 准备数据
    Map<String, Object> map = new HashMap<>();
    // 让key的可读性增强
    map.put("carNum", "103");
    map.put("brand", "奔驰E300L");
    map.put("guidePrice", 50.3);
    map.put("produceTime", "2020-10-01");
    map.put("carType", "燃油车");
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句（使用map集合给sql语句传递数据）
    int count = sqlSession.insert("insertCar", map);
    System.out.println("插入了几条记录：" + count);

    sqlSession.commit();
    sqlSession.close();
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace先随意写一个-->
<mapper namespace="car">
    <!--insert sql：保存一个汽车信息-->
    <insert id="insertCar">
        insert into t_car(car_num,brand,guide_price,produce_time,car_type)
        values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
    </insert>
</mapper>
```

运行测试：

![image-20260119154456685](mybatis.assets/image-20260119154456685.png)



### 3.1.2 使用POJO传参

除了可以使用Map集合传参，也可以使用pojo（简单普通的java对象）完成传参。

1. 创建一个pojo类（`Car`），提供相关属性。属性要与数据库表中的一一对应。

   ```java
   package cn.piggy.mybatis.pojo;
   
   /**
    * 封装汽车相关信息的POJO类，普通的java类
    */
   public class Car {
       // 数据库表中的字段应该和POJO类的属性一一对应
   
       // 建议使用包装类，防止遇到null的问题
       private Long id;
       private String carNum;
       private String brand;
       private Double guidePrice;
       private String produceTime;
       private String carType;
   
       @Override
       public String toString() {
           return "Car{" +
                   "id=" + id +
                   ", carNum='" + carNum + '\'' +
                   ", brand='" + brand + '\'' +
                   ", guidePrice=" + guidePrice +
                   ", produceTime='" + produceTime + '\'' +
                   ", carType='" + carType + '\'' +
                   '}';
       }
   
       public Car() {
       }
   
       public Car(Long id, String carNum, String brand, Double guidePrice, String produceTime, String carType) {
           this.id = id;
           this.carNum = carNum;
           this.brand = brand;
           this.guidePrice = guidePrice;
           this.produceTime = produceTime;
           this.carType = carType;
       }
   
       public Long getId() {
           return id;
       }
   
       public void setId(Long id) {
           this.id = id;
       }
   
       public String getCarNum() {
           return carNum;
       }
   
       public void setCarNum(String carNum) {
           this.carNum = carNum;
       }
   
       public String getBrand() {
           return brand;
       }
   
       public void setBrand(String brand) {
           this.brand = brand;
       }
   
       public Double getGuidePrice() {
           return guidePrice;
       }
   
       public void setGuidePrice(Double guidePrice) {
           this.guidePrice = guidePrice;
       }
   
       public String getProduceTime() {
           return produceTime;
       }
   
       public void setProduceTime(String produceTime) {
           this.produceTime = produceTime;
       }
   
       public String getCarType() {
           return carType;
       }
   
       public void setCarType(String carType) {
           this.carType = carType;
       }
   }
   ```

2. 编写测试程序。

   ```java
   @Test
   public void testInsertCarByPOJO() {
       SqlSession sqlSession = SqlSessionUtil.openSession();
       // 封装数据
       Car car = new Car(null, "105", "比亚迪秦", 10.0, "2020-11-11", "新能源");
       // 执行SQL
       int count =  sqlSession.insert("insertCar", car);
       System.out.println(count);
   
       sqlSession.commit();
       sqlSession.close();
   }
   ```

3. 更改SQL语句。

   ```xml
   <mapper namespace="car">
       <!--占位符#{} 里写的是POJO的属性名-->
       <insert id="insertCar">
           insert into t_car(id,car_num,brand,guide_price,produce_time,car_type)
           values(null, #{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
       </insert>
   </mapper>
   ```

4. 运行测试。

   ![image-20260120170230394](mybatis.assets/image-20260120170230394.png)



如果SQL语句中占位符`#{}`中写的不是POJO的属性名，会有什么问题？

![image-20260120170339762](mybatis.assets/image-20260120170339762.png)

可以看到我们将`carNum`改成了`xyz`，程序报错描述：没有在类中找到xyz属性的getter()方法。

mybatis底层实际上是根据属性名，使用反射机制调用类对象的相应的getter方法，然后得到这个属性值。比如在上面测试中，mybatis要获取carNum属性的值，首先会生成相应的getter方法：属性名第一个字母大写，再添加前缀get字段，得到getCarNum()方法。这样一来就可以调用并获取到属性值了。

![image-20260120171017806](mybatis.assets/image-20260120171017806.png)



当然，如果改动了不存在的属性名，如上述的`xyz`，也可以在POJO类中新增一个`getXyz()`的方法，返回对应的属性值即可。





**注意：**

注意：其实传参数的时候有一个属性parameterType，这个属性用来指定传参的数据类型，不过这个属性是可以省略的。

```xml
<insert id="insertCar" parameterType="java.util.Map">
    insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>

<insert id="insertCarByPOJO" parameterType="com.powernode.mybatis.pojo.Car">
    insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>
```





## 3.2 delete（Delete）

需求：根据car_num进行删除。

SQL语句：

```xml
<delete id="deleteById">
    delete from t_car where id = #{id}
</delete>
```

java代码：

```java
@Test
public void testDeleteById() {
    SqlSession sqlSession = SqlSessionUtil.openSession();
    int count = sqlSession.delete("deleteById", 24);
    System.out.println(count);

    sqlSession.commit();
    sqlSession.close();
}
```

![image-20260120174710130](mybatis.assets/image-20260120174710130.png)

运行测试：

![image-20260120174922195](mybatis.assets/image-20260120174922195.png)



**注意：当占位符只有一个的时候，`${}` 里面的内容可以随便写**，如下面这种写法也是可以的：

![image-20260120175014023](mybatis.assets/image-20260120175014023.png)



## 3.3 update（Update）

我们使用对象传参来进行数据更新

SQL语句：

```xml
<update id="updateById">
    update t_car set car_num=#{carNum}, brand=#{brand}, guide_price=#{guidePrice},
    produce_time=#{produceTime}, car_type=#{carType} where id = #{id}
</update>
```

java测试代码：

```java
@Test
public void testUpdateById() {
    SqlSession sqlSession = SqlSessionUtil.openSession();

    // 准备数据
    Car car = new Car(4L, "9999", "凯美瑞", 30.3, "1999-11-10", "燃油车");

    // 执行SQL语句
    int count = sqlSession.update("updateById", car);
    System.out.println(count);

    sqlSession.commit();
    sqlSession.close();
}
```

执行测试：

<img src="mybatis.assets/image-20260123165323229.png" alt="image-20260123165323229" style="zoom:80%;" />



## 3.4 select（Retrieve）

select语句和其它语句不同的是：查询会有一个结果集，这部分来看看mybatis是怎么处理结果集的。



### 3.4.1 查询一条数据

需求：查询id为1的Car的信息

SQL语句：

```xml
<select id="selectById">
    select * from t_car where id = #{id}
</select>
```

java语句：

```java
@Test
public void testSelectById() {
    SqlSession sqlSession = SqlSessionUtil.openSession();
    Object car = sqlSession.selectOne("selectById", 1);
    System.out.println(car);
}
```

执行测试：

![image-20260125215359813](mybatis.assets/image-20260125215359813.png)

上述错误信息表明了，JDBC尝试把返回的结果集`ResultSet`转换成Java对象或者Map集合时，没有找到相应的可转换类型。因此我们如果想要将结果集转换成一个Java对象的话，就需要致命需要转换的类型，可以在`<select>`标签中添加`resultType`属性，并赋值相应的全限定类名：

```xml
<select id="selectById" resultType="cn.piggy.mybatis.pojo.Car">
    select * from t_car where id = #{id}
</select>
```



我们再次执行测试：

![image-20260125215214881](mybatis.assets/image-20260125215214881.png)



我们可以看到返回的Car对象有一些属性没有值，通过对比我们可以发现，只有当字段名跟属性名一致的就能被成功赋值，不一样的则置为了null：

![image-20260125220257658](mybatis.assets/image-20260125220257658.png)

显然在类中属性名我们使用了驼峰命名，而数据库表中的字段名使用了下划线命名，这导致了两边部分数据名称不一致的现象，也是导致无法赋值的原因。我们使用`as`关键字让属性名跟字段名统一起来：

![image-20260125220719120](mybatis.assets/image-20260125220719120.png)

通过测试得知，如果当查询结果的字段名和java类的属性名对应不上的话，可以采用as关键字给字段名起别名，**<font style="color:#E8323C;">当然还有其它解决方案，我们后面再看</font>**。



### 3.4.2 查询多条数据

需求：查询所有数据

SQL语句：

```xml
<!--此处仍需要指定返回对象的类型-->
<select id="selectAll" resultType="cn.piggy.mybatis.pojo.Car">
    select
    id,
    car_num as carNum,
    brand,
    guide_price as guidePrice,
    produce_time as produceTime,
    car_type as carType
    from
    t_car
</select>
```

java代码：

```java
@Test
public void testSelectAll() {
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句
    List<Object> cars = sqlSession.selectList("selectAll");
    cars.forEach(System.out::println);
    sqlSession.close();
}
```

执行结果：

![image-20260125221724988](mybatis.assets/image-20260125221724988.png)



注意：

+ `resultType`：还是指定要封装的结果集的类型，也就是指定List集合中元素的类型。
+ `selectList()`方法：mybatis通过该方法就可以得知需要返回List集合，他会自动给你返回一个List集合。



## 3.5 关于SQL Mapper的namespace

在SQL Mapper配置文件中`<mapper>`标签的`namespace`属性可以翻译为命名空间，这个命名空间主要是为了防止`sqlId`冲突的。

我们在调用mapper中的方法时，完整的写法应该是**命名空间+sqlId**：

![image-20260125223521119](mybatis.assets/image-20260125223521119.png)



如果当我们存在两个Mapper文件，且里面有些方法名称是一样的时候，不指定namespace，直接使用方法名就会报错：

![image-20260125223721767](mybatis.assets/image-20260125223721767.png)

![image-20260125224323526](mybatis.assets/image-20260125224323526.png)



# 四、MyBatis核心配置文件详解

我们新建一个模块`mybatis-003-configuration`，并拷贝部分002项目的代码文件：

![image-20260126150614295](mybatis.assets/image-20260126150614295.png)

我们来看看mybatis的核心配置文件的标签：

![image-20260126151328302](mybatis.assets/image-20260126151328302.png)



## 4.1 environment

一个`<environment>`环境连接一个数据库，而数据库会对应一个SqlSessionFactory对象。

我们创建两个环境：开发环境`development`和生产环境`production`，并编写相应代码：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--默认使用开发环境-->
    <environments default="development">
        <!--开发环境-->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://8.134.254.79:3306/powernode"/>
                <property name="username" value="test"/>
                <property name="password" value="Aa123456#."/>
            </dataSource>
        </environment>
        <!--生产环境-->
        <environment id="production">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://8.134.254.79:3306/powernode2"/>
                <property name="username" value="test"/>
                <property name="password" value="Aa123456#."/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```

```java
@Test
public void testEnvironment() throws Exception {
    // 准备数据
    Car car = new Car();
    car.setCarNum("133");
    car.setBrand("丰田霸道");
    car.setGuidePrice(50.3);
    car.setProduceTime("2020-01-10");
    car.setCarType("燃油车");

    // 一个数据库对应一个SqlSessionFactory对象
    // 两个数据库对应两个SqlSessionFactory对象，以此类推
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    // 使用默认数据库
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    int count = sqlSession.insert("insertCar", car);
    System.out.println("插入了几条记录：" + count);

    // 使用指定数据库
    SqlSessionFactory sqlSessionFactory1 = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "production");
    SqlSession sqlSession1 = sqlSessionFactory1.openSession(true);
    int count1 = sqlSession1.insert("insertCar", car);
    System.out.println("插入了几条记录：" + count1);
}
```



执行测试：

![image-20260126154359147](mybatis.assets/image-20260126154359147.png)

![](mybatis.assets/1660117624656-64973cf7-6700-43da-96fa-5960dfc5045b.png)

![](mybatis.assets/1660117692466-ecd6317f-5868-4858-a33c-0c7ec68044ac.png)





## 4.2 transactionManager

![image-20260127165310662](mybatis.assets/image-20260127165310662.png)

`<transactionManager>`标签：

+ 作用：配置事务管理器。指定mybatis具体使用什么方式去管理事务。

+ `type`属性的两个值：

  + `JDBC`：使用原生的JDBC代码来管理事务。

    ```java
    conn.setAutoCommit(false);
    ...
    conn.commit();
    ```

  + `MANAGED`：mybatis不再负责事务的管理，将事务管理交给其他的JEE（JavaEE）容器来管理，如Spring等。

    如果使用了`Managed`属性且没有容器支持，也即当mybatis找不到容器支持时，那么数据库操作就没有事务，每执行一条SQL语句就提交一次。

+ type属性不区分大小写，且只能二选一。

+ 在mybatis中提供了一个事务管理器接口：`Transaction`，且该接口下有两个实现类：`JdbcTransaction`和`ManagedTransaction`。

  <img src="mybatis.assets/image-20260127165826768.png" alt="image-20260127165826768" style="zoom:80%;" />

  根据type属性，底层实例化相应的管理器实现类。



## 4.3 dataSource

![image-20260128114145420](mybatis.assets/image-20260128114145420.png)



dataSource被称为**数据源**。

dataSource作用：**为程序提供Connnection对象**。（但凡是给程序提供Connection对象的，都叫数据源）



数据源实际上是一套规范，JDK中有这套规范：`javax.sql.DataSource`。（基于这套规范制定的接口实际上也是JDK规定的）。

我们也可以自定义数据源组件，只要实现`javax.sql.DataSource`接口就行了。比如可以写一个自定义的数据库连接池（数据库连接池是提供对象的，所以数据库连接池就是一个数据源）。



常见的数据源组件（常见的数据库连接池）有哪些？

+ 阿里巴巴的德鲁伊连接池（druid）
+ c3p0
+ dbcp
+ 等等



`type`属性用来指定数据源的类型，就是指定具体使用什么方式来获取Connection对象，其有三种内建的数据源类型`"[UNPOOLED|POOLED|JNDI]`。

+ `UNPOOLED`：不使用数据库连接池技术，每一次请求都会创建新的Connection对象。
+ `POOLED`：使用mybatis自己实现的数据库连接池。
+ `JNDI`：集成其他第三方的数据库连接池。



### 4.3.1 数据源下的不同属性

**JNDI**是一套规范。大部分web容器都实现了JNDI规范，比如Tomcat、Jetty、WebLogic等都实现了这套规范。

也就是说Java命名目录接口，而web容器则实现了这个规范。

![image-20260128170040998](mybatis.assets/image-20260128170040998.png)



### 4.3.2 UNPOOLED 和 POOLED的区别

连接池的优点：

+ 每一次获取连接都从池中拿，效率高。
+ 因为每一次只能从池中拿，所以连接对象的创建数量是可控的。



我们准备测试代码，使用默认的环境进行测试：

```java
@Test
public void testDataSource() throws Exception {
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));

    // 准备数据
    Car car = new Car();
    car.setCarNum("199");
    car.setBrand("丰田霸道");
    car.setGuidePrice(50.3);
    car.setProduceTime("2020-01-10");
    car.setCarType("燃油车");

    // 通过sqlSessionFactory对象可以开启多个会话
    // 会话1
    SqlSession sqlSession1 = sqlSessionFactory.openSession();
    sqlSession1.insert("car.insertCar", car);
    sqlSession1.commit();
    sqlSession1.close();

    // 会话2
    SqlSession sqlSession2 = sqlSessionFactory.openSession();
    sqlSession2.insert("car.insertCar", car);
    sqlSession2.commit();
    sqlSession2.close();
}
```



使用UNPOOLED，两次会话都会打开和关闭Connection连接对象：

![image-20260128170542647](mybatis.assets/image-20260128170542647.png)



使用POOLED，在会话开始时会向连接池申请Connection连接，结束后会返还给连接池。

![image-20260128171013620](mybatis.assets/image-20260128171013620.png)





### 4.3.3 POOLED状态下的其他参数设置

1. `poolMaximumActiveConnections`：连接池当中正在使用的连接对象的数量上限（同一段时间最大的连接数量），默认为10。

   测试一：我们设置其为3，在测试代码中申请4个会话。

   ```xml
   <property name="poolMaximumActiveConnections" value="3"/>
   ```

   ```java
   @Test
   public void testProperties() throws Exception {
       SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
       SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
   
       // 准备数据
       Car car = new Car();
       car.setCarNum("199");
       car.setBrand("丰田霸道");
       car.setGuidePrice(50.3);
       car.setProduceTime("2020-01-10");
       car.setCarType("燃油车");
   
       for (int i = 0; i < 4; i++) {
           SqlSession sqlSession1 = sqlSessionFactory.openSession();
           sqlSession1.insert("car.insertCar", car);
           // 不要关闭，不要返还连接对象
       }
   }
   ```

   ![image-20260128172437700](mybatis.assets/image-20260128172437700.png)



2. `poolMaximumCheckoutTime`：在被强制返回之前，池中连接被检出（check out）时间，默认值为20000毫秒（20秒）。

   一般情况下，对某个已占用的连接对象，处于空闲状态且超过检出时间时，会被连接池强制回收。

   如上个测试例子可以看到，第四个会话的等待池中可用连接的时间可以通过这个参数进行调整。

   

3. `poolTimeToWait`：这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。

   测试二：我们将检出时间设置为10s，且打印日志间隔为2s，继续执行测试一。

   ```xml
   <!--池中连接检出时间，，默认20s。现设置为10s-->
   <property name="poolMaximumCheckoutTime" value="10000"/>
   <!--每隔2s打印日志，并且尝试获取连接对象-->
   <property name="poolTimeToWait" value="2000"/>
   ```

   ![image-20260128173410494](mybatis.assets/image-20260128173410494.png)



4. `poolMaximumIdleConnections `： 任意时间可能存在的空闲连接数上限。

   如果当池中的空闲对象数量超过设定的空闲连接数上限时，多余的空闲连接对象会被真正的关闭。

   ```xml
   <!--空闲连接数上限,设置为5个-->
   <property name="poolMaximumIdleConnections " value="5"/>
   ```

   

## 4.4 Properties

mybatis提供了更加灵活的配置，连接数据库的信息可以抽取出来或者单独写到一个属性资源文件中。

1. 将数据库配置信息抽取出来：

   <img src="mybatis.assets/image-20260129164253161.png" alt="image-20260129164253161" style="zoom:80%;" />

2. 单独写到一个配置文件`jdbc.properties`中（放到根目录下）：

   <img src="mybatis.assets/image-20260129164712815.png" alt="image-20260129164712815" style="zoom:80%;" />

3. 单独写到一个配置文件`jdbc.properties`中（放到绝对路径下）：

   比如将配置文件放在D盘下的写法：

   ```xml
   <!--url从绝对路径下加载文件-->
   <properties url="file:///d:/jdbc.properties"/>
   ```



**properties两个属性：**

+ `resource`：这个属性从类的根路径下开始加载。【常用的。】
+ `url`：从指定的url加载，假设文件放在`d:/jdbc.properties`，这个url可以写成：`file:///d:/jdbc.properties`。（注意是三个斜杠）



注意：如果不知道`mybatis-config.xml`文件中标签的编写顺序的话，可以有两种方式知道它的顺序：

+ 第一种方式：查看dtd约束文件。
+ 第二种方式：通过idea的报错提示信息。【一般采用这种方式】



## 4.5 Mapper

mapper标签用来指定SQL映射文件的路径，包含多种指定方式，这里先主要看其中两种：

+ `resource`：从类的根路径下开始加载。
+ `url`：从指定的url位置加载。



实例：

<img src="mybatis.assets/image-20260129165438922.png" alt="image-20260129165438922" style="zoom:80%;" />







# 五、手写MyBatis框架

## 5.1 dom4j解析XML文件

新建一个模块`parse-xml-by-dom4j`，用于测试解析程序。



### 5.1.1 解析mybaits核心配置文件

1. 在`pom.xml`中引入依赖：

   ```xml
   <dependencies>
       <!--dom4j依赖-->
       <dependency>
           <groupId>org.dom4j</groupId>
           <artifactId>dom4j</artifactId>
           <version>2.1.3</version>
       </dependency>
       <!--jaxen依赖-->
       <dependency>
           <groupId>jaxen</groupId>
           <artifactId>jaxen</artifactId>
           <version>1.2.0</version>
       </dependency>
       <!--junit依赖-->
       <dependency>
           <groupId>junit</groupId>
           <artifactId>junit</artifactId>
           <version>4.13.2</version>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

   

2. 编写代码并测试：

   ```java
   @Test
   public void testParseMyBatisConfigXML() throws Exception {
       // 创建SAXReader对象
       SAXReader reader = new SAXReader();
       // 获取输入流
       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
       // 读XML文件，返回document独享，document对象是文档对象，代表了整个XML文件
       Document document = reader.read(is);
       System.out.println(document);
   
       // 获取文档当中的根标签
       /*Element rootElement = document.getRootElement();
           System.out.println("根节点的名字：" + rootElement.getName());*/
   
       // 获取default默认的环境id
       // xpath是做标签路径匹配的，能够让我们快速定位XML文件中的元素。
       // 下面的xpath代表：从根下找configuration标签，然后找该标签下的environments子标签
       String xpath = "/configuration/environments";
       Element environments = (Element) document.selectSingleNode(xpath);  // Element是Node的子类，方法更多
       // 获取属性的值
       String defaultEnvironmentId = environments.attributeValue("default");
       System.out.println("默认环境的环境ID：" + defaultEnvironmentId);
   
       // 根据默认的环境ID 获取具体的环境environment
       xpath = "/configuration/environments/environment[@id='" + defaultEnvironmentId + "']";
       Element environment = (Element) document.selectSingleNode(xpath);
       // System.out.println(environment);
   
       // 获取environment节点下的TransactionManager节点
       // 使用Element的element()方法来互殴孩子节点
       Element transactionManager = environment.element("transactionManager");
       String transactionType = transactionManager.attributeValue("type");
       System.out.println("事务管理器的类型： " + transactionType);
   
       // 获取dataSource节点
       Element dataSource = environment.element("dataSource");
       String dataSourceType = dataSource.attributeValue("type");
       System.out.println("数据源的类型：" + dataSourceType);
   
       // 获取dataSource节点下的所有子节点
       List<Element> propertyElts = dataSource.elements();
       // 遍历输出
       propertyElts.forEach(propertyElt -> {
           String name = propertyElt.attributeValue("name");
           String value = propertyElt.attributeValue("value");
           System.out.println(name + ":" + value);
       });
   
       // 获取所有的Mapper标签
       // 不想从根下开始获取，从任意位置开始，获取所有的mapper标签
       xpath = "//mapper";
       List<Node> mappers = document.selectNodes(xpath);
       // 遍历
       mappers.forEach(mapper -> {
           Element mapperElt = (Element) mapper;
           String resource = mapperElt.attributeValue("resource");
           System.out.println(resource);
       });
   }
   ```

3. 执行测试：

   ![image-20260129174553590](mybatis.assets/image-20260129174553590.png)

   ![image-20260129174850935](mybatis.assets/image-20260129174850935.png)



### 5.1.2 解析mapper映射文件

准备解析代码：

```java
@Test
public void testParseSqlMapperXML() throws Exception {
    SAXReader reader = new SAXReader();
    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("CarMapper.xml");
    Document document = reader.read(is);

    // 获取namespace
    String xpath = "/mapper";
    Element mapper = (Element) document.selectSingleNode(xpath);
    String namespace = mapper.attributeValue("namespace");
    System.out.println("命名空间：" + namespace);

    // 获取mapper节点下所有的子节点
    List<Element> elements = mapper.elements();
    elements.forEach(element -> {
        // 获取sqlId
        String id = element.attributeValue("id");
        System.out.println(id);

        // 获取resultType（如果没有这个属性则会返回null）
        String resultType = element.attributeValue("resultType");
        System.out.println(resultType);

        // 获取标签中的sql语句（获取文本且去除前后空白）
        String sql = element.getTextTrim();
        System.out.println(sql);

        /** MYBATIS封装了JDBC,我们需要将#{}转换成JDBC规范里的？
             * insert into t_car values(null, #{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
             * ↑ 转换为 → insert into t_car values(null,?,?,?,?,?)
             */
        String newSql = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
        System.out.println(newSql);
    });
}
```



测试：

![image-20260129181131932](mybatis.assets/image-20260129181131932.png)

![image-20260129181324962](mybatis.assets/image-20260129181324962.png)





## 5.2 GodBatis

### 5.2.1 创建GodBatis模块

新建maven项目`godbatis`，并引入依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.piggy</groupId>
    <artifactId>godbatis</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <!--dom4j-->
        <dependency>
            <groupId>org.dom4j</groupId>
            <artifactId>dom4j</artifactId>
            <version>2.1.3</version>
        </dependency>
        <!--jaxen-->
        <dependency>
            <groupId>jaxen</groupId>
            <artifactId>jaxen</artifactId>
            <version>1.2.0</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```



分析构建的流程：

1. 通过观察之前的代码，可以得知`SqlSessionFactory`对象先通过`SqlSessionFactoryBuilder`对象的build方法生成的，而build方法需要加载核心配置文件，因此我们可以先把这两个对象给定义出来，并封装加载文件的方法。

   ![image-20260131111445783](mybatis.assets/image-20260131111445783.png)





### 5.2.2 创建资源工具类

创建资源工具类`cn.piggy.godbatis.utils.Resouces`，方便获取指向配置文件的输入流:

```java
/**
 * godBatis框架提供的一个工具类
 * 这个工具类专门完成类路径中资源的加载
 */
public class Resources {
    /**
     * 工具了的构造方法都是建议私有化的
     * 因为工具类中的方法都是静态的，不能创建对象就能调佣。
     * 为了避免new对象，所有构造方法私有化。
     * 这是一种编程习惯。
     */
    private Resources() {}

    /**
     * 从类路径中加载资源。
     * @param resource 放在类路径当中的资源文件
     * @return 访问资源文件的一个输入流
     */
    public static InputStream getResourceAsStream(String resource) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
```



### 5.2.3 定义SqlSessionFactoryBuilder类

提供一个无参数构造方法，再提供一个build方法，该build方法要返回SqlSessionFactory对象。

```java
/**
 * SqlSessionFactory的构建器对象
 * 通过SqlSessionFactoryBuilder的build方法来解析godbatis-config.xml文件，然后创建SqlSessionFactory对象。
 */
public class SqlSessionFactoryBuilder {

    /**
     * 无参数构造方法
     */
    public SqlSessionFactoryBuilder() {
    }

    /**
     * 解析godbatis-config.xml文件，来构建SqlSessionFactory对象
     * @param in 指向godbatis-config.xml的文件流
     * @return SqlSessionFactory对象
     */
    public SqlSessionFactory build(InputStream in) {
        // 解析配置文件，创建数据源对象
        // 解析配置文件，创建事务管理器对象
        // 解析配置文件，后去所有的SQL映射对象
        // 解析完成之后，构建SqlSessionFactory对象
    }
}
```



### 5.2.4 定义
