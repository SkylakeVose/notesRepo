package cn.piggy.spring6.service;

import org.aspectj.lang.ProceedingJoinPoint;

public class TimerAspect {
    // 通知
    public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();    // 前环绕

        // 目标执行
        joinPoint.proceed();

        long end = System.currentTimeMillis();      // 后环绕
        System.out.println("耗时" + (end - begin) + "ms");
    }
}
