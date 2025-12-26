package cn.piggy.proxy.service;

public class OrderServiceImpl implements OrderService {

    @Override
    public void generate() {
        // 模拟耗时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("订单已生成...");
    }

    @Override
    public void modify() {
        // 模拟耗时
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("订单已修改...");
    }

    @Override
    public void detail() {
        // 模拟耗时
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("请看订单详情...");
    }
}
