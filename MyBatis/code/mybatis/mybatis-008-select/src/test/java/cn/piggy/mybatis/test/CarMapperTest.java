package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class CarMapperTest {

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(1L);
        System.out.println(car);
        sqlSession.close();
    }
}
