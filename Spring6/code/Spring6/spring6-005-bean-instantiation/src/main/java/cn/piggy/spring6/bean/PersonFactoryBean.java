package cn.piggy.spring6.bean;

import org.springframework.beans.factory.FactoryBean;

// PersonFactoryBean也是一个Bean，但是比较特殊，叫做工厂Bean
// 通过工厂Bean这个特殊的Bean可以获取一个普通的Bean
public class PersonFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        // 最终这个Bean的创建还是程序员自己new的
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    // 这个方法在接口中有默认实现，默认返回true（表示单例）
    // 如果想多例，可以将该方法返回值修改为false
    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
