package cn.piggy.godbatis.test;

import cn.piggy.godbatis.core.SqlSession;
import cn.piggy.godbatis.core.SqlSessionFactory;
import cn.piggy.godbatis.core.SqlSessionFactoryBuilder;
import cn.piggy.godbatis.pojo.User;
import cn.piggy.godbatis.utils.Resources;
import org.junit.Test;

public class UserMapperTest {
    @Test
    public void testInsertUser() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User("1112", "李四", "30");
        int count = sqlSession.insert("user.insertUser", user);
        System.out.println("影响行数:" + count);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectById() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Object obj = sqlSession.selectOne("user.selectById", "1112");
        System.out.println(obj);

        sqlSession.close();
    }
}
