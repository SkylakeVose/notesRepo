package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.ClazzMapper;
import cn.piggy.mybatis.pojo.Clazz;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ClazzMapperTest {

    @Test
    public void testSelectByStep1(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByStep1(1000);
        System.out.println(clazz);
        sqlSession.close();
    }

    @Test
    public void testSelectByCollection() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCollection(1001);
        System.out.println(clazz);
        sqlSession.close();
    }
}
