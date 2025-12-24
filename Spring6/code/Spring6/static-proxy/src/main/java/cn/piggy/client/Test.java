package cn.piggy.client;

import cn.piggy.proxy.service.OrderServiceImpl;
import cn.piggy.proxy.service.OrderServiceImplSub;
import cn.piggy.proxy.service.OrderServiceProxy;

public class Test {
    public static void main(String[] args) {
        /*OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.generate();
        orderService.modify();
        orderService.detail();*/

        // 创建目标对象
        OrderServiceImpl target = new OrderServiceImpl();
        // 创建代理对象
        OrderServiceProxy proxy = new OrderServiceProxy(target);
        // 调用代理对象的代理方法
        proxy.generate();
        proxy.modify();
        proxy.detail();
    }
}
