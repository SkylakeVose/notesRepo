package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class CarMapperTest {
    @Test
    public void testInsert() throws Exception{
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "1008", "丰田 卡罗拉", 10.0, "2018-10-10", "燃油车");
        int count = mapper.insert(car);
        System.out.println("插入了几条记录：" + count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDelete() throws Exception{
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        mapper.deleteById(8L);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdate() throws Exception{
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(7L,"1001", "凯美瑞", 30.0,"2020-11-11", "新能源");
        mapper.update(car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectById() throws Exception{
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper carMapper = sqlSession.getMapper(CarMapper.class);
        Car car = carMapper.selectById(2L);
        System.out.println(car);
    }
}
