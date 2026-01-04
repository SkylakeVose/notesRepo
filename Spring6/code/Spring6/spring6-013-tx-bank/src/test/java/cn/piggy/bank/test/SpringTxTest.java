package cn.piggy.bank.test;

import cn.piggy.bank.Spring6Config;
import cn.piggy.bank.dao.impl.IsolationService1;
import cn.piggy.bank.dao.impl.IsolationService2;
import cn.piggy.bank.pojo.Account;
import cn.piggy.bank.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringTxTest {

    @Test
    public void testNoXML() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Spring6Config.class);
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        try {
            accountService.transfer("act-001", "act-002", 10000);
            System.out.println("转账成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsolation1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        IsolationService1 i1 = applicationContext.getBean("i1", IsolationService1.class);
        i1.getByActno("act-004");
    }

    @Test
    public void testIsolation2() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        IsolationService2 i2 = applicationContext.getBean("i2", IsolationService2.class);
        Account accout = new Account("act-004", 1000.0);
        i2.save(accout);
    }

    @Test
    public void testSpringTx() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        try {
            accountService.transfer("act-001", "act-002", 10000);
            System.out.println("转账成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
