package cn.piggy.godbatis.test;

import cn.piggy.godbatis.core.SqlSession;
import cn.piggy.godbatis.core.SqlSessionFactory;
import cn.piggy.godbatis.core.SqlSessionFactoryBuilder;
import cn.piggy.godbatis.pojo.User;
import cn.piggy.godbatis.utils.Resources;
import org.junit.Test;

import java.sql.SQLException;

public class GodBatisTest {

    /*@Test
    public void testSelectOne() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行SQL
        Object obj = sqlSession.selectOne("user.selectById", "1111");
        System.out.println(obj);

        sqlSession.close();
    }

    @Test
    public void testInsertUser() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行SQL
        User user = new User("1111", "张三", "28");
        int count = sqlSession.insert("user.insertUser", user);
        System.out.println("影响行数：" + count);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSqlSessionFactory() throws Exception {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        System.out.println(sqlSessionFactory);
    }*/
}
