package com.piggy.service;

import com.piggy.dao.StudentDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Resource
    private StudentDao studentDao;

    public void deleteStudent() {
        studentDao.deleteById();
    }
}
