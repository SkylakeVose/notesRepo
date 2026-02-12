package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsertCarUseGeneratedKey() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "527", "大众迈腾",
                20.0, "2016-12-12", "燃油车");
        mapper.insertCarUseGeneratedKey(car);
        System.out.println("插入汽车：" + car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByBrandLike() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByBrandLike("比亚迪");
        cars.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testDeleteBatch() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.deleteBatch("1,2,4");
        System.out.println("影响行数：" + count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByCarType(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // mapper实际上就是daoImpl对象
        // 底层不但为CarMapper接口生成了字节码，并且还new实现类对象了
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByCarType("新能源");
        // 遍历
        cars.forEach(System.out::println);
        // 关闭会话
        sqlSession.close();
    }

    @Test
    public void testselectAllByAscOrDesc(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // mapper实际上就是daoImpl对象
        // 底层不但为CarMapper接口生成了字节码，并且还new实现类对象了
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAllByAscOrDesc("desc");
        // 遍历
        cars.forEach(System.out::println);
        // 关闭会话
        sqlSession.close();
    }
}
