package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class CarMapperTest {

    @Test
    public void testSelectById2() throws IOException {
        // 我们之前实现这个创建sqlSession对象的时候，使用了ThreadLocal，使其获取的SqlSession对象都是同一个
        // SqlSession sqlSession = SqlSessionUtil.openSession();

        // 使用SqlSessionFactory来创建两个不同的SqlSession对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(2L);
        System.out.println(car1);

        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);
        Car car2 = mapper2.selectById(2L);
        System.out.println(car2);

        sqlSession1.close();
        sqlSession2.close();
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(2L);
        System.out.println(car1);

        CarMapper mapper2 = sqlSession.getMapper(CarMapper.class);
        Car car2 = mapper2.selectById(2L);
        System.out.println(car2);

        sqlSession.close();
    }
}
