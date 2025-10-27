package cn.piggy.spring6.dao.impl;

import cn.piggy.spring6.dao.UserDao;

/**
 * 升级到ORACLE数据库
 */
public class UserDaoImplForOracle implements UserDao {
    @Override
    public void deleteById() {
        System.out.println("ORACLE正在删除用户信息...");
    }
}
