package cn.piggy.proxy.util;

import cn.piggy.proxy.service.OrderService;
import cn.piggy.proxy.service.TimerInvocationHandler;

import java.lang.reflect.Proxy;

public class ProxyUtil {
    // 封装一个工具方法，可以通过这个方法获取代理对象
    public static Object newProxyInstance(Object target) {
         return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TimerInvocationHandler(target));
    }
}
