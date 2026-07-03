package test;

import cn.piggy.mybatis.mapper.CarMapper;
import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class CarMapperTest {

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        int pageNum = 2;
        int pageSize = 3;
        // 执行SQL之前，开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 执行SQL
        List<Car> cars = mapper.selectAll();
        // cars.forEach(System.out::println);

        // 执行SQL之后，封装pageInfo分页信息
        PageInfo<Car> pageInfo = new PageInfo<Car>(cars, 5);
        System.out.println(pageInfo);

        sqlSession.close();
    }

    @Test
    public void testSelectByPage() {
        // 每页显示的记录条数
        int pageSize = 3;
        // 页码
        int pageNum = 2;
        // 计算起始下标
        int startIndex = (pageNum - 1) * pageSize;

        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByPage(startIndex, pageSize);
        cars.forEach(System.out::println);
    }
}
