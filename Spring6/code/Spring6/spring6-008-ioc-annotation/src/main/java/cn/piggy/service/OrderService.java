package cn.piggy.service;

import cn.piggy.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderDao orderDao;

    public OrderService(@Autowired OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void generate() {
        orderDao.insert();
    }
}
