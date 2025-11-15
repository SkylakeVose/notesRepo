package cn.piggy.spring6.service;

import cn.piggy.spring6.dao.OrderDao;

public class OrderService {

    private OrderDao orderDao;

    // 通过set方法给属性赋值
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * 生成订单的业务方法
     */
    public void generate() {
        orderDao.insert();
    }
}
