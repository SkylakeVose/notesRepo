package cn.piggy.spring6.test;

import cn.piggy.spring6.bean.Student;
import cn.piggy.spring6.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLifecycleTest {

    @Test
    public void testRegisterBean() {
        // 自己new对象
        Student student = new Student();
        System.out.println(student);

        // 将以上自己new的对象纳入Spring容器来管理
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerSingleton("studentBean",student);

        // 从Spring容器中获取
        Object studentBean = factory.getBean("studentBean");
        System.out.println(studentBean);
    }

    @Test
    public void testBeanLifecycleFive() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println("第八步：使用Bean:" + user);

        // 注意：必须手动关闭Spring容器，这样Spring容器才会销毁Bean
        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
    }
}
