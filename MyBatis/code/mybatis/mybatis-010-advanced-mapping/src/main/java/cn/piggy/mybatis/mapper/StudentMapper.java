package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Student;

import java.util.List;

public interface StudentMapper {

    /**
     * 根据班级编号查询学生信息
     * @param cid
     * @return
     */
    List<Student> selectByCidStep2(Integer cid);

    /**
     * 分布查询第一步：先根据学生的id查询学生信息
     * @param sid
     * @return
     */
    Student selectByIdStep1(Integer sid);

    /**
     * 一条SQL语句：association
     * @param id
     * @return
     */
    Student selectByIdAssociation(Integer id);

    /**
     * 根据id获取学生信息，同时获取学生关联的班级信息
     * @param id 学生的id
     * @return 学生对象，但是学生对下给你当中含有班级对象
     */
    Student selectById(Integer id);
}
