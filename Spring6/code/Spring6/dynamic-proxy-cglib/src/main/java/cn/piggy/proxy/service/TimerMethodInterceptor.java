package cn.piggy.proxy.service;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TimerMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object target, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        long begin = System.currentTimeMillis();

        // 用目标对象的目标方法
        Object retValue = methodProxy.invoke(target, objects);

        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "ms");

        return retValue;
    }
}
