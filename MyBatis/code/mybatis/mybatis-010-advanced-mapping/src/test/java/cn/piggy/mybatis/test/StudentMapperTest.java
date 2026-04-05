package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.StudentMapper;
import cn.piggy.mybatis.pojo.Student;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class StudentMapperTest {
    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(1);
        System.out.println(student);
        sqlSession.close();
    }
}
