package cn.piggy.dao.impl;

import cn.piggy.dao.OrderDao;
import org.springframework.stereotype.Repository;

// @Repository
public class OrderDaoImplForMySQL implements OrderDao {
    @Override
    public void insert() {
        System.out.println("MySQL正在保存订单信息...");
    }
}
