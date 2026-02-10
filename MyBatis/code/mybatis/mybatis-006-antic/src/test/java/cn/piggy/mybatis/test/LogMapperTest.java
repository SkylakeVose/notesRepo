package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.LogMapper;
import cn.piggy.mybatis.pojo.Log;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class LogMapperTest {

    @Test
    public void testselectAllByTable() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        LogMapper mapper = sqlSession.getMapper(LogMapper.class);
        List<Log> logs = mapper.selectAllByTable("20260210");
        logs.forEach(System.out::println);
    }
}
