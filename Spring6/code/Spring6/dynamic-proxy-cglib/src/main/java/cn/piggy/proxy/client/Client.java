package cn.piggy.proxy.client;

import cn.piggy.proxy.service.TimerMethodInterceptor;
import cn.piggy.proxy.service.UserService;
import net.sf.cglib.proxy.Enhancer;

public class Client {
    public static void main(String[] args) {
        // 创建字节码增强器对象
        // 这个对象是CGLIB库当中的核心对象，就是依靠它来生成代理类
        Enhancer enhancer = new Enhancer();

        // 告诉CGLIB父类是谁？也即指定目标类
        enhancer.setSuperclass(UserService.class);

        // 设置回调（等同于JDK动态代理当中的调用处理器：InvocationHandler）
        // 在CGLIB当中不是InvocationHandler，而是方法拦截器MethodInterception
        enhancer.setCallback(new TimerMethodInterceptor());

        // 创建代理对象
        // 这个方法会做两件事：
        //  1. 在内存中色花姑娘成UserService类的子类，其实就是代理类的字节码。
        //  2. 创建代理对象
        // 父类是UserService，子类这个代理类一定也是UserService
        UserService userServiceProxy = (UserService) enhancer.create();

        // 调用代理对象的代理方法
        boolean success = userServiceProxy.login("admin", "123456");
        System.out.println(success ? "登陆成功" : "登录失败");

        userServiceProxy.logout();
    }
}
