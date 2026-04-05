package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Student;

public interface StudentMapper {

    /**
     * 根据id获取学生信息，同时获取学生关联的班级信息
     * @param id 学生的id
     * @return 学生对象，但是学生对下给你当中含有班级对象
     */
    Student selectById(Integer id);
}
