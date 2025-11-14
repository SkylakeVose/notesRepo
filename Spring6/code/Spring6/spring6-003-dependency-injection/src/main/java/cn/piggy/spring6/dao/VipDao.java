package cn.piggy.spring6.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VipDao {

    public static final Logger logger = LoggerFactory.getLogger(VipDao.class);

    public void insert() {
        logger.info("正在保存vip信息...");
    }
}
