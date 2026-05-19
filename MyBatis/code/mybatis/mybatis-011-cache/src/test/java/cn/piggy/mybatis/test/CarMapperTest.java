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
        // 使用SqlSessionFactory来创建两个不同的SqlSession对象
        // 因为是同一个SqlSessionFactory对象，因此他们使用同一个二级缓存
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);

        // 数据缓存到sqlSession1的一级缓存中
        Car car1 = mapper1.selectById(2L);
        System.out.println(car1);

        // sqlSession1一级缓存中的数据写入到二级缓存
        sqlSession1.close();

        // 数据缓存到sqlSession2的一级缓存中
        Car car2 = mapper2.selectById(2L);
        System.out.println(car2);

        // sqlSession2一级缓存中的数据写入到二级缓存
        sqlSession2.close();
    }

    // 思考：什么时候不走缓存？
    //  1. SqlSession对象不是同一个，不走缓存
    //  2. 查询条件不一样，也不走缓存

    // 思考：什么时候一级缓存失效？
    // 两次DQL语句执行之间，做了以下两件事情，都会让缓存清空：
    //  1. 执行了sqlSession的clearCache()方法，这是手动清空缓存。
    //  2. 执行了INSERT、DELETE或UPDATE语句。（不管是操作哪张表，都会清空一级缓存）


    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(2L);
        System.out.println(car1);

        // 情况1：手动清空一级缓存
        sqlSession.clearCache();

        // 情况2：执行了增删改操作（只做演示，无具体实现）
        // mapper1.insert(...)

        CarMapper mapper2 = sqlSession.getMapper(CarMapper.class);
        Car car2 = mapper2.selectById(2L);
        System.out.println(car2);

        sqlSession.close();
    }
}
