package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.pojo.CarExample;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class CarMapperTest {

    // CarEample类是封装查询条件的
    @Test
    public void testSelect() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 1. 查询一个
        Car car = mapper.selectByPrimaryKey(1L);
        System.out.println(car);

        // 2. 查询所有（查询条件为null）
        List<Car> cars = mapper.selectByExample(null);
        cars.forEach(System.out::println);

        // 3. 按照条件进行查询
        // QBC风格：Query by criteria。一种查询方式，比较面向对象，看不到sql语句
        // 封装查询条件
        CarExample carExample = new CarExample();
        // 创建carExample.createCriteria()方法添加查询条件
        carExample.createCriteria()
                .andBrandLike("%比亚迪%")
                .andGuidePriceGreaterThan(new BigDecimal(20.0));
        carExample.or().andCarTypeEqualTo("燃油车");
        // 执行查询
        List<Car> cars2 = mapper.selectByExample(carExample);
        cars2.forEach(System.out::println);
    }
}
