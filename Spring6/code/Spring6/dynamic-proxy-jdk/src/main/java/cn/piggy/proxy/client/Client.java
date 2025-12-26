package cn.piggy.proxy.client;

import cn.piggy.proxy.service.OrderService;
import cn.piggy.proxy.service.OrderServiceImpl;
import cn.piggy.proxy.service.TimerInvocationHandler;
import cn.piggy.proxy.util.ProxyUtil;

import java.lang.reflect.Proxy;

public class Client {
    // 客户端程序
    public static void main(String[] args) {
        // 创建目标对象
        OrderService target = new OrderServiceImpl();

        // 创建代理对象
        /**
         *  1. newProxyInstance()：新建代理对象。
         *      本质上这个方法执行做了两件事：
         *      1.1 在内存中动态生成了一个代理类的字节码.class
         *      2.2 通过内存中生成的代理类，实例化了代理对象
         *
         *  2. 关于newPorxyInstance()方法的三个重要参数，每一个的含义和作用？
         *     1. CLassLoader loader：类加载器
         *      在内存当中生成的字节码文件（.class文件），要执行前也得先加载到内存当中，所以这里要制定类加载器
         *      并且JDK要求，目标类的类加载器 和 代理类的类加载器 必须要同一个。
         *     2. Class<?>[] interfaces：接口
         *      代理类和目标类要实现同一个或同一些接口
         *      在内存中生成代理类的时候，这个代理类是需要制定实现哪些接口。
         *     3. InvocationHandler h：调用处理器，是一个接口
         *      在调用处理器接口编写的就是：增强代码。
         *      既然是接口，就要写接口的实现类
         *
         *  注意：代理对象和目标对象实现的接口一样，所以可以向下转型
         */
        /*OrderService proxyObj = (OrderService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                                                target.getClass().getInterfaces(),
                                                new TimerInvocationHandler(target));*/

        // 上述代码通过一个工具类的封装，使看起来简洁一点
        OrderService proxyObj = (OrderService) ProxyUtil.newProxyInstance(target);

        // 调用代理对象的代理方法
        proxyObj.generate();
        proxyObj.modify();
    }
}
