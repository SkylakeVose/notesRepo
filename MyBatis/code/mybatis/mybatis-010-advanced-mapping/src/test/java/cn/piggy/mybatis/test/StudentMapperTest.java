package cn.piggy.mybatis.test;

import cn.piggy.mybatis.mapper.StudentMapper;
import cn.piggy.mybatis.pojo.Student;
import cn.piggy.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class StudentMapperTest {
    @Test
    public void testSelectByIdStep1() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByIdStep1(5);
        // System.out.println(student);

        // 只输出学生名字，不输出班级信息
        System.out.println(student.getSname());

        // 输出学生对应的班级名称
        System.out.println(student.getClazz().getCname());

        sqlSession.close();
    }

    @Test
    public void testSelectByIdAssociation() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByIdAssociation(2);
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(1);
        System.out.println(student);
        sqlSession.close();
    }
}
