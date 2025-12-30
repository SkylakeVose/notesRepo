package cn.piggy.spring6.test;

import cn.piggy.spring6.biz.UserService;
import cn.piggy.spring6.biz.VipService;
import cn.piggy.spring6.service.AccountService;
import cn.piggy.spring6.service.OrderService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPRealAppTest {

    @Test
    public void testSecurityLog() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.saveUser();
        userService.deleteUser();
        userService.modifyUser();
        userService.getUser();

        System.out.println();

        VipService vipService = applicationContext.getBean("vipService", VipService.class);
        vipService.saveVip();
        vipService.deleteVip();
        vipService.modifyVip();
        vipService.getVip();
    }

    @Test
    public void testTransaction() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        accountService.transfer();
        accountService.withdraw();

        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        orderService.generate();
        orderService.cancel();
    }
}
