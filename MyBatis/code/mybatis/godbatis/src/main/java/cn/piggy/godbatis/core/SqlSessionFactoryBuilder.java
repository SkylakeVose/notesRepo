package cn.piggy.godbatis.core;

import java.io.InputStream;

/**
 * SqlSessionFactory的构建器对象
 * 通过SqlSessionFactoryBuilder的build方法来解析godbatis-config.xml文件，然后创建SqlSessionFactory对象。
 */
public class SqlSessionFactoryBuilder {

    /**
     * 无参数构造方法
     */
    public SqlSessionFactoryBuilder() {
    }

    /**
     * 解析godbatis-config.xml文件，来构建SqlSessionFactory对象
     * @param in 指向godbatis-config.xml的文件流
     * @return
     */
    public SqlSessionFactory build(InputStream in) {
        // 解析完成之后，构建SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactory();
        return factory;
    }
}
