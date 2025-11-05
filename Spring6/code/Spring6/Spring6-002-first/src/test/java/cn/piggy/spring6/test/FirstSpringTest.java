package cn.piggy.spring6.test;

import cn.piggy.spring6.bean.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class FirstSpringTest {

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

    @Test
    public void testBeanFactory() {
        // ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        BeanFactory applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user = applicationContext.getBean("userBean", User.class);
        System.out.println(user);
    }

    @Test
    public void testXmlPath() {
        FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("D:/spring.xml");
        User user = applicationContext.getBean("userBean", User.class);
        System.out.println(user);
    }

    @Test
    public void testFirstSpringCode() {
        // 第一步：获取spring容器对象
        // ApplicationContext：应用上下文，即spring容器
        // ApplicationContext是一个接口，有很多个实现类，其中有一个实现类叫 ClassPathXmlApplicationContext
        // ClassPathXmlApplicationContext类 是专门从类路径当中加载spring配置文件的一个Spring上下文对象
        // 这行代码只要执行，就相当于启动了spring容器，解析xml配置文件，并实例化所有bean对象，放到spring容器中
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml", "vip.xml");

        // 第二步：根据bean的id从spring容器中获取对象
        Object userBean = applicationContext.getBean("userBean");
        Object vipBean = applicationContext.getBean("vipBean");
        System.out.println(userBean);
        System.out.println(vipBean);

//        Object nowDate = applicationContext.getBean("nowDate");

        // Date nowDate = (Date) applicationContext.getBean("nowDate");
        // 不适用强制类型转换，可以使用一下方法（第二个参数指定返回类型）
        Date nowDate = applicationContext.getBean("nowDate", Date.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String strNowDate = sdf.format(nowDate);
        System.out.println(strNowDate);
    }
}
