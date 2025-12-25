package cn.piggy.proxy.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 专用负责计时的一个调用处理器对象
// 在这个调用处理器当中编写计时相关的增强代码
public class TimerInvocationHandler implements InvocationHandler {

    /*
        1. 为什么强行要求必须实现InvocationHandler接口？
            因为一个类实现接口就必须实现接口中的方法。
            以下这个方法必须是invoke()，因为JDK在底层调用invoke()方法的程序已经提前写好了。
            注意：invoke方法不是我们程序员负责调用的，是JDK负责调用的。
        2. invoke方法什么时候被调用的？
            当代理对象调用代理方法的时候，注册在InvocationHandler调用处理器当中的invoke()方法就会被调用。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke...");
        return null;
    }
}
