package cn.piggy.proxy.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 专用负责计时的一个调用处理器对象
// 在这个调用处理器当中编写计时相关的增强代码
public class TimerInvocationHandler implements InvocationHandler {

    private Object target;

    public TimerInvocationHandler(Object target) {
        this.target = target;
    }

    /*
            1. 为什么强行要求必须实现InvocationHandler接口？
                因为一个类实现接口就必须实现接口中的方法。
                以下这个方法必须是invoke()，因为JDK在底层调用invoke()方法的程序已经提前写好了。
                注意：invoke方法不是我们程序员负责调用的，是JDK负责调用的。
            2. invoke方法什么时候被调用的？
                当代理对象调用代理方法的时候，注册在InvocationHandler调用处理器当中的invoke()方法就会被调用。
            3. invoke方法的三个参数：
                invoke方法是JDK负责调用的，所以JDK调用这个方法的时候会自动给我们传来这三个参数。
                3.1 Object proxy, 代理对象的引用
                3.2 Method method, 目标对象上的目标方法
                3.3 Object[] args, 目标方法上的实参
                invoke方法执行过程中，使用method来调用目标对象的目标方法。
         */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这个接口的目的是为了写增强代码

        long begin = System.currentTimeMillis();

        Object retValue = method.invoke(target, args);

        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "ms.");

        // 如果代理对象调用代理方法有返回结果，则需要将该执行结果继续返回。
        return retValue;
    }
}
