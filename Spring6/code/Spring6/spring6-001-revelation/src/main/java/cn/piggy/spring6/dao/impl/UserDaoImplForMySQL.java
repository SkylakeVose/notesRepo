package cn.piggy.spring6.dao.impl;

import cn.piggy.spring6.dao.UserDao;

public class UserDaoImplForMySQL implements UserDao {
    @Override
    public void deleteById() {
        System.out.println("MySQL正在删除用户信息...");
    }
}
