package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {
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
