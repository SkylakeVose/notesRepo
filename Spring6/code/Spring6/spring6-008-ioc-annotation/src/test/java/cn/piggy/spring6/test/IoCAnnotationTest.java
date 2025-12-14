package cn.piggy.spring6.test;


import cn.piggy.spring6.bean.Order;
import cn.piggy.spring6.bean.Student;
import cn.piggy.spring6.bean.User;
import cn.piggy.spring6.bean.Vip;
import cn.piggy.spring6.dao.OrderDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCAnnotationTest {

    @Test
    public void testBeanComponent(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        User userBean = applicationContext.getBean("userBean", User.class);
        System.out.println(userBean);

        Vip vipBean = applicationContext.getBean("vipBean", Vip.class);
        System.out.println(vipBean);

        Order orderBean = applicationContext.getBean("order", Order.class);
        System.out.println(orderBean);

        Student studentBean = applicationContext.getBean("student", Student.class);
        System.out.println(studentBean);

        OrderDao orderDao = applicationContext.getBean("orderDao", OrderDao.class);
        System.out.println(orderDao);

    }
}
