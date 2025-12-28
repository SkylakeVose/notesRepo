package cn.piggy.spring6.test;

import cn.piggy.spring6.service.OrderService;
import cn.piggy.spring6.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAOPTest {
    @Test
    public void testBefore() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        /*UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.login();*/

        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        orderService.generate();
    }
}
