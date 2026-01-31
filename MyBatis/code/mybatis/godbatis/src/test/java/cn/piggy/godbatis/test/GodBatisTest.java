package cn.piggy.godbatis.test;

import cn.piggy.godbatis.core.SqlSessionFactory;
import cn.piggy.godbatis.core.SqlSessionFactoryBuilder;
import cn.piggy.godbatis.utils.Resources;
import org.junit.Test;

public class GodBatisTest {
    @Test
    public void testSqlSessionFactory() throws Exception {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        System.out.println(sqlSessionFactory);
    }
}
