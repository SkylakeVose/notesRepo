package cn.piggy.spring6.test;


import cn.piggy.service.OrderService;
import cn.piggy.spring6.bean.Order;
import cn.piggy.spring6.bean.Student;
import cn.piggy.spring6.bean.User;
import cn.piggy.spring6.bean.Vip;
import cn.piggy.spring6.bean3.MyDataSource;
import cn.piggy.spring6.bean3.Person;
import cn.piggy.spring6.dao.OrderDao;
import com.piggy.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCAnnotationTest {

    @Test
    public void testResource(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-resource.xml");
        StudentService studentService = applicationContext.getBean("studentService", StudentService.class);
        studentService.deleteStudent();
    }

    @Test
    public void testAutowired() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-autowired.xml");
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        orderService.generate();
    }

    @Test
    public void testDIByAnnotation(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-di-annotation.xml");
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
    }

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

    @Test
    public void testChoose() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-choose.xml");
    }
}
