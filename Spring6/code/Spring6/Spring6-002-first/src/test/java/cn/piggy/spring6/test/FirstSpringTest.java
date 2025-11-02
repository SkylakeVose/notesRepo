package cn.piggy.spring6.test;

import cn.piggy.spring6.bean.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstSpringTest {

    @Test
    public void testFirstSpringCode() {
        // 第一步：获取spring容器对象
        // ApplicationContext：应用上下文，即spring容器
        // ApplicationContext是一个接口，有很多个实现类，其中有一个实现类叫 ClassPathXmlApplicationContext
        // ClassPathXmlApplicationContext类 是专门从类路径当中加载spring配置文件的一个Spring上下文对象
        // 这行代码只要执行，就相当于启动了spring容器，解析xml配置文件，并实例化所有bean对象，放到spring容器中
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        // 第二步：根据bean的id从spring容器中获取对象
        Object userBean = applicationContext.getBean("userBean");
        System.out.println(userBean);
    }
}
