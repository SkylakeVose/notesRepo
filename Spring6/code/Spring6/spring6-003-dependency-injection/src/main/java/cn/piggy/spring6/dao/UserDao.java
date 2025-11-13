package cn.piggy.spring6.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

    public static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void insert() {
        // System.out.println("数据库正在保存用户信息...");
        // 使用下log4j2日志框架
        logger.info("数据库正在保存用户信息...");
    }
}
