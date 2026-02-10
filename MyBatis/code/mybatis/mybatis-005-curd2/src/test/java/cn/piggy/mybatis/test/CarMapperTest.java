package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {
    @Test
    public void testInsert() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 面向接口，获取接口的代理对象
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "12345", "凯美瑞", 3.0, "2000-10-30", "新能源");
        int count = mapper.insert(car);
        System.out.println("影响行数：" + count);
        
        sqlSession.commit();
    }

    @Test
    public void testDelete() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 面向接口，获取接口的代理对象
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.deleteById(54L);
        System.out.println("影响行数：" + count);

        sqlSession.commit();
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 面向接口，获取接口的代理对象
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(30L, "12345", "凯美瑞", 3.0, "2000-10-30", "新能源");
        int count = mapper.update(car);
        System.out.println("影响行数：" + count);

        sqlSession.commit();
    }

    @Test
    public void testSelect() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 面向接口，获取接口的代理对象
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(30L);
        System.out.println(car);
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 面向接口，获取接口的代理对象
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        cars.forEach(System.out::println);
    }
}
