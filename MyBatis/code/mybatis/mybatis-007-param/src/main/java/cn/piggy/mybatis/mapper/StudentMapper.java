package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Student;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {


    /**
     * 保存学生信息，通过POJO参数。
     * 以下是单个参数，但是参数的类型不是简单类型，是POJO实体类
     * @param student
     * @return
     */
    int insertStudentByPOJO(Student student);

    /**
     * 保存学生信息，通过Map参数。
     * 以下是单个参数，但是参数的类型不是简单类型，是Map集合
     * @param map
     * @return
     */
    int insertStudentByMap(Map<String, Object > map);

    /**
     * 当接口中的方法的参数只有一个（单个参数），并且参数的数据类型都是简单类型
     * 根据id查询、name查询、birth查询，sex查询
     */
    List<Student> selectById(Long id);
    List<Student> selectByName(String name);
    List<Student> selectByBirth(Date birth);
    List<Student> selectBySex(Character sex);
}
