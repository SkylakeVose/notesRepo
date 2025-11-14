package cn.piggy.spring6.service;

import cn.piggy.spring6.dao.UserDao;

public class UserService {

    private UserDao userDao;

    // set注入的话，必须提供一个set方法
    // Spring容器会调用这个set方法，来给userDao属性赋值

    // 这个set方法是IDEA工具生成的，符合javabean规范
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 我们自己写一个set方法，不适用IDEA工具生成的，不符合javabean规范的
     * 至少这个方法是以set单词开头的
     */
    /*public void setMySQLUserDao(UserDao xyz) {
        this.userDao = xyz;
    }*/

    public void saveUser() {
        // 保存用户信息到数据库
        userDao.insert();
    }
}
