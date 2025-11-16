package cn.piggy.spring6.test;

import cn.piggy.spring6.bean.QianDaYe;
import cn.piggy.spring6.bean.SimpleValueType;
import cn.piggy.spring6.bean.Student;
import cn.piggy.spring6.jdbc.MyDataSource;
import cn.piggy.spring6.service.CustomerService;
import cn.piggy.spring6.service.OrderService;
import cn.piggy.spring6.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringDITest {

    @Test
    public void testArray() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-array.xml");
        QianDaYe yuQian = applicationContext.getBean("yuQian", QianDaYe.class);
        System.out.println(yuQian);

    }

    @Test
    public void testCascade() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("cascade.xml");

        Student studentBean = applicationContext.getBean("studentBean", Student.class);
        System.out.println(studentBean);
    }

    @Test
    public void testMyDataSource() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        MyDataSource myDataSource = applicationContext.getBean("myDataSource", MyDataSource.class);
        System.out.println(myDataSource);
    }

    @Test
    public void testSimpleType(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        /*User user = applicationContext.getBean("userBean", User.class);
        System.out.println(user);*/

        SimpleValueType svt = applicationContext.getBean("svt",SimpleValueType.class);
        System.out.println(svt);
    }

    @Test
    public void testSetDI2() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        OrderService orderService = applicationContext.getBean("orderServiceBean", OrderService.class);
        orderService.generate();
    }

    @Test
    public void testSetDI() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = applicationContext.getBean("userServiceBean", UserService.class);
        userService.saveUser();
    }

    @Test
    public void testConstructorDI() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        CustomerService customerService = applicationContext.getBean("csBean", CustomerService.class);
        customerService.save();
    }
}
