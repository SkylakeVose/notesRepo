package cn.piggy.mybatis.test;


import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsertBatch() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        List<Car> cars = new ArrayList<>();
        cars.add(new Car(null, "1008", "宝马x3", 20.0, "2022-12-10", "燃油车"));
        cars.add(new Car(null, "1009", "宝马x5", 44.0, "2022-11-12", "燃油车"));
        cars.add(new Car(null, "1010", "宝马x7", 87.0, "2024-05-13", "燃油车"));

        int count = mapper.insertBatch(cars);
        System.out.println("影响行数：" + count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteByIds() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.deleteByIds(new Long[]{1L, 2L, 3L});
        System.out.println("影响行数：" + count);
        // sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByChoose() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 第一个参数不为空
        // List<Car> cars = mapper.selectByChoose("比亚迪", null, null);
        // cars.forEach(System.out::println);

        // 第二个参数不为空
        // List<Car> cars = mapper.selectByChoose("", 10.0, null);
        // cars.forEach(System.out::println);

        // 第三个参数不为空
        // List<Car> cars = mapper.selectByChoose("", null, "新能源");
        // cars.forEach(System.out::println);

        // 三个参数全为空
        List<Car> cars = mapper.selectByChoose("", null, null);
        cars.forEach(System.out::println);

        sqlSession.close();
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(3L, null, null, 26.0, null, "油电混动");
        mapper.updateById(car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithTrim() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 三个条件都不是空
        // List<Car> cars = mapper.selectByMultiConditionWithTrim("比亚迪", 2.0, "新能源");

        // 三个条件都是空
        // List<Car> cars = mapper.selectByMultiConditionWithTrim("", null, "");

        // 三个条件不全满足
        // List<Car> cars = mapper.selectByMultiConditionWithTrim("", 4.0, "燃油车");

        // 三个条件不全满足
        List<Car> cars = mapper.selectByMultiConditionWithTrim("比亚迪", null, "");

        cars.forEach(System.out::println);
        sqlSession.close();
    }


    @Test
    public void testSelectByMultiConditionWithWhere() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 三个条件都不是空
        // List<Car> cars = mapper.selectByMultiConditionWithWhere("比亚迪", 2.0, "新能源");

        // 三个条件都是空
        List<Car> cars = mapper.selectByMultiConditionWithWhere("", null, "");

        // 三个条件不全满足
        // List<Car> cars = mapper.selectByMultiConditionWithWhere("", 4.0, "燃油车");

        cars.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiCondition() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 三个条件都不是空
        List<Car> cars = mapper.selectByMultiCondition("比亚迪", 2.0, "新能源");

        // 三个条件都是空
        // List<Car> cars = mapper.selectByMultiCondition("", null, "");

        // 三个条件不全满足
        // List<Car> cars = mapper.selectByMultiCondition("", 4.0, "");

        cars.forEach(System.out::println);
        sqlSession.close();
    }
}
