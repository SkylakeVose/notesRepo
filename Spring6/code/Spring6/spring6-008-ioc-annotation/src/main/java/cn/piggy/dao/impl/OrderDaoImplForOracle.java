package cn.piggy.dao.impl;

import cn.piggy.dao.OrderDao;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImplForOracle implements OrderDao {
    @Override
    public void insert() {
        System.out.println("Oracle正在保存订单信息...");
    }
}
