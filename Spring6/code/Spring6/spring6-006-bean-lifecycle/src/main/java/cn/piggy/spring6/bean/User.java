package cn.piggy.spring6.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * Bean生命周期-五步:
 *  第一步：实例化Bean（调用无参数构造方法）
 *  第二步：给Bean赋值（调用set方法）
 *  第三步：初始化Bean（会调用Bean的init方法，注意该方法需要自己写，自己配）
 *  第四步：使用Bean
 *  第五步：销毁Bean（会调用Bean的destroy方法，注意该方法需要自己写，自己配）
 */
public class User implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
        InitializingBean, DisposableBean {


    private String name;

    public void setName(String name) {
        System.out.println("第二步：给对象的属性赋值...");
        this.name = name;
    }

    public User() {
        System.out.println("第一步：无参构造方法执行...");
    }

    public void initBean() {
        System.out.println("第六步：初始化Bean...");
    }

    public void destroyBean() {
        System.out.println("第十步：销毁Bean...");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("\tBean这个类的加载器：" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("\t生产这个Bean的工厂对象是：" + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("第三步：");
        System.out.println("\t这个Bean的名字是：" + name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("第五步：InitializingBean afterPropertiesSet方法执行...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("第九步：DisposableBean destroy方法执行...");
    }
}
