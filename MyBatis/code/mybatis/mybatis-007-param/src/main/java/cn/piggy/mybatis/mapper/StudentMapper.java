package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Student;

import java.util.Date;
import java.util.List;

public interface StudentMapper {
    /**
     * 当接口中的方法的参数只有一个（单个参数），并且参数的数据类型都是简单类型
     * 根据id查询、name查询、birth查询，sex查询
     */
    List<Student> selectById(Long id);
    List<Student> selectByName(String name);
    List<Student> selectByBirth(Date birth);
    List<Student> selectBySex(Character sex);
}
