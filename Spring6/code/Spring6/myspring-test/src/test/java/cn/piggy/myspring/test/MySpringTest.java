package cn.piggy.myspring.test;

import cn.piggy.myspring.bean.OrderService;
import org.junit.Test;
import org.myspringframework.core.ApplicationContext;
import org.myspringframework.core.ClassPathXmlApplicationContext;

public class MySpringTest {
    @Test
    public void testMySpring() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("myspring.xml");

        Object vip = applicationContext.getBean("Vip");
        System.out.println(vip);

        OrderService orderService = (OrderService) applicationContext.getBean("orderServiceBean");
        orderService.generate();

    }
}
