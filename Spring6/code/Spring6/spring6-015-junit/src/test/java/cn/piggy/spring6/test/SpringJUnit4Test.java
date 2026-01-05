package cn.piggy.spring6.test;

import cn.piggy.spring6.bean.User;
import cn.piggy.spring6.bean.Vip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SpringJUnit4Test {

    @Autowired
    private User user;

    @Autowired
    private Vip vip;

    @Test
    public void testUser() {
        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user = applicationContext.getBean("user", User.class);*/
        System.out.println(user.getName());
    }

    @Test
    public void testVip() {
        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Vip vip = applicationContext.getBean("vip", Vip.class);*/
        System.out.println(vip.getName());
    }

    @Test
    public void testUserAndVip() {
        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user = applicationContext.getBean("user", User.class);
        Vip vip = applicationContext.getBean("vip", Vip.class);*/
        System.out.println("user:" + user.getName() + ", vip:" + vip.getName());
    }
}
