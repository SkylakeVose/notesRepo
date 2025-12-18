package com.piggy.dao.impl;

import com.piggy.dao.StudentDao;
import org.springframework.stereotype.Repository;

@Repository("studentDao1")
public class StudentDaoImplForMySQL implements StudentDao {
    @Override
    public void deleteById() {
        System.out.println("MySQL正在删除学生信息...");
    }
}
