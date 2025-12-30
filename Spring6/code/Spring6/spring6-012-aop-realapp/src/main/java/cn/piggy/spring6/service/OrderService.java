package cn.piggy.spring6.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // 生成订单
    public void generate() {
        System.out.println("正在生成订单...");
    }

    // 取消订单
    public void cancel() {
        System.out.println("订单正在取消...");
        // 故意制造异常
        int i = 10 / 0;
    }
}
