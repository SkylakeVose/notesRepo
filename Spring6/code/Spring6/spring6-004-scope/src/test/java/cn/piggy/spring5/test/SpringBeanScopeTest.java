package cn.piggy.spring5.test;

import cn.piggy.spring6.bean.SpringBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanScopeTest {

    @Test
    public void testThreadScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-scope.xml");

        SpringBean sb = applicationContext.getBean("sb", SpringBean.class);
        System.out.println(sb);
        SpringBean sb1 = applicationContext.getBean("sb", SpringBean.class);
        System.out.println(sb1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SpringBean sb3 = applicationContext.getBean("sb", SpringBean.class);
                System.out.println(sb3);
                SpringBean sb4 = applicationContext.getBean("sb", SpringBean.class);
                System.out.println(sb4);
            }
        }).start();
    }

    @Test
    public void testBeanScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-scope.xml");

        SpringBean sb = applicationContext.getBean("sb", SpringBean.class);
        System.out.println(sb);

        SpringBean sb2 = applicationContext.getBean("sb", SpringBean.class);
        System.out.println(sb2);
    }
}
